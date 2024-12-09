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
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;

/**
 * 勤務形態パターンマスタ参照インターフェース。
 */
public interface WorkTypePatternReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 履歴一覧を取得する。<br>
	 * パターンコードから勤務形態パターンマスタリストを取得する。<br>
	 * @param patternCode パターンコード
	 * @return 勤務形態パターンマスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkTypePatternDtoInterface> getWorkTypePatternHistory(String patternCode) throws MospException;
	
	/**
	 * プルダウン用配列取得。
	 * <p>
	 * 対象年月日からプルダウン用配列を取得。
	 * </p>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate) throws MospException;
	
	/**
	 * 勤務形態パターンマスタからレコードを取得する。<br>
	 * パターンコード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @return 勤務形態パターンマスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypePatternDtoInterface findForKey(String patternCode, Date activateDate) throws MospException;
	
	/**
	 * 勤務形態パターンマスタからレコードを取得する。<br>
	 * パターンコード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @return 勤務形態パターンマスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypePatternDtoInterface findForInfo(String patternCode, Date activateDate) throws MospException;
	
}
