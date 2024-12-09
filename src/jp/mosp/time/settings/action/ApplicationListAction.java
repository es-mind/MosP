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
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.ApplicationRegistBeanInterface;
import jp.mosp.time.bean.ApplicationSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.comparator.settings.ApplicationMasterApplicationCodeComparator;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.ApplicationListVo;

/**
 * 勤怠管理、カレンダ、有給休暇についての設定対象を各種マスタに適用させる。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li></ul>
 */
public class ApplicationListAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM5610";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に設定適用情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM5612";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たな設定紐付けの適用や既存の適用情報の編集を行った際に
	 * 検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String	CMD_RE_SEARCH			= "TM5613";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 一覧表示欄の選択チェックボックスで選択されているレコードを対象に
	 * 論理削除を行うよう繰り返し処理を行う。<br>
	 */
	public static final String	CMD_DELETE				= "TM5617";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_SORT				= "TM5618";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM5619";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 勤務地・雇用契約・所属・職位・勤怠設定・有休設定・カレンダの各マスタを参照する。<br>
	 * それらのマスタからここで決定した有効日時点で有効なレコードのリストを作成し、
	 * 各マスタ情報毎にそれぞれのプルダウンに表示する。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM5680";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE		= "TM5685";
	
	
	/**
	 * {@link TimeSettingAction#TimeSettingAction()}を実行する。<br>
	 */
	public ApplicationListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ApplicationListVo();
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
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
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
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO準備
		ApplicationListVo vo = (ApplicationListVo)mospParams.getVo();
		// 勤怠設定共通VO初期値設定
		initTimeSettingVoFields();
		// 初期値設定
		vo.setTxtSearchApplicationCode("");
		vo.setTxtSearchApplicationName("");
		vo.setTxtSearchApplicationAbbr("");
		vo.setRadApplicationType(PlatformConst.APPLICATION_TYPE_MASTER);
		vo.setPltSearchWorkPlaceMaster("");
		vo.setPltSearchEmploymentMaster("");
		vo.setPltSearchSectionMaster("");
		vo.setPltSearchPositionMaster("");
		vo.setTxtSearchEmployeeCode("");
		vo.setPltSearchWorkSetting("");
		vo.setPltSearchSchedule("");
		vo.setPltSearchPaidHoliday("");
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ApplicationMasterApplicationCodeComparator.class.getName());
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
		ApplicationListVo vo = (ApplicationListVo)mospParams.getVo();
		// 検索クラス取得
		ApplicationSearchBeanInterface search = timeReference().applicationSearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setApplicationCode(vo.getTxtSearchApplicationCode());
		search.setApplicationType(vo.getRadApplicationType());
		search.setApplicationName(vo.getTxtSearchApplicationName());
		search.setApplicationAbbr(vo.getTxtSearchApplicationAbbr());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		search.setWorkSettingCode(vo.getPltSearchWorkSetting());
		search.setScheduleCode(vo.getPltSearchSchedule());
		search.setPaidHolidayCode(vo.getPltSearchPaidHoliday());
		search.setWorkPlaceCode(vo.getPltSearchWorkPlaceMaster());
		search.setEmploymentCode(vo.getPltSearchEmploymentMaster());
		search.setSectionCode(vo.getPltSearchSectionMaster());
		search.setPositionCode(vo.getPltSearchPositionMaster());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<ApplicationDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ApplicationMasterApplicationCodeComparator.class.getName());
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
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		ApplicationListVo vo = (ApplicationListVo)mospParams.getVo();
		// 削除対象ID配列取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 削除
		time().applicationRegist().delete(idArray);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteHistory(mospParams, idArray.length);
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
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		ApplicationListVo vo = (ApplicationListVo)mospParams.getVo();
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
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		ApplicationListVo vo = (ApplicationListVo)mospParams.getVo();
		// クラス準備
		ApplicationRegistBeanInterface regist = time().applicationRegist();
		// 値準備
		long[] chbSelect = getIdArray(vo.getCkbSelect());
		int inactiveFlag = getInt(vo.getPltUpdateInactivate());
		// 一括更新処理
		regist.update(chbSelect, getUpdateActivateDate(), inactiveFlag);
		// 一括更新結果確認
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
		search();
	}
	
	/**
	 * プルダウンを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void setPulldown() throws MospException {
		// VO準備
		ApplicationListVo vo = (ApplicationListVo)mospParams.getVo();
		// プルダウンの設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltSearchWorkPlaceMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchEmploymentMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchSectionMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchPositionMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchWorkSetting(getInputActivateDatePulldown());
			vo.setAryPltSearchSchedule(getInputActivateDatePulldown());
			vo.setAryPltSearchPaidHoliday(getInputActivateDatePulldown());
			return;
		}
		// 検索有効日取得
		Date date = getSearchActivateDate();
		// 勤務地
		String[][] workPlace = reference().workPlace().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchWorkPlaceMaster(workPlace);
		// 雇用契約
		String[][] aryEmployment = reference().employmentContract().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchEmploymentMaster(aryEmployment);
		// 所属
		String[][] arySection = reference().section().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchSectionMaster(arySection);
		// 職位
		String[][] aryPosition = reference().position().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchPositionMaster(aryPosition);
		// 勤怠設定
		vo.setAryPltSearchWorkSetting(timeReference().timeSetting().getCodedSelectArray(date, true));
		// カレンダ
		vo.setAryPltSearchSchedule(timeReference().scheduleUtil().getCodedSelectArray(date, true));
		// 有給休暇設定
		vo.setAryPltSearchPaidHoliday(timeReference().paidHoliday().getCodedSelectArray(date, true));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO準備
		ApplicationListVo vo = (ApplicationListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblApplicationCode = new String[list.size()];
		String[] aryLblApplicationAbbr = new String[list.size()];
		String[] aryLblWorkPlace = new String[list.size()];
		String[] aryLblEmployment = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblPosition = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblWorkSetting = new String[list.size()];
		String[] aryLblSchadeule = new String[list.size()];
		String[] aryLblPaidHoliday = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// 検索日取得
		Date targetDate = getSearchActivateDate();
		// 参照クラス準備
		TimeSettingReferenceBeanInterface timeSetting = timeReference().timeSetting();
		ScheduleUtilBeanInterface scheduleUtil = timeReference().scheduleUtil();
		PaidHolidayReferenceBeanInterface paidHoliday = timeReference().paidHoliday();
		HumanReferenceBeanInterface human = reference().human();
		PlatformMasterBeanInterface platformMaster = reference().master();
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			ApplicationDtoInterface dto = (ApplicationDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblApplicationCode[i] = dto.getApplicationCode();
			aryLblApplicationAbbr[i] = dto.getApplicationAbbr();
			aryLblWorkPlace[i] = reference().workPlace().getWorkPlaceAbbr(dto.getWorkPlaceCode(), targetDate);
			aryLblEmployment[i] = reference().employmentContract().getContractAbbr(dto.getEmploymentContractCode(),
					targetDate);
			aryLblSection[i] = platformMaster.getSectionAbbr(dto.getSectionCode(), targetDate);
			aryLblPosition[i] = reference().position().getPositionAbbr(dto.getPositionCode(), targetDate);
			aryLblWorkSetting[i] = timeSetting.getTimeSettingAbbr(dto.getWorkSettingCode(), targetDate);
			aryLblSchadeule[i] = scheduleUtil.getScheduleAbbr(dto.getScheduleCode(), targetDate);
			aryLblPaidHoliday[i] = paidHoliday.getPaidHolidayAbbr(dto.getPaidHolidayCode(), targetDate);
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
			aryLblEmployeeCode[i] = human.getHumanNames(dto.getPersonalIds(), targetDate);
		}
		// データをVOに設定
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblApplicationCode(aryLblApplicationCode);
		vo.setAryLblApplicationAbbr(aryLblApplicationAbbr);
		vo.setAryLblWorkPlace(aryLblWorkPlace);
		vo.setAryLblEmployment(aryLblEmployment);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblPosition(aryLblPosition);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblWorkSetting(aryLblWorkSetting);
		vo.setAryLblSchadeule(aryLblSchadeule);
		vo.setAryLblPaidHoliday(aryLblPaidHoliday);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
}
