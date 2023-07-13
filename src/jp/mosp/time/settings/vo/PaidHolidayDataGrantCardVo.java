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
package jp.mosp.time.settings.vo;

import jp.mosp.time.base.TimeVo;

/**
 * 有給休暇データ情報を格納する。
 */
public class PaidHolidayDataGrantCardVo extends TimeVo {
	
	private static final long	serialVersionUID	= -7914634869323335518L;
	
//	private long				recordId;
//	private String				lblGrantDate;
//	private String				lblExpirationDate;
//	private String				lblGrantDays;
//	private String				txtGrantDays;
	
	private long[]				aryRecordId;
	private String[]			aryLblGrantDate;
	private String[]			aryLblActivateDate;
	private String[]			aryLblExpirationDate;
	private String[]			aryLblGrantDays;
	private String[]			aryTxtGrantDays;
	
	
	/**
	 * @return aryRecordId
	 */
	public long[] getAryRecordId() {
		return getLongArrayClone(aryRecordId);
	}
	
	/**
	 * @param aryRecordId セットする aryRecordId
	 */
	public void setAryRecordId(long[] aryRecordId) {
		this.aryRecordId = getLongArrayClone(aryRecordId);
	}
	
	/**
	 * @return aryLblGrantDate
	 */
	public String[] getAryLblGrantDate() {
		return getStringArrayClone(aryLblGrantDate);
	}
	
	/**
	 * @param aryLblGrantDate セットする aryLblGrantDate
	 */
	public void setAryLblGrantDate(String[] aryLblGrantDate) {
		this.aryLblGrantDate = getStringArrayClone(aryLblGrantDate);
	}
	
	/**
	 * @return aryLblActivateDate
	 */
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @param aryLblActivateDate セットする aryLblActivateDate
	 */
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @return aryLblExpirationDate
	 */
	public String[] getAryLblExpirationDate() {
		return getStringArrayClone(aryLblExpirationDate);
	}
	
	/**
	 * @param aryLblExpirationDate セットする aryLblExpirationDate
	 */
	public void setAryLblExpirationDate(String[] aryLblExpirationDate) {
		this.aryLblExpirationDate = getStringArrayClone(aryLblExpirationDate);
	}
	
	/**
	 * @return aryLblGrantDays
	 */
	public String[] getAryLblGrantDays() {
		return getStringArrayClone(aryLblGrantDays);
	}
	
	/**
	 * @param aryLblGrantDays セットする aryLblGrantDays
	 */
	public void setAryLblGrantDays(String[] aryLblGrantDays) {
		this.aryLblGrantDays = getStringArrayClone(aryLblGrantDays);
	}
	
	/**
	 * @return aryTxtGrantDays
	 */
	public String[] getAryTxtGrantDays() {
		return getStringArrayClone(aryTxtGrantDays);
	}
	
	/**
	 * @param aryTxtGrantDays セットする aryTxtGrantDays
	 */
	public void setAryTxtGrantDays(String[] aryTxtGrantDays) {
		this.aryTxtGrantDays = getStringArrayClone(aryTxtGrantDays);
	}
	
}
