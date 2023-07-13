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
package jp.mosp.setup.vo;

import jp.mosp.platform.base.PlatformVo;

/**
 * 
 * ユーザ設定画面の情報を格納する。<br>
 * 
 */
public class FirstUserVo extends PlatformVo {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * 有効日（年）。<br>
	 */
	private String				txtActivateYear;
	
	/**
	 * 有効日（月）。<br>
	 */
	private String				txtActivateMonth;
	
	/**
	 * 有効日（日）。<br>
	 */
	private String				txtActivateDay;
	
	/**
	 * 社員コード。<br>
	 */
	private String				txtEmployeeCode;
	
	/**
	 * 姓。<br>
	 */
	private String				txtLastName;
	
	/**
	 * 名。<br>
	 */
	private String				txtFirstName;
	
	/**
	 * 姓（カナ）。<br>
	 */
	private String				txtLastKana;
	
	/**
	 * 名（カナ）。<br>
	 */
	private String				txtFirstKana;
	
	/**
	 * MosPユーザ名。<br>
	 */
	private String				txtUserId;
	
	/**
	 * 入社年。<br>
	 */
	private String				txtEntranceYear;
	
	/**
	 * 入社月。<br>
	 */
	private String				txtEntranceMonth;
	
	/**
	 * 入社日。<br>
	 */
	private String				txtEntranceDay;
	
	
	/**
	 * @param txtEntranceDay セットする txtEntranceDay<br>
	 */
	public void setTxtEntranceDay(String txtEntranceDay) {
		this.txtEntranceDay = txtEntranceDay;
	}
	
	/**
	 * @param txtUserId セットする txtUserId<br>
	 */
	public void setTxtUserId(String txtUserId) {
		this.txtUserId = txtUserId;
	}
	
	/**
	 * @return txtActivateYear<br>
	 */
	public String getTxtActivateYear() {
		return txtActivateYear;
	}
	
	/**
	 * @param txtActivateYear セットする txtActivateYear<br>
	 */
	public void setTxtActivateYear(String txtActivateYear) {
		this.txtActivateYear = txtActivateYear;
	}
	
	/**
	 * @return txtActivateMonth<br>
	 */
	public String getTxtActivateMonth() {
		return txtActivateMonth;
	}
	
	/**
	 * @param txtActivateMonth セットする txtActivateMonth<br>
	 */
	public void setTxtActivateMonth(String txtActivateMonth) {
		this.txtActivateMonth = txtActivateMonth;
	}
	
	/**
	 * @return txtActivateDay<br>
	 */
	public String getTxtActivateDay() {
		return txtActivateDay;
	}
	
	/**
	 * @param txtActivateDay セットする txtActivateDay<br>
	 */
	public void setTxtActivateDay(String txtActivateDay) {
		this.txtActivateDay = txtActivateDay;
	}
	
	/**
	 * @return txtEmployeeCode<br>
	 */
	public String getTxtEmployeeCode() {
		return txtEmployeeCode;
	}
	
	/**
	 * @param txtEmployeeCode セットする txtEmployeeCode<br>
	 */
	public void setTxtEmployeeCode(String txtEmployeeCode) {
		this.txtEmployeeCode = txtEmployeeCode;
	}
	
	/**
	 * @return txtLastName<br>
	 */
	public String getTxtLastName() {
		return txtLastName;
	}
	
	/**
	 * @param txtLastName セットする txtLastName<br>
	 */
	public void setTxtLastName(String txtLastName) {
		this.txtLastName = txtLastName;
	}
	
	/**
	 * @return txtFirstName<br>
	 */
	public String getTxtFirstName() {
		return txtFirstName;
	}
	
	/**
	 * @param txtFirstName セットする txtFirstName<br>
	 */
	public void setTxtFirstName(String txtFirstName) {
		this.txtFirstName = txtFirstName;
	}
	
	/**
	 * @return txtLastKana<br>
	 */
	public String getTxtLastKana() {
		return txtLastKana;
	}
	
	/**
	 * @param txtLastKana セットする txtLastKana
	 */
	public void setTxtLastKana(String txtLastKana) {
		this.txtLastKana = txtLastKana;
	}
	
	/**
	 * @return txtFirstKana
	 */
	public String getTxtFirstKana() {
		return txtFirstKana;
	}
	
	/**
	 * @param txtFirstKana セットする txtFirstKana
	 */
	public void setTxtFirstKana(String txtFirstKana) {
		this.txtFirstKana = txtFirstKana;
	}
	
	/**
	 * @return txtMospId
	 */
	public String getTxtUserId() {
		return txtUserId;
	}
	
	/**
	 * @return txtEntranceYear
	 */
	public String getTxtEntranceYear() {
		return txtEntranceYear;
	}
	
	/**
	 * @param txtEntranceYear セットする txtEntranceYear
	 */
	public void setTxtEntranceYear(String txtEntranceYear) {
		this.txtEntranceYear = txtEntranceYear;
	}
	
	/**
	 * @return txtEntranceMonth
	 */
	public String getTxtEntranceMonth() {
		return txtEntranceMonth;
	}
	
	/**
	 * @param txtEntranceMonth セットする txtEntranceMonth
	 */
	public void setTxtEntranceMonth(String txtEntranceMonth) {
		this.txtEntranceMonth = txtEntranceMonth;
	}
	
	/**
	 * @return getTxtEntranceDay
	 */
	public String getTxtEntranceDay() {
		return txtEntranceDay;
	}
	
}
