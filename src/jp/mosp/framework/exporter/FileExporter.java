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

import java.io.BufferedInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jp.mosp.framework.base.BaseExporter;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospExporterInterface;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;

/**
 * MosPパラメータに設定された内容を、
 * バイト配列に変換し、出力ストリームに出力する。<br>
 */
public class FileExporter extends BaseExporter implements MospExporterInterface {
	
	@Override
	public void export(MospParams mospParams, HttpServletResponse response) throws MospException {
		// コンテンツタイプ設定
		setFileContentType(mospParams, response);
		// ファイル名設定
		setFileName(mospParams, response);
		// 出力対象準備
		BufferedInputStream in = (BufferedInputStream)mospParams.getFile();
		// 出力準備
		byte[] data = new byte[MospConst.PROCESS_BYTES];
		int len;
		// バイト配列に変換し、出力
		try {
			// 出力ストリーム準備
			ServletOutputStream out = response.getOutputStream();
			// 出力
			while ((len = in.read(data, 0, MospConst.PROCESS_BYTES)) != -1) {
				out.write(data, 0, len);
			}
			// 出力対象クローズ
			in.close();
		} catch (Exception e) {
			// ClientAbortException確認(ClientAbortExceptionの場合は処理無し)
			if (isClientAbortException(e) == false) {
				throw new MospException(e, ExceptionConst.EX_FAIL_OUTPUT_FILE, null);
			}
		}
	}
	
}
