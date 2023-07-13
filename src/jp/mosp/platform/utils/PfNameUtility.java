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
package jp.mosp.platform.utils;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.platform.constant.PlatformConst;

/**
 * 名称に関するユーティリティクラス。<br>
 * 
 * プラットフォームにおいて作成される名称は、
 * 全てこのクラスを通じて作成される。<br>
 * <br>
 */
public class PfNameUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PfNameUtility() {
		// 処理無し
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 人事管理
	 */
	public static String menuHumanManage(MospParams mospParams) {
		return mospParams.getName("menuHumanManage");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユーザ
	 */
	public static String user(MospParams mospParams) {
		return mospParams.getName("User");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象ユーザ
	 */
	public static String targetUser(MospParams mospParams) {
		return mospParams.getName("Target", "User");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユーザID
	 */
	public static String userId(MospParams mospParams) {
		return mospParams.getName("User", "Id");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return パスワード
	 */
	public static String password(MospParams mospParams) {
		return mospParams.getName("Password");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return アカウント
	 */
	public static String account(MospParams mospParams) {
		return mospParams.getName("Account");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 個人基本情報
	 */
	public static String personalBasisInfo(MospParams mospParams) {
		return mospParams.getName("PersonalBasisInformation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 変更日
	 */
	public static String changeDate(MospParams mospParams) {
		return mospParams.getName("Change", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 住所情報
	 */
	public static String addressInfo(MospParams mospParams) {
		return mospParams.getName("Address", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 住所区分
	 */
	public static String addressType(MospParams mospParams) {
		return mospParams.getName("Address", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 郵便番号
	 */
	public static String postalCode(MospParams mospParams) {
		return mospParams.getName("PostalCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 都道府県
	 */
	public static String prefecture(MospParams mospParams) {
		return mospParams.getName("Prefecture");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 市区町村
	 */
	public static String city(MospParams mospParams) {
		return mospParams.getName("City");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 番地
	 */
	public static String streetAddress(MospParams mospParams) {
		return mospParams.getName("StreetAddress");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 建物
	 */
	public static String building(MospParams mospParams) {
		return mospParams.getName("Building");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 電話情報
	 */
	public static String phoneInfo(MospParams mospParams) {
		return mospParams.getName("Phone", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 電話区分
	 */
	public static String phoneType(MospParams mospParams) {
		return mospParams.getName("Phone", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 電話番号
	 */
	public static String phoneNumber(MospParams mospParams) {
		return mospParams.getName("Phone", "Number");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return コード
	 */
	public static String code(MospParams mospParams) {
		return mospParams.getName("Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員
	 */
	public static String employee(MospParams mospParams) {
		return mospParams.getName("Employee");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象社員
	 */
	public static String targetEmployee(MospParams mospParams) {
		return mospParams.getName("Target", "Employee");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員選択
	 */
	public static String selectEmployee(MospParams mospParams) {
		return mospParams.getName("Employee", "Select");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員コード
	 */
	public static String employeeCode(MospParams mospParams) {
		return mospParams.getName("EmployeeCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員名
	 */
	public static String employeeName(MospParams mospParams) {
		return mospParams.getName("Employee", "FirstName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認者職位等級範囲
	 */
	public static String approverPositionGrade(MospParams mospParams) {
		return mospParams.getName("ApproverPositionGrade");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 範囲
	 */
	public static String range(MospParams mospParams) {
		return mospParams.getName("Range");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 、
	 */
	public static String touten(MospParams mospParams) {
		return mospParams.getName("Touten");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 空白
	 */
	public static String blank(MospParams mospParams) {
		return mospParams.getName("Blank");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 自動設定
	 */
	public static String autoNumbering(MospParams mospParams) {
		return mospParams.getName("AutoNumbering");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効
	 */
	public static String effective(MospParams mospParams) {
		return mospParams.getName("Effectiveness");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有
	 */
	public static String exsistAbbr(MospParams mospParams) {
		return mospParams.getName("EffectivenessExistence");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 無
	 */
	public static String notExsistAbbr(MospParams mospParams) {
		return mospParams.getName("InactivateExistence");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 名
	 */
	public static String firstName(MospParams mospParams) {
		return mospParams.getName("FirstName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 姓
	 */
	public static String lastName(MospParams mospParams) {
		return mospParams.getName("LastName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return カナ
	 */
	public static String kana(MospParams mospParams) {
		return mospParams.getName("Kana");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 名(カナ)
	 */
	public static String firstNameKana(MospParams mospParams) {
		return new StringBuilder(firstName(mospParams)).append(parentheses(mospParams, kana(mospParams))).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 姓(カナ)
	 */
	public static String lastNameKana(MospParams mospParams) {
		return new StringBuilder(lastName(mospParams)).append(parentheses(mospParams, kana(mospParams))).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 氏名
	 */
	public static String fullName(MospParams mospParams) {
		return mospParams.getName("FullName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 氏名(カナ)
	 */
	public static String fullNameKana(MospParams mospParams) {
		return new StringBuilder(fullName(mospParams)).append(parentheses(mospParams, kana(mospParams))).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ：
	 */
	public static String colon(MospParams mospParams) {
		return NameUtility.colon(mospParams);
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return :
	 */
	public static String singleColon(MospParams mospParams) {
		return mospParams.getName("SingleColon");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return .
	 */
	public static String period(MospParams mospParams) {
		return mospParams.getName("Period");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 期間
	 */
	public static String term(MospParams mospParams) {
		return mospParams.getName("Term");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象期間
	 */
	public static String targetTerm(MospParams mospParams) {
		return mospParams.getName("Target", "Term");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 表示期間
	 */
	public static String displayTerm(MospParams mospParams) {
		return mospParams.getName("Display", "Term");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索年月
	 */
	public static String searchYearMonth(MospParams mospParams) {
		return mospParams.getName("Search", "Year", "Month");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索区分
	 */
	public static String searchType(MospParams mospParams) {
		return mospParams.getName("Search", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索
	 */
	public static String search(MospParams mospParams) {
		return mospParams.getName("Search");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索条件
	 */
	public static String searchConditions(MospParams mospParams) {
		return mospParams.getName("Search", "Conditions");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員検索
	 */
	public static String searchEmployee(MospParams mospParams) {
		return mospParams.getName("Employee", "Search");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象検索
	 */
	public static String targetSearch(MospParams mospParams) {
		return mospParams.getName("Target", "Search");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員一覧
	 */
	public static String employeeList(MospParams mospParams) {
		return mospParams.getName("Employee", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効日
	 */
	public static String activateDate(MospParams mospParams) {
		return mospParams.getName("ActivateDate");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効日(年)
	 */
	public static String activateDateYear(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(activateDate(mospParams));
		return sb.append(parentheses(mospParams, year(mospParams))).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効日(月)
	 */
	public static String activateDateMonth(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(activateDate(mospParams));
		return sb.append(parentheses(mospParams, month(mospParams))).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効日(日)
	 */
	public static String activateDateDay(MospParams mospParams) {
		StringBuilder sb = new StringBuilder(activateDate(mospParams));
		return sb.append(parentheses(mospParams, day(mospParams))).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効/無効
	 */
	public static String inactivate(MospParams mospParams) {
		return mospParams.getName("Effectiveness", "Slash", "Inactivate");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有/無
	 */
	public static String inactivateAbbr(MospParams mospParams) {
		return mospParams.getName("EffectivenessExistence", "Slash", "InactivateExistence");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効/無効切替
	 */
	public static String switchInactivate(MospParams mospParams) {
		return mospParams.getName("Effectiveness", "Slash", "Inactivate", "Switching");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 年
	 */
	public static String year(MospParams mospParams) {
		return mospParams.getName("Year");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 月
	 */
	public static String month(MospParams mospParams) {
		return mospParams.getName("Month");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 日
	 */
	public static String day(MospParams mospParams) {
		return mospParams.getName("Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 年月日
	 */
	public static String yearMonthDay(MospParams mospParams) {
		// 年月日文字列を準備
		StringBuilder sb = new StringBuilder(year(mospParams));
		sb.append(month(mospParams));
		sb.append(day(mospParams));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時
	 */
	public static String hour(MospParams mospParams) {
		return mospParams.getName("Hour");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 分
	 */
	public static String minutes(MospParams mospParams) {
		return mospParams.getName("Minutes");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 週間
	 */
	public static String amountWeek(MospParams mospParams) {
		return mospParams.getName("AmountWeek");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ヶ月
	 */
	public static String amountMonth(MospParams mospParams) {
		return mospParams.getName("AmountMonth");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 決定
	 */
	public static String decision(MospParams mospParams) {
		return mospParams.getName("Decision");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 変更
	 */
	public static String change(MospParams mospParams) {
		return mospParams.getName("Change");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 選択
	 */
	public static String select(MospParams mospParams) {
		return mospParams.getName("Select");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 全選択
	 */
	public static String selectAll(MospParams mospParams) {
		return mospParams.getName("All", "Select");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 選択項目
	 */
	public static String selectItem(MospParams mospParams) {
		return mospParams.getName("Select", "Item");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 新規登録
	 */
	public static String newInsert(MospParams mospParams) {
		return mospParams.getName("New", "Insert");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 登録
	 */
	public static String insert(MospParams mospParams) {
		return mospParams.getName("Insert");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 更新
	 */
	public static String update(MospParams mospParams) {
		return mospParams.getName("Update");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 一括更新
	 */
	public static String batchUpdate(MospParams mospParams) {
		return mospParams.getName("Bulk", "Update");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 編集
	 */
	public static String edit(MospParams mospParams) {
		return mospParams.getName("Edit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴追加
	 */
	public static String addHistory(MospParams mospParams) {
		return mospParams.getName("History", "Add");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴編集
	 */
	public static String edtiHistory(MospParams mospParams) {
		return mospParams.getName("History", "Edit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 複製
	 */
	public static String replication(MospParams mospParams) {
		return mospParams.getName("Replication");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 出力
	 */
	public static String output(MospParams mospParams) {
		return mospParams.getName("Output");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 実行
	 */
	public static String execution(MospParams mospParams) {
		return mospParams.getName("Execution");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 削除
	 */
	public static String delete(MospParams mospParams) {
		return mospParams.getName("Delete");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 削除フラグ
	 */
	public static String deleteFlag(MospParams mospParams) {
		return mospParams.getName("Delete", "Flag");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴削除
	 */
	public static String deleteHistory(MospParams mospParams) {
		return mospParams.getName("History", "Delete");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴参照
	 */
	public static String referHistory(MospParams mospParams) {
		return mospParams.getName("History", "Reference");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴
	 */
	public static String history(MospParams mospParams) {
		return mospParams.getName("History");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 確認
	 */
	public static String confirmation(MospParams mospParams) {
		return mospParams.getName("Confirmation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート適用
	 */
	public static String routeApplication(MospParams mospParams) {
		return mospParams.getName("Route", "Apply");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート適用コード
	 */
	public static String routeApplicationCode(MospParams mospParams) {
		return mospParams.getName("Route", "Apply", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 適用コード
	 */
	public static String applicationCode(MospParams mospParams) {
		return mospParams.getName("Apply", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート適用名称
	 */
	public static String routeApplicationName(MospParams mospParams) {
		return mospParams.getName("Route", "Apply", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 適用名称
	 */
	public static String applicationName(MospParams mospParams) {
		return mospParams.getName("Apply", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート適用範囲
	 */
	public static String routeApplicationRange(MospParams mospParams) {
		return mospParams.getName("Route", "Apply", "Range");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート適用一覧
	 */
	public static String routeApplicationList(MospParams mospParams) {
		return mospParams.getName("Route", "Apply", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return フロー区分
	 */
	public static String workflowType(MospParams mospParams) {
		return mospParams.getName("Flow", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 被承認者社員コード
	 */
	public static String approvedEmployeeCode(MospParams mospParams) {
		return mospParams.getName("Suffer", "Approver", "EmployeeCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 被承認者社員指名
	 */
	public static String approvedName(MospParams mospParams) {
		return mospParams.getName("Suffer", "Approver", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート
	 */
	public static String route(MospParams mospParams) {
		return mospParams.getName("Route");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルートコード
	 */
	public static String routeCode(MospParams mospParams) {
		return mospParams.getName("Route", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート名称
	 */
	public static String routeName(MospParams mospParams) {
		return mospParams.getName("Route", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 階層数
	 */
	public static String stageNumber(MospParams mospParams) {
		return mospParams.getName("Hierarchy", "Num");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認階層
	 */
	public static String approvalCount(MospParams mospParams) {
		return mospParams.getName("Approval", "Hierarchy");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート設定
	 */
	public static String routeSetting(MospParams mospParams) {
		return mospParams.getName("Route", "Set");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ルート一覧
	 */
	public static String routeList(MospParams mospParams) {
		return mospParams.getName("Route", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 一次ユニット
	 */
	public static String firstUnit(MospParams mospParams) {
		return mospParams.getName("No1", "Following", "WorkflowUnit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 最終ユニット
	 */
	public static String lastUnit(MospParams mospParams) {
		return mospParams.getName("Finality", "WorkflowUnit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユニット
	 */
	public static String unit(MospParams mospParams) {
		return mospParams.getName("WorkflowUnit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユニットコード
	 */
	public static String unitCode(MospParams mospParams) {
		return mospParams.getName("WorkflowUnit", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユニット名称
	 */
	public static String unitName(MospParams mospParams) {
		return mospParams.getName("WorkflowUnit", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユニット区分
	 */
	public static String unitType(MospParams mospParams) {
		return mospParams.getName("WorkflowUnit", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ユニット一覧
	 */
	public static String unitList(MospParams mospParams) {
		return mospParams.getName("WorkflowUnit", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認者
	 */
	public static String approver(MospParams mospParams) {
		return mospParams.getName("Approver");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認者所属
	 */
	public static String approverSection(MospParams mospParams) {
		return mospParams.getName("Approver", "Section");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認者職位
	 */
	public static String approverPosition(MospParams mospParams) {
		return mospParams.getName("Approver", "Position");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認者社員コード
	 */
	public static String approverEmployeeCode(MospParams mospParams) {
		return mospParams.getName("Approver", "EmployeeCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認者氏名
	 */
	public static String approverName(MospParams mospParams) {
		return mospParams.getName("Approver", "FullName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認者設定
	 */
	public static String approverSetting(MospParams mospParams) {
		return mospParams.getName("Approver", "Set");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param stege      階層
	 * @return n次承認者
	 */
	public static String stagedApprover(MospParams mospParams, int stege) {
		StringBuilder sb = new StringBuilder();
		sb.append(stege);
		sb.append(mospParams.getName("Following", "Approver"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 最終承認者
	 */
	public static String lastApprover(MospParams mospParams) {
		return mospParams.getName("Finality", "Approver");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理承認者
	 */
	public static String substituteApprover(MospParams mospParams) {
		return mospParams.getName("Substitution", "Approver");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理
	 */
	public static String substitute(MospParams mospParams) {
		return mospParams.getName("Substitution");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理開始日
	 */
	public static String substituteStartDate(MospParams mospParams) {
		return mospParams.getName("Substitution", "Start", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理終了日
	 */
	public static String substituteEndDate(MospParams mospParams) {
		return mospParams.getName("Substitution", "End", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理人
	 */
	public static String subApprover(MospParams mospParams) {
		return mospParams.getName("SubApprover");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理人氏名
	 */
	public static String subApproverName(MospParams mospParams) {
		return mospParams.getName("SubApprover", "FullName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理人所属
	 */
	public static String subApproverSection(MospParams mospParams) {
		return mospParams.getName("SubApprover", "Section");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理人職位
	 */
	public static String subApproverPosition(MospParams mospParams) {
		return mospParams.getName("SubApprover", "Position");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 代理人社員コード
	 */
	public static String subApproverEmployeeCode(MospParams mospParams) {
		return mospParams.getName("SubApprover", "EmployeeCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認
	 */
	public static String approval(MospParams mospParams) {
		return mospParams.getName("Approval");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認ルート
	 */
	public static String approvalRoute(MospParams mospParams) {
		return mospParams.getName("Approval", "Route");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 下書
	 */
	public static String draft(MospParams mospParams) {
		return mospParams.getName("WorkPaper");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 差戻
	 */
	public static String reverted(MospParams mospParams) {
		return mospParams.getName("SendingBack");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 取下
	 */
	public static String withdraw(MospParams mospParams) {
		return mospParams.getName("TakeDown");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承解除
	 */
	public static String cancelApprovalAbbr(MospParams mospParams) {
		return mospParams.getName("ApprovalRelease");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 解除取下
	 */
	public static String cancelWithdraw(MospParams mospParams) {
		return mospParams.getName("Release", "TakeDown");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 未承認
	 */
	public static String notApproved(MospParams mospParams) {
		return mospParams.getName("Ram", "Approval");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 解除申
	 */
	public static String canelApplyAbbr(MospParams mospParams) {
		return mospParams.getName("Release", "Register");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param stage      段階
	 * @return n次済
	 */
	public static String stageApproved(MospParams mospParams, int stage) {
		return new StringBuilder().append(stage).append(mospParams.getName("Following", "Finish")).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param stage      段階
	 * @return n次戻
	 */
	public static String stageReverted(MospParams mospParams, int stage) {
		return new StringBuilder().append(stage).append(mospParams.getName("Following", "Back")).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 追加
	 */
	public static String add(MospParams mospParams) {
		return mospParams.getName("Add");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 解除
	 */
	public static String release(MospParams mospParams) {
		return mospParams.getName("Release");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 全解除
	 */
	public static String releaseAll(MospParams mospParams) {
		return mospParams.getName("All", "Release");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認解除
	 */
	public static String cancelApproval(MospParams mospParams) {
		return mospParams.getName("Approval", "Release");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 行
	 */
	public static String row(MospParams mospParams) {
		return NameUtility.row(mospParams);
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ▲ページの先頭へ
	 */
	public static String topOfPage(MospParams mospParams) {
		return mospParams.getName("UpperTriangular", "TopOfPage");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 基本情報
	 */
	public static String basisInfo(MospParams mospParams) {
		return mospParams.getName("Basis", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 個人
	 */
	public static String personal(MospParams mospParams) {
		return mospParams.getName("Personal");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 最新
	 */
	public static String latest(MospParams mospParams) {
		return mospParams.getName("Latest");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ロール
	 */
	public static String role(MospParams mospParams) {
		return mospParams.getName("Role");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ロールコード
	 */
	public static String roleCode(MospParams mospParams) {
		return mospParams.getName("Role", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ロール区分
	 */
	public static String roleType(MospParams mospParams) {
		return mospParams.getName("Role", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 追加ロール
	 */
	public static String extraRole(MospParams mospParams) {
		return mospParams.getName("Add", "Role");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ロール選択
	 */
	public static String selectRole(MospParams mospParams) {
		return mospParams.getName("Role", "Select");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 人事情報
	 */
	public static String humanInfo(MospParams mospParams) {
		return mospParams.getName("HumanInfo", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 入社
	 */
	public static String entrance(MospParams mospParams) {
		return mospParams.getName("Joined");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 入社日
	 */
	public static String entranceDate(MospParams mospParams) {
		return mospParams.getName("Joined", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 入社情報
	 */
	public static String entranceInfo(MospParams mospParams) {
		return mospParams.getName("EntranceInformation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤続年数
	 */
	public static String serviceYears(MospParams mospParams) {
		return mospParams.getName("ServiceYears");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 在職
	 */
	public static String inService(MospParams mospParams) {
		return mospParams.getName("InService");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 退職
	 */
	public static String retirement(MospParams mospParams) {
		return mospParams.getName("Retirement");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 退職情報
	 */
	public static String retirementInfo(MospParams mospParams) {
		return mospParams.getName("RetirementInformation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 退職日
	 */
	public static String retirementDate(MospParams mospParams) {
		return mospParams.getName("Retirement", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 退職理由
	 */
	public static String retirementReason(MospParams mospParams) {
		return mospParams.getName("Retirement", "Reason");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 退職理由詳細
	 */
	public static String retirementReasonDetail(MospParams mospParams) {
		return mospParams.getName("Retirement", "Reason", "Detail");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休職
	 */
	public static String suspension(MospParams mospParams) {
		return mospParams.getName("Suspension");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休職情報
	 */
	public static String suspensionInfo(MospParams mospParams) {
		return mospParams.getName("SuspensionInformation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休職開始日
	 */
	public static String suspensionStartDate(MospParams mospParams) {
		return mospParams.getName("Suspension", "Start", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休職終了予定日
	 */
	public static String expectedSuspensionEndDate(MospParams mospParams) {
		return mospParams.getName("Suspension", "End", "Schedule", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休職終了日
	 */
	public static String suspensionEndDate(MospParams mospParams) {
		return mospParams.getName("Suspension", "End", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休職理由
	 */
	public static String suspensionReason(MospParams mospParams) {
		return mospParams.getName("SuspensionReason");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 理由詳細
	 */
	public static String reasonDetail(MospParams mospParams) {
		return mospParams.getName("ReasonDetail");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休退職
	 */
	public static String retirementAndSuspension(MospParams mospParams) {
		return mospParams.getName("RetirementAndSuspension");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 休退職区分
	 */
	public static String retirementAndSuspensionType(MospParams mospParams) {
		return mospParams.getName("RetirementAndSuspension", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地
	 */
	public static String workPlace(MospParams mospParams) {
		return mospParams.getName("WorkPlace");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地コード
	 */
	public static String workPlaceCode(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地名称
	 */
	public static String workPlaceName(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地略称
	 */
	public static String workPlaceAbbreviation(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "Abbreviation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地名称(カナ)
	 */
	public static String workPlaceNameKana(MospParams mospParams) {
		return new StringBuilder(workPlaceName(mospParams)).append(parentheses(mospParams, kana(mospParams)))
			.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地郵便番号
	 */
	public static String workPlacePostalCode(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "PostalCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地都道府県
	 */
	public static String workPlacePrefecture(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "Prefecture");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地市区町村
	 */
	public static String workPlaceCity(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "City");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地番地
	 */
	public static String workPlaceStreetAddress(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "StreetAddress");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地建物情報
	 */
	public static String workPlaceBuilding(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "Building", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地電話番号
	 */
	public static String workPlacePhoneNumber(MospParams mospParams) {
		return mospParams.getName("WorkPlace", "Phone", "Number");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 雇用契約
	 */
	public static String employmentContract(MospParams mospParams) {
		return mospParams.getName("EmploymentContract");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 雇用契約コード
	 */
	public static String employmentContractCode(MospParams mospParams) {
		return mospParams.getName("EmploymentContract", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 雇用契約名称
	 */
	public static String employmentContractName(MospParams mospParams) {
		return mospParams.getName("EmploymentContract", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 雇用契約略称
	 */
	public static String employmentContractAbbreviation(MospParams mospParams) {
		return mospParams.getName("EmploymentContract", "Abbreviation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 雇契
	 */
	public static String employmentContractAbbr(MospParams mospParams) {
		return mospParams.getName("EmploymentContractAbbr");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 雇契略称
	 */
	public static String employmentContractAbbrAbbreviation(MospParams mospParams) {
		return mospParams.getName("EmploymentContractAbbr", "Abbreviation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 雇用
	 */
	public static String employment(MospParams mospParams) {
		return mospParams.getName("Employment");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属
	 */
	public static String section(MospParams mospParams) {
		return mospParams.getName("Section");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属コード
	 */
	public static String sectionCode(MospParams mospParams) {
		return mospParams.getName("Section", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属名称
	 */
	public static String sectionName(MospParams mospParams) {
		return mospParams.getName("Section", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属略称
	 */
	public static String sectionAbbreviation(MospParams mospParams) {
		return mospParams.getName("Section", "Abbreviation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属表示名称
	 */
	public static String sectionDisplayName(MospParams mospParams) {
		return mospParams.getName("Section", "Display", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属階層
	 */
	public static String sectionClassRoute(MospParams mospParams) {
		return mospParams.getName("Section", "Hierarchy");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 上位所属
	 */
	public static String higherSection(MospParams mospParams) {
		return mospParams.getName("HigherOrder", "Section");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 上位所属選択
	 */
	public static String selectHigherSection(MospParams mospParams) {
		return mospParams.getName("HigherOrder", "Section", "Select");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索対象
	 */
	public static String searchTarget(MospParams mospParams) {
		return mospParams.getName("Search", "Target");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 所属情報
	 */
	public static String sectionInfo(MospParams mospParams) {
		return mospParams.getName("Section", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 職位
	 */
	public static String position(MospParams mospParams) {
		return mospParams.getName("Position");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 職位コード
	 */
	public static String positionCode(MospParams mospParams) {
		return mospParams.getName("Position", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 職位名称
	 */
	public static String positionName(MospParams mospParams) {
		return mospParams.getName("Position", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 職位略称
	 */
	public static String positionAbbreviation(MospParams mospParams) {
		return mospParams.getName("Position", "Abbreviation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 職位ランク
	 */
	public static String positionGrade(MospParams mospParams) {
		return mospParams.getName("PositionGrade");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 等級
	 */
	public static String positionLevel(MospParams mospParams) {
		return mospParams.getName("PositionLevel");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 号
	 */
	public static String positionLevelUnit(MospParams mospParams) {
		return mospParams.getName("PositionLevelUnit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 役職
	 */
	public static String post(MospParams mospParams) {
		return mospParams.getName("Post");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 職位情報
	 */
	public static String positionInfo(MospParams mospParams) {
		return mospParams.getName("Position", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 兼務
	 */
	public static String concurrent(MospParams mospParams) {
		return mospParams.getName("Concurrent");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 兼務情報
	 */
	public static String concurrentInfo(MospParams mospParams) {
		return mospParams.getName("ConcurrentInformation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 兼務開始日
	 */
	public static String concurrentStartDate(MospParams mospParams) {
		return mospParams.getName("Concurrent", "Start", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 兼務終了日
	 */
	public static String concurrentEndDate(MospParams mospParams) {
		return mospParams.getName("Concurrent", "End", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 現在の主担当
	 */
	public static String currentMainCharge(MospParams mospParams) {
		return mospParams.getName("PresentTime", "Of", "Mainly", "Charge");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メールアドレス
	 */
	public static String mailAddress(MospParams mospParams) {
		return mospParams.getName("MailAddress");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 現在のパスワード
	 */
	public static String currentPassrword(MospParams mospParams) {
		return mospParams.getName("PresentTime", "Of", "Password");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 新しいパスワード
	 */
	public static String newPassrword(MospParams mospParams) {
		return mospParams.getName("ItNew", "Password");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return パスワード入力確認
	 */
	public static String confirmPassrword(MospParams mospParams) {
		return mospParams.getName("Password", "Input", "Confirmation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return パスワード初期化
	 */
	public static String initPassrword(MospParams mospParams) {
		return mospParams.getName("Password", "Initialization");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ログイン
	 */
	public static String login(MospParams mospParams) {
		return mospParams.getName("Login");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ログアウト
	 */
	public static String logout(MospParams mospParams) {
		return mospParams.getName("Logout");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return エクスポート情報
	 */
	public static String exportInfo(MospParams mospParams) {
		return mospParams.getName("Export", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return エクスポートコード
	 */
	public static String exportCode(MospParams mospParams) {
		return mospParams.getName("Export", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return エクスポート名称
	 */
	public static String exportName(MospParams mospParams) {
		return mospParams.getName("Export", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return インポートコード
	 */
	public static String importCode(MospParams mospParams) {
		return mospParams.getName("Import", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return インポート名称
	 */
	public static String importName(MospParams mospParams) {
		return mospParams.getName("Import", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return データ区分
	 */
	public static String fileType(MospParams mospParams) {
		return mospParams.getName("FileType");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return データ型
	 */
	public static String dataClass(MospParams mospParams) {
		return mospParams.getName("DataClass");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ヘッダ有無
	 */
	public static String withOrWithoutHeader(MospParams mospParams) {
		return mospParams.getName("Header", "EffectivenessExistence", "InactivateExistence");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 登録データ
	 */
	public static String insertData(MospParams mospParams) {
		return mospParams.getName("Insert", "Data");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 情報
	 */
	public static String information(MospParams mospParams) {
		return mospParams.getName("Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 時間
	 */
	public static String time(MospParams mospParams) {
		return mospParams.getName("Time");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 画面
	 */
	public static String screen(MospParams mospParams) {
		return mospParams.getName("Screen");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 表示設定
	 */
	public static String displaySetting(MospParams mospParams) {
		return mospParams.getName("DisplaySetting");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 理由
	 */
	public static String reason(MospParams mospParams) {
		return mospParams.getName("Reason");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請
	 */
	public static String application(MospParams mospParams) {
		return mospParams.getName("Application");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請日
	 */
	public static String applicationDate(MospParams mospParams) {
		return mospParams.getName("Application", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申請情報
	 */
	public static String applicationInfo(MospParams mospParams) {
		return mospParams.getName("Application", "Information");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 承認済
	 */
	public static String completed(MospParams mospParams) {
		return mospParams.getName("Approval", "Finish");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 済
	 */
	public static String completedAbbr(MospParams mospParams) {
		return mospParams.getName("Finish");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 申
	 */
	public static String appliedAbbr(MospParams mospParams) {
		return mospParams.getName("Register");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 戻
	 */
	public static String revertedAbbr(MospParams mospParams) {
		return mospParams.getName("Back");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 下
	 */
	public static String draftAbbr(MospParams mospParams) {
		return mospParams.getName("Under");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 自己承認
	 */
	public static String selfApproval(MospParams mospParams) {
		return mospParams.getName("SelfApproval");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 予定
	 */
	public static String schedule(MospParams mospParams) {
		return mospParams.getName("Schedule");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 備考
	 */
	public static String remarks(MospParams mospParams) {
		return mospParams.getName("Remarks");
	}
	
	/**
	 * @param mospParams
	 * @return 出勤日
	 */
	public static String goingWorkDay(MospParams mospParams) {
		return mospParams.getName("GoingWork", "Day");
	}
	
	/**
	 * @param mospParams
	 * @return 人事
	 */
	public static String humanManageAbbr(MospParams mospParams) {
		return mospParams.getName("HumanInfo");
	}
	
	/**
	 * @param mospParams
	 * @return 勤怠
	 */
	public static String workManageAbbr(MospParams mospParams) {
		return mospParams.getName("WorkManage");
	}
	
	/**
	 * @param mospParams
	 * @return 勤務日
	 */
	public static String workDay(MospParams mospParams) {
		return mospParams.getName("Work", "Day");
	}
	
	/**
	 * @param mospParams
	 * @return 対象月
	 */
	public static String targetMonth(MospParams mospParams) {
		return mospParams.getName("Target", "Month");
	}
	
	/**
	 * @param mospParams
	 * @return 期限日
	 */
	public static String limitDay(MospParams mospParams) {
		return mospParams.getName("TimeLimit", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return カードID
	 */
	public static String cardId(MospParams mospParams) {
		return mospParams.getName("IcCardId");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ヘッダ
	 */
	public static String header(MospParams mospParams) {
		return mospParams.getName("Header");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return アプリケーション設定キー
	 */
	public static String appKey(MospParams mospParams) {
		return mospParams.getName("AppKey");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 情報一覧
	 */
	public static String dataList(MospParams mospParams) {
		return mospParams.getName("Information", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String message(MospParams mospParams) {
		return mospParams.getName("Message");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メッセージ区分
	 */
	public static String messageType(MospParams mospParams) {
		return mospParams.getName("Message", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メッセージNo
	 */
	public static String messageNo(MospParams mospParams) {
		return mospParams.getName("Message", "No");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メッセージタイトル
	 */
	public static String messageTitle(MospParams mospParams) {
		return mospParams.getName("Message", "Title");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 公開開始日
	 */
	public static String publishStartDate(MospParams mospParams) {
		return mospParams.getName("Open", "Start", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 公開終了日
	 */
	public static String publishEndDate(MospParams mospParams) {
		return mospParams.getName("Open", "End", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 開始日
	 */
	public static String startDate(MospParams mospParams) {
		return mospParams.getName("Start", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 終了日
	 */
	public static String endDate(MospParams mospParams) {
		return mospParams.getName("End", "Day");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 重要度
	 */
	public static String importance(MospParams mospParams) {
		return mospParams.getName("Importance");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 適用範囲
	 */
	public static String applyRange(MospParams mospParams) {
		return mospParams.getName("Apply", "Range");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 登録者
	 */
	public static String insertUser(MospParams mospParams) {
		return mospParams.getName("InsertUser");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 表示対象者氏名
	 */
	public static String publishedUserName(MospParams mospParams) {
		return mospParams.getName("Display", "TargetPerson", "FullName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メッセージ設定
	 */
	public static String messageSetting(MospParams mospParams) {
		return mospParams.getName("Message", "Set");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メッセージ一覧
	 */
	public static String messageList(MospParams mospParams) {
		return mospParams.getName("Message", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return タイトルを入力して下さい。
	 */
	public static String inputTitle(MospParams mospParams) {
		return mospParams.getName("Title", "InputSomething");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メッセージはありません。
	 */
	public static String noMessage(MospParams mospParams) {
		return mospParams.getName("Message", "IsNothing");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メッセージを入力して下さい。
	 */
	public static String inputMessage(MospParams mospParams) {
		return mospParams.getName("Message", "InputSomething");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 名称
	 */
	public static String name(MospParams mospParams) {
		return mospParams.getName("Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 名称区分
	 */
	public static String namingType(MospParams mospParams) {
		return mospParams.getName("Name", "Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 名称項目コード
	 */
	public static String namingItemCode(MospParams mospParams) {
		return mospParams.getName("Name", "Item", "Code");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 名称項目名称
	 */
	public static String namingItemName(MospParams mospParams) {
		return mospParams.getName("Name", "Item", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 名称項目略称
	 */
	public static String namingItemAbbreviation(MospParams mospParams) {
		return mospParams.getName("Name", "Item", "Abbreviation");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return テンプレート
	 */
	public static String template(MospParams mospParams) {
		return mospParams.getName("Template");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索ワード
	 */
	public static String searchWord(MospParams mospParams) {
		return mospParams.getName("SearchWord");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 詳細検索
	 */
	public static String detailSearch(MospParams mospParams) {
		return mospParams.getName("Detail", "Search");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 有効日を入力して下さい。
	 */
	public static String inputActiveDate(MospParams mospParams) {
		return mospParams.getName("InputActiveDate");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 開始日を入力して下さい。
	 */
	public static String inputStartDate(MospParams mospParams) {
		return mospParams.getName("InputStartDate");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return コードを検索して下さい。
	 */
	public static String searchCode(MospParams mospParams) {
		return mospParams.getName("SearchCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 検索して下さい。
	 */
	public static String searchPlease(MospParams mospParams) {
		return mospParams.getName("PleaseSearch");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 社員コードを入力して下さい。
	 */
	public static String inputEmployeeCode(MospParams mospParams) {
		return mospParams.getName("InputEmployeeCode");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return カンマ区切で入力
	 */
	public static String inputCsv(MospParams mospParams) {
		return mospParams.getName("InputCsv");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 【なし】 最上位部署として登録
	 */
	public static String registAsGreatest(MospParams mospParams) {
		return mospParams.getName("RegistAsGreatest");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ※エクスポートマスタを選択してから実行して下さい。
	 */
	public static String selectExportBeforeExecute(MospParams mospParams) {
		return mospParams.getName("SelectExportBeforeExecute");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 勤務地、雇用契約、所属、職位を全て未指定で登録することにより全体適用になります。
	 */
	public static String howToApplyAllRange(MospParams mospParams) {
		return mospParams.getName("HowToApplyAllRange");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return パスワードを忘れた場合
	 */
	public static String inCaseForgotPassword(MospParams mospParams) {
		return mospParams.getName("InCaseForgotPassword");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return マスタ
	 */
	public static String master(MospParams mospParams) {
		return mospParams.getName("Master");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ID
	 */
	public static String id(MospParams mospParams) {
		return mospParams.getName("Id");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 区分
	 */
	public static String type(MospParams mospParams) {
		return mospParams.getName("Type");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 番号
	 */
	public static String number(MospParams mospParams) {
		return mospParams.getName("Number");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return No
	 */
	public static String no(MospParams mospParams) {
		return mospParams.getName("No");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 再表示
	 */
	public static String refresh(MospParams mospParams) {
		return mospParams.getName("Refresh");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 参照
	 */
	public static String reference(MospParams mospParams) {
		return mospParams.getName("Reference");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 詳細
	 */
	public static String detail(MospParams mospParams) {
		return mospParams.getName("Detail");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 以降
	 */
	public static String onAndAfter(MospParams mospParams) {
		return mospParams.getName("Since");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 全
	 */
	public static String all(MospParams mospParams) {
		return mospParams.getName("All");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 歳
	 */
	public static String yearsOld(MospParams mospParams) {
		return mospParams.getName("YearsOld");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 赤
	 */
	public static String red(MospParams mospParams) {
		return mospParams.getName("Red");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 黄
	 */
	public static String yellow(MospParams mospParams) {
		return mospParams.getName("Yellow");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return -
	 */
	public static String hyphen(MospParams mospParams) {
		return mospParams.getName("Hyphen");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return /
	 */
	public static String slash(MospParams mospParams) {
		return mospParams.getName("Slash");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ※
	 */
	public static String signStar(MospParams mospParams) {
		return mospParams.getName("SignStar");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ファイル名称
	 */
	public static String fileName(MospParams mospParams) {
		return mospParams.getName("File", "Name");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象ファイル
	 */
	public static String targetFile(MospParams mospParams) {
		return mospParams.getName("Target", "File");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ファイル出力
	 */
	public static String outputFile(MospParams mospParams) {
		return mospParams.getName("File", "Output");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return メール送信
	 */
	public static String sendMail(MospParams mospParams) {
		return mospParams.getName("SendMail");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 行追加
	 */
	public static String addRow(MospParams mospParams) {
		return mospParams.getName("Row", "Add");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 情報登録
	 */
	public static String insertInfo(MospParams mospParams) {
		return mospParams.getName("Information", "Insert");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 情報追加
	 */
	public static String addInfo(MospParams mospParams) {
		return mospParams.getName("Information", "Add");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 情報編集
	 */
	public static String editInfo(MospParams mospParams) {
		return mospParams.getName("Information", "Edit");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 一覧
	 */
	public static String list(MospParams mospParams) {
		return mospParams.getName("List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 履歴一覧
	 */
	public static String historyList(MospParams mospParams) {
		return mospParams.getName("History", "List");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return の
	 */
	public static String of(MospParams mospParams) {
		return mospParams.getName("Of");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return (
	 */
	public static String frontParentheses(MospParams mospParams) {
		return mospParams.getName("FrontParentheses");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return )
	 */
	public static String backParentheses(MospParams mospParams) {
		return mospParams.getName("BackParentheses");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 【
	 */
	public static String frontWithCornerParentheses(MospParams mospParams) {
		return mospParams.getName("FrontWithCornerParentheses");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 】
	 */
	public static String backWithCornerParentheses(MospParams mospParams) {
		return mospParams.getName("BackWithCornerParentheses");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 対象データが存在しません。
	 */
	public static String noTargetData(MospParams mospParams) {
		return mospParams.getName("NoTargetData");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return を作成する場合、入力してください
	 */
	public static String inputAsMaking(MospParams mospParams) {
		return mospParams.getName("InputAsMaking");
	}
	
	/**
	 * @param mospParams       MosP処理情報
	 * @param activateDateMode 有効日モード
	 * @return 決定or変更
	 */
	public static String activeteDateButton(MospParams mospParams, String activateDateMode) {
		return activateDateMode.equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? change(mospParams)
				: decision(mospParams);
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param stage      段階
	 * @return n次
	 */
	public static String stage(MospParams mospParams, int stage) {
		return new StringBuilder().append(stage).append(mospParams.getName("Following")).toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param enclosed   カッコ内の文字列
	 * @return (enclosed)
	 */
	public static String parentheses(MospParams mospParams, String enclosed) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("FrontParentheses"));
		sb.append(enclosed);
		sb.append(mospParams.getName("BackParentheses"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param enclosed   カッコ内の文字列
	 * @return (enclosed)
	 */
	public static String squareParentheses(MospParams mospParams, String enclosed) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("LeftSquareBracket"));
		sb.append(enclosed);
		sb.append(mospParams.getName("RightSquareBracket"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param count 件数
	 * @return 全count件
	 */
	public static String allCount(MospParams mospParams, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("All"));
		sb.append(count);
		sb.append(mospParams.getName("Count"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param digit 桁数
	 * @return digit桁
	 */
	public static String digit(MospParams mospParams, int digit) {
		StringBuilder sb = new StringBuilder();
		sb.append(digit);
		sb.append(mospParams.getName("Digit"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return http://bbs.mosp.jp/forum/login_page.php
	 */
	public static String questionAndAnswerURI(MospParams mospParams) {
		return mospParams.getName("QuestionAndAnswerURI");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return Q&A
	 */
	public static String questionAndAnswer(MospParams mospParams) {
		return mospParams.getName("QuestionAndAnswer");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ▲
	 */
	public static String upperTriangular(MospParams mospParams) {
		return mospParams.getName("UpperTriangular");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ▼
	 */
	public static String lowerTriangular(MospParams mospParams) {
		return mospParams.getName("LowerTriangular");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return Copyright All Rights Reserved.
	 */
	public static String copyRight(MospParams mospParams) {
		return mospParams.getName("CopyRight");
	}
	
}
