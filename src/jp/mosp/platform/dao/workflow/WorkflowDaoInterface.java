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
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフローDAOインターフェース。<br>
 */
public interface WorkflowDaoInterface extends BaseDaoInterface {
	
	/**
	 * ワークフロー番号からワークフロー情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return ワークフローDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkflowDtoInterface findForKey(long workflow) throws MospException;
	
	/**
	 * ワークフロー番号からワークフロー情報群を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、空のマップを返す。<br>
	 * @param workflowSet ワークフロー番号のセット
	 * @return ワークフロー群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<Long, WorkflowDtoInterface> findForInKey(Set<Long> workflowSet) throws MospException;
	
	/**
	 * 現在のワークフロー番号の最大値の次を取得する。
	 * @return	ワークフロー番号
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	long nextWorkflow() throws MospException;
	
	/**
	 * サブクエリ取得。
	 * @param useStage 段階利用フラグ
	 * @param useStatus 状況利用フラグ
	 * @param useRoute ルートコード利用フラグ
	 * @param useFunction 機能コード利用フラグ
	 * @return 段階、状況、ルートコード、機能コードからワークフロー番号を取得するサブクエリ。
	 */
	String getSubQueryForSetting(boolean useStage, boolean useStatus, boolean useRoute, boolean useFunction);
	
	/**
	 * サブクエリ取得。
	 * 状況が下書であるものを除く。
	 * @return 状況からワークフロー番号を取得するサブクエリ。
	 */
	String getSubQueryForNotEqualDraft();
	
	/**
	 * サブクエリ取得。
	 * 状況が取下であるものを除く。
	 * @return 状況からワークフロー番号を取得するサブクエリ。
	 */
	String getSubQueryForNotEqualWithdrawn();
	
	/**
	 * サブクエリを取得する。<br>
	 * 状況が差戻で段階が0(一次戻)であるものを除く。<br>
	 * @return 状況と段階からワークフロー番号を取得するサブクエリ。
	 */
	String getSubQueryForNotEqualFirstReverted();
	
	/**
	 * 申請済ワークフロー番号を抽出するサブクエリを取得する。<br>
	 * 申請済ワークフローであるもの(一次戻、下書、取下以外)を取得する。<br>
	 * @param column ワークフロー番号列名
	 * @return 申請済ワークフロー番号を抽出するサブクエリ。
	 */
	String getSubQueryForApplied(String column);
	
	/**
	 * 承認済ワークフロー番号を抽出するサブクエリを取得する。<br>
	 * @param column ワークフロー番号列名
	 * @return 承認済ワークフロー番号を抽出するサブクエリ
	 */
	String getSubQueryForCompleted(String column);
	
	/**
	 * ワークフロー番号からワークフロー情報履歴を取得する。<br>
	 * @param workflow ワークフロー番号
	 * @return ワークフローリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> findForHistory(long workflow) throws MospException;
	
	/**
	 * 承認可能ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * 次の何れかに当てはまるワークフローが、承認可能と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が未承認
	 * </li><li>
	 * ワークフロー状況が承認
	 * </li><li>
	 * ワークフロー状況が差戻で、ワークフロー段階が0でない
	 * </li><li>
	 * ワークフロー状況が承認解除
	 * </li></ul>
	 * <br>
	 * @param functionCodeSet 機能コードセット
	 * @return 未承認ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> findApprovable(Set<String> functionCodeSet) throws MospException;
	
	/**
	 * 承認可能ワークフロー情報リストを取得する。<br>
	 * 機能コードセットに含まれるワークフロー情報が抽出される。<br>
	 * 次の何れかに当てはまるワークフローが、承認可能と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が未承認
	 * </li><li>
	 * ワークフロー状況が承認
	 * </li><li>
	 * ワークフロー状況が差戻で、ワークフロー段階が0でない
	 * </li><li>
	 * ワークフロー状況が承認解除
	 * </li><li>
	 * ワークフロー状況が解除申
	 * </li></ul>
	 * @param fromDate 期間自
	 * @param toDate 期間至
	 * @return ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> findApprovable(Date fromDate, Date toDate) throws MospException;
	
	/**
	 * 一時戻ワークフロー情報リストを取得する。<br>
	 * @param fromDate 期間自
	 * @param toDate 期間至
	 * @return ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> findFirstReverted(Date fromDate, Date toDate) throws MospException;
	
	/**
	 * ワークフロー情報リストを取得する。<br>
	 * 設定された条件で、検索を行う。<br>
	 * @param fromDate         期間自
	 * @param toDate           期間至
	 * @param functionCodeSet  機能コードセット
	 * @param workflowStateSet ワークフロー状況セット
	 * @return 有効ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> findForCondition(Date fromDate, Date toDate, Set<String> functionCodeSet,
			Set<String> workflowStateSet) throws MospException;
	
	/**
	 * ワークフロー情報リストを取得する。<br>
	 * 設定された条件で、検索を行う。<br>
	 * 個人IDで申請を検索する。<br>
	 * @param personalId 申請者個人ID
	 * @param fromDate         期間自
	 * @param toDate           期間至
	 * @param functionCodeSet  機能コードセット
	 * @return 有効ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> findForCondition(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException;
	
	/**
	 * ワークフロー情報リストを取得する。<br>
	 * 設定された条件で、検索を行う。<br>
	 * 個人IDで申請を検索する。<br>
	 * @param personalId 申請者個人ID
	 * @param fromDate         期間自
	 * @param toDate           期間至
	 * @return 有効ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<Long, WorkflowDtoInterface> findForCondition(String personalId, Date fromDate, Date toDate)
			throws MospException;
	
	/**
	 * ワークフロー情報リストを取得する。<br>
	 * 設定された条件で、検索を行う。<br>
	 * @param personalId 個人ID
	 * @param fromDate 期間自
	 * @param toDate 期間至
	 * @param functionCodeSet 機能コードセット
	 * @param workflowStateSet ワークフロー状況セット
	 * @return 有効ワークフロー情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowDtoInterface> findForCondition(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet, Set<String> workflowStateSet) throws MospException;
	
	/**
	 * 個人ID及びワークフロー対象日が合致する
	 * ワークフロー情報群を取得する。<br>
	 * <br>
	 * @param personalId   個人ID
	 * @param workflowDate ワークフロー対象日
	 * @return ワークフロー情報群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<Long, WorkflowDtoInterface> findForPersonAndDay(String personalId, Date workflowDate) throws MospException;
	
}
