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
import jp.mosp.framework.property.MospProperties;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.system.WorkPlaceRegistBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceSearchBeanInterface;
import jp.mosp.platform.comparator.base.EmploymentContractCodeComparator;
import jp.mosp.platform.comparator.base.WorkPlaceCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.system.vo.WorkPlaceMasterVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 勤務地マスタ情報の登録・編集・削除を行う。<br>
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
public class WorkPlaceMasterAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。編集テーブルは新規登録モードにする。<br>
	 */
	public static final String	CMD_SHOW			= "PF2500";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿った所属情報の一覧表示を行う。一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "PF2502";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "PF2504";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "PF2505";
	
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
	public static final String	CMD_REGIST			= "PF2507";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードを削除するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない状態で削除ボタンがクリックされた場合はエラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_DELETE			= "PF2509";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 画面上部編集テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンク・
	 * 履歴編集モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE		= "PF2511";
	
	/**
	 * 履歴編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を画面上部編集テーブルの各入力欄にそれぞれ表示させる。<br>
	 * 登録ボタンクリック時のコマンドを更新コマンドに切り替える。<br>
	 * 有効日の年月日、所属コードの入力欄を読取専用にする。<br>
	 * 編集テーブルヘッダに新規登録モード切替リンク・履歴追加モード切替リンクを表示させる。<br>
	 */
	public static final String	CMD_EDIT_MODE		= "PF2512";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE		= "PF2513";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE	= "PF2514";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public WorkPlaceMasterAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new WorkPlaceMasterVo();
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
			// 新規登録
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
	 */
	protected void show() {
		// VO取得
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// プルダウンの設定
		setPulldownList(vo);
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
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
		// 検索クラス取得
		WorkPlaceSearchBeanInterface search = reference().workPlaceSearch();
		// 検索項目の設定
		setSearchParams(search, vo);
		// 検索条件をもとに検索クラスからマスタリストを取得。
		List<WorkPlaceDtoInterface> list = search.getSearchList(search);
		// 検索結果リスト設定
		vo.setList(list);
		// ソートキー及びソート順設定
		vo.setComparatorName(WorkPlaceCodeComparator.class.getName());
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
	 */
	protected void insertMode() {
		// 新規登録モード設定
		setEditInsertMode();
		// VO取得
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtEditWorkPlaceCode("");
		vo.setTxtEditWorkPlaceName("");
		vo.setTxtEditWorkPlaceKana("");
		vo.setTxtEditWorkPlaceAbbr("");
		vo.setTxtEditAddress1("");
		vo.setTxtEditAddress2("");
		vo.setTxtEditAddress3("");
		vo.setTxtEditPhoneNumber1("");
		vo.setTxtEditPhoneNumber2("");
		vo.setTxtEditPhoneNumber3("");
		vo.setTxtEditPostalCode1("");
		vo.setTxtEditPostalCode2("");
		vo.setPltEditPrefecture("");
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
	 * 勤務地コードと有効日で編集対象情報を取得する。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param activateDate  有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String workPlaceCode, Date activateDate) throws MospException {
		// 履歴編集対象取得
		WorkPlaceDtoInterface dto = reference().workPlace().findForKey(workPlaceCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード(履歴編集)設定
		setEditUpdateMode(reference().workPlace().getHistory(workPlaceCode));
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 */
	protected void addMode() {
		// 履歴追加モード設定
		setEditAddMode();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
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
		WorkPlaceRegistBeanInterface regist = platform().workPlaceRegist();
		// DTOの準備
		WorkPlaceDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getWorkPlaceCode(), dto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		WorkPlaceRegistBeanInterface regist = platform().workPlaceRegist();
		// DTOの準備
		WorkPlaceDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getWorkPlaceCode(), dto.getActivateDate());
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
		WorkPlaceRegistBeanInterface regist = platform().workPlaceRegist();
		// DTOの準備
		WorkPlaceDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getWorkPlaceCode(), dto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * プルダウンリストの設定
	 * @param vo 勤務地マスタVO
	 */
	protected void setPulldownList(WorkPlaceMasterVo vo) {
		// プルダウン設定
		MospProperties properties = mospParams.getProperties();
		String[][] aryPrefecture = properties.getCodeArray(PlatformConst.CODE_KEY_PREFECTURE, true);
		// 都道府県プルダウンの設定(編集)
		vo.setAryPltEditPrefecture(aryPrefecture);
		// 都道府県プルダウンの設定(検索)
		vo.setAryPltSearchPrefecture(aryPrefecture);
	}
	
	/**
	 * beanへの検索項目の設定
	 * @param search 勤務地マスタ検索Beanインターフェース
	 * @param vo 勤務地マスタVO
	 * @throws MospException 検索項目設定時に例外処理が発生した場合
	 */
	protected void setSearchParams(WorkPlaceSearchBeanInterface search, WorkPlaceMasterVo vo) throws MospException {
		// 検索クラスから検索条件を生成する。
		// VOの値を検索条件へセットする。
		search.setActivateDate(getSearchActivateDate());
		search.setWorkPlaceCode(vo.getTxtSearchWorkPlaceCode());
		search.setWorkPlaceName(vo.getTxtSearchWorkPlaceName());
		search.setWorkPlaceKana(vo.getTxtSearchWorkPlaceKana());
		search.setWorkPlaceAbbr(vo.getTxtSearchWorkPlaceAbbr());
		search.setPrefecture(vo.getPltSearchPrefecture());
		search.setAddress1(vo.getTxtSearchAddress1());
		search.setAddress2(vo.getTxtSearchAddress2());
		search.setAddress3(vo.getTxtSearchAddress3());
		search.setPhoneNumber1(vo.getTxtSearchPhoneNumber1());
		search.setPhoneNumber2(vo.getTxtSearchPhoneNumber2());
		search.setPhoneNumber3(vo.getTxtSearchPhoneNumber3());
		search.setPostalCode1(vo.getTxtSearchPostalCode1());
		search.setPostalCode2(vo.getTxtSearchPostalCode2());
		search.setInactivateFlag(vo.getPltSearchInactivate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
		// 削除対象ID配列取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 削除処理
		platform().workPlaceRegist().delete(idArray);
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
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
		// 一括更新処理
		platform().workPlaceRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
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
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO準備
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
		// 配列の初期化
		long[] aryCkbRecordId = new long[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblWorkPlaceCode = new String[list.size()];
		String[] aryLblWorkPlaceName = new String[list.size()];
		String[] aryLblWorkPlaceAbbr = new String[list.size()];
		String[] aryLblPostalCode = new String[list.size()];
		String[] aryLblPhoneNumber = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データの設定
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			WorkPlaceDtoInterface dto = (WorkPlaceDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = dto.getPfmWorkPlaceId();
			aryLblWorkPlaceCode[i] = dto.getWorkPlaceCode();
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblWorkPlaceName[i] = dto.getWorkPlaceName();
			aryLblWorkPlaceAbbr[i] = dto.getWorkPlaceAbbr();
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
			// 郵便番号の操作
			if (dto.getPostalCode2().isEmpty()) {
				aryLblPostalCode[i] = dto.getPostalCode1();
			} else {
				aryLblPostalCode[i] = dto.getPostalCode1() + "-" + dto.getPostalCode2();
			}
			// 電話番号の操作
			if (dto.getPhoneNumber3().isEmpty()) {
				if (dto.getPhoneNumber2().isEmpty()) {
					aryLblPhoneNumber[i] = dto.getPhoneNumber1();
				} else {
					aryLblPhoneNumber[i] = dto.getPhoneNumber1() + "-" + dto.getPhoneNumber2();
				}
			} else {
				aryLblPhoneNumber[i] = dto.getPhoneNumber1() + "-" + dto.getPhoneNumber2() + "-"
						+ dto.getPhoneNumber3();
			}
		}
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblWorkPlaceCode(aryLblWorkPlaceCode);
		vo.setAryLblWorkPlaceName(aryLblWorkPlaceName);
		vo.setAryLblWorkPlaceAbbr(aryLblWorkPlaceAbbr);
		vo.setAryLblPostalCode(aryLblPostalCode);
		vo.setAryLblPhoneNumber(aryLblPhoneNumber);
		vo.setAryLblInactivate(aryLblInactivate);
		vo.setAryCkbRecordId(aryCkbRecordId);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setDtoFields(WorkPlaceDtoInterface dto) throws MospException {
		// VO準備
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
		dto.setPfmWorkPlaceId(vo.getRecordId());
		dto.setActivateDate(getEditActivateDate());
		dto.setWorkPlaceCode(vo.getTxtEditWorkPlaceCode());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		dto.setWorkPlaceKana(vo.getTxtEditWorkPlaceKana());
		dto.setWorkPlaceName(vo.getTxtEditWorkPlaceName());
		dto.setWorkPlaceAbbr(vo.getTxtEditWorkPlaceAbbr());
		dto.setPostalCode1(vo.getTxtEditPostalCode1());
		dto.setPostalCode2(vo.getTxtEditPostalCode2());
		dto.setPhoneNumber1(vo.getTxtEditPhoneNumber1());
		dto.setPhoneNumber2(vo.getTxtEditPhoneNumber2());
		dto.setPhoneNumber3(vo.getTxtEditPhoneNumber3());
		dto.setPrefecture(vo.getPltEditPrefecture());
		dto.setAddress1(vo.getTxtEditAddress1());
		dto.setAddress2(vo.getTxtEditAddress2());
		dto.setAddress3(vo.getTxtEditAddress3());
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(WorkPlaceDtoInterface dto) {
		// VO準備
		WorkPlaceMasterVo vo = (WorkPlaceMasterVo)mospParams.getVo();
		vo.setRecordId(dto.getPfmWorkPlaceId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtEditWorkPlaceCode(dto.getWorkPlaceCode());
		vo.setTxtEditWorkPlaceName(dto.getWorkPlaceName());
		vo.setTxtEditWorkPlaceKana(dto.getWorkPlaceKana());
		vo.setTxtEditWorkPlaceAbbr(dto.getWorkPlaceAbbr());
		vo.setPltEditPrefecture(String.valueOf(dto.getPrefecture()));
		vo.setTxtEditAddress1(dto.getAddress1());
		vo.setTxtEditAddress2(dto.getAddress2());
		vo.setTxtEditAddress3(dto.getAddress3());
		vo.setTxtEditPostalCode1(dto.getPostalCode1());
		vo.setTxtEditPostalCode2(dto.getPostalCode2());
		vo.setTxtEditPhoneNumber1(dto.getPhoneNumber1());
		vo.setTxtEditPhoneNumber2(dto.getPhoneNumber2());
		vo.setTxtEditPhoneNumber3(dto.getPhoneNumber3());
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
}
