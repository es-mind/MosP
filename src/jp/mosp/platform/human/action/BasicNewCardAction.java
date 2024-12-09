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
package jp.mosp.platform.human.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.EntranceRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.vo.BasicNewCardVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 社員基本情報の新規登録を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT}
 * </li></ul>
 */
public class BasicNewCardAction extends PlatformHumanAction {
	
	/**
	 * MosP汎用パラメータキー(人事情報一覧遷移ボタン利用可否)。<br>
	 * 空文字列を設定すると、人事情報一覧遷移ボタンが利用できるようになる。<br>
	 * このキーで設定したStringは、画面表示時にJavaScriptの変数として宣言される。<br>
	 */
	protected static final String	MGP_JS_HUMAN_INFO		= "jsToHumanInfo";
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String		CMD_SHOW				= "PF1210";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 各レコード毎に有効日が設定されている項目（所属情報、職位情報、雇用契約情報）に対して
	 * テキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なレコードのプルダウンリストを作成し、各種検索項目毎にセットする。<br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE	= "PF1211";
	
	/**
	 * 自動設定コマンド。<br>
	 * <br>
	 * 社員コードを自動採番して、VOに設定する。<br>
	 */
	public static final String		CMD_AUTO_NUMBERING		= "PF1212";
	
	/**
	 * 新規登録コマンド。<br>
	 * <br>
	 * 登録情報入力欄に入力された内容を基に社員情報の新規登録を行う。<br>
	 * 入力チェックを行い、入力必須項目（有効日、社員コード、姓）が入力されていない場合や
	 * 社員コード・ユーザIDが登録済みの社員データと重複する場合はエラーメッセージで通知する。<br>
	 * 登録後は自動的に新規社員登録後画面へ遷移する。<br>
	 * ユーザIDが入力された状態で登録を行うとアカウント情報が作成され、
	 * 入社日が入力された状態で登録を行うと入社情報が作成される。<br>
	 */
	public static final String		CMD_INSERT				= "PF1217";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String		CMD_TRANSFER			= "PF1219";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public BasicNewCardAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_SHOW;
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_AUTO_NUMBERING)) {
			// 自動設定
			prepareVo();
			autoNumbering();
		} else if (mospParams.getCommand().equals(CMD_INSERT)) {
			// 新規登録
			prepareVo();
			insert();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 画面遷移
			prepareVo(true, false);
			transfer();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new BasicNewCardVo();
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 有効日設定
		vo.setTxtActivateYear(getStringYear(date));
		vo.setTxtActivateMonth(getStringMonth(date));
		vo.setTxtActivateDay(getStringDay(date));
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 追加機能情報取得
		vo.setNeedPost(mospParams.getApplicationPropertyBool(PlatformConst.APP_ADD_USE_POST));
		// 自動設定ボタン要否設定
		vo.setNeedNumberingButton(reference().employeeNumbering().isEmployeeNumberingAvailable());
		// 追加JSPリスト初期化
		vo.setExtraJspList(new ArrayList<String>());
		// プルダウン設定
		setPulldown();
		setPostPulldown();
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * @throws MospException プルダウンの設定に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定(編集状態→決定状態)
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定(決定状態→編集状態)
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン設定
		setPulldown();
		setPostPulldown();
	}
	
	/**
	 * 自動設定処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void autoNumbering() throws MospException {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 自動採番
		String employeeCode = reference().employeeNumbering().getNewEmployeeCode();
		// VO(社員コード)に設定
		vo.setTxtEmployeeCode(employeeCode);
	}
	
	/**
	 * 新規登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 人事マスタ登録
		HumanDtoInterface dto = registHuman();
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 入社情報登録
		registEntrance();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// ユーザマスタ登録
		registUser();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 役職登録
		registPost();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 追加情報登録
		registExtra(dto.getPersonalId());
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageNewInsertSucceed(mospParams);
		// 人事情報一覧遷移用個人ID及び対象日設定
		vo.setPersonalId(dto.getPersonalId());
		vo.setTargetDate(dto.getActivateDate());
		// 人事情報一覧遷移ボタン利用設定
		mospParams.addGeneralParam(MGP_JS_HUMAN_INFO, "");
		// 入力項目初期化
		initVoFields();
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
		setPostPulldown();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(HumanInfoAction.class.getName())) {
			// 社員の人事情報をまとめて表示
			mospParams.setNextCommand(HumanInfoAction.CMD_SELECT);
		}
	}
	
	/**
	 * 有効日モード及び有効日を基にプルダウンを設定する。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// プルダウン設定
			vo.setAryPltSection(getInputActivateDatePulldown());
			vo.setAryPltPosition(getInputActivateDatePulldown());
			vo.setAryPltEmployment(getInputActivateDatePulldown());
			vo.setAryPltWorkPlaceName(getInputActivateDatePulldown());
			return;
		}
		// 有効日取得
		Date targetDate = getEditActivateDate();
		// プルダウン取得及び設定
//		vo.setAryPltSection(reference().section().getLeveledSelectArray(targetDate, true));
		vo.setAryPltSection(reference().section().getCodedSelectArray(targetDate, true, null));
		vo.setAryPltPosition(reference().position().getCodedSelectArray(targetDate, true, null));
		vo.setAryPltEmployment(reference().employmentContract().getCodedAbbrSelectArray(targetDate, true, null));
		vo.setAryPltWorkPlaceName(reference().workPlace().getCodedAbbrSelectArray(targetDate, true, null));
	}
	
	/**
	 * 役職プルダウンを設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPostPulldown() throws MospException {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		if (vo.getNeedPost() == false) {
			return;
		}
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltPostName(getInputActivateDatePulldown());
			return;
		}
		// 役職プルダウン設定
		vo.setAryPltPostName(
				reference().naming().getCodedSelectArray(PlatformConst.NAMING_TYPE_POST, getEditActivateDate(), true));
	}
	
	/**
	 * VOのフィールドを初期化する。<br>
	 */
	protected void initVoFields() {
		// VO準備
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// VOのフィールドを初期化
		vo.setTxtEmployeeCode("");
		vo.setTxtLastName("");
		vo.setTxtFirstName("");
		vo.setTxtLastKana("");
		vo.setTxtFirstKana("");
		vo.setPltSectionName("");
		vo.setPltPositionName("");
		vo.setPltEmploymentName("");
		vo.setPltWorkPlaceName("");
		vo.setTxtUserId("");
		vo.setTxtEntranceYear("");
		vo.setTxtEntranceMonth("");
		vo.setTxtEntranceDay("");
	}
	
	/**
	 * VO(編集項目)の値をDTO(人事マスタ)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 日付の取得に失敗した場合
	 */
	protected void setDtoFields(HumanDtoInterface dto) throws MospException {
		// VO準備
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setActivateDate(getEditActivateDate());
		dto.setEmployeeCode(vo.getTxtEmployeeCode());
		dto.setLastName(vo.getTxtLastName());
		dto.setFirstName(vo.getTxtFirstName());
		dto.setLastKana(vo.getTxtLastKana());
		dto.setFirstKana(vo.getTxtFirstKana());
		dto.setEmploymentContractCode(vo.getPltEmploymentName());
		dto.setSectionCode(vo.getPltSectionName());
		dto.setPositionCode(vo.getPltPositionName());
		dto.setWorkPlaceCode(vo.getPltWorkPlaceName());
		// メールアドレスは未使用
		dto.setMail("");
	}
	
	/**
	 * VO(編集項目)の値をDTO(人事入社情報)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(EntranceDtoInterface dto) throws MospException {
		// VO準備
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setEntranceDate(getEntranceDate());
		// 社員コードから個人IDを取得して設定
		dto.setPersonalId(reference().human().getPersonalId(vo.getTxtEmployeeCode(), getEditActivateDate()));
	}
	
	/**
	 * VO(編集項目)の値をDTO(ユーザマスタ)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void setDtoFields(UserMasterDtoInterface dto) throws MospException {
		// VO準備
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// メインロールの初期ロールコードを取得
		String roleCode = RoleUtility.getDefaultRole(mospParams);
		// VOの値をDTOに設定
		dto.setUserId(vo.getTxtUserId());
		dto.setActivateDate(getEditActivateDate());
		// 社員コードから個人IDを取得して設定
		dto.setPersonalId(reference().human().getPersonalId(vo.getTxtEmployeeCode(), getEditActivateDate()));
		// デフォルトロールコード設定
		dto.setRoleCode(roleCode);
		// 無効フラグ設定(有効)
		dto.setInactivateFlag(MospConst.DELETE_FLAG_OFF);
	}
	
	/**
	 * 人事マスタを登録する。
	 * @return 登録した人事情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected HumanDtoInterface registHuman() throws MospException {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 登録クラス取得
		HumanRegistBeanInterface regist = platform().humanRegist();
		// 参照クラス取得
		HumanReferenceBeanInterface reference = reference().human();
		// 入力された社員コード取得
		String employeeCode = vo.getTxtEmployeeCode();
		// 既に社員コードが使用済でないか確認
		HumanDtoInterface oldHoman = reference.getHumanInfoForEmployeeCode(employeeCode, getEditActivateDate());
		if (oldHoman != null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeCodeExist(mospParams, employeeCode);
			return null;
		}
		// DTOの準備
		HumanDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 登録処理
		regist.insert(dto);
		return dto;
	}
	
	/**
	 * 人事入社情報を登録する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registEntrance() throws MospException {
		// 入社日確認
		if (getEntranceDate() == null) {
			return;
		}
		// 入社情報登録クラス取得
		EntranceRegistBeanInterface regist = platform().entranceRegist();
		// 入社情報DTOの準備
		EntranceDtoInterface dto = regist.getInitDto();
		// 入社情報DTOに値を設定
		setDtoFields(dto);
		// 入社情報登録処理
		regist.insert(dto);
	}
	
	/**
	 * ユーザマスタを登録する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registUser() throws MospException {
		// VOを準備
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// ユーザIDが入力されていない場合
		if (MospUtility.isEmpty(vo.getTxtUserId())) {
			// 登録不要
			return;
		}
		// 登録処理を準備
		UserAccountRegistBeanInterface regist = platform().userAccountRegist();
		// 初期DTOを準備
		UserMasterDtoInterface userDto = regist.getInitUserDto();
		// 初期DTOに値を設定
		setDtoFields(userDto);
		// 新規登録(デフォルトユーザ追加ロール情報も登録)
		regist.insert(userDto, true);
	}
	
	/**
	 * 役職を登録する。
	 * 人事汎用履歴に登録する値を設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registPost() throws MospException {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 役職確認
		if (vo.getNeedPost() == false) {
			return;
		}
		if (vo.getPltPostName().isEmpty()) {
			return;
		}
		// 登録クラス取得
		HumanHistoryRegistBeanInterface historyRegist = platform().humanHistoryRegist();
		// DTOの準備
		HumanHistoryDtoInterface humanHistoryDto = historyRegist.getInitDto();
		// DTOに値を設定
		humanHistoryDto.setActivateDate(vo.getTargetDate());
		humanHistoryDto.setPersonalId(vo.getPersonalId());
		humanHistoryDto.setHumanItemType(PlatformConst.NAMING_TYPE_POST);
		humanHistoryDto.setHumanItemValue(vo.getPltPostName());
	}
	
	/**
	 * 追加情報を登録する。<br>
	 * @param personalId 個人ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void registExtra(String personalId) throws MospException {
		// アドオン等で実装
	}
	
	/**
	 * VOから有効日(編集)を取得する。<br>
	 * @return 有効日(編集)
	 */
	public Date getEditActivateDate() {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
	}
	
	/**
	 * VOから入社日を取得する。<br>
	 * @return 入社日
	 */
	protected Date getEntranceDate() {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 入社日取得
		return getDate(vo.getTxtEntranceYear(), vo.getTxtEntranceMonth(), vo.getTxtEntranceDay());
	}
	
	/**
	 * 追加JSPをリストに追加する。<br>
	 * @param path 追加JSPパス
	 */
	protected void addExtraJsp(String path) {
		// VO取得
		BasicNewCardVo vo = (BasicNewCardVo)mospParams.getVo();
		// 追加JSPリスト取得
		List<String> extraJspList = vo.getExtraJspList();
		// 追加JSPをリストに追加
		extraJspList.add(path);
	}
	
}
