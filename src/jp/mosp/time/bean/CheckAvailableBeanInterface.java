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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 機能利用可否チェックインターフェース。<br>
 */
public interface CheckAvailableBeanInterface extends BaseBeanInterface {
	/**
	 * 利用可否判定を行う。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param types 対象機能
	 * @return 利用可否判定(true:利用可能、false:利用不可)
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	boolean check(String personalId, Date targetDate, String... types) throws MospException;
}
