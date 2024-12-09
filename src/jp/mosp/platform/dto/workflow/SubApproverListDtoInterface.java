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
package jp.mosp.platform.dto.workflow;

/**
 * 代理承認者テーブル検索リストDTOインターフェース。
 */
public interface SubApproverListDtoInterface extends SubApproverDtoInterface {
	
	/**
	 * @return 代理承認者社員コード
	 */
	String getSubApproverCode();
	
	/**
	 * @param subApproverCode セットする代理承認者社員コード
	 */
	void setSubApproverCode(String subApproverCode);
	
	/**
	 * @return 代理承認者名
	 */
	String getSubApproverName();
	
	/**
	 * @param subApproverName セットする代理承認者名
	 */
	void setSubApproverName(String subApproverName);
	
	/**
	 * @return 代理承認者所属コード
	 */
	String getSubApproverSectionCode();
	
	/**
	 * @param subApproverSectionCode セットする代理承認者所属コード
	 */
	void setSubApproverSectionCode(String subApproverSectionCode);
	
	/**
	 * @return 代理承認者所属名
	 */
	String getSubApproverSectionName();
	
	/**
	 * @param subApproverSectionName セットする代理承認者所属名
	 */
	void setSubApproverSectionName(String subApproverSectionName);
	
}
