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
package jp.mosp.platform.dao.human.impl;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.ConcurrentDaoInterface;
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanConcurrentDto;

/**
 * 人事兼務情報DAOクラス。
 */
public class PfaHumanConcurrentDao extends PlatformDao implements ConcurrentDaoInterface {
	
	/**
	 * 人事兼務情報。
	 */
	public static final String		TABLE						= "pfa_human_concurrent";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_PFA_HUMAN_CONCURRENT_ID	= "pfa_human_concurrent_id";
	
	/**
	 * 個人ID。
	 */
	public static final String		COL_PERSONAL_ID				= "personal_id";
	
	/**
	 * 開始日。
	 */
	public static final String		COL_START_DATE				= "start_date";
	
	/**
	 * 終了日。
	 */
	public static final String		COL_END_DATE				= "end_date";
	
	/**
	 * 所属コード。
	 */
	public static final String		COL_SECTION_CODE			= "section_code";
	
	/**
	 * 職位コード。
	 */
	public static final String		COL_POSITION_CODE			= "position_code";
	
	/**
	 * 備考。
	 */
	public static final String		COL_CONCURRENT_REMARK		= "concurrent_remark";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1						= COL_PFA_HUMAN_CONCURRENT_ID;
	
	/**
	 * 職位マスタDAOクラス(サブクエリ等取得用)。
	 */
	protected PositionDaoInterface	positionDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanConcurrentDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() throws MospException {
		positionDao = (PositionDaoInterface)loadDao(PositionDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaHumanConcurrentDto dto = new PfaHumanConcurrentDto();
		dto.setPfaHumanConcurrentId(getLong(COL_PFA_HUMAN_CONCURRENT_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setStartDate(getDate(COL_START_DATE));
		dto.setEndDate(getDate(COL_END_DATE));
		dto.setSectionCode(getString(COL_SECTION_CODE));
		dto.setPositionCode(getString(COL_POSITION_CODE));
		dto.setConcurrentRemark(getString(COL_CONCURRENT_REMARK));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ConcurrentDtoInterface> mappingAll() throws MospException {
		List<ConcurrentDtoInterface> all = new ArrayList<ConcurrentDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public List<ConcurrentDtoInterface> findForHistory(String personalId) throws MospException {
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
	public List<ConcurrentDtoInterface> findForTerm(Date fromDate, Date toDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			if (toDate == null) {
				sb.append(leftParenthesis());
				sb.append(greaterEqual(COL_END_DATE));
				sb.append(or());
				sb.append(isNull(COL_END_DATE));
				sb.append(rightParenthesis());
				prepareStatement(sb.toString());
				setParam(index++, fromDate);
			} else {
				sb.append(less(COL_START_DATE));
				sb.append(and());
				sb.append(leftParenthesis());
				sb.append(greaterEqual(COL_END_DATE));
				sb.append(or());
				sb.append(isNull(COL_END_DATE));
				sb.append(rightParenthesis());
				prepareStatement(sb.toString());
				setParam(index++, toDate);
				setParam(index++, fromDate);
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
	public List<ConcurrentDtoInterface> findForList(String personalId, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_START_DATE));
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(isNull(COL_END_DATE));
			sb.append(or());
			sb.append(greaterEqual(COL_END_DATE));
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, activateDate);
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			ConcurrentDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanConcurrentId());
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
			ConcurrentDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanConcurrentId());
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
		ConcurrentDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaHumanConcurrentId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getStartDate());
		setParam(index++, dto.getEndDate());
		setParam(index++, dto.getSectionCode());
		setParam(index++, dto.getPositionCode());
		setParam(index++, dto.getConcurrentRemark());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public String getQueryForSection(String targetColumn, boolean needLowerSection) throws MospException {
		// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
		SectionDaoInterface sectionDao = (SectionDaoInterface)loadDao(SectionDaoInterface.class);
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		// 個人IDを抽出
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		// 所属条件SQL追加
		sb.append(and());
		if (needLowerSection) {
			sb.append(sectionDao.getQueryForLowerSection(COL_SECTION_CODE));
		} else {
			sb.append(equal(COL_SECTION_CODE));
		}
		// 対象日条件SQL追加
		sb.append(and());
		sb.append(lessEqual(COL_START_DATE));
		sb.append(and());
		sb.append(leftParenthesis());
		sb.append(isNull(COL_END_DATE));
		sb.append(or());
		sb.append(greaterEqual(COL_END_DATE));
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForSection(int index, String sectionCode, Date targetDate, boolean needLowerSection,
			PreparedStatement ps) throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// 条件パラメータ設定
		if (needLowerSection) {
			// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
			SectionDaoInterface sectionDao = (SectionDaoInterface)loadDao(SectionDaoInterface.class);
			// 下位所属条件パラメータ設定
			idx = sectionDao.setParamsForLowerSection(idx, sectionCode, targetDate, ps);
		} else {
			setParam(idx++, sectionCode, ps);
		}
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, targetDate, false, ps);
		// インデックス返却
		return idx;
	}
	
	@Override
	public String getQueryForPosition(String targetColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		// 個人IDを抽出
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		// 職位条件SQL追加
		sb.append(and());
		sb.append(equal(COL_POSITION_CODE));
		// 対象日条件SQL追加
		sb.append(and());
		sb.append(lessEqual(COL_START_DATE));
		sb.append(and());
		sb.append(leftParenthesis());
		sb.append(isNull(COL_END_DATE));
		sb.append(or());
		sb.append(greaterEqual(COL_END_DATE));
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForPosition(int index, String positionCode, Date targetDate, PreparedStatement ps)
			throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// パラメータ設定
		setParam(idx++, positionCode, ps);
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, targetDate, false, ps);
		// インデックス返却
		return idx;
	}
	
	@Override
	public String getQueryForPositionGrade(boolean greaterEqual, String targetColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		// 個人IDを抽出
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		// 職位条件SQL追加
		sb.append(and());
		sb.append(COL_POSITION_CODE);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(positionDao.getQueryForPositionGrade(greaterEqual));
		sb.append(rightParenthesis());
		// 対象日条件SQL追加
		sb.append(and());
		sb.append(lessEqual(COL_START_DATE));
		sb.append(and());
		sb.append(leftParenthesis());
		sb.append(isNull(COL_END_DATE));
		sb.append(or());
		sb.append(greaterEqual(COL_END_DATE));
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForPositionGrade(int index, String positionCode, Date targetDate, PreparedStatement ps)
			throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// パラメータ設定
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, positionCode, ps);
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, targetDate, false, ps);
		// インデックス返却
		return idx;
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected ConcurrentDtoInterface castDto(BaseDtoInterface baseDto) {
		return (ConcurrentDtoInterface)baseDto;
	}
	
}
