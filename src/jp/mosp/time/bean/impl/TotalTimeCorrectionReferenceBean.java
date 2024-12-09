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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalTimeCorrectionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.TotalTimeCorrectionDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;

/**
 * 勤怠集計修正情報参照クラス。
 */
public class TotalTimeCorrectionReferenceBean extends PlatformBean
		implements TotalTimeCorrectionReferenceBeanInterface {
	
	/**
	 * 勤怠データ修正情報DAOクラス。<br>
	 */
	protected TotalTimeCorrectionDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalTimeCorrectionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(TotalTimeCorrectionDaoInterface.class);
	}
	
	@Override
	public TotalTimeCorrectionDtoInterface getLatestTotalTimeCorrectionInfo(String personalId, int calculationYear,
			int calculationMonth) throws MospException {
		return dao.findForLatestInfo(personalId, calculationYear, calculationMonth);
	}
	
	@Override
	public List<TotalTimeCorrectionDtoInterface> getTotalTimeCorrectionHistory(String personalId, int calculationYear,
			int calculationMonth) throws MospException {
		return dao.findForHistory(personalId, calculationYear, calculationMonth);
	}
	
	@Override
	public String getCorrectionValue(String type, String value) {
		// 項目が数値型か時間型か確認
		boolean timeType = isTimeType(type);
		boolean numType = isNumType(type);
		// 
		final String dummyValue = "00";
		final String time = mospParams.getName("Time");
		final String minutes = mospParams.getName("Minutes");
		StringBuffer sb = new StringBuffer();
		if (timeType) {
			int work = Integer.parseInt(value);
			if (work <= 0) {
				sb.append(dummyValue);
				sb.append(time);
				sb.append(dummyValue);
				sb.append(minutes);
				return sb.toString();
			}
			sb.append(work / 60);
			sb.append(time);
			int minute = work % 60;
			if (minute < 10) {
				sb.append(0);
			}
			sb.append(minute);
			sb.append(minutes);
			return sb.toString();
		} else if (numType) {
			return value;
		} else if (TimeConst.CODE_TOTALTIME_ITEM_NAME_PAIDHOLIDAYHOUR.equals(type)) {
			// 有給休暇時間
			sb.append(value);
			sb.append(time);
			return sb.toString();
		}
		return value;
	}
	
	/**
	 * 項目が時間型かの確認をする。
	 * @param type 項目
	 * @return 確認結果(true：時間型、false：時間型ではない)
	 * 
	 */
	protected boolean isTimeType(String type) {
		return
		// 勤務時間
		TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKTIME.equals(type)
				// 所定勤務時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICWORKTIME.equals(type)
				//休憩時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTTIME.equals(type)
				// 深夜休憩時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTLATENIGHT.equals(type)
				// 所定休日出勤休憩時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTWORKONSPECIFICHOLIDAY.equals(type)
				// 法定休日出勤労働時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_RESTWORKONHOLIDAY.equals(type)
				// 公用外出時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_PUBLICTIME.equals(type)
				// 私用外出時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_PRIVATETIME.equals(type)
				// 残業時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME.equals(type)
				// 法定内残業時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME_IN.equals(type)
				// 法定外残業時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_OVERTIME_OUT.equals(type)
				// 深夜時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LATENIGHT.equals(type)
				// 所定休日出勤時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKONSPECIFICHOLIDAY.equals(type)
				// 法定休日出勤時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_WORKONHOLIDAY.equals(type)
				// 減額対象時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_DECREASETIME.equals(type)
				// 45時間超残業時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_FORTYFIVEHOUROVERTIME.equals(type)
				// 遅刻時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LATETIME.equals(type)
				// 早退時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVEEARLYTIME.equals(type)
				// 60時間超残業時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_SIXTYHOUROVERTIME.equals(type)
				// 平日時間外時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_WEEKDAYOVERTIME.equals(type)
				// 所定休日時間外時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICOVERTIME.equals(type)
				// 遅刻30分以上時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_THIRTY_MINUTES_OR_MORE_TIME.equals(type)
				// 遅刻30分未満時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_LESS_THAN_THIRTY_MINUTES_TIME.equals(type)
				// 早退30分以上時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME.equals(type)
				// 早退30分未満時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME.equals(type)
				// 無給時短時間
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_SHORT_UNPAID.equals(type);
	}
	
	/**
	 * 項目が数値型かの確認をする。
	 * @param type 項目
	 * @return 確認結果(true：数値型、false：数値型ではない)
	 * 
	 */
	protected boolean isNumType(String type) {
		return
		// 出勤日数
		TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESWORKDATE.equals(type)
				// 出勤回数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESWORK.equals(type)
				// 法定休日出勤日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LEGALWORKONHOLIDAY.equals(type)
				// 所定休日出勤日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_SPECIFICWORKONHOLIDAY.equals(type)
				// 直行回数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_DIRECTSTART.equals(type)
				// 直帰回数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_DIRECTEND.equals(type)
				// 残業回数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESOVERTIME.equals(type)
				// 休出回数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMES_WORKING_HOLIDAY.equals(type)
				// 遅刻回数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLATE.equals(type)
				// 早退回数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEAVEEARLY.equals(type)
				// 休日日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESHOLIDAY.equals(type)
				// 法定休日日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALHOLIDAY.equals(type)
				// 所定休日日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICHOLIDAY.equals(type)
				// 有給休暇日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESPAIDHOLIDAY.equals(type)
				// ストック休暇日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSTOCKHOLIDAY.equals(type)
				// 代休日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESCOMPENSATION.equals(type)
				// 法定代休日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALCOMPENSATION.equals(type)
				// 所定代休日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICCOMPENSATION.equals(type)
				// 深夜代休日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLATECOMPENSATION.equals(type)
				// 振替休日日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESHOLIDAYSUBSTITUTE.equals(type)
				// 法定振替休日日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESLEGALHOLIDAYSUBSTITUTE.equals(type)
				// 定振替休日日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESSPECIFICHOLIDAYSUBSTITUTE.equals(type)
				// 特別休暇合計日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALSPECIALHOLIDAY.equals(type)
				// その他休暇合計日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALOTHERHOLIDAY.equals(type)
				// 欠勤合計日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALABSENCE.equals(type)
				// 手当合計
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TOTALALLOWANCE.equals(type)
				// 代替休暇日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_TIMESALTERNATIVE.equals(type)
				// 手当1
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE1.equals(type)
				// 手当2
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE2.equals(type)
				// 手当3
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE3.equals(type)
				// 手当4
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE4.equals(type)
				// 手当5
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE5.equals(type)
				// 手当6
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE6.equals(type)
				// 手当7
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE7.equals(type)
				// 手当8
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE8.equals(type)
				// 手当9
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE9.equals(type)
				// 手当10
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_ALLOWANCE10.equals(type)
				// 遅刻30分以上日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_THIRTY_MINUTES_OR_MORE.equals(type)
				// 遅刻30分未満日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LATE_LESS_THAN_THIRTY_MINUTES.equals(type)
				// 早退30分以上日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE.equals(type)
				// 早退30分未満日数
				|| TimeConst.CODE_TOTALTIME_ITEM_NAME_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES.equals(type);
	}
}
