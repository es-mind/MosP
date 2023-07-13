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

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalAllowanceRegistBeanInterface;
import jp.mosp.time.dao.settings.TotalAllowanceDaoInterface;
import jp.mosp.time.dto.settings.TotalAllowanceDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalAllowanceDto;

/**
 * 勤怠集計手当データ登録クラス。
 */
public class TotalAllowanceRegistBean extends PlatformBean implements TotalAllowanceRegistBeanInterface {
	
	/**
	 * 勤怠集計手当データDAOクラス。<br>
	 */
	TotalAllowanceDaoInterface totalAllowanceDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalAllowanceRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		totalAllowanceDao = createDaoInstance(TotalAllowanceDaoInterface.class);
	}
	
	@Override
	public TotalAllowanceDtoInterface getInitDto() {
		return new TmdTotalAllowanceDto();
	}
	
	@Override
	public void insert(TotalAllowanceDtoInterface dto) throws MospException {
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
		dto.setTmdTotalAllowanceId(totalAllowanceDao.nextRecordId());
		// 登録処理
		totalAllowanceDao.insert(dto);
	}
	
	@Override
	public void update(TotalAllowanceDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		logicalDelete(totalAllowanceDao, dto.getTmdTotalAllowanceId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdTotalAllowanceId(totalAllowanceDao.nextRecordId());
		// 登録処理
		totalAllowanceDao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(TotalAllowanceDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(totalAllowanceDao.findForHistory(dto.getPersonalId(), dto.getCalculationYear(),
				dto.getCalculationMonth(), dto.getAllowanceCode()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(TotalAllowanceDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(totalAllowanceDao, dto.getTmdTotalAllowanceId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(TotalAllowanceDtoInterface dto) {
		// 妥当性確認
	}
}
