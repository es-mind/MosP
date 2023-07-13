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
package jp.mosp.platform.dao.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.SuspensionDaoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanSuspensionDto;

/**
 * 人事休職情報DAOクラス。
 */
public class PfaHumanSuspensionDao extends PlatformDao implements SuspensionDaoInterface {
	
	/**
	 * 人事休職情報。
	 */
	public static final String	TABLE						= "pfa_human_suspension";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_HUMAN_SUSPENSION_ID	= "pfa_human_suspension_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID				= "personal_id";
	
	/**
	 * 開始日。
	 */
	public static final String	COL_START_DATE				= "start_date";
	
	/**
	 * 終了日。
	 */
	public static final String	COL_END_DATE				= "end_date";
	
	/**
	 * 終了予定日。
	 */
	public static final String	COL_SCHEDULE_END_DATE		= "schedule_end_date";
	
	/**
	 * 給与区分。
	 */
	public static final String	COL_ALLOWANCE_TYPE			= "allowance_type";
	
	/**
	 * 理由。
	 */
	public static final String	COL_SUSPENSION_REASON		= "suspension_reason";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_PFA_HUMAN_SUSPENSION_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanSuspensionDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaHumanSuspensionDto dto = new PfaHumanSuspensionDto();
		dto.setPfaHumanSuspensionId(getLong(COL_PFA_HUMAN_SUSPENSION_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setStartDate(getDate(COL_START_DATE));
		dto.setEndDate(getDate(COL_END_DATE));
		dto.setScheduleEndDate(getDate(COL_SCHEDULE_END_DATE));
		dto.setAllowanceType(getString(COL_ALLOWANCE_TYPE));
		dto.setSuspensionReason(getString(COL_SUSPENSION_REASON));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<SuspensionDtoInterface> mappingAll() throws MospException {
		List<SuspensionDtoInterface> all = new ArrayList<SuspensionDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public List<SuspensionDtoInterface> findForHistory(String personalId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(getOrderByColumn(COL_START_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
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
	public SuspensionDtoInterface findForInfo(String personalId, Date suspensionDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(COL_START_DATE);
			sb.append(" <= ?");
			sb.append(and());
			sb.append("((");
			sb.append(COL_END_DATE);
			sb.append(" IS NULL");
			sb.append(and());
			sb.append(COL_SCHEDULE_END_DATE);
			sb.append(" >= ?) OR (");
			sb.append(COL_END_DATE);
			sb.append(" >= ?))");
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, suspensionDate);
			setParam(index++, suspensionDate);
			setParam(index++, suspensionDate);
			executeQuery();
			SuspensionDtoInterface dto = null;
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			SuspensionDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanSuspensionId());
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
			SuspensionDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanSuspensionId());
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
		SuspensionDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaHumanSuspensionId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getStartDate());
		setParam(index++, dto.getEndDate());
		setParam(index++, dto.getScheduleEndDate());
		setParam(index++, dto.getAllowanceType());
		setParam(index++, dto.getSuspensionReason());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected SuspensionDtoInterface castDto(BaseDtoInterface baseDto) {
		return (SuspensionDtoInterface)baseDto;
	}
	
	@Override
	public List<SuspensionDtoInterface> findForList(Date targetDate, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(lessEqual(COL_START_DATE));
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(leftParenthesis());
			sb.append(isNull(COL_END_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_SCHEDULE_END_DATE));
			sb.append(rightParenthesis());
			sb.append(or());
			sb.append(leftParenthesis());
			sb.append(isNotNull(COL_END_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_END_DATE));
			sb.append(rightParenthesis());
			sb.append(rightParenthesis());
			sb.append(getOrderByColumn(COL_PERSONAL_ID, COL_START_DATE));
			prepareStatement(sb.toString());
			if (startDate != null && endDate != null) {
				setParam(index++, endDate);
				setParam(index++, startDate);
				setParam(index++, startDate);
			} else {
				setParam(index++, targetDate);
				setParam(index++, targetDate);
				setParam(index++, targetDate);
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
	public Map<String, SuspensionDtoInterface> findForPersonalIds(String[] personalIds, Date suspensionDate)
			throws MospException {
		try {
			// パラメータインデックス準備
			index = 1;
			// SQL作成
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_PERSONAL_ID, personalIds.length));
			sb.append(and());
			sb.append(COL_START_DATE);
			sb.append(" <= ?");
			sb.append(and());
			sb.append("((");
			sb.append(COL_END_DATE);
			sb.append(" IS NULL");
			sb.append(and());
			sb.append(COL_SCHEDULE_END_DATE);
			sb.append(" >= ?) OR (");
			sb.append(COL_END_DATE);
			sb.append(" >= ?))");
			
			// ステートメント生成
			prepareStatement(sb.toString());
			setParamsIn(personalIds);
			setParam(index++, suspensionDate);
			setParam(index++, suspensionDate);
			setParam(index++, suspensionDate);
			// SQL実行
			executeQuery();
			// 結果取得
			Map<String, SuspensionDtoInterface> allmap = new HashMap<String, SuspensionDtoInterface>();
			while (next()) {
				SuspensionDtoInterface dto = castDto(mapping());
				allmap.put(dto.getPersonalId(), dto);
				
			}
			return allmap;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
