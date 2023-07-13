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
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 有給休暇情報参照処理インターフェース。
 */
public interface PaidHolidayInfoReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 表示用有給休暇情報を取得する。<br>
	 * 個別有給休暇確認画面及び有給休暇確認画面で用いる。<br>
	 * <br>
	 * 支給日数(時間数)と廃棄日数(時間数)と利用日数(時間数)は、対象日までの累計として取得する。<br>
	 * 休暇申請は、申請済承認状況群(一次戻と下書と取下以外)である有給休暇申請を対象とする。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 表示用有給休暇情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getPaidHolidayInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 表示用有給休暇情報を取得する。<br>
	 * 個別有給休暇確認画面で用いる。<br>
	 * <br>
	 * 支給日数(時間数)と廃棄日数(時間数)と利用日数(時間数)は、期間の累計として取得する。<br>
	 * 休暇申請は、申請済承認状況群(一次戻と下書と取下以外)である有給休暇申請を対象とする。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 表示用有給休暇情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getPaidHolidayReferenceInfo(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 表示用有給休暇リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 表示用有給休暇リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<Map<String, Object>> getPaidHolidayDataListForView(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇次回付与予定を取得する。<br><br>
	 * @param personalId 個人ID
	 * @return 有給休暇次回付与予定
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Object> getNextGivingInfo(String personalId) throws MospException;
	
	/**
	 * 有給休暇次回付与予定日を取得する。<br><br>
	 * @param personalId 個人ID
	 * @return 有給休暇次回付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getNextManualGivingDate(String personalId) throws MospException;
	
	/**
	 * 有給休暇次回付与予定日数を取得する。<br><br>
	 * @param personalId 個人ID
	 * @return 有給休暇次回付与予定日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getNextManualGivingDaysAndHours(String personalId) throws MospException;
	
	/**
	 * 有給休暇時間休の時間単位限度を取得する。
	 * 時間休申請が承認済みの場合計算する。
	 * @param personalId        個人ID
	 * @param targetDate        対象日
	 * @param isCompleted       承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @param holidayRequestDto 休暇申請DTO
	 * @return 時間単位残限度配列：○日、○時間、時間限度
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int[] getHolidayTimeUnitLimit(String personalId, Date targetDate, boolean isCompleted,
			HolidayRequestDtoInterface holidayRequestDto) throws MospException;
	
}
