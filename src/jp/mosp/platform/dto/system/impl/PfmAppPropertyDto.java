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
package jp.mosp.platform.dto.system.impl;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.AppPropertyDtoInterface;

/**
 * アプリケーション設定情報。<br>
 */
public class PfmAppPropertyDto extends BaseDto implements AppPropertyDtoInterface {
	
	private static final long	serialVersionUID	= 6141556062230711082L;
	
	/**
	 * レコード識別ID。<br>
	 */
	private long				pfmAppPropertyId;
	
	/**
	 * システム管理コード。<br>
	 */
	private String				appKey;
	
	/**
	 * システム管理文字列。<br>
	 */
	private String				appValue;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmAppPropertyDto() {
		// 処理なし
	}
	
	@Override
	public long getPfmAppPropertyId() {
		return pfmAppPropertyId;
	}
	
	@Override
	public void setPfmAppPropertyId(long pfmAppPropertyId) {
		this.pfmAppPropertyId = pfmAppPropertyId;
	}
	
	@Override
	public String getAppKey() {
		return appKey;
	}
	
	@Override
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	@Override
	public String getAppValue() {
		return appValue;
	}
	
	@Override
	public void setAppValue(String appValue) {
		this.appValue = appValue;
	}
	
	@Override
	public long getRecordId() {
		return getPfmAppPropertyId();
	}
	
}
