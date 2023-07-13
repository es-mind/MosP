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

/**
 * 休暇申請実行処理インターフェース。<br>
 */
public interface HolidayRequestExecuteBeanInterface extends BaseBeanInterface {
	
	/**
	 * 休暇申請を下書する。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param holidayRange     休暇範囲
	 * @param startTime        時休開始時刻
	 * @param hours            時休時間数
	 * @param requestReason    申請理由
	 * @param recordId         レコード識別ID
	 * @param approverIds      承認者個人ID配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void draft(String personalId, Date requestStartDate, Date requestEndDate, int holidayType1, String holidayType2,
			int holidayRange, Date startTime, int hours, String requestReason, long recordId, String[] approverIds)
			throws MospException;
	
	/**
	 * 休暇申請を申請する。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param holidayRange     休暇範囲
	 * @param startTime        時休開始時刻
	 * @param hours            時休時間数
	 * @param requestReason    申請理由
	 * @param recordId         レコード識別ID
	 * @param approverIds      承認者個人ID配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void apply(String personalId, Date requestStartDate, Date requestEndDate, int holidayType1, String holidayType2,
			int holidayRange, Date startTime, int hours, String requestReason, long recordId, String[] approverIds)
			throws MospException;
	
	/**
	 * 休暇申請(インポート)を申請する。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param holidayRange     休暇範囲
	 * @param startTime        時休開始時刻
	 * @param hours            時休時間数
	 * @param requestReason    申請理由
	 * @param row              行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void apply(String personalId, Date requestStartDate, Date requestEndDate, int holidayType1, String holidayType2,
			int holidayRange, Date startTime, int hours, String requestReason, int row) throws MospException;
	
	/**
	 * 休暇申請を一括更新(下書の休暇申請を一括で申請)する。<br>
	 * @param personalId 個人ID
	 * @param recordIds  レコード識別ID配列
	 * @return 半休有無(true：一括更新対象に半休が含まれている、false：半休が含まれていない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean batchUpdate(String personalId, long[] recordIds) throws MospException;
	
	/**
	 * 連続休暇の休暇対象日リストを取得する。<br>
	 * 申請期間のうち、勤務形態が休日か休日出勤である日は、連続休暇の休暇対象日でないと判断する。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param requestEndDate   申請終了日
	 * @return 連続休暇の休暇対象日リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<Date> getConsecutiveHolidayDates(String personalId, Date requestStartDate, Date requestEndDate)
			throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
