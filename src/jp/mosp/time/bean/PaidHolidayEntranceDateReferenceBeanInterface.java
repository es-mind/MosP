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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;

/**
 * 有給休暇自動付与(入社日)参照インターフェース。
 */
public interface PaidHolidayEntranceDateReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 有給休暇自動付与(入社日)マスタからレコードを取得する。<br>
	 * 有給休暇コード、有効日、勤続勤務月数で合致するレコードが無い場合、nullを返す。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @param workMonth 勤続勤務月数
	 * @return 有給休暇自動付与(入社日)マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayEntranceDateDtoInterface findForKey(String paidHolidayCode, Date activateDate, int workMonth)
			throws MospException;
	
	/**
	 * 有給休暇自動付与(入社日)マスタからレコードリストを取得する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @return 有給休暇自動付与(入社日)マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayEntranceDateDtoInterface> findForList(String paidHolidayCode, Date activateDate)
			throws MospException;
	
}
