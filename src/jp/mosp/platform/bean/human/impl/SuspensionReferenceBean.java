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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.dao.human.SuspensionDaoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;

/**
 * 人事休職情報参照クラス。<br>
 */
public class SuspensionReferenceBean extends BaseBean implements SuspensionReferenceBeanInterface {
	
	/**
	 * 人事休職情報DAO。<br>
	 */
	protected SuspensionDaoInterface suspensionDao;
	
	
	/**
	 * {@link BaseBean#BaseBean()}を実行する。<br>
	 */
	public SuspensionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		suspensionDao = createDaoInstance(SuspensionDaoInterface.class);
	}
	
	@Override
	public SuspensionDtoInterface getSuspentionInfo(String personalId, Date targetDate) throws MospException {
		return suspensionDao.findForInfo(personalId, targetDate);
	}
	
	@Override
	public List<SuspensionDtoInterface> getSuspentionList(String personalId) throws MospException {
		return suspensionDao.findForHistory(personalId);
	}
	
	@Override
	public List<SuspensionDtoInterface> getContinuedSuspentionList(String personalId, Date targetDate)
			throws MospException {
		// 履歴一覧を取得
		List<SuspensionDtoInterface> list = suspensionDao.findForHistory(personalId);
		// 終了日が設定されていて対象日より前の情報をリストから除く
		for (int i = list.size() - 1; i >= 0; i--) {
			// DTO取得
			SuspensionDtoInterface dto = list.get(i);
			// 終了日が設定されていない場合
			if (dto.getEndDate() == null) {
				continue;
			}
			// 終了日が対象日より前の場合
			if (dto.getEndDate().before(targetDate)) {
				// リストから除く
				list.remove(i);
			}
		}
		return list;
	}
	
	@Override
	public boolean isSuspended(String personalId, Date targetDate) throws MospException {
		SuspensionDtoInterface dto = getSuspentionInfo(personalId, targetDate);
		if (dto != null) {
			return true;
		}
		return false;
	}
	
}
