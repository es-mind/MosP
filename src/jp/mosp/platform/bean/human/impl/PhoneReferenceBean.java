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
package jp.mosp.platform.bean.human.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.PhoneReferenceBeanInterface;
import jp.mosp.platform.dao.human.PhoneDaoInterface;
import jp.mosp.platform.dto.human.PhoneDtoInterface;

/**
 * 電話情報参照クラス
 */
public class PhoneReferenceBean extends PlatformBean implements PhoneReferenceBeanInterface {
	
	/**
	 * 電話区分(個人)。<br>
	 * この場合、保持者IDは個人IDとなる。<br>
	 */
	public static final String	TYPE_PHONE_PERSONAL	= "1";
	
	/**
	 * 電話情報DAOクラス。<br>
	 */
	protected PhoneDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PhoneReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(PhoneDaoInterface.class);
	}
	
	@Override
	public List<PhoneDtoInterface> getPersonalPhoneList(String personalId) throws MospException {
		return dao.findForHolder(personalId, TYPE_PHONE_PERSONAL);
	}
	
	@Override
	public PhoneDtoInterface getLatestPersonalPhone(String personalId, Date targetDate) throws MospException {
		return dao.findForInfo(personalId, TYPE_PHONE_PERSONAL, targetDate);
	}
	
}
