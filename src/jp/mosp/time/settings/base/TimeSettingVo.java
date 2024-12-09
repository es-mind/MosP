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
package jp.mosp.time.settings.base;

import jp.mosp.time.base.TimeVo;

/**
 * MosP勤怠管理におけるVOの基本となる情報を格納する。<br>
 */
public abstract class TimeSettingVo extends TimeVo {
	
	private static final long	serialVersionUID	= -7228645022886195694L;
	
	/**
	 * 編集無効フラグ。<br>
	 */
	private String				pltEditInactivate;
	
	/**
	 * 検索無効フラグ。<br>
	 */
	private String				pltSearchInactivate;
	
	/**
	 * 検索無効フラグ。<br>
	 */
	private String				pltUpdateInactivate;
	
	/**
	 * レコードID。<br>
	 * 履歴編集対象のレコードIDを保持しておく。<br>
	 */
	private long				recordId;
	
	/**
	 * 次履歴有効日(編集領域における履歴移動機能)。<br>
	 */
	private String				lblNextActivateDate;
	
	/**
	 * 前履歴有効日(編集領域における履歴移動機能)。<br>
	 */
	private String				lblBackActivateDate;
	
	/**
	 * 履歴総件数(編集領域における履歴移動機能)。<br>
	 */
	private int					countHistory;
	
	/**
	 * 編集中履歴順番(編集領域における履歴移動機能)。<br>
	 * 最も新しい履歴が、1番となる。<br>
	 */
	private int					currentHistory;
	
	/**
	 * 一覧項目(レコード識別ID)。<br>
	 */
	private long[]				aryCkbRecordId;
	
	/**
	 * 一覧項目(有効日)。<br>
	 */
	private String[]			aryLblActivateDate;
	
	/**
	 * 一覧項目(無効フラグ)。<br>
	 */
	private String[]			aryLblInactivate;
	
	
	/**
	 * @return recordId
	 */
	public long getRecordId() {
		return recordId;
	}
	
	/**
	 * @param recordId セットする recordId
	 */
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	
	/**
	 * @return lblNextActivateDate
	 */
	public String getLblNextActivateDate() {
		return lblNextActivateDate;
	}
	
	/**
	 * @return lblBackActivateDate
	 */
	public String getLblBackActivateDate() {
		return lblBackActivateDate;
	}
	
	/**
	 * @return countHistory
	 */
	public int getCountHistory() {
		return countHistory;
	}
	
	/**
	 * @return currentHistory
	 */
	public int getCurrentHistory() {
		return currentHistory;
	}
	
	/**
	 * @param lblNextActivateDate セットする lblNextActivateDate
	 */
	public void setLblNextActivateDate(String lblNextActivateDate) {
		this.lblNextActivateDate = lblNextActivateDate;
	}
	
	/**
	 * @param lblBackActivateDate セットする lblBackActivateDate
	 */
	public void setLblBackActivateDate(String lblBackActivateDate) {
		this.lblBackActivateDate = lblBackActivateDate;
	}
	
	/**
	 * @param countHistory セットする countHistory
	 */
	public void setCountHistory(int countHistory) {
		this.countHistory = countHistory;
	}
	
	/**
	 * @param currentHistory セットする currentHistory
	 */
	public void setCurrentHistory(int currentHistory) {
		this.currentHistory = currentHistory;
	}
	
	/**
	 * @return pltSearchInactivate
	 */
	public String getPltSearchInactivate() {
		return pltSearchInactivate;
	}
	
	/**
	 * @param pltSearchInactivate セットする pltSearchInactivate
	 */
	public void setPltSearchInactivate(String pltSearchInactivate) {
		this.pltSearchInactivate = pltSearchInactivate;
	}
	
	/**
	 * @return pltUpdateInactivate
	 */
	public String getPltUpdateInactivate() {
		return pltUpdateInactivate;
	}
	
	/**
	 * @param pltUpdateInactivate セットする pltUpdateInactivate
	 */
	public void setPltUpdateInactivate(String pltUpdateInactivate) {
		this.pltUpdateInactivate = pltUpdateInactivate;
	}
	
	/**
	 * @return pltEditInactivate
	 */
	public String getPltEditInactivate() {
		return pltEditInactivate;
	}
	
	/**
	 * @param pltEditInactivate セットする pltEditInactivate
	 */
	public void setPltEditInactivate(String pltEditInactivate) {
		this.pltEditInactivate = pltEditInactivate;
	}
	
	/**
	 * @return aryCkbRecordId
	 */
	public long[] getAryCkbRecordId() {
		return getLongArrayClone(aryCkbRecordId);
	}
	
	/**
	 * @param aryCkbRecordId セットする aryCkbRecordId
	 */
	public void setAryCkbRecordId(long[] aryCkbRecordId) {
		this.aryCkbRecordId = getLongArrayClone(aryCkbRecordId);
	}
	
	/**
	 * @return aryLblActivateDate
	 */
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblActivateDate
	 */
	public String getAryLblActivateDate(int idx) {
		return aryLblActivateDate[idx];
	}
	
	/**
	 * @param aryLblActivateDate セットする aryLblActivateDate
	 */
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @return aryLblInactivate
	 */
	public String[] getAryLblInactivate() {
		return getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblInactivate
	 */
	public String getAryLblInactivate(int idx) {
		return aryLblInactivate[idx];
	}
	
	/**
	 * @param aryLblInactivate セットする aryLblInactivate
	 */
	public void setAryLblInactivate(String[] aryLblInactivate) {
		this.aryLblInactivate = getStringArrayClone(aryLblInactivate);
	}
	
}
