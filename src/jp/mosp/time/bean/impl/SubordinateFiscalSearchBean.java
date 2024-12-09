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

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.impl.HumanSubordinateBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.HolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.SubordinateFiscalSearchBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.SubordinateFiscalListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;
import jp.mosp.time.dto.settings.impl.SubordinateFiscalListDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * 統計情報一覧検索処理。<br>
 */
public class SubordinateFiscalSearchBean extends HumanSubordinateBean implements SubordinateFiscalSearchBeanInterface {
	
	/**
	 * 勤怠情報参照クラス。<br>
	 */
	protected TotalTimeCalcBeanInterface			totalTimeCalc;
	
	/**
	 * 勤怠集計データ登録クラス。<br>
	 */
	protected TotalTimeReferenceBeanInterface		totalTimeRefer;
	
	/**
	 * 休暇申請情報参照クラス。<br>
	 */
	protected HolidayRequestReferenceBeanInterface	holidayRequestRefer;
	
	/**
	 * 勤怠情報参照クラス。<br>
	 */
	protected AttendanceReferenceBeanInterface		attendanceRefer;
	
	/**
	 * 休暇付与情報参照処理。<br>
	 */
	protected HolidayDataReferenceBeanInterface		holidayDataRefer;
	
	/**
	 * 休暇数参照処理。<br>
	 */
	protected HolidayInfoReferenceBeanInterface		holidayInfo;
	
	/**
	 * 有給休暇残数取得処理。<br>
	 */
	protected PaidHolidayRemainBeanInterface		paidHolidayRemain;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface				timeMaster;
	
	/**
	 * 表示年度。
	 */
	protected int									displayYear;
	
	/**
	 * 対象年。
	 */
	protected int									targetYear;
	
	/**
	 * 対象月。
	 */
	protected int									targetMonth;
	
	
	/**
	 * {@link HumanSubordinateBean#HumanSubordinateBean()}を実行する。<br>
	 */
	public SubordinateFiscalSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// 勤怠集計クラス
		totalTimeCalc = createBeanInstance(TotalTimeCalcBeanInterface.class);
		// 休暇申請情報参照クラス
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		// 勤怠集計参照クラス
		totalTimeRefer = createBeanInstance(TotalTimeReferenceBeanInterface.class);
		// 勤怠参照クラス
		attendanceRefer = createBeanInstance(AttendanceReferenceBeanInterface.class);
		// 休暇付与情報参照処理
		holidayDataRefer = createBeanInstance(HolidayDataReferenceBeanInterface.class);
		// 休暇数参照処理
		holidayInfo = createBeanInstance(HolidayInfoReferenceBeanInterface.class);
		// 有給休暇残数取得処理
		paidHolidayRemain = createBeanInstance(PaidHolidayRemainBeanInterface.class);
		// 勤怠関連マスタ参照処理
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// 休暇残数取得処理に勤怠関連マスタ参照処理を設定
		paidHolidayRemain.setTimeMaster(timeMaster);
	}
	
	@Override
	public List<SubordinateFiscalListDtoInterface> getSubordinateFiscalList() throws MospException {
		// 統計情報リスト取得
		return subordinateFiscalList(getHumanSubordinates(humanType, PlatformConst.WORKFLOW_TYPE_TIME));
	}
	
	/**
	 * 人事情報リストを基に、統計情報一覧情報リストを取得する。<br>
	 * <br>
	 * <br>
	 * @param humanList 人事情報リスト
	 * @return 統計情報一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<SubordinateFiscalListDtoInterface> subordinateFiscalList(List<HumanDtoInterface> humanList)
			throws MospException {
		// 統計情報リスト準備
		List<SubordinateFiscalListDtoInterface> subordinateFiscalList = new ArrayList<SubordinateFiscalListDtoInterface>();
		// 年度の開始日、終了日取得
		Date firstDate = MonthUtility.getFiscalYearFirstDate(displayYear, mospParams);
		Date lastDate = MonthUtility.getFiscalYearLastDate(displayYear, mospParams);
		// 検索基準開始日、終了日取得
		Date searchEndDate = DateUtility.getEndTargetDate(firstDate, lastDate);
		// エラーメッセージリストを保持
		List<String> errorMessageList = new ArrayList<String>(mospParams.getErrorMessageList());
		// 検索結果から部下一覧リストを作成
		for (HumanDtoInterface humanDto : humanList) {
			// 設定適用判断日付（検索年月最終日）取得
			Date applicationTargetDate = MonthUtility.getYearMonthTermLastDate(targetYear, targetMonth, mospParams);
			// 設定適用情報取得
			ApplicationDtoInterface applicationDto = timeMaster.getApplication(humanDto, applicationTargetDate);
			if (applicationDto == null) {
				continue;
			}
			// 個人IDを取得
			String personalId = humanDto.getPersonalId();
			// 部下年度一覧DTO準備
			SubordinateFiscalListDto dto = new SubordinateFiscalListDto();
			// 年度期間内の勤怠情報リスト取得
			List<AttendanceDtoInterface> attendanceList = attendanceRefer.getAttendanceList(humanDto.getPersonalId(),
					firstDate, lastDate);
			// 勤怠集計マップ取得
			Map<Integer, TotalTimeDataDtoInterface> totalMap = totalTimeRefer.findFiscalMap(personalId, firstDate,
					lastDate);
			if (!attendanceList.isEmpty() || (totalMap != null && !totalMap.isEmpty())) {
				// 残業時間設定
				setOverTime(dto, totalMap, personalId, firstDate);
			} else {
				dto.setOverTime(0);
			}
			// 有休日数設定
			setPaidHoliday(dto, personalId, displayYear, searchEndDate);
			// ストック日数設定
			setStockHoliday(dto, personalId, displayYear, searchEndDate);
			// 休暇日数設定
			setHoliday(dto, humanDto, applicationTargetDate, firstDate, lastDate);
			// 人事情報設定
			setHuman(dto, humanDto);
			// 追加業務ロジック処理
			doStoredLogic(TimeConst.CODE_KEY_ADD_SUBORDINATEFISCALSEARCHBEAN_SUBORDINATEFISCALLIST, dto, totalMap);
			// リストに追加
			subordinateFiscalList.add(dto);
		}
		// 保持していたエラーメッセージリストを再設定(検索によるエラーメッセージを除去)
		mospParams.getErrorMessageList().clear();
		mospParams.getErrorMessageList().addAll(errorMessageList);
		// 統計情報リストを取得
		return subordinateFiscalList;
	}
	
	/**
	 * 残業時間を取得し、設定する。<br>
	 * @param dto        統計情報一覧DTO
	 * @param totalMap   勤怠集計情報群(キー：月)
	 * @param personalId 個人ID
	 * @param firstDate  年度初日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setOverTime(SubordinateFiscalListDtoInterface dto, Map<Integer, TotalTimeDataDtoInterface> totalMap,
			String personalId, Date firstDate) throws MospException {
		// 値準備
		int overTime = 0;
		// 12回処理
		for (int i = 0; i < 12; i++) {
			// 対象日取得
			Date targetDate = DateUtility.addMonth(firstDate, i);
			// 年月取得
			int year = DateUtility.getYear(targetDate);
			int month = DateUtility.getMonth(targetDate);
			// 勤怠集計情報を取得
			TotalTimeDataDtoInterface totalTimeDto = totalMap.get(month);
			// 勤怠集計情報を取得できなかった場合
			if (totalTimeDto == null) {
				// 勤怠集計を実施
				totalTimeDto = totalTimeCalc.calc(personalId, year, month, false);
				// 勤怠集計情報を勤怠集計情報群(キー：月)に追加
				totalMap.put(month, totalTimeDto);
			}
			// 残業時間取得
			overTime += totalTimeDto.getOvertime();
		}
		// 残業時間設定
		dto.setOverTime(overTime);
	}
	
	/**
	 * 有給休暇を取得し、設定する。<br>
	 * 下記のいずれかを基準日とする。
	 * ・システム日付が表示年度開始日以前の場合、表示年度開始日。<br>
	 * ・システム日付が表示年度期間内の場合、システム日付。<br>
	 * ・システム日付が表示年度最終日以降の場合、表示年度最終日。<br>
	 * @param dto         統計情報一覧DTO
	 * @param personalId  個人ID
	 * @param displayYear 表示年度
	 * @param targetDate  表示期間対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPaidHoliday(SubordinateFiscalListDtoInterface dto, String personalId, int displayYear,
			Date targetDate) throws MospException {
		// 年度で有給休暇残情報を取得
		HolidayRemainDto remain = paidHolidayRemain.getPaidHolidayRemainForYear(personalId, targetDate, displayYear);
		// 対象年度初日時点における残日数(及び残時間数)と対象日における残日数(及び残時間数)を設定
		dto.setPaidHolidayDays(remain.getGivenDays());
		dto.setPaidHolidayTime(remain.getGivenHours());
		dto.setPaidHolidayRestDays(remain.getRemainDays());
		dto.setPaidHolidayRestTime(remain.getRemainHours());
	}
	
	/**
	 * ストック休暇を設定する。<br>
	 * 下記のいずれかを基準日とする。
	 * ・システム日付が表示年度開始日以前の場合、表示年度開始日。<br>
	 * ・システム日付が表示年度期間内の場合、システム日付。<br>
	 * ・システム日付が表示年度最終日以降の場合、表示年度最終日。<br>
	 * @param dto         統計情報一覧DTO
	 * @param personalId  個人ID
	 * @param displayYear 表示年度
	 * @param targetDate  表示期間対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setStockHoliday(SubordinateFiscalListDtoInterface dto, String personalId, int displayYear,
			Date targetDate) throws MospException {
		// 年度でストック休暇残情報を取得
		HolidayRemainDto remain = paidHolidayRemain.getStockHolidayRemainForYear(personalId, targetDate, displayYear);
		// 対象年度初日時点における残日数と対象日における残日数を設定
		dto.setStockHolidayDays(remain.getGivenDays());
		dto.setStockHolidayRestDays(remain.getRemainDays());
	}
	
	/**
	 * 休暇を統計情報に設定する。<br>
	 * <br>
	 * 年度内に付与(休暇付与情報の有効日が年度内)された休暇付与情報に対して、
	 * 年度内に申請された休暇申請情報と合わせて、残日数及び残時間数を計算する。<br>
	 * <br>
	 * @param dto        統計情報
	 * @param humanDto   人事情報
	 * @param targetDate 対象日(1日の時間数を取得する際に利用)
	 * @param firstDate  表示年度開始日
	 * @param lastDate   表示年度最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setHoliday(SubordinateFiscalListDtoInterface dto, HumanDtoInterface humanDto, Date targetDate,
			Date firstDate, Date lastDate) throws MospException {
		// 人事情報から個人IDを取得
		String personalId = humanDto.getPersonalId();
		// 有休時間取得限度時間(1日の時間数)を取得
		int hoursPerDay = timeMaster.getPaidHolidayHoursPerDay(humanDto, targetDate);
		// 1日の分数を取得
		int minutesPerDay = holidayInfo.getMinutesPerDay(personalId, targetDate);
		// 季節休コードを取得
		String holidayCode = mospParams.getApplicationProperty(TimeConst.APP_SHOW_SEASON_HOLIDAY_CODE);
		// 休暇区分(特別休暇)を取得
		int holidayType = TimeConst.CODE_HOLIDAYTYPE_SPECIAL;
		// 現在の残日数及び残時間数を準備
		double currentDays = 0D;
		int currentHours = 0;
		// 利用分数を準備
		int useMinutes = 0;
		// 休暇取得日群を準備
		Set<Date> acquisitionDates = new HashSet<Date>();
		// 対象期間内に付与された休暇付与情報リストを取得
		List<HolidayDataDtoInterface> list = holidayDataRefer.getActiveListForTerm(personalId, firstDate, lastDate,
				holidayType, holidayCode);
		// 対象期間内に付与された休暇付与情報毎に処理
		for (HolidayDataDtoInterface holidayDataDto : list) {
			// 現在の残日数及び残時間数を加算
			currentDays += TimeUtility.getCurrentDays(holidayDataDto);
			currentHours += TimeUtility.getCurrentHours(holidayDataDto);
			// 休暇取得日群に追加
			acquisitionDates.add(holidayDataDto.getActivateDate());
			// 利用分数を加算
			useMinutes += holidayInfo.getUseMinutes(holidayDataDto);
		}
		// 現在の残日数及び残時間数を取得
		SimpleEntry<Double, Integer> uses = holidayRequestRefer.getHolidayUses(personalId, firstDate, lastDate,
				holidayType, holidayCode, acquisitionDates);
		// 利用日数及び利用時間数を取得
		double useDays = uses.getKey();
		int useHours = uses.getValue();
		// 休暇残情報を準備
		HolidayRemainDto remain = new HolidayRemainDto();
		remain.setRemainDays(currentDays);
		remain.setRemainHours(currentHours);
		// 休暇残情報を計算
		TimeUtility.calcHolidayRemains(remain, useDays, useHours, useMinutes, hoursPerDay, minutesPerDay);
		// 統計情報に設定
		dto.setSeasonHolidayDays(currentDays);
		dto.setSeasonHolidayRestDays(remain.getRemainDays());
		dto.setSeasonHolidayRestHours(remain.getRemainHours());
		dto.setSeasonHolidayRestMinutes(remain.getRemainMinutes());
	}
	
	/**
	 * 人事情報を統計情報に設定する。<br>
	 * @param dto 統計情報
	 * @param humanDto 人事情報
	 */
	protected void setHuman(SubordinateFiscalListDtoInterface dto, HumanDtoInterface humanDto) {
		if (humanDto == null) {
			return;
		}
		dto.setPersonalId(humanDto.getPersonalId());
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		dto.setLastName(humanDto.getLastName());
		dto.setFirstName(humanDto.getFirstName());
		dto.setSectionCode(humanDto.getSectionCode());
	}
	
	@Override
	public void setDisplayYear(int displayYear) {
		this.displayYear = displayYear;
	}
	
	@Override
	public void setTargetYear(int targetYear) {
		this.targetYear = targetYear;
	}
	
	@Override
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
	
}
