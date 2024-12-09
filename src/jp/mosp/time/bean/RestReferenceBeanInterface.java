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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 勤怠データ休憩情報参照インターフェース。
 */
public interface RestReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 休憩データDTOを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @param times 回数
	 * @return 休憩データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RestDtoInterface findForKey(String personalId, Date workDate, int timesWork, int times) throws MospException;
	
	/**
	 * 勤怠データ休憩情報リスト取得。
	 * <p>
	 * 社員コードと勤務日と勤務回数から勤怠データ休憩情報リストを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param works 勤務回数
	 * @return 勤怠データ休憩情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<RestDtoInterface> getRestList(String personalId, Date workDate, int works) throws MospException;
	
	/**
	 * 休憩情報リストを取得する。<br>
	 * 社員コードと勤務日から勤怠データ休憩情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @return 休憩情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<RestDtoInterface> getRestList(String personalId, Date workDate) throws MospException;
	
}
