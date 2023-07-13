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
import = "jp.mosp.time.management.action.RequestListAction"
import = "jp.mosp.time.management.vo.RequestListVo"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
RequestListVo vo = (RequestListVo)params.getVo();
%>
<div class="List">
	<table class="InputTable" id="requestList_tblSearch">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%=params.getName("Application","Information","Search")%></span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><%=PfNameUtility.displayTerm(params)%></td>
				<td class="InputTd" id="divSearchActiveDate">
					<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchRequestYear(), vo.getPltSearchRequestYear())%>
					</select>
					<%=params.getName("Year")%>
					<select class="Number2PullDown" id="pltSearchRequestMonth" name="pltSearchRequestMonth">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchRequestMonth(), vo.getPltSearchRequestMonth())%>
					</select>
					<%=params.getName("Month")%>
					<select class="Number2PullDown" id="pltSearchRequestDay" name="pltSearchRequestDay">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchRequestDay(), vo.getPltSearchRequestDay())%>
					</select>
					<%=params.getName("Day")%>&nbsp;
					<button type="button" class="Name2Button" id="btnRequestDate" onclick="submitForm(event, 'divSearchActiveDate', checkExtra, '<%=RequestListAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</td>
				<td class="TitleTd"><%=PfNameUtility.employeeCode(params)%></td>
				<td class="InputTd">
					<input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode())%>" />
				</td>
				<td class="TitleTd"><%=params.getName("Employee","FirstName")%></td>
				<td class="InputTd">
					<input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName())%>" />
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%=params.getName("WorkPlace")%></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchWorkPlace" name="pltSearchWorkPlace">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchWorkPlace(), vo.getPltSearchWorkPlace())%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("EmploymentContract")%></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchEmployment" name="pltSearchEmployment">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchEmployment(), vo.getPltSearchEmployment())%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("Categories")%></td>
				<td class="InputTd">
					<select class="Name6PullDown" id="pltSearchApprovalType" name="pltSearchApprovalType">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_APPROVAL_TYPE, vo.getPltSearchApprovalType(), true)%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%=params.getName("Section")%></td>
				<td class="InputTd">
					<select class="SectionNamePullDown" id="pltSearchSection" name="pltSearchSection">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchSection(), vo.getPltSearchSection())%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("Position")%></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchPosition" name="pltSearchPosition">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchPosition(), vo.getPltSearchPosition())%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("State")%></td>
				<td class="InputTd">
					<select class="Name3PullDown" id="pltSearchState" name="pltSearchState">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchState(), vo.getPltSearchState())%>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnSearch" onclick="submitForm(event, 'divSearch', checkSearch, '<%=RequestListAction.CMD_SEARCH%>')"><%=params.getName("Search")%></button>
			</td>
		</tr>
	</table>
</div>
<%=HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex())%>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeCodeComparator.class.getName()%>'), '<%=RequestListAction.CMD_SORT%>')"><%=PfNameUtility.employeeCode(params)%><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= RequestListAction.CMD_SORT %>')"><%= params.getName("Employee","FirstName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= RequestListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprovalType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestTypeComparator.class.getName() %>'), '<%= RequestListAction.CMD_SORT %>')"><%= params.getName("Type") %><%= PlatformUtility.getSortMark(ManagementRequestRequestTypeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApprovalDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestDateComparator.class.getName() %>'), '<%= RequestListAction.CMD_SORT %>')"><%= params.getName("Date") %><%= PlatformUtility.getSortMark(ManagementRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thRequestInfo" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestRequestInfoComparator.class.getName() %>'), '<%= RequestListAction.CMD_SORT %>')"><%= params.getName("Application","Information","Detail") %><%= PlatformUtility.getSortMark(ManagementRequestRequestInfoComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thState" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ManagementRequestStageStateComparator.class.getName() %>'), '<%= RequestListAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(ManagementRequestStageStateComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblEmployeeCode().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ApprovalCardAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= RequestListAction.CMD_TRANSFER %>');"><%= params.getName("Detail") %></button>
				</td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblSection()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRequestType()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRequestDate()[i]) %></td>
				<td class="ListInputTd" id="" <%-- vo.getAryBackColor()[i] --%>><%= vo.getAryLblRequestInfo()[i] %></td>
				<td class="ListSelectTd" id="">
					<a class="Link" id="linkApprovalHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= ApprovalHistoryAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= RequestListAction.CMD_TRANSFER %>');"><span <%= vo.getAryStateStyle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span></a>
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
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
