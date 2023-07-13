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
package jp.mosp.time.settings.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRegistBeanInterface;
import jp.mosp.time.bean.HolidaySearchBeanInterface;
import jp.mosp.time.comparator.settings.HolidayMasterHolidayCodeComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.HolidayMasterVo;

/**
 * 特別休暇、その他休暇としてシステム上で利用する休暇項目の管理を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li></ul>
 */
public class HolidayMasterAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM4100";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に締日情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM4102";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たな休暇種別の登録や既存の休暇種別の編集を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM4103";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 編集テーブルに入力されている内容を休暇種別マスタテーブルに登録する。<br>
	 */
	public static final String	CMD_REGIST				= "TM4105";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 一覧表示欄の選択チェックボックスで選択されているレコードを対象に論理削除を行うよう繰り返し処理を行う。<br>
	 */
	public static final String	CMD_DELETE				= "TM4107";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM4108";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM4109";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 編集テーブルの各入力欄に表示されているレコード内容をクリアにする。登録ボタンクリック時のコマンドを登録コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "TM4191";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を編集テーブルの各入力欄にそれぞれ表示させる。有効日の入力欄を読取専用にする。<br>
	 * 適用ボタンクリック時のコマンドを更新コマンドに切り替える。編集テーブルヘッダに新規登録モード、履歴追加モードの切替リンクを表示させる。<br>
	 */
	public static final String	CMD_EDIT_MODE			= "TM4192";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE			= "TM4193";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、チェックの入っているレコードに一括更新テーブル内入力欄の内容を<br>
	 * 反映させるよう繰り返し処理を行う。有効日入力欄に日付が入力されていない場合やチェックが1件も入っていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE		= "TM4195";
	
	/**
	 * 給与区分(無給)。<br>
	 */
	public static final int		TYPE_SALARY_PAY_NONE	= 1;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public HolidayMasterAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HolidayMasterVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替コマンド
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 履歴編集モード切替コマンド
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替コマンド
			prepareVo();
			addMode();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 */
	protected void show() {
		// 勤怠設定共通VO初期値設定
		initTimeSettingVoFields();
		// 新規登録モード設定
		insertMode();
		// VO取得
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(HolidayMasterHolidayCodeComparator.class.getName());
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// 検索クラス取得
		HolidaySearchBeanInterface search = timeReference().holidaySearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setHolidayType(vo.getPltSearchHolidayType());
		search.setHolidayCode(vo.getTxtSearchHolidayCode());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<HolidayDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(HolidayMasterHolidayCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
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
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// VO取得
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// 登録件数を確認
		// 検索クラス取得
		HolidaySearchBeanInterface search = timeReference().holidaySearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setHolidayType(vo.getPltEditHolidayType());
		search.setHolidayCode("");
		search.setInactivateFlag("");
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<HolidayDtoInterface> list = search.getSearchList();
		if (vo.getPltEditHolidayType().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_SPECIAL))) {
			if (list.size() >= TimeConst.PRM_HOLIDAYTYPE_SPECIAL_MAX) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_TYPE_MAX_OVER,
						mospParams.getName("Specially") + mospParams.getName("Holiday"),
						String.valueOf(TimeConst.PRM_HOLIDAYTYPE_SPECIAL_MAX));
				return;
			}
		} else if (vo.getPltEditHolidayType().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_OTHER))) {
			if (list.size() >= TimeConst.PRM_HOLIDAYTYPE_OTHER_MAX) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_TYPE_MAX_OVER,
						mospParams.getName("Others") + mospParams.getName("Holiday"),
						String.valueOf(TimeConst.PRM_HOLIDAYTYPE_OTHER_MAX));
				return;
			}
		} else if (vo.getPltEditHolidayType().equals(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_ABSENCE))) {
			if (list.size() >= TimeConst.PRM_HOLIDAYTYPE_ABSENCE_MAX) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_TYPE_MAX_OVER, mospParams.getName("Absence"),
						String.valueOf(TimeConst.PRM_HOLIDAYTYPE_ABSENCE_MAX));
				return;
			}
		}
		// 登録クラス取得
		HolidayRegistBeanInterface regist = time().holidayRegist();
		// DTOの準備
		HolidayDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.insert(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getHolidayCode(), dto.getActivateDate(), dto.getHolidayType());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		HolidayRegistBeanInterface regist = time().holidayRegist();
		// DTOの準備
		HolidayDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.add(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージを設定
		PfMessageUtility.addMessageAddHistorySucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getHolidayCode(), dto.getActivateDate(), dto.getHolidayType());
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
		HolidayRegistBeanInterface regist = time().holidayRegist();
		// DTOの準備
		HolidayDtoInterface dto = regist.getInitDto();
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
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getHolidayCode(), dto.getActivateDate(), dto.getHolidayType());
		// 検索有効日設定(登録有効日を検索条件に設定)
		setSearchActivateDate(getEditActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// 一括更新処理
		time().holidayRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
				getInt(vo.getPltUpdateInactivate()));
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 検索有効日設定(一括更新有効日を検索条件に設定)
		setSearchActivateDate(getUpdateActivateDate());
		search();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// 削除処理
		// 削除対象ID配列取得
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 削除処理
		time().holidayRegist().delete(idArray);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteHistory(mospParams, idArray.length);
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 検索
		search();
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
	 */
	protected void page() {
		setVoList(pageList());
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 */
	protected void insertMode() {
		// 編集モード設定
		setEditInsertMode();
		// 初期値設定
		setDefaultValues();
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addMode() throws MospException {
		// 履歴追加モード設定
		setEditAddMode();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード、有効日、汎用区分で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// 休暇区分取得
		String holidayType = getTransferredType();
		// 休暇区分確認
		if (holidayType == null || holidayType.isEmpty()) {
			// VO取得
			HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
			// 休暇区分取得(履歴移動の際は休暇区分が送られないため)
			holidayType = vo.getPltEditHolidayType();
		}
		// 遷移汎用コード、有効日、汎用区分から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode(), getDate(getTransferredActivateDate()), getInt(holidayType));
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 休暇コード、有効日、休暇区分で編集対象情報を取得する。<br>
	 * 新規登録、履歴追加、履歴編集後にも、実行される。<br>
	 * @param holidayCode	休暇コード
	 * @param activateDate	有効日
	 * @param holidayType  休暇区分
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String holidayCode, Date activateDate, int holidayType) throws MospException {
		// 休暇種別参照クラス取得
		HolidayReferenceBeanInterface reference = timeReference().holiday();
		// 履歴編集対象取得
		HolidayDtoInterface dto = reference.findForKey(holidayCode, activateDate, holidayType);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 編集モード設定(編集対象の履歴を取得)
		setEditUpdateMode(reference.getHolidayHistory(holidayCode, holidayType));
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// 入力項目設定
		vo.setTxtEditHolidayName("");
		vo.setTxtEditHolidayAbbr("");
		vo.setTxtEditHolidayGiving("");
		vo.setTxtEditHolidayLimitMonth("");
		vo.setTxtEditHolidayLimitDay("");
		vo.setTxtEditHolidayCode("");
		vo.setPltEditHalfHolidayRequest("");
		vo.setPltEditHourlyHoliday(String.valueOf(MospConst.INACTIVATE_FLAG_ON));
		vo.setPltEditHolidayType("");
		vo.setPltEditContinue("");
		vo.setPltEditSalary("");
		vo.setPltEditReasonType("");
		vo.setPltEditPaidHolidayCalc("");
		// チェックボックス
		vo.setCkbNoLimit(MospConst.CHECKBOX_OFF);
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// データ配列初期化
		long[] aryCkbRecordId = new long[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblHolidayCode = new String[list.size()];
		String[] aryLblHolidayType = new String[list.size()];
		String[] aryLblHolidayTypeName = new String[list.size()];
		String[] aryLblHolidayName = new String[list.size()];
		String[] aryLblHolidayAbbr = new String[list.size()];
		String[] aryLblHolidayGiving = new String[list.size()];
		String[] aryLblHolidayLimit = new String[list.size()];
		String[] aryLblHolidayContinue = new String[list.size()];
		String[] aryLblTimelyHoliday = new String[list.size()];
		String[] aryLblHolidaySalary = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			HolidayDtoInterface dto = (HolidayDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = dto.getTmmHolidayId();
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblHolidayCode[i] = dto.getHolidayCode();
			aryLblHolidayType[i] = String.valueOf(dto.getHolidayType());
			aryLblHolidayTypeName[i] = mospParams.getProperties()
				.getCodeItemName(TimeConst.CODE_KEY_HOLIDAY_TYPE_MASTER, aryLblHolidayType[i]);
			aryLblHolidayName[i] = dto.getHolidayName();
			aryLblHolidayAbbr[i] = dto.getHolidayAbbr();
			if (dto.getNoLimit() == getInt(MospConst.CHECKBOX_ON)) {
				aryLblHolidayGiving[i] = mospParams.getName("NoLimit");
			} else {
				aryLblHolidayGiving[i] = dto.getHolidayGiving() + mospParams.getName("Day");
			}
			if (dto.getHolidayLimitDay() == 0) {
				if (dto.getHolidayLimitMonth() == 0) {
					// 取得期限の月日両方0の場合は"0日"と表示する
					aryLblHolidayLimit[i] = dto.getHolidayLimitDay() + mospParams.getName("Day");
				} else {
					aryLblHolidayLimit[i] = dto.getHolidayLimitMonth() + mospParams.getName("Months");
				}
			} else {
				if (dto.getHolidayLimitMonth() == 0) {
					aryLblHolidayLimit[i] = dto.getHolidayLimitDay() + mospParams.getName("Day");
				} else {
					aryLblHolidayLimit[i] = dto.getHolidayLimitMonth() + mospParams.getName("Months")
							+ dto.getHolidayLimitDay() + mospParams.getName("Day");
				}
			}
			aryLblHolidayContinue[i] = mospParams.getProperties().getCodeItemName(TimeConst.CODE_KEY_CONTINUE,
					String.valueOf(dto.getContinuousAcquisition()));
			aryLblTimelyHoliday[i] = mospParams.getProperties().getCodeItemName(PlatformConst.CODE_KEY_INACTIVATE_FLAG,
					String.valueOf(dto.getTimelyHolidayFlag()));
			aryLblHolidaySalary[i] = mospParams.getProperties().getCodeItemName(TimeConst.CODE_KEY_SALARY_PAY_TYPE,
					String.valueOf(dto.getSalary()));
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryCkbRecordId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblHolidayCode(aryLblHolidayCode);
		vo.setAryLblHolidayType(aryLblHolidayType);
		vo.setAryLblHolidayTypeName(aryLblHolidayTypeName);
		vo.setAryLblHolidayName(aryLblHolidayName);
		vo.setAryLblHolidayAbbr(aryLblHolidayAbbr);
		vo.setAryLblHolidayGiving(aryLblHolidayGiving);
		vo.setAryLblHolidayLimit(aryLblHolidayLimit);
		vo.setAryLblHolidayContinue(aryLblHolidayContinue);
		vo.setAryLblTimelyHoliday(aryLblTimelyHoliday);
		vo.setAryLblHolidaySalary(aryLblHolidaySalary);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(HolidayDtoInterface dto) {
		// VO取得
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// DTOの値をVOに設定
		dto.setTmmHolidayId(vo.getRecordId());
		dto.setActivateDate(getEditActivateDate());
		dto.setHolidayType(getInt(vo.getPltEditHolidayType()));
		dto.setHolidayName(vo.getTxtEditHolidayName());
		dto.setHolidayAbbr(vo.getTxtEditHolidayAbbr());
		dto.setHolidayGiving(getDouble(vo.getTxtEditHolidayGiving()));
		dto.setNoLimit(MospUtility.getInt(vo.getCkbNoLimit()));
		dto.setHolidayLimitMonth(getInt(vo.getTxtEditHolidayLimitMonth()));
		dto.setHolidayLimitDay(getInt(vo.getTxtEditHolidayLimitDay()));
		dto.setHolidayCode(vo.getTxtEditHolidayCode());
		dto.setHalfHolidayRequest(getInt(vo.getPltEditHalfHolidayRequest()));
		dto.setTimelyHolidayFlag(getInt(vo.getPltEditHourlyHoliday()));
		dto.setContinuousAcquisition(MospUtility.getInt(vo.getPltEditContinue()));
		dto.setPaidHolidayCalc(getInt(vo.getPltEditPaidHolidayCalc()));
		dto.setReasonType(getInt(vo.getPltEditReasonType()));
		dto.setSalary(MospUtility.getInt(vo.getPltEditSalary()));
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		// 休暇区分が欠勤の場合
		if (dto.getHolidayType() == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 無制限にチェックを付加
			dto.setNoLimit(getInt(MospConst.CHECKBOX_ON));
			// 給与区分設定(無給)
			dto.setSalary(TYPE_SALARY_PAY_NONE);
		} else {
		}
		// 時間単位区分が有効であるか無制限にチェックが入っていた場合
		if (dto.getTimelyHolidayFlag() == MospConst.INACTIVATE_FLAG_OFF
				|| dto.getNoLimit() == getInt(MospConst.CHECKBOX_ON)) {
			// 連続取得区分を不要に設定
			dto.setContinuousAcquisition(TimeConst.TYPE_CONTINUOUS_UNNECESSARY);
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(HolidayDtoInterface dto) {
		// VO取得
		HolidayMasterVo vo = (HolidayMasterVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmmHolidayId());
		vo.setTxtEditActivateYear(DateUtility.getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(DateUtility.getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(DateUtility.getStringDay(dto.getActivateDate()));
		vo.setTxtEditHolidayName(dto.getHolidayName());
		vo.setTxtEditHolidayAbbr(dto.getHolidayAbbr());
		vo.setTxtEditHolidayGiving(String.valueOf(dto.getHolidayGiving()));
		vo.setCkbNoLimit(String.valueOf(dto.getNoLimit()));
		vo.setTxtEditHolidayLimitMonth(String.valueOf(dto.getHolidayLimitMonth()));
		vo.setTxtEditHolidayLimitDay(String.valueOf(dto.getHolidayLimitDay()));
		vo.setTxtEditHolidayCode(dto.getHolidayCode());
		vo.setPltEditHalfHolidayRequest(String.valueOf(dto.getHalfHolidayRequest()));
		vo.setPltEditHourlyHoliday(String.valueOf(dto.getTimelyHolidayFlag()));
		vo.setPltEditHolidayType(String.valueOf(dto.getHolidayType()));
		vo.setPltEditContinue(String.valueOf(dto.getContinuousAcquisition()));
		vo.setPltEditPaidHolidayCalc(String.valueOf(dto.getPaidHolidayCalc()));
		vo.setPltEditSalary(String.valueOf(dto.getSalary()));
		vo.setPltEditReasonType(String.valueOf(dto.getReasonType()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
}
