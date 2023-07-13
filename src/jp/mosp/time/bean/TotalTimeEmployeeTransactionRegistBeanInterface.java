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
 * 社員勤怠集計管理登録インターフェース。
 */
public interface TotalTimeEmployeeTransactionRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	TotalTimeEmployeeDtoInterface getInitDto();
	
	/**
	 * 仮締を行う。<br>
	 * <br>
	 * @param personalId       個人ID
	 * @param calculationYear  集計年
	 * @param calculationMonth 集計付
	 * @param cutoffCode       締日コード
	 * @param calculationDate  計算年月
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void draft(String personalId, int calculationYear, int calculationMonth, String cutoffCode, Date calculationDate)
			throws MospException;
	
	/**
	 * 仮締を行う。<br>
	 * @param personalIdList 個人IDリスト
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @param cutoffCode 締日コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void draft(List<String> personalIdList, int calculationYear, int calculationMonth, String cutoffCode)
			throws MospException;
	
	/**
	 * 仮締解除を行う。<br>
	 * @param personalIdList 個人IDリスト
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @param cutoffCode 締日コード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void draftRelease(List<String> personalIdList, int calculationYear, int calculationMonth, String cutoffCode)
			throws MospException;
	
}
