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
package jp.mosp.platform.dto.system.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.PositionDtoInterface;

/**
 * 職位マスタDTO。
 */
public class PfmPositionDto extends BaseDto implements PositionDtoInterface {
	
	private static final long	serialVersionUID	= -2855032583484978407L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmPositionId;
	/**
	 * 職位コード。
	 */
	private String				positionCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 職位名称。
	 */
	private String				positionName;
	/**
	 * 職位略称。
	 */
	private String				positionAbbr;
	/**
	 * 等級。
	 */
	private int					positionGrade;
	/**
	 * 号数。
	 */
	private int					positionLevel;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmPositionDto() {
		// 処理無し
	}
	
	@Override
	public long getPfmPositionId() {
		return pfmPositionId;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getPositionName() {
		return positionName;
	}
	
	@Override
	public String getPositionAbbr() {
		return positionAbbr;
	}
	
	@Override
	public int getPositionGrade() {
		return positionGrade;
	}
	
	@Override
	public int getPositionLevel() {
		return positionLevel;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfmPositionId(long pfmPositionId) {
		this.pfmPositionId = pfmPositionId;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	@Override
	public void setPositionAbbr(String positionAbbr) {
		this.positionAbbr = positionAbbr;
	}
	
	@Override
	public void setPositionGrade(int positionGrade) {
		this.positionGrade = positionGrade;
	}
	
	@Override
	public void setPositionLevel(int positionLevel) {
		this.positionLevel = positionLevel;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
