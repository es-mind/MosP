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
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanListDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.vo.HumanListVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 各種検索項目から社員一覧を検索する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li></ul>
 */
public class HumanListAction extends PlatformHumanAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "PF1110";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 各レコード毎に有効日が設定されている項目（所属情報、職位情報、雇用契約情報）に対して
	 * テキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なレコードのプルダウンリストを作成し、各種検索項目毎にセットする。<br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF1111";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿った社員情報の一覧表示を行う。<br>
	 * 入力チェックを行った際に有効日が入力されていなければエラーメッセージを表示する。<br>
	 * 一覧表示の際には社員コードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "PF1112";
	
	/**
	 * 再検索表示コマンド。<br>
	 * <br>
	 * 一度検索を行った上でこれよりも奥の階層にあたる画面から戻ってきた際には再検索を行い、
	 * 最初から検索結果が一覧表示されている。<br>
	 */
	public static final String	CMD_RE_SEARCH			= "PF1113";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "PF1114";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "PF1115";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 画面遷移を行う。<br>
	 */
	public static final String	CMD_TRANSFER			= "PF1116";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再検索
			prepareVo(true, false);
			search();
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
		return new HumanListVo();
	}
	
	/**
	 * 初期表示処理を行う。
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO準備
		HumanListVo vo = (HumanListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ設定
		setPageInfo(CMD_PAGE, getListLength());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 検索拡張モード設定
		vo.setModeSearchExpansion("");
		// プルダウン設定
		setPulldown();
		// 一覧初期化
		setList(new ArrayList<BaseDtoInterface>());
		// ソートキー設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
	}
	
	/**
	 * 有効日モード変更処理を行う。
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		HumanListVo vo = (HumanListVo)mospParams.getVo();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定(編集状態→決定状態)
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定(決定状態→編集状態)
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン設定
		setPulldown();
		// 一覧初期化
		setList(new ArrayList<BaseDtoInterface>());
		// データ配列初期化
		vo.setList(new ArrayList<BaseDtoInterface>());
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException 検索、或いはソートに失敗した場合
	 */
	protected void search() throws MospException {
		// VO準備
		HumanListVo vo = (HumanListVo)mospParams.getVo();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// エラーメッセージを設定(有効日を決定してください)
			PfMessageUtility.addErrorActivateDateNotSettled(mospParams);
			return;
		}
		// 検索クラス取得
		HumanSearchBeanInterface humanSearch = reference().humanSearch();
		// 検索条件確認
		checkSearchCondition(vo.getTxtEmployeeCode(), vo.getTxtLastName(), vo.getPltWorkPlaceAbbr(),
				vo.getPltEmploymentName(), vo.getPltSectionAbbr(), vo.getPltPositionName(), vo.getPltState(),
				vo.getTxtFirstName(), vo.getTxtLastKana(), vo.getTxtFirstKana(), vo.getTxtSearchWord());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 検索条件設定
		humanSearch.setTargetDate(getActivateDate());
		humanSearch.setEmployeeCode(vo.getTxtEmployeeCode());
		humanSearch.setLastName(vo.getTxtLastName());
		humanSearch.setWorkPlaceCode(vo.getPltWorkPlaceAbbr());
		humanSearch.setSectionCode(vo.getPltSectionAbbr());
		humanSearch.setPositionCode(vo.getPltPositionName());
		humanSearch.setEmploymentContractCode(vo.getPltEmploymentName());
		humanSearch.setFirstName(vo.getTxtFirstName());
		humanSearch.setLastKana(vo.getTxtLastKana());
		humanSearch.setLastKanaType(vo.getPltLastKana());
		humanSearch.setFirstKana(vo.getTxtFirstKana());
		humanSearch.setStateType(vo.getPltState());
		humanSearch.setInformationType(vo.getPltInfoType());
		humanSearch.setSearchWord(vo.getTxtSearchWord());
		humanSearch.setEmployeeCodeType(vo.getPltEmployeeCode());
		humanSearch.setFirstKanaType(vo.getPltFirstKana());
		humanSearch.setFirstNameType(vo.getPltFirstName());
		humanSearch.setLastNameType(vo.getPltLastName());
		// 検索条件設定(下位所属要否)
		humanSearch.setNeedLowerSection(true);
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 検索
		List<HumanListDtoInterface> list = humanSearch.getHumanList();
		// 検索結果設定
		vo.setList(list);
		// ソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.size() == 0) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// 検索有効日設定
		vo.setActivateDate(getActivateDate());
	}
	
	/**
	 * ソート処理を行う。<br>
	 * @throws MospException VO、或いは比較クラスの取得に失敗した場合
	 */
	protected void sort() throws MospException {
		// 検索
		setList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * ページ繰り処理を行う。<br>
	 * @throws MospException VOの取得に失敗した場合
	 */
	protected void page() throws MospException {
		// ページ繰り及びリスト設定
		setList(pageList());
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		HumanListVo vo = (HumanListVo)mospParams.getVo();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getAryPersonalId(getTransferredIndex()));
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getActivateDate());
		// 譲渡Actionクラス名毎に処理
		mospParams.setNextCommand(HumanInfoAction.CMD_SELECT);
	}
	
	/**
	 * VOに初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		HumanListVo vo = (HumanListVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 初期値設定
		vo.setTxtActivateYear(getStringYear(date));
		vo.setTxtActivateMonth(getStringMonth(date));
		vo.setTxtActivateDay(getStringDay(date));
		vo.setPltEmployeeCode(PlatformConst.SEARCH_BROAD_MATCH);
		vo.setPltLastKana(PlatformConst.SEARCH_BROAD_MATCH);
		vo.setPltLastName(PlatformConst.SEARCH_BROAD_MATCH);
		vo.setPltFirstKana(PlatformConst.SEARCH_BROAD_MATCH);
		vo.setPltFirstName(PlatformConst.SEARCH_BROAD_MATCH);
		vo.setPltState(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		vo.setJsSearchConditionRequired(isSearchConditionRequired());
	}
	
	/**
	 * 人事一覧情報リストをVOに設定する。<br>
	 * @param humanList 人事一覧情報リスト
	 */
	protected void setList(List<? extends BaseDtoInterface> humanList) {
		// VO取得
		HumanListVo vo = (HumanListVo)mospParams.getVo();
		// 配列の初期化
		String[] aryEmployeeCode = new String[humanList.size()];
		String[] aryEmployeeName = new String[humanList.size()];
		String[] aryEmployeeKana = new String[humanList.size()];
		String[] aryWorkPlaceAbbr = new String[humanList.size()];
		String[] arySection = new String[humanList.size()];
		String[] aryPositionAbbr = new String[humanList.size()];
		String[] aryEmploymentAbbr = new String[humanList.size()];
		String[] aryState = new String[humanList.size()];
		String[] aryPersonalId = new String[humanList.size()];
		// 配列にデータを設定
		for (int i = 0; i < humanList.size(); i++) {
			// 人事一覧情報取得
			HumanListDtoInterface humanDto = (HumanListDtoInterface)humanList.get(i);
			// 配列に設定
			aryEmployeeCode[i] = humanDto.getEmployeeCode();
			aryEmployeeName[i] = MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName());
			aryEmployeeKana[i] = MospUtility.getHumansName(humanDto.getFirstKana(), humanDto.getLastKana());
			aryWorkPlaceAbbr[i] = humanDto.getWorkPlaceAbbr();
			arySection[i] = humanDto.getSectionName();
			aryPositionAbbr[i] = humanDto.getPositionAbbr();
			aryEmploymentAbbr[i] = humanDto.getEmploymentContractAbbr();
			aryState[i] = humanDto.getRetireState();
			aryPersonalId[i] = humanDto.getPersonalId();
		}
		// VOに配列を設定
		vo.setAryEmployeeCode(aryEmployeeCode);
		vo.setAryEmployeeName(aryEmployeeName);
		vo.setAryEmployeeKana(aryEmployeeKana);
		vo.setAryWorkPlaceAbbr(aryWorkPlaceAbbr);
		vo.setArySection(arySection);
		vo.setAryPositionAbbr(aryPositionAbbr);
		vo.setAryEmploymentAbbr(aryEmploymentAbbr);
		vo.setAryState(aryState);
		vo.setAryPersonalId(aryPersonalId);
	}
	
	/**
	 * VOから有効日(編集)を取得する。<br>
	 * @return 有効日(編集)
	 */
	protected Date getActivateDate() {
		// VO取得
		HumanListVo vo = (HumanListVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
	}
	
	/**
	 * 有効日モード及び有効日を基にプルダウンを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		HumanListVo vo = (HumanListVo)mospParams.getVo();
		// プルダウン初期化
		vo.setAryPltWorkPlace(getInputActivateDatePulldown());
		vo.setAryPltEmployment(getInputActivateDatePulldown());
		vo.setAryPltSectionAbbr(getInputActivateDatePulldown());
		vo.setAryPltPosition(getInputActivateDatePulldown());
		// 有効日取得
		Date targetDate = getActivateDate();
		// 有効日確認
		if (mospParams.hasErrorMessage()) {
			// 有効日が不正な場合
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			return;
		}
		// プルダウン取得及び設定
		vo.setAryPltWorkPlace(
				reference().workPlace().getCodedAbbrSelectArray(targetDate, true, MospConst.OPERATION_TYPE_REFER));
		vo.setAryPltEmployment(reference().employmentContract().getCodedAbbrSelectArray(targetDate, true,
				MospConst.OPERATION_TYPE_REFER));
		vo.setAryPltSectionAbbr(
				reference().section().getCodedSelectArray(targetDate, true, MospConst.OPERATION_TYPE_REFER));
		vo.setAryPltPosition(
				reference().position().getCodedSelectArray(targetDate, true, MospConst.OPERATION_TYPE_REFER));
		
		// 人事汎用のみで使用されるためプルダウン化
		vo.setAryPltFreeWordTypes(getCodeArrayForHumanGeneral(PlatformConst.CODE_KEY_FREE_WORD_TYPE, false));
		
	}
	
}
