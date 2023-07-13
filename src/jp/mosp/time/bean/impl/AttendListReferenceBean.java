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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.bean.AttendListCheckBoxBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.AttendListEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.utils.AttendanceUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠一覧参照処理。<br>
 */
public class AttendListReferenceBean extends PlatformBean implements AttendanceListReferenceBeanInterface {
	
	/**
	 * プラットフォームマスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface					platformMaster;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	/**
	 * カレンダユーティリティ。<br>
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 勤怠情報参照処理。<br>
	 */
	protected AttendanceReferenceBeanInterface				attendanceRefer;
	
	/**
	 * 残業申請参照処理。<br>
	 */
	protected OvertimeRequestReferenceBeanInterface			overtimeReqRefer;
	
	/**
	 * 休暇申請参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface			holidayReqRefer;
	
	/**
	 * 休日出勤申請参照処理。<br>
	 */
	protected WorkOnHolidayRequestReferenceBeanInterface	workOnHolidayReqRefer;
	
	/**
	 * 振替休日参照処理。<br>
	 */
	protected SubstituteReferenceBeanInterface				substituteRefer;
	
	/**
	 * 代休申請参照処理。<br>
	 */
	protected SubHolidayRequestReferenceBeanInterface		subHolidayReqRefer;
	
	/**
	 * 代休情報参照処理。<br>
	 */
	protected SubHolidayReferenceBeanInterface				subHolidayRefer;
	
	/**
	 * 勤務形態変更申請参照処理。<br>
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface	workTypeChangeReqRefer;
	
	/**
	 * 時差出勤申請参照処理。<br>
	 */
	protected DifferenceRequestReferenceBeanInterface		differenceReqRefer;
	
	/**
	 * ワークフロー参照処理。<br>
	 */
	protected WorkflowReferenceBeanInterface				workflowRefer;
	
	/**
	 * 勤怠修正情報参照処理。<br>
	 */
	protected AttendanceCorrectionReferenceBeanInterface	correctionRefer;
	
	/**
	 * 勤怠一覧チェックボックス要否設定処理。<br>
	 */
	protected AttendListCheckBoxBeanInterface				checkBox;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		timeMaster.setPlatformMaster(platformMaster);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		scheduleUtil.setTimeMaster(timeMaster);
		attendanceRefer = createBeanInstance(AttendanceReferenceBeanInterface.class);
		overtimeReqRefer = createBeanInstance(OvertimeRequestReferenceBeanInterface.class);
		holidayReqRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		workOnHolidayReqRefer = createBeanInstance(WorkOnHolidayRequestReferenceBeanInterface.class);
		substituteRefer = createBeanInstance(SubstituteReferenceBeanInterface.class);
		subHolidayReqRefer = createBeanInstance(SubHolidayRequestReferenceBeanInterface.class);
		workTypeChangeReqRefer = createBeanInstance(WorkTypeChangeRequestReferenceBeanInterface.class);
		differenceReqRefer = createBeanInstance(DifferenceRequestReferenceBeanInterface.class);
		subHolidayRefer = createBeanInstance(SubHolidayReferenceBeanInterface.class);
		workflowRefer = createBeanInstance(WorkflowReferenceBeanInterface.class);
		correctionRefer = createBeanInstance(AttendanceCorrectionReferenceBeanInterface.class);
		checkBox = createBeanInstance(AttendListCheckBoxBeanInterface.class);
		checkBox.setTimeMaster(timeMaster);
	}
	
	@Override
	public List<AttendanceListDto> getScheduleList(String personalId, Date targetDate) throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, targetDate);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_SCHEDULE, false);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getScheduleList(String personalId, int year, int month) throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, year, month);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_SCHEDULE, false);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getScheduleList(String personalId, int year, int month, int cutoffDate)
			throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, year, month, cutoffDate);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_SCHEDULE, false);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getActualList(String personalId, int year, int month) throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, year, month);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_ACTUAL, false);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getActualList(String personalId, int year, int month, int cutoffDate)
			throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, year, month, cutoffDate);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_ACTUAL, false);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getAttendanceList(String personalId, Date targetDate) throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, targetDate);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_ATTENDANCE, true);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getAttendanceList(String personalId, int year, int month) throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, year, month);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_ATTENDANCE, true);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getAttendanceList(String personalId, int year, int month, int cutoffDate)
			throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, year, month, cutoffDate);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_ATTENDANCE, true);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getWeeklyAttendanceList(String personalId, Date targetDate) throws MospException {
		// 勤怠一覧情報リスト(週)(初期化)を取得
		List<AttendanceListDto> attendList = getInitWeeklyAttendList(personalId, targetDate);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_ATTENDANCE, false);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public List<AttendanceListDto> getApprovalAttendanceList(String personalId, int year, int month)
			throws MospException {
		// 勤怠一覧情報リスト(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, year, month);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_APPROVAL, true);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	@Override
	public AttendanceListDto getAttendanceListDto(String personalId, Date targetDate) throws MospException {
		// 勤怠一覧情報リスト(日)(初期化)を取得(要素は対象日の勤怠一覧情報一つのみ)
		List<AttendanceListDto> attendList = getInitAttendList(personalId, targetDate, targetDate);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_ATTENDANCE, false);
		// 勤怠一覧情報を取得
		return MospUtility.getFirstValue(attendList);
	}
	
	@Override
	public List<AttendanceListDto> getTermAttendanceList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// 勤怠一覧情報リスト(期間)(初期化)を取得
		List<AttendanceListDto> attendList = getInitAttendList(personalId, firstDate, lastDate);
		// 勤怠一覧情報リストに値を設定
		fillInAttendList(attendList, AttendanceUtility.TYPE_LIST_ATTENDANCE, true);
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	/**
	 * 勤怠一覧情報リスト(初期化)を取得する。<br>
	 * 引数の対象日を、設定適用情報を取得するための対象日とする。<br>
	 * 対象日が含まれる締月を、対象年月とする。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendanceListDto> getInitAttendList(String personalId, Date targetDate) throws MospException {
		// 対象日で設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 設定適用エンティティが妥当でない場合
		if (application.isValid(mospParams) == false) {
			// 空のリストを取得
			return Collections.emptyList();
		}
		// 対象日が含まれる締月を取得
		Date cuttoffMonth = application.getCutoffEntity().getCutoffMonth(targetDate, mospParams);
		int targetYear = DateUtility.getYear(cuttoffMonth);
		int targetMonth = DateUtility.getMonth(cuttoffMonth);
		// 勤怠一覧情報リストを取得
		return getInitAttendList(personalId, targetYear, targetMonth, application);
	}
	
	/**
	 * 勤怠一覧情報リスト(初期化)を取得する。<br>
	 * 年月指定時の基準日を、設定適用情報を取得するための対象日とする。<br>
	 * 引数の対象年月を、対象年月とする。<br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendanceListDto> getInitAttendList(String personalId, int targetYear, int targetMonth)
			throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 年月指定時の基準日で設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 勤怠一覧情報リストを取得
		return getInitAttendList(personalId, targetYear, targetMonth, application);
	}
	
	/**
	 * 勤怠一覧情報リスト(初期化)を取得する。<br>
	 * 締期間最終日を、設定適用情報を取得するための対象日とする。<br>
	 * 引数の対象年月を、対象年月とする。<br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffDate  締日
	 * @return 勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendanceListDto> getInitAttendList(String personalId, int targetYear, int targetMonth,
			int cutoffDate) throws MospException {
		// 締期間最終日を取得
		Date targetDate = TimeUtility.getCutoffLastDate(cutoffDate, targetYear, targetMonth, mospParams);
		// 年月指定時の基準日で設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 勤怠一覧情報リストを取得
		return getInitAttendList(personalId, targetYear, targetMonth, application);
	}
	
	/**
	 * 勤怠一覧情報リスト(週)(初期化)を取得する。<br>
	 * 引数の対象日を、設定適用情報を取得するための対象日とする。<br>
	 * 対象日が含まれる締月を、対象年月とする。<br>
	 * 有効な設定適用エンティティが取得できなかった場合は、空のリストを返す。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendanceListDto> getInitWeeklyAttendList(String personalId, Date targetDate) throws MospException {
		// 対象日で設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 有効な設定適用エンティティが取得できなかった場合
		if (application.isValid() == false) {
			// 空のリストを取得
			return Collections.emptyList();
		}
		// 週の起算曜日を取得
		int startDayOfWeek = application.getStartDayOfWeek();
		// 対象日を含む週の初日及び最終日を取得
		Date firstDate = DateUtility.getFirstDateOfWeek(startDayOfWeek, targetDate);
		Date lastDate = DateUtility.getLastDateOfWeek(startDayOfWeek, targetDate);
		// 勤怠一覧情報リストを取得
		return getInitAttendList(personalId, firstDate, lastDate);
	}
	
	/**
	 * 勤怠一覧情報リスト(初期化)を取得する。<br>
	 * 年月指定時の締期間分の勤怠一覧情報を取得する。<br>
	 * <br>
	 * 勤怠一覧情報には、次の値が設定される。<br>
	 * ・個人ID<br>
	 * ・勤務日<br>
	 * ・対象年<br>
	 * ・対象月<br>
	 * ・締日<br>
	 * ・カレンダコード<br>
	 * <br>
	 * 締期間を取得できなかった場合、エラーメッセージを設定し、空の勤怠一覧情報リストを取得する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param application 設定適用エンティティ
	 * @return 勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendanceListDto> getInitAttendList(String personalId, int targetYear, int targetMonth,
			ApplicationEntity application) throws MospException {
		// 勤怠一覧情報リストを準備
		List<AttendanceListDto> attendList = new ArrayList<AttendanceListDto>();
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			// 処理終了(空の勤怠一覧情報リストを取得)
			return attendList;
		}
		// 締期間の日付毎に処理
		for (Date workDate : application.getCutoffEntity().getCutoffTerm(targetYear, targetMonth, mospParams)) {
			// 勤怠一覧情報準備
			AttendanceListDto dto = new AttendanceListDto();
			// 個人IDと勤務日と対象年及び対象月とカレンダコードを設定
			dto.setPersonalId(personalId);
			dto.setWorkDate(workDate);
			dto.setTargetYear(targetYear);
			dto.setTargetMonth(targetMonth);
			dto.setCutoffDate(application.getCutoffDate());
			dto.setScheduleCode(application.getScheduleCode());
			// 勤怠一覧情報リストに設定
			attendList.add(dto);
		}
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	/**
	 * 勤怠一覧情報リスト(初期化)を取得する。<br>
	 * 月単位ではない対象期間の勤怠一覧情報を取得する。<br>
	 * <br>
	 * 勤怠一覧情報には、次の値が設定される。<br>
	 * ・個人ID<br>
	 * ・勤務日<br>
	 * <br>
	 * ここでは、対象年、対象月、締日、カレンダコードは、設定されない。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 勤怠一覧情報リスト
	 */
	protected List<AttendanceListDto> getInitAttendList(String personalId, Date firstDate, Date lastDate) {
		// 勤怠一覧情報リストを準備
		List<AttendanceListDto> attendList = new ArrayList<AttendanceListDto>();
		// 締期間の日付毎に処理
		for (Date workDate : TimeUtility.getDateList(firstDate, lastDate)) {
			// 勤怠一覧情報準備
			AttendanceListDto dto = new AttendanceListDto();
			// 個人IDと勤務日と対象年及び対象月とカレンダコードを設定
			dto.setPersonalId(personalId);
			dto.setWorkDate(workDate);
			// 勤怠一覧情報リストに設定
			attendList.add(dto);
		}
		// 勤怠一覧情報リストを取得
		return attendList;
	}
	
	/**
	 * 勤怠一覧情報リストに値を設定する。<br>
	 * 勤怠一覧区分で、設定内容を判断する。<br>
	 * @param attendList 勤怠一覧情報リスト
	 * @param listType   勤怠一覧区分
	 * @param isCheckBox 勤怠一覧チェックボックス設定要否(true：勤怠一覧チェックボックス設定要、false：不要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void fillInAttendList(List<AttendanceListDto> attendList, int listType, boolean isCheckBox)
			throws MospException {
		// 勤怠一覧情報リストが空である場合
		if (MospUtility.isEmpty(attendList)) {
			// 処理無し
			return;
		}
		// 勤怠一覧エンティティを取得
		AttendListEntityInterface entity = getAttendListEntity(attendList);
		// 勤怠一覧情報リストに値を設定
		entity.fillInAttendList(listType);
		// 勤怠一覧チェックボックス設定要である場合
		if (isCheckBox) {
			// 勤怠一覧情報に勤怠一覧チェックボックス要否を設定
			checkBox.setCheckBox(attendList, entity);
		}
		// 備考を設定(追加業務ロジック処理)
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTEND_LIST_REMARKS, entity);
		// 勤怠設定関連の項目を設定
		fillTimeSettingFields(attendList);
		// ヘッダ項目(社員名等)を設定
		fillHeadersAndFooters(attendList);
		// 追加業務ロジック処理(備考以外)
		doStoredLogic(TimeConst.CODE_KEY_ADD_ATTEND_LIST_AFTER, entity);
	}
	
	/**
	 * 勤怠一覧エンティティを取得する。<br>
	 * @param attendList 勤怠一覧情報リスト
	 * @return 勤怠一覧エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected AttendListEntityInterface getAttendListEntity(List<AttendanceListDto> attendList) throws MospException {
		// 勤怠一覧エンティティを準備
		AttendListEntityInterface entity = createObject(AttendListEntityInterface.class);
		// MosP処理情報を設定
		entity.setMospParams(mospParams);
		// 勤怠一覧情報の最初の要素を取得
		AttendanceListDto dto = MospUtility.getFirstValue(attendList);
		// 個人ID及び対象期間を取得
		String personalId = dto.getPersonalId();
		Date firstDate = dto.getWorkDate();
		Date lastDate = MospUtility.getLastValue(attendList).getWorkDate();
		// 勤怠一覧情報リストを設定
		entity.setAttendList(attendList);
		// 人事退職休職情報を設定
		entity.setRetireDate(HumanUtility.getRetireDate(platformMaster.getRetirement(personalId)));
		entity.setSuspensions(platformMaster.getSuspensions(personalId));
		// カレンダ日情報群(キー：日)を取得
		Map<Date, ScheduleDateDtoInterface> schedules = scheduleUtil.getScheduleDates(personalId, firstDate, lastDate);
		// 申請等情報を取得
		Map<Date, AttendanceDtoInterface> attendances = attendanceRefer.getAttendances(personalId, firstDate, lastDate);
		Map<Date, List<OvertimeRequestDtoInterface>> overtimeRequests = overtimeReqRefer.getOvertimeRequests(personalId,
				firstDate, lastDate);
		Map<Date, List<HolidayRequestDtoInterface>> holidayRequests = holidayReqRefer.getHolidayRequests(personalId,
				firstDate, lastDate);
		List<WorkOnHolidayRequestDtoInterface> workOnHolidayRequestList = workOnHolidayReqRefer
			.getWorkOnHolidayRequestList(personalId, firstDate, lastDate);
		Map<Date, List<SubstituteDtoInterface>> substitutes = substituteRefer.getSubstitutes(personalId, firstDate,
				lastDate);
		Map<Date, List<SubHolidayRequestDtoInterface>> subHolidayRequests = subHolidayReqRefer
			.getSubHolidayRequests(personalId, firstDate, lastDate);
		Map<Integer, List<SubHolidayDtoInterface>> subHolidays = subHolidayRefer.getSubHolidays(personalId, firstDate,
				lastDate);
		List<WorkTypeChangeRequestDtoInterface> workTypeChangeRequestList = workTypeChangeReqRefer
			.getWorkTypeChangeRequestList(personalId, firstDate, lastDate);
		List<DifferenceRequestDtoInterface> differenceRequestList = differenceReqRefer
			.getDifferenceRequestList(personalId, firstDate, lastDate);
		// ワークフロー番号群を取得
		Set<Long> workflowSet = WorkflowUtility.getWorkflowSet(attendances.values(), overtimeRequests.values(),
				holidayRequests.values(), workOnHolidayRequestList, substitutes.values(), subHolidayRequests.values(),
				workTypeChangeRequestList, differenceRequestList);
		// ワークフロー情報群を取得
		Map<Long, WorkflowDtoInterface> workflows = workflowRefer.getWorkflows(workflowSet);
		// 取下以外の承認状況群を取得
		Set<String> statuses = WorkflowUtility.getStatusesExceptWithDrawn();
		// 休日出勤申請情報群(キー：申請日)(取下を除く)を取得
		Map<Date, WorkOnHolidayRequestDtoInterface> workOnHolidayRequests = TimeUtility
			.getRequestDateMap(workOnHolidayRequestList, workflows, statuses);
		// 勤務形態変更申請情報群(キー：申請日)(取下を除く)を取得
		Map<Date, WorkTypeChangeRequestDtoInterface> workTypeChangeRequests = TimeUtility
			.getRequestDateMap(workTypeChangeRequestList, workflows, statuses);
		// 時差出勤申請情報群(キー：申請日)(取下を除く)を取得
		Map<Date, DifferenceRequestDtoInterface> differenceRequests = TimeUtility
			.getRequestDateMap(differenceRequestList, workflows, statuses);
		// カレンダ日情報群(キー：日)を設定
		entity.setScheduleDates(schedules);
		// 申請等情報を設定
		entity.setAttendances(attendances);
		entity.setOvertimeRequests(overtimeRequests);
		entity.setHolidayRequests(holidayRequests);
		entity.setWorkOnHolidayRequests(workOnHolidayRequests);
		entity.setSubstitutes(substitutes);
		entity.setSubHolidayRequests(subHolidayRequests);
		entity.setSubHolidays(subHolidays);
		entity.setWorkTypeChangeRequests(workTypeChangeRequests);
		entity.setDifferenceRequests(differenceRequests);
		// ワークフロー情報群を設定
		entity.setWorkflows(workflows);
		// 休日出勤申請情報(振替申請(勤務形態変更なし)のみ)(下書及び取下以外)から出勤日群を取得
		Set<Date> substituteWorkDates = TimeUtility.getSubstituteWorkDates(workOnHolidayRequests, workflows);
		// 振替日群(キー：出勤日)を取得
		Map<Date, Date> substituteDates = substituteRefer.getSubstituteDates(personalId, substituteWorkDates);
		// 振り替えられたカレンダ日情報群(キー：勤務日)を取得
		Map<Date, ScheduleDateDtoInterface> substitutedSchedules = scheduleUtil.getSubstitutedSchedules(personalId,
				substituteDates);
		// 振り替えられたカレンダ日情報群(キー：勤務日)を設定
		entity.setSubstitutedSchedules(substitutedSchedules);
		// 勤務形態コード群を取得
		Set<String> workTypeCodes = TimeUtility.getWorkTypeCodes(schedules.values(), attendances.values(),
				workOnHolidayRequests.values(), workTypeChangeRequests.values(), differenceRequests.values(),
				substitutedSchedules.values());
		// 勤務形態エンティティ群を設定
		entity.setWorkTypeEntities(timeMaster.getWorkTypeEntities(workTypeCodes));
		// 勤怠修正情報群(キー：勤務日)を設定
		entity.setCorrections(correctionRefer.getCorrections(personalId, firstDate, lastDate));
		// 休暇種別情報群(対象期間最終日における情報)を設定
		entity.setHolidays(timeMaster.getHolidaySet(lastDate));
		// 勤怠一覧エンティティを取得
		return entity;
	}
	
	/**
	 * ヘッダ及びフッタ項目(社員名等)を設定する。<br>
	 * @param attendList 勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void fillHeadersAndFooters(List<AttendanceListDto> attendList) throws MospException {
		// 勤怠一覧情報リストの最初と最後の要素を取得
		AttendanceListDto headerDto = MospUtility.getFirstValue(attendList);
		AttendanceListDto footerDto = MospUtility.getLastValue(attendList);
		// 個人ID及び対象日(対象期間最終日)を取得
		String personalId = headerDto.getPersonalId();
		Date targetDate = footerDto.getWorkDate();
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 人事情報を取得
		HumanDtoInterface human = platformMaster.getHuman(personalId, targetDate);
		// 人事情報を取得できなかった場合(出勤簿のエクスポートで発生する可能性がある)
		if (MospUtility.isEmpty(human)) {
			// 処理終了(人事情報の出力内容はエクスポートの処理で行うため設定しなくても問題無い)
			return;
		}
		// 社員コードを設定
		headerDto.setEmployeeCode(human.getEmployeeCode());
		// 社員氏名を設定
		headerDto.setEmployeeName(MospUtility.getHumansName(human.getFirstName(), human.getLastName()));
		// 所属コードを取得
		String sectionCode = human.getSectionCode();
		// 所属名称か表示名称を設定
		headerDto.setSectionName(platformMaster.getSectionNameOrDisplay(sectionCode, targetDate, mospParams));
		// 帳票承認印欄表示設定
		headerDto.setSealBoxAvailable(true);
		// 帳票代休申請要否を設定
		footerDto.setSubHolidayRequestValid(TimeUtility.isSubHolidayRequestValid(mospParams));
		// 有給休暇情報を参照し時間単位有給休暇機能が有効でない場合
		if (application.isHourlyPaidHolidayAvailable() == false) {
			// 時間単位有給休暇時間にハイフンを設定
			footerDto.setPaidHolidayTimeString(PfNameUtility.hyphen(mospParams));
		}
	}
	
	/**
	 * 勤怠設定関連の項目を設定する。<br>
	 * <br>
	 * 以下の処理を行う。<br>
	 * ・限度基準情報(外残の文字色)を設定する。<br>
	 * ・勤務予定時間表示に合わせて始終業時刻(表示用)を再設定する。<br>
	 * @param attendList 勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void fillTimeSettingFields(List<AttendanceListDto> attendList) throws MospException {
		// 勤怠一覧情報リストの最後の要素を取得
		AttendanceListDto lastDto = MospUtility.getLastValue(attendList);
		// 個人ID及び対象日(対象期間最終日)を取得
		String personalId = lastDto.getPersonalId();
		Date targetDate = lastDto.getWorkDate();
		// 勤怠設定エンティティを取得
		TimeSettingEntityInterface timeSetting = timeMaster.getTimeSettingForPersonalId(personalId, targetDate);
		// 限度基準情報(外残の文字色)を設定
		fillLimitStandard(attendList, timeSetting);
		// 勤務予定時間表示に合わせて始終業時刻(表示用)を再設定
		AttendanceUtility.resetStartEndTimeString(mospParams, attendList, timeSetting);
	}
	
	/**
	 * 限度基準情報(外残の文字色)を設定する。<br>
	 * @param attendList  勤怠一覧情報リスト
	 * @param timeSetting 勤怠設定エンティティ
	 */
	protected void fillLimitStandard(List<AttendanceListDto> attendList, TimeSettingEntityInterface timeSetting) {
		// 法定外残業時間合計値を準備
		int overtimeTotal = 0;
		// 勤怠一覧情報毎に残業時間を確認
		for (AttendanceListDto dto : attendList) {
			// 法定外残業時間を取得
			int overtimeOut = MospUtility.getInt(dto.getOvertimeOut());
			// 法定外残業時間が0である場合
			if (overtimeOut == 0) {
				// 外残の文字色は設定不要
				continue;
			}
			// 法定外残業時間加算
			overtimeTotal += overtimeOut;
			// 外残のスタイル文字列を設定
			dto.setOvertimeStyle(timeSetting.getOneMonthStyle(overtimeTotal));
		}
	}
	
}
