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
/**
 * 
 */
package jp.mosp.time.dto.settings;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;

/**
 * 集計時エラー内容参照DTOインターフェース
 */
public interface CutoffErrorListDtoInterface extends BaseDtoInterface, EmployeeNameDtoInterface {
	
	/**
	 * @return 日付。
	 */
	Date getDate();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 社員コード。
	 */
	String getEmployeeCode();
	
	/**
	 * @return 勤務地コード。
	 */
	String getWorkPlaceCode();
	
	/**
	 * @return 雇用契約コード。
	 */
	String getEmploymentCode();
	
	/**
	 * @return 所属コード。
	 */
	String getSectionCode();
	
	/**
	 * @return 職位コード。
	 */
	String getPositionCode();
	
	/**
	 * @return 区分。
	*/
	String getType();
	
	/**
	 * @return 状態。
	 */
	String getState();
	
	/**
	 * @param date セットする 日付。
	 */
	void setDate(Date date);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param employeeCode セットする 社員コード。
	 */
	void setEmployeeCode(String employeeCode);
	
	/**
	 * @param workPlaceCode セットする  勤務地コード。
	 */
	void setWorkPlaceCode(String workPlaceCode);
	
	/**
	 * @param employmentCode セットする 雇用契約コード。
	 */
	void setEmploymentCode(String employmentCode);
	
	/**
	 * @param  sectionCode セットする 所属コード。
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * @param positionCode セットする 職位コード。
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * @param type セットする 区分。
	*/
	void setType(String type);
	
	/**
	 * @param state セットする 状態。
	 */
	void setState(String state);
	
}
