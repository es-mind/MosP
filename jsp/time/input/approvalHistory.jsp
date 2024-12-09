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
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.ApprovalHistoryAction"
import = "jp.mosp.time.input.vo.ApprovalHistoryVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApprovalHistoryVo vo = (ApprovalHistoryVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
</div>
<%
if (params.getCommand().equals(ApprovalHistoryAction.CMD_TIME_APPROVAL_HISTORY_SELECT_SHOW) || params.getCommand().equals(ApprovalHistoryAction.CMD_TIME_APPROVAL_HISTORY_RE_SEARCH)) {
%>
<div class="FixList" id="divAttendance">
	<table class="ListTable" id="approvalHistory_tblAttendance">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thDate"><%= params.getName("Work") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thWorkType"><%= params.getName("Form") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("StartWork") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("EndWork") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("RestTime") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("GoingOut") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("LateLeaveEarly") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("Inside") %><%= params.getName("Remainder") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("LeftOut") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("WorkingHoliday") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("Midnight") %></th>
				<th class="ListSelectTh" id="thAttendanceState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thAttendanceRemark"><%= params.getName("Remarks") %></th>
				<th class="ListSelectTh" id="th1"><%= params.getName("AttendanceCorrection") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="SelectTd" id="lblAttendanceDate"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceDate()) %></td>
				<td class="SelectTd" id="lblAttendanceWorkType"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceWorkType()) %></td>
				<td class="SelectTd" id="lblAttendanceStartDate"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceStartDate()) %></td>
				<td class="SelectTd" id="lblAttendanceEndDate"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceEndDate()) %></td>
				<td class="SelectTd" id="lblAttendanceWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceWorkTime()) %></td>
				<td class="SelectTd" id="lblAttendanceRestTime"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceRestTime()) %></td>
				<td class="SelectTd" id="lblAttendanceLateTime"><%= HtmlUtility.escapeHTML(vo.getLblAttendancePrivateTime()) %></td>
				<td class="SelectTd" id="lblAttendanceLeaveEarly"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceLateLeaveEarly()) %></td>
				<td class="SelectTd" id="lblAttendanceOverTimeIn"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceOverTimeIn()) %></td>
				<td class="SelectTd" id="lblAttendanceOverTimeOut"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceOverTimeOut()) %></td>
				<td class="SelectTd" id="lblAttendanceWorkOnHoliday"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceWorkOnHoliday()) %></td>
				<td class="SelectTd" id="lblAttendanceLateNight"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceLateNight()) %></td>
				<td class="SelectTd" id="lblAttendanceState"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceState()) %></td>
				<td class="InputTd" id="lblAttendanceRemark"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceRemark()) %></td>
				<td class="SelectTd" id="lblAttendanceCorrection">
<% if (!vo.getLblAttendanceCorrection().isEmpty()) { %>
					<a class="Link" id="linkCorrectionHistory" onclick="submitTransfer(event, null, null, null, '<%= ApprovalHistoryAction.CMD_TRANSFER %>');"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceCorrection()) %></a>
<% } %>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<%
}
if (params.getCommand().equals(ApprovalHistoryAction.CMD_OVERTIME_APPROVAL_HISTORY_SELECT_SHOW)) {
%>
<div class="FixList" id="divOverTime">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thOvertimeDate"><%= params.getName("OvertimeWork") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thOvertimeType"><%= params.getName("Type") %></th>
				<th class="ListSelectTh" id="thOvertimeType"><%= params.getName("Schedule") %></th>
				<th class="ListSelectTh" id="thOvertimeResult"><%= params.getName("Performance") %></th>
				<th class="ListSelectTh" id="thOvertimeReason"><%= params.getName("Reason") %></th>
				<th class="ListSelectTh" id="thOvertimeState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thOvertimeApprover"><%= params.getName("Approver") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="ListSelectTd" id="lblOverTimeDate"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeDate()) %></td>
				<td class="ListSelectTd" id="lblOverTimeType"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeType()) %></td>
				<td class="ListSelectTd" id="lblOverTimeSchedule"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeSchedule()) %></td>
				<td class="ListSelectTd" id="lblOverTimeResultBefore"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeResultTime()) %></td>
				<td class="ListInputTd" id="lblOverTimeRequestReason"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeRequestReason()) %></td>
				<td class="ListSelectTd" id="lblOverTimeState"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeState()) %></td>
				<td class="ListSelectTd" id="lblOverTimeApprover"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeApprover()) %></td>
			</tr>
		</tbody>
	</table>
</div>
<%
}
if (params.getCommand().equals(ApprovalHistoryAction.CMD_LEAVE_APPROVAL_HISTORY_SELECT_SHOW)) {
%>
<div class="FixList" id="divHoliday">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thHolidayDate"><%= params.getName("Holiday") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thHolidayType"><%= params.getName("Holiday") %><%= params.getName("Classification") %></th>
				<th class="ListSelectTh" id="thHolidayRange"><%= params.getName("Range") %></th>
				<th class="ListSelectTh" id="thHolidayReason"><%= params.getName("Reason") %></th>
				<th class="ListSelectTh" id="thHolidayState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thHolidayApprover"><%= params.getName("Approver") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="ListSelectTd" id="lblHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblHolidayDate()) %></td>
				<td class="ListSelectTd" id="lblHolidayType"><%= HtmlUtility.escapeHTML(vo.getLblHolidayType1()) %>&nbsp;<%= vo.getLblHolidayType2() %></td>
				<td class="ListSelectTd" id="lblHolidayLength"><%= HtmlUtility.escapeHTML(vo.getLblHolidayLength()) %></td>
				<td class="ListInputTd" id="lblHolidayRequestReason"><%= HtmlUtility.escapeHTML(vo.getLblHolidayRequestReason()) %></td>
				<td class="ListSelectTd" id="lblHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblHolidayState()) %></td>
				<td class="ListSelectTd" id="lblHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblHolidayApprover()) %></td>
			</tr>
		</tbody>
	</table>
</div>
<%
}
if (params.getCommand().equals(ApprovalHistoryAction.CMD_APPROVAL_HISTORY_HOLIDAY_WORK_SELECT_SHOW)) {
%>
<div class="FixList" id="divSubstitute">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thDate"><%= params.getName("GoingWork") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thDate"><%= params.getName("Schedule") %></th>
				<th class="ListSelectTh" id="thSubstituteReason"><%= params.getName("Reason") %></th>
				<th class="ListSelectTh" id="thSubstituteDate"><%= params.getName("Transfer") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="th4"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thName"><%= params.getName("Approver") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="ListSelectTd" id="lblWorkOnHolidayWorkDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayWorkDate()) %></td>
				<td class="ListSelectTd" id="lblWorkOnHolidayTime"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTime()) %></td>
				<td class="ListInputTd" id="lblWorkOnHolidayReason"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayReason()) %></td>
				<td class="ListSelectTd" id="lblWorkOnHolidayDate1"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayDate1()) %></td>
				<td class="ListSelectTd" id="lblWorkOnHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayState()) %></td>
				<td class="ListSelectTd" id="lblWorkOnHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayApprover()) %></td>
			</tr>
		</tbody>
	</table>
</div>
<%
}
if (params.getCommand().equals(ApprovalHistoryAction.CMD_APPROVAL_HISTORY_LIEU_SELECT_SHOW)) {
%>
<div class="FixList" id="divCompensation">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thSubHolidayDate"><%= params.getName("CompensatoryHoliday") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thSubHolidayDate"><%= params.getName("GoingWork") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="th4"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thName"><%= params.getName("Approver") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="ListSelectTd" id="lblSubHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayDate()) %></td>
				<td class="ListSelectTd" id="lblSubHolidayWorkDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayWorkDate()) %></td>
				<td class="ListSelectTd" id="lblSubHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayState()) %></td>
				<td class="ListSelectTd" id="lblSubHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayApprover()) %></td>
			</tr>
		</tbody>
	</table>
</div>
<%
}
if (params.getCommand().equals(ApprovalHistoryAction.CMD_WORK_TYPE_CHANGE_APPROVAL_HISTORY_SELECT_SHOW)) {
%>
<div class="FixList" id="divWorkTypeChange">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thDate"><%= params.getName("GoingWork") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thChangeWorkType"><%= params.getName("Change") %><%= params.getName("Later") %><%= params.getName("Work") %><%= params.getName("Form") %></th>
				<th class="ListSelectTh" id="thWorkTypeChangeReason"><%= params.getName("Reason") %></th>
				<th class="ListSelectTh" id="thState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thName"><%= params.getName("Approver") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="ListSelectTd" id="lblWorkTypeChangeDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeDate()) %></td>
				<td class="ListSelectTd" id="lblWorkTypeChangeWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeWorkType()) %></td>
				<td class="ListInputTd" id="lblWorkTypeChangeReason"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeReason()) %></td>
				<td class="ListSelectTd" id="lblWorkTypeChangeState"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeState()) %></td>
				<td class="ListSelectTd" id="lblWorkTypeChangeApprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeApprover()) %></td>
			</tr>
		</tbody>
	</table>
</div>
<%
}
if (params.getCommand().equals(ApprovalHistoryAction.CMD_DIFFERENCE_WORK_APPROVAL_HISTORY_SELECT_SHOW)) {
%>
<div class="FixList" id="divDifference">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thDate"><%= params.getName("GoingWork") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="th3"><%= params.getName("Type") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("TimeDifference") %><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="thDifferenceReason"><%= params.getName("Reason") %></th>
				<th class="ListSelectTh" id="thState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thName"><%= params.getName("Approver") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="ListSelectTd" id="lblDifferenceDate"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceDate()) %></td>
				<td class="ListSelectTd" id="lblDifferenceType"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceType()) %></td>
				<td class="ListSelectTd" id="lblDifferenceTime"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceTime()) %></td>
				<td class="ListInputTd" id="lblDifferenceReason"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceReason()) %></td>
				<td class="ListSelectTd" id="lblDifferenceState"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceState()) %></td>
				<td class="ListSelectTd" id="lblDifferenceApprover"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceApprover()) %></td>
			</tr>
		</tbody>
	</table>
</div>
<%
}
%>
<div class="FixList" id="divHistory">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thApprovalDate"><%= params.getName("Day") %><%= params.getName("Hour") %></th>
				<th class="ListSelectTh" id="thApprovalApprover"><%= params.getName("Operator") %></th>
				<th class="ListSelectTh" id="thApprovalResult"><%= params.getName("Process") %></th>
				<th class="ListSelectTh" id="thApprovalState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thApprovalComment"><%= params.getName("Comment") %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getLblApprovalState().length; i++) {
%>
			<tr>
				<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblApprovalDate()[i]) %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblApprovalApprover()[i]) %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblApprovalResult()[i]) %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblApprovalState()[i]) %></td>
				<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblApprovalComment()[i]) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
