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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendCalcExecuteBeanInterface;
import jp.mosp.time.bean.AttendCalcExecuteExtraBeanInterface;
import jp.mosp.time.bean.AttendCalcReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.entity.AttendCalcEntity;
import jp.mosp.time.entity.AttendCalcEntityInterface;
import jp.mosp.time.entity.AttendanceEntityInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.utils.AttendanceUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠計算(日々)実行処理。<br>
 */
public class AttendCalcExecuteBean extends TimeBean implements AttendCalcExecuteBeanInterface {
	
	/**
	 * コードキー(勤怠計算(日々)実行時事前処理設定)。<br>
	 */
	protected static final String				CODE_KEY_BEFORE	= "AttendCalcExecuteBefore";
	
	/**
	 * コードキー(勤怠計算(日々)実行時事後処理設定)。<br>
	 */
	protected static final String				CODE_KEY_AFTER	= "AttendCalcExecuteAfter";
	
	/**
	 * 勤怠計算(日々)関連情報取得処理。<br>
	 */
	protected AttendCalcReferenceBeanInterface	refer;
	
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
	}
	
	@Override
	public void setAttendCalcRefer(AttendCalcReferenceBeanInterface refer) {
		// 勤怠計算(日々)関連情報取得処理を設定
		this.refer = refer;
	}
	
	@Override
	public AttendCalcEntityInterface getCalcEntity(AttendanceEntityInterface attendance) throws MospException {
		// 勤怠計算(日々)エンティティを取得
		return createCalcEntity(attendance);
	}
	
	@Override
	public void calc(AttendanceEntityInterface attendance) throws MospException {
		// 勤怠(日々)情報を複製(勤怠計算(日々)エンティティでは勤怠(日々)情報に値を設定しない)
		AttendanceDtoInterface dto = AttendanceUtility.getAttendanceDtoClone(null, attendance.getAttendanceDto());
		// 勤怠計算(日々)エンティティを取得
		AttendCalcEntityInterface calc = createCalcEntity(attendance);
		// 勤怠計算(日々)実行時事前処理設定に基づき追加処理を実施
		for (AttendCalcExecuteExtraBeanInterface extra : getExtraBeans(CODE_KEY_BEFORE)) {
			// 勤怠計算(日々)実行事前処理設定を実施
			extra.execute(calc, dto);
		}
		// 入力始業時刻と入力終業時刻を元に始終業時刻を計算し勤怠(日々)情報に設定
		setStartEndTimes(dto, calc);
		// 始終業時刻と休憩情報群と外出情報群を元に計算項目を計算し勤怠(日々)情報に設定
		setCalcItems(dto, calc);
		// 遅刻時間と実遅刻時間を元に遅刻関連情報を勤怠(日々)情報に設定
		setLateItems(dto);
		// 早退時間と実早退時間を元に早退関連情報を勤怠(日々)情報に設定
		setLeaveEarlyItems(dto);
		// 前残業時間と後残業時間を元に残業関連情報を勤怠(日々)情報に設定
		setOvertimeItems(dto);
		// 勤務時間関連情報を勤怠(日々)情報に設定
		setWorkTimes(dto, attendance);
		// 日数及び回数項目を勤怠(日々)情報に設定
		setDaysAndTimes(dto);
		// 勤務時間を元に代休発生日数を勤怠(日々)情報に設定
		setCompensationDays(dto);
		// 特別な項目を勤怠(日々)情報に設定
		setExtraItems(dto, calc);
		// 通常では使われない項目を勤怠(日々)情報に設定
		setUnusedItems(dto);
		// 勤怠計算(日々)実行時事後処理設定に基づき追加処理を実施
		for (AttendCalcExecuteExtraBeanInterface extra : getExtraBeans(CODE_KEY_AFTER)) {
			// 勤怠計算(日々)実行事後処理設定を実施
			extra.execute(calc, dto);
		}
		// 計算結果を設定した勤怠(日々)情報を勤怠(日々)情報エンティティに設定
		attendance.setAttendanceDto(dto);
	}
	
	/**
	 * 入力始業時刻と入力終業時刻を元に始終業時刻を計算し勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 勤怠(日々)情報に入力始業時刻と入力終業時刻が設定されている必要がある。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・始業時刻<br>
	 * ・実始業時刻<br>
	 * ・終業時刻<br>
	 * ・実終業時刻<br>
	 * <br>
	 * @param dto  勤怠(日々)情報
	 * @param calc 勤怠計算(日々)エンティティ
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void setStartEndTimes(AttendanceDtoInterface dto, AttendCalcEntityInterface calc) throws MospException {
		// 勤怠(日々)情報に設定されている入力始業時刻及び入力終業時刻(0:00からの分)を取得
		int inputStartTime = getMinutes(dto.getActualStartTime(), dto.getWorkDate());
		int inputEndTime = getMinutes(dto.getActualEndTime(), dto.getWorkDate());
		// 始業時刻を計算し設定
		dto.setStartTime(toDate(dto, calc.calcStartTime(inputStartTime)));
		// 実始業時刻を計算し設定
		dto.setActualStartTime(toDate(dto, calc.calcActualStartTime(inputStartTime)));
		// 終業時刻を計算し設定
		dto.setEndTime(toDate(dto, calc.calcEndTime(inputEndTime)));
		// 実終業時刻を計算し設定
		dto.setActualEndTime(toDate(dto, calc.calcActualEndTime(inputEndTime)));
	}
	
	/**
	 * 始業時刻と終業時刻を元に計算項目を計算し勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 勤怠(日々)情報に始業時刻と終業時刻が設定されている必要がある。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・遅刻時間<br>
	 * ・実遅刻時間<br>
	 * ・早退時間<br>
	 * ・実早退時間<br>
	 * ・勤務時間<br>
	 * ・所定労働時間<br>
	 * ・所定労働時間内労働時間<br>
	 * ・無給時短時間<br>
	 * ・休憩時間<br>
	 * ・深夜休憩時間<br>
	 * ・法定休出休憩時間<br>
	 * ・所定休出休憩時間<br>
	 * ・公用外出時間<br>
	 * ・私用外出時間<br>
	 * ・前残業時間<br>
	 * ・後残業時間<br>
	 * ・法定内残業時間<br>
	 * ・法定外残業時間<br>
	 * ・平日法定時間内残業時間<br>
	 * ・平日法定時間外残業時間<br>
	 * ・所定休日法定時間内残業時間<br>
	 * ・所定休日法定時間外残業時間<br>
	 * ・深夜勤務時間<br>
	 * ・深夜所定労働時間内時間<br>
	 * ・深夜時間外時間<br>
	 * ・深夜休日労働時間<br>
	 * ・所定休日勤務時間<br>
	 * ・法定休日勤務時間<br>
	 * ・減額対象時間<br>
	 * <br>
	 * @param dto  勤怠(日々)情報
	 * @param calc 勤怠計算(日々)エンティティ
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void setCalcItems(AttendanceDtoInterface dto, AttendCalcEntityInterface calc) throws MospException {
		// 始業及び終業時刻(勤怠計算上)(0:00からの分)を計算
		int startTime = getMinutes(dto.getStartTime(), dto.getWorkDate());
		int endTime = getMinutes(dto.getEndTime(), dto.getWorkDate());
		// 遅刻時間を計算し設定
		dto.setLateTime(calc.calcLateTime(startTime, endTime));
		// 実遅刻時間を計算し設定
		dto.setActualLateTime(calc.calcActualLateTime(startTime, endTime));
		// 早退時間を計算し設定
		dto.setLeaveEarlyTime(calc.calcLeaveEarlyTime(startTime, endTime));
		// 実早退時間を計算し設定
		dto.setActualLeaveEarlyTime(calc.calcActualLeaveEarlyTime(startTime, endTime));
		// 勤務時間を計算し設定
		dto.setWorkTime(calc.calcWorkTime(startTime, endTime));
		// 所定労働時間を計算し設定
		dto.setGeneralWorkTime(calc.calcPrescribedWorkTime());
		// 所定労働時間内労働時間を設定
		dto.setWorkTimeWithinPrescribedWorkTime(calc.calcWorkTimeWithinPrescribed(startTime, endTime));
		// 無給時短時間を計算し設定
		dto.setShortUnpaid(calc.calcShortUnpaid(startTime, endTime));
		// 休憩時間を計算し設定
		dto.setRestTime(calc.calcRestTime(startTime, endTime));
		// 深夜休憩時間を計算し設定
		dto.setNightRestTime(calc.calcNightRestTime(startTime, endTime));
		// 法定休出休憩時間を計算し設定
		dto.setLegalHolidayRestTime(calc.calcLegalHolidayRestTime(startTime, endTime));
		// 所定休出休憩時間を計算し設定
		dto.setPrescribedHolidayRestTime(calc.calcPrescribedHolidayRestTime(startTime, endTime));
		// 公用外出時間を計算し設定
		dto.setPublicTime(calc.calcPublicTime());
		// 私用外出時間を計算し設定
		dto.setPrivateTime(calc.calcPrivateTime());
		// 前残業時間を計算し設定
		dto.setOvertimeBefore(calc.calcOvertimeBefore(startTime, endTime));
		// 後残業時間を計算し設定
		dto.setOvertimeAfter(calc.calcOvertimeAfter(startTime, endTime));
		// 法定内残業時間を計算し設定
		dto.setOvertimeIn(calc.calcOvertimeIn(startTime, endTime));
		// 法定外残業時間を計算し設定
		dto.setOvertimeOut(calc.calcOvertimeOut(startTime, endTime));
		// 平日法定時間内残業時間を計算し設定
		dto.setWorkdayOvertimeIn(calc.calcWorkdayOvertimeIn(startTime, endTime));
		// 平日法定時間外残業時間を計算し設定
		dto.setWorkdayOvertimeOut(calc.calcWorkdayOvertimeOut(startTime, endTime));
		// 所定休日法定時間内残業時間を計算し設定
		dto.setPrescribedHolidayOvertimeIn(calc.calcPrescribedHolidayOvertimeIn(startTime, endTime));
		// 所定休日法定時間外残業時間を計算し設定
		dto.setPrescribedHolidayOvertimeOut(calc.calcPrescribedHolidayOvertimeOut(startTime, endTime));
		// 深夜勤務時間を計算し設定
		dto.setLateNightTime(calc.calcNightWorkTime(startTime, endTime));
		// 深夜所定労働時間内時間を計算し設定
		dto.setNightWorkWithinPrescribedWork(calc.calcNightWorkWithinPrescribed(startTime, endTime));
		// 深夜時間外時間を計算し設定
		dto.setNightOvertimeWork(calc.calcNightWorkOvertime(startTime, endTime));
		// 深夜休日労働時間を計算し設定
		dto.setNightWorkOnHoliday(calc.calcNightWorkOnLegalHoliday(startTime, endTime));
		// 所定休日勤務時間を計算し設定
		dto.setSpecificWorkTime(calc.calcPrescribedHolidayWorkTime(startTime, endTime));
		// 法定休日勤務時間を計算し設定
		dto.setLegalWorkTime(calc.calcLegalHolidayWorkTime(startTime, endTime));
		// 減額対象時間を計算し設定
		dto.setDecreaseTime(calc.calcDecreaseTime(startTime, endTime));
		// 所定労働時間内法定休日労働時間
		dto.setStatutoryHolidayWorkTimeIn(calc.calcLegalHolidayWorkTimeIn(startTime, endTime));
		// 所定労働時間外法定休日労働時間
		dto.setStatutoryHolidayWorkTimeOut(calc.calcLegalHolidayWorkTimeOut(startTime, endTime));
		// 所定労働時間内所定休日労働時間
		dto.setPrescribedHolidayWorkTimeIn(calc.calcPrescribedHolidayWorkTimeIn(startTime, endTime));
		// 所定労働時間外所定休日労働時間
		dto.setPrescribedHolidayWorkTimeOut(calc.calcPrescribedHolidayWorkTimeOut(startTime, endTime));
	}
	
	/**
	 * 遅刻時間と実遅刻時間を元に遅刻関連情報を勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 勤怠(日々)情報に遅刻時間と実遅刻時間が設定されている必要がある。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・遅刻日数<br>
	 * ・遅刻30分以上日数<br>
	 * ・遅刻30分未満日数<br>
	 * ・遅刻30分以上時間<br>
	 * ・遅刻30分未満時間<br>
	 * ・遅刻理由(必要に応じて調整)<br>
	 * <br>
	 * @param dto 勤怠(日々)情報
	 */
	protected void setLateItems(AttendanceDtoInterface dto) {
		// 遅刻時間と実遅刻時間を勤怠(日々)情報から取得
		int lateTime = dto.getLateTime();
		int actualLateTime = dto.getActualLateTime();
		// 遅刻関連情報を初期化
		dto.setLateDays(0);
		dto.setLateThirtyMinutesOrMore(0);
		dto.setLateLessThanThirtyMinutes(0);
		dto.setLateThirtyMinutesOrMoreTime(0);
		dto.setLateLessThanThirtyMinutesTime(0);
		// 遅刻時間が0より大きい場合
		if (lateTime > 0) {
			// 遅刻日数を設定
			dto.setLateDays(1);
			// 遅刻時間を確認
			if (lateTime >= 30) {
				// 遅刻時間が30分以上の場合
				dto.setLateThirtyMinutesOrMore(1);
				dto.setLateThirtyMinutesOrMoreTime(actualLateTime);
			} else {
				// 遅刻時間が30分より短い場合
				dto.setLateLessThanThirtyMinutes(1);
				dto.setLateLessThanThirtyMinutesTime(actualLateTime);
			}
			// 遅刻理由がない場合
			if (MospUtility.isEmpty(dto.getLateReason())) {
				// 個人都合を設定
				dto.setLateReason(TimeConst.CODE_TARDINESS_WHY_INDIVIDU);
			}
		}
		// 遅刻時間が0且つ遅刻理由が個人都合の場合
		if (lateTime == 0 && MospUtility.isEqual(TimeConst.CODE_TARDINESS_WHY_INDIVIDU, dto.getLateReason())) {
			// 遅刻理由に空白を設定
			dto.setLateReason(MospConst.STR_EMPTY);
		}
	}
	
	/**
	 * 早退時間と実早退時間を元に早退関連情報を勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 勤怠(日々)情報に早退時間と実早退時間が設定されている必要がある。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・早退日数<br>
	 * ・早退30分以上日数<br>
	 * ・早退30分未満日数<br>
	 * ・早退30分以上時間<br>
	 * ・早退30分未満時間<br>
	 * ・早退理由(必要に応じて調整)<br>
	 * <br>
	 * @param dto 勤怠(日々)情報
	 */
	protected void setLeaveEarlyItems(AttendanceDtoInterface dto) {
		// 早退時間と実早退時間を取得
		int leaveEarlyTime = dto.getLeaveEarlyTime();
		int actualLeaveEarlyTime = dto.getActualLeaveEarlyTime();
		// 早退関連情報を初期化
		dto.setLeaveEarlyDays(0);
		dto.setLeaveEarlyThirtyMinutesOrMore(0);
		dto.setLeaveEarlyLessThanThirtyMinutes(0);
		dto.setLeaveEarlyThirtyMinutesOrMoreTime(0);
		dto.setLeaveEarlyLessThanThirtyMinutesTime(0);
		// 早退時間が0より大きい場合
		if (leaveEarlyTime > 0) {
			// 早退日数を設定
			dto.setLeaveEarlyDays(1);
			// 早退時間を確認
			if (leaveEarlyTime >= 30) {
				// 早退時間が30分以上の場合
				dto.setLeaveEarlyThirtyMinutesOrMore(1);
				dto.setLeaveEarlyThirtyMinutesOrMoreTime(actualLeaveEarlyTime);
			} else {
				// 早退時間が30分より短い場合
				dto.setLeaveEarlyLessThanThirtyMinutes(1);
				dto.setLeaveEarlyLessThanThirtyMinutesTime(actualLeaveEarlyTime);
			}
			// 早退理由がない場合
			if (MospUtility.isEmpty(dto.getLeaveEarlyReason())) {
				// 個人都合を設定
				dto.setLeaveEarlyReason(TimeConst.CODE_TARDINESS_WHY_INDIVIDU);
			}
		}
		// 早退時間が0且つ早退理由が個人都合の場合
		if (leaveEarlyTime == 0
				&& MospUtility.isEqual(TimeConst.CODE_LEAVEEARLY_WHY_INDIVIDU, dto.getLeaveEarlyReason())) {
			// 早退理由に空白を設定
			dto.setLeaveEarlyReason(MospConst.STR_EMPTY);
		}
	}
	
	/**
	 * 前残業時間と後残業時間を元に残業関連情報を勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 勤怠(日々)情報に前残業時間と後残業時間が設定されている必要がある。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・残業時間<br>
	 * ・残業回数<br>
	 * <br>
	 * @param dto 勤怠(日々)情報
	 */
	protected void setOvertimeItems(AttendanceDtoInterface dto) {
		// 前残業時間及び後残業時間を取得
		int overtimeBefore = dto.getOvertimeBefore();
		int overtimeAfter = dto.getOvertimeAfter();
		// 残業時間を計算(前残業時間 + 後残業時間)
		int overtime = overtimeBefore + overtimeAfter;
		// 残業時間を設定
		dto.setOvertime(overtime);
		// 残業回数を初期化
		dto.setTimesOvertime(0);
		// 残業時間が0より大きい場合
		if (overtime > 0) {
			// 残業回数を設定
			dto.setTimesOvertime(1);
		}
	}
	
	/**
	 * 勤務時間関連情報を勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 勤怠(日々)情報に設定されている次の項目を用いる。<br>
	 * ・所定労働時間内労働時間<br>
	 * ・実遅刻時間<br>
	 * ・実早退時間<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・契約勤務時間<br>
	 * <br>
	 * @param dto        勤怠(日々)情報
	 * @param attendance 勤怠(日々)情報エンティティ
	 */
	protected void setWorkTimes(AttendanceDtoInterface dto, AttendanceEntityInterface attendance) {
		// 所定労働時間内労働時間(勤務時間 - 残業時間 - 法定休日勤務時間)を取得
		int workTimeWithinPrescribed = dto.getWorkTimeWithinPrescribedWorkTime();
		// 契約勤務時間を準備
		int contractWorkTime = workTimeWithinPrescribed;
		// 遅刻理由が電車遅延か会社指示である場合
		if (attendance.isLateReasonTrain() || attendance.isLateReasonCompany()) {
			// 契約勤務時間に実遅刻時間を加算
			contractWorkTime += dto.getActualLateTime();
		}
		// 早退理由が会社指示である場合
		if (attendance.isLeaveEarlyReasonCompany()) {
			// 契約勤務時間に実早退時間を加算
			contractWorkTime += dto.getActualLeaveEarlyTime();
		}
		// 契約勤務時間を設定
		dto.setContractWorkTime(contractWorkTime);
	}
	
	/**
	 * 日数及び回数項目を勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・出勤日数<br>
	 * ・有給休暇用出勤日数<br>
	 * ・有給休暇用全労働日<br>
	 * ・休日出勤回数<br>
	 * ・法定休日出勤回数<br>
	 * ・所定休日出勤回数<br>
	 * ・有給休暇日数<br>
	 * ・有給休暇時間数<br>
	 * ・ストック休暇日数<br>
	 * ・代休日数<br>
	 * ・法定代休日数<br>
	 * ・所定代休日数<br>
	 * ・深夜代休日数<br>
	 * ・特別休暇日数<br>
	 * ・特別休暇時間数<br>
	 * ・その他休暇日数<br>
	 * ・その他休暇時間数<br>
	 * ・欠勤日数<br>
	 * ・欠勤時間数<br>
	 * <br>
	 * @param dto 勤怠(日々)情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setDaysAndTimes(AttendanceDtoInterface dto) throws MospException {
		// 承認済フラグ(true：承認済申請のみ取得)を準備
		boolean isCompleted = true;
		// 個人IDと勤務日と勤務形態コードを取得
		String personalId = dto.getPersonalId();
		Date workDate = dto.getWorkDate();
		// 申請エンティティを取得
		RequestEntityInterface request = refer.getRequest(personalId, workDate);
		// 出勤日数を計算し設定
		dto.setWorkDays(request.calcWorkDays(isCompleted));
		// 有給休暇用出勤日数を計算し設定
		dto.setWorkDaysForPaidLeave(request.calcWorkDaysForPaidHoliday(isCompleted));
		// 有給休暇用全労働日を計算し設定(有給休暇用出勤日数と同じ)
		dto.setTotalWorkDaysForPaidLeave(request.calcWorkDaysForPaidHoliday(isCompleted));
		// 休日出勤回数を計算し設定
		dto.setTimesHolidayWork(request.calcWorkOnHolidayTimes(isCompleted));
		// 法定休日出勤回数を計算し設定
		dto.setTimesLegalHolidayWork(request.calcWorkOnLegalHolidayTimes(isCompleted));
		// 所定休日出勤回数を計算し設定
		dto.setTimesPrescribedHolidayWork(request.calcWorkOnPrescribedHolidayTimes(isCompleted));
		// 有給休暇日数を計算し設定
		dto.setPaidLeaveDays(request.calcPaidHolidayDays(isCompleted));
		// 有給休暇時間数を計算し設定
		dto.setPaidLeaveHours(request.calcPaidHolidayHours(isCompleted));
		// ストック休暇日数を計算し設定
		dto.setStockLeaveDays(request.calcStockHolidayDays(isCompleted));
		// 代休日数を計算し設定
		dto.setCompensationDays(request.calcSubHolidayDays(isCompleted));
		// 法定代休日数を計算し設定
		dto.setLegalCompensationDays(request.calcLegalSubHolidayDays(isCompleted));
		// 所定代休日数を計算し設定
		dto.setPrescribedCompensationDays(request.calcPrescribedSubHolidayDays(isCompleted));
		// 深夜代休日数を計算し設定
		dto.setNightCompensationDays(request.calcNightSubHolidayDays(isCompleted));
		// 特別休暇日数を計算し設定
		dto.setSpecialLeaveDays(request.calcSpecialHolidayDays(isCompleted));
		// 特別休暇時間数を計算し設定
		dto.setSpecialLeaveHours(request.calcSpecialHolidayHours(isCompleted));
		// その他休暇日数を計算し設定
		dto.setOtherLeaveDays(request.calcOtherHolidayDays(isCompleted));
		// その他休暇時間数を計算し設定
		dto.setOtherLeaveHours(request.calcOtherHolidayHours(isCompleted));
		// 欠勤日数を計算し設定
		dto.setAbsenceDays(request.calcAbsenceDays(isCompleted));
		// 欠勤時間数を計算し設定
		dto.setAbsenceHours(request.calcAbsenceHours(isCompleted));
	}
	
	/**
	 * 勤務時間を元に代休発生日数を勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 勤怠(日々)情報に勤務時間が設定されている必要がある。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・法定代休発生日数<br>
	 * ・所定代休発生日数<br>
	 * <br>
	 * @param dto 勤怠(日々)情報
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void setCompensationDays(AttendanceDtoInterface dto) throws MospException {
		// 承認済フラグ(true：承認済申請のみ取得)を準備
		boolean isCompleted = true;
		// 個人IDと勤務日と勤務形態コードを取得
		String personalId = dto.getPersonalId();
		Date workDate = dto.getWorkDate();
		// 申請エンティティと勤怠設定エンティティを取得
		RequestEntityInterface request = refer.getRequest(personalId, workDate);
		TimeSettingEntityInterface timeSetting = refer.getTimeSetting(personalId, workDate);
		// 勤務時間を取得
		int workTime = dto.getWorkTime();
		// 代休取得基準時間(全休及び半休)(分)
		int subHolidayAllNorm = timeSetting.getSubHolidayAllNorm();
		int subHolidayHalfNorm = timeSetting.getSubHolidayHalfNorm();
		// 代休発生日数を初期化
		dto.setGrantedLegalCompensationDays(0);
		dto.setGrantedPrescribedCompensationDays(0);
		// 代休発生日数を準備
		float compensation = 0F;
		// 勤務時間を確認
		if (subHolidayAllNorm <= workTime) {
			// 勤務時間が代休取得基準時間(全休)以上である場合(代休発生日数：1)
			compensation = 1F;
		} else if (subHolidayHalfNorm <= workTime) {
			// 勤務時間が代休取得基準時間(半休)以上である場合(代休発生日数：0.5)
			compensation = TimeConst.HOLIDAY_TIMES_HALF;
		}
		// 法定休日出勤である場合
		if (request.isWorkOnLegal(isCompleted)) {
			// 法定代休発生日数に設定
			dto.setGrantedLegalCompensationDays(compensation);
		}
		// 所定休日出勤である場合
		if (request.isWorkOnPrescribed(isCompleted)) {
			// 所定代休発生日数に設定
			dto.setGrantedPrescribedCompensationDays(compensation);
		}
	}
	
	/**
	 * 特別な項目を勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・分単位休暇A時間<br>
	 * <br>
	 * @param dto  勤怠(日々)情報
	 * @param calc 勤怠計算(日々)エンティティ
	 */
	protected void setExtraItems(AttendanceDtoInterface dto, AttendCalcEntityInterface calc) {
		// 分単位休暇A時間を設定
		dto.setMinutelyHolidayATime(getMinutelyHolidayATime(calc));
	}
	
	/**
	 * 通常では使われない項目を勤怠(日々)情報に設定する。<br>
	 * <br>
	 * 次の項目を設定する。<br>
	 * ・法定外休憩時間<br>
	 * ・分単位休暇B時間<br>
	 * ・分単位休暇A全休<br>
	 * ・分単位休暇B全休<br>
	 * ・深夜代休発生日数<br>
	 * ・法定休出時間(代休あり)<br>
	 * ・法定休出時間(代休なし)<br>
	 * ・所定休出時間(代休あり)<br>
	 * ・所定休出時間(代休なし)<br>
	 * ・法定労働時間内残業時間(代休あり)<br>
	 * ・法定労働時間内残業時間(代休なし)<br>
	 * ・法定労働時間外残業時間(代休あり)<br>
	 * ・法定労働時間外残業時間(代休なし)<br>
	 * <br>
	 * @param dto 勤怠(日々)情報
	 */
	protected void setUnusedItems(AttendanceDtoInterface dto) {
		// 法定外休憩時間を設定
		dto.setOverRestTime(0);
		// 分単位休暇B時間を設定
		dto.setMinutelyHolidayBTime(0);
		// 分単位休暇A全休を設定
		dto.setMinutelyHolidayA(0);
		// 分単位休暇B全休を設定
		dto.setMinutelyHolidayB(0);
		// 深夜代休発生日数を設定
		dto.setGrantedNightCompensationDays(0);
		// 法定休出時間(代休あり)を設定
		dto.setLegalHolidayWorkTimeWithCompensationDay(0);
		// 法定休出時間(代休なし)を設定
		dto.setLegalHolidayWorkTimeWithoutCompensationDay(0);
		// 所定休出時間(代休あり)を設定
		dto.setPrescribedHolidayWorkTimeWithCompensationDay(0);
		// 所定休出時間(代休なし)を設定
		dto.setPrescribedHolidayWorkTimeWithoutCompensationDay(0);
		// 法定労働時間内残業時間(代休あり)を設定
		dto.setOvertimeInWithCompensationDay(0);
		// 法定労働時間内残業時間(代休なし)を設定
		dto.setOvertimeInWithoutCompensationDay(0);
		// 法定労働時間外残業時間(代休あり)を設定
		dto.setOvertimeOutWithCompensationDay(0);
		// 法定労働時間外残業時間(代休なし)を設定
		dto.setOvertimeOutWithoutCompensationDay(0);
	}
	
	/**
	 * 分単位休暇A時間を取得する。<br>
	 * @param calc 勤怠計算(日々)エンティティ
	 * @return 分単位休暇A時間
	 */
	protected int getMinutelyHolidayATime(AttendCalcEntityInterface calc) {
		// 追加分単位休暇A時間群の総時間(分)を取得
		return TimeUtility.getMinutes(calc.getDurationsParam(AttendCalcEntity.KEY_ADD_MINUTELY_HOLIDAY_A_TIMES));
	}
	
	/**
	 * 勤怠計算(日々)エンティティを取得する。<br>
	 * <br>
	 * @param attendance 勤怠(日々)情報エンティティ
	 * @return 勤怠計算(日々)エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected AttendCalcEntityInterface createCalcEntity(AttendanceEntityInterface attendance) throws MospException {
		// 個人IDと勤務日と勤務形態コードを取得
		String personalId = attendance.getPersonalId();
		Date workDate = attendance.getWorkDate();
		String workTypeCode = attendance.getWorkTypeCode();
		// 勤怠計算(日々)エンティティを準備
		AttendCalcEntityInterface calc = createCalcEntity(personalId, workDate);
		// 勤怠計算(日々)エンティティに必要な情報を設定
		calc.setAttendance(attendance);
		calc.setTimeSetting(refer.getTimeSetting(personalId, workDate));
		calc.setWorkType(refer.getWorkType(personalId, workDate, workTypeCode));
		calc.setRequest(refer.getRequest(personalId, workDate));
		calc.setNextWorkType(refer.getNextWorkType(personalId, workDate));
		calc.setMospParams(mospParams);
		// 勤怠計算(日々)エンティティを取得
		return calc;
	}
	
	/**
	 * 勤怠計算(日々)追加処理設定に基づき勤怠計算(日々)追加処理リストを取得する。<br>
	 * <br>
	 * @param key コードキー(勤怠計算(日々)追加処理設定)
	 * @return 勤怠計算(日々)作成時追加処理リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendCalcExecuteExtraBeanInterface> getExtraBeans(String key) throws MospException {
		// 勤怠計算(日々)追加処理リストを準備
		List<AttendCalcExecuteExtraBeanInterface> extras = new ArrayList<AttendCalcExecuteExtraBeanInterface>();
		// 勤怠計算(日々)追加処理設定毎に処理
		for (String[] array : MospUtility.getCodeArray(mospParams, key, false)) {
			// 勤怠計算(日々)作成時追加処理クラス名を取得
			String className = array[0];
			// 勤怠計算(日々)追加処理を取得
			AttendCalcExecuteExtraBeanInterface extra = createBean(AttendCalcExecuteExtraBeanInterface.class,
					className);
			// 勤怠計算(日々)関連情報取得処理を設定
			extra.setAttendCalcRefer(refer);
			// 勤怠計算(日々)追加処理を追加
			extras.add(extra);
		}
		// 勤怠計算(日々)追加処理リストを取得
		return extras;
	}
	
	/**
	 * 勤怠計算(日々)エンティティ(空)を取得する。<br>
	 * アドオン等で用いるために引数を準備している。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @return 勤怠計算(日々)エンティティ
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected AttendCalcEntityInterface createCalcEntity(String personalId, Date workDate) throws MospException {
		// 勤怠計算(日々)エンティティを取得
		return createObject(AttendCalcEntityInterface.class);
	}
	
	/**
	 * 時刻を日付オブジェクトに変換して取得する。<br>
	 * 時刻は分単位とする。<br>
	 * @param dto     勤怠(日々)情報
	 * @param minutes 時刻(0:00からの分)
	 * @return 日付オブジェクト
	 */
	protected Date toDate(AttendanceDtoInterface dto, int minutes) {
		// 時刻(0:00からの分)が負の値(最小値)である場合
		if (minutes == Integer.MIN_VALUE) {
			// nullを取得
			return null;
		}
		// 時刻を日付オブジェクトに変換して取得
		return DateUtility.addMinute(dto.getWorkDate(), minutes);
	}
	
	/**
	 * 時刻(0:00からの分)を取得する。<br>
	 * @param time     時刻(日時)
	 * @param workDate 勤務日
	 * @return 時刻(0:00からの分)
	 */
	protected int getMinutes(Date time, Date workDate) {
		// 時刻(0:00からの分)を取得
		return TimeUtility.getAttendMinutes(time, workDate);
	}
	
}
