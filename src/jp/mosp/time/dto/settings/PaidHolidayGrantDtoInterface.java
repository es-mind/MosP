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
package jp.mosp.time.dto.settings;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 有給休暇付与情報インターフェース。<br>
 */
public interface PaidHolidayGrantDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID
	 */
	long getTmtPaidHolidayGrantId();
	
	/**
	 * @return 個人ID
	 */
	String getPersonalId();
	
	/**
	 * @return 付与日
	 */
	Date getGrantDate();
	
	/**
	 * @return 付与状態
	 */
	int getGrantStatus();
	
	/**
	 * @param tmtPaidHolidayGrantId レコード識別ID
	 */
	void setTmtPaidHolidayGrantId(long tmtPaidHolidayGrantId);
	
	/**
	 * @param personalId 個人ID
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param grantDate 付与日
	 */
	void setGrantDate(Date grantDate);
	
	/**
	 * @param grantStatus 付与状態
	 */
	void setGrantStatus(int grantStatus);
	
}
