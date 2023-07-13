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
package jp.mosp.time.dto.base;

import java.util.Date;

/**
 * 申請日DTOのインターフェース。<br>
 */
public interface RequestDateDtoInterface {
	
	/**
	 * 申請日を取得する。<br>
	 * @return 申請日
	 */
	Date getRequestDate();
	
	/**
	 * 申請日を設定する。<br>
	 * @param requestDate 申請日
	 */
	void setRequestDate(Date requestDate);
	
}
