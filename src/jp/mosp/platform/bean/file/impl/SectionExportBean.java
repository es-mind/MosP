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
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.file.SectionExportBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionSearchBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.system.impl.PfmSectionDao;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.system.constant.PlatformSystemConst;

/**
 * 所属マスタエクスポートクラス。
 */
public class SectionExportBean extends BaseExportBean implements SectionExportBeanInterface {
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface	sectionReference;
	
	/**
	 * 所属マスタ検索クラス。<br>
	 */
	protected SectionSearchBeanInterface	sectionSearch;
	
	
	/**
	 * {@link BaseExportBean#BaseExportBean()}を実行する。<br>
	 */
	public SectionExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// Beanの準備
		sectionReference = createBeanInstance(SectionReferenceBeanInterface.class);
		sectionSearch = createBeanInstance(SectionSearchBeanInterface.class);
	}
	
	@Override
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// CSVデータリストを準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// 所属情報を検索
		List<SectionDtoInterface> sections = search(targetDate, sectionCode);
		// 所属情報付加
		addData(csvDataList, fieldList, sections);
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * 検索条件に基づき所属情報を検索する。<br>
	 * @param targetDate  対象日
	 * @param sectionCode 所属コード
	 * @return 所属情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<SectionDtoInterface> search(Date targetDate, String sectionCode) throws MospException {
		// 所属情報検索条件設定
		sectionSearch.setActivateDate(targetDate);
		sectionSearch.setSectionCode(sectionCode);
		sectionSearch.setSectionName("");
		sectionSearch.setSectionAbbr("");
		sectionSearch.setSectionType(PlatformSystemConst.SEARCH_SECTION_ROUTE);
		sectionSearch.setCloseFlag(Integer.toString(MospConst.DELETE_FLAG_OFF));
		return sectionSearch.getExportList();
	}
	
	/**
	 * CSVデータリストに所属情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param sections    所属情報リスト
	 */
	protected void addData(List<String[]> csvDataList, List<String> fieldList, List<SectionDtoInterface> sections) {
		// 所属情報フィールド情報準備
		Integer sectionCodeIndex = null;
		Integer activateDateIndex = null;
		Integer sectionNameIndex = null;
		Integer sectionAbbrIndex = null;
		Integer sectionDisplayIndex = null;
		Integer upperSectionCodeIndex = null;
		Integer closeFlagIndex = null;
		// エクスポートフィールド名称毎に処理
		for (int i = 0; i < fieldList.size(); i++) {
			// エクスポートフィールド名称を取得
			String field = fieldList.get(i);
			// エクスポートフィールド名称毎にインデックスを取得
			if (MospUtility.isEqual(field, PfmSectionDao.COL_SECTION_CODE)) {
				sectionCodeIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PfmSectionDao.COL_ACTIVATE_DATE)) {
				activateDateIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PfmSectionDao.COL_SECTION_NAME)) {
				sectionNameIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PfmSectionDao.COL_SECTION_ABBR)) {
				sectionAbbrIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PfmSectionDao.COL_SECTION_DISPLAY)) {
				sectionDisplayIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_UPPER_SECTION_CODE)) {
				upperSectionCodeIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PfmSectionDao.COL_CLOSE_FLAG)) {
				closeFlagIndex = Integer.valueOf(i);
			}
		}
		// CSVデータリスト毎に情報を付加
		for (SectionDtoInterface dto : sections) {
			// CSVデータ準備
			String[] csvData = new String[fieldList.size()];
			// 所属情報設定
			if (sectionCodeIndex != null) {
				csvData[sectionCodeIndex.intValue()] = dto.getSectionCode();
			}
			if (activateDateIndex != null) {
				csvData[activateDateIndex.intValue()] = getStringDate(dto.getActivateDate());
			}
			if (sectionNameIndex != null) {
				csvData[sectionNameIndex.intValue()] = dto.getSectionName();
			}
			if (sectionAbbrIndex != null) {
				csvData[sectionAbbrIndex.intValue()] = dto.getSectionAbbr();
			}
			if (sectionDisplayIndex != null) {
				csvData[sectionDisplayIndex.intValue()] = dto.getSectionDisplay();
			}
			if (upperSectionCodeIndex != null) {
				csvData[upperSectionCodeIndex.intValue()] = getUpperSectionCode(dto);
			}
			if (closeFlagIndex != null) {
				csvData[closeFlagIndex.intValue()] = Integer.toString(dto.getCloseFlag());
			}
			// CSVデータをCSVデータリストに追加
			csvDataList.add(csvData);
		}
	}
	
	/**
	 * 上位所属コードを取得する。
	 * @param dto 対象DTO
	 * @return 上位所属コード
	 */
	protected String getUpperSectionCode(SectionDtoInterface dto) {
		String[] sectionArray = sectionReference.getClassRouteArray(dto);
		int length = sectionArray.length;
		if (length == 0) {
			return "";
		}
		return sectionArray[--length];
	}
	
}
