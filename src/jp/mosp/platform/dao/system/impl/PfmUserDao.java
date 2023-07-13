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
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.system.UserExtraRoleDaoInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmUserDto;

/**
 * ユーザマスタDAOクラス。
 */
public class PfmUserDao extends PlatformDao implements UserMasterDaoInterface {
	
	/**
	 * ユーザ追加ロール情報DAO(サブクエリ等取得用)。<br>
	 */
	protected UserExtraRoleDaoInterface	userExtraRoleDao;
	
	/**
	 * pfm_user(ユーザマスタ)。
	 */
	public static final String			TABLE				= "pfm_user";
	/**
	 * pfm_user_id(レコード識別ID)。
	 */
	public static final String			COL_PFM_USER_ID		= "pfm_user_id";
	/**
	 * user_id(ユーザID)。
	 */
	public static final String			COL_USER_ID			= "user_id";
	/**
	 * activate_date(有効日)。
	 */
	public static final String			COL_ACTIVATE_DATE	= "activate_date";
	/**
	 * personal_id(個人ID)。
	 */
	public static final String			COL_PERSONAL_ID		= "personal_id";
	/**
	 * role_code(ロールコード)。
	 */
	public static final String			COL_ROLE_CODE		= "role_code";
	/**
	 * inactivate_flag(無効フラグ)。
	 */
	public static final String			COL_INACTIVATE_FLAG	= "inactivate_flag";
	
	/**
	 * KEY_1 = pfm_user_id(レコード識別ID)。
	 */
	public static final String			KEY_1				= COL_PFM_USER_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmUserDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	/**
	 * サブクエリ等を取得するためのDAOクラスを設定する。<br>
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected void setDaoInstances() throws MospException {
		// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
		userExtraRoleDao = (UserExtraRoleDaoInterface)loadDao(UserExtraRoleDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmUserDto dto = new PfmUserDto();
		dto.setPfmUserId(getLong(COL_PFM_USER_ID));
		dto.setUserId(getString(COL_USER_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setRoleCode(getString(COL_ROLE_CODE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<UserMasterDtoInterface> mappingAll() throws MospException {
		List<UserMasterDtoInterface> all = new ArrayList<UserMasterDtoInterface>();
		while (next()) {
			all.add((UserMasterDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public UserMasterDtoInterface findForKey(String userId, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_USER_ID));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, userId);
			setParam(index++, activateDate);
			executeQuery();
			UserMasterDtoInterface dto = null;
			if (next()) {
				dto = (UserMasterDtoInterface)mapping();
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
	public UserMasterDtoInterface findForInfo(String userId, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_USER_ID));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, userId);
			setParam(index++, activateDate);
			executeQuery();
			UserMasterDtoInterface dto = null;
			if (rs.next()) {
				dto = (UserMasterDtoInterface)mapping();
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
	public List<UserMasterDtoInterface> findForHistory(String userId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_USER_ID));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, userId);
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
	public List<UserMasterDtoInterface> findForPersonalId(String personalId, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_USER_ID, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			// 有効日における最新の情報を抽出する条件SQLを追加
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(getOrderByColumn(COL_USER_ID));
			// ステートメント生成
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, activateDate, ps);
			// 条件のパラメータを設定(個人ID)
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
	public List<UserMasterDtoInterface> findForPersonalId(String personalId) throws MospException {
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
	public List<UserMasterDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
			HumanDaoInterface humanDao = (HumanDaoInterface)loadDao(HumanDaoInterface.class);
			// 検索条件準備
			Date activateDate = (Date)param.get("activateDate");
			String employeeCode = String.valueOf(param.get("employeeCode"));
			String userId = String.valueOf(param.get("userId"));
			String employeeName = String.valueOf(param.get("employeeName"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			// パラメータインデックス準備
			index = 1;
			StringBuffer sb = new StringBuffer();
			// SELECT部追加
			sb.append(getSelectQuery(getClass()));
			// 有効日が入力されていた場合
			if (activateDate != null) {
				sb.append(getQueryForMaxActivateDate(TABLE, COL_USER_ID, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			// ユーザーIDが入力されていた場合
			if (!userId.isEmpty()) {
				sb.append(and());
				sb.append(like(COL_USER_ID));
			}
			// 社員コードが入力されていた場合
			if (!employeeCode.isEmpty()) {
				sb.append(and());
				sb.append(COL_PERSONAL_ID);
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(humanDao.getQueryForEmployeeCode());
				sb.append(rightParenthesis());
			}
			// 社員名が入力されていた場合
			if (employeeName.isEmpty() == false) {
				sb.append(humanDao.getQueryForEmployeeName(COL_PERSONAL_ID));
			}
			// 有効/無効
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			// 有効日が入力されていた場合
			if (activateDate != null) {
				setParam(index++, activateDate);
			}
			// ユーザーIDが入力されていた場合
			if (!userId.isEmpty()) {
				setParam(index++, startWithParam(userId));
			}
			// 社員コードが入力されていた場合
			if (!employeeCode.isEmpty()) {
				Date targetDate = activateDate;
				// 有効日が検索条件として設定されていない場合、現在の日付で検索
				if (targetDate == null) {
					targetDate = DateUtility.getSystemDate();
				}
				setParam(index++, targetDate);
				setParam(index++, startWithParam(employeeCode));
			}
			// 社員名が入力されていた場合
			if (!employeeName.isEmpty()) {
				Date targetDate = activateDate;
				// 有効日が検索条件として設定されていない場合、現在の日付で検索
				if (targetDate == null) {
					targetDate = DateUtility.getSystemDate();
				}
				index = humanDao.setParamsForEmployeeName(index, containsParam(employeeName), targetDate, ps);
			}
			// 有効/無効
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
			UserMasterDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmUserId());
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
			UserMasterDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmUserId());
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
		UserMasterDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmUserId());
		setParam(index++, dto.getUserId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getRoleCode());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public String getQueryForEmployeeName() throws MospException {
		// 人事マスタDAO準備(サブクエリ取得用)
		HumanDaoInterface humanDao = (HumanDaoInterface)loadDao(HumanDaoInterface.class);
		// SQL作成
		StringBuffer sb = new StringBuffer();
		sb.append(select());
		sb.append(COL_USER_ID);
		sb.append(from(TABLE));
		sb.append(getQueryForMaxActivateDate(TABLE, COL_USER_ID, COL_ACTIVATE_DATE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(humanDao.getQueryForEmployeeName(COL_PERSONAL_ID));
		return sb.toString();
	}
	
	@Override
	public String getQueryForApprover(String targetColumn) throws MospException {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		// 承認ロール取得及び確認
		Set<String> approverRoleSet = RoleUtility.getApproverRoles(mospParams);
		if (approverRoleSet.isEmpty()) {
			return sb.toString();
		}
		// サブクエリ等を取得するためのDAOクラスを設定
		setDaoInstances();
		// SQL作成
		sb.append(and());
		sb.append(targetColumn);
		sb.append(in());
		sb.append(leftParenthesis());
		// SELECT部追加(個人ID)
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		// FROM部追加
		sb.append(from(TABLE));
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(getQueryForMaxActivateDate(TABLE, COL_USER_ID, COL_ACTIVATE_DATE));
		// 追加ロールコードを付加するSQLを追加
		sb.append(userExtraRoleDao.getQueryForJoinUser(COL_USER_ID, COL_ACTIVATE_DATE));
		// WHERE部追加
		sb.append(where());
		// 削除されていない情報のみ抽出
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(leftParenthesis());
		// 承認権限保持条件SQL追加
		for (int i = 0; i < approverRoleSet.size(); i++) {
			sb.append(equal(COL_ROLE_CODE));
			sb.append(or());
			sb.append(equal(userExtraRoleDao.getRoleCodeColumnForJoinUser()));
			if (i < approverRoleSet.size() - 1) {
				sb.append(or());
			}
		}
		sb.append(rightParenthesis());
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	@Override
	public int setParamsForApprover(int index, Date targetDate, PreparedStatement ps) throws MospException {
		// 承認ロール取得及び確認
		Set<String> approverRoleSet = RoleUtility.getApproverRoles(mospParams);
		if (approverRoleSet.isEmpty()) {
			return index;
		}
		// パラメータインデックス準備
		int idx = index;
		// 承認ロール条件パラメータ設定
		setParam(idx++, targetDate, false, ps);
		for (String approverRole : approverRoleSet) {
			setParam(idx++, approverRole, ps);
			setParam(idx++, approverRole, ps);
		}
		// インデックス返却
		return idx;
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected UserMasterDtoInterface castDto(BaseDtoInterface baseDto) {
		return (UserMasterDtoInterface)baseDto;
	}
}
