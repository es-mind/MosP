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
import java.util.HashMap;
import java.util.Map;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 人事汎用バイナリ履歴一覧情報登録画面の情報を格納する。
 */
public class HumanBinaryHistoryListVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= -1500283560491545902L;
	
	private String[]			aryActiveteDate;
	private String[]			aryPfaHumanBinaryHistoryId;
	private String[]			aryFileType;
	private String[]			aryFileName;
	private String[]			aryFileRemark;
	
	private Date				activeDate;
	
	// 削除：履歴が一つだけしかない時の削除要否フラグ
	private Boolean				jsIsLastHistory;
	
	private Map<Date, Long>		hidRecordIdMap;
	
	
	/**
	 * VOの初期設定を行う。<br>
	 * <br>
	 */
	public HumanBinaryHistoryListVo() {
		super();
		// ポータルパラメータマップ、ポータルJSPリストを初期化
		hidRecordIdMap = new HashMap<Date, Long>();
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
	 * @param aryPfaHumanBinaryHistoryId セットする aryPfaHumanBinaryHistoryId
	 */
	public void setAryPfaHumanBinaryHistoryId(String[] aryPfaHumanBinaryHistoryId) {
		this.aryPfaHumanBinaryHistoryId = getStringArrayClone(aryPfaHumanBinaryHistoryId);
	}
	
	/**
	 * @return aryPfaHumanBinaryHistoryId
	 */
	public String[] getAryPfaHumanBinaryHistoryId() {
		return getStringArrayClone(aryPfaHumanBinaryHistoryId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPfaHumanBinaryHistoryId
	 */
	public String getAryPfaHumanBinaryHistoryId(int idx) {
		return aryPfaHumanBinaryHistoryId[idx];
	}
	
	/**
	 * @param aryFileType セットする aryFileType
	 */
	public void setAryFileType(String[] aryFileType) {
		this.aryFileType = getStringArrayClone(aryFileType);
	}
	
	/**
	 * @return aryFileType
	 */
	public String[] getAryFileType() {
		return getStringArrayClone(aryFileType);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryFileType
	 */
	public String getAryFileType(int idx) {
		return aryFileType[idx];
	}
	
	/**
	 * @param aryFileName セットする aryFileName
	 */
	public void setAryFileName(String[] aryFileName) {
		this.aryFileName = getStringArrayClone(aryFileName);
	}
	
	/**
	 * @return aryFileName
	 */
	public String[] getAryFileName() {
		return getStringArrayClone(aryFileName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryFileName
	 */
	public String getAryFileName(int idx) {
		return aryFileName[idx];
	}
	
	/**
	 * @param aryFileRemark セットする aryFileRemark
	 */
	public void setAryFileRemark(String[] aryFileRemark) {
		this.aryFileRemark = getStringArrayClone(aryFileRemark);
	}
	
	/**
	 * @return aryFileRemark
	 */
	public String[] getAryFileRemark() {
		return getStringArrayClone(aryFileRemark);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryFileRemark
	 */
	public String getAryFileRemark(int idx) {
		return aryFileRemark[idx];
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
	 * @return hidRecordIdMap
	 */
	public Map<Date, Long> getHidRecordIdMap() {
		return hidRecordIdMap;
	}
	
	/**
	 * マップ追加
	 * @param date 有効日
	 * @param id レコード識別ID
	 */
	public void putHidRecordIdMap(Date date, Long id) {
		hidRecordIdMap.put(date, id);
	}
	
	/**
	 * @param hidRecordIdMap セットする hidRecordIdMap
	 */
	public void setHidRecordIdMap(Map<Date, Long> hidRecordIdMap) {
		this.hidRecordIdMap = hidRecordIdMap;
	}
	
}
