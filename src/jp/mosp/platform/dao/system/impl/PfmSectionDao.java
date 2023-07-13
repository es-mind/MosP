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
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmSectionDto;
import jp.mosp.platform.system.constant.PlatformSystemConst;

/**
 * 所属マスタDAOクラス。
 */
public class PfmSectionDao extends PlatformDao implements SectionDaoInterface {
	
	/**
	 * 所属マスタ。
	 */
	public static final String		TABLE							= "pfm_section";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_PFM_SECTION_ID				= "pfm_section_id";
	
	/**
	 * 所属コード。
	 */
	public static final String		COL_SECTION_CODE				= "section_code";
	
	/**
	 * 有効日。
	 */
	public static final String		COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * 所属名称。
	 */
	public static final String		COL_SECTION_NAME				= "section_name";
	
	/**
	 * 所属略称。
	 */
	public static final String		COL_SECTION_ABBR				= "section_abbr";
	
	/**
	 * 所属表示名称。
	 */
	public static final String		COL_SECTION_DISPLAY				= "section_display";
	
	/**
	 * 階層経路。
	 */
	public static final String		COL_CLASS_ROUTE					= "class_route";
	
	/**
	 * 閉鎖フラグ。
	 */
	public static final String		COL_CLOSE_FLAG					= "close_flag";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1							= COL_PFM_SECTION_ID;
	
	/**
	 * 
	 */
	static protected final String	TMP_TALBE_LOWERE_SECTION_RANGE	= "tmp_" + TABLE + "_range";
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmSectionDao() {
		super();
	}
	
	@Override
	public void initDao() {
		
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmSectionDto dto = new PfmSectionDto();
		dto.setPfmSectionId(getLong(COL_PFM_SECTION_ID));
		dto.setSectionCode(getString(COL_SECTION_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setSectionName(getString(COL_SECTION_NAME));
		dto.setSectionAbbr(getString(COL_SECTION_ABBR));
		dto.setSectionDisplay(getString(COL_SECTION_DISPLAY));
		dto.setClassRoute(getString(COL_CLASS_ROUTE));
		dto.setCloseFlag(getInt(COL_CLOSE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<SectionDtoInterface> mappingAll() throws MospException {
		List<SectionDtoInterface> all = new ArrayList<SectionDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public SectionDtoInterface findForKey(String sectionCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SECTION_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, sectionCode);
			setParam(index++, activateDate);
			executeQuery();
			SectionDtoInterface dto = null;
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
	public List<SectionDtoInterface> findForActivateDate(Date activateDate, String[] rangeArray) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_SECTION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(deleteFlagOff(COL_CLOSE_FLAG));
			// 操作範囲条件SQL追加
			sb.append(getQueryForRange(rangeArray, COL_SECTION_CODE));
			sb.append(getOrderByColumn(concat(COL_CLASS_ROUTE, COL_SECTION_CODE)));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, activateDate, ps);
			// 操作範囲条件パラメータ設定
			index = setParamsForRange(index, rangeArray, activateDate, ps);
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
	public List<SectionDtoInterface> findForHistory(String sectionCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SECTION_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, sectionCode);
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
	public SectionDtoInterface findForInfo(String sectionCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SECTION_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, sectionCode);
			setParam(index++, activateDate);
			executeQuery();
			SectionDtoInterface dto = null;
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
	public List<SectionDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate) throws MospException {
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
			sb.append(getOrderByColumn(COL_SECTION_CODE, COL_ACTIVATE_DATE));
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
	public List<SectionDtoInterface> findForCodeRange(String sectionCodeFrom, String sectionCodeTo, Date activateDate)
			throws MospException {
		try {
			index = 1;
			// SELECT文追加
			StringBuffer sb = getSelectQuery(getClass());
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_SECTION_CODE, COL_ACTIVATE_DATE));
			// WHERE句追加
			sb.append(where());
			// 削除されていない情報を取得
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(deleteFlagOff(COL_CLOSE_FLAG));
			// 所属コード範囲To
			if (sectionCodeTo != null && sectionCodeTo.isEmpty() == false) {
				sb.append(and());
				sb.append(lessEqual(COL_SECTION_CODE));
			}
			// 所属コード範囲From
			if (sectionCodeFrom != null && sectionCodeFrom.isEmpty() == false) {
				sb.append(and());
				sb.append(greaterEqual(COL_SECTION_CODE));
			}
			// 所属コード順
			sb.append(getOrderByColumn(COL_SECTION_CODE));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, activateDate, ps);
			// 所属コード範囲Toのパラメータを設定
			if (sectionCodeTo != null && sectionCodeTo.isEmpty() == false) {
				setParam(index++, sectionCodeTo);
			}
			// 所属コード範囲Fromのパラメータを設定
			if (sectionCodeFrom != null && sectionCodeFrom.isEmpty() == false) {
				setParam(index++, sectionCodeFrom);
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
	public List<SectionDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// パラメータ取得
			Date targetDate = (Date)param.get(SectionDaoInterface.SEARCH_TARGET_DATE);
			String sectionType = String.valueOf(param.get(SectionDaoInterface.SEARCH_SECTION_TYPE));
			String sectionCode = String.valueOf(param.get(SectionDaoInterface.SEARCH_SECTION_CODE));
			String sectionName = String.valueOf(param.get(SectionDaoInterface.SEARCH_SECTION_NAME));
			String sectionAbbr = String.valueOf(param.get(SectionDaoInterface.SEARCH_SECTION_ABBR));
			String closeFlag = String.valueOf(param.get(SectionDaoInterface.SEARCH_CLOSE_FLAG));
			// パラメータインデックス準備
			index = 1;
			// SQL作成
			StringBuffer sb = getSelectQuery(getClass());
			// 対象日以前で最新の情報を取得
			sb.append(getQueryForMaxActivateDate(TABLE, COL_SECTION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(like(COL_SECTION_CODE));
			sb.append(and());
			sb.append(like(COL_SECTION_NAME));
			sb.append(and());
			sb.append(like(COL_SECTION_ABBR));
			if (sectionType.equals(PlatformSystemConst.SEARCH_SECTION_ROUTE)) {
				sb.append(or());
				sb.append(leftParenthesis());
				sb.append(exists());
				sb.append(leftParenthesis());
				sb.append(getSelectQuery(getClass()));
				sb.append(asTmpTable(TABLE));
				sb.append(where());
				sb.append(like(getTmpTableColumn(TABLE, COL_SECTION_CODE)));
				sb.append(and());
				sb.append(like(getTmpTableColumn(TABLE, COL_SECTION_NAME)));
				sb.append(and());
				sb.append(like(getTmpTableColumn(TABLE, COL_SECTION_ABBR)));
				sb.append(and());
				sb.append(getExplicitTableColumn(TABLE, COL_CLASS_ROUTE));
				sb.append(like());
				sb.append(concat("'%,'", getTmpTableColumn(TABLE, COL_SECTION_CODE), "',%'"));
				sb.append(rightParenthesis());
				sb.append(rightParenthesis());
			}
			sb.append(rightParenthesis());
			if (!closeFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_CLOSE_FLAG));
			}
			sb.append(getOrderByColumn(COL_SECTION_CODE));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, targetDate, ps);
			// パラメータ設定
			setParam(index++, startWithParam(sectionCode));
			setParam(index++, containsParam(sectionName));
			setParam(index++, containsParam(sectionAbbr));
			if (sectionType.equals(PlatformSystemConst.SEARCH_SECTION_ROUTE)) {
				setParam(index++, startWithParam(sectionCode));
				setParam(index++, containsParam(sectionName));
				setParam(index++, containsParam(sectionAbbr));
			}
			if (!closeFlag.isEmpty()) {
				setParam(index++, Integer.parseInt(closeFlag));
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
	public List<SectionDtoInterface> findForExport(Map<String, Object> param) throws MospException {
		try {
			// パラメータ取得
			Date targetDate = (Date)param.get(SectionDaoInterface.SEARCH_TARGET_DATE);
			String sectionCode = String.valueOf(param.get(SectionDaoInterface.SEARCH_SECTION_CODE));
			String closeFlag = String.valueOf(param.get(SectionDaoInterface.SEARCH_CLOSE_FLAG));
			// パラメータインデックス準備
			index = 1;
			// SQL作成
			StringBuffer sb = getSelectQuery(getClass());
			// 対象日以前で最新の情報を取得
			sb.append(getQueryForMaxActivateDate(TABLE, COL_SECTION_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			// 有効日における最新の情報を抽出する条件SQLを追加
			if (!sectionCode.isEmpty()) {
				sb.append(and());
				sb.append(leftParenthesis());
				sb.append(equal(COL_SECTION_CODE));
				sb.append(or());
				sb.append(like(COL_CLASS_ROUTE));
				sb.append(rightParenthesis());
			}
			if (!closeFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_CLOSE_FLAG));
			}
			sb.append(getOrderByColumn(COL_SECTION_CODE));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, targetDate, ps);
			// パラメータ設定
			if (!sectionCode.isEmpty()) {
				setParam(index++, sectionCode);
				setParam(index++, containsParam(getClassRouteParam(sectionCode)));
			}
			if (!closeFlag.isEmpty()) {
				setParam(index++, Integer.parseInt(closeFlag));
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			SectionDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmSectionId());
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
			SectionDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmSectionId());
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
		SectionDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmSectionId());
		setParam(index++, dto.getSectionCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getSectionName());
		setParam(index++, dto.getSectionAbbr());
		setParam(index++, dto.getSectionDisplay());
		setParam(index++, dto.getClassRoute());
		setParam(index++, dto.getCloseFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	@Override
	public String getQueryForSectionName() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_SECTION_CODE);
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_SECTION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(leftParenthesis());
		sb.append(like(COL_SECTION_NAME));
		sb.append(or());
		sb.append(like(COL_SECTION_ABBR));
		sb.append(or());
		sb.append(like(COL_SECTION_DISPLAY));
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public StringBuffer getQueryForLowerSection(String targetColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_SECTION_CODE);
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_SECTION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(leftParenthesis());
		sb.append(equal(COL_SECTION_CODE));
		sb.append(or());
		sb.append(leftParenthesis());
		sb.append(equal(COL_CLOSE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
		sb.append(and());
		sb.append(like(COL_CLASS_ROUTE));
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		return sb;
	}
	
	@Override
	public int setParamsForLowerSection(int index, String sectionCode, Date targetDate, PreparedStatement ps)
			throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// 下位所属条件パラメータ設定
		setParam(idx++, targetDate, false, ps);
		setParam(idx++, sectionCode, ps);
		setParam(idx++, containsParam(getClassRouteParam(sectionCode)), ps);
		// インデックス返却
		return idx;
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
		// 操作範囲条件SQL作成
		sb.append(and());
		sb.append(leftParenthesis());
		for (int i = 0; i < rangeArrayLength; i++) {
			sb.append(getQueryForLowerSection(targetColumn));
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
			idx = setParamsForLowerSection(idx, range, targetDate, ps);
		}
		// インデックス返却
		return idx;
	}
	
	@Override
	public String getClassRouteParam(String sectionCode) {
		return PlatformSystemConst.SEPARATOR_CLASS_ROUTE + sectionCode + PlatformSystemConst.SEPARATOR_CLASS_ROUTE;
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected SectionDtoInterface castDto(BaseDtoInterface baseDto) {
		return (SectionDtoInterface)baseDto;
	}
	
	@Override
	public StringBuffer getQueryForLowerSectionRange(String targetColumn, boolean fromExist, boolean toExist) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_SECTION_CODE);
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_SECTION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(leftParenthesis());
		// 指定範囲の所属コード検索条件
		sb.append(leftParenthesis());
		if (fromExist) {
			sb.append(greaterEqual(COL_SECTION_CODE));
			if (toExist) {
				sb.append(and());
			}
		}
		if (toExist) {
			sb.append(lessEqual(COL_SECTION_CODE));
		}
		sb.append(rightParenthesis());
		sb.append(or());
		// 指定範囲の所属コードの下位所属の検索条件
		sb.append(exists());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(" * "); // EXISTS なので、速度を稼ぐために全カラム指定
		sb.append(from(TABLE));
		sb.append(as(TMP_TALBE_LOWERE_SECTION_RANGE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_SECTION_CODE, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff(getExplicitTableColumn(TMP_TALBE_LOWERE_SECTION_RANGE, colDeleteFlag)));
		if (fromExist) {
			sb.append(and());
			sb.append(greaterEqual(getExplicitTableColumn(TMP_TALBE_LOWERE_SECTION_RANGE, COL_SECTION_CODE)));
		}
		if (toExist) {
			sb.append(and());
			sb.append(lessEqual(getExplicitTableColumn(TMP_TALBE_LOWERE_SECTION_RANGE, COL_SECTION_CODE)));
		}
		sb.append(and());
		sb.append(equal(getExplicitTableColumn(TABLE, COL_CLOSE_FLAG), MospConst.INACTIVATE_FLAG_OFF));
		sb.append(and());
		sb.append(getExplicitTableColumn(TABLE, COL_CLASS_ROUTE));
		sb.append(like());
		sb.append(concat("'%,'", getExplicitTableColumn(TMP_TALBE_LOWERE_SECTION_RANGE, COL_SECTION_CODE), "',%'"));
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		
		return sb;
	}
	
	@Override
	public int setParamsForLowerSectionRange(int index, String sectionCodeFrom, String sectionCodeTo, Date targetDate,
			PreparedStatement ps) throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// 下位所属条件パラメータ設定
		setParam(idx++, targetDate, false, ps);
		if (!sectionCodeFrom.isEmpty()) {
			setParam(idx++, sectionCodeFrom, ps);
		}
		if (!sectionCodeTo.isEmpty()) {
			setParam(idx++, sectionCodeTo, ps);
		}
		setParam(idx++, targetDate, false, ps);
		if (!sectionCodeFrom.isEmpty()) {
			setParam(idx++, sectionCodeFrom, ps);
		}
		if (!sectionCodeTo.isEmpty()) {
			setParam(idx++, sectionCodeTo, ps);
		}
		// インデックス返却
		return idx;
	}
	
}
