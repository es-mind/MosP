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
import jp.mosp.platform.bean.human.HumanBinaryArrayRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanBinaryArrayDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.human.vo.HumanBinaryArrayCardVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用バイナリ一覧情報を履歴編集・表示・登録する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_ADD_SELECT}
 * </li></ul>
 * {@link #CMD_EDIT_SELECT}
 * </li></ul>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li></ul>
 * {@link #CMD_TRANSFER}
 * </li><li>
 */
public class HumanBinaryArrayCardAction extends PlatformHumanAction {
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF1561";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 社員コード入力欄に入力された社員コードで検索を行い、該当する社員の入社情報登録画面へ遷移する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 入力した社員コードが存在しない、または入力されていない状態で
	 * 「検索ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_SEARCH				= "PF1562";
	
	/**
	 * 追加画面の選択表示コマンド。<br>
	 * <br>
	 * 遷移元の人事情報一覧画面で表示されていた社員の個人基本情報を取得し、
	 * 人事汎用一覧情報追加画面を表示する。<br>
	 */
	public static final String	CMD_ADD_SELECT			= "PF1564";
	
	/**
	 * 新規登録コマンド。
	 */
	public static final String	CMD_ADD					= "PF1565";
	
	/**
	 * 履歴編集画面の選択表示コマンド。<br>
	 * <br>
	 * 遷移元の人事情報一覧で表示されていた社員の個人基本情報を取得し、
	 * 人事汎用一覧情報編集画面へ遷移する。<br>
	 * 入力欄には登録済みの各種情報をデフォルトで表示させる。<br>
	 */
	public static final String	CMD_EDIT_SELECT			= "PF1566";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 登録済みの人事汎用一覧情報の削除を行う。<br>
	 */
	public static final String	CMD_DELETE				= "PF1567";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * バイナリ値は更新できない。
	 */
	public static final String	CMD_UPDATE				= "PF1568";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER			= "PF1569";
	
	/**
	 * 人事汎用管理表示区分(履歴一覧)。<br>
	 */
	public static final String	KEY_VIEW_BINARY_ARRAY	= "BinaryArray";
	
	/**
	 * パラメータID(バイナリファイル)。
	 */
	public static final String	PRM_FILE_BINARY_ARRAY	= "fileBinaryArray";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanBinaryArrayCardAction() {
		super();
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
		} else if (mospParams.getCommand().equals(CMD_ADD_SELECT)) {
			// 追加画面表示
			prepareVo(true, false);
			addSelect();
		} else if (mospParams.getCommand().equals(CMD_ADD)) {
			// 登録
			prepareVo();
			add();
		} else if (mospParams.getCommand().equals(CMD_EDIT_SELECT)) {
			// 選択編集画面表示
			prepareVo(false, false);
			editSelect();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 更新
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
	private void setActivationDate() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
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
		return new HumanBinaryArrayCardVo();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	private void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 初期表示
		setDefaultValues();
	}
	
	/**
	 * 追加画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void addSelect() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 人事汎用管理区分設定
		vo.setDivision(getTransferredType());
		// パンくず名準備
		StringBuilder name = new StringBuilder(NameUtility.getName(mospParams, vo.getDivision()));
		name.append(PfNameUtility.addInfo(mospParams));
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
	 * 新規登録処理を行う。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void add() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 有効日取得
		Date activeDate = DateUtility.getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(),
				vo.getTxtActivateDay());
		// 入力値確認
		validate();
		// 結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 登録クラス取得
		HumanBinaryArrayRegistBeanInterface regist = platform().humanBinaryArrayRegist();
		// DTO取得
		HumanBinaryArrayDtoInterface dto = regist.getInitDto();
		// DTOに設定
		setDtoFields(dto, activeDate);
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
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 値再設定
		setBinaryArrayInfo(dto.getHumanRowId());
		// 履歴編集モードに設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 入力値確認
		validate();
		// 結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 登録クラス取得
		HumanBinaryArrayRegistBeanInterface regist = platform().humanBinaryArrayRegist();
		// 登録済のDTO取得
		HumanBinaryArrayDtoInterface oldDto = reference().humanBinaryArray().findForKey(vo.getHidRecordId(), false);
		// DTOに設定
		oldDto.setFileType(vo.getPltFileType());
		oldDto.setFileName(vo.getFileBinaryArray());
		oldDto.setFileRemark(vo.getTxtFileRemark());
		// 更新処理
		regist.update(oldDto);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 値再設定
		setBinaryArrayInfo(oldDto.getHumanRowId());
	}
	
	/**
	 * 人事汎用一覧情報を設定する。
	 * @param rowId 行ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setBinaryArrayInfo(int rowId) throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 参照クラス取得
		HumanBinaryArrayDtoInterface dto = reference().humanBinaryArray().findForKey(vo.getPersonalId(),
				vo.getDivision(), rowId);
		// 情報がない場合
		if (dto == null) {
			// 初期値設定
			setDefaultValues();
			return;
		}
		// 画面に設定
		setVoFields(dto);
	}
	
	/**
	 * 人事汎用一覧情報編集画面の選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void editSelect() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 人事汎用管理区分取得
		String division = getTransferredType();
		// 人事汎用管理区分設定
		vo.setDivision(division);
		// 行ID取得
		int rowId = getTransferredIndex();
		// 行ID設定
		vo.setRowId(rowId);
		// パンくず名準備
		StringBuilder name = new StringBuilder(NameUtility.getName(mospParams, division));
		name.append(PfNameUtility.editInfo(mospParams));
		// パンくず名設定
		TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), name.toString());
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_CODE_AND_NAME);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 一覧参照クラス取得
		HumanBinaryArrayDtoInterface dto = reference().humanBinaryArray().findForKey(vo.getPersonalId(), division,
				rowId);
		// プルダウン設定
		setPulldown();
		// VO設定
		setVoFields(dto);
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 登録クラス取得
		HumanBinaryArrayRegistBeanInterface regist = platform().humanBinaryArrayRegist();
		// 一覧参照クラス取得
		HumanBinaryArrayDtoInterface dto = reference().humanBinaryArray().findForKey(vo.getHidRecordId(), false);
		// 削除処理
		regist.delete(dto);
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
		// 初期化
		setDefaultValues();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
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
	 * VOに初期値を設定する。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setDefaultValues() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtActivateYear("");
		vo.setTxtActivateMonth("");
		vo.setTxtActivateDay("");
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_ADD);
		
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * プルダウンを設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void setPulldown() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// プルダウン設定
		MospProperties properties = mospParams.getProperties();
		String[][] aryBinaryFileType = properties.getCodeArray(PlatformConst.CODE_KEY_BINARY_FILE_TYPE, true);
		vo.setAryPltFileType(aryBinaryFileType);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(HumanBinaryArrayDtoInterface dto) {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 有効日編集項目に設定
		vo.setHidRecordId(dto.getPfaHumanBinaryArrayId());
		vo.setRowId(dto.getHumanRowId());
		vo.setTxtActivateYear(DateUtility.getStringYear(dto.getActivateDate()));
		vo.setTxtActivateMonth(DateUtility.getStringMonth(dto.getActivateDate()));
		vo.setTxtActivateDay(DateUtility.getStringDay(dto.getActivateDate()));
		vo.setPltFileType(dto.getFileType());
		vo.setFileBinaryArray(dto.getFileName());
		vo.setTxtFileRemark(dto.getFileRemark());
	}
	
	/**
	 * DTOに値を設定する。
	 * @param dto 人事バイナリ通常情報
	 * @param activeDate 有効日
	 * @throws MospException 例外処理が発生した場合
	 */
	private void setDtoFields(HumanBinaryArrayDtoInterface dto, Date activeDate) throws MospException {
		// ファイルをbyte[]で取得
		byte[] file = BinaryUtility.getBinaryData(mospParams.getRequestFile(PRM_FILE_BINARY_ARRAY));
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// DTOに設定
		dto.setPersonalId(vo.getPersonalId());
		dto.setHumanItemType(vo.getDivision());
		dto.setActivateDate(activeDate);
		dto.setHumanItemBinary(file);
		dto.setFileType(HumanUtility.getBinaryFileType(vo.getFileBinaryArray()));
		dto.setFileName(vo.getFileBinaryArray());
		dto.setFileRemark(vo.getTxtFileRemark());
	}
	
	/**
	 * 入力値妥当性確認
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void validate() throws MospException {
		// 処理無し
	}
	
	/**
	 * 当該画面の共通情報を設定する
	 */
	public void setCardCommonInfo() {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 人事汎用管理区分参照権限設定
		vo.setJsIsReferenceDivision(RoleUtility.getReferenceDivisionsList(mospParams).contains(getTransferredType()));
	}
}
