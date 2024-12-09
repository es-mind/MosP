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

import java.util.Date;

/**
 * 勤怠集計関連の共通のフィールドを提供するVO。<br>
 */
public abstract class TotalTimeBaseVo extends TimeVo {
	
	private static final long	serialVersionUID	= -7890447286436418565L;
	
	/**
	 * 締日コード。
	 */
	private String				cutoffCode;
	
	/**
	 * 対象年。
	 */
	private int					targetYear;
	
	/**
	 * 対象月。
	 */
	private int					targetMonth;
	
	/**
	 * 締期間における初日。
	 */
	private Date				cutoffFirstDate;
	
	/**
	 * 締期間における最終日。
	 */
	private Date				cutoffLastDate;
	
	/**
	 * 締期間における対象日。
	 */
	private Date				cutoffTermTargetDate;
	
	/**
	 * 集計日。
	 */
	private Date				calculationDate;
	
	/**
	 * 表示用締日名称。
	 */
	private String				lblCutoffName;
	
	/**
	 * 表示用選択年月。
	 */
	private String				lblYearMonth;
	
	/**
	 * 表示用締期間初日。
	 */
	private String				lblCutoffFirstDate;
	
	/**
	 * 表示用締期間最終日。
	 */
	private String				lblCutoffLastDate;
	
	
	/**
	 * @param cutoffCode セットする cutoffCode
	 */
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	/**
	 * @return cutoffCode
	 */
	public String getCutoffCode() {
		return cutoffCode;
	}
	
	/**
	 * @param targetYear セットする targetYear
	 */
	public void setTargetYear(int targetYear) {
		this.targetYear = targetYear;
	}
	
	/**
	 * @return targetYear
	 */
	public int getTargetYear() {
		return targetYear;
	}
	
	/**
	 * @param targetMonth セットする targetMonth
	 */
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
	
	/**
	 * @return targetMonth
	 */
	public int getTargetMonth() {
		return targetMonth;
	}
	
	/**
	 * @param cutoffFirstDate セットする cutoffFirstDate
	 */
	public void setCutoffFirstDate(Date cutoffFirstDate) {
		this.cutoffFirstDate = getDateClone(cutoffFirstDate);
	}
	
	/**
	 * @return cutoffFirstDate
	 */
	public Date getCutoffFirstDate() {
		return getDateClone(cutoffFirstDate);
	}
	
	/**
	 * @param cutoffLastDate セットする cutoffLastDate
	 */
	public void setCutoffLastDate(Date cutoffLastDate) {
		this.cutoffLastDate = getDateClone(cutoffLastDate);
	}
	
	/**
	 * @return cutoffLastDate
	 */
	public Date getCutoffLastDate() {
		return getDateClone(cutoffLastDate);
	}
	
	/**
	 * @param cutoffTermTargetDate セットする cutoffTermTargetDate
	 */
	public void setCutoffTermTargetDate(Date cutoffTermTargetDate) {
		this.cutoffTermTargetDate = getDateClone(cutoffTermTargetDate);
	}
	
	/**
	 * @return cutoffTermTargetDate
	 */
	public Date getCutoffTermTargetDate() {
		return getDateClone(cutoffTermTargetDate);
	}
	
	/**
	 * @param calculationDate セットする calculationDate
	 */
	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = getDateClone(calculationDate);
	}
	
	/**
	 * @return calculationDate
	 */
	public Date getCalculationDate() {
		return getDateClone(calculationDate);
	}
	
	/**
	 * @param lblCutoffName セットする lblCutoffName
	 */
	public void setLblCutoffName(String lblCutoffName) {
		this.lblCutoffName = lblCutoffName;
	}
	
	/**
	 * @return lblCutoffName
	 */
	public String getLblCutoffName() {
		return lblCutoffName;
	}
	
	/**
	 * @param lblYearMonth セットする lblYearMonth
	 */
	public void setLblYearMonth(String lblYearMonth) {
		this.lblYearMonth = lblYearMonth;
	}
	
	/**
	 * @return lblYearMonth
	 */
	public String getLblYearMonth() {
		return lblYearMonth;
	}
	
	/**
	 * @param lblCutoffFirstDate セットする lblCutoffFirstDate
	 */
	public void setLblCutoffFirstDate(String lblCutoffFirstDate) {
		this.lblCutoffFirstDate = lblCutoffFirstDate;
	}
	
	/**
	 * @return lblCutoffFirstDate
	 */
	public String getLblCutoffFirstDate() {
		return lblCutoffFirstDate;
	}
	
	/**
	 * @param lblCutoffLastDate セットする lblCutoffLastDate
	 */
	public void setLblCutoffLastDate(String lblCutoffLastDate) {
		this.lblCutoffLastDate = lblCutoffLastDate;
	}
	
	/**
	 * @return lblCutoffLastDate
	 */
	public String getLblCutoffLastDate() {
		return lblCutoffLastDate;
	}
}
