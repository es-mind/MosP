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
package jp.mosp.platform.dto.system;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.base.RecordDtoInterface;

/**
 * アプリケーション設定情報インターフェース。<br>
 */
public interface AppPropertyDtoInterface extends BaseDtoInterface, RecordDtoInterface {
	
	/**
	 * @return レコード識別ID
	 */
	long getPfmAppPropertyId();
	
	/**
	 * @param pfmAppPropertyId レコード識別ID
	 */
	void setPfmAppPropertyId(long pfmAppPropertyId);
	
	/**
	 * @return アプリケーション設定キー
	 */
	String getAppKey();
	
	/**
	 * @param appKey アプリケーション設定キー
	 */
	void setAppKey(String appKey);
	
	/**
	 * @return アプリケーション設定値
	 */
	String getAppValue();
	
	/**
	 * @param appValue アプリケーション設定値
	 */
	void setAppValue(String appValue);
	
}
