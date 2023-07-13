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

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.WorkPlaceCodeDtoInterface;

/**
 * 勤務地マスタDTOのインターフェース。<br>
 */
public interface WorkPlaceDtoInterface extends PlatformDtoInterface, WorkPlaceCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmWorkPlaceId();
	
	/**
	 * @return 勤務地名称。
	 */
	String getWorkPlaceName();
	
	/**
	 * @return 勤務地カナ。
	 */
	String getWorkPlaceKana();
	
	/**
	 * @return 勤務地略称。
	 */
	String getWorkPlaceAbbr();
	
	/**
	 * @return 勤務地都道府県。
	 */
	String getPrefecture();
	
	/**
	 * @return 勤務地市区町村。
	 */
	String getAddress1();
	
	/**
	 * @return 勤務地番地。
	 */
	String getAddress2();
	
	/**
	 * @return 勤務地建物情報。
	 */
	String getAddress3();
	
	/**
	 * @return 勤務地郵便番号1。
	 */
	String getPostalCode1();
	
	/**
	 * @return 勤務地郵便番号2。
	 */
	String getPostalCode2();
	
	/**
	 * @return 勤務地電話番号1。
	 */
	String getPhoneNumber1();
	
	/**
	 * @return 勤務地電話番号2。
	 */
	String getPhoneNumber2();
	
	/**
	 * @return 勤務地電話番号3。
	 */
	String getPhoneNumber3();
	
	/**
	 * @param pfmWorkPlaceId セットする レコード識別ID。
	 */
	void setPfmWorkPlaceId(long pfmWorkPlaceId);
	
	/**
	 * @param workPlaceName セットする 勤務地名称。
	 */
	void setWorkPlaceName(String workPlaceName);
	
	/**
	 * @param workPlaceKana セットする 勤務地カナ。
	 */
	void setWorkPlaceKana(String workPlaceKana);
	
	/**
	 * @param workPlaceAbbr セットする 勤務地略称。
	 */
	void setWorkPlaceAbbr(String workPlaceAbbr);
	
	/**
	 * @param prefecture セットする 勤務地都道府県。
	 */
	void setPrefecture(String prefecture);
	
	/**
	 * @param address1 セットする 勤務地市区町村。
	 */
	void setAddress1(String address1);
	
	/**
	 * @param address2 セットする 勤務地番地。
	 */
	void setAddress2(String address2);
	
	/**
	 * @param address3 セットする 勤務地建物情報。
	 */
	void setAddress3(String address3);
	
	/**
	 * @param postalCode1 セットする 勤務地郵便番号1。
	 */
	void setPostalCode1(String postalCode1);
	
	/**
	 * @param postalCode2 セットする 勤務地郵便番号2。
	 */
	void setPostalCode2(String postalCode2);
	
	/**
	 * @param phoneNumber1 セットする 勤務地電話番号1。
	 */
	void setPhoneNumber1(String phoneNumber1);
	
	/**
	 * @param phoneNumber2 セットする 勤務地電話番号2。
	 */
	void setPhoneNumber2(String phoneNumber2);
	
	/**
	 * @param phoneNumber3 セットする 勤務地電話番号3。
	 */
	void setPhoneNumber3(String phoneNumber3);
	
}
