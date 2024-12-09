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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;

/**
 * 打刻情報DAOインターフェース。<br>
 */
public interface TimeRecordDaoInterface extends BaseDaoInterface {
	
	/**
	 * 打刻情報を取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @param timesWork  勤務回数
	 * @param recordType 打刻区分
	 * @return 打刻データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TimeRecordDtoInterface findForKey(String personalId, Date workDate, int timesWork, String recordType)
			throws MospException;
	
	/**
	 * 打刻情報群を取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @param timesWork  勤務回数
	 * @return 打刻情報群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TimeRecordDtoInterface> findForPersonAndDay(String personalId, Date workDate, int timesWork)
			throws MospException;
	
}
