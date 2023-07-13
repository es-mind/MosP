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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.base.PlatformBeanHandlerInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationSearchBeanInterface;
import jp.mosp.platform.comparator.workflow.ApprovalUnitMasterUnitCodeComparator;
import jp.mosp.platform.comparator.workflow.RouteApplicationMasterApplicationCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.workflow.vo.RouteApplicationListVo;

/**
 * ユニットと組み合わせたルートの承認対象を設定するルート適用情報の検索・参照を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li></ul>
 */
public class RouteApplicationListAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * メニューバーより「ルート適用一覧」の項目を選択した場合はこのコマンドが実行され、「ルート適用一覧画面」として初期表示を行う。<br>
	 * 検索欄のテーブルはルート適用一覧用のものを表示し、ルート名称のリンク文字クリック時はルート適用詳細画面の選択表示コマンドが実行されるようにする。<br>
	 */
	public static final String	CMD_SHOW			= "PF3310";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿ったルート適用情報の一覧表示を行う。<br>
	 * 一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "PF3312";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている情報を最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SEARCH		= "PF3313";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "PF3318";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "PF3319";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 更新テーブル有効日入力欄に日付が入力されていない場合やチェックが1件も入っていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE	= "PF3385";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public RouteApplicationListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new RouteApplicationListVo();
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
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			search();
			/*
			} else if (mospParams.getCommand().equals(CMD_SPECIAL_DISPLAY)) {
				// 特殊表示【未使用】
				prepareVo();
			*/
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
			/*
			} else if (mospParams.getCommand().equals(CMD_SPECIAL_DISP)) {
				// 特殊表示【未使用】
				prepareVo();
			*/
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException VOの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		RouteApplicationListVo vo = (RouteApplicationListVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// 初期値設定
		setDefaultValues();
		vo.setPltSearchFlowType("");
		vo.setTxtSearchApplicationCode("");
		vo.setTxtSearchApplicationName("");
		vo.setTxtSearchApplicationEmployee("");
		vo.setTxtSearchRouteCode("");
		vo.setTxtSearchRouteName("");
		vo.setTxtSearchRouteEmployee("");
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(ApprovalUnitMasterUnitCodeComparator.class.getName());
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		RouteApplicationListVo vo = (RouteApplicationListVo)mospParams.getVo();
		// 検索クラス取得
		RouteApplicationSearchBeanInterface search = reference().routeApplicationSearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		search.setWorkflowType(vo.getPltSearchFlowType());
		search.setRouteApplicationCode(vo.getTxtSearchApplicationCode());
		search.setRouteApplicationName(vo.getTxtSearchApplicationName());
		search.setEmployeeCode(vo.getTxtSearchApplicationEmployee());
		search.setRouteCode(vo.getTxtSearchRouteCode());
		search.setRouteName(vo.getTxtSearchRouteName());
		search.setApproverEmployeeCode(vo.getTxtSearchRouteEmployee());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<RouteApplicationDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(RouteApplicationMasterApplicationCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 一覧選択情報初期化
		initCkbSelect();
		// 検索結果確認
		if (list.isEmpty()) {
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
	 * @throws MospException VOに値を設定する処理に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// VO準備
		RouteApplicationListVo vo = (RouteApplicationListVo)mospParams.getVo();
		// 一括更新処理
		PlatformBeanHandlerInterface platform = platform();
		// 承認ユニットマスタ
		platform.routeApplicationRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
				getInt(vo.getPltUpdateInactivate()));
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 一括更新失敗メッセージを設定
			PfMessageUtility.addMessageBatchUpdatetFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 検索有効日設定(一括更新有効日を検索条件に設定)
		setSearchActivateDate(getUpdateActivateDate());
		// 検索
		search();
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 人事マスタ情報の取得に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		RouteApplicationListVo vo = (RouteApplicationListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblApplicationCode = new String[list.size()];
		String[] aryLblApplicationName = new String[list.size()];
		String[] aryLblRouteName = new String[list.size()];
		String[] aryLblRouteActivateDate = new String[list.size()];
		String[] aryLblRouteCode = new String[list.size()];
		String[] aryLblFlowType = new String[list.size()];
		String[] aryLblApplicationLength = new String[list.size()];
		String[] aryLblUnitInactivate = new String[list.size()];
		// 人事マスタ
		HumanReferenceBeanInterface getHumanInfo = reference().human();
		// 勤務地マスタ
		WorkPlaceReferenceBeanInterface getWorkPlaceInfo = reference().workPlace();
		// 雇用契約マスタ
		EmploymentContractReferenceBeanInterface getEmploymentInfo = reference().employmentContract();
		// 職位マスタ
		PositionReferenceBeanInterface getPositionInfo = reference().position();
		// 所属マスタ
		SectionReferenceBeanInterface getSectionInfo = reference().section();
		// 承認ルートマスタ
		ApprovalRouteReferenceBeanInterface getRouteInfo = reference().approvalRoute();
		// 検索有効日
		Date date = getSearchActivateDate();
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			RouteApplicationDtoInterface dto = (RouteApplicationDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getPfmRouteApplicationId());
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblApplicationCode[i] = dto.getRouteApplicationCode();
			aryLblApplicationName[i] = dto.getRouteApplicationName();
			aryLblFlowType[i] = getFlowTypeName(dto.getWorkflowType());
			aryLblUnitInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
			// ルート取得
			ApprovalRouteDtoInterface approvalRouteDto = getRouteInfo.getApprovalRouteInfo(dto.getRouteCode(), date);
			// ルート有効日、ルート名準備
			aryLblRouteActivateDate[i] = aryLblActivateDate[i];
			aryLblRouteName[i] = "";
			// ルート情報がある場合
			if (approvalRouteDto != null) {
				// 設定
				aryLblRouteActivateDate[i] = getStringDate(approvalRouteDto.getActivateDate());
				aryLblRouteName[i] = approvalRouteDto.getRouteName();
			}
			
			StringBuffer sb = new StringBuffer();
			if (dto.getRouteApplicationType() == getInt(PlatformConst.APPLICATION_TYPE_MASTER)) {
				// マスタ組み合わせ指定
				// 勤務地略称取得
				if (!dto.getWorkPlaceCode().equals("")) {
					sb.append(getWorkPlaceInfo.getWorkPlaceAbbr(dto.getWorkPlaceCode(), date));
				}
				// 雇用契約略称取得
				if (!dto.getEmploymentContractCode().equals("")) {
					String employmentAbbr = getEmploymentInfo.getContractAbbr(dto.getEmploymentContractCode(), date);
					if (sb.length() != 0) {
						sb.append(" ");
					}
					sb.append(employmentAbbr);
				}
				// 職位略称取得
				if (!dto.getPositionCode().equals("")) {
					String positionAbbr = getPositionInfo.getPositionAbbr(dto.getPositionCode(), date);
					if (sb.length() != 0) {
						sb.append(" ");
					}
					sb.append(positionAbbr);
				}
				// 所属略称取得
				if (!dto.getSectionCode().equals("")) {
					String sectionAbbr = getSectionInfo.getSectionAbbr(dto.getSectionCode(), date);
					if (sb.length() != 0) {
						sb.append(" ");
					}
					sb.append(sectionAbbr);
				}
				// 適用範囲を設定
				aryLblApplicationLength[i] = sb.toString();
			} else {
				// 個人指定
				String[] aryPersonalCode = dto.getPersonalIds().split(",");
				ArrayList<String> aryEmployeeCode = new ArrayList<String>();
				for (String element : aryPersonalCode) {
					// 社員コード取得
					String employeeCode = getHumanInfo.getEmployeeCode(element, date);
					if (!employeeCode.equals("")) {
						aryEmployeeCode.add(employeeCode);
					}
				}
				// 社員コードリストをソートする。
				Collections.sort(aryEmployeeCode);
				// 被承認者氏名準備
				String humanName = "";
				// 社員コードリスト確認
				if (aryEmployeeCode.isEmpty() == false) {
					// 人事マスタ参照
					HumanDtoInterface humanDto = getHumanInfo.getHumanInfoForEmployeeCode(aryEmployeeCode.get(0), date);
					// 被承認者氏名を設定
					humanName = MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
				}
				// 被承認者氏名を設定
				aryLblApplicationLength[i] = humanName;
			}
			// ルートコード
			aryLblRouteCode[i] = dto.getRouteCode();
		}
		// データをVOに設定
		vo.setAryCkbRouteApplicationListId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblApplicationCode(aryLblApplicationCode);
		vo.setAryLblApplicationName(aryLblApplicationName);
		vo.setAryLblRouteName(aryLblRouteName);
		vo.setAryLblRouteActivateDate(aryLblRouteActivateDate);
		vo.setAryLblRouteCode(aryLblRouteCode);
		vo.setAryLblFlowType(aryLblFlowType);
		vo.setAryLblApplicationLength(aryLblApplicationLength);
		vo.setAryLblInactivate(aryLblUnitInactivate);
	}
	
	/**
	 * フロー区分名称を取得する。<br>
	 * @param workflowType フロー区分
	 * @return フロー区分名称
	 */
	protected String getFlowTypeName(int workflowType) {
		// MosP設定情報からフロー区分配列を取得し確認
		for (String[] code : mospParams.getProperties().getCodeArray(PlatformConst.CODE_KEY_WORKFLOW_TYPE, false)) {
			if (code[0].equals(String.valueOf(workflowType))) {
				return code[1];
			}
		}
		return "";
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO準備
		RouteApplicationListVo vo = (RouteApplicationListVo)mospParams.getVo();
		// 初期値設定
		vo.setAryLblActivateDate(new String[0]);
		vo.setAryLblApplicationCode(new String[0]);
		vo.setAryLblApplicationName(new String[0]);
		vo.setAryLblRouteName(new String[0]);
		vo.setAryLblRouteCode(new String[0]);
		vo.setAryLblRouteActivateDate(new String[0]);
		vo.setAryLblFlowType(new String[0]);
		vo.setAryLblApplicationLength(new String[0]);
		vo.setAryLblInactivate(new String[0]);
		vo.setAryCkbRecordId(new long[0]);
		vo.setAryCkbRouteApplicationListId(new String[0]);
		// 一覧選択情報を初期化
		initCkbSelect();
	}
}
