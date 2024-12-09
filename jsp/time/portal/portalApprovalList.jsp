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
import = "jp.mosp.platform.portal.vo.PortalVo"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.management.action.ApprovalListAction"
import = "jp.mosp.time.portal.bean.impl.PortalApprovalListBean"
import = "jp.mosp.time.utils.TimeUtility"
%><%
//VOを準備
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PortalVo vo = (PortalVo)params.getVo();
//VOを取得できなかった場合
if (vo == null) {
	// 処理終了
	return;
}
%>
<div class="List" id="divApprovalList">
	<table class="LeftListTable">
		<tr>
			<th class="ListSelectTh"><%= params.getName("Approval") %><%= params.getName("Type") %></th>
			<th class="ListSelectTh"><%= params.getName("Ram") %><%= params.getName("Approval") %><%= params.getName("Count") %><%= params.getName("Num") %></th>
			<th class="ListSelectTh"></th>
			<td class="Divider"></td>
			<th class="ListSelectTh"><%= params.getName("Approval") %><%= params.getName("Type") %></th>
			<th class="ListSelectTh"><%= params.getName("Ram") %><%= params.getName("Approval") %><%= params.getName("Count") %><%= params.getName("Num") %></th>
			<th class="ListSelectTh"></th>
			<td class="Divider"></td>
			<th class="ListSelectTh"><%= params.getName("Approval") %><%= params.getName("Type") %></th>
			<th class="ListSelectTh"><%= params.getName("Ram") %><%= params.getName("Approval") %><%= params.getName("Count") %><%= params.getName("Num") %></th>
			<th class="ListSelectTh"></th>
		</tr>
		<tr>
			<td class="SelectTd"><%= params.getName("WorkManage") %><%= params.getName("Approval") %></td>
			<td class="SelectTd" id="lblAttendance"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_WORK_MANAGE))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnAttendance" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= TimeConst.CODE_FUNCTION_WORK_MANGE %>', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
<%
if (TimeUtility.isWorkOnHolidayRequestValid(params)) {
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%= params.getName("SubstituteAbbr") %><%= params.getName("GoingWorkAbbr") %><%= params.getName("WorkingHoliday") %><%= params.getName("Approval") %></td>
			<td class="SelectTd" id="lblWorkOnHoliday"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_HOLIDAY_GOINGWORK))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnWorkOnHoliday" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= TimeConst.CODE_FUNCTION_WORK_HOLIDAY %>', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="4"></td>
<%
}
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%= params.getName("All") %><%= params.getName("Approval") %></td>
			<td class="SelectTd" id="lblTotalApproval"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_ALL_APPROVAL))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnTotalApproval" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
		</tr>
<%
if (TimeUtility.isOvertimeRequestValid(params) || TimeUtility.isSubHolidayRequestValid(params)) {
%>
		<tr>
<% if (TimeUtility.isOvertimeRequestValid(params)) { %>
			<td class="SelectTd"><%= params.getName("OvertimeWork") %><%= params.getName("Approval") %></td>
			<td class="SelectTd" id="lblOverTime"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_OVERTIME_WORK))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnOverTime" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= TimeConst.CODE_FUNCTION_OVER_WORK %>', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
<% } else { %>
			<td class="Blank" colspan="3"></td>
<% }
   if (TimeUtility.isSubHolidayRequestValid(params)) { %>
			<td class="Divider"></td>
			<td class="SelectTd"><%= params.getName("CompensatoryHoliday") %><%= params.getName("Approval") %></td>
			<td class="SelectTd" id="lblSubHoliday"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_COMPENSATORY_HOLIDAY))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnSubHoliday" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY %>', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
<% } else { %>
			<td class="Blank" colspan="4"></td>
<% } %>
			<td class="Blank" colspan="4"></td>
		</tr>
		<tr>
<%
}
if (TimeUtility.isHolidayRequestValid(params)) {
%>
			<td class="SelectTd"><%= params.getName("Holiday") %><%= params.getName("Approval") %></td>
			<td class="SelectTd" id="lblHoliday"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_VACATION))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnHoliday" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= TimeConst.CODE_FUNCTION_VACATION %>', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="3"></td>
<%
}
if (TimeUtility.isWorkTypeChangeRequestValid(params)) {
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%= params.getName("WorkTypeAbbr") %><%= params.getName("Change") %><%= params.getName("Approval") %></td>
			<td class="SelectTd" id="lblWorkTypeChange"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_WORK_TYPE_CHANGE))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnWorkTypeChange" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE %>', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
<%
} else if (params.getProperties().getMainMenuProperties().get("menuTimeInput").getMenuMap().containsKey("DifferenceRequest")) {
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%= params.getName("TimeDifference") %><%= params.getName("GoingWork") %><%= params.getName("Approval") %></td>
			<td class="SelectTd" id="lblDifference"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_TIME_DIFFERENCE_GOINGWORK))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnDifference" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= TimeConst.CODE_FUNCTION_DIFFERENCE %>', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="4"></td>
<%
}
if (TimeUtility.isCancellationRequestValid(params)) {
%>
			<td class="Divider"></td>
			<th class="ListSelectTh"><%= params.getName("Approval") %><%= params.getName("Release") %><%= params.getName("Application") %></th>
			<td class="SelectTd" id="lblCancelAppli"><%=HtmlUtility.escapeHTML(vo.getPortalParameter(PortalApprovalListBean.PRM_APPROVAL_LIST_ALL_CANCEL))%><%= params.getName("Count") %></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnCancelAppli" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '', '<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= PlatformConst.CODE_STATUS_CANCEL_APPLY %>'), '<%= ApprovalListAction.CMD_SHOW_SERCH %>');"><%= params.getName("Display") %></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="4"></td>
<%
}
%>
		</tr>
	</table>
</div>
