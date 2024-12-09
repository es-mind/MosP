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
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.entity.AttendCalcEntityInterface;

/**
 * 勤怠計算(日々)実行時追加処理インターフェース。<br>
 */
public interface AttendCalcExecuteExtraBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠計算(日々)関連情報取得処理を設定する。<br>
	 * @param refer 勤怠計算(日々)関連情報取得処理
	 */
	void setAttendCalcRefer(AttendCalcReferenceBeanInterface refer);
	
	/**
	 * 勤怠計算(日々)実行追加処理を実行する。<br>
	 * 勤怠計算(日々)エンティティの汎用パラメータ群に値を設定したりする際に利用することを想定している。<br>
	 * <br>
	 * @param calc 勤怠計算(日々)エンティティ
	 * @param dto  勤怠(日々)情報(計算結果を設定するDTO)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void execute(AttendCalcEntityInterface calc, AttendanceDtoInterface dto) throws MospException;
	
}
