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
package jp.mosp.time.portal.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.impl.PortalBean;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.utils.AttendanceUtility;

/**
 * ログイン直後ポータル画面表示時に勤怠未入力確認クラス。
 */
public class PortalAttendanceCheckBean extends PortalBean implements PortalBeanInterface {
	
	/**
	 * パス(ポータル用勤怠未入力確認機能JSP)。
	 */
	protected static final String									PATH_PORTAL_CHECK_TIME_VIEW		= "/jsp/time/portal/portalAttendanceCheck.jsp";
	
	/**
	 * 勤怠未入力時の確認メッセージ。
	 */
	public static final String										MSG_NO_APPLI_TIME_WORK			= "TMW0287";
	
	/**
	 * 確認メッセージ。
	 */
	public static final String										MSG_CONFIRM						= "TMI0003";
	
	/**
	 * ポータルパラメータキー(ログイン直後メッセージ)。
	 */
	public static final String										PRM_ATTENDANCE_CHECK_MESSAGE	= "prmAttendanceCheckMessage";
	
	/**
	 * 入社日参照クラス。
	 */
	protected EntranceReferenceBeanInterface						entranceRefer;
	
	/**
	 * 勤怠集計参照クラス。
	 */
	protected TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeeRefer;
	
	/**
	 * 勤怠未入力確認用に勤怠一覧情報リスト
	 */
	protected AttendanceListReferenceBeanInterface					attendanceList;
	
	/**
	 * 勤怠関連マスタ参照クラス。<br>
	 */
	protected TimeMasterBeanInterface								timeMaster;
	
	
	/**
	 * {@link PortalBean#PortalBean()}を実行する。<br>
	 */
	public PortalAttendanceCheckBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
		entranceRefer = createBeanInstance(EntranceReferenceBeanInterface.class);
		totalTimeEmployeeRefer = createBeanInstance(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		attendanceList = createBeanInstance(AttendanceListReferenceBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
	}
	
	@Override
	public void show() throws MospException {
		// ポータル用JSPパス追加
		addPortalViewList(PATH_PORTAL_CHECK_TIME_VIEW);
		// 個人ID取得
		String personalId = mospParams.getUser().getPersonalId();
		// システム日付前日取得
		Date yesterday = DateUtility.addDay(getSystemDate(), -1);
		// 期間開始日取得
		Date startDate = setStartDate(personalId, yesterday);
		// 期間開始日がないまたは開始日が終了日より後の場合
		if (startDate == null || startDate.after(yesterday)) {
			// エラーメッセージクリア
			mospParams.getErrorMessageList().clear();
			return;
		}
		// 対象期間の勤怠勤怠一覧情報リストを取得
		List<AttendanceListDto> attendList = attendanceList.getTermAttendanceList(personalId, startDate, yesterday);
		// 勤怠未申請日付リストを取得
		List<Date> dates = AttendanceUtility.getAttendanceAppliableDates(attendList);
		// メッセージ設定
		addErrorMessageNotTimeAppli(dates);
		// エラーメッセージクリア
		mospParams.getErrorMessageList().clear();
	}
	
	/**
	 * 期間開始日に締期間初日を設定する。
	 * 勤怠集計データが無く、締め期間初日からシステム日付前日までに
	 * 入社日があった場合、入社日を設定する。
	 * @param personalId 対象個人ID
	 * @param yesterday システム日付前日
	 * @return 期間開始日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date setStartDate(String personalId, Date yesterday) throws MospException {
		// システム日付取得
		Date today = getSystemDate();
		// 入社日取得
		Date entranceDate = entranceRefer.getEntranceDate(personalId);
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, today);
		// 設定適用エンティティが有効でないか勤怠管理対象でない場合
		if (application.isValid() == false || application.isTimeManaged() == false) {
			// 処理なし
			return null;
		}
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = application.getCutoffEntity();
		// 締め状態の勤怠集計データ取得
		TotalTimeEmployeeDtoInterface totalTimeEmployeeDto = totalTimeEmployeeRefer.findForPersonalList(personalId,
				TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT);
		// 勤怠集計データが存在する場合
		if (totalTimeEmployeeDto != null) {
			// 勤怠集計データ年月取得
			int totalTimeYear = totalTimeEmployeeDto.getCalculationYear();
			int totalTimeMounth = totalTimeEmployeeDto.getCalculationMonth();
			// 締期間終了日の次の日を期間初日に設定
			Date startDate = DateUtility.addDay(cutoff.getCutoffLastDate(totalTimeYear, totalTimeMounth, mospParams),
					1);
			// 入社日が締め期間終了日の次の日を期間初日より遅い場合
			if (entranceDate.after(startDate)) {
				// 入社日を締期間初日に設定
				return entranceDate;
			}
			return startDate;
		}
		// 締期間初日を取得
		Date startDate = cutoff.getCutoffFirstDate(today, mospParams);
		// 締期間初日からシステム日付前日までの期間に入社日が含まれている場合
		if (DateUtility.isTermContain(entranceDate, startDate, yesterday)) {
			// 期間初日を入社日に設定
			return entranceDate;
		}
		return startDate;
	}
	
	/**
	 * エラーメッセージを作成しポータルパラメータに設定する。
	 * @param errorDateList エラー日付リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addErrorMessageNotTimeAppli(List<Date> errorDateList) throws MospException {
		// エラー日付リストが空の場合
		if (errorDateList.isEmpty()) {
			return;
		}
		// メッセージ準備
		StringBuffer sb = new StringBuffer();
		// エラー日付毎に処理
		for (int i = 0; i < errorDateList.size(); i++) {
			// メッセージ設定
			String[] rep = { DateUtility.getStringDate(errorDateList.get(i)) };
			sb.append(mospParams.getMessage(MSG_NO_APPLI_TIME_WORK, rep));
			sb.append("\\\\n");
			// 10件以上の場合
			if (i == 9 && errorDateList.size() > 10) {
				// 他○件設定
				sb.append(mospParams.getName("Other"));
				sb.append(errorDateList.size() - (i + 1));
				sb.append(mospParams.getName("Count"));
				sb.append("\\\\n");
				break;
			}
		}
		// 置換文字に設定
		String[] rep = { sb.toString() };
		// パラメータに設定
		mospParams.addGeneralParam(PRM_ATTENDANCE_CHECK_MESSAGE, mospParams.getMessage(MSG_CONFIRM, rep));
	}
	
	@Override
	public void regist() {
		// 処理なし		
	}
}
