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
package jp.mosp.time.settings.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 勤務形態の情報を格納する。
 */
public class WorkTypeCardVo extends TimeSettingVo {
	
	private static final long		serialVersionUID	= -6430605613779135771L;
	
	private String					txtWorkTypeCode;
	private String					txtWorkTypeName;
	private String					txtWorkTypeAbbr;
	private String					txtWorkStartHour;
	private String					txtWorkStartMinute;
	private String					txtWorkEndHour;
	private String					txtWorkEndMinute;
	private String					txtRestTimeHour;
	private String					txtRestTimeMinute;
	private String					txtWorkTimeHour;
	private String					txtWorkTimeMinute;
	private String					txtRestStart1Hour;
	private String					txtRestStart1Minute;
	private String					txtRestEnd1Hour;
	private String					txtRestEnd1Minute;
	private String					txtRestStart2Hour;
	private String					txtRestStart2Minute;
	private String					txtRestEnd2Hour;
	private String					txtRestEnd2Minute;
	private String					txtRestStart3Hour;
	private String					txtRestStart3Minute;
	private String					txtRestEnd3Hour;
	private String					txtRestEnd3Minute;
	private String					txtRestStart4Hour;
	private String					txtRestStart4Minute;
	private String					txtRestEnd4Hour;
	private String					txtRestEnd4Minute;
	private String					txtFrontStartHour;
	private String					txtFrontStartMinute;
	private String					txtFrontEndHour;
	private String					txtFrontEndMinute;
	private String					txtBackStartHour;
	private String					txtBackStartMinute;
	private String					txtBackEndHour;
	private String					txtBackEndMinute;
	private String					txtOverBeforeHour;
	private String					txtOverBeforeMinute;
	private String					txtOverPerHour;
	private String					txtOverPerMinute;
	private String					txtOverRestHour;
	private String					txtOverRestMinute;
	private String					txtHalfRestHour;
	private String					txtHalfRestMinute;
	private String					txtHalfRestStartHour;
	private String					txtHalfRestStartMinute;
	private String					txtHalfRestEndHour;
	private String					txtHalfRestEndMinute;
	private String					pltAutoBeforeOverWork;
	
	/**
	 * 直行。
	 */
	private String					ckbDirectStart;
	
	/**
	 * 直帰。
	 */
	private String					ckbDirectEnd;
	
	/**
	 * 割増休憩除外。
	 */
	private String					pltMidnightRestExclusion;
	
	/**
	 * 時短時間1開始時刻(時)。
	 */
	private String					txtShort1StartHour;
	
	/**
	 * 時短時間1開始時刻(分)。
	 */
	private String					txtShort1StartMinute;
	
	/**
	 * 時短時間1終了時刻(時)。
	 */
	private String					txtShort1EndHour;
	
	/**
	 * 時短時間1終了時刻(分)。
	 */
	private String					txtShort1EndMinute;
	
	/**
	 * 時短時間1給与区分。
	 */
	private String					pltShort1Type;
	
	/**
	 * 時短時間2開始時刻(時)。
	 */
	private String					txtShort2StartHour;
	
	/**
	 * 時短時間2開始時刻(分)。
	 */
	private String					txtShort2StartMinute;
	
	/**
	 * 時短時間2終了時刻(時)。
	 */
	private String					txtShort2EndHour;
	
	/**
	 * 時短時間2終了時刻(分)。
	 */
	private String					txtShort2EndMinute;
	
	/**
	 * 時短時間2給与区分。
	 */
	private String					pltShort2Type;
	
	private String					lblWorkTime;
	private String					lblRestTime;
	
	private long[]					tmmWorkTypeItemId;
	
	/**
	 * 勤怠設定追加JSPリスト。<br>
	 */
	private List<String>			addonJsps;
	
	/**
	 * 勤怠設定追加パラメータ群(キー：パラメータ名)。<br>
	 */
	private Map<String, String>		addonParams;
	
	/**
	 * 勤怠設定追加パラメータ配列群(キー：パラメータ名)。<br>
	 */
	private Map<String, String[]>	addonArrays;
	
	/**
	 * 勤怠設定追加プルダウン配列群(キー：パラメータ名)。<br>
	 */
	private Map<String, String[][]>	addonAryPlts;
	
	
	/**
	 * {@link TimeSettingVo#TimeSettingVo()}を実行する。<br>
	 */
	public WorkTypeCardVo() {
		super();
		// 初期化処理
		addonJsps = new ArrayList<String>();
		addonParams = new HashMap<String, String>();
		addonArrays = new HashMap<String, String[]>();
		addonAryPlts = new HashMap<String, String[][]>();
	}
	
	/**
	 * @return txtWorkTypeCode
	 */
	public String getTxtWorkTypeCode() {
		return txtWorkTypeCode;
	}
	
	/**
	 * @param txtWorkTypeCode セットする txtWorkTypeCode
	 */
	public void setTxtWorkTypeCode(String txtWorkTypeCode) {
		this.txtWorkTypeCode = txtWorkTypeCode;
	}
	
	/**
	 * @return txtWorkTypeName
	 */
	public String getTxtWorkTypeName() {
		return txtWorkTypeName;
	}
	
	/**
	 * @param txtWorkTypeName セットする txtWorkTypeName
	 */
	public void setTxtWorkTypeName(String txtWorkTypeName) {
		this.txtWorkTypeName = txtWorkTypeName;
	}
	
	/**
	 * @return txtWorkTypeAbbr
	 */
	public String getTxtWorkTypeAbbr() {
		return txtWorkTypeAbbr;
	}
	
	/**
	 * @param txtWorkTypeAbbr セットする txtWorkTypeAbbr
	 */
	public void setTxtWorkTypeAbbr(String txtWorkTypeAbbr) {
		this.txtWorkTypeAbbr = txtWorkTypeAbbr;
	}
	
	/**
	 * @return txtWorkStartHour
	 */
	public String getTxtWorkStartHour() {
		return txtWorkStartHour;
	}
	
	/**
	 * @param txtWorkStartHour セットする txtWorkStartHour
	 */
	public void setTxtWorkStartHour(String txtWorkStartHour) {
		this.txtWorkStartHour = txtWorkStartHour;
	}
	
	/**
	 * @return txtWorkStartMinute
	 */
	public String getTxtWorkStartMinute() {
		return txtWorkStartMinute;
	}
	
	/**
	 * @param txtWorkStartMinute セットする txtWorkStartMinute
	 */
	public void setTxtWorkStartMinute(String txtWorkStartMinute) {
		this.txtWorkStartMinute = txtWorkStartMinute;
	}
	
	/**
	 * @return txtWorkEndHour
	 */
	public String getTxtWorkEndHour() {
		return txtWorkEndHour;
	}
	
	/**
	 * @param txtWorkEndHour セットする txtWorkEndHour
	 */
	public void setTxtWorkEndHour(String txtWorkEndHour) {
		this.txtWorkEndHour = txtWorkEndHour;
	}
	
	/**
	 * @return txtWorkEndMinute
	 */
	public String getTxtWorkEndMinute() {
		return txtWorkEndMinute;
	}
	
	/**
	 * @param txtWorkEndMinute セットする txtWorkEndMinute
	 */
	public void setTxtWorkEndMinute(String txtWorkEndMinute) {
		this.txtWorkEndMinute = txtWorkEndMinute;
	}
	
	/**
	 * @return txtRestTimeHour
	 */
	public String getTxtRestTimeHour() {
		return txtRestTimeHour;
	}
	
	/**
	 * @param txtRestTimeHour セットする txtRestTimeHour
	 */
	public void setTxtRestTimeHour(String txtRestTimeHour) {
		this.txtRestTimeHour = txtRestTimeHour;
	}
	
	/**
	 * @return txtRestTimeMinute
	 */
	public String getTxtRestTimeMinute() {
		return txtRestTimeMinute;
	}
	
	/**
	 * @param txtRestTimeMinute セットする txtRestTimeMinute
	 */
	public void setTxtRestTimeMinute(String txtRestTimeMinute) {
		this.txtRestTimeMinute = txtRestTimeMinute;
	}
	
	/**
	 * @return txtWorkTimeHour
	 */
	public String getTxtWorkTimeHour() {
		return txtWorkTimeHour;
	}
	
	/**
	 * @param txtWorkTimeHour セットする txtWorkTimeHour
	 */
	public void setTxtWorkTimeHour(String txtWorkTimeHour) {
		this.txtWorkTimeHour = txtWorkTimeHour;
	}
	
	/**
	 * @return txtWorkTimeMinute
	 */
	public String getTxtWorkTimeMinute() {
		return txtWorkTimeMinute;
	}
	
	/**
	 * @param txtWorkTimeMinute セットする txtWorkTimeMinute
	 */
	public void setTxtWorkTimeMinute(String txtWorkTimeMinute) {
		this.txtWorkTimeMinute = txtWorkTimeMinute;
	}
	
	/**
	 * @return txtRestStart1Hour
	 */
	public String getTxtRestStart1Hour() {
		return txtRestStart1Hour;
	}
	
	/**
	 * @param txtRestStart1Hour セットする txtRestStart1Hour
	 */
	public void setTxtRestStart1Hour(String txtRestStart1Hour) {
		this.txtRestStart1Hour = txtRestStart1Hour;
	}
	
	/**
	 * @return txtRestStart1Minute
	 */
	public String getTxtRestStart1Minute() {
		return txtRestStart1Minute;
	}
	
	/**
	 * @param txtRestStart1Minute セットする txtRestStart1Minute
	 */
	public void setTxtRestStart1Minute(String txtRestStart1Minute) {
		this.txtRestStart1Minute = txtRestStart1Minute;
	}
	
	/**
	 * @return txtRestEnd1Hour
	 */
	public String getTxtRestEnd1Hour() {
		return txtRestEnd1Hour;
	}
	
	/**
	 * @param txtRestEnd1Hour セットする txtRestEnd1Hour
	 */
	public void setTxtRestEnd1Hour(String txtRestEnd1Hour) {
		this.txtRestEnd1Hour = txtRestEnd1Hour;
	}
	
	/**
	 * @return txtRestEnd1Minute
	 */
	public String getTxtRestEnd1Minute() {
		return txtRestEnd1Minute;
	}
	
	/**
	 * @param txtRestEnd1Minute セットする txtRestEnd1Minute
	 */
	public void setTxtRestEnd1Minute(String txtRestEnd1Minute) {
		this.txtRestEnd1Minute = txtRestEnd1Minute;
	}
	
	/**
	 * @return txtRestStart2Hour
	 */
	public String getTxtRestStart2Hour() {
		return txtRestStart2Hour;
	}
	
	/**
	 * @param txtRestStart2Hour セットする txtRestStart2Hour
	 */
	public void setTxtRestStart2Hour(String txtRestStart2Hour) {
		this.txtRestStart2Hour = txtRestStart2Hour;
	}
	
	/**
	 * @return txtRestStart2Minute
	 */
	public String getTxtRestStart2Minute() {
		return txtRestStart2Minute;
	}
	
	/**
	 * @param txtRestStart2Minute セットする txtRestStart2Minute
	 */
	public void setTxtRestStart2Minute(String txtRestStart2Minute) {
		this.txtRestStart2Minute = txtRestStart2Minute;
	}
	
	/**
	 * @return txtRestEnd2Hour
	 */
	public String getTxtRestEnd2Hour() {
		return txtRestEnd2Hour;
	}
	
	/**
	 * @param txtRestEnd2Hour セットする txtRestEnd2Hour
	 */
	public void setTxtRestEnd2Hour(String txtRestEnd2Hour) {
		this.txtRestEnd2Hour = txtRestEnd2Hour;
	}
	
	/**
	 * @return txtRestEnd2Minute
	 */
	public String getTxtRestEnd2Minute() {
		return txtRestEnd2Minute;
	}
	
	/**
	 * @param txtRestEnd2Minute セットする txtRestEnd2Minute
	 */
	public void setTxtRestEnd2Minute(String txtRestEnd2Minute) {
		this.txtRestEnd2Minute = txtRestEnd2Minute;
	}
	
	/**
	 * @return txtRestStart3Hour
	 */
	public String getTxtRestStart3Hour() {
		return txtRestStart3Hour;
	}
	
	/**
	 * @param txtRestStart3Hour セットする txtRestStart3Hour
	 */
	public void setTxtRestStart3Hour(String txtRestStart3Hour) {
		this.txtRestStart3Hour = txtRestStart3Hour;
	}
	
	/**
	 * @return txtRestStart3Minute
	 */
	public String getTxtRestStart3Minute() {
		return txtRestStart3Minute;
	}
	
	/**
	 * @param txtRestStart3Minute セットする txtRestStart3Minute
	 */
	public void setTxtRestStart3Minute(String txtRestStart3Minute) {
		this.txtRestStart3Minute = txtRestStart3Minute;
	}
	
	/**
	 * @return txtRestEnd3Hour
	 */
	public String getTxtRestEnd3Hour() {
		return txtRestEnd3Hour;
	}
	
	/**
	 * @param txtRestEnd3Hour セットする txtRestEnd3Hour
	 */
	public void setTxtRestEnd3Hour(String txtRestEnd3Hour) {
		this.txtRestEnd3Hour = txtRestEnd3Hour;
	}
	
	/**
	 * @return txtRestEnd3Minute
	 */
	public String getTxtRestEnd3Minute() {
		return txtRestEnd3Minute;
	}
	
	/**
	 * @param txtRestEnd3Minute セットする txtRestEnd3Minute
	 */
	public void setTxtRestEnd3Minute(String txtRestEnd3Minute) {
		this.txtRestEnd3Minute = txtRestEnd3Minute;
	}
	
	/**
	 * @return txtRestStart4Hour
	 */
	public String getTxtRestStart4Hour() {
		return txtRestStart4Hour;
	}
	
	/**
	 * @param txtRestStart4Hour セットする txtRestStart4Hour
	 */
	public void setTxtRestStart4Hour(String txtRestStart4Hour) {
		this.txtRestStart4Hour = txtRestStart4Hour;
	}
	
	/**
	 * @return txtRestStart4Minute
	 */
	public String getTxtRestStart4Minute() {
		return txtRestStart4Minute;
	}
	
	/**
	 * @param txtRestStart4Minute セットする txtRestStart4Minute
	 */
	public void setTxtRestStart4Minute(String txtRestStart4Minute) {
		this.txtRestStart4Minute = txtRestStart4Minute;
	}
	
	/**
	 * @return txtRestEnd4Hour
	 */
	public String getTxtRestEnd4Hour() {
		return txtRestEnd4Hour;
	}
	
	/**
	 * @param txtRestEnd4Hour セットする txtRestEnd4Hour
	 */
	public void setTxtRestEnd4Hour(String txtRestEnd4Hour) {
		this.txtRestEnd4Hour = txtRestEnd4Hour;
	}
	
	/**
	 * @return txtRestEnd4Minute
	 */
	public String getTxtRestEnd4Minute() {
		return txtRestEnd4Minute;
	}
	
	/**
	 * @param txtRestEnd4Minute セットする txtRestEnd4Minute
	 */
	public void setTxtRestEnd4Minute(String txtRestEnd4Minute) {
		this.txtRestEnd4Minute = txtRestEnd4Minute;
	}
	
	/**
	 * @return txtFrontStartHour
	 */
	public String getTxtFrontStartHour() {
		return txtFrontStartHour;
	}
	
	/**
	 * @param txtFrontStartHour セットする txtFrontStartHour
	 */
	public void setTxtFrontStartHour(String txtFrontStartHour) {
		this.txtFrontStartHour = txtFrontStartHour;
	}
	
	/**
	 * @return txtFrontStartMinute
	 */
	public String getTxtFrontStartMinute() {
		return txtFrontStartMinute;
	}
	
	/**
	 * @param txtFrontStartMinute セットする txtFrontStartMinute
	 */
	public void setTxtFrontStartMinute(String txtFrontStartMinute) {
		this.txtFrontStartMinute = txtFrontStartMinute;
	}
	
	/**
	 * @return txtFrontEndHour
	 */
	public String getTxtFrontEndHour() {
		return txtFrontEndHour;
	}
	
	/**
	 * @param txtFrontEndHour セットする txtFrontEndHour
	 */
	public void setTxtFrontEndHour(String txtFrontEndHour) {
		this.txtFrontEndHour = txtFrontEndHour;
	}
	
	/**
	 * @return txtFrontEndMinute
	 */
	public String getTxtFrontEndMinute() {
		return txtFrontEndMinute;
	}
	
	/**
	 * @param txtFrontEndMinute セットする txtFrontEndMinute
	 */
	public void setTxtFrontEndMinute(String txtFrontEndMinute) {
		this.txtFrontEndMinute = txtFrontEndMinute;
	}
	
	/**
	 * @return txtBackStartHour
	 */
	public String getTxtBackStartHour() {
		return txtBackStartHour;
	}
	
	/**
	 * @param txtBackStartHour セットする txtBackStartHour
	 */
	public void setTxtBackStartHour(String txtBackStartHour) {
		this.txtBackStartHour = txtBackStartHour;
	}
	
	/**
	 * @return txtBackStartMinute
	 */
	public String getTxtBackStartMinute() {
		return txtBackStartMinute;
	}
	
	/**
	 * @param txtBackStartMinute セットする txtBackStartMinute
	 */
	public void setTxtBackStartMinute(String txtBackStartMinute) {
		this.txtBackStartMinute = txtBackStartMinute;
	}
	
	/**
	 * @return txtBackEndHour
	 */
	public String getTxtBackEndHour() {
		return txtBackEndHour;
	}
	
	/**
	 * @param txtBackEndHour セットする txtBackEndHour
	 */
	public void setTxtBackEndHour(String txtBackEndHour) {
		this.txtBackEndHour = txtBackEndHour;
	}
	
	/**
	 * @return txtBackEndMinute
	 */
	public String getTxtBackEndMinute() {
		return txtBackEndMinute;
	}
	
	/**
	 * @param txtBackEndMinute セットする txtBackEndMinute
	 */
	public void setTxtBackEndMinute(String txtBackEndMinute) {
		this.txtBackEndMinute = txtBackEndMinute;
	}
	
	/**
	 * @return txtOverBeforeHour
	 */
	public String getTxtOverBeforeHour() {
		return txtOverBeforeHour;
	}
	
	/**
	 * @param txtOverBeforeHour セットする txtOverBeforeHour
	 */
	public void setTxtOverBeforeHour(String txtOverBeforeHour) {
		this.txtOverBeforeHour = txtOverBeforeHour;
	}
	
	/**
	 * @return txtOverBeforeMinute
	 */
	public String getTxtOverBeforeMinute() {
		return txtOverBeforeMinute;
	}
	
	/**
	 * @param txtOverBeforeMinute セットする txtOverBeforeMinute
	 */
	public void setTxtOverBeforeMinute(String txtOverBeforeMinute) {
		this.txtOverBeforeMinute = txtOverBeforeMinute;
	}
	
	/**
	 * @return txtOverPerHour
	 */
	public String getTxtOverPerHour() {
		return txtOverPerHour;
	}
	
	/**
	 * @param txtOverPerHour セットする txtOverPerHour
	 */
	public void setTxtOverPerHour(String txtOverPerHour) {
		this.txtOverPerHour = txtOverPerHour;
	}
	
	/**
	 * @return txtOverPerMinute
	 */
	public String getTxtOverPerMinute() {
		return txtOverPerMinute;
	}
	
	/**
	 * @param txtOverPerMinute セットする txtOverPerMinute
	 */
	public void setTxtOverPerMinute(String txtOverPerMinute) {
		this.txtOverPerMinute = txtOverPerMinute;
	}
	
	/**
	 * @return txtOverRestHour
	 */
	public String getTxtOverRestHour() {
		return txtOverRestHour;
	}
	
	/**
	 * @param txtOverRestHour セットする txtOverRestHour
	 */
	public void setTxtOverRestHour(String txtOverRestHour) {
		this.txtOverRestHour = txtOverRestHour;
	}
	
	/**
	 * @return txtOverRestMinute
	 */
	public String getTxtOverRestMinute() {
		return txtOverRestMinute;
	}
	
	/**
	 * @param txtOverRestMinute セットする txtOverRestMinute
	 */
	public void setTxtOverRestMinute(String txtOverRestMinute) {
		this.txtOverRestMinute = txtOverRestMinute;
	}
	
	/**
	 * @return txtHalfRestHour
	 */
	public String getTxtHalfRestHour() {
		return txtHalfRestHour;
	}
	
	/**
	 * @param txtHalfRestHour セットする txtHalfRestHour
	 */
	public void setTxtHalfRestHour(String txtHalfRestHour) {
		this.txtHalfRestHour = txtHalfRestHour;
	}
	
	/**
	 * @return txtHalfRestMinute
	 */
	public String getTxtHalfRestMinute() {
		return txtHalfRestMinute;
	}
	
	/**
	 * @param txtHalfRestMinute セットする txtHalfRestMinute
	 */
	public void setTxtHalfRestMinute(String txtHalfRestMinute) {
		this.txtHalfRestMinute = txtHalfRestMinute;
	}
	
	/**
	 * @return txtHalfRestStartHour
	 */
	public String getTxtHalfRestStartHour() {
		return txtHalfRestStartHour;
	}
	
	/**
	 * @param txtHalfRestStartHour セットする txtHalfRestStartHour
	 */
	public void setTxtHalfRestStartHour(String txtHalfRestStartHour) {
		this.txtHalfRestStartHour = txtHalfRestStartHour;
	}
	
	/**
	 * @return txtHalfRestStartMinute
	 */
	public String getTxtHalfRestStartMinute() {
		return txtHalfRestStartMinute;
	}
	
	/**
	 * @param txtHalfRestStartMinute セットする txtHalfRestStartMinute
	 */
	public void setTxtHalfRestStartMinute(String txtHalfRestStartMinute) {
		this.txtHalfRestStartMinute = txtHalfRestStartMinute;
	}
	
	/**
	 * @return txtHalfRestEndHour
	 */
	public String getTxtHalfRestEndHour() {
		return txtHalfRestEndHour;
	}
	
	/**
	 * @param txtHalfRestEndHour セットする txtHalfRestEndHour
	 */
	public void setTxtHalfRestEndHour(String txtHalfRestEndHour) {
		this.txtHalfRestEndHour = txtHalfRestEndHour;
	}
	
	/**
	 * @return txtHalfRestEndMinute
	 */
	public String getTxtHalfRestEndMinute() {
		return txtHalfRestEndMinute;
	}
	
	/**
	 * @param txtHalfRestEndMinute セットする txtHalfRestEndMinute
	 */
	public void setTxtHalfRestEndMinute(String txtHalfRestEndMinute) {
		this.txtHalfRestEndMinute = txtHalfRestEndMinute;
	}
	
	/**
	 * @return ckbDirectStart
	 */
	public String getCkbDirectStart() {
		return ckbDirectStart;
	}
	
	/**
	 * @param ckbDirectStart セットする ckbDirectStart
	 */
	public void setCkbDirectStart(String ckbDirectStart) {
		this.ckbDirectStart = ckbDirectStart;
	}
	
	/**
	 * @return ckbDirectEnd
	 */
	public String getCkbDirectEnd() {
		return ckbDirectEnd;
	}
	
	/**
	 * @param ckbDirectEnd セットする ckbDirectEnd
	 */
	public void setCkbDirectEnd(String ckbDirectEnd) {
		this.ckbDirectEnd = ckbDirectEnd;
	}
	
	/**
	 * @return pltMidnightRestExclusion
	 */
	public String getPltMidnightRestExclusion() {
		return pltMidnightRestExclusion;
	}
	
	/**
	 * @param pltMidnightRestExclusion セットする pltMidnightRestExclusion
	 */
	public void setPltMidnightRestExclusion(String pltMidnightRestExclusion) {
		this.pltMidnightRestExclusion = pltMidnightRestExclusion;
	}
	
	/**
	 * @return txtShort1StartHour
	 */
	public String getTxtShort1StartHour() {
		return txtShort1StartHour;
	}
	
	/**
	 * @param txtShort1StartHour セットする txtShort1StartHour
	 */
	public void setTxtShort1StartHour(String txtShort1StartHour) {
		this.txtShort1StartHour = txtShort1StartHour;
	}
	
	/**
	 * @return txtShort1StartMinute
	 */
	public String getTxtShort1StartMinute() {
		return txtShort1StartMinute;
	}
	
	/**
	 * @param txtShort1StartMinute セットする txtShort1StartMinute
	 */
	public void setTxtShort1StartMinute(String txtShort1StartMinute) {
		this.txtShort1StartMinute = txtShort1StartMinute;
	}
	
	/**
	 * @return txtShort1EndHour
	 */
	public String getTxtShort1EndHour() {
		return txtShort1EndHour;
	}
	
	/**
	 * @param txtShort1EndHour セットする txtShort1EndHour
	 */
	public void setTxtShort1EndHour(String txtShort1EndHour) {
		this.txtShort1EndHour = txtShort1EndHour;
	}
	
	/**
	 * @return txtShort1EndMinute
	 */
	public String getTxtShort1EndMinute() {
		return txtShort1EndMinute;
	}
	
	/**
	 * @param txtShort1EndMinute セットする txtShort1EndMinute
	 */
	public void setTxtShort1EndMinute(String txtShort1EndMinute) {
		this.txtShort1EndMinute = txtShort1EndMinute;
	}
	
	/**
	 * @return pltShort1Type
	 */
	public String getPltShort1Type() {
		return pltShort1Type;
	}
	
	/**
	 * @param pltShort1Type セットする pltShort1Type
	 */
	public void setPltShort1Type(String pltShort1Type) {
		this.pltShort1Type = pltShort1Type;
	}
	
	/**
	 * @return txtShort2StartHour
	 */
	public String getTxtShort2StartHour() {
		return txtShort2StartHour;
	}
	
	/**
	 * @param txtShort2StartHour セットする txtShort2StartHour
	 */
	public void setTxtShort2StartHour(String txtShort2StartHour) {
		this.txtShort2StartHour = txtShort2StartHour;
	}
	
	/**
	 * @return txtShort2StartMinute
	 */
	public String getTxtShort2StartMinute() {
		return txtShort2StartMinute;
	}
	
	/**
	 * @param txtShort2StartMinute セットする txtShort2StartMinute
	 */
	public void setTxtShort2StartMinute(String txtShort2StartMinute) {
		this.txtShort2StartMinute = txtShort2StartMinute;
	}
	
	/**
	 * @return txtShort2EndHour
	 */
	public String getTxtShort2EndHour() {
		return txtShort2EndHour;
	}
	
	/**
	 * @param txtShort2EndHour セットする txtShort2EndHour
	 */
	public void setTxtShort2EndHour(String txtShort2EndHour) {
		this.txtShort2EndHour = txtShort2EndHour;
	}
	
	/**
	 * @return txtShort2EndMinute
	 */
	public String getTxtShort2EndMinute() {
		return txtShort2EndMinute;
	}
	
	/**
	 * @param txtShort2EndMinute セットする txtShort2EndMinute
	 */
	public void setTxtShort2EndMinute(String txtShort2EndMinute) {
		this.txtShort2EndMinute = txtShort2EndMinute;
	}
	
	/**
	 * @return pltShort2Type
	 */
	public String getPltShort2Type() {
		return pltShort2Type;
	}
	
	/**
	 * @param pltShort2Type セットする pltShort2Type
	 */
	public void setPltShort2Type(String pltShort2Type) {
		this.pltShort2Type = pltShort2Type;
	}
	
	/**
	 * @return lblWorkTime
	 */
	public String getLblWorkTime() {
		return lblWorkTime;
	}
	
	/**
	 * @param lblWorkTime セットする lblWoekTime
	 */
	public void setLblWorkTime(String lblWorkTime) {
		this.lblWorkTime = lblWorkTime;
	}
	
	/**
	 * @return lblRestTime
	 */
	public String getLblRestTime() {
		return lblRestTime;
	}
	
	/**
	 * @param lblRestTime セットする lblRestTime
	 */
	public void setLblRestTime(String lblRestTime) {
		this.lblRestTime = lblRestTime;
	}
	
	/**
	 * @param i レコード識別IDのIndex
	 * @return tmmWorkTypeItemId
	 */
	public long getTmmWorkTypeItemId(int i) {
		return tmmWorkTypeItemId[i];
	}
	
	/**
	 * @param tmmWorkTypeItemId セットする tmmWorkTypeItemId
	 * @param i レコード識別IDのIndex
	 */
	public void setTmmWorkTypeItemId(long tmmWorkTypeItemId, int i) {
		this.tmmWorkTypeItemId[i] = tmmWorkTypeItemId;
	}
	
	/**
	 * @param tmmWorkTypeItemId セットする tmmWorkTypeItemId
	 */
	public void setTmmWorkTypeItemId(long[] tmmWorkTypeItemId) {
		this.tmmWorkTypeItemId = getLongArrayClone(tmmWorkTypeItemId);
	}
	
	/**
	 * @return 前残業自動有効区分
	 */
	public String getPltAutoBeforeOverWork() {
		return pltAutoBeforeOverWork;
	}
	
	/**
	 * @param pltAutoBeforeOverWork セットする pltAutoBeforeOverWork
	 */
	public void setPltAutoBeforeOverWork(String pltAutoBeforeOverWork) {
		this.pltAutoBeforeOverWork = pltAutoBeforeOverWork;
	}
	
	/**
	 * @return addonJsps
	 */
	public List<String> getAddonJsps() {
		return addonJsps;
	}
	
	/**
	 * @param addonJsps セットする addonJsps
	 */
	public void setAddonJsps(List<String> addonJsps) {
		this.addonJsps = addonJsps;
	}
	
	/**
	 * @return addonParams
	 */
	public Map<String, String> getAddonParams() {
		return addonParams;
	}
	
	/**
	 * @param name パラメータ名
	 * @return 勤怠設定追加パラメータ
	 */
	public String getAddonParam(String name) {
		return addonParams.get(name);
	}
	
	/**
	 * @param addonParams セットする addonParams
	 */
	public void setAddonParams(Map<String, String> addonParams) {
		this.addonParams = addonParams;
	}
	
	/**
	 * @param name  パラメータ名
	 * @param value パラメータ値
	 */
	public void putAddonParam(String name, String value) {
		addonParams.put(name, value);
	}
	
	/**
	 * @return addonArrays
	 */
	public Map<String, String[]> getAddonArrays() {
		return addonArrays;
	}
	
	/**
	 * @param name パラメータ名
	 * @return 勤怠設定追加パラメータ配列
	 */
	public String[] getAddonArray(String name) {
		return getStringArrayClone(addonArrays.get(name));
	}
	
	/**
	 * @param addonArrays セットする addonArrays
	 */
	public void setAddonArrays(Map<String, String[]> addonArrays) {
		this.addonArrays = addonArrays;
	}
	
	/**
	 * @param name  パラメータ名
	 * @param value パラメータ値
	 */
	public void putAddonArray(String name, String[] value) {
		addonArrays.put(name, getStringArrayClone(value));
	}
	
	/**
	 * @return addonAryPlts
	 */
	public Map<String, String[][]> getAddonAryPlts() {
		return addonAryPlts;
	}
	
	/**
	 * @param name パラメータ名
	 * @return 勤務形態追加パラメータ配列
	 */
	public String[][] getAddonAryPlts(String name) {
		return getStringArrayClone(addonAryPlts.get(name));
	}
	
	/**
	 * @param addonAryPlts セットする addonAryPlts
	 */
	public void setAddonAryPlts(Map<String, String[][]> addonAryPlts) {
		this.addonAryPlts = addonAryPlts;
	}
	
	/**
	 * @param name  パラメータ名
	 * @param array 配列
	 */
	public void putAddonAryPlts(String name, String[][] array) {
		addonAryPlts.put(name, getStringArrayClone(array));
	}
}
