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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.AddressDaoInterface;
import jp.mosp.platform.dto.human.AddressDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaAddressDto;

/**
 * 住所情報DAOクラス。
 */
public class PfaAddressDao extends PlatformDao implements AddressDaoInterface {
	
	/**
	 * 住所情報。
	 */
	public static final String	TABLE				= "pfa_address";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_ADDRESS_ID	= "pfa_address_id";
	
	/**
	 * 保持者ID。
	 */
	public static final String	COL_HOLDER_ID		= "holder_id";
	
	/**
	 * 住所区分。
	 */
	public static final String	COL_ADDRESS_TYPE	= "address_type";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE	= "activate_date";
	
	/**
	 * 郵便番号1。
	 */
	public static final String	COL_POSTAL_CODE_1	= "postal_code_1";
	
	/**
	 * 郵便番号2。
	 */
	public static final String	COL_POSTAL_CODE_2	= "postal_code_2";
	
	/**
	 * 都道府県コード。<br>
	 */
	public static final String	COL_PREFECTURE		= "prefecture";
	
	/**
	 * 市区町村コード。<br>
	 */
	public static final String	COL_CITY_CODE		= "city_code";
	
	/**
	 * 市区町村。
	 */
	public static final String	COL_ADDRESS			= "address";
	
	/**
	 * 番地。
	 */
	public static final String	COL_ADDRESS_NUMBER	= "address_number";
	
	/**
	 * 建物情報。
	 */
	public static final String	COL_BUILDING		= "building";
	
	/**
	 * 申請区分。
	 */
	public static final String	COL_REQUEST_TYPE	= "request_type";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	KEY_1				= COL_PFA_ADDRESS_ID;
	
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaAddressDto dto = new PfaAddressDto();
		dto.setPfaAddressId(getLong(COL_PFA_ADDRESS_ID));
		dto.setHolderId(getString(COL_HOLDER_ID));
		dto.setAddressType(getString(COL_ADDRESS_TYPE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setPostalCode1(getString(COL_POSTAL_CODE_1));
		dto.setPostalCode2(getString(COL_POSTAL_CODE_2));
		dto.setPrefecture(getString(COL_PREFECTURE));
		dto.setCityCode(getString(COL_CITY_CODE));
		dto.setAddress(getString(COL_ADDRESS));
		dto.setAddressNumber(getString(COL_ADDRESS_NUMBER));
		dto.setBuilding(getString(COL_BUILDING));
		dto.setRequestType(getString(COL_REQUEST_TYPE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<AddressDtoInterface> mappingAll() throws MospException {
		List<AddressDtoInterface> all = new ArrayList<AddressDtoInterface>();
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
	protected Set<AddressDtoInterface> mappingAllSet() throws MospException {
		Set<AddressDtoInterface> all = new HashSet<AddressDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		AddressDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaAddressId());
		setParam(index++, dto.getHolderId());
		setParam(index++, dto.getAddressType());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getPostalCode1());
		setParam(index++, dto.getPostalCode2());
		setParam(index++, dto.getPrefecture());
		setParam(index++, dto.getCityCode());
		setParam(index++, dto.getAddress());
		setParam(index++, dto.getAddressNumber());
		setParam(index++, dto.getBuilding());
		setParam(index++, dto.getRequestType());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			AddressDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaAddressId());
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
			AddressDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaAddressId());
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
	protected AddressDtoInterface castDto(BaseDtoInterface baseDto) {
		return (AddressDtoInterface)baseDto;
	}
	
	@Override
	public AddressDtoInterface findForKey(String holderId, String addressType, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_ADDRESS_TYPE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, addressType);
			setParam(index++, activateDate);
			executeQuery();
			AddressDtoInterface dto = null;
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
	public AddressDtoInterface findForInfo(String holderId, String addressType, Date targetDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_ADDRESS_TYPE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, addressType);
			setParam(index++, targetDate);
			executeQuery();
			AddressDtoInterface dto = null;
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
	public List<AddressDtoInterface> findForHolder(String holderId, String addressType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_ADDRESS_TYPE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, addressType);
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
	public Set<AddressDtoInterface> findForRequestType(String holderId, Date activateDate, String requestType)
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
