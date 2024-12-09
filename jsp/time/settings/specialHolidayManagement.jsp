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
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.HolidayManagementListHolidayCodeComparator"
import = "jp.mosp.time.comparator.settings.HolidayManagementListHolidayLimitComparator"
import = "jp.mosp.time.comparator.settings.HolidayManagementListHolidayRemainderComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.settings.action.SpecialHolidayHistoryAction"
import = "jp.mosp.time.settings.action.SpecialHolidayManagementAction"
import = "jp.mosp.time.settings.vo.SpecialHolidayManagementVo"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SpecialHolidayManagementVo vo = (SpecialHolidayManagementVo)params.getVo();
%>
<div class="List">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Search")%></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdActivateDate"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("ActivateDate")%></td>
			<td class="InputTd" id="tdSpecialSearchHolidayDate">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>"/>
				<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>"/>
				<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())%>"/>
				<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>&nbsp;
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'tdSpecialSearchHolidayDate', null, '<%=SpecialHolidayManagementAction.CMD_SET_ACTIVATION_DATE%>');"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
			</td>
			<td class="TitleTd"><%=PfNameUtility.employeeCode(params)%></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode()) %>"/>
			</td>
			<td class="TitleTd"><%= params.getName("Employee","FirstName") %></td>
			<td class="InputTd">
				<input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName()) %>"/>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("WorkPlace") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchWorkPlace" name="pltSearchWorkPlace">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchWorkPlace(), vo.getPltSearchWorkPlace()) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("EmploymentContract") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchEmployment" name="pltSearchEmployment">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchEmployment(), vo.getPltSearchEmployment()) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Section") %></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltSearchSection" name="pltSearchSection">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchSection(), vo.getPltSearchSection()) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("Position") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchPosition" name="pltSearchPosition">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchPosition(), vo.getPltSearchPosition()) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitTransfer(event, null, null, null, '<%= SpecialHolidayManagementAction.CMD_SEARCH %>');"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List">
	<table class="LeftListTable" id="list">
		<tr>
			<th class="ListSelectTh" id="thButton"></th>
			<th class="ListSortTh" id="thActivateDate"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= SpecialHolidayManagementAction.CMD_SORT %>');"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thEmployeeName"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= SpecialHolidayManagementAction.CMD_SORT %>');"><%= params.getName("Employee","FirstName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thSection"			onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= SpecialHolidayManagementAction.CMD_SORT %>');"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thHolidayType"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayManagementListHolidayCodeComparator.class.getName() %>'), '<%= SpecialHolidayManagementAction.CMD_SORT %>');"><%= params.getName("Holiday","Classification") %><%= PlatformUtility.getSortMark(HolidayManagementListHolidayCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thHolidayRemainder"	onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayManagementListHolidayRemainderComparator.class.getName() %>'), '<%= SpecialHolidayManagementAction.CMD_SORT %>');"><%= params.getName("Remainder","Days") %><%= PlatformUtility.getSortMark(HolidayManagementListHolidayRemainderComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thHolidayLimit"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayManagementListHolidayLimitComparator.class.getName() %>'), '<%= SpecialHolidayManagementAction.CMD_SORT %>');"><%= params.getName("Acquisition","TimeLimit") %><%= PlatformUtility.getSortMark(HolidayManagementListHolidayLimitComparator.class.getName(), params) %></th>
		</tr>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
		<tr>
			<td class="ListSelectTd">
				<button type="button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= vo.getAryLblEmployeeCode()[i] %>','<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>','<%=vo.getAryLblHolidayCode()[i]%>'), '<%= SpecialHolidayHistoryAction.CMD_SELECT_SHOW %>');"><%= params.getName("History") %></button>
			</td>
			<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
			<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblSection()[i]) %></td>
			<td class="ListSelectTd" id=""><%=HtmlUtility.escapeHTML(vo.getAryLblHolidayCodeName()[i])%></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayRemainder()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayLimit()[i]) %></td>
		</tr>
<%
}
%>
	</table>
</div>
<%
if (vo.getList().isEmpty() ==false) {
%>
	<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<%
}
%>
<div class="Button">
	<button type="button" id="btnSpecialHolidayHistory" class="Name4Button" onclick="submitTransfer(event, null, null, null, '<%= SpecialHolidayHistoryAction.CMD_SHOW %>');"><%= params.getName("New","Insert") %></button>
</div>
<%
if (vo.getList().isEmpty()) {
	return;
}
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
