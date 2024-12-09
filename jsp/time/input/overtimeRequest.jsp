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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.comparator.settings.RequestApproverNameComparator"
import = "jp.mosp.time.comparator.settings.RequestStateComparator"
import = "jp.mosp.time.comparator.settings.OvertimeRequestOvertimeTypeComparator"
import = "jp.mosp.time.comparator.settings.OvertimeRequestRequestDateComparator"
import = "jp.mosp.time.comparator.settings.OvertimeRequestRequestTimeComparator"
import = "jp.mosp.time.comparator.settings.OvertimeRequestResultsTimeComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.OvertimeRequestAction"
import = "jp.mosp.time.input.vo.OvertimeRequestVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
OvertimeRequestVo vo = (OvertimeRequestVo)params.getVo();
boolean isChanging = vo.isModeActivateDateFixed() == false;
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%=TimeConst.PATH_TIME_COMMON_INFO_JSP%>" flush="false" />
		</tr>
	</table>
</div>
<div class="List" id="divEdit">
	<table class="ListTable" id="overtimeRequest_tblRemainder">
		<tr>
			<td class="ListSelectTh" rowspan="2"><%=TimeNamingUtility.applicableTime(params)%></td>
			<td class="ListSelectTh"><%=TimeNamingUtility.oneWeek(params)%></td>
			<td class="ListSelectTh"><%=TimeNamingUtility.oneMonth(params)%></td>
		</tr>
		<tr>
			<td class="SelectTd" id="lblRemainderWeek"><%=HtmlUtility.escapeHTML(vo.getLblRemainderWeek())%></td>
			<td class="SelectTd" id="lblRemainderMonth"><%=HtmlUtility.escapeHTML(vo.getLblRemainderMonth())%></td>
		</tr>
	</table>
	<table class="InputTable" id="overtimeRequest_tblEdit">
		<tr>
			<th class="EditTableTh" colspan="6">
				<jsp:include page="<%=TimeConst.PATH_TIME_APPLY_INFO_JSP%>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("OvertimeWork","Year","Month","Day")%></td>
			<td class="InputTd">
				<%=CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditRequest", "", "", false, false, isChanging)%>
				<select class="Number4PullDown" id="pltEditRequestYear" name="pltEditRequestYear">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestYear(), vo.getPltEditRequestYear(), true)%>
				</select>
				<%=params.getName("Year")%>&nbsp;
				<select class="Number2PullDown" id="pltEditRequestMonth" name="pltEditRequestMonth">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestMonth(), vo.getPltEditRequestMonth())%>
				</select>
				<%=params.getName("Month")%>&nbsp;
				<select class="Number2PullDown" id="pltEditRequestDay" name="pltEditRequestDay">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestDay(), vo.getPltEditRequestDay())%>
				</select>
				<%=params.getName("Day")%>
				<button type="button" class="Name2Button" id="btnRequestDate" name="btnRequestDate" onclick="submitForm(event, null, checkDateExtra, '<%=OvertimeRequestAction.CMD_SET_OVERTIME_DATE%>');"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
			</td>
			<td class="TitleTd"><%=TimeNamingUtility.applicationTime(params)%></td>
			<td class="InputTd">
				<select class="Number2PullDown" id="pltEditRequestHour" name="pltEditRequestHour">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestHour(), vo.getPltEditRequestHour())%>
				</select>&nbsp;<%=PfNameUtility.time(params)%>
				<select class="Number2PullDown" id="pltEditRequestMinute" name="pltEditRequestMinute">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestMinute(), vo.getPltEditRequestMinute())%>
				</select>&nbsp;<%=PfNameUtility.minutes(params)%>
			</td>
			<td class="TitleTd"><%=params.getName("OvertimeWork","Type")%></td>
			<td class="InputTd">
				<select class="Name5PullDown" id="pltEditOverTimeType" name="pltEditOverTimeType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditOverTimeType(), vo.getPltEditOverTimeType())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditRequestReason"><%=params.getName("Reason")%></label></td>
			<td class="InputTd" colspan="5">
				<input type="text" class="Name33RequiredTextBox" id="txtEditRequestReason" name="txtEditRequestReason" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditRequestReason())%>" />
			</td>
		</tr>
	</table>
	<jsp:include page="<%=TimeConst.PATH_TIME_APPLY_BUTTON_JSP%>" flush="false" />
<%
// 承認個人ID利用
if(!params.isTargetApprovalUnit()){
%>
	<jsp:include page="<%=TimeConst.PATH_TIME_APPROVER_PULLDOWN_JSP%>" flush="false" />
<%
}
%>
</div>
<div class="List">
	<table class="InputTable SearchInputTable" id="overtimeRequest_tblSearch">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%=params.getName("Search")%></span>
			</th>
			<td rowspan="2" class="ButtonTd">
				<button type="button" class="Name2Button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%=OvertimeRequestAction.CMD_SEARCH%>')"><%=params.getName("Search")%></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("State")%></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltSearchState" name="pltSearchState">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchState(), vo.getPltSearchState())%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("Schedule","Excess")%></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchScheduleOver" name="pltSearchScheduleOver">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchScheduleOver(), vo.getPltSearchScheduleOver())%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("OvertimeWork","Type")%></td>
			<td class="InputTd">
				<select class="Name5PullDown" id="pltSearchOverTimeType" name="pltSearchOverTimeType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchOverTimeType(), vo.getPltSearchOverTimeType())%>
				</select>
			</td>
			<td class="TitleTd"><%=PfNameUtility.displayTerm(params)%></td>
			<td class="InputTd">
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_MONTH, "pltSearchRequest", "", "", false, false, true) %>
				<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestYear(), vo.getPltSearchRequestYear(), true) %>
				</select>
				<%= params.getName("Year") %>&nbsp;
				<select class="Number2PullDown" id="pltSearchRequestMonth" name="pltSearchRequestMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestMonth(), vo.getPltSearchRequestMonth()) %>
				</select>
				<%= params.getName("Month") %>
			</td>
		</tr>
	</table>
</div>
<div class="RequestListInfo">
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
</div>
<div class="FixList" id="divList">
	<table class="LeftListTable LeftRequestListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thRequestDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= OvertimeRequestRequestDateComparator.class.getName() %>'), '<%= OvertimeRequestAction.CMD_SORT %>')"><%= params.getName("Day") %><%= PlatformUtility.getSortMark(OvertimeRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRequestType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= OvertimeRequestOvertimeTypeComparator.class.getName() %>'), '<%= OvertimeRequestAction.CMD_SORT %>')"><%= params.getName("Type") %><%= PlatformUtility.getSortMark(OvertimeRequestOvertimeTypeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRequestTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= OvertimeRequestRequestTimeComparator.class.getName() %>'), '<%= OvertimeRequestAction.CMD_SORT %>')"><%= params.getName("Schedule") %><%= PlatformUtility.getSortMark(OvertimeRequestRequestTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thResult" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= OvertimeRequestResultsTimeComparator.class.getName() %>'), '<%= OvertimeRequestAction.CMD_SORT %>')"><%= params.getName("Performance") %><%= PlatformUtility.getSortMark(OvertimeRequestResultsTimeComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thRequestReason"><%= params.getName("Reason") %></th>
				<th class="ListSortTh" id="thState" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestStateComparator.class.getName() %>'), '<%= OvertimeRequestAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(RequestStateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprover" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestApproverNameComparator.class.getName() %>'), '<%= OvertimeRequestAction.CMD_SORT %>')"><%= params.getName("Approver") %><%= PlatformUtility.getSortMark(RequestApproverNameComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (!vo.getList().isEmpty()) {
%>
					<input type="checkbox" onclick="doAllBoxChecked(this);" />
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if(!vo.getAryLblOnOff()[i].isEmpty()) { %>
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %>', '<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeTypeCode()[i]) %>'), '<%= OvertimeRequestAction.CMD_EDIT_MODE %>');"><%= params.getName("Select") %></button>
<% } %>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblOverTimeTypeName()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblScheduleTime()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblResultTime()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRequestReason()[i]) %></td>
				<td class="ListSelectTd" id="">
					<a class="Link" id="linkApprovalHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= OvertimeRequestAction.CMD_TRANSFER %>');"><span <%= vo.getAryStateStyle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span></a>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblApprover()[i]) %></td>
				<td class="ListSelectTd">
<% if(PlatformConst.CODE_STATUS_DRAFT.equals(vo.getAryWorkflowStatus()[i]) || PlatformConst.CODE_STATUS_APPLY.equals(vo.getAryWorkflowStatus()[i])) { %>
					<input type="checkbox" class="CheckBox" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbOvertimeRequestListId()[i]) %>" />
<% } %>
				</td>
			</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
			<tr>
				<td class="TitleTd" id="tdAlignRight" colspan="9">
					<span class="TableButtonSpan">
						<button type="button" class="Name5Button" id="btnUpdate" onclick="submitRegist(event, null, checkBatchUpdateExtra, '<%= OvertimeRequestAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Application") %></button>
						<button type="button" class="Name5Button" id="btnWithdrawn" onclick="submitRegist(event, null, checkBatchWithdrawnExtra, '<%= OvertimeRequestAction.CMD_BATCH_WITHDRAWN %>');"><%= params.getName("Ram", "Approval", "TakeDown") %></button>
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
if (!vo.getList().isEmpty()) {
%>
<div class="RequestListPage">
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
</div>
<%
}
%>
