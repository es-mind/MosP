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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeListDtoInterface;

/**
 * 勤務形態管理検索処理インターフェース。<br>
 */
public interface WorkTypeSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 条件による検索。
	 * 検索条件から勤務形態リストを取得。
	 * @return 勤務形態リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<WorkTypeListDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param workTypeCode セットする 勤務形態コード。
	 */
	void setWorkTypeCode(String workTypeCode);
	
	/**
	 * @param workTypeName セットする 勤務形態名称。
	 */
	void setWorkTypeName(String workTypeName);
	
	/**
	 * @param workTypeAbbr セットする 勤務形態略称。
	 */
	void setWorkTypeAbbr(String workTypeAbbr);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
