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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * カレンダ日参照処理。<br>
 */
public class ScheduleDateReferenceBean extends PlatformBean implements ScheduleDateReferenceBeanInterface {
	
	/**
	 * カレンダ日マスタDAO。
	 */
	protected ScheduleDateDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ScheduleDateReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(ScheduleDateDaoInterface.class);
	}
	
	@Override
	public ScheduleDateDtoInterface getScheduleDateInfo(String scheduleCode, Date scheduleDate) throws MospException {
		return dao.findForKey(scheduleCode, scheduleDate);
	}
	
	@Override
	public List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date startDate, Date endDate)
			throws MospException {
		return dao.findForList(scheduleCode, startDate, endDate);
	}
	
	@Override
	public List<String> findForWorkTypeCode(String scheduleCode) throws MospException {
		return dao.findForWorkTypeCode(scheduleCode);
	}
	
}
