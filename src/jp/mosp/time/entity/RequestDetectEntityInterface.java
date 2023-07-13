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

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 申請検出エンティティインターフェース。<br>
 */
public interface RequestDetectEntityInterface {
	
	/**
	 * 未承認(一次戻含む)が存在するかを確認する。<br>
	 * <br>
	 * 対象年月の以下の申請につき、未承認がないかを確認する。<br>
	 * ・勤怠申請<br>
	 * ・残業申請<br>
	 * ・休暇申請<br>
	 * ・休日出勤申請<br>
	 * ・勤務形態変更申請<br>
	 * ・代休申請<br>
	 * ・時差出勤申請<br>
	 * <br>
	 * 即時フラグがtrueの場合は、一つでも未承認が見つかった時点で、
	 * 処理を終了しtrueを返す。<br>
	 * <br>
	 * @param mospParamsm   MosP処理情報
	 * @param isImmediately 即時フラグ
	 * @return 確認結果(true：未承認有、false：未承認無)
	 * @throws MospException 業務処理で実行時例外が発生した場合
	 */
	boolean isApprovableExist(MospParams mospParamsm, boolean isImmediately) throws MospException;
	
	/**
	 * 勤怠の申請が可能か確認する。<br>
	 * 次の条件を満たす時、勤怠の申請が可能と判断する。<br>
	 * ・勤怠が未申請である。<br>
	 * ・申請済であり未承認の残業申請がない。<br>
	 * ・
	 * @return 確認結果(true：勤怠申請可能、false：勤怠申請不可)
	 */
	boolean isAppliableExistContainRequests();
	
	/**
	 * 勤怠未申請が存在するかを確認する。<br>
	 * <br>
	 * 対象年月につき、勤怠が申請されていない日があるかを確認する。<br>
	 * 休職期間中は、勤怠未申請確認対象外とする。<br>
	 * <br>
	 * 即時フラグがtrueの場合は、一つでも勤怠未申請が見つかった時点で、
	 * 処理を終了しtrueを返す。<br>
	 * <br>
	 * @param isImmediately 即時フラグ
	 * @return 勤怠未申請フラグ(true：勤怠未申請有、false：勤怠未申請無)
	 */
	boolean isAppliableExist(boolean isImmediately);
	
	/**
	 * 残業未申請が存在するかを確認する。<br>
	 * <br>
	 * 対象年月で勤怠が申請されている日につき、
	 * 残業が申請されていない日があるかを確認する。<br>
	 * <br>
	 * 休日出勤の場合は、残業申請は不要とする。<br>
	 * <br>
	 * 即時フラグがtrueの場合は、一つでも残業未申請が見つかった時点で、
	 * 処理を終了しtrueを返す。<br>
	 * <br>
	 * @param isImmediately 即時フラグ
	 * @return 残業未申請フラグ(true：残業未申請有、false：残業未申請無)
	 */
	boolean isOvertimeNotAppliedExist(boolean isImmediately);
	
	/**
	 * 集計時エラー内容参照情報リストを取得する。<br>
	 * <br>
	 * {@link RequestDetectEntityInterface#isApprovableExist(MospParams, boolean)}、
	 * {@link RequestDetectEntityInterface#isAppliableExist(boolean)}
	 * を実行した後に、当メソッドを利用する。<br>
	 * <br>
	 * MosP処理情報はNamingを取得するのに用いる。<br>
	 * <br>
	 * @param humanDto   人事情報
	 * @param mospParams MosP処理情報
	 * @return 集計時エラー内容参照情報
	 */
	List<CutoffErrorListDtoInterface> getCutoffErrorList(MospParams mospParams, HumanDtoInterface humanDto);
	
	/**
	 * 対象期間を対象日前日までに設定する。<br>
	 * <br>
	 * 但し、対象期間に対象日が含まれない場合は、何もしない。<br>
	 * <br>
	 * 部下一覧等で「前日まで」を設定した場合に、用いる。<br>
	 * <br>
	 * @param targetDate 対象日
	 */
	void setBeforeDay(Date targetDate);
	
	/**
	 * @param personalId セットする personalId
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param targetDateList セットする targetDateList
	 */
	void setTargetDateList(List<Date> targetDateList);
	
	/**
	 * @param suspensionList セットする suspensionList
	 */
	void setSuspensionList(List<SuspensionDtoInterface> suspensionList);
	
	/**
	 * @param scheduleMap セットする scheduleMap
	 */
	void setScheduleMap(Map<Date, String> scheduleMap);
	
	/**
	 * @param attendanceList セットする attendanceList
	 */
	void setAttendanceList(List<AttendanceDtoInterface> attendanceList);
	
	/**
	 * @param workOnHolidayRequestList セットする workOnHolidayRequestList
	 */
	void setWorkOnHolidayRequestList(List<WorkOnHolidayRequestDtoInterface> workOnHolidayRequestList);
	
	/**
	 * @param holidayRequestList セットする holidayRequestList
	 */
	void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList);
	
	/**
	 * @param subHolidayRequestList セットする subHolidayRequestList
	 */
	void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList);
	
	/**
	 * @param overtimeRequestList セットする overtimeRequestList
	 */
	void setOvertimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList);
	
	/**
	 * @param workTypeChangeRequestList セットする workTypeChangeRequestList
	 */
	void setWorkTypeChangeRequestList(List<WorkTypeChangeRequestDtoInterface> workTypeChangeRequestList);
	
	/**
	 * @param differenceRequestList セットする differenceRequestList
	 */
	void setDifferenceRequestList(List<DifferenceRequestDtoInterface> differenceRequestList);
	
	/**
	 * @param substituteList セットする substituteList
	 */
	void setSubstituteList(List<SubstituteDtoInterface> substituteList);
	
	/**
	 * @param workflowMap セットする workflowMap
	 */
	void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap);
	
}
