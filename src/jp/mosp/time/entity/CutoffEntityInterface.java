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
package jp.mosp.time.entity;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.base.CodeDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;

/**
 * 締日エンティティインターフェース。<br>
 */
public interface CutoffEntityInterface extends CodeDtoInterface {
	
	/**
	 * 締日情報を取得する。<br>
	 * @return 締日情報
	 */
	CutoffDtoInterface getCutoffDto();
	
	/**
	 * 締日情報を設定する。<br>
	 * @param dto 締日情報
	 */
	void setCutoffDto(CutoffDtoInterface dto);
	
	/**
	 * 締日情報が存在するかを確認する。<br>
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	boolean isExist();
	
	/**
	 * 締日名称を取得する。<br>
	 * 締日情報が存在しない場合は、空文字を取得する。<br>
	 * @return 締日名称
	 */
	String getCutoffName();
	
	/**
	 * 締日を取得する。<br>
	 * 締日情報が存在しない場合は、0(月末締)を取得する。<br>
	 * @return 締日
	 */
	int getCutoffDate();
	
	/**
	 * 未承認仮締を取得する。<br>
	 * 締日情報が存在しない場合は、0(有効)を取得する。<br>
	 * @return 未承認仮締
	 */
	int getNoApproval();
	
	/**
	 * 自己月締が有効であるかを確認する。<br>
	 * 締日情報が存在しない場合は、false(無効)を取得する。<br>
	 * @return 確認結果(true：自己月締有効、false：無効)
	 */
	boolean isSelfTightening();
	
	/**
	 * 対象年月及び締日から締期間初日を取得する。<br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間初日
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffFirstDate(int targetYear, int targetMonth, MospParams mospParams) throws MospException;
	
	/**
	 * 対象日が含まれる締期間の初日を取得する。<br>
	 * @param targetDate  対象日
	 * @param mospParams  MosP処理情報
	 * @return 締期間初日
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffFirstDate(Date targetDate, MospParams mospParams) throws MospException;
	
	/**
	 * 対象年月及び締日から締期間最終日を取得する。<br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間最終日
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffLastDate(int targetYear, int targetMonth, MospParams mospParams) throws MospException;
	
	/**
	 * 対象日が含まれる締期間の最終日を取得する。<br>
	 * @param targetDate  対象日
	 * @param mospParams  MosP処理情報
	 * @return 締期間最終日
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffLastDate(Date targetDate, MospParams mospParams) throws MospException;
	
	/**
	 * 対象年月における締期間の基準日を取得する。<br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間基準日
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffTermTargetDate(int targetYear, int targetMonth, MospParams mospParams) throws MospException;
	
	/**
	 * 対象年月における締期間の集計日を取得する。<br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間集計日
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffCalculationDate(int targetYear, int targetMonth, MospParams mospParams) throws MospException;
	
	/**
	 * 対象日が含まれる締月(年月の初日)を取得する。<br>
	 * @param targetDate 対象日
	 * @param mospParams MosP処理情報
	 * @return 対象日が含まれる締月(年月の初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	Date getCutoffMonth(Date targetDate, MospParams mospParams) throws MospException;
	
	/**
	 * 締期間(日付のリスト)を取得する。<br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間(日付のリスト)
	 * @throws MospException 日付操作に失敗した場合
	 */
	List<Date> getCutoffTerm(int targetYear, int targetMonth, MospParams mospParams) throws MospException;
	
	/**
	 * 締期間(日付のリスト)を取得する。<br>
	 * 対象日が含まれる締月の期間を取得する。<br>
	 * @param targetDate  対象日
	 * @param mospParams  MosP処理情報
	 * @return 締期間集計日
	 * @throws MospException 日付操作に失敗した場合
	 */
	List<Date> getCutoffTerm(Date targetDate, MospParams mospParams) throws MospException;
	
}
