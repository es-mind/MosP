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
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;

/**
 * 有給休暇初年度管理DAOインターフェース
 */
public interface PaidHolidayFirstYearDaoInterface extends BaseDaoInterface {
	
	/**
	 * 有給休暇コードと有効日と入社月から有給休暇初年度管理情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @param entranceMonth 入社月
	 * @return 有給休暇初年度管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayFirstYearDtoInterface findForKey(String paidHolidayCode, Date activateDate, int entranceMonth)
			throws MospException;
	
	/**
	 * 有給休暇初年度管理マスタ取得。
	 * <p>
	 * 有給休暇コードと有効日と入社月から有給休暇初年度管理マスタを取得する。
	 * </p>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @param entranceMonth 入社月
	 * @return 有給休暇初年度管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayFirstYearDtoInterface findForInfo(String paidHolidayCode, Date activateDate, int entranceMonth)
			throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 有給休暇コードと入社月から有給休暇初年度管理マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param entranceMonth 入社月
	 * @return 有給休暇初年度管理マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayFirstYearDtoInterface> findForHistory(String paidHolidayCode, int entranceMonth)
			throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 有給休暇初年度管理検索条件マップ
	 */
	Map<String, Object> getParamsMap();
}
