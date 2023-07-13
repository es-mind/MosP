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
/**
 * 
 */
package jp.mosp.time.dto.settings;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.base.ActivateDtoInterface;

/**
 * 限度基準マスタDTOインターフェース
 */
public interface LimitStandardDtoInterface extends BaseDtoInterface, ActivateDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmLimitStandardId();
	
	/**
	 * @return 勤怠設定コード。
	 */
	String getWorkSettingCode();
	
	/**
	 * @return 期間。
	 */
	String getTerm();
	
	/**
	 * @return 時間外限度時間。
	 */
	int getLimitTime();
	
	/**
	 * @return 時間外注意時間。
	 */
	int getAttentionTime();
	
	/**
	 * @return 時間外警告時間。
	 */
	int getWarningTime();
	
	/**
	 * @param tmmLimitStandardId セットする レコード識別ID。
	 */
	void setTmmLimitStandardId(long tmmLimitStandardId);
	
	/**
	 * @param workSettingCode セットする 勤怠設定コード。
	 */
	void setWorkSettingCode(String workSettingCode);
	
	/**
	 * @param term セットする 期間。
	 */
	void setTerm(String term);
	
	/**
	 * @param limitTime セットする 時間外限度時間。
	 */
	void setLimitTime(int limitTime);
	
	/**
	 * @param attentionTime セットする 時間外注意時間。
	 */
	void setAttentionTime(int attentionTime);
	
	/**
	 * @param warningTime セットする 時間外警告時間。
	 */
	void setWarningTime(int warningTime);
}
