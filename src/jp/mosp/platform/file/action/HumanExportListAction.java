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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.bean.file.ExportBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.file.base.ExportListAction;
import jp.mosp.platform.file.base.ExportListVo;
import jp.mosp.platform.file.vo.HumanExportListVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 人事エクスポートの実行。エクスポートマスタの管理を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_EXPORT}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_EXPORT}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li></ul>
 */
public class HumanExportListAction extends ExportListAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "PF1410";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿ったエクスポートマスタ情報の一覧表示を行う。<br>
	 * 一覧表示の際にはエクスポートコードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "PF1412";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている
	 * 情報を最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW				= "PF1413";
	
	/**
	 * エクスポートコマンド。<br>
	 * <br>
	 * 選択したエクスポートマスタを用いて入力した出力期間の範囲内でエクスポートを実行する。<br>
	 * 実行時にエクスポートマスタが選択されていない、または出力期間・有効日が決定していない場合は
	 * エラーメッセージにて通知し、処理を中止する。<br>
	 */
	public static final String	CMD_EXPORT				= "PF1415";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "PF1418";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "PF1419";
	
	/**
	 * データ区分決定コマンド。<br>
	 * <br>
	 * エクスポートマスタ一覧から選択したレコードのデータ区分の情報を取得し、
	 * 勤怠関連のテーブルであれば有効日入力欄とその決定ボタンを、<br>
	 * 人事関連のテーブルであれば出力期間入力欄とその決定ボタンをそれぞれ読取専用にする。<br>
	 */
	public static final String	CMD_SET_EXPORT			= "PF1480";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 入力されている有効日をチェックし、勤務地・雇用契約・所属・職位の各マスタから
	 * その有効日時点で有効なレコードを検索し、それぞれのプルダウンに表示する。<br>
	 * 有効日決定時は有効日入力欄が読取専用となる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF1482";
	
	
	/**
	 * {@link ExportListAction#ExportListAction()}を実行する。<br>
	 * また、データ区分コードキーを設定する。<br>
	 */
	public HumanExportListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HumanExportListVo();
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
		} else if (mospParams.getCommand().equals(CMD_EXPORT)) {
			// エクスポート
			prepareVo();
			export();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_EXPORT)) {
			// データ区分決定
			prepareVo();
			setExport();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		}
	}
	
	/**
	 * エクスポート一覧共通JSP用コマンド及びデータ区分コードキーをVOに設定する。<br>
	 */
	protected void setExportListInfo() {
		// VO(エクスポート一覧共通VO)取得
		ExportListVo vo = (ExportListVo)mospParams.getVo();
		// データ区分コードキー設定(人事情報データ区分)
		vo.setTableTypeCodeKey(PlatformFileConst.CODE_KEY_HUMAN_EXPORT_TABLE_TYPE);
		// 再表示コマンド設定
		vo.setReShowCommand(CMD_RE_SHOW);
		// 検索コマンド設定
		vo.setSearchCommand(CMD_SEARCH);
		// 並び替えコマンド設定
		vo.setSortCommand(CMD_SORT);
		// データ区分設定コマンド設定
		vo.setSetExportCommand(CMD_SET_EXPORT);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// エクスポート一覧共通JSP用コマンド及びデータ区分をVOに設定
		setExportListInfo();
		// エクスポート一覧共通VO初期値設定
		initExportListVoFields();
		// 出力条件を初期化
		initOutputCondition();
	}
	
	@Override
	protected void search() throws MospException {
		// VO取得
		ExportListVo vo = (ExportListVo)mospParams.getVo();
		
		// 継承元実行
		super.search();
		
		// 対象が存在しない場合
		if (vo.getList().isEmpty()) {
			return;
		}
		
		// 人事汎用管理区分配列（非表示となる管理区分）
		String[] aryDivision = MospUtility.toArray(RoleUtility.getHiddenDivisionsList(mospParams));
		
		// ロール制御がないユーザーの場合
		if (aryDivision.length == 0) {
			return;
			
		}
		
		// 再設定用リスト
		List<ExportDtoInterface> list = new ArrayList<ExportDtoInterface>();
		
		// 人事汎用管理区分にて参照権限妥当性チェック
		for (int idx = 0; idx < vo.getList().size(); idx++) {
			// エクスポート情報DTO取得
			ExportDtoInterface dto = (ExportDtoInterface)vo.getList().get(idx);
			
			// データ区分:人事情報以外はチェック対象外
			if (!dto.getExportTable().contains(PlatformFileConst.CODE_KEY_TABLE_TYPE_HUMAN)) {
				list.add(dto);
				continue;
			}
			
			// 対象エクスポートコード内に非表示対象の人事管理汎用区分が存在するか確認
			if (reference().humanExport().isExistLikeFieldName(dto.getExportCode(), aryDivision)) {
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
	 * 有効日設定処理を行う。<br>
	 * 保持有効日モードを確認し、プルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		HumanExportListVo vo = (HumanExportListVo)mospParams.getVo();
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
	 * プルダウンの設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		HumanExportListVo vo = (HumanExportListVo)mospParams.getVo();
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// プルダウン設定
			vo.setAryPltWorkPlace(getInputActivateDatePulldown());
			vo.setAryPltEmployment(getInputActivateDatePulldown());
			vo.setAryPltSection(getInputActivateDatePulldown());
			vo.setAryPltPosition(getInputActivateDatePulldown());
			return;
		}
		// プルダウン対象日取得
		Date targetDate = getPulldownTargetDate();
		// 勤務地設定
		vo.setAryPltWorkPlace(
				reference().workPlace().getCodedAbbrSelectArray(targetDate, true, MospConst.OPERATION_TYPE_REFER));
		// 雇用契約設定
		vo.setAryPltEmployment(reference().employmentContract().getCodedAbbrSelectArray(targetDate, true,
				MospConst.OPERATION_TYPE_REFER));
		// 所属設定
		vo.setAryPltSection(
				reference().section().getCodedSelectArray(targetDate, true, MospConst.OPERATION_TYPE_REFER));
		// 職位設定
		vo.setAryPltPosition(
				reference().position().getCodedSelectArray(targetDate, true, MospConst.OPERATION_TYPE_REFER));
	}
	
	/**
	 * プルダウン対象日を取得する。<br>
	 * @return プルダウン対象日
	 */
	protected Date getPulldownTargetDate() {
		// VO取得
		HumanExportListVo vo = (HumanExportListVo)mospParams.getVo();
		// プルダウン対象日取得
		return getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
	}
	
	/**
	 * 出力条件を初期化する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void initOutputCondition() throws MospException {
		// VO取得
		HumanExportListVo vo = (HumanExportListVo)mospParams.getVo();
		// 有効日設定
		Date systemDate = getSystemDate();
		vo.setTxtActivateYear(getStringYear(systemDate));
		vo.setTxtActivateMonth(getStringMonth(systemDate));
		vo.setTxtActivateDay(getStringDay(systemDate));
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * エクスポート処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void export() throws MospException {
		// VO準備
		HumanExportListVo vo = (HumanExportListVo)mospParams.getVo();
		// 対象日取得
		Date targetDate = getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
		// エクスポートマスタ取得及び確認
		ExportDtoInterface exportDto = reference().export().findForKey(vo.getRadSelect());
		if (exportDto == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// エクスポートクラス準備
		ExportBeanInterface exportBean;
		// データ区分確認
		if (exportDto.getExportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_HUMAN)) {
			// 人事マスタエクスポートクラス取得
			exportBean = reference().humanExport();
		} else if (exportDto.getExportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_USER)) {
			// ユーザマスタエクスポートクラス取得
			exportBean = reference().userExport();
		} else if (exportDto.getExportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_USER_EXTRA_ROLE)) {
			// ユーザ追加ロール情報エクスポート処理を取得
			exportBean = reference().userExtraRoleExport();
		} else if (exportDto.getExportTable().equals(PlatformFileConst.CODE_KEY_TABLE_TYPE_SECTION)) {
			// 所属マスタエクスポートクラス取得
			exportBean = reference().sectionExport();
		} else {
			// エクスポート処理を取得
			exportBean = getExportBean(exportDto.getExportTable());
		}
		// エクスポート処理が取得できなかった場合
		if (exportBean == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// エクスポート実施
		exportBean.export(vo.getRadSelect(), targetDate, vo.getPltWorkPlace(), vo.getPltEmployment(),
				vo.getPltSection(), vo.getPltPosition());
	}
	
	/**
	 * エクスポート処理を取得する。<br>
	 * @param exportTable データ区分(エクスポート)
	 * @return エクスポート処理
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ExportBeanInterface getExportBean(String exportTable) throws MospException {
		// モデルキー(データ区分)からモデルクラス名を取得
		String modelClass = MospUtility.getModelClass(exportTable, mospParams.getProperties(), getSystemDate());
		// エクスポート処理を取得
		return (ExportBeanInterface)reference().createBean(modelClass);
	}
	
}
