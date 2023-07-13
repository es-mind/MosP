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
import = "jp.mosp.framework.utils.CalendarHtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.RequestApproverNameComparator"
import = "jp.mosp.time.comparator.settings.RequestStateComparator"
import = "jp.mosp.time.comparator.settings.WorkOnHolidayRequestRequestDateComparator"
import = "jp.mosp.time.comparator.settings.WorkOnHolidayRequestRequestTimeComparator"
import = "jp.mosp.time.comparator.settings.WorkOnHolidayRequestSubstituteDate1Comparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.WorkOnHolidayRequestAction"
import = "jp.mosp.time.input.vo.WorkOnHolidayRequestVo"
import = "jp.mosp.framework.utils.CalendarHtmlUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
WorkOnHolidayRequestVo vo = (WorkOnHolidayRequestVo)params.getVo();
boolean isChanging = vo.isModeActivateDateFixed() == false;
boolean isSubstituteChanging = vo.isModeActivateDateFixed();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%=TimeConst.PATH_TIME_COMMON_INFO_JSP%>" flush="false" />
		</tr>
	</table>
</div>
<div class="List" id="divEdit">
	<table class="InputTable" id="workOnHolidayRequest_tblEdit">
		<tr>
			<th class="EditTableTh" colspan="4">
				<jsp:include page="<%=TimeConst.PATH_TIME_APPLY_INFO_JSP%>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("GoingWork","Day")%></td>
			<td class="InputTd" colspan="3">
				<%=CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditRequest", "", "", false, false, isChanging)%>
				<select class="Number4PullDown" id="pltEditRequestYear" name="pltEditRequestYear">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestYear(), vo.getPltEditRequestYear(), true)%>
				</select>
				<%=params.getName("Year")%>
				<select class="Number2PullDown" id="pltEditRequestMonth" name="pltEditRequestMonth">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestMonth(), vo.getPltEditRequestMonth())%>
				</select>
				<%=params.getName("Month")%>
				<select class="Number2PullDown" id="pltEditRequestDay" name="pltEditRequestDay">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestDay(), vo.getPltEditRequestDay())%>
				</select>
				<%=params.getName("Day")%>
				<button type="button" class="Name2Button" id="btnRequestDate" onclick="submitForm(event, null, checkDateExtra, '<%=WorkOnHolidayRequestAction.CMD_SET_ACTIVATION_DATE%>');">
					<%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%>
				</button>
				<select class="Name13PullDown" id="pltEditSubstitute" name="pltEditSubstitute">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_WORKONHOLIDAY_SUBSTITUTE, vo.getPltEditSubstitute(), false)%>
				</select>&nbsp;
<%
if (vo.isModeHalfSubstitute()) {
%>
				<select class="Name2PullDown" id="pltEditSubstituteWorkRange" name="pltEditSubstituteWorkRange">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_SUBSTITUTE_WORK_RANGE, vo.getPltEditSubstituteWorkRange(), false)%>
				</select>&nbsp;
<%
}
%>
			</td>
		</tr>
		<tr id="trSubstitute">
			<td class="TitleTd"><%=params.getName("Application", "Content")%></td>
			<td class="InputTd" colspan="3">
				<%=params.getName("Transfer", "Day", "Colon")%><!--params.getName("No1")1") %>-->
				<%=CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditSubstitute1", "", "", false, false, isSubstituteChanging)%>
				<select class="Number4PullDown" id="pltEditSubstitute1Year" name="pltEditSubstitute1Year">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditSubstitute1Year(), vo.getPltEditSubstitute1Year(), true)%>
				</select>
				<%=params.getName("Year")%>
				<select class="Number2PullDown" id="pltEditSubstitute1Month" name="pltEditSubstitute1Month">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditSubstitute1Month(), vo.getPltEditSubstitute1Month())%>
				</select>
				<%=params.getName("Month")%>
				<select class="Number2PullDown" id="pltEditSubstitute1Day" name="pltEditSubstitute1Day">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditSubstitute1Day(), vo.getPltEditSubstitute1Day())%>
				</select>
				<%=params.getName("Day")%>
<%
if (vo.isModeHalfSubstitute()) {
%>
				<select class="Name2PullDown" id="pltEditSubstitute1Range" name="pltEditSubstitute1Range">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_SUBSTITUTE_HOLIDAY_RANGE, vo.getPltEditSubstitute1Range(), false)%>
				</select>
<%
}
%>
				<span id="spanWorkType">
					<%=params.getName("Change", "Later", "Work", "Form", "ArrowRight")%>
					<%=HtmlUtility.getSelectTag("WorkTypeFullNamePullDown", "pltEditWorkType", "pltEditWorkType", vo.getPltEditWorkType(), vo.getAryPltEditWorkType(), false)%>
				</span>
			</td>
		</tr>
		<tr id="trWorkOnDayOff">
			<td class="TitleTd"><%=params.getName("Application", "Content")%></td>
			<td class="InputTd" colspan="3">
				<%=params.getName("WorkingHoliday", "Schedule", "Moment", "Colon")%>
				<select class="Number2PullDown" id="pltEditStartHour" name="pltEditStartHour">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditStartHour(), vo.getPltEditStartHour())%>
				</select>&nbsp;<%=params.getName("Hour")%>
				<select class="Number2PullDown" id="pltEditStartMinute" name="pltEditStartMinute">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditStartMinute(), vo.getPltEditStartMinute())%>
				</select>&nbsp;<%=params.getName("Minutes")%>&nbsp;<%=params.getName("Wave")%>
				<select class="Number2PullDown" id="pltEditEndHour" name="pltEditEndHour">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndHour(), vo.getPltEditEndHour())%>
				</select>&nbsp;<%=params.getName("Hour")%>
				<select class="Number2PullDown" id="pltEditEndMinute" name="pltEditEndMinute">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndMinute(), vo.getPltEditEndMinute())%>
				</select>&nbsp;<%=params.getName("Minutes")%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditRequestReason"><%=params.getName("Application","Reason")%></label></td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Name32RequiredTextBox" id="txtEditRequestReason" name="txtEditRequestReason" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditRequestReason())%>"/>
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
	<table class="InputTable SearchInputTable" id="workOnHolidayRequest_tblSearch">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Search")%></span>
			</th>
			<td rowspan="2" class="ButtonTd">
				<button type="button" class="Name2Button" id="btnSearch" onclick="submitTransfer(event, null, null, null, '<%=WorkOnHolidayRequestAction.CMD_SEARCH%>');"><%=params.getName("Search")%></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("SubstituteAbbr","GoingWorkAbbr","Slash","WorkingHoliday")%></td>
			<td class="InputTd">
				<select class="Name13PullDown" id="pltSearchSubstitute" name="pltSearchSubstitute">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_WORKONHOLIDAY_SUBSTITUTE, vo.getPltSearchSubstitute(), true)%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("State")%></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltSearchState" name="pltSearchState">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchState(), vo.getPltSearchState())%>
				</select>
			</td>
			<td class="TitleTd"><%=PfNameUtility.displayTerm(params)%></td>
			<td class="InputTd">
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_MONTH, "pltSearchRequest", "", "", false, false, true) %>
				<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestYear(), vo.getPltSearchRequestYear(), true) %>
				</select>
				<%= params.getName("Year") %>
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
<div class="FixList">
	<table class="LeftListTable LeftRequestListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thRequestDate"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkOnHolidayRequestRequestDateComparator.class.getName() %>'), '<%= WorkOnHolidayRequestAction.CMD_SORT %>')"><%= params.getName("GoingWork","Day") %><%= PlatformUtility.getSortMark(WorkOnHolidayRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRequestTime"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkOnHolidayRequestRequestTimeComparator.class.getName() %>'), '<%= WorkOnHolidayRequestAction.CMD_SORT %>')"><%= params.getName("Schedule") %><%= PlatformUtility.getSortMark(WorkOnHolidayRequestRequestTimeComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thRequestReason"><%= params.getName("Reason") %></th>
				<th class="ListSortTh" id="thSubstituteDate1" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkOnHolidayRequestSubstituteDate1Comparator.class.getName() %>'), '<%= WorkOnHolidayRequestAction.CMD_SORT %>')"><%= params.getName("Transfer","Day") %><!-- <%= params.getName("No1") %> --><%= PlatformUtility.getSortMark(WorkOnHolidayRequestSubstituteDate1Comparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thState"           onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestStateComparator.class.getName() %>'), '<%= WorkOnHolidayRequestAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(RequestStateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprover"        onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestApproverNameComparator.class.getName() %>'), '<%= WorkOnHolidayRequestAction.CMD_SORT %>')"><%= params.getName("Approver") %><%= PlatformUtility.getSortMark(RequestApproverNameComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (!vo.getList().isEmpty()) {
%>
					<input type="checkbox" class="CheckBox" onclick="doAllBoxChecked(this);">
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblWorkDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if(!vo.getAryLblOnOff()[i].isEmpty()) { %>
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblWorkDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= HtmlUtility.escapeHTML(vo.getLblEmployeeCode()) %>'), '<%= WorkOnHolidayRequestAction.CMD_EDIT_MODE %>');"><%= params.getName("Select") %></button>
<% } %>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblWorkDate(i)) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRequestTime(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRequestReason(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSubstituteDate1(i)) %></td>
				<td class="ListSelectTd">
					<a class="Link" id="linkApprovalHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= WorkOnHolidayRequestAction.CMD_TRANSFER %>');"><span <%= vo.getAryStateStyle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span></a>
				</td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblApprover()[i]) %></td>
				<td class="ListSelectTd">
<% if(PlatformConst.CODE_STATUS_DRAFT.equals(vo.getAryWorkflowStatus()[i]) || PlatformConst.CODE_STATUS_APPLY.equals(vo.getAryWorkflowStatus()[i])) { %>
					<input type="checkbox" class="" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbWorkOnHolidayRequestListId()[i]) %>" />
<% } %>
				</td>
			</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
			<tr>
				<td class="TitleTd" colspan="8">
					<span class="TableButtonSpan">
						<button type="button" class="Name5Button" id="btnUpdate" onclick="submitRegist(event, null, checkBatchUpdateExtra, '<%= WorkOnHolidayRequestAction.CMD_BATCH_UPDATE %>');"><%= params.getName("Application") %></button>
						<button type="button" class="Name5Button" id="btnWithdrawn" onclick="submitRegist(event, null, checkBatchWithdrawnExtra, '<%= WorkOnHolidayRequestAction.CMD_BATCH_WITHDRAWN %>');"><%= params.getName("Ram", "Approval", "TakeDown") %></button>
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
