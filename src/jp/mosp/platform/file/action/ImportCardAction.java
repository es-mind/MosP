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
package jp.mosp.platform.file.action;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.bean.file.ImportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportFieldRegistBeanInterface;
import jp.mosp.platform.bean.file.ImportReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.message.MessageDtoInterface;
import jp.mosp.platform.file.vo.ImportCardVo;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * インポートに用いるマスタの詳細情報の確認、編集を行う。<br>
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
 * {@link #CMD_SET_TABLE_TYPE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li></ul>
 */
public class ImportCardAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "PF1320";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * マスタ一覧画面で選択したレコードの情報を取得し、詳細画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "PF1321";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 基本情報テーブル、項目選択テーブルに入力されている内容をインポートマスタテーブルに登録する。<br>
	 * データ区分が決定されていない、必須項目が入力されていない、インポートコードが
	 * 登録済みのレコードのものと重複しているといった場合はエラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_REGIST				= "PF1325";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 削除前に確認メッセージを表示する。その後、現在表示しているインポートマスタの情報を論理削除する。<br>
	 */
	public static final String	CMD_DELETE				= "PF1327";
	
	/**
	 * データ区分決定コマンド。<br>
	 * <br>
	 * インポート対象となる項目についてデータ区分プルダウンで選択した情報で検索を行う。<br>
	 * それらの情報から候補となる項目のプルダウンリストを作成し、項目選択のプルダウンにセットする。<br>
	 * データ区分決定後、データ区分は選択不可になる。<br>
	 */
	public static final String	CMD_SET_TABLE_TYPE		= "PF1370";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 基本情報テーブル、選択項目テーブルの内容ををクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "PF1371";
	
	/**
	 * 人事汎用管理表示区分(インポート)。<br>
	 */
	public static final String	KEY_VIEW_HUMAN_IMPORT	= "HumanImport";
	
	
	/**
	 * {@link PlatformSystemAction#PlatformSystemAction()}を実行する。<br>
	 */
	public ImportCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ImportCardVo();
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
			select();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_SET_TABLE_TYPE)) {
			// データ区分決定
			prepareVo();
			setTableType();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替コマンド
			prepareVo();
			insertMode();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void show() throws MospException {
		// インポート詳細JSP用コマンド及びデータ区分コードキー設定
		setImportCardInfo();
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 選択表示処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// インポート詳細JSP用コマンド及びデータ区分コードキー設定
		setImportCardInfo();
		// 編集モード設定
		editMode();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
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
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// 登録クラス取得
		ImportRegistBeanInterface regist = platform().importRegist();
		ImportFieldRegistBeanInterface registField = platform().importFieldRegist();
		// テーブル内容選択欄から必要なデータを取得する
		String[] arySelectSelected = vo.getJsPltSelectSelected();
		// DTOの準備
		ImportDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 登録処理
		regist.insert(dto);
		registField.insert(vo.getTxtEditCode(), vo.getPltEditInactivate(), arySelectSelected);
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
		setEditUpdateMode(dto.getImportCode());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// 登録クラス取得
		ImportRegistBeanInterface regist = platform().importRegist();
		ImportFieldRegistBeanInterface registField = platform().importFieldRegist();
		// DTOの準備
		ImportDtoInterface dto = regist.getInitDto();
		setDtoFields(dto);
		// テーブル内容選択欄から必要なデータを取得する
		String[] arySelectSelected = vo.getJsPltSelectSelected();
		// 更新処理
		regist.update(dto);
		registField.update(vo.getTxtEditCode(), vo.getPltEditInactivate(), arySelectSelected);
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
		setEditUpdateMode(dto.getImportCode());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO準備
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// 削除処理
		ImportDtoInterface dto = reference().importRefer().findForKey(vo.getTxtEditCode());
		platform().importRegist().delete(dto);
		platform().importFieldRegist().delete(vo.getTxtEditCode());
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
	 * データ区分決定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * 決定時に表示される項目の設定を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setTableType() throws MospException {
		// VO準備
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン設定
		setPulldown();
		// 必須項目をあらかじめ表示させる
		/*
		// 人事情報の場合
		if (vo.getPltEditTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_HUMAN)) {
			// 必須項目取得(社員コード・有効日・姓・名)
			String[] arySelected = { PlatformFileConst.FIELD_EMPLOYEE_CODE, PlatformFileConst.FIELD_ACTIVATE_DATE,
			PlatformFileConst.FIELD_FIRST_NAME, PlatformFileConst.FIELD_LAST_NAME };
			// 必須項目を初期表示に設定
			vo.setJsPltSelectSelected(arySelected);
		}
		*/
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insertMode() throws MospException {
		// 新規登録モード設定
		setEditInsertMode();
		// 初期値設定
		setDefaultValues();
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
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
	 * インポートコードで編集対象情報を取得する。<br>
	 * @param importCode インポートコード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String importCode) throws MospException {
		// VO準備
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// 参照クラス取得
		ImportReferenceBeanInterface reference = reference().importRefer();
		ImportFieldReferenceBeanInterface fieldReference = reference().importField();
		// 履歴編集対象取得
		ImportDtoInterface dto = reference.findForKey(importCode);
		List<ImportFieldDtoInterface> list = fieldReference.getImportFieldList(importCode);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		setVoFields(list);
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		// 編集モード(履歴編集)設定(次前履歴情報設定不能)
		setEditUpdateMode(new ArrayList<MessageDtoInterface>());
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// 検索項目設定
		vo.setTxtEditCode("");
		vo.setTxtEditName("");
		vo.setPltEditTable("");
		vo.setPltEditHeader("");
		vo.setPltEditType(PlatformFileConst.FILE_TYPE_CSV);
		vo.setJsPltSelectSelected(new String[0]);
	}
	
	/**
	 * プルダウンを設定する。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// 初期化
		vo.setJsPltSelectTable(new String[0][0]);
		vo.setLblTableName("");
		// データ区分プルダウン取得
		vo.setAryPltTableType(reference().tableType().getTableTypeArray(vo.getTableTypeCodeKey(), false));
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// プルダウン設定(データ区分を条件にコード配列を取得)
			vo.setJsPltSelectTable(getCodeArray(vo.getPltEditTable(), false));
			vo.setLblTableName(MospUtility.getCodeName(vo.getPltEditTable(), vo.getAryPltTableType()));
		}
		// 人事情報区分の場合
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)
				&& vo.getPltEditTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_HUMAN)) {
			// 画面区分配列取得
			String[] aryDivision = mospParams.getApplicationProperties(PlatformConst.APP_HUMAN_GENERAL_DIVISIONS);
			// 画面区分毎に処理
			for (String division : aryDivision) {
				// ロールで非表示設定された人事汎用管理区分が存在した場合は表示対象としない
				if (RoleUtility.getHiddenDivisionsList(mospParams).contains(division)) {
					continue;
				}
				// 参照権限の場合、表示対象としない
				if (RoleUtility.getReferenceDivisionsList(mospParams).contains(division)) {
					continue;
				}
				// 項目配列取得
				String[][] aryAddItem = reference().humanNormal().getPulldownForHumanExportImport(division,
						KEY_VIEW_HUMAN_IMPORT);
				// 取得できない場合
				if (aryAddItem == null || aryAddItem.length == 0) {
					continue;
				}
				// プルダウン追加設定
				vo.setJsPltSelectTable(addArrayString(vo.getJsPltSelectTable(), aryAddItem));
			}
		}
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setDtoFields(ImportDtoInterface dto) {
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setPfmImportId(vo.getRecordId());
		dto.setImportTable(vo.getPltEditTable());
		dto.setImportCode(vo.getTxtEditCode());
		dto.setImportName(vo.getTxtEditName());
		dto.setType(vo.getPltEditType());
		dto.setHeader(getInt(vo.getPltEditHeader()));
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(ImportDtoInterface dto) {
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getPfmImportId());
		vo.setPltEditTable(dto.getImportTable());
		vo.setTxtEditCode(dto.getImportCode());
		vo.setTxtEditName(dto.getImportName());
		vo.setPltEditType(dto.getType());
		vo.setPltEditHeader(String.valueOf(dto.getHeader()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list 対象DTOリスト
	 */
	protected void setVoFields(List<ImportFieldDtoInterface> list) {
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// 設定配列準備
		String[] jsPltSelectSelected = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ImportFieldDtoInterface dto = list.get(i);
			jsPltSelectSelected[i] = dto.getFieldName();
		}
		// DTOの値をVOに設定
		vo.setJsPltSelectSelected(jsPltSelectSelected);
	}
	
	/**
	 * インポート詳細JSP用コマンド及びデータ区分コードキーをVOに設定する。<br>
	 * 設定値は、譲渡汎用区分及び譲渡汎用コマンドから受け取る。<br>
	 */
	protected void setImportCardInfo() {
		// 譲渡汎用区分取得
		String tableTypeCodeKey = getTransferredType();
		// 譲渡汎用コマンド取得
		String showListCommand = getTransferredCommand();
		// データ区分コードキー確認
		if (tableTypeCodeKey == null || tableTypeCodeKey.isEmpty()) {
			return;
		}
		// VO取得
		ImportCardVo vo = (ImportCardVo)mospParams.getVo();
		// データ区分コードキー設定
		vo.setTableTypeCodeKey(tableTypeCodeKey);
		// 一覧表示設定
		vo.setShowListCommand(showListCommand);
	}
	
}
