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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.base.MospUser;
import jp.mosp.framework.comparator.IndexComparator;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.RangeProperty;
import jp.mosp.framework.property.RoleMenuProperty;
import jp.mosp.framework.property.RoleProperty;

/**
 * ロール及びログインユーザのロール操作に有用なメソッドを提供する。<br><br>
 */
public class RoleUtility {
	
	/**
	 * ロール区分(メインロール)。<br>
	 */
	public static final String		ROLE_TYPE_MAIN			= "";
	
	/**
	 * ロール追加情報(初期ロール)。<br>
	 * 社員新規登録で作成するユーザに付加するロールに設定することを想定している。<br>
	 * 初期ロールは、メインロール以外に追加ロールでも設定することができる。<br>
	 * 一つのロール区分に対して、一つのロールのみに設定する。<br>
	 * 一つのロール区分に対して複数設定した場合、
	 * ロールコードの自然順序付けで最初の要素のみが、初期ロールとして扱われる。<br>
	 */
	protected static final String	ROLE_EXTRA_DEFAULT		= "default";
	
	/**
	 * ロール追加情報(必須ロール)。<br>
	 * 特権ロールのような人事情報及びアカウント情報を扱えるロールに
	 * 設定することを想定している。<br>
	 * 必須ロールは、メインロールでのみ考慮する。<br>
	 * 一つのロールのみに設定する。<br>
	 * 複数設定した場合、
	 * ロールコードの自然順序付けで最初の要素のみが、必須ロールとして扱われる。<br>
	 */
	protected static final String	ROLE_EXTRA_NEEDED		= "needed";
	
	/**
	 * ロール追加情報(特権ロール)。<br>
	 */
	protected static final String	ROLE_EXTRA_SUPER		= "super";
	
	/**
	 * ロール追加情報(承認ロール)。<br>
	 */
	protected static final String	ROLE_EXTRA_APPROVER		= "approver";
	
	/**
	 * ロール追加情報(計算ロール)。<br>
	 */
	protected static final String	ROLE_EXTRA_CALCULATOR	= "calculator";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private RoleUtility() {
		// 処理無し
	}
	
	/**
	 * ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)を取得する。<br>
	 * <br>
	 * 対象のロール区分が設定されたロールのみを取得する。<br>
	 * ロール区分にnullを指定した場合は、全てのロールを取得する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param roleType   ロール区分
	 * @return ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)
	 */
	public static Map<String, String> getRoles(MospParams mospParams, String roleType) {
		// ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)を準備
		Map<String, String> map = new LinkedHashMap<String, String>();
		// MosP設定情報(ロール)リスト(インデックス昇順)を取得
		List<RoleProperty> list = getRoleProperties(mospParams, roleType);
		// MosP設定情報(ロール)毎に処理
		for (RoleProperty role : list) {
			// ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)に追加
			map.put(role.getKey(), role.getRoleName());
		}
		// ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)を取得
		return map;
	}
	
	/**
	 * ログインユーザのロールに対象のロールコードが設定されているかを確認する。<br>
	 * 追加ロールがあれば、それらのロールコードも考慮する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param roleCode  ロールコード
	 * @return 確認結果(true：設定されている、false：設定されていない)
	 */
	public static boolean isTheRoleCodeSet(MospParams mospParams, String roleCode) {
		// ログインユーザのロールコード群を取得
		Set<String> set = getUserRoleCodes(mospParams);
		// ログインユーザのロールコード群に存在しているかを確認
		return set.contains(roleCode);
	}
	
	/**
	 * ログインユーザのロールに対象のロール追加情報が設定されているかを確認する。<br>
	 * 追加ロールがあれば、それらのロール追加情報も考慮する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param roleExtra  ロール追加情報
	 * @return 確認結果(true：設定されている、false：設定されていない)
	 */
	public static boolean isTheRoleExtraSet(MospParams mospParams, String roleExtra) {
		// ログインユーザのロール追加情報群を取得
		Set<String> set = getUserRoleExtras(mospParams);
		// ログインユーザのロール追加情報群に存在しているかを確認
		return set.contains(roleExtra);
	}
	
	/**
	 * 対象ロールに対象のロール追加情報が設定されているかを確認する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param roleCode   ロールコード
	 * @param roleExtra  ロール追加情報
	 * @return 確認結果(true：設定されている、false：設定されていない)
	 */
	public static boolean isTheRoleExtraSet(MospParams mospParams, String roleCode, String roleExtra) {
		// 対象ロールのを取得
		RoleProperty role = getRoleProperty(mospParams, roleCode);
		// 対象ロールが取得できなかった場合
		if (role == null) {
			// 設定されていないと判断
			return false;
		}
		// 対象ロールのロール追加情報群に存在しているかを確認
		return MospUtility.asList(role.getRoleExtra(), MospConst.APP_PROPERTY_SEPARATOR).contains(roleExtra);
	}
	
	/**
	 * ログインユーザが特権ロールであるかを確認する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：特権ロールである、false：特権ロールでない)
	 */
	public static boolean isSuper(MospParams mospParams) {
		// ログインユーザのロールに特権ロールが設定されているかを確認
		return isTheRoleExtraSet(mospParams, ROLE_EXTRA_SUPER);
	}
	
	/**
	 * ログインユーザが承認ロールであるかを確認する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：承認ロールである、false：承認ロールでない)
	 */
	public static boolean isApprover(MospParams mospParams) {
		// ログインユーザのロールに特権ロールが設定されているかを確認
		return isTheRoleExtraSet(mospParams, ROLE_EXTRA_APPROVER);
	}
	
	/**
	 * ログインユーザが計算ロールであるかを確認する。<br>r>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：計算ロールである、false：計算ロールでない)
	 */
	public static boolean isCalculator(MospParams mospParams) {
		// ログインユーザのロールに特権ロールが設定されているかを確認
		return isTheRoleExtraSet(mospParams, ROLE_EXTRA_CALCULATOR);
	}
	
	/**
	 * ログインユーザのメニューキー群(インデックス昇順)を取得する。<br>
	 * ロールメニュー設定情報のインデックスでソートする。<br>
	 * <br>
	 * 追加ロールがあれば、それらのメニューも追加する。<br>
	 * 但し、複数のロールで同じメニューキーが存在する場合は一つに纏められる。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return メニューキー群(インデックス昇順)
	 */
	public static Set<String> getUserMenuKeys(MospParams mospParams) {
		// ロールメニュー設定情報リストを準備
		List<RoleMenuProperty> list = new ArrayList<RoleMenuProperty>();
		// ログインユーザのロール設定情報毎に処理
		for (RoleProperty roleProperty : getUserRoles(mospParams)) {
			// ロールメニュー設定情報リストにロールメニュー設定情報群を追加
			list.addAll(roleProperty.getRoleMenuMap().values());
		}
		// ロールメニュー設定情報のインデックスでソート
		Collections.sort(list, new IndexComparator());
		// メニューキー群(インデックス昇順)を準備
		Set<String> set = new LinkedHashSet<String>();
		// ロールメニュー設定情報毎に処理
		for (RoleMenuProperty roleMenu : list) {
			// メニューキー群に追加
			set.add(roleMenu.getKey());
		}
		// メニューキー群(インデックス昇順)を取得
		return set;
	}
	
	/**
	 * ログインユーザのロールから操作範囲設定情報群(キー：操作区分)を取得する。<br>
	 * 追加ロールがあれば、それらの操作範囲設定情報も考慮する。<br>
	 * <br>
	 * あるロールのある操作区分で操作範囲設定情報が設定されていなければ(操作範囲：全体)、
	 * 他のロールで設定されていても、それらは無視される。<br>
	 * <br>
	 * ある操作区分で複数操作範囲設定情報が設定されている場合、
	 * それらの全てを含む操作範囲を取得する。<br>
	 * <br>
	 * 元の(設定ファイルから取得した)操作範囲設定情報群自体は変更しない。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return 操作範囲設定情報群(キー：操作区分)
	 */
	public static Map<String, RangeProperty> getUserRanges(MospParams mospParams, String menuKey) {
		// 操作範囲設定情報群(キー：操作区分)を準備
		Map<String, RangeProperty> rangeMap = null;
		// ログインユーザのロール設定情報毎に処理
		for (RoleProperty roleProperty : getUserRoles(mospParams)) {
			// 対象メニューキーのロールメニュー設定情報を取得
			RoleMenuProperty menu = roleProperty.getRoleMenuMap().get(menuKey);
			// ロールメニュー設定情報が取得できなかった場合
			if (menu == null) {
				// 処理無し
				continue;
			}
			// 操作範囲設定情報群が作成されていない(最初の要素である)場合
			if (rangeMap == null) {
				// 操作範囲設定情報群を複製
				rangeMap = copyRangeMap(menu.getRangeMap());
				// 次のロールメニュー設定情報へ
				continue;
			}
			// 操作範囲設定情報群に追加ロールメニュー設定の範囲設定情報を追加
			addRangeMap(rangeMap, menu.getRangeMap());
		}
		// 操作範囲設定情報群(キー：操作区分)を取得
		return rangeMap;
	}
	
	/**
	 * ログインユーザのロール実行可能コマンドを確認する。<br>
	 * 実行不可コマンドは、コマンドでのみ、実行不可コマンドリストから検索する。<br>
	 * 実行不可コマンドリスト内にコマンドが含まれている場合、falseを返す。<br>
	 * 実行可能コマンドは、見つかるまでワイルドカードで置換して、
	 * 実行可能コマンドリストから検索する。<br>
	 * 実行可能コマンドリストに含まれていない場合、falseを返す。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param command    コマンド
	 * @return 確認結果(true：実行可能、false：実行不能)
	 */
	public static boolean hasAuthority(MospParams mospParams, String command) {
		// コマンドが指定されていない場合
		if (command == null || command.isEmpty()) {
			// 実行不能であると判断
			return false;
		}
		// ログインユーザのロール設定情報群を取得
		Set<RoleProperty> rolePropertys = getUserRoles(mospParams);
		// ログインユーザのロール設定情報毎に処理
		for (RoleProperty roleProperty : rolePropertys) {
			// 実行不可コマンドリストに含まれる場合
			if (roleProperty.getRejectCmdList().contains(command)) {
				// 実行不能であると判断
				return false;
			}
		}
		// ログインユーザのロール設定情報毎に処理
		for (RoleProperty roleProperty : rolePropertys) {
			// 実行可能コマンドリストを取得
			List<String> acceptCmdList = roleProperty.getAcceptCmdList();
			// 実行可能コマンドリストに含まれる場合
			if (acceptCmdList.contains(command)) {
				// 実行可能であると判断
				return true;
			}
			// コマンド末尾をワイルドカード化
			String wildCardCommand = MospUtility.getWildCardCommand(command);
			// 実行可能コマンドリスト確認
			while (wildCardCommand.isEmpty() == false) {
				// 実行可能コマンドリストに含まれる場合
				if (acceptCmdList.contains(wildCardCommand)) {
					// 実行可能であると判断
					return true;
				}
				// コマンド末尾をワイルドカード化
				wildCardCommand = MospUtility.getWildCardCommand(wildCardCommand);
			}
		}
		// 実行不能であると判断(実行可能コマンドリストに含まれていない場合)
		return false;
	}
	
	/**
	 * ログインユーザの人事汎用管理区分非表示リストを取得する。<br>
	 * 追加ロールがあれば、それらの人事汎用管理区分非表示リストも考慮する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return 人事汎用管理区分非表示リスト
	 */
	public static List<String> getHiddenDivisionsList(MospParams mospParams) {
		// 人事汎用管理区分非表示リストを準備
		List<String> list = new ArrayList<String>();
		// ログインユーザのロール設定情報毎に処理
		for (RoleProperty roleProperty : getUserRoles(mospParams)) {
			// 人事汎用管理区分非表示リストに各ロールのリストを追加
			list.addAll(roleProperty.getHiddenDivisionsList());
		}
		// 人事汎用管理区分非表示リストを取得
		return list;
	}
	
	/**
	 * ログインユーザの人事汎用管理区分参照リストを取得する。<br>
	 * 追加ロールがあれば、それらの人事汎用管理区分参照リストも考慮する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return 人事汎用管理区分参照リスト
	 */
	public static List<String> getReferenceDivisionsList(MospParams mospParams) {
		// 人事汎用管理区分参照リストを準備
		List<String> list = new ArrayList<String>();
		// ログインユーザのロール設定情報毎に処理
		for (RoleProperty roleProperty : getUserRoles(mospParams)) {
			// 人事汎用管理区分参照リストに各ロールのリストを追加
			list.addAll(roleProperty.getReferenceDivisionsList());
		}
		// 人事汎用管理区分参照リストを取得
		return list;
	}
	
	/**
	 * ロール追加情報に承認ロールが設定されているロールコード群を取得する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return 承認ロールコード群
	 */
	public static Set<String> getApproverRoles(MospParams mospParams) {
		// 承認ロールコード群を準備
		Set<String> set = new HashSet<String>();
		// ロールコード群(キー：ロール区分)を取得
		Map<String, Set<String>> approverRolesMap = getRoleExtraCodes(mospParams, ROLE_EXTRA_APPROVER);
		// ロールコード群毎に処理
		for (Set<String> approverRoles : approverRolesMap.values()) {
			// 承認ロールコード群に追加
			set.addAll(approverRoles);
		}
		// 承認ロールコード群を取得
		return set;
	}
	
	/**
	 * 必須ロールコードを取得する。<br>
	 * <br>
	 * 取得できなかった場合は、空文字を返す。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return 必須ロールコード
	 */
	public static String getNeededRole(MospParams mospParams) {
		// メインロールの必須ロールコード群(自然順序付け)を取得
		Set<String> set = getRoleExtraCodes(mospParams, ROLE_EXTRA_NEEDED).get(ROLE_TYPE_MAIN);
		// メインロールの必須ロールコード群が取得できなかった場合
		if (set == null || set.isEmpty()) {
			// 空文字を取得
			return "";
		}
		// メインロールの必須ロールコードを取得
		return set.iterator().next();
	}
	
	/**
	 * 対象ロール区分の初期ロールコードを取得する。<br>
	 * <br>
	 * 取得できなかった場合は、空文字を返す。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param roleType   ロール区分
	 * @return 初期ロールコード
	 */
	public static String getDefaultRole(MospParams mospParams, String roleType) {
		// 対象ロール区分の初期ロールコード群(自然順序付け)を取得
		Set<String> set = getRoleExtraCodes(mospParams, ROLE_EXTRA_DEFAULT).get(roleType);
		// 対象ロール区分の初期ロールコード(コレクションの最初の文字列)を取得
		return MospUtility.getFirstString(set);
	}
	
	/**
	 * メインロールの初期ロールコードを取得する。<br>
	 * <br>
	 * 取得できなかった場合は、空文字を返す。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return 初期ロールコード
	 */
	public static String getDefaultRole(MospParams mospParams) {
		// メインロールの初期ロールコードを取得
		return getDefaultRole(mospParams, ROLE_TYPE_MAIN);
	}
	
	/**
	 * メインロールの特権ロールコードを取得する。<br>
	 * <br>
	 * 取得できなかった場合は、空文字を返す。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return 初期ロールコード
	 */
	public static String getSuperRole(MospParams mospParams) {
		// メインロールの特権ロールコード群(自然順序付け)を取得
		Set<String> set = getRoleExtraCodes(mospParams, ROLE_EXTRA_SUPER).get(ROLE_TYPE_MAIN);
		// メインロールの特権ロールコード(コレクションの最初の文字列)を取得
		return MospUtility.getFirstString(set);
	}
	
	/**
	 * ログインユーザのロール設定情報群を取得する。<br>
	 * 追加ロールがあれば、それらのロール追加情報も考慮する。<br>
	 * <br>
	 * 最初の要素は、ログインユーザのメインロール。<br>
	 * 但し、ロール設定情報が取得できないロールはロール設定情報群に含めない。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return ロール設定情報群
	 */
	protected static Set<RoleProperty> getUserRoles(MospParams mospParams) {
		// ロール設定情報群(順序有)を準備
		Set<RoleProperty> set = new LinkedHashSet<RoleProperty>();
		// ログインユーザのロールコード毎に処理
		for (String roleCode : getUserRoleCodes(mospParams)) {
			// ロール設定情報を取得
			RoleProperty roleProperty = getRoleProperty(mospParams, roleCode);
			// ロール設定情報が取得できなかった場合
			if (roleProperty == null) {
				// 処理無し
				continue;
			}
			// ロール設定情報群に追加
			set.add(roleProperty);
		}
		// ロール設定情報群を取得
		return set;
	}
	
	/**
	 * ログインユーザのロールコード群を取得する。<br>
	 * 追加ロールがあれば、それらのロール追加情報も考慮する。<br>
	 * <br>
	 * 最初の要素は、ログインユーザのメインロール。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return ロールコード群
	 */
	protected static Set<String> getUserRoleCodes(MospParams mospParams) {
		// ロールコード群(順序有)を準備
		Set<String> set = new LinkedHashSet<String>();
		// ログインユーザ情報を取得
		MospUser user = mospParams.getUser();
		// ログインユーザ情報が取得できなかった場合
		if (user == null) {
			// 処理終了
			return set;
		}
		// ログインユーザのメインロールを設定
		set.add(user.getRole());
		// ログインユーザの追加ロールを設定
		set.addAll(user.getExtraRoles());
		// ロールコード群を取得
		return set;
	}
	
	/**
	 * ログインユーザのロール追加情報群を取得する。<br>
	 * 追加ロールがあれば、それらのロール追加情報も考慮する。<br>
	 * <br>
	 * ロール追加情報は、カンマ区切の文字列。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return ロール追加情報群
	 */
	protected static Set<String> getUserRoleExtras(MospParams mospParams) {
		// ロール追加情報群を準備
		Set<String> set = new HashSet<String>();
		// ログインユーザのロール設定情報群を取得
		Set<RoleProperty> roles = getUserRoles(mospParams);
		// ロール設定情報毎に処理
		for (RoleProperty role : roles) {
			// ロール追加情報群に対象ロールの情報を追加
			set.addAll(MospUtility.asList(role.getRoleExtra(), MospConst.APP_PROPERTY_SEPARATOR));
		}
		// ロール追加情報群を取得
		return set;
	}
	
	/**
	 * ロール設定情報を取得する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param roleCode   ロールコード
	 * @return ロール設定情報
	 */
	protected static RoleProperty getRoleProperty(MospParams mospParams, String roleCode) {
		// MosP処理情報からロール設定情報を取得
		return mospParams.getProperties().getRoleProperties().get(roleCode);
	}
	
	/**
	 * MosP設定情報(ロール)リスト(インデックス昇順)を取得する。<br>
	 * <br>
	 * 対象のロール区分が設定されたMosP設定情報(ロール)のみを取得する。<br>
	 * ロール区分にnullを指定した場合は、全てのMosP設定情報(ロール)を取得する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param roleType   ロール区分
	 * @return MosP設定情報(ロール)リスト(インデックス昇順)
	 */
	protected static List<RoleProperty> getRoleProperties(MospParams mospParams, String roleType) {
		// MosP設定情報(ロール)リストを準備
		List<RoleProperty> list = new ArrayList<RoleProperty>();
		// XMLから全ロール情報群を取得
		Map<String, RoleProperty> all = mospParams.getProperties().getRoleProperties();
		// ロール情報毎に処理
		for (RoleProperty roleProperty : all.values()) {
			// ロール区分がnullであるか一致する場合
			if (roleType == null || roleType.equals(roleProperty.getRoleType())) {
				// MosP設定情報(ロール)リストに追加
				list.add(roleProperty);
			}
		}
		// コード項目リストソート
		Collections.sort(list, new IndexComparator());
		// MosP設定情報(ロール)リストを取得
		return list;
	}
	
	/**
	 * 操作範囲設定情報群を複製する。<br>
	 * {@link RoleUtility#getUserRanges(MospParams, String)}参照。<br>
	 * <br>
	 * @param rangeMap 操作範囲設定情報群(複製元)
	 * @return 操作範囲設定情報群
	 */
	protected static Map<String, RangeProperty> copyRangeMap(Map<String, RangeProperty> rangeMap) {
		// 操作範囲設定情報群(複製先)を準備
		Map<String, RangeProperty> map = new HashMap<String, RangeProperty>();
		// 操作範囲設定情報(複製元)毎に処理
		for (Entry<String, RangeProperty> entry : rangeMap.entrySet()) {
			// 操作区分を取得
			String operationType = entry.getKey();
			// 操作範囲設定情報を取得
			RangeProperty value = entry.getValue();
			// 操作範囲設定情報に設定されている値を取得
			String workPlace = value.getWorkPlace();
			String employmentContract = value.getEmploymentContract();
			String section = value.getSection();
			String position = value.getPosition();
			String employee = value.getEmployee();
			// 操作範囲設定情報を複製
			RangeProperty range = new RangeProperty(operationType, workPlace, employmentContract, section, position,
					employee);
			// 操作範囲設定情報群(複製先)に設定
			map.put(operationType, range);
		}
		// 操作範囲設定情報群(複製先)を取得
		return map;
	}
	
	/**
	 * 操作範囲設定情報を操作範囲設定情報群に追加する。<br>
	 * {@link RoleUtility#getUserRanges(MospParams, String)}参照。<br>
	 * <br>
	 * @param rangeMap 操作範囲設定情報群
	 * @param extraMap 追加操作範囲設定情報群
	 */
	protected static void addRangeMap(Map<String, RangeProperty> rangeMap, Map<String, RangeProperty> extraMap) {
		// 操作区分毎に処理
		for (String operationType : getOperationTypes()) {
			// 操作範囲設定情報から対象操作区分の範囲設定情報を取得
			RangeProperty range = rangeMap.get(operationType);
			// 対象操作区分の範囲設定情報が設定されていない場合
			if (range == null) {
				// 処理終了(操作範囲全体と判断)
				continue;
			}
			// 追加操作範囲設定情報から対象操作区分の範囲設定情報を取得
			RangeProperty value = extraMap.get(operationType);
			// 対象操作区分の範囲設定情報が設定されていない場合
			if (value == null) {
				// 操作範囲全体と判断し操作範囲設定情報群から除去
				rangeMap.remove(operationType);
				// 処理終了
				continue;
			}
			// 操作範囲設定値(カンマ区切)を連結
			String workPlace = margeRange(range.getWorkPlace(), value.getWorkPlace());
			String employmentContract = margeRange(range.getEmploymentContract(), value.getEmploymentContract());
			String section = margeRange(range.getSection(), value.getSection());
			String position = margeRange(range.getPosition(), value.getPosition());
			String employee = margeRange(range.getEmployee(), value.getEmployee());
			// 連結した値で範囲設定情報を再作成
			range = new RangeProperty(operationType, workPlace, employmentContract, section, position, employee);
			// 範囲設定情報を設定
			rangeMap.put(operationType, range);
		}
	}
	
	/**
	 * 操作範囲設定値(カンマ区切)を連結する。<br>
	 * {@link RoleUtility#getUserRanges(MospParams, String)}参照。<br>
	 * <br>
	 * 操作範囲設定値は、カンマ区切で複数設定することができる。<br>
	 * 同じ操作範囲設定値があった場合は、一つのみ残す。<br>
	 * <br>
	 * @param values 操作範囲設定値(カンマ区切)配列
	 * @return 操作範囲設定値(カンマ区切)
	 */
	protected static String margeRange(String... values) {
		// 操作範囲設定値群を準備
		Set<String> set = new LinkedHashSet<String>();
		// 操作範囲設定値毎に処理
		for (String value : values) {
			// 操作範囲設定値(カンマ区切)を分割して操作範囲設定値群に追加(重複を除去)
			set.addAll(MospUtility.asList(value, MospConst.APP_PROPERTY_SEPARATOR));
		}
		// 操作範囲設定値(カンマ区切)を取得
		return MospUtility.toSeparatedString(set, MospConst.APP_PROPERTY_SEPARATOR);
	}
	
	/**
	 * 操作区分群を取得する。<br>
	 * 現状で使われている操作区分は、参照のみ。<br>
	 * 登録等別の操作区分が使われるようになったら、追加する。<br>
	 * <br>
	 * ・1：参照
	 * <br>
	 * @return 操作区分群
	 */
	protected static Set<String> getOperationTypes() {
		// 操作区分群を準備
		Set<String> set = new HashSet<String>();
		// 操作区分を追加
		set.add(MospConst.OPERATION_TYPE_REFER);
		// 操作区分群を取得
		return set;
	}
	
	/**
	 * 対象ロール追加情報が設定されているロールコード群(キー：ロール区分)を取得する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param roleExtra  ロール追加情報
	 * @return ロールコード群(キー：ロール区分、値：ロールコード群(自然順序付け))
	 */
	protected static Map<String, Set<String>> getRoleExtraCodes(MospParams mospParams, String roleExtra) {
		// ロールコード群(キー：ロール区分)を準備
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		// MosP設定情報(ロール)リスト(インデックス昇順)を取得
		List<RoleProperty> all = getRoleProperties(mospParams, null);
		// ロール情報毎に処理
		for (RoleProperty roleProperty : all) {
			// ロールコード及びロール区分を取得
			String roleCode = roleProperty.getKey();
			String roleType = roleProperty.getRoleType();
			// 対象ロールに対象のロール追加情報が設定されていない場合
			if (isTheRoleExtraSet(mospParams, roleCode, roleExtra) == false) {
				// 処理無し
				continue;
			}
			// 対象ロール区分のロールコード群を取得
			Set<String> set = map.get(roleType);
			// 対象ロール区分のロールコード群が取得できなかった場合
			if (set == null) {
				// 対象ロール区分のロールコード群(自然順序付け)を作成
				set = new TreeSet<String>();
				map.put(roleType, set);
			}
			// ロールコード群にロールコードを追加
			set.add(roleCode);
		}
		// ロールコード群(キー：ロール区分)
		return map;
	}
	
}
