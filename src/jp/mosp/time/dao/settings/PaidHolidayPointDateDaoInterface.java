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
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;

/**
 * 有給休暇基準日管理DAOインターフェース
 */
public interface PaidHolidayPointDateDaoInterface extends BaseDaoInterface {
	
	/**
	 * 有給休暇コードと有効日と基準日経過回数から有給休暇基準日管理情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @param timesPointDate 基準日経過回数
	 * @return 有給休暇基準日管理マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	PaidHolidayPointDateDtoInterface findForKey(String paidHolidayCode, Date activateDate, int timesPointDate)
			throws MospException;
	
	/**
	 * 有給休暇コードと有効日から有給休暇基準日管理情報リストを取得する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @return 有給休暇基準日管理マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayPointDateDtoInterface> findForList(String paidHolidayCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 有給休暇コードと基準日経過回数から有給休暇基準日管理マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param timesPointDate 基準日経過回数
	 * @return 有給休暇基準日管理マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<PaidHolidayPointDateDtoInterface> findForHistory(String paidHolidayCode, int timesPointDate)
			throws MospException;
	
}
