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
import jp.mosp.time.dto.base.WorkTypeCodeDtoInterface;

/**
 * 勤務形態管理DTOインターフェース
 */
public interface WorkTypeDtoInterface extends PlatformDtoInterface, WorkTypeCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmWorkTypeId();
	
	/**
	 * @return 勤務形態名称。
	 */
	String getWorkTypeName();
	
	/**
	 * @return 勤務形態略称。
	 */
	String getWorkTypeAbbr();
	
	/**
	 * @param tmmWorkTypeId セットする レコード識別ID。
	 */
	void setTmmWorkTypeId(long tmmWorkTypeId);
	
	/**
	 * @param workTypeName セットする 勤務形態名称。
	 */
	void setWorkTypeName(String workTypeName);
	
	/**
	 * @param workTypeAbbr セットする 勤務形態略称。
	 */
	void setWorkTypeAbbr(String workTypeAbbr);
}
