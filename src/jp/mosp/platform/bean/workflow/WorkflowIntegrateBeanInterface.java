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
package jp.mosp.platform.bean.workflow;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.comparator.human.HumanPositionGradeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフロー統括インターフェース。
 */
public interface WorkflowIntegrateBeanInterface extends BaseBeanInterface {
	
	/**
	 * 最新のワークフロー取得。
	 * <p>
	 * ワークフロー番号から最新のワークフローを取得。
	 * </p>
	 * @param workflow ワークフロー番号
	 * @return ワークフローデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface getLatestWorkflowInfo(long workflow) throws MospException;
	
	/**
	 * 対象日における申請者のフロー区分におけるルート承認者リストを取得する。<br>
	 * <br>
	 * ルート適用情報にルート{@link PlatformConst#APPROVAL_ROUTE_SELF}が
	 * 設定されていた場合は、承認者リスト(自己承認)を返す。<br>
	 * ルート適用情報にその他のルートが設定されていた場合は、
	 * ルート情報からユニット情報を取得し承認者リストを作成する。<br>
	 * その際、一つのユニットに複数の承認者が設定されている場合は、
	 * 職位ランクが優れている順に並べられる。
	 * ルート適用情報が取得できなかった場合は、空のリストを返す。<br>
	 * <br>
	 * @param personalId   申請者個人ID
	 * @param targetDate   対象年月日
	 * @param workflowType フロー区分
	 * @return ルート承認者リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<List<String[]>> getRouteApproverList(String personalId, Date targetDate, int workflowType)
			throws MospException;
	
	/**
	 * 対象個人IDが属する承認ルートコード群を取得する。<br>
	 * <br>
	 * 対象個人IDが対象日時点で属する承認ユニットを取得し、
	 * その承認ユニットが設定されている承認ルート対象とする。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 承認ルートコード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<String> getApproverRouteSet(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDが属する承認ユニットコード群を取得する。<br>
	 * <br>
	 * 対象個人IDが対象日時点で属する承認ユニットを対象とする。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 承認ユニットコード群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<String> getApproverUnitSet(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象ワークフローにおいて、対象個人IDが操作権を持つかどうかを確認する。<br>
	 * @param dto        対象ワークフロー情報
	 * @param personalId 対象個人ID
	 * @return 確認結果(true：操作権を持つ、false：操作権を持たない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isApprover(WorkflowDtoInterface dto, String personalId) throws MospException;
	
	/**
	 * ワークフローの状態と段階からワークフロー状態(表示用)を取得する。<br>
	 * @param status ワークフロー状態
	 * @param stage  ワークフロー段階
	 * @return ワークフロー状態(表示用)
	 */
	String getWorkflowStatus(String status, int stage);
	
	/**
	 * ワークフロー操作(表示用)を取得する。<br>
	 * 表示の際に用いる文字列を取得する。<br>
	 * @param status ワークフロー状態
	 * @return ワークフロー操作(表示用)
	 */
	String getWorkflowOperation(String status);
	
	/**
	 * 承認可能ワークフロー情報マップを取得する。<br>
	 * 対象個人IDが承認可能なものを抽出する。<br>
	 * 但し、ログインユーザが特権ロールを持つ場合、全ての承認可能なワークフローを返す。<br>
	 * 対象機能コードセットに含まれる機能コードのワークフローのみを取得する。<br>
	 * @param personalId      対象個人ID
	 * @param functionCodeSet 対象機能コードセット
	 * @return 承認可能ワークフロー情報マップ(キー：機能コード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Map<Long, WorkflowDtoInterface>> getApprovableMap(String personalId, Set<String> functionCodeSet)
			throws MospException;
	
	/**
	 * 承認可能ワークフロー情報群を取得する。<br>
	 * 対象個人IDが承認可能なものを抽出する。<br>
	 * 但し、ログインユーザが特権ロールを持つ場合、全ての承認可能なワークフローを返す。<br>
	 * 対象機能コードのワークフローのみを取得する。<br>
	 * @param personalId   対象個人ID
	 * @param functionCode 対象機能コード
	 * @return 承認可能ワークフロー情報マップ(キー：ワークフロー番号)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Long, WorkflowDtoInterface> getApprovableMap(String personalId, String functionCode) throws MospException;
	
	/**
	 * 代理承認可能ワークフロー情報マップを取得する。<br>
	 * 対象個人IDが代理として承認可能なものを抽出する。<br>
	 * 対象機能コードセットに含まれる機能コードのワークフローのみを取得する。<br>
	 * 但し、承認可能ワークフロー情報マップに含まれるワークフローは、除外する。<br>
	 * @param personalId      対象個人ID
	 * @param functionCodeSet 対象機能コードセット
	 * @param workflowType    対象フロー区分
	 * @param approvableMap   承認可能ワークフロー情報マップ
	 * @return 承認可能ワークフロー情報マップ(キー：機能コード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Map<Long, WorkflowDtoInterface>> getSubApprovableMap(String personalId, Set<String> functionCodeSet,
			int workflowType, Map<String, Map<Long, WorkflowDtoInterface>> approvableMap) throws MospException;
	
	/**
	 * 解除承認可能ワークフロー情報マップを取得する。<br>
	 * 対象個人IDが解除承認可能なものを抽出する。<br>
	 * 但し、ログインユーザが特権ロールを持つ場合、全ての解除承認可能なワークフローを返す。<br>
	 * 対象機能コードセットに含まれる機能コードのワークフローのみを取得する。<br>
	 * @param personalId 対象個人ID
	 * @param functionCodeSet 対象機能コードセット
	 * @return 解除承認可能ワークフロー情報マップ(キー：機能コード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Map<Long, WorkflowDtoInterface>> getCancelableMap(String personalId, Set<String> functionCodeSet)
			throws MospException;
	
	/**
	 * 代理解除承認可能ワークフロー情報マップを取得する。<br>
	 * 対象個人IDが代理として解除承認可能なものを抽出する。<br>
	 * 対象機能コードセットに含まれる機能コードのワークフローのみを取得する。<br>
	 * 但し、解除承認可能ワークフロー情報マップに含まれるワークフローは、除外する。<br>
	 * @param personalId 対象個人ID
	 * @param functionCodeSet 対象機能コードセット
	 * @param workflowType 対象フロー区分
	 * @param approvableMap 解除承認可能ワークフロー情報マップ
	 * @return 解除承認可能ワークフロー情報マップ(キー：機能コード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Map<Long, WorkflowDtoInterface>> getSubCancelableMap(String personalId, Set<String> functionCodeSet,
			int workflowType, Map<String, Map<Long, WorkflowDtoInterface>> approvableMap) throws MospException;
	
	/**
	 * 対象期間における有効ワークフロー情報一覧を取得する。<br>
	 * 申請者が対象申請者個人IDセットに含まれるもの、
	 * 或いは対象個人IDが承認者として含まれるものを抽出する。<br>
	 * @param personalId      対象個人ID
	 * @param fromDate        対象期間自
	 * @param toDate          対象期間至
	 * @param functionCodeSet 対象機能コードセット
	 * @param state           対象ワークフロー状態
	 * @param personalIdSet    対象申請者個人IDセット(検索条件による)
	 * @param subordinateIdSet 対象申請者個人IDセット(部下)
	 * @return 有効ワークフロー情報一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowDtoInterface> getEffectiveList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet, String state, Set<String> personalIdSet, Set<String> subordinateIdSet)
			throws MospException;
	
	/**
	 * 対象期間における承認済ワークフロー情報一覧を取得する。<br>
	 * @param personalId 個人ID
	 * @param fromDate 対象期間自
	 * @param toDate 対象期間至
	 * @param functionCodeSet 機能コードセット
	 * @return 承認済ワークフロー情報一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowDtoInterface> getCompletedList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException;
	
	/**
	 * 対象ワークフローが取下であるかを確認する。<br>
	 * @param workflow 対象ワークフロー番号
	 * @return 確認結果(true：取下である、false：取下でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isWithDrawn(long workflow) throws MospException;
	
	/**
	 * 対象ワークフローが取下であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：取下である、false：取下でない)
	 */
	boolean isWithDrawn(WorkflowDtoInterface dto);
	
	/**
	 * 対象ワークフローが下書であるかを確認する。<br>
	 * @param workflow 対象ワークフロー番号
	 * @return 確認結果(true：下書である、false：下書でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isDraft(long workflow) throws MospException;
	
	/**
	 * 対象ワークフローが下書であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：下書である、false：下書でない)
	 */
	boolean isDraft(WorkflowDtoInterface dto);
	
	/**
	 * 対象ワークフローが1次戻であるかを確認する。<br>
	 * @param workflow 対象ワークフロー番号
	 * @return 確認結果(true：1次戻である、false：1次戻でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isFirstReverted(long workflow) throws MospException;
	
	/**
	 * 対象ワークフローが1次戻であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：1次戻である、false：1次戻でない)
	 */
	boolean isFirstReverted(WorkflowDtoInterface dto);
	
	/**
	 * 対象ワークフローが承認済であるかを確認する。<br>
	 * @param workflow 対象ワークフロー番号
	 * @return 確認結果(true：承認済である、false：承認済でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isCompleted(long workflow) throws MospException;
	
	/**
	 * 対象ワークフローが承認済であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認済である、false：承認済でない)
	 */
	boolean isCompleted(WorkflowDtoInterface dto);
	
	/**
	 * 対象ワークフローが承認可能であるかを確認する。<br>
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
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：承認可能である、false：承認可能でない)
	 */
	boolean isApprovable(WorkflowDtoInterface dto);
	
	/**
	 * 対象ワークフローが解除承認可能であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：解除承認可能である、false：解除承認可能でない)
	 */
	boolean isCancelApprovable(WorkflowDtoInterface dto);
	
	/**
	 * 対象ワークフローが解除承認可能であるかを確認する。<br>
	 * @param dto 対象ワークフロー情報
	 * @return 確認結果(true：解除承認可能である、false：解除承認可能でない)
	 */
	boolean isCancelWithDrawnApprovable(WorkflowDtoInterface dto);
	
	/**
	 * 対象日におけるユニットに設定されている承認者を取得する。<br>
	 * 但し、代理承認者は含まれない。<br>
	 * <br>
	 * また、対象日において次の条件のいずれかに合致する承認者は、含めない。<br>
	 * ・人事基本情報が存在しない<br>
	 * ・入社していない<br>
	 * ・休職している<br>
	 * ・退職している<br>
	 * ・承認ロールを有していない<br>
	 * <br>
	 * 対象日におけるユニットに設定されている承認者が1人も存在しない場合、
	 * エラーメッセージ(PFW0228)を付加する。<br>
	 * <br>
	 * @param unitCode   ユニットコード
	 * @param targetDate 対象日
	 * @return ユニット承認者リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<HumanDtoInterface> getUnitApproverList(String unitCode, Date targetDate) throws MospException;
	
	/**
	 * 職位比較クラスを取得する。<br>
	 * @param targetDate 対象年月日
	 * @return 職位比較クラス
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	HumanPositionGradeComparator getPositionGradeComparator(Date targetDate) throws MospException;
	
	/**
	 * 申請用承認者情報配列を取得する。<br>
	 * 配列の内容は以下の通り。<br>
	 * <ul><li>
	 * 1次元目：承認階層
	 * </li><li>
	 * 2次元目：承認階層における申請用承認者情報インデックス
	 * </li><li>
	 * 3次元目：各申請用承認者情報
	 * </li></ul>
	 * @param personalId 申請者個人ID
	 * @param targetDate 申請年月日
	 * @param workflowType フロー区分
	 * @return 申請用承認者情報配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][][] getArrayForApproverSetting(String personalId, Date targetDate, int workflowType) throws MospException;
	
	/**
	 * 対象ワークフローにおいて、対象個人IDが承認者の一人であるかどうかを確認する。<br>
	 * ここでは、代理は考慮しない。<br>
	 * @param dto 対象ワークフロー情報
	 * @param personalId 対象個人ID
	 * @return 確認結果(true：承認者の一人である、false：承認者の一人ではない)
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	boolean isOneOfApprover(WorkflowDtoInterface dto, String personalId) throws MospException;
}
