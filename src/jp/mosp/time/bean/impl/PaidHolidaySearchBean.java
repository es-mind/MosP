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

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidaySearchBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;

/**
 * 有給休暇設定検索クラス。
 */
public class PaidHolidaySearchBean extends PlatformBean implements PaidHolidaySearchBeanInterface {
	
	/**
	 * 有給休暇設定DAO。
	 */
	protected PaidHolidayDaoInterface	paidHolidayDao;
	
	/**
	 * 有効日。
	 */
	private Date						activateDate;
	
	/**
	 * 有休コード。
	 */
	private String						paidHolidayCode;
	
	/**
	 * 有休名称。
	 */
	private String						paidHolidayName;
	
	/**
	 * 有休略称。
	 */
	private String						paidHolidayAbbr;
	
	/**
	 * 付与区分。
	 */
	private String						paidHolidayType;
	
	/**
	 * 有効無効フラグ。
	 */
	private String						inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PaidHolidaySearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 有給休暇設定DAO取得
		paidHolidayDao = createDaoInstance(PaidHolidayDaoInterface.class);
	}
	
	@Override
	public List<PaidHolidayDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = paidHolidayDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("paidHolidayCode", paidHolidayCode);
		param.put("paidHolidayName", paidHolidayName);
		param.put("paidHolidayAbbr", paidHolidayAbbr);
		param.put("paidHolidayType", paidHolidayType);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		return paidHolidayDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setPaidHolidayName(String paidHolidayName) {
		this.paidHolidayName = paidHolidayName;
	}
	
	@Override
	public void setPaidHolidayAbbr(String paidHolidayAbbr) {
		this.paidHolidayAbbr = paidHolidayAbbr;
	}
	
	@Override
	public void setPaidHolidayType(String paidHolidayType) {
		this.paidHolidayType = paidHolidayType;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
