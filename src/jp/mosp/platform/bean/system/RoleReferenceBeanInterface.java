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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 	ロール参照処理。<br>
 */
public interface RoleReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 追加ロールが利用可能である場合は、メインロール(ロール区分：空白)の
	 * プルダウン用配列を取得する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @param viewCode   コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, boolean viewCode) throws MospException;
	
	/**
	 * 追加ロールのプルダウン用配列群(キー：ロール区分)を取得する。<br>
	 * 利用可能ロール区分につき、取得する。<br>
	 * ロール区分インデックス昇順。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @param viewCode  コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列群(キー：ロール区分)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, String[][]> getExtraRoleArrays(Date targetDate, boolean needBlank, boolean viewCode)
			throws MospException;
	
	/**
	 * プルダウン用配列(全て)を取得する。<br>
	 * getSelectArrayとgetExtraRoleArraysを足したもの。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @param viewCode  コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getAllArrays(Date targetDate, boolean needBlank, boolean viewCode) throws MospException;
	
	/**
	 * 利用可能ロールコード群を取得する。<br>
	 * ロール区分に関係なく、全てのロールコードを含める。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 利用可能ロールコードリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<String> getAvailabeRoleCodes(Date targetDate) throws MospException;
	
	/**
	 * 利用可能ロールコード群を取得する。<br>
	 * 指定ロール区分のロールコードを対象とする。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @param roleType   ロール区分
	 * @return 利用可能ロールコードリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<String> getAvailabeRoleCodes(Date targetDate, String roleType) throws MospException;
	
	/**
	 * プルダウン用配列(ロール区分)を取得する。<br>
	 * @param targetDate 対象日
	 * @return プルダウン用配列(ロール区分)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getRoleTypeSelectArray(Date targetDate) throws MospException;
	
	/**
	 * 利用可能ロール区分リスト(インデックス昇順)を取得する。<br>
	 * @param targetDate 対象日
	 * @return 利用可能ロール区分リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<String> getAvailableRoleTypes(Date targetDate) throws MospException;
	
	/**
	 * 追加ロールが利用可能であるかを確認する。<br>
	 * 利用可能ロール区分の要素が存在するかどうかで判断する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 確認結果(true：追加ロールが利用可能である、false：そうでない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isExtraRolesAvailable(Date targetDate) throws MospException;
	
}
