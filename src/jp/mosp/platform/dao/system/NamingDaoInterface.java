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
package jp.mosp.platform.dao.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.NamingDtoInterface;

/**
 * 名称区分マスタDAOインターフェース。<br>
 */
public interface NamingDaoInterface extends BaseDaoInterface {
	
	/**
	 * 名称区分・名称項目コードと有効日から名称区分情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param namingType 名称区分 
	 * @param namingItemCode 名称項目コード
	 * @param activateDate           有効日
	 * @return 名称区分情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	NamingDtoInterface findForKey(String namingType, String namingItemCode, Date activateDate) throws MospException;
	
	/**
	 * 名称項目マスタ取得。
	 * <p>
	 * 名称区分・名称項目コードと有効日から名称区分マスタを取得する。
	 * </p>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param activateDate 有効日
	 * @return 名称区分情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	NamingDtoInterface findForInfo(String namingType, String namingItemCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 名称区分・名称項目コードから名称区分マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @return 名称区分マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<NamingDtoInterface> findForHistory(String namingType, String namingItemCode) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から名称区分マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 名称区分マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<NamingDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 雇用契約マスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 名称区分と有効日から名称区分マスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param namingType ルート適用コード
	 * @param activateDate 有効日
	 * @return ルート適用マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<NamingDtoInterface> findForList(String namingType, Date activateDate) throws MospException;
	
}
