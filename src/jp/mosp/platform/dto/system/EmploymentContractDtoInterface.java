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
package jp.mosp.platform.dto.system;

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.EmploymentContractCodeDtoInterface;

/**
 * 雇用契約マスタDTOインターフェース。<br>
 */
public interface EmploymentContractDtoInterface extends PlatformDtoInterface, EmploymentContractCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmEmploymentContractId();
	
	/**
	 * @return 雇用契約名称。
	 */
	String getEmploymentContractName();
	
	/**
	 * @return 雇用契約略称。
	 */
	String getEmploymentContractAbbr();
	
	/**
	 * @param pfmEmploymentContractId セットする レコード識別ID。
	 */
	void setPfmEmploymentContractId(long pfmEmploymentContractId);
	
	/**
	 * @param employmentContractName セットする 雇用契約名称。
	 */
	void setEmploymentContractName(String employmentContractName);
	
	/**
	 * @param employmentContractAbbr セットする 雇用契約略称。
	 */
	void setEmploymentContractAbbr(String employmentContractAbbr);
	
}
