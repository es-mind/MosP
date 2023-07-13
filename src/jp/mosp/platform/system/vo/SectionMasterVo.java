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
package jp.mosp.platform.system.vo;

import java.util.Date;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * 所属マスタ画面の情報を格納する。
 */
public class SectionMasterVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= 7232326603987157701L;
	
	private String				pltEditClassRoute;
	private String				txtEditSectionCode;
	private String				txtEditSectionName;
	private String				txtEditSectionAbbr;
	private String				txtEditSectionDisplay;
	
	private String				txtSearchSectionCode;
	private String				txtSearchSectionName;
	private String				pltSearchSectionAbbr;
	
	/**
	 * 検索条件(検索対象：所属のみ or 階層含む)。<br>
	 */
	private String				pltSectionType;
	
	private String				pltUpdateClassRoute;
	
	/**
	 * 一括更新区分(上位所属 or 有効/無効)。<br>
	 */
	private String				radBatchUpdateType;
	
	private String[]			aryLblSectionCode;
	private String[]			aryLblSectionName;
	private String[]			aryLblClassRoute;
	
	/**
	 * 上位所属プルダウンリスト(編集用)。<br>
	 */
	private String[][]			aryPltEditClassRoute;
	
	/**
	 * 上位所属プルダウンリスト(一括更新用)。<br>
	 */
	private String[][]			aryPltUpdateClassRoute;
	
	/**
	 * 有効日(一括更新)モード。<br>
	 */
	private String				modeUpdateActivateDate;
	
	/**
	 * 検索対象日。<br>
	 * 検索処理時に設定される。<br>
	 * ソート時やページ繰り時等で、経路略称を取得するために用いる。<br>
	 */
	private Date				searchDate;
	
	/**
	 * 所属表示名称利用設定(true：利用、false：利用しない)。<br>
	 */
	private boolean				useDisplay;
	
	
	/**
	 * @param pltEditClassRoute セットする pltEditClassRoute
	 */
	public void setPltEditClassRoute(String pltEditClassRoute) {
		this.pltEditClassRoute = pltEditClassRoute;
	}
	
	/**
	 * @return pltEditClassRoute
	 */
	public String getPltEditClassRoute() {
		return pltEditClassRoute;
	}
	
	/**
	 * @param txtEditSectionCode セットする txtEditSectionCode
	 */
	public void setTxtEditSectionCode(String txtEditSectionCode) {
		this.txtEditSectionCode = txtEditSectionCode;
	}
	
	/**
	 * @return txtEditSectionCode
	 */
	public String getTxtEditSectionCode() {
		return txtEditSectionCode;
	}
	
	/**
	 * @param txtEditSectionName セットする txtEditSectionName
	 */
	public void setTxtEditSectionName(String txtEditSectionName) {
		this.txtEditSectionName = txtEditSectionName;
	}
	
	/**
	 * @return txtEditSectionName
	 */
	public String getTxtEditSectionName() {
		return txtEditSectionName;
	}
	
	/**
	 * @param pltEditSectionAbbr セットする pltEditSectionAbbr
	 */
	public void setTxtEditSectionAbbr(String pltEditSectionAbbr) {
		txtEditSectionAbbr = pltEditSectionAbbr;
	}
	
	/**
	 * @return pltEditSectionAbbr
	 */
	public String getTxtEditSectionAbbr() {
		return txtEditSectionAbbr;
	}
	
	/**
	 * @return txtEditSectionDisplay
	 */
	public String getTxtEditSectionDisplay() {
		return txtEditSectionDisplay;
	}
	
	/**
	 * @param txtEditSectionDisplay セットする txtEditSectionDisplay
	 */
	public void setTxtEditSectionDisplay(String txtEditSectionDisplay) {
		this.txtEditSectionDisplay = txtEditSectionDisplay;
	}
	
	/**
	 * @param txtSearchSectionCode セットする txtSearchSectionCode
	 */
	public void setTxtSearchSectionCode(String txtSearchSectionCode) {
		this.txtSearchSectionCode = txtSearchSectionCode;
	}
	
	/**
	 * @return txtSearchSectionCode
	 */
	public String getTxtSearchSectionCode() {
		return txtSearchSectionCode;
	}
	
	/**
	 * @param txtSearchSectionName セットする txtSearchSectionName
	 */
	public void setTxtSearchSectionName(String txtSearchSectionName) {
		this.txtSearchSectionName = txtSearchSectionName;
	}
	
	/**
	 * @return txtSearchSectionName
	 */
	public String getTxtSearchSectionName() {
		return txtSearchSectionName;
	}
	
	/**
	 * @param pltSearchSectionAbbr セットする pltSearchSectionAbbr
	 */
	public void setPltSearchSectionAbbr(String pltSearchSectionAbbr) {
		this.pltSearchSectionAbbr = pltSearchSectionAbbr;
	}
	
	/**
	 * @return pltSearchSectionAbbr
	 */
	public String getPltSearchSectionAbbr() {
		return pltSearchSectionAbbr;
	}
	
	/**
	 * @param pltSectionType セットする pltSectionType
	 */
	public void setPltSectionType(String pltSectionType) {
		this.pltSectionType = pltSectionType;
	}
	
	/**
	 * @return pltSectionType
	 */
	public String getPltSectionType() {
		return pltSectionType;
	}
	
	/**
	 * @param pltUpdateClassRoute セットする pltUpdateClassRoute
	 */
	public void setPltUpdateClassRoute(String pltUpdateClassRoute) {
		this.pltUpdateClassRoute = pltUpdateClassRoute;
	}
	
	/**
	 * @return pltUpdateClassRoute
	 */
	public String getPltUpdateClassRoute() {
		return pltUpdateClassRoute;
	}
	
	/**
	 * @return radBatchUpdateType
	 */
	public String getRadBatchUpdateType() {
		return radBatchUpdateType;
	}
	
	/**
	 * @param radBatchUpdateType セットする radBatchUpdateType
	 */
	public void setRadBatchUpdateType(String radBatchUpdateType) {
		this.radBatchUpdateType = radBatchUpdateType;
	}
	
	/**
	 * @param aryLblSectionCode セットする aryLblSectionCode
	 */
	public void setAryLblSectionCode(String[] aryLblSectionCode) {
		this.aryLblSectionCode = getStringArrayClone(aryLblSectionCode);
	}
	
	/**
	 * @return aryLblSectionCode
	 */
	public String[] getAryLblSectionCode() {
		return getStringArrayClone(aryLblSectionCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSectionCode
	 */
	public String getAryLblSectionCode(int idx) {
		return aryLblSectionCode[idx];
	}
	
	/**
	 * @param aryLblClassRoute セットする aryLblClassRoute
	 */
	public void setAryLblClassRoute(String[] aryLblClassRoute) {
		this.aryLblClassRoute = getStringArrayClone(aryLblClassRoute);
	}
	
	/**
	 * @return aryLblClassRoute
	 */
	public String[] getAryLblClassRoute() {
		return getStringArrayClone(aryLblClassRoute);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblClassRoute
	 */
	public String getAryLblClassRoute(int idx) {
		return aryLblClassRoute[idx];
	}
	
	/**
	 * @param aryLblSectionName セットする aryLblSectionName
	 */
	public void setAryLblSectionName(String[] aryLblSectionName) {
		this.aryLblSectionName = getStringArrayClone(aryLblSectionName);
	}
	
	/**
	 * @return aryLblSectionName
	 */
	public String[] getAryLblSectionName() {
		return getStringArrayClone(aryLblSectionName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSectionName
	 */
	public String getAryLblSectionName(int idx) {
		return aryLblSectionName[idx];
	}
	
	/**
	 * @param aryPltEditClassRoute セットする aryPltEditClassRoute
	 */
	public void setAryPltEditClassRoute(String[][] aryPltEditClassRoute) {
		this.aryPltEditClassRoute = getStringArrayClone(aryPltEditClassRoute);
	}
	
	/**
	 * @return aryPltEditClassRoute
	 */
	public String[][] getAryPltEditClassRoute() {
		return getStringArrayClone(aryPltEditClassRoute);
	}
	
	/**
	 * @param aryPltUpdateClassRoute セットする aryPltUpdateClassRoute
	 */
	public void setAryPltUpdateClassRoute(String[][] aryPltUpdateClassRoute) {
		this.aryPltUpdateClassRoute = getStringArrayClone(aryPltUpdateClassRoute);
	}
	
	/**
	 * @return aryPltUpdateClassRoute
	 */
	public String[][] getAryPltUpdateClassRoute() {
		return getStringArrayClone(aryPltUpdateClassRoute);
	}
	
	/**
	 * @return modeUpdateActivateDate
	 */
	public String getModeUpdateActivateDate() {
		return modeUpdateActivateDate;
	}
	
	/**
	 * @param modeUpdateActivateDate セットする modeUpdateActivateDate
	 */
	public void setModeUpdateActivateDate(String modeUpdateActivateDate) {
		this.modeUpdateActivateDate = modeUpdateActivateDate;
	}
	
	/**
	 * @param searchDate セットする searchDate
	 */
	public void setSearchDate(Date searchDate) {
		this.searchDate = getDateClone(searchDate);
	}
	
	/**
	 * @return searchDate
	 */
	public Date getSearchDate() {
		return getDateClone(searchDate);
	}
	
	/**
	 * @return useDisplay
	 */
	public boolean getUseDisplay() {
		return useDisplay;
	}
	
	/**
	 * @param useDisplay セットする useDisplay
	 */
	public void setUseDisplay(boolean useDisplay) {
		this.useDisplay = useDisplay;
	}
	
}
