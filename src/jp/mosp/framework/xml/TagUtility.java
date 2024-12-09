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

import java.text.MessageFormat;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jp.mosp.framework.base.MospException;

/**
 * MosP設定情報作成ユーティリティ。<br>
 */
public class TagUtility {
	
	/**
	 * MosP属性名(XML：キー)。<br>
	 */
	public static final String ATT_KEY = "key";
	
	
	private static class InstanceHolder {
		
		private static final XPath X_PATH = getXPathFactory().newXPath();
	}
	
	
	/**
	 * @return XPathFactory
	 */
	private static XPathFactory getXPathFactory() {
		return XPathFactory.newInstance();
	}
	
	/**
	 * @return XPath
	 */
	public static XPath getXPath() {
		return InstanceHolder.X_PATH;
	}
	
	/**
	 * 下位テキストノード(前後空白除去)を取得する。<br>
	 * @param node ノード
	 * @return 下位テキストノード(前後空白除去)
	 */
	public static String trimText(Node node) {
		// ノード確認
		if (node == null) {
			return null;
		}
		// 下位ノード確認
		if (node.getFirstChild() == null) {
			return null;
		}
		// 下位ノード型確認
		if (node.getFirstChild().getNodeType() != Node.TEXT_NODE) {
			return null;
		}
		// 下位テキストノード値取得
		return node.getFirstChild().getTextContent().trim();
	}
	
	/**
	 * 要素かどうかの判定を行う。<br>
	 * @param node 対象ノード
	 * @return 判定結果(true：要素、false：要素でない)
	 */
	public static boolean isElement(Node node) {
		if (node == null) {
			return false;
		}
		if (node instanceof Element == false) {
			return false;
		}
		return true;
	}
	
	/**
	 * ノードがタグ名の要素であるかを確認する。<br>
	 * @param node    ノード
	 * @param tagName タグ名
	 * @return 確認結果(true：ノードがタグ名の要素である、false：そうでない)
	 */
	public static boolean isTag(Node node, String tagName) {
		if (tagName == null || tagName.isEmpty()) {
			return false;
		}
		return isElement(node) && ((Element)node).getTagName().equals(tagName);
	}
	
	/**
	 * ノードからキーを取得する。<br>
	 * @param node ノード
	 * @return キー
	 */
	public static String getKey(Node node) {
		if (node == null) {
			return "";
		}
		return node.getAttributes().getNamedItem(ATT_KEY).getNodeValue();
	}
	
	/**
	 * XPath式を用いてStringを取得。
	 * @param expression XPath式
	 * @param item item
	 * @return XPath式によって判定された値
	 */
	public static String getString(String expression, Object item) {
		try {
			return getXPath().evaluate(expression, item);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(new MospException(e));
		}
	}
	
	/**
	 * XPath式を用いて真偽値を取得。
	 * @param expression XPath式
	 * @param item item
	 * @return XPath式によって判定された値
	 */
	public static boolean getBoolean(String expression, Object item) {
		try {
			return ((Boolean)getXPath().evaluate(expression, item, XPathConstants.BOOLEAN)).booleanValue();
		} catch (XPathExpressionException e) {
			throw new RuntimeException(new MospException(e));
		}
	}
	
	/**
	 * XPath式を用いてノードリストを取得。
	 * @param expression XPath式
	 * @param item item
	 * @return XPath式によって判定されたノードリスト
	 */
	public static NodeList getElements(String expression, Object item) {
		try {
			return (NodeList)getXPath().evaluate(expression, item, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(new MospException(e));
		}
	}
	
	/**
	 * XPath式を用いて数字を取得。
	 * @param expression XPath式
	 * @param item item
	 * @return XPath式によって判定された値
	 */
	public static Number getNumber(String expression, Object item) {
		try {
			Object obj = getXPath().evaluate(expression, item, XPathConstants.NUMBER);
			return (Number)obj;
		} catch (XPathExpressionException e) {
			throw new RuntimeException(new MospException(e));
		}
	}
	
	/**
	 * エラーメッセージを出力する。<br>
	 * @param path ドキュメントファイルパス
	 * @param node ノード
	 */
	public static void invalidMassage(String path, Node node) {
		String format = "  MosP設定情報ファイルの要素値が不正です。file:{0},<{1} key=\"{2}\">";
		Object[] params = { path, node.getNodeName(), getKey(node) };
		System.out.println(MessageFormat.format(format, params));
	}
	
	/**
	 * エラーメッセージを出力する。<br>
	 * @param path        ドキュメントファイルパス
	 * @param node        ノード
	 * @param itemTagName 要素名
	 * @param itemIndex   要素インデックス
	 */
	public static void invalidItemMassage(String path, Node node, String itemTagName, int itemIndex) {
		String format = "  MosP設定情報ファイルの要素値が不正です。file:{0},<{1} key=\"{2}\">,<{3}>,	index:{4}";
		Object[] params = { path, node.getNodeName(), getKey(node), itemTagName, itemIndex };
		System.out.println(MessageFormat.format(format, params));
	}
	
	/**
	 * エラーメッセージを出力する。<br>
	 * @param path  ドキュメントファイルパス
	 * @param node  ノード
	 * @param index 要素インデックス
	 */
	public static void invalidTagMassage(String path, Node node, int index) {
		String format = "  MosP設定情報ファイルの要素名が不正です。file:{0},<{1}>,	index:{2}";
		Object[] params = { path, node.getNodeName(), index };
		System.out.println(MessageFormat.format(format, params));
	}
	
	/**
	 * エラーメッセージを出力する。<br>
	 * @param path  ドキュメントファイルパス
	 * @param node  ノード
	 * @param index 要素インデックス
	 */
	public static void noElementKeyMessage(String path, Node node, int index) {
		String format = "  MosP設定情報ファイルの要素キーがありません。file:{0},<{1}>,	index:{2}";
		Object[] params = { path, node.getNodeName(), index };
		System.out.println(MessageFormat.format(format, params));
	}
	
}
