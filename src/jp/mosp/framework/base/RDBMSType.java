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

import java.util.List;
import java.util.Locale;

/**
 * MosPで利用可能なRDBMSに関するクラス
 */
public enum RDBMSType {
	
	/**
	 * MySQL
	 */
	MySQL {
		
		@Override
		public String tableListSQL() {
			return "show tables";
		}
		
		@Override
		public String tableName() {
			return null;
		}
		
		@Override
		public String toString() {
			return "MySQL";
		}
		
		@Override
		public String lockTableSQL(List<String[]> tableList) {
			StringBuffer sb = new StringBuffer();
			sb.append("LOCK TABLES ");
			for (String[] table : tableList) {
				sb.append(table[0]);
				if (Boolean.parseBoolean(table[1])) {
					sb.append(" WRITE ");
				} else {
					sb.append(" READ ");
				}
				sb.append(',');
			}
			sb.delete(sb.lastIndexOf(","), sb.length());
			return sb.toString();
		}
		
		@Override
		public String getTableDescriptionQuery(String tableName) {
			StringBuffer sb = new StringBuffer();
			sb.append("SHOW FULL COLUMNS FROM ");
			sb.append(tableName);
			return sb.toString();
		}
	},
	/**
	 * PostgreSQL
	 */
	PostgreSQL {
		
		@Override
		public String tableListSQL() {
			return "select * from pg_tables where not tablename like 'pg%' order by tablename";
		}
		
		@Override
		public String tableName() {
			return "tablename";
		}
		
		@Override
		public String toString() {
			return "PostgreSQL";
		}
		
		@Override
		public String lockTableSQL(List<String[]> tableList) {
			StringBuffer sb = new StringBuffer();
			sb.append("LOCK TABLE ");
			for (String[] table : tableList) {
				if (Boolean.parseBoolean(table[1])) {
					sb.append(table[0]);
					sb.append(',');
				}
			}
			sb.delete(sb.lastIndexOf(","), sb.length());
			// ロックモードを SHARE ROW EXCLUSIVE に指定
			sb.append(" IN SHARE ROW EXCLUSIVE MODE");
			return sb.toString();
		}
		
		@Override
		public String getTableDescriptionQuery(String tableName) {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ");
			sb.append("pg_attribute.attname as FIELD, ");
			sb.append("pg_type.typname as TYPE, ");
			sb.append("pg_description.description as COMMENT ");
			sb.append("FROM ");
			sb.append("pg_attribute, ");
			sb.append("pg_type, ");
			sb.append("pg_description, ");
			sb.append("pg_stat_all_tables ");
			sb.append("WHERE ");
			sb.append("pg_stat_all_tables.relname = '");
			sb.append(tableName.toLowerCase(Locale.getDefault()));
			sb.append("' ");
			sb.append("AND ");
			sb.append("pg_stat_all_tables.relid = pg_description.objoid ");
			sb.append("AND ");
			sb.append("pg_description.objsubid <> 0 ");
			sb.append("AND ");
			sb.append("pg_description.objoid = pg_attribute.attrelid ");
			sb.append("AND ");
			sb.append("pg_description.objsubid = pg_attribute.attnum ");
			sb.append("AND ");
			sb.append("pg_type.oid = pg_attribute.atttypid ");
			sb.append("ORDER BY ");
			sb.append("pg_description.objsubid");
			return sb.toString();
		}
	},
	
	/**
	 * Oracle
	 */
	Oracle {
		
		@Override
		public String tableListSQL() {
			return "select * from USER_TABLES";
		}
		
		@Override
		public String getTableDescriptionQuery(String tableName) {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ");
			sb.append("T1.COLUMN_NAME FIELD, ");
			sb.append("T1.DATA_TYPE TYPE, ");
			sb.append("T2.COMMENTS COMMNET ");
			sb.append(" FROM ");
			sb.append("USER_TAB_COLUMNS T1");
			sb.append(" JOIN ");
			sb.append("USER_COL_COMMENTS T2");
			sb.append(" ON ");
			sb.append("T1.TABLE_NAME = T2.TABLE_NAME");
			sb.append(" AND ");
			sb.append("T1.COLUMN_NAME = T2.COLUMN_NAME");
			sb.append(" WHERE ");
			sb.append("T1.TABLE_NAME = '" + tableName + "'");
			return sb.toString();
		}
		
		@Override
		public String lockTableSQL(List<String[]> tableList) {
			StringBuffer sb = new StringBuffer();
			sb.append("LOCK TABLE ");
			for (String[] table : tableList) {
				if (Boolean.parseBoolean(table[1])) {
					sb.append(table[0]);
					sb.append(',');
				}
			}
			sb.delete(sb.lastIndexOf(","), sb.length());
			// ロックモードをEXCLUSIVEに指定
			sb.append(" IN EXCLUSIVE MODE");
			return sb.toString();
		}
		
		@Override
		public String tableName() {
			return null;
		}
		
		@Override
		public String toString() {
			return "Oracle";
		}
		
	},
	
	/**
	 * H2
	 */
	H2 {
		
		@Override
		public String tableListSQL() {
			return "select TABLE_NAME from information_schema.tables";
		}
		
		@Override
		public String getTableDescriptionQuery(String tableName) {
			StringBuffer sb = new StringBuffer();
			sb.append("select ");
			sb.append("COLUMN_NAME FIELD" + ",");
			sb.append("TYPE_NAME TYPE" + ",");
			sb.append("REMARKS COMMNET");
			sb.append(" from ");
			sb.append("information_schema.columns");
			sb.append(" where ");
			sb.append("table_name =?");
			return sb.toString();
		}
		
		@Override
		public String lockTableSQL(List<String[]> tableList) {
			StringBuffer sb = new StringBuffer();
			sb.append("LOCK TABLE ");
			for (String[] table : tableList) {
				if (Boolean.parseBoolean(table[1])) {
					sb.append(table[0]);
					sb.append(',');
				}
			}
			sb.delete(sb.lastIndexOf(","), sb.length());
			return sb.toString();
		}
		
		@Override
		public String tableName() {
			return null;
		}
		
		@Override
		public String toString() {
			return "H2";
		}
	},
	
	;
	
	/**
	 * @return	テーブル名取得SQL
	 */
	public abstract String tableListSQL();
	
	/**
	 * @return	対象テーブル名
	 */
	public abstract String tableName();
	
	/**
	 * @param tableList 対象テーブル情報一覧
	 * @return LOCK TABLE用SQL
	 */
	public abstract String lockTableSQL(List<String[]> tableList);
	
	/**
	 * テーブル定義取得SQLを取得。
	 * @param tableName 対象テーブル名
	 * @return テーブル定義取得SQL
	 */
	public abstract String getTableDescriptionQuery(String tableName);
	
	@Override
	public abstract String toString();
	
}
