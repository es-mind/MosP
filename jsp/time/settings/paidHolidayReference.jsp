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
import = "jp.mosp.time.comparator.settings.PaidHolidayMasterPaidHolidayCodeComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayMasterPaidHolidayNameComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayMasterPaidHolidayTypeComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.settings.action.PaidHolidayHistoryAction"
import = "jp.mosp.time.settings.action.PaidHolidayReferenceAction"
import = "jp.mosp.time.settings.vo.PaidHolidayReferenceVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PaidHolidayReferenceVo vo = (PaidHolidayReferenceVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
</div>
<div class="List">
	<table class="ListTable">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="10"><span class="TitleTh">
					<%= params.getName("PaidVacation","Information","FrontWithCornerParentheses") %>
					<%= HtmlUtility.escapeHTML(vo.getLblSystemDate()) %>
					<%= params.getName("BackWithCornerParentheses") %></span></th>
			</tr>
			<tr>
				<th class="ListSelectTh" id="th">
					<%= params.getName("PreviousYear","Times","Remainder","Days") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("PreviousYear","Times","Remainder","Time") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("ThisYear","Times","Remainder","Days") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("ThisYear","Times","Remainder","Time") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Provision","Days") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Provision","Time") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Abandonment","Days") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Abandonment","Time") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Use","Days") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Use","Time") %>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblFormerDate()) %><%= params.getName("Day") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblFormerTime()) %><%= params.getName("Time") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblDate()) %><%= params.getName("Day") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblTime()) %><%= params.getName("Time") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblGivingDate()) %><%= params.getName("Day") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblGivingTime()) %><%= params.getName("Time") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblCancelDate()) %><%= params.getName("Day") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblCancelTime()) %><%= params.getName("Time") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblUseDate()) %><%= params.getName("Day") %></td>
				<td class="SelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getLblUseTime()) %><%= params.getName("Time") %></td>
			</tr>
		</tbody>
	</table>
</div>
<div class="ListHeader" id="divSearch">
	<table class="SubTable">
		<tr>
			<td>
				<%= params.getName("Effectiveness","Year","Times","Colon") %>
				<select class="Number4PullDown" id="pltSelectYear" name="pltSelectYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSelectYear(), vo.getPltSelectYear()) %>
				</select>
				<label for="divSearch"><%= params.getName("Year","Times") %></label>
			</td>
			<td class="ButtonTd">
				<button type="button" id="btnRefresh" class="Name3Button" onclick="submitTransfer(event, null, null, null, '<%= PaidHolidayReferenceAction.CMD_SEARCH %>')"><%= params.getName("Refresh") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="List">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="11"><span class="TitleTh"><%= params.getName("Year","Month","Another") %></span></th>
			</tr>
			<tr>
				<th class="ListSelectTh" id="th"></th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Remainder","Days","FrontParentheses","Ahead","BackParentheses") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Remainder","Time","FrontParentheses","Ahead","BackParentheses") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Remainder","Days","FrontParentheses","Now","BackParentheses") %>
				</th>
				<th class="ListSelectTh" id="th">
					<%= params.getName("Remainder","Time","FrontParentheses","Now","BackParentheses") %>
				</th>
				<th class="ListSelectTh" id="th"><%= params.getName("Provision","Days") %></th>
				<th class="ListSelectTh" id="th"><%= params.getName("Provision","Time") %></th>
				<th class="ListSelectTh" id="th"><%= params.getName("Abandonment","Days") %></th>
				<th class="ListSelectTh" id="th"><%= params.getName("Abandonment","Time") %></th>
				<th class="ListSelectTh" id="th"><%= params.getName("Use","Days") %></th>
				<th class="ListSelectTh" id="th"><%= params.getName("Use","Time") %></th>
			</tr>
		</thead>
		<tbody>
<% for (int i = vo.getAryLblViewYearMonth().length - 1; i >= 0; i--) { %>
			<tr>
				<td class="ListSelectTh" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblViewYearMonth()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblFormerDate()[i]) %><%= params.getName("Day") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblFormerTime()[i]) %><%= params.getName("Time") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %><%= params.getName("Day") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblTime()[i]) %><%= params.getName("Time") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblGivingDate()[i]) %><%= params.getName("Day") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblGivingTime()[i]) %><%= params.getName("Time") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCancelDate()[i]) %><%= params.getName("Day") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblCancelTime()[i]) %><%= params.getName("Time") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblUseDate()[i]) %><%= params.getName("Day") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblUseTime()[i]) %><%= params.getName("Time") %></td>
			</tr>
<% } %>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" id="btnPaidHolidayGivingList" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= vo.getLblEmployeeCode() %>', '<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= vo.getTxtActiveDate() %>'), '<%= PaidHolidayHistoryAction.CMD_SHOW %>');"><%= params.getName("New","Insert") %></button>
</div>
