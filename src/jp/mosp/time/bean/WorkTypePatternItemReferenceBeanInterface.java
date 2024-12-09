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
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypePatternItemDtoInterface;

/**
 * 勤務形態パターン項目参照インターフェース。
 */
public interface WorkTypePatternItemReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤務形態パターン項目リスト取得。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @return 勤務形態パターン項目リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkTypePatternItemDtoInterface> getWorkTypePatternItemList(String patternCode, Date activateDate)
			throws MospException;
	
	/**
	 * パターンコードと対象年月日から勤務形態プルダウン用配列を取得する。<br>
	 * 表示内容は、勤務形態略称。<br>
	 * @param patternCode パターンコード
	 * @param targetDate 対象年月日
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(String patternCode, Date targetDate) throws MospException;
	
	/**
	 * パターンコードと対象年月日から勤務形態プルダウン用配列を取得する。<br>
	 * 表示内容は、勤務形態名称。<br>
	 * @param patternCode パターンコード
	 * @param targetDate 対象年月日
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getNameSelectArray(String patternCode, Date targetDate) throws MospException;
	
	/**
	 * パターンコードと対象年月日から勤務形態プルダウン用配列を取得する。<br>
	 * 表示内容は、勤務形態略称【始業時刻～終業時刻】。<br>
	 * @param patternCode パターンコード
	 * @param targetDate 対象年月日
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getTimeSelectArray(String patternCode, Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException;
	
	/**
	 * パターンコードと対象年月日から勤務形態プルダウン用配列を取得する。<br>
	 * 表示内容は、勤務形態名称【始業時刻～終業時刻】。<br>
	 * @param patternCode パターンコード
	 * @param targetDate 対象年月日
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getNameTimeSelectArray(String patternCode, Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException;
	
}
