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
package jp.mosp.platform.bean.workflow;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.SubApproverListDtoInterface;

/**
 * 代理承認者テーブル検索インターフェース。
 */
public interface SubApproverSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件からメッセージリストを取得する。<br><br>
	 * @return メッセージリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<SubApproverListDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param messageNo セットする代理元個人ID。
	 */
	void setPersonalId(String messageNo);
	
	/**
	 * @param targetYear セットする対象年。
	 */
	void setTargetYear(int targetYear);
	
	/**
	 * @param targetMonth セットする対象月。
	 */
	void setTargetMonth(int targetMonth);
	
	/**
	 * @param sectionName セットする代理人所属名。
	 */
	void setSectionName(String sectionName);
	
	/**
	 * @param employeeName セットする代理人氏名。
	 */
	void setEmployeeName(String employeeName);
	
	/**
	 * @param inactivateFlag セットする有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
