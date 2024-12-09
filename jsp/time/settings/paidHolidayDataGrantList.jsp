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
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.PaidHolidayDataGrantListAccomplishComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayDataGrantListAttendanceRateComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayDataGrantListGrantComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayDataGrantListGrantDateComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayDataGrantListGrantDaysComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.settings.action.PaidHolidayDataGrantListAction"
import = "jp.mosp.time.settings.vo.PaidHolidayDataGrantListVo"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.utils.TimeNamingUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PaidHolidayDataGrantListVo vo = (PaidHolidayDataGrantListVo)params.getVo();
%>
<div class="List">
	<table class="InputTable" id="paidHolidayDataSearch">
		<tbody>
			<tr>
				<th colspan="6" class="ListTableTh">
					<span class="TitleTh"><%=params.getName("Employee", "Search")%></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd">
					<span class="RequiredLabel">*&nbsp;</span><%=params.getName("ActivateDate")%>
				</td>
				<td class="InputTd" id="tdSearchActivateDate">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>" />
					<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>" />
					<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())%>" />
					<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>
					<button type="button" id="btnActivateDate" class="Name2Button" onclick="submitForm(event, 'tdSearchActivateDate', null, '<%=PaidHolidayDataGrantListAction.CMD_SET_ACTIVATION_DATE%>');"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</td>
				<td class="TitleTd">
					<%=params.getName("Joined", "Day")%>
				</td>
				<td class="InputTd">
					<input type="text" class="Number4TextBox" id="txtSearchEntrance" name="txtSearchEntrance" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEntrance())%>" />
					<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
					<input type="text" class="Number2TextBox" id="txtSearchEntranceMonth" name="txtSearchEntranceMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEntranceMonth())%>" />
					<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
					<input type="text" class="Number2TextBox" id="txtSearchEntranceDay" name="txtSearchEntranceDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEntranceDay())%>" />
					<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>&nbsp;
				</td>
				<td class="TitleTd"><label for="txtSearchEmployeeCode"><%=PfNameUtility.employeeCode(params)%></label></td>
				<td class="InputTd">
					<input type="text" class="Code10TextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode())%>" />
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="txtSearchEmployeeName"><%=params.getName("Employee", "FirstName")%></label></td>
				<td class="InputTd">
					<input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName())%>" />
				</td>
				<td class="TitleTd"><label for="pltSearchPaidHoliday"><%=params.getName("PaidHolidayAbbr", "Set")%></label></td>
				<td id="tdSearchPaidHolidayDate" class="InputTd">
					<select name="pltSearchPaidHoliday" id="pltSearchPaidHoliday" class="Name13PullDown">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchPaidHoliday(), vo.getPltSearchPaidHoliday())%>
					</select>
				</td>
				<td class="TitleTd"><label for="pltSearchGrant"><%=params.getName("Giving", "State")%></label></td>
				<td class="InputTd">
					<select name="pltSearchGrant" id="pltSearchGrant" class="Name8PullDown">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_GRANT, vo.getPltSearchGrant(), true)%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="pltSearchWorkPlace"><%=params.getName("WorkPlace")%></label></td>
				<td class="InputTd">
					<select name="pltSearchWorkPlace" id="pltSearchWorkPlace" class="Name15PullDown">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchWorkPlace(), vo.getPltSearchWorkPlace())%>
					</select>
				</td>
				<td class="TitleTd"><label for="pltSearchEmployment"><%=params.getName("EmploymentContract")%></label></td>
				<td class="InputTd">
					<select name="pltSearchEmployment" id="pltSearchEmployment" class="Name13PullDown">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchEmployment(), vo.getPltSearchEmployment())%>
					</select>
				</td>
				<td colspan="2" class="Blank"></td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="pltSearchSection"><%=params.getName("Section")%></label></td>
				<td class="InputTd">
					<select name="pltSearchSection" id="pltSearchSection" class="SectionNamePullDown">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchSection(), vo.getPltSearchSection())%>
					</select>
				</td>
				<td class="TitleTd"><label for="pltSearchPosition"><%=params.getName("Position")%></label></td>
				<td class="InputTd">
					<select name="pltSearchPosition" id="pltSearchPosition" class="Name13PullDown">
						<%=HtmlUtility.getSelectOption(vo.getAryPltSearchPosition(), vo.getPltSearchPosition())%>
					</select>
				</td>
				<td colspan="2" class="Blank"></td>
			</tr>
		</tbody>
	</table>
	<table class="ButtonTable">
		<tbody>
			<tr>
				<td class="ButtonTd">
					<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'paidHolidayDataSearch', checkExtra, '<%=PaidHolidayDataGrantListAction.CMD_SEARCH%>');"><%=params.getName("Search")%></button>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<%=HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex())%>
<%
if (!vo.getList().isEmpty()) {
%>
<div class="Button">
	<span class="FloatLeftSpan">
		<button type="button" class="Name8Button" onclick="submitRegist(event, 'paidHolidayDataSearch', null, '<%=PaidHolidayDataGrantListAction.CMD_CALC2%>');"><%=params.getName("GoingWork", "Rate", "Bulk", "Calc")%></button>
		<button type="button" class="Name8Button" onclick="submitRegist(event, 'paidHolidayDataSearch', checkCalcAttendanceRate, '<%=PaidHolidayDataGrantListAction.CMD_OTHER_BATCH_UPDATE1%>');"><%=params.getName("PaidVacation", "Bulk", "Giving")%></button>
	</span>
	<span class="TableButtonSpan">
		<button type="button" class="Name8Button" onclick="submitRegist(event, 'paidHolidayDataSearch', null, '<%=PaidHolidayDataGrantListAction.CMD_OTHER_BATCH_UPDATE2%>');"><%=params.getName("Stock", "Holiday", "Giving")%></button>
		<button type="button" class="Name8Button" onclick="submitFile(event, null, null, '<%=PaidHolidayDataGrantListAction.CMD_OUTPUT%>');"><%=TimeNamingUtility.paidHolidayUsageExport(params)%></button>
	</span>
</div>
<%
}
%>
<div class="FixList">
	<table id="tblHeader" class="OverTable">
		<thead>
<%
if (!vo.getList().isEmpty()) {
%>
			<tr>
				<th>
					<span class="TableButtonSpan">
						<button type="button" class="Name8Button" onclick="submitRegist(event, 'paidHolidayDataSearch', checkCalcExtra, '<%=PaidHolidayDataGrantListAction.CMD_CALC1%>');"><%=params.getName("GoingWork", "Rate", "Select", "Calc")%></button>
						<button type="button" class="Name8Button" onclick="submitRegist(event, 'paidHolidayDataSearch', checkGrantExtra, '<%=PaidHolidayDataGrantListAction.CMD_BATCH_UPDATE%>');"><%=params.getName("PaidVacation", "Select", "Giving")%></button>
					</span>
				</th>
			</tr>
<%
}
%>
		</thead>
	</table>
	<table id="list" class="UnderTable">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeCodeComparator.class.getName()%>'), '<%=PaidHolidayDataGrantListAction.CMD_SORT%>');">
					<%=PfNameUtility.employeeCode(params)%><%=PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params)%>
				</th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=EmployeeNameComparator.class.getName()%>'), '<%=PaidHolidayDataGrantListAction.CMD_SORT%>');">
					<%=params.getName("Employee", "FirstName")%><%=PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params)%>
				</th>
				<th class="ListSortTh" id="thAttendanceRate" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=PaidHolidayDataGrantListAttendanceRateComparator.class.getName()%>'), '<%=PaidHolidayDataGrantListAction.CMD_SORT%>');">
					<%=params.getName("GoingWork", "Rate")%><%=PlatformUtility.getSortMark(PaidHolidayDataGrantListAttendanceRateComparator.class.getName(), params)%>
				</th>
				<th class="ListSortTh" id="thAccomplish" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=PaidHolidayDataGrantListAccomplishComparator.class.getName()%>'), '<%=PaidHolidayDataGrantListAction.CMD_SORT%>');">
					<%=params.getName("GoingWork", "Rate", "Norm")%><%=PlatformUtility.getSortMark(PaidHolidayDataGrantListAccomplishComparator.class.getName(), params)%>
				</th>
				<th class="ListSortTh" id="thGrant" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=PaidHolidayDataGrantListGrantComparator.class.getName()%>'), '<%=PaidHolidayDataGrantListAction.CMD_SORT%>');">
					<%=params.getName("Giving", "State")%><%=PlatformUtility.getSortMark(PaidHolidayDataGrantListGrantComparator.class.getName(), params)%>
				</th>
				<th class="ListSortTh" id="thGrantDate" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=PaidHolidayDataGrantListGrantDateComparator.class.getName()%>'), '<%=PaidHolidayDataGrantListAction.CMD_SORT%>');">
					<%=params.getName("Giving", "Date")%><%=PlatformUtility.getSortMark(PaidHolidayDataGrantListGrantDateComparator.class.getName(), params)%>
				</th>
				<th class="ListSortTh" id="thGrantDays" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=PaidHolidayDataGrantListGrantDaysComparator.class.getName()%>'), '<%=PaidHolidayDataGrantListAction.CMD_SORT%>');">
					<%=params.getName("Giving", "Days")%><%=PlatformUtility.getSortMark(PaidHolidayDataGrantListGrantDaysComparator.class.getName(), params)%>
				</th>
				<th class="ListSelectTh" id="thSelect">
<%
if (!vo.getList().isEmpty()) {
%>
					<input type="checkbox" class="CheckBox" onclick="doAllBoxChecked(this);" />
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryPersonalId().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<%
if (MospUtility.isEqual(vo.getAryLblGrantDate(i), PfNameUtility.hyphen(params)) == false) {
%>
					<button type="button" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_INDEX%>', '<%=i%>'), '<%=PaidHolidayDataGrantListAction.CMD_TRANSFER%>');"><%=params.getName("Select")%></button>
<%
}
%>
				</td>
				<td class="ListInputTd"><%=HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode(i))%></td>
				<td class="ListInputTd"><%=HtmlUtility.escapeHTML(vo.getAryLblEmployeeName(i))%></td>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblAttendanceRate(i))%></td>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblAccomplish(i))%></td>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblGrant(i))%></td>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblGrantDate(i))%></td>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblGrantDays(i))%></td>
				<td class="ListSelectTd">
<%
if (MospUtility.isEqual(vo.getAryLblGrantDate(i), PfNameUtility.hyphen(params)) == false) {
%>
					<input type="checkbox" name="ckbSelect" class="CheckBox" value="<%= i %>" />
<%
	}
%>
				</td>
			</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
			<tr>
				<th colspan="9" class="UnderTd">
					<span class="TableButtonSpan">
						<button type="button" class="Name8Button" onclick="submitRegist(event, 'paidHolidayDataSearch', checkCalcExtra, '<%= PaidHolidayDataGrantListAction.CMD_CALC1 %>');"><%= params.getName("GoingWork", "Rate", "Select", "Calc") %></button>
						<button type="button" class="Name8Button" onclick="submitRegist(event, 'paidHolidayDataSearch', checkGrantExtra, '<%= PaidHolidayDataGrantListAction.CMD_BATCH_UPDATE %>');"><%= params.getName("PaidVacation", "Select", "Giving") %></button>
					</span>
				</th>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (!vo.getList().isEmpty()) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
