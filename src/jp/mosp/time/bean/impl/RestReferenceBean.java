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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.RestReferenceBeanInterface;
import jp.mosp.time.dao.settings.RestDaoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 勤怠データ休憩情報参照クラス。
 */
public class RestReferenceBean extends TimeBean implements RestReferenceBeanInterface {
	
	/**
	 *  勤怠データ休憩情報マスタDAOクラス。<br>
	 */
	protected RestDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public RestReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(RestDaoInterface.class);
	}
	
	@Override
	public RestDtoInterface findForKey(String personalId, Date workDate, int timesWork, int times)
			throws MospException {
		return dao.findForKey(personalId, workDate, timesWork, times);
	}
	
	@Override
	public List<RestDtoInterface> getRestList(String personalId, Date workDate, int works) throws MospException {
		return dao.findForList(personalId, workDate, works);
	}
	
	@Override
	public List<RestDtoInterface> getRestList(String personalId, Date workDate) throws MospException {
		return dao.findForList(personalId, workDate, TIMES_WORK_DEFAULT);
	}
	
}
