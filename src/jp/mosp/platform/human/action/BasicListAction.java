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
package jp.mosp.platform.human.action;

import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.TopicPath;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HistoryBasicDeleteBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.base.PlatformHumanVo;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.BasicListVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 社員一覧画面で選択した社員の個人基本情報の履歴の一覧を表示する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li></ul>
 * {@link #CMD_TRANSFER}
 * </li><li>
 */
public class BasicListAction extends PlatformHumanAction {
	
	/**
	 * 社員検索コマンド。<br>
	 * <br>
	 * 社員コード入力欄に入力された社員コードを基に検索を行い、
	 * 条件に当てはまる社員の基本情報履歴一覧を表示する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 入力した社員コードが存在しない、または入力されていない状態で
	 * 「検索ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 */
	public static final String		CMD_SEARCH		= "PF1142";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 社員一覧画面で選択された社員の個人IDを基に個人基本情報の履歴を表示する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String		CMD_SELECT		= "PF1146";
	
	/**
	 * 再検索コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこれよりも奥の階層の画面から改めて遷移した場合、
	 * 各種情報の登録状況が更新された状態で再表示を行う。<br>
	 */
	public static final String		CMD_RE_SEARCH	= "PF1147";
	
	/**
	 * 履歴削除コマンド。<br>
	 * <br>
	 * 社員一覧で選択された社員の個人IDを基に個人基本情報の履歴を削除する。<br>
	 * 削除前に行われる削除可否チェックにてエラーの場合、エラーメッセージにて通知する。<br>
	 */
	public static final String		CMD_DELETE		= "PF1148";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String		CMD_TRANSFER	= "PF1149";
	
	/**
	 * MosPアプリケーション設定キー(ポータル用Beanクラス群)。
	 */
	protected static final String	APP_BASIC_BEANS	= "HistoryBasicDeleteBeans";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public BasicListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択表示
			prepareVo(true, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			reSearch();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo(true, false);
			delete();
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
		return new BasicListVo();
	}
	
	/**
	 * 検索処理を行う。
	 * @throws MospException VO、或いは社員情報の取得に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 人事履歴情報リストを取得しVOに設定
		getBasicList();
	}
	
	/**
	 * 選択表示処理を行う。
	 * @throws MospException VO、或いは社員情報の取得に失敗した場合
	 */
	protected void select() throws MospException {
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 人事履歴情報リストを取得しVOに設定
		getBasicList();
	}
	
	/**
	 * VOに保持された情報を基に、再検索を行う。
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void reSearch() throws MospException {
		// VO取得
		BasicListVo vo = (BasicListVo)mospParams.getVo();
		// 人事管理共通情報設定
		setHumanCommonInfo(vo.getPersonalId(), vo.getTargetDate());
		// 項目初期化
		setDefaultValues();
		// 人事履歴情報リストを取得しVOに設定
		getBasicList();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException  インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		BasicListVo vo = (BasicListVo)mospParams.getVo();
		// 履歴削除処理
		deleteHistoryBasicInfo();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴の最終データを削除した段階で社員一覧に画面遷移する
		if (vo.getJsIsLastHistoryBasic() == true) {
			// ポータル画面表示コマンドを取得
			String nextCommand = mospParams.getApplicationProperty(APP_COMMAND_PORTAL);
			// パンくずリスト取得
			List<TopicPath> topicPathList = mospParams.getTopicPathList();
			// パンくずリストの存在及びサイズを確認
			if (topicPathList != null && topicPathList.size() > 1) {
				// 2つ目のパンくずからコマンドを取得
				nextCommand = topicPathList.get(1).getCommand();
			}
			// 連続実行コマンド設定
			mospParams.setNextCommand(nextCommand);
			return;
		}
		// 項目初期化
		setDefaultValues();
		// 人事履歴情報リストを取得しVOに設定
		getBasicList();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		BasicListVo vo = (BasicListVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(BasicCardAction.CMD_EDIT_SELECT)) {
			// MosP処理情報に対象日を再設定
			setTargetDate(getDate(vo.getAryActiveteDate(getTransferredIndex())));
			// MosP処理情報に対象日を設定
			//	setTargetDate(getDate(vo.getAryActiveteDate(getTransferredIndex())));
			// 履歴編集
			mospParams.setNextCommand(BasicCardAction.CMD_EDIT_SELECT);
		} else if (actionName.equals(BasicCardAction.CMD_ADD_SELECT)) {
			// 履歴追加
			mospParams.setNextCommand(BasicCardAction.CMD_ADD_SELECT);
		} else if (actionName.equals(HumanInfoAction.class.getName())) {
			// 社員の人事情報をまとめて表示
			mospParams.setNextCommand(HumanInfoAction.CMD_SELECT);
		}
	}
	
	/**
	 * 個人基本情報履歴削除処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void deleteHistoryBasicInfo() throws MospException {
		// VO取得
		BasicListVo vo = (BasicListVo)mospParams.getVo();
		// 削除対象IDXを取得
		int idx = getTransferredIndex();
		// 削除対象レコードIDを取得
		long pfmHumanId = getLong(vo.getAryPfmHumanId()[idx]);
		// 全削除フラグ取得
		boolean isAllDelete = vo.getJsIsLastHistoryBasic();
		// 個人基本情報削除Beanクラス取得
		HistoryBasicDeleteBeanInterface bean = platform().historyBasicDelete();
		// 個人基本情報削除
		bean.delete(pfmHumanId, isAllDelete);
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// メッセージ設定
		if (isAllDelete) {
			// 履歴削除(全件)成功メッセージを設定
			PfMessageUtility.addMessageDeleteAllHistory(mospParams);
		} else {
			// 削除成功メッセージを設定
			PfMessageUtility.addMessageDeleteSucceed(mospParams);
		}
		// コミット
		commit();
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		BasicListVo vo = (BasicListVo)mospParams.getVo();
		// 初期値設定
		vo.setAryActiveteDate(new String[0]);
		vo.setAryEmployeeKana(new String[0]);
		vo.setAryEmployeeName(new String[0]);
		vo.setAryWorkPlace(new String[0]);
		vo.setAryEmployment(new String[0]);
		vo.setAryPosition(new String[0]);
		vo.setArySection(new String[0]);
		vo.setAryPost(new String[0]);
		vo.setNeedDeleteBasciHistory(
				mospParams.getApplicationPropertyBool(PlatformConst.APP_HISTORY_BASIC_DELETE_BUTTON));
		vo.setNeedPost(mospParams.getApplicationPropertyBool(PlatformConst.APP_ADD_USE_POST));
	}
	
	/**
	 * 人事履歴情報リストを取得し、VOに設定する。<br>
	 * 対象社員コードは{@link PlatformHumanVo#getEmployeeCode()}を、
	 * 対象年月日は{@link PlatformHumanVo#getTargetDate()}を用いる。<br>
	 * @throws MospException 社員情報の取得に失敗した場合
	 */
	protected void getBasicList() throws MospException {
		// VO取得
		BasicListVo vo = (BasicListVo)mospParams.getVo();
		// 個人IDを設定
		String personalId = vo.getPersonalId();
		// 個人IDが設定されていない場合
		if (vo.getPersonalId().isEmpty()) {
			personalId = reference().human().getPersonalId(vo.getEmployeeCode(), getSystemDate());
		}
		// 社員マスタ履歴情報取得
		List<HumanDtoInterface> list = reference().human().getHistory(personalId);
		// 件数確認フラグ
		boolean isLastHistory = false;
		// 件数が1件だった場合
		if (list.size() == 1) {
			isLastHistory = true;
		}
		// JSに譲渡
		vo.setJsIsLastHistoryBasic(isLastHistory);
		// 設定配列準備
		String[] aryPfmHumanId = new String[list.size()];
		String[] aryActiveteDate = new String[list.size()];
		String[] aryEmployeeKana = new String[list.size()];
		String[] aryEmployeeName = new String[list.size()];
		String[] aryWorkPlaceCode = new String[list.size()];
		String[] aryWorkPlace = new String[list.size()];
		String[] aryEmploymentCode = new String[list.size()];
		String[] aryEmployment = new String[list.size()];
		String[] aryPositionCode = new String[list.size()];
		String[] aryPosition = new String[list.size()];
		String[] arySectionCode = new String[list.size()];
		String[] arySection = new String[list.size()];
		// 参照クラス準備
		PositionReferenceBeanInterface position = reference().position();
		SectionReferenceBeanInterface section = reference().section();
		EmploymentContractReferenceBeanInterface contract = reference().employmentContract();
		WorkPlaceReferenceBeanInterface workPlace = reference().workPlace();
		// フィールド設定
		for (int i = 0; i < list.size(); i++) {
			// 人事情報取得(フィールド設定順序は有効日の昇順)
			HumanDtoInterface dto = list.get(list.size() - 1 - i);
			// 個人ID
			vo.setPersonalId(dto.getPersonalId());
			// 識別ID
			aryPfmHumanId[i] = String.valueOf(dto.getPfmHumanId());
			// 有効日設定
			aryActiveteDate[i] = getStringDate(dto.getActivateDate());
			// 氏名設定
			aryEmployeeKana[i] = MospUtility.getHumansName(dto.getFirstKana(), dto.getLastKana());
			aryEmployeeName[i] = MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
			// 勤務地
			aryWorkPlaceCode[i] = dto.getWorkPlaceCode();
			aryWorkPlace[i] = workPlace.getWorkPlaceName(dto.getWorkPlaceCode(), dto.getActivateDate());
			// 雇用契約
			aryEmploymentCode[i] = dto.getEmploymentContractCode();
			aryEmployment[i] = contract.getContractName(dto.getEmploymentContractCode(), dto.getActivateDate());
			// 職位
			aryPositionCode[i] = dto.getPositionCode();
			aryPosition[i] = position.getPositionName(dto.getPositionCode(), dto.getActivateDate());
			// 所属
			arySectionCode[i] = dto.getSectionCode();
			arySection[i] = section.getSectionName(dto.getSectionCode(), dto.getActivateDate());
		}
		// VOに設定
		vo.setAryPfmHumanId(aryPfmHumanId);
		vo.setAryActiveteDate(aryActiveteDate);
		vo.setAryEmployeeKana(aryEmployeeKana);
		vo.setAryEmployeeName(aryEmployeeName);
		vo.setAryWorkPlaceCode(aryWorkPlaceCode);
		vo.setAryWorkPlace(aryWorkPlace);
		vo.setAryEmploymentCode(aryEmploymentCode);
		vo.setAryEmployment(aryEmployment);
		vo.setAryPositionCode(aryPositionCode);
		vo.setAryPosition(aryPosition);
		vo.setArySectionCode(arySectionCode);
		vo.setArySection(arySection);
		// 役職が有効の場合
		if (vo.getNeedPost()) {
			setPost(list);
		}
	}
	
	/**
	 * 役職を設定する。
	 * @param list 社員マスタ履歴情報
	 * @throws MospException 社員情報の取得に失敗した場合
	 */
	private void setPost(List<HumanDtoInterface> list) throws MospException {
		// VO取得
		BasicListVo vo = (BasicListVo)mospParams.getVo();
		// 設定配列準備
		String[] aryPostCode = new String[list.size()];
		String[] aryPost = new String[list.size()];
		// 参照クラス準備
		HumanHistoryReferenceBeanInterface humanHistoryReference = reference().humanHistory();
		NamingReferenceBeanInterface namingReference = reference().naming();
		// フィールド設定
		for (int i = 0; i < list.size(); i++) {
			// 人事情報取得(フィールド設定順序は有効日の昇順)
			HumanDtoInterface dto = list.get(list.size() - 1 - i);
			// 人事汎用履歴情報取得
			HumanHistoryDtoInterface postDto = humanHistoryReference.findForKey(dto.getPersonalId(),
					PlatformConst.NAMING_TYPE_POST, dto.getActivateDate());
			if (postDto == null) {
				continue;
			}
			// 役職名取得
			String namingName = namingReference.getNamingItemName(PlatformConst.NAMING_TYPE_POST,
					postDto.getHumanItemValue(), dto.getActivateDate());
			// 役職名
			aryPostCode[i] = postDto.getHumanItemValue();
			aryPost[i] = namingName;
		}
		// VOに設定
		vo.setAryPostCode(aryPostCode);
		vo.setAryPost(aryPost);
	}
	
}
