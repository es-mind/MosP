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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザマスタ参照クラス。
 */
public class UserMasterReferenceBean extends PlatformBean implements UserMasterReferenceBeanInterface {
	
	/**
	 * ユーザマスタDAO。<br>
	 */
	protected UserMasterDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserMasterReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(UserMasterDaoInterface.class);
	}
	
	@Override
	public UserMasterDtoInterface findForKey(String userId, Date activateDate) throws MospException {
		return dao.findForKey(userId, activateDate);
	}
	
	@Override
	public UserMasterDtoInterface getUserInfo(String userId, Date targetDate) throws MospException {
		return dao.findForInfo(userId, targetDate);
	}
	
	@Override
	public List<UserMasterDtoInterface> getUserHistory(String userId) throws MospException {
		return dao.findForHistory(userId);
	}
	
	@Override
	public List<UserMasterDtoInterface> getUserHistoryForPersonalId(String personalId) throws MospException {
		return dao.findForPersonalId(personalId);
	}
	
	@Override
	public List<UserMasterDtoInterface> getUserListForPersonalId(String personalId, Date targetDate)
			throws MospException {
		return dao.findForPersonalId(personalId, targetDate);
	}
	
	@Override
	public UserMasterDtoInterface findForkey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (UserMasterDtoInterface)dto;
		}
		return null;
	}
	
}
