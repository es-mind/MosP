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
package jp.mosp.framework.base;

import java.sql.Connection;

/**
 * Beanの基本機能のインターフェース。
 */
public interface BaseBeanInterface {
	
	/**
	 * {@link MospParams}、{@link Connection}をBeanに設定する。
	 * @param mospParams 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	void setParams(MospParams mospParams, Connection connection);
	
	/**
	 * Beanの初期化。
	 * <p>
	 * 主にBeanで利用するDAOの初期化やパラメータの初期化を行う。
	 * </p>
	 * @throws MospException Beanの初期化で例外が発生した場合
	 */
	void initBean() throws MospException;
	
}
