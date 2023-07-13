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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザ追加ロール情報参照処理インターフェース。<br>
 */
public interface UserExtraRoleReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * ユーザIDと有効日が合致するユーザ追加ロール情報群を取得する。<br>
	 * ロール区分のインデックス順。<br>
	 * 利用可能なロール区分のロールのみを取得する。<br>
	 * <br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @return ユーザ追加ロール情報群(キー：ロール区分、値：ロールコード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, String> getUserExtraRoleMap(String userId, Date activateDate) throws MospException;
	
	/**
	 * ユーザIDと有効日が合致するユーザ追加ロールコード群を取得する。<br>
	 * ロール区分のインデックス順。<br>
	 * 利用可能なロール区分のロールのみを取得する。<br>
	 * <br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @return ユーザ追加ロールコード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<String> getUserExtraRoles(String userId, Date activateDate) throws MospException;
	
	/**
	 * 対象ユーザのロールに対象のロールコードが設定されているかを確認する。<br>
	 * 追加ロールがあれば、それらのロールコードも考慮する。<br>
	 * 但し、利用可能でないロール区分のロールは考慮しない。<br>
	 * <br>
	 * @param dto      ユーザマスタ情報
	 * @param roleCode ロールコード
	 * @return 確認結果(true：設定されている、false：設定されていない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isTheRoleCodeSet(UserMasterDtoInterface dto, String roleCode) throws MospException;
	
	/**
	 * 対象ユーザのロールに対象のロールコード群が設定されているかを確認する。<br>
	 * 追加ロールがあれば、それらのロールコードも考慮する。<br>
	 * 但し、利用可能でないロール区分のロールは考慮しない。<br>
	 * <br>
	 * 対象ユーザのロールのうち一つでも、ロールコード群に含まれていれば、
	 * 設定されていると判断する。<br>
	 * <br>
	 * @param dto       ユーザマスタ情報
	 * @param roleCodes ロールコード群
	 * @return 確認結果(true：設定されている、false：設定されていない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isTheRoleCodeSet(UserMasterDtoInterface dto, Set<String> roleCodes) throws MospException;
	
}
