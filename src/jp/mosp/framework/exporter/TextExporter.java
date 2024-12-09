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
package jp.mosp.framework.exporter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import jp.mosp.framework.base.BaseExporter;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospExporterInterface;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.ExceptionConst;

/**
 * MosPパラメータに設定された内容を、
 * テキスト出力ストリームに出力する。<br>
 */
public class TextExporter extends BaseExporter implements MospExporterInterface {
	
	@Override
	public void export(MospParams mospParams, HttpServletResponse response) throws MospException {
		Object file = mospParams.getFile();
		String contents = "";
		if (file instanceof TextContents) {
			// コンテンツタイプ設定
			response.setContentType("text/plain");
			// ファイル名設定
			setFileName(mospParams, response);
			TextContents text = (TextContents)file;
			response.setCharacterEncoding(text.getEncoding());
			// 出力内容
			contents = text.getContents();
		} else {
			// コンテンツタイプ設定
			setFileContentType(mospParams, response);
			// ファイル名設定
			setFileName(mospParams, response);
			// 出力内容
			contents = file.toString();
		}
		// テキスト出力ストリームに出力
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(contents);
		} catch (Exception e) {
			// ClientAbortException確認(ClientAbortExceptionの場合は処理無し)
			if (isClientAbortException(e) == false) {
				throw new MospException(e, ExceptionConst.EX_FAIL_OUTPUT_FILE, null);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
}
