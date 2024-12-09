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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 申請ユーティリティインターフェース。<br>
 */
public interface RequestUtilBeanInterface extends BaseBeanInterface {
	
	/**
	 * 対象個人IDと対象日における申請されている各種申請を取得する。
	 * 休暇申請、休日出勤申請、振替休日申請、残業申請、時差出勤申請、勤怠情報を取得する。
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setRequests(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDと対象日の申請エンティティを取得する。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RequestEntityInterface getRequestEntity(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 振替休日申請情報の中で対象承認状況の情報を取得する。
	 * 存在しない場合は、空の情報リストを返す。
	 * @param status 承認状況(true：承認済、false：取下、下書以外)
	 * @return 振替申請情報リスト(承認済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubstituteDtoInterface> getSubstituteList(boolean status) throws MospException;
	
	/**
	 * 残業申請情報の中で対象承認状況の情報を取得する。
	 * 存在しない場合は、空の情報リストを返す。
	 * @param status 承認状況(true：承認済、false：取下、下書以外)
	 * @return 残業申請情報リスト(承認済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<OvertimeRequestDtoInterface> getOverTimeList(boolean status) throws MospException;
	
	/**
	 * 休暇申請情報の中で対象承認状況の情報を取得する。
	 * 存在しない場合は、空の情報リストを返す。
	 * @param status 承認状況(true：承認済、false：取下、下書以外)
	 * @return 休暇申請情報リスト(承認済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRequestDtoInterface> getHolidayList(boolean status) throws MospException;
	
	/**
	 * 代休申請情報の中で対象承認状況の情報を取得する。
	 * 存在しない場合は、空の情報リストを返す。
	 * @param status 承認状況(true：承認済、false：取下、下書以外)
	 * @return 代休申請情報リスト(承認済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubHolidayRequestDtoInterface> getSubHolidayList(boolean status) throws MospException;
	
	/**
	 * 時差出勤申請情報で対象承認状況の情報を取得する。
	 * 存在しない場合nullを返す。
	 * @param status 承認状況(true：承認済、false：取下、下書以外)
	 * @return 時差出勤申請情報リスト(承認済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	DifferenceRequestDtoInterface getDifferenceDto(boolean status) throws MospException;
	
	/**
	 * 勤務形態変更申請情報で対象承認状況の情報を取得する。
	 * 存在しない場合nullを返す。
	 * @param status 承認状況(true：承認済、false：取下、下書以外)
	 * @return 勤務形態変更申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeChangeRequestDtoInterface getWorkTypeChangeDto(boolean status) throws MospException;
	
	/**
	 * 休日出勤申請情報で対象承認状況の情報を取得する。
	 * 存在しない場合nullを返す。
	 * @param status 承認状況(true：承認済、false：取下、下書以外)
	 * @return 休日出勤申請情報リスト(承認済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkOnHolidayRequestDtoInterface getWorkOnHolidayDto(boolean status) throws MospException;
	
	/**
	 * 休暇申請の中で承認済かつ休暇範囲が全休の申請情報を取得する。
	 * 承認済でない又は全休でない場合nullを返す。
	 * @return 休暇申請情報(承認済、全休)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRequestDtoInterface getCompletedHolidayRangeAll() throws MospException;
	
	/**
	 * 振替休日申請の中で承認済かつ
	 * 更に休日出勤申請(承認済)又は振替休日申請(承認済)がされていない申請情報を取得する。
	 * 承認済でない又は更に休日申請又は振替申請がされている場合nullを返す。
	 * @return 振替休日申請情報(承認済、更に申請がされていない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SubstituteDtoInterface getCompletedSubstituteRangeAll() throws MospException;
	
	/**
	 * 代休申請情報の中で承認済かつ休暇範囲が全休の申請情報を取得する。
	 * 承認済でない又は全休でない場合nullを返す。
	 * @return 代休申請情報(承認済、全休)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SubHolidayRequestDtoInterface getCompletedSubHolidayRangeAll() throws MospException;
	
	/**
	 * 休暇申請情報リストの休暇範囲が午前休、午後休、時間休、全休か確認する。
	 * 情報が存在しない場合0を返す。
	 * @param holidayRequestList 休暇申請情報リスト
	 * @return 休暇範囲(0：休暇なし、1：全休、2：午前休、3：午後休、4：時間休、5：午前休、午後休)
	 */
	int checkHolidayRangeHoliday(List<HolidayRequestDtoInterface> holidayRequestList);
	
	/**
	 * 振出休日申請情報の休暇範囲を確認をする。
	 * 情報が存在しない場合0を返す。
	 * @param substituteList 振出休日申請情報
	 * @return 休暇範囲(0：休暇なし、1：全休、2：午前休、3：午後休、5：午前休、午後休)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	int checkHolidayRangeSubstitute(List<SubstituteDtoInterface> substituteList) throws MospException;
	
	/**
	 * 代休申請情報リストの休暇範囲を確認する。
	 * 情報が存在しない場合0を返す。
	 * @param subHolidayList 代休申請情報リスト
	 * @return 休暇範囲(0：休暇なし、1：全休、2：午前休、3：午後休、5：午前休、午後休)
	 */
	int checkHolidayRangeSubHoliday(List<SubHolidayRequestDtoInterface> subHolidayList);
	
	/**
	 * 勤怠申請情報(下書)を取得する。<br>
	 * 存在しない場合は、nullを返す。
	 * @return 勤怠申請情報(下書)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceDtoInterface getDraftAttendance() throws MospException;
	
	/**
	 * 勤怠申請情報(1次戻)を取得する。<br>
	 * 存在しない場合は、nullを返す。
	 * @return 勤怠申請情報(1次戻)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceDtoInterface getFirstRevertedAttendance() throws MospException;
	
	/**
	 * 対象日と対象個人IDから勤怠申請情報(取下、下書、1次戻以外)を取得する。
	 * 存在しない場合は、nullを返す。
	 * @return 勤怠申請情報(取下、下書、1次戻以外)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceDtoInterface getApplicatedAttendance() throws MospException;
	
	/**
	 * 対象日と対象個人IDから勤怠申請情報(承認状況は不問)を取得する。
	 * 設定されていない場合及び存在しない場合は、nullを返す。
	 * @return 勤怠申請情報
	 */
	AttendanceDtoInterface getAttendance();
	
	/**
	 * 対象承認状況の休暇申請、振替出勤申請、代休申請により1日中休日になるか確認する。
	 * @param status 承認状況(true：承認済、false：取下、下書以外)
	 * @return (true：申請により休日、false：午前休、午後休、勤務日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isHolidayAllDay(boolean status) throws MospException;
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * 予定される勤務形態(承認済の申請を考慮)の勤務形態エンティティを取得する。<br>
	 * 時差出勤申請がある場合は、時差出勤申請の内容を考慮する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 勤務形態エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeEntityInterface getWorkTypeEntity(String personalId, Date targetDate) throws MospException;
	
	/**
	 * カレンダユーティリティに勤怠関連マスタ参照処理を設定する。<br>
	 * 勤怠関連マスタ参照処理を処理間で共有するために用いる。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
