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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;

/**
 * 休暇付与情報参照処理インターフェース。<br>
 */
public interface HolidayDataReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 休暇データからレコードを取得する。<br>
	 * 社員コード、有効日、休暇コード、休暇区分で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayDataDtoInterface findForKey(String personalId, Date activateDate, String holidayCode, int holidayType)
			throws MospException;
	
	/**
	 * 対象日時点で有効な休暇付与情報リストを取得する。<br>
	 * 対象日が有効日から取得期限の間にある有効な休暇付与情報を取得する。<br>
	 * <br>
	 * @param personalId     個人ID
	 * @param targetDate     対象日
	 * @param holidayType    休暇区分(休暇種別1)
	 * @return 休暇付与情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayDataDtoInterface> getActiveList(String personalId, Date targetDate, int holidayType)
			throws MospException;
	
	/**
	 * 対象期間内に付与された休暇付与情報リストを取得する。<br>
	 * 休暇付与情報の有効日が対象期間内である有効な休暇付与情報を取得する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param firstDate   対象期間初日
	 * @param lastDate    対象期間最終日
	 * @param holidayType 休暇区分(休暇種別1：特別休暇orその他休暇)
	 * @param holidayCode 休暇コード(休暇種別2)
	 * @return 休暇付与情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public List<HolidayDataDtoInterface> getActiveListForTerm(String personalId, Date firstDate, Date lastDate,
			int holidayType, String holidayCode) throws MospException;
	
}
