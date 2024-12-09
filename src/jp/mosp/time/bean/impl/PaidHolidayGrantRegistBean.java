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

import jp.mosp.framework.base.MospException;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.PaidHolidayGrantRegistBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayGrantDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayGrantDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtPaidHolidayGrantDto;

/**
 * 有給休暇付与登録クラス。
 */
public class PaidHolidayGrantRegistBean extends TimeBean implements PaidHolidayGrantRegistBeanInterface {
	
	/**
	 * 有給休暇付与DAOクラス。
	 */
	protected PaidHolidayGrantDaoInterface dao;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public PaidHolidayGrantRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(PaidHolidayGrantDaoInterface.class);
	}
	
	@Override
	public PaidHolidayGrantDtoInterface getInitDto() {
		return new TmtPaidHolidayGrantDto();
	}
	
	@Override
	public void regist(PaidHolidayGrantDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getPersonalId(), dto.getGrantDate()) == null) {
			// 新規登録
			insert(dto);
			return;
		}
		// 更新処理
		update(dto);
	}
	
	@Override
	public void delete(PaidHolidayGrantDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmtPaidHolidayGrantId());
	}
	
	/**
	 * 新規登録を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert(PaidHolidayGrantDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		dto.setTmtPaidHolidayGrantId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 更新処理を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update(PaidHolidayGrantDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmtPaidHolidayGrantId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmtPaidHolidayGrantId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkInsert(PaidHolidayGrantDtoInterface dto) throws MospException {
		// 重複確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getGrantDate()));
	}
	
	/**
	 * 更新処理時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkUpdate(PaidHolidayGrantDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmtPaidHolidayGrantId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDelete(PaidHolidayGrantDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmtPaidHolidayGrantId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(PaidHolidayGrantDtoInterface dto) {
		// 処理無し
	}
	
}
