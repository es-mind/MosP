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
/**
 * 
 */
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;

/**
 * 勤怠修正情報DTO。<br>
 */
public class TmdAttendanceCorrectionDto extends BaseDto implements AttendanceCorrectionDtoInterface {
	
	private static final long	serialVersionUID	= -4790806931089412636L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdAttendanceCorrectionId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 勤務日。
	 */
	private Date				workDate;
	
	/**
	 * 勤務回数。
	 */
	private int					works;
	
	/**
	 * 修正番号。
	 */
	private int					correctionTimes;
	
	/**
	 * 修正日時。
	 */
	private Date				correctionDate;
	
	/**
	 * 修正個人ID。
	 */
	private String				correctionPersonalId;
	
	/**
	 * 修正箇所。
	 */
	private String				correctionType;
	
	/**
	 * 修正前。
	 */
	private String				correctionBefore;
	
	/**
	 * 修正後。
	 */
	private String				correctionAfter;
	
	/**
	 * 修正理由。
	 */
	private String				correctionReason;
	
	
	@Override
	public Date getCorrectionDate() {
		return getDateClone(correctionDate);
	}
	
	@Override
	public String getCorrectionPersonalId() {
		return correctionPersonalId;
	}
	
	@Override
	public String getCorrectionReason() {
		return correctionReason;
	}
	
	@Override
	public int getCorrectionTimes() {
		return correctionTimes;
	}
	
	@Override
	public String getCorrectionType() {
		return correctionType;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public long getTmdAttendanceCorrectionId() {
		return tmdAttendanceCorrectionId;
	}
	
	@Override
	public Date getWorkDate() {
		return getDateClone(workDate);
	}
	
	@Override
	public int getWorks() {
		return works;
	}
	
	@Override
	public void setCorrectionDate(Date correctionDate) {
		this.correctionDate = getDateClone(correctionDate);
	}
	
	@Override
	public void setCorrectionPersonalId(String correctionPersonalId) {
		this.correctionPersonalId = correctionPersonalId;
	}
	
	@Override
	public void setCorrectionReason(String correctionReason) {
		this.correctionReason = correctionReason;
	}
	
	@Override
	public void setCorrectionTimes(int correctionTimes) {
		this.correctionTimes = correctionTimes;
	}
	
	@Override
	public void setCorrectionType(String correctionType) {
		this.correctionType = correctionType;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setTmdAttendanceCorrectionId(long tmdAttendanceCorrectionId) {
		this.tmdAttendanceCorrectionId = tmdAttendanceCorrectionId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
	@Override
	public void setWorks(int works) {
		this.works = works;
	}
	
	@Override
	public String getCorrectionAfter() {
		return correctionAfter;
	}
	
	@Override
	public String getCorrectionBefore() {
		return correctionBefore;
	}
	
	@Override
	public void setCorrectionAfter(String correctionAfter) {
		this.correctionAfter = correctionAfter;
	}
	
	@Override
	public void setCorrectionBefore(String correctionBefore) {
		this.correctionBefore = correctionBefore;
	}
	
}
