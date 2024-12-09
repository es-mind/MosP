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
package jp.mosp.time.base;

import java.util.List;

import jp.mosp.framework.base.BaseVo;

/**
 * 勤怠一覧等で表示される勤怠の集計値を保持する。<br>
 */
public class AttendanceTotalVo extends BaseVo {
	
	private static final long	serialVersionUID	= -4092455962401812006L;
	
	/**
	 * 項目名がnullだったら1個あける。<br>
	 * 項目名があり値がnullの場合は、項目のみの箱になる。<br>
	 * 項目名には日本語を入れる。<br>
	 * 1行の項目数によって改行する。<br>
	 */
	
	// クラス名
	private String				className;
	
	// 対象年月
	private String				targetDate;
	
	// 項目リスト
	private List<String>		titleList;
	
	// 値リスト
	private List<String>		valueList;
	
	// 1行の項目数
	private int					rowItemNumber;
	
	
	/**
	 * @return className
	 */
	@Override
	public String getClassName() {
		return className;
	}
	
	/**
	 * @param className セットする className
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * @return rowItemNumber
	 */
	public int getRowItemNumber() {
		return rowItemNumber;
	}
	
	/**
	 * @param rowItemNumber セットする rowItemNumber
	 */
	public void setRowItemNumber(int rowItemNumber) {
		this.rowItemNumber = rowItemNumber;
	}
	
	/**
	 * @return titleList
	 */
	public List<String> getTitleList() {
		return titleList;
	}
	
	/**
	 * @param titleList セットする titleList
	 */
	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
	}
	
	/**
	 * @return valueList
	 */
	public List<String> getValueList() {
		return valueList;
	}
	
	/**
	 * @param valueList セットする valueList
	 */
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
	
	/**
	 * @return targetDate
	 */
	public String getTargetDate() {
		return targetDate;
	}
	
	/**
	 * @param targetDate セットする targetDate
	 */
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
}
