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
package jp.mosp.platform.dto.human;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.base.ActivateDtoInterface;

/**
 * 住所情報DTOインターフェース。
 */
public interface AddressDtoInterface extends BaseDtoInterface, ActivateDtoInterface {
	
	/**
	 * レコード識別IDを取得する。
	 * @return レコード識別ID。
	 */
	public long getPfaAddressId();
	
	/**
	 * レコード識別IDを設定する。
	 * @param pfaAddressId レコード識別ID。
	 */
	public void setPfaAddressId(long pfaAddressId);
	
	/**
	 * 保持者IDを取得する。
	 * @return 保持者ID。
	 */
	public String getHolderId();
	
	/**
	 * 保持者IDを設定する。
	 * @param holderId 保持者ID。
	 */
	public void setHolderId(String holderId);
	
	/**
	 * 住所区分を取得する。
	 * @return 住所区分。
	 */
	public String getAddressType();
	
	/**
	 * 住所区分を設定する。
	 * @param addressType 住所区分。
	 */
	public void setAddressType(String addressType);
	
	/**
	 * 郵便番号1を取得する。
	 * @return 郵便番号1。
	 */
	public String getPostalCode1();
	
	/**
	 * 郵便番号1を設定する。
	 * @param postalCode1 郵便番号1。
	 */
	public void setPostalCode1(String postalCode1);
	
	/**
	 * 郵便番号2を取得する。
	 * @return 郵便番号2。
	 */
	public String getPostalCode2();
	
	/**
	 * 郵便番号2を設定する。
	 * @param postalCode2 郵便番号2。
	 */
	public void setPostalCode2(String postalCode2);
	
	/**
	 * 都道府県コードを取得する。<br>
	 * @return 都道府県コード
	 */
	public String getPrefecture();
	
	/**
	 * 都道府県コードを設定する。<br>
	 * @param prefecture 都道府県コード
	 */
	public void setPrefecture(String prefecture);
	
	/**
	 * 市区町村コードを取得する。<br>
	 * @return 市区町村コード
	 */
	public String getCityCode();
	
	/**
	 * 市区町村コードコードを設定する。<br>
	 * @param cityCode セットする市区町村コード
	 */
	public void setCityCode(String cityCode);
	
	/**
	 * 市区町村を取得する。
	 * @return 市区町村。
	 */
	public String getAddress();
	
	/**
	 * 市区町村を設定する。
	 * @param address 市区町村。
	 */
	public void setAddress(String address);
	
	/**
	 * 番地を取得する。
	 * @return 番地。
	 */
	public String getAddressNumber();
	
	/**
	 * 番地を設定する。
	 * @param addressNumber 番地。
	 */
	public void setAddressNumber(String addressNumber);
	
	/**
	 * 建物情報を取得する。
	 * @return 建物情報。
	 */
	public String getBuilding();
	
	/**
	 * 建物情報を設定する。
	 * @param building 建物情報。
	 */
	public void setBuilding(String building);
	
	/**
	 * 申請区分を取得する。
	 * @return 申請区分。
	 */
	public String getRequestType();
	
	/**
	 * 申請区分を設定する。
	 * @param requestType 申請区分。
	 */
	public void setRequestType(String requestType);
	
}
