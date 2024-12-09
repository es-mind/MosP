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
package jp.mosp.time.bean.impl;

import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 勤怠一覧情報備考設定処理(振出・休出申請)。<br>
 */
public class AttendListReferRemarkWorkOnHolidayBean extends AttendListReferRemarkBaseBean {
	
	@Override
	protected String getRemark(AttendListEntityInterface entity, AttendanceListDto dto) throws MospException {
		// 対象となる承認状況群を取得
		Set<String> statuses = getWorkflowStatuses(dto);
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = entity.getRequestEntity(dto);
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface request = requestEntity.getWorkOnHolidayRequestDto(statuses);
		// 振出・休出申請情報を取得できなかった場合
		if (MospUtility.isEmpty(request)) {
			// 空文字を取得
			return getEmpty();
		}
		// ワークフロー情報を取得
		WorkflowDtoInterface workflow = requestEntity.getWorkflowMap().get(request.getWorkflow());
		// 接頭辞を準備
		String prefix = getWorkOnHolidayAbbr(request);
		// 備考を取得
		return getRequestWorkflowStatusRemark(prefix, workflow);
	}
	
	/**
	 * 休日出勤申請の略称(備考用)を取得する。
	 * @param dto 休日出勤申請情報
	 * @return 休日出勤の略称(備考用)
	 */
	protected String getWorkOnHolidayAbbr(WorkOnHolidayRequestDtoInterface dto) {
		// 振出・休出申請が休日出勤(振替申請しない)である場合
		if (TimeRequestUtility.isWorkOnHolidaySubstituteOff(dto)) {
			// 休出を取得
			return TimeNamingUtility.workOnHolidayNotSubstituteAbbr(mospParams);
		}
		// 振出・休出申請が休日出勤(振替申請しない)でない場合(振出)
		return TimeNamingUtility.substituteWorkAbbr(mospParams);
	}
	
}
