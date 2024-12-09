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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dao.human.impl.PfmHumanDao;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.time.bean.CalendarWorkingDaysBeanInterface;
import jp.mosp.time.bean.ExportTableReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.ExportDataDaoInterface;
import jp.mosp.time.dao.settings.impl.TmdAttendanceDao;
import jp.mosp.time.dao.settings.impl.TmdHolidayDataDao;
import jp.mosp.time.dao.settings.impl.TmdPaidHolidayDao;
import jp.mosp.time.dao.settings.impl.TmdStockHolidayDao;
import jp.mosp.time.dao.settings.impl.TmdTotalTimeDao;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.portal.bean.impl.PortalTimeCardBean;
import jp.mosp.time.utils.TimeUtility;

/**
 * エクスポートテーブル参照クラス。
 */
public class ExportTableReferenceBean extends PlatformBean implements ExportTableReferenceBeanInterface {
	
	/**
	 * エクスポートコード。
	 */
	protected String									exportCode;
	
	/**
	 * 開始年。
	 */
	protected int										startYear;
	
	/**
	 * 開始月。
	 */
	protected int										startMonth;
	
	/**
	 * 終了年。
	 */
	protected int										endYear;
	
	/**
	 * 終了月。
	 */
	protected int										endMonth;
	
	/**
	 * 締日コード。
	 */
	protected String									cutoffCode;
	
	/**
	 * 勤務地コード。
	 */
	protected String									workPlaceCode;
	
	/**
	 * 雇用契約コード。
	 */
	protected String									employmentCode;
	
	/**
	 * 所属コード。
	 */
	protected String									sectionCode;
	
	/**
	 * 職位コード。
	 */
	protected String									positionCode;
	
	/**
	 * 下位所属含むチェックボックス。
	 */
	protected int										ckbNeedLowerSection;
	
	/**
	 * エクスポートマスタDAO。
	 */
	protected ExportDaoInterface						exportDao;
	
	/**
	 * エクスポートフィールドマスタDAO。
	 */
	protected ExportFieldDaoInterface					exportFieldDao;
	
	/**
	 * エクスポートデータDAO。
	 */
	protected ExportDataDaoInterface					exportDataDao;
	
	/**
	 * 打刻データ参照クラス。
	 */
	protected TimeRecordReferenceBeanInterface			timeRecord;
	
	/**
	 * 勤務地マスタ参照クラス。<br>　
	 */
	protected WorkPlaceReferenceBeanInterface			workPlace;
	
	/**
	 * 雇用契約マスタ参照クラス。<br>　
	 */
	protected EmploymentContractReferenceBeanInterface	employmentContract;
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface				section;
	
	/**
	 * 職位マスタ参照クラス。<br>　
	 */
	protected PositionReferenceBeanInterface			position;
	
	/**
	 * 人事退社情報参照クラス。
	 */
	protected RetirementReferenceBeanInterface			retirementReference;
	
	/**
	 * 有給休暇残数取得処理処理。<br>
	 */
	protected PaidHolidayRemainBeanInterface			paidHolidayRemain;
	
	/**
	 * プラットフォーム関連マスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface				platformMaster;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface					timeMaster;
	
	/**
	 * カレンダ勤務日数参照インターフェイス。<br> 
	 */
	protected CalendarWorkingDaysBeanInterface			calendarWorkingDays;
	/**
	 * 操作時間数(-1年)
	 */
	protected static final int							addPrevious	= -1;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ExportTableReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		exportDao = createDaoInstance(ExportDaoInterface.class);
		exportFieldDao = createDaoInstance(ExportFieldDaoInterface.class);
		exportDataDao = createDaoInstance(ExportDataDaoInterface.class);
		timeRecord = createBeanInstance(TimeRecordReferenceBeanInterface.class);
		workPlace = createBeanInstance(WorkPlaceReferenceBeanInterface.class);
		employmentContract = createBeanInstance(EmploymentContractReferenceBeanInterface.class);
		section = createBeanInstance(SectionReferenceBeanInterface.class);
		position = createBeanInstance(PositionReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		paidHolidayRemain = createBeanInstance(PaidHolidayRemainBeanInterface.class);
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		calendarWorkingDays = createBeanInstance(CalendarWorkingDaysBeanInterface.class);
		// プラットフォーム関連マスタ参照処理をBeanに設定
		timeMaster.setPlatformMaster(platformMaster);
		calendarWorkingDays.setPlatformMaster(platformMaster);
		// 勤怠関連マスタ参照処理をBeanに設定
		paidHolidayRemain.setTimeMaster(timeMaster);
		calendarWorkingDays.setTimeMaster(timeMaster);
	}
	
	@Override
	public List<String[]> export() throws MospException {
		// エクスポート情報取得
		ExportDtoInterface exportDto = exportDao.findForKey(exportCode);
		// 情報確認
		if (exportDto == null) {
			return null;
		}
		// エクスポートフィールドマスタリストを取得
		List<ExportFieldDtoInterface> exportFieldDtoList = exportFieldDao.findForList(exportCode);
		// リスト確認
		if (exportFieldDtoList.isEmpty()) {
			return null;
		}
		// エクスポート時間フォーマット区分
		int format = getExportTimeFormat();
		// リスト準備
		List<String[]> list = new ArrayList<String[]>();
		// ヘッダが有りの場合
		if (exportDto.getHeader() == 1) {
			// ヘッダリスト準備
			List<String> headerList = new ArrayList<String>();
			// エクスポートフィールドマスタリスト毎に処理
			for (ExportFieldDtoInterface exportFieldDto : exportFieldDtoList) {
				// ヘッダ名設定
				headerList.add(mospParams.getProperties().getCodeItemName(exportDto.getExportTable(),
						exportFieldDto.getFieldName()));
			}
			// 1行目にヘッダ名列追加
			list.add(headerList.toArray(new String[0]));
		}
		// 締日エンティティを取得
		CutoffEntityInterface cutoffStart = timeMaster.getCutoff(cutoffCode, startYear, startMonth);
		CutoffEntityInterface cutoffEnd = timeMaster.getCutoff(cutoffCode, endYear, endMonth);
		// 対象年月及び締日から締期間初日及び最終日を取得
		Date startDate = cutoffStart.getCutoffFirstDate(startYear, startMonth, mospParams);
		Date endDate = cutoffEnd.getCutoffLastDate(endYear, endMonth, mospParams);
		// 対象日取得
		Date firstTargetDate = MonthUtility.getYearMonthTargetDate(startYear, startMonth, mospParams);
		// SQL準備
		ResultSet rs = null;
		// データ区分確認フラグ準備
		boolean isAttendance = TimeFileConst.CODE_EXPORT_TYPE_TMD_ATTENDANCE.equals(exportDto.getExportTable());
		boolean isTotalTime = TimeFileConst.CODE_EXPORT_TYPE_TMD_TOTAL_TIME.equals(exportDto.getExportTable());
		boolean isPaidHoliday = TimeFileConst.CODE_EXPORT_TYPE_TMD_PAID_HOLIDAY.equals(exportDto.getExportTable());
		boolean isStockHoliday = TimeFileConst.CODE_EXPORT_TYPE_TMD_STOCK_HOLIDAY.equals(exportDto.getExportTable());
		boolean isHolidayData = TimeFileConst.CODE_EXPORT_TYPE_TMD_HOLIDAY.equals(exportDto.getExportTable());
		if (isAttendance) {
			// 勤怠データ
			rs = exportDataDao.findForAttendance(startDate, endDate, cutoffCode, workPlaceCode, employmentCode,
					sectionCode, ckbNeedLowerSection, positionCode);
		}
		if (isTotalTime) {
			// 勤怠集計データ
			rs = exportDataDao.findForTotalTime(startDate, endDate, cutoffCode, workPlaceCode, employmentCode,
					sectionCode, ckbNeedLowerSection, positionCode);
		}
		if (isPaidHoliday) {
			// 有給休暇データ
			rs = exportDataDao.findForPaidHoliday(startDate, endDate, cutoffCode, workPlaceCode, employmentCode,
					sectionCode, ckbNeedLowerSection, positionCode);
		}
		if (isStockHoliday) {
			// ストック休暇データ
			rs = exportDataDao.findForStockHoliday(startDate, endDate, cutoffCode, workPlaceCode, employmentCode,
					sectionCode, ckbNeedLowerSection, positionCode);
		}
		if (isHolidayData) {
			// 休暇データ
			rs = exportDataDao.findForHolidayData(startDate, endDate, cutoffCode, workPlaceCode, employmentCode,
					sectionCode, ckbNeedLowerSection, positionCode);
		}
		try {
			while (rs != null && rs.next()) {
				List<String> dataList = new ArrayList<String>();
				if (!cutoffCode.isEmpty()) {
					String personalId = "";
					if (isAttendance) {
						// 勤怠データ
						personalId = rs.getString(TmdAttendanceDao.COL_PERSONAL_ID);
						firstTargetDate = rs.getDate(TmdAttendanceDao.COL_WORK_DATE);
					}
					if (isTotalTime) {
						// 勤怠集計データ
						personalId = rs.getString(TmdTotalTimeDao.COL_PERSONAL_ID);
						firstTargetDate = rs.getDate(TmdTotalTimeDao.COL_CALCULATION_DATE);
					}
					if (isPaidHoliday) {
						// 有給休暇データ
						personalId = rs.getString(TmdPaidHolidayDao.COL_PERSONAL_ID);
						firstTargetDate = rs.getDate(TmdPaidHolidayDao.COL_ACTIVATE_DATE);
						// 退職日取得
						Date retirementDate = retirementReference.getRetireDate(personalId);
						if (retirementDate != null
								&& DateUtility.isTermContain(firstTargetDate, retirementDate, null)) {
							// 退職日が存在し、有効日が退職日より後の場合
							continue;
						}
					}
					if (isStockHoliday) {
						// ストック休暇データ
						personalId = rs.getString(TmdStockHolidayDao.COL_PERSONAL_ID);
						firstTargetDate = rs.getDate(TmdStockHolidayDao.COL_ACTIVATE_DATE);
					}
					if (isHolidayData) {
						// 休暇データ
						personalId = rs.getString(TmdHolidayDataDao.COL_PERSONAL_ID);
						firstTargetDate = rs.getDate(TmdHolidayDataDao.COL_ACTIVATE_DATE);
					}
					// 設定適用エンティティを取得
					ApplicationEntity application = timeMaster.getApplicationEntity(personalId, firstTargetDate);
					// 設定適用エンティティが有効でない場合
					if (application.isValid() == false) {
						// 次の情報へ
						continue;
					}
					// 締日コードが異なる場合
					if (MospUtility.isEqual(cutoffCode, application.getCutoffCode()) == false) {
						// 次の情報へ
						continue;
					}
				}
				// エクスポートフィールドマスタリスト毎に処理
				for (ExportFieldDtoInterface exportFieldDto : exportFieldDtoList) {
					String fieldName = exportFieldDto.getFieldName();
					// 個人ID及び対象日準備
					String personalId = "";
					Date targetDate = null;
					// 個人ID及び対象日設定
					if (isAttendance) {
						// 勤怠データ
						personalId = rs.getString(TmdAttendanceDao.COL_PERSONAL_ID);
						targetDate = rs.getDate(TmdAttendanceDao.COL_WORK_DATE);
					}
					if (isTotalTime) {
						// 勤怠集計データ
						personalId = rs.getString(TmdTotalTimeDao.COL_PERSONAL_ID);
						targetDate = rs.getDate(TmdTotalTimeDao.COL_CALCULATION_DATE);
					}
					if (isPaidHoliday) {
						// 有給休暇データ
						personalId = rs.getString(TmdPaidHolidayDao.COL_PERSONAL_ID);
						targetDate = rs.getDate(TmdPaidHolidayDao.COL_ACTIVATE_DATE);
					}
					if (isStockHoliday) {
						// ストック休暇データ
						personalId = rs.getString(TmdStockHolidayDao.COL_PERSONAL_ID);
						targetDate = rs.getDate(TmdStockHolidayDao.COL_ACTIVATE_DATE);
					}
					if (isHolidayData) {
						// 休暇データ
						personalId = rs.getString(TmdHolidayDataDao.COL_PERSONAL_ID);
						targetDate = rs.getDate(TmdHolidayDataDao.COL_ACTIVATE_DATE);
					}
					if (isAttendance) {
						// 勤怠データ
						if (TmdAttendanceDao.COL_WORK_DATE.equals(fieldName)) {
							// 勤務日
							dataList.add(DateUtility.getStringDate(rs.getDate(fieldName)));
							continue;
						}
						// 始業時刻（丸め打刻）
						if (TmdAttendanceDao.COL_START_TIME.equals(fieldName)) {
							// 丸め時刻追加
							dataList.add(
									DateUtility.getStringDateAndTime(rs.getTimestamp(TmdAttendanceDao.COL_START_TIME)));
							continue;
						}
						// 始業時刻(実打刻)
						if (TmdAttendanceDao.COL_ACTUAL_START_TIME.equals(fieldName)) {
							// 実打刻追加
							dataList.add(DateUtility
								.getStringDateAndTime(rs.getTimestamp(TmdAttendanceDao.COL_ACTUAL_START_TIME)));
							continue;
						}
						// 始業時刻(ポータル打刻)
						if (TimeFileConst.FIELD_TIME_ROCODE_START_TIME.equals(fieldName)) {
							String timeRecodeStartTime = "";
							// 打刻データを取得
							TimeRecordDtoInterface recodeDto = timeRecord.findForKey(personalId, targetDate,
									PortalTimeCardBean.RECODE_START_WORK);
							// 打刻データがある場合
							if (recodeDto != null) {
								// 打刻データ取得
								timeRecodeStartTime = DateUtility.getStringDateAndTime(recodeDto.getRecordTime());
							}
							// 打刻データ追加
							dataList.add(timeRecodeStartTime);
							continue;
						}
						// 終業時刻（丸め打刻）
						if (TmdAttendanceDao.COL_END_TIME.equals(fieldName)) {
							dataList
								.add(DateUtility.getStringDateAndTime(rs.getTimestamp(TmdAttendanceDao.COL_END_TIME)));
							continue;
						}
						// 終業時刻(実打刻)
						if (TmdAttendanceDao.COL_ACTUAL_END_TIME.equals(fieldName)) {
							// 実打刻追加
							dataList.add(DateUtility
								.getStringDateAndTime(rs.getTimestamp(TmdAttendanceDao.COL_ACTUAL_END_TIME)));
							continue;
						}
						// 終業時刻(ポータル打刻)
						if (TimeFileConst.FIELD_TIME_ROCODE_END_TIME.equals(fieldName)) {
							String timeRecodeStartTime = "";
							// 打刻データを取得
							TimeRecordDtoInterface recodeDto = timeRecord.findForKey(personalId, targetDate,
									PortalTimeCardBean.RECODE_END_WORK);
							// 打刻データがある場合
							if (recodeDto != null) {
								timeRecodeStartTime = DateUtility.getStringDateAndTime(recodeDto.getRecordTime());
							}
							// 打刻データ取得
							dataList.add(timeRecodeStartTime);
							continue;
						}
						if (
						// 遅刻時間
						TmdAttendanceDao.COL_LATE_TIME.equals(fieldName)
								// 実遅刻時間
								|| TmdAttendanceDao.COL_ACTUAL_LATE_TIME.equals(fieldName)
								// 遅刻30分以上時間
								|| TmdAttendanceDao.COL_LATE_THIRTY_MINUTES_OR_MORE_TIME.equals(fieldName)
								// 遅刻30分未満時間
								|| TmdAttendanceDao.COL_LATE_LESS_THAN_THIRTY_MINUTES_TIME.equals(fieldName)
								// 早退時間
								|| TmdAttendanceDao.COL_LEAVE_EARLY_TIME.equals(fieldName)
								// 実早退時間
								|| TmdAttendanceDao.COL_ACTUAL_LEAVE_EARLY_TIME.equals(fieldName)
								// 早退30分以上時間
								|| TmdAttendanceDao.COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME.equals(fieldName)
								// 早退30分未満時間
								|| TmdAttendanceDao.COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME.equals(fieldName)
								// 勤務時間
								|| TmdAttendanceDao.COL_WORK_TIME.equals(fieldName)
								// 所定労働時間
								|| TmdAttendanceDao.COL_GENERAL_WORK_TIME.equals(fieldName)
								// 所定労働時間内労働時間
								|| TmdAttendanceDao.COL_WORK_TIME_WITHIN_PRESCRIBED_WORK_TIME.equals(fieldName)
								// 契約勤務時間
								|| TmdAttendanceDao.COL_CONTRACT_WORK_TIME.equals(fieldName)
								// 無給時短時間
								|| TmdAttendanceDao.COL_SHORT_UNPAID.equals(fieldName)
								// 休憩時間
								|| TmdAttendanceDao.COL_REST_TIME.equals(fieldName)
								// 法定外休憩時間
								|| TmdAttendanceDao.COL_OVER_REST_TIME.equals(fieldName)
								// 深夜休憩時間
								|| TmdAttendanceDao.COL_NIGHT_REST_TIME.equals(fieldName)
								// 法定休出休憩時間
								|| TmdAttendanceDao.COL_LEGAL_HOLIDAY_REST_TIME.equals(fieldName)
								// 所定休出休憩時間
								|| TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_REST_TIME.equals(fieldName)
								// 公用外出時間
								|| TmdAttendanceDao.COL_PUBLIC_TIME.equals(fieldName)
								// 私用外出時間
								|| TmdAttendanceDao.COL_PRIVATE_TIME.equals(fieldName)
								// 分単位休暇A時間
								|| TmdAttendanceDao.COL_MINUTELY_HOLIDAY_A_TIME.equals(fieldName)
								// 分単位休暇B時間
								|| TmdAttendanceDao.COL_MINUTELY_HOLIDAY_B_TIME.equals(fieldName)
								// 残業時間
								|| TmdAttendanceDao.COL_OVERTIME.equals(fieldName)
								// 前残業時間
								|| TmdAttendanceDao.COL_OVERTIME_BEFORE.equals(fieldName)
								// 後残業時間
								|| TmdAttendanceDao.COL_OVERTIME_AFTER.equals(fieldName)
								// 法定内残業時間
								|| TmdAttendanceDao.COL_OVERTIME_IN.equals(fieldName)
								// 法定外残業時間
								|| TmdAttendanceDao.COL_OVERTIME_OUT.equals(fieldName)
								// 平日法定時間内残業時間
								|| TmdAttendanceDao.COL_WORKDAY_OVERTIME_IN.equals(fieldName)
								// 平日法定時間外残業時間
								|| TmdAttendanceDao.COL_WORKDAY_OVERTIME_OUT.equals(fieldName)
								// 所定休日法定時間内残業時間
								|| TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_OVERTIME_IN.equals(fieldName)
								// 所定休日法定時間外残業時間
								|| TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_OVERTIME_OUT.equals(fieldName)
								// 深夜勤務時間
								|| TmdAttendanceDao.COL_LATE_NIGHT_TIME.equals(fieldName)
								// 深夜所定労働時間内時間
								|| TmdAttendanceDao.COL_NIGHT_WORK_WITHIN_PRESCRIBED_WORK.equals(fieldName)
								// 深夜時間外時間
								|| TmdAttendanceDao.COL_NIGHT_OVERTIME_WORK.equals(fieldName)
								// 深夜休日労働時間
								|| TmdAttendanceDao.COL_NIGHT_WORK_ON_HOLIDAY.equals(fieldName)
								// 所定休日勤務時間
								|| TmdAttendanceDao.COL_SPECIFIC_WORK_TIME.equals(fieldName)
								// 法定休日勤務時間
								|| TmdAttendanceDao.COL_LEGAL_WORK_TIME.equals(fieldName)
								// 減額対象時間
								|| TmdAttendanceDao.COL_DECREASE_TIME.equals(fieldName)
								// 法定休出時間(代休あり)
								|| TmdAttendanceDao.COL_LEGAL_HOLIDAY_WORK_TIME_WITH_COMPENSATION_DAY.equals(fieldName)
								// 法定休出時間(代休なし)
								|| TmdAttendanceDao.COL_LEGAL_HOLIDAY_WORK_TIME_WITHOUT_COMPENSATION_DAY
									.equals(fieldName)
								// 所定休出時間(代休あり)
								|| TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_WITH_COMPENSATION_DAY
									.equals(fieldName)
								// 所定休出時間(代休なし)
								|| TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_WITHOUT_COMPENSATION_DAY
									.equals(fieldName)
								// 法定労働時間内残業時間(代休あり)
								|| TmdAttendanceDao.COL_OVERTIME_IN_WITH_COMPENSATION_DAY.equals(fieldName)
								// 法定労働時間内残業時間(代休なし)
								|| TmdAttendanceDao.COL_OVERTIME_IN_WITHOUT_COMPENSATION_DAY.equals(fieldName)
								// 法定労働時間外残業時間(代休あり)
								|| TmdAttendanceDao.COL_OVERTIME_OUT_WITH_COMPENSATION_DAY.equals(fieldName)
								// 法定労働時間外残業時間(代休なし)
								|| TmdAttendanceDao.COL_OVERTIME_OUT_WITHOUT_COMPENSATION_DAY.equals(fieldName)
								// 所定労働時間内法定休日労働時間
								|| TmdAttendanceDao.COL_STATUTORY_HOLIDAY_WORK_TIME_IN.equals(fieldName)
								// 所定労働時間外法定休日労働時間
								|| TmdAttendanceDao.COL_STATUTORY_HOLIDAY_WORK_TIME_OUT.equals(fieldName)
								// 所定労働時間内所定休日労働時間
								|| TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_IN.equals(fieldName)
								// 所定労働時間外所定休日労働時間
								|| TmdAttendanceDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_OUT.equals(fieldName)) {
							// 項目追加
							dataList.add(getExportTime(rs.getInt(fieldName), format));
							continue;
						}
					}
					// データ区分が勤怠集計データである場合
					if (isTotalTime) {
						if (TmdTotalTimeDao.COL_CALCULATION_DATE.equals(fieldName)) {
							// 集計日
							dataList.add(DateUtility.getStringDate(rs.getDate(fieldName)));
							continue;
						}
						// 週40時間超勤務時間(法定内発生分)である場合
						if (MospUtility.isEqual(fieldName, TimeFileConst.WEEKLY_OVER_FORTY_IN)) {
							// 法定内残業時間と法定内残業時間(週40時間超除く)を取得
							int overtimeIn = rs.getInt(TmdTotalTimeDao.COL_OVERTIME_IN);
							int overtimeInNoWeeklyForty = rs.getInt(TmdTotalTimeDao.COL_OVERTIME_IN_NO_WEEKLY_FORTY);
							// 週40時間超勤務時間(法定内発生分)を計算
							int weeklyOverFortyIn = TimeUtility.calcWeeklyOverFortyIn(overtimeIn,
									overtimeInNoWeeklyForty);
							// 週40時間超勤務時間(法定内発生分)をエクスポートデータに設定
							dataList.add(getExportTime(weeklyOverFortyIn, format));
							// 次の項目へ
							continue;
						}
						// 週40時間超勤務時間(勤務内発生分)である場合
						if (MospUtility.isEqual(fieldName, TimeFileConst.WEEKLY_OVER_FORTY_NORMNAL)) {
							// 週40時間超勤務時間と法定内残業時間と法定内残業時間(週40時間超除く)を取得
							int weeklyOverForty = rs.getInt(TmdTotalTimeDao.COL_WEEKLY_OVER_FORTY_HOUR_WORK_TIME);
							int overtimeIn = rs.getInt(TmdTotalTimeDao.COL_OVERTIME_IN);
							int overtimeInNoWeeklyForty = rs.getInt(TmdTotalTimeDao.COL_OVERTIME_IN_NO_WEEKLY_FORTY);
							// 週40時間超勤務時間(勤務内発生分)を計算
							int weeklyOverFortyNormal = TimeUtility.calcWeeklyOverFortyNormal(weeklyOverForty,
									overtimeIn, overtimeInNoWeeklyForty);
							// 週40時間超勤務時間(勤務内発生分)をエクスポートデータに設定
							dataList.add(getExportTime(weeklyOverFortyNormal, format));
							// 次の項目へ
							continue;
						}
						if (
						// 勤務時間
						TmdTotalTimeDao.COL_WORK_TIME.equals(fieldName)
								// 所定勤務時間
								|| TmdTotalTimeDao.COL_SPECIFIC_WORK_TIME.equals(fieldName)
								// 契約勤務時間
								|| TmdTotalTimeDao.COL_CONTRACT_WORK_TIME.equals(fieldName)
								// 無給時短時間
								|| TmdTotalTimeDao.COL_SHORT_UNPAID.equals(fieldName)
								// 休憩時間
								|| TmdTotalTimeDao.COL_REST_TIME.equals(fieldName)
								// 深夜休憩時間
								|| TmdTotalTimeDao.COL_REST_LATE_NIGHT.equals(fieldName)
								// 所定休出休憩時間
								|| TmdTotalTimeDao.COL_REST_WORK_ON_SPECIFIC_HOLIDAY.equals(fieldName)
								// 法定休出休憩時間
								|| TmdTotalTimeDao.COL_REST_WORK_ON_HOLIDAY.equals(fieldName)
								// 公用外出時間
								|| TmdTotalTimeDao.COL_PUBLIC_TIME.equals(fieldName)
								// 私用外出時間
								|| TmdTotalTimeDao.COL_PRIVATE_TIME.equals(fieldName)
								// 分単位休暇A時間
								|| TmdTotalTimeDao.COL_MINUTELY_HOLIDAY_A_TIME.equals(fieldName)
								// 分単位休暇B時間
								|| TmdTotalTimeDao.COL_MINUTELY_HOLIDAY_B_TIME.equals(fieldName)
								// 残業時間
								|| TmdTotalTimeDao.COL_OVERTIME.equals(fieldName)
								// 法定内残業時間
								|| TmdTotalTimeDao.COL_OVERTIME_IN.equals(fieldName)
								// 法定外残業時間
								|| TmdTotalTimeDao.COL_OVERTIME_OUT.equals(fieldName)
								// 深夜時間
								|| TmdTotalTimeDao.COL_LATE_NIGHT.equals(fieldName)
								// 深夜所定労働時間内時間
								|| TmdTotalTimeDao.COL_NIGHT_WORK_WITHIN_PRESCRIBED_WORK.equals(fieldName)
								// 深夜時間外時間
								|| TmdTotalTimeDao.COL_NIGHT_OVERTIME_WORK.equals(fieldName)
								// 深夜休日労働時間
								|| TmdTotalTimeDao.COL_NIGHT_WORK_ON_HOLIDAY.equals(fieldName)
								// 所定休出時間
								|| TmdTotalTimeDao.COL_WORK_ON_SPECIFIC_HOLIDAY.equals(fieldName)
								// 法定休出時間
								|| TmdTotalTimeDao.COL_WORK_ON_HOLIDAY.equals(fieldName)
								// 減額対象時間
								|| TmdTotalTimeDao.COL_DECREASE_TIME.equals(fieldName)
								// 45時間超残業時間
								|| TmdTotalTimeDao.COL_FORTY_FIVE_HOUR_OVERTIME.equals(fieldName)
								// 合計遅刻時間
								|| TmdTotalTimeDao.COL_LATE_TIME.equals(fieldName)
								// 遅刻30分以上時間
								|| TmdTotalTimeDao.COL_LATE_THIRTY_MINUTES_OR_MORE_TIME.equals(fieldName)
								// 遅刻30分未満時間
								|| TmdTotalTimeDao.COL_LATE_LESS_THAN_THIRTY_MINUTES_TIME.equals(fieldName)
								// 合計早退時間
								|| TmdTotalTimeDao.COL_LEAVE_EARLY_TIME.equals(fieldName)
								// 早退30分以上時間
								|| TmdTotalTimeDao.COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME.equals(fieldName)
								// 早退30分未満時間
								|| TmdTotalTimeDao.COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME.equals(fieldName)
								// 60時間超残業時間
								|| TmdTotalTimeDao.COL_SIXTY_HOUR_OVERTIME.equals(fieldName)
								// 平日時間外時間
								|| TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME.equals(fieldName)
								// 所定休日時間外時間
								|| TmdTotalTimeDao.COL_SPECIFIC_OVERTIME.equals(fieldName)
								// 所定労働時間内法定休日労働時間
								|| TmdTotalTimeDao.COL_STATUTORY_HOLIDAY_WORK_TIME_IN.equals(fieldName)
								// 所定労働時間外法定休日労働時間
								|| TmdTotalTimeDao.COL_STATUTORY_HOLIDAY_WORK_TIME_OUT.equals(fieldName)
								// 所定労働時間内所定休日労働時間
								|| TmdTotalTimeDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_IN.equals(fieldName)
								// 所定労働時間外所定休日労働時間
								|| TmdTotalTimeDao.COL_PRESCRIBED_HOLIDAY_WORK_TIME_OUT.equals(fieldName)
								// 週40時間超勤務時間
								|| TmdTotalTimeDao.COL_WEEKLY_OVER_FORTY_HOUR_WORK_TIME.equals(fieldName)
								// 法定内残業時間(週40時間超除く)
								|| TmdTotalTimeDao.COL_OVERTIME_IN_NO_WEEKLY_FORTY.equals(fieldName)
								// 法定外残業時間(週40時間超除く)
								|| TmdTotalTimeDao.COL_OVERTIME_OUT_NO_WEEKLY_FORTY.equals(fieldName)
								// 平日残業合計時間
								|| TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME_TOTAL.equals(fieldName)
								// 平日時間内時間(週40時間超除く)
								|| TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME_IN_NO_WEEKLY_FORTY.equals(fieldName)
								// 平日時間外時間(週40時間超除く)
								|| TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME_OUT_NO_WEEKLY_FORTY.equals(fieldName)
								// 平日時間内時間
								|| TmdTotalTimeDao.COL_WEEK_DAY_OVERTIME_IN.equals(fieldName)
								// 汎用項目1(数値)
								|| TmdTotalTimeDao.COL_GENERAL_INT_ITEM1.equals(fieldName)) {
							// 項目追加
							dataList.add(getExportTime(rs.getInt(fieldName), format));
							continue;
						}
					}
					// 有給休暇データ(有効日・取得日・期限日)である場合
					if (isPaidHoliday && (TmdPaidHolidayDao.COL_ACTIVATE_DATE.equals(fieldName)
							|| TmdPaidHolidayDao.COL_ACQUISITION_DATE.equals(fieldName)
							|| TmdPaidHolidayDao.COL_LIMIT_DATE.equals(fieldName))) {
						// 有効日・取得日・期限日
						dataList.add(DateUtility.getStringDate(rs.getDate(fieldName)));
						continue;
					}
					// 有給休暇データ(前年繰越日数)である場合
					if (isPaidHoliday && MospUtility.isEqual(fieldName, TimeFileConst.FIELD_CARYYOVER_DAY)) {
						// 対象有給休暇情報の付与日を取得
						Date acquisitionDate = rs.getDate(TmdPaidHolidayDao.COL_ACQUISITION_DATE);
						// システム日付を取得
						Date systemDate = getSystemDate();
						// 前年繰越日数を取得(システム日付における最新の有効日の情報で取得)
						double days = paidHolidayRemain.getCarryoverDays(personalId, systemDate, acquisitionDate);
						// 前年繰越日数を設定
						dataList.add(TransStringUtility.getDoubleTimes(mospParams, days, false, true));
						// 次のフィールドへ
						continue;
					}
					// 有給休暇データ(前年繰越時間)である場合
					if (isPaidHoliday && MospUtility.isEqual(fieldName, TimeFileConst.FIELD_CARYYOVER_HOUR)) {
						// 対象有給休暇情報の付与日を取得
						Date acquisitionDate = rs.getDate(TmdPaidHolidayDao.COL_ACQUISITION_DATE);
						// システム日付を取得
						Date systemDate = getSystemDate();
						// 前年繰越時間を取得(システム日付における最新の有効日の情報で取得)
						int hours = paidHolidayRemain.getCarryoverHours(personalId, systemDate, acquisitionDate);
						// 前年繰越日数を設定
						dataList.add(TransStringUtility.getIntegerTimes(mospParams, hours, false));
						// 次のフィールドへ
						continue;
					}
					if (isStockHoliday && (TmdStockHolidayDao.COL_ACTIVATE_DATE.equals(fieldName)
							|| TmdStockHolidayDao.COL_ACQUISITION_DATE.equals(fieldName)
							|| TmdStockHolidayDao.COL_LIMIT_DATE.equals(fieldName))) {
						// ストック休暇データ
						// 有効日・取得日・期限日
						dataList.add(DateUtility.getStringDate(rs.getDate(fieldName)));
						continue;
					}
					if (isHolidayData && (TmdHolidayDataDao.COL_ACTIVATE_DATE.equals(fieldName)
							|| TmdHolidayDataDao.COL_HOLIDAY_LIMIT_DATE.equals(fieldName))) {
						// 休暇データ
						// 有効日・取得期限
						dataList.add(DateUtility.getStringDate(rs.getDate(fieldName)));
						continue;
					}
					if (PlatformFileConst.FIELD_FULL_NAME.equals(fieldName)) {
						// 氏名
						dataList.add(MospUtility.getHumansName(rs.getString(PfmHumanDao.COL_FIRST_NAME),
								rs.getString(PfmHumanDao.COL_LAST_NAME)));
						continue;
					}
					if (PlatformFileConst.FIELD_WORK_PLACE_NAME.equals(fieldName)) {
						// 勤務地名称
						dataList
							.add(workPlace.getWorkPlaceName(rs.getString(PfmHumanDao.COL_WORK_PLACE_CODE), targetDate));
						continue;
					}
					if (PlatformFileConst.FIELD_EMPLOYMENT_CONTRACT_NAME.equals(fieldName)) {
						// 雇用契約名称
						dataList.add(employmentContract
							.getContractName(rs.getString(PfmHumanDao.COL_EMPLOYMENT_CONTRACT_CODE), targetDate));
						continue;
					}
					if (PlatformFileConst.FIELD_SECTION_NAME.equals(fieldName)) {
						// 所属名称
						dataList.add(section.getSectionName(rs.getString(PfmHumanDao.COL_SECTION_CODE), targetDate));
						continue;
					}
					if (PlatformFileConst.FIELD_POSITION_NAME.equals(fieldName)) {
						// 職位名称
						dataList.add(position.getPositionName(rs.getString(PfmHumanDao.COL_POSITION_CODE), targetDate));
						continue;
					}
					if (PlatformFileConst.FIELD_SECTION_DISPLAY.equals(fieldName)) {
						// 所属表示名称
						dataList.add(section.getSectionDisplay(rs.getString(PfmHumanDao.COL_SECTION_CODE), targetDate));
						continue;
					}
					if (TimeFileConst.CALENDAR_WORKING_DAYS.equals(fieldName)) {
						// カレンダ勤務日数
						// 対象年を取得
						int targetYear = rs.getInt("calculation_year");
						// 対象月を取得
						int targetMonth = rs.getInt("calculation_month");
						// カレンダ勤務日数を取得
						int days = calendarWorkingDays.getCalendarWorkingDays(personalId, cutoffCode, targetYear,
								targetMonth);
						dataList.add(String.valueOf(days));
						continue;
					}
					// 追加項目が存在する場合
					if (doStoredLogic(TimeConst.CODE_KEY_ADD_EXPORTTABLEREFERENCEBEAN_EXPORT, personalId, targetDate,
							dataList, fieldName)) {
						continue;
					}
					// 項目追加フィールド設定
					if (addExtraField(personalId, targetDate, dataList, fieldName, rs)) {
						continue;
					}
					dataList.add(rs.getString(fieldName));
				}
				list.add(dataList.toArray(new String[0]));
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
		exportDataDao.closers();
		return list;
	}
	
	/**
	 * エクスポート時間フォーマット設定情報取得。<br>
	 * @return エクスポート時間フォーマット設定情報
	 */
	protected int getExportTimeFormat() {
		// エクスポート時間フォーマット設定情報を取得
		return TimeUtility.getExportTimeFormat(mospParams);
	}
	
	/**
	 * エクスポート時間を取得する。<br>
	 * @param minute 分
	 * @param format エクスポート時間フォーマット
	 * @return エクスポート時間
	 */
	protected String getExportTime(int minute, int format) {
		// エクスポート時間を取得
		return TimeUtility.getExportTime(mospParams, minute, format);
	}
	
	@Override
	public void setExportCode(String exportCode) {
		this.exportCode = exportCode;
	}
	
	@Override
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	
	@Override
	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}
	
	@Override
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	
	@Override
	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setCkbNeedLowerSection(int ckbNeedLowerSection) {
		this.ckbNeedLowerSection = ckbNeedLowerSection;
	}
	
	/**
	 * 追加フィールドを設定する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日付
	 * @param dataList 出力内容
	 * @param fieldName フィールド名
	 * @param rs DB結果
	 * @return 処理結果(true：追加処理実施、false：追加処理なし)
	 * @throws MospException ここでは発生しない
	 */
	protected boolean addExtraField(String personalId, Date targetDate, List<String> dataList, String fieldName,
			ResultSet rs) throws MospException {
		return false;
	}
	
}
