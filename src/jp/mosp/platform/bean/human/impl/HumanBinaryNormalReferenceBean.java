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
package jp.mosp.platform.bean.human.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.HumanBinaryNormalReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanBinaryNormalDaoInterface;
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;

/**
 * 人事汎用通常情報参照クラス。
 */
public class HumanBinaryNormalReferenceBean extends HumanGeneralBean
		implements HumanBinaryNormalReferenceBeanInterface {
	
	/**
	 * 人事通常情報DAO。
	 */
	protected HumanBinaryNormalDaoInterface dao;
	
	
	/**
	 * {@link HumanGeneralBean#HumanGeneralBean()}を実行する。<br>
	 */
	public HumanBinaryNormalReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanBinaryNormalDaoInterface.class);
	}
	
	@Override
	public void setCommounInfo(String division, String viewKey) {
		// 人事汎用項目区分設定情報取得
		conventionProperty = mospParams.getProperties().getConventionProperties()
			.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
	}
	
	@Override
	public List<HumanBinaryNormalDtoInterface> findForList(String personalId) throws MospException {
		return dao.findForList(personalId);
	}
	
	@Override
	public HumanBinaryNormalDtoInterface findForInfo(String personalId, String itemName) throws MospException {
		return dao.findForInfo(personalId, itemName);
	}
	
	@Override
	public HumanBinaryNormalDtoInterface findForKey(Long pfaHumanBinaryNormalId, boolean isUpdate)
			throws MospException {
		return (HumanBinaryNormalDtoInterface)dao.findForKey(pfaHumanBinaryNormalId, isUpdate);
	}
}
