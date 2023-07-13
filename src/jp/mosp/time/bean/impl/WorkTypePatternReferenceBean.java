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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypePatternReferenceBeanInterface;
import jp.mosp.time.dao.settings.WorkTypePatternDaoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;

/**
 * 勤務形態パターンマスタ参照クラス。
 */
public class WorkTypePatternReferenceBean extends PlatformBean implements WorkTypePatternReferenceBeanInterface {
	
	/**
	 * 勤務形態パターン情報DAO。<br>
	 */
	protected WorkTypePatternDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypePatternReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(WorkTypePatternDaoInterface.class);
	}
	
	@Override
	public List<WorkTypePatternDtoInterface> getWorkTypePatternHistory(String patternCode) throws MospException {
		return dao.findForHistory(patternCode);
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate) throws MospException {
		// 一覧取得
		List<WorkTypePatternDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		String[][] array = prepareSelectArray(list.size(), true);
		for (int i = 0; i < list.size(); i++) {
			WorkTypePatternDtoInterface dto = list.get(i);
			array[i + 1][0] = dto.getPatternCode();
			array[i + 1][1] = dto.getPatternName();
		}
		return array;
	}
	
	@Override
	public WorkTypePatternDtoInterface findForKey(String patternCode, Date activateDate) throws MospException {
		return dao.findForKey(patternCode, activateDate);
	}
	
	@Override
	public WorkTypePatternDtoInterface findForInfo(String patternCode, Date activateDate) throws MospException {
		return dao.findForInfo(patternCode, activateDate);
	}
	
}
