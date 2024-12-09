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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayPointDateReferenceBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayPointDateDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;

/**
 * 有給休暇自動付与(基準日)参照クラス。
 */
public class PaidHolidayPointDateReferenceBean extends PlatformBean
		implements PaidHolidayPointDateReferenceBeanInterface {
	
	/**
	 * 有給休暇自動付与(基準日)DAO。
	 */
	protected PaidHolidayPointDateDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayPointDateReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(PaidHolidayPointDateDaoInterface.class);
	}
	
	@Override
	public PaidHolidayPointDateDtoInterface findForKey(String paidHolidayCode, Date activateDate, int timesPointDate)
			throws MospException {
		return dao.findForKey(paidHolidayCode, activateDate, timesPointDate);
	}
	
	@Override
	public List<PaidHolidayPointDateDtoInterface> findForList(String paidHolidayCode, Date activateDate)
			throws MospException {
		return dao.findForList(paidHolidayCode, activateDate);
	}
	
}
