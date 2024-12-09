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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;

/**
 * 勤務形態パターンマスタDAOインターフェース
 */
public interface WorkTypePatternDaoInterface extends BaseDaoInterface {
	
	/**
	 * パターンコードと有効日から勤務形態パターンマスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @return 勤務形態パターンマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkTypePatternDtoInterface findForKey(String patternCode, Date activateDate) throws MospException;
	
	/**
	 * 勤務形態パターンマスタ取得。
	 * <p>
	 * パターンコードと有効日から勤務形態パターンマスタを取得する。
	 * </p>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @return 勤務形態パターンDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkTypePatternDtoInterface findForInfo(String patternCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * パターンコードから勤務形態パターンマスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param patternCode パターンコード
	 * @return 勤務形態パターンマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkTypePatternDtoInterface> findForHistory(String patternCode) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から勤務形態パターンマスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 勤務形態パターンマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkTypePatternDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から勤務形態パターンマスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 勤務形態パターンマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkTypePatternDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 勤務形態パターンマスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
}
