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
package jp.mosp.time.bean.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 勤怠一覧情報備考設定処理(振替休日)。<br>
 */
public class AttendListReferRemarkSubstituteBean extends AttendListReferRemarkBaseBean {
	
	@Override
	protected String getRemark(AttendListEntityInterface entity, AttendanceListDto dto) throws MospException {
		// 振替休日用備考を準備
		Set<String> remarks = new LinkedHashSet<String>();
		// 接頭辞を準備
		String prefix = TimeNamingUtility.substituteAbbr(mospParams);
		// 対象となる承認状況群を取得
		Set<String> statuses = getWorkflowStatuses(dto);
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = entity.getRequestEntity(dto);
		// 振出・休出申請が存在する(振替の振替である)場合
		if (MospUtility.isEmpty(requestEntity.getWorkOnHolidayRequestDto(statuses)) == false) {
			// 空文字を取得(振出・休出申請で備考を設定)
			return getEmpty();
		}
		// 振替休日情報リストを取得
		List<SubstituteDtoInterface> substitutes = requestEntity.getSubstituteList(statuses);
		// ワークフロー情報群を取得
		Map<Long, WorkflowDtoInterface> workflows = requestEntity.getWorkflowMap();
		// 振替休日情報リストを承認状態でソート
		WorkflowUtility.sortByStatus(substitutes, workflows);
		// 休暇申請毎に処理
		for (SubstituteDtoInterface substitute : substitutes) {
			// 備考を設定
			remarks.add(getRequestWorkflowStatusRemark(prefix, workflows.get(substitute.getWorkflow())));
		}
		// 備考を取得
		return getRemark(remarks);
	}
	
}
