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
import jp.mosp.time.dto.settings.ScheduleDtoInterface;

/**
 * カレンダ管理参照インターフェース。
 */
public interface ScheduleReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * カレンダマスタ取得。
	 * <p>
	 * カレンダコードと対象日からカレンダマスタを取得。
	 * </p>
	 * @param scheduleCode カレンダコード
	 * @param targetDate 対象年月日
	 * @return カレンダマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDtoInterface getScheduleInfo(String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * カレンダコードからカレンダマスタリストを取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @return カレンダマスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ScheduleDtoInterface> getScheduleHistory(String scheduleCode) throws MospException;
	
	/**
	 * カレンダ名称を取得する。<br>
	 * 対象となるカレンダ情報が存在しない場合は、カレンダコードを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param targetDate 対象年月日
	 * @return カレンダ名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduleName(String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * カレンダ略称を取得する。<br>
	 * 対象となるカレンダ情報が存在しない場合は、カレンダコードを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param targetDate 対象年月日
	 * @return カレンダ略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getScheduleAbbr(String scheduleCode, Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + カレンダ名称。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * カレンダマスタからレコードを取得する。<br>
	 * カレンダコード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @return カレンダマスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDtoInterface findForKey(String scheduleCode, Date activateDate) throws MospException;
	
	/**
	 * カレンダ管理の存在チェック。<br>
	 * @param dto カレンダ管理
	 * @param targetDate メッセージ用の年月日
	 */
	void chkExistSchedule(ScheduleDtoInterface dto, Date targetDate);
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、所属の略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
}
