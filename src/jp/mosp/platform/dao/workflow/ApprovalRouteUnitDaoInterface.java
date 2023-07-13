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
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;

/**
 * 承認ルートユニットマスタDAOインターフェース
 */
public interface ApprovalRouteUnitDaoInterface extends BaseDaoInterface {
	
	/**
	 * ルートコードと有効日、承認段階から承認ルートユニットマスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param routeCode ルートコード
	 * @param activateDate 有効日
	 * @param approvalStage 承認段階
	 * @return 承認ルートユニットマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApprovalRouteUnitDtoInterface findForKey(String routeCode, Date activateDate, int approvalStage)
			throws MospException;
	
	/**
	 * 承認ルートユニットマスタ取得。
	 * <p>
	 * ルートコードと有効日、承認段階から承認ルートユニットマスタを取得する。
	 * </p>
	 * @param routeCode ルートコード
	 * @param activateDate 有効日
	 * @param approvalStage 承認段階
	 * @return 承認ルートユニットマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApprovalRouteUnitDtoInterface findForInfo(String routeCode, Date activateDate, int approvalStage)
			throws MospException;
	
	/**
	 * ルートコードと対象日が合致した承認ルートユニットマスタリストを取得する。<br>
	 * 承認階層でソートされる。<br>
	 * @param routeCode  ルートコード
	 * @param targetDate 対象日
	 * @return 承認ルートユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalRouteUnitDtoInterface> findForRouteList(String routeCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * ルートコードと承認階層数から合致した承認ルートユニットマスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param routeCode ルートコード
	 * @param approvalStage 承認段階
	 * @return 承認ルートマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalRouteUnitDtoInterface> findForHistory(String routeCode, int approvalStage) throws MospException;
	
	/**
	 * ユニットコードによる承認ルートユニットマスタリスト取得。
	 * @param unitCode ユニットコード
	 * @param activateDate 有効日
	 * @return 承認ルートユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalRouteUnitDtoInterface> findForApprovalUnit(String unitCode, Date activateDate) throws MospException;
	
	/**
	 * 承認ルートユニットマスタリストを取得する。<br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 有効日の範囲で検索する。但し、有効日From及び有効日Toは、検索対象に含まれない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @return 承認ルートユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalRouteUnitDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から承認ルートユニットマスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 承認ルートユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalRouteUnitDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 承認ルートマスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 条件による検索のための文字列。
	 * <p>
	 * 最大有効日レコードのクエリを取得する。
	 * @return ユニットコードに紐づく有効日が最大であるレコード取得クエリ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StringBuffer getQueryForMaxActivateDate() throws MospException;
	
	/**
	 * サブクエリ。
	 * <p>
	 * ユニットコードと有効日によるルートコードを取得するSQL。
	 * LIKE検索はあいまい検索にて
	 * </p>
	 * @return
	 * <pre>
	 * SELECT 
	 * 	ルートコード 
	 * FROM 承認ルートユニットマスタ 
	 * WHERE 有効日以前で最新
	 * AND 削除フラグ = 0
	 * AND ユニットコード like ?
	 * </pre>
	 */
	String getQueryForUnitCode();
	
}
