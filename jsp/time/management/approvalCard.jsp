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
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.management.action.ApprovalCardAction"
import = "jp.mosp.time.management.vo.ApprovalCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApprovalCardVo vo = (ApprovalCardVo)params.getVo();
%>
<%
if (vo.getRollArray() != null) {
%>
<div class="List">
	<table class="EmployeeCodeRollTable">
		<tr>
			<td class="RollTd" id="tdFormerCode">
<% if (vo.getPrevCommand() != null && !vo.getPrevCommand().isEmpty()) { %>
				<%= params.getName("Ahead", "To") %>
			</td>
			<td class="RollTd" id="tdFormerButton">
				<a onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFER_SEARCH_MODE %>', '<%= TimeConst.SEARCH_BACK %>'), '<%= ApprovalCardAction.CMD_ROLL %>');">
					<%= params.getName("UpperTriangular") %>
				</a>
<% } else { %>
				<span Class="FontGray"><%= params.getName("Ahead", "To") %></span>
			</td>
			<td class="RollTd" id="tdFormerButton">
				<span Class="FontGray"><%= params.getName("UpperTriangular") %></span>
<% } %>
			</td>
			<td class="RollTd" id="tdNextButton">
<% if (vo.getNextCommand() != null && !vo.getNextCommand().isEmpty()) { %>
				<a onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFER_SEARCH_MODE %>', '<%= TimeConst.SEARCH_NEXT %>'), '<%= ApprovalCardAction.CMD_ROLL %>');">
					<%= params.getName("LowerTriangular") %>
				</a>
			<td class="RollTd" id="tdNextCode">
				<%= params.getName("Following", "To") %>
<% } else { %>
				<span Class="FontGray"><%= params.getName("LowerTriangular") %></span>
			</td>
			<td class="RollTd" id="tdNextCode">
				<span Class="FontGray"><%= params.getName("Following", "To") %></span>
<% } %>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
	<table class="DateTable">
		<tr>
			<td>&nbsp;
				<%= HtmlUtility.escapeHTML(vo.getLblYear()) %><%= params.getName("Year") %>
				<%= HtmlUtility.escapeHTML(vo.getLblMonth()) %><%= params.getName("Month") %>
				<%= HtmlUtility.escapeHTML(vo.getLblDay()) %><%= params.getName("Day") %>
			</td>
		</tr>
	</table>
</div>
<div class="FixList">
<%
if (vo.getLblWorkType() != null) {
// 出退勤情報・休憩情報・遅刻早退情報
for (String applicationProperty : params.getApplicationProperties("ApprovalCardItemJsp")) {
%>
	<jsp:include page="<%= applicationProperty %>" flush="false" />
<%
}
if (params.getGeneralParam(ApprovalCardAction.PRM_APPROVAL_EXTRA_JSP) != null) {
%>
	<jsp:include page="<%= (String)params.getGeneralParam(ApprovalCardAction.PRM_APPROVAL_EXTRA_JSP) %>" flush="false" />
<%
}
%>
</div>
<div>
	<table class="LeftListTable" id="approvalCard_tblAttendanceRequest">
		<tr>
<% if (vo.isAttendance()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("WorkApprovalSituation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtAttendanceComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isAttendance() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtAttendanceComment" name="txtAttendanceComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblAttendanceComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblAttendanceState"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceState()) %></td>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblAttendanceApprover"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.getLblOvertimeType().length > 0) {
%>
	<table class="LeftListTable" id="approvalCard_tblOvertimeRequest">
		<tr>
<% if (vo.isOvertime()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("OvertimeRequestSituation") %></span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblOvertimeType().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Type") %></td>
			<td class="InputTd" id="lblOverTimeType"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeType()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Application","Time") %></td>
			<td class="InputTd" id="lblOverTimeSchedule"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeSchedule()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Performance","Time") %></td>
			<td class="InputTd" id="lblOverTimeResult"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeResult()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Reason") %></td>
			<td class="InputTd" id="lblOverTimeReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeReason()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblOverTimeState"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeState()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtOverTimeComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<%	if (vo.isOvertime() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtOverTimeComment" name="txtOverTimeComment" />
<%	} else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblOvertimeComment()[i]) %>
<%	} %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblOverTimeApprover"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (vo.getLblHolidayType().length > 0) {
%>
	<table class="LeftListTable" id="approvalCard_tblHolidayRequest">
		<tr>
<% if (vo.isHoliday()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("HolidayRequestSituation") %></span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblHolidayType().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("Date") %></td>
			<td class="InputTd" id="lblHolidayDate" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblHolidayDate()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Holiday","Classification") %></td>
			<td class="InputTd" id="lblHolidayType"><%= HtmlUtility.escapeHTML(vo.getLblHolidayType()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Holiday","Content") %></td>
			<td class="InputTd" id="lblHolidayLength"><%= HtmlUtility.escapeHTML(vo.getLblHolidayLength()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Holiday","Time") %></td>
			<td class="InputTd" id="lblHolidayTime"><%= HtmlUtility.escapeHTML(vo.getLblHolidayTime()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Holiday","Reason") %></td>
			<td class="InputTd" id="lblHolidayReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblHolidayReason()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblHolidayState()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtHolidayComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<%	if (vo.isHoliday() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtHolidayComment" name="txtHolidayComment" />
<%	} else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblHolidayComment()[i]) %>
<%	} %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblHolidayApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (vo.getLblWorkOnHolidayDate() != null) {
%>
	<table class="LeftListTable" id="approvalCard_tblWorkOnHolidayRequest">
		<tr>
<% if (vo.isWorkOnHoliday()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("WorkOnHolidayRequestSituation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblWorkOnHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayDate()) %></td>
			<td class="TitleTd"><%= params.getName("Schedule") %></td>
			<td class="InputTd" id="lblWorkOnHolidayTime"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTime()) %></td>
			<td class="TitleTd"><%= params.getName("Transfer") %><%= params.getName("Day") %></td>
			<td class="InputTd" id="lblWorkOnHolidayTransferDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTransferDate()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="lblWorkOnHolidayReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblWorkOnHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtWorkOnHolidayComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isWorkOnHoliday() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtWorkOnHolidayComment" name="txtWorkOnHolidayComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblWorkOnHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.getLblSubHolidayDate() != null) {
%>
	<table class="LeftListTable" id="approvalCard_tblSubHolidayRequest">
		<tr>
<% if (vo.isSubHoliday()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("SubHolidayRequestSituation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("CompensatoryHoliday","Day") %></td>
			<td class="InputTd" id="lblSubHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayDate()) %></td>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblSubHolidayWorkDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayWorkDate()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblSubHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtCompensationComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isSubHoliday() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtCompensationComment" name="txtCompensationComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblSubHolidayComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblSubHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.getLblWorkTypeChangeDate() != null) {
%>
	<table class="LeftListTable" id="approvalCard_tblWorkTypeChangeRequest">
		<tr>
<% if (vo.isWorkTypeChange()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("WorkTypeChangeRequestSituation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork") %><%= params.getName("Day") %></td>
			<td class="InputTd" id="lblWorkTypeChangeDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeDate()) %></td>
			<td class="TitleTd"><%= params.getName("Change") %><%= params.getName("Ahead","Work","Form") %></td>
			<td class="InputTd" id="lblWorkTypeChangeBeforeWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeBeforeWorkType()) %></td>
			<td class="TitleTd"><%= params.getName("Change") %><%= params.getName("Later","Work","Form") %></td>
			<td class="InputTd" id="lblWorkTypeChangeAfterWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeAfterWorkType()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="lblWorkTypeChangeReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblWorkTypeChangeState"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtWorkTypeChangeComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isWorkTypeChange() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtWorkTypeChangeComment" name="txtWorkTypeChangeComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblWorkTypeChangeApprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.getLblDifferenceDate() != null) {
%>
	<table class="LeftListTable" id="approvalCard_tblDifferenceRequest">
		<tr>
<% if (vo.isDifference()) { %>
			<th class="EditTableTh" colspan="6">
<% } else { %>
			<th class="ListTableTh" colspan="6">
<% } %>
				<span class="TitleTh"><%= params.getName("jp.mosp.time.input.vo.DifferenceRequestVo","Situation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblDifferenceDate"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceDate()) %></td>
			<td class="TitleTd"><%= params.getName("Application","Ahead","Work","Form") %></td>
			<td class="InputTd" id="lblDifferenceWorkType"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceWorkType()) %></td>
			<td class="TitleTd"><%= params.getName("Application","Later","Work","Time") %></td>
			<td class="InputTd" id="lblDifferenceWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceWorkTime()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="lblDifferenceReason" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblDifferenceState"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtDifferenceComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
<% if (vo.isDifference() && vo.isNeedApproveButton()) { %>
				<input type="text" class="Name30TextBox" id="txtDifferenceComment" name="txtDifferenceComment" />
<% } else { %>
				<%= HtmlUtility.escapeHTML(vo.getLblDifferenceComment()) %>
<% } %>
			</td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblDifferenceApprover"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceApprover()) %></td>
		</tr>
	</table>
<%
}
if (vo.isNeedCancelApproveButton()) {
%>
	<table class="LeftListTable" id="approvalCard_tblCancellationRequest">
		<tr>
			<th class="EditTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Approval","Release","Situation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtCancelComment"><%= params.getName("Comment") %></label>
			</td>
			<td class="InputTd" id="tdApprovalComment" colspan="3">
				<input type="text" class="Name30TextBox" id="txtCancelComment" name="txtCancelComment" />
			</td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblCancelState"><%= HtmlUtility.escapeHTML(vo.getLblCancelState()) %></td>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblCancelApprover"><%= HtmlUtility.escapeHTML(vo.getLblCancelApprover()) %></td>
		</tr>
	</table>
<%
}
%>
</div>
<div class="Button">
<%
if (vo.isNeedCancelButton()) {
%>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= ApprovalCardAction.CMD_DELETE %>');"><%= params.getName("Approval","Release") %></button>
<%
}
if (vo.isNeedApproveButton()) {
%>
	<button type="button" class="Name4Button" onclick="submitRegist(event, 'ApprovalUpdate', null, '<%= ApprovalCardAction.CMD_APPROVAL %>');"><%= params.getName("Approval") %></button>
	<button type="button" class="Name4SendingBackButton" onclick="submitRegist(event, 'ApprovalUpdate', checkRevert, '<%= ApprovalCardAction.CMD_REVERTING %>');"><%= params.getName("SendingBack") %></button>
<%
}
if (vo.isNeedCancelApproveButton()) {
%>
	<button type="button" id="btnDelete" class="Name4CancelApproveButton" onclick="submitDelete(event, null, null, '<%= ApprovalCardAction.CMD_DELETE %>');"><%= params.getName("Approval","Release") %></button>
	<button type="button" class="Name4Button" onclick="submitRegist(event, 'ApprovalUpdate', checkRevert, '<%= ApprovalCardAction.CMD_REVERTING %>');"><%= params.getName("Release","TakeDown") %></button>
<%
}
%>
<%
for (String addonJsp : vo.getAddonJsps()) {
%>
	<jsp:include page="<%= addonJsp %>" flush="false" />
<%
}
%>
</div>
