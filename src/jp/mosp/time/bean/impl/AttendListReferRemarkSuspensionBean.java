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

import java.util.Collection;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;

/**
 * 勤怠一覧情報備考設定処理(休職)。<br>
 */
public class AttendListReferRemarkSuspensionBean extends AttendListReferRemarkBaseBean {
	
	@Override
	protected String getRemark(AttendListEntityInterface entity, AttendanceListDto dto) throws MospException {
		// 勤務日を取得
		Date workDate = dto.getWorkDate();
		// 休職情報群を取得
		Collection<SuspensionDtoInterface> suspensions = entity.getSuspensions();
		// 休職中でない場合
		if (HumanUtility.isSuspension(suspensions, workDate) == false) {
			// 空文字を取得
			return getEmpty();
		}
		// 休職用備考を準備
		StringBuilder remark = new StringBuilder();
		remark.append(PfNameUtility.suspension(mospParams));
		// 休職理由を取得
		String reason = HumanUtility.getSuspensionReason(suspensions, workDate);
		// 休職理由が設定されている場合
		if (MospUtility.isEmpty(reason) == false) {
			remark.append(PfNameUtility.singleColon(mospParams));
			remark.append(reason);
		}
		// 休職用備考を取得
		return remark.toString();
	}
	
}
