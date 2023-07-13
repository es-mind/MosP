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
import jp.mosp.platform.dto.human.SuspensionDtoInterface;

/**
 * 人事休職情報DTO
 */
public class PfaHumanSuspensionDto extends BaseDto implements SuspensionDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7015243713819775575L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaHumanSuspensionId;
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
	 * 終了予定日。
	 */
	private Date				scheduleEndDate;
	/**
	 * 給与区分。
	 */
	private String				allowanceType;
	/**
	 * 休職理由。
	 */
	private String				suspensionReason;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanSuspensionDto() {
		// 初期化
	}
	
	@Override
	public long getPfaHumanSuspensionId() {
		return pfaHumanSuspensionId;
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
	public Date getScheduleEndDate() {
		return getDateClone(scheduleEndDate);
	}
	
	@Override
	public String getAllowanceType() {
		return allowanceType;
	}
	
	@Override
	public String getSuspensionReason() {
		return suspensionReason;
	}
	
	@Override
	public void setPfaHumanSuspensionId(long pfaHumanSuspensionId) {
		this.pfaHumanSuspensionId = pfaHumanSuspensionId;
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
	public void setScheduleEndDate(Date scheduleEndDate) {
		this.scheduleEndDate = getDateClone(scheduleEndDate);
	}
	
	@Override
	public void setAllowanceType(String allowanceType) {
		this.allowanceType = allowanceType;
	}
	
	@Override
	public void setSuspensionReason(String suspensionReason) {
		this.suspensionReason = suspensionReason;
	}
	
}
