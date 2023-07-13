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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;

/**
 * 勤怠一覧情報備考設定処理(カレンダ)。<br>
 */
public class AttendListReferRemarkScheduleBean extends AttendListReferRemarkBaseBean {
	
	@Override
	protected String getRemark(AttendListEntityInterface entity, AttendanceListDto dto) throws MospException {
		// 勤務日を取得
		Date workDate = dto.getWorkDate();
		// カレンダ日情報を取得
		ScheduleDateDtoInterface schedule = entity.getScheduleDate(workDate);
		// カレンダ日情報を取得できなかった場合
		if (MospUtility.isEmpty(schedule)) {
			// 	空文字を取得
			return getEmpty();
		}
		// 備考を取得
		return schedule.getRemark();
	}
	
}
