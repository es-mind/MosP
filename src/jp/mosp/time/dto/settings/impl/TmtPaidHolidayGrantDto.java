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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.PaidHolidayGrantDtoInterface;

/**
 * 有給休暇付与情報。<br>
 */
public class TmtPaidHolidayGrantDto extends BaseDto implements PaidHolidayGrantDtoInterface {
	
	private static final long	serialVersionUID	= 4314912664584935495L;
	
	/**
	 * レコード識別ID。<br>
	 */
	private long				tmtPaidHolidayGrantId;
	
	/**
	 * 個人ID。<br>
	 */
	private String				personalId;
	
	/**
	 * 付与日。<br>
	 */
	private Date				grantDate;
	
	/**
	 * 付与状態。<br>
	 */
	private int					grantStatus;
	
	
	@Override
	public long getTmtPaidHolidayGrantId() {
		return tmtPaidHolidayGrantId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getGrantDate() {
		return getDateClone(grantDate);
	}
	
	@Override
	public int getGrantStatus() {
		return grantStatus;
	}
	
	@Override
	public void setTmtPaidHolidayGrantId(long tmtPaidHolidayGrantId) {
		this.tmtPaidHolidayGrantId = tmtPaidHolidayGrantId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setGrantDate(Date grantDate) {
		this.grantDate = getDateClone(grantDate);
	}
	
	@Override
	public void setGrantStatus(int grantStatus) {
		this.grantStatus = grantStatus;
	}
	
}
