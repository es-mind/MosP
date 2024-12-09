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

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.GeneralReferenceBeanInterface;
import jp.mosp.platform.dao.system.GeneralDaoInterface;
import jp.mosp.platform.dto.system.GeneralDtoInterface;

/**
 * 汎用マスタ参照クラス。
 */
public class GeneralReferenceBean extends PlatformBean implements GeneralReferenceBeanInterface {
	
	/**
	 * 汎用データDAOクラス。<br>
	 */
	protected GeneralDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public GeneralReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(GeneralDaoInterface.class);
	}
	
	@Override
	public List<GeneralDtoInterface> findForHistory(String generalType, String generalCode, Date generalDate)
			throws MospException {
		return dao.findForHistory(generalType, generalCode, generalDate);
	}
	
	@Override
	public GeneralDtoInterface findForKey(String generalType, String generalCode, Date generalDate)
			throws MospException {
		return dao.findForKey(generalType, generalCode, generalDate);
	}
	
	@Override
	public List<GeneralDtoInterface> findForTerm(String generalType, String generalCode, Date firstDate, Date lastDate)
			throws MospException {
		return dao.findForTerm(generalType, generalCode, firstDate, lastDate);
	}
	
	@Override
	public GeneralDtoInterface findForInfo(String generalType, String generalCode, Date generalDate)
			throws MospException {
		return dao.findForInfo(generalType, generalCode, generalDate);
	}
	
}
