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
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;

/**
 * カレンダユーティリティ追加処理インターフェース。<br>
 */
public interface ScheduleUtilAddonBeanInterface extends BaseBeanInterface {
	
	/**
	 * {@link ScheduleUtilBeanInterface#getSchedule(String, Date) }
	 * で実行する追加処理。<br>
	 * @param dto        追加処理前に決定したカレンダ情報
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return カレンダ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDtoInterface getSchedule(ScheduleDtoInterface dto, String personalId, Date targetDate) throws MospException;
	
	/**
	 * {@link ScheduleUtilBeanInterface#getScheduleDate(String, Date) }及び
	 * {@link ScheduleUtilBeanInterface#getScheduleDateNoMessage(String, Date) }
	 * で実行する追加処理。<br>
	 * @param dto                  追加処理前に決定したカレンダ日情報
	 * @param personalId           個人ID
	 * @param targetDate           対象日
	 * @param isErrorMessageNeeded エラーメッセージ要否(true：エラーメッセージ要、false：不要)
	 * @return カレンダ日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDateDtoInterface getScheduleDate(ScheduleDateDtoInterface dto, String personalId, Date targetDate,
			boolean isErrorMessageNeeded) throws MospException;
	
	/**
	 * {@link ScheduleUtilBeanInterface#getScheduleDates(String, Date, Date) }
	 * で実行する追加処理。<br>
	 * @param dtos       追加処理前に決定したカレンダ日情報群(キー：日)
	 * @param personalId 個人ID
	 * @param startDate  期間開始日
	 * @param endDate    期間終了日
	 * @return カレンダ日情報報群(キー：日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, ScheduleDateDtoInterface> getScheduleDates(Map<Date, ScheduleDateDtoInterface> dtos, String personalId,
			Date startDate, Date endDate) throws MospException;
	
	/**
	 * {@link ScheduleUtilBeanInterface#getScheduledWorkTypeCode(String, Date) }
	 * で実行する追加処理。<br>
	 * @param workTypeCode         追加処理前に決定した勤務形態コード
	 * @param personalId           個人ID
	 * @param targetDate           対象日
	 * @param isErrorMessageNeeded エラーメッセージ要否(true：エラーメッセージ要、false：不要)
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCode(String workTypeCode, String personalId, Date targetDate,
			boolean isErrorMessageNeeded) throws MospException;
	
	/**
	 * {@link ScheduleUtilBeanInterface#getScheduledWorkTypeCode(String, Date, RequestUtilBeanInterface) }
	 * で実行する追加処理。<br>
	 * @param workTypeCode 追加処理前に決定した勤務形態コード
	 * @param personalId   個人ID
	 * @param targetDate   対象日
	 * @param requestUtil  申請ユーティリティ
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduledWorkTypeCode(String workTypeCode, String personalId, Date targetDate,
			RequestUtilBeanInterface requestUtil) throws MospException;
	
	/**
	 * {@link ScheduleUtilBeanInterface#getScheduleName(String, Date) }
	 * で実行する追加処理。<br>
	 * @param name         追加処理前に決定した名称
	 * @param scheduleCode カレンダコード
	 * @param targetDate   対象年月日
	 * @return カレンダ名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduleName(String name, String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * {@link ScheduleUtilBeanInterface#getScheduleAbbr(String, Date) }
	 * で実行する追加処理。<br>
	 * @param abbr         追加処理前に決定した略称
	 * @param scheduleCode カレンダコード
	 * @param targetDate   対象年月日
	 * @return カレンダ略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduleAbbr(String abbr, String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * {@link ScheduleUtilBeanInterface#getCodedSelectArray(Date, boolean) }
	 * で実行する追加処理。<br>
	 * @param selectArray 追加処理前に決定したプルダウン用配列
	 * @param targetDate  対象年月日
	 * @param needBlank   空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列(カレンダ情報)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(String[][] selectArray, Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * {@link ScheduleUtilBeanInterface#getSelectArray(Date, boolean) }
	 * で実行する追加処理。<br>
	 * @param selectArray 追加処理前に決定したプルダウン用配列
	 * @param targetDate  対象年月日
	 * @param needBlank   空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列(カレンダ情報)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(String[][] selectArray, Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * 処理間で情報を共有するために用いる。<br>
	 * これらを用いない場合は、当メソッドで処理を実装する必要は無い。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
