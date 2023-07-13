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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.HumanBinaryArrayReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanBinaryArrayDaoInterface;
import jp.mosp.platform.dto.human.HumanBinaryArrayDtoInterface;

/**
 * 人事汎用バイナリ一覧情報参照クラス。
 */
public class HumanBinaryArrayReferenceBean extends HumanGeneralBean implements HumanBinaryArrayReferenceBeanInterface {
	
	/**
	 * 人事入社情報DAO。
	 */
	protected HumanBinaryArrayDaoInterface dao;
	
	
	/**
	 * {@link HumanGeneralBean#HumanGeneralBean()}を実行する。<br>
	 */
	public HumanBinaryArrayReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanBinaryArrayDaoInterface.class);
	}
	
	@Override
	public List<HumanBinaryArrayDtoInterface> findForItemType(String personalId, String humanItemType)
			throws MospException {
		return dao.findForItemType(personalId, humanItemType);
	}
	
	@Override
	public HumanBinaryArrayDtoInterface findForKey(String personalId, String humanItemType, int rowId)
			throws MospException {
		return dao.findForKey(personalId, humanItemType, rowId);
	}
	
	@Override
	public String[] getArrayActiveDate(LinkedHashMap<String, Map<String, String>> rowIdArrayMapInfo) {
		// 有効日リスト取得
		List<String> rowIdList = new ArrayList<String>(rowIdArrayMapInfo.keySet());
		String[] arrayRowId = new String[rowIdList.size()];
		if (rowIdList.isEmpty()) {
			return arrayRowId;
		}
		// ソート
		Collections.sort(rowIdList);
		for (int i = 0; i < rowIdList.size(); i++) {
			arrayRowId[i] = rowIdList.get(i);
		}
		
		return arrayRowId;
	}
	
	@Override
	public HumanBinaryArrayDtoInterface findForKey(Long id, boolean isUpdate) throws MospException {
		return (HumanBinaryArrayDtoInterface)dao.findForKey(id, isUpdate);
	}
	
}
