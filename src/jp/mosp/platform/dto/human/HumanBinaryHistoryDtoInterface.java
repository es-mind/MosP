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
package jp.mosp.platform.dto.human;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 人事汎用バイナリ履歴情報DTOインターフェース。
 */
public interface HumanBinaryHistoryDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfaHumanBinaryHistoryId();
	
	/**
	 * @return 個人ID
	 */
	String getPersonalId();
	
	/**
	 * @return 人事項目区分。
	 */
	String getHumanItemType();
	
	/**
	 * @return 人事項目バイナリ値。
	 */
	byte[] getHumanItemBinary();
	
	/**
	 * @return ファイル拡張子。
	 */
	String getFileType();
	
	/**
	 * @return ファイル名。
	 */
	String getFileName();
	
	/**
	 * @return ファイル備考。
	 */
	String getFileRemark();
	
	/**
	 * @param pfaHumanBinaryHistoryId セットする レコード識別ID。
	 */
	void setPfaHumanBinaryHistoryId(long pfaHumanBinaryHistoryId);
	
	/**
	 * @param personalId セットする 個人ID
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param humanItemType セットする 人事項目区分。
	 */
	void setHumanItemType(String humanItemType);
	
	/**
	 * @param humanItemBinary セットする 人事項目バイナリ値。
	 */
	void setHumanItemBinary(byte[] humanItemBinary);
	
	/**
	 * @param fileType セットする ファイル拡張子。
	 */
	void setFileType(String fileType);
	
	/**
	 * @param fileName セットする ファイル名。
	 */
	void setFileName(String fileName);
	
	/**
	 * @param fileRemark セットする ファイル備考。
	 */
	void setFileRemark(String fileRemark);
}
