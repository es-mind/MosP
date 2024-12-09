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
package jp.mosp.time.settings.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.comparator.base.ActivateDateComparator;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.PaidHolidayManagementSearchBeanInterface;
import jp.mosp.time.dto.settings.PaidHolidayManagementListDtoInterface;
import jp.mosp.time.settings.vo.PaidHolidayManagementVo;

/**
 * 各従業員の有給休暇の保有日数を確認する。<br>
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
public class PaidHolidayManagementAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM4410";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を基に人事情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM4412";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たに休暇の付与・破棄を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM4413";
	
	/**
	 * 遷移コマンド。<br>
	 * <br>
	 * 画面遷移を行う。<br>
	 */
	public static final String	CMD_TRANSFER			= "TM4416";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM4418";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM4419";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 決定した有効日時点で有効な勤務地、雇用契約、所属、職位のレコードを取得し、名称を各プルダウンで表示する。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM4480";
	
	/**
	 * CSV出力コマンド。<br>
	 * <br>
	 * 検索結果に表示された社員の有給休暇情報をCSVに出力する。<br>
	 */
	public static final String	CMD_OUTPUT				= "TM4440";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public PaidHolidayManagementAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PaidHolidayManagementVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 検索
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定コマンド
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_OUTPUT)) {
			// CSV出力コマンド
			prepareVo();
			output();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO準備
		PaidHolidayManagementVo vo = (PaidHolidayManagementVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(ActivateDateComparator.class.getName());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		PaidHolidayManagementVo vo = (PaidHolidayManagementVo)mospParams.getVo();
		// 検索クラス取得
		PaidHolidayManagementSearchBeanInterface search = timeReference().paidHolidayManagementSearch();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// エラーメッセージを設定(有効日を決定してください)
			PfMessageUtility.addErrorActivateDateNotSettled(mospParams);
			return;
		}
		// 検索条件確認
		checkSearchCondition(vo.getTxtSearchEmployeeCode(), vo.getTxtSearchEmployeeName(), vo.getPltSearchWorkPlace(),
				vo.getPltSearchEmployment(), vo.getPltSearchSection(), vo.getPltSearchPosition());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setWorkPlaceCode(vo.getPltSearchWorkPlace());
		search.setEmploymentCode(vo.getPltSearchEmployment());
		search.setSectionCode(vo.getPltSearchSection());
		search.setPositionCode(vo.getPltSearchPosition());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<PaidHolidayManagementListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// 検索時有効日をyyyyMMdd形式で取得
		vo.setCsvActivateDate(DateUtility.getStringDate(getSearchActivateDate(), "yyyyMMdd"));
		// ソート
		sort();
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
		PaidHolidayManagementVo vo = (PaidHolidayManagementVo)mospParams.getVo();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getAryPersonalId(getTransferredIndex()));
		// MosP処理情報に対象日を設定
		setTargetDate(getDate(vo.getAryLblActivateDate()[getTransferredIndex()]));
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(PaidHolidayReferenceAction.class.getName())) {
			// 選択表示
			mospParams.setNextCommand(PaidHolidayReferenceAction.CMD_SELECT_SHOW);
		} else if (actionName.equals(PaidHolidayHistoryAction.class.getName())) {
			// 選択表示
			mospParams.setNextCommand(PaidHolidayHistoryAction.CMD_SELECT_SHOW);
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
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		PaidHolidayManagementVo vo = (PaidHolidayManagementVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 上位所属のプルダウン取得
		setPulldown();
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		PaidHolidayManagementVo vo = (PaidHolidayManagementVo)mospParams.getVo();
		// システム日付取得
		Date date = DateUtility.getSystemDate();
		// 検索項目設定
		vo.setTxtSearchActivateYear(DateUtility.getStringYear(date));
		vo.setTxtSearchActivateMonth(DateUtility.getStringMonth(date));
		vo.setTxtSearchActivateDay(DateUtility.getStringDay(date));
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchEmployeeName("");
		vo.setPltSearchEmployment("");
		vo.setPltSearchPosition("");
		vo.setPltSearchSection("");
		vo.setPltSearchWorkPlace("");
		vo.setJsSearchConditionRequired(isSearchConditionRequired());
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setPulldown() throws MospException {
		// VO準備
		PaidHolidayManagementVo vo = (PaidHolidayManagementVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// システム日付取得
			Date date = DateUtility.getSystemDate();
			// プルダウンの設定
			String[][] aryWorkPlace = reference().workPlace().getCodedSelectArray(date, true, null);
			vo.setAryPltSearchWorkPlace(aryWorkPlace);
			String[][] aryEmployment = reference().employmentContract().getCodedSelectArray(date, true, null);
			vo.setAryPltSearchEmployment(aryEmployment);
			String[][] arySection = reference().section().getCodedSelectArray(date, true, null);
			vo.setAryPltSearchSection(arySection);
			String[][] aryPosition = reference().position().getCodedSelectArray(date, true, null);
			vo.setAryPltSearchPosition(aryPosition);
		} else {
			// プルダウン設定
			vo.setAryPltSearchWorkPlace(getInputActivateDatePulldown());
			vo.setAryPltSearchEmployment(getInputActivateDatePulldown());
			vo.setAryPltSearchSection(getInputActivateDatePulldown());
			vo.setAryPltSearchPosition(getInputActivateDatePulldown());
		}
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO準備
		PaidHolidayManagementVo vo = (PaidHolidayManagementVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryPersonalId = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblFormerDate = new String[list.size()];
		String[] aryLblFormerTime = new String[list.size()];
		String[] aryLblDate = new String[list.size()];
		String[] aryLblTime = new String[list.size()];
		String[] aryLblStockDate = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			PaidHolidayManagementListDtoInterface dto = (PaidHolidayManagementListDtoInterface)list.get(i);
			SectionReferenceBeanInterface getSection = reference().section();
			Date date = DateUtility.getSystemDate();
			// 配列に情報を設定
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryPersonalId[i] = dto.getPersonalId();
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblSection[i] = getSection.getSectionAbbr(dto.getSectionCode(), date);
			aryLblFormerDate[i] = getNumberString(dto.getFormerDate(), 1);
			aryLblFormerTime[i] = String.valueOf(dto.getFormerTime());
			aryLblDate[i] = getNumberString(dto.getDate(), 1);
			aryLblTime[i] = String.valueOf(dto.getTime());
			aryLblStockDate[i] = getNumberString(dto.getStockDate(), 1);
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryPersonalId(aryPersonalId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblFormerDate(aryLblFormerDate);
		vo.setAryLblFormerTime(aryLblFormerTime);
		vo.setAryLblDate(aryLblDate);
		vo.setAryLblTime(aryLblTime);
		vo.setAryLblStockDate(aryLblStockDate);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
	/**
	 * 検索結果に表示された社員の有給休暇情報をCSVに出力する。
	 * @throws MospException 例外発生時
	 */
	protected void output() throws MospException {
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(getCsvDataList()));
	}
	
	/**
	 * VOの値をCSVリストにセットする。
	 * @return CSV出力データ
	 * @throws MospException 例外発生時
	 */
	protected List<String[]> getCsvDataList() throws MospException {
		// VO準備
		PaidHolidayManagementVo vo = (PaidHolidayManagementVo)mospParams.getVo();
		// 有効日
		// ファイル名設定
		mospParams.setFileName(vo.getCsvActivateDate() + "_PaidHolidayManagement.csv");
		// 出力リスト準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// ヘッダ名取得
		String[] header = getHeadName();
		csvDataList.add(header);
		// 休暇確認情報ごとに処理
		@SuppressWarnings("unchecked")
		List<PaidHolidayManagementListDtoInterface> list = (List<PaidHolidayManagementListDtoInterface>)vo.getList();
		// 休暇確認情報ごとに処理
		for (PaidHolidayManagementListDtoInterface dto : list) {
			String[] fieldValue = new String[header.length];
			fieldValue[0] = MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
			fieldValue[1] = dto.getEmployeeCode();
			fieldValue[2] = reference().section().getSectionAbbr(dto.getSectionCode(), DateUtility.getSystemDate());
			fieldValue[3] = getNumberString(dto.getFormerDate(), 1);
			fieldValue[4] = String.valueOf(dto.getFormerTime());
			fieldValue[5] = getNumberString(dto.getDate(), 1);
			fieldValue[6] = String.valueOf(dto.getTime());
			fieldValue[7] = String.valueOf(dto.getStockDate());
			csvDataList.add(fieldValue);
		}
		
		return csvDataList;
	}
	
	/**
	 * ヘッダ配列を取得する。
	 * @return ヘッダ配列
	 */
	public String[] getHeadName() {
		String[] headName = { mospParams.getName("Employee", "FirstName"), mospParams.getName("Employee", "Code"),
			mospParams.getName("Section"), mospParams.getName("PreviousYear", "Days"),
			mospParams.getName("PreviousYear", "Time"), mospParams.getName("ThisYear", "Days"),
			mospParams.getName("ThisYear", "Time"), mospParams.getName("Stock") };
		return headName;
	}
}
