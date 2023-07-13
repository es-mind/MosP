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
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.SubordinateSearchBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.SubordinateListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.input.action.AttendanceListAction;
import jp.mosp.time.input.action.ScheduleReferenceAction;
import jp.mosp.time.management.vo.SubordinateListVo;
import jp.mosp.time.settings.base.TimeSettingAction;

/**
 * 勤怠管理権限者が自分の部下の勤怠実績・勤怠予定の確認や勤怠関連項目の代理申請のための画面遷移を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_CALC}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_OUTPUT}
 * </li><li>
 * {@link #CMD_SCHEDULE}
 * </li><li>
 * {@link #CMD_PAID_HOLIDAY_USAGE}
 * </li><li>
 * {@link #CMD_SHOW_APPROVED}
 * </li></ul>
 */
public class SubordinateListAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * サーバより日付を取得し、社員検索欄の表示期間に年月をデフォルトで表示する。<br>
	 */
	public static final String	CMD_SHOW				= "TM2100";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った部下情報の一覧表示を行う。<br>
	 * 一覧表示の際には社員コードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM2102";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこの画面よりも奥の階層から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で検索し、再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM2103";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、対象個人ID、対象日等をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String	CMD_TRANSFER			= "TM2105";
	
	/**
	 * 勤怠集計コマンド。<br>
	 * <br>
	 * 実行時点で社員一覧に表示されている社員の内、選択チェックボックスにチェックの
	 * 入っている社員の勤怠情報をシステム日付時点の締期間を基準に集計を行う。<br>
	 * 集計結果を社員一覧部分に表示するが、データベースには反映されない。<br>
	 */
	public static final String	CMD_CALC				= "TM2106";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM2108";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM2109";
	
	/**
	 * 表示期間決定コマンド。<br>
	 * <br>
	 * 入力されている表示期間に有効な勤務地、雇用契約、所属、職位のレコードを取得し、
	 * 各プルダウンに入れて選択可能な状態にする。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM2190";
	
	/**
	 * 出勤簿出力コマンド。<br>
	 * <br>
	 * コマンドを実行した時点で社員一覧に表示されている社員情報を表計算ファイルで出力する。<br>
	 */
	public static final String	CMD_OUTPUT				= "TM2196";
	
	/**
	 * 予定簿出力コマンド。<br>
	 * <br>
	 * コマンドを実行した時点で社員一覧に表示されている社員情報を表計算ファイルで出力する。<br>
	 */
	public static final String	CMD_SCHEDULE			= "TM2197";
	
	/**
	 * 有給休暇取得状況確認情報出力コマンド。<br>
	 * <br>
	 * 部下一覧画面の検索対象者に対して、有給休暇取得状況確認情報を出力する。<br>
	 * 検索年月の末日を検索日付とし、対象期間を決定する。<br>
	 */
	public static final String	CMD_PAID_HOLIDAY_USAGE	= "TM2198";
	
	/**
	 * 表示コマンド(社員別勤怠承認画面)。<br>
	 * <br>
	 * サーバより日付を取得し、社員検索欄の表示期間に年月をデフォルトで表示する。<br>
	 * 承認対象検索モードで画面を表示する。<br>
	 */
	public static final String	CMD_SHOW_APPROVED		= "TM2410";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public SubordinateListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SubordinateListVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SHOW_APPROVED)) {
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
		} else if (mospParams.getCommand().equals(CMD_CALC)) {
			// 勤怠集計
			prepareVo();
			calc();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 表示期間決定
			prepareVo();
			setActivateDate();
		} else if (mospParams.getCommand().equals(CMD_OUTPUT)) {
			// 出勤簿出力
			prepareVo();
			outputAttendanceBooks();
		} else if (mospParams.getCommand().equals(CMD_SCHEDULE)) {
			// 予定簿出力
			prepareVo();
			outputScheduleBooks();
		} else if (mospParams.getCommand().equals(CMD_PAID_HOLIDAY_USAGE)) {
			// 有給休暇取得状況確認情報出力
			prepareVo();
			outputPaidHolidayUsage();
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
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO取得
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 表示コマンド設定
		vo.setShowCommand(mospParams.getCommand());
		// 表示コマンド確認
		if (vo.getShowCommand().equals(CMD_SHOW_APPROVED)) {
			// パンくずリスト名称設定(承認者モードの場合)
			TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), mospParams.getName("AttendanceHumanList"));
		}
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
	 * 検索処理を行う。<br>
	 * @throws MospException 検索、或いはソートに失敗した場合
	 */
	protected void search() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 対象年月を取得
		int targetYear = getInt(vo.getPltSearchRequestYear());
		int targetMonth = getInt(vo.getPltSearchRequestMonth());
		// 検索クラス取得
		SubordinateSearchBeanInterface search = timeReference().subordinateSearch();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// エラーメッセージを設定(有効日を決定してください)
			PfMessageUtility.addErrorActivateDateNotSettled(mospParams);
			return;
		}
		// 検索条件確認
		checkSearchCondition(vo.getTxtSearchEmployeeCode(), vo.getTxtSearchEmployeeName(), vo.getPltSearchWorkPlace(),
				vo.getPltSearchEmployment(), vo.getPltSearchSection(), vo.getPltSearchPosition(),
				vo.getPltSearchApproval(), vo.getPltSearchCalc(), vo.getPltSearchHumanType());
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
		search.setApproval(vo.getPltSearchApproval());
		search.setApprovalBeforeDay(vo.getCkbYesterday());
		search.setCalc(vo.getPltSearchCalc());
		search.setHumanType(vo.getPltSearchHumanType());
		search.setStartDate(MonthUtility.getYearMonthTermFirstDate(targetYear, targetMonth, mospParams));
		search.setEndDate(MonthUtility.getYearMonthTermLastDate(targetYear, targetMonth, mospParams));
		search.setTargetYear(targetYear);
		search.setTargetMonth(targetMonth);
		// 検索
		List<SubordinateListDtoInterface> list = search.getSubordinateList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 一覧選択情報初期化
		initCkbSelect();
		// 検索結果確認
		if (list.isEmpty()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(getSelectedPersonalId(getTransferredIndex()));
		// MosP処理情報に対象年月を設定
		setTargetYear(getInt(vo.getPltSearchRequestYear()));
		setTargetMonth(getInt(vo.getPltSearchRequestMonth()));
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(AttendanceListAction.class.getName())) {
			// 表示コマンド確認
			if (vo.getShowCommand().equals(SubordinateListAction.CMD_SHOW_APPROVED)) {
				// 勤怠一覧画面(勤怠承認モード)へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(AttendanceListAction.CMD_SHOW_APPROVAL);
			} else {
				mospParams.addGeneralParam(TimeConst.PRM_ROLL_ARRAY, getArray());
				// 勤怠一覧画面へ遷移(連続実行コマンド設定)
				mospParams.setNextCommand(AttendanceListAction.CMD_SELECT_SHOW);
			}
		} else if (actionName.equals(ScheduleReferenceAction.class.getName())) {
			// 予定確認画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(ScheduleReferenceAction.CMD_SELECT_SHOW);
		}
	}
	
	/**
	 * 勤怠集計処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void calc() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		HumanReferenceBeanInterface human = reference().human();
		// 検索クラス取得
		SubordinateSearchBeanInterface search = timeReference().subordinateSearch();
		// 勤怠集計クラス取得
		TotalTimeCalcBeanInterface total = time().totalTimeCalc();
		// VOの値を検索クラスへ設定
		search.setTargetDate(getSearchDate());
		// 選択された部下一覧情報のリストを取得
		List<SubordinateListDtoInterface> list = getSelectedListDto();
		// 選択された部下一覧情報毎に処理
		for (SubordinateListDtoInterface dto : list) {
			// 個人ID及び対象年月を取得
			String personalId = dto.getPersonalId();
			int targetYear = dto.getTargetYear();
			int targetMonth = dto.getTargetMonth();
			// 勤怠集計
			TotalTimeDataDtoInterface totalTimeDataDto = total.calc(personalId, targetYear, targetMonth, true);
			// 部下一覧情報に勤怠集計情報を設定
			search.setTotalTimeData(dto, totalTimeDataDto);
			HumanDtoInterface humanDto = human.getHumanInfo(dto.getPersonalId(), getSearchDate());
			// 限度基準情報を設定
			search.setLimitStandard(dto, humanDto);
		}
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 一覧選択情報初期化
		initCkbSelect();
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
	 * @throws MospException 例外発生時
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 表示期間設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivateDate() throws MospException {
		// VO取得
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 利用可否確認
		checkSubordinateAvailable(getSearchDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			// プルダウン取得
			setPulldown();
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
	 * 初期値を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 検索項目設定
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchEmployeeName("");
		vo.setPltSearchWorkPlace("");
		vo.setPltSearchEmployment("");
		vo.setPltSearchSection("");
		vo.setPltSearchPosition("");
		vo.setPltSearchApproval("");
		vo.setCkbYesterday(MospConst.CHECKBOX_OFF);
		vo.setPltSearchCalc("");
		// 人事検索区分設定(部下)
		vo.setPltSearchHumanType(String.valueOf(TimeConst.CODE_SUBORDINATE_SEARCH_TYPE_SUBORDINATE));
		// 社員別勤怠承認画面の場合
		if (vo.getShowCommand().equals(CMD_SHOW_APPROVED)) {
			// 人事検索区分再設定(承認すべき申請)
			vo.setPltSearchHumanType(String.valueOf(TimeConst.CODE_SUBORDINATE_SEARCH_TYPE_APPROVER));
		}
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 年月日指定時(システム日付)の基準月を取得
		Date targetYearMonth = MonthUtility.getTargetYearMonth(getSystemDate(), mospParams);
		// 表示期間の年月プルダウン作成(基準月の年)
		vo.setAryPltRequestYear(getYearArray(DateUtility.getYear(targetYearMonth)));
		vo.setAryPltRequestMonth(getMonthArray());
		// 表示期間の年月プルダウンの初期値を設定(基準月の年月)
		vo.setPltSearchRequestYear(DateUtility.getStringYear(targetYearMonth));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(targetYearMonth));
		// 利用可否確認
		checkSubordinateAvailable(getSearchDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		vo.setJsSearchConditionRequired(isSearchConditionRequired());
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setPulldown() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 操作区分準備
		String operationType = MospConst.OPERATION_TYPE_REFER;
		// 表示コマンド確認
		if (vo.getShowCommand().equals(CMD_SHOW_APPROVED)) {
			// 承認対象検索モードの場合(操作区分及び範囲による制限無し)
			operationType = null;
		}
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
		// 未承認
		vo.setAryPltApproval(properties.getCodeArray(TimeConst.CODE_NOT_APPROVED, true));
		// 締状態
		vo.setAryPltCalc(properties.getCodeArray(TimeConst.CODE_CUTOFFSTATE, true));
		// 人事検索区分
		vo.setAryPltHumanType(properties.getCodeArray(TimeConst.CODE_SUBORDINATE_SEARCH_TYPE, true));
		// 表示期間の年月プルダウン作成(基準月の年)
		vo.setAryPltRequestYear(getYearArray(MospUtility.getInt(vo.getPltSearchRequestYear())));
	}
	
	/**
	 * プルダウン初期化設定
	 * @throws MospException 例外発生時
	 */
	private void setInitPulldown() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
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
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblWorkDate = new String[list.size()];
		String[] aryLblWorkTime = new String[list.size()];
		String[] aryLblRestTime = new String[list.size()];
		String[] aryLblPrivateTime = new String[list.size()];
		String[] aryLblLateTime = new String[list.size()];
		String[] aryLblLeaveEarlyTime = new String[list.size()];
		String[] aryLblLateLeaveEarlyTime = new String[list.size()];
		String[] aryLblOverTimeIn = new String[list.size()];
		String[] aryLblOverTimeOut = new String[list.size()];
		String[] aryLblWorkOnHolidayTime = new String[list.size()];
		String[] aryLblLateNightTime = new String[list.size()];
		String[] aryLblPaidHoliday = new String[list.size()];
		String[] aryLblAllHoliday = new String[list.size()];
		String[] aryLblAbsence = new String[list.size()];
		String[] aryLblApploval = new String[list.size()];
		String[] aryLblCalc = new String[list.size()];
		String[] aryLblCorrection = new String[list.size()];
		String[] aryOvertimeOutStyle = new String[list.size()];
		String[] claApploval = new String[list.size()];
		String[] claCalc = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			SubordinateListDtoInterface dto = (SubordinateListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblSection[i] = reference().section().getSectionAbbr(dto.getSectionCode(), getSearchDate());
			aryLblWorkDate[i] = getNumberString(dto.getWorkDate(), 1);
			aryLblWorkTime[i] = toTimeDotFormatString(dto.getWorkTime());
			aryLblRestTime[i] = toTimeDotFormatString(dto.getRestTime());
			aryLblPrivateTime[i] = toTimeDotFormatString(dto.getPrivateTime());
			aryLblLateTime[i] = toTimeDotFormatString(dto.getLateTime());
			aryLblLeaveEarlyTime[i] = toTimeDotFormatString(dto.getLeaveEarlyTime());
			aryLblLateLeaveEarlyTime[i] = toTimeDotFormatString(dto.getLateLeaveEarlyTime());
			aryLblOverTimeIn[i] = toTimeDotFormatString(dto.getOverTimeIn());
			aryOvertimeOutStyle[i] = dto.getOvertimeOutStyle();
			aryLblOverTimeOut[i] = toTimeDotFormatString(dto.getOverTimeOut());
			aryLblWorkOnHolidayTime[i] = toTimeDotFormatString(dto.getWorkOnHolidayTime());
			aryLblLateNightTime[i] = toTimeDotFormatString(dto.getLateNightTime());
			aryLblPaidHoliday[i] = getNumberString(dto.getPaidHoliday(), 1);
			aryLblAllHoliday[i] = getNumberString(dto.getAllHoliday(), 1);
			aryLblAbsence[i] = getNumberString(dto.getAbsence(), 1);
			claApploval[i] = dto.getApprovalStateClass();
			aryLblApploval[i] = dto.getApproval();
			claCalc[i] = dto.getCutoffStateClass();
			aryLblCalc[i] = dto.getCalc();
			aryLblCorrection[i] = dto.getCorrection();
		}
		// データをVOに設定
		// 一覧表示項目設定
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblWorkDate(aryLblWorkDate);
		vo.setAryLblWorkTime(aryLblWorkTime);
		vo.setAryLblRestTime(aryLblRestTime);
		vo.setAryLblPrivateTime(aryLblPrivateTime);
		vo.setAryLblLateTime(aryLblLateTime);
		vo.setAryLblLeaveEarlyTime(aryLblLeaveEarlyTime);
		vo.setAryLblLateLeaveEarlyTime(aryLblLateLeaveEarlyTime);
		vo.setAryLblOverTimeIn(aryLblOverTimeIn);
		vo.setAryLblOverTimeOut(aryLblOverTimeOut);
		vo.setAryLblWorkOnHolidayTime(aryLblWorkOnHolidayTime);
		vo.setAryLblLateNightTime(aryLblLateNightTime);
		vo.setAryLblPaidHoliday(aryLblPaidHoliday);
		vo.setAryLblAllHoliday(aryLblAllHoliday);
		vo.setAryLblAbsence(aryLblAbsence);
		vo.setClaApploval(claApploval);
		vo.setAryLblApploval(aryLblApploval);
		vo.setAryOvertimeOutStyle(aryOvertimeOutStyle);
		vo.setClaCalc(claCalc);
		vo.setAryLblCalc(aryLblCalc);
		vo.setAryLblCorrection(aryLblCorrection);
	}
	
	/**
	 * 帳票(出勤簿)を作成し、送出ファイルとして設定する。<br>
	 * @throws MospException 帳票の作成に失敗した場合
	 */
	protected void outputAttendanceBooks() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 個人ID配列取得
		String[] personalIds = getSelectedPersonalIds(vo.getCkbSelect());
		// 対象年月取得(VOから)
		int year = getInt(vo.getPltSearchRequestYear());
		int month = getInt(vo.getPltSearchRequestMonth());
		// 年月で出勤簿を作成
		timeReference().attendanceBook().makeAttendanceBooks(personalIds, year, month);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 帳票(予定簿)を作成し、送出ファイルとして設定する。<br>
	 * @throws MospException 帳票の作成に失敗した場合
	 */
	protected void outputScheduleBooks() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 個人ID配列取得
		String[] personalIds = getSelectedPersonalIds(vo.getCkbSelect());
		// 対象年月取得(VOから)
		int year = getInt(vo.getPltSearchRequestYear());
		int month = getInt(vo.getPltSearchRequestMonth());
		// 年月で予定簿を作成
		timeReference().scheduleBook().makeScheduleBooks(personalIds, year, month);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 帳票(有給休暇取得状況確認情報)を作成し、送出ファイルとして設定する。<br>
	 * @throws MospException 帳票の作成に失敗した場合
	 */
	protected void outputPaidHolidayUsage() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 個人ID配列取得
		String[] personalIds = getSelectedPersonalIds(vo.getCkbSelect());
		// 対象年月取得(VOから)
		int year = getInt(vo.getPltSearchRequestYear());
		int month = getInt(vo.getPltSearchRequestMonth());
		// 年月で有給休暇取得状況確認情報を作成
		timeReference().paidHolidayUsageExport().export(personalIds, year, month);
	}
	
	/**
	 * VOから検索対象日を取得する。<br>
	 * @return 検索対象日
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected Date getSearchDate() throws MospException {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 年月指定時の基準日
		return MonthUtility.getYearMonthTargetDate(getInt(vo.getPltSearchRequestYear()),
				getInt(vo.getPltSearchRequestMonth()), mospParams);
	}
	
	/**
	 * 選択された部下一覧情報のリストを取得する。<br>
	 * <br>
	 * @return 部下一覧情報リスト
	 */
	protected List<SubordinateListDtoInterface> getSelectedListDto() {
		// VO準備
		SubordinateListVo vo = (SubordinateListVo)mospParams.getVo();
		// 給与計算結果一覧情報リスト準備
		List<SubordinateListDtoInterface> selectedList = new ArrayList<SubordinateListDtoInterface>();
		// 選択されたチェックボックス毎に処理
		for (String idx : vo.getCkbSelect()) {
			// 選択された給与計算結果一覧情報を追加
			selectedList.add((SubordinateListDtoInterface)getSelectedListDto(idx));
		}
		// 選択された給与計算結果一覧情報のリストを取得
		return selectedList;
	}
	
}
