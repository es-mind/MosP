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
package jp.mosp.platform.dao.system.impl;

import java.sql.PreparedStatement;
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
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmPositionDto;

/**
 * 職位マスタDAOクラス。
 */
public class PfmPositionDao extends PlatformDao implements PositionDaoInterface {
	
	/**
	 * 職位マスタ。
	 */
	public static final String	TABLE				= "pfm_position";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_POSITION_ID	= "pfm_position_id";
	
	/**
	 * 職位コード。
	 */
	public static final String	COL_POSITION_CODE	= "position_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE	= "activate_date";
	
	/**
	 * 職位名称。
	 */
	public static final String	COL_POSITION_NAME	= "position_name";
	
	/**
	 * 職位略称。
	 */
	public static final String	COL_POSITION_ABBR	= "position_abbr";
	
	/**
	 * 等級。
	 */
	public static final String	COL_POSITION_GRADE	= "position_grade";
	
	/**
	 * 号数。
	 */
	public static final String	COL_POSITION_LEVEL	= "position_level";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG	= "inactivate_flag";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	KEY_1				= COL_PFM_POSITION_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmPositionDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmPositionDto dto = new PfmPositionDto();
		dto.setPfmPositionId(getLong(COL_PFM_POSITION_ID));
		dto.setPositionCode(getString(COL_POSITION_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setPositionName(getString(COL_POSITION_NAME));
		dto.setPositionAbbr(getString(COL_POSITION_ABBR));
		dto.setPositionGrade(getInt(COL_POSITION_GRADE));
		dto.setPositionLevel(getInt(COL_POSITION_LEVEL));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PositionDtoInterface> mappingAll() throws MospException {
		List<PositionDtoInterface> all = new ArrayList<PositionDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public List<PositionDtoInterface> findForActivateDate(Date targetDate, String[] rangeArray) throws MospException {
		try {
			// パラメータインデックス準備
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			// 削除されていない情報のみ抽出
			sb.append(deleteFlagOff());
			// 有効な情報のみ抽出
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			// 操作範囲条件SQL追加
			sb.append(getQueryForRange(rangeArray, COL_POSITION_CODE));
			// 並べ替え
			sb.append(getOrderByColumn(COL_POSITION_CODE));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, targetDate, ps);
			// 操作範囲条件パラメータ設定
			index = setParamsForRange(index, rangeArray, targetDate, ps);
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
	public List<PositionDtoInterface> findForHistory(String positionCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_POSITION_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
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
	public PositionDtoInterface findForInfo(String positionCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_POSITION_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ?");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, positionCode);
			setParam(index++, activateDate);
			executeQuery();
			PositionDtoInterface dto = null;
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
	public List<PositionDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String positionCode = getSearchParam(param, "positionCode");
			String positionName = getSearchParam(param, "positionName");
			String positionAbbr = getSearchParam(param, "positionAbbr");
			String positionGrade = getSearchParam(param, "positionGrade");
			String positionLevel = getSearchParam(param, "positionLevel");
			String inactivateFlag = getSearchParam(param, "inactivateFlag");
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			
			if (!(activateDate == null)) {
				// 有効日における最新の情報を抽出する条件SQLを追加
				sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_POSITION_CODE));
			sb.append(and());
			sb.append(like(COL_POSITION_NAME));
			sb.append(and());
			sb.append(like(COL_POSITION_ABBR));
			
			if (!positionGrade.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_POSITION_GRADE));
			}
			if (!positionLevel.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_POSITION_LEVEL));
			}
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			
			prepareStatement(sb.toString());
			if (!(activateDate == null)) {
				// 有効日における最新の情報を抽出する条件のパラメータを設定
				index = setParamsForMaxActivateDate(index, activateDate, ps);
			}
			setParam(index++, startWithParam(positionCode));
			setParam(index++, containsParam(positionName));
			setParam(index++, containsParam(positionAbbr));
			if (!positionGrade.isEmpty()) {
				setParam(index++, Integer.parseInt(positionGrade));
			}
			if (!positionLevel.isEmpty()) {
				setParam(index++, Integer.parseInt(positionLevel));
			}
			if (!inactivateFlag.isEmpty()) {
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			PositionDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmPositionId());
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
			PositionDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmPositionId());
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
		PositionDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmPositionId());
		setParam(index++, dto.getPositionCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getPositionName());
		setParam(index++, dto.getPositionAbbr());
		setParam(index++, dto.getPositionGrade());
		setParam(index++, dto.getPositionLevel());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	@Override
	public PositionDtoInterface findForKey(String positionCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_POSITION_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, positionCode);
			setParam(index++, activateDate);
			executeQuery();
			PositionDtoInterface dto = null;
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
	public String getQueryForPositionName(String targetColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(and());
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_POSITION_CODE);
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		// 名称条件SQLを追加
		sb.append(and());
		sb.append(leftParenthesis());
		sb.append(like(COL_POSITION_NAME));
		sb.append(or());
		sb.append(like(COL_POSITION_ABBR));
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForPositionName(int index, String positionName, Date targetDate, PreparedStatement ps)
			throws MospException {
		// パラメータインデックス準備
		int idx = index;
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, containsParam(positionName), ps);
		setParam(idx++, containsParam(positionName), ps);
		// インデックス返却
		return idx;
	}
	
	@Override
	public StringBuffer getQueryForLowerPosition() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_POSITION_CODE);
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		if (hasLowGradeAdvantage()) {
			sb.append(greater(COL_POSITION_GRADE));
		} else {
			sb.append(less(COL_POSITION_GRADE));
		}
		return sb;
	}
	
	@Override
	public String getQueryForRange(String[] rangeArray, String targetColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		// 操作範囲配列長取得
		int rangeArrayLength = rangeArray.length;
		// 操作範囲確認
		if (rangeArrayLength == 0) {
			// 操作範囲条件不用
			return sb.toString();
		}
		// 等級優劣関係確認
		String gradeCondition = less();
		if (hasLowGradeAdvantage()) {
			gradeCondition = greater();
		}
		// 操作範囲条件SQL作成
		sb.append(and());
		sb.append(leftParenthesis());
		for (int i = 0; i < rangeArrayLength; i++) {
			sb.append(targetColumn);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(select());
			sb.append(COL_POSITION_CODE);
			sb.append(from(TABLE));
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_POSITION_GRADE);
			// 操作範囲職位の等級より等級が大きい職位を抽出
			sb.append(gradeCondition);
			sb.append(leftParenthesis());
			sb.append(select());
			sb.append(COL_POSITION_GRADE);
			sb.append(from(TABLE));
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_POSITION_CODE));
			sb.append(rightParenthesis());
			sb.append(rightParenthesis());
			if (i < rangeArrayLength - 1) {
				sb.append(or());
			}
		}
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForRange(int index, String[] rangeArray, Date targetDate, PreparedStatement ps)
			throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// 操作範囲条件設定
		for (String range : rangeArray) {
			setParam(idx++, targetDate, false, ps);
			setParam(idx++, targetDate, false, ps);
			setParam(idx++, range, ps);
		}
		// インデックス返却
		return idx;
	}
	
	@Override
	public String getQueryForPositionGrade(boolean greaterEqual) {
		boolean hasLowGradeAdvantage = hasLowGradeAdvantage();
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_POSITION_CODE);
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		// 等級条件SQLを追加
		sb.append(and());
		sb.append(COL_POSITION_GRADE);
		if (greaterEqual) {
			// 以上
			if (hasLowGradeAdvantage) {
				sb.append(lessEqual());
			} else {
				sb.append(greaterEqual());
			}
		} else {
			// 以下
			if (hasLowGradeAdvantage) {
				sb.append(greaterEqual());
			} else {
				sb.append(lessEqual());
			}
		}
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_POSITION_GRADE);
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		// コード条件SQLを追加
		sb.append(and());
		sb.append(equal(COL_POSITION_CODE));
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForPositionGrande(int index, String positionCode, Date targetDate, PreparedStatement ps)
			throws MospException {
		// パラメータインデックス準備
		int idx = index;
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, positionCode, ps);
		// インデックス返却
		return idx;
	}
	
	@Override
	public String getQueryForApprover(String targetColumn) {
		// MosP設定情報から承認者職位等級を取得
		String grade = mospParams.getApplicationProperty(PlatformConst.APP_APPROVER_POSITION_GRADE);
		// 承認者職位等級確認
		if (grade == null || grade.isEmpty()) {
			return "";
		}
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		// SQL作成
		sb.append(and());
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		// SELECT部追加(職位コード)
		sb.append(select());
		sb.append(COL_POSITION_CODE);
		// FROM部追加
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_POSITION_CODE, COL_ACTIVATE_DATE));
		// WHERE部追加
		sb.append(where());
		// 削除されていない情報のみ抽出
		sb.append(deleteFlagOff());
		// 優れた職位のみを抽出
		sb.append(and());
		if (hasLowGradeAdvantage()) {
			sb.append(less(COL_POSITION_GRADE));
		} else {
			sb.append(greater(COL_POSITION_GRADE));
		}
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForApprover(int index, Date targetDate, PreparedStatement ps) throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// MosP設定情報から承認者職位等級を取得
		String grade = mospParams.getApplicationProperty(PlatformConst.APP_APPROVER_POSITION_GRADE);
		// 承認者職位等級確認
		if (grade == null || grade.isEmpty()) {
			return idx;
		}
		// 操作範囲条件設定
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, mospParams.getApplicationProperty(PlatformConst.APP_APPROVER_POSITION_GRADE, 0), ps);
		// インデックス返却
		return idx;
	}
	
	@Override
	public boolean hasLowGradeAdvantage() {
		// 等級優劣関係確認
		String advantage = mospParams.getApplicationProperty(PlatformConst.APP_POSITION_GRADE_ADVANTAGE);
		if (advantage != null && advantage.equals(PlatformConst.POSITION_GRADE_ADVANTAGE_LOW)) {
			return true;
		}
		return false;
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected PositionDtoInterface castDto(BaseDtoInterface baseDto) {
		return (PositionDtoInterface)baseDto;
	}
	
}
