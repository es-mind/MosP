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
package jp.mosp.platform.entity;

import java.util.List;

import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;

/**
 * 社員情報インターフェース
 */
public interface EmployeeEntityInterface {
	
	/**
	 * @return 個人ID
	 */
	String getPersonalId();
	
	/**
	 * @return 人事情報(履歴)
	 */
	List<HumanDtoInterface> getHumanList();
	
	/**
	 * @return 人事入社情報
	 */
	EntranceDtoInterface getEntranceDto();
	
	/**
	 * @return 人事兼務情報
	 */
	List<ConcurrentDtoInterface> getConcurrentList();
	
	/**
	 * @return 人事休職情報
	 */
	List<SuspensionDtoInterface> getSuspensionList();
	
	/**
	 * @return 人事退職情報
	 */
	RetirementDtoInterface getRetirementDto();
	
	/**
	 * @param personalId セットする 個人ID
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param humanList セットする 人事情報(履歴)
	 */
	void setHumanList(List<HumanDtoInterface> humanList);
	
	/**
	 * @param entranceDto セットする 人事入社情報
	 */
	void setEntranceDto(EntranceDtoInterface entranceDto);
	
	/**
	 * @param concurrentList セットする 人事兼務情報
	 */
	void setConcurrentList(List<ConcurrentDtoInterface> concurrentList);
	
	/**
	 * @param suspensionList セットする 人事休職情報
	 */
	void setSuspensionList(List<SuspensionDtoInterface> suspensionList);
	
	/**
	 * @param retirementDto セットする 人事退職情報
	 */
	void setRetirementDto(RetirementDtoInterface retirementDto);
	
}
