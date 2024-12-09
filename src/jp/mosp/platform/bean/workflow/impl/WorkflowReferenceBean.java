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
package jp.mosp.platform.bean.workflow.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;

/**
 * ワークフロー参照処理。<br>
 */
public class WorkflowReferenceBean extends PlatformBean implements WorkflowReferenceBeanInterface {
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkflowReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(WorkflowDaoInterface.class);
	}
	
	@Override
	public WorkflowDtoInterface getLatestWorkflowInfo(long workflow) throws MospException {
		return dao.findForKey(workflow);
	}
	
	@Override
	public WorkflowDtoInterface findForId(long id) throws MospException {
		BaseDtoInterface baseDto = findForKey(dao, id, false);
		if (baseDto != null) {
			return (WorkflowDtoInterface)baseDto;
		}
		return null;
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> getWorkflows(Set<Long> workflowSet) throws MospException {
		return dao.findForInKey(workflowSet);
	}
	
	@Override
	public List<WorkflowDtoInterface> getApprovableList(Set<String> functionCodeSet) throws MospException {
		return dao.findApprovable(functionCodeSet);
	}
	
	@Override
	public List<WorkflowDtoInterface> getCancelableList(Set<String> functionCodeSet) throws MospException {
		return dao.findForCondition(null, null, functionCodeSet, getCancelAppliedSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getEffectiveList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, WorkflowUtility.getEffectiveStatuses());
	}
	
	@Override
	public List<WorkflowDtoInterface> getCompletedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, WorkflowUtility.getCompletedStatuses());
	}
	
	@Override
	public List<WorkflowDtoInterface> getCompletedList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException {
		return dao.findForCondition(personalId, fromDate, toDate, functionCodeSet,
				WorkflowUtility.getCompletedStatuses());
	}
	
	@Override
	public List<WorkflowDtoInterface> getNonApprovedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, getNonApprovedSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getRevertedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, getRevertedSet());
	}
	
	@Override
	public List<WorkflowDtoInterface> getCancelAppliedList(Date fromDate, Date toDate, Set<String> functionCodeSet)
			throws MospException {
		return dao.findForCondition(fromDate, toDate, functionCodeSet, getCancelAppliedSet());
	}
	
	/**
	 * 差戻ワークフロー状況セットを取得する。<br>
	 * @return 差戻ワークフロー状況セット
	 */
	protected Set<String> getRevertedSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(PlatformConst.CODE_STATUS_REVERT);
		return set;
	}
	
	/**
	 * 有効ワークフロー状況セットを取得する。<br>
	 * @return 有効ワークフロー状況セット
	 */
	protected Set<String> getNonApprovedSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(PlatformConst.CODE_STATUS_APPLY);
		set.add(PlatformConst.CODE_STATUS_APPROVED);
		return set;
	}
	
	/**
	 * 解除申ワークフロー状況セットを取得する。<br>
	 * @return 解除申ワークフロー状況セット
	 */
	protected Set<String> getCancelAppliedSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(PlatformConst.CODE_STATUS_CANCEL_APPLY);
		set.add(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY);
		return set;
	}
	
	@Override
	public List<WorkflowDtoInterface> getPersonalList(String personalId, Date startDate, Date endDate,
			Set<String> functionCodeSet) throws MospException {
		return dao.findForCondition(personalId, startDate, endDate, functionCodeSet);
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> findForPersonAndDay(String personalId, Date workflowDate,
			Set<String> functionCode) throws MospException {
		// 個人IDと対象日で対象機能コードのワークフロー情報群を取得
		return WorkflowUtility.getWorkflowMap(dao.findForPersonAndDay(personalId, workflowDate).values(), functionCode);
	}
	
}
