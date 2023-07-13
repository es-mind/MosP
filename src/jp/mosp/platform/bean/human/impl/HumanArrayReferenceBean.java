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
package jp.mosp.platform.bean.human.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.human.HumanArrayReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanArrayDaoInterface;
import jp.mosp.platform.dto.human.HumanArrayDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;

/**
 * 人事汎用一覧情報参照クラス。
 */
public class HumanArrayReferenceBean extends HumanGeneralBean implements HumanArrayReferenceBeanInterface {
	
	/**
	 * 人事入社情報DAO。
	 */
	private HumanArrayDaoInterface				dao;
	
	/**
	 * 人事汎用項目情報リスト。
	 */
	protected List<TableItemProperty>			tableItemList;
	
	/**
	 * 人事行ID一覧情報汎用マップ。
	 */
	LinkedHashMap<String, Map<String, String>>	arrayHumanInfoMap;
	
	/**
	 * 人事一覧情報汎用マップ。
	 */
	Map<String, String>							arrayMap;
	
	/**
	 * 人事通常レコード識別マップ
	 */
	protected LinkedHashMap<String, Long>		recordsMap;
	
	
	/**
	 * {@link HumanGeneralBean#HumanGeneralBean()}を実行する。<br>
	 */
	public HumanArrayReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanArrayDaoInterface.class);
	}
	
	@Override
	public List<HumanArrayDtoInterface> findForItemType(String personalId, String humanItemType) throws MospException {
		return dao.findForItemType(personalId, humanItemType);
	}
	
	@Override
	public HumanArrayDtoInterface findForKey(String personalId, String humanItemType, int rowId) throws MospException {
		return dao.findForKey(personalId, humanItemType, rowId);
	}
	
	private HumanArrayDtoInterface findForKeyItems(String personalId, String humanItemType, int rowId)
			throws MospException {
		HumanArrayDtoInterface dto = dao.findForKey(personalId, humanItemType, rowId);
		
		// フォーマット文字列(年月日)の場合
		if (dto == null) {
			dto = findForKey(personalId, humanItemType + "Year", rowId);
		}
		// フォーマット文字列(電話)の場合
		if (dto == null) {
			dto = findForKey(personalId, humanItemType + "Area", rowId);
		}
		
		// フォーマット文字列(連結)の場合
		if (dto == null) {
			String[] arySplitItemName = MospUtility.split(humanItemType, MospConst.APP_PROPERTY_SEPARATOR);
			if (arySplitItemName.length != 0) {
				dto = findForKey(personalId, arySplitItemName[0], rowId);
			}
		}
		
		return dto;
		
	}
	
	@Override
	public void setCommounInfo(String division, String viewKey) {
		// 人事汎用項目区分設定情報取得
		conventionProperty = mospParams.getProperties().getConventionProperties()
			.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
		// 人事汎用項目情報リストを取得
		tableItemList = getTableItemList(division, viewKey);
		// 人事行ID一覧情報汎用マップ準備
		arrayHumanInfoMap = new LinkedHashMap<String, Map<String, String>>();
		// 人事情報汎用マップ準備
		arrayMap = new HashMap<String, String>();
		// レコード識別IDマップ準備
		recordsMap = new LinkedHashMap<String, Long>();
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getHumanArrayMapInfo(String division, String viewKey,
			String personalId, int rowID) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 人事汎用項目名取得
				String itemName = itemNames[i];
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// 人事汎用一覧情報取得
				HumanArrayDtoInterface dto = findForKey(personalId, itemName, rowID);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					arrayMap.put(itemName, "");
					continue;
				}
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// プルダウンコードを取得する
				String pulldownValue = getPulldownValue(itemProperty, dto.getActivateDate(), dto.getHumanItemValue(),
						itemName, false);
				if (pulldownValue.isEmpty() == false) {
					// プルダウンコードを取得し設定
					arrayMap.put(itemName, pulldownValue);
				}
				// 値を設定
				arrayMap.put(itemName, dto.getHumanItemValue());
				arrayMap.put(PlatformHumanConst.PRM_HUMAN_ARRAY_DATE, DateUtility.getStringDate(dto.getActivateDate()));
				arrayHumanInfoMap.put(String.valueOf(rowID), arrayMap);
			}
		}
		return arrayHumanInfoMap;
	}
	
	@Override
	public void getHumanArrayDtoMapInfo(String division, String viewKey, String personalId, int rowID)
			throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		// マップに設定
		LinkedHashMap<String, Long> keyMap = new LinkedHashMap<String, Long>();
		
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 人事汎用項目名取得
				String itemName = itemNames[i];
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// 人事汎用一覧情報取得
				HumanArrayDtoInterface dto = findForKey(personalId, itemName, rowID);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					continue;
				}
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// プルダウンコードを取得する
				String pulldownValue = getPulldownValue(itemProperty, dto.getActivateDate(), dto.getHumanItemValue(),
						itemName, false);
				if (pulldownValue.isEmpty() == false) {
					dto.setHumanItemValue(pulldownValue);
				}
				// 値を設定
				keyMap.put(itemName, dto.getPfaHumanArrayId());
				
				// 値を設定
				arrayMap.put(itemName, dto.getHumanItemValue());
				arrayMap.put(PlatformHumanConst.PRM_HUMAN_ARRAY_DATE, DateUtility.getStringDate(dto.getActivateDate()));
				arrayHumanInfoMap.put(String.valueOf(rowID), arrayMap);
				recordsMap.put(itemName, dto.getPfaHumanArrayId());
			}
		}
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getRowIdArrayMapInfo(String division, String viewKey,
			String personalId, Date targetDate) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		
		// 行IDセット取得
		arrayHumanInfoMap = getArrayHumanInfoMapForKey(tableItemList, personalId);
		
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			
			// 人事汎用項目ラベルキーを取得する
			String[] labelKeys = tableItem.getLabelKeys();
			
			// 行IDセット毎に処理
			for (String rowId : arrayHumanInfoMap.keySet()) {
				// 人事汎用項目毎に処理
				for (int i = 0; i < itemNames.length; i++) {
					// 行ID取得
					String mapRowId = String.valueOf(rowId);
					
					// プルダウンのコードから名称を取得し設定
					Map<String, String> arrayMap = arrayHumanInfoMap.get(mapRowId);
					if (arrayMap == null) {
						arrayMap = new HashMap<String, String>();
					}
					
					// 人事汎用履歴情報取得
					HumanArrayDtoInterface dto = findForKeyItems(personalId, itemNames[i], Integer.parseInt(rowId));
					
					// 人事汎用項目設定情報取得
					ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
					
					String humanItemValue = "";
					
					if (dto == null) {
						continue;
					}
					
					// 人事汎用項目ラベルキー取得
					String labelKey = labelKeys[i];
					// フォーマットに合わせ合体させたデータを取得
					String dateTypeValue = getSeparateTxtItemArrayValue(personalId, itemNames[i], itemProperty,
							mapRowId, targetDate, labelKey);
					arrayMap.put(itemNames[i], dateTypeValue);
					
					// 設定値を取得
					humanItemValue = dateTypeValue;
					
					// プルダウン名称を取得する
					String pulldownValue = getPulldownValue(itemProperty, dto.getActivateDate(),
							dto.getHumanItemValue(), itemNames[i], true);
					
					// プルダウン値ではない場合に空になるので、空で比較
					if (pulldownValue.isEmpty() == false) {
						humanItemValue = pulldownValue;
						arrayMap.put(itemNames[i], pulldownValue);
					}
					
					// 人事情報汎用マップに詰める
					if (humanItemValue.isEmpty()) {
						arrayMap.put(dto.getHumanItemType(), dto.getHumanItemValue());
					}
					
					arrayMap.put(PlatformHumanConst.PRM_HUMAN_ARRAY_DATE,
							DateUtility.getStringDate(dto.getActivateDate()));
					
					// 有効日人事汎用履歴情報マップに詰める
					arrayHumanInfoMap.put(mapRowId, arrayMap);
				}
			}
		}
		return arrayHumanInfoMap;
	}
	
	@Override
	public String getArrayItemValue(String division, String viewKey, String personalId, Date targetDate,
			String tableItemKey, boolean isPulldownName) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		// 行IDセット取得
		arrayHumanInfoMap = getArrayHumanInfoMapForKey(tableItemList, personalId);
		
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			if (tableItem.getKey().equals(tableItemKey) == false) {
				continue;
			}
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目ラベルキーを取得する
			String[] labelKeys = tableItem.getLabelKeys();
			
			// 行IDセット毎に処理
			for (String rowId : arrayHumanInfoMap.keySet()) {
				// 人事汎用項目毎に処理
				for (int i = 0; i < itemNames.length; i++) {
					// 行ID取得
					String mapRowId = rowId;
					// 人事汎用項目設定情報取得
					ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
					// 人事汎用項目ラベルキー取得
					String labelKey = labelKeys[i];
					// 人事汎用項目ラベルキーが有効日の場合
					if (labelKey.equals(PlatformHumanConst.PRM_IMPORT_HISTORY_ARRAY_ACTIVATE_DATE)) {
						// 人事汎用履歴情報取得
						HumanArrayDtoInterface dto = findForKeyItems(personalId, itemNames[i], Integer.parseInt(rowId));
						if (dto == null) {
							return "";
						}
						return getStringDate(dto.getActivateDate());
					}
					// フォーマットに合わせ合体させたデータを取得
					String dateTypeValue = getSeparateTxtItemArrayValue(personalId, itemNames[i], itemProperty,
							mapRowId, targetDate, labelKey);
					if (dateTypeValue.isEmpty() == false) {
						return dateTypeValue;
					}
					// 人事汎用履歴情報取得
					HumanArrayDtoInterface dto = findForKey(personalId, itemNames[i], Integer.parseInt(rowId));
					if (dto == null) {
						continue;
					}
					// プルダウン名称を取得する
					String pulldownValue = getPulldownValue(itemProperty, dto.getActivateDate(),
							dto.getHumanItemValue(), itemNames[i], true);
					if (pulldownValue.isEmpty() == false) {
						return pulldownValue;
					}
					return dto.getHumanItemType();
				}
			}
		}
		return "";
	}
	
	/**
	 * 有効日人事汎用通常情報マップを取得する
	 * @param tableItemList 人事汎用表示テーブル項目設定情報リスト
	 * @param personalId 個人ID
	 * @return 有効日人事汎用通常情報マップ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public LinkedHashMap<String, Map<String, String>> getArrayHumanInfoMapForKey(List<TableItemProperty> tableItemList,
			String personalId) throws MospException {
		// 返却用Map準備
		LinkedHashMap<String, Map<String, String>> arrayRowHumanInfoMap = new LinkedHashMap<String, Map<String, String>>();
		
		// 項目値リスト取得
		List<HumanArrayDtoInterface> valueList = new ArrayList<HumanArrayDtoInterface>();
		
		for (TableItemProperty tableItem : tableItemList) {
			String[] itemNames = tableItem.getItemNames();
			// 項目毎に処理
			for (String itemName : itemNames) {
				
				// 項目値リスト取得
				List<HumanArrayDtoInterface> dataList = dao.findForItemType(personalId, itemName);
				
				// フォーマット文字列(年月日)の場合
				if (dataList.isEmpty()) {
					dataList = findForItemType(personalId, itemName + "Year");
				}
				// フォーマット文字列(電話)の場合
				if (dataList.isEmpty()) {
					dataList = findForItemType(personalId, itemName + "Area");
				}
				
				// フォーマット文字列(連結)の場合
				if (dataList.isEmpty()) {
					String[] aryItemName = MospUtility.split(itemName, MospConst.APP_PROPERTY_SEPARATOR);
					if (aryItemName.length != 0) {
						dataList = findForItemType(personalId, aryItemName[0]);
					}
				}
				// 重複行削除
				for (int i = 0; i < dataList.size() - 1; i++) {
					for (int j = i + 1; j < dataList.size();) {
						if (dataList.get(i).getHumanRowId() == dataList.get(j).getHumanRowId()) {
							dataList.remove(j);
						} else {
							++j;
						}
					}
				}
				// リスト値設定
				for (HumanArrayDtoInterface dto : dataList) {
					valueList.add(dto);
				}
			}
		}
		
		// 項目値リスト毎に処理
		for (HumanArrayDtoInterface dto : valueList) {
			
			HashMap<String, String> arrayRowMap = new HashMap<String, String>();
			arrayRowMap.put(PlatformHumanConst.PRM_HUMAN_ARRAY_DATE, DateUtility.getStringDate(dto.getActivateDate()));
			
			arrayRowHumanInfoMap.put(String.valueOf(dto.getHumanRowId()), arrayRowMap);
		}
		
		return arrayRowHumanInfoMap;
	}
	
	@Override
	public String[] getArrayActiveDate(LinkedHashMap<String, Map<String, String>> rowIdArrayMapInfo) {
		// 有効日リスト取得
		List<String> rowIdList = new ArrayList<String>(rowIdArrayMapInfo.keySet());
		String[] arrayRowId = new String[rowIdList.size()];
		if (rowIdList.isEmpty()) {
			return arrayRowId;
		}
		// ソート
		Collections.sort(rowIdList);
		for (int i = 0; i < rowIdList.size(); i++) {
			arrayRowId[i] = rowIdList.get(i);
		}
		
		return arrayRowId;
	}
	
	@Override
	public LinkedHashMap<String, Long> getRecordsMap() {
		return recordsMap;
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getArrayHumanInfoMap() {
		return arrayHumanInfoMap;
	}
	
}
