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
package jp.mosp.platform.system.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionRegistBeanInterface;
import jp.mosp.platform.bean.system.SectionSearchBeanInterface;
import jp.mosp.platform.comparator.system.SectionMasterClassRouteComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.system.vo.SectionMasterVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 所属マスタ情報の登録・編集・削除を行う。<br>
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
 * {@link #CMD_SET_BATCH_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SET_BATCH_UPDATE_TYPE}
 * </li></ul>
 */
public class SectionMasterAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。編集テーブルは新規登録モードにする。<br>
	 */
	public static final String	CMD_SHOW						= "PF2200";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 上位所属情報についてテキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なロールのプルダウンリストを作成し、検索項目にセットする。<br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE			= "PF2201";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿った所属情報の一覧表示を行う。一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH						= "PF2202";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT						= "PF2204";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE						= "PF2205";
	
	/**
	 * 登録コマンド。<br>
	 * 編集モードで判断し、新規登録、履歴追加、履歴更新を行う。<br>
	 * <br>
	 * <ul><li>
	 * 新規登録処理。<br>
	 * 編集テーブルに入力されている内容を所属マスタテーブルに登録する。<br>
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
	public static final String	CMD_REGIST						= "PF2207";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードを削除するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない状態で削除ボタンがクリックされた場合はエラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_DELETE						= "PF2209";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 画面上部編集テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンク・
	 * 履歴編集モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE					= "PF2211";
	
	/**
	 * 履歴編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を画面上部編集テーブルの各入力欄にそれぞれ表示させる。<br>
	 * 登録ボタンクリック時のコマンドを更新コマンドに切り替える。<br>
	 * 有効日の年月日、所属コードの入力欄を読取専用にする。<br>
	 * 編集テーブルヘッダに新規登録モード切替リンク・履歴追加モード切替リンクを表示させる。<br>
	 */
	public static final String	CMD_EDIT_MODE					= "PF2212";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE					= "PF2213";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE				= "PF2214";
	
	/**
	 * 一括更新テーブル有効日決定コマンド。<br>
	 * <br>
	 * 一括更新テーブル内のテキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なロールのプルダウンリストを作成し、
	 * 一括更新テーブル内のプルダウンにセットする。有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_BATCH_ACTIVATION_DATE	= "PF2215";
	
	/**
	 * 一括更新区分設定コマンド。<br>
	 * <br>
	 * 一括更新の更新対象を変更する。<br>
	 * 有効日決定が必要な更新対象を選択した場合、有効日は編集状態にする。<br>
	 */
	public static final String	CMD_SET_BATCH_UPDATE_TYPE		= "PF2216";
	
	/**
	 * 一括更新区分(上位所属)。
	 */
	public static final String	TYPE_BATCH_UPDATE_CLASS_ROUTE	= "classRoute";
	
	/**
	 * 一括更新区分(有効/無効)。
	 */
	public static final String	TYPE_BATCH_UPDATE_INACTIVATE	= "inactivate";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public SectionMasterAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定コマンド
			prepareVo();
			setActivationDate();
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
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 履歴編集モード切替
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替
			prepareVo();
			addMode();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		} else if (mospParams.getCommand().equals(CMD_SET_BATCH_ACTIVATION_DATE)) {
			// 一括更新テーブル有効日決定
			prepareVo();
			setBatchActivationDate();
			// スクロール先HTML要素ID設定
			setJsScrollTo(HID_DIV_MOVE_UP);
		} else if (mospParams.getCommand().equals(CMD_SET_BATCH_UPDATE_TYPE)) {
			// 一括更新区分設定
			prepareVo();
			setBatchUpdateType();
			// スクロール先HTML要素ID設定
			setJsScrollTo(HID_DIV_MOVE_UP);
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SectionMasterVo();
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// 初期値設定
		vo.setTxtEditSectionCode("");
		vo.setTxtEditSectionName("");
		vo.setPltEditInactivate("");
		vo.setTxtEditSectionAbbr("");
		vo.setPltSectionType("");
		vo.setPltSearchSectionAbbr("");
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(SectionMasterClassRouteComparator.class.getName());
		// 新規登録モード設定
		insertMode();
		// 一括更新モード設定
		vo.setRadBatchUpdateType(TYPE_BATCH_UPDATE_CLASS_ROUTE);
		setBatchUpdateType();
		// 所属表示名称利用設定
		vo.setUseDisplay(reference().section().useDisplayName());
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		SectionReferenceBeanInterface reference = reference().section();
		
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 上位所属のプルダウン取得
		setEditPulldown();
		
		SectionDtoInterface scheduleDto = reference.getSectionInfo(vo.getTxtEditSectionCode(), getEditActivateDate());
		if (scheduleDto == null) {
			return;
		}
		
		vo.setPltEditClassRoute(scheduleDto.getClassRoute());
		
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 検索クラス取得
		SectionSearchBeanInterface search = reference().sectionSearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setSectionCode(vo.getTxtSearchSectionCode());
		search.setSectionName(vo.getTxtSearchSectionName());
		search.setSectionAbbr(vo.getPltSearchSectionAbbr());
		search.setSectionType(vo.getPltSectionType());
		search.setCloseFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<SectionDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(SectionMasterClassRouteComparator.class.getName());
		vo.setAscending(false);
		// 検索対象日設定(経路略称取得時に用いる)
		vo.setSearchDate(getSearchActivateDate());
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
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
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
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// 登録クラス取得
		SectionRegistBeanInterface regist = platform().sectionRegist();
		// DTOの準備
		SectionDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 登録処理
		regist.insert(dto);
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
		setEditUpdateMode(dto.getSectionCode(), dto.getActivateDate());
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
		// 登録クラス取得
		SectionRegistBeanInterface regist = platform().sectionRegist();
		// DTOの準備
		SectionDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
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
		setEditUpdateMode(dto.getSectionCode(), dto.getActivateDate());
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
		// 登録クラス取得
		SectionRegistBeanInterface regist = platform().sectionRegist();
		// DTOの準備
		SectionDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.update(dto);
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
		setEditUpdateMode(dto.getSectionCode(), dto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 削除対象ID配列取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 削除処理
		platform().sectionRegist().delete(idArray);
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
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 一括更新区分確認
		if (vo.getRadBatchUpdateType().equals(TYPE_BATCH_UPDATE_CLASS_ROUTE)) {
			// 一括更新処理(上位所属)
			platform().sectionRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
					vo.getPltUpdateClassRoute());
		} else {
			// 一括更新処理(有効/無効)
			platform().sectionRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
					getInt(vo.getPltUpdateInactivate()));
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
		setSearchActivateDate(getUpdateActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void insertMode() throws MospException {
		// 新規登録モード設定
		setEditInsertMode();
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtEditSectionCode("");
		vo.setTxtEditSectionName("");
		vo.setTxtEditSectionAbbr("");
		vo.setTxtEditSectionDisplay("");
		vo.setPltEditClassRoute("");
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン(編集)設定
		setEditPulldown();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void addMode() throws MospException {
		// 履歴追加モード設定
		setEditAddMode();
		// 有効日(編集)モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setEditPulldown();
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
	 * 所属コードと有効日で編集対象情報を取得する。<br>
	 * @param sectionCode  所属コード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String sectionCode, Date activateDate) throws MospException {
		// 履歴編集対象取得
		SectionDtoInterface dto = reference().section().findForKey(sectionCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード(履歴編集)設定
		setEditUpdateMode(reference().section().getSectionHistory(sectionCode));
		// 有効日(編集)モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setEditPulldown();
	}
	
	/**
	 * 有効日(一括更新)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setBatchActivationDate() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeUpdateActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeUpdateActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeUpdateActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン(一括更新)設定
		setBatchPulldown();
	}
	
	/**
	 * 一括更新区分を設定する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setBatchUpdateType() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 有効日(一括更新)モード設定(変更時は必ず有効日編集状態にする)
		vo.setModeUpdateActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン(一括更新)設定
		setBatchPulldown();
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 所属マスタ参照クラス取得
		SectionReferenceBeanInterface reference = reference().section();
		// データ配列初期化
		long[] aryCkbRecordId = new long[list.size()];
		String[] aryLblSectionCode = new String[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblSectionName = new String[list.size()];
		String[] aryLblClassRoute = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			SectionDtoInterface dto = (SectionDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = dto.getPfmSectionId();
			aryLblSectionCode[i] = dto.getSectionCode();
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblSectionName[i] = dto.getSectionName();
			aryLblInactivate[i] = getInactivateFlagName(dto.getCloseFlag());
			// 経路情報設定
			aryLblClassRoute[i] = reference.getClassRouteAbbr(dto, vo.getSearchDate());
		}
		// データをVOに設定
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblSectionCode(aryLblSectionCode);
		vo.setAryLblSectionName(aryLblSectionName);
		vo.setAryLblClassRoute(aryLblClassRoute);
		vo.setAryLblInactivate(aryLblInactivate);
		vo.setAryCkbRecordId(aryCkbRecordId);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(SectionDtoInterface dto) {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setPfmSectionId(vo.getRecordId());
		dto.setSectionCode(vo.getTxtEditSectionCode());
		dto.setActivateDate(getEditActivateDate());
		dto.setSectionName(vo.getTxtEditSectionName());
		dto.setSectionAbbr(vo.getTxtEditSectionAbbr());
		dto.setSectionDisplay(vo.getTxtEditSectionDisplay());
		dto.setClassRoute(vo.getPltEditClassRoute());
		dto.setInactivateFlag(Integer.parseInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	public void setVoFields(SectionDtoInterface dto) {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getPfmSectionId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtEditSectionCode(dto.getSectionCode());
		vo.setTxtEditSectionName(dto.getSectionName());
		vo.setTxtEditSectionAbbr(dto.getSectionAbbr());
		vo.setTxtEditSectionDisplay(dto.getSectionDisplay());
		vo.setPltEditInactivate(String.valueOf(dto.getCloseFlag()));
		vo.setPltEditClassRoute(dto.getClassRoute());
	}
	
	/**
	 * プルダウン(編集)の設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditPulldown() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// プルダウン設定
			vo.setAryPltEditClassRoute(reference().section().getSelectArrayForMaintenance(getEditActivateDate()));
			return;
		}
		// プルダウン設定
		vo.setAryPltEditClassRoute(getInputActivateDatePulldown());
	}
	
	/**
	 * プルダウン(一括更新)の設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setBatchPulldown() throws MospException {
		// VO取得
		SectionMasterVo vo = (SectionMasterVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getModeUpdateActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// プルダウン設定
			vo.setAryPltUpdateClassRoute(reference().section().getSelectArrayForMaintenance(getUpdateActivateDate()));
			return;
		}
		// プルダウン設定
		vo.setAryPltUpdateClassRoute(getInputActivateDatePulldown());
	}
	
}
