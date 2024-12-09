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
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.comparator.settings.WorkOnHolidayRequestRequestDateComparator;
import jp.mosp.time.comparator.settings.WorkOnHolidayRequestSubstituteDate1Comparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestListDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.input.vo.WorkOnHolidayRequestVo;

/**
 * 振出・休出申請画面。<br>
 * 振替出勤、休日出勤申請情報の登録、確認、編集を行う。<br>
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
 * {@link #CMD_SET_TRANSFER_DAY}
 * </li></ul>
 */
public class WorkOnHolidayRequestAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの休日出勤申請画面を表示する。<br>
	 */
	public static final String		CMD_SHOW				= "TM1600";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 休日出勤申請画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW			= "TM1601";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に休日出勤申請情報の検索を行う。<br>
	 */
	public static final String		CMD_SEARCH				= "TM1602";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たな下書情報作成や申請を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String		CMD_RE_SHOW				= "TM1603";
	
	/**
	 * 下書コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を休日出勤情報テーブルに登録し、下書情報として保存する。<br>
	 */
	public static final String		CMD_DRAFT				= "TM1604";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を休日出勤情報テーブルに登録し、休日出勤申請を行う。以降、このレコードは上長が差戻をしない限り編集不可となる。<br>
	 * 休日出勤の申請可能時間を超過している、申請年月日で申請不可な日付が選択されている、<br>
	 * 申請時間が0時間0分のまま、休日出勤理由の項目が入力されていない、<br>
	 * といった状態で申請を行おうとした場合はエラーメッセージにて通知し、申請は実行されない。<br>
	 */
	public static final String		CMD_APPLI				= "TM1605";
	
	/**
	 * 取下コマンド。<br>
	 * <br>
	 * 下書状態または差戻状態で登録されていたレコードの取下を行う。<br>
	 * 取下後、対象の休日出勤申請情報は未申請状態へ戻る。<br>
	 */
	public static final String		CMD_WITHDRAWN			= "TM1607";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String		CMD_SORT				= "TM1608";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String		CMD_PAGE				= "TM1609";
	
	/**
	 * 一括取下コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている未承認状態のレコードの取下処理を繰り返し行う。
	 * ひとつもチェックが入っていない状態で一括取下ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。
	 */
	public static final String		CMD_BATCH_WITHDRAWN		= "TM1636";
	
	/**
	 * 出勤日決定コマンド。<br>
	 * <br>
	 * 入力された出勤日と振替申請のプルダウンの内容を基に処理を行う。<br>
	 * 振替休暇申請のプルダウンで"非申請"を選択した場合は勤務予定時刻、振替日1と振替日2のそれぞれの日付と休暇の長さのプルダウンが読取専用となる。<br>
	 * "申請"を選択、出勤日が法定休日である場合は振替日1の日付プルダウンのみ編集可能で残りの項目は読取専用となる。<br>
	 * "申請"を選択、出勤日が所定休日である場合は"非申請"を選択した時に読取専用だった全ての項目が編集可能となる。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE	= "TM1690";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 申請テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 申請テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE			= "TM1691";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を申請テーブルの各入力欄にそれぞれ表示させる。申請テーブルヘッダに新規登録モード切替リンクを表示させる。
	 * に表示されているレコード内容をクリアにする。<br>
	 * 申請テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_EDIT_MODE			= "TM1692";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている下書状態のレコードの申請処理を繰り返し行う。<br>
	 * ひとつもチェックが入っていない状態で一括更新ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。<br>
	 */
	public static final String		CMD_BATCH_UPDATE		= "TM1695";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String		CMD_TRANSFER			= "TM1696";
	
	/**
	 * 。<br>
	 * <br>
	 */
	public static final String		CMD_SET_TRANSFER_DAY	= "TM1698";
	
	/**
	 * 機能コード。<br>
	 */
	protected static final String	CODE_FUNCTION			= TimeConst.CODE_FUNCTION_WORK_HOLIDAY;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public WorkOnHolidayRequestAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new WorkOnHolidayRequestVo();
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
			// 出勤日決定コマンド
			prepareVo();
			setActivationDate();
			getWorkPlanFlag();
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
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_SET_TRANSFER_DAY)) {
			prepareVo();
			transferDay();
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
		// 申請種別の確認
		getWorkPlanFlag();
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
		// 申請種別の確認
		getWorkPlanFlag();
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditRequestDate(), CODE_FUNCTION);
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setPulldown() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getTargetDate();
		if (date == null) {
			date = getSystemDate();
		}
		// 検索条件のプルダウン設定
		vo.setAryPltSearchSubstituteRange(
				mospParams.getProperties().getCodeArray(TimeConst.CODE_SUBSTITUTE1_RANGE, true));
		vo.setAryPltSearchState(mospParams.getProperties().getCodeArray(TimeConst.CODE_APPROVAL_STATE, true));
		vo.setAryPltSearchRequestYear(getYearArray(DateUtility.getYear(date)));
		vo.setAryPltSearchRequestMonth(getMonthArray(true));
		vo.setAryPltEditSubstitute1Range(
				mospParams.getProperties().getCodeArray(TimeConst.CODE_SUBSTITUTE1_RANGE, false));
		// 勤務予定時刻
		vo.setAryPltEditStartHour(getHourArray());
		// 分は15分単位
		vo.setAryPltEditStartMinute(getMinuteArray(15));
		vo.setAryPltEditEndHour(getHourArray(48, true));
		// 分は15分単位
		vo.setAryPltEditEndMinute(getMinuteArray(15));
		// 出勤日のプルダウンを設定
		setEditRequestPullDown(date);
		// 振替日のプルダウンを設定
		setEditSubstitutePullDown(date);
	}
	
	/**
	 * 勤務終了予定時刻プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setEndHourPulldown() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		ApplicationReferenceBeanInterface application = timeReference().application();
		TimeSettingReferenceBeanInterface timeSetting = timeReference().timeSetting();
		vo.setAryPltEditEndHour(getHourArray(48, true));
		Date requestDate = getEditRequestDate();
		if (requestDate == null) {
			return;
		}
		// 適用されている設定を取得
		ApplicationDtoInterface applicationDto = application.findForPerson(vo.getPersonalId(), requestDate);
		// 設定適用マスタの存在
		application.chkExistApplication(applicationDto, requestDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠マスタ取得
		TimeSettingDtoInterface timeSettingDto = timeSetting.getTimeSettingInfo(applicationDto.getWorkSettingCode(),
				requestDate);
		// 勤怠マスタの存在チェック
		timeSetting.chkExistTimeSetting(timeSettingDto, requestDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		vo.setAryPltEditEndHour(getHourArray(
				DateUtility.getHour(timeSettingDto.getStartDayTime()) + TimeConst.TIME_DAY_ALL_HOUR, true));
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 検索クラス取得
		WorkOnHolidayRequestSearchBeanInterface search = timeReference().workOnHolidayRequestSearch();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// VOの値を検索クラスへ設定
		search.setPersonalId(personalId);
		search.setSubstitute(vo.getPltSearchSubstitute());
		search.setSubstituteRange("");
		search.setWorkflowStatus(vo.getPltSearchState());
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
		List<WorkOnHolidayRequestListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(WorkOnHolidayRequestRequestDateComparator.class.getName());
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
	 * @throws MospException 例外発生時
	 */
	protected void draft() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 利用可否チェック
		isAvailable(getEditRequestDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 登録クラス取得
		WorkOnHolidayRequestRegistBeanInterface regist = time().workOnHolidayRequestRegist();
		SubstituteRegistBeanInterface substituteRegist = time().substituteRegist();
		// DTOの準備
		WorkOnHolidayRequestDtoInterface dto = timeReference().workOnHolidayRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = regist.getInitDto();
		}
		// DTOに値を設定
		setWorkOnHolidayDtoFields(dto);
		// 妥当性チェック
		regist.validate(dto);
		regist.checkDraft(dto);
		boolean substituteFlag = Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON)
			.equals(vo.getPltEditSubstitute())
				|| Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE)
					.equals(vo.getPltEditSubstitute());
		// 振替休日DTOの準備
		SubstituteDtoInterface substituteDto = null;
		if (substituteFlag) {
			// 振り替える場合
			substituteDto = substituteRegist.getInitDto();
			// 振替休日DTOに値を設定
			setSubstituteDtoFields(substituteDto);
			substituteDto.setWorkflow(dto.getWorkflow());
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// 振替休日1の妥当性チェック
			substituteRegist.validate(substituteDto);
			substituteRegist.checkRegist(substituteDto);
		}
		// 登録クラスの取得。
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
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
			if (substituteFlag) {
				// 振り替える場合
				substituteDto.setWorkflow(workflowDto.getWorkflow());
			}
			// 登録
			regist.regist(dto);
			substituteRegist.delete(workflowDto.getWorkflow());
			if (substituteFlag) {
				// 振り替える場合
				substituteRegist.insert(substituteDto);
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
		// 申請年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getRequestDate());
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
		// 検索
		search();
		// 履歴編集対象を取得
		setEditUpdateMode(dto.getRequestDate());
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_DRAFT);
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 利用可否チェック
		isAvailable(getEditRequestDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 登録クラス取得
		WorkOnHolidayRequestRegistBeanInterface regist = time().workOnHolidayRequestRegist();
		SubstituteRegistBeanInterface substituteRegist = time().substituteRegist();
		// DTOの準備
		WorkOnHolidayRequestDtoInterface dto = timeReference().workOnHolidayRequest().findForKey(vo.getRecordId());
		if (dto == null) {
			dto = regist.getInitDto();
		}
		// 振替休日DTOの準備
		SubstituteDtoInterface substituteDto = null;
		if (Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON).equals(vo.getPltEditSubstitute())
				|| Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE)
					.equals(vo.getPltEditSubstitute())) {
			// 振り替える場合
			substituteDto = substituteRegist.getInitDto();
		}
		// 申請処理
		appli(dto, substituteDto);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// コミット
		commit();
		// 申請成功メッセージ設定
		addAppliMessage();
		// 半振出の場合
		if (vo.isModeHalfSubstitute()) {
			// 振替日の勤怠が削除されるメッセージ追加
			mospParams.addMessage(TimeMessageConst.MSG_HALF_SUBHOLIDAY_REQUEST);
		}
		// 登録結果確認
		if (!mospParams.hasErrorMessage()) {
			// 登録が成功した場合、初期状態に戻す。
			insertMode();
			// 申請年月日が含まれる締月を取得し検索条件に設定
			Date searchDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getRequestDate());
			vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
			vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
			search();
		}
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @param dto 振出・休出DTO
	 * @param substituteDto 振替休日DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli(WorkOnHolidayRequestDtoInterface dto, SubstituteDtoInterface substituteDto)
			throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		WorkOnHolidayRequestRegistBeanInterface regist = time().workOnHolidayRequestRegist();
		SubstituteRegistBeanInterface substituteRegist = time().substituteRegist();
		
		// DTOに値を設定
		setWorkOnHolidayDtoFields(dto);
		// 申請の相関チェック
		regist.checkAppli(dto);
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 振出・休出フラグ
		boolean substituteFlag = Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON)
			.equals(vo.getPltEditSubstitute())
				|| Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE)
					.equals(vo.getPltEditSubstitute());
		if (substituteFlag) {
			// 振替休日DTOに値を設定
			setSubstituteDtoFields(substituteDto);
			substituteDto.setWorkflow(dto.getWorkflow());
			// 振替休日の相関チェック
			substituteRegist.checkRegist(substituteDto);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// 振替日の勤怠申請情報を削除する。
			substituteAttendanceDelete(substituteDto);
		}
		// 登録クラスの取得
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		// ワークフローの設定
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
		}
		// 承認者個人ID
		workflowRegist.setDtoApproverIds(workflowDto, getSelectApproverIds());
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
				PlatformConst.WORKFLOW_TYPE_TIME, null);
		if (workflowDto != null) {
			// ワークフロー番号セット
			dto.setWorkflow(workflowDto.getWorkflow());
			if (substituteFlag) {
				// 振り替える場合
				substituteDto.setWorkflow(workflowDto.getWorkflow());
			}
			// 登録
			regist.regist(dto);
			substituteRegist.delete(workflowDto.getWorkflow());
			// 振替休日登録
			registSubstitute(substituteFlag, substituteDto);
		}
	}
	
	/**
	 * 振替休日登録
	 * @param substituteFlag 振替出勤判定：true:振替あり、false:振替無し
	 * @param substituteDto 振替休日DTO
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void registSubstitute(boolean substituteFlag, SubstituteDtoInterface substituteDto) throws MospException {
		AttendanceTransactionRegistBeanInterface attendanceTransactionRegist = time().attendanceTransactionRegist();
		SubstituteRegistBeanInterface substituteRegist = time().substituteRegist();
		if (substituteFlag) {
			// 振り替える場合
			substituteRegist.insert(substituteDto);
			// 勤怠トランザクション登録
			attendanceTransactionRegist.regist(substituteDto);
		}
		
	}
	
	/**
	 * 振替日の勤怠申請情報を削除する。<br>
	 * @param substituteDto 振替休日DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void substituteAttendanceDelete(SubstituteDtoInterface substituteDto) throws MospException {
		// 振替日に勤怠申請情報(下書)を削除
		time().attendanceRegist().delete(substituteDto.getPersonalId(), substituteDto.getSubstituteDate());
	}
	
	/**
	* 取下処理を行う。<br>
	* @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	*/
	protected void withdrawn() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// beanの準備
		WorkOnHolidayRequestRegistBeanInterface regist = time().workOnHolidayRequestRegist();
		// DTOの準備
		WorkOnHolidayRequestDtoInterface dto = timeReference().workOnHolidayRequest().findForKey(vo.getRecordId());
		// 存在確認(エラー時はポータルへ遷移)
		checkSelectedDataExist(dto);
		// 取下の相関チェック
		regist.checkWithdrawn(dto);
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		// 存在確認(エラー時はポータルへ遷移)
		checkSelectedDataExist(workflowDto);
		// 状態判定
		boolean isDraft = PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus());
		// 取下実行
		if (isDraft) {
			// 下書きの場合、削除
			delete(dto, workflowDto);
		} else {
			// 取下げ処理
			withdrawn(dto, workflowDto);
		}
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// メッセージ設定
		if (isDraft) {
			// 削除成功メッセージを設定
			PfMessageUtility.addMessageDeleteSucceed(mospParams);
		} else {
			// 取下成功メッセージ設定
			addTakeDownMessage();
		}
		// コミット
		commit();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 申請年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(dto.getPersonalId(), dto.getRequestDate());
		vo.setPltSearchRequestYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchRequestMonth(DateUtility.getStringMonthM(searchDate));
		
		// 検索
		search();
	}
	
	/**
	 * 下書時の削除処理
	 * @param dto 振出休出申請DTO
	 * @param workflowDto ワークフローDTO
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void delete(WorkOnHolidayRequestDtoInterface dto, WorkflowDtoInterface workflowDto) throws MospException {
		WorkOnHolidayRequestRegistBeanInterface regist = time().workOnHolidayRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		WorkflowCommentRegistBeanInterface workflowCommentRegist = platform().workflowCommentRegist();
		long workflow = workflowDto.getWorkflow();
		// 下書の場合は削除する
		workflowRegist.delete(workflowDto);
		workflowCommentRegist.deleteList(reference().workflowComment().getWorkflowCommentList(workflow));
		regist.delete(dto);
		time().substituteRegist().delete(workflow);
		
	}
	
	/**
	 * 取下処理
	 * @param dto 振出休出申請DTO
	 * @param workflowDto ワークフローDTO
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void withdrawn(WorkOnHolidayRequestDtoInterface dto, WorkflowDtoInterface workflowDto)
			throws MospException {
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		WorkflowCommentRegistBeanInterface workflowCommentRegist = platform().workflowCommentRegist();
		// 下書でない場合は取下する
		// ワークフロー登録
		workflowDto = workflowRegist.withdrawn(workflowDto);
		if (workflowDto != null) {
			// ワークフローコメント登録
			workflowCommentRegist.addComment(workflowDto, mospParams.getUser().getPersonalId(),
					PfMessageUtility.getWithdrawSucceed(mospParams));
		}
		// 取下時の勤怠データ削除
		time().attendanceRegist().delete(dto.getPersonalId(), dto.getRequestDate());
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
	 * 一括取下処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void batchWithdrawn() throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括取下
		batchWithdrawn(getIdArray(vo.getCkbSelect()));
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
	 * 一括取下処理
	 * @param aryId 識別ID配列
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void batchWithdrawn(long[] aryId) throws MospException {
		// 一括更新処理
		time().workOnHolidayRequestRegist().withdrawn(aryId);
		
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 休日出勤申請登録クラスを取得
		WorkOnHolidayRequestRegistBeanInterface regist = time().workOnHolidayRequestRegist();
		// 現在の有効日モードを確認
		// 変更モードの場合
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 利用不可である場合
			if (isAvailable(getEditRequestDate(), CODE_FUNCTION) == false) {
				// 処理終了
				return;
			}
			// 承認者用プルダウンの作成
			if (setApproverPullDown(vo.getPersonalId(), getEditRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME) == false) {
				return;
			}
			// 休日出勤申請取得
			WorkOnHolidayRequestDtoInterface dto = timeReference().workOnHolidayRequest().findForKey(vo.getRecordId());
			if (dto == null) {
				// 登録用DTOを取得
				dto = regist.getInitDto();
			}
			// DTOに値をつめる
			dto.setPersonalId(vo.getPersonalId());
			dto.setRequestDate(getEditRequestDate());
			dto.setWorkOnHolidayType(getScheduledWorkTypeCode());
			// // 振替出勤の場合
			if (Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON).equals(vo.getPltEditSubstitute())) {
				// 振替申請区分取得
				String editWorkRange = vo.getPltEditSubstituteWorkRange();
				// 午前又は午後の場合
				if (editWorkRange != null && !editWorkRange.isEmpty()
						&& (Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM).equals(editWorkRange)
								|| Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM)
									.equals(editWorkRange))) {
					// 振替申請区分設定
					dto.setSubstitute(getInt(editWorkRange));
				}
			} else if (Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF)
				.equals(vo.getPltEditSubstitute())) {
				// 休日出勤の場合
				vo.setPltEditSubstituteWorkRange("1");
			}
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 出勤日設定時のチェック
			regist.checkSetRequestDate(dto);
			if (mospParams.hasErrorMessage()) {
				// 決定失敗メッセージを設定
				PfMessageUtility.addMessageDecisiontFailed(mospParams);
				return;
			}
			// 勤務形態プルダウン設定
			setWorkTypePulldown();
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			transferDay();
		} else {
			// 変更モード以外
			String[] aryPltLblApproverSetting = new String[0];
			vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 勤務終了予定時間プルダウン設定
		setEndHourPulldown();
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 半日振替出勤利用設定
		vo.setModeHalfSubstitute(timeReference().workOnHolidayRequest().useHalfSubstitute());
		// 初期値設定
		setDefaultValues();
		// 編集モード設定
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 勤務予定の表示
		vo.setJsModeWorkPlanFlag(TimeConst.MODE_WORK_PLAN_APPLICATION_OFF);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(WorkOnHolidayRequestSubstituteDate1Comparator.class.getName());
		// プルダウン設定
		setPulldown();
		// 勤務形態プルダウン設定
		setWorkTypePulldown();
		// 基本情報チェック
		if (null != getEditRequestDate()) {
			timeReference().workOnHolidayRequest().chkBasicInfo(vo.getPersonalId(), getEditRequestDate());
		}
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
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
			// 申請種別の確認
			getWorkPlanFlag();
			// 検索
			search();
		}
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getDate(getTransferredActivateDate()));
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		setActivationDate();
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		update();
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
		// VOを準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
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
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 一括更新処理
		// ワークフローの更新のみ
		time().workOnHolidayRequestRegist().update(getIdArray(vo.getCkbSelect()));
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
		// 承認履歴画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(ApprovalHistoryAction.CMD_APPROVAL_HISTORY_HOLIDAY_WORK_SELECT_SHOW);
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void transferDay() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 同日付に時差出勤が申請されているか確認する。
		getDifferenceRequest1(vo.getPersonalId(), getDate(vo.getPltEditSubstitute1Year(),
				vo.getPltEditSubstitute1Month(), vo.getPltEditSubstitute1Day()));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 申請日で編集対象情報を取得する。<br>
	 * @param requestDate 申請日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(Date requestDate) throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 履歴編集対象取得
		WorkOnHolidayRequestDtoInterface dto = timeReference().workOnHolidayRequest()
			.findForKeyOnWorkflow(vo.getPersonalId(), requestDate);
		// dto存在確認
		checkSelectedDataExist(dto);
		// 振替休日データリストを取得
		List<SubstituteDtoInterface> list = timeReference().substitute().getSubstituteList(dto.getWorkflow());
		// list存在確認
		checkSelectedDataExist(list);
		// VOにセット
		setVoFields(dto);
		setVoFields(list);
		// 出勤日のプルダウンを設定
		setEditRequestPullDown(dto.getRequestDate());
		// 振替日のプルダウンを設定
		setEditSubstitutePullDown(dto.getRequestDate());
		// 出勤日のための再取得
		setVoFields(dto);
		setVoFields(list);
		getWorkPlanFlag();
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 検索項目設定
		vo.setRecordId(0);
		vo.setPltEditRequestYear("");
		vo.setPltEditRequestMonth("");
		vo.setPltEditRequestDay("");
		vo.setPltEditSubstitute("");
		vo.setPltEditStartHour("");
		vo.setPltEditStartMinute("");
		vo.setPltEditEndHour("");
		vo.setPltEditEndMinute("");
		vo.setPltEditSubstitute1Year("");
		vo.setPltEditSubstitute1Month("");
		vo.setPltEditSubstitute1Day("");
		vo.setPltEditSubstitute1Range(Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON));
		vo.setTxtEditRequestReason("");
		vo.setPltEditStartHour("0");
		vo.setPltEditStartMinute("0");
		vo.setPltEditEndHour("0");
		vo.setPltEditEndMinute("0");
		vo.setJsModeLegalHoliday("off");
		vo.setPltSearchSubstitute("");
		vo.setPltSearchSubstituteRange("");
		vo.setPltSearchState("");
		// 申請年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(vo.getPersonalId(), date);
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
	}
	
	/**
	 * 表示期間を設定する。<br>
	 * @param year 年
	 * @param month 月
	 */
	protected void setSearchRequestDate(String year, String month) {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		vo.setPltSearchRequestYear(year);
		vo.setPltSearchRequestMonth(month);
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblRequestDate = new String[list.size()];
		String[] aryLblRequestTime = new String[list.size()];
		String[] aryLblRequestReason = new String[list.size()];
		String[] aryLblSubstituteDate = new String[list.size()];
		String[] aryLblWorkflowStatus = new String[list.size()];
		String[] aryStatusStyle = new String[list.size()];
		String[] aryLblApproverName = new String[list.size()];
		String[] aryLblOnOff = new String[list.size()];
		String[] aryWorkflowStatus = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			WorkOnHolidayRequestListDtoInterface dto = (WorkOnHolidayRequestListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getTmdWorkOnHolidayRequestId());
			aryLblRequestDate[i] = getStringDateAndDay(dto.getRequestDate());
			aryLblRequestTime[i] = getScheduleForList(dto);
			aryLblRequestReason[i] = dto.getRequestReason();
			StringBuffer substitute = new StringBuffer();
			substitute.append(getStringDateAndDay(dto.getSubstituteDate()));
			aryLblSubstituteDate[i] = substitute.toString() + MospConst.STR_SB_SPACE
					+ getRangeName(dto.getSubstituteRange());
			aryLblWorkflowStatus[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryStatusStyle[i] = getStatusColor(dto.getState());
			aryLblOnOff[i] = getButtonOnOff(dto.getState(), dto.getStage());
			aryLblApproverName[i] = dto.getApproverName();
			aryWorkflowStatus[i] = dto.getState();
			aryWorkflow[i] = dto.getWorkflow();
		}
		// データをVOに設定
		vo.setAryCkbWorkOnHolidayRequestListId(aryCkbRecordId);
		vo.setAryLblWorkDate(aryLblRequestDate);
		vo.setAryLblRequestTime(aryLblRequestTime);
		vo.setAryLblRequestReason(aryLblRequestReason);
		vo.setAryLblSubstituteDate1(aryLblSubstituteDate);
		vo.setAryLblState(aryLblWorkflowStatus);
		vo.setAryStateStyle(aryStatusStyle);
		vo.setAryLblApprover(aryLblApproverName);
		vo.setAryLblOnOff(aryLblOnOff);
		vo.setAryWorkflowStatus(aryWorkflowStatus);
		vo.setAryWorkflow(aryWorkflow);
	}
	
	/**
	 * 予定を取得する。<br>
	 * @param dto 対象DTO
	 * @return 予定
	 * @throws MospException 例外発生時
	 */
	protected String getScheduleForList(WorkOnHolidayRequestListDtoInterface dto) throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// クラス準備
		ScheduleUtilBeanInterface scheduleUtil = timeReference().scheduleUtil();
		WorkTypeReferenceBeanInterface workType = timeReference().workType();
		SubstituteReferenceBeanInterface substitute = timeReference().substitute();
		// 休日出勤の場合
		if (dto.getRequestDate() != null && dto.getStartTime() != null && dto.getEndTime() != null) {
			// 時刻取得
			return getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestDate());
		}
		// 予定値準備
		String scheduleValue = "";
		// 振替出勤(勤務形態変更なし)・振替出勤(勤務形態変更あり)の場合
		String workTypeCode = dto.getWorkTypeCode();
		Date targetDate = dto.getRequestDate();
		// 振替出勤(勤務形態変更なし)の場合
		if (workTypeCode.isEmpty()) {
			// ワークフロー番号から振替休日データリストを取得
			List<SubstituteDtoInterface> list = substitute.getSubstituteList(dto.getWorkflow());
			if (list.isEmpty()) {
				return scheduleValue;
			}
			targetDate = list.get(0).getSubstituteDate();
			// 勤務形態コードを取得
			workTypeCode = scheduleUtil.getScheduledWorkTypeCodeNoMessage(vo.getPersonalId(), targetDate);
		}
		WorkTypeDtoInterface workTypeDto = workType.findForInfo(workTypeCode, targetDate);
		if (workTypeDto == null) {
			return scheduleValue;
		}
		// 予定値設定
		scheduleValue = workTypeDto.getWorkTypeAbbr();
		// 振替申請（区分）名称取得
		String range = getSubstituteName(dto.getSubstitute());
		// 勤務形態略称
		return scheduleValue + MospConst.STR_SB_SPACE + range;
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setWorkOnHolidayDtoFields(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 申請日取得
		Date requestDate = getEditRequestDate();
		// 勤務形態コード取得
		String workTypeCode = getScheduledWorkTypeCode();
		// 結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		//
		int substitute = Integer.parseInt(vo.getPltEditSubstitute());
		// VOの値をDTOに設定
		dto.setTmdWorkOnHolidayRequestId(vo.getRecordId());
		dto.setPersonalId(vo.getPersonalId());
		dto.setRequestDate(requestDate);
		dto.setTimesWork(1);
		dto.setWorkOnHolidayType(workTypeCode);
		dto.setRequestReason(vo.getTxtEditRequestReason());
		dto.setSubstitute(substitute);
		dto.setWorkTypeCode("");
		// 振替出勤(勤務形態変更あり)の場合
		if (Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE)
			.equals(vo.getPltEditSubstitute())) {
			// 勤務形態コード設定
			dto.setWorkTypeCode(vo.getPltEditWorkType());
		}
		dto.setStartTime(null);
		dto.setEndTime(null);
		// 振替出勤の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			if (vo.getPltEditSubstituteWorkRange() == null || vo.getPltEditSubstituteWorkRange().isEmpty()) {
				return;
			}
			// 午前又は午後の場合
			if (Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM)
				.equals(vo.getPltEditSubstituteWorkRange())
					|| Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM)
						.equals(vo.getPltEditSubstituteWorkRange())) {
				// 振替区分設定
				dto.setSubstitute(getInt(vo.getPltEditSubstituteWorkRange()));
			}
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤の場合
			int year = Integer.parseInt(vo.getPltEditRequestYear());
			int month = Integer.parseInt(vo.getPltEditRequestMonth());
			int day = Integer.parseInt(vo.getPltEditRequestDay());
			int startHour = Integer.parseInt(vo.getPltEditStartHour());
			int startMinute = Integer.parseInt(vo.getPltEditStartMinute());
			int endHour = Integer.parseInt(vo.getPltEditEndHour());
			int endMinute = Integer.parseInt(vo.getPltEditEndMinute());
			dto.setStartTime(DateUtility.getDateTime(year, month, day, startHour, startMinute));
			long endTime = requestDate.getTime();
			endTime += endHour * (long)DateUtility.TIME_HOUR_MILLI_SEC;
			endTime += endMinute * DateUtility.TIME_HOUR_MILLI_SEC / TimeConst.CODE_DEFINITION_HOUR;
			dto.setEndTime(new Date(endTime));
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setSubstituteDtoFields(SubstituteDtoInterface dto) throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		String workTypeCode = getScheduledWorkTypeCode();
		// 結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VOの値をDTOに設定
		dto.setPersonalId(vo.getPersonalId());
		dto.setSubstituteDate(DateUtility.getDate(vo.getPltEditSubstitute1Year(), vo.getPltEditSubstitute1Month(),
				vo.getPltEditSubstitute1Day()));
		dto.setSubstituteType(workTypeCode);
		dto.setSubstituteRange(1);
		if (getInt(vo.getPltEditSubstitute()) == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON && (Integer
			.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM).equals(vo.getPltEditSubstituteWorkRange())
				|| Integer.toString(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM)
					.equals(vo.getPltEditSubstituteWorkRange()))) {
			// 午前又は午後の場合
			dto.setSubstituteRange(getInt(vo.getPltEditSubstitute1Range()));
		}
		dto.setWorkDate(getEditRequestDate());
		dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
	}
	
	/**
	 * 出勤日を取得する。
	 * @return 出勤日
	 */
	protected Date getEditRequestDate() {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		return getDate(vo.getPltEditRequestYear(), vo.getPltEditRequestMonth(), vo.getPltEditRequestDay());
	}
	
	/**
	 * 出勤日のプルダウンを設定する。<br>
	 * @param date 日付
	 * @throws MospException 例外発生時
	 */
	protected void setEditRequestPullDown(Date date) throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 出勤日のプルダウンを設定
		int year = DateUtility.getYear(date);
		vo.setAryPltEditRequestYear(getYearArray(year));
		vo.setAryPltEditRequestMonth(getMonthArray());
		vo.setAryPltEditRequestDay(getDayArray());
		vo.setPltEditRequestYear(String.valueOf(year));
		vo.setPltEditRequestMonth(DateUtility.getStringMonthM(date));
		vo.setPltEditRequestDay(DateUtility.getStringDayD(date));
	}
	
	/**
	 * 振替日のプルダウンを設定する。<br>
	 * @param date 日付
	 * @throws MospException 例外発生時
	 */
	protected void setEditSubstitutePullDown(Date date) throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 振替日のプルダウンを設定
		int year = DateUtility.getYear(date);
		vo.setAryPltEditSubstitute1Year(getYearArray(year));
		vo.setAryPltEditSubstitute1Month(getMonthArray());
		vo.setAryPltEditSubstitute1Day(getDayArray());
		vo.setPltEditSubstitute1Year(String.valueOf(year));
		vo.setPltEditSubstitute1Month(DateUtility.getStringMonthM(date));
		vo.setPltEditSubstitute1Day(DateUtility.getStringDayD(date));
	}
	
	/**
	 * 勤務形態プルダウンを設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setWorkTypePulldown() throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		ApplicationReferenceBeanInterface application = timeReference().application();
		ScheduleReferenceBeanInterface schedule = timeReference().schedule();
		// 出勤日
		Date requestDate = getEditRequestDate();
		// 設定適用取得
		ApplicationDtoInterface applicationDto = application.findForPerson(vo.getPersonalId(), requestDate);
		if (applicationDto == null) {
			vo.setAryPltEditWorkType(new String[0][0]);
			return;
		}
		// カレンダマスタ取得
		ScheduleDtoInterface scheduleDto = schedule.getScheduleInfo(applicationDto.getScheduleCode(), requestDate);
		if (scheduleDto == null) {
			vo.setAryPltEditWorkType(new String[0][0]);
			return;
		}
		vo.setAryPltEditWorkType(getWorkTypeArray(scheduleDto.getPatternCode(), requestDate, true, true, false, false));
		// 追加業務ロジック処理がある場合
		doAdditionalLogic(TimeConst.CODE_KEY_CHANGE_WORK_TYPE_PULLDOWN);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setVoFields(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		int substitute = dto.getSubstitute();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmdWorkOnHolidayRequestId());
		vo.setPltEditRequestYear(DateUtility.getStringYear(dto.getRequestDate()));
		vo.setPltEditRequestMonth(String.valueOf(DateUtility.getMonth(dto.getRequestDate())));
		vo.setPltEditRequestDay(String.valueOf(DateUtility.getDay(dto.getRequestDate())));
		vo.setTxtEditRequestReason(dto.getRequestReason());
		vo.setPltEditSubstitute(String.valueOf(substitute));
		vo.setPltEditSubstituteWorkRange(String.valueOf(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON));
		vo.setPltEditWorkType("");
		if (!dto.getWorkTypeCode().isEmpty()) {
			vo.setPltEditWorkType(dto.getWorkTypeCode());
		}
		vo.setPltEditStartHour("");
		vo.setPltEditStartMinute("");
		vo.setPltEditEndHour("");
		vo.setPltEditEndMinute("");
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤の場合
			vo.setPltEditStartHour(String.valueOf(DateUtility.getHour(dto.getStartTime())));
			vo.setPltEditStartMinute(String.valueOf(DateUtility.getMinute(dto.getStartTime())));
			vo.setPltEditEndHour(String.valueOf(DateUtility.getHour(dto.getEndTime(), dto.getRequestDate())));
			vo.setPltEditEndMinute(String.valueOf(DateUtility.getMinute(dto.getEndTime())));
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
			// 振替出勤(午前)の場合
			vo.setPltEditSubstitute(String.valueOf(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON));
			vo.setPltEditSubstituteWorkRange(String.valueOf(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM));
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替出勤(午後)の場合
			vo.setPltEditSubstitute(String.valueOf(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON));
			vo.setPltEditSubstituteWorkRange(String.valueOf(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM));
		}
		vo.setModeCardEdit(getApplicationMode(dto.getWorkflow()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoFields(List<SubstituteDtoInterface> list) {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// DTOの値をVOに設定
		int i = 0;
		for (SubstituteDtoInterface dto : list) {
			Date substituteDate = dto.getSubstituteDate();
			int substituteRange = dto.getSubstituteRange();
			if (i == 0) {
				vo.setPltEditSubstitute1Year(DateUtility.getStringYear(substituteDate));
				vo.setPltEditSubstitute1Month(DateUtility.getStringMonthM(substituteDate));
				vo.setPltEditSubstitute1Day(DateUtility.getStringDayD(substituteDate));
				vo.setPltEditSubstitute1Range(String.valueOf(substituteRange));
			}
			i++;
		}
	}
	
	/**
	 * 勤務形態コードを取得する。<br>
	 * @return 勤務形態コード
	 * @throws MospException 例外発生時
	 */
	protected String getScheduledWorkTypeCode() throws MospException {
		return getScheduleWorkTypeCode(getEditRequestDate());
	}
	
	/**
	 * 出勤日から勤務形態コードを取得する。<br>
	 * @param workeDate 出勤日
	 * @return 勤務形態コード
	 * @throws MospException 例外発生時
	 */
	protected String getScheduleWorkTypeCode(Date workeDate) throws MospException {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 申請ユティリティクラス準備
		RequestUtilBeanInterface requestUtil = timeReference().requestUtil();
		requestUtil.setRequests(vo.getPersonalId(), workeDate);
		// 承認済振替休日データリストを取得
		List<SubstituteDtoInterface> list = requestUtil.getSubstituteList(true);
		// 振替休日データリスト毎に処理
		for (SubstituteDtoInterface substituteDto : list) {
			return substituteDto.getSubstituteType();
		}
		// カレンダに登録されている勤務形態コードを取得
		String workTypeCode = timeReference().scheduleUtil().getScheduledWorkTypeCode(vo.getPersonalId(), workeDate);
		// 取得に失敗した場合
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態コードを取得
		return workTypeCode;
	}
	
	/**
	 * 申請種別の表示設定を行う。<br>
	 */
	protected void getWorkPlanFlag() {
		// VO取得
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// VOの振替申請の状態チェック
		// 振替申請（全日）の場合
		if (vo.getPltEditSubstitute().equals(String.valueOf(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON))) {
			vo.setJsModeWorkPlanFlag(TimeConst.MODE_WORK_PLAN_APPLICATION_ON);
		} else if (vo.getPltEditSubstitute().equals(String.valueOf(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF))) {
			// 休日出勤申請の場合
			vo.setJsModeWorkPlanFlag(TimeConst.MODE_WORK_PLAN_APPLICATION_OFF);
		} else {
			vo.setJsModeWorkPlanFlag("");
		}
	}
	
	/**
	 * 振替範囲名称を取得する。
	 * "全日"/"午前"/"午後"
	 * @param code 振替範囲
	 * @return 振替範囲名称
	 */
	protected String getRangeName(int code) {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 半日振替でない場合
		if (!vo.isModeHalfSubstitute()) {
			return "";
		}
		// 全休の場合
		if (code == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			return getCodeName(code, TimeConst.CODE_SUBSTITUTE_WORK_RANGE);
		}
		// 午前・午後の場合
		if (code == TimeConst.CODE_HOLIDAY_RANGE_AM || code == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			return getCodeName(code, TimeConst.CODE_SUBSTITUTE_HOLIDAY_RANGE);
		}
		return "";
	}
	
	/**
	 * 振替申請（区分）名称を取得する。
	 * "全日"/"午前"/"午後"
	 * @param code 振替申請（区分）
	 * @return 振替申請（区分）名称
	 */
	protected String getSubstituteName(int code) {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 半日振替でない場合
		if (!vo.isModeHalfSubstitute()) {
			return "";
		}
		// 振替・午前・午後の場合
		if (code == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON || code == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				|| code == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			return getCodeName(code, TimeConst.CODE_SUBSTITUTE_WORK_RANGE);
		}
		// 勤務形態変更の場合
		if (code == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			return getCodeName(1, TimeConst.CODE_SUBSTITUTE_WORK_RANGE);
		}
		
		return "";
	}
	
	/**
	 * 利用可否チェックを行う。<br>
	 * @param targetDate 対象日
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void isAvailable(Date targetDate) throws MospException {
		// VO準備
		WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)mospParams.getVo();
		// 利用可否チェック
		isAvailable(TimeConst.CODE_KEY_CHECK_APPLY_AVAILABLE, vo.getPersonalId(), targetDate, CODE_FUNCTION,
				vo.getPltEditSubstitute(), vo.getPltEditSubstituteWorkRange());
	}
}
