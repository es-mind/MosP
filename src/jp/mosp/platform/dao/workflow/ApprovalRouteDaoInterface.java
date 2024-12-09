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
package jp.mosp.platform.dao.workflow;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;

/**
 * 承認ルートマスタDAOインターフェース
 */
public interface ApprovalRouteDaoInterface extends BaseDaoInterface {
	
	/**
	 * ルートコードと有効日から承認ルートマスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param routeCode ルートコード
	 * @param activateDate 有効日
	 * @return 承認ルートマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApprovalRouteDtoInterface findForKey(String routeCode, Date activateDate) throws MospException;
	
	/**
	 * 承認ルートマスタ取得。
	 * <p>
	 * ルートコードと有効日から承認ルートマスタを取得する。
	 * </p>
	 * @param routeCode ルートコード
	 * @param activateDate 有効日
	 * @return 承認ルートマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApprovalRouteDtoInterface findForInfo(String routeCode, Date activateDate) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から承認ルートマスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 承認ルートマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalRouteDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から承認ルートマスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 承認ルートマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalRouteDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * ルートコードから承認ルートマスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param routeCode ルートコード
	 * @return 承認ルートマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalRouteDtoInterface> findForHistory(String routeCode) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 承認ルートマスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * サブクエリ。
	 * <p>
	 * ルート名称と有効日によるルートコードを取得するSQL。
	 * LIKE検索はあいまい検索にて
	 * </p>
	 * @return
	 * <pre>
	 * SELECT 
	 * 	ルートコード 
	 * FROM 承認ルートマスタ 
	 * WHERE 有効日以前で最新
	 * AND 削除フラグ = 0
	 * AND ルート名称 like ?
	 * </pre>
	 */
	String getQueryForRouteName();
	
}
