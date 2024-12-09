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
/**
 * 
 */
package jp.mosp.time.dao.settings;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;

/**
 * 勤怠データ修正情報参照DAOインターフェース。<br>
 */
public interface AttendanceCorrectionDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと勤務日と勤務回数から最新の勤怠データ修正情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param works 勤務回数
	 * @return 勤怠修正データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceCorrectionDtoInterface findForLatestInfo(String personalId, Date workDate, int works)
			throws MospException;
	
	/**
	 * 個人IDと勤務日と勤務回数から勤怠修正情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param works 勤務回数
	 * @return 勤怠修正情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AttendanceCorrectionDtoInterface> findForHistory(String personalId, Date workDate, int works)
			throws MospException;
	
	/**
	 * 勤怠修正情報群(キー：勤務日)を取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  期間開始日
	 * @param lastDate   期間終了日
	 * @return 勤怠修正情報群(キー：勤務日)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<Date, List<AttendanceCorrectionDtoInterface>> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 個人IDと勤務日で勤怠修正情報リストを取得する。<br>
	 * @param personalIds 個人ID群
	 * @param workDate    勤務日
	 * @return 勤怠修正情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AttendanceCorrectionDtoInterface> findForPersonalIds(Collection<String> personalIds, Date workDate)
			throws MospException;
	
}
