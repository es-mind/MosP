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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.CutoffDtoInterface;

/**
 * 締日情報。<br>
 */
public class TmmCutoffDto extends BaseDto implements CutoffDtoInterface {
	
	private static final long	serialVersionUID	= 7366697752121223840L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmCutoffId;
	/**
	 * 締日コード。
	 */
	private String				cutoffCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 締日名称。
	 */
	private String				cutoffName;
	/**
	 * 締日略称。
	 */
	private String				cutoffAbbr;
	/**
	 * 締日。
	 */
	private int					cutoffDate;
	/**
	 * 締区分。
	 */
	private String				cutoffType;
	/**
	 * 未承認仮締。
	 */
	private int					noApproval;
	
	/**
	 * 自己月締。
	 */
	private int					selfTightening;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmCutoffDto() {
		// 処理無し
	}
	
	@Override
	public long getTmmCutoffId() {
		return tmmCutoffId;
	}
	
	@Override
	public String getCutoffCode() {
		return cutoffCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getCutoffName() {
		return cutoffName;
	}
	
	@Override
	public String getCutoffAbbr() {
		return cutoffAbbr;
	}
	
	@Override
	public int getCutoffDate() {
		return cutoffDate;
	}
	
	@Override
	public String getCutoffType() {
		return cutoffType;
	}
	
	@Override
	public int getNoApproval() {
		return noApproval;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setTmmCutoffId(long tmmCutoffId) {
		this.tmmCutoffId = tmmCutoffId;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
		
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setCutoffName(String cutoffName) {
		this.cutoffName = cutoffName;
	}
	
	@Override
	public void setCutoffAbbr(String cutoffAbbr) {
		this.cutoffAbbr = cutoffAbbr;
	}
	
	@Override
	public void setCutoffDate(int cutoffDate) {
		this.cutoffDate = cutoffDate;
	}
	
	@Override
	public void setCutoffType(String cutoffType) {
		this.cutoffType = cutoffType;
	}
	
	@Override
	public void setNoApproval(int noApproval) {
		this.noApproval = noApproval;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public int getSelfTightening() {
		return selfTightening;
	}
	
	@Override
	public void setSelfTightening(int selfTightening) {
		this.selfTightening = selfTightening;
	}
	
}
