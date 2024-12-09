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
package jp.mosp.time.settings.action;

import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBeanHandlerInterface;
import jp.mosp.time.bean.TimeSettingSearchBeanInterface;
import jp.mosp.time.comparator.settings.TimeSettingMasterWorkSettingCodeComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.TimeSettingListVo;

/**
 * 勤怠設定情報の確認、管理を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li></ul>
 */
public class TimeSettingListAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM5110";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った勤怠設定情報の一覧表示を行う。<br>
	 * 一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM5112";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている情報を最新のものに反映させ、
	 * 検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM5113";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM5118";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM5119";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 入力した有効日時点で有効な締日の略称を締日プルダウンで選択できるよう入れる。<br>
	 * 有効日決定後は有効日の入力欄が読取専用となる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM5180";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、<br>
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合やチェックが1件も入っていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE		= "TM5185";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public TimeSettingListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new TimeSettingListVo();
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
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO取得
		TimeSettingListVo vo = (TimeSettingListVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initTimeSettingVoFields();
		// 初期化設定
		vo.setTxtSearchTimeSettingCode("");
		vo.setTxtSearchTimeSettingName("");
		vo.setTxtSearchTimeSettingAbbr("");
		vo.setPltSearchCutoffDate("");
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(TimeSettingMasterWorkSettingCodeComparator.class.getName());
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 締日略称のプルダウン取得
		vo.setAryPltSearchCutoffDate(timeReference().cutoff().getSelectArray(getSearchActivateDate()));
	}
	
	/**
	 * 検索をする。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO準備
		TimeSettingListVo vo = (TimeSettingListVo)mospParams.getVo();
		// 検索クラス取得
		TimeSettingSearchBeanInterface search = timeReference().timeSettingSearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setWorkSettingCode(vo.getTxtSearchTimeSettingCode());
		search.setWorkSettingName(vo.getTxtSearchTimeSettingName());
		search.setWorkSettingAbbr(vo.getTxtSearchTimeSettingAbbr());
		search.setCutoffCode(vo.getPltSearchCutoffDate());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<TimeSettingDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(TimeSettingMasterWorkSettingCodeComparator.class.getName());
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
	 * @throws MospException VOに値を設定する処理に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException  例外処理が発生した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO取得
		TimeSettingListVo vo = (TimeSettingListVo)mospParams.getVo();
		// 一括更新処理
		TimeBeanHandlerInterface time = time();
		// 勤怠設定管理情報
		time.timeSettingRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
				getInt(vo.getPltUpdateInactivate()));
		// 追加項目の一括更新処理
		doAdditionalLogic(TimeConst.CODE_KEY_ADDITIONAL_TIME_SETTING_UPDATE);
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 検索有効日設定(一括更新有効日を検索条件に設定)
		setSearchActivateDate(getUpdateActivateDate());
		// 締日略称のプルダウンを再取得
		vo.setAryPltSearchCutoffDate(timeReference().cutoff().getSelectArray(getSearchActivateDate()));
		// 締日検索条件を再設定
		vo.setPltSearchCutoffDate("");
		// 検索
		search();
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		TimeSettingListVo vo = (TimeSettingListVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			// 締日略称のプルダウン取得
			vo.setAryPltSearchCutoffDate(timeReference().cutoff().getSelectArray(getSearchActivateDate()));
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			vo.setAryPltSearchCutoffDate(getInputActivateDatePulldown());
		}
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		TimeSettingListVo vo = (TimeSettingListVo)mospParams.getVo();
		// データ配列初期化
		long[] aryCkbRecordId = new long[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblTimeSettingCode = new String[list.size()];
		String[] aryLblTimeSettingName = new String[list.size()];
		String[] aryLblTimeSettingAbbr = new String[list.size()];
		String[] aryLblCutoff = new String[list.size()];
		String[] aryLblTimeSettingInactivate = new String[list.size()];
		// 検索有効日で締日情報を取得
		String[][] cutoffDateArray = timeReference().cutoff().getSelectArray(getSearchActivateDate());
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			TimeSettingDtoInterface dto = (TimeSettingDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = dto.getTmmTimeSettingId();
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblTimeSettingCode[i] = dto.getWorkSettingCode();
			aryLblTimeSettingName[i] = dto.getWorkSettingName();
			aryLblTimeSettingAbbr[i] = dto.getWorkSettingAbbr();
			aryLblCutoff[i] = getCodeName(dto.getCutoffCode(), cutoffDateArray);
			aryLblTimeSettingInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryCkbRecordId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblSettingCode(aryLblTimeSettingCode);
		vo.setAryLblSettingName(aryLblTimeSettingName);
		vo.setAryLblSettingAbbr(aryLblTimeSettingAbbr);
		vo.setAryLblCutoff(aryLblCutoff);
		vo.setAryLblInactivate(aryLblTimeSettingInactivate);
	}
	
}
