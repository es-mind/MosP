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
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計情報参照処理インターフェース。
 */
public interface TotalTimeReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠集計からレコードを取得する。<br>
	 * 社員コード、集計年、集計月で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @return 勤怠集計DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalTimeDataDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException;
	
	/**
	 * 期間で勤怠集計情報群(キー：年月を表す日付(年月の初日))(年月昇順)を取得する。<br>
	 * @param personalId 個人ID
	 * @param firstMonth  対象年月From
	 * @param lastMonth   対象年月To
	 * @return 勤怠集計情報群(キー：年月を表す日付(年月の初日))(年月昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, TotalTimeDataDtoInterface> findTermMap(String personalId, Date firstMonth, Date lastMonth)
			throws MospException;
	
	/**
	 * 年度の勤怠集計マップを取得する。<br>
	 * 統計情報で使用する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  対象年度初日
	 * @param lastDate   対象年度最終日
	 * @return 年度の勤怠集計リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Integer, TotalTimeDataDtoInterface> findFiscalMap(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 期間で勤怠集計情報群(キー：集計月)を取得する。<br>
	 * 集計月をキーとしているため、1年間以上は取得できない。<br>
	 * @param personalId 個人ID
	 * @param startYear  期間開始年
	 * @param startMonth 期間開始月
	 * @param endYear    期間終了年
	 * @param endMonth   期間終了月
	 * @return 勤怠集計情報群(キー：集計月)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Integer, TotalTimeDataDtoInterface> findFiscalMap(String personalId, int startYear, int startMonth, int endYear,
			int endMonth) throws MospException;
	
	/**
	 * 勤怠集計情報が存在する最小の年を取得する。<br>
	 * 勤怠集計情報が存在しない場合、0を返す。<br>
	 * @return 勤怠集計情報が存在する最小の年
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getMinYear() throws MospException;
	
}
