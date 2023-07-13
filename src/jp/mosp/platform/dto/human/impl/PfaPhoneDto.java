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
import jp.mosp.platform.dto.human.PhoneDtoInterface;

/**
 * 電話情報DTOクラス。
 */
public class PfaPhoneDto extends BaseDto implements PhoneDtoInterface {
	
	private static final long	serialVersionUID	= 4316989929688780885L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaPhoneId;
	
	/**
	 * 保持者ID。
	 */
	private String				holderId;
	
	/**
	 * 電話区分。
	 */
	private String				phoneType;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 電話番号1。
	 */
	private String				phoneNumber1;
	
	/**
	 * 電話番号2。
	 */
	private String				phoneNumber2;
	
	/**
	 * 電話番号3。
	 */
	private String				phoneNumber3;
	
	/**
	 * 申請区分。
	 */
	private String				requestType;
	
	
	/**
	 * @return pfaPhoneId
	 */
	@Override
	public long getPfaPhoneId() {
		return pfaPhoneId;
	}
	
	@Override
	public String getHolderId() {
		return holderId;
	}
	
	@Override
	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}
	
	/**
	 * @return activateDate
	 */
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	/**
	 * @return phoneType
	 */
	@Override
	public String getPhoneType() {
		return phoneType;
	}
	
	/**
	 * @return phoneNumber1
	 */
	@Override
	public String getPhoneNumber1() {
		return phoneNumber1;
	}
	
	/**
	 * @return phoneNumber2
	 */
	@Override
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	
	/**
	 * @return phoneNumber3
	 */
	@Override
	public String getPhoneNumber3() {
		return phoneNumber3;
	}
	
	/**
	 * @return requestType
	 */
	@Override
	public String getRequestType() {
		return requestType;
	}
	
	/**
	 * @param pfaPhoneId セットする pfaPhoneId
	 */
	@Override
	public void setPfaPhoneId(long pfaPhoneId) {
		this.pfaPhoneId = pfaPhoneId;
	}
	
	/**
	 * @param activateDate セットする activateDate
	 */
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	/**
	 * @param phoneType セットする phoneType
	 */
	@Override
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	/**
	 * @param phoneNumber1 セットする phoneNumber1
	 */
	@Override
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	
	/**
	 * @param phoneNumber2 セットする phoneNumber2
	 */
	@Override
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	
	/**
	 * @param phoneNumber3 セットする phoneNumber3
	 */
	@Override
	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}
	
	/**
	 * @param requestType セットする requestType
	 */
	@Override
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
}
