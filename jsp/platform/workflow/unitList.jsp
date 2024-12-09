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
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalUnitMasterUnitCodeComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalUnitMasterUnitNameComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalUnitMasterUnitTypeComparator"
import = "jp.mosp.platform.comparator.workflow.ApprovalUnitMasterApproverComparator"
import = "jp.mosp.platform.workflow.action.UnitListAction"
import = "jp.mosp.platform.workflow.action.UnitCardAction"
import = "jp.mosp.platform.workflow.vo.UnitListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
UnitListVo vo = (UnitListVo)params.getVo();
%>
<div class="List">
	<table class="InputTable" id="tblSearch">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= PfNameUtility.search(params) %></span></th>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%=PfNameUtility.activateDate(params)%></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
				<label for="txtSearchActivateYear"><%=PfNameUtility.year(params)%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
				<label for="txtSearchActivateMonth"><%=PfNameUtility.month(params)%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />
				<label for="txtSearchActivateDay"><%=PfNameUtility.day(params)%></label>
			</td>
			<td class="TitleTd"><label for="pltSearchUnitType"><%= PfNameUtility.unitType(params) %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchUnitType" name="pltSearchUnitType">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_UNIT_TYPE, vo.getPltSearchUnitType(), false) %>
				</select>
			</td>
			<td class="Blank" colspan="2" rowspan="3"></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchUnitCode"><%= PfNameUtility.unitCode(params) %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchUnitCode" name="txtSearchUnitCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchUnitCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchUnitName"><%= PfNameUtility.unitName(params) %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchUnitName" name="txtSearchUnitName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchUnitName()) %>"/></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchEmployeeCode"><%= PfNameUtility.approverEmployeeCode(params) %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchApprover"><%= PfNameUtility.approverName(params) %></label></td>
			<td class="InputTd"><input type="text" class="Name10TextBox" id="txtSearchApprover" name="txtSearchApprover" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApprover()) %>"/></td>
		</tr>
		<tr>			
			<td class="TitleTd"><label for="txtSearchSectionName"><%= PfNameUtility.approverSection(params) %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchSectionName" name="txtSearchSectionName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchSectionName()) %>"/></td>		
			<td class="TitleTd"><label for="txtSearchPositoinName"><%= PfNameUtility.approverPosition(params) %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchPositoinName" name="txtSearchPositoinName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPositoinName()) %>"/></td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%= PfNameUtility.inactivate(params) %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
	<tr>
		<td class="ButtonTd">
			<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'tblSearch', null, '<%= UnitListAction.CMD_SEARCH %>')"><%= PfNameUtility.search(params) %></button>
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
				<th class="ListSortTh"   id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator              .class.getName() %>'), '<%= UnitListAction.CMD_SORT %>')"><%= PfNameUtility.activateDate(params)   %><%= PlatformUtility.getSortMark(ActivateDateComparator              .class.getName(), params) %></th>
				<th class="ListSortTh"   id="thUnitCode"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalUnitMasterUnitCodeComparator.class.getName() %>'), '<%= UnitListAction.CMD_SORT %>')"><%= PfNameUtility.code(params)           %><%= PlatformUtility.getSortMark(ApprovalUnitMasterUnitCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thUnitName"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalUnitMasterUnitNameComparator.class.getName() %>'), '<%= UnitListAction.CMD_SORT %>')"><%= PfNameUtility.name(params)           %><%= PlatformUtility.getSortMark(ApprovalUnitMasterUnitNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thUnitType"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalUnitMasterUnitTypeComparator.class.getName() %>'), '<%= UnitListAction.CMD_SORT %>')"><%= PfNameUtility.type(params)           %><%= PlatformUtility.getSortMark(ApprovalUnitMasterUnitTypeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thApprover"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApprovalUnitMasterApproverComparator.class.getName() %>'), '<%= UnitListAction.CMD_SORT %>')"><%= PfNameUtility.approver(params)       %><%= PlatformUtility.getSortMark(ApprovalUnitMasterApproverComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thInactivate"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator                .class.getName() %>'), '<%= UnitListAction.CMD_SORT %>')"><%= PfNameUtility.inactivateAbbr(params) %><%= PlatformUtility.getSortMark(InactivateComparator                .class.getName(), params) %></th>
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
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblUnitCode()[i]) %>'), '<%= UnitCardAction.CMD_SELECT_SHOW %>');" ><%= PfNameUtility.detail(params) %></button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblUnitCode()[i])     %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblUnitName()[i])     %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblUnitType()[i])     %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblApproval()[i])     %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i])   %></td>
				<td class="ListSelectTd"><input type="checkbox" class="" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbUnitListId()[i]) %>"></td>
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
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= UnitListAction.CMD_BATCH_UPDATE %>')"><%= PfNameUtility.update(params) %></button>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitForm(event, null, null, '<%= UnitCardAction.CMD_SHOW %>')"><%= PfNameUtility.newInsert(params) %></button>
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
