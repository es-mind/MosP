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
package jp.mosp.platform.comparator.workflow;

import java.util.Comparator;

import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * 承認ユニットの承認者による比較クラス。<br>
 */
public class ApprovalUnitMasterApproverComparator implements Comparator<ApprovalUnitDtoInterface> {
	
	@Override
	public int compare(ApprovalUnitDtoInterface dto1, ApprovalUnitDtoInterface dto2) {
		if (dto1.getUnitType().equals(PlatformConst.UNIT_TYPE_PERSON)) {
			return dto1.getApproverPersonalId().compareTo(dto2.getApproverPersonalId());
		} else {
			String str1 = dto1.getApproverSectionCode() + dto1.getApproverPositionCode();
			String str2 = dto2.getApproverSectionCode() + dto2.getApproverPositionCode();
			return str1.compareTo(str2);
		}
	}
	
}
