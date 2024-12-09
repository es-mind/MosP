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
package jp.mosp.time.dto.settings;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 申請一覧共通DTOインターフェース
 */
public interface RequestListDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return 承認段階。
	 */
	int getStage();
	
	/**
	 * @return 承認状況。
	 */
	String getState();
	
	/**
	 * @return 承認者。
	 */
	String getApproverName();
	
	/**
	 * @return ワークフロー番号。
	 */
	long getWorkflow();
	
	/**
	 * @param stage セットする 承認段階。
	 */
	void setStage(int stage);
	
	/**
	 * @param state セットする 承認状況。
	 */
	void setState(String state);
	
	/**
	 * @param approverName セットする 承認者。
	 */
	void setApproverName(String approverName);
	
	/**
	 * @param workflow セットする ワークフロー番号。
	 */
	void setWorkflow(long workflow);
	
}
