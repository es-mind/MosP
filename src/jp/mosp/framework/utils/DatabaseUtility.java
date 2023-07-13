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
package jp.mosp.framework.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.RDBMSType;

/**
 * データベースに関するクラス
 */
public class DatabaseUtility {
	
	/**
	 * コンストラクタ
	 */
	private DatabaseUtility() {
		// 他クラスによるインスタンス化を防止
	}
	
	/**
	 * データベース名取得
	 * @param conn	DBコネクション
	 * @return	データベース名
	 * @throws MospException SQL例外が発生した場合
	 */
	public static String getDatabaseProductName(Connection conn) throws MospException {
		try {
			if (conn != null) {
				return conn.getMetaData().getDatabaseProductName();
			}
			return null;
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * データベース種類取得
	 * @param conn	DBコネクション
	 * @return	データベース種類
	 * @throws MospException SQL例外が発生した場合
	 */
	public static RDBMSType getRDBMS(Connection conn) throws MospException {
		
		String name = getDatabaseProductName(conn);
		if (name == null) {
			return null;
		}
		if (RDBMSType.MySQL.toString().equals(name)) {
			return RDBMSType.MySQL;
		}
		if (RDBMSType.PostgreSQL.toString().equals(name)) {
			return RDBMSType.PostgreSQL;
		}
		if (name.toLowerCase(Locale.JAPANESE).contains("oracle")) {
			return RDBMSType.Oracle;
		}
		if (RDBMSType.H2.toString().equals(name)) {
			return RDBMSType.H2;
		}
		return null;
	}
	
}
