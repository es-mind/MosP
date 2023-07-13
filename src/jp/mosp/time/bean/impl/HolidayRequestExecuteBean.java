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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestExecuteBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇申請実行処理。<br>
 */
public class HolidayRequestExecuteBean extends PlatformBean implements HolidayRequestExecuteBeanInterface {
	
	/**
	 * 承認者個人ID配列(自己承認)。<br>
	 */
	private static final String[]						APPROVER_IDS_FOR_SELF	= { PlatformConst.APPROVAL_ROUTE_SELF };
	
	/**
	 * 休暇申請参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface		refer;
	
	/**
	 * 休暇申請登録処理。<br>
	 */
	protected HolidayRequestRegistBeanInterface			regist;
	
	/**
	 * ワークフロー登録処理。<br>
	 */
	protected WorkflowRegistBeanInterface				workflowRegist;
	
	/**
	 * ワークフロー登録処理。<br>
	 */
	protected WorkflowCommentRegistBeanInterface		workflowCommentRegist;
	
	/**
	 * 勤怠トランザクション登録処理。<br>
	 */
	protected AttendanceTransactionRegistBeanInterface	attendanceTransactionRegist;
	
	/**
	 * カレンダユーティリティ。<br>
	 */
	protected ScheduleUtilBeanInterface					scheduleUtil;
	
	/**
	 * 有給休暇残数取得処理。<br>
	 */
	protected PaidHolidayRemainBeanInterface			paidHolidayRemain;
	
	/**
	 * 休暇数参照処理。<br>
	 */
	protected HolidayInfoReferenceBeanInterface			holidayInfo;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface					timeMaster;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayRequestExecuteBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		refer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		regist = createBeanInstance(HolidayRequestRegistBeanInterface.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = createBeanInstance(WorkflowCommentRegistBeanInterface.class);
		attendanceTransactionRegist = createBeanInstance(AttendanceTransactionRegistBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		paidHolidayRemain = createBeanInstance(PaidHolidayRemainBeanInterface.class);
		holidayInfo = createBeanInstance(HolidayInfoReferenceBeanInterface.class);
		// 勤怠関連マスタ参照処理を準備(準備したBeanにも設定するため最後に実施)
		setTimeMaster(createBeanInstance(TimeMasterBeanInterface.class));
	}
	
	@Override
	public void draft(String personalId, Date requestStartDate, Date requestEndDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime, int hours, String requestReason, long recordId,
			String[] approverIds) throws MospException {
		// 休暇申請情報リストを取得
		List<HolidayRequestDtoInterface> dtos = getHolidayRequests(personalId, requestStartDate, requestEndDate,
				holidayType1, holidayType2, holidayRange, startTime, hours);
		// 休暇申請情報(リストの最初の休暇申請情報)にレコード識別IDとワークフロー番号を設定
		setRecordIdAndWorkflow(dtos, recordId);
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 休暇申請情報に休暇取得日及び使用日数を設定
			setAcquisitionDateAndUseDays(dto, null);
			// 休暇取得日が取得できない(休暇残数が無い)場合
			if (mospParams.hasErrorMessage()) {
				// 処理終了
				return;
			}
			// 申請理由を設定
			dto.setRequestReason(requestReason);
			// 休暇申請情報を下書
			draft(dto, approverIds);
		}
	}
	
	@Override
	public void apply(String personalId, Date requestStartDate, Date requestEndDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime, int hours, String requestReason, long recordId,
			String[] approverIds) throws MospException {
		// 休暇申請を申請
		apply(personalId, requestStartDate, requestEndDate, holidayType1, holidayType2, holidayRange, startTime, hours,
				requestReason, recordId, approverIds, null);
	}
	
	@Override
	public void apply(String personalId, Date requestStartDate, Date requestEndDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime, int hours, String requestReason, int row)
			throws MospException {
		// 休暇申請(インポート)を申請
		apply(personalId, requestStartDate, requestEndDate, holidayType1, holidayType2, holidayRange, startTime, hours,
				requestReason, 0L, APPROVER_IDS_FOR_SELF, row);
	}
	
	@Override
	public boolean batchUpdate(String personalId, long[] recordIds) throws MospException {
		// レコード識別ID配列を取得できなかった場合
		if (MospUtility.isEmpty(recordIds)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequireCheck(mospParams);
			// 処理終了(半休は含まれていないと判断)
			return false;
		}
		// 半休有無を準備
		boolean isHalfHolidayContained = false;
		// レコード識別ID毎に処理
		for (long recordId : recordIds) {
			// レコード識別で休暇申請情報を取得
			HolidayRequestDtoInterface dto = refer.findForKey(recordId);
			// レコード識別で休暇申請情報を取得できなかった(削除されていた)場合
			if (PlatformUtility.isDtoDeleted(dto)) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorExclusive(mospParams);
				// 処理終了
				return isHalfHolidayContained;
			}
			// 休暇申請情報に休暇取得日及び使用日数を設定(下書時と状況が変わっている可能性があるため再設定)
			setAcquisitionDateAndUseDays(dto, null);
			// 休暇取得日が取得できない(休暇残数が無い)場合
			if (mospParams.hasErrorMessage()) {
				// 処理終了
				return isHalfHolidayContained;
			}
			// 休暇申請情報を申請
			applyForBatchUpdate(dto);
			// 半休有無を設定
			isHalfHolidayContained = isHalfHolidayContained ? true : TimeRequestUtility.isHolidayRangeHalf(dto);
		}
		// 半休有無を取得
		return isHalfHolidayContained;
	}
	
	@Override
	public List<Date> getConsecutiveHolidayDates(String personalId, Date requestStartDate, Date requestEndDate)
			throws MospException {
		// 連続休暇の休暇対象日リストを準備
		List<Date> holidayDates = new ArrayList<Date>();
		// 申請期間の申請日リストを取得
		List<Date> dateList = TimeUtility.getDateList(requestStartDate, requestEndDate);
		// 申請日毎に処理
		for (Date requestDate : dateList) {
			// 申請日の勤務形態コードを取得
			String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, requestDate, true);
			// 勤務形態が休日か休日出勤である場合
			if (TimeRequestUtility.isNotHolidayForConsecutiveHolidays(workTypeCode)) {
				// 次の申請日へ(申請期間中の休日及び休日出勤日は連続休暇の休暇対象日でない)
				continue;
			}
			// 申請日を連続休暇の休暇対象日リストに設定
			holidayDates.add(requestDate);
		}
		// 連続休暇の休暇対象日リストを取得
		return holidayDates;
	}
	
	/**
	 * 休暇申請を申請する。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param holidayRange     休暇範囲
	 * @param startTime        時休開始時刻
	 * @param hours            時休時間数
	 * @param requestReason    申請理由
	 * @param recordId         レコード識別ID
	 * @param approverIds      承認者個人ID配列
	 * @param row              行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void apply(String personalId, Date requestStartDate, Date requestEndDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime, int hours, String requestReason, long recordId,
			String[] approverIds, Integer row) throws MospException {
		// 休暇申請情報リストを取得
		List<HolidayRequestDtoInterface> dtos = getHolidayRequests(personalId, requestStartDate, requestEndDate,
				holidayType1, holidayType2, holidayRange, startTime, hours);
		// 休暇申請情報(リストの最初の休暇申請情報)にレコード識別IDとワークフロー番号を設定
		setRecordIdAndWorkflow(dtos, recordId);
		// 休暇申請情報毎に処理(申請する度に休暇残数が変化するため都度休暇取得日を設定する必要がある)
		for (HolidayRequestDtoInterface dto : dtos) {
			// 休暇申請情報に休暇取得日及び使用日数を設定
			setAcquisitionDateAndUseDays(dto, row);
			// 休暇取得日が取得できない(休暇残数が無い)場合
			if (mospParams.hasErrorMessage()) {
				// 処理終了
				return;
			}
			// 申請理由を設定
			dto.setRequestReason(requestReason);
			// 休暇申請情報を申請
			apply(dto, approverIds, row);
		}
	}
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * 引数を基に、休暇申請情報を作成する。<br>
	 * 但し、休暇取得日及び使用日数は、ここでは設定しない。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param holidayRange     休暇範囲
	 * @param startTime        時休開始時刻
	 * @param hours            時休時間数
	 * @return 休暇申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HolidayRequestDtoInterface> getHolidayRequests(String personalId, Date requestStartDate,
			Date requestEndDate, int holidayType1, String holidayType2, int holidayRange, Date startTime, int hours)
			throws MospException {
		// 時間休である場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時間単位休暇申請情報リストを取得
			return getHourlyHolidayRequests(personalId, requestStartDate, holidayType1, holidayType2, startTime, hours);
		}
		// 有給(及びストック)休暇である場合
		if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			// 有給(及びストック)休暇申請情報リストを取得
			return getPaidHolidayRequests(personalId, requestStartDate, requestEndDate, holidayType2, holidayRange);
		}
		// 休暇申請情報リストを準備
		List<HolidayRequestDtoInterface> dtos = new ArrayList<HolidayRequestDtoInterface>();
		// 休暇申請情報を取得し設定(有給(及びストック)休暇でなく時間休でもない場合は期間で1申請)
		dtos.add(getHolidayRequest(personalId, requestStartDate, requestEndDate, holidayType1, holidayType2,
				holidayRange));
		// 休暇申請情報リストを取得
		return dtos;
	}
	
	/**
	 * 有給(及びストック)休暇申請情報リストを取得する。<br>
	 * 引数を基に、休暇申請情報を作成する。<br>
	 * 但し、休暇取得日及び使用日数は、ここでは設定しない。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @param holidayType2     休暇種別2(有給休暇かストック休暇)
	 * @param holidayRange     休暇範囲
	 * @return 有給(及びストック)休暇申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HolidayRequestDtoInterface> getPaidHolidayRequests(String personalId, Date requestStartDate,
			Date requestEndDate, String holidayType2, int holidayRange) throws MospException {
		// 有給(及びストック)休暇申請情報リストを準備
		List<HolidayRequestDtoInterface> dtos = new ArrayList<HolidayRequestDtoInterface>();
		// 連続休暇の休暇対象日リストを取得
		List<Date> holidayDates = getConsecutiveHolidayDates(personalId, requestStartDate, requestEndDate);
		// 申請日毎に処理(有給及びストック休暇申請は1日1申請)
		for (Date requestDate : holidayDates) {
			// 有給(及びストック)休暇申請情報を取得し設定
			dtos.add(getPaidHolidayRequest(personalId, requestDate, holidayType2, holidayRange));
		}
		// 有給(及びストック)休暇申請情報リストを取得
		return dtos;
	}
	
	/**
	 * 時間単位休暇申請情報リストを取得する。<br>
	 * 引数を基に、休暇申請情報を作成する。<br>
	 * 但し、休暇取得日及び使用日数は、ここでは設定しない。<br>
	 * @param personalId   個人ID
	 * @param requestDate  申請日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param startTime    時休開始時刻
	 * @param hours        時休時間数
	 * @return 時間単位休暇申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HolidayRequestDtoInterface> getHourlyHolidayRequests(String personalId, Date requestDate,
			int holidayType1, String holidayType2, Date startTime, int hours) throws MospException {
		// 休暇申請情報(有給休暇)リストを準備
		List<HolidayRequestDtoInterface> dtos = new ArrayList<HolidayRequestDtoInterface>();
		// 時休時間数を確認
		checkHours(hours);
		// エラーメッセージが設定されている場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return dtos;
		}
		// 時休時間毎に処理
		for (int i = 0; i < hours; i++) {
			// 時休開始時刻を取得
			Date start = DateUtility.addHour(startTime, i);
			// 休暇申請情報(時間単位)を取得し設定
			dtos.add(getHourlyHolidayRequest(personalId, requestDate, holidayType1, holidayType2, start));
		}
		// 休暇申請情報(有給休暇)リストを取得
		return dtos;
	}
	
	/**
	 * 休暇申請情報(時間単位)を取得する。<br>
	 * 引数を基に、休暇申請情報(時間単位)を作成する。<br>
	 * 但し、休暇取得日及び使用日数は、ここでは設定しない。<br>
	 * 時休終了時刻及び使用時間数は、時間単位は1時間1申請であるため、時休開始時刻から取得する。<br>
	 * @param personalId   個人ID
	 * @param requestDate  申請日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param startTime    時休開始時刻
	 * @return 休暇申請情報(時間単位)
	 */
	protected HolidayRequestDtoInterface getHourlyHolidayRequest(String personalId, Date requestDate, int holidayType1,
			String holidayType2, Date startTime) {
		// 休暇申請情報を準備
		HolidayRequestDtoInterface dto = regist.getInitDto();
		// 値を設定
		dto.setPersonalId(personalId);
		dto.setRequestStartDate(requestDate);
		dto.setRequestEndDate(requestDate);
		dto.setHolidayType1(holidayType1);
		dto.setHolidayType2(holidayType2);
		dto.setHolidayRange(TimeConst.CODE_HOLIDAY_RANGE_TIME);
		dto.setStartTime(startTime);
		dto.setEndTime(DateUtility.addHour(startTime, 1));
		dto.setUseHour(1);
		// 休暇申請情報を取得
		return dto;
	}
	
	/**
	 * 休暇申請情報を取得する。<br>
	 * 引数を基に、休暇申請情報(時間単位でない)を作成する。<br>
	 * 但し、休暇取得日及び使用日数は、ここでは設定しない。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param holidayRange     休暇範囲
	 * @return 休暇申請情報
	 */
	protected HolidayRequestDtoInterface getHolidayRequest(String personalId, Date requestStartDate,
			Date requestEndDate, int holidayType1, String holidayType2, int holidayRange) {
		// 休暇申請情報を準備
		HolidayRequestDtoInterface dto = regist.getInitDto();
		// 値を設定
		dto.setPersonalId(personalId);
		dto.setRequestStartDate(requestStartDate);
		dto.setRequestEndDate(requestEndDate);
		dto.setHolidayType1(holidayType1);
		dto.setHolidayType2(holidayType2);
		dto.setHolidayRange(holidayRange);
		dto.setStartTime(requestStartDate);
		dto.setEndTime(requestStartDate);
		dto.setUseHour(0);
		// 休暇申請情報を取得
		return dto;
	}
	
	/**
	 * 有給(及びストック)休暇申請情報を取得する。<br>
	 * 引数を基に、休暇申請情報(時間単位でない)を作成する。<br>
	 * 但し、休暇取得日及び使用日数は、ここでは設定しない。<br>
	 * @param personalId   個人ID
	 * @param requestDate  申請開始日
	 * @param holidayType2 休暇種別2
	 * @param holidayRange 休暇範囲
	 * @return 有給(及びストック)休暇申請情報
	 */
	protected HolidayRequestDtoInterface getPaidHolidayRequest(String personalId, Date requestDate, String holidayType2,
			int holidayRange) {
		// 休暇申請情報を取得
		return getHolidayRequest(personalId, requestDate, requestDate, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, holidayType2,
				holidayRange);
	}
	
	/**
	 * 時休時間数を確認する。<br>
	 * @param hours 時休時間数
	 */
	protected void checkHours(int hours) {
		// 時休時間数が無い場合
		if (hours <= 0) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, TimeNamingUtility.hourlyHoliday(mospParams));
		}
	}
	
	/**
	 * 休暇申請情報(リストの最初の休暇申請情報)にレコード識別IDとワークフロー番号を設定する。<br>
	 * 但し、レコード識別IDが無い(0である)場合には、何も設定しない。<br>
	 * @param dtos     休暇申請リスト
	 * @param recordId レコード識別ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setRecordIdAndWorkflow(List<HolidayRequestDtoInterface> dtos, long recordId) throws MospException {
		// レコード識別IDが無い(0である)場合
		if (recordId == 0D) {
			// 処理終了
			return;
		}
		// リストの最初の休暇申請を取得
		HolidayRequestDtoInterface dto = MospUtility.getFirstValue(dtos);
		// リストの最初の休暇申請を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 処理終了
			return;
		}
		// レコード識別IDで登録済の休暇申請情報を取得
		HolidayRequestDtoInterface registeredDto = refer.findForKey(recordId);
		// レコード識別IDで登録済の休暇申請情報を取得できなかった場合
		if (MospUtility.isEmpty(registeredDto)) {
			// 処理終了
			return;
		}
		// レコード識別IDを設定
		dto.setTmdHolidayRequestId(recordId);
		// ワークフロー番号を設定
		dto.setWorkflow(registeredDto.getWorkflow());
	}
	
	/**
	 * 休暇申請情報に休暇取得日及び使用日数を設定する。<br>
	 * 休暇残数には下書や一次戻の申請は含まれないため、これらの申請を除去する必要は無い。<br>
	 * 休暇取得日が取得できない(休暇残数が無い)場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param dto 休暇申請情報
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAcquisitionDateAndUseDays(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		// 有給休暇申請である場合
		if (TimeRequestUtility.isPaidHoliday(dto)) {
			// 休暇申請情報に休暇取得日及び使用日数を設定
			setAcquisitionDateAndUseDaysForPaidHoliday(dto, row);
		}
		// ストック休暇申請である場合
		if (TimeRequestUtility.isStockHoliday(dto)) {
			// 休暇申請情報に休暇取得日及び使用日数を設定
			setAcquisitionDateAndUseDaysForStockHoliday(dto, row);
		}
		// 特別休暇・その他休暇・欠勤の場合
		if (TimeRequestUtility.isSpecialHoliday(dto) || TimeRequestUtility.isOtherHoliday(dto)
				|| TimeRequestUtility.isAbsenece(dto)) {
			// 休暇申請情報に休暇取得日及び使用日数を設定
			setAcquisitionDateAndUseDaysForHoliday(dto, row);
		}
	}
	
	/**
	 * 有給休暇申請情報に休暇取得日及び使用日数を設定する。<br>
	 * @param dto 休暇申請情報
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAcquisitionDateAndUseDaysForPaidHoliday(HolidayRequestDtoInterface dto, Integer row)
			throws MospException {
		// 休暇申請情報から個人IDと対象日を取得
		String personalId = dto.getPersonalId();
		Date targetDate = dto.getRequestStartDate();
		// 休暇数を取得し休暇申請情報に設定
		dto.setUseDay(TimeUtility.getHolidayTimes(dto.getHolidayRange()));
		// 休暇残情報(有給休暇)リスト(取得日昇順)を取得
		List<HolidayRemainDto> remains = paidHolidayRemain.getPaidHolidayRemainsForRequest(personalId, targetDate);
		// 休暇取得日を取得
		Date acquisitionDate = getAcquisitionDateForHolidayRequest(remains, dto).orElse(null);
		// 休暇取得日が取得できなかった場合
		if (MospUtility.isEmpty(acquisitionDate)) {
			// 休暇(範囲)情報が時間休である場合
			if (TimeRequestUtility.isHolidayRangeHour(dto)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShortPaidHolidayRemainHours(mospParams, row);
				// 処理終了
				return;
			}
			// エラーメッセージを設定
			TimeMessageUtility.addErrorShortPaidHolidayRemainDays(mospParams, row);
			// 処理終了
			return;
		}
		// 有給休暇申請情報に休暇取得日及び使用日数を設定
		dto.setHolidayAcquisitionDate(acquisitionDate);
		// 全日の有給休暇に対して休暇取得日より前に半日の有給休暇が残っていないかを確認
		checkPreviousPaidHolidayRemain(dto, remains, row);
	}
	
	/**
	 * ストック休暇申請情報に休暇取得日及び使用日数を設定する。<br>
	 * @param dto 休暇申請情報
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAcquisitionDateAndUseDaysForStockHoliday(HolidayRequestDtoInterface dto, Integer row)
			throws MospException {
		// 休暇申請情報から個人IDと対象日と休暇範囲を取得
		String personalId = dto.getPersonalId();
		Date targetDate = dto.getRequestStartDate();
		// 休暇数を取得しストック休暇申請情報に設定
		dto.setUseDay(TimeUtility.getHolidayTimes(dto.getHolidayRange()));
		// 休暇残情報(ストック休暇)リスト(取得日昇順)を取得
		List<HolidayRemainDto> remains = paidHolidayRemain.getStockHolidayRemainsForRequest(personalId, targetDate);
		// 休暇取得日を取得
		Date acquisitionDate = getAcquisitionDateForHolidayRequest(remains, dto).orElse(null);
		// 休暇取得日が取得できなかった場合
		if (MospUtility.isEmpty(acquisitionDate)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorShortStockHolidayRemainDays(mospParams, row);
			// 処理終了
			return;
		}
		// ストック休暇申請情報に休暇取得日を設定
		dto.setHolidayAcquisitionDate(acquisitionDate);
	}
	
	/**
	 * 全日の休暇に対して休暇取得日より前に半日の有給休暇が残っていないかを確認する。<br>
	 * 残っている場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param dto     休暇申請情報
	 * @param remains 休暇残情報(有給休暇)リスト(取得日昇順)
	 * @param row     行インデックス
	 */
	protected void checkPreviousPaidHolidayRemain(HolidayRequestDtoInterface dto, List<HolidayRemainDto> remains,
			Integer row) {
		// 休暇(範囲)情報が全休でない場合
		if (TimeRequestUtility.isHolidayRangeAll(dto) == false) {
			// 処理無し
			return;
		}
		// 休暇取得日を取得
		Date acquisitionDate = dto.getHolidayAcquisitionDate();
		// 休暇残情報毎に処理
		for (HolidayRemainDto remain : remains) {
			// 休暇残情報の取得日よりもが休暇取得日よりも前でない場合
			if (remain.getAcquisitionDate().before(acquisitionDate) == false) {
				// 次の休暇残情報へ(確認対象外)
				continue;
			}
			// 半日の有給休暇が残っている場合
			if (TimeUtility.isHolidayTimesHalf(remain.getRemainDays())) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorPreviousPaidHolidayRemain(mospParams, row);
				// 処理終了
				return;
			}
		}
	}
	
	/**
	 * 休暇残情報リストから休暇取得日を取得する。<br>
	 * 休暇申請情報の休暇を取得し得る休暇残情報から休暇取得日を取得する。<br>
	 * 休暇取得日を取得できなかった場合は、nullを取得する。<br>
	 * 休暇申請情報には、休暇範囲と使用日数と使用時間数が設定されている必要がある。<br>
	 * @param remains 休暇残情報リスト(取得日昇順)
	 * @param dto     休暇申請情報
	 * @return 休暇取得日
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected Optional<Date> getAcquisitionDateForHolidayRequest(List<HolidayRemainDto> remains,
			HolidayRequestDtoInterface dto) throws MospException {
		// 休暇取得日を準備
		Date acquisitionDate = null;
		// 休暇残情報の残数の合計が申請休暇数を超えている場合(手動破棄した場合等に起こり得る)
		if (isHolidayRemain(TimeUtility.getTotalHolidayRemain(remains), dto) == false) {
			// 休暇取得日を取得
			return Optional.ofNullable(acquisitionDate);
		}
		// 休暇残情報毎に処理
		for (HolidayRemainDto remain : remains) {
			// 休暇が残っている(利用できる)場合
			if (isHolidayRemain(remain, dto)) {
				// 休暇取得日を取得
				acquisitionDate = remain.getAcquisitionDate();
				// 処理終了
				break;
			}
		}
		// 休暇取得日を取得
		return Optional.ofNullable(acquisitionDate);
	}
	
	/**
	 * 休暇が残っている(利用できる)かを確認する。<br>
	 * 休暇申請情報の休暇を取得し得るかを確認する。<br>
	 * @param remain 休暇残情報
	 * @param dto    休暇申請情報
	 * @return 確認結果(true：休暇が残っている、false：休暇が残っていない)
	 */
	protected boolean isHolidayRemain(HolidayRemainDto remain, HolidayRequestDtoInterface dto) {
		// 休暇申請情報から使用日数と使用時間数を取得
		double useDays = dto.getUseDay();
		int useHours = dto.getUseHour();
		// 時間休である場合
		if (TimeRequestUtility.isHolidayRangeHour(dto)) {
			// 残時間数が使用時間数以上であるか残日数が1以上であるかを確認(時間休は1申請1時間)
			return useHours <= remain.getRemainHours() || TimeConst.HOLIDAY_TIMES_ALL <= remain.getRemainDays();
		}
		// 残日数が使用日数以上であるかを確認
		return useDays <= remain.getRemainDays();
	}
	
	/**
	 * 休暇申請情報(特別休暇・その他休暇・欠勤)に休暇取得日及び使用日数を設定する。<br>
	 * @param dto 休暇申請情報
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAcquisitionDateAndUseDaysForHoliday(HolidayRequestDtoInterface dto, Integer row)
			throws MospException {
		// 休暇申請情報から個人IDと対象日(申請開始日)と申請終了日を取得
		String personalId = dto.getPersonalId();
		Date targetDate = dto.getRequestStartDate();
		Date requestEndDate = dto.getRequestEndDate();
		// 休暇申請情報から休暇種別を取得
		String holidayCode = dto.getHolidayType2();
		int holidayType = dto.getHolidayType1();
		// 休暇種別情報を取得
		HolidayDtoInterface holidayDto = timeMaster.getHoliday(holidayCode, holidayType, targetDate);
		// 有効な休暇種別情報を取得できなかった場合
		if (PlatformUtility.isDtoActivate(holidayDto) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.holidayType(mospParams), row);
			// 処理終了
			return;
		}
		// 使用日数を取得し設定
		dto.setUseDay(getUseDaysForHoliday(dto));
		// 欠勤である場合
		if (TimeRequestUtility.isAbsenece(dto)) {
			// 休暇取得日は申請開始日(欠勤には休暇情報は無い(休暇の付与はできない))
			dto.setHolidayAcquisitionDate(dto.getRequestStartDate());
			// 処理終了
			return;
		}
		// 申請可能な休暇残情報を取得
		HolidayRemainDto remain = holidayInfo.getAppliableHoliday(personalId, targetDate, holidayCode, holidayType);
		// 申請可能な休暇残情報が取得できなかった場合
		if (MospUtility.isEmpty(remain)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorHolidayNotGranted(mospParams, holidayDto.getHolidayName(), row);
			// 処理終了
			return;
		}
		// 休暇種別情報が無制限でなく休暇が残っていない(利用できない)場合
		if (TimeUtility.isUnlimited(holidayDto) == false && isHolidayRemain(remain, dto) == false) {
			// 休暇(範囲)情報が時間休である場合
			if (TimeRequestUtility.isHolidayRangeHour(dto)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShortHolidayRemainHours(mospParams, holidayDto.getHolidayName(), row);
				// 処理終了
				return;
			}
			// エラーメッセージを設定
			TimeMessageUtility.addErrorShortHolidayRemainDays(mospParams, holidayDto.getHolidayName(), row);
			// 処理終了
			return;
		}
		// 休暇の期限が切れている場合
		if (requestEndDate.after(remain.getHolidayLimitDate())) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorHolidayExpiredLimit(mospParams, row);
			// 処理終了
			return;
		}
		// 休暇種別情報の連続取得が必須である場合
		if (TimeUtility.isForcedConsecutive(holidayDto)) {
			// 残日数を全て利用
			dto.setUseDay(remain.getRemainDays());
		}
		// 休暇取得日を設定
		dto.setHolidayAcquisitionDate(remain.getAcquisitionDate());
	}
	
	/**
	 * 休暇申請情報(特別休暇・その他休暇・欠勤)から使用日数を取得する。<br>
	 * @param dto 休暇申請情報
	 * @return 使用日数(特別休暇・その他休暇・欠勤)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected double getUseDaysForHoliday(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請情報から個人IDを取得
		String personalId = dto.getPersonalId();
		// 時間休である場合
		if (TimeRequestUtility.isHolidayRangeHour(dto)) {
			// 0を取得
			return 0D;
		}
		// 半休である場合
		if (TimeRequestUtility.isHolidayRangeHalf(dto)) {
			// 0.5を取得
			return TimeUtility.getHolidayTimes(dto.getHolidayRange());
		}
		// 連続休暇の休暇対象日リストを取得
		List<Date> dates = getConsecutiveHolidayDates(personalId, dto.getRequestStartDate(), dto.getRequestEndDate());
		// 使用日数を取得
		return MospUtility.getDouble(dates.size());
	}
	
	/**
	 * 休暇申請情報を下書する。<br>
	 * @param dto         休暇申請情報
	 * @param approverIds 承認者個人ID配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void draft(HolidayRequestDtoInterface dto, String[] approverIds) throws MospException {
		// 申請の相関チェック
		regist.checkDraft(dto);
		// ワークフロー情報を取得
		WorkflowDtoInterface workflow = workflowRegist.getInitDto(dto.getWorkflow(), TimeConst.CODE_FUNCTION_VACATION);
		// 承認者個人IDを設定
		workflowRegist.setDtoApproverIds(workflow, approverIds);
		// 休暇申請情報から個人IDと申請開始日を取得
		String personalID = dto.getPersonalId();
		Date targetDate = dto.getRequestStartDate();
		// 申請(ワークフロー情報を登録)
		workflow = workflowRegist.draft(workflow, personalID, targetDate, PlatformConst.WORKFLOW_TYPE_TIME);
		// ワークフロー情報を登録できなかった場合
		if (MospUtility.isEmpty(workflow)) {
			// 処理終了
			return;
		}
		// 休暇申請情報にワークフロー番号を設定
		dto.setWorkflow(workflow.getWorkflow());
		// ログインユーザの個人IDを取得
		String loginPersonalId = MospUtility.getLoginPersonalId(mospParams);
		// ワークフローコメントを準備
		String workflowComment = PfMessageUtility.getDraftSucceed(mospParams);
		// ワークフローコメントを登録
		workflowCommentRegist.addComment(workflow, loginPersonalId, workflowComment);
		// 休暇申請情報を登録
		regist.regist(dto);
	}
	
	/**
	 * 休暇申請情報を申請する。<br>
	 * 承認者個人ID配列が空である(一括申請である)場合、
	 * 承認者個人IDの設定は行わない(既にワークフロー情報に設定されているものを使う)。<br>
	 * @param dto         休暇申請情報
	 * @param approverIds 承認者個人ID配列
	 * @param row         行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void apply(HolidayRequestDtoInterface dto, String[] approverIds, Integer row) throws MospException {
		// 申請の相関チェック
		regist.checkAppli(dto, row);
		// ワークフロー情報を取得
		WorkflowDtoInterface workflow = workflowRegist.getInitDto(dto.getWorkflow(), TimeConst.CODE_FUNCTION_VACATION);
		// 承認者個人ID配列が空でない(一括申請でない)場合
		if (MospUtility.isEmpty(approverIds) == false) {
			// 承認者個人IDを設定
			workflowRegist.setDtoApproverIds(workflow, approverIds);
		}
		// 休暇申請情報から個人IDと申請開始日を取得
		String personalID = dto.getPersonalId();
		Date targetDate = dto.getRequestStartDate();
		// フロー区分を準備
		int workflowType = PlatformConst.WORKFLOW_TYPE_TIME;
		// 申請(ワークフロー情報を登録)
		workflow = workflowRegist.appli(workflow, personalID, targetDate, workflowType, MospConst.STR_EMPTY);
		// ワークフロー情報を登録できなかった場合
		if (MospUtility.isEmpty(workflow)) {
			// 処理終了
			return;
		}
		// 休暇申請情報にワークフロー番号を設定
		dto.setWorkflow(workflow.getWorkflow());
		// 休暇申請情報を登録
		regist.regist(dto, row);
		// 勤怠データを削除
		regist.deleteAttendance(dto);
		// 勤怠データを下書
		regist.draftAttendance(dto);
		// 勤怠トランザクションを登録
		attendanceTransactionRegist.regist(dto);
	}
	
	/**
	 * 休暇申請情報を申請(一括申請用)する。<br>
	 * 承認者個人IDの設定は行わない(既にワークフロー情報に設定されているものを使う)。<br>
	 * @param dto         休暇申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void applyForBatchUpdate(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請情報を申請
		apply(dto, new String[]{ MospConst.STR_EMPTY }, null);
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
		// 勤怠関連マスタ参照処理をBeanに設定
		regist.setTimeMaster(timeMaster);
		scheduleUtil.setTimeMaster(timeMaster);
		paidHolidayRemain.setTimeMaster(timeMaster);
		holidayInfo.setTimeMaster(timeMaster);
		attendanceTransactionRegist.setTimeMaster(timeMaster);
	}
	
}
