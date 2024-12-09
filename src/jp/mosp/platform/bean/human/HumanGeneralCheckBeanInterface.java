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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 人事汎用管理機能チェックインターフェース
 */
public interface HumanGeneralCheckBeanInterface extends BaseBeanInterface {
	
	/**
	 * 妥当性確認(画面登録用)
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void validate(String division, String viewKey) throws MospException;
	
	/**
	 * 妥当性確認(インポート用)
	 * @param division 人事汎用管理区分
	 * @param listPlatformDto チェック対象DTOリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void validate(String division, List<? extends PlatformDtoInterface> listPlatformDto) throws MospException;
	
}
