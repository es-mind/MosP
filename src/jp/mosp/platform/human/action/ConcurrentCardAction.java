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

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.ConcurrentRegistBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.ConcurrentCardVo;
import jp.mosp.platform.utils.InputCheckUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 社員基本情報の兼務情報の登録・更新・削除を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li></ul>
 */
public class ConcurrentCardAction extends PlatformHumanAction {
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 各レコード毎に有効日が設定されている項目（所属情報、職位情報）に対して
	 * テキストボックスに入力した有効日で検索を行って情報を取得する。<br>
	 * それらの情報から選択可能なレコードのプルダウンリストを作成し、各種検索項目毎にセットする。<br>
	 * 有効日決定後、有効日は編集不可になる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "PF1180";
	
	/**
	 * 検索コマンド。<br>
	 * 社員コード入力欄に入力された社員コードで検索を行い、該当する社員の兼務情報を表示する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 入力した社員コードが存在しない、または入力されていない状態で
	 * 「検索ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_SEARCH				= "PF1182";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 情報入力欄の入力内容を基に人事兼務情報テーブルの更新を行う。<br>
	 * レコードが存在しなければ新規登録し、既存のものがあるなら更新の処理を行う。<br>
	 * 入力チェックを行った際に入力必須項目が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_UPDATE				= "PF1183";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 社員一覧画面で選択された社員の個人IDを基に兼務情報を表示する。<br>
	 */
	public static final String	CMD_SELECT				= "PF1186";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 各レコード毎に用意されたチェックボックスの状態を確認し、
	 * チェックの入っているものは削除するように繰り返し処理を行う。<br>
	 * チェックボックスに1件もチェックが入っていない状態で削除ボタンを
	 * クリックした場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_DELETE				= "PF1187";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER			= "PF1189";
	
	/**
	 * パラメータID(譲渡行No)。
	 */
	public static final String	PRM_TRANSFERRED_ROW_NO	= "transferredRowNo";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public ConcurrentCardAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 更新
			prepareVo();
			update();
		} else if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択表示
			prepareVo(true, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
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
		return new ConcurrentCardVo();
	}
	
	/**
	 * 編集画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 現在の職務情報を設定
		setCurrentWorkInfo();
		// 兼務情報を取得しVOに設定
		setConcurrentInfo();
		// プルダウン設定
		setPulldownList();
		// JavaScript出力項目設定
		setJsValue();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 現在の職務情報を設定
		setCurrentWorkInfo();
		// 兼務情報を取得しVOに設定
		setConcurrentInfo();
		// プルダウン設定
		setPulldownList();
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 入力値確認
		validate();
		// 結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 登録クラス取得
		ConcurrentRegistBeanInterface regist = platform().concurrentRegist();
		// 登録DTOリスト準備
		List<ConcurrentDtoInterface> list = regist.getInitDtoList(vo.getAryHidPfaHumanConcurrentId().length);
		// DTOに値を設定
		setDtoFields(list);
		// 登録処理
		regist.regist(list);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 兼務情報再設定
		setConcurrentInfo();
		// プルダウン設定
		setPulldownList();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合 
	 */
	protected void delete() throws MospException {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 削除対象ID配列取得
		long[] idArray = getIdArray(getIndexArray(vo.getCkbSelect()), vo.getAryHidPfaHumanConcurrentId());
		// 削除対象確認(DB未登録行のみチェックを付けるとidArrayは長さ0)
		if (idArray.length == 0) {
			// 削除成功メッセージを設定
			PfMessageUtility.addMessageDeleteSucceed(mospParams);
			// VOから削除行を除去
			removeDeletedRow();
			// プルダウン設定
			setPulldownList();
			return;
		}
		// 削除処理
		platform().concurrentRegist().delete(idArray);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteSucceed(mospParams);
		// VOから削除行を除去
		removeDeletedRow();
		// プルダウン設定
		setPulldownList();
	}
	
	/**
	 * 有効日モードを設定する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// 行番号取得
		int rowNo = getTransferredRowNo();
		// 有効日モード設定
		setModeActivateDate(rowNo);
		// プルダウンの設定
		setPulldownList();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(HumanInfoAction.class.getName())) {
			// 社員の人事情報をまとめて表示
			mospParams.setNextCommand(HumanInfoAction.CMD_SELECT);
		}
	}
	
	/**
	 * 有効日モードを設定する。
	 * @param rowNo 行No
	 */
	protected void setModeActivateDate(int rowNo) {
		// VO準備
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 有効日モード取得
		String[] modeActivateDateArray = vo.getModeActivateDateArray();
		// 有効日設定行の有効日モードを変更
		if (modeActivateDateArray[rowNo].equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			modeActivateDateArray[rowNo] = PlatformConst.MODE_ACTIVATE_DATE_FIXED;
		} else {
			modeActivateDateArray[rowNo] = PlatformConst.MODE_ACTIVATE_DATE_CHANGING;
		}
		// 有効日モード設定
		vo.setModeActivateDateArray(modeActivateDateArray);
	}
	
	/**
	 * プルダウンリストを設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setPulldownList() throws MospException {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 参照クラス取得
		SectionReferenceBeanInterface section = reference().section();
		PositionReferenceBeanInterface position = reference().position();
		// 操作区分(参照)を準備
		String range = MospConst.OPERATION_TYPE_REFER;
		// プルダウンリスト準備
		List<String[][]> listAryPltSectionAbbr = new ArrayList<String[][]>();
		List<String[][]> listAryPltPosition = new ArrayList<String[][]>();
		// プルダウン取得
		for (int i = 0; i < vo.getAryHidPfaHumanConcurrentId().length; i++) {
			if (vo.getModeActivateDateArray()[i].equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
				// 有効日を基にプルダウン取得
				listAryPltSectionAbbr.add(section.getCodedSelectArray(getConcurrentStartDate(i), false, range));
				listAryPltPosition.add(position.getCodedSelectArray(getConcurrentStartDate(i), false, range));
			} else {
				// プルダウンに有効日指定メッセージを設定
				listAryPltSectionAbbr.add(getInputStartDatePulldown());
				listAryPltPosition.add(getInputStartDatePulldown());
			}
		}
		// リストに設定
		vo.setListAryPltSectionAbbr(listAryPltSectionAbbr);
		vo.setListAryPltPosition(listAryPltPosition);
	}
	
	/**
	 * 現在の職務情報を設定する。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setCurrentWorkInfo() throws MospException {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// 人事基本情報取得(システム日付で取得)
		HumanDtoInterface dto = reference().human().getHumanInfo(vo.getPersonalId(), targetDate);
		if (dto == null) {
			return;
		}
		// 職位名称取得
		vo.setLblPositionName(reference().position().getPositionName(dto.getPositionCode(), targetDate));
		// 経路名称配列取得
		vo.setAryLblClassRoute(reference().section().getClassRouteNameArray(dto.getSectionCode(), targetDate));
	}
	
	/**
	 * 兼務情報を取得し、VOに設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setConcurrentInfo() throws MospException {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 兼務情報を取得しVOに設定
		setVoFields(reference().concurrent().getConcurrentHistory(vo.getPersonalId()));
	}
	
	/**
	 * VOに初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 初期値準備
		String[] aryBlank = { "" };
		Date systemDate = getSystemDate();
		// 初期値設定
		vo.setAryHidPfaHumanConcurrentId(aryBlank);
		vo.setAryTxtConcurrentStartYear(new String[]{ getStringYear(systemDate) });
		vo.setAryTxtConcurrentStartMonth(new String[]{ getStringMonth(systemDate) });
		vo.setAryTxtConcurrentStartDay(new String[]{ getStringDay(systemDate) });
		vo.setAryPosition(aryBlank);
		vo.setArySectionAbbr(aryBlank);
		vo.setAryTxtConcurrentEndYear(aryBlank);
		vo.setAryTxtConcurrentEndMonth(aryBlank);
		vo.setAryTxtConcurrentEndDay(aryBlank);
		vo.setAryTxtRemark(aryBlank);
		vo.setModeActivateDateArray(new String[]{ PlatformConst.MODE_ACTIVATE_DATE_CHANGING });
	}
	
	/**
	 * JavaScript出力値を設定する。<br>
	 */
	protected void setJsValue() {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 値設定
		vo.setJsActivateDateButtonName(PfNameUtility.decision(mospParams));
		vo.setJsDefaultPulldown(PfNameUtility.inputStartDate(mospParams));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list 対象DTOリスト
	 */
	protected void setVoFields(List<ConcurrentDtoInterface> list) {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 兼務情報存在確認
		if (list.isEmpty()) {
			// 初期化
			setDefaultValues();
			return;
		}
		// 設定配列準備
		String[] aryHidPfaHumanConcurrentId = new String[list.size()];
		String[] aryTxtConcurrentStartYear = new String[list.size()];
		String[] aryTxtConcurrentStartMonth = new String[list.size()];
		String[] aryTxtConcurrentStartDay = new String[list.size()];
		String[] aryTxtConcurrentEndYear = new String[list.size()];
		String[] aryTxtConcurrentEndMonth = new String[list.size()];
		String[] aryTxtConcurrentEndDay = new String[list.size()];
		String[] arySectionAbbr = new String[list.size()];
		String[] aryPosition = new String[list.size()];
		String[] aryTxtRemark = new String[list.size()];
		String[] aryModeActivateDate = new String[list.size()];
		// 配列に値を設定
		for (int i = 0; i < list.size(); i++) {
			// DTO取得
			ConcurrentDtoInterface dto = list.get(i);
			// 開始日
			aryTxtConcurrentStartYear[i] = getStringYear(dto.getStartDate());
			aryTxtConcurrentStartMonth[i] = getStringMonth(dto.getStartDate());
			aryTxtConcurrentStartDay[i] = getStringDay(dto.getStartDate());
			// 終了日
			aryTxtConcurrentEndYear[i] = getStringYear(dto.getEndDate());
			aryTxtConcurrentEndMonth[i] = getStringMonth(dto.getEndDate());
			aryTxtConcurrentEndDay[i] = getStringDay(dto.getEndDate());
			// 所属
			arySectionAbbr[i] = dto.getSectionCode();
			// 職位
			aryPosition[i] = dto.getPositionCode();
			// 備考
			aryTxtRemark[i] = dto.getConcurrentRemark();
			// 識別ID
			aryHidPfaHumanConcurrentId[i] = String.valueOf(dto.getPfaHumanConcurrentId());
			// 有効日モード
			aryModeActivateDate[i] = PlatformConst.MODE_ACTIVATE_DATE_FIXED;
			
		}
		// 配列をVOに設定
		vo.setAryHidPfaHumanConcurrentId(aryHidPfaHumanConcurrentId);
		vo.setAryTxtConcurrentStartYear(aryTxtConcurrentStartYear);
		vo.setAryTxtConcurrentStartMonth(aryTxtConcurrentStartMonth);
		vo.setAryTxtConcurrentStartDay(aryTxtConcurrentStartDay);
		vo.setAryTxtConcurrentEndYear(aryTxtConcurrentEndYear);
		vo.setAryTxtConcurrentEndMonth(aryTxtConcurrentEndMonth);
		vo.setAryTxtConcurrentEndDay(aryTxtConcurrentEndDay);
		vo.setArySectionAbbr(arySectionAbbr);
		vo.setAryPosition(aryPosition);
		vo.setAryTxtRemark(aryTxtRemark);
		vo.setModeActivateDateArray(aryModeActivateDate);
	}
	
	/**
	 * VO(編集項目)の値をDTO(人事兼務情報)に設定する。<br>
	 * @param list 兼務情報DTOリスト
	 */
	protected void setDtoFields(List<ConcurrentDtoInterface> list) {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// リスト内DTOに値を設定
		for (int i = 0; i < list.size(); i++) {
			// DTO取得
			ConcurrentDtoInterface dto = list.get(i);
			// レコードID設定(追加行はレコードIDが空白)
			if (vo.getAryHidPfaHumanConcurrentId()[i].isEmpty()) {
				// 空白なら0を設定
				dto.setPfaHumanConcurrentId(0L);
			} else {
				dto.setPfaHumanConcurrentId(getLong(vo.getAryHidPfaHumanConcurrentId()[i]));
			}
			// 値設定
			dto.setPersonalId(vo.getPersonalId());
			dto.setStartDate(getConcurrentStartDate(i));
			dto.setEndDate(getConcurrentEndDate(i));
			dto.setSectionCode(vo.getArySectionAbbr()[i]);
			dto.setPositionCode(vo.getAryPosition()[i]);
			dto.setConcurrentRemark(vo.getAryTxtRemark()[i]);
		}
	}
	
	/**
	 * VOから削除行を除去する。<br>
	 */
	protected void removeDeletedRow() {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 削除対象インデックス配列取得
		int[] deletedRows = getIndexArray(vo.getCkbSelect());
		// 配列長取得
		int length = vo.getAryHidPfaHumanConcurrentId().length - deletedRows.length;
		// 配列長確認
		if (length <= 0) {
			// 初期化
			setDefaultValues();
			return;
		}
		// 設定配列準備
		String[] aryHidPfaHumanConcurrentId = new String[length];
		String[] aryTxtConcurrentStartYear = new String[length];
		String[] aryTxtConcurrentStartMonth = new String[length];
		String[] aryTxtConcurrentStartDay = new String[length];
		String[] aryTxtConcurrentEndYear = new String[length];
		String[] aryTxtConcurrentEndMonth = new String[length];
		String[] aryTxtConcurrentEndDay = new String[length];
		String[] arySectionAbbr = new String[length];
		String[] aryPosition = new String[length];
		String[] aryTxtRemark = new String[length];
		String[] aryModeActivateDate = new String[length];
		// 配列に値を設定
		int idx = 0;
		for (int i = 0; i < vo.getAryHidPfaHumanConcurrentId().length; i++) {
			// 削除行確認
			if (isIndexed(i, deletedRows)) {
				continue;
			}
			// 開始日
			aryTxtConcurrentStartYear[idx] = vo.getAryTxtConcurrentStartYear()[i];
			aryTxtConcurrentStartMonth[idx] = vo.getAryTxtConcurrentStartMonth()[i];
			aryTxtConcurrentStartDay[idx] = vo.getAryTxtConcurrentStartDay()[i];
			// 終了日
			aryTxtConcurrentEndYear[idx] = vo.getAryTxtConcurrentEndYear()[i];
			aryTxtConcurrentEndMonth[idx] = vo.getAryTxtConcurrentEndMonth()[i];
			aryTxtConcurrentEndDay[idx] = vo.getAryTxtConcurrentEndDay()[i];
			// 所属
			arySectionAbbr[idx] = vo.getArySectionAbbr()[i];
			// 職位
			aryPosition[idx] = vo.getAryPosition()[i];
			// 備考
			aryTxtRemark[idx] = vo.getAryTxtRemark()[i];
			// 識別ID
			aryHidPfaHumanConcurrentId[idx] = vo.getAryHidPfaHumanConcurrentId()[i];
			// 有効日モード(追加行分は存在しないため長さチェックを実施)
			if (i < vo.getModeActivateDateArray().length) {
				aryModeActivateDate[idx++] = vo.getModeActivateDateArray()[i];
			} else {
				// 追加行で有効日を変更していないものは、変更モード
				aryModeActivateDate[idx++] = PlatformConst.MODE_ACTIVATE_DATE_CHANGING;
			}
		}
		// 配列をVOに設定
		vo.setAryHidPfaHumanConcurrentId(aryHidPfaHumanConcurrentId);
		vo.setAryTxtConcurrentStartYear(aryTxtConcurrentStartYear);
		vo.setAryTxtConcurrentStartMonth(aryTxtConcurrentStartMonth);
		vo.setAryTxtConcurrentStartDay(aryTxtConcurrentStartDay);
		vo.setAryTxtConcurrentEndYear(aryTxtConcurrentEndYear);
		vo.setAryTxtConcurrentEndMonth(aryTxtConcurrentEndMonth);
		vo.setAryTxtConcurrentEndDay(aryTxtConcurrentEndDay);
		vo.setArySectionAbbr(arySectionAbbr);
		vo.setAryPosition(aryPosition);
		vo.setAryTxtRemark(aryTxtRemark);
		vo.setModeActivateDateArray(aryModeActivateDate);
	}
	
	/**
	 * 入力値妥当性確認。
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void validate() throws MospException {
		// VO準備
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 「兼務開始日」名称取得
		String startDateName = PfNameUtility.concurrentStartDate(mospParams);
		// 「兼務終了日」名称取得
		String endDateName = PfNameUtility.concurrentEndDate(mospParams);
		for (int i = 0; i < vo.getAryTxtConcurrentStartYear().length; i++) {
			// 必須確認
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtConcurrentStartYear()[i], startDateName);
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtConcurrentStartMonth()[i], startDateName);
			InputCheckUtility.checkRequired(mospParams, vo.getAryTxtConcurrentStartDay()[i], startDateName);
			// 数値確認
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtConcurrentStartYear()[i], startDateName);
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtConcurrentStartMonth()[i], startDateName);
			InputCheckUtility.checkNumber(mospParams, vo.getAryTxtConcurrentStartDay()[i], startDateName);
			// 項目長チェック
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtConcurrentStartYear()[i], 4, startDateName);
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtConcurrentStartMonth()[i], 2, startDateName);
			InputCheckUtility.checkLength(mospParams, vo.getAryTxtConcurrentStartDay()[i], 2, startDateName);
			// 終了日が入力されていた場合
			if ((vo.getAryTxtConcurrentEndYear()[i] != null && !vo.getAryTxtConcurrentEndYear()[i].isEmpty())
					|| (vo.getAryTxtConcurrentEndMonth()[i] != null && !vo.getAryTxtConcurrentEndMonth()[i].isEmpty())
					|| (vo.getAryTxtConcurrentEndDay()[i] != null && !vo.getAryTxtConcurrentEndDay()[i].isEmpty())) {
				// 必須確認
				InputCheckUtility.checkRequired(mospParams, vo.getAryTxtConcurrentEndYear()[i], endDateName);
				InputCheckUtility.checkRequired(mospParams, vo.getAryTxtConcurrentEndMonth()[i], endDateName);
				InputCheckUtility.checkRequired(mospParams, vo.getAryTxtConcurrentEndDay()[i], endDateName);
				// 数値確認
				InputCheckUtility.checkNumber(mospParams, vo.getAryTxtConcurrentEndYear()[i], endDateName);
				InputCheckUtility.checkNumber(mospParams, vo.getAryTxtConcurrentEndMonth()[i], endDateName);
				InputCheckUtility.checkNumber(mospParams, vo.getAryTxtConcurrentEndDay()[i], endDateName);
				// 項目長チェック
				InputCheckUtility.checkLength(mospParams, vo.getAryTxtConcurrentEndYear()[i], 4, endDateName);
				InputCheckUtility.checkLength(mospParams, vo.getAryTxtConcurrentEndMonth()[i], 2, endDateName);
				InputCheckUtility.checkLength(mospParams, vo.getAryTxtConcurrentEndDay()[i], 2, endDateName);
				// 日付期間妥当性確認
				// 開始日
				Date startDate = getDate(vo.getAryTxtConcurrentStartYear()[i], vo.getAryTxtConcurrentStartMonth()[i],
						vo.getAryTxtConcurrentStartDay()[i]);
				// 終了日
				Date endDate = getDate(vo.getAryTxtConcurrentEndYear()[i], vo.getAryTxtConcurrentEndMonth()[i],
						vo.getAryTxtConcurrentEndDay()[i]);
				// 終了日妥当性チェック
				InputCheckUtility.checkDateOrder(mospParams, startDate, endDate, startDateName, endDateName);
			}
		}
	}
	
	/**
	 * VOから兼務開始日を取得する。<br>
	 * @param idx インデックス
	 * @return 兼務開始日
	 */
	protected Date getConcurrentStartDate(int idx) {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getAryTxtConcurrentStartYear()[idx], vo.getAryTxtConcurrentStartMonth()[idx],
				vo.getAryTxtConcurrentStartDay()[idx]);
	}
	
	/**
	 * VOから兼務終了日を取得する。<br>
	 * @param idx インデックス
	 * @return 兼務終了日
	 */
	protected Date getConcurrentEndDate(int idx) {
		// VO取得
		ConcurrentCardVo vo = (ConcurrentCardVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getAryTxtConcurrentEndYear()[idx], vo.getAryTxtConcurrentEndMonth()[idx],
				vo.getAryTxtConcurrentEndDay()[idx]);
	}
	
	/**
	 * リクエストされた譲渡行Noを取得する。
	 * @return 譲渡行No
	 */
	protected int getTransferredRowNo() {
		int retInt = 0;
		if (mospParams.getRequestParam(PRM_TRANSFERRED_ROW_NO) != null) {
			retInt = Integer.parseInt(mospParams.getRequestParam(PRM_TRANSFERRED_ROW_NO));
		}
		return retInt;
	}
	
}
