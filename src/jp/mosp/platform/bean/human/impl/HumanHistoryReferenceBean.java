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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanHistoryDaoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;

/**
 * 人事汎用履歴情報参照クラス。
 */
public class HumanHistoryReferenceBean extends HumanGeneralBean implements HumanHistoryReferenceBeanInterface {
	
	/**
	 * 人事汎用履歴情報DAO。
	 */
	protected HumanHistoryDaoInterface								dao;
	
	/**
	 * 人事有効日履歴情報汎用マップ。
	 */
	protected LinkedHashMap<String, Map<String, String>>			historyHumanInfoMap;
	
	/**
	 * 人事履歴情報汎用マップ準備。
	 */
	protected Map<String, String>									historyMap;
	
	/**
	 * 人事履歴レコード識別IDマップ
	 */
	protected LinkedHashMap<String, Long>							recordsMap;
	
	/**
	 * 人事履歴レコード識別IDマップ（履歴一覧時使用）
	 */
	protected LinkedHashMap<String, LinkedHashMap<String, Long>>	historyReferenceInfoMap;
	/**
	 * 人事汎用項目情報リスト
	 */
	protected List<TableItemProperty>								tableItemList;
	
	
	/**
	 * {@link HumanGeneralBean#HumanGeneralBean()}を実行する。<br>
	 */
	public HumanHistoryReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanHistoryDaoInterface.class);
	}
	
	@Override
	public void setCommounInfo(String division, String viewKey) {
		// 人事汎用項目区分設定情報取得
		conventionProperty = mospParams.getProperties().getConventionProperties()
			.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
		// 人事汎用項目情報リストを取得
		tableItemList = getTableItemList(division, viewKey);
		// 人事有効日履歴情報汎用マップ初期化
		historyHumanInfoMap = new LinkedHashMap<String, Map<String, String>>();
		// 人事履歴情報汎用マップ準備
		historyMap = new HashMap<String, String>();
		// レコード識別IDマップ準備
		recordsMap = new LinkedHashMap<String, Long>();
		// レコード識別IDマップ準備（履歴一覧）
		historyReferenceInfoMap = new LinkedHashMap<String, LinkedHashMap<String, Long>>();
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getHistoryMapInfo(String division, String viewKey,
			String personalId, Date activeDate) throws MospException {
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
				// 人事汎用通常情報取得
				HumanHistoryDtoInterface dto = findForKey(personalId, itemName, activeDate);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					historyMap.put(itemName, "");
					continue;
				}
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						false);
				if (pulldownValue.isEmpty() == false) {
					historyMap.put(itemName, pulldownValue);
					// 項目が終りの場合
					if (i == itemNames.length - 1) {
						historyHumanInfoMap.put(DateUtility.getStringDate(dto.getActivateDate()), historyMap);
					}
					continue;
				}
				// 値を設定
				historyMap.put(itemName, dto.getHumanItemValue());
				historyHumanInfoMap.put(DateUtility.getStringDate(dto.getActivateDate()), historyMap);
			}
		}
		return historyHumanInfoMap;
	}
	
	@Override
	public void getHistoryRecordMapInfo(String division, String viewKey, String personalId, Date activeDate)
			throws MospException {
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
				// 人事汎用通常情報取得
				HumanHistoryDtoInterface dto = findForKey(personalId, itemName, activeDate);
				// 人事汎用通常情報がない場合
				if (dto == null) {
					continue;
				}
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						false);
				if (pulldownValue.isEmpty() == false) {
					dto.setHumanItemValue(pulldownValue);
				}
				// 値を設定
				historyMap.put(itemName, dto.getHumanItemValue());
				historyHumanInfoMap.put(DateUtility.getStringDate(dto.getActivateDate()), historyMap);
				recordsMap.put(itemName, dto.getPfaHumanHistoryId());
			}
		}
	}
	
	@Override
	public Map<String, String> getBeforeHumanHistoryMapInfo(String division, String viewKey, String personalId,
			Date activeDate, Date targetDate) throws MospException {
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
				// 人事汎用通常情報取得
				HumanHistoryDtoInterface dto = findForInfoItems(personalId, itemName, activeDate);
				
				// 人事汎用通常情報がない場合
				if (dto == null) {
					List<HumanHistoryDtoInterface> humanList = findForHistory(personalId, itemName);
					if (humanList.isEmpty()) {
						historyMap.put(itemName, "");
						continue;
					}
					dto = humanList.get(0);
				}
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						false);
				if (pulldownValue.isEmpty() == false) {
					historyMap.put(itemName, pulldownValue);
					continue;
				}
				// 値を設定
				historyMap.put(itemName, dto.getHumanItemValue());
			}
		}
		return historyMap;
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getHumanHistoryMapInfo(String division, String viewKey,
			String personalId, Date activeDate, Date targetDate) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目ラベルキーを取得
			String[] labelKeys = tableItem.getLabelKeys();
			// 人事汎用項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 人事汎用項目名取得
				String itemName = itemNames[i];
				// 人事汎用項目キー取得
				String itemKey = itemKeys[i];
				// 人事汎用項目ラベルキーを取得する
				String labelKey = labelKeys[i];
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				
				String humanItemValue = "";
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKey);
				
				// 人事汎用通常情報取得
				HumanHistoryDtoInterface dto = findForInfoItems(personalId, itemName, activeDate);
				
				if (dto == null) {
					continue;
				}
				// プルダウンのコードから名称を取得し設定
				historyMap = historyHumanInfoMap.get(DateUtility.getStringDate(dto.getActivateDate()));
				if (historyMap == null) {
					historyMap = new HashMap<String, String>();
				}
				
				// フォーマットに合わせ合体させたデータを取得
				String dateTypeValue = getSeparateTxtItemHistoryValue(personalId, itemName, itemProperty, activeDate,
						targetDate, labelKey);
				// 設定
				historyMap.put(itemName, dateTypeValue);
				
				humanItemValue = dateTypeValue;
				
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(), itemName,
						true);
				// 存在しない場合は空になるので、空で比較
				if (pulldownValue.isEmpty() == false) {
					historyMap.put(itemName, pulldownValue);
					humanItemValue = pulldownValue;
					
				}
				
				// 値を設定
				if (humanItemValue.isEmpty()) {
					historyMap.put(itemName, dto.getHumanItemValue());
				}
				
				historyHumanInfoMap.put(DateUtility.getStringDate(dto.getActivateDate()), historyMap);
			}
		}
		return historyHumanInfoMap;
	}
	
	@Override
	public void getActiveDateHistoryMapInfo(String division, String viewKey, String personalId, Date targetDate)
			throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		// 有効日セット取得
		HashSet<Date> activeSet = getActiveDateList(tableItemList, personalId);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目ラベルキーを取得する
			String[] labelKeys = tableItem.getLabelKeys();
			// 有効日セット毎に処理
			for (Date activeDate : activeSet) {
				
				// 人事汎用項目毎に処理
				for (int i = 0; i < itemNames.length; i++) {
					
					// プルダウンのコードから名称を取得し設定（日付は文字列）
					historyMap = historyHumanInfoMap.get(DateUtility.getStringDate(activeDate));
					recordsMap = historyReferenceInfoMap.get(DateUtility.getStringDate(activeDate));
					
					// 有効日で取得出来ない場合はインスタンス作成
					if (historyMap == null) {
						historyMap = new HashMap<String, String>();
					}
					if (recordsMap == null) {
						recordsMap = new LinkedHashMap<String, Long>();
					}
					
					// 人事汎用履歴情報取得
					HumanHistoryDtoInterface dto = findForInfoItems(personalId, itemNames[i], activeDate);
					
					// 人事汎用項目設定情報取得
					ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
					
					// 変換用項目値
					String humanItemValue = "";
					
					// フォーマットに合わせ合体させたデータを取得
					humanItemValue = getSeparateTxtItemHistoryValue(personalId, itemNames[i], itemProperty, activeDate,
							targetDate, labelKeys[i]);
					
					// DTOが存在しない場合
					if (dto == null) {
						// フォーマットデータを設定
						historyMap.put(itemNames[i], humanItemValue);
						continue;
					}
					
					// フォーマットデータが存在しない場合
					if (humanItemValue.isEmpty()) {
						// プルダウン名称を取得する
						humanItemValue = getPulldownValue(itemProperty, activeDate, dto.getHumanItemValue(),
								itemNames[i], true);
					}
					
					// フォーマットデータを設定
					historyMap.put(itemNames[i], humanItemValue);
					
					// 値が空の場合はDTOデータに置き換え
					if (humanItemValue.isEmpty()) {
						historyMap.put(dto.getHumanItemType(), dto.getHumanItemValue());
					}
					// レコード識別ID設定
					recordsMap.put(dto.getHumanItemType(), dto.getPfaHumanHistoryId());
					
					// 有効日人事汎用履歴情報マップに詰める
					historyHumanInfoMap.put(DateUtility.getStringDate(activeDate), historyMap);
					// 履歴一覧用レコード識別IDを取得
					historyReferenceInfoMap.put(DateUtility.getStringDate(activeDate), recordsMap);
					
				}
			}
		}
	}
	
	@Override
	public void getHistoryListData(String division, String viewKey) throws MospException {
		// 既に取得済みの情報を使用するため変更が必要な箇所のみ初期化
		
		// 人事汎用項目区分設定情報取得
		conventionProperty = mospParams.getProperties().getConventionProperties()
			.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
		// 人事汎用項目情報リストを取得
		tableItemList = getTableItemList(division, viewKey);
		
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			
			// 有効日毎にデータを確認
			for (Entry<String, Map<String, String>> set : historyHumanInfoMap.entrySet()) {
				// 人事汎用項目毎に処理
				for (int i = 0; i < itemNames.length; i++) {
					// 人事汎用項目設定情報取得
					ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
					// データ区分が存在しない場合、処理無し
					if (itemProperty == null) {
						continue;
					}
					if (itemProperty.getDataType() == null) {
						continue;
					}
					// データ区分有りのKEY取得
					Map<String, String> map = set.getValue();
					
					// データ区分有りのKEY取得（値追加用）
					Map<String, String> valueMap = set.getValue();
					
					// マップに設定する値
					String value = "";
					
					// データ区分取得
					String dataType = itemProperty.getDataType();
					
					// 日付又は年齢の場合
					if (dataType.equals(KEY_FORMAT_DATE) || dataType.equals(KEY_FORMAT_AGE)) {
						// 日付を取得（記述が長いため外出し）
						Date date = DateUtility.getDate(map.get(itemNames[i] + KEY_FORMAT_YEAR),
								map.get(itemNames[i] + KEY_FORMAT_MONTH), map.get(itemNames[i] + KEY_FORMAT_DAY));
						
						// フォーマット文字列取得（日付又は年齢）
						value = getFormatDateAge(date, itemProperty, DateUtility.getDate(set.getKey()));
					}
					
					// 人事汎用項目データ型が電話の場合
					if (dataType.equals(KEY_FORMAT_PHONE)) {
						// 電話
						value = getFormatPhone(map.get(itemNames[i] + KEY_FORMAT_PHONE_1),
								map.get(itemNames[i] + KEY_FORMAT_PHONE_2), map.get(itemNames[i] + KEY_FORMAT_PHONE_3),
								itemProperty.getFormat());
					}
					
					// 配列で項目を取得
					String[] itemSepaleteNames = MospUtility.split(itemNames[i], MospConst.APP_PROPERTY_SEPARATOR);
					// フォーマット設定用配列
					String[] aryReplaceText = new String[itemSepaleteNames.length];
					
					int idx = 0;
					for (String replaceText : itemSepaleteNames) {
						// Nameが一致しない場合
						if (!map.containsKey(replaceText)) {
							continue;
						}
						// 項目結合の場合
						if (dataType.equals(KEY_CONCATENATED_LABEL)) {
							value += map.get(replaceText) == null ? "" : map.get(replaceText);
						}
						// フォーマット設定の場合
						if (dataType.equals(KEY_FORMAT)) {
							aryReplaceText[idx++] = map.get(replaceText) == null ? "" : map.get(replaceText);
							if (itemSepaleteNames.length == idx) {
								// 値を整形
								value = getReplaceFormat(itemProperty.getFormat(), aryReplaceText);
							}
						}
					}
					// マップに設定
					valueMap.put(itemNames[i], value);
					
					// 表示用マップに設定
					historyHumanInfoMap.put(set.getKey(), valueMap);
					
				}
			}
		}
	}
	
	@Override
	public String getHistoryItemValue(String division, String viewKey, String personalId, Date targetDate,
			String tableItemKey, boolean isPulldownName) throws MospException {
		// 共通情報設定
		setCommounInfo(division, viewKey);
		// 人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目キーが違う場合
			if (tableItem.getKey().equals(tableItemKey) == false) {
				continue;
			}
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目ラベルキー取得
			String[] labelKeys = tableItem.getLabelKeys();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			// 有効日
			Date activeDate;
			
			// 人事汎用項目毎に処理
			for (int i = 0; i < itemKeys.length;) {
				// 対象年月日から見て最新の有効日を取得
				HumanHistoryDtoInterface dto = findForInfoItems(personalId, itemNames[i], targetDate);
				if (dto == null) {
					return "";
				}
				// 有効日取得
				activeDate = dto.getActivateDate();
				
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
				// 人事汎用項目キーが有効日の場合
				if (itemKeys[i].equals(PlatformHumanConst.PRM_IMPORT_HISTORY_ARRAY_ACTIVATE_DATE)) {
					// 人事汎用履歴情報取得
					dto = findForInfo(personalId, itemNames[i], activeDate);
					if (dto == null) {
						return "";
					}
					// 有効日取得
					return getStringDate(dto.getActivateDate());
				}
				// フォーマットに合わせ合体させたデータを取得
				String dateTypeValue = getSeparateTxtItemHistoryValue(personalId, itemNames[i], itemProperty,
						activeDate, targetDate, labelKeys[i]);
				if (dateTypeValue.isEmpty() == false) {
					return dateTypeValue;
				}
				// 人事汎用履歴情報取得
				dto = findForInfo(personalId, itemNames[i], activeDate);
				if (dto == null) {
					return "";
				}
				// プルダウン名称を取得する
				String pulldownValue = getPulldownValue(itemProperty, dto.getActivateDate(), dto.getHumanItemValue(),
						itemNames[i], isPulldownName);
				if (pulldownValue.isEmpty() == false) {
					return pulldownValue;
				}
				return dto.getHumanItemValue();
			}
		}
		return "";
		
	}
	
	@Override
	public List<HumanHistoryDtoInterface> findForHistory(String personalId, String humanItemType) throws MospException {
		return dao.findForHistory(personalId, humanItemType);
	}
	
	@Override
	public HumanHistoryDtoInterface findForInfo(String personalId, String humanItemType, Date targetDate)
			throws MospException {
		return dao.findForInfo(personalId, humanItemType, targetDate);
	}
	
	private HumanHistoryDtoInterface findForInfoItems(String personalId, String humanItemType, Date targetDate)
			throws MospException {
		HumanHistoryDtoInterface dto = dao.findForInfo(personalId, humanItemType, targetDate);
		
		// 日付有効日取得
		if (dto == null) {
			dto = findForInfo(personalId, humanItemType + "Year", targetDate);
		}
		
		// 電話有効日取得
		if (dto == null) {
			dto = findForInfo(personalId, humanItemType + "Area", targetDate);
		}
		// カンマ区切り
		if (dto == null) {
			String[] aryItemName = MospUtility.split(humanItemType, MospConst.APP_PROPERTY_SEPARATOR);
			if (aryItemName.length != 0) {
				// 上記以外のフォーマットの場合
				dto = findForInfo(personalId, aryItemName[0], targetDate);
			}
		}
		
		return dto;
		
	}
	
	@Override
	public HumanHistoryDtoInterface findForKey(String personalId, String humanItemType, Date activateDate)
			throws MospException {
		return dao.findForKey(personalId, humanItemType, activateDate);
	}
	
	/**
	 * 対象項目の有効日セットを取得する。
	 * @param tableItemList 人事汎用表示テーブル項目設定情報リスト
	 * @param personalId 個人ID
	 * @return 有効日セット
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public HashSet<Date> getActiveDateList(List<TableItemProperty> tableItemList, String personalId)
			throws MospException {
		// 有効日リスト準備
		List<Date> activeDateList = new ArrayList<Date>();
		for (TableItemProperty tableItem : tableItemList) {
			String[] itemNames = tableItem.getItemNames();
			// 項目毎に処理
			for (String itemName : itemNames) {
				// 配列に変換
				String[] itemNameSplit = MospUtility.split(itemName, MospConst.APP_PROPERTY_SEPARATOR);
				// 項目名配列毎に処理
				for (String item : itemNameSplit) {
					// 項目値リスト取得
					List<HumanHistoryDtoInterface> valueList = findForHistory(personalId, item);
					// 日付有効日取得
					if (valueList.isEmpty()) {
						valueList = findForHistory(personalId, item + "Year");
					}
					
					// 電話有効日取得
					if (valueList.isEmpty()) {
						valueList = findForHistory(personalId, item + "Area");
					}
					// カンマ区切り
					if (valueList.isEmpty()) {
						String[] aryItemName = MospUtility.split(item, MospConst.APP_PROPERTY_SEPARATOR);
						if (aryItemName.length != 0) {
							// 上記以外のフォーマットの場合
							valueList = findForHistory(personalId, aryItemName[0]);
						}
					}
					
					// 項目値リスト毎に処理
					for (HumanHistoryDtoInterface dto : valueList) {
						// 有効日を詰める
						activeDateList.add(dto.getActivateDate());
					}
				}
			}
		}
		// 有効日セット準備
		HashSet<Date> activeDateSet = new HashSet<Date>();
		// 重複削除
		activeDateSet.addAll(activeDateList);
		
		return activeDateSet;
		
	}
	
	@Override
	public String[] getArrayActiveDate(LinkedHashMap<String, Map<String, String>> activeDateHistoryMapInfo) {
		// 有効日リスト取得
		List<String> activeList = new ArrayList<String>(activeDateHistoryMapInfo.keySet());
		String[] arrayActiveDate = new String[activeList.size()];
		if (activeList.isEmpty()) {
			return arrayActiveDate;
		}
		// ソート
		Collections.sort(activeList);
		for (int i = 0; i < activeList.size(); i++) {
			arrayActiveDate[i] = activeList.get(activeList.size() - i - 1);
		}
		
		return arrayActiveDate;
	}
	
	@Override
	public LinkedHashMap<String, Long> getRecordsMap() {
		return recordsMap;
	}
	
	@Override
	public LinkedHashMap<String, Map<String, String>> getHistoryHumanInfoMap() {
		return historyHumanInfoMap;
	}
	
	@Override
	public LinkedHashMap<String, LinkedHashMap<String, Long>> getHistoryReferenceInfoMap() {
		return historyReferenceInfoMap;
	}
	
}
