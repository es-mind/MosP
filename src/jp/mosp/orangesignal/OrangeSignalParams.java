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
package jp.mosp.orangesignal;

import java.util.List;

import jp.mosp.framework.constant.MospConst;
import jp.sf.orangesignal.csv.CsvConfig;

/**
 * OrangeSignal処理情報を保持するクラス。<br>
 */
public class OrangeSignalParams {
	
	/**
	 * デフォルト文字コード(MS932)。
	 */
	public static final String	DEFAULT_ENCODING	= "MS932";
	
	/**
	 * 区切り文字。
	 */
	private char				separator;
	
	/**
	 * 囲み文字。
	 */
	private char				quote;
	
	/**
	 * 囲み文字無効フラグ
	 */
	private boolean				quoteDisabled;
	
	/**
	 * エスケープ文字。
	 */
	private char				escape;
	
	/**
	 * 	空行無視フラグ。 
	 */
	private boolean				ignoreEmptyLines;
	
	/**
	 * 文字コード。<br>
	 */
	private String				encoding;
	
	/**
	 * 改行文字列
	 */
	private String				lineSeparator;
	
	/**
	 * 出力対象リスト。<br>
	 */
	private List<String[]>		csvDataList;
	
	
	/**
	 * 各フィールドに初期値を設定する。<br>
	 */
	public OrangeSignalParams() {
		// 区切り文字(,)
		separator = CsvConfig.DEFAULT_SEPARATOR;
		// 囲み文字(")
		quote = CsvConfig.DEFAULT_QUOTE;
		// 囲み文字無効フラグ(")
		quoteDisabled = false;
		// エスケープ文字(\)
		escape = CsvConfig.DEFAULT_ESCAPE;
		// 文字コード
		encoding = DEFAULT_ENCODING;
		// 空行無視フラグ(空行無視)
		ignoreEmptyLines = true;
		// 改行文字列(OSの改行文字列)
		lineSeparator = MospConst.LINE_SEPARATOR;
	}
	
	/**
	 * @return separator
	 */
	public char getSeparator() {
		return separator;
	}
	
	/**
	 * @param separator セットする separator
	 */
	public void setSeparator(char separator) {
		this.separator = separator;
	}
	
	/**
	 * @return quote
	 */
	public char getQuote() {
		return quote;
	}
	
	/**
	 * @param quote セットする quote
	 */
	public void setQuote(char quote) {
		this.quote = quote;
	}
	
	/**
	 * 囲み文字無効フラグの取得
	 * @return 囲み文字無効フラグ
	 */
	public boolean getQuoteDisabled() {
		return quoteDisabled;
	}
	
	/**
	 * 囲み文字無効フラグの設定
	 * @param quoteDisabled セットする囲み文字無効フラグ
	 */
	public void setQuoteDisabled(boolean quoteDisabled) {
		this.quoteDisabled = quoteDisabled;
	}
	
	/**
	 * @return escape
	 */
	public char getEscape() {
		return escape;
	}
	
	/**
	 * @param escape セットする escape
	 */
	public void setEscape(char escape) {
		this.escape = escape;
	}
	
	/**
	 * @return ignoreEmptyLines
	 */
	public boolean isIgnoreEmptyLines() {
		return ignoreEmptyLines;
	}
	
	/**
	 * @param ignoreEmptyLines セットする ignoreEmptyLines
	 */
	public void setIgnoreEmptyLines(boolean ignoreEmptyLines) {
		this.ignoreEmptyLines = ignoreEmptyLines;
	}
	
	/**
	 * 改行文字列の取得
	 * @return 改行文字列
	 */
	public String getLineSeparator() {
		return lineSeparator;
	}
	
	/**
	 * 改行文字列の設定
	 * @param lineSeparator セットする改行文字列
	 */
	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
	
	/**
	 * @return encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	
	/**
	 * @param encoding セットする encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * @return csvDataList
	 */
	public List<String[]> getCsvDataList() {
		return csvDataList;
	}
	
	/**
	 * @param csvDataList セットする csvDataList
	 */
	public void setCsvDataList(List<String[]> csvDataList) {
		this.csvDataList = csvDataList;
	}
	
}
