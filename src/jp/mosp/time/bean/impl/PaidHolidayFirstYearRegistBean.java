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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.PaidHolidayFirstYearRegistBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayFirstYearDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmPaidHolidayFirstYearDto;

/**
 * 有給休暇初年度付与登録クラス。
 */
public class PaidHolidayFirstYearRegistBean extends PlatformBean implements PaidHolidayFirstYearRegistBeanInterface {
	
	/**
	 * 有給休暇初年度付与DAOクラス。<br>
	 */
	protected PaidHolidayFirstYearDaoInterface	dao;
	
	/**
	 * 有給休暇DAOクラス。<br>
	 */
	protected PaidHolidayDaoInterface			paidHolidayDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayFirstYearRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(PaidHolidayFirstYearDaoInterface.class);
		paidHolidayDao = createDaoInstance(PaidHolidayDaoInterface.class);
	}
	
	@Override
	public PaidHolidayFirstYearDtoInterface getInitDto() {
		return new TmmPaidHolidayFirstYearDto();
	}
	
	@Override
	public void insert(PaidHolidayFirstYearDtoInterface dto) throws MospException {
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
		dto.setTmmPaidHolidayFirstYearId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(PaidHolidayFirstYearDtoInterface dto) throws MospException {
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
		dto.setTmmPaidHolidayFirstYearId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(PaidHolidayFirstYearDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmmPaidHolidayFirstYearId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmPaidHolidayFirstYearId(dao.nextRecordId());
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
			for (int i = 1; i <= 12; i++) {
				// 対象有給休暇初年度付与における有効日の情報を取得
				PaidHolidayFirstYearDtoInterface dto = dao.findForKey(code, activateDate, i);
				// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
				if (dto == null) {
					// 対象有給休暇初年度付与における有効日以前で最新の情報を取得
					dto = dao.findForInfo(code, activateDate, i);
					// 対象有給休暇初年度付与情報確認
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
					dto.setTmmPaidHolidayFirstYearId(dao.nextRecordId());
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
					logicalDelete(dao, dto.getTmmPaidHolidayFirstYearId());
					// レコード識別ID最大値をインクリメントしてDTOに設定
					dto.setTmmPaidHolidayFirstYearId(dao.nextRecordId());
					// 登録処理
					dao.insert(dto);
				}
			}
		}
	}
	
	@Override
	public void delete(String paidHolidayCode, Date activateDate) throws MospException {
		for (int i = 1; i <= 12; i++) {
			PaidHolidayFirstYearDtoInterface dto = dao.findForKey(paidHolidayCode, activateDate, i);
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 削除確認
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmPaidHolidayFirstYearId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(PaidHolidayFirstYearDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getPaidHolidayCode(), dto.getEntranceMonth()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(PaidHolidayFirstYearDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getPaidHolidayCode(), dto.getActivateDate(), dto.getEntranceMonth()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(PaidHolidayFirstYearDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmPaidHolidayFirstYearId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(PaidHolidayFirstYearDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmPaidHolidayFirstYearId());
	}
	
	/**
	 * 有休コードリストを取得する。<br>
	 * @param idArray レコード識別ID配列
	 * @return 有休コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			PaidHolidayDtoInterface dto = (PaidHolidayDtoInterface)paidHolidayDao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getPaidHolidayCode());
		}
		return list;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(PaidHolidayFirstYearDtoInterface dto) {
		// TODO 妥当性確認
	}
	
}
