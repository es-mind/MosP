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
package jp.mosp.platform.bean.system.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.PostalCodeReferenceBeanInterface;
import jp.mosp.platform.dao.system.PostalCodeDaoInterface;
import jp.mosp.platform.dto.system.PostalCodeDtoInterface;

/**
 * 郵便番号マスタ参照クラス。
 */
public class PostalCodeReferenceBean extends PlatformBean implements PostalCodeReferenceBeanInterface {
	
	/**
	 * 郵便番号マスタDAOクラス。
	 */
	protected PostalCodeDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PostalCodeReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(PostalCodeDaoInterface.class);
	}
	
	@Override
	public PostalCodeDtoInterface findForKey(String postalCode) throws MospException {
		return dao.findForKey(postalCode);
	}
	
}
