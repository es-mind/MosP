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

import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.EndDateDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;
import jp.mosp.platform.dto.base.WorkflowTypeDtoInterface;
import jp.mosp.platform.dto.workflow.SubApproverListDtoInterface;

/**
 * 代理承認者テーブル検索リストDTO。
 */
public class SubApproverListDto extends PftSubApproverDto
		implements SubApproverListDtoInterface, WorkflowTypeDtoInterface, EndDateDtoInterface, EmployeeCodeDtoInterface,
		EmployeeNameDtoInterface, SectionCodeDtoInterface {
	
	private static final long	serialVersionUID	= 3537352514596296192L;
	
	/**
	 * 代理承認者社員コード。
	 */
	private String				subApproverCode;
	
	/**
	 * 代理承認者名。
	 */
	private String				subApproverName;
	
	/**
	 * 代理承認者所属コード。
	 */
	private String				subApproverSectionCode;
	
	/**
	 * 代理承認者所属名。
	 */
	private String				subApproverSectionName;
	
	
	@Override
	public String getSubApproverCode() {
		return subApproverCode;
	}
	
	@Override
	public void setSubApproverCode(String subApproverCode) {
		this.subApproverCode = subApproverCode;
	}
	
	@Override
	public String getSubApproverName() {
		return subApproverName;
	}
	
	@Override
	public void setSubApproverName(String subApproverName) {
		this.subApproverName = subApproverName;
	}
	
	@Override
	public String getSubApproverSectionCode() {
		return subApproverSectionCode;
	}
	
	@Override
	public void setSubApproverSectionCode(String subApproverSectionCode) {
		this.subApproverSectionCode = subApproverSectionCode;
	}
	
	@Override
	public String getSubApproverSectionName() {
		return subApproverSectionName;
	}
	
	@Override
	public void setSubApproverSectionName(String subApproverSectionName) {
		this.subApproverSectionName = subApproverSectionName;
	}
	
	@Override
	public String getEmployeeCode() {
		return getSubApproverCode();
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		// 処理無し
	}
	
	@Override
	public String getFirstName() {
		return getSubApproverName();
	}
	
	@Override
	public String getLastName() {
		return "";
	}
	
	@Override
	public void setFirstName(String firstName) {
		// 処理無し
	}
	
	@Override
	public void setLastName(String lastName) {
		// 処理無し
	}
	
	@Override
	public String getSectionCode() {
		return getSubApproverSectionCode();
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		// 処理無し
	}
	
}
