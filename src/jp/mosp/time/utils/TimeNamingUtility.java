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
package jp.mosp.time.utils;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.input.vo.AttendanceListVo;
import jp.mosp.time.input.vo.DifferenceRequestVo;
import jp.mosp.time.input.vo.HolidayRequestVo;
import jp.mosp.time.input.vo.OvertimeRequestVo;
import jp.mosp.time.input.vo.SubHolidayRequestVo;
import jp.mosp.time.input.vo.WorkTypeChangeRequestVo;

/**
 * 名称に関するユーティリティクラス。<br>
 * <br>
 * 勤怠管理システムにおいて作成される名称は、
 * 全てこのクラスを通じて作成される。<br>
 * <br>
 */
public class TimeNamingUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeNamingUtility() {
		// 処理無し
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤怠一覧画面
	 */
	public static String attendanceListScreen(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName(AttendanceListVo.class.getName()));
		sb.append(PfNameUtility.screen(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 残業申請画面
	 */
	public static String overtimeRequestScreen(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName(OvertimeRequestVo.class.getName()));
		sb.append(PfNameUtility.screen(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤怠承認一覧
	 */
	public static String attendanceApprovalList(MospParams mospParams) {
		return mospParams.getName("AttendanceApprovalListVo");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 日数
	 */
	public static String days(MospParams mospParams) {
		return mospParams.getName("Days");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休日
	 */
	public static String dayOff(MospParams mospParams) {
		return mospParams.getName("DayOff");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所定休日
	 */
	public static String prescribedHoliday(MospParams mospParams) {
		return mospParams.getName("PrescribedHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所休日
	 */
	public static String prescribedHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("PrescribedAbbreviation", "DayOff");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所定
	 */
	public static String prescribed(MospParams mospParams) {
		return mospParams.getName("Prescribed");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法定休日
	 */
	public static String legalHoliday(MospParams mospParams) {
		return mospParams.getName("LegalHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法休日
	 */
	public static String legalHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("LegalAbbreviation", "DayOff");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法定
	 */
	public static String legal(MospParams mospParams) {
		return mospParams.getName("Legal");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法定振替休日
	 */
	public static String legalTransferHoliday(MospParams mospParams) {
		return mospParams.getName("LegalTransferHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所定振替休日
	 */
	public static String prescribedTransferHoliday(MospParams mospParams) {
		return mospParams.getName("PrescribedTransferHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 表示年度
	 */
	public static String displayFiscalYear(MospParams mospParams) {
		return mospParams.getName("Display", "FiscalYear");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 限度
	 */
	public static String limit(MospParams mospParams) {
		return mospParams.getName("Limit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 注意
	 */
	public static String caution(MospParams mospParams) {
		return mospParams.getName("Caution");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 警告
	 */
	public static String warning(MospParams mospParams) {
		return mospParams.getName("Warning");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間外
	 */
	public static String outOfTime(MospParams mospParams) {
		return mospParams.getName("OutOfTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 外残
	 */
	public static String overtimeOutAbbr(MospParams mospParams) {
		return mospParams.getName("LeftOut");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤怠設定
	 */
	public static String timeSetting(MospParams mospParams) {
		return mospParams.getName("WorkManage", "Set");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤怠設定コード
	 */
	public static String timeSettingCode(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(timeSetting(mospParams));
		sb.append(PfNameUtility.code(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return カレンダ
	 */
	public static String calendar(MospParams mospParams) {
		return mospParams.getName("Calendar");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return カレンダデータ
	 */
	public static String calendarData(MospParams mospParams) {
		return mospParams.getName("Calendar", "Data");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 週の起算曜日
	 */
	public static String startDayOfTheWeek(MospParams mospParams) {
		return mospParams.getName("StartDayOfTheWeek");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 曜日
	 */
	public static String dayOfTheWeek(MospParams mospParams) {
		return mospParams.getName("DayOfTheWeek");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1週間限度時間
	 */
	public static String oneWeekLimitTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(oneWeek(mospParams));
		sb.append(limit(mospParams));
		sb.append(PfNameUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1ヶ月限度時間
	 */
	public static String oneMonthLimitTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(oneMonth(mospParams));
		sb.append(limit(mospParams));
		sb.append(PfNameUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1ヶ月注意時間
	 */
	public static String oneMonthAttentionTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(oneMonth(mospParams));
		sb.append(caution(mospParams));
		sb.append(PfNameUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1ヶ月警告時間
	 */
	public static String oneMonthWarningTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(oneMonth(mospParams));
		sb.append(warning(mospParams));
		sb.append(PfNameUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1週間
	 */
	public static String oneWeek(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(mospParams.getName("No1"));
		sb.append(PfNameUtility.amountWeek(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 1ヶ月
	 */
	public static String oneMonth(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(mospParams.getName("No1"));
		sb.append(PfNameUtility.amountMonth(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請可能時間
	 */
	public static String applicableTime(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("Application", "Possible"));
		sb.append(PfNameUtility.time(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振休取得期限
	 */
	public static String substituteHolidayLimit(MospParams mospParams) {
		return mospParams.getName("ClosedVibration", "Acquisition", "TimeLimit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振休取得期限(休出後)
	 */
	public static String substituteHolidayLaterLimit(MospParams mospParams) {
		StringBuilder later = new StringBuilder();
		later.append(workOnHolidayNotSubstituteAbbr(mospParams));
		later.append(mospParams.getName("Later"));
		StringBuilder sb = new StringBuilder();
		sb.append(substituteHolidayLimit(mospParams));
		sb.append(PfNameUtility.parentheses(mospParams, later.toString()));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 月末指定
	 */
	public static String specifyEndOfMonth(MospParams mospParams) {
		return mospParams.getName("Month", "TheEnd", "Given");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇
	 */
	public static String holiday(MospParams mospParams) {
		return mospParams.getName("Holiday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇種別
	 */
	public static String holidayType(MospParams mospParams) {
		return mospParams.getName("Holiday", "Classification");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇理由
	 */
	public static String holidayReason(MospParams mospParams) {
		return mospParams.getName("Holiday", "Reason");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇年月日
	 */
	public static String holidayDate(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(getVacation(mospParams));
		sb.append(PfNameUtility.yearMonthDay(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 仮締
	 */
	public static String tempTighten(MospParams mospParams) {
		return mospParams.getName("Provisional", "Cutoff");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 確定
	 */
	public static String fixed(MospParams mospParams) {
		return mospParams.getName("Definition");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 締日
	 */
	public static String cutoffDate(MospParams mospParams) {
		return mospParams.getName("CutoffDate");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 24時より前の時間
	 */
	public static String timeBeforeTwentyFour(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(TimeConst.TIME_DAY_ALL_HOUR);
		sb.append(mospParams.getName("Hour", "From", "Ahead", "Of", "Time"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 同
	 */
	public static String same(MospParams mospParams) {
		return mospParams.getName("Same");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return に
	 */
	public static String in(MospParams mospParams) {
		return mospParams.getName("In");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return と
	 */
	public static String and(MospParams mospParams) {
		return mospParams.getName("And");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return または
	 */
	public static String or(MospParams mospParams) {
		return mospParams.getName("Or");
	}
	
	/**
	 * 遅名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 遅
	 */
	public static String getLateAbbrNaming(MospParams mospParams) {
		return mospParams.getName("LateAbbr");
	}
	
	/**
	 * 早名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 早
	 */
	public static String getEarlyAbbrNaming(MospParams mospParams) {
		return mospParams.getName("EarlyAbbr");
	}
	
	/**
	 * 未申請名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 未申請
	 */
	public static String getNotApplied(MospParams mospParams) {
		return mospParams.getName("Ram", "Application");
	}
	
	/**
	 * 未承認名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 未承認
	 */
	public static String getNotApproved(MospParams mospParams) {
		return mospParams.getName("Ram", "Approval");
	}
	
	/**
	 * 1次戻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 1次戻
	 */
	public static String getFirstReverted(MospParams mospParams) {
		return mospParams.getName("No1", "Following", "Back");
	}
	
	/**
	 * 勤怠名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 勤怠
	 */
	public static String getWorkManage(MospParams mospParams) {
		return mospParams.getName("WorkManage");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤怠申請
	 */
	public static String attendanceRequest(MospParams mospParams) {
		return mospParams.getName("WorkManage", "Application");
	}
	
	/**
	 * 残業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 残業
	 */
	public static String getOvertimeWork(MospParams mospParams) {
		return mospParams.getName("OvertimeWork");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 残
	 */
	public static String overtimeAbbr(MospParams mospParams) {
		return mospParams.getName("OvertimeAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 残業申請
	 */
	public static String overtimeRequest(MospParams mospParams) {
		return mospParams.getName(OvertimeRequestVo.class.getName());
	}
	
	/**
	 * 休暇名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休暇
	 */
	public static String getVacation(MospParams mospParams) {
		return mospParams.getName("Holiday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇申請
	 */
	public static String holidayRequest(MospParams mospParams) {
		return mospParams.getName(HolidayRequestVo.class.getName());
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休日出勤
	 */
	public static String workOnHolidayNotSubstitute(MospParams mospParams) {
		return mospParams.getName("DayOff", "GoingWork");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休出
	 */
	public static String workOnHolidayNotSubstituteAbbr(MospParams mospParams) {
		return mospParams.getName("WorkingHoliday");
	}
	
	/**
	 * 振出休出名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 振出休出
	 */
	public static String getWorkOnHoliday(MospParams mospParams) {
		return mospParams.getName("SubstituteWorkAbbr", "WorkingHoliday");
	}
	
	/**
	 * 振出・休出名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 振出・休出
	 */
	public static String workOnHoliday(MospParams mospParams) {
		return mospParams.getName("SubstituteWorkAbbr", "MiddlePoint", "WorkingHoliday");
	}
	
	/**
	 * 半日振替名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 半日振替
	 */
	public static String getHalfSubstitute(MospParams mospParams) {
		return mospParams.getName("HalfDay", "Transfer");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振替日
	 */
	public static String substituteDay(MospParams mospParams) {
		return mospParams.getName("Transfer", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振替休日
	 */
	public static String substituteHoliday(MospParams mospParams) {
		return mospParams.getName("Transfer", "DayOff");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振替休日範囲
	 */
	public static String substituteHolidayRange(MospParams mospParams) {
		return mospParams.getName("Transfer", "DayOff", "Range");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振替出勤
	 */
	public static String substituteWork(MospParams mospParams) {
		return mospParams.getName("Transfer", "GoingWork");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振替出勤申請
	 */
	public static String substituteWorkAppli(MospParams mospParams) {
		return mospParams.getName("Transfer", "GoingWork", "Application");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 半振休
	 */
	public static String halfSubstituteHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("HalfSubstituteHolidayAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振△
	 */
	public static String anteSubstituteHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("AnteMeridiemSubstituteHolidayAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return △振
	 */
	public static String postSubstituteHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("PostMeridiemSubstituteHolidayAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 半振出
	 */
	public static String halfSubstituteWorkAbbr(MospParams mospParams) {
		return mospParams.getName("HalfSubstituteWorkAbbr");
	}
	
	/**
	 * 代休名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 代休
	 */
	public static String getSubHoliday(MospParams mospParams) {
		return mospParams.getName("CompensatoryHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代
	 */
	public static String subHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("Generation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代休申請
	 */
	public static String subHolidayRequest(MospParams mospParams) {
		return mospParams.getName(SubHolidayRequestVo.class.getName());
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代休日
	 */
	public static String subHolidayDay(MospParams mospParams) {
		return mospParams.getName("CompensatoryHoliday", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所定代休
	 */
	public static String prescribedSubHoliday(MospParams mospParams) {
		return mospParams.getName("Prescribed", "CompensatoryHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所代休
	 */
	public static String prescribedSubHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("PrescribedAbbreviation", "CompensatoryHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法定代休
	 */
	public static String legalSubHoliday(MospParams mospParams) {
		return mospParams.getName("Legal", "CompensatoryHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法代休
	 */
	public static String legalSubHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("LegalAbbreviation", "CompensatoryHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 深夜
	 */
	public static String midnight(MospParams mospParams) {
		return mospParams.getName("Midnight");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 深夜代休
	 */
	public static String midnightSubHoliday(MospParams mospParams) {
		return mospParams.getName("Midnight", "CompensatoryHoliday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務形態
	 */
	public static String workType(MospParams mospParams) {
		return mospParams.getName("Work", "Form");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務形態コード
	 */
	public static String workTypeCode(MospParams mospParams) {
		return mospParams.getName("Work", "Form", "Code");
	}
	
	/**
	 * 時差名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 時差
	 */
	public static String getTimeDifference(MospParams mospParams) {
		return mospParams.getName("TimeDifference");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時
	 */
	public static String timeDifferenceAbbr(MospParams mospParams) {
		return mospParams.getName("TimeDefferenceAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時差出勤申請
	 */
	public static String differenceRequest(MospParams mospParams) {
		return mospParams.getName(DifferenceRequestVo.class.getName());
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤形変更
	 */
	public static String workTypeChangeShort(MospParams mospParams) {
		return mospParams.getName("WorkTypeAbbr", "Change");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務形態変更申請
	 */
	public static String workTypeChangeRequest(MospParams mospParams) {
		return mospParams.getName(WorkTypeChangeRequestVo.class.getName());
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 始業
	 */
	public static String startWork(MospParams mospParams) {
		return mospParams.getName("StartWork");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 始業時刻
	 */
	public static String startWorkTime(MospParams mospParams) {
		return mospParams.getName("StartWork", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 終業
	 */
	public static String endWork(MospParams mospParams) {
		return mospParams.getName("EndWork");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 終業時刻
	 */
	public static String endWorkTime(MospParams mospParams) {
		return mospParams.getName("EndWork", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 終業予定時刻
	 */
	public static String scheduledEndWorkTime(MospParams mospParams) {
		return mospParams.getName("EndWork", "Schedule", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務時間
	 */
	public static String workTime(MospParams mospParams) {
		return mospParams.getName("Work", "Time");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休憩
	 */
	public static String rest(MospParams mospParams) {
		return mospParams.getName("RestTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param idx        インデックス
	 * @return 休憩1～4
	 */
	public static String rest(MospParams mospParams, int idx) {
		return new StringBuilder(rest(mospParams)).append(idx).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休憩入り
	 */
	public static String startRest(MospParams mospParams) {
		return mospParams.getName("RestTime", "Into");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休憩戻り
	 */
	public static String endRest(MospParams mospParams) {
		return mospParams.getName("RestTime", "Return");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休憩時間
	 */
	public static String restTime(MospParams mospParams) {
		return mospParams.getName("RestTime", "Time");
	}
	
	/**
	 * 公用外出名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 公用外出
	 */
	public static String getPublicGoOut(MospParams mospParams) {
		return mospParams.getName("PublicGoingOut");
	}
	
	/**
	 * 私用外出名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 私用外出
	 */
	public static String getPrivateGoOut(MospParams mospParams) {
		return mospParams.getName("PrivateGoingOut");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 私用外出入り
	 */
	public static String startPrivateGoOut(MospParams mospParams) {
		return mospParams.getName("PrivateGoingOut", "Into");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 私用外出戻り
	 */
	public static String endPrivateGoOut(MospParams mospParams) {
		return mospParams.getName("PrivateGoingOut", "Return");
	}
	
	/**
	 * 残前休憩名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 残前休憩
	 */
	public static String overtimeBeforeRest(MospParams mospParams) {
		return mospParams.getName("RestBeforeOvertimeWork");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 残業休憩
	 */
	public static String overtimeRest(MospParams mospParams) {
		return mospParams.getName("RestInOvertime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 出勤予定時刻
	 */
	public static String scheduledStartWork(MospParams mospParams) {
		return mospParams.getName("GoingWork", "Schedule", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 退勤予定時刻
	 */
	public static String scheduledEndWork(MospParams mospParams) {
		return mospParams.getName("RetireOffice", "Schedule", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有給休暇
	 */
	public static String paidHoliday(MospParams mospParams) {
		return mospParams.getName("PaidVacation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有休
	 */
	public static String paidHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("PaidHolidayAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有休コード
	 */
	public static String paidHolidayCodeAbbr(MospParams mospParams) {
		return mospParams.getName("PaidHolidayAbbr", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有休名称
	 */
	public static String paidHolidayNameAbbr(MospParams mospParams) {
		return mospParams.getName("PaidHolidayAbbr", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有休略称
	 */
	public static String paidHolidayAbbreviationAbbr(MospParams mospParams) {
		return mospParams.getName("PaidHolidayAbbr", "Abbreviation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有休繰越
	 */
	public static String paidHolidayBroughtForwardAbbr(MospParams mospParams) {
		return mospParams.getName("PaidHolidayAbbr", "BroughtForward");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有休繰越
	 */
	public static String paidHolidaySettingListAbbr(MospParams mospParams) {
		return mospParams.getName("PaidHolidayAbbr", "Set", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ストック休暇
	 */
	public static String stockHoliday(MospParams mospParams) {
		return mospParams.getName("Stock", "Holiday");
	}
	
	/**
	 * ストック休暇名称(略称)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return スト休
	 */
	public static String getStockHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("StockHolidayAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 特別休暇
	 */
	public static String specialHoliday(MospParams mospParams) {
		return mospParams.getName("Specially", "Holiday");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 特休
	 */
	public static String specialHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("SpecialLeave");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return その他休暇
	 */
	public static String otherHoliday(MospParams mospParams) {
		return mospParams.getName("Others", "Holiday");
	}
	
	/**
	 * 所定労働時間名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 所定労働時間
	 */
	public static String getPrescribedWorkTime(MospParams mospParams) {
		return mospParams.getName("Prescribed", "Labor", "Time");
	}
	
	/**
	 * 設定適用名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 設定適用
	 */
	public static String getApplication(MospParams mospParams) {
		return mospParams.getName("Set", "Apply");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有給休暇設定
	 */
	public static String paidHolidaySetting(MospParams mospParams) {
		return mospParams.getName("PaidVacation", "Set");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有休設定
	 */
	public static String paidHolidaySettingAbbr(MospParams mospParams) {
		return mospParams.getName("PaidHolidayAbbr", "Set");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間単位取得
	 */
	public static String hourlyPaidHolidayFlag(MospParams mospParams) {
		return mospParams.getName("Time", "Unit", "Acquisition");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有休取得状況出力
	 */
	public static String paidHolidayUsageExport(MospParams mospParams) {
		return mospParams.getName("PaidHolidayUsage", "Output");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 取得日
	 */
	public static String acquisitionDate(MospParams mospParams) {
		return mospParams.getName("Acquisition", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 達成
	 */
	public static String accomplish(MospParams mospParams) {
		return mospParams.getName("Accomplish");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 付与
	 */
	public static String giving(MospParams mospParams) {
		return mospParams.getName("Giving");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 付与日数
	 */
	public static String givingDays(MospParams mospParams) {
		return mospParams.getName("Giving", "Days");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 付与日
	 */
	public static String givingDay(MospParams mospParams) {
		return mospParams.getName("Giving", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請日数
	 */
	public static String appliedDays(MospParams mospParams) {
		return mospParams.getName("Application", "Days");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請日
	 */
	public static String applicationDate(MospParams mospParams) {
		return mospParams.getName("Application", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請開始日
	 */
	public static String requestStartDate(MospParams mospParams) {
		return mospParams.getName("Application", "Start", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請終了日
	 */
	public static String requestEndDate(MospParams mospParams) {
		return mospParams.getName("Application", "End", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 未消化日数(合算)
	 */
	public static String paidHolidayUsageShortTotal(MospParams mospParams) {
		return mospParams.getName("PaidHolidayUsageShortTotal");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振
	 */
	public static String substituteAbbr(MospParams mospParams) {
		return mospParams.getName("SubstituteAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振出
	 */
	public static String substituteWorkAbbr(MospParams mospParams) {
		return mospParams.getName("SubstituteWorkAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 振休
	 */
	public static String substituteHolidayAbbr(MospParams mospParams) {
		return mospParams.getName("ClosedVibration");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所振休
	 */
	public static String prescribedSubstituteAbbr(MospParams mospParams) {
		return mospParams.getName("PrescribedAbbreviation", "ClosedVibration");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 法振休
	 */
	public static String legalSubstituteAbbr(MospParams mospParams) {
		return mospParams.getName("LegalAbbreviation", "ClosedVibration");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休暇範囲
	 */
	public static String holidayRange(MospParams mospParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(getVacation(mospParams));
		sb.append(PfNameUtility.range(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 全休
	 */
	public static String holidayRangeAll(MospParams mospParams) {
		return mospParams.getName("AllTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return (全休)
	 */
	public static String holidayRangeAllWithParentheses(MospParams mospParams) {
		return PfNameUtility.parentheses(mospParams, holidayRangeAll(mospParams));
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 【全休】
	 */
	public static String holidayRangeAllWithCornerParentheses(MospParams mospParams) {
		return NameUtility.cornerParentheses(mospParams, holidayRangeAll(mospParams));
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 半休
	 */
	public static String holidayHalf(MospParams mospParams) {
		return mospParams.getName("HalfTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return (半休)
	 */
	public static String holidayHalfWithParentheses(MospParams mospParams) {
		return PfNameUtility.parentheses(mospParams, holidayHalf(mospParams));
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 【半休】
	 */
	public static String holidayHalfWithCornerParentheses(MospParams mospParams) {
		return NameUtility.cornerParentheses(mospParams, holidayHalf(mospParams));
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 前休
	 */
	public static String holidayRangeFrontAbbr(MospParams mospParams) {
		return mospParams.getName("FrontTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 後休
	 */
	public static String holidayRangeBackAbbr(MospParams mospParams) {
		return mospParams.getName("BackTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間休
	 */
	public static String hourlyHoliday(MospParams mospParams) {
		return mospParams.getName("HolidayTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時休
	 */
	public static String holidayRangeHourAbbr(MospParams mospParams) {
		return mospParams.getName("HourTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return (時休)
	 */
	public static String hourlyHolidayAbbrWithParentheses(MospParams mospParams) {
		return PfNameUtility.parentheses(mospParams, holidayRangeHourAbbr(mospParams));
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return (分数)
	 */
	public static String minutesWithParentheses(MospParams mospParams) {
		return PfNameUtility.parentheses(mospParams, mospParams.getName("Minutes", "Num"));
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間休の申請が
	 */
	public static String hourlyHolidayRequestForMessage(MospParams mospParams) {
		return mospParams.getName("HolidayTime", "Of", "Application", "The");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間休申請時間
	 */
	public static String hourlyHolidayRequestTime(MospParams mospParams) {
		return mospParams.getName("HolidayTime", "Application", "Time");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 変
	 */
	public static String workTypeChangeAbbr(MospParams mospParams) {
		return mospParams.getName("WorkTypeChangeAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 午前休
	 */
	public static String amRest(MospParams mospParams) {
		return mospParams.getName("AmRest");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 午前休開始時刻
	 */
	public static String amRestStartTime(MospParams mospParams) {
		return mospParams.getName("AmRest", "Start", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 午前休終了時刻
	 */
	public static String amRestEndTime(MospParams mospParams) {
		return mospParams.getName("AmRest", "End", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 午後休
	 */
	public static String pmRest(MospParams mospParams) {
		return mospParams.getName("PmRest");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 午前休開始時刻
	 */
	public static String pmRestStartTime(MospParams mospParams) {
		return mospParams.getName("PmRest", "Start", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 午前休終了時刻
	 */
	public static String pmRestEndTime(MospParams mospParams) {
		return mospParams.getName("PmRest", "End", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 半休取得時休憩
	 */
	public static String halfHolidayRest(MospParams mospParams) {
		return mospParams.getName("HalfTime", "Acquisition", "Hour", "RestTime");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 半休取得時休憩開始時刻
	 */
	public static String halfHolidayRestStartTime(MospParams mospParams) {
		return mospParams.getName("HalfTime", "Acquisition", "Hour", "RestTime", "Start", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 半休取得時休憩終了時刻
	 */
	public static String halfHolidayRestEndTime(MospParams mospParams) {
		return mospParams.getName("HalfTime", "Acquisition", "Hour", "RestTime", "End", "Moment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務前残業実績登録
	 */
	public static String registActualOvertimeBeforeWork(MospParams mospParams) {
		return mospParams.getName("Work", "Ahead", "OvertimeWork", "Performance", "Insert");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 実
	 */
	public static String factAbbr(MospParams mospParams) {
		return mospParams.getName("Fact");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 年間
	 */
	public static String forYear(MospParams mospParams) {
		return mospParams.getName("Years");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 当月
	 */
	public static String thisMonth(MospParams mospParams) {
		return mospParams.getName("This", "Month");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 残業可能時間
	 */
	public static String availabileOvertime(MospParams mospParams) {
		return mospParams.getName("OvertimeWork", "Possible", "Time");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請時間
	 */
	public static String applicationTime(MospParams mospParams) {
		return mospParams.getName("Application", "Time");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請理由
	 */
	public static String applicationReason(MospParams mospParams) {
		return mospParams.getName("Application", "Reason");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 残業理由
	 */
	public static String overtimeReason(MospParams mospParams) {
		return mospParams.getName("OvertimeWork", "Reason");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 管理
	 */
	public static String management(MospParams mospParams) {
		return mospParams.getName("Management");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 午前
	 */
	public static String anteMeridiem(MospParams mospParams) {
		return mospParams.getName("AnteMeridiem");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return (午前)
	 */
	public static String anteMeridiemWithParentheses(MospParams mospParams) {
		return PfNameUtility.parentheses(mospParams, mospParams.getName("AnteMeridiem"));
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 午後
	 */
	public static String postMeridiem(MospParams mospParams) {
		return mospParams.getName("PostMeridiem");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return (午後)
	 */
	public static String postMeridiemWithParentheses(MospParams mospParams) {
		return PfNameUtility.parentheses(mospParams, mospParams.getName("PostMeridiem"));
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認解除申請
	 */
	public static String cancellationRequest(MospParams mospParams) {
		return mospParams.getName("Approval", "Release", "Application");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 直行
	 */
	public static String directStart(MospParams mospParams) {
		return mospParams.getName("DirectStart");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 直帰
	 */
	public static String directEnd(MospParams mospParams) {
		return mospParams.getName("DirectEnd");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 修正
	 */
	public static String correction(MospParams mospParams) {
		return mospParams.getName("Correction");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 修正前
	 */
	public static String beforeCorrection(MospParams mospParams) {
		return mospParams.getName("Correction", "Ahead");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 本
	 */
	public static String selfCorrectAbbr(MospParams mospParams) {
		return mospParams.getName("SelfCorrectionAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 他
	 */
	public static String otherCorrectAbbr(MospParams mospParams) {
		return mospParams.getName("Other");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 初年度
	 */
	public static String firstFiscalYear(MospParams mospParams) {
		return mospParams.getName("FirstYear");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 前年度
	 */
	public static String previousFiscalYear(MospParams mospParams) {
		return mospParams.getName("PreviousYear", "Times");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 今年度
	 */
	public static String thisFiscalYear(MospParams mospParams) {
		return mospParams.getName("ThisYear", "Times");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 現在の残日数合計
	 */
	public static String currentTotalRemainDays(MospParams mospParams) {
		return mospParams.getName("PresentTime", "Of", "Remainder", "Days", "SumTotal");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時差A
	 */
	public static String differenceA(MospParams mospParams) {
		return mospParams.getName("TimeDifference", "CharaA");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時差B
	 */
	public static String differenceB(MospParams mospParams) {
		return mospParams.getName("TimeDifference", "CharaB");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時差C
	 */
	public static String differenceC(MospParams mospParams) {
		return mospParams.getName("TimeDifference", "CharaC");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時差D
	 */
	public static String differenceD(MospParams mospParams) {
		return mospParams.getName("TimeDifference", "CharaD");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時差S
	 */
	public static String differenceS(MospParams mospParams) {
		return mospParams.getName("TimeDifference", "CharaS");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return A
	 */
	public static String charaA(MospParams mospParams) {
		return mospParams.getName("CharaA");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return B
	 */
	public static String charaB(MospParams mospParams) {
		return mospParams.getName("CharaB");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return C
	 */
	public static String charaC(MospParams mospParams) {
		return mospParams.getName("CharaC");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return D
	 */
	public static String charaD(MospParams mospParams) {
		return mospParams.getName("CharaD");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return S
	 */
	public static String charaS(MospParams mospParams) {
		return mospParams.getName("CharaS");
	}
	
}
