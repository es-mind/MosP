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
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.calculation.action.TotalTimeCardAction"
import = "jp.mosp.time.calculation.action.TotalTimeListAction"
import = "jp.mosp.time.calculation.vo.TotalTimeListVo"
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
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.AttendanceHistoryAction"
import = "jp.mosp.time.utils.TimeNamingUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TotalTimeListVo vo = (TotalTimeListVo)params.getVo();
%>
<jsp:include page="<%=TimeConst.PATH_TIME_TOTAL_JSP%>" flush="false" />
<div class="List">
<table class="InputTable">
	<thead>
		<tr>
			<th class="ListTableTh" colspan="4">
				<span class="TitleTh"><%=params.getName("Employee","Search")%></span>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="TitleTd"><%=PfNameUtility.employeeCode(params)%></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtEditFromEmployeeCode" name="txtEditFromEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditFromEmployeeCode()) %>" />
				<%= params.getName("Wave") %>
				<input type="text" class="Code10TextBox" id="txtEditToEmployeeCode" name="txtEditToEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditToEmployeeCode()) %>" />
			</td>
			<td class="TitleTd"><%= params.getName("Employee","FirstName") %></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtEditEmployeeName" name="txtEditEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEmployeeName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("WorkPlace") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEditWorkPlace" name="pltEditWorkPlace">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditWorkPlace(), vo.getPltEditWorkPlace()) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("EmploymentContract") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEditEmployment" name="pltEditEmployment">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditEmployment(), vo.getPltEditEmployment()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Section") %></td>
			<td class="InputTd">
				<select class=SectionNamePullDown id="pltEditSection" name="pltEditSection">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditSection(), vo.getPltEditSection()) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("Position") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEditPosition" name="pltEditPosition">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditPosition(), vo.getPltEditPosition()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Ram","Approval") %></td>
			<td class="InputTd">
				<select class="Name7PullDown" id="pltEditApproval" name="pltEditApproval">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditApproval(), vo.getPltEditApproval()) %>
				</select>
				<input type="checkbox" class="CheckBox" id="ckbYesterday" name="ckbYesterday" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbYesterday()) %> />&nbsp;<%= params.getName("BeforeDay","Until") %>
			</td>
			<td class="TitleTd"><%= params.getName("Cutoff","State") %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditCalc" name="pltEditCalc">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditCalc(), vo.getPltEditCalc()) %>
				</select>
			</td>
		</tr>
	</tbody>
</table>
<table class="ButtonTable">
	<tr>
		<td class="ButtonTd" id="">
			<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', checkSearch, '<%= TotalTimeListAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
		</td>
	</tr>
</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List" id="divList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="20">
					<span class="TitleTh"><%= params.getName("jp.mosp.platform.human.vo.HumanListVo") %></span>
					<span class="TableButtonSpan">
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
						<button type="button" class="Name5Button" id="btnExport" onclick="submitFile(event, checkExtraForFile, null, '<%= TotalTimeListAction.CMD_OUTPUT_FORMS %>');"><%= params.getName("AttendanceBook", "Output") %></button>
						<button type="button" class="Name5Button" id="btnCalc" onclick="submitForm(event, null, checkExtra, '<%= TotalTimeListAction.CMD_CALC %>');"><%= params.getName("WorkManage","Total") %></button>
<%
}
%>
					</span>
				</th>
			</tr>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeCodeComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("Code") %><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("Employee","FirstName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkDateComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("GoingWork") %><%= PlatformUtility.getSortMark(SubordinateWorkDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkTimeComaparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("Work") %><%= PlatformUtility.getSortMark(SubordinateWorkTimeComaparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRestTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateRestTimeComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("RestTime") %><%= PlatformUtility.getSortMark(SubordinateRestTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPrivateTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinatePrivateTimeComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("GoingOut") %><%= PlatformUtility.getSortMark(SubordinatePrivateTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLateLeaveEarlyTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateLateLeaveEarlyTimeComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("LateLeaveEarly") %><%= PlatformUtility.getSortMark(SubordinateLateLeaveEarlyTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thOverTimeIn" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateOverTimeInComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("Inside","OvertimeAbbr") %><%= PlatformUtility.getSortMark(SubordinateOverTimeInComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thOverTimeOut" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateOverTimeOutComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("LeftOut") %><%= PlatformUtility.getSortMark(SubordinateOverTimeOutComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkOnHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateWorkOnHolidayTimeComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("WorkingHoliday") %><%= PlatformUtility.getSortMark(SubordinateWorkOnHolidayTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLateNight" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateLateNightTimeComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("Midnight") %><%= PlatformUtility.getSortMark(SubordinateLateNightTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPaidHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinatePaidHolidayComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= TimeNamingUtility.paidHolidayAbbr(params) %><%= PlatformUtility.getSortMark(SubordinatePaidHolidayComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thAllHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateAllHolidayComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("Holiday") %><%= PlatformUtility.getSortMark(SubordinateAllHolidayComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thAbsence" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateAbsenceComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("Absence") %><%= PlatformUtility.getSortMark(SubordinateAbsenceComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApproval" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateApprovalComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("UnApproval") %><%= PlatformUtility.getSortMark(SubordinateApprovalComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCalc" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateCalcComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(SubordinateCalcComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCorrection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateCorrectionComparator.class.getName() %>'), '<%= TotalTimeListAction.CMD_SORT %>')"><%= params.getName("AttendanceCorrection") %><%= PlatformUtility.getSortMark(SubordinateCorrectionComparator.class.getName(), params) %></th>
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
<% if (vo.getAryNeedDetail(i)) { %>
					<button type="button" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= TotalTimeCardAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= TotalTimeListAction.CMD_TRANSFER %>');"><%= params.getName("Detail") %></button>
<% } %>
				</td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkDate(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRestTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPrivateTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblLateLeaveEarlyTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeIn(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeOut(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkOnHolidayTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblLateNightTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHoliday(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblAllHoliday(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblAbsence(i)) %></td>
				<td class="ListSelectTd"><span class="<%= vo.getClaApploval(i) %>"><%= HtmlUtility.escapeHTML(vo.getAryLblApploval(i)) %></span></td>
				<td class="ListSelectTd"><span class="<%= vo.getClaCalc(i) %>"><%= HtmlUtility.escapeHTML(vo.getAryLblCalc(i)) %></span></td>
				<td class="ListSelectTd"><a class="Link" id="linkCorecctionHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= AttendanceHistoryAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= TotalTimeListAction.CMD_TRANSFER %>');"><%= HtmlUtility.escapeHTML(vo.getAryLblCorrection()[i]) %></a></td>
				<td class="ListSelectTd"><input type="checkbox" class="SelectCheckBox" id="ckbSelect" name="ckbSelect" value="<%= i %>" <%= HtmlUtility.getChecked(i, vo.getCkbSelect()) %> /></td>
			</tr>
<%
}
if (vo.getAryLblEmployeeCode().length > 0) {
%>
			<tr>
				<td class="TitleTd" colspan="20">
					<span class="TableButtonSpan">
						<button type="button" class="Name4Button" id="btnDraft" name="btnDraft" onclick="submitRegist(event, 'divList', checkExtra, '<%= TotalTimeListAction.CMD_TEMPORARY_TIGHTENING %>')"><%= params.getName("Provisional","Cutoff") %></button>&nbsp;
						<button type="button" class="Name4Button" id="btnRelease" name="btnRelease" onclick="submitRegist(event, 'divList', checkExtra, '<%= TotalTimeListAction.CMD_RELEASE %>')"><%= params.getName("Release") %></button>
					</span>
				</td>
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
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
