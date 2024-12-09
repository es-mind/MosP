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
import = "jp.mosp.time.comparator.settings.RequestApproverNameComparator"
import = "jp.mosp.time.comparator.settings.RequestStateComparator"
import = "jp.mosp.time.comparator.settings.DifferenceRequestAroundTypeComparator"
import = "jp.mosp.time.comparator.settings.DifferenceRequestRequestDateComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.DifferenceRequestAction"
import = "jp.mosp.time.input.vo.DifferenceRequestVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
DifferenceRequestVo vo = (DifferenceRequestVo)params.getVo();
boolean isChanging = vo.isModeActivateDateFixed() == false;
boolean isSearchChanging = PlatformUtility.isActivateDateChanging(vo.getJsSearchActivateDateMode());
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%=TimeConst.PATH_TIME_COMMON_INFO_JSP%>" flush="false" />
		</tr>
	</table>
</div>
<div class="List" id="divEdit">
	<table class="InputTable">
		<tr>
			<th class="EditTableTh" colspan="6">
				<jsp:include page="<%=TimeConst.PATH_TIME_APPLY_INFO_JSP%>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("TimeDifference")%><%=params.getName("GoingWork")%><%=params.getName("Day")%></td>
			<td class="InputTd" id="tdWorkDate">
				<%=CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditRequest", "", "", false, false, isChanging)%>
				<select class="Number4PullDown" id="pltEditRequestYear" name="pltEditRequestYear">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestYear(), vo.getPltEditRequestYear())%>
				</select>
				<%=params.getName("Year")%>
				<select class="Number2PullDown" id="pltEditRequestMonth" name="pltEditRequestMonth">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestMonth(), vo.getPltEditRequestMonth())%>
				</select>
				<%=params.getName("Month")%>
				<select class="Number2PullDown" id="pltEditRequestDay" name="pltEditRequestDay">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestDay(), vo.getPltEditRequestDay())%>
				</select>
				<%=params.getName("Day")%>&nbsp;
				<button type="button" id="btnWorkDate" class="Name2Button" onclick="submitForm(event, 'null', checkDateExtra, '<%=DifferenceRequestAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
			</td>
			<td class="TitleTd"><%=params.getName("TimeDifference")%><%=params.getName("GoingWork")%><%=params.getName("Type")%></td>
			<td class="InputTd">
				<%=vo.getLblWorkTypeName()%><%=params.getName("ArrowRight")%>
				<select class="DifferencePullDown" id="pltEditDifferenceType" name="pltEditDifferenceType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditDifferenceType(), vo.getPltEditDifferenceType())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=PfNameUtility.term(params)%><%=params.getName("End")%><%=params.getName("Day")%></td>
			<td class="InputTd" id="tdEndDate">
				<%=CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditEnd", "", "", false, false, true)%>
				<select class="Number4PullDown" id="pltEditEndYear" name="pltEditEndYear">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndYear(), vo.getPltEditEndYear())%>
				</select>
				<%=params.getName("Year")%>
				<select class="Number2PullDown" id="pltEditEndMonth" name="pltEditEndMonth">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndMonth(), vo.getPltEditEndMonth())%>
				</select>
				<%=params.getName("Month")%>
				<select class="Number2PullDown" id="pltEditEndDay" name="pltEditEndDay">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndDay(), vo.getPltEditEndDay())%>
				</select>
				<%=params.getName("Day")%>
				<input type="checkbox" class="CheckBox" id="ckbEndDate" name="ckbEndDate" value="<%=MospConst.CHECKBOX_ON%>" <%=HtmlUtility.getChecked(vo.getCkbEndDate())%>>&nbsp;<span><%=PfNameUtility.term(params)%><%=params.getName("Given")%></span>
			</td>
			<td class="TitleTd"><%=params.getName("TimeDifference")%><%=params.getName("GoingWork")%><%=params.getName("Moment")%></td>
			<td class="InputTd">
				<select class="Number2PullDown" id="pltEditRequestHour" name="pltEditRequestHour">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestHour(), vo.getPltEditRequestHour())%>
				</select>&nbsp;<%=params.getName("Hour")%>
				<select class="Number2PullDown" id="pltEditRequestMinute" name="pltEditRequestMinute">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditRequestMinute(), vo.getPltEditRequestMinute())%>
				</select>&nbsp;<%=params.getName("Minutes")%>
				<%=params.getName("Wave")%>
				<input type="text" class="Number2TextBox" id="lblEndTimeHour" name="lblEndTimeHour" value="<%=HtmlUtility.escapeHTML(vo.getLblEndTimeHour())%>"/>&nbsp;<%=params.getName("Hour")%>
				<input type="text" class="Number2TextBox" id="lblEndTimeMinute" name="lblEndTimeMinute" value="<%=HtmlUtility.escapeHTML(vo.getLblEndTimeMinute())%>"/>&nbsp;<%=params.getName("Minutes")%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditRequestReason"><%=params.getName("Reason")%></label></td>
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
	<table class="InputTable SearchInputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Search")%></span>
			</th>
			<td rowspan="2" class="ButtonTd">
				<button type="button" class="Name2Button" id="btnSearch" onclick="submitTransfer(event, null, null, null, '<%=DifferenceRequestAction.CMD_SEARCH%>');"><%=params.getName("Search")%></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Approval")%><%=params.getName("Situation")%></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltSearchState" name="pltSearchState">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchState(), vo.getPltSearchState())%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("Work")%><%=params.getName("Form")%></td>
			<td class="InputTd">
				<select class="WorkTypePullDown" id="pltSearchWorkType" name="pltSearchWorkType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchWorkType(), vo.getPltSearchWorkType())%>
				</select>
			</td>
			<td class="TitleTd"><%=PfNameUtility.displayTerm(params)%></td>
			<td class="InputTd">
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_MONTH, "pltSearchRequest", "", "", false, false, isSearchChanging) %>
				<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestYear(), vo.getPltSearchRequestYear()) %>
				</select>
				<%= params.getName("Year") %>&nbsp;
				<select class="Number2PullDown" id="pltSearchRequestMonth" name="pltSearchRequestMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestMonth(), vo.getPltSearchRequestMonth()) %>
				</select>
				<%= params.getName("Month") %>&nbsp;
				<button type="button" id="btnRequestDate" class="Name2Button" onclick="submitTransfer(event, null, null, null, '<%= DifferenceRequestAction.CMD_SET_VIEW_PERIOD %>');"><%= vo.getJsSearchActivateDateMode().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
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
				<th class="ListSortTh" id="thRequestDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= DifferenceRequestRequestDateComparator.class.getName() %>'), '<%= DifferenceRequestAction.CMD_SORT %>')"><%= params.getName("GoingWork") %><%= params.getName("Day") %><%= PlatformUtility.getSortMark(DifferenceRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRequestType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= DifferenceRequestAroundTypeComparator.class.getName() %>'), '<%= DifferenceRequestAction.CMD_SORT %>')"><%= params.getName("Type") %><%= PlatformUtility.getSortMark(DifferenceRequestAroundTypeComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thRequestTime"><%= params.getName("TimeDifference") %><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="thRequestReason"><%= params.getName("Reason") %></th>
				<th class="ListSortTh" id="thState" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestStateComparator.class.getName() %>'), '<%= DifferenceRequestAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(RequestStateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprover" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestApproverNameComparator.class.getName() %>'), '<%= DifferenceRequestAction.CMD_SORT %>')"><%= params.getName("Approver") %><%= PlatformUtility.getSortMark(RequestApproverNameComparator.class.getName(), params) %></th>
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
for (int i = 0; i < vo.getAryLblDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if(!vo.getAryLblOnOff()[i].isEmpty()) { %>
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %>','<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= HtmlUtility.escapeHTML(vo.getLblEmployeeCode()) %>'), '<%= DifferenceRequestAction.CMD_EDIT_MODE %>');"><%= params.getName("Select") %></button>
<% } %>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRequestType()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTime()[i]) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRequestReason()[i]) %></td>
				<td class="ListSelectTd">
					<a class="Link" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= DifferenceRequestAction.CMD_TRANSFER %>');">
						<span <%= vo.getAryStateStyle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span>
					</a>
				</td>
				<td class="ListSelectTd"><%= vo.getAryLblApprover()[i] %></td>
				<td class="ListSelectTd">
<% if(PlatformConst.CODE_STATUS_DRAFT.equals(vo.getAryWorkflowStatus()[i]) || PlatformConst.CODE_STATUS_APPLY.equals(vo.getAryWorkflowStatus()[i])) { %>
					<input type="checkbox" class="CheckBox" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbDifferenceRequestListId()[i]) %>" />
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
						<button type="button" class="Name5Button" id="btnUpdate" onclick="submitRegist(event, null, checkBatchUpdateExtra, '<%= DifferenceRequestAction.CMD_BATCH_UPDATE %>');"><%= params.getName("Application") %></button>
						<button type="button" class="Name5Button" id="btnWithdrawn" onclick="submitRegist(event, null, checkBatchWithdrawnExtra, '<%= DifferenceRequestAction.CMD_BATCH_WITHDRAWN %>');"><%= params.getName("Ram", "Approval", "TakeDown") %></button>
					</span>
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
