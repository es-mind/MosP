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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.utils.DifferenceUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 時差出勤申請参照処理。<br>
 */
public class DifferenceRequestReferenceBean extends TimeBean implements DifferenceRequestReferenceBeanInterface {
	
	/**
	 * 時差出勤DAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface dao;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public DifferenceRequestReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(DifferenceRequestDaoInterface.class);
	}
	
	/**
	 * @throws MospException アドオンで例外が発生した場合
	 */
	@Override
	public String[][] getSelectArray() throws MospException {
		return mospParams.getProperties().getCodeArray(TimeConst.CODE_DIFFERENCE_TYPE, false);
	}
	
	@Override
	public DifferenceRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate)
			throws MospException {
		return dao.findForKeyOnWorkflow(personalId, requestDate);
	}
	
	@Override
	public DifferenceRequestDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (DifferenceRequestDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public DifferenceRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public List<DifferenceRequestDtoInterface> getDifferenceRequestList(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return dao.findForList(personalId, firstDate, lastDate);
	}
	
	@Override
	public List<DifferenceRequestDtoInterface> getDifferenceRequests(Collection<String> personalIds, Date requestDate)
			throws MospException {
		return dao.findForPersonalIds(personalIds, requestDate);
	}
	
	@Override
	public String getDifferenceTime(DifferenceRequestDtoInterface dto) {
		// 表示例 08:00～16:00
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtility.getStringTime(dto.getRequestStart(), dto.getRequestDate()));
		sb.append(mospParams.getName("Wave"));
		sb.append(DateUtility.getStringTime(dto.getRequestEnd(), dto.getRequestDate()));
		return sb.toString();
	}
	
	/**
	 * @throws MospException アドオンで例外が発生した場合
	 */
	@Override
	public String[][] getDifferenceSelectArray(String type) throws MospException {
		String[][] aryDifference = new String[1][2];
		String[][] aryDifferenceType = mospParams.getProperties().getCodeArray(TimeConst.CODE_DIFFERENCE_TYPE, false);
		if (TimeConst.CODE_DIFFERENCE_TYPE_A.equals(type)) {
			aryDifference[0][0] = aryDifferenceType[0][0];
			aryDifference[0][1] = aryDifferenceType[0][1];
			return aryDifference;
		}
		if (TimeConst.CODE_DIFFERENCE_TYPE_B.equals(type)) {
			aryDifference[0][0] = aryDifferenceType[1][0];
			aryDifference[0][1] = aryDifferenceType[1][1];
			return aryDifference;
		}
		if (TimeConst.CODE_DIFFERENCE_TYPE_C.equals(type)) {
			aryDifference[0][0] = aryDifferenceType[2][0];
			aryDifference[0][1] = aryDifferenceType[2][1];
			return aryDifference;
		}
		if (TimeConst.CODE_DIFFERENCE_TYPE_D.equals(type)) {
			aryDifference[0][0] = aryDifferenceType[3][0];
			aryDifference[0][1] = aryDifferenceType[3][1];
			return aryDifference;
		}
		if (TimeConst.CODE_DIFFERENCE_TYPE_S.equals(type)) {
			aryDifference[0][0] = aryDifferenceType[4][0];
			aryDifference[0][1] = aryDifferenceType[4][1];
			return aryDifference;
		}
		return new String[0][0];
	}
	
	@Override
	public String getDifferenceAbbr(String type) {
		// 時差出勤申請が有効ではない場合
		if (!TimeUtility.isDifferenceRequestValid(mospParams)) {
			return type;
		}
		// 時差出勤名称を取得
		return DifferenceUtility.getDifferenceName(mospParams, type);
	}
	
	@Override
	public boolean isDifferenceTypeA(String differenceType) {
		return DifferenceUtility.isDifferenceTypeA(differenceType);
	}
	
	@Override
	public boolean isDifferenceTypeB(String differenceType) {
		return DifferenceUtility.isDifferenceTypeB(differenceType);
	}
	
	@Override
	public boolean isDifferenceTypeC(String differenceType) {
		return DifferenceUtility.isDifferenceTypeC(differenceType);
	}
	
	@Override
	public boolean isDifferenceTypeD(String differenceType) {
		return DifferenceUtility.isDifferenceTypeD(differenceType);
	}
	
	@Override
	public boolean isDifferenceTypeS(String differenceType) {
		return DifferenceUtility.isDifferenceTypeS(differenceType);
	}
	
	@Override
	public boolean isDifferenceTypeA(DifferenceRequestDtoInterface dto) {
		return isDifferenceTypeA(dto.getDifferenceType());
	}
	
	@Override
	public boolean isDifferenceTypeB(DifferenceRequestDtoInterface dto) {
		return isDifferenceTypeB(dto.getDifferenceType());
	}
	
	@Override
	public boolean isDifferenceTypeC(DifferenceRequestDtoInterface dto) {
		return isDifferenceTypeC(dto.getDifferenceType());
	}
	
	@Override
	public boolean isDifferenceTypeD(DifferenceRequestDtoInterface dto) {
		return isDifferenceTypeD(dto.getDifferenceType());
	}
	
	@Override
	public boolean isDifferenceTypeS(DifferenceRequestDtoInterface dto) {
		return isDifferenceTypeS(dto.getDifferenceType());
	}
	
	@Override
	public int getDifferenceWorkTime(boolean morningOff, boolean afternoonOff) {
		if (morningOff && afternoonOff) {
			// 午前休且つ午後休の場合
			return 0;
		} else if (morningOff) {
			// 午前休の場合
			return 3 * TimeConst.CODE_DEFINITION_HOUR + 30;
		} else if (afternoonOff) {
			// 午後休の場合
			return 3 * TimeConst.CODE_DEFINITION_HOUR + 30;
		}
		// 午前休でなく且つ午後休でない場合
		return 7 * TimeConst.CODE_DEFINITION_HOUR;
	}
	
	@Override
	public int getDifferenceRestTime(boolean morningOff, boolean afternoonOff) {
		if (morningOff && afternoonOff) {
			// 午前休且つ午後休の場合
			return 0;
		} else if (morningOff) {
			// 午前休の場合
			return 0;
		} else if (afternoonOff) {
			// 午後休の場合
			return 0;
		}
		// 午前休でなく且つ午後休でない場合
		return TimeConst.CODE_DEFINITION_HOUR;
	}
	
	@Override
	public Date getDifferenceStartTimeTypeA(Date date) throws MospException {
		int year = DateUtility.getYear(date);
		int month = DateUtility.getMonth(date);
		int day = DateUtility.getDay(date);
		return DateUtility.getDateTime(year, month, day, 8, 0);
	}
	
	@Override
	public Date getDifferenceStartTimeTypeB(Date date) throws MospException {
		int year = DateUtility.getYear(date);
		int month = DateUtility.getMonth(date);
		int day = DateUtility.getDay(date);
		return DateUtility.getDateTime(year, month, day, 8, 30);
	}
	
	@Override
	public Date getDifferenceStartTimeTypeC(Date date) throws MospException {
		int year = DateUtility.getYear(date);
		int month = DateUtility.getMonth(date);
		int day = DateUtility.getDay(date);
		return DateUtility.getDateTime(year, month, day, 9, 30);
	}
	
	@Override
	public Date getDifferenceStartTimeTypeD(Date date) throws MospException {
		int year = DateUtility.getYear(date);
		int month = DateUtility.getMonth(date);
		int day = DateUtility.getDay(date);
		return DateUtility.getDateTime(year, month, day, 10, 0);
	}
	
	@Override
	public Date getDifferenceStartTimeMorningOff(Date startTime) {
		int minute = getDifferenceWorkTime(false, false) + getDifferenceRestTime(false, false)
				- getDifferenceWorkTime(true, false);
		return DateUtility.addMinute(startTime, minute);
	}
	
	@Override
	public Date getDifferenceEndTimeTypeA(Date date) throws MospException {
		int year = DateUtility.getYear(date);
		int month = DateUtility.getMonth(date);
		int day = DateUtility.getDay(date);
		return DateUtility.getDateTime(year, month, day, 16, 0);
	}
	
	@Override
	public Date getDifferenceEndTimeTypeB(Date date) throws MospException {
		int year = DateUtility.getYear(date);
		int month = DateUtility.getMonth(date);
		int day = DateUtility.getDay(date);
		return DateUtility.getDateTime(year, month, day, 16, 30);
	}
	
	@Override
	public Date getDifferenceEndTimeTypeC(Date date) throws MospException {
		int year = DateUtility.getYear(date);
		int month = DateUtility.getMonth(date);
		int day = DateUtility.getDay(date);
		return DateUtility.getDateTime(year, month, day, 17, 30);
	}
	
	@Override
	public Date getDifferenceEndTimeTypeD(Date date) throws MospException {
		int year = DateUtility.getYear(date);
		int month = DateUtility.getMonth(date);
		int day = DateUtility.getDay(date);
		return DateUtility.getDateTime(year, month, day, 18, 0);
	}
	
	@Override
	public Date getDifferenceEndTimeTypeS(Date startTime) {
		int minute = getDifferenceWorkTime(false, false) + getDifferenceRestTime(false, false);
		return DateUtility.addMinute(startTime, minute);
	}
	
	@Override
	public Date getDifferenceEndTimeAfternoonOff(Date endTime) {
		int minute = getDifferenceWorkTime(false, false) + getDifferenceRestTime(false, false)
				- getDifferenceWorkTime(false, true);
		return DateUtility.addMinute(endTime, -minute);
	}
	
	@Override
	public Date getDifferenceRestStartTimeTypeA(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Aの休憩開始時刻は対象日の12時
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), 12, 0);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の3時間後を休憩開始時刻とする
		return DateUtility.addHour(startTime, 3);
	}
	
	@Override
	public Date getDifferenceRestStartTimeTypeB(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Bの休憩開始時刻は対象日の12時
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), 12, 0);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の3時間後を休憩開始時刻とする
		return DateUtility.addHour(startTime, 3);
	}
	
	@Override
	public Date getDifferenceRestStartTimeTypeC(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Cの休憩開始時刻は対象日の12時
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), 12, 0);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の3時間後を休憩開始時刻とする
		return DateUtility.addHour(startTime, 3);
	}
	
	@Override
	public Date getDifferenceRestStartTimeTypeD(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Dの休憩開始時刻は対象日の12時
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), 12, 0);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の3時間後を休憩開始時刻とする
		return DateUtility.addHour(startTime, 3);
	}
	
	@Override
	public Date getDifferenceRestStartTimeTypeS(Date startTime, Date endTime, Date differenceStartTime,
			boolean isHalfHoliday) {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Sの休憩開始時刻は規定時差出勤始業時刻の3時間後
			return DateUtility.addHour(differenceStartTime, 3);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の3時間後を休憩開始時刻とする
		return DateUtility.addHour(startTime, 3);
	}
	
	@Override
	public Date getDifferenceRestEndTimeTypeA(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Aの休憩終了時刻は対象日の13時
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), 13, 0);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の4時間後を休憩終了時刻とする
		return DateUtility.addHour(startTime, 4);
	}
	
	@Override
	public Date getDifferenceRestEndTimeTypeB(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Bの休憩終了時刻は対象日の13時
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), 13, 0);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の4時間後を休憩終了時刻とする
		return DateUtility.addHour(startTime, 4);
	}
	
	@Override
	public Date getDifferenceRestEndTimeTypeC(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Cの休憩終了時刻は対象日の13時
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), 13, 0);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の4時間後を休憩終了時刻とする
		return DateUtility.addHour(startTime, 4);
	}
	
	@Override
	public Date getDifferenceRestEndTimeTypeD(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Dの休憩終了時刻は対象日の13時
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), 13, 0);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の4時間後を休憩終了時刻とする
		return DateUtility.addHour(startTime, 4);
	}
	
	@Override
	public Date getDifferenceRestEndTimeTypeS(Date startTime, Date endTime, Date differenceStartTime,
			boolean isHalfHoliday) {
		if (!isHalfHoliday) {
			// 半休でない場合
			// 時差出勤区分Sの休憩終了時刻は規定時差出勤始業時刻の4時間後
			return DateUtility.addHour(differenceStartTime, 4);
		}
		// 半休の場合
		if (startTime == null || endTime == null
				|| getDefferenceMinutes(startTime, endTime) <= 6 * TimeConst.CODE_DEFINITION_HOUR) {
			return null;
		}
		// 始業時刻と終業時刻の差が6時間を超える場合は
		// 始業時刻の4時間後を休憩終了時刻とする
		return DateUtility.addHour(startTime, 4);
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		// 勤怠基本情報確認
		initial(personalId, targetDate, TimeConst.CODE_FUNCTION_DIFFERENCE);
	}
	
}
