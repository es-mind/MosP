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
package jp.mosp.time.management.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.comparator.settings.ManagementRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;
import jp.mosp.time.input.action.ApprovalHistoryAction;
import jp.mosp.time.management.vo.ApprovalListVo;

/**
 * 勤怠管理権限者が自分の部下の未承認情報について確認・検索と一括承認を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SHOW_SERCH}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_BATCH_APPROVAL}
 * </li><li>
 * {@link #CMD_BATCH_CANCEL}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li></ul>
 */
public class ApprovalListAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW			= "TM2310";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 初期処理及び検索処理をまとめて行う。<br>
	 * ポータル画面から遷移をする場合などに用いる。<br>
	 */
	public static final String	CMD_SHOW_SERCH		= "TM2311";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 各申請項目ごとの未承認表示ボタンをクリックした際に現在ログインしているユーザが承認すべき申請情報を表示する。<br>
	 * 表示対象となる申請項目はクリックした未承認表示ボタンにより異なる。<br>
	 * 一覧表示の際には日付でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "TM2312";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から改めて遷移した場合、承認状況の更新を反映し、<br>
	 * 直前の検索条件（申請項目）で検索を行って画面表示をする。<br>
	 */
	public static final String	CMD_RE_SHOW			= "TM2313";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String	CMD_TRANSFER		= "TM2315";
	
	/**
	 * 一括承認コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードの申請を一括で承認するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_APPROVAL	= "TM2316";
	
	/**
	 * 一括解除承認コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードの申請を一括で解除承認するよう繰り返し処理を行う。<br>
	 * チェックが1件も入っていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_CANCEL	= "TM2317";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "TM2318";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "TM2319";
	
	/**
	 * 未承認一覧画面メニューキー。<br>
	 * ポータル画面から遷移した場合に、範囲設定をするために用いる。<br>
	 */
	public static final String	MENU_APPROVAL_LIST	= "ApprovalList";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public ApprovalListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ApprovalListVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SHOW_SERCH)) {
			// 表示検索
			prepareVo(false, false);
			show();
			search(true, false);
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search(true, true);
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search(true, false);
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_BATCH_APPROVAL)) {
			// 更新
			prepareVo();
			batchUpdate();
		} else if (mospParams.getCommand().equals(CMD_BATCH_CANCEL)) {
			// 解除承認
			prepareVo();
			batchCancel();
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
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void show() throws MospException {
		// 操作範囲確認(ポータル画面から遷移してくる場合)
		checkRangeMap(MENU_APPROVAL_LIST);
		// VO準備
		ApprovalListVo vo = (ApprovalListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(ManagementRequestRequestDateComparator.class.getName());
		// 検索(各申請件数のみ取得)
		search(false, false);
	}
	
	/**
	 * 検索処理を行う。<br>
	 * 各申請件数及び一覧を取得し、VOに設定する。<br>
	 * 一覧要否フラグがtrueの場合、各申請件数のみを取得する。<br>
	 * @param needList            一覧要否フラグ(true：一覧要、false：一覧不要)
	 * @param needNoResultMessage 検索結果無し時メッセージ要否(true：要、false：不要)
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search(boolean needList, boolean needNoResultMessage) throws MospException {
		// VO準備
		ApprovalListVo vo = (ApprovalListVo)mospParams.getVo();
		// 申請情報参照クラス準備
		ApprovalInfoReferenceBeanInterface approvalRef = timeReference().approvalInfo();
		// ログインユーザの個人IDを取得
		String personalId = mospParams.getUser().getPersonalId();
		// ログインユーザ個人IDで承認可能な勤怠ワークフロー情報群を取得
		Map<String, Map<Long, WorkflowDtoInterface>> approvableMap = approvalRef.getApprovableMap(personalId);
		// ログインユーザ個人IDで代理承認可能な勤怠ワークフロー情報群を取得
		Map<String, Map<Long, WorkflowDtoInterface>> subApprovableMap = approvalRef.getSubApprovableMap(personalId,
				approvableMap);
		// ログインユーザ個人IDで解除承認可能な勤怠ワークフロー情報群を取得
		Map<String, Map<Long, WorkflowDtoInterface>> cancelableMap = approvalRef.getCancelableMap(personalId);
		// ログインユーザ個人IDで代理承認可能な勤怠ワークフロー情報群を取得
		Map<String, Map<Long, WorkflowDtoInterface>> subCancelableMap = approvalRef.getSubCancelableMap(personalId,
				approvableMap);
		// 各申請件数設定
		setApprovableConut(approvableMap, subApprovableMap, cancelableMap, subCancelableMap);
		// 一覧要否フラグ確認
		if (needList == false) {
			return;
		}
		String approvalType = getTransferredGenericCode();
		if (approvalType == null) {
			approvalType = vo.getApprovalType();
		}
		vo.setApprovalType(approvalType);
		// 機能コード取得(譲渡汎用コード)
		String functionCode = getTransferredType();
		// 機能コード確認
		if (functionCode == null) {
			// 機能コード取得(VO)
			functionCode = vo.getFunctionCode();
		}
		// 機能コードをVOに設定
		vo.setFunctionCode(functionCode);
		List<ManagementRequestListDtoInterface> list = null;
		if (PlatformConst.CODE_STATUS_CANCEL_APPLY.equals(approvalType)
				|| PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY.equals(approvalType)) {
			list = approvalRef.getApprovableList(cancelableMap, subCancelableMap, "");
		} else {
			// 機能コードで承認可能勤怠申請情報リストを取得
			list = approvalRef.getApprovableList(approvableMap, subApprovableMap, functionCode);
		}
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ManagementRequestRequestDateComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty() && needNoResultMessage) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		ApprovalListVo vo = (ApprovalListVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(ApprovalHistoryAction.class.getName())) {
			// 承認履歴画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(vo.getAryLblRequestTypeHistoryCmd(getTransferredIndex()));
		} else if (actionName.equals(ApprovalCardAction.class.getName())) {
			mospParams.addGeneralParam(TimeConst.PRM_ROLL_ARRAY, getArray());
			// 承認管理詳細画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(vo.getAryLblRequestTypeCmd(getTransferredIndex()));
		}
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		ApprovalListVo vo = (ApprovalListVo)mospParams.getVo();
		// 一括更新準備
		long[] aryWorkflow = getIdArray(vo.getCkbSelect());
		// 一括更新処理
		time().timeApproval().approve(aryWorkflow, null);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 再検索
		search(true, false);
	}
	
	/**
	 * 一括解除承認処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void batchCancel() throws MospException {
		// VO取得
		ApprovalListVo vo = (ApprovalListVo)mospParams.getVo();
		// 一括解除承認準備
		long[] aryWorkflow = getIdArray(vo.getCkbSelect());
		// 一括解除承認処理
		time().timeApproval().cancelApprove(aryWorkflow, null);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 一覧選択情報を初期化
		initCkbSelect();
		// 再検索
		search(true, false);
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
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		ApprovalListVo vo = (ApprovalListVo)mospParams.getVo();
		vo.setLblAttendance("0");
		vo.setLblOverTime("0");
		vo.setLblHoliday("0");
		vo.setLblWorkOnHoliday("0");
		vo.setLblSubHoliday("0");
		vo.setLblWorkTypeChange("0");
		vo.setLblDifference("0");
		vo.setLblTotalApproval("0");
		vo.setLblTotalCancel("0");
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		ApprovalListVo vo = (ApprovalListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblRequestType = new String[list.size()];
		String[] aryLblRequestDate = new String[list.size()];
		String[] aryRequestDate = new String[list.size()];
		String[] aryLblRequestInfo = new String[list.size()];
		String[] aryLblState = new String[list.size()];
		String[] aryStateStyle = new String[list.size()];
		String[] aryLblRequestFunctionCode = new String[list.size()];
		String[] aryLblRequestTypeCmd = new String[list.size()];
		String[] aryLblRequestTypeHistoryCmd = new String[list.size()];
		String[] aryState = new String[list.size()];
		String[] aryStage = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		String[] aryClasOverTimeIn = new String[list.size()];
		String[] aryBackColor = new String[list.size()];
		SectionReferenceBeanInterface getSection = reference().section();
		Date date = DateUtility.getSystemDate();
		// データ作成
		int i = 0;
		for (BaseDtoInterface baseDto : list) {
			// リストから情報を取得
			ManagementRequestListDtoInterface dto = (ManagementRequestListDtoInterface)baseDto;
			// 配列に情報を設定
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblSection[i] = getSection.getSectionAbbr(dto.getSectionCode(), date);
			aryLblRequestType[i] = getRequestTypeForView(dto);
			aryLblRequestTypeCmd[i] = getCardCommand(dto);
			aryLblRequestTypeHistoryCmd[i] = getHistoryCommand(dto.getRequestType());
			aryLblRequestFunctionCode[i] = dto.getRequestType();
			aryLblRequestDate[i] = DateUtility.getStringDateAndDay(dto.getRequestDate());
			aryRequestDate[i] = DateUtility.getStringDate(dto.getRequestDate());
			aryLblRequestInfo[i] = getRequestInfo(dto);
			aryLblState[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryStateStyle[i] = getStatusColor(dto.getState());
			aryState[i] = String.valueOf(dto.getState());
			aryStage[i] = String.valueOf(dto.getStage());
			aryWorkflow[i] = dto.getWorkflow();
			aryBackColor[i] = setBackColor(dto.getPersonalId(), dto.getRequestDate(), dto.getRequestType());
			aryClasOverTimeIn[i] = "";
			i++;
		}
		// データをVOに設定
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblRequestType(aryLblRequestType);
		vo.setAryLblRequestDate(aryLblRequestDate);
		vo.setAryRequestDate(aryRequestDate);
		vo.setAryLblRequestInfo(aryLblRequestInfo);
		vo.setAryLblState(aryLblState);
		vo.setAryStateStyle(aryStateStyle);
		vo.setAryLblRequestTypeCmd(aryLblRequestTypeCmd);
		vo.setAryLblRequestTypeHistoryCmd(aryLblRequestTypeHistoryCmd);
		vo.setAryLblRequestFunctionCode(aryLblRequestFunctionCode);
		vo.setAryState(aryState);
		vo.setAryStage(aryStage);
		vo.setAryWorkflow(aryWorkflow);
		vo.setAryClasOverTimeIn(aryClasOverTimeIn);
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
			return ApprovalCardAction.CMD_APPROVAL_ATTENDANCE;
		} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(dto.getRequestType())) {
			// 残業
			return ApprovalCardAction.CMD_APPROVAL_OVERTIME;
		} else if (TimeConst.CODE_FUNCTION_VACATION.equals(dto.getRequestType())) {
			// 休暇
			return ApprovalCardAction.CMD_APPROVAL_HOLIDAY;
		} else if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(dto.getRequestType())) {
			// 振出・休出
			return ApprovalCardAction.CMD_APPROVAL_WORKONHOLIDAY;
		} else if (TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(dto.getRequestType())) {
			// 代休
			return ApprovalCardAction.CMD_APPROVAL_SUBHOLIDAY;
		} else if (TimeConst.CODE_FUNCTION_DIFFERENCE.equals(dto.getRequestType())) {
			// 時差出勤
			return ApprovalCardAction.CMD_APPROVAL_DIFFERENCE;
		} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(dto.getRequestType())) {
			// 勤務形態変更
			return ApprovalCardAction.CMD_APPROVAL_WORKTYPECHANGE;
		}
		return "";
	}
	
	/**
	 * 各承認可能申請件数をVOに設定する。<br>
	 * @param approvableMap    承認可能申請情報群(キー：機能コード)
	 * @param subApprovableMap 代理承認可能申請情報群(キー：機能コード)
	 * @param cancelableMap    解除承認可能申請情報群(キー：機能コード)
	 * @param subCancelableMap 代理解除承認可能申請情報群(キー：機能コード)
	 */
	protected void setApprovableConut(Map<String, Map<Long, WorkflowDtoInterface>> approvableMap,
			Map<String, Map<Long, WorkflowDtoInterface>> subApprovableMap,
			Map<String, Map<Long, WorkflowDtoInterface>> cancelableMap,
			Map<String, Map<Long, WorkflowDtoInterface>> subCancelableMap) {
		// VO取得
		ApprovalListVo vo = (ApprovalListVo)mospParams.getVo();
		// 各承認可能申請件数を取得
		int attendanceCount = approvableMap.get(TimeConst.CODE_FUNCTION_WORK_MANGE).size();
		int overTimeCount = approvableMap.get(TimeConst.CODE_FUNCTION_OVER_WORK).size();
		int holidayCount = approvableMap.get(TimeConst.CODE_FUNCTION_VACATION).size();
		int workOnHolidayCount = approvableMap.get(TimeConst.CODE_FUNCTION_WORK_HOLIDAY).size();
		int subHolidayCount = approvableMap.get(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY).size();
		int differenceCount = approvableMap.get(TimeConst.CODE_FUNCTION_DIFFERENCE).size();
		int workTypeChangeCount = approvableMap.get(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE).size();
		// 代理分件数追加
		attendanceCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_WORK_MANGE).size();
		overTimeCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_OVER_WORK).size();
		holidayCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_VACATION).size();
		workOnHolidayCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_WORK_HOLIDAY).size();
		subHolidayCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY).size();
		differenceCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_DIFFERENCE).size();
		workTypeChangeCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE).size();
		// VOに設定
		vo.setLblAttendance(String.valueOf(attendanceCount));
		vo.setLblOverTime(String.valueOf(overTimeCount));
		vo.setLblHoliday(String.valueOf(holidayCount));
		vo.setLblWorkOnHoliday(String.valueOf(workOnHolidayCount));
		vo.setLblSubHoliday(String.valueOf(subHolidayCount));
		vo.setLblDifference(String.valueOf(differenceCount));
		vo.setLblWorkTypeChange(String.valueOf(workTypeChangeCount));
		vo.setLblTotalApproval(String.valueOf(attendanceCount + overTimeCount + holidayCount + workOnHolidayCount
				+ subHolidayCount + differenceCount + workTypeChangeCount));
		vo.setLblTotalCancel(String.valueOf(cancelableMap.get(TimeConst.CODE_FUNCTION_WORK_MANGE).size()
				+ cancelableMap.get(TimeConst.CODE_FUNCTION_OVER_WORK).size()
				+ cancelableMap.get(TimeConst.CODE_FUNCTION_VACATION).size()
				+ cancelableMap.get(TimeConst.CODE_FUNCTION_WORK_HOLIDAY).size()
				+ cancelableMap.get(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY).size()
				+ cancelableMap.get(TimeConst.CODE_FUNCTION_DIFFERENCE).size()
				+ cancelableMap.get(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE).size()
				+ subCancelableMap.get(TimeConst.CODE_FUNCTION_WORK_MANGE).size()
				+ subCancelableMap.get(TimeConst.CODE_FUNCTION_OVER_WORK).size()
				+ subCancelableMap.get(TimeConst.CODE_FUNCTION_VACATION).size()
				+ subCancelableMap.get(TimeConst.CODE_FUNCTION_WORK_HOLIDAY).size()
				+ subCancelableMap.get(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY).size()
				+ subCancelableMap.get(TimeConst.CODE_FUNCTION_DIFFERENCE).size()
				+ subCancelableMap.get(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE).size()));
	}
	
	@Override
	protected String getStatusColor(String status) {
		if (PlatformConst.CODE_STATUS_CANCEL.equals(status)) {
			// 承認解除の場合
			return "";
		}
		return super.getStatusColor(status);
	}
	
}
