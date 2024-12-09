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

/**
 * カレンダ関連のHTML作成に有用なメソッドを提供する。<br><br>
 * HTMLのヘッダーやフッター等、標準化されたHTMLを作成するのに役立つ。<br>
 */
public class CalendarHtmlUtility {
	
	/**
	 * 区分：カレンダ(日表示)。<br>
	 */
	public static final String	TYPE_DAY			= "TYPE_CALENDAR_DAY";
	
	/**
	 * 区分：カレンダ(月表示)。<br>
	 */
	public static final String	TYPE_MONTH			= "TYPE_CALENDAR_MONTH";
	
	/**
	 * 区分：カレンダ(年表示)。<br>
	 */
	public static final String	TYPE_YEAR			= "TYPE_CALENDAR_YEAR";
	
	/**
	 * ファイルパス(カレンダーIMG)。<br>
	 */
	public static final String	PATH_CALENDAR_IMG	= "../pub/images/calendar.png";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private CalendarHtmlUtility() {
		// 処理無し
	}
	
	/**
	 * カレンダ要素を取得する。<br>
	 * カレンダ要素はDIVで囲まれており、次の要素が含まれている。<br>
	 * ・BUTTON(及びIMG)<br>
	 * ・INPUT(textかhidden)<br>
	 * ・DIV(カレンダー表示領域)<br>
	 * <br>
	 * カレンダ要素内のタグ名でJavaScriptが要素を取得するため、
	 * カレンダ要素に同じタグを複数含めてはならない。<br>
	 * <br>
	 * @param calType    カレンダ区分
	 * @param id         INPUT要素ID(不要な場合は空白を設定)
	 * @param name       INPUT要素名
	 * @param value      INPUT要素値
	 * @param isText     テキストボックスフラグ(true：INPUT要素がtext、false：hidden)
	 * @param isRequired 必須フラグ(true：INPUT要素が必須、false：必須でない)
	 * @param isClickableCalendar クリックイベント(true：追加する、false：追加しない)
	 * @return カレンダ要素HTMLタグ
	 */
	public static String getCalendarDiv(String calType, String id, String name, String value, boolean isText,
			boolean isRequired, boolean isClickableCalendar) {
		// カレンダ要素文字列を準備
		StringBuilder sb = new StringBuilder();
		// DIV要素を追加
		sb.append("<div class=\"CalendarDiv\">");
		// ボタン要素を追加
		sb.append("<button type=\"button\" class=\"CalendarButton\" ");
		if (isClickableCalendar) {
			// ボタン要素のクリックイベントを追加
			sb.append("onclick=\"showCalendar(event, ").append(calType).append(");\"");
		}
		// ボタン要素の閉じかっこを追加
		sb.append(">");
		// IMG要素を追加
		sb.append("<img src=\"").append(PATH_CALENDAR_IMG).append("\" />");
		// ボタン要素を終了
		sb.append("</button>");
		// カレンダ用INPUT要素を追加
		sb.append(getInputTag(calType, id, name, value, isText, isRequired));
		// カレンダー表示領域(DIV)を追加
		sb.append("<div class=\"CalendarDisplayDiv\"></div>");
		// DIV要素を終了
		sb.append("</div>");
		// カレンダ要素文字列を取得
		return sb.toString();
	}
	
	/**
	 * カレンダ用INPUT要素を取得する。<br>
	 * <br>
	 * @param calType    カレンダ区分
	 * @param id         INPUT要素ID(不要な場合は空白を設定)
	 * @param name       INPUT要素名(不要な場合は空白を設定)
	 * @param value      INPUT要素値
	 * @param isText     テキストボックスフラグ(true：INPUT要素がtext、false：hidden)
	 * @param isRequired 必須フラグ(true：INPUT要素が必須、false：必須でない)
	 * @return カレンダ用INPUT要素HTMLタグ
	 */
	protected static String getInputTag(String calType, String id, String name, String value, boolean isText,
			boolean isRequired) {
		// カレンダ用INPUT要素文字列を準備
		StringBuilder sb = new StringBuilder();
		// typeを準備
		String type = isText ? "text" : "hidden";
		// クラスを準備
		String className = "";
		// カレンダ区分を確認
		if (calType.equals(TYPE_YEAR)) {
			className = isRequired ? "Calendar4RequiredTextBox" : "Calendar4TextBox";
		} else if (calType.equals(TYPE_MONTH)) {
			className = isRequired ? "Calendar7RequiredTextBox" : "Calendar7TextBox";
		} else if (calType.equals(TYPE_DAY)) {
			className = isRequired ? "Calendar10RequiredTextBox" : "Calendar10TextBox";
		}
		// INPUT要素を追加
		sb.append("<input ");
		// typeを追加
		sb.append("type=\"").append(type).append("\" ");
		// クラスを追加
		sb.append("class=\"").append(className).append("\" ");
		// INPUT要素IDが指定されている場合
		if (MospUtility.isEmpty(id) == false) {
			// idを追加
			sb.append("id=\"").append(id).append("\" ");
		}
		// INPUT要素名が指定されている場合
		if (MospUtility.isEmpty(name) == false) {
			// idを追加
			sb.append("name=\"").append(name).append("\" ");
		}
		// 値を設定
		sb.append("value=\"").append(HtmlUtility.escapeHTML(value)).append("\" ");
		// INPUT要素を終了
		sb.append("/>");
		// カレンダ用INPUT要素文字列を取得
		return sb.toString();
	}
	
}
