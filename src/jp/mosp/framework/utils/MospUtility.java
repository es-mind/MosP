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
package jp.mosp.framework.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.base.TopicPath;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.property.AddonProperty;
import jp.mosp.framework.property.MessageProperty;
import jp.mosp.framework.property.ModelProperty;
import jp.mosp.framework.property.MospProperties;

/**
 * MosPアプリケーションを開発する上で有用なメソッドを提供する。<br><br>
 */
public final class MospUtility {
	
	/**
	 * Bean及びDAO接尾辞リスト。<br>
	 */
	private static final String[]	SUFFIX_ARRAY		= { "BeanInterface", "Bean", "Interface" };
	
	/**
	 * 区切文字(半角スペース)。
	 */
	public static final char		CHR_SEPARATOR_SPACE	= ' ';
	
	/**
	 * コマンド末尾のワイルドカード。
	 */
	public static final String		WILD_CARD_COMMAND	= "*";
	
	/**
	 * 文字コード（UTF-8）。
	 */
	public static final String		CHARACTER_ENCODING	= "UTF-8";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospUtility() {
		// 処理無し
	}
	
	/**
	 * モデルキーからモデルクラス名を取得する。<br>
	 * <br>
	 * 対象日がnullの場合、モデル設定情報からモデルクラス名(モデル有効日指定無し)を取得する。<br>
	 * <br>
	 * 対象日がnullでない場合、モデルクラス名群から対象日以前で最新のモデルクラス名を取得する。<br>
	 * 但し、対象日以前のキー(モデル有効日)が無い場合は、モデルクラス名(モデル有効日指定無し)を
	 * 取得する。<br>
	 * <br>
	 * @param modelKey       モデルキー
	 * @param mospProperties MosP設定情報
	 * @param targetDate     対象日
	 * @return モデルクラス名
	 * @throws MospException モデルクラス名の取得に失敗した場合
	 */
	public static String getModelClass(String modelKey, MospProperties mospProperties, Date targetDate)
			throws MospException {
		// MosP設定情報からモデル設定情報群を取得
		Map<String, ModelProperty> modelProperties = mospProperties.getModelProperties();
		// モデル設定情報群からモデル設定情報を取得
		ModelProperty modelProperty = modelProperties.get(modelKey);
		// モデル設定情報確認
		if (modelProperty == null) {
			throw new MospException(new Exception(), ExceptionConst.EX_FAIL_CLASS_NAME, modelKey);
		}
		// 対象日がnullの場合
		if (targetDate == null) {
			// モデル設定情報からモデルクラス名を取得
			return modelProperty.getModelClass();
		}
		// モデル設定情報からモデルクラス名群を取得
		Map<Date, String> modelClassMap = modelProperty.getModelClassMap();
		// モデルクラス名群のキーをリストに変換
		List<Date> keyList = new ArrayList<Date>(modelClassMap.keySet());
		// キーリストをソート(降順)
		Collections.sort(keyList);
		Collections.reverse(keyList);
		// キー毎に処理
		for (Date key : keyList) {
			if (key.after(targetDate)) {
				// 対象外
				continue;
			}
			return modelClassMap.get(key);
		}
		// モデルクラス名を取得(対象日以前で最も新しいキーが無い場合)
		return modelProperty.getModelClass();
	}
	
	/**
	 * モデルクラス名を取得する。<br>
	 * <br>
	 * モデルインターフェースからモデルキーを取得し、MosP設定情報からモデル設定情報を取得する。<br>
	 * <br>
	 * @param cls            対象モデルインターフェース
	 * @param mospProperties MosP設定情報
	 * @param targetDate     対象日
	 * @return モデルクラス名
	 * @throws MospException モデルクラス名の取得に失敗した場合
	 */
	public static String getModelClass(Class<?> cls, MospProperties mospProperties, Date targetDate)
			throws MospException {
		// モデルインターフェースからモデルキーを取得
		String modelKey = getModelKey(cls);
		// モデルキーからモデルクラス名を取得
		return getModelClass(modelKey, mospProperties, targetDate);
	}
	
	/**
	 * モデルインターフェースからモデルキーを取得する。<br>
	 * @param cls 対象モデルインターフェース
	 * @return モデルキー
	 */
	public static String getModelKey(Class<?> cls) {
		// クラス名取得
		String key = cls.getSimpleName();
		// モデルキー取得
		for (String suffix : SUFFIX_ARRAY) {
			if (key.indexOf(suffix) == key.length() - suffix.length()) {
				key = key.replace(suffix, "");
				break;
			}
		}
		return key;
	}
	
	/**
	 * オブジェクトを生成する。<br>
	 * {@link InstanceFactory#loadGeneralInstance(Class, MospParams)}を用いる。<br>
	 * <br>
	 * @param cls        対象オブジェクトインターフェース
	 * @param mospParams MosP処理情報
	 * @return オブジェクト
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createObject(Class<?> cls, MospParams mospParams) throws MospException {
		// インスタンス生成クラスを用いてインスタンスを生成
		return (T)InstanceFactory.loadGeneralInstance(cls, mospParams);
	}
	
	/**
	 * メッセージ設定情報をJavaScriptファイルに変換する。<br>
	 * @param mospProperties MosP設定情報
	 * @throws MospException ファイル出力に失敗した場合
	 */
	public static void outputMessageJs(MospProperties mospProperties) throws MospException {
		// JavaScriptファイルパス及びJavaScript変数名宣言
		final String path = mospProperties.getApplicationProperty(MospConst.APP_DOCBASE) + "/pub/common/js/message.js";
		final String name = "messages";
		// メッセージ設定情報群取得
		Map<String, MessageProperty> messageProperties = mospProperties.getMessageProperties();
		// 出力文字列作成
		StringBuffer sb = new StringBuffer();
		sb.append("var ");
		sb.append(name);
		sb.append(" = new Object();");
		sb.append(MospConst.LINE_SEPARATOR);
		for (Entry<String, MessageProperty> entry : messageProperties.entrySet()) {
			// メッセージ設定情報取得
			MessageProperty messageProperty = entry.getValue();
			// クライアント利用可否確認
			if (messageProperty.getClientAvailable() == false) {
				continue;
			}
			// 要素追加
			sb.append(name);
			sb.append("[\"");
			sb.append(messageProperty.getKey());
			sb.append("\"] = \"");
			sb.append(messageProperty.getMessageBody());
			sb.append("\";");
			sb.append(MospConst.LINE_SEPARATOR);
		}
		// ファイル出力
		outputFile(mospProperties, path, sb.toString());
	}
	
	/**
	 * プルダウン用配列から、コードに対応するコードアイテムキーを取得する。<br>
	 * @param code  対象コード
	 * @param array プルダウン用配列
	 * @return コードアイテムキー
	 */
	public static String getCodeItemCode(String code, String[][] array) {
		// コード確認
		if (code == null) {
			return "";
		}
		// プルダウンの内容を確認
		for (String[] element : array) {
			if (element[0].equals(code)) {
				return element[0];
			}
		}
		return code;
	}
	
	/**
	 * プルダウン用配列から、コードに対応する名称を取得する。<br>
	 * @param code  対象コード
	 * @param array プルダウン用配列
	 * @return コード名称
	 */
	public static String getCodeName(String code, String[][] array) {
		// コード確認
		if (code == null) {
			return "";
		}
		// プルダウンの内容を確認
		for (String[] element : array) {
			if (element[0].equals(code)) {
				return element[1];
			}
		}
		return code;
	}
	
	/**
	 * コードキーに対応するプルダウン用配列を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    コードキー
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 */
	public static String[][] getCodeArray(MospParams mospParams, String codeKey, boolean needBlank) {
		// プルダウン用配列を取得
		return mospParams.getProperties().getCodeArray(codeKey, needBlank);
	}
	
	/**
	 * コードに対応する名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param code       対象コード
	 * @param codeKey    コードキー
	 * @return コード名称
	 */
	public static String getCodeName(MospParams mospParams, String code, String codeKey) {
		return getCodeName(code, getCodeArray(mospParams, codeKey, false));
	}
	
	/**
	 * コードに対応する名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param code       対象コード
	 * @param codeKey    コードキー
	 * @return コード名称
	 */
	public static String getCodeName(MospParams mospParams, int code, String codeKey) {
		// コード名称を取得
		return getCodeName(mospParams, String.valueOf(code), codeKey);
	}
	
	/**
	 * プルダウン用配列を用いて、コードが存在するかを確認する。<br>
	 * <br>
	 * 対象コードがnullである場合、falseを返す。<br>
	 * <br>
	 * @param code  対象コード
	 * @param array プルダウン用配列
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	public static boolean isCodeExist(String code, String[][] array) {
		// 対象コードがnullである場合
		if (code == null) {
			return false;
		}
		// プルダウンの内容を確認
		for (String[] element : array) {
			if (element[0].equals(code)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * コードキーに対応する値のリストを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    コードキー
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return 値のリスト
	 */
	public static List<String> getCodeList(MospParams mospParams, String codeKey, boolean needBlank) {
		// 値のリストを準備
		List<String> list = new ArrayList<String>();
		// コードキーに対応するプルダウン用配列を取得
		String[][] codeArray = getCodeArray(mospParams, codeKey, needBlank);
		// プルダウン用配列毎に処理
		for (String[] array : codeArray) {
			// 値をリストに設定
			list.add(array[0]);
		}
		// 値のリストを取得
		return list;
	}
	
	/**
	 * コードキーに対応するマップ(キー、値)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    コードキー
	 * @return マップ(キー、値)
	 */
	public static Map<String, String> getCodeMap(MospParams mospParams, String codeKey) {
		// コードキーに対応するマップ(キー、値)を取得
		return asMap(getCodeArray(mospParams, codeKey, false));
	}
	
	/**
	 * ファイルを出力する。
	 * @param mospProperties MosP設定情報
	 * @param path 出力ファイルパス
	 * @param body 出力内容
	 * @throws MospException ファイル出力に失敗した場合
	 */
	public static void outputFile(MospProperties mospProperties, String path, String body) throws MospException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(path, false);
			osw = new OutputStreamWriter(fos, mospProperties.getApplicationProperty(MospConst.APP_CHARACTER_ENCODING));
			osw.write(body);
		} catch (FileNotFoundException e) {
			throw new MospException(e);
		} catch (UnsupportedEncodingException e) {
			throw new MospException(e);
		} catch (IOException e) {
			throw new MospException(e);
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
			} catch (IOException e) {
				throw new MospException(e);
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					throw new MospException(e);
				}
			}
		}
	}
	
	/**
	 * ログインユーザの個人IDを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ログインユーザの個人ID
	 */
	public static String getLoginPersonalId(MospParams mospParams) {
		// ログインユーザ情報が存在しない場合
		if (mospParams.getUser() == null) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// ログインユーザの個人IDを取得
		String personalId = mospParams.getUser().getPersonalId();
		// ログインユーザの個人IDが取得できなかった場合
		if (isEmpty(personalId)) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// ログインユーザの個人IDを取得
		return personalId;
	}
	
	/**
	 * 最新のパンくず名称(表示している画面の名称)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ログインユーザの個人ID
	 */
	public static String getTopicPathName(MospParams mospParams) {
		// 最新のパンくず情報を取得
		TopicPath topicPath = getLastValue(mospParams.getTopicPathList());
		// 最新のパンくず情報が取得できなかった場合
		if (topicPath == null) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// パンくず名称を取得
		String name = topicPath.getName();
		// パンくず名称が取得できなかった場合
		if (isEmpty(name)) {
			// 空を取得
			return MospConst.STR_EMPTY;
		}
		// パンくず名称を取得
		return name;
	}
	
	/**
	 * MosP処理情報に設定されているVOがそのクラスであるかを確認する。<br>
	 * MosP処理情報に設定されているVOがそのクラスを継承している場合も、trueを返す。<br>
	 * どの画面からリクエストを受けたのかを判定したりする場合に用いる。<br>
	 * @param mospParams MosP処理情報
	 * @param cls        クラス
	 * @return 確認結果(true：VOがそのクラスである、false：VOがそのクラスでない)
	 */
	public static <T> boolean isTheVo(MospParams mospParams, Class<T> cls) {
		// クラスがnullである場合
		if (cls == null) {
			// VOがそのクラスでないと判断
			return false;
		}
		// MosP処理情報に設定されているVOがそのクラスであるかを確認
		return cls.isInstance(mospParams.getVo());
	}
	
	/**
	 * 人名を取得する。<br>
	 * @param firstName 名
	 * @param lastName  姓
	 * @return 人名
	 */
	public static String getHumansName(String firstName, String lastName) {
		return concat(lastName, firstName);
	}
	
	/**
	 * コマンドの末尾をワイルドカード化した文字列を取得する。<br>
	 * 既に末尾がワイルドカード化されている場合、
	 * ワイルドカードを除いてから処理を行う。<br>
	 * @param command コマンド
	 * @return コマンドの末尾をワイルドカード化した文字列
	 */
	public static String getWildCardCommand(String command) {
		// コマンドからワイルドカードを除去
		String wildCardCommand = command.replaceAll("\\*", "");
		// コマンド確認
		if (wildCardCommand.isEmpty()) {
			return wildCardCommand;
		}
		// コマンド末尾をワイルドカード化
		return wildCardCommand.substring(0, wildCardCommand.length() - 1) + WILD_CARD_COMMAND;
	}
	
	/**
	 * アドオンが有効であるかを確認する。<br>
	 * @param mospParams MosP処理情報
	 * @param addonKey   アドオンキー。
	 * @return 確認結果(true：アドオンが有効である、false：有効でない)
	 */
	public static boolean isAddonValid(MospParams mospParams, String addonKey) {
		// MosP設定情報(アドオン)を取得
		AddonProperty addon = mospParams.getProperties().getAddonProperties().get(addonKey);
		// MosP設定情報(アドオン)が取得できなかった場合
		if (addon == null) {
			// 有効でないと判断
			return false;
		}
		// アドオンが有効であるかを取得
		return addon.isAddonValid();
	}
	
	/**
	 * リクエストのコンテンツタイプに対象のコンテンツタイプが含まれているかを確認する。<br>
	 * リクエストのコンテンツタイプは、ControllerでMosP処理情報に保持されている。<br>
	 * @param mospParams  MosP処理情報
	 * @param contentType 対象コンテントタイプ
	 * @return 確認結果(true：対象のコンテントタイプが含まれている、false：含まれていない)
	 */
	public static boolean isContentTypeContain(MospParams mospParams, String contentType) {
		// リクエストのコンテンツタイプを取得
		String requestType = (String)mospParams.getGeneralParam(MospConst.ATT_CONTENT_TYPE);
		// コンテンツタイプに対象のコンテンツタイプが含まれているかを確認
		return MospUtility.isContain(requestType, contentType);
	}
	
	/**
	 * 連結文字配列を半角スペースで連結する。<br>
	 * @param strs 被連結文字配列
	 * @return 連結された文字列
	 */
	public static String concat(String... strs) {
		return concat(CHR_SEPARATOR_SPACE, strs);
	}
	
	/**
	 * 連結文字配列を区切り文字で連結する。<br>
	 * @param separator 区切文字
	 * @param strs      被連結文字配列
	 * @return 連結された文字列
	 */
	public static String concat(Object separator, String... strs) {
		// 連結文字列準備
		StringBuilder concatted = new StringBuilder();
		// 文字配列を区切り文字で連結
		concat(separator, concatted, strs);
		// 連結文字配列を取得
		return concatted.toString();
	}
	
	/**
	 * 文字配列を半角スペースで連結する。<br>
	 * @param concatted  連結文字列
	 * @param strs       被連結文字配列
	 */
	public static void concat(StringBuilder concatted, String... strs) {
		// 文字配列を半角スペースで連結
		concat(CHR_SEPARATOR_SPACE, concatted, strs);
	}
	
	/**
	 * 文字配列を区切り文字で連結する。<br>
	 * @param separator  区切文字
	 * @param concatted  連結文字列
	 * @param strs       被連結文字配列
	 */
	public static void concat(Object separator, StringBuilder concatted, String... strs) {
		// 被連結文字配列確認
		if (MospUtility.isEmpty(concatted, strs)) {
			// 処理無し
			return;
		}
		// 被連結文字毎に処理
		for (String str : strs) {
			// 被連結文字確認
			if (str == null || str.isEmpty()) {
				continue;
			}
			// 連結文字列確認
			if (concatted.length() != 0) {
				// 区切文字追加
				concatted.append(separator);
			}
			// 被連結文字追加
			concatted.append(str);
		}
	}
	
	/**
	 * 対象文字列を正規表現の区切りで分割する。<br>
	 * 前後に空白が存在した場合は、その空白を除く。<br>
	 * @param target 対象文字列
	 * @param regex  正規表現の区切り
	 * @return 文字列の配列
	 */
	public static String[] split(String target, String regex) {
		// 対象文字列が空の場合
		if (target == null || target.trim().isEmpty()) {
			return new String[0];
		}
		// 対象文字列を正規表現の区切りで分割
		String[] array = target.split(regex);
		// 空白除去
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}
	
	/**
	 * 対象文字列の一部を置換する。<br>
	 * @param value       対象文字列
	 * @param regex       この文字列との一致を判定する正規表現
	 * @param replacement 一致するものそれぞれに置き換えられる文字列
	 * @return 置換後の文字列
	 */
	public static String replaceAll(String value, String regex, String replacement) {
		// 引数が不正である場合
		if (MospUtility.isEmpty(regex) || replacement == null) {
			// 対象文字列を取得
			return getString(value);
		}
		// 置換後の文字列を取得
		return getString(value).replaceAll(regex, replacement);
	}
	
	/**
	 * 対象文字列を正規表現の区切りで分割しリストを取得する。<br>
	 * @param target 対象文字列
	 * @param regex  正規表現の区切り
	 * @return 文字列のリスト
	 */
	public static List<String> asList(String target, String regex) {
		return asList(split(target, regex));
	}
	
	/**
	 * 配列(String)をリスト(String)に変換する。<br>
	 * @param array 対象配列(String)
	 * @return リスト(String)
	 */
	public static List<String> asList(String[] array) {
		return new ArrayList<String>(Arrays.asList(array));
	}
	
	/**
	 * 配列(long)をリスト(Long)に変換する。<br>
	 * @param array 対象配列(long)
	 * @return リスト(Long)
	 */
	public static List<Long> asList(long[] array) {
		List<Long> list = new ArrayList<Long>();
		for (long value : array) {
			list.add(Long.valueOf(value));
		}
		return list;
	}
	
	/**
	 * 配列(String[])をリスト(String[])に変換する。<br>
	 * @param array 対象配列(String[])
	 * @return リスト(String[])
	 */
	public static List<String[]> asList(String[][] array) {
		return Arrays.asList(array);
	}
	
	/**
	 * コレクション(リスト等)(String)を配列(String)に変換する。<br>
	 * @param collection 対象コレクション(String)
	 * @return 配列(String)
	 */
	public static String[] toArray(Collection<String> collection) {
		return collection.toArray(new String[collection.size()]);
	}
	
	/**
	 * 文字列(String)を配列(String)に変換する。<br>
	 * @param strs 文字列の配列
	 * @return 配列(String)
	 */
	public static String[] toArray(String... strs) {
		return strs;
	}
	
	/**
	 * リスト(String)を配列(String)に変換する。<br>
	 * @param list 対象リスト(String)
	 * @return 配列(String)
	 */
	public static String[][] toArrayArray(List<String[]> list) {
		return list.toArray(new String[list.size()][]);
	}
	
	/**
	 * リスト(Integer)を配列(int)に変換する。<br>
	 * @param values 対象リスト(Integer)
	 * @return 配列(int)
	 */
	public static int[] toArrayInt(List<Integer> values) {
		// 配列(int)を準備
		int[] array = new int[values.size()];
		// 対象リスト(Integer)が存在しない場合
		if (isEmpty(values)) {
			// 処理終了
			return array;
		}
		// 値毎に処理
		for (int i = 0; i < values.size(); i++) {
			// 配列(int)に値を設定
			array[i] = getInt(values.get(i));
		}
		// 配列(int)を取得
		return array;
	}
	
	/**
	 * リスト(Long)を配列(long)に変換する。<br>
	 * @param list 対象リスト(Long)
	 * @return 配列(Long)
	 */
	public static long[] toArrayLong(List<Long> list) {
		long[] array = new long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i).longValue();
		}
		return array;
	}
	
	/**
	 * 二次元配列(String)をマップ(String)に変換する。<br>
	 * 各配列には、2つの要素(キーと値)があるものとする。<br>
	 * @param arrays 対象二次元配列(String)
	 * @return マップ(String)
	 */
	public static Map<String, String> asMap(String[][] arrays) {
		// マップを準備
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 配列毎に処理
		for (String[] array : arrays) {
			// キーと値を設定
			map.put(array[0], array[1]);
		}
		// マップを取得
		return map;
	}
	
	/**
	 * マップ(String)を二次元配列(String)に変換する。<br>
	 * @param map マップ(String)
	 * @return 二次元配列(String)
	 */
	public static String[][] toArray(Map<String, String> map) {
		// リストを準備
		List<String[]> list = new ArrayList<String[]>();
		// マップの要素毎に処理
		for (Entry<String, String> entry : map.entrySet()) {
			// 配列を準備
			String[] array = new String[2];
			array[0] = entry.getKey();
			array[1] = entry.getValue();
			// 配列をリストに追加
			list.add(array);
		}
		// 配列を取得
		return toArrayArray(list);
	}
	
	/**
	 * 配列(String)を区切文字で区切った文字列にする。<br>
	 * @param array     対象配列(String)
	 * @param separator 区切文字
	 * @return 区切文字で区切った文字列
	 */
	public static String toSeparatedString(String[] array, String separator) {
		// 字列準備
		StringBuilder sb = new StringBuilder();
		// 文字列毎に処理
		for (String str : array) {
			// 文字列追加
			sb.append(str);
			sb.append(separator);
		}
		// 最終区切文字除去
		if (sb.length() > 0) {
			sb.delete(sb.lastIndexOf(separator), sb.length());
		}
		return sb.toString();
	}
	
	/**
	 * 配列(int)を区切文字で区切った文字列にする。<br>
	 * @param array     対象配列(int)
	 * @param separator 区切文字
	 * @return 区切文字で区切った文字列
	 */
	public static String toSeparatedString(int[] array, String separator) {
		// 字列準備
		StringBuilder sb = new StringBuilder();
		// 数値毎に処理
		for (int value : array) {
			// 文字列追加
			sb.append(value);
			sb.append(separator);
		}
		// 最終区切文字除去
		if (sb.length() > 0) {
			sb.delete(sb.lastIndexOf(separator), sb.length());
		}
		return sb.toString();
	}
	
	/**
	 * コレクションを区切文字で区切った文字列を取得する。<br>
	 * @param collection コレクション
	 * @param separator  区切文字
	 * @return リスト(String)
	 */
	public static String toSeparatedString(Collection<String> collection, String separator) {
		// コレクションを配列に変換
		String[] array = MospUtility.toArray(new ArrayList<String>(collection));
		// 配列(String)を区切文字で区切った文字列を取得
		return toSeparatedString(array, separator);
	}
	
	/**
	 * 対象文字列を制限文字列で切った文字列を取得する。<br>
	 * @param value  対象文字列
	 * @param length 制限文字数
	 * @return 対象文字列を制限文字列で切った文字列
	 */
	public static String substring(String value, int length) {
		// 対象文字列が空である場合
		if (value == null || value.isEmpty() || length < 0) {
			return "";
		}
		// 対象文字列の文字数が制限文字数より小さい場合
		if (value.length() < length) {
			return value;
		}
		// 対象文字列を制限文字列で切った文字列を取得
		return value.substring(0, length);
	}
	
	/**
	 * 文字列の最後の文字を除去する。<br>
	 * @param sb 文字列
	 */
	public static void deleteLastChar(StringBuilder sb) {
		// 文字列が空白の場合
		if (MospUtility.isEmpty(sb) || sb.length() == 0) {
			// 処理無し
			return;
		}
		// 文字列の最後の文字を除去
		sb.deleteCharAt(sb.length() - 1);
	}
	
	/**
	 * 対象文字列コレクションから空文字を除去する。<br>
	 * @param collection 対象文字列コレクション
	 */
	public static void removeEmpty(Collection<String> collection) {
		// 対象文字列コレクションが空である場合
		if (MospUtility.isEmpty(collection)) {
			// 処理不要
			return;
		}
		// 削除対象を準備
		Set<String> set = new HashSet<String>();
		set.add(null);
		set.add(MospConst.STR_EMPTY);
		// nullと空文字を除去
		collection.removeAll(set);
	}
	
	/**
	 * 文字列の配列にnullか空白が含まれるかを確認する。<br>
	 * @param strs 文字列の配列
	 * @return 確認結果(true：nullか空白が含まれる、false：nullも空白も含まれない)
	 */
	public static boolean isEmpty(String... strs) {
		// 文字列毎に処理
		for (String str : strs) {
			// nullか空白である場合
			if (str == null || str.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 配列の文字列が全てnullか空白であるかを確認する。<br>
	 * @param strs 文字列の配列
	 * @return 確認結果(true：全てnullか空白である、false：nullか空白でないものが含まれる)
	 */
	public static boolean isAllEmpty(String... strs) {
		// 文字列毎に処理
		for (String str : strs) {
			// nullか空白でない場合
			if (isEmpty(str) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * オブジェクトの配列にnullが含まれるかを確認する。<br>
	 * @param objects オブジェクトの配列
	 * @return 確認結果(true：nullが含まれる、false：nullが含まれない)
	 */
	public static boolean isEmpty(Object... objects) {
		// オブジェクト毎に処理
		for (Object obj : objects) {
			// nullである場合
			if (obj == null) {
				return true;
			}
		}
		// nullが含まれない場合
		return false;
	}
	
	/**
	 * コレクションがnullか空白であるかを確認する。<br>
	 * @param collection コレクション
	 * @return 確認結果(true：nullか空白、false：nullでも空白でもない)
	 */
	public static boolean isEmpty(Collection<?> collection) {
		// コレクションがnullか空白であるかを確認
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * Mapがnullか空白であるかを確認する。<br>
	 * @param map Map
	 * @return 確認結果(true：nullか空白、false：nullでも空白でもない)
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		// Mapがnullか空白であるかを確認
		return map == null || map.isEmpty();
	}
	
	/**
	 * longの配列がnullか空であるかを確認する。<br>
	 * @param longs longの配列
	 * @return 確認結果(true：nullか空である、false：nullでも空でもない)
	 */
	public static boolean isEmpty(long[] longs) {
		// longの配列がnullか空であるかを確認
		return longs == null || longs.length == 0;
	}
	
	/**
	 * 文字列1に文字列2が含まれるかを確認する。<br>
	 * ただし、両方かいずれかのオブジェクトがnullである場合は、同じでないと判断する。<br>
	 * <br>
	 * @param str1 文字列1
	 * @param str2 文字列2
	 * @return 確認結果(true：文字列1に文字列2が含まれる、false：含まれない)
	 */
	public static boolean isContain(String str1, String str2) {
		// 両方かいずれかの文字列がnullである場合
		if (str1 == null || str2 == null) {
			// 含まれないと判断
			return false;
		}
		// 文字列1に文字列2が含まれるかを確認
		return str1.contains(str2);
	}
	
	/**
	 * 対象数値が数値群に含まれる(数値群のいずれかである)かを確認する。<br>
	 * <br>
	 * @param target 対象数値
	 * @param values 数値群
	 * @return 確認結果(true：対象数値が数値群に含まれる、false：含まれない)
	 */
	public static boolean isContain(int target, int... values) {
		// 数値毎に処理
		for (int value : values) {
			// 対象数値と数値が同じである場合
			if (target == value) {
				// 対象数値が数値群に含まれる(数値群のいずれかである)と判断
				return true;
			}
		}
		// 対象数値が数値群に含まれないと判断
		return false;
	}
	
	/**
	 * オブジェクトが同じ(equals)であるかを確認する。<br>
	 * ただし、両方かいずれかのオブジェクトがnullである場合は、同じでないと判断する。<br>
	 * <br>
	 * @param obj1 オブジェクト1
	 * @param obj2 オブジェクト2
	 * @return 確認結果(true：オブジェクトが同じである、false：オブジェクトが同じでない)
	 */
	public static boolean isEqual(Object obj1, Object obj2) {
		// 両方かいずれかのオブジェクトがnullである場合
		if (obj1 == null || obj2 == null) {
			// 同じでないと判断
			return false;
		}
		// オブジェクトが同じ(equals)であるかを確認
		return obj1.equals(obj2);
	}
	
	/**
	 * 日付を比較する。<br>
	 * @param date1 日付1
	 * @param date2 日付2
	 * @return 比較結果(date1.compareTo(date2))
	 */
	public static int compare(Date date1, Date date2) {
		// 日付を比較(nullが含まれる場合)
		if (date1 == null && date2 == null) {
			return 0;
		}
		if (date1 == null) {
			return -1;
		}
		if (date2 == null) {
			return 1;
		}
		// 日付を比較
		return date1.compareTo(date2);
	}
	
	/**
	 * 文字列を比較する。<br>
	 * @param str1 文字列1
	 * @param str2 文字列2
	 * @return 比較結果(date1.compareTo(date2))
	 */
	public static int compare(String str1, String str2) {
		// 文字列を比較(nullが含まれる場合)
		if (str1 == null && str2 == null) {
			return 0;
		}
		if (str1 == null) {
			return -1;
		}
		if (str2 == null) {
			return 1;
		}
		// 文字列を比較
		return str1.compareTo(str2);
	}
	
	/**
	 * 数値を比較する。<br>
	 * @param num1 数値1
	 * @param num2 数値2
	 * @return 比較結果(new BigDecimal(num1).compareTo(new BigDecimal(num2)))
	 */
	public static int compare(double num1, double num2) {
		// 数値を比較
		return new BigDecimal(num1).compareTo(new BigDecimal(num2));
	}
	
	/**
	 * 文字列を取得する。<br>
	 * nullである場合は、空文字を取得する。<br>
	 * @param obj オブジェクト
	 * @return 文字列
	 */
	public static String getString(Object obj) {
		// nullである場合
		if (obj == null) {
			return MospConst.STR_EMPTY;
		}
		// 文字列を取得
		return obj.toString();
	}
	
	/**
	 * コレクションをソートしたリストを取得する。<br>
	 * 取得できなかった場合は、空のリストを返す。<br>
	 * @param collection コレクション
	 * @param cls        比較クラス
	 * @param isDesc     降順フラグ(true：降順、false：昇順)
	 * @return コレクションをソートしたリスト
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static <C, T extends C> List<T> getSortList(Collection<T> collection, Class<? extends Comparator<C>> cls,
			boolean isDesc) throws MospException {
		// リストを準備
		List<T> list = new ArrayList<T>();
		// コレクションが空である場合
		if (MospUtility.isEmpty(collection)) {
			// 空のリストを取得
			return list;
		}
		// リストにコレクションの要素を設定
		list.addAll(collection);
		// 比較クラスを準備
		Comparator<C> comp = InstanceFactory.getNewInstance(cls);
		// 降順である場合
		if (isDesc) {
			// 逆順を義務付けるコンパレータを取得
			comp = comp.reversed();
		}
		// リストをソート
		Collections.sort(list, comp);
		// コレクションをソートしたリストを取得
		return list;
	}
	
	/**
	 * コレクションの最初の要素を取得する。<br>
	 * 取得できなかった場合は、nullを返す。<br>
	 * @param collection コレクション
	 * @return コレクションの最初の要素
	 */
	public static <T> T getFirstValue(Collection<T> collection) {
		// コレクションが空である場合
		if (MospUtility.isEmpty(collection)) {
			// nullを取得
			return null;
		}
		// コレクションの最初の要素を取得
		return collection.iterator().next();
	}
	
	/**
	 * コレクションの最初の要素を取得する。<br>
	 * 取得できなかった場合は、nullを返す。<br>
	 * コレクションの要素を比較クラスでソートした上で、最初の要素を取得する。<br>
	 * @param collection コレクション
	 * @param cls        比較クラス
	 * @param isDesc     降順フラグ(true：降順、false：昇順)
	 * @return コレクションの最初の要素
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static <C, T extends C> T getFirstValue(Collection<T> collection, Class<? extends Comparator<C>> cls,
			boolean isDesc) throws MospException {
		// コレクションの最初の要素を取得
		return getFirstValue(getSortList(collection, cls, isDesc));
	}
	
	/**
	 * コレクションの最後の要素を取得する。<br>
	 * 取得できなかった場合は、nullを返す。<br>
	 * @param collection コレクション
	 * @return コレクションの最初の要素
	 */
	public static <T> T getLastValue(Collection<T> collection) {
		// コレクションが空である場合
		if (collection == null || collection.isEmpty()) {
			// nullを取得
			return null;
		}
		// 要素を準備
		T value = null;
		// 要素毎に処理
		for (Iterator<T> iterator = collection.iterator(); iterator.hasNext();) {
			// 要素を取得
			value = iterator.next();
		}
		// コレクションの最後の要素を取得
		return value;
	}
	
	/**
	 * コレクションの最初の文字列を取得する。<br>
	 * 取得できなかった場合は、空文字を返す。<br>
	 * @param collection コレクション
	 * @return コレクションの最初の文字列
	 */
	public static String getFirstString(Collection<String> collection) {
		// コレクションが空である場合
		if (collection == null || collection.isEmpty()) {
			// 空文字を取得
			return "";
		}
		// コレクションの最初の要素を取得
		return collection.iterator().next();
	}
	
	/**
	 * リストからインデックスに対応する値を取得する。<br>
	 * リストのサイズがインデックスよりも小さい場合は、空文字を返す。<br>
	 * @param list リスト
	 * @param idx  インデックス
	 * @return インデックスに対応する値
	 */
	public static String getListValue(List<String> list, int idx) {
		// リストのサイズがインデックスよりも小さい場合
		if (list == null || list.size() <= idx) {
			// 空文字を取得
			return "";
		}
		// リストからインデックスに対応する値を取得
		return list.get(idx);
	}
	
	/**
	 * インデックスが妥当であるかを確認する。<br>
	 * インデックスが0より小さい場合は、妥当でないと判断する。<br>
	 * インデックスが配列の大きさを超える場合は、妥当でないと判断する。<br>
	 * @param idx   インデックス
	 * @param array 配列
	 * @return 確認結果(true：インデックスが妥当である、false：妥当でない)
	 */
	public static boolean isIndexValid(int idx, String[] array) {
		// 配列が無い場合
		if (array == null) {
			// 妥当でないと判断
			return false;
		}
		// インデックスが0より小さい場合
		if (isIndexValid(idx) == false) {
			// 妥当でないと判断
			return false;
		}
		// インデックスが配列の大きさを超えないかを確認
		return idx < array.length;
	}
	
	/**
	 * インデックスが妥当であるかを確認する。<br>
	 * インデックスが0より小さい場合は、妥当でないと判断する。<br>
	 * @param idx   インデックス
	 * @return 確認結果(true：インデックスが妥当である、false：妥当でない)
	 */
	public static boolean isIndexValid(int idx) {
		// インデックスが0より小さい場合
		if (idx < 0) {
			// 妥当でないと判断
			return false;
		}
		// 妥当であると判断
		return true;
	}
	
	/**
	 * 戻り値の型に合わせてキャストする。<br>
	 * 呼び出し元からアドオンへ値を引き継きつぐ時に使用することを想定
	 * @param obj オブジェクト
	 * @return castObject オブジェクトをキャストしたもの
	 */
	@SuppressWarnings("unchecked")
	public static <T> T castObject(Object obj) {
		T castObject = (T)obj;
		return castObject;
	}
	
	/**
	 * マップからキーで値(Collection)を取得する。<br>
	 * 取得できない場合は、空のCollectionを取得(マップにも設定)する。<br>
	 * @param map マップ
	 * @param key キー
	 * @param cls コレクションのクラス
	 * @return 値(Collection)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static <K, V, C extends Collection<V>> C getValue(Map<K, C> map, K key, Class<?> cls) throws MospException {
		// マップからキーで値(Collection)を取得
		C value = map.get(key);
		// 値がnullである場合
		if (value == null) {
			// 空のCollectionを取得
			value = castObject(InstanceFactory.getNewInstance(cls));
			// マップに設定
			map.put(key, value);
		}
		// 値を取得
		return value;
	}
	
	/**
	 * マップからキーで値(Map)を取得する。<br>
	 * 取得できない場合は、空のMapを取得(マップにも設定)する。<br>
	 * @param map マップ
	 * @param key キー
	 * @param cls マップに設定されているマップのクラス
	 * @return 値(Map)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static <K, V, C> Map<V, C> getMapValue(Map<K, Map<V, C>> map, K key, Class<?> cls) throws MospException {
		// マップからキーで値(Map)を取得
		Map<V, C> value = map.get(key);
		// 値がnullである場合
		if (value == null) {
			// 空のCollectionを取得
			value = castObject(InstanceFactory.getNewInstance(cls));
			// マップに設定
			map.put(key, value);
		}
		// 値を取得
		return value;
	}
	
	/**
	 * マップからキーで値(List)を取得する。<br>
	 * 取得できない場合は、空のListを取得(マップにも設定)する。<br>
	 * @param map マップ
	 * @param key キー
	 * @return 値(List)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static <K, V> List<V> getListValue(Map<K, List<V>> map, K key) throws MospException {
		// マップからキーで値(List)を取得
		return castObject(getValue(map, key, ArrayList.class));
	}
	
	/**
	 * マップからキーで値を取得する。<br>
	 * 取得できない場合は、nullを取得する。<br>
	 * @param map マップ
	 * @param key キー
	 * @return 値
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Map<String, Object> map, String key) {
		// マップが空である場合
		if (isEmpty(map)) {
			// nullを取得
			return null;
		}
		// キーで値を取得
		return (T)map.get(key);
	}
	
	/**
	 * マップからキーで値(int)を取得する。<br>
	 * 取得できない場合は、0を取得する。<br>
	 * @param map マップ
	 * @param key キー
	 * @return 値(int)
	 */
	public static int getIntValue(Map<String, Object> map, String key) {
		// マップからキーで値(int)を取得
		return getInt(getValue(map, key));
	}
	
	/**
	 * マップに値(Integer)を設定する。<br>
	 * 既に値が設定されている場合は、足した値を設定する。<br>
	 * @param map   マップ
	 * @param key   キー
	 * @param value 値
	 */
	public static <K> void addIntValue(Map<K, Integer> map, K key, int value) {
		// 汎用パラメータ群から現在設定されている値を取得
		int current = getInt(map.get(key));
		// 汎用パラメータ群に値(Integer)を追加
		map.put(key, current + value);
	}
	
	/**
	 * 汎用パラメータ群に値(Integer)を追加する。<br>
	 * 値は、数値を想定している。<br>
	 * @param params 汎用パラメータ群
	 * @param key    キー
	 * @param value  値
	 */
	public static void addValue(Map<String, Object> params, String key, int value) {
		// 汎用パラメータ群から現在設定されている値を取得
		int current = getInt(getValue(params, key));
		// 汎用パラメータ群に値(Integer)を追加
		params.put(key, current + value);
	}
	
	/**
	 * コレクションに要素を設定する。<br>
	 * 但し、nullは設定しない。
	 * @param collection コレクション
	 * @param value      値
	 */
	public static <T> void addValue(Collection<T> collection, T value) {
		// 引数が不足する場合
		if (MospUtility.isEmpty(collection, value)) {
			// 処理無し
			return;
		}
		// コレクションに要素を設定
		collection.add(value);
	}
	
	/**
	 * 文字列から数値を取得する。<br>
	 * 取得できなかった場合は、エラー等とせず0を返す。<br>
	 * @param value 文字列
	 * @return 数値
	 */
	public static int getInt(Object value) {
		try {
			// 文字列である場合
			if (value instanceof String) {
				// 文字列から数値を取得
				return Integer.parseInt((String)value);
			}
			// Integerである場合
			if (value instanceof Integer) {
				// 数値を取得
				return ((Integer)value).intValue();
			}
			// Floatである場合
			if (value instanceof Float) {
				// 数値を取得
				return ((Float)value).intValue();
			}
			// Daoubleである場合
			if (value instanceof Double) {
				// 数値を取得
				return ((Double)value).intValue();
			}
			// それ以外の場合は0を取得
			return 0;
		} catch (Throwable e) {
			// 取得できなかった場合は0を取得
			return 0;
		}
	}
	
	/**
	 * 文字列から数値を取得する。<br>
	 * 取得できなかった場合は、エラー等とせず0を返す。<br>
	 * @param value 文字列
	 * @return 数値
	 */
	public static float getFloat(Object value) {
		try {
			// 文字列である場合
			if (value instanceof String) {
				// 文字列から数値を取得
				return Float.parseFloat((String)value);
			}
			// Integerである場合
			if (value instanceof Integer) {
				// 数値を取得
				return ((Integer)value).floatValue();
			}
			// Floatである場合
			if (value instanceof Float) {
				// 数値を取得
				return ((Float)value).floatValue();
			}
			// Daoubleである場合
			if (value instanceof Double) {
				// 数値を取得
				return ((Double)value).floatValue();
			}
			// それ以外の場合は0を取得
			return 0F;
		} catch (Throwable e) {
			// 取得できなかった場合は0を取得
			return 0F;
		}
	}
	
	/**
	 * 文字列から数値を取得する。<br>
	 * 取得できなかった場合は、エラー等とせず0を返す。<br>
	 * @param value 文字列
	 * @return 数値
	 */
	public static double getDouble(Object value) {
		try {
			// 文字列である場合
			if (value instanceof String) {
				// 文字列から数値を取得
				return Double.parseDouble((String)value);
			}
			// Integerである場合
			if (value instanceof Integer) {
				// 数値を取得
				return ((Integer)value).doubleValue();
			}
			// Floatである場合
			if (value instanceof Float) {
				// 数値を取得
				return ((Float)value).doubleValue();
			}
			// Daoubleである場合
			if (value instanceof Double) {
				// 数値を取得
				return ((Double)value).doubleValue();
			}
			// それ以外の場合は0を取得
			return 0D;
		} catch (Throwable e) {
			// 取得できなかった場合は0を取得
			return 0D;
		}
	}
	
	/**
	 * チェックボックスがチェックされているかを確認する。<br>
	 * @param value 値
	 * @return 確認結果(true：チェックされている、false：チェックされていない)
	 */
	public static boolean isChecked(int value) {
		// チェックボックスがチェックされているかを確認
		return value == Integer.parseInt(MospConst.CHECKBOX_ON);
	}
	
	/**
	 * チェックボックスがチェックされているかを確認する。<br>
	 * @param value 値
	 * @return 確認結果(true：チェックされている、false：チェックされていない)
	 */
	public static boolean isChecked(String value) {
		// チェックボックスがチェックされているかを確認
		return MospUtility.isEqual(value, MospConst.CHECKBOX_ON);
	}
	
	/**
	 * 例外スタックトレースを取得する。<br>
	 * @param ex 例外オブジェクト
	 * @return 例外スタックトレース文字列
	 */
	public static String getStackTrace(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String msg = sw.toString();
		msg = msg.replaceAll(MospConst.LINE_SEPARATOR, "");
		return msg;
	}
	
	/**
	 * 値からbyteの配列を取得する。<br>
	 * @param value 値
	 * @return 値のbyte配列
	 */
	public static byte[] getBytes(String value) {
		// 値が空の場合
		if (value == null || value.isEmpty()) {
			return new byte[0];
		}
		try {
			// 値からbyteの配列を取得
			return value.getBytes(CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return new byte[0];
		}
	}
	
	/**
	 * byteの配列から文字列を取得する。<br>
	 * @param value 値
	 * @return byte配列の文字列
	 */
	public static String newString(byte[] value) {
		// 値が空の場合
		if (value == null || value.length == 0) {
			return "";
		}
		try {
			//
			return new String(value, CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/**
	 * 数値(マイナスの場合は0)を取得する。<br>
	 * @param value        数値
	 * @return 数値(マイナスの場合は0)
	 */
	public static int getIntOrZero(int value) {
		// 数値(マイナスの場合は0)を取得
		return value < 0 ? 0 : value;
	}
	
	/**
	 * 除算を行う。<br>
	 * @param value        値
	 * @param divisor      値を除算する値
	 * @param scale        商のスケール
	 * @param roundingMode 適用する丸めモード
	 * @return 除算結果
	 */
	public static double divide(double value, double divisor, int scale, RoundingMode roundingMode) {
		// BigDecimalを準備
		BigDecimal bd = BigDecimal.valueOf(value);
		// 除算
		return bd.divide(BigDecimal.valueOf(divisor), scale, roundingMode).doubleValue();
	}
	
	/**
	 * 除算(整数)を行う。<br>
	 * @param value        値
	 * @param divisor      値を除算する値
	 * @return 除算結果
	 */
	public static int divide(int value, int divisor) {
		// 値を除算する値が0である場合
		if (divisor == 0) {
			// 値をそのまま取得
			return value;
		}
		// BigDecimalを準備
		BigDecimal bd = BigDecimal.valueOf(value);
		// 除算
		return bd.divideToIntegralValue(BigDecimal.valueOf(divisor)).intValue();
	}
	
	/**
	 * 剰余を取得する。<br>
	 * @param value        値
	 * @param divisor      値を除算する値
	 * @return 剰余
	 */
	public static int remainder(int value, int divisor) {
		// 値を除算する値が0である場合
		if (divisor == 0) {
			// 値をそのまま取得
			return value;
		}
		// BigDecimalを準備
		BigDecimal bd = BigDecimal.valueOf(value);
		// 剰余
		return bd.remainder(BigDecimal.valueOf(divisor)).intValue();
	}
	
	/**
	 * 最大値を取得する。<br>
	 * @param values 値配列
	 * @return 最大値
	 */
	public static int getMaxValue(int... values) {
		// 最大値を準備
		int maxValue = Integer.MIN_VALUE;
		// 値毎に処理
		for (int value : values) {
			maxValue = Math.max(maxValue, value);
		}
		// 最大値を取得
		return maxValue;
	}
	
	/**
	 * 最小値を取得する。<br>
	 * @param values 値配列
	 * @return 最小値
	 */
	public static int getMinValue(int... values) {
		// 最小値を準備
		int minValue = Integer.MAX_VALUE;
		// 値毎に処理
		for (int value : values) {
			minValue = Math.min(minValue, value);
		}
		// 最小値を取得
		return minValue;
	}
	
	/**
	 * 最大値を取得する。<br>
	 * @param values 値コレクション
	 * @return 最大値
	 */
	public static int getMaxValue(Collection<Integer> values) {
		// 値が存在しない場合
		if (isEmpty(values)) {
			// 最小値を取得
			return Integer.MIN_VALUE;
		}
		// 最大値を取得
		return getMaxValue(toArrayInt(new ArrayList<Integer>(values)));
	}
	
	/**
	 * 最小値を取得する。<br>
	 * @param values 値コレクション
	 * @return 最小値
	 */
	public static int getMinValue(Collection<Integer> values) {
		// 値が存在しない場合
		if (isEmpty(values)) {
			// 最大値を取得
			return Integer.MAX_VALUE;
		}
		// 最小値を取得
		return getMinValue(toArrayInt(new ArrayList<Integer>(values)));
	}
	
	/**
	 * 最大値を持つキー群を取得する。<br>
	 * 対象情報群が存在しない場合は、空のキー群を取得する。<br>
	 * @param map 対象情報群
	 * @return 最大値を持つキー群
	 */
	public static <K> Set<K> getKeysHaveMaxValue(Map<K, Integer> map) {
		// 最大値を持つキー群を準備
		Set<K> keys = new TreeSet<K>();
		// 対象情報群が存在しない場合
		if (isEmpty(map)) {
			// 空のキー群を取得
			return keys;
		}
		// 最大値を取得
		int maxValue = getMaxValue(map.values());
		// 対象情報毎に処理
		for (Entry<K, Integer> entry : map.entrySet()) {
			// 値が最大値である場合
			if (maxValue == getInt(entry.getValue())) {
				// キーを最大値を持つキー群に設定
				keys.add(entry.getKey());
			}
		}
		// 最大値を持つキー群を取得
		return keys;
	}
	
	/**
	 * 基の文字列に文字列を設定する。<br>
	 * 但し、基の文字列に設定する文字列が含まれる場合は、設定しない。<br>
	 * @param appended 基の文字列
	 * @param value    設定する文字列
	 * @param isHead   先頭フラグ(true：先頭に設定、false：末尾に設定)
	 * @return 設定後の文字列
	 */
	public static String addString(String appended, String value, boolean isHead) {
		// 基の文字列に設定する文字列が含まれる場合
		if (isContain(appended, value)) {
			// 基の文字列を取得
			return appended;
		}
		// 文字列を準備
		StringBuilder sb = new StringBuilder();
		// 基の文字列が存在する場合
		if (MospUtility.isEmpty(appended) == false) {
			// 基の文字列を設定
			sb.append(appended);
		}
		// 基の文字列に文字列を設定
		addString(sb, value, isHead);
		// 文字列を取得
		return sb.toString();
	}
	
	/**
	 * 基の文字列に文字列を設定する。<br>
	 * @param sb     文字列
	 * @param value  設定する文字列
	 * @param isHead 先頭フラグ(true：先頭に設定、false：末尾に設定)
	 */
	public static void addString(StringBuilder sb, String value, boolean isHead) {
		// 引数が存在しない場合
		if (MospUtility.isEmpty(sb, value)) {
			// 処理無し
			return;
		}
		// 先頭に設定する場合
		if (isHead) {
			// 先頭に設定
			sb.insert(0, value);
			// 処理終了
			return;
		}
		// 末尾に設定(先頭に設定しない場合)
		sb.append(value);
	}
	
}
