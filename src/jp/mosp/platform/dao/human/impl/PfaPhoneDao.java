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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.PhoneDaoInterface;
import jp.mosp.platform.dto.human.PhoneDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaPhoneDto;

/**
 * 人事電話情報DAOクラス。
 */
public class PfaPhoneDao extends PlatformDao implements PhoneDaoInterface {
	
	/**
	 * 人事住所情報。
	 */
	public static final String	TABLE				= "pfa_phone";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_PHONE_ID	= "pfa_phone_id";
	
	/**
	 * 保持者ID。
	 */
	public static final String	COL_HOLDER_ID		= "holder_id";
	
	/**
	 * 電話区分。
	 */
	public static final String	COL_PHONE_TYPE		= "phone_type";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE	= "activate_date";
	
	/**
	 * 電話番号1。
	 */
	public static final String	COL_PHONE_NUMBER_1	= "phone_number_1";
	
	/**
	 * 電話番号2。
	 */
	public static final String	COL_PHONE_NUMBER_2	= "phone_number_2";
	
	/**
	 * 電話番号3。
	 */
	public static final String	COL_PHONE_NUMBER_3	= "phone_number_3";
	
	/**
	 * 申請区分。
	 */
	public static final String	COL_REQUEST_TYPE	= "request_type";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	KEY_1				= COL_PFA_PHONE_ID;
	
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaPhoneDto dto = new PfaPhoneDto();
		dto.setPfaPhoneId(getLong(COL_PFA_PHONE_ID));
		dto.setHolderId(getString(COL_HOLDER_ID));
		dto.setPhoneType(getString(COL_PHONE_TYPE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setPhoneNumber1(getString(COL_PHONE_NUMBER_1));
		dto.setPhoneNumber2(getString(COL_PHONE_NUMBER_2));
		dto.setPhoneNumber3(getString(COL_PHONE_NUMBER_3));
		dto.setRequestType(getString(COL_REQUEST_TYPE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PhoneDtoInterface> mappingAll() throws MospException {
		List<PhoneDtoInterface> all = new ArrayList<PhoneDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	/**
	 * ResultSetの内容を、DTOにマッピングし、セットとして返す。<br>
	 * <br>
	 * @return 検索結果(DTOのSet)。
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Set<PhoneDtoInterface> mappingAllSet() throws MospException {
		Set<PhoneDtoInterface> all = new HashSet<PhoneDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		PhoneDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaPhoneId());
		setParam(index++, dto.getHolderId());
		setParam(index++, dto.getPhoneType());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getPhoneNumber1());
		setParam(index++, dto.getPhoneNumber2());
		setParam(index++, dto.getPhoneNumber3());
		setParam(index++, dto.getRequestType());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			PhoneDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaPhoneId());
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
			PhoneDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaPhoneId());
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
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected PhoneDtoInterface castDto(BaseDtoInterface baseDto) {
		return (PhoneDtoInterface)baseDto;
	}
	
	@Override
	public PhoneDtoInterface findForKey(String holderId, String phoneType, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_PHONE_TYPE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, phoneType);
			setParam(index++, activateDate);
			executeQuery();
			PhoneDtoInterface dto = null;
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
	public PhoneDtoInterface findForInfo(String holderId, String phoneType, Date targetDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_PHONE_TYPE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, phoneType);
			setParam(index++, targetDate);
			executeQuery();
			PhoneDtoInterface dto = null;
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
	public List<PhoneDtoInterface> findForHolder(String holderId, String phoneType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_PHONE_TYPE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, phoneType);
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
	public Set<PhoneDtoInterface> findForRequestType(String holderId, Date activateDate, String requestType)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_REQUEST_TYPE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, activateDate);
			setParam(index++, requestType);
			executeQuery();
			return mappingAllSet();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
