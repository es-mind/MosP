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

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ApplicationRegistBeanInterface;
import jp.mosp.time.bean.CheckAvailableBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmApplicationDto;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.ApplicationCardVo;

/**
 * 勤怠管理、カレンダ、有給休暇についての設定対象を各種マスタに適用させる。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li></ul>
 */
public class ApplicationCardAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String		CMD_SHOW				= "TM5620";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 設定適用管理一覧画面で選択したレコードの情報を取得し、履歴編集モードで画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW			= "TM5621";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力項目に入力されている内容を基に新規登録モード、
	 * 履歴追加モードであれば登録処理を、履歴編集モードであれば更新処理を実行する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、または設定適用コードが
	 * 登録済みのレコードのものと同一であった場合、エラーメッセージを表示する。<br>
	 */
	public static final String		CMD_REGIST				= "TM5625";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 一覧表示欄の選択チェックボックスで選択されているレコードを対象に
	 * 論理削除を行うよう繰り返し処理を行う。<br>
	 */
	public static final String		CMD_DELETE				= "TM5627";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 勤務地・雇用契約・所属・職位・勤怠設定・有休設定・カレンダの各マスタを参照する。<br>
	 * それらのマスタからここで決定した有効日時点で有効なレコードのリストを作成し、
	 * 各マスタ情報毎にそれぞれのプルダウンに表示する。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE	= "TM5670";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 編集テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE			= "TM5671";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String		CMD_ADD_MODE			= "TM5673";
	
	/**
	 * コードキー(設定適用チェック処理)。<br>
	 */
	protected static final String	CODE_KEY_CHECK			= "CheckApplication";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public ApplicationCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ApplicationCardVo();
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
			editMode();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定コマンド
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替
			prepareVo();
			addMode();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理発生
	 */
	protected void show() throws MospException {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 新規追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		ApplicationCardVo vo = (ApplicationCardVo)mospParams.getVo();
		// 編集モード確認
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
			// 新規登録
			insert();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
			// 履歴追加
			add();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			// 履歴更新
			update();
		}
	}
	
	/**
	 * 新規追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		ApplicationRegistBeanInterface regist = time().applicationRegist();
		// DTOの準備
		ApplicationDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 登録する設定適用が利用可能かチェックする
		isAvailableApplySetting(dto.getActivateDate(), dto.getWorkSettingCode(), dto.getScheduleCode());
		// 新規追加処理
		regist.insert(dto);
		// 新規追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getApplicationCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		ApplicationRegistBeanInterface regist = time().applicationRegist();
		// DTOの準備
		ApplicationDtoInterface dto = new TmmApplicationDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 登録する設定適用が利用可能かチェックを行う
		isAvailableApplySetting(dto.getActivateDate(), dto.getWorkSettingCode(), dto.getScheduleCode());
		// 履歴追加処理
		regist.add(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージを設定
		PfMessageUtility.addMessageAddHistorySucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getApplicationCode(), dto.getActivateDate());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO準備
		ApplicationCardVo vo = (ApplicationCardVo)mospParams.getVo();
		// 登録クラス取得
		ApplicationRegistBeanInterface regist = time().applicationRegist();
		// DTOの準備
		ApplicationDtoInterface dto = timeReference().application().findForKey(vo.getTxtEditApplicationCode(),
				getEditActivateDate());
		// DTOに値を設定
		setDtoFields(dto);
		// 登録する設定適用が利用可能かチェックを行う
		isAvailableApplySetting(dto.getActivateDate(), dto.getWorkSettingCode(), dto.getScheduleCode());
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
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getApplicationCode(), dto.getActivateDate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// DTOの準備
		ApplicationDtoInterface dto = new TmmApplicationDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 削除処理
		time().applicationRegist().delete(dto);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteSucceed(mospParams);
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		ApplicationCardVo vo = (ApplicationCardVo)mospParams.getVo();
		ApplicationReferenceBeanInterface application = timeReference().application();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// 有効日モード設定(変更)
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// プルダウン取得
			setPulldown();
			return;
		}
		// 有効日モード設定(決定)
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン取得
		setPulldown();
		// プルダウン内容取得(勤怠設定、カレンダ、有給休暇設定)
		String workSetting = vo.getAryPltEditWorkSetting()[0][0];
		String schedule = vo.getAryPltEditSchedule()[0][0];
		String paidHoliday = vo.getAryPltEditPaidHoliday()[0][0];
		// 勤怠設定、カレンダ、有給休暇設定に設定対象が存在しない場合
		if (workSetting.isEmpty() || schedule.isEmpty() || paidHoliday.isEmpty()) {
			// エラーメッセージ準備
			String emptySetting = "";
			if (workSetting.isEmpty()) {
				emptySetting = mospParams.getName("WorkManage", "Set");
				// エラーメッセージ設定
				mospParams.addErrorMessage(TimeMessageConst.MSG_SETUP_INFORMATION, emptySetting);
			}
			if (schedule.isEmpty()) {
				emptySetting = mospParams.getName("Calendar");
				// エラーメッセージ設定
				mospParams.addErrorMessage(TimeMessageConst.MSG_SETUP_INFORMATION, emptySetting);
			}
			if (paidHoliday.isEmpty()) {
				emptySetting = mospParams.getName("PaidVacation", "Set");
				// エラーメッセージ設定
				mospParams.addErrorMessage(TimeMessageConst.MSG_SETUP_INFORMATION, emptySetting);
			}
			// 有効日モード設定(変更)
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// プルダウン再取得
			setPulldown();
			return;
		}
		ApplicationDtoInterface applicationDto = application.findFormerInfo(vo.getTxtEditApplicationCode(),
				getEditActivateDate());
		if (applicationDto == null) {
			return;
		}
		vo.setPltEditWorkSetting(applicationDto.getWorkSettingCode());
		vo.setPltEditSchedule(applicationDto.getScheduleCode());
		vo.setPltEditPaidHoliday(applicationDto.getPaidHolidayCode());
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void insertMode() throws MospException {
		// 編集モード設定
		setEditInsertMode();
		// VO取得
		ApplicationCardVo vo = (ApplicationCardVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtEditApplicationCode("");
		vo.setTxtEditApplicationName("");
		vo.setTxtEditApplicationAbbr("");
		vo.setTxtEditEmployeeCode("");
		vo.setLblEmployeeName("");
		vo.setPltEditWorkPlaceMaster("");
		vo.setPltEditEmploymentMaster("");
		vo.setPltEditPositionMaster("");
		vo.setPltEditSectionMaster("");
		vo.setRadApplicationType(PlatformConst.APPLICATION_TYPE_MASTER);
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン(編集)設定
		setPulldown();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void addMode() throws MospException {
		// 履歴追加モード設定
		setEditAddMode();
		// 有効日(編集)モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode(), getDate(getTransferredActivateDate()));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 勤務形態コードと有効日で編集対象情報を取得する。<br>
	 * @param applicationCode 設定適用コード
	 * @param activateDate    有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String applicationCode, Date activateDate) throws MospException {
		// VO取得
		ApplicationCardVo vo = (ApplicationCardVo)mospParams.getVo();
		// 設定適用参照クラス取得
		ApplicationReferenceBeanInterface application = timeReference().application();
		// 履歴編集対象取得
		ApplicationDtoInterface dto = application.findForKey(applicationCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード設定(編集対象の履歴を取得)
		setEditUpdateMode(application.getApplicationHistory(applicationCode));
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * プルダウンを設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		ApplicationCardVo vo = (ApplicationCardVo)mospParams.getVo();
		// プルダウンの設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltEditWorkPlaceMaster(getInputActivateDatePulldown());
			vo.setAryPltEditEmploymentMaster(getInputActivateDatePulldown());
			vo.setAryPltEditSectionMaster(getInputActivateDatePulldown());
			vo.setAryPltEditPositionMaster(getInputActivateDatePulldown());
			vo.setAryPltEditWorkSetting(getInputActivateDatePulldown());
			vo.setAryPltEditSchedule(getInputActivateDatePulldown());
			vo.setAryPltEditPaidHoliday(getInputActivateDatePulldown());
			return;
		}
		// 編集有効日取得
		Date date = getEditActivateDate();
		// 勤務地
		vo.setAryPltEditWorkPlaceMaster(reference().workPlace().getCodedSelectArray(date, true, null));
		// 雇用契約
		vo.setAryPltEditEmploymentMaster(reference().employmentContract().getCodedSelectArray(date, true, null));
		// 所属
		vo.setAryPltEditSectionMaster(reference().section().getCodedSelectArray(date, true, null));
		// 職位
		vo.setAryPltEditPositionMaster(reference().position().getCodedSelectArray(date, true, null));
		// 勤怠設定
		vo.setAryPltEditWorkSetting(timeReference().timeSetting().getCodedSelectArray(date, false));
		// カレンダ
		vo.setAryPltEditSchedule(timeReference().scheduleUtil().getCodedSelectArray(date, false));
		// 有給休暇設定
		vo.setAryPltEditPaidHoliday(timeReference().paidHoliday().getCodedSelectArray(date, false));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 個人IDの取得に失敗した場合
	 */
	protected void setDtoFields(ApplicationDtoInterface dto) throws MospException {
		// VO取得
		ApplicationCardVo vo = (ApplicationCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		Date targetDate = getEditActivateDate();
		dto.setTmmApplicationId(vo.getRecordId());
		dto.setActivateDate(targetDate);
		dto.setApplicationCode(vo.getTxtEditApplicationCode());
		dto.setApplicationType(getInt(vo.getRadApplicationType()));
		dto.setApplicationName(vo.getTxtEditApplicationName());
		dto.setApplicationAbbr(vo.getTxtEditApplicationAbbr());
		dto.setWorkSettingCode(vo.getPltEditWorkSetting());
		dto.setScheduleCode(vo.getPltEditSchedule());
		dto.setPaidHolidayCode(vo.getPltEditPaidHoliday());
		dto.setInactivateFlag(Integer.parseInt(vo.getPltEditInactivate()));
		// 設定適用区分確認
		if (vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_MASTER)) {
			// マスタ組合設定
			dto.setWorkPlaceCode(vo.getPltEditWorkPlaceMaster());
			dto.setEmploymentContractCode(vo.getPltEditEmploymentMaster());
			dto.setSectionCode(vo.getPltEditSectionMaster());
			dto.setPositionCode(vo.getPltEditPositionMaster());
			// 個人ID設定
			dto.setPersonalIds("");
		} else {
			// マスタ組合設定
			dto.setWorkPlaceCode("");
			dto.setEmploymentContractCode("");
			dto.setSectionCode("");
			dto.setPositionCode("");
			// 社員の存在チェック
			List<String> humanList = reference().human().getPersonalIdList(vo.getTxtEditEmployeeCode(), targetDate);
			// 初期化
			StringBuffer sb = new StringBuffer();
			String targetId = "";
			// 入社、退社に情報を取得
			for (int i = 0; i < humanList.size(); i++) {
				targetId = humanList.get(i);
				// 個人IDが存在しない場合
				if (targetId.equals("")) {
					continue;
				}
				// 社員コードを取得
				String employeeCode = getEmployeeCode(targetId, targetDate);
				// 入社情報の設定
				if (reference().entrance().isEntered(targetId, targetDate) == false) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorEmployeeNotEntered(mospParams, targetDate, employeeCode);
					continue;
				}
				// 退社情報の設定
				if (reference().retirement().isRetired(targetId, targetDate)) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorEmployeeRetired(mospParams, targetDate, employeeCode);
					continue;
				}
				// バッファに追加、追加する際にDTOに設定するための形式する
				if (sb.length() == 0) {
					sb.append(targetId);
				} else {
					sb.append(mospParams.getName("Comma") + targetId);
				}
			}
			// 個人ID設定
			dto.setPersonalIds(sb.toString());
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 社員コードの取得に失敗した場合
	 */
	protected void setVoFields(ApplicationDtoInterface dto) throws MospException {
		// VO取得
		ApplicationCardVo vo = (ApplicationCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmmApplicationId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtEditApplicationCode(dto.getApplicationCode());
		vo.setRadApplicationType(String.valueOf(dto.getApplicationType()));
		vo.setTxtEditApplicationName(dto.getApplicationName());
		vo.setTxtEditApplicationAbbr(dto.getApplicationAbbr());
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		vo.setPltEditWorkPlaceMaster(dto.getWorkPlaceCode());
		vo.setPltEditEmploymentMaster(dto.getEmploymentContractCode());
		vo.setPltEditSectionMaster(dto.getSectionCode());
		vo.setPltEditPositionMaster(dto.getPositionCode());
		vo.setPltEditWorkSetting(dto.getWorkSettingCode());
		vo.setPltEditSchedule(dto.getScheduleCode());
		vo.setPltEditPaidHoliday(dto.getPaidHolidayCode());
		// 社員コード
		vo.setTxtEditEmployeeCode(reference().human().getEmployeeCodes(dto.getPersonalIds(), dto.getActivateDate()));
		// 適用対象者氏名
		vo.setLblEmployeeName(reference().human().getHumanNames(dto.getPersonalIds(), dto.getActivateDate()));
	}
	
	/**
	 * 登録する設定適用が利用可能かチェックを行う<br>
	 * @param targetDate 対象日
	 * @param types 対象機能
	 * @return エラーの場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public boolean isAvailableApplySetting(Date targetDate, String... types) throws MospException {
		boolean checkApplySetting = true;
		// 設定適用のチェック毎に処理
		for (CheckAvailableBeanInterface addonBean : getBeans(CheckAvailableBeanInterface.class, CODE_KEY_CHECK)) {
			// 設定適用のチェック処理
			// 引数の個人IDは使用しない
			checkApplySetting = addonBean.check("", targetDate, types);
		}
		return checkApplySetting;
	}
}
