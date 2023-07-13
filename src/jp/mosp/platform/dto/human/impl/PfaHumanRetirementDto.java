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
import jp.mosp.platform.dto.human.RetirementDtoInterface;

/**
 * 人事退職情報DTOクラス。
 */
public class PfaHumanRetirementDto extends BaseDto implements RetirementDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1647841102997261485L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaHumanRetirementId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 退職日。
	 */
	private Date				retirementDate;
	/**
	 * 退職理由。
	 */
	private String				retirementReason;
	/**
	 * 詳細。
	 */
	private String				retirementDetail;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanRetirementDto() {
		// 初期化
	}
	
	@Override
	public long getPfaHumanRetirementId() {
		return pfaHumanRetirementId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getRetirementDate() {
		return getDateClone(retirementDate);
	}
	
	@Override
	public String getRetirementReason() {
		return retirementReason;
	}
	
	@Override
	public String getRetirementDetail() {
		return retirementDetail;
	}
	
	@Override
	public void setPfaHumanRetirementId(long pfaHumanRetirementId) {
		this.pfaHumanRetirementId = pfaHumanRetirementId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setRetirementDate(Date retirementDate) {
		this.retirementDate = getDateClone(retirementDate);
	}
	
	@Override
	public void setRetirementReason(String retirementReason) {
		this.retirementReason = retirementReason;
	}
	
	@Override
	public void setRetirementDetail(String retirementDetail) {
		this.retirementDetail = retirementDetail;
	}
	
}
