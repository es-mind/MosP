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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.system.AppPropertyDaoInterface;
import jp.mosp.platform.dto.system.AppPropertyDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmAppPropertyDto;

/**
 * システム管理情報DAOクラス。<br>
 */
public class PfmAppPropertyDao extends PlatformDao implements AppPropertyDaoInterface {
	
	/**
	 * システム管理情報テーブル。<br>
	 */
	public static final String	TABLE					= "pfm_app_property";
	
	/**
	 * レコード識別ID。<br>
	 */
	public static final String	COL_PFM_APP_PROPERTY_ID	= "pfm_app_property_id";
	
	/**
	 * アプリケーション設定キー。<br>
	 */
	public static final String	COL_APP_KEY				= "app_key";
	
	/**
	 * アプリケーション設定値。<br>
	 */
	public static final String	COL_APP_VALUE			= "app_value";
	
	/**
	 * キー。<br>
	 */
	public static final String	KEY_1					= COL_PFM_APP_PROPERTY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmAppPropertyDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理なし
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmAppPropertyDto dto = new PfmAppPropertyDto();
		dto.setPfmAppPropertyId(getLong(COL_PFM_APP_PROPERTY_ID));
		dto.setAppKey(getString(COL_APP_KEY));
		dto.setAppValue(getString(COL_APP_VALUE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<?> mappingAll() {
		// 処理無し(当テーブルへの処理は不要)
		return Collections.emptyList();
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			AppPropertyDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmAppPropertyId());
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
	public int delete(BaseDtoInterface baseDto) {
		// 処理無し(当テーブルへの削除処理は不要)
		return 0;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		AppPropertyDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmAppPropertyId());
		setParam(index++, dto.getAppKey());
		setParam(index++, dto.getAppValue());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected AppPropertyDtoInterface castDto(BaseDtoInterface baseDto) {
		return (AppPropertyDtoInterface)baseDto;
	}
	
	@Override
	public Optional<AppPropertyDtoInterface> findForKey(String appkey) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_APP_KEY));
			prepareStatement(sb.toString());
			setParam(index++, appkey);
			executeQuery();
			AppPropertyDtoInterface dto = null;
			if (next()) {
				dto = castDto(mapping());
			}
			return Optional.ofNullable(dto);
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
