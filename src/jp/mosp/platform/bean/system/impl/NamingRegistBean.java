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
import jp.mosp.platform.bean.system.NamingRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.HumanHistoryDaoInterface;
import jp.mosp.platform.dao.system.NamingDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmNamingDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 名称区分マスタ登録クラス。
 */
public class NamingRegistBean extends PlatformBean implements NamingRegistBeanInterface {
	
	/**
	 * 名称項目コード項目長。<br>
	 */
	protected static final int			LEN_NAMING_ITEM_CODE	= 10;
	
	/**
	 * 名称項目名称項目長。<br>
	 */
	protected static final int			LEN_NAMING_ITEM_NAME	= 15;
	
	/**
	 * 名称項目略称項目長(バイト数)。<br>
	 */
	protected static final int			LEN_NAMING_ITEM_ABBR	= 6;
	
	/**
	 * 名称区分マスタDAOクラス。<br>
	 */
	protected NamingDaoInterface		dao;
	
	/**
	 * 人事汎用履歴DAOクラス。<br>
	 */
	protected HumanHistoryDaoInterface	humanHistoryDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public NamingRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(NamingDaoInterface.class);
		humanHistoryDao = createDaoInstance(HumanHistoryDaoInterface.class);
	}
	
	@Override
	public NamingDtoInterface getInitDto() {
		return new PfmNamingDto();
	}
	
	@Override
	public void insert(NamingDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmNamingId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(NamingDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmNamingId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(NamingDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmNamingId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmNamingId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(long[] idArray, String namingType, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 一括更新処理
		for (String code : getCodeList(idArray)) {
			// 対象雇用契約における有効日の情報を取得
			NamingDtoInterface dto = dao.findForKey(namingType, code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象雇用契約における有効日以前で最新の情報を取得
				dto = dao.findForInfo(namingType, code, activateDate);
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
				validate(dto, null);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmNamingId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto, null);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmNamingId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmNamingId(dao.nextRecordId());
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
			checkDelete((NamingDtoInterface)dao.findForKey(id, true));
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
	protected void checkInsert(NamingDtoInterface dto) throws MospException {
		// 新規登録コードの履歴が存在しないことを確認
		checkDuplicateInsert(dao.findForHistory(dto.getNamingType(), dto.getNamingItemCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(NamingDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getNamingType(), dto.getNamingItemCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<NamingDtoInterface> list = dao.findForHistory(dto.getNamingType(), dto.getNamingItemCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getNamingItemCode(), humanList);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(NamingDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmNamingId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getPfmNamingId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 更新対象コードの履歴情報を取得
		List<NamingDtoInterface> list = dao.findForHistory(dto.getNamingType(), dto.getNamingItemCode());
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getNamingItemCode(), humanList);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象雇用契約を設定している社員がいないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(NamingDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmNamingId());
		// 対象DTOの無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<NamingDtoInterface> list = dao.findForHistory(dto.getNamingType(), dto.getNamingItemCode());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getNamingItemCode(), humanList);
	}
	
	/**
	 * 名称項目コードリストを取得する。<br>
	 * @param idArray レコード識別ID配列
	 * @return 名称項目コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			NamingDtoInterface dto = (NamingDtoInterface)dao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getNamingItemCode());
		}
		return list;
	}
	
	/**
	 * 人事マスタリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param code 対象コード
	 * @param list 人事マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkCodeIsUsed(String code, List<HumanDtoInterface> list) throws MospException {
		// 人事マスタリストの中身を確認
		for (HumanDtoInterface dto : list) {
			// 人事汎用履歴情報取得
			List<HumanHistoryDtoInterface> humanHistoryList = humanHistoryDao.findForHistory(dto.getPersonalId(),
					PlatformConst.NAMING_TYPE_POST);
			// 人事汎用履歴情報の中身を確認
			for (HumanHistoryDtoInterface historyDto : humanHistoryList) {
				// 対象コード確認
				if (code.equals(historyDto.getHumanItemValue())) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeIsUsed(mospParams, code, dto.getEmployeeCode());
				}
			}
		}
	}
	
	@Override
	public void validate(NamingDtoInterface dto, Integer row) {
		// 必須確認(名称区分)
		checkRequired(dto.getNamingType(), PfNameUtility.namingType(mospParams), row);
		// 名称区分存在確認
		if (isExistNamingType(dto.getNamingType()) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoItem(mospParams, PfNameUtility.namingType(mospParams));
			return;
		}
		// 必須確認(名称項目コード)
		checkRequired(dto.getNamingItemCode(), PfNameUtility.namingItemCode(mospParams), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), PfNameUtility.activateDate(mospParams), row);
		// 必須確認(名称項目名)
		checkRequired(dto.getNamingItemName(), PfNameUtility.namingItemName(mospParams), row);
		// 必須確認(名称項目略称)
		checkRequired(dto.getNamingItemAbbr(), PfNameUtility.namingItemAbbreviation(mospParams), row);
		// 桁数確認(名称項目コード)
		checkLength(dto.getNamingItemCode(), LEN_NAMING_ITEM_CODE, PfNameUtility.namingItemCode(mospParams), row);
		// 桁数確認(名称項目名)
		checkLength(dto.getNamingItemName(), LEN_NAMING_ITEM_NAME, PfNameUtility.namingItemName(mospParams), row);
		// バイト数(表示上)確認(名称項目略称)
		checkByteLength(dto.getNamingItemAbbr(), LEN_NAMING_ITEM_ABBR, PfNameUtility.namingItemAbbreviation(mospParams),
				row);
		// 型確認(名称項目コード)
		checkTypeCode(dto.getNamingItemCode(), PfNameUtility.namingItemCode(mospParams), row);
		// 型確認(無効フラグ)
		checkInactivateFlag(dto.getInactivateFlag(), row);
	}
	
	/**
	 * 名称区分がコードに存在するか確認
	 * @param namingType 名称区分
	 * @return (true：存在する、false：存在しない)
	 */
	public boolean isExistNamingType(String namingType) {
		// コード配列取得
		String[][] codeNamingType = mospParams.getProperties().getCodeArray(PlatformConst.CODE_KEY_NAMING_TYPE, false);
		// コード配列毎に処理
		for (String[] element : codeNamingType) {
			String item = element[0];
			// 同じアイテムキーが存在する場合
			if (item.equals(namingType)) {
				// 存在する
				return true;
			}
		}
		// 存在しない
		return false;
	}
	
}
