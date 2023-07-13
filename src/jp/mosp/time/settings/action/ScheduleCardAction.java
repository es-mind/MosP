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
package jp.mosp.time.settings.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleDateRegistBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleRegistBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmScheduleDateDto;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.settings.base.TimeSettingAction;
import jp.mosp.time.settings.vo.ScheduleCardVo;
import jp.mosp.time.utils.HolidayUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * カレンダ情報の個別詳細情報の確認、編集を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_REFLECTION}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_SET_COPY}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_MONTH_SWITCH}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li><li>
 * {@link #CMD_INSERT_MODE}
 * </li><li>
 * {@link #CMD_ADD_MODE}
 * </li><li>
 * {@link #CMD_REPLICATION_MODE}
 * </li><li>
 * {@link #CMD_SET_PATTERN}
 * </li></ul>
 */
public class ScheduleCardAction extends TimeSettingAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 新規登録モードで画面の初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM5420";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * カレンダ一覧画面で選択したレコードの情報を取得し、詳細画面を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "TM5421";
	
	/**
	 * 入力内容月反映コマンド。<br>
	 * <br>
	 * ラジオボタンで選択した範囲指定方法に基づき選択した勤務形態が
	 * その範囲内で適用されるよう繰り返し処理を行う。<br>
	 * また、祝祭日の適用ボタンがクリックされた際には
	 * 予め登録されている日付の勤務形態が所定休日となるよう月一括で表示を行う。<br>
	 */
	public static final String	CMD_REFLECTION			= "TM5424";
	
	/**
	 * 入力内容年度反映コマンド。<br>
	 * <br>
	 * ラジオボタンで選択した範囲指定方法に基づき選択した勤務形態が
	 * その範囲内で適用されるよう繰り返し処理を行う。<br>
	 * また、祝祭日の適用ボタンがクリックされた際には
	 * 予め登録されている日付の勤務形態が所定休日となるよう年度一括で登録を行う。<br>
	 */
	public static final String	CMD_ALL_REFLECTION		= "TM5436";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力欄に入力されている情報を元に勤怠設定情報テーブルに登録する。<br>
	 * 入力チェック時に入力必須項目が入力されていない、
	 * またはカレンダコードが登録済みのレコードのものと同一であった場合、エラーメッセージを表示する。<br>
	 */
	public static final String	CMD_REGIST				= "TM5425";
	
	/**
	 * 複製実行コマンド。<br>
	 * <br>
	 * カレンダコード入力欄に入力されている内容を確認して複製モード切替え時に
	 * 表示していたレコードの内容を新たなレコードとして新規登録を行う。<br>
	 * 入力必須項目が未入力またはカレンダコードが登録済みのレコードのものと同一であった場合、
	 * エラーメッセージにて通知し、複製処理を中断する。<br>
	 * 複製処理完了後、画面表示はそのまま複製を行ったレコードの履歴編集モードに切り替わる。<br>
	 */
	public static final String	CMD_SET_COPY			= "TM5426";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 現在表示しているカレンダ情報の論理削除を行う。<br>
	 */
	public static final String	CMD_DELETE				= "TM5427";
	
	/**
	 * 表示月切替コマンド。<br>
	 * <br>
	 * 月毎に用意されたボタンをクリックした際に選択した月の情報を取得し、日毎の状況を一覧で表示させる。<br>
	 * この時、勤務形態マスタよりその月に有効な勤務形態の情報を取得し
	 * 勤務形態指定欄と日付表示欄の各プルダウンに入れて選択可能な状態にする。<br>
	 */
	public static final String	CMD_MONTH_SWITCH		= "TM5429";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 有効日入力欄に入力した有効日を基に
	 * その年度の4月に有効な勤務形態パターンの情報をプルダウンに入れて選択可能な状態とする。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM5470";
	
	/**
	 * 新規登録モード切替コマンド。<br>
	 * <br>
	 * 各種入力欄に表示されている内容をクリアにする。<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替え、新規登録モード切替リンクを非表示にする。<br>
	 */
	public static final String	CMD_INSERT_MODE			= "TM5471";
	
	/**
	 * 履歴追加モード切替コマンド。<br>
	 * <br>
	 * 履歴編集モードで読取専用となっていた有効日の年月日入力欄を編集可能にする。<br>
	 * 登録ボタンクリック時のコマンドを履歴追加コマンドに切り替える。<br>
	 * 編集テーブルヘッダに表示されている履歴編集モードリンクを非表示にする。<br>
	 */
	public static final String	CMD_ADD_MODE			= "TM5473";
	
	/**
	 * 複製モード切替コマンド。<br>
	 * <br>
	 * 編集モードで編集不可だった有効日、コードが編集可能となり、<br>
	 * 登録ボタンクリック時の内容を登録コマンドに切り替える。<br>
	 * モード切替前に現在編集中のレコードのコードを変更することで新たなレコードとして登録できる旨を
	 * 伝える確認ダイアログを表示して利用者が承認をしてからモードを切り替える。<br>
	 */
	public static final String	CMD_REPLICATION_MODE	= "TM5474";
	
	/**
	 * パターン決定コマンド。<br>
	 * <br>
	 * 有効日を基に何年度のカレンダを作成しようとしているのかを判断し、
	 * その年度の日付・曜日情報を日付表示欄に出力する。<br>
	 * パターン決定直後は表示すべき年度の4月の日付の出力と
	 * その年度の4月に有効な勤務形態の情報を各プルダウンに入れて選択可能な状態とする。<br>
	 */
	public static final String	CMD_SET_PATTERN			= "TM5478";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public ScheduleCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ScheduleCardVo();
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
			prepareVo();
			editMode();
		} else if (mospParams.getCommand().equals(CMD_REFLECTION)) {
			// 入力内容月反映
			prepareVo();
			reflection();
		} else if (mospParams.getCommand().equals(CMD_ALL_REFLECTION)) {
			// 入力内容年度反映
			prepareVo();
			allReflection();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_SET_COPY)) {
			// カレンダ複製
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_MONTH_SWITCH)) {
			// 表示月切替
			prepareVo();
			monthSwitch();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		} else if (mospParams.getCommand().equals(CMD_INSERT_MODE)) {
			// 新規登録モード切替
			prepareVo();
			insertMode();
		} else if (mospParams.getCommand().equals(CMD_ADD_MODE)) {
			// 履歴追加モード切替
			prepareVo();
			addMode();
		} else if (mospParams.getCommand().equals(CMD_REPLICATION_MODE)) {
			// 複製モード切替
			prepareVo();
			replicationMode();
		} else if (mospParams.getCommand().equals(CMD_SET_PATTERN)) {
			// パターン決定
			prepareVo();
			setPattern();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void show() throws MospException {
		// 新規登録モード設定
		insertMode();
	}
	
	/**
	 * 入力内容を反映する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void reflection() throws MospException {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		String pltWorkType = vo.getPltWorkType();
		String[] aryWorkTypeMonth = vo.getPltWorkTypeMonth();
		String[] aryLblStartTime = vo.getAryLblStartMonth();
		String[] aryLblEndTime = vo.getAryLblEndMonth();
		String[] aryLblWorkTime = vo.getAryLblWorkMonth();
		// 対象年月取得
		int year = DateUtility.getYear(vo.getTargetMonth());
		int month = DateUtility.getMonth(vo.getTargetMonth());
		// 年月の初日と最終日を取得
		Date firstDate = MonthUtility.getYearMonthTermFirstDate(year, month, mospParams);
		Date lastDate = MonthUtility.getYearMonthTermLastDate(year, month, mospParams);
		// 祝日マップ取得
		Map<Date, String> holidayMap = HolidayUtility.getHolidayMap(getInt(vo.getPltFiscalYear()), mospParams);
		// 対象日のリストを取得
		List<Date> dateList = TimeUtility.getDateList(firstDate, lastDate);
		// 勤怠関連マスタ参照処理を準備
		TimeMasterBeanInterface timeMaster = timeReference().master();
		// ラジオ選択判定
		if (vo.getRadioSelect().equals(TimeConst.CODE_RADIO_WEEK)) {
			// 曜日指定
			if (vo.getCkbMonday().equals(MospConst.CHECKBOX_OFF) && vo.getCkbTuesday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbWednesday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbThursday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbFriday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbSatureday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbSunday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbNationalHoliday().equals(MospConst.CHECKBOX_OFF)) {
				// 日付チェックが選択されていない
				String[] aryMeassage = { mospParams.getName("DayOfTheWeek") };
				mospParams.addMessage(TimeMessageConst.MSG_SCHEDULE_CHECK, aryMeassage);
				return;
			}
			// インデックス準備
			int i = 0;
			for (Date targetDate : dateList) {
				if (vo.getCkbMonday().equals(MospConst.CHECKBOX_OFF) == false) {
					// 月曜日
					if (DateUtility.isMonday(targetDate)) {
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
				if (vo.getCkbTuesday().equals(MospConst.CHECKBOX_OFF) == false) {
					// 火曜日
					if (DateUtility.isTuesday(targetDate)) {
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
				if (vo.getCkbWednesday().equals(MospConst.CHECKBOX_OFF) == false) {
					// 水曜日
					if (DateUtility.isWednesday(targetDate)) {
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
				if (vo.getCkbThursday().equals(MospConst.CHECKBOX_OFF) == false) {
					// 木曜日
					if (DateUtility.isThursday(targetDate)) {
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
				if (vo.getCkbFriday().equals(MospConst.CHECKBOX_OFF) == false) {
					// 金曜日
					if (DateUtility.isFriday(targetDate)) {
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
				if (vo.getCkbSatureday().equals(MospConst.CHECKBOX_OFF) == false) {
					// 土曜日
					if (DateUtility.isSaturday(targetDate)) {
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
				if (vo.getCkbSunday().equals(MospConst.CHECKBOX_OFF) == false) {
					// 日曜日
					if (DateUtility.isSunday(targetDate)) {
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
				if (vo.getCkbNationalHoliday().equals(MospConst.CHECKBOX_OFF) == false) {
					// 祝日
					String strHoliday = holidayMap.get(DateUtility.getDate(year, month, i + 1));
					if (strHoliday != null && strHoliday.isEmpty() == false) {
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
				i++;
			}
		} else if (vo.getRadioSelect().equals(TimeConst.CODE_RADIO_PERIOD)) {
			// 期間指定
			// 開始日取得
			int scheduleStartDay = getInt(vo.getPltScheduleStartDay());
			// 終了日取得
			int scheduleEndDay = getInt(vo.getPltScheduleEndDay());
			for (int i = scheduleStartDay - 1; i < scheduleEndDay; i++) {
				Date targetDate = DateUtility.getDate(year, month, i + 1);
				aryWorkTypeMonth[i] = pltWorkType;
				aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
				aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
				aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
			}
		} else if (vo.getRadioSelect().equals(TimeConst.CODE_RADIO_CHECK)) {
			// チェック日付指定
			long[] idArray = getIdArray(vo.getCkbSelect());
			if (idArray.length == 0) {
				// 日付チェックが選択されていない
				String[] aryMeassage = { mospParams.getName("Date") };
				mospParams.addMessage(TimeMessageConst.MSG_SCHEDULE_CHECK, aryMeassage);
				return;
			}
			for (int i = 0; i < vo.getPltWorkTypeMonth().length; i++) {
				for (long element : idArray) {
					if (element == i + 1) {
						Date targetDate = DateUtility.getDate(year, month, i + 1);
						aryWorkTypeMonth[i] = pltWorkType;
						aryLblStartTime[i] = getWorkStartTime(timeMaster, pltWorkType, targetDate);
						aryLblEndTime[i] = getWorkEndTime(timeMaster, pltWorkType, targetDate);
						aryLblWorkTime[i] = getWorkTime(timeMaster, pltWorkType, targetDate);
					}
				}
			}
		} else {
			// ラジオが選択されていない
			mospParams.addMessage(TimeMessageConst.MSG_RADIO_CHECK);
		}
		// 一覧選択情報を初期化
		initCkbSelect();
		// 初期化
		initRadioValue();
		vo.setPltWorkTypeMonth(aryWorkTypeMonth);
		vo.setAryLblStartMonth(aryLblStartTime);
		vo.setAryLblEndMonth(aryLblEndTime);
		vo.setAryLblWorkMonth(aryLblWorkTime);
	}
	
	/**
	 * 入力内容を年度反映する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void allReflection() throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// クラス準備
		ScheduleReferenceBeanInterface refer = timeReference().schedule();
		ScheduleDateReferenceBeanInterface dateRefer = timeReference().scheduleDate();
		// 登録クラス取得
		ScheduleRegistBeanInterface regist = time().scheduleRegist();
		// カレンダコード・有効日取得
		String scheduleCode = vo.getTxtScheduleCode();
		Date activateDate = getActivateYearDate();
		// カレンダ情報取得
		ScheduleDtoInterface scheduleDto = refer.findForKey(scheduleCode, activateDate);
		if (scheduleDto == null) {
			// DTOの準備
			scheduleDto = regist.getInitDto();
			// DTOに値を設定
			setDtoFields(scheduleDto);
			// 登録処理
			if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
				// 履歴追加
				regist.add(scheduleDto);
			} else {
				// 新規登録
				regist.insert(scheduleDto);
			}
		} else {
			// DTOに値を設定
			scheduleDto.setPatternCode(vo.getPltPattern());
			scheduleDto.setScheduleName(vo.getTxtScheduleName());
			scheduleDto.setScheduleAbbr(vo.getTxtScheduleAbbr());
			// 更新処理
			regist.update(scheduleDto);
		}
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 対象年取得
		int targetYear = getInt(vo.getPltFiscalYear());
		// 年度の初日、最終日取得
		Date firstDate = MonthUtility.getFiscalYearFirstDate(targetYear, mospParams);
		Date lastDate = MonthUtility.getFiscalYearLastDate(targetYear, mospParams);
		// 対象日取得
		Date targetDate = firstDate;
		// 祝日マップ取得
		Map<Date, String> holidayMap = HolidayUtility.getHolidayMap(targetYear, mospParams);
		// 登録カレンダ日々リスト準備
		List<ScheduleDateDtoInterface> workList = new ArrayList<ScheduleDateDtoInterface>();
		// ラジオ選択判定
		if (vo.getRadioSelect().equals(TimeConst.CODE_RADIO_WEEK)) {
			// 曜日指定
			if (vo.getCkbMonday().equals(MospConst.CHECKBOX_OFF) && vo.getCkbTuesday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbWednesday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbThursday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbFriday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbSatureday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbSunday().equals(MospConst.CHECKBOX_OFF)
					&& vo.getCkbNationalHoliday().equals(MospConst.CHECKBOX_OFF)) {
				// 日付チェックが選択されていない
				String[] aryMeassage = { mospParams.getName("DayOfTheWeek") };
				mospParams.addMessage(TimeMessageConst.MSG_SCHEDULE_CHECK, aryMeassage);
				return;
			}
			// 対象期間最終日まで処理
			while (targetDate.after(lastDate) == false) {
				// カレンダ日情報取得
				ScheduleDateDtoInterface dateDto = dateRefer.getScheduleDateInfo(scheduleCode, targetDate);
				if (dateDto == null) {
					// カレンダ日情報取得、設定
					dateDto = new TmmScheduleDateDto();
					dateDto.setScheduleCode(vo.getTxtScheduleCode());
					dateDto.setActivateDate(getActivateYearDate());
					dateDto.setScheduleDate(targetDate);
					dateDto.setWorks(TimeBean.TIMES_WORK_DEFAULT);
					dateDto.setWorkTypeCode("");
					// 備考取得
					String remark = holidayMap.get(targetDate);
					if (remark == null) {
						remark = "";
					}
					// 備考設定
					dateDto.setRemark(remark);
					dateDto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
				}
				// ラジオ選択判定
				if (vo.getRadioSelect().equals(TimeConst.CODE_RADIO_WEEK)) {
					// 曜日指定
					if (vo.getCkbMonday().equals(MospConst.CHECKBOX_OFF)
							&& vo.getCkbTuesday().equals(MospConst.CHECKBOX_OFF)
							&& vo.getCkbWednesday().equals(MospConst.CHECKBOX_OFF)
							&& vo.getCkbThursday().equals(MospConst.CHECKBOX_OFF)
							&& vo.getCkbFriday().equals(MospConst.CHECKBOX_OFF)
							&& vo.getCkbSatureday().equals(MospConst.CHECKBOX_OFF)
							&& vo.getCkbSunday().equals(MospConst.CHECKBOX_OFF)
							&& vo.getCkbNationalHoliday().equals(MospConst.CHECKBOX_OFF)) {
						// 日付チェックが選択されていない
						String[] aryMeassage = { mospParams.getName("DayOfTheWeek") };
						mospParams.addMessage(TimeMessageConst.MSG_SCHEDULE_CHECK, aryMeassage);
						return;
					}
					// 月曜日
					if (!vo.getCkbMonday().equals(MospConst.CHECKBOX_OFF) && DateUtility.isMonday(targetDate)) {
						dateDto.setWorkTypeCode(vo.getPltWorkType());
					}
					// 火曜日
					if (!vo.getCkbTuesday().equals(MospConst.CHECKBOX_OFF) && DateUtility.isTuesday(targetDate)) {
						dateDto.setWorkTypeCode(vo.getPltWorkType());
					}
					// 水曜日
					if (!vo.getCkbWednesday().equals(MospConst.CHECKBOX_OFF) && DateUtility.isWednesday(targetDate)) {
						dateDto.setWorkTypeCode(vo.getPltWorkType());
					}
					// 木曜日
					if (!vo.getCkbThursday().equals(MospConst.CHECKBOX_OFF) && DateUtility.isThursday(targetDate)) {
						dateDto.setWorkTypeCode(vo.getPltWorkType());
					}
					// 金曜日
					if (!vo.getCkbFriday().equals(MospConst.CHECKBOX_OFF) && DateUtility.isFriday(targetDate)) {
						dateDto.setWorkTypeCode(vo.getPltWorkType());
					}
					// 土曜日
					if (!vo.getCkbSatureday().equals(MospConst.CHECKBOX_OFF) && DateUtility.isSaturday(targetDate)) {
						dateDto.setWorkTypeCode(vo.getPltWorkType());
					}
					// 日曜日
					if (!vo.getCkbSunday().equals(MospConst.CHECKBOX_OFF) && DateUtility.isSunday(targetDate)) {
						dateDto.setWorkTypeCode(vo.getPltWorkType());
					}
					// 祝日
					if (!vo.getCkbNationalHoliday().equals(MospConst.CHECKBOX_OFF)) {
						// 祝日取得
						String strHoliday = holidayMap.get(targetDate);
						if (strHoliday != null && !strHoliday.isEmpty()) {
							dateDto.setWorkTypeCode(vo.getPltWorkType());
						}
					}
					// 対象日加算
					targetDate = DateUtility.addDay(targetDate, 1);
					// リスト追加
					workList.add(dateDto);
				}
			}
			
		} else if (vo.getRadioSelect().equals(TimeConst.CODE_RADIO_PERIOD)) {
			// 期間指定
			// 開始日取得
			int scheduleStartDay = getInt(vo.getPltScheduleStartDay());
			// 終了日取得
			int scheduleEndDay = getInt(vo.getPltScheduleEndDay());
			// 対象期間最終日まで処理
			while (targetDate.after(lastDate) == false) {
				// 日取得
				int targetDay = DateUtility.getDay(targetDate);
				// カレンダ日情報取得
				ScheduleDateDtoInterface dateDto = dateRefer.getScheduleDateInfo(scheduleCode, targetDate);
				if (dateDto == null) {
					// カレンダ日情報取得、設定
					dateDto = new TmmScheduleDateDto();
					dateDto.setScheduleCode(vo.getTxtScheduleCode());
					dateDto.setActivateDate(getActivateYearDate());
					dateDto.setScheduleDate(targetDate);
					dateDto.setWorks(TimeBean.TIMES_WORK_DEFAULT);
					dateDto.setWorkTypeCode("");
					// 備考取得
					String remark = holidayMap.get(targetDate);
					if (remark == null) {
						remark = "";
					}
					// 備考設定
					dateDto.setRemark(remark);
					dateDto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
				}
				// 日が範囲内の場合
				if (targetDay >= scheduleStartDay && targetDay <= scheduleEndDay) {
					// 勤務形態設定
					dateDto.setWorkTypeCode(vo.getPltWorkType());
				}
				// 対象日加算
				targetDate = DateUtility.addDay(targetDate, 1);
				// リスト追加
				workList.add(dateDto);
			}
		} else if (vo.getRadioSelect().equals(TimeConst.CODE_RADIO_CHECK)) {
			// チェック日付指定
			// チェック日付配列取得
			long[] idArray = getIdArray(vo.getCkbSelect());
			if (idArray.length == 0) {
				// 日付チェックが選択されていない
				String[] aryMeassage = { mospParams.getName("Date") };
				mospParams.addMessage(TimeMessageConst.MSG_SCHEDULE_CHECK, aryMeassage);
				return;
			}
			// 日にちセット準備
			Set<Integer> daySet = new HashSet<Integer>();
			// 日にちセットに変換
			for (long id : idArray) {
				daySet.add(getInt(String.valueOf(id)));
			}
			// 対象期間最終日まで処理
			while (targetDate.after(lastDate) == false) {
				// 日取得
				int targetDay = DateUtility.getDay(targetDate);
				// カレンダ日情報取得
				ScheduleDateDtoInterface dateDto = dateRefer.getScheduleDateInfo(scheduleCode, targetDate);
				if (dateDto == null) {
					
					// カレンダ日情報取得、設定
					dateDto = new TmmScheduleDateDto();
					dateDto.setScheduleCode(vo.getTxtScheduleCode());
					dateDto.setActivateDate(getActivateYearDate());
					dateDto.setScheduleDate(targetDate);
					dateDto.setWorks(TimeBean.TIMES_WORK_DEFAULT);
					dateDto.setWorkTypeCode("");
					// 備考取得
					String remark = holidayMap.get(targetDate);
					if (remark == null) {
						remark = "";
					}
					// 備考設定
					dateDto.setRemark(remark);
					dateDto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
				}
				// 日が含まれている場合
				if (daySet.contains(targetDay)) {
					// 勤務形態設定
					dateDto.setWorkTypeCode(vo.getPltWorkType());
				}
				// 対象日加算
				targetDate = DateUtility.addDay(targetDate, 1);
				// リスト追加
				workList.add(dateDto);
			}
		} else {
			// ラジオが選択されていない
			mospParams.addMessage(TimeMessageConst.MSG_RADIO_CHECK);
			return;
		}
		// カレンダ日登録
		allYearRegist(workList);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(scheduleDto);
		// カレンダ日情報リストを取得
		List<ScheduleDateDtoInterface> list = getScheduleDateList(vo.getTxtScheduleCode(), vo.getTargetMonth());
		// DTOの値をVO(編集項目)に設定
		setVoFieldsDate(list);
		for (int i = 0; i < list.size(); i++) {
			// DTOの値をVO(編集項目)に設定
			setVoFieldsWork(list.get(i), i);
		}
		// 履歴編集モード設定
		setEditUpdateMode(scheduleDto.getScheduleCode(), scheduleDto.getActivateDate());
		// ボタン背景色設定
		setButtonColor(scheduleDto.getScheduleCode());
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 編集モード確認
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
			if (!vo.getJsCopyModeEdit().equals(CMD_REPLICATION_MODE)) {
				// 新規登録
				insert();
			} else {
				// 複製モードからの新規登録
				replication();
			}
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
			// 履歴追加
			add();
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			// 履歴更新
			update();
		}
	}
	
	/**
	 * 年度登録の登録処理を行う。<br>
	 * @param list カレンダ日リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void allYearRegist(List<ScheduleDateDtoInterface> list) throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// カレンダ日登録クラス準備
		ScheduleDateRegistBeanInterface regist = time().scheduleDateRegist();
		// 編集モード確認
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
			if (!vo.getJsCopyModeEdit().equals(CMD_REPLICATION_MODE)) {
				// 新規登録
				regist.allReflectionRegist(list);
				// 登録結果確認
				if (mospParams.hasErrorMessage()) {
					// 登録失敗メッセージを設定
					PfMessageUtility.addMessageInsertFailed(mospParams);
					return;
				}
				// コミット
				commit();
				// 登録成功メッセージを設定
				PfMessageUtility.addMessageNewInsertSucceed(mospParams);
			}
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
			// 履歴追加
			regist.add(list);
			// 更新結果確認
			if (mospParams.hasErrorMessage()) {
				// 更新失敗メッセージを設定
				PfMessageUtility.addMessageUpdateFailed(mospParams);
				return;
			}
			// コミット
			commit();
			// 履歴追加成功メッセージを設定
			PfMessageUtility.addMessageAddHistorySucceed(mospParams);
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			// 履歴更新
			regist.allReflectionRegist(list);
			// 更新結果確認
			if (mospParams.hasErrorMessage()) {
				// 更新失敗メッセージを設定
				PfMessageUtility.addMessageUpdateFailed(mospParams);
				return;
			}
			// コミット
			commit();
			// 履歴編集成功メッセージを設定
			PfMessageUtility.addMessageEditHistorySucceed(mospParams);
		}
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// DTOの準備
		ScheduleDtoInterface dto = time().scheduleRegist().getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 削除処理
		time().scheduleRegist().delete(dto);
		time().scheduleDateRegist().delete(dto.getScheduleCode(), dto.getActivateDate());
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
		// 新規登録モード設定(編集領域をリセット)
		insertMode();
	}
	
	/**
	 * 表示月切り替え処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合 
	 */
	protected void monthSwitch() throws MospException {
		// 月詳細表示処理
		setMonthDate();
		// チェックボックスの初期化
		initRadioValue();
	}
	
	/**
	 * 有効日決定処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		ScheduleReferenceBeanInterface schedule = timeReference().schedule();
		// 有効日決定時の場合
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 履歴追加の場合
			if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
				// 重複するデータがないかチェックする
				ScheduleDtoInterface dto = timeReference().schedule().findForKey(vo.getTxtScheduleCode(),
						getActivateYearDate());
				if (dto != null) {
					// 重複したデータがあるのでエラーメッセージを出力して処理を終了する
					mospParams.addErrorMessage(TimeMessageConst.MSG_SCHEDULE_HIST_ALREADY_EXISTED);
					return;
				}
			}
			// 有効日のモード設定
			setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日のモード設定
			setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// 年度プルダウンを再設定
		vo.setAryPltFiscalYear(getYearArray(getInt(vo.getPltFiscalYear())));
		// パターンプルダウン設定
		setPatternPulldown();
		// カレンダマスタ取得
		ScheduleDtoInterface scheduleDto = schedule.getScheduleInfo(vo.getTxtScheduleCode(),
				getActivateYearDate()/*(vo.getPltFiscalYear())*/);
		if (scheduleDto == null) {
			return;
		}
		// パターンコード設定
		vo.setPltPattern(scheduleDto.getPatternCode());
	}
	
	/**
	 * 月の詳細表示処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合 
	 */
	protected void setMonthDate() throws MospException {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 年度取得
		int fiscalYear = getInt(vo.getPltFiscalYear());
		// 対象年月準備(年度開始月)
		Date targetMonth = MonthUtility.getFiscalStartMonth(fiscalYear, mospParams);
		// 対象月取得
		String transferredMonth = getTransferredMonth();
		// 対象月確認
		if (transferredMonth != null) {
			// 対象年月再取得
			targetMonth = MonthUtility.getFiscalYearMonth(fiscalYear, getInt(transferredMonth), mospParams);
		}
		// 対象年月をVOに設定
		vo.setTargetMonth(targetMonth);
		// 対象年及び月を取得
		int year = DateUtility.getYear(targetMonth);
		int month = DateUtility.getMonth(targetMonth);
		// 年月の初日と最終日を取得
		Date firstDate = MonthUtility.getYearMonthTermFirstDate(year, month, mospParams);
		Date lastDate = MonthUtility.getYearMonthTermLastDate(year, month, mospParams);
		// 祝日マップ取得
		Map<Date, String> holidayMap = HolidayUtility.getHolidayMap(fiscalYear, mospParams);
		setPulldown();
		// 有効な勤務形態が存在しなければ何も処理を行わない
		String[][] workType = vo.getAryPltWorkType();
		if (workType.length == 0 || !workType[0][1].isEmpty()) {
			// メッセージ作成
			String mes1 = year + mospParams.getName("FiscalYear") + month + mospParams.getName("Month", "Point");
			String mes2 = mospParams.getName("Work", "Form");
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORKFORM_EXISTENCE2, mes1, mes2);
			mospParams.addErrorMessage(TimeMessageConst.MSG_CALENDAR_ERROR_MESSAGE);
			// 勤務形態
			String[] aryLblWorkTypeMonth = new String[0];
			vo.setPltWorkTypeMonth(aryLblWorkTypeMonth);
			return;
		}
		vo.setLblFiscalYear(year + mospParams.getName("Year") + month + mospParams.getName("Month"));
		// 参照クラス準備
		ScheduleDateReferenceBeanInterface scheduleDate = timeReference().scheduleDate();
		// 勤怠関連マスタ参照処理を準備
		TimeMasterBeanInterface timeMaster = timeReference().master();
		// 対象日のリストを取得
		List<Date> dateList = TimeUtility.getDateList(firstDate, lastDate);
		// 表示内容の初期化
		long[] aryCkbRecordId = new long[dateList.size()];
		String[] aryLblWorkTypeMonth = new String[dateList.size()];
		String[] aryLblRemarkMonth = new String[dateList.size()];
		String[] aryLblMonth = new String[dateList.size()];
		String[] aryLblStartMonth = new String[dateList.size()];
		String[] aryLblEndMonth = new String[dateList.size()];
		String[] aryLblWorkMonth = new String[dateList.size()];
		String[][] aryPltWorkType = vo.getAryPltWorkType();
		// インデックス準備
		int i = 0;
		// 対象日毎に処理
		for (Date targetDate : dateList) {
			ScheduleDateDtoInterface dto = scheduleDate.getScheduleDateInfo(vo.getTxtScheduleCode(), targetDate);
			aryCkbRecordId[i] = i + 1;
			// 日付
			aryLblMonth[i] = DateUtility.getStringMonthAndDay(targetDate);
			// 形態
			if (MospUtility.isEmpty(dto) == false) {
				// 勤務形態コードを取得
				String workTypeCode = dto.getWorkTypeCode();
				// 勤務形態コードを設定
				aryLblWorkTypeMonth[i] = workTypeCode;
				// 出勤時刻を設定
				aryLblStartMonth[i] = getWorkStartTime(timeMaster, workTypeCode, targetDate);
				// 退勤時刻を設定
				aryLblEndMonth[i] = getWorkEndTime(timeMaster, workTypeCode, targetDate);
				// 勤務時間を設定
				aryLblWorkMonth[i] = getWorkTime(timeMaster, workTypeCode, targetDate);
				// 備考
				aryLblRemarkMonth[i] = String.valueOf(dto.getRemark());
			} else {
				aryLblWorkTypeMonth[i] = aryPltWorkType[0][0];
				aryLblStartMonth[i] = getWorkStartTime(timeMaster, aryLblWorkTypeMonth[i], targetDate);
				aryLblEndMonth[i] = getWorkEndTime(timeMaster, aryLblWorkTypeMonth[i], targetDate);
				aryLblWorkMonth[i] = getWorkTime(timeMaster, aryLblWorkTypeMonth[i], targetDate);
				// 休暇情報取得
				String strHoliday = holidayMap.get(targetDate);
				// 休暇情報確認
				if (strHoliday != null && strHoliday.isEmpty() == false) {
					aryLblRemarkMonth[i] = strHoliday;
				} else {
					aryLblRemarkMonth[i] = MospConst.STR_EMPTY;
				}
			}
			i++;
		}
		vo.setAryCkbRecordId(aryCkbRecordId);
		vo.setAryLblMonth(aryLblMonth);
		vo.setPltWorkTypeMonth(aryLblWorkTypeMonth);
		vo.setAryLblStartMonth(aryLblStartMonth);
		vo.setAryLblEndMonth(aryLblEndMonth);
		vo.setAryLblWorkMonth(aryLblWorkMonth);
		vo.setTxtRemarkMonth(aryLblRemarkMonth);
	}
	
	/**
	 * 新規登録モードで画面を表示する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void insertMode() throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 新規登録モード設定
		setEditInsertMode();
		// 初期化
		setDefaultValues();
		// 有効日モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		vo.setModePattern(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
		// パターンプルダウン設定
		setPatternPulldown();
	}
	
	/**
	 * 履歴追加モードで画面を表示する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void addMode() throws MospException {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		vo.setJsCopyModeEdit("off");
		vo.setButtonBackColorJanuary(setButtonBackColor(0));
		vo.setButtonBackColorFebruary(setButtonBackColor(0));
		vo.setButtonBackColorMarch(setButtonBackColor(0));
		vo.setButtonBackColorApril(setButtonBackColor(0));
		vo.setButtonBackColorMay(setButtonBackColor(0));
		vo.setButtonBackColorJune(setButtonBackColor(0));
		vo.setButtonBackColorJuly(setButtonBackColor(0));
		vo.setButtonBackColorAugust(setButtonBackColor(0));
		vo.setButtonBackColorSeptember(setButtonBackColor(0));
		vo.setButtonBackColorOctorber(setButtonBackColor(0));
		vo.setButtonBackColorNovember(setButtonBackColor(0));
		vo.setButtonBackColorDecember(setButtonBackColor(0));
		// 履歴追加モード設定
		setEditAddMode();
		// 有効日(編集)モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		vo.setModePattern(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// パターンプルダウン設定
		setPatternPulldown();
	}
	
	/**
	 * 履歴編集モードで画面を表示する。<br>
	 * 履歴編集対象は、遷移汎用コード及び有効日で取得する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void editMode() throws MospException {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		vo.setJsCopyModeEdit("off");
		// 遷移汎用コード及び有効日から履歴編集対象を取得し編集モードを設定
		setEditUpdateMode(getTransferredCode(), getDate(getTransferredActivateDate()));
		// 有効日(編集)モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		vo.setModePattern(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウンの設定
		setPulldown();
		// パターンプルダウン設定
		setPatternPulldown();
		// 月の表示
		monthSwitch();
	}
	
	/**
	 * 複製モードに設定する。<br>
	 */
	protected void replicationMode() {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 複製モード設定
		setEditReplicationMode();
		vo.setModePattern(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		vo.setJsCopyModeEdit(CMD_REPLICATION_MODE);
		vo.setCopyFiscalYear(vo.getPltFiscalYear());
		vo.setCopyScheduleCode(vo.getTxtScheduleCode());
		// コードを空白に設定
		vo.setTxtScheduleCode("");
	}
	
	/**
	 * パターン決定処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setPattern() throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		if (PlatformConst.MODE_ACTIVATE_DATE_CHANGING.equals(vo.getModePattern())) {
			// パターンのモード設定
			vo.setModePattern(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
			// 表示月切替
			monthSwitch();
			return;
		} else if (PlatformConst.MODE_ACTIVATE_DATE_FIXED.equals(vo.getModePattern())) {
			// パターンのモード設定
			vo.setModePattern(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
	}
	
	/**
	 * 新規追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// 登録クラス取得
		ScheduleRegistBeanInterface regist = time().scheduleRegist();
		ScheduleDateRegistBeanInterface regist2 = time().scheduleDateRegist();
		// DTOの準備
		ScheduleDtoInterface dto = regist.getInitDto();
		List<ScheduleDateDtoInterface> listWork = new ArrayList<ScheduleDateDtoInterface>();
		// DTOに値を設定
		setDtoFields(dto);
		List<ScheduleDateDtoInterface> list = setDtoFieldsDate(listWork);
		// 登録処理
		regist.insert(dto);
		regist2.insert(list);
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageNewInsertSucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		setVoFieldsDate(list);
		for (int i = 0; i < list.size(); i++) {
			setVoFieldsWork(list.get(i), i);
		}
		// 履歴編集モード設定
		setEditUpdateMode(dto.getScheduleCode(), dto.getActivateDate());
		// ボタン背景色設定
		setButtonColor(dto.getScheduleCode());
	}
	
	/**
	 * 履歴追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add() throws MospException {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 登録クラス取得
		ScheduleRegistBeanInterface regist = time().scheduleRegist();
		ScheduleDateRegistBeanInterface regist2 = time().scheduleDateRegist();
		// DTOの準備
		ScheduleDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 履歴追加処理
		regist.add(dto);
		// カレンダコード及び有効日を取得
		String scheduleCode = vo.getTxtScheduleCode();
		// カレンダ日マスタ情報リスト取得
		List<ScheduleDateDtoInterface> list = getScheduleDateList(scheduleCode, vo.getTargetMonth());
		// DTOに値を設定
		List<ScheduleDateDtoInterface> workList = setDtoFieldsDate(list);
		// 履歴追加処理
		regist2.add(workList);
		// 対象年月(年度開始月(年度開始月の初日))を準備
		Date targetMonth = MonthUtility.getFiscalStartMonth(getInt(vo.getPltFiscalYear()), mospParams);
		// 12か月分データ更新
		for (int i = 0; i < TimeConst.CODE_DEFINITION_YEAR; i++) {
			if (targetMonth.equals(vo.getTargetMonth())) {
				// 対象年月の場合
				// 対象年月加算
				targetMonth = DateUtility.addMonth(targetMonth, 1);
				continue;
			}
			// カレンダ日マスタ情報リスト取得
			List<ScheduleDateDtoInterface> allMonthList = getScheduleDateList(scheduleCode, targetMonth);
			if (!allMonthList.isEmpty()) {
				List<ScheduleDateDtoInterface> workAllMonthList = new ArrayList<ScheduleDateDtoInterface>();
				for (int j = 0; j < allMonthList.size(); j++) {
					ScheduleDateDtoInterface scheduleDto = allMonthList.get(j);
					scheduleDto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
					workAllMonthList.add(scheduleDto);
				}
				regist2.add(workAllMonthList);
			}
			// 対象年月加算
			targetMonth = DateUtility.addMonth(targetMonth, 1);
		}
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴追加成功メッセージを設定
		PfMessageUtility.addMessageAddHistorySucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		setVoFieldsDate(workList);
		for (int i = 0; i < workList.size(); i++) {
			setVoFieldsWork(workList.get(i), i);
		}
		// 履歴編集モード設定
		setEditUpdateMode(dto.getScheduleCode(), dto.getActivateDate());
		// ボタン背景色設定
		setButtonColor(dto.getScheduleCode());
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 登録クラス取得
		ScheduleRegistBeanInterface regist = time().scheduleRegist();
		ScheduleDateRegistBeanInterface dateRegist = time().scheduleDateRegist();
		// DTOの準備
		ScheduleDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.update(dto);
		// カレンダコード及び有効日を取得
		String scheduleCode = vo.getTxtScheduleCode();
		// カレンダ日マスタ情報リスト取得
		List<ScheduleDateDtoInterface> list = getScheduleDateList(scheduleCode, vo.getTargetMonth());
		// DTOに値を設定
		List<ScheduleDateDtoInterface> workList = setDtoFieldsDate(list);
		// 更新処理
		if (list.isEmpty()) {
			// データが存在しない場合
			dateRegist.insert(workList);
		} else {
			dateRegist.update(workList);
		}
		// 対象年月(年度開始月(年度開始月の初日))を準備
		Date targetMonth = MonthUtility.getFiscalStartMonth(getInt(vo.getPltFiscalYear()), mospParams);
		// 12か月分データ更新
		for (int i = 0; i < TimeConst.CODE_DEFINITION_YEAR; i++) {
			// カレンダ日マスタ情報リスト取得
			List<ScheduleDateDtoInterface> allMonthList = getScheduleDateList(scheduleCode, targetMonth);
			// 情報が有る場合
			if (!allMonthList.isEmpty()) {
				// リスト準備
				List<ScheduleDateDtoInterface> workAllMonthList = new ArrayList<ScheduleDateDtoInterface>();
				// カレンダ日マスタ情報毎に処理
				for (int j = 0; j < allMonthList.size(); j++) {
					// カレンダ日マスタ情報取得
					ScheduleDateDtoInterface scheduleDto = allMonthList.get(j);
					// 有効/無効フラグ設定
					scheduleDto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
					// リストに追加
					workAllMonthList.add(scheduleDto);
				}
				// 更新
				dateRegist.update(workAllMonthList);
			}
			// 対象年月加算
			targetMonth = DateUtility.addMonth(targetMonth, 1);
		}
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 履歴編集成功メッセージを設定
		PfMessageUtility.addMessageEditHistorySucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		setVoFieldsDate(workList);
		for (int i = 0; i < workList.size(); i++) {
			setVoFieldsWork(workList.get(i), i);
		}
		// 履歴編集モード設定
		setEditUpdateMode(dto.getScheduleCode(), dto.getActivateDate());
		// ボタン背景色設定
		setButtonColor(dto.getScheduleCode());
	}
	
	/**
	 * 複製モードからの新規追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void replication() throws MospException {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 登録クラス取得
		ScheduleRegistBeanInterface regist = time().scheduleRegist();
		ScheduleDateRegistBeanInterface regist2 = time().scheduleDateRegist();
		// DTOの準備
		ScheduleDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 登録処理
		regist.insert(dto);
		// 対象年月(年度開始月(年度開始月の初日))を準備
		Date targetMonth = MonthUtility.getFiscalStartMonth(getInt(vo.getPltFiscalYear()), mospParams);
		// 表示された月以外の複製元データを全てコピーする
		for (int i = 0; i < TimeConst.CODE_DEFINITION_YEAR; i++) {
			// 勤務形態プルダウン用配列取得
			String[][] workTypeArray = getWorkTypeArray(targetMonth);
			// 休日プルダウン用配列取得
			String[][] dayOffArray = mospParams.getProperties().getCodeArray(TimeConst.CODE_PRESCRIBED_LEGAL_HOLIDAY,
					true);
			// カレンダ日マスタ情報リスト取得
			List<ScheduleDateDtoInterface> allMonthList = getScheduleDateList(vo.getCopyScheduleCode(), targetMonth);
			if (!allMonthList.isEmpty()) {
				// 年度リスト準備
				List<ScheduleDateDtoInterface> workAllMonthList = new ArrayList<ScheduleDateDtoInterface>();
				// カレンダ日マスタ情報リスト毎に処理
				for (int j = 0; j < allMonthList.size(); j++) {
					// カレンダ日情報取得
					ScheduleDateDtoInterface scheduleDto = allMonthList.get(j);
					// 値設定
					scheduleDto.setActivateDate(dto.getActivateDate());
					scheduleDto.setScheduleCode(dto.getScheduleCode());
					// 勤務形態がない場合
					if (!contains(scheduleDto.getWorkTypeCode(), workTypeArray)
							&& !contains(scheduleDto.getWorkTypeCode(), dayOffArray)) {
						scheduleDto.setWorkTypeCode("");
					}
					scheduleDto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
					// 年度リスト追加
					workAllMonthList.add(scheduleDto);
				}
				// 年度リスト登録
				regist2.insert(workAllMonthList);
			}
			// 対象年月加算
			targetMonth = DateUtility.addMonth(targetMonth, 1);
		}
		// 登録結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageNewInsertSucceed(mospParams);
		// 登録後の情報をVOに設定(レコード識別ID更新)
		setVoFields(dto);
		// カレンダ日情報リストを取得
		List<ScheduleDateDtoInterface> list = getScheduleDateList(vo.getTxtScheduleCode(), vo.getTargetMonth());
		// DTOの値をVO(編集項目)に設定
		setVoFieldsDate(list);
		for (int i = 0; i < list.size(); i++) {
			// DTOの値をVO(編集項目)に設定
			setVoFieldsWork(list.get(i), i);
		}
		// 履歴編集モード設定
		setEditUpdateMode(dto.getScheduleCode(), dto.getActivateDate());
		// ボタン背景色設定
		setButtonColor(dto.getScheduleCode());
		// コピー設定初期化
		vo.setJsCopyModeEdit("off");
	}
	
	/**
	 * 履歴編集モードを設定する。<br>
	 * カレンダコードと有効日で編集対象情報を取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(String scheduleCode, Date activateDate) throws MospException {
		// カレンダ参照クラス取得
		ScheduleReferenceBeanInterface schedule = timeReference().schedule();
		// カレンダマスタ取得
		ScheduleDtoInterface dto = schedule.findForKey(scheduleCode, activateDate);
		// カレンダマスタをVOに設定
		setVoFields(dto);
		// 有効日(編集)モード設定
		setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// 編集モード設定
		setEditUpdateMode(schedule.getScheduleHistory(scheduleCode));
		// ボタン背景色設定
		setButtonColor(scheduleCode);
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 検索項目設定
		vo.setPltFiscalYear(String.valueOf(MonthUtility.getFiscalYear(getSystemDate(), mospParams)));
		vo.setPltPattern("");
		vo.setTxtScheduleCode("");
		vo.setTxtScheduleName("");
		vo.setTxtScheduleAbbr("");
		vo.setPltWorkTypeChange(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		
		vo.setLblFiscalYear("");
		
		vo.setPltWorkType("");
		vo.setPltScheduleStartDay("");
		vo.setPltScheduleEndDay("");
		String[] initWorkTypeMonth = new String[1];
		initWorkTypeMonth[0] = "";
		vo.setPltWorkTypeMonth(initWorkTypeMonth);
		
		// 月ボタン背景色の初期化
		vo.setButtonBackColorJanuary(setButtonBackColor(0));
		vo.setButtonBackColorFebruary(setButtonBackColor(0));
		vo.setButtonBackColorMarch(setButtonBackColor(0));
		vo.setButtonBackColorApril(setButtonBackColor(0));
		vo.setButtonBackColorMay(setButtonBackColor(0));
		vo.setButtonBackColorJune(setButtonBackColor(0));
		vo.setButtonBackColorJuly(setButtonBackColor(0));
		vo.setButtonBackColorAugust(setButtonBackColor(0));
		vo.setButtonBackColorSeptember(setButtonBackColor(0));
		vo.setButtonBackColorOctorber(setButtonBackColor(0));
		vo.setButtonBackColorNovember(setButtonBackColor(0));
		vo.setButtonBackColorDecember(setButtonBackColor(0));
		
		vo.setJsCopyModeEdit("off");
		vo.setCopyFiscalYear("");
		vo.setCopyScheduleCode("");
		
		vo.setRadioWeek(TimeConst.CODE_RADIO_WEEK);
		vo.setRadioPeriod(TimeConst.CODE_RADIO_PERIOD);
		vo.setRadioCheck(TimeConst.CODE_RADIO_CHECK);
	}
	
	/**
	 * 初期設定プルダウン設定
	 * @throws MospException 例外発生時
	 */
	protected void setPulldown() throws MospException {
		// VO準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 所定休日・法定休日勤務形態配列を取得
		String[][] aryDataType = mospParams.getProperties().getCodeArray(TimeConst.CODE_PRESCRIBED_LEGAL_HOLIDAY, true);
		// 年度を取得
		int fiscalYear = getInt(vo.getPltFiscalYear());
		// 年度プルダウンを設定
		vo.setAryPltFiscalYear(getYearArray(fiscalYear));
		// 対象年月準備(年度開始月)
		Date targetMonth = MonthUtility.getFiscalStartMonth(fiscalYear, mospParams);
		// 対象月取得
		String transferredMonth = getTransferredMonth();
		// 対象月確認
		if (transferredMonth != null) {
			// 対象年月再取得
			targetMonth = MonthUtility.getFiscalYearMonth(fiscalYear, getInt(transferredMonth), mospParams);
		}
		// 対象年月から年及び月を取得
		int year = DateUtility.getYear(targetMonth);
		int month = DateUtility.getMonth(targetMonth);
		// 年月指定時の期間の最終日を取得
		Date yearMonthTermLastDate = MonthUtility.getYearMonthTermLastDate(year, month, mospParams);
		// 範囲指定欄の勤務形態プルダウン作成
		String[][] pltWorkType = getWorkTypeArray(targetMonth, true);
		// 期間指定の日付プルダウン設定
		vo.setAryPltScheduleDay(getDayArray(DateUtility.getDay(yearMonthTermLastDate), false));
		// 日々の勤務形態プルダウン作成
		String[][] pltWorkTypeMonth = getWorkTypeArray(targetMonth, false);
		// 所定休日・法定休日勤務形態配列をリストに変換
		List<String[]> workTypeList = new ArrayList<String[]>(Arrays.asList(aryDataType));
		List<String[]> workTypeMonthList = new ArrayList<String[]>(Arrays.asList(aryDataType));
		// 勤務形態配列をリストに追加
		workTypeList.addAll(Arrays.asList(pltWorkType));
		workTypeMonthList.addAll(Arrays.asList(pltWorkTypeMonth));
		// 勤務形態配列リストを配列に変換しVOに設定
		vo.setAryPltWorkType(workTypeList.toArray(new String[workTypeList.size()][]));
		vo.setAryPltWorkTypeMonth(workTypeMonthList.toArray(new String[workTypeMonthList.size()][]));
	}
	
	/**
	 * パターンプルダウンを設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setPatternPulldown() throws MospException {
		// VOを取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		if (PlatformConst.MODE_ACTIVATE_DATE_CHANGING.equals(vo.getModeActivateDate())) {
			// 有効日変更状態の場合
			vo.setAryPltPattern(getInputActivateDatePulldown());
			// 処理終了
			return;
		}
		// 有効日決定状態の場合
		// パターン略称のプルダウン取得
		String[][] aryPltPattern = timeReference().workTypePattern().getSelectArray(getActivateYearDate());
		// データが存在しない場合
		if (aryPltPattern.length == 1) {
			// プルダウン設定
			vo.setAryPltPattern(new String[][]{ { "", "" } });
			return;
		}
		// プルダウン設定
		vo.setAryPltPattern(aryPltPattern);
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setDtoFields(ScheduleDtoInterface dto) throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		dto.setTmmScheduleId(vo.getRecordId());
		dto.setActivateDate(MonthUtility.getFiscalYearFirstDate(getInt(vo.getPltFiscalYear()), mospParams));
		dto.setFiscalYear(getInt(vo.getPltFiscalYear()));
		dto.setScheduleCode(vo.getTxtScheduleCode());
		dto.setScheduleName(vo.getTxtScheduleName());
		dto.setScheduleAbbr(vo.getTxtScheduleAbbr());
		dto.setPatternCode(vo.getPltPattern());
		dto.setWorkTypeChangeFlag(getInt(vo.getPltWorkTypeChange()));
		dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(ScheduleDtoInterface dto) {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setRecordId(dto.getTmmScheduleId());
		Date date = dto.getActivateDate();
		vo.setTxtEditActivateYear(getStringYear(date));
		vo.setTxtEditActivateMonth(getStringMonth(date));
		vo.setTxtEditActivateDay(getStringDay(date));
		vo.setPltFiscalYear(String.valueOf(dto.getFiscalYear()));
		vo.setTxtScheduleCode(dto.getScheduleCode());
		vo.setTxtScheduleName(dto.getScheduleName());
		vo.setTxtScheduleAbbr(dto.getScheduleAbbr());
		vo.setPltPattern(dto.getPatternCode());
		vo.setPltWorkTypeChange(String.valueOf(dto.getWorkTypeChangeFlag()));
		vo.setPltEditInactivate(String.valueOf(dto.getInactivateFlag()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param list 対象DTO
	 * @return 日にちごとのカレンダデータ
	 * @throws MospException 例外発生時
	 */
	protected List<ScheduleDateDtoInterface> setDtoFieldsDate(List<ScheduleDateDtoInterface> list)
			throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 対象年及び月を取得
		int year = DateUtility.getYear(vo.getTargetMonth());
		int month = DateUtility.getMonth(vo.getTargetMonth());
		// 対象期間最終日取得
		Date lastDate = MonthUtility.getYearMonthTermLastDate(year, month, mospParams);
		// 対象日として対象期間初日を取得
		Date targetDate = MonthUtility.getYearMonthTermFirstDate(year, month, mospParams);
		// リスト準備
		List<ScheduleDateDtoInterface> workList = new ArrayList<ScheduleDateDtoInterface>();
		// VOの値をDTOに設定
		String[] aryWorkTypeMonth = vo.getPltWorkTypeMonth();
		String[] aryTxtRemarkMonth = vo.getTxtRemarkMonth();
		// インデックス準備
		int index = 0;
		// 対象期間最終日まで処理
		while (targetDate.after(lastDate) == false) {
			ScheduleDateDtoInterface dto = new TmmScheduleDateDto();
			if (!list.isEmpty()) {
				ScheduleDateDtoInterface listDto = list.get(index);
				dto.setTmmScheduleDateId(listDto.getTmmScheduleDateId());
			}
			dto.setScheduleCode(vo.getTxtScheduleCode());
			dto.setActivateDate(getActivateYearDate());
			dto.setScheduleDate(targetDate);
			dto.setWorks(TimeBean.TIMES_WORK_DEFAULT);
			dto.setWorkTypeCode(aryWorkTypeMonth[index]);
			dto.setRemark(aryTxtRemarkMonth[index]);
			dto.setInactivateFlag(getInt(vo.getPltEditInactivate()));
			workList.add(dto);
			// インデックス加算
			++index;
			// 対象日加算
			targetDate = DateUtility.addDay(targetDate, 1);
		}
		return workList;
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param list 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsDate(List<ScheduleDateDtoInterface> list) throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		String[] aryPltWorkTypeMonth = new String[vo.getPltWorkTypeMonth().length];
		String[] aryTxtRemarkMonth = new String[vo.getTxtRemarkMonth().length];
		// 備考月毎に処理
		int i = 0;
		for (ScheduleDateDtoInterface dto : list) {
			// 設定
			aryPltWorkTypeMonth[i] = dto.getWorkTypeCode();
			aryTxtRemarkMonth[i] = dto.getRemark();
			i++;
		}
		vo.setPltWorkTypeMonth(aryPltWorkTypeMonth);
		vo.setTxtRemarkMonth(aryTxtRemarkMonth);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @param i 日付
	 * @throws MospException 例外発生時
	 */
	protected void setVoFieldsWork(ScheduleDateDtoInterface dto, int i) throws MospException {
		// VOを準備
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 勤務形態コード配列が空である場合
		if (vo.getPltWorkTypeMonth().length == 0) {
			// 処理終了
			return;
		}
		// DTOの値をVOに設定
		vo.setTxtScheduleCode(dto.getScheduleCode());
		// 勤務形態コードを取得
		String workTypeCode = dto.getWorkTypeCode();
		// 対象日を準備
		Date targetDate = dto.getScheduleDate();
		// 勤怠関連マスタ参照処理を準備
		TimeMasterBeanInterface timeMaster = timeReference().master();
		// VOから出勤時刻と退勤時刻と勤務時間を取得
		String[] aryLblStartMonth = vo.getAryLblStartMonth();
		String[] aryLblEndMonth = vo.getAryLblEndMonth();
		String[] aryLblWorkMonth = vo.getAryLblWorkMonth();
		// 出勤時刻を設定
		aryLblStartMonth[i] = getWorkStartTime(timeMaster, workTypeCode, targetDate);
		// 退勤時刻を設定
		aryLblEndMonth[i] = getWorkEndTime(timeMaster, workTypeCode, targetDate);
		// 勤務時間を設定
		aryLblWorkMonth[i] = getWorkTime(timeMaster, workTypeCode, targetDate);
		// VOに出勤時刻と退勤時刻と勤務時間を設定
		vo.setAryLblStartMonth(aryLblStartMonth);
		vo.setAryLblEndMonth(aryLblEndMonth);
		vo.setAryLblWorkMonth(aryLblWorkMonth);
	}
	
	/**
	 * 引数で渡された年月日と勤務形態コードから該当する勤務開始時間を取得する。<br>
	 * @param timeMaster   勤怠関連マスタ参照処理
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate   対象日
	 * @return 勤務開始時間(hh:MM)
	 * @throws MospException 例外発生時
	 */
	protected String getWorkStartTime(TimeMasterBeanInterface timeMaster, String workTypeCode, Date targetDate)
			throws MospException {
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workType = timeMaster.getWorkTypeEntity(workTypeCode, targetDate);
		// 出勤時刻を取得
		Date date = workType.getItemValue(TimeConst.CODE_WORKSTART, true);
		// 出勤時刻(文字列)を取得
		return TransStringUtility.getHourColonMinute(mospParams, date, DateUtility.getDefaultTime(), true);
	}
	
	/**
	 * 引数で渡された年月日と勤務形態コードから該当する勤務終了時間を取得する。<br>
	 * @param timeMaster   勤怠関連マスタ参照処理
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate   対象日
	 * @return 勤務終了時間(hh:MM)
	 * @throws MospException 例外発生時
	 */
	protected String getWorkEndTime(TimeMasterBeanInterface timeMaster, String workTypeCode, Date targetDate)
			throws MospException {
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workType = timeMaster.getWorkTypeEntity(workTypeCode, targetDate);
		// 退勤時刻を取得
		Date date = workType.getItemValue(TimeConst.CODE_WORKEND, true);
		// 退勤時刻(文字列)を取得
		return TransStringUtility.getHourColonMinute(mospParams, date, DateUtility.getDefaultTime(), true);
	}
	
	/**
	 * 引数で渡された年月日と勤務形態コードから該当する勤務時間を取得する。<br>
	 * @param timeMaster   勤怠関連マスタ参照処理
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate   対象日
	 * @return 勤務時間(hh.MM)
	 * @throws MospException 例外発生時
	 */
	protected String getWorkTime(TimeMasterBeanInterface timeMaster, String workTypeCode, Date targetDate)
			throws MospException {
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workType = timeMaster.getWorkTypeEntity(workTypeCode, targetDate);
		// 勤務時間を取得
		Date date = workType.getItemValue(TimeConst.CODE_WORKTIME, true);
		// 勤務時間(文字列)を取得
		return TransStringUtility.getHourPeriodMinute(mospParams, date, DateUtility.getDefaultTime(), false, true);
	}
	
	/**
	 * 該当年度の月データがあればボタン背景色の設定を行う。<br>
	 * @param scheduleCode カレンダコード
	 * @throws MospException 例外発生時
	 */
	protected void setButtonColor(String scheduleCode) throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 月ボタン背景色の初期化
		vo.setButtonBackColorJanuary(setButtonBackColor(0));
		vo.setButtonBackColorFebruary(setButtonBackColor(0));
		vo.setButtonBackColorMarch(setButtonBackColor(0));
		vo.setButtonBackColorApril(setButtonBackColor(0));
		vo.setButtonBackColorMay(setButtonBackColor(0));
		vo.setButtonBackColorJune(setButtonBackColor(0));
		vo.setButtonBackColorJuly(setButtonBackColor(0));
		vo.setButtonBackColorAugust(setButtonBackColor(0));
		vo.setButtonBackColorSeptember(setButtonBackColor(0));
		vo.setButtonBackColorOctorber(setButtonBackColor(0));
		vo.setButtonBackColorNovember(setButtonBackColor(0));
		vo.setButtonBackColorDecember(setButtonBackColor(0));
		// 対象年月(年度開始月(年度開始月の初日))を準備
		Date targetMonth = MonthUtility.getFiscalStartMonth(getInt(vo.getPltFiscalYear()), mospParams);
		// 表示された月以外の複製元データを全てコピーする
		for (int i = 0; i < TimeConst.CODE_DEFINITION_YEAR; i++) {
			// カレンダ日マスタ情報リスト取得
			List<ScheduleDateDtoInterface> list = getScheduleDateList(scheduleCode, targetMonth);
			// 月ボタン背景色の設定
			if (!list.isEmpty()) {
				// 対象月を取得
				int month = DateUtility.getMonth(targetMonth);
				// 対象月を確認
				if (month == getInt(TimeConst.CODE_DISPLAY_JANUARY)) {
					vo.setButtonBackColorJanuary(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_FEBRUARY)) {
					vo.setButtonBackColorFebruary(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_MARCH)) {
					vo.setButtonBackColorMarch(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_APRIL)) {
					vo.setButtonBackColorApril(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_MAY)) {
					vo.setButtonBackColorMay(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_JUNE)) {
					vo.setButtonBackColorJune(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_JULY)) {
					vo.setButtonBackColorJuly(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_AUGUST)) {
					vo.setButtonBackColorAugust(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_SEPTEMBER)) {
					vo.setButtonBackColorSeptember(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_OCTOBER)) {
					vo.setButtonBackColorOctorber(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_NOVEMBER)) {
					vo.setButtonBackColorNovember(setButtonBackColor(1));
				} else if (month == getInt(TimeConst.CODE_DISPLAY_DECEMBER)) {
					vo.setButtonBackColorDecember(setButtonBackColor(1));
				}
			}
			// 対象年月加算
			targetMonth = DateUtility.addMonth(targetMonth, 1);
		}
	}
	
	/**
	 * カレンダ日情報リストを取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param targetMonth  対象年月
	 * @return カレンダ日情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<ScheduleDateDtoInterface> getScheduleDateList(String scheduleCode, Date targetMonth)
			throws MospException {
		// 対象年及び月を取得
		int year = DateUtility.getYear(targetMonth);
		int month = DateUtility.getMonth(targetMonth);
		// 期間開始終了日取得
		Date startDate = MonthUtility.getYearMonthTermFirstDate(year, month, mospParams);
		Date endDate = MonthUtility.getYearMonthTermLastDate(year, month, mospParams);
		// カレンダ日マスタ情報リスト取得
		return timeReference().scheduleDate().findForList(scheduleCode, startDate, endDate);
	}
	
	/**
	 * ラジオチェックボックスの初期化を行う。<br>
	 */
	protected void initRadioValue() {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		vo.setRadioSelect(MospConst.CHECKBOX_OFF);
		vo.setRadioCheck(TimeConst.CODE_RADIO_CHECK);
		vo.setRadioPeriod(TimeConst.CODE_RADIO_PERIOD);
		vo.setRadioWeek(TimeConst.CODE_RADIO_WEEK);
	}
	
	/**
	 * 勤務形態プルダウン用配列を取得する。<br>
	 * @param targetDate 対象年月日
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException 例外発生時
	 */
	protected String[][] getWorkTypeArray(Date targetDate) throws MospException {
		return getWorkTypeArray(targetDate, true);
	}
	
	/**
	 * 勤務形態プルダウン用配列を取得する。<br>
	 * @param targetDate 対象年月日
	 * @param isName 名称表示(true：名称表示、false：略称表示)
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException 例外発生時
	 */
	protected String[][] getWorkTypeArray(Date targetDate, boolean isName) throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		return getWorkTypeArray(vo.getPltPattern(), targetDate, isName, isName, false, false);
	}
	
	/**
	 * 年を表す日付(年度の初日)を取得する。<br>
	 * 画面で入力された年度から取得する。<br>
	 * @return 年を表す日付(年度の初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected Date getActivateYearDate() throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 年を表す日付(年度の初日)を取得
		return MonthUtility.getYearDate(getInt(vo.getPltFiscalYear()), mospParams);
	}
	
	/**
	 * 年度と月を指定して実際の年月(年月の初日)を取得する。<br>
	 * 画面で入力された年度及び対象月から取得する。<br>
	 * @param month 対象月
	 * @return 実際の年月(年月の初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected Date getFiscalYearMonth(String month) throws MospException {
		// VO取得
		ScheduleCardVo vo = (ScheduleCardVo)mospParams.getVo();
		// 対象年度取得
		int fiscalYear = getInt(vo.getPltFiscalYear());
		// 年度と月を指定して実際の年月(年月の初日)を取得
		return MonthUtility.getFiscalYearMonth(fiscalYear, getInt(month), mospParams);
	}
	
	/**
	 * プルダウン用配列にコードがあるかどうか確認する。
	 * @param code コード
	 * @param array プルダウン用配列
	 * @return 確認結果(true：有り、false：無し)
	 */
	protected boolean contains(String code, String[][] array) {
		for (String[] strings : array) {
			if (code.equals(strings[0])) {
				return true;
			}
		}
		return false;
	}
	
}
