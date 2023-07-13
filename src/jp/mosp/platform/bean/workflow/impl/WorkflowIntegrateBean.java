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
package jp.mosp.platform.bean.workflow.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.comparator.human.HumanPositionGradeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;

/**
 * ワークフロー統括クラス。
 */
public class WorkflowIntegrateBean extends PlatformBean implements WorkflowIntegrateBeanInterface {
	
	/**
	 * ワークフロー参照クラス。
	 */
	protected WorkflowReferenceBeanInterface			workflowReference;
	
	/**
	 * プラットフォームマスタ参照クラス。<br>
	 */
	protected PlatformMasterBeanInterface				platformMaster;
	
	/**
	 * 承認ルート参照クラス。
	 */
	protected ApprovalRouteReferenceBeanInterface		routeReference;
	
	/**
	 * 承認ルートユニット情報参照クラス。
	 */
	protected ApprovalRouteUnitReferenceBeanInterface	routeUnitReference;
	
	/**
	 * 承認ユニット参照クラス。
	 */
	protected ApprovalUnitReferenceBeanInterface		unitReference;
	
	/**
	 * 人事マスタ検索クラス。
	 */
	protected HumanSearchBeanInterface					humanSearch;
	
	/**
	 * 人事マスタ参照クラス。
	 */
	protected HumanReferenceBeanInterface				humanReference;
	
	/**
	 * 人事退職情報参照クラス。
	 */
	protected RetirementReferenceBeanInterface			retirementReference;
	
	/**
	 * 人事休職報参照クラス。
	 */
	protected SuspensionReferenceBeanInterface			suspensionReference;
	
	/**
	 * 人事兼務報参照クラス。
	 */
	protected ConcurrentReferenceBeanInterface			concurrentReference;
	
	/**
	 * ユーザマスタ参照クラス。
	 */
	protected UserMasterReferenceBeanInterface			userMasterReference;
	
	/**
	 * ユーザ追加ロール情報参照処理。<br>
	 */
	protected UserExtraRoleReferenceBeanInterface		userExtraRoleRefer;
	
	/**
	 * 職位マスタ参照クラス。
	 */
	protected PositionReferenceBeanInterface			positionReference;
	
	/**
	 * 代理承認者テーブル参照クラス。
	 */
	protected SubApproverReferenceBeanInterface			subApproverReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkflowIntegrateBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		routeReference = createBeanInstance(ApprovalRouteReferenceBeanInterface.class);
		routeUnitReference = createBeanInstance(ApprovalRouteUnitReferenceBeanInterface.class);
		unitReference = createBeanInstance(ApprovalUnitReferenceBeanInterface.class);
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		humanReference = createBeanInstance(HumanReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		suspensionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		concurrentReference = createBeanInstance(ConcurrentReferenceBeanInterface.class);
		userMasterReference = createBeanInstance(UserMasterReferenceBeanInterface.class);
		userExtraRoleRefer = createBeanInstance(UserExtraRoleReferenceBeanInterface.class);
		positionReference = createBeanInstance(PositionReferenceBeanInterface.class);
		workflowReference = createBeanInstance(WorkflowReferenceBeanInterface.class);
		subApproverReference = createBeanInstance(SubApproverReferenceBeanInterface.class);
		// プラットフォームマスタ参照クラスを取得
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
	}
	
	@Override
	public WorkflowDtoInterface getLatestWorkflowInfo(long workflow) throws MospException {
		return workflowReference.getLatestWorkflowInfo(workflow);
	}
	
	@Override
	public List<List<String[]>> getRouteApproverList(String personalId, Date targetDate, int workflowType)
			throws MospException {
		// ルート承認者リスト準備
		List<List<String[]>> routeApproverList = new ArrayList<List<String[]>>();
		// ルート適用情報取得及び確認
		RouteApplicationDtoInterface routeApplicationDto = platformMaster.getRouteApplication(personalId, targetDate,
				workflowType);
		if (routeApplicationDto == null) {
			return routeApproverList;
		}
		// ルートコード取得
		String routeCode = routeApplicationDto.getRouteCode();
		// 自己承認確認
		if (routeCode.equals(PlatformConst.APPROVAL_ROUTE_SELF)) {
			// 承認者リスト(自己承認)作成(自己承認の場合の承認階層は1)
			routeApproverList.add(getSelfApproverList());
			return routeApproverList;
		}
		// ルート情報取得及び確認
		ApprovalRouteDtoInterface routeDto = routeReference.getApprovalRouteInfo(routeCode, targetDate);
		if (routeDto == null) {
			return routeApproverList;
		}
		// 承認ルートユニット取得(承認階層順)
		List<ApprovalRouteUnitDtoInterface> unitList = routeUnitReference.getApprovalRouteUnitList(routeCode,
				targetDate);
		// 職位等級比較クラス準備
		HumanPositionGradeComparator comparator = getPositionGradeComparator(targetDate);
		// ユニット毎に承認者を設定
		for (ApprovalRouteUnitDtoInterface routeUnitDto : unitList) {
			// ユニット承認者リスト取得
			List<HumanDtoInterface> unitApproverList = getUnitApproverList(routeUnitDto.getUnitCode(), targetDate);
			// 職位等級でソート
			Collections.sort(unitApproverList, comparator);
			// ルート承認者リストに追加
			routeApproverList.add(getApproverList(unitApproverList, personalId, targetDate, workflowType));
		}
		return routeApproverList;
	}
	
	@Override
	public HumanPositionGradeComparator getPositionGradeComparator(Date targetDate) throws MospException {
		// 職位マスタマップ取得(比較用のため操作範囲指定は無し)
		Map<String, PositionDtoInterface> positionMap = positionReference.getPositionMap(targetDate, null);
		// 職位優劣取得
		boolean hasLowGradeAdvantage = positionReference.hasLowGradeAdvantage();
		// 職位等級比較クラス準備
		return new HumanPositionGradeComparator(positionMap, hasLowGradeAdvantage);
	}
	
	@Override
	public Set<String> getApproverRouteSet(String personalId, Date targetDate) throws MospException {
		// 承認ルートコード群を準備
		Set<String> set = new HashSet<String>();
		// 対象個人IDが属する承認ユニットコード群毎に処理
		for (String unitCode : getApproverUnitSet(personalId, targetDate)) {
			set.addAll(routeUnitReference.getRouteSetForUnit(unitCode, targetDate));
		}
		return set;
	}
	
	@Override
	public Set<String> getApproverUnitSet(String personalId, Date targetDate) throws MospException {
		// 承認ユニットコード群を準備
		Set<String> set = new HashSet<String>();
		// 承認者個人IDから承認ユニットコード群を取得
		set.addAll(unitReference.getUnitSetForPersonalId(personalId, targetDate));
		// 対象個人ID及び対象日で人事情報を取得
		HumanDtoInterface humanDto = getHumanInfo(personalId, targetDate);
		// 人事情報が存在しない場合
		if (humanDto == null) {
			return set;
		}
		// 所属コード及び職位コードを取得
		String section = humanDto.getSectionCode();
		String position = humanDto.getPositionCode();
		// 所属コード及び職位コードから承認ユニットコード群を取得
		set.addAll(unitReference.getUnitSetForMaster(section, position, targetDate));
		// 兼務情報取得
		List<ConcurrentDtoInterface> concurrentList = concurrentReference.getConcurrentList(personalId, targetDate);
		for (ConcurrentDtoInterface concurrentDto : concurrentList) {
			// 所属コード及び職位コードを取得
			section = concurrentDto.getSectionCode();
			position = concurrentDto.getPositionCode();
			// 所属コード及び職位コードから承認ユニットコード群を取得
			set.addAll(unitReference.getUnitSetForMaster(section, position, targetDate));
		}
		return set;
	}
	
	@Override
	public String getWorkflowStatus(String status, int stage) {
		// ワークフローの状態と段階からワークフロー状態(表示用)を取得
		return WorkflowUtility.getWorkflowStatus(mospParams, status, stage);
	}
	
	@Override
	public String getWorkflowOperation(String status) {
		// ワークフロー状態確認
		if (PlatformConst.CODE_STATUS_DRAFT.equals(status)) {
			// 下書
			return PfNameUtility.draft(mospParams);
		} else if (status.equals(PlatformConst.CODE_STATUS_APPLY)) {
			// 申請
			return PfNameUtility.application(mospParams);
		} else if (status.equals(PlatformConst.CODE_STATUS_APPROVED)) {
			// 承認
			return PfNameUtility.approval(mospParams);
		} else if (status.equals(PlatformConst.CODE_STATUS_REVERT)) {
			// 差戻
			return PfNameUtility.reverted(mospParams);
		} else if (status.equals(PlatformConst.CODE_STATUS_CANCEL)) {
			// 承解除(取消)
			return PfNameUtility.cancelApprovalAbbr(mospParams);
		} else if (status.equals(PlatformConst.CODE_STATUS_WITHDRAWN)) {
			// 取下
			return PfNameUtility.withdraw(mospParams);
		} else if (status.equals(PlatformConst.CODE_STATUS_COMPLETE)) {
			// 承認(最終承認)
			return PfNameUtility.approval(mospParams);
		} else if (status.equals(PlatformConst.CODE_STATUS_CANCEL_APPLY)
				|| status.equals(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY)) {
			// 解除申
			return PfNameUtility.application(mospParams);
		}
		return status;
	}
	
	@Override
	public boolean isApprover(WorkflowDtoInterface dto, String personalId) throws MospException {
		// ルートユニットリスト群を準備
		Map<String, List<ApprovalRouteUnitDtoInterface>> routeUnitMap = new HashMap<String, List<ApprovalRouteUnitDtoInterface>>();
		// 承認者ユニットコード群を準備
		Set<String> approverSet = new HashSet<String>();
		// 非承認者ユニットコード群を準備
		Set<String> notApproverSet = new HashSet<String>();
		// 対象個人IDが操作権を持つかどうかを確認
		return isApprover(dto, personalId, routeUnitMap, approverSet, notApproverSet);
	}
	
	/**
	 * 対象ワークフローにおいて、対象個人IDが操作権を持つかどうかを確認する。<br>
	 * <br>
	 * 一度確認したユニットは、承認者ユニットコード群
	 * 及び非承認者ユニットコード群に保持しておく。<br>
	 * <br>
	 * @param dto            対象ワークフロー情報
	 * @param personalId     対象個人ID
	 * @param routeUnitMap   ルートユニットリスト群(キー：ルートコード)
	 * @param approverSet    承認者ユニットコード群(キー：対象日)
	 * @param notApproverSet 非承認者ユニットコード群(キー：対象日)
	 * @return 確認結果(true：操作権を持つ、false：操作権を持たない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isApprover(WorkflowDtoInterface dto, String personalId,
			Map<String, List<ApprovalRouteUnitDtoInterface>> routeUnitMap, Set<String> approverSet,
			Set<String> notApproverSet) throws MospException {
		// ワークフロー段階確認
		if (dto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO) {
			// 操作者が申請者の場合
			return false;
		}
		// ワークフロー状況確認
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_DRAFT)
				|| dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_WITHDRAWN)) {
			// 下書か取下の場合
			return false;
		}
		// 操作権を持つ段階を取得
		List<Integer> approverStageList = getApproverStageList(dto, personalId, routeUnitMap, approverSet,
				notApproverSet);
		// ワークフロー段階確認
		if (approverStageList.contains(dto.getWorkflowStage())) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isOneOfApprover(WorkflowDtoInterface dto, String personalId) throws MospException {
		// 自己承認確認
		if (dto.getApproverId().equals(PlatformConst.APPROVAL_ROUTE_SELF)
				|| dto.getRouteCode().equals(PlatformConst.APPROVAL_ROUTE_SELF)) {
			// 申請者確認
			if (dto.getPersonalId().equals(personalId)) {
				return true;
			}
		}
		// 承認者個人ID確認
		if (dto.getApproverId().isEmpty() == false) {
			// 承認者個人ID配列取得
			String[] approverArray = split(dto.getApproverId(), SEPARATOR_DATA);
			// 対象個人ID確認
			for (String element : approverArray) {
				if (element.equals(personalId)) {
					// 対象個人IDが承認者個人IDに含まれる場合
					return true;
				}
			}
			return false;
		}
		// システム日付を取得
		Date systemDate = getSystemDate();
		// ルートユニット情報取得
		List<ApprovalRouteUnitDtoInterface> routeUnitList = routeUnitReference
			.getApprovalRouteUnitList(dto.getRouteCode(), systemDate);
		// ルートユニット毎に確認
		for (ApprovalRouteUnitDtoInterface routeUnitDto : routeUnitList) {
			// ユニット承認者リスト取得
			List<HumanDtoInterface> humanList = getUnitApproverList(routeUnitDto.getUnitCode(), systemDate);
			// ユニット承認者毎に処理
			for (HumanDtoInterface humanDto : humanList) {
				// 対象個人ID確認
				if (personalId.equals(humanDto.getPersonalId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 対象ワークフローにおいて、対象個人IDが操作権を持つ段階のリスト取得する。<br>
	 * 操作権を持たない場合は0を返す。<br>
	 * <br>
	 * @param dto            対象ワークフロー情報
	 * @param personalId     対象個人ID
	 * @param routeUnitMap   ルートユニットリスト群(キー：ルートコード)
	 * @param approverSet    承認者ユニットコード群
	 * @param notApproverSet 非承認者ユニットコード群
	 * @return 操作権を持つ段階のリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<Integer> getApproverStageList(WorkflowDtoInterface dto, String personalId,
			Map<String, List<ApprovalRouteUnitDtoInterface>> routeUnitMap, Set<String> approverSet,
			Set<String> notApproverSet) throws MospException {
		// 操作権を持つ段階のリスト準備
		List<Integer> stageList = new ArrayList<Integer>();
		// 承認者個人ID確認
		if (dto.getApproverId().isEmpty() == false) {
			// 承認者個人ID配列取得
			String[] approverArray = split(dto.getApproverId(), SEPARATOR_DATA);
			// 対象個人ID確認
			for (int i = 0; i < approverArray.length; i++) {
				if (approverArray[i].equals(personalId)) {
					// 対象個人IDが承認者個人IDに含まれる場合
					stageList.add(i + 1);
				}
			}
			return stageList;
		}
		// システム日付を取得
		Date systemDate = getSystemDate();
		// ルートコードを取得
		String routeCode = dto.getRouteCode();
		// ルートユニット情報を取得
		List<ApprovalRouteUnitDtoInterface> routeUnitList = routeUnitMap.get(routeCode);
		// ルートユニット情報が取得できなかった場合
		if (routeUnitList == null) {
			// ルートユニット情報取得
			routeUnitList = routeUnitReference.getApprovalRouteUnitList(routeCode, systemDate);
			routeUnitMap.put(routeCode, routeUnitList);
		}
		// ルートユニット毎に確認
		for (ApprovalRouteUnitDtoInterface routeUnitDto : routeUnitList) {
			// ユニットコード取得
			String unitCode = routeUnitDto.getUnitCode();
			// 承認者ユニットコード群に含まれる場合
			if (approverSet.contains(unitCode)) {
				// 操作権を持つ段階のリストに追加
				stageList.add(routeUnitDto.getApprovalStage());
				continue;
			}
			// 非承認者ユニットコード群に含まれる場合
			if (notApproverSet.contains(unitCode)) {
				// 処理無し
				continue;
			}
			// 対象日におけるユニットに設定されている承認者個人ID群を取得
			Set<String> unitApproverSet = getUnitApproverSet(unitCode, systemDate);
			// 対象個人ID確認
			if (unitApproverSet.contains(personalId)) {
				// 承認者ユニットコード群に追加
				approverSet.add(unitCode);
				// 操作権を持つ段階のリストに追加
				stageList.add(routeUnitDto.getApprovalStage());
			} else {
				// 非承認者ユニットコード群に追加
				notApproverSet.add(unitCode);
			}
		}
		return stageList;
	}
	
	/**
	 * 対象日におけるユニットに設定されている承認者個人ID群を取得する。<br>
	 * <br>
	 * 但し、代理承認者は含まれない。<br>
	 * また、入社、休職、退職、ロールは、考慮しない。<br>
	 * <br>
	 * @param unitCode   ユニットコード
	 * @param targetDate 対象日
	 * @return 承認者個人ID群
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected Set<String> getUnitApproverSet(String unitCode, Date targetDate) throws MospException {
		// 検索ユニット承認者群準備
		Set<String> set = new HashSet<String>();
		// ユニット情報取得
		ApprovalUnitDtoInterface unitDto = unitReference.getApprovalUnitInfo(unitCode, targetDate);
		// ユニット区分が個人IDの場合
		if (PlatformConst.UNIT_TYPE_PERSON.equals(unitDto.getUnitType())) {
			// 個人ID配列取得
			set.addAll(asList(unitDto.getApproverPersonalId(), SEPARATOR_DATA));
		}
		// ユニット区分が所属の場合
		if (PlatformConst.UNIT_TYPE_SECTION.equals(unitDto.getUnitType())) {
			// 検索条件設定(対象日)
			humanSearch.setTargetDate(targetDate);
			// 検索条件設定(所属コード)
			humanSearch.setSectionCode(unitDto.getApproverSectionCode());
			// 検索条件設定(職位コード)
			humanSearch.setPositionCode(unitDto.getApproverPositionCode());
			// 検索条件設定(職位等級範囲)
			humanSearch.setPositionGradeRange(unitDto.getApproverPositionGrade());
			// 検索条件設定(兼務要否)
			humanSearch.setNeedConcurrent(true);
			// 承認者検索
			set.addAll(humanSearch.getPersonalIdSet());
		}
		return set;
	}
	
	@Override
	public List<HumanDtoInterface> getUnitApproverList(String unitCode, Date targetDate) throws MospException {
		// 検索ユニット承認者リスト準備
		List<HumanDtoInterface> searchList = new ArrayList<HumanDtoInterface>();
		// ユニット情報取得
		ApprovalUnitDtoInterface unitDto = unitReference.getApprovalUnitInfo(unitCode, targetDate);
		// ユニット区分が個人IDの場合
		if (PlatformConst.UNIT_TYPE_PERSON.equals(unitDto.getUnitType())) {
			// 個人ID配列取得
			String[] approverIdArray = split(unitDto.getApproverPersonalId(), SEPARATOR_DATA);
			// 各個人IDから人事情報を取得してユニット承認者リストに設定
			for (String approverId : approverIdArray) {
				// 承認者人事情報取得及び確認
				HumanDtoInterface approverDto = humanReference.getHumanInfo(approverId, targetDate);
				if (approverDto == null) {
					continue;
				}
				// 検索ユニット承認者リストに追加
				searchList.add(approverDto);
			}
		}
		// ユニット区分が所属の場合
		if (PlatformConst.UNIT_TYPE_SECTION.equals(unitDto.getUnitType())) {
			// 検索条件設定(対象日)
			humanSearch.setTargetDate(targetDate);
			// 検索条件設定(所属コード)
			humanSearch.setSectionCode(unitDto.getApproverSectionCode());
			// 検索条件設定(職位コード)
			humanSearch.setPositionCode(unitDto.getApproverPositionCode());
			// 検索条件設定(職位等級範囲)
			humanSearch.setPositionGradeRange(unitDto.getApproverPositionGrade());
			// 検索条件設定(兼務要否)
			humanSearch.setNeedConcurrent(true);
			// 承認者検索
			searchList = humanSearch.search();
		}
		// ユニット承認者リスト準備
		List<HumanDtoInterface> approverList = new ArrayList<HumanDtoInterface>();
		// 承認ロールセット取得
		Set<String> approverRoleSet = RoleUtility.getApproverRoles(mospParams);
		// 検索ユニット承認者毎に処理
		for (HumanDtoInterface approver : searchList) {
			// 対象者が有効な承認者であるかを確認
			if (isValidApprover(approver.getPersonalId(), targetDate, approverRoleSet)) {
				// ユニット承認者リストに追加
				approverList.add(approver);
			}
		}
		// 承認者リスト確認
		if (approverList.isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorUnitHaveNoApprover(mospParams, unitDto.getUnitCode(), targetDate);
		}
		return approverList;
	}
	
	/**
	 * 対象者が有効な承認者であるかを確認する。<br>
	 * <br>
	 * 対象日において次の条件のいずれかに合致する承認者は、有効な承認者ではない。<br>
	 * ・入社していない<br>
	 * ・休職している<br>
	 * ・退職している<br>
	 * ・承認ロールを有していない<br>
	 * <br>
	 * @param psersonalId 対象者個人ID
	 * @param targetDate  対象日
	 * @param approverRoleSet 承認ロールセット
	 * @return 確認結果(true：有効な承認者、false：有効でない承認者)
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected boolean isValidApprover(String psersonalId, Date targetDate, Set<String> approverRoleSet)
			throws MospException {
		// 入社、休職、退職を確認
		if (isEntered(psersonalId, targetDate) == false || retirementReference.isRetired(psersonalId, targetDate)
				|| suspensionReference.isSuspended(psersonalId, targetDate)) {
			return false;
		}
		// 承認者のユーザ情報を取得
		List<UserMasterDtoInterface> userList = userMasterReference.getUserListForPersonalId(psersonalId, targetDate);
		// ユーザ情報毎に確認
		for (UserMasterDtoInterface user : userList) {
			// 承認ロールを確認
			if (userExtraRoleRefer.isTheRoleCodeSet(user, approverRoleSet)) {
				// 承認ロールを有している場合
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 承認者リストを作成する。<br>
	 * 代理承認者はリストには含まれない。<br>
	 * 取得されるリストには、{@link #getApproverOption(HumanDtoInterface)}
	 * で取得した配列が含まれる。<br>
	 * @param list         承認者リスト
	 * @param personalId   申請者個人ID
	 * @param targetDate   対象年月日
	 * @param workflowType フロー区分
	 * @return 承認者リスト(代理含む)
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<String[]> getApproverList(List<HumanDtoInterface> list, String personalId, Date targetDate,
			int workflowType) throws MospException {
		// 承認者リスト準備
		List<String[]> approverList = new ArrayList<String[]>();
		// 承認者リストから申請用承認者情報を作成
		for (HumanDtoInterface approver : list) {
			// 申請用承認者情報配列作成用リストに追加
			approverList.add(getApproverOption(approver));
		}
		return approverList;
	}
	
	/**
	 * 承認者リスト(自己承認)を作成する。<br>
	 * 取得されるリストには、以下内容の配列のみが含まれる。<br>
	 * <ul><li>
	 * 1列目：{@link PlatformConst#APPROVAL_ROUTE_SELF}
	 * </li><li>
	 * 2列目：自己承認
	 * </li></ul>
	 * @return 承認者リスト(自己承認)
	 */
	protected List<String[]> getSelfApproverList() {
		// 承認者リスト準備
		List<String[]> approverList = new ArrayList<String[]>();
		// 自己承認設定
		approverList
			.add(new String[]{ PlatformConst.APPROVAL_ROUTE_SELF, PfNameUtility.selfApproval(mospParams) });
		// 申請用承認者情報作成
		return approverList;
	}
	
	/**
	 * 申請用承認者情報を作成する。<br>
	 * 取得される配列の内容は以下の通り。<br>
	 * <ul><li>
	 * 1列目：個人ID
	 * </li><li>
	 * 2列目：社員コード+社員名
	 * </li></ul>
	 * @param dto 承認者社員情報
	 * @return 申請用承認者情報
	 */
	protected String[] getApproverOption(HumanDtoInterface dto) {
		// 社員名準備
		String humanName = MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
		// 申請用承認者情報作成
		return new String[]{ dto.getPersonalId(),
			getCodedName(dto.getEmployeeCode(), humanName, dto.getEmployeeCode().length()) };
	}
	
	/**
	 * 承認可能ワークフロー情報リストを取得する。<br>
	 * 対象個人IDが承認可能なものを抽出する。<br>
	 * 但し、ログインユーザが特権ロールを持つ場合、全ての承認可能なワークフローを返す。<br>
	 * 対象機能コードセットに含まれる機能コードのワークフローのみを取得する。<br>
	 * @param personalId      対象個人ID
	 * @param functionCodeSet 対象機能コードセット
	 * @return 承認可能ワークフロー情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<WorkflowDtoInterface> getApprovableList(String personalId, Set<String> functionCodeSet)
			throws MospException {
		// 承認可能ワークフロー情報リスト取得
		List<WorkflowDtoInterface> approvableList = workflowReference.getApprovableList(functionCodeSet);
		// 特権ロール確認
		if (RoleUtility.isSuper(mospParams)) {
			// 全ての承認可能ワークフロー情報を取得
			return approvableList;
		}
		// 対象個人IDが承認可能なワークフロー情報リストを準備
		List<WorkflowDtoInterface> list = new ArrayList<WorkflowDtoInterface>();
		// ルートユニットリスト群を準備
		Map<String, List<ApprovalRouteUnitDtoInterface>> routeUnitMap = new HashMap<String, List<ApprovalRouteUnitDtoInterface>>();
		// 承認者ユニットコード群を準備
		Set<String> approverSet = new HashSet<String>();
		// 非承認者ユニットコード群を準備
		Set<String> notApproverSet = new HashSet<String>();
		// ワークフロー情報毎に処理
		for (WorkflowDtoInterface dto : approvableList) {
			// 承認者確認
			if (isApprover(dto, personalId, routeUnitMap, approverSet, notApproverSet)) {
				// リストにワークフロー情報を追加
				list.add(dto);
			}
			
		}
		return list;
	}
	
	/**
	 * 解除承認可能ワークフロー情報リストを取得する。<br>
	 * 対象個人IDが解除承認可能なものを抽出する。<br>
	 * 但し、ログインユーザが特権ロールを持つ場合、全ての解除承認可能なワークフローを返す。<br>
	 * 対象機能コードセットに含まれる機能コードのワークフローのみを取得する。<br>
	 * @param personalId 対象個人ID
	 * @param functionCodeSet 対象機能コードセット
	 * @return 解除承認可能ワークフロー情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<WorkflowDtoInterface> getCancelableList(String personalId, Set<String> functionCodeSet)
			throws MospException {
		// 承認可能ワークフロー情報リスト取得
		List<WorkflowDtoInterface> cancelableList = workflowReference.getCancelableList(functionCodeSet);
		// 特権ロール確認
		if (RoleUtility.isSuper(mospParams)) {
			// 全ての承認可能ワークフロー情報を取得
			return cancelableList;
		}
		// 対象個人IDが承認可能なワークフロー情報リストを準備
		List<WorkflowDtoInterface> list = new ArrayList<WorkflowDtoInterface>();
		// ルートユニットリスト群を準備
		Map<String, List<ApprovalRouteUnitDtoInterface>> routeUnitMap = new HashMap<String, List<ApprovalRouteUnitDtoInterface>>();
		// 承認者ユニットコード群を準備
		Set<String> approverSet = new HashSet<String>();
		// 非承認者ユニットコード群を準備
		Set<String> notApproverSet = new HashSet<String>();
		// ワークフロー情報毎に処理
		for (WorkflowDtoInterface dto : cancelableList) {
			// 承認者確認
			if (isApprover(dto, personalId, routeUnitMap, approverSet, notApproverSet)) {
				// リストにワークフロー情報を追加
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public Map<String, Map<Long, WorkflowDtoInterface>> getApprovableMap(String personalId, Set<String> functionCodeSet)
			throws MospException {
		// 承認可能ワークフロー情報マップ準備
		Map<String, Map<Long, WorkflowDtoInterface>> approvableMap = new HashMap<String, Map<Long, WorkflowDtoInterface>>();
		// 機能コード毎にリストを作成し承認可能ワークフロー情報情報マップに追加
		for (String function : functionCodeSet) {
			approvableMap.put(function, new HashMap<Long, WorkflowDtoInterface>());
		}
		// 承認可能ワークフロー情報リスト取得
		List<WorkflowDtoInterface> approvableList = getApprovableList(personalId, functionCodeSet);
		// 承認可能ワークフロー毎に処理
		for (WorkflowDtoInterface dto : approvableList) {
			// 承認可能ワークフロー情報マップに設定
			approvableMap.get(dto.getFunctionCode()).put(dto.getWorkflow(), dto);
		}
		return approvableMap;
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> getApprovableMap(String personalId, String functionCode)
			throws MospException {
		// 承認可能ワークフロー情報群を準備
		Map<Long, WorkflowDtoInterface> approvableMap = new TreeMap<Long, WorkflowDtoInterface>();
		// 機能コード群を準備
		Set<String> functionCodes = new HashSet<String>();
		functionCodes.add(functionCode);
		// 承認可能ワークフロー情報リスト取得
		List<WorkflowDtoInterface> approvableList = getApprovableList(personalId, functionCodes);
		// 承認可能ワークフロー毎に処理
		for (WorkflowDtoInterface dto : approvableList) {
			// 承認可能ワークフロー情報マップに設定
			approvableMap.put(dto.getWorkflow(), dto);
		}
		// 承認可能ワークフロー情報群を取得
		return approvableMap;
	}
	
	@Override
	public Map<String, Map<Long, WorkflowDtoInterface>> getSubApprovableMap(String personalId,
			Set<String> functionCodeSet, int workflowType, Map<String, Map<Long, WorkflowDtoInterface>> approvableMap)
			throws MospException {
		// 代理承認可能ワークフロー情報マップ準備
		Map<String, Map<Long, WorkflowDtoInterface>> subAapprovableMap = new HashMap<String, Map<Long, WorkflowDtoInterface>>();
		// 機能コード毎にリストを作成し代理承認可能ワークフロー情報情報マップに追加
		for (String function : functionCodeSet) {
			subAapprovableMap.put(function, new HashMap<Long, WorkflowDtoInterface>());
		}
		// 特権ロール確認
		if (RoleUtility.isSuper(mospParams)) {
			// 空の代理承認可能ワークフロー情報マップを取得(マップ特権ロールは全ての申請を操作できるため)
			return subAapprovableMap;
		}
		// システム日付における代理情報を取得
		List<SubApproverDtoInterface> subApproverList = subApproverReference.findForSubApproverId(personalId,
				workflowType, getSystemDate(), getSystemDate());
		// 代理情報毎に処理
		for (SubApproverDtoInterface subApproverDto : subApproverList) {
			// 代理承認可能ワークフロー情報リスト取得
			List<WorkflowDtoInterface> subAapprovableList = getApprovableList(subApproverDto.getPersonalId(),
					functionCodeSet);
			// 代理承認可能ワークフロー毎に処理
			for (WorkflowDtoInterface dto : subAapprovableList) {
				// ワークフロー重複確認
				if (approvableMap.get(dto.getFunctionCode()).containsKey(dto.getWorkflow())) {
					// 設定不要(対象ワークフローが承認可能ワークフロー情報マップに存在する場合)
					continue;
				}
				// 代理承認可能ワークフロー情報マップに設定
				subAapprovableMap.get(dto.getFunctionCode()).put(dto.getWorkflow(), dto);
			}
		}
		return subAapprovableMap;
	}
	
	@Override
	public Map<String, Map<Long, WorkflowDtoInterface>> getCancelableMap(String personalId, Set<String> functionCodeSet)
			throws MospException {
		// 承認可能ワークフロー情報マップ準備
		Map<String, Map<Long, WorkflowDtoInterface>> approvableMap = new HashMap<String, Map<Long, WorkflowDtoInterface>>();
		// 機能コード毎にリストを作成し承認可能ワークフロー情報情報マップに追加
		for (String function : functionCodeSet) {
			approvableMap.put(function, new HashMap<Long, WorkflowDtoInterface>());
		}
		// 承認可能ワークフロー情報リスト取得
		List<WorkflowDtoInterface> approvableList = getCancelableList(personalId, functionCodeSet);
		// 承認可能ワークフロー毎に処理
		for (WorkflowDtoInterface dto : approvableList) {
			// 承認可能ワークフロー情報マップに設定
			approvableMap.get(dto.getFunctionCode()).put(dto.getWorkflow(), dto);
		}
		return approvableMap;
	}
	
	@Override
	public Map<String, Map<Long, WorkflowDtoInterface>> getSubCancelableMap(String personalId,
			Set<String> functionCodeSet, int workflowType, Map<String, Map<Long, WorkflowDtoInterface>> approvableMap)
			throws MospException {
		// 代理承認可能ワークフロー情報マップ準備
		Map<String, Map<Long, WorkflowDtoInterface>> subAapprovableMap = new HashMap<String, Map<Long, WorkflowDtoInterface>>();
		// 機能コード毎にリストを作成し代理承認可能ワークフロー情報情報マップに追加
		for (String function : functionCodeSet) {
			subAapprovableMap.put(function, new HashMap<Long, WorkflowDtoInterface>());
		}
		// 特権ロール確認
		if (RoleUtility.isSuper(mospParams)) {
			// 空の代理承認可能ワークフロー情報マップを取得(マップ特権ロールは全ての申請を操作できるため)
			return subAapprovableMap;
		}
		// システム日付における代理情報を取得
		List<SubApproverDtoInterface> subApproverList = subApproverReference.findForSubApproverId(personalId,
				workflowType, getSystemDate(), getSystemDate());
		// 代理情報毎に処理
		for (SubApproverDtoInterface subApproverDto : subApproverList) {
			// 代理承認可能ワークフロー情報リスト取得
			List<WorkflowDtoInterface> subAapprovableList = getCancelableList(subApproverDto.getPersonalId(),
					functionCodeSet);
			// 代理承認可能ワークフロー毎に処理
			for (WorkflowDtoInterface dto : subAapprovableList) {
				// ワークフロー重複確認
				if (approvableMap.get(dto.getFunctionCode()).containsKey(dto.getWorkflow())) {
					// 設定不要(対象ワークフローが承認可能ワークフロー情報マップに存在する場合)
					continue;
				}
				// 代理承認可能ワークフロー情報マップに設定
				subAapprovableMap.get(dto.getFunctionCode()).put(dto.getWorkflow(), dto);
			}
		}
		return subAapprovableMap;
	}
	
	@Override
	public List<WorkflowDtoInterface> getEffectiveList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet, String state, Set<String> personalIdSet, Set<String> subordinateIdSet)
			throws MospException {
		// 有効ワークフロー情報リスト準備
		List<WorkflowDtoInterface> effectiveList;
		// ワークフロー状態確認
		if (state.equals(PlatformConst.CODE_STATUS_APPLY)) {
			// 未承認ワークフロー情報取得
			effectiveList = workflowReference.getNonApprovedList(fromDate, toDate, functionCodeSet);
		} else if (state.equals(PlatformConst.CODE_STATUS_COMPLETE)) {
			// 承認済ワークフロー情報取得
			effectiveList = workflowReference.getCompletedList(fromDate, toDate, functionCodeSet);
		} else if (state.equals(PlatformConst.CODE_STATUS_REVERT)) {
			// 差戻ワークフロー情報取得
			effectiveList = workflowReference.getRevertedList(fromDate, toDate, functionCodeSet);
		} else if (state.equals(PlatformConst.CODE_STATUS_CANCEL_APPLY)
				|| state.equals(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY)) {
			// 解除申ワークフロー情報取得
			effectiveList = workflowReference.getCancelAppliedList(fromDate, toDate, functionCodeSet);
		} else {
			// 有効ワークフロー情報取得
			effectiveList = workflowReference.getEffectiveList(fromDate, toDate, functionCodeSet);
		}
		// 検索条件絞込用ワークフロー情報リストを準備
		List<WorkflowDtoInterface> searchList = new ArrayList<WorkflowDtoInterface>();
		// ワークフロー情報毎に処理
		for (WorkflowDtoInterface dto : effectiveList) {
			// 検索条件による絞込
			if (personalIdSet == null || personalIdSet.contains(dto.getPersonalId())) {
				// 検索条件絞込用ワークフロー情報リストに追加
				searchList.add(dto);
			}
		}
		// 計算ロール確認
		if (RoleUtility.isCalculator(mospParams)) {
			// 全ての有効ワークフロー情報を取得
			return searchList;
		}
		// 対象個人IDが承認可能なワークフロー情報リストを準備
		List<WorkflowDtoInterface> list = new ArrayList<WorkflowDtoInterface>();
		// ワークフロー情報毎に処理
		for (WorkflowDtoInterface dto : searchList) {
			// 部下確認
			if (subordinateIdSet.contains(dto.getPersonalId())) {
				// リストにワークフロー情報を追加
				list.add(dto);
				continue;
			}
			// 承認者確認
			if (isOneOfApprover(dto, personalId)) {
				// リストにワークフロー情報を追加
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public List<WorkflowDtoInterface> getCompletedList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException {
		return workflowReference.getCompletedList(personalId, fromDate, toDate, functionCodeSet);
	}
	
	@Override
	public boolean isApprovable(WorkflowDtoInterface dto) {
		// ワークフロー状況確認(未承認)
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_APPLY)) {
			return true;
		}
		// ワークフロー状況確認(承認)
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_APPROVED)) {
			return true;
		}
		// ワークフロー状況確認(承認解除)
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL)) {
			return true;
		}
		// ワークフロー状況確認(差戻で、ワークフロー段階が0でない)
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)
				&& dto.getWorkflowStage() != PlatformConst.WORKFLOW_STAGE_ZERO) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isCancelApprovable(WorkflowDtoInterface dto) {
		return dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL_APPLY);
	}
	
	@Override
	public boolean isCancelWithDrawnApprovable(WorkflowDtoInterface dto) {
		return dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY);
	}
	
	@Override
	public boolean isCompleted(long workflow) throws MospException {
		// ワークフロー情報を取得し承認済を確認
		return isCompleted(workflowReference.getLatestWorkflowInfo(workflow));
	}
	
	@Override
	public boolean isCompleted(WorkflowDtoInterface dto) {
		// ワークフロー状況確認
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isWithDrawn(long workflow) throws MospException {
		// ワークフロー情報を取得し状況を確認
		return isWithDrawn(workflowReference.getLatestWorkflowInfo(workflow));
	}
	
	@Override
	public boolean isWithDrawn(WorkflowDtoInterface dto) {
		// ワークフロー状況確認
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_WITHDRAWN)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isDraft(long workflow) throws MospException {
		// ワークフロー情報を取得し状況を確認
		return isDraft(workflowReference.getLatestWorkflowInfo(workflow));
	}
	
	@Override
	public boolean isDraft(WorkflowDtoInterface dto) {
		// ワークフロー状況確認
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_DRAFT)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isFirstReverted(long workflow) throws MospException {
		// ワークフロー情報を取得し状況を確認
		return isFirstReverted(workflowReference.getLatestWorkflowInfo(workflow));
	}
	
	@Override
	public boolean isFirstReverted(WorkflowDtoInterface dto) {
		// ワークフロー状況確認(差戻で、ワークフロー段階が0)
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)
				&& dto.getWorkflowStage() == PlatformConst.WORKFLOW_STAGE_ZERO) {
			return true;
		}
		return false;
	}
	
	@Override
	public String[][][] getArrayForApproverSetting(String personalId, Date targetDate, int workflowType)
			throws MospException {
		// 承認ルート人事マスタ一覧
		List<List<String[]>> approvalRouteList = getRouteApproverList(personalId, targetDate, workflowType);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return new String[0][][];
		}
		// 承認階層分の申請用承認者情報配列準備
		String[][][] aryApproverSetting = new String[approvalRouteList.size()][][];
		// 承認階層インデックス準備
		int approvalLevel = 0;
		// 承認階層毎に申請用承認者情報を作成
		for (List<String[]> approverList : approvalRouteList) {
			// i次承認者配列作成用リスト準備
			List<String[]> list = new ArrayList<String[]>();
			// 承認者リストから申請用承認者情報を作成
			for (String[] approver : approverList) {
				// 申請用承認者情報配列作成用リストに追加
				list.add(approver);
			}
			aryApproverSetting[approvalLevel++] = list.toArray(new String[list.size()][]);
		}
		return aryApproverSetting;
	}
	
}
