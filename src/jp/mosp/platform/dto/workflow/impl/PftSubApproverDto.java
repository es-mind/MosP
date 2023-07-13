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
package jp.mosp.platform.dto.workflow.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;

/**
 * 代理承認者テーブルDTOクラス。
 */
public class PftSubApproverDto extends BaseDto implements SubApproverDtoInterface {
	
	private static final long	serialVersionUID	= -6851225544795274258L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pftSubApproverId;
	
	/**
	 * 代理承認者登録No。
	 */
	private String				subApproverNo;
	
	/**
	 * 代理元個人ID。
	 */
	private String				personalId;
	
	/**
	 * 代理開始日。
	 */
	private Date				startDate;
	
	/**
	 * 代理終了日。
	 */
	private Date				endDate;
	
	/**
	 * 代理承認者個人ID。
	 */
	private String				subApproverId;
	
	/**
	 * フロー区分。
	 */
	private int					workflowType;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPftSubApproverId() {
		return pftSubApproverId;
	}
	
	@Override
	public void setPftSubApproverId(long pftSubApproverId) {
		this.pftSubApproverId = pftSubApproverId;
	}
	
	@Override
	public String getSubApproverNo() {
		return subApproverNo;
	}
	
	@Override
	public void setSubApproverNo(String subApproverNo) {
		this.subApproverNo = subApproverNo;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public Date getStartDate() {
		return getDateClone(startDate);
	}
	
	@Override
	public void setStartDate(Date startDate) {
		this.startDate = getDateClone(startDate);
	}
	
	@Override
	public Date getEndDate() {
		return getDateClone(endDate);
	}
	
	@Override
	public void setEndDate(Date endDate) {
		this.endDate = getDateClone(endDate);
	}
	
	@Override
	public String getSubApproverId() {
		return subApproverId;
	}
	
	@Override
	public void setSubApproverId(String subApproverId) {
		this.subApproverId = subApproverId;
	}
	
	@Override
	public int getWorkflowType() {
		return workflowType;
	}
	
	@Override
	public void setWorkflowType(int workflowType) {
		this.workflowType = workflowType;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(startDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		startDate = getDateClone(activateDate);
	}
	
}
