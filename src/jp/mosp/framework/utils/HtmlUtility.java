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
package jp.mosp.framework.utils;

import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.base.TopicPath;
import jp.mosp.framework.constant.MospConst;

/**
 * HTML作成に有用なメソッドを提供する。<br><br>
 * HTMLのヘッダーやフッター等、標準化されたHTMLを作成するのに役立つ。<br>
 */
public class HtmlUtility {
	
	/**
	 * 一覧頁繰りボタン数。<br>
	 */
	public static final int		COUNT_PAGE_BUTTON	= 10;
	
	/**
	 * エラーメッセージ上限。<br>
	 */
	public static final int		COUNT_ERROR_MESSAGE	= 10;
	
	/**
	 * JavaScriptメニュー配列定数名。<br>
	 */
	public static final String	JS_ARY_MENU			= "ARY_MENU";
	
	/**
	 * タグ(br)。<br>
	 */
	public static final String	TAG_BR				= "<br />";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private HtmlUtility() {
		// 処理無し
	}
	
	/**
	 * セレクトオプション出力。<br>
	 * 任意のセレクトオプションを出力する。<br>
	 * @param array セレクトオプション内容
	 * @param value 初期選択値
	 * @return HTMLセレクトオプション文字列
	 */
	public static String getSelectOption(String[][] array, String value) {
		// セレクトオプションを出力
		return getSelectOption(array, value, false);
	}
	
	/**
	 * セレクトオプションを出力する。<br>
	 * 任意のセレクトオプションを出力する。<br>
	 * <br>
	 * 初期選択値追加がtrueで初期選択値がセレクトオプション内容に存在しない場合、
	 * セレクトオプションの末尾に初期選択値のオプションを追加する。<br>
	 * <br>
	 * @param array    セレクトオプション内容
	 * @param value    初期選択値
	 * @param isAppend 初期選択値追加(true：追加する、false：追加しない)
	 * @return HTMLセレクトオプション文字列
	 */
	public static String getSelectOption(String[][] array, String value, boolean isAppend) {
		// セレクトオプションを準備
		StringBuilder sb = new StringBuilder();
		// セレクトオプション内容毎に処理
		for (String[] element : array) {
			String selected = "";
			if (MospUtility.isEqual(element[0], value)) {
				selected = " selected=\"selected\"";
			}
			sb.append("<option value=\"");
			sb.append(escapeHTML(element[0]));
			sb.append("\"");
			sb.append(selected);
			sb.append(">");
			sb.append(escapeHTML(element[1]));
			sb.append("</option>");
		}
		// 初期選択値追加がtrueで初期選択値がセレクトオプション内容に存在しない場合
		if (isAppend && MospUtility.isCodeExist(value, array) == false) {
			// セレクトオプションの末尾に初期選択値のオプションを追加
			sb.append("<option value=\"");
			sb.append(escapeHTML(value));
			sb.append("\" selected=\"selected\">");
			sb.append(escapeHTML(value));
			sb.append("</option>");
		}
		// セレクトオプションを取得
		return sb.toString();
	}
	
	/**
	 * セレクトオプションを出力する。<br>
	 * MosPコード情報から対象コードキーの情報を取得して、
	 * セレクトオプションを作成する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    対象コードキー
	 * @param value      初期選択値
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return HTMLセレクトオプション文字列
	 */
	public static String getSelectOption(MospParams mospParams, String codeKey, String value, boolean needBlank) {
		// MosP設定情報からコード配列を取得
		String[][] array = mospParams.getProperties().getCodeArray(codeKey, needBlank);
		// セレクトオプション出力
		return getSelectOption(array, value);
	}
	
	/**
	 * ラジオボタンのHTMLタグ(複数)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param cls        class
	 * @param name       name
	 * @param codeKey    コードキー
	 * @param value      value
	 * @return ラジオボタンのHTMLタグ(複数)
	 */
	public static String getRadioButonInput(MospParams mospParams, String cls, String name, String codeKey,
			String value) {
		// MosP処理情報からコード配列を取得
		String[][] array = mospParams.getProperties().getCodeArray(codeKey, false);
		// ラジオボタンのHTMLタグ(複数)を取得
		return getRadioButonInput(cls, name, array, value);
	}
	
	/**
	 * ラジオボタンのHTMLタグ(複数)を取得する。<br>
	 * @param cls     class
	 * @param array   array
	 * @param name    name
	 * @param value   value
	 * @return ラジオボタンのHTMLタグ(複数)
	 */
	public static String getRadioButonInput(String cls, String name, String[][] array, String value) {
		// HTML生成
		StringBuffer sb = new StringBuffer();
		if (array == null) {
			return sb.toString();
		}
		for (String[] element : array) {
			String checked = "";
			// 選択状態取得
			if (element[0].equals(value)) {
				checked = "checked=\"checked\"";
			}
			// ラジオボタンを作成
			sb.append("<label>");
			sb.append("<input type=\"radio\" ");
			if (cls != null && !cls.isEmpty()) {
				sb.append("class=\"" + cls + "\" ");
			}
			if (name != null && !name.isEmpty()) {
				sb.append("name=\"" + name + "\" ");
			}
			sb.append("value=\"" + escapeHTML(element[0]) + "\"" + escapeHTML(checked) + "\" />");
			sb.append(escapeHTML(element[1]));
			sb.append("</label>");
		}
		return sb.toString();
	}
	
	/**
	 * ラジオボタンのHTMLタグを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param cls        class
	 * @param name       name
	 * @param value      ラジオボタンの値
	 * @param selected   選択値
	 * @param isLabel    ラベル(spanタグ)表示(true：ラベル、false：ラジオボタン)
	 * @return ラジオボタンのHTMLタグ
	 */
	public static String getRadioTag(MospParams mospParams, String cls, String name, String value, String selected,
			boolean isLabel) {
		// HTML生成
		StringBuilder sb = new StringBuilder();
		// チェックボックスがチェックされているかを確認
		boolean isChecked = isChecked(value, selected);
		// ラベルの場合
		if (isLabel) {
			sb.append("<span>");
			sb.append(escapeHTML(isChecked ? NameUtility.selected(mospParams) : MospConst.STR_EMPTY));
			sb.append("</span>");
			return sb.toString();
		}
		// ラジオボタンを作成
		sb.append("<input type=\"radio\" ");
		if (MospUtility.isEmpty(cls) == false) {
			sb.append("class=\"" + cls + "\" ");
		}
		if (MospUtility.isEmpty(name) == false) {
			sb.append("name=\"" + name + "\" ");
		}
		sb.append("value=\"" + escapeHTML(value) + "\" ");
		sb.append(getChecked(isChecked));
		sb.append(" />");
		return sb.toString();
	}
	
	/**
	 * 必須マーク出力。<br>
	 * MosPフレームワーク標準の必須マークを出力する。<br>
	 * @return HTML必須マーク文字列
	 */
	public static String getRequiredMark() {
		return "<span class=\"RequiredLabel\">*&nbsp;</span>";
	}
	
	/**
	 * HTMLエスケープ。<br>
	 * @param aStr エスケープ対象文字列
	 * @return エスケープ後文字列
	 */
	public static String escapeHTML(String aStr) {
		char c;
		String strTarget = aStr != null ? aStr : "";
		StringBuffer returnStr = new StringBuffer();
		int length = strTarget.length();
		for (int i = 0; i < length; i++) {
			c = strTarget.charAt(i);
			if (c == '<') {
				returnStr = returnStr.append("&lt;");
			} else if (c == '>') {
				returnStr = returnStr.append("&gt;");
			} else if (c == '&') {
				returnStr = returnStr.append("&amp;");
			} else if (c == '"') {
				returnStr = returnStr.append("&quot;");
			} else if (c == '\'') {
				returnStr = returnStr.append("&#39;");
			} else {
				returnStr = returnStr.append(c);
			}
		}
		return new String(returnStr);
	}
	
	/**
	 * Line Feedをbrタグに変換する。<br>
	 * @param value 値
	 * @return 変換後の値
	 */
	public static String lineFeedToBrTag(String value) {
		// Line Feedをbrタグに変換
		return MospUtility.replaceAll(value, MospConst.STR_LINE_FEED, TAG_BR);
	}
	
	/**
	 * ボタンタグを取得する。<br>
	 * @param cls        class
	 * @param id         id
	 * @param onClick    onClick関数
	 * @param name       ボタン名称
	 * @param isDisabled 表示要否(true：表示不要、false：表示)
	 * @return ボタンタグHTML文字列
	 */
	public static String getButtonTag(String cls, String id, String onClick, String name, boolean isDisabled) {
		StringBuffer sb = new StringBuffer();
		sb.append("<button type=\"button\" ");
		if (MospUtility.isEmpty(cls) == false) {
			sb.append("class=\"");
			sb.append(cls);
			sb.append("\"");
		}
		if (MospUtility.isEmpty(id) == false) {
			sb.append("id=\"");
			sb.append(id);
			sb.append("\"");
		}
		sb.append(" onclick=\"");
		sb.append(onClick);
		sb.append("\"");
		if (isDisabled) {
			sb.append(" disabled");
		}
		sb.append(">");
		sb.append(escapeHTML(name));
		sb.append("</button>");
		return sb.toString();
	}
	
	/**
	 * ボタンタグ(Submit)を取得する。<br>
	 * @param id   id
	 * @param cmd  コマンドNo.
	 * @param name ボタン名称
	 * @return ボタンタグHTML文字列
	 */
	public static String getButtonTag(String id, String cmd, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("<button type=\"button\" ");
		if (id.length() != 0) {
			sb.append("id=\"");
			sb.append(id);
			sb.append("\"");
		}
		sb.append(" onclick=\"doSubmit(document.form, '");
		sb.append(cmd);
		sb.append("')\">");
		sb.append(escapeHTML(name));
		sb.append("</button>");
		return sb.toString();
	}
	
	/**
	 * 戻るボタンタグを取得する。<br>
	 * platform.jsのsubmitTransferを実行する。<br>
	 * @param mospParams  MosP処理情報
	 * @param checkTarget 変更チェック対象(null：チェックを行わない、""：全体をチェック)
	 * @param extraFunc   追加処理関数文字列(null：追加処理無し)
	 * @return 戻るボタンタグHTML文字列
	 */
	public static String getBackButtonTag(MospParams mospParams, String checkTarget, String extraFunc) {
		// パンくずリストを取得
		List<TopicPath> topicPathList = mospParams.getTopicPathList();
		// パンくずが二つ以下である場合
		if (topicPathList.size() <= 2) {
			// 前の画面がトップになるため空文字を取得
			return "";
		}
		// 一つ前のパンくずを取得
		TopicPath topicPath = topicPathList.get(topicPathList.size() - 2);
		// パンくずからコマンド及び画面名を取得
		String cmd = topicPath.getCommand();
		String name = topicPath.getName();
		// 戻るボタンタグHTML文字列を作成
		StringBuffer sb = new StringBuffer();
		sb.append("<button type=\"button\" style=\"width: auto;\"");
		sb.append(" onclick=\"submitTransfer(event, ");
		// 変更チェック対象を設定
		if (checkTarget == null) {
			sb.append("null");
		} else {
			sb.append("'").append(checkTarget).append("'");
		}
		sb.append(", ");
		// 追加処理関数を設定
		if (extraFunc == null) {
			sb.append("null");
		} else {
			sb.append(extraFunc);
		}
		sb.append(", null, '");
		sb.append(cmd);
		sb.append("')\">");
		sb.append(escapeHTML(name)).append(NameUtility.to(mospParams));
		sb.append("</button>");
		return sb.toString();
	}
	
	/**
	 * テキストボックスのHTMLタグを取得する。<br>
	 * @param cls     class
	 * @param id      id
	 * @param name    name
	 * @param value   value
	 * @param isLabel ラベル(spanタグ)表示(true：ラベル、false：テキストボックス)
	 * @return テキストボックスHTMLタグ
	 */
	public static String getTextboxTag(String cls, String id, String name, String value, boolean isLabel) {
		// HTML生成
		StringBuffer sb = new StringBuffer();
		if (isLabel) {
			// ラベルの場合
			sb.append("<span ");
			if (id != null && !id.isEmpty()) {
				sb.append("id=\"" + id + "\"");
			}
			sb.append(">");
			sb.append(escapeHTML(value));
			sb.append("</span>");
			return sb.toString();
		}
		// テキストボックスの場合
		sb.append("<input type=\"text\" ");
		if (cls != null && !cls.isEmpty()) {
			sb.append("class=\"" + cls + "\" ");
		}
		if (id != null && !id.isEmpty()) {
			sb.append("id=\"" + id + "\" ");
		}
		if (name != null && !name.isEmpty()) {
			sb.append("name=\"" + name + "\" ");
		}
		sb.append("value=\"" + escapeHTML(value) + "\" />");
		return sb.toString();
	}
	
	/**
	 * テキストボックスのHTMLタグを取得する。<br>
	 * nameには、idを入れる。<br>
	 * @param cls     class
	 * @param id      id
	 * @param value   value
	 * @param isLabel ラベル(spanタグ)表示(true：ラベル、false：テキストボックス)
	 * @return テキストボックスHTMLタグ
	 */
	public static String getTextboxTag(String cls, String id, String value, boolean isLabel) {
		// nameにidを入れテキストボックスを作成
		return getTextboxTag(cls, id, id, value, isLabel);
	}
	
	/**
	 * テキストボックスのHTMLタグを取得する。<br>
	 * idには、name + idxを入れる。<br>
	 * @param cls     class
	 * @param name    name
	 * @param idx     インデックス
	 * @param value   value
	 * @return テキストボックスHTMLタグ
	 */
	public static String getTextboxTag(String cls, String name, int idx, String value) {
		// idにname + idxを入れテキストボックスを作成
		return getTextboxTag(cls, new StringBuilder(name).append(idx).toString(), name, value, false);
	}
	
	/**
	 * テキストエリアのHTMLタグを取得する。<br>
	 * @param cls     class
	 * @param id      id
	 * @param name    name
	 * @param value   value
	 * @param isLabel ラベル(spanタグ)表示(true：ラベル、false：テキストボックス)
	 * @return テキストエリアHTMLタグ
	 */
	public static String getTextAreaTag(String cls, String id, String name, String value, boolean isLabel) {
		// HTML生成
		StringBuffer sb = new StringBuffer();
		if (isLabel) {
			// ラベルの場合
			sb.append("<span ");
			if (id != null && !id.isEmpty()) {
				sb.append("id=\"" + id + "\"");
			}
			sb.append(">");
			sb.append(escapeHTML(value));
			sb.append("</span>");
			return sb.toString();
		}
		// テキストエリアの場合
		sb.append("<textarea ");
		if (cls != null && !cls.isEmpty()) {
			sb.append("class=\"" + cls + "\" ");
		}
		if (id != null && !id.isEmpty()) {
			sb.append("id=\"" + id + "\" ");
		}
		if (name != null && !name.isEmpty()) {
			sb.append("name=\"" + name + "\" ");
		}
		sb.append(">" + escapeHTML(value) + "</textarea>");
		return sb.toString();
	}
	
	/**
	 * テキストエリアのHTMLタグを取得する。<br>
	 * idには、name + idxを入れる。<br>
	 * @param cls   class
	 * @param name  name
	 * @param idx   インデックス
	 * @param value value
	 * @return テキストエリアHTMLタグ
	 */
	public static String getTextAreaTag(String cls, String name, int idx, String value) {
		// idにname + idxを入れテキストエリアを作成
		return getTextAreaTag(cls, new StringBuilder(name).append(idx).toString(), name, value, false);
	}
	
	/**
	 * プルダウンのHTMLタグを取得する。
	 * @param cls     class
	 * @param id      id
	 * @param name    name
	 * @param value   value
	 * @param array   プルダウンオプション配列
	 * @param isLabel ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @return プルダウンHTMLタグ
	 */
	public static String getSelectTag(String cls, String id, String name, String value, String[][] array,
			boolean isLabel) {
		return getSelectTag(cls, id, name, value, array, false, isLabel);
	}
	
	/**
	 * プルダウンのHTMLタグを取得する。
	 * nameには、idを入れる。<br>
	 * @param cls   class
	 * @param id    id
	 * @param value value
	 * @param array プルダウンオプション配列
	 * @return プルダウンHTMLタグ
	 */
	public static String getSelectTag(String cls, String id, String value, String[][] array) {
		return getSelectTag(cls, id, id, value, array, false, false);
	}
	
	/**
	 * プルダウンのHTMLタグを取得する。
	 * @param cls       class
	 * @param id        id
	 * @param name      name
	 * @param value     value
	 * @param option    プルダウンオプション文字列
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @param isLabel   ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @param label     ラベル用文字列
	 * @return プルダウンHTMLタグ
	 */
	public static String getSelectTag(String cls, String id, String name, String value, String option,
			boolean needBlank, boolean isLabel, String label) {
		// HTML生成
		StringBuffer sb = new StringBuffer();
		if (isLabel) {
			// ラベルの場合
			sb.append("<span ");
			if (id != null && !id.isEmpty()) {
				sb.append("id=\"" + id + "\"");
			}
			sb.append(">");
			sb.append(escapeHTML(label));
			sb.append("</span>");
			return sb.toString();
		}
		// プルダウンの場合
		sb.append("<select ");
		if (cls != null && !cls.isEmpty()) {
			sb.append("class=\"" + cls + "\" ");
		}
		if (id != null && !id.isEmpty()) {
			sb.append("id=\"" + id + "\" ");
		}
		if (name != null && !name.isEmpty()) {
			sb.append("name=\"" + name + "\" ");
		}
		sb.append(">");
		// 空白行要の場合
		if (needBlank) {
			sb.append("<option value=\"\"></option>");
		}
		sb.append(option);
		sb.append("</select>");
		return sb.toString();
	}
	
	/**
	 * プルダウンのHTMLタグを取得する。<br>
	 * @param cls       class
	 * @param id        id
	 * @param name      name
	 * @param value     value
	 * @param array     プルダウンオプション配列
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @param isLabel   ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @return プルダウンHTMLタグ
	 */
	public static String getSelectTag(String cls, String id, String name, String value, String[][] array,
			boolean needBlank, boolean isLabel) {
		// ラベル用文字列取得
		String label = MospUtility.getCodeName(value, array);
		// オプション文字列取得
		String option = getSelectOption(array, value);
		// プルダウンのHTMLタグを取得
		return getSelectTag(cls, id, name, value, option, needBlank, isLabel, label);
	}
	
	/**
	 * プルダウンのHTMLタグを取得する。<br>
	 * idには、name + idxを入れる。<br>
	 * @param cls       class
	 * @param name      name
	 * @param idx     インデックス
	 * @param value     value
	 * @param array     プルダウンオプション配列
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウンHTMLタグ
	 */
	public static String getSelectTag(String cls, String name, int idx, String value, String[][] array,
			boolean needBlank) {
		// プルダウンのHTMLタグを取得
		return getSelectTag(cls, new StringBuilder(name).append(idx).toString(), name, value, array, needBlank, false);
	}
	
	/**
	 * プルダウンのHTMLタグを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param cls        class
	 * @param id         id
	 * @param name       name
	 * @param value      value
	 * @param codeKey    対象コードキー
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @param isLabel    ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @return プルダウンHTML文字列
	 */
	public static String getSelectTag(MospParams mospParams, String cls, String id, String name, String value,
			String codeKey, boolean needBlank, boolean isLabel) {
		// ラベル用文字列取得
		String label = MospUtility.getCodeName(mospParams, value, codeKey);
		// オプション文字列取得
		String option = getSelectOption(mospParams, codeKey, value, false);
		// プルダウンのHTMLタグを取得
		return getSelectTag(cls, id, name, value, option, needBlank, isLabel, label);
	}
	
	/**
	 * ロゴイメージタグ取得。<br>
	 * @param logoPath ロゴファイルパス
	 * @param namLogo  ロゴファイルタイトル
	 * @return ロゴイメージタグHTML文字列
	 */
	public static String getTagLogoImage(String logoPath, String namLogo) {
		StringBuffer sb = new StringBuffer();
		if (logoPath != null) {
			sb.append("<img class=\"Logo\" id=\"logo\" src=\"");
			sb.append(MospConst.URL_PUB + logoPath);
			sb.append("\" alt=\"" + namLogo + "\">");
		}
		return sb.toString();
	}
	
	/**
	 * チェックボックスのHTMLタグを取得する。<br>
	 * @param mospParams    MosP処理情報
	 * @param cls           class
	 * @param id            id
	 * @param name          name
	 * @param value         value
	 * @param isLabel       ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @param selectedArray チェックボックス選択値配列
	 * @return チェックボックスHTML文字列
	 */
	public static String getCheckTag(MospParams mospParams, String cls, String id, String name, String value,
			boolean isLabel, String... selectedArray) {
		// チェックボックスがチェックされているかを確認
		boolean isChecked = isChecked(value, selectedArray);
		// HTML生成
		StringBuffer sb = new StringBuffer();
		if (isLabel) {
			// ラベルの場合
			sb.append("<span ");
			if (MospUtility.isEmpty(id) == false) {
				sb.append("id=\"" + id + "\"");
			}
			sb.append(">");
			sb.append(escapeHTML(isChecked ? NameUtility.checked(mospParams) : MospConst.STR_EMPTY));
			sb.append("</span>");
			return sb.toString();
		}
		// チェックボックスの場合
		sb.append("<input type=\"checkbox\" ");
		if (MospUtility.isEmpty(cls) == false) {
			sb.append("class=\"" + cls + "\" ");
		}
		if (MospUtility.isEmpty(id) == false) {
			sb.append("id=\"" + id + "\" ");
		}
		if (MospUtility.isEmpty(name) == false) {
			sb.append("name=\"" + name + "\" ");
		}
		sb.append("value=\"" + escapeHTML(value) + "\" ");
		sb.append(getChecked(isChecked));
		sb.append("/>");
		return sb.toString();
	}
	
	/**
	 * チェックボックスのHTMLタグを取得する。<br>
	 * @param mospParams    MosP処理情報
	 * @param cls           class
	 * @param id            id
	 * @param name          name
	 * @param value         value
	 * @param isLabel       ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @param selectedArray チェックボックス選択値配列
	 * @return チェックボックスHTML文字列
	 */
	public static String getCheckTag(MospParams mospParams, String cls, String id, String name, long value,
			boolean isLabel, String... selectedArray) {
		return getCheckTag(mospParams, cls, id, name, String.valueOf(value), isLabel, selectedArray);
	}
	
	/**
	 * チェックボックスのHTMLタグ(複数)を取得する。<br>
	 * @param mospParams    MosP処理情報
	 * @param cls           class
	 * @param name          name
	 * @param codeKey       コードキー
	 * @param isLabel       ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @param selectedArray チェックボックス選択値配列
	 * @return チェックボックスHTML文字列
	 */
	public static String getCheckTags(MospParams mospParams, String cls, String name, String codeKey, boolean isLabel,
			String... selectedArray) {
		// MosP処理情報からコード配列を取得
		String[][] array = mospParams.getProperties().getCodeArray(codeKey, false);
		// ラジオボタンのHTMLタグ(複数)を取得
		return getCheckTags(mospParams, cls, name, array, isLabel, selectedArray);
	}
	
	/**
	 * チェックボックスのHTMLタグ(複数)を取得する。<br>
	 * @param mospParams    MosP処理情報
	 * @param cls           class
	 * @param name          name
	 * @param array         オプション配列
	 * @param isLabel       ラベル(spanタグ)表示(true：ラベル、false：プルダウン)
	 * @param selectedArray チェックボックス選択値配列
	 * @return チェックボックスHTML文字列
	 */
	public static String getCheckTags(MospParams mospParams, String cls, String name, String[][] array, boolean isLabel,
			String... selectedArray) {
		// HTML生成
		StringBuffer sb = new StringBuffer();
		if (array == null) {
			return sb.toString();
		}
		// オプション毎に処理
		for (String[] element : array) {
			// チェックボックスを作成
			sb.append("<label>");
			sb.append(getCheckTag(mospParams, cls, "", name, element[0], isLabel, selectedArray));
			// 名称を作成
			sb.append(element[1]);
			sb.append("</label>");
		}
		return sb.toString();
	}
	
	/**
	 * チェックボックスのHTMLタグを取得する。<br>
	 * idには、name + idxを入れる。<br>
	 * チェックボックス値には、1を入れる。<br>
	 * @param mospParams    MosP処理情報
	 * @param cls           class
	 * @param name          name
	 * @param idx           インデックス
	 * @param selectedArray チェックボックス選択値配列
	 * @return チェックボックスHTMLタグ
	 */
	public static String getCheckTag(MospParams mospParams, String cls, String name, int idx, String... selectedArray) {
		// idにname + idxを入れチェックボックスを作成
		return getCheckTag(mospParams, cls, new StringBuilder(name).append(idx).toString(), name, idx, false,
				selectedArray);
	}
	
	/**
	 * チェックボックスがチェックされているかを確認する。<br>
	 * @param value         チェックボックス値
	 * @param selectedArray チェックボックス選択値配列
	 * @return 確認結果(true：チェックされている、false：チェックされていない)
	 */
	public static boolean isChecked(String value, String... selectedArray) {
		// 値が無い場合
		if (value == null || selectedArray == null) {
			// チェックされていないと判断
			return false;
		}
		// チェックボックス選択値毎に処理
		for (String selected : selectedArray) {
			// チェックボックス値と同じである場合
			if (value.equals(selected)) {
				// チェックされていると判断
				return true;
			}
		}
		// チェックされていないと判断
		return false;
	}
	
	/**
	 * チェック文字列を取得する。<br>
	 * @param value         チェックボックス値
	 * @param selectedArray チェックボックス選択値配列
	 * @return 文字列(checked="checked" or 空白)
	 */
	public static String getChecked(long value, String[] selectedArray) {
		return getChecked(String.valueOf(value), selectedArray);
	}
	
	/**
	 * チェック文字列を取得する。<br>
	 * @param value         チェックボックス値
	 * @param selectedArray チェックボックス選択値配列
	 * @return 文字列(checked="checked" or 空白)
	 */
	public static String getChecked(String value, String... selectedArray) {
		return getChecked(isChecked(value, selectedArray));
	}
	
	/**
	 * チェック文字列を取得する。<br>
	 * チェックボックス値と、{@link MospConst#CHECKBOX_ON}を比較する。<br>
	 * @param value チェックボックス値
	 * @return 文字列(checked="checked" or 空白)
	 */
	public static String getChecked(String value) {
		if (value != null && value.equals(MospConst.CHECKBOX_ON)) {
			return getChecked(true);
		}
		return getChecked(false);
	}
	
	/**
	 * チェック文字列を取得する。<br>
	 * @param isChecked チェック判定
	 * @return 文字列(checked="checked" or 空白)
	 */
	public static String getChecked(boolean isChecked) {
		if (isChecked) {
			return "checked=\"checked\"";
		}
		return "";
	}
	
	/**
	 * SAPNのHTMLタグを取得する。<br>
	 * @param cls    クラス
	 * @param value  表示文字列
	 * @param isSpan SPANフラグ(true：SAPNのHTMLタグ、false：タグ無し(文字列のみ))
	 * @return SAPNのHTMLタグ
	 */
	public static String getSpanTag(String cls, String value, boolean isSpan) {
		// 文字列を準備
		StringBuilder sb = new StringBuilder(escapeHTML(value));
		// SAPNのHTMLタグである場合
		if (isSpan) {
			// SAPNのHTMLタグを追加
			sb.insert(0, "<span class=\"" + cls + "\">");
			sb.append("</span>");
		}
		// 文字列を取得
		return sb.toString();
	}
	
	/**
	 * メッセージ領域を取得する。<br>
	 * {@link MospParams#getMessageList()}、{@link MospParams#getErrorMessageList()}
	 * で得られるメッセージを表示する。
	 * @param params パラメータ
	 * @return HTMLメッセージ領域
	 */
	public static String getMessageDiv(MospParams params) {
		// HTML文字列作成準備
		StringBuffer sb = new StringBuffer();
		// メッセージ取得
		List<String> messageList = params.getMessageList();
		List<String> errorMessageList = params.getErrorMessageList();
		// メッセージ確認
		if (messageList.size() == 0 && errorMessageList.size() == 0) {
			return sb.toString();
		}
		// メッセージ領域作成
		sb.append("<div class=\"Message\">");
		// メッセージ追加
		for (String message : messageList) {
			sb.append("<span class=\"MessageSpan\">");
			sb.append(escapeHTML(message));
			sb.append("</span><br />");
		}
		// エラーメッセージ追加
		int count = 0;
		for (String errorMessage : errorMessageList) {
			// エラーメッセージ上限確認
			if (count >= COUNT_ERROR_MESSAGE) {
				sb.append("<span class=\"ErrorMessageSpan\">");
				sb.append(NameUtility.other(params));
				sb.append(NameUtility.count(params, errorMessageList.size() - count));
				sb.append("</span><br />");
				break;
			}
			sb.append("<span class=\"ErrorMessageSpan\">");
			sb.append(escapeHTML(errorMessage));
			sb.append("</span><br />");
			count++;
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * リスト情報出力。<br>
	 * 一覧の件数及び前頁、次頁ボタンを出力する。<br>
	 * 任意の頁に遷移できる機能を持つ。<br>
	 * @param mospParams  MosP処理情報
	 * @param list        リスト
	 * @param command     頁繰りコマンド
	 * @param dataPerPage 1頁あたりのデータ数
	 * @param selectIndex 選択インデックス
	 * @return HTMLリスト情報文字列
	 */
	public static String getListInfoFlex(MospParams mospParams, List<? extends BaseDtoInterface> list, String command,
			int dataPerPage, String selectIndex) {
		StringBuffer sb = new StringBuffer();
		int countAllData = list == null ? 0 : list.size();
		int select = Integer.parseInt(selectIndex);
		int offset = (select - 1) * dataPerPage;
		int full = MospUtility.divide(countAllData, dataPerPage);
		int end = offset + dataPerPage;
		if (MospUtility.remainder(countAllData, dataPerPage) != 0) {
			full++;
		}
		end = end > countAllData ? countAllData : end;
		sb.append("<div class=\"ListInfo\">");
		sb.append("<table class=\"ListInfoTopTable\">");
		sb.append("<tr>");
		sb.append("<td class=\"RollLinkTd\">");
		if (select - 1 > 0) {
			sb.append("<a ");
			sb.append("onclick=\"submitTransfer(event, null, null, new Array('");
			sb.append(MospConst.PRM_SELECT_INDEX);
			sb.append("', '");
			sb.append(select - 1);
			sb.append("'), '");
			sb.append(command);
			sb.append("')\">");
			sb.append("&lt;&lt;");
			sb.append("</a>");
		}
		sb.append("</td>");
		sb.append("<td>");
		sb.append(countAllData == 0 ? 0 : offset + 1);
		sb.append("&nbsp;");
		sb.append(NameUtility.wave(mospParams));
		sb.append("&nbsp;");
		sb.append(end);
		sb.append("&nbsp;/&nbsp;");
		sb.append(countAllData);
		sb.append("&nbsp;");
		sb.append(NameUtility.count(mospParams));
		sb.append("</td>");
		sb.append("<td class=\"RollLinkTd\">");
		if (select + 1 <= full) {
			sb.append("<a ");
			sb.append("onclick=\"submitTransfer(event, null, null, new Array('");
			sb.append(MospConst.PRM_SELECT_INDEX);
			sb.append("', '");
			sb.append(select + 1);
			sb.append("'), '");
			sb.append(command);
			sb.append("')\">");
			sb.append("&gt;&gt;");
			sb.append("</a>");
		}
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		if (countAllData <= dataPerPage) {
			sb.append("</div>");
			return sb.toString();
		}
		sb.append("<table class=\"ListInfoBottomTable\">");
		sb.append("<tr>");
		sb.append("<td class=\"ListInfoButton\">");
		if (full != 0) {
			int pageNum = MospUtility.divide(select, COUNT_PAGE_BUTTON * COUNT_PAGE_BUTTON);
			for (int i = -1; i < COUNT_PAGE_BUTTON + 1; i++) {
				if (pageNum + i > 0 && pageNum + i <= full) {
					if (select != pageNum + i) {
						sb.append("<a onclick=\"submitTransfer(event, null, null, new Array('");
						sb.append(MospConst.PRM_SELECT_INDEX);
						sb.append("', '");
						sb.append(pageNum + i);
						sb.append("'), '");
						sb.append(command);
						sb.append("')\">");
						sb.append(NameUtility.cornerParentheses(mospParams, String.valueOf(pageNum + i)));
						sb.append("</a>");
					} else if (select == pageNum + i) {
						sb.append(NameUtility.cornerParentheses(mospParams, String.valueOf(pageNum + i)));
					}
				}
			}
		}
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * JavaScript配列(文字列)を取得する。<br>
	 * @param variableName 変数名
	 * @param array        配列
	 * @return JavaScript配列(文字列)
	 */
	public static String getJsArray(String variableName, String[] array) {
		// JavaScript配列(文字列)準備
		StringBuffer sb = new StringBuffer();
		// JavaScript配列宣言
		sb.append(getJsArrayDeclaration(variableName));
		// JavaScript配列に値を追加
		for (String value : array) {
			sb.append(getJsArrayPush(variableName, "\"" + value + "\""));
		}
		return sb.toString();
	}
	
	/**
	 * JavaScript配列(文字列)を取得する。<br>
	 * @param variableName 変数名
	 * @param array        配列
	 * @return JavaScript配列(文字列)
	 */
	public static String getJsArray(String variableName, String[][] array) {
		// JavaScript配列(文字列)準備
		StringBuffer sb = new StringBuffer();
		// JavaScript配列宣言
		sb.append(getJsArrayDeclaration(variableName));
		// JavaScript配列に値を追加
		for (int i = 0; i < array.length; i++) {
			// 一次元配列作成
			String name = variableName + i;
			sb.append(getJsArray(name, array[i]));
			// JavaScript配列に値を追加
			sb.append(getJsArrayPush(variableName, name));
		}
		return sb.toString();
	}
	
	/**
	 * JS配列宣言取得。<br>
	 * @param variableName 変数名
	 * @return JS配列宣言
	 */
	private static String getJsArrayDeclaration(String variableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("var ");
		sb.append(variableName);
		sb.append(" = ");
		sb.append(getJsNewArray(""));
		sb.append(";");
		return sb.toString();
	}
	
	/**
	 * JS配列コンストラクタ取得。<br>
	 * @param value 値
	 * @return JS配列コンストラクタ
	 */
	private static String getJsNewArray(String value) {
		StringBuffer sb = new StringBuffer();
		sb.append("new Array(");
		sb.append(value);
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * JS配列push文字列取得。<br>
	 * @param array 配列
	 * @param value 値
	 * @return JS配列push文字列
	 */
	private static String getJsArrayPush(String array, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(array);
		sb.append(".push(");
		sb.append(value);
		sb.append(");");
		return sb.toString();
	}
	
	/**
	 * disabled属性文字列を取得する。<br>
	 * @param disabled disabled設定(true；disabled、false：disabledでない)
	 * @return disabled属性文字列
	 */
	public static String getDisabled(boolean disabled) {
		if (disabled) {
			return " disabled=\"disabled\" ";
		}
		return "";
	}
	
	/**
	 * read only属性文字列を取得する。<br>
	 * @param isReadOnly read only設定(true；read only、read onlyでない)
	 * @return read only属性文字列
	 */
	public static String getReadOnly(boolean isReadOnly) {
		if (isReadOnly) {
			return " readonly=\"readonly\" ";
		}
		return "";
	}
	
}
