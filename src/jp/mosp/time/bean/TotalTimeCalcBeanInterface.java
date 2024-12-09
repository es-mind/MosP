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
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計インターフェース。
 */
public interface TotalTimeCalcBeanInterface extends BaseBeanInterface {
	
	/**
	 * 仮締を行う。<br>
	 * <br>
	 * 勤怠集計前の確認、勤怠集計、データの登録を行う。<br>
	 * 勤怠集計前の確認でエラーがあった場合は、集計時エラー内容情報リストを返す。<br>
	 * <br>
	 * 対象年月において対象締日コードが適用されている個人IDに対して処理を行う。<br>
	 * <br>
	 * 勤怠集計管理画面等で用いる。<br>
	 * <br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffCode  締日コード
	 * @return 集計時エラー内容情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<CutoffErrorListDtoInterface> tightening(int targetYear, int targetMonth, String cutoffCode)
			throws MospException;
	
	/**
	 * 仮締を行う。<br>
	 * <br>
	 * 勤怠集計前の確認、勤怠集計、データの登録を行う。<br>
	 * 勤怠集計前の確認でエラーがあった場合は、集計時エラー内容情報リストを返す。<br>
	 * <br>
	 * 処理の結果、対象年月において対象締日コードが設定されている社員全員が
	 * 仮締状態となった場合、対象年月における対象締日コードを仮締とする。<br>
	 * <br>
	 * 勤怠集計結果一覧画面等で用いる。<br>
	 * <br>
	 * @param aryPersonalId 個人ID配列
	 * @param targetYear    対象年
	 * @param targetMonth   対象月
	 * @param cutoffCode    締日コード
	 * @return 集計時エラー内容情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<CutoffErrorListDtoInterface> tightening(String[] aryPersonalId, int targetYear, int targetMonth,
			String cutoffCode) throws MospException;
	
	/**
	 * 仮締を行う。<br>
	 * <br>
	 * 勤怠集計前の確認、勤怠集計、データの登録を行う。<br>
	 * 勤怠集計前の確認でエラーがあった場合は、集計時エラー内容情報リストを返す。<br>
	 * <br>
	 * 当メソッドは一般社員が自己月締をする際に用いることを想定しており、
	 * 処理の結果仮に全員が仮締になったとしても、
	 * 対象年月における対象締日コードを仮締とはしない。<br>
	 * <br>
	 * 勤怠一覧画面等で用いる。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 集計時エラー内容情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<CutoffErrorListDtoInterface> tightening(String personalId, int targetYear, int targetMonth)
			throws MospException;
	
	/**
	 * 勤怠計算を行う。<br>
	 * <br>
	 * 勤怠集計前の確認は、行わない。<br>
	 * 部下一覧画面等で用いる。<br>
	 * <br>
	 * @param personalId  個人ID配列
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ残す、false：申請済申請を残す)
	 * @return 勤怠集計情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalTimeDataDtoInterface calc(String personalId, int targetYear, int targetMonth, boolean isCompleted)
			throws MospException;
	
}
