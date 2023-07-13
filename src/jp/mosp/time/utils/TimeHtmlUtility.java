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
package jp.mosp.time.utils;

import java.util.List;

import jp.mosp.time.base.AttendanceTotalVo;

/**
 * 勤怠関連のHTML作成に有用なメソッドを提供する。<br><br>
 * HTMLのヘッダーやフッター等、標準化されたHTMLを作成するのに役立つ。<br>
 */
public class TimeHtmlUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeHtmlUtility() {
		// 処理無し
	}
	
	/**
	 * 勤怠一覧等で表示される勤怠の集計値のHTMLを取得する。<br>
	 * @param vo 勤怠集計値表示用VO
	 * @return 勤怠集計値のHTML
	 */
	public static String getAttendanceTotalTable(AttendanceTotalVo vo) {
		// 項目リスト・値リスト取得
		List<String> titleList = vo.getTitleList();
		List<String> valueList = vo.getValueList();
		// 全体SB
		StringBuilder sb = new StringBuilder();
		sb.append("<br>");
		sb.append(vo.getTargetDate());
		sb.append("</br>");
		sb.append("<table class=\"LeftListTable\" ");
		sb.append(" id=\"" + "list\"" + ">");
		// 項目SB
		StringBuilder titleSb = new StringBuilder();
		// 値SB
		StringBuilder valueSb = new StringBuilder();
		// 項目リスト毎に処理
		for (int i = 0; i < titleList.size(); i++) {
			// 項目初め又は行の切り替わりの場合
			if (i == 0 || i % vo.getRowItemNumber() == 0) {
				titleSb.append("<tr>");
				valueSb.append("<tr>");
			}
			// 項目が深夜代休かつ、値が0.0の場合
			if ("深夜".equals(titleList.get(i)) && "0.0".equals(valueList.get(i))) {
				// 項目最後又は行の切れ目かつ項目はじめの場合
				if (i == titleList.size() - 1 || i % vo.getRowItemNumber() == (vo.getRowItemNumber() - 1)) {
					titleSb.append("</tr>");
					valueSb.append("</tr>");
					// 全体に詰める
					sb.append(titleSb.toString());
					sb.append(valueSb.toString());
					// 初期化
					titleSb = new StringBuilder();
					valueSb = new StringBuilder();
				}
				continue;
			}
			// 項目がない場合
			if (titleList.get(i) == null) {
				// 項目ブランク作成
				titleSb.append("<th class=\"Blank\"");
			} else {
				// 項目作成
				titleSb.append("<th class=\"ListSelectTh\"");
				titleSb.append(vo.getClassName());
			}
			if (valueList.get(i) == null) {
				titleSb.append(" rowspan=\"2\"");
			}
			titleSb.append(">");
			if (titleList.get(i) != null) {
				titleSb.append(titleList.get(i));
			}
			titleSb.append("</th>");
			// 値作成
			if (valueList.get(i) != null) {
				valueSb.append("<td class=\"SelectTd\">");
				valueSb.append(valueList.get(i));
				valueSb.append("</td>");
			}
			// 項目最後又は行の切れ目かつ項目はじめの場合
			if (i == titleList.size() - 1 || i % vo.getRowItemNumber() == (vo.getRowItemNumber() - 1)) {
				titleSb.append("</tr>");
				valueSb.append("</tr>");
				// 全体に詰める
				sb.append(titleSb.toString());
				sb.append(valueSb.toString());
				// 初期化
				titleSb = new StringBuilder();
				valueSb = new StringBuilder();
			}
		}
		sb.append("</table>");
		return sb.toString();
	}
	
}
