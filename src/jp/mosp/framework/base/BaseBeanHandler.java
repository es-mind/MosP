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

import java.util.Date;

import jp.mosp.framework.instance.InstanceFactory;

/**
 * BeanHandlerの基本機能を提供する。<br>
 * <br>
 * 当クラスを拡張してBeanHandlerクラスを定義することを想定している。<br>
 * BeanHandlerは、機能の処理を実装したBeanクラスを纏めたものである。<br>
 * BeanHandlerは、Actionクラスによって生成、利用される。<br>
 * BeanHandlerは、ActionクラスがBeanクラスを扱い易くすることを目的としている。<br>
 * <br>
 * BeanHandlerは、当クラスの持つ基本機能の他に、次の機能を実装することを想定している。<br>
 * <ul>
 * <li>Beanクラスをインスタンス化する。</li>
 * <li>生成Beanインスタンスを保持する。</li>
 * <li>保持BeanインスタンスをActionに提供する。</li>
 * <li>{@link DBConnBean#getConnection()}、{@link #mospParams}をBeanインスタンスに提供する。</li>
 * </ul>
 */
public abstract class BaseBeanHandler implements BaseBeanHandlerInterface {
	
	/**
	 * MosP処理情報。
	 */
	protected MospParams	mospParams;
	
	/**
	 * DBコネクション。
	 */
	protected DBConnBean	dbConnBean;
	
	
	/**
	 * コンストラクタ。
	 */
	public BaseBeanHandler() {
		// 処理無し
	}
	
	@Override
	public DBConnBean getConnection() {
		return dbConnBean;
	}
	
	@Override
	public void setConnection(DBConnBean dbConnBean) {
		this.dbConnBean = dbConnBean;
	}
	
	@Override
	public void setMospParams(MospParams mospParams) {
		this.mospParams = mospParams;
	}
	
	@Override
	public void commit() throws MospException {
		dbConnBean.commit();
	}
	
	@Override
	public BaseBeanInterface createBean(Class<?> cls) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBean(cls, mospParams, dbConnBean.getConnection());
	}
	
	@Override
	public <T extends BaseBeanInterface> T createBeanInstance(Class<T> cls) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBeanInstance(cls, mospParams, dbConnBean.getConnection());
	}
	
	@Override
	public BaseBeanInterface createBean(Class<?> cls, Date targetDate) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBean(cls, targetDate, mospParams, dbConnBean.getConnection());
	}
	
	@Override
	public BaseBeanInterface createBean(String modelClass) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBean(modelClass, mospParams, dbConnBean.getConnection());
	}
	
	@Override
	public <T extends BaseBeanInterface> T createBean(Class<T> cls, String modelClass) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBean(cls, modelClass, mospParams, dbConnBean.getConnection());
	}
}
