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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.impl.PfmHumanDto;

/**
 * 人事マスタDAOクラス。
 */
public class PfmHumanDao extends PlatformDao implements HumanDaoInterface {
	
	/**
	 * 人事マスタテーブル。
	 */
	public static final String	TABLE							= "pfm_human";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_HUMAN_ID				= "pfm_human_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID					= "personal_id";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * 社員コード。
	 */
	public static final String	COL_EMPLOYEE_CODE				= "employee_code";
	
	/**
	 * 姓。
	 */
	public static final String	COL_LAST_NAME					= "last_name";
	
	/**
	 * 名。
	 */
	public static final String	COL_FIRST_NAME					= "first_name";
	
	/**
	 * カナ姓。
	 */
	public static final String	COL_LAST_KANA					= "last_kana";
	
	/**
	 * カナ名。
	 */
	public static final String	COL_FIRST_KANA					= "first_kana";
	
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
	 * 勤務地コード。
	 */
	public static final String	COL_WORK_PLACE_CODE				= "work_place_code";
	
	/**
	 * メールアドレス。
	 */
	public static final String	COL_MAIL						= "mail";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_PFM_HUMAN_ID;
	
	/**
	 * シーケンス。
	 */
	public static final String	SEQUENCE						= "pfm_human_personal_id_seq";
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmHumanDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmHumanDto dto = new PfmHumanDto();
		dto.setPfmHumanId(getLong(COL_PFM_HUMAN_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setEmployeeCode(getString(COL_EMPLOYEE_CODE));
		dto.setLastName(getString(COL_LAST_NAME));
		dto.setFirstName(getString(COL_FIRST_NAME));
		dto.setLastKana(getString(COL_LAST_KANA));
		dto.setFirstKana(getString(COL_FIRST_KANA));
		dto.setEmploymentContractCode(getString(COL_EMPLOYMENT_CONTRACT_CODE));
		dto.setSectionCode(getString(COL_SECTION_CODE));
		dto.setPositionCode(getString(COL_POSITION_CODE));
		dto.setWorkPlaceCode(getString(COL_WORK_PLACE_CODE));
		dto.setMail(getString(COL_MAIL));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<HumanDtoInterface> mappingAll() throws MospException {
		List<HumanDtoInterface> all = new ArrayList<HumanDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	/**
	 * ResultSetの内容を、人事情報群(キー：個人ID)として取得する。<br>
	 * @return 人事情報群
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Map<String, HumanDtoInterface> mappingAllMap() throws MospException {
		Map<String, HumanDtoInterface> all = new HashMap<String, HumanDtoInterface>();
		while (next()) {
			all.put(getString(COL_PERSONAL_ID), castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public HumanDtoInterface findForKey(String personalId, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, activateDate);
			executeQuery();
			HumanDtoInterface dto = null;
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
	public List<HumanDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			// SELECT部追加
			sb.append(getSelectQuery(getClass()));
			// WHERE部追加(対象日以前で削除されていない最新の情報を取得)
			sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(getOrderByColumn(COL_EMPLOYEE_CODE));
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, activateDate);
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
	public Map<String, HumanDtoInterface> findForTargetDate(Date targetDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			// SELECT部追加
			sb.append(getSelectQuery(getClass()));
			// WHERE部追加(対象日以前で削除されていない最新の情報を取得)
			sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, targetDate);
			// SQL実行
			executeQuery();
			// 結果取得
			return mappingAllMap();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public HumanDtoInterface findForEmployeeCode(String employeeCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EMPLOYEE_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件パラメータを追加
			index = setParamsForMaxActivateDate(index, activateDate, ps);
			setParam(index++, employeeCode);
			setParam(index++, activateDate);
			executeQuery();
			HumanDtoInterface dto = null;
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
	public List<HumanDtoInterface> findForEmployeeCode(String employeeCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EMPLOYEE_CODE));
			prepareStatement(sb.toString());
			setParam(index++, employeeCode);
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
	public List<HumanDtoInterface> findForHistory(String personalId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
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
	public HumanDtoInterface findForInfo(String personalId, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, activateDate);
			executeQuery();
			HumanDtoInterface dto = null;
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
	public List<HumanDtoInterface> findAll() throws MospException {
		try {
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(getOrderByColumn(COL_EMPLOYEE_CODE, COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
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
	public Set<String> findForEmployeeNumbering() throws MospException {
		try {
			index = 1;
			// SQL作成準備(SELECT文追加)
			StringBuffer sb = new StringBuffer();
			sb.append(select() + COL_EMPLOYEE_CODE);
			sb.append(from(TABLE));
			// WHERE部追加(削除フラグが立っていない情報全て)
			sb.append(where());
			sb.append(deleteFlagOff());
			// ステートメント取得
			prepareStatement(sb.toString());
			// 実行
			executeQuery();
			return getResultAsSet(COL_EMPLOYEE_CODE);
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<HumanDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate) throws MospException {
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
			sb.append(getOrderByColumn(COL_EMPLOYEE_CODE, COL_ACTIVATE_DATE));
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
	public long nextPersonalId() throws MospException {
		return nextValue(SEQUENCE);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			HumanDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmHumanId());
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
			HumanDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmHumanId());
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
		HumanDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmHumanId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getEmployeeCode());
		setParam(index++, dto.getLastName());
		setParam(index++, dto.getFirstName());
		setParam(index++, dto.getLastKana());
		setParam(index++, dto.getFirstKana());
		setParam(index++, dto.getEmploymentContractCode());
		setParam(index++, dto.getSectionCode());
		setParam(index++, dto.getPositionCode());
		setParam(index++, dto.getWorkPlaceCode());
		setParam(index++, dto.getMail());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public String getQueryForEmployeeCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(like(COL_EMPLOYEE_CODE));
		return sb.toString();
	}
	
	/**
	 * 社員氏名条件SQLを作成する。<br>
	 * 姓、名を分けずに条件を指定する場合に用いる。<br>
	 * @return 社員氏名条件条件SQL
	 */
	protected String getQueryForEmployeeName() {
		StringBuffer sb = new StringBuffer();
		sb.append(and());
		sb.append(leftParenthesis());
		sb.append(like(COL_LAST_NAME));
		sb.append(or());
		sb.append(like(COL_FIRST_NAME));
		sb.append(or());
		sb.append(like(concat(COL_LAST_NAME, COL_FIRST_NAME)));
		sb.append(or());
		sb.append(like(concat(COL_LAST_NAME, blank(), COL_FIRST_NAME)));
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	/**
	 * 社員氏名条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index        パラメータインデックス
	 * @param employeeName 社員氏名条件パラメータ
	 * @param ps           ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	protected int setParamsForEmployeeName(int index, String employeeName, PreparedStatement ps) throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// 社員氏名条件パラメータ設定
		setParam(idx++, containsParam(employeeName), ps);
		setParam(idx++, containsParam(employeeName), ps);
		setParam(idx++, containsParam(employeeName), ps);
		setParam(idx++, containsParam(employeeName), ps);
		// インデックス返却
		return idx;
	}
	
	@Override
	public String getQueryForEmployeeName(String targetColumn) {
		StringBuffer sb = new StringBuffer();
		sb.append(and());
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(getQueryForEmployeeName());
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForEmployeeName(int index, String employeeName, Date targetDate, PreparedStatement ps)
			throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// 社員氏名条件パラメータ設定
		setParam(idx++, targetDate, false, ps);
		idx = setParamsForEmployeeName(idx, employeeName, ps);
		// インデックス返却
		return idx;
	}
	
	@Override
	public String getQueryForWorkPlaceCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(equal(COL_WORK_PLACE_CODE));
		return sb.toString();
	}
	
	@Override
	public String getQueryForEmploymentContractCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
		return sb.toString();
	}
	
	@Override
	public String getQueryForSectionCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(equal(COL_SECTION_CODE));
		return sb.toString();
	}
	
	@Override
	public String getQueryForLowerSection() throws MospException {
		// 所属マスタDAO準備(サブクエリ取得用)
		SectionDaoInterface sectionDao = (SectionDaoInterface)loadDao(SectionDaoInterface.class);
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(sectionDao.getQueryForLowerSection(COL_SECTION_CODE));
		return sb.toString();
	}
	
	@Override
	public String getQueryForSectionName() throws MospException {
		// 所属マスタDAO準備(サブクエリ取得用)
		SectionDaoInterface sectionDao = (SectionDaoInterface)loadDao(SectionDaoInterface.class);
		// SQL作成
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(COL_SECTION_CODE);
		sb.append(in());
		sb.append(leftParenthesis());
		sb.append(sectionDao.getQueryForSectionName());
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public String getQueryForPositionCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_PERSONAL_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(equal(COL_POSITION_CODE));
		return sb.toString();
	}
	
	/**
	 * 操作範囲条件SQLを作成する。<br>
	 * 操作範囲対象列が操作範囲配列内のいずれかの値のレコードのみを抽出する条件を作成する。<br>
	 * @param rangeArray   操作範囲配列
	 * @param targetColumn 操作範囲制限対象列名
	 * @return 操作範囲条件SQL
	 */
	protected String getQueryForRange(String[] rangeArray, String targetColumn) {
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
			sb.append(equal(targetColumn));
			if (i < rangeArrayLength - 1) {
				sb.append(or());
			}
		}
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	/**
	 * 操作範囲条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index      パラメータインデックス
	 * @param rangeArray 操作範囲配列
	 * @param ps         ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	protected int setParamsForRange(int index, String[] rangeArray, PreparedStatement ps) throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// 操作範囲条件パラメータ設定
		for (String range : rangeArray) {
			setParam(idx++, range, ps);
		}
		// インデックス返却
		return idx;
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected HumanDtoInterface castDto(BaseDtoInterface baseDto) {
		return (HumanDtoInterface)baseDto;
	}
	
}
