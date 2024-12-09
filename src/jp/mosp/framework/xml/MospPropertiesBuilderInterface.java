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
package jp.mosp.framework.xml;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.property.MospProperties;

/**
 * MosP設定情報作成インターフェース。<br>
 */
public interface MospPropertiesBuilderInterface {
	
	/**
	 * ログメッセージ(ドキュメントエレメント不正)。
	 */
	static final String	MSG_INVALID_DOC_ELEMENT	= "  MosP設定情報ファイルのドキュメント要素が不正です。";
	
	/**
	 * ログメッセージ(要素値無し)。
	 */
	static final String	MSG_INVALID_VALUE		= "  MosP設定情報ファイルの要素値が不正です。";
	
	/**
	 * ログメッセージ(アドオン無効)。
	 */
	static final String	MSG_ADDON_INVALID		= "  MosP設定情報ファイルのアドオン設定が無効です。";
	
	
	/**
	 * MosP設定情報生成
	 * @param docBase MosPアプリケーションが配置されている実際のパス
	 * @return MosP設定情報
	 * @throws MospException ドキュメントリストの取得に失敗した場合
	 */
	public abstract MospProperties build(String docBase) throws MospException;
	
}
