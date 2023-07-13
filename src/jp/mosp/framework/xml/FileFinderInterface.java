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
package jp.mosp.framework.xml;

import java.util.List;

/**
 * MosP設定情報ファイル取得インターフェース。<br>
 */
public interface FileFinderInterface {
	
	/**
	 * MosP設定ファイルパスリストを取得する。<br>
	 * @param docBase MosPアプリケーションが配置されている実際のパス
	 * @return MosP設定ファイルパスリスト
	 */
	public List<String> getPathList(String docBase);
	
	/**
	 * MosP設定情報ファイルパスのリストを取得する。<br>
	 * 下位ディレクトリ参照要の場合、一つ下のディレクトリのみを参照する。<br>
	 * @param dirPath MosP設定情報ファイルが配置されている実際のパス
	 * @param containDir 下位ディレクトリ参照要否(true：要、false：不要)
	 * @return MosP設定情報ファイルパスリスト
	 */
	public List<String> getFilePathList(String dirPath, boolean containDir);
	
}
