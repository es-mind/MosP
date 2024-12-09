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
package jp.mosp.platform.dao.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.GeneralDtoInterface;

/**
 * 汎用テーブルDAOインターフェース。
 *
 */
public interface GeneralDaoInterface extends BaseDaoInterface {
	
	/**
	 * 汎用区分、汎用コード及び汎用日付で情報を取得する。<br>
	 * 条件が合致しない場合nullを返す。<br>
	 * @param generalType 汎用区分
	 * @param generalCode 汎用コード
	 * @param generalDate 汎用日付
	 * @return 汎用情報
	 * @throws MospException SQL例外が発生した場合
	 */
	GeneralDtoInterface findForKey(String generalType, String generalCode, Date generalDate) throws MospException;
	
	/**
	 * 汎用情報を取得する。<br>
	 * 汎用区分、汎用コード及び汎用日付で情報を取得する<br>
	 * @param generalType 汎用区分
	 * @param generalCode 汎用コード
	 * @param generalDate 汎用日付
	 * @return 汎用情報
	 * @throws MospException SQL例外が発生した場合
	 */
	GeneralDtoInterface findForInfo(String generalType, String generalCode, Date generalDate) throws MospException;
	
	/**
	 * 汎用区分、汎用コードを指定し、<br>
	 * 対象期間に対象日付が含まれる情報を取得する。<br>
	 * @param generalType 汎用区分
	 * @param generalCode 汎用コード
	 * @param firstDate 対象期間初日
	 * @param lastDate 対象期間最終日
	 * @return 汎用情報リスト
	 * @throws MospException SQL例外が発生した場合
	 */
	List<GeneralDtoInterface> findForTerm(String generalType, String generalCode, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 勤務地マスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 個人IDと勤務日と勤務回数から勤怠情報リストを取得する。
	 * </p>
	 * @param generalType 汎用区分
	 * @param generalCode 汎用コード
	 * @param generalDate 汎用日付
	 * @return 勤怠情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<GeneralDtoInterface> findForHistory(String generalType, String generalCode, Date generalDate)
			throws MospException;
	
}
