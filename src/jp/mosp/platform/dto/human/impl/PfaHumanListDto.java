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
package jp.mosp.platform.dto.human.impl;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.human.HumanListDtoInterface;

/**
 * @author a_nakamura
 *
 */
public class PfaHumanListDto extends BaseDto implements HumanListDtoInterface {
	
	private static final long	serialVersionUID	= -2280352696572621790L;
	
	/**
	 * レコード識別ID。
	 */
	private String				pfmHumanId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 社員コード。
	 */
	private String				employeeCode;
	
	/**
	 * 姓。
	 */
	private String				lastName;
	
	/**
	 * 名。
	 */
	private String				firstName;
	
	/**
	 * カナ姓。
	 */
	private String				lastKana;
	
	/**
	 * カナ名。
	 */
	private String				firstKana;
	
	/**
	 * 勤務地コード。
	 */
	private String				workPlaceCode;
	
	/**
	 * 勤務地略称。
	 */
	private String				workPlaceAbbr;
	
	/**
	 * 雇用契約コード。
	 */
	private String				employmentContractCode;
	
	/**
	 * 雇用契約略称。
	 */
	private String				employmentContractAbbr;
	
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	
	/**
	 * 所属名称。
	 */
	private String				sectionName;
	
	/**
	 * 職位コード。
	 */
	private String				positionCode;
	
	/**
	 * 職位略称。
	 */
	private String				positionAbbr;
	
	/**
	 * 休退職区分。
	 */
	private String				retireState;
	
	
	/**
	 * コンストラクター。
	 */
	public PfaHumanListDto() {
	}
	
	@Override
	public String getPfmHumanId() {
		return pfmHumanId;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public String getEmploymentContractCode() {
		return employmentContractCode;
	}
	
	@Override
	public String getEmploymentContractAbbr() {
		return employmentContractAbbr;
	}
	
	@Override
	public String getFirstKana() {
		return firstKana;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public String getLastKana() {
		return lastKana;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public String getWorkPlaceAbbr() {
		return workPlaceAbbr;
	}
	
	@Override
	public void setWorkPlaceAbbr(String workPlaceAbbr) {
		this.workPlaceAbbr = workPlaceAbbr;
	}
	
	@Override
	public String getSectionName() {
		return sectionName;
	}
	
	@Override
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public String getRetireState() {
		return retireState;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public String getPositionAbbr() {
		return positionAbbr;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPfmHumanId(String pfmHumanId) {
		this.pfmHumanId = pfmHumanId;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setEmploymentContractCode(String employmentContractCode) {
		this.employmentContractCode = employmentContractCode;
	}
	
	@Override
	public void setEmploymentContractAbbr(String employmentContractAbbr) {
		this.employmentContractAbbr = employmentContractAbbr;
	}
	
	@Override
	public void setFirstKana(String firstKana) {
		this.firstKana = firstKana;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public void setLastKana(String lastKana) {
		this.lastKana = lastKana;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setRetireState(String retireState) {
		this.retireState = retireState;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setPositionAbbr(String positionAbbr) {
		this.positionAbbr = positionAbbr;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
}
