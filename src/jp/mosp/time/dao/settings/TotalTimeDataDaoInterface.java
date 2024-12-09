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
package jp.mosp.time.dao.settings;

import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計データDAOインターフェース。<br>
 */
public interface TotalTimeDataDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと年と月から勤怠集計データを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId       個人ID
	 * @param calculationYear  年
	 * @param calculationMonth 月
	 * @return 勤怠集計データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TotalTimeDataDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException;
	
	/**
	 * 期間で勤怠集計情報リストを取得する。<br>
	 * 統計情報等で使用する。<br>
	 * @param personalId 個人ID
	 * @param startYear  開始年
	 * @param startMonth 開始月
	 * @param endYear    終了年
	 * @param endMonth   終了月
	 * @return 勤怠集計リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<TotalTimeDataDtoInterface> findForTerm(String personalId, int startYear, int startMonth, int endYear,
			int endMonth) throws MospException;
	
	/**
	 * 勤怠集計情報が存在する最小の年を取得する。<br>
	 * 勤怠集計情報が存在しない場合、0を返す。<br>
	 * @return 勤怠集計情報が存在する最小の年
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getMinYear() throws MospException;
	
}
