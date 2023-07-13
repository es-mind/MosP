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
package jp.mosp.time.dao.settings.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.human.impl.PfmHumanDao;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.time.dao.settings.ExportDataDaoInterface;

/**
 * エクスポートデータDAOクラス。
 */
public class ExportDataDao extends PlatformDao implements ExportDataDaoInterface {
	
	private WorkflowDaoInterface	workflowDao;
	private HumanDaoInterface		humanDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public ExportDataDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
		humanDao = (HumanDaoInterface)loadDao(HumanDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() {
		// 処理なし
		return null;
	}
	
	@Override
	public List<?> mappingAll() {
		// 処理なし
		return null;
	}
	
	@Override
	public ResultSet findForAttendance(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(select());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_EMPLOYEE_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_LAST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_FIRST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_SECTION_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(TmdAttendanceDao.TABLE, "*"));
			sb.append(from(TmdAttendanceDao.TABLE));
			sb.append("LEFT JOIN ");
			sb.append(PfmHumanDao.TABLE);
			sb.append(" ON ");
			sb.append(getExplicitTableColumn(TmdAttendanceDao.TABLE, TmdAttendanceDao.COL_PERSONAL_ID));
			sb.append(" = ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(where());
			sb.append(deleteFlagOff(getExplicitTableColumn(TmdAttendanceDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(deleteFlagOff(getExplicitTableColumn(PfmHumanDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(" =");
			sb.append(leftParenthesis());
			sb.append(selectMax(PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(from(PfmHumanDao.TABLE));
			sb.append(asTmpTable(PfmHumanDao.TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equalTmpColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(and());
			sb.append(PfmHumanDao.COL_ACTIVATE_DATE);
			sb.append(" <= ");
			sb.append(getExplicitTableColumn(TmdAttendanceDao.TABLE, TmdAttendanceDao.COL_WORK_DATE));
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(greaterEqual(TmdAttendanceDao.COL_WORK_DATE));
			sb.append(and());
			sb.append(lessEqual(TmdAttendanceDao.COL_WORK_DATE));
			sb.append(and());
			sb.append(TmdAttendanceDao.COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualDraft());
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(TmdAttendanceDao.COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			if (!workPlaceCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdAttendanceDao.TABLE, TmdAttendanceDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForWorkPlaceCode());
				sb.append(rightParenthesis());
			}
			if (!employmentCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdAttendanceDao.TABLE, TmdAttendanceDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForEmploymentContractCode());
				sb.append(rightParenthesis());
			}
			if (!sectionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdAttendanceDao.TABLE, TmdAttendanceDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					sb.append(humanDao.getQueryForLowerSection());
				} else {
					sb.append(humanDao.getQueryForSectionCode());
				}
				sb.append(rightParenthesis());
			}
			if (!positionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdAttendanceDao.TABLE, TmdAttendanceDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForPositionCode());
				sb.append(rightParenthesis());
			}
			sb.append(getOrderByColumns(PfmHumanDao.COL_EMPLOYEE_CODE, TmdAttendanceDao.COL_WORK_DATE,
					TmdAttendanceDao.COL_TIMES_WORK));
			prepareStatement(sb.toString());
			setParam(index++, startDate);
			setParam(index++, endDate);
			if (!workPlaceCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, workPlaceCode);
			}
			if (!employmentCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, employmentCode);
			}
			if (!sectionCode.isEmpty()) {
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					setParam(index++, endDate);
					setParam(index++, endDate);
					setParam(index++, sectionCode);
					setParam(index++, containsParam(sectionCode));
				} else {
					setParam(index++, endDate);
					setParam(index++, sectionCode);
				}
			}
			if (!positionCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, positionCode);
			}
			executeQuery();
			return rs;
		} catch (Throwable e) {
			throw new MospException(e);
		}
	}
	
	@Override
	public ResultSet findForTotalTime(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(select());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_EMPLOYEE_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_LAST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_FIRST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_WORK_PLACE_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_EMPLOYMENT_CONTRACT_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_SECTION_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_POSITION_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(TmdTotalTimeDao.TABLE, "*"));
			sb.append(from(TmdTotalTimeDao.TABLE));
			sb.append("LEFT JOIN ");
			sb.append(PfmHumanDao.TABLE);
			sb.append(" ON ");
			sb.append(getExplicitTableColumn(TmdTotalTimeDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
			sb.append(" = ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(where());
			sb.append(deleteFlagOff(getExplicitTableColumn(TmdTotalTimeDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(deleteFlagOff(getExplicitTableColumn(PfmHumanDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(" =");
			sb.append(leftParenthesis());
			sb.append(selectMax(PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(from(PfmHumanDao.TABLE));
			sb.append(asTmpTable(PfmHumanDao.TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equalTmpColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(and());
			sb.append(PfmHumanDao.COL_ACTIVATE_DATE);
			sb.append(" <= ");
			sb.append(getExplicitTableColumn(TmdTotalTimeDao.TABLE, TmdTotalTimeDao.COL_CALCULATION_DATE));
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(greaterEqual(TmdTotalTimeDao.COL_CALCULATION_DATE));
			sb.append(and());
			sb.append(lessEqual(TmdTotalTimeDao.COL_CALCULATION_DATE));
			if (!workPlaceCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdTotalTimeDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForWorkPlaceCode());
				sb.append(rightParenthesis());
			}
			if (!employmentCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdTotalTimeDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForEmploymentContractCode());
				sb.append(rightParenthesis());
			}
			if (!sectionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdTotalTimeDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					sb.append(humanDao.getQueryForLowerSection());
				} else {
					sb.append(humanDao.getQueryForSectionCode());
				}
				sb.append(rightParenthesis());
			}
			if (!positionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdTotalTimeDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForPositionCode());
				sb.append(rightParenthesis());
			}
			sb.append(getOrderByColumns(PfmHumanDao.COL_EMPLOYEE_CODE, TmdTotalTimeDao.COL_CALCULATION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, startDate);
			setParam(index++, endDate);
			if (!workPlaceCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, workPlaceCode);
			}
			if (!employmentCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, employmentCode);
			}
			if (!sectionCode.isEmpty()) {
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					setParam(index++, endDate);
					setParam(index++, endDate);
					setParam(index++, sectionCode);
					setParam(index++, containsParam(sectionCode));
				} else {
					setParam(index++, endDate);
					setParam(index++, sectionCode);
				}
			}
			if (!positionCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, positionCode);
			}
			executeQuery();
			return rs;
		} catch (Throwable e) {
			throw new MospException(e);
		}
	}
	
	@Override
	public ResultSet findForPaidHoliday(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(select());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_EMPLOYEE_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_LAST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_FIRST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_SECTION_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, "*"));
			sb.append(from(TmdPaidHolidayDao.TABLE));
			sb.append("LEFT JOIN ");
			sb.append(PfmHumanDao.TABLE);
			sb.append(" ON ");
			sb.append(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
			sb.append(" = ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(where());
			sb.append(deleteFlagOff(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(deleteFlagOff(getExplicitTableColumn(PfmHumanDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(" =");
			sb.append(leftParenthesis());
			sb.append(selectMax(PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(from(PfmHumanDao.TABLE));
			sb.append(asTmpTable(PfmHumanDao.TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equalTmpColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(and());
			sb.append(PfmHumanDao.COL_ACTIVATE_DATE);
			sb.append(" <= ");
			sb.append(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, TmdPaidHolidayDao.COL_ACTIVATE_DATE));
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(
					greaterEqual(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, TmdPaidHolidayDao.COL_ACTIVATE_DATE)));
			sb.append(and());
			sb.append(lessEqual(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, TmdPaidHolidayDao.COL_ACTIVATE_DATE)));
			if (!workPlaceCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForWorkPlaceCode());
				sb.append(rightParenthesis());
			}
			if (!employmentCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForEmploymentContractCode());
				sb.append(rightParenthesis());
			}
			if (!sectionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					sb.append(humanDao.getQueryForLowerSection());
				} else {
					sb.append(humanDao.getQueryForSectionCode());
				}
				sb.append(rightParenthesis());
			}
			if (!positionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdPaidHolidayDao.TABLE, TmdTotalTimeDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForPositionCode());
				sb.append(rightParenthesis());
			}
			sb.append(getOrderByColumns(PfmHumanDao.COL_EMPLOYEE_CODE, TmdPaidHolidayDao.COL_ACQUISITION_DATE,
					TmdPaidHolidayDao.COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, startDate);
			setParam(index++, endDate);
			if (!workPlaceCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, workPlaceCode);
			}
			if (!employmentCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, employmentCode);
			}
			if (!sectionCode.isEmpty()) {
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					setParam(index++, endDate);
					setParam(index++, endDate);
					setParam(index++, sectionCode);
					setParam(index++, containsParam(sectionCode));
				} else {
					setParam(index++, endDate);
					setParam(index++, sectionCode);
				}
			}
			if (!positionCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, positionCode);
			}
			executeQuery();
			return rs;
		} catch (Throwable e) {
			throw new MospException(e);
		}
	}
	
	@Override
	public ResultSet findForStockHoliday(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(select());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_EMPLOYEE_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_LAST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_FIRST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_SECTION_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(TmdStockHolidayDao.TABLE, "*"));
			sb.append(from(TmdStockHolidayDao.TABLE));
			sb.append("LEFT JOIN ");
			sb.append(PfmHumanDao.TABLE);
			sb.append(" ON ");
			sb.append(getExplicitTableColumn(TmdStockHolidayDao.TABLE, TmdStockHolidayDao.COL_PERSONAL_ID));
			sb.append(" = ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(where());
			sb.append(deleteFlagOff(getExplicitTableColumn(TmdStockHolidayDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(deleteFlagOff(getExplicitTableColumn(PfmHumanDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(" =");
			sb.append(leftParenthesis());
			sb.append(selectMax(PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(from(PfmHumanDao.TABLE));
			sb.append(asTmpTable(PfmHumanDao.TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equalTmpColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(and());
			sb.append(PfmHumanDao.COL_ACTIVATE_DATE);
			sb.append(" <= ");
			sb.append(getExplicitTableColumn(TmdStockHolidayDao.TABLE, TmdStockHolidayDao.COL_ACTIVATE_DATE));
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(greaterEqual(
					getExplicitTableColumn(TmdStockHolidayDao.TABLE, TmdStockHolidayDao.COL_ACTIVATE_DATE)));
			sb.append(and());
			sb.append(
					lessEqual(getExplicitTableColumn(TmdStockHolidayDao.TABLE, TmdStockHolidayDao.COL_ACTIVATE_DATE)));
			if (!workPlaceCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdStockHolidayDao.TABLE, TmdStockHolidayDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForWorkPlaceCode());
				sb.append(rightParenthesis());
			}
			if (!employmentCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdStockHolidayDao.TABLE, TmdStockHolidayDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForEmploymentContractCode());
				sb.append(rightParenthesis());
			}
			if (!sectionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdStockHolidayDao.TABLE, TmdStockHolidayDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					sb.append(humanDao.getQueryForLowerSection());
				} else {
					sb.append(humanDao.getQueryForSectionCode());
				}
				sb.append(rightParenthesis());
			}
			if (!positionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdStockHolidayDao.TABLE, TmdStockHolidayDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForPositionCode());
				sb.append(rightParenthesis());
			}
			sb.append(getOrderByColumns(PfmHumanDao.COL_EMPLOYEE_CODE, TmdStockHolidayDao.COL_ACQUISITION_DATE,
					TmdStockHolidayDao.COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, startDate);
			setParam(index++, endDate);
			if (!workPlaceCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, workPlaceCode);
			}
			if (!employmentCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, employmentCode);
			}
			if (!sectionCode.isEmpty()) {
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					setParam(index++, endDate);
					setParam(index++, endDate);
					setParam(index++, sectionCode);
					setParam(index++, containsParam(sectionCode));
				} else {
					setParam(index++, endDate);
					setParam(index++, sectionCode);
				}
			}
			if (!positionCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, positionCode);
			}
			executeQuery();
			return rs;
		} catch (Throwable e) {
			throw new MospException(e);
		}
	}
	
	@Override
	public ResultSet findForHolidayData(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(select());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_EMPLOYEE_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_LAST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_FIRST_NAME));
			sb.append(", ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_SECTION_CODE));
			sb.append(", ");
			sb.append(getExplicitTableColumn(TmdHolidayDataDao.TABLE, "*"));
			sb.append(from(TmdHolidayDataDao.TABLE));
			sb.append("LEFT JOIN ");
			sb.append(PfmHumanDao.TABLE);
			sb.append(" ON ");
			sb.append(getExplicitTableColumn(TmdHolidayDataDao.TABLE, TmdHolidayDataDao.COL_PERSONAL_ID));
			sb.append(" = ");
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(where());
			sb.append(deleteFlagOff(getExplicitTableColumn(TmdHolidayDataDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(deleteFlagOff(getExplicitTableColumn(PfmHumanDao.TABLE, colDeleteFlag)));
			sb.append(and());
			sb.append(getExplicitTableColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(" =");
			sb.append(leftParenthesis());
			sb.append(selectMax(PfmHumanDao.COL_ACTIVATE_DATE));
			sb.append(from(PfmHumanDao.TABLE));
			sb.append(asTmpTable(PfmHumanDao.TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equalTmpColumn(PfmHumanDao.TABLE, PfmHumanDao.COL_PERSONAL_ID));
			sb.append(and());
			sb.append(PfmHumanDao.COL_ACTIVATE_DATE);
			sb.append(" <= ");
			sb.append(getExplicitTableColumn(TmdHolidayDataDao.TABLE, TmdHolidayDataDao.COL_ACTIVATE_DATE));
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(
					greaterEqual(getExplicitTableColumn(TmdHolidayDataDao.TABLE, TmdHolidayDataDao.COL_ACTIVATE_DATE)));
			sb.append(and());
			sb.append(lessEqual(getExplicitTableColumn(TmdHolidayDataDao.TABLE, TmdHolidayDataDao.COL_ACTIVATE_DATE)));
			if (!workPlaceCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdHolidayDataDao.TABLE, TmdHolidayDataDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForWorkPlaceCode());
				sb.append(rightParenthesis());
			}
			if (!employmentCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdHolidayDataDao.TABLE, TmdHolidayDataDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForEmploymentContractCode());
				sb.append(rightParenthesis());
			}
			if (!sectionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdHolidayDataDao.TABLE, TmdHolidayDataDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					sb.append(humanDao.getQueryForLowerSection());
				} else {
					sb.append(humanDao.getQueryForSectionCode());
				}
				sb.append(rightParenthesis());
			}
			if (!positionCode.isEmpty()) {
				sb.append(and());
				sb.append(getExplicitTableColumn(TmdHolidayDataDao.TABLE, TmdHolidayDataDao.COL_PERSONAL_ID));
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForPositionCode());
				sb.append(rightParenthesis());
			}
			sb.append(getOrderByColumns(PfmHumanDao.COL_EMPLOYEE_CODE, TmdHolidayDataDao.COL_ACTIVATE_DATE,
					TmdHolidayDataDao.COL_HOLIDAY_TYPE, TmdHolidayDataDao.COL_HOLIDAY_CODE));
			prepareStatement(sb.toString());
			setParam(index++, startDate);
			setParam(index++, endDate);
			if (!workPlaceCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, workPlaceCode);
			}
			if (!employmentCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, employmentCode);
			}
			if (!sectionCode.isEmpty()) {
				// 下位所属含むチェックボックスで判定
				if (ckbNeedLowerSection == 1) {
					setParam(index++, endDate);
					setParam(index++, endDate);
					setParam(index++, sectionCode);
					setParam(index++, containsParam(sectionCode));
				} else {
					setParam(index++, endDate);
					setParam(index++, sectionCode);
				}
			}
			if (!positionCode.isEmpty()) {
				setParam(index++, endDate);
				setParam(index++, positionCode);
			}
			executeQuery();
			return rs;
		} catch (Throwable e) {
			throw new MospException(e);
		}
	}
	
	@Override
	public void closers() throws MospException {
		releaseResultSet();
		releasePreparedStatement();
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) {
		// 処理なし
		return 0;
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) {
		// 処理なし
		return 0;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) {
		// 処理なし
	}
	
}
