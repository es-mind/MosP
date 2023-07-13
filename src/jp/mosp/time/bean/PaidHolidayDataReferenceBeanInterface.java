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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;

/**
 * 有給休暇情報参照処理インターフェース。<br>
 */
public interface PaidHolidayDataReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 有給休暇データ取得。
	 * @param id レコード識別ID
	 * @return 有給休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDataDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * 有給休暇データからレコードを取得する。<br>
	 * 個人ID、有効日、取得日で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return 有給休暇データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 個人ID及び取得日で有給休暇情報を取得する。<br>
	 * 但し、有効日が対象日よりも前の情報は、対象外とする。<br>
	 * 有効日が異なる有給休暇情報が存在する場合は、対象日以前で最新の情報を取得する。<br>
	 * @param personalId      個人ID
	 * @param targetDate      対象日
	 * @param acquisitionDate 取得日
	 * @return 有給休暇情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDataDtoInterface getPaidHolidayDataInfo(String personalId, Date targetDate, Date acquisitionDate)
			throws MospException;
	
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
	List<PaidHolidayDataDtoInterface> getPaidHolidayDatas(String personalId, Date targetDate, Date acquisitionDate,
			Date limitDate) throws MospException;
	
	/**
	 * 対象日で有給休暇情報リスト(取得日昇順)を取得する。<br>
	 * 同一取得日別有効日の情報が存在する場合は、対象日以降で最新の情報を取得する。<br>
	 * 取得日が対象日以前の情報のみを取得する。<br>
	 * 期限日が対象日以降の情報のみを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇情報リスト(取得日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> getPaidHolidayDataForDate(String personalId, Date targetDate)
			throws MospException;
	
	/**
	 * 期間で有給休暇情報リストを取得する。<br>
	 * 同一取得日別有効日の情報が存在する場合は、対象日(期間最終日)以降で最新の情報を取得する。<br>
	 * 取得日が期間最終日以前の情報のみを取得する。<br>
	 * 期限日が期間初日以降の情報のみを取得する。<br>
	 * @param firstDate 期間初日
	 * @param lastDate  期間最終日
	 * @return 有給休暇情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDataDtoInterface> getPaidHolidayDataForTerm(Date firstDate, Date lastDate) throws MospException;
	
	/**
	 * 期限日で有給休暇情報リストを取得する。<br>
	 * 期限日が引数の期限日より前の情報は、取得対象外とする。<br>
	 * 同一取得日別有効日の情報も含まれる(有給休暇付与修正画面で用いられる)。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param limitDate  期限日
	 * @return 有給休暇情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> getPaidHolidayDataForLimit(String personalId, Date limitDate)
			throws MospException;
	
	/**
	 * 有給休暇データリスト取得。<br>
	 * 次年度以降の有給休暇データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> findForNextInfoList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 期限日決定用の有給休暇情報を取得する。<br>
	 * 同一取得日別有効日の情報が存在する場合は、対象日(期間最終日)以降で最新の情報を取得する。<br>
	 * 対象日以前に付与されており、対象日時点で期限切れとなっている有給休暇情報のうち、
	 * 最も期限日が大きい有給休暇情報の期限日を取得する。<br>
	 * 対象となる有給休暇情報が存在しない場合は、nullを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Optional<PaidHolidayDataDtoInterface> getPaidHolidayDataForExpiration(String personalId, Date targetDate)
			throws MospException;
	
	/**
	 * 期限日で有給休暇情報リストを取得する。<br>
	 * 同一取得日別有効日の情報が存在する場合は、対象日(期限日)以降で最新の情報を取得する。<br>
	 * 取得日が引数の期限日以前の情報のみを取得する。<br>
	 * 期限日が引数の期限日である情報のみを取得する。<br>
	 * @param personalId 個人ID
	 * @param limitDate  期限日
	 * @return 有給休暇情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> getExpiredPaidHolidayDatas(String personalId, Date limitDate)
			throws MospException;
	
}
