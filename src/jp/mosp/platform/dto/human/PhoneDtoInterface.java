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
package jp.mosp.platform.dto.human;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.base.ActivateDtoInterface;

/**
 * 電話情報DTOインターフェース
 */
public interface PhoneDtoInterface extends BaseDtoInterface, ActivateDtoInterface {
	
	/**
	 * @return レコード識別ID
	 */
	public long getPfaPhoneId();
	
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
	 * @return 電話区分
	 */
	public String getPhoneType();
	
	/**
	 * @return 電話番号1
	 */
	public String getPhoneNumber1();
	
	/**
	 * @return 電話番号2
	 */
	public String getPhoneNumber2();
	
	/**
	 * @return 電話番号3
	 */
	public String getPhoneNumber3();
	
	/**
	 * @return 申請区分
	 */
	public String getRequestType();
	
	/**
	 * @param pfaPhoneId セットする レコード識別ID
	 */
	public void setPfaPhoneId(long pfaPhoneId);
	
	/**
	 * @param phoneType セットする 電話区分
	 */
	public void setPhoneType(String phoneType);
	
	/**
	 * @param phoneNumber1 セットする 電話番号1
	 */
	public void setPhoneNumber1(String phoneNumber1);
	
	/**
	 * @param phoneNumber2 セットする 電話番号2
	 */
	public void setPhoneNumber2(String phoneNumber2);
	
	/**
	 * @param phoneNumber3 セットする 電話番号3
	 */
	public void setPhoneNumber3(String phoneNumber3);
	
	/**
	 * @param requestType セットする 申請区分
	 */
	public void setRequestType(String requestType);
	
}
