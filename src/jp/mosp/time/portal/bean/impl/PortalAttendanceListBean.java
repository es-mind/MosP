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
package jp.mosp.time.portal.bean.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.impl.PortalBean;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * ポータル用勤怠一覧クラス。<br>
 */
public class PortalAttendanceListBean extends PortalBean implements PortalBeanInterface {
	
	/**
	 * パス(ポータル用メッセージJSP)。
	 */
	protected static final String					PATH_PORTAL_VIEW						= "/jsp/time/portal/portalAttendanceList.jsp";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(画面遷移用日付)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_DATE				= "attendanceListDate";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(日)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_WORK_DATE			= "attendanceListWorkDate";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(曜日)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_WEEK				= "attendanceListWeek";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(曜日スタイル)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_WEEK_STYLE			= "attendanceListWeekStyle";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(形態)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_WORK_TYPE			= "attendanceListWorkType";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(始業)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_START_TIME			= "attendanceListStartTime";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(始業配色)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_START_TIME_STYLE	= "attendanceListStartTimeStyle";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(終業)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_END_TIME			= "attendanceListEndTime";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(終業配色)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_END_TIME_STYLE		= "attendanceListEndTimeStyle";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(勤時)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_WORK_TIME			= "attendanceListWorkTime";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(休憩)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_REST_TIME			= "attendanceListRestTime";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(私用)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_PRIVATE_TIME		= "attendanceListPrivateTime";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(遅刻)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_LATE_TIME			= "attendanceListLateTime";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(早退)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_LEAVE_EARLY			= "attendanceListLeaveEarly";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(遅早)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_LATE_LEAVE_EARLY	= "attendanceListLateLeaveEarly";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(内残)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_OVER_IN				= "attendanceListOverIn";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(外残)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_OVER_OUT			= "attendanceListOverOut";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(休出)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_HOLIDAY				= "attendanceHoliday";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(深夜)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_LATE_NIGHT			= "attendanceListLateNight";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(状態)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_STATUS				= "attendanceListStatus";
	
	/**
	 * ポータルパラメータキー(勤怠一覧)(備考)。
	 */
	public static final String						PRM_ATTENDANCE_LIST_REMARK				= "attendanceListRemark";
	
	/**
	 * 勤怠一覧参照クラス。
	 */
	protected AttendanceListReferenceBeanInterface	attendanceListReference;
	
	
	/**
	 * {@link PortalBean#PortalBean()}を実行する。<br>
	 */
	public PortalAttendanceListBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 参照クラス準備
		attendanceListReference = createBeanInstance(AttendanceListReferenceBeanInterface.class);
	}
	
	@Override
	public void show() throws MospException {
		// 勤怠一覧が利用できない場合
		if (TimeUtility.isAttendanceListAvailable(mospParams) == false) {
			// 処理無し
			return;
		}
		// ポータル用JSPパス追加
		addPortalViewList(PATH_PORTAL_VIEW);
		// エラーメッセージ長取得
		int errorMessageCount = mospParams.getErrorMessageList().size();
		// 個人ID取得(ログインユーザの個人ID)
		String personalId = mospParams.getUser().getPersonalId();
		// 勤怠一覧取得(個人ID及びシステム日付で取得)
		List<AttendanceListDto> list = attendanceListReference.getWeeklyAttendanceList(personalId, getSystemDate());
		// アドオン用追加処理
		doAdditionalLogic(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_LIST_ADDONS, list);
		// 検索結果リストの内容をVOに設定
		setVoList(list);
		// 追加されたエラーメッセージを除去(当機能は表示のみでありエラーメッセージ不要)
		removeAddedErrorMessages(errorMessageCount);
	}
	
	@Override
	public void regist() {
		// 処理無し
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<AttendanceListDto> list) {
		// 表示件数確認
		int count = list.size();
		// データ配列初期化
		String[] attendanceListDate = new String[count];
		String[] attendanceListWorkDate = new String[count];
		String[] attendanceListWeek = new String[count];
		String[] attendanceListWeekStyle = new String[count];
		String[] attendanceListWorkType = new String[count];
		String[] attendanceListStartTime = new String[count];
		String[] attendanceListStartTimeStyle = new String[count];
		String[] attendanceListEndTime = new String[count];
		String[] attendanceListEndTimeStyle = new String[count];
		String[] attendanceListWorkTime = new String[count];
		String[] attendanceListRestTime = new String[count];
		String[] attendanceListPrivateTime = new String[count];
		String[] attendanceListLateTime = new String[count];
		String[] attendanceListLeaveEarly = new String[count];
		String[] attendanceListLateLeaveEarly = new String[count];
		String[] attendanceListOvertimeIn = new String[count];
		String[] attendanceListOverOut = new String[count];
		String[] attendanceHoliday = new String[count];
		String[] attendanceListLateNight = new String[count];
		String[] attendanceListStatus = new String[count];
		String[] attendanceListRemark = new String[count];
		// データ作成
		for (int i = 0; i < count; i++) {
			// リストから情報を取得
			AttendanceListDto dto = list.get(i);
			// 配列に情報を設定
			attendanceListDate[i] = getStringDate(dto.getWorkDate());
			attendanceListWorkDate[i] = dto.getWorkDateString();
			attendanceListWeek[i] = dto.getWorkDayOfWeek();
			attendanceListWeekStyle[i] = dto.getWorkDayOfWeekStyle();
			attendanceListWorkType[i] = dto.getWorkTypeAbbr();
			attendanceListStartTime[i] = dto.getStartTimeString();
			attendanceListStartTimeStyle[i] = dto.getStartTimeStyle();
			attendanceListEndTime[i] = dto.getEndTimeString();
			attendanceListEndTimeStyle[i] = dto.getEndTimeStyle();
			attendanceListWorkTime[i] = dto.getWorkTimeString();
			attendanceListRestTime[i] = dto.getRestTimeString();
			attendanceListPrivateTime[i] = dto.getPrivateTimeString();
			attendanceListLateTime[i] = dto.getLateTimeString();
			attendanceListLeaveEarly[i] = dto.getLeaveEarlyTimeString();
			attendanceListLateLeaveEarly[i] = dto.getLateLeaveEarlyTimeString();
			attendanceListOvertimeIn[i] = dto.getOvertimeInString();
			attendanceListOverOut[i] = dto.getOvertimeOutString();
			attendanceHoliday[i] = dto.getHolidayWorkTimeString();
			attendanceListLateNight[i] = dto.getLateNightTimeString();
			attendanceListStatus[i] = dto.getApplicationInfo();
			attendanceListRemark[i] = MospUtility.concat(dto.getRemark(), dto.getTimeComment());
		}
		// データをVOに設定
		putPortalParameters(PRM_ATTENDANCE_LIST_DATE, attendanceListDate);
		putPortalParameters(PRM_ATTENDANCE_LIST_WORK_DATE, attendanceListWorkDate);
		putPortalParameters(PRM_ATTENDANCE_LIST_WEEK, attendanceListWeek);
		putPortalParameters(PRM_ATTENDANCE_LIST_WEEK_STYLE, attendanceListWeekStyle);
		putPortalParameters(PRM_ATTENDANCE_LIST_WORK_TYPE, attendanceListWorkType);
		putPortalParameters(PRM_ATTENDANCE_LIST_START_TIME, attendanceListStartTime);
		putPortalParameters(PRM_ATTENDANCE_LIST_START_TIME_STYLE, attendanceListStartTimeStyle);
		putPortalParameters(PRM_ATTENDANCE_LIST_END_TIME, attendanceListEndTime);
		putPortalParameters(PRM_ATTENDANCE_LIST_END_TIME_STYLE, attendanceListEndTimeStyle);
		putPortalParameters(PRM_ATTENDANCE_LIST_WORK_TIME, attendanceListWorkTime);
		putPortalParameters(PRM_ATTENDANCE_LIST_REST_TIME, attendanceListRestTime);
		putPortalParameters(PRM_ATTENDANCE_LIST_PRIVATE_TIME, attendanceListPrivateTime);
		putPortalParameters(PRM_ATTENDANCE_LIST_LATE_TIME, attendanceListLateTime);
		putPortalParameters(PRM_ATTENDANCE_LIST_LEAVE_EARLY, attendanceListLeaveEarly);
		putPortalParameters(PRM_ATTENDANCE_LIST_LATE_LEAVE_EARLY, attendanceListLateLeaveEarly);
		putPortalParameters(PRM_ATTENDANCE_LIST_OVER_IN, attendanceListOvertimeIn);
		putPortalParameters(PRM_ATTENDANCE_LIST_OVER_OUT, attendanceListOverOut);
		putPortalParameters(PRM_ATTENDANCE_LIST_HOLIDAY, attendanceHoliday);
		putPortalParameters(PRM_ATTENDANCE_LIST_LATE_NIGHT, attendanceListLateNight);
		putPortalParameters(PRM_ATTENDANCE_LIST_STATUS, attendanceListStatus);
		putPortalParameters(PRM_ATTENDANCE_LIST_REMARK, attendanceListRemark);
	}
	
	/**
	 * 追加されたエラーメッセージを除く。<br>
	 * @param errorMessageCount 元のエラーメッセージ数
	 */
	protected void removeAddedErrorMessages(int errorMessageCount) {
		// エラーメッセージリスト取得
		List<String> errorMessageList = mospParams.getErrorMessageList();
		// 追加されたエラーメッセージを除去
		for (int i = errorMessageList.size(); i > errorMessageCount; i--) {
			errorMessageList.remove(i - 1);
		}
	}
}
