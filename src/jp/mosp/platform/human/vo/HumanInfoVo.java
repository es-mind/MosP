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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 人事情報一覧画面の情報を格納する。<br>
 */
public class HumanInfoVo extends PlatformHumanVo {
	
	private static final long serialVersionUID = -7529586050432800847L;
	
	
	/**
	 * VOの初期設定を行う。<br>
	 * <br>
	 */
	public HumanInfoVo() {
		super();
		divisionMap = new HashMap<String, String>();
		// 人事情報一覧画面追加情報パラメータマップを初期化
		extraParameters = new HashMap<String, String[]>();
		// 人事情報一覧画面追加情報JSPリスト
		extraViewList = new ArrayList<String>();
	}
	
	
	/**
	 * 入社日。<br>
	 */
	private String					lblEntranceDate;
	/**
	 * 雇用契約。<br>
	 */
	private String					lblEmployment;
	/**
	 * 社員名(ｶﾅ)。<br>
	 */
	private String					lblEmployeeKana;
	/**
	 * 勤務地。<br>
	 */
	private String					lblWorkPlace;
	/**
	 * 所属。<br>
	 */
	private String					lblSection;
	/**
	 * 職位。<br>
	 */
	private String					lblPosition;
	/**
	 * 役職。<br>
	 */
	private String					lblPost;
	/**
	 * 退職日。<br>
	 */
	private String					lblRetirementDate;
	/**
	 * 退職理由。<br>
	 */
	private String					lblRetirementReason;
	/**
	 * 詳細。<br>
	 */
	private String					lblRetirementDetail;
	/**
	 * 休職開始日。<br>
	 */
	private String[]				arySuspensionStartDate;
	/**
	 * 休職終了日。<br>
	 */
	private String[]				arySuspensionEndDate;
	/**
	 * 休職終了予定日。<br>
	 */
	private String[]				arySuspensionsScheduleEndDate;
	/**
	 * 休職理由。<br>
	 */
	private String[]				arySuspensionReason;
	/**
	 * 開始日(兼務)。<br>
	 */
	private String[]				aryConcurrentStartDate;
	/**
	 * 終了日(兼務)。<br>
	 */
	private String[]				aryConcurrentEndDate;
	/**
	 * 所属略称(兼務)。<br>
	 */
	private String[]				aryConcurrentSectionAbbr;
	/**
	 * 職位略称(兼務)。<br>
	 */
	private String[]				aryConcurrentPositionAbbr;
	/**
	 * 備考(兼務)。<br>
	 */
	private String[]				aryConcurrentRemark;
	
	//////人事汎用//////
	
	/**
	 *  勤続年数
	 */
	private String					lblYearsOfService;
	
	/**
	 * 人事情報一覧画面追加情報パラメータマップ。<br>
	 * 人事情報一覧画面では複数の人事情報一覧画面追加情報用Beanが
	 * VOに値を設定するため、Mapの形でパラメータを保持する。<br>
	 */
	private Map<String, String[]>	extraParameters;
	
	/**
	 * 人事情報一覧画面追加情報JSPリスト。<br>
	 * humanInfo.jspでインクルードするJSPのリスト。<br>
	 */
	private List<String>			extraViewList;
	
	/**
	 * アドオン追加JSPリスト。<br>
	 */
	private List<String>			addonJsps;
	
	/**
	 * アドオン追加パラメータ群(キー：パラメータ名)。<br>
	 */
	private Map<String, String>		addonParams;
	
	/**
	 * アドオン追加パラメータ配列群(キー：パラメータ名)。<br>
	 */
	private Map<String, String[]>	addonArrays;
	
	
	/**
	 * 人事情報一覧画面追加情報パラメータを設定する。<br>
	 * @param key    キー
	 * @param values 値
	 */
	public void putExtraParameters(String key, String[] values) {
		extraParameters.put(key, values);
	}
	
	/**
	 * 人事情報一覧画面追加情報パラメータを設定する。<br>
	 * @param key   キー
	 * @param value 値
	 */
	public void putExtraParameters(String key, String value) {
		String[] values = { value };
		extraParameters.put(key, values);
	}
	
	/**
	 * 人事情報一覧画面追加情報パラメータを取得する。<br>
	 * @param key キー
	 * @return ポータルパラメータ
	 */
	public String[] getExtraParameters(String key) {
		return extraParameters.get(key);
	}
	
	/**
	 * 人事情報一覧画面追加情報パラメータを取得する。<br>
	 * @param key キー
	 * @return 人事情報一覧画面追加情報パラメータ
	 */
	public String getExtraParameter(String key) {
		String[] extraParameter = extraParameters.get(key);
		if (extraParameter == null || extraParameter.length == 0) {
			return "";
		}
		return extraParameter[0];
	}
	
	/**
	 * 人事情報一覧画面追加情報JSPリストを追加する。<br>
	 * @param view JSPパス
	 */
	public void addExtraViewList(String view) {
		if (extraViewList.contains(view) == false) {
			extraViewList.add(view);
		}
	}
	
	/**
	 * @return extraParameters
	 */
	public Map<String, String[]> getExtraParameters() {
		return extraParameters;
	}
	
	/**
	 * @param extraParameters セットする portalParameters
	 */
	public void setExtraParameters(Map<String, String[]> extraParameters) {
		this.extraParameters = extraParameters;
	}
	
	/**
	 * @return extraViewList
	 */
	public List<String> getExtraViewList() {
		return extraViewList;
	}
	
	/**
	 * @param extraViewList セットする portalViewList
	 */
	public void setExtraViewList(List<String> extraViewList) {
		this.extraViewList = extraViewList;
	}
	
	/**
	 * @param lblYearsOfService セットする lblYearsOfService
	 */
	public void setLblYearsOfService(String lblYearsOfService) {
		this.lblYearsOfService = lblYearsOfService;
	}
	
	/**
	 * @return lblYearsOfService
	 */
	public String getLblYearsOfService() {
		return lblYearsOfService;
	}
	
	
	// 名称区分：役職追加判断
	private boolean				needPost;
	
	// 人事汎用管理設定
	private String[]			aryDivision;
	
	// 人事汎用管理設定
	private Map<String, String>	divisionMap;
	
	// 人事汎用管理機能：表示テーブル設定
	private String[]			aryViewTable;
	
	
	/**
	 * @param lblEntranceDate セットする lblEntranceDate
	 */
	public void setLblEntranceDate(String lblEntranceDate) {
		this.lblEntranceDate = lblEntranceDate;
	}
	
	/**
	 * @return lblEntranceDate
	 */
	public String getLblEntranceDate() {
		return lblEntranceDate;
	}
	
	/**
	 * @param lblEmployment セットする lblEmployment
	 */
	public void setLblEmployment(String lblEmployment) {
		this.lblEmployment = lblEmployment;
	}
	
	/**
	 * @return lblEmployment
	 */
	public String getLblEmployment() {
		return lblEmployment;
	}
	
	/**
	 * @return lblEmployeeKana
	 */
	public String getLblEmployeeKana() {
		return lblEmployeeKana;
	}
	
	/**
	 * @param lblEmployeeKana セットする lblEmployeeKana
	 */
	public void setLblEmployeeKana(String lblEmployeeKana) {
		this.lblEmployeeKana = lblEmployeeKana;
	}
	
	/**
	 * @return lblWorkPlace
	 */
	public String getLblWorkPlace() {
		return lblWorkPlace;
	}
	
	/**
	 * @param lblWorkPlace セットする lblWorkPlace
	 */
	public void setLblWorkPlace(String lblWorkPlace) {
		this.lblWorkPlace = lblWorkPlace;
	}
	
	/**
	 * @param lblPosition セットする lblPosition
	 */
	public void setLblPosition(String lblPosition) {
		this.lblPosition = lblPosition;
	}
	
	/**
	 * @return lblPosition
	 */
	public String getLblPosition() {
		return lblPosition;
	}
	
	/**
	 * @param lblPost セットする lblPost
	 */
	public void setLblPost(String lblPost) {
		this.lblPost = lblPost;
	}
	
	/**
	 * @return lblPost
	 */
	public String getLblPost() {
		return lblPost;
	}
	
	/**
	 * @param lblRetirementDate セットする lblRetirementDate
	 */
	public void setLblRetirementDate(String lblRetirementDate) {
		this.lblRetirementDate = lblRetirementDate;
	}
	
	/**
	 * @return lblRetirementDate
	 */
	public String getLblRetirementDate() {
		return lblRetirementDate;
	}
	
	/**
	 * @param lblRetirementReason セットする lblRetirementReason
	 */
	public void setLblRetirementReason(String lblRetirementReason) {
		this.lblRetirementReason = lblRetirementReason;
	}
	
	/**
	 * @return lblRetirementReason
	 */
	public String getLblRetirementReason() {
		return lblRetirementReason;
	}
	
	/**
	 * @param lblRetirementDetail セットする lblRetirementDetail
	 */
	public void setLblRetirementDetail(String lblRetirementDetail) {
		this.lblRetirementDetail = lblRetirementDetail;
	}
	
	/**
	 * @return lblRetirementDetail
	 */
	public String getLblRetirementDetail() {
		return lblRetirementDetail;
	}
	
	/**
	 * @param arySuspensionStartDate セットする arySuspensionStartDate
	 */
	public void setArySuspensionStartDate(String[] arySuspensionStartDate) {
		this.arySuspensionStartDate = getStringArrayClone(arySuspensionStartDate);
	}
	
	/**
	 * @return arySuspensionStartDate
	 */
	public String[] getArySuspensionStartDate() {
		return getStringArrayClone(arySuspensionStartDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return arySuspensionStartDate
	 */
	public String getArySuspensionStartDate(int idx) {
		return arySuspensionStartDate[idx];
	}
	
	/**
	 * @param arySuspensionEndDate セットする arySuspensionEndDate
	 */
	public void setArySuspensionEndDate(String[] arySuspensionEndDate) {
		this.arySuspensionEndDate = getStringArrayClone(arySuspensionEndDate);
	}
	
	/**
	 * @return arySuspensionEndDate
	 */
	public String[] getArySuspensionEndDate() {
		return getStringArrayClone(arySuspensionEndDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return arySuspensionEndDate
	 */
	public String getArySuspensionEndDate(int idx) {
		return arySuspensionEndDate[idx];
	}
	
	/**
	 * @param arySuspensionsScheduleEndDate セットする arySuspensionsScheduleEndDate
	 */
	public void setArySuspensionsScheduleEndDate(String[] arySuspensionsScheduleEndDate) {
		this.arySuspensionsScheduleEndDate = getStringArrayClone(arySuspensionsScheduleEndDate);
	}
	
	/**
	 * @return arySuspensionsScheduleEndDate
	 */
	public String[] getArySuspensionsScheduleEndDate() {
		return getStringArrayClone(arySuspensionsScheduleEndDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return arySuspensionsScheduleEndDate
	 */
	public String getArySuspensionsScheduleEndDate(int idx) {
		return arySuspensionsScheduleEndDate[idx];
	}
	
	/**
	 * @param arySuspensionReason セットする arySuspensionReason
	 */
	public void setArySuspensionReason(String[] arySuspensionReason) {
		this.arySuspensionReason = getStringArrayClone(arySuspensionReason);
	}
	
	/**
	 * @return arySuspensionReason
	 */
	public String[] getArySuspensionReason() {
		return getStringArrayClone(arySuspensionReason);
	}
	
	/**
	 * @param idx インデックス
	 * @return arySuspensionReason
	 */
	public String getArySuspensionReason(int idx) {
		return arySuspensionReason[idx];
	}
	
	/**
	 * @param aryConcurrentStartDate セットする aryConcurrentStartDate
	 */
	public void setAryConcurrentStartDate(String[] aryConcurrentStartDate) {
		this.aryConcurrentStartDate = getStringArrayClone(aryConcurrentStartDate);
	}
	
	/**
	 * @return aryConcurrentStartDate
	 */
	public String[] getAryConcurrentStartDate() {
		return getStringArrayClone(aryConcurrentStartDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return arySuspensionReason
	 */
	public String getAryConcurrentStartDate(int idx) {
		return aryConcurrentStartDate[idx];
	}
	
	/**
	 * @param aryConcurrentEndDate セットする aryConcurrentEndDate
	 */
	public void setAryConcurrentEndDate(String[] aryConcurrentEndDate) {
		this.aryConcurrentEndDate = getStringArrayClone(aryConcurrentEndDate);
	}
	
	/**
	 * @return aryConcurrentEndDate
	 */
	public String[] getAryConcurrentEndDate() {
		return getStringArrayClone(aryConcurrentEndDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryConcurrentEndDate
	 */
	public String getAryConcurrentEndDate(int idx) {
		return aryConcurrentEndDate[idx];
	}
	
	/**
	 * @param aryConcurrentSectionAbbr セットする aryConcurrentSectionAbbr
	 */
	public void setAryConcurrentSectionAbbr(String[] aryConcurrentSectionAbbr) {
		this.aryConcurrentSectionAbbr = getStringArrayClone(aryConcurrentSectionAbbr);
	}
	
	/**
	 * @return aryConcurrentSectionAbbr
	 */
	public String[] getAryConcurrentSectionAbbr() {
		return getStringArrayClone(aryConcurrentSectionAbbr);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryConcurrentSectionAbbr
	 */
	public String getAryConcurrentSectionAbbr(int idx) {
		return aryConcurrentSectionAbbr[idx];
	}
	
	/**
	 * @param aryConcurrentPositionAbbr セットする aryConcurrentPositionAbbr
	 */
	public void setAryConcurrentPositionAbbr(String[] aryConcurrentPositionAbbr) {
		this.aryConcurrentPositionAbbr = getStringArrayClone(aryConcurrentPositionAbbr);
	}
	
	/**
	 * @return aryConcurrentPositionAbbr
	 */
	public String[] getAryConcurrentPositionAbbr() {
		return getStringArrayClone(aryConcurrentPositionAbbr);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryConcurrentPositionAbbr
	 */
	public String getAryConcurrentPositionAbbr(int idx) {
		return aryConcurrentPositionAbbr[idx];
	}
	
	/**
	 * @param aryConcurrentRemark セットする aryConcurrentRemark
	 */
	public void setAryConcurrentRemark(String[] aryConcurrentRemark) {
		this.aryConcurrentRemark = getStringArrayClone(aryConcurrentRemark);
	}
	
	/**
	 * @return aryConcurrentRemark
	 */
	public String[] getAryConcurrentRemark() {
		return getStringArrayClone(aryConcurrentRemark);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryConcurrentRemark
	 */
	public String getAryConcurrentRemark(int idx) {
		return aryConcurrentRemark[idx];
	}
	
	/**
	 * @param lblSection セットする lblSection
	 */
	public void setLblSection(String lblSection) {
		this.lblSection = lblSection;
	}
	
	/**
	 * @return lblSection
	 */
	public String getLblSection() {
		return lblSection;
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
	
	/**
	 * @param aryDivision セットする aryDivision
	 */
	public void setAryDivision(String[] aryDivision) {
		this.aryDivision = getStringArrayClone(aryDivision);
	}
	
	/**
	 * @return aryDivision
	 */
	public String[] getAryDivision() {
		return getStringArrayClone(aryDivision);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryDivision
	 */
	public String getAryDivision(int idx) {
		return aryDivision[idx];
	}
	
	/**
	 * 人事汎用項目値を設定する。<br>
	 * @param division 人事汎用管理区分(人事汎用履歴時は有効日)
	 * @param item Normal又は有効日又は行ID
	 */
	public void putDivisionItem(String division, String item) {
		divisionMap.put(division, item);
	}
	
	/**
	 * 人事汎用項目値を取得する。<br>
	 * @param division 人事汎用管理区分 (人事汎用履歴時は有効日)
	 * @return item Normal又は有効日又は行ID
	 */
	public String getDivisionItem(String division) {
		String item = divisionMap.get(division);
		if (item == null) {
			return "";
		}
		return item;
	}
	
	/**
	 * @return aryViewTable
	 */
	public String[] getAryViewTable() {
		return getStringArrayClone(aryViewTable);
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
	 * @return アドオン追加パラメータ
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
	 * @return アドオン追加パラメータ配列
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
	 * @param idx インデックス
	 * @return aryViewTable
	 */
	public String getAryViewTable(int idx) {
		return aryViewTable[idx];
	}
	
}
