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
package jp.mosp.platform.human.vo;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 人事入社情報詳細画面の情報を格納する。
 */
public class EntranceCardVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= 6666227114300426937L;
	
	/**
	 * レコードID。<br>
	 * 履歴編集対象のレコードIDを保持しておく。<br>
	 */
	private long				recordId;
	
	private String				txtEntranceYear;
	private String				txtEntranceMonth;
	private String				txtEntranceDay;
	
	
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
	 * @param txtEntranceYear セットする txtEntranceYear
	 */
	public void setTxtEntranceYear(String txtEntranceYear) {
		this.txtEntranceYear = txtEntranceYear;
	}
	
	/**
	 * @return txtEntranceYear
	 */
	public String getTxtEntranceYear() {
		return txtEntranceYear;
	}
	
	/**
	 * @param txtEntranceMonth セットする txtEntranceMonth
	 */
	public void setTxtEntranceMonth(String txtEntranceMonth) {
		this.txtEntranceMonth = txtEntranceMonth;
	}
	
	/**
	 * @return txtEntranceMonth
	 */
	public String getTxtEntranceMonth() {
		return txtEntranceMonth;
	}
	
	/**
	 * @param txtEntranceDay セットする txtEntranceDay
	 */
	public void setTxtEntranceDay(String txtEntranceDay) {
		this.txtEntranceDay = txtEntranceDay;
	}
	
	/**
	 * @return txtEntranceDay
	 */
	public String getTxtEntranceDay() {
		return txtEntranceDay;
	}
	
}
