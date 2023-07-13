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
package jp.mosp.time.settings.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 勤務形態一覧の情報を格納する。
 */
public class WorkTypeListVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 3179687060467860413L;
	
	private String				txtSearchActivateYear;
	private String				txtSearchActivateMonth;
	private String				txtSearchActivateDay;
	private String				txtSearchWorkTypeCode;
	private String				txtSearchWorkTypeName;
	private String				txtSearchWorkTypeAbbr;
	private String				pltSearchInactivate;
	
	private String				txtUpdateActivateYear;
	private String				txtUpdateActivateMonth;
	private String				txtUpdateActivateDay;
	private String				pltUpdateInactivate;
	
	private String[]			aryLblActivateMonth;
	private String[]			aryLblWorkTypeCode;
	private String[]			aryLblWorkTypeName;
	private String[]			aryLblWorkTypeAbbr;
	private String[]			aryLblStartTime;
	private String[]			aryLblEndTime;
	private String[]			aryLblWorkTime;
	private String[]			aryLblRestTime;
	private String[]			aryLblFrontTime;
	private String[]			aryLblBackTime;
	private String[]			aryCkbWorkTypeListId;
	
	
	/**
	 * @return txtSearchActivateYear
	 */
	@Override
	public String getTxtSearchActivateYear() {
		return txtSearchActivateYear;
	}
	
	/**
	 * @param txtSearchActivateYear セットする txtSearchActivateYear
	 */
	@Override
	public void setTxtSearchActivateYear(String txtSearchActivateYear) {
		this.txtSearchActivateYear = txtSearchActivateYear;
	}
	
	/**
	 * @return txtSearchActivateMonth
	 */
	@Override
	public String getTxtSearchActivateMonth() {
		return txtSearchActivateMonth;
	}
	
	/**
	 * @param txtSearchActivateMonth セットする txtSearchActivateMonth
	 */
	@Override
	public void setTxtSearchActivateMonth(String txtSearchActivateMonth) {
		this.txtSearchActivateMonth = txtSearchActivateMonth;
	}
	
	/**
	 * @return txtSearchActivateDay
	 */
	@Override
	public String getTxtSearchActivateDay() {
		return txtSearchActivateDay;
	}
	
	/**
	 * @param txtSearchActivateDay セットする txtSearchActivateDay
	 */
	@Override
	public void setTxtSearchActivateDay(String txtSearchActivateDay) {
		this.txtSearchActivateDay = txtSearchActivateDay;
	}
	
	/**
	 * @return txtSearchWorkTypeCode
	 */
	public String getTxtSearchWorkTypeCode() {
		return txtSearchWorkTypeCode;
	}
	
	/**
	 * @param txtSearchWorkTypeCode セットする txtSearchWorkTypeCode
	 */
	public void setTxtSearchWorkTypeCode(String txtSearchWorkTypeCode) {
		this.txtSearchWorkTypeCode = txtSearchWorkTypeCode;
	}
	
	/**
	 * @return txtSearchWorkTypeName
	 */
	public String getTxtSearchWorkTypeName() {
		return txtSearchWorkTypeName;
	}
	
	/**
	 * @param txtSearchWorkTypeName セットする txtSearchWorkTypeName
	 */
	public void setTxtSearchWorkTypeName(String txtSearchWorkTypeName) {
		this.txtSearchWorkTypeName = txtSearchWorkTypeName;
	}
	
	/**
	 * @return txtSearchWorkTypeAbbr
	 */
	public String getTxtSearchWorkTypeAbbr() {
		return txtSearchWorkTypeAbbr;
	}
	
	/**
	 * @param txtSearchWorkTypeAbbr セットする txtSearchWorkTypeAbbr
	 */
	public void setTxtSearchWorkTypeAbbr(String txtSearchWorkTypeAbbr) {
		this.txtSearchWorkTypeAbbr = txtSearchWorkTypeAbbr;
	}
	
	/**
	 * @return pltSearchInactivate
	 */
	@Override
	public String getPltSearchInactivate() {
		return pltSearchInactivate;
	}
	
	/**
	 * @param pltSearchInactivate セットする pltSearchInactivate
	 */
	@Override
	public void setPltSearchInactivate(String pltSearchInactivate) {
		this.pltSearchInactivate = pltSearchInactivate;
	}
	
	/**
	 * @return txtUpdateActivateYear
	 */
	@Override
	public String getTxtUpdateActivateYear() {
		return txtUpdateActivateYear;
	}
	
	/**
	 * @param txtUpdateActivateYear セットする txtUpdateActivateYear
	 */
	@Override
	public void setTxtUpdateActivateYear(String txtUpdateActivateYear) {
		this.txtUpdateActivateYear = txtUpdateActivateYear;
	}
	
	/**
	 * @return txtUpdateActivateMonth
	 */
	@Override
	public String getTxtUpdateActivateMonth() {
		return txtUpdateActivateMonth;
	}
	
	/**
	 * @param txtUpdateActivateMonth セットする txtUpdateActivateMonth
	 */
	@Override
	public void setTxtUpdateActivateMonth(String txtUpdateActivateMonth) {
		this.txtUpdateActivateMonth = txtUpdateActivateMonth;
	}
	
	/**
	 * @return txtUpdateActivateDay
	 */
	@Override
	public String getTxtUpdateActivateDay() {
		return txtUpdateActivateDay;
	}
	
	/**
	 * @param txtUpdateActivateDay セットする txtUpdateActivateDay
	 */
	@Override
	public void setTxtUpdateActivateDay(String txtUpdateActivateDay) {
		this.txtUpdateActivateDay = txtUpdateActivateDay;
	}
	
	/**
	 * @return pltUpdateInactivate
	 */
	@Override
	public String getPltUpdateInactivate() {
		return pltUpdateInactivate;
	}
	
	/**
	 * @param pltUpdateInactivate セットする pltUpdateInactivate
	 */
	@Override
	public void setPltUpdateInactivate(String pltUpdateInactivate) {
		this.pltUpdateInactivate = pltUpdateInactivate;
	}
	
	/**
	 * @return aryLblActivateMonth
	 */
	public String[] getAryLblActivateMonth() {
		return getStringArrayClone(aryLblActivateMonth);
	}
	
	/**
	 * @param aryLblActivateMonth セットする aryLblActivateMonth
	 */
	public void setAryLblActivateMonth(String[] aryLblActivateMonth) {
		this.aryLblActivateMonth = getStringArrayClone(aryLblActivateMonth);
	}
	
	/**
	 * @return aryLblWorkTypeCode
	 */
	public String[] getAryLblWorkTypeCode() {
		return getStringArrayClone(aryLblWorkTypeCode);
	}
	
	/**
	 * @param aryLblWorkTypeCode セットする aryLblWorkTypeCode
	 */
	public void setAryLblWorkTypeCode(String[] aryLblWorkTypeCode) {
		this.aryLblWorkTypeCode = getStringArrayClone(aryLblWorkTypeCode);
	}
	
	/**
	 * @return aryLblWorkTypeName
	 */
	public String[] getAryLblWorkTypeName() {
		return getStringArrayClone(aryLblWorkTypeName);
	}
	
	/**
	 * @param aryLblWorkTypeName セットする aryLblWorkTypeName
	 */
	public void setAryLblWorkTypeName(String[] aryLblWorkTypeName) {
		this.aryLblWorkTypeName = getStringArrayClone(aryLblWorkTypeName);
	}
	
	/**
	 * @return aryLblWorkTypeAbbr
	 */
	public String[] getAryLblWorkTypeAbbr() {
		return getStringArrayClone(aryLblWorkTypeAbbr);
	}
	
	/**
	 * @param aryLblWorkTypeAbbr セットする aryLblWorkTypeAbbr
	 */
	public void setAryLblWorkTypeAbbr(String[] aryLblWorkTypeAbbr) {
		this.aryLblWorkTypeAbbr = getStringArrayClone(aryLblWorkTypeAbbr);
	}
	
	/**
	 * @return aryLblStartTime
	 */
	public String[] getAryLblStartTime() {
		return getStringArrayClone(aryLblStartTime);
	}
	
	/**
	 * @param aryLblStartTime セットする aryLblStartTime
	 */
	public void setAryLblStartTime(String[] aryLblStartTime) {
		this.aryLblStartTime = getStringArrayClone(aryLblStartTime);
	}
	
	/**
	 * @return aryLblEndTime
	 */
	public String[] getAryLblEndTime() {
		return getStringArrayClone(aryLblEndTime);
	}
	
	/**
	 * @param aryLblEndTime セットする aryLblEndTime
	 */
	public void setAryLblEndTime(String[] aryLblEndTime) {
		this.aryLblEndTime = getStringArrayClone(aryLblEndTime);
	}
	
	/**
	 * @return aryLblWorkTime
	 */
	public String[] getAryLblWorkTime() {
		return getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @param aryLblWorkTime セットする aryLblWorkTime
	 */
	public void setAryLblWorkTime(String[] aryLblWorkTime) {
		this.aryLblWorkTime = getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @return aryLblRestTime
	 */
	public String[] getAryLblRestTime() {
		return getStringArrayClone(aryLblRestTime);
	}
	
	/**
	 * @param aryLblRestTime セットする aryLblRestTime
	 */
	public void setAryLblRestTime(String[] aryLblRestTime) {
		this.aryLblRestTime = getStringArrayClone(aryLblRestTime);
	}
	
	/**
	 * @return aryLblFrontTime
	 */
	public String[] getAryLblFrontTime() {
		return getStringArrayClone(aryLblFrontTime);
	}
	
	/**
	 * @param aryLblFrontTime セットする aryLblFrontTime
	 */
	public void setAryLblFrontTime(String[] aryLblFrontTime) {
		this.aryLblFrontTime = getStringArrayClone(aryLblFrontTime);
	}
	
	/**
	 * @return aryLblBackTime
	 */
	public String[] getAryLblBackTime() {
		return getStringArrayClone(aryLblBackTime);
	}
	
	/**
	 * @param aryLblBackTime セットする aryLblBackTime
	 */
	public void setAryLblBackTime(String[] aryLblBackTime) {
		this.aryLblBackTime = getStringArrayClone(aryLblBackTime);
	}
	
	/**
	 * @return aryCkbWorkTypeListId
	 */
	public String[] getAryCkbWorkTypeListId() {
		return getStringArrayClone(aryCkbWorkTypeListId);
	}
	
	/**
	 * @param aryCkbWorkTypeListId セットする aryCkbWorkTypeListId
	 */
	public void setAryCkbWorkTypeListId(String[] aryCkbWorkTypeListId) {
		this.aryCkbWorkTypeListId = getStringArrayClone(aryCkbWorkTypeListId);
	}
	
}
