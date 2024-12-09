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

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態項目管理DAOインターフェース
 */
public interface WorkTypeItemDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤務形態コードと有効日と勤務形態項目コードから勤務形態項目情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkTypeItemDtoInterface findForKey(String workTypeCode, Date activateDate, String workTypeItemCode)
			throws MospException;
	
	/**
	 * 勤務形態項目マスタ取得。
	 * <p>
	 * 勤務形態項目コードと有効日から勤務形態項目マスタを取得する。
	 * </p>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkTypeItemDtoInterface findForInfo(String workTypeCode, Date activateDate, String workTypeItemCode)
			throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 勤務形態コードと勤務形態項目コードから勤務形態項目マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkTypeItemDtoInterface> findForHistory(String workTypeCode, String workTypeItemCode) throws MospException;
	
	/**
	 * 勤務形態項目情報リストを取得する。<br>
	 * 対象の勤務形態コード及び有効日で、勤務形態項目情報を取得する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @return 勤務形態項目情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkTypeItemDtoInterface> findForWorkType(String workTypeCode, Date activateDate) throws MospException;
	
	/**
	 * 勤務形態項目情報リストを取得する。<br>
	 * 有効日及び対象の勤務形態コード、勤務形態項目値で、勤務形態項目情報を取得する。<br>
	 * 有効日がnullの場合、有効日を検索条件に含めない。<br>
	 * @param activateDate 有効日
	 * @param workTypeItemCode 勤務形態項目コード
	 * @param workTypeItemValue 勤務形態項目値
	 * @return 勤務形態項目情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkTypeItemDtoInterface> findForWorkTypeItem(Date activateDate, String workTypeItemCode,
			Date workTypeItemValue) throws MospException;
	
	/**
	 * 勤務形態項目(予備)情報リストを取得する。<br>
	 * 有効日及び対象の勤務形態コード、勤務形態項目値で、勤務形態項目情報を取得する。<br>
	 * 有効日がnullの場合、有効日を検索条件に含めない。<br>
	 * @param activateDate 有効日
	 * @param workTypeItemCode 勤務形態項目コード
	 * @param preliminary 勤務形態項目値(予備)
	 * @return 勤務形態項目情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkTypeItemDtoInterface> findForWorkTypeItem(Date activateDate, String workTypeItemCode, String preliminary)
			throws MospException;
}
