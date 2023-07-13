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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceTransactionReferenceBeanInterface;
import jp.mosp.time.dao.settings.AttendanceTransactionDaoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;

/**
 * 勤怠トランザクション参照クラス。
 */
public class AttendanceTransactionReferenceBean extends TimeBean
		implements AttendanceTransactionReferenceBeanInterface {
	
	/**
	 * 勤怠トランザクションDAOクラス。
	 */
	protected AttendanceTransactionDaoInterface dao;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public AttendanceTransactionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(AttendanceTransactionDaoInterface.class);
	}
	
	@Override
	public Map<Date, AttendanceTransactionDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public AttendanceTransactionDtoInterface sum(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		return dao.sum(personalId, firstDate, lastDate);
	}
	
	@Override
	public Set<Long> findForMilliseconds(String personalId, Date firstDate, Date lastDate) throws MospException {
		return dao.findForMilliseconds(personalId, firstDate, lastDate);
	}
	
}
