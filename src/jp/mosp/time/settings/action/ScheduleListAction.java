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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ScheduleSearchBeanInterface;
import jp.mosp.time.comparator.settings.ScheduleMasterScheduleCodeComparator;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.settings.vo.ScheduleListVo;

/**
 * カレンダ情報の検索、管理を行う。<br>
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
 * </li></ul>
 */
public class ScheduleListAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW	= "TM5410";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿ったカレンダ情報の一覧表示を行う。一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH	= "TM5412";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている情報を最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW	= "TM5413";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT	= "TM5418";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE	= "TM5419";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public ScheduleListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ScheduleListVo();
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
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO準備
		ScheduleListVo vo = (ScheduleListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// プルダウン設定
		setPulldown();
		// ソートキー設定
		vo.setComparatorName(ScheduleMasterScheduleCodeComparator.class.getName());
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合 
	 */
	protected void search() throws MospException {
		// VO準備
		ScheduleListVo vo = (ScheduleListVo)mospParams.getVo();
		// 検索クラス取得
		ScheduleSearchBeanInterface search = timeReference().scheduleSearch();
		// 年を表す日付(年度の初日)を取得
		Date activateDate = MonthUtility.getYearDate(getInt(vo.getPltSearchFiscalYear()), mospParams);
		// VOの値を検索クラスへ設定
		search.setActivateDate(activateDate);
		search.setFiscalYear(vo.getPltSearchFiscalYear());
		search.setScheduleCode(vo.getTxtSearchScheduleCode());
		search.setScheduleName(vo.getTxtSearchScheduleName());
		search.setScheduleAbbr(vo.getTxtSearchScheduleAbbr());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<ScheduleDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ScheduleMasterScheduleCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 年プルダウンを再設定
		setPulldown();
		// 検索結果確認
		if (list.isEmpty()) {
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
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		ScheduleListVo vo = (ScheduleListVo)mospParams.getVo();
		vo.setPltSearchFiscalYear(String.valueOf(MonthUtility.getFiscalYear(getSystemDate(), mospParams)));
		vo.setTxtSearchScheduleCode("");
		vo.setTxtSearchScheduleName("");
		vo.setTxtSearchScheduleAbbr("");
		vo.setPltSearchInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setPulldown() throws MospException {
		// VO準備
		ScheduleListVo vo = (ScheduleListVo)mospParams.getVo();
		// プルダウンの設定
		vo.setAryPltSearchFiscalYear(getYearArray(getInt(vo.getPltSearchFiscalYear())));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		ScheduleListVo vo = (ScheduleListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbScheduleListId = new String[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblFiscalYear = new String[list.size()];
		String[] aryLblScheduleCode = new String[list.size()];
		String[] aryLblScheduleName = new String[list.size()];
		String[] aryLblScheduleAbbr = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			ScheduleDtoInterface dto = (ScheduleDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbScheduleListId[i] = String.valueOf(dto.getTmmScheduleId());
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblFiscalYear[i] = String.valueOf(dto.getFiscalYear());
			aryLblScheduleCode[i] = dto.getScheduleCode();
			aryLblScheduleName[i] = dto.getScheduleName();
			aryLblScheduleAbbr[i] = dto.getScheduleAbbr();
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryCkbScheduleListId(aryCkbScheduleListId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblFiscalYear(aryLblFiscalYear);
		vo.setAryLblScheduleCode(aryLblScheduleCode);
		vo.setAryLblScheduleName(aryLblScheduleName);
		vo.setAryLblScheduleAbbr(aryLblScheduleAbbr);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
}
