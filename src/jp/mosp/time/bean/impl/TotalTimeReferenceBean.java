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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.bean.TotalTimeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計情報参照処理。<br>
 */
public class TotalTimeReferenceBean extends PlatformBean implements TotalTimeReferenceBeanInterface {
	
	/**
	 * 勤怠集計データDAO。
	 */
	protected TotalTimeDataDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(TotalTimeDataDaoInterface.class);
	}
	
	@Override
	public TotalTimeDataDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException {
		TotalTimeDataDtoInterface dto = dao.findForKey(personalId, calculationYear, calculationMonth);
		// 追加業務ロジック処理を行う
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_TOTALTIMEREFERENCEBEAN_FINDFORKEY, dto);
		return dto;
	}
	
	@Override
	public Map<Date, TotalTimeDataDtoInterface> findTermMap(String personalId, Date firstMonth, Date lastMonth)
			throws MospException {
		// 勤怠集計情報群(キー：年月を表す日付(年月の初日))(年月昇順)を準備
		Map<Date, TotalTimeDataDtoInterface> map = new TreeMap<Date, TotalTimeDataDtoInterface>();
		int startYear = DateUtility.getYear(firstMonth);
		int startMonth = DateUtility.getMonth(firstMonth);
		int endYear = DateUtility.getYear(lastMonth);
		int endMonth = DateUtility.getMonth(lastMonth);
		// 勤怠集計情毎に処理
		for (TotalTimeDataDtoInterface dto : dao.findForTerm(personalId, startYear, startMonth, endYear, endMonth)) {
			// 勤怠集計情報群(キー：年月を表す日付(年月の初日))に勤怠集計情報を追加
			map.put(MonthUtility.getYearMonthDate(dto.getCalculationYear(), dto.getCalculationMonth()), dto);
		}
		// 勤怠集計情報群(キー：年月を表す日付(年月の初日)(年月昇順))を取得
		return map;
	}
	
	@Override
	public Map<Integer, TotalTimeDataDtoInterface> findFiscalMap(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// 値取得
		int startYear = DateUtility.getYear(firstDate);
		int startMonth = DateUtility.getMonth(firstDate);
		int endYear = DateUtility.getYear(lastDate);
		int endMonth = DateUtility.getMonth(lastDate);
		return findFiscalMap(personalId, startYear, startMonth, endYear, endMonth);
	}
	
	@Override
	public Map<Integer, TotalTimeDataDtoInterface> findFiscalMap(String personalId, int startYear, int startMonth,
			int endYear, int endMonth) throws MospException {
		// 勤怠集計情報群(キー：月)を準備
		Map<Integer, TotalTimeDataDtoInterface> map = new LinkedHashMap<Integer, TotalTimeDataDtoInterface>();
		// 勤怠集計情毎に処理
		for (TotalTimeDataDtoInterface dto : dao.findForTerm(personalId, startYear, startMonth, endYear, endMonth)) {
			// 勤怠集計情報群(キー：月)に勤怠集計情報を追加
			map.put(dto.getCalculationMonth(), dto);
		}
		// 追加業務ロジック処理を行う
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_TOTALTIMEREFERENCEBEAN_FINDFISCALMAP, map);
		// 勤怠集計情報群(キー：月)を取得
		return map;
	}
	
	@Override
	public int getMinYear() throws MospException {
		// 勤怠集計情報が存在する最小の年を取得
		return dao.getMinYear();
	}
	
}
