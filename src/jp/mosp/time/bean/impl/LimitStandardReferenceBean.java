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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.LimitStandardReferenceBeanInterface;
import jp.mosp.time.dao.settings.LimitStandardDaoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;

/**
 * 限度基準参照処理。<br>
 */
public class LimitStandardReferenceBean extends PlatformBean implements LimitStandardReferenceBeanInterface {
	
	/**
	 * 限度基準管理DAO
	 */
	protected LimitStandardDaoInterface dao;
	
	
	/**
	 * コンストラクタ。
	 */
	public LimitStandardReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(LimitStandardDaoInterface.class);
	}
	
	@Override
	public LimitStandardDtoInterface findForKey(String workSettingCode, Date activateDate, String term)
			throws MospException {
		return dao.findForKey(workSettingCode, activateDate, term);
	}
	
	@Override
	public Set<LimitStandardDtoInterface> getLimitStandards(String workSettingCode) throws MospException {
		return new LinkedHashSet<LimitStandardDtoInterface>(dao.findForCode(workSettingCode));
	}
	
}
