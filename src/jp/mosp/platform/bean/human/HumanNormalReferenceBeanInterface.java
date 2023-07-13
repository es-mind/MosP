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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;

/**
 * 人事汎用通常情報参照インターフェース
 */
public interface HumanNormalReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 変数に共通情報を設定する。
	 * 人事汎用項目区分設定情報、人事汎用項目情報リストを取得し、
	 * 人事通常情報汎用マップを初期化する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 */
	void setCommounInfo(String division, String viewKey);
	
	/**
	 * 人事汎用通常情報をマップで取得する。
	 * 日付や電話番号などは一つのマップにまとめて詰める。
	 * プルダウンの場合、プルダウン名で取得する。
	 * 人事情報一覧画面など表示する際に使用する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate プルダウン有効日
	 * @param targetDate 対象日
	 * @return 人事汎用通常情報マップ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	Map<String, String> getShowHumanNormalMapInfo(String division, String viewKey, String personalId, Date activeDate,
			Date targetDate) throws MospException;
	
	/**
	 * 人事汎用通常情報取得。
	 * <p>
	 * 人事汎用管理区分・個人IDより情報を生成する。
	 * </p>
	 * @param itemName 人事汎用管理項目
	 * @param personalId 個人ID
	 * @return 人事汎用通常情報DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HumanNormalDtoInterface getHumanNormalInfo(String itemName, String personalId) throws MospException;
	
	/**
	 * 人事汎用通常情報取得。
	 * <p>
	 * 項目値から人事汎用通常情報を取得する。<br>
	 * @param itemName 項目名
	 * @param itemValue 項目値
	 * @return 人事汎用通常情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<HumanNormalDtoInterface> findForInfoForValue(String itemName, String itemValue) throws MospException;
	
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
	 * 人事汎用項目キーから値を設定表示形式で取得する。
	 * インポート・エクスポートに使用する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用表示区分
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param activeDate プルダウン有効日
	 * @param tableItemKey 人事汎用項目キー
	 * @param isPulldownName プルダウン名要否確認(true：プルダウン名、false：コード)
	 * @return 設定表示形式値
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	String getNormalItemValue(String division, String viewKey, String personalId, Date targetDate, Date activeDate,
			String tableItemKey, boolean isPulldownName) throws MospException;
	
	/**
	 * 人事汎用情報のエクスポート、インポート用の項目配列を作成する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用表示区分
	 * @return 項目配列(項目コード+表示項目キー)
	 */
	String[][] getPulldownForHumanExportImport(String division, String viewKey);
	
	/**
	 * 人事汎用通常DTO情報をマップで取得する。
	 * 日付や電話番号などは一つのマップにまとめて詰める。
	 * プルダウンの場合、プルダウン名で取得する。
	 * 人事情報一覧画面など表示する際に使用する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate プルダウン有効日
	 * @param isPulldownName プルダウン設定
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void getHumanNormalRecordMapInfo(String division, String viewKey, String personalId, Date activeDate,
			boolean isPulldownName) throws MospException;
	
	/**
	 * レコード識別IDマップ取得
	 * @return マップ（項目ID、レコード識別ID）
	 */
	LinkedHashMap<String, Long> getRecordsMap();
	
	/**
	 * 人事通常情報マップ取得
	 * @return マップ（項目ID、項目値）
	 */
	Map<String, String> getNormalMap();
}
