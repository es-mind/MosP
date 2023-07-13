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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.system.UserPasswordDaoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.platform.dto.system.impl.PfaUserPasswordDto;

/**
 * ユーザパスワード情報DAOクラス。
 */
public class PfaUserPasswordDao extends PlatformDao implements UserPasswordDaoInterface {
	
	/**
	 * ユーザパスワード情報。
	 */
	public static final String	TABLE						= "pfa_user_password";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_USER_PASSWORD_ID	= "pfa_user_password_id";
	
	/**
	 * ユーザID。
	 */
	public static final String	COL_USER_ID					= "user_id";
	
	/**
	 * 変更日。
	 */
	public static final String	COL_CHANGE_DATE				= "change_date";
	
	/**
	 * パスワード。
	 */
	public static final String	COL_PASSWORD				= "password";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_PFA_USER_PASSWORD_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaUserPasswordDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaUserPasswordDto dto = new PfaUserPasswordDto();
		dto.setPfaUserPasswordId(getLong(COL_PFA_USER_PASSWORD_ID));
		dto.setUserId(getString(COL_USER_ID));
		dto.setChangeDate(getDate(COL_CHANGE_DATE));
		dto.setPassword(getString(COL_PASSWORD));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<?> mappingAll() throws MospException {
		List<UserPasswordDtoInterface> all = new ArrayList<UserPasswordDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public UserPasswordDtoInterface findForKey(String userId, Date changeDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_USER_ID));
			sb.append(and());
			sb.append(equal(COL_CHANGE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, userId);
			setParam(index++, changeDate);
			executeQuery();
			UserPasswordDtoInterface dto = null;
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			UserPasswordDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaUserPasswordId());
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
			UserPasswordDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaUserPasswordId());
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
		UserPasswordDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaUserPasswordId());
		setParam(index++, dto.getUserId());
		setParam(index++, dto.getChangeDate());
		setParam(index++, dto.getPassword());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public UserPasswordDtoInterface findForInfo(String userId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_USER_ID));
			sb.append(getOrderByColumnDescLimit1(COL_CHANGE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, userId);
			executeQuery();
			UserPasswordDtoInterface dto = null;
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
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected UserPasswordDtoInterface castDto(BaseDtoInterface baseDto) {
		return (UserPasswordDtoInterface)baseDto;
	}
	
}
