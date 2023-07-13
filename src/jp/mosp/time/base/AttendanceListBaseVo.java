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

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 勤怠一覧における画面の情報を格納する。<br>
 * <br>
 */
public class AttendanceListBaseVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 9015459341372577347L;
	
	/**
	 * 表示年月(年)。
	 */
	private String				pltSelectYear;
	
	/**
	 * 表示年月(月)。
	 */
	private String				pltSelectMonth;
	
	/**
	 * 日付(表示用)。
	 */
	private String[]			aryLblDate;
	
	/**
	 * 曜日。
	 */
	private String[]			aryLblWeek;
	
	/**
	 * 曜日スタイル。
	 */
	private String[]			aryWorkDayOfWeekStyle;
	
	/**
	 * 勤務形態。
	 */
	private String[]			aryLblWorkType;
	
	/**
	 * 始業時間。
	 */
	private String[]			aryLblStartTime;
	
	/**
	 * 始業時間スタイル。
	 */
	private String[]			aryStartTimeStyle;
	
	/**
	 * 終業時間。
	 */
	private String[]			aryLblEndTime;
	
	/**
	 * 終業時間スタイル。
	 */
	private String[]			aryEndTimeStyle;
	
	/**
	 * 勤務時間。
	 */
	private String[]			aryLblWorkTime;
	
	/**
	 * 休憩時間。
	 */
	private String[]			aryLblRestTime;
	
	/**
	 * 備考。
	 */
	private String[]			aryLblRemark;
	
	/**
	 * 合計勤務時間。
	 */
	private String				lblTotalWork;
	
	/**
	 * 合計休憩時間。
	 */
	private String				lblTotalRest;
	
	/**
	 * 合計出勤回数。
	 */
	private String				lblTimesWork;
	
	/**
	 * 合計所定休日日数。
	 */
	private String				lblTimesPrescribedHoliday;
	
	/**
	 * 合計法定休日日数。
	 */
	private String				lblTimesLegalHoliday;
	
	/**
	 * 合計休日日数。
	 */
	private String				lblTimesHoliday;
	
	/**
	 * プルダウン用配列(表示年月(年))。
	 */
	private String[][]			aryPltSelectYear;
	
	/**
	 * プルダウン用配列(表示年月(月))。
	 */
	private String[][]			aryPltSelectMonth;
	
	/**
	 * 今月ボタンの年。<br>
	 */
	private int					thisMonthYear;
	
	/**
	 * 今月ボタンの月。<br>
	 */
	private int					thisMonthMonth;
	
	/**
	 * 次月ボタンの年。<br>
	 */
	private int					nextMonthYear;
	
	/**
	 * 次月ボタンの月。<br>
	 */
	private int					nextMonthMonth;
	
	/**
	 * 先月ボタンの年。<br>
	 */
	private int					prevMonthYear;
	
	/**
	 * 先月ボタンの月。<br>
	 */
	private int					prevMonthMonth;
	
	/**
	 * 前社員コード。<br>
	 */
	private String				lblPrevEmployeeCode;
	
	/**
	 * 次社員コード。<br>
	 */
	private String				lblNextEmployeeCode;
	
	/**
	 * 前個人ID。
	 */
	private String				prevPersonalId;
	
	/**
	 * 次個人ID。
	 */
	private String				nextPersonalId;
	
	/**
	 * 遷移DTO配列。<br>
	 */
	private BaseDtoInterface[]	rollArray;
	
	/**
	 * 表示コマンド。<br>
	 * 入力モードか承認モードかを判別するのに用いる。<br>
	 */
	private String				showCommand;
	
	/**
	 * 締日。<br>
	 * 表示している勤怠一覧等の締日を保持する。<br>
	 */
	private int					cutoffDate;
	
	
	/**
	 * @return pltSelectYear
	 */
	public String getPltSelectYear() {
		return pltSelectYear;
	}
	
	/**
	 * @return pltSelectMonth
	 */
	public String getPltSelectMonth() {
		return pltSelectMonth;
	}
	
	/**
	 * @return aryLblDate
	 */
	public String[] getAryLblDate() {
		return getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblDate
	 */
	public String getAryLblDate(int idx) {
		return aryLblDate[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWeek
	 */
	public String getAryLblWeek(int idx) {
		return aryLblWeek[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkType
	 */
	public String getAryLblWorkType(int idx) {
		return aryLblWorkType[idx];
	}
	
	/**
	 * @return aryLblStartTime
	 */
	public String[] getAryLblStartTime() {
		return getStringArrayClone(aryLblStartTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblStartTime
	 */
	public String getAryLblStartTime(int idx) {
		return aryLblStartTime[idx];
	}
	
	/**
	 * @param idx インデックス 
	 * @return aryStartTimeStyle
	 */
	public String getAryStartTimeStyle(int idx) {
		return aryStartTimeStyle[idx];
	}
	
	/**
	 * @param aryStartTimeStyle セットする aryStartTimeStyle
	 */
	public void setAryStartTimeStyle(String[] aryStartTimeStyle) {
		this.aryStartTimeStyle = getStringArrayClone(aryStartTimeStyle);
	}
	
	/**
	 * @return aryLblEndTime
	 */
	public String[] getAryLblEndTime() {
		return getStringArrayClone(aryLblEndTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEndTime
	 */
	public String getAryLblEndTime(int idx) {
		return aryLblEndTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkTime
	 */
	public String getAryLblWorkTime(int idx) {
		return aryLblWorkTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblRestTime(int idx) {
		return aryLblRestTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRemark
	 */
	public String getAryLblRemark(int idx) {
		return aryLblRemark[idx];
	}
	
	/**
	 * @return lblTotalWork
	 */
	public String getLblTotalWork() {
		return lblTotalWork;
	}
	
	/**
	 * @return lblTotalRest
	 */
	public String getLblTotalRest() {
		return lblTotalRest;
	}
	
	/**
	 * @return lblTimesWork
	 */
	public String getLblTimesWork() {
		return lblTimesWork;
	}
	
	/**
	 * @return lblTimesLegalHoliday
	 */
	public String getLblTimesLegalHoliday() {
		return lblTimesLegalHoliday;
	}
	
	/**
	 * @return lblTimesSpecialLeave
	 */
	public String getLblTimesPrescribedHoliday() {
		return lblTimesPrescribedHoliday;
	}
	
	/**
	 * @return lblTimesHoliday
	 */
	public String getLblTimesHoliday() {
		return lblTimesHoliday;
	}
	
	/**
	 * @param lblTimesHoliday セットする lblTimesHoliday。
	 */
	public void setLblTimesHoliday(String lblTimesHoliday) {
		this.lblTimesHoliday = lblTimesHoliday;
	}
	
	/**
	 * @return aryPltSelectYear
	 */
	public String[][] getAryPltSelectYear() {
		return getStringArrayClone(aryPltSelectYear);
	}
	
	/**
	 * @return aryPltSelectMonth
	 */
	public String[][] getAryPltSelectMonth() {
		return getStringArrayClone(aryPltSelectMonth);
	}
	
	/**
	 * @param pltSelectYear セットする pltSelectYear
	 */
	public void setPltSelectYear(String pltSelectYear) {
		this.pltSelectYear = pltSelectYear;
	}
	
	/**
	 * @param pltSelectMonth セットする pltSelectMonth
	 */
	public void setPltSelectMonth(String pltSelectMonth) {
		this.pltSelectMonth = pltSelectMonth;
	}
	
	/**
	 * @param aryLblDate セットする aryLblDate
	 */
	public void setAryLblDate(String[] aryLblDate) {
		this.aryLblDate = getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @param aryLblWeek セットする aryLblWeek
	 */
	public void setAryLblWeek(String[] aryLblWeek) {
		this.aryLblWeek = getStringArrayClone(aryLblWeek);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkDayOfWeekStyle
	 */
	public String getAryWorkDayOfWeekStyle(int idx) {
		return aryWorkDayOfWeekStyle[idx];
	}
	
	/**
	 * @param aryWorkDayOfWeekStyle セットする aryWorkDayOfWeekStyle
	 */
	public void setAryWorkDayOfWeekStyle(String[] aryWorkDayOfWeekStyle) {
		this.aryWorkDayOfWeekStyle = getStringArrayClone(aryWorkDayOfWeekStyle);
	}
	
	/**
	 * @param aryLblWorkType セットする aryLblWorkType
	 */
	public void setAryLblWorkType(String[] aryLblWorkType) {
		this.aryLblWorkType = getStringArrayClone(aryLblWorkType);
	}
	
	/**
	 * @param aryLblStartTime セットする aryLblStartTime
	 */
	public void setAryLblStartTime(String[] aryLblStartTime) {
		this.aryLblStartTime = getStringArrayClone(aryLblStartTime);
	}
	
	/**
	 * @param aryLblEndTime セットする aryLblEndTime
	 */
	public void setAryLblEndTime(String[] aryLblEndTime) {
		this.aryLblEndTime = getStringArrayClone(aryLblEndTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryEndTimeStyle
	 */
	public String getAryEndTimeStyle(int idx) {
		return aryEndTimeStyle[idx];
	}
	
	/**
	 * @param aryEndTimeStyle セットする aryEndTimeStyle
	 */
	public void setAryEndTimeStyle(String[] aryEndTimeStyle) {
		this.aryEndTimeStyle = getStringArrayClone(aryEndTimeStyle);
	}
	
	/**
	 * @param aryLblWorkTime セットする aryLblWorkTime
	 */
	public void setAryLblWorkTime(String[] aryLblWorkTime) {
		this.aryLblWorkTime = getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @param aryLblRestTime セットする aryLblRestTime
	 */
	public void setAryLblRestTime(String[] aryLblRestTime) {
		this.aryLblRestTime = getStringArrayClone(aryLblRestTime);
	}
	
	/**
	 * @param aryLblRemark セットする aryLblRemark
	 */
	public void setAryLblRemark(String[] aryLblRemark) {
		this.aryLblRemark = getStringArrayClone(aryLblRemark);
	}
	
	/**
	 * @param lblTotalWork セットする lblTotalWork
	 */
	public void setLblTotalWork(String lblTotalWork) {
		this.lblTotalWork = lblTotalWork;
	}
	
	/**
	 * @param lblTotalRest セットする lblTotalRest
	 */
	public void setLblTotalRest(String lblTotalRest) {
		this.lblTotalRest = lblTotalRest;
	}
	
	/**
	 * @param lblTimesWork セットする lblTimesWork
	 */
	public void setLblTimesWork(String lblTimesWork) {
		this.lblTimesWork = lblTimesWork;
	}
	
	/**
	 * @param lblTimesLegalHoliday セットする lblTimesLegalHoliday
	 */
	public void setLblTimesLegalHoliday(String lblTimesLegalHoliday) {
		this.lblTimesLegalHoliday = lblTimesLegalHoliday;
	}
	
	/**
	 * @param lblTimesPrescribedHoliday セットする lblTimesPrescribedHoliday
	 */
	public void setLblTimesPrescribedHoliday(String lblTimesPrescribedHoliday) {
		this.lblTimesPrescribedHoliday = lblTimesPrescribedHoliday;
	}
	
	/**
	 * @param aryPltSelectYear セットする aryPltSelectYear
	 */
	public void setAryPltSelectYear(String[][] aryPltSelectYear) {
		this.aryPltSelectYear = getStringArrayClone(aryPltSelectYear);
	}
	
	/**
	 * @param aryPltSelectMonth セットする aryPltSelectMonth
	 */
	public void setAryPltSelectMonth(String[][] aryPltSelectMonth) {
		this.aryPltSelectMonth = getStringArrayClone(aryPltSelectMonth);
	}
	
	/**
	 * @return thisMonthYear
	 */
	public int getThisMonthYear() {
		return thisMonthYear;
	}
	
	/**
	 * @param thisMonthYear セットする thisMonthYear
	 */
	public void setThisMonthYear(int thisMonthYear) {
		this.thisMonthYear = thisMonthYear;
	}
	
	/**
	 * @return thisMonthMonth
	 */
	public int getThisMonthMonth() {
		return thisMonthMonth;
	}
	
	/**
	 * @param thisMonthMonth セットする thisMonthMonth
	 */
	public void setThisMonthMonth(int thisMonthMonth) {
		this.thisMonthMonth = thisMonthMonth;
	}
	
	/**
	 * @return nextMonthYear
	 */
	public int getNextMonthYear() {
		return nextMonthYear;
	}
	
	/**
	 * @param nextMonthYear セットする nextMonthYear
	 */
	public void setNextMonthYear(int nextMonthYear) {
		this.nextMonthYear = nextMonthYear;
	}
	
	/**
	 * @return nextMonthMonth
	 */
	public int getNextMonthMonth() {
		return nextMonthMonth;
	}
	
	/**
	 * @param nextMonthMonth セットする nextMonthMonth
	 */
	public void setNextMonthMonth(int nextMonthMonth) {
		this.nextMonthMonth = nextMonthMonth;
	}
	
	/**
	 * @return prevMonthYear
	 */
	public int getPrevMonthYear() {
		return prevMonthYear;
	}
	
	/**
	 * @param prevMonthYear セットする prevMonthYear
	 */
	public void setPrevMonthYear(int prevMonthYear) {
		this.prevMonthYear = prevMonthYear;
	}
	
	/**
	 * @return prevMonthMonth
	 */
	public int getPrevMonthMonth() {
		return prevMonthMonth;
	}
	
	/**
	 * @param prevMonthMonth セットする prevMonthMonth
	 */
	public void setPrevMonthMonth(int prevMonthMonth) {
		this.prevMonthMonth = prevMonthMonth;
	}
	
	/**
	 * @return lblPrevEmployeeCode
	 */
	public String getLblPrevEmployeeCode() {
		return lblPrevEmployeeCode;
	}
	
	/**
	 * @param lblPrevEmployeeCode セットする lblPrevEmployeeCode
	 */
	public void setLblPrevEmployeeCode(String lblPrevEmployeeCode) {
		this.lblPrevEmployeeCode = lblPrevEmployeeCode;
	}
	
	/**
	 * @return lblNextEmployeeCode
	 */
	public String getLblNextEmployeeCode() {
		return lblNextEmployeeCode;
	}
	
	/**
	 * @param lblNextEmployeeCode セットする lblNextEmployeeCode
	 */
	public void setLblNextEmployeeCode(String lblNextEmployeeCode) {
		this.lblNextEmployeeCode = lblNextEmployeeCode;
	}
	
	/**
	 * @return prevPersonalId
	 */
	public String getPrevPersonalId() {
		return prevPersonalId;
	}
	
	/**
	 * @param prevPersonalId セットする prevPersonalId
	 */
	public void setPrevPersonalId(String prevPersonalId) {
		this.prevPersonalId = prevPersonalId;
	}
	
	/**
	 * @return nextPersonalId
	 */
	public String getNextPersonalId() {
		return nextPersonalId;
	}
	
	/**
	 * @param nextPersonalId セットする nextPersonalId
	 */
	public void setNextPersonalId(String nextPersonalId) {
		this.nextPersonalId = nextPersonalId;
	}
	
	/**
	 * @return rollArray
	 */
	public BaseDtoInterface[] getRollArray() {
		return getDtoArrayClone(rollArray);
	}
	
	/**
	 * @param rollArray セットする rollArray
	 */
	public void setRollArray(BaseDtoInterface[] rollArray) {
		this.rollArray = getDtoArrayClone(rollArray);
	}
	
	/**
	 * @return showCommand
	 */
	public String getShowCommand() {
		return showCommand;
	}
	
	/**
	 * @param showCommand セットする showCommand
	 */
	public void setShowCommand(String showCommand) {
		this.showCommand = showCommand;
	}
	
	/**
	 * @return cutoffDate
	 */
	public int getCutoffDate() {
		return cutoffDate;
	}
	
	/**
	 * @param cutoffDate セットする cutoffDate
	 */
	public void setCutoffDate(int cutoffDate) {
		this.cutoffDate = cutoffDate;
	}
	
}
