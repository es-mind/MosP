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

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.ExportFieldRegistBeanInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.file.impl.PfaExportFieldDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * エクスポートフィールドマスタ登録クラス。
 */
public class ExportFieldRegistBean extends PlatformBean implements ExportFieldRegistBeanInterface {
	
	/**
	 * エクスポートフィールドマスタDAOクラス。<br>
	 */
	ExportFieldDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ExportFieldRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(ExportFieldDaoInterface.class);
	}
	
	@Override
	public ExportFieldDtoInterface getInitDto() {
		return new PfaExportFieldDto();
	}
	
	@Override
	public void insert(String exportCode, String inactivateFlag, String[] fieldArray) throws MospException {
		// フィールド配列の妥当性確認
		validateAryField(fieldArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		ExportFieldDtoInterface dto = getInitDto();
		int i = 0;
		for (String fieldName : fieldArray) {
			setDtoFields(dto, exportCode, inactivateFlag, fieldName, ++i);
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 新規登録情報の検証
			checkInsert(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setPfaExportFieldId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void update(String exportCode, String inactivateFlag, String[] fieldArray) throws MospException {
		// フィールド配列の妥当性確認
		validateAryField(fieldArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		List<ExportFieldDtoInterface> list = dao.findForList(exportCode);
		for (ExportFieldDtoInterface dto : list) {
			// 論理削除
			logicalDelete(dao, dto.getPfaExportFieldId());
		}
		ExportFieldDtoInterface dto = getInitDto();
		int i = 0;
		for (String fieldName : fieldArray) {
			setDtoFields(dto, exportCode, inactivateFlag, fieldName, ++i);
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 履歴更新情報の検証
			checkUpdate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setPfaExportFieldId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void delete(String exportCode) throws MospException {
		List<ExportFieldDtoInterface> list = dao.findForList(exportCode);
		for (ExportFieldDtoInterface dto : list) {
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, dto.getPfaExportFieldId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(ExportFieldDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getExportCode(), dto.getFieldName()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkUpdate(ExportFieldDtoInterface dto) {
		// 処理なし
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(ExportFieldDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaExportFieldId());
		// 対象DTOの無効フラグ確認
		if (!isDtoActivate(dto)) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(ExportFieldDtoInterface dto) {
		// TODO 妥当性確認
	}
	
	/**
	 * フィールド配列の妥当性確認を行う。<br>
	 * @param aryField 対象フィールド配列
	 */
	protected void validateAryField(String[] aryField) {
		// 存在確認
		if (aryField == null || aryField.length == 0) {
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.selectItem(mospParams));
		}
	}
	
	/**
	 * DTOに値を設定する。
	 * @param dto 対象DTO
	 * @param exportCode エクスポートコード
	 * @param inactivateFlag 無効フラグ
	 * @param fieldName フィールド名称
	 * @param i フィールド順序
	 */
	protected void setDtoFields(ExportFieldDtoInterface dto, String exportCode, String inactivateFlag, String fieldName,
			int i) {
		dto.setExportCode(exportCode);
		dto.setFieldName(fieldName);
		dto.setFieldOrder(i);
		dto.setInactivateFlag(Integer.parseInt(inactivateFlag));
	}
	
}
