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
package jp.mosp.platform.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフローに関する有用なメソッドを提供する。<br>
 */
public class WorkflowUtility {
	
	/**
	 * 承認状況(一次戻)。<br>
	 * {@link #isMatch(WorkflowDtoInterface, Set)}で用いられる。<br>
	 */
	protected static final String	CODE_STATUS_FIRST_REVERT		= "30";
	
	/**
	 * 承認状況(二次以上戻)。<br>
	 * {@link #isMatch(WorkflowDtoInterface, Set)}で用いられる。<br>
	 */
	protected static final String	CODE_STATUS_OVER_SECOND_REVERT	= "31";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private WorkflowUtility() {
		// 処理無し
	}
	
	/**
	 * コレクションからワークフロー番号群を取得する。<br>
	 * コレクションの要素は、コレクションであっても良い。<br>
	 * コレクションの要素、或いはコレクションの要素であるのコレクションの要素は、
	 * {@link WorkflowNumberDtoInterface }を実装している必要がある。<br>
	 * @param collections コレクション配列
	 * @return ワークフロー番号群
	 */
	public static Set<Long> getWorkflowSet(Collection<?>... collections) {
		// ワークフロー番号群を準備
		Set<Long> set = new TreeSet<Long>();
		// コレクション毎に処理
		for (Collection<?> collection : collections) {
			// コレクションの要素毎に処理
			for (Object obj : collection) {
				// コレクションの要素がコレクションでない場合
				if (obj instanceof Collection<?> == false) {
					// ワークフロー番号群にワークフロー番号を設定
					set.add(getWorkflowNumber(obj));
					// 次の要素へ
					continue;
				}
				// キャスト
				Collection<? extends WorkflowNumberDtoInterface> dtos = MospUtility.castObject(obj);
				// コレクションの要素毎に処理
				for (WorkflowNumberDtoInterface dto : dtos) {
					// ワークフロー番号群にワークフロー番号を設定
					set.add(dto.getWorkflow());
				}
			}
		}
		// ワークフロー番号群を取得
		return set;
	}
	
	/**
	 * オブジェクトからワークフロー番号を取得する。<br>
	 * 引数のオブジェクトは{@link WorkflowNumberDtoInterface }を実装している必要がある。<br>
	 * @param obj オブジェクト
	 * @return ワークフロー番号
	 */
	public static long getWorkflowNumber(Object obj) {
		// キャスト 
		WorkflowNumberDtoInterface dto = PlatformUtility.castObject(obj);
		// ワークフロー番号を取得
		return dto.getWorkflow();
	}
	
	/**
	 * 対象ワークフローの承認状況が、申請済であるかを確認する。<br>
	 * 下書、取下、一次戻以外の場合は、申請済であると判断する。<br>
	 * <br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：申請済である、false：そうでない)
	 */
	public static boolean isApplied(WorkflowDtoInterface dto) {
		if (isDraft(dto) || isWithDrawn(dto) || isFirstReverted(dto)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 対象ワークフローが承認可能であるかを確認する。<br>
	 * 次の何れかに当てはまるワークフローが、承認可能と判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が未承認
	 * </li><li>
	 * ワークフロー状況が承認
	 * </li><li>
	 * ワークフロー状況が差戻で、ワークフローが一次戻でない
	 * </li><li>
	 * ワークフロー状況が承認解除
	 * </li></ul>
	 * <br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認可能である、false：承認可能でない)
	 */
	public static boolean isApprovable(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBeanと同じ
		// ワークフロー状況確認(未承認)
		if (isApply(dto)) {
			return true;
		}
		// ワークフロー状況確認(承認)
		if (isApproved(dto)) {
			return true;
		}
		// ワークフロー状況確認(承認解除)
		if (isCancel(dto)) {
			return true;
		}
		// ワークフロー状況確認(差戻で、一次戻でない)
		if (isReverted(dto)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 対象ワークフローが未承認であるかを確認する。<br>
	 * 次の何れかに当てはまるワークフローが、未承認であると判断される。<br>
	 * <ul><li>
	 * ワークフロー状況が解除申
	 * </li><li>
	 * ワークフロー状況が承認解除
	 * </li><li>
	 * ワークフロー状況が差戻(一次戻に限らず)
	 * </li><li>
	 * ワークフロー状況が承認
	 * </li><li>
	 * ワークフロー状況が未承認
	 * </li></ul>
	 * <br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：未承認である、false：未承認でない)
	 */
	public static boolean isNotApproved(WorkflowDtoInterface dto) {
		// 解除申の場合
		if (WorkflowUtility.isCancelApply(dto)) {
			return true;
		}
		// 解除申(取下希望)である場合
		if (WorkflowUtility.isCancelWithDrawnApply(dto)) {
			return true;
		}
		// 承認解除の場合
		if (WorkflowUtility.isCancel(dto)) {
			return true;
		}
		// 差戻の場合
		if (WorkflowUtility.isReverted(dto)) {
			return true;
		}
		// 承認の場合
		if (WorkflowUtility.isApproved(dto)) {
			return true;
		}
		// 未承認の場合
		if (WorkflowUtility.isApply(dto)) {
			return true;
		}
		// 未承認でない
		return false;
	}
	
	/**
	 * 対象ワークフロー番号情報の承認状況が承認済であるかを確認する。<br>
	 * 情報に不備がある場合は、承認済でないと判断する。<br>
	 * @param dto       対象ワークフロー番号情報
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 * @return 確認結果(true：承認済である、false：そうでない)
	 */
	public static boolean isCompleted(WorkflowNumberDtoInterface dto, Map<Long, WorkflowDtoInterface> workflows) {
		// 対象ワークフロー番号情報かワークフロー情報群が存在しない場合
		if (MospUtility.isEmpty(dto, workflows)) {
			// 承認済でないと判断
			return false;
		}
		// 対象ワークフロー番号情報の承認状況が承認済であるかを確認
		return isCompleted(workflows.get(dto.getWorkflow()));
	}
	
	/**
	 * 対象ワークフローの承認状況が合致するかを確認する。<br>
	 * <br>
	 * 承認済申請のみである場合、承認済であるかを確認する。<br>
	 * 申請済申請含む場合、申請済(一次戻含む)であるかを確認する。<br>
	 * <br>
	 * @param dto         対象ワークフロー情報
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @return 確認結果(true：承認状況が合致する、false：合致しない)
	 */
	public static boolean isStatusMatch(WorkflowDtoInterface dto, boolean isCompleted) {
		// 承認済申請のみである場合
		if (isCompleted) {
			// 承認済であるかを確認
			return WorkflowUtility.isCompleted(dto);
		}
		// 申請済(一次戻含む)であるかを確認
		return WorkflowUtility.isApplied(dto) || WorkflowUtility.isFirstReverted(dto);
	}
	
	/**
	 * 対象ワークフローの承認状況が、承認済であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認済である、false：そうでない)
	 */
	public static boolean isCompleted(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBean.isCompletedと同じ
		return isTheStatus(dto, PlatformConst.CODE_STATUS_COMPLETE);
	}
	
	/**
	 * 対象ワークフローの承認状況が、解除申であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：解除申である、false：そうでない)
	 */
	public static boolean isCancelApply(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_CANCEL_APPLY);
	}
	
	/**
	 * 対象ワークフローの承認状況が、解除申であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：解除申である、false：そうでない)
	 */
	public static boolean isCancelWithDrawnApply(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY);
	}
	
	/**
	 * 対象ワークフローの承認状況が、承認解除であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認解除である、false：そうでない)
	 */
	public static boolean isCancel(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_CANCEL);
	}
	
	/**
	 * 対象ワークフローが、差戻(一次戻以外)であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：差戻(一次戻以外)である、false：そうでない)
	 */
	public static boolean isReverted(WorkflowDtoInterface dto) {
		// ワークフロー情報の承認状況(差戻)を確認
		if (isTheStatus(dto, PlatformConst.CODE_STATUS_REVERT) == false) {
			return false;
		}
		// 一次戻であるかを確認
		return isFirstReverted(dto) == false;
	}
	
	/**
	 * 対象ワークフローの承認状況が、承認であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認である、false：そうでない)
	 */
	public static boolean isApproved(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_APPROVED);
	}
	
	/**
	 * 対象ワークフローの承認状況が、未承認であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：未承認である、false：そうでない)
	 */
	public static boolean isApply(WorkflowDtoInterface dto) {
		return isTheStatus(dto, PlatformConst.CODE_STATUS_APPLY);
	}
	
	/**
	 * 対象ワークフローの承認状況が、取下であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：取下である、false：そうでない)
	 */
	public static boolean isWithDrawn(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBeanと同じ
		return isTheStatus(dto, PlatformConst.CODE_STATUS_WITHDRAWN);
	}
	
	/**
	 * 対象ワークフローの承認状況が、下書であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：下書である、false：そうでない)
	 */
	public static boolean isDraft(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBeanと同じ
		return isTheStatus(dto, PlatformConst.CODE_STATUS_DRAFT);
	}
	
	/**
	 * 対象ワークフローが、一次戻であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：一次戻である、false：そうでない)
	 */
	public static boolean isFirstReverted(WorkflowDtoInterface dto) {
		// TODO WorkflowIntegrateBean.isFirstRevertedと同じ
		// ワークフロー情報の承認状況(差戻)を確認
		if (isTheStatus(dto, PlatformConst.CODE_STATUS_REVERT) == false) {
			return false;
		}
		// ワークフロー情報の段階(0)を確認
		return dto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO;
	}
	
	/**
	 * 対象ワークフローの承認状況が、その承認状況であるかを確認する。<br>
	 * @param dto    対象ワークフロー情報
	 * @param status その承認状況
	 * @return 確認結果(true：その承認状況である、false：そうでない)
	 */
	protected static boolean isTheStatus(WorkflowDtoInterface dto, String status) {
		if (dto == null || dto.getWorkflowStatus() == null) {
			return false;
		}
		return dto.getWorkflowStatus().equals(status);
	}
	
	/**
	 * 対象ワークフローの承認状況が承認状況群に含まれるかを確認する。<br>
	 * @param dto      ワークフロー情報
	 * @param statuses 承認状況群
	 * @return 確認結果(true：承認状況群に含まれる、false：承認状況群に含まれない)
	 */
	public static boolean isMatch(WorkflowDtoInterface dto, Set<String> statuses) {
		// ワークフロー情報か承認状況群が存在しない場合
		if (MospUtility.isEmpty(dto, statuses)) {
			// 承認状況群に含まれないと判断
			return false;
		}
		// 承認状況を取得
		String status = dto.getWorkflowStatus();
		// 承認状況群に含まれる場合
		if (statuses.contains(status)) {
			// 承認状況群に含まれると判断
			return true;
		}
		// 一次戻である場合
		if (isFirstReverted(dto)) {
			// 一次戻が承認状況群に含まれるかを確認
			return statuses.contains(CODE_STATUS_FIRST_REVERT);
		}
		// 二次以上戻である場合
		if (isReverted(dto)) {
			// 二次以上戻が承認状況群に含まれるかを確認
			return statuses.contains(CODE_STATUS_OVER_SECOND_REVERT);
		}
		// 承認状況群に含まれないと判断
		return false;
	}
	
	/**
	 * 申請情報群から対象となる承認状況の申請情報群を取得する。<br>
	 * @param dtos      申請情報リスト
	 * @param workflows ワークフロー情報群
	 * @param statuses  対象となる承認状況群
	 * @return 申請情報群
	 */
	public static <T extends WorkflowNumberDtoInterface> Set<T> getRequests(Collection<T> dtos,
			Map<Long, WorkflowDtoInterface> workflows, Set<String> statuses) {
		// 申請情報リストを準備
		Set<T> requests = new LinkedHashSet<T>();
		// 引数が存在しない場合
		if (MospUtility.isEmpty(dtos, workflows, statuses)) {
			// 空のリストを取得
			return requests;
		}
		// 申請情報毎に処理
		for (T dto : dtos) {
			// 対象ワークフローの承認状況が承認状況群に含まれる場合
			if (WorkflowUtility.isMatch(workflows.get(dto.getWorkflow()), statuses)) {
				// 申請情報リストに設定
				requests.add(dto);
			}
		}
		// 申請情報リストを取得
		return requests;
	}
	
	/**
	 * 対象申請情報から、申請情報を取得する。<br>
	 * 対象申請情報が対象となる承認状況群に合致しない場合はnullを返す。<br>
	 * <br>
	 * @param dto       抽出対象申請情報
	 * @param workflows ワークフロー情報群
	 * @param statuses  対象となる承認状況群
	 * @return 申請情報
	 */
	public static <T extends WorkflowNumberDtoInterface> T getRequest(T dto, Map<Long, WorkflowDtoInterface> workflows,
			Set<String> statuses) {
		// 引数が存在しない場合
		if (MospUtility.isEmpty(dto, workflows, statuses)) {
			// nullを取得
			return null;
		}
		// 対象ワークフローの承認状況が承認状況群に含まれる場合
		if (WorkflowUtility.isMatch(workflows.get(dto.getWorkflow()), statuses)) {
			// 申請情報を取得
			return dto;
		}
		// nullを取得(対象申請情報が対象となる承認状況群に合致しない場合)
		return null;
	}
	
	/**
	 * 有効な承認状況群(下書と取下以外)を取得する。<br>
	 * <br>
	 * 次の承認状況が含まれる(一次戻も含まれる)。<br>
	 * ・承認済<br>
	 * ・解除申(取下希望)<br>
	 * ・解除申<br>
	 * ・承認解除<br>
	 * ・差戻<br>
	 * ・承認<br>
	 * ・未承認<br>
	 * <br>
	 * @return 有効な承認状況群(下書と取下以外)
	 */
	public static Set<String> getEffectiveStatuses() {
		// 承認状況群を取得
		Set<String> statuses = getAllStatuses();
		// 下書と取下を除去
		statuses.remove(PlatformConst.CODE_STATUS_DRAFT);
		statuses.remove(PlatformConst.CODE_STATUS_WITHDRAWN);
		// 有効な承認状況群を取得
		return statuses;
	}
	
	/**
	 * 承認済以外の有効な承認状況群(下書と取下以外)を取得する。<br>
	 * <br>
	 * 次の承認状況が含まれる(一次戻も含まれる)。<br>
	 * ・解除申(取下希望)<br>
	 * ・解除申<br>
	 * ・承認解除<br>
	 * ・差戻<br>
	 * ・承認<br>
	 * ・未承認<br>
	 * <br>
	 * @return 承認済以外の有効な承認状況群(下書と取下以外)
	 */
	public static Set<String> getEffectiveWithoutCompletedStatuses() {
		// 承認状況群を取得
		Set<String> statuses = getEffectiveStatuses();
		// 承認済を除去
		statuses.remove(PlatformConst.CODE_STATUS_COMPLETE);
		// 承認済以外の有効な承認状況群を取得
		return statuses;
	}
	
	/**
	 * 申請済承認状況群(一次戻と下書と取下以外)を取得する。<br>
	 * <br>
	 * 次の承認状況が含まれる。<br>
	 * ・承認済<br>
	 * ・解除申(取下希望)<br>
	 * ・解除申<br>
	 * ・承認解除<br>
	 * ・二次以上戻<br>
	 * ・承認<br>
	 * ・未承認<br>
	 * <br>
	 * @return 申請済承認状況群(一次戻と下書と取下以外)
	 */
	public static Set<String> getAppliedStatuses() {
		// 承認状況群を取得
		Set<String> statuses = getAllStatuses();
		// 差戻と下書と取下を除去
		statuses.remove(PlatformConst.CODE_STATUS_DRAFT);
		statuses.remove(PlatformConst.CODE_STATUS_WITHDRAWN);
		statuses.remove(PlatformConst.CODE_STATUS_REVERT);
		// 二次以上戻を設定
		statuses.add(CODE_STATUS_OVER_SECOND_REVERT);
		// 申請済承認状況群を取得
		return statuses;
	}
	
	/**
	 * 承認済承認状況群を取得する。<br>
	 * <br>
	 * 次の承認状況が含まれる。<br>
	 * ・承認済<br>
	 * <br>
	 * @return 承認済承認状況群
	 */
	public static Set<String> getCompletedStatuses() {
		// 承認状況群を準備
		Set<String> statuses = new HashSet<String>();
		// 承認状況を設定
		statuses.add(PlatformConst.CODE_STATUS_COMPLETE);
		// 承認済承認状況群を取得
		return statuses;
	}
	
	/**
	 * 承認状況群を取得する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済承認状況群を取得、false：申請済承認状況群を取得)
	 * @return 承認状況群
	 */
	public static Set<String> getCompletedOrAppliedStatuses(boolean isCompleted) {
		// 承認状況群を取得
		return isCompleted ? getCompletedStatuses() : getAppliedStatuses();
	}
	
	/**
	 * 取下以外の承認状況群を取得する。<br>
	 * <br>
	 * 次の承認状況が含まれる。<br>
	 * ・承認済<br>
	 * ・解除申(取下希望)<br>
	 * ・解除申<br>
	 * ・承認解除<br>
	 * ・差戻<br>
	 * ・承認<br>
	 * ・未承認<br>
	 * ・下書<br>
	 * <br>
	 * @return 承認状況群
	 */
	public static Set<String> getStatusesExceptWithDrawn() {
		// 承認状況群を取得
		Set<String> statuses = getAllStatuses();
		// 取下を除去
		statuses.remove(PlatformConst.CODE_STATUS_WITHDRAWN);
		// 申請済承認状況群を取得
		return statuses;
	}
	
	/**
	 * 承認状況群を取得する。<br>
	 * <br>
	 * 次の承認状況が含まれる。<br>
	 * ・承認済<br>
	 * ・解除申(取下希望)<br>
	 * ・解除申<br>
	 * ・取下<br>
	 * ・承認解除<br>
	 * ・差戻<br>
	 * ・承認<br>
	 * ・未承認<br>
	 * ・下書<br>
	 * <br>
	 * @return 承認状況群
	 */
	public static Set<String> getAllStatuses() {
		// 承認状況群を準備
		Set<String> statuses = new HashSet<String>();
		// 承認状況を設定
		statuses.add(PlatformConst.CODE_STATUS_COMPLETE);
		statuses.add(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY);
		statuses.add(PlatformConst.CODE_STATUS_CANCEL_APPLY);
		statuses.add(PlatformConst.CODE_STATUS_WITHDRAWN);
		statuses.add(PlatformConst.CODE_STATUS_CANCEL);
		statuses.add(PlatformConst.CODE_STATUS_REVERT);
		statuses.add(PlatformConst.CODE_STATUS_APPROVED);
		statuses.add(PlatformConst.CODE_STATUS_APPLY);
		statuses.add(PlatformConst.CODE_STATUS_DRAFT);
		// 承認状況群を取得
		return statuses;
	}
	
	/**
	 * 承認状態が最も低いワークフロー情報を取得する。<br>
	 * 状況を数値として取得し、その大小で判断する。<br>
	 * 状況が同じ場合は、段階の大小で判断する。<br>
	 * 段階も同じ場合は、先頭のワークフロー情報を取得する。<br>
	 * ワークフロー情報が存在しない場合は、nullを返す。<br>
	 * @param dtos ワークフロー情報配列
	 * @return 承認状態が低い方のワークフロー情報
	 */
	public static WorkflowDtoInterface getLowestWorkflow(WorkflowDtoInterface... dtos) {
		// ワークフロー情報配列が存在しない場合
		if (dtos == null || dtos.length == 0) {
			// nullを取得
			return null;
		}
		// ワークフロー情報リストを取得
		List<WorkflowDtoInterface> list = new ArrayList<WorkflowDtoInterface>(Arrays.asList(dtos));
		// nullを除去
		list.removeAll(Collections.singleton(null));
		// ワークフロー情報リストが空である場合
		if (MospUtility.isEmpty(list)) {
			// nullを取得
			return null;
		}
		// ソート
		Collections.sort(list, (dto1, dto2) -> compareStageAndStatus(dto1, dto2));
		// 承認状態が最も低いワークフロー情報を取得
		return MospUtility.getFirstValue(list);
	}
	
	/**
	 * ワークフロー番号情報リストを承認状態でソートする。<br>
	 * @param dtos      ワークフロー番号情報リスト
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 */
	public static void sortByStatus(List<? extends WorkflowNumberDtoInterface> dtos,
			Map<Long, WorkflowDtoInterface> workflows) {
		// ワークフロー番号情報リストを承認状況でソート
		Collections.sort(dtos, (dto1, dto2) -> compareStageAndStatus(workflows.get(dto1.getWorkflow()),
				workflows.get(dto2.getWorkflow())));
	}
	
	/**
	 * ワークフロー情報を承認状態で比較する。<br>
	 * ワークフロー情報はnullでないことを前提とする。<br>
	 * 状況を数値として取得し、その大小で判断する。<br>
	 * 状況が同じ場合は、段階の大小で判断する。<br>
	 * @param dto1 ワークフロー情報1
	 * @param dto2 ワークフロー情報2
	 * @return 比較結果(マイナス：dto1の方が小さい、0：同じ、プラス：dto1の方が大きい)
	 */
	protected static int compareStageAndStatus(WorkflowDtoInterface dto1, WorkflowDtoInterface dto2) {
		// 状況及び段階を取得
		int status1 = MospUtility.getInt(dto1.getWorkflowStatus());
		int status2 = MospUtility.getInt(dto2.getWorkflowStatus());
		int stage1 = dto1.getWorkflowStage();
		int stage2 = dto2.getWorkflowStage();
		// 状況を比較
		int status = status1 - status2;
		// 状況に差がある場合
		if (status != 0) {
			// 比較結果を取得
			return status;
		}
		// 段階の大小で判断
		return stage1 - stage2;
	}
	
	/**
	 * ワークフロー情報群(キー：ワークフロー)を取得する。<br>
	 * @param workflows ワークフロー情報群
	 * @return ワークフロー情報群(キー：ワークフロー)
	 */
	public static Map<Long, WorkflowDtoInterface> getWorkflowMap(Collection<WorkflowDtoInterface> workflows) {
		// ワークフロー情報群(キー：ワークフロー)を準備
		Map<Long, WorkflowDtoInterface> workflowMap = new TreeMap<Long, WorkflowDtoInterface>();
		// ワークフロー情報毎に処理
		for (WorkflowDtoInterface dto : workflows) {
			// ワークフロー情報群(キー：ワークフロー)に設定
			workflowMap.put(dto.getWorkflow(), dto);
		}
		// ワークフロー情報群(キー：ワークフロー)を取得
		return workflowMap;
	}
	
	/**
	 * 対象機能コード群に含まれるワークフロー情報群(キー：ワークフロー)を取得する。<br>
	 * @param workflows     ワークフロー情報群
	 * @param functionCodes 対象機能コード群
	 * @return ワークフロー情報群(キー：ワークフロー)
	 */
	public static Map<Long, WorkflowDtoInterface> getWorkflowMap(Collection<WorkflowDtoInterface> workflows,
			Set<String> functionCodes) {
		// ワークフロー情報群(キー：ワークフロー)を準備
		Map<Long, WorkflowDtoInterface> workflowMap = new TreeMap<Long, WorkflowDtoInterface>();
		// ワークフロー情報毎に処理
		for (WorkflowDtoInterface dto : workflows) {
			// 対象機能コードに含まれる場合
			if (functionCodes.contains(dto.getFunctionCode())) {
				// ワークフロー情報群(キー：ワークフロー)に設定
				workflowMap.put(dto.getWorkflow(), dto);
			}
		}
		// ワークフロー情報群(キー：ワークフロー)を取得
		return workflowMap;
	}
	
	/**
	 * ワークフロー情報が自己承認であるかを確認する。<br>
	 * @param dto ワークフロー情報
	 * @return 確認結果(true：ワークフロー情報が自己承認である、false：そうでない)
	 */
	public static boolean isSelfApprove(WorkflowDtoInterface dto) {
		// ワークフロー情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 自己承認でないと判断
			return false;
		}
		// ワークフロー情報が自己承認であるかを確認
		return MospUtility.isEqual(dto.getApproverId(), PlatformConst.APPROVAL_ROUTE_SELF)
				|| MospUtility.isEqual(dto.getRouteCode(), PlatformConst.APPROVAL_ROUTE_SELF);
	}
	
	/**
	 * DTO群から対象となる承認状況であるDTO群を取得する。<br>
	 * @param dtos      DTO群(ワークフロー番号を有する情報群)
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 * @param statuses  対象となる承認状況群
	 * @return 対象となる承認状況であるDTO群
	 */
	public static <T extends WorkflowNumberDtoInterface> Set<T> getStatusMatchedDtos(Collection<T> dtos,
			Map<Long, WorkflowDtoInterface> workflows, Set<String> statuses) {
		// 対象となる承認状況であるDTO群を準備
		Set<T> statusMatchedDtos = new LinkedHashSet<T>();
		// 抽出対象のDTO群か対象となる承認状況群が存在しない場合
		if (MospUtility.isEmpty(dtos, statuses)) {
			// 空のDTO群を取得
			return statusMatchedDtos;
		}
		//  DTO(ワークフロー番号を有する情報)毎に処理
		for (T dto : dtos) {
			// 対象ワークフローの承認状況が承認状況群に含まれる場合
			if (WorkflowUtility.isMatch(workflows.get(dto.getWorkflow()), statuses)) {
				// 対象となる承認状況であるDTO群に設定
				statusMatchedDtos.add(dto);
			}
		}
		// 対象となる承認状況であるDTO群を取得
		return statusMatchedDtos;
	}
	
	/**
	 * DTO群から対象となる承認状況であるDTOリストを取得する。<br>
	 * @param dtos      DTO群(ワークフロー番号を有する情報群)
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 * @param statuses  対象となる承認状況群
	 * @return 対象となる承認状況であるDTOリスト
	 */
	public static <T extends WorkflowNumberDtoInterface> List<T> getStatusMatchedList(Collection<T> dtos,
			Map<Long, WorkflowDtoInterface> workflows, Set<String> statuses) {
		// 対象となる承認状況であるDTOリストを取得
		return new ArrayList<T>(WorkflowUtility.getStatusMatchedDtos(dtos, workflows, statuses));
	}
	
	/**
	 * ワークフローの状態と段階からワークフロー状態(表示用)を取得する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param dto        ワークフロー情報
	 * @return ワークフロー状態(表示用)
	 */
	public static String getWorkflowStatus(MospParams mospParams, WorkflowDtoInterface dto) {
		// ワークフロー情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// ワークフローの状態と段階からワークフロー状態(表示用)を取得
		return getWorkflowStatus(mospParams, dto.getWorkflowStatus(), dto.getWorkflowStage());
	}
	
	/**
	 * ワークフローの状態と段階からワークフロー状態(表示用)を取得する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param status     ワークフロー状態
	 * @param stage      ワークフロー段階
	 * @return ワークフロー状態(表示用)
	 */
	public static String getWorkflowStatus(MospParams mospParams, String status, int stage) {
		// ワークフロー状態確認
		if (PlatformConst.CODE_STATUS_DRAFT.equals(status)) {
			// 下書
			return PfNameUtility.draft(mospParams);
		}
		if (PlatformConst.CODE_STATUS_APPLY.equals(status)) {
			// 未承認
			return PfNameUtility.notApproved(mospParams);
		}
		if (PlatformConst.CODE_STATUS_CANCEL.equals(status)) {
			// 承解除
			return PfNameUtility.cancelApprovalAbbr(mospParams);
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(status)) {
			// 取下
			return PfNameUtility.withdraw(mospParams);
		}
		if (PlatformConst.CODE_STATUS_COMPLETE.equals(status)) {
			// 承認済
			return PfNameUtility.completed(mospParams);
		}
		if (PlatformConst.CODE_STATUS_CANCEL_APPLY.equals(status)
				|| PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY.equals(status)) {
			// 解除申
			return PfNameUtility.canelApplyAbbr(mospParams);
		}
		if (PlatformConst.CODE_STATUS_APPROVED.equals(status)) {
			// n次済
			return PfNameUtility.stageApproved(mospParams, stage - 1);
		}
		if (PlatformConst.CODE_STATUS_REVERT.equals(status)) {
			// n次戻
			return PfNameUtility.stageReverted(mospParams, stage + 1);
		}
		return status;
	}
	
}
