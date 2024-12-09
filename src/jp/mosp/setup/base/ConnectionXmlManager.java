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
package jp.mosp.setup.base;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jp.mosp.framework.base.DBConnBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.xml.PropertyTag;
import jp.mosp.framework.xml.TagUtility;

/**
 * ユーザデータベース接続情報管理処理。<br>
 */
public final class ConnectionXmlManager {
	
	/**
	 * XMLファイル(DB接続情報)を出力する。<br>
	 * @param xml      出力ファイル
	 * @param url      URL
	 * @param user     DBユーザ名
	 * @param password DBパスワード
	 * @throws MospException XMLファイルの操作に失敗した場合
	 */
	public static void export(File xml, String url, String user, String password) throws MospException {
		// 文書を作成
		Document document = createDocument(PropertyTag.TAG_DOCUMENT);
		// タグ名(Application)を取得
		String tagName = PropertyTag.APPLICATION.getName();
		// 要素を追加
		addElement(document, tagName, DBConnBean.APP_DB_URL, url);
		addElement(document, tagName, DBConnBean.APP_DB_USER, user);
		addElement(document, tagName, DBConnBean.APP_DB_PASS, password);
		// XMLファイルを出力
		createXMLFile(document, xml);
	}
	
	/**
	 * XMLファイルからDB接続情報を読み取る。<br>
	 * @param xml XMLファイル
	 * @return DB接続情報
	 * @throws MospException XMLファイルの操作に失敗した場合
	 */
	public static Map<String, String> load(File xml) throws MospException {
		// DB接続情報を準備
		Map<String, String> map = new HashMap<String, String>();
		// ドキュメント要素を取得
		Element element = readXml(xml).getDocumentElement();
		// 下位ノードを取得
		NodeList nodes = element.getElementsByTagName(PropertyTag.APPLICATION.getName());
		// ノード毎に処理
		for (int i = 0; i < nodes.getLength(); i++) {
			// ノードを取得
			Node node = nodes.item(i);
			// DB接続情報にキーと値を設定
			map.put(TagUtility.getKey(node), TagUtility.trimText(node));
		}
		// DB接続情報
		return map;
	}
	
	/**
	 * XMLファイルを読み込む。<br>
	 * @param xml XMLファイル
	 * @return XMLドキュメント
	 * @throws MospException XMLファイルの操作に失敗した場合
	 */
	private static Document readXml(File xml) throws MospException {
		try {
			// XMLファイル読込準備
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// XMLファイル読込
			return builder.parse(xml);
		} catch (Throwable t) {
			// MosP例外を発行
			throw new MospException(t);
		}
	}
	
	/**
	 * 文書を作成する。<br>
	 * @param qualifiedName 作成される文書要素の修飾名
	 * @return 文書
	 * @throws MospException XMLファイルの操作に失敗した場合
	 */
	private static Document createDocument(String qualifiedName) throws MospException {
		try {
			// 文書作成準備
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// DOM操作処理を準備
			DOMImplementation dom = builder.getDOMImplementation();
			// 文書を作成
			return dom.createDocument("", qualifiedName, null);
		} catch (Throwable t) {
			// MosP例外を発行
			throw new MospException(t);
		}
	}
	
	/**
	 * 文書に要素を追加する。<br>
	 * @param document 文書
	 * @param tagName  タグ名
	 * @param key      キー(属性として設定される)
	 * @param value    値(テキスト内容として設定される)
	 */
	private static void addElement(Document document, String tagName, String key, String value) {
		// 文書要素を取得
		Element documentElement = document.getDocumentElement();
		// タグ名の要素を作成
		Element element = document.createElement(tagName);
		// 属性(キー)を設定
		element.setAttribute(TagUtility.ATT_KEY, key);
		// テキスト内容を設定
		element.setTextContent(value);
		// 文書要素に追加
		documentElement.appendChild(element);
	}
	
	/**
	 * 文書をXMLファイルとして出力する。<br>
	 * @param document 文書
	 * @param xml      XMLファイル
	 * @throws MospException XMLファイルの操作に失敗した場合
	 */
	private static void createXMLFile(Document document, File xml) throws MospException {
		try {
			// 文書変換処理を準備
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			// 文書変換処理に変換設定を追加
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// 文書をXMLファイルに出力
			transformer.transform(new DOMSource(document), new StreamResult(xml));
		} catch (Throwable t) {
			// MosP例外を発行
			throw new MospException(t);
		}
	}
	
}
