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

import java.sql.ResultSet;
import java.util.Date;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;

/**
 * エクスポートデータDAOインターフェース。<br>
 */
public interface ExportDataDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤怠データを取得する。<br>
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param ckbNeedLowerSection 下位所属含む
	 * @param positionCode 職位コード
	 * @return 勤怠データ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ResultSet findForAttendance(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException;
	
	/**
	 * 勤怠集計データを取得する。<br>
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param ckbNeedLowerSection 下位所属含む
	 * @param positionCode 職位コ－ド
	 * @return　勤怠集計データ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ResultSet findForTotalTime(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException;
	
	/**
	 * 有給休暇データを取得する。<br>
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param ckbNeedLowerSection 下位所属含む
	 * @param positionCode 職位コード
	 * @return 有給休暇データ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ResultSet findForPaidHoliday(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException;
	
	/**
	 * ストック休暇データを取得する。<br>
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param ckbNeedLowerSection 下位所属含む
	 * @param positionCode 職位コード
	 * @return ストック休暇データ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ResultSet findForStockHoliday(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException;
	
	/**
	 * 休暇データを取得する。<br>
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @param cutoffCode 締日コード
	 * @param workPlaceCode 勤務地コード
	 * @param employmentCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param ckbNeedLowerSection 下位所属含む
	 * @param positionCode 職位コード
	 * @return 休暇データ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ResultSet findForHolidayData(Date startDate, Date endDate, String cutoffCode, String workPlaceCode,
			String employmentCode, String sectionCode, int ckbNeedLowerSection, String positionCode)
			throws MospException;
	
	/**
	 * 終了処理
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void closers() throws MospException;
	
}
