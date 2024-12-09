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
package jp.mosp.platform.workflow.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.base.PlatformBeanHandlerInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfmApprovalUnitDto;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.workflow.vo.UnitCardVo;

/**
 * 各種申請項目の承認者として個人単位や職位単位で設定するユニット情報の登録や削除を行う。<br>
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
public class UnitCardAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "PF3120";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * ユニット一覧画面で選択したレコードの情報を取得し、履歴編集モードで詳細画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "PF3121";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力項目に入力されている内容を基に新規登録モード、履歴追加モードであれば登録処理を、履歴編集モードであれば更新処理を実行する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、ユニットコードが登録済みのレコードのものと同一である、個人指定欄に正しい社員コードが入力されていない<br>
	 * といった場合はエラーメッセージにて通知し、登録・更新処理を中止する。<br>
	 */
	public static final String	CMD_REGIST				= "PF3125";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているレコード情報の削除を行う。<br>
	 */
	public static final String	CMD_DELETE				= "PF3127";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 有効日入力欄に入力されている日付時点で有効な所属情報、職位情報を取得し、コードと名称をプルダウンに表示する。<br>
	 * 有効日を決定することによって登録処理が可能となる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF3170";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "PF3171";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日入力欄を編集可能にした上で内容を空欄にする。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE			= "PF3173";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public UnitCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new UnitCardVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo();
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo();
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
			// 有効日決定
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
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void show() throws MospException {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * @throws MospException 例外発生時
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
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
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
	 * 新規登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		ApprovalUnitRegistBeanInterface regist = platform.approvalUnitRegist();
		// DTOの準備
		ApprovalUnitDtoInterface dto = new PfmApprovalUnitDto();
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
		setEditUpdateMode(dto.getUnitCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		ApprovalUnitRegistBeanInterface regist = platform.approvalUnitRegist();
		// DTOの準備
		ApprovalUnitDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 新規登録処理
		regist.add(dto);
		// 新規登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージを設定
		PfMessageUtility.addMessageAddHistorySucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getUnitCode(), dto.getActivateDate());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		ApprovalUnitRegistBeanInterface regist = platform.approvalUnitRegist();
		// DTOの準備
		ApprovalUnitDtoInterface dto = regist.getInitDto();
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
		// 履歴編集成功メッセージを設定
		PfMessageUtility.addMessageEditHistorySucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getUnitCode(), dto.getActivateDate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException  インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		ApprovalUnitRegistBeanInterface regist = platform.approvalUnitRegist();
		// DTOの準備
		ApprovalUnitDtoInterface dto = regist.getInitDto();
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
	 * 有効日決定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException 例外処理が発生した場合 
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
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
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// 有効な所属マスタ情報または職位マスタ情報が登録されていない。
			if (vo.getAryPltSectionMaster().length == 1 || vo.getAryPltPositionMaster().length == 1) {
				String masterName = "";
				if (vo.getAryPltSectionMaster().length == 1) {
					// 有効な所属マスタ情報が登録されていない。
					masterName = PfNameUtility.section(mospParams);
				} else if (vo.getAryPltPositionMaster().length == 1) {
					// 有効な職位マスタ情報が登録されていない。
					masterName = PfNameUtility.position(mospParams);
				}
				// 有効マスタ無しメッセージ設定
				if ((vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD))
						&& (vo.getRadUnitType().equals(PlatformConst.UNIT_TYPE_PERSON))) {
					// 履歴追加モードで個人指定の場合はメッセージを出力しない。
					// （ユニット区分は変更できないので所属指定となることはない。）
				} else {
					// エラーメッセージを設定
					PfMessageUtility.addErrorValidDataNotExist(mospParams, masterName);
				}
				// 新規登録モードの場合、個人指定ラジオボタン選択状態とする。
				if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
					vo.setRadUnitType(PlatformConst.UNIT_TYPE_PERSON);
				}
			}
		}
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
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void addMode() throws MospException {
		// VO準備
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
		// 履歴追加モード設定
		setEditAddMode();
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン取得
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
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
		// ユニットコード
		vo.setTxtUnitCode("");
		// ユニット名称
		vo.setTxtUnitName("");
		// 複数決済
		vo.setPltRouteStage(String.valueOf(MospConst.DELETE_FLAG_OFF));
		// 承認者設定
		// 所属名称プルダウン
		vo.setPltSectionMaster("");
		// 職位名称プルダウン
		vo.setPltPositionMaster("");
		// 個人指定欄
		vo.setTxtEmployeeCode("");
		// 所属指定承認部署
		vo.setLblSectionPosition("");
		// 個人指定承認者
		vo.setLblEmployeeCode("");
		// ラジオボタンの設定
		vo.setRadUnitType(PlatformConst.UNIT_TYPE_SECTION);
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * ユニットコードと有効日で編集対象情報を取得する。<br>
	 * @param unitCode       ユニットコード
	 * @param activateDate   有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String unitCode, Date activateDate) throws MospException {
		// VO準備
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
		// 参照クラス取得
		ApprovalUnitReferenceBeanInterface reference = reference().approvalUnit();
		// 履歴編集対象取得
		ApprovalUnitDtoInterface dto = reference.findForKey(unitCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 編集モード(履歴編集)設定
		setEditUpdateMode(reference.getApprovalUnitHistory(unitCode));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 人事マスタ情報の取得に失敗した場合
	 */
	protected void setDtoFields(ApprovalUnitDtoInterface dto) throws MospException {
		// VO準備
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
		// 有効日
		dto.setActivateDate(getEditActivateDate());
		// ユニットコード
		dto.setUnitCode(vo.getTxtUnitCode());
		// ユニット名称
		dto.setUnitName(vo.getTxtUnitName());
		// 複数決済
		dto.setRouteStage(getInt(vo.getPltRouteStage()));
		// 無効フラグ
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		// ラジオボタン
		dto.setUnitType(vo.getRadUnitType());
		// 承認者所属コード、承認者職位コード、承認者個人ID
		if (vo.getRadUnitType().equals(PlatformConst.UNIT_TYPE_SECTION)) {
			// 承認者所属コード
			dto.setApproverSectionCode(vo.getPltSectionMaster());
			// 承認者職位コード
			dto.setApproverPositionCode(vo.getPltPositionMaster());
			// 承認者職位等級
			dto.setApproverPositionGrade(vo.getPltPositionGradeRange());
			// 社員コード
			dto.setApproverPersonalId("");
		} else {
			// 承認者所属コード
			dto.setApproverSectionCode("");
			// 承認者職位コード
			dto.setApproverPositionCode("");
			// 承認者職位等級
			dto.setApproverPositionGrade("");
			// 入力された社員コードから個人IDを取得
			// 追加予定箇所
			dto.setApproverPersonalId(checkWithdrawal(
					reference().human().getPersonalIds(vo.getTxtEmployeeCode(), getEditActivateDate())));
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 人事マスタ情報の取得に失敗した場合
	 */
	protected void setVoFields(ApprovalUnitDtoInterface dto) throws MospException {
		// VO準備
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtUnitCode(dto.getUnitCode());
		vo.setTxtUnitName(dto.getUnitName());
		vo.setPltRouteStage(String.valueOf(dto.getRouteStage()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		vo.setRadUnitType(dto.getUnitType());
		vo.setPltSectionMaster(dto.getApproverSectionCode());
		vo.setPltPositionMaster(dto.getApproverPositionCode());
		vo.setPltPositionGradeRange(dto.getApproverPositionGrade());
		// 人事マスタ参照クラス準備
		HumanReferenceBeanInterface human = reference().human();
		// 個人指定欄
		vo.setTxtEmployeeCode(human.getEmployeeCodes(dto.getApproverPersonalId(), dto.getActivateDate()));
		// 個人指定承認者
		vo.setLblEmployeeCode(human.getHumanNames(dto.getApproverPersonalId(), dto.getActivateDate()));
		// ユニット区分確認
		if (vo.getRadUnitType().equals(PlatformConst.UNIT_TYPE_PERSON)) {
			return;
		}
		// 人事マスタ検索クラス準備
		HumanSearchBeanInterface humanSearch = reference().humanSearch();
		// 検索条件設定(対象日)
		humanSearch.setTargetDate(dto.getActivateDate());
		// 検索条件設定(所属コード)
		humanSearch.setSectionCode(dto.getApproverSectionCode());
		// 検索条件設定(職位コード)
		humanSearch.setPositionCode(dto.getApproverPositionCode());
		// 検索条件設定(職位等級範囲)
		humanSearch.setPositionGradeRange(dto.getApproverPositionGrade());
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(休退職区分)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 承認者を検索
		List<HumanDtoInterface> list = humanSearch.search();
		// 個人IDリスト準備
		List<String> personalIdList = new ArrayList<String>();
		// 個人IDリスト作成
		for (HumanDtoInterface humanDto : list) {
			personalIdList.add(humanDto.getPersonalId());
		}
		// 所属指定承認部署
		vo.setLblEmployeeCode(human.getHumanNames(personalIdList, dto.getActivateDate()));
	}
	
	/**
	 * プルダウンを設定する。<br>
	 * @throws MospException 所属マスタ情報、職位マスタ情報の取得に失敗した場合
	 */
	private void setPulldown() throws MospException {
		// VO準備
		UnitCardVo vo = (UnitCardVo)mospParams.getVo();
		// プルダウンの設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// プルダウン初期化
			vo.setAryPltSectionMaster(getInputActivateDatePulldown());
			vo.setAryPltPositionMaster(getInputActivateDatePulldown());
			// 個人指定承認者初期化
			vo.setLblEmployeeCode("");
		} else {
			Date date = getEditActivateDate();
			// 所属
			String[][] arySection = reference().section().getCodedSelectArray(date, true, null);
			vo.setAryPltSectionMaster(arySection);
			// 職位
			String[][] aryPosition = reference().position().getCodedSelectArray(date, true, null);
			vo.setAryPltPositionMaster(aryPosition);
		}
	}
	
	/**
	 * カンマ区切りの個人IDを取得してそのIDが退職されているかチェックする。
	 * @param id 個人ID
	 * @return 個人ID
	 * @throws MospException 退職情報の取得に失敗した場合
	 */
	protected String checkWithdrawal(String id) throws MospException {
		Date targetDate = getEditActivateDate();
		String[] arrayCode = id.split(",");
		// 退職情報チェック
		for (String element : arrayCode) {
			if (MospUtility.isEmpty(element)) {
				continue;
			}
			if (reference().retirement().isRetired(element, targetDate)) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeRetired(mospParams, targetDate, getEmployeeCode(element, targetDate));
				return MospConst.STR_EMPTY;
			}
		}
		return id;
	}
}
