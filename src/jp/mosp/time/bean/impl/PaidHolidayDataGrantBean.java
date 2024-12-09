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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayProportionallyReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayGrantDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayProportionallyDtoInterface;

/**
 * 有給休暇データ生成クラス。
 */
public class PaidHolidayDataGrantBean extends TimeApplicationBean implements PaidHolidayDataGrantBeanInterface {
	
	/**
	 * 有給休暇データ参照クラス。
	 */
	protected PaidHolidayDataReferenceBeanInterface				paidHolidayDataRefer;
	
	/**
	 * 有給休暇データ登録クラス。
	 */
	protected PaidHolidayDataRegistBeanInterface				paidHolidayDataRegist;
	
	/**
	 * 有給休暇付与参照クラス。
	 */
	protected PaidHolidayGrantReferenceBeanInterface			paidHolidayGrantRefer;
	
	/**
	 * 有給休暇付与登録クラス。
	 */
	protected PaidHolidayGrantRegistBeanInterface				paidHolidayGrantRegist;
	
	/**
	 * 有給休暇比例付与参照クラス。
	 */
	protected PaidHolidayProportionallyReferenceBeanInterface	paidHolidayProportionallyRefer;
	
	/**
	 * 有給休暇初年度付与参照クラス。
	 */
	protected PaidHolidayFirstYearReferenceBeanInterface		paidHolidayFirstYearRefer;
	
	/**
	 * 有給休暇自動付与(基準日)参照クラス。
	 */
	protected PaidHolidayPointDateReferenceBeanInterface		paidHolidayPointDateRefer;
	
	/**
	 * 人事入社情報参照クラス。
	 */
	protected EntranceReferenceBeanInterface					entranceRefer;
	
	/**
	 * 人事汎用履歴情報参照クラス。
	 */
	protected HumanHistoryReferenceBeanInterface				humanHistoryRefer;
	
	/**
	 * 週所定労働時間数。
	 */
	protected static final String								PRESCRIBED_WEEKLY_WORKING_HOURS	= "prescribedWeeklyWorkingHours";
	
	/**
	 * 週所定労働日数。
	 */
	protected static final String								PRESCRIBED_WEEKLY_WORKING_DAYS	= "prescribedWeeklyWorkingDays";
	
	/**
	 * 年所定労働日数。
	 */
	protected static final String								PRESCRIBED_ANNUAL_WORKING_DAYS	= "prescribedAnnualWorkingDays";
	
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean()}を実行する。<br>
	 */
	public PaidHolidayDataGrantBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		paidHolidayDataRefer = createBeanInstance(PaidHolidayDataReferenceBeanInterface.class);
		paidHolidayDataRegist = createBeanInstance(PaidHolidayDataRegistBeanInterface.class);
		paidHolidayGrantRefer = createBeanInstance(PaidHolidayGrantReferenceBeanInterface.class);
		paidHolidayGrantRegist = createBeanInstance(PaidHolidayGrantRegistBeanInterface.class);
		paidHolidayProportionallyRefer = createBeanInstance(PaidHolidayProportionallyReferenceBeanInterface.class);
		paidHolidayFirstYearRefer = createBeanInstance(PaidHolidayFirstYearReferenceBeanInterface.class);
		paidHolidayPointDateRefer = createBeanInstance(PaidHolidayPointDateReferenceBeanInterface.class);
		humanHistoryRefer = createBeanInstance(HumanHistoryReferenceBeanInterface.class);
		entranceRefer = createBeanInstance(EntranceReferenceBeanInterface.class);
	}
	
	@Override
	public void grant(String personalId, Date targetDate) throws MospException {
		grant(create(personalId, targetDate));
	}
	
	/**
	 * 有給休暇データ付与を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void grant(PaidHolidayDataDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		PaidHolidayDataDtoInterface paidHolidayDataDto = paidHolidayDataRefer.findForKey(dto.getPersonalId(),
				dto.getActivateDate(), dto.getAcquisitionDate());
		if (paidHolidayDataDto != null) {
			// 削除
			paidHolidayDataRegist.delete(paidHolidayDataDto);
		}
		// 新規登録
		paidHolidayDataRegist.insert(dto);
		paidHolidayGrantRegist(dto);
	}
	
	/**
	 * 有給休暇付与登録処理を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void paidHolidayGrantRegist(PaidHolidayDataDtoInterface dto) throws MospException {
		paidHolidayGrantRegist(dto.getPersonalId(), dto.getAcquisitionDate());
	}
	
	/**
	 * 有給休暇付与登録処理を行う。
	 * @param personalId 個人ID
	 * @param grantDate 付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void paidHolidayGrantRegist(String personalId, Date grantDate) throws MospException {
		PaidHolidayGrantDtoInterface dto = paidHolidayGrantRefer.findForKey(personalId, grantDate);
		if (dto == null) {
			dto = paidHolidayGrantRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setGrantDate(grantDate);
		}
		dto.setGrantStatus(PaidHolidayDataSearchBean.GRANTED);
		paidHolidayGrantRegist.regist(dto);
	}
	
	/**
	 * 有給休暇データを生成する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected PaidHolidayDataDtoInterface create(String personalId, Date targetDate) throws MospException {
		return create(personalId, targetDate, true);
	}
	
	@Override
	public PaidHolidayDataDtoInterface create(String personalId, Date targetDate, boolean accomplish)
			throws MospException {
		return create(personalId, getGrantTimes(personalId, targetDate), accomplish);
	}
	
	/**
	 * 有給休暇データを生成する。
	 * @param personalId 個人ID
	 * @param grantTimes 有給休暇付与回数
	 * @param accomplish 達成率基準
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected PaidHolidayDataDtoInterface create(String personalId, int grantTimes, boolean accomplish)
			throws MospException {
		if (paidHolidayDto == null) {
			return null;
		}
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY
				|| paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY) {
			// 基準日・比例付与の場合
			Date grantDate = getGrantDate(personalId, grantTimes);
			if (grantDate == null) {
				return null;
			}
			PaidHolidayDataDtoInterface dto = paidHolidayDataRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setHoldDay(0);
			dto.setHoldHour(0);
			dto.setGivingDay(0);
			dto.setGivingHour(0);
			dto.setCancelDay(0);
			dto.setCancelHour(0);
			dto.setUseDay(0);
			dto.setUseHour(0);
			dto.setDenominatorDayHour(paidHolidayDto.getTimeAcquisitionLimitTimes());
			dto.setTemporaryFlag(1);
			dto.setActivateDate(grantDate);
			dto.setAcquisitionDate(grantDate);
			dto.setLimitDate(getExpirationDate(personalId, grantDate, grantTimes));
			dto.setHoldDay(getGrantDays(personalId, grantTimes, accomplish));
			dto.setHoldHour(0);
			return dto;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return null;
		}
		return null;
	}
	
	@Override
	public int getGrantTimes(String personalId, Date targetDate) throws MospException {
		// 有給休暇設定取得
		setPaidHolidaySettings(personalId, targetDate);
		if (paidHolidayDto == null) {
			return 0;
		}
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			// 初年度付与日
			Date grantDateOfFirstFiscalYear = getGrantDateOfFirstFiscalYear(personalId);
			if (grantDateOfFirstFiscalYear != null && targetDate.before(grantDateOfFirstFiscalYear)) {
				// 初年度付与日より前の場合
				return 0;
			}
			// 初年度付与日以後の場合
			// 付与回数
			int count = 0;
			Date entranceDate = entranceRefer.getEntranceDate(personalId);
			if (entranceDate == null) {
				return 0;
			}
			int grantYear = DateUtility.getYear(entranceDate);
			int grantMonth = paidHolidayDto.getPointDateMonth();
			int grantDay = paidHolidayDto.getPointDateDay();
			// 付与日
			Date grantDate = DateUtility.getDate(grantYear, grantMonth, grantDay);
			while (!grantDate.after(entranceDate)) {
				// 付与日が入社日より後でない場合
				grantYear++;
				grantDate = DateUtility.getDate(grantYear, grantMonth, grantDay);
			}
			if (grantDateOfFirstFiscalYear != null) {
				while (!grantDate.after(grantDateOfFirstFiscalYear)) {
					// 付与日が初年度付与日より後でない場合
					grantYear++;
					grantDate = DateUtility.getDate(grantYear, grantMonth, grantDay);
				}
				if (!targetDate.before(grantDateOfFirstFiscalYear) && targetDate.before(grantDate)) {
					// 初年度付与日より前でなく且つ次年度付与日より前の場合は初年度とする
					count = 1;
					return 1;
				}
			}
			if (targetDate.before(grantDate)) {
				// 付与日より前の場合
				return 0;
			}
			// 次年度以後の場合
			count = 2;
			// 次年度付与日
			Date grantDateOfNextFiscalYear = DateUtility.getDate(DateUtility.getYear(grantDate) + 1, grantMonth,
					grantDay);
			while (!targetDate.before(grantDateOfNextFiscalYear)) {
				// 次年度付与日より前でない場合
				grantDate = grantDateOfNextFiscalYear;
				grantDateOfNextFiscalYear = DateUtility.getDate(DateUtility.getYear(grantDate) + 1, grantMonth,
						grantDay);
				count++;
			}
			return count;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return 0;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return 0;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY) {
			// 比例付与の場合
			Date entranceDate = entranceRefer.getEntranceDate(personalId);
			if (entranceDate == null) {
				return 0;
			}
			int count = 0;
			int addMonth = 6;
			while (!targetDate.before(DateUtility.addMonth(entranceDate, addMonth))) {
				// 入社日のaddMonthヶ月後より前でない場合
				// 回数を加算
				count++;
				// 12ヶ月を加算
				addMonth += 12;
			}
			return count;
		}
		return 0;
	}
	
	@Override
	public Date getGrantDate(String personalId, Date targetDate, int grantTimes) throws MospException {
		if (!hasPaidHolidaySettings(personalId, targetDate)) {
			return null;
		}
		return getGrantDate(personalId, grantTimes);
	}
	
	@Override
	public Date getGrantDate(String personalId, int grantTimes) throws MospException {
		return getGrantDate(personalId, grantTimes, entranceRefer.getEntranceDate(personalId));
	}
	
	@Override
	public Date getGrantDate(String personalId, Date targetDate, int grantTimes, Date entranceDate)
			throws MospException {
		if (!hasPaidHolidaySettings(personalId, targetDate)) {
			return null;
		}
		return getGrantDate(personalId, grantTimes, entranceDate);
	}
	
	/**
	 * 有給休暇付与日を取得する。
	 * @param personalId 個人ID
	 * @param grantTimes 付与回数
	 * @param entranceDate 入社日
	 * @return 有給休暇付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getGrantDate(String personalId, int grantTimes, Date entranceDate) throws MospException {
		if (entranceDate == null) {
			return null;
		}
		if (paidHolidayDto == null) {
			return null;
		}
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			// 初年度付与日
			Date grantDateOfFirstFiscalYear = getGrantDateOfFirstFiscalYear(personalId);
			if (grantTimes <= 0) {
				// 0以下の場合
				return null;
			} else if (grantTimes == 1) {
				// 1の場合
				return grantDateOfFirstFiscalYear;
			}
			// 1より大きい場合
			int grantMonth = paidHolidayDto.getPointDateMonth();
			int grantDay = paidHolidayDto.getPointDateDay();
			Date grantDate = DateUtility.getDate(DateUtility.getYear(entranceDate), grantMonth, grantDay);
			while (!grantDate.after(entranceDate)) {
				// 付与日が入社日より後でない場合
				grantDate = DateUtility.getDate(DateUtility.getYear(grantDate) + 1, grantMonth, grantDay);
			}
			if (grantDateOfFirstFiscalYear != null) {
				while (!grantDate.after(grantDateOfFirstFiscalYear)) {
					// 付与日が初年度付与日より後でない場合
					grantDate = DateUtility.getDate(DateUtility.getYear(grantDate) + 1, grantMonth, grantDay);
				}
			}
			if (grantTimes == 2) {
				// 2の場合
				return grantDate;
			}
			// 2より大きい場合
			return DateUtility.getDate(DateUtility.getYear(grantDate) + grantTimes - 2, grantMonth, grantDay);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY) {
			// 比例付与の場合
			if (grantTimes <= 0) {
				// 0以下の場合
				return null;
			}
			// 0より大きい場合
			int addMonth = (grantTimes - 1) * 12 + 6;
			return DateUtility.addMonth(entranceDate, addMonth);
		}
		return null;
	}
	
	/**
	 * 有給休暇初年度付与日を取得する。
	 * @param personalId 個人ID
	 * @return 有給休暇初年度付与日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getGrantDateOfFirstFiscalYear(String personalId) throws MospException {
		Date entranceDate = entranceRefer.getEntranceDate(personalId);
		if (entranceDate == null) {
			return null;
		}
		if (paidHolidayDto == null) {
			return null;
		}
		PaidHolidayFirstYearDtoInterface dto = paidHolidayFirstYearRefer.findForKey(paidHolidayDto.getPaidHolidayCode(),
				paidHolidayDto.getActivateDate(), DateUtility.getMonth(entranceDate));
		if (dto == null) {
			return null;
		}
		if (dto.getGivingAmount() <= 0) {
			// 付与日数が0以下の場合
			return null;
		}
		return addDay(
				DateUtility.addMonth(MonthUtility.getTargetYearMonth(entranceDate, mospParams), dto.getGivingMonth()),
				paidHolidayDto.getPointDateDay() - 1);
	}
	
	@Override
	public Date getExpirationDate(String personalId, Date grantDate, int grantTimes) throws MospException {
		if (paidHolidayDto == null) {
			return null;
		}
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			if (grantTimes <= 0) {
				// 0以下の場合
				return null;
			} else if (grantTimes == 1) {
				// 1の場合
				return getExpirationDateOfFirstFiscalYear(personalId, grantDate);
			}
			// 1より大きい場合
			int addYear = 1;
			if (paidHolidayDto.getMaxCarryOverYear() == 0) {
				// 有休繰越が有効の場合は2年とする
				addYear = 2;
			}
			return addDay(DateUtility.addYear(grantDate, addYear), -1);
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return null;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY) {
			// 比例付与の場合
			if (grantTimes <= 0) {
				// 0以下の場合
				return null;
			}
			// 0より大きい場合
			int addYear = 1;
			if (paidHolidayDto.getMaxCarryOverYear() == 0) {
				// 有休繰越が有効の場合は2年とする
				addYear = 2;
			}
			return addDay(DateUtility.addYear(grantDate, addYear), -1);
		}
		return null;
	}
	
	/**
	 * 有給休暇初年度期限日を取得する。
	 * @param personalId 個人ID
	 * @param grantDate 付与日
	 * @return 有給休暇初年度期限日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getExpirationDateOfFirstFiscalYear(String personalId, Date grantDate) throws MospException {
		Date entranceDate = entranceRefer.getEntranceDate(personalId);
		if (entranceDate == null) {
			return null;
		}
		if (paidHolidayDto == null) {
			return null;
		}
		return getExpirationDateOfFirstFiscalYear(
				paidHolidayFirstYearRefer.findForKey(paidHolidayDto.getPaidHolidayCode(),
						paidHolidayDto.getActivateDate(), DateUtility.getMonth(entranceDate)),
				grantDate);
	}
	
	/**
	 * 有給休暇初年度期限日を取得する。
	 * @param dto 有給休暇初年度DTO
	 * @param grantDate 付与日
	 * @return 有給休暇初年度期限日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getExpirationDateOfFirstFiscalYear(PaidHolidayFirstYearDtoInterface dto, Date grantDate)
			throws MospException {
		if (dto == null) {
			return null;
		}
		return addDay(DateUtility.addMonth(grantDate, dto.getGivingLimit()), -1);
	}
	
	/**
	 * 有給休暇付与日数を取得する。
	 * @param personalId 個人ID
	 * @param grantTimes 付与回数
	 * @param accomplish 出勤率基準
	 * @return 有給休暇付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getGrantDays(String personalId, int grantTimes, boolean accomplish) throws MospException {
		if (paidHolidayDto == null) {
			return 0;
		}
		int paidHolidayType = paidHolidayDto.getPaidHolidayType();
		if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_STANDARDSDAY) {
			// 基準日の場合
			if (!accomplish) {
				// 未達成の場合
				return 0;
			}
			// 達成の場合
			if (grantTimes <= 0) {
				// 0以下の場合
				return 0;
			} else if (grantTimes == 1) {
				// 1の場合
				return getGrantDaysOfFirstFiscalYear(personalId, accomplish);
			}
			// 1より大きい場合
			PaidHolidayPointDateDtoInterface paidHolidayPointDateDto = paidHolidayPointDateRefer
				.findForKey(paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(), grantTimes);
			if (paidHolidayPointDateDto == null) {
				// 登録情報最大まで経過後の場合
				return paidHolidayDto.getGeneralPointAmount();
			}
			// 登録情報最大まで経過していない場合
			return paidHolidayPointDateDto.getPointDateAmount();
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEMONTH) {
			// 入社月の場合
			return 0;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_ENTRANCEDAY) {
			// 入社日の場合
			return 0;
		} else if (paidHolidayType == TimeConst.CODE_PAID_HOLIDAY_TYPE_PROPORTIONALLY) {
			// 比例付与の場合
			return getGrantDaysForProportionally(personalId, grantTimes, accomplish);
		}
		return 0;
	}
	
	/**
	 * 有給休暇付与日数を取得する。
	 * @param personalId 個人ID
	 * @param grantTimes 付与回数
	 * @param accomplish 出勤率基準
	 * @return 有給休暇付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getGrantDaysForProportionally(String personalId, int grantTimes, boolean accomplish)
			throws MospException {
		if (!accomplish) {
			// 未達成の場合
			return 0;
		}
		// 達成の場合
		Date grantDate = getGrantDate(personalId, grantTimes);
		if (grantDate == null) {
			return 0;
		}
		return getGrantDaysForProportionally(personalId, grantDate, grantTimes);
	}
	
	@Override
	public int getGrantDaysForProportionally(String personalId, Date grantDate, int grantTimes) throws MospException {
		Double prescribedWeeklyWorkingHours = null;
		Integer prescribedWeeklyWorkingDays = null;
		Integer prescribedAnnualWorkingDays = null;
		// 週所定労働時間数取得
		HumanHistoryDtoInterface prescribedWeeklyWorkingHoursDto = humanHistoryRefer.findForInfo(personalId,
				PRESCRIBED_WEEKLY_WORKING_HOURS, grantDate);
		// 週所定労働時間数が設定されている場合
		if (prescribedWeeklyWorkingHoursDto != null && !prescribedWeeklyWorkingHoursDto.getHumanItemValue().isEmpty()) {
			// 設定
			prescribedWeeklyWorkingHours = Double.valueOf(prescribedWeeklyWorkingHoursDto.getHumanItemValue());
		}
		// 週所定労働日数取得
		HumanHistoryDtoInterface prescribedWeeklyWorkingDaysDto = humanHistoryRefer.findForInfo(personalId,
				PRESCRIBED_WEEKLY_WORKING_DAYS, grantDate);
		// 週所定労働日数が設定されている場合
		if (prescribedWeeklyWorkingDaysDto != null && !prescribedWeeklyWorkingDaysDto.getHumanItemValue().isEmpty()) {
			// 設定	
			prescribedWeeklyWorkingDays = Integer.valueOf(prescribedWeeklyWorkingDaysDto.getHumanItemValue());
		}
		// 年所定労働日数取得
		HumanHistoryDtoInterface prescribedAnnualWorkingDaysDto = humanHistoryRefer.findForInfo(personalId,
				PRESCRIBED_ANNUAL_WORKING_DAYS, grantDate);
		// 年所定労働日数が設定されている場合
		if (prescribedAnnualWorkingDaysDto != null && !prescribedAnnualWorkingDaysDto.getHumanItemValue().isEmpty()) {
			// 設定
			prescribedAnnualWorkingDays = Integer.valueOf(prescribedAnnualWorkingDaysDto.getHumanItemValue());
		}
		return getGrantDaysForProportionally(grantTimes, prescribedWeeklyWorkingHours, prescribedWeeklyWorkingDays,
				prescribedAnnualWorkingDays);
	}
	
	/**
	 * 有給休暇付与日数を取得する。
	 * @param grantTimes 付与回数
	 * @param prescribedWeeklyWorkingHours 週所定労働時間数
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @param prescribedAnnualWorkingDays 年所定労働日数
	 * @return 有給休暇付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getGrantDaysForProportionally(int grantTimes, Double prescribedWeeklyWorkingHours,
			Integer prescribedWeeklyWorkingDays, Integer prescribedAnnualWorkingDays) throws MospException {
		if (prescribedWeeklyWorkingHours == null || prescribedWeeklyWorkingHours.intValue() >= 30) {
			// 週所定労働時間数が設定されていない又は週所定労働時間数が30時間以上の場合
			return getGrantDaysForPrescribedWeeklyWorkingDays(grantTimes, 5);
		}
		// 週所定労働時間数が30時間未満の場合
		if (prescribedWeeklyWorkingDays != null) {
			// 週所定労働日数が設定されている場合
			return getGrantDaysForPrescribedWeeklyWorkingDays(grantTimes, prescribedWeeklyWorkingDays.intValue());
		}
		// 週所定労働日数が設定されていない場合
		int pAWD = 217;
		if (prescribedAnnualWorkingDays != null) {
			// 年所定労働日数が設定されている場合
			pAWD = prescribedAnnualWorkingDays.intValue();
		}
		return getGrantDaysForPrescribedAnnualWorkingDays(grantTimes, pAWD);
	}
	
	/**
	 * 有給休暇付与日数を取得する。
	 * @param grantTimes 付与回数
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @return 有給休暇付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getGrantDaysForPrescribedWeeklyWorkingDays(int grantTimes, int prescribedWeeklyWorkingDays)
			throws MospException {
		int days = prescribedWeeklyWorkingDays;
		if (prescribedWeeklyWorkingDays >= 5) {
			// 5以上の場合
			days = 5;
		} else if (prescribedWeeklyWorkingDays <= 0) {
			// 0以下の場合
			return 0;
		}
		int continuousServiceTermsCountingFromTheEmploymentDay = 12 * (grantTimes - 1) + 6;
		if (grantTimes >= 7) {
			// 7以上の場合
			continuousServiceTermsCountingFromTheEmploymentDay = 78;
		} else if (grantTimes <= 0) {
			// 0以下の場合
			return 0;
		}
		PaidHolidayProportionallyDtoInterface dto = paidHolidayProportionallyRefer.findForInfo(
				paidHolidayDto.getPaidHolidayCode(), paidHolidayDto.getActivateDate(), days,
				continuousServiceTermsCountingFromTheEmploymentDay);
		if (dto == null) {
			return 0;
		}
		if (dto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_OFF) {
			return dto.getDays();
		}
		return 0;
	}
	
	/**
	 * 有給休暇付与日数を取得する。
	 * @param grantTimes 付与回数
	 * @param prescribedAnnualWorkingDays 年所定労働日数
	 * @return 有給休暇付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getGrantDaysForPrescribedAnnualWorkingDays(int grantTimes, int prescribedAnnualWorkingDays)
			throws MospException {
		if (prescribedAnnualWorkingDays >= 217) {
			// 217以上の場合
			return getGrantDaysForPrescribedWeeklyWorkingDays(grantTimes, 5);
		} else if (prescribedAnnualWorkingDays >= 169) {
			// 169以上216以下の場合
			return getGrantDaysForPrescribedWeeklyWorkingDays(grantTimes, 4);
		} else if (prescribedAnnualWorkingDays >= 121) {
			// 121以上168以下の場合
			return getGrantDaysForPrescribedWeeklyWorkingDays(grantTimes, 3);
		} else if (prescribedAnnualWorkingDays >= 73) {
			// 73以上120以下の場合
			return getGrantDaysForPrescribedWeeklyWorkingDays(grantTimes, 2);
		} else if (prescribedAnnualWorkingDays >= 48) {
			// 48以上72以下の場合
			return getGrantDaysForPrescribedWeeklyWorkingDays(grantTimes, 1);
		}
		// 48未満の場合
		return getGrantDaysForPrescribedWeeklyWorkingDays(grantTimes, 0);
	}
	
	/**
	 * 有給休暇初年度付与日数を取得する。
	 * @param personalId 個人ID
	 * @param accomplish 出勤率基準
	 * @return 有給休暇初年度付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getGrantDaysOfFirstFiscalYear(String personalId, boolean accomplish) throws MospException {
		if (!accomplish) {
			// 未達成の場合
			return 0;
		}
		// 達成の場合
		Date entranceDate = entranceRefer.getEntranceDate(personalId);
		if (entranceDate == null) {
			return 0;
		}
		if (paidHolidayDto == null) {
			return 0;
		}
		PaidHolidayFirstYearDtoInterface dto = paidHolidayFirstYearRefer.findForKey(paidHolidayDto.getPaidHolidayCode(),
				paidHolidayDto.getActivateDate(), DateUtility.getMonth(entranceDate));
		if (dto == null) {
			return 0;
		}
		return dto.getGivingAmount();
	}
	
}
