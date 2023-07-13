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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * カレンダユーティリティ処理の基となるクラス。<br>
 */
public abstract class ScheduleUtilBaseBean extends PlatformBean {
	
	/**
	 * 振替休日情報参照処理。<br>
	 */
	protected SubstituteReferenceBeanInterface	substituteRefer;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface			timeMaster;
	
	
	/**
	 * {@link ScheduleUtilBaseBean}を生成する。<br>
	 */
	public ScheduleUtilBaseBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		substituteRefer = createBeanInstance(SubstituteReferenceBeanInterface.class);
		// 勤怠関連マスタ参照処理を取得
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
	}
	
	/**
	 * カレンダコードを取得する。<br>
	 * カレンダコードが取得できなかった場合は空文字を返す。<br>
	 * @param personalId           個人ID
	 * @param targetDate           対象日
	 * @param isErrorMessageNeeded エラーメッセージ要否(true：エラーメッセージ要、false：不要)
	 * @return カレンダコード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getScheduleCode(String personalId, Date targetDate, boolean isErrorMessageNeeded)
			throws MospException {
		// 設定適用情報を取得
		ApplicationDtoInterface dto = getApplicationDto(personalId, targetDate, isErrorMessageNeeded);
		// 設定適用情報を取得できなかった場合
		if (dto == null) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// カレンダコードを取得
		return dto.getScheduleCode();
	}
	
	/**
	 * 設定適用情報を取得する。<br>
	 * 設定適用情報を取得できなかった場合はnullを返す。<br>
	 * @param personalId           個人ID
	 * @param targetDate           対象日
	 * @param isErrorMessageNeeded エラーメッセージ要否(true：エラーメッセージ要、false：不要)
	 * @return カレンダコード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ApplicationDtoInterface getApplicationDto(String personalId, Date targetDate,
			boolean isErrorMessageNeeded) throws MospException {
		// 設定適用情報を取得
		ApplicationDtoInterface dto = timeMaster.getApplication(personalId, targetDate);
		// 設定適用エンティティを取得できなかった場合(エラーメッセージが必要である場合)
		if (dto == null && isErrorMessageNeeded) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorApplicationDefect(mospParams, targetDate);
		}
		// 設定適用情報を取得
		return dto;
	}
	
	/**
	 * 各種申請から勤務形態コードを取得する。<br>
	 * 勤務形態に影響を及ぼす申請(振替休日、振出・休出申請、勤務形態変更申請、時差出勤申請)を考慮する。<br>
	 * 各種申請からでは勤務形態コードが決まらない場合は、空文字を返す。<br>
	 * <br>
	 * @param difference     時差出勤申請情報(承認済)
	 * @param workTypeChange 勤務形態変更申請情報(承認済)
	 * @param workOnHoliday  振出・休出申請情報(承認済)
	 * @param substitutes    振替休日情報(承認済)リスト(対象日が振替休日情報の振替日)
	 * @return 勤務形態コード
	 */
	protected String getRequestedWorkTypeCode(DifferenceRequestDtoInterface difference,
			WorkTypeChangeRequestDtoInterface workTypeChange, WorkOnHolidayRequestDtoInterface workOnHoliday,
			List<SubstituteDtoInterface> substitutes) {
		// 時差出勤申請情報(承認済)が存在する場合
		if (difference != null) {
			// 時差出勤区分を取得
			return difference.getDifferenceType();
		}
		// 勤務形態変更申請情報(承認済)が存在する場合
		if (workTypeChange != null) {
			// 勤務形態変更申請情報の勤務形態コードを取得
			return workTypeChange.getWorkTypeCode();
		}
		
		// 振出・休出申請情報(承認済)が存在する場合
		if (workOnHoliday != null) {
			// 振替申請区分を取得
			int substitute = workOnHoliday.getSubstitute();
			// 振替出勤申請(勤務形態変更あり)の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
				// 振出・休出申請情報から勤務形態コードを取得
				return workOnHoliday.getWorkTypeCode();
			}
			// 休日出勤申請の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休出種別を取得
				String workOnHolidayType = workOnHoliday.getWorkOnHolidayType();
				// 休出種別が法定休日である場合
				if (MospUtility.isEqual(workOnHolidayType, TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
					// 法定休日出勤を取得
					return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
				}
				// 休出種別が所定休日である場合
				if (MospUtility.isEqual(workOnHolidayType, TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
					// 所定休日出勤を取得
					return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
				}
			}
		}
		// 振出・休出申請情報(承認済)が存在しない場合
		if (workOnHoliday == null) {
			// 振替休日情報(承認済)毎に処理
			for (SubstituteDtoInterface substitute : substitutes) {
				// 振替休日が全休である場合
				if (TimeRequestUtility.isHolidayRangeAll(substitute)) {
					// 振替種別を取得
					return substitute.getSubstituteType();
				}
			}
		}
		// 各種申請からでは勤務形態コードが決まらない場合(空文字を取得)
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * カレンダ日情報を取得するための対象日を取得する。<br>
	 * 振出・休出申請(承認済)(振替出勤(勤務形態変更なし・午前・午後))がある場合は、振替元の勤務日を取得する。<br>
	 * 上記申請が無い場合は、引数の勤務日を返す。<br>
	 * <br>
	 * @param workDate      勤務日
	 * @param workOnHoliday 振出・休出申請情報(承認済)
	 * @return カレンダ日情報を取得するための対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getScheduleTargetDate(Date workDate, WorkOnHolidayRequestDtoInterface workOnHoliday)
			throws MospException {
		// 振出・休出申請(承認済)が存在しない場合
		if (workOnHoliday == null) {
			// 引数の勤務日を取得
			return workDate;
		}
		// 振替申請区分を取得
		int substitute = workOnHoliday.getSubstitute();
		// 振替出勤申請(勤務形態変更なし・午前・午後)でない場合
		if (substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
				&& substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				&& substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 引数の勤務日を取得
			return workDate;
		}
		// 振替休日情報(承認済)リスト(対象日が振替休日情報の出勤日)を準備
		List<SubstituteDtoInterface> substitutes = substituteRefer.getSubstituteList(workOnHoliday.getWorkflow());
		// 振替休日情報(承認済)リスト(対象日が振替休日情報の出勤日)が空である場合
		if (substitutes.isEmpty()) {
			// 引数の勤務日を取得
			return workDate;
		}
		// 振替元の勤務日を取得
		return MospUtility.getFirstValue(substitutes).getSubstituteDate();
	}
	
}
