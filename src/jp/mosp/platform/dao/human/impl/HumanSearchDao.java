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
package jp.mosp.platform.dao.human.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.ConcurrentDaoInterface;
import jp.mosp.platform.dao.human.HumanSearchDaoInterface;
import jp.mosp.platform.dao.system.EmploymentContractDaoInterface;
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dao.system.WorkPlaceDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;

/**
 * 人事情報検索DAOクラス。
 */
public class HumanSearchDao extends PfmHumanDao implements HumanSearchDaoInterface {
	
	/**
	 * 所属マスタDAOクラス(サブクエリ等取得用)。
	 */
	protected SectionDaoInterface		sectionDao;
	
	/**
	 * 職位マスタDAOクラス(サブクエリ等取得用)。
	 */
	protected PositionDaoInterface		positionDao;
	
	/**
	 * 人事兼務情報DAOクラス(サブクエリ等取得用)。
	 */
	protected ConcurrentDaoInterface	concurrentDao;
	
	/**
	 * ユーザマスタDAOクラス(サブクエリ等取得用)。
	 */
	protected UserMasterDaoInterface	userDao;
	
	/**
	 * 検索条件(対象日)。
	 */
	protected Date						targetDate;
	
	/**
	 * 検索条件(社員コード)。
	 */
	protected String					employeeCode;
	
	/**
	 * 検索条件(from社員コード)。
	 */
	protected String					fromEmployeeCode;
	
	/**
	 * 検索条件(to社員コード)。
	 */
	protected String					toEmployeeCode;
	
	/**
	 * 検索条件(社員コード検索条件区分)。
	 */
	protected String					employeeCodeType;
	
	/**
	 * 検索条件(姓)。
	 */
	protected String					lastName;
	
	/**
	 * 検索条件(姓検索条件区分)。
	 */
	protected String					lastNameType;
	
	/**
	 * 検索条件(名)。
	 */
	protected String					firstName;
	
	/**
	 * 検索条件(名検索条件区分)。
	 */
	protected String					firstNameType;
	
	/**
	 * 検索条件(姓(カナ))。
	 */
	protected String					lastKana;
	
	/**
	 * 検索条件(姓(カナ)検索条件区分)。
	 */
	protected String					lastKanaType;
	
	/**
	 * 検索条件(名(カナ))。
	 */
	protected String					firstKana;
	
	/**
	 * 検索条件(名(カナ)検索条件区分)。
	 */
	protected String					firstKanaType;
	
	/**
	 * 検索条件(氏名)。
	 */
	protected String					employeeName;
	
	/**
	 * 検索条件(勤務地コード)。
	 */
	protected String					workPlaceCode;
	
	/**
	 * 検索条件(雇用契約コード)。
	 */
	protected String					employmentContractCode;
	
	/**
	 * 検索条件(所属コード)。
	 */
	protected String					sectionCode;
	
	/**
	 * 検索条件(下位所属要否)。
	 */
	protected boolean					needLowerSection;
	
	/**
	 * 検索条件(職位コード)。
	 */
	protected String					positionCode;
	
	/**
	 * 検索条件(職位等級範囲)。
	 */
	protected String					positionGradeRange;
	
	/**
	 * 検索条件(兼務要否)。
	 */
	protected boolean					needConcurrent;
	
	/**
	 * 検索条件(不要個人ID)。
	 */
	protected String					unnecessaryPersonalId;
	
	/**
	 * 検索条件(承認権限要否)。
	 */
	protected boolean					needApproverRole;
	
	
	/**
	 * コンストラクタ。
	 */
	public HumanSearchDao() {
		super();
	}
	
	@Override
	public List<HumanDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// サブクエリ等を取得するためのDAOクラスを設定
			setDaoInstances();
			// 検索条件設定
			setSearchParams(param);
			// ステートメント生成
			prepareStatement(getQueryForSearch(param));
			// 検索条件パラメータ設定
			setParamsForSearch(param);
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	/**
	 * サブクエリ等を取得するためのDAOクラスを設定する。<br>
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected void setDaoInstances() throws MospException {
		// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
		sectionDao = (SectionDaoInterface)loadDao(SectionDaoInterface.class);
		positionDao = (PositionDaoInterface)loadDao(PositionDaoInterface.class);
		concurrentDao = (ConcurrentDaoInterface)loadDao(ConcurrentDaoInterface.class);
		userDao = (UserMasterDaoInterface)loadDao(UserMasterDaoInterface.class);
	}
	
	/**
	 * 検索パラメータを設定する。<br>
	 * @param param 検索条件マップ
	 */
	protected void setSearchParams(Map<String, Object> param) {
		targetDate = (Date)param.get(HumanSearchDaoInterface.SEARCH_TARGET_DATE);
		employeeCode = getSearchParam(param, HumanSearchDaoInterface.SEARCH_EMPLOYEE_CODE);
		fromEmployeeCode = getSearchParam(param, HumanSearchDaoInterface.SEARCH_FROM_EMPLOYEE_CODE);
		toEmployeeCode = getSearchParam(param, HumanSearchDaoInterface.SEARCH_TO_EMPLOYEE_CODE);
		employeeCodeType = getSearchParam(param, HumanSearchDaoInterface.SEARCH_EMPLOYEE_CODE_TYPE);
		lastName = getSearchParam(param, "lastName");
		lastNameType = getSearchParam(param, "lastNameType");
		firstName = getSearchParam(param, "firstName");
		firstNameType = getSearchParam(param, "firstNameType");
		lastKana = getSearchParam(param, "lastKana");
		lastKanaType = getSearchParam(param, "lastKanaType");
		firstKana = getSearchParam(param, "firstKana");
		firstKanaType = getSearchParam(param, "firstKanaType");
		employeeName = getSearchParam(param, HumanSearchDaoInterface.SEARCH_EMPLOYEE_NAME);
		workPlaceCode = getSearchParam(param, HumanSearchDaoInterface.SEARCH_WORK_PLACE_CODE);
		employmentContractCode = getSearchParam(param, HumanSearchDaoInterface.SEARCH_EMPLOYMENT_CONTRACT_CODE);
		sectionCode = getSearchParam(param, HumanSearchDaoInterface.SEARCH_SECTION_CODE);
		needLowerSection = getSearchBoolParam(param, HumanSearchDaoInterface.SEARCH_NEED_LOWER_SECTION);
		positionCode = getSearchParam(param, HumanSearchDaoInterface.SEARCH_POSITION_CODE);
		positionGradeRange = getSearchParam(param, SEARCH_POSITION_GRADE_RANGE);
		needConcurrent = getSearchBoolParam(param, HumanSearchDaoInterface.SEARCH_NEED_CONCURRENT);
		unnecessaryPersonalId = getSearchParam(param, HumanSearchDaoInterface.SEARCH_UNNECESSARY_PERSONAL_ID);
		needApproverRole = getSearchBoolParam(param, HumanSearchDaoInterface.SEARCH_NEED_APPROVER_ROLE);
	}
	
	/**
	 * 検索SQLを取得する。<br>
	 * @param param 検索条件マップ
	 * @return 検索SQL
	 * @throws MospException SQLの作成に失敗した場合
	 */
	protected String getQueryForSearch(Map<String, Object> param) throws MospException {
		// SQL作成準備(SELECT文追加)
		StringBuffer sb = new StringBuffer(getSelectQuery(getClass()));
		// WHERE部追加(対象日以前で削除されていない最新の情報を取得)
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		// 社員コード条件SQL追加
		if (employeeCode.isEmpty() == false) {
			sb.append(and());
			sb.append(getSubQueryCondition(employeeCodeType, COL_EMPLOYEE_CODE));
		}
		// fromTo社員コード条件SQL追加
		if (fromEmployeeCode.isEmpty() == false || toEmployeeCode.isEmpty() == false) {
			sb.append(and());
			sb.append(leftParenthesis());
			// fromTo社員コード条件SQL追加
			if (fromEmployeeCode.isEmpty() == false && toEmployeeCode.isEmpty() == false) {
				sb.append(greaterEqual(COL_EMPLOYEE_CODE));
				sb.append(and());
				sb.append(lessEqual(COL_EMPLOYEE_CODE));
			}
			// from社員コード条件SQL追加
			if (fromEmployeeCode.isEmpty() == false && toEmployeeCode.isEmpty()) {
				sb.append(greaterEqual(COL_EMPLOYEE_CODE));
			}
			// to社員コード条件SQL追加
			if (fromEmployeeCode.isEmpty() && toEmployeeCode.isEmpty() == false) {
				sb.append(lessEqual(COL_EMPLOYEE_CODE));
			}
			sb.append(rightParenthesis());
		}
		// 社員名条件SQL追加
		if (employeeName.isEmpty() == false) {
			sb.append(getQueryForEmployeeName());
		}
		// 姓条件SQL追加
		if (lastName.isEmpty() == false) {
			sb.append(and());
			sb.append(getSubQueryCondition(lastNameType, COL_LAST_NAME));
		}
		// 姓(カナ)条件SQL追加
		if (lastKana.isEmpty() == false) {
			sb.append(and());
			sb.append(getSubQueryCondition(lastKanaType, COL_LAST_KANA));
		}
		// 名条件SQL追加
		if (firstName.isEmpty() == false) {
			sb.append(and());
			sb.append(getSubQueryCondition(firstNameType, COL_FIRST_NAME));
		}
		// 名(カナ)条件SQL追加
		if (firstKana.isEmpty() == false) {
			sb.append(and());
			sb.append(getSubQueryCondition(firstKanaType, COL_FIRST_KANA));
		}
		// 勤務地条件SQL追加
		if (workPlaceCode.isEmpty() == false) {
			sb.append(and());
			sb.append(equal(COL_WORK_PLACE_CODE));
		}
		// 雇用契約条件SQL追加
		if (employmentContractCode.isEmpty() == false) {
			sb.append(and());
			sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
		}
		// 所属条件SQL追加
		if (sectionCode.isEmpty() == false) {
			// 人事マスタの所属
			sb.append(and());
			sb.append(leftParenthesis());
			// 人事マスタの所属
			if (needLowerSection) {
				// 下位所属含む
				sb.append(sectionDao.getQueryForLowerSection(COL_SECTION_CODE));
			} else {
				sb.append(equal(COL_SECTION_CODE));
			}
			// 人事兼務情報の所属
			if (needConcurrent) {
				sb.append(or());
				sb.append(concurrentDao.getQueryForSection(COL_PERSONAL_ID, needLowerSection));
			}
			sb.append(rightParenthesis());
		}
		// 職位条件SQL追加
		if (positionGradeRange.isEmpty()) {
			if (!positionCode.isEmpty()) {
				sb.append(and());
				sb.append(leftParenthesis());
				// 人事マスタの職位
				sb.append(equal(COL_POSITION_CODE));
				// 人事兼務情報の職位
				if (needConcurrent) {
					sb.append(or());
					sb.append(concurrentDao.getQueryForPosition(COL_PERSONAL_ID));
				}
				sb.append(rightParenthesis());
			}
		} else {
			boolean greaterEqual = "1".equals(positionGradeRange);
			if (!positionCode.isEmpty()) {
				sb.append(and());
				sb.append(leftParenthesis());
				sb.append(COL_POSITION_CODE);
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(positionDao.getQueryForPositionGrade(greaterEqual));
				sb.append(rightParenthesis());
				if (needConcurrent) {
					sb.append(or());
					sb.append(concurrentDao.getQueryForPositionGrade(greaterEqual, COL_PERSONAL_ID));
				}
				sb.append(rightParenthesis());
			}
		}
		// 不要個人ID条件SQL追加
		if (unnecessaryPersonalId.isEmpty() == false) {
			sb.append(and());
			sb.append(notEqual(COL_PERSONAL_ID));
		}
		// 承認ロール条件SQL追加
		if (needApproverRole) {
			sb.append(userDao.getQueryForApprover(COL_PERSONAL_ID));
			sb.append(positionDao.getQueryForApprover(COL_POSITION_CODE));
		}
		// 範囲条件SQL追加
		sb.append(getQueryForRange(param, COL_PERSONAL_ID));
		// その他条件SQL追加
		sb.append(getAdditionalQuery(param));
		// 並び替え
		sb.append(getOrderByColumnForParam(param));
		return sb.toString();
	}
	
	/**
	 * 検索条件パラメータを設定する。<br>
	 * @param param 検索条件マップ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParamsForSearch(Map<String, Object> param) throws MospException {
		// パラメータインデックス準備
		index = 1;
		// 有効日における最新の情報を抽出する条件パラメータを追加
		index = setParamsForMaxActivateDate(index, targetDate, ps);
		// 社員コード検索条件設定
		if (employeeCode.isEmpty() == false) {
			setParam(index++, getSetParamCondition(employeeCodeType, employeeCode));
		}
		// fromTo社員コード条件SQL追加
		if (fromEmployeeCode.isEmpty() == false || toEmployeeCode.isEmpty() == false) {
			// fromTo社員コード条件SQL追加
			if (fromEmployeeCode.isEmpty() == false && toEmployeeCode.isEmpty() == false) {
				setParam(index++, fromEmployeeCode);
				setParam(index++, toEmployeeCode);
			}
			// from社員コード条件SQL追加
			if (fromEmployeeCode.isEmpty() == false && toEmployeeCode.isEmpty()) {
				setParam(index++, fromEmployeeCode);
			}
			// to社員コード条件SQL追加
			if (fromEmployeeCode.isEmpty() && toEmployeeCode.isEmpty() == false) {
				setParam(index++, toEmployeeCode);
			}
		}
		// 社員名条件パラメータ設定
		if (employeeName.isEmpty() == false) {
			index = setParamsForEmployeeName(index, employeeName, ps);
		}
		// 姓検索条件設定
		if (lastName.isEmpty() == false) {
			setParam(index++, getSetParamCondition(lastNameType, lastName));
		}
		// 姓(カナ)検索条件設定
		if (lastKana.isEmpty() == false) {
			setParam(index++, getSetParamCondition(lastKanaType, lastKana));
		}
		// 名検索条件設定
		if (firstName.isEmpty() == false) {
			setParam(index++, getSetParamCondition(firstNameType, firstName));
		}
		// 名(カナ)検索条件設定
		if (firstKana.isEmpty() == false) {
			setParam(index++, getSetParamCondition(firstKanaType, firstKana));
		}
		// 勤務地検索条件設定
		if (workPlaceCode.isEmpty() == false) {
			setParam(index++, workPlaceCode);
		}
		// 雇用契約検索条件設定
		if (employmentContractCode.isEmpty() == false) {
			setParam(index++, employmentContractCode);
		}
		// 所属検索条件設定
		if (sectionCode.isEmpty() == false) {
			// 人事マスタの所属
			if (needLowerSection) {
				index = sectionDao.setParamsForLowerSection(index, sectionCode, targetDate, ps);
			} else {
				setParam(index++, sectionCode);
			}
			// 人事兼務情報の所属
			if (needConcurrent) {
				index = concurrentDao.setParamsForSection(index, sectionCode, targetDate, needLowerSection, ps);
			}
		}
		// 職位検索条件設定
		if (positionGradeRange.isEmpty()) {
			if (!positionCode.isEmpty()) {
				setParam(index++, positionCode);
				// 人事兼務情報の職位
				if (needConcurrent) {
					index = concurrentDao.setParamsForPosition(index, positionCode, targetDate, ps);
				}
			}
		} else {
			if (!positionCode.isEmpty()) {
				index = positionDao.setParamsForPositionGrande(index, positionCode, targetDate, ps);
				if (needConcurrent) {
					index = concurrentDao.setParamsForPositionGrade(index, positionCode, targetDate, ps);
				}
			}
		}
		// 不要個人ID条件パラメータ追加
		if (unnecessaryPersonalId.isEmpty() == false) {
			setParam(index++, unnecessaryPersonalId);
		}
		// 承認ロール条件パラメータ追加
		if (needApproverRole) {
			index = userDao.setParamsForApprover(index, targetDate, ps);
			index = positionDao.setParamsForApprover(index, targetDate, ps);
		}
		// 範囲検索条件パラメータ設定
		setParamsForRange(param);
	}
	
	/**
	 * 操作範囲条件SQLを作成する。<br>
	 * <br>
	 * 操作範囲条件では、検索対象者の人事基本情報に設定されている所属及び職位を、
	 * 条件合致の対象とする(検索対象者の兼務情報は考慮されない)。<br>
	 * つまり、Aさんが所属：S1、職位：P1であり、所属：S2、職位：P2を兼務している場合、
	 * 操作者Bさんの操作範囲が所属：S2、職位：P2であったとしても、Aさんは検索結果に含まれない。<br>
	 * <br>
	 * 操作範囲(所属)及び操作範囲(職位)に範囲設定(自身)がなされている場合に限り、
	 * 操作者の兼務情報による条件を付加する。<br>
	 * 兼務の条件は、検索対象者の人事基本情報に設定されている所属及び職位のみを
	 * 条件合致の対象とする(検索対象者の兼務情報は考慮されない)。<br>
	 * 兼務の条件は、兼務の件数により、通常の条件に加えて「or」で検索される。<br>
	 * 例：通常の条件 or 兼務1の条件 or 兼務2の条件・・・<br>
	 * <br>
	 * @param param        検索条件マップ
	 * @param targetColumn 検索対象個人ID列
	 * @return 操作範囲用条件SQL
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected String getQueryForRange(Map<String, Object> param, String targetColumn) throws MospException {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		// 操作範囲条件設定確認
		if (isRangeEmployeeSetted(param) == false && isRangeMasterSetted(param) == false) {
			// 操作範囲条件不用
			return sb.toString();
		}
		// 操作範囲条件SQL(勤務地)追加準備
		WorkPlaceDaoInterface workPlaceDao = (WorkPlaceDaoInterface)loadDao(WorkPlaceDaoInterface.class);
		String[] rangeWorkPlace = getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_WORK_PLACE);
		// 操作範囲条件SQL(雇用契約)追加準備
		EmploymentContractDaoInterface employmentContractDao;
		employmentContractDao = (EmploymentContractDaoInterface)loadDao(EmploymentContractDaoInterface.class);
		String[] rangeEmploymentContract = getSearchParams(param,
				HumanSearchDaoInterface.SEARCH_RANGE_EMPLOYMENT_CONTRACT);
		// 操作範囲検索条件SQL(所属)追加準備
		String[] rangeSection = getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_SECTION);
		// 操作範囲条件SQL(職位)追加準備
		PositionDaoInterface positionDao = (PositionDaoInterface)loadDao(PositionDaoInterface.class);
		String[] rangePosition = getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_POSITION);
		// 操作範囲条件SQL(社員)追加準備
		String[] rangeEmployee = getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_EMPLOYEE);
		// 操作範囲検索条件SQL(兼務)追加準備
		String[][][] rangeConcurrent = getSearchArrayParams(param, HumanSearchDaoInterface.SEARCH_RANGE_CONCURRENT);
		// SQL作成
		sb.append(and());
		sb.append(leftParenthesis());
		// 操作範囲条件SQL(社員)追加
		if (isRangeEmployeeSetted(param)) {
			// SQL追加
			sb.append(targetColumn);
			sb.append(in());
			sb.append(leftParenthesis());
			// SELECT部追加(個人ID)
			sb.append(select());
			sb.append(COL_PERSONAL_ID);
			// FROM部追加
			sb.append(from(TABLE));
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
			// WHERE部追加
			sb.append(where());
			// 削除されていない情報のみ抽出
			sb.append(deleteFlagOff());
			// 操作範囲条件SQL(社員)追加
			sb.append(getQueryForRange(rangeEmployee, COL_PERSONAL_ID));
			sb.append(rightParenthesis());
		}
		// 操作範囲条件SQL(勤務地、雇用契約、所属、職位)追加
		if (isRangeMasterSetted(param)) {
			// 操作範囲条件SQL(社員)追加確認
			if (isRangeEmployeeSetted(param)) {
				// OR追加
				sb.append(or());
			}
			// SQL追加
			sb.append(targetColumn);
			sb.append(in());
			sb.append(leftParenthesis());
			// SELECT部追加(個人ID)
			sb.append(select());
			sb.append(COL_PERSONAL_ID);
			// FROM部追加
			sb.append(from(TABLE));
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
			// WHERE部追加
			sb.append(where());
			// 削除されていない情報のみ抽出
			sb.append(deleteFlagOff());
			// 操作範囲条件SQL(勤務地)追加
			sb.append(workPlaceDao.getQueryForRange(rangeWorkPlace, COL_WORK_PLACE_CODE));
			// 操作範囲条件SQL(雇用契約)追加
			sb.append(employmentContractDao.getQueryForRange(rangeEmploymentContract, COL_EMPLOYMENT_CONTRACT_CODE));
			// 操作範囲条件SQL(所属)追加
			sb.append(sectionDao.getQueryForRange(rangeSection, COL_SECTION_CODE));
			// 操作範囲条件SQL(職位)追加
			sb.append(positionDao.getQueryForRange(rangePosition, COL_POSITION_CODE));
			sb.append(rightParenthesis());
		}
		// 操作範囲条件SQL(兼務)追加(兼務情報件数の分だけ)
		for (String[][] concurrent : rangeConcurrent) {
			// SQL追加
			sb.append(or());
			sb.append(targetColumn);
			sb.append(in());
			sb.append(leftParenthesis());
			// SELECT部追加(個人ID)
			sb.append(select());
			sb.append(COL_PERSONAL_ID);
			// FROM部追加
			sb.append(from(TABLE));
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
			// WHERE部追加
			sb.append(where());
			// 削除されていない情報のみ抽出
			sb.append(deleteFlagOff());
			// 操作範囲条件SQL(所属(兼務))追加
			sb.append(sectionDao.getQueryForRange(concurrent[0], COL_SECTION_CODE));
			// 操作範囲条件SQL(職位(兼務))追加
			sb.append(positionDao.getQueryForRange(concurrent[1], COL_POSITION_CODE));
			sb.append(rightParenthesis());
		}
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	/**
	 * 操作範囲条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param param 検索条件マップ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParamsForRange(Map<String, Object> param) throws MospException {
		// 操作範囲条件設定確認
		if (isRangeEmployeeSetted(param) == false && isRangeMasterSetted(param) == false) {
			// 操作範囲条件パラメータ設定不要
			return;
		}
		// 操作範囲条件パラメータ(勤務地)設定準備
		WorkPlaceDaoInterface workPlaceDao = (WorkPlaceDaoInterface)loadDao(WorkPlaceDaoInterface.class);
		String[] rangeWorkPlace = getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_WORK_PLACE);
		// 操作範囲条件パラメータ(雇用契約)設定準備
		EmploymentContractDaoInterface employmentContractDao;
		employmentContractDao = (EmploymentContractDaoInterface)loadDao(EmploymentContractDaoInterface.class);
		String[] rangeEmploymentContract = getSearchParams(param,
				HumanSearchDaoInterface.SEARCH_RANGE_EMPLOYMENT_CONTRACT);
		// 操作範囲条件パラメータ(所属)設定準備
		String[] rangeSection = getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_SECTION);
		// 操作範囲条件パラメータ(職位)設定準備
		PositionDaoInterface positionDao = (PositionDaoInterface)loadDao(PositionDaoInterface.class);
		String[] rangePosition = getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_POSITION);
		// 操作範囲条件SQL(社員)追加準備
		String[] rangeEmployee = getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_EMPLOYEE);
		// 操作範囲検索条件SQL(兼務)追加準備
		String[][][] rangeConcurrent = getSearchArrayParams(param, HumanSearchDaoInterface.SEARCH_RANGE_CONCURRENT);
		// 操作範囲条件SQL(社員)確認
		if (isRangeEmployeeSetted(param)) {
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, targetDate, ps);
			// 操作範囲条件パラメータ(社員)設定
			index = setParamsForRange(index, rangeEmployee, ps);
		}
		// 操作範囲条件SQL(勤務地、雇用契約、所属、職位)確認
		if (isRangeMasterSetted(param)) {
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, targetDate, ps);
			// 操作範囲条件パラメータ(勤務地)設定
			index = workPlaceDao.setParamsForRange(index, rangeWorkPlace, ps);
			// 操作範囲条件パラメータ(雇用契約)設定
			index = employmentContractDao.setParamsForRange(index, rangeEmploymentContract, ps);
			// 操作範囲条件パラメータ(所属)設定
			index = sectionDao.setParamsForRange(index, rangeSection, targetDate, ps);
			// 操作範囲条件パラメータ(職位)設定
			index = positionDao.setParamsForRange(index, rangePosition, targetDate, ps);
		}
		// 操作範囲条件パラメータ(兼務)追加(兼務情報件数の分だけ)
		for (String[][] concurrent : rangeConcurrent) {
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, targetDate, ps);
			// 操作範囲条件パラメータ(所属(兼務))設定
			index = sectionDao.setParamsForRange(index, concurrent[0], targetDate, ps);
			// 操作範囲条件パラメータ(職位(兼務))設定
			index = positionDao.setParamsForRange(index, concurrent[1], targetDate, ps);
		}
	}
	
	/**
	 * 検索条件を取得する。<br>
	 * キーの検索条件が設定されていない場合、空の配列を返す。<br>
	 * @param searchParams 検索条件群
	 * @param key          検索条件キー
	 * @return 検索条件
	 */
	protected String[][][] getSearchArrayParams(Map<String, Object> searchParams, String key) {
		Object obj = searchParams.get(key);
		if (obj instanceof String[][][]) {
			return (String[][][])obj;
		}
		return new String[0][][];
	}
	
	/**
	 * 操作範囲(勤務地、雇用契約、所属、職位)条件が設定されているかを確認する。<br>
	 * @param param 検索条件マップ
	 * @return 操作範囲条件設定確認結果(true：設定されている、false：設定されていない)
	 */
	protected boolean isRangeMasterSetted(Map<String, Object> param) {
		// 勤務地範囲検索条件確認
		if (getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_WORK_PLACE).length > 0) {
			return true;
		}
		// 雇用契約範囲検索条件確認
		if (getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_EMPLOYMENT_CONTRACT).length > 0) {
			return true;
		}
		// 所属範囲検索条件確認
		if (getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_SECTION).length > 0) {
			return true;
		}
		// 職位範囲検索条件確認
		if (getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_POSITION).length > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 操作範囲(社員)条件が設定されているかを確認する。<br>
	 * @param param 検索条件マップ
	 * @return 操作範囲条件設定確認結果(true：設定されている、false：設定されていない)
	 */
	protected boolean isRangeEmployeeSetted(Map<String, Object> param) {
		// 社員範囲検索条件確認
		if (getSearchParams(param, HumanSearchDaoInterface.SEARCH_RANGE_EMPLOYEE).length > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 検索条件を区分で判断し、サブクエリを返却する。<br>
	 * @param queryType 検索条件区分
	 * @param column 検索列
	 * @return 検索条件サブクエリ
	 */
	protected String getSubQueryCondition(Object queryType, String column) {
		String retString = "";
		if (queryType.equals(PlatformConst.SEARCH_FORWARD_MATCH)) {
			// 前方一致
			retString = like(column);
		} else if (queryType.equals(PlatformConst.SEARCH_BROAD_MATCH)) {
			// 部分一致
			retString = like(column);
		} else if (queryType.equals(PlatformConst.SEARCH_EXACT_MATCH)) {
			// 完全一致
			retString = equal(column);
		}
		return retString;
	}
	
	/**
	 * 検索条件区分で判断し、パラメータを取得する。<br>
	 * @param queryType 検索条件区分
	 * @param value 検索値(元)
	 * @return 検索値(設定後)
	 */
	protected String getSetParamCondition(Object queryType, String value) {
		String retString = "";
		if (queryType.equals(PlatformConst.SEARCH_FORWARD_MATCH)) {
			// 前方一致
			retString = startWithParam(value);
		} else if (queryType.equals(PlatformConst.SEARCH_BROAD_MATCH)) {
			// 部分一致
			retString = containsParam(value);
		} else if (queryType.equals(PlatformConst.SEARCH_EXACT_MATCH)) {
			// 完全一致
			retString = value;
		}
		return retString;
	}
	
	/**
	 * @param param 検索条件マップ
	 * @return その他条件SQL
	 */
	protected String getAdditionalQuery(Map<String, Object> param) {
		return "";
	}
	
	/**
	 * @param param 検索条件マップ
	 * @return ソート条件句
	 */
	protected String getOrderByColumnForParam(Map<String, Object> param) {
		return getOrderByColumn(COL_EMPLOYEE_CODE);
	}
	
}
