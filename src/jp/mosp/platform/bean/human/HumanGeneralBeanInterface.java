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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.TableItemProperty;

/**
 * 人事汎用管理機能で使用する。
 */
public interface HumanGeneralBeanInterface extends BaseBeanInterface {
	
	/**
	 * 人事汎用項目情報リストを取得する。<br>
	 * 人事汎用管理登録共通化の処理で利用する。<br>
	 * 人事汎用項目情報がない場合、初期化したリストをかえす。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @return 人事汎用項目情報リスト
	 */
	List<TableItemProperty> getTableItemList(String division, String viewKey);
	
	/**
	 * 対象人事汎用表示テーブル情報から、人事汎用対象プルダウンを取得する。
	 * 取得できない場合、nullを返す。
	 * @param itemProperty 人事汎用項目情報
	 * @param activeDate 有効日
	 * @return 人事汎用対象プルダウン
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	String[][] getHumanGeneralItemPulldown(ItemProperty itemProperty, Date activeDate) throws MospException;
	
	/**
	 * プルダウンがあるか確認し、あった場合名称又はコードで取得する。
	 * @param itemProperty  人事汎用項目設定情報
	 * @param activeDate プルダウン有効日
	 * @param value 値
	 * @param itemName 項目名
	 * @param isPulldownName プルダウン名要否確認(true：プルダウン名、false：コード)
	 * @return プルダウン名称又はプルダウンコード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	String getPulldownValue(ItemProperty itemProperty, Date activeDate, String value, String itemName,
			boolean isPulldownName) throws MospException;
	
	/**
	 * 人事汎用対象プルダウン情報群を取得
	 * 人事汎用通常・履歴・一覧画面のプルダウンをマップで取得する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用表示区分
	 * @param activeDate 有効日
	 * @return 人事汎用対象プルダウン情報群
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	Map<String, String[][]> getHumanGeneralPulldown(String division, String viewKey, Date activeDate)
			throws MospException;
	
	/**
	 * 有効日決定前の人事汎用対象プルダウン情報群を取得する。
	 * 履歴・一覧の登録・追加画面で使用する。
	 * プルダウン対象項目名をkeyにして"有効日を決定してください"が入ったマップを取得する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用表示区分
	 * @return 有効日決定前の人事汎用対象プルダウン情報群
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	Map<String, String[][]> getInputActiveDateGeneralPulldown(String division, String viewKey) throws MospException;
	
	/**
	 * 人事汎用通常情報の項目が別々のデータの場合、
	 * フォーマットに合わせ合体させたデータを取得する。
	 * 人事汎用項目データ型がある場合の
	 * 通常・履歴・一覧情報をマップにデータを詰める場合に使用する。
	 * @param personalId 個人ID
	 * @param itemName 項目名
	 * @param itemProperty 人事汎用項目設定情報
	 * @param targetDate 対象日
	 * @param labelKeys 人事汎用表示項目キー
	 * @return 項目名の値を合体し、フォーマットに変換した項目値
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	String getSeparateTxtItemNormalValue(String personalId, String itemName, ItemProperty itemProperty, Date targetDate,
			String labelKeys) throws MospException;
	
	/**
	 * 人事汎用履歴情報の項目が別々のデータの場合、
	 * フォーマットに合わせ合体させたデータを取得する。
	 * 人事汎用項目データ型がある場合の
	 * 通常・履歴・一覧情報をマップにデータを詰める場合に使用する。
	 * @param personalId 個人ID
	 * @param itemName 項目名
	 * @param itemProperty 人事汎用項目設定情報
	 * @param activeDate 有効日
	 * @param targetDate 対象日
	 * @param labelKeys 人事汎用表示項目キー
	 * @return 項目名の値を合体し、フォーマットに変換した項目値
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	String getSeparateTxtItemHistoryValue(String personalId, String itemName, ItemProperty itemProperty,
			Date activeDate, Date targetDate, String labelKeys) throws MospException;
	
	/**
	 * 人事汎用一覧情報の項目が別々のデータの場合、
	 * フォーマットに合わせ合体させたデータを取得する。
	 * 人事汎用項目データ型がある場合の
	 * 通常・履歴・一覧情報をマップにデータを詰める場合に使用する。
	 * @param personalId 個人ID
	 * @param itemName 項目名
	 * @param itemProperty 人事汎用項目設定情報
	 * @param rowId 行ID
	 * @param targetDate 対象日
	 * @param labelKeys 人事汎用表示項目キー
	 * @return 項目名の値を合体し、フォーマットに変換した項目値
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	String getSeparateTxtItemArrayValue(String personalId, String itemName, ItemProperty itemProperty, String rowId,
			Date targetDate, String labelKeys) throws MospException;
	
	/**
	 * 人事汎用情報のエクスポート、インポート用の項目配列を作成する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用表示区分
	 * @return 項目配列(項目コード+表示項目キー)
	 */
	String[][] getPulldownForHumanExportImport(String division, String viewKey);
	
	/**
	 * 項目名、個人IDから項目名の日付を取得する。
	 * @param itemName 項目名
	 * @param personalId 個人ID
	 * @return 日付
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	Date humanNormalDate(String itemName, String personalId) throws MospException;
	
}
