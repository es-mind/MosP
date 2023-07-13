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
package jp.mosp.framework.base;

/**
 * 処理を記載するActionクラスのインターフェース。<br><br>
 * 
 * このインターフェースを実装したActionクラスを、{@link Controller}
 * はインスタンス化、実行することができる。
 */
public interface ActionInterface {
	
	/**
	 * Actionインスタンス初期化。<br>
	 * {@link Controller}によりActionクラスインスタンスが生成された際に実行される。
	 * @param params  MosP処理情報
	 * @throws MospException 実行時例外が発生した場合
	 */
	void init(MospParams params) throws MospException;
	
	/**
	 * Action実行。<br>
	 * {@link Controller}により{@link #init(MospParams)}
	 * の後に実行される。
	 * @throws MospException 実行時例外が発生した場合
	 */
	void doAction() throws MospException;
	
	/**
	 * Action実行。<br>
	 * 実際に処理を行いたいロジックを実装する。
	 * @throws MospException 実行時例外が発生した場合
	 */
	void action() throws MospException;
	
}
