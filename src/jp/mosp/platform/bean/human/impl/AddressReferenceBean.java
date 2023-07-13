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
package jp.mosp.platform.bean.human.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.AddressReferenceBeanInterface;
import jp.mosp.platform.dao.human.AddressDaoInterface;
import jp.mosp.platform.dto.human.AddressDtoInterface;

/**
 * 住所情報参照クラス。<br>
 */
public class AddressReferenceBean extends PlatformBean implements AddressReferenceBeanInterface {
	
	/**
	 * 住所区分(個人)。<br>
	 * この場合、保持者IDは個人IDとなる。<br>
	 */
	public static final String		TYPE_ADDRESS_PERSONAL	= "1";
	
	/**
	 * 住所区分(住民票)。<br>
	 * この場合、保持者IDは個人IDとなる。<br>
	 */
	public static final String		TYPE_ADDRESS_LEGAL		= "2";
	
	/**
	 * 住所情報DAOクラス。<br>
	 */
	protected AddressDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AddressReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(AddressDaoInterface.class);
	}
	
	@Override
	public AddressDtoInterface getPersonalAddress(String personalId, Date activateDate) throws MospException {
		return dao.findForKey(personalId, TYPE_ADDRESS_PERSONAL, activateDate);
	}
	
	@Override
	public List<AddressDtoInterface> getPersonalAddressList(String personalId) throws MospException {
		return dao.findForHolder(personalId, TYPE_ADDRESS_PERSONAL);
	}
	
	@Override
	public List<AddressDtoInterface> getLegalAddressList(String personalId) throws MospException {
		return dao.findForHolder(personalId, TYPE_ADDRESS_LEGAL);
	}
	
}
