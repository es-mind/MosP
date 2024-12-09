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
package jp.mosp.platform.dao.human;

import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;

/**
 * 人事汎用バイナリ通常情報DAOインターフェース。
 */
public interface HumanBinaryNormalDaoInterface extends BaseDaoInterface {
	
	/**
	 * 人事バイナリ通常情報を取得する。
	 * @param personalId 個人ID
	 * @param itemName 項目名
	 * @return 人事バイナリ通常情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	HumanBinaryNormalDtoInterface findForInfo(String personalId, String itemName) throws MospException;
	
	/**
	 * 対象人事汎用管理項目名以外で存在する管理項目名を取得。
	 * <p>
	 * 人事汎用汎用管理項目名・個人IDから人事入社情報を取得する。
	 * </p>
	 * @param itemNames 人事汎用汎用項目名
	 * @return 人事汎用通常情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanBinaryNormalDtoInterface> findForInfoNotIn(List<String> itemNames) throws MospException;
	
	/**
	 * 人事バイナリ通常情報一覧を取得する。
	 * @param personalId 個人ID
	 * @return 人事バイナリ通常情報一覧
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	List<HumanBinaryNormalDtoInterface> findForList(String personalId) throws MospException;
}
