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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;

/**
 * 勤怠情報参照処理インターフェース。<br>
 */
public interface AttendanceReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠情報リストを取得する。<br>
	 * 個人IDと開始年月日と終了年月日から勤怠データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param startDate  開始年月日
	 * @param endDate    終了年月日
	 * @return 勤怠情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceDtoInterface> getAttendanceList(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 勤怠情報群(キー：勤務日)(キー昇順)を取得する。<br>
	 * @param personalId 個人ID
	 * @param startDate  開始日
	 * @param endDate    終了日
	 * @return 勤怠情報群(キー：勤務日)(キー昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, AttendanceDtoInterface> getAttendances(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 勤怠情報群(キー：個人ID)(キー昇順)を取得する。<br>
	 * @param personalIds 個人ID群
	 * @param workDate    勤務日
	 * @return 勤怠情報群(キー：勤務日)(キー昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, AttendanceDtoInterface> getAttendances(Collection<String> personalIds, Date workDate)
			throws MospException;
	
	/**
	 * 勤怠データからレコードを取得する。<br>
	 * 個人ID、勤務日で合致するレコードが無い場合、nullを返す。<br>
	 * ワークフローの状態が取下げであるものは除く。
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @return 勤怠詳細データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AttendanceDtoInterface findForKey(String personalId, Date workDate) throws MospException;
	
	/**
	 * ワークフロー番号からレコードを取得する。<br>
	 * ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 勤怠詳細データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AttendanceDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 勤務日群に含まれている申請済以上の勤怠情報群(キー：勤務日)(キー昇順)を取得する。<br>
	 * @param personalId 個人ID
	 * @param workDates  勤務日群
	 * @return 勤怠申請済以上の勤務日群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, AttendanceDtoInterface> getAppliedForWorkDates(String personalId, Set<Date> workDates)
			throws MospException;
	
	/**
	 * 勤務日群に含まれている承認済以上の勤怠情報群(キー：勤務日)(キー昇順)を取得する。<br>
	 * @param personalId 個人ID
	 * @param workDates  勤務日群
	 * @return 勤怠申請済以上の勤務日群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, AttendanceDtoInterface> getCompletedForWorkDates(String personalId, Set<Date> workDates)
			throws MospException;
	
	/**
	 * 基本情報チェック
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
}
