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
package jp.mosp.time.input.action;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestExecuteBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.HolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.comparator.settings.HolidayRequestRequestDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestListDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.input.vo.HolidayRequestVo;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇申請情報の確認と編集を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_DRAFT}
 * </li><li>
 * {@link #CMD_APPLI}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_WITHDRAWN}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_BATCH_WITHDRAWN}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_EDIT_MODE}
 * </li><li>
 * {@link #CMD_BATCH_UPDATE}
 * </li><li>
 * {@link #CMD_SET_VIEW_PERIOD}
 * </li><li>
 * {@link #CMD_SET_TRANSFER_HOLIDAY}
 * </li><li>
 * {@link #CMD_SELECT_ACTIVATION_DATE}
 * </li></ul>
 */
public class HolidayRequestAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの休暇申請画面を表示する。<br>
	 */
	public static final String		CMD_SHOW					= "TM1500";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 休暇申請画面を表示する。<br>
	 */
	public static final String		CMD_SELECT_SHOW				= "TM1501";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を元に休暇申請情報の検索を行う。<br>
	 */
	public static final String		CMD_SEARCH					= "TM1502";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たな下書情報作成や申請を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String		CMD_RE_SHOW					= "TM1503";
	
	/**
	 * 下書コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を休暇情報テーブルに登録し、下書情報として保存する。<br>
	 */
	public static final String		CMD_DRAFT					= "TM1504";
	
	/**
	 * 申請コマンド。<br>
	 * <br>
	 * 申請欄に入力した内容を休暇情報テーブルに登録し、休暇申請を行う。以降、このレコードは上長が差戻をしない限り編集不可となる。<br>
	 * 休暇の申請可能時間を超過している、申請年月日で申請不可な日付が選択されている、<br>
	 * 申請時間が0時間0分のまま、休暇理由の項目が入力されていない、<br>
	 * といった状態で申請を行おうとした場合はエラーメッセージにて通知し、申請は実行されない。<br>
	 */
	public static final String		CMD_APPLI					= "TM1505";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 */
	public static final String		CMD_TRANSFER				= "TM1506";
	
	/**
	 * 取下コマンド。<br>
	 * <br>
	 * 下書状態または差戻状態で登録されていたレコードの取下を行う。<br>
	 * 取下後、対象の休暇申請情報は未申請状態へ戻る。<br>
	 */
	public static final String		CMD_WITHDRAWN				= "TM1507";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String		CMD_SORT					= "TM1508";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String		CMD_PAGE					= "TM1509";
	
	/**
	 * 一括取下コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている未承認状態のレコードの取下処理を繰り返し行う。
	 * ひとつもチェックが入っていない状態で一括取下ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。
	 */
	public static final String		CMD_BATCH_WITHDRAWN			= "TM1536";
	
	/**
	 * 休暇年月日決定コマンド。<br>
	 * <br>
	 * 休暇年月日入力欄に入力されている日付を元にログイン中のユーザが取得可能な休暇種別を検索、各プルダウンに表示させる。<br>
	 */
	public static final String		CMD_SET_ACTIVATION_DATE		= "TM1590";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 申請テーブルの各入力欄に表示されているレコード内容をクリアにする。<br>
	 * 申請テーブルヘッダに表示されている新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String		CMD_INSERT_MODE				= "TM1591";
	
	/**
	 * 編集モード切替コマンド。<br>
	 * <br>
	 * 選択したレコードの内容を申請テーブルの各入力欄にそれぞれ表示させる。<br>
	 * 申請テーブルヘッダに新規登録モード切替リンクを表示させる。
	 */
	public static final String		CMD_EDIT_MODE				= "TM1592";
	
	/**
	 * 一括更新コマンド。<br>
	 * <br>
	 * 一覧にて選択チェックボックスにチェックが入っている下書状態のレコードの申請処理を繰り返し行う。<br>
	 * ひとつもチェックが入っていない状態で一括更新ボタンをクリックした場合はエラーメッセージにて通知し、処理を中断する。<br>
	 */
	public static final String		CMD_BATCH_UPDATE			= "TM1595";
	
	/**
	 * 表示期間決定コマンド。<br>
	 * <br>
	 * 入力した締期間で検索を行い、残日数のある休暇区分と休暇種別を取得し、プルダウンに表示する。<br>
	 */
	public static final String		CMD_SET_VIEW_PERIOD			= "TM1597";
	
	/**
	 * 休暇種別決定コマンド。<br>
	 * <br>
	 * 休暇情報の連続取得判定を行うために休暇種別プルダウン切替時にその休暇の連続取得区分が"必須/警告/不要"の内どれであるかを判断する。<br>
	 */
	public static final String		CMD_SET_TRANSFER_HOLIDAY	= "TM1598";
	
	/**
	 * 休暇年月日決定コマンド。<br>
	 * <br>
	 * 休暇年月日入力欄に入力されている日付を元にログイン中のユーザが取得可能な休暇種別を検索、各プルダウンに表示させる。<br>
	 */
	public static final String		CMD_SELECT_ACTIVATION_DATE	= "TM1599";
	
	/**
	 * 機能コード。<br>
	 */
	protected static final String	CODE_FUNCTION				= TimeConst.CODE_FUNCTION_VACATION;
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public HolidayRequestAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HolidayRequestVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_DRAFT)) {
			// 下書き
			prepareVo();
			draft();
		} else if (mospParams.getCommand().equals(CMD_APPLI)) {
			// 申請
			prepareVo();
			appli();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_WITHDRAWN)) {
			// 取下
			prepareVo();
			withdrawn();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_BATCH_WITHDRAWN)) {
			// 一括取下
			prepareVo();
			batchWithdrawn();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 休暇年月日
			prepareVo();
			setEditActivationDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_EDIT_MODE)) {
			// 編集モード切替
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_BATCH_UPDATE)) {
			// 一括更新
			prepareVo();
			batchUpdate();
		} else if (mospParams.getCommand().equals(CMD_SET_VIEW_PERIOD)) {
			// 検索表示期間
			prepareVo();
			setSearchActivationDate();
		} else if (mospParams.getCommand().equals(CMD_SET_TRANSFER_HOLIDAY)) {
			//
			prepareVo();
			setHolidayContinue();
		} else if (mospParams.getCommand().equals(CMD_SELECT_ACTIVATION_DATE)) {
			// 休暇年月日
			prepareVo(false, false);
			selectActivationDate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// 個人ID取得(ログインユーザ情報から)
		String personalId = mospParams.getUser().getPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 新規登録モード設定
		insertMode();
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditStartDate(), CODE_FUNCTION);
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// 個人ID確認
		if (personalId == null || personalId.isEmpty()) {
			// ログインユーザの個人IDを取得
			personalId = mospParams.getUser().getPersonalId();
		}
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 新規登録モード設定
		insertMode();
		// 検索
		search();
		// 利用可否チェック
		isAvailable(getEditStartDate(), CODE_FUNCTION);
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 検索クラス取得
		HolidayRequestSearchBeanInterface search = timeReference().holidayRequestSearch();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// VOの値を検索クラスへ設定
		search.setPersonalId(personalId);
		String holidayType1 = vo.getPltSearchHolidayType();
		search.setHolidayType1(holidayType1);
		search.setHolidayType2("");
		search.setHolidayLength("");
		if (holidayType1 != null && !holidayType1.isEmpty()) {
			// 休暇種別の種類を判断
			int holidayType = Integer.parseInt(holidayType1);
			String holidayType2 = "";
			String holidayLength = vo.getPltSearchHolidayRange1();
			if (TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == holidayType) {
				// 有給休暇
				holidayType2 = vo.getPltSearchStatusWithPay();
			} else if (TimeConst.CODE_HOLIDAYTYPE_SPECIAL == holidayType) {
				// 特別休暇
				holidayType2 = vo.getPltSearchStatusSpecial();
			} else if (TimeConst.CODE_HOLIDAYTYPE_OTHER == holidayType) {
				// その他
				holidayType2 = vo.getPltSearchSpecialOther();
			} else if (TimeConst.CODE_HOLIDAYTYPE_ABSENCE == holidayType) {
				// 欠勤
				holidayType2 = vo.getPltSearchSpecialAbsence();
			}
			search.setHolidayType2(holidayType2);
			search.setHolidayLength(holidayLength);
		}
		// 変数準備
		int year = Integer.parseInt(vo.getPltSearchYear());
		int startMonth = getInt(TimeConst.CODE_DISPLAY_JANUARY);
		int endMonth = getInt(TimeConst.CODE_DISPLAY_DECEMBER);
		// 月検索(検索月が指定されている)ならば
		if (MospUtility.isEmpty(vo.getPltSearchMonth()) == false) {
			// 月を設定
			startMonth = getInt(vo.getPltSearchMonth());
			endMonth = startMonth;
		}
		// 勤怠関連マスタ参照処理を取得
		TimeMasterBeanInterface timeMaster = timeReference().master();
		// 年月(FROM及びTO)で設定適用エンティティを取得
		ApplicationEntity applicationFrom = timeMaster.getApplicationEntity(personalId, year, startMonth);
		ApplicationEntity applicationTo = timeMaster.getApplicationEntity(personalId, year, endMonth);
		// 締期間の開始及び最終日を取得
		Date firstDate = applicationFrom.getCutoffEntity().getCutoffFirstDate(year, startMonth, mospParams);
		Date lastDate = applicationTo.getCutoffEntity().getCutoffLastDate(year, endMonth, mospParams);
		// 締期間を検索範囲に設定
		search.setRequestStartDate(firstDate);
		search.setRequestEndDate(lastDate);
		search.setWorkflowStatus(vo.getPltSearchState());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<HolidayRequestListDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(HolidayRequestRequestDateComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty() && mospParams.getCommand().equals(CMD_SEARCH)) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException 例外発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		HolidayRequestReferenceBeanInterface holidayRequest = timeReference().holidayRequest();
		// システム日付取得
		Date date = getSystemDate();
		// 個人ID取得
		String personalId = vo.getPersonalId();
		final String nameNoLimit = mospParams.getName("NoLimit");
		// 有給休暇情報取得
		PaidHolidayInfoReferenceBeanInterface paidHolidayInfo = timeReference().paidHolidayInfo();
		// 休暇情報取得
		HolidayInfoReferenceBeanInterface holidayInfo = timeReference().holidayInfo();
		// 有給休暇残数取得処理を取得
		PaidHolidayRemainBeanInterface paidHolidayRemain = timeReference().paidHolidayRemain();
		vo.setJsPaidHolidayReasonRequired(holidayRequest.isPaidHolidayReasonRequired());
		// 残数のある休暇残情報群(特別休暇+その他休暇)を取得
		Set<HolidayRemainDto> remains = holidayInfo.getRemainHolidays(personalId, date);
		String[] aryGivingDate = new String[remains.size()];
		String[] arySpecialHolidayType = new String[remains.size()];
		String[] arySpecialHolidayName = new String[remains.size()];
		String[] aryRemainder = new String[remains.size()];
		String[] aryLimit = new String[remains.size()];
		int cnt = 0;
		// 休暇残情報毎に処理
		for (HolidayRemainDto dto : remains) {
			// 残数を取得
			double remainDays = dto.getRemainDays();
			int remainHours = dto.getRemainHours();
			int remainMinutes = dto.getRemainMinutes();
			// 有効日を設定
			aryGivingDate[cnt] = DateUtility.getStringDateAndDay(dto.getAcquisitionDate());
			// 残数を設定
			aryRemainder[cnt] = getFormatDaysHoursMinutes(remainDays, remainHours, remainMinutes, false);
			// 取得期限を設定
			aryLimit[cnt] = DateUtility.getStringDateAndDay(dto.getHolidayLimitDate());
			// 無制限である場合
			if (TimeUtility.isUnlimited(dto.getHolidayLimitDate())) {
				// 残数及び取得期限を再設定
				aryRemainder[cnt] = nameNoLimit;
				aryLimit[cnt] = nameNoLimit;
			}
			// 休暇区分及び休暇名称を設定
			arySpecialHolidayType[cnt] = getCodeName(dto.getHolidayType(), TimeConst.CODE_HOLIDAY_TYPE);
			arySpecialHolidayName[cnt++] = dto.getHolidayName();
		}
		vo.setRecordId(0);
		vo.setAryLblGivingDate(aryGivingDate);
		vo.setAryLblSpecialHolidayType(arySpecialHolidayType);
		vo.setAryLblSpecialHolidayName(arySpecialHolidayName);
		vo.setAryLblRemainder(aryRemainder);
		vo.setAryLblLimit(aryLimit);
		// 対象日がある場合
		if (getTargetDate() == null) {
			vo.setPltEditStartYear(DateUtility.getStringYear(date));
			vo.setPltEditStartMonth(DateUtility.getStringMonthM(date));
			vo.setPltEditStartDay(DateUtility.getStringDayD(date));
			vo.setPltEditEndYear(DateUtility.getStringYear(date));
			vo.setPltEditEndMonth(DateUtility.getStringMonthM(date));
			vo.setPltEditEndDay(DateUtility.getStringDayD(date));
		} else {
			vo.setPltEditStartYear(DateUtility.getStringYear(getTargetDate()));
			vo.setPltEditStartMonth(DateUtility.getStringMonthM(getTargetDate()));
			vo.setPltEditStartDay(DateUtility.getStringDayD(getTargetDate()));
			vo.setPltEditEndYear(DateUtility.getStringYear(getTargetDate()));
			vo.setPltEditEndMonth(DateUtility.getStringMonthM(getTargetDate()));
			vo.setPltEditEndDay(DateUtility.getStringDayD(getTargetDate()));
		}
		// 時間単位限度設定
		int[] timeUnitLimit = paidHolidayInfo.getHolidayTimeUnitLimit(personalId, date, false, null);
		vo.setLblHolidayTimeUnitLimit(getNumberOfDayAndHour(timeUnitLimit[0], timeUnitLimit[1]));
		// VOを初期化
		initVo();
		// 時間単位設定
		setPaidLeaveByHour();
		// ストック休暇
		double stock = paidHolidayRemain.getStockHolidayRemainDaysForView(personalId, date);
		vo.setLblPaidHolidayStock(TransStringUtility.getDoubleTimes(mospParams, stock, false, true));
		// 有給休暇情報欄表示
		setPaidLeave();
		// 有給休暇情報欄表示
		Map<String, Object> nextYearMap = paidHolidayInfo.getNextGivingInfo(personalId);
		if (nextYearMap == null) {
			return;
		}
		double nextYearDay = ((Double)nextYearMap.get(TimeConst.CODE_NEXT_PLAN_YEAR_DAY)).doubleValue();
		if (nextYearDay == -1) {
			nextYearDay = 0.0;
		}
		int nextYearTime = ((Integer)nextYearMap.get(TimeConst.CODE_NEXT_PLAN_TIME)).intValue();
		Date nextYearGivingDate = ((Date)nextYearMap.get(TimeConst.CODE_NEXT_PLAN_GIVING_DATE));
		Date nextYearrLimitDate = ((Date)nextYearMap.get(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE));
		// 有給休暇次回付与予定日
		vo.setLblNextGivingDate(DateUtility.getStringDateAndDay(nextYearGivingDate));
		// 有給休暇次回付与予定日数
		vo.setLblNextGivingAmount(getFormatDaysHoursMinutes(nextYearDay, nextYearTime, 0, false));
		// 有給休暇次回付与期限日
		vo.setLblNextLimitDate(DateUtility.getStringDateAndDay(nextYearrLimitDate));
		// 次回付与予定日(手動)取得及び確認
		Date nextManualGivingDate = paidHolidayInfo.getNextManualGivingDate(personalId);
		if (null == nextManualGivingDate) {
			return;
		}
		// 次回付与予定日(手動)
		vo.setLblNextManualGivingDate(DateUtility.getStringDateAndDay(nextManualGivingDate));
		// 次回付与予定日数(手動)
		vo.setLblNextManualGivingAmount(paidHolidayInfo.getNextManualGivingDaysAndHours(vo.getPersonalId()));
	}
	
	/**
	 * 有給休暇設定。
	 * @throws MospException 例外発生時
	 */
	protected void setPaidLeave() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		PaidHolidayInfoReferenceBeanInterface paidHolidayInfo = timeReference().paidHolidayInfo();
		Date date = getSystemDate();
		List<Map<String, Object>> list = paidHolidayInfo.getPaidHolidayDataListForView(vo.getPersonalId(), date);
		String[] aryLblPaidLeaveFiscalYear = new String[list.size()];
		String[] aryLblStyle = new String[list.size()];
		String[] aryLblPaidLeaveGrantDate = new String[list.size()];
		String[] aryLblPaidLeaveExpirationDate = new String[list.size()];
		String[] aryLblPaidLeaveRemainDays = new String[list.size()];
		String[] aryLblPaidLeaveGrantDays = new String[list.size()];
		double totalRemainDays = 0;
		int totalRemainHours = 0;
		for (int i = 0; i < list.size(); i++) {
			// 情報取得
			Map<String, Object> m = list.get(i);
			// スタイル準備
			aryLblStyle[i] = "";
			// 付与日取得
			aryLblPaidLeaveGrantDate[i] = "";
			Object grantDate = m.get(TimeConst.CODE_PAID_LEAVE_GRANT_DATE);
			if (grantDate != null) {
				aryLblPaidLeaveGrantDate[i] = DateUtility.getStringDateAndDay((Date)grantDate);
				// システム日付より付与日が先の場合
				if (getSystemDate().compareTo((Date)grantDate) < 0) {
					// 設定
					aryLblStyle[i] = PlatformConst.STYLE_GRAY;
				}
			}
			// 項目名取得
			String fiscalYearString = "";
			Object fiscalYear = m.get(TimeConst.CODE_PAID_LEAVE_FISCAL_YEAR);
			if (fiscalYear != null) {
				fiscalYearString = fiscalYear.toString();
			}
			aryLblPaidLeaveFiscalYear[i] = fiscalYearString;
			
			// 期限日取得
			aryLblPaidLeaveExpirationDate[i] = "";
			Object expirationDate = m.get(TimeConst.CODE_PAID_LEAVE_EXPIRATION_DATE);
			if (expirationDate != null) {
				aryLblPaidLeaveExpirationDate[i] = DateUtility.getStringDateAndDay((Date)expirationDate);
			}
			// 残日数取得
			double remainDaysDouble = 0;
			Object remainDays = m.get(TimeConst.CODE_PAID_LEAVE_REMAIN_DAYS);
			if (remainDays != null) {
				remainDaysDouble = ((Double)remainDays).doubleValue();
			}
			// 残時間取得
			int remainHoursInt = 0;
			Object remainHours = m.get(TimeConst.CODE_PAID_LEAVE_REMAIN_HOURS);
			if (remainHours != null) {
				remainHoursInt = ((Integer)remainHours).intValue();
			}
			// 付与日取得
			double grantDaysDouble = 0;
			Object grantDays = m.get(TimeConst.CODE_PAID_LEAVE_GRANT_DAYS);
			if (grantDays != null) {
				grantDaysDouble = ((Double)grantDays).doubleValue();
			}
			// 付与時間取得
			int grantHoursInt = 0;
			Object grantHours = m.get(TimeConst.CODE_PAID_LEAVE_GRANT_HOURS);
			if (grantHours != null) {
				grantHoursInt = ((Integer)grantHours).intValue();
			}
			aryLblPaidLeaveRemainDays[i] = getFormatDaysHoursMinutes(remainDaysDouble, remainHoursInt, 0, false);
			aryLblPaidLeaveGrantDays[i] = getFormatDaysHoursMinutes(grantDaysDouble, grantHoursInt, 0, false);
			if (mospParams.getName("PreviousYear", "Times").equals(fiscalYearString)
					|| mospParams.getName("ThisYear", "Times").equals(fiscalYearString)) {
				// 前年度又は今年度の場合
				totalRemainDays += remainDaysDouble;
				totalRemainHours += remainHoursInt;
			}
		}
		vo.setAryLblPaidLeaveFiscalYear(aryLblPaidLeaveFiscalYear);
		vo.setAryLblStyle(aryLblStyle);
		vo.setAryLblPaidLeaveGrantDate(aryLblPaidLeaveGrantDate);
		vo.setAryLblPaidLeaveExpirationDate(aryLblPaidLeaveExpirationDate);
		vo.setAryLblPaidLeaveRemainDays(aryLblPaidLeaveRemainDays);
		vo.setAryLblPaidLeaveGrantDays(aryLblPaidLeaveGrantDays);
		// 有休申請可能日数
		vo.setLblTotalDay(Double.toString(totalRemainDays));
		// 有休申請可能時間
		vo.setLblTotalTime(Integer.toString(totalRemainHours));
	}
	
	/**
	 * 有給休暇時間単位利用可否設定。
	 * @throws MospException 例外発生時
	 */
	protected void setPaidLeaveByHour() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		ApplicationReferenceBeanInterface applicationReference = timeReference().application();
		PaidHolidayReferenceBeanInterface paidHolidayReference = timeReference().paidHoliday();
		vo.setPaidLeaveByHour(false);
		Date date = getSystemDate();
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(vo.getPersonalId(), date);
		if (applicationDto == null) {
			return;
		}
		PaidHolidayDtoInterface paidHolidayDto = paidHolidayReference
			.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), date);
		if (paidHolidayDto == null) {
			return;
		}
		vo.setPaidLeaveByHour(paidHolidayDto.getTimelyPaidHolidayFlag() == 0);
	}
	
	/**
	 * 残数を取得する。<br>
	 * @param day 日数
	 * @param hour 時間数
	 * @return 残数
	 */
	protected String getNumberOfDayAndHour(int day, int hour) {
		return TransStringUtility.getJapaneaseDaysAndHours(mospParams, day, hour);
	}
	
	/**
	 * 表示期間を設定する。<br>
	 * @param year 年
	 * @param month 月
	 */
	protected void setSearchRequestDate(String year, String month) {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		vo.setPltSearchYear(year);
		vo.setPltSearchMonth(month);
	}
	
	/**
	 * VOの初期化を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void initVo() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		vo.setPltEditHolidayType1(String.valueOf(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY));
		vo.setPltEditStatusWithPay("");
		vo.setPltEditStatusSpecial("");
		vo.setPltEditSpecialOther("");
		vo.setPltEditSpecialAbsence("");
		vo.setPltEditHolidayRange("");
		vo.setPltEditHolidayRangePaidHoliday("");
		vo.setPltEditStartHour("0");
		vo.setPltEditStartMinute("0");
		vo.setPltEditEndTime("1");
		vo.setTxtEditRequestReason("");
		vo.setPltSearchHolidayType("");
		vo.setPltSearchHolidayType1("");
		vo.setPltSearchHolidayType2("");
		vo.setPltSearchHolidayType3("");
		vo.setPltSearchState("");
		// 申請残業年月日が含まれる締月を取得し検索条件に設定
		Date searchDate = timeReference().cutoffUtil().getCutoffMonth(vo.getPersonalId(), date);
		// 検索プルダウン年月設定
		vo.setPltSearchYear(DateUtility.getStringYear(searchDate));
		vo.setPltSearchMonth(DateUtility.getStringMonthM(searchDate));
		// 承認者欄の初期化
		String[] aryPltLblApproverSetting = new String[0];
		vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
		vo.setPltApproverSetting1("");
		vo.setPltApproverSetting2("");
		vo.setPltApproverSetting3("");
		vo.setPltApproverSetting4("");
		vo.setPltApproverSetting5("");
		vo.setPltApproverSetting6("");
		vo.setPltApproverSetting7("");
		vo.setPltApproverSetting8("");
		vo.setPltApproverSetting9("");
		vo.setPltApproverSetting10("");
		// 編集項目設定
		vo.setTxtEditRequestReason("");
		// 有給休暇情報の初期設定
		vo.setLblTotalDay("0");
		vo.setLblTotalTime("0");
		vo.setLblNextGivingDate("0");
		vo.setLblNextGivingAmount("0");
		final String hyphen = PfNameUtility.hyphen(mospParams);
		vo.setLblNextManualGivingDate(hyphen);
		vo.setLblNextManualGivingAmount(hyphen);
	}
	
	/**
	 * 下書処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void draft() throws MospException {
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// Beanを準備
		HolidayRequestExecuteBeanInterface bean = time().holidayRequestExecute();
		// VOから個人IDを取得
		String personalId = vo.getPersonalId();
		// VOから請開始日及び終了日を取得
		Date startDate = getEditStartDate();
		Date endDate = getEditEndDate();
		// VOから休暇種別1及び休暇種別2を取得
		int type1 = getInt(vo.getPltEditHolidayType1());
		String type2 = getHolidayType2(type1);
		// VOから休暇範囲を取得
		int range = getHolidayRange(type1, type2);
		// VOから時休開始時刻と時休時間数を取得
		Date startTime = getEditStartTime();
		int hours = MospUtility.getInt(vo.getPltEditEndTime());
		// VOから申請理由を取得
		String reason = vo.getTxtEditRequestReason();
		// VOからレコード識別IDを取得
		long recordId = vo.getRecordId();
		// VOから承認者個人ID配列を取得
		String[] approvers = getSelectApproverIds();
		// 休暇申請を下書
		bean.draft(personalId, startDate, endDate, type1, type2, range, startTime, hours, reason, recordId, approvers);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			// 処理終了
			return;
		}
		// コミット
		commit();
		// 下書成功メッセージを設定
		addDraftMessage();
		// 表示期間(検索)の年月を設定(申請開始日の年月)
		vo.setPltSearchYear(DateUtility.getStringYear(startDate));
		vo.setPltSearchMonth(DateUtility.getStringMonthM(startDate));
		// プルダウンを再設定
		setSearchPulldown();
		// 検索
		search();
		// 履歴編集対象を取得(編集モードに設定)
		setEditUpdateMode(personalId, startDate, type1, type2, range, startTime);
	}
	
	/**
	 * 申請処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void appli() throws MospException {
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// Beanを準備
		HolidayRequestExecuteBeanInterface bean = time().holidayRequestExecute();
		// VOから個人IDを取得
		String personalId = vo.getPersonalId();
		// VOから請開始日及び終了日を取得
		Date startDate = getEditStartDate();
		Date endDate = getEditEndDate();
		// VOから休暇種別1及び休暇種別2を取得
		int type1 = getInt(vo.getPltEditHolidayType1());
		String type2 = getHolidayType2(type1);
		// VOから休暇範囲を取得
		int range = getHolidayRange(type1, type2);
		// VOから時休開始時刻と時休時間数を取得
		Date startTime = getEditStartTime();
		int hours = MospUtility.getInt(vo.getPltEditEndTime());
		// VOから申請理由を取得
		String reason = vo.getTxtEditRequestReason();
		// VOからレコード識別IDを取得
		long recordId = vo.getRecordId();
		// VOから承認者個人ID配列を取得
		String[] approvers = getSelectApproverIds();
		// 休暇申請を申請
		bean.apply(personalId, startDate, endDate, type1, type2, range, startTime, hours, reason, recordId, approvers);
		// 追加業務ロジック処理を実行
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_HOLIDAYREQUESTACTION_APPLI);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			// 処理終了
			return;
		}
		// コミット
		commit();
		// 申請成功メッセージを設定
		addAppliMessage();
		// 午前休又は午後休の場合
		if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// メッセージを設定
			TimeMessageUtility.addHalfHolidayRequestNotice(mospParams);
		}
		// 新規登録モードに設定(入力内容を初期化)
		insertMode();
		// 表示期間(検索)の年月を設定(申請開始日の年月)
		vo.setPltSearchYear(DateUtility.getStringYear(startDate));
		vo.setPltSearchMonth(DateUtility.getStringMonthM(startDate));
		// プルダウンを再設定
		setSearchPulldown();
		// 検索
		search();
	}
	
	/**
	 * 一括更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void batchUpdate() throws MospException {
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// Beanを準備
		HolidayRequestExecuteBeanInterface bean = time().holidayRequestExecute();
		// VOから個人IDとレコード識別ID配列を取得
		String personalId = vo.getPersonalId();
		long[] idArray = getIdArray(vo.getCkbSelect());
		// 休暇申請を一括更新(下書の休暇申請を一括で申請)(半休が含まれているかを取得)
		boolean isHalfHolidayContained = bean.batchUpdate(personalId, idArray);
		// 追加業務ロジック処理を実行
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_HOLIDAYREQUESTACTION_APPLI, idArray);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 一括更新失敗メッセージを設定
			PfMessageUtility.addMessageBatchUpdatetFailed(mospParams);
			// 処理終了
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 半休が含まれている場合
		if (isHalfHolidayContained) {
			// メッセージを設定
			TimeMessageUtility.addHalfHolidayRequestNotice(mospParams);
		}
		// 表示期間(検索)の年月を取得
		String searchYear = vo.getPltSearchYear();
		String searchMonth = vo.getPltSearchMonth();
		// 新規登録モードに設定(入力内容を初期化)
		insertMode();
		// 表示期間(検索)の年月を設定
		vo.setPltSearchYear(searchYear);
		vo.setPltSearchMonth(searchMonth);
		// プルダウンを再設定
		setSearchPulldown();
		// 検索
		search();
	}
	
	/**
	 * 取下処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void withdrawn() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 登録クラス取得
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		WorkflowRegistBeanInterface workflowRegist = platform().workflowRegist();
		WorkflowCommentRegistBeanInterface workflowCommentRegist = platform().workflowCommentRegist();
		// DTOの準備
		HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForKey(vo.getRecordId());
		// 存在確認
		checkSelectedDataExist(dto);
		// 取下の相関チェック
		regist.checkWithdrawn(dto);
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		// 存在確認
		checkSelectedDataExist(workflowDto);
		boolean isDraft = PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus());
		if (isDraft) {
			// 下書の場合は削除する
			workflowRegist.delete(workflowDto);
			workflowCommentRegist
				.deleteList(reference().workflowComment().getWorkflowCommentList(workflowDto.getWorkflow()));
			regist.delete(dto);
		} else {
			// 下書でない場合は取下する
			// ワークフロー登録
			workflowDto = workflowRegist.withdrawn(workflowDto);
			if (workflowDto != null) {
				// ワークフローコメント登録
				workflowCommentRegist.addComment(workflowDto, mospParams.getUser().getPersonalId(),
						PfMessageUtility.getWithdrawSucceed(mospParams));
			}
		}
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		if (isDraft) {
			// 削除成功メッセージを設定
			PfMessageUtility.addMessageDeleteSucceed(mospParams);
		} else {
			// 取下成功メッセージ設定
			addTakeDownMessage();
		}
		String searchYear = vo.getPltEditStartYear();
		String searchMonth = vo.getPltEditStartMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		vo.setPltSearchYear(searchYear);
		vo.setPltSearchMonth(searchMonth);
		setSearchPulldown();
		// 検索
		search();
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
	 * @throws MospException 例外発生時
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 一括取下処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void batchWithdrawn() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 利用不可である場合
		if (isAvailable(getSystemDate(), CODE_FUNCTION) == false) {
			// 処理終了
			return;
		}
		// 一括更新処理
		time().holidayRequestRegist().withdrawn(getIdArray(vo.getCkbSelect()));
		// 一括更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 一括更新失敗メッセージを設定
			PfMessageUtility.addMessageBatchUpdatetFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 取下成功メッセージ設定
		addTakeDownMessage();
		String searchYear = vo.getPltSearchYear();
		String searchMonth = vo.getPltSearchMonth();
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
		// 表示期間設定
		setSearchRequestDate(searchYear, searchMonth);
		// 検索
		search();
	}
	
	/**
	 * プルダウンを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// 有効日を取得
		Date date = getEditStartDate();
		int editRequestYear = DateUtility.getYear(date);
		// 編集項目設定
		vo.setAryPltEditStartYear(getYearArray(editRequestYear));
		vo.setAryPltEditStartMonth(getMonthArray());
		// 31日まで取得
		vo.setAryPltEditStartDay(getDayArray());
		vo.setAryPltEditEndYear(getYearArray(editRequestYear));
		vo.setAryPltEditEndMonth(getMonthArray());
		// 31日まで取得
		vo.setAryPltEditEndDay(getDayArray());
		vo.setAryPltEditHolidayType1(mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false));
		// 休暇種別2(有給休暇)プルダウン設定
		vo.setAryPltEditHolidayType2WithPay(
				mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false));
		// 分(時間休申請時間)プルダウンを設定
		vo.setAryPltEditStartHour(getHourArray());
		// 休暇範囲プルダウンを取得
		String[][] aryRange = MospUtility.getCodeArray(mospParams, TimeConst.CODE_HOLIDAY_TYPE3_RANGE2, false);
		String[][] aryHourlyRange = MospUtility.getCodeArray(mospParams, TimeConst.CODE_HOLIDAY_TYPE3_RANGE1, false);
		// 休暇範囲プルダウン(有給休暇用及び有給休暇以外用)を設定
		vo.setAryPltEditHolidayRangePaidHoliday(aryRange);
		vo.setAryPltEditHolidayRange(aryRange);
		// 設定適用エンティティを取得
		ApplicationEntity entity = timeReference().master().getApplicationEntity(personalId, date);
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = entity.getTimeSettingDto();
		// 勤怠設定情報を取得できた場合
		if (timeSettingDto != null) {
			// 一日の起算時の23時間後まで
			vo.setAryPltEditStartHour(getHourArray(DateUtility.getHour(timeSettingDto.getStartDayTime()) + 23, true));
			// 終了時刻は勤怠設定の所定労働時間まで
			vo.setAryPltEditEndTime(getHourArray(DateUtility.getHour(timeSettingDto.getGeneralWorkTime()), false));
		}
		// 有給休暇設定を取得
		PaidHolidayDtoInterface paidHolidayDto = entity.getPaidHolidayDto();
		// 有給休暇設定を取得できた場合
		if (paidHolidayDto != null) {
			// 分(時間休申請時間)プルダウンを設定(申請時間間隔：1/15/30単位)
			vo.setAryPltEditStartMinute(getMinuteArray(paidHolidayDto.getAppliTimeInterval()));
			// 時間単位取得が有効である場合
			if (PlatformUtility.isActivate(paidHolidayDto.getTimelyPaidHolidayFlag())) {
				// 休暇範囲(有給休暇用)プルダウンを再設定(時間休有)
				vo.setAryPltEditHolidayRangePaidHoliday(aryHourlyRange);
			}
		}
		// 休暇種別に時間単位がある場合
		if (timeReference().holiday().isTimelyHoliday(date)) {
			// 休暇範囲(有給休暇以外用)プルダウンを再設定(時間休有)
			vo.setAryPltEditHolidayRange(aryHourlyRange);
		}
		// 検索項目設定
		vo.setAryPltSearchYear(getYearArray(editRequestYear));
		vo.setAryPltSearchMonth(getMonthArray(true));
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setEditActivationDate() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		HolidayRequestRegistBeanInterface regist = time().holidayRequestRegist();
		// プルダウン設定
		setPulldown();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 開始年月日と終了年月日の比較
			if (chkActivationDateValidate()) {
				return;
			}
			// 利用不可である場合
			if (isAvailable(getEditStartDate(), CODE_FUNCTION) == false
					|| isAvailable(getEditEndDate(), CODE_FUNCTION) == false) {
				// 処理終了
				return;
			}
			if (setApproverPullDown(vo.getPersonalId(), getEditStartDate(),
					PlatformConst.WORKFLOW_TYPE_TIME) == false) {
				return;
			}
			HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForKey(vo.getRecordId());
			if (dto == null) {
				dto = regist.getInitDto();
			}
			dto.setPersonalId(vo.getPersonalId());
			dto.setRequestStartDate(getEditStartDate());
			dto.setRequestEndDate(getEditEndDate());
			// 有効日設定時のチェック
			regist.checkSetRequestDate(dto);
			if (mospParams.hasErrorMessage()) {
				// 決定失敗メッセージを設定
				PfMessageUtility.addMessageDecisiontFailed(mospParams);
				return;
			}
			if (!getEditStartDate().equals(getEditEndDate())) {
				// 休暇開始日と休暇終了日が異なる場合は全休をセット
				vo.setPltEditHolidayRange(String.valueOf(TimeConst.CODE_HOLIDAY_RANGE_ALL));
				vo.setPltEditHolidayRangePaidHoliday(String.valueOf(TimeConst.CODE_HOLIDAY_RANGE_ALL));
			}
			// 連続取得設定
			setHolidayContinue();
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			String[] aryPltLblApproverSetting = new String[0];
			vo.setAryPltLblApproverSetting(aryPltLblApproverSetting);
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		setEditPulldown();
	}
	
	/**
	 * プルダウン(編集)の設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditPulldown() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		HolidayReferenceBeanInterface holidayReference = timeReference().holiday();
		HolidayInfoReferenceBeanInterface holidayInfo = timeReference().holidayInfo();
		// 有効日フラグ確認
		if (!vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			return;
		}
		// 個人IDを取得
		String personalId = vo.getPersonalId();
		// 休暇開始日取得
		Date startDate = getEditStartDate();
		// 有給休暇確認
		boolean paidLeave = isPaidLeaveHoliday(vo.getPersonalId(), startDate);
		// ストック休暇確認
		boolean stockLeave = isStockLeaveHoliday(vo.getPersonalId(), startDate);
		// プルダウンデータ取得
		String[][] paidArray = getAryPltEditHolidayType2Paid(paidLeave, stockLeave);
		String[][] specialArray = holidayInfo.getRemainArray(personalId, startDate, TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		String[][] otherArray = holidayInfo.getRemainArray(personalId, startDate, TimeConst.CODE_HOLIDAYTYPE_OTHER);
		String[][] absenceArray = holidayReference.getSelectArray(getEditStartDate(),
				TimeConst.CODE_HOLIDAYTYPE_ABSENCE, false);
		// プルダウンデータの存在チェック
		String noTargetData = PfNameUtility.noTargetData(mospParams);
		boolean paidStockDeleteFlag = !paidLeave && !stockLeave;
		boolean specialDeleteFlag = noTargetData.equals(specialArray[0][1]);
		boolean otherDeleteFlag = noTargetData.equals(otherArray[0][1]);
		boolean absenceDeleteFlag = noTargetData.equals(absenceArray[0][1]);
		String[][] holidayArray = mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE, false);
		String[][] newHolidayArray = holidayArray;
		int minus = 0;
		if (paidStockDeleteFlag) {
			minus++;
		}
		if (specialDeleteFlag) {
			minus++;
		}
		if (otherDeleteFlag) {
			minus++;
		}
		if (absenceDeleteFlag) {
			minus++;
		}
		if (minus > 0) {
			newHolidayArray = new String[holidayArray.length - minus][2];
			int i = 0;
			for (String[] holiday : holidayArray) {
				if (paidStockDeleteFlag) {
					if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(holiday[0])) {
						continue;
					}
				}
				if (specialDeleteFlag) {
					if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_SPECIAL).equals(holiday[0])) {
						continue;
					}
				}
				if (otherDeleteFlag) {
					if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_OTHER).equals(holiday[0])) {
						continue;
					}
				}
				if (absenceDeleteFlag) {
					if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_ABSENCE).equals(holiday[0])) {
						continue;
					}
				}
				newHolidayArray[i][0] = holiday[0];
				newHolidayArray[i][1] = holiday[1];
				i++;
			}
		}
		if (newHolidayArray.length == 0) {
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// 決定失敗メッセージを設定
			PfMessageUtility.addMessageDecisiontFailed(mospParams);
			// エラーメッセージを設定
			PfMessageUtility.addErrorValidDataNotExist(mospParams, TimeNamingUtility.holiday(mospParams));
			return;
		}
		// プルダウン設定
		vo.setAryPltEditHolidayType1(newHolidayArray);
		vo.setAryPltEditHolidayType2WithPay(paidArray);
		vo.setAryPltEditHolidayType2Special(specialArray);
		vo.setAryPltEditHolidayType2Other(otherArray);
		vo.setAryPltEditHolidayType2Absence(absenceArray);
		Date endDate = getEditEndDate();
		if (startDate.compareTo(endDate) == 0) {
			// 同日付に時差出勤が申請されているか確認する。
			getDifferenceRequest1(vo.getPersonalId(), startDate);
		}
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setSearchActivationDate() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getJsSearchModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 有効日モード設定
			vo.setJsSearchModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setJsSearchModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		setSearchPulldown();
	}
	
	/**
	 * 休暇連続取得設定を行う。<br>
	 * 休暇年月日決定時及び休暇種別プルダウン切替時に実行される。<br>
	 * <br>
	 * 連続取得が必須か警告であり使用日数よりも残数が多い場合に、申請ボタン押下時にメッセージを表示させる。<br>
	 * そのために必要な情報を、ここで設定する。<br>
	 * 使用日数と残数が同じ場合は連続取得警告メッセージを表示しないため、使用日数と残数も必要になる。<br>
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setHolidayContinue() throws MospException {
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// Beanを準備
		TimeMasterBeanInterface timeMaster = timeReference().master();
		HolidayRequestExecuteBeanInterface holidayRequestExecute = time().holidayRequestExecute();
		HolidayInfoReferenceBeanInterface holidayInfoRefer = timeReference().holidayInfo();
		holidayRequestExecute.setTimeMaster(timeMaster);
		holidayInfoRefer.setTimeMaster(timeMaster);
		// 初期化(連続取得不要)
		vo.setJsHolidayTerm(Integer.toString(0));
		vo.setJsHolidayContinue(Integer.toString(TimeConst.TYPE_CONTINUOUS_UNNECESSARY));
		vo.setJsHolidayRemainDay(Integer.toString(0));
		// VOから個人IDと休暇種別を取得
		String personalId = vo.getPersonalId();
		int holidayType = getInt(vo.getPltEditHolidayType1());
		// 休暇種別が有給か欠勤である場合
		if (TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == holidayType || TimeConst.CODE_HOLIDAYTYPE_ABSENCE == holidayType) {
			// 処理終了
			return;
		}
		// 休暇コードを取得
		String holidayCode = getHolidayType2(holidayType);
		// 休暇申請の期間を取得
		Date startDate = getEditStartDate();
		Date endDate = getEditEndDate();
		// 休暇種別情報を取得
		HolidayDtoInterface holidayDto = timeMaster.getHoliday(holidayCode, holidayType, startDate);
		// 休暇種別情報を取得できなかった場合
		if (PlatformUtility.isDtoActivate(holidayDto) == false) {
			// 処理終了
			return;
		}
		// 休暇対象日数を取得
		int useDays = holidayRequestExecute.getConsecutiveHolidayDates(personalId, startDate, endDate).size();
		// 申請可能な休暇残情報(残数があり有効日が最も早いもの)を取得
		HolidayRemainDto remain = holidayInfoRefer.getAppliableHoliday(personalId, startDate, holidayCode, holidayType);
		// 残数が無い場合
		if (MospUtility.isEmpty(remain)) {
			// 処理終了
			return;
		}
		// VOに連続取得と使用日数と残日数を設定
		vo.setJsHolidayContinue(Integer.toString(holidayDto.getContinuousAcquisition()));
		vo.setJsHolidayTerm(Integer.toString(useDays));
		vo.setJsHolidayRemainDay(Double.toString(remain.getRemainDays()));
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void selectActivationDate() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		select();
		String transferredHolidayType1 = getTransferredHolidayType1();
		if (transferredHolidayType1 != null) {
			vo.setPltEditHolidayType1(transferredHolidayType1);
		}
		boolean isPaid = TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(vo.getPltEditHolidayType1());
		boolean isSpecial = TimeConst.CODE_HOLIDAYTYPE_SPECIAL == getInt(vo.getPltEditHolidayType1());
		boolean isOther = TimeConst.CODE_HOLIDAYTYPE_OTHER == getInt(vo.getPltEditHolidayType1());
		boolean isAbsence = TimeConst.CODE_HOLIDAYTYPE_ABSENCE == getInt(vo.getPltEditHolidayType1());
		String transferredHolidayType2 = getTransferredHolidayType2();
		if (transferredHolidayType2 != null) {
			if (isPaid) {
				// 有給休暇
				vo.setPltEditStatusWithPay(transferredHolidayType2);
			} else if (isSpecial) {
				// 特別休暇
				vo.setPltEditStatusSpecial(transferredHolidayType2);
			} else if (isOther) {
				// その他休暇
				vo.setPltEditSpecialOther(transferredHolidayType2);
			} else if (isAbsence) {
				// 欠勤
				vo.setPltEditSpecialAbsence(transferredHolidayType2);
			}
		}
		String transferredHolidayRange = getTransferredHolidayRange();
		if (transferredHolidayRange != null) {
			if (isPaid) {
				// 有給休暇
				vo.setPltEditHolidayRangePaidHoliday(transferredHolidayRange);
			} else if (isSpecial || isOther || isAbsence) {
				// 特別休暇・その他休暇・欠勤
				vo.setPltEditHolidayRange(transferredHolidayRange);
			}
		}
		setEditActivationDate();
	}
	
	/**
	 * プルダウン(編集)の設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setSearchPulldown() throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// クラス取得
		HolidayReferenceBeanInterface holidayRefer = timeReference().holiday();
		ApplicationReferenceBeanInterface appRefer = timeReference().application();
		PaidHolidayReferenceBeanInterface paidHolidayRefer = timeReference().paidHoliday();
		// 有効日が決定の場合
		if (vo.getJsSearchModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
			// プルダウン設定
			Date date;
			// 休暇月が空の場合
			if (vo.getPltSearchMonth().isEmpty()) {
				date = MonthUtility.getYearMonthTargetDate(getInt(vo.getPltSearchYear()), 1, mospParams);
			} else {
				date = MonthUtility.getYearMonthTargetDate(getInt(vo.getPltSearchYear()),
						getInt(vo.getPltSearchMonth()), mospParams);
			}
			// 休暇種別2を設定
			vo.setAryPltSearchHolidayType2Special(
					holidayRefer.getSelectArray(date, TimeConst.CODE_HOLIDAYTYPE_SPECIAL, true));
			vo.setAryPltSearchHolidayType2Other(
					holidayRefer.getSelectArray(date, TimeConst.CODE_HOLIDAYTYPE_OTHER, true));
			vo.setAryPltSearchHolidayType2Absence(
					holidayRefer.getSelectArray(date, TimeConst.CODE_HOLIDAYTYPE_ABSENCE, true));
			vo.setAryPltSearchHolidayRangePaidHoliday(
					mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE3_RANGE2, true));
			// 休暇種別に時間単位がある場合
			if (holidayRefer.isTimelyHoliday(date)) {
				vo.setAryPltSearchHolidayRangePaidHoliday(
						mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE3_RANGE1, true));
			}
			ApplicationDtoInterface applicationDto = appRefer.findForPerson(vo.getPersonalId(), date);
			if (applicationDto == null) {
				return;
			}
			// 有給休暇情報取得
			PaidHolidayDtoInterface paidHolidayDto = paidHolidayRefer
				.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), date);
			if (paidHolidayDto == null) {
				return;
			}
			// 時休が有効の場合
			if (paidHolidayDto.getTimelyPaidHolidayFlag() == 0) {
				vo.setAryPltSearchHolidayRangePaidHoliday(
						mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE3_RANGE1, true));
			}
		}
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// プルダウン設定
		setPulldown();
		// 状態
		vo.setModeCardEdit(TimeConst.MODE_APPLICATION_NEW);
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// 有効日(検索)モード設定
		vo.setJsSearchModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(HolidayRequestRequestDateComparator.class.getName());
		//
		vo.setJsModeDifferenceRequest1("");
		//
		setEditPulldown();
		setSearchPulldown();
		// 基本情報チェック
		timeReference().holidayRequest().chkBasicInfo(vo.getPersonalId(), getEditStartDate());
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void editMode() throws MospException {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		if (personalId != null) {
			// 人事情報をVOに設定
			setEmployeeInfo(personalId, targetDate);
			// ページ繰り設定
			setPageInfo(CMD_PAGE, getListLength());
			// 新規登録モード設定
			insertMode();
			// 検索
			search();
		}
		// 個人IDを再取得
		personalId = vo.getPersonalId();
		// リクエストされた値を取得
		Date requestStartDate = getDate(getTransferredActivateDate());
		int holidayType1 = getInt(getTransferredHolidayType1());
		String holidayType2 = getTransferredHolidayType2();
		int holidayRange = getInt(getTransferredHolidayRange());
		Date startTime = DateUtility.getTime(getTransferredStartTime(), requestStartDate);
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(personalId, requestStartDate, holidayType1, holidayType2, holidayRange, startTime);
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		setEditActivationDate();
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * 申請開始日と休暇種別1と休暇種別2と休暇範囲と時休開始時刻で編集対象情報を取得する。<br>
	 * @param personalId       個人ID
	 * @param requestStartDate 申請開始日
	 * @param holidayType1     休暇種別1
	 * @param holidayType2     休暇種別2
	 * @param holidayRange     休暇範囲
	 * @param startTime        時休開始時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String personalId, Date requestStartDate, int holidayType1, String holidayType2,
			int holidayRange, Date startTime) throws MospException {
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// DTOを取得
		HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForKeyOnWorkflow(personalId,
				requestStartDate, holidayType1, holidayType2, holidayRange, startTime);
		// 存在確認
		checkSelectedDataExist(dto);
		// VOにセット
		setVoFields(dto);
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getAryWorkflow(getTransferredIndex()));
		// 承認履歴画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(ApprovalHistoryAction.CMD_LEAVE_APPROVAL_HISTORY_SELECT_SHOW);
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// データ配列初期化
		String[] aryCkbRecordId = new String[list.size()];
		String[] aryLblRequestDate = new String[list.size()];
		String[] aryLblHolidayType1 = new String[list.size()];
		String[] aryLblHolidayType2 = new String[list.size()];
		String[] aryLblHolidayRange = new String[list.size()];
		String[] aryLblRequestReason = new String[list.size()];
		String[] aryLblWorkflowStatus = new String[list.size()];
		String[] aryLblApproverName = new String[list.size()];
		String[] aryLblWorkflow = new String[list.size()];
		String[] aryStatusStyle = new String[list.size()];
		String[] aryHolidayType1 = new String[list.size()];
		String[] aryHolidayType2 = new String[list.size()];
		String[] aryHolidayRange = new String[list.size()];
		String[] aryStartTime = new String[list.size()];
		String[] aryLblOnOff = new String[list.size()];
		String[] aryWorkflowStatus = new String[list.size()];
		long[] aryWorkflow = new long[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			HolidayRequestListDtoInterface dto = (HolidayRequestListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryCkbRecordId[i] = String.valueOf(dto.getTmdHolidayRequestId());
			aryLblRequestDate[i] = DateUtility.getStringDateAndDay(dto.getRequestStartDate())
					+ mospParams.getName("Wave") + DateUtility.getStringDateAndDay(dto.getRequestEndDate());
			aryLblHolidayType1[i] = timeReference().holiday()
				.getHolidayType1NameForHolidayRequest(dto.getHolidayType1(), dto.getHolidayType2());
			aryLblHolidayType2[i] = getHolidayType2Abbr(dto.getHolidayType1(), dto.getHolidayType2(),
					dto.getRequestStartDate());
			aryLblHolidayRange[i] = getHolidayRange(dto);
			aryHolidayType1[i] = String.valueOf(dto.getHolidayType1());
			aryHolidayType2[i] = dto.getHolidayType2();
			aryHolidayRange[i] = String.valueOf(dto.getHolidayRange());
			aryStartTime[i] = DateUtility.getStringTime(dto.getStartTime(), dto.getRequestStartDate());
			aryLblRequestReason[i] = dto.getRequestReason();
			aryLblWorkflowStatus[i] = getStatusStageValueView(dto.getState(), dto.getStage());
			aryStatusStyle[i] = getStatusColor(dto.getState());
			aryLblApproverName[i] = dto.getApproverName();
			aryLblWorkflow[i] = String.valueOf(dto.getWorkflow());
			aryLblOnOff[i] = getButtonOnOff(dto.getState(), dto.getStage());
			aryWorkflowStatus[i] = dto.getState();
			aryWorkflow[i] = dto.getWorkflow();
		}
		// データをVOに設定
		vo.setAryCkbHolidayRequestListId(aryCkbRecordId);
		vo.setAryLblDate(aryLblRequestDate);
		vo.setAryLblHolidayType1(aryLblHolidayType1);
		vo.setAryLblHolidayType2(aryLblHolidayType2);
		vo.setAryLblHolidayType3(aryLblHolidayRange);
		vo.setAryLblRequestReason(aryLblRequestReason);
		vo.setAryLblState(aryLblWorkflowStatus);
		vo.setAryStateStyle(aryStatusStyle);
		vo.setAryLblApprover(aryLblApproverName);
		vo.setAryLblWorkflow(aryLblWorkflow);
		vo.setAryHolidayType1(aryHolidayType1);
		vo.setAryHolidayType2(aryHolidayType2);
		vo.setAryHolidayType3(aryHolidayRange);
		vo.setAryStartTime(aryStartTime);
		vo.setAryLblOnOff(aryLblOnOff);
		vo.setAryWorkflowStatus(aryWorkflowStatus);
		vo.setAryWorkflow(aryWorkflow);
	}
	
	/**
	 * VOから休暇種別2を取得する。<br>
	 * @param holidayType1 休暇種別1
	 * @return 休暇種別2
	 */
	protected String getHolidayType2(int holidayType1) {
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 休暇種別1毎に処理
		switch (holidayType1) {
			// 有給休暇である場合
			case TimeConst.CODE_HOLIDAYTYPE_HOLIDAY:
				// VOから休暇種別2を取得
				return vo.getPltEditStatusWithPay();
			// 特別休暇である場合
			case TimeConst.CODE_HOLIDAYTYPE_SPECIAL:
				// VOから休暇種別2を取得
				return vo.getPltEditStatusSpecial();
			// その他休暇である場合
			case TimeConst.CODE_HOLIDAYTYPE_OTHER:
				// VOから休暇種別2を取得
				return vo.getPltEditSpecialOther();
			// 欠勤である場合
			case TimeConst.CODE_HOLIDAYTYPE_ABSENCE:
				// VOから休暇種別2を取得
				return vo.getPltEditSpecialAbsence();
			// それ以外である場合
			default:
				// 空文字を取得
				return MospConst.STR_EMPTY;
		}
	}
	
	/**
	 * VOから休暇範囲を取得する。<br>
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @return 休暇範囲
	 */
	protected int getHolidayRange(int holidayType1, String holidayType2) {
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 休暇種別1が有給休暇である場合
		if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			// 休暇種別2が有給休暇である場合
			if (MospUtility.isEqual(holidayType2, TimeConst.CODE_HOLIDAYTYPE2_PAID)) {
				// VOから休暇範囲を取得
				return getInt(vo.getPltEditHolidayRangePaidHoliday());
			}
		}
		// VOから休暇範囲を取得
		return getInt(vo.getPltEditHolidayRange());
	}
	
	/**
	* 休暇範囲を取得する。<br>
	 * @param dto 対象DTO
	 * @return 休暇範囲
	*/
	protected String getHolidayRange(HolidayRequestListDtoInterface dto) {
		int holidayRange = dto.getHolidayRange();
		// 時休の場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			return getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestStartDate());
		}
		// 休暇範囲略称を取得
		return TimeUtility.getHolidayRangeAbbr(mospParams, holidayRange);
	}
	
	/**
	 * @return 休暇開始日を返す
	 */
	protected Date getEditStartDate() {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		return getDate(vo.getPltEditStartYear(), vo.getPltEditStartMonth(), vo.getPltEditStartDay());
	}
	
	/**
	 * @return 休暇終了日を返す
	 */
	protected Date getEditEndDate() {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		return getDate(vo.getPltEditEndYear(), vo.getPltEditEndMonth(), vo.getPltEditEndDay());
	}
	
	/**
	 * 休暇開始時刻を取得する。<br>
	 * @return 休暇開始時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getEditStartTime() throws MospException {
		// VOを準備
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// 休暇開始時刻を取得
		return DateUtility.addMinute(
				DateUtility.addHour(getEditStartDate(), MospUtility.getInt(vo.getPltEditStartHour())),
				MospUtility.getInt(vo.getPltEditStartMinute()));
		
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setVoFields(HolidayRequestDtoInterface dto) throws MospException {
		// VO取得
		HolidayRequestVo vo = (HolidayRequestVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmdHolidayRequestId());
		vo.setPltEditStartYear(DateUtility.getStringYear(dto.getRequestStartDate()));
		vo.setPltEditStartMonth(DateUtility.getStringMonthM(dto.getRequestStartDate()));
		vo.setPltEditStartDay(DateUtility.getStringDayD(dto.getRequestStartDate()));
		vo.setPltEditEndYear(DateUtility.getStringYear(dto.getRequestEndDate()));
		vo.setPltEditEndMonth(DateUtility.getStringMonthM(dto.getRequestEndDate()));
		vo.setPltEditEndDay(DateUtility.getStringDayD(dto.getRequestEndDate()));
		vo.setPltEditHolidayType1(String.valueOf(dto.getHolidayType1()));
		if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			vo.setPltEditStatusWithPay(dto.getHolidayType2());
		} else if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_SPECIAL) {
			vo.setPltEditStatusSpecial(dto.getHolidayType2());
		} else if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			vo.setPltEditSpecialOther(dto.getHolidayType2());
		} else {
			vo.setPltEditSpecialAbsence(dto.getHolidayType2());
		}
		if (dto.getHolidayType1() == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY
				&& TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == getInt(dto.getHolidayType2())) {
			// 有給休暇の場合
			vo.setPltEditHolidayRangePaidHoliday(String.valueOf(dto.getHolidayRange()));
		} else {
			// その他の場合
			vo.setPltEditHolidayRange(String.valueOf(dto.getHolidayRange()));
		}
		vo.setPltEditStartHour(String.valueOf(DateUtility.getHour(dto.getStartTime(), dto.getRequestStartDate())));
		vo.setPltEditStartMinute(DateUtility.getStringMinuteM(dto.getStartTime()));
		vo.setPltEditEndTime(String.valueOf(dto.getUseHour()));
		vo.setTxtEditRequestReason(dto.getRequestReason());
		vo.setModeCardEdit(getApplicationMode(dto.getWorkflow()));
	}
	
	/**
	 * 開始年月日と終了年月日の比較
	 * @return trueは年月日が超えている。falseの年月日が超えていない。
	 * @throws MospException 例外発生時
	 */
	protected boolean chkActivationDateValidate() throws MospException {
		if (getEditStartDate().after(getEditEndDate())) {
			TimeMessageUtility.addErrorHolidayOrderInvalid(mospParams, null);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 有給休暇の保有日数を確認する。
	 * @param personalId 対象個人ID
	 * @param startDate 休暇開始日
	 * @return 確認結果(true：有給休暇保有日数あり、false：有給休暇保有日数なし)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 * 
	 */
	protected boolean isPaidLeaveHoliday(String personalId, Date startDate) throws MospException {
		// 対象日で有給休暇残情報リスト(取得日昇順)を取得
		List<HolidayRemainDto> remains = timeReference().paidHolidayRemain().getPaidHolidayRemainsForRequest(personalId,
				startDate);
		// 申請用有給休暇申請可能数リスト毎に処理
		for (HolidayRemainDto remain : remains) {
			// 残数がある場合
			if (remain.getRemainDays() > 0 || remain.getRemainHours() > 0) {
				// 有給休暇保有日数ありと判断
				return true;
			}
		}
		// 有給休暇保有日数なしと判断
		return false;
	}
	
	/**
	 * ストック休暇の保有日数を確認する。
	 * @param personalId 対象個人ID
	 * @param startDate 休暇開始日
	 * @return 確認結果(true：ストック保有日数あり、false：ストック保有日数なし)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 * 
	 */
	protected boolean isStockLeaveHoliday(String personalId, Date startDate) throws MospException {
		// 申請用ストック休暇申請可能数リストを取得
		return timeReference().paidHolidayRemain().getStockHolidayRemainDaysForView(personalId, startDate) > 0D;
	}
	
	/**
	 * 有給休暇プルダウンを取得する。<br>
	 * @param paidLeave 有給休暇有無
	 * @param stockLeave ストック休暇有無
	 * @return 有給休暇プルダウン
	 */
	protected String[][] getAryPltEditHolidayType2Paid(boolean paidLeave, boolean stockLeave) {
		String[][] array = mospParams.getProperties().getCodeArray(TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, false);
		int minus = 0;
		if (!paidLeave) {
			minus++;
		}
		if (!stockLeave) {
			minus++;
		}
		if (minus == 0) {
			return array;
		}
		String[][] paidArray = new String[array.length - minus][2];
		int i = 0;
		for (String[] holiday : array) {
			if (!paidLeave) {
				// 有給休暇の場合
				if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(holiday[0])) {
					continue;
				}
			}
			if (!stockLeave) {
				// ストック休暇の場合
				if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(holiday[0])) {
					continue;
				}
			}
			paidArray[i][0] = holiday[0];
			paidArray[i][1] = holiday[1];
			i++;
		}
		return paidArray;
	}
	
}
