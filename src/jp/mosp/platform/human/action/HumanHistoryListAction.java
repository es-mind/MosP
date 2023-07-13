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
package jp.mosp.platform.human.action;

import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.HumanHistoryListVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用管理形式の履歴一覧で履歴編集・表示・登録へ遷移する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 *  <ul><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li></ul>
 * {@link #CMD_TRANSFER}
 * </li><li>
 */
public class HumanHistoryListAction extends PlatformHumanAction {
	
	/**
	 * 社員検索コマンド。<br>
	 * <br>
	 * 社員コード入力欄に入力された社員コードを基に検索を行い、
	 * 条件に当てはまる社員の基本情報履歴一覧を表示する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 入力した社員コードが存在しない、または入力されていない状態で
	 * 「検索ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_SEARCH					= "PF1542";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 社員一覧画面で選択された社員の個人IDを基に個人基本情報の履歴を表示する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_SELECT					= "PF1546";
	
	/**
	 * 再検索コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこれよりも奥の階層の画面から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で再表示を行う。<br>
	 */
	public static final String	CMD_RE_SEARCH				= "PF1547";
	
	/**
	 * 履歴削除コマンド。<br>
	 * <br>
	 * 社員一覧で選択された社員の個人IDを基に個人基本情報の履歴を削除する。<br>
	 * 削除前に行われる削除可否チェックにてエラーの場合、エラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_DELETE					= "PF1548";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER				= "PF1549";
	
	/**
	 * 人事汎用管理表示区分(履歴一覧)。<br>
	 */
	public static final String	KEY_VIEW_HISTORY_LIST		= "HistoryList";
	
	/**
	 * 人事汎用管理表示区分(削除用)。<br>
	 */
	public static final String	KEY_VIEW_HUMAN_HISTORY_CARD	= "HistoryCard";
	
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択表示
			prepareVo(true, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			reSearch();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo(true, false);
			delete();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 画面遷移
			prepareVo(true, false);
			transfer();
		} else {
			throwInvalidCommandException();
		}
		
	}
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanHistoryListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HumanHistoryListVo();
	}
	
	/**
	 * 検索処理を行う。
	 * @throws MospException VO、或いは社員情報の取得に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 人事履歴情報リストを取得しVOに設定
		getHistoryList();
	}
	
	/**
	 * 選択表示処理を行う。
	 * @throws MospException VO、或いは社員情報の取得に失敗した場合
	 */
	protected void select() throws MospException {
		// VO取得
		HumanHistoryListVo vo = (HumanHistoryListVo)mospParams.getVo();
		// 人事汎用管理区分設定
		vo.setDivision(getTransferredType());
		// パンくず名準備
		StringBuilder name = new StringBuilder(NameUtility.getName(mospParams, vo.getDivision()));
		name.append(PfNameUtility.historyList(mospParams));
		// パンくず名設定
		TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), name.toString());
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 人事履歴情報リストを取得しVOに設定
		getHistoryList();
	}
	
	/**
	 * VOに保持された情報を基に、再検索を行う。
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void reSearch() throws MospException {
		// VO取得
		HumanHistoryListVo vo = (HumanHistoryListVo)mospParams.getVo();
		// 人事管理共通情報設定
		setHumanCommonInfo(vo.getPersonalId(), vo.getTargetDate());
		// 項目初期化
		setDefaultValues();
		// 人事履歴情報リストを取得しVOに設定
		getHistoryList();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException  インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// 履歴削除処理
		deleteHistoryBasicInfo();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 項目初期化
		setDefaultValues();
		// 人事履歴情報リストを取得しVOに設定
		getHistoryList();
	}
	
	/**
	 * 個人基本情報履歴削除処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void deleteHistoryBasicInfo() throws MospException {
		// VO取得
		HumanHistoryListVo vo = (HumanHistoryListVo)mospParams.getVo();
		// 人事汎用管理区分設定
		String division = getTransferredType();
		// 有効日取得
		Date activeDate = DateUtility.getDate(getTransferredActivateDate());
		// 削除
		platform().humanHistoryRegist().delete(division, KEY_VIEW_HUMAN_HISTORY_CARD,
				vo.getAryHistoryRecordIdMap(DateUtility.getStringDate(activeDate)));
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// 削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteSucceed(mospParams);
		// コミット
		commit();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		HumanHistoryListVo vo = (HumanHistoryListVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		// 人事汎用通常画面：履歴編集の場合
		if (actionName.equals(HumanHistoryCardAction.CMD_EDIT_SELECT)) {
			// 人事汎用通常画面：履歴編集
			mospParams.setNextCommand(HumanHistoryCardAction.CMD_EDIT_SELECT);
			// 人事汎用履歴画面：履歴追加の場合
		} else if (actionName.equals(HumanHistoryCardAction.CMD_ADD_SELECT)) {
			// 履歴追加
			mospParams.setNextCommand(HumanHistoryCardAction.CMD_ADD_SELECT);
			// 人事情報一覧の場合
		} else if (actionName.equals(HumanInfoAction.class.getName())) {
			// 社員の人事情報をまとめて表示
			mospParams.setNextCommand(HumanInfoAction.CMD_SELECT);
		}
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		HumanHistoryListVo vo = (HumanHistoryListVo)mospParams.getVo();
		// 初期値設定
		vo.setAryActiveteDate(new String[0]);
	}
	
	/**
	 * 人事履歴情報リストを取得し、VOに設定する。<br>
	 * @throws MospException 社員情報の取得に失敗した場合
	 */
	protected void getHistoryList() throws MospException {
		// VO取得
		HumanHistoryListVo vo = (HumanHistoryListVo)mospParams.getVo();
		// 人事汎用管理区分取得
		String division = vo.getDivision();
		// 個人IDを設定
		String personalId = vo.getPersonalId();
		// 個人IDが設定されていない場合
		if (personalId.isEmpty()) {
			personalId = reference().human().getPersonalId(vo.getEmployeeCode(), getSystemDate());
		}
		// 人事汎用通常情報参照クラス取得
		HumanHistoryReferenceBeanInterface humanReference = reference().humanHistory();
		// 人事汎用履歴情報取得（レコード識別ID取得のために編集画面KEYで取得）
		humanReference.getActiveDateHistoryMapInfo(division, KEY_VIEW_HUMAN_HISTORY_CARD, personalId,
				vo.getTargetDate());
		// 人事汎用履歴情報をリスト用に加工（識別ID取得のための措置）
		humanReference.getHistoryListData(division, KEY_VIEW_HISTORY_LIST);
		// 人事汎用履歴情報リスト確認
		if (humanReference.getHistoryHumanInfoMap().isEmpty()) {
			return;
		}
		// 件数確認フラグ
		boolean isLastHistory = false;
		// マップ情報のサイズを取得
		if (humanReference.getHistoryHumanInfoMap().size() == 1) {
			// 1件だけの場合
			isLastHistory = true;
		}
		// 件数確認フラグ設定
		vo.setJsIsLastHistory(isLastHistory);
		// 有効日配列準備
		String[] activeDate = humanReference.getArrayActiveDate(humanReference.getHistoryHumanInfoMap());
		
		// 登録情報を取得しVOに設定
		vo.putHistoryItem(division, humanReference.getHistoryHumanInfoMap());
		
		// レコード識別IDマップ情報取得
		vo.setAryHistoryRecordIdMap(humanReference.getHistoryReferenceInfoMap());
		
		// 有効日配列設定
		vo.setAryActiveteDate(activeDate);
	}
	
}
