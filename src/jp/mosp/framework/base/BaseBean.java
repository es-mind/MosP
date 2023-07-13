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

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.framework.utils.MospUtility;

/**
 * Beanの基本機能を提供する。<br>
 * <br>
 * 当クラスを拡張してBeanクラスを定義することを想定している。<br>
 * Beanは、機能の処理を実装したものである。<br>
 * Beanは、BeanHandlerクラスによって生成される。<br>
 * {@link #mospParams}、{@link #connection}は、BeanHandlerから提供される。<br>
 * <br>
 * Beanは、当クラスの持つ基本機能の他に、実際のビジネスロジックを実装することを想定している。<br>
 * BeanではHttpRequestやHttpSessionを扱わず、Action等から必要なパラメータのみを受け取るのが望ましい。<br>
 * <br>
 * Beanで処理した結果やメッセージ等は、{@link #mospParams}を介してAction等に伝えられる。<br>
 */
public abstract class BaseBean implements BaseBeanInterface {
	
	/**
	 * MosP処理情報。
	 */
	protected MospParams	mospParams;
	
	/**
	 * データベースコネクション。
	 */
	protected Connection	connection;
	
	
	/**
	 * {@link BaseBean}を生成する。<br>
	 */
	public BaseBean() {
		// 処理無し
	}
	
	/**
	 * MosP処理情報及びデータベースコネクションを設定する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection データベースコネクション
	 */
	@Override
	public void setParams(MospParams mospParams, Connection connection) {
		this.mospParams = mospParams;
		this.connection = connection;
	}
	
	/**
	 * DAOインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadDao(Class, MospParams, Connection)}を用いる。<br>
	 * @param cls 対象DAOインターフェース
	 * @return 初期化されたDAOインスタンス
	 * @throws MospException DAOインスタンスの生成及び初期化に失敗した場合
	 */
	protected BaseDaoInterface createDao(Class<?> cls) throws MospException {
		// インスタンス生成クラスを用いてDAOインスタンスを生成し初期化
		return InstanceFactory.loadDao(cls, mospParams, connection);
	}
	
	/**
	 * DAOインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadBeanInstance(Class, MospParams, Connection)}を用いる。<br>
	 * @param cls 対象DAOインターフェース
	 * @return 初期化されたDAOインスタンス
	 * @throws MospException DAOインスタンスの生成及び初期化に失敗した場合
	 */
	protected <T extends BaseDaoInterface> T createDaoInstance(Class<T> cls) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadDaoInstance(cls, mospParams, connection);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadBean(Class, MospParams, Connection)}を用いる。<br>
	 * @param cls 対象Beanインターフェース
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected BaseBeanInterface createBean(Class<?> cls) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBean(cls, mospParams, connection);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadBeanInstance(Class, MospParams, Connection)}を用いる。<br>
	 * @param cls 対象Beanインターフェース
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected <T extends BaseBeanInterface> T createBeanInstance(Class<T> cls) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBeanInstance(cls, mospParams, connection);
	}
	
	/**
	 * 対象日以前で最新のBeanインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadBean(Class, Date, MospParams, Connection)}を用いる。<br>
	 * @param cls        対象Beanインターフェース
	 * @param targetDate 対象日
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected BaseBeanInterface createBean(Class<?> cls, Date targetDate) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBean(cls, targetDate, mospParams, connection);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。
	 * {@link InstanceFactory#loadBean(String, MospParams, Connection)}を用いる。<br>
	 * @param modelClass モデルクラス名
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected BaseBeanInterface createBean(String modelClass) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBean(modelClass, mospParams, connection);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * {@link InstanceFactory#loadBean(Class, String, MospParams, Connection)}を用いる。<br>
	 * @param cls 対象Beanインターフェース
	 * @param modelClass モデルクラス名
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected <T extends BaseBeanInterface> T createBean(Class<T> cls, String modelClass) throws MospException {
		// インスタンス生成クラスを用いてBeanインスタンスを生成し初期化
		return InstanceFactory.loadBean(cls, modelClass, mospParams, connection);
	}
	
	/**
	 * オブジェクトを生成する。<br>
	 * {@link InstanceFactory#loadGeneralInstance(Class, MospParams)}を用いる。<br>
	 * <br>
	 * @param cls 対象オブジェクトインターフェース
	 * @return オブジェクト
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected <T> T createObject(Class<?> cls) throws MospException {
		// オブジェクトを生成
		return MospUtility.createObject(cls, mospParams);
	}
	
	/**
	 * キーによるデータ取得を行う。
	 * @param dao 対象DAOオブジェクト
	 * @param id  レコード識別ID
	 * @param isUpdate 行ロックフラグ
	 * @return 検索結果(DTO)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected BaseDto findForKey(BaseDaoInterface dao, long id, boolean isUpdate) throws MospException {
		return dao.findForKey(id, isUpdate);
	}
	
	/**
	 * 排他確認
	 * @param formerDto 編集前対象DTO
	 * @param baseDto 更新対象DTO
	 * @return
	 * <p>
	 * 編集前の更新日と更新対象の更新日が等しい場合true、そうでない場合false。
	 * </p>
	 */
	protected boolean checkExclusive(BaseDtoInterface formerDto, BaseDtoInterface baseDto) {
		return formerDto != null && baseDto != null && formerDto.getDeleteFlag() == MospConst.DELETE_FLAG_OFF
				&& formerDto.getUpdateDate().compareTo(baseDto.getUpdateDate()) == 0;
	}
	
	/**
	 * 論理削除確認を行う。<br>
	 * 対象レコードが論理削除されていない場合は、
	 * {@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param dao 対象DAOオブジェクト
	 * @param id レコード識別ID
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkLogicalDelete(BaseDaoInterface dao, long id) throws MospException {
		// 対象レコード取得
		BaseDto dto = findForKey(dao, id, false);
		// 対象レコードが存在しない場合
		if (dto != null && dto.getDeleteFlag() == MospConst.DELETE_FLAG_OFF) {
			// エラーメッセージを設定
			MessageUtility.addErrorDbUpdate(mospParams);
		}
	}
	
	/**
	 * 論理削除を行う。<br>
	 * レコード識別IDがlongの最小値である場合は、処理をしない。<br>
	 * @param dao 対象DAOオブジェクト
	 * @param id  レコード識別ID
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void logicalDelete(BaseDaoInterface dao, long id) throws MospException {
		// レコード識別IDがlongの最小値である場合
		if (id == Long.MIN_VALUE) {
			// 処理無し
			return;
		}
		// 削除準備
		BaseDto baseDto = dao.findForKey(id, true);
		baseDto.setDeleteFlag(MospConst.DELETE_FLAG_ON);
		// 排他確認
		if (checkExclusive(dao.findForKey(id, true), baseDto)) {
			// 論理削除
			dao.update(baseDto);
			// 削除確認
			checkLogicalDelete(dao, id);
		} else {
			// エラーメッセージを設定
			MessageUtility.addErrorDbDelete(mospParams);
		}
	}
	
}
