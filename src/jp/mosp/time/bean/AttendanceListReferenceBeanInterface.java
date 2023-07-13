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
import jp.mosp.time.dto.settings.impl.AttendanceListDto;

/**
 * 勤怠一覧参照インターフェース。
 */
public interface AttendanceListReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 個人ID及び対象日で予定一覧を取得する。<br>
	 * 予定一覧画面、予定簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 予定一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getScheduleList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び年月で予定一覧を取得する。<br>
	 * 予定一覧画面、予定簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @return 予定一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getScheduleList(String personalId, int year, int month) throws MospException;
	
	/**
	 * 個人IDと年月及び締日で予定一覧を取得する。<br>
	 * 予定一覧画面、予定簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @param cutoffDate 締日
	 * @return 予定一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getScheduleList(String personalId, int year, int month, int cutoffDate)
			throws MospException;
	
	/**
	 * 個人ID及び年月で実績一覧を取得する。<br>
	 * 出勤簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @return 実績一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getActualList(String personalId, int year, int month) throws MospException;
	
	/**
	 * 個人IDと年月及び締日で実績一覧を取得する。<br>
	 * 出勤簿作成等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @param cutoffDate 締日
	 * @return 実績一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getActualList(String personalId, int year, int month, int cutoffDate) throws MospException;
	
	/**
	 * 個人ID及び対象日で勤怠一覧を取得する。<br>
	 * 勤怠一覧画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 勤怠一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getAttendanceList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び年月で勤怠一覧を取得する。<br>
	 * 勤怠一覧画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @return 勤怠一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getAttendanceList(String personalId, int year, int month) throws MospException;
	
	/**
	 * 個人IDと年月及び締日で勤怠一覧を取得する。<br>
	 * 勤怠一覧画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @param cutoffDate 締日
	 * @return 勤怠一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getAttendanceList(String personalId, int year, int month, int cutoffDate)
			throws MospException;
	
	/**
	 * 個人ID及び対象日で週の勤怠一覧を取得する。<br>
	 * 個人ID及び対象日で有効な設定適用情報が取得できなかった場合は、空のリストを返す。<br>
	 * ポータル画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 週の勤怠一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getWeeklyAttendanceList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 個人ID及び対象日で勤怠承認一覧を取得する。<br>
	 * 勤怠承認一覧画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @return 勤怠承認一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getApprovalAttendanceList(String personalId, int year, int month) throws MospException;
	
	/**
	 * 個人ID及び対象日で勤怠一覧データを取得する。<br>
	 * 承認履歴参照画面等で利用する。<br>
	 * @param personalId 取得対象個人ID
	 * @param targetDate 取得対象日
	 * @return 勤怠一覧データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AttendanceListDto getAttendanceListDto(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象期間の勤怠勤怠一覧情報リストを取得する。<br>
	 * 勤怠未申請確認処理等で用いられる。<br>
	 * <br>
	 * @param personalId 取得対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 怠勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceListDto> getTermAttendanceList(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
}
