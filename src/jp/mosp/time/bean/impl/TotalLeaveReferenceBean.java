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
import jp.mosp.time.bean.TotalLeaveReferenceBeanInterface;
import jp.mosp.time.dao.settings.TotalLeaveDaoInterface;
import jp.mosp.time.dto.settings.TotalLeaveDtoInterface;

/**
 * 勤怠集計特別休暇データ参照クラス。
 */
public class TotalLeaveReferenceBean extends PlatformBean implements TotalLeaveReferenceBeanInterface {
	
	/**
	 * 勤怠集計データDAOクラス。<br>
	 */
	protected TotalLeaveDaoInterface totalLeaveDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalLeaveReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		totalLeaveDao = createDaoInstance(TotalLeaveDaoInterface.class);
	}
	
	@Override
	public TotalLeaveDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String holidayCode) throws MospException {
		return totalLeaveDao.findForKey(personalId, calculationYear, calculationMonth, holidayCode);
	}
	
	@Override
	public List<TotalLeaveDtoInterface> getTotalLeaveList(String personalId, int calculationYear, int calculationMonth)
			throws MospException {
		return totalLeaveDao.findForList(personalId, calculationYear, calculationMonth);
	}
	
}
