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

import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 勤怠一覧情報備考設定処理(時差出勤申請)。<br>
 */
public class AttendListReferRemarkDifferenceBean extends AttendListReferRemarkBaseBean {
	
	@Override
	protected String getRemark(AttendListEntityInterface entity, AttendanceListDto dto) throws MospException {
		// 対象となる承認状況群を取得
		Set<String> statuses = getWorkflowStatuses(dto);
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = entity.getRequestEntity(dto);
		// 時差出勤申請情報を取得
		DifferenceRequestDtoInterface request = requestEntity.getDifferenceRequestDto(statuses);
		// 時差出勤申請情報を取得できなかった場合
		if (MospUtility.isEmpty(request)) {
			// 空文字を取得
			return getEmpty();
		}
		// ワークフロー情報を取得
		WorkflowDtoInterface workflow = requestEntity.getWorkflowMap().get(request.getWorkflow());
		// 接頭辞を準備
		String prefix = TimeNamingUtility.timeDifferenceAbbr(mospParams);
		// 備考を取得
		return getRequestWorkflowStatusRemark(prefix, workflow);
	}
	
}
