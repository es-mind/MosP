/*
 * MosP - Mind Open Source Project
 * Copyright (C) esMind, LLC  https://www.e-s-mind.com/
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendCalcConditionBeanInterface;
import jp.mosp.time.bean.AttendCalcExecuteBeanInterface;
import jp.mosp.time.bean.AttendCalcExtraBeanInterface;
import jp.mosp.time.bean.AttendCalcReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.GoOutReferenceBeanInterface;
import jp.mosp.time.bean.RestReferenceBeanInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.entity.AttendanceEntityInterface;
import jp.mosp.time.utils.AttendanceUtility;

/**
 * 勤怠計算(日々)処理。<br>
 * <br>
 * 次の処理を行う。<br>
 * ・勤怠計算(日々)処理切替設定に基づき勤怠計算(日々)実行処理を取得<br>
 * ・勤怠計算(日々)処理を実行<br>
 * ・勤怠計算(日々)追加処理設定に基づき追加処理を実施<br>
 * <br>
 * 設定値は、/WEB-INF/xml/addon/time/time_calc_bean.xmlを参照。<br>
 * <br>
 */
public class AttendCalcBean extends TimeBean implements AttendanceCalcBeanInterface {
	
	/**
	 * コードキー(勤怠計算(日々)処理切替設定)。<br>
	 */
	protected static final String				CODE_KEY_SWITCH	= "AttendCalcSwitch";
	
	/**
	 * コードキー(勤怠計算(日々)事前処理設定)。<br>
	 */
	protected static final String				CODE_KEY_BEFORE	= "AttendCalcBefore";
	
	/**
	 * コードキー(勤怠計算(日々)事後処理設定)。<br>
	 */
	protected static final String				CODE_KEY_AFTER	= "AttendCalcAfter";
	
	/**
	 * 勤怠計算(日々)関連情報取得処理。<br>
	 */
	protected AttendCalcReferenceBeanInterface	refer;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		refer = createBeanInstance(AttendCalcReferenceBeanInterface.class);
	}
	
	/**
	 * 勤怠(日々)エンティティを作成して勤怠計算を実行する。<br>
	 */
	@Override
	public void attendanceCalc(AttendanceDtoInterface attendanceDto) throws MospException {
		// Beanを準備
		RestReferenceBeanInterface restRefer = createBeanInstance(RestReferenceBeanInterface.class);
		GoOutReferenceBeanInterface goOutRefer = createBeanInstance(GoOutReferenceBeanInterface.class);
		// 個人IDと勤務日を取得
		String personalId = attendanceDto.getPersonalId();
		Date workDate = attendanceDto.getWorkDate();
		// 休憩情報群と外出情報群を取得
		Collection<RestDtoInterface> restDtos = restRefer.getRestList(personalId, workDate);
		Collection<GoOutDtoInterface> goOutDtos = goOutRefer.getGoOutList(personalId, workDate);
		// 勤怠(日々)エンティティを作成
		AttendanceEntityInterface attendance = getAttendanceEntity(attendanceDto, restDtos, goOutDtos);
		// 勤怠計算(日々)
		calc(attendance);
		// 勤怠計算(日々)でエラーがあった場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// 勤怠(日々)情報に値を設定
		AttendanceUtility.getAttendanceDtoClone(attendanceDto, attendance.getAttendanceDto());
	}
	
	/**
	 * 勤怠(日々)エンティティを作成して勤怠計算を実行する。<br>
	 * <br>
	 */
	@Override
	public void attendanceCalc(AttendanceDtoInterface attendanceDto, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> publicGoOutList, List<GoOutDtoInterface> privateGoOutList) throws MospException {
		// 外出情報群を取得
		Set<GoOutDtoInterface> goOutDtos = new HashSet<GoOutDtoInterface>();
		goOutDtos.addAll(publicGoOutList);
		goOutDtos.addAll(privateGoOutList);
		// 勤怠(日々)エンティティを作成
		AttendanceEntityInterface attendance = getAttendanceEntity(attendanceDto, restList, goOutDtos);
		// 勤怠計算(日々)
		calc(attendance);
		// 勤怠計算(日々)でエラーがあった場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// 勤怠(日々)情報に値を設定
		AttendanceUtility.getAttendanceDtoClone(attendanceDto, attendance.getAttendanceDto());
	}
	
	/**
	 * 勤怠(日々)エンティティを作成して勤怠計算を実行する。<br>
	 * <br>
	 * 勤怠計算結果から次の項目を取得する。<br>
	 * ・始業時刻(勤怠計算上)<br>
	 * ・実始業時刻<br>
	 * ・終業時刻(勤怠計算上)<br>
	 * ・実終業時刻<br>
	 * <br>
	 * ここでは、休憩情報及び外出情報は取得せず、空の情報群を用いる。<br>
	 * また、useBetweenTimeは、用いない。<br>
	 * <br>
	 * 始終業時刻を取得するためのメソッドであるが、
	 * 勤怠計算(日々)事前処理と勤怠計算(日々)と勤怠計算(日々)追加処理が、
	 * 一通り実行される。<br>
	 * いずれは、当メソッドを廃止し、休憩情報と外出情報の調整も含めて
	 * 勤怠計算(日々)処理で実施することを検討する。<br>
	 * <br>
	 */
	@Override
	public void calcStartEndTime(AttendanceDtoInterface attendanceDto, boolean useBetweenTime) throws MospException {
		// 休憩情報群と外出情報群を準備
		Collection<RestDtoInterface> restDtos = Collections.emptySet();
		Collection<GoOutDtoInterface> goOutDtos = Collections.emptySet();
		// 勤怠(日々)エンティティを作成
		AttendanceEntityInterface attendance = getAttendanceEntity(attendanceDto, restDtos, goOutDtos);
		// 勤怠計算(日々)
		calc(attendance);
		// 勤怠計算(日々)でエラーがあった場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// 勤怠(日々)情報に値を設定
		attendanceDto.setStartTime(attendance.getAttendanceDto().getStartTime());
		attendanceDto.setActualStartTime(attendance.getAttendanceDto().getActualStartTime());
		attendanceDto.setEndTime(attendance.getAttendanceDto().getEndTime());
		attendanceDto.setActualEndTime(attendance.getAttendanceDto().getActualEndTime());
	}
	
	/**
	 * 勤怠計算を行う。<br>
	 * <br>
	 * 次の処理を行う。<br>
	 * ・勤怠計算(日々)処理切替設定に基づき勤怠計算(日々)実行処理を取得<br>
	 * ・勤怠計算(日々)処理を実行<br>
	 * ・勤怠計算(日々)追加処理設定に基づき追加処理を実施<br>
	 * <br>
	 * @param attendance 勤怠(日々)情報エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void calc(AttendanceEntityInterface attendance) throws MospException {
		// 勤怠計算(日々)事前処理設定に基づき事前処理を実施
		for (AttendCalcExtraBeanInterface extra : getExtraBeans(CODE_KEY_BEFORE, attendance)) {
			// 事前処理を実施
			extra.execute(attendance);
		}
		// 事前処理でエラーがあった場合
		if (mospParams.hasErrorMessage()) {
			// 計算をせずに処理を終了
			return;
		}
		// 勤怠計算(日々)処理切替設定に基づき勤怠計算(日々)実行処理を取得
		AttendCalcExecuteBeanInterface calc = getCalcBean(attendance);
		// 勤怠計算(日々)処理を実行
		calc.calc(attendance);
		// 勤怠計算(日々)追加処理設定に基づき事後処理を実施
		for (AttendCalcExtraBeanInterface extra : getExtraBeans(CODE_KEY_AFTER, attendance)) {
			// 追加処理を実施
			extra.execute(attendance);
		}
	}
	
	/**
	 * 勤怠計算(日々)処理切替設定に基づき勤怠計算(日々)実行処理を取得する。<br>
	 * <br>
	 * @param attendance 勤怠(日々)情報エンティティ
	 * @return 勤怠計算(日々)実行処理
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected AttendCalcExecuteBeanInterface getCalcBean(AttendanceEntityInterface attendance) throws MospException {
		// 勤怠計算(日々)実行処理を準備
		AttendCalcExecuteBeanInterface calc = null;
		// 勤怠計算(日々)処理切替設定毎に処理
		for (String[] array : MospUtility.getCodeArray(mospParams, CODE_KEY_SWITCH, false)) {
			// 勤怠計算(日々)処理切替条件クラス名及び勤怠計算(日々)実行処理クラス名を取得
			String conditionName = array[0];
			String executeName = array[1];
			// 切替条件を満たす場合
			if (isSatisfied(conditionName, executeName, attendance)) {
				// 勤怠計算(日々)処理を取得
				calc = createBean(AttendCalcExecuteBeanInterface.class, executeName);
				// 切替条件判定を終了
				break;
			}
		}
		// 切替条件判定で勤怠計算(日々)実行処理が取得できなかった場合
		if (calc == null) {
			// デフォルトの勤怠計算(日々)実行処理を取得
			calc = createBeanInstance(AttendCalcExecuteBeanInterface.class);
		}
		// 勤怠計算(日々)実行処理に勤怠計算(日々)関連情報取得処理を設定
		calc.setAttendCalcRefer(refer);
		// 勤怠計算(日々)実行処理を取得
		return calc;
	}
	
	/**
	 * 勤怠計算(日々)追加処理設定に基づき勤怠計算(日々)追加処理リストを取得する。<br>
	 * <br>
	 * @param codeKey    コードキー
	 * @param attendance 勤怠(日々)情報エンティティ
	 * @return 勤怠計算(日々)追加実行処理リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendCalcExtraBeanInterface> getExtraBeans(String codeKey, AttendanceEntityInterface attendance)
			throws MospException {
		// 勤怠計算(日々)追加処理リストを準備
		List<AttendCalcExtraBeanInterface> extras = new ArrayList<AttendCalcExtraBeanInterface>();
		// 勤怠計算(日々)追加処理設定毎に処理
		for (String[] array : MospUtility.getCodeArray(mospParams, codeKey, false)) {
			// 勤怠計算(日々)追加処理実行条件クラス名と勤怠計算(日々)追加処理クラス名を取得
			String conditionName = array[0];
			String executeName = array[1];
			// 実行条件を満たさない場合
			if (isSatisfied(conditionName, executeName, attendance) == false) {
				// 次の勤怠計算(日々)追加処理設定へ
				continue;
			}
			// 勤怠計算(日々)追加処理を取得
			AttendCalcExtraBeanInterface extra = createBean(AttendCalcExtraBeanInterface.class, executeName);
			// 勤怠計算(日々)関連情報取得処理を設定
			extra.setAttendCalcRefer(refer);
			// 勤怠計算(日々)追加処理を追加
			extras.add(extra);
		}
		// 勤怠計算(日々)追加処理リストを取得
		return extras;
	}
	
	/**
	 * 勤怠計算(日々)切替条件を満たすかを判定する。<br>
	 * <br>
	 * クラス名が同じである場合、切替条件を満たすと判定する。<br>
	 * そうでない場合、切替条件処理の判定に従う。<br>
	 * <br>
	 * @param conditionName 勤怠計算(日々)処理切替条件クラス名
	 * @param executeName   勤怠計算(日々)処理クラス名
	 * @param attendance    勤怠(日々)情報エンティティ
	 * @return 判定結果(true：切替条件を満たす、false：切替条件を満たさない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isSatisfied(String conditionName, String executeName, AttendanceEntityInterface attendance)
			throws MospException {
		// クラス名に空白がある場合
		if (MospUtility.isEmpty(conditionName, executeName)) {
			// 切替しないと判定
			return false;
		}
		// クラス名が同じである場合
		if (MospUtility.isEqual(conditionName, executeName)) {
			// 切替と判定
			return true;
		}
		// 切替判定処理を準備
		AttendCalcConditionBeanInterface condition = createBean(AttendCalcConditionBeanInterface.class, conditionName);
		// 切替判定処理に勤怠計算(日々)関連情報取得処理を設定
		condition.setAttendCalcRefer(refer);
		// 勤怠計算(日々)切替条件を満たすかを判定
		return condition.isSatisfied(attendance);
	}
	
	/**
	 * 勤怠(日々)エンティティを作成する。<br>
	 * @param attendanceDto 勤怠(日々)情報
	 * @param restDtos      休憩情報群
	 * @param goOutDtos     外出情報群
	 * @return 勤怠(日々)エンティティ
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected AttendanceEntityInterface getAttendanceEntity(AttendanceDtoInterface attendanceDto,
			Collection<RestDtoInterface> restDtos, Collection<GoOutDtoInterface> goOutDtos) throws MospException {
		// 勤怠(日々)エンティティを準備
		AttendanceEntityInterface attendance = createObject(AttendanceEntityInterface.class);
		// 勤怠(日々)エンティティに値を設定
		attendance.setAttendanceDto(attendanceDto);
		attendance.setRestDtos(restDtos);
		attendance.setGoOutDtos(goOutDtos);
		// 勤怠(日々)エンティティを取得
		return attendance;
	}
	
}
