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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.framework.xml.ViewProperty;
import jp.mosp.framework.xml.ViewTableProperty;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanArrayDaoInterface;
import jp.mosp.platform.dao.human.HumanHistoryDaoInterface;
import jp.mosp.platform.dao.human.HumanNormalDaoInterface;
import jp.mosp.platform.dto.human.HumanArrayDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * 人事汎用管理機能で使用する。
 */
public class HumanGeneralBean extends PlatformHumanBean implements HumanGeneralBeanInterface {
	
	/**
	 * 名称区分マスタ参照クラス。
	 */
	protected NamingReferenceBeanInterface	namingReference;
	
	/**
	 * 人事通常情報DAOクラス。
	 */
	protected HumanNormalDaoInterface		normalDao;
	
	/**
	 * 人事履歴情報DAOクラス。
	 */
	protected HumanHistoryDaoInterface		historyDao;
	
	/**
	 * 人事一覧情報DAOクラス。
	 */
	protected HumanArrayDaoInterface		arrayDao;
	
	/**
	 * フォーマット確認定数：日付の場合(誕生日の場合)。
	 */
	public static final String				KEY_FORMAT_AGE			= "Age";
	
	/**
	 * フォーマット確認定数：日付の場合。
	 */
	public static final String				KEY_FORMAT_DATE			= "Date";
	
	/**
	 * フォーマット確認定数：日付の年を探す場合。
	 */
	public static final String				KEY_FORMAT_YEAR			= "Year";
	
	/**
	 * フォーマット確認定数：日付の月を探す場合。
	 */
	public static final String				KEY_FORMAT_MONTH		= "Month";
	
	/**
	 * フォーマット確認定数：日付の日を探す場合。
	 */
	public static final String				KEY_FORMAT_DAY			= "Day";
	
	/**
	 * フォーマット確認定数：電話番号の場合。
	 */
	public static final String				KEY_FORMAT_PHONE		= "Phone";
	
	/**
	 * フォーマット確認定数：電話番号1。
	 */
	public static final String				KEY_FORMAT_PHONE_1		= "Area";
	
	/**
	 * フォーマット確認定数：電話番号2。
	 */
	public static final String				KEY_FORMAT_PHONE_2		= "Local";
	
	/**
	 * フォーマット確認定数：電話番号3。
	 */
	public static final String				KEY_FORMAT_PHONE_3		= "Subscriber";
	
	/**
	 * フォーマット確認定数：バイナリファイル。
	 */
	public static final String				KEY_FORMAT_BINARY		= "Binary";
	
	/**
	 * フォーマット確認定数：値をくっつける場合。
	 */
	public static final String				KEY_CONCATENATED_LABEL	= "Concatenate";
	
	/**
	 * フォーマット確認定数：置換する場合。
	 */
	public static final String				KEY_FORMAT				= "Format";
	
	/**
	 * 人事汎用項目区分設定情報。
	 */
	protected ConventionProperty			conventionProperty;
	
	/**
	 * 項目内個別人事汎用項目設定情報。
	 */
	protected ItemProperty					labelItemProperty;
	
	/**
	 *  項目内個別人事汎用項目データ型。
	 */
	protected String						labelDateType			= null;
	
	/**
	 * 項目内個別人事汎用項目データ型フォーマット取得
	 */
	protected String						labelFormat				= null;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public HumanGeneralBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元の処理を実行
		super.initBean();
		// DAOを準備
		normalDao = createDaoInstance(HumanNormalDaoInterface.class);
		historyDao = createDaoInstance(HumanHistoryDaoInterface.class);
		arrayDao = createDaoInstance(HumanArrayDaoInterface.class);
		// Beanを準備
		namingReference = createBeanInstance(NamingReferenceBeanInterface.class);
	}
	
	@Override
	public String[][] getHumanGeneralItemPulldown(ItemProperty itemProperty, Date activeDate) throws MospException {
		// 人事汎用表示テーブル項目設定情報確認
		if (itemProperty == null) {
			return null;
		}
		// MosPプルダウンコードキー取得
		String codeKey = itemProperty.getCodeKey();
		boolean isNeedSpace = itemProperty.isNeedSpace();
		if (codeKey != null && codeKey.isEmpty() == false) {
			return mospParams.getProperties().getCodeArray(codeKey, isNeedSpace);
		}
		// 名称区分プルダウンキー取得
		String namingKey = itemProperty.getNamingKey();
		// 名称区分がある場合
		if (namingKey != null) {
			return namingReference.getCodedSelectArray(namingKey, activeDate, isNeedSpace);
		}
		return null;
	}
	
	@Override
	public Map<String, String[][]> getHumanGeneralPulldown(String division, String viewKey, Date activeDate)
			throws MospException {
		if (viewKey == null) {
			return null;
		}
		// 人事汎用項目区分設定情報取得
		ConventionProperty conventionProperty = mospParams.getProperties().getConventionProperties()
			.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
		// プルダウン用マップ準備
		Map<String, String[][]> pulldownMap = new HashMap<String, String[][]>();
		// 人事汎用表示テーブル項目情報リストを取得
		List<TableItemProperty> tableItemList = getTableItemList(division, viewKey);
		// 人事汎用表示テーブル項目情報リスト毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キー毎に処理
			for (int i = 0; i < itemKeys.length; i++) {
				// 空の場合
				if (itemKeys[i].isEmpty()) {
					continue;
				}
				// 人事汎用項目設定情報取得
				ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
				// 対象項目プルダウン設定
				String[][] pulldown = getHumanGeneralItemPulldown(itemProperty, activeDate);
				// プルダウンが存在する場合
				if (pulldown != null) {
					// マップにつめる
					pulldownMap.put(itemNames[i], pulldown);
				}
			}
		}
		return pulldownMap;
	}
	
	@Override
	public String getPulldownValue(ItemProperty itemProperty, Date activeDate, String value, String itemName,
			boolean isPulldownName) throws MospException {
		// 人事汎用項目設定情報確認
		if (itemProperty == null) {
			return "";
		}
		// プルダウン取得
		String[][] pulldown = getHumanGeneralItemPulldown(itemProperty, activeDate);
		// プルダウンがあり人事汎用項目設定情報がある場合
		if (pulldown != null) {
			// MosPコードキー取得
			String codeKey = itemProperty.getCodeKey();
			if (codeKey != null && codeKey.isEmpty() == false) {
				if (isPulldownName) {
					// プルダウン名称を設定
					return MospUtility.getCodeName(value, pulldown);
				}
				// コード
				return value;
			}
			// 名称区分コード取得
			String namingType = itemProperty.getNamingKey();
			if (namingType != null && namingType.isEmpty() == false) {
				if (isPulldownName) {
					return namingReference.getNamingItemName(namingType, value, activeDate);
				}
				// コード
				return value;
			}
		}
		return "";
	}
	
	@Override
	public List<TableItemProperty> getTableItemList(String division, String viewKey) {
		// 人事汎用管理区分設定取得
		ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(division);
		ViewProperty viewProperty = viewConfig.getView(viewKey);
		// 情報確認
		if (viewProperty == null) {
			return new ArrayList<TableItemProperty>();
		}
		// 人事汎用テーブル情報取得
		String[] viewTableKeys = viewProperty.getViewTableKeys();
		// 人事汎用項目情報リスト準備
		List<TableItemProperty> tableItemList = new ArrayList<TableItemProperty>();
		// 人事汎用項目情報リスト毎に処理
		for (String viewTableKey : viewTableKeys) {
			// 人事汎用表示テーブル設定取得
			ViewTableProperty viewTableProperty = viewConfig.getViewTable(viewTableKey);
			// 人事汎用項目情報追加
			tableItemList.addAll(viewTableProperty.getTableItem());
		}
		return tableItemList;
	}
	
	@Override
	public Map<String, String[][]> getInputActiveDateGeneralPulldown(String division, String viewKey) {
		if (viewKey == null) {
			return null;
		}
		// プルダウン用マップ準備
		Map<String, String[][]> pulldownMap = new HashMap<String, String[][]>();
		// 人事汎用表示テーブル項目情報リストを取得
		List<TableItemProperty> tableItemList = getTableItemList(division, viewKey);
		// 人事汎用表示テーブル項目情報リスト毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目キー毎に処理
			for (int i = 0; i < itemKeys.length; i++) {
				// 空の場合
				if (itemKeys[i].isEmpty()) {
					continue;
				}
				// 対象項目プルダウンを準備しマップに設定
				pulldownMap.put(itemNames[i], PlatformUtility.getInputActivateDatePulldown(mospParams));
			}
		}
		return pulldownMap;
	}
	
	@Override
	public Date humanNormalDate(String itemName, String personalId) throws MospException {
		// 年取得
		HumanNormalDtoInterface normalYearDto = normalDao.findForInfo(itemName + KEY_FORMAT_YEAR, personalId);
		// 月取得
		HumanNormalDtoInterface normalMonthDto = normalDao.findForInfo(itemName + KEY_FORMAT_MONTH, personalId);
		// 日取得
		HumanNormalDtoInterface normalDayDto = normalDao.findForInfo(itemName + KEY_FORMAT_DAY, personalId);
		// 情報がない場合
		if (normalYearDto == null || normalMonthDto == null || normalDayDto == null) {
			return null;
		}
		// 日付取得
		return DateUtility.getDate(normalYearDto.getHumanItemValue(), normalMonthDto.getHumanItemValue(),
				normalDayDto.getHumanItemValue());
	}
	
	/**
	 * 項目名、個人IDから項目名の日付を取得する。
	 * @param itemName 項目名
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @return 日付
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public Date humanHistoryDate(String itemName, String personalId, Date activeDate) throws MospException {
		// 年取得
		HumanHistoryDtoInterface historyYearDto = historyDao.findForInfo(personalId, itemName + KEY_FORMAT_YEAR,
				activeDate);
		// 月取得
		HumanHistoryDtoInterface historyMonthDto = historyDao.findForInfo(personalId, itemName + KEY_FORMAT_MONTH,
				activeDate);
		// 日取得
		HumanHistoryDtoInterface historyDayDto = historyDao.findForInfo(personalId, itemName + KEY_FORMAT_DAY,
				activeDate);
		// 情報がない場合
		if (historyYearDto == null || historyMonthDto == null || historyDayDto == null) {
			return null;
		}
		// 日付取得
		return DateUtility.getDate(historyYearDto.getHumanItemValue(), historyMonthDto.getHumanItemValue(),
				historyDayDto.getHumanItemValue());
	}
	
	/**
	 * 項目名、個人IDから項目名の日付を取得する。
	 * @param itemName 項目名
	 * @param personalId 個人ID
	 * @param rowId 行ID
	 * @return 日付
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public Date humanArrayDate(String itemName, String personalId, String rowId) throws MospException {
		// 年取得
		HumanArrayDtoInterface arrayYearDto = arrayDao.findForKey(personalId, itemName + KEY_FORMAT_YEAR,
				MospUtility.getInt(rowId));
		// 月取得
		HumanArrayDtoInterface arrayMonthDto = arrayDao.findForKey(personalId, itemName + KEY_FORMAT_MONTH,
				MospUtility.getInt(rowId));
		// 日取得
		HumanArrayDtoInterface arrayDayDto = arrayDao.findForKey(personalId, itemName + KEY_FORMAT_DAY,
				MospUtility.getInt(rowId));
		// 情報がない場合
		if (arrayYearDto == null || arrayMonthDto == null || arrayDayDto == null) {
			return null;
		}
		// 日付取得
		return DateUtility.getDate(arrayYearDto.getHumanItemValue(), arrayMonthDto.getHumanItemValue(),
				arrayDayDto.getHumanItemValue());
	}
	
	/**
	 * 共通情報を設定する。
	 * @param labelKey 項目ラベルキー
	 */
	protected void setCommounInfo(String labelKey) {
		// 項目内個別人事汎用項目区分設定情報取得
		conventionProperty = mospParams.getProperties().getConventionProperties()
			.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
		// 項目内個別人事汎用項目設定情報取得
		labelItemProperty = conventionProperty.getItem(labelKey);
		// 項目内個別人事汎用項目データ型取得
		labelDateType = labelItemProperty.getDataType();
		// 項目内個別人事汎用項目データ型フォーマット取得
		labelFormat = labelItemProperty.getFormat();
	}
	
	@Override
	public String getSeparateTxtItemNormalValue(String personalId, String itemName, ItemProperty itemProperty,
			Date targetDate, String labelKey) throws MospException {
		// 人事汎用項目設定情報確認
		if (itemProperty == null) {
			return "";
		}
		// 人事汎用項目データ型取得
		String dataType = itemProperty.getDataType();
		if (dataType == null) {
			return "";
		}
		// フォーマット取得
		String format = itemProperty.getFormat();
		if (dataType.equals(KEY_FORMAT_DATE) || dataType.equals(KEY_FORMAT_AGE)) {
			// 日付または年齢のフォーマットに合わせて表示文字を作成
			return getNormalFormatDateAge(personalId, itemName, itemProperty, targetDate);
		}
		// 人事汎用項目データ型が電話の場合
		if (dataType.equals(KEY_FORMAT_PHONE)) {
			// 電話のフォーマットに合わせて表示文字を作成
			return getNormalFormatPhone(personalId, itemName, format);
		}
		// 配列で項目を取得
		String[] itemNames = MospUtility.split(itemName, MospConst.APP_PROPERTY_SEPARATOR);
		// 配列で項目ラベルキーを取得
		String[] labelKeys = MospUtility.split(labelKey, MospConst.APP_PROPERTY_SEPARATOR);
		// 項目をくっつけて表示する場合
		if (dataType.equals(KEY_CONCATENATED_LABEL)) {
			// 値準備
			StringBuilder value = new StringBuilder();
			// 項目毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 共通情報設定
				setCommounInfo(labelKeys[i]);
				// 個別項目にデータ型が存在する場合
				if (labelDateType != null) {
					// 日付変換または年齢を出力した値がある場合
					String formatDate = getNormalFormatDateAge(personalId, itemNames[i], labelItemProperty, targetDate);
					if (formatDate.equals("") == false) {
						// 値作成
						value.append(formatDate);
						continue;
					}
					// 電話変換を出力した値がある場合
					String formatPhone = getNormalFormatPhone(personalId, itemNames[i], labelFormat);
					if (formatPhone.equals("") == false) {
						// 値作成
						value.append(formatPhone);
						continue;
					}
				}
				// 人事汎用通常情報取得
				HumanNormalDtoInterface itemDto = normalDao.findForInfo(itemNames[i], personalId);
				if (itemDto != null) {
					// プルダウン値取得
					String pulldownValue = getPulldownValue(labelItemProperty, targetDate, itemDto.getHumanItemValue(),
							itemNames[i], true);
					// プルダウン値がある場合
					if (pulldownValue.isEmpty() == false) {
						// 値作成
						value.append(pulldownValue);
						continue;
					}
					// 値作成 
					value.append(itemDto.getHumanItemValue());
				}
			}
			return value.toString();
		}
		// フォーマット(置換)の場合
		if (dataType.equals(KEY_FORMAT)) {
			// 値準備
			String[] rep = new String[itemNames.length];
			// 項目毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 共通情報設定
				setCommounInfo(labelKeys[i]);
				// 個別項目にデータ型が存在する場合
				if (labelDateType != null) {
					// 日付変換または年齢を出力した値がある場合
					String formatDate = getNormalFormatDateAge(personalId, itemNames[i], labelItemProperty, targetDate);
					if (formatDate.equals("") == false) {
						// 値作成
						rep[i] = formatDate;
						continue;
					}
					// 電話変換を出力した値がある場合
					String formatPhone = getNormalFormatPhone(personalId, itemNames[i], labelFormat);
					if (formatPhone.equals("") == false) {
						// 値作成
						rep[i] = formatPhone;
						continue;
					}
				}
				// 人事汎用通常情報取得
				HumanNormalDtoInterface itemDto = normalDao.findForInfo(itemNames[i], personalId);
				if (itemDto != null) {
					// プルダウン値取得
					String pulldownValue = getPulldownValue(labelItemProperty, targetDate, itemDto.getHumanItemValue(),
							itemNames[i], true);
					// プルダウン値がある場合
					if (pulldownValue.isEmpty() == false) {
						// 値作成
						rep[i] = pulldownValue;
						continue;
					}
					// 値作成 
					rep[i] = itemDto.getHumanItemValue();
					continue;
				}
				rep[i] = "";
			}
			// フォーマット確認
			if (format != null) {
				// 指定された置換文字列作成
				return getReplaceFormat(format, rep);
			}
			return format;
		}
		return "";
	}
	
	@Override
	public String getSeparateTxtItemHistoryValue(String personalId, String itemName, ItemProperty itemProperty,
			Date activeDate, Date targetDate, String labelKey) throws MospException {
		// 人事汎用項目設定情報確認
		if (itemProperty == null) {
			return "";
		}
		// 人事汎用項目データ型取得
		String dataType = itemProperty.getDataType();
		if (dataType == null) {
			return "";
		}
		// フォーマット取得
		String format = itemProperty.getFormat();
		// 日付又は年齢の場合
		if (dataType.equals(KEY_FORMAT_DATE) || dataType.equals(KEY_FORMAT_AGE)) {
			// 日付変化設定
			return getHistoryFormatDateAge(personalId, itemName, itemProperty, activeDate, targetDate);
		}
		// 人事汎用項目データ型が電話の場合
		if (dataType.equals(KEY_FORMAT_PHONE)) {
			// 電話変換設定
			return getHistoryFormatPhone(personalId, itemName, activeDate, format);
		}
		// 配列で項目を取得
		String[] itemNames = MospUtility.split(itemName, MospConst.APP_PROPERTY_SEPARATOR);
		// 配列で項目ラベルキーを取得
		String[] labelKeys = MospUtility.split(labelKey, MospConst.APP_PROPERTY_SEPARATOR);
		// 項目をくっつけて表示する場合
		if (dataType.equals(KEY_CONCATENATED_LABEL)) {
			// 値準備
			StringBuilder value = new StringBuilder();
			// 項目毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 共通情報設定
				setCommounInfo(labelKeys[i]);
				// 個別項目にデータ型が存在する場合
				if (labelDateType != null) {
					// 日付変換または年齢を出力した値がある場合
					String formatDate = getHistoryFormatDateAge(personalId, itemNames[i], labelItemProperty, activeDate,
							targetDate);
					if (formatDate.equals("") == false) {
						// 値作成
						value.append(formatDate);
						continue;
					}
					// 電話変換を出力した値がある場合
					String formatPhone = getHistoryFormatPhone(personalId, itemNames[i], activeDate, labelFormat);
					if (formatPhone.equals("") == false) {
						// 値作成
						value.append(formatPhone);
						continue;
					}
				}
				// 人事汎用通常情報取得
				HumanHistoryDtoInterface itemDto = historyDao.findForInfo(personalId, itemNames[i], activeDate);
				if (itemDto != null) {
					// プルダウン値取得
					String pulldownValue = getPulldownValue(labelItemProperty, targetDate, itemDto.getHumanItemValue(),
							itemNames[i], true);
					// プルダウン値がある場合
					if (pulldownValue.isEmpty() == false) {
						// 値作成
						value.append(pulldownValue);
						continue;
					}
					// 値作成 
					value.append(itemDto.getHumanItemValue());
				}
			}
			return value.toString();
		}
		// フォーマット(置換)の場合
		if (dataType.equals(KEY_FORMAT)) {
			// 値準備
			String[] rep = new String[itemNames.length];
			// 項目毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 共通情報設定
				setCommounInfo(labelKeys[i]);
				// 個別項目にデータ型が存在する場合
				if (labelDateType != null) {
					// 日付変換または年齢を出力した値がある場合
					String formatDate = getHistoryFormatDateAge(personalId, itemNames[i], labelItemProperty, activeDate,
							targetDate);
					if (formatDate.equals("") == false) {
						// 値作成
						rep[i] = formatDate;
						continue;
					}
					// 電話変換を出力した値がある場合
					String formatPhone = getHistoryFormatPhone(personalId, itemNames[i], activeDate, labelFormat);
					if (formatPhone.equals("") == false) {
						// 値作成
						rep[i] = formatPhone;
						continue;
					}
				}
				// 人事汎用通常情報取得
				HumanHistoryDtoInterface itemDto = historyDao.findForInfo(personalId, itemNames[i], activeDate);
				if (itemDto != null) {
					// プルダウン値取得
					String pulldownValue = getPulldownValue(labelItemProperty, targetDate, itemDto.getHumanItemValue(),
							itemNames[i], true);
					// プルダウン値がある場合
					if (pulldownValue.isEmpty() == false) {
						// 値作成
						rep[i] = pulldownValue;
						continue;
					}
					// 値作成 
					rep[i] = itemDto.getHumanItemValue();
					continue;
				}
				rep[i] = "";
			}
			// フォーマット確認
			if (format != null) {
				// 指定された置換文字列作成
				return getReplaceFormat(format, rep);
			}
			return format;
		}
		return "";
	}
	
	@Override
	public String getSeparateTxtItemArrayValue(String personalId, String itemName, ItemProperty itemProperty,
			String rowId, Date targetDate, String labelKey) throws MospException {
		// 人事汎用項目設定情報確認
		if (itemProperty == null) {
			return "";
		}
		// 人事汎用項目データ型取得
		String dataType = itemProperty.getDataType();
		if (dataType == null) {
			return "";
		}
		// フォーマット取得
		String format = itemProperty.getFormat();
		// 日付又は年齢の場合
		if (dataType.equals(KEY_FORMAT_DATE) || dataType.equals(KEY_FORMAT_AGE)) {
			// 日付変化設定
			return getArrayFormatDateAge(personalId, itemName, itemProperty, rowId, targetDate);
		}
		// 人事汎用項目データ型が電話の場合
		if (dataType.equals(KEY_FORMAT_PHONE)) {
			// 電話変換設定
			return getArrayFormatPhone(personalId, itemName, rowId, format);
		}
		// 配列で項目を取得
		String[] itemNames = MospUtility.split(itemName, MospConst.APP_PROPERTY_SEPARATOR);
		// 配列で項目ラベルキーを取得
		String[] labelKeys = MospUtility.split(labelKey, MospConst.APP_PROPERTY_SEPARATOR);
		// 項目をくっつけて表示する場合
		if (dataType.equals(KEY_CONCATENATED_LABEL)) {
			// 値準備
			StringBuilder value = new StringBuilder();
			// 項目毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 共通情報設定
				setCommounInfo(labelKeys[i]);
				// 個別項目にデータ型が存在する場合
				if (labelDateType != null) {
					// 日付変換または年齢を出力した値がある場合
					String formatDate = getArrayFormatDateAge(personalId, itemNames[i], labelItemProperty, rowId,
							targetDate);
					if (formatDate.equals("") == false) {
						// 値作成
						value.append(formatDate);
						continue;
					}
					// 電話変換を出力した値がある場合
					String formatPhone = getArrayFormatPhone(personalId, itemNames[i], rowId, labelFormat);
					if (formatPhone.equals("") == false) {
						// 値作成
						value.append(formatPhone);
						continue;
					}
				}
				// 人事汎用一覧情報取得
				HumanArrayDtoInterface itemDto = arrayDao.findForKey(personalId, itemNames[i],
						MospUtility.getInt(rowId));
				if (itemDto != null) {
					// プルダウン値取得
					String pulldownValue = getPulldownValue(labelItemProperty, targetDate, itemDto.getHumanItemValue(),
							itemNames[i], true);
					// プルダウン値がある場合
					if (pulldownValue.isEmpty() == false) {
						// 値作成
						value.append(pulldownValue);
						continue;
					}
					// 値作成 
					value.append(itemDto.getHumanItemValue());
				}
			}
			return value.toString();
		}
		// フォーマット(置換)の場合
		if (dataType.equals(KEY_FORMAT)) {
			// 値準備
			String[] rep = new String[itemNames.length];
			// 項目毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				// 人事汎用通常情報取得
				HumanArrayDtoInterface itemDto = arrayDao.findForKey(personalId, itemNames[i],
						MospUtility.getInt(rowId));
				if (itemDto != null) {
					// 共通情報設定
					setCommounInfo(labelKeys[i]);
					// 個別項目にデータ型が存在する場合
					if (labelDateType != null) {
						// 日付変換または年齢を出力した値がある場合
						String formatDate = getArrayFormatDateAge(personalId, itemNames[i], labelItemProperty, rowId,
								targetDate);
						if (formatDate.equals("") == false) {
							// 値作成
							rep[i] = formatDate;
							continue;
						}
						// 電話変換を出力した値がある場合
						String formatPhone = getArrayFormatPhone(personalId, itemNames[i], rowId, labelFormat);
						if (formatPhone.equals("") == false) {
							// 値作成
							rep[i] = formatPhone;
							continue;
						}
					}
					// プルダウン値取得
					String pulldownValue = getPulldownValue(labelItemProperty, targetDate, itemDto.getHumanItemValue(),
							itemNames[i], true);
					// プルダウン値がある場合
					if (pulldownValue.isEmpty() == false) {
						// 値作成
						rep[i] = pulldownValue;
						continue;
					}
					// 値作成 
					rep[i] = itemDto.getHumanItemValue();
					continue;
				}
				rep[i] = "";
			}
			// フォーマット確認
			if (format != null) {
				// 指定された置換文字列作成
				return getReplaceFormat(format, rep);
			}
			return format;
		}
		return "";
	}
	
	/**
	 * フォーマットを確認する。
	 * 値が全て空ではない場合、指定された置換文字列に置き換る。
	 * @param format フォーマット
	 * @param rep 値配列
	 * @return 置換文字列
	 */
	protected String getReplaceFormat(String format, String[] rep) {
		// 全て空の場合
		if (isFormatValue(rep) == false) {
			return "";
		}
		// 値毎に処理
		for (int i = 0; i < rep.length; i++) {
			if (rep[i].isEmpty()) {
				rep[i] = "";
			}
			format = format.replaceAll("%" + String.valueOf(i + 1) + "%", rep[i]);
		}
		return format;
	}
	
	/**
	 * フォーマットの場合、配列に値が入っているか確認する。
	 * @param rep 値の配列
	 * @return 確認結果(true：値が入っている、false：全て空)
	 */
	protected boolean isFormatValue(String[] rep) {
		// 値毎に処理
		for (String element : rep) {
			if (element.isEmpty() == false) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 人事汎用通常情報：日付または年齢のフォーマットに合わせて表示文字を作成する。
	 * @param personalId 対象個人ID
	 * @param itemName 対象項目名
	 * @param itemProperty 対象人事汎用項目設定情報
	 * @param targetDate 対象表示日
	 * @return フォーマット変換された表示文字
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public String getNormalFormatDateAge(String personalId, String itemName, ItemProperty itemProperty, Date targetDate)
			throws MospException {
		// 日付取得
		Date date = humanNormalDate(itemName, personalId);
		// フォーマット設定（日付又は年齢）
		return getFormatDateAge(date, itemProperty, targetDate);
	}
	
	/**
	 * 人事汎用履歴情報：日付または年齢のフォーマットに合わせて表示文字を作成する。
	 * @param personalId 対象個人ID
	 * @param itemName 対象項目名
	 * @param itemProperty 対象人事汎用項目設定情報
	 * @param activeDate 対象有効日
	 * @param targetDate 対象表示日
	 * @return フォーマット変換された表示文字
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public String getHistoryFormatDateAge(String personalId, String itemName, ItemProperty itemProperty,
			Date activeDate, Date targetDate) throws MospException {
		// 日付取得
		Date date = humanHistoryDate(itemName, personalId, activeDate);
		// フォーマット設定（日付又は年齢）
		return getFormatDateAge(date, itemProperty, targetDate);
	}
	
	/**
	 * 人事汎用一覧情報：日付または年齢のフォーマットに合わせて表示文字を作成する。
	 * @param personalId 対象個人ID
	 * @param itemName 対象項目名
	 * @param itemProperty 対象人事汎用項目設定情報
	 * @param rowId 行ID
	 * @param targetDate 対象表示日
	 * @return フォーマット変換された表示文字
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public String getArrayFormatDateAge(String personalId, String itemName, ItemProperty itemProperty, String rowId,
			Date targetDate) throws MospException {
		// 日付取得
		Date date = humanArrayDate(itemName, personalId, rowId);
		// フォーマット設定（日付又は年齢）
		return getFormatDateAge(date, itemProperty, targetDate);
	}
	
	/**
	 * 人事汎用情報（全体）：日付または年齢のフォーマットに合わせて表示文字を作成する。
	 * @param formatDate フォーマット対象日
	 * @param itemProperty 対象人事汎用項目設定情報
	 * @param targetDate 対象表示日
	 * @return フォーマット変換された表示文字
	 */
	public String getFormatDateAge(Date formatDate, ItemProperty itemProperty, Date targetDate) {
		if (MospUtility.isEmpty(formatDate)) {
			return MospConst.STR_EMPTY;
		}
		// 人事汎用項目データ型取得
		String dateType = itemProperty.getDataType();
		String format = itemProperty.getFormat();
		// 人事汎用項目データ型が日付の場合
		if (dateType.equals(KEY_FORMAT_DATE)) {
			return DateUtility.getStringDate(formatDate, format);
		}
		// 人事汎用項目データ型が年齢の場合
		if (dateType.equals(KEY_FORMAT_AGE)) {
			StringBuilder sb = new StringBuilder(DateUtility.getStringDate(formatDate, format));
			return sb.append(HumanUtility.getHumanOlder(formatDate, targetDate, mospParams)).toString();
		}
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * 人事汎用履歴情報：電話のフォーマットに合わせて表示文字を作成する。
	 * @param personalId 対象個人ID
	 * @param itemName 対象項目名
	 * @param format 対象人事汎用項目データ型フォーマット
	 * @return フォーマット変換された表示文字
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public String getNormalFormatPhone(String personalId, String itemName, String format) throws MospException {
		// 電話番号1取得
		HumanNormalDtoInterface normalAreaDto = normalDao.findForInfo(itemName + KEY_FORMAT_PHONE_1, personalId);
		// 電話番号2取得
		HumanNormalDtoInterface normalLocalDto = normalDao.findForInfo(itemName + KEY_FORMAT_PHONE_2, personalId);
		// 電話番号3取得
		HumanNormalDtoInterface normalSubscriberDto = normalDao.findForInfo(itemName + KEY_FORMAT_PHONE_3, personalId);
		
		// 人事汎用項目値取得（電話番号1）
		String area = normalAreaDto == null ? "" : normalAreaDto.getHumanItemValue();
		// 人事汎用項目値取得（電話番号2）
		String local = normalLocalDto == null ? "" : normalLocalDto.getHumanItemValue();
		// 人事汎用項目値取得（電話番号3）
		String subscriber = normalSubscriberDto == null ? "" : normalSubscriberDto.getHumanItemValue();
		
		// 文字列整形
		return getFormatPhone(area, local, subscriber, format);
	}
	
	/**
	 * 人事汎用履歴情報：電話のフォーマットに合わせて表示文字を作成する。
	 * @param personalId 対象個人ID
	 * @param itemName 対象項目名
	 * @param activeDate 対象有効日
	 * @param format 対象人事汎用項目データ型フォーマット
	 * @return フォーマット変換された表示文字
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public String getHistoryFormatPhone(String personalId, String itemName, Date activeDate, String format)
			throws MospException {
		
		// 電話番号1情報取得
		HumanHistoryDtoInterface historyAreaDto = historyDao.findForInfo(personalId, itemName + KEY_FORMAT_PHONE_1,
				activeDate);
		// 電話番号2情報取得
		HumanHistoryDtoInterface historyLocalDto = historyDao.findForInfo(personalId, itemName + KEY_FORMAT_PHONE_2,
				activeDate);
		// 電話番号3情報取得
		HumanHistoryDtoInterface historySubscriberDto = historyDao.findForInfo(personalId,
				itemName + KEY_FORMAT_PHONE_3, activeDate);
		
		// 人事汎用項目値取得（電話番号1）
		String area = historyAreaDto == null ? "" : historyAreaDto.getHumanItemValue();
		// 人事汎用項目値取得（電話番号2）
		String local = historyLocalDto == null ? "" : historyLocalDto.getHumanItemValue();
		// 人事汎用項目値取得（電話番号3）
		String subscriber = historySubscriberDto == null ? "" : historySubscriberDto.getHumanItemValue();
		
		// 文字列整形
		return getFormatPhone(area, local, subscriber, format);
		
	}
	
	/**
	 * 人事汎用一覧情報：電話のフォーマットに合わせて表示文字を作成する。
	 * @param personalId 対象個人ID
	 * @param itemName 対象項目名
	 * @param rowId 行ID
	 * @param format 対象人事汎用項目データ型フォーマット
	 * @return フォーマット変換された表示文字
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public String getArrayFormatPhone(String personalId, String itemName, String rowId, String format)
			throws MospException {
		// 電話番号1取得
		HumanArrayDtoInterface arrayAreaDto = arrayDao.findForKey(personalId, itemName + KEY_FORMAT_PHONE_1,
				MospUtility.getInt(rowId));
		// 電話番号2取得
		HumanArrayDtoInterface arrayLocalDto = arrayDao.findForKey(personalId, itemName + KEY_FORMAT_PHONE_2,
				MospUtility.getInt(rowId));
		// 電話番号3取得
		HumanArrayDtoInterface arraySubscriberDto = arrayDao.findForKey(personalId, itemName + KEY_FORMAT_PHONE_3,
				MospUtility.getInt(rowId));
		
		// 人事汎用項目値取得（電話番号1）
		String area = arrayAreaDto == null ? "" : arrayAreaDto.getHumanItemValue();
		// 人事汎用項目値取得（電話番号2）
		String local = arrayLocalDto == null ? "" : arrayLocalDto.getHumanItemValue();
		// 人事汎用項目値取得（電話番号3）
		String subscriber = arraySubscriberDto == null ? "" : arraySubscriberDto.getHumanItemValue();
		
		// 文字列整形
		return getFormatPhone(area, local, subscriber, format);
	}
	
	/**
	 * 人事汎用情報（全体）：電話のフォーマットに合わせて表示文字を作成する。
	 * @param parmArea 電話番号１
	 * @param parmLocal 電話番号２
	 * @param parmSubscriber 電話番号3
	 * @param format 対象人事汎用項目データ型フォーマット
	 * @return フォーマット変換された表示文字
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public String getFormatPhone(String parmArea, String parmLocal, String parmSubscriber, String format)
			throws MospException {
		// 電話番号1整形
		String area = getFormatHyphenValue(parmArea);
		// 電話番号2整形
		String local = getFormatHyphenValue(parmLocal);
		// 電話番号3取得
		String subscriber = parmSubscriber;
		// フォーマット確認
		if (format.equals(PlatformHumanConst.FORMAT_HUMAN_PHONE)) {
			// 人事汎用通常情報マップに詰める
			return area + local + subscriber;
		}
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * 値が存在する場合"-"をつけた値を取得する。
	 * 電話番号を表示するために使用する。
	 * @param value 値
	 * @return 値 + -
	 */
	public String getFormatHyphenValue(String value) {
		// 値がありかつ空白でない場合
		if (MospUtility.isEmpty(value) == false) {
			// 値 + -
			return new StringBuilder(value).append(PfNameUtility.hyphen(mospParams)).toString();
		}
		// 値が無い場合空白
		return MospConst.STR_EMPTY;
	}
	
	@Override
	public String[][] getPulldownForHumanExportImport(String division, String viewKey) {
		// 人事汎用項目情報リスト取得
		List<TableItemProperty> tableItemList = getTableItemList(division, viewKey);
		// 情報リスト確認
		if (tableItemList == null || tableItemList.isEmpty()) {
			return new String[0][];
		}
		// 配列準備
		String[] tableItemKeys = new String[tableItemList.size()];
		//人事汎用項目毎に処理
		for (int i = 0; i < tableItemList.size(); i++) {
			String key = tableItemList.get(i).getKey();
			tableItemKeys[i] = key;
		}
		// 項目名長取得
		int nameSize = tableItemKeys.length;
		// 項目名長の配列準備
		String[][] array = new String[nameSize][2];
		// 配列設定
		for (int i = 0; i < nameSize; i++) {
			// 値を準備
			StringBuilder value = new StringBuilder(division);
			value.append(MospConst.APP_PROPERTY_SEPARATOR).append(tableItemKeys[i]);
			// 名称を準備
			StringBuilder name = new StringBuilder(NameUtility.getName(mospParams, tableItemKeys[i]));
			name.append(PfNameUtility.parentheses(mospParams, NameUtility.getName(mospParams, division)));
			// 配列に設定
			array[i][0] = value.toString();
			array[i][1] = name.toString();
		}
		return array;
	}
	
}
