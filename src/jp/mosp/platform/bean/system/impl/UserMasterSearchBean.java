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
package jp.mosp.platform.bean.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterSearchBeanInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.AccountInfoDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.system.impl.AccountInfoDto;

/**
 * ユーザマスタ検索クラス。
 */
public class UserMasterSearchBean extends PlatformBean implements UserMasterSearchBeanInterface {
	
	/**
	 * ユーザマスタ検索DAO
	 */
	protected UserMasterDaoInterface				userMasterDao;
	
	/**
	 * ユーザ追加ロール情報参照処理。<br>
	 */
	protected UserExtraRoleReferenceBeanInterface	extraRoleRefer;
	
	/**
	 * ロール参照処理。<br>
	 */
	protected RoleReferenceBeanInterface			roleRefer;
	
	/**
	 * 有効日。
	 */
	private Date									activateDate;
	
	/**
	 * ユーザID。
	 */
	private String									userId;
	
	/**
	 * 社員コード。
	 */
	private String									employeeCode;
	
	/**
	 * 社員名。
	 */
	private String									employeeName;
	
	/**
	 * ロールコード。
	 */
	private String									roleCode;
	
	/**
	 * 有効無効フラグ。
	 */
	private String									inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public UserMasterSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		userMasterDao = createDaoInstance(UserMasterDaoInterface.class);
		// Beanを準備
		extraRoleRefer = createBeanInstance(UserExtraRoleReferenceBeanInterface.class);
		roleRefer = createBeanInstance(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public List<AccountInfoDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = userMasterDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("userId", userId);
		param.put("employeeCode", employeeCode);
		param.put("employeeName", employeeName);
		param.put("roleCode", roleCode);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		List<UserMasterDtoInterface> userList = userMasterDao.findForSearch(param);
		// アカウント情報リスト準備
		List<AccountInfoDtoInterface> accountList = new ArrayList<AccountInfoDtoInterface>();
		// 検索結果確認
		if (userList.size() == 0) {
			return accountList;
		}
		// 人事マスタDAO準備
		HumanDaoInterface humanDao = createDaoInstance(HumanDaoInterface.class);
		// プルダウン用配列(全て)を取得
		String[][] array = roleRefer.getAllArrays(activateDate, false, false);
		// アカウント情報リスト作成
		for (UserMasterDtoInterface userDto : userList) {
			// 社員コード、有効日から社員情報を取得する
			HumanDtoInterface humanDto = humanDao.findForInfo(userDto.getPersonalId(), activateDate);
			// ユーザIDと有効日が合致するユーザ追加ロールコード群(ロール区分のインデックス順)を取得
			Map<String, String> extraRoles = extraRoleRefer.getUserExtraRoleMap(userDto.getUserId(),
					userDto.getActivateDate());
			// アカウント情報DTO生成
			AccountInfoDtoInterface dto = new AccountInfoDto();
			dto.setActivateDate(userDto.getActivateDate());
			dto.setPersonalId(userDto.getPersonalId());
			dto.setUserId(userDto.getUserId());
			dto.setInactivateFlag(userDto.getInactivateFlag());
			dto.setPfmUserId(userDto.getPfmUserId());
			dto.setRoleCode(userDto.getRoleCode());
			dto.setExtraRoles(extraRoles);
			dto.setRoleName(getRoleName(userDto, extraRoles, array));
			dto.setEmployeeCode("");
			dto.setLastName("");
			dto.setFirstName("");
			// 人事情報設定
			if (humanDto != null) {
				dto.setEmployeeCode(humanDto.getEmployeeCode());
				dto.setLastName(humanDto.getLastName());
				dto.setFirstName(humanDto.getFirstName());
			}
			// リスト追加
			accountList.add(dto);
		}
		return accountList;
	}
	
	/**
	 * ロール名称(追加ロール含む)を取得する。<br>
	 * @param userDto    ユーザマスタ情報
	 * @param extraRoles ユーザ追加ロール情報群(キー：ロール区分、値：ロールコード)
	 * @param array      プルダウン用配列(全て)
	 * @return ロール名称(追加ロール含む)
	 */
	protected String getRoleName(UserMasterDtoInterface userDto, Map<String, String> extraRoles, String[][] array) {
		// ロール名称(追加ロール含む)を準備
		StringBuilder sb = new StringBuilder();
		// メインロールの名称をロール名称(追加ロール含む)に追加
		sb.append(getCodeName(userDto.getRoleCode(), array));
		// ユーザ追加ロール情報毎(ロール区分インデックス昇順)に処理
		for (String extraRole : extraRoles.values()) {
			// ユーザ追加ロールの名称をロール名称(追加ロール含む)に追加
			sb.append(MospConst.STR_SB_SPACE);
			sb.append(getCodeName(extraRole, array));
		}
		// ロール名称(追加ロール含む)を取得
		return sb.toString();
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
