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
package jp.mosp.time.bean.impl;

import java.sql.SQLException;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TimeRecordRegistBeanInterface;
import jp.mosp.time.dao.settings.TimeRecordDaoInterface;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTimeRecordDto;

/**
 * 打刻情報登録処理。<br>
 */
public class TimeRecordRegistBean extends PlatformBean implements TimeRecordRegistBeanInterface {
	
	/**
	 * 打刻情報DAO。<br>
	 */
	private TimeRecordDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TimeRecordRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(TimeRecordDaoInterface.class);
	}
	
	@Override
	public TimeRecordDtoInterface getInitDto() {
		return new TmdTimeRecordDto();
	}
	
	@Override
	public void insert(TimeRecordDtoInterface dto) throws MospException {
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
		dto.setTmdTimeRecordId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(TimeRecordDtoInterface dto) {
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(TimeRecordDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(
				dao.findForKey(dto.getPersonalId(), dto.getWorkDate(), dto.getTimesWork(), dto.getRecordType()));
	}
	
	@Override
	public void commit() throws MospException {
		if (connection == null) {
			return;
		}
		try {
			if (connection.isClosed()) {
				return;
			}
			// コミット
			connection.commit();
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
}
