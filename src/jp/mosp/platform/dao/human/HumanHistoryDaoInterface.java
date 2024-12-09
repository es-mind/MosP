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
package jp.mosp.platform.dao.human;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;

/**
 * 人事汎用履歴情報DAOインターフェース。
 */
public interface HumanHistoryDaoInterface extends BaseDaoInterface {
	
	/**
	 * 人事汎用履歴情報を取得する。<br>
	 * 個人ID・人事項目区分と有効日から人事汎用履歴情報を取得する。<br>
	 * 合致する情報が存在しない場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param humanItemType 人事項目区分
	 * @param activateDate 有効日
	 * @return 人事汎用履歴情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanHistoryDtoInterface findForKey(String personalId, String humanItemType, Date activateDate)
			throws MospException;
	
	/**
	 * 人事汎用履歴情報を取得する。<br>
	 * 個人IDと人事項目区分が合致する情報のうち、有効日が対象日以前で最新の情報を取得する。<br>
	 * 合致する情報が存在しない場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param humanItemType 人事項目区分
	 * @param targetDate 対象日
	 * @return 人事汎用履歴情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanHistoryDtoInterface findForInfo(String personalId, String humanItemType, Date targetDate) throws MospException;
	
	/**
	 * 人事汎用履歴情報リストを取得する。<br>
	 * 個人ID・人事項目区分(人事汎用項目)から人事汎用履歴情報リストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param personalId 個人ID
	 * @param humanItemType 人事項目区分
	 * @return 人事汎用履歴情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanHistoryDtoInterface> findForHistory(String personalId, String humanItemType) throws MospException;
	
	/**
	 * 対象人事汎用管理項目名以外で存在する管理項目名を取得。
	 * <p>
	 * 人事汎用汎用管理項目名・個人IDから人事入社情報を取得する。
	 * </p>
	 * @param itemNames 人事汎用汎用項目名
	 * @return 人事汎用通常情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanHistoryDtoInterface> findForInfoNotIn(List<String> itemNames) throws MospException;
	
}
