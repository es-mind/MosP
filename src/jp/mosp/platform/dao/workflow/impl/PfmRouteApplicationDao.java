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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.ApprovalRouteDaoInterface;
import jp.mosp.platform.dao.workflow.RouteApplicationDaoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfmRouteApplicationDto;

/**
 * ルート適用マスタDAOクラス。
 */
public class PfmRouteApplicationDao extends PlatformDao implements RouteApplicationDaoInterface {
	
	/**
	 * ルート適用マスタ。
	 */
	public static final String	TABLE							= "pfm_route_application";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_ROUTE_APPLICATION_ID	= "pfm_route_application_id";
	
	/**
	 * ルート適用コード。
	 */
	public static final String	COL_ROUTE_APPLICATION_CODE		= "route_application_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * ルート適用名称。
	 */
	public static final String	COL_ROUTE_APPLICATION_NAME		= "route_application_name";
	
	/**
	 * ルートコード。
	 */
	public static final String	COL_ROUTE_CODE					= "route_code";
	
	/**
	 * フロー区分。
	 */
	public static final String	COL_WORKFLOW_TYPE				= "workflow_type";
	
	/**
	 * ルート適用区分。
	 */
	public static final String	COL_ROUTE_APPLICATION_TYPE		= "route_application_type";
	
	/**
	 * 勤務地コード。
	 */
	public static final String	COL_WORK_PLACE_CODE				= "work_place_code";
	
	/**
	 * 雇用契約コード。
	 */
	public static final String	COL_EMPLOYMENT_CONTRACT_CODE	= "employment_contract_code";
	
	/**
	 * 所属コード。
	 */
	public static final String	COL_SECTION_CODE				= "section_code";
	
	/**
	 * 所属コード。
	 */
	public static final String	COL_POSITION_CODE				= "position_code";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_IDS				= "personal_ids";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG				= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_PFM_ROUTE_APPLICATION_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmRouteApplicationDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public List<RouteApplicationDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_APPLICATION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_INACTIVATE_FLAG);
			sb.append(" = ");
			sb.append(MospConst.INACTIVATE_FLAG_OFF);
			sb.append(" ");
			sb.append(getOrderByColumn(COL_ROUTE_APPLICATION_CODE));
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
	public List<RouteApplicationDtoInterface> findForApproverSection(String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode, Date activateDate, int workflowType)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_APPLICATION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_TYPE));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_TYPE));
			if (!workPlaceCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_WORK_PLACE_CODE));
			}
			if (!employmentContractCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
			}
			if (!sectionCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_SECTION_CODE));
			}
			if (!positionCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_POSITION_CODE));
			}
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER));
			setParam(index++, workflowType);
			if (!workPlaceCode.equals("")) {
				setParam(index++, workPlaceCode);
			}
			if (!employmentContractCode.equals("")) {
				setParam(index++, employmentContractCode);
			}
			if (!sectionCode.equals("")) {
				setParam(index++, sectionCode);
			}
			if (!positionCode.equals("")) {
				setParam(index++, positionCode);
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
	public List<RouteApplicationDtoInterface> findForHistory(String routeApplicationCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, routeApplicationCode);
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
	public RouteApplicationDtoInterface findForInfo(String routeApplicationCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, routeApplicationCode);
			setParam(index++, activateDate);
			executeQuery();
			RouteApplicationDtoInterface dto = null;
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
	public RouteApplicationDtoInterface findForKey(String routeApplicationCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, routeApplicationCode);
			setParam(index++, activateDate);
			executeQuery();
			RouteApplicationDtoInterface dto = null;
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
	public RouteApplicationDtoInterface findForPersonalId(String personalId, Date activateDate, int workflowType)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_APPLICATION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_TYPE));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_TYPE));
			sb.append(and());
			sb.append(like(COL_PERSONAL_IDS));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON));
			setParam(index++, workflowType);
			setParam(index++, containsParam(personalId));
			executeQuery();
			RouteApplicationDtoInterface dto = null;
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
	public RouteApplicationDtoInterface findForMaster(Date activateDate, int workflowType, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_APPLICATION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_TYPE));
			sb.append(and());
			sb.append(equal(COL_WORK_PLACE_CODE));
			sb.append(and());
			sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
			sb.append(and());
			sb.append(equal(COL_SECTION_CODE));
			sb.append(and());
			sb.append(equal(COL_POSITION_CODE));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, workflowType);
			setParam(index++, workPlaceCode);
			setParam(index++, employmentContractCode);
			setParam(index++, sectionCode);
			setParam(index++, positionCode);
			executeQuery();
			RouteApplicationDtoInterface dto = null;
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
	public List<RouteApplicationDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
			ApprovalRouteDaoInterface routeDao = (ApprovalRouteDaoInterface)loadDao(ApprovalRouteDaoInterface.class);
			// 検索パラメータ取得
			Date activateDate = (Date)param.get("activateDate");
			String workflowType = String.valueOf(param.get("workflowType"));
			String routeApplicationCode = String.valueOf(param.get("routeApplicationCode"));
			String routeApplicationName = String.valueOf(param.get("routeApplicationName"));
			String routeCode = String.valueOf(param.get("routeCode"));
			String routeName = String.valueOf(param.get("routeName"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			if (activateDate != null) {
				sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_APPLICATION_CODE, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_ROUTE_APPLICATION_CODE));
			sb.append(and());
			sb.append(like(COL_ROUTE_APPLICATION_NAME));
			// フロー区分が入力されていた場合
			if (!workflowType.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_WORKFLOW_TYPE));
			}
			// ルートコードが入力されていた場合
			if (!routeCode.isEmpty()) {
				sb.append(and());
				sb.append(like(COL_ROUTE_CODE));
			}
			// ルート名称が入力されていた場合
			if (!routeName.isEmpty()) {
				sb.append(and());
				sb.append(COL_ROUTE_CODE);
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(routeDao.getQueryForRouteName());
				sb.append(rightParenthesis());
			}
			if (!inactivateFlag.equals("")) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			if (activateDate != null) {
				setParam(index++, activateDate);
			}
			setParam(index++, startWithParam(routeApplicationCode));
			setParam(index++, containsParam(routeApplicationName));
			// フロー区分が入力されていた場合
			if (!workflowType.isEmpty()) {
				setParam(index++, Integer.parseInt(workflowType));
			}
			// ルートコードが入力されていた場合
			if (!routeCode.isEmpty()) {
				setParam(index++, startWithParam(routeCode));
			}
			// ルート名称が入力されていた場合
			if (!routeName.isEmpty()) {
				Date targetDate = activateDate;
				// 有効日が検索条件として設定されていない場合、現在の日付で検索
				if (targetDate == null) {
					targetDate = DateUtility.getSystemDate();
				}
				setParam(index++, targetDate);
				setParam(index++, containsParam(routeName));
			}
			if (!inactivateFlag.equals("")) {
				setParam(index++, Integer.parseInt(inactivateFlag));
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
	public String getQueryForRouteApplicationName() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_ROUTE_APPLICATION_CODE);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_ROUTE_APPLICATION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(like(COL_ROUTE_APPLICATION_NAME));
		return sb.toString();
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			RouteApplicationDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmRouteApplicationId());
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
		PfmRouteApplicationDto dto = new PfmRouteApplicationDto();
		dto.setPfmRouteApplicationId(getLong(COL_PFM_ROUTE_APPLICATION_ID));
		dto.setRouteApplicationCode(getString(COL_ROUTE_APPLICATION_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setRouteApplicationName(getString(COL_ROUTE_APPLICATION_NAME));
		dto.setRouteCode(getString(COL_ROUTE_CODE));
		dto.setWorkflowType(getInt(COL_WORKFLOW_TYPE));
		dto.setRouteApplicationType(getInt(COL_ROUTE_APPLICATION_TYPE));
		dto.setWorkPlaceCode(getString(COL_WORK_PLACE_CODE));
		dto.setEmploymentContractCode(getString(COL_EMPLOYMENT_CONTRACT_CODE));
		dto.setSectionCode(getString(COL_SECTION_CODE));
		dto.setPositionCode(getString(COL_POSITION_CODE));
		dto.setPersonalIds(getString(COL_PERSONAL_IDS));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<RouteApplicationDtoInterface> mappingAll() throws MospException {
		List<RouteApplicationDtoInterface> all = new ArrayList<RouteApplicationDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		RouteApplicationDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmRouteApplicationId());
		setParam(index++, dto.getRouteApplicationCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getRouteApplicationName());
		setParam(index++, dto.getRouteCode());
		setParam(index++, dto.getWorkflowType());
		setParam(index++, dto.getRouteApplicationType());
		setParam(index++, dto.getWorkPlaceCode());
		setParam(index++, dto.getEmploymentContractCode());
		setParam(index++, dto.getSectionCode());
		setParam(index++, dto.getPositionCode());
		setParam(index++, dto.getPersonalIds());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			RouteApplicationDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmRouteApplicationId());
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
	public List<RouteApplicationDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate)
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
			sb.append(getOrderByColumn(COL_ROUTE_CODE, COL_ACTIVATE_DATE));
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
	public RouteApplicationDtoInterface findFormerInfo(String applicationCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_CODE));
			sb.append(and());
			sb.append(less(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			sb.append(getDesc());
			prepareStatement(sb.toString());
			setParam(index++, applicationCode);
			setParam(index++, activateDate);
			executeQuery();
			RouteApplicationDtoInterface dto = null;
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
	public RouteApplicationDtoInterface findLatterInfo(String applicationCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_CODE));
			sb.append(and());
			sb.append(greater(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, applicationCode);
			setParam(index++, activateDate);
			executeQuery();
			RouteApplicationDtoInterface dto = null;
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
	public List<RouteApplicationDtoInterface> findMasterDuplicated(Date startDate, Date endDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode, int workflowType)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			sb.append(and());
			sb.append(greater(COL_ACTIVATE_DATE));
			sb.append(and());
			if (endDate != null) {
				sb.append(less(COL_ACTIVATE_DATE));
				sb.append(and());
			}
			sb.append(equal(COL_ROUTE_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_TYPE));
			sb.append(and());
			sb.append(equal(COL_WORK_PLACE_CODE));
			sb.append(and());
			sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
			sb.append(and());
			sb.append(equal(COL_SECTION_CODE));
			sb.append(and());
			sb.append(equal(COL_POSITION_CODE));
			prepareStatement(sb.toString());
			setParam(index++, startDate);
			if (endDate != null) {
				setParam(index++, endDate);
			}
			setParam(index++, workflowType);
			setParam(index++, workPlaceCode);
			setParam(index++, employmentContractCode);
			setParam(index++, sectionCode);
			setParam(index++, positionCode);
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
	public List<RouteApplicationDtoInterface> findPersonDuplicated(Date startDate, Date endDate, String personalId,
			int workflowType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			sb.append(and());
			sb.append(greater(COL_ACTIVATE_DATE));
			if (endDate != null) {
				sb.append(and());
				sb.append(less(COL_ACTIVATE_DATE));
			}
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON)));
			sb.append(and());
			sb.append(equal(COL_WORKFLOW_TYPE));
			sb.append(and());
			sb.append(like(COL_PERSONAL_IDS));
			prepareStatement(sb.toString());
			setParam(index++, startDate);
			if (endDate != null) {
				setParam(index++, endDate);
			}
			setParam(index++, workflowType);
			setParam(index++, containsParam(personalId));
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
	public List<RouteApplicationDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			if (startDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_ACTIVATE_DATE));
			}
			if (endDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_ACTIVATE_DATE));
			}
			sb.append(and());
			sb.append(equal(COL_ROUTE_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON)));
			sb.append(and());
			sb.append(like(COL_PERSONAL_IDS));
			prepareStatement(sb.toString());
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
			}
			setParam(index++, containsParam(personalId));
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
	protected RouteApplicationDtoInterface castDto(BaseDtoInterface baseDto) {
		return (RouteApplicationDtoInterface)baseDto;
	}
	
}
