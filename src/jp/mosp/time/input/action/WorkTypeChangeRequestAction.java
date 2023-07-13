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
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.comparator.settings.WorkTypeChangeRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestListDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.input.vo.WorkTypeChangeRequestVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤務形態変更申請情報の確認と編集を行う。<br>
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
 * {@link #CMD_BATCH_UPDATE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_DRAFT}
 * </li><li>
 * {@link #CMD_APPLI}
 * </li><li>
 * {@link #CMD_WITHDRAWN}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_SET_VIEW_PERIOD}
 * </li><li>
 * {@link #CMD_BATCH_WITHDRAWN}
 * </li></ul>
 */
public class WorkTypeChangeRequestAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの勤務形態変更申請画面を表示する。
	 */
	public static final String		CMD_SHOW				= "TM011100";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 勤務形態変更申請画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW			= "TM011101";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に勤務形態変更申請情報の検索を行う。
	 */
	public static final String		CMD_SEARCH				= "TM011102";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 一度この画面を表示した後、パンくずリスト等を用いて他の画面から改めて遷移した場合、
	 * 各種情報の登録状況を更新した上で保持していた検索条件で検索を行って画面を再表示する。
	 */
	public static final String		CMD_RE_SHOW				= "TM011103";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている下書状態のレコードの申請処理を繰り返し行う。
	 * ひとつもチェックが入っていない状態で一括更新ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。
	 */
	public static final String		CMD_BATCH_UPDATE		= "TM011106";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。
	 */
	public static final String		CMD_SORT				= "TM011107";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。
	 */
	public static final String		CMD_PAGE				= "TM011108";
	
	/**
	 * 出勤日決定コマンド。<br>
	 * <br>
	 * 入力した出勤日の勤務形態をカレンダより取得して略称をラベルに出力する。
	 * 決定した出勤日時点で申請者に紐付けられている承認者情報を取得し、
	 * 各承認段階毎に用意されている承認者プルダウンに表示させる。
	 * 無効な日付（全日休日・休暇または半日休暇の組み合わせ）や申請者に承認者が
	 * 紐付けられていない日付で決定した場合エラーメッセージを表示し、処理を中断する。
	 */
	public static final String		CMD_SET_ACTIVATION_DATE	= "TM011109";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 申請テーブルの各入力欄に表示されているレコード内容をクリアにする。
	 * 申請テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。
	 */
	public static final String		CMD_INSERT_MODE			= "TM011110";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を申請テーブルの各入力欄にそれぞれ表示させる。
	 * 申請テーブルヘッダに新規登録モード切替リンクを表示させる。
	 */
	public static final String		CMD_EDIT_MODE			= "TM011111";
	
	/**
	 * 下書コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を勤務形態変更情報テーブルに登録し、下書情報として保存する。
	 * 入力チェックは実行されない。
	 */
	public static final String		CMD_DRAFT				= "TM011114";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を勤務形態変更情報テーブルに登録し、勤務形態変更申請を行う。以降、このレコードは上長が差戻をしない限り編集不可となる。
	 * 出勤日が決定していない、出勤日で申請不可な日付が選択されている、申請理由の項目が入力されていない、
	 * といった状態で申請を行おうとした場合は場合はエラーメッセージにて通知し、申請は実行されない。
	 * 終了日指定チェックボックスにチェックが入っている場合は終了日の日付チェックを行い、
	 * 出勤日から終了日までの勤務日でまとめて申請を行う。
	 */
	public static final String		CMD_APPLI				= "TM011115";
	
	/**
	 * 取下コマンド。<br>
	 * <br>
	 * 下書状態または差戻状態で登録されていたレコードの取下を行う。
	 * 取下後、対象の勤務形態変更申請情報は未申請状態へ戻る。
	 */
	public static final String		CMD_WITHDRAWN			= "TM011118";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String		CMD_TRANSFER			= "TM011120";
	
	/**
	 * 表示期間決定コマンド。<br>
	 * <br>
	 * 入力した表示期間時点で有効な勤務形態情報を取得し、その略称と勤務時間を勤務形態プルダウンに表示する。
	 */
	public static final String		CMD_SET_VIEW_PERIOD		= "TM011131";
	
	/**
	 * 一括取下コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている未承認状態のレコードの取下処理を繰り返し行う。
	 * ひとつもチェックが入っていない状態で一括取下ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。
	 */
	public static final String		CMD_BATCH_WITHDRAWN		= "TM011136";
	
	/**
	 * 機能コード。<br>
	 */
	protected static final String	CODE_FUNCTION			= TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public WorkTypeChangeRequestAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new WorkTypeChangeRequestVo();
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
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
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
		} else if (mospParams.getCommand().equals(CMD_DRAFT)) {
			// 下書
			prepareVo();
			draft();
		} else if (mospParams.getCommand().equals(CMD_APPLI)) {
			// 申請
			prepareVo();
			appli();
		} else if (mospParams.getCommand().equals(CMD_WITHDRAWN)) {
			// 取下
			prepareVo();
			withdrawn();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_SET_VIEW_PERIOD)) {
			// 表示期間決定
			prepareVo();
			setViewPeriod();
		} else if (mospParams.getCommand().equals(CMD_BATCH_WITHDRAWN)) {
			// 一括取下
			prepareVo();
			batchWithdrawn();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void show() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
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
		vo.setComparatorName(WorkTypeChangeRequestRequestDateComparator.class.getName());
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void select() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
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
		vo.setComparatorName(WorkTypeChangeRequestRequestDateComparator.class.getName());
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void search() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// beanの準備
		WorkTypeChangeRequestSearchBeanInterface search = timeReference().workTypeChangeRequestSearch();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// VOの値を検索クラスへ設定
		search.setPersonalId(personalId);
		search.setWorkflowStatus(vo.getPltSearchState());
		search.setWorkTypeCode(vo.getPltSearchWorkType());
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
		List<WorkTypeChangeRequestListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(WorkTypeChangeRequestRequestDateComparator.class.getName());
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
	 * 一括更新処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void batchUpdate() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		time().workTypeChangeRequestRegist().update(getIdArray(vo.getCkbSelect()));
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
		String searchYear = vo.getPltSearchRequestYear();
		String searchMonth = vo.getPltSearchRequestMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 検索プルダウン再設定
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
	 * 一覧のページ処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		WorkTypeChangeRequestRegistBeanInterface regist = time().workTypeChangeRequestRegist();
		if (PlatformConst.MODE_ACTIVATE_DATE_CHANGING.equals(vo.getModeActivateDate())) {
			// 利用不可である場合
			if (isAvailable(getEditRequestDate(), CODE_FUNCTION) == false) {
				// 処理終了
				return;
			}
			// 承認者用プルダウン作成
			if (!setApproverPullDown(vo.getPersonalId(), getEditRequestDate(), PlatformConst.WORKFLOW_TYPE_TIME)) {
				// 決定失敗メッセージを設定
				PfMessageUtility.addMessageDecisiontFailed(mospParams);
				return;
			}
			WorkTypeChangeRequestDtoInterface dto = timeReference().workTypeChangeRequest()
				.findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			dto.setPersonalId(vo.getPersonalId());
			dto.setRequestDate(getEditRequestDate());
			// 有効日設定時のチェック
			regist.checkSetActivationDate(dto);
			if (mospParams.hasErrorMessage()) {
				// 決定失敗メッセージを設定
				PfMessageUtility.addMessageDecisiontFailed(mospParams);
				return;
			}
			// 期間終了日設定
			setEditEndDate();
			// 有効日(編集)モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			vo.setCkbEndDate(MospConst.CHECKBOX_OFF);
			vo.setAryPltLblApproverSetting(new String[0]);
			// 有効日(編集)モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 勤務形態設定
		setLblWorkTypeName();
		// 勤務形態プルダウン設定
		setEditWorkTypePulldown();
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void insertMode() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 有効日(検索)モード設定
		vo.setModeSearchActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 初期値設定
		setDefaultValues();
		// 期間終了日設定
		setEditEndDate();
		// 表示期間設定
		setSearchRequestDate(getSystemDate());
		// 変更前勤務形態設定
		setLblWorkTypeName();
		// プルダウン設定
		setPulldown();
		// 勤務形態プルダウン設定
		setEditWorkTypePulldown();
		// 検索勤務形態プルダウン設定
		setSearchWorkTypePulldown();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void editMode() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// リクエストから個人IDを取得
		String personalId = getTargetPersonalId();
		if (personalId != null) {
			// 人事情報をVOに設定
			setEmployeeInfo(personalId, getSystemDate());
			// ページ繰り設定
			setPageInfo(CMD_PAGE, getListLength());
			// 新規登録モード設定
			insertMode();
			// デフォルトソートキー及びソート順設定
			vo.setComparatorName(WorkTypeChangeRequestRequestDateComparator.class.getName());
			// 検索
			search();
		}
		String transferredActivateDate = getTransferredActivateDate();
		Date targetDate = getTargetDate();
		if (transferredActivateDate != null && !transferredActivateDate.isEmpty()) {
			// 一覧
			setEditUpdateMode(getDate(transferredActivateDate));
		} else if (targetDate != null) {
			// 勤怠詳細から遷移
			setEditUpdateMode(targetDate);
		}
		// 有効日モード設定(下のメソッドで決定状態にするために変更状態に設定)
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 有効日を設定
		setActivationDate();
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * @param requestDate 出勤日
	 * @throws MospException 例外処理発生時
	 */
	protected void setEditUpdateMode(Date requestDate) throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		WorkTypeChangeRequestDtoInterface dto = timeReference().workTypeChangeRequest()
			.findForKeyOnWorkflow(vo.getPersonalId(), requestDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
	}
	
	/**
	 * 下書処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void draft() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// beanの準備
		WorkTypeChangeRequestReferenceBeanInterface reference = timeReference().workTypeChangeRequest();
		WorkTypeChangeRequestRegistBeanInterface regist = time().workTypeChangeRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		Date requestDate = getEditRequestDate();
		Date startDate = requestDate;
		Date endDate = requestDate;
		if (isCheckedEndDate()) {
			// 期間指定の場合
			endDate = getEditEndDate();
		}
		// 期間チェック
		checkPeriod(startDate, endDate);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 開始日のカレンダチェック
		regist.checkSchedule(vo.getPersonalId(), startDate);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 終了日のカレンダチェック
		regist.checkSchedule(vo.getPersonalId(), endDate);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		List<Date> list = TimeUtility.getDateList(startDate, endDate);
		for (Date date : list) {
			// DTOの準備
			WorkTypeChangeRequestDtoInterface dto = reference.findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			if (!date.equals(startDate)) {
				dto = regist.getInitDto();
			}
			// DTOに値を設定
			setDtoFields(dto, date);
			if (!date.equals(startDate)) {
				dto.setTmdWorkTypeChangeRequestId(0);
			}
			// 妥当性チェック
			regist.validate(dto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// 勤務形態コード取得
			String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), date);
			if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
					|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
				// 法定休日・所定休日の場合
				continue;
			}
			// 申請の相関チェック
			regist.checkDraft(dto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto == null) {
				workflowDto = workflowRegist.getInitDto();
				workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE);
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
		// 表示期間設定
		setSearchRequestDate(requestDate);
		// 検索
		search();
		// 履歴編集対象を取得
		setEditUpdateMode(requestDate);
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void appli() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// beanの準備
		WorkTypeChangeRequestReferenceBeanInterface reference = timeReference().workTypeChangeRequest();
		WorkTypeChangeRequestRegistBeanInterface regist = time().workTypeChangeRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		Date requestDate = getEditRequestDate();
		Date startDate = requestDate;
		Date endDate = requestDate;
		if (isCheckedEndDate()) {
			// 期間指定の場合
			endDate = getEditEndDate();
		}
		// 期間チェック
		checkPeriod(startDate, endDate);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 開始日のカレンダチェック
		regist.checkSchedule(vo.getPersonalId(), startDate);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 終了日のカレンダチェック
		regist.checkSchedule(vo.getPersonalId(), endDate);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		List<Date> list = TimeUtility.getDateList(startDate, endDate);
		for (Date date : list) {
			// DTOの準備
			WorkTypeChangeRequestDtoInterface dto = reference.findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			if (!date.equals(startDate)) {
				dto = regist.getInitDto();
			}
			// DTOに値を設定
			setDtoFields(dto, date);
			if (!date.equals(startDate)) {
				dto.setTmdWorkTypeChangeRequestId(0);
			}
			// 妥当性チェック
			regist.validate(dto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// 勤務形態コード取得
			String workTypeCode = regist.getScheduledWorkTypeCode(vo.getPersonalId(), date);
			if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
					|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
				// 法定休日・所定休日の場合
				continue;
			}
			// 申請の相関チェック
			regist.checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto == null) {
				workflowDto = workflowRegist.getInitDto();
				workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE);
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
			// 表示期間設定
			setSearchRequestDate(requestDate);
			// 検索
			search();
		}
	}
	
	/**
	 * 取下処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void withdrawn() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// beanの準備
		WorkTypeChangeRequestRegistBeanInterface regist = time().workTypeChangeRequestRegist();
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		// DTOの準備
		WorkTypeChangeRequestDtoInterface dto = timeReference().workTypeChangeRequest().findForKey(vo.getRecordId());
		// 存在確認
		checkSelectedDataExist(dto);
		if (workflowIntegrate.isDraft(dto.getWorkflow())) {
			// 下書の場合
			delete(dto);
			return;
		}
		// 取下の相関チェック
		regist.checkWithdrawn(dto);
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
		// 存在確認
		checkSelectedDataExist(workflowDto);
		// ワークフロー登録
		workflowDto = platform().workflowRegist().withdrawn(workflowDto);
		if (workflowDto != null) {
			// ワークフローコメント登録
			platform().workflowCommentRegist().addComment(workflowDto, mospParams.getUser().getPersonalId(),
					PfMessageUtility.getWithdrawSucceed(mospParams));
		}
		// 取下結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 取下成功メッセージ設定
		addTakeDownMessage();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 表示期間設定
		setSearchRequestDate(getEditRequestDate());
		// 検索
		search();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外処理発生時
	 */
	protected void delete(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 登録クラス取得
		WorkTypeChangeRequestRegistBeanInterface regist = time().workTypeChangeRequestRegist();
		// 削除の相関チェック
		regist.checkWithdrawn(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		// 存在確認
		checkSelectedDataExist(workflowDto);
		// 削除する
		platform().workflowRegist().delete(workflowDto);
		platform().workflowCommentRegist()
			.deleteList(reference().workflowComment().getWorkflowCommentList(workflowDto.getWorkflow()));
		regist.delete(dto);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteSucceed(mospParams);
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 表示期間設定
		setSearchRequestDate(getEditRequestDate());
		// 検索
		search();
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、連続実行コマンドを設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void transfer() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow()[getTransferredIndex()]);
		// 承認履歴画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(ApprovalHistoryAction.CMD_WORK_TYPE_CHANGE_APPROVAL_HISTORY_SELECT_SHOW);
	}
	
	/**
	 * 有効日(検索)設定処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setViewPeriod() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (PlatformConst.MODE_ACTIVATE_DATE_CHANGING.equals(vo.getModeSearchActivateDate())) {
			// 有効日モード設定
			vo.setModeSearchActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeSearchActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		setSearchWorkTypePulldown();
	}
	
	/**
	 * 一括取下処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void batchWithdrawn() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		time().workTypeChangeRequestRegist().withdrawn(getIdArray(vo.getCkbSelect()));
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
		// 検索プルダウン再設定
		vo.setPltSearchRequestYear(searchYear);
		vo.setPltSearchRequestMonth(searchMonth);
		// 検索
		search();
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		Date date = getSystemDate();
		if (getTargetDate() != null) {
			date = getTargetDate();
		}
		vo.setPltEditRequestYear(DateUtility.getStringYear(date));
		vo.setPltEditRequestMonth(DateUtility.getStringMonthM(date));
		vo.setPltEditRequestDay(DateUtility.getStringDayD(date));
		vo.setCkbEndDate(MospConst.CHECKBOX_OFF);
		vo.setTxtEditRequestReason("");
		vo.setRecordId(0);
		vo.setPltSearchState("");
		vo.setPltSearchWorkType("");
		// 承認者欄の初期化
		vo.setAryPltLblApproverSetting(new String[0]);
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
	}
	
	/**
	 * 期間終了日を設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setEditEndDate() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// 期間指定でない場合
		vo.setPltEditEndYear(vo.getPltEditRequestYear());
		vo.setPltEditEndMonth(vo.getPltEditRequestMonth());
		vo.setPltEditEndDay(vo.getPltEditRequestDay());
	}
	
	/**
	 * 表示期間を設定する。<br>
	 * 初期表示は申請期間開始日を設定する。
	 * @param targetDate 対象日
	 * @throws MospException 例外処理発生時
	 */
	protected void setSearchRequestDate(Date targetDate) throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// 申請年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(vo.getPersonalId(), targetDate);
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
	}
	
	/**
	 * 変更前勤務形態を設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setLblWorkTypeName() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		vo.setLblWorkTypeName("");
		if (PlatformConst.MODE_ACTIVATE_DATE_FIXED.equals(vo.getModeActivateDate())) {
			// 決定の場合
			vo.setLblWorkTypeName(time().workTypeChangeRequestRegist().getScheduledWorkTypeName(vo.getPersonalId(),
					getEditRequestDate()));
		}
	}
	
	/**
	 * プルダウン設定。<br>
	 */
	protected void setPulldown() {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		int year = DateUtility.getYear(date);
		vo.setAryPltEditRequestYear(getYearArray(year));
		vo.setAryPltEditRequestMonth(getMonthArray());
		vo.setAryPltEditRequestDay(getDayArray());
		vo.setAryPltEditEndYear(getYearArray(year));
		vo.setAryPltEditEndMonth(getMonthArray());
		vo.setAryPltEditEndDay(getDayArray());
		vo.setAryPltSearchRequestYear(getYearArray(year));
		vo.setAryPltSearchRequestMonth(getMonthArray(true));
	}
	
	/**
	 * 勤務形態プルダウン設定。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setEditWorkTypePulldown() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		ApplicationReferenceBeanInterface application = timeReference().application();
		ScheduleReferenceBeanInterface schedule = timeReference().schedule();
		vo.setAryPltEditWorkType(new String[0][0]);
		if (PlatformConst.MODE_ACTIVATE_DATE_FIXED.equals(vo.getModeActivateDate())) {
			// 決定の場合
			Date requestDate = getEditRequestDate();
			ApplicationDtoInterface applicationDto = application.findForPerson(vo.getPersonalId(), requestDate);
			application.chkExistApplication(applicationDto, requestDate);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			ScheduleDtoInterface scheduleDto = schedule.getScheduleInfo(applicationDto.getScheduleCode(), requestDate);
			schedule.chkExistSchedule(scheduleDto, requestDate);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			String[][] aryWorkType = getWorkTypeArray(scheduleDto.getPatternCode(), requestDate, false, true, false,
					false);
			if (aryWorkType == null) {
				vo.setAryPltEditWorkType(new String[0][0]);
				return;
			}
			vo.setAryPltEditWorkType(aryWorkType);
		}
		// 追加業務ロジック処理がある場合
		doAdditionalLogic(TimeConst.CODE_KEY_CHANGE_WORK_TYPE_PULLDOWN);
	}
	
	/**
	 * 検索勤務形態プルダウン設定。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setSearchWorkTypePulldown() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		vo.setAryPltSearchWorkType(new String[0][0]);
		if (PlatformConst.MODE_ACTIVATE_DATE_FIXED.equals(vo.getModeSearchActivateDate())) {
			// 決定の場合
			vo.setAryPltSearchWorkType(timeReference().workType().getTimeSelectArray(getSearchRequestDate()));
			// 追加業務ロジック処理がある場合
			doAdditionalLogic(TimeConst.CODE_KEY_CHANGE_WORK_TYPE_PULLDOWN);
		}
	}
	
	/**
	 * 出勤日を取得する。<br>
	 * @return 出勤日
	 * @throws MospException 例外処理発生時
	 */
	protected Date getEditRequestDate() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		return DateUtility.getDate(vo.getPltEditRequestYear(), vo.getPltEditRequestMonth(), vo.getPltEditRequestDay());
	}
	
	/**
	 * 期間終了日を取得する。<br>
	 * @return 期間終了日
	 * @throws MospException 例外処理発生時
	 */
	protected Date getEditEndDate() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		return DateUtility.getDate(vo.getPltEditEndYear(), vo.getPltEditEndMonth(), vo.getPltEditEndDay());
	}
	
	/**
	 * 検索日付を取得する。<br>
	 * @return 検索日付
	 * @throws MospException 例外処理発生時
	 */
	protected Date getSearchRequestDate() throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		int searchRequestMonth = 1;
		if (!vo.getPltSearchRequestMonth().isEmpty()) {
			searchRequestMonth = getInt(vo.getPltSearchRequestMonth());
		}
		return MonthUtility.getYearMonthTargetDate(getInt(vo.getPltSearchRequestYear()), searchRequestMonth,
				mospParams);
	}
	
	/**
	 * 期間指定かどうか確認する。<br>
	 * @return 期間指定の場合true、そうでない場合false
	 */
	protected boolean isCheckedEndDate() {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		return MospConst.CHECKBOX_ON.equals(vo.getCkbEndDate());
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外処理発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// beanの準備
		WorkTypeReferenceBeanInterface workType = timeReference().workType();
		// データ配列初期化
		String[] aryCkbWorkTypeChangeRequestId = new String[list.size()];
		String[] aryDate = new String[list.size()];
		String[] aryLblDateAndDay = new String[list.size()];
		String[] aryLblWorkType = new String[list.size()];
		String[] aryLblRequestReason = new String[list.size()];
		String[] aryLblState = new String[list.size()];
		String[] aryStateStyle = new String[list.size()];
		String[] aryLblApprover = new String[list.size()];
		String[] aryOnOff = new String[list.size()];
		String[] aryWorkflowStatus = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			WorkTypeChangeRequestListDtoInterface dto = (WorkTypeChangeRequestListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbWorkTypeChangeRequestId[i] = Long.toString(dto.getTmdWorkTypeChangeRequestId());
			aryDate[i] = getStringDate(dto.getRequestDate());
			aryLblDateAndDay[i] = getStringDateAndDay(dto.getRequestDate());
			aryLblWorkType[i] = workType.getWorkTypeAbbrAndTime(dto.getWorkTypeCode(), dto.getRequestDate());
			aryLblRequestReason[i] = dto.getRequestReason();
			aryLblState[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryStateStyle[i] = getStatusColor(dto.getState());
			aryLblApprover[i] = dto.getApproverName();
			aryOnOff[i] = getButtonOnOff(dto.getState(), dto.getStage());
			aryWorkflowStatus[i] = dto.getState();
			aryWorkflow[i] = dto.getWorkflow();
		}
		// データをVOに設定
		vo.setAryCkbWorkTypeChangeRequestId(aryCkbWorkTypeChangeRequestId);
		vo.setAryDate(aryDate);
		vo.setAryLblDateAndDay(aryLblDateAndDay);
		vo.setAryLblWorkType(aryLblWorkType);
		vo.setAryLblRequestReason(aryLblRequestReason);
		vo.setAryLblState(aryLblState);
		vo.setAryStateStyle(aryStateStyle);
		vo.setAryLblApprover(aryLblApprover);
		vo.setAryOnOff(aryOnOff);
		vo.setAryWorkflowStatus(aryWorkflowStatus);
		vo.setAryWorkflow(aryWorkflow);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外処理発生時
	 */
	protected void setVoFields(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		String year = Integer.toString(DateUtility.getYear(dto.getRequestDate()));
		String month = DateUtility.getStringMonthM(dto.getRequestDate());
		String day = DateUtility.getStringDayD(dto.getRequestDate());
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmdWorkTypeChangeRequestId());
		vo.setPltEditRequestYear(year);
		vo.setPltEditRequestMonth(month);
		vo.setPltEditRequestDay(day);
		vo.setPltEditEndYear(year);
		vo.setPltEditEndMonth(month);
		vo.setPltEditEndDay(day);
		vo.setPltEditWorkType(dto.getWorkTypeCode());
		vo.setTxtEditRequestReason(dto.getRequestReason());
		vo.setCkbEndDate(MospConst.CHECKBOX_OFF);
		vo.setModeCardEdit(getApplicationMode(dto.getWorkflow()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param requestDate 出勤日
	 */
	protected void setDtoFields(WorkTypeChangeRequestDtoInterface dto, Date requestDate) {
		// VO取得
		WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmdWorkTypeChangeRequestId(vo.getRecordId());
		dto.setPersonalId(vo.getPersonalId());
		dto.setRequestDate(requestDate);
		dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
		dto.setWorkTypeCode(vo.getPltEditWorkType());
		dto.setRequestReason(vo.getTxtEditRequestReason());
	}
	
	/**
	 * 期間のチェック。<br>
	 * @param startDate 開始日
	 * @param endDate 終了日
	 */
	protected void checkPeriod(Date startDate, Date endDate) {
		// 開始日が終了日より後の場合
		if (startDate.after(endDate)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorInputValueInvalid(mospParams, PfNameUtility.term(mospParams));
		}
	}
	
}
