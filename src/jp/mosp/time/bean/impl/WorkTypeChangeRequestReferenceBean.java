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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.dao.settings.WorkTypeChangeRequestDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 勤務形態変更申請参照クラス。
 */
public class WorkTypeChangeRequestReferenceBean extends PlatformBean
		implements WorkTypeChangeRequestReferenceBeanInterface {
	
	/**
	 * 勤務形態変更申請DAOクラス。<br>
	 */
	protected WorkTypeChangeRequestDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypeChangeRequestReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(WorkTypeChangeRequestDaoInterface.class);
	}
	
	@Override
	public WorkTypeChangeRequestDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto == null) {
			return null;
		}
		return (WorkTypeChangeRequestDtoInterface)dto;
	}
	
	@Override
	public WorkTypeChangeRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate)
			throws MospException {
		return dao.findForKeyOnWorkflow(personalId, requestDate);
	}
	
	@Override
	public WorkTypeChangeRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public List<WorkTypeChangeRequestDtoInterface> getWorkTypeChangeRequestList(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public List<WorkTypeChangeRequestDtoInterface> getWorkTypeChangeRequests(Collection<String> personalIds,
			Date requestDate) throws MospException {
		return dao.findForPersonalIds(personalIds, requestDate);
	}
	
}
