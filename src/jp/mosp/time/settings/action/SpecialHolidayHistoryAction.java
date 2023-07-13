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
package jp.mosp.time.settings.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.comparator.base.ActivateDateComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.HolidayDataRegistBeanInterface;
import jp.mosp.time.bean.HolidayHistorySearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayHistoryListDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdHolidayDataDto;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.SpecialHolidayHistoryVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * 各従業員に対して特別休暇の手動付与を行う。<br>
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
 * {@link #CMD_REGISTER}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_EMPLOYEE_DECISION}
 * </li><li>
 * {@link #CMD_SET_DAY_GRANT}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li></ul>
 */
public class SpecialHolidayHistoryAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW					= "TM4220";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 特別休暇確認画面で選択したレコードの情報を編集欄に入力した状態で編集モードにして画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW				= "TM4221";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を基に人事情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH					= "TM4222";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層にあたる画面からパンくずリスト等で再び遷移した際に登録内容等を反映させた上で自動で検索を行い、再表示させる。<br>
	 */
	public static final String	CMD_RE_SHOW					= "TM4223";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 編集欄に入力した内容を基に従業員別の特別休暇の保有数を管理するテーブルに登録する。<br>
	 * 有効日や付与・破棄の日数が入力されていない状態で登録を行おうとした場合は場合はエラーメッセージにて通知し、処理は実行されない。<br>
	 */
	public static final String	CMD_REGISTER				= "TM4225";
	
	/**
	 * 休暇情報反映コマンド。<br>
	 * <br>
	 * 休暇種別プルダウンで選択した休暇の標準付与日数と標準取得期限を取得し、それぞれのテキストボックスへ出力する。<br>
	 * このコマンドは休暇種別プルダウンの内容が変更される度に実行され、取得日数と取得期限の入力欄に随時反映される。<br>
	 */
	public static final String	CMD_SET_DAY_GRANT			= "TM4226";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT					= "TM4228";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE					= "TM4229";
	
	/**
	 * 社員コード決定コマンド。<br>
	 * <br>
	 * 入力した社員コードを基に社員名を取得し、社員名表示ラベルに出力する。<br>
	 */
	public static final String	CMD_SET_EMPLOYEE_DECISION	= "TM4270";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 編集テーブルの各入力欄に表示されているレコード内容をクリアにする。登録ボタンクリック時のコマンドを登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE				= "TM4271";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を編集テーブルの各入力欄にそれぞれ表示させ、編集モードへ切り替える。<br>
	 */
	public static final String	CMD_EDIT_MODE				= "TM4272";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 入力した有効日時点で有効な勤務地、雇用契約、所属、職位の情報を取得し、そのコードと名称をそれぞれのプルダウンに表示する。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE		= "TM4277";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public SpecialHolidayHistoryAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SpecialHolidayHistoryVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示コマンド
			prepareVo();
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_REGISTER)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_SET_DAY_GRANT)) {
			// 標準日数付与
			prepareVo();
			setDayGrant();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_EMPLOYEE_DECISION)) {
			// 社員コード決定
			prepareVo();
			setEmployeeDecision();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 編集対象決定
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
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
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 新規登録モード設定
		insertMode();
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ActivateDateComparator.class.getName());
	}
	
	/**
	 * 検索処理を行う。
	 * @throws MospException 例外処理発生時
	 */
	protected void select() throws MospException {
		// 編集モード設定
		try {
			// VO取得
			SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
			// ページ繰り設定
			setPageInfo(CMD_PAGE, getListLength());
			// デフォルトソートキー及びソート順設定
			vo.setComparatorName(ActivateDateComparator.class.getName());
			editMode();
			// 編集モード設定
			vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
		} catch (Exception e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 検索クラス取得
		HolidayHistorySearchBeanInterface search = timeReference().holidayHistorySearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setWorkPlaceCode(vo.getPltSearchWorkPlace());
		search.setEmploymentCode(vo.getPltSearchEmployment());
		search.setSectionCode(vo.getPltSearchSection());
		search.setPositionCode(vo.getPltSearchPosition());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<HolidayHistoryListDtoInterface> list = search.getSearchList(
				getInt(mospParams.getProperties().getCodeArray(TimeConst.CODE_KEY_HOLIDAY_TYPE_MASTER, false)[0][0]));
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ActivateDateComparator.class.getName());
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
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
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
	 * 新規登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// DTOの準備
		HolidayDataDtoInterface dto = new TmdHolidayDataDto();
		// 登録クラス取得
		HolidayDataRegistBeanInterface regist = time().holidayDataRegist();
		// DTOに値を設定
		setDtoFields(dto);
		// 登録処理
		regist.insert(dto);
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
		setEditUpdateMode(dto.getPersonalId(), dto.getActivateDate(), dto.getHolidayCode());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// DTOの準備
		HolidayDataDtoInterface dto = new TmdHolidayDataDto();
		// 登録クラス取得
		HolidayDataRegistBeanInterface regist = time().holidayDataRegist();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.update(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴編集成功メッセージを設定
		PfMessageUtility.addMessageEditHistorySucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getPersonalId(), dto.getActivateDate(), dto.getHolidayCode());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDayGrant() throws MospException {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		HolidayDtoInterface holidayDto = timeReference().holiday().getHolidayInfo(vo.getPltEditHolidayType(),
				getEditActivateDate(), TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		if (holidayDto != null) {
			vo.setTxtEditHolidayGiving(String.valueOf(holidayDto.getHolidayGiving()));
			vo.setTxtEditHolidayLimitMonth(String.valueOf(holidayDto.getHolidayLimitMonth()));
			vo.setTxtEditHolidayLimitDay(String.valueOf(holidayDto.getHolidayLimitDay()));
			// 無制限設定
			setJsEditNoLimit();
		} else {
			// 該当する休暇種別がない場合はエラーメッセージを出力しモードも変更しない。
			// 有効日モード設定
			vo.setJsEditActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			vo.setAryPltEditHolidayType(getInputActivateDatePulldown());
			vo.setTxtEditEmployeeCode("");
			vo.setLblEmployeeName("");
			// エラーメッセージ設定
			String message = mospParams.getName("Holiday", "Classification");
			mospParams.addMessage(TimeMessageConst.MSG_WORKFORM_EXISTENCE, message);
		}
	}
	
	/**
	 * 無制限設定。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setJsEditNoLimit() throws MospException {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		HolidayDtoInterface holidayDto = timeReference().holiday().getHolidayInfo(vo.getPltEditHolidayType(),
				getEditActivateDate(), TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		if (holidayDto == null || holidayDto.getNoLimit() != getInt(MospConst.CHECKBOX_ON)) {
			vo.setJsEditNoLimit(false);
			return;
		}
		vo.setJsEditNoLimit(true);
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
	 * @throws MospException 例外発生時
	 */
	protected void setEmployeeDecision() throws MospException {
		// VO準備
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getJsEditActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setJsEditActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setJsEditActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 社員コードの取得
		setEditEmployee();
		// 休暇種別の取得
		setEditDecision();
	}
	
	/**
	 * @throws MospException 例外発生時
	 */
	protected void setEditEmployee() throws MospException {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 有効日モード確認
		if (vo.getJsEditActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			Date activateDate = getEditActivateDate();
			// 社員コードの取得
			HumanDtoInterface humanDto = reference().human().getHumanInfoForEmployeeCode(vo.getTxtEditEmployeeCode(),
					activateDate);
			if (humanDto == null) {
				vo.setJsEditActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				vo.setLblEmployeeName(mospParams.getName("NoEmployee"));
				// 検索結果無しメッセージ設定
				addNotExistEmployeesErrorMessage();
				return;
			}
			// 個人ID及び社員コードを取得
			String personalId = humanDto.getPersonalId();
			String employeeCode = humanDto.getEmployeeCode();
			if (!reference().entrance().isEntered(personalId, activateDate)) {
				// 入社していない場合
				vo.setJsEditActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				vo.setLblEmployeeName(mospParams.getName("NoEmployee"));
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeNotEntered(mospParams, activateDate, employeeCode);
				return;
			}
			if (reference().retirement().isRetired(personalId, activateDate)) {
				// 退職している場合
				vo.setJsEditActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
				vo.setLblEmployeeName(mospParams.getName("NoEmployee"));
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeRetired(mospParams, activateDate, employeeCode);
				return;
			}
			vo.setLblEmployeeName(getLastFirstName(humanDto.getLastName(), humanDto.getFirstName()));
			// 個人IDをVOにセット
			vo.setPersonalId(personalId);
			return;
		}
		vo.setLblEmployeeName("");
	}
	
	/**
	 * @throws MospException 例外発生時
	 */
	protected void setEditDecision() throws MospException {
		setEditPulldown();
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// システム日付取得
		Date date = DateUtility.getSystemDate();
		// 検索項目設定
		vo.setTxtEditActivateYear(DateUtility.getStringYear(date));
		vo.setTxtEditActivateMonth(DateUtility.getStringMonth(date));
		vo.setTxtEditActivateDay(DateUtility.getStringDay(date));
		vo.setTxtEditEmployeeCode("");
		vo.setLblEmployeeName("");
		vo.setPltEditInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		vo.setTxtSearchActivateYear(DateUtility.getStringYear(date));
		vo.setTxtSearchActivateMonth(DateUtility.getStringMonth(date));
		vo.setTxtSearchActivateDay(DateUtility.getStringDay(date));
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchEmployeeName("");
		vo.setPltSearchWorkPlace("");
		vo.setPltSearchEmployment("");
		vo.setPltSearchSection("");
		vo.setPltSearchPosition("");
		vo.setPltSearchInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		vo.setTxtUpdateActivateYear(DateUtility.getStringYear(date));
		vo.setTxtUpdateActivateMonth(DateUtility.getStringMonth(date));
		vo.setTxtUpdateActivateDay(DateUtility.getStringDay(date));
		vo.setPltUpdateInactivate("");
		vo.setJsEditHistoryMode("");
		vo.setJsEditNoLimit(false);
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// VO準備
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		// jsのモード設定
		setJsMode();
		// 検索用プルダウンの設定
		setPulldown();
		// 編集用プルダウンの設定
		setEditPulldown();
	}
	
	/**
	 * @throws MospException 例外発生時
	 */
	protected void addMode() throws MospException {
		// 有効日取得
		Date activateDate = getDate(getTransferredActivateDate());
		// 個人ID取得
		String personalId = reference().human().getPersonalId(getTransferredCode(), activateDate);
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(personalId, activateDate, getTransferredGenericCode());
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setEditPulldown() throws MospException {
		// VO準備
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 休暇種別
		// 有効日モード確認
		if (vo.getJsEditActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// 休暇種別のプルダウン取得
			String[][] aryHolidayType = timeReference().holiday().getSelectArray(getEditActivateDate(),
					TimeConst.CODE_HOLIDAYTYPE_SPECIAL, false);
			vo.setAryPltEditHolidayType(aryHolidayType);
			if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
				vo.setPltEditHolidayType(aryHolidayType[0][0]);
				setDayGrant();
				return;
			}
			// 無制限設定
			setJsEditNoLimit();
			return;
		}
		// 付与日数/取得期限の初期化
		vo.setTxtEditHolidayGiving("");
		vo.setTxtEditHolidayLimitMonth("");
		vo.setTxtEditHolidayLimitDay("");
		vo.setAryPltEditHolidayType(getInputActivateDatePulldown());
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 例外発生時
	 */
	private void setPulldown() throws MospException {
		// VO準備
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 有効日モード確認
		if (!vo.getJsSearchActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// プルダウン設定
			vo.setAryPltSearchSection(getInputActivateDatePulldown());
			vo.setAryPltSearchPosition(getInputActivateDatePulldown());
			vo.setAryPltSearchEmployment(getInputActivateDatePulldown());
			vo.setAryPltSearchWorkPlace(getInputActivateDatePulldown());
			return;
		}
		// 有効日取得
		Date targetDate = getSearchActivateDate();
		// プルダウン取得及び設定
		vo.setAryPltSearchSection(reference().section().getCodedSelectArray(targetDate, true, null));
		vo.setAryPltSearchPosition(reference().position().getCodedSelectArray(targetDate, true, null));
		vo.setAryPltSearchEmployment(reference().employmentContract().getCodedSelectArray(targetDate, true, null));
		vo.setAryPltSearchWorkPlace(reference().workPlace().getCodedSelectArray(targetDate, true, null));
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO準備
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		setDefaultValues();
		String holidayCode = getTransferredGenericCode();
		Date activateDate = getDate(getTransferredActivateDate());
		// 個人ID取得
		String personalId = reference().human().getPersonalId(getTransferredCode(), activateDate);
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(personalId, activateDate, holidayCode);
		// 有効日をVOに設定(setEditEmployeeで用いるため)
		vo.setTxtEditActivateYear(DateUtility.getStringYear(activateDate));
		vo.setTxtEditActivateMonth(DateUtility.getStringMonth(activateDate));
		vo.setTxtEditActivateDay(DateUtility.getStringDay(activateDate));
		// 社員コードの取得
		setEditEmployee();
		// 登録クラス取得
		HolidayDataDtoInterface dto = timeReference().holidayData().findForKey(personalId, activateDate, holidayCode,
				TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		// 編集項目の表示
		vo.setTxtEditActivateYear(DateUtility.getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(DateUtility.getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(DateUtility.getStringDay(dto.getActivateDate()));
		vo.setTxtEditEmployeeCode(getEmployeeCode(dto.getPersonalId(), dto.getActivateDate()));
		vo.setTxtEditHolidayGiving(String.valueOf(dto.getGivingDay()));
		vo.setTxtEditHolidayLimitMonth(String.valueOf(dto.getHolidayLimitMonth()));
		vo.setTxtEditHolidayLimitDay(String.valueOf(dto.getHolidayLimitDay()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		// 休暇種別の取得
		setEditDecision();
		// 検索用プルダウンの設定
		setPulldown();
		vo.setJsEditHistoryMode(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * @throws MospException 例外発生時
	 */
	protected void setActivationDate() throws MospException {
		// VO準備
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getJsSearchActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setJsSearchActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setJsSearchActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		setPulldown();
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 勤務形態コードと有効日で編集対象情報を取得する。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param holidayCode 休暇コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String personalId, Date activateDate, String holidayCode) throws MospException {
		// VO準備
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 履歴編集対象取得
		HolidayDataDtoInterface dto = timeReference().holidayData().findForKey(personalId, activateDate, holidayCode,
				TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
		vo.setJsEditActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		vo.setJsSearchActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFields(HolidayDataDtoInterface dto) throws MospException {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmdHolidayId(vo.getTmdHolidayId());
		dto.setActivateDate(getEditActivateDate());
		dto.setPersonalId(vo.getPersonalId());
		dto.setGivingDay(Double.valueOf(vo.getTxtEditHolidayGiving()));
		dto.setGivingHour(0);
		// 取得期間が0ヶ月0日の場合、Date型の最大日付を設定する。
		if ("0".equals(vo.getTxtEditHolidayLimitMonth()) && "0".equals(vo.getTxtEditHolidayLimitDay())) {
			dto.setHolidayLimitDate(TimeUtility.getUnlimitedDate());
		} else {
			dto.setHolidayLimitDate(DateUtility.addDay(
					DateUtility.addMonth(getEditActivateDate(), getInt(vo.getTxtEditHolidayLimitMonth())),
					getInt(vo.getTxtEditHolidayLimitDay()) - 1));
		}
		dto.setHolidayLimitMonth(getInt(vo.getTxtEditHolidayLimitMonth()));
		dto.setHolidayLimitDay(getInt(vo.getTxtEditHolidayLimitDay()));
		dto.setHolidayType(TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		dto.setCancelDay(0);
		dto.setCancelHour(0);
		dto.setHolidayCode(vo.getPltEditHolidayType());
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setVoFields(HolidayDataDtoInterface dto) throws MospException {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTmdHolidayId(dto.getTmdHolidayId());
		vo.setTxtActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtEditEmployeeCode(getEmployeeCode(dto.getPersonalId(), dto.getActivateDate()));
		vo.setPltEditHolidayType(String.valueOf(dto.getHolidayCode()));
		vo.setTxtEditHolidayGiving(String.valueOf(dto.getGivingDay()));
		vo.setTxtEditHolidayLimitMonth(String.valueOf(dto.getHolidayLimitMonth()));
		vo.setTxtEditHolidayLimitDay(String.valueOf(dto.getHolidayLimitDay()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblHolidayCode = new String[list.size()];
		String[] aryLblHolidayType = new String[list.size()];
		String[] aryLblHolidayGiving = new String[list.size()];
		String[] aryLblHolidayLimit = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			HolidayHistoryListDtoInterface dto = (HolidayHistoryListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblSection[i] = reference().section().getSectionAbbr(dto.getSectionCode(), getSearchActivateDate());
			aryLblHolidayCode[i] = dto.getHolidayCode();
			aryLblHolidayType[i] = getHolidayAbbr(dto.getHolidayCode(), getSearchActivateDate(),
					TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
			if (TimeUtility.getUnlimitedDate().compareTo(dto.getHolidayLimit()) == 0) {
				aryLblHolidayGiving[i] = mospParams.getName("NoLimit");
				aryLblHolidayLimit[i] = mospParams.getName("NoLimit");
			} else {
				aryLblHolidayGiving[i] = getFormatDaysHoursMinutes(dto.getHolidayGiving(), 0, 0, false);
				aryLblHolidayLimit[i] = DateUtility.getStringDate(dto.getHolidayLimit());
			}
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblHolidayCode(aryLblHolidayCode);
		vo.setAryLblHolidayType(aryLblHolidayType);
		vo.setAryLblHolidayGiving(aryLblHolidayGiving);
		vo.setAryLblHolidayLimit(aryLblHolidayLimit);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
	/**
	 * JSのモードを設定する。<br>
	 */
	protected void setJsMode() {
		// VO取得
		SpecialHolidayHistoryVo vo = (SpecialHolidayHistoryVo)mospParams.getVo();
		// 有効日(編集)モード設定
		vo.setJsEditActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 有効日(検索)モード設定
		vo.setJsSearchActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
}
