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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;

/**
 * 時差出勤申請参照処理インターフェース。<br>
 */
public interface DifferenceRequestReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * プルダウン用配列取得。
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray() throws MospException;
	
	/**
	 *  時差出勤申請からレコードを取得する。<br>
	 *  個人ID、申請日で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 時差出勤申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	DifferenceRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate) throws MospException;
	
	/**
	 * 時差出勤申請データ取得。
	 * レコード識別IDから時差出勤申請データを取得。
	 * @param id レコード識別ID
	 * @return 時差出勤申請データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	DifferenceRequestDtoInterface findForKey(long id) throws MospException;
	
	/**
	 *  ワークフロー番号からレコードを取得する。<br>
	 *  ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 時差出勤申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	DifferenceRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 時差出勤申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 時差出勤申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<DifferenceRequestDtoInterface> getDifferenceRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 時差出勤申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalIds 個人ID群
	 * @param requestDate 時差出勤日
	 * @return 時差出勤申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<DifferenceRequestDtoInterface> getDifferenceRequests(Collection<String> personalIds, Date requestDate)
			throws MospException;
	
	/**
	 * 時差出勤時刻を取得する。<br>
	 * @param dto 時差申請DTO
	 * @return 時差出勤時刻
	 */
	String getDifferenceTime(DifferenceRequestDtoInterface dto);
	
	/**
	 * 時差出勤のプルダウンの取得
	 * @param type 対象コード
	 * @return 時差出勤のプルダウン
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getDifferenceSelectArray(String type) throws MospException;
	
	/**
	 * 時差出勤略称を取得する。<br>
	 * 時差出勤申請が有効の場合、時差出勤名称を取得する。<br>
	 * 時差出勤申請が無効の場合は時差出勤区分をかえす。<br>
	 * @param type 時差出勤区分
	 * @return 時差出勤区分の略称又は時差出勤区分
	 */
	String getDifferenceAbbr(String type);
	
	/**
	 * 時差出勤区分Aかどうか確認する。
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分A、false：時差出勤区分Aでない)
	 */
	boolean isDifferenceTypeA(String differenceType);
	
	/**
	 * 時差出勤区分Bかどうか確認する。
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分B、false：時差出勤区分Bでない)
	 */
	boolean isDifferenceTypeB(String differenceType);
	
	/**
	 * 時差出勤区分Cかどうか確認する。
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分C、false：時差出勤区分Cでない)
	 */
	boolean isDifferenceTypeC(String differenceType);
	
	/**
	 * 時差出勤区分Dかどうか確認する。
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分D、false：時差出勤区分Dでない)
	 */
	boolean isDifferenceTypeD(String differenceType);
	
	/**
	 * 時差出勤区分Sかどうか確認する。
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分S、false：時差出勤区分Sでない)
	 */
	boolean isDifferenceTypeS(String differenceType);
	
	/**
	 * 時差出勤区分Aかどうか確認する。
	 * @param dto 対象DTO
	 * @return 確認結果(true：時差出勤区分A、false：時差出勤区分Aでない)
	 */
	boolean isDifferenceTypeA(DifferenceRequestDtoInterface dto);
	
	/**
	 * 時差出勤区分Bかどうか確認する。
	 * @param dto 対象DTO
	 * @return 確認結果(true：時差出勤区分B、false：時差出勤区分Bでない)
	 */
	boolean isDifferenceTypeB(DifferenceRequestDtoInterface dto);
	
	/**
	 * 時差出勤区分Cかどうか確認する。
	 * @param dto 対象DTO
	 * @return 確認結果(true：時差出勤区分C、false：時差出勤区分Cでない)
	 */
	boolean isDifferenceTypeC(DifferenceRequestDtoInterface dto);
	
	/**
	 * 時差出勤区分Dかどうか確認する。
	 * @param dto 対象DTO
	 * @return 確認結果(true：時差出勤区分D、false：時差出勤区分Dでない)
	 */
	boolean isDifferenceTypeD(DifferenceRequestDtoInterface dto);
	
	/**
	 * 時差出勤区分Sかどうか確認する。
	 * @param dto 対象DTO
	 * @return 確認結果(true：時差出勤区分S、false：時差出勤区分Sでない)
	 */
	boolean isDifferenceTypeS(DifferenceRequestDtoInterface dto);
	
	/**
	 * 時差出勤労働時間を取得する。<br>
	 * @param morningOff 午前休の場合true、そうでない場合false
	 * @param afternoonOff 午後休の場合true、そうでない場合false
	 * @return 時差出勤労働時間(分単位)
	 */
	int getDifferenceWorkTime(boolean morningOff, boolean afternoonOff);
	
	/**
	 * 時差出勤休憩時間を取得する。<br>
	 * @param morningOff 午前休の場合true、そうでない場合false
	 * @param afternoonOff 午後休の場合true、そうでない場合false
	 * @return 時差出勤労働時間(分単位)
	 */
	int getDifferenceRestTime(boolean morningOff, boolean afternoonOff);
	
	/**
	 * 時差出勤区分Aの始業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤区分Aの始業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceStartTimeTypeA(Date date) throws MospException;
	
	/**
	 * 時差出勤区分Bの始業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤区分Bの始業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceStartTimeTypeB(Date date) throws MospException;
	
	/**
	 * 時差出勤区分Cの始業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤区分Cの始業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceStartTimeTypeC(Date date) throws MospException;
	
	/**
	 * 時差出勤区分Dの始業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤区分Dの始業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceStartTimeTypeD(Date date) throws MospException;
	
	/**
	 * 午前休且つ時差出勤の場合の始業時刻を取得する。<br>
	 * @param startTime 始業時刻
	 * @return 午前休且つ時差出勤の場合の始業時刻
	 */
	Date getDifferenceStartTimeMorningOff(Date startTime);
	
	/**
	 * 時差出勤区分Aの終業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤区分Aの終業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceEndTimeTypeA(Date date) throws MospException;
	
	/**
	 * 時差出勤区分Bの終業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤区分Bの終業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceEndTimeTypeB(Date date) throws MospException;
	
	/**
	 * 時差出勤区分Cの終業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤区分Cの終業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceEndTimeTypeC(Date date) throws MospException;
	
	/**
	 * 時差出勤区分Dの終業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤区分Dの終業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceEndTimeTypeD(Date date) throws MospException;
	
	/**
	 * 時差出勤区分Sの終業時刻を取得する。<br>
	 * @param startTime 始業時刻
	 * @return 時差出勤区分Sの終業時刻
	 */
	Date getDifferenceEndTimeTypeS(Date startTime);
	
	/**
	 * 午後休且つ時差出勤の場合の終業時刻を取得する。<br>
	 * @param endTime 終業時刻
	 * @return 午前休且つ時差出勤の場合の終業時刻
	 */
	Date getDifferenceEndTimeAfternoonOff(Date endTime);
	
	/**
	 * 時差出勤区分Aの休憩開始時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Aの休憩開始時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceRestStartTimeTypeA(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException;
	
	/**
	 * 時差出勤区分Bの休憩開始時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Bの休憩開始時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceRestStartTimeTypeB(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException;
	
	/**
	 * 時差出勤区分Cの休憩開始時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Cの休憩開始時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceRestStartTimeTypeC(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException;
	
	/**
	 * 時差出勤区分Dの休憩開始時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Dの休憩開始時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceRestStartTimeTypeD(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException;
	
	/**
	 * 時差出勤区分Sの休憩開始時刻を取得する。<br>
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param differenceStartTime 規定時差出勤始業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Sの休憩開始時刻
	 */
	Date getDifferenceRestStartTimeTypeS(Date startTime, Date endTime, Date differenceStartTime, boolean isHalfHoliday);
	
	/**
	 * 時差出勤区分Aの休憩終了時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Aの休憩終了時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceRestEndTimeTypeA(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException;
	
	/**
	 * 時差出勤区分Bの休憩終了時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Bの休憩終了時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceRestEndTimeTypeB(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException;
	
	/**
	 * 時差出勤区分Cの休憩終了時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Cの休憩終了時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceRestEndTimeTypeC(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException;
	
	/**
	 * 時差出勤区分Dの休憩終了時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Dの休憩終了時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getDifferenceRestEndTimeTypeD(Date date, Date startTime, Date endTime, boolean isHalfHoliday)
			throws MospException;
	
	/**
	 * 時差出勤区分Sの休憩終了時刻を取得する。<br>
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param differenceStartTime 規定時差出勤始業時刻
	 * @param isHalfHoliday 半休の場合true、そうでない場合false
	 * @return 時差出勤区分Sの休憩終了時刻
	 */
	Date getDifferenceRestEndTimeTypeS(Date startTime, Date endTime, Date differenceStartTime, boolean isHalfHoliday);
	
	/**
	 * 基本情報チェック
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
}
