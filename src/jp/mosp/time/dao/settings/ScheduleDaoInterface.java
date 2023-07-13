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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;

/**
 * カレンダマスタDAOインターフェース
 */
public interface ScheduleDaoInterface extends BaseDaoInterface {
	
	/**
	 * カレンダコードと有効日からカレンダ情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @return カレンダマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ScheduleDtoInterface findForKey(String scheduleCode, Date activateDate) throws MospException;
	
	/**
	 * カレンダマスタ取得。
	 * <p>
	 * カレンダコードと有効日からカレンダマスタを取得する。
	 * </p>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @return カレンダ管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ScheduleDtoInterface findForInfo(String scheduleCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * カレンダコードからカレンダマスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param scheduleCode カレンダコード
	 * @return カレンダマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDtoInterface> findForHistory(String scheduleCode) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日からカレンダマスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return カレンダマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件からカレンダマスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return カレンダマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return カレンダ管理検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 条件による検索のための文字列。
	 * <p>
	 * 最大有効日レコードのクエリを取得する。
	 * </p>
	 * @return カレンダコードに紐づく有効日が最大であるレコード取得クエリ
	 */
	StringBuffer getQueryForMaxActivateDate();
	
}
