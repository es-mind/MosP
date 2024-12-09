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
package jp.mosp.platform.bean.system.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;

/**
 * ロール参照処理。<br>
 * <br>
 * ロールのマスタ管理機能を想定して各メソッドに対象日を付けているが、
 * 当クラスではXMLから取得するため対象日は利用しない。<br>
 * <br>
 */
public class RoleReferenceBean extends PlatformBean implements RoleReferenceBeanInterface {
	
	/**
	 * コードキー(ロール区分)。<br>
	 */
	protected static final String CODE_KEY_ROLE_TYPE = "RoleType";
	
	
	/**
	 * コンストラクタ。
	 */
	public RoleReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean viewCode) throws MospException {
		// ロール区分を準備
		String roleType = null;
		// 追加ロールが利用可能である場合
		if (isExtraRolesAvailable(targetDate)) {
			// ロール区分にメインロール(空白)を設定
			roleType = RoleUtility.ROLE_TYPE_MAIN;
		}
		// ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)を取得
		Map<String, String> roles = RoleUtility.getRoles(mospParams, roleType);
		// プルダウン用配列を取得
		return getSelectArray(roles, false, viewCode);
	}
	
	@Override
	public Map<String, String[][]> getExtraRoleArrays(Date targetDate, boolean needBlank, boolean viewCode)
			throws MospException {
		// 追加ロールのプルダウン用配列群(キー：ロール区分)を準備
		Map<String, String[][]> map = new LinkedHashMap<String, String[][]>();
		// 利用可能ロール区分リスト(インデックス昇順)を取得
		List<String> roleTypes = getAvailableRoleTypes(targetDate);
		// 利用可能ロール区分毎に処理
		for (String roleType : roleTypes) {
			// ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)を取得
			Map<String, String> roles = RoleUtility.getRoles(mospParams, roleType);
			// 追加ロールのプルダウン用配列を取得し設定
			map.put(roleType, getSelectArray(roles, needBlank, viewCode));
		}
		// 追加ロールのプルダウン用配列群(キー：ロール区分)を取得
		return map;
	}
	
	@Override
	public String[][] getAllArrays(Date targetDate, boolean needBlank, boolean viewCode) throws MospException {
		// プルダウン用配列を取得
		String[][] selectArray = getSelectArray(targetDate, viewCode);
		// 追加ロールのプルダウン用配列群(キー：ロール区分)を取得
		Map<String, String[][]> extraRoleArrays = getExtraRoleArrays(targetDate, false, viewCode);
		// リストを準備
		List<String[]> list = new ArrayList<String[]>();
		// プルダウン用配列を追加
		list.addAll(Arrays.asList(selectArray));
		// 追加ロールのプルダウン用配列毎(ロール区分インデックス昇順)に処理
		for (String[][] extraRoleArray : extraRoleArrays.values()) {
			// プルダウン用配列を追加
			list.addAll(Arrays.asList(extraRoleArray));
		}
		// プルダウン用配列を取得
		return list.toArray(new String[list.size()][2]);
	}
	
	@Override
	public Set<String> getAvailabeRoleCodes(Date targetDate) throws MospException {
		// ロール群(キー：ロールコード、値:ロール名称)を取得
		Map<String, String> all = RoleUtility.getRoles(mospParams, null);
		// 利用可能ロールコード群を取得
		return all.keySet();
	}
	
	@Override
	public Set<String> getAvailabeRoleCodes(Date targetDate, String roleType) throws MospException {
		// ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)を取得
		Map<String, String> roles = RoleUtility.getRoles(mospParams, roleType);
		// 利用可能ロールコード群を取得
		return roles.keySet();
	}
	
	@Override
	public String[][] getRoleTypeSelectArray(Date targetDate) throws MospException {
		// プルダウン用配列(ロール区分)を取得
		return MospUtility.getCodeArray(mospParams, CODE_KEY_ROLE_TYPE, false);
	}
	
	@Override
	public List<String> getAvailableRoleTypes(Date targetDate) throws MospException {
		return MospUtility.getCodeList(mospParams, CODE_KEY_ROLE_TYPE, false);
	}
	
	@Override
	public boolean isExtraRolesAvailable(Date targetDate) throws MospException {
		// 利用可能ロール区分の要素が存在するかどうかで判断
		return getAvailableRoleTypes(targetDate).isEmpty() == false;
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * @param roles     ロール群(キー：ロールコード、値:ロール名称)(インデックス昇順)
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @param viewCode  コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(Map<String, String> roles, boolean needBlank, boolean viewCode)
			throws MospException {
		// 一覧件数確認
		if (roles == null || roles.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// コード最大長取得
		int length = getMaxCodeLength(roles.keySet(), viewCode);
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(roles.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// プルダウン用配列作成
		for (Entry<String, String> entry : roles.entrySet()) {
			// コード及び名称を取得
			String code = entry.getKey();
			String name = entry.getValue();
			// コード表示によって名称を変更
			name = viewCode ? getCodedName(code, name, length) : name;
			// コード及び名称を設定
			array[idx][0] = code;
			array[idx++][1] = name;
		}
		return array;
	}
	
	/**
	 * ロールコード群におけるコード最大文字数を取得する。<br>
	 * @param roleCodes ロールコード群
	 * @param viewCode  コード表示(true：コード表示、false：コード非表示)
	 * @return ロール情報リストにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(Set<String> roleCodes, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (String roleCode : roleCodes) {
			if (roleCode.length() > length) {
				length = roleCode.length();
			}
		}
		return length;
	}
	
}
