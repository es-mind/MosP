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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;

/**
 * 人事兼務情報参照インターフェース
 */
public interface ConcurrentReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 対象日時点で有効な人事兼務情報リストを取得する。<br>
	 * <br>
	 * 兼務開始日が対象日より後の情報は、リストに含まれない。<br>
	 * 兼務終了日が対象日より前の情報は、リストに含まれない。<br>
	 * 兼務開始日が対象日より前で兼務終了日が設定されていない場合は、リストに含まれる。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 人事兼務情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ConcurrentDtoInterface> getConcurrentList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 人事兼務情報リストを取得する。<br>
	 * 個人IDから全ての人事兼務情報をリストで取得する。<br>
	 * @param personalId 個人ID
	 * @return 人事兼務情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ConcurrentDtoInterface> getConcurrentHistory(String personalId) throws MospException;
	
	/**
	 * 対象日時点で終了していない人事兼務情報のリストを取得する。<br>
	 * <br>
	 * 兼務終了日が対象日より前の情報は、リストに含まれない。<br>
	 * 兼務終了日が設定されていない場合は、リストに含まれる。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 人事兼務情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<ConcurrentDtoInterface> getContinuedConcurrentList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 人事兼務情報取得。
	 * <p>
	 * レコード識別IDから兼務情報を取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return 人事兼務情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ConcurrentDtoInterface findForKey(long id) throws MospException;
	
}
