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

import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;

/**
 * 承認階層による比較クラス。<br>
 */
public class ApprovalRouteMasterApprovalCountComparator implements Comparator<ApprovalRouteDtoInterface> {
	
	@Override
	public int compare(ApprovalRouteDtoInterface dto1, ApprovalRouteDtoInterface dto2) {
		return dto2.getApprovalCount() - dto1.getApprovalCount();
	}
	
}
