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
package jp.mosp.time.base;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.bean.AllowanceReferenceBeanInterface;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ApplicationReferenceSearchBeanInterface;
import jp.mosp.time.bean.ApplicationSearchBeanInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceTotalInfoBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.CutoffSearchBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestSearchBeanInterface;
import jp.mosp.time.bean.ExportTableReferenceBeanInterface;
import jp.mosp.time.bean.GoOutReferenceBeanInterface;
import jp.mosp.time.bean.HolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.HolidayHistorySearchBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayManagementSearchBeanInterface;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.HolidaySearchBeanInterface;
import jp.mosp.time.bean.ImportTableReferenceBeanInterface;
import jp.mosp.time.bean.LimitStandardReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeInfoReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayEntranceDateReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayHistorySearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayManagementSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayProportionallyReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.PaidHolidaySearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayUsageExportBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.RestReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleDateReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleSearchBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.StockHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.SubordinateFiscalSearchBeanInterface;
import jp.mosp.time.bean.SubordinateSearchBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingSearchBeanInterface;
import jp.mosp.time.bean.TotalAbsenceReferenceBeanInterface;
import jp.mosp.time.bean.TotalAllowanceReferenceBeanInterface;
import jp.mosp.time.bean.TotalLeaveReferenceBeanInterface;
import jp.mosp.time.bean.TotalOtherVacationReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeSearchBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionSearchBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternSearchBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeSearchBeanInterface;
import jp.mosp.time.report.bean.AttendanceBookBeanInterface;
import jp.mosp.time.report.bean.ScheduleBookBeanInterface;

/**
 * MosP勤怠管理参照用BeanHandlerインターフェース。
 */
public interface TimeReferenceBeanHandlerInterface {
	
	/**
	 * @return 勤怠打刻参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TimeRecordReferenceBeanInterface timeRecord() throws MospException;
	
	/**
	 * @return 勤怠データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceReferenceBeanInterface attendance() throws MospException;
	
	/**
	 * @return 勤怠データ修正情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceCorrectionReferenceBeanInterface attendanceCorrection() throws MospException;
	
	/**
	 * @return 勤怠データ休憩情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RestReferenceBeanInterface rest() throws MospException;
	
	/**
	 * @return 勤怠データ外出情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	GoOutReferenceBeanInterface goOut() throws MospException;
	
	/**
	 * @return 勤怠データ手当情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AllowanceReferenceBeanInterface allowance() throws MospException;
	
	/**
	 * @return 残業申請参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	OvertimeRequestReferenceBeanInterface overtimeRequest() throws MospException;
	
	/**
	 * @return 残業申請検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	OvertimeRequestSearchBeanInterface overtimeRequestSearch() throws MospException;
	
	/**
	 * @return 休暇申請参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayRequestReferenceBeanInterface holidayRequest() throws MospException;
	
	/**
	 * @return 休暇申請検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayRequestSearchBeanInterface holidayRequestSearch() throws MospException;
	
	/**
	 * @return 休日出勤申請参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkOnHolidayRequestReferenceBeanInterface workOnHolidayRequest() throws MospException;
	
	/**
	 * @return 休日出勤申請検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkOnHolidayRequestSearchBeanInterface workOnHolidayRequestSearch() throws MospException;
	
	/**
	 * @return 代休参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubHolidayReferenceBeanInterface subHoliday() throws MospException;
	
	/**
	 * @return 代休申請参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubHolidayRequestReferenceBeanInterface subHolidayRequest() throws MospException;
	
	/**
	 * @return 代休申請検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubHolidayRequestSearchBeanInterface subHolidayRequestSearch() throws MospException;
	
	/**
	 * @return 勤務形態変更申請参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypeChangeRequestReferenceBeanInterface workTypeChangeRequest() throws MospException;
	
	/**
	 * @return 勤務形態変更申請検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypeChangeRequestSearchBeanInterface workTypeChangeRequestSearch() throws MospException;
	
	/**
	 * @return 時差出勤申請参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	DifferenceRequestReferenceBeanInterface differenceRequest() throws MospException;
	
	/**
	 * @param targetDate 対象日
	 * @return 時差出勤申請参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	DifferenceRequestReferenceBeanInterface differenceRequest(Date targetDate) throws MospException;
	
	/**
	 * @return 時差出勤申請検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	DifferenceRequestSearchBeanInterface differenceRequestSearch() throws MospException;
	
	/**
	 * @return 部下一覧検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubordinateSearchBeanInterface subordinateSearch() throws MospException;
	
	/**
	 * @return 統計情報一覧検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubordinateFiscalSearchBeanInterface subordinateFiscalSearch() throws MospException;
	
	/**
	 * @return 勤怠集計管理参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeTransactionReferenceBeanInterface totalTimeTransaction() throws MospException;
	
	/**
	 * @return 社員勤怠集計管理参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeEmployeeTransactionReferenceBeanInterface totalTimeEmployeeTransaction() throws MospException;
	
	/**
	 * @return 勤怠集計管理検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeTransactionSearchBeanInterface totalTimeTransactionSearch() throws MospException;
	
	/**
	 * @return 勤怠集計結果検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeSearchBeanInterface totalTimeSearch() throws MospException;
	
	/**
	 * @return 勤怠集計データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeReferenceBeanInterface totalTime() throws MospException;
	
	/**
	 * @return 勤怠集計修正情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeCorrectionReferenceBeanInterface totalTimeCorrection() throws MospException;
	
	/**
	 * @return 勤怠集計特別休暇データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalLeaveReferenceBeanInterface totalLeave() throws MospException;
	
	/**
	 * @return 勤怠集計その他休暇データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalOtherVacationReferenceBeanInterface totalOtherVacation() throws MospException;
	
	/**
	 * @return 勤怠集計欠勤データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalAbsenceReferenceBeanInterface totalAbsence() throws MospException;
	
	/**
	 * @return 勤怠集計手当データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalAllowanceReferenceBeanInterface totalAllowance() throws MospException;
	
	/**
	 * @return インポートテーブル参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportTableReferenceBeanInterface importTable() throws MospException;
	
	/**
	 * @return エクスポートテーブル参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ExportTableReferenceBeanInterface exportTable() throws MospException;
	
	/**
	 * @return 休暇種別管理参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayReferenceBeanInterface holiday() throws MospException;
	
	/**
	 * @return 休暇種別管理検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidaySearchBeanInterface holidaySearch() throws MospException;
	
	/**
	 * @return 休暇確認検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayManagementSearchBeanInterface holidayManagementSearch() throws MospException;
	
	/**
	 * @return 休暇データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayDataReferenceBeanInterface holidayData() throws MospException;
	
	/**
	 * @return 休暇付与検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayHistorySearchBeanInterface holidayHistorySearch() throws MospException;
	
	/**
	 * @return 有給休暇確認検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayManagementSearchBeanInterface paidHolidayManagementSearch() throws MospException;
	
	/**
	 * @return 有給休暇手動付与検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayHistorySearchBeanInterface paidHolidayHistorySearch() throws MospException;
	
	/**
	 * @return 有給休暇トランザクション参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayTransactionReferenceBeanInterface paidHolidayTransaction() throws MospException;
	
	/**
	 * @return 有給休暇データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayDataReferenceBeanInterface paidHolidayData() throws MospException;
	
	/**
	 * @return 有給休暇データ検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayDataSearchBeanInterface paidHolidayDataSearch() throws MospException;
	
	/**
	 * @return ストック休暇トランザクション参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	StockHolidayTransactionReferenceBeanInterface stockHolidayTransaction() throws MospException;
	
	/**
	 * @return ストック休暇データ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	StockHolidayDataReferenceBeanInterface stockHolidayData() throws MospException;
	
	/**
	 * @return 勤怠設定参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TimeSettingReferenceBeanInterface timeSetting() throws MospException;
	
	/**
	 * @return 限度基準参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	LimitStandardReferenceBeanInterface limitStandard() throws MospException;
	
	/**
	 * @return 勤怠設定検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TimeSettingSearchBeanInterface timeSettingSearch() throws MospException;
	
	/**
	 * @return 勤務形態管理参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypeReferenceBeanInterface workType() throws MospException;
	
	/**
	 * @return 勤務形態管理検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypeSearchBeanInterface workTypeSearch() throws MospException;
	
	/**
	 * 勤務形態詳細参照クラスを取得する。
	 * @return 勤務形態詳細参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypeItemReferenceBeanInterface workTypeItem() throws MospException;
	
	/**
	 * @return 勤務形態パターン参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypePatternReferenceBeanInterface workTypePattern() throws MospException;
	
	/**
	 * @return 勤務形態パターン検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypePatternSearchBeanInterface workTypePatternSearch() throws MospException;
	
	/**
	 * @return 勤務パターン項目参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypePatternItemReferenceBeanInterface workTypePatternItem() throws MospException;
	
	/**
	 * @return 有給休暇設定参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayReferenceBeanInterface paidHoliday() throws MospException;
	
	/**
	 * @return 有給休暇比例付与参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayProportionallyReferenceBeanInterface paidHolidayProportionally() throws MospException;
	
	/**
	 * @return 有給休暇初年度付与参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayFirstYearReferenceBeanInterface paidHolidayFirstYear() throws MospException;
	
	/**
	 * @return 有給休暇自動付与(標準日)参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayPointDateReferenceBeanInterface paidHolidayPointDate() throws MospException;
	
	/**
	 * @return 有給休暇自動付与(入社日)参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayEntranceDateReferenceBeanInterface paidHolidayEntranceDate() throws MospException;
	
	/**
	 * @return ストック休暇設定参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	StockHolidayReferenceBeanInterface stockHoliday() throws MospException;
	
	/**
	 * @return 有給休暇設定検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidaySearchBeanInterface paidHolidaySearch() throws MospException;
	
	/**
	 * @return カレンダ管理参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ScheduleReferenceBeanInterface schedule() throws MospException;
	
	/**
	 * @return カレンダ日参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ScheduleDateReferenceBeanInterface scheduleDate() throws MospException;
	
	/**
	 * @return カレンダ管理検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ScheduleSearchBeanInterface scheduleSearch() throws MospException;
	
	/**
	 * @return 締日管理参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	CutoffReferenceBeanInterface cutoff() throws MospException;
	
	/**
	 * @return 締日管理検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	CutoffSearchBeanInterface cutoffSearch() throws MospException;
	
	/**
	 * @return 設定適用管理参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApplicationReferenceBeanInterface application() throws MospException;
	
	/**
	 * @return 設定適用・締日管理参照画面検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApplicationReferenceSearchBeanInterface applicationReferenceSearch() throws MospException;
	
	/**
	 * @return 設定適用管理検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApplicationSearchBeanInterface applicationSearch() throws MospException;
	
	/**
	 * @return 有給休暇情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayInfoReferenceBeanInterface paidHolidayInfo() throws MospException;
	
	/**
	 * @return 休暇情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayInfoReferenceBeanInterface holidayInfo() throws MospException;
	
	/**
	 * @return 残業情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	OvertimeInfoReferenceBeanInterface overtimeInfo() throws MospException;
	
	/**
	 * @return 承認情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalInfoReferenceBeanInterface approvalInfo() throws MospException;
	
	/**
	 * @return 有給休暇付与参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayGrantReferenceBeanInterface paidHolidayGrant() throws MospException;
	
	/**
	 * @return 振替休日データ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubstituteReferenceBeanInterface substitute() throws MospException;
	
	/**
	 * @return 勤怠一覧情報参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceListReferenceBeanInterface attendanceList() throws MospException;
	
	/**
	 * @return 予定簿作成インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ScheduleBookBeanInterface scheduleBook() throws MospException;
	
	/**
	 * @return 出勤簿作成インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceBookBeanInterface attendanceBook() throws MospException;
	
	/**
	 * @return 有給休暇取得状況確認情報出力処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayUsageExportBeanInterface paidHolidayUsageExport() throws MospException;
	
	/**
	 * @return 締日ユーティリティインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	CutoffUtilBeanInterface cutoffUtil() throws MospException;
	
	/**
	 * @return 申請ユーティリティインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RequestUtilBeanInterface requestUtil() throws MospException;
	
	/**
	 * @return カレンダユーティリティインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ScheduleUtilBeanInterface scheduleUtil() throws MospException;
	
	/**
	 * @return 日々勤怠の集計クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceTotalInfoBeanInterface attendanceTotalInfo() throws MospException;
	
	/**
	 * 有給休暇残数取得処理を取得する。<br>
	 * @return 有給休暇残数取得処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayRemainBeanInterface paidHolidayRemain() throws MospException;
	
	/**
	 * 勤怠管理マスタ参照処理を取得する。<br>
	 * @return 勤怠管理マスタ参照処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TimeMasterBeanInterface master() throws MospException;
	
}
