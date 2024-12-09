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
package jp.mosp.time.utils;

/**
 * 勤怠打刻機能における有用なメソッドを提供する。<br><br>
 */
public class TimeRecordUtility {
	
	/**
	 * カード読込コマンド。<br>
	 * カード内容に応じて、システム日付で打刻する。<br>
	 */
	public static final String	CMD_READ_CARD				= "ADTR11";
	
	/**
	 * JSPファイルパス。<br>
	 */
	public static final String	PATH_TIME_RECODR_JSP		= "/jsp/addon/timerecorder/cardRead.jsp";
	
	/**
	 * カードリーダステータス(始業)。
	 */
	public static final String	CARD_READER_START_WORK		= "01";
	
	/**
	 * カードリーダステータス(終業)。
	 */
	public static final String	CARD_READER_END_WORK		= "02";
	
	/**
	 * カードリーダステータス(休憩入)。
	 */
	public static final String	CARD_READER_START_REST		= "03";
	
	/**
	 * カードリーダステータス(休憩戻)。
	 */
	public static final String	CARD_READER_END_REST		= "04";
	
	/**
	 * カードリーダステータス(カードID登録)。
	 */
	public static final String	CARD_REGIST_IC_CARD			= "05";
	
	/**
	 * カードリーダステータス(私用外出入)。
	 */
	public static final String	CARD_READER_START_PRIVATE	= "11";
	
	/**
	 * カードリーダステータス(私用外出戻)。
	 */
	public static final String	CARD_READER_END_PRIVATE		= "12";
	
	/**
	 * パラメータID(対象カードID)。
	 */
	public static final String	PRM_CARD_IC_CARD_ID			= "cid";
	
	/**
	 * パラメータID(カードリーダ打刻時間)。
	 */
	public static final String	PRM_CARD_READER_TIME		= "tim";
	
	/**
	 * パラメータID(カードリーダステータス)。
	 */
	public static final String	PRM_CARD_READER_STATUS		= "sts";
	
	/**
	 * 受付時(ICカード新規登録時)のメッセージコード。<br>
	 */
	public static final String	MSG_REGIST_IC_CARD			= "ADTR001";
	
	/**
	 * カード読み取り失敗字のメッセージコード。<br>
	 */
	public static final String	MSG_FAILED_READ				= "ADTR002";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeRecordUtility() {
		// 処理無し
	}
	
}
