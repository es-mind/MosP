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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;

/**
 * 特別休暇DTO
 */
public class CutoffErrorListDto extends BaseDto implements CutoffErrorListDtoInterface {
	
	private static final long	serialVersionUID	= -1219190730858683447L;
	
	/**
	 * 日付。
	 */
	private Date				date;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 社員コード。
	 */
	private String				employeeCode;
	
	/**
	 * 社員氏名(姓)。
	 */
	private String				lastName;
	
	/**
	 * 社員氏名(名)。
	 */
	private String				firstName;
	
	/**
	 * 勤務地コード。
	 */
	private String				workPlaceCode;
	
	/**
	 * 雇用契約コード。
	 */
	private String				employmentCode;
	
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	
	/**
	 * 職位コード。
	 */
	private String				positionCode;
	
	/**
	 * 区分。
	*/
	private String				type;
	
	/**
	 * 状態。
	 */
	private String				state;
	
	
	@Override
	public Date getDate() {
		return getDateClone(date);
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public String getEmploymentCode() {
		return employmentCode;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public String getState() {
		return state;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public void setDate(Date date) {
		this.date = getDateClone(date);
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
}
