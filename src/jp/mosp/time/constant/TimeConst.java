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
package jp.mosp.time.constant;

/**
 * MosPプラットフォームで用いる定数を宣言する。<br>
 * <br>
 */
public final class TimeConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeConst() {
		// 処理無し
	}
	
	
	/**
	 * コードキー(締日)。
	 */
	public static final String	CODE_KEY_CUTOFF_DATE												= "CutoffDate";
	
	/**
	 * コードキー(未承認仮締)。
	 */
	public static final String	CODE_KEY_NO_APPROVAL												= "NoApproval";
	
	/**
	 * コードキー(付与区分)。
	 */
	public static final String	CODE_KEY_PAID_HOLIDAY_TYPE											= "PaidHolidayType";
	
	/**
	 * コードキー(有休単位時間)。
	 */
	public static final String	CODE_KEY_PAID_HOLIDAY_TIME											= "PaidHolidayTime";
	
	/**
	 * コードキー(時間限度日数/年)。
	 */
	public static final String	CODE_KEY_TTIME_ACQUISITION_LIMIT_DAYS								= "TimeAcquisitionLimitDays";
	
	/**
	 * コードキー(時間限度時間/日)。
	 */
	public static final String	CODE_KEY_TIME_ACQUISITION_LIMIT_TIMES								= "TimeAcquisitionLimitTimes";
	
	/**
	 * コードキー(申請時間間隔)
	 */
	public static final String	CODE_KEY_APPLI_TIME_INTERVAL										= "AppliTimeInterval";
	
	/**
	 * コードキー(最大繰越年数)。
	 */
	public static final String	CODE_KEY_MAX_CARRY_OVER_YEARS										= "MaxCarryOverYears";
	
	/**
	 * コードキー(時間単位繰越)。
	 */
	public static final String	CODE_KEY_MAX_CARRY_OVER_TIMES										= "MaxCarryOverTimes";
	
	/**
	 * コードキー(休暇マスタ区分)。
	 */
	public static final String	CODE_KEY_HOLIDAY_MASTER_TYPE										= "HolidayMasterType";
	
	/**
	 * コードキー(半休申請)。
	 */
	public static final String	CODE_KEY_HALF_HOLIDAY_REQUEST										= "HalfHolidayRequest";
	
	/**
	 * コードキー(連続取得)。
	 */
	public static final String	CODE_KEY_CONTINUE													= "Continue";
	
	/**
	 * コードキー(理由入力)。
	 */
	public static final String	CODE_KEY_REASON_TYPE												= "ReasonType";
	
	/**
	 * コードキー(給与区分)。
	 */
	public static final String	CODE_KEY_SALARY_PAY_TYPE											= "SalaryPayType";
	
	/**
	 * コードキー(付与)。
	 */
	public static final String	CODE_KEY_GRANT														= "Grant";
	
	/**
	 * コードキー(出勤時刻)。
	 */
	public static final String	CODE_WORKSTART														= "WorkStart";
	
	/**
	 * コードキー(退勤時刻)。
	 */
	public static final String	CODE_WORKEND														= "WorkEnd";
	
	/**
	 * コードキー(勤務時間)。
	 */
	public static final String	CODE_WORKTIME														= "WorkTime";
	
	/**
	 * コードキー(休憩時間)。
	 */
	public static final String	CODE_RESTTIME														= "RestTime";
	
	/**
	 * コードキー(休憩1(開始))。
	 */
	public static final String	CODE_RESTSTART1														= "RestStart1";
	
	/**
	 * コードキー(休憩1(終了))。
	 */
	public static final String	CODE_RESTEND1														= "RestEnd1";
	
	/**
	 * コードキー(休憩2(開始))。
	 */
	public static final String	CODE_RESTSTART2														= "RestStart2";
	
	/**
	 * コードキー(休憩2(終了))。
	 */
	public static final String	CODE_RESTEND2														= "RestEnd2";
	
	/**
	 * コードキー(休憩3(開始))。
	 */
	public static final String	CODE_RESTSTART3														= "RestStart3";
	
	/**
	 * コードキー(休憩3(終了))。
	 */
	public static final String	CODE_RESTEND3														= "RestEnd3";
	
	/**
	 * コードキー(休憩4(開始))。
	 */
	public static final String	CODE_RESTSTART4														= "RestStart4";
	
	/**
	 * コードキー(休憩4(終了))。
	 */
	public static final String	CODE_RESTEND4														= "RestEnd4";
	
	/**
	 * コードキー(前半休(開始))。
	 */
	public static final String	CODE_FRONTSTART														= "FrontStart";
	
	/**
	 * コードキー(前半休(終了))。
	 */
	public static final String	CODE_FRONTEND														= "FrontEnd";
	
	/**
	 * コードキー(後半休(開始))。
	 */
	public static final String	CODE_BACKSTART														= "BackStart";
	
	/**
	 * コードキー(後半休(終了))。
	 */
	public static final String	CODE_BACKEND														= "BackEnd";
	
	/**
	 * コードキー(残前休憩)。
	 */
	public static final String	CODE_OVERBEFORE														= "OverBefore";
	
	/**
	 * コードキー(残業休憩時間(毎))。
	 */
	public static final String	CODE_OVERPER														= "OverPer";
	
	/**
	 * コードキー(残業休憩時間)。
	 */
	public static final String	CODE_OVERREST														= "OverRest";
	
	/**
	 * コードキー(半休休憩対象時間)。
	 */
	public static final String	CODE_HALFREST														= "HalfRest";
	
	/**
	 * コードキー(半休休憩開始時刻)。
	 */
	public static final String	CODE_HALFRESTSTART													= "HalfRestStart";
	
	/**
	 * コードキー(半休休憩終了時刻)。
	 */
	public static final String	CODE_HALFRESTEND													= "HalfRestEnd";
	
	/**
	 * コードキー(勤務形態項目：直行)。
	 */
	public static final String	CODE_WORK_TYPE_ITEM_DIRECT_START									= "DirectStart";
	
	/**
	 * コードキー(勤務形態項目：直帰)。
	 */
	public static final String	CODE_WORK_TYPE_ITEM_DIRECT_END										= "DirectEnd";
	
	/**
	 * コードキー(勤務形態項目：割増休憩除外)。
	 */
	public static final String	CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST								= "ExcludeNightRest";
	
	/**
	 * コードキー(勤務形態項目：時短時間1開始時刻及び給与区分)。
	 */
	public static final String	CODE_WORK_TYPE_ITEM_SHORT1_START									= "Short1Start";
	
	/**
	 * コードキー(勤務形態項目：時短時間1終了時刻)。
	 */
	public static final String	CODE_WORK_TYPE_ITEM_SHORT1_END										= "Short1End";
	
	/**
	 * コードキー(勤務形態項目：時短時間2開始時刻及び給与区分)。
	 */
	public static final String	CODE_WORK_TYPE_ITEM_SHORT2_START									= "Short2Start";
	
	/**
	 * コードキー(勤務形態項目：時短時間2終了時刻)。
	 */
	public static final String	CODE_WORK_TYPE_ITEM_SHORT2_END										= "Short2End";
	
	/**
	 * コードキー(勤務形態項目：勤務前残業自動申請)。
	 */
	public static final String	CODE_AUTO_BEFORE_OVERWORK											= "AutoBefOverWork";
	
	/**
	 * コードキー(丸め)。
	 */
	public static final String	CODE_ROUNDING_ITEMS													= "RoundingItems";
	
	/**
	 * コードキー(曜日)。
	 */
	public static final String	CODE_KEY_DAY_OF_THE_WEEK											= "DayOfTheWeek";
	
	/**
	 * コードキー(所定休日取扱)。
	 */
	public static final String	CODE_KEY_SPECIFIC_HOLIDAY											= "SpecificHoliday";
	
	/**
	 * コードキー(勤怠管理対象)。
	 */
	public static final String	CODE_KEY_TIME_MANAGEMENT											= "TimeManagement";
	
	/**
	 * コードキー(ポータル出退勤ボタン表示)。
	 */
	public static final String	CODE_KEY_PORTAL_TIME_BUTTONS										= "PortalTimeButtons";
	
	/**
	 * コードキー(ポータル休憩ボタン表示)。
	 */
	public static final String	CODE_KEY_PORTAL_REST_BUTTONS										= "PortalRestButtons";
	
	/**
	 * コードキー(出勤率計算)。
	 */
	public static final String	CODE_KEY_PAID_HOLIDAY_CALC											= "PaidHolidayCalc";
	
	/**
	 * コードキー(休日出勤出勤率計算)。
	 */
	public static final String	CODE_KEY_WORK_ON_HOLIDAY_CALC										= "WorkOnHolidayCalc";
	
	/**
	 * コードキー(半休入替取得)。
	 */
	public static final String	CODE_HALF_TIME_EXCHANGE												= "HalfTimeExchange";
	
	/**
	 * コードキー(所定/法定休日)。
	 */
	public static final String	CODE_PRESCRIBED_LEGAL_HOLIDAY										= "PrescribedLegalHoliday";
	
	/**
	 * コードキー(休暇区分)。
	 */
	public static final String	CODE_KEY_HOLIDAY_TYPE_MASTER										= "HolidayTypeMaster";
	
	/**
	 * 表示月コード(1月)。
	 */
	public static final String	CODE_DISPLAY_JANUARY												= "1";
	
	/**
	 * 表示月コード(2月)。
	 */
	public static final String	CODE_DISPLAY_FEBRUARY												= "2";
	
	/**
	 * 表示月コード(3月)。
	 */
	public static final String	CODE_DISPLAY_MARCH													= "3";
	
	/**
	 * 表示月コード(4月)。
	 */
	public static final String	CODE_DISPLAY_APRIL													= "4";
	
	/**
	 * 表示月コード(5月)。
	 */
	public static final String	CODE_DISPLAY_MAY													= "5";
	
	/**
	 * 表示月コード(6月)。
	 */
	public static final String	CODE_DISPLAY_JUNE													= "6";
	
	/**
	 * 表示月コード(7月)。
	 */
	public static final String	CODE_DISPLAY_JULY													= "7";
	
	/**
	 * 表示月コード(8月)。
	 */
	public static final String	CODE_DISPLAY_AUGUST													= "8";
	
	/**
	 * 表示月コード(9月)。
	 */
	public static final String	CODE_DISPLAY_SEPTEMBER												= "9";
	
	/**
	 * 表示月コード(10月)。
	 */
	public static final String	CODE_DISPLAY_OCTOBER												= "10";
	
	/**
	 * 表示月コード(11月)。
	 */
	public static final String	CODE_DISPLAY_NOVEMBER												= "11";
	
	/**
	 * 表示月コード(12月)。
	 */
	public static final String	CODE_DISPLAY_DECEMBER												= "12";
	
	/**
	 * コードキー(ワークフロー状態)。
	 */
	public static final String	CODE_APPROVAL_STATE													= "ApprovalState";
	
	/**
	 * コードキー(有効ワークフロー状態)。
	 */
	public static final String	CODE_EFFECTIVE_STATE												= "EffectiveState";
	
	/**
	 * コードキー(検索条件：承認状態)。
	 */
	public static final String	CODE_NOT_APPROVED													= "NotApproved";
	
	/**
	 * 検索条件：承認状態(未承認無)。
	 */
	public static final int		CODE_NOT_APPROVED_NONE												= 0;
	
	/**
	 * 検索条件：承認状態(未承認有)。
	 */
	public static final int		CODE_NOT_APPROVED_EXIST												= 1;
	
	/**
	 * 検索条件：承認状態(未申)。
	 */
	public static final int		CODE_NOT_TIME_APPROVED_EXIST										= 2;
	
	/**
	 * 検索条件：承認状態(未承認・未申請有)。
	 */
	public static final int		CODE_NOT_CUTOFF_INFO_EXIST											= 3;
	
	/**
	 * 検索条件：検索区分（承認すべき申請者）。
	 */
	public static final int		CODE_SUBORDINATE_SEARCH_TYPE_APPROVER								= 1;
	
	/**
	 * 検索条件：検索区分（部下）。
	 */
	public static final int		CODE_SUBORDINATE_SEARCH_TYPE_SUBORDINATE							= 2;
	
	/**
	 * 表示ステータスコード。
	 */
	public static final String	CODE_SCHEDULE_OVER													= "ScheduleOver";
	
	/**
	 * 表示ステータスコード。
	 */
	public static final String	CODE_OVER_TIME_TYPE													= "OverTimeType";
	
	/**
	 * 表示ステータス(申請範囲1)。
	 */
	public static final String	CODE_SUBSTITUTE1_RANGE												= "Substitute1Range";
	
	/**
	 * 表示ステータス(申請範囲2)。
	 */
	public static final String	CODE_SUBSTITUTE2_RANGE												= "Substitute2Range";
	
	/**
	 * 表示ステータス(時差出勤区分)。
	 */
	public static final String	CODE_DIFFERENCE_TYPE												= "DifferenceType";
	
	/**
	 * 表示ステータス（ヘッダー有無）
	 */
	public static final String	CODE_HEARDER														= "Header";
	
	/**
	 * 表示ステータス（休日出勤区分）
	 */
	public static final String	CODE_WORK_ON_HOLIDAY												= "WorkOnHoliday";
	
	/**
	 * 表示ステータス(時差出勤区分)。
	 */
	public static final String	CODE_START_DATE														= "StartDate";
	
	/**
	 * 表示ステータス(休暇申請区分)。
	 */
	public static final String	CODE_HOLIDAY_TYPE													= "HolidayType";
	
	/**
	 * 表示ステータス(休暇申請区分)。
	 */
	public static final String	CODE_HOLIDAY_TYPE3_RANGE1											= "HolidayType3Range1";
	
	/**
	 * 表示ステータス(休暇申請区分)。
	 */
	public static final String	CODE_HOLIDAY_TYPE3_RANGE2											= "HolidayType3Range2";
	
	/**
	 * 未承認仮締コード(有効)。<br>
	 */
	public static final int		CODE_NO_APPROVAL_VALID												= 0;
	
	/**
	 * 未承認仮締コード(無効(残業事後申請可))。<br>
	 */
	public static final int		CODE_NO_APPROVAL_AFTER_OVER_REQ										= 1;
	
	/**
	 * 未承認仮締コード(無効(残業事前申請のみ))。<br>
	 */
	public static final int		CODE_NO_APPROVAL_BEFORE_OVER_REQ									= 2;
	
	/**
	 * 未承認仮締コード(無効(残業申請なし))。<br>
	 */
	public static final int		CODE_NO_APPROVAL_NO_OVER_REQ										= 3;
	
	/**
	 * ラジオコード(曜日指定)。
	 */
	public static final String	CODE_RADIO_WEEK														= "week";
	
	/**
	 * ラジオコード(期間指定)。
	 */
	public static final String	CODE_RADIO_PERIOD													= "period";
	
	/**
	 * ラジオコード(日付指定)。
	 */
	public static final String	CODE_RADIO_CHECK													= "check";
	
	/**
	 * 年計算用コード。
	 */
	public static final int		CODE_DEFINITION_YEAR												= 12;
	
	/**
	 * 時間計算用コード。
	 */
	public static final int		CODE_DEFINITION_HOUR												= 60;
	
	/**
	 * 時間計算用コード。<br>
	 * 分をミリ秒に直した数値(1000 * 60)。<br>
	 */
	public static final int		CODE_DEFINITION_MINUTE_MILLI_SEC									= 60000;
	
	/**
	 * コードキー(承認カテゴリ)。
	 */
	public static final String	CODE_APPROVAL_TYPE													= "ApprovalType";
	
	/**
	 * コードキー(締状態)。
	 */
	public static final String	CODE_CUTOFFSTATE													= "CutoffState";
	
	/**
	 * コードキー(部下検索区分)。
	 */
	public static final String	CODE_SUBORDINATE_SEARCH_TYPE										= "SubordinateSearchType";
	
	/**
	 * コードキー(勤怠情報データインポート区分)。
	 */
	public static final String	CODE_KEY_TIME_IMPORT_TABLE_TYPE										= "TimeImportTableType";
	
	/**
	 * コードキー(勤怠情報データエクスポート区分)。
	 */
	public static final String	CODE_KEY_TIME_EXPORT_TABLE_TYPE										= "TimeExportTableType";
	
	/**
	 * コードキー(手当区分)。
	 */
	public static final String	CODE_ALLOWANCE														= "Allowance";
	
	/**
	 * コードキー(有給休暇の休暇種別)。
	 */
	public static final String	CODE_HOLIDAY_TYPE2_WITHPAY											= "HolidayType2WithPay";
	
	/**
	 * コードキー(有給休暇の休暇種別)。
	 */
	public static final String	CODE_GIVING_TYPE													= "GivingType";
	
	/**
	 * コードキー(振替申請)。
	 */
	public static final String	CODE_WORKONHOLIDAY_SUBSTITUTE										= "WorkOnHolidaySubstitute";
	
	/**
	 * コードキー(振替出勤範囲)。
	 */
	public static final String	CODE_SUBSTITUTE_WORK_RANGE											= "SubstituteWorkRange";
	
	/**
	 * コードキー(振替休日範囲)。
	 */
	public static final String	CODE_SUBSTITUTE_HOLIDAY_RANGE										= "SubstituteHolidayRange";
	
	/**
	 * コードキー（公用外出）。
	 */
	public static final int		CODE_GO_OUT_PUBLIC													= 1;
	
	/**
	 * コードキー（私用外出）。
	 */
	public static final int		CODE_GO_OUT_PRIVATE													= 2;
	
	/**
	 * コードキー（有給休暇）。
	 */
	public static final int		CODE_HOLIDAYTYPE_HOLIDAY											= 1;
	
	/**
	 * コードキー（特別休暇）。
	 */
	public static final int		CODE_HOLIDAYTYPE_SPECIAL											= 2;
	
	/**
	 * コードキー（その他休暇）。
	 */
	public static final int		CODE_HOLIDAYTYPE_OTHER												= 3;
	
	/**
	 * コードキー（欠勤）。
	 */
	public static final int		CODE_HOLIDAYTYPE_ABSENCE											= 4;
	
	/**
	 * コードキー（ストック休暇）。
	 * CODE_HOLIDAYTYPE2_STOCKに置き換える。
	 */
	public static final int		CODE_HOLIDAYTYPE_STOCK												= 2;
	
	/**
	 * 休暇区分2(有給休暇)。
	 */
	public static final String	CODE_HOLIDAYTYPE2_PAID												= "1";
	
	/**
	 * 休暇区分2(ストック休暇)。
	 */
	public static final String	CODE_HOLIDAYTYPE2_STOCK												= "2";
	
	/**
	 * 出勤率区分(出勤扱い)。<br>
	 */
	public static final int		CODE_PAID_HOLIDAY_CALC_PRESENCE										= 1;
	
	/**
	 * 出勤率区分(欠勤扱い)。<br>
	 */
	public static final int		CODE_PAID_HOLIDAY_CALC_ABSENCE										= 2;
	
	/**
	 * 出勤率区分(計算対象外)。<br>
	 */
	public static final int		CODE_PAID_HOLIDAY_CALC_NONE											= 3;
	
	/**
	 * 有給休暇付与日付コード。
	 */
	public static final String	CODE_PAID_LEAVE_GRANT_DATE											= "PaidLeaveGrantDate";
	
	/**
	 * 有給休暇期限日付コード。
	 */
	public static final String	CODE_PAID_LEAVE_EXPIRATION_DATE										= "PaidLeaveExpirationDate";
	
	/**
	 * 有給休暇付与日数コード。
	 */
	public static final String	CODE_PAID_LEAVE_GRANT_DAYS											= "PaidLeaveGrantDays";
	
	/**
	 * 有給休暇付与時間数コード。
	 */
	public static final String	CODE_PAID_LEAVE_GRANT_HOURS											= "PaidLeaveGrantHours";
	
	/**
	 * 有給休暇残日数コード。
	 */
	public static final String	CODE_PAID_LEAVE_REMAIN_DAYS											= "PaidLeaveRemainDays";
	
	/**
	 * 有休休暇残時間数コード。
	 */
	public static final String	CODE_PAID_LEAVE_REMAIN_HOURS										= "PaidLeaveRemainHours";
	
	/**
	 * 有給休暇年度コード。
	 */
	public static final String	CODE_PAID_LEAVE_FISCAL_YEAR											= "PaidLeaveFiscalYear";
	
	/**
	 * 前年度残日数コード。
	 */
	public static final String	CODE_FORMER_YEAR_DAY												= "FormerYearDay";
	
	/**
	 * 前年度残時間コード。
	 */
	public static final String	CODE_FORMER_YEAR_TIME												= "FormerTime";
	
	/**
	 * 今年度残日数コード。
	 */
	public static final String	CODE_CURRENT_YEAR_DAY												= "CurrentYearDay";
	
	/**
	 * 今年度残時間コード。
	 */
	public static final String	CODE_CURRENT_TIME													= "CurrentTime";
	
	/**
	 * 次年度付与日コード。
	 */
	public static final String	CODE_NEXT_PLAN_GIVING_DATE											= "NextPlanGivingDate";
	
	/**
	 * 次年度付与日数コード。
	 */
	public static final String	CODE_NEXT_PLAN_YEAR_DAY												= "NextPlantYearDay";
	
	/**
	 * 次年度付与時間コード。
	 */
	public static final String	CODE_NEXT_PLAN_TIME													= "NextPlanTime";
	
	/**
	 * 次年度期限コード。
	 */
	public static final String	CODE_NEXT_PLAN_LIMIT_DATE											= "NextPlanLimitDate";
	
	/**
	 * 支給日数コード。
	 */
	public static final String	CODE_GIVING_DAY														= "GivingDay";
	
	/**
	 * 支給時間コード。
	 */
	public static final String	CODE_GIVING_TIME													= "GivingTime";
	
	/**
	 * 廃棄日数コード。
	 */
	public static final String	CODE_CANCEL_DAY														= "CancelDay";
	
	/**
	 * 廃棄時間コード。
	 */
	public static final String	CODE_CANCEL_TIME													= "CancelTime";
	
	/**
	 * 利用日数コード。
	 */
	public static final String	CODE_USE_DAY														= "UseDay";
	
	/**
	 * 利用時間コード。
	 */
	public static final String	CODE_USE_TIME														= "UseTime";
	
	/**
	 * 有効日コード。
	 */
	public static final String	CODE_ACTIVATE_DATE													= "ActivateDate";
	
	/**
	 * 合計勤務時間コード。
	 */
	public static final String	CODE_TOTAL_WORK_TIME												= "TotalWorkTime";
	
	/**
	 * 合計休憩時間コード。
	 */
	public static final String	CODE_TOTAL_REST_TIME												= "TotalRestTime";
	
	/**
	 * 合計遅刻時間コード。
	 */
	public static final String	CODE_TOTAL_LATE_TIME												= "TotalLateTime";
	
	/**
	 * 合計早退時間コード。
	 */
	public static final String	CODE_TOTAL_LEAVE_EARLY												= "TotalLeaveEarly";
	
	/**
	 * 合計残業時間コード。
	 */
	public static final String	CODE_TOTAL_OVER_TIMEIN												= "TotalOverTimeIn";
	
	/**
	 * 合計外残時間コード。
	 */
	public static final String	CODE_TOTAL_OVER_TIMEOUT												= "TotalOverTimeOut";
	
	/**
	 * 合計休日出勤時間コード。
	 */
	public static final String	CODE_TOTAL_WORK_ON_HOLIDAY											= "TotalWorkOnHoliday";
	
	/**
	 * 合計深夜勤務時間コード。
	 */
	public static final String	CODE_TOTAL_LATE_NIGHT												= "TotalLateNight";
	
	/**
	 * 合計出勤回数コード。
	 */
	public static final String	CODE_TOTAL_TIMES_WORK												= "TotalTimesWork";
	
	/**
	 * 合計遅刻回数コード。
	 */
	public static final String	CODE_TOTAL_TIMES_LATE												= "TotalTimesLate";
	
	/**
	 * 合計早退回数コード。
	 */
	public static final String	CODE_TOTAL_TIMES_LEAVE_EARLY										= "TotalTimesLeaveEarly";
	
	/**
	 * 合計残業回数コード。
	 */
	public static final String	CODE_TOTAL_TIMES_OVERTIMEWORK										= "TotalTimesOverTimeWork";
	
	/**
	 * 合計休出回数コード。
	 */
	public static final String	CODE_TOTAL_TIMES_WORKONHOLIDAY										= "TotalTimesWorkOnHoliday";
	
	/**
	 *合計法定休日回数
	 */
	public static final String	CODE_TOTAL_TIMES_LEGALHOLIDAY										= "TotalTimesLegalHoliday";
	
	/**
	 *合計所定休日回数
	 */
	public static final String	CODE_TOTAL_TIMES_SPECIFICHOLIDAY									= "TotalTimesSpecificHoliday";
	
	/**
	 *合計有休回数
	 */
	public static final String	CODE_TOTAL_TIMES_PAIDHOLIDAY										= "TotalTimesPaidHoliday";
	
	/**
	 *合計有休時間
	 */
	public static final String	CODE_TOTAL_TIMES_PAIDHOLIDAY_TIME									= "TotalTimesPaidHolidayTime";
	
	/**
	 *合計特別休暇回数
	 */
	public static final String	CODE_TOTAL_TIMES_SPECIALHOLIDAY										= "TotalTimesSpecialHoloiday";
	
	/**
	 *合計その他休暇回数
	 */
	public static final String	CODE_TOTAL_TIMES_OTHERHOLIDAY										= "TotalTimesOtherHoloiday";
	
	/**
	 *合計振替休暇回数
	 */
	public static final String	CODE_TOTAL_TIMES_SUBSTITUTE											= "TotalTimesSubstitute";
	
	/**
	 *合計代休回数
	 */
	public static final String	CODE_TOTAL_TIMES_SUBHOLIDAY											= "TotalTimesSubHoliday";
	
	/**
	 *合計欠勤回数
	 */
	public static final String	CODE_TOTAL_TIMES_ABSENCE											= "TotalTimesAbsence";
	
	/**
	 *手当コード1
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO1											= "1";
	
	/**
	 *手当コード2
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO2											= "2";
	
	/**
	 *手当コード3
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO3											= "3";
	
	/**
	 *手当コード4
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO4											= "4";
	
	/**
	 *手当コード5
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO5											= "5";
	
	/**
	 *手当コード6
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO6											= "6";
	
	/**
	 *手当コード7
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO7											= "7";
	
	/**
	 *手当コード8
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO8											= "8";
	
	/**
	 *手当コード9
	 */
	public static final String	CODE_ALLOWANCE_CODE_INFO9											= "9";
	
	/**
	 *手当コード10
	 */
	public static final String	CODE_ALLOWANCE_CODE_INF10											= "10";
	
	/**
	 * 申請、承認済み、使用済みの合計日数
	 */
	public static final String	CODE_REQUEST_DAY													= "requestDay";
	
	/**
	 * 申請、承認済み、使用済みの合計時間
	 */
	public static final String	CODE_REQUEST_HOUR													= "requestHour";
	
	/**
	 * 承認済みの合計日数
	 */
	public static final String	CODE_APPROVED_DAY													= "approvedDay";
	
	/**
	 * 承認済みの合計時間
	 */
	public static final String	CODE_APPROVED_HOUR													= "approvedHour";
	
	/**
	 * 表示ステータス（遅刻理由区分）
	 */
	public static final String	CODE_REASON_OF_LATE													= "ReasonOfLate";
	
	/**
	 * 表示ステータス（早退理由区分）
	 */
	public static final String	CODE_REASON_OF_LEAVE_EARLY											= "ReasonOfLeaveEarly";
	
	/**
	 * パラメータID(対象年)。
	 */
	public static final String	PRM_TARGET_YEAR														= "prmTargetYear";
	
	/**
	 * パラメータID(対象月)。
	 */
	public static final String	PRM_TARGET_MONTH													= "prmTargetMonth";
	
	/**
	 * パラメータID(対象時刻)。
	 */
	public static final String	PRM_TARGET_TIME														= "prmTargetTime";
	
	/**
	 * パラメータID(ワークフロー番号)。
	 */
	public static final String	PRM_TARGET_WORKFLOW													= "prmTargetWorkflow";
	
	/**
	 * パラメータID(譲渡汎用区分)。
	 */
	public static final String	PRM_TRANSFERRED_TYPE												= "transferredType";
	
	/**
	 * パラメータID(譲渡汎用月)。
	 */
	public static final String	PRM_TRANSFERRED_MONTH												= "transferredMonth";
	
	/**
	 * パラメータID(譲渡年)。
	 */
	public static final String	PRM_TRANSFERRED_YEAR												= "transferredYear";
	
	/**
	 * パラメータID(休暇種別1)。
	 */
	public static final String	PRM_TRANSFERRED_HOLIDAY_TYPE1										= "holidayType1";
	
	/**
	 * パラメータID(休暇種別2)。
	 */
	public static final String	PRM_TRANSFERRED_HOLIDAY_TYPE2										= "holidayType2";
	
	/**
	 * パラメータID(休暇範囲)。
	 */
	public static final String	PRM_TRANSFERRED_HOLIDAY_RANGE										= "holidayRange";
	
	/**
	 * パラメータID(時休開始時刻)。
	 */
	public static final String	PRM_TRANSFERRED_START_TIME											= "StartTime";
	
	/**
	 * パラメータID(譲渡汎用コード)。
	 */
	public static final String	PRM_TRANSFERRED_GENERIC_CODE										= "transferredGenericCode";
	
	/**
	 * パラメータID(譲渡汎用日)。
	 */
	public static final String	PRM_TRANSFERRED_DAY													= "transferredDay";
	
	/**
	 * パラメータID(時間)
	 */
	public static final String	PRM_TRANSFERRED_HOUR												= "transferredHour";
	
	/**
	 * パラメータID(分)
	 */
	public static final String	PRM_TRANSFERRED_MINUTE												= "transferredMinute";
	
	/**
	 * パラメータID(開始時間)
	 */
	public static final String	PRM_TRANSFERRED_START_HOUR											= "transferredStartHour";
	
	/**
	 * パラメータID(開始分)
	 */
	public static final String	PRM_TRANSFERRED_START_MINUTE										= "transferredStartMinute";
	
	/**
	 * パラメータID(終了時間)
	 */
	public static final String	PRM_TRANSFERRED_END_HOUR											= "transferredEndHour";
	
	/**
	 * パラメータID(終了分)
	 */
	public static final String	PRM_TRANSFERRED_END_MINUTE											= "transferredEndMinute";
	
	/**
	 * パラメータID(直行)。
	 */
	public static final String	PRM_TRANSFERRED_DIRECT_START										= "transferredDirectStart";
	
	/**
	 * パラメータID(直帰)。
	 */
	public static final String	PRM_TRANSFERRED_DIRECT_END											= "transferredDirectEnd";
	
	/**
	 * パラメータID(勤怠コメント)。
	 */
	public static final String	PRM_TRANSFERRED_TIME_COMMENT										= "transferredTimeComment";
	
	/**
	 * パラメータID(勤怠集計時エラーリスト)。
	 */
	public static final String	PRM_TOTALTIME_ERROR													= "TotalTimeError";
	
	/**
	 * パラメータID(遷移DTO配列)。
	 */
	public static final String	PRM_ROLL_ARRAY														= "RollArray";
	
	/**
	 * 休暇最大登録数(特別休暇)。
	 */
	public static final int		PRM_HOLIDAYTYPE_SPECIAL_MAX											= 30;
	
	/**
	 * 休暇最大登録数(その他休暇)。
	 */
	public static final int		PRM_HOLIDAYTYPE_OTHER_MAX											= 50;
	
	/**
	 * 休暇最大登録数(欠勤)。
	 */
	public static final int		PRM_HOLIDAYTYPE_ABSENCE_MAX											= 20;
	
	/**
	 * 検索モード。
	 */
	public static final String	PRM_TRANSFER_SEARCH_MODE											= "transferSearch";
	
	/**
	 * 検索モード(前)。
	 */
	public static final String	SEARCH_BACK															= "searchBack";
	
	/**
	 * 検索モード(次)。
	 */
	public static final String	SEARCH_NEXT															= "searchNext";
	
	/**
	 * 申請モード【新規】。
	 */
	public static final String	MODE_APPLICATION_NEW												= "new";
	
	/**
	 * 申請モード【下書編集】。
	 */
	public static final String	MODE_APPLICATION_DRAFT												= "draft";
	
	/**
	 * 申請モード【差戻編集】（1次戻）。
	 */
	public static final String	MODE_APPLICATION_REVERT												= "revert";
	
	/**
	 * 申請モード【未承認】。
	 */
	public static final String	MODE_APPLICATION_APPLY												= "apply";
	
	/**
	 * 申請モード【申請済】。
	 */
	public static final String	MODE_APPLICATION_APPLIED											= "applied";
	
	/**
	 * 休日出勤申請の勤務予定の表示(申請する)。<br>
	 */
	public static final String	MODE_WORK_PLAN_APPLICATION_ON										= "1";
	
	/**
	 * 休日出勤申請の勤務予定の表示(申請しない)。<br>
	 */
	public static final String	MODE_WORK_PLAN_APPLICATION_OFF										= "2";
	
	/**
	 * 残業申請の予定超過の有り。<br>
	 */
	public static final String	SCHEDULE_OVER_FLAG_ON												= "1";
	
	/**
	 * 残業申請の予定超過の無し。<br>
	 */
	public static final String	SCHEDULE_OVER_FLAG_OFF												= "2";
	
	/**
	 * 機能コード(勤怠)。
	 */
	public static final String	CODE_FUNCTION_WORK_MANGE											= "1";
	
	/**
	 * 機能コード(残業)。
	 */
	public static final String	CODE_FUNCTION_OVER_WORK												= "2";
	
	/**
	 * 機能コード(休暇)。
	 */
	public static final String	CODE_FUNCTION_VACATION												= "3";
	
	/**
	 * 機能コード(休出)。
	 */
	public static final String	CODE_FUNCTION_WORK_HOLIDAY											= "4";
	
	/**
	 * 機能コード(代休)。
	 */
	public static final String	CODE_FUNCTION_COMPENSATORY_HOLIDAY									= "5";
	
	/**
	 * 機能コード(時差出勤)。
	 */
	public static final String	CODE_FUNCTION_DIFFERENCE											= "6";
	
	/**
	 * 機能コード(勤務形態変更)。
	 */
	public static final String	CODE_FUNCTION_WORK_TYPE_CHANGE										= "7";
	
	/**
	 * 機能コード(承認解除)。<br>
	 * 申請確認画面や承認解除申請画面に出す必要が無いため、
	 * 設定ファイル(XML)で{@link #CODE_APPROVAL_TYPE}には記載しない。<br>
	 * また、ワークフローの機能コードとして用いられることもない。<br>
	 */
	public static final String	CODE_FUNCTION_CANCELLATION											= "9";
	
	/**
	 * 有給休暇設定詳細の付与区分（基準日）。
	 */
	public static final int		CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY									= 0;
	
	/**
	 * 有給休暇設定詳細の付与区分（入社月）。
	 */
	public static final int		CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH								= 1;
	
	/**
	 * 有給休暇設定詳細の付与区分（入社日）。
	 */
	public static final int		CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY									= 2;
	
	/**
	 * 有給休暇設定詳細の付与区分（対象外）。
	 */
	public static final int		CODE_PAID_HOLIDAY_TYPE_NOT											= 3;
	
	/**
	 * 有給休暇設定詳細の付与区分（比例）。
	 */
	public static final int		CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY								= 4;
	
	/**
	 * 勤怠詳細(勤務形態の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_WORKTYPECODE								= "1";
	/**
	 * 勤怠詳細(出勤時刻の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_STARTTIME									= "2";
	/**
	 * 勤怠詳細(退勤時刻の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ENDTIME									= "3";
	/**
	 * 勤怠詳細(直行の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_DIRECTSTART								= "4";
	/**
	 * 勤怠詳細(直帰の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_DIRECTEND									= "5";
	/**
	 * 勤怠詳細(遅刻理由の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_LATEREASON								= "6";
	/**
	 * 勤怠詳細(遅刻証明書の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_LATECERTIFICATE							= "7";
	/**
	 * 勤怠詳細(遅刻コメントの名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_LATECOMMENT								= "8";
	/**
	 * 勤怠詳細(早退理由の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYREASON							= "9";
	/**
	 * 勤怠詳細(早退証明書の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCERTIFICATE						= "10";
	/**
	 * 勤怠詳細(早退コメントの名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCOMMENT							= "11";
	/**
	 * 勤怠詳細(勤怠コメントの名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_TIMECOMMENT								= "12";
	/**
	 * 勤怠詳細(手当1の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE1								= "13";
	/**
	 * 勤怠詳細(手当2の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE2								= "14";
	/**
	 * 勤怠詳細(手当3の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE3								= "15";
	/**
	 * 勤怠詳細(手当4の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE4								= "16";
	/**
	 * 勤怠詳細(手当5の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE5								= "17";
	/**
	 * 勤怠詳細(手当6の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE6								= "18";
	/**
	 * 勤怠詳細(手当7の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE7								= "19";
	/**
	 * 勤怠詳細(手当8の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE8								= "20";
	/**
	 * 勤怠詳細(手当9の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE9								= "21";
	/**
	 * 勤怠詳細(手当10の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE10								= "22";
	/**
	 * 勤怠詳細(休憩1の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_BREAK1									= "23";
	/**
	 * 勤怠詳細(休憩2の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_BREAK2									= "24";
	/**
	 * 勤怠詳細(休憩3の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_BREAK3									= "25";
	/**
	 * 勤怠詳細(休憩4の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_BREAK4									= "26";
	/**
	 * 勤怠詳細(休憩5の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_BREAK5									= "27";
	/**
	 * 勤怠詳細(休憩6の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_BREAK6									= "28";
	/**
	 * 勤怠詳細(公用外出1の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_OFFICIAL_GOING_OUT1						= "29";
	/**
	 * 勤怠詳細(公用外出2の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_OFFICIAL_GOING_OUT2						= "30";
	/**
	 * 勤怠詳細(私用外出1の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_PRIVATE_GOING_OUT1						= "31";
	/**
	 * 勤怠詳細(私用外出2の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_PRIVATE_GOING_OUT2						= "32";
	/**
	 * 勤怠詳細(実出勤時刻の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ACTUAL_STARTTIME							= "33";
	/**
	 * 勤怠詳細(実退勤時刻の名前)。
	 */
	public static final String	CODE_ATTENDANCE_ITEM_NAME_ACTUAL_ENDTIME							= "34";
	
	/**
	 * ファイルパス(MosP勤怠管理勤怠設定編集領域ヘッダJSP)。
	 */
	public static final String	PATH_SETTINGS_EDIT_HEADER_JSP										= "/jsp/time/settings/settingsEditHeader.jsp";
	
	/**
	 * 勤怠修正(勤務時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_WORKTIME									= "1";
	
	/**
	 * 勤怠修正(所定勤務時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_SPECIFICWORKTIME							= "2";
	
	/**
	 * 勤怠修正(出勤日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESWORKDATE								= "3";
	
	/**
	 * 勤怠修正(出勤回数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESWORK									= "4";
	
	/**
	 * 勤怠修正(法定休日出勤日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LEGALWORKONHOLIDAY							= "5";
	
	/**
	 * 勤怠修正(所定休日出勤日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_SPECIFICWORKONHOLIDAY						= "6";
	
	/**
	 * 勤怠修正(直行回数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_DIRECTSTART								= "7";
	
	/**
	 * 勤怠修正(直帰回数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_DIRECTEND									= "8";
	
	/**
	 * 勤怠修正(休憩時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_RESTTIME									= "9";
	
	/**
	 * 勤怠修正(深夜休憩時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_RESTLATENIGHT								= "10";
	
	/**
	 * 勤怠修正(所定休出休憩時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_RESTWORKONSPECIFICHOLIDAY					= "11";
	
	/**
	 * 勤怠修正(法定休出休憩時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_RESTWORKONHOLIDAY							= "12";
	
	/**
	 * 勤怠修正(公用外出時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_PUBLICTIME									= "13";
	
	/**
	 * 勤怠修正(私用外出時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_PRIVATETIME								= "14";
	
	/**
	 * 勤怠修正(残業回数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESOVERTIME								= "15";
	
	/**
	 * 勤怠修正(法定内残業時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_OVERTIME_IN								= "16";
	
	/**
	 * 勤怠修正(法定外残業時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_OVERTIME_OUT								= "17";
	
	/**
	 * 勤怠修正(深夜時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LATENIGHT									= "18";
	
	/**
	 * 勤怠修正(所定休出時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_WORKONSPECIFICHOLIDAY						= "19";
	
	/**
	 * 勤怠修正(法定休出時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_WORKONHOLIDAY								= "20";
	
	/**
	 * 勤怠修正(減額対象時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_DECREASETIME								= "21";
	
	/**
	 * 勤怠修正(45時間超残業時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_FORTYFIVEHOUROVERTIME						= "22";
	
	/**
	 * 勤怠修正(遅刻合計回数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESLATE									= "23";
	
	/**
	 * 勤怠修正(遅刻合計時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LATETIME									= "27";
	
	/**
	 * 勤怠修正(早退合計回数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESLEAVEEARLY							= "31";
	
	/**
	 * 勤怠修正(早退合計時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LEAVEEARLYTIME								= "35";
	
	/**
	 * 勤怠修正(合計休日日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESHOLIDAY								= "39";
	
	/**
	 * 勤怠修正(法定休日日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESLEGALHOLIDAY							= "40";
	
	/**
	 * 勤怠修正(所定休日日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICHOLIDAY						= "41";
	
	/**
	 * 勤怠修正(有給休暇日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESPAIDHOLIDAY							= "42";
	
	/**
	 * 勤怠修正(有給休暇時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_PAIDHOLIDAYHOUR							= "43";
	
	/**
	 * 勤怠修正(ストック休暇日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESSTOCKHOLIDAY							= "44";
	
	/**
	 * 勤怠修正(合計代休日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESCOMPENSATION							= "45";
	
	/**
	 * 勤怠修正(法定代休日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESLEGALCOMPENSATION						= "46";
	
	/**
	 * 勤怠修正(所定代休日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICCOMPENSATION					= "47";
	
	/**
	 * 勤怠修正(深夜代休日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESLATECOMPENSATION						= "48";
	
	/**
	 * 勤怠修正(合計振替休日日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESHOLIDAYSUBSTITUTE						= "49";
	
	/**
	 * 勤怠修正(法定振替休日日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESLEGALHOLIDAYSUBSTITUTE				= "50";
	
	/**
	 * 勤怠修正(所定振替休日日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICHOLIDAYSUBSTITUTE				= "51";
	
	/**
	 * 勤怠修正(特別休暇合計日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TOTALSPECIALHOLIDAY						= "52";
	
	/**
	 * 勤怠修正(その他休暇合計日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TOTALOTHERHOLIDAY							= "53";
	
	/**
	 * 勤怠修正(欠勤合計日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TOTALABSENCE								= "54";
	
	/**
	 * 勤怠修正(手当合計)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TOTALALLOWANCE								= "55";
	
	/**
	 * 勤怠修正(60時間超残業時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_SIXTYHOUROVERTIME							= "56";
	
	/**
	 * 勤怠修正(平日時間外時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_WEEKDAYOVERTIME							= "57";
	
	/**
	 * 勤怠修正(所定休日時間外時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_SPECIFICOVERTIME							= "58";
	
	/**
	 * 勤怠修正(代替休暇日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMESALTERNATIVE							= "59";
	
	/**
	 * 勤怠修正(特別休暇)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TOTALLEAVE									= "61";
	
	/**
	 * 勤怠修正(その他休暇)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_OTHERVACATION								= "71";
	
	/**
	 * 勤怠修正(欠勤)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ABSENCE									= "81";
	
	/**
	 * 勤怠修正(手当1)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE1									= "91";
	
	/**
	 * 勤怠修正(手当2)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE2									= "92";
	
	/**
	 * 勤怠修正(手当3)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE3									= "93";
	
	/**
	 * 勤怠修正(手当4)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE4									= "94";
	
	/**
	 * 勤怠修正(手当5)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE5									= "95";
	
	/**
	 * 勤怠修正(手当6)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE6									= "96";
	
	/**
	 * 勤怠修正(手当7)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE7									= "97";
	
	/**
	 * 勤怠修正(手当8)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE8									= "98";
	
	/**
	 * 勤怠修正(手当9)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE9									= "99";
	
	/**
	 * 勤怠修正(手当10)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ALLOWANCE10								= "100";
	
	/**
	 * 勤怠修正(遅刻合計日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LATEDAYS									= "101";
	
	/**
	 * 勤怠修正(早退合計日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LEAVEEARLYDAYS								= "102";
	
	/**
	 * 勤怠修正(法定代休未取得日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LEGALCOMPENSATIONUNUSED					= "103";
	
	/**
	 * 勤怠修正(所定代休未取得日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_SPECIFICCOMPENSATIONUNUSED					= "104";
	
	/**
	 * 勤怠修正(深夜代休未取得日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LATECOMPENSATIONUNUSED						= "105";
	
	/**
	 * 勤怠修正(出勤実績日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ATTENDANCE_ACHIEVEMENT						= "106";
	
	/**
	 * 勤怠修正(出勤対象日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_WORK_DATE									= "107";
	
	/**
	 * 勤怠修正(残業時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_OVERTIME									= "108";
	
	/**
	 * 勤怠修正(休出回数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TIMES_WORKING_HOLIDAY						= "109";
	
	/**
	 * 勤怠修正(遅刻30分以上日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LATE_THIRTY_MINUTES_OR_MORE				= "110";
	
	/**
	 * 勤怠修正(遅刻30分未満日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LATE_LESS_THAN_THIRTY_MINUTES				= "111";
	
	/**
	 * 勤怠修正(遅刻30分以上時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LATE_THIRTY_MINUTES_OR_MORE_TIME			= "112";
	
	/**
	 * 勤怠修正(遅刻30分未満時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LATE_LESS_THAN_THIRTY_MINUTES_TIME			= "113";
	
	/**
	 * 勤怠修正(早退30分以上日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE			= "114";
	
	/**
	 * 勤怠修正(早退30分未満日数)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES		= "115";
	
	/**
	 * 勤怠修正(早退30分以上時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME	= "116";
	
	/**
	 * 勤怠修正(早退30分未満時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME	= "117";
	
	/**
	 * 勤怠修正(無給時短時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_SHORT_UNPAID								= "120";
	
	/**
	 * 勤怠修正(特別休暇時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_TOTALLEAVEHOUR								= "121";
	
	/**
	 * 勤怠修正(その他休暇時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_OTHERVACATIONHOUR							= "122";
	
	/**
	 * 勤怠修正(欠勤時間)。
	 */
	public static final String	CODE_TOTALTIME_ITEM_NAME_ABSENCEHOUR								= "123";
	
	/**
	 * 集計項目1
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER1											= "1";
	
	/**
	 * 集計項目2
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER2											= "2";
	
	/**
	 * 集計項目3
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER3											= "3";
	
	/**
	 * 集計項目4
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER4											= "4";
	
	/**
	 * 集計項目5
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER5											= "5";
	
	/**
	 * 集計項目6
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER6											= "6";
	
	/**
	 * 集計項目7
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER7											= "7";
	
	/**
	 * 集計項目8
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER8											= "8";
	
	/**
	 * 集計項目9
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER9											= "9";
	
	/**
	 * 集計項目10
	 */
	public static final String	CODE_COMMON_ITEMS_NUMBER10											= "10";
	
	/**
	 * 表示日付更新用コード（一日進める）
	 */
	public static final String	CODE_DATE_INCREMENT													= "Increment";
	
	/**
	 * 表示日付更新用コード（一日戻る）
	 */
	public static final String	CODE_DATE_DECREMENT													= "Decrement";
	
	/**
	 * 表示日付更新用コード（日付リセット）
	 */
	public static final String	CODE_DATE_RESET														= "Reset";
	
	/**
	 * 表示日付更新用コード（カレンダー日付）
	 */
	public static final String	CODE_DATE_CALENDAR													= "Calendar";
	
	/**
	 * 代休種別コード（所定代休）
	 */
	public static final int		CODE_PRESCRIBED_SUBHOLIDAY_CODE										= 1;
	
	/**
	 * 代休種別コード（法定代休）
	 */
	public static final int		CODE_LEGAL_SUBHOLIDAY_CODE											= 2;
	
	/**
	 * 代休種別コード（深夜代休）
	 */
	public static final int		CODE_MIDNIGHT_SUBHOLIDAY_CODE										= 3;
	
	/**
	 * 勤怠設定の勤務前残業コード（有効）
	 */
	public static final int		CODE_BEFORE_OVERTIME_VALID											= 0;
	
	/**
	 * 勤怠設定の勤務前残業コード（無効）
	 */
	public static final int		CODE_BEFORE_OVERTIME_INVALID										= 1;
	
	/**
	 * 残業申請（勤務前残業）
	 */
	public static final int		CODE_OVERTIME_WORK_BEFORE											= 1;
	
	/**
	 * 残業申請（勤務後残業）
	 */
	public static final int		CODE_OVERTIME_WORK_AFTER											= 2;
	
	/**
	 * 検索条件（下書き）
	 */
	public static final String	CODE_SEARCH_STATUS_DRAFT											= "1";
	
	/**
	 * 検索条件（未承認）
	 */
	public static final String	CODE_SEARCH_STATUS_APPLY											= "2";
	
	/**
	 * 検索条件（承認済）
	 */
	public static final String	CODE_SEARCH_STATUS_COMPLETE											= "3";
	
	/**
	 * 検索条件（差戻）
	 */
	public static final String	CODE_SEARCH_STATUS_REVERT											= "4";
	
	/**
	 * 遅刻理由（個人都合）
	 */
	public static final String	CODE_TARDINESS_WHY_INDIVIDU											= "individu";
	
	/**
	 * 遅刻理由（体調不良）
	 */
	public static final String	CODE_TARDINESS_WHY_BAD_HEALTH										= "bad_health";
	
	/**
	 * 遅刻理由（その他）
	 */
	public static final String	CODE_TARDINESS_WHY_OTHERS											= "others";
	
	/**
	 * 遅刻理由（電車遅延）
	 */
	public static final String	CODE_TARDINESS_WHY_TRAIN											= "train";
	
	/**
	 * 遅刻理由（会社指示）
	 */
	public static final String	CODE_TARDINESS_WHY_COMPANY											= "company";
	
	/**
	 * 早退理由（個人都合）
	 */
	public static final String	CODE_LEAVEEARLY_WHY_INDIVIDU										= "individu";
	
	/**
	 * 早退理由（体調不良）
	 */
	public static final String	CODE_LEAVEEARLY_WHY_BAD_HEALTH										= "bad_health";
	
	/**
	 * 早退理由（その他）
	 */
	public static final String	CODE_LEAVEEARLY_WHY_OTHERS											= "others";
	
	/**
	 * 早退理由（会社指示）
	 */
	public static final String	CODE_LEAVEEARLY_WHY_COMPANY											= "company";
	
	/**
	 * 法定休日
	 */
	public static final String	CODE_HOLIDAY_LEGAL_HOLIDAY											= "legal_holiday";
	
	/**
	 * 所定休日
	 */
	public static final String	CODE_HOLIDAY_PRESCRIBED_HOLIDAY										= "prescribed_holiday";
	
	/**
	 * 法定休出
	 */
	public static final String	CODE_WORK_ON_LEGAL_HOLIDAY											= "work_on_legal";
	
	/**
	 * 所定休出
	 */
	public static final String	CODE_WORK_ON_PRESCRIBED_HOLIDAY										= "work_on_prescribed";
	
	/**
	 * 締日マスタ 月の末締
	 */
	public static final String	CODE_CUTOFF_TYPE_MONTH_END_CLOSING									= "month";
	
	/**
	 * 休日出勤申請 振替申請する(全日)。<br>
	 */
	public static final int		CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON									= 1;
	
	/**
	 * 休日出勤申請 振替申請しない。<br>
	 */
	public static final int		CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF									= 2;
	
	/**
	 * 休日出勤申請 振替申請する(午前)。<br>
	 */
	public static final int		CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM									= 3;
	
	/**
	 * 休日出勤申請 振替申請する(午後)。<br>
	 */
	public static final int		CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM									= 4;
	
	/**
	 * 休日出勤申請 振替申請する(全日・勤務形態変更)。<br>
	 */
	public static final int		CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE					= 5;
	
	/**
	 * 時差出勤申請 時差出勤区分A<br>
	 */
	public static final String	CODE_DIFFERENCE_TYPE_A												= "a";
	
	/**
	 * 時差出勤申請 時差出勤区分B<br>
	 */
	public static final String	CODE_DIFFERENCE_TYPE_B												= "b";
	
	/**
	 * 時差出勤申請 時差出勤区分C<br>
	 */
	public static final String	CODE_DIFFERENCE_TYPE_C												= "c";
	
	/**
	 * 時差出勤申請 時差出勤区分D<br>
	 */
	public static final String	CODE_DIFFERENCE_TYPE_D												= "d";
	
	/**
	 * 時差出勤申請 時差出勤区分S<br>
	 */
	public static final String	CODE_DIFFERENCE_TYPE_S												= "s";
	
	/**
	 * 休暇の範囲 全休<br>
	 */
	public static final int		CODE_HOLIDAY_RANGE_ALL												= 1;
	
	/**
	 * 休暇の範囲 午前休<br>
	 */
	public static final int		CODE_HOLIDAY_RANGE_AM												= 2;
	
	/**
	 * 休暇の範囲 午後休<br>
	 */
	public static final int		CODE_HOLIDAY_RANGE_PM												= 3;
	
	/**
	 * 休暇の範囲 時間休<br>
	 */
	public static final int		CODE_HOLIDAY_RANGE_TIME												= 4;
	
	/**
	 * 休暇の範囲 全休(半休+半休)<br>
	 */
	public static final int		CODE_HOLIDAY_RANGE_HALF_AND_HALF									= 5;
	
	/**
	 * 締日(月末締)。<br>
	 */
	public static final int		CUTOFF_DATE_LAST_DAY												= 0;
	
	/**
	 * 翌月締日の最大値のデフォルト値。<br>
	 * この日よりも小さな締日は、翌月締となる。<br>
	 * 但し、月末締はこの限りでない。
	 */
	public static final int		CUTOFF_DATE_NEXT_MONTH_MAX											= 15;
	
	/**
	 * 締状態コード(末締)。<br>
	 */
	public static final int		CODE_CUTOFF_STATE_NOT_TIGHT											= 0;
	
	/**
	 * 締状態コード(仮締)。<br>
	 */
	public static final int		CODE_CUTOFF_STATE_TEMP_TIGHT										= 1;
	
	/**
	 * 締状態コード(確定)。<br>
	 */
	public static final int		CODE_CUTOFF_STATE_TIGHTENED											= 2;
	
	/**
	 * 出勤区分(出勤)。<br>
	 */
	public static final String	CODE_ATTENDANCE_TYPE_ATTENDANCE										= "attendance";
	
	/**
	 * 出勤区分(休暇)。<br>
	 */
	public static final String	CODE_ATTENDANCE_TYPE_HOLIDAY										= "holiday";
	
	/**
	 * 出勤区分(代休)。<br>
	 */
	public static final String	CODE_ATTENDANCE_TYPE_SUB_HOLIDAY									= "sub_holiday";
	
	/**
	 * 出勤区分(振替休日)。<br>
	 */
	public static final String	CODE_ATTENDANCE_TYPE_SUBSTITUTE										= "substitute";
	
	/**
	 * 出勤区分(休職)。<br>
	 */
	public static final String	CODE_ATTENDANCE_TYPE_SUSPENSION										= "suspension";
	
	/**
	 * 出勤区分(退職)。<br>
	 */
	public static final String	CODE_ATTENDANCE_TYPE_RETIREMENT										= "retirement";
	
	/**
	 * 理由入力(任意)。<br>
	 */
	public static final int		CODE_REASON_TYPE_NOT_REQUIRED										= 0;
	
	/**
	 * 理由入力(必須)。<br>
	 */
	public static final int		CODE_REASON_TYPE_REQUIRED											= 1;
	
	/**
	 * 休暇連続取得区分(必須)。<br>
	 */
	public static final int		TYPE_CONTINUOUS_NECESSARY											= 0;
	
	/**
	 * 休暇連続取得区分(警告)。<br>
	 */
	public static final int		TYPE_CONTINUOUS_WARNING												= 1;
	
	/**
	 * 休暇連続取得区分(不要)。<br>
	 */
	public static final int		TYPE_CONTINUOUS_UNNECESSARY											= 2;
	
	/**
	 * 休暇給与区分(有給)。<br>
	 */
	public static final int		TYPE_SALARY_PAY														= 0;
	
	/**
	 * 丸め区分(無し)。
	 */
	public static final int		TYPE_ROUND_NONE														= 0;
	
	/**
	 * 丸め区分(切捨)。
	 */
	public static final int		TYPE_ROUND_DOWN														= 1;
	
	/**
	 * 丸め区分(切上)。
	 */
	public static final int		TYPE_ROUND_UP														= 2;
	
	/**
	 * ファイルパス(MosP勤怠管理共通JSP)。
	 */
	public static final String	PATH_TIME_COMMON_INFO_JSP											= "/jsp/time/base/timeCommonInfo.jsp";
	
	/**
	 * ファイルパス(MosP勤怠管理申請ステータス共通JSP)。
	 */
	public static final String	PATH_TIME_APPLY_INFO_JSP											= "/jsp/time/base/timeCommonStatusApplicationInfo.jsp";
	
	/**
	 * ファイルパス(MosP勤怠管理申請承認者共通JSP)。
	 */
	public static final String	PATH_TIME_APPROVER_PULLDOWN_JSP										= "/jsp/time/base/timeCommonApproverSettingInfo.jsp";
	
	/**
	 * ファイルパス(MosP勤怠管理申請ボタンJSP)。
	 */
	public static final String	PATH_TIME_APPLY_BUTTON_JSP											= "/jsp/time/base/timeApplyButton.jsp";
	
	/**
	 * ファイルパス(MosP勤怠管理締日共通情報JSP)。
	 */
	public static final String	PATH_TIME_TOTAL_JSP													= "/jsp/time/base/timeCommonTotal.jsp";
	
	/**
	 * CSSクラス定義(赤ラベル)。
	 */
	public static final String	CSS_RED_LABEL														= "RedLabel";
	
	/**
	 * CSSクラス定義(青ラベル)。
	 */
	public static final String	CSS_BLUE_LABEL														= "BlueLabel";
	
	/**
	 * CSSクラス定義(黒ラベル)。
	 */
	public static final String	CSS_BLACK_LABEL														= "BlackLabel";
	
	/**
	 * 月の最大日。<br>
	 */
	public static final int		DATE_MONTH_MAX_DAY													= 31;
	
	/**
	 * 月末指定時の日の値。<br>
	 */
	public static final int		DAY_END_OF_MONTH													= Integer.MIN_VALUE;
	
	/**
	 * 休暇回数(全休)
	 */
	public static final float	HOLIDAY_TIMES_ALL													= 1F;
	
	/**
	 * 休暇回数(半休)
	 */
	public static final float	HOLIDAY_TIMES_HALF													= 0.5F;
	
	/**
	 * 深夜時間開始時刻。<br>
	 */
	public static final int		TIME_NIGHT_WORK_START												= 22;
	
	/**
	 * 深夜時間終了時刻。<br>
	 */
	public static final int		TIME_NIGHT_WORK_END													= 29;
	
	/**
	 * 所定労働時間。<br>
	 */
	public static final int		TIME_WORKING_HOUR													= 8;
	
	/**
	 * 週の法定労働時間(分)。<br>
	 */
	public static final int		TIME_LEGAL_WEEKLY_WORK_TIME											= 2400;
	
	/**
	 * 30分の定数。<br>
	 */
	public static final int		TIME_HURF_HOUR_MINUTES												= 30;
	
	/**
	 * 1日の時間。<br>
	 */
	public static final int		TIME_DAY_ALL_HOUR													= 24;
	
	/**
	 * 勤務時間（6時間）。<br>
	 */
	public static final int		TIME_WORK_TIME_6													= 360;
	
	/**
	 * MosPアプリケーション設定キー(法定労働時間(分))。<br>
	 */
	public static final String	APP_LEGAL_WORK_TIME													= "LegalWorkTime";
	
	/**
	 * MosPアプリケーション設定キー(週の法定労働時間(分))。<br>
	 */
	public static final String	APP_LEGAL_WEEKLY_WORK_TIME											= "LegalWeeklyWorkTime";
	
	/**
	 * MosPアプリケーション設定キー(時短時間機能利用可否)。
	 */
	public static final String	APP_ADD_USE_SHORT_UNPAID											= "UseShortUnpaid";
	
	/**
	 * MosPアプリケーション設定キー(有給休暇申請理由必須設定)。
	 */
	public static final String	APP_PAID_HOLIDAY_REASON_REQUIRED									= "PaidHolidayReasonRequired";
	
	/**
	 * MosPアプリケーション設定キー(ストック休暇出勤率扱い)。
	 */
	public static final String	APP_STOCK_HOLIDAY_ATTENDANCE										= "StockHolidayAttendance";
	
	/**
	 * MosPアプリケーション設定キー(ポータル時刻表示機能)。
	 */
	public static final String	APP_VIEW_PORTAL_TIME												= "ViewPortalTime";
	
	/**
	 * MosPアプリケーション設定キー(画面遷移後の集計値保持機能)。
	 */
	public static final String	APP_VIEW_TOTAL_VALUES												= "ViewTotalValues";
	
	/**
	 * MosPアプリケーション設定キー(部下一覧等未申表示)。
	 */
	public static final String	APP_SHOW_APPLIABLE_EXIST											= "ShowAppliableExist";
	
	/**
	 * MosPアプリケーション設定キー(統計情報一覧：季休コード)。
	 */
	public static final String	APP_SHOW_SEASON_HOLIDAY_CODE										= "SeasonHolidayCode";
	
	/**
	 * MosPアプリケーション設定キー(エクスポート時間フォーマット)。<br>
	 */
	public static final String	APP_EXPORT_TIME_FORMAT												= "ExportTimeFormat";
	
	/**
	 * MosPアプリケーション設定キー(翌月締日の最大値)。<br>
	 */
	public static final String	APP_CUTOFF_DATE_NEXT_MONTH_MAX										= "CutoffDateNextMonthMax";
	
	/**
	 * MosPアプリケーション設定キー(半日法定振替出勤)。<br>
	 */
	public static final String	APP_HALF_LEGAL_SUBSTITUTE											= "HalfLegalSubstitute";
	
	/**
	 * 勤務形態プルダウンの変更処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_CHANGE_WORK_TYPE_PULLDOWN									= "ChangeWorkTypePulldown";
	
	/**
	 * 勤務形態の追加アイテム用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADDITIONAL_WORKTYPE_ITEM									= "additionalWorkTypeItem";
	
	/**
	 * 勤務設定の追加項目一括更新用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADDITIONAL_TIME_SETTING_UPDATE								= "AdditionalTimeSettingUpdate";
	
	/**
	 * 勤怠詳細の私用外出チェック追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCECARDACTION_CHKPRIVATEGOOUT					= "Add_AttendanceCardAction_ChkPrivateGoOut";
	
	/**
	 * エクスポートテーブル追加項目用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_EXPORTTABLEREFERENCEBEAN_EXPORT						= "Add_ExportTableReferenceBean_Export";
	
	/**
	 * カレンダ登録の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_SCHEDULEREGISTBEAN_VALIDATE							= "Add_ScheduleRegistBean_Validate";
	
	/**
	 * 勤怠集計からレコードを取得する際の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMEREFERENCEBEAN_FINDFORKEY						= "Add_TotalTimeReferenceBean_FindForKey";
	
	/**
	 * 期間で勤怠集計を取得する際の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMEREFERENCEBEAN_FINDFISCALMAP					= "Add_TotalTimeReferenceBean_FindFiscalMap";
	
	/**
	 * 勤怠集計データ新規登録の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMEREGISTBEAN_INSERT								= "Add_TotalTimeRegistBean_Insert";
	
	/**
	 * 勤怠集計データ履歴更新の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMEREGISTBEAN_UPDATE								= "Add_TotalTimeRegistBean_Update";
	
	/**
	 * 勤怠集計データ論理削除の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMEREGISTBEAN_DELETE1							= "Add_TotalTimeRegistBean_Delete1";
	
	/**
	 * 勤怠集計データ論理削除の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMEREGISTBEAN_DELETE2							= "Add_TotalTimeRegistBean_Delete2";
	
	/**
	 * 申請時確認処理の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCEREGISTBEAN_CHECKAPPLI						= "Add_AttendanceRegistBean_CheckAppli";
	
	/**
	 * 時間限度チェックの追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_HOLIDAYREQUESTREGISTBEAN_CHECKTIMEHOLIDAYLIMIT			= "Add_HolidayRequestRegistBean_CheckTimeHolidayLimit";
	
	/**
	 * 時間単位休暇確認処理の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_HOLIDAYREQUESTREGISTBEAN_CHECKTIMEHOLIDAY				= "Add_HolidayRequestRegistBean_CheckTimeHoliday";
	
	/**
	 * 休憩時刻調整処理の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_RESTREGISTBEAN_SETRESTSTARTENDTIME						= "Add_RestRegistBean_SetRestStartEndTime";
	
	/**
	 * 始業終業時刻を元に外出開始時刻と外出終了時刻を設定する際の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_GOOUTREGISTBEAN_SETGOOUTSTARTEND						= "Add_GoOutRegistBean_SetGoOutStartEnd";
	
	/**
	 * 勤怠集計クラスの集計追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMECALCBEAN_TOTAL								= "Add_TotalTimeCalcBean_Total";
	
	/**
	 * 勤怠集計情報を取得する際の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMECALCBEAN_GETTOTALTIMEDATA						= "Add_TotalTimeCalcBean_GetTotalTimeData";
	
	/**
	 * 勤怠集計クラスの勤怠集計エンティティ取得する際の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_TOTALTIMEENTITYREFERENCEBEAN_GETTOTALTIMEENTITY		= "Add_TotalTimeEntityReferenceBean_GetTotalTimeEntity";
	
	/**
	 * 休暇申請画面申請処理の追加処理用コードキー。<br>
	 */
	public static final String	CODE_KEY_ADD_HOLIDAYREQUESTACTION_APPLI								= "Add_HolidayRequestAction_Appli";
	
	/**
	 * コードキー(追加業務ロジック：勤怠詳細画面：勤怠情報設定)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCECARDACTION_SETATTENDANCE						= "Add_AttendanceCardAction_setAttendance";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧情報備考設定)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTEND_LIST_REMARKS									= "Add_AttendListRemarks";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧情報後処理)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTEND_LIST_AFTER										= "Add_AttendListAfter";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧参照処理：予定一覧取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETSCHEDULELIST			= "Add_AttendanceListReferenceBean_getScheduleList";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧参照処理：出勤簿取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETACTUALLIST				= "Add_AttendanceListReferenceBean_getActualList";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧参照処理：勤怠一覧取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETATTENDANCELIST			= "Add_AttendanceListReferenceBean_getAttendanceList";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧参照処理：勤怠一覧(週)取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETWEEKLYATTENDANCELIST	= "Add_AttendanceListReferenceBean_getWeeklyAttendanceList";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧参照処理：勤怠承認一覧取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETAPPROVALATTENDANCELIST	= "Add_AttendanceListReferenceBean_getApprovalAttendanceList";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧参照処理：勤怠一覧情報取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETATTENDANCELISTDTO		= "Add_AttendanceListReferenceBean_getAttendanceListDto";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧参照処理：勤怠情報設定)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_SETDTOFIELDS				= "Add_AttendanceListReferenceBean_setDtoFields";
	
	/**
	 * コードキー(追加業務ロジック：勤怠一覧参照処理：休暇申請情報設定)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_ADDHOLIDAYREQUESTLIST		= "Add_AttendanceListReferenceBean_addHolidayRequestList";
	
	/**
	 * コードキー(追加業務ロジック：部下一覧検索処理：勤怠集計情報設定)。<br>
	 */
	public static final String	CODE_KEY_ADD_SUBORDINATESEARCHBEAN_SETTOTALTIMEDATA					= "Add_SubordinateSearchBean_setTotalTimeData";
	
	/**
	 * コードキー(追加業務ロジック：統計情報一覧検索処理：統計情報一覧情報取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_SUBORDINATEFISCALSEARCHBEAN_SUBORDINATEFISCALLIST		= "Add_SubordinateFiscalSearchBean_subordinateFiscalList";
	
	/**
	 * コードキー(追加業務ロジック：承認情報参照処理：勤怠申請一覧情報取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_APPROVALINFOREFERENCEBEAN_GETMANAGEMENTREQUESTLISTDTO	= "Add_ApprovalInfoReferenceBean_getManagementRequestListDto";
	
	/**
	 * コードキー(追加業務ロジック：休暇種別情報参照処理：エクスポート項目用配列取得)。<br>
	 */
	public static final String	CODE_KEY_ADD_HOLIDAYREFERENCEBEAN_GETEXPORTARRAY					= "Add_HolidayReferenceBean_getExportArray";
	
	/**
	 * コードキー(追加業務ロジック：休暇取得データエクスポート処理：CSVデータリスト作成)。<br>
	 */
	public static final String	CODE_KEY_ADD_HOLIDAYEXPORTBEAN_GETCSVDATALIST						= "Add_HolidayExportBean_getCsvDataList";
	
	/**
	 * コードキー(追加業務ロジック：休暇取得データエクスポート処理：ヘッダ付加)。<br>
	 */
	public static final String	CODE_KEY_ADD_HOLIDAYEXPORTBEAN_ADDHEADER							= "Add_HolidayExportBean_addHeader";
	
	/**
	 * コードキー(勤怠トランザクション情報作成追加処理)。<br>
	 */
	public static final String	CODE_KEY_ADD_MAKE_ATTENDANCE_TRANSACTION_DTO						= "Add_Make_Attedance_Transaction_Dto";
	
	/**
	 * コードキー(ポータル打刻登録追加処理)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCE_REGIST_REGIST_ADDON_TABLE					= "Add_Portal_Attedance_Regist_registAddonTable";
	
	/**
	 * コードキー(ポータル打刻登録追加処理：始業時対象日判定)。<br>
	 */
	public static final String	CODE_KEY_ADD_ATTENDANCE_GET_TARGET_DATE_ON_START_WORK				= "Add_Portal_Attedance_Regist_getTargetDateOnStartWork";
	
	/**
	 * コードキー(ポータル打刻追加処理クラス)。<br>
	 */
	public static final String	CODE_KEY_ATTENDANCE_REGIST_ADDONS									= "AttendanceCheckAddons";
	
	/**
	 * コードキー(勤怠登録追加処理：始業チェック)。<br>
	 */
	public static final String	CODE_KEY_ADD_CHECK_START_TIME										= "Add_CheckStartTime";
	
	/**
	 * コードキー(勤怠登録追加処理：終業チェック)。<br>
	 */
	public static final String	CODE_KEY_ADD_CHECK_END_TIME											= "Add_CheckEndTime";
	
	/**
	 * コードキー(追加業務ロジック：分単位休暇取得)。<br>
	 */
	public static final String	CODE_KEY_MINUTES_OFF												= "MinutesOffBeans";
	
	/**
	 * レコードIDが取得できなかった際のコードキー。<br>
	 */
	public static final long	CODE_KEY_NOT_RECORD_ID												= 0;
	
	/**
	 * コードキー(利用可否チェック処理)。<br>
	 */
	public static final String	CODE_KEY_CHECK_APPLY_AVAILABLE										= "CheckApplyAvailable";
	
	/**
	 * コードキー(勤怠一覧画面追加処理)。<br>
	 */
	public static final String	CODE_KEY_ATTENDANCE_LIST_ADDONS										= "AttedanceListAddons";
	
	/**
	 * コードキー(ポータル画面勤怠一覧追加処理)。<br>
	 */
	public static final String	CODE_KEY_PORTAL_ATTENDANCE_LIST_ADDONS								= "PortalAttedanceListAddons";
	
	/**
	 * コードキー(ポータル打刻追加処理クラス)。<br>
	 */
	public static final String	CODE_KEY_PORTAL_ATTENDANCE_CARD_ADDONS								= "PortalAttedanceCardAddons";
	
	/**
	 * コードキー(打刻前追加処理)。<br>
	 */
	public static final String	CODE_KEY_BEFORE_RECORD_TIME_ADDONS									= "BeforeRecordTimeAddons";
	
	/**
	 * コードキー(振出・休出申請登録追加処理)。<br>
	 */
	public static final String	CODE_KEY_WORK_ON_HOLIDAY_REQUEST_REGIST_ADDONS						= "WorkOnHolidayRequestRegistAddons";
	
	/**
	 * コードキー(勤怠集計エンティティ参照申請検出エンティティ取得追加処理)。<br>
	 */
	public static final String	CODE_KEY_TOTAL_TIME_ENT_REF_DETECT_ADDONS							= "TotalTimeEntRefDetectAddons";
	
	/**
	 * コードキー(申請検出エンティティワークフロー取得追加処理)。<br>
	 */
	public static final String	CODE_KEY_REQUEST_DETECT_GET_WORKFLOWS_ADDONS						= "RequestDetectGetWorkflowsAddons";
	
	/**
	 * ポータルパラメータキー用文字列(ポータル画面勤怠一覧アドオン列項目名)。<br>
	 * アドオンでポータル勤怠一覧にアドオン列を設定する際に利用する。<br>
	 * 勤怠一覧追加処理のクラス名と合わせて、ポータルパラメータキーとなる。<br>
	 */
	public static final String	PORTAL_PARAM_KEY_COLUMN_NAME										= "ColumnName";
	
	/**
	 * ポータルパラメータキー用文字列(ポータル画面勤怠一覧アドオン列項目値)。<br>
	 * アドオンでポータル勤怠一覧にアドオン列を設定する際に利用する。<br>
	 * 勤怠一覧追加処理のクラス名と合わせて、ポータルパラメータキーとなる。<br>
	 */
	public static final String	PORTAL_PARAM_KEY_COLUMN_VALUE										= "ColumnValue";
	
	/**
	 * ポータルパラメータキー用文字列(ポータル画面勤怠一覧アドオン列項目クラス文字)。<br>
	 * アドオンでポータル勤怠一覧にアドオン列を設定する際に利用する。<br>
	 * 勤怠一覧追加処理のクラス名と合わせて、ポータルパラメータキーとなる。<br>
	 */
	public static final String	PORTAL_PARAM_KEY_COLUMN_CLASS										= "ColumnClass";
	
}
