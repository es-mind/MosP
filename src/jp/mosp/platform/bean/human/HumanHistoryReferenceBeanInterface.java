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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;

/**
 * 人事汎用履歴情報参照クラス。
 */
public interface HumanHistoryReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 変数に共通情報を設定する。
	 * 人事汎用項目区分設定情報、人事汎用項目情報リストを取得し、
	 * 人事有効日履歴情報汎用マップ、人事履歴情報汎用マップを初期化する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 */
	void setCommounInfo(String division, String viewKey);
	
	/**
	 * 人事汎用履歴情報を取得する。<br>
	 * 個人ID・人事項目区分と有効日から人事汎用履歴情報を取得する。<br>
	 * 合致する情報が存在しない場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param humanItemType 人事項目区分
	 * @param activateDate 有効日
	 * @return 人事汎用履歴情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanHistoryDtoInterface findForKey(String personalId, String humanItemType, Date activateDate)
			throws MospException;
	
	/**
	 * 人事汎用履歴情報を取得する。<br>
	 * 個人IDと人事項目区分が合致する情報のうち、有効日が対象日以前で最新の情報を取得する。<br>
	 * 合致する情報が存在しない場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param humanItemType 人事項目区分
	 * @param targetDate 対象日
	 * @return 人事汎用履歴情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	HumanHistoryDtoInterface findForInfo(String personalId, String humanItemType, Date targetDate) throws MospException;
	
	/**
	 * 人事汎用履歴情報リストを取得する。<br>
	 * 個人ID・人事項目区分(人事汎用項目)から人事汎用履歴情報リストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param personalId 個人ID
	 * @param humanItemType 人事項目区分
	 * @return 人事汎用履歴情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanHistoryDtoInterface> findForHistory(String personalId, String humanItemType) throws MospException;
	
	/**
	 * 人事汎用対象プルダウン情報群を取得する。<br>
	 * 人事汎用通常・履歴・一覧画面のプルダウンをマップで取得する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用表示区分
	 * @param activeDate 有効日
	 * @return 人事汎用対象プルダウン情報群
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	Map<String, String[][]> getHumanGeneralPulldown(String division, String viewKey, Date activeDate)
			throws MospException;
	
	/**
	 * 人事汎用履歴情報をマップで取得する。<br>
	 * 個人IDと人事項目区分、有効日が全て合致する情報を取得する。<br>
	 * プルダウンの値をコードで取得する。<br>
	 * 履歴登録・履歴編集に利用する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @return 人事汎用履歴情報マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	LinkedHashMap<String, Map<String, String>> getHistoryMapInfo(String division, String viewKey, String personalId,
			Date activeDate) throws MospException;
	
	/**
	 * 人事汎用履歴情報をマップで取得する。<br>
	 * 個人IDと人事項目区分が合致する情報のうち、有効日が対象日以前で最新の情報を取得する。<br>
	 * プルダウンの値を名称で取得する。<br>
	 * 人事一覧表示に利用する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @param targetDate 対象日
	 * @return 人事汎用履歴情報マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	LinkedHashMap<String, Map<String, String>> getHumanHistoryMapInfo(String division, String viewKey,
			String personalId, Date activeDate, Date targetDate) throws MospException;
	
	/**
	 * 人事汎用履歴情報をマップで取得する。<br>
	 * 個人IDと人事項目区分が合致する情報のうち、有効日が対象日以前で最新の情報を取得する。<br>
	 * プルダウンの値をコードで取得する。<br>
	 * 人事履歴追加の際、前情報の表示に利用する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @param targetDate 対象日
	 * @return 人事汎用履歴情報マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, String> getBeforeHumanHistoryMapInfo(String division, String viewKey, String personalId,
			Date activeDate, Date targetDate) throws MospException;
	
	/**
	 * 有効日から人事汎用履歴情報をマップで取得する。<br>
	 * 個人ID・人事項目区分(人事汎用項目)から
	 * 有効日の昇順で並べられた人事汎用履歴情報リストを取得する。<br>
	 * プルダウンの値を名称で取得する。<br>
	 * 人事汎用履歴一覧表示に利用する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void getActiveDateHistoryMapInfo(String division, String viewKey, String personalId, Date targetDate)
			throws MospException;
	
	/**
	 * 対象項目キーからの値を設定された表示形式で取得する。
	 * インポート・エクスポートで仕様する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param tableItemKey 人事汎用項目キー
	 * @param isPulldownName プルダウン(true：名称、false：コード)
	 * @return 表示形式値
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getHistoryItemValue(String division, String viewKey, String personalId, Date targetDate, String tableItemKey,
			boolean isPulldownName) throws MospException;
	
	/**
	 * 降順で有効日の配列を取得する。<br>
	 * 人事汎用履歴一覧表示に利用する。<br>
	 * @param activeDateHistoryMapInfo 有効日人事汎用履歴情報マップ
	 * @return 有効日配列
	 */
	String[] getArrayActiveDate(LinkedHashMap<String, Map<String, String>> activeDateHistoryMapInfo);
	
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
	 * 人事汎用履歴情報をマップで取得する。<br>
	 * 個人IDと人事項目区分が合致する情報のうち、有効日が対象日以前で最新の情報を取得する。<br>
	 * プルダウンの値をコードで取得する。<br>
	 * 人事履歴追加の際、前情報の表示に利用する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void getHistoryRecordMapInfo(String division, String viewKey, String personalId, Date activeDate)
			throws MospException;
	
	/**
	 * レコード識別IDマップ取得
	 * @return レコード識別IDマップ（K:項目ID,V:レコード識別ID）
	 */
	LinkedHashMap<String, Long> getRecordsMap();
	
	/**
	 * 人事汎用履歴情報マップ取得
	 * @return 人事履歴情報マップ（K:有効日,V:項目ID,項目値）
	 */
	LinkedHashMap<String, Map<String, String>> getHistoryHumanInfoMap();
	
	/**
	 * 人事汎用履歴レコード識別IDマップ取得<br>
	 * 履歴一覧にて使用
	 * @return レコード識別IDマップ（K:有効日,V:項目ID,レコード識別ID）
	 */
	LinkedHashMap<String, LinkedHashMap<String, Long>> getHistoryReferenceInfoMap();
	
	/**
	 * 人事汎用履歴情報を画面表示用に再加工
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void getHistoryListData(String division, String viewKey) throws MospException;
	
}
