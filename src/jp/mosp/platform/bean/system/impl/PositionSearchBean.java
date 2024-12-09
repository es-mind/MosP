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
import jp.mosp.platform.bean.system.PositionSearchBeanInterface;
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;

/**
 * 職位マスタ検索クラス。
 */
public class PositionSearchBean extends PlatformBean implements PositionSearchBeanInterface {
	
	private PositionDaoInterface	positionDao;
	
	/**
	 * 有効日。
	 */
	private Date					activateDate;
	
	/**
	 * 職位コード。
	 */
	private String					positionCode;
	
	/**
	 * 職位名称。
	 */
	private String					positionName;
	
	/**
	 * 職位名称(略称)。
	 */
	private String					positionAbbr;
	
	/**
	 * 等級。
	 */
	private String					positionGrade;
	
	/**
	 * 号数。
	 */
	private String					positionLevel;
	
	/**
	 * 有効無効フラグ。
	 */
	private String					inactivateFlag;
	
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public String getPositionGrade() {
		return positionGrade;
	}
	
	@Override
	public String getPositionLevel() {
		return positionLevel;
	}
	
	@Override
	public String getPositionName() {
		return positionName;
	}
	
	@Override
	public String getPositionAbbr() {
		return positionAbbr;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setPositionGrade(String positionGrade) {
		this.positionGrade = positionGrade;
	}
	
	@Override
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	
	@Override
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	@Override
	public void setPositionAbbr(String positionAbbr) {
		this.positionAbbr = positionAbbr;
	}
	
	/**
	 * コンストラクタ。
	 */
	public PositionSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		positionDao = createDaoInstance(PositionDaoInterface.class);
	}
	
	@Override
	public List<PositionDtoInterface> getSearchList(PositionSearchBeanInterface searchParams) throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = positionDao.getParamsMap();
		param.put("activateDate", searchParams.getActivateDate());
		param.put("positionCode", searchParams.getPositionCode());
		param.put("positionName", searchParams.getPositionName());
		param.put("positionAbbr", searchParams.getPositionAbbr());
		param.put("positionGrade", searchParams.getPositionGrade());
		param.put("positionLevel", searchParams.getPositionLevel());
		param.put("inactivateFlag", searchParams.getInactivateFlag());
		List<PositionDtoInterface> list = positionDao.findForSearch(param);
		return list;
	}
	
}
