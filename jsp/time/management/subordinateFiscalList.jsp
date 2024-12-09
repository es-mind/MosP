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
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateFiscalOverTimeComparator"
import = "jp.mosp.time.comparator.settings.SubordinateFiscalPaidHolidayDaysComparator"
import = "jp.mosp.time.comparator.settings.SubordinateFiscalPaidHolidayRestDaysComparator"
import = "jp.mosp.time.comparator.settings.SubordinateFiscalStockHolidayDaysComparator"
import = "jp.mosp.time.comparator.settings.SubordinateFiscalStockHolidayRestDaysComparator"
import = "jp.mosp.time.comparator.settings.SubordinateFiscalSeasonHolidayDaysComparator"
import = "jp.mosp.time.comparator.settings.SubordinateFiscalSeasonHolidayRestDaysComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.management.action.SubordinateFiscalReferenceAction"
import = "jp.mosp.time.input.action.ScheduleReferenceAction"
import = "jp.mosp.time.management.action.SubordinateFiscalListAction"
import = "jp.mosp.time.management.vo.SubordinateFiscalListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SubordinateFiscalListVo vo = (SubordinateFiscalListVo)params.getVo();
%>

<div class="List" id="divSearch">
	<table class="InputTable" id="subordinateFiscalList_tblSearch">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%=params.getName("Employee","Search")%></span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><%=params.getName("Display","FiscalYear")%></td>
				<td class="InputTd">
					<select class="Number4PullDown" id="pltSearchDisplayYear" name="pltSearchDisplayYear">
						<%=HtmlUtility.getSelectOption(vo.getAryPltDisplayYear(), vo.getPltSearchDisplayYear())%>
					</select>
					<%=params.getName("FiscalYear")%>
					<button type="button" class="Name2Button" id="btnDisplayYear" onclick="submitForm(event, 'divSearchClassRoute', null, '<%=SubordinateFiscalListAction.CMD_SET_DISPLAY_YEAR%>')"><%=vo.getModeDisplayYear().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</td>
				<td class="TitleTd"><%=params.getName("Search","Type")%></td>
				<td class="InputTd">
					<select class="Name7PullDown" id="pltSearchHumanType" name="pltSearchHumanType">
						<%=HtmlUtility.getSelectOption(vo.getAryPltHumanType(), vo.getPltSearchHumanType())%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("Search","Year","Month")%></td>
				<td class="InputTd">
					<select class="Number4PullDown" id="pltSearchRequestYear" name="pltSearchRequestYear">
						<%=HtmlUtility.getSelectOption(vo.getAryPltRequestYear(), vo.getPltSearchRequestYear())%>
					</select>
					<%=params.getName("Year")%>
					<select class="Number2PullDown" id="pltSearchRequestMonth" name="pltSearchRequestMonth">
						<%=HtmlUtility.getSelectOption(vo.getAryPltRequestMonth(), vo.getPltSearchRequestMonth())%>
					</select>
					<%=params.getName("Month")%>&nbsp;
					<button type="button" class="Name2Button" id="btnSearchDate" onclick="submitForm(event, 'divSearchClassRoute', null, '<%=SubordinateFiscalListAction.CMD_SET_SEARCH_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%=PfNameUtility.employeeCode(params)%></td>
				<td class="InputTd">
					<Input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode()) %>" />
				</td>
				<td class="TitleTd"><%= params.getName("WorkPlace") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchWorkPlace" name="pltSearchWorkPlace">
						<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlace(), vo.getPltSearchWorkPlace()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("EmploymentContract") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchEmployment" name="pltSearchEmployment">
						<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltSearchEmployment()) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Employee","FirstName") %></td>
				<td class="InputTd">
					<Input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName()) %>" />
				</td>
				<td class="TitleTd"><%= params.getName("Section") %></td>
				<td class="InputTd">
					<select class="SectionNamePullDown" id="pltSearchSection" name="pltSearchSection">
						<%= HtmlUtility.getSelectOption(vo.getAryPltSection(), vo.getPltSearchSection()) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Position") %></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltSearchPosition" name="pltSearchPosition">
						<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltSearchPosition()) %>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', checkSearch, '<%= SubordinateFiscalListAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="20">
					<span class="TitleTh"><%= params.getName("Employee","List") %></span>					
				</th>
			</tr>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeCodeComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= params.getName("SubordinateCode") %><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= params.getName("Employee","FirstName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thOverTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateFiscalOverTimeComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= params.getName("OvertimeWork") %><%= PlatformUtility.getSortMark(SubordinateFiscalOverTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPaidHolidayDays" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateFiscalPaidHolidayDaysComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= params.getName("ListPaidHoliday","StartAbbr")%><%= PlatformUtility.getSortMark(SubordinateFiscalPaidHolidayDaysComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPaidHolidayRestDays" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateFiscalPaidHolidayRestDaysComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= params.getName("ListPaidHoliday","Remainder")%><%= PlatformUtility.getSortMark(SubordinateFiscalPaidHolidayRestDaysComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thStockHolidayDays" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateFiscalStockHolidayDaysComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= params.getName("ListStockHoliday","StartAbbr")%><%= PlatformUtility.getSortMark(SubordinateFiscalStockHolidayDaysComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thStockHolidayRestDays" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateFiscalStockHolidayRestDaysComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= params.getName("ListStockHoliday","Remainder")%><%= PlatformUtility.getSortMark(SubordinateFiscalStockHolidayRestDaysComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSeasonHolidayDays" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateFiscalSeasonHolidayDaysComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= vo.getLblSeasonHolidayItem() +params.getName("StartAbbr")%><%= PlatformUtility.getSortMark(SubordinateFiscalSeasonHolidayDaysComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSeasonHolidayRestDays"  onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SubordinateFiscalSeasonHolidayRestDaysComparator.class.getName() %>'), '<%= SubordinateFiscalListAction.CMD_SORT %>')"><%= vo.getLblSeasonHolidayItem() + params.getName("Remainder")%><%= PlatformUtility.getSortMark(SubordinateFiscalSeasonHolidayRestDaysComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblEmployeeCode().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= SubordinateFiscalReferenceAction.class.getName() %>', '<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= SubordinateFiscalListAction.CMD_TRANSFER %>');"><%= params.getName("Detail") %></button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblOverTime(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHolidayDays(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHolidayRestDays(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblStockHolidayDays(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblStockHolidayRestDays(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSeasonHolidayDays(i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSeasonHolidayRestDays(i)) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblEmployeeCode().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<%
}
%>
