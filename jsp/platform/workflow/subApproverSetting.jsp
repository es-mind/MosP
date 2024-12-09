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
language = "java"
pageEncoding = "UTF-8"
buffer = "256kb"
autoFlush = "false"
errorPage = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.EndDateComparator"
import = "jp.mosp.platform.comparator.base.WorkflowTypeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.workflow.action.SubApproverSettingAction"
import = "jp.mosp.platform.workflow.vo.SubApproverSettingVo"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SubApproverSettingVo vo = (SubApproverSettingVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="InputTable" id="subApproverSetting_tblEdit">
		<tr>
			<th class="EditTableTh" colspan="4">
				<jsp:include page="<%= PlatformSystemConst.PATH_SYSTEM_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.substituteStartDate(params) %></td>
			<td class="InputTd" id="tdEditActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />
				<label for="txtEditActivateYear"><%= PfNameUtility.year(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />
				<label for="txtEditActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />
				<label for="txtEditActivateDay"><%= PfNameUtility.day(params) %></label>&nbsp;
				<button type="button" class="Name2Button" id="btnEditActivateDate" onclick="submitForm(event, 'tdEditActivateDate', null, '<%= SubApproverSettingAction.CMD_SET_ACTIVATION_DATE %>')">
					<%=PfNameUtility.activeteDateButton(params, vo.getModeActivateDate())%>
				</button>
			</td>
			<td class="TitleTd"><label for="pltEditSection"><%= PfNameUtility.subApproverSection(params) %></label></td>
			<td class="InputTd" id="tdNoBorderBottom">
				<select class="SectionNamePullDown" id="pltEditSection" name="pltEditSection">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditSection(), vo.getPltEditSection()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.substituteEndDate(params) %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtEditEndYear" name="txtEditEndYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEndYear()) %>" />
				<label for="txtEditEndYear"><%= PfNameUtility.year(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditEndMonth" name="txtEditEndMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEndMonth()) %>" />
				<label for="txtEditEndMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditEndDay" name="txtEditEndDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEndDay()) %>" />
				<label for="txtEditEndDay"><%= PfNameUtility.day(params) %></label>
			</td>
			<td class="TitleTd"><label for="pltEditPosition"><%= PfNameUtility.subApproverPosition(params) %></label></td>
			<td class="InputTd" id="tdNoBorderVertical">
				<select class="Name15PullDown" id="pltEditPosition" name="pltEditPosition">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditPosition(), vo.getPltEditPosition()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="pltEditWorkflowType"><%= PfNameUtility.workflowType(params) %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditWorkflowType" name="pltEditWorkflowType">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_WORKFLOW_TYPE, vo.getPltEditWorkflowType(), false) %>
				</select>
			</td>
			<td class="TitleTd"><label for="txtEditEmployeeCode"><%= PfNameUtility.subApproverEmployeeCode(params) %></label></td>
			<td class="InputTd" id="tdNoBorderVertical">
				<input type="text" class="Code10TextBox" id="txtEditEmployeeCode" name="txtEditEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEmployeeCode()) %>"/>&nbsp;
				<button type="button" class="Name2Button" id="btnEmployeeSearch" onclick="submitForm(event, null, null, '<%= SubApproverSettingAction.CMD_SET_EMPLOYEE %>')"><%= PfNameUtility.search(params) %></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="pltEditInactivate"><%=PfNameUtility.inactivate(params)%></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
			<td class="TitleTd"><label for="pltEditEmployeeName"><%= PfNameUtility.subApprover(params) %></label></td>
			<td class="InputTd" id="tdNoBorderTop">
				<select class="Name15PullDown" id="pltEditEmployeeName" name="pltEditEmployeeName">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditEmployee(), vo.getPltEditEmployeeName()) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divEdit', null, '<%= SubApproverSettingAction.CMD_REGIST %>')"><%=PfNameUtility.insert(params) %></button>
			</td>
		</tr>
	</table>
</div>
<div class="List" id="divSearch">
	<table class="InputTable" id="subApproverSetting_tblSearch">
		<tr>
			<th class="ListTableTh" colspan="4">
				<span class="TitleTh"><%= PfNameUtility.search(params) %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.displayTerm(params) %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
				<label for="txtSearchActivateYear"><%= PfNameUtility.year(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
				<label for="txtSearchActivateMonth"><%= PfNameUtility.month(params) %></label>
			</td>
			<td class="TitleTd"><label for="txtSearchSectionName"><%= PfNameUtility.subApproverSection(params) %></label></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchSectionName" name="txtSearchSectionName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchSectionName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchEmployeeName"><%= PfNameUtility.subApproverName(params) %></label></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName()) %>" />
			</td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%= PfNameUtility.inactivate(params) %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= SubApproverSettingAction.CMD_SEARCH %>')"><%= PfNameUtility.search(params) %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh"   id="thStartDate"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= PfNameUtility.startDate(params)               %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thEndDate"      onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EndDateComparator     .class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= PfNameUtility.endDate(params)                 %><%= PlatformUtility.getSortMark(EndDateComparator     .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thFlowType"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkflowTypeComparator.class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= PfNameUtility.type(params)                    %><%= PlatformUtility.getSortMark(WorkflowTypeComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeCodeComparator.class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= PfNameUtility.subApproverEmployeeCode(params) %><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= PfNameUtility.subApproverName(params)         %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thSection"      onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator .class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= PfNameUtility.subApproverSection(params)      %><%= PlatformUtility.getSortMark(SectionCodeComparator .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thInactivate"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator  .class.getName() %>'), '<%= SubApproverSettingAction.CMD_SORT %>')"><%= PfNameUtility.inactivateAbbr(params)          %><%= PlatformUtility.getSortMark(InactivateComparator  .class.getName() , params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblSubApproverNo(i)) %>'), '<%= SubApproverSettingAction.CMD_EDIT_MODE %>');"><%= PfNameUtility.select(params) %></button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEndDate     ()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkflowName()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection     ()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate  ()[i]) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblActivateDate().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= PfNameUtility.topOfPage(params) %></a>
</div>
<%
}
%>
