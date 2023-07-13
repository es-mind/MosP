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
package jp.mosp.platform.dao.workflow;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;

/**
 * ルート適用マスタDAOインターフェース。
 */
public interface RouteApplicationDaoInterface extends BaseDaoInterface {
	
	/**
	 * ルート適用コードと有効日からルート適用マスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param routeApplicationCode ルート適用コード
	 * @param activateDate 有効日
	 * @return ルート適用マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RouteApplicationDtoInterface findForKey(String routeApplicationCode, Date activateDate) throws MospException;
	
	/**
	 * ルート適用マスタ取得。
	 * <p>
	 * ユニットコードと有効日からルート適用マスタを取得する。
	 * </p>
	 * @param routeApplicationCode ルート適用コード
	 * @param activateDate 有効日
	 * @return ルート適用マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RouteApplicationDtoInterface findForInfo(String routeApplicationCode, Date activateDate) throws MospException;
	
	/**
	 * 個人IDからルート適用マスタリストを取得する。<br>
	 * @param personalId 被承認者個人ID
	 * @param activateDate 有効日
	 * @param workflowType フロー区分
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RouteApplicationDtoInterface findForPersonalId(String personalId, Date activateDate, int workflowType)
			throws MospException;
	
	/**
	 * 選択された各マスタのコードの組み合わせに紐づいたルート適用マスタリストを取得する。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param positionCode 職位コード
	 * @param activateDate 有効日
	 * @param workflowType フロー区分
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RouteApplicationDtoInterface> findForApproverSection(String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode, Date activateDate, int workflowType) throws MospException;
	
	/**
	 * 全てのルート適用マスタリストを取得する。<br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 有効日の範囲で検索する。但し、有効日From及び有効日Toは、検索対象に含まれない。<br>
	 * 人事・勤怠全てのルート適用を取得する。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @return 全てのルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RouteApplicationDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日からルート適用マスタリストを取得する。
	 * 人事・勤怠全てのルート適用を取得する。<br>
	 * </p>
	 * @param activateDate 有効日
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RouteApplicationDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件からルート適用マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RouteApplicationDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * ルート適用コードからルート適用マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param routeApplicationCode ルート適用コード
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RouteApplicationDtoInterface> findForHistory(String routeApplicationCode) throws MospException;
	
	/**
	 * マスタ組合による適用範囲が設定されている、有効日時点で最新の有効な情報を取得する。<br>
	 * @param activateDate           有効日
	 * @param workflowType           フロー区分
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return ルート適用マスタ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RouteApplicationDtoInterface findForMaster(Date activateDate, int workflowType, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 承認ユニットマスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * サブクエリ。
	 * <p>
	 * ルート適用名称と有効日によるルート適用コードを取得するSQL。
	 * LIKE検索はあいまい検索にて
	 * </p>
	 * @return
	 * <pre>
	 * SELECT 
	 * 	ルート適用コード 
	 * FROM ルート適用マスタ 
	 * WHERE 有効日以前で最新
	 * AND 削除フラグ = 0
	 * AND ルート適用名称 like ?
	 * </pre>
	 */
	String getQueryForRouteApplicationName();
	
	/**
	 * ルート適用コードにつき、有効日より前の情報を取得する。<br>
	 * 有効無効は問わない。<br>
	 * 履歴削除により最新となる情報を取得する場合等に用いる。<br>
	 * @param routeApplicationCode ルート適用コード
	 * @param activateDate    有効日
	 * @return ルート適用マスタ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RouteApplicationDtoInterface findFormerInfo(String routeApplicationCode, Date activateDate) throws MospException;
	
	/**
	 * ルート適用コードにつき、有効日より後の情報を取得する。<br>
	 * 有効無効は問わない。<br>
	 * 対象情報が有効である範囲を取得する場合等に用いる。<br>
	 * @param routeApplicationCode ルート適用コード
	 * @param activateDate    有効日
	 * @return ルート適用マスタ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RouteApplicationDtoInterface findLatterInfo(String routeApplicationCode, Date activateDate) throws MospException;
	
	/**
	 * 個人IDが設定されている、有効日の範囲内で有効な情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報は、含まれない。<br>
	 * 設定重複確認等で用いる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @param personalId 個人ID
	 * @param workflowType フロー区分
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RouteApplicationDtoInterface> findPersonDuplicated(Date startDate, Date endDate, String personalId,
			int workflowType) throws MospException;
	
	/**
	 * 個人IDが設定されている、有効日の範囲内で情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報も含まる。<br>
	 * 削除時の整合性確認等で用いる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @param personalId 個人ID
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RouteApplicationDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * マスタ組合せによる適用範囲が設定されている、有効日より後で有効な情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報は、含まれない。<br>
	 * 設定重複確認等で用いる。<br>
	 * @param startDate              開始日(有効日)
	 * @param endDate                終了日(有効日)
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @param workflowType           フロー区分
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RouteApplicationDtoInterface> findMasterDuplicated(Date startDate, Date endDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode, int workflowType)
			throws MospException;
	
}
