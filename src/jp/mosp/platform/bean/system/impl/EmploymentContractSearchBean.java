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
package jp.mosp.platform.bean.system.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.EmploymentContractSearchBeanInterface;
import jp.mosp.platform.dao.system.EmploymentContractDaoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;

/**
 * 雇用契約マスタ検索クラス。
 */
public class EmploymentContractSearchBean extends PlatformBean implements EmploymentContractSearchBeanInterface {
	
	/**
	 * 雇用契約マスタDAO。
	 */
	private EmploymentContractDaoInterface	employmentContractDao;
	
	/**
	 * 有効日。
	 */
	private Date							activateDate;
	
	/**
	 * 雇用契約コード。
	 */
	private String							employmentCode;
	
	/**
	 * 雇用契約名称。
	 */
	private String							employmentName;
	
	/**
	 * 雇契(略称)。
	 */
	private String							employmentAbbr;
	
	/**
	 * 有効無効フラグ。
	 */
	private String							inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public EmploymentContractSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 雇用契約マスタDAO取得
		employmentContractDao = createDaoInstance(EmploymentContractDaoInterface.class);
	}
	
	@Override
	public List<EmploymentContractDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = employmentContractDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("employmentCode", employmentCode);
		param.put("employmentName", employmentName);
		param.put("employmentAbbr", employmentAbbr);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		return employmentContractDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}
	
	@Override
	public void setEmploymentName(String employmentName) {
		this.employmentName = employmentName;
	}
	
	@Override
	public void setEmploymentAbbr(String employmentAbbr) {
		this.employmentAbbr = employmentAbbr;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
