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
package jp.mosp.platform.dto.system.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.platform.dto.system.GeneralDtoInterface;

/**
 * 汎用マスタDTO。
 */
public class GeneralDto extends BaseDto implements GeneralDtoInterface {
	
	private static final long	serialVersionUID	= -2121091071540722715L;
	
	/**
	 * レコード識別ID。
	 */
	long						pfgGeneralId;
	
	/**
	 * 汎用区分。
	 */
	String						generalType;
	
	/**
	 * 汎用コード。
	 */
	String						generalCode;
	
	/**
	 * 汎用日付。
	 */
	Date						generalDate;
	
	/**
	 * 汎用文字列1。
	 */
	String						generalChar1;
	
	/**
	 * 汎用文字列2。
	 */
	String						generalChar2;
	
	/**
	 * 汎用数値。
	 */
	double						generalNumeric;
	
	/**
	 * 汎用日時。
	 */
	Date						generalTime;
	
	/**
	 * 汎用バイナリ。
	 */
	byte[]						generalBinary;
	
	
	@Override
	public String getGeneralChar1() {
		return generalChar1;
	}
	
	@Override
	public String getGeneralChar2() {
		return generalChar2;
	}
	
	@Override
	public String getGeneralCode() {
		return generalCode;
	}
	
	@Override
	public Date getGeneralDate() {
		return getDateClone(generalDate);
	}
	
	@Override
	public double getGeneralNumeric() {
		return generalNumeric;
	}
	
	@Override
	public Date getGeneralTime() {
		return getDateClone(generalTime);
	}
	
	@Override
	public String getGeneralType() {
		return generalType;
	}
	
	@Override
	public long getPfgGeneralId() {
		return pfgGeneralId;
	}
	
	@Override
	public void setGeneralChar1(String generalChar1) {
		this.generalChar1 = generalChar1;
	}
	
	@Override
	public void setGeneralChar2(String generalChar2) {
		this.generalChar2 = generalChar2;
	}
	
	@Override
	public void setGeneralCode(String generalCode) {
		this.generalCode = generalCode;
	}
	
	@Override
	public void setGeneralDate(Date generalDate) {
		this.generalDate = getDateClone(generalDate);
	}
	
	@Override
	public void setGeneralNumeric(double generalNumeric) {
		this.generalNumeric = generalNumeric;
	}
	
	@Override
	public void setGeneralTime(Date generalTime) {
		this.generalTime = getDateClone(generalTime);
	}
	
	@Override
	public void setGeneralType(String generalType) {
		this.generalType = generalType;
	}
	
	@Override
	public void setPfgGeneralId(long pfgGeneralId) {
		this.pfgGeneralId = pfgGeneralId;
	}
	
	@Override
	public byte[] getGeneralBinary() {
		return CapsuleUtility.getByteArrayClone(generalBinary);
	}
	
	@Override
	public void setGeneralBinary(byte[] generalBinary) {
		this.generalBinary = CapsuleUtility.getByteArrayClone(generalBinary);
	}
	
}
