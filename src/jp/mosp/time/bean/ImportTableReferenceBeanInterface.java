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

import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * インポートテーブル参照インターフェース。
 */
public interface ImportTableReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * インポートされた内容からDTOリストを取得する。
	 * @param importCode インポートコード
	 * @param list 対象リスト
	 * @return DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<AttendanceDtoInterface> getAttendanceList(String importCode, List<String[]> list) throws MospException;
	
	/**
	 * インポートされた内容からDTOリストを取得する。
	 * @param importCode インポートコード
	 * @param list 対象リスト
	 * @return DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<TotalTimeDataDtoInterface> getTotalTimeList(String importCode, List<String[]> list) throws MospException;
	
	/**
	 * インポートされた内容からDTOリストを取得する。
	 * @param importCode インポートコード
	 * @param list 対象リスト
	 * @return DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDataDtoInterface> getPaidHolidayList(String importCode, List<String[]> list) throws MospException;
	
	/**
	 * インポートされた内容からDTOリストを取得する。
	 * @param importCode インポートコード
	 * @param list 対象リスト
	 * @return DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<StockHolidayDataDtoInterface> getStockHolidayList(String importCode, List<String[]> list) throws MospException;
	
	/**
	 * インポートされた内容からDTOリストを取得する。
	 * @param importCode インポートコード
	 * @param list 対象リスト
	 * @return DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<HolidayDataDtoInterface> getHolidayDataList(String importCode, List<String[]> list) throws MospException;
	
	/**
	 * インポートされた内容からDTOリストを取得する。
	 * @param importCode インポートコード
	 * @param list 対象リスト
	 * @return 勤務形態マップ＜勤務形態データ,＜勤務形態項目コード,勤務形態項目データ＞＞
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	Map<WorkTypeDtoInterface, Map<String, WorkTypeItemDtoInterface>> getWorkType(String importCode, List<String[]> list)
			throws MospException;
	
}
