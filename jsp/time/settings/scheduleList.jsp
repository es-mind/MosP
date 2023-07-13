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
import="jp.mosp.framework.base.MospParams"
import="jp.mosp.framework.constant.MospConst"
import="jp.mosp.framework.utils.HtmlUtility"
import="jp.mosp.platform.comparator.base.InactivateComparator"
import="jp.mosp.platform.constant.PlatformConst"
import="jp.mosp.platform.utils.PlatformUtility"
import="jp.mosp.time.comparator.settings.ScheduleMasterFiscalYearComparator"
import="jp.mosp.time.comparator.settings.ScheduleMasterScheduleAbbrComparator"
import="jp.mosp.time.comparator.settings.ScheduleMasterScheduleCodeComparator"
import="jp.mosp.time.comparator.settings.ScheduleMasterScheduleNameComparator"
import="jp.mosp.time.settings.action.ScheduleCardAction"
import="jp.mosp.time.settings.action.ScheduleListAction"
import="jp.mosp.time.settings.vo.ScheduleListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ScheduleListVo vo = (ScheduleListVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Search") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><label for="pltSearchFiscalYear"><%= params.getName("FiscalYear") %></label></td>
			<td class="InputTd">
				<select class="Number4PullDown" id="pltSearchFiscalYear" name="pltSearchFiscalYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchFiscalYear(), vo.getPltSearchFiscalYear()) %>
				</select>
				<%= params.getName("FiscalYear") %>
			</td>
			<td class="TitleTd"><label for="txtSearchScheduleCode"><%= params.getName("Calendar", "Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchScheduleCode" name="txtSearchScheduleCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchScheduleCode()) %>" /></td>
			<td class="TitleTd"><label for="txtSearchScheduleName"><%= params.getName("Calendar", "Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchScheduleName" name="txtSearchScheduleName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchScheduleName()) %>" /></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchScheduleAbbr"><%= params.getName("Calendar", "Abbreviation") %></label></td>
			<td class="InputTd"><input type="text" class="Byte6TextBox" id="txtSearchScheduleAbbr" name="txtSearchScheduleAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchScheduleAbbr()) %>" /></td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%= params.getName("Effectiveness", "Slash", "Inactivate") %></label></td>
			<td class="InputTd">
			<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
				<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
			</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= ScheduleListAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
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
				<th class="ListSortTh" id="thFiscalYear" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ScheduleMasterFiscalYearComparator.class.getName() %>'), '<%= ScheduleListAction.CMD_SORT %>')"><%= params.getName("FiscalYear") %><%= PlatformUtility.getSortMark(ScheduleMasterFiscalYearComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thScheduleTypeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ScheduleMasterScheduleCodeComparator.class.getName() %>'), '<%= ScheduleListAction.CMD_SORT %>')"><%= params.getName("Calendar", "Code") %><%= PlatformUtility.getSortMark(ScheduleMasterScheduleCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thScheduleName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ScheduleMasterScheduleNameComparator.class.getName() %>'), '<%= ScheduleListAction.CMD_SORT %>')"><%= params.getName("Calendar", "Name") %><%= PlatformUtility.getSortMark(ScheduleMasterScheduleNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thScheduleAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ScheduleMasterScheduleAbbrComparator.class.getName() %>'), '<%= ScheduleListAction.CMD_SORT %>')"><%= params.getName("Calendar", "Abbreviation") %><%= PlatformUtility.getSortMark(ScheduleMasterScheduleAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= ScheduleListAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence", "Slash", "InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblFiscalYear().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblScheduleCode()[i]) %>'), '<%= ScheduleCardAction.CMD_SELECT_SHOW %>')"><%= params.getName("Detail") %></button>
				</td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblFiscalYear()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblScheduleCode()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblScheduleName()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblScheduleAbbr()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i]) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblFiscalYear().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, null, '<%= ScheduleCardAction.CMD_SHOW %>');"><%= params.getName("New", "Insert") %></button>
</div>
<%
if (vo.getAryLblFiscalYear().length > 0) {
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular", "TopOfPage") %></a>
</div>
<%
}
%>
