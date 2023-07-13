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
package jp.mosp.platform.bean.system.impl;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.system.ReceptionIcCardReferenceBeanInterface;
import jp.mosp.platform.dao.system.ReceptionIcCardDaoInterface;
import jp.mosp.platform.dto.system.ReceptionIcCardDtoInterface;

/**
 * カードID受付情報参照処理。<br>
 */
public class ReceptionIcCardReferenceBean extends BaseBean implements ReceptionIcCardReferenceBeanInterface {
	
	/**
	 * カードID受付情報DAO。<br>
	 */
	protected ReceptionIcCardDaoInterface dao;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public ReceptionIcCardReferenceBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(ReceptionIcCardDaoInterface.class);
	}
	
	@Override
	public ReceptionIcCardDtoInterface findForKey(String receiptNumber) throws MospException {
		return dao.findForKey(Long.parseLong(receiptNumber));
	}
	
	@Override
	public ReceptionIcCardDtoInterface findForIcCardId(String icCardId) throws MospException {
		return dao.findForIcCardId(icCardId);
	}
	
}
