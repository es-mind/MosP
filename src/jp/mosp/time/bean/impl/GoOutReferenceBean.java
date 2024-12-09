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
import jp.mosp.time.bean.GoOutReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.GoOutDaoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;

/**
 * 勤怠データ外出情報参照クラス。
 */
public class GoOutReferenceBean extends TimeBean implements GoOutReferenceBeanInterface {
	
	/**
	 *  勤怠データ外出情報マスタDAOクラス。<br>
	 */
	protected GoOutDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public GoOutReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(GoOutDaoInterface.class);
	}
	
	@Override
	public GoOutDtoInterface findForKey(String personalId, Date workDate, int timesWork, int type, int times)
			throws MospException {
		return dao.findForKey(personalId, workDate, timesWork, type, times);
	}
	
	@Override
	public List<GoOutDtoInterface> getGoOutList(String personalId, Date workDate, int timesWork, int goOutType)
			throws MospException {
		return dao.findForList(personalId, workDate, timesWork, goOutType);
	}
	
	@Override
	public List<GoOutDtoInterface> getGoOutTypeList(String personalId, Date workDate, int goOutType)
			throws MospException {
		return dao.findForHistoryList(personalId, workDate, goOutType);
	}
	
	@Override
	public List<GoOutDtoInterface> getGoOutList(String personalId, Date workDate) throws MospException {
		return dao.findForList(personalId, workDate, TIMES_WORK_DEFAULT);
	}
	
	@Override
	public List<GoOutDtoInterface> getPrivateGoOutList(String personalId, Date workDate) throws MospException {
		return getGoOutList(personalId, workDate, TIMES_WORK_DEFAULT, TimeConst.CODE_GO_OUT_PRIVATE);
	}
	
	@Override
	public List<GoOutDtoInterface> getPublicGoOutList(String personalId, Date workDate) throws MospException {
		return getGoOutList(personalId, workDate, TIMES_WORK_DEFAULT, TimeConst.CODE_GO_OUT_PUBLIC);
	}
	
}
