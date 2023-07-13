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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;

/**
 * 有給休暇手動付与情報参照処理インターフェース。<br>
 */
public interface PaidHolidayTransactionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 有給休暇トランザクションリスト取得。
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return 有給休暇トランザクションリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForList(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 有給休暇トランザクションリスト取得。
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 有給休暇トランザクションリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForList(String personalId, Date acquisitionDate, Date startDate,
			Date endDate) throws MospException;
	
	/**
	 * 有給休暇トランザクションリスト取得。
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param inactivateFlag 有効/無効
	 * @return 有給休暇トランザクションリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForInfoList(String personalId, Date activateDate,
			String inactivateFlag) throws MospException;
	
	/**
	 * 期間内に適用されている設定が存在するか確認する。<br>
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param personalId 対象個人ID
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 個人IDから有給休暇トランザクションリスト取得。
	 * @param personalId 個人ID
	 * @return 有給休暇トランザクションリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForHistoryList(String personalId) throws MospException;
	
	/**
	 * 個人IDと取得日から有給休暇トランザクションリスト取得する。
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @return 有給休暇トランザクションリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayTransactionDtoInterface> findForAcquisitionList(String personalId, Date acquisitionDate)
			throws MospException;
}
