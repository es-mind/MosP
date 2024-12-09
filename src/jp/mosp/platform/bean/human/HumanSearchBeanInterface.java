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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanListDtoInterface;

/**
 * 人事マスタ検索インターフェース。
 */
public interface HumanSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から人事マスタリストを取得する。<br>
	 * {@link #setTargetDate(Date)}等で設定された条件で、検索を行う。<br>
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<HumanDtoInterface> search() throws MospException;
	
	/**
	 * 検索条件から人事マスタリストを取得する。<br>
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<HumanListDtoInterface> getHumanList() throws MospException;
	
	/**
	 * 個人IDセットを取得する。<br>
	 * 設定された条件で、検索を行う。<br>
	 * @return 個人IDセット
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	Set<String> getPersonalIdSet() throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 検索条件からプルダウン用配列を取得する。<br>
	 * 値は、社員コード。<br>
	 * 表示内容は、社員コード + 社員名。<br>
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(boolean needBlank) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 検索条件からプルダウン用配列を取得する。<br>
	 * 値は、社員コード。<br>
	 * 表示内容は、社員名。<br>
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(boolean needBlank) throws MospException;
	
	/**
	 * 人事情報マップを取得する。<br>
	 * 個人IDと人事情報のマップを取得する。<br>
	 * @return 人事情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, HumanDtoInterface> getHumanDtoMap() throws MospException;
	
	/**
	 * @param targetDate セットする対象日
	 */
	void setTargetDate(Date targetDate);
	
	/**
	 * @param employeeCode セットする 社員コード。
	 */
	void setEmployeeCode(String employeeCode);
	
	/**
	 * @param fromEmployeeCode セットする from社員コード。
	 */
	void setFromEmployeeCode(String fromEmployeeCode);
	
	/**
	 * @param toEmployeeCode セットする to社員コード。
	 */
	void setToEmployeeCode(String toEmployeeCode);
	
	/**
	 * 社員名を設定する。<br>
	 * 姓+名で人事情報を抽出するための条件。<br>
	 * @param employeeName セットする社員名
	 */
	void setEmployeeName(String employeeName);
	
	/**
	 * @param lastName セットする 姓。
	 */
	void setLastName(String lastName);
	
	/**
	 * @param firstName セットする 名。
	 */
	void setFirstName(String firstName);
	
	/**
	 * @param lastKana セットする 姓（カナ）。
	 */
	void setLastKana(String lastKana);
	
	/**
	 * @param firstKana セットする 名（カナ）。
	 */
	void setFirstKana(String firstKana);
	
	/**
	 * 所属コードを設定する。<br>
	 * 所属コードの下位所属が設定されている人事情報を抽出するための条件。<br>
	 * @param sectionCode セットする所属コード
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * 下位所属要否を設定する。<br>
	 * 所属コードで抽出した人事情報のみでなく、下位所属で抽出した人事情報を加える。<br>
	 * @param needLowerSection セットする下位所属要否
	 */
	void setNeedLowerSection(boolean needLowerSection);
	
	/**
	 * 職位コードを設定する。<br>
	 * 職位コードが設定されている人事情報を抽出するための条件。<br>
	 * @param positionCode セットする職位コード
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * 職位等級範囲を設定する。<br>
	 * 職位コードで抽出した人事情報のみでなく、職位ランクで抽出した人事情報を加える。<br>
	 * @param positionGradeRange セットする職位等級範囲
	 */
	void setPositionGradeRange(String positionGradeRange);
	
	/**
	 * 兼務要否を設定する。<br>
	 * 所属コード及び職位コードで抽出した人事情報のみでなく、
	 * 人事兼務情報から所属コードの下位所属及び職位コードで抽出した人事情報を加える。<br>
	 * 未設定、或いはfalseの場合、人事兼務情報は考慮されない。<br>
	 * @param needConcurrent セットする兼務要否
	 */
	void setNeedConcurrent(boolean needConcurrent);
	
	/**
	 * @param employmentContractCode セットする 雇用契約コード。
	 */
	void setEmploymentContractCode(String employmentContractCode);
	
	/**
	 * @param workPlaceCode セットする 勤務地コード。
	 */
	void setWorkPlaceCode(String workPlaceCode);
	
	/**
	 * @param informationType セットする 情報区分。
	 */
	void setInformationType(String informationType);
	
	/**
	 * @param searchWord セットする 検索ワード。
	 */
	void setSearchWord(String searchWord);
	
	/**
	 * @param stateType セットする 休退職区分。
	 */
	void setStateType(String stateType);
	
	/**
	 * @param employeeCodeType セットする 条件区分（社員コード）。
	 */
	void setEmployeeCodeType(String employeeCodeType);
	
	/**
	 * @param lastNameType セットする 条件区分（姓）。
	 */
	void setLastNameType(String lastNameType);
	
	/**
	 * @param firstNameType セットする 条件区分（名）。
	 */
	void setFirstNameType(String firstNameType);
	
	/**
	 * @param lastKanaType セットする 条件区分（姓（カナ））。
	 */
	void setLastKanaType(String lastKanaType);
	
	/**
	 * @param firstKanaType セットする 条件区分（名（カナ））。
	 */
	void setFirstKanaType(String firstKanaType);
	
	/**
	 * 不要個人IDを設定する。<br>
	 * 検索結果に含めたくない人事情報が存在する場合に設定する。<br>
	 * @param unnecessaryPersonalId セットする不要個人ID
	 */
	void setUnnecessaryPersonalId(String unnecessaryPersonalId);
	
	/**
	 * 承認ロール要否を設定する。<br>
	 * trueにすると、承認ロールを持った人事情報のみが抽出される。<br>
	 * 未設定、或いはfalseの場合、承認ロールは考慮されない。<br>
	 * @param needApproverRole セットする承認ロール要否
	 */
	void setNeedApproverRole(boolean needApproverRole);
	
	/**
	 * 操作区分を設定する。<br>
	 * 設定された操作区分から各マスタの設定を取得し、抽出される人事情報を制限する。<br>
	 * 未設定の場合、操作区分は考慮されない。<br>
	 * @param operationType セットする操作区分
	 */
	void setOperationType(String operationType);
	
	/**
	 * 期間開始日を設定する。<br>
	 * 休退職区分を範囲で条件指定したい場合に設定する。<br>
	 * @param startDate セットする期間開始日
	 */
	void setStartDate(Date startDate);
	
	/**
	 * 期間終了日を設定する。<br>
	 * 休退職区分を範囲で条件指定したい場合に設定する。<br>
	 * @param endDate セットする期間終了日
	 */
	void setEndDate(Date endDate);
	
}
