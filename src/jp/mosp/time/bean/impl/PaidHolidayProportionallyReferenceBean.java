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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayProportionallyReferenceBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayProportionallyDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayProportionallyDtoInterface;

/**
 * 有給休暇比例付与参照クラス。
 */
public class PaidHolidayProportionallyReferenceBean extends PlatformBean
		implements PaidHolidayProportionallyReferenceBeanInterface {
	
	/**
	 * 有給休暇比例付与DAOクラス。
	 */
	protected PaidHolidayProportionallyDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayProportionallyReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(PaidHolidayProportionallyDaoInterface.class);
	}
	
	@Override
	public PaidHolidayProportionallyDtoInterface findForInfo(String paidHolidayCode, Date targetDate,
			int prescribedWeeklyWorkingDays, int continuousServiceTermsCountingFromTheEmploymentDay)
			throws MospException {
		return dao.findForInfo(paidHolidayCode, targetDate, prescribedWeeklyWorkingDays,
				continuousServiceTermsCountingFromTheEmploymentDay);
	}
	
	@Override
	public List<PaidHolidayProportionallyDtoInterface> findForList(String paidHolidayCode, Date activateDate)
			throws MospException {
		return dao.findForList(paidHolidayCode, activateDate);
	}
	
}
