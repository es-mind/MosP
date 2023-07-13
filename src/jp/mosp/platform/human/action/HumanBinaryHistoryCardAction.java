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
package jp.mosp.platform.human.action;

import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.BinaryUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanBinaryHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.human.vo.HumanBinaryHistoryCardVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用バイナリ履歴情報の履歴追加・履歴削除行う。
 */
public class HumanBinaryHistoryCardAction extends PlatformHumanAction {
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 各レコード毎に有効日が設定されている項目（所属情報、職位情報、雇用契約情報）に対して
	 * テキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なレコードのプルダウンリストを作成し、各種検索項目毎にセットする。<br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE				= "PF1571";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 */
	public static final String	CMD_SEARCH							= "PF1572";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこれよりも奥の階層の画面から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で再表示を行う。<br>
	 */
	public static final String	CMD_RE_SEARCH						= "PF1573";
	
	/**
	 * 履歴追加画面の選択表示コマンド。<br>
	 * <br>
	 * 遷移元の人事情報一覧画面で表示されていた社員の個人基本情報を取得し、
	 * 個人基本情報履歴追加画面へ遷移する。<br>
	 * 入力欄には登録済みの社員コードをデフォルトで表示させる。<br>
	 */
	public static final String	CMD_ADD_SELECT						= "PF1574";
	
	/**
	 * 履歴追加コマンド。<br>
	 * <br>
	 * 登録情報入力欄に入力された内容を基に社員情報の履歴追加を行う。<br>
	 * 入力チェックを行い、入力必須項目（有効日、姓）が入力されていない場合は
	 * エラーメッセージで通知する。<br>
	 */
	public static final String	CMD_ADD								= "PF1575";
	
	/**
	 * 履歴編集画面の選択表示コマンド。<br>
	 * <br>
	 * 遷移元の人事情報一覧画面で表示されていた社員の個人基本情報を取得し、
	 * 個人基本情報履歴編集画面へ遷移する。<br>
	 * 入力欄には登録済みの各種情報をデフォルトで表示させる。<br>
	 */
	public static final String	CMD_EDIT_SELECT						= "PF1576";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 登録情報入力欄に入力された内容を基に社員情報の履歴編集を行う。<br>
	 * 入力チェックを行い、入力必須項目（有効日、姓）が入力されていない場合はエラーメッセージで通知する。<br>
	 */
	public static final String	CMD_UPDATE							= "PF1578";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER						= "PF1579";
	
	/**
	 * 人事汎用管理表示区分(履歴一覧)。<br>
	 */
	public static final String	KEY_VIEW_HUMAN_BINARY_HISTORY_CARD	= "HistoryCard";
	
	/**
	 * パラメータID(バイナリファイル)。
	 */
	public static final String	PRM_FILE_BINARY_HISTORY				= "fileBinaryHistory";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanBinaryHistoryCardAction() {
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
			// 選択
			prepareVo(true, false);
			addSelect();
		} else if (mospParams.getCommand().equals(CMD_ADD)) {
			// 履歴追加
			prepareVo();
			add();
		} else if (mospParams.getCommand().equals(CMD_EDIT_SELECT)) {
			// 選択
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
		// 当該画面共通情報設定
		setCardCommonInfo();
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// 個人ID・有効日を取得
		String personalId = vo.getPersonalId();
		Date activateDate = getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 人事マスタ取得
			HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, activateDate);
			// 人事マスタがある場合
			if (humanDto == null) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorPersonalBasisInfoNotExist(mospParams);
				return;
			}
			// 対象情報が存在する場合
			if (!reference().humanBinaryHistory().findForActivateDate(personalId, activateDate).isEmpty()) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorHistoryDuplicate(mospParams);
				return;
			}
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン設定
		setPulldown();
		
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HumanBinaryHistoryCardVo();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 初期表示
		setDefaultValues();
	}
	
	/**
	 * 再表示処理を行う。<br>
	 * パンくずリストからVOを取得し、VOに保持されている対象社員コード及び有効日で
	 * 各種人事情報を取得及び設定する。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void reSearch() throws MospException {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
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
	}
	
	/**
	 * 履歴追加画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addSelect() throws MospException {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// 人事汎用管理区分設定
		vo.setDivision(getTransferredType());
		// パンくず名準備
		StringBuilder name = new StringBuilder(NameUtility.getName(mospParams, vo.getDivision()));
		name.append(PfNameUtility.insertInfo(mospParams));
		// パンくず名設定
		TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), name.toString());
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 初期表示
		setDefaultValues();
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// 人事汎用管理区分取得
		String division = vo.getDivision();
		// 有効日取得
		Date activeDate = getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
		// 登録クラス取得
		HumanBinaryHistoryRegistBeanInterface regist = platform().humanBinaryHistoryRegist();
		// DTO取得
		HumanBinaryHistoryDtoInterface newDto = regist.getInitDto();
		// DTOに設定
		setDtoFields(newDto, activeDate);
		// 履歴追加処理
		regist.insert(newDto);
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
		setHumanCommonInfo(vo.getPersonalId(), activeDate);
		// 人事汎用通常情報参照クラス取得
		HumanBinaryHistoryReferenceBeanInterface binaryHistoryRefer = reference().humanBinaryHistory();
		// 登録情報取得
		HumanBinaryHistoryDtoInterface dto = binaryHistoryRefer.findForKey(vo.getPersonalId(), division, activeDate);
		// 登録情報を取得しVOに設定
		setVoFields(dto);
		// 履歴編集モードに設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
	}
	
	/**
	 * 履歴編集画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void editSelect() throws MospException {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// 人事汎用管理区分設定
		vo.setDivision(getTransferredType());
		// パンくず名準備
		StringBuilder name = new StringBuilder(NameUtility.getName(mospParams, vo.getDivision()));
		name.append(PfNameUtility.insertInfo(mospParams));
		// パンくず名設定
		TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), name.toString());
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_CODE_AND_NAME);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 遷移された有効日取得
		Date activeDate = DateUtility.getDate(getTransferredActivateDate());
		// プルダウン設定
		setPulldown();
		// 人事汎用通常情報参照クラス取得
		HumanBinaryHistoryReferenceBeanInterface binaryHistoryRefer = reference().humanBinaryHistory();
		// 登録情報取得
		HumanBinaryHistoryDtoInterface dto = binaryHistoryRefer.findForKey(vo.getPersonalId(), vo.getDivision(),
				activeDate);
		// 人事汎用履歴情報設定
		setVoFields(dto);
		// 遷移された有効日をMosP処理情報設定
		mospParams.addGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE, getTransferredActivateDate());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
		
	}
	
	/**
	 * 履歴編集処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 登録クラス取得
		HumanBinaryHistoryRegistBeanInterface regist = platform().humanBinaryHistoryRegist();
		// 有効日取得
		Date activeDate = getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
		// 登録情報取得
		HumanBinaryHistoryDtoInterface oldDto = reference().humanBinaryHistory().findForKey(vo.getHidRecordId(), false);
		// DTOに設定
		oldDto.setFileName(vo.getFileBinaryHistory());
		oldDto.setFileRemark(vo.getTxtFileRemark());
		// 履歴追加処理
		regist.update(oldDto);
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
		// MosP処理情報設定
		mospParams.addGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE, DateUtility.getStringDate(activeDate));
		// 人事汎用通常情報参照クラス取得
		HumanBinaryHistoryReferenceBeanInterface binaryHistoryRefer = reference().humanBinaryHistory();
		// 登録情報取得
		HumanBinaryHistoryDtoInterface dto = binaryHistoryRefer.findForKey(vo.getPersonalId(), vo.getDivision(),
				activeDate);
		// 値再設定
		setVoFields(dto);
	}
	
	/**
	 * 画面遷移を行う。
	 */
	protected void transfer() {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
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
		} else if (actionName.equals(HumanBinaryHistoryListAction.class.getName())) {
			// 個人基本情報の履歴の一覧
			mospParams.setNextCommand(HumanBinaryHistoryListAction.CMD_SELECT);
		}
	}
	
	/**
	 * 履歴追加モードにおける画面の初期表示内容を設定する。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void setDefaultValues() throws MospException {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// 有効日を空白に設定
		vo.setTxtActivateYear("");
		vo.setTxtActivateMonth("");
		vo.setTxtActivateDay("");
		// 項目を空白に設定
		vo.setFileBinaryHistory("");
		vo.setTxtFileRemark("");
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_ADD);
		
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 画面に値を設定する。
	 * @param dto 人事バイナリ履歴情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setVoFields(HumanBinaryHistoryDtoInterface dto) throws MospException {
		// 情報がない場合
		if (dto == null) {
			// 初期値設定
			setDefaultValues();
			return;
		}
		// バイナリデータ表示
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// VOに設定
		vo.setHidRecordId(dto.getPfaHumanBinaryHistoryId());
		vo.setTxtActivateYear(DateUtility.getStringYear(dto.getActivateDate()));
		vo.setTxtActivateMonth(DateUtility.getStringMonth(dto.getActivateDate()));
		vo.setTxtActivateDay(DateUtility.getStringDay(dto.getActivateDate()));
		vo.setPltFileType(dto.getFileType());
		vo.setFileBinaryHistory(dto.getFileName());
		vo.setTxtFileRemark(dto.getFileRemark());
	}
	
	/**
	 * DTOに値を設定する。
	 * @param dto 人事バイナリ通常情報
	 * @param activeDate 有効日
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setDtoFields(HumanBinaryHistoryDtoInterface dto, Date activeDate) throws MospException {
		// ファイルをbyte[]で取得
		byte[] file = BinaryUtility.getBinaryData(mospParams.getRequestFile(PRM_FILE_BINARY_HISTORY));
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// DTOに設定
		dto.setActivateDate(activeDate);
		dto.setPersonalId(vo.getPersonalId());
		dto.setHumanItemType(vo.getDivision());
		dto.setHumanItemBinary(file);
		dto.setFileType(HumanUtility.getBinaryFileType(vo.getFileBinaryHistory()));
		dto.setFileName(vo.getFileBinaryHistory());
		dto.setFileRemark(vo.getTxtFileRemark());
	}
	
	/**
	 * プルダウンを設定する。
	 */
	protected void setPulldown() {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// プルダウン設定
		MospProperties properties = mospParams.getProperties();
		String[][] aryBinaryFileType = properties.getCodeArray(PlatformConst.CODE_KEY_BINARY_FILE_TYPE, true);
		vo.setAryPltFileType(aryBinaryFileType);
	}
	
	/**
	 * 当該画面の共通情報を設定する
	 */
	public void setCardCommonInfo() {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// 人事汎用管理区分参照権限設定
		vo.setJsIsReferenceDivision(RoleUtility.getReferenceDivisionsList(mospParams).contains(getTransferredType()));
	}
	
}
