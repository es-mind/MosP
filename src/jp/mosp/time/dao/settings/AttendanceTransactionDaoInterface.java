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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;

/**
 * 勤怠トランザクションDAOインターフェース
 */
public interface AttendanceTransactionDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤怠トランザクションを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @return 勤怠トランザクション
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceTransactionDtoInterface findForKey(String personalId, Date workDate) throws MospException;
	
	/**
	 * 勤怠トランザクションマップを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate 対象期間初日
	 * @param lastDate 対象期間末日
	 * @return 勤怠トランザクションマップ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<Date, AttendanceTransactionDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 勤怠トランザクションの和を取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate 対象期間初日
	 * @param lastDate 対象期間末日
	 * @return 勤怠トランザクションの和
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AttendanceTransactionDtoInterface sum(String personalId, Date firstDate, Date lastDate) throws MospException;
	
	/**
	 * 勤務日を表すミリ秒数セットを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate 対象期間初日
	 * @param lastDate 対象期間末日
	 * @return 勤務日を表すミリ秒数セット
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<Long> findForMilliseconds(String personalId, Date firstDate, Date lastDate) throws MospException;
	
}
