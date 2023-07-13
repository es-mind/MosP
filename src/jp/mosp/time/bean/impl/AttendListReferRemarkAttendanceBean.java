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
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;
import jp.mosp.time.utils.AttendanceUtility;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 勤怠一覧情報備考設定処理(勤怠)。<br>
 */
public class AttendListReferRemarkAttendanceBean extends AttendListReferRemarkBaseBean {
	
	@Override
	protected String getRemark(AttendListEntityInterface entity, AttendanceListDto dto) throws MospException {
		// 勤怠一覧情報区分を取得
		int listType = dto.getListType();
		// 勤怠一覧区分が予定である場合
		if (listType == AttendanceUtility.TYPE_LIST_SCHEDULE) {
			// 	空文字を取得
			return getEmpty();
		}
		// 対象となる承認状況群を取得
		Set<String> statuses = getWorkflowStatuses(dto);
		// 勤怠(日々)情報を取得
		AttendanceDtoInterface attendance = entity.getRequestEntity(dto).getAttendanceDto(statuses);
		// 勤怠(日々)情報を取得できなかった場合
		if (MospUtility.isEmpty(attendance)) {
			// 	空文字を取得
			return getEmpty();
		}
		// 備考を準備
		StringBuilder remark = new StringBuilder();
		// 遅刻用の備考を取得し設定
		addRemark(remark, getLateRemark(attendance));
		// 早退用の備考を取得し設定
		addRemark(remark, getLeaveEarlyRemark(attendance));
		// 直行用の備考を取得し設定
		addRemark(remark, getDirectStartRemark(attendance));
		// 直帰用の備考を取得し設定
		addRemark(remark, getDirectEndRemark(attendance));
		// 勤怠コメント及び備考を取得し設定
		addRemark(remark, attendance.getTimeComment());
		addRemark(remark, attendance.getRemarks());
		// 備考を取得
		return remark.toString();
	}
	
	/**
	 * 遅刻用の備考を取得する。<br>
	 * @param dto 勤怠(日々)情報
	 * @return 遅刻用の備考
	 */
	protected String getLateRemark(AttendanceDtoInterface dto) {
		// 遅刻理由を取得
		String lateReason = dto.getLateReason();
		// 遅刻でない場合
		if (MospUtility.isEmpty(lateReason)) {
			// 	空文字を取得
			return getEmpty();
		}
		// 遅刻用の備考を準備
		StringBuilder remark = new StringBuilder();
		// 遅刻用の備考を作成
		addRemark(remark, TimeNamingUtility.getLateAbbrNaming(mospParams));
		addRemark(remark, MospUtility.getCodeName(mospParams, lateReason, TimeConst.CODE_REASON_OF_LATE));
		// 遅刻用の備考を取得
		return remark.toString();
	}
	
	/**
	 * 早退用の備考を取得する。<br>
	 * @param dto 勤怠(日々)情報
	 * @return 早退用の備考
	 */
	protected String getLeaveEarlyRemark(AttendanceDtoInterface dto) {
		// 早退理由を取得
		String leaveEarlyReason = dto.getLeaveEarlyReason();
		// 早退でない場合
		if (MospUtility.isEmpty(leaveEarlyReason)) {
			// 	空文字を取得
			return getEmpty();
		}
		// 早退用の備考を準備
		StringBuilder remark = new StringBuilder();
		// 早退用の備考を作成
		addRemark(remark, TimeNamingUtility.getEarlyAbbrNaming(mospParams));
		addRemark(remark, MospUtility.getCodeName(mospParams, leaveEarlyReason, TimeConst.CODE_REASON_OF_LEAVE_EARLY));
		// 早退用の備考を取得
		return remark.toString();
	}
	
	/**
	 * 直行用の備考を取得する。<br>
	 * @param dto 勤怠(日々)情報
	 * @return 直行用の備考
	 */
	protected String getDirectStartRemark(AttendanceDtoInterface dto) {
		// 直行でない場合
		if (MospUtility.isChecked(dto.getDirectStart()) == false) {
			// 	空文字を取得
			return getEmpty();
		}
		// 直行用の備考を取得
		return TimeNamingUtility.directStart(mospParams);
	}
	
	/**
	 * 直帰用の備考を取得する。<br>
	 * @param dto 勤怠(日々)情報
	 * @return 直帰用の備考
	 */
	protected String getDirectEndRemark(AttendanceDtoInterface dto) {
		// 直帰でない場合
		if (MospUtility.isChecked(dto.getDirectEnd()) == false) {
			// 	空文字を取得
			return getEmpty();
		}
		// 直帰用の備考を取得
		return TimeNamingUtility.directEnd(mospParams);
	}
	
}
