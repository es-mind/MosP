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
package jp.mosp.platform.human.vo;

import java.util.List;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 兼職情報詳細画面の情報を格納する。
 */
public class ConcurrentCardVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= -3548832467621524916L;
	
	private String[]			aryHidPfaHumanConcurrentId;
	private String[]			aryTxtConcurrentStartYear;
	private String[]			aryTxtConcurrentStartMonth;
	private String[]			aryTxtConcurrentStartDay;
	private String[]			aryTxtConcurrentEndYear;
	private String[]			aryTxtConcurrentEndMonth;
	private String[]			aryTxtConcurrentEndDay;
	private String[]			arySectionAbbr;
	private String[]			aryPosition;
	private String[]			aryTxtRemark;
	
	/**
	 * 有効日モード(配列)。<br>
	 * JSPの各行にhidden項目として設定される。<br>
	 */
	private String[]			modeActivateDateArray;
	
	private String[]			aryLblClassRoute;
	private String				lblPositionName;
	
	/**
	 * 有効日決定ボタン名。<br>
	 * JavaScriptの変数として出力される。<br>
	 */
	private String				jsActivateDateButtonName;
	
	/**
	 * 有効日変更状態のプルダウン表示内容。<br>
	 * JavaScriptの変数として出力される。<br>
	 */
	private String				jsDefaultPulldown;
	
	/**
	 * 所属(略称)プルダウンリスト。
	 */
	private List<String[][]>	listAryPltSectionAbbr;
	
	/**
	 * 職位プルダウンリスト。
	 */
	private List<String[][]>	listAryPltPosition;
	
	
	/**
	 * @param aryTxtConcurrentStartYear セットする aryTxtConcurrentStartYear
	 */
	public void setAryTxtConcurrentStartYear(String[] aryTxtConcurrentStartYear) {
		this.aryTxtConcurrentStartYear = getStringArrayClone(aryTxtConcurrentStartYear);
	}
	
	/**
	 * @return aryHidPfaHumanConcurrentId
	 */
	public String[] getAryHidPfaHumanConcurrentId() {
		return getStringArrayClone(aryHidPfaHumanConcurrentId);
	}
	
	/**
	 * @param aryHidPfaHumanConcurrentId セットする aryHidPfaHumanConcurrentId
	 */
	public void setAryHidPfaHumanConcurrentId(String[] aryHidPfaHumanConcurrentId) {
		this.aryHidPfaHumanConcurrentId = getStringArrayClone(aryHidPfaHumanConcurrentId);
	}
	
	/**
	 * @return aryTxtConcurrentStartYear
	 */
	public String[] getAryTxtConcurrentStartYear() {
		return getStringArrayClone(aryTxtConcurrentStartYear);
	}
	
	/**
	 * @param aryTxtConcurrentStartMonth セットする aryTxtConcurrentStartMonth
	 */
	public void setAryTxtConcurrentStartMonth(String[] aryTxtConcurrentStartMonth) {
		this.aryTxtConcurrentStartMonth = getStringArrayClone(aryTxtConcurrentStartMonth);
	}
	
	/**
	 * @return aryTxtConcurrentStartMonth
	 */
	public String[] getAryTxtConcurrentStartMonth() {
		return getStringArrayClone(aryTxtConcurrentStartMonth);
	}
	
	/**
	 * @param aryTxtConcurrentStartDay セットする aryTxtConcurrentStartDay
	 */
	public void setAryTxtConcurrentStartDay(String[] aryTxtConcurrentStartDay) {
		this.aryTxtConcurrentStartDay = getStringArrayClone(aryTxtConcurrentStartDay);
	}
	
	/**
	 * @return aryTxtConcurrentStartDay
	 */
	public String[] getAryTxtConcurrentStartDay() {
		return getStringArrayClone(aryTxtConcurrentStartDay);
	}
	
	/**
	 * @param aryTxtConcurrentEndYear セットする aryTxtConcurrentEndYear
	 */
	public void setAryTxtConcurrentEndYear(String[] aryTxtConcurrentEndYear) {
		this.aryTxtConcurrentEndYear = getStringArrayClone(aryTxtConcurrentEndYear);
	}
	
	/**
	 * @return aryTxtConcurrentEndYear
	 */
	public String[] getAryTxtConcurrentEndYear() {
		return getStringArrayClone(aryTxtConcurrentEndYear);
	}
	
	/**
	 * @param aryTxtConcurrentEndMonth セットする aryTxtConcurrentEndMonth
	 */
	public void setAryTxtConcurrentEndMonth(String[] aryTxtConcurrentEndMonth) {
		this.aryTxtConcurrentEndMonth = getStringArrayClone(aryTxtConcurrentEndMonth);
	}
	
	/**
	 * @return aryTxtConcurrentEndMonth
	 */
	public String[] getAryTxtConcurrentEndMonth() {
		return getStringArrayClone(aryTxtConcurrentEndMonth);
	}
	
	/**
	 * @param aryTxtConcurrentEndDay セットする aryTxtConcurrentEndDay
	 */
	public void setAryTxtConcurrentEndDay(String[] aryTxtConcurrentEndDay) {
		this.aryTxtConcurrentEndDay = getStringArrayClone(aryTxtConcurrentEndDay);
	}
	
	/**
	 * @return aryTxtConcurrentEndDay
	 */
	public String[] getAryTxtConcurrentEndDay() {
		return getStringArrayClone(aryTxtConcurrentEndDay);
	}
	
	/**
	 * @param arySectionAbbr セットする arySectionAbbr
	 */
	public void setArySectionAbbr(String[] arySectionAbbr) {
		this.arySectionAbbr = getStringArrayClone(arySectionAbbr);
	}
	
	/**
	 * @return arySectionAbbr
	 */
	public String[] getArySectionAbbr() {
		return getStringArrayClone(arySectionAbbr);
	}
	
	/**
	 * @param aryPosition セットする aryPosition
	 */
	public void setAryPosition(String[] aryPosition) {
		this.aryPosition = getStringArrayClone(aryPosition);
	}
	
	/**
	 * @return aryPosition
	 */
	public String[] getAryPosition() {
		return getStringArrayClone(aryPosition);
	}
	
	/**
	 * @param aryTxtRemark セットする aryTxtRemark
	 */
	public void setAryTxtRemark(String[] aryTxtRemark) {
		this.aryTxtRemark = getStringArrayClone(aryTxtRemark);
	}
	
	/**
	 * @return aryTxtRemark
	 */
	public String[] getAryTxtRemark() {
		return getStringArrayClone(aryTxtRemark);
	}
	
	/**
	 * @param listAryPltSectionAbbr セットする listAryPltSectionAbbr
	 */
	public void setListAryPltSectionAbbr(List<String[][]> listAryPltSectionAbbr) {
		this.listAryPltSectionAbbr = listAryPltSectionAbbr;
	}
	
	/**
	 * @return listAryPltSectionAbbr
	 */
	public List<String[][]> getListAryPltSectionAbbr() {
		return listAryPltSectionAbbr;
	}
	
	/**
	 * @param listAryPltPosition セットする listAryPltPosition
	 */
	public void setListAryPltPosition(List<String[][]> listAryPltPosition) {
		this.listAryPltPosition = listAryPltPosition;
	}
	
	/**
	 * @return listAryPltPosition
	 */
	public List<String[][]> getListAryPltPosition() {
		return listAryPltPosition;
	}
	
	/**
	 * @return jsActivateDateButtonName
	 */
	public String getJsActivateDateButtonName() {
		return jsActivateDateButtonName;
	}
	
	/**
	 * @param jsActivateDateButtonName セットする jsActivateDateButtonName
	 */
	public void setJsActivateDateButtonName(String jsActivateDateButtonName) {
		this.jsActivateDateButtonName = jsActivateDateButtonName;
	}
	
	/**
	 * @return jsDefaultPulldown
	 */
	public String getJsDefaultPulldown() {
		return jsDefaultPulldown;
	}
	
	/**
	 * @param jsDefaultPulldown セットする jsDefaultPulldown
	 */
	public void setJsDefaultPulldown(String jsDefaultPulldown) {
		this.jsDefaultPulldown = jsDefaultPulldown;
	}
	
	/**
	 * @return modeActivateDateArray
	 */
	public String[] getModeActivateDateArray() {
		return getStringArrayClone(modeActivateDateArray);
	}
	
	/**
	 * @param modeActivateDateArray セットする modeActivateDateArray
	 */
	public void setModeActivateDateArray(String[] modeActivateDateArray) {
		this.modeActivateDateArray = getStringArrayClone(modeActivateDateArray);
	}
	
	/**
	 * @return aryLblClassRoute
	 */
	public String[] getAryLblClassRoute() {
		return getStringArrayClone(aryLblClassRoute);
	}
	
	/**
	 * @param aryLblClassRoute セットする aryLblClassRoute
	 */
	public void setAryLblClassRoute(String[] aryLblClassRoute) {
		this.aryLblClassRoute = getStringArrayClone(aryLblClassRoute);
	}
	
	/**
	 * @return lblPositionName
	 */
	public String getLblPositionName() {
		return lblPositionName;
	}
	
	/**
	 * @param lblPositionName セットする lblPositionName
	 */
	public void setLblPositionName(String lblPositionName) {
		this.lblPositionName = lblPositionName;
	}
	
}
