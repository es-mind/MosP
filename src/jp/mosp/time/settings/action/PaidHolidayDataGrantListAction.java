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
package jp.mosp.time.settings.action;

import java.text.Format;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayDataGrantBeanInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataGrantListDtoInterface;
import jp.mosp.time.settings.vo.PaidHolidayDataGrantListVo;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 有給休暇データの検索、付与を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_OUTPUT}
 * </li><li>
 * {@link #CMD_CALC1}
 * </li><li>
 * {@link #CMD_CALC2}
 * </li><li>
 * {@link #CMD_OTHER_BATCH_UPDATE1}
 * </li><li>
 * {@link #CMD_OTHER_BATCH_UPDATE2}
 * </li></ul>
 */
public class PaidHolidayDataGrantListAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String		CMD_SHOW				= "TM040900";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に有給休暇データの検索を行う。
	 */
	public static final String		CMD_SEARCH				= "TM040902";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 一度この画面を表示した後、パンくずリスト等を用いて他の画面から改めて遷移した場合、
	 * 各種情報の登録状況を更新した上で保持していた検索条件で検索を行って画面を再表示する。
	 */
	public static final String		CMD_RE_SHOW				= "TM040903";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っているレコードの有給休暇付与処理を繰り返し行う。<br>
	 * ひとつもチェックが入っていない状態でボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。<br>
	 */
	public static final String		CMD_BATCH_UPDATE		= "TM040906";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。
	 */
	public static final String		CMD_SORT				= "TM040907";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String		CMD_PAGE				= "TM040908";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 決定した有効日時点で有効な勤務地、雇用契約、所属、職位、有休設定のレコードを取得し、名称を各プルダウンで表示する。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE	= "TM040909";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String		CMD_TRANSFER			= "TM040920";
	
	/**
	 * 有給休暇取得状況確認情報出力コマンド。<br>
	 * <br>
	 * 検索一覧に表示された個々人の付与日を基準とし、その基準日からの1年間を対象として出力する。<br>
	 */
	public static final String		CMD_OUTPUT				= "TM040921";
	
	/**
	 * 計算1コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っているレコードの出勤率計算を繰り返し行う。<br>
	 * ひとつもチェックが入っていない状態でボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。<br>
	 */
	public static final String		CMD_CALC1				= "TM040926";
	
	/**
	 * 計算2コマンド。<br>
	 * <br>
	 * 一覧に表示されているレコードの出勤率を計算する。<br>
	 */
	public static final String		CMD_CALC2				= "TM040927";
	
	/**
	 * その他一括処理1コマンド。<br>
	 * <br>
	 * 一覧に表示されているレコードの有給休暇付与処理を繰り返し行う。<br>
	 * 出勤率基準が未達成の場合は処理を行わない。
	 */
	public static final String		CMD_OTHER_BATCH_UPDATE1	= "TM040936";
	
	/**
	 * その他一括処理2コマンド。<br>
	 * <br>
	 * 一覧に表示されているレコードのストック休暇付与処理を繰り返し行う。<br>
	 */
	public static final String		CMD_OTHER_BATCH_UPDATE2	= "TM040937";
	
	/**
	 * パラメータID(選択チェックボックス)。<br>
	 */
	protected static final String	PRM_CKB_SELECT			= "ckbSelect";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public PaidHolidayDataGrantListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PaidHolidayDataGrantListVo();
	}
	
	/**
	 * 選択チェックボックスが選択されなかった場合に、
	 * VOのchkSelectの値を初期化する。<br>
	 */
	@Override
	protected BaseVo prepareVo(boolean useStoredVo, boolean useParametersMapper) throws MospException {
		// 継承元のメソッドを実行
		super.prepareVo(useStoredVo, useParametersMapper);
		// パラメータをVOにマッピングしない場合
		if (useParametersMapper == false) {
			return mospParams.getVo();
		}
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// パラメータとしてchkSelectが送られてこなかった場合
		if (mospParams.getRequestParam(PRM_CKB_SELECT) == null) {
			// 一覧選択情報を初期化
			initCkbSelect();
		}
		return vo;
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
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括付与
			prepareVo();
			batchUpdate();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定コマンド
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_OUTPUT)) {
			// 出力
			prepareVo();
			output();
		} else if (mospParams.getCommand().equals(CMD_CALC1)) {
			// 計算1
			prepareVo();
			calc1();
		} else if (mospParams.getCommand().equals(CMD_CALC2)) {
			// 計算2
			prepareVo();
			calc2();
		} else if (mospParams.getCommand().equals(CMD_OTHER_BATCH_UPDATE1)) {
			// その他一括処理1
			prepareVo();
			otherBatchUpdate1();
		} else if (mospParams.getCommand().equals(CMD_OTHER_BATCH_UPDATE2)) {
			// その他一括処理2
			prepareVo();
			otherBatchUpdate2();
		} else {
			throwInvalidCommandException();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void search() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 検索クラス取得
		PaidHolidayDataSearchBeanInterface search = getSearchBean();
		List<PaidHolidayDataGrantListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
		// 出勤率計算確認フラグ
		vo.setJsCalcAttendanceRate(MospConst.STR_EMPTY);
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void batchUpdate() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		PaidHolidayDataGrantBeanInterface paidHolidayDataGrant = time().paidHolidayDataGrant();
		for (String index : vo.getCkbSelect()) {
			int i = Integer.parseInt(index);
			// 達成でない場合
			if (MospUtility.isEqual(vo.getAryLblAccomplish(i), TimeNamingUtility.accomplish(mospParams)) == false) {
				continue;
			}
			// 付与
			paidHolidayDataGrant.grant(vo.getAryPersonalId(i), getDate(vo.getAryLblGrantDate(i)));
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
		}
		// コミット
		commit();
		// 付与成功メッセージ設定
		addGrantMessage();
		// 計算
		calc();
	}
	
	/**
	 * 一覧のソート処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void sort() throws MospException {
		setVoList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * 一覧のページ処理を行う。<br>
	 */
	protected void page() {
		setVoList(pageList());
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		if (PlatformConst.MODE_ACTIVATE_DATE_CHANGING.equals(vo.getModeActivateDate())) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン設定
		setPulldown();
		List<BaseDtoInterface> list = Collections.emptyList();
		vo.setList(list);
		setVoList(list);
	}
	
	/**
	 * 連続実行コマンドを設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void transfer() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		int index = getTransferredIndex();
		setTargetPersonalId(vo.getAryPersonalId(index));
		setTargetDate(getDate(vo.getAryLblGrantDate(index)));
		mospParams.setNextCommand(PaidHolidayDataGrantCardAction.CMD_SELECT_SHOW);
	}
	
	/**
	 * 有給休暇取得状況確認情報を出力する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	@SuppressWarnings("unchecked")
	protected void output() throws MospException {
		// VOを取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// VOから有給休暇付与一覧情報リストを取得
		List<PaidHolidayDataGrantListDtoInterface> list = (List<PaidHolidayDataGrantListDtoInterface>)vo.getList();
		// 個人ID配列を取得
		String[] presonalIds = MospUtility.toArray(PlatformUtility.getPersonalIdSet(list));
		// 有給休暇取得状況確認情報を出力
		timeReference().paidHolidayUsageExport().export(presonalIds, getSearchActivateDate());
	}
	
	/**
	 * 出勤率選択計算を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void calc1() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 計算
		calc();
		// 出勤率未計算設定
		vo.setJsCalcAttendanceRate(Boolean.toString(true));
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// コミット
		commit();
	}
	
	/**
	 * 出勤率一括計算を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void calc2() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 検索クラス取得
		PaidHolidayDataSearchBeanInterface search = getSearchBean();
		// VOの値を検索クラスへ設定
		search.setCalcAttendanceRate(true);
		List<PaidHolidayDataGrantListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
		// 出勤率未計算設定
		vo.setJsCalcAttendanceRate(Boolean.toString(true));
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// コミット
		commit();
	}
	
	/**
	 * 有給休暇一括付与処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void otherBatchUpdate1() throws MospException {
		// VOを準備
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 有給休暇付与処理を準備
		PaidHolidayDataGrantBeanInterface paidHolidayDataGrant = time().paidHolidayDataGrant();
		// 検索対象個人IDを準備
		Set<String> personalIds = new TreeSet<String>();
		// 有給休暇一覧情報(画面に表示されていない分も含めて)毎に処理
		for (BaseDtoInterface baseDto : vo.getList()) {
			// 有給休暇一覧情報を取得
			PaidHolidayDataGrantListDtoInterface dto = MospUtility.castObject(baseDto);
			// 達成でない場合
			if (MospUtility.isEqual(dto.getAccomplish(), TimeNamingUtility.accomplish(mospParams)) == false) {
				// 次の有給休暇一覧情報へ
				continue;
			}
			// 付与
			paidHolidayDataGrant.grant(dto.getPersonalId(), dto.getGrantDate());
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
			// 付与した社員を設定
			personalIds.add(dto.getPersonalId());
		}
		// コミット
		commit();
		// 付与した社員がいる場合
		if (MospUtility.isEmpty(personalIds) == false) {
			// 付与成功メッセージ設定
			addGrantMessage();
		}
		// 計算
		registedSearch(personalIds);
	}
	
	/**
	 * ストック休暇一括付与処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void otherBatchUpdate2() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		StockHolidayDataGrantBeanInterface stockHolidayDataGrant = time().stockHolidayDataGrant();
		Date searchActivateDate = getSearchActivateDate();
		for (String personalId : vo.getAryPersonalId()) {
			// 付与
			stockHolidayDataGrant.grant(personalId, searchActivateDate);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージを設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				return;
			}
		}
		// コミット
		commit();
		// 新規追加成功メッセージ設定
		addGrantMessage();
		// 検索
		search();
	}
	
	/**
	 * 計算処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void calc() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 個人IDセット準備
		Set<String> set = new HashSet<String>();
		// インデックス配列取得
		String[] aryIndex = vo.getCkbSelect();
		// インデックス毎に処理
		for (String index : aryIndex) {
			// 個人IDセットに設定
			set.add(vo.getAryPersonalId(MospUtility.getInt(index)));
		}
		// 検索
		registedSearch(set);
	}
	
	/**
	 * 対象個人IDセットの検索処理を行う。
	 * 付与した後に付与した社員のみ検索する。
	 * @param personalIdSet 対象個人ID
	 * @throws MospException 例外発生時
	 */
	protected void registedSearch(Set<String> personalIdSet) throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 検索クラス取得
		PaidHolidayDataSearchBeanInterface search = getSearchBean();
		// 検索条件設定
		search.setCalcAttendanceRate(true);
		search.setPersonalIdSet(personalIdSet);
		// 検索
		List<PaidHolidayDataGrantListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeCodeComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		Date date = getSystemDate();
		// 検索項目設定
		vo.setTxtSearchActivateYear(getStringYear(date));
		vo.setTxtSearchActivateMonth(getStringMonth(date));
		vo.setTxtSearchActivateDay(getStringDay(date));
		vo.setTxtSearchEntrance(MospConst.STR_EMPTY);
		vo.setTxtSearchEntranceMonth(MospConst.STR_EMPTY);
		vo.setTxtSearchEntranceDay(MospConst.STR_EMPTY);
		vo.setTxtSearchEmployeeCode(MospConst.STR_EMPTY);
		vo.setTxtSearchEmployeeName(MospConst.STR_EMPTY);
		vo.setPltSearchWorkPlace(MospConst.STR_EMPTY);
		vo.setPltSearchEmployment(MospConst.STR_EMPTY);
		vo.setPltSearchSection(MospConst.STR_EMPTY);
		vo.setPltSearchPosition(MospConst.STR_EMPTY);
		vo.setPltSearchPaidHoliday(MospConst.STR_EMPTY);
		vo.setPltSearchGrant(MospConst.STR_EMPTY);
		// 出勤率計算確認フラグ
		vo.setJsCalcAttendanceRate(MospConst.STR_EMPTY);
		// 検索項目必須設定
		vo.setJsSearchConditionRequired(isSearchConditionRequired());
	}
	
	/**
	 * プルダウン設定。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		WorkPlaceReferenceBeanInterface workPlace = reference().workPlace();
		EmploymentContractReferenceBeanInterface employmentContract = reference().employmentContract();
		SectionReferenceBeanInterface section = reference().section();
		PositionReferenceBeanInterface position = reference().position();
		PaidHolidayReferenceBeanInterface paidHoliday = timeReference().paidHoliday();
		if (PlatformConst.MODE_ACTIVATE_DATE_FIXED.equals(vo.getModeActivateDate())) {
			Date searchActivateDate = getSearchActivateDate();
			// プルダウン設定
			vo.setAryPltSearchWorkPlace(workPlace.getCodedSelectArray(searchActivateDate, true, null));
			vo.setAryPltSearchEmployment(employmentContract.getCodedSelectArray(searchActivateDate, true, null));
			vo.setAryPltSearchSection(section.getCodedSelectArray(searchActivateDate, true, null));
			vo.setAryPltSearchPosition(position.getCodedSelectArray(searchActivateDate, true, null));
			vo.setAryPltSearchPaidHoliday(paidHoliday.getCodedSelectArray(searchActivateDate, true));
			return;
		}
		String[][] inputActivateDatePulldown = getInputActivateDatePulldown();
		// プルダウン設定
		vo.setAryPltSearchWorkPlace(inputActivateDatePulldown);
		vo.setAryPltSearchEmployment(inputActivateDatePulldown);
		vo.setAryPltSearchSection(inputActivateDatePulldown);
		vo.setAryPltSearchPosition(inputActivateDatePulldown);
		vo.setAryPltSearchPaidHoliday(inputActivateDatePulldown);
	}
	
	/**
	 * 入社日検索対象期間初日を取得する。<br>
	 * 年のみの場合は年度の初日、年月は基準日の初日を取得する。<br>
	 * @return 検索対象入社日期間自
	 * @throws MospException 日付の取得に失敗した場合
	 */
	protected Date getSearchEntranceFromDate() throws MospException {
		// VO準備
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 年月日取得
		String year = vo.getTxtSearchEntrance();
		String month = vo.getTxtSearchEntranceMonth();
		String day = vo.getTxtSearchEntranceDay();
		// 年月日がある場合
		if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {
			return getDate(year, month, day);
		}
		// 年月がある場合
		if (!year.isEmpty() && !month.isEmpty()) {
			return MonthUtility.getYearMonthTermFirstDate(getInt(year), getInt(month), mospParams);
		}
		// 年月日がない場合
		if (year.isEmpty() && month.isEmpty() && day.isEmpty()) {
			return null;
		}
		// 年がある場合(検索年の初日を取得)
		return MonthUtility.getYearMonthTermFirstDate(getInt(year), 1, mospParams);
	}
	
	/**
	 * 入社日検索対象期間末日を取得する。<br>
	 * 年のみの場合は年度の末日、年月は基準日の末日を取得する。<br>
	 * @return 検索対象入社日期間至
	 * @throws MospException 日付の取得に失敗した場合
	 */
	protected Date getSearchEntranceToDate() throws MospException {
		// VO準備
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 年月日取得
		String year = vo.getTxtSearchEntrance();
		String month = vo.getTxtSearchEntranceMonth();
		String day = vo.getTxtSearchEntranceDay();
		// 年月日がある場合
		if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {
			return getDate(year, month, day);
		}
		// 年月がある場合
		if (!year.isEmpty() && !month.isEmpty()) {
			// 年月の末日を取得
			return MonthUtility.getYearMonthTermLastDate(getInt(year), getInt(month), mospParams);
		}
		// 年月日がない場合
		if (year.isEmpty() && month.isEmpty() && day.isEmpty()) {
			return null;
		}
		// 年がある場合(検索年の最終日を取得)
		return MonthUtility.getYearMonthTermLastDate(getInt(year), 12, mospParams);
	}
	
	/**
	 * 検索クラスを取得する。<br>
	 * @return 検索クラス
	 * @throws MospException 例外発生時
	 */
	protected PaidHolidayDataSearchBeanInterface getSearchBean() throws MospException {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// 検索クラス取得
		PaidHolidayDataSearchBeanInterface search = timeReference().paidHolidayDataSearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setEntranceFromDate(getSearchEntranceFromDate());
		search.setEntranceToDate(getSearchEntranceToDate());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setWorkPlaceCode(vo.getPltSearchWorkPlace());
		search.setEmploymentCode(vo.getPltSearchEmployment());
		search.setSectionCode(vo.getPltSearchSection());
		search.setPositionCode(vo.getPltSearchPosition());
		search.setPaidHolidayCode(vo.getPltSearchPaidHoliday());
		search.setGrant(vo.getPltSearchGrant());
		search.setCalcAttendanceRate(false);
		return search;
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) {
		// VO取得
		PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblAttendanceRate = new String[list.size()];
		String[] aryLblAccomplish = new String[list.size()];
		String[] aryLblGrant = new String[list.size()];
		String[] aryLblGrantDate = new String[list.size()];
		String[] aryLblGrantDays = new String[list.size()];
		String[] aryPersonalId = new String[list.size()];
		Format format = getPercentFormat(1);
		for (int i = 0; i < list.size(); i++) {
			PaidHolidayDataGrantListDtoInterface dto = (PaidHolidayDataGrantListDtoInterface)list.get(i);
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblAttendanceRate[i] = getAttendanceRate(dto, format);
			aryLblAccomplish[i] = dto.getAccomplish();
			aryLblGrant[i] = dto.getGrant();
			aryLblGrantDate[i] = getGrantDate(dto);
			aryLblGrantDays[i] = getGrantDays(dto);
			aryPersonalId[i] = dto.getPersonalId();
		}
		// データをVOに設定
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblAttendanceRate(aryLblAttendanceRate);
		vo.setAryLblAccomplish(aryLblAccomplish);
		vo.setAryLblGrant(aryLblGrant);
		vo.setAryLblGrantDate(aryLblGrantDate);
		vo.setAryLblGrantDays(aryLblGrantDays);
		vo.setAryPersonalId(aryPersonalId);
	}
	
	/**
	 * 出勤率を取得する。<br>
	 * @param dto 対象DTO
	 * @param format フォーマット
	 * @return 出勤率
	 */
	protected String getAttendanceRate(PaidHolidayDataGrantListDtoInterface dto, Format format) {
		return getAttendanceRate(dto.getAttendanceRate(), format);
	}
	
	/**
	 * 出勤率を取得する。<br>
	 * @param attendanceRate 出勤率
	 * @param format フォーマット
	 * @return 出勤率
	 */
	protected String getAttendanceRate(Double attendanceRate, Format format) {
		if (MospUtility.isEmpty(attendanceRate)) {
			return PfNameUtility.hyphen(mospParams);
		}
		return format.format(attendanceRate);
	}
	
	/**
	 * 付与日付を取得する。<br>
	 * @param dto 対象DTO
	 * @return 付与日付
	 */
	protected String getGrantDate(PaidHolidayDataGrantListDtoInterface dto) {
		if (MospUtility.isEmpty(dto.getGrantDate())) {
			return PfNameUtility.hyphen(mospParams);
		}
		return getStringDateAndDay(dto.getGrantDate());
	}
	
	/**
	 * 付与日数を取得する。<br>
	 * @param dto 対象DTO
	 * @return 付与日数
	 */
	protected String getGrantDays(PaidHolidayDataGrantListDtoInterface dto) {
		return getGrantDays(dto.getGrantDays());
	}
	
	/**
	 * 付与日数を取得する。<br>
	 * @param grantDays 付与日数
	 * @return 付与日数
	 */
	protected String getGrantDays(Double grantDays) {
		if (MospUtility.isEmpty(grantDays)) {
			return PfNameUtility.hyphen(mospParams);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(grantDays.toString());
		sb.append(PfNameUtility.day(mospParams));
		return sb.toString();
	}
	
	/**
	 * 付与成功メッセージの設定。
	 */
	protected void addGrantMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, TimeNamingUtility.giving(mospParams));
	}
	
}
