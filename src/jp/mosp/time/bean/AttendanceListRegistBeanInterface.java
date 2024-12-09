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
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 勤怠一覧登録インターフェース。<br>
 */
public interface AttendanceListRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 一括下書を行う。<br>
	 * @param personalId  個人ID
	 * @param targetDates 対象日配列
	 * @param startTimes  始業時刻配列
	 * @param endTimes    終業時刻配列
	 * @param deleteRest 休憩を削除する場合はtrue、しない場合はfalse
	 * @param useWorkTypeChangeRequest 勤務形態変更申請を利用する場合true、そうでない場合false
	 * @param useSchedule カレンダを利用する場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void draft(String personalId, String[] targetDates, String[] startTimes, String[] endTimes, boolean deleteRest,
			boolean useWorkTypeChangeRequest, boolean useSchedule) throws MospException;
	
	/**
	 * 一括申請を行う。<br>
	 * @param personalId  個人ID
	 * @param targetDates 対象日配列
	 * @param startTimes  始業時刻配列
	 * @param endTimes    終業時刻配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void apply(String personalId, String[] targetDates, String[] startTimes, String[] endTimes) throws MospException;
	
	/**
	 * 対象勤怠データ情報に対し、
	 * ワークフロー(下書)を作成して、ワークフロー番号を設定する。<br>
	 * 但し、既にワークフロー番号が設定されている場合は、何もしない。<br>
	 * @param dto 対象勤怠データ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void draft(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 休憩データを取得する。<br>
	 * @param dto 対象DTO
	 * @return 休憩データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<RestDtoInterface> getRestList(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 修正データを登録する。<br>
	 * @param dto 勤怠DTO
	 * @param oldDto 旧勤怠DTO
	 * @param oldRestList 旧休憩リスト
	 * @param oldPublicList 旧公用外出リスト
	 * @param oldPrivateList 旧私用外出リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void registCorrection(AttendanceDtoInterface dto, AttendanceDtoInterface oldDto, List<RestDtoInterface> oldRestList,
			List<GoOutDtoInterface> oldPublicList, List<GoOutDtoInterface> oldPrivateList) throws MospException;
	
	/**
	 * 代休データを登録する。<br>
	 * @param dto 対象勤怠データ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void registSubHoliday(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 勤怠申請時に当該申請より残業時間が発生する場合、
	 * 残業申請要否及び残業申請の有無を確認し、必要であれば残業申請を促すメッセージを表示する。
	 * 残業申請督促はするが、勤怠申請自体は行われる。
	 * @param attendanceDto 勤怠データDTOインターフェース
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkOvertime(AttendanceDtoInterface attendanceDto) throws MospException;
	
	/**
	 * 勤怠申請時に当該申請より残業時間が発生する場合、
	 * 残業申請要否及び残業申請の有無を確認し、必要であれば残業申請を促すメッセージを表示する。
	 * 残業申請督促はするが、勤怠申請自体は行われる。
	 * @param personalId  個人ID
	 * @param targetDates 対象日配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkOvertime(String personalId, String[] targetDates) throws MospException;
	
	/**
	 * ワークフロー情報に、承認者を設定する。<br>
	 * 一つの階層に複数の承認者が設定されている場合、
	 * 先頭の(職位ランクの最も優れた)承認者を設定する。<br>
	 * @param dto 対象DTO
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setRouteApprover(WorkflowDtoInterface dto, String personalId, Date targetDate) throws MospException;
	
}
