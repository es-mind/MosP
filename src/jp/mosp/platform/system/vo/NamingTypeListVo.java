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
package jp.mosp.platform.system.vo;

import jp.mosp.platform.base.PlatformVo;

/**
 * 名称区分情報一覧画面を格納する。
 */
public class NamingTypeListVo extends PlatformVo {
	
	private static final long	serialVersionUID	= 2491156592113464915L;
	
	/**
	 * 名称区分。
	 */
	private String				lblNamingType;
	
	/**
	 * 名称区分名。
	 */
	private String				lblNamingTypeName;
	
	/**
	 * 名称区分名。
	 */
	private String[][]			aryLblNamingTypeName;
	
	
	/**
	 * @param lblNamingType セットする lblNamingType
	 */
	public void setLblNamingType(String lblNamingType) {
		this.lblNamingType = lblNamingType;
	}
	
	/**
	 * @return lblNamingType
	 */
	public String getLblNamingType() {
		return lblNamingType;
	}
	
	/**
	 * @param lblNamingTypeName セットする lblNamingTypeName
	 */
	public void setLblNamingTypeName(String lblNamingTypeName) {
		this.lblNamingTypeName = lblNamingTypeName;
	}
	
	/**
	 * @return lblNamingTypeName
	 */
	public String getLblNamingTypeName() {
		return lblNamingTypeName;
	}
	
	/**
	 * @param aryLblNamingTypeName セットする aryLblNamingTypeName
	 */
	public void setAryLblNamingTypeName(String[][] aryLblNamingTypeName) {
		this.aryLblNamingTypeName = getStringArrayClone(aryLblNamingTypeName);
	}
	
	/**
	 * @return aryLblNamingTypeName
	 */
	public String[][] getAryLblNamingTypeName() {
		return getStringArrayClone(aryLblNamingTypeName);
	}
	
}
