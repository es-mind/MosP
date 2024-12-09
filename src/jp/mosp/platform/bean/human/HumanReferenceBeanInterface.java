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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;

/**
 * 人事マスタ参照インターフェース。<br>
 */
public interface HumanReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 人事マスタ取得。
	 * <p>
	 * レコード識別IDから人事マスタDTOを生成する。
	 * </p>
	 * @param id レコード識別ID
	 * @return 人事マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HumanDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * 人事マスタ取得。
	 * <p>
	 * 個人IDと対象年月日から人事マスタDTOを生成する。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 人事マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HumanDtoInterface getHumanInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 人事マスタ取得。
	 * <p>
	 * 社員コードと対象日から人事マスタDTOを生成する。
	 * </p>
	 * @param employeeCode 社員コード
	 * @param targetDate 対象年月日
	 * @return 人事マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HumanDtoInterface getHumanInfoForEmployeeCode(String employeeCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧取得。
	 * <p>
	 * 個人IDから人事マスタリストを生成する。
	 * </p>
	 * @param personalId 個人ID
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HumanDtoInterface> getHistory(String personalId) throws MospException;
	
	/**
	 * 有効日マスタ一覧取得。
	 * <p>
	 * 対象年月日から人事マスタリストを生成する。
	 * </p>
	 * @param targetDate 対象年月日
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HumanDtoInterface> getHumanList(Date targetDate) throws MospException;
	
	/**
	 * 社員コード取得。
	 * <p>
	 * 個人IDと対象年月日から社員コードを生成する。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 社員コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getEmployeeCode(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人IDを取得する。<br>
	 * 社員コードと対象年月日から個人IDを取得する。<br>
	 * 社員の履歴が存在しない場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param employeeCode 社員コード
	 * @param targetDate 対象年月日
	 * @return 個人ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getPersonalId(String employeeCode, Date targetDate) throws MospException;
	
	/**
	 * 氏名を取得する。<br>
	 * 個人IDと対象年月日から氏名を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 氏名
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getHumanName(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 社員コードリストから個人IDリストを取得する。<br>
	 * 社員の履歴が存在しない場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param employeeCodeList 社員コードリスト
	 * @param targetDate       対象日
	 * @return 個人IDリスト
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	List<String> getPersonalIdList(List<String> employeeCodeList, Date targetDate) throws MospException;
	
	/**
	 * 個人IDリストから社員コードリストを取得する。<br>
	 * @param personalIdList 個人IDリスト
	 * @param targetDate     対象日
	 * @return 社員コードリスト
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	List<String> getEmployeeCodeList(List<String> personalIdList, Date targetDate) throws MospException;
	
	/**
	 * 個人IDリストから氏名リストを取得する。<br>
	 * @param personalIdList 個人IDリスト
	 * @param targetDate     対象日
	 * @return 氏名リスト
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	List<String> getHumanNameList(List<String> personalIdList, Date targetDate) throws MospException;
	
	/**
	 * 社員コード(カンマ区切)から個人ID(カンマ区切)を取得する。<br>
	 * 社員の履歴が存在しない場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param employeeCodes 社員コード(カンマ区切)
	 * @param targetDate    対象日
	 * @return 個人ID(カンマ区切)
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	String getPersonalIds(String employeeCodes, Date targetDate) throws MospException;
	
	/**
	 * 社員コード(カンマ区切)から個人IDリストを取得する。<br>
	 * 社員の履歴が存在しない場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param employeeCodes 社員コード(カンマ区切)
	 * @param targetDate    対象日
	 * @return 個人IDリスト
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	List<String> getPersonalIdList(String employeeCodes, Date targetDate) throws MospException;
	
	/**
	 * 個人ID(カンマ区切)から社員コード(カンマ区切)を取得する。<br>
	 * @param personalIds 個人ID(カンマ区切)
	 * @param targetDate  対象日
	 * @return 社員コード(カンマ区切)
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	String getEmployeeCodes(String personalIds, Date targetDate) throws MospException;
	
	/**
	 * 個人ID(カンマ区切)から社員名(カンマ区切)を取得する。<br>
	 * @param personalIds 個人ID(カンマ区切)
	 * @param targetDate  対象日
	 * @return 社員名(カンマ区切)
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	String getHumanNames(String personalIds, Date targetDate) throws MospException;
	
	/**
	 * 個人IDリストから社員名(カンマ区切)を取得する。<br>
	 * @param personalIdList 個人IDリスト
	 * @param targetDate     対象日
	 * @return 社員名(カンマ区切)
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	String getHumanNames(List<String> personalIdList, Date targetDate) throws MospException;
	
	/**
	 * 人事情報リストに対象個人IDの情報を追加する。<br>
	 * ただし、対象個人IDの人事情報が既にリストに含まれる場合は、追加しない。<br>
	 * <br>
	 * @param list       人事情報リスト
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	void addHuman(List<HumanDtoInterface> list, String personalId, Date targetDate) throws MospException;
	
}
