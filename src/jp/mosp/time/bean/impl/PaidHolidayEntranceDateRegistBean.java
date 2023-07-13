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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayEntranceDateRegistBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayEntranceDateDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmPaidHolidayEntranceDateDto;

/**
 * 有給休暇自動付与(入社日)登録クラス。
 */
public class PaidHolidayEntranceDateRegistBean extends PlatformBean
		implements PaidHolidayEntranceDateRegistBeanInterface {
	
	/**
	 * 有給休暇自動付与(入社日)DAOクラス。<br>
	 */
	PaidHolidayEntranceDateDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayEntranceDateRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(PaidHolidayEntranceDateDaoInterface.class);
	}
	
	@Override
	public PaidHolidayEntranceDateDtoInterface getInitDto() {
		return new TmmPaidHolidayEntranceDateDto();
	}
	
	@Override
	public void insert(PaidHolidayEntranceDateDtoInterface dto) throws MospException {
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
		dto.setTmmPaidHolidayEntranceDateId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(String paidHolidayCode, Date activateDate) throws MospException {
		List<PaidHolidayEntranceDateDtoInterface> list = dao.findForList(paidHolidayCode, activateDate);
		for (PaidHolidayEntranceDateDtoInterface dto : list) {
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
			logicalDelete(dao, dto.getTmmPaidHolidayEntranceDateId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(PaidHolidayEntranceDateDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getPaidHolidayCode(), dto.getWorkMonth()));
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(PaidHolidayEntranceDateDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmPaidHolidayEntranceDateId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(PaidHolidayEntranceDateDtoInterface dto) {
		// 妥当性確認
	}
	
}
