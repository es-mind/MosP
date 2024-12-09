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

import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;

/**
 * 欠勤集計データDAOインターフェース
 */
public interface TotalAbsenceDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤怠欠勤集計データからレコードを取得する。<br>
	 * 個人ID、年、月、手当コードで合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @param absenceCode 欠勤集計コード
	 * @return 勤怠欠勤集計データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TotalAbsenceDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String absenceCode) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 欠勤集計データ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 個人IDと計算年と計算月から欠勤集計データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @return 欠勤集計データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TotalAbsenceDtoInterface> findForList(String personalId, int calculationYear, int calculationMonth)
			throws MospException;
	
}
