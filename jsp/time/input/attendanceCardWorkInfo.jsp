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
language     = "java"
pageEncoding = "UTF-8"
buffer       = "256kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.AttendanceCardAction"
import = "jp.mosp.time.input.vo.AttendanceCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceCardVo vo = (AttendanceCardVo)params.getVo();
%>
	<table class="HeaderInputTable" id="attendanceCard_tblAttendanceHeader">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("AttendanceInformation") %></span></th>
		</tr>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblAttendance">
		<tr>
			<td class="TitleTd"><%= params.getName("Work","Form") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getSelectTag("WorkTypePullDown", "pltWorkType", "pltWorkType", vo.getPltWorkType(), vo.getAryPltWorkType(), vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_APPLY) || vo.getModeCardEdit().equals(TimeConst.MODE_APPLICATION_APPLIED) || vo.getModeCardEdit().equals(AttendanceCardAction.MODE_APPLICATION_COMPLETED_HOLIDAY)) %>
			</td>
			<td class="TitleTd"><label for="txtStartTimeHour"><%= params.getName("StartWork","Moment") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtStartTimeHour", "txtStartTimeHour", vo.getTxtStartTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtStartTimeMinute", "txtStartTimeMinute", vo.getTxtStartTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
				<%= vo.getLblStartTime() %>
			</td>
			<td class="TitleTd"><label for="txtEndTimeHour"><%= params.getName("EndWork","Moment") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtEndTimeHour", "txtEndTimeHour", vo.getTxtEndTimeHour(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtEndTimeMinute", "txtEndTimeMinute", vo.getTxtEndTimeMinute(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
				<%= vo.getLblEndTime() %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Work","Time") %></td>
			<td class="InputTd" id="lblWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblWorkTime()) %></td>
			<td class="TitleTd"><%= params.getName("Prescribed","Labor","Time") %></td>
			<td class="InputTd" id="lblGeneralWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblGeneralWorkTime()) %></td>
			<td class="TitleTd"><%= params.getName("DirectStart","Slash","DirectEnd") %></td>
			<td class="InputTd">
				<input type="checkbox" class="CheckBox" id="ckbDirectStart" name="ckbDirectStart" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbDirectStart()) %>>&nbsp;<%= params.getName("DirectStart") %>&nbsp;
				<input type="checkbox" class="CheckBox" id="ckbDirectEnd" name="ckbDirectEnd" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbDirectEnd()) %>>&nbsp;<%= params.getName("DirectEnd") %>
			</td>
		</tr>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)) {
%>
		<tr>
			<td class="TitleTd" id="tdUnpaidShortTime"><%= params.getName("UnpaidShortTime") %></td>
			<td class="InputTd" id="tdLblUnpaidShortTime" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblUnpaidShortTime()) %></td>
		</tr>
<%
}
%>
		<tr>
			<td class="TitleTd"><label for="txtTimeComment"><%= params.getName("WorkManageComment") %></label></td>
			<td class="InputTd" id="tdAttendanceComment" colspan="5">
				<%= HtmlUtility.getTextboxTag("Name50TextBox", "txtTimeComment", "txtTimeComment", vo.getTxtTimeComment(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
	</table>
<%
if (params.getGeneralParam(AttendanceCardAction.PRM_ATTENDANCE_EXTRA_JSP) != null) {
%>
	<jsp:include page="<%= (String)params.getGeneralParam(AttendanceCardAction.PRM_ATTENDANCE_EXTRA_JSP) %>" flush="false" />
<%
}
%>
