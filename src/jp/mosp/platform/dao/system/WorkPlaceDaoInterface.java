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
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;

/**
 * 勤務地マスタDAOインターフェース。
 */
public interface WorkPlaceDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤務地コードと有効日から勤務地情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param activateDate           有効日
	 * @return 勤務地マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkPlaceDtoInterface findForKey(String workPlaceCode, Date activateDate) throws MospException;
	
	/**
	 * 勤務地マスタ取得。
	 * <p>
	 * 勤務地コードと有効日から勤務地マスタを取得する。
	 * </p>
	 * @param workPlaceCode 勤務地コード
	 * @param activateDate 有効日
	 * @return 勤務地マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkPlaceDtoInterface findForInfo(String workPlaceCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 勤務地コードから勤務地マスタリストを取得する。
	 * </p>
	 * @param workPlaceCode 勤務地コード
	 * @return 所属マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkPlaceDtoInterface> findForHistory(String workPlaceCode) throws MospException;
	
	/**
	 * 対象日における勤務地マスタリストを取得する。<br>
	 * @param targetDate 対象日
	 * @param rangeArray 操作範囲配列
	 * @return 勤務地マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkPlaceDtoInterface> findForActivateDate(Date targetDate, String[] rangeArray) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件による勤務地マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 勤務地マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkPlaceDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 勤務地マスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 操作範囲条件SQLを作成する。<br>
	 * 操作範囲対象列が操作範囲配列内のいずれかの値のレコードのみを抽出する条件を作成する。<br>
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
	 * @param ps         ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForRange(int index, String[] rangeArray, PreparedStatement ps) throws MospException;
	
}
