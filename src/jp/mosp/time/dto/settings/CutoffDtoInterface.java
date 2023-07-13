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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 締日情報インターフェース。
 */
public interface CutoffDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmCutoffId();
	
	/**
	 * @return 締日コード。
	 */
	String getCutoffCode();
	
	/**
	 * @return 締日名称。
	 */
	String getCutoffName();
	
	/**
	 * @return 締日略称。
	 */
	String getCutoffAbbr();
	
	/**
	 * @return 締日。
	 */
	int getCutoffDate();
	
	/**
	 * @return 締区分。
	 */
	String getCutoffType();
	
	/**
	 * @return 未承認仮締。
	 */
	int getNoApproval();
	
	/**
	 * @param tmmCutoffId セットする レコード識別ID。
	 */
	void setTmmCutoffId(long tmmCutoffId);
	
	/**
	 * @param cutoffCode セットする 締日コード。
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * @param cutoffName セットする 締日名称。
	 */
	void setCutoffName(String cutoffName);
	
	/**
	 * @param cutoffAbbr セットする 締日略称。
	 */
	void setCutoffAbbr(String cutoffAbbr);
	
	/**
	 * @param cutoffDate セットする 締日。
	 */
	void setCutoffDate(int cutoffDate);
	
	/**
	 * @param cutoffType セットする 締区分。
	 */
	void setCutoffType(String cutoffType);
	
	/**
	 * @param noApproval セットする 未承認仮締。
	 */
	void setNoApproval(int noApproval);
	
	/**
	 * @return selfTightening
	 */
	int getSelfTightening();
	
	/**
	 * @param selfTightening セットする selfTightening
	 */
	void setSelfTightening(int selfTightening);
	
}
