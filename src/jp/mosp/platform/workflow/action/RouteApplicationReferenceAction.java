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
package jp.mosp.platform.workflow.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceSearchBeanInterface;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.RouteApplicationReferenceDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.system.base.PlatformSystemVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.workflow.vo.RouteApplicationReferenceVo;

/**
 * ルート適用を検索、参照する。<br>
 * 社員コードや承認者社員コード、ルート適用コードなどを指定して、<br>
 * 誰にどのルートが適用されているかを参照する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li></ul>
 */
public class RouteApplicationReferenceAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * サーバより日付を取得し、検索欄の表示期間に年月をデフォルトで表示する。<br>
	 */
	public static final String	CMD_SHOW				= "PF3500";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った承認情報の一覧表示を行う。<br>
	 * 一覧表示の際には社員コードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "PF3502";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 有効日を決定し、所属や職位などのプルダウンを取得する。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF3506";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "PF3508";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "PF3509";
	
	
	/**
	 * {@link PlatformSystemAction#PlatformSystemAction()}を実行する。<br>
	 */
	public RouteApplicationReferenceAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new RouteApplicationReferenceVo();
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
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定コマンド
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO準備
		RouteApplicationReferenceVo vo = (RouteApplicationReferenceVo)mospParams.getVo();
		// 初期値設定
		initApplicationReferenceVoFields();
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
		
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合 
	 */
	protected void search() throws MospException {
		// VO準備
		RouteApplicationReferenceVo vo = (RouteApplicationReferenceVo)mospParams.getVo();
		// 検索クラス取得
		RouteApplicationReferenceSearchBeanInterface search = reference().routeApplicationReferenceSearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setWorkPlaceCode(vo.getPltSearchWorkPlace());
		search.setEmploymentCode(vo.getPltSearchEmployment());
		search.setSectionCode(vo.getPltSearchSection());
		search.setPositionCode(vo.getPltSearchPosition());
		search.setWorkflowType(getInt(vo.getPltSearchFlowType()));
		search.setRouteApplicationCode(vo.getTxtSearchRouteApplicationCode());
		search.setRouteApplicationName(vo.getTxtSearchRouteApplicationName());
		search.setRouteCode(vo.getTxtSearchRouteCode());
		search.setRouteName(vo.getTxtSearchRouteName());
		search.setApproverCode(vo.getTxtSearchApproverCode());
		search.setApproverName(vo.getTxtSearchApproverName());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<RouteApplicationReferenceDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.size() == 0) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		RouteApplicationReferenceVo vo = (RouteApplicationReferenceVo)mospParams.getVo();
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
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合 
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * プルダウンを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合 
	 */
	private void setPulldown() throws MospException {
		// VO準備
		RouteApplicationReferenceVo vo = (RouteApplicationReferenceVo)mospParams.getVo();
		// プルダウンの設定
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			vo.setAryPltSearchWorkPlaceMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchEmploymentMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchSectionMaster(getInputActivateDatePulldown());
			vo.setAryPltSearchPositionMaster(getInputActivateDatePulldown());
			return;
		}
		// 検索有効日取得
		Date date = getSearchActivateDate();
		// 勤務地
		vo.setAryPltSearchWorkPlaceMaster(reference().workPlace().getCodedSelectArray(date, true, null));
		// 雇用契約
		vo.setAryPltSearchEmploymentMaster(reference().employmentContract().getCodedSelectArray(date, true, null));
		// 所属
		vo.setAryPltSearchSectionMaster(reference().section().getCodedSelectArray(date, true, null));
		// 職位
		vo.setAryPltSearchPositionMaster(reference().position().getCodedSelectArray(date, true, null));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO準備
		RouteApplicationReferenceVo vo = (RouteApplicationReferenceVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblRouteApplicationCode = new String[list.size()];
		String[] aryLblRouteApplicationName = new String[list.size()];
		String[] aryLblRouteCode = new String[list.size()];
		String[] aryLblRouteName = new String[list.size()];
		String[] aryLblFirstApprovalName = new String[list.size()];
		String[] aryLblRouteStage = new String[list.size()];
		String[] aryLblEndApprovalName = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			RouteApplicationReferenceDtoInterface dto = (RouteApplicationReferenceDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = dto.getEmployeeName();
			aryLblRouteApplicationCode[i] = dto.getRouteApplicationCode();
			aryLblRouteApplicationName[i] = dto.getRouteApplicationName();
			aryLblRouteCode[i] = dto.getRouteCode();
			aryLblRouteName[i] = dto.getRouteName();
			aryLblRouteStage[i] = String.valueOf(dto.getRouteStage());
			aryLblFirstApprovalName[i] = dto.getFirstApprovalName();
			aryLblEndApprovalName[i] = dto.getEndApprovalName();
		}
		// データをVOに設定
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblRouteApplicationCode(aryLblRouteApplicationCode);
		vo.setAryLblRouteApplicationName(aryLblRouteApplicationName);
		vo.setAryLblRouteCode(aryLblRouteCode);
		vo.setAryLblRouteName(aryLblRouteName);
		vo.setAryLblRouteStage(aryLblRouteStage);
		vo.setAryLblFirstApprovalName(aryLblFirstApprovalName);
		vo.setAryLblEndApprovalName(aryLblEndApprovalName);
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		RouteApplicationReferenceVo vo = (RouteApplicationReferenceVo)mospParams.getVo();
		// 社員コード
		vo.setTxtSearchEmployeeCode("");
		// 氏名
		vo.setTxtSearchEmployeeName("");
		// 勤務地名称プルダウン
		vo.setPltSearchWorkPlace("");
		// 雇用契約名称プルダウン
		vo.setPltSearchEmployment("");
		// 所属名称プルダウン
		vo.setPltSearchSection("");
		// 職位名称プルダウン
		vo.setPltSearchPosition("");
		// ワークフロー区分
		vo.setPltSearchFlowType("");
		// ルート適用コード
		vo.setTxtSearchRouteApplicationCode("");
		// ルート適用名称
		vo.setTxtSearchRouteApplicationName("");
		// ルートコード
		vo.setTxtSearchRouteCode("");
		// ルート名称
		vo.setTxtSearchRouteName("");
		// 承認者社員コード
		vo.setTxtSearchApproverCode("");
		// 承認者氏名
		vo.setTxtSearchApproverName("");
	}
	
	/**
	 * {@link PlatformSystemVo}のフィールドへ初期値を設定する。<br>
	 */
	protected void initApplicationReferenceVoFields() {
		// VO準備
		RouteApplicationReferenceVo vo = (RouteApplicationReferenceVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 初期値設定
		setSearchActivateDate(date);
		vo.setPltSearchInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		vo.setTxtUpdateActivateYear(getStringYear(date));
		vo.setTxtUpdateActivateMonth(getStringMonth(date));
		vo.setTxtUpdateActivateDay(getStringDay(date));
		vo.setPltUpdateInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
	}
}
