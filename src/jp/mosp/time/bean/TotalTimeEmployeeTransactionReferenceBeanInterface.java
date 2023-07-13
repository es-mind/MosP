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
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;

/**
 * 社員勤怠集計管理参照インターフェース。
 */
public interface TotalTimeEmployeeTransactionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 社員勤怠集計管理からレコードを取得する。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @return 社員勤怠集計管理DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalTimeEmployeeDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException;
	
	/**
	 * 個人ID及び集計年月で社員勤怠集計管理情報を取得する。<br>
	 * 集計年月の社員勤怠集計管理情報をDBから纏めて取得し保持しておく。<br>
	 * @param personalId 個人ID
	 * @param month      集計年月
	 * @return 社員勤怠集計管理情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalTimeEmployeeDtoInterface getTotalTimeEmployee(String personalId, Date month) throws MospException;
	
	/**
	 * 社員勤怠集計管理から締状態を取得する。<br>
	 * @param personalId 個人ID
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @return 締状態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Integer getCutoffState(String personalId, int calculationYear, int calculationMonth) throws MospException;
	
	/**
	 * 期間内に作成されているデータが存在するか確認する。<br>
	 * @param personalId 個人ID
	 * @param startDate 期間開始日 
	 * @param endDate 期間終了日
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean getCutoffTermState(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 仮締コードから対象期間内の情報リストを取得する。<br>
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param cutoffCode 締日コード
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<TotalTimeEmployeeDtoInterface> isCutoffTermState(String cutoffCode, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 個人IDと締状態から対象日時点で最新の社員勤怠集計管理データを取得する。
	 * 最新の社員勤怠集計管理データがない場合、nullをかえす。
	 * @param personalId 個人ID
	 * @param cutoffState 締状態
	 * @return 最新の社員勤怠集計管理データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalTimeEmployeeDtoInterface findForPersonalList(String personalId, int cutoffState) throws MospException;
	
}
