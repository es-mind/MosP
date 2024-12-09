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
import = "jp.mosp.time.comparator.settings.WorkTypeChangeRequestRequestDateComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeChangeRequestWorkTypeComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.WorkTypeChangeRequestAction"
import = "jp.mosp.time.input.vo.WorkTypeChangeRequestVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
WorkTypeChangeRequestVo vo = (WorkTypeChangeRequestVo)params.getVo();
boolean isChanging = vo.isModeActivateDateFixed() == false;
boolean isSearchChanging = PlatformUtility.isActivateDateChanging(vo.getModeSearchActivateDate());
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%=TimeConst.PATH_TIME_COMMON_INFO_JSP%>" flush="false" />
		</tr>
	</table>
</div>
<div class="List" id="divEdit">
	<table class="InputTable" id="workTypeChangeRequest_tblEdit">
		<tr>
			<th class="EditTableTh" colspan="6">
				<jsp:include page="<%=TimeConst.PATH_TIME_APPLY_INFO_JSP%>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("GoingWork", "Day")%></td>
			<td class="InputTd" id="tdWorkDate">
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
				<%=params.getName("Day")%>&nbsp;
				<button type="button" id="btnWorkDate" class="Name2Button" onclick="submitForm(event, 'null', checkDateExtra, '<%=WorkTypeChangeRequestAction.CMD_SET_ACTIVATION_DATE%>');"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
			</td>
			<td class="TitleTd"><%=PfNameUtility.term(params)%><%=params.getName("End", "Day")%></td>
			<td class="InputTd" id="tdEndDate">
				<%=CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditEnd", "", "", false, false, true)%>
				<select class="Number4PullDown" id="pltEditEndYear" name="pltEditEndYear">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndYear(), vo.getPltEditEndYear(), true)%>
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
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Change", "Later", "Work", "Form")%></td>
			<td class="InputTd" colspan="3">
				<%=vo.getLblWorkTypeName()%><%=params.getName("ArrowRight")%>
				<select class="WorkTypePullDown" id="pltEditWorkType" name="pltEditWorkType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditWorkType(), vo.getPltEditWorkType())%>
				</select>
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
	<table class="InputTable SearchInputTable" id="workTypeChangeRequest_tblSearch">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Search")%></span>
			</th>
			<td rowspan="2" class="ButtonTd">
				<button type="button" class="Name2Button" id="btnSearch" onclick="submitTransfer(event, null, null, null, '<%=WorkTypeChangeRequestAction.CMD_SEARCH%>');"><%=params.getName("Search")%></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Approval", "Situation")%></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltSearchState" name="pltSearchState">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_APPROVAL_STATE, vo.getPltSearchState(), true)%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("Change", "Later", "Work", "Form")%></td>
			<td class="InputTd">
				<select class="WorkTypePullDown" id="pltSearchWorkType" name="pltSearchWorkType">
					<option value=""></option>
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchWorkType(), vo.getPltSearchWorkType())%>
				</select>
			</td>
			<td class="TitleTd"><%=PfNameUtility.displayTerm(params)%></td>
			<td class="InputTd">
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_MONTH, "pltSearchRequest", "", "", false, false, isSearchChanging) %>
				<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestYear(), vo.getPltSearchRequestYear(), true) %>
				</select>
				<%= params.getName("Year") %>&nbsp;
				<select class="Number2PullDown" id="pltSearchRequestMonth" name="pltSearchRequestMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchRequestMonth(), vo.getPltSearchRequestMonth()) %>
				</select>
				<%= params.getName("Month") %>&nbsp;
				<button type="button" id="btnRequestDate" class="Name2Button" onclick="submitTransfer(event, null, null, null, '<%= WorkTypeChangeRequestAction.CMD_SET_VIEW_PERIOD %>');"><%= vo.getModeSearchActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
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
				<th class="ListSortTh" id="thRequestDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeChangeRequestRequestDateComparator.class.getName() %>'), '<%= WorkTypeChangeRequestAction.CMD_SORT %>');"><%= params.getName("GoingWork", "Day") %><%= PlatformUtility.getSortMark(WorkTypeChangeRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeChangeRequestWorkTypeComparator.class.getName() %>'), '<%= WorkTypeChangeRequestAction.CMD_SORT %>');"><%= params.getName("Change", "Later", "Work", "Form") %><%= PlatformUtility.getSortMark(WorkTypeChangeRequestWorkTypeComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thRequestReason"><%= params.getName("Reason") %></th>
				<th class="ListSortTh" id="thState" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestStateComparator.class.getName() %>'), '<%= WorkTypeChangeRequestAction.CMD_SORT %>');"><%= params.getName("State") %><%= PlatformUtility.getSortMark(RequestStateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprover" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestApproverNameComparator.class.getName() %>'), '<%= WorkTypeChangeRequestAction.CMD_SORT %>');"><%= params.getName("Approver") %><%= PlatformUtility.getSortMark(RequestApproverNameComparator.class.getName(), params) %></th>
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
for (int i = 0; i < vo.getAryDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if(!vo.getAryOnOff()[i].isEmpty()) { %>
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryDate()[i]) %>'), '<%= WorkTypeChangeRequestAction.CMD_EDIT_MODE %>');"><%= params.getName("Select") %></button>
<% } %>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblDateAndDay()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkType()[i]) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblRequestReason()[i]) %></td>
				<td class="ListSelectTd">
					<a class="Link" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= WorkTypeChangeRequestAction.CMD_TRANSFER %>');">
						<span <%= vo.getAryStateStyle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span>
					</a>
				</td>
				<td class="ListSelectTd"><%= vo.getAryLblApprover()[i] %></td>
				<td class="ListSelectTd">
<% if (PlatformConst.CODE_STATUS_DRAFT.equals(vo.getAryWorkflowStatus()[i]) || PlatformConst.CODE_STATUS_APPLY.equals(vo.getAryWorkflowStatus()[i])) { %>
					<input type="checkbox" class="CheckBox" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbWorkTypeChangeRequestId()[i]) %>" />
<% } %>
				</td>
			</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
			<tr>
				<td class="TitleTd" colspan="7">
					<span class="TableButtonSpan">
						<button type="button" class="Name5Button" id="btnUpdate" onclick="submitRegist(event, null, checkBatchUpdateExtra, '<%= WorkTypeChangeRequestAction.CMD_BATCH_UPDATE %>');"><%= params.getName("Application") %></button>
						<button type="button" class="Name5Button" id="btnWithdrawn" onclick="submitRegist(event, null, checkBatchWithdrawnExtra, '<%= WorkTypeChangeRequestAction.CMD_BATCH_WITHDRAWN %>');"><%= params.getName("Ram", "Approval", "TakeDown") %></button>
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
