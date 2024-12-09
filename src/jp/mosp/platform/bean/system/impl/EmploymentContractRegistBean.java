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
package jp.mosp.platform.bean.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.EmploymentContractRegistBeanInterface;
import jp.mosp.platform.dao.system.EmploymentContractDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmEmploymentContractDto;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 雇用契約マスタ登録クラス。
 */
public class EmploymentContractRegistBean extends PlatformBean implements EmploymentContractRegistBeanInterface {
	
	/**
	 * 雇用契約マスタDAOクラス。<br>
	 */
	protected EmploymentContractDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public EmploymentContractRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(EmploymentContractDaoInterface.class);
	}
	
	@Override
	public EmploymentContractDtoInterface getInitDto() {
		return new PfmEmploymentContractDto();
	}
	
	@Override
	public void insert(EmploymentContractDtoInterface dto) throws MospException {
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
		dto.setPfmEmploymentContractId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(EmploymentContractDtoInterface dto) throws MospException {
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
		dto.setPfmEmploymentContractId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(EmploymentContractDtoInterface dto) throws MospException {
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
		// 論理削除
		logicalDelete(dao, dto.getPfmEmploymentContractId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmEmploymentContractId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 一括更新処理
		for (String code : getCodeList(idArray)) {
			// 対象雇用契約における有効日の情報を取得
			EmploymentContractDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象雇用契約における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象雇用契約情報確認
				if (dto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, code);
					continue;
				}
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmEmploymentContractId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmEmploymentContractId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmEmploymentContractId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	@Override
	public void delete(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 対象雇用契約リストの中身を削除
		for (long id : idArray) {
			// 削除対象雇用契約を設定している社員がいないかを確認
			checkDelete((EmploymentContractDtoInterface)dao.findForKey(id, true));
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら履歴削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, id);
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(EmploymentContractDtoInterface dto) throws MospException {
		// 新規登録コードの履歴が存在しないことを確認
		checkDuplicateInsert(dao.findForHistory(dto.getEmploymentContractCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(EmploymentContractDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getEmploymentContractCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<EmploymentContractDtoInterface> list = dao.findForHistory(dto.getEmploymentContractCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getEmploymentContractCode(), humanList);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(EmploymentContractDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmEmploymentContractId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getPfmEmploymentContractId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 更新対象コードの履歴情報を取得
		List<EmploymentContractDtoInterface> list = dao.findForHistory(dto.getEmploymentContractCode());
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getEmploymentContractCode(), humanList);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象雇用契約を設定している社員がいないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(EmploymentContractDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmEmploymentContractId());
		// 対象DTOの無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<EmploymentContractDtoInterface> list = dao.findForHistory(dto.getEmploymentContractCode());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getEmploymentContractCode(), humanList);
	}
	
	/**
	 * 雇用契約コードリストを取得する。<br>
	 * @param idArray レコード識別ID配列
	 * @return 雇用契約コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			EmploymentContractDtoInterface dto = (EmploymentContractDtoInterface)dao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getEmploymentContractCode());
		}
		return list;
	}
	
	/**
	 * 人事マスタリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param code 対象コード
	 * @param list 人事マスタリスト
	 */
	protected void checkCodeIsUsed(String code, List<HumanDtoInterface> list) {
		// 人事マスタリストの中身を確認
		for (HumanDtoInterface dto : list) {
			// 対象コード確認
			if (code.equals(dto.getEmploymentContractCode())) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorCodeIsUsed(mospParams, code, dto.getEmployeeCode());
			}
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(EmploymentContractDtoInterface dto) {
		// 妥当性確認
	}
	
}
