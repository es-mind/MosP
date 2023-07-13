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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 人事基本情報履歴削除インターフェース。<br>
 */
public interface HistoryBasicDeleteBeanInterface extends BaseBeanInterface {
	
	/**
	 * ケース：1 履歴が1件だけの場合。<br>
	 */
	public static final int	CASE_ONLY		= 1;
	
	/**
	 * ケース：2 消去対象がリストの中で一番古い履歴の場合。<br>
	 */
	public static final int	CASE_OLDEST		= 2;
	
	/**
	 * ケース：3 履歴が1件だけの場合。<br>
	 */
	public static final int	CASE_BETWEEN	= 3;
	
	/**
	 * ケース：4 消去対象がリストの中で一番古い履歴の場合。<br>
	 */
	public static final int	CASE_NEWEST		= 4;
	
	
	/**
	 * 人事基本情報の履歴削除を行う。<br>
	 * 削除可否を確認の上、人事基本情報履歴を削除する。<br>
	 * 対象社員情報全削除フラグがtrueの場合、対象社員に関わる全ての情報を削除する。<br>
	 * @param pfmHumanId 削除対象レコード識別ID
	 * @param isAllDelete 対象社員情報全削除フラグ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(long pfmHumanId, boolean isAllDelete) throws MospException;
	
}
