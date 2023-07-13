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
package jp.mosp.platform.dto.system;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 汎用マスタDTOインターフェース
 *
 */
public interface GeneralDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID
	 */
	long getPfgGeneralId();
	
	/**
	 * @return 汎用区分
	 */
	String getGeneralType();
	
	/**
	 * @return 汎用コード
	 */
	String getGeneralCode();
	
	/**
	 * @return 汎用日付
	 */
	Date getGeneralDate();
	
	/**
	 * @return 汎用文字列1
	 */
	String getGeneralChar1();
	
	/**
	 * @return 汎用文字列2
	 */
	String getGeneralChar2();
	
	/**
	 * @return 汎用数値
	 */
	double getGeneralNumeric();
	
	/**
	 * @return 汎用日時
	 */
	Date getGeneralTime();
	
	/**
	 * @return 汎用バイナリ
	 */
	byte[] getGeneralBinary();
	
	/**
	 * @param pfgGeneralId セットする レコード識別ID。
	 */
	void setPfgGeneralId(long pfgGeneralId);
	
	/**
	 * @param generalType セットする 汎用区分。
	 */
	void setGeneralType(String generalType);
	
	/**
	 * @param generalCode セットする 汎用コード。
	 */
	void setGeneralCode(String generalCode);
	
	/**
	 * @param generalDate セットする 汎用日付。
	 */
	void setGeneralDate(Date generalDate);
	
	/**
	 * @param generalChar1 セットする 汎用文字列1。
	 */
	void setGeneralChar1(String generalChar1);
	
	/**
	 * @param generalChar2 セットする 汎用文字列2。
	 */
	void setGeneralChar2(String generalChar2);
	
	/**
	 * @param generalNumeric セットする 汎用数値。
	 */
	void setGeneralNumeric(double generalNumeric);
	
	/**
	 * @param generalTime セットする 汎用日時。
	 */
	void setGeneralTime(Date generalTime);
	
	/**
	 * @param generalBinary セットする 汎用バイナリ。
	 */
	void setGeneralBinary(byte[] generalBinary);
	
}
