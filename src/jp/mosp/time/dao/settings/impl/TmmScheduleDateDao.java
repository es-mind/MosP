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
/**
 * 
 */
package jp.mosp.time.dao.settings.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmScheduleDateDto;

/**
 * カレンダ日マスタDAOクラス。
 */
public class TmmScheduleDateDao extends PlatformDao implements ScheduleDateDaoInterface {
	
	/**
	 * カレンダ日マスタ。
	 */
	public static final String	TABLE						= "tmm_schedule_date";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_SCHEDULE_DATE_ID	= "tmm_schedule_date_id";
	
	/**
	 * カレンダコード。
	 */
	public static final String	COL_SCHEDULE_CODE			= "schedule_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * 日。
	 */
	public static final String	COL_SCHEDULE_DATE			= "schedule_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String	COL_WORKS					= "works";
	
	/**
	 * 勤務形態コード。
	 */
	public static final String	COL_WORK_TYPE_CODE			= "work_type_code";
	
	/**
	 * 備考。
	 */
	public static final String	COL_REMARK					= "remark";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMM_SCHEDULE_DATE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmScheduleDateDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmScheduleDateDto dto = new TmmScheduleDateDto();
		dto.setTmmScheduleDateId(getLong(COL_TMM_SCHEDULE_DATE_ID));
		dto.setScheduleCode(getString(COL_SCHEDULE_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setScheduleDate(getDate(COL_SCHEDULE_DATE));
		dto.setWorks(getInt(COL_WORKS));
		dto.setWorkTypeCode(getString(COL_WORK_TYPE_CODE));
		dto.setRemark(getString(COL_REMARK));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ScheduleDateDtoInterface> mappingAll() throws MospException {
		List<ScheduleDateDtoInterface> all = new ArrayList<ScheduleDateDtoInterface>();
		while (next()) {
			all.add((ScheduleDateDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public ScheduleDateDtoInterface findForKey(String scheduleCode, Date scheduleDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SCHEDULE_CODE));
			sb.append(and());
			sb.append(equal(COL_SCHEDULE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, scheduleCode);
			setParam(index++, scheduleDate);
			executeQuery();
			ScheduleDateDtoInterface dto = null;
			if (next()) {
				dto = (ScheduleDateDtoInterface)mapping();
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
	public List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SCHEDULE_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumn(COL_SCHEDULE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, scheduleCode);
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
			ScheduleDateDtoInterface dto = (ScheduleDateDtoInterface)baseDto;
			setParam(index++, dto.getTmmScheduleDateId());
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
			ScheduleDateDtoInterface dto = (ScheduleDateDtoInterface)baseDto;
			setParam(index++, dto.getTmmScheduleDateId());
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
		ScheduleDateDtoInterface dto = (ScheduleDateDtoInterface)baseDto;
		setParam(index++, dto.getTmmScheduleDateId());
		setParam(index++, dto.getScheduleCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getScheduleDate());
		setParam(index++, dto.getWorks());
		setParam(index++, dto.getWorkTypeCode());
		setParam(index++, dto.getRemark());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SCHEDULE_CODE));
			sb.append(and());
			sb.append(greaterEqual(COL_SCHEDULE_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_SCHEDULE_DATE));
			sb.append(getOrderByColumn(COL_SCHEDULE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, scheduleCode);
			setParam(index++, startDate);
			setParam(index++, endDate);
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
	public List<ScheduleDateDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate, String targetCode)
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
				sb.append(greaterEqual(COL_SCHEDULE_DATE));
			}
			if (toActivateDate != null) {
				sb.append(and());
				sb.append(less(COL_SCHEDULE_DATE));
			}
			// 勤務形態コード
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			// 無効フラグOFF
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			// ソート
			sb.append(getOrderByColumn(COL_WORK_TYPE_CODE, COL_ACTIVATE_DATE));
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定(有効日範囲による条件)
			if (fromActivateDate != null) {
				setParam(index++, fromActivateDate);
			}
			if (toActivateDate != null) {
				setParam(index++, toActivateDate);
			}
			setParam(index++, targetCode);
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
	
	/**
	 * 条件による検索のための文字列。
	 * <p>
	 * 最大有効日レコードのクエリを取得する。
	 * @return カレンダコードに紐づく有効日が最大であるレコード取得クエリ
	 */
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
		sb.append(equalTmpColumn(TABLE, COL_SCHEDULE_CODE));
		sb.append(and());
		sb.append(equalTmpColumn(TABLE, COL_SCHEDULE_DATE));
		sb.append(and());
		sb.append(lessEqual(COL_ACTIVATE_DATE));
		sb.append(rightParenthesis());
		return sb;
	}
	
	@Override
	public List<String> findForWorkTypeCode(String scheduleCode) throws MospException {
		try {
			// 検索SQLを取得する
			StringBuffer sb = getSelectDistinctQuery(TABLE, COL_WORK_TYPE_CODE);
			sb.append(where());
			// 削除フラグがOFF
			sb.append(deleteFlagOff());
			sb.append(and());
			// カレンダコードと一致
			sb.append(equal(COL_SCHEDULE_CODE));
			prepareStatement(sb.toString());
			setParam(1, scheduleCode);
			executeQuery();
			List<String> workTypeCodeList = new ArrayList<String>();
			while (next()) {
				workTypeCodeList.add(getString(COL_WORK_TYPE_CODE));
			}
			// 勤務形態コードのリストを返却
			return workTypeCodeList;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
