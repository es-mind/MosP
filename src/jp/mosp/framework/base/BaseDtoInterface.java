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
package jp.mosp.framework.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 共通DTOインターフェース
 */
public interface BaseDtoInterface extends Serializable {
	
	/**
	 * 削除フラグ取得。
	 * @return 削除フラグ
	 */
	int getDeleteFlag();
	
	/**
	 * 登録日取得。
	 * @return 登録日
	 */
	Date getInsertDate();
	
	/**
	 * 登録者取得。
	 * @return 登録者
	 */
	String getInsertUser();
	
	/**
	 * 更新日取得。
	 * @return 更新日
	 */
	Date getUpdateDate();
	
	/**
	 * 更新者取得。
	 * @return 更新者
	 */
	String getUpdateUser();
	
	/**
	 * 削除フラグ設定。
	 * @param deleteFlag セットする削除フラグ
	 */
	void setDeleteFlag(int deleteFlag);
	
	/**
	 * 登録日設定。
	 * @param insertDate セットする登録日
	 */
	void setInsertDate(Date insertDate);
	
	/**
	 * 登録者設定。
	 * @param insertUser セットする登録者
	 */
	void setInsertUser(String insertUser);
	
	/**
	 * 更新日設定。
	 * @param updateDate セットする更新日
	 */
	void setUpdateDate(Date updateDate);
	
	/**
	 * 更新者設定。
	 * @param updateUser セットする更新者
	 */
	void setUpdateUser(String updateUser);
	
}
