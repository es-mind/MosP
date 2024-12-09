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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.BasicCardVo;
import jp.mosp.platform.portal.action.PortalAction;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 社員基本情報の履歴追加・履歴編集を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_ADD_SELECT}
 * </li><li>
 * {@link #CMD_ADD}
 * </li><li>
 * {@link #CMD_EDIT_SELECT}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li></ul>
 */
public class BasicCardAction extends PlatformHumanAction {
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 各レコード毎に有効日が設定されている項目（所属情報、職位情報、雇用契約情報）に対して
	 * テキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なレコードのプルダウンリストを作成し、各種検索項目毎にセットする。<br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF1131";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 */
	public static final String	CMD_SEARCH				= "PF1132";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこれよりも奥の階層の画面から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で再表示を行う。<br>
	 */
	public static final String	CMD_RE_SEARCH			= "PF1133";
	
	/**
	 * 履歴追加画面の選択表示コマンド。<br>
	 * <br>
	 * 遷移元の人事情報一覧画面で表示されていた社員の個人基本情報を取得し、
	 * 個人基本情報履歴追加画面へ遷移する。<br>
	 * 入力欄には登録済みの社員コードをデフォルトで表示させる。<br>
	 */
	public static final String	CMD_ADD_SELECT			= "PF1136";
	
	/**
	 * 履歴追加コマンド。<br>
	 * <br>
	 * 登録情報入力欄に入力された内容を基に社員情報の履歴追加を行う。<br>
	 * 入力チェックを行い、入力必須項目（有効日、姓）が入力されていない場合は
	 * エラーメッセージで通知する。<br>
	 */
	public static final String	CMD_ADD					= "PF1138";
	
	/**
	 * 履歴編集画面の選択表示コマンド。<br>
	 * <br>
	 * 遷移元の人事情報一覧画面で表示されていた社員の個人基本情報を取得し、
	 * 個人基本情報履歴編集画面へ遷移する。<br>
	 * 入力欄には登録済みの各種情報をデフォルトで表示させる。<br>
	 */
	public static final String	CMD_EDIT_SELECT			= "PF1156";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 登録情報入力欄に入力された内容を基に社員情報の履歴編集を行う。<br>
	 * 入力チェックを行い、入力必須項目（有効日、姓）が入力されていない場合はエラーメッセージで通知する。<br>
	 */
	public static final String	CMD_UPDATE				= "PF1158";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER			= "PF1159";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public BasicCardAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			reSearch();
		} else if (mospParams.getCommand().equals(CMD_ADD_SELECT)) {
			// 履歴追加画面選択表示
			prepareVo(true, false);
			addSelect();
		} else if (mospParams.getCommand().equals(CMD_ADD)) {
			// 履歴追加
			prepareVo();
			add();
		} else if (mospParams.getCommand().equals(CMD_EDIT_SELECT)) {
			// 履歴編集画面選択表示
			prepareVo(false, false);
			editSelect();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 履歴編集
			prepareVo();
			update();
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
		return new BasicCardVo();
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			// プルダウン設定
			setPulldown();
			setPostPulldown();
			// 有効日が近い情報設定
			setPersonalInfo(vo.getPersonalId(), getActivateDate(), false);
		} else if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// プルダウン設定
			setPulldown();
			setPostPulldown();
		}
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 初期値設定
		setDefaultValue();
	}
	
	/**
	 * 再表示処理を行う。<br>
	 * パンくずリストからVOを取得し、VOに保持されている対象社員コード及び有効日で
	 * 各種人事情報を取得及び設定する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void reSearch() throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 人事管理共通情報設定
		setHumanCommonInfo(vo.getPersonalId(), vo.getTargetDate());
		// 編集モード確認
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
			// 有効日を空白に設定
			vo.setTxtActivateYear("");
			vo.setTxtActivateMonth("");
			vo.setTxtActivateDay("");
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		}
		// プルダウンリストの設定
		setPulldown();
		// 人事基本情報設定
		setPersonalInfo(vo.getPersonalId(), vo.getTargetDate(), false);
	}
	
	/**
	 * 履歴追加画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addSelect() throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 役職要否確認取得
		vo.setNeedPost(mospParams.getApplicationPropertyBool(PlatformConst.APP_ADD_USE_POST));
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 初期値設定
		setDefaultValue();
		// 社員コード変更機能設定
		vo.setJsChangeEmployCode(mospParams.getApplicationPropertyBool(PlatformConst.APP_CHANGE_EMPLOY_CODE));
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		HumanRegistBeanInterface regist = platform().humanRegist();
		// DTOの準備
		HumanDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// DTOに値を設定
		registPost();
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージを設定
		PfMessageUtility.addMessageAddHistorySucceed(mospParams);
		// 人事管理共通情報設定
		setHumanCommonInfo(dto.getPersonalId(), dto.getActivateDate());
		// 登録内容再表示
		setPersonalInfo(dto.getPersonalId(), dto.getActivateDate(), true);
	}
	
	/**
	 * 履歴編集画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void editSelect() throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 役職要否確認取得
		vo.setNeedPost(mospParams.getApplicationPropertyBool(PlatformConst.APP_ADD_USE_POST));
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_CODE_AND_NAME);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
		// 登録内容を画面に設定
		setPersonalInfo(vo.getPersonalId(), vo.getTargetDate(), true);
		// プルダウンリストの設定
		setPulldown();
		setPostPulldown();
		// 社員コード変更機能設定
		vo.setJsChangeEmployCode(mospParams.getApplicationPropertyBool(PlatformConst.APP_CHANGE_EMPLOY_CODE));
	}
	
	/**
	 * 履歴編集処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録クラス取得
		HumanRegistBeanInterface regist = platform().humanRegist();
		// DTOの準備
		HumanDtoInterface dto = regist.getInitDto();
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
		// 役職を更新
		registUpdatePost(dto);
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴編集成功メッセージを設定
		PfMessageUtility.addMessageEditHistorySucceed(mospParams);
		// 社員情報再設定
		setPersonalInfo(dto.getPersonalId(), dto.getActivateDate(), true);
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
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
		} else if (actionName.equals(BasicListAction.class.getName())) {
			// 個人基本情報の履歴の一覧
			mospParams.setNextCommand(BasicListAction.CMD_SELECT);
		}
	}
	
	/**
	 * プルダウンの設定を行う。<br>
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltSection(getInputActivateDatePulldown());
			vo.setAryPltPosition(getInputActivateDatePulldown());
			vo.setAryPltEmployment(getInputActivateDatePulldown());
			vo.setAryPltWorkPlaceName(getInputActivateDatePulldown());
			return;
		}
		// 有効日取得
		Date targetDate = getActivateDate();
		// 操作区分(参照)を準備
		String range = MospConst.OPERATION_TYPE_REFER;
		// プルダウン設定
		vo.setAryPltSection(reference().section().getCodedSelectArray(targetDate, true, range));
		vo.setAryPltPosition(reference().position().getCodedSelectArray(targetDate, true, range));
		vo.setAryPltEmployment(reference().employmentContract().getCodedAbbrSelectArray(targetDate, true, range));
		vo.setAryPltWorkPlaceName(reference().workPlace().getCodedAbbrSelectArray(targetDate, true, range));
	}
	
	/**
	 * 役職をプルダウンに設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPostPulldown() throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 役職要否確認
		if (vo.getNeedPost() == false) {
			return;
		}
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltPostName(getInputActivateDatePulldown());
			return;
		}
		// プルダウン設定
		vo.setAryPltPostName(
				reference().naming().getCodedSelectArray(PlatformConst.NAMING_TYPE_POST, getActivateDate(), true));
	}
	
	/**
	 * 人事基本情報を取得し、VOに設定する。
	 * 最新の有効日情報で人事基本情報を取得する。
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param isActiveDate 有効日要否確認(true：必要、false：不要)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void setPersonalInfo(String personalId, Date activateDate, boolean isActiveDate) throws MospException {
		// 人事マスタ情報取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, activateDate);
		// 人事情報がない場合
		if (humanDto == null) {
			// 人事履歴一覧取得
			List<HumanDtoInterface> humanList = reference().human().getHistory(personalId);
			// 人事履歴がない場合
			if (humanList.isEmpty()) {
				// メッセージ設定(社員が存在しない場合)
				PfMessageUtility.addErrorEmployeeNotExist(mospParams);
				// エラーメッセージ確認
				if (mospParams.hasErrorMessage()) {
					// 連続実行コマンド設定(ポータル画面表示)
					mospParams.setNextCommand(PortalAction.CMD_SHOW);
				}
				// 例外発行
				throw new MospException(ExceptionConst.EX_NO_DATA);
			}
			// 人事情報取得
			humanDto = humanList.get(0);
		}
		// DTOの値をVO(編集項目)に設定
		setVoFields(humanDto, isActiveDate);
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 役職無効の場合
		if (vo.getNeedPost() == false) {
			// 処理無し
			return;
		}
		// 役職情報取得
		HumanHistoryDtoInterface postDto = reference().humanHistory().findForInfo(personalId,
				PlatformConst.NAMING_TYPE_POST, activateDate);
		// DTOの値をVO(編集項目)に設定
		setVoFieldsPost(postDto);
	}
	
	/**
	 * VO(編集項目)の値をDTO(人事マスタ)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 日付の取得に失敗した場合
	 */
	protected void setDtoFields(HumanDtoInterface dto) throws MospException {
		// VO準備
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setPfmHumanId(vo.getRecordId());
		dto.setPersonalId(vo.getPersonalId());
		dto.setActivateDate(getActivateDate());
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
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param humanDto 対象人事マスタDTO
	 * @param isActiveDate 有効日要否確認(true：必要、false：不要)
	 */
	protected void setVoFields(HumanDtoInterface humanDto, boolean isActiveDate) {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(humanDto.getPfmHumanId());
		vo.setPersonalId(humanDto.getPersonalId());
		vo.setTxtEmployeeCode(humanDto.getEmployeeCode());
		vo.setTxtLastName(humanDto.getLastName());
		vo.setTxtFirstName(humanDto.getFirstName());
		vo.setTxtLastKana(humanDto.getLastKana());
		vo.setTxtFirstKana(humanDto.getFirstKana());
		vo.setPltSectionName(humanDto.getSectionCode());
		vo.setPltPositionName(humanDto.getPositionCode());
		vo.setPltEmploymentName(humanDto.getEmploymentContractCode());
		vo.setPltWorkPlaceName(humanDto.getWorkPlaceCode());
		if (isActiveDate) {
			vo.setTxtActivateYear(getStringYear(humanDto.getActivateDate()));
			vo.setTxtActivateMonth(getStringMonth(humanDto.getActivateDate()));
			vo.setTxtActivateDay(getStringDay(humanDto.getActivateDate()));
		}
	}
	
	/**
	 * 役職情報(人事汎用履歴情報)の値をVO(編集項目)に設定する。<br>
	 * @param postDto 役職情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setVoFieldsPost(HumanHistoryDtoInterface postDto) throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 役職無効の場合
		if (vo.getNeedPost() == false) {
			// 処理無し
			return;
		}
		// DTOの値をVO(編集項目)に設定
		if (postDto == null) {
			vo.setPltPostName("");
			return;
		}
		// DTOの値をVOに設定
		vo.setPltPostName(postDto.getHumanItemValue());
	}
	
	/**
	 * 役職(人事汎用履歴情報)を登録する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registPost() throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 役職無効の場合
		if (vo.getNeedPost() == false) {
			// 処理無し
			return;
		}
		// 役職が登録されていない場合
		if (vo.getPltPostName() == null || vo.getPltPostName().isEmpty()) {
			// 処理無し
			return;
		}
		// 登録クラス取得
		HumanHistoryRegistBeanInterface historyRegist = platform().humanHistoryRegist();
		// DTOの準備
		HumanHistoryDtoInterface humanHistoryDto = historyRegist.getInitDto();
		// DTO設定
		humanHistoryDto.setActivateDate(getActivateDate());
		humanHistoryDto.setPersonalId(vo.getPersonalId());
		humanHistoryDto.setHumanItemType(PlatformConst.NAMING_TYPE_POST);
		humanHistoryDto.setHumanItemValue(vo.getPltPostName());
		// 人事汎用履歴情報の登録又は更新
		historyRegist.add(humanHistoryDto);
	}
	
	/**
	 * 役職(人事汎用履歴情報)を更新する。
	 * @param dto 人事マスタ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registUpdatePost(HumanDtoInterface dto) throws MospException {
		// 登録クラス取得
		HumanHistoryRegistBeanInterface historyRegist = platform().humanHistoryRegist();
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 役職無効の場合
		if (vo.getNeedPost() == false) {
			// 処理無し
			return;
		}
		// 人事汎用履歴情報を取得
		HumanHistoryDtoInterface historyDto = reference().humanHistory().findForKey(dto.getPersonalId(),
				PlatformConst.NAMING_TYPE_POST, dto.getActivateDate());
		// 人事汎用履歴情報がない場合
		if (historyDto == null) {
			// 新規登録
			registPost();
			return;
		}
		// 役職を登録しない場合
		if (vo.getPltPostName() == null || vo.getPltPostName().isEmpty()) {
			// 処理無し
			historyRegist.delete(historyDto);
			return;
		} else {
			// 項目地を更新
			historyDto.setHumanItemValue(vo.getPltPostName());
			// 更新
			historyRegist.update(historyDto);
		}
	}
	
	/**
	 * VOから有効日(編集)を取得する。<br>
	 * @return 有効日(編集)
	 */
	protected Date getActivateDate() {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
	}
	
	/**
	 * 履歴追加画面の初期化をする。
	 * 履歴追加選択表示、検索で使用する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setDefaultValue() throws MospException {
		// VO取得
		BasicCardVo vo = (BasicCardVo)mospParams.getVo();
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_ADD);
		// プルダウン設定
		setPulldown();
		setPostPulldown();
		// 個人情報設定
		setPersonalInfo(vo.getPersonalId(), vo.getTargetDate(), false);
		// 有効日を空白に設定
		vo.setTxtActivateYear("");
		vo.setTxtActivateMonth("");
		vo.setTxtActivateDay("");
	}
	
}
