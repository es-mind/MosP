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
package jp.mosp.time.base;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanHandler;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.bean.AllowanceReferenceBeanInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ApplicationReferenceSearchBeanInterface;
import jp.mosp.time.bean.ApplicationSearchBeanInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTotalInfoBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.CutoffSearchBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestSearchBeanInterface;
import jp.mosp.time.bean.ExportTableReferenceBeanInterface;
import jp.mosp.time.bean.GoOutReferenceBeanInterface;
import jp.mosp.time.bean.HolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.HolidayHistorySearchBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayManagementSearchBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.HolidaySearchBeanInterface;
import jp.mosp.time.bean.ImportTableReferenceBeanInterface;
import jp.mosp.time.bean.LimitStandardReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeInfoReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayEntranceDateReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayHistorySearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayManagementSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayProportionallyReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.PaidHolidaySearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayUsageExportBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.RestReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleSearchBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.StockHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.SubordinateFiscalSearchBeanInterface;
import jp.mosp.time.bean.SubordinateSearchBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingSearchBeanInterface;
import jp.mosp.time.bean.TotalAbsenceReferenceBeanInterface;
import jp.mosp.time.bean.TotalAllowanceReferenceBeanInterface;
import jp.mosp.time.bean.TotalLeaveReferenceBeanInterface;
import jp.mosp.time.bean.TotalOtherVacationReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeSearchBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionSearchBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeSearchBeanInterface;
import jp.mosp.time.report.bean.AttendanceBookBeanInterface;
import jp.mosp.time.report.bean.ScheduleBookBeanInterface;

/**
 * MosP勤怠管理参照用BeanHandlerクラス。
 */
public class TimeReferenceBeanHandler extends BaseBeanHandler implements TimeReferenceBeanHandlerInterface {
	
	/**
	 * コンストラクタ。
	 */
	public TimeReferenceBeanHandler() {
		super();
	}
	
	@Override
	public AttendanceReferenceBeanInterface attendance() throws MospException {
		return createBeanInstance(AttendanceReferenceBeanInterface.class);
	}
	
	@Override
	public AttendanceCorrectionReferenceBeanInterface attendanceCorrection() throws MospException {
		return createBeanInstance(AttendanceCorrectionReferenceBeanInterface.class);
	}
	
	@Override
	public RestReferenceBeanInterface rest() throws MospException {
		return createBeanInstance(RestReferenceBeanInterface.class);
	}
	
	@Override
	public GoOutReferenceBeanInterface goOut() throws MospException {
		return createBeanInstance(GoOutReferenceBeanInterface.class);
	}
	
	@Override
	public AllowanceReferenceBeanInterface allowance() throws MospException {
		return createBeanInstance(AllowanceReferenceBeanInterface.class);
	}
	
	@Override
	public OvertimeRequestReferenceBeanInterface overtimeRequest() throws MospException {
		return createBeanInstance(OvertimeRequestReferenceBeanInterface.class);
	}
	
	@Override
	public OvertimeRequestSearchBeanInterface overtimeRequestSearch() throws MospException {
		return createBeanInstance(OvertimeRequestSearchBeanInterface.class);
	}
	
	@Override
	public HolidayRequestReferenceBeanInterface holidayRequest() throws MospException {
		return createBeanInstance(HolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayRequestSearchBeanInterface holidayRequestSearch() throws MospException {
		return createBeanInstance(HolidayRequestSearchBeanInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestReferenceBeanInterface workOnHolidayRequest() throws MospException {
		return createBeanInstance(WorkOnHolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestSearchBeanInterface workOnHolidayRequestSearch() throws MospException {
		return createBeanInstance(WorkOnHolidayRequestSearchBeanInterface.class);
	}
	
	@Override
	public SubHolidayReferenceBeanInterface subHoliday() throws MospException {
		return createBeanInstance(SubHolidayReferenceBeanInterface.class);
	}
	
	@Override
	public SubHolidayRequestReferenceBeanInterface subHolidayRequest() throws MospException {
		return createBeanInstance(SubHolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public SubHolidayRequestSearchBeanInterface subHolidayRequestSearch() throws MospException {
		return createBeanInstance(SubHolidayRequestSearchBeanInterface.class);
	}
	
	@Override
	public WorkTypeChangeRequestReferenceBeanInterface workTypeChangeRequest() throws MospException {
		return createBeanInstance(WorkTypeChangeRequestReferenceBeanInterface.class);
	}
	
	@Override
	public WorkTypeChangeRequestSearchBeanInterface workTypeChangeRequestSearch() throws MospException {
		return createBeanInstance(WorkTypeChangeRequestSearchBeanInterface.class);
	}
	
	@Override
	public DifferenceRequestReferenceBeanInterface differenceRequest() throws MospException {
		return createBeanInstance(DifferenceRequestReferenceBeanInterface.class);
	}
	
	@Override
	public DifferenceRequestReferenceBeanInterface differenceRequest(Date targetDate) throws MospException {
		return (DifferenceRequestReferenceBeanInterface)createBean(DifferenceRequestReferenceBeanInterface.class,
				targetDate);
	}
	
	@Override
	public DifferenceRequestSearchBeanInterface differenceRequestSearch() throws MospException {
		return createBeanInstance(DifferenceRequestSearchBeanInterface.class);
	}
	
	@Override
	public SubordinateSearchBeanInterface subordinateSearch() throws MospException {
		return createBeanInstance(SubordinateSearchBeanInterface.class);
	}
	
	@Override
	public SubordinateFiscalSearchBeanInterface subordinateFiscalSearch() throws MospException {
		return createBeanInstance(SubordinateFiscalSearchBeanInterface.class);
	}
	
	@Override
	public TotalTimeTransactionReferenceBeanInterface totalTimeTransaction() throws MospException {
		return createBeanInstance(TotalTimeTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public TotalTimeEmployeeTransactionReferenceBeanInterface totalTimeEmployeeTransaction() throws MospException {
		return createBeanInstance(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public TotalTimeTransactionSearchBeanInterface totalTimeTransactionSearch() throws MospException {
		return createBeanInstance(TotalTimeTransactionSearchBeanInterface.class);
	}
	
	@Override
	public TotalTimeSearchBeanInterface totalTimeSearch() throws MospException {
		return createBeanInstance(TotalTimeSearchBeanInterface.class);
	}
	
	@Override
	public TotalTimeReferenceBeanInterface totalTime() throws MospException {
		return createBeanInstance(TotalTimeReferenceBeanInterface.class);
	}
	
	@Override
	public TotalTimeCorrectionReferenceBeanInterface totalTimeCorrection() throws MospException {
		return createBeanInstance(TotalTimeCorrectionReferenceBeanInterface.class);
	}
	
	@Override
	public TotalLeaveReferenceBeanInterface totalLeave() throws MospException {
		return createBeanInstance(TotalLeaveReferenceBeanInterface.class);
	}
	
	@Override
	public TotalOtherVacationReferenceBeanInterface totalOtherVacation() throws MospException {
		return createBeanInstance(TotalOtherVacationReferenceBeanInterface.class);
	}
	
	@Override
	public TotalAbsenceReferenceBeanInterface totalAbsence() throws MospException {
		return createBeanInstance(TotalAbsenceReferenceBeanInterface.class);
	}
	
	@Override
	public TotalAllowanceReferenceBeanInterface totalAllowance() throws MospException {
		return createBeanInstance(TotalAllowanceReferenceBeanInterface.class);
	}
	
	@Override
	public ImportTableReferenceBeanInterface importTable() throws MospException {
		return createBeanInstance(ImportTableReferenceBeanInterface.class);
	}
	
	@Override
	public ExportTableReferenceBeanInterface exportTable() throws MospException {
		return createBeanInstance(ExportTableReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayReferenceBeanInterface holiday() throws MospException {
		return createBeanInstance(HolidayReferenceBeanInterface.class);
	}
	
	@Override
	public HolidaySearchBeanInterface holidaySearch() throws MospException {
		return createBeanInstance(HolidaySearchBeanInterface.class);
	}
	
	@Override
	public HolidayManagementSearchBeanInterface holidayManagementSearch() throws MospException {
		return createBeanInstance(HolidayManagementSearchBeanInterface.class);
	}
	
	@Override
	public HolidayDataReferenceBeanInterface holidayData() throws MospException {
		return createBeanInstance(HolidayDataReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayHistorySearchBeanInterface holidayHistorySearch() throws MospException {
		return createBeanInstance(HolidayHistorySearchBeanInterface.class);
	}
	
	@Override
	public PaidHolidayManagementSearchBeanInterface paidHolidayManagementSearch() throws MospException {
		return createBeanInstance(PaidHolidayManagementSearchBeanInterface.class);
	}
	
	@Override
	public PaidHolidayHistorySearchBeanInterface paidHolidayHistorySearch() throws MospException {
		return createBeanInstance(PaidHolidayHistorySearchBeanInterface.class);
	}
	
	@Override
	public PaidHolidayTransactionReferenceBeanInterface paidHolidayTransaction() throws MospException {
		return createBeanInstance(PaidHolidayTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataReferenceBeanInterface paidHolidayData() throws MospException {
		return createBeanInstance(PaidHolidayDataReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataSearchBeanInterface paidHolidayDataSearch() throws MospException {
		return createBeanInstance(PaidHolidayDataSearchBeanInterface.class);
	}
	
	@Override
	public StockHolidayTransactionReferenceBeanInterface stockHolidayTransaction() throws MospException {
		return createBeanInstance(StockHolidayTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public StockHolidayDataReferenceBeanInterface stockHolidayData() throws MospException {
		return createBeanInstance(StockHolidayDataReferenceBeanInterface.class);
	}
	
	@Override
	public TimeSettingReferenceBeanInterface timeSetting() throws MospException {
		return createBeanInstance(TimeSettingReferenceBeanInterface.class);
	}
	
	@Override
	public LimitStandardReferenceBeanInterface limitStandard() throws MospException {
		return createBeanInstance(LimitStandardReferenceBeanInterface.class);
	}
	
	@Override
	public TimeSettingSearchBeanInterface timeSettingSearch() throws MospException {
		return createBeanInstance(TimeSettingSearchBeanInterface.class);
	}
	
	@Override
	public WorkTypeReferenceBeanInterface workType() throws MospException {
		return createBeanInstance(WorkTypeReferenceBeanInterface.class);
	}
	
	@Override
	public WorkTypeSearchBeanInterface workTypeSearch() throws MospException {
		return createBeanInstance(WorkTypeSearchBeanInterface.class);
	}
	
	@Override
	public WorkTypeItemReferenceBeanInterface workTypeItem() throws MospException {
		return createBeanInstance(WorkTypeItemReferenceBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternReferenceBeanInterface workTypePattern() throws MospException {
		return createBeanInstance(WorkTypePatternReferenceBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternSearchBeanInterface workTypePatternSearch() throws MospException {
		return createBeanInstance(WorkTypePatternSearchBeanInterface.class);
	}
	
	@Override
	public WorkTypePatternItemReferenceBeanInterface workTypePatternItem() throws MospException {
		return createBeanInstance(WorkTypePatternItemReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayReferenceBeanInterface paidHoliday() throws MospException {
		return createBeanInstance(PaidHolidayReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayProportionallyReferenceBeanInterface paidHolidayProportionally() throws MospException {
		return createBeanInstance(PaidHolidayProportionallyReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayFirstYearReferenceBeanInterface paidHolidayFirstYear() throws MospException {
		return createBeanInstance(PaidHolidayFirstYearReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayPointDateReferenceBeanInterface paidHolidayPointDate() throws MospException {
		return createBeanInstance(PaidHolidayPointDateReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayEntranceDateReferenceBeanInterface paidHolidayEntranceDate() throws MospException {
		return createBeanInstance(PaidHolidayEntranceDateReferenceBeanInterface.class);
	}
	
	@Override
	public StockHolidayReferenceBeanInterface stockHoliday() throws MospException {
		return createBeanInstance(StockHolidayReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidaySearchBeanInterface paidHolidaySearch() throws MospException {
		return createBeanInstance(PaidHolidaySearchBeanInterface.class);
	}
	
	@Override
	public ScheduleReferenceBeanInterface schedule() throws MospException {
		return createBeanInstance(ScheduleReferenceBeanInterface.class);
	}
	
	@Override
	public ScheduleDateReferenceBeanInterface scheduleDate() throws MospException {
		return createBeanInstance(ScheduleDateReferenceBeanInterface.class);
	}
	
	@Override
	public ScheduleSearchBeanInterface scheduleSearch() throws MospException {
		return createBeanInstance(ScheduleSearchBeanInterface.class);
	}
	
	@Override
	public CutoffReferenceBeanInterface cutoff() throws MospException {
		return createBeanInstance(CutoffReferenceBeanInterface.class);
	}
	
	@Override
	public CutoffSearchBeanInterface cutoffSearch() throws MospException {
		return createBeanInstance(CutoffSearchBeanInterface.class);
	}
	
	@Override
	public ApplicationReferenceBeanInterface application() throws MospException {
		return createBeanInstance(ApplicationReferenceBeanInterface.class);
	}
	
	@Override
	public ApplicationSearchBeanInterface applicationSearch() throws MospException {
		return createBeanInstance(ApplicationSearchBeanInterface.class);
	}
	
	@Override
	public ApplicationReferenceSearchBeanInterface applicationReferenceSearch() throws MospException {
		return createBeanInstance(ApplicationReferenceSearchBeanInterface.class);
	}
	
	@Override
	public PaidHolidayInfoReferenceBeanInterface paidHolidayInfo() throws MospException {
		return createBeanInstance(PaidHolidayInfoReferenceBeanInterface.class);
	}
	
	@Override
	public HolidayInfoReferenceBeanInterface holidayInfo() throws MospException {
		return createBeanInstance(HolidayInfoReferenceBeanInterface.class);
	}
	
	@Override
	public OvertimeInfoReferenceBeanInterface overtimeInfo() throws MospException {
		return createBeanInstance(OvertimeInfoReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalInfoReferenceBeanInterface approvalInfo() throws MospException {
		return createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayGrantReferenceBeanInterface paidHolidayGrant() throws MospException {
		return createBeanInstance(PaidHolidayGrantReferenceBeanInterface.class);
	}
	
	@Override
	public SubstituteReferenceBeanInterface substitute() throws MospException {
		return createBeanInstance(SubstituteReferenceBeanInterface.class);
	}
	
	@Override
	public AttendanceListReferenceBeanInterface attendanceList() throws MospException {
		return createBeanInstance(AttendanceListReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayUsageExportBeanInterface paidHolidayUsageExport() throws MospException {
		return createBeanInstance(PaidHolidayUsageExportBeanInterface.class);
	}
	
	@Override
	public ScheduleBookBeanInterface scheduleBook() throws MospException {
		return createBeanInstance(ScheduleBookBeanInterface.class);
	}
	
	@Override
	public AttendanceBookBeanInterface attendanceBook() throws MospException {
		return createBeanInstance(AttendanceBookBeanInterface.class);
	}
	
	@Override
	public CutoffUtilBeanInterface cutoffUtil() throws MospException {
		return createBeanInstance(CutoffUtilBeanInterface.class);
	}
	
	@Override
	public RequestUtilBeanInterface requestUtil() throws MospException {
		return createBeanInstance(RequestUtilBeanInterface.class);
	}
	
	@Override
	public ScheduleUtilBeanInterface scheduleUtil() throws MospException {
		return createBeanInstance(ScheduleUtilBeanInterface.class);
	}
	
	@Override
	public TimeRecordReferenceBeanInterface timeRecord() throws MospException {
		return createBeanInstance(TimeRecordReferenceBeanInterface.class);
	}
	
	@Override
	public AttendanceTotalInfoBeanInterface attendanceTotalInfo() throws MospException {
		return createBeanInstance(AttendanceTotalInfoBeanInterface.class);
	}
	
	@Override
	public PaidHolidayRemainBeanInterface paidHolidayRemain() throws MospException {
		return createBeanInstance(PaidHolidayRemainBeanInterface.class);
	}
	
	@Override
	public TimeMasterBeanInterface master() throws MospException {
		return createBeanInstance(TimeMasterBeanInterface.class);
	}
	
}
