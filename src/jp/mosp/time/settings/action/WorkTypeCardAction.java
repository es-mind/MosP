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
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.WorkTypeBeanInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeItemRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeRegistBeanInterface;
import jp.mosp.time.bean.impl.WorkTypeItemReferenceBean;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.WorkTypeCardVo;

/**
 * 勤務形態情報の個別詳細情報の確認、編集を行う。<br>
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
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li></ul>
 */
public class WorkTypeCardAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String		CMD_SHOW		= "TM5220";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 勤務形態一覧画面で選択したレコードの情報を取得し、詳細画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW	= "TM5221";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力欄に入力されている情報を元に勤務形態情報テーブルに登録する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、または勤務形態コードが登録済のレコードのものと同一であった場合、
	 * エラーメッセージを表示する。<br>
	 */
	public static final String		CMD_REGIST		= "TM5225";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているレコード情報の削除を行う。<br>
	 */
	public static final String		CMD_DELETE		= "TM5227";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE	= "TM5271";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String		CMD_ADD_MODE	= "TM5273";
	
	/**
	 * コードキー(勤務形態設定追加処理)。<br>
	 */
	protected static final String	CODE_KEY_ADDONS	= "WorkTypeSettingAddons";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public WorkTypeCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new WorkTypeCardVo();
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
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 勤務形態詳細画面VOにリクエストパラメータを設定
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
			prepareVo(true, false);
			editMode();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替コマンド
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替コマンド
			prepareVo();
			addMode();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void show() throws MospException {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 新規追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
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
		// 登録クラス取得
		WorkTypeRegistBeanInterface regist = time().workTypeRegist();
		WorkTypeItemRegistBeanInterface itemRegist = time().workTypeItemRegist();
		// DTOの準備
		WorkTypeDtoInterface dto = regist.getInitDto();
		// 勤務形態項目リスト
		List<WorkTypeItemDtoInterface> list = new ArrayList<WorkTypeItemDtoInterface>();
		// DTOに値を設定
		setDtoFields(dto);
		// 新規追加処理
		regist.insert(dto);
		// 勤務形態項目毎に処理
		for (String item : itemRegist.getCodesWorkTypeItem()) {
			// DTOの準備
			WorkTypeItemDtoInterface itemDto = itemRegist.getInitDto();
			// DTOに値を設定
			setDtoFieldsItem(itemDto, item);
			// リストに追加
			list.add(itemDto);
		}
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 勤務形態登録時追加処理
			list.addAll(addonBean.setDtoFieldsAddonItems());
		}
		// 新規追加処理
		itemRegist.insert(list);
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getWorkTypeCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		WorkTypeRegistBeanInterface regist = time().workTypeRegist();
		WorkTypeItemRegistBeanInterface itemRegist = time().workTypeItemRegist();
		// DTOの準備
		WorkTypeDtoInterface dto = regist.getInitDto();
		// 勤務形態項目リスト
		List<WorkTypeItemDtoInterface> list = new ArrayList<WorkTypeItemDtoInterface>();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
		// 勤務形態項目毎に処理
		for (String item : itemRegist.getCodesWorkTypeItem()) {
			// DTOの準備
			WorkTypeItemDtoInterface itemDto = itemRegist.getInitDto();
			// DTOに値を設定
			setDtoFieldsItem(itemDto, item);
			// リストに追加
			list.add(itemDto);
			
		}
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 勤務形態登録時追加処理
			list.addAll(addonBean.setDtoFieldsAddonItems());
		}
		// 履歴追加処理
		itemRegist.add(list);
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
		setEditUpdateMode(dto.getWorkTypeCode(), dto.getActivateDate());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録クラス取得
		WorkTypeRegistBeanInterface regist = time().workTypeRegist();
		WorkTypeItemRegistBeanInterface itemRegist = time().workTypeItemRegist();
		// DTOの準備
		WorkTypeDtoInterface dto = regist.getInitDto();
		// 勤務形態項目リスト
		List<WorkTypeItemDtoInterface> list = new ArrayList<WorkTypeItemDtoInterface>();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.update(dto);
		// 勤務形態項目毎に処理
		for (String item : itemRegist.getCodesWorkTypeItem()) {
			// DTOの準備
			WorkTypeItemDtoInterface itemDto = itemRegist.getInitDto();
			// DTOに値を設定
			setDtoFieldsItem(itemDto, item);
			// リストに追加
			list.add(itemDto);
			
		}
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 勤務形態登録時追加処理
			list.addAll(addonBean.setDtoFieldsAddonItems());
		}
		// 更新処理
		itemRegist.update(list);
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
		setEditUpdateMode(dto.getWorkTypeCode(), dto.getActivateDate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// 登録クラス取得
		WorkTypeRegistBeanInterface regist = time().workTypeRegist();
		// DTOの準備
		WorkTypeDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		Date date = MonthUtility.getYearMonthDate(getInt(vo.getTxtEditActivateYear()),
				getInt(vo.getTxtEditActivateMonth()));
		// 削除処理
		regist.delete(dto);
		time().workTypeItemRegist().delete(vo.getTxtWorkTypeCode(), date);
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 勤務形態削除時追加処理
			addonBean.delete();
		}
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
	 * 新規登録モードで画面を表示する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insertMode() throws MospException {
		// 編集モード設定
		setEditInsertMode();
		// 初期値設定
		setDefaultValues();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addMode() throws MospException {
		// 履歴追加モード設定
		setEditAddMode();
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 履歴追加モードで画面を表示する際の追加処理
			addonBean.setEditAddMode();
		}
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
	 * 勤務形態コードと有効日で編集対象情報を取得する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String workTypeCode, Date activateDate) throws MospException {
		// 参照クラス取得
		WorkTypeReferenceBeanInterface reference = timeReference().workType();
		WorkTypeItemRegistBeanInterface itemRegist = time().workTypeItemRegist();
		// 履歴編集対象取得
		WorkTypeDtoInterface dto = reference.findForKey(workTypeCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 勤務形態項目コード毎に勤務形態項目を取得し設定
		for (String workTypeItemCode : itemRegist.getCodesWorkTypeItem()) {
			// 勤務形態項目取得
			WorkTypeItemDtoInterface itemDto = timeReference().workTypeItem().findForKey(workTypeCode, activateDate,
					workTypeItemCode);
			// 勤務形態項目設定
			if (itemDto != null) {
				setVoFieldsItem(itemDto, workTypeItemCode);
			}
		}
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 勤務形態詳細画面VOに値を設定
			addonBean.setVoFields(dto);
		}
		// 編集モード設定
		setEditUpdateMode(reference.getWorkTypeHistory(workTypeCode));
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public void setDefaultValues() throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// 編集項目の表示
		vo.setTxtWorkTypeCode("");
		vo.setTxtWorkTypeName("");
		vo.setTxtWorkTypeAbbr("");
		// 登録情報欄の初期値設定
		vo.setTxtWorkStartHour("");
		vo.setTxtWorkStartMinute("");
		vo.setTxtWorkEndHour("");
		vo.setTxtWorkEndMinute("");
		vo.setTxtWorkTimeHour("0");
		vo.setTxtWorkTimeMinute("0");
		vo.setTxtRestTimeHour("0");
		vo.setTxtRestTimeMinute("0");
		vo.setTxtRestStart1Hour("");
		vo.setTxtRestStart1Minute("");
		vo.setTxtRestEnd1Hour("");
		vo.setTxtRestEnd1Minute("");
		vo.setTxtRestStart2Hour("");
		vo.setTxtRestStart2Minute("");
		vo.setTxtRestEnd2Hour("");
		vo.setTxtRestEnd2Minute("");
		vo.setTxtRestStart3Hour("");
		vo.setTxtRestStart3Minute("");
		vo.setTxtRestEnd3Hour("");
		vo.setTxtRestEnd3Minute("");
		vo.setTxtRestStart4Hour("");
		vo.setTxtRestStart4Minute("");
		vo.setTxtRestEnd4Hour("");
		vo.setTxtRestEnd4Minute("");
		vo.setTxtFrontStartHour("");
		vo.setTxtFrontStartMinute("");
		vo.setTxtFrontEndHour("");
		vo.setTxtFrontEndMinute("");
		vo.setTxtBackStartHour("");
		vo.setTxtBackStartMinute("");
		vo.setTxtBackEndHour("");
		vo.setTxtBackEndMinute("");
		vo.setTxtOverBeforeHour("");
		vo.setTxtOverBeforeMinute("");
		vo.setTxtOverPerHour("");
		vo.setTxtOverPerMinute("");
		vo.setTxtOverRestHour("");
		vo.setTxtOverRestMinute("");
		vo.setTxtHalfRestHour("");
		vo.setTxtHalfRestMinute("");
		vo.setTxtHalfRestStartHour("");
		vo.setTxtHalfRestStartMinute("");
		vo.setTxtHalfRestEndHour("");
		vo.setTxtHalfRestEndMinute("");
		vo.setCkbDirectStart(MospConst.CHECKBOX_OFF);
		vo.setCkbDirectEnd(MospConst.CHECKBOX_OFF);
		vo.setPltMidnightRestExclusion(String.valueOf(MospConst.INACTIVATE_FLAG_ON));
		vo.setTxtShort1StartHour("");
		vo.setTxtShort1StartMinute("");
		vo.setTxtShort1EndHour("");
		vo.setTxtShort1EndMinute("");
		vo.setPltShort1Type("");
		vo.setTxtShort2StartHour("");
		vo.setTxtShort2StartMinute("");
		vo.setTxtShort2EndHour("");
		vo.setTxtShort2EndMinute("");
		vo.setPltShort2Type("");
		WorkTypeItemRegistBeanInterface itemRegist = time().workTypeItemRegist();
		vo.setTmmWorkTypeItemId(new long[itemRegist.getCodesWorkTypeItem().length]);
		vo.setPltAutoBeforeOverWork(String.valueOf(MospConst.INACTIVATE_FLAG_ON));
		// 勤務形態設定追加JSPリストを設定
		vo.setAddonJsps(getAddonJsps());
		// 勤務形態追加パラメータ群(キー：パラメータ名)を初期化
		vo.setAddonParams(new HashMap<String, String>());
		// 勤務形態追加パラメータ配列群(キー：パラメータ名)を初期化
		vo.setAddonArrays(new HashMap<String, String[]>());
		// 勤務形態追加プルダウン配列群(キー：パラメータ名)を初期化
		vo.setAddonAryPlts(new HashMap<String, String[][]>());
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 勤務形態詳細画面VOに初期値を設定
			addonBean.initVoFields();
		}
		
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFields(WorkTypeDtoInterface dto) throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmmWorkTypeId(vo.getRecordId());
		dto.setActivateDate(MonthUtility.getYearMonthDate(getInt(vo.getTxtEditActivateYear()),
				getInt(vo.getTxtEditActivateMonth())));
		dto.setWorkTypeCode(vo.getTxtWorkTypeCode());
		dto.setWorkTypeName(vo.getTxtWorkTypeName());
		dto.setWorkTypeAbbr(vo.getTxtWorkTypeAbbr());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(WorkTypeDtoInterface dto) {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmmWorkTypeId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtWorkTypeCode(dto.getWorkTypeCode());
		vo.setTxtWorkTypeName(dto.getWorkTypeName());
		vo.setTxtWorkTypeAbbr(dto.getWorkTypeAbbr());
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param itemType 時間種別
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFieldsItem(WorkTypeItemDtoInterface dto, String itemType) throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// 勤務形態項目登録クラス取得
		WorkTypeItemRegistBeanInterface item = time().workTypeItemRegist();
		WorkTypeItemReferenceBeanInterface itemRefer = timeReference().workTypeItem();
		// VOの値をDTOに設定
		dto.setActivateDate(MonthUtility.getYearMonthDate(getInt(vo.getTxtEditActivateYear()),
				getInt(vo.getTxtEditActivateMonth())));
		dto.setWorkTypeCode(vo.getTxtWorkTypeCode());
		dto.setWorkTypeItemValue(DateUtility.getDefaultTime());
		dto.setPreliminary("");
		if (itemType.equals(TimeConst.CODE_WORKSTART)) {
			// 始業時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(0));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORKSTART);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtWorkStartHour(), vo.getTxtWorkStartMinute()));
		} else if (itemType.equals(TimeConst.CODE_WORKEND)) {
			// 終業時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(1));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORKEND);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtWorkEndHour(), vo.getTxtWorkEndMinute()));
		} else if (itemType.equals(TimeConst.CODE_WORKTIME)) {
			// 勤務時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(2));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORKTIME);
			dto.setWorkTypeItemValue(getTimestamp(
					itemRefer.getWorkTime(item.getDefaultTime(vo.getTxtWorkStartHour(), vo.getTxtWorkStartMinute()),
							item.getDefaultTime(vo.getTxtWorkEndHour(), vo.getTxtWorkEndMinute()), getRestTime())));
		} else if (itemType.equals(TimeConst.CODE_RESTTIME)) {
			// 休憩時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(3));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTTIME);
			dto.setWorkTypeItemValue(getTimestamp(getRestTime()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART1)) {
			// 休憩開始時刻1
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(4));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTSTART1);
			if (!vo.getTxtRestStart1Hour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtRestStart1Hour(), vo.getTxtRestStart1Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTEND1)) {
			// 休憩終了時刻1
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(5));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTEND1);
			if (!vo.getTxtRestEnd1Hour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtRestEnd1Hour(), vo.getTxtRestEnd1Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTSTART2)) {
			// 休憩開始時刻2
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(6));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTSTART2);
			if (!vo.getTxtRestStart2Hour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtRestStart2Hour(), vo.getTxtRestStart2Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTEND2)) {
			// 休憩終了時刻2
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(7));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTEND2);
			if (!vo.getTxtRestEnd2Hour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtRestEnd2Hour(), vo.getTxtRestEnd2Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTSTART3)) {
			// 休憩開始時刻3
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(8));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTSTART3);
			if (!vo.getTxtRestStart3Hour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtRestStart3Hour(), vo.getTxtRestStart3Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTEND3)) {
			// 休憩終了時刻3
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(9));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTEND3);
			if (!vo.getTxtRestEnd3Hour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtRestEnd3Hour(), vo.getTxtRestEnd3Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTSTART4)) {
			// 休憩開始時刻4
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(10));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTSTART4);
			if (!vo.getTxtRestStart4Hour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtRestStart4Hour(), vo.getTxtRestStart4Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_RESTEND4)) {
			// 休憩終了時刻4
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(11));
			dto.setWorkTypeItemCode(TimeConst.CODE_RESTEND4);
			if (!vo.getTxtRestEnd4Hour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtRestEnd4Hour(), vo.getTxtRestEnd4Minute()));
			}
		} else if (itemType.equals(TimeConst.CODE_FRONTSTART)) {
			// 午前休開始時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(12));
			dto.setWorkTypeItemCode(TimeConst.CODE_FRONTSTART);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtFrontStartHour(), vo.getTxtFrontStartMinute()));
		} else if (itemType.equals(TimeConst.CODE_FRONTEND)) {
			// 午前休終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(13));
			dto.setWorkTypeItemCode(TimeConst.CODE_FRONTEND);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtFrontEndHour(), vo.getTxtFrontEndMinute()));
		} else if (itemType.equals(TimeConst.CODE_BACKSTART)) {
			// 午後休開始時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(14));
			dto.setWorkTypeItemCode(TimeConst.CODE_BACKSTART);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtBackStartHour(), vo.getTxtBackStartMinute()));
		} else if (itemType.equals(TimeConst.CODE_BACKEND)) {
			// 午後休終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(15));
			dto.setWorkTypeItemCode(TimeConst.CODE_BACKEND);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtBackEndHour(), vo.getTxtBackEndMinute()));
		} else if (itemType.equals(TimeConst.CODE_OVERBEFORE)) {
			// 残前休憩時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(16));
			dto.setWorkTypeItemCode(TimeConst.CODE_OVERBEFORE);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtOverBeforeHour(), vo.getTxtOverBeforeMinute()));
		} else if (itemType.equals(TimeConst.CODE_OVERPER)) {
			// 残業休憩時間(毎)
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(17));
			dto.setWorkTypeItemCode(TimeConst.CODE_OVERPER);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtOverPerHour(), vo.getTxtOverPerMinute()));
		} else if (itemType.equals(TimeConst.CODE_OVERREST)) {
			// 残業休憩時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(18));
			dto.setWorkTypeItemCode(TimeConst.CODE_OVERREST);
			dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtOverRestHour(), vo.getTxtOverRestMinute()));
		} else if (itemType.equals(TimeConst.CODE_HALFREST)) {
			// 半休休憩対象時間
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(19));
			dto.setWorkTypeItemCode(TimeConst.CODE_HALFREST);
			if (!vo.getTxtHalfRestHour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtHalfRestHour(), vo.getTxtHalfRestMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_HALFRESTSTART)) {
			// 半休休憩開始時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(20));
			dto.setWorkTypeItemCode(TimeConst.CODE_HALFRESTSTART);
			if (!vo.getTxtHalfRestStartHour().isEmpty()) {
				dto.setWorkTypeItemValue(
						item.getDefaultTime(vo.getTxtHalfRestStartHour(), vo.getTxtHalfRestStartMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_HALFRESTEND)) {
			// 半休休憩終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(21));
			dto.setWorkTypeItemCode(TimeConst.CODE_HALFRESTEND);
			if (!vo.getTxtHalfRestEndHour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtHalfRestEndHour(), vo.getTxtHalfRestEndMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START)) {
			// 直行
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(22));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START);
			dto.setPreliminary(vo.getCkbDirectStart());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END)) {
			// 直帰
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(23));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END);
			dto.setPreliminary(vo.getCkbDirectEnd());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST)) {
			// 割増休憩除外
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(24));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST);
			dto.setPreliminary(vo.getPltMidnightRestExclusion());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START)) {
			// 時短時間1開始時刻及び給与区分
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(25));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START);
			if (!vo.getTxtShort1StartHour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtShort1StartHour(), vo.getTxtShort1StartMinute()));
				dto.setPreliminary(vo.getPltShort1Type());
			}
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END)) {
			// 時短時間1終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(26));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
			if (!vo.getTxtShort1EndHour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtShort1EndHour(), vo.getTxtShort1EndMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START)) {
			// 時短時間2開始時刻及び給与区分
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(27));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START);
			if (!vo.getTxtShort2StartHour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtShort2StartHour(), vo.getTxtShort2StartMinute()));
				dto.setPreliminary(vo.getPltShort2Type());
			}
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END)) {
			// 時短時間2終了時刻
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(28));
			dto.setWorkTypeItemCode(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
			if (!vo.getTxtShort2EndHour().isEmpty()) {
				dto.setWorkTypeItemValue(item.getDefaultTime(vo.getTxtShort2EndHour(), vo.getTxtShort2EndMinute()));
			}
		} else if (itemType.equals(TimeConst.CODE_AUTO_BEFORE_OVERWORK)) {
			// 勤務前残業自動申請
			dto.setTmmWorkTypeItemId(vo.getTmmWorkTypeItemId(29));
			dto.setWorkTypeItemCode(TimeConst.CODE_AUTO_BEFORE_OVERWORK);
			if (!vo.getPltAutoBeforeOverWork().isEmpty()) {
				dto.setPreliminary(vo.getPltAutoBeforeOverWork());
			}
		}
		// 画面上にはないが必須項目なので設定する
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @param itemType 時間種別
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsItem(WorkTypeItemDtoInterface dto, String itemType) throws MospException {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		if (itemType.equals(TimeConst.CODE_WORKSTART)) {
			// 始業時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 0);
			vo.setTxtWorkStartHour(DateUtility.getStringHour(dto.getWorkTypeItemValue()));
			vo.setTxtWorkStartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_WORKEND)) {
			// 終業時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 1);
			vo.setTxtWorkEndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtWorkEndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_WORKTIME)) {
			// 勤務時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 2);
			vo.setTxtWorkTimeHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtWorkTimeMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTTIME)) {
			// 休憩時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 3);
			vo.setTxtRestTimeHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestTimeMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART1)) {
			// 休憩開始時刻1
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 4);
			vo.setTxtRestStart1Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestStart1Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTEND1)) {
			// 休憩終了時刻1
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 5);
			vo.setTxtRestEnd1Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestEnd1Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART2)) {
			// 休憩開始時刻2
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 6);
			vo.setTxtRestStart2Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestStart2Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTEND2)) {
			// 休憩終了時刻2
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 7);
			vo.setTxtRestEnd2Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestEnd2Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART3)) {
			// 休憩開始時刻3
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 8);
			vo.setTxtRestStart3Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestStart3Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTEND3)) {
			// 休憩終了時刻3
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 9);
			vo.setTxtRestEnd3Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestEnd3Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTSTART4)) {
			// 休憩開始時刻4
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 10);
			vo.setTxtRestStart4Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestStart4Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_RESTEND4)) {
			// 休憩終了時刻4
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 11);
			vo.setTxtRestEnd4Hour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtRestEnd4Minute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_FRONTSTART)) {
			// 午前休開始時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 12);
			vo.setTxtFrontStartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtFrontStartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_FRONTEND)) {
			// 午前休終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 13);
			vo.setTxtFrontEndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtFrontEndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_BACKSTART)) {
			// 午後休開始時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 14);
			vo.setTxtBackStartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtBackStartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_BACKEND)) {
			// 午後休終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 15);
			vo.setTxtBackEndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtBackEndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_OVERBEFORE)) {
			// 残前休憩時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 16);
			vo.setTxtOverBeforeHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtOverBeforeMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_OVERPER)) {
			// 残業休憩時間(毎)
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 17);
			vo.setTxtOverPerHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtOverPerMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_OVERREST)) {
			// 残業休憩時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 18);
			vo.setTxtOverRestHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtOverRestMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_HALFREST)) {
			// 半休休憩対象時間
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 19);
			vo.setTxtHalfRestHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtHalfRestMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_HALFRESTSTART)) {
			// 半休休憩開始時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 20);
			vo.setTxtHalfRestStartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtHalfRestStartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_HALFRESTEND)) {
			// 半休休憩終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 21);
			vo.setTxtHalfRestEndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtHalfRestEndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START)) {
			// 直行
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 22);
			vo.setCkbDirectStart(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END)) {
			// 直帰
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 23);
			vo.setCkbDirectEnd(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST)) {
			// 割増休憩除外
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 24);
			vo.setPltMidnightRestExclusion(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START)) {
			// 時短時間1開始時刻及び給与区分
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 25);
			vo.setTxtShort1StartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtShort1StartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
			vo.setPltShort1Type(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END)) {
			// 時短時間1終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 26);
			vo.setTxtShort1EndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtShort1EndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START)) {
			// 時短時間2開始時刻及び給与区分
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 27);
			vo.setTxtShort2StartHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtShort2StartMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
			vo.setPltShort2Type(dto.getPreliminary());
		} else if (itemType.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END)) {
			// 時短時間2終了時刻
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 28);
			vo.setTxtShort2EndHour(getWorkTypeItemHour(dto.getWorkTypeItemValue()));
			vo.setTxtShort2EndMinute(DateUtility.getStringMinute(dto.getWorkTypeItemValue()));
		} else if (itemType.equals(TimeConst.CODE_AUTO_BEFORE_OVERWORK)) {
			// 勤務前残業自動申請
			vo.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId(), 29);
			vo.setPltAutoBeforeOverWork(dto.getPreliminary());
		}
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * 休憩時間取得。<br>
	 * @return 休憩時間(分)
	 */
	protected int getRestTime() {
		// VO取得
		WorkTypeCardVo vo = (WorkTypeCardVo)mospParams.getVo();
		WorkTypeItemReferenceBeanInterface time = new WorkTypeItemReferenceBean();
		
		// 休憩1時間(分)を取得
		int rest1 = time.getDifferenceTime(vo.getTxtRestStart1Hour(), vo.getTxtRestStart1Minute(),
				vo.getTxtRestEnd1Hour(), vo.getTxtRestEnd1Minute());
		// 休憩2時間(分)を取得
		int rest2 = time.getDifferenceTime(vo.getTxtRestStart2Hour(), vo.getTxtRestStart2Minute(),
				vo.getTxtRestEnd2Hour(), vo.getTxtRestEnd2Minute());
		// 休憩3時間(分)を取得
		int rest3 = time.getDifferenceTime(vo.getTxtRestStart3Hour(), vo.getTxtRestStart3Minute(),
				vo.getTxtRestEnd3Hour(), vo.getTxtRestEnd3Minute());
		// 休憩4時間(分)を取得
		int rest4 = time.getDifferenceTime(vo.getTxtRestStart4Hour(), vo.getTxtRestStart4Minute(),
				vo.getTxtRestEnd4Hour(), vo.getTxtRestEnd4Minute());
		return time.getRestTime(rest1, rest2, rest3, rest4);
	}
	
	private Date getTimestamp(int minute) throws MospException {
		return DateUtility.addMinute(DateUtility.getDefaultTime(), minute);
	}
	
	private String getWorkTypeItemHour(Date time) throws MospException {
		return DateUtility.getStringHour(time, DateUtility.getDefaultTime());
	}
	
	/**
	 * 勤務形態設定追加JSPリストを取得する。<br>
	 * @return 勤務形態設定追加JSPリスト
	 */
	protected List<String> getAddonJsps() {
		// 勤務形態設定追加JSPリストを準備
		List<String> addonJsps = new ArrayList<String>();
		// 勤務形態設定追加処理配列毎に処理
		for (String[] addon : getCodeArray(CODE_KEY_ADDONS, false)) {
			// 勤務形態設定追加JSPを取得
			String addonJsp = addon[1];
			// 勤務形態設定追加JSPが設定されていない場合
			if (MospUtility.isEmpty(addonJsp)) {
				// 次の勤務形態設定追加JSPへ
				continue;
			}
			// 勤務形態設定追加JSPリストに値を追加
			addonJsps.add(addonJsp);
		}
		// 勤務形態設定追加JSPリストを取得
		return addonJsps;
	}
	
	/**
	 * 勤務形態登録時追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void registAddon() throws MospException {
		// 勤務形態追加処理毎に処理
		for (WorkTypeBeanInterface addonBean : getAddonBeans()) {
			// 勤務形態登録時追加処理
			addonBean.regist();
		}
	}
	
	/**
	 * 勤務形態登録時追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void registItemAddon() throws MospException {
	}
	
	/**
	 * 勤務形態追加処理リストを取得する。<br>
	 * @return 勤務形態追加処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<WorkTypeBeanInterface> getAddonBeans() throws MospException {
		// 勤務形態追加処理リストを準備
		List<WorkTypeBeanInterface> addonBeans = new ArrayList<WorkTypeBeanInterface>();
		// 勤務形態追加処理配列毎に処理
		for (String[] addon : getCodeArray(CODE_KEY_ADDONS, false)) {
			// 勤務形態追加処理を取得
			String addonBean = addon[0];
			// 勤務形態追加処理が設定されていない場合
			if (MospUtility.isEmpty(addonBean)) {
				// 次の勤務形態追加処理へ
				continue;
			}
			// 勤務形態追加処理を取得
			WorkTypeBeanInterface bean = (WorkTypeBeanInterface)platform().createBean(addonBean);
			// 勤務形態追加処理リストに値を追加
			addonBeans.add(bean);
		}
		// 勤務形態追加処理リストを取得
		return addonBeans;
	}
}
