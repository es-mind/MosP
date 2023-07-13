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
import = "jp.mosp.time.comparator.settings.ManagementRequestRequestDateComparator"
import = "jp.mosp.time.comparator.settings.ManagementRequestRequestInfoComparator"
import = "jp.mosp.time.comparator.settings.ManagementRequestRequestTypeComparator"
import = "jp.mosp.time.comparator.settings.ManagementRequestStageStateComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.CancellationRequestAction"
import = "jp.mosp.time.input.vo.CancellationRequestVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
CancellationRequestVo vo = (CancellationRequestVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%=TimeConst.PATH_TIME_COMMON_INFO_JSP%>" flush="false" />
		</tr>
	</table>
</div>
<div class="List" id="divEdit">
	<table id="cancellationRequest_tblEdit" class="InputTable">
		<tr>
			<th colspan="6" class="EditTableTh">
				<span class="TitleTh"><%=params.getName("Application")%></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Date")%></td>
			<td class="InputTd" colspan="3"><%=HtmlUtility.escapeHTML(vo.getLblRequestDate())%></td>
			<td class="TitleTd"><%=params.getName("Application","Type")%></td>
			<td class="InputTd"><%=HtmlUtility.escapeHTML(vo.getLblRequestType())%></td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Application","Information","Detail")%></td>
			<td class="InputTd" colspan="3"><%=vo.getLblRequestInfo()%></td>
			<td class="TitleTd"><%=params.getName("State")%></td>
			<td class="InputTd"><%=HtmlUtility.escapeHTML(vo.getLblState())%></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditRequestReason"><%=params.getName("Approval", "Release", "Reason")%></label></td>
			<td class="InputTd" colspan="5">
				<input type="text" class="Name33RequiredTextBox" id="txtEditRequestReason" name="txtEditRequestReason" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditRequestReason())%>">
				<input type="checkbox" class="CheckBox" id="ckbWithdrawn" name="ckbWithdrawn" value="<%=MospConst.CHECKBOX_ON%>" <%=HtmlUtility.getChecked(vo.getCkbWithdrawn())%>>&nbsp;<%=params.getName("WithdrawnAppli")%>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tbody>
			<tr>
				<td class="ButtonTd">
					<button type="button" class="Name2Button" id="btnRegist" onclick="submitRegist(event, 'divEdit', null, '<%=CancellationRequestAction.CMD_APPLI%>');"><%=params.getName("Release")%></button>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="List">
	<table class="InputTable SearchInputTable" id="cancellationRequest_tblSearch">
		<tr>
			<th class="ListTableTh" colspan="4">
				<span class="TitleTh"><%=params.getName("Search")%></span>
			</th>
			<td rowspan="2" class="ButtonTd">
				<button type="button" class="Name2Button" id="btnSearch" onclick="submitForm(event, 'divSearch', checkExtra, '<%=CancellationRequestAction.CMD_SEARCH%>')"><%=params.getName("Search")%></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Application","Type")%></td>
			<td class="InputTd">
					<select class="Name6PullDown" id="pltSearchApprovalType" name="pltSearchApprovalType">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_APPROVAL_TYPE, vo.getPltSearchApprovalType(), true)%>
					</select>
			</td>
			<td class="TitleTd"><%=PfNameUtility.displayTerm(params)%></td>
			<td class="InputTd">
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltSearchRequest", "", "", false, false, true) %>
				<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestYear(), vo.getPltSearchRequestYear(), true) %>
				</select>
				<%= params.getName("Year") %>&nbsp;
				<select class="Number2PullDown" id="pltSearchRequestMonth" name="pltSearchRequestMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestMonth(), vo.getPltSearchRequestMonth()) %>
				</select>
				<%= params.getName("Month") %>&nbsp;
				<select class="Number2PullDown" id="pltSearchRequestDay" name="pltSearchRequestDay">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestDay(), vo.getPltSearchRequestDay()) %>
				</select>
				<%= params.getName("Day") %>
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
				<th class="ListSortTh" id="thRequestDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestDateComparator.class.getName() %>'), '<%= CancellationRequestAction.CMD_SORT %>')"><%= params.getName("Date") %><%= PlatformUtility.getSortMark(ManagementRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRequestType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestTypeComparator.class.getName() %>'), '<%= CancellationRequestAction.CMD_SORT %>')"><%= params.getName("Type") %><%= PlatformUtility.getSortMark(ManagementRequestRequestTypeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRequestInfo" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestInfoComparator.class.getName() %>'), '<%= CancellationRequestAction.CMD_SORT %>')"><%= params.getName("Application","Information","Detail") %><%= PlatformUtility.getSortMark(ManagementRequestRequestInfoComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thState" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestStageStateComparator.class.getName() %>'), '<%= CancellationRequestAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(ManagementRequestStageStateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprover" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestApproverNameComparator.class.getName() %>'), '<%= CancellationRequestAction.CMD_SORT %>')"><%= params.getName("Approver") %><%= PlatformUtility.getSortMark(RequestApproverNameComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblRequestDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= CancellationRequestAction.CMD_EDIT_MODE %>');"><%= params.getName("Select") %></button>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRequestDate()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRequestType()[i]) %></td>
				<td class="ListInputTd" id="" <%-- vo.getAryBackColor()[i] --%>><%= vo.getAryLblRequestInfo()[i] %></td>
				<td class="ListSelectTd" id="">
					<a class="Link" id="linkApprovalHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= CancellationRequestAction.CMD_TRANSFER %>');"><span <%= vo.getAryWorkflow()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span></a>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblApproverName()[i]) %></td>
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
