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
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;

/**
 * 休暇データDAOインターフェース
 */
public interface HolidayDataDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと有効日と休暇コードと休暇区分から休暇情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HolidayDataDtoInterface findForKey(String personalId, Date activateDate, String holidayCode, int holidayType)
			throws MospException;
	
	/**
	 * 休暇データリスト取得。
	 * 個人IDと有効日と無効フラグと休暇区分から休暇データリストを取得する。
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @param holidayType 休暇区分
	 * @return 休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDataDtoInterface> findForInfoList(String personalId, Date activateDate, String inactivateFlag,
			int holidayType) throws MospException;
	
	/**
	 * 休暇データ取得。
	 * <p>
	 * 個人IDと有効日と休暇コードと休暇区分から休暇データを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDataDtoInterface> findForEarliestList(String personalId, Date activateDate, String holidayCode,
			int holidayType) throws MospException;
	
	/**
	 * 休暇データリストを取得する。<br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 有効日の範囲で検索する。但し、有効日Toは、検索対象に含まれない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDataDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate, String holidayCode,
			int holidayType) throws MospException;
	
	/**
	 * 個人IDが設定されている、有効日の範囲内で情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報も含まる。<br>
	 * 削除時の整合性確認等で用いる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @param personalId 個人ID
	 * @param holidayType 休暇区分
	 * @return 有給休暇データDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDataDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate, int holidayType)
			throws MospException;
	
}
