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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;

/**
 * 勤怠集計管理参照インターフェース。
 */
public interface TotalTimeTransactionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠集計管理からレコードを取得する。<br>
	 * 締日コード、集計年、集計月で合致するレコードが無い場合、nullを返す。<br>
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @param cutoffCode 締日コード
	 * @return 勤怠集計管理DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalTimeDtoInterface findForKey(int calculationYear, int calculationMonth, String cutoffCode) throws MospException;
	
	/**
	 * 締状態を取得する。<br>
	 * <br>
	 * 保持された勤怠集計管理情報群に対象となる情報がある場合は、それを参照する。<br>
	 * 保持された勤怠集計管理情報群に対象となる情報がない場合は、
	 * DBから情報を取得し、保持する。<br>
	 * <br>
	 * 部下一覧等、同じ条件で何度も取得することが見込まれる場合に用いることで、
	 * パフォーマンスの向上を図る。<br>
	 * <br>
	 * @param calculationYear  集計年
	 * @param calculationMonth 集計月
	 * @param cutoffCode       締日コード
	 * @return 締状態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getStoredCutoffState(int calculationYear, int calculationMonth, String cutoffCode) throws MospException;
	
}
