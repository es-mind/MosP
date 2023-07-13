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
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.ScheduleSearchBeanInterface;
import jp.mosp.time.dao.settings.ScheduleDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;

/**
 * カレンダ管理検索クラス。
 */
public class ScheduleSearchBean extends PlatformBean implements ScheduleSearchBeanInterface {
	
	/**
	 * カレンダ管理DAO。
	 */
	protected ScheduleDaoInterface	scheduleDao;
	
	/**
	 * 有効日。
	 */
	private Date					activateDate;
	
	/**
	 * カレンダコード。
	 */
	private String					scheduleCode;
	
	/**
	 * カレンダ名称。
	 */
	private String					scheduleName;
	
	/**
	 * カレンダ略称。
	 */
	private String					scheduleAbbr;
	
	/**
	 * 年度。
	 */
	private String					fiscalYear;
	
	/**
	 * 有効無効フラグ。
	 */
	private String					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public ScheduleSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// カレンダ管理DAO取得
		scheduleDao = createDaoInstance(ScheduleDaoInterface.class);
	}
	
	@Override
	public List<ScheduleDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = scheduleDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("scheduleCode", scheduleCode);
		param.put("scheduleName", scheduleName);
		param.put("scheduleAbbr", scheduleAbbr);
		param.put("fiscalYear", fiscalYear);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		return scheduleDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	
	@Override
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	
	@Override
	public void setScheduleAbbr(String scheduleAbbr) {
		this.scheduleAbbr = scheduleAbbr;
	}
	
	@Override
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
