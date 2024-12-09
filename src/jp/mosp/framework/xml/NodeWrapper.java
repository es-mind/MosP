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

import org.w3c.dom.Node;

/**
 * ノード情報。<br>
 */
public final class NodeWrapper implements Serializable {
	
	private static final long	serialVersionUID	= 1518576038024376804L;
	
	/**
	 * ドキュメントのファイルパス。<br>
	 */
	final String				path;
	
	/**
	 * ドキュメント内の対象ノードのインデックス。<br>
	 */
	final int					index;
	
	/**
	 * ノード。<br>
	 */
	private transient Node		node;
	
	
	/**
	 * コンストラクタ。<br>
	 * @param path  ドキュメントのファイルパス
	 * @param node  ノード
	 * @param index ドキュメント内の対象ノードのインデックス
	 */
	public NodeWrapper(String path, Node node, int index) {
		this.path = path;
		this.node = node;
		this.index = index;
	}
	
	/**
	 * @return node
	 */
	public Node getNode() {
		return node.cloneNode(true);
	}
	
}
