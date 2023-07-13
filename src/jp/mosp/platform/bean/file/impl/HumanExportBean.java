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
package jp.mosp.platform.bean.file.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.platform.bean.file.HumanExportBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.file.action.ExportCardAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事マスタエクスポートクラス。
 */
public class HumanExportBean extends BaseExportBean implements HumanExportBeanInterface {
	
	/**
	 * 人事入社情報DAO。<br>
	 */
	protected EntranceDaoInterface					entranceDao;
	
	/**
	 * 人事退職情報DAO。<br>
	 */
	protected RetirementDaoInterface				retirementDao;
	
	/**
	 * 人事汎用通常情報参照クラス。<br>
	 */
	protected HumanNormalReferenceBeanInterface		humanNoraml;
	
	/**
	 * 人事汎用履歴情報参照クラス。<br>
	 */
	protected HumanHistoryReferenceBeanInterface	humanHistory;
	
	/**
	 * 人事汎用一覧情報参照クラス。<br>
	 */
	protected HumanArrayReferenceBeanInterface		humanArray;
	
	/**
	 * 人事マスタ情報リスト。<br>
	 */
	protected List<HumanDtoInterface>				humanList;
	
	
	/**
	 * {@link BaseExportBean#BaseExportBean()}を実行する。<br>
	 */
	public HumanExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// DAO及びBeanの準備
		entranceDao = createDaoInstance(EntranceDaoInterface.class);
		retirementDao = createDaoInstance(RetirementDaoInterface.class);
		humanNoraml = createBeanInstance(HumanNormalReferenceBeanInterface.class);
		humanHistory = createBeanInstance(HumanHistoryReferenceBeanInterface.class);
		humanArray = createBeanInstance(HumanArrayReferenceBeanInterface.class);
	}
	
	@Override
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// CSVデータリストを準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// 人事情報付加
		addHumanData(csvDataList, fieldList, targetDate, workPlaceCode, employmentContractCode, sectionCode,
				positionCode);
		// 人事入社情報付加
		addEntranceData(csvDataList, fieldList);
		// 人事退職情報付加
		addRetirementData(csvDataList, fieldList);
		// 人事汎用情報
		addHumanGeneralData(csvDataList, fieldList, targetDate);
		// CSVデータリストを取得
		return csvDataList;
	}
	
	@Override
	protected String[] getHeader(ExportDtoInterface exportDto, List<String> fieldList, Date targetDate)
			throws MospException {
		// コードキー(エクスポートフィールド)を準備(データ区分と同じ)
		String codeKey = exportDto.getExportTable();
		// ヘッダを準備
		String[] header = new String[fieldList.size()];
		// インデックスを準備
		int i = 0;
		// フィールド毎にヘッダを付加
		for (String field : fieldList) {
			// ヘッダ名取得
			String headerName = NameUtility.getName(mospParams, field);
			// ヘッダ名が存在する場合
			if (MospUtility.isEmpty(headerName) == false) {
				// ヘッダ名を設定
				header[i++] = headerName;
				// 次のフィールドへ
				continue;
			}
			// 項目コードをdivisionと項目キーに分割
			String[] fieldNames = MospUtility.split(field, MospConst.APP_PROPERTY_SEPARATOR);
			// 配列2番目がある場合
			if (fieldNames.length > 1) {
				StringBuilder sb = new StringBuilder(NameUtility.getName(mospParams, fieldNames[1])).append(
						PfNameUtility.parentheses(mospParams, NameUtility.getName(mospParams, fieldNames[0])));
				header[i++] = sb.toString();
				continue;
			}
			// コードからヘッダ名を取得
			header[i++] = getCodeName(field, codeKey);
		}
		// ヘッダを取得
		return header;
	}
	
	/**
	 * 検索条件に基づき人事情報を検索し、CSVデータリストに付加する。<br>
	 * @param csvDataList            CSVデータリスト
	 * @param fieldList              エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addHumanData(List<String[]> csvDataList, List<String> fieldList, Date targetDate,
			String workPlaceCode, String employmentContractCode, String sectionCode, String positionCode)
			throws MospException {
		// 人事情報検索条件設定
		humanSearch.setTargetDate(targetDate);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentContractCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setPositionCode(positionCode);
		// 検索条件設定(状態)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 検索条件設定(下位所属要否)
		humanSearch.setNeedLowerSection(true);
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 人事情報検索
		humanList = humanSearch.search();
		// 人事情報毎にCSVデータリストに付加
		for (HumanDtoInterface human : humanList) {
			// CSVデータ準備
			String[] csvData = new String[fieldList.size()];
			// インデックス準備
			int i = 0;
			// フィールド情報毎にCSVデータに人事情報を付加
			for (String field : fieldList) {
				csvData[i++] = getHumanData(human, field, targetDate);
			}
			// CSVデータをCSVデータリストに追加
			csvDataList.add(csvData);
		}
	}
	
	/**
	 * CSVデータリストに入社情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addEntranceData(List<String[]> csvDataList, List<String> fieldList) throws MospException {
		// 入社情報フィールド情報準備
		Integer entranceDateIndex = null;
		// フィールドリスト確認
		for (int i = 0; i < fieldList.size(); i++) {
			// 入社日である場合
			if (MospUtility.isEqual(fieldList.get(i), PlatformFileConst.FIELD_ENTRANCE_DATE)) {
				entranceDateIndex = Integer.valueOf(i);
				break;
			}
		}
		// 入社情報出力要否確認
		if (entranceDateIndex == null) {
			return;
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			// 人事情報取得
			HumanDtoInterface human = humanList.get(i);
			// 入社情報取得及び確認
			EntranceDtoInterface entrance = entranceDao.findForInfo(human.getPersonalId());
			if (entrance == null) {
				continue;
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i);
			// 入社情報設定
			csvData[entranceDateIndex.intValue()] = getStringDate(entrance.getEntranceDate());
		}
	}
	
	/**
	 * CSVデータリストに退職情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addRetirementData(List<String[]> csvDataList, List<String> fieldList) throws MospException {
		// 退職情報フィールド情報準備
		Integer retirementDateIndex = null;
		Integer retirementReasonIndex = null;
		Integer retirementDetailIndex = null;
		// フィールドリスト確認
		for (int i = 0; i < fieldList.size(); i++) {
			// エクスポートフィールド名称を取得
			String field = fieldList.get(i);
			// 退職日である場合
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_RETIREMENT_DATE)) {
				retirementDateIndex = Integer.valueOf(i);
			}
			// 退職理由である場合
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_RETIREMENT_REASON)) {
				retirementReasonIndex = Integer.valueOf(i);
			}
			// 退職理由詳細である場合
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_RETIREMENT_DETAIL)) {
				retirementDetailIndex = Integer.valueOf(i);
			}
		}
		// 退職情報出力要否確認
		if (retirementDateIndex == null && retirementReasonIndex == null && retirementDetailIndex == null) {
			return;
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humanList.size(); i++) {
			// 人事情報取得
			HumanDtoInterface human = humanList.get(i);
			// 退職情報取得及び確認
			RetirementDtoInterface retirement = retirementDao.findForInfo(human.getPersonalId());
			if (retirement == null) {
				continue;
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i);
			// 退職情報設定
			if (retirementDateIndex != null) {
				csvData[retirementDateIndex.intValue()] = getStringDate(retirement.getRetirementDate());
			}
			if (retirementReasonIndex != null) {
				csvData[retirementReasonIndex.intValue()] = retirement.getRetirementReason();
			}
			if (retirementDetailIndex != null) {
				csvData[retirementDetailIndex.intValue()] = retirement.getRetirementDetail();
			}
		}
	}
	
	/**
	 * CSVデータリストに人事汎用通常情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addHumanGeneralData(List<String[]> csvDataList, List<String> fieldList, Date targetDate)
			throws MospException {
		// エクスポートフィールドマスタ名称毎に処理
		for (int i = 0; i < fieldList.size(); i++) {
			// エクスポートフィールド名称を取得
			String field = fieldList.get(i);
			// 項目コードをdivisionと項目キーに分割
			String[] fieldName = MospUtility.split(field, MospConst.APP_PROPERTY_SEPARATOR);
			// 人事管理表示設定情報を取得
			ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(fieldName[0]);
			// 人事汎用区分でない場合
			if (viewConfig == null) {
				continue;
			}
			// 項目インデックス取得
			int index = Integer.valueOf(i);
			// CSVデータリスト毎に情報を付加
			for (int j = 0; j < humanList.size(); j++) {
				// 人事情報取得
				HumanDtoInterface human = humanList.get(j);
				// 値準備
				String value = "";
				// 通常の場合
				if (viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
					// 人事汎用通常情報をマップ取得
					value = humanNoraml.getNormalItemValue(fieldName[0], ExportCardAction.KEY_VIEW_HUMAN_EXPORT,
							human.getPersonalId(), targetDate, targetDate, fieldName[1], true);
				}
				// 履歴の場合
				if (viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
					value = humanHistory.getHistoryItemValue(fieldName[0], ExportCardAction.KEY_VIEW_HUMAN_EXPORT,
							human.getPersonalId(), targetDate, fieldName[1], true);
				}
				// 一覧の場合
				if (viewConfig.getType().equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
					value = humanArray.getArrayItemValue(fieldName[0], ExportCardAction.KEY_VIEW_HUMAN_EXPORT,
							human.getPersonalId(), targetDate, fieldName[1], true);
				}
				// CSVデータ取得
				String[] csvData = csvDataList.get(j);
				csvData[index] = value;
			}
		}
	}
	
	@Override
	public boolean isExistLikeFieldName(String exportCode, String[] aryFieldName) throws MospException {
		List<ExportFieldDtoInterface> list = exportFieldRefer.getLikeStartNameList(exportCode, aryFieldName);
		list = list == null ? new ArrayList<ExportFieldDtoInterface>() : list;
		return list.isEmpty() ? false : true;
	}
	
}
