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
import jp.mosp.time.entity.AttendanceEntityInterface;

/**
 * 勤怠計算(日々)切替条件処理インターフェース。<br>
 */
public interface AttendCalcConditionBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠計算(日々)切替条件を満たすかを判定する。<br>
	 * <br>
	 * @param attendance 勤怠(日々)情報エンティティ
	 * @return 判定結果(true：切替条件を満たす、false：切替条件を満たさない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isSatisfied(AttendanceEntityInterface attendance) throws MospException;
	
	/**
	 * 勤怠計算(日々)関連情報取得処理を設定する。<br>
	 * @param refer 勤怠計算(日々)関連情報取得処理
	 */
	void setAttendCalcRefer(AttendCalcReferenceBeanInterface refer);
	
}
