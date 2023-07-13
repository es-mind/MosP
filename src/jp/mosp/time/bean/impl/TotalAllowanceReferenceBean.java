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
import jp.mosp.time.bean.TotalAllowanceReferenceBeanInterface;
import jp.mosp.time.dao.settings.TotalAllowanceDaoInterface;
import jp.mosp.time.dto.settings.TotalAllowanceDtoInterface;

/**
 * 勤怠集計手当データ参照クラス。
 */
public class TotalAllowanceReferenceBean extends PlatformBean implements TotalAllowanceReferenceBeanInterface {
	
	/**
	 * 勤怠集計手当データDAOクラス。<br>
	 */
	protected TotalAllowanceDaoInterface totalAllowanceDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalAllowanceReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		totalAllowanceDao = createDaoInstance(TotalAllowanceDaoInterface.class);
	}
	
	@Override
	public TotalAllowanceDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String allowanceCode) throws MospException {
		return totalAllowanceDao.findForKey(personalId, calculationYear, calculationMonth, allowanceCode);
	}
	
	@Override
	public int getTimesAllowance(String[] personalIdArray, int calculationYear, int calculationMonth,
			String allowanceCode) throws MospException {
		int times = 0;
		for (String personalId : personalIdArray) {
			TotalAllowanceDtoInterface dto = totalAllowanceDao.findForKey(personalId, calculationYear, calculationMonth,
					allowanceCode);
			if (dto != null) {
				times += dto.getTimes();
			}
		}
		return times;
	}
}
