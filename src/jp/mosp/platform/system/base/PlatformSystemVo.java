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
package jp.mosp.platform.system.base;

import jp.mosp.platform.base.PlatformVo;

/**
 * MosPプラットフォーム基本設定におけるVOの基本となる情報を格納する。<br>
 * <br>
 * 有効日(編集、検索、一括更新)の年月日を格納する。<br>
 * <br>
 * 無効フラグ(編集、検索、一括更新)を格納する。<br>
 * <br>
 * 編集領域における履歴移動機能の情報を格納する。<br>
 * <br>
 * 他、MosPプラットフォーム基本設定におけるVOの基本となる情報。<br>
 * <br>
 */
public abstract class PlatformSystemVo extends PlatformVo {
	
	private static final long	serialVersionUID	= 1175066640089086731L;
	
	/**
	 * 編集有効日(年)。<br>
	 */
	private String				txtEditActivateYear;
	
	/**
	 * 編集有効日(月)。<br>
	 */
	private String				txtEditActivateMonth;
	
	/**
	 * 編集有効日(日)。<br>
	 */
	private String				txtEditActivateDay;
	
	/**
	 * 検索有効日(年)。<br>
	 */
	private String				txtSearchActivateYear;
	
	/**
	 * 検索有効日(月)。<br>
	 */
	private String				txtSearchActivateMonth;
	
	/**
	 * 検索有効日(日)。<br>
	 */
	private String				txtSearchActivateDay;
	
	/**
	 * 一括更新有効日(年)。<br>
	 */
	private String				txtUpdateActivateYear;
	
	/**
	 * 一括更新有効日(月)。<br>
	 */
	private String				txtUpdateActivateMonth;
	
	/**
	 * 一括更新有効日(日)。<br>
	 */
	private String				txtUpdateActivateDay;
	
	/**
	 * 編集無効フラグ。<br>
	 */
	private String				pltEditInactivate;
	
	/**
	 * 検索無効フラグ。<br>
	 */
	private String				pltSearchInactivate;
	
	/**
	 * 一括更新無効フラグ。<br>
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
	 * @param txtEditActivateDay セットする txtEditActivateDay
	 */
	public void setTxtEditActivateDay(String txtEditActivateDay) {
		this.txtEditActivateDay = txtEditActivateDay;
	}
	
	/**
	 * @return txtEditActivateDay
	 */
	public String getTxtEditActivateDay() {
		return txtEditActivateDay;
	}
	
	/**
	 * @param txtEditActivateYear セットする txtEditActivateYear
	 */
	public void setTxtEditActivateYear(String txtEditActivateYear) {
		this.txtEditActivateYear = txtEditActivateYear;
	}
	
	/**
	 * @return txtEditActivateYear
	 */
	public String getTxtEditActivateYear() {
		return txtEditActivateYear;
	}
	
	/**
	 * @param txtEditActivateMonth セットする txtEditActivateMonth
	 */
	public void setTxtEditActivateMonth(String txtEditActivateMonth) {
		this.txtEditActivateMonth = txtEditActivateMonth;
	}
	
	/**
	 * @return txtEditActivateMonth
	 */
	public String getTxtEditActivateMonth() {
		return txtEditActivateMonth;
	}
	
	/**
	 * @param txtSearchActivateYear セットする txtSearchActivateYear
	 */
	public void setTxtSearchActivateYear(String txtSearchActivateYear) {
		this.txtSearchActivateYear = txtSearchActivateYear;
	}
	
	/**
	 * @return txtSearchActivateYear
	 */
	public String getTxtSearchActivateYear() {
		return txtSearchActivateYear;
	}
	
	/**
	 * @param txtSearchActivateMonth セットする txtSearchActivateMonth
	 */
	public void setTxtSearchActivateMonth(String txtSearchActivateMonth) {
		this.txtSearchActivateMonth = txtSearchActivateMonth;
	}
	
	/**
	 * @return txtSearchActivateMonth
	 */
	public String getTxtSearchActivateMonth() {
		return txtSearchActivateMonth;
	}
	
	/**
	 * @param txtSearchActivateDay セットする txtSearchActivateDay
	 */
	public void setTxtSearchActivateDay(String txtSearchActivateDay) {
		this.txtSearchActivateDay = txtSearchActivateDay;
	}
	
	/**
	 * @return txtSearchActivateDay
	 */
	public String getTxtSearchActivateDay() {
		return txtSearchActivateDay;
	}
	
	/**
	 * @param txtUpdateActivateYear セットする txtUpdateActivateYear
	 */
	public void setTxtUpdateActivateYear(String txtUpdateActivateYear) {
		this.txtUpdateActivateYear = txtUpdateActivateYear;
	}
	
	/**
	 * @return txtUpdateActivateYear
	 */
	public String getTxtUpdateActivateYear() {
		return txtUpdateActivateYear;
	}
	
	/**
	 * @param txtUpdateActivateMonth セットする txtUpdateActivateMonth
	 */
	public void setTxtUpdateActivateMonth(String txtUpdateActivateMonth) {
		this.txtUpdateActivateMonth = txtUpdateActivateMonth;
	}
	
	/**
	 * @return txtUpdateActivateMonth
	 */
	public String getTxtUpdateActivateMonth() {
		return txtUpdateActivateMonth;
	}
	
	/**
	 * @param txtUpdateActivateDay セットする txtUpdateActivateDay
	 */
	public void setTxtUpdateActivateDay(String txtUpdateActivateDay) {
		this.txtUpdateActivateDay = txtUpdateActivateDay;
	}
	
	/**
	 * @return txtUpdateActivateDay
	 */
	public String getTxtUpdateActivateDay() {
		return txtUpdateActivateDay;
	}
	
	/**
	 * @param pltEditInactivate セットする pltEditInactivate
	 */
	public void setPltEditInactivate(String pltEditInactivate) {
		this.pltEditInactivate = pltEditInactivate;
	}
	
	/**
	 * @return pltEditInactivate
	 */
	public String getPltEditInactivate() {
		return pltEditInactivate;
	}
	
	/**
	 * @param pltSearchInactivate セットする pltSearchInactivate
	 */
	public void setPltSearchInactivate(String pltSearchInactivate) {
		this.pltSearchInactivate = pltSearchInactivate;
	}
	
	/**
	 * @return pltSearchInactivate
	 */
	public String getPltSearchInactivate() {
		return pltSearchInactivate;
	}
	
	/**
	 * @param pltUpdateInactivate セットする pltUpdateInactivate
	 */
	public void setPltUpdateInactivate(String pltUpdateInactivate) {
		this.pltUpdateInactivate = pltUpdateInactivate;
	}
	
	/**
	 * @return pltUpdateInactivate
	 */
	public String getPltUpdateInactivate() {
		return pltUpdateInactivate;
	}
	
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
	 * @param lblNextActivateDate セットする lblNextActivateDate
	 */
	public void setLblNextActivateDate(String lblNextActivateDate) {
		this.lblNextActivateDate = lblNextActivateDate;
	}
	
	/**
	 * @return lblBackActivateDate
	 */
	public String getLblBackActivateDate() {
		return lblBackActivateDate;
	}
	
	/**
	 * @param lblBackActivateDate セットする lblBackActivateDate
	 */
	public void setLblBackActivateDate(String lblBackActivateDate) {
		this.lblBackActivateDate = lblBackActivateDate;
	}
	
	/**
	 * @return countHistory
	 */
	public int getCountHistory() {
		return countHistory;
	}
	
	/**
	 * @param countHistory セットする countHistory
	 */
	public void setCountHistory(int countHistory) {
		this.countHistory = countHistory;
	}
	
	/**
	 * @return currentHistory
	 */
	public int getCurrentHistory() {
		return currentHistory;
	}
	
	/**
	 * @param currentHistory セットする currentHistory
	 */
	public void setCurrentHistory(int currentHistory) {
		this.currentHistory = currentHistory;
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
