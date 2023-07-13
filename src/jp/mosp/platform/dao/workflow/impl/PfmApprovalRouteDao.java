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
import jp.mosp.platform.dao.workflow.ApprovalRouteDaoInterface;
import jp.mosp.platform.dao.workflow.ApprovalRouteUnitDaoInterface;
import jp.mosp.platform.dao.workflow.ApprovalUnitDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfmApprovalRouteDto;

/**
 * 承認ルートマスタDAO
 */
public class PfmApprovalRouteDao extends PlatformDao implements ApprovalRouteDaoInterface {
	
	/**
	 * 承認ルートマスタ。
	 */
	public static final String	TABLE						= "pfm_approval_route";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_APPROVAL_ROUTE_ID	= "pfm_approval_route_id";
	
	/**
	 * ルートコード。
	 */
	public static final String	COL_ROUTE_CODE				= "route_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * ルート名称。
	 */
	public static final String	COL_ROUTE_NAME				= "route_name";
	
	/**
	 * 承認階層。
	 */
	public static final String	COL_APPROVAL_COUNT			= "approval_count";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_PFM_APPROVAL_ROUTE_ID;
	
	/**
	 * ユニットコード。
	 */
	public static final String	UNIT_CODE					= PfaApprovalRouteUnitDao.COL_UNIT_CODE;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmApprovalRouteDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public List<ApprovalRouteDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_INACTIVATE_FLAG);
			sb.append(" = ");
			sb.append(MospConst.DELETE_FLAG_OFF);
			sb.append(" ");
			sb.append(getOrderByColumn(COL_ROUTE_CODE));
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
	public List<ApprovalRouteDtoInterface> findForHistory(String routeCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
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
	public ApprovalRouteDtoInterface findForInfo(String routeCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, routeCode);
			setParam(index++, activateDate);
			executeQuery();
			ApprovalRouteDtoInterface dto = null;
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
	public ApprovalRouteDtoInterface findForKey(String routeCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, routeCode);
			setParam(index++, activateDate);
			executeQuery();
			ApprovalRouteDtoInterface dto = null;
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
	public List<ApprovalRouteDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
			ApprovalRouteUnitDaoInterface routeUnitDao;
			routeUnitDao = (ApprovalRouteUnitDaoInterface)loadDao(ApprovalRouteUnitDaoInterface.class);
			ApprovalUnitDaoInterface unitDao = (ApprovalUnitDaoInterface)loadDao(ApprovalUnitDaoInterface.class);
			// 検索パラメータ取得
			Date activateDate = (Date)param.get("activateDate");
			String approvalCount = String.valueOf(param.get("approvalCount"));
			String routeCode = String.valueOf(param.get("routeCode"));
			String routeName = String.valueOf(param.get("routeName"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			String unitCode = String.valueOf(param.get("unitCode"));
			String unitName = String.valueOf(param.get("unitName"));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_ROUTE_CODE));
			sb.append(and());
			sb.append(like(COL_ROUTE_NAME));
			if (!approvalCount.equals("")) {
				sb.append(and());
				sb.append(equal(COL_APPROVAL_COUNT));
			}
			if (!inactivateFlag.equals("")) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			// ユニットコードまたはユニット名称が入力されていた場合
			if (!unitCode.isEmpty() || !unitName.isEmpty()) {
				sb.append(and());
				sb.append(COL_ROUTE_CODE);
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(routeUnitDao.getQueryForUnitCode());
				if (!unitCode.isEmpty()) {
					sb.append(and());
					sb.append(like(UNIT_CODE));
				}
				if (!unitName.isEmpty()) {
					sb.append(and());
					sb.append(UNIT_CODE);
					sb.append(in());
					sb.append(leftParenthesis());
					sb.append(unitDao.getQueryForUnitName());
					sb.append(rightParenthesis());
				}
				sb.append(rightParenthesis());
			}
			
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, startWithParam(routeCode));
			setParam(index++, containsParam(routeName));
			if (!approvalCount.equals("")) {
				setParam(index++, Integer.parseInt(approvalCount));
			}
			if (!inactivateFlag.equals("")) {
				setParam(index++, Integer.parseInt(inactivateFlag));
			}
			if (!unitCode.isEmpty() || !unitName.isEmpty()) {
				setParam(index++, activateDate);
				if (!unitCode.isEmpty()) {
					setParam(index++, startWithParam(unitCode));
				}
				if (!unitName.isEmpty()) {
					setParam(index++, activateDate);
					setParam(index++, containsParam(unitName));
				}
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
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			ApprovalRouteDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmApprovalRouteId());
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
	public BaseDto mapping() throws MospException {
		PfmApprovalRouteDto dto = new PfmApprovalRouteDto();
		dto.setPfmApprovalRouteId(getLong(COL_PFM_APPROVAL_ROUTE_ID));
		dto.setRouteCode(getString(COL_ROUTE_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setRouteName(getString(COL_ROUTE_NAME));
		dto.setApprovalCount(getInt(COL_APPROVAL_COUNT));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ApprovalRouteDtoInterface> mappingAll() throws MospException {
		List<ApprovalRouteDtoInterface> all = new ArrayList<ApprovalRouteDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		ApprovalRouteDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmApprovalRouteId());
		setParam(index++, dto.getRouteCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getRouteName());
		setParam(index++, dto.getApprovalCount());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			ApprovalRouteDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmApprovalRouteId());
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
	public String getQueryForRouteName() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_ROUTE_CODE);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(like(COL_ROUTE_NAME));
		return sb.toString();
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected ApprovalRouteDtoInterface castDto(BaseDtoInterface baseDto) {
		return (ApprovalRouteDtoInterface)baseDto;
	}
	
}
