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
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.time.bean.AfterApplyAttendancesExecuteBeanInterface;
import jp.mosp.time.bean.AllowanceRegistBeanInterface;
import jp.mosp.time.bean.ApplicationRegistBeanInterface;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionRegistBeanInterface;
import jp.mosp.time.bean.AttendanceListRegistBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffRegistBeanInterface;
import jp.mosp.time.bean.DifferenceRequestRegistBeanInterface;
import jp.mosp.time.bean.GoOutRegistBeanInterface;
import jp.mosp.time.bean.HolidayDataRegistBeanInterface;
import jp.mosp.time.bean.HolidayRegistBeanInterface;
import jp.mosp.time.bean.HolidayRequestExecuteBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.LimitStandardRegistBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayEntranceDateRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayFirstYearRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayPointDateRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayProportionallyRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionRegistBeanInterface;
import jp.mosp.time.bean.RestRegistBeanInterface;
import jp.mosp.time.bean.ScheduleDateRegistBeanInterface;
import jp.mosp.time.bean.ScheduleRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayTransactionRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.TimeRecordBeanInterface;
import jp.mosp.time.bean.TimeSettingRegistBeanInterface;
import jp.mosp.time.bean.TotalAbsenceRegistBeanInterface;
import jp.mosp.time.bean.TotalAllowanceRegistBeanInterface;
import jp.mosp.time.bean.TotalLeaveRegistBeanInterface;
import jp.mosp.time.bean.TotalOtherVacationRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionRegistBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeItemRegistBeanInterface;
import jp.mosp.time.bean.WorkTypePatternItemRegistBeanInterface;
import jp.mosp.time.bean.WorkTypePatternRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeRegistBeanInterface;

/**
 * MosP勤怠管理用BeanHandlerインターフェース。<br>
 */
public interface TimeBeanHandlerInterface {
	
	/**
	 * 勤怠データ登録クラスを取得する。
	 * @return 勤怠データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceRegistBeanInterface attendanceRegist() throws MospException;
	
	/**
	 * 勤怠データ修正情報登録クラスを取得する。
	 * @return 勤怠データ修正情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceCorrectionRegistBeanInterface attendanceCorrectionRegist() throws MospException;
	
	/**
	 * 勤怠データ休憩情報登録クラスを取得する。
	 * @return 勤怠データ休憩情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RestRegistBeanInterface restRegist() throws MospException;
	
	/**
	 * 勤怠データ外出情報登録クラスを取得する。
	 * @return 勤怠データ外出情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	GoOutRegistBeanInterface goOutRegist() throws MospException;
	
	/**
	 * 勤怠データ手当情報登録クラスを取得する。
	 * @return 勤怠データ手当情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AllowanceRegistBeanInterface allowanceRegist() throws MospException;
	
	/**
	 * 勤怠トランザクション登録クラスを取得する。
	 * @return 勤怠トランザクション登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceTransactionRegistBeanInterface attendanceTransactionRegist() throws MospException;
	
	/**
	 * 残業申請登録クラスを取得する。
	 * @return 残業申請登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	OvertimeRequestRegistBeanInterface overtimeRequestRegist() throws MospException;
	
	/**
	 * 休暇申請登録クラスを取得する。
	 * @return 休暇申請登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayRequestRegistBeanInterface holidayRequestRegist() throws MospException;
	
	/**
	 * 休暇申請実行処理を取得する。<br>
	 * @return 休暇申請実行処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayRequestExecuteBeanInterface holidayRequestExecute() throws MospException;
	
	/**
	 * 休日出勤申請登録クラスを取得する。
	 * @return 休日出勤申請登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkOnHolidayRequestRegistBeanInterface workOnHolidayRequestRegist() throws MospException;
	
	/**
	 * 代休データ登録クラスを取得する。
	 * @return 代休データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubHolidayRegistBeanInterface subHolidayRegist() throws MospException;
	
	/**
	 * 代休申請登録クラスを取得する。
	 * @return 代休申請登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubHolidayRequestRegistBeanInterface subHolidayRequestRegist() throws MospException;
	
	/**
	 * 勤務形態変更申請クラスを取得する。
	 * @return 勤務形態変更申請登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypeChangeRequestRegistBeanInterface workTypeChangeRequestRegist() throws MospException;
	
	/**
	 * 時差出勤申請登録クラスを取得する。
	 * @return 時差出勤申請登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	DifferenceRequestRegistBeanInterface differenceRequestRegist() throws MospException;
	
	/**
	 * 勤怠集計管理登録クラスを取得する。
	 * @return 勤怠集計管理登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeTransactionRegistBeanInterface totalTimeTransactionRegist() throws MospException;
	
	/**
	 * 社員勤怠集計管理登録クラスを取得する。
	 * @return 社員勤怠集計管理登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeEmployeeTransactionRegistBeanInterface totalTimeEmployeeTransactionRegist() throws MospException;
	
	/**
	 * 勤怠集計データ登録クラスを取得する。
	 * @return 勤怠集計データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeRegistBeanInterface totalTimeRegist() throws MospException;
	
	/**
	 * 勤怠集計修正情報登録クラスを取得する。
	 * @return 勤怠集計修正情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeCorrectionRegistBeanInterface totalTimeCorrectionRegist() throws MospException;
	
	/**
	 * 勤怠集計特別休暇データ登録クラスを取得する。
	 * @return 勤怠集計特別休暇データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalLeaveRegistBeanInterface totalLeaveRegist() throws MospException;
	
	/**
	 * 勤怠集計その他休暇データ登録クラスを取得する。
	 * @return 勤怠集計その他休暇データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalOtherVacationRegistBeanInterface totalOtherVacationRegist() throws MospException;
	
	/**
	 * 勤怠集計欠勤データ登録クラスを取得する。
	 * @return 勤怠集計欠勤データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalAbsenceRegistBeanInterface totalAbsenceRegist() throws MospException;
	
	/**
	 * 勤怠集計手当データ登録クラスを取得する。
	 * @return 勤怠集計手当データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalAllowanceRegistBeanInterface totalAllowanceRegist() throws MospException;
	
	/**
	 * 休暇種別管理登録クラスを取得する。
	 * @return 休暇種別管理登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayRegistBeanInterface holidayRegist() throws MospException;
	
	/**
	 * 休暇データ登録クラスを取得する。
	 * @return 休暇データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HolidayDataRegistBeanInterface holidayDataRegist() throws MospException;
	
	/**
	 * 有給休暇データ登録クラスを取得する。
	 * @return 有給休暇データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayDataRegistBeanInterface paidHolidayDataRegist() throws MospException;
	
	/**
	 * 有給休暇手動付与登録クラスを取得する。
	 * @return 有給休暇手動付与登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayTransactionRegistBeanInterface paidHolidayTransactionRegist() throws MospException;
	
	/**
	 * ストック休暇データ登録クラスを取得する。
	 * @return ストック休暇データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	StockHolidayDataRegistBeanInterface stockHolidayDataRegist() throws MospException;
	
	/**
	 * ストック休暇トランザクション登録クラスを取得する。
	 * @return ストック休暇トランザクション登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	StockHolidayTransactionRegistBeanInterface stockHolidayTransactionRegist() throws MospException;
	
	/**
	 * 勤怠設定登録クラスを取得する。
	 * @return 勤怠設定登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TimeSettingRegistBeanInterface timeSettingRegist() throws MospException;
	
	/**
	 * 限度基準登録クラスを取得する。
	 * @return 限度基準登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	LimitStandardRegistBeanInterface limitStandardRegist() throws MospException;
	
	/**
	 * 勤務形態管理登録クラスを取得する。
	 * @return 勤務形態管理登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypeRegistBeanInterface workTypeRegist() throws MospException;
	
	/**
	 * 勤務形態詳細登録クラスを取得する。
	 * @return 勤務形態詳細登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypeItemRegistBeanInterface workTypeItemRegist() throws MospException;
	
	/**
	 * 勤務形態パターン登録クラスを取得する。
	 * @return 勤務形態パターン登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypePatternRegistBeanInterface workTypePatternRegist() throws MospException;
	
	/**
	 * 勤務形態パターン項目登録クラスを取得する。
	 * @return 勤務形態パターン項目登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkTypePatternItemRegistBeanInterface workTypePatternItemRegist() throws MospException;
	
	/**
	 * 有給休暇設定登録クラスを取得する。
	 * @return 有給休暇設定登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayRegistBeanInterface paidHolidayRegist() throws MospException;
	
	/**
	 * 有給休暇比例付与登録クラスを取得する。
	 * @return 有給休暇比例付与登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayProportionallyRegistBeanInterface paidHolidayProportionallyRegist() throws MospException;
	
	/**
	 * 有給休暇初年度付与登録クラスを取得する。
	 * @return 有給休暇初年度付与登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayFirstYearRegistBeanInterface paidHolidayFirstYearRegist() throws MospException;
	
	/**
	 * 有給休暇自動付与(標準日)登録クラスを取得する。
	 * @return 有給休暇自動付与(標準日)登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayPointDateRegistBeanInterface paidHolidayPointDateRegist() throws MospException;
	
	/**
	 * 有給休暇自動付与(入社日)登録クラスを取得する。
	 * @return 有給休暇自動付与(入社日)登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayEntranceDateRegistBeanInterface paidHolidayEntranceDateRegist() throws MospException;
	
	/**
	 * ストック休暇設定登録クラスを取得する。
	 * @return ストック休暇設定登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	StockHolidayRegistBeanInterface stockHolidayRegist() throws MospException;
	
	/**
	 * カレンダ管理登録クラスを取得する。
	 * @return カレンダ管理登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ScheduleRegistBeanInterface scheduleRegist() throws MospException;
	
	/**
	 * カレンダ日登録クラスを取得する。
	 * @return カレンダ日登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ScheduleDateRegistBeanInterface scheduleDateRegist() throws MospException;
	
	/**
	 * 締日管理登録クラスを取得する。
	 * @return 締日管理登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	CutoffRegistBeanInterface cutoffRegist() throws MospException;
	
	/**
	 * 設定適用管理登録クラスを取得する。
	 * @return 設定適用管理登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApplicationRegistBeanInterface applicationRegist() throws MospException;
	
	/**
	 * 勤怠集計クラスを取得する。
	 * @return 勤怠集計クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TotalTimeCalcBeanInterface totalTimeCalc() throws MospException;
	
	/**
	 * 振替休日データ登録クラスを取得する。
	 * @return 振替休日データ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubstituteRegistBeanInterface substituteRegist() throws MospException;
	
	/**
	 * 勤怠一覧登録クラスを取得する。
	 * @return 勤怠一覧登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceListRegistBeanInterface attendanceListRegist() throws MospException;
	
	/**
	 * 勤怠一覧登録クラスを取得する。
	 * @param targetDate 対象日
	 * @return 勤怠一覧登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceListRegistBeanInterface attendanceListRegist(Date targetDate) throws MospException;
	
	/**
	 * 勤怠自動計算クラスを取得する。
	 * @return 勤怠自動計算クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceCalcBeanInterface attendanceCalc() throws MospException;
	
	/**
	 * 勤怠自動計算クラスを取得する。
	 * @param targetDate 対象日
	 * @return 勤怠自動計算クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AttendanceCalcBeanInterface attendanceCalc(Date targetDate) throws MospException;
	
	/**
	 * 勤怠関連申請承認クラスを取得する。
	 * @return 勤怠関連申請承認クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TimeApprovalBeanInterface timeApproval() throws MospException;
	
	/**
	 * 有給休暇付与登録クラスを取得する。
	 * @return 有給休暇付与登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayGrantRegistBeanInterface paidHolidayGrantRegist() throws MospException;
	
	/**
	 * 有給休暇データ付与クラスを取得する。
	 * @return 有給休暇データ付与クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PaidHolidayDataGrantBeanInterface paidHolidayDataGrant() throws MospException;
	
	/**
	 * ストック休暇データ付与クラスを取得する。
	 * @return ストック休暇データ付与クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	StockHolidayDataGrantBeanInterface stockHolidayDataGrant() throws MospException;
	
	/**
	 * 休暇申請インポートクラスを取得する。<br>
	 * @return 休暇申請インポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportBeanInterface holidayRequestImport() throws MospException;
	
	/**
	 * 振出・休出申請インポートクラスを取得する。<br>
	 * @return 振出・休出申請インポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportBeanInterface workOnHolidayRequestImport() throws MospException;
	
	/**
	 * @return 勤怠申請後処理群実行処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AfterApplyAttendancesExecuteBeanInterface afterApplyAttendancesExecute() throws MospException;
	
	/**
	 * @return 打刻処理クラスを取得
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TimeRecordBeanInterface timeRecord() throws MospException;
	
}
