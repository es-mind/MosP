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
package jp.mosp.platform.dto.human.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;

/**
 * 人事兼務情報DTOクラス。
 */
public class PfaHumanConcurrentDto extends BaseDto implements ConcurrentDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8792691036167297361L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaHumanConcurrentId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 開始日。
	 */
	private Date				startDate;
	/**
	 * 終了日。
	 */
	private Date				endDate;
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	/**
	 * 職位コード。
	 */
	private String				positionCode;
	/**
	 * 備考。
	 */
	private String				concurrentRemark;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanConcurrentDto() {
		// 処理なし
	}
	
	@Override
	public long getPfaHumanConcurrentId() {
		return pfaHumanConcurrentId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getStartDate() {
		return getDateClone(startDate);
	}
	
	@Override
	public Date getEndDate() {
		return getDateClone(endDate);
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
	public String getConcurrentRemark() {
		return concurrentRemark;
	}
	
	@Override
	public void setPfaHumanConcurrentId(long pfaHumanConcurrentId) {
		this.pfaHumanConcurrentId = pfaHumanConcurrentId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setStartDate(Date startDate) {
		this.startDate = getDateClone(startDate);
	}
	
	@Override
	public void setEndDate(Date endDate) {
		this.endDate = getDateClone(endDate);
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
	public void setConcurrentRemark(String concurrentRemark) {
		this.concurrentRemark = concurrentRemark;
	}
	
}
