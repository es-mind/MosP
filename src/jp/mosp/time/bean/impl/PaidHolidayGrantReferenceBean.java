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
package jp.mosp.time.bean.impl;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayGrantReferenceBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayGrantDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayGrantDtoInterface;

/**
 * 有給休暇付与情報参照処理。<br>
 */
public class PaidHolidayGrantReferenceBean extends PlatformBean implements PaidHolidayGrantReferenceBeanInterface {
	
	/**
	 * 有給休暇付与情報DAO。
	 */
	protected PaidHolidayGrantDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayGrantReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(PaidHolidayGrantDaoInterface.class);
	}
	
	@Override
	public PaidHolidayGrantDtoInterface findForKey(String personalId, Date grantDate) throws MospException {
		return dao.findForKey(personalId, grantDate);
	}
	
}
