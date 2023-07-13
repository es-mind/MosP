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
package jp.mosp.time.calculation.action;

import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TotalTimeBaseAction;
import jp.mosp.time.bean.TotalTimeCorrectionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeRegistBeanInterface;
import jp.mosp.time.calculation.vo.TotalTimeCardVo;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;
import jp.mosp.time.dto.settings.TotalLeaveDtoInterface;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalAbsenceDto;
import jp.mosp.time.dto.settings.impl.TmdTotalLeaveDto;
import jp.mosp.time.dto.settings.impl.TmdTotalOtherVacationDto;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 集計結果一覧画面で選択した従業員の集計月毎の勤怠情報を修正する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li><li>
 * {@link #CMD_INSERT}
 * </li></ul>
 */
public class TotalTimeCardAction extends TotalTimeBaseAction {
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 集計結果一覧画面で選択した従業員の集計月の勤怠情報を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW	= "TM3131";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面で入力した内容を破棄し、登録済みの情報を再表示する。<br>
	 * 実行時には確認ダイアログを表示し、編集中の内容が破棄される旨を伝える。<br>
	 */
	public static final String	CMD_RE_SHOW		= "TM3132";
	
	/**
	 * 項目表示切替コマンド。<br>
	 * <br>
	 * 登録済の情報を出力するラベル表示と新たに情報を入力するためのテキストボックス・プルダウンの表示を切り替える。<br>
	 * 該当のボタンをクリックする度に表示が交互に切り替わり、切り替え前後の入出力内容は常に保持しておく。<br>
	 */
	public static final String	CMD_UPDATE		= "TM3133";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 各種入力欄に入力されている内容を勤怠情報テーブルに登録する。<br>
	 * 入力チェックを行い、全ての項目が入力されていない場合、エラーメッセージにて通知して処理は実行されない。<br>
	 */
	public static final String	CMD_INSERT		= "TM3135";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public TotalTimeCardAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new TotalTimeCardVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			reShow();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 項目表示切替
			prepareVo(true, false);
			changeMode();
		} else if (mospParams.getCommand().equals(CMD_INSERT)) {
			// 登録
			prepareVo();
			regist();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void show() throws MospException {
		// VO準備
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// MosP処理情報から個人IDを取得
		String personalId = getTargetPersonalId();
		// MosP処理情報から締日関連情報を取得しVOに設定
		getCutoffParams();
		// 締日関連情報を設定
		setCutoffInfo();
		// VOから締日コード及び対象年月取得
		String cutoffCode = vo.getCutoffCode();
		int targetYear = vo.getTargetYear();
		int targetMonth = vo.getTargetMonth();
		// 画面上部の社員情報を取得(対象日に締期間における基準日を設定)
		setEmployeeInfo(personalId, vo.getCutoffTermTargetDate());
		// 初期値設定
		setDefaultValues();
		setCorrectionVoFields();
		// 表示モード(新規登録モード)を設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		// 締状態を取得
		int state = timeReference().totalTimeTransaction().getStoredCutoffState(targetYear, targetMonth, cutoffCode);
		// 締状態フラグ(true：確定、false：確定以外)を設定
		vo.setTightened(state == TimeConst.CODE_CUTOFF_STATE_TIGHTENED);
	}
	
	/**
	 * 再表示処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void reShow() throws MospException {
		// 締日関連情報を設定
		setCutoffInfo();
		// 初期値設定
		setDefaultValues();
		setCorrectionVoFields();
		// VO準備
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// 新規登録モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
	}
	
	/**
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void changeMode() throws MospException {
		// VO準備
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// 編集モード設定
		if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
			vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
		} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
			vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
			setDefaultValues();
		}
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void regist() throws MospException {
		// VO準備
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// 登録クラス取得
		TotalTimeRegistBeanInterface registTotalTime = time().totalTimeRegist();
		TotalTimeCorrectionRegistBeanInterface correctionRegist = time().totalTimeCorrectionRegist();
		// VOから対象年月取得
		String personalId = vo.getPersonalId();
		int targetYear = vo.getTargetYear();
		int targetMonth = vo.getTargetMonth();
		// DTOの準備
		TotalTimeDataDtoInterface oldTotalTimeDto = timeReference().totalTime().findForKey(personalId, targetYear,
				targetMonth);
		List<TotalLeaveDtoInterface> oldTotalLeaveList = timeReference().totalLeave().getTotalLeaveList(personalId,
				targetYear, targetMonth);
		List<TotalOtherVacationDtoInterface> oldTotalOtherVacationList = timeReference().totalOtherVacation()
			.getTotalOtherVacationList(personalId, targetYear, targetMonth);
		List<TotalAbsenceDtoInterface> oldTotalAbsenceList = timeReference().totalAbsence()
			.getTotalAbsenceList(personalId, targetYear, targetMonth);
		TotalTimeDataDtoInterface workTimeDto = registTotalTime.getInitDto();
		// DTOに値を設定
		setDtoWorkTimeFields(workTimeDto);
		// 特別休暇の登録処理
		for (int i = 0; i < vo.getAryTxtTimesSpecialLeave().length; i++) {
			TotalLeaveDtoInterface dto = new TmdTotalLeaveDto();
			dto.setPersonalId(workTimeDto.getPersonalId());
			dto.setCalculationYear(workTimeDto.getCalculationYear());
			dto.setCalculationMonth(workTimeDto.getCalculationMonth());
			setDtoSpecial(dto, i);
			time().totalLeaveRegist().regist(dto);
		}
		// その他休暇の登録処理
		for (int i = 0; i < vo.getAryTxtTimesOtherVacation().length; i++) {
			TotalOtherVacationDtoInterface dto = new TmdTotalOtherVacationDto();
			dto.setPersonalId(workTimeDto.getPersonalId());
			dto.setCalculationYear(workTimeDto.getCalculationYear());
			dto.setCalculationMonth(workTimeDto.getCalculationMonth());
			setDtoOther(dto, i);
			time().totalOtherVacationRegist().regist(dto);
		}
		// 欠勤の登録処理
		for (int i = 0; i < vo.getAryTxtDeduction().length; i++) {
			TotalAbsenceDtoInterface dto = new TmdTotalAbsenceDto();
			dto.setPersonalId(workTimeDto.getPersonalId());
			dto.setCalculationYear(workTimeDto.getCalculationYear());
			dto.setCalculationMonth(workTimeDto.getCalculationMonth());
			setDtoAbsence(dto, i);
			time().totalAbsenceRegist().regist(dto);
		}
		
		// 登録処理
		registTotalTime.update(workTimeDto);
		TotalTimeCorrectionDtoInterface totalTimeCorrectionDto = correctionRegist.getInitDto();
		setTotalTimeCorrectionDtoFields(totalTimeCorrectionDto);
		correctionRegist.insertTotalTime(totalTimeCorrectionDto, oldTotalTimeDto, workTimeDto);
		for (TotalLeaveDtoInterface oldDto : oldTotalLeaveList) {
			List<TotalLeaveDtoInterface> list = timeReference().totalLeave().getTotalLeaveList(personalId, targetYear,
					targetMonth);
			for (TotalLeaveDtoInterface newDto : list) {
				// フラグ準備
				boolean isDay = false;
				boolean isHour = false;
				// コードが違う場合
				if (!oldDto.getHolidayCode().equals(newDto.getHolidayCode())) {
					continue;
				}
				if (oldDto.getTimes() != newDto.getTimes()) {
					isDay = true;
				}
				if (oldDto.getHours() != newDto.getHours()) {
					isHour = true;
				}
				if (isDay || isHour) {
					correctionRegist.insertLeave(totalTimeCorrectionDto, oldDto, newDto, isDay, isHour);
				}
			}
		}
		for (TotalOtherVacationDtoInterface oldDto : oldTotalOtherVacationList) {
			List<TotalOtherVacationDtoInterface> list = timeReference().totalOtherVacation()
				.getTotalOtherVacationList(personalId, targetYear, targetMonth);
			for (TotalOtherVacationDtoInterface newDto : list) {
				// フラグ準備
				boolean isDay = false;
				boolean isHour = false;
				// コードが違う場合
				if (!oldDto.getHolidayCode().equals(newDto.getHolidayCode())) {
					continue;
				}
				if (oldDto.getTimes() != newDto.getTimes()) {
					isDay = true;
				}
				if (oldDto.getHours() != newDto.getHours()) {
					isHour = true;
				}
				if (isDay || isHour) {
					correctionRegist.insertOtherVacation(totalTimeCorrectionDto, oldDto, newDto, isDay, isHour);
				}
			}
		}
		for (TotalAbsenceDtoInterface oldDto : oldTotalAbsenceList) {
			// 勤怠集計欠勤データリストを取得
			List<TotalAbsenceDtoInterface> list = timeReference().totalAbsence().getTotalAbsenceList(personalId,
					targetYear, targetMonth);
			// 勤怠集計欠勤データ毎に処理
			for (TotalAbsenceDtoInterface newDto : list) {
				// フラグ準備
				boolean isDay = false;
				boolean isHour = false;
				// コードが違う場合
				if (!oldDto.getAbsenceCode().equals(newDto.getAbsenceCode())) {
					continue;
				}
				// 日数が違う場合
				if (oldDto.getTimes() != newDto.getTimes()) {
					isDay = true;
				}
				// 時間数が違う場合
				if (oldDto.getHours() != newDto.getHours()) {
					isHour = true;
				}
				if (isDay || isHour) {
					correctionRegist.insertAbsence(totalTimeCorrectionDto, oldDto, newDto, isDay, isHour);
				}
			}
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
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 登録後の情報をVOに設定
		setVoWorkTimeFields(workTimeDto);
		setLeaveVoFields();
		setCorrectionVoFields();
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @throws MospException 例外処理発生時
	 */
	public void setDefaultValues() throws MospException {
		// VO準備
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// 計算年月取得
		int targetYear = vo.getTargetYear();
		int targetMonth = vo.getTargetMonth();
		// VOに初期値を設定
		vo.setLblMonth(targetYear + mospParams.getName("Year") + targetMonth + mospParams.getName("Month"));
		vo.setLblCorrectionHistory("");
		vo.setTxtCorrectionReason("");
		// 勤怠集計情報取得
		TotalTimeDataDtoInterface totalTimeDto = timeReference().totalTime().findForKey(vo.getPersonalId(), targetYear,
				targetMonth);
		if (totalTimeDto != null) {
			setVoWorkTimeFields(totalTimeDto);
		}
		// 休暇
		setLeaveVoFields();
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setDtoWorkTimeFields(TotalTimeDataDtoInterface dto) throws MospException {
		// VO取得
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// VOの値をDTOに設定
		// レコード識別ID
		dto.setTmdTotalTimeId(vo.getTmdTotalTimeId());
		// 個人ID
		dto.setPersonalId(vo.getPersonalId());
		// 計算年月
		dto.setCalculationYear(vo.getTargetYear());
		dto.setCalculationMonth(vo.getTargetMonth());
		// 集計日
		dto.setCalculationDate(vo.getCalculationDate());
		// 減額対象時間
		dto.setDecreaseTime(getTimeValue(vo.getTxtDecreaseTimeHour(), vo.getTxtDecreaseTimeMinute()));
		// 直行回数
		dto.setDirectStart(getInt(vo.getTxtTimesNonstop()));
		// 直帰回数
		dto.setDirectEnd(getInt(vo.getTxtTimesNoreturn()));
		// 45時間超残業時間
		dto.setFortyFiveHourOvertime(getTimeValue(vo.getTxt45HourOverTimeHour(), vo.getTxt45HourOverTimeMinute()));
		
		// 合計遅刻日数
		dto.setLateDays(getInt(vo.getTxtLateDays()));
		// 遅刻30分以上日数
		dto.setLateThirtyMinutesOrMore(getInt(vo.getTxtLateThirtyMinutesOrMore()));
		// 遅刻30分未満日数
		dto.setLateLessThanThirtyMinutes(getInt(vo.getTxtLateLessThanThirtyMinutes()));
		
		// 深夜時間
		dto.setLateNight(getTimeValue(vo.getTxtLateNightHour(), vo.getTxtLateNightMinute()));
		
		// 合計遅刻時間
		dto.setLateTime(getTimeValue(vo.getTxtLateTimeHour(), vo.getTxtLateTimeMinute()));
		// 遅刻30分以上時間
		dto.setLateThirtyMinutesOrMoreTime(
				getTimeValue(vo.getTxtLateThirtyMinutesOrMoreTimeHour(), vo.getTxtLateThirtyMinutesOrMoreTimeMinute()));
		// 遅刻30分未満時間
		dto.setLateLessThanThirtyMinutesTime(getTimeValue(vo.getTxtLateLessThanThirtyMinutesTimeHour(),
				vo.getTxtLateLessThanThirtyMinutesTimeMinute()));
		
		// 合計早退日数
		dto.setLeaveEarlyDays(getInt(vo.getTxtLeaveEarlyDays()));
		// 早退30分以上日数
		dto.setLeaveEarlyThirtyMinutesOrMore(getInt(vo.getTxtLeaveEarlyThirtyMinutesOrMore()));
		// 早退30分未満日数
		dto.setLeaveEarlyLessThanThirtyMinutes(getInt(vo.getTxtLeaveEarlyLessThanThirtyMinutes()));
		
		// 法定休日出勤日数
		dto.setLegalWorkOnHoliday(getDouble(vo.getTxtLegalWorkOnHoliday()));
		// 有給休暇時間
		dto.setPaidHolidayHour(getInt(vo.getTxtPaidholidayHour()));
		// 私用外出時間
		dto.setPrivateTime(getTimeValue(vo.getTxtPrivateHour(), vo.getTxtPrivateMinute()));
		// 公用外出時間
		dto.setPublicTime(getTimeValue(vo.getTxtPublicHour(), vo.getTxtPublicMinute()));
		// 分単位休暇
		dto.setMinutelyHolidayATime(0);
		dto.setMinutelyHolidayBTime(0);
		// 休憩時間
		dto.setRestTime(getTimeValue(vo.getTxtRestTimeHour(), vo.getTxtRestTimeMinute()));
		// 深夜休憩時間
		dto.setRestLateNight(getTimeValue(vo.getTxtRestLateNightHour(), vo.getTxtRestLateNightMinute()));
		// 所定休出休憩時間
		dto.setRestWorkOnSpecificHoliday(
				getTimeValue(vo.getTxtRestWorkOnSpecificHour(), vo.getTxtRestWorkOnSpecificMinute()));
		// 法定休出休憩時間
		dto.setRestWorkOnHoliday(getTimeValue(vo.getTxtRestWorkOnLegalHour(), vo.getTxtRestWorkOnLegalMinute()));
		// 残業時間
		dto.setOvertime(getTimeValue(vo.getTxtOverTimeHour(), vo.getTxtOverTimeMinute()));
		// 法定内残業時間
		dto.setOvertimeIn(getTimeValue(vo.getTxtOverTimeInHour(), vo.getTxtOverTimeInMinute()));
		// 法定外残業時間
		dto.setOvertimeOut(getTimeValue(vo.getTxtOverTimeOutHour(), vo.getTxtOverTimeOutMinute()));
		// 60時間超残業時間
		dto.setSixtyHourOvertime(getTimeValue(vo.getTxt60HourOverTimeHour(), vo.getTxt60HourOverTimeMinute()));
		// 所定休日時間外時間
		dto.setSpecificOvertime(getTimeValue(vo.getTxtSpecificOverTimeHour(), vo.getTxtSpecificOverTimeMiunte()));
		// 所定休日出勤日数
		dto.setSpecificWorkOnHoliday(getDouble(vo.getTxtSpecificWorkOnHoliday()));
		// 代替休暇日数
		dto.setTimesAlternative(getDouble(vo.getTxtTimesAlternative()));
		// 代休日数
		dto.setTimesCompensation(getDouble(vo.getTxtTimesCompensation()));
		// 休日日数
		dto.setTimesHoliday(getInt(vo.getTxtTimesHoliday()));
		// 合計遅刻回数
		dto.setTimesLate(getInt(vo.getTxtLateDays()));
		// 深夜代休日数
		dto.setTimesLateCompensation(getDouble(vo.getTxtTimesLateCompensation()));
		// 合計早退回数
		dto.setTimesLeaveEarly(getInt(vo.getTxtLeaveEarlyDays()));
		// 法定代休日数
		dto.setTimesLegalCompensation(getDouble(vo.getTxtTimesLegalCompensation()));
		// 法定休日日数
		dto.setTimesLegalHoliday(getInt(vo.getTxtTimesLegalHoliday()));
		// 有給休暇日数
		dto.setTimesPaidHoliday(getDouble(vo.getTxtTimesPaidHoliday()));
		// 所定代休日数
		dto.setTimesSpecificCompensation(getDouble(vo.getTxtTimesSpecificCompensation()));
		// 所定休日日数
		dto.setTimesSpecificHoliday(getInt(vo.getTxtTimesSpecificHoliday()));
		// ストック休暇日数
		dto.setTimesStockHoliday(getDouble(vo.getTxtTimesStockHoliday()));
		// 振替休日日数
		dto.setTimesHolidaySubstitute(getDouble(vo.getTxtTimesSubstitute()));
		// 出勤回数
		dto.setTimesWork(getInt(vo.getTxtTimesWork()));
		// 出勤日数
		dto.setTimesWorkDate(getDouble(vo.getTxtTimesWorkDate()));
		// 平日時間外時間
		dto.setWeekDayOvertime(getTimeValue(vo.getTxtWeekDayOverTimeHour(), vo.getTxtWeekDayOverTimeMinute()));
		// 法定休出時間
		dto.setWorkOnHoliday(getTimeValue(vo.getTxtRestWorkOnLegalHour(), vo.getTxtRestWorkOnLegalMinute()));
		// 所定休出時間
		dto.setWorkOnSpecificHoliday(
				getTimeValue(vo.getTxtRestWorkOnSpecificHour(), vo.getTxtRestWorkOnSpecificMinute()));
		// 勤務時間
		dto.setWorkTime(getTimeValue(vo.getTxtWorkTimeHour(), vo.getTxtWorkTimeMinute()));
		// 出勤対象日数
		dto.setTimesTotalWorkDate(getInt(vo.getTxtTimesTotalWorkDate()));
		// 残業回数
		dto.setTimesOvertime(getInt(vo.getTxtTimesOvertime()));
		// 法定休出時間
		dto.setWorkOnHoliday(getTimeValue(vo.getTxtWorkOnHolidayHour(), vo.getTxtWorkOnHolidayMinute()));
		// 所定休出時間
		dto.setWorkOnSpecificHoliday(
				getTimeValue(vo.getTxtWorkSpecificOnHolidayHour(), vo.getTxtWorkSpecificOnHolidayMinute()));
		// 休日出勤回数
		dto.setTimesWorkingHoliday(getInt(vo.getTxtTimesWorkingHoliday()));
		
		// 合計早退時間
		dto.setLeaveEarlyTime(getTimeValue(vo.getTxtLeaveEarlyTimeHour(), vo.getTxtLeaveEarlyTimeMinute()));
		// 早退30分以上時間
		dto.setLeaveEarlyThirtyMinutesOrMoreTime(getTimeValue(vo.getTxtLeaveEarlyThirtyMinutesOrMoreTimeHour(),
				vo.getTxtLeaveEarlyThirtyMinutesOrMoreTimeMinute()));
		// 早退30分未満時間
		dto.setLeaveEarlyLessThanThirtyMinutesTime(getTimeValue(vo.getTxtLeaveEarlyLessThanThirtyMinutesTimeHour(),
				vo.getTxtLeaveEarlyLessThanThirtyMinutesTimeMinute()));
		
		// 法定振替休日日数
		dto.setTimesLegalHolidaySubstitute(getDouble(vo.getTxtTimesLegalHolidaySubstitute()));
		// 所定振替休日日数
		dto.setTimesSpecificHolidaySubstitute(getDouble(vo.getTxtTimesSpecificHolidaySubstitute()));
		// 法定代休発生日数
		dto.setLegalCompensationOccurred(getDouble(vo.getTxtLegalCompensationOccurred()));
		// 所定代休発生日数
		dto.setSpecificCompensationOccurred(getDouble(vo.getTxtSpecificCompensationOccurred()));
		// 深夜代休発生日数
		dto.setLateCompensationOccurred(getDouble(vo.getTxtLateCompensationOccurred()));
		// 法定代休未使用日数
		dto.setLegalCompensationUnused(getDouble(vo.getTxtLegalCompensationUnused()));
		// 所定代休未使用日数
		dto.setSpecificCompensationUnused(getDouble(vo.getTxtSpecificCompensationUnused()));
		// 深夜代休未使用日数
		dto.setLateCompensationUnused(getDouble(vo.getTxtLateCompensationUnused()));
		// 特別休暇合計日数
		dto.setTotalSpecialHoliday(getDouble(vo.getTxtTotalSpecialHoliday()));
		// 特別休暇時間数
		dto.setSpecialHolidayHour(getInt(vo.getTxtSpecialHolidayHour()));
		// その他休暇合計日数
		dto.setTotalOtherHoliday(getDouble(vo.getTxtTotalOtherHoliday()));
		// その他休暇時間数
		dto.setOtherHolidayHour(getInt(vo.getTxtOtherHolidayHour()));
		// 欠勤合計日数
		dto.setTotalAbsence(getDouble(vo.getTxtTotalDeduction()));
		// 欠勤時間数
		dto.setAbsenceHour(getInt(vo.getTxtDeductionHour()));
		// 出勤実績日数
		dto.setTimesAchievement(getInt(vo.getTxtTimesAchievement()));
		// 所定勤務時間
		dto.setSpecificWorkTime(getTimeValue(vo.getTxtSpecificWorkTimeHour(), vo.getTxtSpecificWorkTimeMinute()));
		// 無給時短時間
		dto.setShortUnpaid(getTimeValue(vo.getTxtUnpaidShortTimeHour(), vo.getTxtUnpaidShortTimeMinute()));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param i 特別休暇Index
	 * @throws MospException 例外発生時
	 */
	protected void setDtoSpecial(TotalLeaveDtoInterface dto, int i) throws MospException {
		// VO取得
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		dto.setTmdTotalLeaveId(vo.getAryTimesSpecialLeaveId()[i]);
		dto.setHolidayCode(vo.getAryTxtTimesSpecialLeaveCode()[i]);
		dto.setTimes(getDouble(vo.getAryTxtTimesSpecialLeave(i)));
		dto.setHours(getInt(vo.getAryTxtTimesSpecialHour(i)));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param i その他休暇Index
	 * @throws MospException 例外発生時
	 */
	protected void setDtoOther(TotalOtherVacationDtoInterface dto, int i) throws MospException {
		// VO取得
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		dto.setTmdTotalOtherVacationId(vo.getAryTimesOtherVacationId()[i]);
		dto.setHolidayCode(vo.getAryTxtTimesOtherVacationCode()[i]);
		dto.setTimes(getDouble(vo.getAryTxtTimesOtherVacation(i)));
		dto.setHours(getInt(vo.getAryTxtTimesOtherVacationHour(i)));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @param i 欠勤Index
	 * @throws MospException 例外発生時
	 */
	protected void setDtoAbsence(TotalAbsenceDtoInterface dto, int i) throws MospException {
		// VO取得
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		dto.setTmdTotalAbsenceId(vo.getAryDeductionId()[i]);
		dto.setAbsenceCode(vo.getAryTxtDeductionCode()[i]);
		dto.setTimes(getDouble(vo.getAryTxtDeduction(i)));
		dto.setHours(getInt(vo.getAryTxtDeductionHour(i)));
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void setTotalTimeCorrectionDtoFields(TotalTimeCorrectionDtoInterface dto) throws MospException {
		// VO取得
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// 個人ID
		dto.setPersonalId(vo.getPersonalId());
		// 年
		dto.setCalculationYear(vo.getTargetYear());
		// 月
		dto.setCalculationMonth(vo.getTargetMonth());
		// 修正番号
		TotalTimeCorrectionDtoInterface latestDto = timeReference().totalTimeCorrection()
			.getLatestTotalTimeCorrectionInfo(vo.getPersonalId(), vo.getTargetYear(), vo.getTargetMonth());
		int correctionTimes = 1;
		if (latestDto != null) {
			correctionTimes += latestDto.getCorrectionTimes();
		}
		dto.setCorrectionTimes(correctionTimes);
		// 修正日時
		dto.setCorrectionDate(DateUtility.getSystemTimeAndSecond());
		// 修正個人ID
		dto.setCorrectionPersonalId(mospParams.getUser().getPersonalId());
		// 修正理由
		dto.setCorrectionReason(vo.getTxtCorrectionReason());
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException 例外処理発生時
	 */
	protected void setVoWorkTimeFields(TotalTimeDataDtoInterface dto) throws MospException {
		// VO取得
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// DTOの値をVOに設定
		vo.setLblEmployeeCode(getEmployeeCode(dto.getPersonalId(), dto.getCalculationDate()));
		// レコード識別ID
		vo.setTmdTotalTimeId(dto.getTmdTotalTimeId());
		// 減額対象時間
		vo.setTxtDecreaseTimeHour(String.valueOf(dto.getDecreaseTime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtDecreaseTimeMinute(String.valueOf(dto.getDecreaseTime() % TimeConst.CODE_DEFINITION_HOUR));
		// 45時間超残業時間
		vo.setTxt45HourOverTimeHour(String.valueOf(dto.getFortyFiveHourOvertime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxt45HourOverTimeMinute(String.valueOf(dto.getFortyFiveHourOvertime() % TimeConst.CODE_DEFINITION_HOUR));
		
		// 合計遅刻日数
		vo.setTxtLateDays(String.valueOf(dto.getLateDays()));
		// 遅刻30分以上日数
		vo.setTxtLateThirtyMinutesOrMore(String.valueOf(dto.getLateThirtyMinutesOrMore()));
		// 遅刻30分未満日数
		vo.setTxtLateLessThanThirtyMinutes(String.valueOf(dto.getLateLessThanThirtyMinutes()));
		
		// 深夜時間
		vo.setTxtLateNightHour(String.valueOf(dto.getLateNight() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtLateNightMinute(String.valueOf(dto.getLateNight() % TimeConst.CODE_DEFINITION_HOUR));
		
		// 合計遅刻時間
		vo.setTxtLateTimeHour(String.valueOf(dto.getLateTime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtLateTimeMinute(String.valueOf(dto.getLateTime() % TimeConst.CODE_DEFINITION_HOUR));
		// 遅刻30分以上時間
		vo.setTxtLateThirtyMinutesOrMoreTimeHour(getHour(dto.getLateThirtyMinutesOrMoreTime()));
		vo.setTxtLateThirtyMinutesOrMoreTimeMinute(getMinute(dto.getLateThirtyMinutesOrMoreTime()));
		// 遅刻30分未満時間
		vo.setTxtLateLessThanThirtyMinutesTimeHour(getHour(dto.getLateLessThanThirtyMinutesTime()));
		vo.setTxtLateLessThanThirtyMinutesTimeMinute(getMinute(dto.getLateLessThanThirtyMinutesTime()));
		
		// 合計早退日数
		vo.setTxtLeaveEarlyDays(String.valueOf(dto.getLeaveEarlyDays()));
		// 早退30分以上日数
		vo.setTxtLeaveEarlyThirtyMinutesOrMore(String.valueOf(dto.getLeaveEarlyThirtyMinutesOrMore()));
		// 早退30分未満日数
		vo.setTxtLeaveEarlyLessThanThirtyMinutes(String.valueOf(dto.getLeaveEarlyLessThanThirtyMinutes()));
		
		// 法定休日出勤日数
		vo.setTxtLegalWorkOnHoliday(String.valueOf(dto.getLegalWorkOnHoliday()));
		// 有給休暇時間
		vo.setTxtPaidholidayHour(String.valueOf(dto.getPaidHolidayHour()));
		// 私用外出時間
		vo.setTxtPrivateHour(String.valueOf(dto.getPrivateTime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtPrivateMinute(String.valueOf(dto.getPrivateTime() % TimeConst.CODE_DEFINITION_HOUR));
		// 公用外出時間
		vo.setTxtPublicHour(String.valueOf(dto.getPublicTime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtPublicMinute(String.valueOf(dto.getPublicTime() % TimeConst.CODE_DEFINITION_HOUR));
		// 深夜休憩時間
		vo.setTxtRestLateNightHour(String.valueOf(dto.getRestLateNight() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtRestLateNightMinute(String.valueOf(dto.getRestLateNight() % TimeConst.CODE_DEFINITION_HOUR));
		// 休憩時間
		vo.setTxtRestTimeHour(String.valueOf(dto.getRestTime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtRestTimeMinute(String.valueOf(dto.getRestTime() % TimeConst.CODE_DEFINITION_HOUR));
		// 法定休出休憩時間
		vo.setTxtRestWorkOnLegalHour(String.valueOf(dto.getRestWorkOnHoliday() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtRestWorkOnLegalMinute(String.valueOf(dto.getRestWorkOnHoliday() % TimeConst.CODE_DEFINITION_HOUR));
		// 60時間超残業時間
		vo.setTxt60HourOverTimeHour(String.valueOf(dto.getSixtyHourOvertime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxt60HourOverTimeMinute(String.valueOf(dto.getSixtyHourOvertime() % TimeConst.CODE_DEFINITION_HOUR));
		// 所定休日時間外時間
		vo.setTxtSpecificOverTimeHour(String.valueOf(dto.getSpecificOvertime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtSpecificOverTimeMiunte(String.valueOf(dto.getSpecificOvertime() % TimeConst.CODE_DEFINITION_HOUR));
		// 所定休日時間外時間
		vo.setTxtSpecificWorkOnHoliday(String.valueOf(dto.getSpecificWorkOnHoliday()));
		// 所定勤務時間
		vo.setTxtSpecificWorkTimeHour(String.valueOf(dto.getSpecificWorkTime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtSpecificWorkTimeMinute(String.valueOf(dto.getSpecificWorkTime() % TimeConst.CODE_DEFINITION_HOUR));
		// 無休時短時間
		vo.setTxtUnpaidShortTimeHour(String.valueOf(dto.getShortUnpaid() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtUnpaidShortTimeMinute(String.valueOf(dto.getShortUnpaid() % TimeConst.CODE_DEFINITION_HOUR));
		// 代替休暇日数
		vo.setTxtTimesAlternative(String.valueOf(dto.getTimesAlternative()));
		// 代休日数
		vo.setTxtTimesCompensation(String.valueOf(dto.getTimesCompensation()));
		// 休日日数
		vo.setTxtTimesHoliday(String.valueOf(dto.getTimesHoliday()));
		// 深夜代休日数
		vo.setTxtTimesLateCompensation(String.valueOf(dto.getTimesLateCompensation()));
		// 法定代休日数
		vo.setTxtTimesLegalCompensation(String.valueOf(dto.getTimesLegalCompensation()));
		// 法定休日日数
		vo.setTxtTimesLegalHoliday(String.valueOf(dto.getTimesLegalHoliday()));
		// 直行回数
		vo.setTxtTimesNonstop(String.valueOf(dto.getDirectStart()));
		// 直帰回数
		vo.setTxtTimesNoreturn(String.valueOf(dto.getDirectEnd()));
		// 有給休暇日数
		vo.setTxtTimesPaidHoliday(String.valueOf(dto.getTimesPaidHoliday()));
		// 所定代休日数
		vo.setTxtTimesSpecificCompensation(String.valueOf(dto.getTimesSpecificCompensation()));
		// 所定休日日数
		vo.setTxtTimesSpecificHoliday(String.valueOf(dto.getTimesSpecificHoliday()));
		// ストック休暇日数
		vo.setTxtTimesStockHoliday(String.valueOf(dto.getTimesStockHoliday()));
		// 振替休日日数
		vo.setTxtTimesSubstitute(String.valueOf(dto.getTimesHolidaySubstitute()));
		// 出勤回数
		vo.setTxtTimesWork(String.valueOf(dto.getTimesWork()));
		// 出勤日数
		vo.setTxtTimesWorkDate(String.valueOf(dto.getTimesWorkDate()));
		// 平日時間外時間
		vo.setTxtWeekDayOverTimeHour(String.valueOf(dto.getWeekDayOvertime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtWeekDayOverTimeMinute(String.valueOf(dto.getWeekDayOvertime() % TimeConst.CODE_DEFINITION_HOUR));
		// 所定休出時間
		vo.setTxtRestWorkOnSpecificHour(
				String.valueOf(dto.getRestWorkOnSpecificHoliday() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtRestWorkOnSpecificMinute(
				String.valueOf(dto.getRestWorkOnSpecificHoliday() % TimeConst.CODE_DEFINITION_HOUR));
		// 法定休出時間
		vo.setTxtRestWorkOnLegalHour(String.valueOf(dto.getRestWorkOnHoliday() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtRestWorkOnLegalMinute(String.valueOf(dto.getRestWorkOnHoliday() % TimeConst.CODE_DEFINITION_HOUR));
		// 残業時間
		vo.setTxtOverTimeHour(String.valueOf(dto.getOvertime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtOverTimeMinute(String.valueOf(dto.getOvertime() % TimeConst.CODE_DEFINITION_HOUR));
		// 法定内残業時間
		vo.setTxtOverTimeInHour(String.valueOf(dto.getOvertimeIn() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtOverTimeInMinute(String.valueOf(dto.getOvertimeIn() % TimeConst.CODE_DEFINITION_HOUR));
		// 法定外残業時間
		vo.setTxtOverTimeOutHour(String.valueOf(dto.getOvertimeOut() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtOverTimeOutMinute(String.valueOf(dto.getOvertimeOut() % TimeConst.CODE_DEFINITION_HOUR));
		// 勤務時間
		vo.setTxtWorkTimeHour(String.valueOf(dto.getWorkTime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtWorkTimeMinute(String.valueOf(dto.getWorkTime() % TimeConst.CODE_DEFINITION_HOUR));
		// 出勤実績日数
		vo.setTxtTimesAchievement(String.valueOf(dto.getTimesAchievement()));
		// 出勤対象日数
		vo.setTxtTimesTotalWorkDate(String.valueOf(dto.getTimesTotalWorkDate()));
		// 残業回数
		vo.setTxtTimesOvertime(String.valueOf(dto.getTimesOvertime()));
		// 法定休出時間
		vo.setTxtWorkOnHolidayHour(String.valueOf(dto.getWorkOnHoliday() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtWorkOnHolidayMinute(String.valueOf(dto.getWorkOnHoliday() % TimeConst.CODE_DEFINITION_HOUR));
		// 所定休出時間
		vo.setTxtWorkSpecificOnHolidayHour(
				String.valueOf(dto.getWorkOnSpecificHoliday() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtWorkSpecificOnHolidayMinute(
				String.valueOf(dto.getWorkOnSpecificHoliday() % TimeConst.CODE_DEFINITION_HOUR));
		// 休日出勤回数
		vo.setTxtTimesWorkingHoliday(String.valueOf(dto.getTimesWorkingHoliday()));
		
		// 合計早退時間
		vo.setTxtLeaveEarlyTimeHour(String.valueOf(dto.getLeaveEarlyTime() / TimeConst.CODE_DEFINITION_HOUR));
		vo.setTxtLeaveEarlyTimeMinute(String.valueOf(dto.getLeaveEarlyTime() % TimeConst.CODE_DEFINITION_HOUR));
		// 早退30分以上時間
		vo.setTxtLeaveEarlyThirtyMinutesOrMoreTimeHour(
				String.valueOf(getHour(dto.getLeaveEarlyThirtyMinutesOrMoreTime())));
		vo.setTxtLeaveEarlyThirtyMinutesOrMoreTimeMinute(
				String.valueOf(getMinute(dto.getLeaveEarlyThirtyMinutesOrMoreTime())));
		// 早退30分未満時間
		vo.setTxtLeaveEarlyLessThanThirtyMinutesTimeHour(
				String.valueOf(getHour(dto.getLeaveEarlyLessThanThirtyMinutesTime())));
		vo.setTxtLeaveEarlyLessThanThirtyMinutesTimeMinute(
				String.valueOf(getMinute(dto.getLeaveEarlyLessThanThirtyMinutesTime())));
		
		// 法定振替休日日数
		vo.setTxtTimesLegalHolidaySubstitute(String.valueOf(dto.getTimesLegalHolidaySubstitute()));
		// 所定振替休日日数
		vo.setTxtTimesSpecificHolidaySubstitute(String.valueOf(dto.getTimesSpecificHolidaySubstitute()));
		// 特別休暇合計日数
		vo.setTxtTotalSpecialHoliday(String.valueOf(dto.getTotalSpecialHoliday()));
		// 特別休暇時間数
		vo.setTxtSpecialHolidayHour(String.valueOf(dto.getSpecialHolidayHour()));
		// その他休暇合計日数
		vo.setTxtTotalOtherHoliday(String.valueOf(dto.getTotalOtherHoliday()));
		// その他休暇時間数
		vo.setTxtOtherHolidayHour(String.valueOf(dto.getOtherHolidayHour()));
		// 欠勤合計日数
		vo.setTxtTotalDeduction(String.valueOf(dto.getTotalAbsence()));
		// 欠勤時間数
		vo.setTxtDeductionHour(String.valueOf(dto.getAbsenceHour()));
		// 法定代休発生日数
		vo.setTxtLegalCompensationOccurred(String.valueOf(dto.getLegalCompensationOccurred()));
		// 所定代休発生日数
		vo.setTxtSpecificCompensationOccurred(String.valueOf(dto.getSpecificCompensationOccurred()));
		// 深夜代休発生日数
		vo.setTxtLateCompensationOccurred(String.valueOf(dto.getLateCompensationOccurred()));
		// 法定代休未使用日数
		vo.setTxtLegalCompensationUnused(String.valueOf(dto.getLegalCompensationUnused()));
		// 所定代休未使用日数
		vo.setTxtSpecificCompensationUnused(String.valueOf(dto.getSpecificCompensationUnused()));
		// 深夜代休未使用日数
		vo.setTxtLateCompensationUnused(String.valueOf(dto.getLateCompensationUnused()));
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setLeaveVoFields() throws MospException {
		// VO取得
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// 特別休暇情報取得(締期間の基準日取得)
		List<HolidayDtoInterface> specialList = timeReference().holiday().getHolidayList(vo.getTargetDate(),
				TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		int specialListSize = specialList.size();
		String[] aryTxtTimesSpecialLeave = new String[specialListSize];
		String[] aryTxtTimesSpecialHour = new String[specialListSize];
		String[] aryTxtTimesSpecialLeaveTitle = new String[specialListSize];
		String[] aryTxtTimesSpecialLeaveCode = new String[specialListSize];
		long[] aryTimesSpecialLeaveId = new long[specialListSize];
		int i = 0;
		for (HolidayDtoInterface dto : specialList) {
			aryTxtTimesSpecialLeaveTitle[i] = dto.getHolidayAbbr();
			aryTxtTimesSpecialLeaveCode[i] = dto.getHolidayCode();
			aryTxtTimesSpecialLeave[i] = Double.toString(0);
			aryTxtTimesSpecialHour[i] = String.valueOf(0);
			TotalLeaveDtoInterface totalLeaveDto = timeReference().totalLeave().findForKey(vo.getPersonalId(),
					vo.getTargetYear(), vo.getTargetMonth(), dto.getHolidayCode());
			if (totalLeaveDto != null) {
				aryTimesSpecialLeaveId[i] = totalLeaveDto.getTmdTotalLeaveId();
				aryTxtTimesSpecialLeave[i] = String.valueOf(totalLeaveDto.getTimes());
				aryTxtTimesSpecialHour[i] = String.valueOf(totalLeaveDto.getHours());
			}
			i++;
		}
		vo.setAryTxtTimesSpecialLeave(aryTxtTimesSpecialLeave);
		vo.setAryTxtTimesSpecialHour(aryTxtTimesSpecialHour);
		vo.setAryTxtTimesSpecialLeaveTitle(aryTxtTimesSpecialLeaveTitle);
		vo.setAryTxtTimesSpecialLeaveCode(aryTxtTimesSpecialLeaveCode);
		vo.setAryTimesSpecialLeaveId(aryTimesSpecialLeaveId);
		// その他休暇情報取得(締期間の基準日取得)
		List<HolidayDtoInterface> otherList = timeReference().holiday().getHolidayList(vo.getTargetDate(),
				TimeConst.CODE_HOLIDAYTYPE_OTHER);
		int otherListSize = otherList.size();
		String[] aryTxtTimesOtherVacation = new String[otherListSize];
		String[] aryTxtTimesOtherVacationHour = new String[otherListSize];
		String[] aryTxtTimesOtherVacationTitle = new String[otherListSize];
		String[] aryTxtTimesOtherVacationCode = new String[otherListSize];
		long[] aryTimesOtherVacationId = new long[otherListSize];
		i = 0;
		for (HolidayDtoInterface dto : otherList) {
			aryTxtTimesOtherVacationTitle[i] = dto.getHolidayAbbr();
			aryTxtTimesOtherVacationCode[i] = dto.getHolidayCode();
			aryTxtTimesOtherVacation[i] = Double.toString(0);
			aryTxtTimesOtherVacationHour[i] = String.valueOf(0);
			TotalOtherVacationDtoInterface totalOtherVacationDto = timeReference().totalOtherVacation()
				.findForKey(vo.getPersonalId(), vo.getTargetYear(), vo.getTargetMonth(), dto.getHolidayCode());
			if (totalOtherVacationDto != null) {
				aryTimesOtherVacationId[i] = totalOtherVacationDto.getTmdTotalOtherVacationId();
				aryTxtTimesOtherVacation[i] = String.valueOf(totalOtherVacationDto.getTimes());
				aryTxtTimesOtherVacationHour[i] = String.valueOf(totalOtherVacationDto.getHours());
			}
			i++;
		}
		vo.setAryTxtTimesOtherVacation(aryTxtTimesOtherVacation);
		vo.setAryTxtTimesOtherVacationHour(aryTxtTimesOtherVacationHour);
		vo.setAryTxtTimesOtherVacationTitle(aryTxtTimesOtherVacationTitle);
		vo.setAryTxtTimesOtherVacationCode(aryTxtTimesOtherVacationCode);
		vo.setAryTimesOtherVacationId(aryTimesOtherVacationId);
		// 欠勤情報取得(締期間の基準日取得)
		List<HolidayDtoInterface> deductionList = timeReference().holiday().getHolidayList(vo.getTargetDate(),
				TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
		int deductionListSize = deductionList.size();
		String[] aryTxtTimesDeduction = new String[deductionListSize];
		String[] aryTxtTimesDeductionHour = new String[deductionListSize];
		String[] aryTxtTimesDeductionTitle = new String[deductionListSize];
		String[] aryTxtTimesDeductionCode = new String[deductionListSize];
		long[] aryDeductionId = new long[deductionListSize];
		i = 0;
		for (HolidayDtoInterface dto : deductionList) {
			aryTxtTimesDeductionTitle[i] = dto.getHolidayAbbr();
			aryTxtTimesDeductionCode[i] = dto.getHolidayCode();
			aryTxtTimesDeduction[i] = Double.toString(0);
			aryTxtTimesDeductionHour[i] = String.valueOf(0);
			TotalAbsenceDtoInterface totalAbsenceDto = timeReference().totalAbsence().findForKey(vo.getPersonalId(),
					vo.getTargetYear(), vo.getTargetMonth(), dto.getHolidayCode());
			if (totalAbsenceDto != null) {
				aryDeductionId[i] = totalAbsenceDto.getTmdTotalAbsenceId();
				aryTxtTimesDeduction[i] = String.valueOf(totalAbsenceDto.getTimes());
				aryTxtTimesDeductionHour[i] = String.valueOf(totalAbsenceDto.getHours());
			}
			i++;
		}
		vo.setAryTxtDeduction(aryTxtTimesDeduction);
		vo.setAryTxtDeductionHour(aryTxtTimesDeductionHour);
		vo.setAryTxtDeductionTitle(aryTxtTimesDeductionTitle);
		vo.setAryTxtDeductionCode(aryTxtTimesDeductionCode);
		vo.setAryDeductionId(aryDeductionId);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void setCorrectionVoFields() throws MospException {
		// VO取得
		TotalTimeCardVo vo = (TotalTimeCardVo)mospParams.getVo();
		// 最新の勤怠集計修正情報を取得
		TotalTimeCorrectionDtoInterface dto = timeReference().totalTimeCorrection()
			.getLatestTotalTimeCorrectionInfo(vo.getPersonalId(), vo.getTargetYear(), vo.getTargetMonth());
		// 最新の勤怠集計修正情報を取得出来なかった場合
		if (dto == null) {
			// 処理無し
			return;
		}
		// 修正履歴を作成
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("Finality"));
		sb.append(mospParams.getName("Corrector"));
		sb.append(PfNameUtility.colon(mospParams));
		sb.append(reference().human().getHumanName(dto.getCorrectionPersonalId(), dto.getCorrectionDate()));
		sb.append(MospConst.STR_SB_SPACE);
		sb.append(TimeNamingUtility.days(mospParams));
		sb.append(PfNameUtility.colon(mospParams));
		sb.append(DateUtility.getStringDateAndDayAndTime(dto.getCorrectionDate()));
		// 修正履歴及び修正理由を設定
		vo.setLblCorrectionHistory(sb.toString());
		vo.setTxtCorrectionReason(dto.getCorrectionReason());
	}
	
	/**
	 * 時間取得。<br>
	 * @param time 対象時間
	 * @return 時間
	 */
	protected String getHour(int time) {
		return String.valueOf(TimeUtility.getHours(time));
	}
	
	/**
	 * 分取得。<br>
	 * @param time 対象時間
	 * @return 分
	 */
	protected String getMinute(int time) {
		return String.valueOf(TimeUtility.getMinutes(time));
	}
	
}
