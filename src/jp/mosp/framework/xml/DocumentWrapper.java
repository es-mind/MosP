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

import java.io.Serializable;

import org.w3c.dom.Document;

/**
 * ドキュメントクラス。<br>
 */
public class DocumentWrapper implements Serializable {
	
	private static final long			serialVersionUID	= 6850067929501863848L;
	
	/**
	 * ファイルパス。<br>
	 */
	final String						path;
	
	private transient final Document	document;
	
	
	/**
	 * コンストラクタ。
	 * @param path xmlのファイルパス。
	 * @param document documentオブジェクト。
	 */
	public DocumentWrapper(String path, Document document) {
		this.path = path;
		this.document = document;
	}
	
	/**
	 * @return document xmlDocumentオブジェクト。
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * @return MosP用のxmlの場合true、そうでない場合false。
	 */
	public boolean isMosPDocument() {
		return PropertyTag.TAG_DOCUMENT.equals(document.getDocumentElement().getTagName());
	}
	
	/**
	 * @return アドオンの場合true、そうでない場合false
	 */
	public boolean isAddon() {
		return TagUtility.getString(PropertyTag.TAG_DOCUMENT + "/Addon", document).isEmpty() == false;
	}
	
	/**
	 * @return アドオン無効の場合true、そうでない場合false
	 */
	public boolean isAddonValid() {
		return Boolean
			.parseBoolean(TagUtility.getString(PropertyTag.TAG_DOCUMENT + "/Addon/AddonValid[text()]", document));
	}
	
}
