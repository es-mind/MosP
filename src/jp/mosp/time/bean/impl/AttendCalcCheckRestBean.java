/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.AttendCalcExecuteExtraBeanInterface;
import jp.mosp.time.bean.AttendCalcReferenceBeanInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.entity.AttendCalcEntityInterface;
import jp.mosp.time.entity.AttendanceEntityInterface;
import jp.mosp.time.entity.TimeDuration;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠計算(日々)確認処理。<br>
 * <br>
 * 残前休憩と残業休憩が次の時間と重複していないことを確認する。<br>
 * ・休憩<br>
 * ・私用外出<br>
 * ・公用外出<br>
 * <br>
 * 勤怠(日々)エンティティに設定されている次の値を用いる。<br>
 * ・始業時刻(勤怠計算上)<br>
 * ・終業時刻(勤怠計算上)<br>
 * ・全入力休憩時間群<br>
 * ・公用外出時間群<br>
 * ・私用外出時間群<br>
 * <br>
 * 確認に用いる勤怠計算(日々)エンティティの取得方法は、下記メソッドを参照。<br>
 * {@link AttendCalcExecuteBean#createCalcEntity(AttendanceEntityInterface) }<br>
 * <br>
 */
public class AttendCalcCheckRestBean extends PlatformBean implements AttendCalcExecuteExtraBeanInterface {
	
	/**
	 * 勤怠計算(日々)関連情報取得処理。<br>
	 */
	protected AttendCalcReferenceBeanInterface refer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AttendCalcCheckRestBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
	}
	
	@Override
	public void setAttendCalcRefer(AttendCalcReferenceBeanInterface refer) {
		// 勤怠計算(日々)関連情報取得処理を設定
		this.refer = refer;
	}
	
	@Override
	public void execute(AttendCalcEntityInterface calc, AttendanceDtoInterface dto) throws MospException {
		// 勤怠(日々)エンティティを取得
		AttendanceEntityInterface attendance = calc.getAttendance();
		// 個人IDと勤務日と始終業時刻を取得
		String personalId = attendance.getPersonalId();
		Date workDate = attendance.getWorkDate();
		int startTime = TimeUtility.getAttendMinutes(attendance.getStartTime(), workDate);
		int endTime = TimeUtility.getAttendMinutes(attendance.getEndTime(), workDate);
		// 勤怠設定を取得
		TimeSettingEntityInterface timeSetting = refer.getTimeSetting(personalId, workDate);
		// 入力休憩時間群を取得
		Map<Integer, TimeDuration> rests = attendance.getRestTimes(timeSetting);
		// 公用外出時間群を取得
		Map<Integer, TimeDuration> publics = attendance.getPublicGoOutTimes(timeSetting);
		// 私用外出時間群を取得
		Map<Integer, TimeDuration> privates = attendance.getPrivateGoOutTimes(timeSetting);
		// 残前休憩時間との重複を確認
		checkOvertimeBeforeRest(startTime, endTime, calc, rests, publics, privates);
		// 残業休憩時間との重複を確認
		checkOvertimeRest(startTime, endTime, calc, rests, publics, privates);
	}
	
	/**
	 * 残前休憩時間との重複を確認する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)
	 * @param endTime   終業時刻(勤怠計算上)
	 * @param calc      勤怠計算(日々)エンティティ
	 * @param rests     入力休憩時間群
	 * @param publics   公用外出時間群
	 * @param privates  私用外出時間群
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void checkOvertimeBeforeRest(int startTime, int endTime, AttendCalcEntityInterface calc,
			Map<Integer, TimeDuration> rests, Map<Integer, TimeDuration> publics, Map<Integer, TimeDuration> privates)
			throws MospException {
		// 残前休憩時間を取得
		Map<Integer, TimeDuration> overtimeBeforeRest = calc.getOvertimeBeforeRest(startTime, endTime);
		// 残前休憩時間と入力休憩時間群との重複を確認
		for (TimeDuration overlap : TimeUtility.getOverlap(overtimeBeforeRest, rests).values()) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorOverBefRestOverlapWithRest(mospParams, overlap);
		}
		// 残前休憩時間と公用外出時間群との重複を確認
		for (TimeDuration overlap : TimeUtility.getOverlap(overtimeBeforeRest, publics).values()) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorOverBefRestOverlapWithPublic(mospParams, overlap);
		}
		// 残前休憩時間と私用外出時間群との重複を確認
		for (TimeDuration overlap : TimeUtility.getOverlap(overtimeBeforeRest, privates).values()) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorOverBefRestOverlapWithPrivate(mospParams, overlap);
		}
	}
	
	/**
	 * 残業休憩時間との重複を確認する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)
	 * @param endTime   終業時刻(勤怠計算上)
	 * @param calc      勤怠計算(日々)エンティティ
	 * @param rests     入力休憩時間群
	 * @param publics   公用外出時間群
	 * @param privates  私用外出時間群
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void checkOvertimeRest(int startTime, int endTime, AttendCalcEntityInterface calc,
			Map<Integer, TimeDuration> rests, Map<Integer, TimeDuration> publics, Map<Integer, TimeDuration> privates)
			throws MospException {
		// 残業休憩時間を取得
		Map<Integer, TimeDuration> overtimeRests = calc.getOvertimeRest(startTime, endTime);
		// 残業休憩時間と入力休憩時間群との重複を確認
		for (TimeDuration overlap : TimeUtility.getOverlap(overtimeRests, rests).values()) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorOvertimeRestOverlapWithRest(mospParams, overlap);
		}
		// 残業休憩時間と公用外出時間群との重複を確認
		for (TimeDuration overlap : TimeUtility.getOverlap(overtimeRests, publics).values()) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorOvertimeRestOverlapWithPublic(mospParams, overlap);
		}
		// 残業休憩時間と私用外出時間群との重複を確認
		for (TimeDuration overlap : TimeUtility.getOverlap(overtimeRests, privates).values()) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorOvertimeRestOverlapWithPrivate(mospParams, overlap);
		}
	}
	
}
