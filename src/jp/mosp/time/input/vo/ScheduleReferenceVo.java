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
package jp.mosp.time.input.vo;

import jp.mosp.time.base.AttendanceListBaseVo;

/**
 * 予定確認の情報を格納する。
 */
public class ScheduleReferenceVo extends AttendanceListBaseVo {
	
	private static final long	serialVersionUID	= 1481544353567093878L;
	
	/**
	 * 現在の予定表。
	 */
	private String				lblApplicationSchedule;
	
	/**
	 * 始業打刻時間表示要否。
	 */
	private boolean				isLblStartRecordTime;
	
	/**
	 * 終業打刻時間表示要否。
	 */
	private boolean				isLblEndRecordTime;
	
	
	/**
	 * @return lblApplicationSchedule
	 */
	public String getLblApplicationSchedule() {
		return lblApplicationSchedule;
	}
	
	/**
	 * @param lblApplicationSchedule セットする lblApplicationSchedule
	 */
	public void setLblApplicationSchedule(String lblApplicationSchedule) {
		this.lblApplicationSchedule = lblApplicationSchedule;
	}
	
	/**
	 * @return isLblStartRecordTime
	 */
	public boolean isLblStartRecordTime() {
		return isLblStartRecordTime;
	}
	
	/**
	 * @param isLblStartRecordTime セットする isLblStartRecordTime
	 */
	public void setLblStartRecordTime(boolean isLblStartRecordTime) {
		this.isLblStartRecordTime = isLblStartRecordTime;
	}
	
	/**
	 * @return isLblEndRecordTime
	 */
	public boolean isLblEndRecordTime() {
		return isLblEndRecordTime;
	}
	
	/**
	 * @param isLblEndRecordTime セットする isLblEndRecordTime
	 */
	public void setLblEndRecordTime(boolean isLblEndRecordTime) {
		this.isLblEndRecordTime = isLblEndRecordTime;
	}
	
}
