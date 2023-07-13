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
/**
 * 
 */
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;

/**
 * ストック休暇トランザクションDAOインターフェース。<br>
 */
public interface StockHolidayTransactionDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと有効日と取得日からストック休暇トランザクションを取得する。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇トランザクション
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StockHolidayTransactionDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * ストック休暇トランザクション取得。
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇トランザクションDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StockHolidayTransactionDtoInterface findForInfo(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 個人IDと取得日と開始日と終了日からストック休暇トランザクション情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return ストック休暇トランザクション情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<StockHolidayTransactionDtoInterface> findForList(String personalId, Date acquisitionDate, Date startDate,
			Date endDate) throws MospException;
	
	/**
	 * 個人IDと有効日からストック休暇トランザクション情報を取得する。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @return ストック休暇トランザクション情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StockHolidayTransactionDtoInterface findForKey(String personalId, Date activateDate, String inactivateFlag)
			throws MospException;
	
}
