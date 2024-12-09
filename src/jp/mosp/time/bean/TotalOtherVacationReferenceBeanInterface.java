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
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;

/**
 * 勤怠集計その他休暇データ参照インターフェース。
 */
public interface TotalOtherVacationReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠集計その他休暇データからレコードを取得する。<br>
	 * 社員コード、年、月、休暇コードで合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @param holidayCode 休暇コード
	 * @return 勤怠集計その他休暇データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalOtherVacationDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String holidayCode) throws MospException;
	
	/**
	 * 勤怠集計その他休暇データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @return 勤怠集計その他休暇データDTOリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<TotalOtherVacationDtoInterface> getTotalOtherVacationList(String personalId, int calculationYear,
			int calculationMonth) throws MospException;
	
}
