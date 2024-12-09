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

import java.io.IOException;

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
 * 画像出力ストリームに出力する。<br>
 * 人事汎用バイナリ情報を出力する。
 */
public class HumanBinaryExporter extends BaseExporter implements MospExporterInterface {
	
	@Override
	public void export(MospParams mospParams, HttpServletResponse response) throws MospException {
		Object file = mospParams.getFile();
		// 出力内容保持クラス取得
		ImageContents image = (ImageContents)file;
		// クラスのオブジェクトである場合
		if (image.getFileType().equals(MospConst.CODE_BINARY_FILE_GIF)) {
			// コンテンツタイプ設定
			response.setContentType("image/gif");
		} else if (image.getFileType().equals(MospConst.CODE_BINARY_FILE_JPEG)) {
			// コンテンツタイプ設定
			response.setContentType("image/jpeg");
		} else if (image.getFileType().equals(MospConst.CODE_BINARY_FILE_PNG)) {
			// コンテンツタイプ設定
			response.setContentType("image/png");
		} else {
			// コンテンツタイプ設定
			setFileContentType(mospParams, response);
		}
		// ファイル名設定
		setFileName(mospParams, response);
		// 出力対象準備
		byte[] binaryData = image.getBinaryData();
		// バイト配列に変換し、出力
		try {
			// 出力ストリーム準備
			ServletOutputStream out = response.getOutputStream();
			// 出力
			for (int offset = 0; offset < binaryData.length; offset += MospConst.PROCESS_BYTES) {
				int length = MospConst.PROCESS_BYTES;
				if (offset + length > binaryData.length) {
					length = binaryData.length - offset;
				}
				out.write(binaryData, offset, length);
			}
		} catch (IOException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_OUTPUT_FILE, null);
		}
	}
}
