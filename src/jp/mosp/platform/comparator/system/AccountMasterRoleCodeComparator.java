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
package jp.mosp.platform.comparator.system;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.platform.dto.system.AccountInfoDtoInterface;

/**
 * ロールコードによる比較クラス。<br>
 */
public class AccountMasterRoleCodeComparator implements Comparator<AccountInfoDtoInterface> {
	
	@Override
	public int compare(AccountInfoDtoInterface dto1, AccountInfoDtoInterface dto2) {
		// メインロールによる比較
		int compare = dto1.getRoleCode().compareTo(dto2.getRoleCode());
		// メインロールに差がある場合
		if (compare != 0) {
			// 比較結果を取得
			return compare;
		}
		// ユーザ追加ロールコード群を取得
		Map<String, String> extraRoles1 = dto1.getExtraRoles();
		Map<String, String> extraRoles2 = dto2.getExtraRoles();
		// ユーザ追加ロールコード1毎に処理
		for (Entry<String, String> entry1 : extraRoles1.entrySet()) {
			// ユーザ追加ロールコード2を取得
			String extraRole2 = extraRoles2.get(entry1.getKey());
			// ユーザ追加ロールコード2が設定されていない場合
			if (extraRole2 == null) {
				// 1の方が大きいと判断
				return 1;
			}
			// ユーザ追加ロールコードによる比較
			compare = entry1.getValue().compareTo(extraRole2);
			// ユーザ追加ロールコードに差がある場合
			if (compare != 0) {
				// 比較結果を取得
				return compare;
			}
		}
		// 同じであると判断
		return 0;
	}
	
}
