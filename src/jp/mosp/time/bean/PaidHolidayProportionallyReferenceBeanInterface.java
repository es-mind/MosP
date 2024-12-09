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
import jp.mosp.time.dto.settings.PaidHolidayProportionallyDtoInterface;

/**
 * 有給休暇比例付与参照インターフェース。
 */
public interface PaidHolidayProportionallyReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 有給休暇比例付与マスタ取得。<br>
	 * @param paidHolidayCode 有休コード
	 * @param targetDate 対象日
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 * @return 有給休暇比例付与マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayProportionallyDtoInterface findForInfo(String paidHolidayCode, Date targetDate,
			int prescribedWeeklyWorkingDays, int continuousServiceTermsCountingFromTheEmploymentDay)
			throws MospException;
	
	/**
	 * 有給休暇比例付与マスタリスト取得。<br>
	 * @param paidHolidayCode 有休コード
	 * @param activateDate 有効日
	 * @return 有給休暇比例付与マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayProportionallyDtoInterface> findForList(String paidHolidayCode, Date activateDate)
			throws MospException;
	
}
