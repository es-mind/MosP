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
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;

/**
 * 勤怠設定参照処理インターフェース。
 */
public interface TimeSettingReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠マスタ取得。
	 * <p>
	 * 勤怠設定コードと対象日から勤怠マスタを取得。
	 * </p>
	 * @param workSettingCode 勤怠設定コード
	 * @param targetDate 対象年月日
	 * @return 勤怠マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingDtoInterface getTimeSettingInfo(String workSettingCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 勤怠設定コードから勤怠設定マスタリストを取得する。<br>
	 * @param timeSettingCode 勤怠設定コード
	 * @return 勤怠設定マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<TimeSettingDtoInterface> getTimeSettingHistory(String timeSettingCode) throws MospException;
	
	/**
	 * 勤怠設定略称を取得する。<br><br>
	 * 対象となる勤怠マスタ情報が存在しない場合は、勤怠設定コードを返す。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param targetDate 対象年月日
	 * @return 勤怠設定略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getTimeSettingAbbr(String workSettingCode, Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + 勤怠設定名称。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 勤怠マスタからレコードを取得する。<br>
	 * 勤怠設定コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate 有効日
	 * @return 勤怠マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingDtoInterface findForKey(String workSettingCode, Date activateDate) throws MospException;
	
	/**
	 * 勤怠設定コードのリストを取得する。<br>
	 * 締日コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param cutoffcode 締日コード
	 * @param activateDate 有効日
	 * @return 勤怠設定コードのリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<String> getWorkSettingCode(String cutoffcode, Date activateDate) throws MospException;
	
	/**
	 * 所定労働時間を取得する。<br>
	 * 1時間未満の端数は切り上げる。<br>
	 * 対象となる所定労働時間が存在しない場合は、法定労働時間を返す。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param targetDate 対象年月日
	 * @return 所定労働時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getGeneralWorkHour(String workSettingCode, Date targetDate) throws MospException;
	
	/**
	 * 勤怠マスタの存在チェック。<br>
	 * @param dto 勤怠マスタ
	 * @param targetDate メッセージ用の年月日
	 */
	void chkExistTimeSetting(TimeSettingDtoInterface dto, Date targetDate);
	
	/**
	 * 勤務前残業の有効無効を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 勤務前残業の有効無効。取得に失敗した場合null
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Integer getBeforeOvertimeFlag(String personalId, Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、勤怠設定の略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 対象月が見込月であるかどうか判定する。<br>
	 * @param targetMonth 対象月
	 * @param prospectsMonths 見込月
	 * @return 見込月判定（true:見込月、false:見込月ではない）
	 */
	boolean isProspectsMonth(int targetMonth, String prospectsMonths);
	
	/**
	 * 実績入力モード時の文言を返却します。
	 * @return 実績入力モード判定文字列
	 */
	String getPerformanceInputModeString();
	
	/**
	 * 勤怠設定エンティティを取得する。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param targetDate      対象日
	 * @return 勤怠設定エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingEntityInterface getEntity(String workSettingCode, Date targetDate) throws MospException;
	
	/**
	 * 勤怠設定エンティティを取得する。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate    有効日
	 * @return 勤怠設定エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingEntityInterface getEntityForKey(String workSettingCode, Date activateDate) throws MospException;
	
	/**
	 * 勤怠設定エンティティを取得する。<br>
	 * @param dto 勤怠設定情報
	 * @return 勤怠設定エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingEntityInterface getEntity(TimeSettingDtoInterface dto) throws MospException;
	
}
