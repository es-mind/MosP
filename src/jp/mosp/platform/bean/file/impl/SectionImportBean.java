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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.SectionImportBeanInterface;
import jp.mosp.platform.bean.system.impl.SectionRegistBean;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.system.impl.PfmSectionDao;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.system.constant.PlatformSystemConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 所属マスタインポートクラス。
 */
public class SectionImportBean extends SectionRegistBean implements SectionImportBeanInterface {
	
	/**
	 * 所属情報リスト。<br>
	 */
	protected List<SectionDtoInterface> sectionList;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SectionImportBean() {
		super();
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
		// 所属マスタ情報毎に登録
		for (int i = 0; i < sectionList.size(); i++) {
			// 所属マスタ情報登録
			registSectionDto(sectionList.get(i));
		}
		// 登録件数取得
		return sectionList.size();
	}
	
	/**
	 * 登録対象リスト群を取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストから所属マスタ情報リストに変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * @param importDto インポートマスタ情報
	 * @param dataList 登録情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void getTargetLists(ImportDtoInterface importDto, List<String[]> dataList) throws MospException {
		// 所属マスタリスト準備
		sectionList = new ArrayList<SectionDtoInterface>();
		// インポートフィールド情報取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// 所属情報取得及び確認
			SectionDtoInterface sectionDto = getSectionDto(fieldList, data, i);
			if (sectionDto != null) {
				// 所属情報リストに追加
				sectionList.add(sectionDto);
			}
		}
		// 経路設定
		for (SectionDtoInterface sectionDto : sectionList) {
			// 経路設定
			sectionDto.setClassRoute(PlatformSystemConst.SEPARATOR_CLASS_ROUTE);
			// 上位所属取得
			SectionDtoInterface upperSectionDto = getUpperSection(sectionDto, dataList, fieldList);
			while (upperSectionDto != null) {
				if (upperSectionDto.getClassRoute() != null
						&& upperSectionDto.getClassRoute().contains(PlatformSystemConst.SEPARATOR_CLASS_ROUTE)) {
					// 所属経路区切文字を含む場合
					sectionDto.setClassRoute(upperSectionDto.getClassRoute() + upperSectionDto.getSectionCode()
							+ sectionDto.getClassRoute());
					break;
				}
				// 経路追加
				sectionDto.setClassRoute(PlatformSystemConst.SEPARATOR_CLASS_ROUTE + upperSectionDto.getSectionCode()
						+ sectionDto.getClassRoute());
				// 上位所属取得
				upperSectionDto = getUpperSection(upperSectionDto, dataList, fieldList);
			}
		}
	}
	
	/**
	 * 登録情報リストから対象所属の上位所属を取得する。<br>
	 * @param dto 対象DTO
	 * @param dataList 登録情報リスト
	 * @param fieldList インポートフィールド情報リスト
	 * @return 上位所属
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private SectionDtoInterface getUpperSection(SectionDtoInterface dto, List<String[]> dataList,
			List<ImportFieldDtoInterface> fieldList) throws MospException {
		// 上位所属コード
		String upperSectionCode = "";
		for (String[] data : dataList) {
			if (dto.getSectionCode().equals(getFieldValue(PfmSectionDao.COL_SECTION_CODE, fieldList, data)) && dto
				.getActivateDate().equals(getDateFieldValue(PfmSectionDao.COL_ACTIVATE_DATE, fieldList, data))) {
				// 所属コード及び有効日が一致する場合
				upperSectionCode = getFieldValue(PlatformFileConst.FIELD_UPPER_SECTION_CODE, fieldList, data);
			}
		}
		if (upperSectionCode == null || upperSectionCode.isEmpty() || upperSectionCode.equals(dto.getSectionCode())) {
			return null;
		}
		// 上位所属の最大有効日
		Date maxUpperActivateDate = null;
		String upperInactivateFlag = Integer.toString(MospConst.INACTIVATE_FLAG_OFF);
		for (String[] data : dataList) {
			if (upperSectionCode.equals(getFieldValue(PfmSectionDao.COL_SECTION_CODE, fieldList, data))) {
				// 上位所属の有効日
				Date upperActivateDate = getDateFieldValue(PfmSectionDao.COL_ACTIVATE_DATE, fieldList, data);
				if (upperActivateDate != null && !upperActivateDate.after(dto.getActivateDate())
						&& (maxUpperActivateDate == null || !maxUpperActivateDate.after(upperActivateDate))) {
					// 上位所属の有効日を上位所属の最大有効日とする
					maxUpperActivateDate = upperActivateDate;
					upperInactivateFlag = getFieldValue(PfmSectionDao.COL_CLOSE_FLAG, fieldList, data);
				}
			}
		}
		SectionDtoInterface upperSectionDto = dao.findForInfo(upperSectionCode, dto.getActivateDate());
		if (upperSectionDto != null
				&& (maxUpperActivateDate == null || upperSectionDto.getActivateDate().after(maxUpperActivateDate))) {
			if (upperSectionDto.getCloseFlag() == MospConst.INACTIVATE_FLAG_OFF) {
				// 有効の場合
				return upperSectionDto;
			}
			return null;
		}
		if (maxUpperActivateDate == null
				|| Integer.toString(MospConst.INACTIVATE_FLAG_ON).equals(upperInactivateFlag)) {
			return null;
		}
		SectionDtoInterface sectionDto = getInitDto();
		sectionDto.setSectionCode(upperSectionCode);
		sectionDto.setActivateDate(maxUpperActivateDate);
		sectionDto.setCloseFlag(Integer.parseInt(upperInactivateFlag));
		return sectionDto;
	}
	
	/**
	 * 所属情報を取得する。<br>
	 * インポートフィールド情報リストに従い、登録情報リストから所属情報に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * インポート不能登録情報の場合は、nullを返す。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param row       行インデックス
	 * @return 所属情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected SectionDtoInterface getSectionDto(List<ImportFieldDtoInterface> fieldList, String[] data, int row)
			throws MospException {
		String sectionCode = getFieldValue(PfmSectionDao.COL_SECTION_CODE, fieldList, data);
		Date activateDate = getDateFieldValue(PfmSectionDao.COL_ACTIVATE_DATE, fieldList, data);
		String sectionName = getFieldValue(PfmSectionDao.COL_SECTION_NAME, fieldList, data);
		String sectionAbbr = getFieldValue(PfmSectionDao.COL_SECTION_ABBR, fieldList, data);
		String sectionDisplay = getFieldValue(PfmSectionDao.COL_SECTION_DISPLAY, fieldList, data);
		String closeFlag = getFieldValue(PfmSectionDao.COL_CLOSE_FLAG, fieldList, data);
		// 所属コード確認
		if (sectionCode == null || sectionCode.isEmpty()) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.sectionCode(mospParams), row);
			return null;
		}
		// 有効日確認
		if (activateDate == null) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorActivateDateRequired(mospParams, row);
			return null;
		}
		// 所属情報準備
		SectionDtoInterface section = dao.findForInfo(sectionCode, activateDate);
		// 所属情報確認
		if (section == null) {
			section = getInitDto();
		}
		// 所属情報に登録情報の内容を設定
		section.setSectionCode(sectionCode);
		section.setActivateDate(activateDate);
		if (sectionName != null) {
			section.setSectionName(sectionName);
		} else if (section.getSectionName() == null) {
			section.setSectionName("");
		}
		if (sectionAbbr != null) {
			section.setSectionAbbr(sectionAbbr);
		} else if (section.getSectionAbbr() == null) {
			section.setSectionAbbr("");
		}
		if (sectionDisplay != null) {
			section.setSectionDisplay(sectionDisplay);
		} else if (section.getSectionDisplay() == null) {
			section.setSectionDisplay("");
		}
		section.setCloseFlag(MospConst.INACTIVATE_FLAG_OFF);
		if (closeFlag != null && !closeFlag.isEmpty()) {
			try {
				section.setCloseFlag(Integer.parseInt(closeFlag));
			} catch (NumberFormatException e) {
				PfMessageUtility.addErrorActivateOrInactivateInvalid(mospParams, row);
				return null;
			}
		}
		// 入力チェック
		validate(section, Integer.valueOf(row));
		return section;
	}
	
	/**
	 * 所属情報を登録する。<br>
	 * @param section 所属情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registSectionDto(SectionDtoInterface section) throws MospException {
		// 所属情報確認
		if (section == null) {
			return;
		}
		// 所属情報取得及び確認
		List<SectionDtoInterface> list = dao.findForHistory(section.getSectionCode());
		if (list.isEmpty()) {
			// 新規登録
			insert(section);
			return;
		}
		// 所属情報取得及び確認
		SectionDtoInterface dto = dao.findForKey(section.getSectionCode(), section.getActivateDate());
		if (dto == null) {
			// 履歴追加
			add(section);
			return;
		}
		// 履歴更新
		update(section);
	}
	
}
