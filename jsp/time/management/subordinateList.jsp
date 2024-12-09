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
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.comparator.settings.SubordinateAbsenceComparator"
import = "jp.mosp.time.comparator.settings.SubordinateAllHolidayComparator"
import = "jp.mosp.time.comparator.settings.SubordinateApprovalComparator"
import = "jp.mosp.time.comparator.settings.SubordinateCalcComparator"
import = "jp.mosp.time.comparator.settings.SubordinateCorrectionComparator"
import = "jp.mosp.time.comparator.settings.SubordinateLateLeaveEarlyTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateLateNightTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateOverTimeInComparator"
import = "jp.mosp.time.comparator.settings.SubordinateOverTimeOutComparator"
import = "jp.mosp.time.comparator.settings.SubordinatePaidHolidayComparator"
import = "jp.mosp.time.comparator.settings.SubordinatePrivateTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateRestTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateWorkDateComparator"
import = "jp.mosp.time.comparator.settings.SubordinateWorkOnHolidayTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateWorkTimeComaparator"
import = "jp.mosp.time.input.action.AttendanceListAction"
import = "jp.mosp.time.input.action.ScheduleReferenceAction"
import = "jp.mosp.time.management.action.SubordinateListAction"
import = "jp.mosp.time.management.vo.SubordinateListVo"
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.utils.TimeUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SubordinateListVo vo = (SubordinateListVo)params.getVo();
boolean isSubHolidayRequestValid = TimeUtility.isSubHolidayRequestValid(params);
%>
<div class="List" id="divSearch">
	<table class="InputTable" id="subordinateList_tblSearch">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%=params.getName("Employee","Search")%></span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><%=PfNameUtility.displayTerm(params)%></td>
				<td class="InputTd">
					<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
						<%=HtmlUtility.getSelectOption(vo.getAryPltRequestYear(), vo.getPltSearchRequestYear())%>
					</select>
					<%=params.getName("Year")%>
					<select class="Number2PullDown" id="pltSearchRequestMonth" name="pltSearchRequestMonth">
						<%=HtmlUtility.getSelectOption(vo.getAryPltRequestMonth(), vo.getPltSearchRequestMonth())%>
					</select>
					<%=params.getName("Month")%>&nbsp;
					<button type="button" class="Name2Button" id="btnRequestDate" onclick="submitForm(event, 'divSearchClassRoute', null, '<%=SubordinateListAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</td>
				<td class="TitleTd"><%=PfNameUtility.employeeCode(params)%></td>
				<td class="InputTd">
					<Input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode()) %>" />
				</td>
				<td class="TitleTd"><%= params.getName("Employee","FirstName") %></td>
				<td class="InputTd">
					<Input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName()) %>" />
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("WorkPlace") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchWorkPlace" name="pltSearchWorkPlace">
						<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlace(), vo.getPltSearchWorkPlace()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("EmploymentContract") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchEmployment" name="pltSearchEmployment">
						<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltSearchEmployment()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Search","Type") %></td>
				<td class="InputTd">
					<select class="Name7PullDown" id="pltSearchHumanType" name="pltSearchHumanType">
						<%= HtmlUtility.getSelectOption(vo.getAryPltHumanType(), vo.getPltSearchHumanType()) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Section") %></td>
				<td class="InputTd">
					<select class="SectionNamePullDown" id="pltSearchSection" name="pltSearchSection">
						<%= HtmlUtility.getSelectOption(vo.getAryPltSection(), vo.getPltSearchSection()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Position") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchPosition" name="pltSearchPosition">
						<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltSearchPosition()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Cutoff","State") %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltSearchCalc" name="pltSearchCalc">
						<%= HtmlUtility.getSelectOption(vo.getAryPltCalc(), vo.getPltSearchCalc()) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Ram","Approval") %></td>
				<td class="InputTd">
					<select class="Name7PullDown" id="pltSearchApproval" name="pltSearchApproval">
						<%= HtmlUtility.getSelectOption(vo.getAryPltApproval(), vo.getPltSearchApproval()) %>
					</select>
					<input type="checkbox" class="CheckBox" id="ckbYesterday" name="ckbYesterday" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbYesterday()) %> />&nbsp;<%= params.getName("BeforeDay","Until") %>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', checkSearch, '<%= SubordinateListAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="20">
					<span class="TitleTh"><%= params.getName("Employee","List") %></span>
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
					<span class="TableButtonSpan">
<% if (vo.getShowCommand().equals(SubordinateListAction.CMD_SHOW)) { %>
						<button type="button" class="Name8Button" id="btnPaidHolidayExport" onclick="submitFile(event, checkExtraForFile, null, '<%= SubordinateListAction.CMD_PAID_HOLIDAY_USAGE %>');"><%= TimeNamingUtility.paidHolidayUsageExport(params) %></button>&nbsp;
						<button type="button" class="Name5Button" id="btnExport" onclick="submitFile(event, checkExtraForFile, null, '<%= SubordinateListAction.CMD_OUTPUT %>');"><%= params.getName("AttendanceBook", "Output") %></button>&nbsp;
						<button type="button" class="Name5Button" id="btnScheduleExport" onclick="submitFile(event, checkExtraForFile, null, '<%= SubordinateListAction.CMD_SCHEDULE %>');"><%= params.getName("ScheduleBook", "Output") %></button>&nbsp;
<% } %>
						<button type="button" class="Name5Button" id="btnCalc" onclick="submitForm(event, null, checkExtra, '<%= SubordinateListAction.CMD_CALC %>');"><%= params.getName("WorkManage","Total") %></button>
					</span>
<%
}
%>
				</th>
			</tr>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeCodeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("SubordinateCode") %><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Employee","FirstName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkDateComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("GoingWork") %><%= PlatformUtility.getSortMark(SubordinateWorkDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkTimeComaparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Work") %><%= PlatformUtility.getSortMark(SubordinateWorkTimeComaparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRestTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateRestTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("RestTime") %><%= PlatformUtility.getSortMark(SubordinateRestTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPrivateTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinatePrivateTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("GoingOut") %><%= PlatformUtility.getSortMark(SubordinatePrivateTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLateLeaveEarlyTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateLateLeaveEarlyTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("LateLeaveEarly") %><%= PlatformUtility.getSortMark(SubordinateLateLeaveEarlyTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thOverTimeIn" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateOverTimeInComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Inside","OvertimeAbbr") %><%= PlatformUtility.getSortMark(SubordinateOverTimeInComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thOverTimeOut" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateOverTimeOutComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("LeftOut") %><%= PlatformUtility.getSortMark(SubordinateOverTimeOutComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkOnHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkOnHolidayTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("WorkingHoliday") %><%= PlatformUtility.getSortMark(SubordinateWorkOnHolidayTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLateNight" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateLateNightTimeComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Midnight") %><%= PlatformUtility.getSortMark(SubordinateLateNightTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPaidHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinatePaidHolidayComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= TimeNamingUtility.paidHolidayAbbr(params) %><%= PlatformUtility.getSortMark(SubordinatePaidHolidayComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thAllHoliday"  onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateAllHolidayComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Holiday") %><%= PlatformUtility.getSortMark(SubordinateAllHolidayComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thAbsence"  onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateAbsenceComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("Absence") %><%= PlatformUtility.getSortMark(SubordinateAbsenceComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApproval" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateApprovalComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("UnApproval") %><%= PlatformUtility.getSortMark(SubordinateApprovalComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCalc" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateCalcComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(SubordinateCalcComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCorrection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateCorrectionComparator.class.getName() %>'), '<%= SubordinateListAction.CMD_SORT %>')"><%= params.getName("AttendanceCorrection") %><%= PlatformUtility.getSortMark(SubordinateCorrectionComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
					<input type="checkbox" class="CheckBox" id="ckbSelect" onclick="doAllBoxChecked(this);">
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblEmployeeCode().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if (vo.getShowCommand().equals(SubordinateListAction.CMD_SHOW)) { %>
					<button type="button" class="Name1Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= AttendanceListAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= SubordinateListAction.CMD_TRANSFER %>');"><%= params.getName("Fact") %></button>
					<button type="button" class="Name1Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ScheduleReferenceAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= SubordinateListAction.CMD_TRANSFER %>');"><%= params.getName("Plan") %></button>
<% } else if (vo.getShowCommand().equals(SubordinateListAction.CMD_SHOW_APPROVED)) { %>
					<button type="button" class="Name2Button" id="btnAttendance" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= AttendanceListAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= SubordinateListAction.CMD_TRANSFER %>');"><%= params.getName("WorkManage") %></button>
<% } %>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkDate(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRestTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPrivateTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblLateLeaveEarlyTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeIn(i)) %></td>
				<td class="ListSelectTd"><span <%= vo.getAryOvertimeOutStyle(i) %>><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeOut(i)) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkOnHolidayTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblLateNightTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHoliday(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblAllHoliday(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblAbsence(i)) %></td>
				<td class="ListSelectTd"><span class="<%= vo.getClaApploval(i) %>"><%= vo.getAryLblApploval(i) %></span></td>
				<td class="ListSelectTd"><span class="<%= vo.getClaCalc(i) %>"><%= vo.getAryLblCalc(i) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCorrection(i)) %></td>
				<td class="ListSelectTd"><input type="checkbox" class="CheckBox" name="ckbSelect" value="<%= i %>" <%= HtmlUtility.getChecked(i, vo.getCkbSelect()) %> /></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<%
}
%>
