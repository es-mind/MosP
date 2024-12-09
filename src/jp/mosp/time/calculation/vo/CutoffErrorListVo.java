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
package jp.mosp.time.calculation.vo;

import jp.mosp.time.base.TotalTimeBaseVo;

/**
 * 勤怠集計実行時に未申請・未承認のレコードがあった場合の内容を表示する。
 */
public class CutoffErrorListVo extends TotalTimeBaseVo {
	
	private static final long	serialVersionUID	= 6133793322006002710L;
	
	private String[]			aryLblDate;
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblWorkPlace;
	private String[]			aryLblEmployment;
	private String[]			aryLblSection;
	private String[]			aryLblPosition;
	private String[]			aryLblType;
	private String[]			aryLblState;
	
	
	/**
	 * @return aryLblDate
	 */
	public String[] getAryLblDate() {
		return getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @param aryLblDate セットする aryLblDate
	 */
	public void setAryLblDate(String[] aryLblDate) {
		this.aryLblDate = getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @return aryLblWorkPlace
	 */
	public String[] getAryLblWorkPlace() {
		return getStringArrayClone(aryLblWorkPlace);
	}
	
	/**
	 * @param aryLblWorkPlace セットする aryLblWorkPlace
	 */
	public void setAryLblWorkPlace(String[] aryLblWorkPlace) {
		this.aryLblWorkPlace = getStringArrayClone(aryLblWorkPlace);
	}
	
	/**
	 * @return aryLblEmployeeCode
	 */
	public String[] getAryLblEmployeeCode() {
		return getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param aryLblEmployeeCode セットする aryLblEmployeeCode
	 */
	public void setAryLblEmployeeCode(String[] aryLblEmployeeCode) {
		this.aryLblEmployeeCode = getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @return aryLblEmployeeName
	 */
	public String[] getAryLblEmployeeName() {
		return getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param aryLblEmployeeName セットする aryLblEmployeeName
	 */
	public void setAryLblEmployeeName(String[] aryLblEmployeeName) {
		this.aryLblEmployeeName = getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @return aryLblEmployment
	 */
	public String[] getAryLblEmployment() {
		return getStringArrayClone(aryLblEmployment);
	}
	
	/**
	 * @param aryLblEmployment セットする aryLblEmployment
	 */
	public void setAryLblEmployment(String[] aryLblEmployment) {
		this.aryLblEmployment = getStringArrayClone(aryLblEmployment);
	}
	
	/**
	 * @return aryLblSection
	 */
	public String[] getAryLblSection() {
		return getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @param aryLblSection セットする aryLblSection
	 */
	public void setAryLblSection(String[] aryLblSection) {
		this.aryLblSection = getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @return aryLblType
	 */
	public String[] getAryLblType() {
		return getStringArrayClone(aryLblType);
	}
	
	/**
	 * @param aryLblType セットする aryLblType
	 */
	public void setAryLblType(String[] aryLblType) {
		this.aryLblType = getStringArrayClone(aryLblType);
	}
	
	/**
	 * @return aryLblState
	 */
	public String[] getAryLblState() {
		return getStringArrayClone(aryLblState);
	}
	
	/**
	 * @param aryLblState セットする aryLblState
	 */
	public void setAryLblState(String[] aryLblState) {
		this.aryLblState = getStringArrayClone(aryLblState);
	}
	
	/**
	 * @return aryLblPosition
	 */
	public String[] getAryLblPosition() {
		return getStringArrayClone(aryLblPosition);
	}
	
	/**
	 * @param aryLblPosition セットする aryLblPosition
	 */
	public void setAryLblPosition(String[] aryLblPosition) {
		this.aryLblPosition = getStringArrayClone(aryLblPosition);
	}
	
}
