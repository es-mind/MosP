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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.workflow.ApprovalRouteUnitDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfaApprovalRouteUnitDto;

/**
 * 承認ルートユニットマスタDAO
 */
public class PfaApprovalRouteUnitDao extends PlatformDao implements ApprovalRouteUnitDaoInterface {
	
	/**
	 * 承認ルートマスタ。
	 */
	public static final String	TABLE							= "pfa_approval_route_unit";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_APPROVAL_ROUTE_UNIT_ID	= "pfa_approval_route_unit_id";
	
	/**
	 * ルートコード。
	 */
	public static final String	COL_ROUTE_CODE					= "route_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * 承認段階。
	 */
	public static final String	COL_APPROVAL_STAGE				= "approval_stage";
	
	/**
	 * ユニットコード。
	 */
	public static final String	COL_UNIT_CODE					= "unit_code";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG				= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_PFA_APPROVAL_ROUTE_UNIT_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaApprovalRouteUnitDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaApprovalRouteUnitDto dto = new PfaApprovalRouteUnitDto();
		dto.setPfaApprovalRouteUnitId(getLong(COL_PFA_APPROVAL_ROUTE_UNIT_ID));
		dto.setRouteCode(getString(COL_ROUTE_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setApprovalStage(getInt(COL_APPROVAL_STAGE));
		dto.setUnitCode(getString(COL_UNIT_CODE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ApprovalRouteUnitDtoInterface> mappingAll() throws MospException {
		List<ApprovalRouteUnitDtoInterface> all = new ArrayList<ApprovalRouteUnitDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public List<ApprovalRouteUnitDtoInterface> findForHistory(String routeCode, int approvalStage)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_CODE));
			sb.append(and());
			sb.append(equal(COL_APPROVAL_STAGE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, routeCode);
			setParam(index++, approvalStage);
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
	public ApprovalRouteUnitDtoInterface findForInfo(String routeCode, Date activateDate, int approvalStage)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_APPROVAL_STAGE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, routeCode);
			setParam(index++, activateDate);
			setParam(index++, approvalStage);
			executeQuery();
			ApprovalRouteUnitDtoInterface dto = null;
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
	public ApprovalRouteUnitDtoInterface findForKey(String routeCode, Date activateDate, int approvalStage)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_APPROVAL_STAGE));
			prepareStatement(sb.toString());
			setParam(index++, routeCode);
			setParam(index++, activateDate);
			setParam(index++, approvalStage);
			executeQuery();
			ApprovalRouteUnitDtoInterface dto = null;
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
	public List<ApprovalRouteUnitDtoInterface> findForRouteList(String routeCode, Date targetDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_ROUTE_CODE));
			sb.append(getOrderByColumn(COL_APPROVAL_STAGE));
			prepareStatement(sb.toString());
			setParam(index++, targetDate);
			setParam(index++, routeCode);
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
	public List<ApprovalRouteUnitDtoInterface> findForApprovalUnit(String unitCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_UNIT_CODE));
			sb.append(and());
			sb.append(COL_ROUTE_CODE);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(getQueryForUnitCode());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, unitCode);
			setParam(index++, activateDate);
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
	public List<ApprovalRouteUnitDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate)
			throws MospException {
		try {
			index = 1;
			// SELECT文追加
			StringBuffer sb = getSelectQuery(getClass());
			// WHERE句追加
			sb.append(where());
			// 削除されていない情報を取得
			sb.append(deleteFlagOff());
			// 有効日範囲による条件
			if (fromActivateDate != null) {
				sb.append(and());
				sb.append(greater(COL_ACTIVATE_DATE));
			}
			if (toActivateDate != null) {
				sb.append(and());
				sb.append(less(COL_ACTIVATE_DATE));
			}
			// ソート
			sb.append(getOrderByColumn(COL_UNIT_CODE, COL_ACTIVATE_DATE));
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定(有効日範囲による条件)
			if (fromActivateDate != null) {
				setParam(index++, fromActivateDate);
			}
			if (toActivateDate != null) {
				setParam(index++, toActivateDate);
			}
			// SQL実行
			executeQuery();
			// 結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<ApprovalRouteUnitDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(getOrderByColumn(COL_UNIT_CODE));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
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
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			ApprovalRouteUnitDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaApprovalRouteUnitId());
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
		ApprovalRouteUnitDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaApprovalRouteUnitId());
		setParam(index++, dto.getRouteCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getApprovalStage());
		setParam(index++, dto.getUnitCode());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			ApprovalRouteUnitDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaApprovalRouteUnitId());
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
	public StringBuffer getQueryForMaxActivateDate() {
		StringBuffer sb = new StringBuffer();
		sb.append(COL_ACTIVATE_DATE);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(selectMax(COL_ACTIVATE_DATE));
		sb.append(from(TABLE));
		sb.append(asTmpTable(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(equalTmpColumn(TABLE, COL_ROUTE_CODE));
		sb.append(and());
		sb.append(equalTmpColumn(TABLE, COL_APPROVAL_STAGE));
		sb.append(and());
		sb.append(lessEqual(COL_ACTIVATE_DATE));
		sb.append(rightParenthesis());
		return sb;
	}
	
	@Override
	public String getQueryForUnitCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_ROUTE_CODE);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(getQueryForMaxActivateDate());
		return sb.toString();
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected ApprovalRouteUnitDtoInterface castDto(BaseDtoInterface baseDto) {
		return (ApprovalRouteUnitDtoInterface)baseDto;
	}
	
}
