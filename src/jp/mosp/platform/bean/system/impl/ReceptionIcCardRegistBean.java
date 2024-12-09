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

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.ReceptionIcCardRegistBeanInterface;
import jp.mosp.platform.dao.system.ReceptionIcCardDaoInterface;
import jp.mosp.platform.dto.system.ReceptionIcCardDtoInterface;
import jp.mosp.platform.dto.system.impl.PftReceptionIcCardDto;

/**
 * カードID受付登録クラス。
 */
public class ReceptionIcCardRegistBean extends PlatformBean implements ReceptionIcCardRegistBeanInterface {
	
	/**
	 * カードID受付DAO。
	 */
	protected ReceptionIcCardDaoInterface	dao;
	
	/**
	 * 受付時(ICカード新規登録時)のメッセージコード。<br>
	 */
	public static final String				MSG_REGIST_IC_CARD	= "ADTR001";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ReceptionIcCardRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(ReceptionIcCardDaoInterface.class);
	}
	
	@Override
	public ReceptionIcCardDtoInterface getInitDto() {
		return new PftReceptionIcCardDto();
	}
	
	@Override
	public void insert(ReceptionIcCardDtoInterface dto) throws MospException {
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
		dto.setPftReceptionIcCardId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録の検証
	 * @param dto 対象DTO
	 */
	private void checkInsert(ReceptionIcCardDtoInterface dto) {
		// 処理無し
	}
	
	/**
	 * 登録の妥当性確認。
	 * ICカードIDが既に登録されている場合、受付番号を表示する。
	 */
	@Override
	public void validate(ReceptionIcCardDtoInterface dto, Integer row) throws MospException {
		// 受付番号情報を取得
		ReceptionIcCardDtoInterface infoDto = dao.findForIcCardId(dto.getIcCardId());
		if (infoDto == null) {
			return;
		}
		mospParams.addErrorMessage(MSG_REGIST_IC_CARD, String.valueOf(infoDto.getPftReceptionIcCardId()));
	}
	
}
