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
package jp.mosp.platform.human.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanInfoBeanInterface;
import jp.mosp.platform.bean.human.HumanInfoExtraBeanInterface;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanBinaryArrayDtoInterface;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.human.vo.HumanInfoVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 社員一覧画面で選択した社員の人事情報をまとめて表示する。<br>
 * <br>
 * 以下のコマンドを扱う。
 * <ul><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li></ul>
 * {@link #CMD_TRANSFER}
 * </li><li>
 */
public class HumanInfoAction extends PlatformHumanAction {
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 社員一覧画面で選択された社員の個人ID及び対象日を用いて各種情報を表示する。<br>
	 */
	public static final String		CMD_SELECT			= "PF1121";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 社員コード入力欄・有効日入力欄に入力された社員コード・有効日を基に検索する。
	 * 条件に当てはまる社員の人事情報一覧を表示する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 再表示ボタンがクリックされた場合は現在表示している社員の有効日入力欄に
	 * 入力された有効日の時点での人事情報一覧を表示する。<br>
	 * 入力した社員コードや有効日が存在しない、またはそれぞれ入力されていない状態で
	 * 「検索ボタン」「再表示ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String		CMD_SEARCH			= "PF1122";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこれよりも奥の階層の画面から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で再表示を行う。<br>
	 * <br>
	 * 人事管理共通情報にある「再表示」ボタンは、当該コマンドでなく
	 * {@link HumanInfoAction#CMD_SELECT}をリクエストする。<br>
	 */
	public static final String		CMD_RE_SEARCH		= "PF1123";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String		CMD_TRANSFER		= "PF1126";
	
	/**
	 * 人事汎用管理表示区分(人事一覧画面)。<br>
	 */
	public static final String		KEY_VIEW_HUMAN_INFO	= "HumanInfo";
	
	/**
	 * コードキー(人事一覧追加処理)。<br>
	 */
	protected static final String	CODE_KEY_ADDONS		= "HumanInfoAddons";
	
	
	/**
	 * {@link PlatformHumanAction#PlatformHumanAction()}を実行する。<br>
	 * パンくずリスト用コマンドを設定する。
	 */
	public HumanInfoAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択
			prepareVo(true, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			reSearch();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 画面遷移
			prepareVo(true, false);
			transfer();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HumanInfoVo();
	}
	
	/**
	 * 選択表示処理を行う。
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_SHOW_ALL);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 各種人事情報設定
		setHumanInfo();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 各種人事情報設定
		setHumanInfo();
	}
	
	/**
	 * 再表示処理を行う。<br>
	 * パンくずリストからVOを取得し、VOに保持されている対象社員コード及び有効日で
	 * 各種人事情報を取得及び設定する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void reSearch() throws MospException {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		// 人事管理共通情報設定
		setHumanCommonInfo(vo.getPersonalId(), vo.getTargetDate());
		// 項目初期化
		setDefaultValues();
		// 各種人事情報設定
		setHumanInfo();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(BasicListAction.class.getName())) {
			// 履歴一覧
			mospParams.setNextCommand(BasicListAction.CMD_SELECT);
		} else if (actionName.equals(BasicCardAction.class.getName())) {
			// 履歴追加・履歴編集
			mospParams.setNextCommand(BasicCardAction.CMD_ADD_SELECT);
		} else if (actionName.equals(EntranceCardAction.class.getName())) {
			// 社員入社情報の編集
			mospParams.setNextCommand(EntranceCardAction.CMD_SELECT);
		} else if (actionName.equals(ConcurrentCardAction.class.getName())) {
			// 兼務情報の登録・更新・削除
			mospParams.setNextCommand(ConcurrentCardAction.CMD_SELECT);
		} else if (actionName.equals(SuspensionCardAction.class.getName())) {
			// 休職情報の登録・更新・削除
			mospParams.setNextCommand(SuspensionCardAction.CMD_SELECT);
		} else if (actionName.equals(RetirementCardAction.class.getName())) {
			// 退職情報の登録・更新・削除
			mospParams.setNextCommand(RetirementCardAction.CMD_SELECT);
		} else if (actionName.equals(HumanNormalCardAction.class.getName())) {
			// 人事汎用管理形式：人事汎用通常情報の登録・更新・削除
			mospParams.setNextCommand(HumanNormalCardAction.CMD_SELECT);
		} else if (actionName.equals(HumanHistoryListAction.class.getName())) {
			// 人事汎用管理形式：人事汎用履歴情報履歴一覧画面表示
			mospParams.setNextCommand(HumanHistoryListAction.CMD_SELECT);
		} else if (actionName.equals(HumanHistoryCardAction.class.getName())) {
			// 人事汎用管理形式：人事汎用履歴情報追加画面表示
			mospParams.setNextCommand(HumanHistoryCardAction.CMD_ADD_SELECT);
		} else if (actionName.equals(HumanArrayCardAction.CMD_EDIT_SELECT)) {
			// 人事汎用一覧画面：人事汎用一覧情報編集画面表示
			mospParams.setNextCommand(HumanArrayCardAction.CMD_EDIT_SELECT);
		} else if (actionName.equals(HumanArrayCardAction.class.getName())) {
			// 人事汎用管理形式：人事汎用一覧情報追加画面表示
			mospParams.setNextCommand(HumanArrayCardAction.CMD_ADD_SELECT);
		} else if (actionName.equals(HumanBinaryNormalCardAction.class.getName())) {
			// 人事汎用管理形式：人事汎用バイナリ通常情報の登録・更新・削除
			mospParams.setNextCommand(HumanBinaryNormalCardAction.CMD_SELECT);
		} else if (actionName.equals(HumanBinaryHistoryListAction.class.getName())) {
			// 人事汎用管理形式：人事汎用バイナリ履歴情報履歴一覧画面表示
			mospParams.setNextCommand(HumanBinaryHistoryListAction.CMD_SELECT);
		} else if (actionName.equals(HumanBinaryHistoryCardAction.class.getName())) {
			// 人事汎用管理形式：人事汎用バイナリ履歴情報追加画面表示
			mospParams.setNextCommand(HumanBinaryHistoryCardAction.CMD_ADD_SELECT);
		} else if (actionName.equals(HumanBinaryArrayCardAction.CMD_EDIT_SELECT)) {
			// 人事汎用一覧画面：人事汎用バイナリ一覧情報編集画面表示
			mospParams.setNextCommand(HumanBinaryArrayCardAction.CMD_EDIT_SELECT);
		} else if (actionName.equals(HumanBinaryArrayCardAction.class.getName())) {
			// 人事汎用管理形式：人事汎用バイナリ一覧情報追加画面表示
			mospParams.setNextCommand(HumanBinaryArrayCardAction.CMD_ADD_SELECT);
		} else {
			// 追加機能等(transferredActionにコマンドを直接指定)
			mospParams.setNextCommand(actionName);
		}
	}
	
	/**
	 * VOに初期値を設定する。<br>
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	public void setDefaultValues() throws MospException {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		// 役職要否設定
		vo.setNeedPost(mospParams.getApplicationPropertyBool(PlatformConst.APP_ADD_USE_POST));
		// 人事汎用管理利用区分設定
		vo.setAryDivision(getRemoveHiddenDivisions());
		// 初期値設定
		vo.setLblEntranceDate("");
		vo.setLblEmployment("");
		vo.setLblEmployeeKana("");
		vo.setLblWorkPlace("");
		vo.setLblSection("");
		vo.setLblPosition("");
		vo.setLblPost("");
		vo.setLblRetirementDate("");
		vo.setLblRetirementReason("");
		vo.setLblRetirementDetail("");
		vo.setArySuspensionStartDate(new String[0]);
		vo.setArySuspensionEndDate(new String[0]);
		vo.setArySuspensionsScheduleEndDate(new String[0]);
		vo.setArySuspensionReason(new String[0]);
		vo.setAryConcurrentStartDate(new String[0]);
		vo.setAryConcurrentEndDate(new String[0]);
		vo.setAryConcurrentSectionAbbr(new String[0]);
		vo.setAryConcurrentPositionAbbr(new String[0]);
		vo.setAryConcurrentRemark(new String[0]);
		// 人事情報一覧画面追加情報を初期化
		vo.setExtraParameters(new HashMap<String, String[]>());
		vo.setExtraViewList(new ArrayList<String>());
	}
	
	/**
	 * VOに設定されている対象社員コード及び有効日で各種人事情報を取得し、
	 * それらをVOに設定する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setHumanInfo() throws MospException {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		// 社員コード及び有効日取得
		String personalId = vo.getPersonalId();
		Date targetDate = vo.getTargetDate();
		// 個人情報取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, targetDate);
		if (MospUtility.isEmpty(humanDto)) {
			// エラーメッセージを設定(社員が存在しない場合)
			PfMessageUtility.addErrorCnaNotGet(mospParams, PfNameUtility.humanInfo(mospParams),
					PfNameUtility.activateDate(mospParams));
			return;
		}
		// 入社日情報取得
		EntranceDtoInterface entranceDto = reference().entrance().getEntranceInfo(humanDto.getPersonalId());
		// 兼務情報取得
		List<ConcurrentDtoInterface> concurrentDtoList = reference().concurrent()
			.getContinuedConcurrentList(humanDto.getPersonalId(), targetDate);
		// 休職情報取得
		List<SuspensionDtoInterface> suspensionDtoList = reference().suspension()
			.getContinuedSuspentionList(humanDto.getPersonalId(), targetDate);
		// 退職情報取得
		RetirementDtoInterface retirementDto = reference().retirement().getRetireInfo(humanDto.getPersonalId());
		// VOに情報を設定
		setHumanInfo(humanDto, entranceDto, retirementDto, suspensionDtoList, concurrentDtoList);
		// 人事汎用項目設定
		setHumanGeneralValues(personalId, targetDate);
		// 人事情報一覧画面追加情報用Beanクラス毎に処理
		for (String className : getHumanInfoExtraBeansClassNames()) {
			// 人事情報一覧画面追加情報用Beanクラス取得
			HumanInfoExtraBeanInterface bean = reference().humanInfoExtra(className);
			// 人事情報一覧画面追加情報を設定
			bean.setHumanInfo();
		}
		// 勤怠設定追加JSPリストを設定
		vo.setAddonJsps(getAddonJsps());
		// アドオン追加パラメータ群(キー：パラメータ名)を初期化
		vo.setAddonParams(new HashMap<String, String>());
		// アドオン追加パラメータ配列群(キー：パラメータ名)を初期化
		vo.setAddonArrays(new HashMap<String, String[]>());
		// アドオン追加処理毎に処理
		for (HumanInfoBeanInterface addonBean : getAddonBeans()) {
			// 勤怠設定詳細画面VOに値を設定
			addonBean.setHumanInfo();
		}
	}
	
	/**
	 * 人事汎用項目を設定する。
	 * @param personalId 個人ＩＤ
	 * @param targetDate 有効日
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	private void setHumanGeneralValues(String personalId, Date targetDate) throws MospException {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		
		// 人事画面区分毎に処理
		for (String division : vo.getAryDivision()) {
			
			// 人事汎用管理区分設定取得
			ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(division);
			
			String type = viewConfig.getType();
			// 通常の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
				// 人事汎用通常情報設定
				vo.putNormalItem(division, reference().humanNormal().getShowHumanNormalMapInfo(division,
						KEY_VIEW_HUMAN_INFO, personalId, targetDate, targetDate));
				continue;
			}
			// バイナリ通常の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_NORMAL)) {
				// 人事汎用バイナリ通常情報取得
				HumanBinaryNormalDtoInterface binaryNormal = reference().humanBinaryNormal().findForInfo(personalId,
						division);
				// 項目準備
				String[] aryFileType = new String[1];
				String[] aryFileName = new String[1];
				String[] aryFileRemark = new String[1];
				// 情報がある場合
				if (binaryNormal != null) {
					aryFileType[0] = binaryNormal.getFileType();
					aryFileName[0] = binaryNormal.getFileName();
					aryFileRemark[0] = binaryNormal.getFileRemark();
				}
				// VOに設定
				vo.putAryBinaryFileTypeMap(division, aryFileType);
				vo.putAryBinaryFileNameMap(division, aryFileName);
				vo.putAryBinaryFileRemarkMap(division, aryFileRemark);
				continue;
			}
			// 履歴の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
				// システム日付から人事汎用履歴情報マップ取得
				LinkedHashMap<String, Map<String, String>> historyMap = reference().humanHistory()
					.getHumanHistoryMapInfo(division, KEY_VIEW_HUMAN_INFO, personalId, targetDate, targetDate);
				// 存在しない場合
				if (historyMap == null) {
					// 初期化
					historyMap = new LinkedHashMap<String, Map<String, String>>();
					// 人事汎用履歴情報設定
					vo.putHistoryItem(division, historyMap);
					continue;
				}
				// 有効日リスト取得
				List<String> activeList = new ArrayList<String>(historyMap.keySet());
				if (activeList.isEmpty() == false) {
					// VOに設定
					vo.putDivisionItem(division, activeList.get(0));
				}
				// 人事汎用履歴情報設定
				vo.putHistoryItem(division, historyMap);
				continue;
			}
			// バイナリ履歴登録の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_HISTORY)) {
				// 人事汎用履歴バイナリ情報を取得
				HumanBinaryHistoryDtoInterface binaryHistory = reference().humanBinaryHistory().findForInfo(personalId,
						division, targetDate);
				// 項目準備
				String[] aryActiveDate = new String[1];
				String[] aryFileType = new String[1];
				String[] aryFileName = new String[1];
				String[] aryFileRemark = new String[1];
				// 情報が有る場合
				if (binaryHistory != null) {
					aryActiveDate[0] = DateUtility.getStringDate(binaryHistory.getActivateDate());
					aryFileType[0] = binaryHistory.getFileType();
					aryFileName[0] = binaryHistory.getFileName();
					aryFileRemark[0] = binaryHistory.getFileRemark();
				}
				// VOに設定
				vo.putAryBinaryActiveDateMap(division, aryActiveDate);
				vo.putAryBinaryFileTypeMap(division, aryFileType);
				vo.putAryBinaryFileNameMap(division, aryFileName);
				vo.putAryBinaryFileRemarkMap(division, aryFileRemark);
				continue;
			}
			// 一覧の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
				LinkedHashMap<String, Map<String, String>> arrayMap = reference().humanArray()
					.getRowIdArrayMapInfo(division, KEY_VIEW_HUMAN_INFO, personalId, targetDate);
				// 人事汎用一覧情報設定
				vo.putArrayItem(division, arrayMap);
			}
			// バイナリ一覧登録の場合
			if (type.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_ARRAY)) {
				// 人事汎用一覧情報リストを取得
				List<HumanBinaryArrayDtoInterface> binaryArrayList = reference().humanBinaryArray()
					.findForItemType(personalId, division);
				String[] aryRowId = new String[binaryArrayList.size()];
				String[] aryActiveDate = new String[binaryArrayList.size()];
				String[] aryFileType = new String[binaryArrayList.size()];
				String[] aryFileName = new String[binaryArrayList.size()];
				String[] aryFileRemark = new String[binaryArrayList.size()];
				for (int i = 0; i < binaryArrayList.size(); i++) {
					HumanBinaryArrayDtoInterface binaryArrayDto = binaryArrayList.get(i);
					aryRowId[i] = String.valueOf(binaryArrayDto.getHumanRowId());
					aryActiveDate[i] = getStringDate(binaryArrayDto.getActivateDate());
					aryFileType[i] = binaryArrayDto.getFileType();
					aryFileName[i] = binaryArrayDto.getFileName();
					aryFileRemark[i] = binaryArrayDto.getFileRemark();
				}
				vo.putAryBinaryRowIdMap(division, aryRowId);
				vo.putAryBinaryActiveDateMap(division, aryActiveDate);
				vo.putAryBinaryFileTypeMap(division, aryFileType);
				vo.putAryBinaryFileNameMap(division, aryFileName);
				vo.putAryBinaryFileRemarkMap(division, aryFileRemark);
				continue;
			}
		}
	}
	
	/**
	 * 人事情報をVOに設定する。<br>
	 * @param humanDto          人事基本情報
	 * @param entranceDto       入社情報
	 * @param retirementDto     退職情報
	 * @param suspensionDtoList 休職情報
	 * @param concurrentDtoList 兼務情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setHumanInfo(HumanDtoInterface humanDto, EntranceDtoInterface entranceDto,
			RetirementDtoInterface retirementDto, List<SuspensionDtoInterface> suspensionDtoList,
			List<ConcurrentDtoInterface> concurrentDtoList) throws MospException {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		// 所属参照クラス、職位参照クラス取得、人事汎用履歴参照クラス、名称区分参照クラス取得
		SectionReferenceBeanInterface sectionReference = reference().section();
		PositionReferenceBeanInterface positionReference = reference().position();
		HumanHistoryReferenceBeanInterface humanHistoryReference = reference().humanHistory();
		NamingReferenceBeanInterface namingReference = reference().naming();
		
		// 対象日取得
		Date targetDate = vo.getTargetDate();
		
		// 人事基本情報設定
		vo.setLblEmployeeKana(MospUtility.getHumansName(humanDto.getFirstKana(), humanDto.getLastKana()));
		
		// 人事履歴情報リスト取得
		List<HumanDtoInterface> humanList = reference().human().getHistory(humanDto.getPersonalId());
		
		// 勤務地情報取得
		WorkPlaceDtoInterface workPlaceDto = reference().workPlace().getWorkPlaceInfo(humanDto.getWorkPlaceCode(),
				targetDate);
		
		// 現勤務地の名・継続年数を設定
		vo.setLblWorkPlace(HumanUtility.getWorkPlaceStayMonths(humanDto, humanList, targetDate, workPlaceDto,
				retirementDto, mospParams));
		
		// 所属情報設定
		SectionDtoInterface sectionDto = sectionReference.getSectionInfo(humanDto.getSectionCode(), targetDate);
		if (sectionDto != null) {
			// 現所属の名・継続年数を設定
			vo.setLblSection(HumanUtility.getSectionStayMonths(humanDto, humanList, targetDate, sectionDto,
					retirementDto, mospParams));
		}
		
		// 職位情報設定
		PositionDtoInterface positionDto = positionReference.getPositionInfo(humanDto.getPositionCode(), targetDate);
		if (positionDto != null) {
			// 現職位の名・継続年数を設定
			vo.setLblPosition(HumanUtility.getPositionStayMonths(humanDto, humanList, targetDate, positionDto,
					retirementDto, mospParams));
		}
		
		// 雇用契約情報設定
		EmploymentContractDtoInterface employmentContractDto = reference().employmentContract()
			.getContractInfo(humanDto.getEmploymentContractCode(), targetDate);
		if (employmentContractDto != null) {
			// 現雇用契約の名・継続年数を設定
			vo.setLblEmployment(HumanUtility.getEmploymentStayMonths(humanDto, humanList, targetDate,
					employmentContractDto, retirementDto, mospParams));
		}
		
		// 役職が有効の場合
		if (vo.getNeedPost()) {
			// 役職情報設定
			List<HumanHistoryDtoInterface> postList = humanHistoryReference.findForHistory(humanDto.getPersonalId(),
					PlatformConst.NAMING_TYPE_POST);
			// 対象役職情報取得
			HumanHistoryDtoInterface postDto = humanHistoryReference.findForKey(humanDto.getPersonalId(),
					PlatformConst.NAMING_TYPE_POST, humanDto.getActivateDate());
			// 役職情報がないまたは空ではない場合
			if (postDto != null && postList.isEmpty() == false) {
				// 役職名取得
				String namingItemName = namingReference.getNamingItemName(PlatformConst.NAMING_TYPE_POST,
						postDto.getHumanItemValue(), targetDate);
				// 画面に設定
				vo.setLblPost(HumanUtility.getPostStayMonths(humanDto, humanList, targetDate, postList, postDto,
						retirementDto, mospParams, namingItemName));
			}
		}
		
		// 入社情報設定
		if (entranceDto != null) {
			// 入社日設定
			vo.setLblEntranceDate(getStringDate(entranceDto.getEntranceDate()));
			// 勤続日数設定
			vo.setLblYearsOfService(HumanUtility.getContinuationMonthName(targetDate, entranceDto.getEntranceDate(),
					retirementDto, mospParams));
		}
		
		// 退社情報設定
		if (retirementDto != null) {
			vo.setLblRetirementDate(getStringDate(retirementDto.getRetirementDate()));
			vo.setLblRetirementReason(mospParams.getProperties().getCodeItemName(PlatformConst.CODE_KEY_RETIREMENT,
					retirementDto.getRetirementReason()));
			vo.setLblRetirementDetail(retirementDto.getRetirementDetail());
		}
		
		// 休職情報作成
		String[] arySuspensionStartDate = new String[suspensionDtoList.size()];
		String[] arySuspensionEndDate = new String[suspensionDtoList.size()];
		String[] arySuspensionsScheduleEndDate = new String[suspensionDtoList.size()];
		String[] arySuspensionReason = new String[suspensionDtoList.size()];
		for (int i = 0; i < suspensionDtoList.size(); i++) {
			SuspensionDtoInterface dto = suspensionDtoList.get(i);
			arySuspensionStartDate[i] = getStringDate(dto.getStartDate());
			arySuspensionEndDate[i] = getStringDate(dto.getEndDate());
			arySuspensionsScheduleEndDate[i] = getStringDate(dto.getScheduleEndDate());
			arySuspensionReason[i] = dto.getSuspensionReason();
		}
		
		// 休職情報設定
		vo.setArySuspensionStartDate(arySuspensionStartDate);
		vo.setArySuspensionEndDate(arySuspensionEndDate);
		vo.setArySuspensionsScheduleEndDate(arySuspensionsScheduleEndDate);
		vo.setArySuspensionReason(arySuspensionReason);
		
		// 兼務情報作成
		String[] aryConcurrentStartDate = new String[concurrentDtoList.size()];
		String[] aryConcurrentEndDate = new String[concurrentDtoList.size()];
		String[] aryConcurrentSectionAbbr = new String[concurrentDtoList.size()];
		String[] aryConcurrentPositionAbbr = new String[concurrentDtoList.size()];
		String[] aryConcurrentRemark = new String[concurrentDtoList.size()];
		for (int i = 0; i < concurrentDtoList.size(); i++) {
			ConcurrentDtoInterface dto = concurrentDtoList.get(i);
			aryConcurrentStartDate[i] = getStringDate(dto.getStartDate());
			aryConcurrentEndDate[i] = getStringDate(dto.getEndDate());
			aryConcurrentRemark[i] = dto.getConcurrentRemark();
			// 所属情報取得
			SectionDtoInterface concurrentSectionDto = sectionReference.getSectionInfo(dto.getSectionCode(),
					dto.getStartDate());
			if (concurrentSectionDto != null) {
				aryConcurrentSectionAbbr[i] = concurrentSectionDto.getSectionAbbr();
			}
			// 職位情報取得
			PositionDtoInterface concurrentPositionDto = positionReference.getPositionInfo(dto.getPositionCode(),
					dto.getStartDate());
			if (concurrentPositionDto != null) {
				aryConcurrentPositionAbbr[i] = concurrentPositionDto.getPositionAbbr();
			}
		}
		
		// 兼務情報設定
		vo.setAryConcurrentStartDate(aryConcurrentStartDate);
		vo.setAryConcurrentEndDate(aryConcurrentEndDate);
		vo.setAryConcurrentSectionAbbr(aryConcurrentSectionAbbr);
		vo.setAryConcurrentPositionAbbr(aryConcurrentPositionAbbr);
		vo.setAryConcurrentRemark(aryConcurrentRemark);
	}
	
	/**
	 * MosP設定情報から人事情報一覧画面追加情報用Beanクラス群(クラス名)を取得する。<br>
	 * @return 人事情報一覧画面追加情報用Beanクラス群(クラス名)
	 */
	protected String[] getHumanInfoExtraBeansClassNames() {
		// 人事情報一覧画面追加情報用Beanクラス群(クラス名)取得
		String extraBeans = mospParams.getApplicationProperty(PlatformConst.APP_HUMAN_INFO_EXTRA_BEANS);
		// 取得できない場合
		if (extraBeans == null) {
			return new String[0];
		}
		// 分割
		return MospUtility.split(extraBeans, MospConst.APP_PROPERTY_SEPARATOR);
	}
	
	/**
	 * ロールから人事汎用管理区分を再設定
	 * @return 人事汎用管理区分配列
	 */
	protected String[] getRemoveHiddenDivisions() {
		// 再設定用リスト
		List<String> divisionsList = new ArrayList<String>();
		// 定義された人事汎用管理区分を取得
		for (String division : mospParams.getApplicationProperties(PlatformConst.APP_HUMAN_GENERAL_DIVISIONS)) {
			// ロールで非表示設定された人事汎用管理区分が存在した場合は表示対象としない
			if (RoleUtility.getHiddenDivisionsList(mospParams).contains(division)) {
				continue;
			}
			divisionsList.add(division);
			
		}
		return MospUtility.toArray(divisionsList);
		
	}
	
	/**
	 * 勤怠設定追加処理リストを取得する。<br>
	 * @return 勤怠設定追加処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<HumanInfoBeanInterface> getAddonBeans() throws MospException {
		// 勤怠設定追加処理リストを準備
		List<HumanInfoBeanInterface> addonBeans = new ArrayList<HumanInfoBeanInterface>();
		// 勤怠設定追加処理配列毎に処理
		for (String[] addon : getCodeArray(CODE_KEY_ADDONS, false)) {
			// 勤怠設定追加処理を取得
			String addonBean = addon[0];
			// 勤怠設定追加処理が設定されていない場合
			if (MospUtility.isEmpty(addonBean)) {
				// 次の勤怠設定追加処理へ
				continue;
			}
			// 勤怠設定追加処理を取得
			HumanInfoBeanInterface bean = (HumanInfoBeanInterface)platform().createBean(addonBean);
			// 勤怠設定追加処理リストに値を追加
			addonBeans.add(bean);
		}
		// 勤怠設定追加処理リストを取得
		return addonBeans;
	}
	
	/**
	 * 勤怠設定追加JSPリストを取得する。<br>
	 * @return 勤怠設定追加JSPリスト
	 */
	protected List<String> getAddonJsps() {
		// 勤怠設定追加JSPリストを準備
		List<String> addonJsps = new ArrayList<String>();
		// 勤怠設定追加処理配列毎に処理
		for (String[] addon : getCodeArray(CODE_KEY_ADDONS, false)) {
			// 勤怠設定追加JSPを取得
			String addonJsp = addon[1];
			// 勤怠設定追加JSPが設定されていない場合
			if (MospUtility.isEmpty(addonJsp)) {
				// 次の勤怠設定追加JSPへ
				continue;
			}
			// 勤怠設定追加JSPリストに値を追加
			addonJsps.add(addonJsp);
		}
		// 勤怠設定追加JSPリストを取得
		return addonJsps;
	}
}
