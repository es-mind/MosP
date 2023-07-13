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
package jp.mosp.time.bean;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalAllowanceDtoInterface;

/**
 * 勤怠集計手当データ参照インターフェース。<br>
 */
public interface TotalAllowanceReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠集計手当データからレコードを取得する。<br>
	 * 個人ID、年、月、手当コードで合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @param allowanceCode 手当コード
	 * @return 勤怠集計手当データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalAllowanceDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String allowanceCode) throws MospException;
	
	/**
	 * 手当回数取得。
	 * @param personalIdArray 個人ID配列
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @param allowanceCode 手当コード
	 * @return 手当回数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getTimesAllowance(String[] personalIdArray, int calculationYear, int calculationMonth, String allowanceCode)
			throws MospException;
	
}
