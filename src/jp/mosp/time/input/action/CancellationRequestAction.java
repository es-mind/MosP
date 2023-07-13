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
package jp.mosp.time.input.action;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.comparator.settings.ManagementRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.input.vo.CancellationRequestVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * 承認解除申請を行う。<br>
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
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_APPLI}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li></ul>
 */
public class CancellationRequestAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの承認解除申請画面を表示する。<br>
	 */
	public static final String		CMD_SHOW		= "TM011000";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に申請情報の検索を行う。
	 */
	public static final String		CMD_SEARCH		= "TM011002";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 一度この画面を表示してからパンくずリスト等で再度遷移する場合は保持していた検索条件で検索を行って再表示する。<br>
	 */
	public static final String		CMD_RE_SHOW		= "TM011003";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String		CMD_SORT		= "TM011007";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String		CMD_PAGE		= "TM011008";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を申請テーブルの各入力欄にそれぞれ表示させる。<br>
	 */
	public static final String		CMD_EDIT_MODE	= "TM011011";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容をワークフローコメントテーブルに登録し、承認解除申請を行う。<br>
	 * 理由の項目が入力されていないといった状態で申請を行おうとした場合はエラーメッセージにて通知し、<br>
	 * 申請は実行されない。<br>
	 */
	public static final String		CMD_APPLI		= "TM011015";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String		CMD_TRANSFER	= "TM011020";
	
	/**
	 * 機能コード。<br>
	 */
	protected static final String	CODE_FUNCTION	= TimeConst.CODE_FUNCTION_CANCELLATION;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public CancellationRequestAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new CancellationRequestVo();
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
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 編集モード切替
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_APPLI)) {
			// 申請
			prepareVo();
			appli();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void show() throws MospException {
		// 個人ID取得(ログインユーザ情報から)
		String personalId = mospParams.getUser().getPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 新規登録モード設定
		insertMode();
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getSystemDate(), CODE_FUNCTION);
	}
	
	/**
	 * 検索する。
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// 年月日取得
		int year = getInt(vo.getPltSearchRequestYear());
		int month = getInt(vo.getPltSearchRequestMonth());
		String day = vo.getPltSearchRequestDay();
		// 期間開始日および終了日を準備
		Date firstDate = null;
		Date lastDate = null;
		// 日が指定されていない場合
		if (day.isEmpty()) {
			// 年月で設定適用エンティティを取得
			ApplicationEntity application = timeReference().master().getApplicationEntity(personalId, year, month);
			// 締期間の開始及び最終日を取得
			firstDate = application.getCutoffEntity().getCutoffFirstDate(year, month, mospParams);
			lastDate = application.getCutoffEntity().getCutoffLastDate(year, month, mospParams);
			
		} else {
			// 指定年月日を取得(日が指定された場合)
			firstDate = DateUtility.getDate(year, month, getInt(day));
			lastDate = DateUtility.getDate(year, month, getInt(day));
		}
		
		// 申請情報取得
		List<ManagementRequestListDtoInterface> list = timeReference().approvalInfo()
			.getCompletedList(vo.getPersonalId(), firstDate, lastDate, getFunctionCodeSet());
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ManagementRequestRequestDateComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty() && mospParams.getCommand().equals(CMD_SEARCH)) {
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
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// プルダウン設定
		setPulldown();
		// 状態
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		// ソートキー設定
		vo.setComparatorName(ManagementRequestRequestDateComparator.class.getName());
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * ワークフローで対象情報を取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		long workflow = vo.getAryWorkflow()[getTransferredIndex()];
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
		ManagementRequestListDtoInterface dto = timeReference().approvalInfo().getManagementRequestListDto(workflowDto,
				false);
		// 存在確認
		checkSelectedDataExist(dto);
		// 利用不可である場合
		if (isAvailable(dto.getRequestDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// VOにセット
		setVoFields(dto);
		// 理由を初期化
		vo.setTxtEditRequestReason("");
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli() throws MospException {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		// 申請情報の取得
		BaseDtoInterface dto = timeReference().approvalInfo().getRequestDtoForWorkflow(vo.getWorkflow(), true);
		// 解除申請時の確認処理
		time().timeApproval().checkCancelAppli(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		if (workflowDto == null) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		if (workflowDto.getFunctionCode().equals(TimeConst.CODE_FUNCTION_WORK_MANGE)) {
			// 勤怠申請の場合
			platform().workflowRegist().cancelAttendanceAppli(workflowDto, vo.getTxtEditRequestReason(),
					vo.getCkbWithdrawn());
			// 登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			if (workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY)
					&& isSelfApproval(workflowDto)) {
				// 自己承認かつ、承認解除申請(取下希望)の場合
				time.attendanceRegist().delete(workflowDto.getPersonalId(), workflowDto.getWorkflowDate());
				// 勤怠トランザクション登録
				time.timeApproval().registAttendanceTransaction(workflowDto.getPersonalId(),
						workflowDto.getWorkflowDate(), dto);
			}
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
		} else {
			platform().workflowRegist().cancelAppli(workflowDto, vo.getTxtEditRequestReason(), vo.getCkbWithdrawn());
			// 登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// 自己承認時の取下げ
			withdrawnSelfApproval(workflowDto, dto);
			// 登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
		}
		
		// コミット
		commit();
		
		// 申請成功メッセージ設定
		addCancellAppliMessage();
		// 登録結果確認
		if (!mospParams.hasErrorMessage())
		
		{
			// 登録が成功した場合、初期状態に戻す。
			insertMode();
			// 申請年月日が含まれる締月を取得し検索条件に設定
			Date searchDate = timeReference().cutoffUtil().getCutoffMonth(workflowDto.getPersonalId(),
					workflowDto.getWorkflowDate());
			vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
			vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
			search();
		}
	}
	
	/**
	 * 自己承認時の取下処理
	 * @param workflowDto
	 * @param dto
	 * @throws MospException
	 */
	protected void withdrawnSelfApproval(WorkflowDtoInterface workflowDto, BaseDtoInterface dto) throws MospException {
		// 自己承認確認
		if (isSelfApproval(workflowDto)
				&& workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_WITHDRAWN)) {
			
			// 自己承認かつ、承認解除申請(取下希望)の場合
			if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(workflowDto.getFunctionCode())) {
				// 振出・休出申請の場合は勤怠を削除する
				time.attendanceRegist().delete(workflowDto.getPersonalId(), workflowDto.getWorkflowDate());
			} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(workflowDto.getFunctionCode())
					|| TimeConst.CODE_FUNCTION_VACATION.equals(workflowDto.getFunctionCode())
					|| TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(workflowDto.getFunctionCode())
					|| TimeConst.CODE_FUNCTION_DIFFERENCE.equals(workflowDto.getFunctionCode())) {
				// 残業申請・休暇申請・代休申請・時差出勤申請の場合は勤怠を下書し直す
				time.timeApproval().reDraft(workflowDto.getPersonalId(), workflowDto.getWorkflowDate(), false, false,
						false);
			} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(workflowDto.getFunctionCode())) {
				// 勤務形態変更申請の場合は勤怠を下書し直す
				time.timeApproval().reDraft(workflowDto.getPersonalId(), workflowDto.getWorkflowDate(), false, false,
						true);
			}
			// 勤怠トランザクション登録
			time.timeApproval().registAttendanceTransaction(workflowDto.getPersonalId(), workflowDto.getWorkflowDate(),
					dto);
		}
		
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow()[getTransferredIndex()]);
		// 承認履歴画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(vo.getAryHistoryCmd()[getTransferredIndex()]);
	}
	
	/**
	 * 機能コードセットを取得する。<br>
	 * VOに申請カテゴリが設定されていない場合は、勤怠管理用機能コードセットを返す。<br>
	 * @return 機能コードセット
	 */
	protected Set<String> getFunctionCodeSet() {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
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
	 * 初期値を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		Date date = getSystemDate();
		vo.setPltSearchApprovalType("");
		// システム日付が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(vo.getPersonalId(), date);
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
		vo.setPltSearchRequestDay("");
		vo.setLblRequestDate("");
		vo.setLblRequestType("");
		vo.setLblRequestInfo("");
		vo.setLblState("");
		vo.setTxtEditRequestReason("");
		vo.setCkbWithdrawn(MospConst.CHECKBOX_OFF);
		vo.setJsRequestType("");
	}
	
	/**
	 * プルダウン設定。<br>
	 */
	protected void setPulldown() {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		vo.setAryPltSearchRequestYear(getYearArray(DateUtility.getYear(getSystemDate())));
		vo.setAryPltSearchRequestMonth(getMonthArray());
		vo.setAryPltSearchRequestDay(getDayArray(true));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoFields(ManagementRequestListDtoInterface dto) throws MospException {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setWorkflow(dto.getWorkflow());
		vo.setLblRequestDate(DateUtility.getStringDateAndDay(dto.getRequestDate()));
		vo.setLblRequestType(getRequestTypeForView(dto));
		vo.setLblRequestInfo(getRequestInfo(dto));
		vo.setLblState(getStatusStageValueView(dto.getState(), dto.getStage()));
		if (dto.getRequestType().equals(TimeConst.CODE_FUNCTION_WORK_MANGE)) {
			vo.setCkbWithdrawn(MospConst.CHECKBOX_OFF);
			vo.setJsRequestType(TimeConst.CODE_FUNCTION_WORK_MANGE);
		} else {
			vo.setCkbWithdrawn(MospConst.CHECKBOX_ON);
			vo.setJsRequestType("");
		}
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		CancellationRequestVo vo = (CancellationRequestVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblRequestDate = new String[list.size()];
		String[] aryLblRequestType = new String[list.size()];
		String[] aryLblRequestInfo = new String[list.size()];
		String[] aryLblWorkflowStatus = new String[list.size()];
		String[] aryLblApproverName = new String[list.size()];
		String[] aryBackColor = new String[list.size()];
		String[] aryHistoryCmd = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			ManagementRequestListDtoInterface dto = (ManagementRequestListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblRequestDate[i] = DateUtility.getStringDateAndDay(dto.getRequestDate());
			aryLblRequestType[i] = getRequestTypeForView(dto);
			aryLblRequestInfo[i] = getRequestInfo(dto);
			aryLblWorkflowStatus[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryLblApproverName[i] = dto.getApproverName();
			aryBackColor[i] = setBackColor(dto.getPersonalId(), dto.getRequestDate(), dto.getRequestType());
			aryHistoryCmd[i] = getHistoryCommand(dto.getRequestType());
			aryWorkflow[i] = dto.getWorkflow();
		}
		// データをVOに設定
		vo.setAryLblRequestDate(aryLblRequestDate);
		vo.setAryLblRequestType(aryLblRequestType);
		vo.setAryLblRequestInfo(aryLblRequestInfo);
		vo.setAryLblState(aryLblWorkflowStatus);
		vo.setAryLblApproverName(aryLblApproverName);
		vo.setAryBackColor(aryBackColor);
		vo.setAryHistoryCmd(aryHistoryCmd);
		vo.setAryWorkflow(aryWorkflow);
	}
	
	/**
	 * ワークフローが自己承認であるかを確認する。<br>
	 * @param dto 確認対象ワークフロー情報
	 * @return 自己承認確認結果(true：自己承認、false：自己承認でない)
	 */
	protected boolean isSelfApproval(WorkflowDtoInterface dto) {
		// 承認者ID確認
		if (dto.getApproverId().equals(PlatformConst.APPROVAL_ROUTE_SELF)) {
			return true;
		}
		// ルートコード確認
		if (dto.getRouteCode().equals(PlatformConst.APPROVAL_ROUTE_SELF)) {
			return true;
		}
		return false;
	}
}
