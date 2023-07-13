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
import jp.mosp.platform.dao.system.UserExtraRoleDaoInterface;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;
import jp.mosp.platform.dto.system.impl.PfaUserExtraRoleDto;

/**
 * ユーザ追加ロール情報DAOクラス。<br>
 */
public class PfaUserExtraRoleDao extends PlatformDao implements UserExtraRoleDaoInterface {
	
	/**
	 * ユーザ追加ロール情報テーブル。<br>
	 */
	public static final String	TABLE						= "pfa_user_extra_role";
	
	/**
	 * レコード識別ID。<br>
	 */
	public static final String	COL_PFA_USER_EXTRA_ROLE_ID	= "pfa_user_extra_role_id";
	
	/**
	 * ユーザID。<br>
	 */
	public static final String	COL_USER_ID					= "user_id";
	
	/**
	 * 有効日。<br>
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * ロール区分。<br>
	 */
	public static final String	COL_ROLE_TYPE				= "role_type";
	
	/**
	 * ロールコード。<br>
	 */
	public static final String	COL_ROLE_CODE				= "role_code";
	
	/**
	 * キー。<br>
	 */
	public static final String	KEY_1						= COL_PFA_USER_EXTRA_ROLE_ID;
	
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaUserExtraRoleDto dto = new PfaUserExtraRoleDto();
		dto.setPfaUserExtraRoleId(getLong(COL_PFA_USER_EXTRA_ROLE_ID));
		dto.setUserId(getString(COL_USER_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setRoleType(getString(COL_ROLE_TYPE));
		dto.setRoleCode(getString(COL_ROLE_CODE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<UserExtraRoleDtoInterface> mappingAll() throws MospException {
		List<UserExtraRoleDtoInterface> all = new ArrayList<UserExtraRoleDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected UserExtraRoleDtoInterface castDto(BaseDtoInterface baseDto) {
		return (UserExtraRoleDtoInterface)baseDto;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			UserExtraRoleDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaUserExtraRoleId());
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
			UserExtraRoleDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaUserExtraRoleId());
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
		UserExtraRoleDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaUserExtraRoleId());
		setParam(index++, dto.getUserId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getRoleType());
		setParam(index++, dto.getRoleCode());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public List<UserExtraRoleDtoInterface> findForUser(String userId, Date activateDate) throws MospException {
		try {
			// インデックス初期化
			index = 1;
			// SQL作成
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_USER_ID));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumn(COL_ROLE_TYPE));
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, userId);
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
	public List<UserExtraRoleDtoInterface> findForRoleType(String roleType) throws MospException {
		try {
			// インデックス初期化
			index = 1;
			// SQL作成
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_ROLE_TYPE));
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, roleType);
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
	public String getQueryForJoinUser(String userIdColumn, String activateDateColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(leftJoin());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_USER_ID);
		sb.append(asTmpTable(COL_USER_ID));
		sb.append(comma());
		sb.append(COL_ACTIVATE_DATE);
		sb.append(asTmpTable(COL_ACTIVATE_DATE));
		sb.append(comma());
		sb.append(COL_ROLE_TYPE);
		sb.append(asTmpTable(COL_ROLE_TYPE));
		sb.append(comma());
		sb.append(COL_ROLE_CODE);
		sb.append(asTmpTable(COL_ROLE_CODE));
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(rightParenthesis());
		sb.append(asTmpTable(TABLE));
		sb.append(on());
		sb.append(userIdColumn);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(TABLE), getTmpColumn(COL_USER_ID)));
		sb.append(and());
		sb.append(activateDateColumn);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(TABLE), getTmpColumn(COL_ACTIVATE_DATE)));
		// SQLを取得
		return sb.toString();
	}
	
	@Override
	public String getRoleTypeColumnForJoinUser() {
		return getTmpColumn(COL_ROLE_TYPE);
	}
	
	@Override
	public String getRoleCodeColumnForJoinUser() {
		return getTmpColumn(COL_ROLE_CODE);
	}
	
}
