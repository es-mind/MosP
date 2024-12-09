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
/**
 * 
 */
package jp.mosp.time.dto.settings;

import java.util.Date;

import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;

/**
 * 申請情報確認一覧DTOインターフェース
 */
public interface ManagementRequestListDtoInterface
		extends EmployeeCodeDtoInterface, EmployeeNameDtoInterface, SectionCodeDtoInterface, RequestListDtoInterface {
	
	/**
	 * @return 申請カテゴリ。
	 */
	String getRequestType();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 申請日付。
	 */
	Date getRequestDate();
	
	/**
	 * @return 申請情報詳細。
	 */
	String getRequestInfo();
	
	/**
	 * @param requestType セットする 申請カテゴリ。
	 */
	void setRequestType(String requestType);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param requestDate セットする 申請日付。
	 */
	void setRequestDate(Date requestDate);
	
	/**
	 * @param requestInfo セットする 申請情報詳細。
	 */
	void setRequestInfo(String requestInfo);
	
}
