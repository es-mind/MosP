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
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalRouteMasterApprovalCountComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalRouteMasterRouteCodeComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalRouteMasterRouteNameComparator"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.workflow.action.RouteCardAction"
import = "jp.mosp.platform.workflow.action.RouteListAction"
import = "jp.mosp.platform.workflow.vo.RouteListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
RouteListVo vo = (RouteListVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
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
			<td class="TitleTd"><label for="pltSearchRouteStage"><%= PfNameUtility.stageNumber(params) %></label></td>
			<td class="InputTd">
				<select class="Number2PullDown" id="pltSearchRouteStage" name="pltSearchRouteStage">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_APPROVAL_COUNT, vo.getPltSearchRouteStage(), true) %>
				</select>
			</td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%= PfNameUtility.inactivate(params) %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchRouteCode"><%= PfNameUtility.routeCode(params) %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchRouteCode" name="txtSearchRouteCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchRouteName"><%= PfNameUtility.routeName(params) %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchRouteName" name="txtSearchRouteName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchRouteName()) %>"/></td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchUnitCode"><%= PfNameUtility.unitCode(params) %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchUnitCode" name="txtSearchUnitCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchUnitCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchUnitName"><%= PfNameUtility.unitName(params) %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchUnitName" name="txtSearchUnitName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchUnitName()) %>"/></td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= RouteListAction.CMD_SEARCH %>')"><%= PfNameUtility.search(params) %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh"   id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator                    .class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= PfNameUtility.activateDate(params)   %><%= PlatformUtility.getSortMark(ActivateDateComparator                    .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thRouteCode"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalRouteMasterRouteCodeComparator    .class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= PfNameUtility.routeCode(params)      %><%= PlatformUtility.getSortMark(ApprovalRouteMasterRouteCodeComparator    .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thRouteName"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalRouteMasterRouteNameComparator    .class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= PfNameUtility.routeName(params)      %><%= PlatformUtility.getSortMark(ApprovalRouteMasterRouteNameComparator    .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thRouteStage"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalRouteMasterApprovalCountComparator.class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= PfNameUtility.approvalCount(params)  %><%= PlatformUtility.getSortMark(ApprovalRouteMasterApprovalCountComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thFirstUnit"><%= PfNameUtility.firstUnit(params) %></th>
				<th class="ListSelectTh" id="thEndUnit"  ><%= PfNameUtility.lastUnit(params)  %></th>
				<th class="ListSortTh"   id="thInactivate"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator                      .class.getName() %>'), '<%= RouteListAction.CMD_SORT %>')"><%= PfNameUtility.inactivateAbbr(params) %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
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
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblRouteCode()[i]) %>'), '<%= RouteCardAction.CMD_SELECT_SHOW %>');" ><%= PfNameUtility.detail(params) %></button>
				</td>
				<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRouteCode()[i])    %></td>
				<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRouteName()[i])    %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRouteStage()[i])   %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblFirstUnit()[i])    %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEndUnit()[i])      %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i])   %></td>
				<td class="ListSelectTd"><input type="checkbox" class="" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbRouteListId()[i]) %>"></td>
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
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= RouteListAction.CMD_BATCH_UPDATE %>')"><%= PfNameUtility.update(params) %></button>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitForm(event, null, null, '<%= RouteCardAction.CMD_SHOW %>')"><%= PfNameUtility.newInsert(params) %></button>
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
