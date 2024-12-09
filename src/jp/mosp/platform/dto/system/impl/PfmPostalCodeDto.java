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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.PostalCodeDtoInterface;

/**
 * 郵便番号マスタDTO。
 */
public class PfmPostalCodeDto extends BaseDto implements PostalCodeDtoInterface {
	
	private static final long	serialVersionUID	= -5079418446795539696L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmPostalCodeId;
	
	/**
	 * 郵便番号。
	 */
	private String				postalCode;
	
	/**
	 * 都道府県コード。
	 */
	private String				prefectureCode;
	
	/**
	 * 市区町村コード。
	 */
	private String				cityCode;
	
	/**
	 * 市区町村名。
	 */
	private String				cityName;
	
	/**
	 * 市区町村名カナ。
	 */
	private String				cityKana;
	
	/**
	 * 町域名。
	 */
	private String				addressName;
	
	/**
	 * 町域名カナ。
	 */
	private String				addressKana;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfmPostalCodeId() {
		return pfmPostalCodeId;
	}
	
	@Override
	public String getPostalCode() {
		return postalCode;
	}
	
	@Override
	public String getPrefectureCode() {
		return prefectureCode;
	}
	
	@Override
	public String getCityCode() {
		return cityCode;
	}
	
	@Override
	public String getCityName() {
		return cityName;
	}
	
	@Override
	public String getCityKana() {
		return cityKana;
	}
	
	@Override
	public String getAddressName() {
		return addressName;
	}
	
	@Override
	public String getAddressKana() {
		return addressKana;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfmPostalCodeId(long pfmPostalCodeId) {
		this.pfmPostalCodeId = pfmPostalCodeId;
	}
	
	@Override
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	@Override
	public void setPrefectureCode(String prefectureCode) {
		this.prefectureCode = prefectureCode;
	}
	
	@Override
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@Override
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Override
	public void setCityKana(String cityKana) {
		this.cityKana = cityKana;
	}
	
	@Override
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
	@Override
	public void setAddressKana(String addressKana) {
		this.addressKana = addressKana;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
