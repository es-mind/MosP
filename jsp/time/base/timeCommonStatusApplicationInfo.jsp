<%--
MosP - Mind Open Source Project
Copyright (C) esMind, LLC  https://www.e-s-mind.com/

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
// 編集モード名称宣言(デフォルト新規)
String editModeTitle = params.getName("New");
// 新規申請リンク要否宣言
boolean needNewLink = false;
// 新規申請コマンド宣言
String newModeCommand = "";
// VO確認(新規申請コマンド設定)
if (vo instanceof OvertimeRequestVo) {
	newModeCommand = OvertimeRequestAction.CMD_INSERT_MODE;
} else if (vo instanceof HolidayRequestVo) {
	newModeCommand = HolidayRequestAction.CMD_INSERT_MODE;
} else if (vo instanceof WorkOnHolidayRequestVo) {
	newModeCommand = WorkOnHolidayRequestAction.CMD_INSERT_MODE;
} else if (vo instanceof SubHolidayRequestVo) {
	newModeCommand = SubHolidayRequestAction.CMD_INSERT_MODE;
} else if (vo instanceof WorkTypeChangeRequestVo) {
	newModeCommand = WorkTypeChangeRequestAction.CMD_INSERT_MODE;
} else if (vo instanceof DifferenceRequestVo) {
	newModeCommand = DifferenceRequestAction.CMD_INSERT_MODE;
}
// モード毎にセッティング
if (vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_DRAFT)) {
	editModeTitle = params.getName("WorkPaper") + params.getName("Edit");
	needNewLink = true;
} else if (vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_REVERT)) {
	editModeTitle = params.getName("SendingBack") + params.getName("Edit");
	needNewLink = true;
}
%>
<span class="TitleTh"><%= params.getName("Application") + params.getName("FrontWithCornerParentheses") + editModeTitle + params.getName("BackWithCornerParentheses") %></span>
<%
if (needNewLink) {
%>
<a onclick="submitTransfer(event, 'divEdit', null, null, '<%= newModeCommand %>')"><%= params.getName("FrontWithCornerParentheses") + params.getName("New") + params.getName("Application") + params.getName("BackWithCornerParentheses") %></a>
<%
}
%>
