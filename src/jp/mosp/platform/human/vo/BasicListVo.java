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
package jp.mosp.platform.human.vo;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 個人基本情報履歴一覧画面の情報を格納する。
 */
public class BasicListVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= 7861386028350822347L;
	
	private String[]			aryPfmHumanId;
	private String[]			aryActiveteDate;
	private String[]			aryEmployeeName;
	private String[]			aryEmployeeKana;
	private String[]			aryWorkPlaceCode;
	private String[]			aryWorkPlace;
	private String[]			aryEmploymentCode;
	private String[]			aryEmployment;
	private String[]			arySectionCode;
	private String[]			arySection;
	private String[]			aryPositionCode;
	private String[]			aryPosition;
	private String[]			aryPostCode;
	private String[]			aryPost;
	
	// 個人履歴削除ボタン要否判断
	private boolean				needDeleteBasciHistory;
	// 名称区分：役職有効判断
	private boolean				needPost;
	private Boolean				jsIsLastHistoryBasic;
	
	
	/**
	 * @param needDeleteBasciHistory セットする needDeleteBasciHistory
	 */
	public void setNeedDeleteBasciHistory(boolean needDeleteBasciHistory) {
		this.needDeleteBasciHistory = needDeleteBasciHistory;
	}
	
	/**
	 * @return needDeleteBasciHistory
	 */
	public boolean getNeedDeleteBasciHistory() {
		return needDeleteBasciHistory;
	}
	
	/**
	 * @param aryPfmHumanId セットする aryPfmHumanId
	 */
	public void setAryPfmHumanId(String[] aryPfmHumanId) {
		this.aryPfmHumanId = getStringArrayClone(aryPfmHumanId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPfmHumanId
	 */
	public String getAryPfmHumanId(int idx) {
		return aryPfmHumanId[idx];
	}
	
	/**
	 * @return aryPfmHumanId
	 */
	public String[] getAryPfmHumanId() {
		return getStringArrayClone(aryPfmHumanId);
	}
	
	/**
	 * @param aryActiveteDate セットする aryActiveteDate
	 */
	public void setAryActiveteDate(String[] aryActiveteDate) {
		this.aryActiveteDate = getStringArrayClone(aryActiveteDate);
	}
	
	/**
	 * @return aryActiveteDate
	 */
	public String[] getAryActiveteDate() {
		return getStringArrayClone(aryActiveteDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryActiveteDate
	 */
	public String getAryActiveteDate(int idx) {
		return aryActiveteDate[idx];
	}
	
	/**
	 * @return aryEmployeeName
	 * @param idx インデックス
	 */
	public String getAryEmployeeName(int idx) {
		return aryEmployeeName[idx];
	}
	
	/**
	 * @param aryEmployeeName セットする aryEmployeeName
	 */
	public void setAryEmployeeName(String[] aryEmployeeName) {
		this.aryEmployeeName = getStringArrayClone(aryEmployeeName);
	}
	
	/**
	 * @return aryEmployeeKana
	 * @param idx インデックス
	 */
	public String getAryEmployeeKana(int idx) {
		return aryEmployeeKana[idx];
	}
	
	/**
	 * @param aryEmployeeKana セットする aryEmployeeKana
	 */
	public void setAryEmployeeKana(String[] aryEmployeeKana) {
		this.aryEmployeeKana = getStringArrayClone(aryEmployeeKana);
	}
	
	/**
	 * @return aryWorkPlaceCode
	 * @param idx インデックス
	 */
	public String getAryWorkPlaceCode(int idx) {
		return aryWorkPlaceCode[idx];
	}
	
	/**
	 * @param aryWorkPlaceCode セットする aryWorkPlaceCode
	 */
	public void setAryWorkPlaceCode(String[] aryWorkPlaceCode) {
		this.aryWorkPlaceCode = getStringArrayClone(aryWorkPlaceCode);
	}
	
	/**
	 * @return aryWorkPlace
	 * @param idx インデックス
	 */
	public String getAryWorkPlace(int idx) {
		return aryWorkPlace[idx];
	}
	
	/**
	 * @param aryWorkPlace セットする aryWorkPlace
	 */
	public void setAryWorkPlace(String[] aryWorkPlace) {
		this.aryWorkPlace = getStringArrayClone(aryWorkPlace);
	}
	
	/**
	 * @param aryEmploymentCode セットする aryEmploymentCode
	 */
	public void setAryEmploymentCode(String[] aryEmploymentCode) {
		this.aryEmploymentCode = getStringArrayClone(aryEmploymentCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryEmploymentCode
	 */
	public String getAryEmploymentCode(int idx) {
		return aryEmploymentCode[idx];
	}
	
	/**
	 * @param aryEmployment セットする aryEmployment
	 */
	public void setAryEmployment(String[] aryEmployment) {
		this.aryEmployment = getStringArrayClone(aryEmployment);
	}
	
	/**
	* @param idx インデックス
	 * @return aryEmployment
	 */
	public String getAryEmployment(int idx) {
		return aryEmployment[idx];
	}
	
	/**
	 * @param arySectionCode セットする arySectionCode
	 */
	public void setArySectionCode(String[] arySectionCode) {
		this.arySectionCode = getStringArrayClone(arySectionCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return arySection
	 */
	public String getArySectionCode(int idx) {
		return arySectionCode[idx];
	}
	
	/**
	 * @param arySection セットする arySection
	 */
	public void setArySection(String[] arySection) {
		this.arySection = getStringArrayClone(arySection);
	}
	
	/**
	 * @param idx インデックス
	 * @return arySection
	 */
	public String getArySection(int idx) {
		return arySection[idx];
	}
	
	/**
	 * @param aryPositionCode セットする aryPositionCode
	 */
	public void setAryPositionCode(String[] aryPositionCode) {
		this.aryPositionCode = getStringArrayClone(aryPositionCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPositionCode
	 */
	public String getAryPositionCode(int idx) {
		return aryPositionCode[idx];
	}
	
	/**
	 * @param aryPosition セットする aryPosition
	 */
	public void setAryPosition(String[] aryPosition) {
		this.aryPosition = getStringArrayClone(aryPosition);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPosition
	 */
	public String getAryPosition(int idx) {
		return aryPosition[idx];
	}
	
	/**
	 * @param aryPostCode セットする aryPostCode
	 */
	public void setAryPostCode(String[] aryPostCode) {
		this.aryPostCode = getStringArrayClone(aryPostCode);
	}
	
	/**
	 * @return aryPostCode
	 * @param idx インデックス
	 */
	public String getAryPostCode(int idx) {
		return aryPostCode[idx];
	}
	
	/**
	 * @param aryPost セットする aryPost
	 */
	public void setAryPost(String[] aryPost) {
		this.aryPost = getStringArrayClone(aryPost);
	}
	
	/**
	 * @return aryPost
	 * @param idx インデックス
	 */
	public String getAryPost(int idx) {
		return aryPost[idx];
	}
	
	/**
	 * @param jsIsLastHistoryBasic セットする jsIsLastHistoryBasic
	 */
	public void setJsIsLastHistoryBasic(Boolean jsIsLastHistoryBasic) {
		this.jsIsLastHistoryBasic = jsIsLastHistoryBasic;
	}
	
	/**
	 * @return jsIsLastHistoryBasic
	 */
	public Boolean getJsIsLastHistoryBasic() {
		return jsIsLastHistoryBasic;
	}
	
	/**
	 * @param needPost セットする needPost
	 */
	public void setNeedPost(boolean needPost) {
		this.needPost = needPost;
	}
	
	/**
	 * @return needPost
	 */
	public boolean getNeedPost() {
		return needPost;
	}
}
