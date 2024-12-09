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
package jp.mosp.time.settings.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.WorkTypePatternCardBeanInterface;
import jp.mosp.time.bean.WorkTypePatternItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternRegistBeanInterface;
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternItemDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.WorkTypePatternCardVo;

/**
 * 勤務形態パターン情報の個別詳細情報の確認、編集を行う。<br>
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
public class WorkTypePatternCardAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String		CMD_SHOW				= "TM5820";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 勤務形態パターン一覧画面で選択したレコードの情報を取得し、詳細画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW			= "TM5821";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力欄に入力されている情報を元に勤務形態パターンマスタテーブルに登録する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、またはパターンコードが登録済のレコードのものと同一であった場合、
	 * エラーメッセージを表示する。<br>
	 */
	public static final String		CMD_REGIST				= "TM5825";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているレコード情報の削除を行う。<br>
	 */
	public static final String		CMD_DELETE				= "TM5827";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 勤務形態マスタを参照する。<br>
	 * マスタからここで決定した有効日時点で有効なレコードのリストを作成し、
	 * プルダウンに表示する。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE	= "TM5870";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE			= "TM5871";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String		CMD_ADD_MODE			= "TM5873";
	
	/**
	 * コードキー(追加処理)。<br>
	 */
	protected static final String	CODE_KEY_ADDONS			= "WorkTypePatternAddons";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public WorkTypePatternCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new WorkTypePatternCardVo();
	}
	
	@Override
	protected BaseVo prepareVo(boolean useStoredVo, boolean useParametersMapper) throws MospException {
		// 継承基のメソッドを実行
		BaseVo vo = super.prepareVo(useStoredVo, useParametersMapper);
		// パラメータをVOにマッピングしない場合
		if (useParametersMapper == false) {
			// VOを取得
			return vo;
		}
		// 勤怠設定追加処理毎に処理
		for (WorkTypePatternCardBeanInterface addonBean : getAddonBeans()) {
			// 勤怠設定詳細画面VOにリクエストパラメータを設定
			addonBean.mapping();
		}
		// VOを取得
		return vo;
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
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
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void regist() throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
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
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// 登録クラス取得
		WorkTypePatternRegistBeanInterface regist = time().workTypePatternRegist();
		// DTOの準備
		WorkTypePatternDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 登録処理
		regist.insert(dto);
		time().workTypePatternItemRegist().insert(vo.getTxtPatternCode(), getActivateDate(),
				getInt(vo.getPltEditInactivate()), vo.getJsPltSelectSelected());
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
		setEditUpdateMode(dto.getPatternCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// 登録クラス取得
		WorkTypePatternRegistBeanInterface regist = time().workTypePatternRegist();
		// DTOの準備
		WorkTypePatternDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
		time().workTypePatternItemRegist().add(vo.getTxtPatternCode(), getActivateDate(),
				getInt(vo.getPltEditInactivate()), vo.getJsPltSelectSelected());
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
		// 履歴編集モード設定
		setEditUpdateMode(dto.getPatternCode(), dto.getActivateDate());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// 登録クラス取得
		WorkTypePatternRegistBeanInterface regist = time().workTypePatternRegist();
		// DTOの準備
		WorkTypePatternDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.update(dto);
		time().workTypePatternItemRegist().update(vo.getTxtPatternCode(), getActivateDate(),
				getInt(vo.getPltEditInactivate()), vo.getJsPltSelectSelected());
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
		// 履歴編集モード設定
		setEditUpdateMode(dto.getPatternCode(), dto.getActivateDate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// 登録クラス取得
		WorkTypePatternRegistBeanInterface regist = time().workTypePatternRegist();
		// DTOの準備
		WorkTypePatternDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 削除処理
		time().workTypePatternRegist().delete(dto);
		time().workTypePatternItemRegist().delete(vo.getTxtPatternCode(), getActivateDate());
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
	 * @throws MospException 例外発生時
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 新規登録モードで画面を表示する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// 編集モード設定
		setEditInsertMode();
		// 初期値設定
		setDefaultValues();
		// 有効日モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 */
	protected void addMode() {
		// 履歴追加モード設定
		setEditAddMode();
		// 有効日モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// 初期値設定
		setDefaultValues();
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode(), getDate(getTransferredActivateDate()));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * パターンコードと有効日で編集対象情報を取得する。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String patternCode, Date activateDate) throws MospException {
		// 参照クラス取得
		WorkTypePatternReferenceBeanInterface reference = timeReference().workTypePattern();
		WorkTypePatternItemReferenceBeanInterface itemReference = timeReference().workTypePatternItem();
		// 履歴編集対象取得
		WorkTypePatternDtoInterface dto = reference.findForKey(patternCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		setVoFields(itemReference.getWorkTypePatternItemList(dto.getPatternCode(), dto.getActivateDate()));
		// 有効日モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 編集モード設定
		setEditUpdateMode(reference.getWorkTypePatternHistory(patternCode));
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	public void setDefaultValues() throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		vo.setTxtPatternCode("");
		vo.setTxtPatternName("");
		vo.setTxtPatternAbbr("");
		vo.setJsPltSelectSelected(new String[0]);
		// 追加JSPリストを設定
		vo.setAddonJsps(getAddonJsps());
		// 追加パラメータ群(キー：パラメータ名)を初期化
		vo.setAddonParams(new HashMap<String, String>());
		// 追加パラメータ配列群(キー：パラメータ名)を初期化
		vo.setAddonArrays(new HashMap<String, String[]>());
		// 追加処理毎に処理
		for (WorkTypePatternCardBeanInterface addonBean : getAddonBeans()) {
			// VOに初期値を設定
			addonBean.initVoFields();
		}
	}
	
	/**
	 * プルダウンを設定する。
	 * @throws MospException 例外発生時
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// 初期化
		vo.setJsPltSelectTable(new String[0][0]);
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// 画面で指定した年月を取得
			int targetYear = getInt(vo.getTxtEditActivateYear());
			int targetMonth = getInt(vo.getTxtEditActivateMonth());
			// 年月指定時の基準日を取得
			Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
			// プルダウン設定(データ区分を条件にコード配列を取得)
			vo.setJsPltSelectTable(timeReference().workType().getNameTimeSelectArray(targetDate));
			// 追加処理毎に処理
			for (WorkTypePatternCardBeanInterface addonBean : getAddonBeans()) {
				// VOに初期値を設定
				addonBean.setPulldown();
			}
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFields(WorkTypePatternDtoInterface dto) throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmmWorkTypePatternId(vo.getRecordId());
		dto.setActivateDate(getActivateDate());
		dto.setPatternCode(vo.getTxtPatternCode());
		dto.setPatternName(vo.getTxtPatternName());
		dto.setPatternAbbr(vo.getTxtPatternAbbr());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void setVoFields(WorkTypePatternDtoInterface dto) throws MospException {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmmWorkTypePatternId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtPatternCode(dto.getPatternCode());
		vo.setTxtPatternName(dto.getPatternName());
		vo.setTxtPatternAbbr(dto.getPatternAbbr());
		vo.setPltEditInactivate(Integer.toString(dto.getInactivateFlag()));
		// 追加処理毎に処理
		for (WorkTypePatternCardBeanInterface addonBean : getAddonBeans()) {
			// VOに初期値を設定
			addonBean.setVoFields();
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list 対象DTOリスト
	 */
	protected void setVoFields(List<WorkTypePatternItemDtoInterface> list) {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// 設定配列準備
		String[] jsPltSelectSelected = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			WorkTypePatternItemDtoInterface dto = list.get(i);
			jsPltSelectSelected[i] = dto.getWorkTypeCode();
		}
		// DTOの値をVOに設定
		vo.setJsPltSelectSelected(jsPltSelectSelected);
	}
	
	/**
	 * 有効日を取得する。<br>
	 * 画面で指定した年月を表す日付(年月の初日)を取得する。<br>
	 * @return 有効日
	 */
	protected Date getActivateDate() {
		// VO取得
		WorkTypePatternCardVo vo = (WorkTypePatternCardVo)mospParams.getVo();
		// 画面で指定した年月を取得
		int targetYear = getInt(vo.getTxtEditActivateYear());
		int targetMonth = getInt(vo.getTxtEditActivateMonth());
		// 年月を表す日付(年月の初日)を取得
		return MonthUtility.getYearMonthDate(targetYear, targetMonth);
	}
	
	/**
	 * 追加処理リストを取得する。<br>
	 * @return 追加処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<WorkTypePatternCardBeanInterface> getAddonBeans() throws MospException {
		// 追加処理リストを準備
		List<WorkTypePatternCardBeanInterface> addonBeans = new ArrayList<WorkTypePatternCardBeanInterface>();
		// 追加処理配列毎に処理
		for (String[] addon : getCodeArray(CODE_KEY_ADDONS, false)) {
			// 追加処理を取得
			String addonBean = addon[0];
			// 追加処理が設定されていない場合
			if (MospUtility.isEmpty(addonBean)) {
				// 次の追加処理へ
				continue;
			}
			// 追加処理を取得
			WorkTypePatternCardBeanInterface bean = (WorkTypePatternCardBeanInterface)platform().createBean(addonBean);
			// 追加処理リストに値を追加
			addonBeans.add(bean);
		}
		// 追加処理リストを取得
		return addonBeans;
	}
	
	/**
	 * 追加JSPリストを取得する。<br>
	 * @return 追加JSPリスト
	 */
	protected List<String> getAddonJsps() {
		// 追加JSPリストを準備
		List<String> addonJsps = new ArrayList<String>();
		// 追加処理配列毎に処理
		for (String[] addon : getCodeArray(CODE_KEY_ADDONS, false)) {
			// 追加JSPを取得
			String addonJsp = addon[1];
			// 追加JSPが設定されていない場合
			if (MospUtility.isEmpty(addonJsp)) {
				// 次の追加JSPへ
				continue;
			}
			// 追加JSPリストに値を追加
			addonJsps.add(addonJsp);
		}
		// 追加JSPリストを取得
		return addonJsps;
	}
	
	/**
	 * 登録時追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void registAddon() throws MospException {
		// 追加処理毎に処理
		for (WorkTypePatternCardBeanInterface addonBean : getAddonBeans()) {
			// 登録時追加処理
			addonBean.regist();
		}
	}
	
}
