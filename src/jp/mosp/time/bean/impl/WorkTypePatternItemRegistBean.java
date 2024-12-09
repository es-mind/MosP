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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.WorkTypePatternItemRegistBeanInterface;
import jp.mosp.time.dao.settings.WorkTypePatternItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmaWorkTypePatternItemDto;

/**
 * 勤務形態パターン項目登録クラス。
 */
public class WorkTypePatternItemRegistBean extends PlatformBean implements WorkTypePatternItemRegistBeanInterface {
	
	/**
	 * 勤務形態パターン項目DAOクラス。<br>
	 */
	protected WorkTypePatternItemDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypePatternItemRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(WorkTypePatternItemDaoInterface.class);
	}
	
	@Override
	public WorkTypePatternItemDtoInterface getInitDto() {
		return new TmaWorkTypePatternItemDto();
	}
	
	@Override
	public void insert(String patternCode, Date activateDate, int inactivateFlag, String[] workTypeCodeArray)
			throws MospException {
		// 配列の妥当性確認
		validate(workTypeCodeArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		int i = 0;
		for (String workTypeCode : workTypeCodeArray) {
			WorkTypePatternItemDtoInterface dto = getInitDto();
			setDtoFields(dto, patternCode, activateDate, workTypeCode, ++i, inactivateFlag);
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
			dto.setTmaWorkTypePatternItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void add(String patternCode, Date activateDate, int inactivateFlag, String[] workTypeCodeArray)
			throws MospException {
		// 配列の妥当性確認
		validate(workTypeCodeArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		int i = 0;
		for (String workTypeCode : workTypeCodeArray) {
			WorkTypePatternItemDtoInterface dto = getInitDto();
			setDtoFields(dto, patternCode, activateDate, workTypeCode, ++i, inactivateFlag);
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 履歴追加情報の検証
			checkAdd(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmaWorkTypePatternItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void update(String patternCode, Date activateDate, int inactivateFlag, String[] workTypeCodeArray)
			throws MospException {
		// 配列の妥当性確認
		validate(workTypeCodeArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		List<WorkTypePatternItemDtoInterface> list = dao.findForList(patternCode, activateDate);
		for (WorkTypePatternItemDtoInterface dto : list) {
			// 論理削除
			logicalDelete(dao, dto.getTmaWorkTypePatternItemId());
		}
		int i = 0;
		for (String workTypeCode : workTypeCodeArray) {
			WorkTypePatternItemDtoInterface dto = getInitDto();
			setDtoFields(dto, patternCode, activateDate, workTypeCode, ++i, inactivateFlag);
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
			dto.setTmaWorkTypePatternItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void delete(String patternCode, Date activateDate) throws MospException {
		List<WorkTypePatternItemDtoInterface> list = dao.findForList(patternCode, activateDate);
		for (WorkTypePatternItemDtoInterface dto : list) {
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmaWorkTypePatternItemId());
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(WorkTypePatternItemDtoInterface dto) {
		// 処理無し
	}
	
	/**
	 * 配列の妥当性確認を行う。<br>
	 * @param <T> T
	 * @param array 対象配列
	 */
	protected <T> void validate(T[] array) {
		if (array == null || array.length == 0) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.selectItem(mospParams));
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(WorkTypePatternItemDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPatternCode(), dto.getActivateDate(), dto.getWorkTypeCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(WorkTypePatternItemDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getPatternCode(), dto.getActivateDate(), dto.getWorkTypeCode()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void checkUpdate(WorkTypePatternItemDtoInterface dto) {
		// 処理なし
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(WorkTypePatternItemDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmaWorkTypePatternItemId());
		// 対象DTOの無効フラグ確認
		if (!isDtoActivate(dto)) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * DTOに値を設定する。
	 * @param dto 対象DTO
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @param workTypeCode 勤務形態コード
	 * @param itemOrder 項目順序
	 * @param inactivateFlag 無効フラグ
	 */
	protected void setDtoFields(WorkTypePatternItemDtoInterface dto, String patternCode, Date activateDate,
			String workTypeCode, int itemOrder, int inactivateFlag) {
		dto.setPatternCode(patternCode);
		dto.setActivateDate(activateDate);
		dto.setWorkTypeCode(workTypeCode);
		dto.setItemOrder(itemOrder);
		dto.setInactivateFlag(inactivateFlag);
	}
	
}
