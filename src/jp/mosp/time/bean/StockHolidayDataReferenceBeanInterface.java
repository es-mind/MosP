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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;

/**
 * ストック休暇情報参照処理インターフェース。<br>
 */
public interface StockHolidayDataReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * ストック休暇データからレコードを取得する。<br>
	 * 個人ID、有効日、取得日で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	StockHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * ストック休暇データ取得。
	 * 個人IDと対象年月日と取得日からストック休暇データを取得。
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @param acquisitionDate 取得日
	 * @return ストック休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	StockHolidayDataDtoInterface getStockHolidayDataInfo(String personalId, Date targetDate, Date acquisitionDate)
			throws MospException;
	
	/**
	 * ストック休暇情報リストを取得する。<br>
	 * 同一取得日別有効日の情報が存在する場合は、対象日以降で最新の情報を取得する。<br>
	 * 個人IDを指定した場合は、対象個人IDの情報のみを取得する。<br>
	 * 取得日を指定した場合は、取得日が対象取得日以前の情報のみを取得する。<br>
	 * 期限日を指定した場合は、期限日が対象期限日以降の情報のみを取得する。<br>
	 * @param personalId      個人ID
	 * @param targetDate      対象日
	 * @param acquisitionDate 取得日
	 * @param limitDate       期限日
	 * @return ストック休暇情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<StockHolidayDataDtoInterface> getStockHolidayDatas(String personalId, Date targetDate, Date acquisitionDate,
			Date limitDate) throws MospException;
	
	/**
	 * 対象日でストック休暇情報リスト(取得日昇順)を取得する。<br>
	 * 同一取得日別有効日の情報が存在する場合は、対象日以降で最新の情報を取得する。<br>
	 * 取得日が対象日以前の情報のみを取得する。<br>
	 * 期限日が対象日以降の情報のみを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇情報リスト(取得日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<StockHolidayDataDtoInterface> getStockHolidayDataForDate(String personalId, Date targetDate)
			throws MospException;
	
}
