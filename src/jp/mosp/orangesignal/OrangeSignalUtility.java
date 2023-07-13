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
package jp.mosp.orangesignal;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.ExceptionConst;
import jp.sf.orangesignal.csv.Csv;
import jp.sf.orangesignal.csv.CsvConfig;
import jp.sf.orangesignal.csv.handlers.StringArrayListHandler;

/**
 * OrangeSignalを用いる上で有用なメソッドを提供する。<br><br>
 */
public class OrangeSignalUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private OrangeSignalUtility() {
		// 処理無し
	}
	
	/**
	 * リクエストされたファイルをCSVとして解析する。<br>
	 * @param requestedFile リクエストされたファイル
	 * @return 解析結果(文字列配列のリスト)
	 * @throws MospException 入出力例外が発生した場合
	 */
	public static List<String[]> parse(InputStream requestedFile) throws MospException {
		return parse(requestedFile, new OrangeSignalParams());
	}
	
	/**
	 * リクエストされたファイルをCSVとして解析する。<br>
	 * @param requestedFile リクエストされたファイル
	 * @param orangeParams OrangeSignal処理情報
	 * @return 解析結果(文字列配列のリスト)
	 * @throws MospException 入出力例外が発生した場合
	 */
	public static List<String[]> parse(InputStream requestedFile, OrangeSignalParams orangeParams)
			throws MospException {
		try {
			return Csv.load(requestedFile, orangeParams.getEncoding(), getCsvConfig(orangeParams),
					new StringArrayListHandler());
		} catch (IOException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_INPUT_FILE, null);
		}
	}
	
	/**
	 * CSVデータリストを設定した{@link OrangeSignalParams}を取得する。<br>
	 * @param csvDataList CSVデータリスト
	 * @return OrangeSignal処理情報
	 */
	public static OrangeSignalParams getOrangeSignalParams(List<String[]> csvDataList) {
		// OrangeSignal処理情報生成
		OrangeSignalParams orangeParams = new OrangeSignalParams();
		// CSVデータリスト設定
		orangeParams.setCsvDataList(csvDataList);
		return orangeParams;
	}
	
	/**
	 * {@link OrangeSignalParams}の設定値から区切り文字形式情報を生成する。<br>
	 * @param orangeParams OrangeSignal処理情報
	 * @return 区切り文字形式情報
	 */
	public static CsvConfig getCsvConfig(OrangeSignalParams orangeParams) {
		// OrangeSignal処理情報取得
		Character separator = orangeParams.getSeparator();
		Character quote = orangeParams.getQuote();
		Character escape = orangeParams.getEscape();
		// 区切り文字形式情報生成
		CsvConfig csvConfig = new CsvConfig(separator, quote, escape);
		// 空行無視設定
		csvConfig.setIgnoreEmptyLines(orangeParams.isIgnoreEmptyLines());
		// 囲み文字無視設定
		csvConfig.setQuoteDisabled(orangeParams.getQuoteDisabled());
		// 改行文字列
		csvConfig.setLineSeparator(orangeParams.getLineSeparator());
		return csvConfig;
	}
	
}
