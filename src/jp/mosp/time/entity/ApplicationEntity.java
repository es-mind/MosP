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
package jp.mosp.time.entity;

import java.util.Calendar;
import java.util.Date;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.utils.TimeMessageUtility;

/**
 * 設定適用エンティティクラス。<br>
 */
public class ApplicationEntity {
	
	/**
	 * ポータル出退勤ボタン表示コード(非表示)。
	 */
	public static final int				CODE_TIME_BUTTON_NONE	= 9;
	
	/**
	 * ポータル休憩ボタン表示コード(非表示)。
	 */
	public static final int				CODE_REST_BUTTON_NONE	= 2;
	
	/**
	 * 設定適用情報。<br>
	 */
	protected ApplicationDtoInterface	applicationDto;
	
	/**
	 * 勤怠設定情報。<br>
	 */
	protected TimeSettingDtoInterface	timeSettingDto;
	
	/**
	 * 締日エンティティ。<br>
	 */
	protected CutoffEntityInterface		cutoffEntity;
	
	/**
	 * 有給休暇設定情報。<br>
	 */
	protected PaidHolidayDtoInterface	paidHolidayDto;
	
	/**
	 * 対象日(設定適用情報等の取得に用いた対象日)。<br>
	 * エラーメッセージに用いる。<br>
	 */
	protected Date						targetDate;
	
	
	/**
	 * コンストラクタ。<br>
	 * @param applicationDto 設定適用情報
	 */
	public ApplicationEntity(ApplicationDtoInterface applicationDto) {
		// フィールドに設定
		this.applicationDto = applicationDto;
	}
	
	/**
	 * 設定適用エンティティが有効であるかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：有効である、false：無効である)
	 */
	public boolean isValid() {
		// 設定適用情報が存在しない場合
		if (applicationDto == null) {
			// 無効であると判断
			return false;
		}
		// 勤怠設定情報が存在しない場合
		if (timeSettingDto == null) {
			// 無効であると判断
			return false;
		}
		// 締日情報が存在しない場合
		if (cutoffEntity.isExist() == false) {
			// 無効であると判断
			return false;
		}
		// 有給休暇情報が存在しない場合
		if (paidHolidayDto == null) {
			// 無効であると判断
			return false;
		}
		// 必要な情報が揃っている場合
		return true;
	}
	
	/**
	 * 設定適用エンティティが有効であるかを確認する。<br>
	 * 有効でない場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：有効である、false：無効である)
	 */
	public boolean isValid(MospParams mospParams) {
		// 確認結果を準備
		boolean isValid = true;
		// 設定適用情報が存在しない場合
		if (applicationDto == null) {
			// 無効であると判断
			isValid = false;
			// エラーメッセージを設定
			TimeMessageUtility.addErrorApplicationDefect(mospParams, targetDate);
		}
		// 確認結果を取得
		return isValid;
	}
	
	/**
	 * 勤怠設定コードを取得する。<br>
	 * 勤怠設定コードが取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @return 勤怠設定コード
	 */
	public String getWorkSettingCode() {
		// 設定適用情報確認
		if (applicationDto == null || MospUtility.isEmpty(applicationDto.getWorkSettingCode())) {
			return "";
		}
		// 勤怠設定コード取得
		return applicationDto.getWorkSettingCode();
	}
	
	/**
	 * カレンダコードを取得する。<br>
	 * カレンダコードが取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @return カレンダコード
	 */
	public String getScheduleCode() {
		// 設定適用情報確認
		if (applicationDto == null || MospUtility.isEmpty(applicationDto.getScheduleCode())) {
			return "";
		}
		// カレンダコード取得
		return applicationDto.getScheduleCode();
	}
	
	/**
	 * 有給休暇設定コードを取得する。<br>
	 * 有給休暇設定コードが取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @return カレンダコード
	 */
	public String getPaidHolidayCode() {
		// 設定適用情報確認
		if (applicationDto == null || MospUtility.isEmpty(applicationDto.getPaidHolidayCode())) {
			return "";
		}
		// 有給休暇設定コードを取得
		return applicationDto.getPaidHolidayCode();
	}
	
	/**
	 * 締日コードを取得する。<br>
	 * 締日コードが取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @return 勤怠設定コード
	 */
	public String getCutoffCode() {
		// 勤怠設定情報確認
		if (timeSettingDto == null || timeSettingDto.getCutoffCode() == null) {
			return "";
		}
		// 締日コード取得
		return timeSettingDto.getCutoffCode();
	}
	
	/**
	 * 締日を取得する。<br>
	 * <br>
	 * 締日が存在しない場合は、0(月末締)を返す。<br>
	 * <br>
	 * @return 締日
	 */
	public int getCutoffDate() {
		// 締日情報を取得
		return cutoffEntity.getCutoffDate();
	}
	
	/**
	 * 勤怠管理対象であるかを確認する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、勤怠管理対象でないと判断する。<br>
	 * <br>
	 * @return 確認結果(true：勤怠管理対象である、false：そうでない)
	 */
	public boolean isTimeManaged() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return false;
		}
		// 勤怠管理対象が有効であるかを確認
		return PlatformUtility.isActivate(timeSettingDto.getTimeManagementFlag());
	}
	
	/**
	 * 週の起算曜日を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、1(日曜)を返す。<br>
	 * <br>
	 * @return 週の起算曜日
	 */
	public int getStartDayOfWeek() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return Calendar.SUNDAY;
		}
		// 週の起算曜日を取得
		return timeSettingDto.getStartWeek();
	}
	
	/**
	 * 未承認仮締を取得する。<br>
	 * 締日情報が存在しない場合は、0(有効)を取得する。<br>
	 * @return 未承認仮締
	 */
	public int getNoApproval() {
		// 未承認仮締を取得
		return cutoffEntity.getNoApproval();
	}
	
	/**
	 * 勤務予定時間表示設定を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 勤務予定時間表示設定（true：勤務予定時間表示有効、false：無効)
	 */
	public boolean useScheduledTime() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return false;
		}
		// 勤務予定時間表示設定を取得
		return timeSettingDto.getUseScheduledTime() == MospConst.INACTIVATE_FLAG_OFF;
	}
	
	/**
	 * 自己月締が有効であるかを確認する。<br>
	 * 締日情報が存在しない場合は、false(無効)を取得する。<br>
	 * <br>
	 * @return 確認結果(true：自己月締有効、false：無効)
	 */
	public boolean isSelfTightening() {
		// 自己月締設定を取得
		return cutoffEntity.isSelfTightening();
	}
	
	/**
	 * ポータル出退勤ボタン表示設定を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、9(非表示)を返す。<br>
	 * <br>
	 * @return ポータル出退勤ボタン表示設定
	 */
	public int getPortalTimeButtons() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return CODE_TIME_BUTTON_NONE;
		}
		// 勤務予定時間表示設定を取得
		return timeSettingDto.getPortalTimeButtons();
	}
	
	/**
	 * ポータル休憩ボタン表示設定を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、2(非表示)を返す。<br>
	 * <br>
	 * @return ポータル休憩ボタン表示設定
	 */
	public int getPortalRestButtons() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return CODE_REST_BUTTON_NONE;
		}
		// 勤務予定時間表示設定を取得
		return timeSettingDto.getPortalRestButtons();
	}
	
	/**
	 * 見込月を取得する。<br>
	 * <br>
	 * 勤怠設定情報が存在しない場合は、0を返す。<br>
	 * <br>
	 * @return ポータル出退勤ボタン表示設定
	 */
	public String getProspectsMonths() {
		// 勤怠設定情報を確認
		if (timeSettingDto == null) {
			return MospConst.CHECKBOX_OFF;
		}
		// 勤務予定時間表示設定を取得
		return timeSettingDto.getProspectsMonths();
	}
	
	/**
	 * 有給休暇設定の時間単位取得が有効であるかを確認する。<br>
	 * @return 確認結果(true：有給休暇設定の時間単位取得が有効である、false：そうでない)
	 */
	public boolean isHourlyPaidHolidayAvailable() {
		// 有給休暇情報が存在しない場合
		if (paidHolidayDto == null) {
			// 有給休暇設定の時間単位取得が有効でないと判断
			return false;
		}
		// 勤務予定時間表示設定を取得
		return PlatformUtility.isActivate(paidHolidayDto.getTimelyPaidHolidayFlag());
	}
	
	/**
	 * @return applicationDto
	 */
	public ApplicationDtoInterface getApplicationDto() {
		return applicationDto;
	}
	
	/**
	 * @return timeSettingDto
	 */
	public TimeSettingDtoInterface getTimeSettingDto() {
		return timeSettingDto;
	}
	
	/**
	 * @param timeSettingDto セットする timeSettingDto
	 */
	public void setTimeSettingDto(TimeSettingDtoInterface timeSettingDto) {
		this.timeSettingDto = timeSettingDto;
	}
	
	/**
	 * 締日エンティティを取得する。<br>
	 * @return 締日エンティティ
	 */
	public CutoffEntityInterface getCutoffEntity() {
		return cutoffEntity;
	}
	
	/**
	 * 締日エンティティを設定する。<br>
	 * @param cutoffEntity 締日エンティティ
	 */
	public void setCutoffEntity(CutoffEntityInterface cutoffEntity) {
		this.cutoffEntity = cutoffEntity;
	}
	
	/**
	 * @return cutoffDto
	 */
	public CutoffDtoInterface getCutoffDto() {
		return cutoffEntity.getCutoffDto();
	}
	
	/**
	 * @return paidHolidayDto
	 */
	public PaidHolidayDtoInterface getPaidHolidayDto() {
		return paidHolidayDto;
	}
	
	/**
	 * @param paidHolidayDto セットする paidHolidayDto
	 */
	public void setPaidHolidayDto(PaidHolidayDtoInterface paidHolidayDto) {
		this.paidHolidayDto = paidHolidayDto;
	}
	
	/**
	 * 対象日を取得する。<br>
	 * @return 対象日
	 */
	public Date getTargetDate() {
		return CapsuleUtility.getDateClone(targetDate);
	}
	
	/**
	 * 対象日を設定する。<br>
	 * @param targetDate 対象日
	 */
	public void setTargetDate(Date targetDate) {
		this.targetDate = CapsuleUtility.getDateClone(targetDate);
	}
	
}
