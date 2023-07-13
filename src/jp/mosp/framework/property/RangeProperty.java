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
package jp.mosp.framework.property;

import java.io.Serializable;

/**
 * 範囲設定情報を扱う。
 */
public class RangeProperty implements Serializable, BaseProperty {
	
	private static final long	serialVersionUID	= -1751850279397718564L;
	
	/**
	 * 操作区分。
	 */
	private String				operationType;
	
	/**
	 * 勤務地。
	 */
	private String				workPlace;
	
	/**
	 * 雇用契約。
	 */
	private String				employmentContract;
	
	/**
	 * 所属。
	 */
	private String				section;
	
	/**
	 * 職位。
	 */
	private String				position;
	
	/**
	 * 社員。
	 */
	private String				employee;
	
	
	/**
	 * 範囲情報を生成する。
	 * @param operationType      操作区分
	 * @param workPlace          勤務地
	 * @param employmentContract 雇用契約
	 * @param section            所属
	 * @param position           職位
	 * @param employee           社員
	 */
	public RangeProperty(String operationType, String workPlace, String employmentContract, String section,
			String position, String employee) {
		this.operationType = operationType;
		this.workPlace = workPlace;
		this.employmentContract = employmentContract;
		this.section = section;
		this.position = position;
		this.employee = employee;
	}
	
	/**
	 * 操作区分を取得する。
	 * @return 操作
	 */
	public String getOperationType() {
		return operationType;
	}
	
	/**
	 * 勤務地を取得する。
	 * @return 勤務地
	 */
	public String getWorkPlace() {
		return workPlace;
	}
	
	/**
	 * 雇用契約を取得する。
	 * @return 雇用契約
	 */
	public String getEmploymentContract() {
		return employmentContract;
	}
	
	/**
	 * 所属を取得する。
	 * @return 所属
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * 職位を取得する。
	 * @return 職位
	 */
	public String getPosition() {
		return position;
	}
	
	/**
	 * 社員を取得する。
	 * @return 社員
	 */
	public String getEmployee() {
		return employee;
	}
	
	@Override
	public String getKey() {
		return operationType;
	}
	
}
