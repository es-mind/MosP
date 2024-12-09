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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceTransactionDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtAttendanceDto;

/**
 * 勤怠トランザクション登録クラス。
 */
public class AttendanceTransactionRegistBean extends TimeBean implements AttendanceTransactionRegistBeanInterface {
	
	/**
	 * 勤怠トランザクションDAOクラス。
	 */
	protected AttendanceTransactionDaoInterface	dao;
	
	/**
	 * 人事休職情報参照クラス。
	 */
	protected SuspensionReferenceBeanInterface	suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。
	 */
	protected RetirementReferenceBeanInterface	retirementReference;
	
	/**
	 * 設定適用管理参照クラス。
	 */
	protected ApplicationReferenceBeanInterface	applicationReference;
	
	/**
	 * 有給休暇設定参照クラス。
	 */
	protected PaidHolidayReferenceBeanInterface	paidHolidayReference;
	
	/**
	 * 休暇種別管理参照クラス。
	 */
	protected HolidayReferenceBeanInterface		holidayReference;
	
	/**
	 * ワークフロー統括クラス。
	 */
	protected WorkflowIntegrateBeanInterface	workflowIntegrate;
	
	/**
	 * カレンダユーティリティ
	 */
	protected ScheduleUtilBeanInterface			scheduleUtil;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface			timeMaster;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public AttendanceTransactionRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(AttendanceTransactionDaoInterface.class);
		// Beanを準備
		suspensionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		applicationReference = createBeanInstance(ApplicationReferenceBeanInterface.class);
		paidHolidayReference = createBeanInstance(PaidHolidayReferenceBeanInterface.class);
		holidayReference = createBeanInstance(HolidayReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		// 勤怠関連マスタ参照処理を設定
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		setTimeMaster(timeMaster);
	}
	
	@Override
	public void regist(String personalId, Date workDate) throws MospException {
		regist(personalId, workDate, true);
	}
	
	@Override
	public void regist(AttendanceDtoInterface dto) throws MospException {
		regist(dto.getPersonalId(), dto.getWorkDate(), dto.getWorkflow());
	}
	
	@Override
	public void regist(HolidayRequestDtoInterface dto) throws MospException {
		for (Date date = dto.getRequestStartDate(); !date.after(dto.getRequestEndDate()); date = addDay(date, 1)) {
			regist(dto.getPersonalId(), date, dto.getWorkflow());
		}
	}
	
	@Override
	public void regist(SubHolidayRequestDtoInterface dto) throws MospException {
		regist(dto.getPersonalId(), dto.getRequestDate(), dto.getWorkflow());
	}
	
	@Override
	public void regist(SubstituteDtoInterface dto) throws MospException {
		regist(dto.getPersonalId(), dto.getSubstituteDate(), dto.getWorkflow());
	}
	
	/**
	 * 登録を行う。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param workflow ワークフロー番号
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(String personalId, Date workDate, long workflow) throws MospException {
		if (workflowIntegrate.isCompleted(workflow)) {
			// 承認済の場合
			regist(personalId, workDate);
		}
	}
	
	/**
	 * 登録を行う。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param isUpdate 更新する場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(String personalId, Date workDate, boolean isUpdate) throws MospException {
		AttendanceTransactionDtoInterface dto = dao.findForKey(personalId, workDate);
		if (dto == null) {
			dto = new TmtAttendanceDto();
			setDtoFields(dto, personalId, workDate);
			// 新規登録
			insert(dto);
			return;
		}
		if (isUpdate) {
			setDtoFields(dto);
			// 履歴更新
			update(dto);
		}
	}
	
	/**
	 * 値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceTransactionDtoInterface dto, String personalId, Date workDate)
			throws MospException {
		dto.setPersonalId(personalId);
		dto.setWorkDate(workDate);
		setDtoFields(dto);
	}
	
	/**
	 * 値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(AttendanceTransactionDtoInterface dto) throws MospException {
		// 初期値設定
		dto.setAttendanceType("");
		dto.setNumerator(0);
		dto.setDenominator(0);
		// 休職である場合
		if (setDtoFieldsIsSuspended(dto)) {
			return;
		}
		// 退職である場合
		if (setDtoFieldsIsRetired(dto)) {
			return;
		}
		// 休職・退職でない場合
		// 申請ユーティリティ
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(dto.getPersonalId(), dto.getWorkDate());
		// 勤怠申請済み
		if (setDtoFieldsIsAttendance(dto, requestUtil)) {
			return;
		}
		// 振替休日
		if (setDtoFieldsIsSubstitute(dto, requestUtil)) {
			return;
		}
		// 代休
		if (setDtoFieldsIsSubHoliday(dto, requestUtil)) {
			return;
		}
		// カレンダ勤務形態コードを取得(申請状況を加味)
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), dto.getWorkDate(), true);
		// 休暇申請
		if (setDtoFieldsIsHoliday(dto, requestUtil, workTypeCode)) {
			return;
		}
		// カレンダ
		if (workTypeCode.isEmpty() || TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 勤務形態が未設定・法定休日・所定休日である場合
			dto.setAttendanceType(workTypeCode);
			dto.setNumerator(0);
			dto.setDenominator(0);
			return;
		}
		// 勤務形態が設定されている場合
		dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
		dto.setNumerator(0);
		dto.setDenominator(1);
	}
	
	/***
	 * DTO設定(休職時)
	 * @param dto 対象DTO
	 * @return 後続処理不要判定(true:不要、false:必要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean setDtoFieldsIsSuspended(AttendanceTransactionDtoInterface dto) throws MospException {
		// 休職である場合
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getWorkDate())) {
			// 休職である場合
			dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_SUSPENSION);
			dto.setNumerator(0);
			dto.setDenominator(0);
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * DTO設定(退職時)
	 * @param dto 対象DTO
	 * @return 後続処理不要判定(true:不要、false:必要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean setDtoFieldsIsRetired(AttendanceTransactionDtoInterface dto) throws MospException {
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getWorkDate())) {
			// 退職である場合
			dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_RETIREMENT);
			dto.setNumerator(0);
			dto.setDenominator(0);
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * DTO設定(勤怠申請)
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @return 後続処理不要判定(true:不要、false:必要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean setDtoFieldsIsAttendance(AttendanceTransactionDtoInterface dto,
			RequestUtilBeanInterface requestUtil) throws MospException {
		AttendanceDtoInterface attendanceDto = requestUtil.getApplicatedAttendance();
		
		if (attendanceDto != null && workflowIntegrate.isCompleted(attendanceDto.getWorkflow())) {
			// 勤怠申請が承認済の場合
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
			if (workOnHolidayRequestDto == null) {
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
				dto.setNumerator(1);
				dto.setDenominator(1);
				return true;
			}
			// 振出・休出申請が承認済の場合
			int substitute = workOnHolidayRequestDto.getSubstitute();
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
				// 振替出勤(全日)の場合
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
				dto.setNumerator(1);
				dto.setDenominator(1);
				return true;
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休日出勤の場合
				String attendanceType = "";
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
					// 法定休日出勤の場合
					attendanceType = TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
				} else if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY
					.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
					// 所定休日出勤の場合
					attendanceType = TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
				}
				if (!attendanceType.isEmpty()) {
					ApplicationDtoInterface applicationDto = applicationReference.findForPerson(dto.getPersonalId(),
							dto.getWorkDate());
					if (applicationDto != null) {
						PaidHolidayDtoInterface paidHolidayDto = paidHolidayReference
							.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), dto.getWorkDate());
						if (paidHolidayDto != null) {
							int workOnHolidayCalc = paidHolidayDto.getWorkOnHolidayCalc();
							if (workOnHolidayCalc == 1) {
								// 出勤扱いの場合
								dto.setAttendanceType(attendanceType);
								dto.setNumerator(1);
								dto.setDenominator(1);
								return true;
							} else if (workOnHolidayCalc == 2) {
								// 欠勤扱いの場合
								dto.setAttendanceType(attendanceType);
								dto.setNumerator(0);
								dto.setDenominator(1);
								return true;
							} else if (workOnHolidayCalc == 3) {
								// 計算対象外の場合
								dto.setAttendanceType(attendanceType);
								dto.setNumerator(0);
								dto.setDenominator(0);
								return true;
							}
						}
					}
				}
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				// 振替出勤(半日)の場合
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
				dto.setNumerator(1);
				dto.setDenominator(1);
				return true;
			}
			dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_ATTENDANCE);
			dto.setNumerator(1);
			dto.setDenominator(1);
			return true;
		}
		return false;
		
	}
	
	/**
	 * DTO設定(振替休日時)
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @return 後続処理不要判定(true:不要、false:必要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean setDtoFieldsIsSubstitute(AttendanceTransactionDtoInterface dto,
			RequestUtilBeanInterface requestUtil) throws MospException {
		int substituteRange = requestUtil.checkHolidayRangeSubstitute(requestUtil.getSubstituteList(true));
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 振替休日が全休又は午前休 + 午後休の場合
			dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_SUBSTITUTE);
			dto.setNumerator(0);
			dto.setDenominator(0);
			return true;
		}
		
		return false;
	}
	
	/**
	 * DTO設定(代休時)
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @return 後続処理不要判定(true:不要、false:必要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean setDtoFieldsIsSubHoliday(AttendanceTransactionDtoInterface dto,
			RequestUtilBeanInterface requestUtil) throws MospException {
		int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(true));
		if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM
				|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 代休の場合
			dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_SUB_HOLIDAY);
			dto.setNumerator(1);
			dto.setDenominator(1);
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * DTO設定(休暇申請時)
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @param workTypeCode 勤務形態コード
	 * @return 後続処理不要判定(true:不要、false:必要)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean setDtoFieldsIsHoliday(AttendanceTransactionDtoInterface dto, RequestUtilBeanInterface requestUtil,
			String workTypeCode) throws MospException {
		Integer amHoliday = null;
		Integer pmHoliday = null;
		List<HolidayRequestDtoInterface> holidayList = requestUtil.getHolidayList(true);
		// 休暇申請リスト毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayList) {
			// 申請休暇範囲取得
			boolean isAll = holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL;
			boolean isAm = holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM;
			boolean isPm = holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM;
			if (holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
				if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(holidayRequestDto.getHolidayType2())) {
					// 有給休暇の場合
					dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
					dto.setNumerator(1);
					dto.setDenominator(1);
					return true;
				} else if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK)
					.equals(holidayRequestDto.getHolidayType2())) {
					// ストック休暇の場合
					// 出勤率扱い取得
					int stockHolidayAttendance = mospParams.getApplicationProperty(
							TimeConst.APP_STOCK_HOLIDAY_ATTENDANCE, TimeConst.CODE_PAID_HOLIDAY_CALC_PRESENCE);
					// 出勤扱いの場合
					if (stockHolidayAttendance == TimeConst.CODE_PAID_HOLIDAY_CALC_PRESENCE) {
						dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
						dto.setNumerator(1);
						dto.setDenominator(1);
						return true;
					}
					// 全休の場合
					if (isAll) {
						dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
						// 欠勤扱いの場合
						if (stockHolidayAttendance == TimeConst.CODE_PAID_HOLIDAY_CALC_ABSENCE) {
							dto.setNumerator(0);
							dto.setDenominator(1);
							return true;
						}
						// 対象外の場合
						if (stockHolidayAttendance == TimeConst.CODE_PAID_HOLIDAY_CALC_NONE) {
							dto.setNumerator(0);
							dto.setDenominator(0);
							return true;
						}
					} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
						// 午前休の場合
						amHoliday = stockHolidayAttendance;
					} else if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
						// 午後休の場合
						pmHoliday = stockHolidayAttendance;
					}
				}
			} else if (holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_SPECIAL
					|| holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_OTHER
					|| holidayRequestDto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
				// 特別休暇・その他休暇・欠勤の場合
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
						|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
					// 勤務形態が未設定・法定休日・所定休日である場合
					dto.setAttendanceType(workTypeCode);
					dto.setNumerator(0);
					dto.setDenominator(0);
					return true;
					
				}
				// 勤務形態が未設定・法定休日・所定休日でない場合
				HolidayDtoInterface holidayDto = holidayReference.getHolidayInfo(holidayRequestDto.getHolidayType2(),
						holidayRequestDto.getRequestStartDate(), holidayRequestDto.getHolidayType1());
				if (holidayDto == null) {
					continue;
				}
				if (holidayDto.getPaidHolidayCalc() == TimeConst.CODE_PAID_HOLIDAY_CALC_PRESENCE) {
					// 出勤扱いの場合
					dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
					dto.setNumerator(1);
					dto.setDenominator(1);
					return true;
					
				} else if (holidayDto.getPaidHolidayCalc() == TimeConst.CODE_PAID_HOLIDAY_CALC_ABSENCE) {
					// 欠勤扱いの場合
					if (isAll) {
						// 全休の場合
						dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
						dto.setNumerator(0);
						dto.setDenominator(1);
						return true;
						
					}
					if (isAm) {
						// 午前休の場合
						amHoliday = TimeConst.CODE_PAID_HOLIDAY_CALC_ABSENCE;
					}
					if (isPm) {
						// 午後休の場合
						pmHoliday = TimeConst.CODE_PAID_HOLIDAY_CALC_ABSENCE;
					}
				} else if (holidayDto.getPaidHolidayCalc() == TimeConst.CODE_PAID_HOLIDAY_CALC_NONE) {
					// 計算対象外の場合
					if (isAll) {
						// 全休の場合
						dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
						dto.setNumerator(0);
						dto.setDenominator(0);
						return true;
					}
					if (isAm && amHoliday == null) {
						// 午前休の場合
						amHoliday = TimeConst.CODE_PAID_HOLIDAY_CALC_NONE;
					}
					if (isPm && pmHoliday == null) {
						// 午後休の場合
						pmHoliday = TimeConst.CODE_PAID_HOLIDAY_CALC_NONE;
					}
				}
			}
		}
		if (requestUtil.isHolidayAllDay(true)) {
			// 全休の場合
			if ((amHoliday != null && amHoliday.intValue() == TimeConst.CODE_PAID_HOLIDAY_CALC_ABSENCE)
					|| (pmHoliday != null && pmHoliday.intValue() == TimeConst.CODE_PAID_HOLIDAY_CALC_ABSENCE)) {
				// 欠勤扱いの場合
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
				dto.setNumerator(0);
				dto.setDenominator(1);
				return true;
			}
			if ((amHoliday != null && amHoliday.intValue() == TimeConst.CODE_PAID_HOLIDAY_CALC_NONE)
					|| (pmHoliday != null && pmHoliday.intValue() == TimeConst.CODE_PAID_HOLIDAY_CALC_NONE)) {
				// 計算対象外の場合
				dto.setAttendanceType(TimeConst.CODE_ATTENDANCE_TYPE_HOLIDAY);
				dto.setNumerator(0);
				dto.setDenominator(0);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void regist(String personalId, Map<Date, String> attendanceMap) throws MospException {
		//  勤怠トランザクション登録判定情報毎に処理
		for (Entry<Date, String> entry : attendanceMap.entrySet()) {
			// 出勤日を取得
			Date workDate = entry.getKey();
			// 出勤区分を取得
			String attendanceType = entry.getValue();
			// DTOを作成
			AttendanceTransactionDtoInterface dto = makeAttendanceTransactionDto(personalId, workDate, attendanceType);
			// DBから勤怠トランザクションを取得
			AttendanceTransactionDtoInterface formerDto = dao.findForKey(personalId, workDate);
			// 勤怠トランザクションが存在する場合
			if (formerDto != null) {
				// 勤怠トランザクション情報が同じ値を持つ場合
				if (isSameDto(dto, formerDto)) {
					// 更新不要(次の勤怠トランザクション登録判定情報へ)
					continue;
				}
				// 論理削除
				logicalDelete(dao, formerDto.getTmtAttendanceId());
			}
			// 登録
			insert(dto);
		}
	}
	
	/**
	 * 勤怠トランザクション情報を作成する。<br>
	 * @param personalId     個人ID
	 * @param workDate       勤務日
	 * @param attendanceType 出勤区分
	 * @return 勤怠トランザクション情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected AttendanceTransactionDtoInterface makeAttendanceTransactionDto(String personalId, Date workDate,
			String attendanceType) throws MospException {
		// 勤怠トランザクション情報を準備
		AttendanceTransactionDtoInterface dto = new TmtAttendanceDto();
		dto.setPersonalId(personalId);
		dto.setWorkDate(workDate);
		dto.setAttendanceType(attendanceType);
		dto.setNumerator(0);
		dto.setDenominator(0);
		// 勤怠トランザクション情報作成追加処理を実行
		doStoredLogic(TimeConst.CODE_KEY_ADD_MAKE_ATTENDANCE_TRANSACTION_DTO, dto);
		// 勤怠トランザクション情報を取得
		return dto;
	}
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void insert(AttendanceTransactionDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmtAttendanceId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update(AttendanceTransactionDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmtAttendanceId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmtAttendanceId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(AttendanceTransactionDtoInterface dto) {
		// 処理なし
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkInsert(AttendanceTransactionDtoInterface dto) throws MospException {
		// 重複確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getWorkDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkUpdate(AttendanceTransactionDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmtAttendanceId());
	}
	
	/**
	 * 勤怠トランザクション情報が同じ値を持つかを確認する。<br>
	 * @param dto1 DTO1
	 * @param dto2 DTO2
	 * @return 確認結果(true：同じ値を持つ、false：同じ値を持たない)
	 */
	protected static boolean isSameDto(AttendanceTransactionDtoInterface dto1, AttendanceTransactionDtoInterface dto2) {
		return MospUtility.isEqual(dto1.getPersonalId(), dto2.getPersonalId())
				&& MospUtility.isEqual(dto1.getWorkDate(), dto2.getWorkDate())
				&& MospUtility.isEqual(dto1.getAttendanceType(), dto2.getAttendanceType())
				&& dto1.getNumerator() == dto2.getNumerator() && dto1.getDenominator() == dto2.getDenominator();
		
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
		scheduleUtil.setTimeMaster(timeMaster);
	}
	
}
