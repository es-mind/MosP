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

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;

/**
 * 人事退職情報参照クラス。<br>
 */
public class RetirementReferenceBean extends BaseBean implements RetirementReferenceBeanInterface {
	
	/**
	 * 人事退職情報DAO。<br>
	 */
	private RetirementDaoInterface retirementDao;
	
	
	/**
	 * {@link BaseBean#BaseBean()}を実行する。<br>
	 */
	public RetirementReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		retirementDao = createDaoInstance(RetirementDaoInterface.class);
	}
	
	@Override
	public RetirementDtoInterface getRetireInfo(String personalId) throws MospException {
		return retirementDao.findForInfo(personalId);
	}
	
	@Override
	public boolean isRetired(String personalId, Date targetDate) throws MospException {
		RetirementDtoInterface dto = retirementDao.findForInfo(personalId, targetDate);
		return HumanUtility.isRetired(dto, targetDate);
	}
	
	@Override
	public Date getRetireDate(String personalId) throws MospException {
		RetirementDtoInterface dto = getRetireInfo(personalId);
		return HumanUtility.getRetireDate(dto);
	}
	
}
