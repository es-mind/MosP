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
package jp.mosp.time.input.vo;

import java.util.HashMap;
import java.util.Map;

import jp.mosp.time.base.AttendanceListBaseVo;

/**
 * 勤怠情報の一覧表示の情報を格納する。
 */
public class AttendanceListVo extends AttendanceListBaseVo {
	
	private static final long		serialVersionUID	= 5197932165843778696L;
	
	/**
	 * 日付(YYYY/MM/DD)。
	 */
	private String[]				aryDate;
	
	/**
	 * 始業打刻時間。
	 */
	private String[]				aryLblStartRecordTime;
	
	/**
	 * 終業打刻時間
	 */
	private String[]				aryLblEndRecordTime;
	
	/**
	 * 私用外出時間。
	 */
	private String[]				aryLblPrivateTime;
	
	/**
	 * 遅刻時間。
	 */
	private String[]				aryLblLateTime;
	
	/**
	 * 早退時間。
	 */
	private String[]				aryLblLeaveEarlyTime;
	
	/**
	 * 遅刻早退時間。
	 */
	private String[]				aryLblLateLeaveEarlyTime;
	
	/**
	 * 法定内残業時間。
	 */
	private String[]				aryLblOverTimeIn;
	
	/**
	 * 残業時間スタイル。
	 */
	private String[]				aryOvertimeStyle;
	
	/**
	 * 法定外残業時間。
	 */
	private String[]				aryLblOverTimeOut;
	
	/**
	 * 休日出勤時間。
	 */
	private String[]				aryLblWorkOnHoliday;
	
	/**
	 * 深夜時間。
	 */
	private String[]				aryLblLateNight;
	
	/**
	 * 無休時短時間。
	 */
	private String[]				aryLblShortUnpaid;
	
	/**
	 * 状態。
	 */
	private String[]				aryLblState;
	
	/**
	 * 状態スタイル。
	 */
	private String[]				aryStateStyle;
	
	/**
	 * 状態リンクコマンド。
	 */
	private boolean[]				aryLinkState;
	
	/**
	 * ワークフロー番号。
	 */
	private long[]					aryWorkflow;
	
	/**
	 * チェックボックス要否情報。
	 */
	private boolean[]				aryCheckState;
	
	/**
	 * 修正情報。
	 */
	private String[]				aryLblCorrection;
	
	/**
	 * 合計私用外出時間。
	 */
	private String					lblTotalPrivate;
	
	/**
	 * 合計遅刻時間。
	 */
	private String					lblTotalLate;
	
	/**
	 * 合計早退時間。
	 */
	private String					lblTotalLeaveEarly;
	
	/**
	 * 合計遅刻早退時間。
	 */
	private String					lblTotalLateLeaveEarly;
	
	/**
	 * 合計残業時間。
	 */
	private String					lblTotalOverTimeIn;
	
	/**
	 * 合計外残時間。
	 */
	private String					lblTotalOverTimeOut;
	
	/**
	 * 合計休日出勤時間。
	 */
	private String					lblTotalWorkOnHoliday;
	
	/**
	 * 合計深夜勤務時間。
	 */
	private String					lblTotalLateNight;
	
	/**
	 * 合計遅刻回数。
	 */
	private String					lblTimesLate;
	
	/**
	 * 合計早退回数。
	 */
	private String					lblTimesLeaveEarly;
	
	/**
	 * 合計残業回数。
	 */
	private String					lblTimesOverTimeWork;
	
	/**
	 * 合計休出回数。
	 */
	private String					lblTimesWorkOnHoliday;
	
	/**
	 * 合計所定代休発生回数。
	 */
	private String					lblTimesBirthPrescribedSubHolidayday;
	
	/**
	 * 合計法定代休発生回数。
	 */
	private String					lblTimesBirthLegalSubHolidayday;
	
	/**
	 * 合計深夜代休発生回数。
	 */
	private String					lblTimesBirthMidnightSubHolidayday;
	
	/**
	 * 合計休暇日数(所定休日+法定休日)
	 */
	private String					lblTimesHoliday;
	
	/**
	 * 合計有休日数。
	 */
	private String					lblTimesPaidHoliday;
	
	/**
	 * 合計有休時間。
	 */
	private String					lblTimesPaidHolidayTime;
	
	/**
	 * 合計特別休暇日数。
	 */
	private String					lblTimesSpecialHoloiday;
	
	/**
	 * 合計その他休暇日数。
	 */
	private String					lblTimesOtherHoloiday;
	
	/**
	 * 合計振替休暇日数。
	 */
	private String					lblTimesSubstitute;
	
	/**
	 * 合計代休日数。
	 */
	private String					lblTimesSubHoliday;
	
	/**
	 * 合計欠勤日数。
	 */
	private String					lblTimesAbsence;
	
	/**
	 * 合計無休時短時間。
	 */
	private String					lblShortUnpaidTotal;
	
	/**
	 * 合計分単位休暇A時間。
	 */
	private String					lblTimesMinutelyHolidayA;
	
	/**
	 * 始業時間(入力値取得用)。
	 */
	private String[]				txtStartTime;
	
	/**
	 * 終業時間(入力値取得用)。
	 */
	private String[]				txtEndTime;
	
	/**
	 * 集計ボタン要否。
	 */
	private boolean					isTotalButtonVisible;
	
	/**
	 * 始業打刻時間表示要否。
	 */
	private boolean					isLblStartRecordTime;
	
	/**
	 * 終業打刻時間表示要否。
	 */
	private boolean					isLblEndRecordTime;
	
	/**
	 * 追加フィールド。<br>
	 * アドオン機能で利用する。<br>
	 */
	private String					lblExtraFiled;
	
	/**
	 * 勤怠設定追加JSPリスト。<br>
	 */
	private String[]				addonJsps;
	
	/**
	 * 勤怠設定追加パラメータ配列群(キー：パラメータ名)。<br>
	 */
	private Map<String, String[]>	addonParameters;
	
	/**
	 * アドオン列項目名(キー：勤怠一覧追加処理)。<br>
	 * 勤怠一覧に表示する列の項目名を設定する。<br>
	 */
	private Map<String, String>		addonColumnNames;
	
	/**
	 * アドオン列項目値群(キー：勤怠一覧追加処理)。<br>
	 * 勤怠一覧に表示する列の項目値を設定する。<br>
	 */
	private Map<String, String[]>	addonColumnValues;
	
	/**
	 * アドオン列項目クラス文字群(キー：勤怠一覧追加処理)。<br>
	 * 勤怠一覧に表示する列のクラス文字列を設定する。<br>
	 * クラス名はListSelectTd+アドオン項目クラス文字となる。<br>
	 */
	private Map<String, String[]>	addonColumnClasses;
	
	
	/**
	 * コンストラクタ
	 */
	public AttendanceListVo() {
		super();
		addonJsps = new String[0];
		addonParameters = new HashMap<String, String[]>();
		addonColumnValues = new HashMap<String, String[]>();
		addonColumnClasses = new HashMap<String, String[]>();
		addonColumnNames = new HashMap<String, String>();
	}
	
	/**
	 * @return txtStartTime
	 */
	public String[] getTxtStartTime() {
		return getStringArrayClone(txtStartTime);
	}
	
	/**
	 * @param txtStartTime セットする txtStartTime
	 */
	public void setTxtStartTime(String[] txtStartTime) {
		this.txtStartTime = getStringArrayClone(txtStartTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblStartRecordTime
	 */
	public String getAryLblStartRecordTime(int idx) {
		return aryLblStartRecordTime[idx];
	}
	
	/**
	 * @param aryLblStartRecordTime セットする aryLblStartRecordTime
	 */
	public void setAryLblStartRecordTime(String[] aryLblStartRecordTime) {
		this.aryLblStartRecordTime = getStringArrayClone(aryLblStartRecordTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblStartRecordTime
	 */
	public String getAryLblEndRecordTime(int idx) {
		return aryLblEndRecordTime[idx];
	}
	
	/**
	 * @param aryLblEndRecordTime セットする aryLblStartRecordTime
	 */
	public void setAryLblEndRecordTime(String[] aryLblEndRecordTime) {
		this.aryLblEndRecordTime = getStringArrayClone(aryLblEndRecordTime);
	}
	
	/**
	 * @return txtEndTime
	 */
	public String[] getTxtEndTime() {
		return getStringArrayClone(txtEndTime);
	}
	
	/**
	 * @param txtEndTime セットする txtEndTime
	 */
	public void setTxtEndTime(String[] txtEndTime) {
		this.txtEndTime = getStringArrayClone(txtEndTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPrivateTime
	 */
	public String getAryLblPrivateTime(int idx) {
		return aryLblPrivateTime[idx];
	}
	
	/**
	 * @param aryLblPrivateTime セットする aryLblPrivateTime
	 */
	public void setAryLblPrivateTime(String[] aryLblPrivateTime) {
		this.aryLblPrivateTime = getStringArrayClone(aryLblPrivateTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateTime
	 */
	public String getAryLblLateTime(int idx) {
		return aryLblLateTime[idx];
	}
	
	/**
	 * @param aryLblLateTime セットする aryLblLateTime
	 */
	public void setAryLblLateTime(String[] aryLblLateTime) {
		this.aryLblLateTime = getStringArrayClone(aryLblLateTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLeaveEarlyTime
	 */
	public String getAryLblLeaveEarlyTime(int idx) {
		return aryLblLeaveEarlyTime[idx];
	}
	
	/**
	 * @param aryLblLeaveEarlyTime セットする aryLblLeaveEarlyTime
	 */
	public void setAryLblLeaveEarlyTime(String[] aryLblLeaveEarlyTime) {
		this.aryLblLeaveEarlyTime = getStringArrayClone(aryLblLeaveEarlyTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateLeaveEarlyTime
	 */
	public String getAryLblLateLeaveEarlyTime(int idx) {
		return aryLblLateLeaveEarlyTime[idx];
	}
	
	/**
	 * @param aryLblLateLeaveEarlyTime セットする aryLblLateLeaveEarlyTime
	 */
	public void setAryLblLateLeaveEarlyTime(String[] aryLblLateLeaveEarlyTime) {
		this.aryLblLateLeaveEarlyTime = getStringArrayClone(aryLblLateLeaveEarlyTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTimeIn
	 */
	public String getAryLblOverTimeIn(int idx) {
		return aryLblOverTimeIn[idx];
	}
	
	/**
	 * @param aryLblOverTimeIn セットする aryLblOverTimeIn
	 */
	public void setAryLblOverTimeIn(String[] aryLblOverTimeIn) {
		this.aryLblOverTimeIn = getStringArrayClone(aryLblOverTimeIn);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryOvertimeStyle
	 */
	public String getAryOvertimeStyle(int idx) {
		return aryOvertimeStyle[idx];
	}
	
	/**
	 * @param aryOvertimeStyle セットする aryOvertimeStyle
	 */
	public void setAryOvertimeStyle(String[] aryOvertimeStyle) {
		this.aryOvertimeStyle = getStringArrayClone(aryOvertimeStyle);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTimeOut
	 */
	public String getAryLblOverTimeOut(int idx) {
		return aryLblOverTimeOut[idx];
	}
	
	/**
	 * @param aryLblOverTimeOut セットする aryLblOverTimeOut
	 */
	public void setAryLblOverTimeOut(String[] aryLblOverTimeOut) {
		this.aryLblOverTimeOut = getStringArrayClone(aryLblOverTimeOut);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkOnHoliday
	 */
	public String getAryLblWorkOnHoliday(int idx) {
		return aryLblWorkOnHoliday[idx];
	}
	
	/**
	 * @param aryLblWorkOnHoliday セットする aryLblWorkOnHoliday
	 */
	public void setAryLblWorkOnHoliday(String[] aryLblWorkOnHoliday) {
		this.aryLblWorkOnHoliday = getStringArrayClone(aryLblWorkOnHoliday);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateNight
	 */
	public String getAryLblLateNight(int idx) {
		return aryLblLateNight[idx];
	}
	
	/**
	 * @param aryLblLateNight セットする aryLblLateNight
	 */
	public void setAryLblLateNight(String[] aryLblLateNight) {
		this.aryLblLateNight = getStringArrayClone(aryLblLateNight);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblState
	 */
	public String getAryLblState(int idx) {
		return aryLblState[idx];
	}
	
	/**
	 * 
	 * @param idx インデックス
	 * @return aryLblShortUnpaid。
	 */
	public String getAryLblShortUnpaid(int idx) {
		return aryLblShortUnpaid[idx];
	}
	
	/**
	 * @param aryLblShortUnpaid セットする aryLblShortUnpaid。
	 */
	public void setAryLblShortUnpaid(String[] aryLblShortUnpaid) {
		this.aryLblShortUnpaid = getStringArrayClone(aryLblShortUnpaid);
	}
	
	/**
	 * @param aryLblState セットする aryLblState
	 */
	public void setAryLblState(String[] aryLblState) {
		this.aryLblState = getStringArrayClone(aryLblState);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryStateStyle
	 */
	public String getAryStateStyle(int idx) {
		return aryStateStyle[idx];
	}
	
	/**
	 * @param aryStateStyle セットする aryStateStyle
	 */
	public void setAryStateStyle(String[] aryStateStyle) {
		this.aryStateStyle = getStringArrayClone(aryStateStyle);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLinkState
	 */
	public boolean getAryLinkState(int idx) {
		return aryLinkState[idx];
	}
	
	/**
	 * @param aryLinkState セットする aryLinkState
	 */
	public void setAryLinkState(boolean[] aryLinkState) {
		this.aryLinkState = getBooleanArrayClone(aryLinkState);
	}
	
	/**
	 * @param aryWorkflow セットする aryWorkflow
	 */
	public void setAryWorkflow(long[] aryWorkflow) {
		this.aryWorkflow = getLongArrayClone(aryWorkflow);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkflow
	 */
	public long getAryWorkflow(int idx) {
		return aryWorkflow[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCorrection
	 */
	public String getAryLblCorrection(int idx) {
		return aryLblCorrection[idx];
	}
	
	/**
	 * @param aryLblCorrection セットする aryLblCorrection
	 */
	public void setAryLblCorrection(String[] aryLblCorrection) {
		this.aryLblCorrection = getStringArrayClone(aryLblCorrection);
	}
	
	/**
	 * @return lblTotalPrivate
	 */
	public String getLblTotalPrivate() {
		return lblTotalPrivate;
	}
	
	/**
	 * @param lblTotalPrivate セットする lblTotalPrivate
	 */
	public void setLblTotalPrivate(String lblTotalPrivate) {
		this.lblTotalPrivate = lblTotalPrivate;
	}
	
	/**
	 * @return lblTotalLate
	 */
	public String getLblTotalLate() {
		return lblTotalLate;
	}
	
	/**
	 * @param lblTotalLate セットする lblTotalLate
	 */
	public void setLblTotalLate(String lblTotalLate) {
		this.lblTotalLate = lblTotalLate;
	}
	
	/**
	 * @return lblTotalLeaveEarly
	 */
	public String getLblTotalLeaveEarly() {
		return lblTotalLeaveEarly;
	}
	
	/**
	 * @param lblTotalLeaveEarly セットする lblTotalLeaveEarly
	 */
	public void setLblTotalLeaveEarly(String lblTotalLeaveEarly) {
		this.lblTotalLeaveEarly = lblTotalLeaveEarly;
	}
	
	/**
	 * @return lblTotalLateLeaveEarly
	 */
	public String getLblTotalLateLeaveEarly() {
		return lblTotalLateLeaveEarly;
	}
	
	/**
	 * @param lblTotalLateLeaveEarly セットする lblTotalLateLeaveEarly
	 */
	public void setLblTotalLateLeaveEarly(String lblTotalLateLeaveEarly) {
		this.lblTotalLateLeaveEarly = lblTotalLateLeaveEarly;
	}
	
	/**
	 * @return lblTotalOverTimeIn
	 */
	public String getLblTotalOverTimeIn() {
		return lblTotalOverTimeIn;
	}
	
	/**
	 * @param lblTotalOverTimeIn セットする lblTotalOverTimeIn
	 */
	public void setLblTotalOverTimeIn(String lblTotalOverTimeIn) {
		this.lblTotalOverTimeIn = lblTotalOverTimeIn;
	}
	
	/**
	 * @return lblTotalOverTimeOut
	 */
	public String getLblTotalOverTimeOut() {
		return lblTotalOverTimeOut;
	}
	
	/**
	 * @param lblTotalOverTimeOut セットする lblTotalOverTimeOut
	 */
	public void setLblTotalOverTimeOut(String lblTotalOverTimeOut) {
		this.lblTotalOverTimeOut = lblTotalOverTimeOut;
	}
	
	/**
	 * @return lblTotalWorkOnHoliday
	 */
	public String getLblTotalWorkOnHoliday() {
		return lblTotalWorkOnHoliday;
	}
	
	/**
	 * @param lblTotalWorkOnHoliday セットする lblTotalWorkOnHoliday
	 */
	public void setLblTotalWorkOnHoliday(String lblTotalWorkOnHoliday) {
		this.lblTotalWorkOnHoliday = lblTotalWorkOnHoliday;
	}
	
	/**
	 * @return lblTotalLateNight
	 */
	public String getLblTotalLateNight() {
		return lblTotalLateNight;
	}
	
	/**
	 * @param lblTotalLateNight セットする lblTotalLateNight
	 */
	public void setLblTotalLateNight(String lblTotalLateNight) {
		this.lblTotalLateNight = lblTotalLateNight;
	}
	
	/**
	 * @return lblTimesLate
	 */
	public String getLblTimesLate() {
		return lblTimesLate;
	}
	
	/**
	 * @param lblTimesLate セットする lblTimesLate
	 */
	public void setLblTimesLate(String lblTimesLate) {
		this.lblTimesLate = lblTimesLate;
	}
	
	/**
	 * @return lblTimesLeaveEarly
	 */
	public String getLblTimesLeaveEarly() {
		return lblTimesLeaveEarly;
	}
	
	/**
	 * @param lblTimesLeaveEarly セットする lblTimesLeaveEarly
	 */
	public void setLblTimesLeaveEarly(String lblTimesLeaveEarly) {
		this.lblTimesLeaveEarly = lblTimesLeaveEarly;
	}
	
	/**
	 * @return lblTimesWorkOnHoliday
	 */
	public String getLblTimesWorkOnHoliday() {
		return lblTimesWorkOnHoliday;
	}
	
	/**
	 * @param lblTimesWorkOnHoliday セットする lblTimesWorkOnHoliday
	 */
	public void setLblTimesWorkOnHoliday(String lblTimesWorkOnHoliday) {
		this.lblTimesWorkOnHoliday = lblTimesWorkOnHoliday;
	}
	
	/**
	 * @return lblTimesBirthPrescribedSubHolidayday
	 */
	public String getLblTimesBirthPrescribedSubHolidayday() {
		return lblTimesBirthPrescribedSubHolidayday;
	}
	
	/**
	 * @param lblTimesBirthPrescribedSubHolidayday セットする lblTimesBirthPrescribedSubHolidayday
	 */
	public void setLblTimesBirthPrescribedSubHolidayday(String lblTimesBirthPrescribedSubHolidayday) {
		this.lblTimesBirthPrescribedSubHolidayday = lblTimesBirthPrescribedSubHolidayday;
	}
	
	/**
	 * @return lblTimesBirthLegalSubHolidayday
	 */
	public String getLblTimesBirthLegalSubHolidayday() {
		return lblTimesBirthLegalSubHolidayday;
	}
	
	/**
	 * @param lblTimesBirthLegalSubHolidayday セットする lblTimesBirthLegalSubHolidayday
	 */
	public void setLblTimesBirthLegalSubHolidayday(String lblTimesBirthLegalSubHolidayday) {
		this.lblTimesBirthLegalSubHolidayday = lblTimesBirthLegalSubHolidayday;
	}
	
	/**
	 * @return lblTimesBirthMidnightSubHolidayday
	 */
	public String getLblTimesBirthMidnightSubHolidayday() {
		return lblTimesBirthMidnightSubHolidayday;
	}
	
	/**
	 * @param lblTimesBirthMidnightSubHolidayday セットする lblTimesBirthMidnightSubHolidayday
	 */
	public void setLblTimesBirthMidnightSubHolidayday(String lblTimesBirthMidnightSubHolidayday) {
		this.lblTimesBirthMidnightSubHolidayday = lblTimesBirthMidnightSubHolidayday;
	}
	
	/**
	 * @return lblTimesHoliday
	 */
	@Override
	public String getLblTimesHoliday() {
		return lblTimesHoliday;
	}
	
	/**
	 * @param lblTimesHoliday セットする lblTimesHoliday
	 */
	@Override
	public void setLblTimesHoliday(String lblTimesHoliday) {
		this.lblTimesHoliday = lblTimesHoliday;
	}
	
	/**
	 * @return lblTimesPaidHoliday
	 */
	public String getLblTimesPaidHoliday() {
		return lblTimesPaidHoliday;
	}
	
	/**
	 * @param lblTimesPaidHoliday セットする lblTimesPaidHoliday
	 */
	public void setLblTimesPaidHoliday(String lblTimesPaidHoliday) {
		this.lblTimesPaidHoliday = lblTimesPaidHoliday;
	}
	
	/**
	 * @return lblTimesSpecialHoloiday
	 */
	public String getLblTimesSpecialHoloiday() {
		return lblTimesSpecialHoloiday;
	}
	
	/**
	 * @param lblTimesSpecialHoloiday セットする lblTimesSpecialHoloiday
	 */
	public void setLblTimesSpecialHoloiday(String lblTimesSpecialHoloiday) {
		this.lblTimesSpecialHoloiday = lblTimesSpecialHoloiday;
	}
	
	/**
	 * @return lblTimesSubstitute
	 */
	public String getLblTimesSubstitute() {
		return lblTimesSubstitute;
	}
	
	/**
	 * @param lblTimesSubstitute セットする lblTimesSubstitute
	 */
	public void setLblTimesSubstitute(String lblTimesSubstitute) {
		this.lblTimesSubstitute = lblTimesSubstitute;
	}
	
	/**
	 * @return lblTimesSubHoliday
	 */
	public String getLblTimesSubHoliday() {
		return lblTimesSubHoliday;
	}
	
	/**
	 * @param lblTimesSubHoliday セットする lblTimesSubHoliday
	 */
	public void setLblTimesSubHoliday(String lblTimesSubHoliday) {
		this.lblTimesSubHoliday = lblTimesSubHoliday;
	}
	
	/**
	 * @return lblTimesAbsence
	 */
	public String getLblTimesAbsence() {
		return lblTimesAbsence;
	}
	
	/**
	 * @param lblTimesAbsence セットする lblTimesAbsence
	 */
	public void setLblTimesAbsence(String lblTimesAbsence) {
		this.lblTimesAbsence = lblTimesAbsence;
	}
	
	/**
	 * @return lblShortUnpaidTotal
	 */
	public String getLblShortUnpaidTotal() {
		return lblShortUnpaidTotal;
	}
	
	/**
	 * @param lblShortUnpaidTotal セットする lblShortUnpaidTotal
	 */
	public void setLblShortUnpaidTotal(String lblShortUnpaidTotal) {
		this.lblShortUnpaidTotal = lblShortUnpaidTotal;
	}
	
	/**
	 * @return lblTimesMinutelyHolidayA
	 */
	public String getLblTimesMinutelyHolidayA() {
		return lblTimesMinutelyHolidayA;
	}
	
	/**
	 * @param lblTimesMinutelyHolidayA セットする lblTimesMinutelyHolidayA
	 */
	public void setLblTimesMinutelyHolidayA(String lblTimesMinutelyHolidayA) {
		this.lblTimesMinutelyHolidayA = lblTimesMinutelyHolidayA;
	}
	
	/**
	 * @return lblTimesOverTimeWork
	 */
	public String getLblTimesOverTimeWork() {
		return lblTimesOverTimeWork;
	}
	
	/**
	 * @return lblTimesPaidHolidayTime
	 */
	public String getLblTimesPaidHolidayTime() {
		return lblTimesPaidHolidayTime;
	}
	
	/**
	 * @return lblTimesOtherHoloiday
	 */
	public String getLblTimesOtherHoloiday() {
		return lblTimesOtherHoloiday;
	}
	
	/**
	 * @param lblTimesOverTimeWork セットする lblTimesOverTimeWork
	 */
	public void setLblTimesOverTimeWork(String lblTimesOverTimeWork) {
		this.lblTimesOverTimeWork = lblTimesOverTimeWork;
	}
	
	/**
	 * @param lblTimesPaidHolidayTime セットする lblTimesPaidHolidayTime
	 */
	public void setLblTimesPaidHolidayTime(String lblTimesPaidHolidayTime) {
		this.lblTimesPaidHolidayTime = lblTimesPaidHolidayTime;
	}
	
	/**
	 * @param lblTimesOtherHoloiday セットする lblTimesOtherHoloiday
	 */
	public void setLblTimesOtherHoloiday(String lblTimesOtherHoloiday) {
		this.lblTimesOtherHoloiday = lblTimesOtherHoloiday;
	}
	
	/**
	 * @return aryDate
	 */
	public String[] getAryDate() {
		return getStringArrayClone(aryDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryDate
	 */
	public String getAryDate(int idx) {
		return aryDate[idx];
	}
	
	/**
	 * @param aryDate セットする aryDate
	 */
	public void setAryDate(String[] aryDate) {
		this.aryDate = getStringArrayClone(aryDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryState
	 */
	public boolean getAryCheckState(int idx) {
		return aryCheckState[idx];
	}
	
	/**
	 * @param aryCheckState セットする aryCheckState
	 */
	public void setAryCheckState(boolean[] aryCheckState) {
		this.aryCheckState = getBooleanArrayClone(aryCheckState);
	}
	
	/**
	 * @return isTotalButtonVisible
	 */
	public boolean isTotalButtonVisible() {
		return isTotalButtonVisible;
	}
	
	/**
	 * @param isTotalButtonVisible セットする isTotalButtonVisible
	 */
	public void setTotalButtonVisible(boolean isTotalButtonVisible) {
		this.isTotalButtonVisible = isTotalButtonVisible;
	}
	
	/**
	 * @return isLblStartRecordTime
	 */
	public boolean isLblStartRecordTime() {
		return isLblStartRecordTime;
	}
	
	/**
	 * @param isLblStartRecordTime セットする isLblStartRecordTime
	 */
	public void setLblStartRecordTime(boolean isLblStartRecordTime) {
		this.isLblStartRecordTime = isLblStartRecordTime;
	}
	
	/**
	 * @return isLblEndRecordTime
	 */
	public boolean isLblEndRecordTime() {
		return isLblEndRecordTime;
	}
	
	/**
	 * @param isLblEndRecordTime セットする isLblEndRecordTime
	 */
	public void setLblEndRecordTime(boolean isLblEndRecordTime) {
		this.isLblEndRecordTime = isLblEndRecordTime;
	}
	
	/**
	 * @return lblExtraFiled
	 */
	public String getLblExtraFiled() {
		return lblExtraFiled;
	}
	
	/**
	 * @param lblExtraFiled セットする lblExtraFiled
	 */
	public void setLblExtraFiled(String lblExtraFiled) {
		this.lblExtraFiled = lblExtraFiled;
	}
	
	/**
	 * @return addonJsps
	 */
	public String[] getAddonJsps() {
		return getStringArrayClone(addonJsps);
	}
	
	/**
	 * @param addonJsps セットする addonJsps
	 */
	public void setAddonJsps(String[] addonJsps) {
		this.addonJsps = getStringArrayClone(addonJsps);
	}
	
	/**
	 * @return addonParameters
	 */
	public Map<String, String[]> getAddonParameters() {
		return addonParameters;
	}
	
	/**
	 * @param addonParameters セットする addonParameters
	 */
	public void setAddonParameters(Map<String, String[]> addonParameters) {
		this.addonParameters = addonParameters;
	}
	
	/**
	 * 追加パラメータを設定する。<br>
	 * @param key    キー
	 * @param values 値
	 */
	public void putAddonParameters(String key, String[] values) {
		addonParameters.put(key, values);
	}
	
	/**
	 * 追加パラメータを設定する。<br>
	 * @param key   キー
	 * @param value 値
	 */
	public void putAddonParameters(String key, String value) {
		String[] values = { value };
		addonParameters.put(key, values);
	}
	
	/**
	 * 追加パラメータを取得する。<br>
	 * @param key キー
	 * @return 追加パラメータ
	 */
	public String[] getAddonParameters(String key) {
		return addonParameters.get(key);
	}
	
	/**
	 * 追加パラメータを取得する。<br>
	 * @param key キー
	 * @return 追加パラメータ
	 */
	public String getAddonParameter(String key) {
		String[] addonParameter = addonParameters.get(key);
		if (addonParameter == null || addonParameter.length == 0) {
			return null;
		}
		return addonParameter[0];
	}
	
	/**
	 * @return addonColumnNames
	 */
	public Map<String, String> getAddonColumnName() {
		return addonColumnNames;
	}
	
	/**
	 * アドオン列項目名を設定する。<br>
	 * @param key   キー
	 * @param value 値
	 */
	public void putAddonColumnName(String key, String value) {
		addonColumnNames.put(key, value);
	}
	
	/**
	 * @return addonColumnValues
	 */
	public Map<String, String[]> getAddonColumnValues() {
		return addonColumnValues;
	}
	
	/**
	 * アドオン列項目値を設定する。<br>
	 * @param key    キー
	 * @param values アドオン列項目値
	 */
	public void putAddonColumnValues(String key, String[] values) {
		addonColumnValues.put(key, values);
	}
	
	/**
	 * @return addonColumnClasses
	 */
	public Map<String, String[]> getAddonColumnClasses() {
		return addonColumnClasses;
	}
	
	/**
	 * アドオン列項目クラス文字群を設定する。<br>
	 * @param key    キー
	 * @param values アドオン列項目クラス文字群
	 */
	public void putAddonColumnClasses(String key, String[] values) {
		addonColumnClasses.put(key, values);
	}
	
}
