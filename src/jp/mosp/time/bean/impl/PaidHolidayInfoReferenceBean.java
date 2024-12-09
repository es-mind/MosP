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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.PaidHolidayEntranceDateDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayPointDateDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayTransactionDaoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;
import jp.mosp.time.entity.HolidayRequestEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 有給休暇情報参照処理。<br>
 */
public class PaidHolidayInfoReferenceBean extends TimeApplicationBean implements PaidHolidayInfoReferenceBeanInterface {
	
	/**
	 * 有給休暇トランザクションDAO。
	 */
	protected PaidHolidayTransactionDaoInterface			paidHolidayTransactionDao;
	
	/**
	 * 休暇申請参照。
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequest;
	
	/**
	 * 有給休暇基準日管理DAO。
	 */
	protected PaidHolidayPointDateDaoInterface				paidHolidayPointDao;
	
	/**
	 * 有給休暇入社日管理DAO。
	 */
	protected PaidHolidayEntranceDateDaoInterface			paidHolidayEntranceDateDao;
	
	/**
	 * 有給休暇初年度付与参照クラス。
	 */
	protected PaidHolidayFirstYearReferenceBeanInterface	paidHolidayFirstYearReference;
	
	/**
	 * 人事入社情報参照クラス。
	 */
	protected EntranceReferenceBeanInterface				entranceRefer;
	
	/**
	 * 有給休暇データ付与クラス。
	 */
	protected PaidHolidayDataGrantBeanInterface				paidHolidayDataGrant;
	
	/**
	 * 有給休暇残数取得処理。<br>
	 */
	protected PaidHolidayRemainBeanInterface				paidHolidayRemain;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	/**
	 * 入社日
	 */
	protected Date											entranceDate;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayInfoReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元の処理を実施
		super.initBean();
		// DAOを準備
		paidHolidayTransactionDao = createDaoInstance(PaidHolidayTransactionDaoInterface.class);
		paidHolidayPointDao = createDaoInstance(PaidHolidayPointDateDaoInterface.class);
		paidHolidayEntranceDateDao = createDaoInstance(PaidHolidayEntranceDateDaoInterface.class);
		// Beanを準備
		paidHolidayFirstYearReference = createBeanInstance(PaidHolidayFirstYearReferenceBeanInterface.class);
		entranceRefer = createBeanInstance(EntranceReferenceBeanInterface.class);
		paidHolidayDataGrant = createBeanInstance(PaidHolidayDataGrantBeanInterface.class);
		holidayRequest = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		paidHolidayRemain = createBeanInstance(PaidHolidayRemainBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// 勤怠関連マスタ参照処理をBeanに設定
		paidHolidayRemain.setTimeMaster(timeMaster);
	}
	
	@Override
	public Map<String, Object> getPaidHolidayInfo(String personalId, Date targetDate) throws MospException {
		// 表示用有給休暇情報を取得(期間開始日：デフォルト日付)
		return getPaidHolidayInfo(personalId, targetDate, DateUtility.getDefaultTime(), targetDate);
	}
	
	@Override
	public Map<String, Object> getPaidHolidayReferenceInfo(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// 表示用有給休暇情報を取得(対象日：期間最終日)
		return getPaidHolidayInfo(personalId, lastDate, firstDate, lastDate);
	}
	
	/**
	 * 表示用有給休暇情報を取得する。<br>
	 * <br>
	 * 表示用有給休暇情報には、次の項目が含まれる。<br>
	 * ・前年度残日数：対象日における有給休暇残情報から取得する。<br>
	 * ・前年度残時間：対象日における有給休暇残情報から取得する。<br>
	 * ・今年度残日数：対象日における有給休暇残情報から取得する。<br>
	 * ・今年度残時間：対象日における有給休暇残情報から取得する。<br>
	 * ・支給日数　　：対象期間に手動付与された有給休暇手動付与情報から算出する。<br>
	 * ・支給時間　　：対象期間に手動付与された有給休暇手動付与情報から算出する。<br>
	 * ・廃棄日数　　：対象期間に手動付与された有給休暇手動付与情報から算出する。<br>
	 * ・廃棄時間　　：対象期間に手動付与された有給休暇手動付与情報から算出する。<br>
	 * ・利用日数　　：対象期間に申請された休暇申請情報から算出する。<br>
	 * ・利用時間　　：対象期間に申請された休暇申請情報から算出する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param firstDate  期間開始日
	 * @param lastDate   期間最終日
	 * @return 表示用有給休暇情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, Object> getPaidHolidayInfo(String personalId, Date targetDate, Date firstDate, Date lastDate)
			throws MospException {
		// 表示用有給休暇情報を準備
		Map<String, Object> map = new HashMap<String, Object>();
		// 前年度残日数を準備
		double previousRemainDays = 0D;
		// 前年度残時間を準備
		int previousRemainHours = 0;
		// 今年度残日数を準備
		double currentRemainDays = 0D;
		// 今年度残時間を準備
		int currentRemainHours = 0;
		// 支給日数を準備
		double givingDays = 0D;
		// 支給時間を準備
		int givingHours = 0;
		// 廃棄日数を準備
		double cancelDays = 0D;
		// 廃棄時間を準備
		int cancelHours = 0;
		// 利用日数
		double useDays = 0D;
		// 利用時間
		int useHours = 0;
		// 表示用有給休暇情報に初期値を設定
		map.put(TimeConst.CODE_FORMER_YEAR_DAY, previousRemainDays);
		map.put(TimeConst.CODE_FORMER_YEAR_TIME, previousRemainHours);
		map.put(TimeConst.CODE_CURRENT_YEAR_DAY, currentRemainDays);
		map.put(TimeConst.CODE_CURRENT_TIME, currentRemainHours);
		map.put(TimeConst.CODE_GIVING_DAY, givingDays);
		map.put(TimeConst.CODE_GIVING_TIME, givingHours);
		map.put(TimeConst.CODE_CANCEL_DAY, cancelDays);
		map.put(TimeConst.CODE_CANCEL_TIME, cancelHours);
		map.put(TimeConst.CODE_USE_DAY, useDays);
		map.put(TimeConst.CODE_USE_TIME, useHours);
		// 有給休暇設定情報を取得できなかった場合
		if (MospUtility.isEmpty(timeMaster.getPaidHolidayForPersonalId(personalId, targetDate).orElse(null))) {
			// 表示用有給休暇情報を取得
			return map;
		}
		// 対象日における有給休暇付与回数を取得
		int grantTimes = paidHolidayDataGrant.getGrantTimes(personalId, targetDate);
		// 今年度の有給休暇付与日を取得
		Date currentAcquisitionDate = paidHolidayDataGrant.getGrantDate(personalId, targetDate, grantTimes);
		// 今年度の有給休暇付与日を取得できなかった場合
		if (MospUtility.isEmpty(currentAcquisitionDate)) {
			// 処理終了
			return map;
		}
		// 対象日で有給休暇残情報リスト(取得日昇順)を取得
		List<HolidayRemainDto> remains = paidHolidayRemain.getPaidHolidayRemainsForList(personalId, targetDate);
		// 休暇残情報毎に処理
		for (HolidayRemainDto remain : remains) {
			// 取得日を取得
			Date acquisitionDate = remain.getAcquisitionDate();
			// 期間(付与日～対象日)で有給休暇手動付与情報リストを取得
			List<PaidHolidayTransactionDtoInterface> paidHolidayTransactions = paidHolidayTransactionDao
				.findForList(personalId, acquisitionDate, firstDate, lastDate);
			// 支給日数と支給時間と廃棄日数と廃棄時間を設定
			givingDays += TimeUtility.getPaidHolidayManualGivingDays(paidHolidayTransactions);
			givingHours += TimeUtility.getPaidHolidayManualGivingHours(paidHolidayTransactions);
			cancelDays += TimeUtility.getPaidHolidayManualCancelDays(paidHolidayTransactions);
			cancelHours += TimeUtility.getPaidHolidayManualCancelHours(paidHolidayTransactions);
			// 期間(付与日～対象日)で休暇取得日数及び時間数を取得
			Map<String, Object> requestMap = holidayRequest.getRequestDayHour(personalId, acquisitionDate,
					TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_PAID, firstDate, lastDate);
			// 利用日数と利用時間数を設定
			useDays += MospUtility.getDouble(requestMap.get(TimeConst.CODE_REQUEST_DAY));
			useHours += MospUtility.getInt(requestMap.get(TimeConst.CODE_REQUEST_HOUR));
			// 取得日が今年度の有給休暇付与日よりも前である場合
			if (acquisitionDate.before(currentAcquisitionDate)) {
				// 前年度残日数と前年度残時間を設定
				previousRemainDays += remain.getRemainDays();
				previousRemainHours += remain.getRemainHours();
				// 次の休暇残情報へ
				continue;
			}
			// 今年度残日数と今年度残時間を設定
			currentRemainDays += remain.getRemainDays();
			currentRemainHours += remain.getRemainHours();
		}
		// 表示用有給休暇情報に値を設定
		map.put(TimeConst.CODE_FORMER_YEAR_DAY, previousRemainDays);
		map.put(TimeConst.CODE_FORMER_YEAR_TIME, previousRemainHours);
		map.put(TimeConst.CODE_CURRENT_YEAR_DAY, currentRemainDays);
		map.put(TimeConst.CODE_CURRENT_TIME, currentRemainHours);
		map.put(TimeConst.CODE_GIVING_DAY, givingDays);
		map.put(TimeConst.CODE_GIVING_TIME, givingHours);
		map.put(TimeConst.CODE_CANCEL_DAY, cancelDays);
		map.put(TimeConst.CODE_CANCEL_TIME, cancelHours);
		map.put(TimeConst.CODE_USE_DAY, useDays);
		map.put(TimeConst.CODE_USE_TIME, useHours);
		// 表示用有給休暇情報を取得
		return map;
	}
	
	@Override
	public List<Map<String, Object>> getPaidHolidayDataListForView(String personalId, Date targetDate)
			throws MospException {
		// 表示用有給休暇情報リストを準備
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 対象日における有給休暇付与回数を取得
		int grantTimes = paidHolidayDataGrant.getGrantTimes(personalId, targetDate);
		// 初年度(1回目)の有給休暇付与日を取得
		Date firstGrant = paidHolidayDataGrant.getGrantDate(personalId, targetDate, 1);
		// 前年度の有給休暇付与日を準備
		Date previousGrant = null;
		// 有給休暇付与回数が2回または2回以上の場合
		if (grantTimes >= 2) {
			// 前回の有給休暇付与日取得
			previousGrant = paidHolidayDataGrant.getGrantDate(personalId, targetDate, grantTimes - 1);
		}
		// 今年度の有給休暇付与日を取得
		Date currentGrant = paidHolidayDataGrant.getGrantDate(personalId, targetDate, grantTimes);
		// 集計用の変数を準備
		double totalGrantDays = 0;
		int totalGrantHours = 0;
		double totalRemainDays = 0;
		int totalRemainHours = 0;
		// 対象日で有給休暇残情報リスト(取得日昇順)を取得
		List<HolidayRemainDto> dtos = paidHolidayRemain.getPaidHolidayRemainsForView(personalId, targetDate);
		// 休暇残情報毎に処理
		for (HolidayRemainDto dto : dtos) {
			// 取得日を取得
			Date acquisitionDate = dto.getAcquisitionDate();
			// 年度文字列を取得
			String fiscalYearString = getFiscalYearString(acquisitionDate, firstGrant, previousGrant, currentGrant);
			// 表示用有給休暇情報を作成
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TimeConst.CODE_PAID_LEAVE_FISCAL_YEAR, fiscalYearString);
			map.put(TimeConst.CODE_PAID_LEAVE_GRANT_DATE, acquisitionDate);
			map.put(TimeConst.CODE_PAID_LEAVE_EXPIRATION_DATE, dto.getHolidayLimitDate());
			map.put(TimeConst.CODE_PAID_LEAVE_GRANT_DAYS, dto.getGivenDays());
			map.put(TimeConst.CODE_PAID_LEAVE_GRANT_HOURS, dto.getGivenHours());
			map.put(TimeConst.CODE_PAID_LEAVE_REMAIN_DAYS, dto.getRemainDays());
			map.put(TimeConst.CODE_PAID_LEAVE_REMAIN_HOURS, dto.getRemainHours());
			// 表示用有給休暇情報リストに設定
			list.add(map);
			// 取得日が対象日よりも後である場合
			if (acquisitionDate.after(targetDate)) {
				// 次の休暇残情報へ(集計不要)
				continue;
			}
			// 集計
			totalGrantDays += dto.getGivenDays();
			totalGrantHours += dto.getGivenHours();
			totalRemainDays += dto.getRemainDays();
			totalRemainHours += dto.getRemainHours();
		}
		// 表示用有給休暇情報(現在の残日数合計)を作成
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put(TimeConst.CODE_PAID_LEAVE_FISCAL_YEAR, TimeNamingUtility.currentTotalRemainDays(mospParams));
		totalMap.put(TimeConst.CODE_PAID_LEAVE_GRANT_DAYS, totalGrantDays);
		totalMap.put(TimeConst.CODE_PAID_LEAVE_GRANT_HOURS, totalGrantHours);
		totalMap.put(TimeConst.CODE_PAID_LEAVE_REMAIN_DAYS, totalRemainDays);
		totalMap.put(TimeConst.CODE_PAID_LEAVE_REMAIN_HOURS, totalRemainHours);
		// 表示用有給休暇情報リストに設定
		list.add(totalMap);
		// 表示用有給休暇情報リストを取得
		return list;
	}
	
	/**
	 * 年度文字列を取得する。<br>
	 * @param acquisitionDate   取得日
	 * @param firstGrantDate    初年度(1回目)の有給休暇付与日
	 * @param previousGrantDate 前年度の有給休暇付与日
	 * @param currentGrantDate  今年度の有給休暇付与日
	 * @return 年度文字列(初年度か前年度か今年度か空白)
	 */
	protected String getFiscalYearString(Date acquisitionDate, Date firstGrantDate, Date previousGrantDate,
			Date currentGrantDate) {
		// 年度文字列を準備
		String fiscalYear = MospConst.STR_EMPTY;
		// 有給休暇情報の取得日が初年度の有給休暇付与日と同じ場合
		if (DateUtility.isSame(acquisitionDate, firstGrantDate)) {
			// 年度文字列(初年度)を設定
			fiscalYear = TimeNamingUtility.firstFiscalYear(mospParams);
		}
		// 有給休暇情報の取得日が前年度の有給休暇付与日と同じ場合
		if (DateUtility.isSame(acquisitionDate, previousGrantDate)) {
			// 年度文字列(前年度)を設定
			fiscalYear = TimeNamingUtility.previousFiscalYear(mospParams);
		}
		// 有給休暇情報の取得日が今年度の有給休暇付与日と同じ場合
		if (DateUtility.isSame(acquisitionDate, currentGrantDate)) {
			// 年度文字列(今年度)を設定
			fiscalYear = TimeNamingUtility.thisFiscalYear(mospParams);
		}
		// 年度文字列を取得
		return fiscalYear;
	}
	
	/**
	 * 基準日区分の際、入社日や有給休暇初年度情報を取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇初年度情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected PaidHolidayFirstYearDtoInterface getPaidHolidayFirstYearDto(String personalId, Date targetDate)
			throws MospException {
		// 入社日取得及び確認
		entranceDate = entranceRefer.getEntranceDate(personalId);
		if (entranceDate == null) {
			// 該当する入社日が存在しない
			PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
			return null;
		}
		// 対象個人ID及び対象日付で有給休暇設定情報を取得し設定
		if (hasPaidHolidaySettings(personalId, targetDate) == false) {
			// 有給休暇情報が取得できない場合
			return null;
		}
		// 初年度付与情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = paidHolidayFirstYearReference.findForKey(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(),
				DateUtility.getMonth(entranceDate));
		return paidHolidayFirstYearDto;
	}
	
	@Override
	public Map<String, Object> getNextGivingInfo(String personalId) throws MospException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, null);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, 0.0);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, null);
		// 入社日や有給休暇初年度情報を取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = getPaidHolidayFirstYearDto(personalId,
				getSystemDate());
		// 入社日が存在しない場合
		if (entranceDate == null) {
			return null;
		}
		// 有給休暇情報がない場合
		if (paidHolidayDto == null) {
			return null;
		}
		// 付与区分取得
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		// 付与区分確認
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日次回付与予定取得
			return getStandardDay(map, paidHolidayFirstYearDto, personalId, entranceDate);
		}
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 処理無し
			return null;
		}
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日次回付与予定取得
			return getEntranceDay(map, paidHolidayFirstYearDto, personalId);
		}
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY) {
			// 比例付与次回付与予定取得
			return getProportionallyDay(map, personalId, getSystemDate());
		}
		return map;
	}
	
	/**
	 * [基準日]初年度付与日を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 初年度付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getFirstYearGivingDate(String personalId, Date targetDate) throws MospException {
		// 有給休暇初年度情報取得
		PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto = getPaidHolidayFirstYearDto(personalId, targetDate);
		// 初年度付与日取得(入社月の基準日から付与月を加算)
		return getFirstYearGivingDate(paidHolidayFirstYearDto, targetDate);
	}
	
	/**
	 * 初年度付与日を取得する。<br>
	 * @param dto 有給休暇初年度DTO
	 * @param date 入社日
	 * @return 初年度付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getFirstYearGivingDate(PaidHolidayFirstYearDtoInterface dto, Date date) throws MospException {
		// 有給休暇初年度情報がない場合
		if (dto == null) {
			return null;
		}
		if (dto.getGivingAmount() <= 0) {
			// 付与日数が0以下の場合
			return null;
		}
		// 初年度付与日取得(入社月の基準日から付与月を加算)
		return addDay(
				DateUtility.addMonth(MonthUtility.getTargetYearMonth(entranceDate, mospParams), dto.getGivingMonth()),
				paidHolidayDto.getPointDateDay() - 1);
	}
	
	/**
	 * 基準日による次回付与予定を取得する。<br>
	 * 休暇申請画面の"次回付与予定"項目に使用する。<br>
	 * @param map マップ
	 * @param paidHolidayFirstYearDto 有給休暇初年度情報
	 * @param personalId 個人ID
	 * @param entranceDate 入社日
	 * @return マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, Object> getStandardDay(Map<String, Object> map,
			PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto, String personalId, Date entranceDate)
			throws MospException {
		// 初年度付与日準備
		Date firstYearGivingDate = getFirstYearGivingDate(personalId, getSystemDate());
		// 初年度付与日確認
		if (firstYearGivingDate != null && getSystemDate().before(firstYearGivingDate)) {
			// 初年度の場合
			map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, firstYearGivingDate);
			map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, (double)paidHolidayFirstYearDto.getGivingAmount());
			map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
			map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE,
					addDay(DateUtility.addMonth(firstYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1));
			return map;
		}
		// 次年度付与日
		Date nextYearGivingDate = null;
		// 次年度付与数
		double nextYearDay = -1;
		// 次年度期限日
		Date nextYearLimitDate = null;
		// 基準日
		int pointMonth = paidHolidayDto.getPointDateMonth();
		int pointDay = paidHolidayDto.getPointDateDay();
		// 基準日準備
		Date pointDate = DateUtility.getDate(DateUtility.getYear(entranceDate), pointMonth, pointDay);
		// 入社日が基準日より前でない場合
		if (!entranceDate.before(pointDate)) {
			// 基準日に1年を加算する
			pointDate = DateUtility.addYear(pointDate, 1);
		}
		// 初年度付与日確認
		if (firstYearGivingDate != null && !firstYearGivingDate.before(pointDate)) {
			// 初年度付与日が基準日より前でない場合は1年を加算する
			pointDate = DateUtility.addYear(pointDate, 1);
		}
		// 基準日経過回数準備
		int count = 2;
		// 基準日経過回数設定
		while (!pointDate.after(getSystemDate())) {
			// 基準日が締日より後でない場合は1年加算
			pointDate = DateUtility.addYear(pointDate, 1);
			count++;
		}
		nextYearGivingDate = pointDate;
		// 有休繰越取得
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
			// 有休繰越が有効の場合は期限は2年
			nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, 2), -1);
		} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
			// 有休繰越が無効の場合は期限は1年
			nextYearLimitDate = addDay(DateUtility.addYear(nextYearGivingDate, 1), -1);
		}
		// 基準日経過回数から有給休暇基準日管理情報を取得
		PaidHolidayPointDateDtoInterface paidHolidayPointDateDto = paidHolidayPointDao
			.findForKey(paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(), count);
		if (paidHolidayPointDateDto == null) {
			// 登録情報超過後
			nextYearDay = paidHolidayDto.getGeneralPointAmount();
		} else {
			nextYearDay = paidHolidayPointDateDto.getPointDateAmount();
		}
		// マップに設定
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, nextYearGivingDate);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, nextYearDay);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, nextYearLimitDate);
		return map;
	}
	
	/**
	 * 入社日による次回付与予定を取得する。<br>
	 * 休暇申請画面の"次回付与予定"項目に使用する。<br>
	 * @param map マップ
	 * @param paidHolidayFirstYearDto 有給休暇初年度情報
	 * @param personalId 個人ID
	 * @return マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, Object> getEntranceDay(Map<String, Object> map,
			PaidHolidayFirstYearDtoInterface paidHolidayFirstYearDto, String personalId) throws MospException {
		// 次年度付与日
		Date nextYearGivingDate = null;
		// 次年度付与数
		double nextYearDay = -1;
		// 次年度期限日
		Date nextYearLimitDate = null;
		// 入社日
		// 初年度付与日準備
		Date firstYearGivingDate = null;
		if (paidHolidayFirstYearDto != null) {
			// 初年度付与マスタが存在する場合
			int amount = paidHolidayFirstYearDto.getGivingAmount();
			if (amount > 0) {
				// 初年度付与マスタが存在し且つ付与日数が0より大きい場合
				// 初年度付与日取得(入社日から付与月を加算)
				firstYearGivingDate = DateUtility.addMonth(entranceDate, paidHolidayFirstYearDto.getGivingMonth());
				// 初年度付与日確認
				if (getSystemDate().before(firstYearGivingDate)) {
					nextYearGivingDate = firstYearGivingDate;
					// 初年度の利用期限
					nextYearLimitDate = addDay(
							DateUtility.addMonth(nextYearGivingDate, paidHolidayFirstYearDto.getGivingLimit()), -1);
					nextYearDay = amount;
				}
			}
		}
		Date givingDate = null;
		int amount = 0;
		Date maxDate = entranceDate;
		int maxCarryOverYear = paidHolidayDto.getMaxCarryOverYear();
		List<PaidHolidayEntranceDateDtoInterface> list = paidHolidayEntranceDateDao
			.findForList(paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate());
		for (PaidHolidayEntranceDateDtoInterface paidHolidayEntranceDateDto : list) {
			Date workDate = DateUtility.addMonth(entranceDate, paidHolidayEntranceDateDto.getWorkMonth());
			if (firstYearGivingDate != null && !firstYearGivingDate.before(workDate)) {
				continue;
			}
			if (maxDate.before(workDate)) {
				maxDate = workDate;
			}
			if (workDate.after(getSystemDate()) && (givingDate == null || givingDate.after(workDate))) {
				givingDate = workDate;
				amount = paidHolidayEntranceDateDto.getJoiningDateAmount();
			}
		}
		if (givingDate == null) {
			// 登録情報最大まで経過後
			int generalJoiningMonth = paidHolidayDto.getGeneralJoiningMonth();
			if (generalJoiningMonth == 0) {
				return null;
			}
			if (nextYearDay == -1) {
				nextYearDay = paidHolidayDto.getGeneralJoiningAmount();
			}
			while (!maxDate.after(getSystemDate())) {
				maxDate = DateUtility.addMonth(maxDate, generalJoiningMonth);
			}
			if (nextYearGivingDate == null) {
				nextYearGivingDate = maxDate;
			}
			if (nextYearLimitDate == null) {
				if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
					// 有休繰越が有効の場合は期限は2年
					nextYearLimitDate = DateUtility.addDay(DateUtility.addYear(nextYearGivingDate, 2), -1);
				} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
					// 有休繰越が無効の場合は期限は1年
					nextYearLimitDate = DateUtility.addDay(DateUtility.addYear(nextYearGivingDate, 1), -1);
				}
			}
		}
		if (nextYearDay == -1) {
			nextYearDay = amount;
		}
		if (nextYearGivingDate == null) {
			nextYearGivingDate = givingDate;
		}
		if (nextYearLimitDate == null) {
			if (maxCarryOverYear == MospConst.DELETE_FLAG_OFF) {
				// 有休繰越が有効の場合は期限は2年
				nextYearLimitDate = DateUtility.addDay(DateUtility.addYear(nextYearGivingDate, 2), -1);
			} else if (maxCarryOverYear == MospConst.DELETE_FLAG_ON) {
				// 有休繰越が無効の場合は期限は1年
				nextYearLimitDate = DateUtility.addDay(DateUtility.addYear(nextYearGivingDate, 1), -1);
			}
		}
		// マップに設定
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, nextYearGivingDate);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, nextYearDay);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, nextYearLimitDate);
		return map;
	}
	
	/**
	 * 比例による次回付与予定を取得する。<br>
	 * 休暇申請画面の"次回付与予定"項目に使用する。<br>
	 * @param map マップ
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, Object> getProportionallyDay(Map<String, Object> map, String personalId, Date targetDate)
			throws MospException {
		// 有給休暇付与回数を取得
		int grantTimes = paidHolidayDataGrant.getGrantTimes(personalId, targetDate);
		// 次年度付与日取得
		Date nextYearGivingDate = paidHolidayDataGrant.getGrantDate(personalId, grantTimes + 1);
		// 次年度付与日数取得
		double nextYearDay = paidHolidayDataGrant.getGrantDaysForProportionally(personalId, nextYearGivingDate,
				grantTimes + 1);
		// 次年度期限日取得
		Date nextYearLimitDate = paidHolidayDataGrant.getExpirationDate(personalId, nextYearGivingDate, grantTimes + 1);
		// マップに設定
		map.put(TimeConst.CODE_NEXT_PLAN_GIVING_DATE, nextYearGivingDate);
		map.put(TimeConst.CODE_NEXT_PLAN_YEAR_DAY, nextYearDay);
		map.put(TimeConst.CODE_NEXT_PLAN_TIME, 0);
		map.put(TimeConst.CODE_NEXT_PLAN_LIMIT_DATE, nextYearLimitDate);
		return map;
	}
	
	@Override
	public Date getNextManualGivingDate(String personalId) throws MospException {
		// 個人ID及びシステム日付で有給休暇トランザクション情報リストを取得
		List<PaidHolidayTransactionDtoInterface> list = paidHolidayTransactionDao.findForNextGiving(personalId,
				getSystemDate());
		// リスト確認
		if (list.isEmpty()) {
			return null;
		}
		// 直近の有給休暇トランザクション情報から付与日を取得
		return list.get(0).getActivateDate();
	}
	
	@Override
	public String getNextManualGivingDaysAndHours(String personalId) throws MospException {
		// 次回付与予定日を取得
		Date activateDate = getNextManualGivingDate(personalId);
		if (activateDate == null) {
			return null;
		}
		// 直近の有給休暇トランザクション情報を取得
		List<PaidHolidayTransactionDtoInterface> list = paidHolidayTransactionDao.findForList(personalId, activateDate);
		// 日数及び時間を準備
		double givingDays = 0D;
		int givingHours = 0;
		// 日数及び時間を加算
		for (PaidHolidayTransactionDtoInterface dto : list) {
			givingDays += dto.getGivingDay();
			givingDays -= dto.getCancelDay();
			givingHours += dto.getGivingHour();
			givingHours -= dto.getCancelHour();
		}
		// 日数及び時間を文字列に変換
		StringBuilder sb = new StringBuilder();
		sb.append(TransStringUtility.getDoubleTimes(mospParams, givingDays, false, false));
		sb.append(PfNameUtility.day(mospParams));
		if (givingHours != 0) {
			sb.append(givingHours);
			sb.append(PfNameUtility.time(mospParams));
		}
		return sb.toString();
	}
	
	@Override
	public int[] getHolidayTimeUnitLimit(String personalId, Date targetDate, boolean isCompleted,
			HolidayRequestDtoInterface holidayRequestDto) throws MospException {
		// int配列準備
		int[] limitDayTime = { 0, 0, 0 };
		// 設定有給休暇情報を取得、設定
		if (hasPaidHolidaySettings(personalId, targetDate) == false) {
			// エラーメッセージ追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORKFORM_EXISTENCE,
					mospParams.getName("PaidVacation", "Set"));
			return limitDayTime;
		}
		// 今年度基準日取得
		Date firstDate = DateUtility.getDate(DateUtility.getYear(targetDate), paidHolidayDto.getPointDateMonth(),
				paidHolidayDto.getPointDateDay());
		// 対象日前の場合
		if (firstDate.after(targetDate)) {
			// 基準日-1年
			firstDate = DateUtility.addYear(firstDate, -1);
		}
		// 翌年基準日1日前を取得
		Date lastDate = addDay(DateUtility.addYear(firstDate, 1), -1);
		// 有休時間取得限度取得
		int limitDay = paidHolidayDto.getTimeAcquisitionLimitDays();
		int limitTime = paidHolidayDto.getTimeAcquisitionLimitTimes();
		// 時間休が有効でない場合
		if (paidHolidayDto.getTimelyPaidHolidayFlag() != 0) {
			return limitDayTime;
		}
		// 時間休限度準備
		int time = limitDay * limitTime;
		// 休暇申請エンティティを取得
		HolidayRequestEntityInterface entity = holidayRequest.getHolidayRequestEntity(personalId, firstDate, lastDate);
		// 休暇申請のレコード識別IDを取得
		long recordId = 0;
		if (holidayRequestDto != null) {
			recordId = holidayRequestDto.getRecordId();
		}
		// 該当申請以外の時間単位有給休暇時間数を取得
		int count = entity.countHourlyPaidHolidays(isCompleted, recordId);
		// 時間休限度から取得時間単位有給休暇時間数を減算
		time -= count;
		// 残時間を作成
		limitDayTime[0] = time / limitTime;
		limitDayTime[1] = time % limitTime;
		limitDayTime[2] = limitTime;
		// 残時間取得
		return limitDayTime;
	}
	
}
