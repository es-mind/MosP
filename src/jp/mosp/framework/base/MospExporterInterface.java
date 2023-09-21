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
package jp.mosp.framework.base;

import javax.servlet.http.HttpServletResponse;

/**
 * ファイル等をレスポンスとして出力する機能を備えるインターフェース。<br>
 */
public interface MospExporterInterface {
	
	/**
	 * ファイル等をレスポンスとして出力する。<br>
	 * 必要に応じて、コンテンツタイプ及びファイル名の設定も行う。<br>
	 * @param mospParams MosPパラメータ
	 * @param response   レスポンス
	 * @throws MospException 出力に失敗した場合
	 */
	void export(MospParams mospParams, HttpServletResponse response) throws MospException;
	
}
