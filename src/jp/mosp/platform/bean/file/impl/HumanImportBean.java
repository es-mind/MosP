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
package jp.mosp.platform.bean.file.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.file.HumanImportBeanInterface;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.EntranceRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralCheckBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementRegistBeanInterface;
import jp.mosp.platform.bean.human.impl.HumanRegistBean;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.file.ImportFieldDaoInterface;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanArrayDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.human.action.HumanArrayCardAction;
import jp.mosp.platform.human.action.HumanHistoryCardAction;
import jp.mosp.platform.human.action.HumanNormalCardAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.utils.HuMessageUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事マスタインポートクラス。
 */
public class HumanImportBean extends HumanRegistBean implements HumanImportBeanInterface {
	
	/**
	 * 人事参照クラス。<br>
	 */
	protected HumanReferenceBeanInterface					humanRefer;
	
	/**
	 * 人事入社情報参照クラス。<br>
	 */
	protected EntranceReferenceBeanInterface				entranceRefer;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface				retirementRefer;
	
	/**
	 * 人事入社情報登録クラス。<br>
	 */
	protected EntranceRegistBeanInterface					entranceRegist;
	
	/**
	 * 人事退職情報登録クラス。<br>
	 */
	protected RetirementRegistBeanInterface					retirementRegist;
	
	/**
	 * 人事汎用通常情報登録クラス。<br>
	 */
	protected HumanNormalRegistBeanInterface				humanNoraml;
	
	/**
	 * 人事汎用履歴情報登録クラス。<br>
	 */
	protected HumanHistoryRegistBeanInterface				humanHistory;
	
	/**
	 * 人事汎用一覧情報登録クラス。<br>
	 */
	protected HumanArrayRegistBeanInterface					humanArray;
	
	/**
	 * 人事汎用管理機能クラス。<br>
	 */
	protected HumanGeneralBeanInterface						humanGeneral;
	
	/**
	 * 人事汎用管理チェッククラス<br>
	 */
	protected HumanGeneralCheckBeanInterface				humanGeneralCheckBean;
	
	/**
	 * 人事情報リスト。<br>
	 */
	protected List<HumanDtoInterface>						humanList;
	
	/**
	 * 人事入社情報リスト。<br>
	 */
	protected List<EntranceDtoInterface>					entranceList;
	
	/**
	 * 人事退職情報リスト。<br>
	 */
	protected List<RetirementDtoInterface>					retirementList;
	
	/**
	 * 人事通常情報リスト。<br>
	 */
	protected Map<String, List<HumanNormalDtoInterface>>	normalMap;
	
	/**
	 * 人事履歴情報リスト。<br>
	 */
	protected Map<String, List<HumanHistoryDtoInterface>>	historyMap;
	
	/**
	 * 人事一覧情報リスト。<br>
	 */
	protected Map<String, List<HumanArrayDtoInterface>>		arrayMap;
	
	/**
	 * 人事汎用管理区分セット。<br>
	 */
	protected HashSet<String>								divisionKeySet;
	
	/**
	 * 人事マスタ情報。<br>
	 * データ取得時及び登録時に、対象人事マスタ情報を保持しておく。<br>
	 */
	protected HumanDtoInterface								human;
	
	/**
	 * インポートフィールド管理DAOインターフェース<br>
	 */
	protected ImportFieldDaoInterface						importFieldDao;
	
	
	/**
	 * {@link HumanRegistBean#HumanRegistBean()}を実行する。<br>
	 */
	public HumanImportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		importFieldDao = createDaoInstance(ImportFieldDaoInterface.class);
		// Beanを準備
		humanRefer = createBeanInstance(HumanReferenceBeanInterface.class);
		entranceRefer = createBeanInstance(EntranceReferenceBeanInterface.class);
		retirementRefer = createBeanInstance(RetirementReferenceBeanInterface.class);
		entranceRegist = createBeanInstance(EntranceRegistBeanInterface.class);
		retirementRegist = createBeanInstance(RetirementRegistBeanInterface.class);
		humanNoraml = createBeanInstance(HumanNormalRegistBeanInterface.class);
		humanHistory = createBeanInstance(HumanHistoryRegistBeanInterface.class);
		humanArray = createBeanInstance(HumanArrayRegistBeanInterface.class);
		humanGeneral = createBeanInstance(HumanGeneralBeanInterface.class);
		humanGeneralCheckBean = createBeanInstance(HumanGeneralCheckBeanInterface.class);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		// 登録情報リストから人事マスタ情報に変換
		getTargetLists(importDto, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 人事マスタ情報毎に登録
		for (int i = 0; i < humanList.size(); i++) {
			HumanDtoInterface humanDto = humanList.get(i);
			// 人事マスタ情報登録
			registHumanDto(humanDto);
			// 人事入社情報登録
			registEntranceDto(entranceList.get(i));
			// 人事入社情報登録
			registRetirementDto(retirementList.get(i));
			// 人事汎用情報登録
			registHumanNormalDto(humanDto.getEmployeeCode(), humanDto.getActivateDate());
			// 人事履歴情報登録
			registHumanHistoryDto(humanDto.getEmployeeCode());
			// 人事一覧情報登録
			registHumanArrayDto(humanDto.getEmployeeCode());
		}
		// 登録件数取得
		return humanList.size();
	}
	
	/**
	 * 登録対象リスト群を取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストから人事マスタ情報リストに変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * @param importDto インポートマスタ情報
	 * @param dataList  登録情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getTargetLists(ImportDtoInterface importDto, List<String[]> dataList) throws MospException {
		// 人事マスタリスト準備
		humanList = new ArrayList<HumanDtoInterface>();
		// 人事入社情報リスト準備
		entranceList = new ArrayList<EntranceDtoInterface>();
		// 人事退職情報リスト準備
		retirementList = new ArrayList<RetirementDtoInterface>();
		// 人事通常情報マップ準備
		normalMap = new HashMap<String, List<HumanNormalDtoInterface>>();
		// 人事履歴情報マップ準備
		historyMap = new HashMap<String, List<HumanHistoryDtoInterface>>();
		// 人事一覧情報マップ準備
		arrayMap = new HashMap<String, List<HumanArrayDtoInterface>>();
		// インポートフィールド情報取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		
		// 人事汎用区分セット準備
		divisionKeySet = new HashSet<String>();
		
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// 人事マスタ情報取得及び確認
			HumanDtoInterface humanDto = getHumanDto(fieldList, data, i);
			if (humanDto != null) {
				// 人事マスタリストに追加
				humanList.add(humanDto);
			} else {
				// 人事情報が取得できなかった場合
				continue;
			}
			// 人事入社情報を取得しリストに追加
			entranceList.add(getEntranceDto(fieldList, data, i));
			// 人事退職情報取得及び確認
			retirementList.add(getRetirementDto(fieldList, data, i));
			// 人事汎用区分情報取得
			getHumanGeneralList(fieldList, data, humanDto);
		}
		
		// 人事汎用管理機能入力チェック
		for (String divisionKey : divisionKeySet) {
			//管理表示設定情報を取得
			ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(divisionKey);
			// 画面タイプ取得
			String type = viewConfig.getType();
			
			// 通常
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
				for (Entry<String, List<HumanNormalDtoInterface>> entry : normalMap.entrySet()) {
					humanGeneralCheckBean.validate(divisionKey, entry.getValue());
				}
			}
			
			// 履歴
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
				for (Entry<String, List<HumanHistoryDtoInterface>> entry : historyMap.entrySet()) {
					humanGeneralCheckBean.validate(divisionKey, entry.getValue());
				}
			}
			// 一覧
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
				for (Entry<String, List<HumanArrayDtoInterface>> entry : arrayMap.entrySet()) {
					humanGeneralCheckBean.validate(divisionKey, entry.getValue());
				}
			}
			
		}
		
	}
	
	/**
	 * 有効日値を取得する。
	 * 有効日値がない場合、エラーメッセージを出力する。
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @param division 人事汎用管理区分
	 * @return 人事マスタ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getHumanGeneralActivateDate(List<ImportFieldDtoInterface> fieldList, String[] data,
			String division) throws MospException {
		// インポートフィールド管理情報毎に処理
		for (ImportFieldDtoInterface field : fieldList) {
			// フィールド名配列取得
			String[] fieldName = MospUtility.split(field.getFieldName(), MospConst.APP_PROPERTY_SEPARATOR);
			// 人事汎用管理区分でない場合
			if (fieldName.length == 1) {
				continue;
			}
			// 対象人事汎用管理区分でない場合
			if (fieldName[0].equals(division) == false) {
				continue;
			}
			// 項目名が有効日でない場合
			if (fieldName[1].equals(PlatformHumanConst.PRM_IMPORT_HISTORY_ARRAY_ACTIVATE_DATE) == false) {
				continue;
			}
			// 有効日取得
			String date = data[field.getFieldOrder() - 1];
			// 有効日がない場合
			if (date == null || date.isEmpty()) {
				// エラーメッセージを設定
				HuMessageUtility.addErrorNoActivateDate(mospParams, NameUtility.getName(mospParams, division));
				return null;
			}
			// 日付にできない場合
			if (DateUtility.getVariousDate(date) == null) {
				// エラーメッセージを設定
				HuMessageUtility.addErrorNotCorrect(mospParams, NameUtility.getName(mospParams, division),
						PfNameUtility.activateDate(mospParams));
				return null;
			}
			return date;
		}
		
		return null;
	}
	
	/**
	 * 人事マスタ情報を取得する。<br>
	 * インポートフィールド情報リストに従い、登録情報リストから人事マスタ情報に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * インポート不能登録情報の場合は、nullを返す。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param row       行インデックス
	 * @return 人事マスタ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected HumanDtoInterface getHumanDto(List<ImportFieldDtoInterface> fieldList, String[] data, int row)
			throws MospException {
		// 社員コード及び有効日取得
		String employeeCode = getFieldValue(PlatformFileConst.FIELD_EMPLOYEE_CODE, fieldList, data);
		Date activateDate = getDateFieldValue(PlatformFileConst.FIELD_ACTIVATE_DATE, fieldList, data);
		// 社員コード確認
		if (MospUtility.isEmpty(employeeCode)) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.employeeCode(mospParams), row);
			return null;
		}
		// 有効日確認
		if (MospUtility.isEmpty(activateDate)) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorActivateDateRequired(mospParams, row);
			return null;
		}
		// 有効日における最新の人事マスタ情報を取得
		human = dao.findForEmployeeCode(employeeCode, activateDate);
		// 有効日における最新の人事マスタ情報が無く社員コードが使われている場合
		if (human == null && isEmployeeCodeUsed(employeeCode)) {
			// 新規DTO取得
			human = getInitDto();
			// 個人IDをセット
			human.setPersonalId(getOldestHumanDto(employeeCode).getPersonalId());
		}
		// 人事マスタDTO準備(新規登録の場合)
		if (human == null) {
			// 個人IDはnull
			human = getInitDto();
		}
		// 登録情報の値をDTOに設定
		setHumanDtoFields(fieldList, data, human);
		// 入力チェック
		validate(human, Integer.valueOf(row));
		return human;
	}
	
	/**
	 * 人事入社情報を取得する。<br>
	 * インポートフィールド情報リストに従い、登録情報リストから人事入社情報に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * インポート不能登録情報の場合は、nullを返す。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param row       行インデックス
	 * @return 人事入社情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected EntranceDtoInterface getEntranceDto(List<ImportFieldDtoInterface> fieldList, String[] data, int row)
			throws MospException {
		// 登録情報の内容を取得(登録情報に含まれない場合はnull)
		Date entranceDate = getDateFieldValue(PlatformFileConst.FIELD_ENTRANCE_DATE, fieldList, data);
		// 登録情報確認
		if (entranceDate == null) {
			// 登録情報に入社日が含まれない場合
			return null;
		}
		// 人事入社情報準備
		EntranceDtoInterface entrance = null;
		// 個人ID確認
		if (human.getPersonalId() != null) {
			entrance = entranceRefer.getEntranceInfo(human.getPersonalId());
		}
		// 人事入社情報確認
		if (entrance == null) {
			entrance = entranceRegist.getInitDto();
		}
		// 人事入社情報に登録情報の内容を設定
		entrance.setEntranceDate(entranceDate);
		// 入力チェック
		entranceRegist.validate(entrance, Integer.valueOf(row));
		return entrance;
	}
	
	/**
	 * 人事退職情報を取得する。<br>
	 * インポートフィールド情報リストに従い、登録情報リストから人事退職情報に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * インポート不能登録情報の場合は、nullを返す。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param row       行インデックス
	 * @return 人事退職情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected RetirementDtoInterface getRetirementDto(List<ImportFieldDtoInterface> fieldList, String[] data, int row)
			throws MospException {
		// 登録情報の内容を取得(登録情報に含まれない場合はnull)
		Date retirementDate = getDateFieldValue(PlatformFileConst.FIELD_RETIREMENT_DATE, fieldList, data);
		String retirementReason = getFieldValue(PlatformFileConst.FIELD_RETIREMENT_REASON, fieldList, data);
		String retirementDetail = getFieldValue(PlatformFileConst.FIELD_RETIREMENT_DETAIL, fieldList, data);
		// 登録情報確認
		if (retirementDate == null && (retirementReason == null || retirementReason.isEmpty())
				&& (retirementDetail == null || retirementDetail.isEmpty())) {
			// 登録情報に退職関連情報が含まれない場合
			return null;
		}
		// 人事退職情報準備
		RetirementDtoInterface retirement = null;
		// 個人ID確認
		if (human.getPersonalId() != null) {
			retirement = retirementRefer.getRetireInfo(human.getPersonalId());
		}
		// 人事退職情報確認
		if (retirement == null) {
			retirement = retirementRegist.getInitDto();
		}
		// 人事退職情報に登録情報の内容を設定
		if (retirementDate != null) {
			retirement.setRetirementDate(retirementDate);
		}
		if (retirementReason != null) {
			retirement.setRetirementReason(retirementReason);
		} else if (retirement.getRetirementReason() == null) {
			retirement.setRetirementReason("");
		}
		if (retirementDetail != null) {
			retirement.setRetirementDetail(retirementDetail);
		} else if (retirement.getRetirementDetail() == null) {
			retirement.setRetirementDetail("");
		}
		// 入力チェック
		retirementRegist.validate(retirement, Integer.valueOf(row));
		return retirement;
	}
	
	/**
	 * 人事汎用情報を取得する。
	 * 一人の社員インポート情報に対し人事汎用情報区分の場合、
	 * インポート情報を取得する。
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @param humanDto 人事情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getHumanGeneralList(List<ImportFieldDtoInterface> fieldList, String[] data,
			HumanDtoInterface humanDto) throws MospException {
		// 社員コード取得
		String employeeCode = humanDto.getEmployeeCode();
		// 登録済画面区分・項目名リスト準備
		Map<String, List<String>> normalRegistedMap = new HashMap<String, List<String>>();
		Map<String, List<String>> historyRegistedMap = new HashMap<String, List<String>>();
		Map<String, List<String>> arrayRegistedMap = new HashMap<String, List<String>>();
		// インポート情報リスト毎に処理
		for (ImportFieldDtoInterface field : fieldList) {
			// 有効日初期化
			String activeDate = "";
			// フィールド名配列取得
			String[] fieldName = MospUtility.split(field.getFieldName(), MospConst.APP_PROPERTY_SEPARATOR);
			// 人事汎用情報でない場合
			if (fieldName.length == 1) {
				continue;
			}
			// 人事汎用区分取得
			String division = fieldName[0];
			// 人事汎用区分セットに追加
			divisionKeySet.add(division);
			// 項目名・項目値取得
			String itemName = fieldName[1];
			String itemValue = data[field.getFieldOrder() - 1];
			//管理表示設定情報を取得
			ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(division);
			// 画面タイプ取得
			String type = viewConfig.getType();
			// 通常の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
				// 人事通常情報取得及び確認
				getNormalDto(itemName, itemValue, division, employeeCode);
				// 登録済情報リスト追加
				List<String> itemNormalList = normalRegistedMap.get(division);
				if (itemNormalList == null || itemNormalList.isEmpty()) {
					itemNormalList = new ArrayList<String>();
					itemNormalList.add(itemName);
					normalRegistedMap.put(division, itemNormalList);
					continue;
				}
				itemNormalList.add(itemName);
				continue;
			}
			// 有効日取得
			activeDate = getHumanGeneralActivateDate(fieldList, data, division);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 項目名が有効日である場合
			if (itemName.equals(PlatformHumanConst.PRM_IMPORT_HISTORY_ARRAY_ACTIVATE_DATE)) {
				continue;
			}
			// 履歴の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
				// 人事履歴情報取得及び確認
				getHistoryDto(itemName, itemValue, division, DateUtility.getVariousDate(activeDate), employeeCode);
				// 登録済情報リスト追加
				List<String> itemHistorylList = historyRegistedMap.get(division);
				if (itemHistorylList == null || itemHistorylList.isEmpty()) {
					itemHistorylList = new ArrayList<String>();
					itemHistorylList.add(itemName + MospConst.APP_PROPERTY_SEPARATOR + activeDate);
					historyRegistedMap.put(division, itemHistorylList);
					continue;
				}
				itemHistorylList.add(itemName + MospConst.APP_PROPERTY_SEPARATOR + activeDate);
				continue;
			}
			// 一覧の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
				// 人事一覧情報取得及び確認
				getArrayDto(itemName, itemValue, division, DateUtility.getVariousDate(activeDate), employeeCode);
				// 登録済情報リスト追加
				// 登録済情報リスト追加
				List<String> itemArrayList = arrayRegistedMap.get(division);
				if (itemArrayList == null || itemArrayList.isEmpty()) {
					itemArrayList = new ArrayList<String>();
					itemArrayList.add(itemName + MospConst.APP_PROPERTY_SEPARATOR + activeDate);
					arrayRegistedMap.put(division, itemArrayList);
					continue;
				}
				itemArrayList.add(itemName + MospConst.APP_PROPERTY_SEPARATOR + activeDate);
				continue;
			}
		}
		
		// 人事画面区分毎に処理
		for (String importDivisionName : divisionKeySet) {
			//管理表示設定情報を取得
			ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties()
				.get(importDivisionName);
			// 画面タイプ取得
			String type = viewConfig.getType();
			
			// 通常の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
				// 登録済人事汎用画面区分項目リスト取得
				List<String> normalList = normalRegistedMap.get(importDivisionName);
				// 空でない場合
				if (normalList != null) {
					// 余った項目を値を空にして追加
					getNormalSpereDtoList(normalList, employeeCode, importDivisionName);
					continue;
				}
				
			}
			// 履歴の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
				// 登録済人事汎用画面区分項目リスト取得
				List<String> historyList = historyRegistedMap.get(importDivisionName);
				// 空でない場合
				if (historyList != null) {
					// 余った項目を値を空にして追加
					getHistorySpereDtoList(historyList, employeeCode, importDivisionName);
					continue;
				}
			}
			// 一覧の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
				// 登録済人事汎用画面区分項目リスト取得
				List<String> arrayList = arrayRegistedMap.get(importDivisionName);
				// 空でない場合
				if (arrayList != null) {
					// 余った項目を値を空にして追加
					getArraySpereDtoList(arrayList, employeeCode, importDivisionName);
					continue;
				}
			}
			
		}
	}
	
	/**
	 * 登録済の項目名ではないか確認する。（人事汎用）
	 * @param registeredList 登録済項目リスト
	 * @param itemName 対象項目名
	 * @return 確認結果（true：存在する、false：存在しない）
	 */
	protected boolean isMacthItemName(List<String> registeredList, String itemName) {
		// 有効日の場合
		if (itemName.equals(PlatformHumanConst.PRM_IMPORT_HISTORY_ARRAY_ACTIVATE_DATE)) {
			return true;
		}
		// 登録済項目リスト毎に処理
		for (String registeredItemName : registeredList) {
			// 項目配列取得
			String[] arry = MospUtility.split(registeredItemName, MospConst.APP_PROPERTY_SEPARATOR);
			// 既に登録している場合
			if (itemName.equals(arry[0])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 余りの項目を通常情報リストに追加する。
	 * インポートで登録されている通常画面区分の登録されていない項目名を取得し、
	 * 通常情報リストに値を空白にして追加する。
	 * @param registeredList 通常登録済リスト
	 * @param employeeCode 社員コード
	 * @param division 人事画面区分
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getNormalSpereDtoList(List<String> registeredList, String employeeCode, String division)
			throws MospException {
		// 通常余り情報がない場合
		if (registeredList.isEmpty()) {
			return;
		}
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division,
				HumanNormalCardAction.KEY_VIEW_NORMAL_CARD);
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
				// 登録済の項目名である場合
				if (isMacthItemName(registeredList, itemName)) {
					continue;
				}
				// 新規で登録
				getNormalDto(itemName, "", division, employeeCode);
			}
		}
	}
	
	/**
	 * 余りの項目を履歴情報リストに追加する。
	 * インポートで登録されている通常画面区分の登録されていない項目名を取得し、
	 * 履歴情報リストに値を空白にして追加する。
	 * @param registeredList 履歴余りリスト
	 * @param employeeCode 社員コード
	 * @param division 人事画面区分
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getHistorySpereDtoList(List<String> registeredList, String employeeCode, String division)
			throws MospException {
		// 履歴余り情報がない場合
		if (registeredList.isEmpty()) {
			return;
		}
		// 有効日取得
		String[] array = MospUtility.split(registeredList.get(0), MospConst.APP_PROPERTY_SEPARATOR);
		Date activeDate = DateUtility.getDate(array[1]);
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division,
				HumanHistoryCardAction.KEY_VIEW_HUMAN_HISTORY_CARD);
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
				// 登録済の項目名である場合
				if (isMacthItemName(registeredList, itemName)) {
					continue;
				}
				// 新規で登録
				getHistoryDto(itemName, "", division, activeDate, employeeCode);
			}
		}
	}
	
	/**
	 * 余りの項目を一覧情報リストに追加する。
	 * インポートで登録されている通常画面区分の登録されていない項目名を取得し、
	 * 一覧情報リストに値を空白にして追加する。
	 * @param registeredList 一覧余りリスト
	 * @param employeeCode 社員コード
	 * @param division 人事画面区分
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getArraySpereDtoList(List<String> registeredList, String employeeCode, String division)
			throws MospException {
		// 履歴余り情報がない場合
		if (registeredList.isEmpty()) {
			return;
		}
		// 有効日取得
		String[] array = MospUtility.split(registeredList.get(0), MospConst.APP_PROPERTY_SEPARATOR);
		Date activeDate = DateUtility.getDate(array[1]);
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division,
				HumanArrayCardAction.KEY_VIEW_ARRAY_CARD);
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
				// 登録済の項目名である場合
				if (isMacthItemName(registeredList, itemName)) {
					continue;
				}
				// 新規で登録
				getArrayDto(itemName, "", division, activeDate, employeeCode);
			}
		}
	}
	
	/**
	 * 人事通常情報を取得する。
	 * 人事汎用通常項目を人事汎用通常情報マップにつめる。<br>
	 * 対象社員コードで区別する。
	 * @param itemName 項目名
	 * @param itemValue 項目地
	 * @param division 人事汎用画面区分
	 * @param employeeCode 社員コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getNormalDto(String itemName, String itemValue, String division, String employeeCode)
			throws MospException {
		// 人事汎用通常情報取得
		HumanNormalDtoInterface dto = humanNoraml.getInitDto();
		// 個人IDは空に設定
		dto.setPersonalId("");
		dto.setHumanItemType(itemName);
		dto.setHumanItemValue(itemValue);
		// 通常リスト追加
		List<HumanNormalDtoInterface> list = normalMap.get(employeeCode);
		// 新情報の場合
		if (list == null || list.isEmpty()) {
			// リスト準備
			list = new ArrayList<HumanNormalDtoInterface>();
			list.add(dto);
			normalMap.put(employeeCode, list);
			return;
		}
		// 既にある場合
		list.add(dto);
	}
	
	/**
	 * 人事履歴情報を取得する。
	 * 人事汎用履歴項目を人事汎用履歴情報マップにつめる。<br>
	 * 対象社員コードで区別する。
	 * @param itemName 項目名
	 * @param itemValue 項目値
	 * @param division 人事汎用画面区分
	 * @param activeDate 有効日
	 * @param employeeCode 社員コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getHistoryDto(String itemName, String itemValue, String division, Date activeDate,
			String employeeCode) throws MospException {
		// 人事汎用履歴情報取得
		HumanHistoryDtoInterface dto = humanHistory.getInitDto();
		// 個人IDは空に設定
		dto.setPersonalId("");
		dto.setActivateDate(activeDate);
		dto.setHumanItemType(itemName);
		dto.setHumanItemValue(itemValue);
		// 履歴リスト追加
		List<HumanHistoryDtoInterface> list = historyMap.get(employeeCode);
		// 新規情報の場合
		if (list == null || list.isEmpty()) {
			// リスト準備
			list = new ArrayList<HumanHistoryDtoInterface>();
			list.add(dto);
			historyMap.put(employeeCode, list);
			return;
		}
		// 既にある場合
		list.add(dto);
	}
	
	/**
	 * 人事一覧情報を取得する。
	 * 人事汎用一覧項目を人事汎用一覧情報マップにつめる。<br>
	 * 対象社員コードで区別する。
	 * @param itemName 項目名
	 * @param itemValue 項目値
	 * @param division 人事汎用画面区分
	 * @param activeDate 有効日
	 * @param employeeCode 社員コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getArrayDto(String itemName, String itemValue, String division, Date activeDate, String employeeCode)
			throws MospException {
		// DTO準備
		HumanArrayDtoInterface dto = humanArray.getInitDto();
		dto.setActivateDate(activeDate);
		// 個人IDは空に設定
		dto.setPersonalId("");
		dto.setHumanItemType(itemName);
		dto.setHumanItemValue(itemValue);
		// 履歴リスト追加
		List<HumanArrayDtoInterface> list = arrayMap.get(employeeCode);
		// 新規情報の場合
		if (list == null || list.isEmpty()) {
			// リスト準備
			list = new ArrayList<HumanArrayDtoInterface>();
			// 行番号取得
			int newRowId = humanArray.getRowId();
			// 行番号設定
			dto.setHumanRowId(newRowId);
			list.add(dto);
			arrayMap.put(employeeCode, list);
			return;
		}
		// 既にある場合
		// 行番号を取得し設定
		dto.setHumanRowId(list.get(0).getHumanRowId());
		list.add(dto);
	}
	
	/**
	 * 社員コードが使用されているかどうかを確認する。<br>
	 * @param employeeCode 社員コード
	 * @return 確認結果(true：使用されている、false：使用されていない)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected boolean isEmployeeCodeUsed(String employeeCode) throws MospException {
		// 社員コードで人事マスタ情報リストを取得
		List<HumanDtoInterface> list = dao.findForEmployeeCode(employeeCode);
		// 人事マスタ情報リスト確認
		if (list.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 最も古い人事マスタ情報を取得する。
	 * @param employeeCode 社員コード
	 * @return 最も古い人事マスタ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected HumanDtoInterface getOldestHumanDto(String employeeCode) throws MospException {
		HumanDtoInterface dto = null;
		// 社員コードで人事マスタ情報リストを取得
		List<HumanDtoInterface> list = dao.findForEmployeeCode(employeeCode);
		for (HumanDtoInterface humanDto : list) {
			if (dto == null || humanDto.getActivateDate().before(dto.getActivateDate())) {
				dto = humanDto;
			}
		}
		return dto;
	}
	
	/**
	 * インポートフィールド情報リストに従い、登録情報の内容を人事マスタ情報のフィールドに設定する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param human     設定対象人事マスタ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setHumanDtoFields(List<ImportFieldDtoInterface> fieldList, String[] data, HumanDtoInterface human)
			throws MospException {
		// 登録情報の内容を取得(登録情報に含まれない場合はnull)
		String employeeCode = getFieldValue(PlatformFileConst.FIELD_EMPLOYEE_CODE, fieldList, data);
		Date activateDate = getDateFieldValue(PlatformFileConst.FIELD_ACTIVATE_DATE, fieldList, data);
		String lastName = getFieldValue(PlatformFileConst.FIELD_LAST_NAME, fieldList, data);
		String firstName = getFieldValue(PlatformFileConst.FIELD_FIRST_NAME, fieldList, data);
		String lastKana = getFieldValue(PlatformFileConst.FIELD_LAST_KANA, fieldList, data);
		String firstKana = getFieldValue(PlatformFileConst.FIELD_FIRST_KANA, fieldList, data);
		String workPlaceCode = getFieldValue(PlatformFileConst.FIELD_WORK_PLACE_CODE, fieldList, data);
		String employmentContractCode = getFieldValue(PlatformFileConst.FIELD_EMPLOYMENT_CONTRACT_CODE, fieldList,
				data);
		String sectionCode = getFieldValue(PlatformFileConst.FIELD_SECTION_CODE, fieldList, data);
		String positionCode = getFieldValue(PlatformFileConst.FIELD_POSITION_CODE, fieldList, data);
		// 人事マスタ情報に登録情報の内容を設定(nullの場合は設定しない)
		if (employeeCode != null) {
			human.setEmployeeCode(employeeCode);
		}
		if (activateDate != null) {
			human.setActivateDate(activateDate);
		}
		if (lastName != null) {
			human.setLastName(lastName);
		} else if (human.getLastName() == null) {
			human.setLastName("");
		}
		if (firstName != null) {
			human.setFirstName(firstName);
		} else if (human.getFirstName() == null) {
			human.setFirstName("");
		}
		if (lastKana != null) {
			human.setLastKana(lastKana);
		} else if (human.getLastKana() == null) {
			human.setLastKana("");
		}
		if (firstKana != null) {
			human.setFirstKana(firstKana);
		} else if (human.getFirstKana() == null) {
			human.setFirstKana("");
		}
		if (workPlaceCode != null) {
			human.setWorkPlaceCode(workPlaceCode);
		} else if (human.getWorkPlaceCode() == null) {
			human.setWorkPlaceCode("");
		}
		if (employmentContractCode != null) {
			human.setEmploymentContractCode(employmentContractCode);
		} else if (human.getEmploymentContractCode() == null) {
			human.setEmploymentContractCode("");
		}
		if (sectionCode != null) {
			human.setSectionCode(sectionCode);
		} else if (human.getSectionCode() == null) {
			human.setSectionCode("");
		}
		if (positionCode != null) {
			human.setPositionCode(positionCode);
		} else if (human.getPositionCode() == null) {
			human.setPositionCode("");
		}
		if (human.getMail() == null) {
			human.setMail("");
		}
	}
	
	/**
	 * 人事マスタ情報を登録する。<br>
	 * @param humanDto 人事マスタ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registHumanDto(HumanDtoInterface humanDto) throws MospException {
		// 人事マスタ情報設定
		human = humanDto;
		// 個人ID確認
		if (human.getPersonalId() == null || human.getPersonalId().isEmpty()) {
			// 新規登録
			insert(human);
			return;
		}
		// 個人ID及び有効日で人事マスタ情報を取得
		if (dao.findForKey(human.getPersonalId(), human.getActivateDate()) == null) {
			// 履歴追加
			add(human);
			return;
		}
		// 履歴更新
		update(human);
	}
	
	/**
	 * 人事入社情報を登録する。<br>
	 * @param entrance 人事入社情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registEntranceDto(EntranceDtoInterface entrance) throws MospException {
		// 人事入社情報確認
		if (entrance == null) {
			return;
		}
		// 個人ID確認
		if (entrance.getPersonalId() == null || entrance.getPersonalId().isEmpty()) {
			// 個人ID設定
			entrance.setPersonalId(human.getPersonalId());
		}
		// 人事入社情報登録
		entranceRegist.regist(entrance);
	}
	
	/**
	 * 人事退職情報を登録する。<br>
	 * @param retirement 人事退職情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registRetirementDto(RetirementDtoInterface retirement) throws MospException {
		// 人事退職情報確認
		if (retirement == null) {
			return;
		}
		// 個人ID確認
		if (retirement.getPersonalId() == null || retirement.getPersonalId().isEmpty()) {
			// 個人ID設定
			retirement.setPersonalId(human.getPersonalId());
		}
		// 人事入社情報登録
		retirementRegist.regist(retirement);
	}
	
	/**
	 * 人事通常情報を登録する。<br>
	 * @param employeeCode 対象社員コード
	 * @param humanActiveDate 人事有効日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registHumanNormalDto(String employeeCode, Date humanActiveDate) throws MospException {
		// 人事通常リスト取得
		List<HumanNormalDtoInterface> list = normalMap.get(employeeCode);
		if (list == null || list.isEmpty()) {
			return;
		}
		
		// 人事通常情報リスト毎に処理
		for (HumanNormalDtoInterface dto : list) {
			// 人事退職情報確認
			if (dto == null) {
				continue;
			}
			// 個人ID取得
			String personalId = humanRefer.getPersonalId(employeeCode, humanActiveDate);
			// 同じ個人IDの場合
			if (personalId.isEmpty()) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeHistoryNotExist(mospParams, humanActiveDate, employeeCode);
				return;
			}
			// 個人ID設定
			dto.setPersonalId(personalId);
			// 人事通常情報登録
			humanNoraml.regist(dto);
		}
	}
	
	/**
	 * 人事履歴情報を登録する。<br>
	 * @param employeeCode 対象社員コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registHumanHistoryDto(String employeeCode) throws MospException {
		// 人事履歴リスト準備
		List<HumanHistoryDtoInterface> list = historyMap.get(employeeCode);
		if (list == null || list.isEmpty()) {
			return;
		}
		// 人事通常情報リスト毎に処理
		for (HumanHistoryDtoInterface dto : list) {
			// 人事退職情報確認
			if (dto == null) {
				continue;
			}
			// 個人ID取得
			String personalId = humanRefer.getPersonalId(employeeCode, dto.getActivateDate());
			// 個人IDがない場合
			if (personalId.isEmpty()) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeHistoryNotExist(mospParams, dto.getActivateDate(), employeeCode);
				return;
			}
			// 個人ID設定
			dto.setPersonalId(personalId);
			// 人事通常情報登録
			humanHistory.regist(dto);
		}
	}
	
	/**
	 * 人事一覧情報を登録する。<br>
	 * @param employeeCode 対象社員コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registHumanArrayDto(String employeeCode) throws MospException {
		// 人事一覧リスト準備
		List<HumanArrayDtoInterface> list = arrayMap.get(employeeCode);
		if (list == null || list.isEmpty()) {
			return;
		}
		// 人事一覧情報リスト毎に処理
		for (HumanArrayDtoInterface dto : list) {
			// 人事退職情報確認
			if (dto == null) {
				continue;
			}
			// 個人ID取得
			String personalId = humanRefer.getPersonalId(employeeCode, dto.getActivateDate());
			// 個人IDがない場合
			if (personalId.isEmpty()) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeHistoryNotExist(mospParams, dto.getActivateDate(), employeeCode);
				return;
			}
			// 個人ID設定
			dto.setPersonalId(human.getPersonalId());
			// 人事通常情報登録
			humanArray.insert(dto);
		}
	}
	
	@Override
	public boolean isExistLikeFieldName(String importCode, String[] aryFieldName) throws MospException {
		List<ImportFieldDtoInterface> list = importFieldDao.findLikeStartNameList(importCode, aryFieldName);
		list = list == null ? new ArrayList<ImportFieldDtoInterface>() : list;
		
		return list.isEmpty() ? false : true;
	}
}
