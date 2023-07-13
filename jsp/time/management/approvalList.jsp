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
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.ManagementRequestRequestDateComparator"
import = "jp.mosp.time.comparator.settings.ManagementRequestRequestInfoComparator"
import = "jp.mosp.time.comparator.settings.ManagementRequestRequestTypeComparator"
import = "jp.mosp.time.comparator.settings.ManagementRequestStageStateComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.ApprovalHistoryAction"
import = "jp.mosp.time.management.action.ApprovalCardAction"
import = "jp.mosp.time.management.action.ApprovalListAction"
import = "jp.mosp.time.management.vo.ApprovalListVo"
import = "jp.mosp.time.utils.TimeUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApprovalListVo vo = (ApprovalListVo)params.getVo();
%>
<div class="List">
	<table class="LeftListTable" id="approvalList_tblSearch">
		<tr>
			<th class="ListSelectTh"><%=params.getName("Approval","Type")%></th>
			<th class="ListSelectTh"><%=params.getName("Ram","Approval","Count","Num")%></th>
			<th class="ListSelectTh"></th>
			<td class="Divider"></td>
			<th class="ListSelectTh"><%=params.getName("Approval","Type")%></th>
			<th class="ListSelectTh"><%=params.getName("Ram","Approval","Count","Num")%></th>
			<th class="ListSelectTh"></th>
			<td class="Divider"></td>
			<th class="ListSelectTh"><%=params.getName("Approval","Type")%></th>
			<th class="ListSelectTh"><%=params.getName("Ram","Approval","Count","Num")%></th>
			<th class="ListSelectTh"></th>
		</tr>
		<tr>
			<td class="SelectTd"><%=params.getName("WorkManage","Approval")%></td>
			<td class="SelectTd" id="lblAttendance"><%=HtmlUtility.escapeHTML(vo.getLblAttendance())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnAttendance" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '<%=TimeConst.CODE_FUNCTION_WORK_MANGE%>', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
			</td>
<%
if (TimeUtility.isWorkOnHolidayRequestValid(params)) {
	// 振出・休出申請が有効の場合
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%=params.getName("SubstituteAbbr","GoingWorkAbbr","WorkingHoliday","Approval")%></td>
			<td class="SelectTd" id="lblWorkOnHoliday"><%=HtmlUtility.escapeHTML(vo.getLblWorkOnHoliday())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnWorkOnHoliday" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '<%=TimeConst.CODE_FUNCTION_WORK_HOLIDAY%>', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="4"></td>
<%
}
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%=params.getName("All","Approval")%></td>
			<td class="SelectTd" id="lblTotalApproval"><%=HtmlUtility.escapeHTML(vo.getLblTotalApproval())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnTotalApproval" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
			</td>
		</tr>
<%
if (TimeUtility.isOvertimeRequestValid(params) || TimeUtility.isSubHolidayRequestValid(params)) {
	// 残業申請が有効又は代休申請が有効の場合
%>
		<tr>
<%
if (TimeUtility.isOvertimeRequestValid(params)) {
%>
			<td class="SelectTd"><%=params.getName("OvertimeWork","Approval")%></td>
			<td class="SelectTd" id="lblOverTime"><%=HtmlUtility.escapeHTML(vo.getLblOverTime())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnOverTime" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '<%=TimeConst.CODE_FUNCTION_OVER_WORK%>', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="3"></td>
<%
}
   if (TimeUtility.isSubHolidayRequestValid(params)) {
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%=params.getName("CompensatoryHoliday","Approval")%></td>
			<td class="SelectTd" id="lblSubHoliday"><%=HtmlUtility.escapeHTML(vo.getLblSubHoliday())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnSubHoliday" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '<%=TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY%>', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="4"></td>
<%
}
%>
			<td class="Blank" colspan="4"></td>
		</tr>
		<tr>
<%
}
if (TimeUtility.isHolidayRequestValid(params)) {
	// 休暇申請が有効の場合
%>
			<td class="SelectTd"><%=params.getName("Holiday","Approval")%></td>
			<td class="SelectTd" id="lblHoliday"><%=HtmlUtility.escapeHTML(vo.getLblHoliday())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnHoliday" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '<%=TimeConst.CODE_FUNCTION_VACATION%>', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="3"></td>
<%
}
if (TimeUtility.isWorkTypeChangeRequestValid(params)) {
	// 勤務形態変更申請が有効の場合
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%=params.getName("WorkTypeAbbr","Change","Approval")%></td>
			<td class="SelectTd" id="lblWorkTypeChange"><%=HtmlUtility.escapeHTML(vo.getLblWorkTypeChange())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnWorkTypeChange" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '<%=TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE%>', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
			</td>
<%
} else if (TimeUtility.isDifferenceRequestValid(params)) {
	// 時差出勤申請が有効の場合
%>
			<td class="Divider"></td>
			<td class="SelectTd"><%=params.getName("TimeDifference","GoingWork","Approval")%></td>
			<td class="SelectTd" id="Difference"><%=HtmlUtility.escapeHTML(vo.getLblDifference())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnDifference" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '<%=TimeConst.CODE_FUNCTION_DIFFERENCE%>', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="4"></td>
<%
}
if (TimeUtility.isCancellationRequestValid(params)) {
	// 承認解除申請が有効の場合
%>
			<td class="Divider"></td>
			<th class="ListSelectTh"><%=params.getName("Approval","Release","Application")%></th>
			<td class="SelectTd" id="lblTotalCancel"><%=HtmlUtility.escapeHTML(vo.getLblTotalCancel())%><%=params.getName("Count")%></td>
			<td class="SelectTd">
				<button type="button" class="Name2Button" id="btnCancelAppli" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_TYPE%>', '', '<%=TimeConst.PRM_TRANSFERRED_GENERIC_CODE%>', '<%=PlatformConst.CODE_STATUS_CANCEL_APPLY%>'), '<%=ApprovalListAction.CMD_SEARCH%>');"><%=params.getName("Display")%></button>
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
<%=HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex())%>
<div class="FixList" id="divUpdate">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeCodeComparator.class.getName()%>'), '<%=ApprovalListAction.CMD_SORT%>')"><%=PfNameUtility.employeeCode(params)%><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= ApprovalListAction.CMD_SORT %>')"><%= params.getName("Employee","FirstName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= ApprovalListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprovalType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestTypeComparator.class.getName() %>'), '<%= ApprovalListAction.CMD_SORT %>')"><%= params.getName("Type") %><%= PlatformUtility.getSortMark(ManagementRequestRequestTypeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprovalDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestDateComparator.class.getName() %>'), '<%= ApprovalListAction.CMD_SORT %>')"><%= params.getName("Date") %><%= PlatformUtility.getSortMark(ManagementRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRequestInfo" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestInfoComparator.class.getName() %>'), '<%= ApprovalListAction.CMD_SORT %>')"><%= params.getName("Application","Information","Detail") %><%= PlatformUtility.getSortMark(ManagementRequestRequestInfoComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thState" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestStageStateComparator.class.getName() %>'), '<%= ApprovalListAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(ManagementRequestStageStateComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSingle">
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
					<input type="checkbox" class="" id="ckbSelect" onclick="doAllBoxChecked(this);">
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
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ApprovalCardAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= ApprovalListAction.CMD_TRANSFER %>');"><%= params.getName("Detail") %></button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRequestType()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRequestDate()[i]) %></td>
				<td class="ListInputTd" <%= vo.getAryBackColor()[i] %>><%= vo.getAryLblRequestInfo()[i] %></td>
				<td class="ListSelectTd">
					<a class="Link" id="linkApprovalHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ApprovalHistoryAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= ApprovalListAction.CMD_TRANSFER %>');"><span <%= vo.getAryStateStyle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span></a>
				</td>
				<td class="ListSelectTd"><input type="checkbox" class="SelectCheckBox" id="ckbSelect" name="ckbSelect" value="<%= vo.getAryWorkflow(i) %>" <%= HtmlUtility.getChecked(vo.getAryWorkflow(i), vo.getCkbSelect()) %> /></td>
			</tr>
<%
}
if (vo.getAryLblEmployeeCode().length > 0) {
%>
			<tr>
				<td class="TitleTd" colspan="9">
					<span class="TableButtonSpan">
<% if (PlatformConst.CODE_STATUS_CANCEL_APPLY.equals(vo.getApprovalType())) { %>
						<button type="button" class="Name4Button" id="btnDelete" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= ApprovalListAction.CMD_BATCH_CANCEL %>');"><%= params.getName("Approval","Release") %></button>
<% } else { %>
						<button type="button" class="Name4Button" id="btnUpdate" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= ApprovalListAction.CMD_BATCH_APPROVAL %>');"><%= params.getName("Approval") %></button>
<% } %>
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
