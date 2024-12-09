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
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.comparator.base.WorkflowTypeComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationMasterApplicationCodeComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationMasterApplicationNameComparator"
import = "jp.mosp.platform.comparator.workflow.RouteApplicationMasterRouteCodeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.workflow.action.RouteApplicationCardAction"
import = "jp.mosp.platform.workflow.action.RouteApplicationListAction"
import = "jp.mosp.platform.workflow.vo.RouteApplicationListVo"
import = "jp.mosp.platform.workflow.action.RouteCardAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
RouteApplicationListVo vo = (RouteApplicationListVo)params.getVo();
%>
<div class="List">
	<table class="InputTable" id="divSearch">
			<tr>
				<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= PfNameUtility.search(params) %></span></th>
			</tr>
			<tr>
				<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.activateDate(params) %></td>
				<td class="InputTd">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
					<label for="txtSearchActivateYear"><%= PfNameUtility.year(params) %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
					<label for="txtSearchActivateMonth"><%= PfNameUtility.month(params) %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />
					<label for="txtSearchActivateDay"><%= PfNameUtility.day(params) %></label>
				</td>
				<td class="TitleTd"><label for="pltSearchRouteStage"><%= PfNameUtility.workflowType(params) %></label></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltSearchFlowType" name="pltSearchFlowType">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_WORKFLOW_TYPE, vo.getPltSearchFlowType(), true) %>
					</select>
				</td>
				<td class="TitleTd"><label for="pltSearchInactivate"><%=PfNameUtility.inactivate(params)%></label></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="txtSearchApplicationCode"><%= PfNameUtility.routeApplicationCode(params) %></label></td>
				<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchApplicationCode" name="txtSearchApplicationCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApplicationCode()) %>"/></td>
				<td class="TitleTd"><label for="txtSearchApplicationName"><%= PfNameUtility.routeApplicationName(params) %></label></td>
				<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchApplicationName" name="txtSearchApplicationName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApplicationName()) %>"/></td>
				<td class="TitleTd"><label for="txtSearchApplicationEmployee"><%= PfNameUtility.approvedEmployeeCode(params) %></label></td>
				<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchApplicationEmployee" name="txtSearchApplicationEmployee" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApplicationEmployee()) %>"/></td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="txtSearchRouteCode"><%= PfNameUtility.routeCode(params) %></label></td>
				<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchRouteCode" name="txtSearchRouteCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteCode()) %>"/></td>
				<td class="TitleTd"><label for="txtSearchRouteName"><%= PfNameUtility.routeName(params) %></label></td>
				<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchRouteName" name="txtSearchRouteName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteName()) %>"/></td>
				<td class="TitleTd"><label for="txtSearchRouteEmployee"><%= PfNameUtility.approverEmployeeCode(params) %></label></td>
				<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchRouteEmployee" name="txtSearchRouteEmployee" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteEmployee()) %>"/></td>
			</tr>
		</table>
		<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= RouteApplicationListAction.CMD_SEARCH %>')"><%= PfNameUtility.search(params) %></button>
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
				<th class="ListSortTh"   id="thActivateDate"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator                         .class.getName() %>'), '<%= RouteApplicationListAction.CMD_SORT %>')"><%= PfNameUtility.activateDate(params)    %><%= PlatformUtility.getSortMark(ActivateDateComparator                         .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thApplicationCode"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationMasterApplicationCodeComparator.class.getName() %>'), '<%= RouteApplicationListAction.CMD_SORT %>')"><%= PfNameUtility.applicationCode(params) %><%= PlatformUtility.getSortMark(RouteApplicationMasterApplicationCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thApplicationName"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationMasterApplicationNameComparator.class.getName() %>'), '<%= RouteApplicationListAction.CMD_SORT %>')"><%= PfNameUtility.applicationName(params) %><%= PlatformUtility.getSortMark(RouteApplicationMasterApplicationNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thRouteName"			onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RouteApplicationMasterRouteCodeComparator      .class.getName() %>'), '<%= RouteApplicationListAction.CMD_SORT %>')"><%= PfNameUtility.routeName(params)       %><%= PlatformUtility.getSortMark(RouteApplicationMasterRouteCodeComparator      .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thFlowType"			onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkflowTypeComparator                         .class.getName() %>'), '<%= RouteApplicationListAction.CMD_SORT %>')"><%= PfNameUtility.type(params)            %><%= PlatformUtility.getSortMark(WorkflowTypeComparator                         .class.getName(), params) %></th>
				<th class="ListSelectTh"><%= PfNameUtility.applyRange(params) %></th>
				<th class="ListSortTh"   id="thInactivate"			onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator                           .class.getName() %>'), '<%= RouteApplicationListAction.CMD_SORT %>')"><%= PfNameUtility.inactivateAbbr(params)  %><%= PlatformUtility.getSortMark(InactivateComparator                           .class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (vo.getList().size() > 0) {
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
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblApplicationCode(i)) %>'), '<%= RouteApplicationCardAction.CMD_SELECT_SHOW %>');" ><%= PfNameUtility.detail(params) %></button>
				</td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate(i))    %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblApplicationCode(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblApplicationName(i)) %></td>
				<td class="ListInputTd">
				<% if(!PlatformConst.APPROVAL_ROUTE_SELF.equals(vo.getAryLblRouteCode()[i])){ %>
					<a class="Link" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblRouteActivateDate(i)) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblRouteCode(i)) %>'), '<%= RouteCardAction.CMD_SELECT_SHOW %>');">
				<% } %>
					<%= HtmlUtility.escapeHTML(vo.getAryLblRouteName()[i]) %>
				<% if (!PlatformConst.APPROVAL_ROUTE_SELF.equals(vo.getAryLblRouteCode()[i])) { %>
					</a>
				<% } %>
				</td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblFlowType(i)) 			%></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblApplicationLength(i))	%></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate(i)) 			%></td>
				<td class="ListSelectTd"><input type="checkbox" class="" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbRouteApplicationListId(i)) %>"></td>
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
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="4">
				<span class="TitleTh"><%= PfNameUtility.batchUpdate(params) %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.activateDate(params) %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" />
				<label for="txtUpdateActivateYear"><%= PfNameUtility.year(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />
				<label for="txtUpdateActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" />
				<label for="txtUpdateActivateDay"><%= PfNameUtility.day(params) %></label>
			</td>
			<td class="TitleTd"><%= PfNameUtility.inactivate(params) %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltUpdateInactivate" name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= RouteApplicationListAction.CMD_BATCH_UPDATE %>')"><%= PfNameUtility.update(params) %></button>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitForm(event, null, null, '<%= RouteApplicationCardAction.CMD_SHOW %>')"><%= PfNameUtility.newInsert(params) %></button>
</div>
<%
if (vo.getAryLblActivateDate().length > 0) {
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= PfNameUtility.topOfPage(params) %></a>
</div>
<%
}
%>
