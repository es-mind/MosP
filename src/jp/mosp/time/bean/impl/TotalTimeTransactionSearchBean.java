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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.bean.TotalTimeTransactionSearchBeanInterface;
import jp.mosp.time.dao.settings.CutoffDaoInterface;
import jp.mosp.time.dao.settings.TotalTimeDaoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCutoffListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;
import jp.mosp.time.dto.settings.impl.TotalTimeCutoffListDto;

/**
 * 勤怠集計管理検索クラス。
 */
public class TotalTimeTransactionSearchBean extends PlatformBean implements TotalTimeTransactionSearchBeanInterface {
	
	/**
	 * 締日マスタDAO。
	 */
	protected CutoffDaoInterface	cutoffDao;
	
	/**
	 * 勤怠集計管理トランザクションDAO。
	 */
	protected TotalTimeDaoInterface	totalTimeDao;
	
	/**
	 * 集計年。
	 */
	private int						requestYear;
	
	/**
	 * 集計月。
	 */
	private int						requestMonth;
	
	/**
	 * 締日。
	 */
	private String					cutoffDate;
	
	/**
	 * 締日コード。
	 */
	private String					cutoffCode;
	
	/**
	 * 締日名称。
	 */
	private String					cutoffName;
	
	/**
	 * 締状態。
	 */
	private String					cutoffState;
	
	
	/**
	 * コンストラクタ。
	 */
	public TotalTimeTransactionSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 締日管理DAO取得
		cutoffDao = createDaoInstance(CutoffDaoInterface.class);
		totalTimeDao = createDaoInstance(TotalTimeDaoInterface.class);
	}
	
	@Override
	public List<TotalTimeCutoffListDtoInterface> getSearchList() throws MospException {
		// 年月を指定して基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(requestYear, requestMonth, mospParams);
		// Mapに検索条件を設定
		Map<String, Object> param = cutoffDao.getParamsMap();
		param.put(CutoffDaoInterface.SEARCH_TARGET_DATE, targetDate);
		param.put(CutoffDaoInterface.SEARCH_CUTOFF_DATE, cutoffDate);
		param.put(CutoffDaoInterface.SEARCH_CUTOFF_CODE, cutoffCode);
		param.put(CutoffDaoInterface.SEARCH_CUTOFF_NAME, cutoffName);
		param.put(CutoffDaoInterface.SEARCH_CUTOFF_ABBR, "");
		param.put(CutoffDaoInterface.SEARCH_NO_APPROVAL, "");
		param.put(CutoffDaoInterface.SEARCH_SELF_TIGHTENING, "");
		param.put(CutoffDaoInterface.SEARCH_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF);
		// 検索
		List<CutoffDtoInterface> list = cutoffDao.findForSearch(param);
		// 勤怠集計管理リスト準備
		List<TotalTimeCutoffListDtoInterface> totalTimeCutoffList = new ArrayList<TotalTimeCutoffListDtoInterface>();
		// 検索結果から勤怠集計管理リストを作成
		for (CutoffDtoInterface dto : list) {
			// 初期化
			TotalTimeCutoffListDtoInterface totalTimeCutoffListDto = new TotalTimeCutoffListDto();
			totalTimeCutoffListDto.setCutoffCode(dto.getCutoffCode());
			totalTimeCutoffListDto.setCutoffName(dto.getCutoffName());
			totalTimeCutoffListDto.setCutoffAbbr(dto.getCutoffAbbr());
			totalTimeCutoffListDto.setCutoffDate(dto.getCutoffDate());
			totalTimeCutoffListDto.setCutoffState(0);
			TotalTimeDtoInterface totalTimeDto = totalTimeDao.findForKey(requestYear, requestMonth,
					dto.getCutoffCode());
			if (totalTimeDto != null) {
				totalTimeCutoffListDto.setCutoffState(totalTimeDto.getCutoffState());
			}
			if (!cutoffState.isEmpty()) {
				if (!String.valueOf(totalTimeCutoffListDto.getCutoffState()).equals(cutoffState)) {
					continue;
				}
			}
			totalTimeCutoffList.add(totalTimeCutoffListDto);
		}
		return totalTimeCutoffList;
	}
	
	@Override
	public void setRequestYear(int requestYear) {
		this.requestYear = requestYear;
	}
	
	@Override
	public void setRequestMonth(int requestMonth) {
		this.requestMonth = requestMonth;
	}
	
	@Override
	public void setCutoffDate(String cutoffDate) {
		this.cutoffDate = cutoffDate;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public void setCutoffName(String cutoffName) {
		this.cutoffName = cutoffName;
	}
	
	@Override
	public void setCutoffState(String cutoffState) {
		this.cutoffState = cutoffState;
	}
	
}
