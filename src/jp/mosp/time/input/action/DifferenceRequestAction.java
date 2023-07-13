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
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestRegistBeanInterface;
import jp.mosp.time.bean.DifferenceRequestSearchBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.comparator.settings.DifferenceRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestListDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.input.vo.DifferenceRequestVo;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 時差出勤申請情報の確認と編集を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_DRAFT}
 * </li><li>
 * {@link #CMD_APPLI}
 * </li><li>
 * {@link #CMD_WORKTIME_CALC}
 * </li><li>
 * {@link #CMD_WITHDRAWN}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_BATCH_WITHDRAWN}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_SET_VIEW_PERIOD}
 * </li><li>
 * {@link #CMD_SELECT_ACTIVATION_DATE}
 * </li></ul>
 */
public class DifferenceRequestAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの時差出勤申請画面を表示する。<br>
	 */
	public static final String		CMD_SHOW					= "TM1000";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 時差出勤申請画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW				= "TM1001";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に時差出勤申請情報の検索を行う。
	 */
	public static final String		CMD_SEARCH					= "TM1002";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 一度この画面を表示した後、パンくずリスト等を用いて他の画面から改めて遷移した場合、<br>
	 * 各種情報の登録状況を更新した上で保持していた検索条件で検索を行って画面を再表示する。<br>
	 */
	public static final String		CMD_RE_SEARCH				= "TM1003";
	
	/**
	 * 下書きコマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を時差出勤情報テーブルに登録し、下書情報として保存する。<br>
	 */
	public static final String		CMD_DRAFT					= "TM1004";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を時差出勤情報テーブルに登録し、時差出勤申請を行う。以降、このレコードは上長が差戻をしない限り編集不可となる。<br>
	 * 開始年月日で申請不可な日付が選択されている、申請時間が0時間0分のまま、申請理由の項目が入力されていない、<br>
	 * といった状態で申請を行おうとした場合はエラーメッセージにて通知し、申請は実行されない。<br>
	 */
	public static final String		CMD_APPLI					= "TM1005";
	
	/**
	 * 勤務時刻再計算コマンド。<br>
	 * <br>
	 * 決定した出勤日の勤務形態をカレンダより取得し、時差出勤開始時刻にその勤務形態の勤務時間と休憩時間を加えた時刻を時差出勤終了時刻欄に表示する。<br>
	 */
	public static final String		CMD_WORKTIME_CALC			= "TM1006";
	
	/**
	 * 取下コマンド。<br>
	 * <br>
	 * 下書状態または差戻状態で登録されていたレコードの取下を行う。取下後、対象の時差出勤申請情報は未申請状態へ戻る。<br>
	 */
	public static final String		CMD_WITHDRAWN				= "TM1007";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String		CMD_SORT					= "TM1008";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String		CMD_PAGE					= "TM1009";
	
	/**
	 * 一括取下コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている未承認状態のレコードの取下処理を繰り返し行う。
	 * ひとつもチェックが入っていない状態で一括取下ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。
	 */
	public static final String		CMD_BATCH_WITHDRAWN			= "TM1036";
	
	/**
	 * 出勤日決定コマンド。<br>
	 * <br>
	 * 入力した時差出勤日の勤務形態をカレンダより取得して略称をラベルに出力する。<br>
	 * 勤務開始時刻は勤務形態プルダウンで選択したレコードの出勤時刻が入力されている状態にする。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE		= "TM1090";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 申請テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 申請テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE				= "TM1091";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を申請テーブルの各入力欄にそれぞれ表示させる。
	 * 申請テーブルヘッダに新規登録モード切替リンクを表示させる。<br>
	 */
	public static final String		CMD_EDIT_MODE				= "TM1092";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている下書状態のレコードの申請処理を繰り返し行う。<br>
	 * ひとつもチェックが入っていない状態で一括更新ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。<br>
	 */
	public static final String		CMD_BATCH_UPDATE			= "TM1095";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String		CMD_TRANSFER				= "TM1096";
	
	/**
	 * 表示期間決定コマンド。<br>
	 * <br>
	 * 入力した表示期間時点で有効な勤務形態情報を取得し、その略称と勤務時間を勤務形態プルダウンに表示する。<br>
	 */
	public static final String		CMD_SET_VIEW_PERIOD			= "TM1097";
	
	/**
	 * 出勤日決定コマンド。<br>
	 * <br>
	 * 入力した時差出勤日の勤務形態をカレンダより取得して略称をラベルに出力する。<br>
	 * 勤務開始時刻は勤務形態プルダウンで選択したレコードの出勤時刻が入力されている状態にする。<br>
	 */
	public static final String		CMD_SELECT_ACTIVATION_DATE	= "TM1098";
	
	/**
	 * 機能コード。<br>
	 */
	protected static final String	CODE_FUNCTION				= TimeConst.CODE_FUNCTION_DIFFERENCE;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public DifferenceRequestAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new DifferenceRequestVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_DRAFT)) {
			// 下書き
			prepareVo();
			draft();
		} else if (mospParams.getCommand().equals(CMD_APPLI)) {
			// 申請
			prepareVo();
			appli();
		} else if (mospParams.getCommand().equals(CMD_WORKTIME_CALC)) {
			// 勤務時刻再計算
			prepareVo();
			workTimeCalc();
		} else if (mospParams.getCommand().equals(CMD_WITHDRAWN)) {
			// 取下
			prepareVo();
			withdrawn();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_BATCH_WITHDRAWN)) {
			// 一括取下
			prepareVo();
			batchWithdrawn();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 出勤日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 編集モード切替
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_SET_VIEW_PERIOD)) {
			// 表示期間決定
			prepareVo();
			setViewPeriod();
		} else if (mospParams.getCommand().equals(CMD_SELECT_ACTIVATION_DATE)) {
			// 出勤日決定
			prepareVo(false, false);
			selectActivationDate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void show() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
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
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(DifferenceRequestRequestDateComparator.class.getName());
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// 個人ID確認
		if (personalId == null || personalId.isEmpty()) {
			// ログインユーザの個人IDを取得
			personalId = mospParams.getUser().getPersonalId();
		}
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 新規登録モード設定
		insertMode();
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(DifferenceRequestRequestDateComparator.class.getName());
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
	}
	
	/**
	* 下書処理を行う。<br>
	 * @throws MospException 例外処理発生時
	*/
	protected void draft() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 登録クラス取得
		DifferenceRequestRegistBeanInterface regist = time().differenceRequestRegist();
		Date startDate = DateUtility.getDate(vo.getPltEditRequestYear(), vo.getPltEditRequestMonth(),
				vo.getPltEditRequestDay());
		Date endDate = startDate;
		if (MospConst.CHECKBOX_ON.equals(vo.getCkbEndDate())) {
			// 期間指定の場合
			endDate = DateUtility.getDate(vo.getPltEditEndYear(), vo.getPltEditEndMonth(), vo.getPltEditEndDay());
		}
		// 終了日が開始日より前である場合
		if (endDate.before(startDate)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorInputValueInvalid(mospParams, PfNameUtility.term(mospParams));
			return;
		}
		Date targetDate = startDate;
		while (!targetDate.after(endDate)) {
			// 退職済み確認
			if (reference().retirement().isRetired(vo.getPersonalId(), targetDate)) {
				addEmployeeRetiredMessage();
				return;
			}
			// 休職期間確認
			if (reference().suspension().isSuspended(vo.getPersonalId(), targetDate)) {
				addEmployeeSuspendedMessage();
				return;
			}
			// DTOの準備
			DifferenceRequestDtoInterface dto = timeReference().differenceRequest().findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			if (targetDate.compareTo(startDate) != 0) {
				dto = regist.getInitDto();
			}
			// DTOに値を設定
			setDtoFields(dto, targetDate);
			if (targetDate.compareTo(startDate) != 0) {
				dto.setTmdDifferenceRequestId(0);
			}
			// 妥当性チェック
			regist.validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 申請日チェック
			regist.checkRequest(dto);
			if (mospParams.hasErrorMessage()) {
				mospParams.getErrorMessageList().clear();
				// 1日加算
				targetDate = DateUtility.addDay(targetDate, 1);
				continue;
			}
			// 申請の相関チェック
			regist.checkDraft(dto);
			// 登録クラスの取得
			WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto == null) {
				workflowDto = workflowRegist.getInitDto();
				workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_DIFFERENCE);
			}
			workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
			// 登録後ワークフローの取得
			workflowDto = workflowRegist.draft(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME);
			if (workflowDto != null) {
				// ワークフローコメント登録
				platform().workflowCommentRegist().addComment(workflowDto, mospParams.getUser().getPersonalId(),
						PfMessageUtility.getDraftSucceed(mospParams));
				// ワークフロー番号セット
				dto.setWorkflow(workflowDto.getWorkflow());
				// 登録
				regist.regist(dto);
			}
			// 1日加算
			targetDate = DateUtility.addDay(targetDate, 1);
		}
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 下書成功メッセージ設定
		addDraftMessage();
		vo.setPltSearchRequestYear(vo.getPltEditRequestYear());
		vo.setPltSearchRequestMonth(vo.getPltEditRequestMonth());
		// 検索
		search();
		// 履歴編集対象を取得
		setEditUpdateMode(startDate);
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_DRAFT);
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void appli() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 登録クラス取得
		DifferenceRequestRegistBeanInterface regist = time().differenceRequestRegist();
		Date startDate = DateUtility.getDate(vo.getPltEditRequestYear(), vo.getPltEditRequestMonth(),
				vo.getPltEditRequestDay());
		Date endDate = startDate;
		if (MospConst.CHECKBOX_ON.equals(vo.getCkbEndDate())) {
			// 期間指定の場合
			endDate = DateUtility.getDate(vo.getPltEditEndYear(), vo.getPltEditEndMonth(), vo.getPltEditEndDay());
		}
		// 終了日が開始日より前である場合
		if (endDate.before(startDate)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorInputValueInvalid(mospParams, PfNameUtility.term(mospParams));
			return;
		}
		Date targetDate = startDate;
		while (!targetDate.after(endDate)) {
			// 退職済み確認
			if (reference().retirement().isRetired(vo.getPersonalId(), targetDate)) {
				addEmployeeRetiredMessage();
				return;
			}
			// 休職期間確認
			if (reference().suspension().isSuspended(vo.getPersonalId(), targetDate)) {
				addEmployeeSuspendedMessage();
				return;
			}
			// 利用不可である場合
			if (isAvailable(targetDate, CODE_FUNCTION) == false) {
				// 処理終了
				return;
			}
			// DTOの準備
			DifferenceRequestDtoInterface dto = timeReference().differenceRequest().findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			if (targetDate.compareTo(startDate) != 0) {
				dto = regist.getInitDto();
			}
			// DTOに値を設定
			setDtoFields(dto, targetDate);
			if (targetDate.compareTo(startDate) != 0) {
				dto.setTmdDifferenceRequestId(0);
			}
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 申請日チェック
			regist.checkRequest(dto);
			if (mospParams.hasErrorMessage()) {
				mospParams.getErrorMessageList().clear();
				// 1日加算
				targetDate = DateUtility.addDay(targetDate, 1);
				continue;
			}
			// 申請の相関チェック
			regist.checkAppli(dto);
			// 登録クラスの取得
			WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto == null) {
				workflowDto = workflowRegist.getInitDto();
				workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_DIFFERENCE);
			}
			workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
			// 登録後ワークフローの取得
			workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			if (workflowDto != null) {
				// ワークフロー番号セット
				dto.setWorkflow(workflowDto.getWorkflow());
				// 登録
				regist.regist(dto);
				// 勤怠データ下書
				regist.draftAttendance(dto);
			}
			// 1日加算
			targetDate = DateUtility.addDay(targetDate, 1);
		}
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 申請成功メッセージ設定
		addAppliMessage();
		// 履歴取得
		setEditUpdateMode(startDate);
		// 登録結果確認
		if (!mospParams.hasErrorMessage()) {
			// 登録が成功した場合、初期状態に戻す。
			String searchYear = vo.getPltEditRequestYear();
			String searchMonth = vo.getPltEditRequestMonth();
			insertMode();
			vo.setPltSearchRequestYear(searchYear);
			vo.setPltSearchRequestMonth(searchMonth);
			search();
		}
	}
	
	/**
	 * 勤務時刻再計算処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void workTimeCalc() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		Date editRequestDate = getEditRequestDate();
		DifferenceRequestReferenceBeanInterface differenceRequest = timeReference().differenceRequest(editRequestDate);
		if (TimeConst.CODE_DIFFERENCE_TYPE_A.equals(vo.getPltEditDifferenceType())) {
			// A
			Date startTime = differenceRequest.getDifferenceStartTimeTypeA(editRequestDate);
			Date endTime = differenceRequest.getDifferenceEndTimeTypeA(editRequestDate);
			vo.setPltEditRequestHour(Integer.toString(DateUtility.getHour(startTime)));
			vo.setPltEditRequestMinute(Integer.toString(DateUtility.getMinute(startTime)));
			vo.setLblEndTimeHour(Integer.toString(DateUtility.getHour(endTime, editRequestDate)));
			vo.setLblEndTimeMinute(Integer.toString(DateUtility.getMinute(endTime)));
			vo.setJsEditDifferenceTypeMode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			return;
		} else if (TimeConst.CODE_DIFFERENCE_TYPE_B.equals(vo.getPltEditDifferenceType())) {
			// B
			Date startTime = differenceRequest.getDifferenceStartTimeTypeB(editRequestDate);
			Date endTime = differenceRequest.getDifferenceEndTimeTypeB(editRequestDate);
			vo.setPltEditRequestHour(Integer.toString(DateUtility.getHour(startTime)));
			vo.setPltEditRequestMinute(Integer.toString(DateUtility.getMinute(startTime)));
			vo.setLblEndTimeHour(Integer.toString(DateUtility.getHour(endTime, editRequestDate)));
			vo.setLblEndTimeMinute(Integer.toString(DateUtility.getMinute(endTime)));
			vo.setJsEditDifferenceTypeMode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			return;
		} else if (TimeConst.CODE_DIFFERENCE_TYPE_C.equals(vo.getPltEditDifferenceType())) {
			// C
			Date startTime = differenceRequest.getDifferenceStartTimeTypeC(editRequestDate);
			Date endTime = differenceRequest.getDifferenceEndTimeTypeC(editRequestDate);
			vo.setPltEditRequestHour(Integer.toString(DateUtility.getHour(startTime)));
			vo.setPltEditRequestMinute(Integer.toString(DateUtility.getMinute(startTime)));
			vo.setLblEndTimeHour(Integer.toString(DateUtility.getHour(endTime, editRequestDate)));
			vo.setLblEndTimeMinute(Integer.toString(DateUtility.getMinute(endTime)));
			vo.setJsEditDifferenceTypeMode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			return;
		} else if (TimeConst.CODE_DIFFERENCE_TYPE_D.equals(vo.getPltEditDifferenceType())) {
			// D
			Date startTime = differenceRequest.getDifferenceStartTimeTypeD(editRequestDate);
			Date endTime = differenceRequest.getDifferenceEndTimeTypeD(editRequestDate);
			vo.setPltEditRequestHour(Integer.toString(DateUtility.getHour(startTime)));
			vo.setPltEditRequestMinute(Integer.toString(DateUtility.getMinute(startTime)));
			vo.setLblEndTimeHour(Integer.toString(DateUtility.getHour(endTime, editRequestDate)));
			vo.setLblEndTimeMinute(Integer.toString(DateUtility.getMinute(endTime)));
			vo.setJsEditDifferenceTypeMode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			return;
		} else if (TimeConst.CODE_DIFFERENCE_TYPE_S.equals(vo.getPltEditDifferenceType())) {
			// S
			Date endTime = null;
			if (vo.getJsEditDifferenceTypeMode().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
				// 始業時刻
				WorkTypeItemDtoInterface workStartDto = timeReference().workTypeItem()
					.getWorkTypeItemInfo(vo.getLblWorkType(), getEditRequestDate(), TimeConst.CODE_WORKSTART);
				if (workStartDto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.workType(mospParams));
					// 有効日モード設定
					vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
					return;
				}
				endTime = differenceRequest.getDifferenceEndTimeTypeS(workStartDto.getWorkTypeItemValue());
				vo.setPltEditRequestHour(String.valueOf(DateUtility.getHour(workStartDto.getWorkTypeItemValue())));
				vo.setPltEditRequestMinute(String.valueOf(DateUtility.getMinute(workStartDto.getWorkTypeItemValue())));
			} else {
				Date startTime = DateUtility.getTime(Integer.parseInt(vo.getPltEditRequestHour()),
						Integer.parseInt(vo.getPltEditRequestMinute()));
				endTime = differenceRequest.getDifferenceEndTimeTypeS(startTime);
			}
			vo.setLblEndTimeHour(Integer.toString(DateUtility.getHour(endTime, DateUtility.getDefaultTime())));
			vo.setLblEndTimeMinute(Integer.toString(DateUtility.getMinute(endTime)));
			vo.setJsEditDifferenceTypeMode(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		}
	}
	
	/**
	 * 取下処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void withdrawn() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// beanの準備
		DifferenceRequestRegistBeanInterface regist = time().differenceRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		WorkflowCommentRegistBeanInterface workflowCommentRegist = platform().workflowCommentRegist();
		// DTOの準備
		DifferenceRequestDtoInterface dto = timeReference().differenceRequest().findForKey(vo.getRecordId());
		// 存在確認
		checkSelectedDataExist(dto);
		// 取下の相関チェック
		regist.checkWithdrawn(dto);
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		// 存在確認
		checkSelectedDataExist(workflowDto);
		boolean isDraft = PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus());
		if (isDraft) {
			// 下書の場合は削除する
			workflowRegist.delete(workflowDto);
			workflowCommentRegist
				.deleteList(reference().workflowComment().getWorkflowCommentList(workflowDto.getWorkflow()));
			regist.delete(dto);
		} else {
			// 下書でない場合は取下する
			// ワークフロー登録
			workflowDto = workflowRegist.withdrawn(workflowDto);
			if (workflowDto != null) {
				// ワークフローコメント登録
				workflowCommentRegist.addComment(workflowDto, mospParams.getUser().getPersonalId(),
						PfMessageUtility.getWithdrawSucceed(mospParams));
			}
		}
		// 取下結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		if (isDraft) {
			// 削除成功メッセージを設定
			PfMessageUtility.addMessageDeleteSucceed(mospParams);
		} else {
			// 取下成功メッセージ設定
			addTakeDownMessage();
		}
		String searchYear = vo.getPltEditRequestYear();
		String searchMonth = vo.getPltEditRequestMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		vo.setPltSearchRequestYear(searchYear);
		vo.setPltSearchRequestMonth(searchMonth);
		// 検索
		search();
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
	 * @throws MospException 例外処理発生時
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 一括取下処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void batchWithdrawn() throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		time().differenceRequestRegist().withdrawn(getIdArray(vo.getCkbSelect()));
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 一括更新失敗メッセージを設定
			PfMessageUtility.addMessageBatchUpdatetFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 取下成功メッセージ設定
		addTakeDownMessage();
		String searchYear = vo.getPltSearchRequestYear();
		String searchMonth = vo.getPltSearchRequestMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 表示期間設定
		setSearchRequestDate(searchYear, searchMonth);
		// 検索
		search();
	}
	
	/**
	 * @throws MospException 例外処理発生時
	 */
	protected void setActivationDate() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			
			// 退職済確認
			if (reference().retirement().isRetired(vo.getPersonalId(), getEditRequestDate())) {
				addEmployeeRetiredMessage();
				return;
			}
			// 休職期間確認
			if (reference().suspension().isSuspended(vo.getPersonalId(), getEditRequestDate())) {
				addEmployeeSuspendedMessage();
				return;
			}
			// 利用不可である場合
			if (isAvailable(getEditRequestDate(), CODE_FUNCTION) == false) {
				// 処理終了
				return;
			}
			if (setApproverPullDown(vo.getPersonalId(), getEditRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME) == false) {
				return;
			}
			DifferenceRequestRegistBeanInterface regist = time().differenceRequestRegist();
			DifferenceRequestDtoInterface dto = timeReference().differenceRequest().findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			dto.setPersonalId(vo.getPersonalId());
			dto.setRequestDate(getEditRequestDate());
			regist.checkTemporaryClosingFinal(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			regist.checkRequest(dto);
			regist.checkDifferenceOverlap(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			vo.setJsEditDifferenceTypeMode(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			// 期間終了日設定
			setEditEndDate();
		} else {
			String[] aryPltLblApproverSetting = new String[0];
			vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
			// 時差出勤区分の初期化
			vo.setPltEditDifferenceType(TimeConst.CODE_DIFFERENCE_TYPE_A);
			vo.setPltEditRequestHour("8");
			vo.setPltEditRequestMinute("0");
			vo.setLblEndTimeHour("16");
			vo.setLblEndTimeMinute("00");
			// 期間指定チェックボックスを初期化
			vo.setCkbEndDate(MospConst.CHECKBOX_OFF);
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			vo.setJsEditDifferenceTypeMode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 出勤日決定
		getEditItems();
		// プルダウン設定
		setDifferenceTypePulldown();
	}
	
	/**
	 * @throws MospException 例外処理発生時
	 */
	protected void getEditItems() throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			Date date = getEditRequestDate();
			// 振替休日データを取得
			SubstituteDtoInterface substituteDto = timeReference().substitute().getSubstituteDto(vo.getPersonalId(),
					getEditRequestDate());
			if (substituteDto != null) {
				// ワークフロー情報
				WorkflowDtoInterface workflowDto = reference().workflow()
					.getLatestWorkflowInfo(substituteDto.getWorkflow());
				// 承認済の場合
				if (WorkflowUtility.isCompleted(workflowDto)) {
					date = substituteDto.getSubstituteDate();
				}
			}
			// カレンダに登録されている勤務形態コードを取得
			String workTypeCode = timeReference().scheduleUtil().getScheduledWorkTypeCode(vo.getPersonalId(), date);
			// カレンダに登録されている勤務形態コードを取得できなかった場合
			if (mospParams.hasErrorMessage()) {
				// 有効日モード設定
				vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				// 処理終了
				return;
			}
			WorkTypeDtoInterface workTypeDto = timeReference().workType().getWorkTypeInfo(workTypeCode, date);
			if (workTypeDto == null) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.workType(mospParams));
				// 有効日モード設定
				vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				return;
			}
			// 勤怠形態の取得
			vo.setLblWorkType(workTypeDto.getWorkTypeCode());
			vo.setLblWorkTypeName(
					timeReference().workType().getWorkTypeAbbrAndTime(workTypeDto.getWorkTypeCode(), date));
			// 開始/終了時刻の設定
			workTimeCalc();
			return;
		}
		// 勤怠形態の取得
		vo.setLblWorkType("");
		vo.setLblWorkTypeName("");
		// 開始時刻の取得
		vo.setPltEditRequestHour("");
		vo.setPltEditRequestMinute("");
		// 終了時刻の取得
		vo.setLblEndTimeHour("");
		vo.setLblEndTimeMinute("");
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		Date targetDate = date;
		if (getTargetDate() != null) {
			targetDate = getTargetDate();
		}
		String targetYear = String.valueOf(DateUtility.getYear(targetDate));
		String targetMonth = String.valueOf(DateUtility.getMonth(targetDate));
		String targetDay = String.valueOf(DateUtility.getDay(targetDate));
		// 検索項目設定
		vo.setRecordId(0);
		vo.setPltEditRequestYear(targetYear);
		vo.setPltEditRequestMonth(targetMonth);
		vo.setPltEditRequestDay(targetDay);
		vo.setPltEditEndYear(targetYear);
		vo.setPltEditEndMonth(targetMonth);
		vo.setPltEditEndDay(targetDay);
		vo.setPltEditRequestType("");
		vo.setPltEditDifferenceType(TimeConst.CODE_DIFFERENCE_TYPE_A);
		vo.setPltEditRequestHour("0");
		vo.setPltEditRequestMinute("0");
		vo.setLblEndTimeHour("");
		vo.setLblEndTimeMinute("");
		vo.setTxtEditRequestReason("");
		vo.setLblWorkType("");
		vo.setLblWorkTypeName("");
		vo.setLblEndTimeHour("");
		vo.setLblEndTimeMinute("");
		vo.setPltSearchState("");
		vo.setPltSearchRequestHour("");
		vo.setPltSearchRequestMinute("");
		vo.setPltSearchRequestYear(String.valueOf(DateUtility.getYear(date)));
		vo.setPltSearchRequestMonth(String.valueOf(DateUtility.getMonth(date)));
		vo.setCkbEndDate("");
		// 承認者欄の初期化
		String[] aryPltLblApproverSetting = new String[0];
		vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
		vo.setPltApproverSetting1("");
		vo.setPltApproverSetting2("");
		vo.setPltApproverSetting3("");
		vo.setPltApproverSetting4("");
		vo.setPltApproverSetting5("");
		vo.setPltApproverSetting6("");
		vo.setPltApproverSetting7("");
		vo.setPltApproverSetting8("");
		vo.setPltApproverSetting9("");
		vo.setPltApproverSetting10("");
		vo.setJsEditDifferenceTypeMode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
	}
	
	/**
	 * 表示期間を設定する。<br>
	 * @param year 年
	 * @param month 月
	 */
	protected void setSearchRequestDate(String year, String month) {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		vo.setPltSearchRequestYear(year);
		vo.setPltSearchRequestMonth(month);
	}
	
	/**
	 * プルダウン設定
	 */
	private void setPulldown() {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		vo.setAryPltEditRequestYear(getYearArray(DateUtility.getYear(date)));
		vo.setAryPltEditRequestMonth(getMonthArray());
		// 31日まで取得
		vo.setAryPltEditRequestDay(getDayArray());
		vo.setAryPltEditRequestHour(getHourArray());
		// 分は15分単位
		vo.setAryPltEditRequestMinute(getMinuteArray(15));
		vo.setAryPltEditEndYear(getYearArray(DateUtility.getYear(date)));
		vo.setAryPltEditEndMonth(getMonthArray());
		vo.setAryPltEditEndDay(getDayArray());
		vo.setAryPltSearchState(mospParams.getProperties().getCodeArray(TimeConst.CODE_APPROVAL_STATE, true));
		vo.setAryPltSearchRequestYear(getYearArray(DateUtility.getYear(date)));
		vo.setAryPltSearchRequestMonth(getMonthArray(true));
	}
	
	/**
	 * 時差出勤区分プルダウン設定
	 * @throws MospException 例外処理発生時
	 */
	protected void setDifferenceTypePulldown() throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		vo.setAryPltEditDifferenceType(timeReference().differenceRequest(getEditRequestDate()).getSelectArray());
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void insertMode() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// プルダウン設定
		setPulldown();
		setDifferenceTypePulldown();
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 有効日(検索)モード設定
		vo.setJsSearchActivateDateMode(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 表示期間決定
		setSearchPulldown();
		// 基本情報チェック
		timeReference().differenceRequest().chkBasicInfo(vo.getPersonalId(), getEditRequestDate());
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 検索クラス取得
		DifferenceRequestSearchBeanInterface search = timeReference().differenceRequestSearch();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// VOの値を検索クラスへ設定
		search.setPersonalId(personalId);
		search.setWorkflowStatus(vo.getPltSearchState());
		search.setWorkTypeCode(vo.getPltSearchWorkType());
		if (vo.getPltSearchWorkType() == null) {
			search.setWorkTypeCode(vo.getAryPltSearchWorkType()[0][0]);
		}
		int year = Integer.parseInt(vo.getPltSearchRequestYear());
		int startMonth = getInt(TimeConst.CODE_DISPLAY_JANUARY);
		int endMonth = getInt(TimeConst.CODE_DISPLAY_DECEMBER);
		// 月検索(検索月が指定されている)ならば
		if (MospUtility.isEmpty(vo.getPltSearchRequestMonth()) == false) {
			// 月を設定
			startMonth = getInt(vo.getPltSearchRequestMonth());
			endMonth = startMonth;
		}
		// 勤怠関連マスタ参照処理を取得
		TimeMasterBeanInterface timeMaster = timeReference().master();
		// 年月(FROM及びTO)で設定適用エンティティを取得
		ApplicationEntity applicationFrom = timeMaster.getApplicationEntity(personalId, year, startMonth);
		ApplicationEntity applicationTo = timeMaster.getApplicationEntity(personalId, year, endMonth);
		// 締期間の開始及び最終日を取得
		Date firstDate = applicationFrom.getCutoffEntity().getCutoffFirstDate(year, startMonth, mospParams);
		Date lastDate = applicationTo.getCutoffEntity().getCutoffLastDate(year, endMonth, mospParams);
		// 締期間を検索範囲に設定
		search.setRequestStartDate(firstDate);
		search.setRequestEndDate(lastDate);
		// 検索条件をもとに検索クラスからリストを取得
		List<DifferenceRequestListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(DifferenceRequestRequestDateComparator.class.getName());
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
	 * @return 有効日
	 */
	protected Date getEditRequestDate() {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getPltEditRequestYear(), vo.getPltEditRequestMonth(), vo.getPltEditRequestDay());
	}
	
	/**
	 * @return 有効日
	 */
	protected Date getSearchRequestDate() {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		Date date;
		// 有効日取得
		if (vo.getPltSearchRequestMonth().isEmpty()) {
			date = getDate(vo.getPltSearchRequestYear(), "1", "1");
		} else {
			date = getDate(vo.getPltSearchRequestYear(), vo.getPltSearchRequestMonth(), "1");
		}
		return date;
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblDate = new String[list.size()];
		String[] aryLblRequestType = new String[list.size()];
		String[] aryLblWorkTime = new String[list.size()];
		String[] aryLblRequestReason = new String[list.size()];
		String[] aryLblState = new String[list.size()];
		String[] aryStateStyle = new String[list.size()];
		String[] aryLblApprover = new String[list.size()];
		String[] aryLblOnOff = new String[list.size()];
		String[] aryWorkflowStatus = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			DifferenceRequestListDtoInterface dto = (DifferenceRequestListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getTmdDifferenceRequestId());
			aryLblDate[i] = DateUtility.getStringDateAndDay(dto.getRequestDate());
			aryLblRequestType[i] = TimeRequestUtility.getDifferenceTypeAbbr(dto.getAroundType(), mospParams);
			aryLblWorkTime[i] = TransStringUtility.getHourColonMinuteTerm(mospParams, dto.getStartTime(),
					dto.getEndTime(), dto.getRequestDate());
			aryLblRequestReason[i] = dto.getRequestReason();
			aryLblState[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryStateStyle[i] = getStatusColor(dto.getState());
			aryLblOnOff[i] = getButtonOnOff(dto.getState(), dto.getStage());
			// 承認者に関してはdtoから直接取得する（他の申請画面も同様）
			aryLblApprover[i] = dto.getApproverName();
			aryWorkflowStatus[i] = dto.getState();
			aryWorkflow[i] = dto.getWorkflow();
		}
		// データをVOに設定
		vo.setAryCkbDifferenceRequestListId(aryCkbRecordId);
		vo.setAryLblDate(aryLblDate);
		vo.setAryLblRequestType(aryLblRequestType);
		vo.setAryLblWorkTime(aryLblWorkTime);
		vo.setAryLblRequestReason(aryLblRequestReason);
		vo.setAryLblState(aryLblState);
		vo.setAryStateStyle(aryStateStyle);
		vo.setAryLblApprover(aryLblApprover);
		vo.setAryLblOnOff(aryLblOnOff);
		vo.setAryWorkflowStatus(aryWorkflowStatus);
		vo.setAryWorkflow(aryWorkflow);
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		time().differenceRequestRegist().update(getIdArray(vo.getCkbSelect()));
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 一括更新失敗メッセージを設定
			PfMessageUtility.addMessageBatchUpdatetFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 検索
		search();
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void transfer() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
		// 承認履歴画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(ApprovalHistoryAction.CMD_DIFFERENCE_WORK_APPROVAL_HISTORY_SELECT_SHOW);
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setViewPeriod() throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getJsSearchActivateDateMode().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setJsSearchActivateDateMode(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setJsSearchActivateDateMode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		setSearchPulldown();
	}
	
	/**
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void selectActivationDate() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		select();
		setActivationDate();
		String transferredType = getTransferredType();
		if (transferredType != null) {
			vo.setPltEditDifferenceType(transferredType);
		}
		String transferredStartHour = getTransferredStartHour();
		if (transferredStartHour != null) {
			vo.setPltEditRequestHour(transferredStartHour);
		}
		String transferredStartMinute = getTransferredStartMinute();
		if (transferredStartMinute != null) {
			vo.setPltEditRequestMinute(transferredStartMinute);
		}
		String transferredEndHour = getTransferredEndHour();
		if (transferredEndHour != null) {
			vo.setLblEndTimeHour(transferredEndHour);
		}
		String transferredEndMinute = getTransferredEndMinute();
		if (transferredEndMinute != null) {
			vo.setLblEndTimeMinute(transferredEndMinute);
		}
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外処理発生時
	 */
	private void setSearchPulldown() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getJsSearchActivateDateMode().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			vo.setAryPltSearchWorkType(timeReference().workType().getTimeSelectArray(getSearchRequestDate()));
			// 追加業務ロジック処理がある場合
			doAdditionalLogic(TimeConst.CODE_KEY_CHANGE_WORK_TYPE_PULLDOWN);
			return;
		}
		vo.setAryPltSearchWorkType(new String[0][0]);
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO準備
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		if (personalId != null) {
			// 人事情報をVOに設定
			setEmployeeInfo(personalId, targetDate);
			// ページ繰り設定
			setPageInfo(CMD_PAGE, getListLength());
			// 新規登録モード設定
			insertMode();
			// デフォルトソートキー及びソート順設定
			vo.setComparatorName(DifferenceRequestRequestDateComparator.class.getName());
			// 検索
			search();
		}
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getDate(getTransferredActivateDate()));
		// 有効日モード設定(下のメソッドで決定状態にするために変更状態に設定)
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 有効日を設定
		setActivationDate();
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 申請日で編集対象情報を取得する。<br>
	 * @param requestDate 申請日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(Date requestDate) throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// 履歴編集対象取得
		DifferenceRequestDtoInterface dto = timeReference().differenceRequest().findForKeyOnWorkflow(vo.getPersonalId(),
				requestDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
	}
	
	/**
	 * 期間終了日を設定する。<br>
	 */
	protected void setEditEndDate() {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		vo.setPltEditEndYear(vo.getPltEditRequestYear());
		vo.setPltEditEndMonth(vo.getPltEditRequestMonth());
		vo.setPltEditEndDay(vo.getPltEditRequestDay());
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外処理発生時
	 */
	protected void setVoFields(DifferenceRequestDtoInterface dto) throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		int year = DateUtility.getYear(dto.getRequestDate());
		int month = DateUtility.getMonth(dto.getRequestDate());
		int day = DateUtility.getDay(dto.getRequestDate());
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmdDifferenceRequestId());
		vo.setPltEditRequestYear(String.valueOf(year));
		vo.setPltEditRequestMonth(String.valueOf(month));
		vo.setPltEditRequestDay(String.valueOf(day));
		vo.setPltEditEndYear(String.valueOf(year));
		vo.setPltEditEndMonth(String.valueOf(month));
		vo.setPltEditEndDay(String.valueOf(day));
		vo.setPltEditRequestHour(String.valueOf(DateUtility.getHour(dto.getRequestStart())));
		vo.setPltEditRequestMinute(String.valueOf(DateUtility.getMinute(dto.getRequestStart())));
		vo.setPltEditDifferenceType(dto.getDifferenceType());
		vo.setLblWorkType(dto.getWorkTypeCode());
		vo.setLblEndTimeHour(DateUtility.getStringHour(dto.getRequestEnd(), dto.getRequestDate()));
		vo.setLblEndTimeMinute(DateUtility.getStringMinute(dto.getRequestEnd()));
		vo.setTxtEditRequestReason(dto.getRequestReason());
		vo.setModeCardEdit(getApplicationMode(dto.getWorkflow()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param date 時差出勤日
	 * @throws MospException 例外処理発生時
	 */
	protected void setDtoFields(DifferenceRequestDtoInterface dto, Date date) throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmdDifferenceRequestId(vo.getRecordId());
		dto.setPersonalId(vo.getPersonalId());
		dto.setRequestDate(date);
		dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
		dto.setDifferenceType(vo.getPltEditDifferenceType());
		dto.setWorkTypeCode(getWorkTypeCode(date));
		Date startTime = getStartTime(date);
		dto.setRequestStart(startTime);
		dto.setRequestEnd(getEndTime(date, startTime));
		dto.setRequestReason(vo.getTxtEditRequestReason());
	}
	
	/**
	 * 勤務形態コードを取得する。<br>
	 * @param date 対象日
	 * @return 勤務形態コード
	 * @throws MospException 例外処理発生時
	 */
	protected String getWorkTypeCode(Date date) throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		ScheduleUtilBeanInterface scheduleUtil = timeReference().scheduleUtil();
		RequestUtilBeanInterface requestUtil = timeReference().requestUtil();
		requestUtil.setRequests(vo.getPersonalId(), date);
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto != null
				&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			// 振替の場合
			List<SubstituteDtoInterface> list = timeReference().substitute()
				.getSubstituteList(workOnHolidayRequestDto.getWorkflow());
			if (list.isEmpty()) {
				return "";
			}
			for (SubstituteDtoInterface dto : list) {
				return scheduleUtil.getScheduledWorkTypeCode(vo.getPersonalId(), dto.getSubstituteDate());
			}
		}
		return scheduleUtil.getScheduledWorkTypeCode(vo.getPersonalId(), date);
	}
	
	/**
	 * 時差出勤始業時刻を取得する。<br>
	 * @param date 対象日
	 * @return 時差出勤始業時刻
	 * @throws MospException 例外処理発生時
	 */
	protected Date getStartTime(Date date) throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		DifferenceRequestReferenceBeanInterface differenceRequest = timeReference().differenceRequest(date);
		if (differenceRequest.isDifferenceTypeA(vo.getPltEditDifferenceType())) {
			// A
			return differenceRequest.getDifferenceStartTimeTypeA(date);
		} else if (differenceRequest.isDifferenceTypeB(vo.getPltEditDifferenceType())) {
			// B
			return differenceRequest.getDifferenceStartTimeTypeB(date);
		} else if (differenceRequest.isDifferenceTypeC(vo.getPltEditDifferenceType())) {
			// C
			return differenceRequest.getDifferenceStartTimeTypeC(date);
		} else if (differenceRequest.isDifferenceTypeD(vo.getPltEditDifferenceType())) {
			// D
			return differenceRequest.getDifferenceStartTimeTypeD(date);
		} else if (differenceRequest.isDifferenceTypeS(vo.getPltEditDifferenceType())) {
			// S
			return DateUtility.getDateTime(DateUtility.getYear(date), DateUtility.getMonth(date),
					DateUtility.getDay(date), Integer.parseInt(vo.getPltEditRequestHour()),
					Integer.parseInt(vo.getPltEditRequestMinute()));
		}
		return null;
	}
	
	/**
	 * 時差出勤終業時刻を取得する。<br>
	 * @param date 対象日
	 * @param startTime 始業時刻
	 * @return 時差出勤終業時刻
	 * @throws MospException 例外処理発生時
	 */
	protected Date getEndTime(Date date, Date startTime) throws MospException {
		// VO取得
		DifferenceRequestVo vo = (DifferenceRequestVo)mospParams.getVo();
		DifferenceRequestReferenceBeanInterface differenceRequest = timeReference().differenceRequest(date);
		if (differenceRequest.isDifferenceTypeA(vo.getPltEditDifferenceType())) {
			// A
			return differenceRequest.getDifferenceEndTimeTypeA(date);
		} else if (differenceRequest.isDifferenceTypeB(vo.getPltEditDifferenceType())) {
			// B
			return differenceRequest.getDifferenceEndTimeTypeB(date);
		} else if (differenceRequest.isDifferenceTypeC(vo.getPltEditDifferenceType())) {
			// C
			return differenceRequest.getDifferenceEndTimeTypeC(date);
		} else if (differenceRequest.isDifferenceTypeD(vo.getPltEditDifferenceType())) {
			// D
			return differenceRequest.getDifferenceEndTimeTypeD(date);
		} else if (differenceRequest.isDifferenceTypeS(vo.getPltEditDifferenceType())) {
			// S
			return differenceRequest.getDifferenceEndTimeTypeS(startTime);
		}
		return null;
	}
	
}
