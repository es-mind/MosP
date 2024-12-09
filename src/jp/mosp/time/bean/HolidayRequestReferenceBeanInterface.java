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

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.entity.HolidayRequestEntityInterface;

/**
 * 休暇申請参照処理インターフェース。<br>
 */
public interface HolidayRequestReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 休暇申請リスト取得。
	 * <p>
	 * 個人IDと申請日から休暇申請リストを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 休暇申請リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestList(String personalId, Date requestDate) throws MospException;
	
	/**
	 * 休暇申請リスト取得。<br>
	 * 個人IDと申請日から休暇申請リストを取得。<br>
	 * ワークフローの状態が取下であるものは除く。
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 休暇申請リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestListOnWorkflow(String personalId, Date requestDate)
			throws MospException;
	
	/**
	 * 休暇申請からレコードを取得する。<br>
	 * 個人ID、申請日、休暇種別1、休暇種別2、休暇範囲、時休開始時刻で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param requestStartDate 申請開始日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param holidayRange 休暇範囲
	 * @param startTime 時休開始時刻
	 * @return 休暇申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestStartDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime) throws MospException;
	
	/**
	 * 休暇申請からレコードを取得する。<br>
	 * ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 休暇申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 休暇申請データ取得。
	 * <p>
	 * レコード識別IDから休暇申請データを取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return 休暇申請データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 休暇申請情報群(キー：個人ID)を取得する。<br>
	 * 対象個人IDの申請日における申請を取得する。<br>
	 * @param personalIds 個人ID群
	 * @param requestDate 申請日
	 * @return 休暇申請情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Set<HolidayRequestDtoInterface>> getHolidayRequests(Collection<String> personalIds, Date requestDate)
			throws MospException;
	
	/**
	 * 休暇申請情報群(キー：申請日)を取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * 期間で申請している場合は、期間中の全ての日(キー)に対して休暇申請情報が設定される。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請情報群(キー：申請日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, List<HolidayRequestDtoInterface>> getHolidayRequests(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * ワークフローの状態が取下であるものは除く。
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestListOnWorkflow(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 個人IDと取得日から有給休暇時間承認状態別休数回数をマップで取得する。<br>
	 * @param personalId 個人ID
	 * @param acquisitionDate 取得日
	 * @param holidayRequestDto 休暇申請DTO
	 * @return 休暇申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<String, Integer> getTimeHolidayStatusTimesMap(String personalId, Date acquisitionDate,
			HolidayRequestDtoInterface holidayRequestDto) throws MospException;
	
	/**
	 * 休暇取得日数及び時間数を取得する。<br>
	 * 承認状態が下書と1次戻と取下以外の休暇申請情報を除いて計算する。<br>
	 * @param personalId       個人ID
	 * @param acquisitionDate  取得日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @return 休暇取得日数及び時間数(キー：休暇取得日数、値：休暇取得時間数)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getRequestDayHour(String personalId, Date acquisitionDate, int holidayType1,
			String holidayType2, Date requestStartDate, Date requestEndDate) throws MospException;
	
	/**
	 * 休暇の利用日数及び利用時間数を取得する。<br>
	 * 取下以外の休暇申請(下書を含む)を対象とする。<br>
	 * <br>
	 * @param personalId       個人ID
	 * @param firstDate        期間初日
	 * @param lastDate         期間最終日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param acquisitionDates 休暇取得日群
	 * @return 休暇の利用日数(キー)及び利用時間数(値)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SimpleEntry<Double, Integer> getHolidayUses(String personalId, Date firstDate, Date lastDate, int holidayType1,
			String holidayType2, Collection<Date> acquisitionDates) throws MospException;
	
	/**
	 * 有給休暇申請理由必須設定を確認する。<br>
	 * @return 確認結果(true:必須、false:任意)
	 */
	boolean isPaidHolidayReasonRequired();
	
	/**
	 * 基本情報チェック。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 休暇申請情報(有給休暇)(申請済：一次戻、下書、取下以外)リストを取得する。<br>
	 * @param personalId      個人ID
	 * @param acquisitionDate 有給休暇取得日
	 * @param startDate       期間開始日
	 * @param endDate         期間終了日
	 * @return 休暇申請情報(有給休暇)(申請済：一次戻、下書、取下以外)リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getAppliedPaidHolidayRequests(String personalId, Date acquisitionDate,
			Date startDate, Date endDate) throws MospException;
	
	/**
	 * 休暇申請情報(有給休暇)(申請済：一次戻、下書、取下以外)リストを取得する。<br>
	 * @param startDate  期間開始日
	 * @param endDate    期間終了日
	 * @return 休暇申請情報(有給休暇)(申請済：一次戻、下書、取下以外)リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getAppliedPaidHolidayRequests(Date startDate, Date endDate) throws MospException;
	
	/**
	 * 休暇申請情報(承認済)リストを取得する。<br>
	 * @param personalId   個人ID
	 * @param startDate    期間開始日
	 * @param endDate      期間終了日
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @return 休暇申請情報(有給休暇)リスト(一次戻、下書、取下以外)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getCompletedRequests(String personalId, Date startDate, Date endDate,
			int holidayType1, String holidayType2) throws MospException;
	
	/**
	 * 休暇取得日で休暇申請情報群を取得する。<br>
	 * @param personalId      個人ID
	 * @param holidayType1    休暇種別1
	 * @param holidayType2    休暇種別2
	 * @param acquisitionDate 休暇取得日
	 * @param statuses        承認状況群(対象となる休暇申請の承認状況)
	 * @return 休暇申請情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<HolidayRequestDtoInterface> getRequestsForAcquisitionDate(String personalId, int holidayType1,
			String holidayType2, Date acquisitionDate, Set<String> statuses) throws MospException;
	
	/**
	 * 休暇取得日で休暇申請情報群を取得する。<br>
	 * 申請開始日が対象日以前の休暇申請情報を対象とする。<br>
	 * @param personalId      個人ID
	 * @param holidayType1    休暇種別1
	 * @param holidayType2    休暇種別2
	 * @param acquisitionDate 休暇付与日
	 * @param statuses        承認状況群(対象となる休暇申請の承認状況)
	 * @param targetDate      対象日(申請開始日が対象日以前の休暇申請情報を対象とする)
	 * @return 休暇申請情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<HolidayRequestDtoInterface> getRequestsForAcquisitionDate(String personalId, int holidayType1,
			String holidayType2, Date acquisitionDate, Set<String> statuses, Date targetDate) throws MospException;
	
	/**
	 * 休暇申請エンティティを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 休暇申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestEntityInterface getHolidayRequestEntity(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
}
