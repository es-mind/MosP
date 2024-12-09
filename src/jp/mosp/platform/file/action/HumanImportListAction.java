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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.file.base.ImportListAction;
import jp.mosp.platform.file.vo.ImportListVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * インポートの実行。インポートマスタの管理を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_EXECUTION}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_TEMP_OUTPUT}
 * </li></ul>
 */
public class HumanImportListAction extends ImportListAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW		= "PF1310";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿ったインポートマスタ情報の一覧表示を行う。<br>
	 * 一覧表示の際にはインポートコードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH		= "PF1312";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている情報を
	 * 最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW		= "PF1313";
	
	/**
	 * 実行コマンド。<br>
	 * <br>
	 * インポートを実行する。<br>
	 * 実行時にインポートマスタが決定していない、対象ファイルが選択されて
	 * いない場合はエラーメッセージにて通知し、処理は実行されない。<br>
	 */
	public static final String	CMD_EXECUTION	= "PF1315";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT		= "PF1318";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE		= "PF1319";
	
	/**
	 * テンプレート出力コマンド。<br>
	 * <br>
	 * ラジオボタンで選択されているインポートマスタの情報を取得し、
	 * マスタ内の項目を用いて参照ファイルのテンプレートを表計算ファイルで出力する。<br>
	 */
	public static final String	CMD_TEMP_OUTPUT	= "PF1386";
	
	
	/**
	 * {@link ImportListAction#ImportListAction()}を実行する。<br>
	 */
	public HumanImportListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
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
		} else if (mospParams.getCommand().equals(CMD_EXECUTION)) {
			// 実行
			prepareVo();
			execution();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_TEMP_OUTPUT)) {
			// テンプレート出力
			prepareVo();
			tempOutput();
		}
	}
	
	/**
	 * インポート一覧JSP用コマンド及びデータ区分コードキーをVOに設定する。<br>
	 */
	protected void setImportListInfo() {
		// VO取得
		ImportListVo vo = (ImportListVo)mospParams.getVo();
		// データ区分コードキー設定(人事情報データ区分)
		vo.setTableTypeCodeKey(PlatformFileConst.CODE_KEY_HUMAN_IMPORT_TABLE_TYPE);
		// 再表示コマンド設定
		vo.setReShowCommand(CMD_RE_SHOW);
		// 検索コマンド設定
		vo.setSearchCommand(CMD_SEARCH);
		// 並び替えコマンド設定
		vo.setSortCommand(CMD_SORT);
		// 実行コマンド設定
		vo.setExecuteCommand(CMD_EXECUTION);
		// テンプレート出力コマンド設定
		vo.setTemplateOutputCommand(CMD_TEMP_OUTPUT);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// インポート一覧共通JSP用コマンド及びデータ区分をVOに設定
		setImportListInfo();
		// インポート一覧共通VO初期値設定
		initImportListVoFields();
	}
	
	@Override
	protected void search() throws MospException {
		// VO取得
		ImportListVo vo = (ImportListVo)mospParams.getVo();
		
		// 継承元実行
		super.search();
		
		// 対象が存在しない場合
		if (vo.getList().isEmpty()) {
			return;
		}
		List<String> ary = new ArrayList<String>();
		ary.addAll(RoleUtility.getHiddenDivisionsList(mospParams));
		ary.addAll(RoleUtility.getReferenceDivisionsList(mospParams));
		
		// 人事汎用管理区分配列（非表示/参照となる管理区分）
		String[] aryDivision = MospUtility.toArray(ary);
		
		// ロール制御がないユーザーの場合
		if (aryDivision.length == 0) {
			return;
			
		}
		
		// 再設定用リスト
		List<ImportDtoInterface> list = new ArrayList<ImportDtoInterface>();
		
		// 人事汎用管理区分にて参照権限妥当性チェック
		for (int idx = 0; idx < vo.getList().size(); idx++) {
			// エクスポート情報DTO取得
			ImportDtoInterface dto = (ImportDtoInterface)vo.getList().get(idx);
			
			// データ区分:人事情報以外はチェック対象外
			if (!dto.getImportTable().contains(PlatformFileConst.CODE_KEY_TABLE_TYPE_HUMAN)) {
				list.add(dto);
				continue;
			}
			
			// 対象エクスポートコード内に非表示対象の人事管理汎用区分が存在するか確認
			if (reference().humanImport().isExistLikeFieldName(dto.getImportCode(), aryDivision)) {
				// 存在する場合、リスト設定をしない
				continue;
			}
			
			// リストに設定
			list.add(dto);
		}
		// 再設定
		setDtoToVoList(list);
	}
	
	/**
	 * インポートの実行を行う。<br>
	 * @throws MospException インポートに失敗した場合
	 */
	protected void execution() throws MospException {
		// VO準備
		ImportListVo vo = (ImportListVo)mospParams.getVo();
		// インポートマスタ取得及び確認
		ImportDtoInterface importDto = reference().importRefer().findForKey(vo.getRadSelect());
		if (importDto == null) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// インポートクラス取得
		ImportBeanInterface importBean = getImportBean(importDto);
		// インポートクラス確認
		if (importBean == null) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// インポート実施
		int count = importBean.importFile(importDto, mospParams.getRequestFile(PRM_FIL_IMPORT));
		// 履歴追加結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// インポート成功メッセージを設定
		PfMessageUtility.addMessageImportSucceed(mospParams, count);
	}
	
	/**
	 * インポート管理情報を基にインポートクラスを取得する。
	 * @param importDto インポート管理情報
	 * @return インポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected ImportBeanInterface getImportBean(ImportDtoInterface importDto) throws MospException {
		// インポートクラス準備
		ImportBeanInterface importBean = null;
		// データ区分確認
		if (importDto.getImportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_HUMAN)) {
			// 人事マスタインポートクラス取得
			importBean = platform().humanImport();
		} else if (importDto.getImportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_USER)) {
			// ユーザマスタインポートクラス取得
			importBean = platform().userImport();
		} else if (importDto.getImportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_USER_EXTRA_ROLE)) {
			// ユーザ追加ロールインポート処理取得
			importBean = platform().userExtraRoleImport();
		} else if (importDto.getImportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_SECTION)) {
			// 所属マスタインポートクラス取得
			importBean = platform().sectionImport();
		} else if (importDto.getImportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_POSITION)) {
			// 職位マスタインポートクラス取得
			importBean = platform().positionImport();
		} else if (importDto.getImportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_PASSWORD)) {
			// ユーザパスワードテーブルインポートクラス取得
			importBean = platform().userPasswordImport();
		} else if (importDto.getImportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_UNIT_SECTION)) {
			// ユニット情報(所属)インポートクラス取得
			importBean = platform().unitSectionImport();
		} else if (importDto.getImportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_UNIT_PERSON)) {
			// ユニット情報(個人)インポートクラス取得
			importBean = platform().unitPersonImport();
		} else {
			// インポート処理を取得(その他)
			importBean = getImportBean(importDto.getImportTable());
		}
		return importBean;
	}
	
	/**
	 * インポート処理を取得する。<br>
	 * @param exportTable データ区分(インポート)
	 * @return インポート処理
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ImportBeanInterface getImportBean(String exportTable) throws MospException {
		// モデルキー(データ区分)からモデルクラス名を取得
		String modelClass = MospUtility.getModelClass(exportTable, mospParams.getProperties(), getSystemDate());
		// インポート処理を取得
		return (ImportBeanInterface)reference().createBean(modelClass);
	}
	
}
