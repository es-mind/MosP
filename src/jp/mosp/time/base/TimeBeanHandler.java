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

import jp.mosp.framework.base.BaseBeanHandler;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.time.bean.AfterApplyAttendancesExecuteBeanInterface;
import jp.mosp.time.bean.AllowanceRegistBeanInterface;
import jp.mosp.time.bean.ApplicationRegistBeanInterface;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionRegistBeanInterface;
import jp.mosp.time.bean.AttendanceListRegistBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffRegistBeanInterface;
import jp.mosp.time.bean.DifferenceRequestRegistBeanInterface;
import jp.mosp.time.bean.GoOutRegistBeanInterface;
import jp.mosp.time.bean.HolidayDataRegistBeanInterface;
import jp.mosp.time.bean.HolidayRegistBeanInterface;
import jp.mosp.time.bean.HolidayRequestExecuteBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.LimitStandardRegistBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayEntranceDateRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayProportionallyRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionRegistBeanInterface;
import jp.mosp.time.bean.RestRegistBeanInterface;
import jp.mosp.time.bean.ScheduleDateRegistBeanInterface;
import jp.mosp.time.bean.ScheduleRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayTransactionRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.TimeRecordBeanInterface;
import jp.mosp.time.bean.TimeSettingRegistBeanInterface;
import jp.mosp.time.bean.TotalAbsenceRegistBeanInterface;
import jp.mosp.time.bean.TotalAllowanceRegistBeanInterface;
import jp.mosp.time.bean.TotalLeaveRegistBeanInterface;
import jp.mosp.time.bean.TotalOtherVacationRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionRegistBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeItemRegistBeanInterface;
import jp.mosp.time.bean.WorkTypePatternItemRegistBeanInterface;
import jp.mosp.time.bean.WorkTypePatternRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeRegistBeanInterface;
import jp.mosp.time.bean.impl.HolidayRequestImportBean;
import jp.mosp.time.bean.impl.WorkOnHolidayRequestImportBean;

/**
 * MosPプラットフォーム用BeanHandler。<br>
 */
public class TimeBeanHandler extends BaseBeanHandler implements TimeBeanHandlerInterface {
	
	/**
	 * コンストラクタ。
	 */
	public TimeBeanHandler() {
		super();
	}
	
	@Override
	public AttendanceRegistBeanInterface attendanceRegist() throws MospException {
		return createBeanInstance(AttendanceRegistBeanInterface.class);
	}
	
	@Override
	public AttendanceCorrectionRegistBeanInterface attendanceCorrectionRegist() throws MospException {
		return createBeanInstance(AttendanceCorrectionRegistBeanInterface.class);
	}
	
	@Override
	public RestRegistBeanInterface restRegist() throws MospException {
		return createBeanInstance(RestRegistBeanInterface.class);
	}
	
	@Override
	public GoOutRegistBeanInterface goOutRegist() throws MospException {
		return createBeanInstance(GoOutRegistBeanInterface.class);
	}
	
	@Override
	public AllowanceRegistBeanInterface allowanceRegist() throws MospException {
		return createBeanInstance(AllowanceRegistBeanInterface.class);
	}
	
	@Override
	public AttendanceTransactionRegistBeanInterface attendanceTransactionRegist() throws MospException {
		return createBeanInstance(AttendanceTransactionRegistBeanInterface.class);
	}
	
	@Override
	public OvertimeRequestRegistBeanInterface overtimeRequestRegist() throws MospException {
		return createBeanInstance(OvertimeRequestRegistBeanInterface.class);
	}
	
	@Override
	public HolidayRequestRegistBeanInterface holidayRequestRegist() throws MospException {
		return createBeanInstance(HolidayRequestRegistBeanInterface.class);
	}
	
	@Override
	public HolidayRequestExecuteBeanInterface holidayRequestExecute() throws MospException {
		return createBeanInstance(HolidayRequestExecuteBeanInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestRegistBeanInterface workOnHolidayRequestRegist() throws MospException {
		return createBeanInstance(WorkOnHolidayRequestRegistBeanInterface.class);
	}
	
	@Override
	public SubHolidayRequestRegistBeanInterface subHolidayRequestRegist() throws MospException {
		return createBeanInstance(SubHolidayRequestRegistBeanInterface.class);
	}
	
	@Override
	public SubHolidayRegistBeanInterface subHolidayRegist() throws MospException {
		return createBeanInstance(SubHolidayRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypeChangeRequestRegistBeanInterface workTypeChangeRequestRegist() throws MospException {
		return createBeanInstance(WorkTypeChangeRequestRegistBeanInterface.class);
	}
	
	@Override
	public DifferenceRequestRegistBeanInterface differenceRequestRegist() throws MospException {
		return createBeanInstance(DifferenceRequestRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeTransactionRegistBeanInterface totalTimeTransactionRegist() throws MospException {
		return createBeanInstance(TotalTimeTransactionRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeEmployeeTransactionRegistBeanInterface totalTimeEmployeeTransactionRegist() throws MospException {
		return createBeanInstance(TotalTimeEmployeeTransactionRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeRegistBeanInterface totalTimeRegist() throws MospException {
		return createBeanInstance(TotalTimeRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeCorrectionRegistBeanInterface totalTimeCorrectionRegist() throws MospException {
		return createBeanInstance(TotalTimeCorrectionRegistBeanInterface.class);
	}
	
	@Override
	public TotalLeaveRegistBeanInterface totalLeaveRegist() throws MospException {
		return createBeanInstance(TotalLeaveRegistBeanInterface.class);
	}
	
	@Override
	public TotalOtherVacationRegistBeanInterface totalOtherVacationRegist() throws MospException {
		return createBeanInstance(TotalOtherVacationRegistBeanInterface.class);
	}
	
	@Override
	public TotalAbsenceRegistBeanInterface totalAbsenceRegist() throws MospException {
		return createBeanInstance(TotalAbsenceRegistBeanInterface.class);
	}
	
	@Override
	public TotalAllowanceRegistBeanInterface totalAllowanceRegist() throws MospException {
		return createBeanInstance(TotalAllowanceRegistBeanInterface.class);
	}
	
	@Override
	public HolidayRegistBeanInterface holidayRegist() throws MospException {
		return createBeanInstance(HolidayRegistBeanInterface.class);
	}
	
	@Override
	public HolidayDataRegistBeanInterface holidayDataRegist() throws MospException {
		return createBeanInstance(HolidayDataRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataRegistBeanInterface paidHolidayDataRegist() throws MospException {
		return createBeanInstance(PaidHolidayDataRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayTransactionRegistBeanInterface paidHolidayTransactionRegist() throws MospException {
		return createBeanInstance(PaidHolidayTransactionRegistBeanInterface.class);
	}
	
	@Override
	public StockHolidayDataRegistBeanInterface stockHolidayDataRegist() throws MospException {
		return createBeanInstance(StockHolidayDataRegistBeanInterface.class);
	}
	
	@Override
	public StockHolidayTransactionRegistBeanInterface stockHolidayTransactionRegist() throws MospException {
		return createBeanInstance(StockHolidayTransactionRegistBeanInterface.class);
	}
	
	@Override
	public TimeSettingRegistBeanInterface timeSettingRegist() throws MospException {
		return createBeanInstance(TimeSettingRegistBeanInterface.class);
	}
	
	@Override
	public LimitStandardRegistBeanInterface limitStandardRegist() throws MospException {
		return createBeanInstance(LimitStandardRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypeRegistBeanInterface workTypeRegist() throws MospException {
		return createBeanInstance(WorkTypeRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypeItemRegistBeanInterface workTypeItemRegist() throws MospException {
		return createBeanInstance(WorkTypeItemRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternRegistBeanInterface workTypePatternRegist() throws MospException {
		return createBeanInstance(WorkTypePatternRegistBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternItemRegistBeanInterface workTypePatternItemRegist() throws MospException {
		return createBeanInstance(WorkTypePatternItemRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayRegistBeanInterface paidHolidayRegist() throws MospException {
		return createBeanInstance(PaidHolidayRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayProportionallyRegistBeanInterface paidHolidayProportionallyRegist() throws MospException {
		return createBeanInstance(PaidHolidayProportionallyRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayFirstYearRegistBeanInterface paidHolidayFirstYearRegist() throws MospException {
		return createBeanInstance(PaidHolidayFirstYearRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayPointDateRegistBeanInterface paidHolidayPointDateRegist() throws MospException {
		return createBeanInstance(PaidHolidayPointDateRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayEntranceDateRegistBeanInterface paidHolidayEntranceDateRegist() throws MospException {
		return createBeanInstance(PaidHolidayEntranceDateRegistBeanInterface.class);
	}
	
	@Override
	public StockHolidayRegistBeanInterface stockHolidayRegist() throws MospException {
		return createBeanInstance(StockHolidayRegistBeanInterface.class);
	}
	
	@Override
	public ScheduleRegistBeanInterface scheduleRegist() throws MospException {
		return createBeanInstance(ScheduleRegistBeanInterface.class);
	}
	
	@Override
	public ScheduleDateRegistBeanInterface scheduleDateRegist() throws MospException {
		return createBeanInstance(ScheduleDateRegistBeanInterface.class);
	}
	
	@Override
	public CutoffRegistBeanInterface cutoffRegist() throws MospException {
		return createBeanInstance(CutoffRegistBeanInterface.class);
	}
	
	@Override
	public ApplicationRegistBeanInterface applicationRegist() throws MospException {
		return createBeanInstance(ApplicationRegistBeanInterface.class);
	}
	
	@Override
	public TotalTimeCalcBeanInterface totalTimeCalc() throws MospException {
		return createBeanInstance(TotalTimeCalcBeanInterface.class);
	}
	
	@Override
	public SubstituteRegistBeanInterface substituteRegist() throws MospException {
		return createBeanInstance(SubstituteRegistBeanInterface.class);
	}
	
	@Override
	public AttendanceListRegistBeanInterface attendanceListRegist() throws MospException {
		return createBeanInstance(AttendanceListRegistBeanInterface.class);
	}
	
	@Override
	public AttendanceListRegistBeanInterface attendanceListRegist(Date targetDate) throws MospException {
		return (AttendanceListRegistBeanInterface)createBean(AttendanceListRegistBeanInterface.class, targetDate);
	}
	
	@Override
	public AttendanceCalcBeanInterface attendanceCalc() throws MospException {
		return createBeanInstance(AttendanceCalcBeanInterface.class);
	}
	
	@Override
	public AttendanceCalcBeanInterface attendanceCalc(Date targetDate) throws MospException {
		return (AttendanceCalcBeanInterface)createBean(AttendanceCalcBeanInterface.class, targetDate);
	}
	
	@Override
	public TimeApprovalBeanInterface timeApproval() throws MospException {
		return createBeanInstance(TimeApprovalBeanInterface.class);
	}
	
	@Override
	public PaidHolidayGrantRegistBeanInterface paidHolidayGrantRegist() throws MospException {
		return createBeanInstance(PaidHolidayGrantRegistBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataGrantBeanInterface paidHolidayDataGrant() throws MospException {
		return createBeanInstance(PaidHolidayDataGrantBeanInterface.class);
	}
	
	@Override
	public StockHolidayDataGrantBeanInterface stockHolidayDataGrant() throws MospException {
		return createBeanInstance(StockHolidayDataGrantBeanInterface.class);
	}
	
	@Override
	public ImportBeanInterface holidayRequestImport() throws MospException {
		return createBeanInstance(HolidayRequestImportBean.class);
	}
	
	@Override
	public ImportBeanInterface workOnHolidayRequestImport() throws MospException {
		return createBeanInstance(WorkOnHolidayRequestImportBean.class);
	}
	
	@Override
	public AfterApplyAttendancesExecuteBeanInterface afterApplyAttendancesExecute() throws MospException {
		return createBeanInstance(AfterApplyAttendancesExecuteBeanInterface.class);
	}
	
	@Override
	public TimeRecordBeanInterface timeRecord() throws MospException {
		return createBeanInstance(TimeRecordBeanInterface.class);
	}
	
}
