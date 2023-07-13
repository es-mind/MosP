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
package jp.mosp.time.bean;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.base.AttendanceTotalVo;

/**
 * 日々勤怠の集計クラス。<br>
 * 統計情報詳細画面で使用する。<br>
 */
public interface AttendanceTotalInfoBeanInterface extends BaseBeanInterface {
	
	/**
	 * 年度の合計と各月の合計値を設定する。<br>
	 * 統計情報詳細画面で利用する。<br>
	 * @param personalId 個人ID
	 * @param fiscalYear 対象年度
	 * @return 年度合計+12カ月分勤怠一覧集計情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AttendanceTotalVo> setFiscalYearAttendanceTotalList(String personalId, int fiscalYear) throws MospException;
	
}
