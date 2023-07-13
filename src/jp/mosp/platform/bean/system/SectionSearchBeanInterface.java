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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;

/**
 * 所属マスタ検索インターフェース。
 */
public interface SectionSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から所属マスタリストを取得する。<br><br>
	 * {@link #setActivateDate(Date)}等で設定された条件で、検索を行う。
	 * @return 所属マスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<SectionDtoInterface> getSearchList() throws MospException;
	
	/**
	 * 検索条件から所属マスタリストを取得する。<br><br>
	 * {@link #setActivateDate(Date)}等で設定された条件で、検索を行う。
	 * @return 所属マスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<SectionDtoInterface> getExportList() throws MospException;
	
	/**
	 * @param sectionDao セットする sectionDao
	 */
	void setSectionDao(SectionDaoInterface sectionDao);
	
	/**
	 * @param activateDate セットする activateDate
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param sectionCode セットする sectionCode
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * @param sectionName セットする sectionName
	 */
	void setSectionName(String sectionName);
	
	/**
	 * @param sectionAbbr セットする sectionAbbr
	 */
	void setSectionAbbr(String sectionAbbr);
	
	/**
	 * @param closeFlag セットする closeFlag
	 */
	void setCloseFlag(String closeFlag);
	
	/**
	 * @param sectionType セットする sectionType
	 */
	void setSectionType(String sectionType);
	
}
