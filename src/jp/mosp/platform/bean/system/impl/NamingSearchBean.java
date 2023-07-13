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
package jp.mosp.platform.bean.system.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.NamingSearchBeanInterface;
import jp.mosp.platform.dao.system.NamingDaoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;

/**
 * 名称区分マスタ検索クラス。
 */
public class NamingSearchBean extends PlatformBean implements NamingSearchBeanInterface {
	
	/**
	 * 名称区分マスタDAO。
	 */
	private NamingDaoInterface	namingDao;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 名称区分。
	 */
	private String				namingType;
	
	/**
	 * 名称項目コード。
	 */
	private String				namingItemCode;
	
	/**
	 * 名称項目名称。
	 */
	private String				namingItemName;
	
	/**
	 * 名称項目略称。
	 */
	private String				namingItemAbbr;
	
	/**
	 * 有効無効フラグ。
	 */
	private String				inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public NamingSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 雇用契約マスタDAO取得
		namingDao = createDaoInstance(NamingDaoInterface.class);
	}
	
	@Override
	public List<NamingDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = namingDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("namingType", namingType);
		param.put("namingItemCode", namingItemCode);
		param.put("namingItemName", namingItemName);
		param.put("namingItemAbbr", namingItemAbbr);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		return namingDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setNamingType(String namingType) {
		this.namingType = namingType;
	}
	
	@Override
	public void setNamingItemCode(String namingItemCode) {
		this.namingItemCode = namingItemCode;
	}
	
	@Override
	public void setNamingItemName(String namingItemName) {
		this.namingItemName = namingItemName;
	}
	
	@Override
	public void setNamingItemAbbr(String namingItemAbbr) {
		this.namingItemAbbr = namingItemAbbr;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
}
