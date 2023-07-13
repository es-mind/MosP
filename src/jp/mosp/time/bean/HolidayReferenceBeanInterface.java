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
import jp.mosp.time.dto.settings.HolidayDtoInterface;

/**
 * 休暇種別管理参照インターフェース。
 */
public interface HolidayReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 休暇種別マスタ取得。
	 * <p>
	 * 休暇コードと対象日と休暇区分から休暇種別マスタを取得。
	 * </p>
	 * @param holidayCode 休暇コード
	 * @param targetDate 対象日
	 * @param holidayType 休暇区分
	 * @return 休暇種別マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayDtoInterface getHolidayInfo(String holidayCode, Date targetDate, int holidayType) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 休暇コード及び休暇区分から休暇種別マスタリストを取得。
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇種別マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayDtoInterface> getHolidayHistory(String holidayCode, int holidayType) throws MospException;
	
	/**
	 * 有効日マスタ一覧取得。
	 * <p>
	 * 対象年月日から休暇種別マスタリストを取得。
	 * </p>
	 * @param targetDate 対象年月日
	 * @param holidayType 休暇区分
	 * @return 休暇種別マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayDtoInterface> getHolidayList(Date targetDate, int holidayType) throws MospException;
	
	/**
	 * プルダウン用配列(略称)取得。
	 * 休暇区分と対象年月日からプルダウン用配列を取得。
	 * @param targetDate  対象年月日
	 * @param holidayType 休暇区分
	 * @param needBlank   空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, int holidayType, boolean needBlank) throws MospException;
	
	/**
	 * プルダウン用配列取得。
	 * 休暇区分と対象年月日からプルダウン用配列を取得。
	 * @param targetDate  対象年月日
	 * @param holidayType 休暇区分
	 * @param isAbbr      略称利用フラグ(true：略称、false：名称)
	 * @param needBlank   空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, int holidayType, boolean isAbbr, boolean needBlank) throws MospException;
	
	/**
	 * エクスポート項目用配列取得。
	 * @param targetDate 対象年月日
	 * @return エクスポート項目用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getExportArray(Date targetDate) throws MospException;
	
	/**
	 * 休暇種別マスタからレコードを取得する。<br>
	 * 休暇コード、有効日、休暇区分で合致するレコードが無い場合、nullを返す。<br>
	 * @param holidayCode 休暇コード
	 * @param activateDate 有効日
	 * @param holidayType 休暇区分
	 * @return 休暇種別マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayDtoInterface findForKey(String holidayCode, Date activateDate, int holidayType) throws MospException;
	
	/**
	 * 休暇略称を取得する。<br><br>
	 * 対象となる休暇情報が存在しない場合は、休暇コードを返す。<br>
	 * @param holidayCode 休暇コード
	 * @param targetDate  対象年月日
	 * @param holidayType 休暇区分
	 * @return 休暇略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getHolidayAbbr(String holidayCode, Date targetDate, int holidayType) throws MospException;
	
	/**
	 * 休暇区分の名称を取得する。<br><br>
	 * 対象となる休暇区分が存在しない場合は、空を返す。<br>
	 * @param type1 休暇区分1
	 * @param type2 休暇区分2
	 * @return 休暇区分(名称)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getHolidayType1NameForHolidayRequest(int type1, String type2) throws MospException;
	
	/**
	 * 有効な休暇情報に時間休利用可の情報が存在するか確認する。<br>
	 * 休暇申請画面休暇区分3プルダウンで利用する。<br>
	 * @param activateDate 有効日
	 * @return 確認結果(true：時間休利用、false：時間休不可)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isTimelyHoliday(Date activateDate) throws MospException;
	
}
