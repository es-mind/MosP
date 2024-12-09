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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ApplicationReferenceSearchBeanInterface;
import jp.mosp.time.dto.settings.ApplicationReferenceDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.ApplicationReferenceVo;

/**
 * 勤怠設定、締日、カレンダーなど設定適用を社員によって参照する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li></ul>
 */
public class ApplicationReferenceAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * サーバより日付を取得し、有効日決定状態にして画面を表示する。<br>
	 */
	public static final String	CMD_SHOW				= "TM5710";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行う。<br>
	 * 一覧表示の際には社員コードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM5712";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 有効日を決定し、所属や職位などのプルダウンを取得する。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM5716";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM5718";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM5719";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public ApplicationReferenceAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ApplicationReferenceVo();
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
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定コマンド
			prepareVo();
			setActivationDate();
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
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO準備
		ApplicationReferenceVo vo = (ApplicationReferenceVo)mospParams.getVo();
		// 勤怠設定共通VO初期値設定
		initTimeSettingVoFields();
		// 初期値設定
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchEmployeeName("");
		vo.setTxtSearchApplicationCode("");
		vo.setTxtSearchApplicationName("");
		vo.setJsSearchConditionRequired(isSearchConditionRequired());
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO準備
		ApplicationReferenceVo vo = (ApplicationReferenceVo)mospParams.getVo();
		// 検索クラス取得
		ApplicationReferenceSearchBeanInterface search = timeReference().applicationReferenceSearch();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// エラーメッセージを設定(有効日を決定してください)
			PfMessageUtility.addErrorActivateDateNotSettled(mospParams);
			return;
		}
		// 検索条件確認
		checkSearchCondition(vo.getTxtSearchEmployeeCode(), vo.getTxtSearchEmployeeName(), vo.getPltSearchWorkPlace(),
				vo.getPltSearchEmployment(), vo.getPltSearchSection(), vo.getPltSearchPosition(),
				vo.getTxtSearchApplicationCode(), vo.getTxtSearchApplicationName(), vo.getPltSearchTimeSetting(),
				vo.getPltSearchSchedule(), vo.getPltSearchPaidHoliday(), vo.getPltSearchCutoff());
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
		search.setApplicationCode(vo.getTxtSearchApplicationCode());
		search.setApplicationName(vo.getTxtSearchApplicationName());
		search.setTimeSettingCode(vo.getPltSearchTimeSetting());
		search.setCutoffCode(vo.getPltSearchCutoff());
		search.setScheduleCode(vo.getPltSearchSchedule());
		search.setPaidHolidayCode(vo.getPltSearchPaidHoliday());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<ApplicationReferenceDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.size() == 0) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		ApplicationReferenceVo vo = (ApplicationReferenceVo)mospParams.getVo();
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
	 * 一覧のソート処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void sort() throws MospException {
		setVoList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * 一覧のページ処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * プルダウンを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void setPulldown() throws MospException {
		// VO準備
		ApplicationReferenceVo vo = (ApplicationReferenceVo)mospParams.getVo();
		// プルダウンの設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltSearchWorkPlaceMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchEmploymentMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchSectionMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchPositionMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchTimeSetting(getInputActivateDatePulldown());
			vo.setAryPltSearchSchedule(getInputActivateDatePulldown());
			vo.setAryPltSearchPaidHoliday(getInputActivateDatePulldown());
			vo.setAryPltSearchCutoff(getInputActivateDatePulldown());
			return;
		}
		// 検索有効日取得
		Date date = getSearchActivateDate();
		// 勤務地
		vo.setAryPltSearchWorkPlaceMaster(reference().workPlace().getCodedSelectArray(date, true, null));
		// 雇用契約
		vo.setAryPltSearchEmploymentMaster(reference().employmentContract().getCodedSelectArray(date, true, null));
		// 所属
		vo.setAryPltSearchSectionMaster(reference().section().getCodedSelectArray(date, true, null));
		// 職位
		vo.setAryPltSearchPositionMaster(reference().position().getCodedSelectArray(date, true, null));
		// 勤怠設定
		vo.setAryPltSearchTimeSetting(timeReference().timeSetting().getCodedSelectArray(date, true));
		// カレンダ
		vo.setAryPltSearchSchedule(timeReference().scheduleUtil().getCodedSelectArray(date, true));
		// 有給休暇設定
		vo.setAryPltSearchPaidHoliday(timeReference().paidHoliday().getCodedSelectArray(date, true));
		// 締日
		vo.setAryPltSearchCutoff(timeReference().cutoff().getCodedSelectArray(date, true, null));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO準備
		ApplicationReferenceVo vo = (ApplicationReferenceVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblApplicationCode = new String[list.size()];
		String[] aryLblApplication = new String[list.size()];
		String[] aryLblTimeSetting = new String[list.size()];
		String[] aryLblCutoff = new String[list.size()];
		String[] aryLblSchadeule = new String[list.size()];
		String[] aryLblPaidHoliday = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			ApplicationReferenceDtoInterface dto = (ApplicationReferenceDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = dto.getEmployeeName();
			aryLblApplicationCode[i] = dto.getApplicationCode();
			aryLblApplication[i] = dto.getApplicationName();
			aryLblTimeSetting[i] = dto.getTimeSettingAbbr();
			aryLblCutoff[i] = dto.getCutoffAbbr();
			aryLblSchadeule[i] = dto.getScheduleAbbr();
			aryLblPaidHoliday[i] = dto.getPaidHolidayAbbr();
		}
		// データをVOに設定
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblApplicationCode(aryLblApplicationCode);
		vo.setAryLblApplication(aryLblApplication);
		vo.setAryLblTimeSetting(aryLblTimeSetting);
		vo.setAryLblCutoff(aryLblCutoff);
		vo.setAryLblSchadeule(aryLblSchadeule);
		vo.setAryLblPaidHoliday(aryLblPaidHoliday);
	}
}
