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
import jp.mosp.platform.bean.workflow.ApprovalUnitSearchBeanInterface;
import jp.mosp.platform.comparator.workflow.ApprovalUnitMasterUnitCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.workflow.vo.UnitListVo;

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
public class UnitListAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW			= "PF3110";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿った雇用契約情報の一覧表示を行う。一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH			= "PF3112";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に
	 * 各画面で扱っている情報を最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SEARCH		= "PF3113";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT			= "PF3118";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE			= "PF3119";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 検索結果一覧の選択チェックボックスの状態を確認し、
	 * チェックの入っているレコードに一括更新テーブル内入力欄の内容を反映させるよう繰り返し処理を行う。<br>
	 * 有効日入力欄に日付が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_BATCH_UPDATE	= "PF3185";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public UnitListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new UnitListVo();
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
		UnitListVo vo = (UnitListVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// 初期値設定
		setDefaultValues();
		vo.setTxtSearchUnitCode("");
		vo.setTxtSearchUnitName("");
		vo.setTxtSearchSectionName("");
		vo.setTxtSearchPositoinName("");
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchApprover("");
		vo.setPltSearchUnitType(PlatformConst.UNIT_TYPE_SECTION);
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
		UnitListVo vo = (UnitListVo)mospParams.getVo();
		// 検索クラス取得
		ApprovalUnitSearchBeanInterface search = reference().approvalUnitSearch();
		// 人事マスタ
		HumanReferenceBeanInterface getHumanInfo = reference().human();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setUnitCode(vo.getTxtSearchUnitCode());
		search.setUnitName(vo.getTxtSearchUnitName());
		search.setUnitType(vo.getPltSearchUnitType());
		search.setSectionName(vo.getTxtSearchSectionName());
		search.setPositionName(vo.getTxtSearchPositoinName());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		search.setApprover(vo.getTxtSearchApprover());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<ApprovalUnitDtoInterface> list = search.getSearchList();
		// 個人指定の場合
		if (vo.getPltSearchUnitType().equals(PlatformConst.UNIT_TYPE_PERSON)) {
			// 承認者社員コード、承認者氏名が入力されている場合
			if ((!vo.getTxtSearchEmployeeCode().isEmpty()) || (!vo.getTxtSearchApprover().isEmpty())) {
				// 人事マスタリストを取得
				Date date = getSearchActivateDate();
				List<HumanDtoInterface> humanList = getHumanInfo.getHumanList(date);
				List<ApprovalUnitDtoInterface> listTmp = new ArrayList<ApprovalUnitDtoInterface>();
				List<String> listPersonId = new ArrayList<String>();
				// 人事マスタリストより検索条件に合致する個人IDを抽出
				for (int i = 0; i < humanList.size(); i++) {
					// リストから情報を取得
					HumanDtoInterface humanDto = humanList.get(i);
					String humanName = humanDto.getLastName() + humanDto.getFirstName();
					String humanNameWithBlank = humanDto.getLastName() + " " + humanDto.getFirstName();
					if ((!vo.getTxtSearchEmployeeCode().isEmpty()) && (!vo.getTxtSearchApprover().isEmpty())) {
						// 承認者社員コード、承認者氏名を両方指定した場合
						if (humanDto.getEmployeeCode().contains(vo.getTxtSearchEmployeeCode())
								&& (humanName.contains(vo.getTxtSearchApprover())
										|| humanNameWithBlank.contains(vo.getTxtSearchApprover()))) {
							listPersonId.add(humanDto.getPersonalId());
						}
					} else if (vo.getTxtSearchApprover().isEmpty()) {
						// 承認者社員コードのみ指定した場合
						if (humanDto.getEmployeeCode().contains(vo.getTxtSearchEmployeeCode())) {
							listPersonId.add(humanDto.getPersonalId());
						}
					} else {
						// 承認者氏名のみ指定した場合
						if (humanName.contains(vo.getTxtSearchApprover())
								|| humanNameWithBlank.contains(vo.getTxtSearchApprover())) {
							listPersonId.add(humanDto.getPersonalId());
						}
					}
				}
				
				for (ApprovalUnitDtoInterface unitDto : list) {
					for (String personalId : listPersonId) {
						if (unitDto.getApproverPersonalId().contains(personalId)) {
							// 指定された承認者社員コード、承認者氏名に合致する個人IDを含む
							// 承認ユニットマスタデータを抽出する。
							// 抽出済みのデータはセットしない。
							if (!listTmp.contains(unitDto)) {
								listTmp.add(unitDto);
							}
						}
					}
				}
				// 承認ユニットマスタリストをクリアする。
				list.clear();
				// 条件に従って抽出したデータリストを承認ユニットマスタリストにセットする。
				if (listTmp.size() != 0) {
					list.addAll(listTmp);
				}
				
			}
		}
		
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(ApprovalUnitMasterUnitCodeComparator.class.getName());
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
		UnitListVo vo = (UnitListVo)mospParams.getVo();
		// 一括更新処理
		PlatformBeanHandlerInterface platform = platform();
		// 承認ユニットマスタ
		platform.approvalUnitRegist().update(getIdArray(vo.getCkbSelect()), getUpdateActivateDate(),
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
		UnitListVo vo = (UnitListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblUnitCode = new String[list.size()];
		String[] aryLblUnitName = new String[list.size()];
		String[] aryLblUnitType = new String[list.size()];
		String[] aryLblApproval = new String[list.size()];
		String[] aryLblUnitInactivate = new String[list.size()];
		// 人事マスタ
		HumanReferenceBeanInterface getHumanInfo = reference().human();
		// 職位マスタ
		PositionReferenceBeanInterface getPositionInfo = reference().position();
		// 所属マスタ
		SectionReferenceBeanInterface getSectionInfo = reference().section();
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			ApprovalUnitDtoInterface dto = (ApprovalUnitDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getPfmApprovalUnitId());
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblUnitCode[i] = dto.getUnitCode();
			aryLblUnitName[i] = dto.getUnitName();
			aryLblUnitType[i] = getUnitTypeName(dto.getUnitType());
			aryLblUnitInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
			Date date = dto.getActivateDate();
			if (dto.getUnitType().equals(PlatformConst.UNIT_TYPE_SECTION)) {
				// 所属指定
				// 職位略称取得
				String positionAbbr = getPositionInfo.getPositionAbbr(dto.getApproverPositionCode(), date);
				// 所属略称取得
				String sectionAbbr = getSectionInfo.getSectionAbbr(dto.getApproverSectionCode(), date);
				// 承認者を設定
				aryLblApproval[i] = positionAbbr + " " + sectionAbbr;
			} else {
				// 個人指定
				String[] aryPersonalCode = dto.getApproverPersonalId().split(",");
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
				aryLblApproval[i] = MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
			}
		}
		// データをVOに設定
		vo.setAryCkbUnitListId(aryCkbRecordId);
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblUnitCode(aryLblUnitCode);
		vo.setAryLblUnitName(aryLblUnitName);
		vo.setAryLblUnitType(aryLblUnitType);
		vo.setAryLblApproval(aryLblApproval);
		vo.setAryLblInactivate(aryLblUnitInactivate);
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO準備
		UnitListVo vo = (UnitListVo)mospParams.getVo();
		// 初期値設定
		vo.setAryLblActivateDate(new String[0]);
		vo.setAryLblUnitCode(new String[0]);
		vo.setAryLblUnitName(new String[0]);
		vo.setAryLblUnitType(new String[0]);
		vo.setAryLblApproval(new String[0]);
		vo.setAryLblInactivate(new String[0]);
		vo.setAryCkbUnitListId(new String[0]);
		// 一覧選択情報を初期化
		initCkbSelect();
	}
	
	/**
	 * ユニット区分名称を取得する。<br>
	 * @param unitType ユニット区分
	 * @return ユニット区分名称
	 */
	protected String getUnitTypeName(String unitType) {
		// MosP設定情報からユニット区分配列を取得し確認
		for (String[] code : mospParams.getProperties().getCodeArray(PlatformConst.CODE_KEY_UNIT_TYPE, false)) {
			if (code[0].equals(String.valueOf(unitType))) {
				return code[1];
			}
		}
		return "";
	}
	
}
