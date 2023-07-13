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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceCorrectionDaoInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 勤怠修正情報参照処理。<br>
 */
public class AttendanceCorrectionReferenceBean extends PlatformBean
		implements AttendanceCorrectionReferenceBeanInterface {
	
	/**
	 * 勤怠データ修正情報DAOクラス。<br>
	 */
	protected AttendanceCorrectionDaoInterface	dao;
	
	/**
	 * 勤務形態管理参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface	workTypeReference;
	
	/**
	 * 休暇種別管理参照クラス。
	 */
	protected HolidayReferenceBeanInterface		holidayReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AttendanceCorrectionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(AttendanceCorrectionDaoInterface.class);
		workTypeReference = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		holidayReference = createBeanInstance(HolidayReferenceBeanInterface.class);
	}
	
	@Override
	public AttendanceCorrectionDtoInterface getLatestAttendanceCorrectionInfo(String personalId, Date workDate,
			int works) throws MospException {
		return dao.findForLatestInfo(personalId, workDate, works);
	}
	
	@Override
	public List<AttendanceCorrectionDtoInterface> getAttendanceCorrectionHistory(String personalId, Date workDate,
			int works) throws MospException {
		return dao.findForHistory(personalId, workDate, works);
	}
	
	@Override
	public Map<Date, List<AttendanceCorrectionDtoInterface>> getCorrections(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public Map<String, Set<AttendanceCorrectionDtoInterface>> getCorrections(Set<String> personalIds, Date workDate)
			throws MospException {
		return PlatformUtility.getPersonalIdMap(dao.findForPersonalIds(personalIds, workDate));
	}
	
	@Override
	public String getCorrectionValue(String code, String value, Date targetDate) throws MospException {
		// 値がハイフンの場合、ハイフンを返す
		if (value.equals(mospParams.getName("Hyphen"))) {
			return value;
		}
		// 勤務形態
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_WORKTYPECODE)) {
			if (workTypeReference != null) {
				return workTypeReference.getWorkTypeAbbr(value, targetDate);
			} else {
				return "";
			}
		}
		// 直行
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTSTART)) {
			if (value.equals("1")) {
				return mospParams.getName("DirectStart");
			} else {
				return "";
			}
		}
		// 直帰
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTEND)) {
			if (value.equals("1")) {
				return mospParams.getName("DirectEnd");
			} else {
				return "";
			}
		}
		// 遅刻証明書、早退証明書、手当1～10
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATECERTIFICATE)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCERTIFICATE)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE1)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE2)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE3)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE4)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE5)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE6)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE7)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE8)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE9)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE10)) {
			if (value.equals("0")) {
				return mospParams.getName("Without");
			} else if (value.equals("1")) {
				return mospParams.getName("Yes");
			} else {
				return "";
			}
		}
		// 出勤時刻、実出勤時刻、退勤時刻、実退勤時刻、勤怠コメント、遅刻コメント、早退コメント、
		// 休暇1～6、公用外出1～2、私用外出1～2
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_STARTTIME)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ENDTIME)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ACTUAL_STARTTIME)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ACTUAL_ENDTIME)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_TIMECOMMENT)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATECOMMENT)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCOMMENT)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK1)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK2)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK3)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK4)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK5)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK6)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_OFFICIAL_GOING_OUT1)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_OFFICIAL_GOING_OUT2)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_PRIVATE_GOING_OUT1)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_PRIVATE_GOING_OUT2)) {
			return value;
		}
		// 遅刻理由、早退理由
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATEREASON)
				|| code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYREASON)) {
			if (TimeConst.CODE_TARDINESS_WHY_INDIVIDU.equals(value)) {
				return mospParams.getName("Personal", "Convenience");
			} else if (TimeConst.CODE_TARDINESS_WHY_BAD_HEALTH.equals(value)) {
				return mospParams.getName("PhysicalCondition", "Bad");
			} else if (TimeConst.CODE_TARDINESS_WHY_OTHERS.equals(value)) {
				return mospParams.getName("Others");
			} else if (TimeConst.CODE_TARDINESS_WHY_TRAIN.equals(value)) {
				return mospParams.getName("Train", "Delay");
			} else if (TimeConst.CODE_TARDINESS_WHY_COMPANY.equals(value)) {
				return mospParams.getName("Company", "Directions");
			} else {
				return "";
			}
		}
		return code;
	}
	
	@Override
	public String getHistoryAttendanceMoreName(String code) {
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_WORKTYPECODE)) {
			return mospParams.getName("Work", "Form");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_STARTTIME)) {
			return mospParams.getName("GoingWork", "Moment");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ENDTIME)) {
			return mospParams.getName("RetireOffice", "Moment");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ACTUAL_STARTTIME)) {
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Fact"));
			sb.append(mospParams.getName("GoingWork"));
			sb.append(mospParams.getName("Moment"));
			return sb.toString();
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ACTUAL_ENDTIME)) {
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Fact"));
			sb.append(mospParams.getName("RetireOffice"));
			sb.append(mospParams.getName("Moment"));
			return sb.toString();
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTSTART)) {
			return mospParams.getName("DirectStart");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTEND)) {
			return mospParams.getName("DirectEnd");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATEREASON)) {
			return mospParams.getName("Tardiness", "Reason");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATECERTIFICATE)) {
			return mospParams.getName("Tardiness", "Certificates");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATECOMMENT)) {
			return mospParams.getName("Tardiness", "Comment");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYREASON)) {
			return mospParams.getName("LeaveEarly", "Reason");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCERTIFICATE)) {
			return mospParams.getName("LeaveEarly", "Certificates");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCOMMENT)) {
			return mospParams.getName("LeaveEarly", "Comment");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_TIMECOMMENT)) {
			return mospParams.getName("WorkManage", "Comment");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE1)) {
			return mospParams.getName("Allowance", "No1");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE2)) {
			return mospParams.getName("Allowance", "No2");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE3)) {
			return mospParams.getName("Allowance", "No3");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE4)) {
			return mospParams.getName("Allowance", "No4");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE5)) {
			return mospParams.getName("Allowance", "No5");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE6)) {
			return mospParams.getName("Allowance", "No6");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE7)) {
			return mospParams.getName("Allowance", "No7");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE8)) {
			return mospParams.getName("Allowance", "No8");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE9)) {
			return mospParams.getName("Allowance", "No9");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE10)) {
			return mospParams.getName("Allowance", "No10");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK1)) {
			return mospParams.getName("Rest1");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK2)) {
			return mospParams.getName("Rest2");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK3)) {
			return mospParams.getName("Rest3");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK4)) {
			return mospParams.getName("Rest4");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK5)) {
			return mospParams.getName("Rest5");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK6)) {
			return mospParams.getName("Rest6");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_OFFICIAL_GOING_OUT1)) {
			return mospParams.getName("Official", "GoingOut", "No1");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_OFFICIAL_GOING_OUT2)) {
			return mospParams.getName("Official", "GoingOut", "No2");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_PRIVATE_GOING_OUT1)) {
			return mospParams.getName("PrivateGoingOut1");
		}
		if (code.equals(TimeConst.CODE_ATTENDANCE_ITEM_NAME_PRIVATE_GOING_OUT2)) {
			return mospParams.getName("PrivateGoingOut2");
		}
		return code;
	}
	
	@Override
	public String getHistoryAttendanceAggregateName(TotalTimeCorrectionDtoInterface dto) throws MospException {
		// 計算年月における基準日取得
		Date date = MonthUtility.getYearMonthTargetDate(dto.getCalculationYear(), dto.getCalculationMonth(),
				mospParams);
		String type = dto.getCorrectionType();
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKTIME)) {
			return mospParams.getName("Work", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICWORKTIME)) {
			return mospParams.getName("Prescribed", "Work", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESWORKDATE)) {
			return mospParams.getName("GoingWork", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESWORK)) {
			return mospParams.getName("GoingWork", "Frequency");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEGALWORKONHOLIDAY)) {
			return mospParams.getName("Legal", "DayOff", "GoingWork", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICWORKONHOLIDAY)) {
			return mospParams.getName("Prescribed", "DayOff", "GoingWork", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_DIRECTSTART)) {
			return mospParams.getName("DirectStart", "Frequency");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_DIRECTEND)) {
			return mospParams.getName("DirectEnd", "Frequency");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTTIME)) {
			return mospParams.getName("RestTime", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTLATENIGHT)) {
			return mospParams.getName("Midnight", "RestTime", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTWORKONSPECIFICHOLIDAY)) {
			return mospParams.getName("Prescribed", "WorkingHoliday", "RestTime", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTWORKONHOLIDAY)) {
			return mospParams.getName("Legal", "WorkingHoliday", "RestTime", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_PUBLICTIME)) {
			return mospParams.getName("PublicGoingOut", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_PRIVATETIME)) {
			return mospParams.getName("PrivateGoingOut", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESOVERTIME)) {
			return mospParams.getName("OvertimeWork", "Frequency");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME)) {
			return mospParams.getName("OvertimeWork", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME_IN)) {
			return mospParams.getName("Legal", "Inside", "OvertimeWork", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME_OUT)) {
			return mospParams.getName("Legal", "Outside", "OvertimeWork", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATENIGHT)) {
			return mospParams.getName("Midnight", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKONSPECIFICHOLIDAY)) {
			return mospParams.getName("Prescribed", "WorkingHoliday", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKONHOLIDAY)) {
			return mospParams.getName("Legal", "WorkingHoliday", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_DECREASETIME)) {
			return mospParams.getName("Reduced", "Target", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_FORTYFIVEHOUROVERTIME)) {
			return mospParams.getName("No45", "Time", "Exceed", "OvertimeWork", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLATE)) {
			// 遅刻合計回数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Tardiness"));
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("Frequency"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATETIME)) {
			// 遅刻合計時間
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Tardiness"));
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("Time"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEAVEEARLY)) {
			// 早退合計回数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("LeaveEarly"));
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("Frequency"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVEEARLYTIME)) {
			// 早退合計時間
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("LeaveEarly"));
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("Time"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESHOLIDAY)) {
			// 合計休日日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("Holiday"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALHOLIDAY)) {
			return mospParams.getName("Legal", "DayOff", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICHOLIDAY)) {
			return mospParams.getName("Prescribed", "DayOff", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESPAIDHOLIDAY)) {
			return mospParams.getName("Salaried", "Holiday", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_PAIDHOLIDAYHOUR)) {
			return mospParams.getName("Salaried", "Holiday", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSTOCKHOLIDAY)) {
			return mospParams.getName("Stock", "Holiday", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESCOMPENSATION)) {
			// 合計代休日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("CompensatoryHoliday"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALCOMPENSATION)) {
			return mospParams.getName("Legal", "CompensatoryHoliday", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICCOMPENSATION)) {
			return mospParams.getName("Prescribed", "CompensatoryHoliday", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLATECOMPENSATION)) {
			return mospParams.getName("Midnight", "CompensatoryHoliday", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESHOLIDAYSUBSTITUTE)) {
			// 合計振替休日日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("Transfer"));
			sb.append(mospParams.getName("DayOff"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALHOLIDAYSUBSTITUTE)) {
			return mospParams.getName("Legal", "Transfer", "DayOff", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICHOLIDAYSUBSTITUTE)) {
			return mospParams.getName("Prescribed", "Transfer", "DayOff", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALSPECIALHOLIDAY)) {
			return mospParams.getName("Specially", "Holiday", "SumTotal", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALOTHERHOLIDAY)) {
			return mospParams.getName("Others", "Holiday", "SumTotal", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALABSENCE)) {
			return mospParams.getName("Absence", "SumTotal", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALALLOWANCE)) {
			return mospParams.getName("Allowance", "SumTotal");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_SIXTYHOUROVERTIME)) {
			return mospParams.getName("No60", "Time", "Exceed", "OvertimeWork", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_WEEKDAYOVERTIME)) {
			return mospParams.getName("Weekday", "Time", "Outside", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICOVERTIME)) {
			return mospParams.getName("Prescribed", "DayOff", "Time", "Outside", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESALTERNATIVE)) {
			return mospParams.getName("Substitute", "Holiday", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE1)) {
			return mospParams.getName("Allowance", "No1");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE2)) {
			return mospParams.getName("Allowance", "No2");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE3)) {
			return mospParams.getName("Allowance", "No3");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE4)) {
			return mospParams.getName("Allowance", "No4");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE5)) {
			return mospParams.getName("Allowance", "No5");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE6)) {
			return mospParams.getName("Allowance", "No6");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE7)) {
			return mospParams.getName("Allowance", "No7");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE8)) {
			return mospParams.getName("Allowance", "No8");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE9)) {
			return mospParams.getName("Allowance", "No9");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE10)) {
			return mospParams.getName("Allowance", "No10");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATEDAYS)) {
			// 遅刻合計日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Tardiness"));
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVEEARLYDAYS)) {
			// 早退合計日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("LeaveEarly"));
			sb.append(mospParams.getName("SumTotal"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEGALCOMPENSATIONUNUSED)) {
			// 法定代休未取得日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Legal"));
			sb.append(mospParams.getName("CompensatoryHoliday"));
			sb.append(mospParams.getName("Ram"));
			sb.append(mospParams.getName("Acquisition"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICCOMPENSATIONUNUSED)) {
			// 所定代休未取得日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Prescribed"));
			sb.append(mospParams.getName("CompensatoryHoliday"));
			sb.append(mospParams.getName("Ram"));
			sb.append(mospParams.getName("Acquisition"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATECOMPENSATIONUNUSED)) {
			// 深夜代休未取得日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Midnight"));
			sb.append(mospParams.getName("CompensatoryHoliday"));
			sb.append(mospParams.getName("Ram"));
			sb.append(mospParams.getName("Acquisition"));
			return sb.toString();
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_ATTENDANCE_ACHIEVEMENT)) {
			// 出勤実績日数
			return mospParams.getName("GoingWork", "Performance", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_WORK_DATE)) {
			// 出勤対象日数
			return mospParams.getName("GoingWork", "Target", "Days");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME)) {
			// 残業時間
			return mospParams.getName("OvertimeWork", "Time");
		}
		if (type.equals(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMES_WORKING_HOLIDAY)) {
			// 休出回数
			return mospParams.getName("WorkingHoliday", "Frequency");
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_THIRTY_MINUTES_OR_MORE.equals(type)) {
			// 遅刻30分以上日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Tardiness"));
			sb.append(mospParams.getName("Thirty"));
			sb.append(mospParams.getName("Minutes"));
			sb.append(mospParams.getName("Over"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_LESS_THAN_THIRTY_MINUTES.equals(type)) {
			// 遅刻30分未満日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Tardiness"));
			sb.append(mospParams.getName("Thirty"));
			sb.append(mospParams.getName("Minutes"));
			sb.append(mospParams.getName("LessThan"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_THIRTY_MINUTES_OR_MORE_TIME.equals(type)) {
			// 遅刻30分以上時間
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Tardiness"));
			sb.append(mospParams.getName("Thirty"));
			sb.append(mospParams.getName("Minutes"));
			sb.append(mospParams.getName("Over"));
			sb.append(mospParams.getName("Time"));
			return sb.toString();
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_LESS_THAN_THIRTY_MINUTES_TIME.equals(type)) {
			// 遅刻30分未満時間
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Tardiness"));
			sb.append(mospParams.getName("Thirty"));
			sb.append(mospParams.getName("Minutes"));
			sb.append(mospParams.getName("LessThan"));
			sb.append(mospParams.getName("Time"));
			return sb.toString();
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE.equals(type)) {
			// 早退30分以上日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("LeaveEarly"));
			sb.append(mospParams.getName("Thirty"));
			sb.append(mospParams.getName("Minutes"));
			sb.append(mospParams.getName("Over"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES.equals(type)) {
			// 早退30分未満日数
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("LeaveEarly"));
			sb.append(mospParams.getName("Thirty"));
			sb.append(mospParams.getName("Minutes"));
			sb.append(mospParams.getName("LessThan"));
			sb.append(mospParams.getName("Days"));
			return sb.toString();
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME.equals(type)) {
			// 早退30分以上時間
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("LeaveEarly"));
			sb.append(mospParams.getName("Thirty"));
			sb.append(mospParams.getName("Minutes"));
			sb.append(mospParams.getName("Over"));
			sb.append(mospParams.getName("Time"));
			return sb.toString();
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME.equals(type)) {
			// 早退30分未満時間
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("LeaveEarly"));
			sb.append(mospParams.getName("Thirty"));
			sb.append(mospParams.getName("Minutes"));
			sb.append(mospParams.getName("LessThan"));
			sb.append(mospParams.getName("Time"));
			return sb.toString();
		}
		if (TimeConst.CODE_TOTALTIME_ITEM_NAME_SHORT_UNPAID.equals(type)) {
			// 無給時短時間
			return mospParams.getName("UnpaidShortTime");
		}
		String[] codeArray = type.split(",");
		if (codeArray.length >= 2) {
			String arrayType = codeArray[0];
			String arrayCode = codeArray[1];
			// 修正箇所区分を確認
			if (MospUtility.isEqual(arrayType, TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALLEAVE)) {
				// 特別休暇日数である場合
				return getHolidayName(arrayCode, date, TimeConst.CODE_HOLIDAYTYPE_SPECIAL, true);
			} else if (MospUtility.isEqual(arrayType, TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALLEAVEHOUR)) {
				// 特別休暇時間である場合
				return getHolidayName(arrayCode, date, TimeConst.CODE_HOLIDAYTYPE_SPECIAL, false);
			} else if (MospUtility.isEqual(arrayType, TimeConst.CODE_TOTALTIME_ITEM_NAME_OTHERVACATION)) {
				// その他休暇日数である場合
				return getHolidayName(arrayCode, date, TimeConst.CODE_HOLIDAYTYPE_OTHER, true);
			} else if (MospUtility.isEqual(arrayType, TimeConst.CODE_TOTALTIME_ITEM_NAME_OTHERVACATIONHOUR)) {
				// その他休暇時間である場合
				return getHolidayName(arrayCode, date, TimeConst.CODE_HOLIDAYTYPE_OTHER, false);
			} else if (MospUtility.isEqual(arrayType, TimeConst.CODE_TOTALTIME_ITEM_NAME_ABSENCE)) {
				// 欠勤日数である場合
				return getHolidayName(arrayCode, date, TimeConst.CODE_HOLIDAYTYPE_ABSENCE, true);
			} else if (MospUtility.isEqual(arrayType, TimeConst.CODE_TOTALTIME_ITEM_NAME_ABSENCEHOUR)) {
				// 欠勤時間である場合
				return getHolidayName(arrayCode, date, TimeConst.CODE_HOLIDAYTYPE_ABSENCE, false);
			}
		}
		return type;
	}
	
	/**
	 * 修正箇所名称(休暇略称日数or時間)を取得する。<br>
	 * @param holidayCode 休暇コード
	 * @param targetDate  対象日
	 * @param holidayType 休暇区分
	 * @param isDays      日数フラグ(true：日数、false：時間)
	 * @return 修正箇所名称(休暇略称日数or時間)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getHolidayName(String holidayCode, Date targetDate, int holidayType, boolean isDays)
			throws MospException {
		// 文字列を準備
		StringBuilder sb = new StringBuilder();
		// 休暇略称を取得し設定
		sb.append(holidayReference.getHolidayAbbr(holidayCode, targetDate, holidayType));
		// 日数フラグを確認
		if (isDays) {
			// 日数を設定
			sb.append(TimeNamingUtility.days(mospParams));
		} else {
			// 時間を設定
			sb.append(PfNameUtility.time(mospParams));
		}
		// 修正箇所名称(休暇略称日数or時間)を取得
		return sb.toString();
	}
	
	@Override
	public boolean chkRest(RestDtoInterface newRest, RestDtoInterface oldRest) {
		return newRest.getRestStart().equals(oldRest.getRestStart()) == false
				|| newRest.getRestEnd().equals(oldRest.getRestEnd()) == false;
	}
	
	@Override
	public boolean chkGoOut(GoOutDtoInterface newGoOut, GoOutDtoInterface oldGoOut) {
		return newGoOut.getGoOutStart().equals(oldGoOut.getGoOutStart()) == false
				|| newGoOut.getGoOutEnd().equals(oldGoOut.getGoOutEnd()) == false;
	}
	
}
