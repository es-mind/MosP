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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 勤怠設定検索インターフェース。<br>
 */
public interface TimeSettingSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 条件による検索。
	 * 検索条件から勤怠設定リストを取得。
	 * @return 勤怠設定リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<TimeSettingDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param workSettingCode セットする 勤怠設定コード。
	 */
	void setWorkSettingCode(String workSettingCode);
	
	/**
	 * @param workSettingName セットする 勤怠設定名称。
	 */
	void setWorkSettingName(String workSettingName);
	
	/**
	 * @param workSettingAbbr セットする 勤怠設定略称。
	 */
	void setWorkSettingAbbr(String workSettingAbbr);
	
	/**
	 * @param cutoffCode セットする 締日コード。
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
