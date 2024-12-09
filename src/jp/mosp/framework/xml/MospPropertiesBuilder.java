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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.property.MospProperties;

/**
 * MosP設定情報を作成する。<br>
 */
public class MospPropertiesBuilder implements MospPropertiesBuilderInterface {
	
	private FileFinderInterface		finder;
	
	private ConvertManagerInterface	manager;
	
	
	@Override
	public MospProperties build(String docBase) throws MospException {
		// 初期化
		init();
		// MosP設定ファイルパスリスト取得
		List<String> pathList = finder.getPathList(docBase);
		// MosP設定ファイルドキュメントリスト取得
		System.out.println("MosP設定情報ファイル解析。");
		List<DocumentWrapper> docList = parse(pathList);
		// 変換
		System.out.println("MosP設定情報作成。");
		ConvertResultInterface result = convert(docList);
		MospProperties properties = new MospProperties();
		properties.setApplicationProperties(result.getApplication());
		properties.setCommandProperties(result.getController());
		properties.setModelProperties(result.getModel());
		properties.setMessageProperties(result.getMessage());
		properties.setNamingProperties(result.getNaming());
		properties.setCodeProperties(result.getCode());
		properties.setAddonProperties(result.getAddon());
		properties.setMainMenuProperties(result.getMainMenu());
		properties.setRoleProperties(result.getRole());
		properties.setConventionProperties(result.getConvention());
		properties.setViewConfigProperties(result.getViewConfig());
		return properties;
	}
	
	/**
	 * 初期化
	 */
	void init() {
		// 初期化
		setFinder(new FileFinder());
		setManager(new ConvertManager());
	}
	
	/**
	 * ファイルパスリストからドキュメントリストを取得する。<br>
	 * @param pathList ファイルパスリスト
	 * @return ドキュメントリスト
	 * @throws MospException ドキュメントリストの取得に失敗した場合
	 */
	List<DocumentWrapper> parse(List<String> pathList) throws MospException {
		// XMLファイル読込準備
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("  MosP設定情報ファイルの解析ができませんでした。");
			throw new MospException(e);
		}
		List<DocumentWrapper> documentList = new ArrayList<DocumentWrapper>();
		for (String path : pathList) {
			// XMLファイル読込
			System.out.println(path);
			File file = new File(path);
			try {
				documentList.add(new DocumentWrapper(path, builder.parse(file)));
			} catch (SAXException e) {
				System.out.println("  MosP設定情報ファイルの解析ができませんでした。");
				throw new MospException(e);
			} catch (IOException e) {
				System.out.println("  MosP設定情報ファイルの解析ができませんでした。");
				throw new MospException(e);
			}
		}
		return documentList;
	}
	
	/**
	 * MosP設定ファイルドキュメントリストを変換する。<br>
	 * @param list MosP設定ファイルドキュメントリスト
	 * @return MosP設定情報変換結果
	 * @throws MospException ドキュメントリストの取得に失敗した場合
	 */
	ConvertResultInterface convert(List<DocumentWrapper> list) throws MospException {
		// 変換結果
		ConvertResultInterface result = manager.init();
		for (DocumentWrapper document : list) {
			// ログ出力
			System.out.println(document.path);
			// ドキュメント要素確認
			if (document.isMosPDocument() == false) {
				System.out.println(MSG_INVALID_DOC_ELEMENT);
				continue;
			}
			// アドオン確認(アドオンでない場合)
			if (document.isAddon() == false) {
				// ドキュメントをMosP設定情報に変換して変換結果に設定
				convert(result, document);
				continue;
			}
			// アドオン確認(アドオンが有効でない場合)
			if (document.isAddonValid() == false) {
				System.out.println(MSG_ADDON_INVALID);
				// 当ファイルの設定は無視
				continue;
			}
			// ドキュメントをMosP設定情報に変換して変換結果に設定
			convert(result, document);
			// アドオンディレクトリの内容を追加
			addAddonDir(result, document);
		}
		return result;
	}
	
	/**
	 * ドキュメントをMosP設定情報に変換し、変換結果に設定する。<br>
	 * @param result   変換結果
	 * @param document ドキュメント
	 */
	protected void convert(ConvertResultInterface result, DocumentWrapper document) {
		// ファイルパス取得
		String path = document.path;
		// ドキュメントのノードリストを取得
		NodeList nodeList = TagUtility.getElements(PropertyTag.TAG_DOCUMENT + "/*", document.getDocument());
		int index = 0;
		int length = nodeList.getLength();
		// ノード毎に内容を変換して設定
		while (index < length) {
			Node node = nodeList.item(index);
			String tag = node.getNodeName();
			// MosPで管理しない設定情報である場合
			if (manager.isUnknown(tag)) {
				// エラーメッセージ。
				TagUtility.invalidTagMassage(path, node, index);
				break;
			}
			// ノードをMosP設定情報に変換
			manager.convert(result.get(tag), new NodeWrapper(path, node, index));
			index++;
		}
	}
	
	/**
	 * アドオンディレクトリの内容を、変換結果に追加する。<br>
	 * <br>
	 * 同一ディレクトリに存在する同一アドオンID(ファイル名から.xmlを除いたもの)名の
	 * ディレクトリに存在するMosP設定情報ファイルを読み込み、MosP設定情報に変換する。<br>
	 * <br>
	 * @param result   変換結果
	 * @param document アドオンドキュメント
	 * @throws MospException ドキュメントリストの取得に失敗した場合
	 */
	protected void addAddonDir(ConvertResultInterface result, DocumentWrapper document) throws MospException {
		// ファイル情報取得
		File file = new File(document.path);
		// アドオンID取得
		String addonId = file.getName().replace(".xml", "");
		// アドオンディレクトリパス作成
		String dirPath = file.getParent() + "/" + addonId;
		// MosP設定ファイルパスリスト取得
		List<String> pathList = finder.getFilePathList(dirPath, false);
		// MosP設定ファイルドキュメントリスト取得
		List<DocumentWrapper> docList = parse(pathList);
		// ドキュメント毎に処理
		for (DocumentWrapper addonDocument : docList) {
			// ドキュメント要素確認
			if (addonDocument.isMosPDocument() == false) {
				System.out.println(MSG_INVALID_DOC_ELEMENT);
				continue;
			}
			// ドキュメントをMosP設定情報に変換して変換結果に設定
			convert(result, addonDocument);
		}
		return;
	}
	
	/**
	 * @param finder セットする finder
	 */
	public void setFinder(FileFinderInterface finder) {
		this.finder = finder;
	}
	
	/**
	 * @param manager セットする manager
	 */
	public void setManager(ConvertManagerInterface manager) {
		this.manager = manager;
	}
	
}
