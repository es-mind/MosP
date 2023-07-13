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
package jp.mosp.platform.dao.workflow.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowCommentDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PftWorkflowCommentDto;

/**
 * ワークフローコメントDAOクラス。
 */
public class PftWorkflowCommentDao extends PlatformDao implements WorkflowCommentDaoInterface {
	
	/**
	 * ワークフローコメント。
	 */
	public static final String	TABLE						= "pft_workflow_comment";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFT_WORKFLOW_COMMENT_ID	= "pft_workflow_comment_id";
	
	/**
	 * ワークフロー番号。
	 */
	public static final String	COL_WORKFLOW				= "workflow";
	
	/**
	 * 段階。
	 */
	public static final String	COL_WORKFLOW_STAGE			= "workflow_stage";
	
	/**
	 * 状況。
	 */
	public static final String	COL_WORKFLOW_STATUS			= "workflow_status";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID				= "personal_id";
	
	/**
	 * コメント。
	 */
	public static final String	COL_WORKFLOW_COMMENT		= "workflow_comment";
	
	/**
	 * 日時。
	 */
	public static final String	COL_WORKFLOW_DATE			= "workflow_date";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_PFT_WORKFLOW_COMMENT_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PftWorkflowCommentDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PftWorkflowCommentDto dto = new PftWorkflowCommentDto();
		dto.setPftWorkflowCommentId(getLong(COL_PFT_WORKFLOW_COMMENT_ID));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		dto.setWorkflowStage(getInt(COL_WORKFLOW_STAGE));
		dto.setWorkflowStatus(getString(COL_WORKFLOW_STATUS));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkflowComment(getString(COL_WORKFLOW_COMMENT));
		dto.setWorkflowDate(getTimestamp(COL_WORKFLOW_DATE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkflowCommentDtoInterface> mappingAll() throws MospException {
		List<WorkflowCommentDtoInterface> all = new ArrayList<WorkflowCommentDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	/**
	 * ResultSetの内容を、DTOにマッピングし、マップとして返す。<br>
	 * <br>
	 * キーはワークフロー番号。<br>
	 * 値はワークフローコメント情報。<br>
	 * <br>
	 * @return 検索結果(DTOのMap)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Map<Long, WorkflowCommentDtoInterface> mappingAllMap() throws MospException {
		Map<Long, WorkflowCommentDtoInterface> all = new HashMap<Long, WorkflowCommentDtoInterface>();
		while (next()) {
			WorkflowCommentDtoInterface dto = castDto(mapping());
			all.put(dto.getWorkflow(), dto);
		}
		return all;
	}
	
	@Override
	public Map<Long, WorkflowCommentDtoInterface> findForInKey(Set<Long> workflowSet) throws MospException {
		if (workflowSet.isEmpty()) {
			return new HashMap<Long, WorkflowCommentDtoInterface>();
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			WorkflowCommentDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPftWorkflowCommentId());
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
			WorkflowCommentDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPftWorkflowCommentId());
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
		WorkflowCommentDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPftWorkflowCommentId());
		setParam(index++, dto.getWorkflow());
		setParam(index++, dto.getWorkflowStage());
		setParam(index++, dto.getWorkflowStatus());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkflowComment());
		setParam(index++, dto.getWorkflowDate(), true);
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public WorkflowCommentDtoInterface findForLatestCommentInfo(long workflow) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW));
			sb.append(getOrderByColumnDescLimit1(COL_PFT_WORKFLOW_COMMENT_ID));
			prepareStatement(sb.toString());
			setParam(index++, workflow);
			executeQuery();
			WorkflowCommentDtoInterface dto = null;
			if (rs.next()) {
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
	public List<WorkflowCommentDtoInterface> findForHistory(long workflow) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW));
			sb.append(and());
			sb.append(notEqual(COL_WORKFLOW_STATUS, PlatformConst.CODE_STATUS_DRAFT));
			sb.append(getOrderByColumnDesc(COL_WORKFLOW_DATE, COL_PFT_WORKFLOW_COMMENT_ID));
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
	public List<WorkflowCommentDtoInterface> findForList(long workflow) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW));
			sb.append(getOrderByColumn(COL_WORKFLOW_DATE));
			sb.append(getDesc());
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
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected WorkflowCommentDtoInterface castDto(BaseDtoInterface baseDto) {
		return (WorkflowCommentDtoInterface)baseDto;
	}
	
}
