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
package jp.mosp.platform.dao.system;

import java.util.Date;
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;

/**
 * ユーザ数カウントDAOインターフェース。<br>
 */
public interface UserCountDaoInterface extends BaseDaoInterface {
	
	/**
	 * ユーザ数を取得する。<br>
	 * <br>
	 * ロールコードを空白にした場合、
	 * 対象ロール区分が設定されているユーザ数をカウントする。<br>
	 * <br>
	 * ロール区分を空白にした場合、
	 * メインロール(ユーザ情報のロールコード)を対象とする。<br>
	 * <br>
	 * @param roleType   ロール区分
	 * @param roleCode   ロールコード
	 * @param targetDate 対象日
	 * @return ユーザ数
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	int count(String roleType, String roleCode, Date targetDate) throws MospException;
	
	/**
	 * 有効日群を取得する。<br>
	 * <br>
	 * システム日付より後である有効日群(+システム日付)を取得する。<br>
	 * ユーザ数チェック等に利用する。<br>
	 * <br>
	 * @return 有効日群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<Date> getActivateDates() throws MospException;
	
}
