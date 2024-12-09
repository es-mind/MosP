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
package jp.mosp.platform.base;

import java.util.Date;

/**
 * 有効日を利用するテーブル用共通DTOインターフェース。<br>
 */
public interface ActivateDtoInterface {
	
	/**
	 * 有効日を取得する。
	 * @return 有効日
	 */
	Date getActivateDate();
	
	/**
	 * 有効日を設定する。
	 * @param activateDate セットする有効日
	 */
	void setActivateDate(Date activateDate);
	
}
