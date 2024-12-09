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
package jp.mosp.platform.dao.human;

import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;

/**
 * 人事情報検索DAOインターフェース
 */
public interface HumanSearchDaoInterface extends BaseDaoInterface {
	
	/**
	 * 検索条件(対象日)。
	 */
	String	SEARCH_TARGET_DATE					= "targetDate";
	
	/**
	 * 検索条件(社員コード)。
	 */
	String	SEARCH_EMPLOYEE_CODE				= "employeeCode";
	
	/**
	 * 検索条件(from社員コード)。
	 */
	String	SEARCH_FROM_EMPLOYEE_CODE			= "fromEmployeeCode";
	
	/**
	 * 検索条件(to社員コード)。
	 */
	String	SEARCH_TO_EMPLOYEE_CODE				= "toEmployeeCode";
	
	/**
	 * 検索条件(社員コード検索区分)。
	 */
	String	SEARCH_EMPLOYEE_CODE_TYPE			= "employeeCodeType";
	
	/**
	 * 検索条件(勤務地コード)。
	 */
	String	SEARCH_WORK_PLACE_CODE				= "workPlaceCode";
	
	/**
	 * 検索条件(勤務地コード)。
	 */
	String	SEARCH_EMPLOYMENT_CONTRACT_CODE		= "employmentContractCode";
	
	/**
	 * 検索条件(所属コード)。
	 */
	String	SEARCH_SECTION_CODE					= "sectionCode";
	
	/**
	 * 検索条件(下位所属要否)。
	 */
	String	SEARCH_NEED_LOWER_SECTION			= "needLowerSection";
	
	/**
	 * 検索条件(職位コード)。
	 */
	String	SEARCH_POSITION_CODE				= "positionCode";
	
	/**
	 * 検索条件(職位等級範囲)。
	 */
	String	SEARCH_POSITION_GRADE_RANGE			= "positionGradeRange";
	
	/**
	 * 検索条件(兼務要否)。
	 */
	String	SEARCH_NEED_CONCURRENT				= "needConcurrent";
	
	/**
	 * 検索条件(休退職区分)。
	 */
	String	SEARCH_EMPLOYEE_STATE				= "employeeState";
	
	/**
	 * 検索条件(社員名)。
	 */
	String	SEARCH_EMPLOYEE_NAME				= "employeeName";
	
	/**
	 * 検索条件(フリーワード区分)。
	 */
	String	SEARCH_FREE_WORD_TYPE				= "freeWordType";
	
	/**
	 * 検索条件(不要個人ID)。
	 */
	String	SEARCH_UNNECESSARY_PERSONAL_ID		= "unnecessary";
	
	/**
	 * 検索条件(承認ロール要否)。
	 */
	String	SEARCH_NEED_APPROVER_ROLE			= "needApproverRole";
	
	/**
	 * 検索条件(勤務地範囲)。
	 */
	String	SEARCH_RANGE_WORK_PLACE				= "rangeWorkPlace";
	
	/**
	 * 検索条件(雇用契約範囲)。
	 */
	String	SEARCH_RANGE_EMPLOYMENT_CONTRACT	= "rangeEmploymentContract";
	
	/**
	 * 検索条件(所属範囲)。
	 */
	String	SEARCH_RANGE_SECTION				= "rangeSection";
	
	/**
	 * 検索条件(職位範囲)。
	 */
	String	SEARCH_RANGE_POSITION				= "rangePosition";
	
	/**
	 * 検索条件(社員範囲)。
	 */
	String	SEARCH_RANGE_EMPLOYEE				= "rangeEmployee";
	
	/**
	 * 検索条件(兼務範囲)。
	 */
	String	SEARCH_RANGE_CONCURRENT				= "rangeConcurrent";
	
	/**
	 * 検索条件(期間開始日)。
	 */
	String	SEARCH_START_DATE					= "startDate";
	
	/**
	 * 検索条件(期間終了日)。
	 */
	String	SEARCH_END_DATE						= "endDate";
	
	
	/**
	 * 検索条件から人事マスタリストを取得する。<br>
	 * @param param 検索条件マップ
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<HumanDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 人事マスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
}
