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
import jp.mosp.time.bean.TimeSettingSearchBeanInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 勤怠設定検索クラス。
 */
public class TimeSettingSearchBean extends PlatformBean implements TimeSettingSearchBeanInterface {
	
	/**
	 * 勤怠設定DAO
	 */
	protected TimeSettingDaoInterface	timeSettingDao;
	
	/**
	 * 有効日。
	 */
	private Date						activateDate;
	
	/**
	 * 勤怠設定コード
	 */
	private String						workSettingCode;
	
	/**
	 * 勤怠設定名称。
	 */
	private String						workSettingName;
	
	/**
	 * 勤怠設定略称。
	 */
	private String						workSettingAbbr;
	
	/**
	 * 締日コード。
	 */
	private String						cutoffCode;
	
	/**
	 * 有効無効フラグ。
	 */
	private String						inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public TimeSettingSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 勤怠設定マスタDAO取得
		timeSettingDao = createDaoInstance(TimeSettingDaoInterface.class);
	}
	
	@Override
	public List<TimeSettingDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = timeSettingDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("workSettingCode", workSettingCode);
		param.put("workSettingName", workSettingName);
		param.put("workSettingAbbr", workSettingAbbr);
		param.put("cutoffCode", cutoffCode);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		return timeSettingDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setWorkSettingCode(String workSettingCode) {
		this.workSettingCode = workSettingCode;
	}
	
	@Override
	public void setWorkSettingName(String workSettingName) {
		this.workSettingName = workSettingName;
	}
	
	@Override
	public void setWorkSettingAbbr(String workSettingAbbr) {
		this.workSettingAbbr = workSettingAbbr;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
