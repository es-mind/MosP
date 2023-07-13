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
<%@page import="jp.mosp.time.utils.TimeNamingUtility"%>
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
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.ApplicationReferenceApplicationCodeComparator"
import = "jp.mosp.time.comparator.settings.ApplicationReferenceApplicationNameComparator"
import = "jp.mosp.time.comparator.settings.ApplicationReferenceCutoffComparator"
import = "jp.mosp.time.comparator.settings.ApplicationReferencePaidHolidayComparator"
import = "jp.mosp.time.comparator.settings.ApplicationReferenceScheduleComparator"
import = "jp.mosp.time.comparator.settings.ApplicationReferenceTimeSettingComparator"
import = "jp.mosp.time.settings.action.ApplicationReferenceAction"
import = "jp.mosp.time.settings.vo.ApplicationReferenceVo"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApplicationReferenceVo vo = (ApplicationReferenceVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%=params.getName("Search")%></span><a></a></th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("ActivateDate")%></td>
			<td class="InputTd">
				<div id="divSearchActiveDate">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>" />
					<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>" />
					<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())%>" />
					<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>
					<button type="button" class="Name2Button" onclick="submitForm(event, 'divSearchActiveDate', null, '<%=ApplicationReferenceAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</div>
			</td>
			<td class="TitleTd"><label for="txtSearchEmployeeCode"><%=PfNameUtility.employeeCode(params)%></label></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode())%>" />
			</td>
			<td class="TitleTd"><label for="txtSearchEmployeeName"><%=params.getName("FullName")%></label></td>
			<td class="InputTd">
				<input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName())%>" />
			</td>
		</tr>
	</table>
	<table class="InputTable">
		<tr>
			<td class="TitleTd" id="tdMasterTitle1"><%=params.getName("WorkPlace","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchWorkPlaceMaster" name="pltSearchWorkPlace">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchWorkPlaceMaster(), vo.getPltSearchWorkPlace())%>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%=params.getName("EmploymentContract","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchEmploymentMaster" name="pltSearchEmployment">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchEmploymentMaster(), vo.getPltSearchEmployment())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdMasterTitle1"><%=params.getName("Section","Name")%></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltSearchSectionMaster" name="pltSearchSection">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchSectionMaster(), vo.getPltSearchSection())%>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%=params.getName("Position","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchPositionMaster" name="pltSearchPosition">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchPositionMaster(), vo.getPltSearchPosition())%>
				</select>
			</td>
		</tr>
	</table>
	<table class="InputTable">
		<tr>
			<td class="TitleTd"><label for="txtSearchApplicationCode"><%=params.getName("Set","Apply","Code")%></label></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchApplicationCode" name="txtSearchApplicationCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchApplicationCode())%>" />
			</td>
			<td class="TitleTd"><label for="txtSearchApplicationName"><%=params.getName("Set","Apply","Name")%></label></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchApplicationName" name="txtSearchApplicationName" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchApplicationName())%>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("WorkManage","Set","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchTimeSetting" name="pltSearchTimeSetting">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchTimeSetting(), vo.getPltSearchTimeSetting())%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("Calendar","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchSchedule" name="pltSearchSchedule">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchSchedule(), vo.getPltSearchSchedule())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("PaidVacation","Set","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchPaidHoliday" name="pltSearchPaidHoliday">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchPaidHoliday(), vo.getPltSearchPaidHoliday())%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("CutoffDate","Set","Name")%></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchCutoff" name="pltSearchCutoff">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchCutoff(), vo.getPltSearchCutoff())%>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', checkExtra, '<%=ApplicationReferenceAction.CMD_SEARCH%>');" ><%=params.getName("Search")%></button>
			</td>
		</tr>
	</table>
</div>
<%=HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex())%>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeCodeComparator.class.getName()%>'), '<%=ApplicationReferenceAction.CMD_SORT%>')"><%=PfNameUtility.employeeCode(params)%><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName() %>'), '<%= ApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("FullName") %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="tdBorderLeft" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= ApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApplicationCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationReferenceApplicationCodeComparator.class.getName() %>'), '<%= ApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("Code") %><%= PlatformUtility.getSortMark(ApplicationReferenceApplicationCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thApplicationAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationReferenceApplicationNameComparator.class.getName() %>'), '<%= ApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("Set","Apply") %><%= PlatformUtility.getSortMark(ApplicationReferenceApplicationNameComparator.class.getName(), params) %></th>
				<th class="EditSortTh" id="tdBorderLeft" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationReferenceTimeSettingComparator.class.getName() %>'), '<%= ApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("menuTimeSettings") %><%= PlatformUtility.getSortMark(ApplicationReferenceTimeSettingComparator.class.getName(), params) %></th>
				<th class="EditSortTh" id="thCutoffAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationReferenceCutoffComparator.class.getName() %>'), '<%= ApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("CutoffDate") %><%= PlatformUtility.getSortMark(ApplicationReferenceCutoffComparator.class.getName(), params) %></th>
				<th class="EditSortTh" id="thSchadeule" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationReferenceScheduleComparator.class.getName() %>'), '<%= ApplicationReferenceAction.CMD_SORT %>')"><%= params.getName("Calendar") %><%= PlatformUtility.getSortMark(ApplicationReferenceScheduleComparator.class.getName(), params) %></th>
				<th class="EditSortTh" id="thPaidHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationReferencePaidHolidayComparator.class.getName() %>'), '<%= ApplicationReferenceAction.CMD_SORT %>')"><%= TimeNamingUtility.paidHolidaySettingAbbr(params) %><%= PlatformUtility.getSortMark(ApplicationReferencePaidHolidayComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName(i)) %></td>
				<td class="ListInputTd" id="tdBorderLeft"><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblApplicationCode(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblApplication(i)) %></td>
				<td class="ListInputTd" id="tdBorderLeft"><%= HtmlUtility.escapeHTML(vo.getAryLblTimeSetting(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblCutoff(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblSchadeule(i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHoliday(i)) %></td>
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
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
