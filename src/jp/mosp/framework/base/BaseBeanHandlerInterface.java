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

import java.sql.Connection;
import java.util.Date;

import jp.mosp.framework.instance.InstanceFactory;

/**
 * BeanHandlerのインターフェース。
 */
public interface BaseBeanHandlerInterface {
	
	/**
	 * DBコネクションを取得する。
	 * @return DBコネクション
	 */
	DBConnBean getConnection();
	
	/**
	 * DBコネクションを設定する。
	 * @param dbConnBean DBコネクション
	 */
	void setConnection(DBConnBean dbConnBean);
	
	/**
	 * MosP処理情報を設定する。<br>
	 * @param mospParams セットする MosP処理情報
	 */
	void setMospParams(MospParams mospParams);
	
	/**
	 * トランザクションをコミットする。
	 * @throws MospException コミット時に例外が発生した場合
	 */
	void commit() throws MospException;
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadBean(Class, MospParams, Connection)}を用いる。<br>
	 * @param cls 対象Beanインターフェース
	 * @return Beanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	BaseBeanInterface createBean(Class<?> cls) throws MospException;
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadBean(Class, MospParams, Connection)}を用いる。<br>
	 * @param cls 対象Beanインターフェース
	 * @return Beanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	<T extends BaseBeanInterface> T createBeanInstance(Class<T> cls) throws MospException;
	
	/**
	 * 対象日以前で最新のBeanインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadBean(Class, Date, MospParams, Connection)}を用いる。<br>
	 * @param cls        対象Beanインターフェース
	 * @param targetDate 対象日
	 * @return Beanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	BaseBeanInterface createBean(Class<?> cls, Date targetDate) throws MospException;
	
	/**
	 * Beanインスタンスを生成し、初期化する。
	 * {@link InstanceFactory#loadBean(String, MospParams, Connection)}を用いる。<br>
	 * @param modelClass モデルクラス名
	 * @return Beanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	BaseBeanInterface createBean(String modelClass) throws MospException;
	
	/**
	 * Beanインスタンスを生成し、初期化する。
	 * {@link InstanceFactory#loadBean(String, MospParams, Connection)}を用いる。<br>
	 * @param cls        クラス
	 * @param modelClass モデルクラス名
	 * @return Beanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	<T extends BaseBeanInterface> T createBean(Class<T> cls, String modelClass) throws MospException;
	
}
