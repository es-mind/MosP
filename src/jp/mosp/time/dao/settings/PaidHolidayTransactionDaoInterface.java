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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;

/**
 * 有給休暇トランザクションDAOインターフェース
 */
public interface PaidHolidayTransactionDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと有効日と取得日から有給休暇トランザクション情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return 有給休暇トランザクションDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayTransactionDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 有給休暇トランザクションリスト取得。
	 * <p>
	 * 個人IDと有効日と無効フラグから有給休暇トランザクションリストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @return 有給休暇トランザクションリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForInfoList(String personalId, Date activateDate,
			String inactivateFlag) throws MospException;
	
	/**
	 * 個人IDと有効日から有給休暇トランザクション情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return 有給休暇トランザクション情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForList(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 個人IDから有給休暇トランザクション情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @return 有給休暇トランザクション情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForHistoryList(String personalId) throws MospException;
	
	/**
	 * 個人IDと取得日と開始日と終了日から有給休暇トランザクション情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 有給休暇トランザクション情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForList(String personalId, Date acquisitionDate, Date startDate,
			Date endDate) throws MospException;
	
	/**
	 * 対象日より後に付与日を持つ有給休暇トランザクションを取得する。<br>
	 * 付与日、取得日でソートされている。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇トランザクション情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForNextGiving(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人IDが設定されている、有効日の範囲内で情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報も含まる。<br>
	 * 削除時の整合性確認等で用いる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @param personalId 個人ID
	 * @return 有給休暇データDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate)
			throws MospException;
	
}
