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
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayProportionallyDtoInterface;

/**
 * 有給休暇比例付与DAOインターフェース
 */
public interface PaidHolidayProportionallyDaoInterface extends BaseDaoInterface {
	
	/**
	 * 有給休暇コードと有効日から有給休暇比例付与情報を取得する。<br>
	 * @param paidHolidayCode 有休コード
	 * @param activateDate 有効日
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 有給休暇比例付与マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayProportionallyDtoInterface findForKey(String paidHolidayCode, Date activateDate,
			int prescribedWeeklyWorkingDays, int continuousServiceTermsCountingFromTheEmploymentDay)
			throws MospException;
	
	/**
	 * 有給休暇比例付与マスタ取得。
	 * @param paidHolidayCode 有休コード
	 * @param activateDate 有効日
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 有給休暇比例付与マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayProportionallyDtoInterface findForInfo(String paidHolidayCode, Date activateDate,
			int prescribedWeeklyWorkingDays, int continuousServiceTermsCountingFromTheEmploymentDay)
			throws MospException;
	
	/**
	 * 有給休暇比例付与マスタリストを取得する。
	 * @param paidHolidayCode 有休コード
	 * @param activateDate 有効日
	 * @return 有給休暇比例付与マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayProportionallyDtoInterface> findForList(String paidHolidayCode, Date activateDate)
			throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * @param paidHolidayCode 有休コード
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 有給休暇比例付与マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayProportionallyDtoInterface> findForHistory(String paidHolidayCode, int prescribedWeeklyWorkingDays,
			int continuousServiceTermsCountingFromTheEmploymentDay) throws MospException;
	
}
