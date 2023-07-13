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

import java.math.RoundingMode;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.HtmlUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.CheckAvailableBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypePatternItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;
import jp.mosp.time.input.action.ApprovalHistoryAction;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * MosP勤怠管理におけるActionの基本機能を提供する。<br>
 */
public abstract class TimeAction extends PlatformAction {
	
	/**
	 * ファイルパス(MosP勤怠管理用JavaScript)。
	 */
	public static final String					PATH_TIME_JS	= "/pub/time/base/js/time.js";
	
	/**
	 * ファイルパス(MosP勤怠管理用JavaScript)。
	 */
	public static final String					PATH_TIME_CSS	= "/pub/time/base/css/time.css";
	
	/**
	 * MosP勤怠管理用BeanHandler。
	 */
	protected TimeBeanHandlerInterface			time;
	
	/**
	 * MosP勤怠管理参照用BeanHandler。
	 */
	protected TimeReferenceBeanHandlerInterface	timeReference;
	
	
	@Override
	protected void addBaseJsCssFiles() {
		super.addBaseJsCssFiles();
		// MosP勤怠管理用JavaScriptファイル追加
		mospParams.addJsFile(PATH_TIME_JS);
		// MosP勤怠管理用CSSファイル追加
		mospParams.addCssFile(PATH_TIME_CSS);
	}
	
	/**
	 * MosP勤怠管理用BeanHandlerを取得する。<br>
	 * @return MosP勤怠管理用BeanHandler
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected TimeBeanHandlerInterface time() throws MospException {
		// MosP勤怠管理用BeanHandler存在確認
		if (time != null) {
			return time;
		}
		// MosP勤怠管理用BeanHandler取得
		time = (TimeBeanHandlerInterface)createHandler(TimeBeanHandlerInterface.class);
		return time;
	}
	
	/**
	 * MosP勤怠管理参照用BeanHandlerを取得する。<br>
	 * @return MosP勤怠管理参照用BeanHandler
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected TimeReferenceBeanHandlerInterface timeReference() throws MospException {
		// MosP勤怠管理参照用BeanHandler存在確認
		if (timeReference != null) {
			return timeReference;
		}
		// MosP勤怠管理参照用BeanHandler取得
		timeReference = (TimeReferenceBeanHandlerInterface)createHandler(TimeReferenceBeanHandlerInterface.class);
		return timeReference;
	}
	
	/**
	 * VOから有効日(編集)を取得する。<br>
	 * @return 有効日(編集)
	 */
	protected Date getEditActivateDate() {
		// VO取得
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtEditActivateYear(), vo.getTxtEditActivateMonth(), vo.getTxtEditActivateDay());
	}
	
	/**
	 * VOから有効日(検索)を取得する。<br>
	 * @return 有効日(検索)
	 */
	protected Date getSearchActivateDate() {
		// VO準備
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtSearchActivateYear(), vo.getTxtSearchActivateMonth(), vo.getTxtSearchActivateDay());
	}
	
	/**
	 * VOから有効日(一括更新)を取得する。<br>
	 * @return 有効日(検索)
	 */
	protected Date getUpdateActivateDate() {
		// VO準備
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtUpdateActivateYear(), vo.getTxtUpdateActivateMonth(), vo.getTxtUpdateActivateDay());
	}
	
	/**
	 * 検索有効日を設定する。<br>
	 * @param date 設定する日付
	 */
	protected void setSearchActivateDate(Date date) {
		// VO取得
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 設定日付確認
		if (date == null) {
			// nullの場合
			vo.setTxtSearchActivateYear("");
			vo.setTxtSearchActivateMonth("");
			vo.setTxtSearchActivateDay("");
		}
		// 検索有効日設定
		vo.setTxtSearchActivateYear(getStringYear(date));
		vo.setTxtSearchActivateMonth(getStringMonth(date));
		vo.setTxtSearchActivateDay(getStringDay(date));
	}
	
	/**
	 * 年プルダウン取得
	 * @param year 年
	 * @return 年プルダウン用文字列配列
	 */
	protected String[][] getYearArray(int year) {
		return getYearArray(year, 3, 1);
	}
	
	/**
	 * 年プルダウンを取得する。<br>
	 * 勤怠集計情報が存在する最小の年からシステム年の翌年までを含める。<br>
	 * @return 年プルダウン
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getYearArray() throws MospException {
		// システム年を取得
		int year = DateUtility.getYear(getSystemDate());
		// 勤怠集計情報が存在する年を取得
		int minYear = timeReference().totalTime().getMinYear();
		// 過去年数を準備
		int former = year - minYear;
		// 勤怠集計情報が存在する最小の年が0であるかシステム年以降である場合
		if (minYear == 0 || former < 1) {
			// 過去年数は1
			former = 1;
		}
		// 年プルダウンを取得
		return getYearArray(year, former, 1);
	}
	
	/**
	 * 年プルダウン取得
	 * @param year 基準年
	 * @param former 過去
	 * @param further 未来
	 * @return 年プルダウン用文字列配列
	 */
	protected String[][] getYearArray(int year, int former, int further) {
		int y = year - former;
		String[][] aryYear = new String[former + further + 1][2];
		for (int i = 0; i < aryYear.length; i++) {
			String stringYear = Integer.toString(y + i);
			aryYear[i][0] = stringYear;
			aryYear[i][1] = stringYear;
		}
		return aryYear;
	}
	
	/**
	 * 月プルダウン取得
	 * @return 月プルダウン用文字列配列
	 */
	public String[][] getMonthArray() {
		return getMonthArray(false);
	}
	
	/**
	 * 月プルダウン取得
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return 月プルダウン用文字列配列
	 */
	public String[][] getMonthArray(boolean needBlank) {
		int month = 12;
		// 空白行要否確認
		if (needBlank == false) {
			String[][] aryMonth = new String[month][2];
			for (int i = 0; i < month; i++) {
				// リスト長分の配列を返す
				aryMonth[i][0] = String.valueOf(i + 1);
				aryMonth[i][1] = String.valueOf(i + 1);
			}
			return aryMonth;
		}
		int months = 13;
		String[][] aryMonth = new String[months][2];
		// 空白行設定
		aryMonth[0][0] = "";
		aryMonth[0][1] = "";
		for (int i = 0; i < month; i++) {
			aryMonth[i + 1][0] = String.valueOf(i + 1);
			aryMonth[i + 1][1] = String.valueOf(i + 1);
		}
		return aryMonth;
	}
	
	/**
	 * 日プルダウン取得
	 * @return 日プルダウン用文字列配列
	 */
	public String[][] getDayArray() {
		return getDayArray(false);
	}
	
	/**
	 * 日プルダウン取得
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return 日プルダウン用文字列配列
	 */
	public String[][] getDayArray(boolean needBlank) {
		return getDayArray(TimeConst.DATE_MONTH_MAX_DAY, needBlank);
	}
	
	/**
	 * 日プルダウン取得
	 * @param day プルダウンの最終日
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return 日プルダウン用文字列配列
	 */
	public String[][] getDayArray(int day, boolean needBlank) {
		if (day < 1) {
			// 日が1未満の場合は1とする
			return getDayArray(1, needBlank);
		} else if (day > TimeConst.DATE_MONTH_MAX_DAY) {
			// 日が31を超える場合は31とする
			return getDayArray(TimeConst.DATE_MONTH_MAX_DAY, needBlank);
		}
		// 空白行要否確認
		if (needBlank) {
			String[][] aryDay = new String[day + 1][2];
			// 空白行設定
			aryDay[0][0] = "";
			aryDay[0][1] = "";
			for (int i = 1; i <= day; i++) {
				String stringDay = Integer.toString(i);
				aryDay[i][0] = stringDay;
				aryDay[i][1] = stringDay;
			}
			return aryDay;
		}
		String[][] aryDay = new String[day][2];
		for (int i = 0; i < day; i++) {
			String stringDay = Integer.toString(i + 1);
			aryDay[i][0] = stringDay;
			aryDay[i][1] = stringDay;
		}
		return aryDay;
	}
	
	/**
	 * 時プルダウン取得
	 * @param maxHour 最大時間
	 * @param needStart (true：0～、false：1～)
	 * @return 0 又は 1 から maxHour までの時プルダウン用文字列配列
	 */
	public String[][] getHourArray(int maxHour, boolean needStart) {
		if (maxHour < 0) {
			// 最大時間が0未満の場合は0とする
			return getHourArray(0, true);
		}
		if (needStart) {
			String[][] aryHour = new String[++maxHour][2];
			for (int i = 0; i < aryHour.length; i++) {
				String stringHour = Integer.toString(i);
				aryHour[i][0] = stringHour;
				aryHour[i][1] = stringHour;
			}
			return aryHour;
		} else {
			String[][] aryHour = new String[maxHour][2];
			for (int i = 0; i < aryHour.length; i++) {
				String stringHour = Integer.toString(i + 1);
				aryHour[i][0] = stringHour;
				aryHour[i][1] = stringHour;
			}
			return aryHour;
		}
	}
	
	/**
	 * 時プルダウン取得
	 * @return 0から23までの時プルダウン用文字列配列
	 */
	public String[][] getHourArray() {
		return getHourArray(23, true);
	}
	
	/**
	 * 分プルダウン取得
	 * @param interval 間隔
	 * @return 分プルダウン用文字列配列
	 */
	public String[][] getMinuteArray(int interval) {
		if (interval <= 0) {
			// 間隔が0以下の場合は1分間隔とする
			return getMinuteArray(1);
		} else if (interval > TimeConst.CODE_DEFINITION_HOUR) {
			// 間隔が60を超える場合は60分間隔とする(プルダウンに表示されるのは「0」のみ)
			return getMinuteArray(TimeConst.CODE_DEFINITION_HOUR);
		}
		String[][] aryMinute = new String[TimeConst.CODE_DEFINITION_HOUR / interval][2];
		for (int i = 0; i < aryMinute.length; i++) {
			String stringMinute = Integer.toString(i * interval);
			aryMinute[i][0] = stringMinute;
			aryMinute[i][1] = stringMinute;
		}
		return aryMinute;
	}
	
	/**
	 * int型の時間をint型の時間(時)に変換
	 * @param time 時間
	 * @return 時間(時)
	 */
	protected int convIntegerTimeToIntegerHour(int time) {
		return TimeUtility.getHours(time);
	}
	
	/**
	 * int型の時間をString型の時間（時）に変換
	 * @param time 時刻
	 * @return 時間（時）
	 */
	protected String convIntegerTimeToStringHour(int time) {
		return String.valueOf(convIntegerTimeToIntegerHour(time));
	}
	
	/**
	 * int型の時間をint型の時間(分)に変換
	 * @param time 時間
	 * @return 時間(分)
	 */
	protected int convIntegerTimeToIntegerMinute(int time) {
		return TimeUtility.getMinutes(time);
	}
	
	/**
	 * int型の時間をString型の時間（分）に変換
	 * @param time 時刻
	 * @return 時間（分）
	 */
	protected String convIntegerTimeToStringMinutes(int time) {
		int minute = convIntegerTimeToIntegerMinute(time);
		StringBuffer sb = new StringBuffer();
		if (minute < 10) {
			sb.append(0);
		}
		sb.append(minute);
		return sb.toString();
	}
	
	/**
	 * 対象個人IDの対象日における人事情報を取得し、VOに設定する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void setEmployeeInfo(String personalId, Date targetDate) throws MospException {
		// VO取得
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 個人IDを設定
		vo.setPersonalId(personalId);
		// 対象日を設定
		vo.setTargetDate(targetDate);
		// 人事情報取得及び確認
		HumanDtoInterface humanDto = getHumanInfo(personalId, targetDate);
		if (humanDto == null) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorEmployeeHistoryNotExist(mospParams, targetDate);
			return;
		}
		// 社員コードを設定
		vo.setLblEmployeeCode(humanDto.getEmployeeCode());
		// 社員名を設定
		vo.setLblEmployeeName(getLastFirstName(humanDto.getLastName(), humanDto.getFirstName()));
		// 所属名を設定
		vo.setLblSectionName(reference().section().getSectionName(humanDto.getSectionCode(), targetDate));
		if (reference().section().useDisplayName()) {
			// 所属表示名称を設定
			vo.setLblSectionName(reference().section().getSectionDisplay(humanDto.getSectionCode(), targetDate));
		}
	}
	
	/**
	 * 対象年を取得する。<br>
	 * 画面遷移用のパラメータをMosP処理情報から取得する。<br>
	 * @return 対象年
	 */
	protected int getTargetYear() {
		return (Integer)mospParams.getGeneralParam(TimeConst.PRM_TARGET_YEAR);
	}
	
	/**
	 * 対象月を取得する。<br>
	 * 画面遷移用のパラメータをMosP処理情報から取得する。<br>
	 * @return 対象月
	 */
	protected int getTargetMonth() {
		return (Integer)mospParams.getGeneralParam(TimeConst.PRM_TARGET_MONTH);
	}
	
	/**
	 * 対象時刻を取得する。<br>
	 * 画面遷移用のパラメータをMosP処理情報から取得する。<br>
	 * @return 対象時刻
	 */
	protected Date getTargetTime() {
		return (Date)mospParams.getGeneralParam(TimeConst.PRM_TARGET_TIME);
	}
	
	/**
	 * 対象ワークフロー番号を取得する。<br>
	 * 画面遷移用のパラメータをMosP処理情報から取得する。<br>
	 * @return 対象ワークフロー番号
	 */
	protected long getTargetWorkflow() {
		return (Long)mospParams.getGeneralParam(TimeConst.PRM_TARGET_WORKFLOW);
	}
	
	/**
	 * 対象年を設定する。<br>
	 * 画面遷移用のパラメータをMosP処理情報に設定する。<br>
	 * @param targetYear 対象年
	 */
	protected void setTargetYear(int targetYear) {
		mospParams.addGeneralParam(TimeConst.PRM_TARGET_YEAR, targetYear);
	}
	
	/**
	 * 対象月を設定する。<br>
	 * 画面遷移用のパラメータをMosP処理情報に設定する。<br>
	 * @param targetMonth 対象月
	 */
	protected void setTargetMonth(int targetMonth) {
		mospParams.addGeneralParam(TimeConst.PRM_TARGET_MONTH, targetMonth);
	}
	
	/**
	 * 対象ワークフロー番号を設定する。<br>
	 * 画面遷移用のパラメータをMosP処理情報に設定する。<br>
	 * @param targetWorkflow 対象ワークフロー番号
	 */
	protected void setTargetWorkflow(long targetWorkflow) {
		mospParams.addGeneralParam(TimeConst.PRM_TARGET_WORKFLOW, targetWorkflow);
	}
	
	/**
	 * リクエストされた譲渡汎用区分を取得する。
	 * @return 譲渡汎用区分
	 */
	@Override
	protected String getTransferredType() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_TYPE);
	}
	
	/**
	 * リクエストされた譲渡汎用月を取得する。
	 * @return 譲渡汎用月
	 */
	protected String getTransferredMonth() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_MONTH);
	}
	
	/**
	 * リクエストされた譲渡汎用区分を取得する。
	 * @return 譲渡汎用コード
	 */
	protected String getTransferredGenericCode() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_GENERIC_CODE);
	}
	
	/**
	 * 数字フォーマット
	 * @param number 数字
	 * @param fraction 小数点以下最大桁数
	 * @return 数字文字列
	 */
	protected String getNumberString(Double number, int fraction) {
		// 数値文字列を取得
		return TimeUtility.getStringNumberOrHyphen(mospParams, number, fraction, false);
	}
	
	/**
	 * 数値文字列を取得する。<br>
	 * @param number   数値
	 * @param fraction 小数点以下最大桁数
	 * @return 数値(文字列)
	 */
	protected String getNumberString(double number, int fraction) {
		// 数値文字列を取得
		return TimeUtility.getStringNumber(mospParams, number, fraction, RoundingMode.HALF_UP);
	}
	
	/**
	 * 時分形式で出力する。
	 * @param time 分
	 * @return HH.MM
	 */
	protected String toTimeDotFormatString(Integer time) {
		// 時間文字列(時.分)を取得
		return TimeUtility.getStringPeriodTimeOrHyphen(mospParams, time, false);
	}
	
	/**
	 * 時分形式で出力する。
	 * @param time 分
	 * @return HH時間MM分
	 */
	protected String getTimeTimeFormat(int time) {
		// 時間文字列(時時間分分)を取得
		return TimeUtility.getStringJpTime(mospParams, time);
	}
	
	/**
	 * 時分形式で出力する。
	 * @param date1 前時間
	 * @param date2 後時間
	 * @param standardDate 基準日付
	 * @return hh:mm～hh:mm
	 */
	protected String getTimeWaveFormat(Date date1, Date date2, Date standardDate) {
		// 日付(期間)を文字列(HH:mm～HH:mm)に変換
		return TransStringUtility.getHourColonMinuteTerm(mospParams, date1, date2, standardDate);
	}
	
	/**
	 * 時分形式で出力する。
	 * @param time 分
	 * @return HH:MM
	 */
	protected String getTimeColonFormat(int time) {
		// 時間文字列(時:分)を取得
		return TimeUtility.getStringColonTime(mospParams, time);
	}
	
	/**
	 * 試算成功メッセージの設定。
	 */
	protected void addCalcMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, mospParams.getName("TrialCalc"));
	}
	
	/**
	 * 下書成功メッセージの設定。
	 */
	protected void addDraftMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.draft(mospParams));
	}
	
	/**
	 * 申請成功メッセージの設定。
	 */
	protected void addAppliMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.application(mospParams));
	}
	
	/**
	 * 承認解除申請成功メッセージの設定。
	 */
	protected void addCancellAppliMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, TimeNamingUtility.cancellationRequest(mospParams));
	}
	
	/**
	 * 取下成功メッセージの設定。
	 */
	protected void addTakeDownMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.withdraw(mospParams));
	}
	
	/**
	 * 残業申請の区分名設定
	 * @param time 区分
	 * @return 区分名
	 */
	protected String getOvertimeTypeName(int time) {
		if (1 == time) {
			return mospParams.getName("Work", "Ahead");
		} else {
			return mospParams.getName("Work", "Later");
		}
	}
	
	/**
	 * 代休種別略称を取得する。<br>
	 * @param subHolidayType 代休種別
	 * @return 文字列に変換した休暇区分
	 */
	protected String setWorkDateSubHolidayType(int subHolidayType) {
		// 代休種別略称を取得
		return TimeRequestUtility.getSubHolidayTypeAbbr(subHolidayType, mospParams);
	}
	
	/**
	 * ワークフローの状態と段階からワークフロー状態(表示用)を取得する。<br>
	 * @param status ワークフロー状態
	 * @param stage  ワークフロー段階
	 * @return ワークフロー状態(表示用)
	 */
	public String getStatusStageValueView(String status, int stage) {
		try {
			return reference().workflowIntegrate().getWorkflowStatus(status, stage);
		} catch (MospException e) {
			return status;
		}
	}
	
	/**
	 * classで使用する文字列の設定。<br>
	 * @param dateTimeNum 一覧表示する日 or 時間
	 * @return dateTimeNum判定後の文字色設定タグ
	 */
	protected String setHistoryDateTimeColor(int dateTimeNum) {
		// 初期値に文字色黒を設定する
		String claSpan = "style=\"color: black\"";
		if (dateTimeNum < 0) {
			// dateTimeNumが0未満（マイナス）の場合は赤文字を設定する
			claSpan = "style=\"color: red\"";
		}
		return claSpan;
	}
	
	/**
	 * classで使用する文字列の設定。<br>
	 * @param dateTimeNum 一覧表示する日 or 時間
	 * @return dateTimeNum判定後の文字色設定タグ
	 */
	protected String setHistoryDateTimeDoubleColor(double dateTimeNum) {
		// 初期値に文字色黒を設定する
		String claSpan = "style=\"color: black\"";
		if (dateTimeNum < 0) {
			// dateTimeNumが0未満（マイナス）の場合は赤文字を設定する
			claSpan = "style=\"color: red\"";
		}
		return claSpan;
	}
	
	/**
	 * 文字列(日時間分)を取得する。<br>
	 * @param days         日数
	 * @param hours        時間数
	 * @param minutes      分数
	 * @param isZeroHyphen ハイフン変換フラグ(true：0日0時間を-に変換、false：変換しない)
	 * @return 日数(文字列) + 日 + 時間数 + 時間
	 */
	protected String getFormatDaysHoursMinutes(double days, int hours, int minutes, boolean isZeroHyphen) {
		return TimeUtility.getStringDaysHoursMinutes(mospParams, days, hours, minutes, isZeroHyphen);
	}
	
	/**
	 * 申請区分を取得する。<br>
	 * @param dto 対象DTO
	 * @return 申請区分
	 * @throws MospException 例外発生時
	 */
	public String getRequestTypeForView(ManagementRequestListDtoInterface dto) throws MospException {
		if (dto == null) {
			return "";
		}
		if (TimeConst.CODE_FUNCTION_WORK_MANGE.equals(dto.getRequestType())) {
			// 勤怠
			return mospParams.getName("WorkManage");
		} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(dto.getRequestType())) {
			// 残業
			return mospParams.getName("OvertimeWork");
		} else if (TimeConst.CODE_FUNCTION_VACATION.equals(dto.getRequestType())) {
			// 休暇
			return mospParams.getName("Holiday");
		} else if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(dto.getRequestType())) {
			// 振出・休出
			BaseDtoInterface baseDto = timeReference().approvalInfo().getRequestDtoForWorkflow(dto.getWorkflow(),
					false);
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = null;
			if (baseDto instanceof WorkOnHolidayRequestDtoInterface) {
				workOnHolidayRequestDto = (WorkOnHolidayRequestDtoInterface)baseDto;
			}
			if (workOnHolidayRequestDto == null) {
				return "";
			}
			int substitute = workOnHolidayRequestDto.getSubstitute();
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
					|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				// 振出
				return mospParams.getName("SubstituteAbbr", "GoingWorkAbbr");
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休出
				return mospParams.getName("WorkingHoliday");
			}
			return "";
		} else if (TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(dto.getRequestType())) {
			// 代休
			return mospParams.getName("CompensatoryHoliday");
		} else if (TimeConst.CODE_FUNCTION_DIFFERENCE.equals(dto.getRequestType())) {
			// 時差出勤
			return mospParams.getName("TimeDifference");
		} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(dto.getRequestType())) {
			// 勤務形態変更
			return mospParams.getName("Change");
		}
		return "";
	}
	
	/**
	 * 承認成功メッセージの設定。
	 */
	protected void addApprovalMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.approval(mospParams));
	}
	
	/**
	 * 差戻成功メッセージの設定。
	 */
	protected void addSendingBackMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.reverted(mospParams));
	}
	
	/**
	 * 解除申請差戻成功メッセージの設定。
	 */
	protected void addReleaseSendingBackMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.cancelWithdraw(mospParams));
	}
	
	/**
	 * Time型で渡された引数から～時～分フォーマットに変換した文字列を返す。
	 * @param date 変換対象時間
	 * @return 変換した文字列
	 */
	protected String getStringTimeMinutes(Date date) {
		if (date == null) {
			return "";
		}
		return getStringHour(date) + mospParams.getName("Hour") + getStringMinute(date) + mospParams.getName("Minutes");
	}
	
	/**
	 * @param type1 休暇区分1
	 * @param type2 休暇区分2
	 * @return 文字列に変換した休暇区分を返す
	 */
	protected String getHolidayType1Name(int type1, String type2) {
		if (type1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(type2)) {
				// 有給休暇
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("Salaried"));
				sb.append(mospParams.getName("Holiday"));
				return sb.toString();
			} else if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(type2)) {
				// ストック休暇
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("Stock"));
				sb.append(mospParams.getName("Holiday"));
				return sb.toString();
			}
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL) {
			// 特別休暇
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Specially"));
			sb.append(mospParams.getName("Holiday"));
			return sb.toString();
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			// その他休暇
			return mospParams.getName("Others");
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 欠勤
			return mospParams.getName("Absence");
		}
		return "";
	}
	
	/**
	 * @param type1 休暇区分1
	 * @param type2 休暇区分2
	 * @param date 対象年月日
	 * @return 休暇略称
	 * @throws MospException 例外発生時
	 */
	protected String getHolidayType2Abbr(int type1, String type2, Date date) throws MospException {
		if (type1 == 1) {
			return "";
		}
		HolidayDtoInterface dto = timeReference().holiday().getHolidayInfo(type2, date, type1);
		if (dto == null) {
			return type2;
		}
		return dto.getHolidayAbbr();
	}
	
	/**
	 * 引数で渡された休暇種別から表示する文字列を返す。<br>
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @param date 対象年月日
	 * @return 表示用文字列
	 * @throws MospException 例外発生時
	 */
	public String getHolidayTypeName(int holidayType1, String holidayType2, Date date) throws MospException {
		StringBuffer sb = new StringBuffer();
		sb.append(getHolidayType1Name(holidayType1, holidayType2));
		sb.append(" ");
		sb.append(getHolidayType2Abbr(holidayType1, holidayType2, date));
		return sb.toString();
	}
	
	/**
	* リクエストされた譲渡年を取得する。
	* @return 譲渡年
	*/
	protected String getTransferredYear() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_YEAR);
	}
	
	/**
	 * 仮締成功メッセージの設定。
	 */
	protected void addTighteningMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, TimeNamingUtility.tempTighten(mospParams));
	}
	
	/**
	 * 解除成功メッセージの設定。
	 */
	protected void addReleaseMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.release(mospParams));
	}
	
	/**
	 * 数値で渡ってきた休日範囲を文字列に変換する。
	 * @param holidayRange 休日範囲
	 * @return "全休/前休/後休/時休"
	 */
	protected String getHolidayRange(int holidayRange) {
		// 休暇範囲略称を取得
		return TimeUtility.getHolidayRangeAbbr(mospParams, holidayRange);
	}
	
	/**
	 * 確定成功メッセージの設定。
	 */
	protected void addDecideMessage() {
		PfMessageUtility.addMessageSucceed(mospParams, TimeNamingUtility.fixed(mospParams));
	}
	
	/**
	 * @param workflowDto ワークフローDTOインターフェース
	 * @param commentDto ワークフローコメントDTOインターフェース
	 * @return コメントデータ
	 * @throws MospException 例外処理発生時
	 */
	protected String getWorkflowCommentDtoComment(WorkflowDtoInterface workflowDto,
			WorkflowCommentDtoInterface commentDto) throws MospException {
		String status = getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage());
		if (status.equals(mospParams.getName("WorkPaper")) || status.equals(mospParams.getName("Ram", "Approval"))) {
			return "";
		} else {
			return commentDto.getWorkflowComment();
		}
	}
	
	/**
	 * @param workflowDto ワークフローDTOインターフェース
	 * @param commentDto ワークフローコメントDTOインターフェース
	 * @return 最終コメント入力者名
	 * @throws MospException 例外処理発生時
	 */
	protected String getWorkflowCommentDtoLastFirstName(WorkflowDtoInterface workflowDto,
			WorkflowCommentDtoInterface commentDto) throws MospException {
		// ワークフロー状態取得
		String status = workflowDto.getWorkflowStatus();
		// 下書の場合
		if (WorkflowUtility.isDraft(workflowDto)) {
			// 申請者名前
			return reference().human().getHumanName(workflowDto.getPersonalId(), workflowDto.getWorkflowDate());
		}
		// 未承認、n次済みの場合
		if (status.equals(PlatformConst.CODE_STATUS_APPLY) || status.equals(PlatformConst.CODE_STATUS_APPROVED)) {
			// 承認予定者名前
			return timeReference().approvalInfo().getNextApprover(workflowDto);
		}
		// 最終コメント入力者名
		return reference().human().getHumanName(commentDto.getPersonalId(), commentDto.getWorkflowDate());
	}
	
	/**
	 * 時(文字列)及び分(文字列)から分を取得する。<br>
	 * @param hours   時(文字列)
	 * @param minutes 分(文字列)
	 * @return 時分から取得した分
	 */
	public int getTimeValue(String hours, String minutes) {
		return TimeUtility.getTime(hours, minutes);
	}
	
	/**
	 * 休暇コードと有効日から休暇種別略称を取得する。<br>
	 * @param holidayCode 休暇コード
	 * @param activateDate 有効日
	 * @param holidayType 休暇区分
	 * @return 文字列変換した休暇種別
	 * @throws MospException 例外発生時
	 */
	protected String getHolidayAbbr(String holidayCode, Date activateDate, int holidayType) throws MospException {
		HolidayDtoInterface dto = timeReference().holiday().getHolidayInfo(holidayCode, activateDate, holidayType);
		if (dto == null) {
			return holidayCode;
		}
		return dto.getHolidayAbbr();
	}
	
	/**
	 * ボタンの背景色文字列の設定。<br>
	 * @param flg 数字
	 * @return 変換後の文字列
	 */
	protected String setButtonBackColor(int flg) {
		String buttonBackColor = "";
		if (flg == 1) {
			// flgが1（有り）の場合は背景色を黄文字を設定する
			buttonBackColor = "style=\"background-color: yellow\"";
		}
		return buttonBackColor;
	}
	
	/**
	* リクエストされた休暇種別1を取得する。
	* @return 休暇種別1
	*/
	protected String getTransferredHolidayType1() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_HOLIDAY_TYPE1);
	}
	
	/**
	* リクエストされた休暇種別2を取得する。
	* @return 休暇種別2
	*/
	protected String getTransferredHolidayType2() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_HOLIDAY_TYPE2);
	}
	
	/**
	* リクエストされた休暇範囲を取得する。
	* @return 休暇範囲
	*/
	protected String getTransferredHolidayRange() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_HOLIDAY_RANGE);
	}
	
	/**
	* リクエストされた時休開始時刻を取得する。
	* @return 時休開始時刻
	*/
	protected String getTransferredStartTime() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_START_TIME);
	}
	
	/**
	 * 承認者の配列を取得。<br>
	 * @return 承認者一覧（配列）
	 * @throws MospException 例外処理発生時
	 */
	protected String[] getSelectApproverIds() throws MospException {
		// VO取得
		TimeVo vo = (TimeVo)mospParams.getVo();
		int loopCnt = 0;
		List<String> list = new ArrayList<String>();
		String[] aryApproverSetting = { vo.getPltApproverSetting1(), vo.getPltApproverSetting2(),
			vo.getPltApproverSetting3(), vo.getPltApproverSetting4(), vo.getPltApproverSetting5(),
			vo.getPltApproverSetting6(), vo.getPltApproverSetting7(), vo.getPltApproverSetting8(),
			vo.getPltApproverSetting9(), vo.getPltApproverSetting10() };
		for (; loopCnt < aryApproverSetting.length; loopCnt++) {
			if (aryApproverSetting[loopCnt].isEmpty()) {
				break;
			}
			list.add(aryApproverSetting[loopCnt]);
		}
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * @param status 承認状況
	 * @param stage 承認段階
	 * @return 文字列変換後の区分
	 */
	protected String getButtonOnOff(String status, int stage) {
		String on = "on";
		// ワークフロー状態確認
		if (PlatformConst.CODE_STATUS_DRAFT.equals(status)) {
			// 下書
			return on;
		}
		if (PlatformConst.CODE_STATUS_REVERT.equals(status) && stage == 0) {
			// 1次戻
			return on;
		}
		return "";
	}
	
	/**
	 * リクエストされた譲渡汎用日を取得する。
	 * @return 譲渡汎用日
	 */
	protected String getTransferredDay() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_DAY);
	}
	
	/**
	 * リクエストされた時間を取得する。
	 * @return 時間
	 */
	protected String getTransferredHour() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_HOUR);
	}
	
	/**
	 * リクエストされた分を取得する。
	 * @return 分
	 */
	protected String getTransferredMinute() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_MINUTE);
	}
	
	/**
	 * リクエストされた開始時間を取得する。
	 * @return 開始時間
	 */
	protected String getTransferredStartHour() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_START_HOUR);
	}
	
	/**
	 * リクエストされた開始分を取得する。
	 * @return 開始分
	 */
	protected String getTransferredStartMinute() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_START_MINUTE);
	}
	
	/**
	 * リクエストされた終了時間を取得する。
	 * @return 終了時間
	 */
	protected String getTransferredEndHour() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_END_HOUR);
	}
	
	/**
	 * リクエストされた終了分を取得する。
	 * @return 終了分
	 */
	protected String getTransferredEndMinute() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_END_MINUTE);
	}
	
	/**
	 * リクエストされた直行を取得する。
	 * @return 直行
	 */
	protected String getTransferredDirectStart() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_DIRECT_START);
	}
	
	/**
	 * リクエストされた直帰を取得する。
	 * @return 直帰
	 */
	protected String getTransferredDirectEnd() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_DIRECT_END);
	}
	
	/**
	 * リクエストされた勤怠コメントを取得する。
	 * @return 勤怠コメント
	 */
	protected String getTransferredTimeComment() {
		return mospParams.getRequestParam(TimeConst.PRM_TRANSFERRED_TIME_COMMENT);
	}
	
	/**
	 * 引数で指定された個人IDと申請日に時差出勤が行われているかどうか判断する。
	 * @param personalId 個人ID
	 * @param date 申請日
	 * @throws MospException 例外発生時
	 */
	protected void getDifferenceRequest1(String personalId, Date date) throws MospException {
		// VO取得
		TimeVo vo = (TimeVo)mospParams.getVo();
		vo.setJsModeDifferenceRequest1("");
		vo.setJsModeRequestDate1("");
		// 該当日付に時差出勤が申請されているか確認する。
		DifferenceRequestDtoInterface diffDto = timeReference().differenceRequest().findForKeyOnWorkflow(personalId,
				date);
		if (diffDto != null) {
			WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(diffDto.getWorkflow());
			if (workflowDto != null) {
				// ワークフローの状態が下書/取下以外の場合にtrueを返す
				if (WorkflowUtility.isDraft(workflowDto) || WorkflowUtility.isWithDrawn(workflowDto)) {
					return;
				} else {
					vo.setJsModeDifferenceRequest1("on");
					vo.setJsModeRequestDate1(getStringDate(date));
					return;
				}
			} else {
				return;
			}
		} else {
			return;
		}
	}
	
	/**
	 * 承認履歴画面のコマンドを取得する。<br>
	 * @param requestType 申請区分
	 * @return コマンド
	 */
	protected String getHistoryCommand(String requestType) {
		if (TimeConst.CODE_FUNCTION_WORK_MANGE.equals(requestType)) {
			// 勤怠
			return ApprovalHistoryAction.CMD_TIME_APPROVAL_HISTORY_SELECT_SHOW;
		} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(requestType)) {
			// 残業
			return ApprovalHistoryAction.CMD_OVERTIME_APPROVAL_HISTORY_SELECT_SHOW;
		} else if (TimeConst.CODE_FUNCTION_VACATION.equals(requestType)) {
			// 休暇
			return ApprovalHistoryAction.CMD_LEAVE_APPROVAL_HISTORY_SELECT_SHOW;
		} else if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(requestType)) {
			// 振出・休出
			return ApprovalHistoryAction.CMD_APPROVAL_HISTORY_HOLIDAY_WORK_SELECT_SHOW;
		} else if (TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(requestType)) {
			// 代休
			return ApprovalHistoryAction.CMD_APPROVAL_HISTORY_LIEU_SELECT_SHOW;
		} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(requestType)) {
			// 勤務形態変更
			return ApprovalHistoryAction.CMD_WORK_TYPE_CHANGE_APPROVAL_HISTORY_SELECT_SHOW;
		} else if (TimeConst.CODE_FUNCTION_DIFFERENCE.equals(requestType)) {
			// 時差出勤
			return ApprovalHistoryAction.CMD_DIFFERENCE_WORK_APPROVAL_HISTORY_SELECT_SHOW;
		}
		return "";
	}
	
	/**
	 * 申請状態の色を取得する。<br>
	 * @param status 申請状態
	 * @return 色
	 */
	protected String getStatusColor(String status) {
		if (PlatformConst.CODE_STATUS_DRAFT.equals(status) || PlatformConst.CODE_STATUS_APPLY.equals(status)
				|| PlatformConst.CODE_STATUS_APPROVED.equals(status) || PlatformConst.CODE_STATUS_REVERT.equals(status)
				|| PlatformConst.CODE_STATUS_CANCEL.equals(status)
				|| PlatformConst.CODE_STATUS_CANCEL_APPLY.equals(status)
				|| PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY.equals(status)) {
			// 下書・未承認・承認・差戻・承認解除・承認解除申請・承認解除申請(取下げ希望)
			return PlatformConst.STYLE_RED;
		} else if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(status)
				|| PlatformConst.CODE_STATUS_COMPLETE.equals(status)) {
			// 取下・承認済
			return "";
		}
		return "";
	}
	
	/**
	 * 勤怠申請状態の色を取得する。<br>
	 * @param state 勤怠申請状態
	 * @return 色
	 */
	protected String getAttendanceStateColor(String state) {
		StringBuffer approved = new StringBuffer();
		approved.append(mospParams.getName("Approval"));
		approved.append(mospParams.getName("Finish"));
		if (state == null || state.isEmpty() || mospParams.getName("Schedule").equals(state)
				|| approved.toString().equals(state)) {
			return "";
		}
		return PlatformConst.STYLE_RED;
	}
	
	/**
	 * @param personalId 個人ID
	 * @param requestDate 対象日
	 * @param type 機能コード
	 * @return 背景
	 * @throws MospException 例外発生時
	 */
	protected String setBackColor(String personalId, Date requestDate, String type) throws MospException {
		// クラス準備
		DifferenceRequestReferenceBeanInterface differenceRequest = timeReference().differenceRequest(requestDate);
		RequestUtilBeanInterface requestUtil = timeReference().requestUtil();
		// 申請ユーティリティを設定
		requestUtil.setRequests(personalId, requestDate);
		// 休暇範囲確認
		int holidayRequestRange = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false));
		boolean isHalfHoliday = holidayRequestRange == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| holidayRequestRange == TimeConst.CODE_HOLIDAY_RANGE_PM;
		int subHolidayRequestRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
		boolean isHalfSubHoliday = subHolidayRequestRange == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| subHolidayRequestRange == TimeConst.CODE_HOLIDAY_RANGE_PM;
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(false);
		if (TimeConst.CODE_FUNCTION_VACATION.equals(type)) {
			// 休暇
			if (isHalfHoliday && differenceRequestDto != null) {
				return PlatformConst.STYLE_BACKGROUND_YELLOW;
			}
		} else if (TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(type)) {
			// 代休
			if (isHalfSubHoliday && differenceRequestDto != null) {
				return PlatformConst.STYLE_BACKGROUND_YELLOW;
			}
		} else if (TimeConst.CODE_FUNCTION_DIFFERENCE.equals(type)) {
			// 時差出勤
			if (isHalfHoliday || isHalfSubHoliday) {
				return PlatformConst.STYLE_BACKGROUND_YELLOW;
			}
			if (differenceRequestDto == null || !differenceRequest.isDifferenceTypeS(differenceRequestDto)) {
				// 時差出勤区分Sでない場合
				return "";
			}
			// 時差出勤区分Sの場合
			int year = DateUtility.getYear(differenceRequestDto.getRequestDate());
			int month = DateUtility.getMonth(differenceRequestDto.getRequestDate());
			int day = DateUtility.getDay(differenceRequestDto.getRequestDate());
			Date fiveHour = DateUtility.getDateTime(year, month, day, 5, 0);
			Date twentyTwoHour = DateUtility.getDateTime(year, month, day, 22, 0);
			if (!differenceRequestDto.getRequestStart().before(fiveHour)
					&& !differenceRequestDto.getRequestStart().after(twentyTwoHour)
					&& !differenceRequestDto.getRequestEnd().before(fiveHour)
					&& !differenceRequestDto.getRequestEnd().after(twentyTwoHour)) {
				// 時差出勤時刻が深夜時間帯でない場合
				return PlatformConst.STYLE_BACKGROUND_YELLOW;
			}
		}
		return "";
	}
	
	/**
	 * 入力されている社員コードが正しくない場合の設定。
	 */
	protected void addNotExistEmployeesErrorMessage() {
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_EXIST_EMPLOYEES);
	}
	
	/**
	 * 社員が退職している場合のメッセージを設定する。<br>
	 */
	protected void addEmployeeRetiredMessage() {
		PfMessageUtility.addErrorEmployeeRetired(mospParams);
	}
	
	/**
	 * 社員が休職している場合のメッセージを設定する。<br>
	 */
	protected void addEmployeeSuspendedMessage() {
		PfMessageUtility.addErrorEmployeeSuspended(mospParams);
	}
	
	/**
	 * 振出・休出申請の予定を取得する。
	 * @param dto 対象DTO
	 * @return 予定
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getWorkOnHolidaySchedule(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// クラス準備
		SubstituteReferenceBeanInterface substitute = timeReference().substitute();
		WorkTypeReferenceBeanInterface workType = timeReference().workType();
		ScheduleUtilBeanInterface scheduleUtil = timeReference().scheduleUtil();
		// 休日出勤の場合
		if (dto.getRequestDate() != null && dto.getStartTime() != null && dto.getEndTime() != null) {
			return getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestDate());
		}
		// 振替出勤(勤務形態変更なし)・振替出勤(勤務形態変更あり)の場合
		String workTypeCode = dto.getWorkTypeCode();
		Date targetDate = dto.getRequestDate();
		// 振替出勤(勤務形態変更なし)の場合
		if (workTypeCode.isEmpty()) {
			// 振替申請情報を取得
			List<SubstituteDtoInterface> list = substitute.getSubstituteList(dto.getWorkflow());
			if (list.isEmpty()) {
				return "";
			}
			// 振替休日取得
			targetDate = list.get(0).getSubstituteDate();
			// カレンダに登録されている勤務形態コードを取得
			workTypeCode = scheduleUtil.getScheduledWorkTypeCodeNoMessage(dto.getPersonalId(), targetDate);
		}
		// 勤務形態情報取得
		WorkTypeDtoInterface workTypeDto = workType.findForInfo(workTypeCode, targetDate);
		if (workTypeDto == null) {
			return "";
		}
		// 勤務形態略称取得
		String base = workTypeDto.getWorkTypeAbbr();
		// 振替区分
		int substituteType = dto.getSubstitute();
		// 半日振替有効の場合
		if (timeReference().workOnHolidayRequest().useHalfSubstitute()) {
			// 全休の場合
			// 振替・午前・午後の場合
			if (substituteType == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
					|| substituteType == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
					|| substituteType == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				return base + MospConst.STR_SB_SPACE
						+ getCodeName(substituteType, TimeConst.CODE_SUBSTITUTE_WORK_RANGE);
			}
			// 勤務形態変更の場合
			if (substituteType == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
				return base + MospConst.STR_SB_SPACE + getCodeName(1, TimeConst.CODE_SUBSTITUTE_WORK_RANGE);
			}
			
		}
		// 勤務形態略称
		return base;
	}
	
	/**
	 * 時差出勤申請前の勤務形態略称取得。
	 * @return 時差出勤申請前の勤務形態略称
	 * @throws MospException 例外発生時
	 */
	protected String getBeforeDifferenceRequestWorkTypeAbbr() throws MospException {
		// VO準備
		TimeVo vo = (TimeVo)mospParams.getVo();
		ScheduleUtilBeanInterface scheduleUtil = timeReference().scheduleUtil();
		WorkTypeReferenceBeanInterface workTypeReference = timeReference().workType();
		RequestUtilBeanInterface requestUtil = timeReference().requestUtil();
		// 各種申請情報取得
		requestUtil.setRequests(vo.getPersonalId(), vo.getTargetDate());
		String personalId = vo.getPersonalId();
		Date targetDate = vo.getTargetDate();
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto != null) {
			// 振替申請取得
			int substitute = workOnHolidayRequestDto.getSubstitute();
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
				// 振替出勤の場合
				List<SubstituteDtoInterface> list = timeReference().substitute()
					.getSubstituteList(workOnHolidayRequestDto.getWorkflow());
				if (list.isEmpty()) {
					return "";
				}
				SubstituteDtoInterface substituteDto = list.get(0);
				if (substituteDto == null) {
					return "";
				}
				personalId = substituteDto.getPersonalId();
				targetDate = substituteDto.getSubstituteDate();
			} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休日出勤の場合
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
					// 法定休日出勤の場合
					return workTypeReference.getWorkTypeAbbr(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY,
							workOnHolidayRequestDto.getRequestDate());
				} else if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY
					.equals(workOnHolidayRequestDto.getWorkOnHolidayType())) {
					// 所定休日出勤の場合
					return workTypeReference.getWorkTypeAbbr(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY,
							workOnHolidayRequestDto.getRequestDate());
				}
				return "";
			} else {
				return "";
			}
		}
		// カレンダに登録されている勤務形態コードを取得
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate);
		// カレンダに登録されている勤務形態コードを取得できなかった場合
		if (mospParams.hasErrorMessage()) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 勤務形態略称を取得
		return workTypeReference.getWorkTypeAbbr(workTypeCode, targetDate);
	}
	
	/**
	 * ワークフロー番号から申請モードを取得する。
	 * @param workflow ワークフロー番号
	 * @return 申請モード
	 * @throws MospException ワークフロー情報入手に失敗した場合
	 */
	protected String getApplicationMode(long workflow) throws MospException {
		// ワークフロー情報取得
		WorkflowDtoInterface dto = reference().workflow().getLatestWorkflowInfo(workflow);
		// ワークフロー情報確認
		if (dto == null) {
			return null;
		}
		// ワークフロー状況確認(下書)
		if (WorkflowUtility.isDraft(dto)) {
			return TimeConst.MODE_APPLICATION_DRAFT;
		}
		// ワークフロー状況確認(差戻)
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_REVERT)) {
			return TimeConst.MODE_APPLICATION_REVERT;
		}
		return null;
	}
	
	/**
	 * 勤務形態プルダウン用配列を取得する。<br>
	 * @param patternCode パターンコード
	 * @param targetDate 対象年月日
	 * @param isName 名称表示(true：名称表示、false：略称表示)
	 * @param viewTime 時刻表示(true：時刻表示、false：時刻非表示)
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException 例外発生時
	 */
	protected String[][] getWorkTypeArray(String patternCode, Date targetDate, boolean isName, boolean viewTime,
			boolean amHoliday, boolean pmHoliday) throws MospException {
		// クラス取得
		WorkTypeReferenceBeanInterface workTypeReference = timeReference().workType();
		WorkTypePatternItemReferenceBeanInterface workTypePatternItemReference = timeReference().workTypePatternItem();
		// 勤務形態パターン情報を取得
		WorkTypePatternDtoInterface workTypePatternDto = timeReference().workTypePattern().findForInfo(patternCode,
				targetDate);
		// パターン使用可否準備
		boolean useWorkTypePattern = false;
		// 勤務形態パターン情報があり、無効でない場合
		if (workTypePatternDto != null && workTypePatternDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_OFF) {
			useWorkTypePattern = true;
		}
		// 勤務形態パターンを使う場合
		if (useWorkTypePattern) {
			if (isName && viewTime) {
				// 勤務形態名称 + 時刻
				return workTypePatternItemReference.getNameTimeSelectArray(workTypePatternDto.getPatternCode(),
						targetDate, amHoliday, pmHoliday);
			} else if (!isName && viewTime) {
				// 勤務形態略称 + 時刻
				return workTypePatternItemReference.getTimeSelectArray(workTypePatternDto.getPatternCode(), targetDate,
						amHoliday, pmHoliday);
			} else if (isName && !viewTime) {
				// 勤務形態名称
				return workTypePatternItemReference.getNameSelectArray(workTypePatternDto.getPatternCode(), targetDate);
			}
			// 勤務形態略称
			return workTypePatternItemReference.getSelectArray(workTypePatternDto.getPatternCode(), targetDate);
		}
		// 勤務形態パターンを使わない場合は全ての勤務形態を表示する
		if (isName && viewTime) {
			// 勤務形態名称 + 時刻
			return workTypeReference.getNameTimeSelectArray(targetDate, amHoliday, pmHoliday);
		} else if (!isName && viewTime) {
			// 勤務形態略称 + 時刻
			return workTypeReference.getTimeSelectArray(targetDate, amHoliday, pmHoliday);
		} else if (isName && !viewTime) {
			// 勤務形態名称
			return workTypeReference.getSelectArray(targetDate);
		}
		// 勤務形態略称
		return workTypeReference.getSelectAbbrArray(targetDate);
	}
	
	/**
	 * 申請情報詳細を取得する。<br>
	 * @param dto 対象DTO
	 * @return 申請情報詳細
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected String getRequestInfo(ManagementRequestListDtoInterface dto) throws MospException {
		AttendanceReferenceBeanInterface attendance = timeReference().attendance();
		if (!TimeConst.CODE_FUNCTION_WORK_MANGE.equals(dto.getRequestType())) {
			// 勤怠でない場合
			return HtmlUtility.escapeHTML(dto.getRequestInfo());
		}
		// 勤怠である場合
		AttendanceDtoInterface attendanceDto = attendance.findForKey(dto.getPersonalId(), dto.getRequestDate());
		if (attendanceDto == null) {
			return HtmlUtility.escapeHTML(dto.getRequestInfo());
		}
		// 半角スペースで分割
		String[] requestInfoArray = dto.getRequestInfo().split(String.valueOf(MospUtility.CHR_SEPARATOR_SPACE));
		String regex = "\\d{2}:\\d{2}～\\d{2}:\\d{2}";
		if (MospConst.CHECKBOX_ON.equals(Integer.toString(attendanceDto.getForgotRecordWorkStart()))
				|| MospConst.CHECKBOX_ON.equals(Integer.toString(attendanceDto.getNotRecordWorkStart()))) {
			// 始業忘れ・その他である場合
			List<String> list = new ArrayList<String>();
			for (String requestInfo : requestInfoArray) {
				if (requestInfo.matches(regex)) {
					// HH:MM～HH:MMの場合
					StringBuffer sb = new StringBuffer();
					sb.append("<span ");
					sb.append(PlatformConst.STYLE_RED);
					sb.append(">");
					sb.append(HtmlUtility.escapeHTML(requestInfo));
					sb.append("</span>");
					list.add(sb.toString());
					continue;
				}
				list.add(HtmlUtility.escapeHTML(requestInfo));
			}
			return concat(list);
		}
		// 始業忘れ・その他でない場合
		boolean isLate = !attendanceDto.getLateReason().isEmpty();
		boolean isLeaveEarly = !attendanceDto.getLeaveEarlyReason().isEmpty();
		if (!isLate && !isLeaveEarly) {
			// 遅刻・早退でない場合
			return HtmlUtility.escapeHTML(dto.getRequestInfo());
		}
		// 遅刻・早退である場合
		List<String> list = new ArrayList<String>();
		for (String requestInfo : requestInfoArray) {
			if (!requestInfo.matches(regex)) {
				// HH:MM～HH:MMでない場合
				list.add(HtmlUtility.escapeHTML(requestInfo));
				continue;
			}
			// HH:MM～HH:MMである場合
			String startWork = requestInfo.substring(0, 5);
			String endWork = requestInfo.substring(6);
			if (isLate && isLeaveEarly) {
				// 遅刻且つ早退の場合
				StringBuffer sb = new StringBuffer();
				sb.append("<span ");
				sb.append(PlatformConst.STYLE_RED);
				sb.append(">");
				sb.append(HtmlUtility.escapeHTML(startWork));
				sb.append("</span>");
				sb.append(HtmlUtility.escapeHTML(requestInfo.substring(5, 6)));
				sb.append("<span ");
				sb.append(PlatformConst.STYLE_RED);
				sb.append(">");
				sb.append(HtmlUtility.escapeHTML(endWork));
				sb.append("</span>");
				list.add(sb.toString());
				continue;
			} else if (isLate) {
				// 遅刻の場合
				StringBuffer sb = new StringBuffer();
				sb.append("<span ");
				sb.append(PlatformConst.STYLE_RED);
				sb.append(">");
				sb.append(HtmlUtility.escapeHTML(startWork));
				sb.append("</span>");
				sb.append(HtmlUtility.escapeHTML(requestInfo.substring(5)));
				list.add(sb.toString());
				continue;
			} else if (isLeaveEarly) {
				// 早退の場合
				StringBuffer sb = new StringBuffer();
				sb.append(HtmlUtility.escapeHTML(requestInfo.substring(0, 6)));
				sb.append("<span ");
				sb.append(PlatformConst.STYLE_RED);
				sb.append(">");
				sb.append(HtmlUtility.escapeHTML(endWork));
				sb.append("</span>");
				list.add(sb.toString());
				continue;
			}
			list.add(HtmlUtility.escapeHTML(requestInfo));
		}
		return concat(list);
	}
	
	/**
	 * 部下一覧を利用できるかを確認する。<br>
	 * 所属、職位が設定されていなければ、エラーメッセージを設定する。<br>
	 * 但し、特権ユーザは無条件で利用可能。<br>
	 * @param targetDate 対象日
	 * 
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void checkSubordinateAvailable(Date targetDate) throws MospException {
		// ログインユーザのロールを確認
		if (RoleUtility.isSuper(mospParams)) {
			return;
		}
		// ログインユーザの個人IDを取得
		String personalId = MospUtility.getLoginPersonalId(mospParams);
		// ログインユーザの人事情報を取得
		HumanDtoInterface dto = getHumanInfo(personalId, targetDate);
		// 人事情報が取得できなかった場合
		if (dto == null) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorUnsetHumanInfo(mospParams, PfNameUtility.humanInfo(mospParams));
			// 処理終了
			return;
		}
		// 所属が設定されていない場合
		if (MospUtility.isEmpty(dto.getSectionCode())) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorUnsetHumanInfo(mospParams, PfNameUtility.sectionInfo(mospParams));
		}
		// 職位が設定されていない場合
		if (MospUtility.isEmpty(dto.getPositionCode())) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorUnsetHumanInfo(mospParams, PfNameUtility.positionInfo(mospParams));
		}
	}
	
	/**
	 * 利用可否チェックを行う。<br>
	 * @param codeKey    コードキー(利用可否チェック処理クラス名)
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param types 対象機能
	 * @return 確認結果(true：利用可、false：利用不可)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public boolean isAvailable(String codeKey, String personalId, Date targetDate, String... types)
			throws MospException {
		// 確認結果を準備
		boolean isAvailable = true;
		// 利用可否チェック毎に処理
		for (CheckAvailableBeanInterface addonBean : getBeans(CheckAvailableBeanInterface.class, codeKey)) {
			// 利用可否チェック処理
			isAvailable = isAvailable & addonBean.check(personalId, targetDate, types);
		}
		// 確認結果を取得
		return isAvailable;
	}
	
	/**
	 * 利用可否チェック(各種申請用)を行う。<br>
	 * 個人IDはVOから取得する。<br>
	 * @param targetDate 対象日
	 * @param function   対象機能コード
	 * @return 確認結果(true：利用可、false：利用不可)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public boolean isAvailable(Date targetDate, String function) throws MospException {
		// VOを準備
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 利用可否チェック
		return isAvailable(TimeConst.CODE_KEY_CHECK_APPLY_AVAILABLE, vo.getPersonalId(), targetDate, function);
	}
	
	/**
	 * 連結文字リストを半角スペースで連結する。
	 * @param list 対象リスト
	 * @return 連結された文字列
	 */
	protected String concat(List<String> list) {
		return MospUtility.concat(list.toArray(new String[0]));
	}
	
	/**
	 * 分数を取得する。<br>
	 * @param numerator 分子
	 * @param denominator 分母
	 * @return 分数
	 */
	protected String getFraction(Integer numerator, Integer denominator) {
		if (numerator == null || denominator == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(numerator.intValue());
		sb.append(mospParams.getName("Slash"));
		sb.append(denominator.intValue());
		return sb.toString();
	}
	
	/**
	 * 括弧を追加する。<br>
	 * @param content 中身文字列
	 * @return 括弧を追加した文字列
	 */
	protected String addParenthesis(String content) {
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("FrontParentheses"));
		sb.append(content);
		sb.append(mospParams.getName("BackParentheses"));
		return sb.toString();
	}
	
	/**
	 * パーセントフォーマットを取得する。<br>
	 * @param minimumFractionDigits 小数部分の最小表示桁数
	 * @return パーセントフォーマット
	 */
	protected Format getPercentFormat(int minimumFractionDigits) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		// 小数部分の最小表示桁数を設定
		nf.setMinimumFractionDigits(minimumFractionDigits);
		return nf;
	}
	
}
