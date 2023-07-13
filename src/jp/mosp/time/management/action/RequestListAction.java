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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.human.HumanSubordinateBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.comparator.settings.ManagementRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;
import jp.mosp.time.input.action.ApprovalHistoryAction;
import jp.mosp.time.management.vo.RequestListVo;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠管理権限者が自分の部下の各種申請承認情報の検索と確認を行う。<br>
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
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li></ul>
 */
public class RequestListAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM2200";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿ったアカウント情報の一覧表示を行う。<br>
	 * 一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM2202";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこの画面よりも奥の階層から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で検索し、再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM2203";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String	CMD_TRANSFER			= "TM2206";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM2208";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM2209";
	
	/**
	 * 表示期間決定コマンド。<br>
	 * <br>
	 * 各プルダウン情報についてテキストボックスに入力した表示期間で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なロールのプルダウンリストを作成し、検索項目にセットする。<br>
	 * 表示期間決定後、表示期間は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM2290";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public RequestListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new RequestListVo();
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
			search();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// 頁繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 表示期間決定
			prepareVo();
			setActivateDate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// システム日付取得
		Date systemDate = getSystemDate();
		// 表示期間（年）
		vo.setAryPltSearchRequestYear(getYearArray(DateUtility.getYear(systemDate)));
		// 表示期間（月）
		vo.setAryPltSearchRequestMonth(getMonthArray());
		// 表示期間（日）
		vo.setAryPltSearchRequestDay(getDayArray(true));
		vo.setPltSearchRequestYear(DateUtility.getStringYear(systemDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(systemDate));
		vo.setPltSearchRequestDay(DateUtility.getStringDayD(systemDate));
		// プルダウン設定
		setPulldown();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(ManagementRequestRequestDateComparator.class.getName());
	}
	
	/**
	 * 検索を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// エラーメッセージを設定(有効日を決定してください)
			PfMessageUtility.addErrorActivateDateNotSettled(mospParams);
			return;
		}
		// 検索条件確認
		checkSearchCondition(vo.getTxtSearchEmployeeCode(), vo.getTxtSearchEmployeeName(), vo.getPltSearchWorkPlace(),
				vo.getPltSearchEmployment(), vo.getPltSearchSection(), vo.getPltSearchPosition(),
				vo.getPltSearchApprovalType(), vo.getPltSearchState());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 申請情報検索条件取得(ログインユーザ個人ID)
		String personalId = mospParams.getUser().getPersonalId();
		// 申請情報取得
		List<ManagementRequestListDtoInterface> list = timeReference().approvalInfo().getEffectiveList(personalId,
				getFromDate(), getToDate(), getFunctionCodeSet(), vo.getPltSearchState(), getConditionIdSet(),
				getSubordinateIdSet());
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ManagementRequestRequestDateComparator.class.getName());
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
	 * 検索条件個人IDセットを取得する。<br>
	 * @return 検索条件個人IDセット
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected Set<String> getConditionIdSet() throws MospException {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// 検索条件確認
		if (vo.getTxtSearchEmployeeCode().isEmpty() && vo.getTxtSearchEmployeeName().isEmpty()
				&& vo.getPltSearchWorkPlace().isEmpty() && vo.getPltSearchEmployment().isEmpty()
				&& vo.getPltSearchSection().isEmpty() && vo.getPltSearchPosition().isEmpty()) {
			return null;
		}
		// 社員検索クラス取得
		HumanSearchBeanInterface humanSearch = timeReference().subordinateSearch();
		// 社員検索条件設定
		humanSearch.setTargetDate(getSearchTargetDate());
		humanSearch.setStartDate(getFromDate());
		humanSearch.setEndDate(getToDate());
		humanSearch.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		humanSearch.setEmployeeName(vo.getTxtSearchEmployeeName());
		humanSearch.setWorkPlaceCode(vo.getPltSearchWorkPlace());
		humanSearch.setEmploymentContractCode(vo.getPltSearchEmployment());
		humanSearch.setSectionCode(vo.getPltSearchSection());
		humanSearch.setPositionCode(vo.getPltSearchPosition());
		// 検索区分設定
		humanSearch.setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		// 範囲設定無視
		humanSearch.setOperationType(null);
		// 検索条件個人IDセット取得
		return humanSearch.getPersonalIdSet();
	}
	
	/**
	 * 部下個人IDセットを取得する。<br>
	 * @return 部下個人IDセット
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected Set<String> getSubordinateIdSet() throws MospException {
		// 部下検索クラス取得
		HumanSubordinateBeanInterface bean = reference().humanSubordinate();
		// 部下検索条件設定
		bean.setTargetDate(getSearchTargetDate());
		bean.setStartDate(getFromDate());
		bean.setEndDate(getToDate());
		// 部下個人IDセット取得
		return bean.getSubordinateIds();
	}
	
	/**
	 * VOから対象期間自を取得する。<br>
	 * @return 対象期間自
	 * @throws MospException 日付の取得に失敗した場合
	 */
	protected Date getFromDate() throws MospException {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// 年月日取得
		String year = vo.getPltSearchRequestYear();
		String month = vo.getPltSearchRequestMonth();
		String day = vo.getPltSearchRequestDay();
		// 日フィールド確認
		if (day.isEmpty() == false) {
			return getDate(year, month, day);
		}
		// 年月の初日を取得
		return MonthUtility.getYearMonthTermFirstDate(getInt(year), getInt(month), mospParams);
	}
	
	/**
	 * VOから対象期間至を取得する。<br>
	 * @return 対象期間至
	 * @throws MospException 日付の取得に失敗した場合
	 */
	protected Date getToDate() throws MospException {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// 年月日取得
		String year = vo.getPltSearchRequestYear();
		String month = vo.getPltSearchRequestMonth();
		String day = vo.getPltSearchRequestDay();
		// 日フィールド確認
		if (day.isEmpty() == false) {
			return getDate(year, month, day);
		}
		// 年月の末日を取得
		return MonthUtility.getYearMonthTermLastDate(getInt(year), getInt(month), mospParams);
	}
	
	/**
	 * 年月日を指定した場合にはその年月日を取得する。
	 * 年月を指定した場合には年月指定時の基準日を取得する。
	 * VOから基準日を取得。<br>
	 * @return 基準日を取得
	 * @throws MospException 日付の取得に失敗した場合
	 */
	protected Date getSearchTargetDate() throws MospException {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// 年月日取得
		String year = vo.getPltSearchRequestYear();
		String month = vo.getPltSearchRequestMonth();
		String day = vo.getPltSearchRequestDay();
		// 日フィールド確認
		if (day.isEmpty() == false) {
			return getDate(year, month, day);
		}
		// 年月の末日を取得
		return MonthUtility.getYearMonthTargetDate(getInt(year), getInt(month), mospParams);
	}
	
	/**
	 * 機能コードセットを取得する。<br>
	 * VOに申請カテゴリが設定されていない場合は、勤怠管理用機能コードセットを返す。<br>
	 * @return 機能コードセット
	 */
	protected Set<String> getFunctionCodeSet() {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// 申請カテゴリ確認
		if (vo.getPltSearchApprovalType().isEmpty()) {
			// 勤怠管理用機能コードセット取得
			return TimeUtility.getTimeFunctionSet();
		}
		// 申請カテゴリを機能コードセットに設定
		Set<String> functionCodeSet = new HashSet<String>();
		functionCodeSet.add(vo.getPltSearchApprovalType());
		return functionCodeSet;
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(getLong(vo.getAryWorkflow(getTransferredIndex())));
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(ApprovalHistoryAction.class.getName())) {
			// 承認履歴画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(vo.getAryHistoryCmd(getTransferredIndex()));
		} else if (actionName.equals(ApprovalCardAction.class.getName())) {
			mospParams.addGeneralParam(TimeConst.PRM_ROLL_ARRAY, getArray());
			// 承認管理詳細画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(vo.getAryRequestTypeCmd(getTransferredIndex()));
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
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン取得
		setPulldown();
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		vo.setPltSearchRequestYear("");
		vo.setPltSearchRequestMonth("");
		vo.setPltSearchRequestDay("");
		vo.setPltSearchApprovalType("");
		vo.setPltSearchState("");
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchEmployeeName("");
		vo.setPltSearchWorkPlace("");
		vo.setPltSearchEmployment("");
		vo.setPltSearchSection("");
		vo.setPltSearchPosition("");
		vo.setJsSearchConditionRequired(isSearchConditionRequired());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setPulldown() throws MospException {
		// VO準備
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		int targetYear = Integer.parseInt(vo.getPltSearchRequestYear());
		int targetMonth = Integer.parseInt(vo.getPltSearchRequestMonth());
		Date date = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		if (!vo.getPltSearchRequestDay().isEmpty()) {
			date = DateUtility.getDate(vo.getPltSearchRequestYear(), vo.getPltSearchRequestMonth(),
					vo.getPltSearchRequestDay());
		}
		// プルダウンの設定
		MospProperties properties = mospParams.getProperties();
		String[][] aryState = properties.getCodeArray(TimeConst.CODE_EFFECTIVE_STATE, true);
		// 状態
		vo.setAryPltSearchState(aryState);
		// 勤務地
		String[][] aryWorkPlace = reference().workPlace().getNameSelectArray(date, true, null);
		vo.setAryPltSearchWorkPlace(aryWorkPlace);
		// 雇用契約
		String[][] aryEmployment = reference().employmentContract().getNameSelectArray(date, true, null);
		vo.setAryPltSearchEmployment(aryEmployment);
		// 所属
		String[][] aryPltSection = reference().section().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchSection(aryPltSection);
		// 職位
		String[][] aryPltPosition = reference().position().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchPosition(aryPltPosition);
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		RequestListVo vo = (RequestListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblRequestType = new String[list.size()];
		String[] aryLblRequestDate = new String[list.size()];
		String[] aryLblRequestInfo = new String[list.size()];
		String[] aryLblState = new String[list.size()];
		String[] aryStateStyle = new String[list.size()];
		String[] aryWorkflow = new String[list.size()];
		String[] aryStage = new String[list.size()];
		String[] aryState = new String[list.size()];
		String[] aryLblRequestTypeCmd = new String[list.size()];
		String[] aryLblHistoryCmd = new String[list.size()];
		String[] aryLblRequestFunctionCode = new String[list.size()];
		String[] aryBackColor = new String[list.size()];
		SectionReferenceBeanInterface section = reference().section();
		Date date = DateUtility.getSystemDate();
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			ManagementRequestListDtoInterface dto = (ManagementRequestListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
			aryLblSection[i] = section.getSectionAbbr(dto.getSectionCode(), date);
			aryLblRequestType[i] = getRequestTypeForView(dto);
			aryLblRequestTypeCmd[i] = getCardCommand(dto);
			aryLblHistoryCmd[i] = getHistoryCommand(dto.getRequestType());
			aryLblRequestFunctionCode[i] = dto.getRequestType();
			aryLblRequestDate[i] = DateUtility.getStringDateAndDay(dto.getRequestDate());
			aryLblRequestInfo[i] = getRequestInfo(dto);
			aryStateStyle[i] = getStatusColor(dto.getState());
			aryLblState[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryWorkflow[i] = String.valueOf(dto.getWorkflow());
			aryStage[i] = String.valueOf(dto.getStage());
			aryState[i] = dto.getState();
			aryBackColor[i] = setBackColor(dto.getPersonalId(), dto.getRequestDate(), dto.getRequestType());
		}
		// データをVOに設定
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblRequestType(aryLblRequestType);
		vo.setAryLblRequestDate(aryLblRequestDate);
		vo.setAryLblRequestInfo(aryLblRequestInfo);
		vo.setAryLblState(aryLblState);
		vo.setAryStateStyle(aryStateStyle);
		vo.setAryRequestTypeCmd(aryLblRequestTypeCmd);
		vo.setAryHistoryCmd(aryLblHistoryCmd);
		vo.setAryRequestFunctionCode(aryLblRequestFunctionCode);
		vo.setAryWorkflow(aryWorkflow);
		vo.setAryStage(aryStage);
		vo.setAryState(aryState);
		vo.setAryBackColor(aryBackColor);
	}
	
	/**
	 * 詳細画面のコマンドを取得する。<br>
	 * @param dto 対象DTO
	 * @return コマンド
	 */
	protected String getCardCommand(ManagementRequestListDtoInterface dto) {
		if (dto == null) {
			return "";
		}
		if (TimeConst.CODE_FUNCTION_WORK_MANGE.equals(dto.getRequestType())) {
			// 勤怠
			return ApprovalCardAction.CMD_APPROVAL_CONFIRMATION_ATTENDANCE;
		} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(dto.getRequestType())) {
			// 残業
			return ApprovalCardAction.CMD_APPROVAL_CONFIRMATION_OVERTIME;
		} else if (TimeConst.CODE_FUNCTION_VACATION.equals(dto.getRequestType())) {
			// 休暇
			return ApprovalCardAction.CMD_APPROVAL_CONFIRMATION_HOLIDAY;
		} else if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(dto.getRequestType())) {
			// 振出・休出
			return ApprovalCardAction.CMD_APPROVAL_CONFIRMATION_WORKONHOLIDAY;
		} else if (TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(dto.getRequestType())) {
			// 代休
			return ApprovalCardAction.CMD_APPROVAL_CONFIRMATION_SUBHOLIDAY;
		} else if (TimeConst.CODE_FUNCTION_DIFFERENCE.equals(dto.getRequestType())) {
			// 時差出勤
			return ApprovalCardAction.CMD_APPROVAL_CONFIRMATION_DIFFERENCE;
		} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(dto.getRequestType())) {
			// 勤務形態変更
			return ApprovalCardAction.CMD_APPROVAL_CONFIRMATION_WORKTYPECHANGE;
		}
		return "";
	}
	
}
