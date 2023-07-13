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
package jp.mosp.platform.human.base;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.mosp.platform.base.PlatformVo;

/**
 * MosPプラットフォーム人事管理におけるVOの基本となる情報を格納する。<br>
 */
public abstract class PlatformHumanVo extends PlatformVo {
	
	private static final long serialVersionUID = -8491195829751058929L;
	
	
	/**
	 * VOの初期設定を行う。<br>
	 * <br>
	 */
	public PlatformHumanVo() {
		super();
		// ポータルパラメータマップ、ポータルJSPリストを初期化
		humanNormalMap = new LinkedHashMap<String, Map<String, String>>();
		humanHistoryMap = new LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>>();
		humanArrayMap = new LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>>();
		pltHumanGeneralMap = new HashMap<String, String[][]>();
		aryBinaryRowIdMap = new HashMap<String, String[]>();
		aryBinaryActivateDateMap = new HashMap<String, String[]>();
		aryBinaryFileTypeMap = new HashMap<String, String[]>();
		aryBinaryFileNameMap = new HashMap<String, String[]>();
		aryBinaryFileRemarkMap = new HashMap<String, String[]>();
		aryRecordIdMap = new LinkedHashMap<String, Long>();
	}
	
	
	/**
	 * 対象社員コード。<br>
	 * 各画面では、この社員コードを用いて情報の検索を行う。<br>
	 */
	private String						employeeCode;
	
	/**
	 * 対象日。<br>
	 * 各画面では、この対象日を用いて情報の検索を行う。<br>
	 */
	private Date						targetDate;
	
	/**
	 * 対象個人ID。<br>
	 * 対象社員コード及び対象有効日で取得した個人IDを格納する。<br>
	 */
	private String						personalId;
	
	/**
	 * 社員名。<br>
	 */
	private String						lblEmployeeName;
	
	/**
	 * 次個人ID。<br>
	 */
	private String						nextPersonalId;
	/**
	 * 前個人ID。<br>
	 */
	private String						backPersonalId;
	
	/**
	 * 次社員コード。<br>
	 */
	private String						lblNextEmployeeCode;
	/**
	 * 前社員コード。<br>
	 */
	private String						lblBackEmployeeCode;
	
	/**
	 * 再表示有効日(年)。<br>
	 */
	private String						txtSearchActivateYear;
	/**
	 * 再表示有効日(月)。<br>
	 */
	private String						txtSearchActivateMonth;
	/**
	 * 再表示有効日(日)。<br>
	 */
	private String						txtSearchActivateDay;
	
	/**
	 * 検索社員コード<br>
	 */
	private String						txtSearchEmployeeCode;
	
	/**
	 * 人事管理共通情報表示モード。<br>
	 */
	private String						modeHumanLayout;
	
	/**
	 * 検索時コマンド<br>
	 */
	private String						cmdSaerch;
	
	//// 人事汎用バイナリ /////
	/**
	 * 行ID(バイナリ)。
	 */
	private Map<String, String[]>		aryBinaryRowIdMap;
	/**
	 * 有効日(バイナリ)。
	 */
	private Map<String, String[]>		aryBinaryActivateDateMap;
	/**
	 * ファイル区分(バイナリ)。
	 */
	private Map<String, String[]>		aryBinaryFileTypeMap;
	/**
	 * ファイル名(バイナリ)。
	 */
	private Map<String, String[]>		aryBinaryFileNameMap;
	/**
	 * ファイル備考(バイナリ)。
	 */
	private Map<String, String[]>		aryBinaryFileRemarkMap;
	/**
	 * 人事汎用管理機能参照確認
	 */
	private boolean						jsIsReferenceDivision;
	/**
	 * 識別IDマップ
	 */
	private LinkedHashMap<String, Long>	aryRecordIdMap;
	
	
	/**
	 * @return employeeCode
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	/**
	 * @param employeeCode セットする employeeCode
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	/**
	 * @return targetDate
	 */
	public Date getTargetDate() {
		return getDateClone(targetDate);
	}
	
	/**
	 * @param targetDate セットする targetDate
	 */
	public void setTargetDate(Date targetDate) {
		this.targetDate = getDateClone(targetDate);
	}
	
	/**
	 * @return personalId
	 */
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * @param personalId セットする personalId
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * @return cmdSaerch
	 */
	public String getCmdSaerch() {
		return cmdSaerch;
	}
	
	/**
	 * @param cmdSaerch セットする cmdSaerch
	 */
	public void setCmdSaerch(String cmdSaerch) {
		this.cmdSaerch = cmdSaerch;
	}
	
	/**
	 * @return txtSearchActivateYear
	 */
	public String getTxtSearchActivateYear() {
		return txtSearchActivateYear;
	}
	
	/**
	 * @param txtSearchActivateYear セットする txtSearchActivateYear
	 */
	public void setTxtSearchActivateYear(String txtSearchActivateYear) {
		this.txtSearchActivateYear = txtSearchActivateYear;
	}
	
	/**
	 * @return txtSearchActivateMonth
	 */
	public String getTxtSearchActivateMonth() {
		return txtSearchActivateMonth;
	}
	
	/**
	 * @param txtSearchActivateMonth セットする txtSearchActivateMonth
	 */
	public void setTxtSearchActivateMonth(String txtSearchActivateMonth) {
		this.txtSearchActivateMonth = txtSearchActivateMonth;
	}
	
	/**
	 * @return txtSearchActivateDay
	 */
	public String getTxtSearchActivateDay() {
		return txtSearchActivateDay;
	}
	
	/**
	 * @param txtSearchActivateDay セットする txtSearchActivateDay
	 */
	public void setTxtSearchActivateDay(String txtSearchActivateDay) {
		this.txtSearchActivateDay = txtSearchActivateDay;
	}
	
	/**
	 * @return txtSearchEmployeeCode
	 */
	public String getTxtSearchEmployeeCode() {
		return txtSearchEmployeeCode;
	}
	
	/**
	 * @param txtSearchEmployeeCode セットする txtSearchEmployeeCode
	 */
	public void setTxtSearchEmployeeCode(String txtSearchEmployeeCode) {
		this.txtSearchEmployeeCode = txtSearchEmployeeCode;
	}
	
	/**
	 * @return lblEmployeeName
	 */
	public String getLblEmployeeName() {
		return lblEmployeeName;
	}
	
	/**
	 * @param lblEmployeeName セットする lblEmployeeName
	 */
	public void setLblEmployeeName(String lblEmployeeName) {
		this.lblEmployeeName = lblEmployeeName;
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
	 * @return lblBackEmployeeCode
	 */
	public String getLblBackEmployeeCode() {
		return lblBackEmployeeCode;
	}
	
	/**
	 * @param lblBackEmployeeCode セットする lblBackEmployeeCode
	 */
	public void setLblBackEmployeeCode(String lblBackEmployeeCode) {
		this.lblBackEmployeeCode = lblBackEmployeeCode;
	}
	
	/**
	 * @return modeScreenLayout
	 */
	public String getModeHumanLayout() {
		return modeHumanLayout;
	}
	
	/**
	 * @param modeHumanLayout セットする modeHumanLayout
	 */
	public void setModeHumanLayout(String modeHumanLayout) {
		this.modeHumanLayout = modeHumanLayout;
	}
	
	/**
	 * @param nextPersonalId セットする nextPersonalId
	 */
	public void setNextPersonalId(String nextPersonalId) {
		this.nextPersonalId = nextPersonalId;
	}
	
	/**
	 * @return nextPersonalId
	 */
	public String getNextPersonalId() {
		return nextPersonalId;
	}
	
	/**
	 * @param backPersonalId セットする backPersonalId
	 */
	public void setBackPersonalId(String backPersonalId) {
		this.backPersonalId = backPersonalId;
	}
	
	/**
	 * @return backPersonalId
	 */
	public String getBackPersonalId() {
		return backPersonalId;
	}
	
	
	/**
	 * 通常人事汎用情報確認フラグ
	 */
	private boolean isHumanNormalInfo;
	
	
	/**
	 * @param isHumanNormalInfo セットする isHumanNormalInfo
	 */
	public void setIsHumanNormalInfo(boolean isHumanNormalInfo) {
		this.isHumanNormalInfo = isHumanNormalInfo;
	}
	
	/**
	 * @return isHumanNormalInfo
	 */
	public boolean getIsHumanNormalInfo() {
		return isHumanNormalInfo;
	}
	
	
	/**
	 * 通常人事汎用項目値
	 */
	private Map<String, Map<String, String>> humanNormalMap;
	
	
	/**
	 * 人事汎用項目値を設定する。<br>
	 * @param division 人事汎用管理区分(人事汎用履歴時は有効日) 
	 * @param itemKey 人事汎用項目
	 * @param value 値
	 */
	public void putNormalItem(String division, String itemKey, String value) {
		Map<String, String> itemMap = humanNormalMap.get(division);
		if (itemMap == null) {
			itemMap = new HashMap<String, String>();
			humanNormalMap.put(division, itemMap);
		}
		itemMap.put(itemKey, value);
	}
	
	/**
	 * 人事汎用項目値を設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param itemMap 項目＋値
	 */
	public void putNormalItem(String division, Map<String, String> itemMap) {
		if (itemMap == null) {
			itemMap = new LinkedHashMap<String, String>();
		}
		humanNormalMap.put(division, itemMap);
	}
	
	/**
	 * 人事汎用項目値を取得する。<br>
	 * @param division 人事汎用管理区分
	 * @param itemKey 人事汎用項目
	 * @return value 値
	 */
	public String getNormalItem(String division, String itemKey) {
		Map<String, String> itemMap = humanNormalMap.get(division);
		if (itemMap == null) {
			return "";
		}
		String value = itemMap.get(itemKey);
		if (value == null) {
			return "";
		}
		return value;
	}
	
	/**
	 * 人事汎用項目マップを取得する。<br>
	 * @param division 人事汎用管理区分
	 * @return 人事汎用項目マップ
	 */
	public Map<String, String> getNormalMapInfo(String division) {
		return humanNormalMap.get(division);
	}
	
	/**
	 * @param humanNormalMap セットする humanNormalMap
	 */
	public void setHumanNoramlMap(Map<String, Map<String, String>> humanNormalMap) {
		this.humanNormalMap = humanNormalMap;
	}
	
	/**
	 * @return humanNormalMap
	 */
	public Map<String, Map<String, String>> getHumanNormalMap() {
		return humanNormalMap;
	}
	
	
	/**
	 * 履歴人事汎用情報確認フラグ
	 */
	private boolean isHumanHistoryInfo;
	
	
	/**
	 * @param isHumanHistoryInfo セットする isHumanHistoryInfo
	 */
	public void setIsHumanHistoryInfo(boolean isHumanHistoryInfo) {
		this.isHumanHistoryInfo = isHumanHistoryInfo;
	}
	
	/**
	 * @return isHumanHistoryInfo
	 */
	public boolean getIsHumanHistoryInfo() {
		return isHumanHistoryInfo;
	}
	
	
	/**
	 * 履歴人事汎用項目値
	 */
	private LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> humanHistoryMap;
	
	
	/**
	 * 人事汎用履歴項目値を設定する。<br>
	 * @param division 人事汎用管理区分 (人事汎用履歴時は有効日) 
	 * @param activeDate 有効日
	 * @param itemKey 項目名
	 * @param value 項目値
	 */
	public void putHistoryItem(String division, String activeDate, String itemKey, String value) {
		Map<String, String> humanItem = humanHistoryMap.get(division).get(activeDate);
		if (humanItem == null) {
			humanItem = new LinkedHashMap<String, String>();
			humanItem.put(itemKey, value);
		}
		LinkedHashMap<String, Map<String, String>> historyMap = humanHistoryMap.get(division);
		if (historyMap == null) {
			historyMap = new LinkedHashMap<String, Map<String, String>>();
			historyMap.put(activeDate, humanItem);
		}
		humanHistoryMap.put(division, historyMap);
	}
	
	/**
	 * 人事汎用履歴項目値を設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param historyMap 有効日毎の項目値
	 */
	public void putHistoryItem(String division, LinkedHashMap<String, Map<String, String>> historyMap) {
		if (historyMap == null) {
			historyMap = new LinkedHashMap<String, Map<String, String>>();
		}
		humanHistoryMap.put(division, historyMap);
	}
	
	/**
	 * 人事汎用項目値を取得する。<br>
	 * @param division 人事汎用管理区分
	 * @param activeDate 有効日
	 * @param itemName 項目名
	 * @return value 値
	 */
	public String getHistoryItem(String division, String activeDate, String itemName) {
		LinkedHashMap<String, Map<String, String>> historyMap = humanHistoryMap.get(division);
		if (historyMap == null) {
			return "";
		}
		Map<String, String> historyItem = historyMap.get(activeDate);
		if (historyItem == null) {
			return "";
		}
		String value = historyItem.get(itemName);
		if (value == null) {
			return "";
		}
		return value;
	}
	
	/**
	 * 人事汎用項目マップを取得する。<br>
	 * @param division 人事汎用管理区分
	 * @return 人事汎用項目マップ
	 */
	public LinkedHashMap<String, Map<String, String>> getHistoryMapInfo(String division) {
		return humanHistoryMap.get(division);
	}
	
	/**
	 * @param humanHistoryMap セットする humanHistoryMap
	 */
	public void setHumanHistoryMap(LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> humanHistoryMap) {
		this.humanHistoryMap = humanHistoryMap;
	}
	
	/**
	 * @return humanHistoryMap
	 */
	public LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> getHumanHistoryMap() {
		return humanHistoryMap;
	}
	
	
	/**
	 * 一覧人事汎用項目値
	 */
	private LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> humanArrayMap;
	
	
	/**
	 * 人事汎用項目値を設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param rowId 行ID
	 * @param itemName 項目名
	 * @param value 項目値
	 */
	public void putArrayItems(String division, String rowId, String itemName, String value) {
		Map<String, String> humanItem = humanArrayMap.get(division).get(rowId);
		if (humanItem == null) {
			humanItem = new HashMap<String, String>();
			humanItem.put(itemName, value);
		}
		LinkedHashMap<String, Map<String, String>> arrayMap = humanArrayMap.get(division);
		if (arrayMap == null) {
			arrayMap = new LinkedHashMap<String, Map<String, String>>();
			arrayMap.put(rowId, humanItem);
		}
		humanArrayMap.put(division, arrayMap);
	}
	
	/**
	 * 人事汎用一覧項目値を設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param arrayMap 行ID毎の項目値
	 */
	public void putArrayItem(String division, LinkedHashMap<String, Map<String, String>> arrayMap) {
		if (arrayMap == null) {
			arrayMap = new LinkedHashMap<String, Map<String, String>>();
		}
		humanArrayMap.put(division, arrayMap);
	}
	
	/**
	 * 人事汎用項目値を取得する。<br>
	 * @param division 人事汎用管理区分
	 * @param rowId 行ID
	 * @param itemName 項目名
	 * @return value 値
	 */
	public String getArrayItem(String division, String rowId, String itemName) {
		LinkedHashMap<String, Map<String, String>> arrayMap = humanArrayMap.get(division);
		if (arrayMap == null) {
			return "";
		}
		Map<String, String> arrayItem = arrayMap.get(rowId);
		if (arrayItem == null) {
			return "";
		}
		String value = arrayItem.get(itemName);
		if (value == null) {
			return "";
		}
		return value;
	}
	
	/**
	 * 人事汎用項目マップを取得する。<br>
	 * @param division 人事汎用管理区分
	 * @return 人事汎用項目マップ
	 */
	public LinkedHashMap<String, Map<String, String>> getArrayMapInfo(String division) {
		return humanArrayMap.get(division);
	}
	
	/**
	 * @param humanArrayMap セットする humanArrayMap
	 */
	public void setHumanArrayMap(LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> humanArrayMap) {
		this.humanArrayMap = humanArrayMap;
	}
	
	/**
	 * @return humanArrayMap
	 */
	public LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> getHumanArrayMap() {
		return humanArrayMap;
	}
	
	
	// 人事汎用管理区分
	private String division;
	
	
	/**
	 * @param division セットする division
	 */
	public void setDivision(String division) {
		this.division = division;
	}
	
	/**
	 * @return division
	 */
	public String getDivision() {
		return division;
	}
	
	
	// 人事汎用プルダウン
	private Map<String, String[][]> pltHumanGeneralMap;
	
	
	/**
	 * 人事汎用プルダウン情報群を設定する。<br>
	 * itemNameをkeyにプルダウンを詰める。<br>
	 * @param pulldownMap 人事汎用プルダウン情報群
	 */
	public void putPltItem(Map<String, String[][]> pulldownMap) {
		if (pulldownMap == null) {
			pulldownMap = new HashMap<String, String[][]>();
		}
		pltHumanGeneralMap.putAll(pulldownMap);
	}
	
	/**
	 * 人事汎用プルダウン情報群を取得する。<br>
	 * codeKeyからプルダウンのマップを取得する。
	 * @param tableItemKey 人事汎用表示テーブル項目
	 * @return プルダウンコード+名称
	 */
	public String[][] getPltItem(String tableItemKey) {
		String[][] pltItemMap = pltHumanGeneralMap.get(tableItemKey);
		if (pltItemMap == null) {
			return new String[0][];
		}
		return pltItemMap;
	}
	
	/**
	 * バイナリ行IDを設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param item 有効日
	 */
	public void putAryBinaryRowIdMap(String division, String[] item) {
		aryBinaryRowIdMap.put(division, item);
	}
	
	/**
	 * バイナリ行IDを取得する。<br>
	 * @param division 人事汎用管理区分
	 * @return item 有効日
	 */
	public String[] getAryBinaryRowIdMap(String division) {
		return aryBinaryRowIdMap.get(division);
	}
	
	/**
	 * バイナリ有効日を設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param item 有効日
	 */
	public void putAryBinaryActiveDateMap(String division, String[] item) {
		aryBinaryActivateDateMap.put(division, item);
	}
	
	/**
	 * バイナリ有効日を取得する。<br>
	 * @param division 人事汎用管理区分
	 * @return item 有効日
	 */
	public String[] getAryBinaryActiveDateMap(String division) {
		return aryBinaryActivateDateMap.get(division);
	}
	
	/**
	 * バイナリファイル区分を設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param item 項目値
	 */
	public void putAryBinaryFileTypeMap(String division, String[] item) {
		aryBinaryFileTypeMap.put(division, item);
	}
	
	/**
	 * バイナリファイル区分を取得する。<br>
	 * @param division 人事汎用管理区分 
	 * @return item 項目値
	 */
	public String[] getAryBinaryFileTypeMap(String division) {
		return aryBinaryFileTypeMap.get(division);
	}
	
	/**
	 * バイナリファイル名を設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param item 項目値
	 */
	public void putAryBinaryFileNameMap(String division, String[] item) {
		aryBinaryFileNameMap.put(division, item);
	}
	
	/**
	 * バイナリファイル名を取得する。<br>
	 * @param division 人事汎用管理区分 
	 * @return item 項目値
	 */
	public String[] getAryBinaryFileNameMap(String division) {
		return aryBinaryFileNameMap.get(division);
	}
	
	/**
	 * バイナリファイル備考を設定する。<br>
	 * @param division 人事汎用管理区分
	 * @param item 項目値
	 */
	public void putAryBinaryFileRemarkMap(String division, String[] item) {
		aryBinaryFileRemarkMap.put(division, item);
	}
	
	/**
	 * バイナリファイル備考を取得する。<br>
	 * @param division 人事汎用管理区分 
	 * @return item 項目値
	 */
	public String[] getAryBinaryFileRemarkMap(String division) {
		return aryBinaryFileRemarkMap.get(division);
	}
	
	/**
	 * @return jsIsReferenceDivision
	 */
	public boolean getJsIsReferenceDivision() {
		return jsIsReferenceDivision;
	}
	
	/**
	 * @param jsIsReferenceDivision セットする jsIsReferenceDivision
	 */
	public void setJsIsReferenceDivision(boolean jsIsReferenceDivision) {
		this.jsIsReferenceDivision = jsIsReferenceDivision;
	}
	
	/**
	 * レコード識別IDマップを設定<br>
	 * @param itemKey 人事汎用管項目
	 * @param recordId 識別ID
	 */
	public void putAryRecordIdMap(String itemKey, Long recordId) {
		aryRecordIdMap.put(itemKey, recordId);
	}
	
	/**
	 * レコード識別IDマップを設定<br>
	 * @param aryRecordIdMap レコード識別IDマップ
	 */
	public void setAryRecordIdMap(LinkedHashMap<String, Long> aryRecordIdMap) {
		this.aryRecordIdMap.putAll(aryRecordIdMap);
	}
	
	/**
	 * レコード識別IDを取得<br>
	 * @param itemKey 人事汎用管理項目 
	 * @return 識別ID 
	 */
	public Long getAryRecordIdMap(String itemKey) {
		return aryRecordIdMap.get(itemKey);
	}
	
	/**
	 * 識別IDマップを取得<br>
	 * @return 識別IDマップ 
	 */
	public LinkedHashMap<String, Long> getAryRecordIdMap() {
		return aryRecordIdMap;
	}
	
}
