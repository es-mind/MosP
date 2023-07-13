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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.time.bean.CalendarWorkingDaysBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * カレンダ勤務日数参照クラス。
 */
public class CalendarWorkingDaysBean extends PlatformBean implements CalendarWorkingDaysBeanInterface {
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface		timeMaster;
	
	/**
	 * プラットフォーム関連マスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface	platformMaster;
	
	/**
	 * カレンダユーティリティインターフェース。<br> 
	 */
	protected ScheduleUtilBeanInterface		scheduleUtil;
	
	
	@Override
	public void initBean() throws MospException {
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
	}
	
	@Override
	public int getCalendarWorkingDays(String personalId, String cutoffCode, int targetYear, int targetMonth)
			throws MospException {
		// カレンダ勤務日数をカウントするための変数を用意
		int calendarWorkingDays = 0;
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth);
		// 締期間の開始及び最終日を取得
		Date firstDate = cutoff.getCutoffFirstDate(targetYear, targetMonth, mospParams);
		Date lastDate = cutoff.getCutoffLastDate(targetYear, targetMonth, mospParams);
		// 対象日毎に処理
		for (Date calendarTargetDate : TimeUtility.getDateList(firstDate, lastDate)) {
			// 対象日時点で退職している場合
			if (platformMaster.isRetired(personalId, calendarTargetDate)) {
				continue;
			}
			// 対象個人IDの対象日に休職情報が設定されている場合
			if (platformMaster.isSuspended(personalId, calendarTargetDate)) {
				continue;
			}
			// 対象個人IDの対象日におけるカレンダマスタに登録されている勤務形態コードを取得
			String workTypeCode = scheduleUtil.getScheduledWorkTypeCodeNoMessage(personalId, calendarTargetDate);
			// 勤務形態コードが所定休日又は法定休日の場合
			if (TimeUtility.isHoliday(workTypeCode)) {
				continue;
			}
			// 勤務形態コードが空欄(カレンダマスタが設定されていない又は入社日より前)の場合
			if (MospUtility.isEmpty(workTypeCode)) {
				continue;
			}
			// 勤務形態コードが所定休日又は法定休日又は空欄ではない場合
			calendarWorkingDays++;
		}
		// カレンダ勤務日数を取得
		return calendarWorkingDays;
	}
	
	@Override
	public void setPlatformMaster(PlatformMasterBeanInterface platformMaster) {
		// プラットフォームマスタ参照処理を設定
		this.platformMaster = platformMaster;
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		this.timeMaster = timeMaster;
		// 勤怠関連マスタ参照処理をBeanに設定
		scheduleUtil.setTimeMaster(timeMaster);
	}
}
