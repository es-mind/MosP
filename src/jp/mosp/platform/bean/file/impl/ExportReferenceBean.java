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
package jp.mosp.platform.bean.file.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.ExportReferenceBeanInterface;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;

/**
 * エクスポートマスタ参照クラス。
 */
public class ExportReferenceBean extends PlatformBean implements ExportReferenceBeanInterface {
	
	/**
	 * エクスポートマスタDAO。
	 */
	private ExportDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ExportReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(ExportDaoInterface.class);
	}
	
	@Override
	public ExportDtoInterface findForKey(String exportCode) throws MospException {
		return dao.findForKey(exportCode);
	}
	
}
