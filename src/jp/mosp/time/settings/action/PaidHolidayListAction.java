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
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.PaidHolidaySearchBeanInterface;
import jp.mosp.time.comparator.settings.PaidHolidayMasterPaidHolidayCodeComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.PaidHolidayListVo;

/**
 * 有給休暇設定情報の検索、管理を行う。<br>
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
public class PaidHolidayListAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW			= "TM5310";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った有給休暇設定情報の一覧表示を行う。<br>
	 * 一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "TM5312";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている情報を最新のものに反映させ、
	 * 検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW			= "TM5313";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "TM5318";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "TM5319";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、<br>
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合やチェックが1件も入っていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE	= "TM5385";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public PaidHolidayListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PaidHolidayListVo();
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
		// VO準備
		PaidHolidayListVo vo = (PaidHolidayListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// プルダウン設定
		setPulldown();
		// ソートキー設定
		vo.setComparatorName(PaidHolidayMasterPaidHolidayCodeComparator.class.getName());
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		PaidHolidayListVo vo = (PaidHolidayListVo)mospParams.getVo();
		// システム日付取得
		Date date = DateUtility.getSystemDate();
		// 検索項目設定
		vo.setTxtSearchActivateYear(DateUtility.getStringYear(date));
		vo.setTxtSearchActivateMonth(DateUtility.getStringMonth(date));
		vo.setTxtSearchActivateDay(DateUtility.getStringDay(date));
		vo.setPltSearchPaidHolidayType("");
		vo.setTxtSearchPaidHolidayCode("");
		vo.setTxtSearchPaidHolidayName("");
		vo.setTxtSearchPaidHolidayAbbr("");
		vo.setPltSearchInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		vo.setPltSearchPaidHolidayType("");
		// 一括更新項目設定 
		vo.setTxtUpdateActivateYear(DateUtility.getStringYear(date));
		vo.setTxtUpdateActivateMonth(DateUtility.getStringMonth(date));
		vo.setTxtUpdateActivateDay(DateUtility.getStringDay(date));
	}
	
	/**
	 * プルダウン設定
	 */
	private void setPulldown() {
		// VO準備
		PaidHolidayListVo vo = (PaidHolidayListVo)mospParams.getVo();
		// プルダウン設定
		MospProperties properties = mospParams.getProperties();
		String[][] aryHolidayType = properties.getCodeArray(TimeConst.CODE_KEY_PAID_HOLIDAY_TYPE, false);
		// 付与区分プルダウンの設定
		vo.setAryPltSearchPaidHolidayType(aryHolidayType);
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合 
	 */
	protected void search() throws MospException {
		// VO準備
		PaidHolidayListVo vo = (PaidHolidayListVo)mospParams.getVo();
		// 検索クラス取得
		PaidHolidaySearchBeanInterface search = timeReference().paidHolidaySearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setPaidHolidayType(vo.getPltSearchPaidHolidayType());
		search.setPaidHolidayCode(vo.getTxtSearchPaidHolidayCode());
		search.setPaidHolidayName(vo.getTxtSearchPaidHolidayName());
		search.setPaidHolidayAbbr(vo.getTxtSearchPaidHolidayAbbr());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<PaidHolidayDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(PaidHolidayMasterPaidHolidayCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
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
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		PaidHolidayListVo vo = (PaidHolidayListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbPaidHolidayListId = new String[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblPaidHolidayType = new String[list.size()];
		String[] aryLblPaidHolidayCode = new String[list.size()];
		String[] aryLblPaidHolidayName = new String[list.size()];
		String[] aryLblPaidHolidayAbbr = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			PaidHolidayDtoInterface dto = (PaidHolidayDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbPaidHolidayListId[i] = Long.toString(dto.getTmmPaidHolidayId());
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblPaidHolidayType[i] = mospParams.getProperties().getCodeItemName(TimeConst.CODE_KEY_PAID_HOLIDAY_TYPE,
					Integer.toString(dto.getPaidHolidayType()));
			aryLblPaidHolidayCode[i] = dto.getPaidHolidayCode();
			aryLblPaidHolidayName[i] = dto.getPaidHolidayName();
			aryLblPaidHolidayAbbr[i] = dto.getPaidHolidayAbbr();
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryCkbPaidHolidayListId(aryCkbPaidHolidayListId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblPaidHolidayType(aryLblPaidHolidayType);
		vo.setAryLblPaidHolidayCode(aryLblPaidHolidayCode);
		vo.setAryLblPaidHolidayName(aryLblPaidHolidayName);
		vo.setAryLblPaidHolidayAbbr(aryLblPaidHolidayAbbr);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
	/**
	 * 一覧のページ処理を行う。
	 */
	protected void page() {
		setVoList(pageList());
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		PaidHolidayListVo vo = (PaidHolidayListVo)mospParams.getVo();
		// 一括更新処理
		time().paidHolidayRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
				getInt(vo.getPltUpdateInactivate()));
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
		search();
	}
	
}
