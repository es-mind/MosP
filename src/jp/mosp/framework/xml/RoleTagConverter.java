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
package jp.mosp.framework.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.RangeProperty;
import jp.mosp.framework.property.RoleMenuProperty;
import jp.mosp.framework.property.RoleProperty;
import jp.mosp.framework.utils.MospUtility;

/**
 * MosP設定情報(ロール)を作成する。<br>
 */
public class RoleTagConverter implements TagConverterInterface {
	
	/**
	 * ロール要素の下位要素名(ロール名称)。
	 */
	private static final String	TAG_ROLE_NAME					= "RoleName";
	
	/**
	 * ロール要素の下位要素名(ロール区分)。
	 */
	private static final String	TAG_ROLE_TYPE					= "RoleType";
	
	/**
	 * ロール要素の下位要素名(ロール追加情報)。
	 */
	private static final String	TAG_ROLE_EXTRA					= "RoleExtra";
	
	/**
	 * ロール要素の下位要素名(表示順)。<br>
	 */
	private static final String	TAG_VIEW_INDEX					= "ViewIndex";
	
	/**
	 * ロール要素の下位要素名(ロール拡張権限)。<br>
	 */
	private static final String	TAG_ROLE_AUTHORITY				= "RoleAuthority";
	
	/**
	 * ロール要素の下位要素名(ロール拡張担当業務)。<br>
	 */
	private static final String	TAG_ROLE_CHARGE					= "RoleCharge";
	
	/**
	 * ロール要素の下位要素名(ロール実行可能コマンド)。
	 */
	private static final String	TAG_ROLE_ACCEPT_COMMAND			= "RoleAcceptCommand";
	
	/**
	 * ロール要素の下位要素名(ロール実行不能コマンド)。
	 */
	private static final String	TAG_ROLE_REJECT_COMMAND			= "RoleRejectCommand";
	
	/**
	 * ロール要素の下位要素名(ロール実行可能コマンド除去)。
	 */
	private static final String	TAG_ROLE_ACCEPT_REMOVE			= "RoleAcceptRemove";
	
	/**
	 * ロール要素の下位要素名(ロール実行不能コマンド除去)。
	 */
	private static final String	TAG_ROLE_REJECT_REMOVE			= "RoleRejectRemove";
	
	/**
	 * ロール要素の下位要素名(メニュー要素)。
	 */
	private static final String	TAG_MENU						= "Menu";
	
	/**
	 * ロール要素の下位要素名(ロールメニュー除去)。
	 */
	private static final String	TAG_ROLE_MENU_REMOVE			= "RoleMenuRemove";
	
	/**
	 * ロール要素の下位要素名(ロール無効フラグ)。
	 */
	private static final String	TAG_ROLE_INVALID				= "RoleInvalid";
	
	/**
	 * インデックス要素名。
	 */
	private static final String	TAG_INDEX						= "Index";
	
	/**
	 * ロールメニュー要素の下位要素名(レンジ要素)。
	 */
	private static final String	TAG_RANGE						= "Range";
	
	/**
	 * レンジ要素の下位要素名(勤務地範囲要素)。
	 */
	private static final String	TAG_WORK_PLACE					= "WorkPlace";
	
	/**
	 * レンジ要素の下位要素名(雇用契約範囲要素)。
	 */
	private static final String	TAG_EMPLOYMENT_CONTRACT			= "EmploymentContract";
	
	/**
	 * レンジ要素の下位要素名(所属範囲要素)。
	 */
	private static final String	TAG_SECTION						= "Section";
	
	/**
	 * レンジ要素の下位要素名(職位範囲要素)。
	 */
	private static final String	TAG_POSITION					= "Position";
	
	/**
	 * レンジ要素の下位要素名(社員範囲要素)。
	 */
	private static final String	TAG_EMPLOYEE					= "Employee";
	
	private String				path;
	
	/**
	 * ロール要素の下位要素名(人事汎用管理非表示)。
	 */
	private static final String	TAG_ROLE_HIDDEN_DIVISIONS		= "RoleHiddenDivisions";
	/**
	 * ロール要素の下位要素名(人事汎用管理参照権限)。
	 */
	private static final String	TAG_ROLE_REFERENCE_DIVISIONS	= "RoleReferenceDivisions";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// Role
		Node node = wrapper.getNode();
		int index = wrapper.index;
		path = wrapper.path;
		
		// キー情報取得
		String key = TagUtility.getKey(node);
		// キー情報確認
		if (key.isEmpty()) {
			// エラーログ出力
			TagUtility.noElementKeyMessage(path, node, index);
			return;
		}
		
		// 要素数確認
		Number number = TagUtility.getNumber("count(*)", node);
		if (number.intValue() == 0) {
			// エラーログ出力
			TagUtility.invalidMassage(path, node);
			return;
		}
		BaseProperty baseProperty = properties.get(key);
		// ロール設定情報確認
		if (baseProperty == null) {
			// メインメニュー設定情報追加
			baseProperty = new RoleProperty(key);
		}
		// ロール設定情報取得
		RoleProperty property = (RoleProperty)baseProperty;
		// ロールメニュー設定情報群取得
		Map<String, RoleMenuProperty> roleMenuMap = property.getRoleMenuMap();
		// ロール実行可能コマンドリスト取得
		List<String> acceptCmdList = property.getAcceptCmdList();
		// ロール実行不能コマンドリスト取得
		List<String> rejectCmdList = property.getRejectCmdList();
		// 人事汎用管理区分非表示リスト取得
		List<String> hiddenDivisionsList = property.getHiddenDivisionsList();
		// 人事汎用管理区分参照リスト取得
		List<String> referenceDivisionsList = property.getReferenceDivisionsList();
		
		// ロール無効フラグ準備
		boolean roleInvalid = false;
		NodeList list = node.getChildNodes();
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node item = list.item(itemIndex);
			
			// ロール名称設定
			if (TagUtility.isTag(item, TAG_ROLE_NAME)) {
				property.setRoleName(TagUtility.trimText(item));
			}
			// ロール区分設定
			if (TagUtility.isTag(item, TAG_ROLE_TYPE)) {
				property.setRoleType(TagUtility.trimText(item));
			}
			// ロール追加情報設定
			if (TagUtility.isTag(item, TAG_ROLE_EXTRA)) {
				property.setRoleExtra(TagUtility.trimText(item));
			}
			// ロール表示順情報設定
			if (TagUtility.isTag(item, TAG_VIEW_INDEX)) {
				try {
					property.setViewIndex(Integer.parseInt(TagUtility.trimText(item)));
				} catch (NumberFormatException e) {
					// エラーログ出力
					TagUtility.invalidItemMassage(path, node, TAG_VIEW_INDEX, index);
				}
			}
			// ロール拡張権限設定
			if (TagUtility.isTag(item, TAG_ROLE_AUTHORITY)) {
				property.setRoleAuthority(TagUtility.trimText(item));
			}
			// ロール拡張担当業務設定
			if (TagUtility.isTag(item, TAG_ROLE_CHARGE)) {
				property.setRoleCharge(TagUtility.trimText(item));
			}
			// ロール無効フラグ設定
			if (TagUtility.isTag(item, TAG_ROLE_INVALID)) {
				roleInvalid = Boolean.parseBoolean(TagUtility.trimText(item));
			}
			// ロール実行可能コマンド設定
			if (TagUtility.isTag(item, TAG_ROLE_ACCEPT_COMMAND)) {
				// ロール実行可能コマンド追加
				acceptCmdList.add(TagUtility.trimText(item));
			}
			if (TagUtility.isTag(item, TAG_ROLE_REJECT_COMMAND)) {
				// ロール実行可能コマンド追加
				rejectCmdList.add(TagUtility.trimText(item));
			}
			if (TagUtility.isTag(item, TAG_ROLE_ACCEPT_REMOVE)) {
				// ロール実行可能コマンド除去
				acceptCmdList.remove(TagUtility.trimText(item));
			}
			if (TagUtility.isTag(item, TAG_ROLE_REJECT_REMOVE)) {
				// ロール実行可能コマンド除去
				rejectCmdList.remove(TagUtility.trimText(item));
			}
			if (TagUtility.isTag(item, TAG_ROLE_HIDDEN_DIVISIONS)) {
				// 人事汎用管理区分非表示リスト
				hiddenDivisionsList.addAll(MospUtility
					.asList(MospUtility.split(TagUtility.trimText(item), MospConst.APP_PROPERTY_SEPARATOR)));
			}
			if (TagUtility.isTag(item, TAG_ROLE_REFERENCE_DIVISIONS)) {
				// 人事汎用管理区分参照リスト
				referenceDivisionsList.addAll(MospUtility
					.asList(MospUtility.split(TagUtility.trimText(item), MospConst.APP_PROPERTY_SEPARATOR)));
			}
			
			// メニュー設定情報
			if (TagUtility.isTag(item, TAG_MENU)) {
				RoleMenuProperty menu = toRoleMenuProperty(item);
				if (menu == null) {
					// エラーログ出力
					TagUtility.invalidItemMassage(path, node, TAG_MENU, itemIndex);
				} else {
					// メニュー設定情報追加
					roleMenuMap.put(menu.getKey(), menu);
				}
			}
			// メニュー設定除去情報
			if (TagUtility.isTag(item, TAG_ROLE_MENU_REMOVE)) {
				// メニュー設定除去
				removeRoleMenu(TagUtility.trimText(item), roleMenuMap);
			}
			itemIndex++;
		}
		// ロール設定情報追加
		properties.put(key, property);
		// ロール無効フラグ確認
		if (roleInvalid) {
			// ロール設定情報削除
			properties.remove(key);
		}
	}
	
	/**
	 * MosPロールメニュー設定情報を取得する。<br>
	 * @param item      ノード
	 * @return MosPロールメニュー設定情報
	 */
	protected RoleMenuProperty toRoleMenuProperty(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		Integer index = null;
		Map<String, RangeProperty> rangeMap = new HashMap<String, RangeProperty>();
		
		NodeList list = item.getChildNodes();
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node menuItem = list.item(itemIndex);
			
			// インデックス取得
			if (TagUtility.isTag(menuItem, TAG_INDEX)) {
				try {
					index = Integer.parseInt(TagUtility.trimText(menuItem));
				} catch (NumberFormatException e) {
					// エラーログ出力
					TagUtility.invalidItemMassage(path, item, TAG_INDEX, itemIndex);
				}
			}
			// レンジ設定情報取得
			if (TagUtility.isTag(menuItem, TAG_RANGE)) {
				RangeProperty range = toRangeProperty(menuItem);
				if (range == null) {
					// エラーログ出力
					TagUtility.invalidItemMassage(path, item, TAG_RANGE, itemIndex);
				} else {
					rangeMap.put(range.getKey(), range);
				}
			}
			itemIndex++;
		}
		return new RoleMenuProperty(key, index, rangeMap);
	}
	
	/**
	 * ロールメニュー設定情報群から、ロールメニューを除去する。<br>
	 * @param keys        除去メニューキー(カンマ区切)
	 * @param roleMenuMap ロールメニュー設定情報群
	 */
	protected void removeRoleMenu(String keys, Map<String, RoleMenuProperty> roleMenuMap) {
		// 除去メニューキー確認
		if (keys == null) {
			return;
		}
		// 除去メニューキー配列取得
		String[] keyArray = MospUtility.split(keys, MospConst.APP_PROPERTY_SEPARATOR);
		// ロールメニュー設定情報群から除去
		for (String key : keyArray) {
			roleMenuMap.remove(key);
		}
	}
	
	/**
	 * 範囲設定情報を取得する。<br>
	 * @param item ノード
	 * @return 範囲設定情報
	 */
	protected RangeProperty toRangeProperty(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		// 情報取得準備
		String operationType = key;
		String workPlace = null;
		String employmentContract = null;
		String section = null;
		String position = null;
		String employee = null;
		
		NodeList list = item.getChildNodes();
		int index = 0;
		int length = list.getLength();
		while (index < length) {
			// ノード取得
			Node rangeItem = list.item(index);
			// 勤務地範囲取得
			if (TagUtility.isTag(rangeItem, TAG_WORK_PLACE)) {
				workPlace = TagUtility.trimText(rangeItem);
			}
			// 雇用契約範囲取得
			if (TagUtility.isTag(rangeItem, TAG_EMPLOYMENT_CONTRACT)) {
				employmentContract = TagUtility.trimText(rangeItem);
			}
			// 所属範囲取得
			if (TagUtility.isTag(rangeItem, TAG_SECTION)) {
				section = TagUtility.trimText(rangeItem);
			}
			// 職位範囲取得
			if (TagUtility.isTag(rangeItem, TAG_POSITION)) {
				position = TagUtility.trimText(rangeItem);
			}
			// 社員範囲取得
			if (TagUtility.isTag(rangeItem, TAG_EMPLOYEE)) {
				employee = TagUtility.trimText(rangeItem);
			}
			index++;
		}
		
		return new RangeProperty(operationType, workPlace, employmentContract, section, position, employee);
	}
	
}
