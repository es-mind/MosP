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

import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.base.PlatformBeanHandlerInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfmRouteApplicationDto;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.workflow.vo.RouteApplicationCardVo;

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
public class RouteApplicationCardAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * ルート適用一覧画面の新規登録ボタンをクリックした場合はこのコマンドが実行され、
	 * 「ルート適用詳細画面」として新規登録モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "PF3320";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * ルート適用一覧画面の詳細ボタンをクリックした場合はこのコマンドが実行され、「ルート適用一覧画面」として初期表示を行う。<br>
	 * ルート適用一覧画面で選択したレコードを編集するために履歴編集モードで表示される。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "PF3321";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力項目に入力されている内容を基に新規登録モード、履歴追加モードであれば登録処理を、履歴編集モードであれば更新処理を実行する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、ルート適用コードが登録済みのレコードのものと同一である、個人指定欄に正しい社員コードが入力されていない<br>
	 * といった場合はエラーメッセージにて通知し、登録・更新処理を中止する。<br>
	 */
	public static final String	CMD_REGIST				= "PF3325";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているレコード情報の削除を行う。<br>
	 */
	public static final String	CMD_DELETE				= "PF3327";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 有効日入力欄に入力されている日付時点で有効なルート情報、勤務地情報、雇用契約情報、所属情報、職位情報を取得し、コードと名称をそれぞれのプルダウンで表示する。<br>
	 * 有効日を決定することによって登録処理が可能となる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF3370";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "PF3371";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日入力欄を編集可能にした上で内容を空欄にする。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE			= "PF3373";
	
	/*
	 * 特殊表示コマンド。<br>
	 * <br>
	 * 承認経路一覧の詳細ボタンをクリックした場合はこのコマンドが実行され、「承認経路詳細画面」として初期表示を行う。<br>
	 * 履歴編集モードと同様に前画面で選択したレコードの情報が表示されるが、入力項目・ボタンは全て読取専用となっている。<br>
	 */
	// public static final String	CMD_SPECIAL_DISPLAY		= "PF3421";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public RouteApplicationCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new RouteApplicationCardVo();
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
			/*
			} else if (mospParams.getCommand().equals(CMD_SPECIAL_DISPLAY)) {
				// 特殊表示【未使用】
				prepareVo();
			*/
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
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
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
		RouteApplicationRegistBeanInterface regist = platform.routeApplicationRegist();
		// DTOの準備
		RouteApplicationDtoInterface dto = new PfmRouteApplicationDto();
		// DTOに値を設定
		setDtoFields(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
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
		setEditUpdateMode(dto.getRouteApplicationCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void add() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		RouteApplicationRegistBeanInterface regist = platform.routeApplicationRegist();
		// DTOの準備
		RouteApplicationDtoInterface dto = new PfmRouteApplicationDto();
		// DTOに値を設定
		setDtoFields(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
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
		setEditUpdateMode(dto.getRouteApplicationCode(), dto.getActivateDate());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		RouteApplicationRegistBeanInterface regist = platform.routeApplicationRegist();
		// DTOの準備
		RouteApplicationDtoInterface dto = new PfmRouteApplicationDto();
		// DTOに値を設定
		setDtoFields(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
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
		setEditUpdateMode(dto.getRouteApplicationCode(), dto.getActivateDate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException  インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		RouteApplicationRegistBeanInterface regist = platform.routeApplicationRegist();
		// DTOの準備
		RouteApplicationDtoInterface dto = new PfmRouteApplicationDto();
		// DTOに値を設定
		setDtoFields(dto);
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
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
	}
	
	/**
	 * 有効日決定処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合 
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
		RouteApplicationReferenceBeanInterface routeApplication = reference().routeApplication();
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
		RouteApplicationDtoInterface routeApplicationDto = routeApplication
			.getRouteApplicationInfo(vo.getTxtApplicationCode(), getEditActivateDate());
		if (routeApplicationDto == null) {
			return;
		}
		vo.setPltRouteName(routeApplicationDto.getRouteCode());
		
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
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
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
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
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
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
		// ルート適用コード
		vo.setTxtApplicationCode("");
		// ルート適用名称
		vo.setTxtApplicationName("");
		// ルート
		vo.setPltRouteName("");
		// フロー区分
		vo.setPltFlowType("");
		// マスタ組合せ指定
		// 勤務地名称プルダウン
		vo.setPltWorkPlace("");
		// 雇用契約プルダウン
		vo.setPltEmployment("");
		// 所属名称プルダウン
		vo.setPltSection("");
		// 職位名称プルダウン
		vo.setPltPosition("");
		// 個人指定欄
		vo.setTxtEmployeeCode("");
		// 個人指定承認者
		vo.setLblSectionPosition("");
		// ラジオボタンの設定
		vo.setRadioSelect(PlatformConst.APPLICATION_TYPE_MASTER);
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * ルート適用コードと有効日で編集対象情報を取得する。<br>
	 * @param routeApplicationCode		ルート適用コード
	 * @param activateDate				有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void setEditUpdateMode(String routeApplicationCode, Date activateDate) throws MospException {
		// VO準備
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
		// 参照クラス取得
		RouteApplicationReferenceBeanInterface reference = reference().routeApplication();
		// 履歴編集対象取得
		RouteApplicationDtoInterface dto = reference.findForKey(routeApplicationCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 編集モード(履歴編集)設定
		setEditUpdateMode(reference.getRouteApplicationHistory(routeApplicationCode));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 人事マスタ情報の取得に失敗した場合
	 */
	private void setDtoFields(RouteApplicationDtoInterface dto) throws MospException {
		// VO準備
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
		// 有効日
		dto.setActivateDate(getEditActivateDate());
		// ルート適用コード
		dto.setRouteApplicationCode(vo.getTxtApplicationCode());
		// ルート適用名称
		dto.setRouteApplicationName(vo.getTxtApplicationName());
		// ルート
		dto.setRouteCode(vo.getPltRouteName());
		// フロー区分
		dto.setWorkflowType(getInt(vo.getPltFlowType()));
		// 無効フラグ
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		// ラジオボタン
		dto.setRouteApplicationType(getInt(vo.getRadioSelect()));
		// 承認者所属コード、承認者職位コード、承認者個人ID
		if (vo.getRadioSelect().equals(PlatformConst.APPLICATION_TYPE_MASTER)) {
			// 勤務地コード
			dto.setWorkPlaceCode(vo.getPltWorkPlace());
			// 雇用契約コード
			dto.setEmploymentContractCode(vo.getPltEmployment());
			// 所属コード
			dto.setSectionCode(vo.getPltSection());
			// 職位コード
			dto.setPositionCode(vo.getPltPosition());
			// 社員コード
			dto.setPersonalIds("");
		} else {
			// 勤務地コード
			dto.setWorkPlaceCode("");
			// 雇用契約コード
			dto.setEmploymentContractCode("");
			// 所属コード
			dto.setSectionCode("");
			// 職位コード
			dto.setPositionCode("");
			// 入力された社員コードから個人IDを取得
			dto.setPersonalIds(reference().human().getPersonalIds(vo.getTxtEmployeeCode(), getEditActivateDate()));
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 人事マスタ情報の取得に失敗した場合
	 */
	private void setVoFields(RouteApplicationDtoInterface dto) throws MospException {
		// VO準備
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
		// 人事マスタ
		HumanReferenceBeanInterface human = reference().human();
		// DTOの値をVOに設定
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtApplicationCode(dto.getRouteApplicationCode());
		vo.setTxtApplicationName(dto.getRouteApplicationName());
		vo.setPltRouteName(dto.getRouteCode());
		vo.setPltFlowType(String.valueOf(dto.getWorkflowType()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		vo.setRadioSelect(String.valueOf(dto.getRouteApplicationType()));
		vo.setPltWorkPlace(dto.getWorkPlaceCode());
		vo.setPltEmployment(dto.getEmploymentContractCode());
		vo.setPltSection(dto.getSectionCode());
		vo.setPltPosition(dto.getPositionCode());
		// ユニット区分確認
		if (vo.getRadioSelect().equals(PlatformConst.APPLICATION_TYPE_PERSON)) {
			// 個人指定欄
			vo.setTxtEmployeeCode(human.getEmployeeCodes(dto.getPersonalIds(), dto.getActivateDate()));
			// 個人指定承認者
			vo.setLblSectionPosition(human.getHumanNames(dto.getPersonalIds(), dto.getActivateDate()));
		} else {
			// 個人指定欄
			vo.setTxtEmployeeCode("");
			// 個人指定承認者
			vo.setLblSectionPosition("");
		}
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 各種マスタ情報の取得に失敗した場合
	 */
	private void setPulldown() throws MospException {
		// VO準備
		RouteApplicationCardVo vo = (RouteApplicationCardVo)mospParams.getVo();
		// プルダウンの設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltRouteName(getInputActivateDatePulldown());
			vo.setAryPltWorkPlace(getInputActivateDatePulldown());
			vo.setAryPltEmployment(getInputActivateDatePulldown());
			vo.setAryPltSectionMaster(getInputActivateDatePulldown());
			vo.setAryPltPositionMaster(getInputActivateDatePulldown());
		} else {
			Date date = getEditActivateDate();
			// ルート
			String[][] aryRouteName = reference().approvalRoute().getCodedSelectArray(date, true);
			vo.setAryPltRouteName(aryRouteName);
			// 勤務地
			String[][] aryWorkPlace = reference().workPlace().getCodedSelectArray(date, true, null);
			vo.setAryPltWorkPlace(aryWorkPlace);
			// 雇用契約
			String[][] aryEmployment = reference().employmentContract().getCodedSelectArray(date, true, null);
			vo.setAryPltEmployment(aryEmployment);
			// 所属
			String[][] arySection = reference().section().getCodedSelectArray(date, true, null);
			vo.setAryPltSectionMaster(arySection);
			// 職位
			String[][] aryPosition = reference().position().getCodedSelectArray(date, true, null);
			vo.setAryPltPositionMaster(aryPosition);
		}
	}
	
}
