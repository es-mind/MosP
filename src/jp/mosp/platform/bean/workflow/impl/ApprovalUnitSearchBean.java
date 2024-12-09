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
package jp.mosp.platform.bean.workflow.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.ApprovalUnitSearchBeanInterface;
import jp.mosp.platform.dao.workflow.ApprovalUnitDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * 承認ユニットマスタ検索クラス。
 */
public class ApprovalUnitSearchBean extends PlatformBean implements ApprovalUnitSearchBeanInterface {
	
	/**
	 * 承認ユニットマスタDAO
	 */
	protected ApprovalUnitDaoInterface	approvalUnitDao;
	
	/**
	 * 有効日。
	 */
	private Date						activateDate;
	
	/**
	 * ユニットコード
	 */
	private String						unitCode;
	
	/**
	 * ユニット名称。
	 */
	private String						unitName;
	
	/**
	 * ユニット区分。
	 */
	private String						unitType;
	
	/**
	 * 承認者所属名称。
	 */
	private String						sectionName;
	
	/**
	 * 承認者職位名称。
	 */
	private String						positionName;
	
	/**
	 * 承認者社員コード。
	 */
	private String						employeeCode;
	
	/**
	 * 承認者氏名。
	 */
	private String						approver;
	
	/**
	 * 有効無効フラグ。
	 */
	private String						inactivateFlag;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApprovalUnitSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 勤怠設定マスタDAO取得
		approvalUnitDao = createDaoInstance(ApprovalUnitDaoInterface.class);
	}
	
	@Override
	public List<ApprovalUnitDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = approvalUnitDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("unitCode", unitCode);
		param.put("unitName", unitName);
		param.put("unitType", unitType);
		param.put("sectionName", sectionName);
		param.put("positionName", positionName);
		param.put("employeeCode", employeeCode);
		param.put("approver", approver);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		return approvalUnitDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
		
	}
	
	@Override
	public void setApprover(String approver) {
		this.approver = approver;
		
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
		
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
		
	}
	
	@Override
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
		
	}
	
	@Override
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
		
	}
	
	@Override
	public void setUnitName(String unitName) {
		this.unitName = unitName;
		
	}
	
	@Override
	public void setUnitType(String unitType) {
		this.unitType = unitType;
		
	}
	
	@Override
	public void setPositionName(String positionName) {
		this.positionName = positionName;
		
	}
	
}
