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
package jp.mosp.platform.dto.system.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;

/**
 * 勤務地マスタDTOクラス。
 */
public class PfmWorkPlaceDto extends BaseDto implements WorkPlaceDtoInterface {
	
	private static final long	serialVersionUID	= 8635124893386364050L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmWorkPlaceId;
	
	/**
	 * 勤務地コード。
	 */
	private String				workPlaceCode;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 勤務地名称。
	 */
	private String				workPlaceName;
	
	/**
	 * 勤務地カナ。
	 */
	private String				workPlaceKana;
	
	/**
	 * 勤務地略称。
	 */
	private String				workPlaceAbbr;
	
	/**
	 * 勤務地都道府県。
	 */
	private String				prefecture;
	
	/**
	 * 勤務地市区町村。
	 */
	private String				address1;
	
	/**
	 * 勤務地番地。
	 */
	private String				address2;
	
	/**
	 * 勤務地建物情報。
	 */
	private String				address3;
	
	/**
	 * 勤務地郵便番号1。
	 */
	private String				postalCode1;
	/**
	 * 勤務地郵便番号2。
	 */
	private String				postalCode2;
	
	/**
	 * 勤務地電話番号1。
	 */
	private String				phoneNumber1;
	
	/**
	 * 勤務地電話番号2。
	 */
	private String				phoneNumber2;
	
	/**
	 * 勤務地電話番号3。
	 */
	private String				phoneNumber3;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmWorkPlaceDto() {
		// 処理無し
	}
	
	@Override
	public long getPfmWorkPlaceId() {
		return pfmWorkPlaceId;
	}
	
	@Override
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getWorkPlaceName() {
		return workPlaceName;
	}
	
	@Override
	public String getWorkPlaceKana() {
		return workPlaceKana;
	}
	
	@Override
	public String getWorkPlaceAbbr() {
		return workPlaceAbbr;
	}
	
	@Override
	public String getPrefecture() {
		return prefecture;
	}
	
	@Override
	public String getAddress1() {
		return address1;
	}
	
	@Override
	public String getAddress2() {
		return address2;
	}
	
	@Override
	public String getAddress3() {
		return address3;
	}
	
	@Override
	public String getPostalCode1() {
		return postalCode1;
	}
	
	@Override
	public String getPostalCode2() {
		return postalCode2;
	}
	
	@Override
	public String getPhoneNumber1() {
		return phoneNumber1;
	}
	
	@Override
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	
	@Override
	public String getPhoneNumber3() {
		return phoneNumber3;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfmWorkPlaceId(long pfmWorkPlaceId) {
		this.pfmWorkPlaceId = pfmWorkPlaceId;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}
	
	@Override
	public void setWorkPlaceKana(String workPlaceKana) {
		this.workPlaceKana = workPlaceKana;
	}
	
	@Override
	public void setWorkPlaceAbbr(String workPlaceAbbr) {
		this.workPlaceAbbr = workPlaceAbbr;
	}
	
	@Override
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}
	
	@Override
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@Override
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@Override
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	
	@Override
	public void setPostalCode1(String postalCode1) {
		this.postalCode1 = postalCode1;
	}
	
	@Override
	public void setPostalCode2(String postalCode2) {
		this.postalCode2 = postalCode2;
	}
	
	@Override
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	
	@Override
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	
	@Override
	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
