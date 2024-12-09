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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.AdditionalLogicBeanInterface;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.HolidayHourlyWorkTypeCheckBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.entity.TimeDuration;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 時間単位休暇と勤務形態の確認処理。<br>
 */
public class HolidayHourlyWorkTypeCheckBean extends PlatformBean
		implements HolidayHourlyWorkTypeCheckBeanInterface, AdditionalLogicBeanInterface {
	
	/**
	 * 休暇申請参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface	holidayRequestRefer;
	
	/**
	 * カレンダユーティリティ。<br>
	 */
	protected ScheduleUtilBeanInterface				scheduleUtil;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface				timeMaster;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanの準備
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// 勤怠関連マスタ参照処理をBeanに設定
		scheduleUtil.setTimeMaster(timeMaster);
	}
	
	@Override
	public void checkHourlyHolidayAndWorkType(Collection<HolidayRequestDtoInterface> dtos,
			WorkTypeEntityInterface workType) throws MospException {
		// 始終業時間間隔情報(つまり時間単位休暇が取得できる時間)を取得
		TimeDuration duration = getWorkTime(dtos, workType);
		// 勤務形態の勤務時間(全日)に時間単位休暇の時間が含まれない場合
		if (checkHourlyHolidayAndWorkType(dtos, duration) == false) {
			//  勤務形態の勤務時間(全日)を取得
			Date startTime = DateUtility.getTime(duration.getStartTime());
			Date endTime = DateUtility.getTime(duration.getEndTime());
			// エラーメッセージを設定
			TimeMessageUtility.addErrorHorlyHolidayOutOfWorkTypeTime(mospParams, startTime, endTime, null);
		}
	}
	
	@Override
	public boolean doAdditionalLogic(Object... objects) throws MospException {
		// コードキーを取得
		String codeKey = (String)objects[0];
		// 勤怠申請時確認処理の追加処理の場合
		if (MospUtility.isEqual(codeKey, TimeConst.CODE_KEY_ADD_ATTENDANCEREGISTBEAN_CHECKAPPLI)) {
			// 勤怠情報を取得
			AttendanceDtoInterface attendanceDto = MospUtility.castObject(objects[1]);
			// 時間単位休暇と勤務形態の時間を確認
			checkForAttendance(attendanceDto, codeKey);
			// 追加処理有り
			return true;
		}
		// 時間単位休暇確認である場合
		if (MospUtility.isEqual(codeKey, TimeConst.CODE_KEY_ADD_HOLIDAYREQUESTREGISTBEAN_CHECKTIMEHOLIDAY)) {
			// 休暇申請情報と行インデックスを取得
			HolidayRequestDtoInterface holidayRequestDto = MospUtility.castObject(objects[1]);
			Integer row = MospUtility.castObject(objects[2]);
			// 時間単位休暇と勤務形態の時間を確認
			checkForHoliday(holidayRequestDto, row, codeKey);
			// 追加処理有り
			return true;
		}
		// 追加処理無しと判断
		return false;
	}
	
	/**
	 * 時間単位休暇と勤務形態の時間を確認する。<bt>
	 * @param attendanceDto 勤怠情報
	 * @param codeKey       コードキー(エラーメッセージ用)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkForAttendance(AttendanceDtoInterface attendanceDto, String codeKey) throws MospException {
		// 個人IDと勤務日と勤務形態を取得
		String personalId = attendanceDto.getPersonalId();
		Date workDate = attendanceDto.getWorkDate();
		String workTypeCode = attendanceDto.getWorkTypeCode();
		// 休暇申請情報群を取得
		List<HolidayRequestDtoInterface> dtos = holidayRequestRefer.getHolidayRequestListOnWorkflow(personalId,
				workDate, workDate);
		// 時間単位休暇と勤務形態の時間を確認
		checkHourlyHolidayAndWorkType(dtos, personalId, workDate, workTypeCode, codeKey, null, null);
	}
	
	/**
	 * 時間単位休暇と勤務形態の時間を確認する。<bt>
	 * @param requestDto 対象休暇申請情報
	 * @param row        行インデックス(エラーメッセージ用)
	 * @param codeKey    コードキー(エラーメッセージ用)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkForHoliday(HolidayRequestDtoInterface requestDto, Integer row, String codeKey)
			throws MospException {
		// 全休である場合
		if (TimeRequestUtility.isHolidayRangeAll(requestDto)) {
			// 確認不要
			return;
		}
		// 個人IDと対象日((半日休暇と時間単位休暇は日の範囲指定が不可))を取得
		String personalId = requestDto.getPersonalId();
		Date targetDate = requestDto.getRequestStartDate();
		// 休暇申請情報群を取得
		List<HolidayRequestDtoInterface> dtos = holidayRequestRefer.getHolidayRequestListOnWorkflow(personalId,
				targetDate, targetDate);
		// 休暇申請情報群にある同一ワークフロー番号の情報を入れ替え
		TimeRequestUtility.replaceWorkflowDto(dtos, requestDto);
		// 勤務形態コードを取得
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate, true);
		// 時間単位休暇と勤務形態の時間を確認
		checkHourlyHolidayAndWorkType(dtos, personalId, targetDate, workTypeCode, codeKey, requestDto, row);
	}
	
	/**
	 * 時間単位休暇と勤務形態の時間を確認する。<bt>
	 * @param dtos         確認対象休暇申請情報群
	 * @param personalId   個人ID
	 * @param targetDate   対象日
	 * @param workTypeCode 勤務形態コード(半日休暇の時間を取得するために用いる)
	 * @param codeKey      コードキー(エラーメッセージ用)
	 * @param requestDto   申請(下書)された休暇申請情報(勤怠申請時確認の場合はnull)(エラーメッセージ用)
	 * @param row          行インデックス(エラーメッセージ用)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHourlyHolidayAndWorkType(Collection<HolidayRequestDtoInterface> dtos, String personalId,
			Date targetDate, String workTypeCode, String codeKey, HolidayRequestDtoInterface requestDto, Integer row)
			throws MospException {
		// 時間単位休暇が無い場合
		if (TimeRequestUtility.hasHolidayRangeHour(dtos) == false) {
			// 確認不要
			return;
		}
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workType = timeMaster.getWorkTypeEntity(workTypeCode, targetDate);
		// 休暇申請情報群に半休が無い場合
		if (TimeRequestUtility.hasHolidayRangeHalf(dtos) == false) {
			// 勤務形態の勤務時間と時間単位休暇の時間を確認
			checkForAll(dtos, workType, codeKey, requestDto, row);
			// 処理終了
			return;
		}
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 半休でない場合
			if (TimeRequestUtility.isHolidayRangeHalf(dto) == false) {
				// 次の休暇申請情報へ
				continue;
			}
			// 半日休暇と時間単位休暇の重複を確認
			checkForHalf(dto, dtos, workType, codeKey, requestDto, row);
		}
	}
	
	/**
	 * 勤務形態の勤務時間と時間単位休暇の時間を確認する。<bt>
	 * @param dtos       確認対象休暇申請情報群(時間単位休暇時間取得用)
	 * @param workType   勤務形態エンティティ(勤務形態の時間を取得するために用いる)
	 * @param codeKey    コードキー(エラーメッセージ用)
	 * @param requestDto 申請(下書)された休暇申請情報(勤怠申請時確認の場合はnull)(エラーメッセージ用)
	 * @param row        行インデックス(エラーメッセージ用)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkForAll(Collection<HolidayRequestDtoInterface> dtos, WorkTypeEntityInterface workType,
			String codeKey, HolidayRequestDtoInterface requestDto, Integer row) throws MospException {
		// 始終業時間間隔情報(つまり時間単位休暇が取得できる時間)を取得
		TimeDuration duration = getWorkTime(dtos, workType);
		// 重複していない時間間隔群がある場合
		if (checkHourlyHolidayAndWorkType(dtos, duration) == false) {
			// エラーメッセージを設定
			addErrorMessage(duration, codeKey, requestDto, row);
		}
	}
	
	/**
	 * 半日休暇と時間単位休暇の重複を確認する。<bt>
	 * @param dto        確認対象休暇申請情報
	 * @param dtos       確認対象休暇申請情報群(時間単位休暇時間取得用)
	 * @param workType   勤務形態エンティティ(半日休暇の時間を取得するために用いる)
	 * @param codeKey    コードキー(エラーメッセージ用)
	 * @param requestDto 申請(下書)された休暇申請情報(勤怠申請時確認の場合はnull)(エラーメッセージ用)
	 * @param row        行インデックス(エラーメッセージ用)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkForHalf(HolidayRequestDtoInterface dto, Collection<HolidayRequestDtoInterface> dtos,
			WorkTypeEntityInterface workType, String codeKey, HolidayRequestDtoInterface requestDto, Integer row)
			throws MospException {
		// 半休時始終業時間間隔情報(つまり時間単位休暇が取得できる時間)を取得
		TimeDuration duration = getHalftWorkTime(dto, workType);
		// 重複していない時間間隔群がある場合
		if (checkHourlyHolidayAndWorkType(dtos, duration) == false) {
			// エラーメッセージを設定
			addErrorMessage(duration, codeKey, requestDto, row);
		}
	}
	
	/**
	 * 勤務形態の勤務時間に時間単位休暇の時間が含まれるかを確認する。<br>
	 * @param dtos     確認対象休暇申請情報群(時間単位休暇時間取得用)
	 * @param duration 勤務形態の勤務時間
	 * @return 確認結果(true：勤務形態の勤務時間に時間単位休暇の時間が含まれる、false：そうではない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean checkHourlyHolidayAndWorkType(Collection<HolidayRequestDtoInterface> dtos, TimeDuration duration)
			throws MospException {
		// 時間休時間間隔情報群を取得
		Set<TimeDuration> hourlyHolidayTimes = TimeRequestUtility.getHourlyHolidayTimes(dtos);
		// 時間休時間間隔群を統合
		Map<Integer, TimeDuration> durations = TimeUtility.mergeDurations(hourlyHolidayTimes);
		// 重複していない時間間隔群を取得
		Map<Integer, TimeDuration> notOverlap = TimeUtility.getNotOverlap(durations, duration);
		// 勤務形態の勤務時間に時間単位休暇の時間が含まれるかを確認
		return MospUtility.isEmpty(notOverlap);
	}
	
	/**
	 * エラーメッセージを設定する。<bt>
	 * @param duration   時間単位休暇が取得できる時間間隔情報
	 * @param codeKey    コードキー(エラーメッセージ用)
	 * @param requestDto 申請(下書)された休暇申請情報(勤怠申請時確認の場合はnull)(エラーメッセージ用)
	 * @param row        行インデックス(エラーメッセージ用)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void addErrorMessage(TimeDuration duration, String codeKey, HolidayRequestDtoInterface requestDto,
			Integer row) throws MospException {
		// 既にエラーメッセージが設定されている場合
		if (mospParams.hasErrorMessage()) {
			// エラーメッセージ設定不要
			return;
		}
		// 時間単位休暇が取得できる時間を取得
		Date startTime = DateUtility.getTime(duration.getStartTime());
		Date endTime = DateUtility.getTime(duration.getEndTime());
		// 勤怠申請時確認処理の追加処理の場合 
		if (MospUtility.isEqual(codeKey, TimeConst.CODE_KEY_ADD_ATTENDANCEREGISTBEAN_CHECKAPPLI)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorHorlyHolidayOutOfWorkTypeTime(mospParams, startTime, endTime, row);
			// 処理終了
			return;
		}
		// 申請(下書)された休暇申請が時間単位休暇である場合
		if (TimeRequestUtility.isHolidayRangeHour(requestDto)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorHorlyHolidayOutOfWorkTypeTime(mospParams, startTime, endTime, row);
			// 処理終了
			return;
		}
		// エラーメッセージを設定
		TimeMessageUtility.addErrorHalfAndHoulyHolidayDuplicate(mospParams, startTime, endTime, row);
	}
	
	/**
	 * 始終業時間間隔情報(つまり時間単位休暇が取得できる時間)を取得する。<br>
	 * @param dtos     確認対象休暇申請情報群(ここでは使われないがアドオンで用いる)
	 * @param workType 勤務形態エンティティ
	 * @return 始終業時間間隔情報
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getWorkTime(Collection<HolidayRequestDtoInterface> dtos, WorkTypeEntityInterface workType)
			throws MospException {
		// 始終業時間間隔情報(つまり時間単位休暇が取得できる時間)を取得
		return workType.getRegularTime();
	}
	
	/**
	 * 半休時始終業時間間隔情報(つまり時間単位休暇が取得できる時間)を取得する。<br>
	 * @param dto      休暇申請情報
	 * @param workType 勤務形態エンティティ
	 * @return 半休時始終業時間間隔情報
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getHalftWorkTime(HolidayRequestDtoInterface dto, WorkTypeEntityInterface workType)
			throws MospException {
		// 半休時始終業時間間隔情報(つまり時間単位休暇が取得できる時間)を取得
		return TimeRequestUtility.getWorkTimeForHalfHoliday(dto, workType);
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
		// 勤怠関連マスタ参照処理をBeanに設定
		scheduleUtil.setTimeMaster(timeMaster);
	}
	
}
