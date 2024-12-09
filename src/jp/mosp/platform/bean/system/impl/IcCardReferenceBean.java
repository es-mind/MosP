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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.IcCardReferenceBeanInterface;
import jp.mosp.platform.dao.system.IcCardDaoInterface;
import jp.mosp.platform.dto.system.IcCardDtoInterface;

/**
 * ICカードマスタ参照クラス。
 */
public class IcCardReferenceBean extends PlatformBean implements IcCardReferenceBeanInterface {
	
	/**
	 * ICカードマスタDAO。
	 */
	protected IcCardDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public IcCardReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(IcCardDaoInterface.class);
	}
	
	@Override
	public List<IcCardDtoInterface> findForHumanList(String personalId, Date activeDate) throws MospException {
		return dao.getFindForHumanList(personalId, activeDate);
	}
	
	@Override
	public IcCardDtoInterface findForCardIdInfo(String cardId, Date activeDate) throws MospException {
		return dao.findForCardIdInfo(cardId, activeDate);
	}
	
	@Override
	public IcCardDtoInterface findForKey(String cardId, Date activeDate) throws MospException {
		return dao.findForKey(cardId, activeDate);
	}
	
	@Override
	public List<IcCardDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		return dao.findForActivateDate(activateDate);
	}
	
	@Override
	public List<IcCardDtoInterface> findForList(String icCardId) throws MospException {
		return dao.findForHistory(icCardId);
	}
	
}
