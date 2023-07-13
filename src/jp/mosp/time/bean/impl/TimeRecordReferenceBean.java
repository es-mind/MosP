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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.dao.settings.TimeRecordDaoInterface;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;

/**
 * 打刻情報参照処理。<br>
 */
public class TimeRecordReferenceBean extends BaseBean implements TimeRecordReferenceBeanInterface {
	
	/**
	 * 打刻情報DAO。<br>
	 */
	protected TimeRecordDaoInterface dao;
	
	
	/**
	 * {@link BaseBean#BaseBean()}を実行する。<br>
	 */
	public TimeRecordReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(TimeRecordDaoInterface.class);
	}
	
	@Override
	public TimeRecordDtoInterface findForKey(String personalId, Date workDate, String recordType) throws MospException {
		return dao.findForKey(personalId, workDate, TimeBean.TIMES_WORK_DEFAULT, recordType);
	}
	
	@Override
	public Map<String, Date> getRecordTimes(String personalId, Date workDate) throws MospException {
		// 打刻時刻群を準備
		Map<String, Date> recordTimes = new TreeMap<String, Date>();
		// 打刻情報群を取得
		List<TimeRecordDtoInterface> dtos = dao.findForPersonAndDay(personalId, workDate, TimeBean.TIMES_WORK_DEFAULT);
		// 打刻情報毎に処理
		for (TimeRecordDtoInterface dto : dtos) {
			// 打刻時刻群に設定
			recordTimes.put(dto.getRecordType(), dto.getRecordTime());
		}
		// 打刻時刻群を取得
		return recordTimes;
	}
	
}
