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
package jp.mosp.platform.dto.system;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 郵便番号マスタDTOインターフェース。<br>
 */
public interface PostalCodeDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmPostalCodeId();
	
	/**
	 * @return 郵便番号。
	 */
	String getPostalCode();
	
	/**
	 * @return 都道府県コード。
	 */
	String getPrefectureCode();
	
	/**
	 * @return 市区町村コード。
	 */
	String getCityCode();
	
	/**
	 * @return 市区町村名。
	 */
	String getCityName();
	
	/**
	 * @return 市区町村名カナ。
	 */
	String getCityKana();
	
	/**
	 * @return 町域名。
	 */
	String getAddressName();
	
	/**
	 * @return 町域名カナ。
	 */
	String getAddressKana();
	
	/**
	 * 無効フラグを取得する。
	 * @return 無効フラグ
	 */
	int getInactivateFlag();
	
	/**
	 * @param pfmPostalCodeId セットする レコード識別ID。
	 */
	void setPfmPostalCodeId(long pfmPostalCodeId);
	
	/**
	 * @param postalCode セットする 郵便番号。
	 */
	void setPostalCode(String postalCode);
	
	/**
	 * @param prefectureCode セットする 都道府県コード。
	 */
	void setPrefectureCode(String prefectureCode);
	
	/**
	 * @param cityCode セットする 市区町村コード。
	 */
	void setCityCode(String cityCode);
	
	/**
	 * @param cityName セットする 市区町村名。
	 */
	void setCityName(String cityName);
	
	/**
	 * @param cityKana セットする 市区町村名カナ。
	 */
	void setCityKana(String cityKana);
	
	/**
	 * @param addressName セットする 町域名。
	 */
	void setAddressName(String addressName);
	
	/**
	 * @param addressKana セットする 町域名カナ。
	 */
	void setAddressKana(String addressKana);
	
	/**
	 * 無効フラグを設定する。
	 * @param inactivateFlag セットする無効フラグ
	 */
	void setInactivateFlag(int inactivateFlag);
	
}
