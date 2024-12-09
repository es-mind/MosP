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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.WorkTypeSearchBeanInterface;
import jp.mosp.time.comparator.settings.WorkTypeMasterWorkTypeCodeComparator;
import jp.mosp.time.dto.settings.WorkTypeListDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.WorkTypeListVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤務形態情報の検索、管理を行う。<br>
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
 * {@link #CMD_BATCH_UPDATE}
 * </li></ul>
 */
public class WorkTypeListAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW			= "TM5210";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った勤務形態情報の一覧表示を行う。<br>
	 * 一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "TM5212";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている情報を最新のものに反映させ、
	 * 検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW			= "TM5213";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "TM5218";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "TM5219";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、<br>
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合やチェックが1件も入っていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE	= "TM5285";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public WorkTypeListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new WorkTypeListVo();
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
		WorkTypeListVo vo = (WorkTypeListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(WorkTypeMasterWorkTypeCodeComparator.class.getName());
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO取得
		WorkTypeListVo vo = (WorkTypeListVo)mospParams.getVo();
		// 検索クラス取得
		WorkTypeSearchBeanInterface search = timeReference().workTypeSearch();
		// 有効日取得
		Date activeDate = MonthUtility.getYearMonthTargetDate(getInt(vo.getTxtSearchActivateYear()),
				getInt(vo.getTxtSearchActivateMonth()), mospParams);
		// VOの値を検索クラスへ設定
		search.setActivateDate(activeDate);
		search.setWorkTypeCode(vo.getTxtSearchWorkTypeCode());
		search.setWorkTypeName(vo.getTxtSearchWorkTypeName());
		search.setWorkTypeAbbr(vo.getTxtSearchWorkTypeAbbr());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<WorkTypeListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(WorkTypeMasterWorkTypeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
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
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO取得
		WorkTypeListVo vo = (WorkTypeListVo)mospParams.getVo();
		Date date = MonthUtility.getYearMonthDate(getInt(vo.getTxtUpdateActivateYear()),
				getInt(vo.getTxtUpdateActivateMonth()));
		// 一括更新処理
		time().workTypeRegist().update(getIdArray(vo.getCkbSelect()), date, getInt(vo.getPltUpdateInactivate()));
		time().workTypeItemRegist().update(getIdArray(vo.getCkbSelect()), date, getInt(vo.getPltUpdateInactivate()));
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
		setSearchActivateDate(date);
		search();
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO取得
		WorkTypeListVo vo = (WorkTypeListVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 検索項目設定
		vo.setTxtSearchActivateYear(getStringYear(date));
		vo.setTxtSearchActivateMonth(getStringMonth(date));
		vo.setTxtSearchActivateDay(getStringDay(date));
		vo.setTxtSearchWorkTypeCode("");
		vo.setTxtSearchWorkTypeName("");
		vo.setTxtSearchWorkTypeAbbr("");
		vo.setPltSearchInactivate(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		// 一括更新項目設定
		vo.setTxtUpdateActivateYear(getStringYear(date));
		vo.setTxtUpdateActivateMonth(getStringMonth(date));
		vo.setTxtUpdateActivateDay(getStringDay(date));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		WorkTypeListVo vo = (WorkTypeListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbWorkTypeListId = new String[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblActivateMonth = new String[list.size()];
		String[] aryLblWorkTypeCode = new String[list.size()];
		String[] aryLblWorkTypeName = new String[list.size()];
		String[] aryLblWorkTypeAbbr = new String[list.size()];
		String[] aryLblStartTime = new String[list.size()];
		String[] aryLblEndTime = new String[list.size()];
		String[] aryLblWorkTime = new String[list.size()];
		String[] aryLblRestTime = new String[list.size()];
		String[] aryLblFrontTime = new String[list.size()];
		String[] aryLblBackTime = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		Date defaultTime = DateUtility.getDefaultTime();
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			WorkTypeListDtoInterface dto = (WorkTypeListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbWorkTypeListId[i] = String.valueOf(dto.getTmmWorkTypeId());
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblActivateMonth[i] = DateUtility.getStringYearMonth(dto.getActivateDate());
			aryLblWorkTypeCode[i] = dto.getWorkTypeCode();
			aryLblWorkTypeName[i] = dto.getWorkTypeName();
			aryLblWorkTypeAbbr[i] = dto.getWorkTypeAbbr();
			aryLblStartTime[i] = DateUtility.getStringTime(dto.getStartTime(), defaultTime);
			aryLblEndTime[i] = DateUtility.getStringTime(dto.getEndTime(), defaultTime);
			aryLblWorkTime[i] = getStringWorkTypeTime(dto.getWorkTime());
			aryLblRestTime[i] = getStringWorkTypeTime(dto.getRestTime());
			aryLblFrontTime[i] = getStringWorkTypeTime(dto.getFrontTime());
			aryLblBackTime[i] = getStringWorkTypeTime(dto.getBackTime());
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblActivateMonth(aryLblActivateMonth);
		vo.setAryLblWorkTypeCode(aryLblWorkTypeCode);
		vo.setAryLblWorkTypeName(aryLblWorkTypeName);
		vo.setAryLblWorkTypeAbbr(aryLblWorkTypeAbbr);
		vo.setAryLblStartTime(aryLblStartTime);
		vo.setAryLblEndTime(aryLblEndTime);
		vo.setAryLblWorkTime(aryLblWorkTime);
		vo.setAryLblRestTime(aryLblRestTime);
		vo.setAryLblFrontTime(aryLblFrontTime);
		vo.setAryLblBackTime(aryLblBackTime);
		vo.setAryLblInactivate(aryLblInactivate);
		vo.setAryCkbWorkTypeListId(aryCkbWorkTypeListId);
	}
	
	/**
	 * 時間をHH.MM形式に変換する。<br>
	 * @param time 対象時間
	 * @return HH.MM
	 * @throws MospException 例外処理が発生した場合
	 */
	protected String getStringWorkTypeTime(Date time) throws MospException {
		if (time == null) {
			return "";
		}
		// 分数を取得
		int minutes = TimeUtility.getMinutes(time, DateUtility.getDefaultTime());
		// 時間文字列を取得
		return TimeUtility.getStringPeriodTime(mospParams, minutes);
	}
	
}
