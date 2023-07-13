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
package jp.mosp.time.settings.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.CutoffRegistBeanInterface;
import jp.mosp.time.bean.CutoffSearchBeanInterface;
import jp.mosp.time.comparator.settings.CutoffMasterCutoffCodeComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.CutoffMasterVo;

/**
 * 締日情報の管理や編集を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
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
public class CutoffMasterAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW			= "TM5500";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に締日情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH			= "TM5502";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たな締日情報の登録や既存の締日情報の編集を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW			= "TM5503";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 編集欄に入力した内容を基に締日情報テーブルに登録する。<br>
	 * 入力必須項目が入力されていない状態で登録を行おうとした場合はエラーメッセージにて通知、処理は実行されない。<br>
	 */
	public static final String	CMD_REGIST			= "TM5505";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードを削除するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない状態で削除ボタンがクリックされた場合はエラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_DELETE			= "TM5507";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "TM5508";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "TM5509";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 編集テーブルの各入力欄に表示されているレコード内容をクリアにする。登録ボタンクリック時のコマンドを登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE		= "TM5591";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を編集テーブルの各入力欄にそれぞれ表示させる。編集テーブルヘッダに新規登録モード切替リンクを表示させる。<br>
	 * 登録ボタンクリック時のコマンドを更新コマンドに切り替える。有効日と締日コードの入力欄を読取専用にする。<br>
	 */
	public static final String	CMD_EDIT_MODE		= "TM5592";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日入力欄を編集可能にした上で内容を空欄にする。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE		= "TM5593";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE	= "TM5595";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public CutoffMasterAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new CutoffMasterVo();
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
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
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
			// 新規登録モード切替コマンド
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 編集モード切替コマンド
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替コマンド
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
	 */
	protected void show() {
		// VO取得
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
		// 勤怠設定共通VO初期値設定
		initTimeSettingVoFields();
		// 初期値設定
		vo.setTxtSearchCutoffCode("");
		vo.setTxtSearchCutoffName("");
		vo.setTxtSearchCutoffAbbr("");
		vo.setPltSearchCutoffDate("");
		vo.setPltSearchNoApproval("");
		vo.setPltSearchSelfTightening("");
		// 新規登録モード設定
		insertMode();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(CutoffMasterCutoffCodeComparator.class.getName());
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
		// 検索クラス取得
		CutoffSearchBeanInterface search = timeReference().cutoffSearch();
		// VOの値を検索条件へ設定
		search.setActivateDate(getSearchActivateDate());
		search.setCutoffCode(vo.getTxtSearchCutoffCode());
		search.setCutoffName(vo.getTxtSearchCutoffName());
		search.setCutoffAbbr(vo.getTxtSearchCutoffAbbr());
		search.setCutoffDate(vo.getPltSearchCutoffDate());
		search.setNoApproval(vo.getPltSearchNoApproval());
		search.setSelfTightening(vo.getPltSearchSelfTightening());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスから締日リストを取得
		List<CutoffDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(CutoffMasterCutoffCodeComparator.class.getName());
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
	 */
	protected void page() {
		setVoList(pageList());
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
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
		CutoffRegistBeanInterface regist = time().cutoffRegist();
		// DTOの準備
		CutoffDtoInterface dto = regist.getInitDto();
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
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getCutoffCode(), dto.getActivateDate());
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
		CutoffRegistBeanInterface regist = time().cutoffRegist();
		// DTOの準備
		CutoffDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getCutoffCode(), dto.getActivateDate());
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
		CutoffRegistBeanInterface regist = time().cutoffRegist();
		// DTOの準備
		CutoffDtoInterface dto = regist.getInitDto();
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
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getCutoffCode(), dto.getActivateDate());
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
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
		// 削除対象ID配列取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 削除処理
		time().cutoffRegist().delete(idArray);
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
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
		// 一括更新処理
		time().cutoffRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
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
	protected void insertMode() {
		// 新規登録モード設定
		setEditInsertMode();
		// VO取得
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtEditCutoffCode("");
		vo.setTxtEditCutoffName("");
		vo.setTxtEditCutoffAbbr("");
		vo.setPltEditCutoffDate(String.valueOf(0));
		vo.setPltEditNoApproval("");
		vo.setPltEditSelfTightening(String.valueOf(MospConst.INACTIVATE_FLAG_ON));
		vo.setPltEditInactivate(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
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
	 * 締日コードと有効日で編集対象情報を取得する。<br>
	 * @param cutoffCode 締日コード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String cutoffCode, Date activateDate) throws MospException {
		// 履歴編集対象取得
		CutoffDtoInterface dto = timeReference().cutoff().findForKey(cutoffCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード(履歴編集)設定
		setEditUpdateMode(timeReference().cutoff().getCutoffHistory(cutoffCode));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
		// データ配列初期化
		long[] aryCkbRecordId = new long[list.size()];
		String[] aryActivateDate = new String[list.size()];
		String[] aryCutoffCode = new String[list.size()];
		String[] aryCutoffName = new String[list.size()];
		String[] aryCutoffAbbr = new String[list.size()];
		String[] aryCutOffDate = new String[list.size()];
		String[] aryNoApproval = new String[list.size()];
		String[] arySelfTightening = new String[list.size()];
		String[] aryInactivateFlag = new String[list.size()];
		// 締日情報取得
		String[][] cutoffDateArray = mospParams.getProperties().getCodeArray(TimeConst.CODE_KEY_CUTOFF_DATE, false);
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			CutoffDtoInterface dto = (CutoffDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = dto.getTmmCutoffId();
			aryActivateDate[i] = getStringDate(dto.getActivateDate());
			aryCutoffCode[i] = dto.getCutoffCode();
			aryCutoffName[i] = dto.getCutoffName();
			aryCutoffAbbr[i] = dto.getCutoffAbbr();
			aryCutOffDate[i] = getCodeName(String.valueOf(dto.getCutoffDate()), cutoffDateArray);
			aryNoApproval[i] = getCodeName(String.valueOf(dto.getNoApproval()),
					mospParams.getProperties().getCodeArray(TimeConst.CODE_KEY_NO_APPROVAL, false));
			arySelfTightening[i] = getInactivateFlagName(dto.getSelfTightening());
			aryInactivateFlag[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryCkbRecordId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryActivateDate);
		vo.setAryLblCutoffCode(aryCutoffCode);
		vo.setAryLblCutoffName(aryCutoffName);
		vo.setAryLblCutoffAbbr(aryCutoffAbbr);
		vo.setAryLblCutoffDate(aryCutOffDate);
		vo.setAryLblNoApproval(aryNoApproval);
		vo.setAryLblSelfTightening(arySelfTightening);
		vo.setAryLblInactivate(aryInactivateFlag);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(CutoffDtoInterface dto) {
		// VO取得
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmmCutoffId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtEditCutoffCode(dto.getCutoffCode());
		vo.setTxtEditCutoffName(dto.getCutoffName());
		vo.setTxtEditCutoffAbbr(dto.getCutoffAbbr());
		vo.setPltEditCutoffDate(String.valueOf(dto.getCutoffDate()));
		vo.setPltEditNoApproval(String.valueOf(dto.getNoApproval()));
		vo.setPltEditSelfTightening(String.valueOf(dto.getSelfTightening()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(CutoffDtoInterface dto) {
		// VO取得
		CutoffMasterVo vo = (CutoffMasterVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmmCutoffId(vo.getRecordId());
		dto.setCutoffCode(vo.getTxtEditCutoffCode());
		dto.setActivateDate(getEditActivateDate());
		dto.setCutoffName(vo.getTxtEditCutoffName());
		dto.setCutoffAbbr(vo.getTxtEditCutoffAbbr());
		dto.setCutoffDate(getInt(vo.getPltEditCutoffDate()));
		dto.setNoApproval(getInt(vo.getPltEditNoApproval()));
		dto.setSelfTightening(getInt(vo.getPltEditSelfTightening()));
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		dto.setCutoffType(TimeConst.CODE_CUTOFF_TYPE_MONTH_END_CLOSING);
	}
	
}
