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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;

/**
 * 休暇数参照処理インターフェース。<br>
 */
public interface HolidayInfoReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 残数のある休暇残情報群(有効日昇順)を取得する。<br>
	 * @param personalId   個人ID
	 * @param targetDate   対象日
	 * @param holidayType  休暇区分
	 * @return 残数のある休暇残情報群(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<HolidayRemainDto> getRemainHolidays(String personalId, Date targetDate, int holidayType) throws MospException;
	
	/**
	 * 残数のある休暇残情報群(特別休暇+その他休暇)を取得する。<br>
	 * 残数のある特別休暇残情報群(有効日昇順)の後に、残数のあるその他休暇残情報群(有効日昇順)を
	 * 付けて残数のある休暇残情報群を作成している。<br>
	 * @param personalId   個人ID
	 * @param targetDate   対象日
	 * @return 残数のある休暇残情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<HolidayRemainDto> getRemainHolidays(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 休暇プルダウン用配列(残数のある休暇)を取得する。<br>
	 * 休暇申請画面で用いる。<br>
	 * @param personalId   個人ID
	 * @param targetDate   対象日
	 * @param holidayType  休暇区分
	 * @return 休暇プルダウン用配列(残数のある休暇)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getRemainArray(String personalId, Date targetDate, int holidayType) throws MospException;
	
	/**
	 * 休暇プルダウン用配列(残数のある休暇)を取得する。<br>
	 * 重複する休暇コードを省き休暇コード順の配列を作成する。<br>
	 * 休暇残情報群中の休暇種別は同一であることを前提とする。<br>
	 * @param remains   休暇残情報群
	 * @param isAbbr    略称利用フラグ(true：略称、false：名称)
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return 休暇プルダウン用配列(残数のある休暇)
	 */
	String[][] getRemainArray(Set<HolidayRemainDto> remains, boolean isAbbr, boolean needBlank);
	
	/**
	 * 申請可能な休暇残情報(残数があり有効日が最も早いもの)を取得する。<br>
	 * @param personalId  個人ID
	 * @param targetDate  対象日
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 申請可能な休暇残情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRemainDto getAppliableHoliday(String personalId, Date targetDate, String holidayCode, int holidayType)
			throws MospException;
	
	/**
	 * 休暇情報リスト(有効日昇順)を取得する。<br>
	 * 対象日時点で有効(対象日が有効日から取得期限の間)な休暇データを取得する。<br>
	 * @param personalId  個人ID
	 * @param targetDate  対象日
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇情報リスト(有効日昇順)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDataDtoInterface> getHolidays(String personalId, Date targetDate, String holidayCode, int holidayType)
			throws MospException;
	
	/**
	 * 1日の分数を取得する。<br>
	 * 分単位休暇取得処理が複数ある場合は、上書する。<br>
	 * 但し、分単位休暇取得処理で1日の分数として0を取得した場合は、上書しない。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 1日の分数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getMinutesPerDay(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 休暇情報に対する利用分数を取得する。<br>
	 * 分単位休暇取得処理が複数ある場合は、加算する。<br>
	 * @param dto 休暇情報
	 * @return 利用分数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getUseMinutes(HolidayDataDtoInterface dto) throws MospException;
	
	/**
	 * 残数のある休暇残情報群(有効日昇順)を取得する。<br>
	 * 休暇残情報群(有効日昇順)を取得する。<br>
	 * <br>
	 * 対象日時点で有効な休暇付与情報リストを取得し、
	 * それぞれの休暇付与情報に対する休暇申請情報と合わせて、
	 * 残日数及び残時間数等を計算する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetDate  対象日
	 * @param holidayType 休暇区分(休暇種別1：特別休暇orその他休暇)
	 * @return 休暇残情報群(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<HolidayRemainDto> getHolidayRemains(String personalId, Date targetDate, int holidayType) throws MospException;
	
	/**
	 * 休暇残情報群(有効日昇順)を取得する。<br>
	 * それぞれの休暇付与情報に対する休暇申請情報と合わせて、残日数及び残時間数等を計算する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param dtos       休暇付与情報群(有効日昇順)
	 * @return 休暇残情報群(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<HolidayRemainDto> getHolidayRemains(String personalId, Date targetDate,
			Collection<HolidayDataDtoInterface> dtos) throws MospException;
	
	/**
	 * 期間内に適用されている設定が存在するか確認する。<br>
	 * @param startDate   期間開始日
	 * @param endDate     期間終了日
	 * @param personalId  対象個人ID
	 * @param holidayType 休暇区分
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean hasPersonalApplication(String personalId, Date startDate, Date endDate, int holidayType)
			throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
