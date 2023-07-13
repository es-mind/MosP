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

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.system.PostalCodeDaoInterface;
import jp.mosp.platform.dto.system.PostalCodeDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmPostalCodeDto;

/**
 * 郵便番号DAOクラス。<br>
 */
public class PfmPostalCodeDao extends PlatformDao implements PostalCodeDaoInterface {
	
	/**
	 * テーブル。
	 */
	public static final String	TABLE					= "pfm_postal_code";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_POSTAL_CODE_ID	= "pfm_postal_code_id";
	
	/**
	 * 郵便番号。
	 */
	public static final String	COL_POSTAL_CODE			= "postal_code";
	
	/**
	 * 都道府県コード。
	 */
	public static final String	COL_PREFECTURE_CODE		= "prefecture_code";
	
	/**
	 * 市区町村コード。
	 */
	public static final String	COL_CITY_CODE			= "city_code";
	
	/**
	 * 市区町村名。
	 */
	public static final String	COL_CITY_NAME			= "city_name";
	
	/**
	 * 市区町村名カナ。
	 */
	public static final String	COL_CITY_KANA			= "city_kana";
	
	/**
	 * 町域名。
	 */
	public static final String	COL_ADDRESS_NAME		= "address_name";
	
	/**
	 * 町域名カナ。
	 */
	public static final String	COL_ADDRESS_KANA		= "address_kana";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_PFM_POSTAL_CODE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmPostalCodeDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		// DTOの準備
		PfmPostalCodeDto dto = new PfmPostalCodeDto();
		// ResultSetの中身をDTOに設定
		dto.setPfmPostalCodeId(getLong(COL_PFM_POSTAL_CODE_ID));
		dto.setPostalCode(getString(COL_POSTAL_CODE));
		dto.setPrefectureCode(getString(COL_PREFECTURE_CODE));
		dto.setCityCode(getString(COL_CITY_CODE));
		dto.setCityName(getString(COL_CITY_NAME));
		dto.setCityKana(getString(COL_CITY_KANA));
		dto.setAddressName(getString(COL_ADDRESS_NAME));
		dto.setAddressKana(getString(COL_ADDRESS_KANA));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		// 共通列情報をDTOに設定
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PostalCodeDtoInterface> mappingAll() throws MospException {
		// DTOのリストを準備
		List<PostalCodeDtoInterface> all = new ArrayList<PostalCodeDtoInterface>();
		// ResultSetの中身をDTOに設定しリストに追加
		while (next()) {
			all.add((PostalCodeDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			PostalCodeDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmPostalCodeId());
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
			PostalCodeDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmPostalCodeId());
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
		PostalCodeDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmPostalCodeId());
		setParam(index++, dto.getPostalCode());
		setParam(index++, dto.getPrefectureCode());
		setParam(index++, dto.getCityCode());
		setParam(index++, dto.getCityName());
		setParam(index++, dto.getCityKana());
		setParam(index++, dto.getAddressName());
		setParam(index++, dto.getAddressKana());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected PostalCodeDtoInterface castDto(BaseDtoInterface baseDto) {
		return (PostalCodeDtoInterface)baseDto;
	}
	
	@Override
	public PostalCodeDtoInterface findForKey(String postalCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_POSTAL_CODE));
			prepareStatement(sb.toString());
			setParam(index++, postalCode);
			executeQuery();
			PostalCodeDtoInterface dto = null;
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
}
