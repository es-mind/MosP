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
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.comparator.base.ActivateDateComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayEntranceDateReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayHistorySearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayHistoryListDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.PaidHolidayHistoryVo;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 各従業員に対して有給休暇の手動付与を行う。<br>
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
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_PAIDHOLIDAY_CALC}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_EMPLOYEE_CODE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_TARGET}
 * </li><li>
 * {@link #CMD_SET_ACTIVATE_DATE}
 * </li></ul>
 */
public class PaidHolidayHistoryAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM4420";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 有給休暇確認画面で選択したレコードの情報を編集欄に入力した状態の編集モードで表示を行う。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "TM4421";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を基に人事情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM4422";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たに休暇の付与・破棄を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM4423";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 編集欄に入力した内容を基に従業員別の有給休暇の保有数を管理するテーブルに登録する。<br>
	 * 有効日が入力されていない状態で登録を行おうとした場合は場合はエラーメッセージにて通知し、処理は実行されない。<br>
	 */
	public static final String	CMD_REGIST				= "TM4425";
	
	/**
	 * 再計算コマンド。<br>
	 * 付与日数/時間再計算<br>
	 * <br>
	 * <br>
	 */
	public static final String	CMD_PAIDHOLIDAY_CALC	= "TM4426";
	
	/**
	 * 遷移コマンド。<br>
	 * <br>
	 * 画面遷移を行う。<br>
	 */
	public static final String	CMD_TRANSFER			= "TM4427";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM4428";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM4429";
	
	/**
	 * 社員コード決定コマンド。<br>
	 * <br>
	 * 入力された社員コードを基に該当の社員情報を検索し、社員名のラベルに氏名を出力する。<br>
	 * 以降、ここで決定した社員の休暇の付与履歴参照や付与・控除といったことを行うことができる。<br>
	 */
	public static final String	CMD_SET_EMPLOYEE_CODE	= "TM4470";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 編集テーブルの各入力欄に表示されているレコード内容をクリアにする。登録ボタンクリック時のコマンドを登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "TM4471";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を編集欄に表示させ、編集モードに切り替える。<br>
	 */
	
	public static final String	CMD_EDIT_TARGET			= "TM4472";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 決定した有効日時点で有効な勤務地、雇用契約、職位、所属の情報を取得し、コードと名称をプルダウンで表示する。<br>
	 */
	public static final String	CMD_SET_ACTIVATE_DATE	= "TM4477";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public PaidHolidayHistoryAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PaidHolidayHistoryVo();
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
			prepareVo();
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_PAIDHOLIDAY_CALC)) {
			// 付与日数/時間再計算
			prepareVo();
			paidHolydayCalc();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
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
		} else if (mospParams.getCommand().equals(CMD_SET_EMPLOYEE_CODE)) {
			// 社員コード決定
			prepareVo();
			setEmployeeCode();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_TARGET)) {
			// 編集対象決定
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATE_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// プルダウン設定
		setPulldown();
		// モード設定
		insertMode();
		// ソートキー設定
		vo.setComparatorName(ActivateDateComparator.class.getName());
	}
	
	/**
	 * 選択表示処理を行う。
	 * @throws MospException 例外処理発生時
	 */
	protected void select() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 初期化
		setDefaultValues();
		// プルダウン設定
		setPulldown();
		// ソートキー設定
		vo.setComparatorName(ActivateDateComparator.class.getName());
		// 個人ID取得
		String personalId = getTargetPersonalId();
		// 対象日(システム日付)取得
		Date targetDate = getSystemDate();
		// 編集モード(新規登録)設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		// 人事情報取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, targetDate);
		if (humanDto != null) {
			// 社員コード設定
			vo.setTxtEditEmployeeCode(humanDto.getEmployeeCode());
		}
		// 付与日設定
		vo.setTxtEditActivateYear(getStringYear(targetDate));
		vo.setTxtEditActivateMonth(getStringMonth(targetDate));
		vo.setTxtEditActivateDay(getStringDay(targetDate));
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合 
	 */
	protected void search() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 検索クラス取得
		PaidHolidayHistorySearchBeanInterface search = timeReference().paidHolidayHistorySearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setWorkPlaceCode(vo.getPltSearchWorkPlace());
		search.setEmploymentCode(vo.getPltSearchEmployment());
		search.setSectionCode(vo.getPltSearchSection());
		search.setPositionCode(vo.getPltSearchPosition());
		search.setInactivateFlag(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<PaidHolidayHistoryListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ActivateDateComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 編集モード確認
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
			// 新規登録
			insert();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			// 履歴更新
			update();
		}
	}
	
	/**
	 * 付与日数/時間の再計算処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void paidHolydayCalc() throws MospException {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		
		// 変更前合計値取得
		double beforeFormerDate = getDouble(vo.getLblBeforeFormerDate());
		int beforeFormerTime = getInt(vo.getLblBeforeFormerTime());
		double beforeDate = getDouble(vo.getLblBeforeDate());
		int beforeTime = getInt(vo.getLblBeforeTime());
		double beforeStockDate = getDouble(vo.getLblBeforeStockDate());
		
		// 付与値取得
		double editFormerDate = 0.0D;
		int editFormerTime = 0;
		double editDate = 0.0D;
		int editTime = 0;
		double editStockDate = 0.0D;
		if (vo.getTxtEditFormerGivingDay().isEmpty()) {
			vo.setTxtEditFormerGivingDay("0.0");
		} else {
			editFormerDate = getDouble(vo.getTxtEditFormerGivingDay());
		}
		if (vo.getTxtEditFormerGivingTime().isEmpty()) {
			vo.setTxtEditFormerGivingTime("0");
		} else {
			editFormerTime = getInt(vo.getTxtEditFormerGivingTime());
		}
		if (vo.getTxtEditGivingDay().isEmpty()) {
			vo.setTxtEditGivingDay("0.0");
		} else {
			editDate = getDouble(vo.getTxtEditGivingDay());
		}
		if (vo.getTxtEditGivingTime().isEmpty()) {
			vo.setTxtEditGivingTime("0");
		} else {
			editTime = getInt(vo.getTxtEditGivingTime());
		}
		if (vo.getTxtEditGivingStockDay().isEmpty()) {
			vo.setTxtEditGivingStockDay("0.0");
		} else {
			editStockDate = getDouble(vo.getTxtEditGivingStockDay());
		}
		
		// 変更後合計値設定
		double afterFormerDate = 0.0D;
		int afterFormerTime = 0;
		double afterDate = 0.0D;
		int afterTime = 0;
		double afterStockDate = 0.0D;
		// 前年度付与日数設定
		if (vo.getPltEditFormerGivingType().equals("1")) {
			// 付与設定
			afterFormerDate = beforeFormerDate + editFormerDate;
			afterFormerTime = beforeFormerTime + editFormerTime;
		} else {
			// 廃棄設定
			afterFormerDate = beforeFormerDate - editFormerDate;
			afterFormerTime = beforeFormerTime - editFormerTime;
		}
		// 今年度付与日数設定
		if (vo.getPltEditGivingType().equals("1")) {
			// 付与設定
			afterDate = beforeDate + editDate;
			afterTime = beforeTime + editTime;
		} else {
			// 廃棄設定
			afterDate = beforeDate - editDate;
			afterTime = beforeTime - editTime;
		}
		// ストック付与日数設定
		if (vo.getPltEditStockType().equals("1")) {
			// 付与設定
			afterStockDate = beforeStockDate + editStockDate;
		} else {
			// 廃棄設定
			afterStockDate = beforeStockDate - editStockDate;
		}
		vo.setLblAfterFormerDate(String.valueOf(afterFormerDate));
		vo.setLblAfterFormerTime(String.valueOf(afterFormerTime));
		vo.setLblAfterDate(String.valueOf(afterDate));
		vo.setLblAfterTime(String.valueOf(afterTime));
		vo.setLblAfterStockDate(String.valueOf(afterStockDate));
		
		vo.setLblAfterTotalDate(String.valueOf(afterFormerDate + afterDate + afterStockDate));
		vo.setLblAfterTotalTime(String.valueOf(afterFormerTime + afterTime));
		
		vo.setClaAfterTotalDate(setHistoryDateTimeDoubleColor(afterFormerDate + afterDate + afterStockDate));
		vo.setClaAfterTotalTime(setHistoryDateTimeDoubleColor(afterFormerTime + afterTime));
		vo.setClaAfterFormerDate(setHistoryDateTimeDoubleColor(afterFormerDate));
		vo.setClaAfterFormerTime(setHistoryDateTimeDoubleColor(afterFormerTime));
		vo.setClaAfterDate(setHistoryDateTimeDoubleColor(afterDate));
		vo.setClaAfterTime(setHistoryDateTimeDoubleColor(afterTime));
		vo.setClaAfterStockDate(setHistoryDateTimeDoubleColor(afterStockDate));
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getAryPersonalId(getTransferredIndex()));
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(PaidHolidayReferenceAction.class.getName())) {
			// 選択表示
			mospParams.setNextCommand(PaidHolidayReferenceAction.CMD_SELECT_SHOW);
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
	 * @throws MospException  比較クラスのインスタンス生成に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 入力された社員コードから社員名の設定を行う。<br>
	 * <br>
	 * 個人ID、社員名、モードを、VOに設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void setEmployeeCode() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 現在の社員コードモードを確認
		if (vo.getJsModeEmployeeCode().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			Date activateDate = getEditActivateDate();
			// 取得した社員コードとシステム日付から個人IDを取得する
			HumanDtoInterface humanDto = reference().human().getHumanInfoForEmployeeCode(vo.getTxtEditEmployeeCode(),
					activateDate);
			if (humanDto == null) {
				// 取得したユーザIDに該当する人事マスタのデータがNULLなら処理終了
				// 検索結果無しメッセージ設定
				addNotExistEmployeesErrorMessage();
				return;
			}
			// 検索時に使用する個人IDを取得
			String personalId = humanDto.getPersonalId();
			vo.setPersonalId(personalId);
			// 社員コードを取得
			String employeeCode = humanDto.getEmployeeCode();
			// 入社情報チェック
			if (!reference().entrance().isEntered(personalId, activateDate)) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeNotEntered(mospParams, activateDate, employeeCode);
				return;
			}
			// 退社情報チェック
			if (reference().retirement().isRetired(personalId, activateDate)) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeRetired(mospParams, activateDate, employeeCode);
				return;
			}
			// 設定適用マスタ情報存在チェック
			ApplicationDtoInterface appDto = timeReference().application().findForPerson(personalId, activateDate);
			timeReference().application().chkExistApplication(appDto, activateDate);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 有給休暇マスタ情報取得
			PaidHolidayDtoInterface paidDto = timeReference().paidHoliday()
				.getPaidHolidayInfo(appDto.getPaidHolidayCode(), activateDate);
			if (paidDto == null) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.paidHoliday(mospParams));
				return;
			}
			// 時間単位有休機能フラグ確認
			if (paidDto.getTimelyPaidHolidayFlag() == 0) {
				// 時間単位取得が有効
				vo.setJsModeGivingtime(true);
			} else {
				// 時間単位取得が無効
				vo.setJsModeGivingtime(false);
			}
			// 検索した社員名を設定する
			vo.setLblEditEmployeeName(getLastFirstName(humanDto.getLastName(), humanDto.getFirstName()));
			// 社員コード決定モード設定
			vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 社員コード決定モード設定
			vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// 初期化する
			vo.setPersonalId("");
			vo.setLblEditEmployeeName("");
			vo.setLblBeforeTotalDate("0");
			vo.setLblBeforeTotalTime("0");
			vo.setLblBeforeFormerDate("0");
			vo.setLblBeforeFormerTime("0");
			vo.setLblBeforeDate("0");
			vo.setLblBeforeTime("0");
			vo.setLblBeforeStockDate("0");
			vo.setTxtEditFormerGivingDay("0");
			vo.setTxtEditFormerGivingTime("0");
			vo.setTxtEditGivingDay("0");
			vo.setTxtEditGivingTime("0");
			vo.setTxtEditGivingStockDay("0");
			vo.setLblAfterTotalDate(String.valueOf(0D));
			vo.setLblAfterTotalTime(String.valueOf(0));
			vo.setLblAfterFormerDate(String.valueOf(0D));
			vo.setLblAfterFormerTime(String.valueOf(0));
			vo.setLblAfterDate(String.valueOf(0D));
			vo.setLblAfterTime(String.valueOf(0));
			vo.setLblAfterStockDate(String.valueOf(0D));
			vo.setPltEditFormerGivingType("0");
			vo.setPltEditGivingType("0");
			vo.setPltEditStockType("0");
			vo.setJsModeGivingtime(false);
		}
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 */
	protected void insertMode() {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		// 初期値設定
		setDefaultValues();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * <br>
	 * 対象個人ID、社員コード、社員名、有効日、各種モード、各種休暇トランザクションを、
	 * VOに設定する。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 取得した社員コードとシステム日付から個人IDを取得する
		String personalId = getTransferredCode();
		Date date = getDate(getTransferredActivateDate());
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, date);
		// 取得したユーザIDに該当する人事マスタのデータが存在しない場合
		if (MospUtility.isEmpty(humanDto)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoItem(mospParams, PfNameUtility.employee(mospParams));
			// モード設定
			vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
			// 処理終了
			return;
		}
		// 有効日/社員コードを設定
		vo.setTxtEditActivateYear(getStringYear(date));
		vo.setTxtEditActivateMonth(getStringMonth(date));
		vo.setTxtEditActivateDay(getStringDay(date));
		vo.setTxtEditEmployeeCode(humanDto.getEmployeeCode());
		vo.setPersonalId(personalId);
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(personalId, date);
		// モード設定
		vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
		// 検索した社員名を設定する
		vo.setLblEditEmployeeName(getLastFirstName(humanDto.getLastName(), humanDto.getFirstName()));
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			// プルダウン取得
			setPulldown();
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// プルダウン設定
			vo.setAryPltSearchWorkPlace(getInputActivateDatePulldown());
			vo.setAryPltSearchEmployment(getInputActivateDatePulldown());
			vo.setAryPltSearchSection(getInputActivateDatePulldown());
			vo.setAryPltSearchPosition(getInputActivateDatePulldown());
		}
	}
	
	/**
	 * 新規登録処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void insert() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 登録クラス取得
		PaidHolidayTransactionRegistBeanInterface regist = time().paidHolidayTransactionRegist();
		// 基準日と締状態のチェック
		if (checkCutoffDayRecord() == false) {
			return;
		}
		
		// ==================================== 今年度設定 ==================================== //
		
		// 今年度設定
		PaidHolidayDataDtoInterface dataDto = getPaidHolidayDataDto(getEditActivateDate());
		if (dataDto == null) {
			// 社員コード決定モード設定
			vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			addSalariedVacationExceptErrorMessage();
			return;
		}
		// DTOの準備
		PaidHolidayTransactionDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto, dataDto.getAcquisitionDate());
		// 登録処理
		regist.insert(dto);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 有給休暇データ取得
		PaidHolidayDataDtoInterface paidHolidayDataDto = timeReference().paidHolidayData()
			.getPaidHolidayDataInfo(dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate());
		// 有給休暇情報が存在しない場合は、新規登録
		if (paidHolidayDataDto == null) {
			time().paidHolidayDataRegist().insert(dataDto);
		}
		
		// ==================================== 前年度設定 ==================================== //
		
		// 前年度設定
		PaidHolidayDataDtoInterface formerDataDto = getPaidHolidayDataDto(
				DateUtility.addYear(getEditActivateDate(), -1));
		// 前年度設定情報が取得できない場合は、日数ゼロのときのみ登録処理を行う
		if (formerDataDto == null) {
			// 付与・破棄する場合
			if (Double.parseDouble(vo.getTxtEditFormerGivingDay()) > 0
					|| Integer.parseInt(vo.getTxtEditFormerGivingTime()) > 0) {
				// 社員コード決定モード設定
				vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				addSalariedVacationExceptErrorMessage();
				return;
			}
			// DTOの準備
			PaidHolidayTransactionDtoInterface dtoFormer = regist.getInitDto();
			// DTOに値を設定
			setDtoFieldsFormer(dtoFormer, DateUtility.addYear(dataDto.getAcquisitionDate(), -1));
			// 登録処理
			regist.insert(dtoFormer);
			// 有給情報取得
			PaidHolidayDataDtoInterface formerPaidHolidayDataDto = timeReference().paidHolidayData()
				.getPaidHolidayDataInfo(dtoFormer.getPersonalId(), dtoFormer.getActivateDate(),
						dtoFormer.getAcquisitionDate());
			// 有給情報が取得できない場合
			if (formerPaidHolidayDataDto == null) {
				// 前年度の有給休暇データがないため仮のデータを作成
				formerDataDto = time().paidHolidayDataRegist().getInitDto();
				formerDataDto.setPersonalId(dataDto.getPersonalId());
				formerDataDto.setActivateDate(getEditActivateDate());
				formerDataDto.setAcquisitionDate(DateUtility.addYear(dataDto.getAcquisitionDate(), -1));
				formerDataDto.setLimitDate(DateUtility.addYear(dataDto.getLimitDate(), -1));
				formerDataDto.setTemporaryFlag(1);
				// 新規登録
				time().paidHolidayDataRegist().insert(formerDataDto);
			}
		} else {
			// DTOの準備
			PaidHolidayTransactionDtoInterface dtoFormer = regist.getInitDto();
			// DTOに値を設定
			setDtoFieldsFormer(dtoFormer, formerDataDto.getAcquisitionDate());
			// 登録処理
			regist.insert(dtoFormer);
			// 有給休暇情報取得
			PaidHolidayDataDtoInterface formerPaidHolidayDataDto = timeReference().paidHolidayData()
				.getPaidHolidayDataInfo(dtoFormer.getPersonalId(), dtoFormer.getActivateDate(),
						dtoFormer.getAcquisitionDate());
			// 有給休暇情報が存在しない場合
			if (formerPaidHolidayDataDto == null) {
				// 新規登録
				time().paidHolidayDataRegist().insert(formerDataDto);
			}
		}
		
		// ==================================== ストック休暇設定 ==================================== //
		
		// ストック休暇設定
		StockHolidayDataDtoInterface stockDataDto = getStockHolidayDataDto(getEditActivateDate(), null);
		// ストック休暇情報が取得できない場合
		if (stockDataDto == null) {
			// 日数を付与・破棄する場合
			if (getDouble(vo.getTxtEditGivingStockDay()) > 0) {
				// 社員コード決定モード設定
				vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				addNotStockVacationGrantErrorMessage();
				return;
			}
			stockDataDto = time().stockHolidayDataRegist().getInitDto();
			setStockHolidayDataDtoFields(stockDataDto, dataDto);
			// DTOの準備
			StockHolidayTransactionDtoInterface dtoStock = time().stockHolidayTransactionRegist().getInitDto();
			// DTOに値を設定
			setDtoFieldsStock(dtoStock, stockDataDto.getAcquisitionDate());
			// 登録処理
			time().stockHolidayTransactionRegist().insert(dtoStock);
			// ストック休暇データ取得
			StockHolidayDataDtoInterface stockHolidayDataDto = timeReference().stockHolidayData()
				.getStockHolidayDataInfo(dtoStock.getPersonalId(), dtoStock.getActivateDate(),
						dtoStock.getAcquisitionDate());
			// ストック休暇情報が存在しない場合
			if (stockHolidayDataDto == null) {
				// 新規登録
				time().stockHolidayDataRegist().insert(stockDataDto);
			}
		} else {
			// DTOの準備
			StockHolidayTransactionDtoInterface dtoStock = time().stockHolidayTransactionRegist().getInitDto();
			// DTOに値を設定
			setDtoFieldsStock(dtoStock, stockDataDto.getAcquisitionDate());
			// 登録処理
			time().stockHolidayTransactionRegist().insert(dtoStock);
			StockHolidayDataDtoInterface stockHolidayDataDto = timeReference().stockHolidayData()
				.getStockHolidayDataInfo(dtoStock.getPersonalId(), dtoStock.getActivateDate(),
						dtoStock.getAcquisitionDate());
			// ストック休暇情報が存在しない又は期限日が異なる場合
			if (stockHolidayDataDto == null) {
				// 新規登録
				time().stockHolidayDataRegist().insert(stockDataDto);
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
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageNewInsertSucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getPersonalId(), dto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 社員コード設定
		vo.setTxtSearchEmployeeCode(vo.getTxtEditEmployeeCode());
		// 検索
		search();
	}
	
	/**
	 * 履歴編集処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void update() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 登録クラス取得
		PaidHolidayTransactionRegistBeanInterface regist = time().paidHolidayTransactionRegist();
		// 基準日と前月締日の日付チェック
		if (checkCutoffDayRecord() == false) {
			return;
		}
		
		// 今年度設定
		// ※今年度有給情報は登録処理にいかないと後続処理に影響が出るため、チェック未設定
		PaidHolidayDataDtoInterface dataDto = getPaidHolidayDataDto(getEditActivateDate());
		if (dataDto == null) {
			// 社員コード決定モード設定
			vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			addNotWithPayVacationGrantErrorMessage();
			return;
		}
		// DTOの準備
		PaidHolidayTransactionDtoInterface dto = null;
		// 有給休暇トランザクションリスト取得
		List<PaidHolidayTransactionDtoInterface> list = timeReference().paidHolidayTransaction()
			.findForList(vo.getPersonalId(), getEditActivateDate());
		// 有給休暇トランザクションリスト毎に処理
		for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : list) {
			// 取得日が同じ場合
			if (paidHolidayTransactionDto.getAcquisitionDate().compareTo(dataDto.getAcquisitionDate()) == 0) {
				// 設定
				dto = paidHolidayTransactionDto;
				break;
			}
		}
		// 情報がない場合
		if (dto == null) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// DTOに値を設定
		setDtoFields(dto, dataDto.getAcquisitionDate());
		// 履歴編集処理
		regist.update(dto);
		// 前年度に付与・破棄する場合
		if (Double.parseDouble(vo.getTxtEditFormerGivingDay()) > 0
				|| Integer.parseInt(vo.getTxtEditFormerGivingTime()) > 0) {
			// 前年度設定
			PaidHolidayDataDtoInterface formerDataDto = getPaidHolidayDataDto(
					DateUtility.addYear(getEditActivateDate(), -1));
			if (formerDataDto == null) {
				// 社員コード決定モード設定
				vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				addNotWithPayVacationGrantErrorMessage();
				return;
			}
			// DTOの準備
			PaidHolidayTransactionDtoInterface dtoFormer = null;
			// 有給休暇トランザクションリスト毎に処理
			for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : list) {
				// 取得日が同じ場合
				if (paidHolidayTransactionDto.getAcquisitionDate().compareTo(formerDataDto.getAcquisitionDate()) == 0) {
					// 設定
					dtoFormer = paidHolidayTransactionDto;
					break;
				}
			}
			// 情報がない場合
			if (dtoFormer == null) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// DTOに値を設定
			setDtoFieldsFormer(dtoFormer, formerDataDto.getAcquisitionDate());
			// 履歴編集処理
			regist.update(dtoFormer);
			
		}
		// 付与するストック休暇を付与・破棄する場合
		if (getDouble(vo.getTxtEditGivingStockDay()) > 0) {
			// ストック情報取得用取得日設定
			Date acquisitionDate = DateUtility.addDay(dataDto.getLimitDate(), 1);
			// 有効日が付与日より後になるまで処理
			while (getEditActivateDate().before(acquisitionDate)) {
				// 付与日減算
				acquisitionDate = DateUtility.addYear(acquisitionDate, -1);
			}
			// ストック設定
			StockHolidayDataDtoInterface stockDataDto = getStockHolidayDataDto(getEditActivateDate(), acquisitionDate);
			if (stockDataDto == null) {
				// 社員コード決定モード設定
				vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				addNotStockVacationGrantErrorMessage();
				return;
			}
			// DTOの準備
			StockHolidayTransactionDtoInterface dtoStock = timeReference().stockHolidayTransaction()
				.findForKey(vo.getPersonalId(), getEditActivateDate(), stockDataDto.getAcquisitionDate());
			// DTOに値を設定
			setDtoFieldsStock(dtoStock, stockDataDto.getAcquisitionDate());
			// 履歴編集処理
			time().stockHolidayTransactionRegist().update(dtoStock);
			// 履歴編集結果確認
			if (mospParams.hasErrorMessage()) {
				// 更新失敗メッセージを設定
				PfMessageUtility.addMessageUpdateFailed(mospParams);
				return;
			}
		}
		// コミット
		commit();
		// 履歴編集成功メッセージを設定
		PfMessageUtility.addMessageEditHistorySucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getPersonalId(), dto.getActivateDate());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 社員コード設定
		vo.setTxtSearchEmployeeCode(vo.getTxtEditEmployeeCode());
		// 検索
		search();
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 個人ID初期化
		vo.setPersonalId("");
		// システム日付取得
		Date date = getSystemDate();
		// 検索項目設定
		vo.setTxtEditActivateYear(getStringYear(date));
		vo.setTxtEditActivateMonth(getStringMonth(date));
		vo.setTxtEditActivateDay(getStringDay(date));
		
		vo.setTxtEditEmployeeCode("");
		vo.setLblEditEmployeeName("");
		vo.setPltEditInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		vo.setTxtSearchActivateYear(getStringYear(date));
		vo.setTxtSearchActivateMonth(getStringMonth(date));
		vo.setTxtSearchActivateDay(getStringDay(date));
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchEmployeeName("");
		
		vo.setPltSearchWorkPlace("");
		vo.setPltSearchEmployment("");
		vo.setPltSearchSection("");
		vo.setPltSearchPosition("");
		
		vo.setTxtEditFormerGivingDay("0");
		vo.setTxtEditFormerGivingTime("0");
		vo.setTxtEditGivingDay("0");
		vo.setTxtEditGivingTime("0");
		vo.setTxtEditGivingStockDay("0");
		
		vo.setLblAfterTotalDate(String.valueOf(0D));
		vo.setLblAfterTotalTime(String.valueOf(0));
		vo.setLblAfterFormerDate(String.valueOf(0D));
		vo.setLblAfterFormerTime(String.valueOf(0));
		vo.setLblAfterDate(String.valueOf(0D));
		vo.setLblAfterTime(String.valueOf(0));
		vo.setLblAfterStockDate(String.valueOf(0D));
		
		vo.setTxtUpdateActivateYear(getStringYear(date));
		vo.setTxtUpdateActivateMonth(getStringMonth(date));
		vo.setTxtUpdateActivateDay(getStringDay(date));
		vo.setPltUpdateInactivate(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		vo.setJsModeGivingtime(false);
		vo.setClaAfterTotalDate("");
		vo.setClaAfterTotalTime("");
		vo.setClaAfterFormerDate("");
		vo.setClaAfterFormerTime("");
		vo.setClaAfterDate("");
		vo.setClaAfterTime("");
		vo.setClaAfterStockDate("");
		
		// 初期化(前年度）
		setDefaltValuesFormer();
		// 初期化(今年度）
		setDefaltValuesFields();
		// 初期化(ストック）
		setDefaltValuesStock();
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 社員コードモード設定
		vo.setJsModeEmployeeCode(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
	}
	
	/**
	 * VO(編集項目)を初期化する。（前年度）<br>
	 */
	public void setDefaltValuesFormer() {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTmtPaidHolidayFormerId(0L);
		vo.setLblBeforeFormerDate("0");
		vo.setLblBeforeFormerTime("0");
		vo.setClaBeforeFormerDate("");
		vo.setClaBeforeFormerTime("");
		vo.setPltEditFormerGivingType("0");
	}
	
	/**
	 * VO(編集項目)を初期化する。（今年度）<br>
	 */
	public void setDefaltValuesFields() {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTmtPaidHolidayId(0L);
		vo.setLblBeforeDate("0");
		vo.setLblBeforeTime("0");
		vo.setPltEditGivingType("0");
		vo.setClaBeforeDate("");
		vo.setClaBeforeTime("");
	}
	
	/**
	 * VO(編集項目)を初期化する。（ストック）<br>
	 */
	public void setDefaltValuesStock() {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTmtStockHolidayId(0L);
		vo.setLblBeforeStockDate("0");
		vo.setClaBeforeStockDate("");
		vo.setPltEditStockType("0");
		vo.setLblBeforeTotalDate("0");
		vo.setLblBeforeTotalTime("0");
		vo.setClaBeforeTotalDate("");
		vo.setClaBeforeTotalTime("");
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setPulldown() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// プルダウンの設定
		// 付与区分プルダウン
		MospProperties properties = mospParams.getProperties();
		String[][] aryGivingType = properties.getCodeArray(TimeConst.CODE_GIVING_TYPE, false);
		vo.setAryPltFormerGivingType(aryGivingType);
		vo.setAryPltGivingType(aryGivingType);
		vo.setAryPltStockType(aryGivingType);
		// システム日付取得
		Date date = getSystemDate();
		// 勤務地
		String[][] aryWorkPlace = reference().workPlace().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchWorkPlace(aryWorkPlace);
		// 雇用契約
		String[][] aryEmployment = reference().employmentContract().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchEmployment(aryEmployment);
		// 所属
		String[][] arySection = reference().section().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchSection(arySection);
		// 職位
		String[][] aryPosition = reference().position().getCodedSelectArray(date, true, null);
		vo.setAryPltSearchPosition(aryPosition);
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// データ配列初期化
		long[] aryCkbRecordId = new long[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblPersonalId = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblFormerDate = new String[list.size()];
		String[] aryLblFormerTime = new String[list.size()];
		String[] aryLblDate = new String[list.size()];
		String[] aryLblTime = new String[list.size()];
		String[] aryLblStockDate = new String[list.size()];
		String[] aryClaFormerDate = new String[list.size()];
		String[] aryClaFormerTime = new String[list.size()];
		String[] aryClaDate = new String[list.size()];
		String[] aryClaTime = new String[list.size()];
		String[] aryClaStockDate = new String[list.size()];
		
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			PaidHolidayHistoryListDtoInterface dto = (PaidHolidayHistoryListDtoInterface)list.get(i);
			SectionReferenceBeanInterface getSection = reference().section();
			Date date = getSystemDate();
			// 配列に情報を設定
			aryCkbRecordId[i] = dto.getTmdPaidHolidayHistoryListId();
			aryLblActivateDate[i] = DateUtility.getStringDate(dto.getActivateDate());
			aryLblPersonalId[i] = dto.getPersonalId();
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblSection[i] = getSection.getSectionAbbr(dto.getSectionCode(), date);
			aryLblFormerDate[i] = getNumberString(dto.getFormerDate(), 1);
			aryClaFormerDate[i] = setHistoryDateTimeDoubleColor(dto.getFormerDate());
			aryLblFormerTime[i] = String.valueOf(dto.getFormerTime());
			aryClaFormerTime[i] = setHistoryDateTimeColor(dto.getFormerTime());
			aryLblDate[i] = getNumberString(dto.getDate(), 1);
			aryClaDate[i] = setHistoryDateTimeDoubleColor(dto.getDate());
			aryLblTime[i] = String.valueOf(dto.getTime());
			aryClaTime[i] = setHistoryDateTimeColor(dto.getTime());
			aryLblStockDate[i] = getNumberString(dto.getStockDate(), 1);
			aryClaStockDate[i] = setHistoryDateTimeDoubleColor(dto.getStockDate());
		}
		// データをVOに設定
		vo.setAryCkbRecordId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryPersonalId(aryLblPersonalId);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblFormerDate(aryLblFormerDate);
		vo.setAryLblFormerTime(aryLblFormerTime);
		vo.setAryLblDate(aryLblDate);
		vo.setAryLblTime(aryLblTime);
		vo.setAryLblStockDate(aryLblStockDate);
		vo.setAryClaFormerDate(aryClaFormerDate);
		vo.setAryClaFormerTime(aryClaFormerTime);
		vo.setAryClaDate(aryClaDate);
		vo.setAryClaTime(aryClaTime);
		vo.setAryClaStockDate(aryClaStockDate);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。（今年度）<br>
	 * @param dto 対象DTO
	 * @param acquisitionDate 取得日
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFields(PaidHolidayTransactionDtoInterface dto, Date acquisitionDate) throws MospException {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmtPaidHolidayId(vo.getTmtPaidHolidayId());
		dto.setActivateDate(getEditActivateDate());
		dto.setPersonalId(vo.getPersonalId());
		dto.setInactivateFlag(MospConst.INACTIVATE_FLAG_OFF);
		if (vo.getPltEditGivingType().equals("1")) {
			// 付与設定
			dto.setGivingDay(dto.getGivingDay() + getDouble(vo.getTxtEditGivingDay()));
			dto.setGivingHour(dto.getGivingHour() + getInt(vo.getTxtEditGivingTime()));
		} else {
			// 廃棄設定
			dto.setCancelDay(dto.getCancelDay() + getDouble(vo.getTxtEditGivingDay()));
			dto.setCancelHour(dto.getCancelHour() + getInt(vo.getTxtEditGivingTime()));
		}
		dto.setAcquisitionDate(acquisitionDate);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。（今年度）<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	public void setVoFields(PaidHolidayTransactionDtoInterface dto) throws MospException {
		double date = 0.0;
		int time = 0;
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTmtPaidHolidayId(dto.getTmtPaidHolidayId());
		date = dto.getGivingDay() - dto.getCancelDay();
		time = dto.getGivingHour() - dto.getCancelHour();
		vo.setLblBeforeDate(getNumberString(date, 1));
		vo.setLblBeforeTime(String.valueOf(time));
		vo.setPltEditGivingType("1");
		vo.setClaBeforeDate(setHistoryDateTimeDoubleColor(date));
		vo.setClaBeforeTime(setHistoryDateTimeDoubleColor(time));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。（前年度）<br>
	 * @param dto 対象DTO
	 * @param acquisitionDate 取得日
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFieldsFormer(PaidHolidayTransactionDtoInterface dto, Date acquisitionDate)
			throws MospException {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmtPaidHolidayId(vo.getTmtPaidHolidayFormerId());
		dto.setActivateDate(getEditActivateDate());
		dto.setPersonalId(vo.getPersonalId());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		if (vo.getPltEditFormerGivingType().equals("1")) {
			// 付与設定
			double day = dto.getGivingDay() + getDouble(vo.getTxtEditFormerGivingDay());
			dto.setGivingDay(day);
			int hour = dto.getGivingHour() + getInt(vo.getTxtEditFormerGivingTime());
			dto.setGivingHour(hour);
		} else {
			// 廃棄設定
			double day = dto.getCancelDay() + getDouble(vo.getTxtEditFormerGivingDay());
			dto.setCancelDay(day);
			int hour = dto.getCancelHour() + getInt(vo.getTxtEditFormerGivingTime());
			dto.setCancelHour(hour);
		}
		dto.setAcquisitionDate(acquisitionDate);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。（前年度）<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	public void setVoFieldsFormer(PaidHolidayTransactionDtoInterface dto) throws MospException {
		double date = 0.0;
		int time = 0;
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTmtPaidHolidayFormerId(dto.getTmtPaidHolidayId());
		date = dto.getGivingDay() - dto.getCancelDay();
		time = dto.getGivingHour() - dto.getCancelHour();
		vo.setLblBeforeFormerDate(getNumberString(date, 1));
		vo.setLblBeforeFormerTime(String.valueOf(time));
		vo.setClaBeforeFormerDate(setHistoryDateTimeDoubleColor(date));
		vo.setClaBeforeFormerTime(setHistoryDateTimeDoubleColor(time));
		vo.setPltEditFormerGivingType("1");
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。（ストック）<br>
	 * @param dto 対象DTO
	 * @param acquisitionDate 取得日
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFieldsStock(StockHolidayTransactionDtoInterface dto, Date acquisitionDate)
			throws MospException {
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmtStockHolidayId(vo.getTmtStockHolidayId());
		dto.setActivateDate(getEditActivateDate());
		dto.setPersonalId(vo.getPersonalId());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		double givingDay = 0.0;
		if (vo.getPltEditStockType().equals("1")) {
			// 付与設定
			givingDay = dto.getGivingDay() + getDouble(vo.getTxtEditGivingStockDay());
			dto.setGivingDay(givingDay);
		} else {
			// 廃棄設定
			givingDay = dto.getCancelDay() + getDouble(vo.getTxtEditGivingStockDay());
			dto.setCancelDay(givingDay);
		}
		dto.setAcquisitionDate(acquisitionDate);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。（ストック）<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	public void setVoFieldsStock(StockHolidayTransactionDtoInterface dto) throws MospException {
		double date = 0.0;
		double totalDate = 0.0;
		int totalTime = 0;
		// VO取得
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTmtStockHolidayId(dto.getTmtStockHolidayId());
		date = dto.getGivingDay() - dto.getCancelDay();
		vo.setLblBeforeStockDate(getNumberString(date, 1));
		vo.setClaBeforeStockDate(setHistoryDateTimeDoubleColor(date));
		vo.setPltEditStockType("1");
		// 合計値の計算
		totalDate = getDouble(vo.getLblBeforeFormerDate()) + getDouble(vo.getLblBeforeDate()) + date;
		totalTime = getInt(vo.getLblBeforeFormerTime()) + getInt(vo.getLblBeforeTime());
		vo.setLblBeforeTotalDate(getNumberString(totalDate, 1));
		vo.setLblBeforeTotalTime(String.valueOf(totalTime));
		vo.setClaBeforeTotalDate(setHistoryDateTimeDoubleColor(totalDate));
		vo.setClaBeforeTotalTime(setHistoryDateTimeDoubleColor(totalTime));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 個人IDと有効日で編集対象情報を取得する。<br>
	 * <br>
	 * 対象有給休暇トランザクション情報、対象ストック休暇トランザクション情報を、
	 * VOに設定する。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String personalId, Date activateDate) throws MospException {
		// 初期化(前年度）
		setDefaltValuesFormer();
		// 初期化(今年度）
		setDefaltValuesFields();
		// 初期化(ストック）
		setDefaltValuesStock();
		// 履歴編集対象取得
		List<PaidHolidayTransactionDtoInterface> list = timeReference().paidHolidayTransaction()
			.findForInfoList(personalId, activateDate, String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		PaidHolidayTransactionDtoInterface dto = null;
		// 検索結果がないならエラーとする
		if (list.isEmpty()) {
			// 有給休暇トランザクションテーブルに該当する社員のデータが無い場合は処理終了
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			dto = list.get(i);
			// 存在確認
			checkSelectedDataExist(dto);
			// VOにセット
			if (i == 0) {
				// 前年度
				setVoFieldsFormer(dto);
			} else if (i == 1) {
				// 今年度
				setVoFields(dto);
			}
		}
		// ストック休暇情報取得
		StockHolidayDataDtoInterface stockHolidayDataDto = getStockHolidayDataDto(activateDate, null);
		if (stockHolidayDataDto == null) {
			// 登録用DTOを取得
			stockHolidayDataDto = time().stockHolidayDataRegist().getInitDto();
			// 有給休暇情報DTO設定
			PaidHolidayDataDtoInterface paidHolidayDataDto = getPaidHolidayDataDto(activateDate);
			if (paidHolidayDataDto == null) {
				return;
			}
			// ストック情報DTO設定
			setStockHolidayDataDtoFields(stockHolidayDataDto, paidHolidayDataDto);
		}
		// ストック休暇トランザクション取得
		StockHolidayTransactionDtoInterface dtoStock = timeReference().stockHolidayTransaction()
			.getStockHolidayTransactionInfo(personalId, activateDate, stockHolidayDataDto.getAcquisitionDate());
		if (dtoStock == null) {
			return;
		}
		// 存在確認
		checkSelectedDataExist(dtoStock);
		// VOにセット
		setVoFieldsStock(dtoStock);
		// 対象社員付与履歴情報リスト取得
		List<PaidHolidayTransactionDtoInterface> grantHistoryList = timeReference().paidHolidayTransaction()
			.findForHistoryList(personalId);
		// 同月を抜いた情報リスト準備
		List<PaidHolidayTransactionDtoInterface> notOverlapGrantHistoryList = new ArrayList<PaidHolidayTransactionDtoInterface>();
		// 対象社員付与履歴情報リスト毎に処理
		for (PaidHolidayTransactionDtoInterface transactionDto : grantHistoryList) {
			// 同じ有効日の場合
			if (isDate(transactionDto.getActivateDate(), notOverlapGrantHistoryList)) {
				continue;
			}
			// 追加
			notOverlapGrantHistoryList.add(transactionDto);
		}
		// 編集モード(履歴編集)設定
		setEditUpdateMode(notOverlapGrantHistoryList);
		// 有効日(編集)モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 付与日数/時間の再計算処理
		paidHolydayCalc();
	}
	
	/**
	 * 対象リストの中に既に同じ有効日の情報があるか確認する。
	 * @param targetDate 対象日付
	 * @param list 対象リスト
	 * @return 確認結果(true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isDate(Date targetDate, List<PaidHolidayTransactionDtoInterface> list) throws MospException {
		// リストがない場合
		if (list.isEmpty()) {
			return false;
		}
		// 対象リスト毎に処理
		for (PaidHolidayTransactionDtoInterface dto : list) {
			// 同じ日付の場合
			if (targetDate.equals(dto.getActivateDate())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 前月締日の日付チェック。<br>
	 * @return 結果成否
	 * @throws MospException 例外発生時
	 */
	protected boolean checkCutoffDayRecord() throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 個人ID及び対象日を取得
		String personalId = vo.getPersonalId();
		// 個人ID及び対象日から締月取得
		int[] calcMonth = getCalcMonth(personalId, getEditActivateDate());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		// 社員勤怠集計管理から締状態を取得
		Integer state = timeReference().totalTimeEmployeeTransaction().getCutoffState(personalId, calcMonth[0],
				calcMonth[1]);
		if (state == null) {
			return true;
		}
		int intState = state.intValue();
		if (intState == 0) {
			// 未締の場合
			return true;
		} else if (intState == 1) {
			// 仮締の場合
			StringBuffer sb = new StringBuffer();
			sb.append(calcMonth[0]);
			sb.append(mospParams.getName("Year"));
			sb.append(calcMonth[1]);
			sb.append(mospParams.getName("Month"));
			mospParams.addErrorMessage(TimeMessageConst.MSG_MONTHLY_TREATMENT, sb.toString(),
					mospParams.getName("GrantDate"));
			return false;
		}
		return false;
	}
	
	/**
	 * 対象個人IDに対して、対象日付が含まれる締月を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param date 対象日付
	 * @return 締月配列(1列目：年、2列目：月)
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected int[] getCalcMonth(String personalId, Date date) throws MospException {
		// 設定適用エンティティを取得
		ApplicationEntity application = timeReference().master().getApplicationEntity(personalId, date);
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			return new int[0];
		}
		// 対象日付が含まれる締月の初日を取得
		Date cutoffMounth = application.getCutoffEntity().getCutoffMonth(date, mospParams);
		// 締月配列の設定
		return new int[]{ DateUtility.getYear(cutoffMounth), DateUtility.getMonth(cutoffMounth) };
	}
	
	/**
	 * ストック情報DTO設定
	 * @param dto ストック休暇情報DTO
	 * @param paidHolidayDataDto 有給休暇情報DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	private void setStockHolidayDataDtoFields(StockHolidayDataDtoInterface dto,
			PaidHolidayDataDtoInterface paidHolidayDataDto) throws MospException {
		// 個人ID準備
		String personalId = paidHolidayDataDto.getPersonalId();
		// 有効日設定
		Date activateDate = getEditActivateDate();
		// 個人ID及び対象日から、適用されている設定を取得
		ApplicationDtoInterface applicationDto = timeReference().application().findForPerson(personalId, activateDate);
		// 設定適用マスタの存在チェック。
		timeReference().application().chkExistApplication(applicationDto, getEditActivateDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 有給休暇コードと対象日から有給休暇マスタを取得
		PaidHolidayDtoInterface paidHolidayDto = timeReference().paidHoliday()
			.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), getEditActivateDate());
		// 有給休暇マスタの存在チェック
		timeReference().paidHoliday().chkExistPaidHoliday(paidHolidayDto, getEditActivateDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// ストック休暇マスタ取得
		StockHolidayDtoInterface stockHolidayDto = timeReference().stockHoliday()
			.getStockHolidayInfo(paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
		if (stockHolidayDto == null) {
			addNotStockVacationGrantErrorMessage();
			return;
		}
		dto.setPersonalId(personalId);
		dto.setActivateDate(activateDate);
		// 日付操作する
		Date acquisitionDate = DateUtility.addDay(paidHolidayDataDto.getLimitDate(), 1);
		// 
		while (getEditActivateDate().before(acquisitionDate)) {
			acquisitionDate = DateUtility.addYear(acquisitionDate, -1);
		}
		dto.setAcquisitionDate(acquisitionDate);
		dto.setLimitDate(DateUtility
			.addDay(DateUtility.addMonth(dto.getAcquisitionDate(), stockHolidayDto.getStockLimitDate()), -1));
	}
	
	/**
	 * 有給休暇情報DTO設定
	 * @param activateDate 有効日
	 * @return 有給休暇情報DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	private PaidHolidayDataDtoInterface getPaidHolidayDataDto(Date activateDate) throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		PaidHolidayDataGrantBeanInterface paidHolidayDataGrant = time().paidHolidayDataGrant();
		// 個人ID
		String personalId = vo.getPersonalId();
		// 入社日取得
		Date entranceDate = reference().entrance().getEntranceDate(personalId);
		if (entranceDate == null) {
			addNotJoinedGrantErrorMessage();
			return null;
		}
		// 設定適用マスタ取得
		ApplicationDtoInterface applicationDto = timeReference().application().findForPerson(personalId,
				getEditActivateDate());
		timeReference().application().chkExistApplication(applicationDto, getEditActivateDate());
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 有給休暇マスタ取得
		PaidHolidayDtoInterface paidHolidayDto = timeReference().paidHoliday()
			.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), getEditActivateDate());
		timeReference().paidHoliday().chkExistPaidHoliday(paidHolidayDto, getEditActivateDate());
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 付与区分
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日
			return getPaidHolidayDataDtoForPoint(paidHolidayDto, entranceDate, activateDate);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月
			return getPaidHolidayDataDtoForEntranceMonth(paidHolidayDto, entranceDate, activateDate);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日
			return getPaidHolidayDataDtoForEntranceDate(paidHolidayDto, entranceDate, activateDate);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_NOT) {
			// 対象外
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY) {
			// 比例
			return paidHolidayDataGrant.create(vo.getPersonalId(), activateDate, false);
		}
		return null;
	}
	
	/**
	 * 有給休暇情報DTO取得(付与区分：基準日)
	 * @param paidHolidayDto 有給休暇情報DTO
	 * @param entranceDate 入社日
	 * @param activateDate 有効日
	 * @return 有給休暇情報DTO
	 * @throws MospException 例外発生時
	 */
	protected PaidHolidayDataDtoInterface getPaidHolidayDataDtoForPoint(PaidHolidayDtoInterface paidHolidayDto,
			Date entranceDate, Date activateDate) throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// DTO初期化
		PaidHolidayDataDtoInterface dto = time().paidHolidayDataRegist().getInitDto();
		dto.setPersonalId(vo.getPersonalId());
		dto.setTemporaryFlag(1);
		
		int pointDay = paidHolidayDto.getPointDateDay();
		// 基準日準備
		Date pointDate = DateUtility.getDate(DateUtility.getYear(entranceDate), paidHolidayDto.getPointDateMonth(),
				pointDay);
		if (!entranceDate.before(pointDate)) {
			// 入社日が基準日より前でない場合は基準日に1年を加算する
			pointDate = DateUtility.addYear(pointDate, 1);
		}
		// 有給休暇初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = timeReference().paidHolidayFirstYear().findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(entranceDate));
		Date firstYearAcquisitionDate = null;
		if (paidHolidayFirstYearDto != null) {
			// 初年度付与マスタが存在する場合
			// 初年度付与日取得(入社月の基準日から付与月を加算)
			firstYearAcquisitionDate = DateUtility
				.addDay(DateUtility.addMonth(
						MonthUtility.getYearMonthTermFirstDate(DateUtility.getYear(entranceDate),
								DateUtility.getMonth(entranceDate), mospParams),
						paidHolidayFirstYearDto.getGivingMonth()), pointDay - 1);
			if (paidHolidayFirstYearDto.getGivingAmount() > 0) {
				// 初年度付与マスタの付与日数が0より大きい場合
				Date acquisitionDate = firstYearAcquisitionDate;
				Date limitDate = DateUtility.addDay(
						DateUtility.addMonth(firstYearAcquisitionDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
				if (limitDate.before(firstYearAcquisitionDate)) {
					limitDate = firstYearAcquisitionDate;
				}
				if (!pointDate.after(acquisitionDate)) {
					// 基準日が初年度付与日より後でない場合は基準日に1年を加算する
					pointDate = DateUtility.addYear(pointDate, 1);
				}
				
				if (activateDate.before(acquisitionDate)) {
					// 有効日が初年度付与日より前の場合
					return null;
				} else if (!activateDate.after(limitDate) && activateDate.before(pointDate)) {
					// 有効日が初年度付与日以降で初年度期限日より後でなく
					// 且つ有効日が基準日より前の場合
					dto.setActivateDate(acquisitionDate);
					dto.setAcquisitionDate(acquisitionDate);
					dto.setLimitDate(limitDate);
					return dto;
				} else if (activateDate.before(pointDate)) {
					// 有効日が初年度期限日より後で且つ
					// 有効日が次の基準日より前の場合
					return null;
				}
			}
		}
		// 返却DTO準備
		while (pointDate.before(activateDate)) {
			// 基準日が有効日より前の場合
			pointDate = DateUtility.addYear(pointDate, 1);
		}
		if (pointDate.compareTo(activateDate) != 0) {
			// 基準日と有効日が同じでない場合
			pointDate = DateUtility.addYear(pointDate, -1);
		}
		if (firstYearAcquisitionDate != null && pointDate.before(firstYearAcquisitionDate)) {
			// 基準日が初年度付与日より前の場合
			return null;
		}
		dto.setActivateDate(pointDate);
		dto.setAcquisitionDate(pointDate);
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
			// 有休繰越が有効の場合は期限は2年
			dto.setLimitDate(DateUtility.addDay(DateUtility.addYear(pointDate, 2), -1));
		} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
			// 有休繰越が無効の場合は期限は1年
			dto.setLimitDate(DateUtility.addDay(DateUtility.addYear(pointDate, 1), -1));
		}
		return dto;
	}
	
	/**
	 * 有給休暇情報DTO取得(付与区分：入社月)
	 * @param paidHolidayDto 有給休暇情報DTO
	 * @param entranceDate 入社日
	 * @param activateDate 有効日
	 * @return 有給休暇情報DTO
	 * @throws MospException 例外処理が発生した場合
	 */
	protected PaidHolidayDataDtoInterface getPaidHolidayDataDtoForEntranceMonth(PaidHolidayDtoInterface paidHolidayDto,
			Date entranceDate, Date activateDate) throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 入社年・入社月取得
		int entranceYear = DateUtility.getYear(entranceDate);
		int entranceMonth = DateUtility.getMonth(entranceDate);
		ApplicationDtoInterface applicationDto = timeReference().application().findForPerson(vo.getPersonalId(),
				getEditActivateDate());
		if (applicationDto == null) {
			return null;
		}
		TimeSettingDtoInterface timeSettingDto = timeReference().timeSetting()
			.getTimeSettingInfo(applicationDto.getWorkSettingCode(), getEditActivateDate());
		if (timeSettingDto == null) {
			return null;
		}
		CutoffDtoInterface cutoffDto = timeReference().cutoff().getCutoffInfo(timeSettingDto.getCutoffCode(),
				getEditActivateDate());
		if (cutoffDto == null) {
			return null;
		}
		
		// DTO初期化
		PaidHolidayDataDtoInterface dto = time().paidHolidayDataRegist().getInitDto();
		dto.setPersonalId(vo.getPersonalId());
		dto.setTemporaryFlag(1);
		
		Date pointDate = DateUtility
			.addDay(MonthUtility.getYearMonthTermLastDate(entranceYear, entranceMonth, mospParams), 1);
		int cutoffDate = cutoffDto.getCutoffDate();
		if (cutoffDate != 0) {
			// 締日が末日でない場合
			pointDate = DateUtility.addDay(DateUtility.getDate(entranceYear, entranceMonth, cutoffDate), 1);
		}
		
		List<PaidHolidayEntranceDateDtoInterface> list = timeReference().paidHolidayEntranceDate()
			.findForList(paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
		Date nextAcquisitionDate = null;
		// 有給休暇初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = timeReference().paidHolidayFirstYear().findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(entranceDate));
		if (paidHolidayFirstYearDto != null && paidHolidayFirstYearDto.getGivingAmount() > 0) {
			// 初年度付与マスタが存在し且つ付与日数が0より大きい場合
			// 初年度付与日取得(入社月の締日の翌日から付与月を加算)
			Date acquisitionDate = DateUtility.addMonth(pointDate, paidHolidayFirstYearDto.getGivingMonth());
			Date limitDate = DateUtility
				.addDay(DateUtility.addMonth(acquisitionDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
			Date maxDate = pointDate;
			for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
				Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
				if (!acquisitionDate.before(workDate)) {
					continue;
				}
				if (maxDate.before(workDate)) {
					maxDate = workDate;
				}
				if (nextAcquisitionDate == null || nextAcquisitionDate.after(workDate)) {
					nextAcquisitionDate = workDate;
				}
			}
			while (nextAcquisitionDate == null) {
				maxDate = DateUtility.addMonth(maxDate, paidHolidayDto.getGeneralJoiningMonth());
				if (!acquisitionDate.before(maxDate)) {
					continue;
				}
				nextAcquisitionDate = maxDate;
			}
			if (activateDate.before(acquisitionDate)) {
				// 有効日が初年度付与日より前の場合
				return null;
			} else if (!activateDate.after(limitDate) && activateDate.before(nextAcquisitionDate)) {
				// 有効日が初年度付与日以降で初年度期限日より後でなく
				// 且つ有効日が次の付与日より前の場合
				dto.setActivateDate(acquisitionDate);
				dto.setAcquisitionDate(acquisitionDate);
				dto.setLimitDate(limitDate);
				return dto;
			} else if (activateDate.before(nextAcquisitionDate)) {
				// 有効日が初年度期限日より後で且つ
				// 有効日が次の付与日より前の場合
				return null;
			}
		}
		Date acquisitionDate = null;
		Date maxDate = pointDate;
		Date maxWorkDate = null;
		// 返却DTO準備
		for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
			Date workDate = DateUtility.addMonth(pointDate, paidHolidayEntranceDateDto.getWorkMonth());
			if (maxDate.before(workDate)) {
				maxDate = workDate;
			}
			if (!workDate.after(activateDate)) {
				if (maxWorkDate == null || maxWorkDate.before(workDate)) {
					maxWorkDate = workDate;
				}
			}
		}
		if (maxWorkDate.equals(maxDate)) {
			while (true) {
				Date nextDate = DateUtility.addMonth(maxDate, paidHolidayDto.getGeneralJoiningMonth());
				if (!maxDate.after(activateDate) && nextDate.after(activateDate)) {
					acquisitionDate = maxDate;
					break;
				}
				maxDate = nextDate;
			}
		} else {
			acquisitionDate = maxWorkDate;
		}
		dto.setActivateDate(acquisitionDate);
		dto.setAcquisitionDate(acquisitionDate);
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
			// 有休繰越が有効の場合は期限は2年
			dto.setLimitDate(DateUtility.addDay(DateUtility.addYear(acquisitionDate, 2), -1));
		} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
			// 有休繰越が無効の場合は期限は1年
			dto.setLimitDate(DateUtility.addDay(DateUtility.addYear(acquisitionDate, 1), -1));
		}
		return dto;
	}
	
	/**
	 * 有給休暇情報DTO取得(付与区分：入社日)
	 * @param paidHolidayDto 有給休暇情報DTO
	 * @param entranceDate 入社日
	 * @param activateDate 有効日
	 * @return 有給休暇情報DTO
	 * @throws MospException 例外処理が発生した場合
	 */
	protected PaidHolidayDataDtoInterface getPaidHolidayDataDtoForEntranceDate(PaidHolidayDtoInterface paidHolidayDto,
			Date entranceDate, Date activateDate) throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// DTO初期化
		PaidHolidayDataDtoInterface dto = time().paidHolidayDataRegist().getInitDto();
		dto.setPersonalId(vo.getPersonalId());
		dto.setTemporaryFlag(1);
		
		List<PaidHolidayEntranceDateDtoInterface> list = timeReference().paidHolidayEntranceDate()
			.findForList(paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
		Date nextAcquisitionDate = null;
		// 有給休暇初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = timeReference().paidHolidayFirstYear().findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(entranceDate));
		if (paidHolidayFirstYearDto != null && paidHolidayFirstYearDto.getGivingAmount() > 0) {
			// 初年度付与マスタが存在し且つ付与日数が0より大きい場合
			// 初年度付与日取得(入社日から付与月を加算)
			Date acquisitionDate = DateUtility.addMonth(entranceDate, paidHolidayFirstYearDto.getGivingMonth());
			Date limitDate = DateUtility
				.addDay(DateUtility.addMonth(acquisitionDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
			Date maxDate = entranceDate;
			for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
				Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
				if (!acquisitionDate.before(workDate)) {
					continue;
				}
				if (maxDate.before(workDate)) {
					maxDate = workDate;
				}
				if (nextAcquisitionDate == null || nextAcquisitionDate.after(workDate)) {
					nextAcquisitionDate = workDate;
				}
			}
			while (nextAcquisitionDate == null) {
				maxDate = DateUtility.addMonth(maxDate, paidHolidayDto.getGeneralJoiningMonth());
				if (!acquisitionDate.before(maxDate)) {
					continue;
				}
				nextAcquisitionDate = maxDate;
			}
			if (activateDate.before(acquisitionDate)) {
				// 有効日が初年度付与日より前の場合
				return null;
			} else if (!activateDate.after(limitDate) && activateDate.before(nextAcquisitionDate)) {
				// 有効日が初年度付与日以降で初年度期限日より後でなく
				// 且つ有効日が次の付与日より前の場合
				dto.setActivateDate(acquisitionDate);
				dto.setAcquisitionDate(acquisitionDate);
				dto.setLimitDate(limitDate);
				return dto;
			} else if (activateDate.before(nextAcquisitionDate)) {
				// 有効日が初年度期限日より後で且つ
				// 有効日が次の付与日より前の場合
				return null;
			}
		}
		Date acquisitionDate = null;
		Date maxDate = entranceDate;
		Date maxWorkDate = null;
		// 返却DTO準備
		for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
			Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
			if (maxDate.before(workDate)) {
				maxDate = workDate;
			}
			if (!workDate.after(activateDate)) {
				if (maxWorkDate == null || maxWorkDate.before(workDate)) {
					maxWorkDate = workDate;
				}
			}
		}
		if (maxWorkDate.equals(maxDate)) {
			while (true) {
				Date nextDate = DateUtility.addMonth(maxDate, paidHolidayDto.getGeneralJoiningMonth());
				if (!maxDate.after(activateDate) && nextDate.after(activateDate)) {
					acquisitionDate = maxDate;
					break;
				}
				maxDate = nextDate;
			}
		} else {
			acquisitionDate = maxWorkDate;
		}
		dto.setActivateDate(acquisitionDate);
		dto.setAcquisitionDate(acquisitionDate);
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
			// 有休繰越が有効の場合は期限は2年
			dto.setLimitDate(DateUtility.addDay(DateUtility.addYear(acquisitionDate, 2), -1));
		} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
			// 有休繰越が無効の場合は期限は1年
			dto.setLimitDate(DateUtility.addDay(DateUtility.addYear(acquisitionDate, 1), -1));
		}
		return dto;
	}
	
	/**
	 * ストック休暇情報取得
	 * @param activateDate 有効日
	 * @param editAcquisitionDate 取得日(履歴編集用)
	 * @return ストック休暇情報DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	private StockHolidayDataDtoInterface getStockHolidayDataDto(Date activateDate, Date editAcquisitionDate)
			throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// 個人ID
		String personalId = vo.getPersonalId();
		// 入社情報DTO取得
		EntranceDtoInterface entranceDto = reference().entrance().getEntranceInfo(personalId);
		if (entranceDto == null) {
			addNotJoinedGrantErrorMessage();
			return null;
		}
		// 設定適用マスタDTO取得
		ApplicationDtoInterface applicationDto = timeReference().application().findForPerson(personalId,
				getEditActivateDate());
		timeReference().application().chkExistApplication(applicationDto, getEditActivateDate());
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 有給休暇情報DTO取得
		PaidHolidayDtoInterface paidHolidayDto = timeReference().paidHoliday()
			.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), getEditActivateDate());
		timeReference().paidHoliday().chkExistPaidHoliday(paidHolidayDto, getEditActivateDate());
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ストック休暇DTO取得
		StockHolidayDtoInterface stockHolidayDto = timeReference().stockHoliday()
			.getStockHolidayInfo(paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
		if (stockHolidayDto == null) {
			addNotStockVacationGrantErrorMessage();
			return null;
		}
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日
			return getStockHolidayDataDtoForPoint(entranceDto.getEntranceDate(), paidHolidayDto, stockHolidayDto,
					activateDate, editAcquisitionDate);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月
			return getStockHolidayDataDtoForEntranceMonth(entranceDto.getEntranceDate(), paidHolidayDto,
					stockHolidayDto, activateDate, editAcquisitionDate);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日
			return getStockHolidayDataDtoForEntranceDate(entranceDto.getEntranceDate(), paidHolidayDto, stockHolidayDto,
					activateDate, editAcquisitionDate);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY) {
			// 比例
			return getStockHolidayDataDtoForProportionally(stockHolidayDto, activateDate);
		}
		return null;
	}
	
	/**
	 * ストック休暇情報DTO取得
	 * @param entranceDate 入社日
	 * @param paidHolidayDto  有給休暇情報DTO
	 * @param stockHolidayDto ストック休暇情報DTO
	 * @param activateDate    有効日
	 * @param editAcquisitionDate 有効日(履歴編集モード用)
	 * @return ストック休暇情報DTO
	 * @throws MospException 例外発生時
	 */
	protected StockHolidayDataDtoInterface getStockHolidayDataDtoForPoint(Date entranceDate,
			PaidHolidayDtoInterface paidHolidayDto, StockHolidayDtoInterface stockHolidayDto, Date activateDate,
			Date editAcquisitionDate) throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// クラス準備
		PaidHolidayFirstYearReferenceBeanInterface firstYearRefer = timeReference().paidHolidayFirstYear();
		StockHolidayDataReferenceBeanInterface stockHolidayDataRefer = timeReference().stockHolidayData();
		// 有給休暇設定：有効日、コード取得
		String paidHolidayCode = paidHolidayDto.getPaidHolidayCode();
		Date paidHolidayActivateDate = paidHolidayDto.getActivateDate();
		// 基準日日付取得
		int pointDay = paidHolidayDto.getPointDateDay();
		// 基準日作成
		Date pointDate = DateUtility.getDate(DateUtility.getYear(entranceDate), paidHolidayDto.getPointDateMonth(),
				pointDay);
		// 入社日が基準日より前でない場合
		if (!entranceDate.before(pointDate)) {
			// 基準日に1年を加算する
			pointDate = DateUtility.addYear(pointDate, 1);
		}
		// 有給休暇期限日準備
		Date paidHolidayLimitDate = null;
		// 有給休暇初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = firstYearRefer.findForKey(paidHolidayCode,
				paidHolidayActivateDate, DateUtility.getMonth(entranceDate));
		// 初年度付与期限日準備
		Date firstYearAcquisitionDate = null;
		// 初年度付与マスタが存在する場合
		if (paidHolidayFirstYearDto != null) {
			// 初年度付与日取得(入社月の基準日から付与月を加算)
			firstYearAcquisitionDate = DateUtility
				.addDay(DateUtility.addMonth(
						MonthUtility.getYearMonthTermFirstDate(DateUtility.getYear(entranceDate),
								DateUtility.getMonth(entranceDate), mospParams),
						paidHolidayFirstYearDto.getGivingMonth()), pointDay - 1);
			// 初年度付与マスタの付与日数が0より大きい場合
			if (paidHolidayFirstYearDto.getGivingAmount() > 0) {
				// 付与日設定
				Date acquisitionDate = firstYearAcquisitionDate;
				// 有給期限日を設定
				paidHolidayLimitDate = DateUtility.addDay(
						DateUtility.addMonth(firstYearAcquisitionDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
				// 
				if (paidHolidayLimitDate.before(firstYearAcquisitionDate)) {
					paidHolidayLimitDate = firstYearAcquisitionDate;
				}
				// 基準日が初年度付与日より後でない場合
				if (!pointDate.after(acquisitionDate)) {
					// 基準日に1年を加算する
					pointDate = DateUtility.addYear(pointDate, 1);
				}
				// 有効日が初年度期限日より後でない場合
				if (!activateDate.after(paidHolidayLimitDate)) {
					StockHolidayDataDtoInterface stockDataDto = null;
					// 取得日(履歴編集のみ)が存在する場合
					if (editAcquisitionDate != null) {
						// ストック休暇データ取得
						stockDataDto = stockHolidayDataRefer.findForKey(vo.getPersonalId(), getEditActivateDate(),
								editAcquisitionDate);
					}
					return stockDataDto;
				}
			}
		}
		// 
		Date paidHolidayAfterLimitDate = null;
		// 値を返すまで処理
		while (true) {
			// 有休繰越取得
			int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
			// 有休繰越が有効（期限2年）の場合
			if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
				// 2年後の前日に設定
				paidHolidayAfterLimitDate = DateUtility.addDay(DateUtility.addYear(pointDate, 2), -1);
			} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
				// 有休繰越が無効（期限1年）の場合
				// 1年後の前日に設定
				paidHolidayAfterLimitDate = DateUtility.addDay(DateUtility.addYear(pointDate, 1), -1);
			} else {
				StockHolidayDataDtoInterface stockDataDto = null;
				// 取得日(履歴編集のみ)が存在する場合
				if (editAcquisitionDate != null) {
					// ストック休暇データ取得
					stockDataDto = stockHolidayDataRefer.findForKey(vo.getPersonalId(), getEditActivateDate(),
							editAcquisitionDate);
				}
				return stockDataDto;
			}
			if (paidHolidayLimitDate == null && !activateDate.after(paidHolidayAfterLimitDate)) {
				StockHolidayDataDtoInterface stockDataDto = null;
				// 取得日(履歴編集のみ)が存在する場合
				if (editAcquisitionDate != null) {
					// ストック休暇データ取得
					stockDataDto = stockHolidayDataRefer.findForKey(vo.getPersonalId(), getEditActivateDate(),
							editAcquisitionDate);
				}
				return stockDataDto;
			}
			// 期限日があり、有給休暇期限日より有効日が後であり、
			if (paidHolidayLimitDate != null && activateDate.after(paidHolidayLimitDate)
					&& !activateDate.after(paidHolidayAfterLimitDate)) {
				// ストック休暇データ準備
				StockHolidayDataDtoInterface dto = time().stockHolidayDataRegist().getInitDto();
				// ストック休暇データ設定
				dto.setPersonalId(vo.getPersonalId());
				dto.setAcquisitionDate(DateUtility.addDay(paidHolidayLimitDate, 1));
				dto.setActivateDate(dto.getAcquisitionDate());
				dto.setLimitDate(DateUtility
					.addDay(DateUtility.addMonth(dto.getAcquisitionDate(), stockHolidayDto.getStockLimitDate()), -1));
				return dto;
			}
			// 1年を加算
			pointDate = DateUtility.addYear(pointDate, 1);
			paidHolidayLimitDate = paidHolidayAfterLimitDate;
		}
	}
	
	/**
	 * ストック休暇情報DTO取得
	 * @param entranceDate 入社日
	 * @param paidHolidayDto 有給休暇情報DTO
	 * @param stockHolidayDto ストック休暇情報DTO
	 * @param activateDate 有効日
	 * @param editAcquisitionDate 有効日(履歴編集モード用)
	 * @return ストック休暇情報DTO
	 * @throws MospException 例外処理が発生した場合
	 */
	protected StockHolidayDataDtoInterface getStockHolidayDataDtoForEntranceMonth(Date entranceDate,
			PaidHolidayDtoInterface paidHolidayDto, StockHolidayDtoInterface stockHolidayDto, Date activateDate,
			Date editAcquisitionDate) throws MospException {
		// 入社年・入社月取得
		int entranceYear = DateUtility.getYear(entranceDate);
		int entranceMonth = DateUtility.getMonth(entranceDate);
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		// クラス準備
		ApplicationReferenceBeanInterface applicationRefer = timeReference().application();
		TimeSettingReferenceBeanInterface timeSettingRefer = timeReference().timeSetting();
		CutoffReferenceBeanInterface cutOffRefer = timeReference().cutoff();
		PaidHolidayEntranceDateReferenceBeanInterface paidHolidayEntranceDate = timeReference()
			.paidHolidayEntranceDate();
		StockHolidayDataReferenceBeanInterface stockHolidayData = timeReference().stockHolidayData();
		// 個人ID取得
		String personalId = vo.getPersonalId();
		// 有給休暇設定：有効日、コード取得
		String paidHolidayCode = paidHolidayDto.getPaidHolidayCode();
		Date paidHolidayActivateDate = paidHolidayDto.getActivateDate();
		ApplicationDtoInterface applicationDto = applicationRefer.findForPerson(personalId, getEditActivateDate());
		if (applicationDto == null) {
			return null;
		}
		TimeSettingDtoInterface timeSettingDto = timeSettingRefer
			.getTimeSettingInfo(applicationDto.getWorkSettingCode(), getEditActivateDate());
		if (timeSettingDto == null) {
			return null;
		}
		CutoffDtoInterface cutoffDto = cutOffRefer.getCutoffInfo(timeSettingDto.getCutoffCode(), getEditActivateDate());
		if (cutoffDto == null) {
			return null;
		}
		
		Date pointDate = DateUtility
			.addDay(MonthUtility.getYearMonthTermLastDate(entranceYear, entranceMonth, mospParams), 1);
		int cutoffDate = cutoffDto.getCutoffDate();
		if (cutoffDate != 0) {
			// 締日が末日でない場合
			pointDate = DateUtility.addDay(DateUtility.getDate(entranceYear, entranceMonth, cutoffDate), 1);
		}
		
		List<PaidHolidayEntranceDateDtoInterface> list = paidHolidayEntranceDate.findForList(paidHolidayCode,
				paidHolidayActivateDate);
		Date nextAcquisitionDate = null;
		Date paidHolidayLimitDate = null;
		// 有給休暇初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = timeReference().paidHolidayFirstYear()
			.findForKey(paidHolidayCode, paidHolidayActivateDate, DateUtility.getMonth(entranceDate));
		if (paidHolidayFirstYearDto != null && paidHolidayFirstYearDto.getGivingAmount() > 0) {
			// 初年度付与マスタが存在し且つ付与日数が0より大きい場合
			// 初年度付与日取得(入社月の基準日から付与月を加算)
			Date acquisitionDate = DateUtility.addMonth(pointDate, paidHolidayFirstYearDto.getGivingMonth());
			paidHolidayLimitDate = DateUtility
				.addDay(DateUtility.addMonth(acquisitionDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
			Date maxDate = entranceDate;
			for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
				Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
				if (!acquisitionDate.before(workDate)) {
					continue;
				}
				if (maxDate.before(workDate)) {
					maxDate = workDate;
				}
				if (nextAcquisitionDate == null || nextAcquisitionDate.after(workDate)) {
					nextAcquisitionDate = workDate;
				}
			}
			while (nextAcquisitionDate == null) {
				maxDate = DateUtility.addMonth(maxDate, paidHolidayDto.getGeneralJoiningMonth());
				if (!acquisitionDate.before(maxDate)) {
					continue;
				}
				nextAcquisitionDate = maxDate;
			}
			if (!activateDate.after(paidHolidayLimitDate)) {
				// 有効日が初年度期限日より後でない場合
				StockHolidayDataDtoInterface stockDataDto = null;
				// 取得日(履歴編集のみ)が存在する場合
				if (editAcquisitionDate != null) {
					stockDataDto = stockHolidayData.findForKey(personalId, getEditActivateDate(), editAcquisitionDate);
				}
				return stockDataDto;
			}
		}
		Date maxDate = pointDate;
		Date maxWorkDate = null;
		Date maxLimitDate = null;
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		// 返却DTO準備
		for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
			Date workDate = DateUtility.addMonth(pointDate, paidHolidayEntranceDateDto.getWorkMonth());
			if (maxDate.before(workDate)) {
				maxDate = workDate;
			}
			// 有休繰越が無効の場合は期限は1年
			Date limitDate = DateUtility.addDay(DateUtility.addYear(workDate, 1), -1);
			// 有休繰越が有効の場合は期限は2年
			if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
				limitDate = DateUtility.addDay(DateUtility.addYear(workDate, 2), -1);
			}
			if (!limitDate.after(activateDate) && (maxLimitDate == null || maxLimitDate.before(limitDate))) {
				maxLimitDate = limitDate;
				maxWorkDate = workDate;
			}
		}
		if (maxWorkDate != null) {
			if (maxWorkDate.equals(maxDate)) {
				while (true) {
					Date nextDate = DateUtility.addMonth(maxDate, paidHolidayDto.getGeneralJoiningMonth());
					Date nextLimitDate = null;
					if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
						// 有休繰越が有効の場合は期限は2年
						nextLimitDate = DateUtility.addDay(DateUtility.addYear(nextDate, 2), -1);
					} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
						// 有休繰越が無効の場合は期限は1年
						nextLimitDate = DateUtility.addDay(DateUtility.addYear(nextDate, 1), -1);
					}
					if (!maxLimitDate.after(activateDate) && nextLimitDate.after(activateDate)) {
						paidHolidayLimitDate = maxLimitDate;
						break;
					}
					maxLimitDate = nextLimitDate;
				}
			} else {
				paidHolidayLimitDate = maxLimitDate;
			}
		}
		StockHolidayDataDtoInterface dto = time().stockHolidayDataRegist().getInitDto();
		dto.setPersonalId(personalId);
		dto.setAcquisitionDate(DateUtility.addDay(paidHolidayLimitDate, 1));
		dto.setActivateDate(dto.getAcquisitionDate());
		dto.setLimitDate(DateUtility
			.addDay(DateUtility.addMonth(dto.getAcquisitionDate(), stockHolidayDto.getStockLimitDate()), -1));
		return dto;
	}
	
	/**
	 * ストック休暇情報DTO取得
	 * @param entranceDate 入社日
	 * @param paidHolidayDto 有給休暇情報DTO
	 * @param stockHolidayDto ストック休暇情報DTO
	 * @param activateDate 有効日
	 * @param editAcquisitionDate 有効日(履歴編集モード用)
	 * @return ストック休暇情報DTO
	 * @throws MospException 例外処理が発生した場合
	 */
	protected StockHolidayDataDtoInterface getStockHolidayDataDtoForEntranceDate(Date entranceDate,
			PaidHolidayDtoInterface paidHolidayDto, StockHolidayDtoInterface stockHolidayDto, Date activateDate,
			Date editAcquisitionDate) throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		
		List<PaidHolidayEntranceDateDtoInterface> list = timeReference().paidHolidayEntranceDate()
			.findForList(paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
		Date nextAcquisitionDate = null;
		Date paidHolidayLimitDate = null;
		// 有給休暇初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = timeReference().paidHolidayFirstYear().findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(entranceDate));
		if (paidHolidayFirstYearDto != null && paidHolidayFirstYearDto.getGivingAmount() > 0) {
			// 初年度付与マスタが存在し且つ付与日数が0より大きい場合
			// 初年度付与日取得(入社日から付与月を加算)
			Date acquisitionDate = DateUtility.addMonth(entranceDate, paidHolidayFirstYearDto.getGivingMonth());
			paidHolidayLimitDate = DateUtility
				.addDay(DateUtility.addMonth(acquisitionDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
			Date maxDate = entranceDate;
			for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
				Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
				if (!acquisitionDate.before(workDate)) {
					continue;
				}
				if (maxDate.before(workDate)) {
					maxDate = workDate;
				}
				if (nextAcquisitionDate == null || nextAcquisitionDate.after(workDate)) {
					nextAcquisitionDate = workDate;
				}
			}
			while (nextAcquisitionDate == null) {
				maxDate = DateUtility.addMonth(maxDate, paidHolidayDto.getGeneralJoiningMonth());
				if (!acquisitionDate.before(maxDate)) {
					continue;
				}
				nextAcquisitionDate = maxDate;
			}
			if (!activateDate.after(paidHolidayLimitDate)) {
				// 有効日が初年度期限日より後でない場合
				StockHolidayDataDtoInterface stockDataDto = null;
				// 取得日(履歴編集のみ)が存在する場合
				if (editAcquisitionDate != null) {
					stockDataDto = timeReference().stockHolidayData().findForKey(vo.getPersonalId(),
							getEditActivateDate(), editAcquisitionDate);
				}
				return stockDataDto;
			}
		}
		Date maxDate = entranceDate;
		Date maxWorkDate = null;
		Date maxLimitDate = null;
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		// 返却DTO準備
		for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
			Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
			if (maxDate.before(workDate)) {
				maxDate = workDate;
			}
			Date limitDate = null;
			if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
				// 有休繰越が有効の場合は期限は2年
				limitDate = DateUtility.addDay(DateUtility.addYear(workDate, 2), -1);
			} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
				// 有休繰越が無効の場合は期限は1年
				limitDate = DateUtility.addDay(DateUtility.addYear(workDate, 1), -1);
			}
			
			if (limitDate != null) {
				if (!limitDate.after(activateDate) && (maxLimitDate == null || maxLimitDate.before(limitDate))) {
					maxLimitDate = limitDate;
					maxWorkDate = workDate;
				}
			}
		}
		if (maxWorkDate != null) {
			if (maxWorkDate.equals(maxDate)) {
				while (true) {
					Date nextDate = DateUtility.addMonth(maxDate, paidHolidayDto.getGeneralJoiningMonth());
					Date nextLimitDate = null;
					if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
						// 有休繰越が有効の場合は期限は2年
						nextLimitDate = DateUtility.addDay(DateUtility.addYear(nextDate, 2), -1);
					} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
						// 有休繰越が無効の場合は期限は1年
						nextLimitDate = DateUtility.addDay(DateUtility.addYear(nextDate, 1), -1);
					}
					if (nextLimitDate != null && !maxLimitDate.after(activateDate)
							&& nextLimitDate.after(activateDate)) {
						paidHolidayLimitDate = maxLimitDate;
						break;
					}
					maxLimitDate = nextLimitDate;
				}
			} else {
				paidHolidayLimitDate = maxLimitDate;
			}
		}
		StockHolidayDataDtoInterface dto = time().stockHolidayDataRegist().getInitDto();
		dto.setPersonalId(vo.getPersonalId());
		dto.setAcquisitionDate(DateUtility.addDay(paidHolidayLimitDate, 1));
		dto.setActivateDate(dto.getAcquisitionDate());
		dto.setLimitDate(DateUtility
			.addDay(DateUtility.addMonth(dto.getAcquisitionDate(), stockHolidayDto.getStockLimitDate()), -1));
		return dto;
	}
	
	/**
	 * 比例付与でのストック休暇情報DTOを取得する。<br>
	 * 有効日、付与日、期限日を取得する。<br>
	 * ストック情報がない場合、0日で設定する。<br>
	 * 初年度の有給休暇期限が切れていない場合、nullを返す。<br>
	 * @param stockHolidayDto ストック休暇情報DTO
	 * @param activateDate    有効日
	 * @return ストック休暇情報DTO
	 * @throws MospException 例外発生時
	 */
	protected StockHolidayDataDtoInterface getStockHolidayDataDtoForProportionally(
			StockHolidayDtoInterface stockHolidayDto, Date activateDate) throws MospException {
		// VO準備
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		PaidHolidayDataGrantBeanInterface paidHolidayDataGrant = time().paidHolidayDataGrant();
		// 個人ID取得
		String personalId = vo.getPersonalId();
		// 有給休暇付与回数を取得
		int grantTimes = paidHolidayDataGrant.getGrantTimes(personalId, activateDate);
		if (grantTimes == 0) {
			return null;
		}
		// 付与日取得
		Date grantDate = paidHolidayDataGrant.getGrantDate(personalId, grantTimes);
		if (grantDate == null) {
			return null;
		}
		// 有給休暇期限日取得
		Date paidHolidayLimitDate = paidHolidayDataGrant.getExpirationDate(personalId, grantDate, grantTimes);
		// 有給休暇期限日より有効日が同じ又は後になるまで処理
		while (activateDate.compareTo(paidHolidayLimitDate) <= 0) {
			// 付与回数-1
			grantTimes = grantTimes - 1;
			if (grantTimes == 0) {
				return null;
			}
			// 付与日再取得
			grantDate = paidHolidayDataGrant.getGrantDate(personalId, grantTimes);
			// 有給休暇期限日再取得
			paidHolidayLimitDate = paidHolidayDataGrant.getExpirationDate(personalId, grantDate, grantTimes);
		}
		// ストック付与日取得
		Date stockHolidayAcquisitionDate = DateUtility.addDay(paidHolidayLimitDate, 1);
		// ストック休暇データ取得
		StockHolidayDataDtoInterface dto = timeReference().stockHolidayData().findForKey(personalId, activateDate,
				stockHolidayAcquisitionDate);
		if (dto == null) {
			// ストック休暇データ準備
			dto = time().stockHolidayDataRegist().getInitDto();
			// ストック休暇データ設定
			dto.setPersonalId(vo.getPersonalId());
			dto.setAcquisitionDate(stockHolidayAcquisitionDate);
			dto.setActivateDate(dto.getAcquisitionDate());
			dto.setLimitDate(DateUtility
				.addDay(DateUtility.addMonth(dto.getAcquisitionDate(), stockHolidayDto.getStockLimitDate()), -1));
		}
		return dto;
	}
	
	/**
	 * 有給休暇有効期間以外を登録する場合のエラーメッセージ。<br>
	 */
	protected void addSalariedVacationExceptErrorMessage() {
		String mes = mospParams.getName("Salaried", "Holiday", "Effectiveness", "Term", "Except");
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REGISTER, mes);
	}
	
	/**
	 * 1年目にストック休暇が付与できない場合のエラーメッセージ。<br>
	 */
	protected void addNotStockVacationGrantErrorMessage() {
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		String mes1 = DateUtility.getStringDate(getEditActivateDate());
		String mes2 = vo.getTxtEditEmployeeCode();
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_STOCK_VACATION_GRANT, mes1, mes2);
	}
	
	/**
	 * 入社していない社員が選択されている場合のエラーメッセージ。<br>
	 */
	protected void addNotJoinedGrantErrorMessage() {
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		String mes1 = DateUtility.getStringDate(getEditActivateDate());
		String mes2 = vo.getTxtEditEmployeeCode();
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_JOINED_GRANT, mes1, mes2);
	}
	
	/**
	 * 入社1年目であるため前年度の有給休暇を付与する場合エラーメッセージ。<br>
	 */
	protected void addNotWithPayVacationGrantErrorMessage() {
		PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)mospParams.getVo();
		String mes1 = DateUtility.getStringDate(getEditActivateDate());
		String mes2 = vo.getTxtEditEmployeeCode();
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_WITH_PAY_VACATION_GRANT, mes1, mes2);
	}
}
