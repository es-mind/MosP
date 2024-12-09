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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.AttendanceTotalVo;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTotalInfoBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;

/**
 * 日々勤怠の集計クラス。<br>
 */
public class AttendanceTotalInfoBean extends TimeBean implements AttendanceTotalInfoBeanInterface {
	
	/**
	 * 勤怠一覧参照クラス。<br>
	 */
	AttendanceListReferenceBeanInterface	attendanceListRefer;
	
	/**
	 * 時間表示時の区切文字。<br>
	 */
	public static final String				SEPARATOR_HOURS	= ".";
	
	/**
	 * 時間表示時の小数点以下の桁数。<br>
	 */
	public static final int					HOURS_DIGITS	= 2;
	
	
	@Override
	public void initBean() throws MospException {
		attendanceListRefer = (AttendanceListReferenceBeanInterface)createBean(
				AttendanceListReferenceBeanInterface.class);
	}
	
	/**
	 * 対象勤怠一覧集計情報を取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 対象勤怠一覧集計情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected AttendanceListDto getTotalAttendanceListDto(String personalId, Date targetDate) throws MospException {
		// 年月取得
		int year = DateUtility.getYear(targetDate);
		int month = DateUtility.getMonth(targetDate);
		// 勤怠一覧を取得
		List<AttendanceListDto> attendanceList = attendanceListRefer.getAttendanceList(personalId, year, month);
		if (attendanceList.isEmpty()) {
			return null;
		}
		// 勤怠一覧集計情報取得
		return attendanceList.get(attendanceList.size() - 1);
	}
	
	/**
	 * 初期値を設定する。<br>
	 * @param targetDate 対象日取得
	 * @return 勤怠集計値VO
	 */
	protected AttendanceTotalVo setDefaltValue(Date targetDate) {
		// VO準備
		AttendanceTotalVo vo = new AttendanceTotalVo();
		// 年月対象設定
		StringBuilder title = new StringBuilder();
		title.append(mospParams.getName("FrontWithCornerParentheses"));
		title.append(DateUtility.getStringYear(targetDate) + mospParams.getName("Year"));
		title.append(DateUtility.getStringMonth(targetDate) + mospParams.getName("Month"));
		title.append(mospParams.getName("BackWithCornerParentheses"));
		vo.setTargetDate(title.toString());
		// 行初期化
		vo.setRowItemNumber(15);
		// 項目設定
		vo.setTitleList(getTitleList());
		// 値初期化
		vo.setValueList(getInitValueList());
		return vo;
	}
	
	/**
	 * 項目設定。<br>
	 * @return 項目リスト
	 */
	protected List<String> getTitleList() {
		// 項目初期化
		List<String> titleList = new ArrayList<String>();
		titleList.add("合計時間");
		titleList.add("勤務");
		titleList.add("休憩");
		titleList.add("外出");
		titleList.add("遅早");
		titleList.add("内残");
		titleList.add("外残");
		titleList.add("休出");
		titleList.add("深夜");
		titleList.add("回数");
		titleList.add("出勤");
		titleList.add("遅刻");
		titleList.add("早退");
		titleList.add("残業");
		titleList.add("休出");
		titleList.add("休日");
		titleList.add("法定");
		titleList.add("所定");
		titleList.add("振休");
		titleList.add("休暇");
		titleList.add("有休");
		titleList.add("有時");
		titleList.add("特休");
		titleList.add("その他");
		titleList.add("代休");
		titleList.add("欠勤");
		titleList.add("代休発生");
		titleList.add("法定");
		titleList.add("所定");
		titleList.add("深夜");
		return titleList;
	}
	
	/**
	 * 値設定。<br>
	 * 
	 * @return 値リスト
	 */
	protected List<String> getInitValueList() {
		List<String> valueList = new ArrayList<String>();
		valueList.add(null);
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add(null);
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add(null);
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add(null);
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add("");
		valueList.add(null);
		valueList.add("");
		valueList.add("");
		valueList.add("");
		return valueList;
	}
	
	/**
	 * 値リストに項目値に対する値を設定する。<br>
	 * ・ 0：合計時間
	 * ・ 1：勤務
	 * ・ 2：休憩
	 * ・ 3：外出
	 * ・ 4：遅早
	 * ・ 5：内残
	 * ・ 6：外残
	 * ・ 7：休出
	 * ・ 8：深夜
	 * ・ 9：回数
	 * ・10：出勤
	 * ・11：遅刻
	 * ・12：早退
	 * ・13：残業
	 * ・14：休出
	 * ・15：休日
	 * ・16：法定
	 * ・17：所定
	 * ・18：振休
	 * ・19：休暇
	 * ・20：有休
	 * ・21：有時
	 * ・22：特休
	 * ・23：その他
	 * ・24：代休
	 * ・25：欠勤
	 * ・26：代休発生
	 * ・27：法定
	 * ・28：所定
	 * ・29：深夜
	 * @param valueList 値リスト
	 * @param dto 勤怠一覧情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setValues(List<String> valueList, AttendanceListDto dto) throws MospException {
		// 値設定
		valueList.set(0, null);
		valueList.set(1, dto.getWorkTimeTotalString());
		valueList.set(2, dto.getRestTimeTotalString());
		valueList.set(3, dto.getPrivateTimeTotalString());
		valueList.set(4, dto.getLateLeaveEarlyTimeTotalString());
		valueList.set(5, dto.getOvertimeInTotalString());
		valueList.set(6, dto.getOvertimeOutTotalString());
		valueList.set(7, dto.getHolidayWorkTimeTotalString());
		valueList.set(8, dto.getLateNightTimeTotalString());
		valueList.set(9, null);
		valueList.set(10, dto.getWorkDaysString());
		valueList.set(11, dto.getLateDaysString());
		valueList.set(12, dto.getLeaveEarlyDaysString());
		valueList.set(13, dto.getOvertimeDaysString());
		valueList.set(14, dto.getHolidayWorkDaysString());
		valueList.set(15, null);
		valueList.set(16, dto.getLegalHolidaysString());
		valueList.set(17, dto.getPrescribedHolidaysString());
		valueList.set(18, dto.getSubstituteHolidaysString());
		valueList.set(19, null);
		valueList.set(20, dto.getPaidHolidaysString());
		valueList.set(21, dto.getPaidHolidayTimeString());
		valueList.set(22, dto.getSpecialHolidaysString());
		valueList.set(23, dto.getOtherHolidaysString());
		valueList.set(24, dto.getSubHolidaysString());
		valueList.set(25, dto.getAbsenceDaysString());
		valueList.set(26, null);
		valueList.set(27, dto.getBirthLegalSubHolidayString());
		valueList.set(28, dto.getBirthPrescribedSubHolidayString());
		valueList.set(29, dto.getBirthMidnightSubHolidayString());
	}
	
	@Override
	public List<AttendanceTotalVo> setFiscalYearAttendanceTotalList(String personalId, int fiscalYear)
			throws MospException {
		// リスト準備
		List<AttendanceTotalVo> list = new ArrayList<AttendanceTotalVo>();
		// 各月リスト準備
		List<AttendanceTotalVo> monthList = new ArrayList<AttendanceTotalVo>();
		// 対象年度初日取得
		Date startDate = MonthUtility.getFiscalStartMonth(fiscalYear, mospParams);
		// 値準備
		Integer workTimeTotal = 0;
		Integer restTimeTotal = 0;
		Integer privateTimeTotal = 0;
		Integer lateLeaveEarlyTimeTotal = 0;
		Integer overtimeInTotal = 0;
		Integer overtimeOutTotal = 0;
		Integer holidayWorkTimeTotal = 0;
		Integer lateNightTimeTotal = 0;
		Integer workDays = 0;
		Integer lateDays = 0;
		Integer leaveEarlyDays = 0;
		Integer overtimeDays = 0;
		Integer holidayWorkDays = 0;
		Integer legalHolidays = 0;
		Integer prescribedHolidays = 0;
		Float substituteHolidays = 0F;
		Float paidHolidays = 0F;
		Float paidHolidayTime = 0F;
		Float specialHolidays = 0F;
		Float specialHolidayHours = 0F;
		Float otherHolidays = 0F;
		Float otherHolidayHours = 0F;
		Float subHolidays = 0F;
		Float absenceDays = 0F;
		Float absenceHours = 0F;
		Float birthLegalSubHoliday = 0F;
		Float birthPrescribedSubHoliday = 0F;
		Float birthMidNightSubHoliday = 0F;
		// 有休時間
		boolean isPaidHolidayTime = false;
		// メッセージ準備
		List<String> errorMes = new ArrayList<String>();
		// 月毎に処理
		for (int i = 0; i < 12; i++) {
			// 対象日取得
			Date targetDate = DateUtility.addMonth(startDate, i);
			// 集計情報取得
			AttendanceListDto attendanceListDto = getTotalAttendanceListDto(personalId, targetDate);
			if (mospParams.hasErrorMessage()) {
				// エラーメッセージ保管（エラーメッセージを追加すると集計情報が取得できないため）
				errorMes.addAll(mospParams.getErrorMessageList());
				// エラーメッセージ削除
				mospParams.getErrorMessageList().clear();
				continue;
			}
			// 初期VO取得
			AttendanceTotalVo vo = setDefaltValue(targetDate);
			// VO設定
			setValues(vo.getValueList(), attendanceListDto);
			// リスト追加
			monthList.add(vo);
			// 値計算
			workTimeTotal += attendanceListDto.getWorkTimeTotal();
			restTimeTotal += attendanceListDto.getRestTimeTotal();
			privateTimeTotal += attendanceListDto.getPrivateTimeTotal();
			lateLeaveEarlyTimeTotal += attendanceListDto.getLateLeaveEarlyTimeTotal();
			overtimeInTotal += attendanceListDto.getOvertimeInTotal();
			overtimeOutTotal += attendanceListDto.getOvertimeOutTotal();
			holidayWorkTimeTotal += attendanceListDto.getHolidayWorkTimeTotal();
			lateNightTimeTotal += attendanceListDto.getLateNightTimeTotal();
			workDays += attendanceListDto.getWorkDays();
			lateDays += attendanceListDto.getLateDays();
			leaveEarlyDays += attendanceListDto.getLeaveEarlyDays();
			overtimeDays += attendanceListDto.getOvertimeDays();
			holidayWorkDays += attendanceListDto.getHolidayWorkDays();
			legalHolidays += attendanceListDto.getLegalHolidays();
			prescribedHolidays += attendanceListDto.getPrescribedHolidays();
			substituteHolidays += attendanceListDto.getSubstituteHolidays();
			paidHolidays += attendanceListDto.getPaidHolidays();
			// 有休時間が存在する場合
			if (attendanceListDto.getPaidHolidayTime() != null) {
				isPaidHolidayTime = true;
			}
			paidHolidayTime += MospUtility.getFloat(attendanceListDto.getPaidHolidayTime());
			specialHolidays += attendanceListDto.getSpecialHolidays();
			specialHolidayHours += attendanceListDto.getSpecialHolidayHours();
			otherHolidays += attendanceListDto.getOtherHolidays();
			otherHolidayHours += attendanceListDto.getOtherHolidayHours();
			subHolidays += attendanceListDto.getSubHolidays();
			absenceDays += attendanceListDto.getAbsenceDays();
			absenceHours += attendanceListDto.getAbsenceHours();
			birthLegalSubHoliday += attendanceListDto.getBirthLegalSubHoliday();
			birthPrescribedSubHoliday += attendanceListDto.getBirthPrescribedSubHoliday();
			birthMidNightSubHoliday += attendanceListDto.getBirthMidnightSubHoliday();
		}
		// 値リスト作成
		List<String> valueList = new ArrayList<String>();
		valueList.add(0, null);
		valueList.add(1, getStringHours(workTimeTotal, false));
		valueList.add(2, getStringHours(restTimeTotal, false));
		valueList.add(3, getStringHours(privateTimeTotal, false));
		valueList.add(4, getStringHours(lateLeaveEarlyTimeTotal, false));
		valueList.add(5, getStringHours(overtimeInTotal, false));
		valueList.add(6, getStringHours(overtimeOutTotal, false));
		valueList.add(7, getStringHours(holidayWorkTimeTotal, false));
		valueList.add(8, getStringHours(lateNightTimeTotal, false));
		valueList.add(9, null);
		valueList.add(10, getStringTimes(workDays, false));
		valueList.add(11, getStringTimes(lateDays, false));
		valueList.add(12, getStringTimes(leaveEarlyDays, false));
		valueList.add(13, getStringTimes(overtimeDays, false));
		valueList.add(14, getStringTimes(holidayWorkDays, false));
		valueList.add(15, null);
		valueList.add(16, getStringTimes(legalHolidays, false));
		valueList.add(17, getStringTimes(prescribedHolidays, false));
		valueList.add(18, getStringTimes(substituteHolidays, false));
		valueList.add(19, null);
		valueList.add(20, getStringTimes(paidHolidays, false));
		// 設定が有効でないため、ハイフンを表示
		valueList.add(21, getStringTimes(paidHolidayTime, !isPaidHolidayTime));
		valueList.add(22, getStringTimes(specialHolidays, false) + mospParams.getName("Slash")
				+ getStringTimes(specialHolidayHours, false));
		valueList.add(23, getStringTimes(otherHolidays, false) + mospParams.getName("Slash")
				+ getStringTimes(otherHolidayHours, false));
		valueList.add(24, getStringTimes(subHolidays, false));
		valueList.add(25,
				getStringTimes(absenceDays, false) + mospParams.getName("Slash") + getStringTimes(absenceHours, false));
		valueList.add(26, null);
		valueList.add(27, getStringFloat(birthLegalSubHoliday));
		valueList.add(28, getStringFloat(birthPrescribedSubHoliday));
		valueList.add(29, getStringFloat(birthMidNightSubHoliday));
		// 集計VOに設定
		// 合計VO準備
		AttendanceTotalVo totalVo = new AttendanceTotalVo();
		// 年月対象設定
		StringBuilder title = new StringBuilder();
		title.append(mospParams.getName("FrontWithCornerParentheses"));
		title.append("年度累計");
		title.append(mospParams.getName("BackWithCornerParentheses"));
		totalVo.setTargetDate(title.toString());
		totalVo.setClassName("style=\"background-color: #ccffcc;\"");
		totalVo.setRowItemNumber(15);
		totalVo.setTitleList(getTitleList());
		totalVo.setValueList(valueList);
		// リストに追加
		list.add(totalVo);
		list.addAll(monthList);
		// エラーメッセージ追加(戻す)
		mospParams.getErrorMessageList().addAll(errorMes);
		return list;
	}
	
	/**
	 * 回数文字列を取得する(Integer→String)。<br>
	 * 回数を文字列で表す。<br>
	 * @param times 対象回数
	 * @param isHyphen (true：ハイフン、ハイフンでない)
	 * @return 回数文字列
	 */
	protected String getStringTimes(Integer times, boolean isHyphen) {
		// 回数確認
		if (times == null || isHyphen) {
			// 回数がnullならハイフン
			return getHyphenNaming();
		}
		// 回数文字列取得
		return times.toString();
	}
	
	/**
	 * 回数文字列を取得する(Float→String)。<br>
	 * 回数を文字列(小数点以下1桁)で表す。<br>
	 * @param times 対象回数
	 * @param isHyphen  (true：ハイフン、ハイフンでない)
	 * @return 回数文字列
	 */
	protected String getStringTimes(Float times, boolean isHyphen) {
		// 回数確認
		if (times == null || isHyphen) {
			// 回数がnullならハイフン
			return getHyphenNaming();
		}
		DecimalFormat df = new DecimalFormat("#.##");
		// 日付文字列取得
		return df.format(times);
	}
	
	/**
	 * 時間文字列を取得する(Integer→String)。<br>
	 * 時間を文字列(小数点以下2桁)で表す。<br>
	 * 小数点以下2桁は、分を表す。<br>
	 * @param minutes    対象時間(分)
	 * @param needHyphen ゼロ時ハイフン表示要否(true：ゼロ時ハイフン、false：ゼロ時はゼロ)
	 * @return 時間文字列(0.00)
	 */
	protected String getStringHours(Integer minutes, boolean needHyphen) {
		// 時間確認
		if (minutes == null) {
			// 時間がnullならハイフン
			return getHyphenNaming();
		}
		if (needHyphen && minutes.intValue() == 0) {
			// 時間が0ならハイフン
			return getHyphenNaming();
		}
		// 時間文字列準備
		StringBuffer sb = new StringBuffer();
		// 時間
		sb.append(minutes.intValue() / TimeConst.CODE_DEFINITION_HOUR);
		// 区切文字
		sb.append(SEPARATOR_HOURS);
		// 分
		int remainder = minutes.intValue() % TimeConst.CODE_DEFINITION_HOUR;
		// 数値フォーマットクラス準備
		NumberFormat nf = NumberFormat.getNumberInstance();
		// 丸め方法指定(切捨)
		nf.setRoundingMode(RoundingMode.DOWN);
		// 桁数指定
		nf.setMinimumIntegerDigits(HOURS_DIGITS);
		sb.append(nf.format(remainder));
		// 時間文字列取得
		return sb.toString();
	}
	
	/**
	 * Float→String変換。
	 * @param value 値
	 * @return 文字列
	 */
	protected String getStringFloat(Float value) {
		// 値がない場合
		if (value == null) {
			// ハイフン
			return getHyphenNaming();
		}
		return String.valueOf(value);
	}
	
}
