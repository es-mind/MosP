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
package jp.mosp.framework.instance;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Comparator;
import java.util.Date;

import jp.mosp.framework.base.BaseBeanHandlerInterface;
import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.framework.utils.MospUtility;

/**
 * インスタンス生成クラス
 */
public class InstanceFactory {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private InstanceFactory() {
		// 処理無し
	}
	
	/**
	 * クラスローダーからインスタンスを生成する。
	 * @param className	 対象クラス名
	 * @return 対象クラスインスタンス
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	public static Object loadInstance(String className) throws MospException {
		try {
			// クラスローダーからインスタンスを生成
			return getNewInstance(Thread.currentThread().getContextClassLoader().loadClass(className));
		} catch (NullPointerException e) {
			throw new MospException(e, ExceptionConst.EX_NO_CLASS_NAME, null);
		} catch (ClassNotFoundException e) {
			throw new MospException(e, ExceptionConst.EX_NO_CLASS, className);
		}
	}
	
	/**
	 * クラスローダーからインスタンスを生成する。
	 * @param cls       クラス
	 * @param className 対象クラス名
	 * @return 対象クラスインスタンス
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	@SuppressWarnings("unchecked")
	public static <T> T loadInstance(Class<T> cls, String className) throws MospException {
		try {
			// クラスローダーからインスタンスを生成
			return (T)getNewInstance(Thread.currentThread().getContextClassLoader().loadClass(className));
		} catch (NullPointerException e) {
			throw new MospException(e, ExceptionConst.EX_NO_CLASS_NAME, null);
		} catch (ClassNotFoundException e) {
			throw new MospException(e, ExceptionConst.EX_NO_CLASS, className);
		}
	}
	
	/**
	 * クラス名からインスタンスを生成する。
	 * @param className 対象クラス名
	 * @return 対象クラスインスタンス
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	@SuppressWarnings("unchecked")
	public static <T> T simplifiedInstance(String className) throws MospException {
		try {
			// クラス名からインスタンスを生成
			return (T)getNewInstance(Class.forName(className));
		} catch (NullPointerException e) {
			throw new MospException(e, ExceptionConst.EX_NO_CLASS_NAME, null);
		} catch (ClassNotFoundException e) {
			throw new MospException(e, ExceptionConst.EX_NO_CLASS, className);
		}
	}
	
	/**
	 * BeanHandlerインスタンスを生成し、初期化する。<br>
	 * 但し、DBコネクションは取得しない。<br>
	 * <br>
	 * {@link MospUtility#getModelClass(Class, jp.mosp.framework.property.MospProperties, Date)}
	 * を用いて、モデルクラス名を取得する。<br>
	 * <br>
	 * @param cls        対象BeanHandlerインターフェース
	 * @param mospParams MosP処理情報
	 * @return 初期化されたBeanHandlerインスタンス
	 * @throws MospException BeanHandlerインスタンスの生成及び初期化に失敗した場合
	 */
	public static BaseBeanHandlerInterface loadBeanHandler(Class<?> cls, MospParams mospParams) throws MospException {
		// モデルクラス名取得
		String modelClass = MospUtility.getModelClass(cls, mospParams.getProperties(), null);
		// BeanHandlerインスタンスを生成し初期化
		return loadBeanHandler(modelClass, mospParams);
	}
	
	/**
	 * BeanHandlerインスタンスを生成し、初期化する。<br>
	 * 但し、DBコネクションは取得しない。
	 * @param modelClass モデルクラス名
	 * @param mospParams MosP処理情報
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected static BaseBeanHandlerInterface loadBeanHandler(String modelClass, MospParams mospParams)
			throws MospException {
		// BeanHandlerインスタンス取得
		BaseBeanHandlerInterface beanHandler = (BaseBeanHandlerInterface)loadInstance(modelClass);
		// MosPパラメータ設定
		beanHandler.setMospParams(mospParams);
		// デバッグメッセージ
		LogUtility.debug(mospParams, beanHandler.toString());
		return beanHandler;
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * <br>
	 * {@link MospUtility#getModelClass(Class, jp.mosp.framework.property.MospProperties, Date)}
	 * を用いてモデルクラス名を取得し、Beanインスタンス生成、初期化する。<br>
	 * <br>
	 * @param cls        対象Beanインターフェース
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	public static BaseBeanInterface loadBean(Class<?> cls, MospParams mospParams, Connection connection)
			throws MospException {
		// モデルクラス名取得
		String modelClass = MospUtility.getModelClass(cls, mospParams.getProperties(), null);
		// Beanインスタンスを生成し初期化
		return loadBean(modelClass, mospParams, connection);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * @param cls        クラス
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	public static <T extends BaseBeanInterface> T loadBeanInstance(Class<T> cls, MospParams mospParams,
			Connection connection) throws MospException {
		// モデルクラス名取得
		String modelClass = MospUtility.getModelClass(cls, mospParams.getProperties(), null);
		// Beanインスタンスを生成し初期化
		return loadBean(cls, modelClass, mospParams, connection);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * <br>
	 * {@link MospUtility#getModelClass(Class, jp.mosp.framework.property.MospProperties, Date)}
	 * を用いて対象日以前で最新のモデルクラス名を取得し、Beanインスタンス生成、初期化する。<br>
	 * <br>
	 * @param cls        対象Beanインターフェース
	 * @param targetDate 対象日
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	public static BaseBeanInterface loadBean(Class<?> cls, Date targetDate, MospParams mospParams,
			Connection connection) throws MospException {
		// モデルクラス名取得
		String modelClass = MospUtility.getModelClass(cls, mospParams.getProperties(), targetDate);
		// Beanインスタンスを生成し初期化
		return loadBean(modelClass, mospParams, connection);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * @param modelClass モデルクラス名
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	public static BaseBeanInterface loadBean(String modelClass, MospParams mospParams, Connection connection)
			throws MospException {
		// Beanインスタンス取得
		BaseBeanInterface bean = (BaseBeanInterface)loadInstance(modelClass);
		// MosP処理情報及びコネクションを設定
		bean.setParams(mospParams, connection);
		// 初期化処理を実行
		bean.initBean();
		// デバッグメッセージ
		LogUtility.debug(mospParams, bean.toString());
		return bean;
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * @param cls        クラス
	 * @param modelClass モデルクラス名
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	public static <T extends BaseBeanInterface> T loadBean(Class<T> cls, String modelClass, MospParams mospParams,
			Connection connection) throws MospException {
		// Beanインスタンス取得
		T bean = loadInstance(cls, modelClass);
		// MosP処理情報及びコネクションを設定
		bean.setParams(mospParams, connection);
		// 初期化処理を実行
		bean.initBean();
		// デバッグメッセージ
		LogUtility.debug(mospParams, bean.toString());
		return bean;
	}
	
	/**
	 * DAOインスタンスを生成し、初期化する。<br>
	 * <br>
	 * {@link MospUtility#getModelClass(Class, jp.mosp.framework.property.MospProperties, Date)}
	 * を用いてモデルクラス名を取得し、DAOインスタンス生成、初期化する。<br>
	 * <br>
	 * @param cls 対象DAOインターフェース
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたDAOインスタンス
	 * @throws MospException DAOインスタンスの生成及び初期化に失敗した場合
	 */
	public static BaseDaoInterface loadDao(Class<?> cls, MospParams mospParams, Connection connection)
			throws MospException {
		// モデルクラス名取得
		String modelClass = MospUtility.getModelClass(cls, mospParams.getProperties(), null);
		// DAOインスタンスを生成し初期化
		return loadDao(modelClass, mospParams, connection);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * @param cls        クラス
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	public static <T extends BaseDaoInterface> T loadDaoInstance(Class<T> cls, MospParams mospParams,
			Connection connection) throws MospException {
		// モデルクラス名取得
		String modelClass = MospUtility.getModelClass(cls, mospParams.getProperties(), null);
		// DAOインスタンスを生成し初期化
		return loadDao(cls, modelClass, mospParams, connection);
	}
	
	/**
	 * DAOインスタンスを生成し、初期化する。<br>
	 * @param modelClass モデルクラス名
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたDAOインスタンス
	 * @throws MospException DAOインスタンスの生成及び初期化に失敗した場合
	 */
	public static BaseDaoInterface loadDao(String modelClass, MospParams mospParams, Connection connection)
			throws MospException {
		// DAOインスタンス取得
		BaseDaoInterface dao = (BaseDaoInterface)loadInstance(modelClass);
		// MosP処理情報及びコネクションを設定
		dao.setInitParams(mospParams, connection);
		// 初期化処理を実行
		dao.initDao();
		// デバッグメッセージ
		LogUtility.debug(mospParams, dao.toString());
		return dao;
	}
	
	/**
	 * DAOインスタンスを生成し、初期化する。<br>
	 * @param cls        クラス
	 * @param modelClass モデルクラス名
	 * @param mospParams MosP処理情報
	 * @param connection コネクション
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	public static <T extends BaseDaoInterface> T loadDao(Class<T> cls, String modelClass, MospParams mospParams,
			Connection connection) throws MospException {
		// DAOインスタンス取得
		T dao = loadInstance(cls, modelClass);
		// MosP処理情報及びコネクションを設定
		dao.setInitParams(mospParams, connection);
		// 初期化処理を実行
		dao.initDao();
		// デバッグメッセージ
		LogUtility.debug(mospParams, dao.toString());
		return dao;
	}
	
	/**
	 * クラスローダーからインスタンスを生成する。<br>
	 * <br>
	 * <br>
	 * @param cls 対象DAOインターフェース
	 * @param mospParams MosP処理情報
	 * @return 対象クラスインスタンス
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	@SuppressWarnings("unchecked")
	public static <T> T loadGeneralInstance(Class<T> cls, MospParams mospParams) throws MospException {
		// モデルクラス名取得
		String className = MospUtility.getModelClass(cls, mospParams.getProperties(), null);
		try {
			// クラスをロードしてインスタンスを生成
			return (T)getNewInstance(Thread.currentThread().getContextClassLoader().loadClass(className));
		} catch (ClassNotFoundException e) {
			throw new MospException(e, ExceptionConst.EX_NO_CLASS, className);
		}
	}
	
	/**
	 * 比較クラスインスタンスを生成し、初期化する。<br>
	 * @param className 比較クラス名
	 * @return 比較クラスインスタンス
	 * @throws MospException 比較クラスインスタンスの生成に失敗した場合
	 */
	@SuppressWarnings("unchecked")
	public static Comparator<Object> loadComparator(String className) throws MospException {
		// 比較クラスインスタンス取得
		return (Comparator<Object>)loadInstance(className);
	}
	
	/**
	 * クラスのインスタンスを生成し取得する。<br>
	 * @param cls クラス
	 * @return クラスのインスタンス
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static <T> T getNewInstance(Class<T> cls) throws MospException {
		try {
			// クラスのインスタンスを生成し取得
			return cls.getDeclaredConstructor().newInstance();
		} catch (InstantiationException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_INSTANTIATE, cls.getSimpleName());
		} catch (IllegalAccessException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_INSTANTIATE, cls.getSimpleName());
		} catch (InvocationTargetException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_INSTANTIATE, cls.getSimpleName());
		} catch (NoSuchMethodException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_INSTANTIATE, cls.getSimpleName());
		}
	}
	
}
