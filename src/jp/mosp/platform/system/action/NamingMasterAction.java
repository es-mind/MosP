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
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.bean.system.NamingRegistBeanInterface;
import jp.mosp.platform.bean.system.NamingSearchBeanInterface;
import jp.mosp.platform.comparator.base.NamingItemCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.system.NamingDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.system.vo.NamingMasterVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 名称区分マスタ対象項目をXMLファイルから取得し表示する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li></ul>
 */
public class NamingMasterAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * リクエストされた名称区分を取得し、初期表示を行う。<br>
	 * 編集テーブルは新規登録モードにする。<br>
	 */
	public static final String	CMD_SHOW			= "PF2620";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿った名称項目情報の一覧表示を行う。一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "PF2622";
	
	/**
	 * 登録コマンド。<br>
	 * 編集モードで判断し、新規登録、履歴追加、履歴更新を行う。<br>
	 * <br>
	 * <ul><li>
	 * 新規登録処理。<br>
	 * 編集テーブルに入力されている内容を雇用契約マスタテーブルに登録する。<br>
	 * 入力チェックを行った際に、当該名称区分について名称項目コードが
	 * 登録済みのレコードのものと重複している場合はエラーメッセージにて通知する。<br>
	 * </li><li>
	 * 履歴追加処理。<br>
	 * 編集テーブルに入力されている内容を基に選択したレコードの新たな有効日を持った
	 * 履歴を追加する。<br>
	 * </li><li>
	 * 履歴更新処理。<br>
	 * 編集テーブルに入力されている内容を基に選択したレコードの編集を行う。<br>
	 * </li></ul>
	 */
	public static final String	CMD_REGIST			= "PF2625";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードを削除するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない状態で削除ボタンがクリックされた場合はエラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_DELETE			= "PF2627";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "PF2628";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "PF2629";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 画面上部編集テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンク・
	 * 履歴編集モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE		= "PF2631";
	
	/**
	 * 履歴編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を画面上部編集テーブルの各入力欄にそれぞれ表示させる。<br>
	 * 有効日の年月日、雇用契約コードの入力欄を読取専用にする。<br>
	 * 編集テーブルヘッダに新規登録モード切替リンク・履歴追加モード切替リンクを表示させる。<br>
	 */
	public static final String	CMD_EDIT_MODE		= "PF2632";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE		= "PF2633";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、チェックの入っているレコードに
	 * 一括更新テーブル内有効/無効プルダウンの内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合やチェックが1件も入っていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE	= "PF2635";
	
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 新規登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
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
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException VOの取得に失敗した場合
	 */
	private void show() throws MospException {
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// 名称区分設定(名称)
		vo.setStringNamingType(getCodeName(getTransferredType(), PlatformConst.CODE_KEY_NAMING_TYPE));
		// 名称区分設定(コード)
		vo.setCodeNamingType(getCodeItemCode(getTransferredType(), PlatformConst.CODE_KEY_NAMING_TYPE));
		// 表示コマンド設定
		vo.setShowCommand(mospParams.getCommand());
		// 表示コマンド確認
		if (vo.getShowCommand().equals(CMD_SHOW)) {
			// パンくずリスト名称設定(承認者モードの場合)
			StringBuilder name = new StringBuilder(vo.getStringNamingType()).append(PfNameUtility.master(mospParams));
			TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), name.toString());
		}
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// 初期値設定
		vo.setTxtSearchNamingItemCode("");
		vo.setTxtSearchNamingItemAbbr("");
		vo.setTxtSearchNamingItemName("");
		// 新規登録モード設定
		insertMode();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(NamingItemCodeComparator.class.getName());
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	private void search() throws MospException {
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// 検索クラス取得
		NamingSearchBeanInterface search = reference().namingSearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setNamingType(vo.getCodeNamingType());
		search.setNamingItemCode(vo.getTxtSearchNamingItemCode());
		search.setNamingItemName(vo.getTxtSearchNamingItemName());
		search.setNamingItemAbbr(vo.getTxtSearchNamingItemAbbr());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<NamingDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(NamingItemCodeComparator.class.getName());
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
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	private void sort() throws MospException {
		setVoList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * 一覧のページ処理を行う。
	 */
	protected void page() {
		setVoList(pageList());
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void regist() throws MospException {
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
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
		NamingRegistBeanInterface regist = platform().namingRegist();
		// DTOの準備
		NamingDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getNamingType(), dto.getNamingItemCode(), dto.getActivateDate());
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
		NamingRegistBeanInterface regist = platform().namingRegist();
		// DTOの準備
		NamingDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getNamingType(), dto.getNamingItemCode(), dto.getActivateDate());
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
		NamingRegistBeanInterface regist = platform().namingRegist();
		// DTOの準備
		NamingDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getNamingType(), dto.getNamingItemCode(), dto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void delete() throws MospException {
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// 削除対象ID配列取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 削除処理
		platform().namingRegist().delete(idArray);
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
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// 一括更新処理
		platform().namingRegist().update(getIdArray(vo.getCkbSelect()), vo.getCodeNamingType(), getUpdateActivateDate(),
				getInt(vo.getPltUpdateInactivate()));
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
	 * 新規登録モードで画面を表示する。<br>
	 */
	private void insertMode() {
		// 新規登録モード設定
		setEditInsertMode();
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtEditNamingItemCode("");
		vo.setTxtEditNamingItemAbbr("");
		vo.setTxtEditNamingItemName("");
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 */
	protected void addMode() {
		// 履歴追加モード設定
		setEditAddMode();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(vo.getCodeNamingType(), getTransferredCode(), getDate(getTransferredActivateDate()));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 名称項目コードと有効日で編集対象情報を取得する。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param activateDate           有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String namingType, String namingItemCode, Date activateDate) throws MospException {
		// 履歴編集対象取得
		NamingDtoInterface dto = reference().naming().findForKey(namingType, namingItemCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード(履歴編集)設定
		setEditUpdateMode(reference().naming().getNamingItemHistory(namingType, namingItemCode));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// データ配列初期化
		long[] aryCkbRecordId = new long[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblNamingItemCode = new String[list.size()];
		String[] aryLblNamingItemName = new String[list.size()];
		String[] aryLblNamingItemAbbr = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			NamingDtoInterface dto = (NamingDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = dto.getPfmNamingId();
			aryLblNamingItemCode[i] = dto.getNamingItemCode();
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblNamingItemName[i] = dto.getNamingItemName();
			aryLblNamingItemAbbr[i] = dto.getNamingItemAbbr();
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryCkbRecordId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblNamingItemCode(aryLblNamingItemCode);
		vo.setAryLblNamingItemName(aryLblNamingItemName);
		vo.setAryLblNamingItemAbbr(aryLblNamingItemAbbr);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(NamingDtoInterface dto) {
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setPfmNamingId(vo.getRecordId());
		// 譲渡された名称区分を設定
		dto.setNamingType(vo.getCodeNamingType());
		dto.setNamingItemCode(vo.getTxtEditNamingItemCode());
		dto.setActivateDate(getEditActivateDate());
		dto.setNamingItemName(vo.getTxtEditNamingItemName());
		dto.setNamingItemAbbr(vo.getTxtEditNamingItemAbbr());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(NamingDtoInterface dto) {
		// VO取得
		NamingMasterVo vo = (NamingMasterVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getPfmNamingId());
		vo.setTxtEditNamingItemCode(dto.getNamingItemCode());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtEditNamingItemName(dto.getNamingItemName());
		vo.setTxtEditNamingItemAbbr(dto.getNamingItemAbbr());
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new NamingMasterVo();
	}
	
}
