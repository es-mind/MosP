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

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.settings.vo.PaidHolidayReferenceVo;

/**
 * 個別有給休暇確認画面。<br>
 * 従業員別に有給休暇に関する情報を確認する。<br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li></ul>
 */
public class PaidHolidayReferenceAction extends TimeAction {
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 選択表示コマンド。有給休暇手動付与画面で選択した従業員のサーバ日付時点から1年間の有給休暇に関する詳細情報を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW	= "TM4431";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索コマンド。有効日入力欄に入力された日付を基にその日付から1年間分の有給休暇関連の詳細情報を表示する。<br>
	 */
	public static final String	CMD_SEARCH		= "TM4432";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 個別有給休暇確認画面の再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW		= "TM4433";
	
	/**
	 * 一覧結果出力用領域。（残日数(前)）<br>
	 * <br>
	 * 一覧結果に出力する残日数(前)を保存する領域。<br>
	 */
	public static double		formerDate		= 0;
	
	/**
	 * 一覧結果出力用領域。（残時間(前)）<br>
	 * <br>
	 * 一覧結果に出力する残時間(前)を保存する領域。<br>
	 */
	public static int			formerTime		= 0;
	
	/**
	 * 一覧結果出力用領域。（残日数(今)）<br>
	 * <br>
	 * 一覧結果に出力する残日数(今)を保存する領域。<br>
	 */
	public static double		currentDate		= 0;
	
	/**
	 * 一覧結果出力用領域。（残時間(今)）<br>
	 * <br>
	 * 一覧結果に出力する残時間(今)を保存する領域。<br>
	 */
	public static int			currentTime		= 0;
	
	/**
	 * 一覧結果出力用領域。（支給日数）<br>
	 * <br>
	 * 一覧結果に出力する支給日数を保存する領域。<br>
	 */
	public static double		givingDate		= 0;
	
	/**
	 * 一覧結果出力用領域。（支給時間）<br>
	 * <br>
	 * 一覧結果に出力する支給時間を保存する領域。<br>
	 */
	public static int			givingTime		= 0;
	
	/**
	 * 一覧結果出力用領域。（廃棄日数）<br>
	 * <br>
	 * 一覧結果に出力する廃棄日数を保存する領域。<br>
	 */
	public static double		cancelDate		= 0;
	
	/**
	 * 一覧結果出力用領域。（廃棄時間）<br>
	 * <br>
	 * 一覧結果に出力する廃棄時間を保存する領域。<br>
	 */
	public static int			cancelTime		= 0;
	
	/**
	 * 一覧結果出力用領域。（利用日数）<br>
	 * <br>
	 * 一覧結果に出力する利用日数を保存する領域。<br>
	 */
	public static double		useDate			= 0;
	
	/**
	 * 一覧結果出力用領域。（利用時間）<br>
	 * <br>
	 * 一覧結果に出力する利用時間を保存する領域。<br>
	 */
	public static int			useTime			= 0;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public PaidHolidayReferenceAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PaidHolidayReferenceVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			prepareVo(false, false);
			// 選択表示
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			prepareVo();
			// 検索
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			prepareVo(true, false);
			// 再表示
			show();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void show() throws MospException {
		// 初期値設定
		setDefaultValues();
		// 有給休暇情報表示
		setDefaultList();
		// 年月別表示
		setList();
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// 年月別表示
		setList();
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		PaidHolidayReferenceVo vo = (PaidHolidayReferenceVo)mospParams.getVo();
		// 個人ID取得
		String personalId = getTargetPersonalId();
		// システム日付取得
		Date systemDate = getSystemDate();
		// 社員情報の設定
		if (personalId != null && personalId.isEmpty() == false) {
			// 個人ID取得
			setEmployeeInfo(personalId, systemDate);
		} else {
			// 個人ID取得
			setEmployeeInfo(vo.getPersonalId(), systemDate);
		}
		// 対象年月をVOに設定
		vo.setPltSelectYear(String.valueOf(MonthUtility.getFiscalYear(systemDate, mospParams)));
		vo.setAryPltSelectYear(getYearArray(MonthUtility.getFiscalYear(systemDate, mospParams)));
		vo.setLblSystemDate(DateUtility.getStringJapaneseDate(systemDate));
		vo.setTxtActiveDate(getStringDate(systemDate));
	}
	
	/**
	 * 初期に表示される有給休暇情報を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	public void setDefaultList() throws MospException {
		// VO準備
		PaidHolidayReferenceVo vo = (PaidHolidayReferenceVo)mospParams.getVo();
		// 有給休暇情報データ準備
		PaidHolidayInfoReferenceBeanInterface getInfo = timeReference().paidHolidayInfo();
		// マップ準備
		Map<String, Object> map = getInfo.getPaidHolidayInfo(vo.getPersonalId(), getSystemDate());
		// 休暇情報設定
		// 情報表示欄の設定
		vo.setLblFormerDate(String.valueOf(map.get(TimeConst.CODE_FORMER_YEAR_DAY)));
		vo.setLblFormerTime(String.valueOf(map.get(TimeConst.CODE_FORMER_YEAR_TIME)));
		vo.setLblDate(String.valueOf(map.get(TimeConst.CODE_CURRENT_YEAR_DAY)));
		vo.setLblTime(String.valueOf(map.get(TimeConst.CODE_CURRENT_TIME)));
		vo.setLblGivingDate(String.valueOf(map.get(TimeConst.CODE_GIVING_DAY)));
		vo.setLblGivingTime(String.valueOf(map.get(TimeConst.CODE_GIVING_TIME)));
		vo.setLblCancelDate(String.valueOf(map.get(TimeConst.CODE_CANCEL_DAY)));
		vo.setLblCancelTime(String.valueOf(map.get(TimeConst.CODE_CANCEL_TIME)));
		vo.setLblUseDate(String.valueOf(map.get(TimeConst.CODE_USE_DAY)));
		vo.setLblUseTime(String.valueOf(map.get(TimeConst.CODE_USE_TIME)));
	}
	
	/**
	 * 有給休暇マップ取得。
	 * @param targetYearMonth 対象表示年月
	 * @return 有給休暇マップ
	 * @throws MospException 例外処理
	 */
	protected Map<String, Object> getPaidHolidayMap(Date targetYearMonth) throws MospException {
		// VO取得
		PaidHolidayReferenceVo vo = (PaidHolidayReferenceVo)mospParams.getVo();
		PaidHolidayInfoReferenceBeanInterface paidHolidayInfo = timeReference().paidHolidayInfo();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// 年月取得
		int targetYear = DateUtility.getYear(targetYearMonth);
		int targetMonth = DateUtility.getMonth(targetYearMonth);
		// 勤怠関連マスタ参照処理を取得
		TimeMasterBeanInterface timeMaster = timeReference().master();
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetYear, targetMonth);
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			// 処理終了
			return Collections.emptyMap();
		}
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = application.getCutoffEntity();
		// 対象年月及び締日から締期間最終日を取得
		Date firstDate = cutoff.getCutoffFirstDate(targetYear, targetMonth, mospParams);
		Date lastDate = cutoff.getCutoffLastDate(targetYear, targetMonth, mospParams);
		return paidHolidayInfo.getPaidHolidayReferenceInfo(vo.getPersonalId(), firstDate, lastDate);
	}
	
	/**
	 * 1月-3月以外ならば次年度に設定する。
	 * @param targetYearMonth 対象年月
	 * @return 対象年度、対象月
	 */
	private String getViewYearMonth(Date targetYearMonth) {
		return getStringYear(targetYearMonth) + mospParams.getName("Year") + getStringMonth(targetYearMonth)
				+ mospParams.getName("Month");
	}
	
	/**
	 * 設定された有効年から取得した年月別の有給休暇情報を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	public void setList() throws MospException {
		// VO準備
		PaidHolidayReferenceVo vo = (PaidHolidayReferenceVo)mospParams.getVo();
		// 有効年度
		vo.setTxtLblPreviousYear(vo.getPltSelectYear());
		// システム日付年度取得
		int fiscalYear = MonthUtility.getFiscalYear(getSystemDate(), mospParams);
		// 各月の表示する情報数準備
		int viewPeriod = 0;
		// 有効年度取得
		int editActivateYear = getInt(vo.getPltSelectYear());
		// 有効年度の初日
		Date startDate = MonthUtility.getFiscalYearFirstDate(editActivateYear, mospParams);
		// 有効年度が現在の年度より前の場合
		if (editActivateYear < fiscalYear) {
			// 12を設定
			viewPeriod = TimeConst.CODE_DEFINITION_YEAR;
		}
		// 有効年度が現在の年度より後の場合
		if (editActivateYear > fiscalYear) {
			// 0を設定
			viewPeriod = 0;
		}
		// 有効年度が現在の年度の場合
		if (editActivateYear == fiscalYear) {
			// 年度の初日からシステム日付までの経過月を取得
			viewPeriod = DateUtility.getMonthDifference(startDate, getSystemDate());
		}
		// 準備
		String[] aryLblViewYearMonth = new String[viewPeriod];
		String[] aryLblFormerDate = new String[viewPeriod];
		String[] aryLblFormerTime = new String[viewPeriod];
		String[] aryLblCurrentDate = new String[viewPeriod];
		String[] aryLblCurrentTime = new String[viewPeriod];
		String[] aryLblGivingDate = new String[viewPeriod];
		String[] aryLblGivingTime = new String[viewPeriod];
		String[] aryLblCancelDate = new String[viewPeriod];
		String[] aryLblCancelTime = new String[viewPeriod];
		String[] aryLblUseDate = new String[viewPeriod];
		String[] aryLblUseTime = new String[viewPeriod];
		// 表示月インデックス取得
		Date targetYearMonth = startDate;
		// 表示する情報数分処理
		for (int i = 0; i < viewPeriod; i++) {
			Map<String, Object> map = getPaidHolidayMap(targetYearMonth);
			aryLblViewYearMonth[i] = getViewYearMonth(targetYearMonth);
			aryLblFormerDate[i] = map.get(TimeConst.CODE_FORMER_YEAR_DAY) == null ? ""
					: ((Double)map.get(TimeConst.CODE_FORMER_YEAR_DAY)).toString();
			aryLblFormerTime[i] = map.get(TimeConst.CODE_FORMER_YEAR_TIME) == null ? ""
					: ((Integer)map.get(TimeConst.CODE_FORMER_YEAR_TIME)).toString();
			aryLblCurrentDate[i] = map.get(TimeConst.CODE_CURRENT_YEAR_DAY) == null ? ""
					: ((Double)map.get(TimeConst.CODE_CURRENT_YEAR_DAY)).toString();
			aryLblCurrentTime[i] = map.get(TimeConst.CODE_CURRENT_TIME) == null ? ""
					: ((Integer)map.get(TimeConst.CODE_CURRENT_TIME)).toString();
			aryLblGivingDate[i] = map.get(TimeConst.CODE_GIVING_DAY) == null ? ""
					: ((Double)map.get(TimeConst.CODE_GIVING_DAY)).toString();
			aryLblGivingTime[i] = map.get(TimeConst.CODE_GIVING_TIME) == null ? ""
					: ((Integer)map.get(TimeConst.CODE_GIVING_TIME)).toString();
			aryLblCancelDate[i] = map.get(TimeConst.CODE_CANCEL_DAY) == null ? ""
					: ((Double)map.get(TimeConst.CODE_CANCEL_DAY)).toString();
			aryLblCancelTime[i] = map.get(TimeConst.CODE_CANCEL_TIME) == null ? ""
					: ((Integer)map.get(TimeConst.CODE_CANCEL_TIME)).toString();
			aryLblUseDate[i] = map.get(TimeConst.CODE_USE_DAY) == null ? ""
					: ((Double)map.get(TimeConst.CODE_USE_DAY)).toString();
			aryLblUseTime[i] = map.get(TimeConst.CODE_USE_TIME) == null ? ""
					: ((Integer)map.get(TimeConst.CODE_USE_TIME)).toString();
			// 対象月追加
			targetYearMonth = DateUtility.addMonth(targetYearMonth, 1);
		}
		vo.setAryLblViewYearMonth(aryLblViewYearMonth);
		vo.setAryLblFormerDate(aryLblFormerDate);
		vo.setAryLblFormerTime(aryLblFormerTime);
		vo.setAryLblDate(aryLblCurrentDate);
		vo.setAryLblTime(aryLblCurrentTime);
		vo.setAryLblGivingDate(aryLblGivingDate);
		vo.setAryLblGivingTime(aryLblGivingTime);
		vo.setAryLblCancelDate(aryLblCancelDate);
		vo.setAryLblCancelTime(aryLblCancelTime);
		vo.setAryLblUseDate(aryLblUseDate);
		vo.setAryLblUseTime(aryLblUseTime);
	}
}
