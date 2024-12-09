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
import java.util.LinkedHashMap;
import java.util.Map;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.HumanHistoryCardVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用管理形式の履歴で履歴追加・履歴編集を行う。
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
 * </li></ul>
 * {@link #CMD_TRANSFER}
 * </li><li>
 */
public class HumanHistoryCardAction extends PlatformHumanAction {
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 各レコード毎に有効日が設定されている項目（所属情報、職位情報、雇用契約情報）に対して
	 * テキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なレコードのプルダウンリストを作成し、各種検索項目毎にセットする。<br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE		= "PF1531";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 */
	public static final String	CMD_SEARCH					= "PF1532";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこれよりも奥の階層の画面から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で再表示を行う。<br>
	 */
	public static final String	CMD_RE_SEARCH				= "PF1533";
	
	/**
	 * 履歴追加画面の選択表示コマンド。<br>
	 * <br>
	 * 遷移元の人事情報一覧画面で表示されていた社員の個人基本情報を取得し、
	 * 個人基本情報履歴追加画面へ遷移する。<br>
	 * 入力欄には登録済みの社員コードをデフォルトで表示させる。<br>
	 */
	public static final String	CMD_ADD_SELECT				= "PF1534";
	
	/**
	 * 履歴追加コマンド。<br>
	 * <br>
	 * 登録情報入力欄に入力された内容を基に社員情報の履歴追加を行う。<br>
	 * 入力チェックを行い、入力必須項目（有効日、姓）が入力されていない場合は
	 * エラーメッセージで通知する。<br>
	 */
	public static final String	CMD_ADD						= "PF1535";
	
	/**
	 * 履歴編集画面の選択表示コマンド。<br>
	 * <br>
	 * 遷移元の人事情報一覧画面で表示されていた社員の個人基本情報を取得し、
	 * 個人基本情報履歴編集画面へ遷移する。<br>
	 * 入力欄には登録済みの各種情報をデフォルトで表示させる。<br>
	 */
	public static final String	CMD_EDIT_SELECT				= "PF1536";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 登録情報入力欄に入力された内容を基に社員情報の履歴編集を行う。<br>
	 * 入力チェックを行い、入力必須項目（有効日、姓）が入力されていない場合はエラーメッセージで通知する。<br>
	 */
	public static final String	CMD_UPDATE					= "PF1538";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER				= "PF1539";
	
	/**
	 * 人事汎用管理表示区分(履歴)。<br>
	 */
	public static final String	KEY_VIEW_HUMAN_HISTORY_CARD	= "HistoryCard";
	
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HumanHistoryCardVo();
	}
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanHistoryCardAction() {
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
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
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
			// 既に情報がないか確認
			if (getSamaActiveDateInfo(personalId, activateDate, vo.getDivision())) {
				return;
			}
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			// モード設定
			vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
			// 有効日が近い情報設定
			setPersonalInfo(personalId, activateDate);
		} else if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン設定
		setPulldown();
		// 画面名区分取得(履歴編集/履歴追加)
		String imageName = getTransferredCommand();
		// 履歴追加画面区分をMosP処理情報に追加
		mospParams.addGeneralParam(KEY_VIEW_HUMAN_HISTORY_CARD, imageName);
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
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 人事管理共通情報設定
		setHumanCommonInfo(vo.getPersonalId(), vo.getTargetDate());
		// 有効日を空白に設定
		vo.setTxtActivateYear("");
		vo.setTxtActivateMonth("");
		vo.setTxtActivateDay("");
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
	}
	
	/**
	 * 履歴追加画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addSelect() throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
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
		// 履歴追加画面区分をMosP処理情報に追加
		mospParams.addGeneralParam(KEY_VIEW_HUMAN_HISTORY_CARD, CMD_ADD_SELECT);
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 人事汎用管理区分取得
		String division = vo.getDivision();
		// 有効日取得
		Date activeDate = getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
		// 登録クラス取得
		HumanHistoryRegistBeanInterface regist = platform().humanHistoryRegist();
		// 人事汎用履歴情報参照クラス取得
		HumanHistoryReferenceBeanInterface historyRefer = reference().humanHistory();
		
		// 履歴追加処理
		regist.add(vo.getDivision(), KEY_VIEW_HUMAN_HISTORY_CARD, vo.getPersonalId(), activeDate);
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
		// MosP処理情報設定
		mospParams.addGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE, DateUtility.getStringDate(activeDate));
		// プルダウンを取得
		vo.putPltItem(historyRefer.getHumanGeneralPulldown(division, KEY_VIEW_HUMAN_HISTORY_CARD, activeDate));
		// 人事マスタ情報取得
		setHistoryItem(division, activeDate);
		// 履歴編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
	}
	
	/**
	 * 履歴編集画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void editSelect() throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
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
		// 人事汎用履歴情報設定
		setEditHistoryInfo();
		// 遷移された有効日をMosP処理情報設定
		mospParams.addGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE, getTransferredActivateDate());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
		// MosP処理情報に追加
		mospParams.addGeneralParam(KEY_VIEW_HUMAN_HISTORY_CARD, CMD_EDIT_SELECT);
	}
	
	/**
	 * 人事汎用履歴情報を取得し、VOに設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setEditHistoryInfo() throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 人事汎用履歴情報初期化
		vo.getHumanHistoryMap().clear();
		// 遷移された有効日取得
		Date activeDate = DateUtility.getDate(getTransferredActivateDate());
		// 人事汎用管理区分取得
		String division = vo.getDivision();
		// 人事汎用履歴情報参照クラス取得
		HumanHistoryReferenceBeanInterface historyRefer = reference().humanHistory();
		// プルダウンを取得
		vo.putPltItem(historyRefer.getHumanGeneralPulldown(division, KEY_VIEW_HUMAN_HISTORY_CARD, activeDate));
		// 人事マスタ情報取得
		setHistoryItem(division, activeDate);
		// 有効日編集項目に設定
		vo.setTxtActivateYear(DateUtility.getStringYear(activeDate));
		vo.setTxtActivateMonth(DateUtility.getStringMonth(activeDate));
		vo.setTxtActivateDay(DateUtility.getStringDay(activeDate));
	}
	
	/**
	 * 履歴編集処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 登録クラス取得
		HumanHistoryRegistBeanInterface regist = platform().humanHistoryRegist();
		// 画面区分取得
		String division = vo.getDivision();
		// 個人ID取得
		String personalId = vo.getPersonalId();
		// 有効日取得
		Date activeDate = getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
		// 履歴追加処理
		regist.update(division, KEY_VIEW_HUMAN_HISTORY_CARD, personalId, activeDate, vo.getAryRecordIdMap());
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
		// 値初期化
		vo.getHumanHistoryMap().clear();
		// MosP処理情報設定
		mospParams.addGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE, DateUtility.getStringDate(activeDate));
		// 値再設定
		setHistoryItem(division, activeDate);
	}
	
	/**
	 * 画面遷移を行う。
	 */
	protected void transfer() {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
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
		} else if (actionName.equals(HumanHistoryListAction.class.getName())) {
			// 個人基本情報の履歴の一覧
			mospParams.setNextCommand(HumanHistoryListAction.CMD_SELECT);
		}
	}
	
	/**
	 * 履歴追加モードにおける画面の初期表示内容を設定する。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void setDefaultValues() throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 有効日を空白に設定
		vo.setTxtActivateYear("");
		vo.setTxtActivateMonth("");
		vo.setTxtActivateDay("");
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * プルダウンを設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void setPulldown() throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 履歴参照クラス取得
		HumanHistoryReferenceBeanInterface historyRefere = reference().humanHistory();
		// プルダウン設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.putPltItem(
					historyRefere.getInputActiveDateGeneralPulldown(vo.getDivision(), KEY_VIEW_HUMAN_HISTORY_CARD));
			return;
		}
		// 有効日取得
		Date activeDate = getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
		// プルダウン設定
		vo.putPltItem(historyRefere.getHumanGeneralPulldown(vo.getDivision(), KEY_VIEW_HUMAN_HISTORY_CARD, activeDate));
	}
	
	/**
	 * 古い情報から人事汎用履歴情報を取得し、画面に設定する。
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPersonalInfo(String personalId, Date activeDate) throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 人事汎用履歴情報初期化
		vo.getHumanHistoryMap().clear();
		// 人事汎用管理区分取得
		String division = vo.getDivision();
		// 人事汎用履歴情報参照クラス取得
		HumanHistoryReferenceBeanInterface historyRefer = reference().humanHistory();
		Map<String, String> historyMap = historyRefer.getBeforeHumanHistoryMapInfo(division,
				KEY_VIEW_HUMAN_HISTORY_CARD, personalId, activeDate, activeDate);
		// 人事有効日履歴情報汎用マップ初期化
		LinkedHashMap<String, Map<String, String>> historyHumanInfoMap = new LinkedHashMap<String, Map<String, String>>();
		historyHumanInfoMap.put(getStringDate(activeDate), historyMap);
		// 人事マスタ情報取得
		vo.putHistoryItem(division, historyHumanInfoMap);
		// 遷移された有効日をMosP処理情報設定
		mospParams.addGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE, getStringDate(activeDate));
	}
	
	/**
	 * 履歴追加の際、同じ有効日の情報がないか確認する。
	 * 有った場合エラーメッセージを出力する。
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @param division 人事汎用区分
	 * @return 確認結果(true：情報がある、false：情報がない)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected boolean getSamaActiveDateInfo(String personalId, Date activeDate, String division) throws MospException {
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 対象有効日情報取得
		setHistoryItem(division, activeDate);
		// 対象情報が存在する場合
		if (vo.getHistoryMapInfo(division).isEmpty() == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorHistoryDuplicate(mospParams);
			return true;
		}
		return false;
	}
	
	/**
	 * 当該画面の共通情報を設定する
	 */
	public void setCardCommonInfo() {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 人事汎用管理区分参照権限設定
		vo.setJsIsReferenceDivision(RoleUtility.getReferenceDivisionsList(mospParams).contains(getTransferredType()));
	}
	
	/**
	 * 人事履歴情報取得
	 * @param division 人事汎用管理区分
	 * @param activeDate 有効日
	 * @throws MospException SQL実行に失敗した場合
	 */
	protected void setHistoryItem(String division, Date activeDate) throws MospException {
		// VO取得
		HumanHistoryCardVo vo = (HumanHistoryCardVo)mospParams.getVo();
		// 人事汎用履歴情報参照クラス取得
		HumanHistoryReferenceBeanInterface historyRefer = reference().humanHistory();
		// 人事履歴情報取得
		historyRefer.getHistoryRecordMapInfo(vo.getDivision(), KEY_VIEW_HUMAN_HISTORY_CARD, vo.getPersonalId(),
				activeDate);
		
		// レコード識別IDのマップを設定
		vo.setAryRecordIdMap(historyRefer.getRecordsMap());
		// 人事マスタ情報取得
		vo.putHistoryItem(division, historyRefer.getHistoryHumanInfoMap());
		
	}
}
