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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;

/**
 * ストック休暇手動付与情報参照処理インターフェース。<br>
 */
public interface StockHolidayTransactionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * ストック休暇トランザクション取得。
	 * 社員コードと対象日と取得日からストック休暇トランザクションを取得。
	 * @param personalId 社員コード
	 * @param targetDate 対象年月日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇トランザクション
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	StockHolidayTransactionDtoInterface getStockHolidayTransactionInfo(String personalId, Date targetDate,
			Date acquisitionDate) throws MospException;
	
	/**
	 * ストック休暇トランザクションからレコードを取得する。<br>
	 * 社員コード、有効日、取得日で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 社員コード
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇トランザクション
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	StockHolidayTransactionDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * ストック休暇手動付与情報リストを取得する。
	 * @param personalId      個人ID
	 * @param acquisitionDate 取得日
	 * @param startDate       開始日
	 * @param endDate         終了日
	 * @return ストック休暇ト手動付与情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<StockHolidayTransactionDtoInterface> findForList(String personalId, Date acquisitionDate, Date startDate,
			Date endDate) throws MospException;
	
}
