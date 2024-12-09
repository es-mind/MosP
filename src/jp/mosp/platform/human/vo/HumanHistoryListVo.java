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
package jp.mosp.platform.human.vo;

import java.util.Date;
import java.util.LinkedHashMap;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 人事汎用情報履歴一覧画面の情報を格納する。
 */
public class HumanHistoryListVo extends PlatformHumanVo {
	
	private static final long									serialVersionUID	= -6918193191713351429L;
	
	private String[]											aryActiveteDate;
	
	private Date												activeDate;
	
	// 削除：履歴が一つだけしかない時の削除要否フラグ
	private Boolean												jsIsLastHistory;
	
	private LinkedHashMap<String, LinkedHashMap<String, Long>>	aryHistoryRecordIdMap;
	
	
	/**
	 * VOの初期設定を行う。<br>
	 * <br>
	 */
	public HumanHistoryListVo() {
		super();
		aryHistoryRecordIdMap = new LinkedHashMap<String, LinkedHashMap<String, Long>>();
		
	}
	
	/**
	 * @param aryActiveteDate セットする aryActiveteDate
	 */
	public void setAryActiveteDate(String[] aryActiveteDate) {
		this.aryActiveteDate = getStringArrayClone(aryActiveteDate);
	}
	
	/**
	 * @return aryActiveteDate
	 */
	public String[] getAryActiveteDate() {
		return getStringArrayClone(aryActiveteDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryActiveteDate
	 */
	public String getAryActiveteDate(int idx) {
		return aryActiveteDate[idx];
	}
	
	/**
	 * @param activeDate セットする activeDate
	 */
	public void setActiveDate(Date activeDate) {
		this.activeDate = getDateClone(activeDate);
	}
	
	/**
	 * @return activeDate
	 */
	public Date getActiveDate() {
		return getDateClone(activeDate);
	}
	
	/**
	 * @param jsIsLastHistory セットする jsIsLastHistory
	 */
	public void setJsIsLastHistory(boolean jsIsLastHistory) {
		this.jsIsLastHistory = jsIsLastHistory;
	}
	
	/**
	 * @return jsIsLastHistory
	 */
	public boolean getJsIsLastHistory() {
		return jsIsLastHistory;
	}
	
	/**
	 * レコード識別IDマップを設定<br>
	 * @param aryHistoryRecordIdMap レコード識別IDマップ
	 */
	public void setAryHistoryRecordIdMap(LinkedHashMap<String, LinkedHashMap<String, Long>> aryHistoryRecordIdMap) {
		this.aryHistoryRecordIdMap.putAll(aryHistoryRecordIdMap);
	}
	
	/**
	 * レコード識別IDを取得<br>
	 * @param itemKey 人事汎用管理項目 
	 * @return 識別ID 
	 */
	public LinkedHashMap<String, Long> getAryHistoryRecordIdMap(String itemKey) {
		return aryHistoryRecordIdMap.get(itemKey);
	}
	
	/**
	 * 識別IDマップを取得<br>
	 * @return 識別IDマップ 
	 */
	public LinkedHashMap<String, LinkedHashMap<String, Long>> getAryHistoryRecordIdMap() {
		return aryHistoryRecordIdMap;
	}
	
}
