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
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.EmploymentContractCodeDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.PositionCodeDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;
import jp.mosp.platform.dto.base.WorkPlaceCodeDtoInterface;

/**
 * 社員一覧DTOのインターフェース。<br>
 */
public interface HumanListDtoInterface
		extends BaseDtoInterface, PersonalIdDtoInterface, EmploymentContractCodeDtoInterface, WorkPlaceCodeDtoInterface,
		PositionCodeDtoInterface, SectionCodeDtoInterface, EmployeeCodeDtoInterface, EmployeeNameDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	String getPfmHumanId();
	
	/**
	 * @return カナ姓。
	 */
	String getLastKana();
	
	/**
	 * @return カナ名。
	 */
	String getFirstKana();
	
	/**
	 * @return 勤務地略称。
	 */
	String getWorkPlaceAbbr();
	
	/**
	 * @return 雇用契約略称。
	 */
	String getEmploymentContractAbbr();
	
	/**
	 * @return 所属名称。
	 */
	String getSectionName();
	
	/**
	 * @return 職位略称。
	 */
	String getPositionAbbr();
	
	/**
	 * @return 休退職区分。
	 */
	String getRetireState();
	
	/**
	 * @param pfmHumanId セットする レコード識別ID。
	 */
	void setPfmHumanId(String pfmHumanId);
	
	/**
	 * @param lastKana セットする カナ姓。
	 */
	void setLastKana(String lastKana);
	
	/**
	 * @param firstKana セットする カナ名。
	 */
	void setFirstKana(String firstKana);
	
	/**
	 * @param workPlaceAbbr セットする 勤務地略称。
	 */
	void setWorkPlaceAbbr(String workPlaceAbbr);
	
	/**
	 * @param employmentContractAbbr セットする 雇用契約略称。
	 */
	void setEmploymentContractAbbr(String employmentContractAbbr);
	
	/**
	 * @param sectionName セットする 所属名称。
	 */
	void setSectionName(String sectionName);
	
	/**
	 * @param positionAbbr セットする 職位略称。
	 */
	void setPositionAbbr(String positionAbbr);
	
	/**
	 * @param retireState セットする 休退職区分。
	 */
	void setRetireState(String retireState);
	
}
