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
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.system.PositionRegistBeanInterface;
import jp.mosp.platform.bean.system.PositionSearchBeanInterface;
import jp.mosp.platform.comparator.base.EmploymentContractCodeComparator;
import jp.mosp.platform.comparator.base.PositionCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.system.vo.PositionMasterVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 職位マスタ情報の登録・編集・削除を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
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
 * </li></ul>
 */
public class PositionMasterAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。編集テーブルは新規登録モードにする。<br>
	 */
	public static final String		CMD_SHOW			= "PF2300";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿った職位情報の一覧表示を行う。一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String		CMD_SEARCH			= "PF2302";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String		CMD_SORT			= "PF2304";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String		CMD_PAGE			= "PF2305";
	
	/**
	 * 登録コマンド。<br>
	 * 編集モードで判断し、新規登録、履歴追加、履歴更新を行う。<br>
	 * <br>
	 * <ul><li>
	 * 新規登録処理。<br>
	 * 編集テーブルに入力されている内容を職位マスタテーブルに登録する。<br>
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
	public static final String		CMD_REGIST			= "PF2307";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードを削除するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない状態で削除ボタンがクリックされた場合はエラーメッセージにて通知する。<br>
	 */
	public static final String		CMD_DELETE			= "PF2309";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 画面上部編集テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンク・
	 * 履歴編集モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE		= "PF2311";
	
	/**
	 * 履歴編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を画面上部編集テーブルの各入力欄にそれぞれ表示させる。<br>
	 * 登録ボタンクリック時のコマンドを更新コマンドに切り替える。<br>
	 * 有効日の年月日、職位コードの入力欄を読取専用にする。<br>
	 * 編集テーブルヘッダに新規登録モード切替リンク・履歴追加モード切替リンクを表示させる。<br>
	 */
	public static final String		CMD_EDIT_MODE		= "PF2312";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String		CMD_ADD_MODE		= "PF2313";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String		CMD_BATCH_UPDATE	= "PF2314";
	
	/**
	 * デフォルト値(新規登録時の号数)。<br>
	 */
	protected static final String	DEFAULT_LEVEL		= "0";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public PositionMasterAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PositionMasterVo();
	}
	
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
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// 新規登録モード設定
		insertMode();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(EmploymentContractCodeComparator.class.getName());
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO準備
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
		// 検索クラス取得
		PositionSearchBeanInterface search = reference().positionSearch();
		// 検索項目の設定
		setSearchParams(search, vo);
		// 検索条件をもとに検索クラスからマスタリストを取得。
		List<PositionDtoInterface> list = search.getSearchList(search);
		// 検索結果リスト設定
		vo.setList(list);
		// ソートキー及びソート順設定
		vo.setComparatorName(PositionCodeComparator.class.getName());
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
		// VO準備
		setVoList(pageList());
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void insertMode() throws MospException {
		// 新規登録モード設定
		setEditInsertMode();
		// VO取得
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtEditPositionCode("");
		vo.setTxtEditPositionName("");
		vo.setTxtEditPositionAbbr("");
		vo.setTxtEditPositionGrade("");
		vo.setTxtEditPositionLevel(DEFAULT_LEVEL);
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
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode(), getDate(getTransferredActivateDate()));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 職位コードと有効日で編集対象情報を取得する。<br>
	 * @param positionCode 職位コード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String positionCode, Date activateDate) throws MospException {
		// 履歴編集対象取得
		PositionDtoInterface dto = reference().position().getPositionInfo(positionCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード(履歴編集)設定
		setEditUpdateMode(reference().position().getPositionHistory(positionCode));
	}
	
	/**
	 * beanへの検索項目の設定
	 * @param search 職位マスタ検索Beanインターフェース
	 * @param vo 職位マスタVO
	 * @throws MospException 検索項目設定時に例外処理が発生した場合
	 */
	protected void setSearchParams(PositionSearchBeanInterface search, PositionMasterVo vo) throws MospException {
		// 検索クラスから検索条件を生成する。
		// VOの値を検索条件へセットする。
		search.setActivateDate(getSearchActivateDate());
		search.setPositionCode(vo.getTxtSearchPositionCode());
		search.setPositionName(vo.getTxtSearchPositionName());
		search.setPositionAbbr(vo.getTxtSearchPositionAbbr());
		search.setPositionGrade(vo.getTxtSearchPositionGrade());
		search.setPositionLevel(vo.getTxtSearchPositionLevel());
		search.setInactivateFlag(vo.getPltSearchInactivate());
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
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
		PositionRegistBeanInterface regist = platform().positionRegist();
		// DTOの準備
		PositionDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getPositionCode(), dto.getActivateDate());
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
		PositionRegistBeanInterface regist = platform().positionRegist();
		// DTOの準備
		PositionDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getPositionCode(), dto.getActivateDate());
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
		PositionRegistBeanInterface regist = platform().positionRegist();
		// DTOの準備
		PositionDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getPositionCode(), dto.getActivateDate());
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
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
		// 削除対象ID配列取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 削除処理
		platform().positionRegist().delete(idArray);
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
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
		// 一括更新処理
		platform().positionRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
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
	 * リストをVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO準備
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
		// 配列の初期化
		String[] aryActivateDate = new String[list.size()];
		String[] aryPositionCode = new String[list.size()];
		String[] aryPositionName = new String[list.size()];
		String[] aryPositionAbbr = new String[list.size()];
		String[] aryPositionGrade = new String[list.size()];
		String[] aryPositionLevel = new String[list.size()];
		String[] aryInactivateFlag = new String[list.size()];
		long[] aryCkbRecordId = new long[list.size()];
		// データの設定
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			PositionDtoInterface dto = (PositionDtoInterface)list.get(i);
			aryActivateDate[i] = getStringDate(dto.getActivateDate());
			aryPositionCode[i] = dto.getPositionCode();
			aryPositionName[i] = dto.getPositionName();
			aryPositionAbbr[i] = dto.getPositionAbbr();
			aryPositionGrade[i] = String.valueOf(dto.getPositionGrade());
			aryPositionLevel[i] = String.valueOf(dto.getPositionLevel());
			aryInactivateFlag[i] = getInactivateFlagName(dto.getInactivateFlag());
			aryCkbRecordId[i] = dto.getPfmPositionId();
		}
		vo.setAryLblActivateDate(aryActivateDate);
		vo.setAryLblPositionCode(aryPositionCode);
		vo.setAryLblPositionName(aryPositionName);
		vo.setAryLblPositionAbbr(aryPositionAbbr);
		vo.setAryLblGrade(aryPositionGrade);
		vo.setAryLblLevel(aryPositionLevel);
		vo.setAryLblInactivate(aryInactivateFlag);
		vo.setAryCkbRecordId(aryCkbRecordId);
	}
	
	/**
	 * フィールド設定
	 * @param dto 対象DTO
	 * @throws MospException 例外処理が発生した場合  
	 */
	protected void setDtoFields(PositionDtoInterface dto) throws MospException {
		// VO準備
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
		dto.setPfmPositionId(vo.getRecordId());
		dto.setActivateDate(getEditActivateDate());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		dto.setPositionCode(vo.getTxtEditPositionCode());
		dto.setPositionName(vo.getTxtEditPositionName());
		dto.setPositionAbbr(vo.getTxtEditPositionAbbr());
		dto.setPositionGrade(getInt(vo.getTxtEditPositionGrade()));
		dto.setPositionLevel(getInt(vo.getTxtEditPositionLevel()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(PositionDtoInterface dto) {
		// VO準備
		PositionMasterVo vo = (PositionMasterVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getPfmPositionId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtEditPositionCode(dto.getPositionCode());
		vo.setTxtEditPositionName(dto.getPositionName());
		vo.setTxtEditPositionAbbr(dto.getPositionAbbr());
		vo.setTxtEditPositionGrade(String.valueOf(dto.getPositionGrade()));
		vo.setTxtEditPositionLevel(String.valueOf(dto.getPositionLevel()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
}
