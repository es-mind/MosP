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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;

/**
 * 勤怠修正情報参照処理インターフェース。<br>
 */
public interface AttendanceCorrectionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 個人IDと勤務日と勤務回数から最新の勤怠データ修正情報を取得。<br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @param works     勤務回数
	 * @return 勤怠修正情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AttendanceCorrectionDtoInterface getLatestAttendanceCorrectionInfo(String personalId, Date workDate, int works)
			throws MospException;
	
	/**
	 * 履歴一覧を取得。<br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @param works      勤務回数
	 * @return 勤怠詳細修正情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceCorrectionDtoInterface> getAttendanceCorrectionHistory(String personalId, Date workDate, int works)
			throws MospException;
	
	/**
	 * 勤怠修正情報群(キー：勤務日)を取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  期間開始日
	 * @param lastDate   期間終了日
	 * @return 勤怠修正情報群(キー：勤務日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, List<AttendanceCorrectionDtoInterface>> getCorrections(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 勤怠修正情報群(キー：個人ID)を取得する。<br>
	 * 個人ID群の勤務日における情報を取得する。<br>
	 * @param personalIds 個人ID群
	 * @param workDate    勤務日
	 * @return 勤怠修正情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Set<AttendanceCorrectionDtoInterface>> getCorrections(Set<String> personalIds, Date workDate)
			throws MospException;
	
	/**
	 * 対象コードから修正箇所の値を取得する。<br>
	 * @param code       対象コード 
	 * @param value      値
	 * @param targetDate 対象年月日
	 * @return 修正箇所の値
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getCorrectionValue(String code, String value, Date targetDate) throws MospException;
	
	/**
	 * 対象コードから修正箇所名を取得する。<br>
	 * @param code 対象コード
	 * @return 修正箇所の名称
	 */
	String getHistoryAttendanceMoreName(String code);
	
	/**
	 * 対象コードから修正箇所名を取得する。<br>
	 * @param dto 対象DTO
	 * @return 修正箇所の名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getHistoryAttendanceAggregateName(TotalTimeCorrectionDtoInterface dto) throws MospException;
	
	/**
	 * @param newRest 新休憩データDTO
	 * @param oldRest 旧休憩データDTO
	 * @return 重複結果
	 */
	boolean chkRest(RestDtoInterface newRest, RestDtoInterface oldRest);
	
	/**
	 * @param newGoOut 新外出データDTO
	 * @param oldGoOut 旧外出データDTO
	 * @return 重複結果
	 */
	boolean chkGoOut(GoOutDtoInterface newGoOut, GoOutDtoInterface oldGoOut);
}
