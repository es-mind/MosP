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
package jp.mosp.platform.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseAction;
import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParametersMapper;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.base.TopicPath;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.property.RangeProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.framework.utils.ViewFileLocationUtility;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * MosPプラットフォームにおけるActionの基本機能を提供する。<br>
 */
public abstract class PlatformAction extends BaseAction {
	
	/**
	 * MosPアプリケーション設定キー(テンプレートJSPファイルパス)。
	 */
	public static final String				APP_TEMPLATE_JSP	= "TemplateJsp";
	
	/**
	 * MosPアプリケーション設定キー(テンプレートJSPファイルパス)。
	 */
	public static final String				APP_NAVI_JSP		= "NaviJsp";
	
	/**
	 * MosPアプリケーション設定キー(標準CSSファイルパス)。
	 */
	public static final String				APP_BASE_CSS_FILES	= "BaseCssFiles";
	
	/**
	 * MosPアプリケーション設定キー(標準JSファイルパス)。
	 */
	public static final String				APP_BASE_JS_FILES	= "BaseJsFiles";
	
	/**
	 * MosPアプリケーション設定キー(ポータル表示コマンド)。
	 */
	protected static final String			APP_COMMAND_PORTAL	= "CommandPortal";
	
	/**
	 * MosPアプリケーション設定キー(初期表示コマンド)。
	 */
	protected static final String			APP_COMMAND_INDEX	= "CommandIndex";
	
	/**
	 * MosPアプリケーション設定キー(一覧表示数)。
	 */
	protected static final String			APP_LIST_LENGTH		= "ListLength";
	
	/**
	 * MosP汎用パラメータキー(画面表示時にスクロールさせるHTML要素のID)。<br>
	 * このキーで設定したStringは、画面表示時にJavaScriptの変数として宣言される。<br>
	 */
	protected static final String			MGP_JS_SCROLL_TO	= "jsScrollTo";
	
	/**
	 * メニューキー(メインメニュー：トップ)。<br>
	 */
	protected static final String			MEN_MAIN_TOP		= "Top";
	
	/**
	 * MosPプラットフォーム用BeanHandler。
	 */
	protected PlatformBeanHandlerInterface	platform;
	
	/**
	 * MosPプラットフォーム参照用BeanHandler。
	 */
	protected ReferenceBeanHandlerInterface	platformReference;
	
	/**
	 * パンくずリスト用コマンド。<br>
	 * コマンドは、各アクションで設定する。<br>
	 */
	protected String						topicPathCommand	= null;
	
	
	/**
	 * アクションの前処理を実行する。<br><br>
	 * <ul><li>
	 * BaseActionのpreActionを実行<br><br>
	 * </li><li>
	 * リクエストされたメニューキーを取得<br>
	 * {@link #getTransferredMenuKey()}で取得する。<br><br>
	 * </li><li>
	 * パンくずリスト初期化(ポータルのみ残す)<br>
	 * メニューキーが存在する場合のみ実施する。<br><br>
	 * </li><li>
	 * 範囲設定の読込<br>
	 * メニューキーが存在する場合のみ実施する。<br><br>
	 * </li></ul>
	 */
	@Override
	protected void preAction() throws MospException {
		// BaseActionのpreActionを実行
		super.preAction();
		// リクエストされたメニューキーを取得
		String menuKey = getTransferredMenuKey();
		// ユーザ確認
		checkLoginUser();
		// メニューキー確認(連続実行の場合は実施しない)
		if (menuKey != null && menuKey.isEmpty() == false && mospParams.getNextCount() == 0) {
			// 範囲設定読込
			setRangeMap(menuKey);
			// パンくずリスト初期化(ポータルのみ残す)
			removeAfterIndex(mospParams.getTopicPathList(), 0);
		}
		// ログインユーザが存在する場合
		if (mospParams.getUser() != null) {
			// アクション前処理クラスの処理を実行
			reference().preAction().preAction();
		}
	}
	
	/**
	 * ログインユーザを確認する。<br>
	 * ログインしている(ログインユーザが存在する)場合、
	 * システム日付において、ユーザ情報、人事基本情報が存在することを確認する。<br>
	 * これらが存在しない場合、MosP例外を発行し、ログアウト処理をする。<br>
	 * @throws MospException ユーザ情報、人事基本情報が存在しない場合
	 */
	protected void checkLoginUser() throws MospException {
		// ユーザがいない場合
		if (mospParams.getUser() == null) {
			return;
		}
		// ログインユーザを取得
		String userlId = mospParams.getUser().getUserId();
		// 個人ID取得
		String personalId = mospParams.getUser().getPersonalId();
		// システム日付取得
		Date systemTime = DateUtility.getSystemTime();
		// システム日付でユーザ情報取得
		UserMasterDtoInterface userMasterDto = reference().user().getUserInfo(userlId, systemTime);
		// システム日付で人事基本情報取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, systemTime);
		// ユーザ情報或いは人事基本情報が存在しない場合
		if (userMasterDto == null || humanDto == null) {
			// ログアウト処理(MosPセッション保持情報初期化)
			mospParams.getStoredInfo().initStoredInfo();
			// 初期表示コマンド設定
			String nextCommand = mospParams.getApplicationProperty(APP_COMMAND_INDEX);
			mospParams.setNextCommand(nextCommand);
			// ログアウトメッセージを設定
			PfMessageUtility.addErrorUserNotExist(mospParams);
			// MosP例外を発行
			throw new MospException(PfMessageUtility.MSG_W_USER_NOT_EXIST);
		}
	}
	
	/**
	 * 範囲設定情報群を設定する。<br>
	 * ログインユーザのロール(追加ロール含む)及びメニューキーから
	 * 範囲設定情報群を取得する。<br>
	 * @param menuKey メニューキー
	 */
	protected void setRangeMap(String menuKey) {
		// ログインユーザのロールから操作範囲設定情報群(キー：操作区分)を取得し設定
		mospParams.getStoredInfo().setRangeMap(RoleUtility.getUserRanges(mospParams, menuKey));
	}
	
	/**
	 * 範囲設定を確認する。<br>
	 * 範囲設定が存在しない場合、
	 * ログインユーザからロールを取得し、メニューキーで範囲情報を取得する。<br>
	 * @param menuKey メニューキー
	 */
	protected void checkRangeMap(String menuKey) {
		// 範囲設定取得及び確認
		Map<String, RangeProperty> rangeMap = mospParams.getStoredInfo().getRangeMap();
		if (rangeMap != null && rangeMap.isEmpty() == false) {
			return;
		}
		// 範囲設定
		setRangeMap(menuKey);
	}
	
	/**
	 * VOインスタンスの取得及び設定。<br><br>
	 * {@link #prepareVo(boolean, boolean)}を実行する。引数はtrue、true。<br>
	 * @return 取得したVOインスタンス
	 * @throws MospException パラメータがマッピングできなかった場合
	 */
	protected BaseVo prepareVo() throws MospException {
		return prepareVo(true, true);
	}
	
	/**
	 * VOインスタンスの取得及び設定。<br><br>
	 * <ul><li>
	 * VOインスタンス取得<br>
	 * {@link #getSpecificVo()}で、処理対象VOインスタンスを取得する。<br><br>
	 * </li><li>
	 * 保存VO確認<br>
	 * {@link MospParams#getStoredVo(String)}で、VOインスタンスのクラス名を
	 * キーとして保存VOを取得する。<br><br>
	 * </li><li>
	 * パラメータマッピング<br>
	 * {@link MospParametersMapper#mapParameters(Object, Map)}を行う。<br><br>
	 * </li><li>
	 * フォワード先URLの設定<br>
	 * {@link #setViewPath(String)}を行う。<br><br>
	 * </li><li>
	 * VOの設定<br>
	 * {@link MospParams#setVo(BaseVo)}を行う。<br><br>
	 * </li><li>
	 * パンくずリストの設定<br>
	 * {@link #setTopicPath()}を行う。<br><br>
	 * </li></ul>
	 * @param useStoredVo         保持VO利用フラグ(true：保持VOを使う、false：保持VOがあろうと新規VOを使う)
	 * @param useParametersMapper マッピングフラグ(true：パラメータをVOにマッピングする、false：マッピングしない)
	 * @return 取得したVOインスタンス
	 * @throws MospException パラメータがマッピングできなかった場合
	 */
	protected BaseVo prepareVo(boolean useStoredVo, boolean useParametersMapper) throws MospException {
		// VOインスタンス取得
		BaseVo baseVo = getSpecificVo();
		// 保存VO確認
		BaseVo storedVo = mospParams.getStoredVo(baseVo.getClassName());
		if (storedVo != null && useStoredVo) {
			baseVo = storedVo;
		}
		// パラメータマッピング
		if (useParametersMapper) {
			MospParametersMapper.mapParameters(baseVo, mospParams.getRequestParamsMap());
		}
		// VO及びフォワード先URLの設定
		setTemplateUrl();
		addBaseJsCssFiles();
		setViewPath(baseVo.getClassName());
		mospParams.setVo(baseVo);
		setTopicPath();
		return baseVo;
	}
	
	/**
	 * VO取得。<br><br>
	 * {@link #prepareVo()}で用いられる。<br>
	 * {@link BaseAction}を拡張したクラスでこのメソッドをオーバーライドすることで、
	 * {@link #prepareVo()}を用いて{@link BaseVo}を拡張した任意のVOインスタンスを
	 * 取得することができる。
	 * @return VOインスタンス
	 */
	protected BaseVo getSpecificVo() {
		return new PlatformVo();
	}
	
	/**
	 * MosPフレームワーク表示用ファイルパス設定
	 * @param className 対象VOクラス名
	 * <p>{@link BaseVo}を継承している対象VOの表示用ファイルパスを設定するメソッド。</p>
	 * <p>{@link BaseVo#BaseVo()}を呼び出した後、{@link PlatformVo#getClassName()}をパラメータにセットすることで、<br>
	 * 対象VOに直接JSP、CSS、JSのファイルパスをハードコードしなくても呼び出すことができる。<br>
	 * その代わり、ファイルの場所は必ず規約通りの場所にしなければならない。<br>
	 * もちろん、このメソッドを利用せず、従来の方法を利用することも可能である。
	 * </p>
	 * <p>
	 * ファイルの場所の規約は{@link ViewFileLocationUtility#ViewFileLocationUtility(String)}を参照すること。
	 * </p>
	 */
	protected void setViewPath(String className) {
		ViewFileLocationUtility location = new ViewFileLocationUtility(className);
		mospParams.setArticleUrl(location.getRetUrl());
		mospParams.addCssFile(location.getExtraCss());
		mospParams.addJsFile(location.getExtraJs());
	}
	
	/**
	 * テンプレートJSP用のURLを設定する。
	 */
	protected void setTemplateUrl() {
		// MosP設定情報からテンプレートJSPファイルパスを取得し設定
		mospParams.setUrl(mospParams.getApplicationProperty(APP_TEMPLATE_JSP));
		// ナビ(メニュー)URLを設定(パスワード変更画面等でnullにされることがある)
		mospParams.setNaviUrl(mospParams.getApplicationProperty(APP_NAVI_JSP));
	}
	
	/**
	 * 標準JavaScriptファイル及びCSSファイルを設定する。
	 */
	protected void addBaseJsCssFiles() {
		// MosP設定情報から標準ファイルパスを取得
		String[] baseCssFiles = mospParams.getApplicationProperties(APP_BASE_CSS_FILES);
		String[] baseJsFiles = mospParams.getApplicationProperties(APP_BASE_JS_FILES);
		// MosPパラメータに標準ファイルパスを設定
		for (String baseCssFile : baseCssFiles) {
			mospParams.addCssFile(baseCssFile);
		}
		for (String baseJsFile : baseJsFiles) {
			mospParams.addJsFile(baseJsFile);
		}
	}
	
	/**
	 * パンくずをリストにセットする。<br>
	 * 既にパンくずリストに操作対象が存在する場合は、VOを上書きする。<br>
	 * 操作対象が存在しない場合は、パンくずリストに追加する。<br>
	 */
	private void setTopicPath() {
		// セッションからパンくずリストを取得
		List<TopicPath> topicPathList = mospParams.getTopicPathList();
		// パンくず操作対象VOを取得
		BaseVo vo = mospParams.getVo();
		// 操作対象のパンくずリストにおけるインデックスを取得
		int idx = TopicPathUtility.getTopicPathIndex(topicPathList, vo.getClassName());
		// インデックス確認
		if (idx >= 0) {
			// パンくず操作対象より後のパンくずを除去
			removeAfterIndex(topicPathList, idx);
			// パンくず操作対象のVOを上書き
			topicPathList.get(idx).setVo(vo);
			return;
		}
		// パンくず追加(操作対象のパンくずが存在しない場合)
		topicPathList.add(createTopicPath());
	}
	
	/**
	 * パンくずを生成する。<br>
	 * @return パンくず
	 */
	private TopicPath createTopicPath() {
		// パンくず生成
		TopicPath topicPath = new TopicPath();
		// 対象VO取得
		BaseVo vo = mospParams.getVo();
		// ID(VOのName)、NAME、VOを設定
		topicPath.setId(vo.getClassName());
		topicPath.setName(NameUtility.getName(mospParams, vo.getClassName()));
		topicPath.setVo(vo);
		// メニューキー設定(メニューからの遷移時以外はnull)
		topicPath.setMenuKey(getTransferredMenuKey());
		// コマンド設定
		if (topicPathCommand != null && topicPathCommand.isEmpty() == false) {
			// パンくずリスト用コマンドを設定
			topicPath.setCommand(topicPathCommand);
		} else {
			// リクエストされたコマンドを設定
			topicPath.setCommand(mospParams.getCommand());
		}
		return topicPath;
	}
	
	/**
	 * リストから指定インデックスより後の要素を除去する。<br>
	 * @param list  削除対象リスト
	 * @param index インデックス
	 */
	protected void removeAfterIndex(List<?> list, int index) {
		for (int i = list.size() - 1; i > index; i--) {
			list.remove(i);
		}
	}
	
	/**
	 * MosPプラットフォーム用BeanHandlerを取得する。<br>
	 * @return MosPプラットフォーム用BeanHandler
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected PlatformBeanHandlerInterface platform() throws MospException {
		// MosPプラットフォーム用BeanHandler存在確認
		if (platform != null) {
			return platform;
		}
		// MosPプラットフォーム用BeanHandler取得
		platform = (PlatformBeanHandlerInterface)createHandler(PlatformBeanHandlerInterface.class);
		return platform;
	}
	
	/**
	 * MosPプラットフォーム参照用BeanHandlerを取得する。<br>
	 * @return MosPプラットフォーム参照用BeanHandler
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected ReferenceBeanHandlerInterface reference() throws MospException {
		// MosPプラットフォーム参照用BeanHandler存在確認
		if (platformReference != null) {
			return platformReference;
		}
		// MosPプラットフォーム参照用BeanHandler取得
		platformReference = (ReferenceBeanHandlerInterface)createHandler(ReferenceBeanHandlerInterface.class);
		return platformReference;
	}
	
	/**
	 * 追加業務ロジック処理を行う。<br>
	 * @param codeKey コードキー(追加業務ロジッククラス名)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public void doAdditionalLogic(String codeKey) throws MospException {
		// 追加業務ロジック毎に処理
		for (AdditionalLogicBeanInterface addonBean : getBeans(AdditionalLogicBeanInterface.class, codeKey)) {
			// 追加業務ロジック処理を実施
			addonBean.doAdditionalLogic();
		}
	}
	
	/**
	 * 追加業務ロジック処理を行う。<br>
	 * <br>
	 * 追加業務ロジックの実行判定を返す。<br>
	 * 通常処理を行うかどうかのチェックの際に用いる想定。<br>
	 * ある条件により追加業務ロジックを実行せず、通常の処理のみ行いたい場合などに用いる。<br>
	 * <br>
	 * @param codeKey コードキー(追加業務ロジッククラス名)
	 * @param objects 追加引数用
	 * @return 追加業務ロジック処理実行判定(true：実行した場合)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public boolean doAdditionalLogic(String codeKey, Object... objects) throws MospException {
		// 追加業務ロジック処理実行判定を準備
		boolean isAdditionalLogicExecution = false;
		// 追加業務ロジック毎に処理
		for (AdditionalLogicBeanInterface addonBean : getBeans(AdditionalLogicBeanInterface.class, codeKey)) {
			// 追加業務ロジック処理を実施(一つでも実行されれば追加業務ロジックの実行判定はtrue)
			isAdditionalLogicExecution = addonBean.doAdditionalLogic(objects) || isAdditionalLogicExecution;
		}
		// 追加業務ロジック処理実行判定を取得
		return isAdditionalLogicExecution;
	}
	
	/**
	 * 業務ロジック処理を行うリストを取得する。<br>
	 * @param cls     クラス
	 * @param codeKey コードキー
	 * @return 業務ロジック処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected <T extends BaseBeanInterface> List<T> getBeans(Class<T> cls, String codeKey) throws MospException {
		// 業務ロジックのリストを準備
		List<T> addonBeans = new ArrayList<T>();
		// 業務ロジック処理を配列毎に処理
		for (String[] addon : getCodeArray(codeKey, false)) {
			// 業務ロジック処理を取得
			String addonBean = addon[0];
			// 業務ロジック処理が設定されていない場合
			if (MospUtility.isEmpty(addonBean)) {
				// 次の処理へ
				continue;
			}
			// 業務ロジック処理を取得
			T bean = platform().createBean(cls, addonBean);
			// 業務ロジック処理のリストに値を追加
			addonBeans.add(bean);
		}
		// 業務ロジック処理のリストを取得
		return addonBeans;
	}
	
	/**
	 * ページ繰り設定。<br>
	 * @param command ページ繰りコマンド
	 * @param dataPerPage 1ページ当たりの表示件数
	 */
	protected void setPageInfo(String command, int dataPerPage) {
		// VO準備
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		vo.setPageCommand(command);
		vo.setDataPerPage(dataPerPage);
		vo.setSelectIndex(String.valueOf(1));
	}
	
	/**
	 * リスト長(一画面表示件数)取得。<br>
	 * @return リスト長
	 */
	protected int getListLength() {
		// 設定ファイルからリスト長を取得
		return mospParams.getApplicationProperty(APP_LIST_LENGTH, 0);
	}
	
	/**
	 * リストをソートし、1ページ目のリストを取得する。<br>
	 * @param sortKey ソートキー(比較クラス名)
	 * @return ソート後1ページ目分のリスト
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	public List<? extends BaseDtoInterface> sortList(String sortKey) throws MospException {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// ソートキー確認
		if (vo.getComparatorName().equals(sortKey)) {
			// 昇順降順フラグ設定
			if (vo.isAscending()) {
				vo.setAscending(false);
			} else {
				vo.setAscending(true);
			}
		} else {
			vo.setAscending(false);
		}
		// ソートキー確認
		if (sortKey != null) {
			// ソートキーをVOに設定
			vo.setComparatorName(sortKey);
		}
		// 比較クラスインスタンス取得
		Comparator<Object> comp = InstanceFactory.loadComparator(vo.getComparatorName());
		// ソート
		if (vo.isAscending()) {
			Collections.sort(vo.getList(), Collections.reverseOrder(comp));
		} else {
			Collections.sort(vo.getList(), comp);
		}
		// 1ページ目のリストを取得
		return getFirstPageList();
	}
	
	/**
	 * VOに設定されているキーで再ソートし、1ページ目のリストを取得する。<br>
	 * <br>
	 * @return ソート後1ページ目分のリスト
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	public List<? extends BaseDtoInterface> reSortList() throws MospException {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// ソートキー取得
		String sortKey = vo.getComparatorName();
		// 昇順降順フラグを逆に設定
		if (vo.isAscending()) {
			vo.setAscending(false);
		} else {
			vo.setAscending(true);
		}
		// 再ソートし1ページ目のリストを取得
		return sortList(sortKey);
	}
	
	/**
	 * 1ページ目のリストを取得する。<br>
	 * @return 1ページ目分のリスト
	 */
	protected List<? extends BaseDtoInterface> getFirstPageList() {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// 選択ページ番号設定
		vo.setSelectIndex(String.valueOf(1));
		// リスト取得
		return pageList();
	}
	
	/**
	 * 選択ページのリストを取得する。<br>
	 * @return 選択ページ分のリスト
	 */
	protected List<? extends BaseDtoInterface> pageList() {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		int offset = (Integer.parseInt(vo.getSelectIndex()) - 1) * vo.getDataPerPage();
		List<BaseDtoInterface> list = new ArrayList<BaseDtoInterface>();
		for (int i = offset; i < (offset + vo.getDataPerPage() < vo.getList().size() ? offset + vo.getDataPerPage()
				: vo.getList().size()); i++) {
			list.add(vo.getList().get(i));
		}
		return list;
	}
	
	/**
	 * 配列を取得する。<br>
	 * @return 配列
	 */
	protected BaseDtoInterface[] getArray() {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		return vo.getList().toArray(new BaseDtoInterface[0]);
	}
	
	/**
	 * 操作範囲の確認を行う。<br>
	 * 確認対象個人IDが参照範囲に含まれない場合、パンくずリストの最初の画面に遷移する。<br>
	 * JavaScriptで個人を指定してリクエストが投げられる場合、
	 * このメソッドを実行して操作権限があるかどうかを確認する必要がある。<br>
	 * @param personalId    確認対象個人ID
	 * @param targetDate    確認対象日
	 * @param operationType 操作範囲
	 * @throws MospException 確認対象個人IDが参照範囲に含まれない場合
	 */
	protected void checkRange(String personalId, Date targetDate, String operationType) throws MospException {
		// 範囲及び個人ID(社員コードに変換)を指定して検索
		HumanSearchBeanInterface bean = reference().humanSearch();
		bean.setTargetDate(targetDate);
		bean.setEmployeeCode(reference().human().getEmployeeCode(personalId, targetDate));
		bean.setEmployeeCodeType(PlatformConst.SEARCH_EXACT_MATCH);
		bean.setOperationType(operationType);
		List<HumanDtoInterface> list = bean.search();
		// 検索結果確認
		if (list.size() == 1) {
			// 操作可能
			return;
		}
		// パンくずリストからコマンドを取得
		mospParams.setNextCommand(mospParams.getTopicPathList().get(0).getCommand());
		// メッセージ設定
		mospParams.addErrorMessage(ExceptionConst.EX_NO_AUTHORITY);
		// 例外発行
		throw new MospException(ExceptionConst.EX_NO_AUTHORITY);
	}
	
	/**
	 * コマンドが不正な場合のMosP例外を発行する。<br>
	 * @throws MospException コマンドが不正な場合
	 */
	protected void throwInvalidCommandException() throws MospException {
		String[] rep = { getClass().getName(), mospParams.getCommand() };
		mospParams.setErrorViewUrl();
		throw new MospException(ExceptionConst.EX_INVALID_COMMAND, rep);
	}
	
	/**
	 * システム日付を取得する。<br>
	 * @return システム日付
	 */
	protected Date getSystemDate() {
		return DateUtility.getSystemDate();
	}
	
	/**
	 * 日付オブジェクトを取得する(String→Date)。
	 * @param date 日付文字列(yyyy/MM/dd)
	 * @return 日付
	 */
	protected Date getDate(String date) {
		return DateUtility.getDate(date);
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd)
	 */
	protected String getStringDate(Date date) {
		return DateUtility.getStringDate(date);
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd(E))
	 */
	protected String getStringDateAndDay(Date date) {
		return DateUtility.getStringDateAndDay(date);
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(hh:mm)
	 */
	protected String getStringTime(Date date) {
		return DateUtility.getStringTime(date);
	}
	
	/**
	 * 年を取得する。<br>
	 * @param date 日付
	 * @return 年
	 */
	protected String getStringYear(Date date) {
		return DateUtility.getStringYear(date);
	}
	
	/**
	 * 月を取得する。<br>
	 * @param date 日付
	 * @return 月
	 */
	protected String getStringMonth(Date date) {
		return DateUtility.getStringMonth(date);
	}
	
	/**
	 * 日を取得する。<br>
	 * @param date 日付
	 * @return 日
	 */
	protected String getStringDay(Date date) {
		return DateUtility.getStringDay(date);
	}
	
	/**
	 * 時間を取得する。<br>
	 * @param date 日付
	 * @return 時間
	 */
	protected String getStringHour(Date date) {
		return DateUtility.getStringHour(date);
	}
	
	/**
	 * 分を取得する。<br>
	 * @param date 日付
	 * @return 分
	 */
	protected String getStringMinute(Date date) {
		return DateUtility.getStringMinute(date);
	}
	
	/**
	 * 日付オブジェクトを取得する(String→Date)。<br>
	 * 日付オブジェクトの取得に失敗した場合は、
	 * {@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 日付
	 */
	protected Date getDate(String year, String month, String day) {
		try {
			return DateUtility.getDate(year, month, day);
		} catch (Throwable e) {
			PfMessageUtility.addErrorCheckDate(mospParams);
			return null;
		}
	}
	
	/**
	 * 日付オブジェクト(時間)を取得する。<br>
	 * 時、分、日がいずれも空白の場合、nullを返す。<br>
	 * 引数にnullが含まれる場合は、例外を発行する。<br>
	 * @param hour   時間
	 * @param minute 分
	 * @return 日付オブジェクト
	 */
	protected Date getTime(String hour, String minute) {
		try {
			return DateUtility.getTime(hour, minute);
		} catch (Throwable e) {
			PfMessageUtility.addErrorCheckTime(mospParams);
			return null;
		}
	}
	
	/**
	 * 数値を取得する(String→long)。<br>
	 * 数値の取得に失敗した場合は、{@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param value 値(String)
	 * @return 値(long)
	 */
	protected long getLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Throwable e) {
			PfMessageUtility.addErrorCheckNumeric(mospParams);
			return 0;
		}
	}
	
	/**
	 * 数値を取得する(String→int)。<br>
	 * 数値の取得に失敗した場合は、{@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param value 値(String)
	 * @return 値(int)
	 */
	protected int getInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Throwable e) {
			PfMessageUtility.addErrorCheckNumeric(mospParams);
			return 0;
		}
	}
	
	/**
	 * 数値を取得する(String→double)。<br>
	 * 数値の取得に失敗した場合は、{@link #mospParams}にエラーメッセージを追加する。<br>
	 * 
	 * @param value 値(String)
	 * @return 値(int)
	 */
	protected double getDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Throwable e) {
			PfMessageUtility.addErrorCheckNumeric(mospParams);
			return 0;
		}
	}
	
	/**
	 * コード配列を取得する。<br>
	 * @param codeKey コードキー
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return コード配列
	 */
	protected String[][] getCodeArray(String codeKey, boolean needBlank) {
		// コード配列を取得
		return mospParams.getProperties().getCodeArray(codeKey, needBlank);
	}
	
	/**
	 * コード名称を取得する。<br>
	 * @param code    コード
	 * @param codeKey コードキー
	 * @return コード名称
	 */
	protected String getCodeName(String code, String codeKey) {
		// コード名称を取得
		return MospUtility.getCodeName(mospParams, code, codeKey);
	}
	
	/**
	 * コード名称を取得する。<br>
	 * @param code    コード
	 * @param codeKey コードキー
	 * @return コード名称
	 */
	protected String getCodeName(int code, String codeKey) {
		// コード名称を取得
		return MospUtility.getCodeName(mospParams, code, codeKey);
	}
	
	/**
	 * コード項目キーを取得する。<br>
	 * @param code    コード
	 * @param codeKey コードキー
	 * @return コード項目キー
	 */
	protected String getCodeItemCode(String code, String codeKey) {
		// コード名称を取得
		return MospUtility.getCodeItemCode(code, getCodeArray(codeKey, false));
	}
	
	/**
	 * コード項目キーを取得する。<br>
	 * @param code    コード
	 * @param codeKey コードキー
	 * @return コード項目キー
	 */
	protected String getCodeItemCode(int code, String codeKey) {
		// コード名称を取得
		return getCodeItemCode(String.valueOf(code), codeKey);
	}
	
	/**
	 * 元の配列に新たな配列を追加する。
	 * @param aryItem 元にある配列
	 * @param aryAddItem 追加したい配列
	 * @return 元にある配列 + 追加したい配列
	 */
	protected String[][] addArrayString(String[][] aryItem, String[][] aryAddItem) {
		// 二次元配列準備
		String[][] lblExportItem = new String[aryItem.length + aryAddItem.length][2];
		// カウント数準備
		int i = 0;
		// 配列最大値まで追加
		while (i < lblExportItem.length) {
			// 元にある配列の最大値以下の場合
			if (i < aryItem.length) {
				// 値をつめる
				lblExportItem[i] = aryItem[i];
			} else {
				// それ以外の場合
				// 追加したい配列を詰める
				lblExportItem[i] = aryAddItem[i - aryItem.length];
			}
			// カウント
			i++;
		}
		// 追加済みの配列
		return lblExportItem;
	}
	
	/**
	 * 無効フラグ名称を取得する。<br>
	 * @param inactivateFlag 無効フラグ
	 * @return 無効フラグ名称
	 */
	protected String getInactivateFlagName(int inactivateFlag) {
		return getCodeName(inactivateFlag, PlatformConst.CODE_KEY_INACTIVATE_FLAG);
	}
	
	/**
	 * 対象DTOを登録したユーザのユーザ名を取得する。<br>
	 * @param dto 対象DTO
	 * @return 登録ユーザ名
	 * @throws MospException ユーザ情報の取得に失敗した場合
	 */
	protected String getInsertUserName(BaseDtoInterface dto) throws MospException {
		// ユーザ情報取得及び確認
		UserMasterDtoInterface userDto = reference().user().getUserInfo(dto.getInsertUser(), dto.getInsertDate());
		if (userDto == null) {
			return MospConst.STR_EMPTY;
		}
		// 人事情報取得及び確認
		HumanDtoInterface humanDto = getHumanInfo(userDto.getPersonalId(), dto.getInsertDate());
		if (humanDto == null) {
			return MospConst.STR_EMPTY;
		}
		return MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
	}
	
	/**
	 * 人事情報を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 人事情報
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected HumanDtoInterface getHumanInfo(String personalId, Date targetDate) throws MospException {
		// 人事情報取得及び確認
		return reference().human().getHumanInfo(personalId, targetDate);
	}
	
	/**
	 * 苗字と名前を受け取りスペースを挿入した名前を返す。<br>
	 * @param lastName 姓
	 * @param firstName 名
	 * @return スペースを挿入したフルネーム
	 */
	public String getLastFirstName(String lastName, String firstName) {
		return MospUtility.getHumansName(firstName, lastName);
	}
	
	/**
	 * 個人IDから社員名を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 社員名
	 * @throws MospException 例外発生時
	 */
	protected String getEmployeeName(String personalId, Date targetDate) throws MospException {
		// 取得したユーザIDとシステム日付から個人IDを取得する
		HumanDtoInterface humanDto = getHumanInfo(personalId, targetDate);
		if (humanDto == null) {
			// 取得したユーザIDに該当する人事マスタのデータがNULLなら処理終了
			PfMessageUtility.addErrorEmployeeNotExist(mospParams);
			return MospConst.STR_EMPTY;
		}
		// 社員名を返す
		return getLastFirstName(humanDto.getLastName(), humanDto.getFirstName());
	}
	
	/**
	 * 個人IDからシステム日付時点の社員名を取得する。<br>
	 * @param personalId 個人ID
	 * @return システム日付時点の社員名
	 * @throws MospException 例外発生時
	 */
	protected String getEmployeeName(String personalId) throws MospException {
		// 社員名を取得
		return getEmployeeName(personalId, getSystemDate());
	}
	
	/**
	 * 個人IDから社員コードを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 社員コード
	 * @throws MospException 例外発生時
	 */
	protected String getEmployeeCode(String personalId, Date targetDate) throws MospException {
		// 取得したユーザIDとシステム日付から個人IDを取得する
		HumanDtoInterface humanDto = getHumanInfo(personalId, targetDate);
		if (humanDto == null) {
			// 取得したユーザIDに該当する人事マスタのデータがNULLなら処理終了
			PfMessageUtility.addErrorEmployeeNotExist(mospParams);
			return MospConst.STR_EMPTY;
		}
		// 社員コードを返す
		return humanDto.getEmployeeCode();
	}
	
	/**
	 * 個人IDからシステム日付時点の社員コードを取得する。<br>
	 * @param personalId 個人ID
	 * @return 社員コード
	 * @throws MospException 例外発生時
	 */
	protected String getEmployeeCode(String personalId) throws MospException {
		// 社員コードを取得
		return getEmployeeCode(personalId, getSystemDate());
	}
	
	/**
	 * 有効日編集中のプルダウンを取得する。<br>
	 * @return 有効日編集中プルダウン
	 */
	protected String[][] getInputActivateDatePulldown() {
		return PlatformUtility.getInputActivateDatePulldown(mospParams);
	}
	
	/**
	 * 開始日編集中のプルダウンを取得する。<br>
	 * @return 開始日編集中プルダウン
	 */
	protected String[][] getInputStartDatePulldown() {
		String[][] aryPulldown = { { MospConst.STR_EMPTY, PfNameUtility.inputStartDate(mospParams) } };
		return aryPulldown;
	}
	
	/**
	 * コード検索中のプルダウンを取得する。<br>
	 * @return コード検索中プルダウン
	 */
	protected String[][] getSearchCodePulldown() {
		String[][] aryPulldown = { { MospConst.STR_EMPTY, PfNameUtility.searchCode(mospParams) } };
		return aryPulldown;
	}
	
	/**
	 * 検索中のプルダウンを取得する。<br>
	 * @return コード検索中プルダウン
	 */
	protected String[][] getPleaseSearchPulldown() {
		String[][] aryPulldown = { { MospConst.STR_EMPTY, PfNameUtility.searchPlease(mospParams) } };
		return aryPulldown;
	}
	
	/**
	 * 文字列配列からレコード識別ID配列を作成する。<br>
	 * 但し、空文字列は除く。<br>
	 * @param aryString 文字列配列
	 * @return レコード識別ID配列
	 */
	protected long[] getIdArray(String[] aryString) {
		// 文字列配列確認
		if (aryString == null) {
			return new long[0];
		}
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDリスト作成
		for (String id : aryString) {
			// ID確認
			if (id == null || id.isEmpty()) {
				continue;
			}
			// レコード識別ID確認
			if (getLong(id) == 0) {
				continue;
			}
			// リストに追加
			list.add(id);
		}
		// レコード識別ID配列作成
		long[] aryLong = new long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			aryLong[i] = getLong(list.get(i));
		}
		return aryLong;
	}
	
	/**
	 * 選択レコード識別ID配列を取得する。<br>
	 * @return 選択レコード識別ID配列
	 */
	protected long[] getRecordIdArray() {
		// VOを準備
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 選択インデックス配列を取得
		int[] indexArray = getIndexArray(vo.getCkbSelect());
		// 全レコード識別ID配列を取得
		long[] aryCkbRecordId = vo.getAryCkbRecordId();
		// 選択レコード識別ID配列を準備
		long[] recordIdArray = new long[indexArray.length];
		// インデックス毎に処理
		for (int i = 0; i < indexArray.length; i++) {
			// レコード識別IDを追加
			recordIdArray[i] = aryCkbRecordId[indexArray[i]];
		}
		// 選択レコード識別ID配列を取得
		return recordIdArray;
	}
	
	/**
	 * 文字列配列からインデックス配列を作成する。<br>
	 * @param aryString 文字列配列
	 * @return インデックス配列
	 */
	protected int[] getIndexArray(String[] aryString) {
		// 文字列配列確認
		if (aryString == null) {
			return new int[0];
		}
		// インデックス配列作成
		int[] aryInt = new int[aryString.length];
		for (int i = 0; i < aryString.length; i++) {
			aryInt[i] = getInt(aryString[i]);
		}
		return aryInt;
	}
	
	/**
	 * インデックス配列内に対象インデックスが含まれるかを確認する。<br>
	 * @param target   対象インデックス
	 * @param aryIndex インデックス配列
	 * @return 確認結果(true：含まれる、false：含まれない)
	 */
	protected boolean isIndexed(int target, int[] aryIndex) {
		for (int idx : aryIndex) {
			if (target == idx) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 文字列配列からレコード識別ID配列を作成する。<br>
	 * @param aryIndex 対象index配列
	 * @param aryId    レコード識別ID配列(String)
	 * @return レコード識別ID配列
	 */
	protected long[] getIdArray(int[] aryIndex, String[] aryId) {
		// 文字列配列確認
		if (aryIndex == null) {
			return new long[0];
		}
		// 配列準備
		String[] idArray = new String[aryIndex.length];
		// 値設定
		for (int i = 0; i < aryIndex.length; i++) {
			idArray[i] = aryId[aryIndex[i]];
		}
		return getIdArray(idArray);
	}
	
	/**
	 * VOに設定されているリストから選択された一覧情報を取得する。<br>
	 * <br>
	 * @param idx インデックス
	 * @return 選択された一覧情報
	 */
	protected BaseDtoInterface getSelectedListDto(String idx) {
		return getSelectedListDto(getInt(idx));
	}
	
	/**
	 * VOに設定されているリストから選択された一覧情報を取得する。<br>
	 * <br>
	 * @param idx インデックス
	 * @return 選択された一覧情報
	 */
	protected BaseDtoInterface getSelectedListDto(int idx) {
		// VO準備
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// 一覧の開始位置を取得
		int offset = (getInt(vo.getSelectIndex()) - 1) * vo.getDataPerPage();
		// 選択インデックスを加算
		int index = offset + idx;
		// 選択インデックスの一覧情報を取得
		return vo.getList().get(index);
	}
	
	/**
	 * VOに設定されているリストから選択された一覧情報リストを取得する。<br>
	 * <br>
	 * @return 選択された一覧情報リスト
	 */
	protected <T> List<T> getSelectedListDtos() {
		// VOを準備
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// 一覧情報リストを準備
		List<BaseDtoInterface> list = new ArrayList<BaseDtoInterface>();
		// 一覧チェック項目毎に処理
		for (String idx : vo.getCkbSelect()) {
			// VOに設定されているリストから選択された一覧情報を取得し追加
			list.add(getSelectedListDto(idx));
		}
		// 一覧情報リストを取得
		return PlatformUtility.castList(list);
	}
	
	/**
	 * VOに設定されている一覧選択情報を初期化する。<br>
	 */
	protected void initCkbSelect() {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// 一覧選択情報を初期化
		vo.setCkbSelect(new String[0]);
	}
	
	/**
	 * VOに設定されているリストから選択された個人IDを取得する。<br>
	 * <br>
	 * {@link PlatformAction#getSelectedListDto(String)}<br>
	 * で一覧情報を取得し、一覧情報から個人IDを取得する。<br>
	 * <br>
	 * 但し、対象となる一覧情報は、
	 * {@link PersonalIdDtoInterface}<br>
	 * を実装していなくてはならない。<br>
	 * <br>
	 * @param idx インデックス
	 * @return 選択された個人ID
	 */
	protected String getSelectedPersonalId(int idx) {
		// VOに設定されているリストから選択された一覧情報を取得
		PersonalIdDtoInterface dto = (PersonalIdDtoInterface)getSelectedListDto(idx);
		// 個人IDを取得
		return dto.getPersonalId();
	}
	
	/**
	 * VOに設定されているリストから選択された個人IDを取得する。<br>
	 * <br>
	 * @param idx インデックス
	 * @return 選択された個人ID
	 */
	protected String getSelectedPersonalId(String idx) {
		// VOに設定されているリストから選択された個人IDを取得
		return getSelectedPersonalId(getInt(idx));
	}
	
	/**
	 * VOに設定されているリストから選択された個人ID配列を取得する。<br>
	 * <br>
	 * @param indexes インデックス配列
	 * @return 選択された個人ID配列
	 */
	protected String[] getSelectedPersonalIds(String[] indexes) {
		// 個人IDリストを準備
		List<String> list = new ArrayList<String>();
		// インデックス毎に処理
		for (String idx : indexes) {
			// 個人IDを取得
			list.add(getSelectedPersonalId(idx));
		}
		// 個人ID配列を取得
		return MospUtility.toArray(list);
	}
	
	/**
	 * 選択データの存在確認を行う。<br>
	 * 選択データが存在しなかった場合は、ポータル画面に戻される。<br>
	 * 一覧からデータを選択し画面遷移する際等に用いる。<br>
	 * @param obj 確認対象オブジェクト
	 * @throws MospException 確認対象オブジェクトがNULLである場合
	 */
	protected void checkSelectedDataExist(Object obj) throws MospException {
		// 対象オブジェクト確認
		if (obj != null) {
			return;
		}
		// エラーメッセージを設定
		PfMessageUtility.addErrorNoItem(mospParams, PfNameUtility.information(mospParams));
		// 連続実行コマンド設定(ポータル画面へ)
		mospParams.setNextCommand(mospParams.getApplicationProperty(APP_COMMAND_PORTAL));
		// 例外発行
		throw new MospException(ExceptionConst.EX_NO_DATA);
	}
	
	/**
	 * 画面表示時にスクロールさせるHTML要素のIDを設定する。<br>
	 * 画面表示時に、JavaScriptの変数として宣言される。<br>
	 * @param target 画面表示時にスクロールさせるHTML要素のID
	 */
	protected void setJsScrollTo(String target) {
		// MosP汎用パラメータに設定
		mospParams.addGeneralParam(MGP_JS_SCROLL_TO, target);
	}
	
	/**
	 * 有効日モードを設定する。<br>
	 * @param modeActivateDate 有効日モード
	 */
	protected void setModeActivateDate(String modeActivateDate) {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// 有効日モード設定
		vo.setModeActivateDate(modeActivateDate);
	}
	
	/**
	 * 承認者用プルダウンの作成。<br>
	 * @param personalId 個人ID
	 * @param date 有効日
	 * @param workflowType ワークフロー区分
	 * @return 結果可否
	 * @throws MospException 例外処理発生時
	 */
	protected boolean setApproverPullDown(String personalId, Date date, int workflowType) throws MospException {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// 承認者設定用プルダウン取得
		String[][][] aryApproverInfo = reference().workflowIntegrate().getArrayForApproverSetting(personalId, date,
				workflowType);
		// 承認者設定用プルダウン確認
		if (aryApproverInfo.length == 0) {
			// 承認者不在メッセージ設定
			addApproverNotExistMessage(workflowType);
		}
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		// 承認者設定用プルダウン設定
		vo.setAryApproverInfo(aryApproverInfo);
		// 承認者タイトルラベル設定
		String[] aryPltLblApproverSetting = new String[aryApproverInfo.length];
		for (int i = 0; i < aryPltLblApproverSetting.length; i++) {
			aryPltLblApproverSetting[i] = PfNameUtility.stagedApprover(mospParams, i + 1);
		}
		vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
		String[] aryApproverSetting = { vo.getPltApproverSetting1(), vo.getPltApproverSetting2(),
			vo.getPltApproverSetting3(), vo.getPltApproverSetting4(), vo.getPltApproverSetting5(),
			vo.getPltApproverSetting6(), vo.getPltApproverSetting7(), vo.getPltApproverSetting8(),
			vo.getPltApproverSetting9(), vo.getPltApproverSetting10() };
		vo.setAryPltApproverSetting(aryApproverSetting);
		String[] pltApproverSetting = { "pltApproverSetting1", "pltApproverSetting2", "pltApproverSetting3",
			"pltApproverSetting4", "pltApproverSetting5", "pltApproverSetting6", "pltApproverSetting7",
			"pltApproverSetting8", "pltApproverSetting9", "pltApproverSetting10" };
		vo.setPltApproverSetting(pltApproverSetting);
		return true;
	}
	
	/**
	 * 承認者が存在しない場合のメッセージを設定する。<br>
	 * @param workflowType ワークフロー区分
	 */
	protected void addApproverNotExistMessage(int workflowType) {
		String workflowName = "";
		// 勤怠の場合
		if (workflowType == PlatformConst.WORKFLOW_TYPE_TIME) {
			workflowName = PfNameUtility.workManageAbbr(mospParams);
		}
		// 人事の場合
		else if (workflowType == PlatformConst.WORKFLOW_TYPE_HUMAN) {
			workflowName = PfNameUtility.humanManageAbbr(mospParams);
		}
		// エラーメッセージを設定
		String object = new StringBuilder(workflowName).append(PfNameUtility.approver(mospParams)).toString();
		PfMessageUtility.addErrorCnaNotGet(mospParams, object, PfNameUtility.applicationDate(mospParams));
	}
	
	/**
	 * 検索条件を確認する。<br>
	 * @param condition 検索条件
	 */
	protected void checkSearchCondition(String... condition) {
		if (isSearchConditionRequired() == false) {
			// 検索条件が必須でない場合
			return;
		}
		// 検索条件が必須の場合
		if (MospUtility.isAllEmpty(condition) == false) {
			// 検索条件が1つでもある場合
			return;
		}
		// 検索条件がない場合
		PfMessageUtility.addErrorSearchCondition(mospParams);
	}
	
	/**
	 * 検索条件必須設定を確認する。<br>
	 * @return 検索条件が必須の場合true、そうでない場合false
	 */
	protected boolean isSearchConditionRequired() {
		return mospParams.getApplicationPropertyBool(PlatformConst.APP_SEARCH_CONDITION_REQUIRED);
	}
	
	/**
	 * 日付文字列から日付を取得する。<br>
	 * <br>
	 * 日付文字列が空白である場合、nullを返す。<br>
	 * 日付の解析に失敗した場合、MosP処理情報にエラーメッセージを設定する。<br>
	 * <br>
	 * @param strDate   日付文字列
	 * @param fieldName フィールド名称(エラーメッセージ用)
	 * @param row       行インデックス
	 * @return 日付
	 */
	protected Date getDateFromString(String strDate, String fieldName, Integer row) {
		// 日付文字列が空白である場合
		if (strDate == null || strDate.isEmpty()) {
			return null;
		}
		// 日付取得(yyyy/MM/dd)
		Date date = DateUtility.getVariousDate(strDate);
		// 日付が取得できた場合
		if (date != null) {
			return date;
		}
		// 日付が取得できなかった場合
		// エラーメッセージを設定
		PfMessageUtility.addErrorDateInvalid(mospParams, fieldName, row);
		return date;
	}
	
	/**
	 * ドキュメントルートを取得する。<br>
	 * @return ドキュメントルート
	 */
	protected String getDocBase() {
		return mospParams.getApplicationProperty(MospConst.APP_DOCBASE);
	}
	
	/**
	 * リクエストされた譲渡インデックスを取得する。
	 * @return 譲渡インデックス
	 */
	protected int getTransferredIndex() {
		return getInt(mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_INDEX));
	}
	
	/**
	 * 対象個人IDを設定する。<br>
	 * 画面遷移用のパラメータをMosP処理情報に設定する。<br>
	 * @param targetPersonalId 対象個人ID
	 */
	protected void setTargetPersonalId(String targetPersonalId) {
		mospParams.addGeneralParam(PlatformConst.PRM_TARGET_PERSONAL_ID, targetPersonalId);
	}
	
	/**
	 * 対象個人IDを取得する。<br>
	 * 画面遷移用のパラメータをMosP処理情報から取得する。<br>
	 * @return 対象個人ID
	 */
	protected String getTargetPersonalId() {
		return (String)mospParams.getGeneralParam(PlatformConst.PRM_TARGET_PERSONAL_ID);
	}
	
	/**
	 * 対象日を設定する。<br>
	 * 画面遷移用のパラメータをMosP処理情報に設定する。<br>
	 * @param targetDate 対象日
	 */
	protected void setTargetDate(Date targetDate) {
		mospParams.addGeneralParam(PlatformConst.PRM_TARGET_DATE, targetDate);
	}
	
	/**
	 * 対象日を取得する。<br>
	 * 画面遷移用のパラメータをMosP処理情報から取得する。<br>
	 * @return 対象日
	 */
	protected Date getTargetDate() {
		return (Date)mospParams.getGeneralParam(PlatformConst.PRM_TARGET_DATE);
	}
	
	/**
	 * リクエストされたメニューキーを取得する。<br>
	 * メニューキーは、メニューをクリックした時のみリクエストされる。<br>
	 * 範囲設定、パンくずリセット等に利用される。<br>
	 * @return メニューキー
	 */
	protected String getTransferredMenuKey() {
		String key = mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_MENU_KEY);
		if (key != null && key.equals(MEN_MAIN_TOP)) {
			key = null;
		}
		return key;
	}
	
	/**
	 * リクエストされた譲渡汎用コードを取得する。
	 * @return 譲渡汎用コード
	 */
	protected String getTransferredCode() {
		return mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_CODE);
	}
	
	/**
	* リクエストされた譲渡ワークフロー番号を取得する。
	* @return 譲渡ワークフロー番号
	*/
	protected String getTransferredWorkflow() {
		return mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_WORKFLOW);
	}
	
	/**
	* リクエストされた譲渡レコード識別IDを取得する。
	* @return 譲渡レコード識別ID
	*/
	protected String getTransferredRecordId() {
		return mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_RECORD_ID);
	}
	
	/**
	 * リクエストされた譲渡有効日を取得する。
	 * @return 譲渡有効日
	 */
	protected String getTransferredActivateDate() {
		return mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE);
	}
	
	/**
	 * リクエストされた譲渡汎用区分を取得する。
	 * @return 譲渡汎用区分
	 */
	protected String getTransferredType() {
		return mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_TYPE);
	}
	
	/**
	 * リクエストされた譲渡汎用コマンドを取得する。<br>
	 * 遷移先から戻るためのコマンド等を受け取る場合に用いる。<br>
	 * @return 譲渡汎用コマンド
	 */
	protected String getTransferredCommand() {
		return mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_COMMAND);
	}
	
	/**
	 * リクエストされた譲渡ソートキーを取得する。
	 * @return 譲渡ソートキー
	 */
	protected String getTransferredSortKey() {
		return mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_SORT_KEY);
	}
	
	/**
	 * リクエストされた譲渡Actionクラス名を取得する。
	 * @return 譲渡Actionクラス名
	 */
	protected String getTransferredAction() {
		return mospParams.getRequestParam(PlatformConst.PRM_TRANSFERRED_ACTION);
	}
	
}
