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
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;

/**
 * 有給休暇情報DAOインターフェース。<br>
 */
public interface PaidHolidayDataDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと有効日と取得日から有給休暇データ情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return 有給休暇情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 個人ID及び取得日で有給休暇情報リスト(有効日が異なる場合に複数存在し得る)を取得する。<br>
	 * @param personalId      個人ID
	 * @param acquisitionDate 取得日
	 * @return 有給休暇情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForHistory(String personalId, Date acquisitionDate) throws MospException;
	
	/**
	 * 期限日で有給休暇情報リストを取得する。<br>
	 * 期限日が引数の期限日より前の情報は、取得対象外とする。<br>
	 * 同一取得日別有効日の情報も含まれる(有給休暇付与修正画面で用いられる)。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param limitDate  期限日
	 * @return 有給休暇情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForLimit(String personalId, Date limitDate) throws MospException;
	
	/**
	 * 有給休暇情報リストを取得する。<br>
	 * 同一取得日別有効日の情報が存在する場合は、対象日以降で最新の情報を取得する。<br>
	 * 個人IDを指定した場合は、対象個人IDの情報のみを取得する。<br>
	 * 取得日を指定した場合は、取得日が対象取得日以前の情報のみを取得する。<br>
	 * 期限日を指定した場合は、期限日が対象期限日以降の情報のみを取得する。<br>
	 * @param personalId      個人ID
	 * @param targetDate      対象日
	 * @param acquisitionDate 取得日
	 * @param limitDate       期限日
	 * @return 有給休暇情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForList(String personalId, Date targetDate, Date acquisitionDate,
			Date limitDate) throws MospException;
	
	/**
	 * 有給休暇データリスト取得。<br>
	 * 次年度以降の有給休暇データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForNextInfoList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇データ取得。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayDataDtoInterface findForExpirationDateInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 有給休暇データリスト取得。
	 * @param personalId 個人ID
	 * @param limitDate  期限日
	 * @return 有給休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> findForExpirationDateList(String personalId, Date limitDate) throws MospException;
	
}
