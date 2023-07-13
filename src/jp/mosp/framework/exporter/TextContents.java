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
package jp.mosp.framework.exporter;

import java.io.Serializable;

/**
 * {@link TextExporter}で出力する内容を保持するクラス。
 */
public class TextContents implements Serializable {
	
	private static final long	serialVersionUID	= 7372646244795754773L;
	
	private final String		encoding;
	
	private final String		contents;
	
	
	/**
	 * 文字コード
	 * @param encoding 文字コード
	 * @param contents 出力内容
	 */
	public TextContents(String encoding, String contents) {
		this.encoding = encoding;
		this.contents = contents;
	}
	
	/**
	 * @return encoding 文字コード
	 */
	public String getEncoding() {
		return encoding;
	}
	
	/**
	 * @return contents 出力内容
	 */
	public String getContents() {
		return contents;
	}
	
}
