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
import jp.mosp.time.bean.HolidaySearchBeanInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;

/**
 * 休暇種別管理検索クラス。
 */
public class HolidaySearchBean extends PlatformBean implements HolidaySearchBeanInterface {
	
	/**
	 * 休暇種別管理DAO。
	 */
	protected HolidayDaoInterface	holidayDao;
	
	/**
	 * 有効日。
	 */
	private Date					activateDate;
	
	/**
	 * 休暇コード。
	 */
	private String					holidayCode;
	
	/**
	 * 休暇区分。
	 */
	private String					holidayType;
	
	/**
	 * 無効フラグ。
	 */
	private String					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public HolidaySearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 個別休暇管理DAO取得
		holidayDao = createDaoInstance(HolidayDaoInterface.class);
	}
	
	@Override
	public List<HolidayDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = holidayDao.getParamsMap();
		param.put("holidayCode", holidayCode);
		param.put("activateDate", activateDate);
		param.put("holidayType", holidayType);
		param.put("inactivateFlag", inactivateFlag);
		return holidayDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setHolidayCode(String holidayCode) {
		this.holidayCode = holidayCode;
	}
	
	@Override
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
