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
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.RequestApproverNameComparator"
import = "jp.mosp.time.comparator.settings.RequestStateComparator"
import = "jp.mosp.time.comparator.settings.SubHolidayRequestRequestDateComparator"
import = "jp.mosp.time.comparator.settings.SubHolidayRequestWorkDateComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.SubHolidayRequestAction"
import = "jp.mosp.time.input.vo.SubHolidayRequestVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SubHolidayRequestVo vo = (SubHolidayRequestVo)params.getVo();
boolean isChanging = vo.isModeActivateDateFixed() == false;
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
</div>
<div class="List">
<%
if (vo.getAryLblCompensationWorkDate().length > 0) {
%>
	<table class="ListTable" id="subHolidayRequest_tblRemainder">
		<tr>
			<th class="ListSelectTh"><%= params.getName("CompensatoryHoliday", "Target", "GoingWork", "Day") %></th>
			<th class="ListSelectTh"><%= params.getName("Acquisition", "TimeLimit") %></th>
			<th class="ListSelectTh"><%= params.getName("CompensatoryHoliday", "Classification") %></th>
			<th class="ListSelectTh"><%= params.getName("Acquisition", "Possible", "CompensatoryHoliday", "Type") %></th>
		</tr>
<% for (int i = 0; i < vo.getAryLblCompensationWorkDate().length; i++) { %>
		<tr>
			<td class="SelectTd"><%= vo.getAryLblCompensationWorkDate()[i] %></td>
			<td class="SelectTd"><%= vo.getAryLblCompensationExpirationDate()[i] %></td>
			<td class="SelectTd"><%= vo.getAryLblCompensationType()[i] %></td>
			<td class="SelectTd"><%= vo.getAryLblCompensationRange()[i] %></td>
		</tr>
<% } %>
	</table>
<%
}
if (vo.getAryLblCompensationDayTh().length > 0) {
%>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="13">
				<span class="TitleTh"><%= params.getName("Unused", "CompensatoryHoliday", "Days") %></span>
			</th>
		</tr>
		<tr>
			<th class="ListSelectTh"><%= params.getName("Birth", "Month") %></th>
<% for (int i = 0; i < vo.getAryLblCompensationDayTh().length; i++) { %>
			<th class="ListSelectTh"><%= HtmlUtility.escapeHTML(vo.getAryLblCompensationDayTh()[i]) %></th>
<% } %>
		</tr>
		<tr>
			<th class="ListSelectTh"><%= params.getName("DayOff") %></th>
<% for (int i = 0; i < vo.getAryLblCompensationDayForWorkOnDayOff().length; i++) { %>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCompensationDayForWorkOnDayOff()[i]) %></td>
<% } %>
		</tr>
		<tr>
			<th class="ListSelectTh"><%= params.getName("Midnight") %></th>
<% for (int i = 0; i < vo.getAryLblCompensationDayForNightWork().length; i++) { %>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCompensationDayForNightWork()[i]) %></td>
<% } %>
		</tr>
	</table>
<%
}
%>
</div>
<div class="List" id="divEdit">
	<table class="OverInputTable" id="subHolidayRequest_tblEdit">
		<tr>
			<th class="EditTableTh" colspan="4">
				<jsp:include page="<%= TimeConst.PATH_TIME_APPLY_INFO_JSP %>" flush="false" />
			</th>
		</tr>
	</table>
	<jsp:include page="<%= TimeConst.PATH_TIME_APPLY_BUTTON_JSP %>" flush="false" />
	<table class="UnderInputTable">
		<tr>
			<td class="TitleTd"><%= params.getName("CompensatoryHoliday","Day") %></td>
			<td class="InputTd" id="tdRequestDate">
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditRequest", "", "", false, false, isChanging) %>
				<select class="Number4PullDown" id="pltEditRequestYear" name="pltEditRequestYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditRequestYear(), vo.getPltEditRequestYear(), true) %>
				</select>
				<%= params.getName("Year") %>
				<select class="Number2PullDown" id="pltEditRequestMonth" name="pltEditRequestMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditRequestMonth(), vo.getPltEditRequestMonth()) %>
				</select>
				<%= params.getName("Month") %>
				<select class="Number2PullDown" id="pltEditRequestDay" name="pltEditRequestDay">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditRequestDay(), vo.getPltEditRequestDay()) %>
				</select>
				<%= params.getName("Day") %>
				<select class="Name3PullDown" id="pltEditHolidayRange" name="pltEditHolidayRange">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditHolidayRange(), vo.getPltEditHolidayRange()) %>
				</select>&nbsp;
				<button type="button" class="Name2Button" id="btnRequestDate" onclick="submitForm(event, 'null', checkDateExtra, '<%= SubHolidayRequestAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
			</td>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="tdWorkDate">
				<select class="PullDown" id="pltEditWorkDate" name="pltEditWorkDate">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditWorkDate(), vo.getPltEditWorkDate()) %>
				</select>
			</td>
		</tr>
<%
// 承認個人ID利用
if(!params.isTargetApprovalUnit()){
%>
		<jsp:include page="<%= TimeConst.PATH_TIME_APPROVER_PULLDOWN_JSP %>" flush="false" />
<%
}
%>
	</table>
</div>
<div class="List">
	<table class="InputTable SearchInputTable" id="subHolidayRequest_tblSearch">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Search") %></span>
			</th>
			<td rowspan="2" class="ButtonTd">
				<button type="button" class="Name2Button" id="btnSearch" onclick="submitForm(event, 'divSearch', null, '<%= SubHolidayRequestAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("CompensatoryHoliday","Day") %></td>
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
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd">
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_MONTH, "pltSearchWork", "", "", false, false, true) %>
				<select class="Number4PullDown" id="pltSearchWorkYear" name="pltSearchWorkYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchWorkYear(), vo.getPltSearchWorkYear(), true) %>
				</select>
				<%= params.getName("Year") %>
				<select class="Number2PullDown" id="pltSearchWorkMonth" name="pltSearchWorkMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchWorkMonth(), vo.getPltSearchWorkMonth()) %>
				</select>
				<%= params.getName("Month") %>
			</td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltSearchState" name="pltSearchState">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchState(), vo.getPltSearchState()) %>
				</select>
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
				<th class="ListSortTh" id="thRequestDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubHolidayRequestRequestDateComparator.class.getName() %>'), '<%= SubHolidayRequestAction.CMD_SORT %>')"><%= params.getName("CompensatoryHoliday","Day") %><%= PlatformUtility.getSortMark(SubHolidayRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkDate"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubHolidayRequestWorkDateComparator.class.getName() %>'), '<%= SubHolidayRequestAction.CMD_SORT %>')"><%= params.getName("GoingWork","Day") %><%= PlatformUtility.getSortMark(SubHolidayRequestWorkDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thState"       onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestStateComparator.class.getName() %>'), '<%= SubHolidayRequestAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(RequestStateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprover"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestApproverNameComparator.class.getName() %>'), '<%= SubHolidayRequestAction.CMD_SORT %>')"><%= params.getName("Approver") %><%= PlatformUtility.getSortMark(RequestApproverNameComparator.class.getName(), params) %></th>
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
for (int i = 0; i < vo.getAryLblRequestDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if(!vo.getAryLblOnOff()[i].isEmpty()) { %>
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %>', '<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblRange()[i]) %>'), '<%= SubHolidayRequestAction.CMD_EDIT_MODE %>');"><%= params.getName("Select") %></button>
<% } %>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRequestDate()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblWorkDate()[i]) %></td>
				<td class="ListSelectTd">
					<a class="Link" id="linkApprovalHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= SubHolidayRequestAction.CMD_TRANSFER %>');"><span <%= vo.getAryStateStyle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span></a>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblApprover()[i]) %></td>
				<td class="ListSelectTd">
<% if(PlatformConst.CODE_STATUS_DRAFT.equals(vo.getAryWorkflowStatus()[i]) || PlatformConst.CODE_STATUS_APPLY.equals(vo.getAryWorkflowStatus()[i])) { %>
					<input type="checkbox" class="CheckBox" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbSubHolidayRequestListId()[i]) %>" />
<% } %>
				</td>
			</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
			<tr>
				<td class="TitleTd" colspan="6">
					<span class="TableButtonSpan">
						<button type="button" class="Name5Button" id="btnUpdate" onclick="submitRegist(event, null, checkBatchUpdateExtra, '<%= SubHolidayRequestAction.CMD_BATCH_UPDATE %>');"><%= params.getName("Application") %></button>
						<button type="button" class="Name5Button" id="btnWithdrawn" onclick="submitRegist(event, null, checkBatchWithdrawnExtra, '<%= SubHolidayRequestAction.CMD_BATCH_WITHDRAWN %>');"><%= params.getName("Ram", "Approval", "TakeDown") %></button>
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
