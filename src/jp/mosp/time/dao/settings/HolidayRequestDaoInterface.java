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
package jp.mosp.time.dao.settings;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 休暇申請DAOインターフェース。<br>
 */
public interface HolidayRequestDaoInterface extends BaseDaoInterface {
	
	/**
	 * ワークフロー番号から休暇申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 休暇申請DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 個人IDと申請日から休暇申請リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 休暇申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForList(String personalId, Date requestDate) throws MospException;
	
	/**
	 * 個人IDと申請日から休暇申請情報リストを取得する。<br>
	 * @param personalIds 個人ID群
	 * @param requestDate 申請日
	 * @return 休暇申請情群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date requestDate)
			throws MospException;
	
	/**
	 * 個人IDと対象期間から休暇申請情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate) throws MospException;
	
	/**
	 * 休暇申請情報リスト(申請済：一次戻、下書、取下以外)を取得する。<br>
	 * 個人ID及び休暇取得日を指定しない場合は、それらを検索条件に含めない。<br>
	 * @param personalId      個人ID
	 * @param acquisitionDate 休暇取得日
	 * @param firstDate       対象期間初日
	 * @param lastDate        対象期間最終日
	 * @param holidayType1    休暇種別1
	 * @param holidayType2    休暇種別2
	 * @return 休暇申請情報リスト(申請済：一次戻、下書、取下以外)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForAppliedList(String personalId, Date acquisitionDate, Date firstDate,
			Date lastDate, int holidayType1, String holidayType2) throws MospException;
	
	/**
	 * 対象期間に対象休暇種別で申請している休暇申請リストを取得する。<br>
	 * 申請がない場合空のリストを返す。<br>
	 * 休暇種別削除時の確認に用いられる。<br>
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 休暇申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForRequestList(int holidayType1, String holidayType2, Date startDate,
			Date endDate) throws MospException;
	
	/**
	 * 個人IDと対象期間から休暇申請情報リストを取得する。<br>
	 * ワークフローの状態が取下であるものは除く。
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForTermOnWorkflow(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 個人IDと取得日と休暇種別1と休暇種別2と申請日から承認完了休暇申請リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param requestDate 申請日
	 * @return 承認完了休暇申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForApprovedList(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date requestDate) throws MospException;
	
	/**
	 * 承認完了休暇申請リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param holidayType1 休暇申請1
	 * @param holidayType2 休暇申請2
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate 申請終了日
	 * @return 承認完了休暇申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForApprovedList(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date requestStartDate, Date requestEndDate) throws MospException;
	
	/**
	 * 個人IDと取得日と休暇種別1と休暇種別2と申請日から休暇申請情報リストを取得する。<br>
	 * 承認状態が下書と1次戻と取下以外の休暇申請情報を取得する。<br>
	 * 
	 * @param personalId      個人ID
	 * @param acquisitionDate 取得日
	 * @param holidayType1    休暇種別1
	 * @param holidayType2    休暇種別2
	 * @param startDate       開始日
	 * @param endDate         終了日
	 * @return 休暇申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForRequestList(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から休暇申請リストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 休暇申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 休暇申請検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 個人ID、申請開始日、休暇種別1、休暇種別2、休暇範囲、時休開始時刻から休暇申請情報を取得する。
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * ワークフローの状態が取下であるものは除く。
	 * @param personalId 個人ID
	 * @param requestStartDate 申請開始日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param holidayRange 休暇範囲
	 * @param startTime 時休開始時刻
	 * @return 休暇申請情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestStartDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime) throws MospException;
	
	/**
	 * 対象休暇取得日の休暇申請リストを取得する。<br>
	 * @param personalId      個人ID
	 * @param holidayType1    休暇種別1
	 * @param holidayType2    休暇種別2
	 * @param acquisitionDate 休暇取得日
	 * @return 対象休暇取得日の休暇申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayRequestDtoInterface> findForAcquisitionList(String personalId, int holidayType1, String holidayType2,
			Date acquisitionDate) throws MospException;
	
}
