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
/**
 * 差戻時の追加入力チェックを行う。
 * @param aryMessage メッセージ配列
 * @param event イベントオブジェクト
 * @return 無し
 */
function checkRevert(aryMessage, event) {
	// 勤怠申請
	var attendanceComment = getObject("txtAttendanceComment");
	if (attendanceComment != null) {
		checkRequired(attendanceComment, aryMessage);
	}
	// 残業申請
	var overTimeComment = getObject("txtOverTimeComment");
	if (overTimeComment != null) {
		checkRequired(overTimeComment, aryMessage);
	}
	// 休暇申請
	var holidayComment = getObject("txtHolidayComment");
	if (holidayComment != null) {
		checkRequired(holidayComment, aryMessage);
	}
	// 休日出勤申請
	var workOnHolidayComment = getObject("txtWorkOnHolidayComment");
	if (workOnHolidayComment != null) {
		checkRequired(workOnHolidayComment, aryMessage);
	}
	// 代休申請
	var compensationComment = getObject("txtCompensationComment");
	if (compensationComment != null) {
		checkRequired(compensationComment, aryMessage);
	}
	// 勤務形態変更申請
	var workTypeChangeComment = getObject("txtWorkTypeChangeComment");
	if (workTypeChangeComment != null) {
		checkRequired(workTypeChangeComment, aryMessage);
	}
	// 時差出勤申請
	var differenceComment = getObject("txtDifferenceComment");
	if (differenceComment != null) {
		checkRequired(differenceComment, aryMessage);
	}
	// 承認解除申請
	var cancelComment = getObject("txtCancelComment");
	if (cancelComment != null) {
		checkRequired(cancelComment, aryMessage);
	}
}
