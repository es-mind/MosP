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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.GeneralRegistBeanInterface;
import jp.mosp.platform.dao.system.GeneralDaoInterface;
import jp.mosp.platform.dto.system.GeneralDtoInterface;
import jp.mosp.platform.dto.system.impl.GeneralDto;

/**
 * 汎用マスタ登録クラス。
 */
public class GeneralRegistBean extends PlatformBean implements GeneralRegistBeanInterface {
	
	/**
	 * 汎用マスタDAOクラス。<br>
	 */
	protected GeneralDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public GeneralRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(GeneralDaoInterface.class);
	}
	
	@Override
	public GeneralDtoInterface getInitDto() {
		return new GeneralDto();
	}
	
	@Override
	public void regist(GeneralDtoInterface dto) throws MospException {
		// 値取得
		String type = dto.getGeneralType();
		Date date = dto.getGeneralDate();
		String code = dto.getGeneralCode();
		// 登録済汎用情報取得
		GeneralDtoInterface oldDto = dao.findForKey(type, code, date);
		if (oldDto == null) {
			// 新規登録
			insert(dto);
		} else {
			// レコード識別ID設定
			dto.setPfgGeneralId(oldDto.getPfgGeneralId());
			// 履歴更新
			update(dto);
		}
	}
	
	@Override
	public void insert(GeneralDtoInterface dto) throws MospException {
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
		dto.setPfgGeneralId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(GeneralDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		dto.setPfgGeneralId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(GeneralDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfgGeneralId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfgGeneralId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(GeneralDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getGeneralType(), dto.getGeneralCode(), dto.getGeneralDate()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(GeneralDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getGeneralType(), dto.getGeneralCode(), dto.getGeneralDate()));
	}
	
	@Override
	public void validate(GeneralDtoInterface dto) {
		// 処理なし
	}
	
	@Override
	public void delete(GeneralDtoInterface dto) throws MospException {
		// 論理削除
		logicalDelete(dao, dto.getPfgGeneralId());
	}
	
	@Override
	public void delete(String generalType, String generalCode, Date generalDate) throws MospException {
		// 削除対象情報取得
		GeneralDtoInterface dto = dao.findForKey(generalType, generalCode, generalDate);
		// 削除
		delete(dto);
	}
	
}
