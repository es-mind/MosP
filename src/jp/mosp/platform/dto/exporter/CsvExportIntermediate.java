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
package jp.mosp.platform.dto.exporter;

import jp.mosp.orangesignal.OrangeSignalParams;

/**
 * CSVで出力する中間パラメータクラス。
 */
public class CsvExportIntermediate {
	
	private final String				filePrefix;
	
	private final OrangeSignalParams	orangeSignalParams;
	
	
	/**
	 * コンストラクタ。
	 * @param filePrefix .csvの前の文字列。
	 * @param orangeSignalParams OrangeSignal処理情報
	 */
	public CsvExportIntermediate(String filePrefix, OrangeSignalParams orangeSignalParams) {
		this.filePrefix = filePrefix;
		this.orangeSignalParams = orangeSignalParams;
	}
	
	/**
	 * @return filePrefix .csvの前の文字列。
	 */
	public String getFilePrefix() {
		return filePrefix;
	}
	
	/**
	 * @return orangeSignalParams OrangeSignal処理情報
	 */
	public OrangeSignalParams getOrangeSignalParams() {
		return orangeSignalParams;
	}
	
}
