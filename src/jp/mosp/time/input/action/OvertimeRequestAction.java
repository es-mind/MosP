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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.OvertimeInfoReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistBeanInterface;
import jp.mosp.time.bean.OvertimeRequestSearchBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.comparator.settings.OvertimeRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestListDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.input.vo.OvertimeRequestVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * 残業申請情報の確認と編集を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_DRAFT}
 * </li><li>
 * {@link #CMD_APPLI}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_WITHDRAWN}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_BATCH_WITHDRAWN}
 * </li><li>
 * {@link #CMD_SET_OVERTIME_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li><li>
 * {@link #CMD_SELECT_OVERTIME_DATE}
 * </li></ul>
 */
public class OvertimeRequestAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの残業申請画面を表示する。<br>
	 */
	public static final String		CMD_SHOW					= "TM1400";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 残業申請画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW				= "TM1401";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に残業申請情報の検索を行う。<br>
	 */
	public static final String		CMD_SEARCH					= "TM1402";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 一度この画面を表示してからパンくずリスト等で再度遷移する場合は保持していた検索条件で検索を行って再表示する。<br>
	 */
	public static final String		CMD_RE_SHOW					= "TM1403";
	
	/**
	 * 下書コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を残業情報テーブルに登録し、下書情報として保存する。<br>
	 */
	public static final String		CMD_DRAFT					= "TM1404";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を残業情報テーブルに登録し、残業申請を行う。以降、このレコードは上長が差戻をしない限り編集不可となる。<br>
	 * 残業の申請可能時間を超過している、申請年月日で申請不可な日付が選択されている、<br>
	 * 申請時間が0時間0分のまま、残業理由の項目が入力されていない、<br>
	 * といった状態で申請を行おうとした場合はエラーメッセージにて通知し、申請は実行されない。<br>
	 */
	public static final String		CMD_APPLI					= "TM1405";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String		CMD_TRANSFER				= "TM1406";
	
	/**
	 * 取下コマンド。<br>
	 * <br>
	 * 下書状態または差戻状態で登録されていたレコードの取下を行う。<br>
	 * 取下後、対象の残業申請情報は未申請状態へ戻る。<br>
	 */
	public static final String		CMD_WITHDRAWN				= "TM1407";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String		CMD_SORT					= "TM1408";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String		CMD_PAGE					= "TM1409";
	
	/**
	 * 一括取下コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている未承認状態のレコードの取下処理を繰り返し行う。
	 * ひとつもチェックが入っていない状態で一括取下ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。
	 */
	public static final String		CMD_BATCH_WITHDRAWN			= "TM1436";
	
	/**
	 * 残業年月日決定コマンド。<br>
	 * <br>
	 * 残業年月日欄に入力されている日付を基にその日時点で申請者に紐付けられている承認者情報を取得し、
	 * それぞれの承認段階のプルダウンで選択可能な状態とする。<br>
	 * 決定した日付時点で申請者がどの承認者にも紐付けられていない場合はエラーメッセージを表示し、
	 * 処理を中断する。<br>
	 */
	public static final String		CMD_SET_OVERTIME_DATE		= "TM1490";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 申請テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 申請テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE				= "TM1491";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を申請テーブルの各入力欄にそれぞれ表示させる。<br>
	 * 申請テーブルヘッダに新規登録モード切替リンクを表示させる。<br>
	 */
	public static final String		CMD_EDIT_MODE				= "TM1492";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている下書状態のレコードの申請処理を繰り返し行う。<br>
	 * ひとつもチェックが入っていない状態で一括更新ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。<br>
	 */
	public static final String		CMD_BATCH_UPDATE			= "TM1495";
	
	/**
	 * 残業年月日決定コマンド。<br>
	 * <br>
	 * 残業年月日欄に入力されている日付を基にその日時点で申請者に紐付けられている承認者情報を取得し、
	 * それぞれの承認段階のプルダウンで選択可能な状態とする。<br>
	 * 決定した日付時点で申請者がどの承認者にも紐付けられていない場合はエラーメッセージを表示し、
	 * 処理を中断する。<br>
	 */
	public static final String		CMD_SELECT_OVERTIME_DATE	= "TM1498";
	
	/**
	 * 機能コード。<br>
	 */
	protected static final String	CODE_FUNCTION				= TimeConst.CODE_FUNCTION_OVER_WORK;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public OvertimeRequestAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new OvertimeRequestVo();
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
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_DRAFT)) {
			// 下書
			prepareVo();
			draft();
		} else if (mospParams.getCommand().equals(CMD_APPLI)) {
			// 申請
			prepareVo();
			appli();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
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
		} else if (mospParams.getCommand().equals(CMD_SET_OVERTIME_DATE)) {
			// 残業年月日決定
			prepareVo();
			setOvertimeDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規モード切替
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
		} else if (mospParams.getCommand().equals(CMD_SELECT_OVERTIME_DATE)) {
			// 選択残業年月日決定
			prepareVo(false, false);
			selectOvertimeDate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
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
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
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
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
	}
	
	/**
	 * 検索する。
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// 検索クラス取得
		OvertimeRequestSearchBeanInterface search = timeReference().overtimeRequestSearch();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// VOの値を検索クラスへ設定
		search.setPersonalId(personalId);
		search.setWorkflowStatus(vo.getPltSearchState());
		search.setScheduleOver(vo.getPltSearchScheduleOver());
		search.setOvertimeType(vo.getPltSearchOverTimeType());
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
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<OvertimeRequestListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(OvertimeRequestRequestDateComparator.class.getName());
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
	* 下書処理を行う。<br>
	* @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	*/
	protected void draft() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// DTOの準備
		OvertimeRequestDtoInterface dto = timeReference().overtimeRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = time().overtimeRequestRegist().getInitDto();
		}
		setOvertimeDtoFields(dto);
		// 妥当性チェック
		time().overtimeRequestRegist().validate(dto);
		// 申請の相関チェック
		time().overtimeRequestRegist().checkDraft(dto);
		// 登録クラスの取得
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_OVER_WORK);
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
			time().overtimeRequestRegist().regist(dto);
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
		// 申請残業年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getRequestDate());
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
		// 検索
		search();
		// 履歴編集対象を取得
		setEditUpdateMode(dto.getRequestDate(), dto.getOvertimeType());
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_DRAFT);
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// DTOの準備
		OvertimeRequestDtoInterface dto = timeReference().overtimeRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = time().overtimeRequestRegist().getInitDto();
		}
		// VO(編集項目)の値をDTOに設定
		setOvertimeDtoFields(dto);
		// 申請の相関チェック
		time().overtimeRequestRegist().checkAppli(dto);
		// 登録クラスの取得
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_OVER_WORK);
		}
		workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
				PlatformConst.WORKFLOW_TYPE_TIME, null);
		if (workflowDto != null) {
			// ワークフロー番号セット
			dto.setWorkflow(workflowDto.getWorkflow());
			// 登録
			time().overtimeRequestRegist().regist(dto);
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
		// 登録結果確認
		if (!mospParams.hasErrorMessage()) {
			// 登録が成功した場合、初期状態に戻す。
			insertMode();
			// 申請残業年月日が含まれる締月を取得し検索条件に設定
			Date searchDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getRequestDate());
			vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
			vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
			search();
		}
	}
	
	/**
	* 取下処理を行う。<br>
	 * @throws MospException 例外発生時
	*/
	protected void withdrawn() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// beanの準備
		OvertimeRequestRegistBeanInterface regist = time().overtimeRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		WorkflowCommentRegistBeanInterface workflowCommentRegist = platform().workflowCommentRegist();
		// DTOの準備
		OvertimeRequestDtoInterface dto = timeReference().overtimeRequest().findForKey(vo.getRecordId());
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
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 申請残業年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getRequestDate());
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
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
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		time().overtimeRequestRegist().withdrawn(getIdArray(vo.getCkbSelect()));
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
		// 表示期間を保持
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
	 * 有効日決定処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setOvertimeDate() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// beanの準備
		OvertimeRequestRegistBeanInterface regist = time().overtimeRequestRegist();
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// プルダウン初期化
			vo.setAryPltLblApproverSetting(new String[0]);
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			return;
		}
		// 利用可否チェック
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		OvertimeRequestDtoInterface dto = timeReference().overtimeRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = regist.getInitDto();
		}
		dto.setPersonalId(vo.getPersonalId());
		dto.setRequestDate(getEditRequestDate());
		dto.setOvertimeType(getInt(vo.getPltEditOverTimeType()));
		regist.checkSetRequestDate(dto);
		if (mospParams.hasErrorMessage()) {
			// 決定失敗メッセージを設定
			PfMessageUtility.addMessageDecisiontFailed(mospParams);
			return;
		}
		// プルダウン作成
		if (setApproverPullDown(vo.getPersonalId(), getEditRequestDate(), PlatformConst.WORKFLOW_TYPE_TIME) == false) {
			// 決定失敗メッセージを設定
			PfMessageUtility.addMessageDecisiontFailed(mospParams);
			return;
		}
		// 有効日のモード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// プルダウン設定
		setPulldown();
		// 状態
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(OvertimeRequestRequestDateComparator.class.getName());
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
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
			// 検索
			search();
		}
		// 利用可否チェック
		isAvailable(getDate(getTransferredActivateDate()), CODE_FUNCTION);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getDate(getTransferredActivateDate()), Integer.parseInt(getTransferredType()));
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		setOvertimeDate();
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		time().overtimeRequestRegist().update(getIdArray(vo.getCkbSelect()));
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
		// 表示期間を保持
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
	 * 有効日決定処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void selectOvertimeDate() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		select();
		String transferredHour = getTransferredHour();
		if (transferredHour != null) {
			vo.setPltEditRequestHour(transferredHour);
		}
		String transferredMinute = getTransferredMinute();
		if (transferredMinute != null) {
			vo.setPltEditRequestMinute(transferredMinute);
		}
		setOvertimeDate();
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void transfer() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
		// 承認履歴画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(ApprovalHistoryAction.CMD_OVERTIME_APPROVAL_HISTORY_SELECT_SHOW);
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// VOから個人IDを取得
		String personalId = vo.getPersonalId();
		OvertimeInfoReferenceBeanInterface overtimeInfo = timeReference().overtimeInfo();
		// システム日付取得
		Date date = getSystemDate();
		Date targetDate = date;
		if (getTargetDate() != null) {
			targetDate = getTargetDate();
		}
		// 編集項目
		vo.setRecordId(0);
		vo.setPltEditRequestYear(String.valueOf(DateUtility.getYear(targetDate)));
		vo.setPltEditRequestMonth(String.valueOf(DateUtility.getMonth(targetDate)));
		vo.setPltEditRequestDay(String.valueOf(DateUtility.getDay(targetDate)));
		vo.setPltEditRequestHour("0");
		vo.setPltEditRequestMinute("0");
		if (getTransferredHour() != null) {
			vo.setPltEditRequestHour(getTransferredHour());
			vo.setPltEditRequestMinute(getTransferredMinute());
		}
		// 勤務後残業をセット
		vo.setPltEditOverTimeType(Integer.toString(2));
		vo.setTxtEditRequestReason("");
		// 検索項目
		vo.setPltSearchState("");
		vo.setPltSearchScheduleOver("");
		vo.setPltSearchOverTimeType("");
		// 申請残業年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(personalId, date);
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
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
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		
		vo.setJsBeforeOvertimeFlag("0");
		
		// 申請可能時間表示
		vo.setLblRemainderWeek(overtimeInfo.getStringPossibleTime1Week(personalId));
		vo.setLblRemainderMonth(overtimeInfo.getStringPossibleTime1Month(personalId));
		//
		Integer beforeOvertimeFlag = timeReference().timeSetting().getBeforeOvertimeFlag(vo.getPersonalId(),
				targetDate);
		// 勤務前残業のチェック
		if (null != beforeOvertimeFlag && 1 == beforeOvertimeFlag) {
			// 無効に設定
			vo.setJsBeforeOvertimeFlag("1");
		}
	}
	
	/**
	 * 表示期間を設定する。<br>
	 * @param year 年
	 * @param month 月
	 */
	protected void setSearchRequestDate(String year, String month) {
		// VO取得
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		vo.setPltSearchRequestYear(year);
		vo.setPltSearchRequestMonth(month);
	}
	
	/**
	 * プルダウン設定
	 */
	protected void setPulldown() {
		// VO準備
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		int editRequestYear = DateUtility.getYear(getSystemDate());
		// 編集項目設定
		vo.setAryPltEditRequestYear(getYearArray(editRequestYear));
		vo.setAryPltEditRequestMonth(getMonthArray());
		// 31日まで取得
		vo.setAryPltEditRequestDay(getDayArray());
		vo.setAryPltEditRequestHour(getHourArray());
		// 分は15分単位
		vo.setAryPltEditRequestMinute(getMinuteArray(15));
		vo.setAryPltEditOverTimeType(mospParams.getProperties().getCodeArray(TimeConst.CODE_OVER_TIME_TYPE, false));
		// 検索項目設定
		vo.setAryPltSearchState(mospParams.getProperties().getCodeArray(TimeConst.CODE_APPROVAL_STATE, true));
		vo.setAryPltSearchScheduleOver(mospParams.getProperties().getCodeArray(TimeConst.CODE_SCHEDULE_OVER, true));
		vo.setAryPltSearchOverTimeType(mospParams.getProperties().getCodeArray(TimeConst.CODE_OVER_TIME_TYPE, true));
		vo.setAryPltSearchRequestYear(getYearArray(editRequestYear));
		vo.setAryPltSearchRequestMonth(getMonthArray(true));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 申請日と残業区分で編集対象情報を取得する。<br>
	 * @param requestDate 申請日
	 * @param overTimeType 残業区分
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(Date requestDate, int overTimeType) throws MospException {
		// VO取得
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// 履歴編集対象取得
		OvertimeRequestDtoInterface dto = timeReference().overtimeRequest().findForKeyOnWorkflow(vo.getPersonalId(),
				requestDate, overTimeType);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setVoFields(OvertimeRequestDtoInterface dto) throws MospException {
		// VO取得
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmdOvertimeRequestId());
		vo.setTmdOvertimeRequestId(dto.getTmdOvertimeRequestId());
		vo.setPltEditRequestYear(String.valueOf(DateUtility.getYear(dto.getRequestDate())));
		vo.setPltEditRequestMonth(String.valueOf(DateUtility.getMonth(dto.getRequestDate())));
		vo.setPltEditRequestDay(String.valueOf(DateUtility.getDay(dto.getRequestDate())));
		vo.setPltEditRequestHour(convIntegerTimeToStringHour(dto.getRequestTime()));
		vo.setPltEditRequestMinute(convIntegerTimeToStringMinutes(dto.getRequestTime()));
		vo.setPltEditOverTimeType(String.valueOf(dto.getOvertimeType()));
		vo.setTxtEditRequestReason(dto.getRequestReason());
		vo.setModeCardEdit(getApplicationMode(dto.getWorkflow()));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblRequestDate = new String[list.size()];
		String[] aryLblOvertimeTypeName = new String[list.size()];
		String[] aryLblOvertimeTypeCode = new String[list.size()];
		String[] aryLblRequestTime = new String[list.size()];
		String[] aryLblResultTime = new String[list.size()];
		String[] aryLblRequestReason = new String[list.size()];
		String[] aryLblWorkflowStatus = new String[list.size()];
		String[] aryStatusStyle = new String[list.size()];
		String[] aryLblApproverName = new String[list.size()];
		String[] aryLblOnOff = new String[list.size()];
		String[] aryWorkflowStatus = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		// データ作成
		int i = 0;
		for (BaseDtoInterface baseDto : list) {
			// リストから情報を取得
			OvertimeRequestListDtoInterface dto = (OvertimeRequestListDtoInterface)baseDto;
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getTmdOvertimeRequestId());
			aryLblRequestDate[i] = DateUtility.getStringDateAndDay(dto.getRequestDate());
			aryLblOvertimeTypeName[i] = getOvertimeTypeName(dto.getOvertimeType());
			aryLblOvertimeTypeCode[i] = String.valueOf(dto.getOvertimeType());
			aryLblRequestTime[i] = TimeUtility.getStringPeriodTime(mospParams, dto.getRequestTime());
			aryLblResultTime[i] = dto.getResultsTime();
			aryLblRequestReason[i] = dto.getRequestReason();
			aryLblWorkflowStatus[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryStatusStyle[i] = getStatusColor(dto.getState());
			aryLblApproverName[i] = dto.getApproverName();
			aryLblOnOff[i] = getButtonOnOff(dto.getState(), dto.getStage());
			aryWorkflowStatus[i] = dto.getState();
			aryWorkflow[i] = dto.getWorkflow();
			i++;
		}
		// データをVOに設定
		vo.setAryCkbOvertimeRequestListId(aryCkbRecordId);
		vo.setAryLblDate(aryLblRequestDate);
		vo.setAryLblOverTimeTypeName(aryLblOvertimeTypeName);
		vo.setAryLblOverTimeTypeCode(aryLblOvertimeTypeCode);
		vo.setAryLblScheduleTime(aryLblRequestTime);
		vo.setAryLblResultTime(aryLblResultTime);
		vo.setAryLblRequestReason(aryLblRequestReason);
		vo.setAryLblState(aryLblWorkflowStatus);
		vo.setAryStateStyle(aryStatusStyle);
		vo.setAryLblApprover(aryLblApproverName);
		vo.setAryLblOnOff(aryLblOnOff);
		vo.setAryWorkflowStatus(aryWorkflowStatus);
		vo.setAryWorkflow(aryWorkflow);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setOvertimeDtoFields(OvertimeRequestDtoInterface dto) throws MospException {
		// VO取得
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmdOvertimeRequestId(vo.getRecordId());
		dto.setPersonalId(vo.getPersonalId());
		dto.setRequestDate(getEditRequestDate());
		int workRequestTime = (getInt(vo.getPltEditRequestHour()) * TimeConst.CODE_DEFINITION_HOUR)
				+ getInt(vo.getPltEditRequestMinute());
		dto.setRequestTime(workRequestTime);
		dto.setOvertimeType(Integer.parseInt(vo.getPltEditOverTimeType()));
		dto.setRequestReason(vo.getTxtEditRequestReason());
	}
	
	/**
	 * @return 文字列となっているリクエスト年月日をDate型へ変換して返す。
	 */
	protected Date getEditRequestDate() {
		// VO取得
		OvertimeRequestVo vo = (OvertimeRequestVo)mospParams.getVo();
		return getDate(vo.getPltEditRequestYear(), vo.getPltEditRequestMonth(), vo.getPltEditRequestDay());
	}
	
}
