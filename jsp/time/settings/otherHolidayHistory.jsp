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
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.HolidayHistoryListHolidayCodeComparator"
import = "jp.mosp.time.comparator.settings.HolidayHistoryListHolidayGivingComparator"
import = "jp.mosp.time.comparator.settings.HolidayHistoryListHolidayLimitComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.settings.action.OtherHolidayHistoryAction"
import = "jp.mosp.time.settings.action.OtherHolidayManagementAction"
import = "jp.mosp.time.settings.vo.OtherHolidayHistoryVo"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
OtherHolidayHistoryVo vo = (OtherHolidayHistoryVo)params.getVo();
%>
<div class="Button">
	<button type="button" id="btn" class="Name7Button" onclick="submitTransfer(event, null, null, null, '<%=OtherHolidayManagementAction.CMD_SHOW%>');"><%=params.getName("Others")%><%=params.getName("Holiday")%><%=params.getName("Confirmation")%></button>
</div>
<div class="List">
	<table class="OverInputTable">
		<tr>
			<th class="EditTableTh" colspan="6">
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
				<span class="TitleTh"><%=params.getName("Edit")%><%=params.getName("FrontWithCornerParentheses")%><%=params.getName("Edit")%><%=params.getName("BackWithCornerParentheses")%></span>
				<a onclick="submitTransfer(event, 'divEdit', null, null, '<%=OtherHolidayHistoryAction.CMD_INSERT_MODE%>')"><%=params.getName("FrontWithCornerParentheses")%><%=params.getName("New")%><%=params.getName("Insert")%><%=params.getName("BackWithCornerParentheses")%></a>
<%
} else {
%>
				<span class="TitleTh"><%=params.getName("Edit")%><%=params.getName("FrontWithCornerParentheses")%><%=params.getName("New")%><%=params.getName("Insert")%><%=params.getName("BackWithCornerParentheses")%></span>
<%
}
%>
			</th>
		</tr>
	</table>
	<table class="UnderInputTable" >
		<tr id="trInsertCheck">
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("GrantDate")%></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateYear())%>"/>
				<label for="txtEditActivateYear"><%=params.getName("Year")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth())%>"/>
				<label for="txtEditActivateMonth"><%=params.getName("Month")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateDay())%>"/>
				<label for="txtEditActivateDay"><%=params.getName("Day")%></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditEmployeeCode"><%=PfNameUtility.employeeCode(params)%></label></td>
			<td class="InputTd">
				<input type="text" class="Code10RequiredTextBox" id="txtEditEmployeeCode" name="txtEditEmployeeCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditEmployeeCode())%>"/>&nbsp;
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'trInsertCheck', null, '<%=OtherHolidayHistoryAction.CMD_SET_EMPLOYEE_DECISION%>');"><%=vo.getJsEditActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
			</td>
			<td class="TitleTd"><%=params.getName("Employee")%><%=params.getName("FirstName")%></td>
			<td class="InputTd" id="lblEditEmployeeName"><%=HtmlUtility.escapeHTML(vo.getLblEmployeeName())%></td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Holiday")%><%=params.getName("Classification")%></td>
			<td class="InputTd">
				<select class="Name12PullDown" id="pltEditHolidayType" name="pltEditHolidayType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditHolidayType(), vo.getPltEditHolidayType())%>
				</select>
			</td>
			<td class="TitleTd">
				<span class="RequiredLabel">*&nbsp;</span><label for="txtEditHolidayGiving"><%=params.getName("Giving")%><%=params.getName("Days")%></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Numeric4RequiredTextBox" id="txtEditHolidayGiving" name="txtEditHolidayGiving"  value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayGiving())%>"/>&nbsp;<%=params.getName("Day")%>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("Acquisition")%><%=params.getName("TimeLimit")%></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtEditHolidayLimitMonth" name="txtEditHolidayLimitMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayLimitMonth())%>"/>&nbsp;<%=params.getName("Months")%>
				<input type="text" class="Number2RequiredTextBox" id="txtEditHolidayLimitDay" name="txtEditHolidayLimitDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayLimitDay())%>"/>&nbsp;<%=params.getName("Day")%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Effectiveness")%><%=params.getName("Slash")%><%=params.getName("Inactivate")%></td>
			<td class="InputTd" id="tdInactivate">
				<select id="pltEditInactivate" name="pltEditInactivate">
					<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false)%>
				</select>
			</td>
			<td class="Blank" colspan="4"></td>
		</tr>
	</table>
	<table class="ButtonTable" id="tblRegistButton">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, '', checkHolidayGiving, '<%=OtherHolidayHistoryAction.CMD_REGISTER%>');"><%=params.getName("Insert")%></button>
			</td>
		</tr>
	</table>
</div>
<div class="List">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Giving")%><%=params.getName("Information")%><%=params.getName("Search")%></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("GrantDate")%></td>
			<td class="InputTd" id="tdOtherSearchHolidayDate">
			<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>"/>
			<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
			<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>"/>
			<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
			<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())%>"/>
			<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>&nbsp;
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'tdOtherSearchHolidayDate', null, '<%=OtherHolidayHistoryAction.CMD_SET_ACTIVATION_DATE%>');"><%=vo.getJsSearchActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				
			</td>
			<td class="TitleTd"><%=PfNameUtility.employeeCode(params)%></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode()) %>"/>
			</td>
			<td class="TitleTd"><%= params.getName("Employee") %><%= params.getName("FirstName") %></td>
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
			<td class="TitleTd"><%= params.getName("Effectiveness") %><%= params.getName("Slash") %><%= params.getName("Inactivate") %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitTransfer(event, null, null, null, '<%= OtherHolidayHistoryAction.CMD_SEARCH %>');"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List" id="divList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=ActivateDateComparator.class.getName()%>'), '<%=OtherHolidayHistoryAction.CMD_SORT%>');"><%=params.getName("GrantDate")%><%=PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeNameComparator.class.getName()%>'), '<%=OtherHolidayHistoryAction.CMD_SORT%>');"><%= params.getName("Employee") %><%= params.getName("FirstName") %><%=PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=SectionCodeComparator.class.getName()%>'), '<%=OtherHolidayHistoryAction.CMD_SORT%>');"><%= params.getName("Section") %><%=PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thHolidayType" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=HolidayHistoryListHolidayCodeComparator.class.getName()%>'), '<%=OtherHolidayHistoryAction.CMD_SORT%>');"><%= params.getName("Classification") %><%=PlatformUtility.getSortMark(HolidayHistoryListHolidayCodeComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thHolidayGiving" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=HolidayHistoryListHolidayGivingComparator.class.getName()%>'), '<%=OtherHolidayHistoryAction.CMD_SORT%>');"><%= params.getName("Giving") %><%= params.getName("Days") %><%=PlatformUtility.getSortMark(HolidayHistoryListHolidayGivingComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thHolidayLimit" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=HolidayHistoryListHolidayLimitComparator.class.getName()%>'), '<%=OtherHolidayHistoryAction.CMD_SORT%>');"><%= params.getName("Acquisition") %><%= params.getName("TimeLimit") %><%=PlatformUtility.getSortMark(HolidayHistoryListHolidayLimitComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= OtherHolidayHistoryAction.CMD_SORT %>');"><%= params.getName("EffectivenessExistence") %><%= params.getName("Slash") %><%= params.getName("InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
	for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" onclick="submitTransfer(event, null, null,new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %>','<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>','<%= vo.getAryLblHolidayCode()[i] %>'), '<%= OtherHolidayHistoryAction.CMD_EDIT_MODE %>');"><%= params.getName("Select") %></button>
				</td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblSection()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayType()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayGiving()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayLimit()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i]) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getList().size() == 0) {
	return;
}
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular") %><%= params.getName("TopOfPage") %></a>
</div>
