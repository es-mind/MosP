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

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBeanHandlerInterface;
import jp.mosp.time.bean.PaidHolidayEntranceDateRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayProportionallyReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayProportionallyRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayProportionallyDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDtoInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.PaidHolidayCardVo;

/**
 * 有給休暇設定情報の個別詳細情報の確認、編集を行う。<br>
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
 * </li><li>
 * {@link #CMD_REPLICATION_MODE}
 * </li></ul>
 */
public class PaidHolidayCardAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで画面の初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM5320";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 勤務形態一覧画面で選択したレコードの情報を取得し、詳細画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "TM5321";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力欄に入力されている情報を元に勤務形態情報テーブルに登録する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、または勤務形態コードが
	 * 登録済みのレコードのものと同一であった場合、エラーメッセージを表示する。<br>
	 */
	public static final String	CMD_REGIST				= "TM5325";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示している有給休暇情報の論理削除を行う。<br>
	 */
	public static final String	CMD_DELETE				= "TM5327";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "TM5371";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE			= "TM5373";
	
	/**
	 * 複製モード切替コマンド。<br>
	 * <br>
	 * 編集モードで編集不可だった有効日、コードが編集可能となり、登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 * モード切替前に現在編集中のレコードのコードを変更することで新たなレコードとして
	 * 登録できる旨を伝える確認ダイアログを表示して利用者が承認をしてからモードを切り替える。<br>
	 */
	public static final String	CMD_REPLICATION_MODE	= "TM5374";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public PaidHolidayCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PaidHolidayCardVo();
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
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替コマンド
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替コマンド
			prepareVo();
			addMode();
		} else if (mospParams.getCommand().equals(CMD_REPLICATION_MODE)) {
			// 複製モード切替コマンド
			prepareVo();
			replicationMode();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理発生
	 */
	protected void show() throws MospException {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO準備
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
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
		// VO準備
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// 登録時に有効フラグにデフォルト値（0）を設定
		vo.setPltEditInactivate("0");
		// 同コネクションを使用するため最初にtime()を定義する。
		TimeBeanHandlerInterface time = time();
		// 基本情報
		PaidHolidayRegistBeanInterface regist = time.paidHolidayRegist();
		PaidHolidayDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 新規追加処理
		regist.insert(dto);
		// 初年度付与
		PaidHolidayFirstYearRegistBeanInterface registFirst = time.paidHolidayFirstYearRegist();
		for (int i = 1; i <= 12; i++) {
			PaidHolidayFirstYearDtoInterface firstDto = registFirst.getInitDto();
			firstDto.setEntranceMonth(i);
			setDtoFields(firstDto);
			registFirst.insert(firstDto);
		}
		// DTOに値を設定、新規追加処理
		// ストック休暇設定
		StockHolidayRegistBeanInterface registStock = time.stockHolidayRegist();
		StockHolidayDtoInterface dtoStock = registStock.getInitDto();
		setDtoFields(dtoStock);
		registStock.insert(dtoStock);
		// DTOに値を設定、新規追加処理
		if (vo.getPltPaidHolidayType().equals(String.valueOf(TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY))) {
			// 自動付与【基準日】
			PaidHolidayPointDateRegistBeanInterface registPoint = time.paidHolidayPointDateRegist();
			String[] aryPointDateAmount = { vo.getTxtPointDateAmount1(), vo.getTxtPointDateAmount2(),
				vo.getTxtPointDateAmount3(), vo.getTxtPointDateAmount4(), vo.getTxtPointDateAmount5(),
				vo.getTxtPointDateAmount6(), vo.getTxtPointDateAmount7(), vo.getTxtPointDateAmount8(),
				vo.getTxtPointDateAmount9(), vo.getTxtPointDateAmount10(), vo.getTxtPointDateAmount11(),
				vo.getTxtPointDateAmount12() };
			for (int i = 1; i <= 12; i++) {
				if (!aryPointDateAmount[i - 1].isEmpty()) {
					PaidHolidayPointDateDtoInterface pointDto = registPoint.getInitDto();
					pointDto.setTimesPointDate(i);
					setDtoFields(pointDto);
					registPoint.insert(pointDto);
				}
			}
		} else if (vo.getPltPaidHolidayType().equals("1") || vo.getPltPaidHolidayType().equals("2")) {
			// 自動付与【入社日】
			registEntranceDto();
		} else if (isProportionally()) {
			// 比例
			insertProportionally();
		}
		// 新規追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 初期値設定
		setDefaultValues();
		// 履歴編集モード設定
		setEditUpdateMode(dto.getPaidHolidayCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// VO準備
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// 同コネクションを使用するため最初にtime()を定義する。
		TimeBeanHandlerInterface time = time();
		// 基本情報
		PaidHolidayRegistBeanInterface regist = time.paidHolidayRegist();
		PaidHolidayDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
		// 初年度付与
		PaidHolidayFirstYearRegistBeanInterface registFirst = time.paidHolidayFirstYearRegist();
		for (int i = 1; i <= 12; i++) {
			PaidHolidayFirstYearDtoInterface firstDto = registFirst.getInitDto();
			firstDto.setEntranceMonth(i);
			setDtoFields(firstDto);
			registFirst.add(firstDto);
		}
		// DTOに値を設定、履歴追加処理
		// ストック休暇設定
		StockHolidayRegistBeanInterface registStock = time.stockHolidayRegist();
		StockHolidayDtoInterface dtoStock = registStock.getInitDto();
		setDtoFields(dtoStock);
		registStock.add(dtoStock);
		// DTOに値を設定、履歴追加処理
		if (vo.getPltPaidHolidayType().equals(String.valueOf(TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY))) {
			// 自動付与【基準日】
			PaidHolidayPointDateRegistBeanInterface registPoint = time.paidHolidayPointDateRegist();
			String[] aryPointDateAmount = { vo.getTxtPointDateAmount1(), vo.getTxtPointDateAmount2(),
				vo.getTxtPointDateAmount3(), vo.getTxtPointDateAmount4(), vo.getTxtPointDateAmount5(),
				vo.getTxtPointDateAmount6(), vo.getTxtPointDateAmount7(), vo.getTxtPointDateAmount8(),
				vo.getTxtPointDateAmount9(), vo.getTxtPointDateAmount10(), vo.getTxtPointDateAmount11(),
				vo.getTxtPointDateAmount12() };
			for (int i = 1; i <= 12; i++) {
				if (!aryPointDateAmount[i - 1].isEmpty()) {
					PaidHolidayPointDateDtoInterface pointDto = registPoint.getInitDto();
					pointDto.setTimesPointDate(i);
					setDtoFields(pointDto);
					registPoint.add(pointDto);
				}
			}
		} else if (vo.getPltPaidHolidayType().equals("1") || vo.getPltPaidHolidayType().equals("2")) {
			// 自動付与【入社日】
			registEntranceDto();
		} else if (isProportionally()) {
			// 比例
			addProportionally();
		}
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
		// 初期値設定
		setDefaultValues();
		// 履歴編集モード設定
		setEditUpdateMode(dto.getPaidHolidayCode(), dto.getActivateDate());
	}
	
	/**
	 * 履歴更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO準備
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// 同コネクションを使用するため最初にtime()を定義する。
		TimeBeanHandlerInterface time = time();
		// 登録クラス取得(基準日)
		PaidHolidayPointDateRegistBeanInterface registPoint = time.paidHolidayPointDateRegist();
		// 登録クラス取得(比例)
		PaidHolidayProportionallyRegistBeanInterface paidHolidayProportionallyRegist = time
			.paidHolidayProportionallyRegist();
		// 基本情報
		PaidHolidayRegistBeanInterface regist = time.paidHolidayRegist();
		Date activateDate = getEditActivateDate();
		PaidHolidayDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.update(dto);
		// 基準日
		registPoint.delete(vo.getTxtPaidHolidayCode(), activateDate);
		// 比例
		paidHolidayProportionallyRegist.delete(vo.getTxtPaidHolidayCode(), activateDate);
		// 初年度付与
		PaidHolidayFirstYearRegistBeanInterface registFirst = time.paidHolidayFirstYearRegist();
		for (int i = 1; i <= 12; i++) {
			PaidHolidayFirstYearDtoInterface firstDto = registFirst.getInitDto();
			firstDto.setEntranceMonth(i);
			setDtoFields(firstDto);
			registFirst.update(firstDto);
		}
		// DTOに値を設定、履歴更新処理
		// ストック休暇設定
		StockHolidayRegistBeanInterface registStock = time.stockHolidayRegist();
		StockHolidayDtoInterface dtoStock = registStock.getInitDto();
		setDtoFields(dtoStock);
		registStock.update(dtoStock);
		// DTOに値を設定、履歴更新処理
		if (vo.getPltPaidHolidayType().equals(String.valueOf(TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY))) {
			// 自動付与【基準日】
			String[] aryPointDateAmount = { vo.getTxtPointDateAmount1(), vo.getTxtPointDateAmount2(),
				vo.getTxtPointDateAmount3(), vo.getTxtPointDateAmount4(), vo.getTxtPointDateAmount5(),
				vo.getTxtPointDateAmount6(), vo.getTxtPointDateAmount7(), vo.getTxtPointDateAmount8(),
				vo.getTxtPointDateAmount9(), vo.getTxtPointDateAmount10(), vo.getTxtPointDateAmount11(),
				vo.getTxtPointDateAmount12() };
			for (int i = 1; i <= 12; i++) {
				if (!aryPointDateAmount[i - 1].isEmpty()) {
					PaidHolidayPointDateDtoInterface pointDto = registPoint.getInitDto();
					pointDto.setTimesPointDate(i);
					setDtoFields(pointDto);
					registPoint.update(pointDto);
				}
			}
		} else if (vo.getPltPaidHolidayType().equals(String.valueOf(TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH))
				|| vo.getPltPaidHolidayType().equals(String.valueOf(TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY))) {
			// 自動付与【入社日】
			time().paidHolidayEntranceDateRegist().delete(vo.getTxtPaidHolidayCode(), getEditActivateDate());
			registEntranceDto();
		} else if (isProportionally()) {
			// 比例
			addProportionally();
		}
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
		// 初期値設定
		setDefaultValues();
		// 履歴編集モード設定
		setEditUpdateMode(dto.getPaidHolidayCode(), dto.getActivateDate());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException  インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO準備
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// 同コネクションを使用するため最初にtime()を定義する。
		TimeBeanHandlerInterface time = time();
		Date activateDate = getEditActivateDate();
		// 登録クラス取得(基準日)
		PaidHolidayPointDateRegistBeanInterface registPoint = time.paidHolidayPointDateRegist();
		// 登録クラス取得(比例)
		PaidHolidayProportionallyRegistBeanInterface paidHolidayProportionallyRegist = time
			.paidHolidayProportionallyRegist();
		// DTOの準備
		// 基本情報
		PaidHolidayDtoInterface dto = timeReference().paidHoliday().findForKey(vo.getTxtPaidHolidayCode(),
				getEditActivateDate());
		// DTOに値を設定、履歴追加処理
		setDtoFields(dto);
		// 削除処理
		time.paidHolidayRegist().delete(dto);
		// 削除処理（基準日）
		registPoint.delete(vo.getTxtPaidHolidayCode(), activateDate);
		// 削除処理（比例）
		paidHolidayProportionallyRegist.delete(vo.getTxtPaidHolidayCode(), activateDate);
		// 初年度付与
		// 削除処理
		time.paidHolidayFirstYearRegist().delete(dto.getPaidHolidayCode(), dto.getActivateDate());
		// DTOに値を設定、履歴追加処理
		// ストック休暇設定
		StockHolidayDtoInterface dtoStock = timeReference().stockHoliday().findForKey(vo.getTxtPaidHolidayCode(),
				getEditActivateDate());
		setDtoFields(dtoStock);
		// 削除処理
		time.stockHolidayRegist().delete(dtoStock);
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
	 */
	protected void insertMode() {
		// 編集モード設定
		setEditInsertMode();
		// 初期値設定
		setDefaultValues();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 */
	protected void addMode() {
		// 履歴追加モード設定
		setEditAddMode();
	}
	
	/**
	 * 複製モードで画面を表示する。<br>
	 */
	protected void replicationMode() {
		// 複製モード設定
		setEditReplicationMode();
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// コードを空白に設定
		vo.setTxtPaidHolidayCode("");
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
	 * 有休コードと有効日で編集対象情報を取得する。<br>
	 * @param paidHolidayCode	勤務形態
	 * @param activateDate		有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String paidHolidayCode, Date activateDate) throws MospException {
		PaidHolidayProportionallyReferenceBeanInterface paidHolidayProportionally = timeReference()
			.paidHolidayProportionally();
		// 有給休暇マスタから情報を取得
		PaidHolidayDtoInterface dto = timeReference().paidHoliday().findForKey(paidHolidayCode, activateDate);
		// 有給休暇比例付与マスタからリストを取得
		List<PaidHolidayProportionallyDtoInterface> PaidHolidayProportionallyList = paidHolidayProportionally
			.findForList(paidHolidayCode, activateDate);
		// 有給休暇初年度マスタから情報を取得
		PaidHolidayFirstYearDtoInterface firstDto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				activateDate, 1);
		// 有給休暇基準日マスタから情報を取得
		List<PaidHolidayPointDateDtoInterface> pointList = timeReference().paidHolidayPointDate()
			.findForList(paidHolidayCode, activateDate);
		// 有給休暇入社日マスタから情報を取得
		List<PaidHolidayEntranceDateDtoInterface> entranceList = timeReference().paidHolidayEntranceDate()
			.findForList(paidHolidayCode, activateDate);
		// ストック休暇マスタから情報を取得
		StockHolidayDtoInterface stockDto = timeReference().stockHoliday().findForKey(paidHolidayCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		for (PaidHolidayProportionallyDtoInterface paidHolidayProportionallyDto : PaidHolidayProportionallyList) {
			checkSelectedDataExist(paidHolidayProportionallyDto);
		}
		checkSelectedDataExist(firstDto);
		for (PaidHolidayPointDateDtoInterface paidHolidayPointDateDto : pointList) {
			checkSelectedDataExist(paidHolidayPointDateDto);
		}
		for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : entranceList) {
			checkSelectedDataExist(paidHolidayEntranceDateDto);
		}
		checkSelectedDataExist(stockDto);
		// VOにセット
		setVoFields(dto);
		setVoFieldsProportionally(PaidHolidayProportionallyList);
		setVoFieldsFirst(firstDto.getPaidHolidayCode());
		setVoFields(pointList);
		setVoFieldsEntrance(entranceList);
		setVoFields(stockDto);
		// 編集モード設定
		setEditUpdateMode(timeReference().paidHoliday().getPaidHolidayHistory(paidHolidayCode));
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// 登録情報欄の初期値設定
		vo.setTxtPaidHolidayCode("");
		vo.setTxtPaidHolidayName("");
		vo.setTxtPaidHolidayAbbr("");
		vo.setTxtWorkRatio("0");
		vo.setPltPaidHolidayType("0");
		vo.setPltTimelyPaidHoliday(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		//vo.setPltTimelyPaidHolidayTime("0");
		vo.setPltTimeAcquisitionLimitDays("0");
		vo.setPltTimeAcquisitionLimitTimes("0");
		vo.setPltAppliTimeInterval("0");
		vo.setTxtMaxCarryOverAmount("");
		vo.setTxtTotalMaxAmount("");
		vo.setPltMaxCarryOverYear(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		vo.setPltMaxCarryOverTimes("0");
		vo.setPltHalfDayUnit(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		vo.setPltWorkOnHolidayCalc("3");
		
		vo.setTxtProportionallyOneDayAndSixMonths(Integer.toString(1));
		vo.setTxtProportionallyOneDayAndOneYearAndSixMonths(Integer.toString(2));
		vo.setTxtProportionallyOneDayAndTwoYearsAndSixMonths(Integer.toString(2));
		vo.setTxtProportionallyOneDayAndThreeYearsAndSixMonths(Integer.toString(2));
		vo.setTxtProportionallyOneDayAndFourYearsAndSixMonths(Integer.toString(3));
		vo.setTxtProportionallyOneDayAndFiveYearsAndSixMonths(Integer.toString(3));
		vo.setTxtProportionallyOneDayAndSixYearsAndSixMonthsOrMore(Integer.toString(3));
		vo.setTxtProportionallyTwoDaysAndSixMonths(Integer.toString(3));
		vo.setTxtProportionallyTwoDaysAndOneYearAndSixMonths(Integer.toString(4));
		vo.setTxtProportionallyTwoDaysAndTwoYearsAndSixMonths(Integer.toString(4));
		vo.setTxtProportionallyTwoDaysAndThreeYearsAndSixMonths(Integer.toString(5));
		vo.setTxtProportionallyTwoDaysAndFourYearsAndSixMonths(Integer.toString(6));
		vo.setTxtProportionallyTwoDaysAndFiveYearsAndSixMonths(Integer.toString(6));
		vo.setTxtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore(Integer.toString(7));
		vo.setTxtProportionallyThreeDaysAndSixMonths(Integer.toString(5));
		vo.setTxtProportionallyThreeDaysAndOneYearAndSixMonths(Integer.toString(6));
		vo.setTxtProportionallyThreeDaysAndTwoYearsAndSixMonths(Integer.toString(6));
		vo.setTxtProportionallyThreeDaysAndThreeYearsAndSixMonths(Integer.toString(8));
		vo.setTxtProportionallyThreeDaysAndFourYearsAndSixMonths(Integer.toString(9));
		vo.setTxtProportionallyThreeDaysAndFiveYearsAndSixMonths(Integer.toString(10));
		vo.setTxtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore(Integer.toString(11));
		vo.setTxtProportionallyFourDaysAndSixMonths(Integer.toString(7));
		vo.setTxtProportionallyFourDaysAndOneYearAndSixMonths(Integer.toString(8));
		vo.setTxtProportionallyFourDaysAndTwoYearsAndSixMonths(Integer.toString(9));
		vo.setTxtProportionallyFourDaysAndThreeYearsAndSixMonths(Integer.toString(10));
		vo.setTxtProportionallyFourDaysAndFourYearsAndSixMonths(Integer.toString(12));
		vo.setTxtProportionallyFourDaysAndFiveYearsAndSixMonths(Integer.toString(13));
		vo.setTxtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore(Integer.toString(15));
		vo.setTxtProportionallyFiveDaysOrMoreAndSixMonths(Integer.toString(10));
		vo.setTxtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths(Integer.toString(11));
		vo.setTxtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths(Integer.toString(12));
		vo.setTxtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths(Integer.toString(14));
		vo.setTxtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths(Integer.toString(16));
		vo.setTxtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths(Integer.toString(18));
		vo.setTxtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore(Integer.toString(20));
		
		vo.setTxtGivingTimingJanuary("0");
		vo.setTxtGivingTimingFebruary("0");
		vo.setTxtGivingTimingMarch("0");
		vo.setTxtGivingTimingApril("0");
		vo.setTxtGivingTimingMay("0");
		vo.setTxtGivingTimingJune("0");
		vo.setTxtGivingTimingJuly("0");
		vo.setTxtGivingTimingAugust("0");
		vo.setTxtGivingTimingSeptember("0");
		vo.setTxtGivingTimingOctober("0");
		vo.setTxtGivingTimingNovember("0");
		vo.setTxtGivingTimingDecember("0");
		
		vo.setTxtGivingAmountJanuary("0");
		vo.setTxtGivingAmountFebruary("0");
		vo.setTxtGivingAmountMarch("0");
		vo.setTxtGivingAmountApril("0");
		vo.setTxtGivingAmountMay("0");
		vo.setTxtGivingAmountJune("0");
		vo.setTxtGivingAmountJuly("0");
		vo.setTxtGivingAmountAugust("0");
		vo.setTxtGivingAmountSeptember("0");
		vo.setTxtGivingAmountOctober("0");
		vo.setTxtGivingAmountNovember("0");
		vo.setTxtGivingAmountDecember("0");
		
		vo.setTxtGivingLimitJanuary("0");
		vo.setTxtGivingLimitFebruary("0");
		vo.setTxtGivingLimitMarch("0");
		vo.setTxtGivingLimitApril("0");
		vo.setTxtGivingLimitMay("0");
		vo.setTxtGivingLimitJune("0");
		vo.setTxtGivingLimitJuly("0");
		vo.setTxtGivingLimitAugust("0");
		vo.setTxtGivingLimitSeptember("0");
		vo.setTxtGivingLimitOctober("0");
		vo.setTxtGivingLimitNovember("0");
		vo.setTxtGivingLimitDecember("0");
		
		vo.setTxtPointDateMonth("1");
		vo.setTxtPointDateDay("1");
		
		vo.setTxtTimesPointDate1("1");
		vo.setTxtTimesPointDate2("2");
		vo.setTxtTimesPointDate3("3");
		vo.setTxtTimesPointDate4("4");
		vo.setTxtTimesPointDate5("5");
		vo.setTxtTimesPointDate6("6");
		vo.setTxtTimesPointDate7("7");
		vo.setTxtTimesPointDate8("8");
		vo.setTxtTimesPointDate9("9");
		vo.setTxtTimesPointDate10("10");
		vo.setTxtTimesPointDate11("11");
		vo.setTxtTimesPointDate12("12");
		
		vo.setTxtPointDateAmount1("");
		vo.setTxtPointDateAmount2("");
		vo.setTxtPointDateAmount3("");
		vo.setTxtPointDateAmount4("");
		vo.setTxtPointDateAmount5("");
		vo.setTxtPointDateAmount6("");
		vo.setTxtPointDateAmount7("");
		vo.setTxtPointDateAmount8("");
		vo.setTxtPointDateAmount9("");
		vo.setTxtPointDateAmount10("");
		vo.setTxtPointDateAmount11("");
		vo.setTxtPointDateAmount12("");
		
		vo.setTxtGeneralPointAmount("0");
		
		vo.setTxtWorkYear1("");
		vo.setTxtWorkYear2("");
		vo.setTxtWorkYear3("");
		vo.setTxtWorkYear4("");
		vo.setTxtWorkYear5("");
		vo.setTxtWorkYear6("");
		vo.setTxtWorkYear7("");
		vo.setTxtWorkYear8("");
		vo.setTxtWorkYear9("");
		vo.setTxtWorkYear10("");
		vo.setTxtWorkYear11("");
		vo.setTxtWorkYear12("");
		
		vo.setTxtWorkMonth1("");
		vo.setTxtWorkMonth2("");
		vo.setTxtWorkMonth3("");
		vo.setTxtWorkMonth4("");
		vo.setTxtWorkMonth5("");
		vo.setTxtWorkMonth6("");
		vo.setTxtWorkMonth7("");
		vo.setTxtWorkMonth8("");
		vo.setTxtWorkMonth9("");
		vo.setTxtWorkMonth10("");
		vo.setTxtWorkMonth11("");
		vo.setTxtWorkMonth12("");
		
		vo.setTxtJoiningDateAmount1("");
		vo.setTxtJoiningDateAmount2("");
		vo.setTxtJoiningDateAmount3("");
		vo.setTxtJoiningDateAmount4("");
		vo.setTxtJoiningDateAmount5("");
		vo.setTxtJoiningDateAmount6("");
		vo.setTxtJoiningDateAmount7("");
		vo.setTxtJoiningDateAmount8("");
		vo.setTxtJoiningDateAmount9("");
		vo.setTxtJoiningDateAmount10("");
		vo.setTxtJoiningDateAmount11("");
		vo.setTxtJoiningDateAmount12("");
		
		vo.setTxtGeneralJoiningMonth("");
		vo.setTxtGeneralJoiningAmount("");
		
		vo.setTxtStockYearAmount("0");
		vo.setTxtStockTotalAmount("0");
		vo.setTxtStockLimitDate("0");
		
		// 各識別IDの初期化
		long[] aryId = new long[12];
		vo.setTmmPaidHolidayFirstYearId(aryId);
		vo.setTmmPaidHolidayPointDateId(aryId);
		vo.setTmmPaidHolidayEntranceDateId(aryId);
	}
	
	/**
	 * 有給休暇比例付与新規追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insertProportionally() throws MospException {
		PaidHolidayProportionallyRegistBeanInterface regist = time().paidHolidayProportionallyRegist();
		for (int i = 0; i < 5; i++) {
			int prescribedWeeklyWorkingDays = i + 1;
			for (int j = 0; j < 7; j++) {
				PaidHolidayProportionallyDtoInterface dto = regist.getInitDto();
				int continuousServiceTermsCountingFromTheEmploymentDay = 12 * j + 6;
				setDtoFields(dto, prescribedWeeklyWorkingDays, continuousServiceTermsCountingFromTheEmploymentDay);
				// 新規登録
				regist.insert(dto);
			}
		}
	}
	
	/**
	 * 有給休暇比例付与履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addProportionally() throws MospException {
		PaidHolidayProportionallyRegistBeanInterface regist = time().paidHolidayProportionallyRegist();
		for (int i = 0; i < 5; i++) {
			int prescribedWeeklyWorkingDays = i + 1;
			for (int j = 0; j < 7; j++) {
				PaidHolidayProportionallyDtoInterface dto = regist.getInitDto();
				int continuousServiceTermsCountingFromTheEmploymentDay = 12 * j + 6;
				setDtoFields(dto, prescribedWeeklyWorkingDays, continuousServiceTermsCountingFromTheEmploymentDay);
				// 履歴追加
				regist.add(dto);
			}
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(PaidHolidayDtoInterface dto) {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmmPaidHolidayId(vo.getRecordId());
		dto.setActivateDate(getEditActivateDate());
		dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
		dto.setPaidHolidayName(vo.getTxtPaidHolidayName());
		dto.setPaidHolidayAbbr(vo.getTxtPaidHolidayAbbr());
		dto.setWorkRatio(getInt(vo.getTxtWorkRatio()));
		dto.setPaidHolidayType(getInt(vo.getPltPaidHolidayType()));
		// 仮付与[現在使用していない]
		dto.setScheduleGiving(0);
		dto.setTimelyPaidHolidayFlag(getInt(vo.getPltTimelyPaidHoliday()));
		// 有休単位時間(画面上で表示せず必ず1を登録)
		dto.setTimelyPaidHolidayTime(1);
		dto.setTimeAcquisitionLimitDays(getInt(vo.getPltTimeAcquisitionLimitDays()));
		dto.setTimeAcquisitionLimitTimes(getInt(vo.getPltTimeAcquisitionLimitTimes()));
		dto.setAppliTimeInterval(getInt(vo.getPltAppliTimeInterval()));
		dto.setMaxCarryOverYear(getInt(vo.getPltMaxCarryOverYear()));
		dto.setMaxCarryOverTimes(getInt(vo.getPltMaxCarryOverTimes()));
		dto.setHalfDayUnit(getInt(vo.getPltHalfDayUnit()));
		dto.setWorkOnHolidayCalc(getInt(vo.getPltWorkOnHolidayCalc()));
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		dto.setPointDateMonth(getInt(vo.getTxtPointDateMonth()));
		dto.setPointDateDay(getInt(vo.getTxtPointDateDay()));
		if (vo.getTxtGeneralPointAmount().length() != 0) {
			dto.setGeneralPointAmount(getInt(vo.getTxtGeneralPointAmount()));
		}
		if (vo.getTxtGeneralJoiningMonth().length() != 0) {
			dto.setGeneralJoiningMonth(getInt(vo.getTxtGeneralJoiningMonth()));
		}
		if (vo.getTxtGeneralJoiningAmount().length() != 0) {
			dto.setGeneralJoiningAmount(getInt(vo.getTxtGeneralJoiningAmount()));
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 */
	protected void setDtoFields(PaidHolidayProportionallyDtoInterface dto, int prescribedWeeklyWorkingDays,
			int continuousServiceTermsCountingFromTheEmploymentDay) {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
		dto.setActivateDate(getEditActivateDate());
		dto.setPrescribedWeeklyWorkingDays(prescribedWeeklyWorkingDays);
		dto.setContinuousServiceTermsCountingFromTheEmploymentDay(continuousServiceTermsCountingFromTheEmploymentDay);
		dto.setDays(0);
		dto.setInactivateFlag(Integer.parseInt(vo.getPltEditInactivate()));
		if (!Integer.toString(TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY).equals(vo.getPltPaidHolidayType())) {
			// 比例でない場合
			return;
		}
		// 比例である場合
		if (isFiveDays(prescribedWeeklyWorkingDays)) {
			// 5日以上の場合
			if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFiveDaysOrMoreAndSixMonths()));
				return;
			} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 1年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths()));
				return;
			} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 2年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths()));
				return;
			} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 3年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths()));
				return;
			} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 4年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths()));
				return;
			} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 5年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths()));
				return;
			} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6年6ヶ月以上の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore()));
				return;
			}
		} else if (isFourDays(prescribedWeeklyWorkingDays)) {
			// 4日の場合
			if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFourDaysAndSixMonths()));
				return;
			} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 1年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFourDaysAndOneYearAndSixMonths()));
				return;
			} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 2年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFourDaysAndTwoYearsAndSixMonths()));
				return;
			} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 3年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFourDaysAndThreeYearsAndSixMonths()));
				return;
			} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 4年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFourDaysAndFourYearsAndSixMonths()));
				return;
			} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 5年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFourDaysAndFiveYearsAndSixMonths()));
				return;
			} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6年6ヶ月以上の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore()));
				return;
			}
		} else if (isThreeDays(prescribedWeeklyWorkingDays)) {
			// 3日の場合
			if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyThreeDaysAndSixMonths()));
				return;
			} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 1年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyThreeDaysAndOneYearAndSixMonths()));
				return;
			} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 2年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyThreeDaysAndTwoYearsAndSixMonths()));
				return;
			} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 3年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyThreeDaysAndThreeYearsAndSixMonths()));
				return;
			} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 4年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyThreeDaysAndFourYearsAndSixMonths()));
				return;
			} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 5年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyThreeDaysAndFiveYearsAndSixMonths()));
				return;
			} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6年6ヶ月以上の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore()));
				return;
			}
		} else if (isTwoDays(prescribedWeeklyWorkingDays)) {
			// 2日の場合
			if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyTwoDaysAndSixMonths()));
				return;
			} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 1年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyTwoDaysAndOneYearAndSixMonths()));
				return;
			} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 2年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyTwoDaysAndTwoYearsAndSixMonths()));
				return;
			} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 3年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyTwoDaysAndThreeYearsAndSixMonths()));
				return;
			} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 4年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyTwoDaysAndFourYearsAndSixMonths()));
				return;
			} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 5年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyTwoDaysAndFiveYearsAndSixMonths()));
				return;
			} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6年6ヶ月以上の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore()));
				return;
			}
		} else if (isOneDay(prescribedWeeklyWorkingDays)) {
			// 1日の場合
			if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyOneDayAndSixMonths()));
				return;
			} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 1年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyOneDayAndOneYearAndSixMonths()));
				return;
			} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 2年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyOneDayAndTwoYearsAndSixMonths()));
				return;
			} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 3年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyOneDayAndThreeYearsAndSixMonths()));
				return;
			} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 4年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyOneDayAndFourYearsAndSixMonths()));
				return;
			} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 5年6ヶ月の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyOneDayAndFiveYearsAndSixMonths()));
				return;
			} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
				// 6年6ヶ月以上の場合
				dto.setDays(Integer.parseInt(vo.getTxtProportionallyOneDayAndSixYearsAndSixMonthsOrMore()));
			}
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(PaidHolidayDtoInterface dto) {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmmPaidHolidayId());
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtPaidHolidayCode(dto.getPaidHolidayCode());
		vo.setTxtPaidHolidayName(dto.getPaidHolidayName());
		vo.setTxtPaidHolidayAbbr(dto.getPaidHolidayAbbr());
		vo.setTxtWorkRatio(String.valueOf(dto.getWorkRatio()));
		vo.setPltPaidHolidayType(String.valueOf(dto.getPaidHolidayType()));
		vo.setPltTimelyPaidHoliday(String.valueOf(dto.getTimelyPaidHolidayFlag()));
		// 有休単位時間(画面上で表示せず必ず1を登録)
		//vo.setPltTimelyPaidHolidayTime(String.valueOf(dto.getTimelyPaidHolidayTime()));
		vo.setPltTimeAcquisitionLimitDays(String.valueOf(dto.getTimeAcquisitionLimitDays()));
		vo.setPltTimeAcquisitionLimitTimes(String.valueOf(dto.getTimeAcquisitionLimitTimes()));
		vo.setPltAppliTimeInterval(String.valueOf(dto.getAppliTimeInterval()));
		vo.setTxtMaxCarryOverAmount(String.valueOf(dto.getMaxCarryOverAmount()));
		vo.setTxtTotalMaxAmount(String.valueOf(dto.getTotalMaxAmount()));
		vo.setPltMaxCarryOverYear(String.valueOf(dto.getMaxCarryOverYear()));
		vo.setPltMaxCarryOverTimes(String.valueOf(dto.getMaxCarryOverTimes()));
		vo.setPltHalfDayUnit(String.valueOf(dto.getHalfDayUnit()));
		vo.setPltWorkOnHolidayCalc(String.valueOf(dto.getWorkOnHolidayCalc()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		vo.setTxtGeneralPointAmount(String.valueOf(dto.getGeneralPointAmount()));
		vo.setTxtGeneralJoiningMonth(String.valueOf(dto.getGeneralJoiningMonth()));
		vo.setTxtGeneralJoiningAmount(String.valueOf(dto.getGeneralJoiningAmount()));
		vo.setTxtPointDateMonth(String.valueOf(dto.getPointDateMonth()));
		vo.setTxtPointDateDay(String.valueOf(dto.getPointDateDay()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list リスト
	 */
	protected void setVoFieldsProportionally(List<PaidHolidayProportionallyDtoInterface> list) {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		for (PaidHolidayProportionallyDtoInterface dto : list) {
			int prescribedWeeklyWorkingDays = dto.getPrescribedWeeklyWorkingDays();
			int continuousServiceTermsCountingFromTheEmploymentDay = dto
				.getContinuousServiceTermsCountingFromTheEmploymentDay();
			if (isFiveDays(prescribedWeeklyWorkingDays)) {
				// 5日以上の場合
				if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6ヶ月の場合
					vo.setTxtProportionallyFiveDaysOrMoreAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 1年6ヶ月の場合
					vo.setTxtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 2年6ヶ月の場合
					vo.setTxtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 3年6ヶ月の場合
					vo.setTxtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 4年6ヶ月の場合
					vo.setTxtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 5年6ヶ月の場合
					vo.setTxtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6年6ヶ月以上の場合
					vo.setTxtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore(Integer.toString(dto.getDays()));
					continue;
				}
			} else if (isFourDays(prescribedWeeklyWorkingDays)) {
				// 4日の場合
				if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6ヶ月の場合
					vo.setTxtProportionallyFourDaysAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 1年6ヶ月の場合
					vo.setTxtProportionallyFourDaysAndOneYearAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 2年6ヶ月の場合
					vo.setTxtProportionallyFourDaysAndTwoYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 3年6ヶ月の場合
					vo.setTxtProportionallyFourDaysAndThreeYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 4年6ヶ月の場合
					vo.setTxtProportionallyFourDaysAndFourYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 5年6ヶ月の場合
					vo.setTxtProportionallyFourDaysAndFiveYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6年6ヶ月以上の場合
					vo.setTxtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore(Integer.toString(dto.getDays()));
					continue;
				}
			} else if (isThreeDays(prescribedWeeklyWorkingDays)) {
				// 3日の場合
				if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6ヶ月の場合
					vo.setTxtProportionallyThreeDaysAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 1年6ヶ月の場合
					vo.setTxtProportionallyThreeDaysAndOneYearAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 2年6ヶ月の場合
					vo.setTxtProportionallyThreeDaysAndTwoYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 3年6ヶ月の場合
					vo.setTxtProportionallyThreeDaysAndThreeYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 4年6ヶ月の場合
					vo.setTxtProportionallyThreeDaysAndFourYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 5年6ヶ月の場合
					vo.setTxtProportionallyThreeDaysAndFiveYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6年6ヶ月以上の場合
					vo.setTxtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore(Integer.toString(dto.getDays()));
					continue;
				}
			} else if (isTwoDays(prescribedWeeklyWorkingDays)) {
				// 2日の場合
				if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6ヶ月の場合
					vo.setTxtProportionallyTwoDaysAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 1年6ヶ月の場合
					vo.setTxtProportionallyTwoDaysAndOneYearAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 2年6ヶ月の場合
					vo.setTxtProportionallyTwoDaysAndTwoYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 3年6ヶ月の場合
					vo.setTxtProportionallyTwoDaysAndThreeYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 4年6ヶ月の場合
					vo.setTxtProportionallyTwoDaysAndFourYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 5年6ヶ月の場合
					vo.setTxtProportionallyTwoDaysAndFiveYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6年6ヶ月以上の場合
					vo.setTxtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore(Integer.toString(dto.getDays()));
					continue;
				}
			} else if (prescribedWeeklyWorkingDays == 1) {
				// 1日の場合
				if (isSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6ヶ月の場合
					vo.setTxtProportionallyOneDayAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isOneYearAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 1年6ヶ月の場合
					vo.setTxtProportionallyOneDayAndOneYearAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isTwoYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 2年6ヶ月の場合
					vo.setTxtProportionallyOneDayAndTwoYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isThreeYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 3年6ヶ月の場合
					vo.setTxtProportionallyOneDayAndThreeYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFourYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 4年6ヶ月の場合
					vo.setTxtProportionallyOneDayAndFourYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isFiveYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 5年6ヶ月の場合
					vo.setTxtProportionallyOneDayAndFiveYearsAndSixMonths(Integer.toString(dto.getDays()));
					continue;
				} else if (isSixYearsAndSixMonths(continuousServiceTermsCountingFromTheEmploymentDay)) {
					// 6年6ヶ月以上の場合
					vo.setTxtProportionallyOneDayAndSixYearsAndSixMonthsOrMore(Integer.toString(dto.getDays()));
				}
			}
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(PaidHolidayFirstYearDtoInterface dto) {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setActivateDate(getEditActivateDate());
		dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		// 値が何も入っていない場合は0を設定する
		// 1月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_JANUARY)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(0));
			if (!(vo.getTxtGivingTimingJanuary().isEmpty()) || !(vo.getTxtGivingAmountJanuary().isEmpty())
					|| !(vo.getTxtGivingLimitJanuary().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingJanuary()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountJanuary()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitJanuary()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 2月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_FEBRUARY)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(1));
			if (!(vo.getTxtGivingTimingFebruary().isEmpty()) || !(vo.getTxtGivingAmountFebruary().isEmpty())
					|| !(vo.getTxtGivingLimitFebruary().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingFebruary()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountFebruary()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitFebruary()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 3月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_MARCH)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(2));
			if (!(vo.getTxtGivingTimingMarch().isEmpty()) || !(vo.getTxtGivingAmountMarch().isEmpty())
					|| !(vo.getTxtGivingLimitMarch().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingMarch()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountMarch()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitMarch()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 4月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_APRIL)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(3));
			if (!(vo.getTxtGivingTimingApril().isEmpty()) || !(vo.getTxtGivingAmountApril().isEmpty())
					|| !(vo.getTxtGivingLimitApril().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingApril()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountApril()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitApril()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 5月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_MAY)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(4));
			if (!(vo.getTxtGivingTimingMay().isEmpty()) || !(vo.getTxtGivingAmountMay().isEmpty())
					|| !(vo.getTxtGivingLimitMay().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingMay()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountMay()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitMay()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 6月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_JUNE)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(5));
			if (!(vo.getTxtGivingTimingJune().isEmpty()) || !(vo.getTxtGivingAmountJune().isEmpty())
					|| !(vo.getTxtGivingLimitJune().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingJune()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountJune()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitJune()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 7月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_JULY)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(6));
			if (!(vo.getTxtGivingTimingJuly().isEmpty()) || !(vo.getTxtGivingAmountJuly().isEmpty())
					|| !(vo.getTxtGivingLimitJuly().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingJuly()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountJuly()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitJuly()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 8月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_AUGUST)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(7));
			if (!(vo.getTxtGivingTimingAugust().isEmpty()) || !(vo.getTxtGivingAmountAugust().isEmpty())
					|| !(vo.getTxtGivingLimitAugust().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingAugust()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountAugust()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitAugust()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 9月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_SEPTEMBER)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(8));
			if (!(vo.getTxtGivingTimingSeptember().isEmpty()) || !(vo.getTxtGivingAmountSeptember().isEmpty())
					|| !(vo.getTxtGivingLimitSeptember().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingSeptember()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountSeptember()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitSeptember()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 10月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_OCTOBER)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(9));
			if (!(vo.getTxtGivingTimingOctober().isEmpty()) || !(vo.getTxtGivingAmountOctober().isEmpty())
					|| !(vo.getTxtGivingLimitOctober().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingOctober()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountOctober()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitOctober()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 11月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_NOVEMBER)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(10));
			if (!(vo.getTxtGivingTimingNovember().isEmpty()) || !(vo.getTxtGivingAmountNovember().isEmpty())
					|| !(vo.getTxtGivingLimitNovember().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingNovember()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountNovember()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitNovember()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
		// 12月分
		if (dto.getEntranceMonth() == getInt(TimeConst.CODE_DISPLAY_DECEMBER)) {
			dto.setTmmPaidHolidayFirstYearId(vo.getTmmPaidHolidayFirstYearId(11));
			if (!(vo.getTxtGivingTimingDecember().isEmpty()) || !(vo.getTxtGivingAmountDecember().isEmpty())
					|| !(vo.getTxtGivingLimitDecember().isEmpty())) {
				dto.setGivingMonth(getInt(vo.getTxtGivingTimingDecember()));
				dto.setGivingAmount(getInt(vo.getTxtGivingAmountDecember()));
				dto.setGivingLimit(getInt(vo.getTxtGivingLimitDecember()));
			} else {
				dto.setGivingMonth(0);
				dto.setGivingAmount(0);
				dto.setGivingLimit(0);
			}
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生
	 */
	protected void setVoFieldsFirst(String paidHolidayCode) throws MospException {
		setVoFieldsJanuary(paidHolidayCode);
		setVoFieldsFebruary(paidHolidayCode);
		setVoFieldsMarch(paidHolidayCode);
		setVoFieldsApril(paidHolidayCode);
		setVoFieldsMay(paidHolidayCode);
		setVoFieldsJune(paidHolidayCode);
		setVoFieldsJuly(paidHolidayCode);
		setVoFieldsAugust(paidHolidayCode);
		setVoFieldsSeptember(paidHolidayCode);
		setVoFieldsOctober(paidHolidayCode);
		setVoFieldsNovember(paidHolidayCode);
		setVoFieldsDecember(paidHolidayCode);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsJanuary(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 1);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 0);
		vo.setTxtGivingTimingJanuary(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountJanuary(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitJanuary(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsFebruary(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 2);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 1);
		vo.setTxtGivingTimingFebruary(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountFebruary(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitFebruary(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsMarch(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 3);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 2);
		vo.setTxtGivingTimingMarch(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountMarch(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitMarch(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsApril(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 4);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 3);
		vo.setTxtGivingTimingApril(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountApril(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitApril(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsMay(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 5);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 4);
		vo.setTxtGivingTimingMay(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountMay(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitMay(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsJune(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 6);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 5);
		vo.setTxtGivingTimingJune(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountJune(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitJune(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsJuly(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 7);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 6);
		vo.setTxtGivingTimingJuly(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountJuly(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitJuly(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsAugust(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 8);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 7);
		vo.setTxtGivingTimingAugust(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountAugust(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitAugust(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsSeptember(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 9);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 8);
		vo.setTxtGivingTimingSeptember(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountSeptember(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitSeptember(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsOctober(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 10);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 9);
		vo.setTxtGivingTimingOctober(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountOctober(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitOctober(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsNovember(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 11);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 10);
		vo.setTxtGivingTimingNovember(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountNovember(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitNovember(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsDecember(String paidHolidayCode) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		PaidHolidayFirstYearDtoInterface dto = timeReference().paidHolidayFirstYear().findForKey(paidHolidayCode,
				getEditActivateDate(), 12);
		if (dto == null) {
			return;
		}
		vo.setTmmPaidHolidayFirstYearId(dto.getTmmPaidHolidayFirstYearId(), 11);
		vo.setTxtGivingTimingDecember(String.valueOf(dto.getGivingMonth()));
		vo.setTxtGivingAmountDecember(String.valueOf(dto.getGivingAmount()));
		vo.setTxtGivingLimitDecember(String.valueOf(dto.getGivingLimit()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(PaidHolidayPointDateDtoInterface dto) {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setActivateDate(getEditActivateDate());
		dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		if (dto.getTimesPointDate() == 1) {
			if (!(vo.getTxtPointDateAmount1().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(0));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate1()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount1()));
			}
		}
		if (dto.getTimesPointDate() == 2) {
			if (!(vo.getTxtPointDateAmount2().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(1));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate2()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount2()));
			}
		}
		if (dto.getTimesPointDate() == 3) {
			if (!(vo.getTxtPointDateAmount3().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(2));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate3()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount3()));
			}
		}
		if (dto.getTimesPointDate() == 4) {
			if (!(vo.getTxtPointDateAmount4().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(3));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate4()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount4()));
			}
		}
		if (dto.getTimesPointDate() == 5) {
			if (!(vo.getTxtPointDateAmount5().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(4));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate5()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount5()));
			}
		}
		if (dto.getTimesPointDate() == 6) {
			if (!(vo.getTxtPointDateAmount6().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(5));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate6()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount6()));
			}
		}
		if (dto.getTimesPointDate() == 7) {
			if (!(vo.getTxtPointDateAmount7().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(6));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate7()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount7()));
			}
		}
		if (dto.getTimesPointDate() == 8) {
			if (!(vo.getTxtPointDateAmount8().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(7));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate8()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount8()));
			}
		}
		if (dto.getTimesPointDate() == 9) {
			if (!(vo.getTxtPointDateAmount9().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(8));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate9()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount9()));
			}
		}
		if (dto.getTimesPointDate() == 10) {
			if (!(vo.getTxtPointDateAmount10().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(9));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate10()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount10()));
			}
		}
		if (dto.getTimesPointDate() == 11) {
			if (!(vo.getTxtPointDateAmount11().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(10));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate11()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount11()));
			}
		}
		if (dto.getTimesPointDate() == 12) {
			if (!(vo.getTxtPointDateAmount12().isEmpty())) {
				dto.setTmmPaidHolidayPointDateId(vo.getTmmPaidHolidayPointDateId(11));
				dto.setTimesPointDate(getInt(vo.getTxtTimesPointDate12()));
				dto.setPointDateAmount(getInt(vo.getTxtPointDateAmount12()));
			}
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list リスト
	 * @throws MospException 例外処理
	 */
	protected void setVoFields(List<PaidHolidayPointDateDtoInterface> list) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			PaidHolidayPointDateDtoInterface dto = list.get(i);
			vo.setTmmPaidHolidayPointDateId(dto.getTmmPaidHolidayPointDateId(), i);
			int timesPointDate = dto.getTimesPointDate();
			if (timesPointDate == 1) {
				// 自動付与1
				vo.setTxtPointDateAmount1(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 2) {
				// 自動付与2
				vo.setTxtPointDateAmount2(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 3) {
				// 自動付与3
				vo.setTxtPointDateAmount3(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 4) {
				// 自動付与4
				vo.setTxtPointDateAmount4(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 5) {
				// 自動付与5
				vo.setTxtPointDateAmount5(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 6) {
				// 自動付与6
				vo.setTxtPointDateAmount6(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 7) {
				// 自動付与7
				vo.setTxtPointDateAmount7(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 8) {
				// 自動付与8
				vo.setTxtPointDateAmount8(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 9) {
				// 自動付与9
				vo.setTxtPointDateAmount9(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 10) {
				// 自動付与10
				vo.setTxtPointDateAmount10(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 11) {
				// 自動付与11
				vo.setTxtPointDateAmount11(String.valueOf(dto.getPointDateAmount()));
			} else if (timesPointDate == 12) {
				// 自動付与12
				vo.setTxtPointDateAmount12(String.valueOf(dto.getPointDateAmount()));
			}
		}
	}
	
	/**
	 * 有給休暇入社日管理DTOを登録する。
	 * @throws MospException 例外発生時
	 */
	protected void registEntranceDto() throws MospException {
		PaidHolidayEntranceDateRegistBeanInterface regist = time.paidHolidayEntranceDateRegist();
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// 有効日取得
		Date activateDate = getEditActivateDate();
		int inactivateFlag = getInt(vo.getPltEditInactivate());
		if (!vo.getTxtWorkYear1().isEmpty() && !vo.getTxtWorkMonth1().isEmpty()
				&& !vo.getTxtJoiningDateAmount1().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear1()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth1()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount1()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear2().isEmpty() && !vo.getTxtWorkMonth2().isEmpty()
				&& !vo.getTxtJoiningDateAmount2().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear2()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth2()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount2()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear3().isEmpty() && !vo.getTxtWorkMonth3().isEmpty()
				&& !vo.getTxtJoiningDateAmount3().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear3()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth3()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount3()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear4().isEmpty() && !vo.getTxtWorkMonth4().isEmpty()
				&& !vo.getTxtJoiningDateAmount4().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear4()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth4()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount4()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear5().isEmpty() && !vo.getTxtWorkMonth5().isEmpty()
				&& !vo.getTxtJoiningDateAmount5().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear5()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth5()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount5()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear6().isEmpty() && !vo.getTxtWorkMonth6().isEmpty()
				&& !vo.getTxtJoiningDateAmount6().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear6()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth6()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount6()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear7().isEmpty() && !vo.getTxtWorkMonth7().isEmpty()
				&& !vo.getTxtJoiningDateAmount7().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear7()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth7()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount7()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear8().isEmpty() && !vo.getTxtWorkMonth8().isEmpty()
				&& !vo.getTxtJoiningDateAmount8().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear8()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth8()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount8()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear9().isEmpty() && !vo.getTxtWorkMonth9().isEmpty()
				&& !vo.getTxtJoiningDateAmount9().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear9()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth9()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount9()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear10().isEmpty() && !vo.getTxtWorkMonth10().isEmpty()
				&& !vo.getTxtJoiningDateAmount10().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear10()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth10()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount10()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear11().isEmpty() && !vo.getTxtWorkMonth11().isEmpty()
				&& !vo.getTxtJoiningDateAmount11().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear11()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth11()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount11()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
		if (!vo.getTxtWorkYear12().isEmpty() && !vo.getTxtWorkMonth12().isEmpty()
				&& !vo.getTxtJoiningDateAmount12().isEmpty()) {
			PaidHolidayEntranceDateDtoInterface dto = regist.getInitDto();
			dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
			dto.setActivateDate(activateDate);
			dto.setWorkMonth(
					getInt(vo.getTxtWorkYear12()) * TimeConst.CODE_DEFINITION_YEAR + getInt(vo.getTxtWorkMonth12()));
			dto.setJoiningDateAmount(getInt(vo.getTxtJoiningDateAmount12()));
			dto.setInactivateFlag(inactivateFlag);
			regist.insert(dto);
		}
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsEntrance(List<PaidHolidayEntranceDateDtoInterface> list) throws MospException {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			PaidHolidayEntranceDateDtoInterface dto = list.get(i);
			vo.setTmmPaidHolidayEntranceDateId(dto.getTmmPaidHolidayEntranceDateId(), i);
			if (i == 0) {
				vo.setTxtWorkYear1(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth1(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount1(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 1) {
				vo.setTxtWorkYear2(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth2(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount2(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 2) {
				vo.setTxtWorkYear3(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth3(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount3(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 3) {
				vo.setTxtWorkYear4(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth4(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount4(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 4) {
				vo.setTxtWorkYear5(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth5(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount5(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 5) {
				vo.setTxtWorkYear6(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth6(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount6(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 6) {
				vo.setTxtWorkYear7(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth7(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount7(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 7) {
				vo.setTxtWorkYear8(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth8(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount8(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 8) {
				vo.setTxtWorkYear9(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth9(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount9(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 9) {
				vo.setTxtWorkYear10(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth10(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount10(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 10) {
				vo.setTxtWorkYear11(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth11(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount11(String.valueOf(dto.getJoiningDateAmount()));
			} else if (i == 11) {
				vo.setTxtWorkYear12(String.valueOf(dto.getWorkMonth() / TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtWorkMonth12(String.valueOf(dto.getWorkMonth() % TimeConst.CODE_DEFINITION_YEAR));
				vo.setTxtJoiningDateAmount12(String.valueOf(dto.getJoiningDateAmount()));
			}
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(StockHolidayDtoInterface dto) {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmmStockHolidayId(vo.getTmmStockHolidayId());
		dto.setActivateDate(getEditActivateDate());
		dto.setPaidHolidayCode(vo.getTxtPaidHolidayCode());
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		dto.setStockYearAmount(getInt(vo.getTxtStockYearAmount()));
		dto.setStockTotalAmount(getInt(vo.getTxtStockTotalAmount()));
		dto.setStockLimitDate(getInt(vo.getTxtStockLimitDate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(StockHolidayDtoInterface dto) {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTmmStockHolidayId(dto.getTmmStockHolidayId());
		vo.setTxtStockYearAmount(String.valueOf(dto.getStockYearAmount()));
		vo.setTxtStockTotalAmount(String.valueOf(dto.getStockTotalAmount()));
		vo.setTxtStockLimitDate(String.valueOf(dto.getStockLimitDate()));
	}
	
	/**
	 * 比例かどうか確認する。<br>
	 * @return 確認結果(true：比例である、false：比例でない)
	 */
	protected boolean isProportionally() {
		// VO取得
		PaidHolidayCardVo vo = (PaidHolidayCardVo)mospParams.getVo();
		return Integer.toString(TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY).equals(vo.getPltPaidHolidayType());
	}
	
	/**
	 * 一日かどうか確認する。<br>
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @return 確認結果(true：一日である、false：一日でない)
	 */
	protected boolean isOneDay(int prescribedWeeklyWorkingDays) {
		return prescribedWeeklyWorkingDays == 1;
	}
	
	/**
	 * 二日かどうか確認する。<br>
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @return 確認結果(true：二日である、false：二日でない)
	 */
	protected boolean isTwoDays(int prescribedWeeklyWorkingDays) {
		return prescribedWeeklyWorkingDays == 2;
	}
	
	/**
	 * 三日かどうか確認する。<br>
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @return 確認結果(true：三日である、false：三日でない)
	 */
	protected boolean isThreeDays(int prescribedWeeklyWorkingDays) {
		return prescribedWeeklyWorkingDays == 3;
	}
	
	/**
	 * 四日かどうか確認する。<br>
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @return 確認結果(true：四日である、false：四日でない)
	 */
	protected boolean isFourDays(int prescribedWeeklyWorkingDays) {
		return prescribedWeeklyWorkingDays == 4;
	}
	
	/**
	 * 五日かどうか確認する。<br>
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @return 確認結果(true：五日である、false：五日でない)
	 */
	protected boolean isFiveDays(int prescribedWeeklyWorkingDays) {
//		return prescribedWeeklyWorkingDays >= 5;
		return prescribedWeeklyWorkingDays == 5;
	}
	
	/**
	 * 六箇月かどうか確認する。<br>
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 確認結果(true：六箇月である、false：六箇月でない)
	 */
	protected boolean isSixMonths(int continuousServiceTermsCountingFromTheEmploymentDay) {
		return continuousServiceTermsCountingFromTheEmploymentDay == 6;
	}
	
	/**
	 * 一年六箇月かどうか確認する。<br>
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 確認結果(true：一年六箇月である、false：一年六箇月でない)
	 */
	protected boolean isOneYearAndSixMonths(int continuousServiceTermsCountingFromTheEmploymentDay) {
		return continuousServiceTermsCountingFromTheEmploymentDay == 18;
	}
	
	/**
	 * 二年六箇月かどうか確認する。<br>
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 確認結果(true：二年六箇月である、false：二年六箇月でない)
	 */
	protected boolean isTwoYearsAndSixMonths(int continuousServiceTermsCountingFromTheEmploymentDay) {
		return continuousServiceTermsCountingFromTheEmploymentDay == 30;
	}
	
	/**
	 * 三年六箇月かどうか確認する。<br>
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 確認結果(true：三年六箇月である、false：三年六箇月でない)
	 */
	protected boolean isThreeYearsAndSixMonths(int continuousServiceTermsCountingFromTheEmploymentDay) {
		return continuousServiceTermsCountingFromTheEmploymentDay == 42;
	}
	
	/**
	 * 四年六箇月かどうか確認する。<br>
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 確認結果(true：四年六箇月である、false：四年六箇月でない)
	 */
	protected boolean isFourYearsAndSixMonths(int continuousServiceTermsCountingFromTheEmploymentDay) {
		return continuousServiceTermsCountingFromTheEmploymentDay == 54;
	}
	
	/**
	 * 五年六箇月かどうか確認する。<br>
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 確認結果(true：五年六箇月である、false：五年六箇月でない)
	 */
	protected boolean isFiveYearsAndSixMonths(int continuousServiceTermsCountingFromTheEmploymentDay) {
		return continuousServiceTermsCountingFromTheEmploymentDay == 66;
	}
	
	/**
	 * 六年六箇月かどうか確認する。<br>
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 確認結果(true：六年六箇月である、false：六年六箇月でない)
	 */
	protected boolean isSixYearsAndSixMonths(int continuousServiceTermsCountingFromTheEmploymentDay) {
		// 78箇月 (12箇月 * 6年 + 6箇月)
		int day = 78;
//		while (day < continuousServiceTermsCountingFromTheEmploymentDay) {
//			// 雇入れの日から起算した継続勤務期間より小さい場合は12箇月を加算
//			day += 12;
//		}
//		// 雇入れの日から起算した継続勤務期間以上の場合
		return continuousServiceTermsCountingFromTheEmploymentDay == day;
	}
	
}
