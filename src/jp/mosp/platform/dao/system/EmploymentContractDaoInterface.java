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
package jp.mosp.platform.dao.system;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;

/**
 * 雇用契約マスタDAOインターフェース。<br>
 */
public interface EmploymentContractDaoInterface extends BaseDaoInterface {
	
	/**
	 * 雇用契約コードと有効日から雇用契約情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param activateDate           有効日
	 * @return 雇用契約マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	EmploymentContractDtoInterface findForKey(String employmentContractCode, Date activateDate) throws MospException;
	
	/**
	 * 雇用契約マスタ取得。
	 * <p>
	 * 雇用契約コードと有効日から雇用契約マスタを取得する。
	 * </p>
	 * @param employmentContractCode 雇用契約コード
	 * @param activateDate 有効日
	 * @return 雇用契約マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	EmploymentContractDtoInterface findForInfo(String employmentContractCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 雇用契約コードから雇用契約マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @return 雇用契約マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<EmploymentContractDtoInterface> findForHistory(String employmentContractCode) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から雇用契約マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @param rangeArray   操作範囲配列
	 * @return 雇用契約マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<EmploymentContractDtoInterface> findForActivateDate(Date activateDate, String[] rangeArray)
			throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から雇用契約マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 雇用契約マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<EmploymentContractDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 雇用契約マスタ検索条件マップ
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
