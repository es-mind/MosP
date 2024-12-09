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
package jp.mosp.time.dao.settings.impl;

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
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmApplicationDto;

/**
 * 設定適用DAOクラス。
 */
public class TmmApplicationDao extends PlatformDao implements ApplicationDaoInterface {
	
	/**
	 * 勤怠データ。
	 */
	public static final String	TABLE							= "tmm_application";
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_APPLICATION_ID			= "tmm_application_id";
	
	/**
	 * 設定適用コード。
	 */
	public static final String	COL_APPLICATION_CODE			= "application_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * 設定適用区分。
	 */
	public static final String	COL_APPLICATION_TYPE			= "application_type";
	
	/**
	 * 設定適用名称。
	 */
	public static final String	COL_APPLICATION_NAME			= "application_name";
	
	/**
	 * 設定適用略称。
	 */
	public static final String	COL_APPLICATION_ABBR			= "application_abbr";
	
	/**
	 * 勤怠設定コード。
	 */
	public static final String	COL_WORK_SETTING_CODE			= "work_setting_code";
	
	/**
	 * カレンダコード。
	 */
	public static final String	COL_SCHEDULE_CODE				= "schedule_code";
	
	/**
	 * 有休コード。
	 */
	public static final String	COL_PAID_HOLIDAY_CODE			= "paid_holiday_code";
	
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
	 * 職位コード。
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
	public static final String	KEY_1							= COL_TMM_APPLICATION_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmApplicationDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmApplicationDto dto = new TmmApplicationDto();
		dto.setTmmApplicationId(getLong(COL_TMM_APPLICATION_ID));
		dto.setApplicationCode(getString(COL_APPLICATION_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setApplicationType(getInt(COL_APPLICATION_TYPE));
		dto.setApplicationName(getString(COL_APPLICATION_NAME));
		dto.setApplicationAbbr(getString(COL_APPLICATION_ABBR));
		dto.setWorkSettingCode(getString(COL_WORK_SETTING_CODE));
		dto.setScheduleCode(getString(COL_SCHEDULE_CODE));
		dto.setPaidHolidayCode(getString(COL_PAID_HOLIDAY_CODE));
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
	public List<ApplicationDtoInterface> mappingAll() throws MospException {
		List<ApplicationDtoInterface> all = new ArrayList<ApplicationDtoInterface>();
		while (next()) {
			all.add((ApplicationDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public ApplicationDtoInterface findForKey(String applicationCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_APPLICATION_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, applicationCode);
			setParam(index++, activateDate);
			executeQuery();
			ApplicationDtoInterface dto = null;
			if (next()) {
				dto = (ApplicationDtoInterface)mapping();
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
	public List<ApplicationDtoInterface> findForHistory(String applicationCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_APPLICATION_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, applicationCode);
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
	public List<ApplicationDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// パラメータ取得
			Date activateDate = (Date)param.get("activateDate");
			String applicationCode = String.valueOf(param.get("applicationCode"));
			Integer applicationType = (Integer)param.get("applicationType");
			String applicationName = String.valueOf(param.get("applicationName"));
			String applicationAbbr = String.valueOf(param.get("applicationAbbr"));
			String workSettingCode = String.valueOf(param.get("workSettingCode"));
			String scheduleCode = String.valueOf(param.get("scheduleCode"));
			String paidHolidayCode = String.valueOf(param.get("paidHolidayCode"));
			String workPlaceCode = String.valueOf(param.get("workPlaceCode"));
			String employmentCode = String.valueOf(param.get("employmentCode"));
			String sectionCode = String.valueOf(param.get("sectionCode"));
			String positionCode = String.valueOf(param.get("positionCode"));
			List<?> employeeCode = (List<?>)param.get("personalId");
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			// パラメータインデックス準備
			index = 1;
			// SQL作成
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(and());
			sb.append(like(COL_APPLICATION_CODE));
			sb.append(and());
			sb.append(equal(COL_APPLICATION_TYPE));
			sb.append(and());
			sb.append(like(COL_APPLICATION_NAME));
			sb.append(and());
			sb.append(like(COL_APPLICATION_ABBR));
			if (workPlaceCode != null && !workPlaceCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_WORK_PLACE_CODE));
			}
			if (employmentCode != null && !employmentCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
			}
			if (sectionCode != null && !sectionCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_SECTION_CODE));
			}
			if (positionCode != null && !positionCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_POSITION_CODE));
			}
			if (!workSettingCode.equals("null") && !workSettingCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_WORK_SETTING_CODE));
			}
			if (!scheduleCode.equals("null") && !scheduleCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_SCHEDULE_CODE));
			}
			if (!paidHolidayCode.equals("null") && !paidHolidayCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_PAID_HOLIDAY_CODE));
			}
			if (inactivateFlag != null && !inactivateFlag.equals("")) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			if (employeeCode.size() > 0) {
				sb.append(and());
				sb.append(leftParenthesis());
				for (int i = 0; i < employeeCode.size(); i++) {
					sb.append(like(COL_PERSONAL_IDS));
					sb.append(or());
				}
				sb.delete(sb.lastIndexOf(or()), sb.length());
				sb.append(rightParenthesis());
			}
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, startWithParam(applicationCode));
			setParam(index++, applicationType);
			setParam(index++, containsParam(applicationName));
			setParam(index++, containsParam(applicationAbbr));
			if (workPlaceCode.equals("") == false) {
				setParam(index++, workPlaceCode);
			}
			if (employmentCode.equals("") == false) {
				setParam(index++, employmentCode);
			}
			if (sectionCode.equals("") == false) {
				setParam(index++, sectionCode);
			}
			if (positionCode.equals("") == false) {
				setParam(index++, positionCode);
			}
			if (!workSettingCode.equals("null") && !workSettingCode.equals("")) {
				setParam(index++, workSettingCode);
			}
			if (!scheduleCode.equals("null") && !scheduleCode.equals("")) {
				setParam(index++, scheduleCode);
			}
			if (!paidHolidayCode.equals("null") && !paidHolidayCode.equals("")) {
				setParam(index++, paidHolidayCode);
			}
			if (inactivateFlag.equals("") == false) {
				setParam(index++, Integer.parseInt(inactivateFlag));
			}
			if (employeeCode.size() > 0) {
				for (int i = 0; i < employeeCode.size(); i++) {
					setParam(index++, containsParam(String.valueOf(employeeCode.get(i))));
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
	public ApplicationDtoInterface findFormerInfo(String applicationCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_APPLICATION_CODE));
			sb.append(and());
			sb.append(less(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, applicationCode);
			setParam(index++, activateDate);
			executeQuery();
			ApplicationDtoInterface dto = null;
			if (next()) {
				dto = (ApplicationDtoInterface)mapping();
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
	public ApplicationDtoInterface findLatterInfo(String applicationCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_APPLICATION_CODE));
			sb.append(and());
			sb.append(greater(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, applicationCode);
			setParam(index++, activateDate);
			executeQuery();
			ApplicationDtoInterface dto = null;
			if (next()) {
				dto = (ApplicationDtoInterface)mapping();
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
	public ApplicationDtoInterface findForPersonalId(Date activateDate, String personalId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			sb.append(and());
			sb.append(equal(COL_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON)));
			sb.append(and());
			sb.append(like(COL_PERSONAL_IDS));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, containsParam(personalId));
			executeQuery();
			ApplicationDtoInterface dto = null;
			if (next()) {
				dto = (ApplicationDtoInterface)mapping();
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
	public List<ApplicationDtoInterface> findPersonDuplicated(Date startDate, Date endDate, String personalId)
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
			if (endDate != null) {
				sb.append(and());
				sb.append(less(COL_ACTIVATE_DATE));
			}
			sb.append(and());
			sb.append(equal(COL_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON)));
			sb.append(and());
			sb.append(like(COL_PERSONAL_IDS));
			prepareStatement(sb.toString());
			setParam(index++, startDate);
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
	
	@Override
	public ApplicationDtoInterface findForMaster(Date activateDate, String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			sb.append(and());
			sb.append(equal(COL_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)));
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
			setParam(index++, workPlaceCode);
			setParam(index++, employmentContractCode);
			setParam(index++, sectionCode);
			setParam(index++, positionCode);
			executeQuery();
			ApplicationDtoInterface dto = null;
			if (next()) {
				dto = (ApplicationDtoInterface)mapping();
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
	public List<ApplicationDtoInterface> findMasterDuplicated(Date startDate, Date endDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
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
			sb.append(equal(COL_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)));
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
	public List<ApplicationDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate)
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
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			ApplicationDtoInterface dto = (ApplicationDtoInterface)baseDto;
			setParam(index++, dto.getTmmApplicationId());
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
			ApplicationDtoInterface dto = (ApplicationDtoInterface)baseDto;
			setParam(index++, dto.getTmmApplicationId());
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
		ApplicationDtoInterface dto = (ApplicationDtoInterface)baseDto;
		setParam(index++, dto.getTmmApplicationId());
		setParam(index++, dto.getApplicationCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getApplicationType());
		setParam(index++, dto.getApplicationName());
		setParam(index++, dto.getApplicationAbbr());
		setParam(index++, dto.getWorkSettingCode());
		setParam(index++, dto.getScheduleCode());
		setParam(index++, dto.getPaidHolidayCode());
		setParam(index++, dto.getWorkPlaceCode());
		setParam(index++, dto.getEmploymentContractCode());
		setParam(index++, dto.getSectionCode());
		setParam(index++, dto.getPositionCode());
		setParam(index++, dto.getPersonalIds());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	/**
	 * 有効日以前で最新の情報を取得するための条件文を取得する。<br>
	 * @return 有効日以前で最新の情報を取得するための条件文
	 */
	@Override
	public StringBuffer getQueryForMaxActivateDate() {
		StringBuffer sb = new StringBuffer();
		sb.append(COL_ACTIVATE_DATE);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(selectMax(COL_ACTIVATE_DATE));
		sb.append(from(TABLE));
		sb.append("AS A ");
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(TABLE + "." + COL_APPLICATION_CODE);
		sb.append(" = A." + COL_APPLICATION_CODE);
		sb.append(and());
		sb.append(lessEqual(COL_ACTIVATE_DATE));
		sb.append(rightParenthesis());
		return sb;
	}
	
	@Override
	public List<ApplicationDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(getOrderByColumn(COL_APPLICATION_CODE));
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
	public List<ApplicationDtoInterface> findForCheckTerm(Date fromActivateDate, Date toActivateDate)
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
			sb.append(getOrderByColumn(COL_APPLICATION_CODE, COL_ACTIVATE_DATE));
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
	public List<ApplicationDtoInterface> findForTerm(Date startDate, Date endDate) throws MospException {
		try {
			index = 1;
			// SELECT文追加
			StringBuffer sb = getSelectQuery(getClass());
			// WHERE句追加
			sb.append(where());
			// 削除されていない情報を取得
			sb.append(deleteFlagOff());
			// 有効日範囲による条件
			if (startDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_ACTIVATE_DATE));
			}
			if (endDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_ACTIVATE_DATE));
			}
			// ソート
			sb.append(getOrderByColumn(COL_APPLICATION_CODE, COL_ACTIVATE_DATE));
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定(有効日範囲による条件)
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
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
}
