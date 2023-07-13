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

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 拡張用人事汎用管理機能チェックインターフェース
 */
public interface ExtraHumanGeneralCheckBeanInterface {
	
	/**
	 * 拡張用人事汎用管理機能入力妥当性確認(入力チェック)
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void extraValidate(String division, String viewKey) throws MospException;
	
	/**
	 * 拡張用人事汎用管理機能入力妥当性確認(インポートチェック)
	 * @param division 人事汎用管理区分
	 * @param listPlatformDto インポートデータリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void extraValidate(String division, List<? extends PlatformDtoInterface> listPlatformDto) throws MospException;
	
}
