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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceBean;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestEntity;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.AttendanceUtility;
import jp.mosp.time.utils.HolidayUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠一覧参照クラス。<br>
 * <br>
 * 締期間における勤怠一覧情報を作成する。<br>
 * 勤怠一覧画面、予定確認画面、出勤簿、予定簿等で用いられる。<br>
 * <br>
 * 勤怠一覧情報作成後に作成情報を取得するためのアクセサメソッドを備える。
 * <br>
 * <br>
 * 勤怠一覧情報を対象日指定、或いは年月指定で取得することができる。<br>
 * 勤怠一覧情報を取得する手順は、以下の通り。<br>
 * <br>
 * 個人ID及び対象日(年月指定の場合は年月の最終日)で、設定適用情報及び締日を取得する。<br>
 * 対象日及び締日から、対象年月を算出する(対象日指定の場合のみ)。<br>
 * 対象年月及び締日から、締期間を算出する。<br>
 * 個人ID及び締期間で、勤怠一覧情報を取得する。<br>
 * <br>
 * 週単位で勤怠一覧情報を取得する場合は、締期間の算出方法が変わる。<br>
 */
public abstract class AttendanceListReferenceBean extends AttendanceBean
		implements AttendanceListReferenceBeanInterface {
	
	/**
	 * 時間表示時の小数点以下の桁数。<br>
	 */
	public static final int											HOURS_DIGITS				= 2;
	
	/**
	 * 時間表示時の区切文字。<br>
	 */
	public static final String										SEPARATOR_HOURS				= ".";
	
	/**
	 * 限度基準期間(1ヶ月)。
	 */
	public static final String										LIMIT_STANDARD_TERM_MONTH1	= "month1";
	
	/**
	 * 半日勤務形態略称長(デフォルト)。<br>
	 */
	protected static final int										LEN_HALF_WORK_TYPE			= 1;
	
	/**
	 * 有給休暇設定マスタ参照クラス。
	 */
	protected PaidHolidayReferenceBean								paidHoliday;
	
	/**
	 * 勤怠修正情報参照クラス。
	 */
	protected AttendanceCorrectionReferenceBeanInterface			correction;
	
	/**
	 * 休暇申請参照クラス。
	 */
	protected HolidayRequestReferenceBeanInterface					holidayRequest;
	
	/**
	 * 残業申請参照クラス。
	 */
	protected OvertimeRequestReferenceBeanInterface					overtime;
	
	/**
	 * 代休情報参照クラス。
	 */
	protected SubHolidayReferenceBeanInterface						subHolidayRefer;
	
	/**
	 * 代休申請参照クラス。
	 */
	protected SubHolidayRequestReferenceBeanInterface				subHoliday;
	
	/**
	 * 勤務形態変更申請参照クラス。
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface			workTypeChange;
	
	/**
	 * 時差出勤申請参照クラス。
	 */
	protected DifferenceRequestReferenceBeanInterface				difference;
	
	/**
	 * 所属マスタ参照クラス。
	 */
	protected SectionReferenceBeanInterface							section;
	
	/**
	 * 休暇マスタ参照クラス。
	 */
	protected HolidayReferenceBeanInterface							holiday;
	
	/**
	 * 人事退職情報参照クラス。
	 */
	protected RetirementReferenceBeanInterface						retirement;
	
	/**
	 * 人事休職情報参照クラス。
	 */
	protected SuspensionReferenceBeanInterface						suspension;
	
	/**
	 * 設定適用情報。
	 */
	protected ApplicationDtoInterface								applicationDto;
	
	/**
	 * 勤怠設定情報。
	 */
	protected TimeSettingDtoInterface								timeSettingDto;
	
	/**
	 * 締日情報。
	 */
	protected CutoffDtoInterface									cutoffDto;
	
	/**
	 * カレンダ情報。
	 */
	protected ScheduleDtoInterface									scheduleDto;
	
	/**
	 * カレンダ日情報群(キー：日)。
	 */
	protected Map<Date, ScheduleDateDtoInterface>					scheduleDates;
	
	/**
	 * 社員勤怠集計管理参照クラス。
	 */
	protected TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployee;
	
	/**
	 * 勤怠関連マスタ参照クラス。
	 */
	protected TimeMasterBeanInterface								timeMaster;
	
	/**
	 * 取得対象個人ID。<br>
	 * インターフェースに定義されたメソッドの最初で設定される。<br>
	 */
	protected String												personalId;
	
	/**
	 * 対象年。<br>
	 */
	protected int													targetYear;
	
	/**
	 * 対象月。<br>
	 */
	protected int													targetMonth;
	
	/**
	 * 締期間初日。<br>
	 * {@link #setCutoffTerm()}により設定される。<br>
	 */
	protected Date													firstDate;
	
	/**
	 * 締期間最終日。<br>
	 * {@link #setCutoffTerm()}により設定される。<br>
	 */
	protected Date													lastDate;
	
	/**
	 * 勤怠一覧区分。<br>
	 * {@link #initFields(String, int)}で設定される。<br>
	 * 追加処理等で利用することを想定している。<br>
	 * 次のいずれかの値が設定される。<br>
	 * ・1：勤怠<br>
	 * ・2：実績<br>
	 * ・3：予定<br>
	 * ・4：承認<br>
	 * ・9：その他<br>
	 */
	protected int													listType;
	
	/**
	 * 対象年月における対象個人IDの締状態。<br>
	 * true：仮締又は確定、false：未締。<br>
	 */
	protected boolean												isTightened;
	
	/**
	 * 半日勤務形態略称長。<br>
	 * 通常は1文字。<br>
	 */
	protected int													halfWorkTypeLength;
	
	/**
	 * 勤怠情報リスト。<br>
	 */
	protected List<AttendanceDtoInterface>							attendanceDtoList;
	
	/**
	 * 休暇申請情報リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>						holidayRequestList;
	
	/**
	 * 残業申請情報リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>						overtimeRequestList;
	
	/**
	 * 振替休日情報リスト。<br>
	 */
	protected List<SubstituteDtoInterface>							substituteList;
	
	/**
	 * 代休申請情報リスト。<br>
	 */
	protected List<SubHolidayRequestDtoInterface>					subHolidayRequestList;
	
	/**
	 * 休出申請情報リスト
	 */
	protected List<WorkOnHolidayRequestDtoInterface>				workOnHolidayRequestList;
	
	/**
	 * 勤務形態変更申請情報リスト
	 */
	protected List<WorkTypeChangeRequestDtoInterface>				workTypeChangeRequestList;
	
	/**
	 * 時差出勤申請情報リスト
	 */
	protected List<DifferenceRequestDtoInterface>					differenceRequestList;
	
	/**
	 * 勤怠一覧情報リスト。<br>
	 * 各メソッドは、ここに勤怠一覧情報を追加していく。<br>
	 */
	protected List<AttendanceListDto>								attendanceList;
	
	/**
	 * 申請ユーティリティ。
	 */
	protected RequestUtilBeanInterface								requestUtil;
	
	/**
	 * ワークフロー情報群。<br>
	 */
	protected Map<Long, WorkflowDtoInterface>						workflowMap;
	
	/**
	 * 勤務形態エンティティ群。<br>
	 */
	protected Map<String, WorkTypeEntityInterface>					workTypeEntityMap;
	
	
	@Override
	public void initBean() throws MospException {
		// 継承元クラスのメソッドを実行
		super.initBean();
		// 参照クラス準備
		paidHoliday = createBeanInstance(PaidHolidayReferenceBean.class);
		correction = createBeanInstance(AttendanceCorrectionReferenceBeanInterface.class);
		holidayRequest = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		overtime = createBeanInstance(OvertimeRequestReferenceBean.class);
		workOnHoliday = createBeanInstance(WorkOnHolidayRequestReferenceBeanInterface.class);
		subHolidayRefer = createBeanInstance(SubHolidayReferenceBeanInterface.class);
		subHoliday = createBeanInstance(SubHolidayRequestReferenceBeanInterface.class);
		workTypeChange = createBeanInstance(WorkTypeChangeRequestReferenceBeanInterface.class);
		difference = createBeanInstance(DifferenceRequestReferenceBeanInterface.class);
		section = createBeanInstance(SectionReferenceBeanInterface.class);
		holiday = createBeanInstance(HolidayReferenceBeanInterface.class);
		retirement = createBeanInstance(RetirementReferenceBeanInterface.class);
		suspension = createBeanInstance(SuspensionReferenceBeanInterface.class);
		totalTimeEmployee = createBeanInstance(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// カレンダユーティリティに勤怠関連マスタ参照処理を設定
		scheduleUtil.setTimeMaster(timeMaster);
		// 半日勤務形態略称長を設定(1文字)
		halfWorkTypeLength = LEN_HALF_WORK_TYPE;
	}
	
	@Override
	public List<AttendanceListDto> getScheduleList(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_SCHEDULE);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 対象日及び締日から対象年月を算出し設定
		setTargetYearMonth(targetDate);
		// 締期間設定
		setCutoffTerm();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 予定一覧表示に申請情報を適用するか否かを確認
		if (isScheduleApplyRequest()) {
			// 勤怠一覧情報リストを初期化(予定一覧表示に申請情報を適用する場合)
			initAttendanceList();
		} else {
			// 勤怠一覧情報リストを初期化(予定一覧表示に申請情報を適用しない場合)
			initScheduleList();
		}
		
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間含む)
		addScheduleList(true, true);
		// 予定一覧表示に申請情報を適用する場合
		if (isScheduleApplyRequest()) {
			// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
			addApplicationList(false, false, true);
			// 申請(承認済)によって予定勤務時間表示無し
			setApprovalTime();
		}
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 帳票用ヘッダー項目設定
		setHeaderFields();
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETSCHEDULELIST, timeSettingDto,
				attendanceList);
		// 予定一覧追加処理
		setScheduleExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getScheduleList(String personalId, int year, int month) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_SCHEDULE);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(year, month);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締期間設定
		setCutoffTerm();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 予定一覧表示に申請情報を適用するか否かを確認
		if (isScheduleApplyRequest()) {
			// 勤怠一覧情報リストを初期化(予定一覧表示に申請情報を適用する場合)
			initAttendanceList();
		} else {
			// 勤怠一覧情報リストを初期化(予定一覧表示に申請情報を適用しない場合)
			initScheduleList();
		}
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間含む)
		addScheduleList(true, true);
		// 予定一覧表示に申請情報を適用する場合
		if (isScheduleApplyRequest()) {
			// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
			addApplicationList(false, false, true);
			// 申請(承認済)によって予定勤務時間表示無し
			setApprovalTime();
		}
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 帳票用ヘッダー項目設定
		setHeaderFields();
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETSCHEDULELIST, timeSettingDto,
				attendanceList);
		// 予定一覧追加処理
		setScheduleExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getScheduleList(String personalId, int year, int month, int cutoffDate)
			throws MospException {
		// 個人ID及び年月で予定一覧を取得(締日は考慮しない)
		return getScheduleList(personalId, year, month);
	}
	
	@Override
	public List<AttendanceListDto> getActualList(String personalId, int year, int month) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_ACTUAL);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(year, month);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締期間設定
		setCutoffTerm();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
		addActualList(false);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報は含めない)
		addApplicationList(false, true, false);
		// カレンダ情報を取得し勤怠一覧情報リストに設定(休日のみ)
		addScheduleList(false, false);
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 帳票用ヘッダー項目設定
		setHeaderFields();
		// 実績一覧追加処理
		setActualExtraInfo();
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETACTUALLIST, timeSettingDto, attendanceList);
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getAttendanceList(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_ATTENDANCE);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 対象日及び締日から対象年月を算出し設定
		setTargetYearMonth(targetDate);
		// 締期間設定
		setCutoffTerm();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		// 限度基準情報設定
		setLimitStandard();
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧情報の特定の項目にハイフンを設定
		setDtoHyphenFields(useScheduledTime());
		// チェックボックス設定
		setNeedCheckbox();
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETATTENDANCELIST, timeSettingDto,
				attendanceList, requestUtil);
		// 勤怠一覧追加処理
		setAttendanceExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getAttendanceList(String personalId, int year, int month) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_ATTENDANCE);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(year, month);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締期間設定
		setCutoffTerm();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		// 限度基準情報設定
		setLimitStandard();
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧情報の特定の項目にハイフンを設定
		setDtoHyphenFields(useScheduledTime());
		// チェックボックス設定
		setNeedCheckbox();
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETATTENDANCELIST, timeSettingDto,
				attendanceList, requestUtil);
		// 勤怠一覧追加処理
		setAttendanceExtraInfo();
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getAttendanceList(String personalId, int year, int month, int cutoffDate)
			throws MospException {
		// 個人ID及び年月で勤怠一覧を取得(締日は考慮しない)
		return getAttendanceList(personalId, year, month);
	}
	
	@Override
	public List<AttendanceListDto> getWeeklyAttendanceList(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_ATTENDANCE);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 対象日から期間(週)を設定
		setWeekTerm(targetDate);
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧情報の特定の項目にハイフンを設定
		setDtoHyphenFields(useScheduledTime());
		// 勤怠一覧(週)追加処理
		setWeeklyAttendanceExtraInfo();
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETWEEKLYATTENDANCELIST, timeSettingDto,
				attendanceList, requestUtil);
		return attendanceList;
	}
	
	@Override
	public List<AttendanceListDto> getApprovalAttendanceList(String personalId, int year, int month)
			throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_APPROVAL);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(year, month);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締期間設定
		setCutoffTerm();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 締状態設定
		setTightened();
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, true, false);
		// カレンダ情報を取得し勤怠一覧情報リストに設定(休日のみ)
		addScheduleList(false, false);
		// 限度基準情報設定
		setLimitStandard();
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧情報の特定の項目にハイフンを設定
		setDtoHyphenFields(false);
		// チェックボックス設定
		setNeedApprovalCheckbox();
		// 勤怠承認一覧追加処理
		setApprovalAttendanceExtraInfo();
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETAPPROVALATTENDANCELIST, timeSettingDto,
				attendanceList, requestUtil);
		return attendanceList;
	}
	
	@Override
	public AttendanceListDto getAttendanceListDto(String personalId, Date targetDate) throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_ATTENDANCE);
		// 対象年月及び締期間最終日を仮設定
		initDateFields(targetDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 対象日を設定
		firstDate = CapsuleUtility.getDateClone(targetDate);
		lastDate = CapsuleUtility.getDateClone(targetDate);
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setDtoStringFields();
		// 勤怠一覧データ追加処理
		setAttendanceListDtoExtraInfo();
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETATTENDANCELISTDTO, timeSettingDto,
				attendanceList, requestUtil);
		// 勤怠一覧情報を取得
		return MospUtility.getFirstValue(attendanceList);
	}
	
	@Override
	public List<AttendanceListDto> getTermAttendanceList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// フィールド初期化及び対象個人ID設定
		initFields(personalId, AttendanceUtility.TYPE_LIST_ATTENDANCE);
		// 期間を設定
		this.firstDate = CapsuleUtility.getDateClone(firstDate);
		this.lastDate = CapsuleUtility.getDateClone(lastDate);
		// 勤怠設定情報群を取得
		setApplicationSettings();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠一覧情報リストを初期化
		initAttendanceList();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return attendanceList;
		}
		// 勤怠情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addActualList(true);
		// 申請情報を取得し勤怠一覧情報リストに設定(未承認情報を含める)
		addApplicationList(true, false, false);
		// 勤怠情報(下書)を取得し勤怠一覧情報リストに設定
		addDraftList();
		// カレンダ情報を取得し勤怠一覧情報リストに設定(勤務時間、休憩時間を除く)
		addScheduleList(true, false);
		return attendanceList;
	}
	
	/**
	 * フィールド初期化及び対象個人ID設定を行う。<br>
	 * @param personalId 対象個人ID
	 * @param listType   勤怠一覧区分
	 */
	protected void initFields(String personalId, int listType) {
		// 設定適用情報初期化
		applicationDto = null;
		// 勤怠設定情報初期化
		timeSettingDto = null;
		// 締日情報初期化
		cutoffDto = null;
		// カレンダ情報初期化
		scheduleDto = null;
		// カレンダ日情報群初期化
		scheduleDates = null;
		// 締期間初日初期化
		firstDate = null;
		// 締期間最終日初期化
		lastDate = null;
		// 勤怠一覧情報リスト初期化
		attendanceList = new ArrayList<AttendanceListDto>();
		// 個人ID設定
		this.personalId = personalId;
		// 勤怠一覧区分を設定
		this.listType = listType;
	}
	
	/**
	 * 対象日で対象年月及び締期間最終日を仮設定する。<br>
	 * @param targetDate 対象日
	 */
	protected void initDateFields(Date targetDate) {
		// 対象年月設定(対象日の年月を仮設定)
		targetYear = DateUtility.getYear(targetDate);
		targetMonth = DateUtility.getMonth(targetDate);
		// 初期表示の場合システム日付設定(対象日を仮設定)
		lastDate = targetDate;
	}
	
	/**
	 * 対象年月で対象年月を設定、締期間最終日を仮設定する。<br>
	 * @param year  対象年
	 * @param month 対象月
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void initDateFields(int year, int month) throws MospException {
		// 対象年月設定
		targetYear = year;
		targetMonth = month;
		// 締期間最終日設定(年月の最終日を仮設定)
		lastDate = MonthUtility.getYearMonthTargetDate(year, month, mospParams);
	}
	
	/**
	 * 帳票用ヘッダー項目を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setHeaderFields() throws MospException {
		// 最初の勤怠一覧レコードを取得
		AttendanceListDto dto = attendanceList.get(0);
		// 人事情報取得
		HumanDtoInterface humanDto = getHumanInfo(personalId, lastDate);
		// 社員コード設定
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		// 社員氏名設定
		dto.setEmployeeName(MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName()));
		// 所属名設定
		dto.setSectionName(section.getSectionName(humanDto.getSectionCode(), lastDate));
		if (section.useDisplayName()) {
			// 所属表示名称を設定
			dto.setSectionName(section.getSectionDisplay(humanDto.getSectionCode(), lastDate));
		}
		// 帳票承認印欄表示設定
		dto.setSealBoxAvailable(true);
		// 最後の勤怠一覧レコードを取得
		AttendanceListDto lastDto = attendanceList.get(attendanceList.size() - 1);
		// 帳票代休申請不要設定
		lastDto.setSubHolidayRequestValid(TimeUtility.isSubHolidayRequestValid(mospParams));
	}
	
	/**
	 * 対象個人ID及び締期間最終日で勤怠設定情報群を取得し、設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setApplicationSettings() throws MospException {
		// 勤怠一覧情報リスト初期化
		attendanceList = new ArrayList<AttendanceListDto>();
		// 基準日における取得対象個人IDの設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, lastDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			// 処理終了
			return;
		}
		// 設定適用情報を取得
		applicationDto = application.getApplicationDto();
		// 勤怠設定情報を取得
		timeSettingDto = application.getTimeSettingDto();
		// 基準日における締日情報を取得
		cutoffDto = application.getCutoffDto();
		// 基準日におけるカレンダ情報を取得
		scheduleDto = scheduleUtil.getSchedule(personalId, lastDate);
	}
	
	/**
	 * 対象日及び締日から対象年月を算出し、設定する。<br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setTargetYearMonth(Date targetDate) throws MospException {
		// 締日取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 対象日及び締日から対象年月を算出
		Date yearMonth = TimeUtility.getCutoffMonth(cutoffDate, targetDate, mospParams);
		// 対象年月設定
		targetYear = DateUtility.getYear(yearMonth);
		targetMonth = DateUtility.getMonth(yearMonth);
	}
	
	/**
	 * 対象年月及び締日から締期間を算出し、設定する。<br>
	 * 勤怠一覧画面等の期間で勤怠情報を取得する場合は、この締期間を利用する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCutoffTerm() throws MospException {
		// 締日取得
		int cutoffDate = cutoffDto.getCutoffDate();
		// 締期間初日設定
		firstDate = TimeUtility.getCutoffFirstDate(cutoffDate, targetYear, targetMonth, mospParams);
		// 締期間最終日設定
		lastDate = TimeUtility.getCutoffLastDate(cutoffDate, targetYear, targetMonth, mospParams);
		// 締期間最終日時点で入社していない又は締日最終日時点で設定適用がない場合
		if (!isEntered(personalId, lastDate) || timeMaster.getApplication(personalId, lastDate) == null) {
			// 締期間最終日で再取得
			setApplicationSettings();
		}
	}
	
	/**
	 * 締状態を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setTightened() throws MospException {
		// 締状態取得
		Integer state = totalTimeEmployee.getCutoffState(personalId, targetYear, targetMonth);
		// 締状態設定
		isTightened = state != null && state != TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT;
	}
	
	/**
	 * 勤怠一覧情報リストを初期化する。<br>
	 * 締期間初日～締期間最終日の勤怠一覧情報を作成し、初期化する。<br>
	 * <br>
	 * また、締期間初日～締期間最終日の
	 * カレンダ日情報リスト、各種申請情報を取得する。<br>
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void initAttendanceList() throws MospException {
		// 初期勤怠一覧情報リスト準備
		attendanceList = new ArrayList<AttendanceListDto>();
		// 勤怠日準備(締期間初日)
		Date workDate = getDateClone(firstDate);
		// カレンダコードを取得
		String scheduleCode = MospUtility.isEmpty(scheduleDto) ? MospConst.STR_EMPTY : scheduleDto.getScheduleCode();
		// 締期間初日から締期間最終日までの初期勤怠一覧情報を作成
		while (workDate.after(lastDate) == false) {
			// 勤怠一覧情報準備
			AttendanceListDto dto = new AttendanceListDto();
			// 初期情報設定
			dto.setWorkDate(workDate);
			// 個人ID(頁単位項目)設定
			dto.setPersonalId(personalId);
			// 対象年及び対象月を設定
			dto.setTargetYear(targetYear);
			dto.setTargetMonth(targetMonth);
			// カレンダコードを設定
			dto.setScheduleCode(scheduleCode);
			// 締日を設定
			dto.setCutoffDate(MospUtility.isEmpty(cutoffDto) ? 0 : cutoffDto.getCutoffDate());
			// 初期勤怠一覧情報リストに追加
			attendanceList.add(dto);
			// 勤怠日加算
			workDate = addDay(workDate, 1);
		}
		// ワークフロー情報群の初期化
		workflowMap = new HashMap<Long, WorkflowDtoInterface>();
		// ワークフロー情報群の初期化
		workTypeEntityMap = new HashMap<String, WorkTypeEntityInterface>();
		// カレンダ日情報取得
		scheduleDates = scheduleUtil.getScheduleDates(personalId, firstDate, lastDate);
		// 勤怠情報取得
		attendanceDtoList = attendanceReference.getAttendanceList(personalId, firstDate, lastDate);
		// 休暇申請情報取得
		holidayRequestList = holidayRequest.getHolidayRequestList(personalId, firstDate, lastDate);
		// 残業申請情報取得
		overtimeRequestList = overtime.getOvertimeRequestList(personalId, firstDate, lastDate);
		// 代休申請情報取得
		subHolidayRequestList = subHoliday.getSubHolidayRequestList(personalId, firstDate, lastDate);
		// 振替休日情報取得
		substituteList = substituteReference.getSubstituteList(personalId, firstDate, lastDate);
		// 休日出勤申請情報取得
		workOnHolidayRequestList = workOnHoliday.getWorkOnHolidayRequestList(personalId, firstDate, lastDate);
		// 勤務形態変更申請情報取得
		workTypeChangeRequestList = workTypeChange.getWorkTypeChangeRequestList(personalId, firstDate, lastDate);
		// 時差出勤申請情報リスト取得
		differenceRequestList = difference.getDifferenceRequestList(personalId, firstDate, lastDate);
	}
	
	/**
	 * 勤怠一覧情報リストを初期化する。<br>
	 * 締期間初日～締期間最終日の勤怠一覧情報を作成し、初期化する。<br>
	 * <br>
	 * また、締期間初日～締期間最終日のカレンダ日情報リストを取得する。<br>
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void initScheduleList() throws MospException {
		// 初期勤怠一覧情報リスト準備
		attendanceList = new ArrayList<AttendanceListDto>();
		// 勤怠日準備(締期間初日)
		Date workDate = getDateClone(firstDate);
		// カレンダコードを取得
		String scheduleCode = MospUtility.isEmpty(scheduleDto) ? MospConst.STR_EMPTY : scheduleDto.getScheduleCode();
		// 締期間初日から締期間最終日までの初期勤怠一覧情報を作成
		while (workDate.after(lastDate) == false) {
			// 勤怠一覧情報準備
			AttendanceListDto dto = new AttendanceListDto();
			// 初期情報設定
			dto.setWorkDate(workDate);
			// 個人ID(頁単位項目)設定
			dto.setPersonalId(personalId);
			// 対象年及び対象月を設定
			dto.setTargetYear(targetYear);
			dto.setTargetMonth(targetMonth);
			// カレンダコードを設定
			dto.setScheduleCode(scheduleCode);
			// 締日を設定
			dto.setCutoffDate(MospUtility.isEmpty(cutoffDto) ? 0 : cutoffDto.getCutoffDate());
			// 初期勤怠一覧情報リストに追加
			attendanceList.add(dto);
			// 勤怠日加算
			workDate = addDay(workDate, 1);
		}
		// ワークフロー情報群の初期化
		workflowMap = new HashMap<Long, WorkflowDtoInterface>();
		// ワークフロー情報群の初期化
		workTypeEntityMap = new HashMap<String, WorkTypeEntityInterface>();
		// カレンダ日情報取得
		scheduleDates = scheduleUtil.getScheduleDates(personalId, firstDate, lastDate);
		// 勤怠情報取得
		attendanceDtoList = Collections.emptyList();
		// 休暇申請情報取得
		holidayRequestList = Collections.emptyList();
		// 残業申請情報取得
		overtimeRequestList = Collections.emptyList();
		// 代休申請情報取得
		subHolidayRequestList = Collections.emptyList();
		// 振替休日情報取得
		substituteList = Collections.emptyList();
		// 休日出勤申請情報取得
		workOnHolidayRequestList = Collections.emptyList();
		// 勤務形態変更申請情報取得
		workTypeChangeRequestList = Collections.emptyList();
		// 時差出勤申請情報リスト取得
		differenceRequestList = Collections.emptyList();
	}
	
	/**
	 * 申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * 同時に、申請情報の集計を行う。<br>
	 * 実績一覧(出勤簿等)でない場合、申請によって、
	 * 申請日の勤怠が下書き状態であれば下書き情報をリストに加える。<br>
	 * @param containNotApproved 未承認要否フラグ(true：下書等未承認申請も含める、false：承認済のみ)
	 * @param isActualList 実績一覧フラグ(true：実績一覧、false：実績一覧ではない)
	 * @param isSchedule 予定確認フラグ(true：予定確認、false：予定確認ではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addApplicationList(boolean containNotApproved, boolean isActualList, boolean isSchedule)
			throws MospException {
		// 勤務形態変更申請情報設定
		addWorkTypeChangeRequestList(containNotApproved, isActualList, isSchedule);
		// 休暇申請情報設定
		addHolidayRequestList(containNotApproved);
		// 休日出勤申請情報設定
		addWorkOnHolidayRequestList(containNotApproved, isActualList);
		// 振替休日申請情報設定
		addSubstituteList(containNotApproved);
		// 代休申請情報設定
		addSubHolidayRequestList(containNotApproved);
		// 残業申請情報設定
		addOvertimeRequestList(containNotApproved);
		// 時差出勤申請情報設定
		addDifferenceRequestList(containNotApproved, isActualList);
		// 時間単位年休利用確認
		confirmTimelyPaidHoliday();
		// 同日複数申請確認
		checkPluralRequest(containNotApproved);
	}
	
	/**
	 * 休暇申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * 同時に、休暇申請情報の集計を行う。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addHolidayRequestList(boolean containNotApproved) throws MospException {
		// 集計値準備
		// 有給休暇回数
		float paidHolidays = 0F;
		// 有給時間
		float paidHolidayTime = 0F;
		// 特別休暇回数
		float specialHolidays = 0F;
		// 特別休暇時間数
		float specialHolidayTimes = 0F;
		// その他休暇回数
		float otherHolidays = 0F;
		// その他休暇時間数
		float otherHolidayTimes = 0F;
		// 欠勤回数
		float absenceDays = 0F;
		// 欠勤時間数
		float absenceTimes = 0F;
		// 休暇申請情報取得
		holidayRequestList = holidayRequest.getHolidayRequestList(personalId, firstDate, lastDate);
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(holidayRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					// 対象日(申請開始日～申請終了日)取得
					List<Date> targetDateList = TimeUtility.getDateList(holidayRequestDto.getRequestStartDate(),
							holidayRequestDto.getRequestEndDate());
					// 開始日から終了日まで日毎に処理
					for (Date targetDate : targetDateList) {
						// カレンダ日情報を取得
						ScheduleDateDtoInterface scheduleDateDto = getScheduleDateDto(targetDate);
						// カレンダ日情報を取得できなかった場合(対象日が期間に含まれない場合等)
						if (scheduleDateDto == null) {
							// 次の日へ
							continue;
						}
						// 勤務形態コードを取得
						String workTypeCode = scheduleDateDto.getWorkTypeCode();
						// 各種申請情報取得
						requestUtil.setRequests(personalId, targetDate);
						// 休日出勤申請がある場合
						workTypeCode = getWorkTypeWorkOnHolidayForHolidayRequest(workTypeCode);
						// 法定休日・所定休日・法定休日出勤・所定休日出勤の場合
						if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
								|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
							continue;
						}
						// 対象日における勤怠一覧情報を取得
						AttendanceListDto dto = getAttendanceListDto(targetDate);
						// 対象日における勤怠一覧情報の存在を確認
						if (dto == null) {
							continue;
						}
						// 備考
						String remark = TimeNamingUtility.holiday(mospParams) + getDraftAbbrNaming();
						// 有給休暇時間承認状態別休数回数マップ取得
						Map<String, Integer> timeHoliday = holidayRequest.getTimeHolidayStatusTimesMap(personalId,
								dto.getWorkDate(), null);
						// 時休を取得している場合
						if (timeHoliday.get(getDraftAbbrNaming()) > 0) {
							// 備考追加
							remark = remark + MospConst.STR_SB_SPACE + mospParams.getName("Hour")
									+ timeHoliday.get(getDraftAbbrNaming());
						}
						// 備考設定
						addRemark(dto, remark);
					}
				}
				continue;
			}
			// 休暇回数取得
			float times = getHolidayTimes(holidayRequestDto);
			// 対象日(申請開始日～申請終了日)取得
			List<Date> targetDateList = TimeUtility.getDateList(holidayRequestDto.getRequestStartDate(),
					holidayRequestDto.getRequestEndDate());
			// 開始日から終了日まで日毎に処理
			for (Date targetDate : targetDateList) {
				// カレンダ日情報を取得
				ScheduleDateDtoInterface scheduleDateDto = getScheduleDateDto(targetDate);
				// カレンダ日情報を取得できなかった場合(対象日が期間に含まれない場合等)
				if (scheduleDateDto == null) {
					// 次の日へ
					continue;
				}
				// 勤務形態コードを取得
				String workTypeCode = scheduleDateDto.getWorkTypeCode();
				// 各種申請情報取得
				requestUtil.setRequests(personalId, targetDate);
				// 休日出勤申請がある場合
				workTypeCode = getWorkTypeWorkOnHolidayForHolidayRequest(workTypeCode);
				// 法定休日・所定休日・法定休日出勤・所定休日出勤の場合
				if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
						|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
					// 次の日へ
					continue;
				}
				// 対象日における勤怠一覧情報を取得
				AttendanceListDto dto = getAttendanceListDto(targetDate);
				// 対象日における勤怠一覧情報の存在を確認
				if (dto == null) {
					// 次の日へ
					continue;
				}
				// 休暇申請情報を勤怠一覧情報に設定
				setDtoFields(dto, holidayRequestDto, workflowDto, containNotApproved);
				// 休暇区分確認
				switch (holidayRequestDto.getHolidayType1()) {
					case TimeConst.CODE_HOLIDAYTYPE_HOLIDAY:
						// 有給休暇の場合
						// 休暇範囲確認
						if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
							// 有給時間加算
							paidHolidayTime += times;
						} else {
							// 有給休暇回数加算
							paidHolidays += times;
						}
						break;
					case TimeConst.CODE_HOLIDAYTYPE_SPECIAL:
						// 特別休暇の場合
						if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
							// 特別休暇時間加算
							specialHolidayTimes += times;
						} else {
							// 特別休暇回数加算
							specialHolidays += times;
						}
						break;
					case TimeConst.CODE_HOLIDAYTYPE_OTHER:
						// その他休暇の場合
						if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
							// その他休暇時間加算
							otherHolidayTimes += times;
						} else {
							// その他休暇回数加算
							otherHolidays += times;
						}
						break;
					case TimeConst.CODE_HOLIDAYTYPE_ABSENCE:
						// 欠勤の場合
						if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
							// 欠勤時間加算
							absenceTimes += times;
						} else {
							// 欠勤回数加算
							absenceDays += times;
						}
						break;
					default:
						// 処理無し
						break;
				}
			}
		}
		// 最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 集計値設定
		// 有給休暇回数
		dto.setPaidHolidays(paidHolidays);
		// 有給時間
		dto.setPaidHolidayTime(paidHolidayTime);
		// 特別休暇回数
		dto.setSpecialHolidays(specialHolidays);
		// 特別休暇時間数
		dto.setSpecialHolidayHours(specialHolidayTimes);
		// その他休暇回数
		dto.setOtherHolidays(otherHolidays);
		// その他休暇時間数
		dto.setOtherHolidayHours(otherHolidayTimes);
		// 欠勤回数
		dto.setAbsenceDays(absenceDays);
		// 欠勤時間数
		dto.setAbsenceHours(absenceTimes);
		// 追加業務ロジック処理(勤怠一覧参照処理：休暇申請情報設定)
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_ADDHOLIDAYREQUESTLIST, attendanceList,
				attendanceDtoList, workflowMap, listType);
	}
	
	/**
	 * 休暇申請情報を設定時の勤務形態取得(振替休日)
	 * @param workTypeBefore メソッド実行前勤務形態
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected String getWorkTypeWorkOnHolidayForHolidayRequest(String workTypeBefore) throws MospException {
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			for (SubstituteDtoInterface substituteDto : requestUtil.getSubstituteList(true)) {
				if (isAll(substituteDto)) {
					// 全休の場合
					return substituteDto.getSubstituteType();
				}
			}
			return workTypeBefore;
		}
		return getWorkOnHolidayWorkType(workOnHolidayRequestDto);
		
	}
	
	/**
	 * ワークフロー情報から申請情報の要否を確認する。<br>
	 * ワークフロー状況及び未承認要否フラグで判断する。<br>
	 * @param workflowDto        ワークフロー情報
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @return 申請情報の要否(true：必要、false：不要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isRequestNeeded(WorkflowDtoInterface workflowDto, boolean containNotApproved)
			throws MospException {
		// ワークフロー状況確認(下書の場合)
		if (WorkflowUtility.isDraft(workflowDto)) {
			// 不要
			return false;
		}
		// ワークフロー状況確認(取下の場合)
		if (WorkflowUtility.isWithDrawn(workflowDto)) {
			// 不要
			return false;
		}
		// 未承認要否フラグ及びワークフロー状況確認(承認済でない場合)
		if (containNotApproved == false && WorkflowUtility.isCompleted(workflowDto) == false) {
			// 不要
			return false;
		}
		return true;
	}
	
	/**
	 * ワークフロー情報から差戻かどうかを判断する。<br>
	 * @param dto ワークフロー情報
	 * @return true：差戻、false：差戻でない
	 */
	protected boolean isRevert(WorkflowDtoInterface dto) {
		return PlatformConst.CODE_STATUS_REVERT.equals(dto.getWorkflowStatus());
	}
	
	/**
	 * 休暇回数を取得する。<br>
	 * 休暇申請情報の休暇範囲から、休暇回数を取得する。<br>
	 * 休暇申請の場合は、時間休も考慮する。<br>
	 * @param holidayRequestDto 休暇申請情報
	 * @return 休暇回数
	 */
	protected float getHolidayTimes(HolidayRequestDtoInterface holidayRequestDto) {
		// 休暇範囲確認
		switch (holidayRequestDto.getHolidayRange()) {
			case TimeConst.CODE_HOLIDAY_RANGE_ALL:
				// 全休の場合
				return TimeConst.HOLIDAY_TIMES_ALL;
			case TimeConst.CODE_HOLIDAY_RANGE_AM:
			case TimeConst.CODE_HOLIDAY_RANGE_PM:
				// 半休の場合
				return TimeConst.HOLIDAY_TIMES_HALF;
			case TimeConst.CODE_HOLIDAY_RANGE_TIME:
				// 時間休の場合
				// 開始時刻と終了時刻の差を取得
				return holidayRequestDto.getUseHour();
			default:
				return 0F;
		}
	}
	
	/**
	 * 休暇回数を取得する。<br>
	 * 休暇申請情報の休暇範囲から、休暇回数を取得する。<br>
	 * @param holidayRange 休暇範囲
	 * @return 休暇回数
	 */
	protected float getHolidayTimes(int holidayRange) {
		// 休暇回数を取得
		return TimeUtility.getHolidayTimes(holidayRange);
	}
	
	/**
	 * 休暇申請情報を勤怠一覧情報に設定する。<br>
	 * ワークフロー情報は、休暇申請情報から取得したものとする。<br>
	 * @param dto               設定対象勤怠一覧情報
	 * @param holidayRequestDto 休暇申請情報
	 * @param workflowDto       ワークフロー情報
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ) ※アドオンで利用
	 * 
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceListDto dto, HolidayRequestDtoInterface holidayRequestDto,
			WorkflowDtoInterface workflowDto, boolean containNotApproved) throws MospException {
		// 備考
		String remark = TimeNamingUtility.holiday(mospParams) + getWorkflowStatusAbbr(workflowDto);
		// 有給休暇時間承認状態別休数回数マップ取得
		Map<String, Integer> timeHoliday = holidayRequest.getTimeHolidayStatusTimesMap(personalId,
				holidayRequestDto.getRequestStartDate(), null);
		
		if (!timeHoliday.isEmpty()) {
			// 時休を取得している場合
			if (timeHoliday.get(getWorkflowStatusAbbr(workflowDto)) > 0) {
				// 備考追加
				remark = remark + MospConst.STR_SB_SPACE + mospParams.getName("Hour")
						+ timeHoliday.get(getWorkflowStatusAbbr(workflowDto));
			}
			
		}
		// 備考設定
		addRemark(dto, remark);
		// 休暇範囲確認
		if (!isDtoFieldsSetHolidayRequestAll(dto, holidayRequestDto, containNotApproved)) {
			// 全休以外の場合は設定不要
			return;
		}
		// 勤務形態設定(空白)(予定による上書を防ぐため)
		dto.setWorkTypeCode("");
		// チェックボックス要否設定(不要)
		dto.setNeedCheckbox(false);
		// 状態設定
		dto.setApplicationInfo(
				workflow.getWorkflowStatus(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		// 状態リンクコマンド設定
		dto.setNeedStatusLink(false);
		// 休暇区分毎に勤務形態略称設定
		dto.setWorkTypeAbbr(getWorkTypeAbbr(holidayRequestDto));
		// 無休時短時間設定
		setShortUnpaid(dto);
	}
	
	/**
	 * DTO設定要否判定
	 * @param dto 設定対象勤怠一覧情報
	 * @param holidayRequestDto 休暇申請DTO
	 * @param containNotApproved  未承認要否フラグ(true：未承認申請も含める、false：承認済のみ) ※アドオンで利用
	 * @return DTO設定要否判定（true:設定要、false:設定不要）
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected boolean isDtoFieldsSetHolidayRequestAll(AttendanceListDto dto,
			HolidayRequestDtoInterface holidayRequestDto, boolean containNotApproved) throws MospException {
		return holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL;
	}
	
	/**
	 * ワークフロー状況略称を取得する。<br>
	 * ワークフロー状況によって申、済を返す。<br>
	 * @param workflowDto ワークフロー情報
	 * @return ワークフロー状況略称
	 */
	protected String getWorkflowStatusAbbr(WorkflowDtoInterface workflowDto) {
		if (workflowDto == null) {
			return "";
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			return "";
		}
		// ワークフロー状況確認(下書の場合)
		if (workflow.isDraft(workflowDto)) {
			return getDraftAbbrNaming();
		}
		// ワークフロー状況確認(承認済の場合)
		if (PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
			return getCompleteApprovalAbbrNaming();
		}
		// ワークフロー状況確認(1次戻の場合)
		if (isRevert(workflowDto) && workflowDto.getWorkflowStage() == 0) {
			// 1次戻の場合
			return getRevertAbbrNaming();
		}
		return getApprovalAbbrNaming();
	}
	
	/**
	 * 休日出勤申請の略称(備考用)を取得する。
	 * @param dto 休日出勤申請情報
	 * @return 休日出勤の略称(備考用)
	 */
	protected String getWorkOnHolidayAbbr(WorkOnHolidayRequestDtoInterface dto) {
		// 振替申請を取得
		int substitute = dto.getSubstitute();
		// 振替申請の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替出勤略称を設定
			return getSubstituteWorkAbbrNaming();
		}
		// 休日出勤申請の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤略称を設定
			return getWorkOnHolidayAbbrNaming();
		}
		return "";
	}
	
	/**
	 * 休日出勤申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @param isActualList 実績一覧フラグ(true：実績一覧、false：実績一覧ではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addWorkOnHolidayRequestList(boolean containNotApproved, boolean isActualList) throws MospException {
		// 休日出勤申請毎に処理
		for (WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto : workOnHolidayRequestList) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(workOnHolidayRequestDto.getRequestDate());
			// 休日出勤略称を設定
			String requestAbbr = getWorkOnHolidayAbbr(workOnHolidayRequestDto);
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(workOnHolidayRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, requestAbbr + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, requestAbbr + getWorkflowStatusAbbr(workflowDto));
			// 勤務形態確認
			if (!isWorkTypeSetWorkOnHoliday(dto, containNotApproved)) {
				// 勤務形態が設定されている場合(実績が存在する場合)
				continue;
			}
			// チェックボックス要否設定
			setNeedCheckbox(dto, workOnHolidayRequestDto);
			// 実績一覧の場合
			if (isActualList) {
				dto.setWorkTypeCode("");
				continue;
			}
			// 休日出勤申請情報から休日出勤時の予定勤務形態を取得して設定
			dto.setWorkTypeCode(getWorkOnHolidayWorkType(workOnHolidayRequestDto));
			// 振替申請確認
			if (workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 振替申請しない場合
				// 始業予定時間設定
				dto.setStartTime(getWorkTypeStartTime(dto));
				// 終業予定時間設定
				dto.setEndTime(getWorkTypeEndTime(dto));
				// 状態設定(予定)
				dto.setApplicationInfo(getScheduleNaming());
			} else {
				// 勤怠一覧情報に勤務形態の内容を設定
				setDtoFields(dto, dto.getWorkTypeCode(), true, false,
						workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM,
						workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM);
				// チェックボックス要否設定
				setNeedCheckbox(dto, workOnHolidayRequestDto);
			}
			// 勤怠データ情報取得(下書確認)
			AttendanceDtoInterface attendanceDto = getAttendanceDtoListDto(dto.getWorkDate());
			if (attendanceDto != null) {
				// ワークフロー情報取得
				WorkflowDtoInterface attendanceWorkflow = getWorkflow(attendanceDto.getWorkflow());
				// 勤怠一覧情報に勤怠データ情報を設定(下書で上書)
				setDtoFields(dto, attendanceDto, attendanceWorkflow);
				// チェックボックス要否設定
				setNeedCheckbox(dto, workOnHolidayRequestDto);
			}
		}
	}
	
	/**
	 * 勤務形態設定判定(休出申請情報取得用)
	 * @param dto 勤怠情報DTO
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * <br> containNotApprovedはアドオンで使用する.
	 * @return 勤務形態設定判定(true:未設定、false:空も含めて設定)
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected boolean isWorkTypeSetWorkOnHoliday(AttendanceListDto dto, boolean containNotApproved)
			throws MospException {
		return dto.getWorkTypeCode() == null;
		
	}
	
	/**
	 * 振替休日情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addSubstituteList(boolean containNotApproved) throws MospException {
		// 振替休日回数
		float substituteHolidays = 0F;
		// 振替休日毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// 振替休日の元である休日出勤申請情報を取得
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHoliday
				.findForWorkflow(substituteDto.getWorkflow());
			// 休日出勤申請情報確認
			if (workOnHolidayRequestDto == null) {
				continue;
			}
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(workOnHolidayRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				continue;
			}
			// 振替休日と同日の休日出勤申請情報を取得
			WorkOnHolidayRequestDtoInterface sameDayWorkOnHolidayRequestDto = workOnHoliday
				.findForKeyOnWorkflow(personalId, substituteDto.getSubstituteDate());
			// 振替休日と同日の休日出勤申請情報確認
			if (sameDayWorkOnHolidayRequestDto != null) {
				// 振替休日と同日の休日出勤申請情報のワークフロー情報取得
				WorkflowDtoInterface sameDayWorkflowDto = getWorkflow(workOnHolidayRequestDto.getWorkflow());
				// 申請情報要否確認
				if (isRequestNeeded(sameDayWorkflowDto, containNotApproved)) {
					continue;
				}
			}
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(substituteDto.getSubstituteDate());
			// 備考設定
			addRemark(dto, getSubstituteAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
			// 振替休日回数加算
			substituteHolidays += getHolidayTimes(substituteDto.getSubstituteRange());
			// 休暇範囲確認
			if (!isSubstituteAllHoliday(substituteDto, containNotApproved)) {
				// 全休以外の場合は設定不要
				continue;
			}
			// 勤務形態コード設定
			dto.setWorkTypeCode("");
			// 勤務形態略称設定
			setWorkTypeAbbrSubstituteAllHoliday(substituteDto, dto);
			// 状態設定
			dto.setApplicationInfo(
					workflow.getWorkflowStatus(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
			// 無休時短時間設定
			setShortUnpaid(dto);
		}
		// 最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 集計値設定
		// 休日出勤回数
		dto.setSubstituteHolidays(substituteHolidays);
	}
	
	/**
	 * 振替休日範囲判定
	 * @param substituteDto 代休申請DTO
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @return 振替休日範囲判定(true:全日振替休日、false:半日振替休日)
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected boolean isSubstituteAllHoliday(SubstituteDtoInterface substituteDto, boolean containNotApproved)
			throws MospException {
		return substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL;
	}
	
	/**
	 * 勤怠一覧情報に振替休日(全休)の略称を設定する。<br>
	 * @param substituteDto 振替休日DTO
	 * @param dto 対象日の勤怠一覧情報
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void setWorkTypeAbbrSubstituteAllHoliday(SubstituteDtoInterface substituteDto, AttendanceListDto dto)
			throws MospException {
		// 勤怠一覧情報に振替休日(全休)の略称を設定
		dto.setWorkTypeAbbr(TimeRequestUtility.getSubstituteAbbr(substituteDto.getSubstituteType(), mospParams));
	}
	
	/**
	 * 代休申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addSubHolidayRequestList(boolean containNotApproved) throws MospException {
		// 代休回数
		float subHolidays = 0F;
		// 代休申請毎に処理
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(subHolidayRequestDto.getRequestDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(subHolidayRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, getSubHolidayAbbrNaming() + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, getSubHolidayAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
			// 代休回数加算
			subHolidays += getHolidayTimes(subHolidayRequestDto.getHolidayRange());
			// 休暇範囲確認
			if (!isAllSubHoliday(subHolidayRequestDto)) {
				// 全休以外の場合は設定不要
				continue;
			}
			// 勤務形態コード設定
			dto.setWorkTypeCode("");
			// 代休種別を取得
			int subHolidayType = subHolidayRequestDto.getWorkDateSubHolidayType();
			// 勤務形態略称設定
			dto.setWorkTypeAbbr(TimeRequestUtility.getSubHolidayAbbr(subHolidayType, mospParams));
			// 状態設定
			dto.setApplicationInfo(
					workflow.getWorkflowStatus(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
			// 無休時短時間設定
			setShortUnpaid(dto);
		}
		// 最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 集計値設定
		// 代休回数
		dto.setSubHolidays(subHolidays);
	}
	
	/**
	 * 代休範囲判定
	 * @param subHolidayRequestDto 代休申請DTO
	 * @return 代休範囲判定(true:全日代休、false:半日代休)
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected boolean isAllSubHoliday(SubHolidayRequestDtoInterface subHolidayRequestDto) throws MospException {
		return subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL;
		
	}
	
	/**
	 * 残業申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addOvertimeRequestList(boolean containNotApproved) throws MospException {
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface overtimeRequestDto : overtimeRequestList) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(overtimeRequestDto.getRequestDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(overtimeRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, getOvertimeAbbrNaming() + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, getOvertimeAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
		}
	}
	
	/**
	 * 勤務形態変更申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @param isActualList 実績一覧フラグ(true：実績一覧、false：実績一覧ではない)
	 * @param isSchedule 予定確認フラグ(true：予定確認、false：予定確認ではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addWorkTypeChangeRequestList(boolean containNotApproved, boolean isActualList, boolean isSchedule)
			throws MospException {
		// 勤務形態変更申請毎に処理
		for (WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto : workTypeChangeRequestList) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(workTypeChangeRequestDto.getRequestDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(workTypeChangeRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, getWorkTypeChangeAbbrNaming() + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, getWorkTypeChangeAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
			// ワークフロー状況確認
			boolean isComplete = workflow.isCompleted(workflowDto);
			if (isComplete && dto.getWorkTypeCode() == null) {
				// 勤務形態設定
				dto.setWorkTypeCode(workTypeChangeRequestDto.getWorkTypeCode());
			}
			// 実績一覧の場合
			if (isActualList) {
				continue;
			}
			if (isComplete) {
				// 勤務形態設定
				dto.setWorkTypeCode(workTypeChangeRequestDto.getWorkTypeCode());
				// 始業予定時間設定
				dto.setStartTime(getWorkTypeStartTime(dto));
				// 終業予定時間設定
				dto.setEndTime(getWorkTypeEndTime(dto));
			}
			// 勤怠データ情報取得
			AttendanceDtoInterface attendanceDto = getAttendanceDtoListDto(dto.getWorkDate());
			// 勤怠データ確認
			if (attendanceDto == null) {
				// 勤怠データが存在しない場合
				dto.setNeedCheckbox(true);
				dto.setApplicationInfo(getScheduleNaming());
			} else {
				// 予定確認の場合
				if (isSchedule) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface attendanceWorkflow = getWorkflow(attendanceDto.getWorkflow());
				// 勤怠一覧情報に勤怠データ情報を設定(下書で上書)
				setDtoFields(dto, attendanceDto, attendanceWorkflow);
			}
		}
	}
	
	/**
	 * 時差出勤申請情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @param isActualList 実績一覧フラグ(true：実績一覧、false：実績一覧ではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addDifferenceRequestList(boolean containNotApproved, boolean isActualList) throws MospException {
		// 時差出勤申請情報取得
		List<DifferenceRequestDtoInterface> list = difference.getDifferenceRequestList(personalId, firstDate, lastDate);
		// 時差出勤申請毎に処理
		for (DifferenceRequestDtoInterface differenceRequestDto : list) {
			// 対象日における勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(differenceRequestDto.getRequestDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(differenceRequestDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				if (containNotApproved && workflow.isDraft(workflowDto)) {
					// 下書の場合
					addRemark(dto, getTimeDefferenceAbbrNaming() + getDraftAbbrNaming());
				}
				continue;
			}
			// 備考設定
			addRemark(dto, getTimeDefferenceAbbrNaming() + getWorkflowStatusAbbr(workflowDto));
			// ワークフロー状況確認
			boolean isComplete = workflow.isCompleted(workflowDto);
			boolean isApprovable = workflow.isApprovable(workflowDto);
			boolean isFirstReverted = workflow.isFirstReverted(workflowDto);
			if (isComplete) {
				// 勤務形態設定
				dto.setWorkTypeCode(differenceRequestDto.getDifferenceType());
			}
			// 実績一覧の場合
			if (isActualList) {
				continue;
			}
			if (isComplete) {
				// 勤務形態設定
				dto.setWorkTypeCode(differenceRequestDto.getDifferenceType());
				// 始業予定時間設定
				dto.setStartTime(differenceRequestDto.getRequestStart());
				// 終業予定時間設定
				dto.setEndTime(differenceRequestDto.getRequestEnd());
			} else if (isApprovable || isFirstReverted) {
				// 勤務形態設定
				dto.setWorkTypeCode(differenceRequestDto.getDifferenceType());
			}
			// 勤怠データ情報取得
			AttendanceDtoInterface attendanceDto = getAttendanceDtoListDto(dto.getWorkDate());
			// 勤怠データ確認
			if (attendanceDto == null) {
				// 勤怠データが存在しない場合
				dto.setNeedCheckbox(true);
				dto.setApplicationInfo(getScheduleNaming());
			} else {
				// ワークフロー情報取得
				WorkflowDtoInterface attendanceWorkflow = getWorkflow(attendanceDto.getWorkflow());
				// 勤怠一覧情報に勤怠データ情報を設定(下書で上書)
				setDtoFields(dto, attendanceDto, attendanceWorkflow);
				if (isComplete || isApprovable || isFirstReverted) {
					// 勤務形態設定
					dto.setWorkTypeCode(differenceRequestDto.getDifferenceType());
				}
			}
		}
	}
	
	/**
	 * 時間単位年休の利用確認を行う。<br>
	 * 時間単位年休を利用しない場合は、時間単位年休の合計値をnullにする。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void confirmTimelyPaidHoliday() throws MospException {
		// 有給休暇コード取得
		String paidHolidayCode = applicationDto.getPaidHolidayCode();
		// 有給休暇設定取得
		PaidHolidayDtoInterface paidHolidayDto = paidHoliday.getPaidHolidayInfo(paidHolidayCode, lastDate);
		// 時間単位年休利用フラグ確認
		if (paidHolidayDto != null && paidHolidayDto.getTimelyPaidHolidayFlag() == MospConst.INACTIVATE_FLAG_OFF) {
			return;
		}
		// 時間単位年休無効の場合
		// 勤怠一覧情報リスト最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 有給時間
		dto.setPaidHolidayTime(null);
	}
	
	/**
	 * 同日複数申請を確認する。<br>
	 * 同日に複数の休暇申請、振替休日、代休申請があり、合わせて終日休暇となる場合に、
	 * 状態及びチェックボックス要否を再設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：未承認申請も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkPluralRequest(boolean containNotApproved) throws MospException {
		// 勤怠一覧情報毎に処理
		attendanceList: for (AttendanceListDto dto : attendanceList) {
			// 休日日数準備
			float holidayTimes = 0F;
			// 午前休ワークフロー
			WorkflowDtoInterface anteWorkflowDto = null;
			// 午後休ワークフロー
			WorkflowDtoInterface postWorkflowDto = null;
			// 申請エンティティを取得
			RequestEntity entity = getRequestEntity(personalId, dto.getWorkDate());
			// 休暇申請情報確認
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				// 休暇日確認
				if (DateUtility.isTermContain(dto.getWorkDate(), holidayRequestDto.getRequestStartDate(),
						holidayRequestDto.getRequestEndDate()) == false) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = getWorkflow(holidayRequestDto.getWorkflow());
				// 申請情報要否確認
				if (!isRequestNeeded(workflowDto, containNotApproved)) {
					continue;
				}
				// 休暇範囲確認
				if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合は処理不要
					continue attendanceList;
				}
				// 休日日数加算
				holidayTimes += getHolidayTimes(holidayRequestDto.getHolidayRange());
				// 半日勤務形態略称を取得
				String workTypeAbbrInitial = substrForHalfWorkType(getWorkTypeAbbr(holidayRequestDto));
				if (dto.getWorkTypeAnteAbbr() == null
						&& holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					dto.setWorkTypeAnteAbbr(workTypeAbbrInitial);
					anteWorkflowDto = workflowDto;
				}
				if (dto.getWorkTypePostAbbr() == null
						&& holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					dto.setWorkTypePostAbbr(workTypeAbbrInitial);
					postWorkflowDto = workflowDto;
				}
			}
			// 代休申請情報確認
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// 代休日確認
				if (subHolidayRequestDto.getRequestDate().compareTo(dto.getWorkDate()) != 0) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = getWorkflow(subHolidayRequestDto.getWorkflow());
				// 申請情報要否確認
				if (!isRequestNeeded(workflowDto, containNotApproved)) {
					continue;
				}
				// 休暇範囲が全休の場合
				if (subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// チェックボックス設定
					dto.setNeedCheckbox(false);
					continue attendanceList;
				}
				// 休日日数加算
				holidayTimes += getHolidayTimes(subHolidayRequestDto.getHolidayRange());
				if (dto.getWorkTypeAnteAbbr() == null
						&& subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					dto.setWorkTypeAnteAbbr(getSubHolidayAbbrNaming());
					anteWorkflowDto = workflowDto;
				}
				if (dto.getWorkTypePostAbbr() == null
						&& subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					dto.setWorkTypePostAbbr(getSubHolidayAbbrNaming());
					postWorkflowDto = workflowDto;
				}
			}
			// 振出・休出申請情報確認
			for (WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto : workOnHolidayRequestList) {
				// 出勤日確認
				if (!workOnHolidayRequestDto.getRequestDate().equals(dto.getWorkDate())) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = workflow
					.getLatestWorkflowInfo(workOnHolidayRequestDto.getWorkflow());
				// 申請情報要否確認
				if (!isRequestNeeded(workflowDto, containNotApproved)) {
					continue;
				}
				// 全休の場合
				if (holidayTimes == TimeConst.HOLIDAY_TIMES_ALL) {
					break;
				}
				// 振替申請区分取得
				int substitute = workOnHolidayRequestDto.getSubstitute();
				// 振替出勤(全日)又は休日出勤又は勤務形態変更有の場合
				if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
						|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF
						|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
					// 処理不要
					continue attendanceList;
				}
				// 半日振替の振替である場合（1日勤務の場合）
				if (entity.isHalfPostpone(!containNotApproved)) {
					// 全日でない場合
					if (!entity.isAmPmHalfSubstitute(!containNotApproved)) {
						continue;
					}
				}
				// 休日日数加算
				holidayTimes += TimeConst.HOLIDAY_TIMES_HALF;
				// 振替出勤(午前)の場合
				if (dto.getWorkTypeAnteAbbr() == null && substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
					// 設定
					dto.setWorkTypeAnteAbbr(getHalfSubstituteWorkAbbrNaming());
					anteWorkflowDto = workflowDto;
				}
				// 振替出勤(午後)の場合
				if (dto.getWorkTypePostAbbr() == null && substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
					// 設定
					dto.setWorkTypePostAbbr(getHalfSubstituteWorkAbbrNaming());
					postWorkflowDto = workflowDto;
				}
			}
			// 振替休日情報確認
			for (SubstituteDtoInterface substituteDto : substituteList) {
				// 振替日確認
				if (!substituteDto.getSubstituteDate().equals(dto.getWorkDate())) {
					continue;
				}
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = getWorkflow(substituteDto.getWorkflow());
				// 申請情報要否確認
				if (!isRequestNeeded(workflowDto, containNotApproved)) {
					continue;
				}
				// 全休の場合
				if (holidayTimes == TimeConst.HOLIDAY_TIMES_ALL) {
					break;
				}
				// 振替休日確認
				if (isPluralRequestSubstituteAllHoliday(substituteDto)) {
					// 全休の場合は処理不要
					continue attendanceList;
				}
				// 半日振替の振替である場合（1日勤務の場合）
				if (entity.isHalfPostpone(!containNotApproved)) {
					continue;
				}
				// 振替休日における振替出勤申請の取得
				WorkOnHolidayRequestDtoInterface workOnHolidayDto = workOnHoliday.findForKeyOnWorkflow(personalId,
						substituteDto.getSubstituteDate());
				if (workOnHolidayDto != null) {
					// 振替出勤申請がある場合
					WorkflowDtoInterface workOnHolidayWorkflow = workflow
						.getLatestWorkflowInfo(workOnHolidayDto.getWorkflow());
					if (!WorkflowUtility.isDraft(workOnHolidayWorkflow)) {
						continue;
					}
				}
				// 休日日数加算
				holidayTimes += getHolidayTimes(substituteDto.getSubstituteRange());
				if (dto.getWorkTypeAnteAbbr() == null
						&& substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					dto.setWorkTypeAnteAbbr(TimeNamingUtility.substituteHolidayAbbr(mospParams));
					anteWorkflowDto = workflowDto;
				}
				if (dto.getWorkTypePostAbbr() == null
						&& substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					dto.setWorkTypePostAbbr(TimeNamingUtility.substituteHolidayAbbr(mospParams));
					postWorkflowDto = workflowDto;
				}
			}
			// 休日日数確認
			if (holidayTimes >= TimeConst.HOLIDAY_TIMES_ALL) {
				setDtoPluralRequest(dto, anteWorkflowDto, postWorkflowDto);
			}
		}
	}
	
	/**
	 * 振替休日範囲判定(checkPluralRequest用)
	 * @param substituteDto 代休申請DTO
	 * @return 振替休日範囲判定(true:全日振替休日、false:半日振替休日)
	 */
	protected boolean isPluralRequestSubstituteAllHoliday(SubstituteDtoInterface substituteDto) {
		return substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL;
	}
	
	/**
	 * 同日複数申請勤怠DTO設定
	 * @param dto 勤怠DTO
	 * @param anteWorkflowDto 午前休ワークフローDTO
	 * @param postWorkflowDto 午後休ワークフローDTO
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void setDtoPluralRequest(AttendanceListDto dto, WorkflowDtoInterface anteWorkflowDto,
			WorkflowDtoInterface postWorkflowDto) throws MospException {
		// 勤務形態コード設定
		dto.setWorkTypeCode("");
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(dto.getWorkTypeAnteAbbr() + getSlashNaming() + dto.getWorkTypePostAbbr());
		if (TimeNamingUtility.substituteHolidayAbbr(mospParams).equals(dto.getWorkTypeAnteAbbr())) {
			// 午前振休である場合
			if (TimeNamingUtility.substituteHolidayAbbr(mospParams).equals(dto.getWorkTypePostAbbr())) {
				// 午後振休である場合
				dto.setWorkTypeAbbr(TimeNamingUtility.substituteHolidayAbbr(mospParams));
			} else {
				// 午後振休でない場合
				dto.setWorkTypeAbbr(
						getAnteMeridiemSubstituteHolidayAbbrNaming() + getSlashNaming() + dto.getWorkTypePostAbbr());
			}
		} else {
			// 午前振休でない場合
			if (TimeNamingUtility.substituteHolidayAbbr(mospParams).equals(dto.getWorkTypePostAbbr())) {
				// 午後振休である場合
				dto.setWorkTypeAbbr(
						dto.getWorkTypeAnteAbbr() + getSlashNaming() + getPostMeridiemSubstituteHolidayAbbrNaming());
			}
		}
		// 無休時短時間設定
		setShortUnpaid(dto);
		// チェックボックス設定
		dto.setNeedCheckbox(false);
		// 状態設定
		setApplicationInfo(dto, anteWorkflowDto, postWorkflowDto);
		
	}
	
	/**
	 * 無休時短時間を設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setShortUnpaid(AttendanceListDto dto) throws MospException {
		dto.setShortUnpaid(null);
	}
	
	/**
	 * 勤怠情報を取得し勤怠一覧情報リストに設定する。<br>
	 * @param containNotApproved 未承認要否フラグ(true：下書き等未承認勤怠も含める、false：承認済のみ)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addActualList(boolean containNotApproved) throws MospException {
		// 勤怠情報毎に処理
		for (AttendanceDtoInterface attendanceDto : attendanceDtoList) {
			// 勤怠情報の勤務日から勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(attendanceDto.getWorkDate());
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(attendanceDto.getWorkflow());
			// 申請情報要否確認
			if (isRequestNeeded(workflowDto, containNotApproved) == false) {
				continue;
			}
			// 勤怠一覧情報に値を設定
			setDtoFields(dto, attendanceDto, workflowDto);
		}
	}
	
	/**
	 * 勤怠情報(下書)を勤怠一覧情報リストに設定する。<br>
	 * 但し、既に対象日の勤怠一覧情報に勤務形態が設定されている場合は設定しない。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addDraftList() throws MospException {
		// 勤怠情報毎に処理
		for (AttendanceDtoInterface attendanceDto : attendanceDtoList) {
			// 勤怠情報の勤務日から勤怠一覧情報を取得
			AttendanceListDto dto = getAttendanceListDto(attendanceDto.getWorkDate());
			// 勤怠情報(下書)要否確認
			if (dto.getWorkTypeCode() != null) {
				// 勤務形態が既に設定されている場合
				continue;
			}
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = getWorkflow(attendanceDto.getWorkflow());
			// ワークフロー状況確認
			if (workflow.isDraft(workflowDto) == false) {
				// 下書でない場合
				continue;
			}
			// 勤怠一覧情報に値を設定
			setDtoFields(dto, attendanceDto, workflowDto);
		}
	}
	
	/**
	 * カレンダ情報を取得し勤怠一覧情報リストに設定する。<br>
	 * 但し、既に対象日の勤怠一覧情報に勤務形態が設定されている場合は設定しない。<br>
	 * 出勤簿では、出勤予定日が不要となる。<br>
	 * 勤怠一覧では、勤務時間及び休憩時間が不要となる。<br>
	 * @param needWorkDay  出勤予定日要否(true：要、false：不要)
	 * @param needWorkTime 勤務時間及び休憩時間要否(true：要、false：不要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addScheduleList(boolean needWorkDay, boolean needWorkTime) throws MospException {
		// 人事退職情報から退職日を取得
		Date retireDate = retirement.getRetireDate(personalId);
		// 勤怠一覧情報にカレンダ日情報を設定
		for (AttendanceListDto dto : attendanceList) {
			// カレンダ日情報取得(勤怠一覧情報の勤務日で取得)及び確認
			ScheduleDateDtoInterface scheduleDateDto = getScheduleDateDto(dto.getWorkDate());
			if (scheduleDateDto == null || scheduleDateDto.getWorkTypeCode().isEmpty()) {
				// カレンダ日情報が存在しない場合
				continue;
			}
			// カレンダ日情報要否確認
			if (dto.getWorkTypeCode() != null) {
				// 勤務形態が既に設定されている場合
				continue;
			}
			// 退職日確認
			if (retireDate != null && retireDate.before(dto.getWorkDate())) {
				// 既に退職している場合
				continue;
			}
			// 休職期間確認
			SuspensionDtoInterface suspensionDto = suspension.getSuspentionInfo(personalId, dto.getWorkDate());
			if (suspensionDto != null) {
				// 休職している場合
				StringBuffer sb = new StringBuffer();
				sb.append(PfNameUtility.suspension(mospParams));
				if (suspensionDto.getSuspensionReason() != null && !suspensionDto.getSuspensionReason().isEmpty()) {
					// 休職理由がある場合
					sb.append(mospParams.getName("SingleColon"));
					sb.append(suspensionDto.getSuspensionReason());
				}
				addRemark(dto, sb.toString());
				continue;
			}
			// 勤怠一覧情報に勤務形態の内容を設定
			setDtoFields(dto, scheduleDateDto.getWorkTypeCode(), needWorkDay, needWorkTime);
		}
	}
	
	/**
	 * 休暇申請情報から勤務形態略称を取得する。<br>
	 * @param dto 休暇申請情報
	 * @return 勤務形態略称
	 * @throws MospException 特別休暇或いはその他休暇の略称取得に失敗した場合
	 */
	protected String getWorkTypeAbbr(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇区分毎に勤務形態略称を取得
		switch (dto.getHolidayType1()) {
			case TimeConst.CODE_HOLIDAYTYPE_HOLIDAY:
				// 有給休暇の場合
				if (dto.getHolidayType2().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY))) {
					return getPaidHolidayAbbrNaming();
				} else {
					return getStockHolidayAbbrNaming();
				}
			case TimeConst.CODE_HOLIDAYTYPE_SPECIAL:
			case TimeConst.CODE_HOLIDAYTYPE_OTHER:
			case TimeConst.CODE_HOLIDAYTYPE_ABSENCE:
				// 特別休暇・その他休暇・欠勤の場合
				return holiday.getHolidayAbbr(dto.getHolidayType2(), dto.getRequestEndDate(), dto.getHolidayType1());
			default:
				return null;
		}
	}
	
	/**
	 * 未承認確認を行う。<br>
	 * ワークフロー状況及び段階から、承認可能であるかを確認する。<br>
	 * @param dto ワークフロー情報
	 * @return 未承認確認結果(true：未承認、false：未承認でない)
	 */
	protected boolean isApprovable(WorkflowDtoInterface dto) {
		// ワークフロー状況取得
		String status = dto.getWorkflowStatus();
		// 未承認、承認済(完了でない)の場合
		if (status.equals(PlatformConst.CODE_STATUS_APPLY) || status.equals(PlatformConst.CODE_STATUS_APPROVED)) {
			// 未承認
			return true;
		}
		// 差戻、取消(承認解除)の場合
		if (status.equals(PlatformConst.CODE_STATUS_CANCEL) || status.equals(PlatformConst.CODE_STATUS_APPROVED)) {
			// 段階が0より大きい場合(申請者が操作者でない場合)
			if (dto.getWorkflowStage() > 0) {
				return true;
			}
		}
		// それ以外の場合
		return false;
	}
	
	/**
	 * チェックボックス要否(勤怠一覧用)を取得する。<br>
	 * @param dto ワークフロー情報
	 * @return チェックボックス要否(勤怠一覧用)
	 */
	protected boolean isNeedCheckBox(WorkflowDtoInterface dto) {
		// 下書の場合
		if (workflow.isDraft(dto)) {
			return true;
		}
		// 差戻の場合
		if (isRevert(dto)) {
			// 段階が0(申請者)の場合
			if (dto.getWorkflowStage() == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 状態リンク要否(勤怠一覧用)を取得する。<br>
	 * @param dto ワークフロー情報
	 * @return 状態リンク要否(勤怠一覧用)
	 */
	protected boolean getNeedStatusLink(WorkflowDtoInterface dto) {
		return !workflow.isDraft(dto);
	}
	
	/**
	 * カレンダ日情報群から対象日のカレンダ日情報を取得する。<br>
	 * カレンダ日情報が設定されていない場合にnullを返す可能性がある。<br>
	 * @param targetDate 対象日
	 * @return カレンダ日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ScheduleDateDtoInterface getScheduleDateDto(Date targetDate) throws MospException {
		// カレンダ日情報を取得
		return scheduleDates.get(targetDate);
	}
	
	/**
	 * 勤怠データ情報リストから対象日の勤怠一覧情報を取得する。<br>
	 * @param targetDate 対象日
	 * @return 勤怠データ情報
	 */
	protected AttendanceDtoInterface getAttendanceDtoListDto(Date targetDate) {
		// 勤怠一覧情報確認
		if (attendanceDtoList == null) {
			return null;
		}
		// 勤怠一覧情報リストから対象日の勤怠一覧情報を取得
		for (AttendanceDtoInterface dto : attendanceDtoList) {
			// 勤務日確認
			if (targetDate.equals(dto.getWorkDate())) {
				return dto;
			}
		}
		return null;
	}
	
	/**
	 * 勤怠一覧情報リストから対象日の勤怠一覧情報を取得する。<br>
	 * @param targetDate 対象日
	 * @return 勤怠一覧情報
	 */
	protected AttendanceListDto getAttendanceListDto(Date targetDate) {
		// 勤怠一覧情報リストから対象日の勤怠一覧情報を取得
		return AttendanceUtility.getAttendanceListDto(attendanceList, targetDate);
	}
	
	/**
	 * 限度基準情報を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setLimitStandard() throws MospException {
		// 勤怠設定エンティティを取得
		TimeSettingEntityInterface entity = timeSettingReference.getEntity(timeSettingDto);
		// 法定外残業時間合計値準備
		int overtimeTotal = 0;
		// 勤怠一覧情報毎に残業時間を確認
		for (AttendanceListDto dto : attendanceList) {
			// 法定外残業時間確認
			if (dto.getOvertimeOut() == null || dto.getOvertimeOut().intValue() == 0) {
				continue;
			}
			// 法定外残業時間加算
			overtimeTotal += dto.getOvertimeOut().intValue();
			// 外残のスタイル文字列を設定
			dto.setOvertimeStyle(entity.getOneMonthStyle(overtimeTotal));
		}
	}
	
	/**
	 * 勤怠一覧情報リストを集計する。<br>
	 * 集計結果は、リストの最終レコードに設定される。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void totalAttendanceList() throws MospException {
		// 集計値準備
		// 勤務時間(分)
		int workTimeTotal = 0;
		// 休憩時間(分)
		int restTimeTotal = 0;
		// 私用外出時間(分)
		int privateTimeTotal = 0;
		// 公用外出時間(分)
		int publicTimeTotal = 0;
		// 分単位休暇A時間(分)
		int minutelyHolidayATimeTotal = 0;
		// 分単位休暇B時間(分)
		int minutelyHolidayBTimeTotal = 0;
		// 遅刻時間(分)
		int lateTimeTotal = 0;
		// 早退時間(分)
		int leaveEarlyTimeTotal = 0;
		// 遅刻早退時間(分)
		int lateLeaveEarlyTimeTotal = 0;
		// 残業時間(分)
		int overtimeTotal = 0;
		// 内残時間(分)
		int overtimeInTotal = 0;
		// 外残時間(分)
		int overtimeOutTotal = 0;
		// 休出時間(分)
		int holidayWorkTimeTotal = 0;
		// 無休時短時間(分)
		int shortUnpaidTotal = 0;
		// 深夜時間(分)
		int lateNightTimeTotal = 0;
		// 出勤回数
		int workDays = 0;
		// 遅刻回数
		int lateDays = 0;
		// 早退回数
		int leaveEarlyDays = 0;
		// 残業回数
		int overtimeDays = 0;
		// 休出回数
		int holidayWorkDays = 0;
		// 深夜回数
		int lateNightDays = 0;
		// 所定休日回数
		int prescribedHolidays = 0;
		// 法定休日回数
		int legalHolidays = 0;
		// 休日回数
		int holidays = 0;
		// 勤怠一覧情報毎に集計
		for (AttendanceListDto dto : attendanceList) {
			// 勤務時間
			workTimeTotal += dto.getWorkTime() == null ? 0 : dto.getWorkTime().intValue();
			// 休憩時間
			restTimeTotal += dto.getRestTime() == null ? 0 : dto.getRestTime().intValue();
			// 私用外出時間
			privateTimeTotal += dto.getPrivateTime() == null ? 0 : dto.getPrivateTime().intValue();
			// 公用外出時間
			publicTimeTotal += dto.getPublicTime() == null ? 0 : dto.getPublicTime().intValue();
			// 分単位休暇A時間
			minutelyHolidayATimeTotal += dto.getMinutelyHolidayATime() == null ? 0
					: dto.getMinutelyHolidayATime().intValue();
			// 分単位休暇B時間
			minutelyHolidayBTimeTotal += dto.getMinutelyHolidayBTime() == null ? 0
					: dto.getMinutelyHolidayBTime().intValue();
			// 遅刻時間
			lateTimeTotal += dto.getLateTime() == null ? 0 : dto.getLateTime().intValue();
			// 早退時間
			leaveEarlyTimeTotal += dto.getLeaveEarlyTime() == null ? 0 : dto.getLeaveEarlyTime().intValue();
			// 遅刻早退時間
			lateLeaveEarlyTimeTotal += dto.getLateLeaveEarlyTime() == null ? 0 : dto.getLateLeaveEarlyTime().intValue();
			// 残業時間
			overtimeTotal += dto.getOvertime() == null ? 0 : dto.getOvertime().intValue();
			// 内残時間
			overtimeInTotal += dto.getOvertimeIn() == null ? 0 : dto.getOvertimeIn().intValue();
			// 外残時間
			overtimeOutTotal += dto.getOvertimeOut() == null ? 0 : dto.getOvertimeOut().intValue();
			// 休出時間
			holidayWorkTimeTotal += dto.getHolidayWorkTime() == null ? 0 : dto.getHolidayWorkTime().intValue();
			// 無休時短時間
			shortUnpaidTotal += dto.getShortUnpaid() == null ? 0 : dto.getShortUnpaid().intValue();
			// 深夜時間
			lateNightTimeTotal += dto.getLateNightTime() == null ? 0 : dto.getLateNightTime().intValue();
			// 出勤回数
			workDays += dto.getGoingWork();
			// 遅刻回数
			lateDays += countHours(dto.getLateTime());
			// 早退回数
			leaveEarlyDays += countHours(dto.getLeaveEarlyTime());
			// 残業回数
			overtimeDays += countHours(dto.getOvertime());
			// 深夜回数
			lateNightDays += countHours(dto.getLateNightTime());
			// 勤務形態確認
			if (dto.getWorkTypeCode() == null || dto.getWorkTypeCode().isEmpty()) {
				continue;
			}
			// 休出回数
			holidayWorkDays += TimeUtility.isWorkOnLegalHoliday(dto.getWorkTypeCode())
					|| TimeUtility.isWorkOnPrescribedHoliday(dto.getWorkTypeCode()) ? 1 : 0;
			// 所定休日回数
			prescribedHolidays += TimeUtility.isPrescribedHoliday(dto.getWorkTypeCode()) ? 1 : 0;
			// 法定休日回数
			legalHolidays += TimeUtility.isLegalHoliday(dto.getWorkTypeCode()) ? 1 : 0;
			// 休日回数
			holidays = prescribedHolidays + legalHolidays;
		}
		// 最終レコード取得
		AttendanceListDto dto = attendanceList.get(attendanceList.size() - 1);
		// 集計値設定
		dto.setWorkTimeTotal(workTimeTotal);
		dto.setRestTimeTotal(restTimeTotal);
		dto.setPrivateTimeTotal(privateTimeTotal);
		dto.setPublicTimeTotal(publicTimeTotal);
		dto.setMinutelyHolidayATimeTotal(minutelyHolidayATimeTotal);
		dto.setMinutelyHolidayBTimeTotal(minutelyHolidayBTimeTotal);
		dto.setLateTimeTotal(lateTimeTotal);
		dto.setLeaveEarlyTimeTotal(leaveEarlyTimeTotal);
		dto.setLateLeaveEarlyTimeTotal(lateLeaveEarlyTimeTotal);
		dto.setOvertimeTotal(overtimeTotal);
		dto.setOvertimeInTotal(overtimeInTotal);
		dto.setOvertimeOutTotal(overtimeOutTotal);
		dto.setHolidayWorkTimeTotal(holidayWorkTimeTotal);
		dto.setShortUnpaidTotal(shortUnpaidTotal);
		dto.setLateNightTimeTotal(lateNightTimeTotal);
		dto.setWorkDays(workDays);
		dto.setLateDays(lateDays);
		dto.setLeaveEarlyDays(leaveEarlyDays);
		dto.setOvertimeDays(overtimeDays);
		dto.setHolidayWorkDays(holidayWorkDays);
		dto.setLateNightDays(lateNightDays);
		dto.setPrescribedHolidays(prescribedHolidays);
		dto.setLegalHolidays(legalHolidays);
		dto.setHolidays(holidays);
		// 代休発生日数取得
		Float[] subHolidayDays = subHolidayRefer.getBirthSubHolidayTimes(personalId,
				attendanceList.get(0).getWorkDate(), attendanceList.get(attendanceList.size() - 1).getWorkDate());
		dto.setBirthPrescribedSubHoliday(subHolidayDays[0]);
		dto.setBirthLegalSubHoliday(subHolidayDays[1]);
		dto.setBirthMidnightSubHolidaydays(subHolidayDays[2]);
	}
	
	/**
	 * 勤怠一覧情報に勤怠情報の内容を設定する。<br>
	 * @param dto           設定対象勤怠一覧情報
	 * @param attendanceDto 勤怠情報
	 * @param workflowDto   ワークフロー情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceListDto dto, AttendanceDtoInterface attendanceDto,
			WorkflowDtoInterface workflowDto) throws MospException {
		// 出勤回数設定
		dto.setGoingWork(TimeBean.TIMES_WORK_DEFAULT);
		// 勤務形態
		dto.setWorkTypeCode(attendanceDto.getWorkTypeCode());
		// 始業時間
//		dto.setStartTime(attendanceDto.getStartTime());
		dto.setStartTime(attendanceDto.getActualStartTime());
		// 終業時間
//		dto.setEndTime(attendanceDto.getEndTime());
		dto.setEndTime(attendanceDto.getActualEndTime());
		// 勤務時間
		dto.setWorkTime(attendanceDto.getWorkTime());
		// 休憩時間
		dto.setRestTime(attendanceDto.getRestTime());
		// 私用外出時間
		dto.setPrivateTime(attendanceDto.getPrivateTime());
		// 公用外出時間
		dto.setPublicTime(attendanceDto.getPublicTime());
		// 分単位休暇A時間
		dto.setMinutelyHolidayATime(attendanceDto.getMinutelyHolidayATime());
		// 分単位休暇A全休
		dto.setMinutelyHolidayA(attendanceDto.getMinutelyHolidayA());
		// 分単位休暇B時間
		dto.setMinutelyHolidayBTime(attendanceDto.getMinutelyHolidayBTime());
		// 分単位休暇B全休
		dto.setMinutelyHolidayB(attendanceDto.getMinutelyHolidayB());
		// 遅刻時間
		dto.setLateTime(attendanceDto.getLateTime());
		// 早退時間
		dto.setLeaveEarlyTime(attendanceDto.getLeaveEarlyTime());
		// 遅刻早退時間
		dto.setLateLeaveEarlyTime(attendanceDto.getLateTime() + attendanceDto.getLeaveEarlyTime());
		// 残業時間
		dto.setOvertime(attendanceDto.getOvertime());
		// 内残時間
		dto.setOvertimeIn(attendanceDto.getOvertimeIn());
		// 外残時間
		dto.setOvertimeOut(attendanceDto.getOvertimeOut());
		// 休出時間
		dto.setHolidayWorkTime(attendanceDto.getLegalWorkTime());
		// 深夜時間
		dto.setLateNightTime(attendanceDto.getLateNightTime());
		// 無休時短時間
		dto.setShortUnpaid(attendanceDto.getShortUnpaid());
		// 勤怠コメント
		dto.setTimeComment(MospUtility.concat(attendanceDto.getTimeComment(), attendanceDto.getRemarks()));
		// 備考
		addAttendanceRemark(dto, attendanceDto);
		// 申請情報(ワークフロー情報から取得)
		dto.setApplicationInfo(
				workflow.getWorkflowStatus(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		// チェックボックス要否(勤怠一覧用)
		dto.setNeedCheckbox(isNeedCheckBox(workflowDto));
		// 状態リンク要否(勤怠一覧用)
		dto.setNeedStatusLink(getNeedStatusLink(workflowDto));
		// ワークフロー番号
		dto.setWorkflow(workflowDto.getWorkflow());
		// 修正情報
		dto.setCorrectionInfo(getCorrectionInfo(attendanceDto));
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_SETDTOFIELDS, dto, attendanceDto);
		
	}
	
	/**
	 * 修正情報を取得する。<br>
	 * @param attendanceDto 勤怠情報
	 * @return 修正情報（本/他）
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getCorrectionInfo(AttendanceDtoInterface attendanceDto) throws MospException {
		// 勤怠修正情報取得
		AttendanceCorrectionDtoInterface attendanceCorrectionDto = correction
			.getLatestAttendanceCorrectionInfo(personalId, attendanceDto.getWorkDate(), TIMES_WORK_DEFAULT);
		// 勤怠修正情報確認
		if (attendanceCorrectionDto == null) {
			return "";
		}
		// 勤怠修正者確認
		if (attendanceCorrectionDto.getPersonalId().equals(attendanceCorrectionDto.getCorrectionPersonalId())) {
			return getSelfCorrectAbbrNaming();
		}
		return getOtherCorrectAbbrNaming();
	}
	
	/**
	 * 勤怠一覧情報に勤怠情報の備考を追加する。<br>
	 * @param dto           追加対象勤怠一覧情報
	 * @param attendanceDto 勤怠情報
	 */
	protected void addAttendanceRemark(AttendanceListDto dto, AttendanceDtoInterface attendanceDto) {
		// 遅刻の場合
		if (!attendanceDto.getLateReason().isEmpty()) {
			addRemark(dto, (TimeNamingUtility.getLateAbbrNaming(mospParams) + " " + (MospUtility.getCodeName(mospParams,
					attendanceDto.getLateReason(), TimeConst.CODE_REASON_OF_LATE))));
		}
		// 早退の場合
		if (!attendanceDto.getLeaveEarlyReason().isEmpty()) {
			addRemark(dto, (TimeNamingUtility.getEarlyAbbrNaming(mospParams) + " " + (MospUtility
				.getCodeName(mospParams, attendanceDto.getLeaveEarlyReason(), TimeConst.CODE_REASON_OF_LEAVE_EARLY))));
		}
		// 直行確認
		if (attendanceDto.getDirectStart() == Integer.parseInt(MospConst.CHECKBOX_ON)) {
			addRemark(dto, TimeNamingUtility.directStart(mospParams));
		}
		// 直帰確認
		if (attendanceDto.getDirectEnd() == Integer.parseInt(MospConst.CHECKBOX_ON)) {
			addRemark(dto, TimeNamingUtility.directEnd(mospParams));
		}
	}
	
	/**
	 * 勤怠一覧情報に勤務形態情報の内容を設定する。<br>
	 * @param dto          設定対象勤怠一覧情報
	 * @param workTypeCode 勤務形態コード
	 * @param needWorkDay  出勤予定日要否(true：要、false：不要)
	 * @param needWorkTime 勤務時間、休憩時間要否(true：要、false：不要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceListDto dto, String workTypeCode, boolean needWorkDay, boolean needWorkTime)
			throws MospException {
		// 勤務形態確認(所定休日或いは法定休日)
		if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)
				|| workTypeCode.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			// 勤務形態設定
			dto.setWorkTypeCode(workTypeCode);
			// 状態設定(承認済)
			dto.setApplicationInfo(getApplovedNaming());
			// チェックボックス要否設定(不要)
			dto.setNeedCheckbox(false);
			return;
		}
		// 出勤予定日不要確認
		if (needWorkDay == false) {
			return;
		}
		// 勤務形態設定
		dto.setWorkTypeCode(workTypeCode);
		// 状態設定(予定)
		dto.setApplicationInfo(getScheduleNaming());
		// チェックボックス要否設定(要)
		dto.setNeedCheckbox(true);
		// 始業時間設定
		if (dto.getStartTime() == null) {
			dto.setStartTime(getWorkTypeStartTime(dto));
		}
		// 終業時間設定
		if (dto.getEndTime() == null) {
			dto.setEndTime(getWorkTypeEndTime(dto));
		}
		// 勤務時間要不要確認
		if (needWorkTime == false) {
			return;
		}
		// 勤務時間
		dto.setWorkTime(getWorkTypeEntity(dto).getWorkTime());
		// 休憩時間
		dto.setRestTime(getWorkTypeEntity(dto).getRestTime());
	}
	
	/**
	 * 勤怠一覧情報に勤務形態情報の内容を設定する。<br>
	 * @param dto          設定対象勤怠一覧情報
	 * @param workTypeCode 勤務形態コード
	 * @param needWorkDay  出勤予定日要否(true：要、false：不要)
	 * @param needWorkTime 勤務時間、休憩時間要否(true：要、false：不要)
	 * @param holidayRangeAm 午前休判断(true：午前休、false：午前休でない)
	 * @param holidayRangePm 午後休判断(true：午後休、false：午後休でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceListDto dto, String workTypeCode, boolean needWorkDay, boolean needWorkTime,
			boolean holidayRangeAm, boolean holidayRangePm) throws MospException {
		if (!holidayRangeAm && !holidayRangePm) {
			setDtoFields(dto, workTypeCode, needWorkDay, needWorkTime);
			return;
		}
		// 勤務形態確認(所定休日或いは法定休日)
		if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)
				|| workTypeCode.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			// 勤務形態設定
			dto.setWorkTypeCode(workTypeCode);
			// 状態設定(承認済)
			dto.setApplicationInfo(getApplovedNaming());
			// チェックボックス要否設定(不要)
			dto.setNeedCheckbox(false);
			return;
		}
		// 出勤予定日不要確認
		if (needWorkDay == false) {
			return;
		}
		// 勤務形態設定
		dto.setWorkTypeCode(workTypeCode);
		// 状態設定(予定)
		dto.setApplicationInfo(getScheduleNaming());
		// チェックボックス要否設定(要)
		dto.setNeedCheckbox(true);
		// 予定始業時刻及び予定終業時刻を取得
		Date startTime = getWorkTypeStartTime(dto);
		Date endTime = getWorkTypeEndTime(dto);
		// 始業時間設定
		if (dto.getStartTime() == null) {
			dto.setStartTime(startTime);
		}
		// 終業時間設定
		if (dto.getEndTime() == null) {
			dto.setEndTime(endTime);
		}
		// 勤務時間要不要確認
		if (needWorkTime == false) {
			return;
		}
		// 勤務時間
		dto.setWorkTime(getWorkTypeEntity(dto).getWorkTime());
		// 休憩時間
		dto.setRestTime(getWorkTypeEntity(dto).getRestTime());
		// 午前休又は午後休の場合
		if (holidayRangeAm || holidayRangePm) {
			dto.setWorkTime(getDefferenceMinutes(startTime, endTime));
			dto.setRestTime(null);
		}
	}
	
	/**
	 * 勤怠一覧情報に表示用文字列(日付、時刻、時間等)を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoStringFields() throws MospException {
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendanceList) {
			setDtoStringFields(dto);
		}
	}
	
	/**
	 * 勤怠一覧情報の特定の項目にハイフンを設定する。<br>
	 * @param useScheduledTime 勤務予定時間表示要否(true：要、false：否)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoHyphenFields(boolean useScheduledTime) throws MospException {
		// 勤務予定時間表示要否確認
		if (useScheduledTime) {
			return;
		}
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendanceList) {
			if (dto.getCorrectionInfo() == null) {
				// 始業時刻
				dto.setStartTimeString(getHyphenNaming());
				// 終業時刻
				dto.setEndTimeString(getHyphenNaming());
			}
		}
	}
	
	/**
	 * 勤怠一覧情報にチェックボックス要否設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setNeedCheckbox() throws MospException {
		// 締状態確認
		if (isTightened == false) {
			// 未締の場合
			return;
		}
		// チェックボックスを不要に設定
		for (AttendanceListDto dto : attendanceList) {
			dto.setNeedCheckbox(false);
		}
	}
	
	/**
	 * 勤怠一覧情報にチェックボックス要否設定する。<br>
	 * @param dto 対象DTO
	 * @param workOnHolidayRequestDto 休日出勤申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setNeedCheckbox(AttendanceListDto dto, WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto)
			throws MospException {
		// チェックボックス要否設定(不要)
		dto.setNeedCheckbox(false);
		// ワークフロー状況確認(承認済の場合)
		if (workflow.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
			// チェックボックス要否設定(要)
			dto.setNeedCheckbox(true);
		}
	}
	
	/**
	 * 勤怠一覧情報(勤怠承認一覧)にチェックボックス要否設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setNeedApprovalCheckbox() throws MospException {
		// 締状態確認
		if (isTightened) {
			// 未締でない場合
			// チェックボックスを不要に設定
			for (AttendanceListDto dto : attendanceList) {
				dto.setNeedCheckbox(false);
			}
			return;
		}
		// 承認可能ワークフロー情報マップ(勤怠)取得
		Map<Long, WorkflowDtoInterface> approvableMap = getApprovableMap();
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendanceList) {
			// チェックボックス要否設定
			dto.setNeedCheckbox(false);
			// ワークフロー確認
			if (dto.getWorkflow() == 0) {
				// ワークフロー番号が設定されていない場合
				continue;
			}
			// 承認可能ワークフロー情報に含まれているか確認
			if (approvableMap.containsKey(dto.getWorkflow())) {
				// チェックボックス要否設定
				dto.setNeedCheckbox(true);
			}
		}
	}
	
	/**
	 * 承認可能ワークフロー情報マップ(勤怠)を取得する。<br>
	 * ログインユーザが承認可能な情報を取得する。<br>
	 * @return 承認可能ワークフロー情報マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Long, WorkflowDtoInterface> getApprovableMap() throws MospException {
		// ログインユーザ個人ID取得
		String personalId = mospParams.getUser().getPersonalId();
		// 承認情報参照クラス取得
		ApprovalInfoReferenceBeanInterface approvalReference = (ApprovalInfoReferenceBeanInterface)createBean(
				ApprovalInfoReferenceBeanInterface.class);
		// 承認可能ワークフローリスト(勤怠)取得
		return approvalReference.getApprovableMap(personalId).get(TimeConst.CODE_FUNCTION_WORK_MANGE);
	}
	
	/**
	 * 勤怠一覧情報に表示用文字列(日付、時刻、時間等)を設定する。<br>
	 * @param dto 設定対象勤怠一覧情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoStringFields(AttendanceListDto dto) throws MospException {
		// 基準日付取得(勤務日)
		Date standardDate = dto.getWorkDate();
		// 対象日取得(勤務日)
		Date targetDate = dto.getWorkDate();
		// 勤務日
		dto.setWorkDateString(getStringMonthDay(targetDate));
		// 勤務曜日
		dto.setWorkDayOfWeek(getStringDayOfWeek(targetDate));
		// 曜日配色設定
		dto.setWorkDayOfWeekStyle(HolidayUtility.getWorkDayOfWeekStyle(targetDate, mospParams));
		// カレンダ備考設定
		addRemark(dto, getScheduleDateRemark(dto.getWorkDate()));
		// 勤務形態略称
		setWorkTypeAbbr(dto);
		// 始業時間
		dto.setStartTimeString(getStringTime(dto.getStartTime(), standardDate));
		// 勤務時間配色設定
		dto.setStartTimeStyle(getAttendanceTimeStyle(dto.getApplicationInfo()));
		// 終業時間
		dto.setEndTimeString(getStringTime(dto.getEndTime(), standardDate));
		// 勤務時間配色設定
		dto.setEndTimeStyle(getAttendanceTimeStyle(dto.getApplicationInfo()));
		// 始業打刻
		dto.setStartRecordTimeString(getStringTime(dto.getStartRecordTime(), standardDate));
		// 終業打刻
		dto.setEndRecordTimeString(getStringTime(dto.getEndRecordTime(), standardDate));
		// 勤務時間
		dto.setWorkTimeString(getStringHours(dto.getWorkTime(), true));
		// 休憩時間
		dto.setRestTimeString(getStringHours(dto.getRestTime(), true));
		// 私用外出時間
		dto.setPrivateTimeString(getStringHours(dto.getPrivateTime(), true));
		// 公用外出時間
		dto.setPublicTimeString(getStringHours(dto.getPublicTime(), true));
		// 遅刻時間
		dto.setLateTimeString(getStringHours(dto.getLateTime(), true));
		// 早退時間
		dto.setLeaveEarlyTimeString(getStringHours(dto.getLeaveEarlyTime(), true));
		// 遅刻早退時間
		dto.setLateLeaveEarlyTimeString(getStringHours(dto.getLateLeaveEarlyTime(), true));
		// 残業時間
		dto.setOvertimeString(getStringHours(dto.getOvertime(), true));
		// 内残時間
		dto.setOvertimeInString(getStringHours(dto.getOvertimeIn(), true));
		// 外残時間
		dto.setOvertimeOutString(getStringHours(dto.getOvertimeOut(), true));
		// 休出時間
		dto.setHolidayWorkTimeString(getStringHours(dto.getHolidayWorkTime(), true));
		// 深夜時間
		dto.setLateNightTimeString(getStringHours(dto.getLateNightTime(), true));
		// 無休時短時間
		dto.setShortUnpaidString(getStringHours(dto.getShortUnpaid(), true));
		// 勤務時間合計
		dto.setWorkTimeTotalString(getStringHours(dto.getWorkTimeTotal(), false));
		// 休憩時間合計
		dto.setRestTimeTotalString(getStringHours(dto.getRestTimeTotal(), false));
		// 私用外出時間合計
		dto.setPrivateTimeTotalString(getStringHours(dto.getPrivateTimeTotal(), false));
		// 公用外出時間合計
		dto.setPublicTimeTotalString(getStringHours(dto.getPublicTimeTotal(), false));
		// 遅刻時間
		dto.setLateTimeTotalString(getStringHours(dto.getLateTimeTotal(), false));
		// 早退時間
		dto.setLeaveEarlyTimeTotalString(getStringHours(dto.getLeaveEarlyTimeTotal(), false));
		// 遅刻早退時間
		dto.setLateLeaveEarlyTimeTotalString(getStringHours(dto.getLateLeaveEarlyTimeTotal(), false));
		// 残業時間
		dto.setOvertimeTotalString(getStringHours(dto.getOvertimeTotal(), false));
		// 内残時間
		dto.setOvertimeInTotalString(getStringHours(dto.getOvertimeInTotal(), false));
		// 外残時間
		dto.setOvertimeOutTotalString(getStringHours(dto.getOvertimeOutTotal(), false));
		// 休出時間
		dto.setHolidayWorkTimeTotalString(getStringHours(dto.getHolidayWorkTimeTotal(), false));
		// 深夜時間
		dto.setLateNightTimeTotalString(getStringHours(dto.getLateNightTimeTotal(), false));
		// 無休時短時間
		dto.setShortUnpaidTotalString(getStringHours(dto.getShortUnpaidTotal(), false));
		// 出勤回数
		dto.setWorkDaysString(getIntegerTimes(dto.getWorkDays()));
		// 遅刻回数
		dto.setLateDaysString(getIntegerTimes(dto.getLateDays()));
		// 早退回数
		dto.setLeaveEarlyDaysString(getIntegerTimes(dto.getLeaveEarlyDays()));
		// 残業回数
		dto.setOvertimeDaysString(getIntegerTimes(dto.getOvertimeDays()));
		// 休出回数
		dto.setHolidayWorkDaysString(getIntegerTimes(dto.getHolidayWorkDays()));
		// 代休発生回数設定
		dto.setBirthPrescribedSubHolidayString(getFloatTimes(dto.getBirthPrescribedSubHoliday(), true));
		dto.setBirthLegalSubHolidayString(getFloatTimes(dto.getBirthLegalSubHoliday(), true));
		dto.setBirthMidnightSubHolidayString(getFloatTimes(dto.getBirthMidnightSubHoliday(), true));
		if (getFloatTimes(dto.getBirthMidnightSubHoliday(), true).equals(getHyphenNaming())) {
			dto.setBirthMidnightSubHolidayString(null);
		}
		// 深夜回数
		dto.setLateNightDaysString(getIntegerTimes(dto.getLateNightDays()));
		// 所定休日回数
		dto.setPrescribedHolidaysString(getIntegerTimes(dto.getPrescribedHolidays()));
		// 法定休日回数
		dto.setLegalHolidaysString(getIntegerTimes(dto.getLegalHolidays()));
		// 休日回数
		dto.setHolidayString(getIntegerTimes(dto.getHolidays()));
		// 振替休日回数
		dto.setSubstituteHolidaysString(getFloatTimes(dto.getSubstituteHolidays(), false));
		// 有給休暇回数
		dto.setPaidHolidaysString(getFloatTimes(dto.getPaidHolidays(), false));
		// 有給時間
		dto.setPaidHolidayTimeString(getFloatTimes(dto.getPaidHolidayTime(), false));
		// 特別休暇回数
		dto.setSpecialHolidaysString(getDaysAndHours(dto.getSpecialHolidays(), dto.getSpecialHolidayHours()));
		// その他休暇回数
		dto.setOtherHolidaysString(getDaysAndHours(dto.getOtherHolidays(), dto.getOtherHolidayHours()));
		// 代休回数
		dto.setSubHolidaysString(getFloatTimes(dto.getSubHolidays(), false));
		// 欠勤回数
		dto.setAbsenceDaysString(getDaysAndHours(dto.getAbsenceDays(), dto.getAbsenceHours()));
		// 分単位休暇A時間
		dto.setMinutelyHolidayATimeString(getStringHours(dto.getMinutelyHolidayATimeTotal(), false));
		// 分単位休暇B時間
		dto.setMinutelyHolidayBTimeString(getStringHours(dto.getMinutelyHolidayBTimeTotal(), false));
		// 社員コード項目タイトル設定
		dto.setEmployeeCodeTitle(PfNameUtility.employeeCode(mospParams));
		// 帳票残業項目タイトル設定
		dto.setOvertimeTitle(TimeNamingUtility.overtimeOutAbbr(mospParams));
		// 時間休項目有無設定
		dto.setHourlyPaidHolidayValid(true);
		// 特別休暇項目タイトル設定
		dto.setSpecialHolidaysTitle(TimeNamingUtility.specialHolidayAbbr(mospParams));
	}
	
	/**
	 * 勤怠の申請状態から始業時刻・終業時刻の色を取得する。<br>
	 * @param state 勤怠申請状態
	 * @return 色
	 */
	protected String getAttendanceTimeStyle(String state) {
		if (mospParams.getName("Schedule").equals(state)) {
			return PlatformConst.STYLE_GRAY;
		}
		return "";
	}
	
	/**
	 * 勤務形態略称を設定する。<br>
	 * @param dto 勤怠一覧情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWorkTypeAbbr(AttendanceListDto dto) throws MospException {
		// 勤務形態確認
		if (dto.getWorkTypeCode() == null || dto.getWorkTypeCode().isEmpty()) {
			// 何もしない
			return;
		}
		// 時差出勤勤務形態略称を取得
		String workTypeAbbr = difference.getDifferenceAbbr(dto.getWorkTypeCode());
		// 時差出勤半日勤務形態略称を取得
		String workTypeAbbrInitial = workTypeAbbr.substring(workTypeAbbr.length() - 1);
		// 時差出勤でない場合
		if (dto.getWorkTypeCode().equals(workTypeAbbr)) {
			// 勤務形態略称を取得
			workTypeAbbr = getWorkTypeEntity(dto.getWorkTypeCode()).getWorkTypeAbbr();
			// 勤務形態略称が取得できた場合
			if (workTypeAbbr.isEmpty() == false) {
				// 半日勤務形態略称を取得
				workTypeAbbrInitial = substrForHalfWorkType(workTypeAbbr);
			}
		}
		if (dto.getWorkTypeAnteAbbr() != null && !dto.getWorkTypeAnteAbbr().isEmpty()) {
			if (getHalfSubstituteWorkAbbrNaming().equals(dto.getWorkTypeAnteAbbr())) {
				// 振替出勤の場合
				dto.setWorkTypeAbbr(getHalfSubstituteWorkAbbrNaming());
				return;
			} else if (TimeNamingUtility.substituteHolidayAbbr(mospParams).equals(dto.getWorkTypeAnteAbbr())) {
				// 振替休日の場合
				dto.setWorkTypeAbbr(getHalfSubstituteHolidayAbbrNaming());
				return;
			}
			// 勤務形態略称(半日勤務形態略称/半日勤務形態略称)を設定
			StringBuffer sb = new StringBuffer();
			sb.append(substrForHalfWorkType(dto.getWorkTypeAnteAbbr()));
			sb.append(getSlashNaming());
			sb.append(workTypeAbbrInitial);
			dto.setWorkTypeAbbr(sb.toString());
			return;
		}
		if (dto.getWorkTypePostAbbr() != null && !dto.getWorkTypePostAbbr().isEmpty()) {
			if (getHalfSubstituteWorkAbbrNaming().equals(dto.getWorkTypePostAbbr())) {
				// 振替出勤の場合
				dto.setWorkTypeAbbr(getHalfSubstituteWorkAbbrNaming());
				return;
			} else if (TimeNamingUtility.substituteHolidayAbbr(mospParams).equals(dto.getWorkTypePostAbbr())) {
				// 振替休日の場合
				dto.setWorkTypeAbbr(getHalfSubstituteHolidayAbbrNaming());
				return;
			}
			// 勤務形態略称(半日勤務形態略称/半日勤務形態略称)を設定
			StringBuffer sb = new StringBuffer();
			sb.append(workTypeAbbrInitial);
			sb.append(getSlashNaming());
			sb.append(substrForHalfWorkType(dto.getWorkTypePostAbbr()));
			dto.setWorkTypeAbbr(sb.toString());
			return;
		}
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(workTypeAbbr);
	}
	
	/**
	 * 対象日のカレンダ日情報の備考を取得する。<br>
	 * @param targetDate 対象日
	 * @return カレンダ日情報の備考
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getScheduleDateRemark(Date targetDate) throws MospException {
		// 対象日のカレンダ日情報を取得
		ScheduleDateDtoInterface dto = getScheduleDateDto(targetDate);
		// 対象日のカレンダ日情報を確認
		if (dto == null) {
			return "";
		}
		return dto.getRemark();
	}
	
	/**
	 * 予定始業時刻を取得する。<br>
	 * <br>
	 * 勤務形態エンティティ及び申請エンティティを用いて、始業時刻を取得する。<br>
	 * <br>
	 * @param dto 勤怠一覧情報
	 * @return 始業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getWorkTypeStartTime(AttendanceListDto dto) throws MospException {
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workTypeEntity = getWorkTypeEntity(dto.getWorkTypeCode());
		// 申請エンティティを取得
		RequestEntity requestEntity = getRequestEntity(personalId, dto.getWorkDate());
		// 始業時刻を取得
		return workTypeEntity.getStartTime(requestEntity);
	}
	
	/**
	 * 予定終業時刻を取得する。<br>
	 * <br>
	 * 勤務形態エンティティ及び申請エンティティを用いて、終業時刻を取得する。<br>
	 * <br>
	 * @param dto 勤怠一覧情報
	 * @return 終業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getWorkTypeEndTime(AttendanceListDto dto) throws MospException {
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workTypeEntity = getWorkTypeEntity(dto.getWorkTypeCode());
		// 申請エンティティを取得
		RequestEntity requestEntity = getRequestEntity(personalId, dto.getWorkDate());
		// 終業時刻を取得
		return workTypeEntity.getEndTime(requestEntity);
	}
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * <br>
	 * 有効日は、勤怠一覧表示期間の最終日として取得する。<br>
	 * 新規に取得した勤務形態エンティティは、勤務形態エンティティ群に設定する。<br>
	 * <br>
	 * @param workTypeCode 対象勤務形態コード
	 * @return 勤務形態エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected WorkTypeEntityInterface getWorkTypeEntity(String workTypeCode) throws MospException {
		// 勤務形態エンティティ群から勤務形態エンティティを取得
		WorkTypeEntityInterface workTypeEntity = workTypeEntityMap.get(workTypeCode);
		// 勤務形態エンティティ確認
		if (workTypeEntity != null) {
			return workTypeEntity;
		}
		// 勤務形態エンティティ再取得
		workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, lastDate);
		// 勤務形態エンティティ群に設定
		workTypeEntityMap.put(workTypeCode, workTypeEntity);
		return workTypeEntity;
	}
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * <br>
	 * 勤怠一覧情報に設定されている勤務形態コードから、勤務形態エンティティを取得する。<br>
	 * <br>
	 * @param dto 勤怠一覧情報
	 * @return 勤務形態エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected WorkTypeEntityInterface getWorkTypeEntity(AttendanceListDto dto) throws MospException {
		// 勤務形態エンティティを取得
		return getWorkTypeEntity(dto.getWorkTypeCode());
	}
	
	/**
	 * 申請エンティティを取得する。<br>
	 * <br>
	 * 取得した各種申請を基に、対象日の申請を抽出する。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected RequestEntity getRequestEntity(String personalId, Date targetDate) throws MospException {
		// 申請エンティティ準備
		RequestEntity requestEntity = (RequestEntity)createObject(RequestEntityInterface.class);
		requestEntity.setPersonalId(personalId);
		requestEntity.setTargetDate(targetDate);
		// 対象申請情報リスト準備
		AttendanceDtoInterface targetAttendanceDto = null;
		List<HolidayRequestDtoInterface> targetHolidayRequestList = new ArrayList<HolidayRequestDtoInterface>();
		List<SubHolidayRequestDtoInterface> targetSubHolidayRequestList = new ArrayList<SubHolidayRequestDtoInterface>();
		List<SubstituteDtoInterface> targetSubstituteList = new ArrayList<SubstituteDtoInterface>();
		List<OvertimeRequestDtoInterface> targetOvertimeRequestList = new ArrayList<OvertimeRequestDtoInterface>();
		WorkOnHolidayRequestDtoInterface targetWorkOnHolidayRequestDto = null;
		DifferenceRequestDtoInterface targetDifferenceRequestDto = null;
		WorkTypeChangeRequestDtoInterface targetWorkTypeChangeRequestDto = null;
		// 勤怠申請情報選別
		targetAttendanceDto = getAttendanceDtoListDto(targetDate);
		// 休暇申請情報選別
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			// 対象日が休暇申請開始日～終了日に含まれる場合
			if (DateUtility.isTermContain(targetDate, dto.getRequestStartDate(), dto.getRequestEndDate())) {
				// 対象休暇申請情報リストに追加
				targetHolidayRequestList.add(dto);
			}
		}
		// 代休申請情報選別
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
			// 対象日が代休日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 対象代休申請情報リストに追加
				targetSubHolidayRequestList.add(dto);
			}
		}
		// 振替休日情報選別
		for (SubstituteDtoInterface dto : substituteList) {
			// 対象日が振替日である場合
			if (targetDate.compareTo(dto.getSubstituteDate()) == 0) {
				// 対象振替休日情報リストに追加
				targetSubstituteList.add(dto);
			}
		}
		// 残業申請情報選別
		for (OvertimeRequestDtoInterface dto : overtimeRequestList) {
			// 対象日が残業年月日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 対象残業申請情報リストに追加
				targetOvertimeRequestList.add(dto);
			}
		}
		// 振出・休出申請情報選別
		for (WorkOnHolidayRequestDtoInterface dto : workOnHolidayRequestList) {
			// 対象日が出勤日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 取下でない場合
				if (WorkflowUtility.isWithDrawn(getWorkflow(dto.getWorkflow())) == false) {
					// 対象振替休日情報リストに追加
					targetWorkOnHolidayRequestDto = dto;
					break;
				}
			}
		}
		// 時差出勤申請情報選別
		for (DifferenceRequestDtoInterface dto : differenceRequestList) {
			// 対象日が申請日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 取下でない場合
				if (WorkflowUtility.isWithDrawn(getWorkflow(dto.getWorkflow())) == false) {
					// 対象時差出勤申請情報を取得
					targetDifferenceRequestDto = dto;
					break;
				}
			}
		}
		// 勤務形態変更申請情報選別
		for (WorkTypeChangeRequestDtoInterface dto : workTypeChangeRequestList) {
			// 対象日が出勤日である場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 取下でない場合
				if (WorkflowUtility.isWithDrawn(getWorkflow(dto.getWorkflow())) == false) {
					// 対象勤務形態変更申請情報を取得
					targetWorkTypeChangeRequestDto = dto;
					break;
				}
			}
		}
		// 対象申請情報リスト設定
		requestEntity.setAttendanceDto(targetAttendanceDto);
		requestEntity.setHolidayRequestList(targetHolidayRequestList);
		requestEntity.setSubHolidayRequestList(targetSubHolidayRequestList);
		requestEntity.setSubstituteList(targetSubstituteList);
		requestEntity.setOverTimeRequestList(targetOvertimeRequestList);
		requestEntity.setWorkOnHolidayRequestDto(targetWorkOnHolidayRequestDto);
		requestEntity.setDifferenceRequestDto(targetDifferenceRequestDto);
		requestEntity.setWorkTypeChangeRequestDto(targetWorkTypeChangeRequestDto);
		// ワークフロー情報群取得
		if (targetAttendanceDto != null) {
			getWorkflow(targetAttendanceDto.getWorkflow());
		}
		for (HolidayRequestDtoInterface dto : targetHolidayRequestList) {
			getWorkflow(dto.getWorkflow());
		}
		for (SubHolidayRequestDtoInterface dto : targetSubHolidayRequestList) {
			getWorkflow(dto.getWorkflow());
		}
		for (SubstituteDtoInterface dto : targetSubstituteList) {
			getWorkflow(dto.getWorkflow());
		}
		for (OvertimeRequestDtoInterface dto : targetOvertimeRequestList) {
			getWorkflow(dto.getWorkflow());
		}
		// ワークフロー情報群設定
		requestEntity.setWorkflowMap(workflowMap);
		return requestEntity;
	}
	
	/**
	 * 勤怠一覧情報に備考を追加する。<br>
	 * @param dto    追加対象勤怠一覧情報
	 * @param remark 追加備考
	 */
	protected void addRemark(AttendanceListDto dto, String remark) {
		// 勤怠一覧情報に備考を追加
		AttendanceUtility.addRemark(dto, remark);
	}
	
	/**
	 * 回数を取得する。<br>
	 * 時間がnull、或いは0の場合は0を返す。<br>
	 * それ以外の場合は1を返す。<br>
	 * @param minutes 時間(分)
	 * @return 回数
	 */
	protected int countHours(Integer minutes) {
		// 時間確認
		if (minutes == null || minutes.intValue() == 0) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * 時間文字列を取得する(Integer→String)。<br>
	 * 時間を文字列(小数点以下2桁)で表す。<br>
	 * 小数点以下2桁は、分を表す。<br>
	 * @param minutes    対象時間(分)
	 * @param needHyphen ゼロ時ハイフン表示要否(true：ゼロ時ハイフン、false：ゼロ時はゼロ)
	 * @return 時間文字列(0.00)
	 */
	protected String getStringHours(Integer minutes, boolean needHyphen) {
		// 時間文字列を取得
		return TimeUtility.getStringPeriodTimeOrHyphen(mospParams, minutes, needHyphen);
	}
	
	/**
	 * 回数文字列を取得する(Integer→String)。<br>
	 * 回数を文字列で表す。<br>
	 * @param times 対象回数
	 * @return 回数文字列
	 */
	protected String getIntegerTimes(Integer times) {
		// 数値オブジェクト(回数)を文字列に変換
		return TransStringUtility.getIntegerTimes(mospParams, times, true);
	}
	
	/**
	 * 回数文字列を取得する(Float→String)。<br>
	 * 回数を文字列(小数点以下1桁)で表す。<br>
	 * @param times     対象回数
	 * @param isDecimal 小数点要否(true：小数点要(1の場合に1.0となる)、false：小数点不要)
	 * @return 回数文字列
	 */
	protected String getFloatTimes(Float times, boolean isDecimal) {
		// 数値オブジェクト(回数等)を文字列に変換
		return TransStringUtility.getFloatTimes(mospParams, times, true, isDecimal);
	}
	
	/**
	 * 日数/時間数文字列を取得する。<br>
	 * @param days  日数
	 * @param hours 時間数
	 * @return 日数/時間数文字列
	 */
	protected String getDaysAndHours(Float days, Float hours) {
		// 日数/時間数文字列を取得
		return MospUtility.concat(PfNameUtility.slash(mospParams),
				new String[]{ TransStringUtility.getFloatTimes(mospParams, days, true, false),
					TransStringUtility.getFloatTimes(mospParams, hours, true, false) });
	}
	
	/**
	 * 勤務予定時間表示要否を取得する。<br>
	 * 勤怠設定情報から取得し、勤怠一覧画面等で利用される。<br>
	 * @return 勤務予定時間表示要否(true：要、false：否)
	 */
	protected boolean useScheduledTime() {
		return timeSettingDto.getUseScheduledTime() == MospConst.INACTIVATE_FLAG_OFF;
	}
	
	/**
	 * 週の起算曜日(勤怠設定)から週期間を算出し、設定する。<br>
	 * ポータル画面等の期間で勤怠情報を取得する場合は、この週期間を利用する。<br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setWeekTerm(Date targetDate) throws MospException {
		// 週の起算曜日を取得
		int startDayOfWeek = timeSettingDto.getStartWeek();
		// 期間初日設定
		firstDate = DateUtility.getFirstDateOfWeek(startDayOfWeek, targetDate);
		// 期間最終日設定
		lastDate = DateUtility.getLastDateOfWeek(startDayOfWeek, targetDate);
	}
	
	/**
	 * ワークフロー情報を取得する。<br>
	 * ワークフロー情報群から取得し、無い場合はDBから取得する。<br>
	 * @param workflowNo ワークフロー番号
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected WorkflowDtoInterface getWorkflow(long workflowNo) throws MospException {
		// ワークフロー情報群からワークフロー情報を取得
		WorkflowDtoInterface dto = workflowMap.get(workflowNo);
		// ワークフロー情報が有る場合
		if (dto != null) {
			return dto;
		}
		// DBからワークフロー情報を取得
		dto = workflow.getLatestWorkflowInfo(workflowNo);
		if (dto != null) {
			workflowMap.put(workflowNo, dto);
		}
		return dto;
	}
	
	/**
	 * 半日勤務形態略称を取得する。<br>
	 * <br>
	 * 半休など、午前と午後で異なる勤務形態となる場合に表示する
	 * 半日勤務形態略称を作成する。<br>
	 * <br>
	 * 勤務形態略称を頭から半日勤務形態略称長(フィールド)分だけ抜き出す。<br>
	 * <br>
	 * @param workTypeAbbr 勤務形態略称
	 * @return 半日勤務形態略称
	 */
	protected String substrForHalfWorkType(String workTypeAbbr) {
		// 半日勤務形態略称を取得
		return MospUtility.substring(workTypeAbbr, halfWorkTypeLength);
	}
	
	/**
	 * 月日を取得する。<br>
	 * @param date 日付
	 * @return 月日
	 */
	protected String getStringMonthDay(Date date) {
		return DateUtility.getStringDate(date, "MM/dd");
	}
	
	/**
	 * 曜日を取得する。<br>
	 * @param date 日付
	 * @return 曜日
	 */
	protected String getStringDayOfWeek(Date date) {
		return DateUtility.getStringDayOfWeek(date);
	}
	
	/**
	 * 予定名称を取得する。<br>
	 * @return 予定名称
	 */
	protected String getScheduleNaming() {
		return mospParams.getName("Schedule");
	}
	
	/**
	 * 承認済名称を取得する。<br>
	 * @return 承認済名称
	 */
	protected String getApplovedNaming() {
		return mospParams.getName("Approval", "Finish");
	}
	
	/**
	 * 有給休暇略称を取得する。<br>
	 * @return 有給休暇略称
	 */
	protected String getPaidHolidayAbbrNaming() {
		return mospParams.getName("PaidHolidayAbbr");
	}
	
	/**
	 * ストック休暇略称を取得する。<br>
	 * @return ストック休暇略称
	 */
	protected String getStockHolidayAbbrNaming() {
		return mospParams.getName("StockHolidayAbbr");
	}
	
	/**
	 * 休日出勤略称を取得する。<br>
	 * @return 休日出勤略称
	 */
	protected String getWorkOnHolidayAbbrNaming() {
		return mospParams.getName("WorkingHoliday");
	}
	
	/**
	 * 振替出勤略称を取得する。<br>
	 * @return 振替出勤略称
	 */
	protected String getSubstituteWorkAbbrNaming() {
		return mospParams.getName("SubstituteAbbr", "GoingWorkAbbr");
	}
	
	/**
	 * 振替休日略称を取得する。<br>
	 * @return 振替休日略称
	 */
	protected String getSubstituteAbbrNaming() {
		return mospParams.getName("SubstituteAbbr");
	}
	
	/**
	 * 半日振替休日略称を取得する。<br>
	 * @return 半日振替休日略称
	 */
	protected String getHalfSubstituteHolidayAbbrNaming() {
		return TimeNamingUtility.halfSubstituteHolidayAbbr(mospParams);
	}
	
	/**
	 * 午前振替休日略称を取得する。<br>
	 * @return 午前振替休日略称
	 */
	protected String getAnteMeridiemSubstituteHolidayAbbrNaming() {
		return TimeNamingUtility.anteSubstituteHolidayAbbr(mospParams);
	}
	
	/**
	 * 午後振替休日略称を取得する。<br>
	 * @return 午後振替休日略称
	 */
	protected String getPostMeridiemSubstituteHolidayAbbrNaming() {
		return TimeNamingUtility.postSubstituteHolidayAbbr(mospParams);
	}
	
	/**
	 * 半日振替出勤略称を取得する。<br>
	 * @return 半日振替出勤略称
	 */
	protected String getHalfSubstituteWorkAbbrNaming() {
		return TimeNamingUtility.halfSubstituteWorkAbbr(mospParams);
	}
	
	/**
	 * 代休略称を取得する。<br>
	 * @return 代休略称
	 */
	protected String getSubHolidayAbbrNaming() {
		return mospParams.getName("Generation");
	}
	
	/**
	 * 残業略称を取得する。<br>
	 * @return 残業略称
	 */
	protected String getOvertimeAbbrNaming() {
		return mospParams.getName("OvertimeAbbr");
	}
	
	/**
	 * 勤務形態変更略称を取得する。<br>
	 * @return 勤務形態略称
	 */
	protected String getWorkTypeChangeAbbrNaming() {
		return mospParams.getName("WorkTypeChangeAbbr");
	}
	
	/**
	 * 時差出勤略称を取得する。<br>
	 * @return 時差出勤略称
	 */
	protected String getTimeDefferenceAbbrNaming() {
		return mospParams.getName("TimeDefferenceAbbr");
	}
	
	/**
	 * 承認済略称を取得する。<br>
	 * @return 承認済略称
	 */
	protected String getCompleteApprovalAbbrNaming() {
		return mospParams.getName("Finish");
	}
	
	/**
	 * 差戻略称を取得する。<br>
	 * @return 差戻略称
	 */
	protected String getRevertAbbrNaming() {
		return mospParams.getName("Back");
	}
	
	/**
	 * 申請略称を取得する。<br>
	 * @return 申請略称
	 */
	protected String getApprovalAbbrNaming() {
		return mospParams.getName("Register");
	}
	
	/**
	 * 下書略称を取得する。<br>
	 * @return 下書略称
	 */
	protected String getDraftAbbrNaming() {
		return mospParams.getName("Under");
	}
	
	/**
	 * 本人修正略称を取得する。<br>
	 * @return 本人修正略称
	 */
	protected String getSelfCorrectAbbrNaming() {
		return TimeNamingUtility.selfCorrectAbbr(mospParams);
	}
	
	/**
	 * 他人修正略称を取得する。<br>
	 * @return 他人修正略称
	 */
	protected String getOtherCorrectAbbrNaming() {
		return TimeNamingUtility.otherCorrectAbbr(mospParams);
	}
	
	/**
	 * スラッシュを取得する。<br>
	 * @return スラッシュ
	 */
	protected String getSlashNaming() {
		return mospParams.getName("Slash");
	}
	
	/**
	 * 予定確認や予定簿の表示について、<br>
	 * 申請(承認済)によって勤務時間の表示を決める。<br>
	 * @throws MospException 例外が発生した時
	 */
	protected void setApprovalTime() throws MospException {
		// 勤怠一覧情報毎に処理
		attendanceList: for (AttendanceListDto dto : attendanceList) {
			boolean amHoliday = false;
			boolean pmHoliday = false;
			WorkTypeChangeRequestDtoInterface workTypeChangeDto = null;
			WorkOnHolidayRequestDtoInterface workOnHolidayDto = null;
			// 休暇申請情報確認
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				// 休暇日確認
				if (!DateUtility.isTermContain(dto.getWorkDate(), holidayRequestDto.getRequestStartDate(),
						holidayRequestDto.getRequestEndDate())) {
					continue;
				}
				if (!workflow.isCompleted(holidayRequestDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				// 休暇範囲確認
				if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					amHoliday = true;
				} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					pmHoliday = true;
				}
				if (amHoliday && pmHoliday) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				}
			}
			// 代休申請情報確認
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// 代休日確認
				if (!subHolidayRequestDto.getRequestDate().equals(dto.getWorkDate())) {
					continue;
				}
				if (!workflow.isCompleted(subHolidayRequestDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				// 休暇範囲確認
				if (subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				} else if (subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					amHoliday = true;
				} else if (subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					pmHoliday = true;
				}
				if (amHoliday && pmHoliday) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				}
			}
			// 振替休日情報確認
			for (SubstituteDtoInterface substituteDto : substituteList) {
				// 振替日確認
				if (!substituteDto.getSubstituteDate().equals(dto.getWorkDate())) {
					continue;
				}
				if (!workflow.isCompleted(substituteDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				// 休日範囲確認
				if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				} else if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休の場合
					amHoliday = true;
				} else if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後休の場合
					pmHoliday = true;
				}
				if (amHoliday && pmHoliday) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				}
			}
			// 振出・休出申請情報確認
			for (WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto : workOnHolidayRequestList) {
				// 出勤日確認
				if (!workOnHolidayRequestDto.getRequestDate().equals(dto.getWorkDate())) {
					continue;
				}
				if (!workflow.isCompleted(workOnHolidayRequestDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				// 振替申請確認
				if (workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
					// 振替出勤(午前)の場合
					pmHoliday = true;
				} else if (workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
					// 振替出勤(午後)の場合
					amHoliday = true;
				}
				if (amHoliday && pmHoliday) {
					// 全休の場合
					dto.setRestTime(null);
					dto.setWorkTime(null);
					continue attendanceList;
				}
				workOnHolidayDto = workOnHolidayRequestDto;
			}
			// 勤務形態変更申請情報確認
			for (WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto : workTypeChangeRequestList) {
				// 出勤日確認
				if (!workTypeChangeRequestDto.getRequestDate().equals(dto.getWorkDate())) {
					continue;
				}
				if (!workflow.isCompleted(workTypeChangeRequestDto.getWorkflow())) {
					// 承認済でない場合
					continue;
				}
				workTypeChangeDto = workTypeChangeRequestDto;
			}
			if (workTypeChangeDto != null) {
				// 勤務形態変更申請が承認済の場合
				dto.setStartTime(null);
				dto.setEndTime(null);
				setDtoFields(dto, workTypeChangeDto.getWorkTypeCode(), true, true, amHoliday, pmHoliday);
				continue;
			}
			if (workOnHolidayDto != null) {
				// 振出・休出申請が承認済の場合
				setDtoFields(dto, getWorkOnHolidayWorkType(workOnHolidayDto), true, true, amHoliday, pmHoliday);
				if (workOnHolidayDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
					// 休日出勤の場合
					// 始業時間
					Date startTime = getWorkTypeStartTime(dto);
					// 終業時間
					Date endTime = getWorkTypeEndTime(dto);
					// 勤務時間
					dto.setWorkTime(getDefferenceMinutes(startTime, endTime));
				}
				continue;
			}
			if (amHoliday || pmHoliday) {
				// 半休の場合
				Date startTime = getWorkTypeStartTime(dto);
				Date endTime = getWorkTypeEndTime(dto);
				// 半休時間確認
				if (startTime == null || endTime == null) {
					continue;
				}
				// 始業時間
				dto.setStartTime(startTime);
				// 終業時間
				dto.setEndTime(endTime);
				// 休憩時間を消去
				dto.setRestTime(null);
				// 勤務時間
				dto.setWorkTime(getDefferenceMinutes(startTime, endTime));
			}
		}
	}
	
	/**
	 * 状態設定。<br>
	 * @param dto 設定対象勤怠一覧情報
	 * @param anteDto 前半休ワークフロー情報
	 * @param postDto 後半休ワークフロー情報
	 */
	protected void setApplicationInfo(AttendanceListDto dto, WorkflowDtoInterface anteDto,
			WorkflowDtoInterface postDto) {
		if (anteDto == null || postDto == null) {
			return;
		}
		int anteStatus = Integer.parseInt(anteDto.getWorkflowStatus());
		int postStatus = Integer.parseInt(postDto.getWorkflowStatus());
		if (anteStatus > postStatus) {
			dto.setApplicationInfo(workflow.getWorkflowStatus(postDto.getWorkflowStatus(), postDto.getWorkflowStage()));
			return;
		} else if (anteStatus < postStatus) {
			dto.setApplicationInfo(workflow.getWorkflowStatus(anteDto.getWorkflowStatus(), anteDto.getWorkflowStage()));
			return;
		}
		// 下書・未承認・承解除・承認済の場合
		if (WorkflowUtility.isDraft(anteDto) || WorkflowUtility.isApply(anteDto) || WorkflowUtility.isCancel(anteDto)
				|| WorkflowUtility.isCompleted(anteDto)) {
			
			dto.setApplicationInfo(workflow.getWorkflowStatus(anteDto.getWorkflowStatus(), anteDto.getWorkflowStage()));
		} else if (PlatformConst.CODE_STATUS_APPROVED.equals(anteDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_REVERT.equals(anteDto.getWorkflowStatus())) {
			// 承認・差戻の場合
			if (anteDto.getWorkflowStage() <= postDto.getWorkflowStage()) {
				dto.setApplicationInfo(
						workflow.getWorkflowStatus(anteDto.getWorkflowStatus(), anteDto.getWorkflowStage()));
				return;
			}
			dto.setApplicationInfo(workflow.getWorkflowStatus(postDto.getWorkflowStatus(), postDto.getWorkflowStage()));
		} else if (PlatformConst.CODE_STATUS_CANCEL_APPLY.equals(anteDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY.equals(anteDto.getWorkflowStatus())) {
			// 承認解除申請の場合
			dto.setApplicationInfo(workflow.getWorkflowStatus(anteDto.getWorkflowStatus(), anteDto.getWorkflowStage()));
			return;
		}
	}
	
	/**
	 * 全休判断。<br>
	 * @param dto 対象DTO
	 * @return 全休の場合true、そうでない場合false
	 */
	protected boolean isAll(SubstituteDtoInterface dto) {
		return isAll(dto.getHolidayRange());
	}
	
	/**
	 * 全休判断。<br>
	 * @param range 休暇範囲
	 * @return 全休の場合true、そうでない場合false
	 */
	protected boolean isAll(int range) {
		return range == TimeConst.CODE_HOLIDAY_RANGE_ALL;
	}
	
	/**
	 * 予定一覧用の追加処理を行う。<br>
	 * ユーザーでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setScheduleExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 実績一覧用の追加処理を行う。<br>
	 * ユーザーでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setActualExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 勤怠一覧用の追加処理を行う。<br>
	 * ユーザーでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setAttendanceExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 勤怠一覧(週)用の追加処理を行う。<br>
	 * ユーザーでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setWeeklyAttendanceExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 勤怠承認一覧用の追加処理を行う。<br>
	 * ユーザーでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setApprovalAttendanceExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 勤怠一覧データ用の追加処理を行う。<br>
	 * ユーザーでOverrideして利用することを想定している。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setAttendanceListDtoExtraInfo() throws MospException {
		// 処理無し
	}
	
	/**
	 * 予定一覧表示に申請情報を適用するか否かを確認する。<br>
	 * 設定がされていない場合は、falseを返す。<br>
	 * @return 確認結果(true：予定一覧表示に申請情報を適用する、false：予定一覧表示に申請情報を適用しない)
	 */
	protected boolean isScheduleApplyRequest() {
		// 予定一覧表示に申請情報を適用するか否かを確認
		return AttendanceUtility.isScheduleApplyRequest(mospParams);
	}
	
}
