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
package jp.mosp.platform.workflow.action;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverRegistBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverSearchBeanInterface;
import jp.mosp.platform.comparator.base.ActivateDateComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;
import jp.mosp.platform.dto.workflow.SubApproverListDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.system.base.PlatformSystemVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.workflow.vo.SubApproverSettingVo;

/**
 * ログイン中のユーザが持つ承認権限（ルート適用情報）を期間を指定して代理人に委譲する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_SET_EMPLOYEE}
 * </li></ul>
 */
public class SubApproverSettingAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "PF3400";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を基に代理人情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH				= "PF3402";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層にあたる画面からパンくずリスト等で再び遷移した際に
	 * 登録内容等を反映させた上で自動で検索を行い、再表示させる。<br>
	 */
	public static final String	CMD_RE_SEARCH			= "PF3403";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 編集欄に入力されている内容を基に新規登録モードであれば登録処理を、
	 * 履歴編集モードであれば更新処理を実行する。<br>
	 */
	public static final String	CMD_REGIST				= "PF3405";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "PF3408";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "PF3409";
	
	/**
	 * 代理開始日決定コマンド。<br>
	 * <br>
	 * 決定した代理開始日時点で有効な所属情報と職位情報を取得し、
	 * "コード＋名称"を各プルダウンで表示する。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF3490";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "PF3491";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を編集テーブルの各入力欄にそれぞれ表示させ、編集モードへ切り替える。<br>
	 */
	public static final String	CMD_EDIT_MODE			= "PF3492";
	
	/**
	 * 社員候補決定コマンド。<br>
	 * <br>
	 * 代理人所属プルダウン、代理人職位プルダウン、社員コードテキストボックスの
	 * 入力内容をそれぞれ参照し、各条件にあてはまる社員情報を取得し、
	 * 社員コード＋氏名"を代理人プルダウンに表示する。<br>
	 */
	public static final String	CMD_SET_EMPLOYEE		= "PF3497";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public SubApproverSettingAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SubApproverSettingVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 代理開始日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 編集モード切替
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_SET_EMPLOYEE)) {
			// 社員候補決定
			prepareVo();
			setEmployeePulldown();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// ログインユーザの個人IDを設定
		vo.setPersonalId(mospParams.getUser().getPersonalId());
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(ActivateDateComparator.class.getName());
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 代理開始日決定処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
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
	 * 代理人プルダウンの設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setEmployeePulldown() throws MospException {
		// VO取得
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// 人事マスタ検索クラス取得
		HumanSearchBeanInterface humanSearch = reference().humanSearch();
		// 検索条件設定(対象日)
		humanSearch.setTargetDate(getEditActivateDate());
		// 検索条件設定(社員コード(前方一致))
		humanSearch.setEmployeeCode(vo.getTxtEditEmployeeCode());
		humanSearch.setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		// 検索条件設定(所属コード)
		humanSearch.setSectionCode(vo.getPltEditSection());
		// 検索条件設定(職位コード)
		humanSearch.setPositionCode(vo.getPltEditPosition());
		// 検索条件設定(休退職区分(在職))
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 検索条件設定(除去個人ID)(代理元は不要)
		humanSearch.setUnnecessaryPersonalId(vo.getPersonalId());
		// 検索条件設定(下位所属要否)
		humanSearch.setNeedLowerSection(true);
		// 検索条件設定(承認ロール要否)
		humanSearch.setNeedApproverRole(true);
		// プルダウンを取得してVOに設定
		vo.setAryPltEditEmployee(humanSearch.getCodedSelectArray(false));
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void insertMode() throws MospException {
		// VO取得
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// 新規登録モード設定
		setEditInsertMode();
		// 初期値設定
		vo.setSubApproverNo("");
		vo.setTxtEditEndYear("");
		vo.setTxtEditEndMonth("");
		vo.setTxtEditEndDay("");
		vo.setTxtEditEmployeeCode("");
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン(編集)設定
		setPulldown();
	}
	
	/**
	 * 編集モードで画面を表示する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// 遷移汎用コード、遷移汎用区分、遷移有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode());
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 代理承認者登録Noで編集対象情報を取得する。<br>
	 * @param subApproverNo 代理承認者登録No
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String subApproverNo) throws MospException {
		// VO準備
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// 履歴編集対象取得
		SubApproverDtoInterface dto = reference().subApprover().findForKey(subApproverNo);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 社員コード設定
		vo.setTxtEditEmployeeCode(reference().human().getEmployeeCode(dto.getSubApproverId(), dto.getStartDate()));
		// 所属及び職位初期化
		vo.setPltEditSection("");
		vo.setPltEditPosition("");
		// 代理人プルダウン設定
		setEmployeePulldown();
		// 編集モード(履歴編集)設定(次前履歴情報設定不能)
		setEditUpdateMode(new ArrayList<SubApproverDtoInterface>());
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// 検索クラス取得
		SubApproverSearchBeanInterface search = reference().subApproverSearch();
		// VOの値を検索クラスへ設定
		search.setPersonalId(vo.getPersonalId());
		search.setTargetYear(getInt(vo.getTxtSearchActivateYear()));
		search.setTargetMonth(getInt(vo.getTxtSearchActivateMonth()));
		search.setSectionName(vo.getTxtSearchSectionName());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<SubApproverListDtoInterface> list = search.getSearchList();
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
	 * 一覧のソート処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void sort() throws MospException {
		setVoList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * 一覧のページ処理を行う。
	 * @throws MospException 検索結果リストの取得に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
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
		SubApproverRegistBeanInterface regist = platform().subApproverRegist();
		// DTOの準備
		SubApproverDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getSubApproverNo());
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
		// 登録クラス取得
		SubApproverRegistBeanInterface regist = platform().subApproverRegist();
		// DTOの準備
		SubApproverDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getSubApproverNo());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * プルダウンの設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltEditSection(getInputStartDatePulldown());
			vo.setAryPltEditPosition(getInputStartDatePulldown());
			vo.setAryPltEditEmployee(getInputStartDatePulldown());
			return;
		}
		// プルダウン設定
		vo.setAryPltEditSection(reference().section().getCodedSelectArray(getEditActivateDate(), true, null));
		vo.setAryPltEditPosition(reference().position().getCodedSelectArray(getEditActivateDate(), true, null));
		// 代理人プルダウン初期化
		vo.setAryPltEditEmployee(getSearchCodePulldown());
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblSubApproverNo = new String[list.size()];
		String[] aryLblStartDate = new String[list.size()];
		String[] aryLblEndDate = new String[list.size()];
		String[] aryLblWorkflowType = new String[list.size()];
		String[] aryLblWorkflowName = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			SubApproverListDtoInterface dto = (SubApproverListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblSubApproverNo[i] = dto.getSubApproverNo();
			aryLblStartDate[i] = getStringDate(dto.getStartDate());
			aryLblEndDate[i] = getStringDate(dto.getEndDate());
			aryLblWorkflowType[i] = String.valueOf(dto.getWorkflowType());
			aryLblWorkflowName[i] = getCodeName(dto.getWorkflowType(), PlatformConst.CODE_KEY_WORKFLOW_TYPE);
			aryLblEmployeeCode[i] = dto.getSubApproverCode();
			aryLblEmployeeName[i] = dto.getSubApproverName();
			aryLblSection[i] = dto.getSubApproverSectionName();
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryLblSubApproverNo(aryLblSubApproverNo);
		vo.setAryLblActivateDate(aryLblStartDate);
		vo.setAryLblEndDate(aryLblEndDate);
		vo.setAryLblWorkflowType(aryLblWorkflowType);
		vo.setAryLblWorkflowName(aryLblWorkflowName);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoFields(SubApproverDtoInterface dto) throws MospException {
		// VOを取得しVOの値をDTOに設定
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// レコード識別ID
		dto.setPftSubApproverId(vo.getRecordId());
		// 代理承認者登録No
		dto.setSubApproverNo(vo.getSubApproverNo());
		// 代理元個人ID
		dto.setPersonalId(vo.getPersonalId());
		// 代理開始日
		dto.setStartDate(getEditActivateDate());
		// 代理終了日
		dto.setEndDate(getDate(vo.getTxtEditEndYear(), vo.getTxtEditEndMonth(), vo.getTxtEditEndDay()));
		// 代理承認者個人ID(社員コードと代理開始日から個人IDを取得)
		dto.setSubApproverId(reference().human().getPersonalId(vo.getPltEditEmployeeName(), getEditActivateDate()));
		// フロー区分
		dto.setWorkflowType(getInt(vo.getPltEditWorkflowType()));
		// 無効フラグ
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(SubApproverDtoInterface dto) {
		// VOを取得しDTOの値をVOに設定
		SubApproverSettingVo vo = (SubApproverSettingVo)mospParams.getVo();
		// レコード識別ID
		vo.setRecordId(dto.getPftSubApproverId());
		// 代理承認者登録No
		vo.setSubApproverNo(dto.getSubApproverNo());
		// 代理元個人ID
		vo.setPersonalId(dto.getPersonalId());
		// 代理開始日
		vo.setTxtEditActivateYear(getStringYear(dto.getStartDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getStartDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getStartDate()));
		// 代理終了日
		vo.setTxtEditEndYear(getStringYear(dto.getEndDate()));
		vo.setTxtEditEndMonth(getStringMonth(dto.getEndDate()));
		vo.setTxtEditEndDay(getStringDay(dto.getEndDate()));
		// 代理承認者個人ID
		vo.setPltEditEmployeeName(dto.getSubApproverId());
		// フロー区分
		vo.setPltEditWorkflowType(String.valueOf(dto.getWorkflowType()));
		// 無効フラグ
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
}
