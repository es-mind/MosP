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
package jp.mosp.time.bean;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;

/**
 * 勤怠集計修正情報参照インターフェース。
 */
public interface TotalTimeCorrectionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 最新の勤怠集計修正情報取得。
	 * 社員コードと年と月から最新の勤怠集計修正情報を取得。
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @return 勤怠集計修正情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalTimeCorrectionDtoInterface getLatestTotalTimeCorrectionInfo(String personalId, int calculationYear,
			int calculationMonth) throws MospException;
	
	/**
	 * 履歴一覧取得。
	 * 社員コードと年と月から勤怠集計修正情報リストを取得。
	 * @param personalId 個人ID
	 * @param calculationYear 年
	 * @param calculationMonth 月
	 * @return 勤怠集計修正情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<TotalTimeCorrectionDtoInterface> getTotalTimeCorrectionHistory(String personalId, int calculationYear,
			int calculationMonth) throws MospException;
	
	/**
	 * 修正箇所の値取得。
	 * @param correctionType 修正箇所
	 * @param correctionValue 値
	 * @return 修正箇所の値
	 */
	String getCorrectionValue(String correctionType, String correctionValue);
	
}
