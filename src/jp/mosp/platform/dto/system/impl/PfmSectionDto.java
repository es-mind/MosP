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
import jp.mosp.platform.dto.system.SectionDtoInterface;

/**
 * 所属マスタDTO。
 */
public class PfmSectionDto extends BaseDto implements SectionDtoInterface {
	
	private static final long	serialVersionUID	= -4297896647400787311L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmSectionId;
	
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 所属名称。
	 */
	private String				sectionName;
	
	/**
	 * 所属略称。
	 */
	private String				sectionAbbr;
	
	/**
	 * 所属表示名称。
	 */
	private String				sectionDisplay;
	
	/**
	 * 階層経路。
	 */
	private String				classRoute;
	
	/**
	 * 閉鎖フラグ。
	 */
	private int					closeFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmSectionDto() {
		// 処理無し
	}
	
	@Override
	public long getPfmSectionId() {
		return pfmSectionId;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getSectionName() {
		return sectionName;
	}
	
	@Override
	public String getSectionAbbr() {
		return sectionAbbr;
	}
	
	@Override
	public String getSectionDisplay() {
		return sectionDisplay;
	}
	
	@Override
	public String getClassRoute() {
		return classRoute;
	}
	
	@Override
	public int getCloseFlag() {
		return closeFlag;
	}
	
	@Override
	public void setPfmSectionId(long pfmSectionId) {
		this.pfmSectionId = pfmSectionId;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	@Override
	public void setSectionAbbr(String sectionAbbr) {
		this.sectionAbbr = sectionAbbr;
	}
	
	@Override
	public void setClassRoute(String classRoute) {
		this.classRoute = classRoute;
	}
	
	@Override
	public void setSectionDisplay(String sectionDisplay) {
		this.sectionDisplay = sectionDisplay;
	}
	
	@Override
	public void setCloseFlag(int closeFlag) {
		this.closeFlag = closeFlag;
	}
	
	@Override
	public int getInactivateFlag() {
		return getCloseFlag();
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		setCloseFlag(inactivateFlag);
	}
	
}
