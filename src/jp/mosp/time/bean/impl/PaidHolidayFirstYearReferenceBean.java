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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayFirstYearDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;

/**
 * 有給休暇初年度付与参照クラス。
 */
public class PaidHolidayFirstYearReferenceBean extends PlatformBean
		implements PaidHolidayFirstYearReferenceBeanInterface {
	
	/**
	 * 有給休暇初年度付与DAO。
	 */
	protected PaidHolidayFirstYearDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayFirstYearReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(PaidHolidayFirstYearDaoInterface.class);
	}
	
	@Override
	public PaidHolidayFirstYearDtoInterface findForKey(String paidHolidayCode, Date activateDate, int entranceMonth)
			throws MospException {
		return dao.findForKey(paidHolidayCode, activateDate, entranceMonth);
	}
	
}
