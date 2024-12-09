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
package jp.mosp.jasperreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JasperPrintで出力する中間パラメータクラス。
 */
public class JasperReportIntermediate implements Serializable {
	
	private static final long	serialVersionUID	= -3719638470694011714L;
	
	/**
	 * MosPアプリケーション設定キー(テンプレートファイルパス)。<br>
	 * 出力ファイル名としても用いられる。<br>
	 */
	private final String		appReport;
	
	/**
	 * データソース。<br>
	 */
	private final List<?>		list;
	
	
	/**
	 * コンストラクタ。
	 * @param appReport MosPアプリケーション設定キー(テンプレートファイルパス)
	 * @param list 出力対象リスト
	 */
	public JasperReportIntermediate(String appReport, List<?> list) {
		this.appReport = appReport;
		this.list = list == null ? new ArrayList<Object>() : list;
	}
	
	/**
	 * @return MosPアプリケーション設定キー(テンプレートファイルパス)
	 */
	public String getAppReport() {
		return appReport;
	}
	
	/**
	 * @return list 出力対象リスト
	 */
	public List<?> getList() {
		return list;
	}
	
}
