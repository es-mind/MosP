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
import = "jp.mosp.time.utils.TimeUtility"
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.input.action.ApprovalHistoryAction"
import = "jp.mosp.time.input.action.AttendanceCardAction"
import = "jp.mosp.time.input.action.AttendanceHistoryAction"
import = "jp.mosp.time.input.action.AttendanceListAction"
import = "jp.mosp.time.input.vo.AttendanceListVo"
import = "jp.mosp.time.management.action.ApprovalCardAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceListVo vo = (AttendanceListVo)params.getVo();
boolean isSubHolidayRequestValid = TimeUtility.isSubHolidayRequestValid(params);
%>
<table class="LeftListTable" id="attendanceList_tblTotal">
		<tr>
			<th class="ListSelectTh" id="thSumTotalTime" rowspan="2">
				<div><%= params.getName("SumTotal") %></div>
				<div><%= params.getName("Time") %></div>
			</th>
			<th class="ListSelectTh" id="thTotalWork"><%= params.getName("Work") %></th>
			<th class="ListSelectTh" id="thTotalRestTime"><%= params.getName("RestTime") %></th>
			<th class="ListSelectTh" id="thTotalGoingOut"><%= params.getName("GoingOut") %></th>
			<th class="ListSelectTh" id="thTotalLateLeaveEarly"><%= params.getName("LateLeaveEarly") %></th>
			<th class="ListSelectTh" id="thTotalInsideRemainder"><%= params.getName("Inside","Remainder") %></th>
			<th class="ListSelectTh" id="thTotalLeftOut"><%= params.getName("LeftOut") %></th>
			<th class="ListSelectTh" id="thTotalWorkingHoliday"><%= params.getName("WorkingHoliday") %></th>
			<th class="ListSelectTh" id="thTotalMidnight"><%= params.getName("Midnight") %></th>
			<th class="ListSelectTh" id="thTotalFrequency" rowspan="2"><%= params.getName("Frequency") %></th>
			<th class="ListSelectTh" id="thTotalGoingWork"><%= params.getName("GoingWork") %></th>
			<th class="ListSelectTh" id="thTotalTardiness"><%= params.getName("Tardiness") %></th>
			<th class="ListSelectTh" id="thTotalLeaveEarly"><%= params.getName("LeaveEarly") %></th>
			<th class="ListSelectTh" id="thTotalOvertimeWork"><%= params.getName("OvertimeWork") %></th>
			<th class="ListSelectTh" id="thTotalWorkingHoliday"><%= params.getName("WorkingHoliday") %></th>
		</tr>
		<tr>
			<td class="SelectTd" id="lblTotalWork"><%= HtmlUtility.escapeHTML(vo.getLblTotalWork()) %></td>
			<td class="SelectTd" id="lblTotalRest"><%= HtmlUtility.escapeHTML(vo.getLblTotalRest()) %></td>
			<td class="SelectTd" id="lblTotalPrivate"><%= HtmlUtility.escapeHTML(vo.getLblTotalPrivate()) %></td>
			<td class="SelectTd" id="lblTotalLateLeaveEarly"><%= HtmlUtility.escapeHTML(vo.getLblTotalLateLeaveEarly()) %></td>
			<td class="SelectTd" id="lblTotalOverTimeIn"><%= HtmlUtility.escapeHTML(vo.getLblTotalOverTimeIn()) %></td>
			<td class="SelectTd" id="lblTotalOverTimeOut"><%= HtmlUtility.escapeHTML(vo.getLblTotalOverTimeOut()) %></td>
			<td class="SelectTd" id="lblTotalWorkOnHoliday"><%= HtmlUtility.escapeHTML(vo.getLblTotalWorkOnHoliday()) %></td>
			<td class="SelectTd" id="lblTotalLateNight"><%= HtmlUtility.escapeHTML(vo.getLblTotalLateNight()) %></td>
			<td class="SelectTd" id="lblTotalTimesWork"><%= HtmlUtility.escapeHTML(vo.getLblTimesWork()) %></td>
			<td class="SelectTd" id="lblTotalTimesLate"><%= HtmlUtility.escapeHTML(vo.getLblTimesLate()) %></td>
			<td class="SelectTd" id="lblTotalTimesLeaveEarly"><%= HtmlUtility.escapeHTML(vo.getLblTimesLeaveEarly()) %></td>
			<td class="SelectTd" id="lblTotalTimesOverTimeWork"><%= HtmlUtility.escapeHTML(vo.getLblTimesOverTimeWork()) %></td>
			<td class="SelectTd" id="lblTotalTimesWorkOnHoliday"><%= HtmlUtility.escapeHTML(vo.getLblTimesWorkOnHoliday()) %></td>
		</tr>
		<tr>
			<th class="ListSelectTh" id="thTotalHoliday" rowspan="2"><%= params.getName("DayOff") %></th>
			<th class="ListSelectTh" id="thTotalLegal"><%= params.getName("Legal") %></th>
			<th class="ListSelectTh" id="thTotalPrescribed"><%= params.getName("Prescribed") %></th>
			<th class="ListSelectTh" id="thTotalClosedVibration"><%= params.getName("ClosedVibration") %></th>
			<th class="ListSelectTh" id="thTotalVacation" rowspan="2"><%= params.getName("Holiday") %></th>
			<th class="ListSelectTh" id="thTotalPaidHoliday"><%= TimeNamingUtility.paidHolidayAbbr(params) %></th>
			<th class="ListSelectTh" id="thTotalSalaryTime"><%= params.getName("SalaryTime") %></th>
			<th class="ListSelectTh" id="thTotalSpecialLeave"><%= TimeNamingUtility.specialHolidayAbbr(params) %></th>
			<th class="ListSelectTh" id="thTotalOthers"><%= params.getName("Others") %></th>
<%
if (isSubHolidayRequestValid) {
%>
			<th class="ListSelectTh" id="thTotalCompensatoryHoliday"><%= params.getName("CompensatoryHoliday") %></th>
<%
}
%>
			<th class="ListSelectTh" id="thTotalAbsence"><%= params.getName("Absence") %></th>
<%
if (isSubHolidayRequestValid) {
%>
			<th class="ListSelectTh" id="thTotalCompensatoryHolidayBirth" rowspan="2">
				<div><%= params.getName("CompensatoryHoliday") %></div>
				<div><%= params.getName("Birth") %></div>
			</th>
			<th class="ListSelectTh" id="thTotalLegal"><%= params.getName("Legal") %></th>
			<th class="ListSelectTh" id="thTotalPrescribed"><%= params.getName("Prescribed") %></th>
<%
	if(vo.getLblTimesBirthMidnightSubHolidayday() != null){
%>
			<th class="ListSelectTh" id="thTotalMidnight"><%= params.getName("Midnight") %></th>
<%
	}
}
%>
		</tr>
		<tr>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesLegalHoliday     ()) %></td>
			<td class="SelectTd" id="lblTimesPrescribedHoliday"><%= HtmlUtility.escapeHTML(vo.getLblTimesPrescribedHoliday()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesSubstitute       ()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesPaidHoliday      ()) %></td>
			<td class="SelectTd" id="lblTimesPaidHolidayTime"><%= HtmlUtility.escapeHTML(vo.getLblTimesPaidHolidayTime()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesSpecialHoloiday  ()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesOtherHoloiday    ()) %></td>
<%
if (isSubHolidayRequestValid) {
%>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesSubHoliday       ()) %></td>
<%
}
%>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesAbsence          ()) %></td>
<%
if (isSubHolidayRequestValid) {
%>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesBirthLegalSubHolidayday()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesBirthPrescribedSubHolidayday()) %></td>
<%
	if(vo.getLblTimesBirthMidnightSubHolidayday() != null){
%>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesBirthMidnightSubHolidayday()) %></td>
<%
	}
}
%>
		</tr>
	</table>
