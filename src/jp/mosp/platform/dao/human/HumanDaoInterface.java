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

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;

/**
 * 人事マスタDAOインターフェース。<br>
 */
public interface HumanDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと有効日から人事マスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return 人事マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanDtoInterface findForKey(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 人事マスタ取得。
	 * <p>
	 * 個人IDと有効日から人事マスタを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return 人事マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanDtoInterface findForInfo(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 人事マスタ取得。
	 * <p>
	 * 社員コードと有効日から人事マスタを取得する。
	 * </p>
	 * @param employeeCode 社員コード
	 * @param activateDate 有効日
	 * @return 人事マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanDtoInterface findForEmployeeCode(String employeeCode, Date activateDate) throws MospException;
	
	/**
	 * 人事マスタリストを取得する。<br>
	 * 対象社員コードが設定されている情報を取得する。<br>
	 * 社員コード重複確認等に用いる。<br>
	 * @param employeeCode 社員コード
	 * @return 人事マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanDtoInterface> findForEmployeeCode(String employeeCode) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 個人IDから人事マスタリストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @return 人事マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanDtoInterface> findForHistory(String personalId) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から人事マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 人事マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 人事情報群(キー：個人ID)を取得する。<br>
	 * <br>
	 * 対象日における全ての人事情報を取得する。<br>
	 * <br>
	 * @param targetDate 有効日
	 * @return 人事情報群(キー：個人ID)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<String, HumanDtoInterface> findForTargetDate(Date targetDate) throws MospException;
	
	/**
	 * 社員コード群を取得する。<br>
	 * <br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 社員コード採番に用いる。<br>
	 * <br>
	 * @return 社員コード群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<String> findForEmployeeNumbering() throws MospException;
	
	/**
	 * 人事マスタリストを取得する。<br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 有効日の範囲で検索する。但し、有効日From及び有効日Toは、検索対象に含まれない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @return 人事マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate) throws MospException;
	
	/**
	 * 個人ID最大値取得。
	 * <p>
	 * 個人IDの最大値を取得する。
	 * </p>
	 * @return 個人ID
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	long nextPersonalId() throws MospException;
	
	/**
	 * サブクエリ。
	 * <p>
	 * 社員コードと有効日による個人IDを取得するSQL。
	 * </p>
	 * @return
	 * <pre>
	 * SELECT
	 * 	個人ID
	 * FROM 人事マスタ
	 * WHERE 削除フラグ = 0
	 * AND 社員コード = ?
	 * AND 有効日 <= ? DESC LIMIT 1
	 * </pre>
	 */
	String getQueryForEmployeeCode();
	
	/**
	 * サブクエリ。
	 * <p>
	 * 社員氏名と有効日による個人IDを取得するSQL。
	 * LIKE検索はあいまい検索にて。
	 * @param targetColumn 対象個人ID列名
	 * </p>
	 * @return
	 * <pre>
	 * SELECT
	 * 	個人ID
	 * FROM 人事マスタ
	 * WHERE 有効日以前で最新
	 * AND 削除フラグ = 0
	 * AND (社員名 like ?
	 * OR 社員姓 like ?
	 * OR 社員姓 + 社員名 like ?
	 * OR 社員姓 + ' ' + 社員名 like ?)
	 * </pre>
	 */
	String getQueryForEmployeeName(String targetColumn);
	
	/**
	 * 社員氏名条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index        パラメータインデックス
	 * @param employeeName 社員氏名
	 * @param targetDate   対象日
	 * @param ps           ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForEmployeeName(int index, String employeeName, Date targetDate, PreparedStatement ps)
			throws MospException;
	
	/**
	 * サブクエリ。
	 * @return
	 * <pre>
	 * SELECT
	 *  個人ID
	 * FROM 人事マスタ
	 * WHERE 削除フラグ = 0
	 * AND 有効日以前で最新
	 * AND 勤務地コード = ?
	 * </pre>
	 */
	String getQueryForWorkPlaceCode();
	
	/**
	 * サブクエリ。
	 * @return
	 * <pre>
	 * SELECT
	 *  個人ID
	 * FROM 人事マスタ
	 * WHERE 削除フラグ = 0
	 * AND 有効日以前で最新
	 * AND 雇用契約コード = ?
	 * </pre>
	 */
	String getQueryForEmploymentContractCode();
	
	/**
	 * サブクエリ。
	 * @return
	 * <pre>
	 * SELECT
	 *  個人ID
	 * FROM 人事マスタ
	 * WHERE 削除フラグ = 0
	 * AND 有効日以前で最新
	 * AND 所属コード = ?
	 * </pre>
	 */
	String getQueryForSectionCode();
	
	/**
	 * サブクエリ。
	 * @return
	 * <pre>
	 * SELECT
	 *  個人ID
	 * FROM 人事マスタ
	 * WHERE 削除フラグ = 0
	 * AND 有効日以前で最新
	 * AND
	 *  {@link SectionDaoInterface#getQueryForLowerSection(String)}
	 * </pre>
	 * @throws MospException SQLの作成に失敗した場合
	 */
	String getQueryForLowerSection() throws MospException;
	
	/**
	 * サブクエリ。
	 * @return
	 * <pre>
	 * SELECT
	 *  個人ID
	 * FROM 人事マスタ
	 * WHERE 削除フラグ = 0
	 * AND 有効日以前で最新
	 * AND 所属コード IN (
	 *  {@link SectionDaoInterface#getQueryForSectionName()}
	 * )
	 * </pre>
	 * @throws MospException SQLの作成に失敗した場合
	 */
	String getQueryForSectionName() throws MospException;
	
	/**
	 * サブクエリ。
	 * @return
	 * <pre>
	 * SELECT
	 *  個人ID
	 * FROM 人事マスタ
	 * WHERE 削除フラグ = 0
	 * AND 有効日以前で最新
	 * AND 職位コード = ?
	 * </pre>
	 */
	String getQueryForPositionCode();
	
}
