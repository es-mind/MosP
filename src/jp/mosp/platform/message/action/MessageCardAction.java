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
package jp.mosp.platform.message.action;

import java.util.ArrayList;
import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.base.PlatformBeanHandlerInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.message.MessageReferenceBeanInterface;
import jp.mosp.platform.bean.message.MessageRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.message.MessageDtoInterface;
import jp.mosp.platform.message.vo.MessageCardVo;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * ポータル画面の通知欄に表示するメッセージの内容やその公開範囲について登録する。<br>
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
 * {@link #CMD_REPLICATION_MODE}
 * </li></ul>
 */
public class MessageCardAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * メッセージ設定一覧画面の新規登録ボタンをクリックした場合に実行される。<br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "PF4120";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * メッセージ設定一覧画面で表示されたレコードの詳細ボタンをクリックした場合はこのコマンドが実行される。<br>
	 * 該当レコードを編集するよう履歴編集モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "PF4121";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力項目に入力されている内容を基に新規登録モード、複製モードであれば登録処理を、履歴編集モードであれば更新処理を実行する。<br>
	 * 入力チェック時に公開開始日が決定されていない、メッセージとタイトルが入力されていない、個人指定欄に正しい社員コードが入力されていない<br>
	 * といった場合はエラーメッセージにて通知し、登録・更新処理を中止する。<br>
	 */
	public static final String	CMD_REGIST				= "PF4125";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているレコード情報の削除を行う。<br>
	 */
	public static final String	CMD_DELETE				= "PF4127";
	
	/**
	 * 開始日決定コマンド。<br>
	 * <br>
	 * 開始日入力欄に入力されている日付時点で有効な勤務地情報、雇用契約情報、所属情報、職位情報を取得し、コードと名称をそれぞれのプルダウンで表示する。<br>
	 * 公開開始日を決定することによって登録処理が可能となる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF4170";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "PF4171";
	
	/**
	 * 複製モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードからのみ切り替えることが可能。<br>
	 * 編集中のレコードの「No」と「登録者」のみクリアにしてその他のレコード内容を引き継いで新たなレコードとして登録することが可能。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_REPLICATION_MODE	= "PF4174";
	
	/**
	 * メール送信コマンド。<br>
	 */
	public static final String	CMD_SEND_MAIL			= "PF4175";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public MessageCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new MessageCardVo();
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
			prepareVo(false, false);
			selectShow();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 開始日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_REPLICATION_MODE)) {
			// 複製モード切替
			prepareVo();
			replicationMode();
		} else if (mospParams.getCommand().equals(CMD_SEND_MAIL)) {
			// メール送信
			prepareVo();
			sendMail();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void selectShow() throws MospException {
		// 編集モード設定
		editMode();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
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
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		MessageRegistBeanInterface regist = platform.messageRegist();
		// DTOの準備
		MessageDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 新規登録処理
		regist.insert(dto);
		// 新規登録結果確認
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
		setEditUpdateMode(dto.getMessageNo());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		MessageRegistBeanInterface regist = platform.messageRegist();
		// DTOの準備
		MessageDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 新規登録処理
		regist.update(dto);
		// 新規登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getMessageNo());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		MessageRegistBeanInterface regist = platform.messageRegist();
		// DTOの準備
		MessageDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 新規登録処理
		regist.delete(dto);
		// 新規登録結果確認
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
	 * 開始日決定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
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
	 * 新規登録モードに設定する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void insertMode() throws MospException {
		// 新規登録モード設定
		setEditInsertMode();
		// 初期値
		setDefaultValues();
		// VO取得
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コードで取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// 遷移汎用コードから履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode());
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * メッセージNoで編集対象情報を取得する。<br>
	 * @param messageNo メッセージNo
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String messageNo) throws MospException {
		// VO準備
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
		// 参照クラス取得
		MessageReferenceBeanInterface reference = reference().message();
		// 履歴編集対象取得
		MessageDtoInterface dto = reference.findForKey(messageNo);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 編集モード(履歴編集)設定(次前履歴情報設定不能)
		setEditUpdateMode(new ArrayList<MessageDtoInterface>());
	}
	
	/**
	 * 複製モードに設定する。<br>
	 */
	protected void replicationMode() {
		// 複製モード設定
		setEditReplicationMode();
		// VO準備
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
		// コードを空白に設定
		vo.setLblMessageNo("");
	}
	
	/**
	 * メール送信処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void sendMail() throws MospException {
		// VOを準備
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
		// メッセージNoを取得
		String messageNo = vo.getLblMessageNo();
		// メールを送信
		int count = reference().messageMail().send(messageNo);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// メッセージを設定
		addSendMailMessage(count);
	}
	
	/**
	 * プルダウンを設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
		// プルダウンの設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltWorkPlace(getInputActivateDatePulldown());
			vo.setAryPltEmployment(getInputActivateDatePulldown());
			vo.setAryPltSection(getInputActivateDatePulldown());
			vo.setAryPltPosition(getInputActivateDatePulldown());
			return;
		}
		// 編集有効日取得
		Date date = getEditActivateDate();
		// 勤務地
		vo.setAryPltWorkPlace(reference().workPlace().getCodedSelectArray(date, true, null));
		// 雇用契約
		vo.setAryPltEmployment(reference().employmentContract().getCodedSelectArray(date, true, null));
		// 所属
		vo.setAryPltSection(reference().section().getCodedSelectArray(date, true, null));
		// 職位
		vo.setAryPltPosition(reference().position().getCodedSelectArray(date, true, null));
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
		// 公開終了日
		vo.setTxtEndYear("");
		vo.setTxtEndMonth("");
		vo.setTxtEndDay("");
		vo.setPltMessageType("");
		vo.setPltImportance("");
		vo.setPltWorkPlace("");
		vo.setPltEmployment("");
		vo.setPltSection("");
		vo.setPltPosition("");
		vo.setTxtEmployeeCode("");
		vo.setTxtMessageTitle("");
		vo.setTxtMessage("");
		vo.setLblMessageNo("");
		vo.setLblRegistUser("");
		vo.setLblEmployeeName("");
		// ラジオボタンの設定
		vo.setRadApplicationType(PlatformConst.APPLICATION_TYPE_MASTER);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 人事マスタ情報の取得に失敗した場合
	 */
	protected void setDtoFields(MessageDtoInterface dto) throws MospException {
		// VO準備
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
		// レコード識別ID
		dto.setPftMessageId(vo.getRecordId());
		// メッセージNo
		dto.setMessageNo(vo.getLblMessageNo());
		// 公開開始日
		dto.setStartDate(getEditActivateDate());
		// 公開終了日
		dto.setEndDate(getDate(vo.getTxtEndYear(), vo.getTxtEndMonth(), vo.getTxtEndDay()));
		// メッセージ区分
		dto.setMessageType(getInt(vo.getPltMessageType()));
		// 重要度
		dto.setMessageImportance(getInt(vo.getPltImportance()));
		// メッセージタイトル
		dto.setMessageTitle(vo.getTxtMessageTitle());
		// メッセージ本文
		dto.setMessageBody(vo.getTxtMessage());
		// 無効フラグ
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		// メッセージ適用範囲区分
		dto.setApplicationType(getInt(vo.getRadApplicationType()));
		// マスタ組合及び個人ID
		if (vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_MASTER)) {
			// 勤務地コード
			dto.setWorkPlaceCode(vo.getPltWorkPlace());
			// 雇用契約コード
			dto.setEmploymentContractCode(vo.getPltEmployment());
			// 所属コード
			dto.setSectionCode(vo.getPltSection());
			// 職位コード
			dto.setPositionCode(vo.getPltPosition());
			// 個人ID
			dto.setPersonalId("");
		} else {
			// 勤務地コード
			dto.setWorkPlaceCode("");
			// 雇用契約コード
			dto.setEmploymentContractCode("");
			// 所属コード
			dto.setSectionCode("");
			// 職位コード
			dto.setPositionCode("");
			// 個人ID
			dto.setPersonalId(reference().human().getPersonalIds(vo.getTxtEmployeeCode(), getEditActivateDate()));
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 人事マスタ情報の取得に失敗した場合
	 */
	protected void setVoFields(MessageDtoInterface dto) throws MospException {
		// VO準備
		MessageCardVo vo = (MessageCardVo)mospParams.getVo();
		// 人事マスタ
		HumanReferenceBeanInterface human = reference().human();
		// レコード識別ID
		vo.setRecordId(dto.getPftMessageId());
		// メッセージNo
		vo.setLblMessageNo(dto.getMessageNo());
		// 公開開始日
		vo.setTxtEditActivateYear(getStringYear(dto.getStartDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getStartDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getStartDate()));
		// 公開終了日
		vo.setTxtEndYear(getStringYear(dto.getEndDate()));
		vo.setTxtEndMonth(getStringMonth(dto.getEndDate()));
		vo.setTxtEndDay(getStringDay(dto.getEndDate()));
		// メッセージ区分
		vo.setPltMessageType(String.valueOf(dto.getMessageType()));
		// 重要度
		vo.setPltImportance(String.valueOf(dto.getMessageImportance()));
		// メッセージタイトル
		vo.setTxtMessageTitle(dto.getMessageTitle());
		// メッセージ本文
		vo.setTxtMessage(dto.getMessageBody());
		// 無効フラグ
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		// メッセージ適用範囲区分
		vo.setRadApplicationType(String.valueOf(dto.getApplicationType()));
		// 勤務地コード
		vo.setPltWorkPlace(dto.getWorkPlaceCode());
		// 雇用契約コード
		vo.setPltEmployment(dto.getEmploymentContractCode());
		// 所属コード
		vo.setPltSection(dto.getSectionCode());
		// 職位コード
		vo.setPltPosition(dto.getPositionCode());
		// 個人ID
		vo.setTxtEmployeeCode(human.getEmployeeCodes(dto.getPersonalId(), dto.getStartDate()));
		// 社員名表示
		vo.setLblEmployeeName(human.getHumanNames(dto.getPersonalId(), dto.getStartDate()));
		// 登録者
		vo.setLblRegistUser(getInsertUserName(dto));
	}
	
	/**
	 * メール送信成功メッセージの設定。
	 * @param count 件数
	 */
	protected void addSendMailMessage(int count) {
		StringBuilder sb = new StringBuilder(NameUtility.count(mospParams, count));
		sb.append(PfNameUtility.sendMail(mospParams));
		PfMessageUtility.addMessageSucceed(mospParams, sb.toString());
	}
	
}
