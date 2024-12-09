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
import jp.mosp.platform.dao.system.EmploymentContractDaoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmEmploymentContractDto;

/**
 * 雇用契約マスタDAOクラス。
 */
public class PfmEmploymentContractDao extends PlatformDao implements EmploymentContractDaoInterface {
	
	/**
	 * 雇用契約マスタ。
	 */
	public static final String	TABLE							= "pfm_employment_contract";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_EMPLOYMENT_CONTRACT_ID	= "pfm_employment_contract_id";
	
	/**
	 * 雇用契約コード。
	 */
	public static final String	COL_EMPLOYMENT_CONTRACT_CODE	= "employment_contract_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * 雇用契約名称。
	 */
	public static final String	COL_EMPLOYMENT_CONTRACT_NAME	= "employment_contract_name";
	
	/**
	 * 雇用契約略称。
	 */
	public static final String	COL_EMPLOYMENT_CONTRACT_ABBR	= "employment_contract_abbr";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG				= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_PFM_EMPLOYMENT_CONTRACT_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmEmploymentContractDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		// DTOの準備
		PfmEmploymentContractDto dto = new PfmEmploymentContractDto();
		// ResultSetの中身をDTOに設定
		dto.setPfmEmploymentContractId(getLong(COL_PFM_EMPLOYMENT_CONTRACT_ID));
		dto.setEmploymentContractCode(getString(COL_EMPLOYMENT_CONTRACT_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setEmploymentContractName(getString(COL_EMPLOYMENT_CONTRACT_NAME));
		dto.setEmploymentContractAbbr(getString(COL_EMPLOYMENT_CONTRACT_ABBR));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		// 共通列情報をDTOに設定
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<EmploymentContractDtoInterface> mappingAll() throws MospException {
		// DTOのリストを準備
		List<EmploymentContractDtoInterface> all = new ArrayList<EmploymentContractDtoInterface>();
		// ResultSetの中身をDTOに設定しリストに追加
		while (next()) {
			all.add((EmploymentContractDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<EmploymentContractDtoInterface> findForActivateDate(Date activateDate, String[] rangeArray)
			throws MospException {
		try {
			// パラメータインデックス準備
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_EMPLOYMENT_CONTRACT_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			// 削除されていない情報のみ抽出
			sb.append(deleteFlagOff());
			// 有効な情報のみ抽出
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			// 操作範囲条件SQL追加
			sb.append(getQueryForRange(rangeArray, COL_EMPLOYMENT_CONTRACT_CODE));
			// 並べ替え
			sb.append(getOrderByColumn(COL_EMPLOYMENT_CONTRACT_CODE));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, activateDate, ps);
			// 操作範囲条件パラメータ設定
			index = setParamsForRange(index, rangeArray, ps);
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
	public List<EmploymentContractDtoInterface> findForHistory(String employmentContractCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, employmentContractCode);
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
	public EmploymentContractDtoInterface findForKey(String employmentContractCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, employmentContractCode);
			setParam(index++, activateDate);
			executeQuery();
			EmploymentContractDtoInterface dto = null;
			if (next()) {
				dto = (EmploymentContractDtoInterface)mapping();
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
	public EmploymentContractDtoInterface findForInfo(String employmentContractCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, employmentContractCode);
			setParam(index++, activateDate);
			executeQuery();
			EmploymentContractDtoInterface dto = null;
			if (next()) {
				dto = (EmploymentContractDtoInterface)mapping();
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
	public List<EmploymentContractDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String employmentCode = String.valueOf(param.get("employmentCode"));
			String employmentName = String.valueOf(param.get("employmentName"));
			String employmentAbbr = String.valueOf(param.get("employmentAbbr"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			if (activateDate != null) {
				// 有効日における最新の情報を抽出する条件SQLを追加
				sb.append(getQueryForMaxActivateDate(TABLE, COL_EMPLOYMENT_CONTRACT_CODE, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_EMPLOYMENT_CONTRACT_CODE));
			sb.append(and());
			sb.append(like(COL_EMPLOYMENT_CONTRACT_NAME));
			sb.append(and());
			sb.append(like(COL_EMPLOYMENT_CONTRACT_ABBR));
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			if (activateDate != null) {
				// 有効日における最新の情報を抽出する条件のパラメータを設定
				index = setParamsForMaxActivateDate(index, activateDate, ps);
			}
			setParam(index++, startWithParam(employmentCode));
			setParam(index++, containsParam(employmentName));
			setParam(index++, containsParam(employmentAbbr));
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
			EmploymentContractDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmEmploymentContractId());
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
			EmploymentContractDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmEmploymentContractId());
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
		EmploymentContractDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmEmploymentContractId());
		setParam(index++, dto.getEmploymentContractCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getEmploymentContractName());
		setParam(index++, dto.getEmploymentContractAbbr());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	@Override
	public String getQueryForRange(String[] rangeArray, String targetColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		// 操作範囲配列長取得
		int rangeArrayLength = rangeArray.length;
		// 操作範囲確認
		if (rangeArrayLength == 0) {
			// 操作範囲条件不要
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
	
	@Override
	public int setParamsForRange(int index, String[] rangeArray, PreparedStatement ps) throws MospException {
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
	protected EmploymentContractDtoInterface castDto(BaseDtoInterface baseDto) {
		return (EmploymentContractDtoInterface)baseDto;
	}
	
}
