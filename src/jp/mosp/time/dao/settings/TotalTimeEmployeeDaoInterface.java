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
/**
 * 
 */
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;

/**
 * 社員勤怠集計管理トランザクションDAOインターフェース。<br>
 */
public interface TotalTimeEmployeeDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと年と月から社員勤怠集計管理情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @return 社員勤怠集計管理トランザクションDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TotalTimeEmployeeDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException;
	
	/**
	 * 集計年月で社員勤怠集計管理情報リストを取得する。<br>
	 * @param calculationYear  集計年
	 * @param calculationMonth 集計月
	 * @return 社員勤怠集計管理情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TotalTimeEmployeeDtoInterface> findForMonth(int calculationYear, int calculationMonth) throws MospException;
	
	/**
	 * 個人IDが設定されている、有効日の範囲内で情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報も含める。<br>
	 * 削除時の整合性確認等で用いる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @param personalId 個人ID
	 * @return 対象期間社員勤怠集計情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TotalTimeEmployeeDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 締日コードが設定されている、期間内の集計情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報も含める。<br>
	 * 締日変更時の整合性確認等で用いる。<br>
	 * @param startDate  期間開始日(有効日)
	 * @param endDate    期間終了日(有効日)
	 * @param cutoffCode 締日コード
	 * @return 対象期間社員勤怠集計情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TotalTimeEmployeeDtoInterface> findCalcDataTerm(String cutoffCode, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 個人IDと締状態から勤怠集計データリストを取得する。
	 * @param personalId 個人ID
	 * @param cutoffState 締状態
	 * @return 対象日時点で最新の勤怠集計データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<TotalTimeEmployeeDtoInterface> findForPersonalList(String personalId, int cutoffState) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から員勤怠集計管理マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 社員勤怠集計管理マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TotalTimeEmployeeDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 残業申請検索条件マップ
	 */
	Map<String, Object> getParamsMap();
}
