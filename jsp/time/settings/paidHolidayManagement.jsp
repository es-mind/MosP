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
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.SectionCodeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.PaidHolidayManagementListDateComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayManagementListFormerDateComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayManagementListFormerTimeComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayManagementListStockDateComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayManagementListTimeComparator"
import = "jp.mosp.time.settings.action.PaidHolidayHistoryAction"
import = "jp.mosp.time.settings.action.PaidHolidayManagementAction"
import = "jp.mosp.time.settings.action.PaidHolidayReferenceAction"
import = "jp.mosp.time.settings.vo.PaidHolidayManagementVo"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PaidHolidayManagementVo vo = (PaidHolidayManagementVo)params.getVo();
%>
<div class="List">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Employee","Search")%></span>
			</th>
		</tr>
		<tr>
		<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("ActivateDate")%></td>
		<td class="InputTd" id="tdSearchPaidHolidayDate">
			<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>"/>
			<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
			<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>"/>
			<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
			<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())%>"/>
			<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>&nbsp;
			<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'tdSearchPaidHolidayDate', null, '<%=PaidHolidayManagementAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
		</td>
		<td class="TitleTd"><%=PfNameUtility.employeeCode(params)%></td>
		<td class="InputTd">
			<input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode())%>"/>
		</td>
		<td class="TitleTd"><%=params.getName("Employee","FirstName")%></td>
		<td class="InputTd">
			<input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName())%>"/>
		</td>
	</tr>
	<tr>
		<td class="TitleTd"><%=params.getName("WorkPlace")%></td>
		<td class="InputTd">
			<select class="Name15PullDown" id="pltSearchWorkPlace" name="pltSearchWorkPlace">
				<%=HtmlUtility.getSelectOption(vo.getAryPltSearchWorkPlace(), vo.getPltSearchWorkPlace())%>
			</select>
		</td>
		<td class="TitleTd"><%=params.getName("EmploymentContract")%></td>
		<td class="InputTd">
			<select class="Name15PullDown" id="pltSearchEmployment" name="pltSearchEmployment">
				<%=HtmlUtility.getSelectOption(vo.getAryPltSearchEmployment(), vo.getPltSearchEmployment())%>
			</select>
		</td>
		<td class="Blank" colspan="2" />
	</tr>
	<tr>
		<td class="TitleTd"><%=params.getName("Section")%></td>
		<td class="InputTd">
			<select class="SectionNamePullDown" id="pltSearchSection" name="pltSearchSection">
				<%=HtmlUtility.getSelectOption(vo.getAryPltSearchSection(), vo.getPltSearchSection())%>
			</select>
		</td>
		<td class="TitleTd"><%=params.getName("Position")%></td>
		<td class="InputTd">
			<select class="Name15PullDown" id="pltSearchPosition" name="pltSearchPosition">
				<%=HtmlUtility.getSelectOption(vo.getAryPltSearchPosition(), vo.getPltSearchPosition())%>
			</select>
		</td>
		<td class="Blank" colspan="2"></td>
	</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, null, checkExtra, '<%=PaidHolidayManagementAction.CMD_SEARCH%>');"><%=params.getName("Search")%></button>
			</td>
		</tr>
	</table>
</div>
<%=HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex())%>
<%
if(vo.getAryLblActivateDate().length > 0){
%>
<div>
	<table class = "TableButtonSpan">
		<tr>
			<td>
				<button type="button" id="btnExport" class="Name6Button" onclick="submitFile(event, null, null, '<%=PaidHolidayManagementAction.CMD_OUTPUT%>');"><%=params.getName("Search","Result","Output")%></button>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSortTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeName"onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeNameComparator.class.getName()%>'), '<%=PaidHolidayManagementAction.CMD_SORT%>')"><%=params.getName("Employee","FirstName")%><%=PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thEmployeeCode"onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeCodeComparator.class.getName()%>'), '<%=PaidHolidayManagementAction.CMD_SORT%>')"><%=PfNameUtility.employeeCode(params)%><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= SectionCodeComparator.class.getName() %>'), '<%= PaidHolidayManagementAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(SectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLblFormerDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayManagementListFormerDateComparator.class.getName() %>'), '<%= PaidHolidayManagementAction.CMD_SORT %>')"><%= params.getName("PreviousYear","Days") %><%= PlatformUtility.getSortMark(PaidHolidayManagementListFormerDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLblFormerTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayManagementListFormerTimeComparator.class.getName() %>'), '<%= PaidHolidayManagementAction.CMD_SORT %>')"><%= params.getName("PreviousYear","Time") %><%= PlatformUtility.getSortMark(PaidHolidayManagementListFormerTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLblDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayManagementListDateComparator.class.getName() %>'), '<%= PaidHolidayManagementAction.CMD_SORT %>')"><%= params.getName("ThisYear","Days") %><%= PlatformUtility.getSortMark(PaidHolidayManagementListDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLblTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayManagementListTimeComparator.class.getName() %>'), '<%= PaidHolidayManagementAction.CMD_SORT %>')"><%= params.getName("ThisYear","Time") %><%= PlatformUtility.getSortMark(PaidHolidayManagementListTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thLblStockDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayManagementListStockDateComparator.class.getName() %>'), '<%= PaidHolidayManagementAction.CMD_SORT %>')"><%= params.getName("Stock") %><%= PlatformUtility.getSortMark(PaidHolidayManagementListStockDateComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" id="btnPaidHolidayGivingCard" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>', '<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= PaidHolidayReferenceAction.class.getName() %>'), '<%= PaidHolidayManagementAction.CMD_TRANSFER %>');"><%= params.getName("List") %></button>&nbsp;
					<button type="button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= PaidHolidayHistoryAction.class.getName() %>'), '<%= PaidHolidayManagementAction.CMD_TRANSFER %>');"><%= params.getName("Giving") %></button>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblSection()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblFormerDate()[i]) %><%= params.getName("Day") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblFormerTime()[i]) %><%= params.getName("Time") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %><%= params.getName("Day") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblTime()[i]) %><%= params.getName("Time") %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblStockDate()[i]) %><%= params.getName("Day") %></td>
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
<%
}
%>
<div class="Button">
	<button type="button" id="btnPaidHolidayHistory" class="Name4Button" onclick="submitTransfer(event, null, null, null, '<%= PaidHolidayHistoryAction.CMD_SHOW %>');"><%= params.getName("New","Insert") %></button>
</div>
<%
if (vo.getAryLblActivateDate().length > 0) {
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
