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
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.EmploymentContractCodeDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.PositionCodeDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;
import jp.mosp.platform.dto.base.WorkPlaceCodeDtoInterface;

/**
 * 人事マスタDTOインターフェース。
 */
public interface HumanDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface, ActivateDtoInterface,
		EmployeeCodeDtoInterface, EmployeeNameDtoInterface, SectionCodeDtoInterface, WorkPlaceCodeDtoInterface,
		PositionCodeDtoInterface, EmploymentContractCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmHumanId();
	
	/**
	 * @return カナ姓。
	 */
	String getLastKana();
	
	/**
	 * @return カナ名。
	 */
	String getFirstKana();
	
	/**
	 * @return メールアドレス。
	 */
	String getMail();
	
	/**
	 * @param pfmHumanId セットする レコード識別ID。
	 */
	void setPfmHumanId(long pfmHumanId);
	
	/**
	 * @param lastKana セットする カナ姓。
	 */
	void setLastKana(String lastKana);
	
	/**
	 * @param firstKana セットする カナ名。
	 */
	void setFirstKana(String firstKana);
	
	/**
	 * @param mail セットする メールアドレス。
	 */
	void setMail(String mail);
	
}
