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
import = "jp.mosp.time.input.action.AttendanceHistoryAction"
import = "jp.mosp.time.input.vo.AttendanceHistoryVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceHistoryVo vo = (AttendanceHistoryVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
</div>
<%
if (params.getCommand().equals(AttendanceHistoryAction.CMD_SELECT_SHOW) || params.getCommand().equals(AttendanceHistoryAction.CMD_RE_SHOW)) {
%>
<div class="FixList">
	<table class="ListTable" id="attendanceHistory_tblAttendance">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thDate"><%= params.getName("Work") %><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="th2"><%= params.getName("Form") %></th>
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
				<td class="SelectTd" id="lblAttendenceOverTimeOut"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceOverTimeOut()) %></td>
				<td class="SelectTd" id="lblAttendanceWorkOnHoliday"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceWorkOnHoliday()) %></td>
				<td class="SelectTd" id="lblAttendanceLateNight"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceLateNight()) %></td>
				<td class="SelectTd" id="lblAttendanceState">
					<a class="Link" id="eventApprovalHistory" onclick="submitTransfer(event, null, null, null, '<%= AttendanceHistoryAction.CMD_TRANSFER %>');"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceState()) %></a>
				</td>
				<td class="InputTd" id="lblAttendanceRemark"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceRemark()) %></td>
				<td class="SelectTd" id="lblAttendanceCorrection"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceCorrection()) %></td>
			</tr>
		</tbody>
	</table>
</div>
<%
}
%>
<div class="FixList">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thNumber"></th>
				<th class="ListSelectTh" id="thCorrectionDate"><%= params.getName("Correction") %><%= params.getName("Day") %><%= params.getName("Hour") %></th>
				<th class="ListSelectTh" id="thCorrectionName"><%= params.getName("Corrector") %></th>
				<th class="ListSelectTh" id="thCorrectionPlace"><%= params.getName("Correction") %><%= params.getName("Place") %></th>
				<th class="ListSelectTh" id="thCorrectionTime"><%= params.getName("Correction") %><%= params.getName("Ahead") %></th>
				<th class="ListSelectTh" id="thCorrectionTime"><%= params.getName("Correction") %><%= params.getName("Later") %></th>
<!--
				<th class="ListSelectTh" id="thComment"><%= params.getName("Comment") %></th>
-->
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblCorrectionNumber().length; i++) {
%>
			<tr>
				<td class="ListNumberTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCorrectionNumber()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCorrectionDate()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCorrectionEmployee()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCorrectionType()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCorrectionBefore()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCorrectionAfter()[i]) %></td>
<!--
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCorrectionComment()[i]) %></td>
-->
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
