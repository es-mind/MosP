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
package jp.mosp.platform.human.vo;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 休職情報詳細画面の情報を格納する。
 */
public class SuspensionCardVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= 6978483944674033014L;
	
	private String[]			aryHidPfaHumanSuspension;
	private String[]			aryTxtSuspensionStartYear;
	private String[]			aryTxtSuspensionStartMonth;
	private String[]			aryTxtSuspensionStartDay;
	private String[]			aryTxtSuspensionEndYear;
	private String[]			aryTxtSuspensionEndMonth;
	private String[]			aryTxtSuspensionEndDay;
	private String[]			aryTxtSuspensionScheduleEndYear;
	private String[]			aryTxtSuspensionScheduleEndMonth;
	private String[]			aryTxtSuspensionScheduleEndDay;
	private String[]			aryTxtSuspensionReason;
	
	
	/**
	 * @param aryHidPfaHumanSuspension セットする aryHidPfaHumanSuspension
	 */
	public void setAryHidPfaHumanSuspension(String[] aryHidPfaHumanSuspension) {
		this.aryHidPfaHumanSuspension = getStringArrayClone(aryHidPfaHumanSuspension);
	}
	
	/**
	 * @return aryHidPfaHumanSuspension
	 */
	public String[] getAryHidPfaHumanSuspension() {
		return getStringArrayClone(aryHidPfaHumanSuspension);
	}
	
	/**
	 * @param aryTxtSuspensionStartYear セットする aryTxtSuspensionStartYear
	 */
	public void setAryTxtSuspensionStartYear(String[] aryTxtSuspensionStartYear) {
		this.aryTxtSuspensionStartYear = getStringArrayClone(aryTxtSuspensionStartYear);
	}
	
	/**
	 * @return aryTxtSuspensionStartYear
	 */
	public String[] getAryTxtSuspensionStartYear() {
		return getStringArrayClone(aryTxtSuspensionStartYear);
	}
	
	/**
	 * @param aryTxtSuspensionStartMonth セットする aryTxtSuspensionStartMonth
	 */
	public void setAryTxtSuspensionStartMonth(String[] aryTxtSuspensionStartMonth) {
		this.aryTxtSuspensionStartMonth = getStringArrayClone(aryTxtSuspensionStartMonth);
	}
	
	/**
	 * @return aryTxtSuspensionStartMonth
	 */
	public String[] getAryTxtSuspensionStartMonth() {
		return getStringArrayClone(aryTxtSuspensionStartMonth);
	}
	
	/**
	 * @param aryTxtSuspensionStartDay セットする aryTxtSuspensionStartDay
	 */
	public void setAryTxtSuspensionStartDay(String[] aryTxtSuspensionStartDay) {
		this.aryTxtSuspensionStartDay = getStringArrayClone(aryTxtSuspensionStartDay);
	}
	
	/**
	 * @return aryTxtSuspensionStartDay
	 */
	public String[] getAryTxtSuspensionStartDay() {
		return getStringArrayClone(aryTxtSuspensionStartDay);
	}
	
	/**
	 * @param aryTxtSuspensionEndYear セットする aryTxtSuspensionEndYear
	 */
	public void setAryTxtSuspensionEndYear(String[] aryTxtSuspensionEndYear) {
		this.aryTxtSuspensionEndYear = getStringArrayClone(aryTxtSuspensionEndYear);
	}
	
	/**
	 * @return aryTxtSuspensionEndYear
	 */
	public String[] getAryTxtSuspensionEndYear() {
		return getStringArrayClone(aryTxtSuspensionEndYear);
	}
	
	/**
	 * @param aryTxtSuspensionEndMonth セットする aryTxtSuspensionEndMonth
	 */
	public void setAryTxtSuspensionEndMonth(String[] aryTxtSuspensionEndMonth) {
		this.aryTxtSuspensionEndMonth = getStringArrayClone(aryTxtSuspensionEndMonth);
	}
	
	/**
	 * @return aryTxtSuspensionEndMonth
	 */
	public String[] getAryTxtSuspensionEndMonth() {
		return getStringArrayClone(aryTxtSuspensionEndMonth);
	}
	
	/**
	 * @param aryTxtSuspensionEndDay セットする aryTxtSuspensionEndDay
	 */
	public void setAryTxtSuspensionEndDay(String[] aryTxtSuspensionEndDay) {
		this.aryTxtSuspensionEndDay = getStringArrayClone(aryTxtSuspensionEndDay);
	}
	
	/**
	 * @return aryTxtSuspensionEndDay
	 */
	public String[] getAryTxtSuspensionEndDay() {
		return getStringArrayClone(aryTxtSuspensionEndDay);
	}
	
	/**
	 * @param aryTxtSuspensionScheduleEndYear セットする aryTxtSuspensionScheduleEndYear
	 */
	public void setAryTxtSuspensionScheduleEndYear(String[] aryTxtSuspensionScheduleEndYear) {
		this.aryTxtSuspensionScheduleEndYear = getStringArrayClone(aryTxtSuspensionScheduleEndYear);
	}
	
	/**
	 * @return aryTxtSuspensionScheduleEndYear
	 */
	public String[] getAryTxtSuspensionScheduleEndYear() {
		return getStringArrayClone(aryTxtSuspensionScheduleEndYear);
	}
	
	/**
	 * @param aryTxtSuspensionScheduleEndMonth セットする aryTxtSuspensionScheduleEndMonth
	 */
	public void setAryTxtSuspensionScheduleEndMonth(String[] aryTxtSuspensionScheduleEndMonth) {
		this.aryTxtSuspensionScheduleEndMonth = getStringArrayClone(aryTxtSuspensionScheduleEndMonth);
	}
	
	/**
	 * @return aryTxtSuspensionScheduleEndMonth
	 */
	public String[] getAryTxtSuspensionScheduleEndMonth() {
		return getStringArrayClone(aryTxtSuspensionScheduleEndMonth);
	}
	
	/**
	 * @param aryTxtSuspensionScheduleEndDay セットする aryTxtSuspensionScheduleEndDay
	 */
	public void setAryTxtSuspensionScheduleEndDay(String[] aryTxtSuspensionScheduleEndDay) {
		this.aryTxtSuspensionScheduleEndDay = getStringArrayClone(aryTxtSuspensionScheduleEndDay);
	}
	
	/**
	 * @return aryTxtSuspensionScheduleEndDay
	 */
	public String[] getAryTxtSuspensionScheduleEndDay() {
		return getStringArrayClone(aryTxtSuspensionScheduleEndDay);
	}
	
	/**
	 * @param aryTxtSuspensionReason セットする aryTxtSuspensionReason
	 */
	public void setAryTxtSuspensionReason(String[] aryTxtSuspensionReason) {
		this.aryTxtSuspensionReason = getStringArrayClone(aryTxtSuspensionReason);
	}
	
	/**
	 * @return aryTxtSuspensionReason
	 */
	public String[] getAryTxtSuspensionReason() {
		return getStringArrayClone(aryTxtSuspensionReason);
	}
	
}
