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
import jp.mosp.platform.base.ReferenceBeanHandlerInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfaApprovalRouteUnitDto;
import jp.mosp.platform.dto.workflow.impl.PfmApprovalRouteDto;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.workflow.vo.RouteCardVo;

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
 *  * </li><li>
 * {@link #CMD_SPECIAL_DISPLAY}
 *  * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li></ul>
 */
public class RouteCardAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * ルート一覧画面で「新規登録」ボタンをクリックした際に実行される。<br>
	 */
	public static final String	CMD_SHOW				= "PF3220";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * ルート一覧画面で「詳細」ボタンをクリックした際に実行される。<br>
	 * 一覧画面で選択したレコードを編集するよう履歴編集モードで表示される。<br>
	 * 勤務形態一覧画面で選択したレコードの情報を取得し、履歴編集モードで詳細画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "PF3221";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力項目に入力されている内容を基に新規登録モード、履歴追加モードであれば登録処理を、履歴編集モードであれば更新処理を実行する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、ルートコードが登録済みのレコードのものと同一である、同一のユニットが2つ以上選択されている、<br>
	 */
	public static final String	CMD_REGIST				= "PF3225";
	
	/**
	 * 特殊表示コマンド。<br>
	 * <br>
	 * 承認経路画面のルート名称リンク文字をクリックした際に実行される。<br>
	 * 履歴編集モードと同様に選択したレコードの情報を表示するが、画面内の入力項目とボタンは全て読取専用の状態となっている。<br>
	 */
	public static final String	CMD_SPECIAL_DISPLAY		= "PF3226";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているレコード情報の削除を行う。<br>
	 */
	public static final String	CMD_DELETE				= "PF3227";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 有効日入力欄に入力されている日付時点で有効なユニット情報を取得し、ユニット名称プルダウンに表示する。<br>
	 * ユニット名称プルダウンは承認階層プルダウンで選択した数の分だけ用意する。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF3270";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "PF3271";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日入力欄を編集可能にした上で内容を空欄にする。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE			= "PF3273";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public RouteCardAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new RouteCardVo();
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
			/*
			 * 承認経路一覧画面用に使用予定。
			} else if (mospParams.getCommand().equals(CMD_SPECIAL_DISPLAY)) {
				// 特殊表示
				prepareVo();
				selectShow();
			*/
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
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
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
		ApprovalRouteRegistBeanInterface regist = platform.approvalRouteRegist();
		// DTOの準備
		ApprovalRouteDtoInterface dto = new PfmApprovalRouteDto();
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
		// 登録クラス取得
		ApprovalRouteUnitRegistBeanInterface unitRegist = platform.approvalRouteUnitRegist();
		// DTOの準備
		ApprovalRouteUnitDtoInterface unitDto = new PfaApprovalRouteUnitDto();
		// 承認階層
		int routeStage = Integer.valueOf(dto.getApprovalCount());
		// 承認階層数分、承認ルートユニットマスタの登録を行う。
		for (int i = 1; i <= routeStage; i++) {
			// DTOに値を設定
			setUnitDtoFields(unitDto, i);
			// 新規登録処理
			unitRegist.insert(unitDto);
			// 新規登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageNewInsertSucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getRouteCode(), dto.getActivateDate());
		
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		ApprovalRouteRegistBeanInterface regist = platform.approvalRouteRegist();
		// DTOの準備
		ApprovalRouteDtoInterface dto = new PfmApprovalRouteDto();
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
		// 登録クラス取得
		ApprovalRouteUnitRegistBeanInterface unitRegist = platform.approvalRouteUnitRegist();
		// DTOの準備
		ApprovalRouteUnitDtoInterface unitDto = new PfaApprovalRouteUnitDto();
		// 承認階層
		int routeStage = Integer.valueOf(dto.getApprovalCount());
		// 承認階層数分、承認ルートユニットマスタの登録を行う。
		for (int i = 1; i <= routeStage; i++) {
			// DTOに値を設定
			setUnitDtoFields(unitDto, i);
			// 新規登録処理
			unitRegist.add(unitDto);
			// 新規登録結果確認
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
		}
		// コミット
		commit();
		// 履歴追加成功メッセージを設定
		PfMessageUtility.addMessageAddHistorySucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getRouteCode(), dto.getActivateDate());
		
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		ApprovalRouteRegistBeanInterface regist = platform.approvalRouteRegist();
		// DTOの準備
		ApprovalRouteDtoInterface dto = new PfmApprovalRouteDto();
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
		// 登録クラス取得
		ApprovalRouteUnitRegistBeanInterface unitRegist = platform.approvalRouteUnitRegist();
		// DTOの準備
		ApprovalRouteUnitDtoInterface unitDto = new PfaApprovalRouteUnitDto();
		// 承認階層
		int routeStage = Integer.valueOf(dto.getApprovalCount());
		// 承認階層数分、承認ルートユニットマスタの登録を行う。
		for (int i = 1; i <= routeStage; i++) {
			// DTOに値を設定
			setUnitDtoFields(unitDto, i);
			// 更新処理
			unitRegist.update(unitDto);
			// 更新結果確認
			if (mospParams.hasErrorMessage()) {
				// 更新失敗メッセージを設定
				PfMessageUtility.addMessageUpdateFailed(mospParams);
				return;
			}
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 履歴編集モード設定
		setEditUpdateMode(dto.getRouteCode(), dto.getActivateDate());
		
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException  インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// 登録クラス取得
		PlatformBeanHandlerInterface platform = platform();
		ApprovalRouteRegistBeanInterface regist = platform.approvalRouteRegist();
		// DTOの準備
		ApprovalRouteDtoInterface dto = new PfmApprovalRouteDto();
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
		// 登録クラス取得
		ApprovalRouteUnitRegistBeanInterface unitRegist = platform.approvalRouteUnitRegist();
		// DTOの準備
		ApprovalRouteUnitDtoInterface unitDto = new PfaApprovalRouteUnitDto();
		// 承認階層数
		int routeStage = Integer.valueOf(dto.getApprovalCount());
		// 承認階層数分、承認ルートユニットマスタの登録を行う。
		for (int i = 1; i <= routeStage; i++) {
			// DTOに値を設定
			setUnitDtoFields(unitDto, i);
			// 削除処理
			unitRegist.delete(unitDto);
			// 削除結果確認
			if (mospParams.hasErrorMessage()) {
				// 履歴削除失敗メッセージを設定
				PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
				return;
			}
		}
		// コミット
		commit();
		// 履歴削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteHistory(mospParams, 1);
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
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
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
			// 有効なユニットが登録されていない。
			if (vo.getAryPltUnitStage().length == 1 && vo.getAryPltUnitStage()[0][0].isEmpty()) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorValidDataNotExist(mospParams, PfNameUtility.unit(mospParams));
				// 有効日モードは決定状態にしない。
				vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			}
		}
		// 承認者氏名欄クリア
		clearApproverStages();
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
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
		
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// ルートコード
		vo.setTxtRouteCode("");
		// ルート名称
		vo.setTxtRouteName("");
		// 階層数
		vo.setPltRouteStage("1");
		// ルート設定欄
		// ユニットプルダウン(1次～10次)
		vo.setPltUnitStage1("");
		vo.setPltUnitStage2("");
		vo.setPltUnitStage3("");
		vo.setPltUnitStage4("");
		vo.setPltUnitStage5("");
		vo.setPltUnitStage6("");
		vo.setPltUnitStage7("");
		vo.setPltUnitStage8("");
		vo.setPltUnitStage9("");
		vo.setPltUnitStage10("");
		// 承認者氏名欄クリア
		clearApproverStages();
		
	}
	
	/**
	 * 承認者氏名欄をクリアする。<br>
	 */
	protected void clearApproverStages() {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// 承認者氏名欄(1次～10次)
		vo.setLblApproverStage1("");
		vo.setLblApproverStage2("");
		vo.setLblApproverStage3("");
		vo.setLblApproverStage4("");
		vo.setLblApproverStage5("");
		vo.setLblApproverStage6("");
		vo.setLblApproverStage7("");
		vo.setLblApproverStage8("");
		vo.setLblApproverStage9("");
		vo.setLblApproverStage10("");
		
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void addMode() throws MospException {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
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
	 * 履歴編集モードを設定する。<br>
	 * ユニットコードと有効日で編集対象情報を取得する。<br>
	 * @param routeCode      ルートコード
	 * @param activateDate   有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void setEditUpdateMode(String routeCode, Date activateDate) throws MospException {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// 参照クラス取得
		ReferenceBeanHandlerInterface platform = reference();
		ApprovalRouteReferenceBeanInterface reference = platform.approvalRoute();
		// 履歴編集対象取得
		ApprovalRouteDtoInterface dto = reference.getApprovalRouteInfo(routeCode, activateDate);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// ルートユニットマスタ参照クラス取得
		ApprovalRouteUnitReferenceBeanInterface unitReference = platform.approvalRouteUnit();
		// 承認階層数
		int routeStage = Integer.valueOf(dto.getApprovalCount());
		// 承認階層数分、承認ルートユニットマスタの登録を行う。
		for (int i = 1; i <= routeStage; i++) {
			// ルートユニット情報取得
			ApprovalRouteUnitDtoInterface unitDto = unitReference.getApprovalRouteUnitInfo(routeCode, activateDate, i);
			// 存在確認
			checkSelectedDataExist(unitDto);
			// VOにセット
			setUnitVoFields(unitDto, i);
			
		}
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 編集モード(履歴編集)設定
		setEditUpdateMode(reference.getApprovalRouteHistory(routeCode));
		
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(ApprovalRouteDtoInterface dto) {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// 有効日
		dto.setActivateDate(getEditActivateDate());
		// ルートコード
		dto.setRouteCode(vo.getTxtRouteCode());
		// ルート名称
		dto.setRouteName(vo.getTxtRouteName());
		// 階層数
		dto.setApprovalCount(getInt(vo.getPltRouteStage()));
		// 無効フラグ
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		
	}
	
	/**
	 * VO(編集項目)の値を承認ルートユニットマスタDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param routeStage 承認段階
	 */
	protected void setUnitDtoFields(ApprovalRouteUnitDtoInterface dto, int routeStage) {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// 有効日
		dto.setActivateDate(getEditActivateDate());
		// ルートコード
		dto.setRouteCode(vo.getTxtRouteCode());
		// 承認段階
		dto.setApprovalStage(routeStage);
		// ユニットコード
		switch (routeStage) {
			case 1:
				// 1次承認
				dto.setUnitCode(vo.getPltUnitStage1());
				break;
			case 2:
				// 2次承認
				dto.setUnitCode(vo.getPltUnitStage2());
				break;
			case 3:
				// 3次承認
				dto.setUnitCode(vo.getPltUnitStage3());
				break;
			case 4:
				// 4次承認
				dto.setUnitCode(vo.getPltUnitStage4());
				break;
			case 5:
				// 5次承認
				dto.setUnitCode(vo.getPltUnitStage5());
				break;
			case 6:
				// 6次承認
				dto.setUnitCode(vo.getPltUnitStage6());
				break;
			case 7:
				// 7次承認
				dto.setUnitCode(vo.getPltUnitStage7());
				break;
			case 8:
				// 8次承認
				dto.setUnitCode(vo.getPltUnitStage8());
				break;
			case 9:
				// 9次承認
				dto.setUnitCode(vo.getPltUnitStage9());
				break;
			case 10:
				// 10次承認
				dto.setUnitCode(vo.getPltUnitStage10());
				break;
			default:
				break;
		}
		// 無効フラグ
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
		
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(ApprovalRouteDtoInterface dto) {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setTxtEditActivateYear(getStringYear(dto.getActivateDate()));
		vo.setTxtEditActivateMonth(getStringMonth(dto.getActivateDate()));
		vo.setTxtEditActivateDay(getStringDay(dto.getActivateDate()));
		vo.setTxtRouteCode(dto.getRouteCode());
		vo.setTxtRouteName(dto.getRouteName());
		vo.setPltRouteStage(String.valueOf(dto.getApprovalCount()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
		
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param unitDto 対象DTO
	 * @param routeStage 承認段階
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setUnitVoFields(ApprovalRouteUnitDtoInterface unitDto, int routeStage) throws MospException {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		switch (routeStage) {
			case 1:
				// 1次承認
				vo.setPltUnitStage1(unitDto.getUnitCode());
				vo.setLblApproverStage1(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 2:
				// 2次承認
				vo.setPltUnitStage2(unitDto.getUnitCode());
				vo.setLblApproverStage2(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 3:
				// 3次承認
				vo.setPltUnitStage3(unitDto.getUnitCode());
				vo.setLblApproverStage3(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 4:
				// 4次承認
				vo.setPltUnitStage4(unitDto.getUnitCode());
				vo.setLblApproverStage4(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 5:
				// 5次承認
				vo.setPltUnitStage5(unitDto.getUnitCode());
				vo.setLblApproverStage5(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 6:
				// 6次承認
				vo.setPltUnitStage6(unitDto.getUnitCode());
				vo.setLblApproverStage6(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 7:
				// 7次承認
				vo.setPltUnitStage7(unitDto.getUnitCode());
				vo.setLblApproverStage7(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 8:
				// 8次承認
				vo.setPltUnitStage8(unitDto.getUnitCode());
				vo.setLblApproverStage8(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 9:
				// 9次承認
				vo.setPltUnitStage9(unitDto.getUnitCode());
				vo.setLblApproverStage9(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			case 10:
				// 10次承認
				vo.setPltUnitStage10(unitDto.getUnitCode());
				vo.setLblApproverStage10(getUnitApproverName(unitDto.getUnitCode(), unitDto.getActivateDate()));
				break;
			default:
				break;
		}
		
	}
	
	/**
	 * プルダウン設定
	 * @throws MospException 承認ユニット情報の取得に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		RouteCardVo vo = (RouteCardVo)mospParams.getVo();
		// プルダウンの設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltUnitStage(getInputActivateDatePulldown());
		} else {
			Date date = getEditActivateDate();
			// 承認ユニット
			String[][] arySection = reference().approvalUnit().getCodedSelectArray(date, false);
			vo.setAryPltUnitStage(arySection);
		}
	}
	
	/**
	 * 指定ユニットの承認者名を取得する。
	 * @param unitCode ユニットコード
	 * @param date 有効日
	 * @return 指定ユニットの承認者
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private String getUnitApproverName(String unitCode, Date date) throws MospException {
		String approverName = "";
		if (unitCode.equals("")) {
			return approverName;
		}
		// ユニットマスタ
		ApprovalUnitReferenceBeanInterface getUnitInfo = reference().approvalUnit();
		// 人事マスタ
		HumanReferenceBeanInterface getHumanInfo = reference().human();
		// 所属マスタ
		SectionReferenceBeanInterface getSectionInfo = reference().section();
		// 職位マスタ
		PositionReferenceBeanInterface getPositionInfo = reference().position();
		// 承認ユニット情報を取得
		ApprovalUnitDtoInterface unitDto = getUnitInfo.getApprovalUnitInfo(unitCode, date);
		if (unitDto.getUnitType().equals(PlatformConst.UNIT_TYPE_SECTION)) {
			// 所属指定
			// 所属略称取得
			String sectionAbbr = getSectionInfo.getSectionAbbr(unitDto.getApproverSectionCode(), date);
			// 職位略称取得
			String positionAbbr = getPositionInfo.getPositionAbbr(unitDto.getApproverPositionCode(), date);
			// 承認者を設定
			approverName = sectionAbbr + " " + positionAbbr;
		} else {
			// 個人指定
			approverName = getHumanInfo.getHumanNames(unitDto.getApproverPersonalId(), date);
		}
		return approverName;
	}
	
}
