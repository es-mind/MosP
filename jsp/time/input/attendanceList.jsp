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
buffer       = "512kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.CalendarHtmlUtility"
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.ApprovalHistoryAction"
import = "jp.mosp.time.input.action.AttendanceCardAction"
import = "jp.mosp.time.input.action.AttendanceHistoryAction"
import = "jp.mosp.time.input.action.AttendanceListAction"
import = "jp.mosp.time.input.vo.AttendanceListVo"
import = "jp.mosp.time.management.action.ApprovalCardAction"
import = "jp.mosp.time.utils.TimeUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceListVo vo = (AttendanceListVo)params.getVo();
boolean isSubHolidayRequestValid = TimeUtility.isSubHolidayRequestValid(params);
String[][] addons = params.getProperties().getCodeArray(TimeConst.CODE_KEY_ATTENDANCE_LIST_ADDONS, false);
%>
<%
if (vo.getRollArray() != null && vo.getRollArray().length != 0) {
%>
<div class="List">
	<table class="EmployeeCodeRollTable">
		<tr>
			<td class="RollTd" id="tdFormerCode">
				<%=HtmlUtility.escapeHTML(vo.getLblPrevEmployeeCode())%>
			</td>
			<td class="RollTd" id="tdFormerButton">
<%
if (vo.getLblPrevEmployeeCode() != null && !vo.getLblPrevEmployeeCode().isEmpty()) {
%>
				<a onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFER_SEARCH_MODE%>', '<%=TimeConst.SEARCH_BACK%>'), '<%=AttendanceListAction.CMD_SEARCH%>');">
					<%=params.getName("UpperTriangular")%>
				</a>
<%
} else {
%>
					<span Class="FontGray"><%=params.getName("UpperTriangular")%></span>
<%
}
%>
			</td>
			<td class="RollTd">
				<%=PfNameUtility.employeeCode(params)%>
			</td>
			<td class="RollTd" id="tdNextButton">
<% if (vo.getLblNextEmployeeCode() != null && !vo.getLblNextEmployeeCode().isEmpty()) { %>
				<a onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFER_SEARCH_MODE %>', '<%= TimeConst.SEARCH_NEXT %>'), '<%= AttendanceListAction.CMD_SEARCH %>');">
					<%= params.getName("LowerTriangular") %>
				</a>
<% } else { %>
					<span Class="FontGray"><%= params.getName("LowerTriangular") %></span>
<% } %>
			</td>
			<td class="RollTd" id="tdNextCode">
				<%= HtmlUtility.escapeHTML(vo.getLblNextEmployeeCode()) %>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="ListHeader">
	<table class="SubTable">
		<tr>
			<td><button type="button" class="Name6Button" id="btnExport" onclick="submitFile(event, null, null, '<%= AttendanceListAction.CMD_OUTPUT %>');"><%= params.getName("AttendanceBook", "Output") %></button></td>
		</tr>
	</table>
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
<%
if (vo.getShowCommand().equals(AttendanceListAction.CMD_SHOW_APPROVAL)) {
%>
	<table class="DateTable">
		<tr>
			<td>
				&nbsp;
				<span id="pltSelectYear"><%= HtmlUtility.escapeHTML(vo.getPltSelectYear()) %></span><%= params.getName("Year") %>
				<span id="pltSelectMonth"><%= HtmlUtility.escapeHTML(vo.getPltSelectMonth()) %></span><%= params.getName("Month") %>
				<input type="hidden" id="hdnSearchCommand" value="<%= AttendanceListAction.CMD_SEARCH %>" />
			</td>
		</tr>
	</table>
<%
} else if (vo.getShowCommand().equals(AttendanceListAction.CMD_SHOW) || vo.getShowCommand().equals(AttendanceListAction.CMD_SELECT_SHOW)) {
%>
	<table class="DateChangeTable">
		<tr>
			<td>
				<a class="RollLink" id="eventFormer" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_YEAR %>', '<%= vo.getPrevMonthYear() %>', '<%= TimeConst.PRM_TRANSFERRED_MONTH %>', '<%= vo.getPrevMonthMonth() %>'), '<%= AttendanceListAction.CMD_SEARCH %>');">&lt;&lt;<%= params.getName("Ahead","Month") %></a>
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
				<input type="hidden" id="hdnSearchCommand" value="<%= AttendanceListAction.CMD_SEARCH %>" />
			</td>
			<td>
				<a class="RollLink" id="eventNext" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_YEAR %>', '<%= vo.getNextMonthYear() %>', '<%= TimeConst.PRM_TRANSFERRED_MONTH %>', '<%= vo.getNextMonthMonth() %>'), '<%= AttendanceListAction.CMD_SEARCH %>');"><%= params.getName("Next","Month") %>&gt;&gt;</a>
			</td>
			<td>
				<button type="button" class="Name3Button" id="btnReShow" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_YEAR %>', '<%= vo.getPltSelectYear() %>', '<%= TimeConst.PRM_TRANSFERRED_MONTH %>', '<%= vo.getPltSelectMonth() %>'), '<%= AttendanceListAction.CMD_SEARCH %>');"><%= params.getName("Again","Display") %></button>
			</td>
			<td class="BetweenTd" class=""></td>
			<td>
				<button type="button" class="Name2Button" id="btnReset" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_YEAR %>', '<%= vo.getThisMonthYear() %>', '<%= TimeConst.PRM_TRANSFERRED_MONTH %>', '<%= vo.getThisMonthMonth() %>'), '<%= AttendanceListAction.CMD_SEARCH %>');"><%= params.getName("Now","Month") %></button>
			</td>
		</tr>
	</table>
<%
}
%>
	<div id="divAddonHeader"></div>
</div>
<%
if (vo.getAryLblDate().length == 0) {
	return;
}
%>
<div class="List" id="divList">
	<table class="OverTable" id="tblHeader">
		<tr>
			<td class="UnderTd" colspan="18">
				<span class="TableButtonSpan">
<%
if (vo.getShowCommand().equals(AttendanceListAction.CMD_SHOW_APPROVAL)) {
%>
					<button type="button" id="btnApprove" class="Name4Button" onclick="submitRegist(event, null, checkDraftExtra, '<%= AttendanceListAction.CMD_APPROVE %>');"><%= params.getName("Approval") %></button>&nbsp;
<%
} else if (vo.getShowCommand().equals(AttendanceListAction.CMD_SHOW) || vo.getShowCommand().equals(AttendanceListAction.CMD_SELECT_SHOW)) {
%>
					<button type="button" id="btnDraft" class="Name4Button" onclick="submitRegist(event, null, checkDraftExtra, '<%= AttendanceListAction.CMD_DRAFT %>');"><%= params.getName("WorkPaper") %></button>&nbsp;
					<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, null, checkExtra, '<%= AttendanceListAction.CMD_APPLI %>');"><%= params.getName("Application") %></button>
<%
}
%>
				</span>
			</td>
		</tr>
	</table>
	<table class="UnderTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSelectTh" id="thTime"  ><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thSingle"><%= params.getName("TheWeek") %></th>
				<th class="ListSelectTh" id="thState" ><%= params.getName("Form") %></th>
				<th class="ListSelectTh" id="thInput" ><%= params.getName("StartWork") %></th>
				<th class="ListSelectTh" id="thInput" ><%= params.getName("EndWork") %></th>
				<% if(vo.isLblStartRecordTime()){%>
				<th class="ListSelectTh" id="thTime"><%= params.getName("StartWorkRecordTimeAbbr") %></th>
				<%} if(vo.isLblEndRecordTime()){ %>
				<th class="ListSelectTh" id="thTime"><%= params.getName("EndWorkRecordTimeAbbr") %></th>
				<%} %>
				<th class="ListSelectTh" id="thTime"  ><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="thTime"  ><%= params.getName("RestTime") %></th>
				<th class="ListSelectTh" id="thTime"  ><%= params.getName("GoingOut") %></th>
				<th class="ListSelectTh" id="thTime"  ><%= params.getName("LateLeaveEarly") %></th>
				<th class="ListSelectTh" id="thTime"  ><%= params.getName("Inside","Remainder") %></th>
				<th class="ListSelectTh" id="thOvertimeOut"><%= params.getName("LeftOut") %></th>
				<th class="ListSelectTh" id="thTime"  ><%= params.getName("WorkingHoliday") %></th>
				<th class="ListSelectTh" id="thTime"  ><%= params.getName("Midnight")%></th>
<%
for (String[] addon : addons) {
	String addonName = vo.getAddonColumnName().get(addon[0]);
	if (MospUtility.isEmpty(addonName) == false){
%>
				<th class="ListSelectTh" id="thTime"><%= HtmlUtility.escapeHTML(addonName) %></th>
<%
	}
}
%>
				
				<th class="ListSelectTh" id="thState" ><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thRemark"><%= params.getName("Remarks") %></th>
				<th class="ListSelectTh" id="thSingle"><input type="checkbox" id="ckbTopAllBoxCheck" onclick="allBoxChecked(this);"></th>
				<th class="ListSelectTh" id="thSingle"><%= params.getName("AttendanceCorrection") %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if (vo.getShowCommand().equals(AttendanceListAction.CMD_SHOW_APPROVAL) && vo.getAryCheckState(i)) { %>
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ApprovalCardAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= AttendanceListAction.CMD_TRANSFER %>');"><%= params.getName("Detail") %></button>
<% } else if (vo.getShowCommand().equals(AttendanceListAction.CMD_SHOW) || vo.getShowCommand().equals(AttendanceListAction.CMD_SELECT_SHOW)) { %>
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= AttendanceCardAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= AttendanceListAction.CMD_TRANSFER %>');"><%= params.getName("Detail") %></button>
<% } %>
				</td>
				<td class="ListSelectTd" title = "<%= HtmlUtility.escapeHTML(vo.getAryDate(i)) %>"><%= HtmlUtility.escapeHTML(vo.getAryLblDate          (i)) %></td>
				<td class="ListSelectTd"><span <%= vo.getAryWorkDayOfWeekStyle(i) %>><%= HtmlUtility.escapeHTML(vo.getAryLblWeek(i)) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkType      (i)) %></td>
				<td class="ListSelectTd"><span <%= vo.getAryStartTimeStyle(i) %>><%= HtmlUtility.escapeHTML(vo.getAryLblStartTime     (i)) %></span></td>
				<td class="ListSelectTd"><span <%= vo.getAryEndTimeStyle(i) %>><%= HtmlUtility.escapeHTML(vo.getAryLblEndTime       (i)) %></span></td>
				<% if(vo.isLblStartRecordTime()){%>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblStartRecordTime(i)) %></td>
				<%} if(vo.isLblEndRecordTime()){ %>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEndRecordTime(i)) %></td>
				<%} %>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTime      (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRestTime      (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPrivateTime   (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblLateLeaveEarlyTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeIn(i)) %></td>
				<td class="ListSelectTd"><span <%= vo.getAryOvertimeStyle(i) %>><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeOut(i)) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkOnHoliday (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblLateNight     (i)) %></td>
<%
for (String[] addon : addons) {
	String[] addonValues = vo.getAddonColumnValues().get(addon[0]);
	String[] addonClasses = vo.getAddonColumnClasses().get(addon[0]);
	if (addonValues != null && addonClasses != null) {
%>
				<td class="ListSelectTd<%= HtmlUtility.escapeHTML(addonClasses[i]) %>"><%= HtmlUtility.escapeHTML(addonValues[i]) %></td>
<%
	}
}
%>
<% if (vo.getAryLinkState(i)) { %>
				<td class="ListSelectTd"><a class="Link" id="approvalHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ApprovalHistoryAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= AttendanceListAction.CMD_TRANSFER %>');"><span <%= vo.getAryStateStyle(i) %>><%= vo.getAryLblState(i) %></span></a></td>
<% } else { %>
				<td class="ListSelectTd"><span <%= vo.getAryStateStyle(i) %>><%= HtmlUtility.escapeHTML(vo.getAryLblState(i)) %></span></td>
<% } %>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRemark(i)) %></td>
				<td class="ListSelectTd">
<% if (vo.getAryCheckState(i)) { %>
					<input type="checkbox" class="CheckBox" name="ckbSelect" onclick="checkChangeBox(this, true);" value="<%= vo.getAryDate(i) %>" <%= HtmlUtility.getChecked(vo.getAryDate(i), vo.getCkbSelect()) %> />
<% } %>
				</td>
				<td class="ListSelectTd"><a class="Link" id="correctionHistory1" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= AttendanceHistoryAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= AttendanceListAction.CMD_TRANSFER %>');"><%= HtmlUtility.escapeHTML(vo.getAryLblCorrection(i)) %></a></td>
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
				<th class="ListSelectTh" id="thOvertimeOut"><%= params.getName("LeftOut") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("WorkingHoliday") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Midnight") %></th>
<%
for (String[] addon : addons) {
	String addonName = vo.getAddonColumnName().get(addon[0]);
	if (MospUtility.isEmpty(addonName) == false){
%>
				<th class="ListSelectTh" id="thTime"><%= HtmlUtility.escapeHTML(addonName) %></th>
<%
	}
}
%>
				<th class="ListSelectTh" id="thState"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thRemark"><%= params.getName("Remarks") %></th>
				<th class="ListSelectTh" id="thSingle"><input type="checkbox" id="ckbBottomAllBoxCheck" onclick="allBoxChecked(this);"></th>
				<th class="ListSelectTh" id="thSingle"><%= params.getName("AttendanceCorrection") %></th>
			</tr>
			<tr id="trFooter">
				<td class="UnderTd" colspan="18">
					<span class="TableButtonSpan">
<%
if (vo.getShowCommand().equals(AttendanceListAction.CMD_SHOW_APPROVAL)) {
%>
						<button type="button" id="btnApprove" class="Name4Button" onclick="submitRegist(event, null, checkDraftExtra, '<%= AttendanceListAction.CMD_APPROVE %>');"><%= params.getName("Approval") %></button>&nbsp;
<%
} else if (vo.getShowCommand().equals(AttendanceListAction.CMD_SHOW) || vo.getShowCommand().equals(AttendanceListAction.CMD_SELECT_SHOW)) {
%>
						<button type="button" id="btnDraftBottom" class="Name4Button" onclick="submitRegist(event, null, checkDraftExtra, '<%= AttendanceListAction.CMD_DRAFT %>');"><%= params.getName("WorkPaper") %></button>&nbsp;
						<button type="button" id="btnRegistBottom" class="Name4Button" onclick="submitRegist(event, null, checkExtra, '<%= AttendanceListAction.CMD_APPLI %>');"><%= params.getName("Application") %></button>
<%
}
%>
					</span>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="divButton">
<%
if (vo.isTotalButtonVisible()) {
%>
		<button type="button" id="btnTotalBottom" class="Name2Button" onclick="submitTransfer(event, null, checkTotal, null, '<%= AttendanceListAction.CMD_TOTAL %>');"><%= params.getName("Total") %></button>
<%
}
%>
	</div>
	<% String applicationProperty = params.getApplicationProperty("AttendanceListItemJsp"); %>
	<jsp:include page="<%=	applicationProperty %>" flush="false" />
<%
for (String addonJsp : vo.getAddonJsps()) {
%>
	<jsp:include page="<%= addonJsp %>" flush="false" />
<%
}
%>
</div>
