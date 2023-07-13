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
package jp.mosp.time.calculation.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TotalTimeBaseAction;
import jp.mosp.time.bean.SubordinateSearchBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeSearchBeanInterface;
import jp.mosp.time.calculation.vo.TotalTimeListVo;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.dto.settings.SubordinateListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;
import jp.mosp.time.input.action.AttendanceHistoryAction;

/**
 * 勤怠集計実行後の結果の確認を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_TEMPORARY_TIGHTENING}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_CALC}
 * </li><li>
 * {@link #CMD_RELEASE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_OUTPUT_FORMS}
 * </li></ul>
 */
public class TotalTimeListAction extends TotalTimeBaseAction {
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 勤怠集計管理画面で選択した締日レコードの勤怠集計結果一覧を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW				= "TM3121";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った社員情報の一覧表示を行う。<br>
	 * 一覧表示の際には社員コードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH					= "TM3122";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から改めて遷移した場合、承認状況の更新を反映し、
	 * 直前の検索条件（申請項目）で検索を行って画面表示をする。<br>
	 */
	public static final String	CMD_RE_SHOW					= "TM3123";
	
	/**
	 * 個別仮締コマンド。<br>
	 * <br>
	 * 一覧の選択チェックボックスでチェックが入っているレコードを対象に個別の仮締処理を繰り返し行う。<br>
	 * 締日全体でまだ仮締が行われていなくてもここで個別仮締を行うことが可能で、その場合はこれ以降解除処理を行わない限り勤怠修正画面以外では情報の編集ができなくなる。<br>
	 * 選択チェックボックスにひとつもチェックが入っていない状態で実行しようとした場合にはエラーメッセージを表示し、処理を中止する。<br>
	 */
	public static final String	CMD_TEMPORARY_TIGHTENING	= "TM3124";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String	CMD_TRANSFER				= "TM3125";
	
	/**
	 * 勤怠集計コマンド。<br>
	 * <br>
	 * 実行時点で社員一覧に表示されている社員の内、選択チェックボックスにチェックの
	 * 入っている社員の勤怠情報をシステム日付時点の締期間を基準に集計を行う。<br>
	 * 集計結果を社員一覧部分に表示するが、データベースには反映されない。<br>
	 */
	public static final String	CMD_CALC					= "TM3126";
	
	/**
	 * 個別解除コマンド。<br>
	 * <br>
	 * 一覧の選択チェックボックスでチェックが入っているレコードを対象に個別の解除処理を繰り返し行う。<br>
	 * 仮締がまだ一度も行われていないレコードが含まれている場合でも実行することは可能だが、そのレコードに対して何も処理は実行されない。<br>
	 * 対象レコードの締状態を一段階ずつ下げるので確定状態であれば仮締状態に、仮締状態であれば未締状態へそれぞれ移行する。<br>
	 * 選択チェックボックスにひとつもチェックが入っていない状態で実行しようとした場合にはエラーメッセージを表示し、処理を中止する。<br>
	 */
	public static final String	CMD_RELEASE					= "TM3127";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT					= "TM3128";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE					= "TM3129";
	
	/**
	 * 帳票出力コマンド。<br>
	 * <br>
	 * 現在表示している勤怠集計結果を表計算ファイルに出勤簿として出力する。<br>
	 */
	public static final String	CMD_OUTPUT_FORMS			= "TM3176";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public TotalTimeListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new TotalTimeListVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 表示
			prepareVo();
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			reShow();
		} else if (mospParams.getCommand().equals(CMD_TEMPORARY_TIGHTENING)) {
			// 個別仮締処理
			prepareVo();
			batchTightening();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_CALC)) {
			// 勤怠集計
			prepareVo();
			calc();
		} else if (mospParams.getCommand().equals(CMD_RELEASE)) {
			// 個別解除処理
			prepareVo();
			batchRelease();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_OUTPUT_FORMS)) {
			// 帳票出力
			prepareVo();
			outputAttendanceBooks();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// 譲渡された値をVOに設定
		getTransferredCutoffParams();
		// 締日関連情報を設定
		setCutoffInfo();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// プルダウン設定
		setPulldown();
		// ソートキー設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
	}
	
	/**
	 * 再表示処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void reShow() throws MospException {
		// 締日関連情報を設定
		setCutoffInfo();
		// 検索
		search();
	}
	
	/**
	 * 検索処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 検索クラス取得
		TotalTimeSearchBeanInterface search = timeReference().totalTimeSearch();
		// 検索条件確認
		checkSearchCondition(vo.getTxtEditFromEmployeeCode(), vo.getTxtEditToEmployeeCode(),
				vo.getTxtEditEmployeeName(), vo.getPltEditWorkPlace(), vo.getPltEditEmployment(),
				vo.getPltEditSection(), vo.getPltEditPosition(), vo.getPltEditApproval(), vo.getPltEditCalc());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VOの値を検索クラスへ設定
		search.setTargetYear(vo.getTargetYear());
		search.setTargetMonth(vo.getTargetMonth());
		search.setFromEmployeeCode(vo.getTxtEditFromEmployeeCode());
		search.setToEmployeeCode(vo.getTxtEditToEmployeeCode());
		search.setEmployeeName(vo.getTxtEditEmployeeName());
		search.setWorkPlaceCode(vo.getPltEditWorkPlace());
		search.setEmploymentContractCode(vo.getPltEditEmployment());
		search.setSectionCode(vo.getPltEditSection());
		search.setPositionCode(vo.getPltEditPosition());
		search.setApproval(vo.getPltEditApproval());
		search.setApprovalBeforeDay(vo.getCkbYesterday());
		search.setCalc(vo.getPltEditCalc());
		search.setCutoffCode(vo.getCutoffCode());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<SubordinateListDtoInterface> list = search.getSearchList();
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
		// 一覧選択情報を初期化
		initCkbSelect();
		TotalTimeDtoInterface totalTimeDto = timeReference().totalTimeTransaction().findForKey(vo.getTargetYear(),
				vo.getTargetMonth(), vo.getCutoffCode());
		if (totalTimeDto == null) {
			return;
		}
		vo.setJsCutoffState(Integer.toString(totalTimeDto.getCutoffState()));
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void transfer() throws MospException {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(getSelectedPersonalId(getTransferredIndex()));
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(AttendanceHistoryAction.class.getName())) {
			// MosP処理情報に対象日を設定(対象年月初日)
			setTargetDate(MonthUtility.getYearMonthDate(vo.getTargetYear(), vo.getTargetMonth()));
			// 勤怠集計修正履歴画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(AttendanceHistoryAction.CMD_SELECT_SHOW_TOTAL);
		} else if (actionName.equals(TotalTimeCardAction.class.getName())) {
			// MosP処理情報に締日コードを設定
			setTargetCutoffCode(vo.getCutoffCode());
			// MosP処理情報に対象年月を設定
			setTargetYear(vo.getTargetYear());
			setTargetMonth(vo.getTargetMonth());
			// 勤怠集計修正履歴画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(TotalTimeCardAction.CMD_SELECT_SHOW);
		}
	}
	
	/**
	 * 個別仮締処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchTightening() throws MospException {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 対象年月取得
		int targetYear = vo.getTargetYear();
		int targetMonth = vo.getTargetMonth();
		// 選択個人ID配列取得
		String[] aryPersonalId = getSelectedPersonalIds(vo.getCkbSelect());
		// 締日コード取得
		String cutoffCode = vo.getCutoffCode();
		// 集計クラス取得
		TotalTimeCalcBeanInterface calc = time().totalTimeCalc();
		// 仮締
		List<CutoffErrorListDtoInterface> list = calc.tightening(aryPersonalId, targetYear, targetMonth, cutoffCode);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// エラーリストがある場合
		if (list.isEmpty() == false) {
			// 仮締処理からlistが帰ってきたら集計時エラー内容参照画面へ遷移
			mospParams.addGeneralParam(TimeConst.PRM_TOTALTIME_ERROR, list);
			mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_GENERIC_CODE, cutoffCode);
			mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_YEAR, targetYear);
			mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_MONTH, targetMonth);
			mospParams.setNextCommand(CutoffErrorListAction.CMD_SHOW);
			return;
		}
		// コミット
		commit();
		// 仮締成功メッセージ設定
		addTighteningMessage();
		// 一覧選択情報を初期化
		initCkbSelect();
		// 検索
		search();
	}
	
	/**
	 * 勤怠集計処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void calc() throws MospException {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 検索クラス取得
		SubordinateSearchBeanInterface search = timeReference().subordinateSearch();
		// 勤怠集計クラス取得
		TotalTimeCalcBeanInterface total = time().totalTimeCalc();
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
		}
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 一覧選択情報を初期化
		initCkbSelect();
		// 検索結果確認
		if (list.isEmpty()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 個別解除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchRelease() throws MospException {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 選択個人ID配列取得
		String[] aryPersonalId = getSelectedPersonalIds(vo.getCkbSelect());
		// 対象年月取得
		int targetYear = vo.getTargetYear();
		int targetMonth = vo.getTargetMonth();
		// 締日コード取得
		String cutoffCode = vo.getCutoffCode();
		// 個別解除処理
		List<String> targetPersonalIdList = new ArrayList<String>();
		for (String personalId : aryPersonalId) {
			Integer state = timeReference().totalTimeEmployeeTransaction().getCutoffState(personalId, targetYear,
					targetMonth);
			if (state == null || state.intValue() != TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) {
				continue;
			}
			targetPersonalIdList.add(personalId);
		}
		if (targetPersonalIdList.isEmpty()) {
			return;
		}
		// 勤怠集計特別休暇データ削除
		time().totalLeaveRegist().delete(targetPersonalIdList, targetYear, targetMonth);
		// 勤怠集計その他休暇データ削除
		time().totalOtherVacationRegist().delete(targetPersonalIdList, targetYear, targetMonth);
		// 勤怠集計欠勤データ削除
		time().totalAbsenceRegist().delete(targetPersonalIdList, targetYear, targetMonth);
		// 勤怠集計データ削除
		time().totalTimeRegist().delete(targetPersonalIdList, targetYear, targetMonth);
		// 勤怠集計修正データ削除
		time().totalTimeCorrectionRegist().delete(targetPersonalIdList, targetYear, targetMonth);
		// 仮締解除
		time().totalTimeEmployeeTransactionRegist().draftRelease(targetPersonalIdList, targetYear, targetMonth,
				cutoffCode);
		// 勤怠集計管理からレコードを取得
		TotalTimeDtoInterface totalTimeDto = timeReference().totalTimeTransaction().findForKey(targetYear, targetMonth,
				cutoffCode);
		if (totalTimeDto != null) {
			int cutoffState = totalTimeDto.getCutoffState();
			if (cutoffState == TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) {
				// 仮締の場合
				totalTimeDto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT);
				time().totalTimeTransactionRegist().draftRelease(totalTimeDto);
			} else if (cutoffState == TimeConst.CODE_CUTOFF_STATE_TIGHTENED) {
				// 確定の場合
				return;
			}
		}
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 解除成功メッセージ設定
		addReleaseMessage();
		// 一覧選択情報を初期化
		initCkbSelect();
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
	 * @throws MospException 例外発生時
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 検索項目設定
		vo.setTxtEditFromEmployeeCode("");
		vo.setTxtEditToEmployeeCode("");
		vo.setTxtEditEmployeeName("");
		vo.setJsCutoffState("");
		vo.setCkbYesterday(MospConst.CHECKBOX_OFF);
		vo.setJsSearchConditionRequired(isSearchConditionRequired());
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setPulldown() throws MospException {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(vo.getTargetYear(), vo.getTargetMonth(), mospParams);
		// プルダウン用配列を取得
		String[][] aryWorkPlace = reference().workPlace().getNameSelectArray(targetDate, true, null);
		String[][] aryEmployment = reference().employmentContract().getNameSelectArray(targetDate, true, null);
		String[][] arySection = reference().section().getCodedSelectArray(targetDate, true, null);
		String[][] aryPosition = reference().position().getCodedSelectArray(targetDate, true, null);
		// プルダウンの設定
		// 勤務地
		vo.setAryPltEditWorkPlace(aryWorkPlace);
		// 雇用契約
		vo.setAryPltEditEmployment(aryEmployment);
		// 所属
		vo.setAryPltEditSection(arySection);
		// 職位
		vo.setAryPltEditPosition(aryPosition);
		// 未承認
		vo.setAryPltEditApproval(getCodeArray(TimeConst.CODE_NOT_APPROVED, true));
		// 未集計
		vo.setAryPltEditCalc(getCodeArray(TimeConst.CODE_CUTOFFSTATE, true));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 対象年月における締期間の基準日を取得
		Date cutoffTargetDate = vo.getCutoffTermTargetDate();
		// データ配列初期化
		String[] aryPersonalId = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblWorkDate = new String[list.size()];
		String[] aryLblPaidHoliday = new String[list.size()];
		String[] aryLblAllHoliday = new String[list.size()];
		String[] aryLblAbsence = new String[list.size()];
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
		String[] aryLblApploval = new String[list.size()];
		String[] aryLblCalc = new String[list.size()];
		String[] aryLblCorrection = new String[list.size()];
		String[] claApploval = new String[list.size()];
		String[] claCalc = new String[list.size()];
		boolean[] aryNeedDetail = new boolean[list.size()];
		// データ作成
		SectionReferenceBeanInterface getSection = reference().section();
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			SubordinateListDtoInterface dto = (SubordinateListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryPersonalId[i] = dto.getPersonalId();
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblSection[i] = getSection.getSectionAbbr(dto.getSectionCode(), cutoffTargetDate);
			aryLblWorkDate[i] = getNumberString(dto.getWorkDate(), 1);
			aryLblPaidHoliday[i] = getNumberString(dto.getPaidHoliday(), 1);
			aryLblAllHoliday[i] = getNumberString(dto.getAllHoliday(), 1);
			aryLblAbsence[i] = getNumberString(dto.getAbsence(), 1);
			aryLblWorkTime[i] = toTimeDotFormatString(dto.getWorkTime());
			aryLblRestTime[i] = toTimeDotFormatString(dto.getRestTime());
			aryLblPrivateTime[i] = toTimeDotFormatString(dto.getPrivateTime());
			aryLblLateTime[i] = toTimeDotFormatString(dto.getLateTime());
			aryLblLeaveEarlyTime[i] = toTimeDotFormatString(dto.getLeaveEarlyTime());
			aryLblLateLeaveEarlyTime[i] = toTimeDotFormatString(dto.getLateLeaveEarlyTime());
			aryLblOverTimeIn[i] = toTimeDotFormatString(dto.getOverTimeIn());
			aryLblOverTimeOut[i] = toTimeDotFormatString(dto.getOverTimeOut());
			aryLblWorkOnHolidayTime[i] = toTimeDotFormatString(dto.getWorkOnHolidayTime());
			aryLblLateNightTime[i] = toTimeDotFormatString(dto.getLateNightTime());
			// 承認状態名称
			aryLblApploval[i] = dto.getApproval();
			// 承認状態表示クラス
			claApploval[i] = dto.getApprovalStateClass();
			// 締状態名称
			aryLblCalc[i] = dto.getCalc();
			// 締状態表示クラス
			claCalc[i] = dto.getCutoffStateClass();
			// 修正情報
			aryLblCorrection[i] = dto.getCorrection();
			aryNeedDetail[i] = dto.getCutoffState() != TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT;
		}
		// データをVOに設定
		vo.setAryPersonalId(aryPersonalId);
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
		vo.setAryLblApploval(aryLblApploval);
		vo.setAryLblCalc(aryLblCalc);
		vo.setAryLblCorrection(aryLblCorrection);
		vo.setClaApploval(claApploval);
		vo.setClaCalc(claCalc);
		vo.setAryNeedDetail(aryNeedDetail);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFields(TotalTimeDtoInterface dto) throws MospException {
		// VO取得
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setCalculationYear(vo.getTargetYear());
		dto.setCalculationMonth(vo.getTargetMonth());
		dto.setCutoffCode(vo.getCutoffCode());
		dto.setCalculationDate(vo.getCalculationDate());
		// 未締に設定
		dto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT);
	}
	
	/**
	 * 帳票(出勤簿)を作成し、送出ファイルとして設定する。<br>
	 * @throws MospException 帳票の作成に失敗した場合
	 */
	protected void outputAttendanceBooks() throws MospException {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
		// 個人ID配列と締日コードと集計年月を取得
		String[] personalIds = getSelectedPersonalIds(vo.getCkbSelect());
		String cutoffCode = vo.getCutoffCode();
		int targetYear = vo.getTargetYear();
		int targetMonth = vo.getTargetMonth();
		// 締日取得用の対象日(年月指定時の基準日)を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 締日を取得
		int cutoffDate = timeReference().cutoff().getCutoffEntity(cutoffCode, targetDate).getCutoffDate();
		// 年月で出勤簿を作成
		timeReference().attendanceBook().makeAttendanceBooks(personalIds, targetYear, targetMonth, cutoffDate);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 選択された部下一覧情報のリストを取得する。<br>
	 * <br>
	 * @return 部下一覧情報リスト
	 */
	protected List<SubordinateListDtoInterface> getSelectedListDto() {
		// VO準備
		TotalTimeListVo vo = (TotalTimeListVo)mospParams.getVo();
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
