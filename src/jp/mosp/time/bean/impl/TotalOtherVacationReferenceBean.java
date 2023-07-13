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

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalOtherVacationReferenceBeanInterface;
import jp.mosp.time.dao.settings.TotalOtherVacationDaoInterface;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;

/**
 * 勤怠集計その他休暇データ参照クラス。
 */
public class TotalOtherVacationReferenceBean extends PlatformBean implements TotalOtherVacationReferenceBeanInterface {
	
	/**
	 * 勤怠集計データDAOクラス。<br>
	 */
	protected TotalOtherVacationDaoInterface totalOtherVacationDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalOtherVacationReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		totalOtherVacationDao = createDaoInstance(TotalOtherVacationDaoInterface.class);
	}
	
	@Override
	public TotalOtherVacationDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String holidayCode) throws MospException {
		return totalOtherVacationDao.findForKey(personalId, calculationYear, calculationMonth, holidayCode);
	}
	
	@Override
	public List<TotalOtherVacationDtoInterface> getTotalOtherVacationList(String personalId, int calculationYear,
			int calculationMonth) throws MospException {
		return totalOtherVacationDao.findForList(personalId, calculationYear, calculationMonth);
	}
	
}
