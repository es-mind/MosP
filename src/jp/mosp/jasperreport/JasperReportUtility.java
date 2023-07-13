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

import java.util.List;

import jp.mosp.framework.base.MospException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * JasperReportを用いる上で有用なメソッドを提供する。<br><br>
 */
public class JasperReportUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private JasperReportUtility() {
		// 処理無し
	}
	
	/**
	 * 帳票(JasperPrint)を作成する。<br>
	 * @param template テンプレートファイルパス
	 * @param list     データソース
	 * @return 帳票(JasperPrint)
	 * @throws MospException 帳票の作成に失敗した場合
	 */
	public static JasperPrint createJasperPrint(String template, List<?> list) throws MospException {
		try {
			return JasperFillManager.fillReport(template, null, new JRBeanCollectionDataSource(list));
		} catch (JRException e) {
			throw new MospException(e);
		}
	}
	
}
