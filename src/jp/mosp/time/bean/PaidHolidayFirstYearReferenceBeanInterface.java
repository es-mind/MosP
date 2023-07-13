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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;

/**
 * 有給休暇初年度付与参照インターフェース。
 */
public interface PaidHolidayFirstYearReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 有給休暇初年度付与マスタからレコードを取得する。<br>
	 * 有給休暇コード、有効日、入社月で合致するレコードが無い場合、nullを返す。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @param entranceMonth 入社月
	 * @return 有給休暇マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayFirstYearDtoInterface findForKey(String paidHolidayCode, Date activateDate, int entranceMonth)
			throws MospException;
	
}
