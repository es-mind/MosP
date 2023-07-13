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
package jp.mosp.platform.dto.workflow;

import java.util.Date;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 代理承認者テーブルDTOインターフェース。
 */
public interface SubApproverDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPftSubApproverId();
	
	/**
	 * @param pftSubApproverId セットするレコード識別ID。
	 */
	void setPftSubApproverId(long pftSubApproverId);
	
	/**
	 * @return 代理承認者登録No。
	 */
	String getSubApproverNo();
	
	/**
	 * @param subApproverNo セットする代理承認者登録No。
	 */
	void setSubApproverNo(String subApproverNo);
	
	/**
	 * @return 代理元個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @param personalId セットする代理元個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @return 代理開始日。
	 */
	Date getStartDate();
	
	/**
	 * @param startDate セットする代理開始日。
	 */
	void setStartDate(Date startDate);
	
	/**
	 * @return 代理終了日。
	 */
	Date getEndDate();
	
	/**
	 * @param endDate セットする代理終了日。
	 */
	void setEndDate(Date endDate);
	
	/**
	 * @return 代理承認者個人ID。
	 */
	String getSubApproverId();
	
	/**
	 * @param subApproverId セットする代理承認者個人ID。
	 */
	void setSubApproverId(String subApproverId);
	
	/**
	 * @return フロー区分。
	 */
	int getWorkflowType();
	
	/**
	 * @param workflowType セットするフロー区分。
	 */
	void setWorkflowType(int workflowType);
	
}
