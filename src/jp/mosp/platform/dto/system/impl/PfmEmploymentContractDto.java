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
package jp.mosp.platform.dto.system.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;

/**
 * 雇用契約マスタDTO。<br>
 */
public class PfmEmploymentContractDto extends BaseDto implements EmploymentContractDtoInterface {
	
	private static final long	serialVersionUID	= -941729922824067774L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmEmploymentContractId;
	
	/**
	 * 雇用契約コード。
	 */
	private String				employmentContractCode;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 雇用契約名称。
	 */
	private String				employmentContractName;
	
	/**
	 * 雇用契約略称。
	 */
	private String				employmentContractAbbr;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmEmploymentContractDto() {
		// 処理無し
	}
	
	@Override
	public long getPfmEmploymentContractId() {
		return pfmEmploymentContractId;
	}
	
	@Override
	public String getEmploymentContractCode() {
		return employmentContractCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getEmploymentContractName() {
		return employmentContractName;
	}
	
	@Override
	public String getEmploymentContractAbbr() {
		return employmentContractAbbr;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfmEmploymentContractId(long pfmEmploymentContractId) {
		this.pfmEmploymentContractId = pfmEmploymentContractId;
	}
	
	@Override
	public void setEmploymentContractCode(String employmentContractCode) {
		this.employmentContractCode = employmentContractCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setEmploymentContractName(String employmentContractName) {
		this.employmentContractName = employmentContractName;
	}
	
	@Override
	public void setEmploymentContractAbbr(String employmentContractAbbr) {
		this.employmentContractAbbr = employmentContractAbbr;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
