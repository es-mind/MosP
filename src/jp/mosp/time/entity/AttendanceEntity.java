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
package jp.mosp.time.entity;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠(日々)エンティティ。<br>
 */
public class AttendanceEntity implements AttendanceEntityInterface {
	
	/**
	 * 勤怠(日々)情報。<br>
	 */
	protected AttendanceDtoInterface		attendanceDto;
	
	/**
	 * 休憩情報群。<br>
	 * 休憩開始時刻及び休憩終了時刻は入力値。<br>
	 * 休憩時間は、休憩開始時刻及び休憩終了時刻を丸めた時刻の差。<br>
	 */
	protected Collection<RestDtoInterface>	restDtos;
	
	/**
	 * 外出情報群。<br>
	 * 外出開始時刻及び外出終了時刻は入力値。<br>
	 * 外出時間は、外出開始時刻及び外出終了時刻を丸めた時刻の差。<br>
	 */
	protected Collection<GoOutDtoInterface>	goOutDtos;
	
	
	/**
	 * 勤怠(日々)情報が存在するかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：勤怠(日々)情報が存在する、false：存在しない)
	 */
	public boolean hasAttendance() {
		return attendanceDto != null;
	}
	
	@Override
	public String getPersonalId() {
		// 勤怠(日々)情報が存在しない場合
		if (hasAttendance() == false) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 個人IDを取得
		return attendanceDto.getPersonalId();
	}
	
	@Override
	public Date getWorkDate() {
		// 勤怠(日々)情報が存在しない場合
		if (hasAttendance() == false) {
			// nullを取得
			return null;
		}
		// 勤務日を取得
		return attendanceDto.getWorkDate();
	}
	
	@Override
	public String getWorkTypeCode() {
		// 勤怠(日々)情報が存在しない場合
		if (hasAttendance() == false) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 勤務形態コードを取得
		return attendanceDto.getWorkTypeCode();
	}
	
	@Override
	public Date getStartTime() {
		// 勤怠(日々)情報が存在しない場合
		if (hasAttendance() == false) {
			// nullを取得
			return null;
		}
		// 始業時刻を取得
		return attendanceDto.getStartTime();
	}
	
	@Override
	public Date getEndTime() {
		// 勤怠(日々)情報が存在しない場合
		if (hasAttendance() == false) {
			// nullを取得
			return null;
		}
		// 終業時刻を取得
		return attendanceDto.getEndTime();
	}
	
	@Override
	public boolean isAttendanceDirectStart() {
		// 勤怠(日々)情報が存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 直行が設定されているかを確認
		return isChecked(attendanceDto.getDirectStart());
	}
	
	@Override
	public boolean isAttendanceDirectEnd() {
		// 勤怠(日々)情報が存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 直帰が設定されているかを確認
		return isChecked(attendanceDto.getDirectEnd());
	}
	
	@Override
	public boolean isLateReasonTrain() {
		// 勤怠(日々)情報が存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 遅刻理由が電車遅延であるかを確認
		return MospUtility.isEqual(attendanceDto.getLateReason(), TimeConst.CODE_TARDINESS_WHY_TRAIN);
	}
	
	@Override
	public boolean isLateReasonCompany() {
		// 勤怠(日々)情報が存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 遅刻理由が会社指示であるかを確認
		return MospUtility.isEqual(attendanceDto.getLateReason(), TimeConst.CODE_TARDINESS_WHY_COMPANY);
	}
	
	@Override
	public boolean isLeaveEarlyReasonCompany() {
		// 勤怠(日々)情報が存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 早退理由が会社指示であるかを確認
		return MospUtility.isEqual(attendanceDto.getLeaveEarlyReason(), TimeConst.CODE_LEAVEEARLY_WHY_COMPANY);
	}
	
	@Override
	public Map<Integer, TimeDuration> getRestTimes(TimeSettingEntityInterface timeSetting) {
		// 休憩時間群を準備
		Map<Integer, TimeDuration> restTimes = new LinkedHashMap<Integer, TimeDuration>();
		// 休憩情報毎に処理
		for (RestDtoInterface dto : restDtos) {
			// 開始時刻及び終了時刻を取得
			int startTime = timeSetting.roundDailyRestStart(TimeUtility.getMinutes(dto.getRestStart(), getWorkDate()));
			int endTime = timeSetting.roundDailyRestEnd(TimeUtility.getMinutes(dto.getRestEnd(), getWorkDate()));
			// 時間間隔を作成し休憩時間群に統合
			restTimes = TimeUtility.mergeDurations(restTimes, TimeDuration.getInstance(startTime, endTime));
		}
		// 時間間隔を結合して休憩時間群を取得
		return TimeUtility.combineDurations(restTimes);
	}
	
	@Override
	public Map<Integer, TimeDuration> getAllGoOutTimes(TimeSettingEntityInterface timeSetting) {
		// 全外出時間群を取得
		return getGoOutTimes(goOutDtos, null, timeSetting);
	}
	
	@Override
	public Map<Integer, TimeDuration> getPublicGoOutTimes(TimeSettingEntityInterface timeSetting) {
		// 公用外出時間群を取得
		return getGoOutTimes(goOutDtos, TimeConst.CODE_GO_OUT_PUBLIC, timeSetting);
	}
	
	@Override
	public Map<Integer, TimeDuration> getPrivateGoOutTimes(TimeSettingEntityInterface timeSetting) {
		// 私用外出時間群を取得
		return getGoOutTimes(goOutDtos, TimeConst.CODE_GO_OUT_PRIVATE, timeSetting);
	}
	
	/**
	 * 外出時間群(キー：開始時刻(キー順))を取得する。<br>
	 * <br>
	 * 外出開始時刻及び終了時刻は、勤怠設定により丸められる。<br>
	 * その上で、時間間隔を結合する。<br>
	 * <br>
	 * @param dtos        外出情報群
	 * @param goOutType   外出区分
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 外出時間群(キー：開始時刻(キー順))
	 */
	protected Map<Integer, TimeDuration> getGoOutTimes(Collection<GoOutDtoInterface> dtos, Integer goOutType,
			TimeSettingEntityInterface timeSetting) {
		// 外出時間群を準備
		Map<Integer, TimeDuration> goOutTimes = new LinkedHashMap<Integer, TimeDuration>();
		// 外出情報毎に処理
		for (GoOutDtoInterface dto : dtos) {
			// 日外出入り時刻(分)及び日外出戻り時刻(分)を取得
			int startTime = TimeUtility.getMinutes(dto.getGoOutStart(), getWorkDate());
			int endTime = TimeUtility.getMinutes(dto.getGoOutEnd(), getWorkDate());
			// 開始時刻及び終了時刻(丸め)を取得
			startTime = timeSetting.roundDailyGoOutStart(startTime, dto.getGoOutType());
			endTime = timeSetting.roundDailyGoOutEnd(endTime, dto.getGoOutType());
			// 時間間隔を取得
			TimeDuration duration = TimeDuration.getInstance(startTime, endTime);
			// 時間間隔が妥当でない場合
			if (duration.isValid() == false) {
				// 次の外出情報へ
				continue;
			}
			// 外出区分が指定されていないか一致する場合
			if (goOutType == null || MospUtility.isEqual(dto.getGoOutType(), goOutType)) {
				// 外出時間群に統合
				goOutTimes = TimeUtility.mergeDurations(goOutTimes, duration);
			}
		}
		// 外出時間群(キー：開始時刻、値：終了時刻)(開始時刻順)を取得
		return TimeUtility.combineDurations(goOutTimes);
	}
	
	/**
	 * チェックボックスがチェックされているかを確認する。<br>
	 * @param value 値
	 * @return 確認結果(true：チェックされている、false：チェックされていない)
	 */
	protected boolean isChecked(int value) {
		// チェックボックスがチェックされているかを確認
		return MospUtility.isChecked(value);
	}
	
	@Override
	public void setAttendanceDto(AttendanceDtoInterface attendanceDto) {
		this.attendanceDto = attendanceDto;
	}
	
	@Override
	public void setRestDtos(Collection<RestDtoInterface> restDtos) {
		this.restDtos = restDtos;
	}
	
	@Override
	public void setGoOutDtos(Collection<GoOutDtoInterface> goOutDtos) {
		this.goOutDtos = goOutDtos;
	}
	
	@Override
	public AttendanceDtoInterface getAttendanceDto() {
		return attendanceDto;
	}
	
	@Override
	public Collection<RestDtoInterface> getRestDtos() {
		return restDtos;
	}
	
	@Override
	public Collection<GoOutDtoInterface> getGoOutDtos() {
		return goOutDtos;
	}
	
}
