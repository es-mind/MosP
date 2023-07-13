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
import java.util.Optional;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * 有給休暇残数取得処理。<br>
 */
public class PaidHolidayRemainBean extends PlatformBean implements PaidHolidayRemainBeanInterface {
	
	/**
	 * 有給休暇情報参照処理。<br>
	 */
	protected PaidHolidayDataReferenceBeanInterface			paidHolidayDataRefer;
	
	/**
	 * 有給休暇付与処理。<br>
	 */
	protected PaidHolidayDataGrantBeanInterface				paidHolidayDataGrant;
	
	/**
	 * 有給休暇手動付与情報参照処理。<br>
	 */
	protected PaidHolidayTransactionReferenceBeanInterface	paidHolidayTransactionRefer;
	
	/**
	 * ストック休暇情報参照処理。<br>
	 */
	protected StockHolidayDataReferenceBeanInterface		stockHolidayDataRefer;
	
	/**
	 * ストック休暇手動付与情報参照処理。<br>
	 */
	protected StockHolidayTransactionReferenceBeanInterface	stockHolidayTransactionRefer;
	
	/**
	 * 休暇申請情報参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequestRefer;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayRemainBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		paidHolidayDataRefer = createBeanInstance(PaidHolidayDataReferenceBeanInterface.class);
		paidHolidayDataGrant = createBeanInstance(PaidHolidayDataGrantBeanInterface.class);
		paidHolidayTransactionRefer = createBeanInstance(PaidHolidayTransactionReferenceBeanInterface.class);
		stockHolidayDataRefer = createBeanInstance(StockHolidayDataReferenceBeanInterface.class);
		stockHolidayTransactionRefer = createBeanInstance(StockHolidayTransactionReferenceBeanInterface.class);
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
	}
	
	@Override
	public List<HolidayRemainDto> getPaidHolidayRemainsForRequest(String personalId, Date targetDate)
			throws MospException {
		// 有給休暇残情報リストを準備
		List<HolidayRemainDto> remains = new ArrayList<HolidayRemainDto>();
		//承認状況群(申請済承認状況群(一次戻と下書と取下以外))を準備
		Set<String> statuses = WorkflowUtility.getAppliedStatuses();
		// 対象日で有給休暇情報リスト(取得日昇順)を取得
		List<PaidHolidayDataDtoInterface> dtos = paidHolidayDataRefer.getPaidHolidayDataForDate(personalId, targetDate);
		// 有給休暇情報毎に処理
		for (PaidHolidayDataDtoInterface dto : dtos) {
			// 有給休暇残情報(休暇申請対象日指定無し：有給休暇情報に対する全ての申請を対象)を取得し設定
			remains.add(getPaidHolidayRemain(dto, targetDate, statuses, targetDate, null));
		}
		// 有給休暇残情報リストを取得
		return remains;
	}
	
	@Override
	public List<HolidayRemainDto> getPaidHolidayRemainsForView(String personalId, Date targetDate)
			throws MospException {
		// 有給休暇残情報リストを準備
		List<HolidayRemainDto> remains = new ArrayList<HolidayRemainDto>();
		//承認状況群(申請済承認状況群(一次戻と下書と取下以外))を準備
		Set<String> statuses = WorkflowUtility.getAppliedStatuses();
		// 対象日で有給休暇情報リスト(取得日昇順)を取得
		List<PaidHolidayDataDtoInterface> dtos = paidHolidayDataRefer.getPaidHolidayDataForDate(personalId, targetDate);
		// 対象日よりも後の有給休暇情報群を設定
		dtos.addAll(paidHolidayDataRefer.findForNextInfoList(personalId, targetDate));
		// 有給休暇情報毎に処理
		for (PaidHolidayDataDtoInterface dto : dtos) {
			// 有給休暇残情報(休暇申請対象日指定無し：有給休暇情報に対する全ての申請を対象)を取得し設定
			remains.add(getPaidHolidayRemain(dto, targetDate, statuses, targetDate, null));
		}
		// 有給休暇残情報リストを取得
		return remains;
	}
	
	@Override
	public List<HolidayRemainDto> getPaidHolidayRemainsForList(String personalId, Date targetDate)
			throws MospException {
		// 有給休暇残情報リストを準備
		List<HolidayRemainDto> remains = new ArrayList<HolidayRemainDto>();
		//承認状況群(申請済承認状況群(一次戻と下書と取下以外))を準備
		Set<String> statuses = WorkflowUtility.getAppliedStatuses();
		// 対象日で有給休暇情報リスト(取得日昇順)を取得
		List<PaidHolidayDataDtoInterface> dtos = paidHolidayDataRefer.getPaidHolidayDataForDate(personalId, targetDate);
		// 有給休暇情報毎に処理
		for (PaidHolidayDataDtoInterface dto : dtos) {
			// 有給休暇残情報を取得し設定
			remains.add(getPaidHolidayRemain(dto, targetDate, statuses, targetDate, targetDate));
		}
		// 有給休暇残情報リストを取得
		return remains;
	}
	
	@Override
	public HolidayRemainDto getPaidHolidayRemainForYear(String personalId, Date targetDate, int targetYear)
			throws MospException {
		// 年度の初日及び最終日を取得
		Date firstDate = MonthUtility.getFiscalYearFirstDate(targetYear, mospParams);
		Date lastDate = MonthUtility.getFiscalYearLastDate(targetYear, mospParams);
		//承認状況群(有効な承認状況群(下書と取下以外))を準備
		Set<String> statuses = WorkflowUtility.getEffectiveStatuses();
		// 有給休暇残情報を準備
		HolidayRemainDto remain = TimeUtility.getBarePaidHolidayRemain(firstDate, targetDate);
		// 対象となる有給休暇情報リスト(取得日が対象日以前で期限日が対象期間初日以降)を取得
		List<PaidHolidayDataDtoInterface> dtos = paidHolidayDataRefer.getPaidHolidayDatas(personalId, targetDate,
				targetDate, firstDate);
		// 有給休暇情報毎に処理
		for (PaidHolidayDataDtoInterface dto : dtos) {
			// 有給休暇残情報に値(付与日数と付与時間数と残日数と残時間数)を設定
			setPaidHolidayRemain(remain, dto, targetDate, firstDate, lastDate, statuses);
		}
		// 有給休暇残情報を取得
		return remain;
	}
	
	@Override
	public Optional<HolidayRemainDto> getPaidHolidayRemainForStock(String personalId, Date targetDate)
			throws MospException {
		// 期限日決定用の有給休暇情報を取得
		PaidHolidayDataDtoInterface paidHolidayData = paidHolidayDataRefer
			.getPaidHolidayDataForExpiration(personalId, targetDate).orElse(null);
		// 期限日決定用の有給休暇情報を取得できなかった場合
		if (MospUtility.isEmpty(paidHolidayData)) {
			// nullを取得
			return Optional.ofNullable(null);
		}
		// 対象となる期限日を取得
		Date limitDate = paidHolidayData.getLimitDate();
		//承認状況群(有効な承認状況群(下書と取下以外))を準備
		Set<String> statuses = WorkflowUtility.getEffectiveStatuses();
		// 有給休暇残情報を準備
		HolidayRemainDto remain = TimeUtility.getBarePaidHolidayRemain(limitDate, limitDate);
		// 対象となる有給休暇情報リスト有給休暇情報リスト(取得日が引数の期限日以前で期限日が引数の期限日)を取得
		List<PaidHolidayDataDtoInterface> dtos = paidHolidayDataRefer.getExpiredPaidHolidayDatas(personalId, limitDate);
		// 有給休暇情報毎に処理
		for (PaidHolidayDataDtoInterface dto : dtos) {
			// 対象日における有給休暇残情報を取得(取得日が同じである全ての有給休暇手動付与を考慮)
			HolidayRemainDto remainForData = getPaidHolidayRemain(dto, limitDate, statuses, null, null);
			// 休暇残情報に対象日における残日数(及び残時間数)を設定
			remain.setRemainDays(remain.getRemainDays() + remainForData.getRemainDays());
			remain.setRemainHours(remain.getRemainHours() + remainForData.getRemainHours());
		}
		// 有給休暇残情報を取得
		return Optional.ofNullable(remain);
	}
	
	@Override
	public double getCarryoverDays(String personalId, Date targetDate, Date acquisitionDate) throws MospException {
		//承認状況群(承認済)を準備
		Set<String> statuses = WorkflowUtility.getCompletedStatuses();
		// 前年度繰越有給休暇残情報(残日数と残時間数)群を取得
		Set<HolidayRemainDto> remains = getCarryoverRemains(personalId, targetDate, acquisitionDate, statuses);
		// 前年度有給休暇残日数を取得
		return TimeUtility.getRemainDays(remains);
	}
	
	@Override
	public int getCarryoverHours(String personalId, Date targetDate, Date acquisitionDate) throws MospException {
		//承認状況群(承認済)を準備
		Set<String> statuses = WorkflowUtility.getCompletedStatuses();
		// 前年度繰越有給休暇残情報(残日数と残時間数)群を取得
		Set<HolidayRemainDto> remains = getCarryoverRemains(personalId, targetDate, acquisitionDate, statuses);
		// 前年度有給休暇残日数を取得
		return TimeUtility.getRemainHours(remains);
	}
	
	/**
	 * 有給休暇残情報に値を設定する。<br>
	 * 既に値が設定されている場合は、その値に足し引きした値を設定する。<br>
	 * <br>
	 * 休暇残情報の
	 * 付与日数(及び付与時間数)に対象期間初日時点における残日数(及び残時間数)を、
	 * 残日数(及び残時間数)に対象日における残日数(及び残時間数)を、設定する。<br>
	 * 但し、残日数の計算に用いる申請は対象期間最終日までを対象とする。<br>
	 * <br>
	 * 対象となる有給休暇トランザクション(手動付与)情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.対象となる承認状況である有給休暇申請<br>
	 * 2.休暇取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 3.申請日が対象期間最終日以前<br>
	 * <br>
	 * @param remain     休暇残情報
	 * @param dto        有給休暇情報
	 * @param targetDate 対象日
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @param statuses   承認状況
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPaidHolidayRemain(HolidayRemainDto remain, PaidHolidayDataDtoInterface dto, Date targetDate,
			Date firstDate, Date lastDate, Set<String> statuses) throws MospException {
		// 対象日における有給休暇残情報を取得(休暇申請は対象期間最終日まで考慮)
		HolidayRemainDto remainForTargetDate = getPaidHolidayRemain(dto, targetDate, statuses, targetDate, lastDate);
		// 休暇残情報に対象日における残日数(及び残時間数)を設定
		remain.setRemainDays(remain.getRemainDays() + remainForTargetDate.getRemainDays());
		remain.setRemainHours(remain.getRemainHours() + remainForTargetDate.getRemainHours());
		// 対象期間初日より後に取得した有給休暇である場合
		if (dto.getAcquisitionDate().after(firstDate)) {
			// 処理終了(対象期間初日時点における残日数(及び残時間数)の計算対象外)
			return;
		}
		// 対象期間初日の1日前を取得
		Date beforeDate = DateUtility.addDay(firstDate, -1);
		// 対象期間初日時点における有給休暇残情報を取得
		HolidayRemainDto remainForFirstDate = getPaidHolidayRemain(dto, targetDate, statuses, targetDate, beforeDate);
		// 休暇残情報に対象期間初日時点における残日数(及び残時間数)を設定
		remain.setGivenDays(remain.getGivenDays() + remainForFirstDate.getRemainDays());
		remain.setGivenHours(remain.getGivenHours() + remainForFirstDate.getRemainHours());
	}
	
	/**
	 * 前年度繰越有給休暇残情報(残日数と残時間数)群を取得する。<br>
	 * 付与日時点で有効な付与日より前に付与された有給休暇情報リストを対象とする。<br>
	 * (対象日時点で最新の有給休暇情報を取得対象とする。)<br>
	 * 休暇申請については、当年の有給休暇付与日より前に申請された情報を対象とする。<br>
	 * @param personalId      個人ID
	 * @param targetDate      対象日
	 * @param acquisitionDate 付与日(今年度に付与された有給休暇情報の付与日)
	 * @param statuses        承認状況群(対象となる休暇申請の承認状況)
	 * @return 前年度繰越休暇日数または前年度繰越休暇時間数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<HolidayRemainDto> getCarryoverRemains(String personalId, Date targetDate, Date acquisitionDate,
			Set<String> statuses) throws MospException {
		// 付与日を対象日として有給休暇付与回数を取得
		int grtantTimes = paidHolidayDataGrant.getGrantTimes(personalId, acquisitionDate);
		// 当年の有給休暇付与日を取得(付与日が含まれる年度の付与日)
		Date thisAcquisitionDate = paidHolidayDataGrant.getGrantDate(personalId, grtantTimes);
		// 当年の有給休暇付与日を取得できなかった場合
		if (MospUtility.isEmpty(thisAcquisitionDate)) {
			// 空の情報群を取得
			return Collections.emptySet();
		}
		// 当年の有給休暇付与日の前日を取得
		Date beforeAcquisitionDate = DateUtility.addDay(thisAcquisitionDate, -1);
		// 当年の有給休暇付与日の前日以前に付与されて期限が切れていない有給休暇付与情報(前年度分の付与情報)を取得
		List<PaidHolidayDataDtoInterface> dtos = paidHolidayDataRefer.getPaidHolidayDatas(personalId, targetDate,
				beforeAcquisitionDate, thisAcquisitionDate);
		// 有給休暇残情報(残日数と残時間数)群を取得(申請は当年の有給休暇付与日の前日までを考慮)
		return getPaidHolidayRemains(dtos, targetDate, statuses, targetDate, beforeAcquisitionDate);
	}
	
	/**
	 * 有給休暇残情報(残日数と残時間数)群を取得する。<br>
	 * @param dtos              有給休暇付与情報群
	 * @param targetDate        対象日(設定適用情報や有給休暇設定情報等の取得に用いる)
	 * @param statuses          承認状況群(対象となる休暇申請の承認状況)
	 * @param manualTargetDate  手動付与対象日(有給休暇手動付与情報等の有効日の上限に用いる)
	 * @param requestTargetDate 休暇申請対象日(休暇申請情報の申請開始日の上限に用いる)
	 * @return 休暇残情報(残日数と残時間数)群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<HolidayRemainDto> getPaidHolidayRemains(Collection<PaidHolidayDataDtoInterface> dtos, Date targetDate,
			Set<String> statuses, Date manualTargetDate, Date requestTargetDate) throws MospException {
		// 休暇残情報(残日数と残時間数)群を準備
		Set<HolidayRemainDto> remains = new LinkedHashSet<HolidayRemainDto>();
		// 有給休暇付与情報毎に処理
		for (PaidHolidayDataDtoInterface dto : dtos) {
			// 休暇残情報を取得し休暇残情報(残日数と残時間数)群に設定
			remains.add(getPaidHolidayRemain(dto, targetDate, statuses, manualTargetDate, requestTargetDate));
		}
		// 休暇残情報(残日数と残時間数)群を取得
		return remains;
	}
	
	/**
	 * 有給休暇残情報(残日数と残時間数)を取得する。<br>
	 * @param dto               有給休暇付与情報
	 * @param targetDate        対象日(設定適用情報や有給休暇設定情報等の取得に用いる)
	 * @param statuses          承認状況群(対象となる休暇申請の承認状況)
	 * @param manualTargetDate  手動付与対象日(有給休暇手動付与情報等の有効日の上限に用いる)
	 * @param requestTargetDate 休暇申請対象日(休暇申請情報の申請開始日の上限に用いる)
	 * @return 休暇残情報(残日数と残時間数)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected HolidayRemainDto getPaidHolidayRemain(PaidHolidayDataDtoInterface dto, Date targetDate,
			Set<String> statuses, Date manualTargetDate, Date requestTargetDate) throws MospException {
		// 個人ID及び付与日を取得
		String personalId = dto.getPersonalId();
		Date acquisitionDate = dto.getAcquisitionDate();
		//  1日の時間数を取得
		int hoursPerDay = timeMaster.getPaidHolidayHoursPerDay(personalId, targetDate);
		// 有給休暇手動付与情報群を取得
		Collection<PaidHolidayTransactionDtoInterface> transactions = getPaidHolidayTransactions(personalId,
				acquisitionDate, manualTargetDate);
		// 有給休暇手動付与日数及び時間数を取得
		double manualDays = TimeUtility.getPaidHolidayManualDays(transactions);
		int manualHours = TimeUtility.getPaidHolidayManualHours(transactions);
		// 休暇取得日で休暇申請情報(有給休暇)群(申請開始日が対象日以前)を取得
		Set<HolidayRequestDtoInterface> requests = getHolidayRequests(personalId, TimeConst.CODE_HOLIDAYTYPE2_PAID,
				acquisitionDate, statuses, requestTargetDate);
		// 休暇使用日数及び時間数を取得
		double useDays = TimeUtility.getHolidayUseDays(requests);
		int useHours = TimeUtility.getHolidayUseHours(requests);
		// 休暇残情報(残日数と残時間数)を取得
		return TimeUtility.getPaidHolidayRemain(dto, manualDays, manualHours, useDays, useHours, hoursPerDay);
	}
	
	/**
	 * 有給休暇手動付与情報群を取得する。<br>
	 * @param personalId      個人ID
	 * @param acquisitionDate 付与日
	 * @param targetDate      対象日(有効日が対象日以前の有給休暇手動付与)
	 * @return 有給休暇手動付与情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Collection<PaidHolidayTransactionDtoInterface> getPaidHolidayTransactions(String personalId,
			Date acquisitionDate, Date targetDate) throws MospException {
		// 有給休暇手動付与情報群を取得
		return paidHolidayTransactionRefer.findForList(personalId, acquisitionDate, null, targetDate);
	}
	
	/**
	 * 休暇取得日で休暇休暇申請情報群(申請開始日が対象日以前)を取得する。<br>
	 * @param personalId      個人ID
	 * @param holidayType2    休暇種別2
	 * @param acquisitionDate 休暇付与日
	 * @param statuses        承認状況群(対象となる休暇申請の承認状況)
	 * @param targetDate      対象日(申請開始日が対象日以前の休暇申請情報を対象とする)
	 * @return 休暇申請情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<HolidayRequestDtoInterface> getHolidayRequests(String personalId, String holidayType2,
			Date acquisitionDate, Set<String> statuses, Date targetDate) throws MospException {
		// 休暇取得日で休暇申請情報(有給休暇)群(申請開始日が対象日以前)を取得
		return holidayRequestRefer.getRequestsForAcquisitionDate(personalId, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY,
				holidayType2, acquisitionDate, statuses, targetDate);
	}
	
	@Override
	public List<HolidayRemainDto> getStockHolidayRemainsForRequest(String personalId, Date targetDate)
			throws MospException {
		// ストック休暇残情報リストを準備
		List<HolidayRemainDto> remains = new ArrayList<HolidayRemainDto>();
		//承認状況群(申請済承認状況群(一次戻と下書と取下以外))を準備
		Set<String> statuses = WorkflowUtility.getAppliedStatuses();
		// 対象日でストック休暇情報リスト(取得日昇順)を取得
		List<StockHolidayDataDtoInterface> dtos = stockHolidayDataRefer.getStockHolidayDataForDate(personalId,
				targetDate);
		// ストック休暇情報毎に処理
		for (StockHolidayDataDtoInterface dto : dtos) {
			// ストック休暇残情報(休暇申請対象日指定無し：ストック休暇情報に対する全ての申請を対象)を取得し設定
			remains.add(getStockHolidayRemain(dto, targetDate, statuses, targetDate, null));
		}
		// ストック休暇残情報リストを取得
		return remains;
	}
	
	@Override
	public double getStockHolidayRemainDaysForView(String personalId, Date targetDate) throws MospException {
		// ストック休暇残日数を準備
		double remainDays = 0D;
		// 対象日でストック休暇残情報リスト(取得日昇順)を取得
		List<HolidayRemainDto> remains = getStockHolidayRemainsForRequest(personalId, targetDate);
		// 休暇残情報毎に処理
		for (HolidayRemainDto remain : remains) {
			// ストック休暇残日数を設定
			remainDays += remain.getRemainDays();
		}
		// ストック休暇残日数を取得
		return remainDays;
	}
	
	@Override
	public double getStockHolidayRemainDaysForList(String personalId, Date targetDate) throws MospException {
		// ストック休暇残日数を準備
		double remainDays = 0D;
		//承認状況群(申請済承認状況群(一次戻と下書と取下以外))を準備
		Set<String> statuses = WorkflowUtility.getAppliedStatuses();
		// 対象日でストック休暇情報リスト(取得日昇順)を取得
		List<StockHolidayDataDtoInterface> dtos = stockHolidayDataRefer.getStockHolidayDataForDate(personalId,
				targetDate);
		// ストック休暇情報毎に処理
		for (StockHolidayDataDtoInterface dto : dtos) {
			// ストック休暇残情報を取得し設定
			remainDays += getStockHolidayRemain(dto, targetDate, statuses, targetDate, targetDate).getRemainDays();
		}
		// ストック休暇残日数を取得
		return remainDays;
	}
	
	@Override
	public HolidayRemainDto getStockHolidayRemainForYear(String personalId, Date targetDate, int targetYear)
			throws MospException {
		// 年度の初日及び最終日を取得
		Date firstDate = MonthUtility.getFiscalYearFirstDate(targetYear, mospParams);
		Date lastDate = MonthUtility.getFiscalYearLastDate(targetYear, mospParams);
		//承認状況群(有効な承認状況群(下書と取下以外))を準備
		Set<String> statuses = WorkflowUtility.getEffectiveStatuses();
		// 有給休暇残情報を準備
		HolidayRemainDto remain = TimeUtility.getBareStockHolidayRemain(firstDate, targetDate);
		// 対象となるストック休暇情報リスト(取得日が対象日以前で期限日が対象期間初日以降)を取得
		List<StockHolidayDataDtoInterface> dtos = stockHolidayDataRefer.getStockHolidayDatas(personalId, targetDate,
				targetDate, firstDate);
		// ストック休暇情報毎に処理
		for (StockHolidayDataDtoInterface dto : dtos) {
			// ストック休暇残情報に値(付与日数と付与時間数と残日数と残時間数)を設定
			setStockHolidayRemain(remain, dto, targetDate, firstDate, lastDate, statuses);
		}
		// 有給休暇残情報を取得
		return remain;
	}
	
	/**
	 * ストック休暇残情報に値を設定する。<br>
	 * 既に値が設定されている場合は、その値に足し引きした値を設定する。<br>
	 * <br>
	 * 休暇残情報の
	 * 付与日数に対象期間初日時点における残日数を、
	 * 残日数に対象日における残日数を、設定する。<br>
	 * 但し、残日数の計算に用いる申請は対象期間最終日までを対象とする。<br>
	 * <br>
	 * 対象となるストック休暇トランザクション(手動付与)情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.対象となる承認状況であるストック休暇申請<br>
	 * 2.休暇取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 3.申請日が対象期間最終日以前<br>
	 * <br>
	 * @param remain     休暇残情報
	 * @param dto        ストック休暇情報
	 * @param targetDate 対象日
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @param statuses   承認状況
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setStockHolidayRemain(HolidayRemainDto remain, StockHolidayDataDtoInterface dto, Date targetDate,
			Date firstDate, Date lastDate, Set<String> statuses) throws MospException {
		// 対象日におけるストック休暇残情報を取得(休暇申請は対象期間最終日まで考慮)
		HolidayRemainDto remainForTargetDate = getStockHolidayRemain(dto, targetDate, statuses, targetDate, lastDate);
		// 休暇残情報に対象日における残日数を設定
		remain.setRemainDays(remain.getRemainDays() + remainForTargetDate.getRemainDays());
		// 対象期間初日より後に取得した有給休暇である場合
		if (dto.getAcquisitionDate().after(firstDate)) {
			// 処理終了(対象期間初日時点における残日数(及び残時間数)の計算対象外)
			return;
		}
		// 対象期間初日の1日前を取得
		Date beforeDate = DateUtility.addDay(firstDate, -1);
		// 対象期間初日時点におけるストック休暇残情報を取得
		HolidayRemainDto remainForFirstDate = getStockHolidayRemain(dto, targetDate, statuses, firstDate, beforeDate);
		// 休暇残情報に対象期間初日時点における残日数を設定
		remain.setGivenDays(remain.getGivenDays() + remainForFirstDate.getRemainDays());
	}
	
	/**
	 * ストック休暇残情報(残日数)を取得する。<br>
	 * @param dto               ストック休暇付与情報
	 * @param targetDate        対象日(設定適用情報や有給休暇設定情報等の取得に用いる)
	 * @param statuses          承認状況群(対象となる休暇申請の承認状況)
	 * @param manualTargetDate  手動付与対象日(有給休暇手動付与情報等の有効日の上限に用いる)
	 * @param requestTargetDate 休暇申請対象日(休暇申請情報の申請開始日の上限に用いる)
	 * @return 休暇残情報(残日数と残時間数)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected HolidayRemainDto getStockHolidayRemain(StockHolidayDataDtoInterface dto, Date targetDate,
			Set<String> statuses, Date manualTargetDate, Date requestTargetDate) throws MospException {
		// 個人ID及び付与日を取得
		String personalId = dto.getPersonalId();
		Date acquisitionDate = dto.getAcquisitionDate();
		// ストック休暇手動付与情報群を取得
		Collection<StockHolidayTransactionDtoInterface> transactions = getStockHolidayTransactions(personalId,
				acquisitionDate, manualTargetDate);
		// ストック休暇手動付与日数及び時間数を取得
		double manualDays = TimeUtility.getStockHolidayManualDays(transactions);
		// 休暇取得日で休暇申請情報(ストック休暇)群(申請開始日が対象日以前)を取得
		Set<HolidayRequestDtoInterface> requests = getHolidayRequests(personalId, TimeConst.CODE_HOLIDAYTYPE2_STOCK,
				acquisitionDate, statuses, requestTargetDate);
		// 休暇使用日数及び時間数を取得
		double useDays = TimeUtility.getHolidayUseDays(requests);
		// 休暇残情報(残日数と残時間数)を取得
		return TimeUtility.getStockHolidayRemain(dto, manualDays, useDays);
	}
	
	/**
	 * ストック休暇手動付与情報群を取得する。<br>
	 * @param personalId      個人ID
	 * @param acquisitionDate 付与日
	 * @param targetDate      対象日(有効日が対象日以前の有給休暇手動付与)
	 * @return 有給休暇手動付与情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Collection<StockHolidayTransactionDtoInterface> getStockHolidayTransactions(String personalId,
			Date acquisitionDate, Date targetDate) throws MospException {
		// 有給休暇手動付与情報群を取得
		return stockHolidayTransactionRefer.findForList(personalId, acquisitionDate, null, targetDate);
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
	}
	
}
