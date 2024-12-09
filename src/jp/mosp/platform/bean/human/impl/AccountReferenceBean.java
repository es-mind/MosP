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
package jp.mosp.platform.bean.human.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.AccountReferenceBeanInterface;
import jp.mosp.platform.dao.human.AccountDaoInterface;
import jp.mosp.platform.dto.human.AccountDtoInterface;

/**
 * 口座情報参照クラス。<br>
 */
public class AccountReferenceBean extends PlatformBean implements AccountReferenceBeanInterface {
	
	/**
	 * 口座区分(給与サブ振込口座)。<br>
	 * この場合、保持者IDは個人IDとなる。<br>
	 */
	public static final String		TYPE_PAY_SUB	= "1";
	
	/**
	 * 口座区分(給与メイン振込口座)。<br>
	 * この場合、保持者IDは個人IDとなる。<br>
	 */
	public static final String		TYPE_PAY_MAIN	= "2";
	
	/**
	 * 口座情報DAOクラス。<br>
	 */
	protected AccountDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AccountReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(AccountDaoInterface.class);
	}
	
	@Override
	public List<AccountDtoInterface> getPayMainAccountList(String personalId) throws MospException {
		return dao.findForHolder(personalId, TYPE_PAY_MAIN);
	}
	
	@Override
	public List<AccountDtoInterface> getPaySubAccountList(String personalId) throws MospException {
		return dao.findForHolder(personalId, TYPE_PAY_SUB);
	}
	
	@Override
	public AccountDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public boolean isExistAccountInfo(String personalId) throws MospException {
		// 銀行申請情報を全て取得
		List<AccountDtoInterface> list = dao.findForAll(personalId);
		// 情報がある場合
		if (!list.isEmpty()) {
			return true;
		}
		return false;
	}
	
}
