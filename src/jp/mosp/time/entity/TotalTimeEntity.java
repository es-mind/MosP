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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.time.bean.impl.TotalTimeEntityReferenceBean;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;
import jp.mosp.time.utils.TotalTimeUtility;

/**
 * 勤怠集計エンティティクラス。<br>
 */
public class TotalTimeEntity implements TotalTimeEntityInterface {
	
	/**------------- 計算情報(定数) -------------**/
	
	/**
	 * 法定労働時間及び法定残業時間の基準時間。<br>
	 */
	protected static final int								LEGAL_OVERTIME_WORK					= 45;
	
	/**------------- 計算情報(変数) -------------**/
	
	/**
	 * MosP処理情報。<br>
	 */
	protected MospParams									mospParams;
	
	/**
	 * 個人ID。<br>
	 */
	protected String										personalId;
	
	/**
	 * 計算対象年。<br>
	 */
	protected int											calculationYear;
	
	/**
	 * 計算対象月。<br>
	 */
	protected int											calculationMonth;
	
	/**
	 * 締日コード
	 */
	protected String										cutoffCode;
	
	/**
	 * 締期間初日。<br>
	 */
	protected Date											cutoffFirstDate;
	
	/**
	 * 締期間最終日。<br>
	 */
	protected Date											cutoffLastDate;
	
	/**
	 * 締期間(個人)対象日リスト。<br>
	 * 締期間初日(個人)及び締期間最終日(個人)から求める。<br>
	 */
	protected List<Date>									targetDateList;
	
	/**
	 * 休職情報リスト。<br>
	 * 休職中の勤怠集計は行わないため、その判断に用いる。<br>
	 */
	protected List<SuspensionDtoInterface>					suspensionList;
	
	/**
	 * 休暇種別情報群。<br>
	 * 締期間基準日における最新の休暇種別情報が格納される。<br>
	 * 但し、締期間基準日における最新の休暇種別情報が
	 * 無効となっている情報は含まれない。<br>
	 */
	protected Set<HolidayDtoInterface>						holidaySet;
	
	/**
	 * 設定適用情報群。<br>
	 * 締期間(個人)における日毎の設定適用情報を格納する。<br>
	 * 勤怠設定情報を特定するために用いる。<br>
	 */
	protected Map<Date, ApplicationDtoInterface>			applicationMap;
	
	/**
	 * 勤怠設定情報群。<br>
	 * 締期間(個人)における日毎の勤怠設定情報を格納する。<br>
	 * 用途は、次の通り。<br>
	 * ・代休取得期限の取得<br>
	 * ・週40時間計算のための週の起算日取得<br>
	 */
	protected Map<Date, TimeSettingDtoInterface>			timeSettingMap;
	
	/**
	 * 予定勤務形態コード群。<br>
	 * 締期間(個人)における日毎の勤務形態コードを格納する。<br>
	 * <br>
	 * カレンダ情報によって作られる。<br>
	 * 振出・休出申請や勤務形態変更申請は考慮されない。<br>
	 * <br>
	 * 用途は、次の通り。<br>
	 * ・法定休日と所定休日の判定<br>
	 * ・休暇取得時の無給時短時間の取得<br>
	 */
	protected Map<Date, String>								scheduleMap;
	
	/**
	 * 振出・休出勤務形態コード群。<br>
	 * <br>
	 * 振出・休出申請により出勤する日の予定勤務形態コード群。<br>
	 * <br>
	 */
	protected Map<Date, String>								substitutedMap;
	
	/**
	 * 勤務形態情報エンティティ群。<br>
	 * 用途は、次の通り。<br>
	 * ・休暇取得時の無給時短時間の取得<br>
	 * <br>
	 * 時短機能を使わない場合は、空のMapが設定される。<br>
	 */
	protected Map<String, List<WorkTypeEntityInterface>>	workTypeEntityMap;
	
	/**
	 * 勤怠申請リスト。<br>
	 */
	protected List<AttendanceDtoInterface>					attendanceList;
	
	/**
	 * 休日出勤申請リスト。<br>
	 */
	protected List<WorkOnHolidayRequestDtoInterface>		workOnHolidayRequestList;
	
	/**
	 * 休暇申請リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>				holidayRequestList;
	
	/**
	 * 代休申請リスト。<br>
	 * <br>
	 * 代休未使用日数を集計するため、締期間(個人)の代休申請だけでなく、
	 * 締期間初日(個人)より前(代休取得期限まで)の情報も含まれる。<br>
	 * <br>
	 */
	protected List<SubHolidayRequestDtoInterface>			subHolidayRequestList;
	
	/**
	 * 残業申請リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>				overtimeRequestList;
	
	/**
	 * 勤務形態変更申請リスト。<br>
	 */
	protected List<WorkTypeChangeRequestDtoInterface>		workTypeChangeRequestList;
	
	/**
	 * 時差出勤申請リスト。<br>
	 */
	protected List<DifferenceRequestDtoInterface>			differenceRequestList;
	
	/**
	 * 振替休日情報リスト。<br>
	 */
	protected List<SubstituteDtoInterface>					substitubeList;
	
	/**
	 * 代休情報リスト。<br>
	 * <br>
	 * 代休未使用日数を集計するため、締期間(個人)の代休情報だけでなく、
	 * 締期間初日(個人)より前(代休取得期限まで)の情報も含まれる。<br>
	 * <br>
	 * 用途は、次の通り。<br>
	 * ・代休発生日数の集計
	 * ・代休未使用日数の集計
	 */
	protected List<SubHolidayDtoInterface>					subHolidayList;
	
	/**
	 * 代休休日出勤日時点の勤怠設定情報リスト。<br>
	 */
	protected Map<Date, TimeSettingDtoInterface>			subHolidayTimeSettingMap;
	
	/**
	 * ワークフロー情報群。<br>
	 */
	protected Map<Long, WorkflowDtoInterface>				workflowMap;
	
	/**
	 * 勤怠トランザクション情報リスト。<br>
	 */
	protected Set<AttendanceTransactionDtoInterface>		attendanceTransactionSet;
	
	/**
	 * 勤怠トランザクション登録判定情報群。<br>
	 * <br>
	 * 休職と所定休日と法定休日の日が、キーとして設定される。<br>
	 * 値は勤怠トランザクションの出勤区分。<br>
	 * <br>
	 */
	protected Map<Date, String>								attendanceTransactionMap;
	
	/**
	 * 申請エンティティ群。<br>
	 * 日毎に作成したエンティティを取っておき、再利用する。<br>
	 */
	protected Map<Date, RequestEntityInterface>				requestEntityMap;
	
	/**------------- 変数(勤怠集計後) -------------**/
	
	/**
	 * 勤務時間
	 */
	protected int											workTime							= 0;
	/**
	 * 契約勤務時間
	 */
	protected int											contractWorkTime					= 0;
	/**
	 * 出勤日数
	 */
	protected double										timesWorkDate						= 0;
	/**
	 * 出勤回数
	 */
	protected int											timesWork							= 0;
	/**
	 * 法定休日出勤日数
	 */
	protected double										legalWorkOnHoliday					= 0;
	/**
	 * 所定休日出勤日数
	 */
	protected double										specificWorkOnHoliday				= 0;
	/**
	 * 出勤実績日数
	 */
	protected int											timesAchievement					= 0;
	/**
	 * 出勤対象日数
	 */
	protected int											timesTotalWorkDate					= 0;
	/**
	 * 直行回数
	 */
	protected int											directStart							= 0;
	/**
	 * 直帰回数
	 */
	protected int											directEnd							= 0;
	/**
	 * 休憩時間
	 */
	protected int											restTime							= 0;
	/**
	 * 深夜休憩時間
	 */
	protected int											restLateNight						= 0;
	/**
	 * 所定休出休憩時間
	 */
	protected int											restWorkOnSpecificHoliday			= 0;
	/**
	 * 法定休出休憩時間
	 */
	protected int											restWorkOnHoliday					= 0;
	/**
	 * 公用外出時間
	 */
	protected int											publicTime							= 0;
	/**
	 * 私用外出時間
	 */
	protected int											privateTime							= 0;
	/**
	 * 分単位休暇A時間
	 */
	protected int											minutelyHolidayATime				= 0;
	/**
	 * 分単位休暇B時間
	 */
	protected int											minutelyHolidayBTime				= 0;
	/**
	 * 残業時間
	 */
	protected int											overtime							= 0;
	
	/**
	 * 法定内残業時間
	 */
	protected int											overtimeIn							= 0;
	/**
	 * 法定外残業時間
	 */
	protected int											overtimeOut							= 0;
	
	/**
	 * 深夜時間
	 */
	protected int											lateNight							= 0;
	/**
	 * 深夜所定労働時間内時間
	 */
	protected int											nightWorkWithinPrescribedWork		= 0;
	/**
	 * 深夜時間外時間
	 */
	protected int											nightOvertimeWork					= 0;
	/**
	 * 深夜休日労働時間
	 */
	protected int											nightWorkOnHoliday					= 0;
	/**
	 * 所定休出時間
	 */
	protected int											workOnSpecificHoliday				= 0;
	/**
	 * 法定休出時間
	 */
	protected int											workOnHoliday						= 0;
	/**
	 * 減額対象時間
	 */
	protected int											decreaseTime						= 0;
	/**
	 * 45時間超残業時間
	 */
	protected int											fortyFiveHourOvertime				= 0;
	/**
	 * 残業回数
	 */
	protected int											timesOvertime						= 0;
	/**
	 * 休日出勤回数
	 */
	protected int											timesWorkingHoliday					= 0;
	/**
	 * 合計遅刻日数
	 */
	protected int											lateDays							= 0;
	/**
	 * 遅刻30分以上日数
	 */
	protected int											lateThirtyMinutesOrMore				= 0;
	/**
	 * 遅刻30分未満日数
	 */
	protected int											lateLessThanThirtyMinutes			= 0;
	/**
	 * 合計遅刻時間
	 */
	protected int											lateTime							= 0;
	/**
	 * 遅刻30分以上時間
	 */
	protected int											lateThirtyMinutesOrMoreTime			= 0;
	/**
	 * 遅刻30分未満時間
	 */
	protected int											lateLessThanThirtyMinutesTime		= 0;
	/**
	 * 合計遅刻回数
	 */
	protected int											timesLate							= 0;
	/**
	 * 合計早退日数
	 */
	protected int											leaveEarlyDays						= 0;
	/**
	 * 早退30分以上日数
	 */
	protected int											leaveEarlyThirtyMinutesOrMore		= 0;
	/**
	 * 早退30分未満日数
	 */
	protected int											leaveEarlyLessThanThirtyMinutes		= 0;
	/**
	 * 合計早退時間
	 */
	protected int											leaveEarlyTime						= 0;
	/**
	 * 早退30分以上時間
	 */
	protected int											leaveEarlyThirtyMinutesOrMoreTime	= 0;
	/**
	 * 早退30分未満時間
	 */
	protected int											leaveEarlyLessThanThirtyMinutesTime	= 0;
	/**
	 * 合計早退回数
	 */
	protected int											timesLeaveEarly						= 0;
	/**
	 * 休日日数
	 */
	protected int											timesHoliday						= 0;
	/**
	 * 法定休日日数
	 */
	protected int											timesLegalHoliday					= 0;
	/**
	 * 所定休日日数
	 */
	protected int											timesSpecificHoliday				= 0;
	/**
	 * 有給休暇日数
	 */
	protected double										timesPaidHoliday					= 0;
	/**
	 * 有給休暇時間
	 */
	protected int											paidHolidayHour						= 0;
	/**
	 * ストック休暇日数
	 */
	protected double										timesStockHoliday					= 0;
	/**
	 * 代休日数
	 */
	protected double										timesCompensation					= 0;
	/**
	 * 法定代休日数
	 */
	protected double										timesLegalCompensation				= 0;
	/**
	 * 所定代休日数
	 */
	protected double										timesSpecificCompensation			= 0;
	/**
	 * 深夜代休日数
	 */
	protected double										timesLateCompensation				= 0;
	/**
	 * 振替休日日数
	 */
	protected double										timesHolidaySubstitute				= 0;
	/**
	 * 法定振替休日日数
	 */
	protected double										timesLegalHolidaySubstitute			= 0;
	/**
	 * 所定振替休日日数
	 */
	protected double										timesSpecificHolidaySubstitute		= 0;
	
	/**
	 * 特別休暇合計日数。<br>
	 */
	protected double										totalSpecialHoliday					= 0D;
	/**
	 * 特別休暇時間。<br>
	 */
	protected int											specialHolidayHour					= 0;
	/**
	 * その他休暇合計日数。<br>
	 */
	protected double										totalOtherHoliday					= 0D;
	/**
	 * その他休暇時間。<br>
	 */
	protected int											otherHolidayHour					= 0;
	/**
	 * 欠勤合計日数。<br>
	 */
	protected double										totalAbsence						= 0D;
	/**
	 * 欠勤時間。<br>
	 */
	protected int											absenceHour							= 0;
	/**
	 * 手当合計
	 */
	protected int											totalAllowance						= 0;
	/**
	 * 60時間超残業時間
	 */
	protected int											sixtyHourOvertime					= 0;
	/**
	 * 平日時間内時間(平日法定労働時間内残業時間)
	 */
	protected int											workdayOvertimeIn					= 0;
	/**
	 * 所定休日時間内時間(所定休日法定労働時間内残業時間)
	 */
	protected int											prescribedHolidayOvertimeIn			= 0;
	/**
	 * 平日時間外時間(平日法定労働時間外残業時間)
	 */
	protected int											workdayOvertimeOut					= 0;
	/**
	 * 所定休日時間外時間(所定休日法定労働時間外残業時間)
	 */
	protected int											prescribedOvertimeOut				= 0;
	/**
	 * 代替休暇日数
	 */
	protected double										timesAlternative					= 0;
	/**
	 * 法定代休発生日数
	 */
	protected double										legalCompensationOccurred			= 0;
	/**
	 * 所定代休発生日数
	 */
	protected double										specificCompensationOccurred		= 0;
	/**
	 * 深夜代休発生日数
	 */
	protected double										lateCompensationOccurred			= 0;
	/**
	 * 法定代休未使用日数
	 */
	protected double										legalCompensationUnused				= 0;
	/**
	 * 所定代休未使用日数
	 */
	protected double										specificCompensationUnused			= 0;
	/**
	 * 深夜代休未使用日数
	 */
	protected double										lateCompensationUnused				= 0;
	/**
	 * 所定労働時間内法定休日労働時間
	 */
	protected int											statutoryHolidayWorkTimeIn			= 0;
	/**
	 * 所定労働時間外法定休日労働時間
	 */
	protected int											statutoryHolidayWorkTimeOut			= 0;
	/**
	 * 所定労働時間内所定休日労働時間
	 */
	protected int											prescribedHolidayWorkTimeIn			= 0;
	/**
	 * 所定労働時間外所定休日労働時間
	 */
	protected int											prescribedHolidayWorkTimeOut		= 0;
	/**
	 * 無給時短時間
	 */
	protected int											shortUnpaid							= 0;
	/**
	 * 週40時間超勤務時間
	 */
	protected int											weeklyOverFortyHourWorkTime			= 0;
	/**
	 * 法定内残業時間(週40時間超除く)
	 */
	protected int											overtimeInNoWeeklyForty				= 0;
	/**
	 * 法定外残業時間(週40時間超除く)
	 */
	protected int											overtimeOutNoWeeklyForty			= 0;
	/**
	 * 平日時間内時間(週40時間超除く)
	 */
	protected int											weekDayOvertimeInNoWeeklyForty		= 0;
	/**
	 * 平日時間外時間(週40時間超除く)
	 */
	protected int											weekDayOvertimeOutNoWeeklyForty		= 0;
	
	/**
	 * 汎用項目1(数値)
	 */
	protected int											generalIntItem1						= 0;
	/**
	 * 汎用項目2(数値)
	 */
	protected int											generalIntItem2						= 0;
	/**
	 * 汎用項目3(数値)
	 */
	protected int											generalIntItem3						= 0;
	/**
	 * 汎用項目4(数値)
	 */
	protected int											generalIntItem4						= 0;
	/**
	 * 汎用項目5(数値)
	 */
	protected int											generalIntItem5						= 0;
	
	/**
	 * 汎用項目1(浮動小数点数)
	 */
	protected double										generalDoubleItem1					= 0;
	/**
	 * 汎用項目2(浮動小数点数)
	 */
	protected double										generalDoubleItem2					= 0;
	/**
	 * 汎用項目3(浮動小数点数)
	 */
	protected double										generalDoubleItem3					= 0;
	/**
	 * 汎用項目4(浮動小数点数)
	 */
	protected double										generalDoubleItem4					= 0;
	/**
	 * 汎用項目5(浮動小数点数)
	 */
	protected double										generalDoubleItem5					= 0;
	
	/**
	 * 特別休暇日数群(キー：休暇コード)。<br>
	 */
	protected Map<String, Float>							specialHolidayDays;
	
	/**
	 * 特別休暇時間数群(キー：休暇コード)。<br>
	 */
	protected Map<String, Integer>							specialHolidayHours;
	
	/**
	 * その他休暇日数群(キー：休暇コード)。<br>
	 */
	protected Map<String, Float>							otherHolidayDays;
	
	/**
	 * その他休暇時間数群(キー：休暇コード)。<br>
	 */
	protected Map<String, Integer>							otherHolidayHours;
	
	/**
	 * 欠勤日数群(キー：休暇コード)。<br>
	 */
	protected Map<String, Float>							absenceDays;
	
	/**
	 * 欠勤時間数群(キー：休暇コード)。<br>
	 */
	protected Map<String, Integer>							absenceHours;
	
	/**
	 * 汎用項目群(キー：汎用コード)。<br>
	 */
	protected Map<String, Object>							generalMap;
	
	/**
	 * 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)。<br>
	 * 申請エンティティを使う際に用いる。<br>
	 * 勤怠計算時には承認済でないものは除去されているため、
	 * ここではtrueで固定とする。<br>
	 */
	protected boolean										isCompleted							= true;
	
	
	/**
	 * コンストラクタ。<br>
	 * 各種コレクションに空のオブジェクトを設定する。<br>
	 */
	public TotalTimeEntity() {
		targetDateList = Collections.emptyList();
		suspensionList = Collections.emptyList();
		attendanceList = Collections.emptyList();
		holidayRequestList = Collections.emptyList();
		workOnHolidayRequestList = Collections.emptyList();
		overtimeRequestList = Collections.emptyList();
		workTypeChangeRequestList = Collections.emptyList();
		differenceRequestList = Collections.emptyList();
		substitubeList = Collections.emptyList();
		subHolidayRequestList = Collections.emptyList();
		subHolidayList = Collections.emptyList();
		holidaySet = Collections.emptySet();
		attendanceTransactionSet = Collections.emptySet();
		timeSettingMap = Collections.emptyMap();
		scheduleMap = Collections.emptyMap();
		substitutedMap = Collections.emptyMap();
		applicationMap = Collections.emptyMap();
		workTypeEntityMap = Collections.emptyMap();
		subHolidayTimeSettingMap = Collections.emptyMap();
		workflowMap = Collections.emptyMap();
		generalMap = Collections.emptyMap();
	}
	
	/**------------- 計算処理 -------------
	 * @throws MospException  日付の成形に失敗した場合 **/
	
	@Override
	public void total() throws MospException {
		// フィールドを初期化
		setInitFileds();
		// 日毎に加算する勤怠項目を集計
		totalAttendance();
		// 法定休日及び所定休日の日数を集計
		totalScheduledHoliday();
		// 有給休暇及びストック休暇の日数を集計
		totalPaidHoliday();
		// 休暇(特別休暇、その他休暇、欠勤)の回数を集計
		totalHoliday();
		// 振替休日日数を集計
		totalSubstituteHoliday();
		// 代休日数を集計
		totalSubHoliday();
		// 代休発生日数を集計
		totalOccurredSubHoliday();
		// 代休未使用日数を集計
		totalUnusedSubHoliday();
		// 休暇時の無給時短時間を加算
		addHolidayShortUnpaid();
		// 週40時間の計算
		calcWeeklyForty();
		// 残業時間(45時間超と60時間超)を計算
		calcExtraOverTime();
		// 汎用項目を集計
		totalGeneralItem();
		// 勤怠トランザクション登録判定情報群を設定
		setAttendanceTransactionMap();
		// 出勤対象日数と出勤実績日数を集計
		totalWorkTimes();
	}
	
	/**
	 * 日毎に加算する勤怠項目を集計する。<br>
	 * @throws MospException  日付の成形に失敗した場合
	 */
	protected void totalAttendance() throws MospException {
		// 計算対象日毎に処理
		for (Date targetDate : targetDateList) {
			// 休職中である場合
			if (HumanUtility.isSuspension(suspensionList, targetDate)) {
				// 処理無し
				continue;
			}
			// 申請エンティティを取得
			RequestEntityInterface entity = getRequestEntity(targetDate);
			// 勤怠申請がされていない場合
			if (entity.isAttendanceApplied() == false) {
				// 処理無し
				continue;
			}
			// 勤怠情報を取得
			AttendanceDtoInterface dto = entity.getAttendanceDto();
			// 勤務時間と出勤日数と出勤回数を加算
			calcWorkTime(entity);
			// 休日・休憩時間設定
			calcHolidayRestTime(entity);
			// 残業時間(週40時間除く)設定
			calcOvertime(entity);
			// 遅刻・早退時間設定
			calcLeaveLateTime(dto);
			// 休日労働時間設定
			calcHolidayWorkTime(dto);
			// 出勤時間等設定
			calcContractWorkTime(dto);
		}
	}
	
	/**
	 * 法定休日及び所定休日の日数を集計する。<br>
	 */
	protected void totalScheduledHoliday() {
		// 対象日毎に処理
		for (Date targetDate : targetDateList) {
			// 休職中である場合
			if (HumanUtility.isSuspension(suspensionList, targetDate)) {
				// 処理無し
				continue;
			}
			// 申請エンティティを取得
			RequestEntityInterface entity = getRequestEntity(targetDate);
			// 勤怠申請がされている場合
			if (entity.isAttendanceApplied()) {
				// 処理無し
				continue;
			}
			// 振替出勤である場合
			if (entity.isWorkOnHolidaySubstitute(isCompleted)) {
				// 処理無し
				continue;
			}
			// 予定勤務形態(カレンダ)を取得
			String workTypeCode = scheduleMap.get(targetDate);
			// 所定休日である場合
			if (TimeUtility.isPrescribedHoliday(workTypeCode)) {
				// 所定休日日数を加算
				timesSpecificHoliday++;
			}
			// 法定休日である場合
			if (TimeUtility.isLegalHoliday(workTypeCode)) {
				// 法定休日日数を加算
				timesLegalHoliday++;
			}
		}
		// 休日日数を設定
		timesHoliday = timesSpecificHoliday + timesLegalHoliday;
	}
	
	/**
	 * 振替休日日数を集計する。<br>
	 * <br>
	 */
	protected void totalSubstituteHoliday() {
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface dto : substitubeList) {
			// 振替日(休日)を取得
			Date targetDate = dto.getSubstituteDate();
			// 振替日の申請エンティティを取得
			RequestEntityInterface entity = getRequestEntity(targetDate);
			// 振替出勤か休日出勤である場合
			if (entity.isWorkOnHolidaySubstitute(isCompleted) || entity.isWorkOnHolidaySubstituteOff(isCompleted)) {
				// 処理無し
				continue;
			}
			// 振替範囲と振替種別を取得
			int range = dto.getSubstituteRange();
			String substituteType = dto.getSubstituteType();
			// 日数を準備
			float amount = 0F;
			// 全休の場合
			if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 日数(1)を設定
				amount++;
			}
			// 半休の場合
			if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 日数(0.5)を設定
				amount = TimeConst.HOLIDAY_TIMES_HALF;
			}
			// 振替休日日数を加算
			timesHolidaySubstitute += amount;
			// 法定振替休日の場合
			if (TimeUtility.isLegalHoliday(substituteType)) {
				// 法定振替休日日数を加算
				timesLegalHolidaySubstitute += amount;
			}
			// 所定振替休日の場合
			if (TimeUtility.isPrescribedHoliday(substituteType)) {
				// 所定振替休日日数を加算
				timesSpecificHolidaySubstitute += amount;
			}
		}
	}
	
	/**
	 * 有給休暇及びストック休暇の日数を集計する。<br>
	 */
	protected void totalPaidHoliday() {
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			// 休暇コードと休暇区分と休暇範囲を取得
			String holidayCode = dto.getHolidayType2();
			int holidayType = dto.getHolidayType1();
			int holidayRange = dto.getHolidayRange();
			// 休暇区分が有給休暇でない場合
			if (holidayType != TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
				// 処理無し
				continue;
			}
			// 有給休暇の場合
			if (holidayCode.equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY))) {
				// 全休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					timesPaidHoliday++;
				}
				// 半休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					timesPaidHoliday += TimeConst.HOLIDAY_TIMES_HALF;
				}
				// 時休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					paidHolidayHour += dto.getUseHour();
				}
			}
			// ストック休暇の場合
			if (holidayCode.equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_STOCK))) {
				// 全休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					timesStockHoliday++;
				}
				// 半休の場合
				if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
						|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					timesStockHoliday += TimeConst.HOLIDAY_TIMES_HALF;
				}
			}
		}
	}
	
	/**
	 * 休暇(特別休暇、その他休暇、欠勤)の回数を集計する。<br>
	 */
	protected void totalHoliday() {
		// 特別休暇回数群(キー：休暇コード)を準備
		specialHolidayDays = new HashMap<String, Float>();
		specialHolidayHours = new HashMap<String, Integer>();
		// その他休暇回数群(キー：休暇コード)を準備
		otherHolidayDays = new HashMap<String, Float>();
		otherHolidayHours = new HashMap<String, Integer>();
		// 欠勤回数群(キー：休暇コード)を準備
		absenceDays = new HashMap<String, Float>();
		absenceHours = new HashMap<String, Integer>();
		// 休暇種別毎に処理(休暇回数群を初期化)
		for (HolidayDtoInterface dto : holidaySet) {
			// 休暇コード及び休暇区分を取得
			String holidayCode = dto.getHolidayCode();
			int holidayType = dto.getHolidayType();
			// 休暇回数群に初期値を設定
			addHolidayTimes(holidayCode, holidayType, 0F, 0);
		}
		// 休暇申請毎に処理(回数を加算)
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			// 休暇コードと休暇区分と休暇範囲を取得
			String holidayCode = dto.getHolidayType2();
			int holidayType = dto.getHolidayType1();
			int holidayRange = dto.getHolidayRange();
			// 回数を準備
			float days = 0F;
			int hours = 0;
			// 全休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 回数は休暇取得日数
				days = DateUtility.getDayDifference(dto.getRequestStartDate(), dto.getRequestEndDate()) + 1;
			}
			// 前半休か後半休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				days = TimeConst.HOLIDAY_TIMES_HALF;
			}
			// 時間休の場合
			if (TimeRequestUtility.isHolidayRangeHour(dto)) {
				// 時間を加算
				++hours;
			}
			// 日数が1以下の場合(単日か半休の場合)
			if (days <= 1) {
				// 休暇回数群(キー：休暇コード)に休暇回数を加算
				addHolidayTimes(holidayCode, holidayType, days, hours);
				continue;
			}
			// 回数が1より大きい場合(所定休日又は法定休日を除いて回数をカウント)
			// 回数を初期化
			days = getHolidayRequestCount(dto);
			// 休暇回数群(キー：休暇コード)に休暇回数を加算
			addHolidayTimes(holidayCode, holidayType, days, hours);
		}
		// 特別休暇日数及び時間数を取得
		totalSpecialHoliday = totalHolidayDays(specialHolidayDays);
		specialHolidayHour = totalHolidayHours(specialHolidayHours);
		// その他休暇回数を取得
		totalOtherHoliday = totalHolidayDays(otherHolidayDays);
		otherHolidayHour = totalHolidayHours(otherHolidayHours);
		// 欠勤回数を取得
		totalAbsence = totalHolidayDays(absenceDays);
		absenceHour = totalHolidayHours(absenceHours);
	}
	
	/**
	 * 代休日数を集計する。<br>
	 * <br>
	 */
	protected void totalSubHoliday() {
		// 代休申請毎に処理
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
			// 今月に申請したものでない場合
			if (cutoffFirstDate.after(dto.getRequestDate())) {
				// 処理無し
				continue;
			}
			// 代休種別と休暇範囲を取得
			int workDateSubHolidayType = dto.getWorkDateSubHolidayType();
			int holidayRange = dto.getHolidayRange();
			// 日数を準備
			float amount = 0F;
			// 全休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 日数(1)を設定
				amount++;
			}
			// 半休の場合
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 日数(0.5)を設定
				amount = TimeConst.HOLIDAY_TIMES_HALF;
			}
			// 代休日数を加算
			timesCompensation += amount;
			// 法定代休の場合
			if (workDateSubHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
				// 法定代休日数を加算
				timesLegalCompensation += amount;
			}
			// 所定代休の場合
			if (workDateSubHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
				// 所定代休日数を加算
				timesSpecificCompensation += amount;
			}
			// 深夜代休の場合
			if (workDateSubHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
				// 深夜代休日数を加算
				timesLateCompensation += amount;
			}
		}
	}
	
	/**
	 * 代休発生日数を集計する。<br>
	 * <br>
	 */
	protected void totalOccurredSubHoliday() {
		// 代休情報毎に処理
		for (SubHolidayDtoInterface dto : subHolidayList) {
			// 今月に発生したものでない場合
			if (cutoffFirstDate.after(dto.getWorkDate())) {
				// 処理無し
				continue;
			}
			// 代休種別と代休日数を取得
			int subHolidayType = dto.getSubHolidayType();
			double subHolidayDays = dto.getSubHolidayDays();
			// 法定代休の場合
			if (subHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
				// 法定代休発生日数
				legalCompensationOccurred += subHolidayDays;
			}
			// 所定代休の場合
			if (subHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
				// 所定代休発生日数
				specificCompensationOccurred += subHolidayDays;
			}
			// 深夜代休の場合
			if (subHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
				// 深夜代休発生日数
				lateCompensationOccurred += subHolidayDays;
			}
		}
	}
	
	/**
	 * 代休未使用日数を集計する。<br>
	 * <br>
	 * 締期間中に未使用で期限に達した代休をカウントする。<br>
	 * <br>
	 * 代休情報及び代休申請情報を基に、次の値を集計する。<br>
	 * ・所定代休未使用日数<br>
	 * ・法定代休未使用日数<br>
	 * ・深夜代休未使用日数<br>
	 * <br>
	 */
	protected void totalUnusedSubHoliday() {
		// 代休情報毎に処理
		for (SubHolidayDtoInterface subHolidayDto : subHolidayList) {
			// 代休が期限切れでない場合
			if (isSubHolidayExpired(subHolidayDto) == false) {
				// 処理無し
				continue;
			}
			// 代休日数と代休種別と出勤日を取得
			double subHolidayDays = subHolidayDto.getSubHolidayDays();
			int subHolidayType = subHolidayDto.getSubHolidayType();
			Date workDate = subHolidayDto.getWorkDate();
			// 使用日数を準備
			double useDays = 0D;
			// 代休申請毎に処理
			for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
				// 勤務日が異なるか代休種別が異なる場合
				if (workDate.compareTo(dto.getWorkDate()) != 0 || subHolidayType != dto.getWorkDateSubHolidayType()) {
					// 処理無し(対象代休情報を利用していないと判断)
					continue;
				}
				// 休暇範囲を取得
				int range = dto.getHolidayRange();
				// 全休の場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 使用日数を加算(1)
					useDays++;
				}
				// 午前休又は午後休の場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 使用日数を加算(0.5)
					useDays += TimeConst.HOLIDAY_TIMES_HALF;
				}
			}
			// 未使用日数を取得
			double unusedDays = subHolidayDays - useDays;
			// 代休種別が所定代休の場合
			if (subHolidayType == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
				// 所定代休未使用日数を加算
				specificCompensationUnused += unusedDays;
			}
			// 代休種別が法定代休の場合
			if (subHolidayType == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
				// 法定代休未使用日数を加算
				legalCompensationUnused += unusedDays;
			}
			// 代休種別が深夜代休の場合
			if (subHolidayType == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
				// 深夜代休未使用日数を加算
				lateCompensationUnused += unusedDays;
			}
		}
	}
	
	/**
	 * 休暇時の無給時短時間を加算する。<br>
	 */
	protected void addHolidayShortUnpaid() {
		// 対象日毎に処理
		for (Date targetDate : targetDateList) {
			// 休職中である場合
			if (HumanUtility.isSuspension(suspensionList, targetDate)) {
				// 処理無し
				continue;
			}
			// 申請エンティティを取得
			RequestEntityInterface entity = getRequestEntity(targetDate);
			// 勤怠申請がされている場合
			if (entity.isAttendanceApplied()) {
				// 処理無し
				continue;
			}
			// 対象日の予定勤務形態コード(振出・休出、振替休日含む)を取得
			String workTypeCode = getScheduledWorkTypeCode(targetDate);
			// 所定休日又は法定休日である場合
			if (TimeUtility.isHoliday(workTypeCode)) {
				// 処理無し
				continue;
			}
			// 勤務形態エンティティを取得
			WorkTypeEntityInterface workTypeEntity = TotalTimeUtility.getLatestWorkTypeEntity(workTypeEntityMap,
					workTypeCode, targetDate);
			// 勤務形態エンティティが取得できなかった場合
			if (workTypeEntity == null) {
				// 処理無し
				continue;
			}
			// 無給時短時間を加算(休暇の場合は無給時短時間を加算)
			shortUnpaid += getShortUnpaid(workTypeEntity);
		}
	}
	
	/**
	 * 無給時短時間を取得する。<br>
	 * <br>
	 * @param workTypeEntity 勤務形態エンティティ
	 * @return 無給時短時間
	 */
	protected int getShortUnpaid(WorkTypeEntityInterface workTypeEntity) {
		// 無給時短時間を準備
		int shortTime = 0;
		try {
			// 時短時間1が無給の場合
			if (workTypeEntity.isShort1TimeSet() && !workTypeEntity.isShort1TypePay()) {
				shortTime += TimeUtility.getMinutes(workTypeEntity.getShort1EndTime())
						- TimeUtility.getMinutes(workTypeEntity.getShort1StartTime());
			}
			// 時短時間2が無給の場合
			if (workTypeEntity.isShort2TimeSet() && !workTypeEntity.isShort2TypePay()) {
				shortTime += TimeUtility.getMinutes(workTypeEntity.getShort2EndTime())
						- TimeUtility.getMinutes(workTypeEntity.getShort2StartTime());
			}
		} catch (MospException e) {
			// 処理無し
		}
		// 無給時短時間を取得
		return shortTime;
	}
	
	/**
	 * 週40時間の計算を行う。<br>
	 * @throws MospException  日付の成形に失敗した場合
	 */
	protected void calcWeeklyForty() throws MospException {
		// 対象日リスト(週40時間計算用)を取得
		List<Date> dateListForWeeklyForty = getDateListForWeeklyForty();
		// 週40時間計算用勤怠申請情報リストを準備
		List<AttendanceDtoInterface> weeklyAttendanceList = new ArrayList<AttendanceDtoInterface>();
		// 週の最終曜日の勤怠申請情報を準備
		AttendanceDtoInterface lastDto = null;
		// 計算対象日毎に処理
		for (Date targetDate : dateListForWeeklyForty) {
			// 勤怠申請情報を取得
			AttendanceDtoInterface dto = TotalTimeUtility.getAttendanceDto(attendanceList, targetDate);
			// 週40時間計算用勤怠申請情報リストに設定
			weeklyAttendanceList.add(dto);
			// 対象日が週の終了曜日でも締期間最終日でもない場合
			if (isEndDayOfWeeklyForty(targetDate) == false) {
				// 処理無し(週40時間計算用時間の設定のみ)
				continue;
			}
			// 週40時間の計算
			calcWeeklyForty(weeklyAttendanceList, lastDto);
			// 週の最終曜日の勤怠申請情報を設定
			lastDto = dto;
			// 週40時間計算用時間群をリセット
			weeklyAttendanceList.clear();
		}
	}
	
	/**
	 * 週40時間の計算を行う。<br>
	 * <br>
	 * 以下の時間について、次の順番で計算を行う。<br>
	 * 1.平日勤務時間(内残と賃金割増対象時間を除く)<br>
	 * 2.平日法定時間内残業時間<br>
	 * 3.所定休日勤務時間(内残と賃金割増対象時間を除く)<br>
	 * 4.所定休日法定時間内残業時間<br>
	 * <br>
	 * @param weeklyAttendanceList 週40時間計算用勤怠申請情報リスト
	 * @param lastDto              週初日前日の勤怠申請情報
	 */
	protected void calcWeeklyForty(List<AttendanceDtoInterface> weeklyAttendanceList, AttendanceDtoInterface lastDto) {
		// 勤務時間週計
		int weeklyTotal = 0;
		// 平日時間内時間(平日法定労働時間内残業時間)減分
		int minusWorkdayOverIn = 0;
		// 所定休日時間内時間(所定休日法定労働時間内残業時間)減分
		int minusPrescribedOverIn = 0;
		// 週の最終曜日の勤怠申請情報から対象となる勤務時間を取得
		weeklyTotal += getLastWeeklyTotal(lastDto);
		// 一週間単位(例：日曜～土曜)で計算
		for (AttendanceDtoInterface dto : weeklyAttendanceList) {
			// 勤怠申請情報が週40時間計算対象でない場合
			if (isWeeklyFortyTarget(dto) == false) {
				// 計算対象外
				continue;
			}
			// 平日勤務時間(内残と賃金割増対象時間を除く)を取得
			int workdayNormal = getWorkdayNormalForForty(dto);
			// 平日法定時間内残業時間を取得
			int workdayOverIn = getWorkdayOverInForForty(dto);
			// 所定休日勤務時間(内残と賃金割増対象時間を除く)を取得
			int prescribedNormal = getPrescribedNormalForForty(dto);
			// 所定休日法定時間内残業時間を取得
			int prescribedOverIn = getPrescribedOverInForForty(dto);
			// 勤務日が締期間初日よりも前である場合
			if (dto.getWorkDate().before(cutoffFirstDate)) {
				// 勤務時間週計に対象時間を加算
				weeklyTotal += workdayNormal;
				weeklyTotal += workdayOverIn;
				weeklyTotal += prescribedNormal;
				weeklyTotal += prescribedOverIn;
				// 勤務時間週計加算のみ(前月分は既に計算されているため)
				continue;
			}
			
			// 1.平日勤務時間(内残と賃金割増対象時間を除く)
			// 週40時間超時間(平日勤務時間)を取得
			int workdayNormalForty = getOverForty(workdayNormal, weeklyTotal);
			// 法定労働時間外残業時間を加算
			overtimeOut += workdayNormalForty;
			// 週40時間超勤務時間を加算
			weeklyOverFortyHourWorkTime += workdayNormalForty;
			// 平日時間外時間を加算
			workdayOvertimeOut += workdayNormalForty;
			// 勤務時間週計に平日勤務時間を加算
			weeklyTotal += workdayNormal;
			
			// 2.平日法定時間内残業時間
			// 週40時間超時間(平日法定時間内残業時間)を取得
			int workdayOverInForty = getOverForty(workdayOverIn, weeklyTotal);
			// 法定労働時間外残業時間を加算
			overtimeOut += workdayOverInForty;
			// 週40時間超勤務時間を加算
			weeklyOverFortyHourWorkTime += workdayOverInForty;
			// 平日時間外時間を加算
			workdayOvertimeOut += workdayOverInForty;
			// 平日時間内時間(平日法定労働時間内残業時間)減分を加算
			minusWorkdayOverIn += workdayOverInForty;
			// 勤務時間週計に平日法定時間内残業時間を加算
			weeklyTotal += workdayOverIn;
			
			// 3.所定休日勤務時間(内残と賃金割増対象時間を除く)
			// 週40時間超時間(所定休日勤務時間)を取得
			int prescribedNormalForty = getOverForty(prescribedNormal, weeklyTotal);
			// 法定労働時間外残業時間を加算
			overtimeOut += prescribedNormalForty;
			// 週40時間超勤務時間を加算
			weeklyOverFortyHourWorkTime += prescribedNormalForty;
			// 所定休日時間外時間を加算
			prescribedOvertimeOut += prescribedNormalForty;
			// 勤務時間週計に所定休日勤務時間を加算
			weeklyTotal += prescribedNormal;
			
			// 4.所定休日法定時間内残業時間
			// 週40時間超時間(平日法定時間内残業時間)を取得
			int prescribedOverInForty = getOverForty(prescribedOverIn, weeklyTotal);
			// 法定労働時間外残業時間を加算
			overtimeOut += prescribedOverInForty;
			// 週40時間超勤務時間を加算
			weeklyOverFortyHourWorkTime += prescribedOverInForty;
			// 所定休日時間外時間を加算
			prescribedOvertimeOut += prescribedOverInForty;
			// 所定休日時間内時間(所定休日法定労働時間内残業時間)減分を加算
			minusPrescribedOverIn += prescribedOverInForty;
			// 勤務時間週計に平日法定時間内残業時間を加算
			weeklyTotal += prescribedOverIn;
			
		}
		// 法定内残業時間から週40時間超勤務時間(法定内残業時間分)を除去
		overtimeIn -= minusWorkdayOverIn + minusPrescribedOverIn;
		workdayOvertimeIn -= minusWorkdayOverIn;
		prescribedHolidayOvertimeIn -= minusPrescribedOverIn;
	}
	
	/**
	 * 勤怠トランザクション登録判定情報群を設定する。<br>
	 * <br>
	 * 勤怠が登録されず申請も何もない日は、勤怠トランザクションが
	 * 登録されないため、ここで登録が必要な日を判定する。<br>
	 * <br>
	 */
	protected void setAttendanceTransactionMap() {
		// 勤怠トランザクション登録判定情報
		attendanceTransactionMap = new TreeMap<Date, String>();
		// 計算対象日毎に処理
		for (Date targetDate : targetDateList) {
			// 休職中である場合
			if (HumanUtility.isSuspension(suspensionList, targetDate)) {
				// トランザクション登録対象(出勤区分：休職)
				attendanceTransactionMap.put(targetDate, TimeConst.CODE_ATTENDANCE_TYPE_SUSPENSION);
				continue;
			}
			// 申請エンティティを取得
			RequestEntityInterface entity = getRequestEntity(targetDate);
			// 勤怠申請がされている場合
			if (entity.isAttendanceApplied()) {
				// トランザクション登録不要
				continue;
			}
			// 対象日が全休である場合
			if (entity.isAllHoliday(isCompleted)) {
				// トランザクション登録不要
				continue;
			}
			// 勤務形態コードを取得
			String workTypeCode = entity.getWorkType();
			// 勤務形態コードが所定休日又は法定休日である場合
			if (TimeUtility.isHoliday(workTypeCode)) {
				// 勤怠トランザクション登録対象(所定休日又は法定休日)
				attendanceTransactionMap.put(targetDate, workTypeCode);
			}
		}
	}
	
	/**
	 * 出勤対象日数と出勤実績日数を集計する。<br>
	 * <br>
	 * 勤怠トランザクション及び勤怠トランザクション登録判定情報群から、
	 * 次の値を集計する。<br>
	 * ・出勤実績日数<br>
	 * ・出勤対象日数<br>
	 * <br>
	 */
	protected void totalWorkTimes() {
		// 勤怠トランザクション情報毎に処理
		for (AttendanceTransactionDtoInterface dto : attendanceTransactionSet) {
			// 勤怠トランザクション登録判定情報群に設定されているい場合
			if (attendanceTransactionMap.get(dto.getWorkDate()) != null) {
				// 加算しない
				continue;
			}
			// 出勤実績日数(分子)を加算
			timesAchievement += dto.getNumerator();
			// 出勤対象日数(分母)を加算
			timesTotalWorkDate += dto.getDenominator();
		}
	}
	
	/**
	 * 勤務時間と出勤日数と出勤回数を加算する。<br>
	 * @param entity 申請エンティティ
	 */
	protected void calcWorkTime(RequestEntityInterface entity) {
		// 勤怠情報を取得
		AttendanceDtoInterface dto = entity.getAttendanceDto();
		// 勤務時間を加算
		workTime += dto.getWorkTime();
		// 半休を確認(出勤日数の加算)
		if (entity.isAmHoliday(isCompleted) || entity.isPmHoliday(isCompleted)) {
			// 出勤日数を0.5加算(前半休か後半休の場合)
			timesWorkDate += TimeConst.HOLIDAY_TIMES_HALF;
		} else {
			// 出勤日数を加算
			timesWorkDate++;
		}
		// 出勤回数を加算
		timesWork++;
	}
	
	/**
	 * 休憩時間と外出時間と休出時間及び休出回数を加算する。<br>
	 * <br>
	 * @param entity 申請エンティティ
	 */
	protected void calcHolidayRestTime(RequestEntityInterface entity) {
		// 勤怠情報を取得
		AttendanceDtoInterface dto = entity.getAttendanceDto();
		workOnHoliday += dto.getLegalWorkTime();
		workOnSpecificHoliday += dto.getSpecificWorkTime();
		restWorkOnHoliday += dto.getLegalHolidayRestTime();
		restWorkOnSpecificHoliday += dto.getPrescribedHolidayRestTime();
		// 休日出勤(振替申請しない)である場合
		if (entity.isWorkOnHolidaySubstituteOff(isCompleted)) {
			timesWorkingHoliday++;
		}
		// 法定休日出勤である場合
		if (entity.isWorkOnLegal(isCompleted)) {
			// 法定休日出勤
			legalWorkOnHoliday++;
		}
		// 所定休日出勤である場合
		if (entity.isWorkOnPrescribed(isCompleted)) {
			// 所定休日出勤
			specificWorkOnHoliday++;
		}
		// 勤怠データに直行が設定されている場合
		if (entity.isAttendanceDirectStart()) {
			directStart++;
		}
		// 勤怠データに直帰が設定されている場合
		if (entity.isAttendanceDirectEnd()) {
			directEnd++;
		}
		restTime += dto.getRestTime();
		restLateNight += dto.getNightRestTime();
		publicTime += dto.getPublicTime();
		privateTime += dto.getPrivateTime();
		minutelyHolidayATime += dto.getMinutelyHolidayATime();
		minutelyHolidayBTime += dto.getMinutelyHolidayBTime();
	}
	
	/**
	 * 残業時間(週40時間除く)設定
	 * <br>
	 * @param entity 申請エンティティ
	 */
	protected void calcOvertime(RequestEntityInterface entity) {
		// 勤怠情報を取得
		AttendanceDtoInterface dto = entity.getAttendanceDto();
		// 残業時間を加算
		overtime += dto.getOvertime();
		// 法定内残業時間加算
		overtimeIn += dto.getOvertimeIn();
		// 法定外残業時間加算
		overtimeOut += dto.getOvertimeOut();
		// 平日時間内時間(平日法定労働時間内残業時間)加算
		workdayOvertimeIn += dto.getWorkdayOvertimeIn();
		// 所定休日時間内時間(所定休日法定労働時間内残業時間)加算
		prescribedHolidayOvertimeIn += dto.getPrescribedHolidayOvertimeIn();
		// 平日時間外時間(平日法定労働時間外残業時間)加算
		workdayOvertimeOut += dto.getWorkdayOvertimeOut();
		// 所定休日時間外時間(所定休日法定労働時間外残業時間)加算
		prescribedOvertimeOut += dto.getPrescribedHolidayOvertimeOut();
		// 法定内残業時間(週40時間超除く)加算
		overtimeInNoWeeklyForty += dto.getOvertimeIn();
		// 法定外残業時間(週40時間超除く)加算
		overtimeOutNoWeeklyForty += dto.getOvertimeOut();
		// 平日時間内時間(週40時間超除く)加算
		weekDayOvertimeInNoWeeklyForty += dto.getWorkdayOvertimeIn();
		// 平日時間外時間(週40時間超除く)加算
		weekDayOvertimeOutNoWeeklyForty += dto.getWorkdayOvertimeOut();
		// 残業時間がある場合
		if (dto.getOvertime() > 0) {
			// 残業回数を加算
			timesOvertime++;
		}
	}
	
	/**
	 * 遅刻・早退時間設定
	 * @param attendanceDto 勤怠DTO
	 */
	protected void calcLeaveLateTime(AttendanceDtoInterface attendanceDto) {
		lateNight += attendanceDto.getLateNightTime();
		nightWorkWithinPrescribedWork += attendanceDto.getNightWorkWithinPrescribedWork();
		nightOvertimeWork += attendanceDto.getNightOvertimeWork();
		nightWorkOnHoliday += attendanceDto.getNightWorkOnHoliday();
		decreaseTime += attendanceDto.getDecreaseTime();
		
		if (attendanceDto.getLateTime() > 0) {
			// 遅刻時間が0分を超える場合
			lateDays++;
			lateTime += attendanceDto.getLateTime();
			if (attendanceDto.getLateTime() < TimeConst.TIME_HURF_HOUR_MINUTES) {
				// 遅刻時間が30分未満の場合
				lateLessThanThirtyMinutes++;
				lateLessThanThirtyMinutesTime += attendanceDto.getLateTime();
			} else {
				// 遅刻時間が30分以上の場合
				lateThirtyMinutesOrMore++;
				lateThirtyMinutesOrMoreTime += attendanceDto.getLateTime();
			}
			timesLate++;
		}
		
		if (attendanceDto.getLeaveEarlyTime() > 0) {
			// 早退時間が0分を超える場合
			leaveEarlyDays++;
			leaveEarlyTime += attendanceDto.getLeaveEarlyTime();
			if (attendanceDto.getLeaveEarlyTime() < TimeConst.TIME_HURF_HOUR_MINUTES) {
				// 遅刻時間が30分未満の場合
				leaveEarlyLessThanThirtyMinutes++;
				leaveEarlyLessThanThirtyMinutesTime += attendanceDto.getLeaveEarlyTime();
			} else {
				// 遅刻時間が30分以上の場合
				leaveEarlyThirtyMinutesOrMore++;
				leaveEarlyThirtyMinutesOrMoreTime += attendanceDto.getLeaveEarlyTime();
			}
			timesLeaveEarly++;
		}
	}
	
	/**
	 * 休日労働時間を設定する。<br>
	 * <br>
	 * @param dto 勤怠申請情報
	 */
	protected void calcHolidayWorkTime(AttendanceDtoInterface dto) {
		// 所定労働時間内法定休日労働時間
		statutoryHolidayWorkTimeIn += dto.getStatutoryHolidayWorkTimeIn();
		// 所定労働時間外法定休日労働時間
		statutoryHolidayWorkTimeOut += dto.getStatutoryHolidayWorkTimeOut();
		// 所定労働時間内所定休日労働時間
		prescribedHolidayWorkTimeIn += dto.getPrescribedHolidayWorkTimeIn();
		// 所定労働時間外所定休日労働時間
		prescribedHolidayWorkTimeOut += dto.getPrescribedHolidayWorkTimeOut();
	}
	
	/**
	 * 勤務時間関連設定
	 * @param attendanceDto 勤怠DTO
	 */
	protected void calcContractWorkTime(AttendanceDtoInterface attendanceDto) {
		// 契約勤務時間
		contractWorkTime += getContractWorkTime(attendanceDto);
		// 無給時短時間
		shortUnpaid += attendanceDto.getShortUnpaid();
	}
	
	/**
	 * 契約勤務時間を取得する。<br>
	 * @param dto 対象DTO
	 * @return 契約勤務時間
	 */
	protected int getContractWorkTime(AttendanceDtoInterface dto) {
		int contractWorkTime = dto.getWorkTime() - dto.getOvertime() - dto.getLegalWorkTime();
		if (TimeConst.CODE_TARDINESS_WHY_TRAIN.equals(dto.getLateReason())
				|| TimeConst.CODE_TARDINESS_WHY_COMPANY.equals(dto.getLateReason())) {
			// 遅刻理由が電車遅延又は会社指示の場合は実遅刻時間を加算
			contractWorkTime += dto.getActualLateTime();
		}
		if (TimeConst.CODE_LEAVEEARLY_WHY_COMPANY.equals(dto.getLeaveEarlyReason())) {
			// 早退理由が会社指示の場合は実早退時間を加算
			contractWorkTime += dto.getActualLeaveEarlyTime();
		}
		return contractWorkTime;
	}
	
	@Override
	public void calcExtraOverTime() {
		// 45時間及び60時間を分で取得
		int fortyFive = LEGAL_OVERTIME_WORK * TimeConst.CODE_DEFINITION_HOUR;
		int sixty = TimeConst.CODE_DEFINITION_HOUR * TimeConst.CODE_DEFINITION_HOUR;
		// 60時間超残業時間を取得(マイナスの場合は0)
		sixtyHourOvertime = overtimeOut - sixty;
		sixtyHourOvertime = sixtyHourOvertime < 0 ? 0 : sixtyHourOvertime;
		// 45時間超残業時間を取得(マイナスの場合は0)
		fortyFiveHourOvertime = overtimeOut - fortyFive - sixtyHourOvertime;
		fortyFiveHourOvertime = fortyFiveHourOvertime < 0 ? 0 : fortyFiveHourOvertime;
	}
	
	/**
	 * 休暇回数群(キー：休暇コード)に休暇回数を加算する。<br>
	 * <br>
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @param days        休暇日数
	 * @param hours       休暇時間数
	 */
	protected void addHolidayTimes(String holidayCode, int holidayType, float days, int hours) {
		// 休暇回数群を準備
		Map<String, Float> daysMap = null;
		Map<String, Integer> hoursMap = null;
		// 休暇区分を確認
		switch (holidayType) {
			case TimeConst.CODE_HOLIDAYTYPE_SPECIAL:
				// 特別休暇の場合
				daysMap = specialHolidayDays;
				hoursMap = specialHolidayHours;
				break;
			case TimeConst.CODE_HOLIDAYTYPE_OTHER:
				// その他休暇の場合
				daysMap = otherHolidayDays;
				hoursMap = otherHolidayHours;
				break;
			case TimeConst.CODE_HOLIDAYTYPE_ABSENCE:
				// 欠勤の場合
				daysMap = absenceDays;
				hoursMap = absenceHours;
				break;
			default:
				// 処理無し(休暇区分がその他の場合)
				return;
		}
		// 合計回数を取得
		Float totalDays = daysMap.get(holidayCode);
		Integer totalHours = hoursMap.get(holidayCode);
		// 合計日数が取得できない場合
		if (totalDays == null) {
			// 合計日数を初期化
			totalDays = 0F;
		}
		// 合計時間数が取得できない場合
		if (totalHours == null) {
			// 合計時間数を初期化
			totalHours = 0;
		}
		// 加算して休暇回数群に設定
		daysMap.put(holidayCode, totalDays + days);
		hoursMap.put(holidayCode, totalHours + hours);
	}
	
	/**
	 * 休暇日数を集計する。<br>
	 * <br>
	 * 休暇区分毎に作成された休暇日数群(キー：休暇コード)の日数を合計する。<br>
	 * <br>
	 * @param map 休暇日数群(キー：休暇コード)
	 * @return 休暇日数
	 */
	protected float totalHolidayDays(Map<String, Float> map) {
		// 集計値を準備
		float total = 0F;
		// 休暇回数毎に処理
		for (float times : map.values()) {
			// 加算
			total += times;
		}
		// 休暇回数集計値を取得
		return total;
	}
	
	/**
	 * 休暇時間数を集計する。<br>
	 * <br>
	 * 休暇区分毎に作成された休暇時間数群(キー：休暇コード)の時間数を合計する。<br>
	 * <br>
	 * @param map 休暇時間数群(キー：休暇コード)
	 * @return 休暇時間数
	 */
	protected int totalHolidayHours(Map<String, Integer> map) {
		// 集計値を準備
		int total = 0;
		// 休暇時間数毎に処理
		for (int times : map.values()) {
			// 加算
			total += times;
		}
		// 休暇時間数集計値を取得
		return total;
	}
	
	/**
	 * 代休が期限切れであるかを確認する。<br>
	 * <br>
	 * 期限日が締期間に含まれている場合、期限切れであると判断する。<br>
	 * <br>
	 * @param dto 代休情報
	 * @return 確認結果(true：代休が期限切れである、false：そうでない)
	 */
	protected boolean isSubHolidayExpired(SubHolidayDtoInterface dto) {
		// 勤務日を取得
		Date targetDate = dto.getWorkDate();
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = subHolidayTimeSettingMap.get(targetDate);
		// 勤怠設定情報が取得できない場合(締期間より前の代休データの場合)
		if (timeSettingDto == null) {
			// 締め期間内で取得
			timeSettingDto = timeSettingMap.get(targetDate);
			if (timeSettingDto == null) {
				// 締期間初日で勤怠設定情報を取得
				timeSettingDto = timeSettingMap.get(cutoffFirstDate);
			}
		}
		// 勤怠設定情報が取得できない場合
		if (timeSettingDto == null) {
			// 期限切れであると判断
			return true;
		}
		// 代休取得期限を取得
		int limitMonth = timeSettingDto.getSubHolidayLimitMonth();
		int limitDay = timeSettingDto.getSubHolidayLimitDate();
		// 勤務日に代休取得期限月を加算
		Date expireDate = DateUtility.addMonth(targetDate, limitMonth);
		// 期限日(代休取得期限日を加算)を取得
		expireDate = DateUtility.addDay(expireDate, limitDay);
		// 期限日が締期間に含まれているかを確認
		return DateUtility.isTermContain(expireDate, cutoffFirstDate, cutoffLastDate);
	}
	
	/**
	 * 対象日が週の終了曜日か締期間最終日であるかを確認する。<br>
	 * <br>
	 * 週40時間の計算に用いる。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 確認結果(true：週の終了曜日か締期間最終日である、false：そうでない)
	 */
	protected boolean isEndDayOfWeeklyForty(Date targetDate) {
		// 対象日が締期間最終日である場合
		if (targetDate.compareTo(cutoffLastDate) == 0) {
			// 締期間最終日であると判断
			return true;
		}
		// 対象日が週の終了曜日であるかを確認
		return isEndDayofWeek(targetDate);
	}
	
	/**
	 * 対象日が週の終了曜日であるかを確認する。<br>
	 * <br>
	 * 勤怠設定情報から週の起算曜日を取得し、確認する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 確認結果(true：週の終了曜日である、false：そうでない)
	 */
	protected boolean isEndDayofWeek(Date targetDate) {
		// 勤怠設定情報を取得
		TimeSettingDtoInterface dto = timeSettingMap.get(targetDate);
		// 勤怠設定が取得できなかった場合
		if (dto == null) {
			// 週の終了曜日でないと判断
			return false;
		}
		// 週の終了曜日を取得
		int endDayOfWeek = getEndDayOfWeek(dto.getStartWeek());
		// 対象日が週の終了曜日であるかを確認
		return DateUtility.isDayOfWeek(targetDate, endDayOfWeek);
	}
	
	/**
	 * 週の終了曜日を取得する。<br>
	 * <br>
	 * @param startDayOfWeek 週の開始曜日を示すフィールド値
	 * @return 週の終了曜日を示すフィールド値
	 */
	protected int getEndDayOfWeek(int startDayOfWeek) {
		// 週の開始曜日が日曜日の場合
		if (startDayOfWeek == Calendar.SUNDAY) {
			// 土曜日を取得
			return Calendar.SATURDAY;
		}
		// 週の開始曜日を減算
		return --startDayOfWeek;
	}
	
	/**
	 * 対象日リスト(週40時間計算用)を取得する。<br>
	 * <br>
	 * 締期間の初日の一日前から最終日のリストを取得する。<br>
	 * 但し、締期間初日が含まれる週の起算曜日が前月の場合、
	 * その週の起算曜日の一日前から最終日までとする。<br>
	 * <br>
	 * 週の起算曜日の前日が法定休日出勤である場合を考慮し、
	 * 起算曜日の一日前を週40時間計算用対象日の初日とする。<br>
	 * <br>
	 * @return 対象日リスト(週40時間計算用)
	 */
	protected List<Date> getDateListForWeeklyForty() {
		// 最初の週の起算日(締期間初日が含まれる週の起算日)の一日前を取得
		Date firstDate = getFirstDateForWeeklyForty();
		// 対象日リストを取得(最初の週の起算日から締期間最終日)
		return TimeUtility.getDateList(firstDate, cutoffLastDate);
	}
	
	/**
	 * 最初の週の起算日(締期間初日が含まれる週の起算日)の一日前を取得する。<br>
	 * <br>
	 * @return 最初の週の起算日(締期間初日が含まれる週の起算日)の一日前
	 */
	protected Date getFirstDateForWeeklyForty() {
		// 締期間の対象日リストを取得
		List<Date> targetDateList = TimeUtility.getDateList(cutoffFirstDate, cutoffLastDate);
		// 対象日毎に処理
		for (Date targetDate : targetDateList) {
			// 対象日が週の終了曜日である場合
			if (isEndDayofWeek(targetDate)) {
				// 7日前を取得
				return DateUtility.addDay(targetDate, TotalTimeEntityReferenceBean.DAYS_FORMER_ATTENDANCE);
			}
		}
		// 締期間初日を取得(最初の週の最終日が取得できなかった場合)
		return CapsuleUtility.getDateClone(cutoffFirstDate);
	}
	
	/**
	 * 平日勤務時間(内残と賃金割増対象時間を除く)を取得する。<br>
	 * <br>
	 * 週40時間の計算で用いる。<br>
	 * <br>
	 * @param dto 勤怠申請情報
	 * @return 平日勤務時間(内残と賃金割増対象時間を除く)
	 */
	protected int getWorkdayNormalForForty(AttendanceDtoInterface dto) {
		// 勤務時間を取得
		int minutes = dto.getWorkTime();
		// 法定休日勤務時間を除去
		minutes -= dto.getLegalWorkTime();
		// 所定休日勤務時間を除去
		minutes -= dto.getSpecificWorkTime();
		// 平日法定時間内残業時間を除去
		minutes -= dto.getWorkdayOvertimeIn();
		// 平日法定時間外残業時間を除去
		minutes -= dto.getWorkdayOvertimeOut();
		// 平日勤務時間(内残と賃金割増対象時間を除く)を取得
		return minutes;
	}
	
	/**
	 * 平日法定時間内残業時間を取得する。<br>
	 * <br>
	 * 週40時間の計算で用いる。<br>
	 * <br>
	 * @param dto 勤怠申請情報
	 * @return 平日法定時間内残業時間
	 */
	protected int getWorkdayOverInForForty(AttendanceDtoInterface dto) {
		// 平日法定時間内残業時間を取得
		return dto.getWorkdayOvertimeIn();
	}
	
	/**
	 * 所定休日勤務時間(内残と賃金割増対象時間を除く)を取得する。<br>
	 * <br>
	 * 週40時間の計算で用いる。<br>
	 * <br>
	 * 所定休日時間を賃金割増対象としている場合は、当メソッドを上書きして
	 * 0を返すようにすることで、週40時間の計算で考慮されなくなる。<br>
	 * <br>
	 * @param dto 勤怠申請情報
	 * @return 所定休日勤務時間(内残と賃金割増対象時間を除く)
	 */
	protected int getPrescribedNormalForForty(AttendanceDtoInterface dto) {
		// 所定休日勤務時間を取得
		int minutes = dto.getSpecificWorkTime();
		// 所定休日法定時間内残業時間を除去
		minutes -= dto.getPrescribedHolidayOvertimeIn();
		// 所定休日法定時間外残業時間を除去
		minutes -= dto.getPrescribedHolidayOvertimeOut();
		// 所定休日勤務時間(内残と賃金割増対象時間を除く)を取得
		return minutes;
	}
	
	/**
	 * 所定休日法定時間内残業時間を取得する。<br>
	 * <br>
	 * 週40時間の計算で用いる。<br>
	 * <br>
	 * 所定休日時間を賃金割増対象としている場合は、当メソッドを上書きして
	 * 0を返すようにすることで、週40時間の計算で考慮されなくなる。<br>
	 * <br>
	 * @param dto 勤怠申請情報
	 * @return 所定休日法定時間内残業時間
	 */
	protected int getPrescribedOverInForForty(AttendanceDtoInterface dto) {
		// 所定休日法定時間内残業時間を取得
		return dto.getPrescribedHolidayOvertimeIn();
	}
	
	/**
	 * 週40時間超時間を取得する。<br>
	 * <br>
	 * 週40時間の計算で用いる。<br>
	 * <br>
	 * @param minutes             対象時間(分)
	 * @param weeklyTotalWorkTime 勤務時間週計(分)
	 * @return 週40時間超時間
	 */
	protected int getOverForty(int minutes, int weeklyTotalWorkTime) {
		// 40時間を分で取得
		int forty = TimeUtility.getLegalWeeklyWorkTime(mospParams);
		// 勤務時間週計(分)が40時間を超えている場合
		if (forty < weeklyTotalWorkTime) {
			// 対象時間(分)を取得
			return minutes;
		}
		// 週40時間超時間(対象時間(分)と勤務時間週計(分)を合計し40時間を減算)を取得
		int overForty = minutes + weeklyTotalWorkTime - forty;
		// 週40時間超時間を取得(マイナスなら0)
		return overForty < 0 ? 0 : overForty;
	}
	
	/**
	 * 勤怠申請情報が週40時間計算対象であるかを確認する。<br>
	 * <br>
	 * 勤怠申請情報が法定休日であり週の終了曜日である場合、
	 * 24時以降の勤怠は翌週に回すため、週40時間計算対象外とする。<br>
	 * <br>
	 * @param dto 勤怠申請情報
	 * @return 確認結果(true：勤怠申請情報が週40時間計算対象である、そうでない)
	 */
	protected boolean isWeeklyFortyTarget(AttendanceDtoInterface dto) {
		// 勤怠申請情報が存在しない場合
		if (dto == null) {
			// 週40時間計算対象外と判断
			return false;
		}
		// 勤怠申請情報が法定休日出勤である場合
		if (TimeUtility.isWorkOnLegalHoliday(dto.getWorkTypeCode())) {
			// 週の終了曜日である場合
			if (isEndDayofWeek(dto.getWorkDate())) {
				// 週40時間計算対象外と判断
				return false;
			}
		}
		// 週40時間計算対象と判断
		return true;
	}
	
	/**
	 * 週の最終曜日の勤怠申請情報から対象となる勤務時間を取得する。<br>
	 * <br>
	 * 週の最終曜日の勤怠が法定休日出勤である場合、次の合計値を取得する。<br>
	 * ・平日勤務時間(内残と賃金割増対象時間を除く)<br>
	 * ・平日法定時間内残業時間<br>
	 * ・所定休日勤務時間(内残と賃金割増対象時間を除く)<br>
	 * ・所定休日法定時間内残業時間<br>
	 * <br>
	 * @param dto 週の最終曜日の勤怠申請情報
	 * @return 対象となる勤務時間
	 */
	protected int getLastWeeklyTotal(AttendanceDtoInterface dto) {
		// 勤怠申請情報が存在しない場合
		if (dto == null) {
			// 対象となる勤務時間は無し
			return 0;
		}
		// 勤怠申請情報が法定休日でない場合
		if (TimeUtility.isWorkOnLegalHoliday(dto.getWorkTypeCode()) == false) {
			// 対象となる勤務時間は無し
			return 0;
		}
		// 対象となる勤務時間を準備
		int lastWeeklyTotal = 0;
		// 平日勤務時間(内残と賃金割増対象時間を除く)を加算
		lastWeeklyTotal += getWorkdayNormalForForty(dto);
		// 平日法定時間内残業時間を加算
		lastWeeklyTotal += getWorkdayOverInForForty(dto);
		// 所定休日勤務時間(内残と賃金割増対象時間を除く)を加算
		lastWeeklyTotal += getPrescribedNormalForForty(dto);
		// 所定休日法定時間内残業時間を加算
		lastWeeklyTotal += getPrescribedOverInForForty(dto);
		// 対象となる勤務時間を加算
		return lastWeeklyTotal;
	}
	
	/**
	 * 汎用項目を集計する。<br>
	 * アドオンで集計値を調整する場合等にも、用いる。<br>
	 */
	protected void totalGeneralItem() {
		// 各アドオンで実装する
	}
	
	/**
	 * 項目初期化
	 */
	protected void setInitFileds() {
		workTime = 0;
		contractWorkTime = 0;
		timesWorkDate = 0;
		timesWork = 0;
		legalWorkOnHoliday = 0;
		specificWorkOnHoliday = 0;
		timesAchievement = 0;
		timesTotalWorkDate = 0;
		directStart = 0;
		directEnd = 0;
		restTime = 0;
		restLateNight = 0;
		restWorkOnSpecificHoliday = 0;
		restWorkOnHoliday = 0;
		publicTime = 0;
		privateTime = 0;
		minutelyHolidayATime = 0;
		minutelyHolidayBTime = 0;
		overtime = 0;
		overtimeIn = 0;
		overtimeOut = 0;
		lateNight = 0;
		nightWorkWithinPrescribedWork = 0;
		nightOvertimeWork = 0;
		nightWorkOnHoliday = 0;
		workOnSpecificHoliday = 0;
		workOnHoliday = 0;
		decreaseTime = 0;
		fortyFiveHourOvertime = 0;
		timesOvertime = 0;
		timesWorkingHoliday = 0;
		lateDays = 0;
		lateThirtyMinutesOrMore = 0;
		lateLessThanThirtyMinutes = 0;
		lateTime = 0;
		lateThirtyMinutesOrMoreTime = 0;
		lateLessThanThirtyMinutesTime = 0;
		timesLate = 0;
		leaveEarlyDays = 0;
		leaveEarlyThirtyMinutesOrMore = 0;
		leaveEarlyLessThanThirtyMinutes = 0;
		leaveEarlyTime = 0;
		leaveEarlyThirtyMinutesOrMoreTime = 0;
		leaveEarlyLessThanThirtyMinutesTime = 0;
		timesLeaveEarly = 0;
		timesHoliday = 0;
		timesLegalHoliday = 0;
		timesSpecificHoliday = 0;
		timesPaidHoliday = 0;
		paidHolidayHour = 0;
		timesStockHoliday = 0;
		timesCompensation = 0;
		timesLegalCompensation = 0;
		timesSpecificCompensation = 0;
		timesLateCompensation = 0;
		timesHolidaySubstitute = 0;
		timesLegalHolidaySubstitute = 0;
		timesSpecificHolidaySubstitute = 0;
		totalSpecialHoliday = 0D;
		specialHolidayHour = 0;
		totalOtherHoliday = 0D;
		otherHolidayHour = 0;
		totalAbsence = 0D;
		absenceHour = 0;
		totalAllowance = 0;
		sixtyHourOvertime = 0;
		workdayOvertimeIn = 0;
		prescribedHolidayOvertimeIn = 0;
		workdayOvertimeOut = 0;
		prescribedOvertimeOut = 0;
		timesAlternative = 0;
		legalCompensationOccurred = 0;
		specificCompensationOccurred = 0;
		lateCompensationOccurred = 0;
		legalCompensationUnused = 0;
		specificCompensationUnused = 0;
		lateCompensationUnused = 0;
		statutoryHolidayWorkTimeIn = 0;
		statutoryHolidayWorkTimeOut = 0;
		prescribedHolidayWorkTimeIn = 0;
		prescribedHolidayWorkTimeOut = 0;
		shortUnpaid = 0;
		weeklyOverFortyHourWorkTime = 0;
		overtimeInNoWeeklyForty = 0;
		overtimeOutNoWeeklyForty = 0;
		weekDayOvertimeInNoWeeklyForty = 0;
		weekDayOvertimeOutNoWeeklyForty = 0;
		generalIntItem1 = 0;
		generalIntItem2 = 0;
		generalIntItem3 = 0;
		generalIntItem4 = 0;
		generalIntItem5 = 0;
		generalDoubleItem1 = 0;
		generalDoubleItem2 = 0;
		generalDoubleItem3 = 0;
		generalDoubleItem4 = 0;
		generalDoubleItem5 = 0;
		requestEntityMap = new HashMap<Date, RequestEntityInterface>();
		generalMap = new HashMap<String, Object>();
	}
	
	@Override
	public String getScheduledWorkTypeCode(Date targetDate) {
		// 振出・休出勤務形態コードを取得
		String substituteWork = substitutedMap.get(targetDate);
		// 振出・休出勤務形態コードがある場合
		if (substituteWork != null && substituteWork.isEmpty() == false) {
			// 振出・休出勤務形態コードを取得
			return substituteWork;
		}
		// 振替日の申請エンティティを取得
		RequestEntityInterface entity = getRequestEntity(targetDate);
		// 振替休日情報(全休or前半休+後半休)の休暇種別(法定休日、所定休日)を取得
		String substituteType = entity.getSubstituteType(isCompleted);
		// 休暇種別(法定休日、所定休日)がある場合
		if (substituteType.isEmpty() == false) {
			// 休暇種別(法定休日、所定休日)を取得
			return substituteType;
		}
		// 予定勤務形態コードを取得
		return scheduleMap.get(targetDate);
	}
	
	@Override
	public RequestEntityInterface getRequestEntity(Date targetDate) {
		// 申請エンティティを取得
		RequestEntityInterface entity = requestEntityMap.get(targetDate);
		// 申請エンティティが取得できた場合
		if (entity != null) {
			// 申請エンティティを取得
			return entity;
		}
		// 申請エンティティを再取得
		entity = new RequestEntity();
		entity.setPersonalId(personalId);
		entity.setTargetDate(targetDate);
		// 申請エンティティ群に設定
		requestEntityMap.put(targetDate, entity);
		// 申請エンティティに各種情報を設定
		entity.setAttendanceDto(TotalTimeUtility.getAttendanceDto(attendanceList, targetDate));
		entity.setWorkOnHolidayRequestDto(
				TotalTimeUtility.getWorkOnHolidayRequestDto(workOnHolidayRequestList, workflowMap, targetDate));
		entity
			.setHolidayRequestList(TotalTimeUtility.getHolidayRequestList(holidayRequestList, workflowMap, targetDate));
		entity.setSubHolidayRequestList(
				TotalTimeUtility.getSubHolidayRequestList(subHolidayRequestList, workflowMap, targetDate));
		entity.setOverTimeRequestList(
				TotalTimeUtility.getOvertimeRequestList(overtimeRequestList, workflowMap, targetDate));
		entity.setWorkTypeChangeRequestDto(
				TotalTimeUtility.getWorkTypeChangeRequestList(workTypeChangeRequestList, workflowMap, targetDate));
		entity.setDifferenceRequestDto(
				TotalTimeUtility.getDifferenceRequestDto(differenceRequestList, workflowMap, targetDate));
		entity.setSubstituteList(TotalTimeUtility.getSubstitutList(substitubeList, workflowMap, targetDate));
		entity.setWorkflowMap(workflowMap);
		entity.setScheduledWorkTypeCode(scheduleMap.get(targetDate));
		entity.setSubstitutedWorkTypeCode(substitutedMap.get(targetDate));
		// 申請エンティティを取得
		return entity;
	}
	
	/**
	 * ワークフロー情報を取得する。<br>
	 * <br>
	 * @param workflow ワークフロー番号
	 * @return ワークフロー情報
	 */
	@Override
	public WorkflowDtoInterface getWorkflowDto(long workflow) {
		return workflowMap.get(workflow);
	}
	
	/**
	 * 休暇申請を考慮した日数を取得
	 * @param dto 休暇申請DTO
	 * @return 休暇日数
	 */
	protected float getHolidayRequestCount(HolidayRequestDtoInterface dto) {
		// 回数を準備
		float days = 0F;
		
		// 休暇期間日リストを取得
		List<Date> holidayDateList = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		// 休暇期間日毎に処理
		for (Date targetDate : holidayDateList) {
			// 締期間に含まれない場合
			if (DateUtility.isTermContain(targetDate, cutoffFirstDate, cutoffLastDate) == false) {
				// 処理無し(カウント期間外)
				continue;
			}
			// 対象日の対象日の予定勤務形態コードを取得
			String workTypeCode = getScheduledWorkTypeCode(targetDate);
			// 対象日の予定勤務形態コードが取得できない場合
			if (workTypeCode == null) {
				// 処理無し(カウント対象外)
				continue;
			}
			// 所定休日又は法定休日である場合
			if (TimeUtility.isHoliday(workTypeCode)) {
				// 処理無し(カウント対象外)
				continue;
			}
			// 法定休日出勤又は所定休日出勤である場合
			if (TimeUtility.isWorkOnLegalHoliday(workTypeCode) || TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
				// 処理無し(カウント対象外)
				continue;
			}
			// 回数をカウント
			days++;
		}
		
		return days;
	}
	
	@Override
	public void setMospParams(MospParams mospParams) {
		this.mospParams = mospParams;
	}
	
	/**------------- 計算情報(変数) -------------**/
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public int getCalculationYear() {
		return calculationYear;
	}
	
	@Override
	public void setCalculationYear(int calculationYear) {
		this.calculationYear = calculationYear;
	}
	
	@Override
	public int getCalculationMonth() {
		return calculationMonth;
	}
	
	@Override
	public void setCalculationMonth(int calculationMonth) {
		this.calculationMonth = calculationMonth;
	}
	
	@Override
	public Set<HolidayDtoInterface> getHolidaySet() {
		return holidaySet;
	}
	
	@Override
	public void setHolidaySet(Set<HolidayDtoInterface> holidaySet) {
		this.holidaySet = holidaySet;
	}
	
	@Override
	public String getCutoffCode() {
		return cutoffCode;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public Date getCutoffFirstDate() {
		return CapsuleUtility.getDateClone(cutoffFirstDate);
	}
	
	@Override
	public void setCutoffFirstDate(Date cutoffFirstDate) {
		this.cutoffFirstDate = CapsuleUtility.getDateClone(cutoffFirstDate);
	}
	
	@Override
	public Date getCutoffLastDate() {
		return CapsuleUtility.getDateClone(cutoffLastDate);
	}
	
	@Override
	public void setCutoffLastDate(Date cutoffLastDate) {
		this.cutoffLastDate = CapsuleUtility.getDateClone(cutoffLastDate);
	}
	
	@Override
	public List<Date> getTargetDateList() {
		return targetDateList;
	}
	
	@Override
	public void setTargetDateList(List<Date> targetDateList) {
		this.targetDateList = targetDateList;
	}
	
	@Override
	public List<SuspensionDtoInterface> getSuspensionList() {
		return suspensionList;
	}
	
	@Override
	public void setSuspensionList(List<SuspensionDtoInterface> suspensionList) {
		this.suspensionList = suspensionList;
	}
	
	@Override
	public Map<Date, ApplicationDtoInterface> getApplicationMap() {
		return applicationMap;
	}
	
	@Override
	public void setApplicationMap(Map<Date, ApplicationDtoInterface> applicationMap) {
		this.applicationMap = applicationMap;
	}
	
	@Override
	public Map<Date, TimeSettingDtoInterface> getTimeSettingMap() {
		return timeSettingMap;
	}
	
	@Override
	public void setTimeSettingMap(Map<Date, TimeSettingDtoInterface> timeSettingMap) {
		this.timeSettingMap = timeSettingMap;
	}
	
	@Override
	public Map<Date, String> getScheduleMap() {
		return scheduleMap;
	}
	
	@Override
	public void setScheduleMap(Map<Date, String> scheduleMap) {
		this.scheduleMap = scheduleMap;
	}
	
	@Override
	public Map<Date, String> getSubstitutedMap() {
		return substitutedMap;
	}
	
	@Override
	public void setSubstitutedMap(Map<Date, String> substitutedMap) {
		this.substitutedMap = substitutedMap;
	}
	
	@Override
	public List<AttendanceDtoInterface> getAttendanceList() {
		return attendanceList;
	}
	
	@Override
	public void setAttendanceList(List<AttendanceDtoInterface> attendanceList) {
		this.attendanceList = attendanceList;
	}
	
	@Override
	public List<WorkOnHolidayRequestDtoInterface> getWorkOnHolidayRequestList() {
		return workOnHolidayRequestList;
	}
	
	@Override
	public void setWorkOnHolidayRequestList(List<WorkOnHolidayRequestDtoInterface> workOnHolidayRequestList) {
		this.workOnHolidayRequestList = workOnHolidayRequestList;
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayRequestList() {
		return holidayRequestList;
	}
	
	@Override
	public void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList) {
		this.holidayRequestList = holidayRequestList;
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList() {
		return subHolidayRequestList;
	}
	
	@Override
	public void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList) {
		this.subHolidayRequestList = subHolidayRequestList;
	}
	
	@Override
	public List<OvertimeRequestDtoInterface> getOvertimeRequestList() {
		return overtimeRequestList;
	}
	
	@Override
	public void setOvertimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList) {
		this.overtimeRequestList = overtimeRequestList;
	}
	
	@Override
	public List<WorkTypeChangeRequestDtoInterface> getWorkTypeChangeRequestList() {
		return workTypeChangeRequestList;
	}
	
	@Override
	public void setWorkTypeChangeRequestList(List<WorkTypeChangeRequestDtoInterface> workTypeChangeRequestList) {
		this.workTypeChangeRequestList = workTypeChangeRequestList;
	}
	
	@Override
	public List<DifferenceRequestDtoInterface> getDifferenceRequestList() {
		return differenceRequestList;
	}
	
	@Override
	public void setDifferenceRequestList(List<DifferenceRequestDtoInterface> differenceRequestList) {
		this.differenceRequestList = differenceRequestList;
	}
	
	@Override
	public List<SubstituteDtoInterface> getSubstitubeList() {
		return substitubeList;
	}
	
	@Override
	public void setSubstitubeList(List<SubstituteDtoInterface> substitubeList) {
		this.substitubeList = substitubeList;
	}
	
	@Override
	public List<SubHolidayDtoInterface> getSubHolidayList() {
		return subHolidayList;
	}
	
	@Override
	public void setSubHolidayList(List<SubHolidayDtoInterface> subHolidayList) {
		this.subHolidayList = subHolidayList;
	}
	
	@Override
	public Map<Date, TimeSettingDtoInterface> getSubHolidayTimeSettingMap() {
		return subHolidayTimeSettingMap;
	}
	
	@Override
	public void setSubHolidayTimeSettingMap(Map<Date, TimeSettingDtoInterface> subHolidayTimeSettingMap) {
		this.subHolidayTimeSettingMap = subHolidayTimeSettingMap;
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> getWorkflowMap() {
		return workflowMap;
	}
	
	@Override
	public void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap) {
		this.workflowMap = workflowMap;
	}
	
	@Override
	public Set<AttendanceTransactionDtoInterface> getAttendanceTransactionSet() {
		return attendanceTransactionSet;
	}
	
	@Override
	public void setAttendanceTransactionSet(Set<AttendanceTransactionDtoInterface> attendanceTransactionSet) {
		this.attendanceTransactionSet = attendanceTransactionSet;
	}
	
	@Override
	public Map<String, List<WorkTypeEntityInterface>> getWorkTypeEntityMap() {
		return workTypeEntityMap;
	}
	
	@Override
	public void setWorkTypeEntityMap(Map<String, List<WorkTypeEntityInterface>> workTypeEntityMap) {
		this.workTypeEntityMap = workTypeEntityMap;
	}
	
	@Override
	public Map<Date, String> getAttendanceTransactionMap() {
		return attendanceTransactionMap;
	}
	
	/**------------- 変数(勤怠集計後) -------------**/
	
	@Override
	public int getWorkTime() {
		return workTime;
	}
	
	@Override
	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	
	@Override
	public int getContractWorkTime() {
		return contractWorkTime;
	}
	
	@Override
	public void setContractWorkTime(int contractWorkTime) {
		this.contractWorkTime = contractWorkTime;
	}
	
	@Override
	public double getTimesWorkDate() {
		return timesWorkDate;
	}
	
	@Override
	public void setTimesWorkDate(double timesWorkDate) {
		this.timesWorkDate = timesWorkDate;
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
	@Override
	public double getLegalWorkOnHoliday() {
		return legalWorkOnHoliday;
	}
	
	@Override
	public void setLegalWorkOnHoliday(double legalWorkOnHoliday) {
		this.legalWorkOnHoliday = legalWorkOnHoliday;
	}
	
	@Override
	public double getSpecificWorkOnHoliday() {
		return specificWorkOnHoliday;
	}
	
	@Override
	public void setSpecificWorkOnHoliday(double specificWorkOnHoliday) {
		this.specificWorkOnHoliday = specificWorkOnHoliday;
	}
	
	@Override
	public int getTimesAchievement() {
		return timesAchievement;
	}
	
	@Override
	public void setTimesAchievement(int timesAchievement) {
		this.timesAchievement = timesAchievement;
	}
	
	@Override
	public int getTimesTotalWorkDate() {
		return timesTotalWorkDate;
	}
	
	@Override
	public void setTimesTotalWorkDate(int timesTotalWorkDate) {
		this.timesTotalWorkDate = timesTotalWorkDate;
	}
	
	@Override
	public int getDirectStart() {
		return directStart;
	}
	
	@Override
	public void setDirectStart(int directStart) {
		this.directStart = directStart;
	}
	
	@Override
	public int getDirectEnd() {
		return directEnd;
	}
	
	@Override
	public void setDirectEnd(int directEnd) {
		this.directEnd = directEnd;
	}
	
	@Override
	public int getRestTime() {
		return restTime;
	}
	
	@Override
	public void setRestTime(int restTime) {
		this.restTime = restTime;
	}
	
	@Override
	public int getRestLateNight() {
		return restLateNight;
	}
	
	@Override
	public void setRestLateNight(int restLateNight) {
		this.restLateNight = restLateNight;
	}
	
	@Override
	public int getRestWorkOnSpecificHoliday() {
		return restWorkOnSpecificHoliday;
	}
	
	@Override
	public void setRestWorkOnSpecificHoliday(int restWorkOnSpecificHoliday) {
		this.restWorkOnSpecificHoliday = restWorkOnSpecificHoliday;
	}
	
	@Override
	public int getRestWorkOnHoliday() {
		return restWorkOnHoliday;
	}
	
	@Override
	public void setRestWorkOnHoliday(int restWorkOnHoliday) {
		this.restWorkOnHoliday = restWorkOnHoliday;
	}
	
	@Override
	public int getPublicTime() {
		return publicTime;
	}
	
	@Override
	public void setPublicTime(int publicTime) {
		this.publicTime = publicTime;
	}
	
	@Override
	public int getPrivateTime() {
		return privateTime;
	}
	
	@Override
	public void setPrivateTime(int privateTime) {
		this.privateTime = privateTime;
	}
	
	@Override
	public int getMinutelyHolidayATime() {
		return minutelyHolidayATime;
	}
	
	@Override
	public void setMinutelyHolidayATime(int minutelyHolidayATime) {
		this.minutelyHolidayATime = minutelyHolidayATime;
	}
	
	@Override
	public int getMinutelyHolidayBTime() {
		return minutelyHolidayBTime;
	}
	
	@Override
	public void setMinutelyHolidayBTime(int minutelyHolidayBTime) {
		this.minutelyHolidayBTime = minutelyHolidayBTime;
	}
	
	@Override
	public int getOvertime() {
		return overtime;
	}
	
	@Override
	public void setOvertime(int overtime) {
		this.overtime = overtime;
	}
	
	@Override
	public int getOvertimeIn() {
		return overtimeIn;
	}
	
	@Override
	public void setOvertimeIn(int overtimeIn) {
		this.overtimeIn = overtimeIn;
	}
	
	@Override
	public int getOvertimeOut() {
		return overtimeOut;
	}
	
	@Override
	public void setOvertimeOut(int overtimeOut) {
		this.overtimeOut = overtimeOut;
	}
	
	@Override
	public int getLateNight() {
		return lateNight;
	}
	
	@Override
	public void setLateNight(int lateNight) {
		this.lateNight = lateNight;
	}
	
	@Override
	public int getNightWorkWithinPrescribedWork() {
		return nightWorkWithinPrescribedWork;
	}
	
	@Override
	public void setNightWorkWithinPrescribedWork(int nightWorkWithinPrescribedWork) {
		this.nightWorkWithinPrescribedWork = nightWorkWithinPrescribedWork;
	}
	
	@Override
	public int getNightOvertimeWork() {
		return nightOvertimeWork;
	}
	
	@Override
	public void setNightOvertimeWork(int nightOvertimeWork) {
		this.nightOvertimeWork = nightOvertimeWork;
	}
	
	@Override
	public int getNightWorkOnHoliday() {
		return nightWorkOnHoliday;
	}
	
	@Override
	public void setNightWorkOnHoliday(int nightWorkOnHoliday) {
		this.nightWorkOnHoliday = nightWorkOnHoliday;
	}
	
	@Override
	public int getWorkOnSpecificHoliday() {
		return workOnSpecificHoliday;
	}
	
	@Override
	public void setWorkOnSpecificHoliday(int workOnSpecificHoliday) {
		this.workOnSpecificHoliday = workOnSpecificHoliday;
	}
	
	@Override
	public int getWorkOnHoliday() {
		return workOnHoliday;
	}
	
	@Override
	public void setWorkOnHoliday(int workOnHoliday) {
		this.workOnHoliday = workOnHoliday;
	}
	
	@Override
	public int getDecreaseTime() {
		return decreaseTime;
	}
	
	@Override
	public void setDecreaseTime(int decreaseTime) {
		this.decreaseTime = decreaseTime;
	}
	
	@Override
	public int getFortyFiveHourOvertime() {
		return fortyFiveHourOvertime;
	}
	
	@Override
	public void setFortyFiveHourOvertime(int fortyFiveHourOvertime) {
		this.fortyFiveHourOvertime = fortyFiveHourOvertime;
	}
	
	@Override
	public int getTimesOvertime() {
		return timesOvertime;
	}
	
	@Override
	public void setTimesOvertime(int timesOvertime) {
		this.timesOvertime = timesOvertime;
	}
	
	@Override
	public int getTimesWorkingHoliday() {
		return timesWorkingHoliday;
	}
	
	@Override
	public void setTimesWorkingHoliday(int timesWorkingHoliday) {
		this.timesWorkingHoliday = timesWorkingHoliday;
	}
	
	@Override
	public int getLateDays() {
		return lateDays;
	}
	
	@Override
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	
	@Override
	public int getLateThirtyMinutesOrMore() {
		return lateThirtyMinutesOrMore;
	}
	
	@Override
	public void setLateThirtyMinutesOrMore(int lateThirtyMinutesOrMore) {
		this.lateThirtyMinutesOrMore = lateThirtyMinutesOrMore;
	}
	
	@Override
	public int getLateLessThanThirtyMinutes() {
		return lateLessThanThirtyMinutes;
	}
	
	@Override
	public void setLateLessThanThirtyMinutes(int lateLessThanThirtyMinutes) {
		this.lateLessThanThirtyMinutes = lateLessThanThirtyMinutes;
	}
	
	@Override
	public int getLateTime() {
		return lateTime;
	}
	
	@Override
	public void setLateTime(int lateTime) {
		this.lateTime = lateTime;
	}
	
	@Override
	public int getLateThirtyMinutesOrMoreTime() {
		return lateThirtyMinutesOrMoreTime;
	}
	
	@Override
	public void setLateThirtyMinutesOrMoreTime(int lateThirtyMinutesOrMoreTime) {
		this.lateThirtyMinutesOrMoreTime = lateThirtyMinutesOrMoreTime;
	}
	
	@Override
	public int getLateLessThanThirtyMinutesTime() {
		return lateLessThanThirtyMinutesTime;
	}
	
	@Override
	public void setLateLessThanThirtyMinutesTime(int lateLessThanThirtyMinutesTime) {
		this.lateLessThanThirtyMinutesTime = lateLessThanThirtyMinutesTime;
	}
	
	@Override
	public int getTimesLate() {
		return timesLate;
	}
	
	@Override
	public void setTimesLate(int timesLate) {
		this.timesLate = timesLate;
	}
	
	@Override
	public int getLeaveEarlyDays() {
		return leaveEarlyDays;
	}
	
	@Override
	public void setLeaveEarlyDays(int leaveEarlyDays) {
		this.leaveEarlyDays = leaveEarlyDays;
	}
	
	@Override
	public int getLeaveEarlyThirtyMinutesOrMore() {
		return leaveEarlyThirtyMinutesOrMore;
	}
	
	@Override
	public void setLeaveEarlyThirtyMinutesOrMore(int leaveEarlyThirtyMinutesOrMore) {
		this.leaveEarlyThirtyMinutesOrMore = leaveEarlyThirtyMinutesOrMore;
	}
	
	@Override
	public int getLeaveEarlyLessThanThirtyMinutes() {
		return leaveEarlyLessThanThirtyMinutes;
	}
	
	@Override
	public void setLeaveEarlyLessThanThirtyMinutes(int leaveEarlyLessThanThirtyMinutes) {
		this.leaveEarlyLessThanThirtyMinutes = leaveEarlyLessThanThirtyMinutes;
	}
	
	@Override
	public int getLeaveEarlyTime() {
		return leaveEarlyTime;
	}
	
	@Override
	public void setLeaveEarlyTime(int leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	@Override
	public int getLeaveEarlyThirtyMinutesOrMoreTime() {
		return leaveEarlyThirtyMinutesOrMoreTime;
	}
	
	@Override
	public void setLeaveEarlyThirtyMinutesOrMoreTime(int leaveEarlyThirtyMinutesOrMoreTime) {
		this.leaveEarlyThirtyMinutesOrMoreTime = leaveEarlyThirtyMinutesOrMoreTime;
	}
	
	@Override
	public int getLeaveEarlyLessThanThirtyMinutesTime() {
		return leaveEarlyLessThanThirtyMinutesTime;
	}
	
	@Override
	public void setLeaveEarlyLessThanThirtyMinutesTime(int leaveEarlyLessThanThirtyMinutesTime) {
		this.leaveEarlyLessThanThirtyMinutesTime = leaveEarlyLessThanThirtyMinutesTime;
	}
	
	@Override
	public int getTimesLeaveEarly() {
		return timesLeaveEarly;
	}
	
	@Override
	public void setTimesLeaveEarly(int timesLeaveEarly) {
		this.timesLeaveEarly = timesLeaveEarly;
	}
	
	@Override
	public int getTimesHoliday() {
		return timesHoliday;
	}
	
	@Override
	public void setTimesHoliday(int timesHoliday) {
		this.timesHoliday = timesHoliday;
	}
	
	@Override
	public int getTimesLegalHoliday() {
		return timesLegalHoliday;
	}
	
	@Override
	public void setTimesLegalHoliday(int timesLegalHoliday) {
		this.timesLegalHoliday = timesLegalHoliday;
	}
	
	@Override
	public int getTimesSpecificHoliday() {
		return timesSpecificHoliday;
	}
	
	@Override
	public void setTimesSpecificHoliday(int timesSpecificHoliday) {
		this.timesSpecificHoliday = timesSpecificHoliday;
	}
	
	@Override
	public double getTimesPaidHoliday() {
		return timesPaidHoliday;
	}
	
	@Override
	public void setTimesPaidHoliday(double timesPaidHoliday) {
		this.timesPaidHoliday = timesPaidHoliday;
	}
	
	@Override
	public int getPaidHolidayHour() {
		return paidHolidayHour;
	}
	
	@Override
	public void setPaidHolidayHour(int paidHolidayHour) {
		this.paidHolidayHour = paidHolidayHour;
	}
	
	@Override
	public double getTimesStockHoliday() {
		return timesStockHoliday;
	}
	
	@Override
	public void setTimesStockHoliday(double timesStockHoliday) {
		this.timesStockHoliday = timesStockHoliday;
	}
	
	@Override
	public double getTimesCompensation() {
		return timesCompensation;
	}
	
	@Override
	public void setTimesCompensation(double timesCompensation) {
		this.timesCompensation = timesCompensation;
	}
	
	@Override
	public double getTimesLegalCompensation() {
		return timesLegalCompensation;
	}
	
	@Override
	public void setTimesLegalCompensation(double timesLegalCompensation) {
		this.timesLegalCompensation = timesLegalCompensation;
	}
	
	@Override
	public double getTimesSpecificCompensation() {
		return timesSpecificCompensation;
	}
	
	@Override
	public void setTimesSpecificCompensation(double timesSpecificCompensation) {
		this.timesSpecificCompensation = timesSpecificCompensation;
	}
	
	@Override
	public double getTimesLateCompensation() {
		return timesLateCompensation;
	}
	
	@Override
	public void setTimesLateCompensation(double timesLateCompensation) {
		this.timesLateCompensation = timesLateCompensation;
	}
	
	@Override
	public double getTimesHolidaySubstitute() {
		return timesHolidaySubstitute;
	}
	
	@Override
	public void setTimesHolidaySubstitute(double timesHolidaySubstitute) {
		this.timesHolidaySubstitute = timesHolidaySubstitute;
	}
	
	@Override
	public double getTimesLegalHolidaySubstitute() {
		return timesLegalHolidaySubstitute;
	}
	
	@Override
	public void setTimesLegalHolidaySubstitute(double timesLegalHolidaySubstitute) {
		this.timesLegalHolidaySubstitute = timesLegalHolidaySubstitute;
	}
	
	@Override
	public double getTimesSpecificHolidaySubstitute() {
		return timesSpecificHolidaySubstitute;
	}
	
	@Override
	public void setTimesSpecificHolidaySubstitute(double timesSpecificHolidaySubstitute) {
		this.timesSpecificHolidaySubstitute = timesSpecificHolidaySubstitute;
	}
	
	@Override
	public double getTotalSpecialHoliday() {
		return totalSpecialHoliday;
	}
	
	@Override
	public void setTotalSpecialHoliday(double totalSpecialHoliday) {
		this.totalSpecialHoliday = totalSpecialHoliday;
	}
	
	@Override
	public int getSpecialHolidayHour() {
		return specialHolidayHour;
	}
	
	@Override
	public void setSpecialHolidayHour(int specialHolidayHour) {
		this.specialHolidayHour = specialHolidayHour;
	}
	
	@Override
	public double getTotalOtherHoliday() {
		return totalOtherHoliday;
	}
	
	@Override
	public void setTotalOtherHoliday(double totalOtherHoliday) {
		this.totalOtherHoliday = totalOtherHoliday;
	}
	
	@Override
	public int getOtherHolidayHour() {
		return otherHolidayHour;
	}
	
	@Override
	public void setOtherHolidayHour(int otherHolidayHour) {
		this.otherHolidayHour = otherHolidayHour;
	}
	
	@Override
	public double getTotalAbsence() {
		return totalAbsence;
	}
	
	@Override
	public void setTotalAbsence(double totalAbsence) {
		this.totalAbsence = totalAbsence;
	}
	
	@Override
	public int getAbsenceHour() {
		return absenceHour;
	}
	
	@Override
	public void setAbsenceHour(int absenceHour) {
		this.absenceHour = absenceHour;
	}
	
	@Override
	public int getTotalAllowance() {
		return totalAllowance;
	}
	
	@Override
	public void setTotalAllowance(int totalAllowance) {
		this.totalAllowance = totalAllowance;
	}
	
	@Override
	public int getSixtyHourOvertime() {
		return sixtyHourOvertime;
	}
	
	@Override
	public void setSixtyHourOvertime(int sixtyHourOvertime) {
		this.sixtyHourOvertime = sixtyHourOvertime;
	}
	
	@Override
	public int getWorkdayOvertimeIn() {
		return workdayOvertimeIn;
	}
	
	@Override
	public void setWorkdayOvertimeIn(int workdayOvertimeIn) {
		this.workdayOvertimeIn = workdayOvertimeIn;
	}
	
	@Override
	public int getPrescribedHolidayOvertimeIn() {
		return prescribedHolidayOvertimeIn;
	}
	
	@Override
	public void setPrescribedHolidayOvertimeIn(int prescribedHolidayOvertimeIn) {
		this.prescribedHolidayOvertimeIn = prescribedHolidayOvertimeIn;
	}
	
	@Override
	public int getWorkdayOvertimeOut() {
		return workdayOvertimeOut;
	}
	
	@Override
	public void setWorkdayOvertimeOut(int workdayOvertimeOut) {
		this.workdayOvertimeOut = workdayOvertimeOut;
	}
	
	@Override
	public int getPrescribedOvertimeOut() {
		return prescribedOvertimeOut;
	}
	
	@Override
	public void setPrescribedOvertimeOut(int prescribedOvertimeOut) {
		this.prescribedOvertimeOut = prescribedOvertimeOut;
	}
	
	@Override
	public double getTimesAlternative() {
		return timesAlternative;
	}
	
	@Override
	public void setTimesAlternative(double timesAlternative) {
		this.timesAlternative = timesAlternative;
	}
	
	@Override
	public double getLegalCompensationOccurred() {
		return legalCompensationOccurred;
	}
	
	@Override
	public void setLegalCompensationOccurred(double legalCompensationOccurred) {
		this.legalCompensationOccurred = legalCompensationOccurred;
	}
	
	@Override
	public double getSpecificCompensationOccurred() {
		return specificCompensationOccurred;
	}
	
	@Override
	public void setSpecificCompensationOccurred(double specificCompensationOccurred) {
		this.specificCompensationOccurred = specificCompensationOccurred;
	}
	
	@Override
	public double getLateCompensationOccurred() {
		return lateCompensationOccurred;
	}
	
	@Override
	public void setLateCompensationOccurred(double lateCompensationOccurred) {
		this.lateCompensationOccurred = lateCompensationOccurred;
	}
	
	@Override
	public double getLegalCompensationUnused() {
		return legalCompensationUnused;
	}
	
	@Override
	public void setLegalCompensationUnused(double legalCompensationUnused) {
		this.legalCompensationUnused = legalCompensationUnused;
	}
	
	@Override
	public double getSpecificCompensationUnused() {
		return specificCompensationUnused;
	}
	
	@Override
	public void setSpecificCompensationUnused(double specificCompensationUnused) {
		this.specificCompensationUnused = specificCompensationUnused;
	}
	
	@Override
	public double getLateCompensationUnused() {
		return lateCompensationUnused;
	}
	
	@Override
	public void setLateCompensationUnused(double lateCompensationUnused) {
		this.lateCompensationUnused = lateCompensationUnused;
	}
	
	@Override
	public int getStatutoryHolidayWorkTimeIn() {
		return statutoryHolidayWorkTimeIn;
	}
	
	@Override
	public void setStatutoryHolidayWorkTimeIn(int statutoryHolidayWorkTimeIn) {
		this.statutoryHolidayWorkTimeIn = statutoryHolidayWorkTimeIn;
	}
	
	@Override
	public int getStatutoryHolidayWorkTimeOut() {
		return statutoryHolidayWorkTimeOut;
	}
	
	@Override
	public void setStatutoryHolidayWorkTimeOut(int statutoryHolidayWorkTimeOut) {
		this.statutoryHolidayWorkTimeOut = statutoryHolidayWorkTimeOut;
	}
	
	@Override
	public int getPrescribedHolidayWorkTimeIn() {
		return prescribedHolidayWorkTimeIn;
	}
	
	@Override
	public void setPrescribedHolidayWorkTimeIn(int prescribedHolidayWorkTimeIn) {
		this.prescribedHolidayWorkTimeIn = prescribedHolidayWorkTimeIn;
	}
	
	@Override
	public int getPrescribedHolidayWorkTimeOut() {
		return prescribedHolidayWorkTimeOut;
	}
	
	@Override
	public void setPrescribedHolidayWorkTimeOut(int prescribedHolidayWorkTimeOut) {
		this.prescribedHolidayWorkTimeOut = prescribedHolidayWorkTimeOut;
	}
	
	@Override
	public int getShortUnpaid() {
		return shortUnpaid;
	}
	
	@Override
	public void setShortUnpaid(int shortUnpaid) {
		this.shortUnpaid = shortUnpaid;
	}
	
	@Override
	public int getWeeklyOverFortyHourWorkTime() {
		return weeklyOverFortyHourWorkTime;
	}
	
	@Override
	public void setWeeklyOverFortyHourWorkTime(int weeklyOverFortyHourWorkTime) {
		this.weeklyOverFortyHourWorkTime = weeklyOverFortyHourWorkTime;
	}
	
	@Override
	public int getOvertimeInNoWeeklyForty() {
		return overtimeInNoWeeklyForty;
	}
	
	@Override
	public void setOvertimeInNoWeeklyForty(int overtimeInNoWeeklyForty) {
		this.overtimeInNoWeeklyForty = overtimeInNoWeeklyForty;
	}
	
	@Override
	public int getOvertimeOutNoWeeklyForty() {
		return overtimeOutNoWeeklyForty;
	}
	
	@Override
	public void setOvertimeOutNoWeeklyForty(int overtimeOutNoWeeklyForty) {
		this.overtimeOutNoWeeklyForty = overtimeOutNoWeeklyForty;
	}
	
	@Override
	public int getWeekDayOvertimeInNoWeeklyForty() {
		return weekDayOvertimeInNoWeeklyForty;
	}
	
	@Override
	public void setWeekDayOvertimeInNoWeeklyForty(int weekDayOvertimeInNoWeeklyForty) {
		this.weekDayOvertimeInNoWeeklyForty = weekDayOvertimeInNoWeeklyForty;
	}
	
	@Override
	public int getWeekDayOvertimeOutNoWeeklyForty() {
		return weekDayOvertimeOutNoWeeklyForty;
	}
	
	@Override
	public void setWeekDayOvertimeOutNoWeeklyForty(int weekDayOvertimeOutNoWeeklyForty) {
		this.weekDayOvertimeOutNoWeeklyForty = weekDayOvertimeOutNoWeeklyForty;
	}
	
	@Override
	public int getGeneralIntItem1() {
		return generalIntItem1;
	}
	
	@Override
	public void setGeneralIntItem1(int generalIntItem1) {
		this.generalIntItem1 = generalIntItem1;
	}
	
	@Override
	public int getGeneralIntItem2() {
		return generalIntItem2;
	}
	
	@Override
	public void setGeneralIntItem2(int generalIntItem2) {
		this.generalIntItem2 = generalIntItem2;
	}
	
	@Override
	public int getGeneralIntItem3() {
		return generalIntItem3;
	}
	
	@Override
	public void setGeneralIntItem3(int generalIntItem3) {
		this.generalIntItem3 = generalIntItem3;
	}
	
	@Override
	public int getGeneralIntItem4() {
		return generalIntItem4;
	}
	
	@Override
	public void setGeneralIntItem4(int generalIntItem4) {
		this.generalIntItem4 = generalIntItem4;
	}
	
	@Override
	public int getGeneralIntItem5() {
		return generalIntItem5;
	}
	
	@Override
	public void setGeneralIntItem5(int generalIntItem5) {
		this.generalIntItem5 = generalIntItem5;
	}
	
	@Override
	public double getGeneralDoubleItem1() {
		return generalDoubleItem1;
	}
	
	@Override
	public void setGeneralDoubleItem1(double generalDoubleItem1) {
		this.generalDoubleItem1 = generalDoubleItem1;
	}
	
	@Override
	public double getGeneralDoubleItem2() {
		return generalDoubleItem2;
	}
	
	@Override
	public void setGeneralDoubleItem2(double generalDoubleItem2) {
		this.generalDoubleItem2 = generalDoubleItem2;
	}
	
	@Override
	public double getGeneralDoubleItem3() {
		return generalDoubleItem3;
	}
	
	@Override
	public void setGeneralDoubleItem3(double generalDoubleItem3) {
		this.generalDoubleItem3 = generalDoubleItem3;
	}
	
	@Override
	public double getGeneralDoubleItem4() {
		return generalDoubleItem4;
	}
	
	@Override
	public void setGeneralDoubleItem4(double generalDoubleItem4) {
		this.generalDoubleItem4 = generalDoubleItem4;
	}
	
	@Override
	public double getGeneralDoubleItem5() {
		return generalDoubleItem5;
	}
	
	@Override
	public void setGeneralDoubleItem5(double generalDoubleItem5) {
		this.generalDoubleItem5 = generalDoubleItem5;
	}
	
	@Override
	public Map<String, Float> getSpecialHolidayDays() {
		return specialHolidayDays;
	}
	
	@Override
	public void setSpecialHolidayDays(Map<String, Float> specialHolidayDays) {
		this.specialHolidayDays = specialHolidayDays;
	}
	
	@Override
	public Map<String, Integer> getSpecialHolidayHours() {
		return specialHolidayHours;
	}
	
	@Override
	public void setSpecialHolidayHours(Map<String, Integer> specialHolidayHours) {
		this.specialHolidayHours = specialHolidayHours;
	}
	
	@Override
	public Map<String, Float> getOtherHolidayDays() {
		return otherHolidayDays;
	}
	
	/**
	 * その他休暇日数群(キー：休暇コード)。<br>を設定します。
	 * @param otherHolidayDays その他休暇日数群(キー：休暇コード)。<br>
	 */
	@Override
	public void setOtherHolidayDays(Map<String, Float> otherHolidayDays) {
		this.otherHolidayDays = otherHolidayDays;
	}
	
	@Override
	public Map<String, Integer> getOtherHolidayHours() {
		return otherHolidayHours;
	}
	
	@Override
	public void setOtherHolidayHours(Map<String, Integer> otherHolidayHours) {
		this.otherHolidayHours = otherHolidayHours;
	}
	
	@Override
	public Map<String, Float> getAbsenceDays() {
		return absenceDays;
	}
	
	@Override
	public void setAbsenceDays(Map<String, Float> absenceDays) {
		this.absenceDays = absenceDays;
	}
	
	@Override
	public Map<String, Integer> getAbsenceHours() {
		return absenceHours;
	}
	
	@Override
	public void setAbsenceHours(Map<String, Integer> absenceHours) {
		this.absenceHours = absenceHours;
	}
	
	@Override
	public Map<String, Object> getGeneralValues() {
		return generalMap;
	}
	
	@Override
	public void setGeneralValues(Map<String, Object> generalMap) {
		this.generalMap = generalMap;
	}
	
}
