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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;

/**
 * 人事汎用バイナリ履歴情報参照インターフェース。
 */
public interface HumanBinaryHistoryReferenceBeanInterface extends BaseBeanInterface {
	
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
	HumanBinaryHistoryDtoInterface findForKey(String personalId, String humanItemType, Date activateDate)
			throws MospException;
	
	/**
	 * 人事汎用履歴バイナリ情報を取得する。<br>
	 * 個人IDと人事項目区分が合致する情報のうち、有効日が対象日以前で最新の情報を取得する。<br>
	 * 合致する情報が存在しない場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param humanItemType 人事項目区分
	 * @param targetDate 対象日
	 * @return 人事汎用履歴情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanBinaryHistoryDtoInterface findForInfo(String personalId, String humanItemType, Date targetDate)
			throws MospException;
	
	/**
	 * 人事汎用バイナリ履歴情報リストを取得する。<br>
	 * 個人ID・人事項目区分(人事汎用項目)から人事汎用履歴情報リストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param personalId 個人ID
	 * @param humanItemType 人事項目区分
	 * @return 人事汎用履歴情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanBinaryHistoryDtoInterface> findForHistory(String personalId, String humanItemType) throws MospException;
	
	/**
	 * 人事汎用バイナリ履歴情報取得
	 * @param id レコード識別ID
	 * @param isUpdate FORUPDATE 使用有無
	 * @return 人事汎用バイナリ履歴情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanBinaryHistoryDtoInterface findForKey(Long id, boolean isUpdate) throws MospException;
	
	/**
	 * 人事汎用バイナリ履歴リスト取得（有効日に合致するもの）
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return 人事汎用バイナリ履歴リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanBinaryHistoryDtoInterface> findForActivateDate(String personalId, Date activateDate) throws MospException;
	
}
