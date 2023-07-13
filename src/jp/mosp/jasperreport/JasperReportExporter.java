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
package jp.mosp.jasperreport;

import javax.servlet.http.HttpServletResponse;

import jp.mosp.framework.base.BaseExporter;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospExporterInterface;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.ExceptionConst;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * JasperReportで作成した帳票を出力する。<br>
 * {@link MospParams#getFile()}により得られる物が、
 * 下記いずれかのクラスのオブジェクトでなくてはならない。
 * <ul>
 * <li>
 * net.sf.jasperreports.engine.JasperPrint
 * </li>
 * <li>
 * net.sf.jasperreports.engine.JRExporter
 * </li>
 * <li>
 * jp.mosp.jasperreport.JasperReportIntermediate
 * </li>
 * </ul>
 */
public class JasperReportExporter extends BaseExporter implements MospExporterInterface {
	
	@Override
	public void export(MospParams mospParams, HttpServletResponse response) throws MospException {
		// コンテンツタイプ設定
		setFileContentType(mospParams, response);
		// ファイル名設定
		setFileName(mospParams, response);
		// Exporter準備
		JRExporter exporter = null;
		// 出力対象取得
		Object file = mospParams.getFile();
		// 出力対象確認
		if (file instanceof JasperPrint) {
			// PDFとしてExporterを設定(JasperPrintの場合)
			exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, file);
		} else if (file instanceof JRExporter) {
			// Exporterを設定(JRExporterの場合)
			exporter = (JRExporter)file;
		} else {
			throw new MospException(ExceptionConst.EX_FAIL_OUTPUT_FILE);
		}
		// 出力ストリームに出力
		try {
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporter.exportReport();
		} catch (Exception e) {
			// ClientAbortException確認(ClientAbortExceptionの場合は処理無し)
			if (isClientAbortException(e) == false) {
				throw new MospException(e, ExceptionConst.EX_FAIL_OUTPUT_FILE, null);
			}
		}
	}
	
}
