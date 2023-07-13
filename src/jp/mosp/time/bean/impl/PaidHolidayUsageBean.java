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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayUsageBeanInterface;
import jp.mosp.time.comparator.settings.PaidHolidayDataAcquisitionDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.PaidHolidayUsageDto;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 有給休暇取得状況確認情報作成処理。<br>
 */
public class PaidHolidayUsageBean extends PlatformBean implements PaidHolidayUsageBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(消化すべき有給休暇日数)。<br>
	 */
	protected static final String							APP_PAID_HOLIDAY_USE_DAY	= "PaidHolidayUseDay";
	
	/**
	 * プラットフォームマスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface					master;
	
	/**
	 * 有給休暇情報参照処理。<br>
	 */
	protected PaidHolidayDataReferenceBeanInterface			paidHolidayRefer;
	
	/**
	 * 休暇申請情報参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface			requestRefer;
	
	/**
	 * 有給休暇トランザクション情報参照処理。<br>
	 */
	protected PaidHolidayTransactionReferenceBeanInterface	paidHolidayTransactionRefer;
	
	/**
	 * 有給休暇情報郡(キー：個人ID)。<br>
	 * 対象日+1年よりも前に取得日が来ている有給休暇情報を保持する。<br>
	 */
	protected Map<String, Set<PaidHolidayDataDtoInterface>>	paidHolidayMap;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		master = createBeanInstance(PlatformMasterBeanInterface.class);
		paidHolidayRefer = createBeanInstance(PaidHolidayDataReferenceBeanInterface.class);
		requestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		paidHolidayTransactionRefer = createBeanInstance(PaidHolidayTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public Collection<PaidHolidayUsageDto> makePaidHolidayUsages(String personalId, Date targetDate)
			throws MospException {
		// 有給休暇取得状況確認情報群(取得日昇順)を準備
		Set<PaidHolidayUsageDto> paidHolidayUsages = new LinkedHashSet<PaidHolidayUsageDto>();
		// 人事基本情報を取得
		HumanDtoInterface humanDto = master.getHuman(personalId, targetDate);
		// 対象期間に有効な有給休暇情報群(取得日昇順)を取得
		Collection<PaidHolidayDataDtoInterface> paidHolidays = getPaidHolidays(personalId, targetDate);
		// 人事基本情報か対象期間に有効な有給休暇情報群(取得日昇順)を取得できなかった場合
		if (humanDto == null || MospUtility.isEmpty(paidHolidays)) {
			// 空の有給休暇取得状況確認情報群を取得
			return Collections.emptySet();
		}
		// 対象期間を取得
		Date usageFromDate = getUsageFromDate(paidHolidays, targetDate);
		Date usageToDate = getUsageToDate(paidHolidays, targetDate);
		// 対象期間に有効な有給休暇情報毎に処理
		for (PaidHolidayDataDtoInterface paidHoliday : paidHolidays) {
			// 有給休暇取得状況確認情報を作成し有給休暇取得状況確認情報群(取得日昇順)に追加
			paidHolidayUsages.add(makePaidHolidayUsage(humanDto, usageFromDate, usageToDate, paidHoliday));
		}
		// 所属名称を取得
		String sectionName = master.getSectionName(humanDto.getSectionCode(), targetDate);
		// 未消化日数と備考を取得
		float shortDay = getShortDay(paidHolidayUsages);
		String remark = getRemark(paidHolidayUsages);
		// 有給休暇取得状況確認情報毎に処理
		for (PaidHolidayUsageDto dto : paidHolidayUsages) {
			// 有給休暇取得状況確認情報に所属名称と未消化日数と備考を設定
			dto.setSectionName(sectionName);
			dto.setShortDay(shortDay);
			dto.setRemark(remark);
		}
		// 有給休暇取得状況確認情報群(取得日昇順)を取得
		return paidHolidayUsages;
	}
	
	/**
	 * 対象期間に有効な有給休暇情報群(取得日昇順)を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Collection<PaidHolidayDataDtoInterface> getPaidHolidays(String personalId, Date targetDate)
			throws MospException {
		// 有給休暇情報群を設定
		setPaidHolidays(targetDate);
		// 個人IDで有給休暇情報群(対象日時点で取得日が来ている有給休暇情報)を取得
		Set<PaidHolidayDataDtoInterface> paidHolidayAll = paidHolidayMap.get(personalId);
		// 個人IDで有給休暇情報群を取得できなかった場合
		if (paidHolidayAll == null) {
			// 空の有給休暇情報群を取得
			return Collections.emptySet();
		}
		// 対象期間を取得
		Date usageFromDate = getUsageFromDate(paidHolidayAll, targetDate);
		Date usageToDate = getUsageToDate(paidHolidayAll, targetDate);
		// 対象期間に有効な有給休暇情報群を準備
		Map<Date, PaidHolidayDataDtoInterface> paidHolidays = new TreeMap<Date, PaidHolidayDataDtoInterface>();
		// 有給休暇情報(対象日時点で取得日が来ている有給休暇情報)毎に処理
		for (PaidHolidayDataDtoInterface paidHoliday : paidHolidayAll) {
			// 取得日及び期限日を取得
			Date acquisitionDate = paidHoliday.getAcquisitionDate();
			Date limitDate = paidHoliday.getLimitDate();
			// 取得日から期限日が対象期間と重複している場合
			if (DateUtility.isOnTheTerm(usageFromDate, usageToDate, acquisitionDate, limitDate)) {
				// 対象期間に有効な有給休暇情報群に追加
				paidHolidays.put(acquisitionDate, paidHoliday);
			}
		}
		// 対象期間に有効な有給休暇情報群(取得日昇順)を取得
		return paidHolidays.values();
	}
	
	/**
	 * 有給休暇情報群を設定する。<br>
	 * <br>
	 * 対象日+1年よりも前に取得日が来ている有給休暇情報を取得する。<br>
	 * 但し、期限日がデフォルト日付(1970年1月1日)より前の情報は取得しない。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPaidHolidays(Date targetDate) throws MospException {
		// 有給休暇情報郡が取得されている場合
		if (paidHolidayMap != null) {
			// 処理無し
			return;
		}
		// 期間開始日(デフォルト日付)
		Date startDate = DateUtility.getDefaultTime();
		// 期間終了日(対象日+1年：対象期間中の情報を纏めて取得するため)を取得(対象期間終了は遅くても対象日+1年-1日)
		Date endDate = DateUtility.addYearAndDay(targetDate, 1, -1);
		// 期間内に有効な有給休暇情報リストを取得
		List<PaidHolidayDataDtoInterface> list = paidHolidayRefer.getPaidHolidayDataForTerm(startDate, endDate);
		// 有給休暇情報郡(キー：個人ID)を設定
		paidHolidayMap = PlatformUtility.getPersonalIdMap(list);
	}
	
	/**
	 * 対象期間開始日を取得する。<br>
	 * <br>
	 * 有給休暇情報群が設定されている必要がある。<br>
	 * 対象期間開始日が取得できない場合、デフォルト日付を取得する。<br>
	 * <br>
	 * @param paidHolidays 有給休暇情報群(対象日+1年よりも前に取得日が来ている有給休暇情報)
	 * @param targetDate   対象日
	 * @return 対象期間開始日
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Date getUsageFromDate(Collection<PaidHolidayDataDtoInterface> paidHolidays, Date targetDate)
			throws MospException {
		// 有給休暇情報リストを取得
		List<PaidHolidayDataDtoInterface> list = new ArrayList<PaidHolidayDataDtoInterface>(paidHolidays);
		// 取得日でソート(取得日降順)
		Collections.sort(list, Collections.reverseOrder(new PaidHolidayDataAcquisitionDateComparator()));
		// 有給休暇情報毎に処理
		for (PaidHolidayDataDtoInterface dto : list) {
			// 取得日が対象日よりも後である場合
			if (dto.getAcquisitionDate().after(targetDate)) {
				// 次の有給休暇情報へ
				continue;
			}
			// 対象日時点で最新の取得日を取得
			return dto.getAcquisitionDate();
		}
		// デフォルト日付を取得を取得
		return DateUtility.getDefaultTime();
	}
	
	/**
	 * 対象期間終了日を取得する。<br>
	 * 有給休暇情報群が設定されている必要がある。<br>
	 * @param paidHolidays 有給休暇情報群(対象日+1年よりも前に取得日が来ている有給休暇情報)
	 * @param targetDate   対象日
	 * @return 対象期間開始日
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Date getUsageToDate(Collection<PaidHolidayDataDtoInterface> paidHolidays, Date targetDate)
			throws MospException {
		// 対象期間終了日を取得(対象期間開始日から1年)
		return DateUtility.addDay(DateUtility.addYear(getUsageFromDate(paidHolidays, targetDate), 1), -1);
	}
	
	/**
	 * 有給休暇取得状況確認情報を作成する。<br>
	 * @param humanDto      人事基本情報
	 * @param usageFromDate 対象期間(From)
	 * @param usageToDate   対象期間(To)
	 * @param paidHoliday   有給休暇情報
	 * @return 有給休暇取得状況確認情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected PaidHolidayUsageDto makePaidHolidayUsage(HumanDtoInterface humanDto, Date usageFromDate, Date usageToDate,
			PaidHolidayDataDtoInterface paidHoliday) throws MospException {
		// 有給休暇取得状況確認情報を準備
		PaidHolidayUsageDto dto = new PaidHolidayUsageDto();
		// 個人ID及び取得日を取得
		String personalId = humanDto.getPersonalId();
		Date acquisitionDate = paidHoliday.getAcquisitionDate();
		// 有給休暇取得状況確認情報に値(人事基本情報及び所属名称)を設定
		dto.setPersonalId(personalId);
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		dto.setLastName(humanDto.getLastName());
		dto.setFirstName(humanDto.getFirstName());
		dto.setSectionCode(humanDto.getSectionCode());
		// 有給休暇取得状況確認情報に値(対象期間)を設定
		dto.setUsageFromDate(usageFromDate);
		dto.setUsageToDate(usageToDate);
		// 有給休暇取得状況確認情報に値(取得日)を設定
		dto.setAcquisitionDate(acquisitionDate);
		// 手動付与日数を取得
		double transactionDays = getTransactionDays(personalId, acquisitionDate, usageToDate);
		// 有給休暇取得状況確認情報に値(付与日数)を設定
		dto.setGivingDay(paidHoliday.getHoldDay() + transactionDays);
		// 申請日を取得
		Map<Date, Integer> useDates = getUseDates(personalId, acquisitionDate, usageFromDate, usageToDate);
		// 有給休暇取得状況確認情報に値(申請日)を設定
		dto.setUseDates(useDates);
		// 有給休暇取得状況確認情報に値(申請日数)を設定
		dto.setUseDay(getUseDay(useDates));
		// 有給休暇取得状況確認情報を取得
		return dto;
	}
	
	/**
	 * 手動付与日数を取得する。<br>
	 * 対象日時点で有効な有給休暇トランザクション情報を基に、計算する。<br>
	 * <br>
	 * @param personalId      個人ID
	 * @param acquisitionDate 取得日
	 * @param targetDate      対象日
	 * @return 手動付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected double getTransactionDays(String personalId, Date acquisitionDate, Date targetDate) throws MospException {
		// 有給休暇トランザクション情報リストを取得
		List<PaidHolidayTransactionDtoInterface> dtos = paidHolidayTransactionRefer.findForList(personalId,
				acquisitionDate, DateUtility.getDefaultTime(), targetDate);
		// 手動付与日数を取得
		return TimeUtility.getPaidHolidayManualDays(dtos);
	}
	
	/**
	 * 申請日(キー：申請日、値：休暇範囲)を取得する。<br>
	 * 申請日昇順。<br>
	 * 有給休暇申請は、期間で申請しても1日ずつに休暇申請が分けられることを前提としている。<br>
	 * @param personalId      個人ID
	 * @param acquisitionDate 取得日
	 * @param usageFromDate   対象期間(From)
	 * @param usageToDate     対象期間(To)
	 * @return 申請日(キー：申請日、値：休暇範囲)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Date, Integer> getUseDates(String personalId, Date acquisitionDate, Date usageFromDate,
			Date usageToDate) throws MospException {
		// 申請日を準備
		Map<Date, Integer> useDates = new TreeMap<Date, Integer>();
		// 休暇申請情報リスト(有給休暇)(申請済：一次戻、下書、取下以外)(時間単位有給休暇も含まれる)を取得
		List<HolidayRequestDtoInterface> requests = requestRefer.getAppliedPaidHolidayRequests(personalId,
				acquisitionDate, usageFromDate, usageToDate);
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface request : requests) {
			// 申請日(申請開始日)を取得
			Date requestDate = request.getRequestStartDate();
			// 時間単位休暇申請である場合
			if (TimeRequestUtility.isHolidayRangeHour(request)) {
				// 次の休暇申請へ
				continue;
			}
			// 申請日に既に休暇範囲が設定されている場合(既に半休を取得していた場合)
			if (useDates.get(requestDate) != null) {
				// 申請日に全休を設定
				useDates.put(requestDate, TimeConst.CODE_HOLIDAY_RANGE_ALL);
				// 次の休暇申請へ
				continue;
			}
			// 申請日に休暇範囲を設定
			useDates.put(requestDate, request.getHolidayRange());
		}
		// 申請日を取得
		return useDates;
	}
	
	/**
	 * 申請日数を取得する。<br>
	 * @param useDates 申請日(キー：申請日、値：休暇範囲)
	 * @return 申請日数
	 */
	protected float getUseDay(Map<Date, Integer> useDates) {
		// 申請日数を準備
		float useDay = 0F;
		// 申請日毎に処理
		for (int holidayRange : useDates.values()) {
			// 申請日数を加算
			useDay += TimeUtility.getHolidayTimes(holidayRange);
		}
		// 申請日数を取得
		return useDay;
	}
	
	/**
	 * 未消化日数(合算)を取得する。<br>
	 * 有給休暇取得状況確認情報に設定されている次の値を用いる。<br>
	 * <ul>
	 * <li>申請日</li>
	 * </ul>
	 * @param paidHolidayUsages 有給休暇取得状況確認情報群
	 * @return 未消化日数(合算)
	 */
	protected float getShortDay(Set<PaidHolidayUsageDto> paidHolidayUsages) {
		// 未消化日数(合算)を準備(消化すべき有給休暇日数)
		float shortDay = mospParams.getApplicationProperty(APP_PAID_HOLIDAY_USE_DAY, 0);
		// 有給休暇取得状況確認情報毎に処理
		for (PaidHolidayUsageDto dto : paidHolidayUsages) {
			// 未消化日数(合算)を申請日数で減算
			shortDay -= dto.getUseDay();
		}
		// 未消化日数(合算)を取得
		return shortDay < 0 ? 0 : shortDay;
	}
	
	/**
	 * 備考を取得する。<br>
	 * 有給休暇取得状況確認情報に設定されている次の値を用いる。<br>
	 * <ul>
	 * <li>取得日</li>
	 * </ul>
	 * @param paidHolidayUsages 有給休暇取得状況確認情報群(取得日昇順)
	 * @return 備考
	 */
	protected String getRemark(Set<PaidHolidayUsageDto> paidHolidayUsages) {
		// 検索日付に一番近い取得日を取得
		Date lastAcquisitionDate = MospUtility.getLastValue(paidHolidayUsages).getAcquisitionDate();
		// 検索日付に一番近い取得日の1年前を取得
		Date doubleTrackStartDate = DateUtility.addYear(lastAcquisitionDate, -1);
		// 有給休暇取得状況確認情報毎に処理
		for (PaidHolidayUsageDto dto : paidHolidayUsages) {
			// 取得日を取得
			Date acquisitionDate = dto.getAcquisitionDate();
			// 検索日付に一番近い取得日と同じ場合
			if (DateUtility.isSame(acquisitionDate, lastAcquisitionDate)) {
				// 次の有給休暇取得状況確認情報へ
				continue;
			}
			// 検索日付に一番近い取得日の1年前より取得日が後である場合
			if (acquisitionDate.after(doubleTrackStartDate)) {
				// 備考を取得
				return TimeMessageUtility.getPaidHolidaySplit(mospParams);
			}
		}
		// 備考(空文字)を取得
		return MospConst.STR_EMPTY;
	}
	
}
