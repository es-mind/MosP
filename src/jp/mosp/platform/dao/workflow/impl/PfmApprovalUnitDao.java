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
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dao.workflow.ApprovalUnitDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfmApprovalUnitDto;

/**
 * 承認ユニットマスタDAOクラス。
 */
public class PfmApprovalUnitDao extends PlatformDao implements ApprovalUnitDaoInterface {
	
	/**
	 * 承認ユニットマスタ。
	 */
	public static final String		TABLE						= "pfm_approval_unit";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_PFM_APPROVAL_UNIT_ID	= "pfm_approval_unit_id";
	
	/**
	 * ユニットコード。
	 */
	public static final String		COL_UNIT_CODE				= "unit_code";
	
	/**
	 * 有効日。
	 */
	public static final String		COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * ユニット名称。
	 */
	public static final String		COL_UNIT_NAME				= "unit_name";
	
	/**
	 * ユニット区分。
	 */
	public static final String		COL_UNIT_TYPE				= "unit_type";
	
	/**
	 * 承認者個人ID。
	 */
	public static final String		COL_APPROVER_PERSONAL_ID	= "approver_personal_id";
	
	/**
	 * 承認者所属コード。
	 */
	public static final String		COL_APPROVER_SECTION_CODE	= "approver_section_code";
	
	/**
	 * 承認者職位コード。
	 */
	public static final String		COL_APPROVER_POSITION_CODE	= "approver_position_code";
	
	/**
	 * 承認者職位等級範囲。
	 */
	public static final String		COL_APPROVER_POSITION_GRADE	= "approver_position_grade";
	
	/**
	 * 複数決済。
	 */
	public static final String		COL_ROUTE_STAGE				= "route_stage";
	
	/**
	 * 無効フラグ。
	 */
	public static final String		COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1						= COL_PFM_APPROVAL_UNIT_ID;
	
	/**
	 * 職位マスタDAOクラス。
	 */
	protected PositionDaoInterface	positionDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmApprovalUnitDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() throws MospException {
		positionDao = (PositionDaoInterface)loadDao(PositionDaoInterface.class);
	}
	
	@Override
	public List<ApprovalUnitDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_UNIT_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_INACTIVATE_FLAG);
			sb.append(" = ");
			sb.append(MospConst.DELETE_FLAG_OFF);
			sb.append(" ");
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
	public List<ApprovalUnitDtoInterface> findForHistory(String unitCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_UNIT_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, unitCode);
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
	public ApprovalUnitDtoInterface findForInfo(String unitCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_UNIT_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, unitCode);
			setParam(index++, activateDate);
			executeQuery();
			ApprovalUnitDtoInterface dto = null;
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
	public ApprovalUnitDtoInterface findForKey(String unitCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_UNIT_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, unitCode);
			setParam(index++, activateDate);
			executeQuery();
			ApprovalUnitDtoInterface dto = null;
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
	public List<ApprovalUnitDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
			SectionDaoInterface sectionDao = (SectionDaoInterface)loadDao(SectionDaoInterface.class);
			PositionDaoInterface positionDao = (PositionDaoInterface)loadDao(PositionDaoInterface.class);
			// 検索パラメータ取得
			Date activateDate = (Date)param.get("activateDate");
			String unitCode = String.valueOf(param.get("unitCode"));
			String unitName = String.valueOf(param.get("unitName"));
			String unitType = String.valueOf(param.get("unitType"));
			String sectionName = String.valueOf(param.get("sectionName"));
			String positionName = String.valueOf(param.get("positionName"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			// 対象日(有効日が指定されなかった場合に所属や職位の検索で用いる)
			Date targetDate = activateDate != null ? activateDate : DateUtility.getSystemDate();
			// パラメータインデックス準備
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			// 条件SQL追加
			// 有効日における最新の情報を抽出する条件SQLを追加
			if (activateDate != null) {
				sb.append(getQueryForMaxActivateDate(TABLE, COL_UNIT_CODE, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_UNIT_CODE));
			sb.append(and());
			sb.append(like(COL_UNIT_NAME));
			// ユニット区分条件SQL追加
			sb.append(and());
			sb.append(equal(COL_UNIT_TYPE));
			// 無効フラグ条件SQL追加
			if (inactivateFlag.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			// ユニット区分確認
			if (unitType.equals(PlatformConst.UNIT_TYPE_SECTION)) {
				// 所属指定での検索
				// 所属名が入力されていた場合
				if (!sectionName.isEmpty()) {
					sb.append(and());
					sb.append(COL_APPROVER_SECTION_CODE);
					sb.append(in());
					sb.append(leftParenthesis());
					sb.append(sectionDao.getQueryForSectionName());
					sb.append(rightParenthesis());
				}
				// 職位名が入力されていた場合
				if (positionName.isEmpty() == false) {
					sb.append(positionDao.getQueryForPositionName(COL_APPROVER_POSITION_CODE));
				}
			}
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件パラメータを追加
			if (activateDate != null) {
				index = setParamsForMaxActivateDate(index, activateDate, ps);
			}
			// パラメータ設定
			setParam(index++, startWithParam(unitCode));
			setParam(index++, containsParam(unitName));
			setParam(index++, unitType);
			// 無効フラグ条件パラメータ設定
			if (inactivateFlag.isEmpty() == false) {
				setParam(index++, Integer.parseInt(inactivateFlag));
			}
			// ユニット区分確認
			if (unitType.equals(PlatformConst.UNIT_TYPE_SECTION)) {
				// 所属名が入力されていた場合
				if (!sectionName.isEmpty()) {
					setParam(index++, targetDate);
					setParam(index++, containsParam(sectionName));
					setParam(index++, containsParam(sectionName));
					setParam(index++, containsParam(sectionName));
				}
				// 職位名が入力されていた場合
				if (!positionName.isEmpty()) {
					index = positionDao.setParamsForPositionName(index, positionName, targetDate, ps);
				}
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
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public List<ApprovalUnitDtoInterface> findForApproverPersonalId(String approverPersonalId, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_UNIT_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_UNIT_TYPE));
			sb.append(and());
			sb.append(like(COL_APPROVER_PERSONAL_ID));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, PlatformConst.UNIT_TYPE_PERSON);
			setParam(index++, containsParam(approverPersonalId));
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
	public List<ApprovalUnitDtoInterface> findForApproverSection(String approverSectionCode,
			String approverPositionCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_UNIT_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_UNIT_TYPE));
			sb.append(and());
			sb.append(equal(COL_APPROVER_SECTION_CODE));
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(equal(COL_APPROVER_POSITION_CODE));
			sb.append(or());
			sb.append(leftParenthesis());
			// 以上
			sb.append(equal(COL_APPROVER_POSITION_GRADE, "1"));
			sb.append(and());
			sb.append(COL_APPROVER_POSITION_CODE);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(positionDao.getQueryForPositionGrade(false));
			sb.append(rightParenthesis());
			sb.append(rightParenthesis());
			sb.append(or());
			sb.append(leftParenthesis());
			// 以下
			sb.append(equal(COL_APPROVER_POSITION_GRADE, "2"));
			sb.append(and());
			sb.append(COL_APPROVER_POSITION_CODE);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(positionDao.getQueryForPositionGrade(true));
			sb.append(rightParenthesis());
			sb.append(rightParenthesis());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, PlatformConst.UNIT_TYPE_SECTION);
			setParam(index++, approverSectionCode);
			setParam(index++, approverPositionCode);
			index = positionDao.setParamsForPositionGrande(index, approverPositionCode, activateDate, ps);
			index = positionDao.setParamsForPositionGrande(index, approverPositionCode, activateDate, ps);
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
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			ApprovalUnitDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmApprovalUnitId());
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
		PfmApprovalUnitDto dto = new PfmApprovalUnitDto();
		dto.setPfmApprovalUnitId(getLong(COL_PFM_APPROVAL_UNIT_ID));
		dto.setUnitCode(getString(COL_UNIT_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setUnitName(getString(COL_UNIT_NAME));
		dto.setUnitType(getString(COL_UNIT_TYPE));
		dto.setApproverPersonalId(getString(COL_APPROVER_PERSONAL_ID));
		dto.setApproverSectionCode(getString(COL_APPROVER_SECTION_CODE));
		dto.setApproverPositionCode(getString(COL_APPROVER_POSITION_CODE));
		dto.setApproverPositionGrade(getString(COL_APPROVER_POSITION_GRADE));
		dto.setRouteStage(getInt(COL_ROUTE_STAGE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ApprovalUnitDtoInterface> mappingAll() throws MospException {
		List<ApprovalUnitDtoInterface> all = new ArrayList<ApprovalUnitDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		ApprovalUnitDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmApprovalUnitId());
		setParam(index++, dto.getUnitCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getUnitName());
		setParam(index++, dto.getUnitType());
		setParam(index++, dto.getApproverPersonalId());
		setParam(index++, dto.getApproverSectionCode());
		setParam(index++, dto.getApproverPositionCode());
		setParam(index++, dto.getApproverPositionGrade());
		setParam(index++, dto.getRouteStage());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			ApprovalUnitDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmApprovalUnitId());
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
	public String getQueryForUnitName() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_UNIT_CODE);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_UNIT_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(like(COL_UNIT_NAME));
		return sb.toString();
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected ApprovalUnitDtoInterface castDto(BaseDtoInterface baseDto) {
		return (ApprovalUnitDtoInterface)baseDto;
	}
	
	@Override
	public List<ApprovalUnitDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate)
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
			sb.append(equal(COL_UNIT_TYPE));
			sb.append(and());
			sb.append(like(COL_APPROVER_PERSONAL_ID));
			prepareStatement(sb.toString());
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
			}
			setParam(index++, PlatformConst.UNIT_TYPE_PERSON);
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
	
}
