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
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * 承認ユニットマスタDAOインターフェース。
 */
public interface ApprovalUnitDaoInterface extends BaseDaoInterface {
	
	/**
	 * ユニットコードと有効日から承認ユニットマスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param unitCode ユニットコード
	 * @param activateDate 有効日
	 * @return 承認ユニットマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApprovalUnitDtoInterface findForKey(String unitCode, Date activateDate) throws MospException;
	
	/**
	 * 承認ユニットマスタ取得。
	 * <p>
	 * ユニットコードと有効日から承認ユニットマスタを取得する。
	 * </p>
	 * @param unitCode ユニットコード
	 * @param activateDate 有効日
	 * @return 承認ユニットマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApprovalUnitDtoInterface findForInfo(String unitCode, Date activateDate) throws MospException;
	
	/**
	 * 承認者個人IDから承認ユニットマスタリストを取得する。
	 * @param approverPersonalId 承認者個人ID
	 * @param activateDate 有効日
	 * @return 承認ユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalUnitDtoInterface> findForApproverPersonalId(String approverPersonalId, Date activateDate)
			throws MospException;
	
	/**
	 * 承認者所属コード、承認者職位コードから承認ユニットマスタリストを取得する。
	 * @param approverSectionCode 承認者所属コード
	 * @param approverPositionCode 承認者職位コード
	 * @param activateDate 有効日
	 * @return 承認ユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalUnitDtoInterface> findForApproverSection(String approverSectionCode, String approverPositionCode,
			Date activateDate) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から承認ユニットマスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 承認ユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalUnitDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から承認ユニットマスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 承認ユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalUnitDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * ユニットコードから承認ユニットマスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param unitCode ユニットコード
	 * @return 承認ユニットマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApprovalUnitDtoInterface> findForHistory(String unitCode) throws MospException;
	
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
	List<ApprovalUnitDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 承認ユニットマスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * サブクエリ。
	 * <p>
	 * ユニット名称と有効日によるユニットコードを取得するSQL。
	 * LIKE検索はあいまい検索にて
	 * </p>
	 * @return
	 * <pre>
	 * SELECT 
	 * 	ユニットコード 
	 * FROM 承認ユニットマスタ 
	 * WHERE 有効日以前で最新
	 * AND 削除フラグ = 0
	 * AND ユニット名称 like ?
	 * </pre>
	 */
	String getQueryForUnitName();
	
}
