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

import java.util.Date;
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;

/**
 * カレンダユーティリティインターフェース。<br>
 */
public interface ScheduleUtilBeanInterface extends BaseBeanInterface {
	
	/**
	 * 接頭辞(特殊カレンダを示すカレンダコード)。<br>
	 */
	static final String CODE_PREFIX_ADDON_SCHEDULE = "@";
	
	
	/**
	 * 対象個人IDの対象日におけるカレンダ情報を取得する。<br>
	 * 取得できなかった場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return カレンダ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDtoInterface getSchedule(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDの対象日におけるカレンダ日情報を取得する。<br>
	 * カレンダ日情報が無い場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return カレンダ日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDateDtoInterface getScheduleDate(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDの対象日におけるカレンダ日情報を取得する。<br>
	 * カレンダ日情報が無い場合でも、MosP処理情報にエラーメッセージを設定しない(nullを返す)。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return カレンダ日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDateDtoInterface getScheduleDateNoMessage(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDの、対象期間における、カレンダに登録されているカレンダ日情報群を取得する。<br>
	 * カレンダ日情報リストの取得に失敗した場合でもエラーメッセージは設定せず、空リのストを返す。<br>
	 * @param personalId 個人ID
	 * @param startDate  期間開始日
	 * @param endDate    期間終了日
	 * @return カレンダ日情報群(キー：日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, ScheduleDateDtoInterface> getScheduleDates(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 対象個人IDの、対象日における、カレンダに登録されている勤務形態コードを取得する。<br>
	 * 勤務形態コードの取得に失敗した場合、エラーメッセージを設定し、空文字を返す。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDの、対象日における、カレンダに登録されている勤務形態コードを取得する。<br>
	 * 勤務形態コードの取得に失敗した場合でも、MosP処理情報にエラーメッセージを設定しない(空文字を返す)。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCodeNoMessage(String personalId, Date targetDate) throws MospException;
	
	/**
	 * カレンダに登録されている勤務形態コード群(キー：対象日)を取得する。<br>
	 * 対象日をキーとし、値には勤務形態コードを設定する。<br>
	 * カレンダ日情報から対象日の勤務形態コードが取得できない場合は、空白を設定する。<br>
	 * @param personalId 個人ID
	 * @param startDate  期間開始日
	 * @param endDate    期間終了日
	 * @return 勤務形態コード群(キー：対象日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, String> getScheduledWorkTypeCodes(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 対象個人IDの、対象日における、カレンダに登録されている勤務形態コードを取得する。<br>
	 * 振替休日、振出・休出申請、勤務形態変更申請、時差出勤申請を考慮する。<br>
	 * @param personalId  個人ID
	 * @param targetDate  対象日
	 * @param requestUtil 申請ユーティリティ
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCode(String personalId, Date targetDate, RequestUtilBeanInterface requestUtil)
			throws MospException;
	
	/**
	 * 対象個人IDの、対象日における、カレンダに登録されている勤務形態コードを取得する。<br>
	 * 振替休日、振出・休出申請、勤務形態変更申請、時差出勤申請を考慮する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param useRequest 申請要否(true：要、false：否)
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCode(String personalId, Date targetDate, boolean useRequest) throws MospException;
	
	/**
	 * 振り替えられたカレンダ日情報群(キー：勤務日)を取得する。<br>
	 * 返値のキーは引数の勤務日で、値は引数の振替日におけるカレンダ日情報。<br>
	 * @param personalId      個人ID
	 * @param substituteDates 振替日群(キー：勤務日)
	 * @return 振り替えられたカレンダ日情報群(キー：勤務日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, ScheduleDateDtoInterface> getSubstitutedSchedules(String personalId, Map<Date, Date> substituteDates)
			throws MospException;
	
	/**
	 * カレンダ名称を取得する。<br>
	 * 対象となるカレンダ情報が存在しない場合は、カレンダコードを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param targetDate   対象年月日
	 * @return カレンダ名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduleName(String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * カレンダ略称を取得する。<br>
	 * 対象となるカレンダ情報が存在しない場合は、カレンダコードを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param targetDate   対象年月日
	 * @return カレンダ略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduleAbbr(String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * カレンダ情報のプルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + カレンダ名称。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列(カレンダ情報)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * カレンダ情報のプルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、カレンダ名称。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列(カレンダ情報)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * 勤怠関連マスタ参照処理を処理間で共有するために用いる。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
