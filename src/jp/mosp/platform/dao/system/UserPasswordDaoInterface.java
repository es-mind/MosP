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

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;

/**
 * ユーザパスワード情報DAOインターフェース。
 */
public interface UserPasswordDaoInterface extends BaseDaoInterface {
	
	/**
	 * ユーザIDと変更日からユーザパスワード情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param userId     ユーザID
	 * @param changeDate 変更日
	 * @return ユーザパスワード情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	UserPasswordDtoInterface findForKey(String userId, Date changeDate) throws MospException;
	
	/**
	 * ユーザパスワード情報DTOを取得する。<br>
	 * 対象ユーザIDにつきパスワード情報を取得する。<br>
	 * @param userId     ユーザID
	 * @return ユーザパスワード情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	UserPasswordDtoInterface findForInfo(String userId) throws MospException;
	
}
