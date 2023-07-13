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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.workflow.SubApproverDaoInterface;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PftSubApproverDto;

/**
 * 代理承認者テーブルDAOクラス。
 */
public class PftSubApproverDao extends PlatformDao implements SubApproverDaoInterface {
	
	/**
	 * 代理承認者テーブル。
	 */
	public static final String	TABLE					= "pft_sub_approver";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFT_SUB_APPROVER_ID	= "pft_sub_approver_id";
	
	/**
	 * 代理承認者登録No。
	 */
	public static final String	COL_SUB_APPROVER_NO		= "sub_approver_no";
	
	/**
	 * 代理元個人ID。
	 */
	public static final String	COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * フロー区分。
	 */
	public static final String	COL_WORKFLOW_TYPE		= "workflow_type";
	
	/**
	 * 代理開始日。
	 */
	public static final String	COL_START_DATE			= "start_date";
	
	/**
	 * 代理終了日。
	 */
	public static final String	COL_END_DATE			= "end_date";
	
	/**
	 * 代理承認者個人ID。
	 */
	public static final String	COL_SUB_APPROVER_ID		= "sub_approver_id";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_PFT_SUB_APPROVER_ID;
	
	/**
	 * シーケンス。
	 */
	public static final String	SEQUENCE				= "pft_sub_approver_sub_approver_no_seq";
	
	
	/**
	 * コンストラクタ。
	 */
	public PftSubApproverDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		// マッピング先DTO準備
		PftSubApproverDto dto = new PftSubApproverDto();
		// レコード識別ID
		dto.setPftSubApproverId(getLong(COL_PFT_SUB_APPROVER_ID));
		// 代理承認者登録No
		dto.setSubApproverNo(getString(COL_SUB_APPROVER_NO));
		// 代理元個人ID
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		// フロー区分
		dto.setWorkflowType(getInt(COL_WORKFLOW_TYPE));
		// 代理開始日
		dto.setStartDate(getDate(COL_START_DATE));
		// 代理終了日
		dto.setEndDate(getDate(COL_END_DATE));
		// 代理承認者個人ID
		dto.setSubApproverId(getString(COL_SUB_APPROVER_ID));
		// 無効フラグ
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		// 共通情報
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<SubApproverDtoInterface> mappingAll() throws MospException {
		List<SubApproverDtoInterface> all = new ArrayList<SubApproverDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			SubApproverDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPftSubApproverId());
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
			SubApproverDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPftSubApproverId());
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
		// 設定元DTO準備
		SubApproverDtoInterface dto = castDto(baseDto);
		// レコード識別ID
		setParam(index++, dto.getPftSubApproverId());
		// 代理承認者登録No
		setParam(index++, dto.getSubApproverNo());
		// 代理元個人ID
		setParam(index++, dto.getPersonalId());
		// フロー区分
		setParam(index++, dto.getWorkflowType());
		// 代理開始日
		setParam(index++, dto.getStartDate());
		// 代理終了日
		setParam(index++, dto.getEndDate());
		// 代理承認者個人ID
		setParam(index++, dto.getSubApproverId());
		// 無効フラグ
		setParam(index++, dto.getInactivateFlag());
		// 共通情報
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public SubApproverDtoInterface findForKey(String subApproverNo) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SUB_APPROVER_NO));
			prepareStatement(sb.toString());
			setParam(index++, subApproverNo);
			executeQuery();
			SubApproverDtoInterface dto = null;
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
	public long getMaxMessageNo() throws MospException {
		return nextValue(SEQUENCE);
	}
	
	@Override
	public List<SubApproverDtoInterface> findForTerm(String personalId, int workflowType, Date termStart, Date termEnd)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_TYPE));
			sb.append(and());
			sb.append(lessEqual(COL_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_END_DATE));
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workflowType);
			setParam(index++, termEnd);
			setParam(index++, termStart);
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
	public List<SubApproverDtoInterface> findSubApproverForTerm(String personalId, Date startDate, Date endDate,
			int workflowType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(or());
			sb.append(equal(COL_SUB_APPROVER_ID));
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_TYPE));
			if (startDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_END_DATE));
			}
			if (endDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_START_DATE));
			}
			// ステートメントを生成
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, personalId);
			setParam(index++, personalId);
			setParam(index++, workflowType);
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
			}
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
	public List<SubApproverDtoInterface> findForSubApproverId(String subApproverId, int workflowType, Date termStart,
			Date termEnd) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SUB_APPROVER_ID));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_TYPE));
			sb.append(and());
			sb.append(lessEqual(COL_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_END_DATE));
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			prepareStatement(sb.toString());
			setParam(index++, subApproverId);
			setParam(index++, workflowType);
			setParam(index++, termEnd);
			setParam(index++, termStart);
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
	public Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * 以下のパラメータは、必須とする。<br>
	 * <ul><li>
	 * {@link SubApproverDaoInterface#SEARCH_PERSONAL_ID}
	 * </li><li>
	 * {@link SubApproverDaoInterface#SEARCH_START_DATE}
	 * </li><li>
	 * {@link SubApproverDaoInterface#SEARCH_END_DATE}
	 * </li></ul>
	 */
	@Override
	public List<SubApproverDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// 人事マスタDAO準備(サブクエリ取得用)
			HumanDaoInterface humanDao = (HumanDaoInterface)loadDao(HumanDaoInterface.class);
			// 検索条件準備
			String personalId = getSearchParam(param, SEARCH_PERSONAL_ID);
			Date startDate = (Date)param.get(SEARCH_START_DATE);
			Date endDate = (Date)param.get(SEARCH_END_DATE);
			String sectionName = getSearchParam(param, SEARCH_SECTION_NAME);
			String employeeName = getSearchParam(param, SEARCH_EMPLOYEE_NAME);
			String inactivateFlag = getSearchParam(param, SEARCH_INACTIVATE_FLAG);
			// パラメータインデックス準備
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			// WHERE部追加
			sb.append(where());
			sb.append(deleteFlagOff());
			// 検索条件SQL追加(代理元個人ID)
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			// 検索条件SQL追加(公開開始日及び公開終了日)
			sb.append(and());
			sb.append(lessEqual(COL_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_END_DATE));
			// 検索条件SQL追加(代理人所属名)
			if (sectionName.isEmpty() == false) {
				// 検索条件SQL追加(所属名)
				sb.append(and());
				sb.append(COL_SUB_APPROVER_ID);
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForSectionName());
				sb.append(rightParenthesis());
			}
			// 検索条件SQL追加(代理人社員名)
			if (employeeName.isEmpty() == false) {
				// 検索条件SQL追加(社員名)
				sb.append(humanDao.getQueryForEmployeeName(COL_SUB_APPROVER_ID));
			}
			// 検索条件SQL追加(無効フラグ)
			if (inactivateFlag.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			// ステートメント生成
			prepareStatement(sb.toString());
			// 検索条件設定(メッセージNo) 
			setParam(index++, personalId);
			// 検索条件設定(代理開始日及び代理終了日)
			setParam(index++, endDate);
			setParam(index++, startDate);
			// 検索条件追加(代理人所属名)
			if (sectionName.isEmpty() == false) {
				setParam(index++, endDate);
				setParam(index++, endDate);
				setParam(index++, containsParam(sectionName));
				setParam(index++, containsParam(sectionName));
				setParam(index++, containsParam(sectionName));
			}
			// 検索条件追加(代理人社員名)
			if (employeeName.isEmpty() == false) {
				index = humanDao.setParamsForEmployeeName(index, containsParam(employeeName), endDate, ps);
			}
			// 検索条件設定(無効フラグ)
			if (inactivateFlag.isEmpty() == false) {
				setParam(index++, Integer.parseInt(inactivateFlag));
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
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected SubApproverDtoInterface castDto(BaseDtoInterface baseDto) {
		return (SubApproverDtoInterface)baseDto;
	}
	
}
