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
package jp.mosp.platform.dao.workflow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PftWorkflowDto;

/**
 * ワークフローDAOクラス。
 */
public class PftWorkflowDao extends PlatformDao implements WorkflowDaoInterface {
	
	/**
	 * ワークフロー。
	 */
	public static final String	TABLE				= "pft_workflow";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFT_WORKFLOW_ID	= "pft_workflow_id";
	
	/**
	 * ワークフロー番号。
	 */
	public static final String	COL_WORKFLOW		= "workflow";
	
	/**
	 * 段階。
	 */
	public static final String	COL_WORKFLOW_STAGE	= "workflow_stage";
	
	/**
	 * 状況。
	 */
	public static final String	COL_WORKFLOW_STATUS	= "workflow_status";
	
	/**
	 * 申請者個人ID。
	 */
	public static final String	COL_PERSONAL_ID		= "personal_id";
	
	/**
	 * ワークフロー対象日。
	 */
	public static final String	COL_WORKFLOW_DATE	= "workflow_date";
	
	/**
	 * ルートコード。
	 */
	public static final String	COL_ROUTE_CODE		= "route_code";
	
	/**
	 * 機能コード。
	 */
	public static final String	COL_FUNCTION_CODE	= "function_code";
	
	/**
	 * 承認者個人ID。
	 */
	public static final String	COL_APPROVER_ID		= "approver_id";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1				= COL_PFT_WORKFLOW_ID;
	
	/**
	 * シーケンス。
	 */
	public static final String	SEQUENCE			= "pft_workflow_workflow_seq";
	
	
	/**
	 * コンストラクタ。
	 */
	public PftWorkflowDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PftWorkflowDto dto = new PftWorkflowDto();
		dto.setPftWorkflowId(getLong(COL_PFT_WORKFLOW_ID));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		dto.setWorkflowStage(getInt(COL_WORKFLOW_STAGE));
		dto.setWorkflowStatus(getString(COL_WORKFLOW_STATUS));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkflowDate(getDate(COL_WORKFLOW_DATE));
		dto.setRouteCode(getString(COL_ROUTE_CODE));
		dto.setFunctionCode(getString(COL_FUNCTION_CODE));
		dto.setApproverId(getString(COL_APPROVER_ID));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkflowDtoInterface> mappingAll() throws MospException {
		List<WorkflowDtoInterface> all = new ArrayList<WorkflowDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	/**
	 * ResultSetの内容を、DTOにマッピングし、マップとして返す。<br>
	 * <br>
	 * キーはワークフロー番号。<br>
	 * 値はワークフロー情報。<br>
	 * <br>
	 * @return 検索結果(DTOのMap)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Map<Long, WorkflowDtoInterface> mappingAllMap() throws MospException {
		Map<Long, WorkflowDtoInterface> all = new HashMap<Long, WorkflowDtoInterface>();
		while (next()) {
			WorkflowDtoInterface dto = castDto(mapping());
			all.put(dto.getWorkflow(), dto);
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			WorkflowDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPftWorkflowId());
			executeUpdate();
			chkUpdate(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			WorkflowDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPftWorkflowId());
			executeUpdate();
			chkDelete(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		WorkflowDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPftWorkflowId());
		setParam(index++, dto.getWorkflow());
		setParam(index++, dto.getWorkflowStage());
		setParam(index++, dto.getWorkflowStatus());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkflowDate());
		setParam(index++, dto.getRouteCode());
		setParam(index++, dto.getFunctionCode());
		setParam(index++, dto.getApproverId());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public WorkflowDtoInterface findForKey(long workflow) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW));
			prepareStatement(sb.toString());
			setParam(index++, workflow);
			executeQuery();
			WorkflowDtoInterface dto = null;
			if (next()) {
				dto = castDto(mapping());
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> findForInKey(Set<Long> workflowSet) throws MospException {
		if (workflowSet.isEmpty()) {
			return new HashMap<Long, WorkflowDtoInterface>();
		}
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_WORKFLOW, workflowSet.size()));
			prepareStatement(sb.toString());
			setParamsIn(workflowSet);
			executeQuery();
			return mappingAllMap();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public long nextWorkflow() throws MospException {
		return nextValue(SEQUENCE);
	}
	
	@Override
	public List<WorkflowDtoInterface> findForHistory(long workflow) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(equal(COL_WORKFLOW));
			prepareStatement(sb.toString());
			setParam(index++, workflow);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<WorkflowDtoInterface> findApprovable(Set<String> functionCodeSet) throws MospException {
		try {
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			// 機能コードによる条件を設定
			sb.append(getQueryForSet(COL_FUNCTION_CODE, functionCodeSet));
			// ワークロー状況及び段階による条件を設定
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(or());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(or());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(or());
			sb.append(leftParenthesis());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(and());
			sb.append(notEqual(COL_WORKFLOW_STAGE));
			sb.append(rightParenthesis());
			sb.append(rightParenthesis());
			// ステートメント生成
			prepareStatement(sb.toString());
			// 機能コードによる条件のパラメータを設定
			setParamsForSet(functionCodeSet);
			// 検索条件パラメータ設定
			setParam(index++, PlatformConst.CODE_STATUS_APPLY);
			setParam(index++, PlatformConst.CODE_STATUS_APPROVED);
			setParam(index++, PlatformConst.CODE_STATUS_CANCEL);
			setParam(index++, PlatformConst.CODE_STATUS_REVERT);
			setParam(index++, PlatformConst.WORKFLOW_STAGE_ZERO);
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<WorkflowDtoInterface> findApprovable(Date fromDate, Date toDate) throws MospException {
		try {
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			// ワークロー状況及び段階による条件を設定
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(or());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(or());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(or());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(or());
			sb.append(leftParenthesis());
			sb.append(equal(COL_WORKFLOW_STATUS));
			sb.append(and());
			sb.append(notEqual(COL_WORKFLOW_STAGE));
			sb.append(rightParenthesis());
			sb.append(rightParenthesis());
			if (fromDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_WORKFLOW_DATE));
			}
			if (toDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_WORKFLOW_DATE));
			}
			// ステートメント生成
			prepareStatement(sb.toString());
			// 検索条件パラメータ設定
			setParam(index++, PlatformConst.CODE_STATUS_APPLY);
			setParam(index++, PlatformConst.CODE_STATUS_APPROVED);
			setParam(index++, PlatformConst.CODE_STATUS_CANCEL);
			setParam(index++, PlatformConst.CODE_STATUS_CANCEL_APPLY);
			setParam(index++, PlatformConst.CODE_STATUS_REVERT);
			setParam(index++, PlatformConst.WORKFLOW_STAGE_ZERO);
			if (fromDate != null) {
				setParam(index++, fromDate, false);
			}
			if (toDate != null) {
				setParam(index++, toDate, false);
			}
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<WorkflowDtoInterface> findFirstReverted(Date fromDate, Date toDate) throws MospException {
		try {
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_STAGE));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_STATUS));
			if (fromDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_WORKFLOW_DATE));
			}
			if (toDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_WORKFLOW_DATE));
			}
			// ステートメント生成
			prepareStatement(sb.toString());
			// 検索条件パラメータ設定
			setParam(index++, PlatformConst.WORKFLOW_STAGE_ZERO);
			setParam(index++, PlatformConst.CODE_STATUS_REVERT);
			if (fromDate != null) {
				setParam(index++, fromDate, false);
			}
			if (toDate != null) {
				setParam(index++, toDate, false);
			}
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<WorkflowDtoInterface> findForCondition(Date fromDate, Date toDate, Set<String> functionCodeSet,
			Set<String> workflowStateSet) throws MospException {
		try {
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			// ワークフロー対象日による条件を設定
			if (fromDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_WORKFLOW_DATE));
			}
			if (toDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_WORKFLOW_DATE));
			}
			// 機能コードによる条件を設定
			sb.append(getQueryForSet(COL_FUNCTION_CODE, functionCodeSet));
			// ワークロー状況による条件を設定
			sb.append(getQueryForSet(COL_WORKFLOW_STATUS, workflowStateSet));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 検索条件パラメータ設定
			if (fromDate != null) {
				setParam(index++, fromDate, false);
			}
			if (toDate != null) {
				setParam(index++, toDate, false);
			}
			// 機能コードによる条件のパラメータを設定
			setParamsForSet(functionCodeSet);
			// ワークロー状況による条件のパラメータを設定
			setParamsForSet(workflowStateSet);
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<WorkflowDtoInterface> findForCondition(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException {
		try {
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			// ワークフロー対象日による条件を設定
			if (fromDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_WORKFLOW_DATE));
			}
			if (toDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_WORKFLOW_DATE));
			}
			// 機能コードによる条件を設定
			sb.append(getQueryForSet(COL_FUNCTION_CODE, functionCodeSet));
			// ステートメント生成
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			// 検索条件パラメータ設定
			if (fromDate != null) {
				setParam(index++, fromDate, false);
			}
			if (toDate != null) {
				setParam(index++, toDate, false);
			}
			// 機能コードによる条件のパラメータを設定
			setParamsForSet(functionCodeSet);
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<WorkflowDtoInterface> findForCondition(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet, Set<String> workflowStateSet) throws MospException {
		try {
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			// ワークフロー対象日による条件を設定
			if (fromDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_WORKFLOW_DATE));
			}
			if (toDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_WORKFLOW_DATE));
			}
			// 機能コードによる条件を設定
			sb.append(getQueryForSet(COL_FUNCTION_CODE, functionCodeSet));
			// ワークロー状況による条件を設定
			sb.append(getQueryForSet(COL_WORKFLOW_STATUS, workflowStateSet));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 検索条件パラメータ設定
			setParam(index++, personalId);
			if (fromDate != null) {
				setParam(index++, fromDate, false);
			}
			if (toDate != null) {
				setParam(index++, toDate, false);
			}
			// 機能コードによる条件のパラメータを設定
			setParamsForSet(functionCodeSet);
			// ワークロー状況による条件のパラメータを設定
			setParamsForSet(workflowStateSet);
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> findForCondition(String personalId, Date fromDate, Date toDate)
			throws MospException {
		try {
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			// ワークフロー対象日による条件を設定
			if (fromDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_WORKFLOW_DATE));
			}
			if (toDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_WORKFLOW_DATE));
			}
			// ステートメント生成
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			// 検索条件パラメータ設定
			if (fromDate != null) {
				setParam(index++, fromDate, false);
			}
			if (toDate != null) {
				setParam(index++, toDate, false);
			}
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAllMap();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> findForPersonAndDay(String personalId, Date workflowDate)
			throws MospException {
		try {
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_DATE));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 検索条件パラメータ設定
			setParam(index++, personalId);
			setParam(index++, workflowDate);
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAllMap();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	/**
	 * セットによる検索条件SQLを取得する。<br>
	 * @param column 検索対象列
	 * @param set    検索条件セット
	 * @return 検索条件SQL
	 */
	protected String getQueryForSet(String column, Set<String> set) {
		StringBuffer sb = new StringBuffer();
		if (set != null && set.isEmpty() == false) {
			sb.append(and());
			sb.append(leftParenthesis());
			for (int i = 0; i < set.size(); i++) {
				sb.append(equal(column));
				sb.append(or());
			}
			sb.delete(sb.length() - or().length(), sb.length());
			sb.append(rightParenthesis());
		}
		return sb.toString();
	}
	
	/**
	 * セットによる検索条件のパラメータを設定する。<br>
	 * @param set 検索条件セット
	 * @throws MospException 検索条件パラメータの設定に失敗した場合
	 */
	protected void setParamsForSet(Set<String> set) throws MospException {
		// 検索条件確認
		if (set == null || set.isEmpty()) {
			// パラメータ設定不要
			return;
		}
		// 検索条件毎に処理
		for (String param : set) {
			// 検索条件インスタンス確認及びパラメータ設定
			setParam(index++, param);
		}
	}
	
	@Override
	public String getSubQueryForSetting(boolean useStage, boolean useStatus, boolean useRoute, boolean useFunction) {
		StringBuilder sb = new StringBuilder();
		sb.append(select());
		sb.append(COL_WORKFLOW);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		if (useStage) {
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_STAGE));
		}
		if (useStatus) {
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_STATUS));
		}
		if (useRoute) {
			sb.append(and());
			sb.append(equal(COL_ROUTE_CODE));
		}
		if (useFunction) {
			sb.append(and());
			sb.append(equal(COL_FUNCTION_CODE));
		}
		return sb.toString();
	}
	
	@Override
	public String getSubQueryForNotEqualDraft() {
		StringBuilder sb = new StringBuilder();
		sb.append(select());
		sb.append(COL_WORKFLOW);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(notEqual(COL_WORKFLOW_STATUS, PlatformConst.CODE_STATUS_DRAFT));
		return sb.toString();
	}
	
	@Override
	public String getSubQueryForNotEqualWithdrawn() {
		StringBuilder sb = new StringBuilder();
		sb.append(select());
		sb.append(COL_WORKFLOW);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(notEqual(COL_WORKFLOW_STATUS, PlatformConst.CODE_STATUS_WITHDRAWN));
		return sb.toString();
	}
	
	@Override
	public String getSubQueryForNotEqualFirstReverted() {
		StringBuilder sb = new StringBuilder();
		sb.append(select());
		sb.append(COL_WORKFLOW);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(leftParenthesis());
		sb.append(notEqual(COL_WORKFLOW_STATUS, PlatformConst.CODE_STATUS_REVERT));
		sb.append(or());
		sb.append(notEqual(COL_WORKFLOW_STAGE, PlatformConst.WORKFLOW_STAGE_ZERO));
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public String getSubQueryForApplied(String column) {
		StringBuilder sb = new StringBuilder();
		sb.append(leftParenthesis());
		sb.append(column);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(getSubQueryForNotEqualWithdrawn());
		sb.append(rightParenthesis());
		sb.append(and());
		sb.append(column);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(getSubQueryForNotEqualDraft());
		sb.append(rightParenthesis());
		sb.append(and());
		sb.append(column);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(getSubQueryForNotEqualFirstReverted());
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public String getSubQueryForCompleted(String column) {
		StringBuilder sb = new StringBuilder();
		sb.append(column);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_WORKFLOW);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(equal(COL_WORKFLOW_STATUS, PlatformConst.CODE_STATUS_COMPLETE));
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected WorkflowDtoInterface castDto(BaseDtoInterface baseDto) {
		return (WorkflowDtoInterface)baseDto;
	}
	
}
