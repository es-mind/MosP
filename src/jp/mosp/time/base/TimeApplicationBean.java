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
package jp.mosp.time.base;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 勤怠管理における設定適用等に関する基本機能を提供する。
 */
public abstract class TimeApplicationBean extends TimeBean {
	
	/**
	 * 設定適用マスタ参照クラス。
	 */
	protected ApplicationReferenceBeanInterface	applicationRefer;
	
	/**
	 * 勤怠設定マスタ参照クラス。
	 */
	protected TimeSettingReferenceBeanInterface	timeSettingRefer;
	
	/**
	 * 締日マスタ参照クラス。
	 */
	protected CutoffReferenceBeanInterface		cutoffRefer;
	
	/**
	 * 有給休暇マスタ参照クラス。
	 */
	protected PaidHolidayReferenceBeanInterface	paidHolidayRefer;
	
	/**
	 * 設定適用情報。
	 */
	protected ApplicationDtoInterface			applicationDto;
	
	/**
	 * 勤怠設定情報。
	 */
	protected TimeSettingDtoInterface			timeSettingDto;
	
	/**
	 * 締日情報。
	 */
	protected CutoffDtoInterface				cutoffDto;
	
	/**
	 * 有給休暇情報。
	 */
	protected PaidHolidayDtoInterface			paidHolidayDto;
	
	
	/**
	 * コンストラクタ。
	 */
	public TimeApplicationBean() {
		// 継承基の処理を実行
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 各種クラスの取得
		cutoffRefer = (CutoffReferenceBeanInterface)createBean(CutoffReferenceBeanInterface.class);
		timeSettingRefer = (TimeSettingReferenceBeanInterface)createBean(TimeSettingReferenceBeanInterface.class);
		applicationRefer = (ApplicationReferenceBeanInterface)createBean(ApplicationReferenceBeanInterface.class);
		paidHolidayRefer = (PaidHolidayReferenceBeanInterface)createBean(PaidHolidayReferenceBeanInterface.class);
	}
	
	/**
	 * 対象個人ID及び対象日付で設定適用を取得し、設定する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日付
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setApplicationSettings(String personalId, Date targetDate) throws MospException {
		// 対象日付における取得対象個人IDの設定適用情報を取得
		applicationDto = applicationRefer.findForPerson(personalId, targetDate);
		// 設定適用情報確認
		applicationRefer.chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
	}
	
	/**
	 * 対象個人ID及び対象日付で勤怠設定情報を取得し、設定する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日付
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setTimeSettings(String personalId, Date targetDate) throws MospException {
		// 対象日付における取得対象個人IDの設定適用情報を取得
		setApplicationSettings(personalId, targetDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 対象日付における勤怠設定情報を取得
		timeSettingDto = timeSettingRefer.getTimeSettingInfo(applicationDto.getWorkSettingCode(), targetDate);
		// 勤怠設定情報確認
		timeSettingRefer.chkExistTimeSetting(timeSettingDto, targetDate);
	}
	
	/**
	 * 対象個人ID及び対象日付で設定締日情報を取得し、設定する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日付
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCutoffSettings(String personalId, Date targetDate) throws MospException {
		// 対象日付における取得対象個人IDの勤怠設定情報を取得
		setTimeSettings(personalId, targetDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 対象日付における締日情報を取得
		cutoffDto = cutoffRefer.getCutoffInfo(timeSettingDto.getCutoffCode(), targetDate);
		// 締日情報確認
		if (cutoffDto == null) {
			// エラーメッセージ設定
			addCutoffNotExistErrorMessage(targetDate);
			return;
		}
	}
	
	/**
	 * 対象個人ID及び対象日付で設定適用を取得し、設定する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日付
	 * @return 取得結果(true：取得成功、false：取得失敗)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean hasApplicationSettings(String personalId, Date targetDate) throws MospException {
		// 対象日付における取得対象個人IDの設定適用情報を取得
		applicationDto = applicationRefer.findForPerson(personalId, targetDate);
		// 設定適用情報確認
		if (applicationDto == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 対象個人ID及び対象日付で勤怠設定情報を取得し、設定する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日付
	 * @return 取得結果(true：取得成功、false：取得失敗)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean hasTimeSettings(String personalId, Date targetDate) throws MospException {
		// 対象日付における取得対象個人IDの設定適用情報を取得
		if (hasApplicationSettings(personalId, targetDate) == false) {
			return false;
		}
		// 対象日付における勤怠設定情報を取得
		timeSettingDto = timeSettingRefer.getTimeSettingInfo(applicationDto.getWorkSettingCode(), targetDate);
		// 勤怠設定情報確認
		if (timeSettingDto == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 対象個人ID及び対象日付で設定有給休暇情報を取得し、設定する。<br>
	 * 取得できない場合はエラーメッセージを追加する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日付
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPaidHolidaySettings(String personalId, Date targetDate) throws MospException {
		// 対象日付における取得対象個人IDの設定適用情報を取得
		setApplicationSettings(personalId, targetDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 対象日付における有給休暇情報を取得
		paidHolidayDto = paidHolidayRefer.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), targetDate);
		// 有給休暇マスタの存在チェック
		paidHolidayRefer.chkExistPaidHoliday(paidHolidayDto, targetDate);
		// 有給休暇情報確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
	}
	
	/**
	 * 対象個人ID及び対象日付で設定有給休暇情報を取得し、設定する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日付
	 * @return 取得結果(true：取得成功、false：取得失敗)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean hasPaidHolidaySettings(String personalId, Date targetDate) throws MospException {
		// 対象日付における取得対象個人IDの設定適用情報を取得
		if (hasApplicationSettings(personalId, targetDate) == false) {
			return false;
		}
		// 対象日付における有給休暇情報を取得
		paidHolidayDto = paidHolidayRefer.getPaidHolidayInfo(applicationDto.getPaidHolidayCode(), targetDate);
		// 有給休暇情報確認
		if (paidHolidayDto == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 締日情報が存在しない場合のエラーメッセージを設定する。<br>
	 * @param targetDate 基準日
	 */
	protected void addCutoffNotExistErrorMessage(Date targetDate) {
		String[] rep = { getStringDate(targetDate), mospParams.getName("CutoffDate", "Information") };
		mospParams.addErrorMessage(TimeMessageConst.MSG_SETTING_APPLICATION_DEFECT, rep);
	}
	
}
