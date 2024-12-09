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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.utils.LogUtility;

/**
 * Actionの基本機能を提供する。<br>
 * <br>
 * MosPフレームワークでは、{@link Controller}により生成されたActionクラスのインスタンスが、
 * DBコネクションの管理、ビジネスロジックの実行、Viewの選択を行う。<br>
 * <br>
 * 各アプリケーションにおいて、当クラスを拡張してActionクラスを
 * 作成することで、MosPフレームワークを有効に利用できる。<br>
 * <br>
 * 拡張して作成したクラスでは{@link ActionInterface#action()}を実装し、
 * このメソッド内にビジネスロジックを記述することになる。
 */
public abstract class BaseAction implements ActionInterface {
	
	/**
	 * MosP処理情報。
	 */
	protected MospParams				mospParams;
	
	/**
	 * MosP用DBコネクション
	 */
	protected List<DBConnBean>			dbConnBeanList;
	
	/**
	 * 連携用DBコネクションMAP
	 */
	protected Map<String, DBConnBean>	dbConnBeanMap;
	
	
	/**
	 * アクション初期化処理。<br>
	 * 各フィールドにオブジェクトを割り当てる。
	 * @param mospParams MosP処理情報
	 */
	@Override
	public void init(MospParams mospParams) {
		// MosP処理情報設定
		this.mospParams = mospParams;
		// DBコネクション初期化
		dbConnBeanMap = new HashMap<String, DBConnBean>();
		dbConnBeanList = new ArrayList<DBConnBean>();
	}
	
	/**
	 * アクション実行処理。<br>
	 * 次の処理及びメソッドを実行する。
	 * <ul>
	 * <li>アクション前処理(各種チェック及び設定)</li>
	 * <li>action(各アプリケーションで実装)</li>
	 * <li>パラメータ設定(Viewに対する情報の設定)</li>
	 * <li>{@link #afterAction()}</li>
	 * </ul>
	 */
	@Override
	public void doAction() throws MospException {
		try {
			// アクション前処理実行
			preAction();
			// アクション開始ログ出力
			LogUtility.actionStart(mospParams, toString());
			// アクション実行
			action();
			// アクション終了ログ出力
			LogUtility.actionEnd(mospParams, toString());
		} catch (MospException e) {
			throw e;
		} finally {
			// アクション後処理
			afterAction();
		}
	}
	
	/**
	 * アクション前処理。
	 * @throws MospException ログインユーザの情報、人事基本情報が存在しない場合
	 */
	protected void preAction() throws MospException {
		// 処理無し
	}
	
	/**
	 * アクション後処理。<br>
	 * @throws MospException BDコネクションの解放に失敗した場合
	 */
	protected void afterAction() throws MospException {
		// DBコネクション毎に処理
		for (DBConnBean dbConnBean : dbConnBeanList) {
			// DBコネクション解放
			dbConnBean.releaseConnection();
		}
		// DBコネクション毎に処理
		Set<Entry<String, DBConnBean>> connectionSet = dbConnBeanMap.entrySet();
		for (Entry<String, DBConnBean> entry : connectionSet) {
			// DBコネクション解放
			entry.getValue().releaseConnection();
		}
	}
	
	/**
	 * BeanHandlerを生成する。<br>
	 * BeanHandler生成後、DBコネクションを設定する。<br>
	 * @param cls        対象BeanHandlerインターフェース
	 * @param dbConnBean DBコネクション
	 * @return BeanHandler
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected BaseBeanHandlerInterface createHandler(Class<?> cls, DBConnBean dbConnBean) throws MospException {
		// BeanHandlerインスタンス取得
		BaseBeanHandlerInterface beanHandler = InstanceFactory.loadBeanHandler(cls, mospParams);
		// DBコネクション設定
		beanHandler.setConnection(dbConnBean);
		return beanHandler;
	}
	
	/**
	 * MosP用BeanHandlerを生成する。<br>
	 * BeanHandler生成後、DBコネクションを設定する。<br>
	 * MosP用の同一DBへのコネクションを複数取得可能。<br>
	 * <br>
	 * 別コネクション要否がtrueの場合、
	 * 新規にDBコネクションを取得し{@link #dbConnBeanList}に追加する。<br>
	 * 別コネクション要否がfalseの場合、
	 * {@link #dbConnBeanList}のDBコネクションを設定する。<br>
	 * <br>
	 * 別コネクションは、異なるトランザクションを利用したい場合等に用いる。<br>
	 * <br>
	 * @param cls                   対象BeanHandlerインターフェース
	 * @param needAnotherConnection 別コネクション要否
	 * @return BeanHandler
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected BaseBeanHandlerInterface createHandler(Class<?> cls, boolean needAnotherConnection) throws MospException {
		// 別コネクション要否及びDBコネクション存在確認
		if (needAnotherConnection == false && dbConnBeanList.isEmpty() == false) {
			return createHandler(cls, dbConnBeanList.get(0));
		}
		// DBコネクション取得
		DBConnBean dbConnBean = new DBConnBean(mospParams);
		// DBコネクションリストに追加
		dbConnBeanList.add(dbConnBean);
		return createHandler(cls, dbConnBean);
	}
	
	/**
	 * 連携用BeanHandlerを生成する。<br>
	 * BeanHandler生成後、DBコネクションを設定する。<br>
	 * <br>
	 * DBのURLを連携用DBコネクションMAPのキーとする。<br>
	 * <br>
	 * @param cls        対象BeanHandlerインターフェース
	 * @param rdbDriver  JDBCドライバ名
	 * @param rdbName    DBのURL
	 * @param userId     DB接続ユーザーID
	 * @param password   パスワード
	 * @return 連携用BeanHandler
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected BaseBeanHandlerInterface createHandler(Class<?> cls, String rdbDriver, String rdbName, String userId,
			String password) throws MospException {
		DBConnBean dbConnBean = dbConnBeanMap.get(rdbName);
		if (dbConnBean == null) {
			dbConnBean = new DBConnBean(mospParams, rdbDriver, rdbName, userId, password);
			dbConnBeanMap.put(rdbName, dbConnBean);
		}
		return createHandler(cls, dbConnBean);
	}
	
	/**
	 * MosP用BeanHandlerを生成する。<br>
	 * BeanHandler生成後、DBコネクションを設定する。<br>
	 * @param cls 対象BeanHandlerインターフェース
	 * @return BeanHandler
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected BaseBeanHandlerInterface createHandler(Class<?> cls) throws MospException {
		return createHandler(cls, false);
	}
	
	/**
	 * MosP用トランザクションをコミットする。<br>
	 * {@link #dbConnBeanList}のDBコネクションに対してコミットを行う。
	 * @throws MospException コミット時に例外が発生した場合
	 */
	protected void commit() throws MospException {
		// コミット
		for (DBConnBean dbConnBean : dbConnBeanList) {
			dbConnBean.commit();
		}
	}
	
	/**
	 * 連携用トランザクションをコミットする。<br>
	 * {@link #dbConnBeanMap}の対象DBコネクションに対してコミットを行う。
	 * @param key JNDIキー、又はDB接続情報：URL
	 * @throws MospException コミット時に例外が発生した場合
	 */
	protected void commit(String key) throws MospException {
		DBConnBean dbConnBean = dbConnBeanMap.get(key);
		if (dbConnBean != null) {
			dbConnBean.commit();
		}
	}
	
	/**
	 * MosP用トランザクションをロールバックする。<br>
	 * {@link #dbConnBeanList}のDBコネクションに対してロールバックを行う。
	 * @throws MospException ロールバック時に例外が発生した場合
	 */
	protected void rollback() throws MospException {
		// ロールバック
		for (DBConnBean dbConnBean : dbConnBeanList) {
			dbConnBean.rollback();
		}
	}
	
	/**
	 * 連携用トランザクションをロールバックする。<br>
	 * {@link #dbConnBeanMap}の対象DBコネクションに対してロールバックを行う。
	 * @param key JNDIキー、又はDB接続情報：URL
	 * @throws MospException ロールバック時に例外が発生した場合
	 */
	protected void rollback(String key) throws MospException {
		DBConnBean dbConnBean = dbConnBeanMap.get(key);
		if (dbConnBean != null) {
			dbConnBean.rollback();
		}
	}
	
}
