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
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayDtoInterface;

/**
 * 休暇種別管理DAOインターフェース。<br>
 */
public interface HolidayDaoInterface extends BaseDaoInterface {
	
	/**
	 * 休暇コードと有効日と休暇区分から休暇種別管理情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param holidayCode	休暇コード
	 * @param activateDate	有効日
	 * @param holidayType	休暇区分
	 * @return 休暇種別マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HolidayDtoInterface findForKey(String holidayCode, Date activateDate, int holidayType) throws MospException;
	
	/**
	 * 休暇種別マスタ取得。
	 * <p>
	 * 休暇コードと有効日から休暇種別マスタを取得する。
	 * </p>
	 * @param holidayCode 休暇コード
	 * @param activateDate 有効日
	 * @param holidayType 休暇区分
	 * @return 休暇種別管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HolidayDtoInterface findForInfo(String holidayCode, Date activateDate, int holidayType) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 休暇コード及び休暇区分から休暇種別マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇種別マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDtoInterface> findForHistory(String holidayCode, int holidayType) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から休暇種別マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @param holidayType 休暇区分
	 * @return 休暇種別マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDtoInterface> findForActivateDate(Date activateDate, int holidayType) throws MospException;
	
	/**
	 * 対象日における最新の休暇種別情報群を取得する。<br>
	 * <br>
	 * 休暇コード及び休暇区分をキーとして、対象日における最新の休暇種別情報群を取得する。<br>
	 * 但し、対象日における最新の休暇種別情報が無効となっているものは、取得しない。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 休暇種別情報群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<HolidayDtoInterface> findForActivateDate(Date targetDate) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から休暇種別マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @param holidayType 休暇区分
	 * @return 休暇種別マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDtoInterface> findForExport(Date activateDate, int holidayType) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から休暇種別マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 休暇種別マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HolidayDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 休暇種別管理検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
}
