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
package jp.mosp.platform.dto.human.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;

/**
 * 人事汎用通常情報DTO。
 */
public class PfaHumanNormalDto extends BaseDto implements HumanNormalDtoInterface {
	
	private static final long	serialVersionUID	= -8764633127991891059L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaHumanNormalId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 人事項目区分。
	 */
	private String				humanItemType;
	
	/**
	 * 人事項目値。
	 */
	private String				humanItemValue;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanNormalDto() {
		// 初期化
	}
	
	@Override
	public long getPfaHumanNormalId() {
		return pfaHumanNormalId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public String getHumanItemType() {
		return humanItemType;
	}
	
	@Override
	public String getHumanItemValue() {
		return humanItemValue;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfaHumanNormalId(long pfaHumanNormalId) {
		this.pfaHumanNormalId = pfaHumanNormalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setHumanItemType(String humanItemType) {
		this.humanItemType = humanItemType;
	}
	
	@Override
	public void setHumanItemValue(String humanItemValue) {
		this.humanItemValue = humanItemValue;
	}
	
	@Override
	public Date getActivateDate() {
		// 処理なし
		return null;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		// 処理なし
	}
	
}
