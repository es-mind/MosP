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
import jp.mosp.platform.bean.file.ExportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ExportFieldRegistBeanInterface;
import jp.mosp.platform.bean.file.ExportReferenceBeanInterface;
import jp.mosp.platform.bean.file.ExportRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.message.MessageDtoInterface;
import jp.mosp.platform.file.vo.ExportCardVo;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * エクスポートに用いるマスタの詳細情報の確認、編集を行う。<br>
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
public class ExportCardAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "PF1420";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * マスタ一覧画面で選択したレコードの情報を取得し、詳細画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "PF1421";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 基本情報テーブル、項目選択テーブルに入力されている内容をエクスポートマスタテーブルに登録する。<br>
	 * データ区分が決定されていない、必須項目が入力されていない、エクスポートコードが
	 * 登録済みのレコードのものと重複しているといった場合はエラーメッセージにて通知する。<br>
	 */
	public static final String	CMD_REGIST				= "PF1425";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 削除前に確認メッセージを表示する。<br>
	 * その後、現在表示しているエクスポートマスタの情報を論理削除する。<br>
	 */
	public static final String	CMD_DELETE				= "PF1427";
	
	/**
	 * データ区分決定コマンド。<br>
	 * <br>
	 * エクスポート対象となる項目についてデータ区分プルダウンで選択した情報で検索を行う。<br>
	 * それらの情報から候補となる項目のプルダウンリストを作成し、項目選択のプルダウンにセットする。<br>
	 * データ区分決定後、データ区分は選択不可になる。<br>
	 */
	public static final String	CMD_SET_TABLE_TYPE		= "PF1470";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 基本情報テーブル、選択項目テーブルの内容ををクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "PF1471";
	
	/**
	 * 人事汎用管理表示区分(エクスポート)。<br>
	 */
	public static final String	KEY_VIEW_HUMAN_EXPORT	= "HumanExport";
	
	
	/**
	 * {@link PlatformSystemAction#PlatformSystemAction()}を実行する。<br>
	 */
	public ExportCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ExportCardVo();
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
		// エクスポート詳細JSP用コマンド及びデータ区分コードキー設定
		setExportCardInfo();
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 選択表示処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// エクスポート詳細JSP用コマンド及びデータ区分コードキーを設定
		setExportCardInfo();
		// 編集モード設定
		editMode();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
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
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		// 登録クラス取得
		ExportRegistBeanInterface regist = platform().exportRegist();
		ExportFieldRegistBeanInterface registField = platform().exportFieldRegist();
		// テーブル内容選択欄から必要なデータを取得する
		String[] arySelectSelected = vo.getJsPltSelectSelected();
		// DTOの準備
		ExportDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getExportCode());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		// 登録クラス取得
		ExportRegistBeanInterface regist = platform().exportRegist();
		ExportFieldRegistBeanInterface registField = platform().exportFieldRegist();
		// DTOの準備
		ExportDtoInterface dto = regist.getInitDto();
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
		setEditUpdateMode(dto.getExportCode());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO準備
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		// 削除処理
		ExportDtoInterface dto = reference().export().findForKey(vo.getTxtEditCode());
		platform().exportRegist().delete(dto);
		platform().exportFieldRegist().delete(vo.getTxtEditCode());
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
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
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
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
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
	 * エクスポートコードで編集対象情報を取得する。<br>
	 * @param exportCode エクスポートコード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String exportCode) throws MospException {
		// VO準備
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		// 参照クラス取得
		ExportReferenceBeanInterface reference = reference().export();
		ExportFieldReferenceBeanInterface fieldReference = reference().exportField();
		// 履歴編集対象取得
		ExportDtoInterface dto = reference.findForKey(exportCode);
		List<ExportFieldDtoInterface> list = fieldReference.getExportFieldList(exportCode);
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
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
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
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
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
				
				// 項目配列取得
				String[][] aryAddItem = reference().humanNormal().getPulldownForHumanExportImport(division,
						KEY_VIEW_HUMAN_EXPORT);
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
	protected void setDtoFields(ExportDtoInterface dto) {
		// VO取得
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setPfmExportId(vo.getRecordId());
		dto.setExportTable(vo.getPltEditTable());
		dto.setExportCode(vo.getTxtEditCode());
		dto.setExportName(vo.getTxtEditName());
		dto.setType(vo.getPltEditType());
		dto.setHeader(getInt(vo.getPltEditHeader()));
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(ExportDtoInterface dto) {
		// VO取得
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getPfmExportId());
		vo.setPltEditTable(dto.getExportTable());
		vo.setTxtEditCode(dto.getExportCode());
		vo.setTxtEditName(dto.getExportName());
		vo.setPltEditType(dto.getType());
		vo.setPltEditHeader(String.valueOf(dto.getHeader()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list 対象DTOリスト
	 */
	protected void setVoFields(List<ExportFieldDtoInterface> list) {
		// VO取得
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		// 設定配列準備
		String[] jsPltSelectSelected = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ExportFieldDtoInterface dto = list.get(i);
			jsPltSelectSelected[i] = dto.getFieldName();
		}
		// DTOの値をVOに設定
		vo.setJsPltSelectSelected(jsPltSelectSelected);
	}
	
	/**
	 * エクスポート詳細JSP用コマンド及びデータ区分コードキーをVOに設定する。<br>
	 * 設定値は、譲渡汎用区分及び譲渡汎用コマンドから受け取る。<br>
	 */
	protected void setExportCardInfo() {
		// 譲渡汎用区分取得
		String tableTypeCodeKey = getTransferredType();
		// 譲渡汎用コマンド取得
		String showListCommand = getTransferredCommand();
		// データ区分コードキー確認
		if (tableTypeCodeKey == null || tableTypeCodeKey.isEmpty()) {
			return;
		}
		// VO取得
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		// データ区分コードキー設定
		vo.setTableTypeCodeKey(tableTypeCodeKey);
		// 一覧表示設定
		vo.setShowListCommand(showListCommand);
	}
	
}
