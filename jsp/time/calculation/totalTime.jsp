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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.calculation.action.CutoffErrorListAction"
import = "jp.mosp.time.calculation.action.TotalTimeAction"
import = "jp.mosp.time.calculation.action.TotalTimeListAction"
import = "jp.mosp.time.calculation.vo.TotalTimeVo"
import = "jp.mosp.time.comparator.settings.TotalTimeCutoffListCutoffAbbrComparator"
import = "jp.mosp.time.comparator.settings.TotalTimeCutoffListCutoffCodeComparator"
import = "jp.mosp.time.comparator.settings.TotalTimeCutoffListCutoffNameComparator"
import = "jp.mosp.time.comparator.settings.TotalTimeCutoffListCutoffStateComparator"
import = "jp.mosp.time.constant.TimeConst"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TotalTimeVo vo = (TotalTimeVo)params.getVo();
%>
<div class="List">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("CutoffDate","Search") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Total","Month") %></td>
			<td class="InputTd">
				<select class="Number4PullDown" id="pltEditRequestYear" name="pltEditRequestYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditRequestYear(), vo.getPltEditRequestYear()) %>
				</select> <%= params.getName("Year") %>
				<select class="Number2PullDown" id="pltEditRequestMonth" name="pltEditRequestMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditRequestMonth(), vo.getPltEditRequestMonth()) %>
				</select> <%= params.getName("Month") %>&nbsp;
				<button type="button" id="btnRequestDate" class="Name2Button" onclick="submitForm(event, '', null, '<%= TotalTimeAction.CMD_SET_TOTAL_MONTH %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
			</td>
			<td class="TitleTd"><%= params.getName("CutoffDate") %></td>
			<td class="InputTd">
				<select class="Name4PullDown" id="pltEditCutoffDate" name="pltEditCutoffDate">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_CUTOFF_DATE, vo.getPltEditCutoffDate(), true) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("CutoffDate","Code") %></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtEditCutoffCode" name="txtEditCutoffCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditCutoffCode()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("CutoffDate","Name") %></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtEditCutoffName" name="txtEditCutoffName" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditCutoffName()) %>" />
			</td>
			<td class="TitleTd"><%= params.getName("Cutoff","State") %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditCutoffState" name="pltEditCutoffState">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_CUTOFFSTATE, vo.getPltEditCutoffState(), true) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= TotalTimeAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List" id="divList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="8">
<%
if (vo.getTxtLblRequestYear().isEmpty()) {
%>
					<span class="TitleTh"><%= params.getName("CutoffDate","Information") %></span>
<%
} else {
%>
					<span class="TitleTh">
						<%= params.getName("CutoffDate","Information") %>
						<%= params.getName("FrontWithCornerParentheses") %><%= HtmlUtility.escapeHTML(vo.getTxtLblRequestYear()) %><%= params.getName("Year") %>
						<%= HtmlUtility.escapeHTML(vo.getTxtLblRequestMonth()) %><%= params.getName("Month","BackWithCornerParentheses") %>
					</span>
<%
}
%>
				</th>
			</tr>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thCutoffCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= TotalTimeCutoffListCutoffCodeComparator.class.getName() %>'), '<%= TotalTimeAction.CMD_SORT %>')"><%= params.getName("CutoffDate","Code") %><%= PlatformUtility.getSortMark(TotalTimeCutoffListCutoffCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCutoffName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= TotalTimeCutoffListCutoffNameComparator.class.getName() %>'), '<%= TotalTimeAction.CMD_SORT %>')"><%= params.getName("CutoffDate","Name") %><%= PlatformUtility.getSortMark(TotalTimeCutoffListCutoffNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCutoffDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= TotalTimeCutoffListCutoffAbbrComparator.class.getName() %>'), '<%= TotalTimeAction.CMD_SORT %>')"><%= params.getName("CutoffDate") %><%= PlatformUtility.getSortMark(TotalTimeCutoffListCutoffAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thCutoffState" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= TotalTimeCutoffListCutoffStateComparator.class.getName() %>'), '<%= TotalTimeAction.CMD_SORT %>')"><%= params.getName("Cutoff","State") %><%= PlatformUtility.getSortMark(TotalTimeCutoffListCutoffStateComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSelectTh" id="thButton"></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblCutoffCode().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_YEAR %>', '<%= HtmlUtility.escapeHTML(vo.getTotalTimeRequestYear()) %>', '<%= TimeConst.PRM_TRANSFERRED_MONTH %>' , '<%= HtmlUtility.escapeHTML(vo.getTotalTimeRequestMonth()) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblCutoffCode()[i]) %>'), '<%= TotalTimeListAction.CMD_SELECT_SHOW %>');"><%= params.getName("Detail") %></button>
				</td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCutoffCode(i)) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCutoffName(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCutoffDate(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCutoffState(i)) %></td>
				<td class="ListSelectTd">
<% if (vo.getAryCutoffState(i) == TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) { %>
					<button type="button" class="Name2Button" onclick="submitTransfer(event,null, confirmCutoff, new Array('<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblCutoffCode(i)) %>'), '<%= TotalTimeAction.CMD_TEMP_TIGHTEN %>');"><%= params.getName("Provisional","Cutoff") %></button>
<% } %>
				</td>
				<td class="ListSelectTd">
<% if (vo.getAryCutoffState(i) == TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) { %>
					<button type="button" class="Name2Button" onclick="submitTransfer(event, null, confirmCutoff, new Array('<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblCutoffCode(i)) %>'), '<%= TotalTimeAction.CMD_DECIDE %>');"><%= params.getName("Definition") %></button>
<% } %>
				</td>
				<td class="ListSelectTd" id="tdRelease">
<% if (vo.getAryCutoffState(i) != TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) { %>
					<button type="button" class="Name2Button" onclick="submitTransfer(event, null, confirmCutoff, new Array('<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblCutoffCode(i)) %>'), '<%= TotalTimeAction.CMD_REMOVE %>');"><%= params.getName("Release") %></button>
<% } %>
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblCutoffCode().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<%
}
%>
