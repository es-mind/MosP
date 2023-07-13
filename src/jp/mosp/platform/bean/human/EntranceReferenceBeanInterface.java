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
package jp.mosp.platform.bean.human;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.EntranceDtoInterface;

/**
 * 人事入社情報参照処理インターフェース。<br>
 */
public interface EntranceReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 入社情報取得。<br>
	 * 個人IDより人事入社情報を生成する。<br>
	 * @param personalId 個人ID
	 * @return 人事入社情報DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	EntranceDtoInterface getEntranceInfo(String personalId) throws MospException;
	
	/**
	 * 入社日取得。<br>
	 * 人事入社情報から入社日を取得する。<br>
	 * @param personalId 個人ID
	 * @return 入社日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getEntranceDate(String personalId) throws MospException;
	
	/**
	 * 入社判断。<br>
	 * 個人IDと対象年月日から入社判断をする。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 入社の場合true、そうでない場合false。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isEntered(String personalId, Date targetDate) throws MospException;
	
}
