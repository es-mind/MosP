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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.TotalTimeCorrectionRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.TotalTimeCorrectionDaoInterface;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;
import jp.mosp.time.dto.settings.TotalAllowanceDtoInterface;
import jp.mosp.time.dto.settings.TotalLeaveDtoInterface;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalTimeCorrectionDto;

/**
 * 勤怠集計修正情報登録クラス。
 */
public class TotalTimeCorrectionRegistBean extends PlatformBean implements TotalTimeCorrectionRegistBeanInterface {
	
	/**
	 * 勤怠集計修正データDAOクラス。<br>
	 */
	protected TotalTimeCorrectionDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeCorrectionRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(TotalTimeCorrectionDaoInterface.class);
	}
	
	@Override
	public TotalTimeCorrectionDtoInterface getInitDto() {
		return new TmdTotalTimeCorrectionDto();
	}
	
	@Override
	public void insert(TotalTimeCorrectionDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdTotalTimeCorrectionId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(TotalTimeCorrectionDtoInterface dto) {
	}
	
	@Override
	public void insertTotalTime(TotalTimeCorrectionDtoInterface dto, TotalTimeDataDtoInterface oldDto,
			TotalTimeDataDtoInterface newDto) throws MospException {
		// 変更部分の検索
		List<TotalTimeCorrectionDtoInterface> list = setChangePointTotalTimeDate(oldDto, newDto);
		for (TotalTimeCorrectionDtoInterface correctionDto : list) {
			// 勤怠情報設定
			dto.setCorrectionType(correctionDto.getCorrectionType());
			dto.setCorrectionBefore(correctionDto.getCorrectionBefore());
			dto.setCorrectionAfter(correctionDto.getCorrectionAfter());
			// 登録
			insert(dto);
		}
	}
	
	@Override
	public void insertAllowance(TotalTimeCorrectionDtoInterface dto, TotalAllowanceDtoInterface oldDto,
			TotalAllowanceDtoInterface newDto) throws MospException {
		// 変更部分の検索
		TotalTimeCorrectionDtoInterface correctionDto = setChangePointTotalAllowance(dto, oldDto, newDto);
		// 登録
		insert(correctionDto);
	}
	
	@Override
	public void insertAbsence(TotalTimeCorrectionDtoInterface dto, TotalAbsenceDtoInterface oldDto,
			TotalAbsenceDtoInterface newDto, boolean isDay, boolean isHour) throws MospException {
		// 休暇コードを取得
		String code = newDto.getAbsenceCode();
		// 変更部分の検索
		if (isDay) {
			// 修正箇所区分を準備
			String type = TimeConst.CODE_TOTALTIME_ITEM_NAME_ABSENCE;
			// 勤怠集計修正情報に休暇修正情報(日数)を設定
			setHolidayCorrection(dto, type, oldDto.getTimes(), newDto.getTimes(), code);
			// 登録
			insert(dto);
		}
		if (isHour) {
			// 修正箇所区分を準備
			String type = TimeConst.CODE_TOTALTIME_ITEM_NAME_ABSENCEHOUR;
			// 勤怠集計修正情報に休暇修正情報(時間)を設定
			setHolidayCorrection(dto, type, oldDto.getHours(), newDto.getHours(), code);
			// 登録
			insert(dto);
		}
	}
	
	@Override
	public void insertLeave(TotalTimeCorrectionDtoInterface dto, TotalLeaveDtoInterface oldDto,
			TotalLeaveDtoInterface newDto, boolean isDay, boolean isHour) throws MospException {
		// 休暇コードを取得
		String code = newDto.getHolidayCode();
		// 変更部分の検索
		if (isDay) {
			// 修正箇所区分を準備
			String type = TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALLEAVE;
			// 勤怠集計修正情報に休暇修正情報(日数)を設定
			setHolidayCorrection(dto, type, oldDto.getTimes(), newDto.getTimes(), code);
			// 登録
			insert(dto);
		}
		if (isHour) {
			// 修正箇所区分を準備
			String type = TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALLEAVEHOUR;
			// 勤怠集計修正情報に休暇修正情報(時間)を設定
			setHolidayCorrection(dto, type, oldDto.getHours(), newDto.getHours(), code);
			// 登録
			insert(dto);
		}
	}
	
	@Override
	public void insertOtherVacation(TotalTimeCorrectionDtoInterface dto, TotalOtherVacationDtoInterface oldDto,
			TotalOtherVacationDtoInterface newDto, boolean isDay, boolean isHour) throws MospException {
		// 休暇コードを取得
		String code = newDto.getHolidayCode();
		if (isDay) {
			// 修正箇所区分を準備
			String type = TimeConst.CODE_TOTALTIME_ITEM_NAME_OTHERVACATION;
			// 勤怠集計修正情報に休暇修正情報(日数)を設定
			setHolidayCorrection(dto, type, oldDto.getTimes(), newDto.getTimes(), code);
			// 登録
			insert(dto);
		}
		if (isHour) {
			// 修正箇所区分を準備
			String type = TimeConst.CODE_TOTALTIME_ITEM_NAME_OTHERVACATIONHOUR;
			// 勤怠集計修正情報に休暇修正情報(時間)を設定
			setHolidayCorrection(dto, type, oldDto.getHours(), newDto.getHours(), code);
			// 登録
			insert(dto);
		}
	}
	
	@Override
	public void delete(List<String> personalIdList, int calculationYear, int calculationMonth) throws MospException {
		for (String personalId : personalIdList) {
			List<TotalTimeCorrectionDtoInterface> list = dao.findForHistory(personalId, calculationYear,
					calculationMonth);
			for (TotalTimeCorrectionDtoInterface dto : list) {
				// DTO妥当性確認
				validate(dto);
				if (mospParams.hasErrorMessage()) {
					return;
				}
				// 確認
				checkDelete(dto);
				if (mospParams.hasErrorMessage()) {
					return;
				}
				// 論理削除
				logicalDelete(dao, dto.getTmdTotalTimeCorrectionId());
			}
		}
	}
	
	/**
	 * 勤怠集計修正の妥当性を確認する。
	 * @param oldDto 旧勤怠集計管理DTO
	 * @param newDto 新勤怠集計管理DTO
	 * @return 勤怠集計修正データDTO
	 */
	protected List<TotalTimeCorrectionDtoInterface> setChangePointTotalTimeDate(TotalTimeDataDtoInterface oldDto,
			TotalTimeDataDtoInterface newDto) {
		List<TotalTimeCorrectionDtoInterface> list = new ArrayList<TotalTimeCorrectionDtoInterface>();
		// 勤務時間
		if (oldDto.getWorkTime() != newDto.getWorkTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKTIME, oldDto.getWorkTime(),
					newDto.getWorkTime()));
		}
		// 所定勤務時間
		if (oldDto.getSpecificWorkTime() != newDto.getSpecificWorkTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICWORKTIME,
					oldDto.getSpecificWorkTime(), newDto.getSpecificWorkTime()));
		}
		// 無給時短時間
		if (oldDto.getShortUnpaid() != newDto.getShortUnpaid()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_SHORT_UNPAID, oldDto.getShortUnpaid(),
					newDto.getShortUnpaid()));
		}
		// 出勤日数
		if (oldDto.getTimesWorkDate() != newDto.getTimesWorkDate()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESWORKDATE,
					oldDto.getTimesWorkDate(), newDto.getTimesWorkDate()));
		}
		// 出勤回数
		if (oldDto.getTimesWork() != newDto.getTimesWork()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESWORK, oldDto.getTimesWork(),
					newDto.getTimesWork()));
		}
		// 法定休日出勤日数
		if (oldDto.getLegalWorkOnHoliday() != newDto.getLegalWorkOnHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEGALWORKONHOLIDAY,
					oldDto.getLegalWorkOnHoliday(), newDto.getLegalWorkOnHoliday()));
		}
		// 所定休日出勤日数
		if (oldDto.getSpecificWorkOnHoliday() != newDto.getSpecificWorkOnHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICWORKONHOLIDAY,
					oldDto.getSpecificWorkOnHoliday(), newDto.getSpecificWorkOnHoliday()));
		}
		// 直行回数
		if (oldDto.getDirectStart() != newDto.getDirectStart()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_DIRECTSTART, oldDto.getDirectStart(),
					newDto.getDirectStart()));
		}
		// 直帰回数
		if (oldDto.getDirectEnd() != newDto.getDirectEnd()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_DIRECTEND, oldDto.getDirectEnd(),
					newDto.getDirectEnd()));
		}
		// 休憩時間
		if (oldDto.getRestTime() != newDto.getRestTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTTIME, oldDto.getRestTime(),
					newDto.getRestTime()));
		}
		// 深夜休憩時間
		if (oldDto.getRestLateNight() != newDto.getRestLateNight()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTLATENIGHT,
					oldDto.getRestLateNight(), newDto.getRestLateNight()));
		}
		// 所定休出休憩時間
		if (oldDto.getRestWorkOnSpecificHoliday() != newDto.getRestWorkOnSpecificHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTWORKONSPECIFICHOLIDAY,
					oldDto.getRestWorkOnSpecificHoliday(), newDto.getRestWorkOnSpecificHoliday()));
		}
		// 法定休出休憩時間
		if (oldDto.getRestWorkOnHoliday() != newDto.getRestWorkOnHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTWORKONHOLIDAY,
					oldDto.getRestWorkOnHoliday(), newDto.getRestWorkOnHoliday()));
		}
		// 公用外出時間
		if (oldDto.getPublicTime() != newDto.getPublicTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_PUBLICTIME, oldDto.getPublicTime(),
					newDto.getPublicTime()));
		}
		// 私用外出時間
		if (oldDto.getPrivateTime() != newDto.getPrivateTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_PRIVATETIME, oldDto.getPrivateTime(),
					newDto.getPrivateTime()));
		}
		// 残業回数
		if (oldDto.getTimesOvertime() != newDto.getTimesOvertime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESOVERTIME,
					oldDto.getTimesOvertime(), newDto.getTimesOvertime()));
		}
		// 残業時間
		if (oldDto.getOvertime() != newDto.getOvertime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME, oldDto.getOvertime(),
					newDto.getOvertime()));
		}
		// 法定内残業時間
		if (oldDto.getOvertimeIn() != newDto.getOvertimeIn()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME_IN, oldDto.getOvertimeIn(),
					newDto.getOvertimeIn()));
		}
		// 法定外残業時間
		if (oldDto.getOvertimeOut() != newDto.getOvertimeOut()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME_OUT, oldDto.getOvertimeOut(),
					newDto.getOvertimeOut()));
		}
		// 深夜時間
		if (oldDto.getLateNight() != newDto.getLateNight()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATENIGHT, oldDto.getLateNight(),
					newDto.getLateNight()));
		}
		// 所定休出時間
		if (oldDto.getWorkOnSpecificHoliday() != newDto.getWorkOnSpecificHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKONSPECIFICHOLIDAY,
					oldDto.getWorkOnSpecificHoliday(), newDto.getWorkOnSpecificHoliday()));
		}
		// 法定休出時間
		if (oldDto.getWorkOnHoliday() != newDto.getWorkOnHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKONHOLIDAY,
					oldDto.getWorkOnHoliday(), newDto.getWorkOnHoliday()));
		}
		// 減額対象時間
		if (oldDto.getDecreaseTime() != newDto.getDecreaseTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_DECREASETIME,
					oldDto.getDecreaseTime(), newDto.getDecreaseTime()));
		}
		// 45時間超残業時間
		if (oldDto.getFortyFiveHourOvertime() != newDto.getFortyFiveHourOvertime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_FORTYFIVEHOUROVERTIME,
					oldDto.getFortyFiveHourOvertime(), newDto.getFortyFiveHourOvertime()));
		}
		// 合計遅刻日数
		if (oldDto.getLateDays() != newDto.getLateDays()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATEDAYS, oldDto.getLateDays(),
					newDto.getLateDays()));
		}
		// 遅刻30分以上日数
		if (oldDto.getLateThirtyMinutesOrMore() != newDto.getLateThirtyMinutesOrMore()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_THIRTY_MINUTES_OR_MORE,
					oldDto.getLateThirtyMinutesOrMore(), newDto.getLateThirtyMinutesOrMore()));
		}
		// 遅刻30分未満日数
		if (oldDto.getLateLessThanThirtyMinutes() != newDto.getLateLessThanThirtyMinutes()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_LESS_THAN_THIRTY_MINUTES,
					oldDto.getLateLessThanThirtyMinutes(), newDto.getLateLessThanThirtyMinutes()));
		}
		// 合計遅刻時間
		if (oldDto.getLateTime() != newDto.getLateTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATETIME, oldDto.getLateTime(),
					newDto.getLateTime()));
		}
		// 遅刻30分以上時間
		if (oldDto.getLateThirtyMinutesOrMoreTime() != newDto.getLateThirtyMinutesOrMoreTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_THIRTY_MINUTES_OR_MORE_TIME,
					oldDto.getLateThirtyMinutesOrMoreTime(), newDto.getLateThirtyMinutesOrMoreTime()));
		}
		// 遅刻30分未満時間
		if (oldDto.getLateLessThanThirtyMinutesTime() != newDto.getLateLessThanThirtyMinutesTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_LESS_THAN_THIRTY_MINUTES_TIME,
					oldDto.getLateLessThanThirtyMinutesTime(), newDto.getLateLessThanThirtyMinutesTime()));
		}
		// 合計遅刻回数
		if (oldDto.getTimesLate() != newDto.getTimesLate()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLATE, oldDto.getTimesLate(),
					newDto.getTimesLate()));
		}
		// 合計早退日数
		if (oldDto.getLeaveEarlyDays() != newDto.getLeaveEarlyDays()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVEEARLYDAYS,
					oldDto.getLeaveEarlyDays(), newDto.getLeaveEarlyDays()));
		}
		// 早退30分以上日数
		if (oldDto.getLeaveEarlyThirtyMinutesOrMore() != newDto.getLeaveEarlyThirtyMinutesOrMore()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE,
					oldDto.getLeaveEarlyThirtyMinutesOrMore(), newDto.getLeaveEarlyThirtyMinutesOrMore()));
		}
		// 早退30分未満日数
		if (oldDto.getLeaveEarlyLessThanThirtyMinutes() != newDto.getLeaveEarlyLessThanThirtyMinutes()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES,
					oldDto.getLeaveEarlyLessThanThirtyMinutes(), newDto.getLeaveEarlyLessThanThirtyMinutes()));
		}
		// 合計早退時間
		if (oldDto.getLeaveEarlyTime() != newDto.getLeaveEarlyTime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVEEARLYTIME,
					oldDto.getLeaveEarlyTime(), newDto.getLeaveEarlyTime()));
		}
		// 早退30分以上時間
		if (oldDto.getLeaveEarlyThirtyMinutesOrMoreTime() != newDto.getLeaveEarlyThirtyMinutesOrMoreTime()) {
			list.add(getTotalTimeCorrectionDto(
					TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME,
					oldDto.getLeaveEarlyThirtyMinutesOrMoreTime(), newDto.getLeaveEarlyThirtyMinutesOrMoreTime()));
		}
		// 早退30分未満時間
		if (oldDto.getLeaveEarlyLessThanThirtyMinutesTime() != newDto.getLeaveEarlyLessThanThirtyMinutesTime()) {
			list.add(getTotalTimeCorrectionDto(
					TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME,
					oldDto.getLeaveEarlyLessThanThirtyMinutesTime(), newDto.getLeaveEarlyLessThanThirtyMinutesTime()));
		}
		// 合計早退回数
		if (oldDto.getTimesLeaveEarly() != newDto.getTimesLeaveEarly()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEAVEEARLY,
					oldDto.getTimesLeaveEarly(), newDto.getTimesLeaveEarly()));
		}
		// 休日日数
		if (oldDto.getTimesHoliday() != newDto.getTimesHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESHOLIDAY,
					oldDto.getTimesHoliday(), newDto.getTimesHoliday()));
		}
		// 法定休日日数
		if (oldDto.getTimesLegalHoliday() != newDto.getTimesLegalHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALHOLIDAY,
					oldDto.getTimesLegalHoliday(), newDto.getTimesLegalHoliday()));
		}
		// 所定休日日数
		if (oldDto.getTimesSpecificHoliday() != newDto.getTimesSpecificHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICHOLIDAY,
					oldDto.getTimesSpecificHoliday(), newDto.getTimesSpecificHoliday()));
		}
		// 有給休暇日数
		if (oldDto.getTimesPaidHoliday() != newDto.getTimesPaidHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESPAIDHOLIDAY,
					oldDto.getTimesPaidHoliday(), newDto.getTimesPaidHoliday()));
		}
		// 有給休暇時間
		if (oldDto.getPaidHolidayHour() != newDto.getPaidHolidayHour()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_PAIDHOLIDAYHOUR,
					oldDto.getPaidHolidayHour(), newDto.getPaidHolidayHour()));
		}
		// ストック休暇日数
		if (oldDto.getTimesStockHoliday() != newDto.getTimesStockHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSTOCKHOLIDAY,
					oldDto.getTimesStockHoliday(), newDto.getTimesStockHoliday()));
		}
		// 代休日数
		if (oldDto.getTimesCompensation() != newDto.getTimesCompensation()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESCOMPENSATION,
					oldDto.getTimesCompensation(), newDto.getTimesCompensation()));
		}
		// 法定代休日数
		if (oldDto.getTimesLegalCompensation() != newDto.getTimesLegalCompensation()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALCOMPENSATION,
					oldDto.getTimesLegalCompensation(), newDto.getTimesLegalCompensation()));
		}
		// 所定代休日数
		if (oldDto.getTimesSpecificCompensation() != newDto.getTimesSpecificCompensation()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICCOMPENSATION,
					oldDto.getTimesSpecificCompensation(), newDto.getTimesSpecificCompensation()));
		}
		// 深夜代休日数
		if (oldDto.getTimesLateCompensation() != newDto.getTimesLateCompensation()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLATECOMPENSATION,
					oldDto.getTimesLateCompensation(), newDto.getTimesLateCompensation()));
		}
		// 振替休日日数
		if (oldDto.getTimesHolidaySubstitute() != newDto.getTimesHolidaySubstitute()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESHOLIDAYSUBSTITUTE,
					oldDto.getTimesHolidaySubstitute(), newDto.getTimesHolidaySubstitute()));
		}
		// 法定振替休日日数
		if (oldDto.getTimesLegalHolidaySubstitute() != newDto.getTimesLegalHolidaySubstitute()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALHOLIDAYSUBSTITUTE,
					oldDto.getTimesLegalHolidaySubstitute(), newDto.getTimesLegalHolidaySubstitute()));
		}
		// 所定振替休日日数
		if (oldDto.getTimesSpecificHolidaySubstitute() != newDto.getTimesSpecificHolidaySubstitute()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICHOLIDAYSUBSTITUTE,
					oldDto.getTimesSpecificHolidaySubstitute(), newDto.getTimesSpecificHolidaySubstitute()));
		}
		// 特別休暇合計日数
		if (oldDto.getTotalSpecialHoliday() != newDto.getTotalSpecialHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALSPECIALHOLIDAY,
					oldDto.getTotalSpecialHoliday(), newDto.getTotalSpecialHoliday()));
		}
		// その他休暇合計日数
		if (oldDto.getTotalOtherHoliday() != newDto.getTotalOtherHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALOTHERHOLIDAY,
					oldDto.getTotalOtherHoliday(), newDto.getTotalOtherHoliday()));
		}
		// 欠勤合計日数
		if (oldDto.getTotalAbsence() != newDto.getTotalAbsence()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALABSENCE,
					oldDto.getTotalAbsence(), newDto.getTotalAbsence()));
		}
		// 手当合計
		if (oldDto.getTotalAllowance() != newDto.getTotalAllowance()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALALLOWANCE,
					oldDto.getTotalAllowance(), newDto.getTotalAllowance()));
		}
		// 60時間超残業時間
		if (oldDto.getSixtyHourOvertime() != newDto.getSixtyHourOvertime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_SIXTYHOUROVERTIME,
					oldDto.getSixtyHourOvertime(), newDto.getSixtyHourOvertime()));
		}
		// 平日時間外時間
		if (oldDto.getWeekDayOvertime() != newDto.getWeekDayOvertime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_WEEKDAYOVERTIME,
					oldDto.getWeekDayOvertime(), newDto.getWeekDayOvertime()));
		}
		// 所定休日時間外時間
		if (oldDto.getSpecificOvertime() != newDto.getSpecificOvertime()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICOVERTIME,
					oldDto.getSpecificOvertime(), newDto.getSpecificOvertime()));
		}
		// 代替休暇日数
		if (oldDto.getTimesAlternative() != newDto.getTimesAlternative()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESALTERNATIVE,
					oldDto.getTimesAlternative(), newDto.getTimesAlternative()));
		}
		// 法定代休未使用日数
		if (oldDto.getLegalCompensationUnused() != newDto.getLegalCompensationUnused()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LEGALCOMPENSATIONUNUSED,
					oldDto.getLegalCompensationUnused(), newDto.getLegalCompensationUnused()));
		}
		// 所定代休未使用日数
		if (oldDto.getSpecificCompensationUnused() != newDto.getSpecificCompensationUnused()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICCOMPENSATIONUNUSED,
					oldDto.getSpecificCompensationUnused(), newDto.getSpecificCompensationUnused()));
		}
		// 深夜代休未使用日数
		if (oldDto.getLateCompensationUnused() != newDto.getLateCompensationUnused()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_LATECOMPENSATIONUNUSED,
					oldDto.getLateCompensationUnused(), newDto.getLateCompensationUnused()));
		}
		// 出勤実績日数
		if (oldDto.getTimesAchievement() != newDto.getTimesAchievement()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_ATTENDANCE_ACHIEVEMENT,
					oldDto.getTimesAchievement(), newDto.getTimesAchievement()));
		}
		// 出勤対象日数
		if (oldDto.getTimesTotalWorkDate() != newDto.getTimesTotalWorkDate()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_WORK_DATE,
					oldDto.getTimesTotalWorkDate(), newDto.getTimesTotalWorkDate()));
		}
		// 休出回数
		if (oldDto.getTimesWorkingHoliday() != newDto.getTimesWorkingHoliday()) {
			list.add(getTotalTimeCorrectionDto(TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMES_WORKING_HOLIDAY,
					oldDto.getTimesWorkingHoliday(), newDto.getTimesWorkingHoliday()));
		}
		return list;
	}
	
	/**
	 * 手当集計データ変更点を確認。
	 * @param dto 対象DTO
	 * @param oldDto 旧手当集計データDTO
	 * @param newDto 新手当集計データDTO
	 * @return 変更箇所情報
	 */
	protected TotalTimeCorrectionDtoInterface setChangePointTotalAllowance(TotalTimeCorrectionDtoInterface dto,
			TotalAllowanceDtoInterface oldDto, TotalAllowanceDtoInterface newDto) {
		if (newDto.getTimes() != oldDto.getTimes()) {
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER1)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE1);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER2)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE2);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER3)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE3);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER4)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE4);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER5)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE5);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER6)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE6);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER7)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE7);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER8)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE8);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER9)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE9);
			}
			if (newDto.getAllowanceCode().equals(TimeConst.CODE_COMMON_ITEMS_NUMBER10)) {
				dto.setCorrectionType(TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE10);
			}
			dto.setCorrectionBefore(String.valueOf(oldDto.getTimes()));
			dto.setCorrectionAfter(String.valueOf(newDto.getTimes()));
		}
		return dto;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(TotalTimeCorrectionDtoInterface dto) {
		// TODO 妥当性確認
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(TotalTimeCorrectionDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdTotalTimeCorrectionId());
	}
	
	private TotalTimeCorrectionDtoInterface getTotalTimeCorrectionDto(String type, Object before, Object after) {
		TotalTimeCorrectionDtoInterface dto = getInitDto();
		dto.setCorrectionType(type);
		dto.setCorrectionBefore(String.valueOf(before));
		dto.setCorrectionAfter(String.valueOf(after));
		return dto;
	}
	
	/**
	 * 勤怠集計修正情報に休暇修正情報(日数)を設定する。<br>
	 * @param dto        勤怠集計修正情報
	 * @param type       修正箇所区分
	 * @param beforeTime 修正前日数
	 * @param afterTime  修正後日数
	 * @param code       休暇コード
	 */
	protected void setHolidayCorrection(TotalTimeCorrectionDtoInterface dto, String type, double beforeTime,
			double afterTime, String code) {
		// 文字配列を準備
		String[] strs = { type, code };
		// 修正箇所を設定
		dto.setCorrectionType(MospUtility.concat(MospConst.APP_PROPERTY_SEPARATOR, strs));
		// 修正前日数を設定
		dto.setCorrectionBefore(String.valueOf(beforeTime));
		// 修正後日数を設定
		dto.setCorrectionAfter(String.valueOf(afterTime));
	}
	
	/**
	 * 勤怠集計修正情報に休暇修正情報(時間)を設定する。<br>
	 * @param dto        勤怠集計修正情報
	 * @param type       修正箇所区分
	 * @param beforeTime 修正前時間
	 * @param afterTime  修正後時間
	 * @param code       休暇コード
	 */
	protected void setHolidayCorrection(TotalTimeCorrectionDtoInterface dto, String type, int beforeTime, int afterTime,
			String code) {
		// 文字配列を準備
		String[] strs = { type, code };
		// 修正箇所を設定
		dto.setCorrectionType(MospUtility.concat(MospConst.APP_PROPERTY_SEPARATOR, strs));
		// 修正前時間を設定
		dto.setCorrectionBefore(getStringHolidayHours(beforeTime));
		// 修正後時間を設定
		dto.setCorrectionAfter(getStringHolidayHours(afterTime));
	}
	
	/**
	 * 時間文字列(hours時間)を取得する。<br>
	 * @param hours 時間
	 * @return 時間文字列(hours時間)
	 */
	protected String getStringHolidayHours(int hours) {
		// 文字列を準備
		StringBuilder sb = new StringBuilder();
		sb.append(hours);
		sb.append(PfNameUtility.time(mospParams));
		// 時間文字列(hours時間)を取得
		return sb.toString();
	}
	
}
