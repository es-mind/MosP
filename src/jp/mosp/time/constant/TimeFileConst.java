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
package jp.mosp.time.constant;

/**
 * インポート、エクスポートで用いる定数を宣言する。<br>
 */
public class TimeFileConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeFileConst() {
		// 処理無し
	}
	
	
	/**
	 * インポート区分(勤怠データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_ATTENDANCE								= "import_tmd_attendance";
	
	/**
	 * インポート区分(勤怠データ【自己承認】)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_ATTENDANCE_SELF						= "import_tmd_attendance_self";
	
	/**
	 * インポート区分(勤怠集計データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_TOTAL_TIME								= "import_tmd_total_time";
	
	/**
	 * インポート区分(有給休暇データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_PAID_HOLIDAY							= "import_tmd_paid_holiday";
	
	/**
	 * インポート区分(ストック休暇データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_STOCK_HOLIDAY							= "import_tmd_stock_holiday";
	
	/**
	 * インポート区分(休暇データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_HOLIDAY								= "import_tmd_holiday";
	
	/**
	 * インポート区分(休暇申請データ【自己承認】)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_HOLIDAY_REQUEST						= "import_tmd_holiday_request";
	
	/**
	 * インポート区分(振替出勤申請データ【自己承認】)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_WORK_ON_HOLIDAY_REQUEST				= "import_tmd_work_on_holiday_request";
	
	/**
	 * インポート区分(休日出勤申請データ【自己承認】)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_WORK_ON_HOLIDAY_REQUEST_SUBSTITUTE_OFF	= "import_tmd_work_on_holiday_request_substitute_off";
	
	/**
	 * インポート区分(勤務形態データ)。
	 */
	public static final String	CODE_IMPORT_TYPE_TMD_WORK_TYPE								= "import_tmd_work_type";
	
	/**
	 * エクスポート区分(勤怠データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_ATTENDANCE								= "export_tmd_attendance";
	
	/**
	 * 勤怠データフィールド(打刻始業時間)。
	 */
	public static final String	FIELD_TIME_ROCODE_START_TIME								= "time_recode_start_time";
	
	/**
	 * 勤怠データフィールド(打刻終業時間)。
	 */
	public static final String	FIELD_TIME_ROCODE_END_TIME									= "time_recode_end_time";
	
	/**
	 * エクスポート区分(勤怠集計データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_TOTAL_TIME								= "export_tmd_total_time";
	
	/**
	 * エクスポート区分(有給休暇データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_PAID_HOLIDAY							= "export_tmd_paid_holiday";
	
	/**
	 * エクスポート区分(ストック休暇データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_STOCK_HOLIDAY							= "export_tmd_stock_holiday";
	
	/**
	 * エクスポート区分(休暇データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_HOLIDAY								= "export_tmd_holiday";
	
	/**
	 * エクスポート区分(代休データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_TMD_SUB_HOLIDAY							= "export_tmd_sub_holiday";
	
	/**
	 * 勤怠エクスポート(休暇取得データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_HOLIDAY_REQUEST_DATA						= "export_holiday_request_data";
	
	/**
	 * エクスポート区分(勤怠再申請対象者)。
	 */
	public static final String	CODE_EXPORT_TYPE_ATTENDANCE_REAPPLICATION					= "export_attendance_reapplication";
	
	/**
	 * エクスポート区分(出勤簿)。
	 */
	public static final String	CODE_EXPORT_TYPE_ATTENDANCE_BOOK							= "export_tmd_attendance_book";
	
	/**
	 * エクスポート区分(各種申請理由データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_APPLI_REASON_DATA							= "export_appli_reason_data";
	
	/**
	 * エクスポート区分(有給休暇取得状況データ)。
	 */
	public static final String	CODE_EXPORT_TYPE_USED_PAID_HOLIDAY_DATA						= "export_used_paid_holiday_data";
	
	/**
	 * フィールド(代休日1)。
	 */
	public static final String	FIELD_REQUEST_DATE1											= "request_date1";
	
	/**
	 * フィールド(代休日2)。
	 */
	public static final String	FIELD_REQUEST_DATE2											= "request_date2";
	
	/**
	 * 出勤簿フィールド(カレンダ日付)。
	 */
	public static final String	FIELD_SHEDULE_DAY											= "schedule_Day";
	
	/**
	 * 出勤簿フィールド(勤務形態コード)。
	 */
	public static final String	FIELD_WORK_TYPE_CODE										= "work_type_code";
	
	/**
	 * 出勤簿フィールド(勤務形態略称)。
	 */
	public static final String	FIELD_WORK_TYPE_ABBR										= "work_type_abbr";
	
	/**
	 * 出勤簿フィールド(始業時刻)。
	 */
	public static final String	FIELD_START_TIME											= "start_time";
	
	/**
	 * 出勤簿フィールド(終業時刻)。
	 */
	public static final String	FIELD_END_TIME												= "end_time";
	
	/**
	 * 出勤簿フィールド(勤務時間)。
	 */
	public static final String	FIELD_WORK_TIME												= "work_time";
	
	/**
	 * 出勤簿フィールド(休憩時間)。
	 */
	public static final String	FIELD_REST_TIME												= "rest_time";
	
	/**
	 * 出勤簿フィールド(私用外出時間)。
	 */
	public static final String	FIELD_PRIVATE_TIME											= "private_time";
	
	/**
	 * 出勤簿フィールド(遅刻早退時間)。
	 */
	public static final String	FIELD_LATE_EARLY_TIME										= "late_leave_early_time";
	
	/**
	 * 出勤簿フィールド(法定内残業時間)。
	 */
	public static final String	FIELD_OVER_TIME_IN											= "overtime_in";
	
	/**
	 * 出勤簿フィールド(法定外残業時間)。
	 */
	public static final String	FIELD_OVER_TIME_OUT											= "overtime_out";
	
	/**
	 * 出勤簿フィールド(休日出勤時間)。
	 */
	public static final String	FIELD_WORK_ON_HOLIDAY										= "work_on_holiday";
	
	/**
	 * 出勤簿フィールド(深夜時間)。
	 */
	public static final String	FIELD_LAST_NIGHT											= "late_night";
	
	/**
	 * 出勤簿フィールド(備考)。
	 */
	public static final String	FIELD_TIME_REMARKS											= "time_remarks";
	
	/**
	 * フィールド(有給休暇(全休))。
	 */
	public static final String	FIELD_PAID_HOLIDAY_ALL										= "paid_holiday_all";
	
	/**
	 * フィールド(有給休暇(半休))。
	 */
	public static final String	FIELD_PAID_HOLIDAY_HALF										= "paid_holiday_half";
	
	/**
	 * フィールド(有給休暇(午前))。
	 */
	public static final String	FIELD_PAID_HOLIDAY_AM										= "paid_holiday_am";
	
	/**
	 * フィールド(有給休暇(午後))。
	 */
	public static final String	FIELD_PAID_HOLIDAY_PM										= "paid_holiday_pm";
	
	/**
	 * フィールド(有給休暇(時休))。
	 */
	public static final String	FIELD_PAID_HOLIDAY_TIME										= "paid_holiday_time";
	
	/**
	 * フィールド(ストック休暇(全休))。
	 */
	public static final String	FIELD_STOCK_HOLIDAY_ALL										= "stock_holiday_all";
	
	/**
	 * フィールド(ストック休暇(半休))。
	 */
	public static final String	FIELD_STOCK_HOLIDAY_HALF									= "stock_holiday_half";
	
	/**
	 * フィールド(ストック休暇(午前))。
	 */
	public static final String	FIELD_STOCK_HOLIDAY_AM										= "stock_holiday_am";
	
	/**
	 * フィールド(ストック休暇(午後))。
	 */
	public static final String	FIELD_STOCK_HOLIDAY_PM										= "stock_holiday_pm";
	
	/**
	 * フィールド(代休(全休))。
	 */
	public static final String	FIELD_SUB_HOLIDAY_ALL										= "sub_holiday_all";
	
	/**
	 * フィールド(代休(午前))。
	 */
	public static final String	FIELD_SUB_HOLIDAY_AM										= "sub_holiday_am";
	
	/**
	 * フィールド(代休(午後))。
	 */
	public static final String	FIELD_SUB_HOLIDAY_PM										= "sub_holiday_pm";
	
	/**
	 * フィールド(代休(半休))。
	 */
	public static final String	FIELD_SUB_HOLIDAY_HALF										= "sub_holiday_half";
	
	/**
	 * フィールド(振替休日(全休))。
	 */
	public static final String	FIELD_SUBSTITUTE_HOLIDAY_ALL								= "substitute_holiday_all";
	
	/**
	 * フィールド(全休)。
	 */
	public static final String	FIELD_ALL													= "all";
	
	/**
	 * フィールド(半休)。
	 */
	public static final String	FIELD_HALF													= "half";
	
	/**
	 * フィールド(午前)。
	 */
	public static final String	FIELD_AM													= "am";
	
	/**
	 * フィールド(午後)。
	 */
	public static final String	FIELD_PM													= "pm";
	
	/**
	 * フィールド(時間休)。
	 */
	public static final String	FIELD_HOUR													= "hour";
	
	/**
	 * フィールド(申請理由データ：日付)。
	 */
	public static final String	FIELD_WORK_DATE												= "work_date";
	
	/**
	 * フィールド(申請理由データ：申請区分)。
	 */
	public static final String	FIELD_APPLI_TYPE											= "appli_type";
	
	/**
	 * フィールド(申請理由データ：詳細1)。
	 */
	public static final String	FIELD_APPLI_DETAIL_1										= "appli_detailed_1";
	
	/**
	 * フィールド(申請理由データ：詳細2)。
	 */
	public static final String	FIELD_APPLI_DETAIL_2										= "appli_detailed_2";
	
	/**
	 * フィールド(申請理由データ：詳細3)。
	 */
	public static final String	FIELD_APPLI_DETAIL_3										= "appli_detailed_3";
	
	/**
	 * フィールド(申請理由データ：詳細4)。
	 */
	public static final String	FIELD_APPLI_DETAIL_4										= "appli_detailed_4";
	
	/**
	 * フィールド(申請理由データ：申請理由)。
	 */
	public static final String	FIELD_APPLI_REASON											= "appli_reason";
	
	/**
	 * エクスポート時間フォーマット(分)。
	 */
	public static final int		CODE_EXPORT_TIME_FORMAT_MINUTES								= 0;
	
	/**
	 * エクスポート時間フォーマット(時間)。
	 */
	public static final int		CODE_EXPORT_TIME_FORMAT_HOURS								= 1;
	
	/**
	 * エクスポート時間フォーマット(HH:MM)。
	 */
	public static final int		CODE_EXPORT_TIME_FORMAT_COLON_SEPARATED						= 2;
	
	/**
	 * エクスポート時間フォーマット(HH.MM)。
	 */
	public static final int		CODE_EXPORT_TIME_FORMAT_DOT_SEPARATED						= 3;
	
	/**
	 * フィールド(前年繰越日数)。
	 */
	public static final String	FIELD_CARYYOVER_DAY											= "carryover_day";
	
	/**
	 * フィールド(前年繰越時間)。
	 */
	public static final String	FIELD_CARYYOVER_HOUR										= "carryover_hour";
	
	/**
	 * フィールド(有効日(年))。
	 */
	public static final String	ACTIVATE_DATE_YEAR											= "activate_date_year";
	
	/**
	 * フィールド(有効日(月))。
	 */
	public static final String	ACTIVATE_DATE_MONTH											= "activate_date_month";
	
	/**
	 * フィールド(始業時刻)。
	 */
	public static final String	WORK_START_TIME												= "work_start_time";
	
	/**
	 * フィールド(終業時刻)。
	 */
	public static final String	WORK_END_TIME												= "work_end_time";
	
	/**
	 * フィールド(休憩1(開始時間))。
	 */
	public static final String	REST1_START_TIME											= "rest1_start_time";
	
	/**
	 * フィールド(休憩1(終了時間))。
	 */
	public static final String	REST1_END_TIME												= "rest1_end_time";
	
	/**
	 * フィールド(休憩2(開始時間))。
	 */
	public static final String	REST2_START_TIME											= "rest2_start_time";
	
	/**
	 * フィールド(休憩2(終了時間))。
	 */
	public static final String	REST2_END_TIME												= "rest2_end_time";
	
	/**
	 * フィールド(休憩3(開始時間))。
	 */
	public static final String	REST3_START_TIME											= "rest3_start_time";
	
	/**
	 * フィールド(休憩3(終了時間))。
	 */
	public static final String	REST3_END_TIME												= "rest3_end_time";
	/**
	 * フィールド(休憩4(開始時間))。
	 */
	public static final String	REST4_START_TIME											= "rest4_start_time";
	
	/**
	 * フィールド(休憩4(終了時間))。
	 */
	public static final String	REST4_END_TIME												= "rest4_end_time";
	
	/**
	 * フィールド(午前休(開始時間))。
	 */
	public static final String	FRONT_START_TIME											= "front_start_time";
	
	/**
	 * フィールド(午前休(終了時間))。
	 */
	public static final String	FRONT_END_TIME												= "front_end_time";
	
	/**
	 * フィールド(午後休(開始時間))。
	 */
	public static final String	BACK_START_TIME												= "back_start_time";
	
	/**
	 * フィールド(午前休(終了時間))。
	 */
	public static final String	BACK_END_TIME												= "back_end_time";
	
	/**
	 * フィールド(残業休憩時間[毎])。
	 */
	public static final String	OVER_PER													= "over_per";
	
	/**
	 * フィールド(残業休憩時間)。
	 */
	public static final String	OVER_REST													= "over_rest";
	
	/**
	 * フィールド(残前休憩)。
	 */
	public static final String	OVER_BEFORE													= "over_before";
	
	/**
	 * フィールド(勤務前残業実績登録)。
	 */
	public static final String	BEFORE_OVERTIME												= "before_overtime";
	
	/**
	 * フィールド(半休取得休憩(午前休取得日の勤務時間))。
	 */
	public static final String	HALF_REST_WORK_TIME											= "half_rest_work_time";
	
	/**
	 * フィールド(半休取得休憩(休憩開始時間))。
	 */
	public static final String	HALF_REST_START_TIME										= "half_rest_start_time";
	
	/**
	 * フィールド(半休取得休憩(休憩終了時間))。
	 */
	public static final String	HALF_REST_END_TIME											= "half_rest_end_time";
	
	/**
	 * フィールド(直行)。
	 */
	public static final String	DIRECT_START												= "direct_start";
	
	/**
	 * フィールド(直帰)。
	 */
	public static final String	DIRECT_END													= "direct_end";
	
	/**
	 * フィールド(割増休憩除外)。
	 */
	public static final String	EXCLUDE_NIGHT_REST											= "exclude_night_rest";
	
	/**
	 * フィールド(時短時間1(開始時間))。
	 */
	public static final String	SHORT1_START												= "short1_start";
	
	/**
	 * フィールド(時短時間1(終了時間))。
	 */
	public static final String	SHORT1_END													= "short1_end";
	
	/**
	 * フィールド(時短時間1(区分))。
	 */
	public static final String	SHORT1_TYPE													= "short1_type";
	
	/**
	 * フィールド(時短時間2(開始時間))。
	 */
	public static final String	SHORT2_START												= "short2_start";
	
	/**
	 * フィールド(時短時間2(終了時間))。
	 */
	public static final String	SHORT2_END													= "short2_end";
	
	/**
	 * フィールド(時短時間2(区分))。
	 */
	public static final String	SHORT2_TYPE													= "short2_type";
	
	/**
	 * フィールド(申請日数)。
	 */
	public static final String	USED_DAYS													= "used_days";
	
	/**
	 * フィールド(申請日)。
	 */
	public static final String	APPLIE_DATE													= "appli_date";
	
	/**
	 * フィールド(取得日)
	 */
	public static final String	ACQUISITION_DATE											= "acquisition_date";
	
	/**
	 * フィールド(カレンダ勤務日数)
	 */
	public static final String	CALENDAR_WORKING_DAYS										= "calendar_working_days";
	
	/**
	 * フィールド(週40時間超勤務時間(法定内発生分))。
	 */
	public static final String	WEEKLY_OVER_FORTY_IN										= "weekly_over_forty_in";
	
	/**
	 * フィールド(週40時間超勤務時間(勤務内発生分))。
	 */
	public static final String	WEEKLY_OVER_FORTY_NORMNAL									= "weekly_over_forty_normnal";
	
}
