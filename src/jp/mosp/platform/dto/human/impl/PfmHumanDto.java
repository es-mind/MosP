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

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.human.HumanDtoInterface;

/**
 * 人事マスタDTO。<br>
 */
public class PfmHumanDto extends BaseDto implements HumanDtoInterface {
	
	private static final long	serialVersionUID	= -8304374504192172252L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmHumanId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
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
	 * 雇用契約コード。
	 */
	private String				employmentContractCode;
	
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	
	/**
	 * 職位コード。
	 */
	private String				positionCode;
	
	/**
	 * 勤務地コード。
	 */
	private String				workPlaceCode;
	
	/**
	 * メールアドレス。
	 */
	private String				mail;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmHumanDto() {
		// 初期化
	}
	
	@Override
	public long getPfmHumanId() {
		return pfmHumanId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
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
	public String getLastKana() {
		return lastKana;
	}
	
	@Override
	public String getFirstKana() {
		return firstKana;
	}
	
	@Override
	public String getEmploymentContractCode() {
		return employmentContractCode;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public String getMail() {
		return mail;
	}
	
	@Override
	public void setPfmHumanId(long pfmHumanId) {
		this.pfmHumanId = pfmHumanId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
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
	public void setLastKana(String lastKana) {
		this.lastKana = lastKana;
	}
	
	@Override
	public void setFirstKana(String firstKana) {
		this.firstKana = firstKana;
	}
	
	@Override
	public void setEmploymentContractCode(String employmentContractCode) {
		this.employmentContractCode = employmentContractCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setMail(String mail) {
		this.mail = mail;
	}
	
}
