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
package jp.mosp.time.bean;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalTimeCutoffListDtoInterface;

/**
 * 勤怠集計管理検索インターフェース。
 */
public interface TotalTimeTransactionSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から勤怠集計管理リストを取得する。<br><br>
	 * 設定された条件で、検索を行う。
	 * @return 勤怠集計管理リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<TotalTimeCutoffListDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param requestYear セットする 集計年。
	 */
	void setRequestYear(int requestYear);
	
	/**
	 * @param requestMonth セットする 集計月。
	 */
	void setRequestMonth(int requestMonth);
	
	/**
	 * @param cutoffDate セットする 締日。
	 */
	void setCutoffDate(String cutoffDate);
	
	/**
	 * @param cutoffCode セットする 締日コード。
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * @param cutoffName セットする 締日名称。
	 */
	void setCutoffName(String cutoffName);
	
	/**
	 * @param cutoffState セットする 締状態。
	 */
	void setCutoffState(String cutoffState);
	
}
