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

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;

/**
 * 有給休暇設定DAOインターフェース
 */
public interface PaidHolidayDaoInterface extends BaseDaoInterface {
	
	/**
	 * 有給休暇コードと有効日から有給休暇情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @return 有給休暇マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場
	 */
	PaidHolidayDtoInterface findForKey(String paidHolidayCode, Date activateDate) throws MospException;
	
	/**
	 * 有給休暇マスタ取得。
	 * <p>
	 * 有給休暇コードと有効日から有給休暇マスタを取得する。
	 * </p>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @return 有給休暇管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayDtoInterface findForInfo(String paidHolidayCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 有給休暇コードから有給休暇マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @return 有給休暇マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDtoInterface> findForHistory(String paidHolidayCode) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から有給休暇マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 有給休暇マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から有給休暇マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 有給休暇マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 有給休暇管理検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 条件による検索のための文字列。
	 * <p>
	 * 最大有効日レコードのクエリを取得する。
	 * </p>
	 * @return 有休コードに紐づく有効日が最大であるレコード取得クエリ
	 */
	StringBuffer getQueryForMaxActivateDate();
	
}
