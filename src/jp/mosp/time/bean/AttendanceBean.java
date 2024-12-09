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
package jp.mosp.time.bean;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.impl.WorkOnHolidayRequestReferenceBean;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠管理における勤怠データ関連の基本機能を提供する。<br>
 */
public abstract class AttendanceBean extends TimeBean {
	
	/**
	 * 勤怠データ参照クラス。
	 */
	protected AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * 設定適用マスタ参照クラス。
	 */
	protected ApplicationReferenceBeanInterface				applicationReference;
	
	/**
	 * 勤怠設定マスタ参照クラス。
	 */
	protected TimeSettingReferenceBeanInterface				timeSettingReference;
	
	/**
	 * カレンダユーティリティクラス。
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 勤務形態マスタ参照クラス。
	 */
	protected WorkTypeReferenceBeanInterface				workTypeReference;
	
	/**
	 * 勤務形態マスタ項目情報参照クラス。
	 */
	protected WorkTypeItemReferenceBeanInterface			workTypeItemReference;
	
	/**
	 * 休日出勤申請参照クラス。
	 */
	protected WorkOnHolidayRequestReferenceBeanInterface	workOnHoliday;
	
	/**
	 * 振替休日参照クラス。
	 */
	protected SubstituteReferenceBeanInterface				substituteReference;
	
	/**
	 * ワークフロー統合クラス。
	 */
	protected WorkflowIntegrateBeanInterface				workflow;
	
	
	/**
	 * コンストラクタ。
	 */
	public AttendanceBean() {
		// 継承基の処理を実行
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		attendanceReference = createBeanInstance(AttendanceReferenceBeanInterface.class);
		applicationReference = createBeanInstance(ApplicationReferenceBeanInterface.class);
		timeSettingReference = createBeanInstance(TimeSettingReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		workTypeReference = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		workTypeItemReference = createBeanInstance(WorkTypeItemReferenceBeanInterface.class);
		workOnHoliday = createBeanInstance(WorkOnHolidayRequestReferenceBean.class);
		substituteReference = createBeanInstance(SubstituteReferenceBeanInterface.class);
		workflow = createBeanInstance(WorkflowIntegrateBeanInterface.class);
	}
	
	/**
	 * 休日出勤申請情報から休日出勤時の予定勤務形態を取得する。<br>
	 * @param dto 休日出勤申請情報
	 * @return 休日出勤時の予定勤務形態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkOnHolidayWorkType(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 休日出勤申請情報から休日出勤時の予定勤務形態を取得
		String workTypeCode = TimeUtility.getWorkOnHolidayWorkType(dto);
		// 勤務形態コードを取得できた場合(振替出勤(勤務形態変更無し)でない場合)
		if (MospUtility.isEmpty(workTypeCode) == false) {
			return workTypeCode;
		}
		// 振替休出日の勤務形態を取得
		return getSubstituteWorkType(dto);
	}
	
	/**
	 * 振替休出日の勤務形態を取得する。
	 * @param workOnHolidayRequestDto 休日出勤申請情報
	 * @return 勤務形態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getSubstituteWorkType(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto)
			throws MospException {
		// 予定として表示する振替休日情報を取得
		SubstituteDtoInterface substituteDto = substituteReference
			.getSubstituteDto(workOnHolidayRequestDto.getPersonalId(), workOnHolidayRequestDto.getRequestDate());
		// 振替休日情報確認
		if (substituteDto == null) {
			return MospConst.STR_EMPTY;
		}
		return scheduleUtil.getScheduledWorkTypeCode(substituteDto.getPersonalId(), substituteDto.getSubstituteDate());
	}
	
	/**
	 * 勤怠申請後処理群を実行する。<br>
	 * @param dto 勤怠情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void afterApplyAttendance(AttendanceDtoInterface dto) throws MospException {
		// 勤怠情報が存在しない場合
		if (dto == null) {
			// 処理無し
			return;
		}
		// エラーメッセージが設定されている場合
		if (mospParams.hasErrorMessage()) {
			// 処理無し
			return;
		}
		// 勤怠申請後処理毎に処理
		for (String modelClass : mospParams.getApplicationProperties(APP_AFTER_APPLY_ATT)) {
			// 勤怠申請後処理を取得し実行
			((AfterApplyAttendanceBeanInterface)createBean(modelClass)).execute(dto);
		}
	}
	
}
