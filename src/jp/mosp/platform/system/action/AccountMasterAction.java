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
package jp.mosp.platform.system.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.bean.system.UserMasterSearchBeanInterface;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.comparator.system.SectionMasterClassRouteComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.AccountInfoDtoInterface;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.system.vo.AccountMasterVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * アカウントマスタ情報の登録・編集・削除を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li><li>
 * {@link #CMD_SET_UPDATE_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SET_EMPLOYEE}
 * </li><li>
 * {@link #CMD_PASS_INIT}
 * </li></ul>
 */
public class AccountMasterAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。編集テーブルは新規登録モードにする。<br>
	 */
	public static final String	CMD_SHOW						= "PF2400";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * ロール情報についてテキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なロールのプルダウンリストを作成し、検索項目にセットする。<br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE			= "PF2401";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿ったアカウント情報の一覧表示を行う。一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH						= "PF2402";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT						= "PF2404";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE						= "PF2405";
	
	/**
	 * 登録コマンド。<br>
	 * 編集モードで判断し、新規登録、履歴追加、履歴更新を行う。<br>
	 * <br>
	 * <ul><li>
	 * 新規登録処理。<br>
	 * 編集テーブルに入力されている内容をアカウントマスタテーブルに登録する。<br>
	 * 入力チェックを行った際にユーザIDが登録済みのレコードのものと重複している場合は
	 * エラーメッセージにて通知する。<br>
	 * </li><li>
	 * 履歴追加処理。<br>
	 * 編集テーブルに入力されている内容を基に選択したレコードの新たな有効日を持った
	 * 履歴を追加する。<br>
	 * </li><li>
	 * 履歴更新処理。<br>
	 * 編集テーブルに入力されている内容を基に選択したレコードの編集を行う。<br>
	 * </li></ul>
	 */
	public static final String	CMD_REGIST						= "PF2407";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードを削除するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない状態で削除ボタンがクリックされた場合はエラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_DELETE						= "PF2409";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 画面上部編集テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンク・
	 * 履歴編集モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE					= "PF2411";
	
	/**
	 * 履歴編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を画面上部編集テーブルの各入力欄にそれぞれ表示させる。<br>
	 * 登録ボタンクリック時のコマンドを更新コマンドに切り替える。<br>
	 * 有効日の年月日、ユーザID、社員コードの入力欄を読取専用にする。<br>
	 * 編集テーブルヘッダに新規登録モード切替リンク・履歴追加モード切替リンクを表示させる。<br>
	 */
	public static final String	CMD_EDIT_MODE					= "PF2412";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE					= "PF2413";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE				= "PF2414";
	
	/**
	 * 一括更新テーブル有効日決定コマンド。<br>
	 * <br>
	 * 一括更新テーブル内のテキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なロールのプルダウンリストを作成し、
	 * 一括更新テーブル内のプルダウンにセットする。有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_UPDATE_ACTIVATION_DATE	= "PF2415";
	
	/**
	 * 一括更新区分設定コマンド。<br>
	 * <br>
	 * 一括更新の更新対象を変更する。<br>
	 * 有効日決定が必要な更新対象を選択した場合、有効日は編集状態にする。<br>
	 */
	public static final String	CMD_SET_BATCH_UPDATE_TYPE		= "PF2418";
	
	/**
	 * 社員決定コマンド。<br>
	 * <br>
	 * 社員決定コマンド。社員コード入力欄に入力した情報で検索を行い、
	 * 取得した社員名を社員名ラベルに表示する。<br>
	 * 以降、この社員が編集や履歴追加といったものを行う該当社員となるため、情報を保持しておく。<br>
	 */
	public static final String	CMD_SET_EMPLOYEE				= "PF2416";
	
	/**
	 * パスワード初期化コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードのパスワードを初期化するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない状態でパスワード初期化ボタンが
	 * クリックされた場合はエラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_PASS_INIT					= "PF2417";
	
	/**
	 * 一括更新区分(ロール)。
	 */
	public static final String	TYPE_BATCH_UPDATE_ROLE			= "role";
	
	/**
	 * 一括更新区分(有効/無効)。
	 */
	public static final String	TYPE_BATCH_UPDATE_INACTIVATE	= "inactivate";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public AccountMasterAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// VO準備
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替コマンド
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替コマンド
			prepareVo();
			addMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 履歴編集モード切替コマンド
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		} else if (mospParams.getCommand().equals(CMD_SET_BATCH_UPDATE_TYPE)) {
			// 一括更新区分設定
			prepareVo();
			// スクロール先HTML要素ID設定
			setJsScrollTo(HID_DIV_MOVE_UP);
		} else if (mospParams.getCommand().equals(CMD_SET_EMPLOYEE)) {
			// 社員決定コマンド
			prepareVo();
			setEmployee();
		} else if (mospParams.getCommand().equals(CMD_PASS_INIT)) {
			// パスワード初期化コマンド
			prepareVo();
			initPassword();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new AccountMasterVo();
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(SectionMasterClassRouteComparator.class.getName());
		// 新規登録モード設定
		insertMode();
		// 一括更新モード設定
		vo.setRadBatchUpdateType(TYPE_BATCH_UPDATE_ROLE);
		// プルダウンを設定
		setBatchPulldown();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 検索クラス取得
		UserMasterSearchBeanInterface search = reference().userMasterSearch();
		// VOの値を検索クラスへ設定
		search.setUserId(vo.getTxtSearchUserId());
		search.setActivateDate(getSearchActivateDate());
		search.setEmployeeCode(vo.getPltSearchEmployeeCode());
		search.setEmployeeName(vo.getPltSearchEmployeeName());
		search.setRoleCode("");
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<AccountInfoDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// ソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 一覧選択情報初期化
		initCkbSelect();
		// 検索結果確認
		if (list.size() == 0) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 一覧のソート処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void sort() throws MospException {
		setVoList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * 一覧のページ処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 編集モード確認
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
			// 新規登録
			insert();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
			// 履歴追加
			add();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			// 履歴更新
			update();
		}
	}
	
	/**
	 * 新規登録処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// 登録処理を準備
		UserAccountRegistBeanInterface regist = platform().userAccountRegist();
		// 初期DTOを準備
		UserMasterDtoInterface userDto = regist.getInitUserDto();
		Set<UserExtraRoleDtoInterface> extraRoleDtos = regist.getInitExtraRoleDtos(getNumberOfRoleType());
		// 初期DTOに値を設定
		setUserDtoFields(userDto);
		setExtraRoleDtosFields(extraRoleDtos);
		// 新規登録
		regist.insert(userDto, extraRoleDtos);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageNewInsertSucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(userDto.getUserId(), userDto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録処理を準備
		UserAccountRegistBeanInterface regist = platform().userAccountRegist();
		// 初期DTOを準備
		UserMasterDtoInterface userDto = regist.getInitUserDto();
		Set<UserExtraRoleDtoInterface> extraRoleDtos = regist.getInitExtraRoleDtos(getNumberOfRoleType());
		// 初期DTOに値を設定
		setUserDtoFields(userDto);
		setExtraRoleDtosFields(extraRoleDtos);
		// 履歴追加
		regist.add(userDto, extraRoleDtos);
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージを設定
		PfMessageUtility.addMessageAddHistorySucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(userDto.getUserId(), userDto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録処理を準備
		UserAccountRegistBeanInterface regist = platform().userAccountRegist();
		// 初期DTOを準備
		UserMasterDtoInterface userDto = regist.getInitUserDto();
		Set<UserExtraRoleDtoInterface> extraRoleDtos = regist.getInitExtraRoleDtos(getNumberOfRoleType());
		// 初期DTOに値を設定
		setUserDtoFields(userDto);
		setExtraRoleDtosFields(extraRoleDtos);
		// 履歴更新
		regist.update(userDto, extraRoleDtos);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴編集成功メッセージを設定
		PfMessageUtility.addMessageEditHistorySucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(userDto.getUserId(), userDto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VOを準備
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 登録処理を準備
		UserAccountRegistBeanInterface regist = platform().userAccountRegist();
		// レコード識別ID配列を取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 削除処理
		regist.delete(idArray);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteHistory(mospParams, idArray.length);
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 検索
		search();
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void insertMode() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 新規登録モード設定
		setEditInsertMode();
		// プルダウン(編集)設定
		setEditPulldown();
		// 初期値設定
		vo.setTxtEditUserId("");
		vo.setTxtEditEmployeeCode("");
		vo.setPltEditRoleCode("");
		vo.setPltExtraRoles(new String[vo.getAryRoleType().length]);
		// 社員選択モード設定
		vo.setModeEditEmployee(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 選択社員情報設定
		setEditEmployee();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void addMode() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 履歴追加モード設定
		setEditAddMode();
		// プルダウン設定
		setEditPulldown();
		// 社員選択モード設定
		vo.setModeEditEmployee(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode(), getDate(getTransferredActivateDate()));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * ユーザIDと有効日で編集対象情報を取得する。<br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String userId, Date activateDate) throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 履歴編集対象取得
		UserMasterDtoInterface dto = reference().user().findForKey(userId, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード(履歴編集)設定
		setEditUpdateMode(reference().user().getUserHistory(userId));
		// プルダウン設定
		setEditPulldown();
		// 社員選択モード設定
		vo.setModeEditEmployee(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 選択社員情報設定
		setEditEmployee();
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VOを準備
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 登録処理を準備
		UserAccountRegistBeanInterface regist = platform().userAccountRegist();
		// レコード識別ID配列及び有効日を取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		Date activateDate = getUpdateActivateDate();
		// 一括更新区分を確認
		if (vo.getRadBatchUpdateType().equals(TYPE_BATCH_UPDATE_ROLE)) {
			// 一括更新処理(ロール)
			regist.update(idArray, activateDate, vo.getPltUpdateRoleCode());
		} else {
			// 一括更新処理(有効/無効)
			regist.update(idArray, activateDate, getInt(vo.getPltUpdateInactivate()));
		}
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 一括更新失敗メッセージを設定
			PfMessageUtility.addMessageBatchUpdatetFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 検索有効日設定(一括更新有効日を検索条件に設定)
		setSearchActivateDate(activateDate);
		// 検索
		search();
	}
	
	/**
	 * 社員設定処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEmployee() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 現在の社員選択モードを確認
		if (vo.getModeEditEmployee().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 社員選択モード設定
			vo.setModeEditEmployee(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 社員選択モード設定
			vo.setModeEditEmployee(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 登録社員情報設定
		setEditEmployee();
		// 登録社員情報結果確認
		if (mospParams.hasErrorMessage()) {
			// 選択社員情報設定に失敗した場合のメッセージを設定
			PfMessageUtility.addMessageFailed(mospParams, PfNameUtility.selectEmployee(mospParams));
			// 社員選択モード設定
			vo.setModeEditEmployee(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
	}
	
	/**
	 * 選択社員情報を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditEmployee() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 現在の社員選択モードを確認
		if (vo.getModeEditEmployee().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 選択社員無し
			vo.setPersonalId(MospConst.STR_EMPTY);
			vo.setLblEditEmployeeName(PfNameUtility.inputEmployeeCode(mospParams));
			return;
		}
		// 人事情報取得準備
		HumanReferenceBeanInterface human = reference().human();
		// 選択社員情報設定
		vo.setPersonalId(human.getPersonalId(vo.getTxtEditEmployeeCode(), getEditActivateDate()));
		vo.setLblEditEmployeeName(human.getHumanName(vo.getPersonalId(), getEditActivateDate()));
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			// 社員名欄再設定
			vo.setLblEditEmployeeName(PfNameUtility.inputEmployeeCode(mospParams));
		}
	}
	
	/**
	 * パスワード初期化を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void initPassword() throws MospException {
		// VOを準備
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 登録処理を準備
		UserAccountRegistBeanInterface regist = platform().userAccountRegist();
		// レコード識別ID配列を取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// パスワードを初期化
		regist.initPassword(idArray);
		// パスワード初期化結果確認
		if (mospParams.hasErrorMessage()) {
			// パスワード初期化失敗メッセージを設定
			PfMessageUtility.addMessageFailed(mospParams, PfNameUtility.initPassrword(mospParams));
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.initPassrword(mospParams));
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 検索有効日設定(一括更新有効日を検索条件に設定)
		setSearchActivateDate(getUpdateActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO準備
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// 配列の初期化
		long[] aryCkbRecordId = new long[list.size()];
		String[] aryLblUserId = new String[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblRole = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			AccountInfoDtoInterface dto = (AccountInfoDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = dto.getPfmUserId();
			aryLblUserId[i] = dto.getUserId();
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
			aryLblRole[i] = dto.getRoleName();
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryCkbRecordId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblUserId(aryLblUserId);
		vo.setAryLblRoleCode(aryLblRole);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
	/**
	 * VO(編集項目)の値をDTO(ユーザマスタDTO)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 個人IDの取得に失敗した場合
	 */
	protected void setUserDtoFields(UserMasterDtoInterface dto) throws MospException {
		// VOを取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setPfmUserId(vo.getRecordId());
		dto.setActivateDate(getEditActivateDate());
		dto.setUserId(vo.getTxtEditUserId());
		dto.setRoleCode(vo.getPltEditRoleCode());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		// 個人ID設定
		dto.setPersonalId(reference().human().getPersonalId(vo.getTxtEditEmployeeCode(), getEditActivateDate()));
	}
	
	/**
	 * VO(編集項目)の値をユーザ追加ロール情報群に設定する。<br>
	 * @param dtos ユーザ追加ロール情報群
	 */
	protected void setExtraRoleDtosFields(Set<UserExtraRoleDtoInterface> dtos) {
		// VOを取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// ユーザID及び有効日を取得
		String userId = vo.getTxtEditUserId();
		Date activateDate = getEditActivateDate();
		// ユーザ追加ロール情報群の反復子を取得
		Iterator<UserExtraRoleDtoInterface> iterator = dtos.iterator();
		// ロール区分毎に処理
		for (int i = 0; i < getNumberOfRoleType(); i++) {
			// ユーザ追加ロール情報を取得
			UserExtraRoleDtoInterface dto = iterator.next();
			// VOの値をユーザ追加ロール情報に設定
			dto.setUserId(userId);
			dto.setActivateDate(activateDate);
			dto.setRoleType(vo.getAryRoleType(i));
			dto.setRoleCode(vo.getPltExtraRoles(i));
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto ユーザマスタ情報
	 * @throws MospException ロール情報、社員情報の取得に失敗した場合
	 */
	public void setVoFields(UserMasterDtoInterface dto) throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// ユーザIDと有効日と個人IDを取得
		String userId = dto.getUserId();
		Date activateDate = dto.getActivateDate();
		String personalId = dto.getPersonalId();
		// 社員情報取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, activateDate);
		// ユーザ追加ロール情報群(キー：ロール区分、値：ロールコード)を取得
		Map<String, String> userExtraRoleMap = reference().userExtraRole().getUserExtraRoleMap(userId, activateDate);
		// DTOの値をVOに設定
		vo.setRecordId(dto.getPfmUserId());
		vo.setTxtEditUserId(userId);
		vo.setTxtEditActivateYear(getStringYear(activateDate));
		vo.setTxtEditActivateMonth(getStringMonth(activateDate));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setPltEditRoleCode(dto.getRoleCode());
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		// 社員情報設定
		vo.setTxtEditEmployeeCode("");
		if (humanDto != null) {
			vo.setTxtEditEmployeeCode(humanDto.getEmployeeCode());
		}
		// ユーザ追加ロール情報群(キー：ロール区分、値：ロールコード)を設定
		String[] pltExtraRoles = new String[vo.getAryRoleType().length];
		for (int i = 0; i < vo.getAryRoleType().length; i++) {
			pltExtraRoles[i] = userExtraRoleMap.get(vo.getAryRoleType(i));
		}
		vo.setPltExtraRoles(pltExtraRoles);
	}
	
	/**
	 * プルダウン(編集)の設定を行う。
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setEditPulldown() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// ロール参照クラスを準備
		RoleReferenceBeanInterface roleRefer = reference().role();
		// 対象日を取得
		Date targetDate = getEditActivateDate();
		// プルダウンリスト(メインロール：編集)を取得しVOに設定
		vo.setAryPltEditRoleCode(roleRefer.getSelectArray(targetDate, true));
		// プルダウンリスト(ロール区分：追加ロール項目名表示用)を取得しVOに設定
		vo.setAryRoleType(roleRefer.getRoleTypeSelectArray(targetDate));
		// プルダウンリスト(追加ロール：編集)を取得しVOに設定
		vo.setAryPltExtraRoles(roleRefer.getExtraRoleArrays(targetDate, false, true));
	}
	
	/**
	 * プルダウン(一括更新)の設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setBatchPulldown() throws MospException {
		// VO取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// プルダウン設定
		vo.setAryPltUpdateRoleCode(reference().role().getSelectArray(getUpdateActivateDate(), true));
	}
	
	/**
	 * ロール区分の個数をVOから取得する。<br>
	 * @return ロール区分の個数
	 */
	protected int getNumberOfRoleType() {
		// VOを取得
		AccountMasterVo vo = (AccountMasterVo)mospParams.getVo();
		// ロール区分の個数をVOから取得
		return vo.getAryRoleType().length;
	}
	
}
