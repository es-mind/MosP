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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 勤怠エクスポート共通インターフェース。
 */
public interface TimeExportBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠関連マスタのエクスポートを行う。<br>
	 * @param exportCode             エクスポートコード
	 * @param startYear              開始年
	 * @param startMonth             開始月
	 * @param endYear                終了年
	 * @param endMonth               終了月
	 * @param cutoffCode             締日コード
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param needLowerSection       下位所属含む
	 * @param positionCode           職位コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void export(String exportCode, int startYear, int startMonth, int endYear, int endMonth, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, boolean needLowerSection,
			String positionCode) throws MospException;
	
}
