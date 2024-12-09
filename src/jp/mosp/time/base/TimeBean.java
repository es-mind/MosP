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
package jp.mosp.time.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠管理におけるBeanの基本機能を提供する。<br>
 * <br>
 */
public abstract class TimeBean extends PlatformBean {
	
	/**
	 * デフォルト勤務回数。
	 */
	public static final int			TIMES_WORK_DEFAULT	= 1;
	
	/**
	 * MosPアプリケーション設定キー(勤怠申請後処理群)。<br>
	 */
	protected static final String	APP_AFTER_APPLY_ATT	= "AfterApplyAttendanceBeans";
	
	
	/**
	 * コンストラクタ。
	 */
	public TimeBean() {
		// 継承基の処理を実行
		super();
	}
	
	/**
	 * 勤怠管理用機能コードセットを取得する。<br>
	 * @return 勤怠管理用機能コードセット
	 */
	protected Set<String> getTimeFunctionSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(TimeConst.CODE_FUNCTION_WORK_MANGE);
		set.add(TimeConst.CODE_FUNCTION_OVER_WORK);
		set.add(TimeConst.CODE_FUNCTION_VACATION);
		set.add(TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
		set.add(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY);
		set.add(TimeConst.CODE_FUNCTION_DIFFERENCE);
		return set;
	}
	
	/**
	 * 時刻を取得する。<br>
	 * デフォルト基準日付の時刻を、基準日付における時刻に変換する。<br>
	 * @param time         デフォルト基準日付の時刻
	 * @param standardDate 基準日付
	 * @return 勤怠時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getTime(Date time, Date standardDate) throws MospException {
		// 基準日付における時刻を取得
		return DateUtility.getTime(time, standardDate);
	}
	
	/**
	 * デフォルト基準日付(1970/01/01 00:00:00)オブジェクトを取得する。<br>
	 * @return デフォルト基準日付オブジェクト
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Date getDefaultStandardDate() throws MospException {
		return DateUtility.getTime(0, 0);
	}
	
	/**
	 * 日付オブジェクトの差を時間(分)で取得する。<br>
	 * @param startTime 開始時刻
	 * @param endTime   終了時刻
	 * @return 日付オブジェクトの差(分)
	 */
	protected int getDefferenceMinutes(Date startTime, Date endTime) {
		return TimeUtility.getDifferenceMinutes(startTime, endTime);
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。<br>
	 * 基準日付からの時間(HH)部は、基準日付からの時間数で表される。<br>
	 * @param date	対象日付オブジェクト
	 * @param standardDate 基準日付
	 * @return 日付文字列(HH:mm)
	 */
	protected String getStringTime(Date date, Date standardDate) {
		// 日付確認
		if (date == null) {
			// 日付がnullならハイフン
			return getHyphenNaming();
		}
		// 日付文字列取得
		return DateUtility.getStringTime(date, standardDate);
	}
	
	/**
	 * ハイフン名称を取得する。<br>
	 * @return ハイフン名称
	 */
	protected String getHyphenNaming() {
		return PfNameUtility.hyphen(mospParams);
	}
	
	/**
	 * 勤怠設定が存在しない場合のエラーメッセージを設定する。<br>
	 * @param targetDate 基準日
	 */
	protected void addWorkTypeNotExistErrorMessage(Date targetDate) {
		String[] rep = { getStringDate(targetDate), mospParams.getName("Work", "Form", "Information") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_SETTING_APPLICATION_DEFECT, rep);
	}
	
	/**
	 * 出勤日でない場合のエラーメッセージを設定する。<br>
	 * @param targetDate 対象日
	 */
	protected void addNotWorkDateErrorMessage(Date targetDate) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_WORK_DATE, getStringDate(targetDate),
				mospParams.getName("jp.mosp.time.input.vo.WorkOnHolidayRequestVo"));
	}
	
	// 残業申請関連
	/**
	 * 残業申請する際に対象日が所定休日または法定休日で申請できない場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addOvertimeTargetWorkDateHolidayErrorMessage(Date date) {
		String[] aryRep = { DateUtility.getStringDate(date), mospParams.getName("GoingWork", "Day"),
			mospParams.getName("OvertimeWork", "Year", "Month", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
	}
	
	/**
	 * 残業申請する際に対象日に既に残業申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addOvertimeTargetDateOvertimeErrorMessage(Date date) {
		String[] aryRep = { DateUtility.getStringDate(date), mospParams.getName("OvertimeWork"),
			mospParams.getName("OvertimeWork", "Year", "Month", "Day", "Or", "OvertimeWork", "Type") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, aryRep);
		
	}
	
	/**
	 * 申請する際に対象日に既に勤怠の申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addOvertimeTargetWorkDateAttendanceRequestErrorMessage(Date date) {
		String[] aryRep = { getStringDate(date), mospParams.getName("WorkManage"),
			mospParams.getName("OvertimeWork", "Year", "Month", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, aryRep);
	}
	
	/**
	 * 残業申請可能な期間を過ぎた年月日で申請が行われている場合、エラーメッセージを追加する。<br>
	 */
	protected void addOvertimePeriodErrorMessage() {
		String[] aryRep = { mospParams.getName("OvertimeWork", "Application"), mospParams.getName("No1", "Months"),
			mospParams.getName("OvertimeWork", "Year") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_2, aryRep);
	}
	
	// 休暇申請関連
	
	/**
	 * 休暇が付与されていない場合、エラーメッセージを追加する。<br>
	 * @param holidayName 休暇名称
	 */
	protected void addHolidayNotGiveErrorMessage(String holidayName) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_NOT_GIVE, holidayName);
	}
	
	/**
	 * 休暇申請する際に休暇日数を超過していた場合、エラーメッセージを追加する。<br>
	 * @param holidayName 休暇名称
	 * @param unit 時間または日
	 */
	protected void addHolidayNumDaysExcessErrorMessage(String holidayName, String unit) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_NUM_DAYS_EXCESS, holidayName, unit);
	}
	
	/**
	 * 休暇申請する際に対象日に既に勤怠の申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addHolidayTargetWorkDateAttendanceRequestErrorMessage(Date date) {
		String[] aryRep = { getStringDate(date), mospParams.getName("WorkManage"),
			mospParams.getName("Holiday", "Year", "Month", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, aryRep);
	}
	
	/**
	 * 休暇年月日が正しく入力でない場合、エラーメッセージを追加する。<br>
	 */
	protected void addHolidayOverlapRange1ErrorMessage() {
		String errorMes1 = mospParams.getName("Holiday", "Content");
		String errorMes2 = mospParams.getName("Holiday", "Year", "Month", "Day");
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_4, errorMes1, errorMes2);
	}
	
	/**
	 * 休暇年月日が正しく入力でない場合、エラーメッセージを追加する。<br>
	 */
	protected void addHolidayOverlapRange2ErrorMessage() {
		String errorMes1 = mospParams.getName("Holiday", "Content");
		String errorMes2 = mospParams.getName("Holiday", "Year", "Month", "Day", "Or", "Holiday", "Range");
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_4, errorMes1, errorMes2);
	}
	
	// 休日出勤関連
	/**
	 * 休日出勤申請する際に対象日が所定休日または法定休日で申請できない場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addWorkOnHolidayTargetWorkDateHolidayErrorMessage(Date date) {
		String[] aryRep = { DateUtility.getStringDate(date), mospParams.getName("DayOff"),
			mospParams.getName("GoingWork", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
	}
	
	// 代休申請関連
	/**
	 * 代休申請する際に対象日に既に代休申請が行われている場合、エラーメッセージを追加する。<br>
	 */
	protected void addSubHolidayTargetDateSubHolidayRequestErrorMessage() {
		String errorMes1 = mospParams.getName("Holiday", "Content");
		String errorMes2 = mospParams.getName("CompensatoryHoliday", "Day", "Or", "Holiday", "Range");
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_4, errorMes1, errorMes2);
	}
	
	/**
	 * 代休申請する際に対象日に既に勤怠の申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addSubHolidayTargetWorkDateAttendanceRequestErrorMessage(Date date) {
		String[] aryRep = { getStringDate(date), mospParams.getName("WorkManage"),
			mospParams.getName("CompensatoryHoliday", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, aryRep);
	}
	
	/**
	 * 代休申請する際に対象日が所定休日または法定休日で申請できない場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addSubHolidayTargetWorkDateHolidayErrorMessage(Date date) {
		String[] aryRep = { DateUtility.getStringDate(date), mospParams.getName("GoingWork", "Day"),
			mospParams.getName("CompensatoryHoliday", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
	}
	
	// 時差出勤関連
	/**
	 * 時差出勤申請する際に対象日が所定休日または法定休日で申請できない場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addDifferenceTargetWorkDateHolidayErrorMessage(Date date) {
		String[] aryRep = { DateUtility.getStringDate(date), mospParams.getName("GoingWork", "Day"),
			mospParams.getName("TimeDifference", "GoingWork", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
	}
	
	/**
	 * 時差出勤申請する際に対象日に既に勤怠の申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addDifferenceTargetWorkDateAttendanceRequestErrorMessage(Date date) {
		String[] aryRep = { getStringDate(date), mospParams.getName("WorkManage"),
			mospParams.getName("TimeDifference", "GoingWork", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, aryRep);
	}
	
	/**
	 * 時差出勤申請する際に対象日に既に他の申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addDifferenceTargetDateRequestErrorMessage(Date date) {
		String[] aryRep = { getStringDate(date), mospParams.getName("TimeDifference", "GoingWork", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, aryRep);
	}
	
	/**
	 * 時差出勤申請可能な期間を過ぎた年月日で申請が行われている場合、エラーメッセージを追加する。<br>
	 */
	protected void addDifferencePeriodErrorMessage() {
		String[] aryRep = { mospParams.getName("TimeDifference", "GoingWork", "Application"),
			mospParams.getName("No1", "Months"), mospParams.getName("TimeDifference", "GoingWork", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_2, aryRep);
	}
	
	/**
	 * 時差出勤申請する際に対象日に既に時差出勤申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 */
	protected void addDifferenceTargetDateDifferenceRequestErrorMessage(Date date) {
		String[] aryRep = { DateUtility.getStringDate(date), mospParams.getName("TimeDifference", "GoingWork"),
			mospParams.getName("TimeDifference", "GoingWork", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, aryRep);
	}
	
	/**
	 * 申請対象日に既に別の申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日付
	 * @param requestTitle 対象申請名称
	 */
	protected void addOthersRequestErrorMessage(Date date, String requestTitle) {
		String[] aryRep = { getStringDate(date), requestTitle };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_12, aryRep);
	}
	
	/**
	 * 勤怠申請可能な期間を過ぎた年月日で申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param limit 期限
	 */
	protected void addAttendancePeriodErrorMessage(String limit) {
		String[] aryRep = { mospParams.getName("WorkManage", "Application"), limit, mospParams.getName("Work", "Day") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_2, aryRep);
	}
	
	/**
	 * 月次処理が行われている場合、エラーメッセージを追加する。<br>
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @param name 項目名
	 */
	protected void addMonthlyTreatmentErrorMessage(int targetYear, int targetMonth, String name) {
		String mes1 = targetYear + mospParams.getName("Year") + targetMonth + mospParams.getName("Month");
		mospParams.addErrorMessage(TimeMessageConst.MSG_MONTHLY_TREATMENT, mes1, name);
	}
	
	/**
	 * 勤怠管理対象では無いユーザ対するエラーメッセージを追加する。<br>
	 * @param targetDate 対象年月日
	 * @param code 社員コード
	 */
	protected void addNotUserAttendanceManagementTargetErrorMessage(Date targetDate, String code) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_USER_ATTENDANCE_MANAGEMENT_TARGET,
				DateUtility.getStringDate(targetDate), code);
	}
	
	/**
	 * 勤怠基本設定情報を確認する。<br>
	 * 設定適用・勤怠設定・勤怠対象者・カレンダ・有給休暇管理を確認する。<br>
	 * 情報がない場合はエラーメッセージを返す。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param functionCode 機能コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void initial(String personalId, Date targetDate, String functionCode) throws MospException {
		// 参照クラス取得
		ApplicationReferenceBeanInterface applicationReference = (ApplicationReferenceBeanInterface)createBean(
				ApplicationReferenceBeanInterface.class);
		TimeSettingReferenceBeanInterface timeSettingReference = (TimeSettingReferenceBeanInterface)createBean(
				TimeSettingReferenceBeanInterface.class);
		ScheduleReferenceBeanInterface scheduleReference = (ScheduleReferenceBeanInterface)createBean(
				ScheduleReferenceBeanInterface.class);
		PaidHolidayReferenceBeanInterface paidHolidayReference = (PaidHolidayReferenceBeanInterface)createBean(
				PaidHolidayReferenceBeanInterface.class);
		// 設定適用情報取得
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, targetDate);
		applicationReference.chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠設定情報取得
		TimeSettingDtoInterface timeSettingDto = timeSettingReference
			.getTimeSettingInfo(applicationDto.getWorkSettingCode(), targetDate);
		timeSettingReference.chkExistTimeSetting(timeSettingDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠管理対象フラグのチェック
		if (timeSettingDto.getTimeManagementFlag() != MospConst.INACTIVATE_FLAG_OFF) {
			// 人事情報取得
			HumanDtoInterface dto = getHumanInfo(personalId, targetDate);
			// 無効(休暇申請可)かつ休暇または、振出/休出または、勤務形態変更申請の場合
			if (timeSettingDto.getTimeManagementFlag() == 2 && (functionCode.equals(TimeConst.CODE_FUNCTION_VACATION)
					|| functionCode.equals(TimeConst.CODE_FUNCTION_WORK_HOLIDAY)
					|| functionCode.equals(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE))) {
				return;
			}
			// エラーメッセージ追加
			addNotUserAttendanceManagementTargetErrorMessage(targetDate, dto.getEmployeeCode());
			return;
		}
		// カレンダコードが接頭辞(追加処理を示すカレンダコード)で始まらない場合
		if (applicationDto.getScheduleCode()
			.startsWith(ScheduleUtilBeanInterface.CODE_PREFIX_ADDON_SCHEDULE) == false) {
			// カレンダ情報取得
			ScheduleDtoInterface scheduleDto = scheduleReference.getScheduleInfo(applicationDto.getScheduleCode(),
					targetDate);
			scheduleReference.chkExistSchedule(scheduleDto, targetDate);
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
		// 有給休暇管理情報取得
		PaidHolidayDtoInterface paidHolidayDto = paidHolidayReference
			.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), targetDate);
		paidHolidayReference.chkExistPaidHoliday(paidHolidayDto, targetDate);
	}
	
	/**
	 * 丸めた時間を取得する。<br>
	 * @param time 対象時間
	 * @param type 丸め種別
	 * @param unit 丸め単位
	 * @return 丸めた時間
	 */
	protected int getRoundMinute(int time, int type, int unit) {
		return TimeUtility.getRoundMinute(time, type, unit);
	}
	
	/**
	 * 時間帯重複チェック。<br>
	 * 申請時間帯が対象時間帯に重複していないか確認する。<br>
	 * @param requestStartTime 申請済開始時刻
	 * @param requestEndTime 申請済終了時刻
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 確認結果(true：重複している、false：重複していない)
	 */
	protected boolean checkDuplicationTimeZone(Date requestStartTime, Date requestEndTime, Date startTime,
			Date endTime) {
		// 時刻が同じ場合
		if (startTime.equals(requestStartTime) || endTime.equals(requestEndTime)) {
			return true;
		}
		// 開始時刻が申請済開始時間の前、終了時刻が申請開始時間より後の場合
		if (startTime.before(requestStartTime) && endTime.after(requestStartTime)) {
			return true;
		}
		// 開始時刻が申請済開始時間の後で開始時刻が申請済終了時刻より前の場合
		if (startTime.after(requestStartTime) && startTime.before(requestEndTime)) {
			return true;
		}
		return false;
	}
	
}
