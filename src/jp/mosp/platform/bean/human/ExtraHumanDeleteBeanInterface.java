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
package jp.mosp.platform.bean.human;

import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;

/**
 * 人事基本情報削除追加クラスインターフェース。<br>
 */
public interface ExtraHumanDeleteBeanInterface {
	
	/**
	 * ロック対象テーブルを取得する。
	 * @return ロック対象テーブルのセット
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	Set<String> getTargetTable() throws MospException;
	
	/**
	 * 人事マスタの履歴削除時、勤怠関連の削除要否確認を行う。
	 * 削除ケース毎に処理。
	 * @param list 削除対象人事情報履歴
	 * @param deleateIndex 削除DTOのi番目
	 * @param deleteCase 削除ケース
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkDelete(List<HumanDtoInterface> list, int deleateIndex, int deleteCase) throws MospException;
	
	/**
	 * 個人情報履歴の削除処理を行う。
	 * @param dto 削除対象DTO
	 * @param isAllDelete 対象社員情報全削除フラグ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void humanDelete(HumanDtoInterface dto, boolean isAllDelete) throws MospException;
}
