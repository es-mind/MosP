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
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteSearchBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.comparator.workflow.ApprovalRouteMasterRouteCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.workflow.vo.RouteListVo;

/**
 * 各種申請項目の承認者として設定するユニット情報の検索・参照を行う。<br>
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
public class RouteListAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW			= "PF3210";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿ったルート情報の一覧表示を行う。<br>
	 * 一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "PF3212";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に
	 * 各画面で扱っている情報を最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SEARCH		= "PF3213";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "PF3218";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "PF3219";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE	= "PF3285";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public RouteListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new RouteListVo();
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
			setVoFields();
			search();
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
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException VOの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		RouteListVo vo = (RouteListVo)mospParams.getVo();
		
		iniVoFields();
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		RouteListVo vo = (RouteListVo)mospParams.getVo();
		// 初期値設定
		vo.setAryLblActivateDate(new String[0]);
		vo.setAryLblRouteCode(new String[0]);
		vo.setAryLblRouteName(new String[0]);
		vo.setAryLblRouteStage(new String[0]);
		vo.setAryLblFirstUnit(new String[0]);
		vo.setAryLblEndUnit(new String[0]);
		vo.setAryLblInactivate(new String[0]);
		vo.setAryCkbRouteListId(new String[0]);
		// 一覧選択情報を初期化
		initCkbSelect();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		RouteListVo vo = (RouteListVo)mospParams.getVo();
		// 検索クラス取得
		ApprovalRouteSearchBeanInterface search = reference().approvalRouteSearch();
		search.setActivateDate(getSearchActivateDate());
		search.setApprovalCount(vo.getPltSearchRouteStage());
		search.setRouteCode(vo.getTxtSearchRouteCode());
		search.setRouteName(vo.getTxtSearchRouteName());
		search.setUnitCode(vo.getTxtSearchUnitCode());
		search.setUnitName(vo.getTxtSearchUnitName());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<ApprovalRouteDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ApprovalRouteMasterRouteCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 一覧選択情報初期化
		initCkbSelect();
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
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合 
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
		RouteListVo vo = (RouteListVo)mospParams.getVo();
		// 一括更新処理
		PlatformBeanHandlerInterface platform = platform();
		// 承認ルートユニットマスタ更新(子テーブル)
		platform.approvalRouteUnitRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
				getInt(vo.getPltUpdateInactivate()));
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 一括更新失敗メッセージを設定
			PfMessageUtility.addMessageBatchUpdatetFailed(mospParams);
			return;
		}
		// 承認ルートマスタ更新
		platform.approvalRouteRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
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
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		RouteListVo vo = (RouteListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblRouteCode = new String[list.size()];
		String[] aryLblRouteName = new String[list.size()];
		String[] aryLblRouteStage = new String[list.size()];
		String[] aryLblFirstUnit = new String[list.size()];
		String[] aryLblEndUnit = new String[list.size()];
		String[] aryLblUnitInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			ApprovalRouteDtoInterface dto = (ApprovalRouteDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getPfmApprovalRouteId());
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblRouteCode[i] = dto.getRouteCode();
			aryLblRouteName[i] = dto.getRouteName();
			aryLblRouteStage[i] = String.valueOf(dto.getApprovalCount());
			aryLblUnitInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
			Date date = dto.getActivateDate();
			String unitCode = "";
			if (dto.getApprovalCount() == 1) {
				// 階層数が1の場合、一次ユニット承認者はブランク。
				aryLblFirstUnit[i] = "";
			} else {
				// 一次ユニットコード取得
				unitCode = getRouteUnit(dto.getRouteCode(), date, 1);
				// 一次ユニット承認者
				aryLblFirstUnit[i] = getUnitApproverName(unitCode, date);
			}
			// 最終ユニットコード取得
			unitCode = getRouteUnit(dto.getRouteCode(), date, dto.getApprovalCount());
			// 最終ユニット承認者
			aryLblEndUnit[i] = getUnitApproverName(unitCode, date);
		}
		// データをVOに設定
		vo.setAryCkbRouteListId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblRouteCode(aryLblRouteCode);
		vo.setAryLblRouteName(aryLblRouteName);
		vo.setAryLblRouteStage(aryLblRouteStage);
		vo.setAryLblFirstUnit(aryLblFirstUnit);
		vo.setAryLblEndUnit(aryLblEndUnit);
		vo.setAryLblInactivate(aryLblUnitInactivate);
	}
	
	/**
	 * ルートに設定されたユニットコードを取得する。<br>
	 * @param routeCode ルートコード
	 * @param date 有効日
	 * @param approvalStage 承認段階
	 * @return 指定承認段階の承認ユニットコード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private String getRouteUnit(String routeCode, Date date, int approvalStage) throws MospException {
		// ルートユニットマスタ
		ApprovalRouteUnitReferenceBeanInterface getRouteUnitInfo = reference().approvalRouteUnit();
		// ルートユニットマスタ取得
		ApprovalRouteUnitDtoInterface routeUnit = getRouteUnitInfo.findForKey(routeCode, date, approvalStage);
		// ユニットコードを返す。
		return routeUnit.getUnitCode();
	}
	
	/**
	 * 承認ユニットの承認者名を取得する。<br>
	 * @param unitCode ユニットコード
	 * @param date 有効日
	 * @return 指定ユニットの承認者
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private String getUnitApproverName(String unitCode, Date date) throws MospException {
		String approverName;
		approverName = "";
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
			String[] aryPersonalCode = unitDto.getApproverPersonalId().split(",");
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
			// 人事マスタ参照
			HumanDtoInterface humanDto = getHumanInfo.getHumanInfoForEmployeeCode(aryEmployeeCode.get(0), date);
			// 承認者氏名を設定
			approverName = MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
		}
		return approverName;
	}
	
	/**
	 * 検索項目の年月日を設定する。<br>
	 * @throws MospException VOの取得に失敗した場合
	 */
	protected void setVoFields() throws MospException {
		// VO取得
		RouteListVo vo = (RouteListVo)mospParams.getVo();
		// DTOの値をVOに設定
		if (null == vo.getTxtSearchActivateYear()) {
			iniVoFields();
		}
	}
	
	/**
	 * 基本設定を行う。<br>
	 * @throws MospException VOの取得に失敗した場合
	 */
	protected void iniVoFields() throws MospException {
		// VO取得
		RouteListVo vo = (RouteListVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// 初期値設定
		setDefaultValues();
		vo.setPltSearchRouteStage("");
		vo.setTxtSearchRouteCode("");
		vo.setTxtSearchRouteName("");
		vo.setTxtSearchUnitCode("");
		vo.setTxtSearchUnitName("");
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(ApprovalRouteMasterRouteCodeComparator.class.getName());
	}
	
}
