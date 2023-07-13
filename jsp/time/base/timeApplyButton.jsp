<%--
MosP - Mind Open Source Project    http://www.mosp.jp/
Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/

This program is free software: you can redistribute it and/or
modify it under the terms of the GNU Affero General Public License
as published by the Free Software Foundation, either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page
language = "java"
pageEncoding = "UTF-8"
buffer = "256kb"
autoFlush = "false"
errorPage = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.time.base.TimeVo"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.OvertimeRequestAction"
import = "jp.mosp.time.input.action.HolidayRequestAction"
import = "jp.mosp.time.input.action.WorkOnHolidayRequestAction"
import = "jp.mosp.time.input.action.SubHolidayRequestAction"
import = "jp.mosp.time.input.action.WorkTypeChangeRequestAction"
import = "jp.mosp.time.input.action.DifferenceRequestAction"
import = "jp.mosp.time.input.vo.OvertimeRequestVo"
import = "jp.mosp.time.input.vo.HolidayRequestVo"
import = "jp.mosp.time.input.vo.WorkOnHolidayRequestVo"
import = "jp.mosp.time.input.vo.SubHolidayRequestVo"
import = "jp.mosp.time.input.vo.WorkTypeChangeRequestVo"
import = "jp.mosp.time.input.vo.DifferenceRequestVo"
%><%
// MosP処理情報及びVOを取得
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TimeVo vo = (TimeVo)params.getVo();
// ボタン要否宣言(true:押せない,false:押せる)
boolean disabledApply = true;
boolean disabledDraft = true;
boolean disabledWithdraw = true;
// コマンド宣言
String applyCommand = "";
String draftCommand = "";
String withdrawCommand = "";
// VO確認(新規申請コマンド設定)
if (vo instanceof OvertimeRequestVo) {
	applyCommand = OvertimeRequestAction.CMD_APPLI;
	draftCommand = OvertimeRequestAction.CMD_DRAFT;
	withdrawCommand = OvertimeRequestAction.CMD_WITHDRAWN;
} else if (vo instanceof HolidayRequestVo) {
	applyCommand = HolidayRequestAction.CMD_APPLI;
	draftCommand = HolidayRequestAction.CMD_DRAFT;
	withdrawCommand = HolidayRequestAction.CMD_WITHDRAWN;
} else if (vo instanceof WorkOnHolidayRequestVo) {
	applyCommand = WorkOnHolidayRequestAction.CMD_APPLI;
	draftCommand = WorkOnHolidayRequestAction.CMD_DRAFT;
	withdrawCommand = WorkOnHolidayRequestAction.CMD_WITHDRAWN;
} else if (vo instanceof SubHolidayRequestVo) {
	applyCommand = SubHolidayRequestAction.CMD_APPLI;
	draftCommand = SubHolidayRequestAction.CMD_DRAFT;
	withdrawCommand = SubHolidayRequestAction.CMD_WITHDRAWN;
} else if (vo instanceof WorkTypeChangeRequestVo) {
	applyCommand = WorkTypeChangeRequestAction.CMD_APPLI;
	draftCommand = WorkTypeChangeRequestAction.CMD_DRAFT;
	withdrawCommand = WorkTypeChangeRequestAction.CMD_WITHDRAWN;
} else if (vo instanceof DifferenceRequestVo) {
	applyCommand = DifferenceRequestAction.CMD_APPLI;
	draftCommand = DifferenceRequestAction.CMD_DRAFT;
	withdrawCommand = DifferenceRequestAction.CMD_WITHDRAWN;
}
// 有効日決定状態からボタン要否をセッティング
if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
	disabledApply = false;
	// モード毎にセッティング
	if (vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_DRAFT)) {
		disabledDraft = false;
		disabledWithdraw = false;
	} else if (vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_REVERT)) {
		disabledWithdraw = false;
	} else if (vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_NEW)) {
		disabledDraft = false;
	}
} else {
	if (vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_DRAFT)) {
		disabledWithdraw = false;
	} else if (vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_REVERT)) {
		disabledWithdraw = false;
	}
}
%>
<table class="ButtonTable">
	<tr>
		<td class="ButtonTd">
			<button type="button" class="Name2Button" id="btnRegist" onclick="submitApplication(event, '<%= applyCommand %>');" <%= HtmlUtility.getDisabled(disabledApply) %>><%= params.getName("Application") %></button>
		</td>
	</tr>
	<tr>
		<td class="ButtonTd">
			<button type="button" class="Name2Button" id="btnDraft" onclick="submitRegist(event, null, checkDraftExtra, '<%= draftCommand %>');" <%= HtmlUtility.getDisabled(disabledDraft) %>><%= params.getName("WorkPaper") %></button>
		</td>
	</tr>
	<tr>
		<td class="ButtonTd">
			<button type="button" class="Name2Button" id="btnDelete" onclick="submitRegist(event, null, null, '<%= withdrawCommand %>');" <%= HtmlUtility.getDisabled(disabledWithdraw) %>><%= TimeConst.MODE_APPLICATION_DRAFT.equals(vo.getModeCardEdit()) ? params.getName("Delete") : params.getName("TakeDown") %></button>
		</td>
	</tr>
</table>
