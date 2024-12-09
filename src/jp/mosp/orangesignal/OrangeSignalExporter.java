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

import javax.servlet.http.HttpServletResponse;

import jp.mosp.framework.base.BaseExporter;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospExporterInterface;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.ExceptionConst;
import jp.sf.orangesignal.csv.Csv;
import jp.sf.orangesignal.csv.CsvConfig;
import jp.sf.orangesignal.csv.handlers.StringArrayListHandler;

/**
 * List<String[]>の内容を、CSVで出力する。<br>
 * {@link MospParams#getFile()}により得られる物が、
 * 下記いずれかのクラスのオブジェクトでなくてはならない。
 * <ul><li>
 * jp.mosp.orangesignal.OrangeSignalParams
 * </li></ul>
 */
public class OrangeSignalExporter extends BaseExporter implements MospExporterInterface {
	
	@Override
	public void export(MospParams mospParams, HttpServletResponse response) throws MospException {
		// コンテンツタイプ設定
		setFileContentType(mospParams, response);
		// ファイル名設定
		setFileName(mospParams, response);
		// 各画面で設定したOrangeSignal処理情報を取得
		OrangeSignalParams orangeParams = (OrangeSignalParams)mospParams.getFile();
		// OrangeSignal処理情報から区切り文字形式情報生成
		CsvConfig cfg = OrangeSignalUtility.getCsvConfig(orangeParams);
		// 文字コード設定
		String encoding = orangeParams.getEncoding() != null ? orangeParams.getEncoding() : "MS932";
		// データを取得
		List<String[]> list = orangeParams.getCsvDataList();
		try {
			// OUTPUTSTREAMに出力する
			Csv.save(list, response.getOutputStream(), encoding, cfg, new StringArrayListHandler());
		} catch (Exception e) {
			// ClientAbortException確認(ClientAbortExceptionの場合は処理無し)
			if (isClientAbortException(e) == false) {
				throw new MospException(e, ExceptionConst.EX_FAIL_OUTPUT_FILE, null);
			}
		}
		
	}
	
}
