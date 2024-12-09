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
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;

/**
 * 勤怠集計欠勤データ参照インターフェース。
 */
public interface TotalAbsenceReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠集計欠勤データからレコードを取得する。<br>
	 * 個人ID、年、月、欠勤コードで合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @param absenceCode 欠勤コード
	 * @return 勤怠集計欠勤データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalAbsenceDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String absenceCode) throws MospException;
	
	/**
	 * 勤怠集計欠勤データリストを取得する。<br>
	 * @param personalId 個人Id
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @return 勤怠集計欠勤データDTOリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<TotalAbsenceDtoInterface> getTotalAbsenceList(String personalId, int calculationYear, int calculationMonth)
			throws MospException;
	
}
