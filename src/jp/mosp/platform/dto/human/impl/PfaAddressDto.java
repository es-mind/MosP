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
import jp.mosp.platform.dto.human.AddressDtoInterface;

/**
 * 住所情報DTO。
 */
public class PfaAddressDto extends BaseDto implements AddressDtoInterface {
	
	private static final long	serialVersionUID	= 5828483908917833701L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaAddressId;
	
	/**
	 * 保持者ID。
	 */
	private String				holderId;
	
	/**
	 * 住所区分。
	 */
	private String				addressType;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 郵便番号1。
	 */
	private String				postalCode1;
	
	/**
	 * 郵便番号2。
	 */
	private String				postalCode2;
	
	/**
	 * 都道府県コード。<br>
	 */
	private String				prefecture;
	
	/**
	 * 市区町村コード。<br>
	 */
	private String				cityCode;
	
	/**
	 * 市区町村。
	 */
	private String				address;
	
	/**
	 * 番地。
	 */
	private String				addressNumber;
	
	/**
	 * 建物情報。
	 */
	private String				building;
	
	/**
	 * 申請区分。
	 */
	private String				requestType;
	
	
	@Override
	public long getPfaAddressId() {
		return pfaAddressId;
	}
	
	@Override
	public void setPfaAddressId(long pfaAddressId) {
		this.pfaAddressId = pfaAddressId;
	}
	
	@Override
	public String getHolderId() {
		return holderId;
	}
	
	@Override
	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public String getAddressType() {
		return addressType;
	}
	
	@Override
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	
	@Override
	public String getPostalCode1() {
		return postalCode1;
	}
	
	@Override
	public void setPostalCode1(String postalCode1) {
		this.postalCode1 = postalCode1;
	}
	
	@Override
	public String getPostalCode2() {
		return postalCode2;
	}
	
	@Override
	public void setPostalCode2(String postalCode2) {
		this.postalCode2 = postalCode2;
	}
	
	@Override
	public String getPrefecture() {
		return prefecture;
	}
	
	@Override
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}
	
	@Override
	public String getCityCode() {
		return cityCode;
	}
	
	@Override
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@Override
	public String getAddress() {
		return address;
	}
	
	@Override
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String getAddressNumber() {
		return addressNumber;
	}
	
	@Override
	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}
	
	@Override
	public String getBuilding() {
		return building;
	}
	
	@Override
	public void setBuilding(String building) {
		this.building = building;
	}
	
	@Override
	public String getRequestType() {
		return requestType;
	}
	
	@Override
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
}
