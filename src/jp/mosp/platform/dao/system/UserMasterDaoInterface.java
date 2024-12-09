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
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザマスタDAOインターフェース
 */
public interface UserMasterDaoInterface extends BaseDaoInterface {
	
	/**
	 * ユーザIDと有効日からユーザ情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @return ユーザマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	UserMasterDtoInterface findForKey(String userId, Date activateDate) throws MospException;
	
	/**
	 * ユーザマスタ取得。
	 * <p>
	 * ユーザIDと有効日からユーザマスタDTOを取得。
	 * </p>
	 * @param userId ユーザID
	 * @param activateDate 有効日
	 * @return ユーザマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	UserMasterDtoInterface findForInfo(String userId, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * ユーザIDからユーザマスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param userId ユーザID
	 * @return ユーザマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<UserMasterDtoInterface> findForHistory(String userId) throws MospException;
	
	/**
	 * 個人IDと有効日からユーザ情報リストを取得する。<br>
	 * 1個人IDにつき複数のユーザIDを持てるため、返値はリストとする。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return ユーザマスタDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<UserMasterDtoInterface> findForPersonalId(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 個人IDでユーザ情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @return ユーザマスタDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<UserMasterDtoInterface> findForPersonalId(String personalId) throws MospException;
	
	/**
	 * 条件による検索を行う。<br>
	 * @param param 検索条件
	 * @return ユーザマスタDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<UserMasterDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return ユーザマスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 社員名からユーザIDを抽出するクエリを取得する。<br>
	 * @return 社員名からユーザIDを抽出するクエリ
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	String getQueryForEmployeeName() throws MospException;
	
	/**
	 * 承認ロール条件SQLを作成する。<br>
	 * @param targetColumn 検索対象個人ID列
	 * @return 承認ロール条件SQL
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	String getQueryForApprover(String targetColumn) throws MospException;
	
	/**
	 * 承認ロール条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index      パラメータインデックス
	 * @param targetDate 対象日
	 * @param ps         ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForApprover(int index, Date targetDate, PreparedStatement ps) throws MospException;
	
}
