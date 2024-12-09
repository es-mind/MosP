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
import jp.mosp.platform.dao.system.WorkPlaceDaoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmWorkPlaceDto;

/**
 * 勤務地マスタDAOクラス。
 */
public class PfmWorkPlaceDao extends PlatformDao implements WorkPlaceDaoInterface {
	
	/**
	 * 勤務地マスタ。
	 */
	public static final String	TABLE					= "pfm_work_place";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_WORK_PLACE_ID	= "pfm_work_place_id";
	
	/**
	 * 勤務地コード。
	 */
	public static final String	COL_WORK_PLACE_CODE		= "work_place_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE		= "activate_date";
	
	/**
	 * 勤務地名称。
	 */
	public static final String	COL_WORK_PLACE_NAME		= "work_place_name";
	
	/**
	 * 勤務地カナ。
	 */
	public static final String	COL_WORK_PLACE_KANA		= "work_place_kana";
	
	/**
	 * 勤務地略称。
	 */
	public static final String	COL_WORK_PLACE_ABBR		= "work_place_abbr";
	
	/**
	 * 勤務地都道府県。
	 */
	public static final String	COL_PREFECTURE			= "prefecture";
	
	/**
	 * 勤務地市区町村。
	 */
	public static final String	COL_ADDRESS_1			= "address_1";
	
	/**
	 * 勤務地番地。
	 */
	public static final String	COL_ADDRESS_2			= "address_2";
	
	/**
	 * 勤務地建物情報。
	 */
	public static final String	COL_ADDRESS_3			= "address_3";
	
	/**
	 * 勤務地郵便番号1。
	 */
	public static final String	COL_POSTAL_CODE_1		= "postal_code_1";
	
	/**
	 * 勤務地郵便番号2。
	 */
	public static final String	COL_POSTAL_CODE_2		= "postal_code_2";
	
	/**
	 * 勤務地電話番号1。
	 */
	public static final String	COL_PHONE_NUMBER_1		= "phone_number_1";
	
	/**
	 * 勤務地電話番号2。
	 */
	public static final String	COL_PHONE_NUMBER_2		= "phone_number_2";
	
	/**
	 * 勤務地電話番号3。
	 */
	public static final String	COL_PHONE_NUMBER_3		= "phone_number_3";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_PFM_WORK_PLACE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmWorkPlaceDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmWorkPlaceDto dto = new PfmWorkPlaceDto();
		dto.setPfmWorkPlaceId(getLong(COL_PFM_WORK_PLACE_ID));
		dto.setWorkPlaceCode(getString(COL_WORK_PLACE_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setWorkPlaceName(getString(COL_WORK_PLACE_NAME));
		dto.setWorkPlaceKana(getString(COL_WORK_PLACE_KANA));
		dto.setWorkPlaceAbbr(getString(COL_WORK_PLACE_ABBR));
		dto.setPrefecture(getString(COL_PREFECTURE));
		dto.setAddress1(getString(COL_ADDRESS_1));
		dto.setAddress2(getString(COL_ADDRESS_2));
		dto.setAddress3(getString(COL_ADDRESS_3));
		dto.setPostalCode1(getString(COL_POSTAL_CODE_1));
		dto.setPostalCode2(getString(COL_POSTAL_CODE_2));
		dto.setPhoneNumber1(getString(COL_PHONE_NUMBER_1));
		dto.setPhoneNumber2(getString(COL_PHONE_NUMBER_2));
		dto.setPhoneNumber3(getString(COL_PHONE_NUMBER_3));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkPlaceDtoInterface> mappingAll() throws MospException {
		List<WorkPlaceDtoInterface> all = new ArrayList<WorkPlaceDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public WorkPlaceDtoInterface findForKey(String workPlaceCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_PLACE_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workPlaceCode);
			setParam(index++, activateDate);
			executeQuery();
			WorkPlaceDtoInterface dto = null;
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
	public List<WorkPlaceDtoInterface> findForHistory(String workPlaceCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_PLACE_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workPlaceCode);
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
	public WorkPlaceDtoInterface findForInfo(String workPlaceCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_PLACE_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ?");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workPlaceCode);
			setParam(index++, activateDate);
			executeQuery();
			WorkPlaceDtoInterface dto = null;
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
	public List<WorkPlaceDtoInterface> findForActivateDate(Date targetDate, String[] rangeArray) throws MospException {
		try {
			// パラメータインデックス準備
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(getQueryForMaxActivateDate(TABLE, COL_WORK_PLACE_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			// 削除されていない情報のみ抽出
			sb.append(deleteFlagOff());
			// 有効な情報のみ抽出
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			// 操作範囲条件SQL追加
			sb.append(getQueryForRange(rangeArray, COL_WORK_PLACE_CODE));
			// 並べ替え
			sb.append(getOrderByColumn(COL_WORK_PLACE_CODE));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, targetDate, ps);
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
	public List<WorkPlaceDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String workPlaceCode = (String)param.get("workPlaceCode");
			String workPlaceName = (String)param.get("workPlaceName");
			String workPlaceKana = (String)param.get("workPlaceKana");
			String workPlaceAbbr = (String)param.get("workPlaceAbbr");
			String postalCode1 = (String)param.get("postalCode1");
			String postalCode2 = (String)param.get("postalCode2");
			String prefecture = (String)param.get("prefecture");
			String address1 = (String)param.get("address1");
			String address2 = (String)param.get("address2");
			String address3 = (String)param.get("address3");
			String phoneNumber1 = (String)param.get("phoneNumber1");
			String phoneNumber2 = (String)param.get("phoneNumber2");
			String phoneNumber3 = (String)param.get("phoneNumber3");
			String inactivateFlag = (String)param.get("inactivateFlag");
			
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			if (!(activateDate == null)) {
				sb.append(getQueryForMaxActivateDate(TABLE, COL_WORK_PLACE_CODE, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_WORK_PLACE_CODE));
			sb.append(and());
			sb.append(like(COL_WORK_PLACE_NAME));
			sb.append(and());
			sb.append(like(COL_WORK_PLACE_KANA));
			sb.append(and());
			sb.append(like(COL_WORK_PLACE_ABBR));
			sb.append(and());
			sb.append(like(COL_POSTAL_CODE_1));
			sb.append(and());
			sb.append(like(COL_POSTAL_CODE_2));
			sb.append(and());
			sb.append(like(COL_ADDRESS_1));
			sb.append(and());
			sb.append(like(COL_ADDRESS_2));
			sb.append(and());
			sb.append(like(COL_ADDRESS_3));
			sb.append(and());
			sb.append(like(COL_PHONE_NUMBER_1));
			sb.append(and());
			sb.append(like(COL_PHONE_NUMBER_2));
			sb.append(and());
			sb.append(like(COL_PHONE_NUMBER_3));
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			if (!prefecture.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_PREFECTURE));
			}
			prepareStatement(sb.toString());
			if (!(activateDate == null)) {
				index = setParamsForMaxActivateDate(index, activateDate, ps);
			}
			setParam(index++, startWithParam(workPlaceCode));
			setParam(index++, containsParam(workPlaceName));
			setParam(index++, containsParam(workPlaceKana));
			setParam(index++, containsParam(workPlaceAbbr));
			setParam(index++, containsParam(postalCode1));
			setParam(index++, containsParam(postalCode2));
			setParam(index++, containsParam(address1));
			setParam(index++, containsParam(address2));
			setParam(index++, containsParam(address3));
			setParam(index++, containsParam(phoneNumber1));
			setParam(index++, containsParam(phoneNumber2));
			setParam(index++, containsParam(phoneNumber3));
			if (inactivateFlag.isEmpty() == false) {
				setParam(index++, Integer.parseInt(inactivateFlag));
			}
			if (prefecture.isEmpty() == false) {
				setParam(index++, prefecture);
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
			WorkPlaceDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmWorkPlaceId());
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
			WorkPlaceDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmWorkPlaceId());
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
		WorkPlaceDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmWorkPlaceId());
		setParam(index++, dto.getWorkPlaceCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getWorkPlaceName());
		setParam(index++, dto.getWorkPlaceKana());
		setParam(index++, dto.getWorkPlaceAbbr());
		setParam(index++, dto.getPrefecture());
		setParam(index++, dto.getAddress1());
		setParam(index++, dto.getAddress2());
		setParam(index++, dto.getAddress3());
		setParam(index++, dto.getPostalCode1());
		setParam(index++, dto.getPostalCode2());
		setParam(index++, dto.getPhoneNumber1());
		setParam(index++, dto.getPhoneNumber2());
		setParam(index++, dto.getPhoneNumber3());
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
	protected WorkPlaceDtoInterface castDto(BaseDtoInterface baseDto) {
		return (WorkPlaceDtoInterface)baseDto;
	}
	
}
