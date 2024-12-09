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
import = "jp.mosp.framework.utils.CalendarHtmlUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.ScheduleReferenceAction"
import = "jp.mosp.time.input.vo.ScheduleReferenceVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ScheduleReferenceVo vo = (ScheduleReferenceVo)params.getVo();
%>
<div class="ListHeader">
	<table class="SubTable">
		<tr>
			<td><button type="button" class="Name5Button" id="btnExport" onclick="submitFile(event, null, null, '<%= ScheduleReferenceAction.CMD_OUTPUT %>');"><%= params.getName("ScheduleBook", "Output") %></button></td>
		</tr>
	</table>
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
	<table class="ShortLabelTable">
		<tr>
			<td class="TitleSelectTd" id="lblApplicationSchedule"><%= params.getName("PresentTime") %><%= params.getName("Of") %><%= params.getName("Calendar") %><%= params.getName("Colon") %>&nbsp;<%= HtmlUtility.escapeHTML(vo.getLblApplicationSchedule()) %></td>
		</tr>
	</table>
	<table class="DateChangeTable">
		<tr>
			<td>
				<a class="RollLink" id="eventFormer" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_YEAR %>', '<%= vo.getPrevMonthYear() %>', '<%= TimeConst.PRM_TRANSFERRED_MONTH %>', '<%= vo.getPrevMonthMonth() %>'), '<%= ScheduleReferenceAction.CMD_SEARCH %>');">&lt;&lt;<%= params.getName("Ahead","Month") %></a>
			</td>
			<td class="RangeSelectTd">
				&nbsp;
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_MONTH, "pltSelect", "", "", false, false, true) %>
				<select class="Number4PullDown" id="pltSelectYear" name="pltSelectYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSelectYear(), vo.getPltSelectYear()) %>
				</select>
				<%= params.getName("Year") %>&nbsp;
				<select class="Number2PullDown" id="pltSelectMonth" name="pltSelectMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSelectMonth(), vo.getPltSelectMonth()) %>
				</select>
				<%= params.getName("Month") %>
				<input type="hidden" id ="hdnSearchCommand" value="<%= ScheduleReferenceAction.CMD_SEARCH %>" />
			</td>
			<td>
				<a class="RollLink" id="eventNext" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_YEAR %>', '<%= vo.getNextMonthYear() %>', '<%= TimeConst.PRM_TRANSFERRED_MONTH %>', '<%= vo.getNextMonthMonth() %>'), '<%= ScheduleReferenceAction.CMD_SEARCH %>');"><%= params.getName("Next","Month") %>&gt;&gt;</a>
			</td>
			<td class="BetweenTd"></td>
			<td>
				<button type="button" class="Name2Button" id="btnReset" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_YEAR %>', '<%= vo.getThisMonthYear() %>', '<%= TimeConst.PRM_TRANSFERRED_MONTH %>', '<%= vo.getThisMonthMonth() %>'), '<%= ScheduleReferenceAction.CMD_SEARCH %>');"><%= params.getName("Now") %><%= params.getName("Month") %></button>
			</td>
		</tr>
	</table>
</div>
<%
if (vo.getAryLblDate().length == 0) {
	return;
}
%>
<div class="List" id="calenderList">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thSingle"><%= params.getName("TheWeek") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Form") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("StartWork") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("EndWork") %></th>
				<% if(vo.isLblStartRecordTime()){%>
				<th class="ListSelectTh" id="thTime"><%= params.getName("StartWorkRecordTimeAbbr") %></th>
				<%} if(vo.isLblEndRecordTime()){ %>
				<th class="ListSelectTh" id="thTime"><%= params.getName("EndWorkRecordTimeAbbr") %></th>
				<%} %>
				<th class="ListSelectTh" id="thTime"><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("RestTime") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("GoingOut") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("LateLeaveEarly") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Inside","Remainder") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("LeftOut") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("WorkingHoliday") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Midnight") %></th>
				<th class="ListSelectTh" id="thState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thRemark"><%= params.getName("Remarks") %></th>
				<th class="ListSelectTh" id="thSingle"></th>
				<th class="ListSelectTh" id="thSingle"></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd"></td>
				<td class="ListSelectTd"  title = "<%= HtmlUtility.escapeHTML(vo.getPltSelectYear() + '/' + vo.getAryLblDate(i)) %>"><%= HtmlUtility.escapeHTML(vo.getAryLblDate     (i)) %></td>
				<td class="ListSelectTd"><span <%= vo.getAryWorkDayOfWeekStyle(i) %>><%= HtmlUtility.escapeHTML(vo.getAryLblWeek(i)) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkType (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblStartTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEndTime  (i)) %></td>
				<% if(vo.isLblStartRecordTime()){%>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<%} if(vo.isLblEndRecordTime()){ %>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<%} %>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTime (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRestTime (i)) %></td>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<td class="ListSelectTd"><%= params.getName("Hyphen") %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRemark(i)) %></td>
				<td class="ListSelectTd"></td>
				<td class="ListSelectTd"></td>
			</tr>
<%
}
%>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thSingle"><%= params.getName("TheWeek") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Form") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("StartWork") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("EndWork") %></th>
				<% if(vo.isLblStartRecordTime()){%>
				<th class="ListSelectTh" id="thTime"><%= params.getName("StartWorkRecordTimeAbbr") %></th>
				<%} if(vo.isLblEndRecordTime()){ %>
				<th class="ListSelectTh" id="thTime"><%= params.getName("EndWorkRecordTimeAbbr") %></th>
				<%} %>
				<th class="ListSelectTh" id="thTime"><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("RestTime") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("GoingOut") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("LateLeaveEarly") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Inside","Remainder") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("LeftOut") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("WorkingHoliday") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Midnight") %></th>
				<th class="ListSelectTh" id="thState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thRemark"><%= params.getName("Remarks") %></th>
				<th class="ListSelectTh" id="thSingle"></th>
				<th class="ListSelectTh" id="thSingle"></th>
			</tr>
		</tbody>
	</table>
</div>
<div class="List">
	<% String applicationProperty = params.getApplicationProperty("ScheduleReferenceItemJsp"); %>
	<jsp:include page="<%=	applicationProperty %>" flush="false" />
</div>
