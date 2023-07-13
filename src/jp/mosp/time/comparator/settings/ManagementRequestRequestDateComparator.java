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
package jp.mosp.time.comparator.settings;

import java.util.Comparator;

import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;

/**
 *
 */
public class ManagementRequestRequestDateComparator implements Comparator<ManagementRequestListDtoInterface> {
	
	@Override
	public int compare(ManagementRequestListDtoInterface dto1, ManagementRequestListDtoInterface dto2) {
		// 申請日
		int compareRequestDate = dto1.getRequestDate().compareTo(dto2.getRequestDate());
		if (compareRequestDate != 0) {
			return compareRequestDate;
		}
		// 社員コード
		int compareEmployeeCode = dto1.getEmployeeCode().compareTo(dto2.getEmployeeCode());
		if (compareEmployeeCode != 0) {
			return compareEmployeeCode;
		}
		int compareRequestType = dto1.getRequestType().compareTo(dto2.getRequestType());
		if (compareRequestType != 0) {
			return compareRequestType;
		}
		// 申請情報詳細
		return dto1.getRequestInfo().compareTo(dto2.getRequestInfo());
	}
	
}
