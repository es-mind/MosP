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
package jp.mosp.platform.file.base;

import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.file.ExportSearchBeanInterface;
import jp.mosp.platform.comparator.file.ExportMasterExportCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * エクスポート一覧におけるActionの基本機能を提供する。<br>
 */
public abstract class ExportListAction extends PlatformAction {
	
	/**
	 * ファイルパス(エクスポート一覧共通JSP)。
	 */
	public static final String	PATH_EXPORT_LIST_JSP	= "/jsp/platform/file/exportList.jsp";
	
	/**
	 * ファイルパス(エクスポート一覧共通JavaScript)。
	 */
	public static final String	PATH_EXPORT_LIST_JS		= "/pub/platform/file/js/exportList.js";
	
	/**
	 * ファイルパス(エクスポート一覧共通CSS)。
	 */
	public static final String	PATH_EXPORT_LIST_CSS	= "/pub/platform/file/css/exportList.css";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public ExportListAction() {
		super();
	}
	
	@Override
	protected void addBaseJsCssFiles() {
		super.addBaseJsCssFiles();
		// エクスポート一覧用ファイル追加
		mospParams.addJsFile(PATH_EXPORT_LIST_JS);
		mospParams.addCssFile(PATH_EXPORT_LIST_CSS);
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		ExportListVo vo = (ExportListVo)mospParams.getVo();
		// 検索クラス取得
		ExportSearchBeanInterface search = reference().exportSearch();
		// VOの値を検索クラスへ設定
		search.setCode(vo.getTxtSearchCode());
		search.setName(vo.getTxtSearchName());
		search.setTable(vo.getPltSearchTable());
		search.setType(vo.getPltSearchType());
		search.setHeader(vo.getPltSearchHeader());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		search.setTableTypeArray(reference().tableType().getTableTypeArray(vo.getTableTypeCodeKey(), false));
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<ExportDtoInterface> list = search.getSearchList();
		setDtoToVoList(list);
		
	}
	
	/**
	 * リスト情報取得後の処理
	 * @param list インポート情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDtoToVoList(List<ExportDtoInterface> list) throws MospException {
		// VO準備
		ExportListVo vo = (ExportListVo)mospParams.getVo();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ExportMasterExportCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 一覧選択情報初期化
		vo.setRadSelect("");
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
	 */
	protected void page() {
		setVoList(pageList());
	}
	
	/**
	 * データ区分設定処理を行う。<br>
	 * 選択されたデータ区分を確認し、出力期間/有効日の有効/無効を設定する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setExport() throws MospException {
		// VO取得
		ExportListVo vo = (ExportListVo)mospParams.getVo();
		// 選択値を設定
		vo.setRadSelect(getTransferredCode());
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		ExportListVo vo = (ExportListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblCode = new String[list.size()];
		String[] aryLblName = new String[list.size()];
		String[] aryLblTable = new String[list.size()];
		String[] aryLblType = new String[list.size()];
		String[] aryLblHeader = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			ExportDtoInterface dto = (ExportDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblCode[i] = dto.getExportCode();
			aryLblName[i] = dto.getExportName();
			aryLblTable[i] = MospUtility.getCodeName(dto.getExportTable(), vo.getAryPltTableType());
			aryLblType[i] = getCodeName(dto.getType(), PlatformConst.CODE_KEY_FILE_TYPE);
			aryLblHeader[i] = getCodeName(dto.getHeader(), PlatformConst.CODE_KEY_HEADER_TYPE);
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryLblCode(aryLblCode);
		vo.setAryLblName(aryLblName);
		vo.setAryLblTable(aryLblTable);
		vo.setAryLblType(aryLblType);
		vo.setAryLblHeader(aryLblHeader);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
	/**
	 * {@link ExportListVo}のフィールドへ初期値を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void initExportListVoFields() throws MospException {
		// VO取得
		ExportListVo vo = (ExportListVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtSearchCode("");
		vo.setTxtSearchName("");
		vo.setPltSearchTable("");
		vo.setPltSearchHeader("");
		vo.setRadSelect("");
		vo.setPltSearchType(PlatformFileConst.FILE_TYPE_CSV);
		vo.setPltSearchInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		// データ区分設定
		vo.setAryPltTableType(reference().tableType().getTableTypeArray(vo.getTableTypeCodeKey(), true));
		// ソートキー設定
		vo.setComparatorName(ExportMasterExportCodeComparator.class.getName());
	}
	
}
