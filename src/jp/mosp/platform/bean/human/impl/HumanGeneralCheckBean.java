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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospMethodInvoker;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.CheckConfigProperty;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.bean.human.ExtraHumanGeneralCheckBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralCheckBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.dto.human.HumanArrayDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;
import jp.mosp.platform.human.action.HumanArrayCardAction;
import jp.mosp.platform.human.action.HumanHistoryCardAction;
import jp.mosp.platform.human.action.HumanNormalCardAction;

/**
 * 人事汎用管理機能チェッククラス。
 */
public class HumanGeneralCheckBean extends PlatformHumanBean implements HumanGeneralCheckBeanInterface {
	
	private String											INPUT_CHECK_BEAN				= "jp.mosp.platform.utils.InputCheckUtility";
	
	/**
	 * キー分割文字列：シングルコーテーション
	 */
	private static String									KEY_PROPERTY_SEPARATOR_SINGLE	= "'";
	
	/**
	 * キー分割文字列：ダブルコーテーション
	 */
	private static String									KEY_PROPERTY_SEPARATOR_DOUBLE	= "\"";
	
	/**
	 * キー分割文字列：プラス
	 */
	private static String									KEY_PROPERTY_SEPARATOR_PLUS		= "\\+";
	
	/**
	 * キー結合文字列：年
	 */
	private static String									KEY_PROPERTY_CONCAT_YEAR		= "Year";
	
	/**
	 * キー結合文字列：月
	 */
	private static String									KEY_PROPERTY_CONCAT_MONTH		= "Month";
	
	/**
	 * キー結合文字列：日
	 */
	private static String									KEY_PROPERTY_CONCAT_DAY			= "Day";
	
	/**
	 * クラス連結文字列：ピリオド
	 */
	private static String									CLASS_CONCAT_PERIOD				= ".";
	
	/**
	 * キー結合文字列：行数
	 */
	private static String									KEY_PROPERTY_ROW_COUNT			= "RowCount";
	
	/**
	 * 入力チェック用マップ
	 */
	protected Map<Integer, LinkedHashMap<String, String>>	generalCheckMap;
	
	/**
	 * 人事汎用管理機能クラス。<br>
	 */
	protected HumanGeneralBeanInterface						humanGeneral;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public HumanGeneralCheckBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元の処理を実行
		super.initBean();
		// Beanを準備
		humanGeneral = createBeanInstance(HumanGeneralBeanInterface.class);
	}
	
	@Override
	public void validate(String division, String viewKey) throws MospException {
		// チェック設定クラス取得
		CheckConfigProperty checkConfig = getCheckConfig(division);
		
		// checkConfig未設定
		if (checkConfig == null) {
			return;
		}
		
		// チェック実施
		executeCheckMethod(checkConfig);
		
		// 拡張入力チェックが存在する場合
		for (String beanName : checkConfig.getExtraCheckList()) {
			ExtraHumanGeneralCheckBeanInterface bean = (ExtraHumanGeneralCheckBeanInterface)createBean(beanName);
			// 拡張入力チェック実施
			bean.extraValidate(division, viewKey);
		}
	}
	
	@Override
	public void validate(String division, List<? extends PlatformDtoInterface> listPlatFormDto) throws MospException {
		// チェック設定クラス取得
		CheckConfigProperty checkConfig = getCheckConfig(division);
		generalCheckMap = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();
		// checkConfig未設定の場合
		if (checkConfig == null) {
			return;
		}
		// データが存在しない場合
		if (listPlatFormDto == null) {
			return;
		}
		
		// 入力チェック用マップ形成
		getInputCheckMap(division, listPlatFormDto);
		
		// 区分と合致しない場合
		if (generalCheckMap.isEmpty()) {
			return;
		}
		
		// チェック実施
		executeCheckMethod(checkConfig);
		
		// 拡張入力チェックが存在する場合
		for (String beanName : checkConfig.getExtraCheckList()) {
			
			ExtraHumanGeneralCheckBeanInterface bean = (ExtraHumanGeneralCheckBeanInterface)createBean(beanName);
			
			// 拡張入力チェック実施
			bean.extraValidate(division, listPlatFormDto);
		}
	}
	
	/**
	 * CheckConfig取得
	 * @param division 人事汎用管理区分
	 * @return CheckConfig チェック設定クラス
	 */
	private CheckConfigProperty getCheckConfig(String division) {
		// 読み込んだ情報を取得
		ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(division);
		return viewConfig.getCheckConfig(division);
	}
	
	/**
	 * チェック用データマップ取得
	 * @param division 人事汎用管理区分
	 * @param listPlatFormDto 入力対象リスト
	 */
	private void getInputCheckMap(String division, List<? extends PlatformDtoInterface> listPlatFormDto) {
		// 変数設定
		String key = MospConst.STR_EMPTY;
		String value = MospConst.STR_EMPTY;
		String viewKey = MospConst.STR_EMPTY;
		int idx = 0;
		String firstKey = MospConst.STR_EMPTY;
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < listPlatFormDto.size(); i++) {
			// 変数初期化
			key = MospConst.STR_EMPTY;
			value = MospConst.STR_EMPTY;
			// 通常
			if (listPlatFormDto.get(i) instanceof HumanNormalDtoInterface) {
				HumanNormalDtoInterface divisionDto = (HumanNormalDtoInterface)listPlatFormDto.get(i);
				key = divisionDto.getHumanItemType();
				value = divisionDto.getHumanItemValue();
				viewKey = HumanNormalCardAction.KEY_VIEW_NORMAL_CARD;
			}
			// 一覧
			if (listPlatFormDto.get(i) instanceof HumanArrayDtoInterface) {
				HumanArrayDtoInterface divisionDto = (HumanArrayDtoInterface)listPlatFormDto.get(i);
				key = divisionDto.getHumanItemType();
				value = divisionDto.getHumanItemValue();
				viewKey = HumanArrayCardAction.KEY_VIEW_ARRAY_CARD;
			}
			// 履歴
			if (listPlatFormDto.get(i) instanceof HumanHistoryDtoInterface) {
				HumanHistoryDtoInterface divisionDto = (HumanHistoryDtoInterface)listPlatFormDto.get(i);
				key = divisionDto.getHumanItemType();
				value = divisionDto.getHumanItemValue();
				viewKey = HumanHistoryCardAction.KEY_VIEW_HUMAN_HISTORY_CARD;
			}
			
			// 初回キーを比較
			if (firstKey.equals(key)) {
				// マップ設定
				generalCheckMap.put(idx, map);
				// 初期化
				map = new LinkedHashMap<String, String>();
				idx++;
			}
			// 初回設定
			if (i == 0) {
				// 基準キー取得
				firstKey = key;
				
			}
			// 人事汎用項目情報リストを取得
			List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division, viewKey);
			// 人事汎用項目情報毎に処理
			for (TableItemProperty tableItem : tableItemList) {
				// 項目名取得
				String[] itemNames = tableItem.getItemNames();
				// 項目名毎に処理
				for (String itemName : itemNames) {
					// 空の場合
					if (itemName.isEmpty()) {
						continue;
					}
					if (itemName.equals(key)) {
						// Mapに設定
						map.put(key, value);
					}
				}
			}
			// 最終行の場合
			if (listPlatFormDto.size() <= i + 1) {
				generalCheckMap.put(idx, map);
			}
		}
	}
	
	/**
	 * 入力チェック実行
	 * @param checkConfig チェック設定クラス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void executeCheckMethod(CheckConfigProperty checkConfig) throws MospException {
		
		// チェックタグ中身取得
		List<LinkedHashMap<String, String>> list = checkConfig.getCheckItem();
		// 取得した入力チェックごとに実施を行う
		for (LinkedHashMap<String, String> map : list) {
			
			// 入力チェック名称、入力値を取得
			for (Entry<String, String> entry : map.entrySet()) {
				// 使用メソッド名取得
				String methodName = INPUT_CHECK_BEAN + CLASS_CONCAT_PERIOD + entry.getKey();
				// メソッド名取得
				Method method = MospMethodInvoker.getMethod(methodName);
				
				// メソッドが存在しない場合
				if (method == null) {
					continue;
				}
				
				// インポート用チェック
				if (generalCheckMap != null) {
					int idx = 0;
					for (Integer keySet : generalCheckMap.keySet()) {
						// 引数の取得
						String[] paramArray = getCheckParam(entry.getKey(), entry.getValue(), keySet, idx);
						// メソッド実行
						MospMethodInvoker.invokeStaticMethod(methodName, mospParams, paramArray);
						idx++;
					}
					continue;
				}
				
				// 引数の取得
				String[] paramArray = getCheckParam(entry.getKey(), entry.getValue(), null, null);
				// メソッド実行
				MospMethodInvoker.invokeStaticMethod(methodName, mospParams, paramArray);
				
			}
			
		}
		
	}
	
	/**
	 * チェックメソッド引数値設定
	 * @param key チェック項目キー
	 * @param values チェック項目引数
	 * @param humanImportKey インポートデータマップキー
	 * @param rowIndex インポートデータマップINDEX
	 * @return チェックメソッド引数配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private String[] getCheckParam(String key, String values, Integer humanImportKey, Integer rowIndex)
			throws MospException {
		// 引数情報取得
		String[] param = MospUtility.split(values, MospConst.APP_PROPERTY_SEPARATOR);
		String[] paramArray = new String[param.length];
		
		for (int i = 0; i < param.length; i++) {
			// 引数値取得用変数
			String paramValue = "";
			
			// リストの存在有無で取得先を判定
			paramValue = getParamValue(param[i], humanImportKey, rowIndex);
			
			// 引数を設定(NULLを考慮)
			paramArray[i] = paramValue;
			
		}
		return paramArray;
	}
	
	/**
	 * 入力チェックに使用する
	 * @param key リクエストキー
	 * @param humanImportKey リクエストキー
	 * @param rowIndex インポートデータマップINDEX
	 * @return リクエスト値(日付の場合は日付文字列)
	 * @throws MospException 文字列取得で例外が起こった場合
	 */
	private String getParamValue(String key, Integer humanImportKey, Integer rowIndex) throws MospException {
		// リクエストキー部分一致用リスト
		List<String> list = new ArrayList<String>();
		
		// XMLやキーをそのまま取得する場合
		String value = getParamsNameForXml(key);
		
		if (!value.isEmpty()) {
			// 行数返却の場合
			if (value.equals(KEY_PROPERTY_ROW_COUNT)) {
				return rowIndex == null ? null : String.valueOf(rowIndex);
			}
			return value;
		}
		
		// インポートからの取得
		if (generalCheckMap != null) {
			LinkedHashMap<String, String> map = generalCheckMap.get(humanImportKey);
			
			// 引数キーでリクエストキー部分検索(前方一致)
			for (String requestParam : map.keySet()) {
				if (requestParam.startsWith(key)) {
					list.add(requestParam);
				}
			}
			// 日付の場合
			if (list.size() == 3) {
				// リクエスト値取得
				String year = map.get(key + KEY_PROPERTY_CONCAT_YEAR);
				String month = map.get(key + KEY_PROPERTY_CONCAT_MONTH);
				String day = map.get(key + KEY_PROPERTY_CONCAT_DAY);
				
				// 日付型文字列取得
				return year == null ? "" : DateUtility.getStringDate(DateUtility.getDate(year, month, day));
			}
			// リクエスト値を取得
			return map.get(key);
			
		}
		
		// 引数キーでリクエストキー部分検索(前方一致)
		for (Entry<String, String[]> requestParam : mospParams.getRequestParamsMap().entrySet()) {
			if (requestParam.getKey().startsWith(key)) {
				list.add(requestParam.getKey());
			}
		}
		// 日付の場合
		if (list.size() == 3) {
			// リクエスト値取得
			String year = mospParams.getRequestParam(key + KEY_PROPERTY_CONCAT_YEAR);
			String month = mospParams.getRequestParam(key + KEY_PROPERTY_CONCAT_MONTH);
			String day = mospParams.getRequestParam(key + KEY_PROPERTY_CONCAT_DAY);
			
			// 日付型文字列取得
			return year == null ? "" : DateUtility.getStringDate(DateUtility.getDate(year, month, day));
		}
		
		// リクエスト値を取得
		return mospParams.getRequestParam(key);
	}
	
	/**
	 * リクエスト、DTO以外から取得
	 * @param key 名称キー
	 * @return 名称文字列
	 */
	private String getParamsNameForXml(String key) {
		// キーをそのまま使用する場合
		if (-1 < key.indexOf(KEY_PROPERTY_SEPARATOR_SINGLE)) {
			// シングルコートを置換
			return key.replace(KEY_PROPERTY_SEPARATOR_SINGLE, MospConst.STR_EMPTY);
		}
		// naming.xmlから名称を取得、または行数を指定する場合
		if (-1 < key.indexOf(KEY_PROPERTY_SEPARATOR_DOUBLE)) {
			// キーからダブルコーテーションを除去
			String newkey = key.replace(KEY_PROPERTY_SEPARATOR_DOUBLE, MospConst.STR_EMPTY);
			if (newkey.equals(KEY_PROPERTY_ROW_COUNT)) {
				return newkey;
			}
			// キーが連結されている場合
			String[] arrayKey = MospUtility.split(newkey, KEY_PROPERTY_SEPARATOR_PLUS);
			// 連結文字列準備
			StringBuffer sb = new StringBuffer();
			
			for (String strs : arrayKey) {
				// 被連結文字確認
				if (strs == null || strs.isEmpty()) {
					continue;
				}
				// 被連結文字追加
				sb.append(NameUtility.getName(mospParams, strs));
			}
			return sb.toString();
		}
		return MospConst.STR_EMPTY;
	}
}
