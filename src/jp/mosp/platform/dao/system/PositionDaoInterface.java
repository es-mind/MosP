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
package jp.mosp.platform.dao.system;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.PositionDtoInterface;

/**
 * 職位マスタDAOインターフェース
 */
public interface PositionDaoInterface extends BaseDaoInterface {
	
	/**
	 * 職位コードと有効日から職位情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param positionCode 職位コード
	 * @param activateDate 有効日
	 * @return 職位マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PositionDtoInterface findForKey(String positionCode, Date activateDate) throws MospException;
	
	/**
	 * 職位マスタ取得。
	 * <p>
	 * 職位コードと有効日から職位マスタを取得する。
	 * </p>
	 * @param positionCode 職位コード
	 * @param activateDate 有効日
	 * @return 職位マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PositionDtoInterface findForInfo(String positionCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 職位コードから職位マスタリストを取得する。
	 * </p>
	 * @param positionCode 職位コード
	 * @return 職位マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PositionDtoInterface> findForHistory(String positionCode) throws MospException;
	
	/**
	 * 対象日における職位マスタリストを取得する。<br>
	 * @param targetDate 対象日
	 * @param rangeArray 操作範囲配列
	 * @return 職位マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PositionDtoInterface> findForActivateDate(Date targetDate, String[] rangeArray) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から職位マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 職位マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PositionDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 職位マスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 職位名称条件SQLを作成する。<br>
	 * 職位名称と有効日による職位コードを取得するSQL。<br>
	 * LIKE検索はあいまい検索にて。<br>
	 * @param targetColumn 職位名称条件対象列名(職位コード)
	 * @return 職位名称条件SQL
	 */
	String getQueryForPositionName(String targetColumn);
	
	/**
	 * 操職位名称条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index        パラメータインデックス
	 * @param positionName 職位名称
	 * @param targetDate   対象日
	 * @param ps           ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForPositionName(int index, String positionName, Date targetDate, PreparedStatement ps)
			throws MospException;
	
	/**
	 * 条件による検索のための文字列。
	 * @return
	 * <pre>
	 * SELECT
	 * 	職位コード
	 * FROM 職位マスタ
	 * WHERE 有効日以前で最新
	 * AND 削除フラグ = 0
	 * AND 等級 > ?
	 * </pre>
	 */
	StringBuffer getQueryForLowerPosition();
	
	/**
	 * 操作範囲条件SQLを作成する。<br>
	 * @param rangeArray   操作範囲配列
	 * @param targetColumn 操作範囲制限対象列名
	 * @return 操作範囲条件SQL
	 */
	String getQueryForRange(String[] rangeArray, String targetColumn);
	
	/**
	 * 操作範囲条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index      パラメータインデックス
	 * @param rangeArray 操作範囲配列
	 * @param targetDate 対象日
	 * @param ps         ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForRange(int index, String[] rangeArray, Date targetDate, PreparedStatement ps) throws MospException;
	
	/**
	 * 職位等級条件SQLを作成する。<br>
	 * @param greaterEqual 以上の場合true、以下の場合false
	 * @return 職位等級条件SQL
	 */
	String getQueryForPositionGrade(boolean greaterEqual);
	
	/**
	 * 職位等級条件パラメータを設定する。<br>
	 * @param index パラメータインデックス
	 * @param positionCode 職位コード
	 * @param targetDate 対象日
	 * @param ps ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForPositionGrande(int index, String positionCode, Date targetDate, PreparedStatement ps)
			throws MospException;
	
	/**
	 * 承認者条件SQLを作成する。<br>
	 * 等級の優劣で承認者かどうかを判断する。<br>
	 * @param targetColumn 承認者制限対象列名
	 * @return 承認者条件SQL
	 */
	String getQueryForApprover(String targetColumn);
	
	/**
	 * 承認者条件パラメータを設定する。<br>
	 * 承認者職位等級は、MosP設定情報から取得する。<br>
	 * @param index      パラメータインデックス
	 * @param targetDate 対象日
	 * @param ps         ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForApprover(int index, Date targetDate, PreparedStatement ps) throws MospException;
	
	/**
	 * 等級の低い方が優れた職位であるかを確認する。
	 * @return 確認結果(true：等級の小さい方が優、false：等級の大きい方が優)
	 */
	boolean hasLowGradeAdvantage();
	
}
