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

import jp.mosp.framework.base.MospException;

/**
 * 人事情報一覧画面追加情報用Beanインターフェース。<br>
 * <br>
 * 当インタフェースを実装したBean名を設定ファイルに追記(HumanInfoExtraBeans)
 * することで、人事情報一覧画面の処理時に実行されるようになる。<br>
 * <br>
 * 人事情報一覧の入社情報と兼務情報の間に、設定した順番で表示される。<br>
 */
public interface HumanInfoExtraBeanInterface {
	
	/**
	 * 人事情報一覧画面追加情報を設定する。<br>
	 * <br>
	 * 人事情報一覧画面追加JSPファイルの設定も、ここで行う。<br>
	 * <br>
	 * @throws MospException MosP例外が発生した場合
	 */
	void setHumanInfo() throws MospException;
	
}
