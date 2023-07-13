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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.comparator.settings.SubHolidayRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestListDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.input.vo.SubHolidayRequestVo;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 代休申請情報の確認と編集を行う。<br>
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
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li></ul>
 */
public class SubHolidayRequestAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの代休申請画面を表示する。<br>
	 */
	public static final String		CMD_SHOW				= "TM1700";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 代休申請画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW			= "TM1701";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に代休申請情報の検索を行う。<br>
	 */
	public static final String		CMD_SEARCH				= "TM1702";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たな下書情報作成や申請を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String		CMD_RE_SHOW				= "TM1703";
	
	/**
	 * 下書コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を代休情報テーブルに登録し、下書情報として保存する。<br>
	 */
	public static final String		CMD_DRAFT				= "TM1704";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を代休情報テーブルに登録し、代休申請を行う。以降、このレコードは上長が差戻をしない限り編集不可となる。<br>
	 * 代休日が決定していない状態で申請を行おうとした場合はエラーメッセージにて通知し、申請は実行されない。<br>
	 */
	public static final String		CMD_APPLI				= "TM1705";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String		CMD_TRANSFER			= "TM1706";
	
	/**
	 * 取下コマンド。<br>
	 * <br>
	 * 下書状態または差戻状態で登録されていたレコードの取下を行う。<br>
	 * 取下後、対象の代休申請情報は未申請状態へ戻る。<br>
	 */
	public static final String		CMD_WITHDRAWN			= "TM1707";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String		CMD_SORT				= "TM1708";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String		CMD_PAGE				= "TM1709";
	
	/**
	 * 一括取下コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている未承認状態のレコードの取下処理を繰り返し行う。
	 * ひとつもチェックが入っていない状態で一括取下ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。
	 */
	public static final String		CMD_BATCH_WITHDRAWN		= "TM1736";
	
	/**
	 * 代休日決定コマンド。<br>
	 * <br>
	 * 申請欄の代休日プルダウンに入力された代休日を基に対象出勤日の検索を行い、結果を出勤日プルダウンに表示する。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE	= "TM1790";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 申請テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 申請テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE			= "TM1791";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を申請テーブルの各入力欄にそれぞれ表示させる。申請テーブルヘッダに新規登録モード切替リンクを表示させる。
	 */
	public static final String		CMD_EDIT_MODE			= "TM1792";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている下書状態のレコードの申請処理を繰り返し行う。<br>
	 * ひとつもチェックが入っていない状態で一括更新ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。<br>
	 */
	public static final String		CMD_BATCH_UPDATE		= "TM1795";
	
	/**
	 * 機能コード。<br>
	 */
	protected static final String	CODE_FUNCTION			= TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public SubHolidayRequestAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SubHolidayRequestVo();
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
			// 下書き
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
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 代休日決定
			prepareVo();
			setActivationDate();
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
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
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
		vo.setComparatorName(SubHolidayRequestRequestDateComparator.class.getName());
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
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
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
		vo.setComparatorName(SubHolidayRequestRequestDateComparator.class.getName());
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 検索クラス取得
		SubHolidayRequestSearchBeanInterface search = timeReference().subHolidayRequestSearch();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// VOの値を検索クラスへ設定
		search.setPersonalId(personalId);
		// 代休日
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
		// 出勤日
		int workYear = Integer.parseInt(vo.getPltSearchWorkYear());
		int startWorkMonth = getInt(TimeConst.CODE_DISPLAY_JANUARY);
		int endWorkMonth = getInt(TimeConst.CODE_DISPLAY_DECEMBER);
		// 月検索(検索月が指定されている)ならば
		if (MospUtility.isEmpty(vo.getPltSearchWorkMonth()) == false) {
			// 月を設定
			startWorkMonth = getInt(vo.getPltSearchWorkMonth());
			endWorkMonth = startWorkMonth;
		}
		// 年月(FROM及びTO)で設定適用エンティティを取得
		ApplicationEntity appWorkFrom = timeMaster.getApplicationEntity(personalId, workYear, startWorkMonth);
		ApplicationEntity appWorkTo = timeMaster.getApplicationEntity(personalId, workYear, endWorkMonth);
		// 締期間の開始及び最終日を取得
		Date workFirstDate = appWorkFrom.getCutoffEntity().getCutoffFirstDate(workYear, startWorkMonth, mospParams);
		Date workLastDate = appWorkTo.getCutoffEntity().getCutoffLastDate(workYear, endWorkMonth, mospParams);
		// 締期間を検索範囲に設定
		search.setWorkStartDate(workFirstDate);
		search.setWorkEndDate(workLastDate);
		search.setWorkflowStatus(vo.getPltSearchState());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<SubHolidayRequestListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(SubHolidayRequestRequestDateComparator.class.getName());
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
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		SubHolidayRequestRegistBeanInterface regist = time().subHolidayRequestRegist();
		// DTOの準備
		SubHolidayRequestDtoInterface dto = timeReference().subHolidayRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = regist.getInitDto();
		}
		// DTOに値を設定
		setSubHolidayDtoFields(dto);
		// 妥当性チェック
		regist.validate(dto);
		// 申請の相関チェック
		regist.checkDraft(dto);
		// 登録クラスの取得。
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY);
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
		// 申請年月日が含まれる締月を取得し検索条件に設定
		Date searchRequestDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getRequestDate());
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchRequestDate));
		vo.setPltSearchRequestMonth((DateUtility.getStringMonthM(searchRequestDate)));
		// 申請勤務年月日が含まれる締月を取得し検索条件に設定
		Date searchWorkDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getWorkDate());
		vo.setPltSearchWorkYear(DateUtility.getStringYear(searchWorkDate));
		vo.setPltSearchWorkMonth(DateUtility.getStringMonthM(searchWorkDate));
		// 検索
		search();
		// 履歴編集対象を取得
		setEditUpdateMode(dto.getRequestDate(), dto.getHolidayRange());
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_DRAFT);
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli() throws MospException {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		SubHolidayRequestRegistBeanInterface regist = time().subHolidayRequestRegist();
		AttendanceTransactionRegistBeanInterface attendanceTransactionRegist = time().attendanceTransactionRegist();
		// DTOの準備
		SubHolidayRequestDtoInterface dto = timeReference().subHolidayRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = regist.getInitDto();
		}
		// DTOに値を設定
		setSubHolidayDtoFields(dto);
		// 申請の相関チェック
		regist.checkAppli(dto);
		// 登録クラスの取得
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY);
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
			// 勤怠データ削除
			regist.deleteAttendance(dto);
			// 勤怠データ下書
			regist.draftAttendance(dto);
			// 勤怠トランザクション登録
			attendanceTransactionRegist.regist(dto);
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
		addHalfHolidayRequestMessage(dto);
		// 登録結果確認
		if (!mospParams.hasErrorMessage()) {
			// 登録が成功した場合、初期状態に戻す。
			insertMode();
			// 申請年月日が含まれる締月を取得し検索条件に設定
			Date searchRequestDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(),
					dto.getRequestDate());
			vo.setPltSearchRequestYear(DateUtility.getStringYear(searchRequestDate));
			vo.setPltSearchRequestMonth((DateUtility.getStringMonthM(searchRequestDate)));
			// 申請勤務年月日が含まれる締月を取得し検索条件に設定
			Date searchWorkDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getWorkDate());
			vo.setPltSearchWorkYear(DateUtility.getStringYear(searchWorkDate));
			vo.setPltSearchWorkMonth(DateUtility.getStringMonthM(searchWorkDate));
			// 検索
			search();
		}
	}
	
	/**
	* 取下処理を行う。<br>
	 * @throws MospException 例外処理発生時
	*/
	protected void withdrawn() throws MospException {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		SubHolidayRequestRegistBeanInterface regist = time().subHolidayRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		WorkflowCommentRegistBeanInterface workflowCommentRegist = platform().workflowCommentRegist();
		// DTOの準備
		SubHolidayRequestDtoInterface dto = timeReference().subHolidayRequest().findForKey(vo.getRecordId());
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
		// 削除結果確認
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
		// 申請年月日が含まれる締月を取得し検索条件に設定
		Date searchRequestDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getRequestDate());
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchRequestDate));
		vo.setPltSearchRequestMonth((DateUtility.getStringMonthM(searchRequestDate)));
		// 申請勤務年月日が含まれる締月を取得し検索条件に設定
		Date searchWorkDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getWorkDate());
		vo.setPltSearchWorkYear(DateUtility.getStringYear(searchWorkDate));
		vo.setPltSearchWorkMonth(DateUtility.getStringMonthM(searchWorkDate));
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
	 */
	protected void page() {
		setVoList(pageList());
	}
	
	/**
	 * 一括取下処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void batchWithdrawn() throws MospException {
		// VO取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		time().subHolidayRequestRegist().withdrawn(getIdArray(vo.getCkbSelect()));
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
		String searchRequestYear = vo.getPltSearchRequestYear();
		String searchRequestMonth = vo.getPltSearchRequestMonth();
		String searchWorkYear = vo.getPltSearchWorkYear();
		String searchWorkMonth = vo.getPltSearchWorkMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 表示期間を再設定
		vo.setPltSearchRequestYear(searchRequestYear);
		vo.setPltSearchRequestMonth(searchRequestMonth);
		vo.setPltSearchWorkYear(searchWorkYear);
		vo.setPltSearchWorkMonth(searchWorkMonth);
		// 検索
		search();
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		SubHolidayRequestRegistBeanInterface regist = time().subHolidayRequestRegist();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 利用不可である場合
			if (isAvailable(getEditRequestDate(), CODE_FUNCTION) == false) {
				// 処理終了
				return;
			}
			SubHolidayRequestDtoInterface dto = timeReference().subHolidayRequest().findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			dto.setPersonalId(vo.getPersonalId());
			dto.setRequestDate(getEditRequestDate());
			dto.setHolidayRange(Integer.parseInt(vo.getPltEditHolidayRange()));
			// 有効日設定時のチェック
			regist.checkSetRequestDate(dto);
			if (mospParams.hasErrorMessage()) {
				// 決定失敗メッセージを設定
				PfMessageUtility.addMessageDecisiontFailed(mospParams);
				return;
			}
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 代休日プルダウン取得
		if (!setEditPulldown()) {
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// 代休残数設定
		setCompensationDay();
		// 代休未取得日数設定
		setUnused();
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// プルダウン設定
		setPulldown();
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 基本情報チェック
		timeReference().subHolidayRequest().chkBasicInfo(vo.getPersonalId(), getEditRequestDate());
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
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
			vo.setComparatorName(SubHolidayRequestRequestDateComparator.class.getName());
			// 検索
			search();
		}
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getDate(getTransferredActivateDate()), Integer.parseInt(getTransferredType()));
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		setActivationDate();
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		boolean containsHalfHoliday = time().subHolidayRequestRegist().update(getIdArray(vo.getCkbSelect()));
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
		// 半休が含まれる場合
		if (containsHalfHoliday) {
			// 半休申請メッセージ設定
			TimeMessageUtility.addHalfHolidayRequestNotice(mospParams);
		}
		// 表示期間を保持
		String searchRequestYear = vo.getPltSearchRequestYear();
		String searchRequestMonth = vo.getPltSearchRequestMonth();
		String searchWorkYear = vo.getPltSearchWorkYear();
		String searchWorkMonth = vo.getPltSearchWorkMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 表示期間を再設定
		vo.setPltSearchRequestYear(searchRequestYear);
		vo.setPltSearchRequestMonth(searchRequestMonth);
		vo.setPltSearchWorkYear(searchWorkYear);
		vo.setPltSearchWorkMonth(searchWorkMonth);
		// 検索
		search();
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
		// 承認履歴画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(ApprovalHistoryAction.CMD_APPROVAL_HISTORY_LIEU_SELECT_SHOW);
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		Date targetDate = date;
		if (getTargetDate() != null) {
			targetDate = getTargetDate();
		}
		vo.setPltEditRequestYear(DateUtility.getStringYear(targetDate));
		vo.setPltEditRequestMonth(DateUtility.getStringMonthM(targetDate));
		vo.setPltEditRequestDay(DateUtility.getStringDayD(targetDate));
		vo.setRecordId(0);
		vo.setPltEditHolidayRange(String.valueOf(TimeConst.CODE_HOLIDAY_RANGE_ALL));
		vo.setPltEditWorkDate("");
		// 申請年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(vo.getPersonalId(), date);
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
		vo.setPltSearchWorkYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchWorkMonth(DateUtility.getStringMonthM(searchDate));
		vo.setPltSearchState("");
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
		vo.setAryLblCompensationWorkDate(new String[0]);
		vo.setAryLblCompensationExpirationDate(new String[0]);
		vo.setAryLblCompensationType(new String[0]);
		vo.setAryLblCompensationRange(new String[0]);
		vo.setAryLblCompensationDayTh(new String[0]);
		vo.setAryLblCompensationDayForWorkOnDayOff(new String[0]);
		vo.setAryLblCompensationDayForNightWork(new String[0]);
	}
	
	/**
	 * 代休残数を設定する。<br>
	 * システム日付の締め期間初日から申請可能な代休情報を取得し、設定する。<br>
	 * 
	 * @throws MospException 例外処理発生時
	 */
	protected void setCompensationDay() throws MospException {
		// VOを取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// クラス準備
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		TimeMasterBeanInterface timeMaster = timeReference().master();
		Date systemDate = getSystemDate();
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoffForPersonalId(personalId, systemDate);
		// 締期間初日を取得
		Date cutoffFirstDate = cutoff.getCutoffFirstDate(systemDate, mospParams);
		// 締期間初日で設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, cutoffFirstDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid() == false) {
			// 処理終了
			return;
		}
		// リスト準備
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		// 締め期間初日時点の代休取得期限～システム
		for (SubHolidayDtoInterface subHolidayDto : timeReference().subHoliday().getFindForList(personalId,
				cutoffFirstDate, systemDate, TimeConst.HOLIDAY_TIMES_HALF)) {
			// 利用日数準備
			float useDays = 0;
			// 代休申請情報情報毎に処理
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : timeReference().subHolidayRequest()
				.getSubHolidayRequestList(subHolidayDto.getPersonalId(), subHolidayDto.getWorkDate(),
						subHolidayDto.getTimesWork(), subHolidayDto.getSubHolidayType())) {
				// ワークフロ情報取得
				WorkflowDtoInterface workflowDto = workflowIntegrate
					.getLatestWorkflowInfo(subHolidayRequestDto.getWorkflow());
				// 申請済でない場合
				if (!WorkflowUtility.isApplied(workflowDto)) {
					continue;
				}
				// 利用日数を設定
				useDays += TimeRequestUtility.getDays(subHolidayRequestDto);
			}
			// 利用日数が同じか多い場合
			if (subHolidayDto.getSubHolidayDays() <= useDays) {
				continue;
			}
			// 申請可能残に日数取得
			subHolidayDto.setSubHolidayDays(subHolidayDto.getSubHolidayDays() - useDays);
			// リストに追加
			list.add(subHolidayDto);
		}
		// 配列準備
		String[] aryLblCompensationWorkDate = new String[list.size()];
		String[] aryLblCompensationExpirationDate = new String[list.size()];
		String[] aryLblCompensationType = new String[list.size()];
		String[] aryLblCompensationRange = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			// 代休情報取得
			SubHolidayDtoInterface dto = list.get(i);
			// 勤務日を取得
			Date workDate = dto.getWorkDate();
			// 休日出勤日時点の勤怠設定エンティティを取得
			TimeSettingEntityInterface timeSetting = timeMaster.getTimeSettingForPersonalId(personalId, workDate);
			// 取得期限取得
			Date limitDate = timeSetting.getSubHolidayLimitDate(dto);
			// 代休種別名称を取得
			String subHolidayName = TimeRequestUtility.getSubHolidayTypeName(dto.getSubHolidayType(), mospParams);
			// 休暇範囲準備
			String subHolidayRange = TimeRequestUtility.getSubHolidayDaysAbbr(dto.getSubHolidayDays(), mospParams);
			// 設定
			aryLblCompensationWorkDate[i] = getStringDateAndDay(dto.getWorkDate());
			aryLblCompensationExpirationDate[i] = getStringDateAndDay(limitDate);
			aryLblCompensationType[i] = subHolidayName;
			aryLblCompensationRange[i] = subHolidayRange;
		}
		// VOに設定
		vo.setAryLblCompensationWorkDate(aryLblCompensationWorkDate);
		vo.setAryLblCompensationExpirationDate(aryLblCompensationExpirationDate);
		vo.setAryLblCompensationType(aryLblCompensationType);
		vo.setAryLblCompensationRange(aryLblCompensationRange);
	}
	
	/**
	 * 代休未消化日数を設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setUnused() throws MospException {
		// 処理無し
	}
	
	/**
	 * プルダウン設定
	 */
	private void setPulldown() {
		// VO準備
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		int editRequestYear = DateUtility.getYear(date);
		// 編集項目設定
		vo.setAryPltEditRequestYear(getYearArray(editRequestYear));
		vo.setAryPltEditRequestMonth(getMonthArray());
		// 31日まで取得
		vo.setAryPltEditRequestDay(getDayArray());
		// 検索項目設定
		vo.setAryPltSearchRequestYear(getYearArray(editRequestYear));
		vo.setAryPltSearchRequestMonth(getMonthArray(true));
		vo.setAryPltSearchWorkYear(getYearArray(editRequestYear));
		vo.setAryPltSearchWorkMonth(getMonthArray(true));
		// プルダウン設定
		vo.setAryPltEditWorkDate(getInputActivateDateCompensatoryLeavePulldown());
		// 編集欄 休暇種別
		vo.setAryPltEditHolidayType(mospParams.getProperties().getCodeArray(TimeConst.CODE_SUBSTITUTE1_RANGE, false));
		// 検索欄 休暇種別
		vo.setAryPltSearchState(mospParams.getProperties().getCodeArray(TimeConst.CODE_APPROVAL_STATE, true));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外処理発生時
	 */
	protected void setSubHolidayDtoFields(SubHolidayRequestDtoInterface dto) throws MospException {
		// VO取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		String[] aryWorkDate = vo.getPltEditWorkDate().split(MospConst.APP_PROPERTY_SEPARATOR);
		// VOの値をDTOに設定
		dto.setTmdSubHolidayRequestId(vo.getRecordId());
		dto.setPersonalId(vo.getPersonalId());
		dto.setRequestDate(DateUtility.getDate(vo.getPltEditRequestYear(), vo.getPltEditRequestMonth(),
				vo.getPltEditRequestDay()));
		dto.setHolidayRange(Integer.parseInt(vo.getPltEditHolidayRange()));
		dto.setWorkDate(DateUtility.getDate(aryWorkDate[0]));
		dto.setTimesWork(1);
		dto.setWorkDateSubHolidayType(Integer.parseInt(aryWorkDate[1]));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblRequestDate = new String[list.size()];
		String[] aryLblWorkDate = new String[list.size()];
		String[] aryLblWorkflowStatus = new String[list.size()];
		String[] aryStatusStyle = new String[list.size()];
		String[] aryLblApproverName = new String[list.size()];
		String[] aryLblDate = new String[list.size()];
		String[] aryLblRange = new String[list.size()];
		String[] aryLblWorkflow = new String[list.size()];
		String[] aryLblOnOff = new String[list.size()];
		String[] aryWorkflowStatus = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			SubHolidayRequestListDtoInterface dto = (SubHolidayRequestListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getTmdSubHolidayRequestId());
			aryLblRequestDate[i] = DateUtility.getStringDateAndDay(dto.getRequestDate()) + MospConst.STR_SB_SPACE
					+ getHolidayRange(getInt(dto.getSubHolidayRange()));
			aryLblWorkDate[i] = setWorkDateSubHolidayType(dto.getWorkDateHolidayType()) + MospConst.STR_SB_SPACE
					+ DateUtility.getStringDateAndDay(dto.getWorkDate());
			aryLblWorkflowStatus[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryStatusStyle[i] = getStatusColor(dto.getState());
			aryLblApproverName[i] = dto.getApproverName();
			aryLblDate[i] = DateUtility.getStringDate(dto.getRequestDate());
			aryLblRange[i] = dto.getSubHolidayRange();
			aryLblWorkflow[i] = String.valueOf(dto.getWorkflow());
			aryLblOnOff[i] = getButtonOnOff(dto.getState(), dto.getStage());
			aryWorkflowStatus[i] = dto.getState();
			aryWorkflow[i] = dto.getWorkflow();
		}
		// データをVOに設定
		vo.setAryCkbSubHolidayRequestListId(aryCkbRecordId);
		vo.setAryLblRequestDate(aryLblRequestDate);
		vo.setAryLblWorkDate(aryLblWorkDate);
		vo.setAryLblState(aryLblWorkflowStatus);
		vo.setAryStateStyle(aryStatusStyle);
		vo.setAryLblApprover(aryLblApproverName);
		vo.setAryLblDate(aryLblDate);
		vo.setAryLblRange(aryLblRange);
		vo.setAryLblWorkflow(aryLblWorkflow);
		vo.setAryLblOnOff(aryLblOnOff);
		vo.setAryWorkflowStatus(aryWorkflowStatus);
		vo.setAryWorkflow(aryWorkflow);
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 代休日と休暇範囲で編集対象情報を取得する。<br>
	 * @param requestDate 代休日
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(Date requestDate, int holidayRange) throws MospException {
		// VO取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 履歴編集対象取得
		SubHolidayRequestDtoInterface dto = timeReference().subHolidayRequest().findForKeyOnWorkflow(vo.getPersonalId(),
				requestDate, holidayRange);
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
	protected void setVoFields(SubHolidayRequestDtoInterface dto) throws MospException {
		// VO取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmdSubHolidayRequestId());
		vo.setPltEditRequestYear(DateUtility.getStringYear(dto.getRequestDate()));
		vo.setPltEditRequestMonth(DateUtility.getStringMonthM(dto.getRequestDate()));
		vo.setPltEditRequestDay(DateUtility.getStringDayD(dto.getRequestDate()));
		vo.setPltEditHolidayRange(String.valueOf(dto.getHolidayRange()));
		vo.setPltEditWorkDate(DateUtility.getStringDate(dto.getWorkDate()) + MospConst.APP_PROPERTY_SEPARATOR
				+ dto.getWorkDateSubHolidayType());
		vo.setModeCardEdit(getApplicationMode(dto.getWorkflow()));
	}
	
	/**
	 * プルダウン(編集)の設定を行う。<br>
	 * @return PullDown取得結果
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Boolean setEditPulldown() throws MospException {
		// VO取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			Date targetDate = getEditRequestDate();
			SubHolidayRequestDtoInterface dto = timeReference().subHolidayRequest().findForKey(vo.getRecordId());
			vo.setAryPltEditWorkDate(timeReference().subHoliday().getSelectArray(vo.getPersonalId(), targetDate,
					vo.getPltEditHolidayRange(), dto));
			// データが存在しない場合
			if (vo.getAryPltEditWorkDate().length == 0) {
				vo.setAryPltEditWorkDate(getInputActivateNoDataLeavePulldown());
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_8,
						mospParams.getName("CompensatoryHoliday", "Day"),
						mospParams.getName("Target", "GoingWork", "Day"));
				return false;
			}
			// 承認者プルダウン設定
			if (setApproverPullDown(vo.getPersonalId(), DateUtility.getDate(vo.getPltEditRequestYear(),
					vo.getPltEditRequestMonth(), vo.getPltEditRequestDay()),
					PlatformConst.WORKFLOW_TYPE_TIME) == false) {
				vo.setAryPltEditWorkDate(getInputActivateNoDataLeavePulldown());
				return false;
			}
			// 同日付に時差出勤が申請されているか確認する。
			getDifferenceRequest1(vo.getPersonalId(), targetDate);
			return true;
		}
		// プルダウン設定
		vo.setAryPltEditWorkDate(getInputActivateDateCompensatoryLeavePulldown());
		String[] aryPltLblApproverSetting = new String[0];
		vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
		return false;
	}
	
	/**
	 * 有効日編集中のプルダウンを取得する。<br>
	 * @return 有効日編集中プルダウン
	 */
	protected String[][] getInputActivateDateCompensatoryLeavePulldown() {
		String[][] aryPulldown = { { "", mospParams.getName("InputCompensatoryLeave") } };
		return aryPulldown;
	}
	
	/**
	 * 有効日編集中のプルダウンを取得する。<br>
	 * @return 有効日編集中プルダウン
	 */
	protected String[][] getInputActivateNoDataLeavePulldown() {
		String[][] aryPulldown = { { "", mospParams.getName("InputActivateNoDataLeave") } };
		return aryPulldown;
	}
	
	/**
	 * @return 文字列となっているリクエスト年月日をDate型へ変換して返す。
	 */
	protected Date getEditRequestDate() {
		// VO取得
		SubHolidayRequestVo vo = (SubHolidayRequestVo)mospParams.getVo();
		return getDate(vo.getPltEditRequestYear(), vo.getPltEditRequestMonth(), vo.getPltEditRequestDay());
	}
	
	/**
	 * 半休の場合のメッセージ表示
	 * @param dto 代休申請DTO
	 * @throws MospException インスタンスの生成或いはSQLの実行が失敗した場合
	 */
	protected void addHalfHolidayRequestMessage(SubHolidayRequestDtoInterface dto) throws MospException {
		// 午前休又は午後休の場合
		if (TimeRequestUtility.isHolidayRangeHalf(dto)) {
			// 半休申請メッセージ設定
			TimeMessageUtility.addHalfHolidayRequestNotice(mospParams);
		}
	}
	
}
