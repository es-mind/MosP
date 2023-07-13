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
package jp.mosp.time.management.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.SubordinateFiscalSearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.SubordinateFiscalListDtoInterface;
import jp.mosp.time.management.vo.SubordinateFiscalListVo;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.utils.TimeUtility;

/**
 * 部下年度一覧クラス。<br>
 */
public class SubordinateFiscalListAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * サーバより日付を取得し、社員検索欄の表示期間に年月をデフォルトで表示する。<br>
	 */
	public static final String	CMD_SHOW				= "TM2510";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った部下情報の一覧表示を行う。<br>
	 * 一覧表示の際には社員コードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM2512";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこの画面よりも奥の階層から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で検索し、再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM2513";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、対象個人ID、対象日等をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String	CMD_TRANSFER			= "TM2515";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM2518";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM2519";
	
	/**
	 * 表示期間決定コマンド。<br>
	 * <br>
	 * 入力されている表示期間に有効な勤務地、雇用契約、所属、職位のレコードを取得し、
	 * 各プルダウンに入れて選択可能な状態にする。<br>
	 */
	public static final String	CMD_SET_DISPLAY_YEAR	= "TM2590";
	
	/**
	 * 検索年月決定コマンド。<br>
	 * <br>
	 * 入力されている表示期間に有効な勤務地、雇用契約、所属、職位のレコードを取得し、
	 * 各プルダウンに入れて選択可能な状態にする。<br>
	 */
	public static final String	CMD_SET_SEARCH_DATE		= "TM2591";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public SubordinateFiscalListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SubordinateFiscalListVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			reShowJudging();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_DISPLAY_YEAR)) {
			// 表示期間決定
			prepareVo();
			setDisplayYear();
		} else if (mospParams.getCommand().equals(CMD_SET_SEARCH_DATE)) {
			// 検索年月決定
			prepareVo();
			setSearchDate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO準備
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// プルダウン設定
		setPulldown();
		// ソートキー設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// 検索項目設定
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchEmployeeName("");
		vo.setPltSearchWorkPlace("");
		vo.setPltSearchEmployment("");
		vo.setPltSearchSection("");
		vo.setPltSearchPosition("");
		vo.setLblSeasonHolidayItem("");
		// 人事検索区分設定(部下)
		vo.setPltSearchHumanType(String.valueOf(TimeConst.CODE_SUBORDINATE_SEARCH_TYPE_SUBORDINATE));
		// 有効日モード設定
		vo.setModeDisplayYear(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 年月日指定時(システム日付)の基準月を取得
		Date targetYearMonth = MonthUtility.getTargetYearMonth(getSystemDate(), mospParams);
		// 表示期間の年月プルダウン作成(基準月の年)
		vo.setAryPltDisplayYear(getYearArray(MonthUtility.getFiscalYear(getSystemDate(), mospParams)));
		vo.setAryPltRequestYear(getYearArray(DateUtility.getYear(targetYearMonth)));
		vo.setAryPltRequestMonth(getMonthArray());
		// 表示期間の年月プルダウンの初期値を設定(基準月の年月)
		vo.setPltSearchDisplayYear(String.valueOf(MonthUtility.getFiscalYear(getSystemDate(), mospParams)));
		vo.setPltSearchRequestYear(DateUtility.getStringYear(targetYearMonth));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(targetYearMonth));
		// 利用可否確認
		checkSubordinateAvailable(getSearchDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 有効日モード設定
		vo.setModeDisplayYear(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		vo.setJsSearchConditionRequired(isSearchConditionRequired());
		// 所属コードをログイン者の所属に設定
		setSectionCode();
		// 季節休項目名設定
		vo.setLblSeasonHolidayItem(getLblSeasonHolidayItemName());
		
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	protected void setPulldown() throws MospException {
		// VO準備
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// 操作区分準備
		String operationType = MospConst.OPERATION_TYPE_REFER;
		// 勤務地
		String[][] workPlace = reference().workPlace().getNameSelectArray(getSearchDate(), true, operationType);
		vo.setAryPltWorkPlace(workPlace);
		// 雇用契約
		String[][] aryEmployment = reference().employmentContract().getNameSelectArray(getSearchDate(), true,
				operationType);
		vo.setAryPltEmployment(aryEmployment);
		// 所属
		String[][] arySection = reference().section().getCodedSelectArray(getSearchDate(), true, operationType);
		vo.setAryPltSection(arySection);
		// 職位
		String[][] aryPosition = reference().position().getCodedSelectArray(getSearchDate(), true, operationType);
		vo.setAryPltPosition(aryPosition);
		MospProperties properties = mospParams.getProperties();
		// 人事検索区分
		vo.setAryPltHumanType(properties.getCodeArray(TimeConst.CODE_SUBORDINATE_SEARCH_TYPE, true));
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException 検索、或いはソートに失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// 対象年月を取得
		int targetYear = getInt(vo.getPltSearchRequestYear());
		int targetMonth = getInt(vo.getPltSearchRequestMonth());
		
		// 検索クラス取得
		SubordinateFiscalSearchBeanInterface search = timeReference().subordinateFiscalSearch();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)
				|| vo.getModeDisplayYear().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// エラーメッセージを設定(有効日を決定してください)
			PfMessageUtility.addErrorActivateDateNotSettled(mospParams);
			return;
		}
		// 検索条件確認
		checkSearchCondition(vo.getTxtSearchEmployeeCode(), vo.getTxtSearchEmployeeName(), vo.getPltSearchWorkPlace(),
				vo.getPltSearchEmployment(), vo.getPltSearchSection(), vo.getPltSearchPosition(),
				vo.getPltSearchHumanType());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VOの値を検索クラスへ設定
		search.setTargetDate(getSearchDate());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setWorkPlaceCode(vo.getPltSearchWorkPlace());
		search.setEmploymentContractCode(vo.getPltSearchEmployment());
		search.setSectionCode(vo.getPltSearchSection());
		search.setPositionCode(vo.getPltSearchPosition());
		search.setHumanType(vo.getPltSearchHumanType());
		search.setStartDate(MonthUtility.getYearMonthTermFirstDate(targetYear, targetMonth, mospParams));
		search.setEndDate(MonthUtility.getYearMonthTermLastDate(targetYear, targetMonth, mospParams));
		search.setTargetYear(targetYear);
		search.setTargetMonth(targetMonth);
		search.setDisplayYear(getFiscalYear());
		// 検索
		List<SubordinateFiscalListDtoInterface> list = search.getSubordinateFiscalList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
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
	 * 再表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void reShowJudging() throws MospException {
		// APP_VIEW_TOTAL_VALUESがtrueの場合
		if (mospParams.getApplicationPropertyBool(TimeConst.APP_VIEW_TOTAL_VALUES)) {
			return;
		} else {
			// 再検索
			search();
		}
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void transfer() throws MospException {
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(getSelectedPersonalId(getTransferredIndex()));
		// MosP処理情報に対象年月を設定
		setTargetDate(getSearchDate());
		// MosP処理情報に対象年度を設定
		setTargetYear(getFiscalYear());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(SubordinateFiscalReferenceAction.class.getName())) {
			mospParams.addGeneralParam(TimeConst.PRM_ROLL_ARRAY, getArray());
			// 統計情報確認画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(SubordinateFiscalReferenceAction.CMD_SELECT_SHOW);
		}
	}
	
	/**
	 * 表示年度を決定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setDisplayYear() throws MospException {
		// VO取得
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// 利用可否確認
		checkSubordinateAvailable(getSearchDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 現在の有効日モードを確認
		if (vo.getModeDisplayYear().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeDisplayYear(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeDisplayYear(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 検索結果消去
		// 空のリスト準備
		List<BaseDtoInterface> list = new ArrayList<BaseDtoInterface>();
		// VOにセット
		vo.setList(list);
		// データ配列初期化
		setVoList(list);
	}
	
	/**
	 * 表示期間を決定する。<br>
	 * 年月日を決定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setSearchDate() throws MospException {
		// VO取得
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// 利用可否確認
		checkSubordinateAvailable(getSearchDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 表示年度が決定していない場合
		if (vo.getModeDisplayYear().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// エラーメッセージ
			mospParams.addErrorMessage(TimeMessageConst.MSG_EFFECTIVE_BOTTOUN,
					mospParams.getName("Display", "FiscalYear"));
			return;
		}
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			// プルダウン取得
			setPulldown();
			// 所属コードをログイン者の所属に設定
			vo.setPltSearchSection("");
			setSectionCode();
			// 季節休項目名設定
			vo.setLblSeasonHolidayItem(getLblSeasonHolidayItemName());
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// プルダウン初期化
			setInitPulldown();
		}
		// 検索結果消去
		// 空のリスト準備
		List<BaseDtoInterface> list = new ArrayList<BaseDtoInterface>();
		// VOにセット
		vo.setList(list);
		// データ配列初期化
		setVoList(list);
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
	 * @throws MospException 例外発生時
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblOverTime = new String[list.size()];
		String[] aryLblPaidHolidayDays = new String[list.size()];
		String[] aryLblPaidHolidayRestDays = new String[list.size()];
		String[] aryLblStockHolidayDays = new String[list.size()];
		String[] aryLblStockHolidayRestDays = new String[list.size()];
		String[] aryLblSeasonHolidayDays = new String[list.size()];
		String[] aryLblSeasonHolidayRestDays = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			SubordinateFiscalListDtoInterface dto = (SubordinateFiscalListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblSection[i] = reference().section().getSectionAbbr(dto.getSectionCode(), getSearchDate());
			aryLblOverTime[i] = toTimeDotFormatString(dto.getOverTime());
			aryLblPaidHolidayDays[i] = getFormatDaysHoursMinutes(dto.getPaidHolidayDays(), dto.getPaidHolidayTime(), 0,
					true);
			aryLblPaidHolidayRestDays[i] = getFormatDaysHoursMinutes(dto.getPaidHolidayRestDays(),
					dto.getPaidHolidayRestTime(), 0, true);
			aryLblStockHolidayDays[i] = getFormatDaysHoursMinutes(dto.getStockHolidayDays(), 0, 0, true);
			aryLblStockHolidayRestDays[i] = getFormatDaysHoursMinutes(dto.getStockHolidayRestDays(), 0, 0, true);
			aryLblSeasonHolidayDays[i] = getFormatDaysHoursMinutes(dto.getSeasonHolidayDays(), 0, 0, true);
			aryLblSeasonHolidayRestDays[i] = TimeUtility.getStringDaysHoursMinutes(mospParams,
					dto.getSeasonHolidayRestDays(), dto.getSeasonHolidayRestHours(), dto.getSeasonHolidayRestMinutes(),
					true);
		}
		// データをVOに設定
		// 一覧表示項目設定
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblOverTime(aryLblOverTime);
		vo.setAryLblPaidHolidayDays(aryLblPaidHolidayDays);
		vo.setAryLblPaidHolidayRestDays(aryLblPaidHolidayRestDays);
		vo.setAryLblStockHolidayDays(aryLblStockHolidayDays);
		vo.setAryLblStockHolidayRestDays(aryLblStockHolidayRestDays);
		vo.setAryLblSeasonHolidayDays(aryLblSeasonHolidayDays);
		vo.setAryLblSeasonHolidayRestDays(aryLblSeasonHolidayRestDays);
	}
	
	/**
	 * VOから検索対象日を取得する。<br>
	 * @return 検索対象日
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected Date getSearchDate() throws MospException {
		// VO準備
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// 年月指定時の基準日
		return MonthUtility.getYearMonthTargetDate(getInt(vo.getPltSearchRequestYear()),
				getInt(vo.getPltSearchRequestMonth()), mospParams);
	}
	
	/**
	 * VOから検索年度を取得する。<br>
	 * @return 対象年度
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected int getFiscalYear() throws MospException {
		// VO準備
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// 年月指定時の基準日
		return getInt(vo.getPltSearchDisplayYear());
	}
	
	/**
	 * プルダウン初期化設定
	 * @throws MospException 例外発生時
	 */
	private void setInitPulldown() throws MospException {
		// VO準備
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		// プルダウンの設定
		// 勤務地
		String[][] initPulldown = getInputActivateDatePulldown();
		vo.setAryPltWorkPlace(initPulldown);
		// 雇用契約
		vo.setAryPltEmployment(initPulldown);
		// 所属
		vo.setAryPltSection(initPulldown);
		// 職位
		vo.setAryPltPosition(initPulldown);
	}
	
	/**
	 * ログインユーザの所属を初期値に設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setSectionCode() throws MospException {
		// VO準備
		SubordinateFiscalListVo vo = (SubordinateFiscalListVo)mospParams.getVo();
		vo.setPltSearchSection("");
	}
	
	/**
	 * 季休項目名を取得する。<br>
	 * 設定した休暇コードの略称の方の前二文字を取得する。<br>
	 * @return 季休項目名
	 * @throws MospException 例外発生時
	 */
	protected String getLblSeasonHolidayItemName() throws MospException {
		// 季節休コード取得
		String seasonCode = mospParams.getApplicationProperty(TimeConst.APP_SHOW_SEASON_HOLIDAY_CODE);
		// 休暇情報取得
		HolidayDtoInterface holidayDto = timeReference().holiday().getHolidayInfo(seasonCode, getSearchDate(),
				TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		if (holidayDto == null || holidayDto.getHolidayAbbr().isEmpty()) {
			return mospParams.getName("Hyphen");
		}
		// 略称を前から2文字切り捨て
		return MospUtility.substring(holidayDto.getHolidayAbbr(), 2);
	}
	
}
