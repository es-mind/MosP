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
import jp.mosp.time.bean.AllowanceReferenceBeanInterface;
import jp.mosp.time.dao.settings.AllowanceDaoInterface;
import jp.mosp.time.dto.settings.AllowanceDtoInterface;

/**
 * 勤怠データ手当情報参照クラス。
 */
public class AllowanceReferenceBean extends PlatformBean implements AllowanceReferenceBeanInterface {
	
	/**
	 * 勤怠データ手当データマスタDAOクラス。<br>
	 */
	AllowanceDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AllowanceReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(AllowanceDaoInterface.class);
	}
	
	@Override
	public AllowanceDtoInterface findForKey(String personalId, Date workDate, int works, String allowanceCode)
			throws MospException {
		return dao.findForKey(personalId, workDate, works, allowanceCode);
	}
	
	@Override
	public int getTimesAllowance(String personalId, String allowanceCode, Date startDate, Date endDate)
			throws MospException {
		return dao.findForList(personalId, allowanceCode, startDate, endDate).size();
	}
}
