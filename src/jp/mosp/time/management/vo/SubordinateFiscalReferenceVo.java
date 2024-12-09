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
package jp.mosp.time.management.vo;

import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.time.base.AttendanceTotalVo;
import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 統計情報詳細画面。<br>
 * 年度の勤怠一覧下部を表示する。<br>
 */
public class SubordinateFiscalReferenceVo extends TimeSettingVo {
	
	private static final long		serialVersionUID	= -1364585873352380874L;
	
	// VOが13個入る
	private List<AttendanceTotalVo>	voList;
	
	/**
	 * 前社員コード。<br>
	 */
	private String					lblPrevEmployeeCode;
	
	/**
	 * 次社員コード。<br>
	 */
	private String					lblNextEmployeeCode;
	
	/**
	 * 前個人ID。
	 */
	private String					prevPersonalId;
	
	/**
	 * 次個人ID。
	 */
	private String					nextPersonalId;
	
	/**
	 * 遷移DTO配列。<br>
	 */
	private BaseDtoInterface[]		rollArray;
	
	/**
	 * 対象年度
	 */
	private int						fiscalYear;
	
	
	/**
	 * @return voList
	 */
	public List<AttendanceTotalVo> getVoList() {
		return voList;
	}
	
	/**
	 * @param voList セットする voList
	 */
	public void setVoList(List<AttendanceTotalVo> voList) {
		this.voList = voList;
	}
	
	/**
	 * @return prevPersonalId
	 */
	public String getPrevPersonalId() {
		return prevPersonalId;
	}
	
	/**
	 * @param prevPersonalId セットする prevPersonalId
	 */
	public void setPrevPersonalId(String prevPersonalId) {
		this.prevPersonalId = prevPersonalId;
	}
	
	/**
	 * @return nextPersonalId
	 */
	public String getNextPersonalId() {
		return nextPersonalId;
	}
	
	/**
	 * @param nextPersonalId セットする nextPersonalId
	 */
	public void setNextPersonalId(String nextPersonalId) {
		this.nextPersonalId = nextPersonalId;
	}
	
	/**
	 * @return lblPrevEmployeeCode
	 */
	public String getLblPrevEmployeeCode() {
		return lblPrevEmployeeCode;
	}
	
	/**
	 * @param lblPrevEmployeeCode セットする lblPrevEmployeeCode
	 */
	public void setLblPrevEmployeeCode(String lblPrevEmployeeCode) {
		this.lblPrevEmployeeCode = lblPrevEmployeeCode;
	}
	
	/**
	 * @return lblNextEmployeeCode
	 */
	public String getLblNextEmployeeCode() {
		return lblNextEmployeeCode;
	}
	
	/**
	 * @param lblNextEmployeeCode セットする lblNextEmployeeCode
	 */
	public void setLblNextEmployeeCode(String lblNextEmployeeCode) {
		this.lblNextEmployeeCode = lblNextEmployeeCode;
	}
	
	/**
	 * @return rollArray
	 */
	public BaseDtoInterface[] getRollArray() {
		return getDtoArrayClone(rollArray);
	}
	
	/**
	 * @param rollArray セットする rollArray
	 */
	public void setRollArray(BaseDtoInterface[] rollArray) {
		this.rollArray = getDtoArrayClone(rollArray);
	}

	/**
	 * @return fiscalYear
	 */
	public int getFiscalYear() {
		return fiscalYear;
	}

	/**
	 * @param fiscalYear セットする fiscalYear
	 */
	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
}
