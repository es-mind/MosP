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
package jp.mosp.time.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠設定エンティティ。<br>
 */
public class TimeSettingEntity implements TimeSettingEntityInterface {
	
	/**
	 * 勤怠設定情報。<br>
	 */
	protected TimeSettingDtoInterface					dto;
	
	/**
	 * 限度基準情報群(キー：期間)。<br>
	 */
	protected Map<String, LimitStandardDtoInterface>	limits;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public TimeSettingEntity() {
		// 限度基準情報群(キー：期間)を初期化
		limits = new HashMap<String, LimitStandardDtoInterface>();
	}
	
	@Override
	public TimeSettingDtoInterface getTimeSettingDto() {
		return dto;
	}
	
	@Override
	public LimitStandardDtoInterface getLimitStandardDto(String term) {
		return limits.get(term);
	}
	
	@Override
	public void setTimeSettingDto(TimeSettingDtoInterface dto) {
		this.dto = dto;
		
	}
	
	@Override
	public void setLimitStandardDtos(Map<String, LimitStandardDtoInterface> limits) {
		this.limits = limits;
	}
	
	@Override
	public String getWorkSettingCode() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 勤怠設定コードを取得
		return dto.getWorkSettingCode();
	}
	
	@Override
	public Date getActivateDate() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// nullを取得
			return null;
		}
		// 有効日を取得
		return dto.getActivateDate();
	}
	
	@Override
	public boolean isExist() {
		return dto != null;
	}
	
	@Override
	public boolean isLimitStandardExist(String term) {
		return getLimitStandardDto(term) != null;
	}
	
	@Override
	public int getPrescribedWorkTime() throws MospException {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 0を取得
			return 0;
		}
		// 所定労働時間を分で取得
		return TimeUtility.getMinutes(dto.getGeneralWorkTime());
	}
	
	@Override
	public int getStartWeek() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 0を取得
			return 0;
		}
		// 週の起算曜日を取得
		return dto.getStartWeek();
	}
	
	@Override
	public int getStartMonthOfYear() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 1を取得
			return 1;
		}
		// 年の起算月を取得
		int startMonth = dto.getStartYear();
		// 年の起算月が0である場合
		if (startMonth == 0) {
			// 1を取得
			return 1;
		}
		// 年の起算月を取得
		return startMonth;
	}
	
	@Override
	public Date getStartDayTime() throws MospException {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// デフォルト日時を取得
			return DateUtility.getDefaultTime();
		}
		// 一日の起算時刻を取得
		return dto.getStartDayTime();
	}
	
	@Override
	public int getSubHolidayLimitMonth() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 0を取得
			return 0;
		}
		// 代休取得期限(月)を取得
		return dto.getSubHolidayLimitMonth();
	}
	
	@Override
	public int getSubHolidayLimitDay() {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// 0を取得
			return 0;
		}
		// 代休取得期限(日)を取得
		return dto.getSubHolidayLimitDate();
	}
	
	@Override
	public Date getSubHolidayLimitDate(SubHolidayDtoInterface subHolidayDto) throws MospException {
		// 代休情報が存在しない場合
		if (MospUtility.isEmpty(subHolidayDto)) {
			// nullを取得
			return null;
		}
		// 代休取得期限(日付)を取得
		return DateUtility.addMonthAndDay(subHolidayDto.getWorkDate(), getSubHolidayLimitMonth(),
				getSubHolidayLimitDay());
	}
	
	@Override
	public int roundDailyStart(int start) {
		// 日出勤丸め
		return getRoundMinute(start, dto.getRoundDailyStart(), dto.getRoundDailyStartUnit());
	}
	
	@Override
	public int roundDailyEnd(int end) {
		// 日退勤丸め
		return getRoundMinute(end, dto.getRoundDailyEnd(), dto.getRoundDailyEndUnit());
	}
	
	@Override
	public int roundDailyWork(int work) {
		// 日勤務時間丸め
		return getRoundMinute(work, dto.getRoundDailyWork(), dto.getRoundDailyTimeWork());
	}
	
	@Override
	public int roundDailyRestStart(int restStart) {
		// 日休憩入り丸め
		return getRoundMinute(restStart, dto.getRoundDailyRestStart(), dto.getRoundDailyRestStartUnit());
	}
	
	@Override
	public int roundDailyRestEnd(int restEnd) {
		// 日休憩戻り丸め
		return getRoundMinute(restEnd, dto.getRoundDailyRestEnd(), dto.getRoundDailyRestEndUnit());
	}
	
	@Override
	public int roundDailyRest(int rest) {
		// 日休憩時間丸め
		return getRoundMinute(rest, dto.getRoundDailyRestTime(), dto.getRoundDailyRestTimeUnit());
	}
	
	@Override
	public int roundDailyGoOutStart(int goOutStart, int goOutType) {
		// 公用外出である場合
		if (MospUtility.isEqual(goOutType, TimeConst.CODE_GO_OUT_PUBLIC)) {
			// 日公用外出入り丸め
			return getRoundMinute(goOutStart, dto.getRoundDailyPublicStart(), dto.getRoundDailyPublicStartUnit());
		}
		// 私用外出である場合
		if (MospUtility.isEqual(goOutType, TimeConst.CODE_GO_OUT_PRIVATE)) {
			// 日私用外出入り丸め
			return getRoundMinute(goOutStart, dto.getRoundDailyPrivateStart(), dto.getRoundDailyPrivateStartUnit());
		}
		// 丸めずに取得(それ以外の場合)
		return goOutStart;
	}
	
	@Override
	public int roundDailyGoOutEnd(int goOutEnd, int goOutType) {
		// 公用外出である場合
		if (MospUtility.isEqual(goOutType, TimeConst.CODE_GO_OUT_PUBLIC)) {
			// 日公用外出戻り丸め
			return getRoundMinute(goOutEnd, dto.getRoundDailyPublicEnd(), dto.getRoundDailyPublicEndUnit());
		}
		// 私用外出である場合
		if (MospUtility.isEqual(goOutType, TimeConst.CODE_GO_OUT_PRIVATE)) {
			// 日私用外出戻り丸め
			return getRoundMinute(goOutEnd, dto.getRoundDailyPrivateEnd(), dto.getRoundDailyPrivateEndUnit());
		}
		// 丸めずに取得(それ以外の場合)
		return goOutEnd;
	}
	
	@Override
	public int roundDailyLate(int late) {
		// 日遅刻丸め
		return getRoundMinute(late, dto.getRoundDailyLate(), dto.getRoundDailyLateUnit());
	}
	
	@Override
	public int roundDailyLeaveEarly(int leaveEarly) {
		// 日早退丸め
		return getRoundMinute(leaveEarly, dto.getRoundDailyLeaveEarly(), dto.getRoundDailyLeaveEarlyUnit());
	}
	
	@Override
	public int roundDailyDecrease(int decrease) {
		// 日減額対象時間丸め
		return getRoundMinute(decrease, dto.getRoundDailyDecreaseTime(), dto.getRoundDailyDecreaseTimeUnit());
	}
	
	@Override
	public int roundDailyShortUnpaid(int decrease) {
		// 日無給時短時間丸
		return getRoundMinute(decrease, dto.getRoundDailyShortUnpaid(), dto.getRoundDailyShortUnpaidUnit());
	}
	
	@Override
	public boolean isOneWeekExist() {
		// 1週間(week1)限度基準情報が存在するかを確認
		return isLimitStandardExist(TERM_ONE_WEEK);
	}
	
	@Override
	public boolean isOneMonthExist() {
		// 1ヶ月(month1)限度基準情報が存在するかを確認
		return isLimitStandardExist(TERM_ONE_MONTH);
	}
	
	@Override
	public int getOneWeekLimit() {
		// 1週間(week1)時間外限度時間(分)を取得
		return getLimit(TERM_ONE_WEEK);
	}
	
	@Override
	public int getOneMonthLimit() {
		// 1ヶ月(month1)時間外限度時間(分)を取得
		return getLimit(TERM_ONE_MONTH);
	}
	
	@Override
	public int getOneMonthAttention() {
		// 1ヶ月(month1)時間外注意時間(分)を取得
		return getAttention(TERM_ONE_MONTH);
	}
	
	@Override
	public int getOneMonthWarning() {
		// 1ヶ月(month1)時間外警告時間(分)を取得
		return getWarning(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneWeekLimitHours() {
		// 1週間(week1)時間外限度時間(時間文字列)を取得
		return getLimitHours(TERM_ONE_WEEK);
	}
	
	@Override
	public String getOneWeekLimitMinutes() {
		// 1週間(week1)時間外限度時間(分文字列)を取得
		return getLimitMinutes(TERM_ONE_WEEK);
	}
	
	@Override
	public String getOneMonthLimitHours() {
		// 1ヶ月(month1)時間外限度時間(時間文字列)を取得
		return getLimitHours(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthLimitMinutes() {
		// 1ヶ月(month1)時間外限度時間(分文字列)を取得
		return getLimitMinutes(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthAttentionHours() {
		// 1ヶ月(month1)時間外注意時間(時間文字列)を取得
		return getAttentionHours(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthAttentionMinutes() {
		// 1ヶ月(month1)時間外注意時間(分文字列)を取得
		return getAttentionMinutes(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthWarningHours() {
		// 1ヶ月(month1)時間外警告時間(時間文字列)を取得
		return getWarningHours(TERM_ONE_MONTH);
	}
	
	@Override
	public String getOneMonthWarningMinutes() {
		// 1ヶ月(month1)時間外警告時間(分文字列)を取得
		return getWarningMinutes(TERM_ONE_MONTH);
	}
	
	@Override
	public int getLimit(String term) {
		// 時間外限度時間(分)を取得
		return getLimit(term, 0);
	}
	
	@Override
	public int getLimit(String term, int defaultValue) {
		// 限度基準情報を取得
		LimitStandardDtoInterface dto = getLimitStandardDto(term);
		// 限度基準情報が取得できなかった場合
		if (dto == null) {
			// デフォルト値を取得
			return defaultValue;
		}
		// 時間外限度時間(分)を取得
		return dto.getLimitTime();
	}
	
	@Override
	public String getLimitHours(String term) {
		// 時間外限度時間(時間文字列)を取得
		return getHours(getLimit(term));
	}
	
	@Override
	public String getLimitMinutes(String term) {
		// 時間外限度時間(分文字列)を取得
		return getMinutes(getLimit(term));
	}
	
	@Override
	public int getAttention(String term) {
		// 限度基準情報を取得
		LimitStandardDtoInterface dto = getLimitStandardDto(term);
		// 限度基準情報が取得できなかった場合
		if (dto == null) {
			// 0を取得
			return 0;
		}
		// 時間外注意時間(分)を取得
		return dto.getAttentionTime();
	}
	
	@Override
	public String getAttentionHours(String term) {
		// 時間外注意時間(時間文字列)を取得
		return getHours(getAttention(term));
	}
	
	@Override
	public String getAttentionMinutes(String term) {
		// 時間外注意時間(分文字列)を取得
		return getMinutes(getAttention(term));
	}
	
	@Override
	public int getWarning(String term) {
		// 限度基準情報を取得
		LimitStandardDtoInterface dto = getLimitStandardDto(term);
		// 限度基準情報が取得できなかった場合
		if (dto == null) {
			// 0を取得
			return 0;
		}
		// 時間外警告時間(分)を取得
		return dto.getWarningTime();
	}
	
	@Override
	public String getWarningHours(String term) {
		// 時間外警告時間(時間文字列)を取得
		return getHours(getWarning(term));
	}
	
	@Override
	public String getWarningMinutes(String term) {
		// 時間外警告時間(分文字列)を取得
		return getMinutes(getWarning(term));
	}
	
	@Override
	public int getOverLimit(String term, int value) {
		// 時間外限度時間(分)を取得
		int limit = getLimit(term);
		// 時間外限度時間(分)が0である場合
		if (limit == 0) {
			// 0を取得
			return 0;
		}
		// 限度超過量を取得
		int overLimit = value - limit;
		// 限度を超過していない場合は0を取得
		return overLimit < 0 ? 0 : overLimit;
	}
	
	@Override
	public boolean isOverLimit(String term, int value) {
		// 限度超過量が0より大きいかを確認
		return getOverLimit(term, value) > 0;
	}
	
	@Override
	public int getOverAttention(String term, int value) {
		// 時間外注意時間(分)を取得を取得
		int attention = getAttention(term);
		// 時間外注意時間(分)が0である場合
		if (attention == 0) {
			// 0を取得
			return 0;
		}
		// 注意超過量を取得
		int overAttention = value - attention;
		// 注意を超過していない場合は0を取得
		return overAttention < 0 ? 0 : overAttention;
	}
	
	@Override
	public boolean isOverAttention(String term, int value) {
		// 注意超過量が0より大きいかを確認
		return getOverAttention(term, value) > 0;
	}
	
	@Override
	public int getOverWarning(String term, int value) {
		// 時間外警告時間(分)を取得を取得
		int warning = getWarning(term);
		// 時間外警告時間(分)が0である場合
		if (warning == 0) {
			// 0を取得
			return 0;
		}
		// 警告超過量を取得
		int overWarning = value - warning;
		// 警告を超過していない場合は0を取得
		return overWarning < 0 ? 0 : overWarning;
	}
	
	@Override
	public boolean isOverWarning(String term, int value) {
		// 警告超過量が0より大きいかを確認
		return getOverWarning(term, value) > 0;
	}
	
	@Override
	public String getStyle(String term, int value) {
		// 限度基準情報が存在しない場合
		if (isLimitStandardExist(term) == false) {
			// 空を取得
			return MospConst.STR_DB_SPACE;
		}
		// 値が警告を超過している場合
		if (isOverWarning(term, value)) {
			// 警告用のスタイル文字列を取得
			return getWarningStyle();
		}
		// 値が注意を超過している場合
		if (isOverAttention(term, value)) {
			// 注意用のスタイル文字列を取得
			return getAttentionStyle();
		}
		// 空を取得
		return MospConst.STR_DB_SPACE;
	}
	
	@Override
	public String getOneMonthStyle(int value) {
		// 1ヶ月(month1)スタイル文字列を取得
		return getStyle(TERM_ONE_MONTH, value);
	}
	
	@Override
	public boolean isScheduledTimeAvailable() {
		// 勤務予定表示が有効であるかを確認
		return isExist() && PlatformUtility.isActivate(dto.getUseScheduledTime());
	}
	
	@Override
	public boolean isActualBeforeOvertimeAvailable() {
		// 勤務前残業が有効であるかを確認
		return isExist() && PlatformUtility.isActivate(dto.getBeforeOvertimeFlag());
	}
	
	@Override
	public boolean isPrescribedHolidayBasedOnCalendar() {
		// 所定休日取扱が暦日であるかを確認
		return isExist() && dto.getSpecificHolidayHandling() == 2;
	}
	
	@Override
	public int getSubHolidayAllNorm() throws MospException {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// intの最大値を取得
			return Integer.MAX_VALUE;
		}
		// 代休取得基準時間(全休)(分)を取得
		return TimeUtility.getMinutes(dto.getSubHolidayAllNorm());
	}
	
	@Override
	public int getSubHolidayHalfNorm() throws MospException {
		// 勤怠設定情報が存在しない場合
		if (isExist() == false) {
			// intの最大値を取得
			return Integer.MAX_VALUE;
		}
		// 代休取得基準時間(全休)(分)を取得
		return TimeUtility.getMinutes(dto.getSubHolidayHalfNorm());
	}
	
	/**
	 * 時間文字列を取得する。<br>
	 * @param time 分
	 * @return 時間文字列
	 */
	protected String getHours(int time) {
		// 分が0である場合
		if (time == 0) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// 時間文字列を取得
		return String.valueOf(TimeUtility.getHours(time));
	}
	
	/**
	 * 分文字列を取得する。<br>
	 * @param time 分
	 * @return 分文字列
	 */
	protected String getMinutes(int time) {
		// 分が0である場合
		if (time == 0) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// 分文字列を取得
		return String.valueOf(TimeUtility.getMinutes(time));
	}
	
	/**
	 * 注意用のスタイル文字列を取得する。<br>
	 * @return 注意用のスタイル文字列
	 */
	protected String getAttentionStyle() {
		// スタイル文字列(黄)を取得
		return PlatformConst.STYLE_YELLOW;
	}
	
	/**
	 * 警告用のスタイル文字列を取得する。<br>
	 * @return 警告用のスタイル文字列
	 */
	protected String getWarningStyle() {
		// スタイル文字列(赤)を取得
		return PlatformConst.STYLE_RED;
	}
	
	/**
	 * 丸めた時間を取得する。<br>
	 * @param time 対象時間
	 * @param type 丸め種別
	 * @param unit 丸め単位
	 * @return 丸めた時間
	 */
	protected int getRoundMinute(int time, int type, int unit) {
		return TimeUtility.getRoundMinute(time, type, unit);
	}
	
}
