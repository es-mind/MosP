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
package jp.mosp.platform.base;

import jp.mosp.framework.base.MospException;

/**
 * 追加業務ロジックインターフェース。<br>
 */
public interface AdditionalLogicInterface {
	
	/**
	 * 追加業務ロジック処理を行う。<br>
	 * @param objects 追加処理に必要な引数を指定
	 * @return 追加業務ロジックを処理した場合true
	 * @throws MospException 実行時例外が発生した場合
	 */
	boolean doAdditionalLogic(Object... objects) throws MospException;
	
}
