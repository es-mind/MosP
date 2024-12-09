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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalTimeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.TotalTimeDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;

/**
 * 勤怠集計管理参照クラス。
 */
public class TotalTimeTransactionReferenceBean extends PlatformBean
		implements TotalTimeTransactionReferenceBeanInterface {
	
	/**
	 * 勤怠集計管理DAO。
	 */
	protected TotalTimeDaoInterface						dao;
	
	/**
	 * 勤怠集計管理情報群(キー：締日コード)。<br>
	 * 一度DBから取得した情報を保持しておき、再利用する。<br>
	 * <br>
	 */
	protected Map<String, Set<TotalTimeDtoInterface>>	storedMap;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeTransactionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOクラス準備
		dao = createDaoInstance(TotalTimeDaoInterface.class);
		// フィールドの初期化
		storedMap = new HashMap<String, Set<TotalTimeDtoInterface>>();
	}
	
	@Override
	public TotalTimeDtoInterface findForKey(int calculationYear, int calculationMonth, String cutoffCode)
			throws MospException {
		return dao.findForKey(calculationYear, calculationMonth, cutoffCode);
	}
	
	@Override
	public int getStoredCutoffState(int calculationYear, int calculationMonth, String cutoffCode) throws MospException {
		// 勤怠集計管理情報群から取得
		Set<TotalTimeDtoInterface> set = storedMap.get(cutoffCode);
		// 勤怠集計管理情報群から取得できなかった場合
		if (set == null) {
			// 勤怠集計管理情報セットを準備し設定
			set = new HashSet<TotalTimeDtoInterface>();
			storedMap.put(cutoffCode, set);
		}
		// 勤怠集計管理情報毎に処理
		for (TotalTimeDtoInterface dto : set) {
			// 年月が一致する場合
			if (dto.getCalculationYear() == calculationYear && dto.getCalculationMonth() == calculationMonth) {
				// 勤怠集計管理情報から締状態を取得
				return dto.getCutoffState();
			}
		}
		// DBから勤怠集計管理情報を取得
		TotalTimeDtoInterface dto = findForKey(calculationYear, calculationMonth, cutoffCode);
		// 勤怠集計管理情報が取得できた場合
		if (dto != null) {
			// 勤怠集計管理情報群に設定
			set.add(dto);
			// 勤怠集計管理情報から締状態を取得
			return dto.getCutoffState();
		}
		// 未締であると判断(勤怠集計管理情報が取得できなかった場合)
		return TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT;
	}
	
}
