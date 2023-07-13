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
package jp.mosp.framework.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.IndexedDtoInterface;

/**
 * MosP設定情報(ロール)。<br>
 */
public class RoleProperty implements IndexedDtoInterface, BaseProperty {
	
	/**
	 * キー。
	 */
	private String							key;
	
	/**
	 * ロール名称。
	 */
	private String							roleName;
	
	/**
	 * ロール区分。<br>
	 * デフォルトは空白。<br>
	 * <br>
	 * 追加ロールを利用する際にパッケージやアドオンを識別する文字列を設定する。<br>
	 * 空白はメインロールを、空白以外は追加ロールを意味する。<br>
	 * <br>
	 */
	private String							roleType;
	
	/**
	 * ロール追加情報。<br>
	 * カンマ区切で複数設定することができる。<br>
	 */
	private String							roleExtra;
	
	/**
	 * ロールメニュー設定情報群。
	 */
	private Map<String, RoleMenuProperty>	roleMenuMap;
	
	/**
	 * ロール実行可能コマンドリスト。<br>
	 */
	private List<String>					acceptCmdList;
	
	/**
	 * ロール実行不能コマンドリスト。<br>
	 */
	private List<String>					rejectCmdList;
	
	/**
	 * ロール表示順。<br>
	 * アカウントマスタ画面等のプルダウン表示順として用いられる。<br>
	 */
	private int								viewIndex;
	
	/**
	 * ロール拡張権限。<br>
	 * アドオン機能等で独自に設定して用いることを想定している。<br>
	 */
	private String							roleAuthority;
	
	/**
	 * ロール拡張担当業務。<br>
	 * アドオン機能等で独自に設定して用いることを想定している。<br>
	 */
	private String							roleCharge;
	
	/**
	 * 人事汎用管理区分非表示リスト。
	 */
	private List<String>					hiddenDivisionsList;
	
	/**
	 * 人事汎用管理区分参照リスト。
	 */
	private List<String>					referenceDivisionsList;
	
	
	/**
	 * MosPロール情報を生成する。
	 * @param key      キー
	 */
	public RoleProperty(String key) {
		this.key = key;
		roleName = "";
		roleType = "";
		roleExtra = "";
		roleMenuMap = new HashMap<String, RoleMenuProperty>();
		acceptCmdList = new ArrayList<String>();
		rejectCmdList = new ArrayList<String>();
		roleAuthority = "";
		roleCharge = "";
		hiddenDivisionsList = new ArrayList<String>();
		referenceDivisionsList = new ArrayList<String>();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * ロール名称を取得する。
	 * @return ロール名称
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * ロール名称を設定する。
	 * @param roleName ロール名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	/**
	 * ロール区分を設定する。<br>
	 * @return ロール区分
	 */
	public String getRoleType() {
		return roleType;
	}
	
	/**
	 * ロール区分を設定する。<br>
	 * @param roleType ロール区分
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	/**
	 * ロール追加情報を取得する。
	 * @return ロール追加情報
	 */
	public String getRoleExtra() {
		return roleExtra;
	}
	
	/**
	 * ロール追加情報を設定する。
	 * @param roleExtra ロール追加情報
	 */
	public void setRoleExtra(String roleExtra) {
		this.roleExtra = roleExtra;
	}
	
	/**
	 * ロールメニュー設定情報群を取得する。
	 * @return ロールメニュー設定情報群
	 */
	public Map<String, RoleMenuProperty> getRoleMenuMap() {
		return roleMenuMap;
	}
	
	/**
	 * ロール実行可能コマンドリストを取得する。
	 * @return ロール実行可能コマンドリスト
	 */
	public List<String> getAcceptCmdList() {
		return acceptCmdList;
	}
	
	/**
	 * ロール実行不能コマンドリストを取得する。
	 * @return ロール実行不能コマンドリスト
	 */
	public List<String> getRejectCmdList() {
		return rejectCmdList;
	}
	
	/**
	 * ロール表示順を取得する。
	 * @return ロール表示順
	 */
	@Override
	public int getIndex() {
		return viewIndex;
	}
	
	/**
	 * ロール表示順を設定する。
	 * @param viewIndex ロール表示順
	 */
	public void setViewIndex(int viewIndex) {
		this.viewIndex = viewIndex;
	}
	
	/**
	 * ロール拡張権限を取得する。
	 * @return ロール拡張権限
	 */
	public String getRoleAuthority() {
		return roleAuthority;
	}
	
	/**
	 * ロール拡張権限を設定する。
	 * @param roleAuthority ロール拡張権限
	 */
	public void setRoleAuthority(String roleAuthority) {
		this.roleAuthority = roleAuthority;
	}
	
	/**
	 * ロール拡張担当業務を取得する。
	 * @return roleCharge ロール拡張担当業務
	 */
	public String getRoleCharge() {
		return roleCharge;
	}
	
	/**
	 * ロール拡張担当業務を設定する。
	 * @param roleCharge ロール拡張担当業務
	 */
	public void setRoleCharge(String roleCharge) {
		this.roleCharge = roleCharge;
	}
	
	/**
	 * 人事汎用管理区分非表示リストを取得する
	 * @return 人事汎用管理区分非表示リスト
	 */
	public List<String> getHiddenDivisionsList() {
		return hiddenDivisionsList;
	}
	
	/**
	 * 人事汎用管理区分参照リストを取得する
	 * @return 人事汎用管理区分参照リスト
	 */
	public List<String> getReferenceDivisionsList() {
		return referenceDivisionsList;
	}
	
}
