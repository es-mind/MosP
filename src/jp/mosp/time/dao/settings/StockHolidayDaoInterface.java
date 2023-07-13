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
/**
 * 
 */
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.StockHolidayDtoInterface;

/**
 * ストック休暇管理DAOインターフェース
 */
public interface StockHolidayDaoInterface extends BaseDaoInterface {
	
	/**
	 * 有給休暇コードと有効日からストック休暇管理情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @return ストック休暇管理マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StockHolidayDtoInterface findForKey(String paidHolidayCode, Date activateDate) throws MospException;
	
	/**
	 * ストック休暇管理マスタ取得。
	 * <p>
	 * 有給休暇コードと有効日からストック休暇管理マスタを取得する。
	 * </p>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @return ストック休暇管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StockHolidayDtoInterface findForInfo(String paidHolidayCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 有給休暇コードからストック休暇管理マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @return ストック休暇管理マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<StockHolidayDtoInterface> findForHistory(String paidHolidayCode) throws MospException;
	
}
