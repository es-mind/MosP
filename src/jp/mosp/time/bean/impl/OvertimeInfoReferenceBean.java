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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.OvertimeInfoReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 残業情報参照クラス。
 */
public class OvertimeInfoReferenceBean extends PlatformBean implements OvertimeInfoReferenceBeanInterface {
	
	/**
	 * 週の計算の比較基準日数。<br>
	 */
	public static final int						WEEK_CALCULATE_DAY	= -7;
	
	/**
	 * 勤怠データDAO。
	 */
	protected AttendanceDaoInterface			attendanceDao;
	
	/**
	 * 残業申請データDAO。
	 */
	protected OvertimeRequestDaoInterface		overtimeRequestDao;
	
	/**
	 * ワークフローDAO。
	 */
	protected WorkflowDaoInterface				workflowDao;
	
	/**
	 * 勤怠設定参照処理。<br>
	 */
	protected TimeSettingReferenceBeanInterface	timeSettingRefer;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface			timeMaster;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public OvertimeInfoReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		attendanceDao = createDaoInstance(AttendanceDaoInterface.class);
		overtimeRequestDao = createDaoInstance(OvertimeRequestDaoInterface.class);
		workflowDao = createDaoInstance(WorkflowDaoInterface.class);
		// Beanを準備
		timeSettingRefer = createBeanInstance(TimeSettingReferenceBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
	}
	
	@Override
	public String getStringPossibleTime1Week(String personalId) throws MospException {
		// 対象日(システム日付)を準備
		Date targetDate = getSystemDate();
		// 対象個人ID及び対象日付で設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			// 限度設定無し文字列(-)を取得
			return getNoLimitString();
		}
		// 勤怠設定エンティティを取得
		TimeSettingEntityInterface timeSetting = timeSettingRefer.getEntity(application.getTimeSettingDto());
		// 1週間(week1)限度基準情報が存在しない場合
		if (timeSetting.isOneWeekExist() == false) {
			// 限度設定無し文字列(-)を取得
			return getNoLimitString();
		}
		// 残業申請可能時間(1週間)を取得
		int time = getPossibleTime1Week(personalId, targetDate, timeSetting);
		// 残業申請可能時間(1週間：文字列)を取得
		return TimeUtility.getStringJpTime(mospParams, time);
	}
	
	@Override
	public String getStringPossibleTime1Month(String personalId) throws MospException {
		// 対象日(システム日付)を準備
		Date targetDate = getSystemDate();
		// 対象個人ID及び対象日付で設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			// 限度設定無し文字列(-)を取得
			return getNoLimitString();
		}
		// 勤怠設定エンティティを取得
		TimeSettingEntityInterface timeSetting = timeSettingRefer.getEntity(application.getTimeSettingDto());
		// 1ヶ月(month1)限度基準情報が存在しない場合
		if (timeSetting.isOneMonthExist() == false) {
			// 限度設定無し文字列(-)を取得
			return getNoLimitString();
		}
		// 残業申請可能時間(1ヶ月)を取得
		int time = getPossibleTime1Month(personalId, targetDate, application.getCutoffEntity(), timeSetting);
		// 残業申請可能時間(1ヶ月：文字列)を取得
		return TimeUtility.getStringJpTime(mospParams, time);
	}
	
	/**
	 * 残業申請可能時間(1週間)を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param entity     勤怠設定エンティティ
	 * @return 残業申請可能時間(1ヶ月)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getPossibleTime1Week(String personalId, Date targetDate, TimeSettingEntityInterface entity)
			throws MospException {
		// 起算曜日準備
		Date startDate = getDateClone(targetDate);
		// 曜日取得-週の起算曜日
		int difference = DateUtility.getDayOfWeek(startDate) - entity.getStartWeek();
		if (difference >= 0) {
			while (difference != 0) {
				startDate = DateUtility.addDay(startDate, -1);
				difference--;
			}
		} else {
			while (difference != 0) {
				startDate = DateUtility.addDay(startDate, 1);
				difference++;
			}
			startDate = DateUtility.addDay(startDate, WEEK_CALCULATE_DAY);
		}
		Date endDate = DateUtility.addDay(startDate, 6);
		List<AttendanceDtoInterface> attendanceList = attendanceDao.findForList(personalId, startDate, endDate);
		boolean requestFlag = true;
		int overTimeOut = 0;
		int requestTime = 0;
		Date indexDate = startDate;
		while (!indexDate.after(endDate)) {
			for (AttendanceDtoInterface attendanceDto : attendanceList) {
				if (indexDate.equals(attendanceDto.getWorkDate())) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
					if (workflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					overTimeOut += attendanceDto.getOvertimeOut();
					requestFlag = false;
					break;
				}
			}
			if (requestFlag) {
				List<OvertimeRequestDtoInterface> list = overtimeRequestDao.findForList(personalId, indexDate);
				for (OvertimeRequestDtoInterface requestDto : list) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
					if (workflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
						// 取下の場合
						continue;
					}
					requestTime += requestDto.getRequestTime();
				}
			}
			requestFlag = true;
			indexDate = DateUtility.addDay(indexDate, 1);
		}
		return entity.getOneWeekLimit() - overTimeOut - requestTime;
	}
	
	/**
	 * 残業申請可能時間(1ヶ月)を取得する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetDate  対象日
	 * @param cutoff      締日エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 残業申請可能時間(1ヶ月)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getPossibleTime1Month(String personalId, Date targetDate, CutoffEntityInterface cutoff,
			TimeSettingEntityInterface timeSetting) throws MospException {
		// 締期間の初日及び最終日取得
		Date firstDate = cutoff.getCutoffFirstDate(targetDate, mospParams);
		Date lastDate = cutoff.getCutoffLastDate(targetDate, mospParams);
		// 締期間の勤怠データリストを取得
		List<AttendanceDtoInterface> attendanceList = attendanceDao.findForList(personalId, firstDate, lastDate);
		boolean requestFlag = true;
		int overTimeOut = 0;
		int requestTime = 0;
		Date indexDate = firstDate;
		while (!indexDate.after(lastDate)) {
			for (AttendanceDtoInterface attendanceDto : attendanceList) {
				if (indexDate.equals(attendanceDto.getWorkDate())) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
					if (workflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					overTimeOut += attendanceDto.getOvertimeOut();
					requestFlag = false;
					break;
				}
			}
			if (requestFlag) {
				List<OvertimeRequestDtoInterface> list = overtimeRequestDao.findForList(personalId, indexDate);
				for (OvertimeRequestDtoInterface requestDto : list) {
					WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
					if (workflowDto == null) {
						continue;
					}
					if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
						// 下書の場合
						continue;
					}
					if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
						// 取下の場合
						continue;
					}
					requestTime += requestDto.getRequestTime();
				}
			}
			requestFlag = true;
			indexDate = DateUtility.addDay(indexDate, 1);
		}
		return timeSetting.getOneMonthLimit() - overTimeOut - requestTime;
	}
	
	/**
	 * 限度設定無し文字列(-)を取得する。<br>
	 * @return 限度設定無し文字列(-)
	 */
	protected String getNoLimitString() {
		return PfNameUtility.hyphen(mospParams);
	}
	
}
