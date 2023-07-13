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
package jp.mosp.time.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤務形態エンティティクラス。<br>
 */
public class WorkTypeEntity implements WorkTypeEntityInterface {
	
	/**
	 * 給与区分(有給)。<br>
	 */
	public static final String					CODE_PAY_TYPE_PAY	= "0";
	
	/**
	 * 勤務形態情報。<br>
	 */
	protected WorkTypeDtoInterface				workTypeDto;
	
	/**
	 * 勤務形態項目情報リスト。<br>
	 */
	protected List<WorkTypeItemDtoInterface>	itemDtoList;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public WorkTypeEntity() {
		// 処理無し
	}
	
	@Override
	public WorkTypeDtoInterface getWorkType() {
		return workTypeDto;
	}
	
	@Override
	public void setWorkTypeDto(WorkTypeDtoInterface workTypeDto) {
		this.workTypeDto = workTypeDto;
	}
	
	@Override
	public List<WorkTypeItemDtoInterface> getWorkTypeItemList() {
		return itemDtoList;
	}
	
	@Override
	public void setWorkTypeItemList(List<WorkTypeItemDtoInterface> itemDtoList) {
		this.itemDtoList = itemDtoList;
	}
	
	@Override
	public boolean isExist() {
		// 勤務形態情報が存在しない場合
		if (workTypeDto == null) {
			// 存在しないと判断
			return false;
		}
		// 勤務形態略称がnullである場合
		if (workTypeDto.getWorkTypeAbbr() == null) {
			// 存在しないと判断
			return false;
		}
		// 存在すると判断
		return true;
	}
	
	@Override
	public String getWorkTypeAbbr() {
		// 勤務形態情報確認
		if (isExist() == false) {
			return "";
		}
		// 勤務形態略称取得
		return workTypeDto.getWorkTypeAbbr();
	}
	
	@Override
	public Date getStartWorkTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORKSTART);
	}
	
	@Override
	public Date getEndWorkTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORKEND);
	}
	
	@Override
	public int getWorkTime() throws MospException {
		return getItemMinutes(TimeConst.CODE_WORKTIME);
	}
	
	@Override
	public int getRestTime() throws MospException {
		return getItemMinutes(TimeConst.CODE_RESTTIME);
	}
	
	@Override
	public Map<Integer, TimeDuration> getRestTimes() throws MospException {
		// 休憩時間間隔群(キー：開始時刻)(キー順)を準備
		Map<Integer, TimeDuration> restTimes = new TreeMap<Integer, TimeDuration>();
		// 休憩1～4を取得
		TimeDuration rest1 = getTimeDuration(TimeConst.CODE_RESTSTART1, TimeConst.CODE_RESTEND1);
		TimeDuration rest2 = getTimeDuration(TimeConst.CODE_RESTSTART2, TimeConst.CODE_RESTEND2);
		TimeDuration rest3 = getTimeDuration(TimeConst.CODE_RESTSTART3, TimeConst.CODE_RESTEND3);
		TimeDuration rest4 = getTimeDuration(TimeConst.CODE_RESTSTART4, TimeConst.CODE_RESTEND4);
		// 休憩1が設定されている場合
		if (rest1.isValid()) {
			// 休憩時間間隔群(キー：開始時刻)(キー順)に追加
			restTimes.put(rest1.getStartTime(), rest1);
		}
		// 休憩2が設定されている場合
		if (rest2.isValid()) {
			// 休憩時間間隔群(キー：開始時刻)(キー順)に追加
			restTimes.put(rest2.getStartTime(), rest2);
		}
		// 休憩3が設定されている場合
		if (rest3.isValid()) {
			// 休憩時間間隔群(キー：開始時刻)(キー順)に追加
			restTimes.put(rest3.getStartTime(), rest3);
		}
		// 休憩4が設定されている場合
		if (rest4.isValid()) {
			// 休憩時間間隔群(キー：開始時刻)(キー順)に追加
			restTimes.put(rest4.getStartTime(), rest4);
		}
		// 休憩時間間隔群(キー：開始時刻)(キー順)を取得
		return restTimes;
	}
	
	@Override
	public TimeDuration getRest1Time() throws MospException {
		// 休憩1時間間隔を取得
		return getTimeDuration(TimeConst.CODE_RESTSTART1, TimeConst.CODE_RESTEND1);
	}
	
	@Override
	public Map<Integer, TimeDuration> getRestTimes(int startTime, int endTime, RequestEntityInterface requestEntity,
			Set<String> statuses) throws MospException {
		// 休憩時間間隔群(キー：開始時刻)(キー順)を準備
		Map<Integer, TimeDuration> restTimes = new TreeMap<Integer, TimeDuration>();
		// 1.振出・休出申請(休日出勤)がある場合
		if (requestEntity.isWorkOnHolidaySubstituteOff(statuses)) {
			// 空の休憩時間間隔群を取得
			return restTimes;
		}
		// 2.全休か後半休の場合
		if (requestEntity.isAllHoliday(statuses) || requestEntity.isPmHoliday(statuses)) {
			// 空の休憩時間間隔群を取得
			return restTimes;
		}
		// 3.前半休の場合
		if (requestEntity.isAmHoliday(statuses)) {
			// 半休取得時休憩を取得
			TimeDuration duration = getTimeDuration(TimeConst.CODE_HALFRESTSTART, TimeConst.CODE_HALFRESTEND);
			// 勤務時間条件(午前休取得時にx時x分まで勤務を行う)を取得
			int condition = getItemMinutes(TimeConst.CODE_HALFREST);
			// 半休取得時休憩が妥当でない場合
			if (duration.isValid() == false) {
				// 空の休憩時間間隔群を取得
				return restTimes;
			}
			// 勤務時間条件に合致しない場合
			if (condition <= startTime || endTime < condition) {
				// 空の休憩時間間隔群を取得
				return restTimes;
			}
			// 半休取得時休憩を追加
			restTimes.put(duration.getStartTime(), duration);
			// 休憩時間間隔群(キー：開始時刻)(キー順)を取得
			return restTimes;
		}
		// 4.全休でも半休でもない場合(休憩1～休憩4を取得)
		return getRestTimes();
	}
	
	@Override
	public Map<Integer, TimeDuration> getRestTimes(int startTime, int endTime, RequestEntityInterface requestEntity,
			boolean isCompleted) throws MospException {
		// 対象となる承認状況群を取得
		Set<String> statuses = WorkflowUtility.getCompletedOrAppliedStatuses(isCompleted);
		// 規定休憩時間間隔群(キー：開始時刻)(キー順)を取得
		return getRestTimes(startTime, endTime, requestEntity, statuses);
	}
	
	/**
	 * 休憩1開始時刻を取得する。<br>
	 * @return 休憩1開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getRest1StartTime() throws MospException {
		return getItemValue(TimeConst.CODE_RESTSTART1);
	}
	
	/**
	 * 休憩1終了時刻を取得する。<br>
	 * @return 休憩1終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getRest1EndTime() throws MospException {
		return getItemValue(TimeConst.CODE_RESTEND1);
	}
	
	/**
	 * 前半休開始時刻を取得する。<br>
	 * <br>
	 * 後半休時の始業時刻にあたる。<br>
	 * <br>
	 * @return 前半休開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getFrontStartTime() throws MospException {
		return getItemValue(TimeConst.CODE_FRONTSTART);
	}
	
	/**
	 * 前半休終了時刻を取得する。<br>
	 * <br>
	 * 後半休時の終業時刻にあたる。<br>
	 * <br>
	 * @return 前半休終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getFrontEndTime() throws MospException {
		return getItemValue(TimeConst.CODE_FRONTEND);
	}
	
	/**
	 * 後半休開始時刻を取得する。<br>
	 * <br>
	 * 前半休時の始業時刻にあたる。<br>
	 * <br>
	 * @return 後半休開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getBackStartTime() throws MospException {
		return getItemValue(TimeConst.CODE_BACKSTART);
	}
	
	/**
	 * 後半休終了時刻を取得する。<br>
	 * <br>
	 * 前半休時の終業時刻にあたる。<br>
	 * <br>
	 * @return 後半休終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getBackEndTime() throws MospException {
		return getItemValue(TimeConst.CODE_BACKEND);
	}
	
	/**
	 * 直行かどうかを確認する。<br>
	 * @return 確認結果(true：直行、false：直行でない)
	 */
	@Override
	public boolean isDirectStart() {
		// 勤務形態項目値(予備)がチェックされているかを確認
		return isChecked(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START);
	}
	
	/**
	 * 直帰かどうかを確認する。<br>
	 * @return 確認結果(true：直帰、false：直帰でない)
	 */
	@Override
	public boolean isDirectEnd() {
		// 勤務形態項目値(予備)がチェックされているかを確認
		return isChecked(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END);
	}
	
	/**
	 * 割増休憩除外が有効であるかを確認する。<br>
	 * @return 確認結果(true：割増休憩除外が有効である、false：有効でない)
	 */
	@Override
	public boolean isNightRestExclude() {
		return isPreliminaryTheValue(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST,
				String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
	}
	
	/**
	 * 時短時間1開始時刻を取得する。<br>
	 * @return 時短時間1開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getShort1StartTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START);
	}
	
	/**
	 * 時短時間1終了時刻を取得する。<br>
	 * @return 時短時間1終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getShort1EndTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
	}
	
	/**
	 * 時短時間1給与区分を確認する。<br>
	 * @return 確認結果(true：時短時間1給与区分が有給、false：無給)
	 */
	@Override
	public boolean isShort1TypePay() {
		return isPreliminaryTheValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START, CODE_PAY_TYPE_PAY);
	}
	
	/**
	 * 時短時間1が設定されているかを確認する。<br>
	 * @return 確認結果(true：時短時間1が設定されている、false：されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public boolean isShort1TimeSet() throws MospException {
		return isItemValueSet(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START, TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
	}
	
	/**
	 * 時短時間2開始時刻を取得する。<br>
	 * @return 時短時間2開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getShort2StartTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START);
	}
	
	/**
	 * 時短時間2終了時刻を取得する。<br>
	 * @return 時短時間2終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public Date getShort2EndTime() throws MospException {
		return getItemValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
	}
	
	/**
	 * 時短時間2給与区分を確認する。<br>
	 * @return 確認結果(true：時短時間2給与区分が有給、false：無給)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public boolean isShort2TypePay() throws MospException {
		return isPreliminaryTheValue(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START, CODE_PAY_TYPE_PAY);
	}
	
	/**
	 * 時短時間2が設定されているかを確認する。<br>
	 * @return 確認結果(true：時短時間2が設定されている、false：されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public boolean isShort2TimeSet() throws MospException {
		return isItemValueSet(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START, TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
	}
	
	/**
	 * 勤務対象勤務形態であるかを確認する。<br>
	 * 未設定(休暇等)、所定休日、法定休日の場合は、勤務対象勤務形態でないと判断する。<br>
	 * @return 確認結果(true：勤務対象勤務形態である、false：そうでない。)
	 */
	@Override
	public boolean isWorkTypeForWork() {
		// 勤務形態コード確認
		if (getWorkTypeCode().isEmpty() || TimeUtility.isHoliday(getWorkTypeCode())) {
			// 未設定(休暇等)、所定休日、法定休日の場合
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isAutoBeforeOvertimeAvailable() {
		// 勤務前残業実績登録が有効であるかを確認
		return isPreliminaryTheValue(TimeConst.CODE_AUTO_BEFORE_OVERWORK, MospConst.INACTIVATE_FLAG_OFF);
	}
	
	@Override
	public TimeDuration getRegularTime(RequestEntityInterface requestEntity, boolean isCompleted) throws MospException {
		// 1.全休の場合
		if (requestEntity.isAllHoliday(isCompleted)) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 2.前半休の場合
		if (requestEntity.isAmHoliday(isCompleted)) {
			// 前半休時の始業時刻及び終業時刻(0:00からの分)を取得
			return getFirstHalfOffRegularTime();
		}
		// 3.後半休の場合
		if (requestEntity.isPmHoliday(isCompleted)) {
			// 後半休時の始業時刻及び終業時刻(0:00からの分)を取得
			return getSecondHalfOffRegularTime();
		}
		// 4.振出・休出申請(休日出勤)がある場合
		if (requestEntity.isWorkOnHolidaySubstituteOff(isCompleted)) {
			// 振出・休出申請(休日出勤)の休出予定時間(0:00からの分)を取得
			return requestEntity.getWorkOnHolidayTime(isCompleted);
		}
		// 5.それ以外の場合
		// 勤務形態の始業時刻(勤務日時刻に調整)を取得
		return getRegularTime();
	}
	
	@Override
	public TimeDuration getRegularAndHourlyHolidayTime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 規定時間(0:00からの分)を取得
		TimeDuration regularTime = getRegularTime(requestEntity, isCompleted);
		// 規定時間(時間単位休暇含む)の開始時刻及び終了時刻を準備
		int startTime = regularTime.getStartTime();
		int endTime = regularTime.getEndTime();
		// 初回連続時間休時間を取得
		TimeDuration firstTime = requestEntity.getHourlyHolidayFirstSequence();
		// 最終連続時間休時間を取得
		TimeDuration lastTime = requestEntity.getHourlyHolidayLastSequence();
		// 初回連続時間休時間が妥当であり規定時間の開始時刻と接する場合
		if (firstTime.isValid() && firstTime.getStartTime() == startTime) {
			// 開始時刻に初回連続時間休時間の終了時刻を設定
			startTime = firstTime.getEndTime();
		}
		// 最終連続時間休時間が妥当であり規定時間の終了時刻と接する場合
		if (lastTime.isValid() && lastTime.getEndTime() == endTime) {
			// 終了時刻に初回連続時間休時間の開始時刻を設定
			endTime = lastTime.getStartTime();
		}
		// 規定時間(時間単位休暇含む)(0:00からの分)を取得
		TimeDuration regularAndHourlyHoliday = TimeDuration.getInstance(startTime, endTime);
		// 規定時間(時間単位休暇含む)(0:00からの分)が妥当である場合
		if (regularAndHourlyHoliday.isValid()) {
			// 規定時間(時間単位休暇含む)(0:00からの分)を取得
			return regularAndHourlyHoliday;
		}
		// 規定時間(時間単位休暇含む)(0:00からの分)が妥当でない場合
		return regularTime;
	}
	
	@Override
	public TimeDuration getRegularAndShortTime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 規定時間(0:00からの分)を取得
		TimeDuration regularTime = getRegularTime(requestEntity, isCompleted);
		// 時短時間を取得
		TimeDuration short1Time = getShort1Time(requestEntity, isCompleted);
		TimeDuration short2Time = getShort2Time(requestEntity, isCompleted);
		// 規定時間(時短時間含む)(0:00からの分)の開始時刻と終了時刻を準備
		int startTime = regularTime.getStartTime();
		int endTime = regularTime.getEndTime();
		// 時短時間1が妥当である場合
		if (short1Time.isValid()) {
			// 規定時間(時短時間含む)開始時刻に時短時間1終了時刻を設定
			startTime = short1Time.getEndTime();
		}
		// 時短時間2が妥当である場合
		if (short2Time.isValid()) {
			// 規定時間(時短時間含む)終了時刻に時短時間2開始時刻を設定
			endTime = short2Time.getStartTime();
		}
		// 規定時間(時短時間含む)(0:00からの分)を取得
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	/**
	 * 勤務形態の始業時刻及び終業時刻(0:00からの分)を取得する。<br>
	 * @return 勤務形態の始業時刻及び終業時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	@Override
	public TimeDuration getRegularTime() throws MospException {
		// 勤務形態の始業時刻及び終業時刻(0:00からの分)を取得
		return getTimeDuration(TimeConst.CODE_WORKSTART, TimeConst.CODE_WORKEND);
	}
	
	/**
	 * 前半休時の始業時刻及び終業時刻(0:00からの分)を取得する。<br>
	 * @return 前半休時の始業時刻及び終業時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getFirstHalfOffRegularTime() throws MospException {
		// 前半休時の始業時刻及び終業時刻(0:00からの分)を取得
		return getTimeDuration(TimeConst.CODE_BACKSTART, TimeConst.CODE_BACKEND);
	}
	
	/**
	 * 後半休時の始業時刻及び終業時刻(0:00からの分)を取得する。<br>
	 * @return 後半休時の始業時刻及び終業時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getSecondHalfOffRegularTime() throws MospException {
		// 後半休時の始業時刻及び終業時刻(0:00からの分)を取得
		return getTimeDuration(TimeConst.CODE_FRONTSTART, TimeConst.CODE_FRONTEND);
	}
	
	/**
	 * 前半休と後半休の間の時間(0:00からの分)を取得する。<br>
	 * @return 前半休と後半休の間の時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getBitweenTime() throws MospException {
		// 前半休と後半休の間の時間(0:00からの分)を取得
		return getTimeDuration(TimeConst.CODE_FRONTEND, TimeConst.CODE_BACKSTART);
	}
	
	/**
	 * 時短時間1(0:00からの分)を取得する。<br>
	 * @return 時短時間1(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getShort1Time() throws MospException {
		// 時短時間1(0:00からの分)を取得
		return getTimeDuration(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START, TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
	}
	
	/**
	 * 時短時間2(0:00からの分)を取得する。<br>
	 * @return 時短時間2(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getShort2Time() throws MospException {
		// 時短時間2(0:00からの分)を取得
		return getTimeDuration(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START, TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
	}
	
	/**
	 * 時短時間1の開始時刻及び終了時刻(0:00からの分)を取得する。<br>
	 * ただし、前半休の場合は時短時間1は無いものとして扱う。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 時短時間1の開始時刻及び終了時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getShort1Time(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 前半休でない場合
		if (requestEntity.isAmHoliday(isCompleted) == false) {
			// 時短時間1(0:00からの分)を取得
			return getShort1Time();
		}
		// 0-0の(妥当でない)時間間隔を取得
		return TimeDuration.getInvalid();
	}
	
	@Override
	public TimeDuration getShort1PayTime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 時短時間1が有給である場合
		if (isShort1TypePay()) {
			// 時短時間1の開始時刻及び終了時刻(0:00からの分)を取得
			return getShort1Time(requestEntity, isCompleted);
		}
		// 時短時間1が無給である場合(0-0の(妥当でない)時間間隔を取得)
		return TimeDuration.getInvalid();
	}
	
	@Override
	public TimeDuration getShort1UnpayTime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 時短時間1が有給である場合
		if (isShort1TypePay()) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 時短時間1が無給である場合(時短時間1の開始時刻及び終了時刻(0:00からの分)を取得)
		return getShort1Time(requestEntity, isCompleted);
	}
	
	/**
	 * 時短時間2の開始時刻及び終了時刻(0:00からの分)を取得する。<br>
	 * ただし、後半休の場合は時短時間2は無いものとして扱う。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 時短時間2の開始時刻及び終了時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getShort2Time(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 後半休でない場合
		if (requestEntity.isPmHoliday(isCompleted) == false) {
			// 時短時間2(0:00からの分)を取得
			return getShort2Time();
		}
		// 0-0の(妥当でない)時間間隔を取得
		return TimeDuration.getInvalid();
	}
	
	@Override
	public TimeDuration getShort2PayTime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 時短時間2が有給である場合
		if (isShort2TypePay()) {
			// 時短時間2の開始時刻及び終了時刻(0:00からの分)を取得
			return getShort2Time(requestEntity, isCompleted);
		}
		// 時短時間2が無給である場合(0-0の(妥当でない)時間間隔を取得)
		return TimeDuration.getInvalid();
	}
	
	@Override
	public TimeDuration getShort2UnpayTime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 時短時間2が有給である場合
		if (isShort2TypePay()) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 時短時間2が無給である場合(時短時間1の開始時刻及び終了時刻(0:00からの分)を取得)
		return getShort2Time(requestEntity, isCompleted);
	}
	
	@Override
	public Map<Integer, TimeDuration> getShortUnpayTimes() throws MospException {
		// 時短時間1を取得
		TimeDuration short1Unpay = getShort1Time();
		// 時短時間1が有給である場合
		if (isShort1TypePay()) {
			// 0-0の(妥当でない)時間間隔を設定
			short1Unpay = TimeDuration.getInvalid();
		}
		// 時短時間2を取得
		TimeDuration short2Unpay = getShort2Time();
		// 時短時間2が有給である場合
		if (isShort2TypePay()) {
			// 0-0の(妥当でない)時間間隔を設定
			short2Unpay = TimeDuration.getInvalid();
		}
		// 時短時間(無給)群(キー：開始時刻(キー順))を準備
		Map<Integer, TimeDuration> shortUnpayTimes = new TreeMap<Integer, TimeDuration>();
		// 時短時間1を統合
		shortUnpayTimes = TimeUtility.mergeDurations(shortUnpayTimes, short1Unpay);
		// 時短時間2を統合
		shortUnpayTimes = TimeUtility.mergeDurations(shortUnpayTimes, short2Unpay);
		// 時短時間(無給)群(キー：開始時刻(キー順))を取得
		return shortUnpayTimes;
	}
	
	@Override
	public TimeDuration getBeforeOvertime(TimeSettingEntityInterface timeSettingEntity,
			RequestEntityInterface requestEntity, boolean isCompleted) throws MospException {
		// 時短時間1(無給)が設定されている勤務形態であり前半休でない場合
		if (isShort1TimeSet() && isShort1TypePay() == false && requestEntity.isAmHoliday(isCompleted) == false) {
			// 0-0の(妥当でない)時間間隔を取得(残業申請(勤務前残業)は無し)
			return TimeDuration.getInvalid();
		}
		// 残業申請(勤務前残業)の申請時間(分)を取得
		int beforeOvertime = requestEntity.getOvertimeMinutesBeforeWork(isCompleted);
		// 前半休である場合
		if (requestEntity.isAmHoliday(isCompleted)) {
			// 前半休と後半休の間の残業時間(0:00からの分)を取得
			return getBitweenOvertime(beforeOvertime, true);
		}
		// 勤怠設定の勤務前残業が有効であり勤務形態の勤務前残業実績登録が有効である場合
		if (isAutoBeforeOvertimeAvailable() && timeSettingEntity.isActualBeforeOvertimeAvailable()) {
			// 残業申請(勤務前残業)の申請時間(分)を再設定
			beforeOvertime = Integer.MAX_VALUE;
		}
		// 残業申請(勤務前残業)が無い場合
		if (beforeOvertime == 0) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 規定始業時刻を取得(時間単位休暇は考慮しない)
		int regularStartTime = getRegularTime(requestEntity, isCompleted).getStartTime();
		// 勤務前残業開始時刻を取得
		int startTime = TimeUtility.getAttendanceMinutes(regularStartTime - beforeOvertime);
		// 前残業時間(0:00からの分)を取得
		return TimeDuration.getInstance(startTime, regularStartTime);
	}
	
	@Override
	public TimeDuration getBitweenAfterOvertime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException {
		// 後半休でない場合
		if (requestEntity.isPmHoliday(isCompleted) == false) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 残業申請(勤務後残業)の申請時間(分)を取得
		int afterOvertime = requestEntity.getOvertimeMinutesAfterWork(isCompleted);
		// 前半休と後半休の間の残業時間(0:00からの分)を取得
		return getBitweenOvertime(afterOvertime, false);
	}
	
	/**
	 * 前半休と後半休の間の残業時間(0:00からの分)を取得する。<br>
	 * <br>
	 * 前半休時の勤務前残業及び後半休時の勤務後残業は、<br>
	 * 勤務形態の前半休及び後半休の間の時間を超え残業することはできない。<br>
	 * <br>
	 * @param overtime    残業時間
	 * @param isAmHoliday 半休フラグ(true：前半休、false：後半休)
	 * @return 前半休と後半休の間の残業時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getBitweenOvertime(int overtime, boolean isAmHoliday) throws MospException {
		// 残業時間が0である(残業申請が無い)場合
		if (overtime == 0) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 前半休と後半休の間の時間(0:00からの分)を取得
		TimeDuration bitweenTime = getBitweenTime();
		// 残業時間が前半休と後半休の間の時間より大きい場合
		if (bitweenTime.getMinutes() < overtime) {
			// 前半休と後半休の間の時間(0:00からの分)を取得
			return bitweenTime;
		}
		// 開始時刻及び終了時刻を準備
		int startTime = bitweenTime.getStartTime();
		int endTime = bitweenTime.getEndTime();
		// 前半休か後半休かを確認
		if (isAmHoliday) {
			// 開始時刻を調整(前半休の場合)
			startTime = TimeUtility.getAttendanceMinutes(endTime - overtime);
		} else {
			// 終了時刻を調整(後半休の場合)
			endTime = TimeUtility.getAttendanceMinutes(startTime + overtime);
		}
		// 前半休と後半休の間の残業時間(0:00からの分)を取得
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	@Override
	public int getOvertimeBeforeRest() throws MospException {
		// 残前休憩時間(分)を取得
		return getItemMinutes(TimeConst.CODE_OVERBEFORE);
	}
	
	@Override
	public int getWorkTimeBeforeOvertimeBeforeRest(RequestEntityInterface requestEntity, int legalWorkTime,
			boolean isCompleted) throws MospException {
		// 規定労働時間(勤務形態の始業時刻と終業時刻の差から規定休憩を除いた時間)を取得
		int restStart = getWorkTime();
		// 前半休である場合
		if (requestEntity.isAmHoliday(isCompleted)) {
			// 前半休時の始業時刻及び終業時刻の時間(分)を取得
			restStart = getFirstHalfOffRegularTime().getMinutes();
		}
		// 残前休憩開始勤務時間(分)を取得(最大でも法定労働時間)
		return restStart < legalWorkTime ? restStart : legalWorkTime;
	}
	
	@Override
	public int getWorkTimeBeforeOvertime(RequestEntityInterface requestEntity, int legalWorkTime, boolean isCompleted)
			throws MospException {
		// 規定労働時間(勤務形態の始業時刻と終業時刻の差から規定休憩を除いた時間)を取得
		int restStart = getWorkTime();
		// 前半休である場合
		if (requestEntity.isAmHoliday(isCompleted)) {
			// 前半休時の始業時刻及び終業時刻の時間(分)を取得
			restStart = getFirstHalfOffRegularTime().getMinutes();
		}
		// 後半休である場合
		if (requestEntity.isPmHoliday(isCompleted)) {
			// 後半休時の始業時刻及び終業時刻の時間(分)を取得
			restStart = getSecondHalfOffRegularTime().getMinutes();
		}
		// 残前休憩開始勤務時間(分)を取得(最大でも法定労働時間)
		return restStart < legalWorkTime ? restStart : legalWorkTime;
	}
	
	@Override
	public boolean isOvertimeRestValid() throws MospException {
		// 残業休憩時間が有効であるかを確認
		return getOvertimeRestPer() > 0 && getOvertimeRest() > 0;
	}
	
	@Override
	public int getOvertimeRestPer() throws MospException {
		// 残業休憩時間(毎)(分)を取得
		return getItemMinutes(TimeConst.CODE_OVERPER);
	}
	
	@Override
	public int getOvertimeRest() throws MospException {
		// 残業休憩時間(分)を取得
		return getItemMinutes(TimeConst.CODE_OVERREST);
	}
	
	@Override
	public Date getStartTime(RequestEntityInterface requestEntity, Set<String> statuses) throws MospException {
		// 勤務日を取得
		Date targetDate = requestEntity.getTargetDate();
		// 残業申請(勤務前残業)の申請時間(分)を取得
		int overtimeMinutes = requestEntity.getOvertimeMinutesBeforeWork(statuses);
		// 振出・休出申請(休出申請)の出勤予定時刻を取得
		Date workOnHolidayStartTime = requestEntity.getWorkOnHolidayStartTime(statuses);
		// 1.全休の場合
		if (requestEntity.isAllHoliday(statuses)) {
			return null;
		}
		// 2.前半休の場合
		if (requestEntity.isAmHoliday(statuses)) {
			// 勤務形態の後半休開始時刻を取得
			Date startTime = getBackStartTime();
			// 始業時刻から残業申請(勤務前残業)の申請時間(分)を減算
			startTime = DateUtility.addMinute(startTime, -overtimeMinutes);
			// 始業時刻と前半休終了時刻を比較
			Date frontEndTime = getFrontEndTime();
			if (startTime.before(frontEndTime)) {
				// 前半休終了時刻を設定(前半休の場合は前半休終了時刻が前残業の限度)
				startTime = frontEndTime;
			}
			// 勤務日時刻に調整
			return TimeUtility.getDateTime(targetDate, startTime);
		}
		// 3.振出・休出申請(休出申請)がある場合
		if (workOnHolidayStartTime != null) {
			// 勤務日時刻に調整(振出・休出申請(休出申請)の出勤予定時刻)
			return workOnHolidayStartTime;
		}
		// 初回連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Date> holidayTimeList = requestEntity.getHourlyHolidayFirstSequenceTimes(statuses);
		// 4.時短時間1が設定されている場合
		if (isShort1TimeSet()) {
			// 時短時間1終了時刻(勤務日時刻に調整)を取得
			Date short1EndTime = TimeUtility.getDateTime(targetDate, getShort1EndTime());
			// 時短時間1終了時刻と時間単位有給休暇が接する場合
			if (holidayTimeList.isEmpty() == false && holidayTimeList.get(0).compareTo(short1EndTime) == 0) {
				// 時間単位有給休暇の終了時刻を取得
				return holidayTimeList.get(1);
			}
			// 時短時間1(無給)が設定されている場合
			if (isShort1TypePay() == false) {
				// 時短時間1終了時刻を取得
				return short1EndTime;
			}
		}
		// 勤務形態の始業時刻(勤務日時刻に調整)を取得
		Date startTime = TimeUtility.getDateTime(targetDate, getStartWorkTime());
		// 5.勤務形態の始業時刻と時間単位有給休暇が接する場合
		if (holidayTimeList.isEmpty() == false && holidayTimeList.get(0).compareTo(startTime) == 0) {
			// 時間単位有給休暇の終了時刻を取得
			return holidayTimeList.get(1);
		}
		// 6.それ以外の場合
		// 勤務形態の始業時刻を取得
		return startTime;
	}
	
	@Override
	public Date getStartTime(RequestEntityInterface requestEntity) throws MospException {
		// 始業時刻を取得(申請済申請を考慮)
		return getStartTime(requestEntity, WorkflowUtility.getAppliedStatuses());
	}
	
	@Override
	public Date getEndTime(RequestEntityInterface requestEntity, Set<String> statuses) throws MospException {
		// 勤務日を取得
		Date targetDate = requestEntity.getTargetDate();
		// 残業申請(勤務後残業)の申請時間(分)を取得
		int overtimeMinutes = requestEntity.getOvertimeMinutesAfterWork(statuses);
		// 振出・休出申請(休出申請)の退勤予定時刻を取得
		Date workOnHolidayEndTime = requestEntity.getWorkOnHolidayEndTime(statuses);
		// 1.全休の場合
		if (requestEntity.isAllHoliday(statuses)) {
			return null;
		}
		// 2.後半休の場合
		if (requestEntity.isPmHoliday(statuses)) {
			// 勤務形態の前半休終了時刻を取得
			Date endTime = getFrontEndTime();
			// 終業時刻に残業申請(勤務後残業)の申請時間(分)を加算
			endTime = DateUtility.addMinute(endTime, overtimeMinutes);
			// 終業時刻と後半休開始時刻を比較
			Date backStartTime = getBackStartTime();
			if (endTime.after(backStartTime)) {
				// 後半休終了時刻を設定(後半休の場合は後半休開始時刻が後残業の限度)
				endTime = backStartTime;
			}
			// 勤務日時刻に調整
			return TimeUtility.getDateTime(targetDate, endTime);
		}
		// 3.振出・休出申請(休出申請)がある場合
		if (workOnHolidayEndTime != null) {
			// 勤務日時刻に調整(振出・休出申請(休出申請)の退勤予定時刻)
			return workOnHolidayEndTime;
		}
		// 最終連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Date> holidayTimeList = requestEntity.getHourlyHolidayLastSequenceTimes(statuses);
		// 4.時短時間2が設定されている場合
		if (isShort2TimeSet()) {
			// 時短時間2開始時刻(勤務日時刻に調整)を取得
			Date short2StartTime = TimeUtility.getDateTime(targetDate, getShort2StartTime());
			// 時短時間2開始時刻と時間単位有給休暇が接する場合
			if (holidayTimeList.isEmpty() == false && holidayTimeList.get(1).compareTo(short2StartTime) == 0) {
				// 時間単位有給休暇の開始時刻を取得
				return holidayTimeList.get(0);
			}
			// 時短時間2(無給)が設定されている場合
			if (isShort2TypePay() == false) {
				// 時短時間1終了時刻を取得
				return short2StartTime;
			}
		}
		// 勤務形態の終業時刻(勤務日時刻に調整)を取得
		Date endTime = TimeUtility.getDateTime(targetDate, getEndWorkTime());
		// 5.勤務形態の終業時刻と時間単位有給休暇が接する場合
		if (holidayTimeList.isEmpty() == false && holidayTimeList.get(1).compareTo(endTime) == 0) {
			// 時間単位有給休暇の開始時刻を取得
			return holidayTimeList.get(0);
		}
		// 6.それ以外の場合
		// 勤務形態の終業時刻を取得
		return endTime;
	}
	
	@Override
	public Date getEndTime(RequestEntityInterface requestEntity) throws MospException {
		// 終業時刻を取得(申請済申請を考慮)
		return getEndTime(requestEntity, WorkflowUtility.getAppliedStatuses());
	}
	
	/**
	 * 勤務形態コードを取得する。<br>
	 * 勤務形態コードが未設定(休暇等)の場合は、空文字を返す。<br>
	 * <br>
	 * @return 勤務形態コード
	 */
	protected String getWorkTypeCode() {
		// 勤務形態情報確認
		if (isExist() == false) {
			return "";
		}
		// 勤務形態コード取得
		return workTypeDto.getWorkTypeCode();
	}
	
	@Override
	public boolean isWorkOnLegal() {
		// 法定休日出勤であるかを確認
		return TimeUtility.isWorkOnLegalHoliday(getWorkTypeCode());
	}
	
	@Override
	public boolean isWorkOnPrescribed() {
		// 法定休日出勤であるかを確認
		return TimeUtility.isWorkOnPrescribedHoliday(getWorkTypeCode());
	}
	
	@Override
	public boolean isWorkOnHoliday() {
		// 休日出勤(法定か所定)であるかを確認
		return isWorkOnLegal() || isWorkOnPrescribed();
	}
	
	@Override
	public boolean isLegal() {
		// 法定休日(法定休日出勤含む)であるかを確認
		return TimeUtility.isLegalHoliday(getWorkTypeCode()) || isWorkOnLegal();
	}
	
	@Override
	public boolean isPrescribed() {
		// 所定休日(所定休日出勤含む)であるかを確認
		return TimeUtility.isPrescribedHoliday(getWorkTypeCode()) || isWorkOnPrescribed();
	}
	
	@Override
	public boolean isLegalOrPrescribed() {
		// 法定休日(法定休日出勤含む)か所定休日(所定休日出勤含む)であるかを確認
		return isLegal() || isPrescribed();
	}
	
	@Override
	public boolean isWorkDay() {
		// 平日であるかを確認
		return isLegalOrPrescribed() == false;
	}
	
	/**
	 * 有効日を取得する。<br>
	 * 勤務形態情報が未設定の場合は、nullを返す。<br>
	 * <br>
	 * @return 有効日
	 */
	@Override
	public Date getActivateDate() {
		// 勤務形態情報確認
		if (isExist() == false) {
			return null;
		}
		// 勤務形態コード取得
		return workTypeDto.getActivateDate();
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		// 処理無し
	}
	
	@Override
	public Date getItemValue(String workTypeItemCode) throws MospException {
		// 勤務形態項目値を取得
		return getItemValue(workTypeItemCode, false);
	}
	
	@Override
	public Date getItemValue(String workTypeItemCode, boolean isNullAvailable) throws MospException {
		// 勤務形態項目情報を取得
		WorkTypeItemDtoInterface dto = getWorkTypeItem(workTypeItemCode);
		// 勤務形態項目情報を確認
		if (dto == null) {
			// 勤務形態項目情報が取得できなかった場合
			return DateUtility.getDefaultTimeOrNull(isNullAvailable);
		}
		// 勤務形態項目値を確認
		if (dto.getWorkTypeItemValue() == null) {
			// 勤務形態項目値が取得できなかった場合
			return DateUtility.getDefaultTimeOrNull(isNullAvailable);
		}
		// 勤務形態項目値を取得
		return new Date(dto.getWorkTypeItemValue().getTime());
	}
	
	/**
	 * 勤務形態項目値(分)を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、0を返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目値(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int getItemMinutes(String workTypeItemCode) throws MospException {
		// 勤務形態項目情報を取得
		WorkTypeItemDtoInterface dto = getWorkTypeItem(workTypeItemCode);
		// 勤務形態項目情報を確認
		if (dto == null) {
			// 勤務形態項目情報が取得できなかった場合
			return 0;
		}
		// 勤務形態項目値を確認
		if (dto.getWorkTypeItemValue() == null) {
			// 勤務形態項目値が取得できなかった場合
			return 0;
		}
		// 勤務形態項目値(分)を取得
		return TimeUtility.getMinutes(dto.getWorkTypeItemValue());
	}
	
	/**
	 * 勤務形態項目値(予備)を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、空文字列を返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目値(予備)
	 */
	@Override
	public String getItemPreliminary(String workTypeItemCode) {
		// 勤務形態項目情報を取得
		WorkTypeItemDtoInterface dto = getWorkTypeItem(workTypeItemCode);
		// 勤務形態項目情報を確認
		if (dto == null) {
			// 勤務形態項目情報が取得できなかった場合
			return "";
		}
		// 勤務形態項目値(予備)を取得
		return dto.getPreliminary();
	}
	
	/**
	 * 勤務形態項目情報を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、nullを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目情報
	 */
	@Override
	public WorkTypeItemDtoInterface getWorkTypeItem(String workTypeItemCode) {
		// 勤務形態項目情報毎に確認
		for (WorkTypeItemDtoInterface dto : itemDtoList) {
			// 勤務形態項目コードが合致する場合
			if (dto.getWorkTypeItemCode().equals(workTypeItemCode)) {
				// 勤務形態項目情報を取得
				return dto;
			}
		}
		// 勤務形態項目情報が取得できなかった場合
		return null;
	}
	
	/**
	 * 勤務形態項目値(予備)(CheckBox)がチェックされているかどうかを確認する。<br>
	 * 勤務形態項目値(予備)が取得できない場合は、falseを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 確認結果(true：チェックされている、false：チェックされていない)
	 */
	protected boolean isChecked(String workTypeItemCode) {
		// チェックされているかどうかを確認
		return isPreliminaryTheValue(workTypeItemCode, MospConst.CHECKBOX_ON);
	}
	
	/**
	 * 勤務形態項目値(予備)が、その値かどうかを確認する。<br>
	 * 勤務形態項目値(予備)が取得できない場合は、falseを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @param theValue         その値
	 * @return 確認結果(true：その値である、false：その値でない)
	 */
	protected boolean isPreliminaryTheValue(String workTypeItemCode, String theValue) {
		// 勤務形態項目値(予備)を取得
		String preliminary = getItemPreliminary(workTypeItemCode);
		// 勤務形態項目値(予備)を確認
		if (preliminary.isEmpty()) {
			return false;
		}
		// 勤務形態項目値(予備)がその値であるかを確認
		return preliminary.equals(theValue);
	}
	
	/**
	 * 勤務形態項目値(予備)が、その値かどうかを確認する。<br>
	 * 勤務形態項目値(予備)が取得できない場合は、falseを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @param theValue         その値
	 * @return 確認結果(true：その値である、false：その値でない)
	 */
	protected boolean isPreliminaryTheValue(String workTypeItemCode, int theValue) {
		// 勤務形態項目値(予備)がその値かどうかを確認
		return isPreliminaryTheValue(workTypeItemCode, String.valueOf(theValue));
	}
	
	/**
	 * 勤務形態項目(時刻～時刻)が設定されているかを確認する。<br>
	 * 勤務形態項目コード(From)及び勤務形態項目コード(To)の値が
	 * 共にデフォルト時刻であった場合、設定されていないと判定する。<br>
	 * @param fromItemCode 勤務形態項目コード(From)
	 * @param toItemCode   勤務形態項目コード(To)
	 * @return 確認結果(true：設定されている、false：設定されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected boolean isItemValueSet(String fromItemCode, String toItemCode) throws MospException {
		// デフォルト時刻準備
		Date defaultTime = DateUtility.getDefaultTime();
		// 勤務形態項目確認
		if (getItemValue(fromItemCode).equals(defaultTime) && getItemValue(toItemCode).equals(defaultTime)) {
			// 勤務形態項目コード(From)及び勤務形態項目コード(To)の値が共にデフォルト時刻であった場合
			return false;
		}
		return true;
	}
	
	/**
	 * 時間間隔(0:00からの分)を取得する。<br>
	 * <br>
	 * 妥当な時間間隔が取得できなかった場合は、0-0の(妥当でない)時間間隔を返す。<br>
	 * <br>
	 * @param startItemCode 勤務形態項目コード(開始時刻)
	 * @param endItemCode   勤務形態項目コード(終了時刻)
	 * @return 時間間隔(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getTimeDuration(String startItemCode, String endItemCode) throws MospException {
		// 時刻(0:00からの分)を取得
		int startTime = getItemMinutes(startItemCode);
		int endTime = getItemMinutes(endItemCode);
		// 時間間隔を取得
		TimeDuration duration = TimeDuration.getInstance(startTime, endTime);
		// 時間間隔が妥当でない場合
		if (duration.isValid() == false) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 時間間隔を取得
		return duration;
	}
	
}
