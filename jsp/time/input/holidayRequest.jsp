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
import = "jp.mosp.framework.utils.CalendarHtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.RequestApproverNameComparator"
import = "jp.mosp.time.comparator.settings.RequestStateComparator"
import = "jp.mosp.time.comparator.settings.HolidayRequestHolidayType1Comparator"
import = "jp.mosp.time.comparator.settings.HolidayRequestHolidayType3Comparator"
import = "jp.mosp.time.comparator.settings.HolidayRequestRequestDateComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.HolidayRequestAction"
import = "jp.mosp.time.input.vo.HolidayRequestVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HolidayRequestVo vo = (HolidayRequestVo)params.getVo();
boolean isChanging = vo.isModeActivateDateFixed() == false;
boolean isSearchChanging = PlatformUtility.isActivateDateChanging(vo.getJsSearchModeActivateDate());
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%=TimeConst.PATH_TIME_COMMON_INFO_JSP%>" flush="false" />
		</tr>
	</table>
</div>
<div class="List">
	<table class="InputTable" id="holidayRequest_tblEdit">
		<tr>
			<th class="EditTableTh" colspan="4">
				<jsp:include page="<%=TimeConst.PATH_TIME_APPLY_INFO_JSP%>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Holiday","Year","Month","Day")%></td>
			<td class="InputTd" colspan="3">
				<%=CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditStart", "", "", false, false, isChanging)%>
				<select class="Number4PullDown" id="pltEditStartYear" name="pltEditStartYear">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditStartYear(), vo.getPltEditStartYear(), true)%>
				</select>
				<%=params.getName("Year")%>&nbsp;
				<select class="Number2PullDown" id="pltEditStartMonth" name="pltEditStartMonth">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditStartMonth(), vo.getPltEditStartMonth())%>
				</select>
				<%=params.getName("Month")%>&nbsp;
				<select class="Number2PullDown" id="pltEditStartDay" name="pltEditStartDay">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditStartDay(), vo.getPltEditStartDay())%>
				</select>
				<%=params.getName("Day","Wave")%>
				<%=CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "pltEditEnd", "", "", false, false, isChanging)%>
				<select class="Number4PullDown" id="pltEditEndYear" name="pltEditEndYear">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndYear(), vo.getPltEditEndYear(), true)%>
				</select>
				<%=params.getName("Year")%>&nbsp;
				<select class="Number2PullDown" id="pltEditEndMonth" name="pltEditEndMonth">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndMonth(), vo.getPltEditEndMonth())%>
				</select>
				<%=params.getName("Month")%>&nbsp;
				<select class="Number2PullDown" id="pltEditEndDay" name="pltEditEndDay">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndDay(), vo.getPltEditEndDay())%>
				</select>
				<%=params.getName("Day")%>&nbsp;
				<button type="Button" class="Name2Button" id="btnHolidayDate" onclick="submitForm(event, null, checkDateExtra, '<%=HolidayRequestAction.CMD_SET_ACTIVATION_DATE%>');"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Holiday","Classification")%></td>
			<td class="InputTd">
				<select class="PullDown" id="pltEditHolidayType" name="pltEditHolidayType1">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditHolidayType1(), vo.getPltEditHolidayType1())%>
				</select>
				<select class="Name4PullDown" id="pltEditStatusWithPay" name="pltEditStatusWithPay">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditHolidayType2WithPay(), vo.getPltEditStatusWithPay())%>
				</select>
				<select class="Name3PullDown" id="pltEditStatusSpecial" name="pltEditStatusSpecial">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditHolidayType2Special(), vo.getPltEditStatusSpecial())%>
				</select>
				<select class="Name3PullDown" id="pltEditSpecialOther" name="pltEditSpecialOther">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditHolidayType2Other(), vo.getPltEditSpecialOther())%>
				</select>
				<select class="Name3PullDown" id="pltEditSpecialAbsence" name="pltEditSpecialAbsence">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditHolidayType2Absence(), vo.getPltEditSpecialAbsence())%>
				</select>
				<select class="Name3PullDown" id="pltEditHolidayRange1" name="pltEditHolidayRangePaidHoliday">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditHolidayRangePaidHoliday(), vo.getPltEditHolidayRangePaidHoliday())%>
				</select>
				<select class="Name3PullDown" id="pltEditHolidayRange2" name="pltEditHolidayRange">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditHolidayRange(), vo.getPltEditHolidayRange())%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("Time","Rest","Application","Time")%></td>
			<td class="InputTd">
				<select class="Number2PullDown" id="pltEditStartHour" name="pltEditStartHour">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditStartHour(), vo.getPltEditStartHour())%>
				</select>&nbsp;:
				<select class="Number2PullDown" id="pltEditStartMinute" name="pltEditStartMinute">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditStartMinute(), vo.getPltEditStartMinute())%>
				</select>&nbsp;
				<%=params.getName("Kara")%>&nbsp;
				<select class="Number2PullDown" id="pltEditEndTime" name="pltEditEndTime">
					<%=HtmlUtility.getSelectOption(vo.getAryPltEditEndTime(), vo.getPltEditEndTime())%>
				</select>&nbsp;<%=params.getName("Time")%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtEditRequestReason"><%=params.getName("Holiday","Reason")%></label></td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Name21TextBox" id="txtEditRequestReason" name="txtEditRequestReason" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditRequestReason())%>" />
			</td>
		</tr>
	</table>
	<jsp:include page="<%=TimeConst.PATH_TIME_APPLY_BUTTON_JSP%>" flush="false" />
<%
// 承認個人ID利用
if(!params.isTargetApprovalUnit()) {
%>
	<jsp:include page="<%=TimeConst.PATH_TIME_APPROVER_PULLDOWN_JSP%>" flush="false" />
<%
}
%>
</div>
<div class="List">
	<table class="LeftTable" id="holidayRequest_tblPaidHoliday1">
		<tr>
			<th class="ListSelectTh" id="thFiscalYear"></th>
			<th class="ListSelectTh" id="thGrantDate"><%=params.getName("Giving", "Day")%></th>
			<th class="ListSelectTh" id="thExpirationDate"><%=params.getName("TimeLimit", "Day")%></th>
			<th class="ListSelectTh" id="thGrantDays"><%=params.getName("Remainder", "Days", "Slash", "Giving", "Days")%></th>
		</tr>
<%
for (int i = 0; i < vo.getAryLblPaidLeaveFiscalYear().length; i++) {
%>
		<tr>
			<th class="ListSelectTh"><%=HtmlUtility.escapeHTML(vo.getAryLblPaidLeaveFiscalYear()[i])%></th>
			<td class="SelectTd"><span <%=vo.getAryLblStyle()[i]%>><%=HtmlUtility.escapeHTML(vo.getAryLblPaidLeaveGrantDate()[i])%></span></td>
			<td class="SelectTd"><span <%=vo.getAryLblStyle()[i]%>><%=HtmlUtility.escapeHTML(vo.getAryLblPaidLeaveExpirationDate()[i])%></span></td>
			<td class="SelectTd">
				<span <%=vo.getAryLblStyle()[i]%>>
				<%=HtmlUtility.escapeHTML(vo.getAryLblPaidLeaveRemainDays()[i])%>
				<%=params.getName("Slash")%>
				<%=HtmlUtility.escapeHTML(vo.getAryLblPaidLeaveGrantDays()[i])%>
				</span>
			</td>
		</tr>
<%
}
%>
	</table>
	<table class="RightTable" id="holidayRequest_tblPaidHoliday2">
		<tr>
			<th class="ListSelectTh" id="thStockDays"><%=params.getName("Stock", "Remainder", "Days")%></th>
<%
if (vo.isPaidLeaveByHour()) {
%>
			<td class="SelectTd" id="tdLabelStockDays"><%=HtmlUtility.escapeHTML(vo.getLblPaidHolidayStock())%><%=params.getName("Day")%></td>
			<th class="ListSelectTh" id="thPaidLeaveByHour"><%=params.getName("Time", "Unit", "Limit")%></th>
			<td class="SelectTd"><%=HtmlUtility.escapeHTML(vo.getLblHolidayTimeUnitLimit())%></td>
<%
} else {
%>
			<td class="SelectTd" colspan="3" id="tdLabelStockDaysNoHour"><%=HtmlUtility.escapeHTML(vo.getLblPaidHolidayStock())%><%=params.getName("Day")%></td>
<%
}
%>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
		</tr>
		<tr>
			<th class="ListSelectTh" id="thPaidLeaveAutomatic">
				<%=params.getName("PaidHolidayAbbr", "NextTime", "Giving", "FrontParentheses", "Automatic", "BackParentheses")%>
			</th>
			<td class="SelectTd" colspan="3">
				<%=HtmlUtility.escapeHTML(vo.getLblNextGivingAmount())%>
				<%=params.getName("FrontParentheses", "Giving", "Day", "Colon")%>
				<%=HtmlUtility.escapeHTML(vo.getLblNextGivingDate())%>
				<%=params.getName("BackParentheses")%>
			</td>
		</tr>
		<tr>
			<th class="ListSelectTh" id="thPaidLeaveManual">
				<%=params.getName("PaidHolidayAbbr", "NextTime", "Giving", "FrontParentheses", "ManualOperation", "BackParentheses")%>
			</th>
			<td class="SelectTd" colspan="3">
				<%=HtmlUtility.escapeHTML(vo.getLblNextManualGivingAmount())%>
				<%=params.getName("FrontParentheses", "Giving", "Day", "Colon")%>
				<%=HtmlUtility.escapeHTML(vo.getLblNextManualGivingDate())%>
				<%=params.getName("BackParentheses")%>
			</td>
		</tr>
	</table>
</div>
<div class="List" id="divRemainder">
<%
if (vo.getAryLblGivingDate().length > 0) {
%>
	<table class="LeftListTable" id="holidayRequest_tblSpecialHoliday">
		<tr>
			<td class="ListSelectTh"><%=params.getName("GrantDate")%>
			<td class="ListSelectTh"><%=params.getName("Holiday","Classification")%></td>
			<td class="ListSelectTh" id="tdVacationName"><%=params.getName("Holiday","Name")%></td>
			<td class="ListSelectTh"><%=params.getName("Application","Possible","Days")%></td>
			<td class="ListSelectTh"><%=params.getName("Acquisition","TimeLimit")%></td>
		</tr>
<%
for (int i = 0; i < vo.getAryLblGivingDate().length; i++) {
%>
		<tr>
			<td class="SelectTd" id="lblGivingDate"><%=HtmlUtility.escapeHTML(vo.getAryLblGivingDate()[i])%></td>
			<td class="SelectTd" id="lblSpecialHolidayType"><%=HtmlUtility.escapeHTML(vo.getAryLblSpecialHolidayType()[i])%></td>
			<td class="SelectTd" id="lblSpecialHolidayName"><%=HtmlUtility.escapeHTML(vo.getAryLblSpecialHolidayName()[i])%></td>
			<td class="SelectTd" id="lblRemainder"><%=HtmlUtility.escapeHTML(vo.getAryLblRemainder()[i])%></td>
			<td class="SelectTd" id="lblLimit"><%=HtmlUtility.escapeHTML(vo.getAryLblLimit()[i])%></td>
		</tr>
<%
}
%>
	</table>
<%
}
%>
</div>
<div class="List">
	<table class="InputTable SearchInputTable" id="holidayRequest_tblSearch">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Search")%></span>
			</th>
			<td rowspan="2" class="ButtonTd">
				<button type="button" class="Name2Button" id="btnSearch" onclick="submitForm(event, 'divSearch', null, '<%=HolidayRequestAction.CMD_SEARCH%>')"><%=params.getName("Search")%></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Holiday","Classification")%></td>
			<td class="InputTd">
				<select class="PullDown" id="pltSearchHolidayType" name="pltSearchHolidayType">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_HOLIDAY_TYPE, vo.getPltSearchHolidayType(), true)%>
				</select>
				<select class="Name3PullDown" id="pltSearchStatus" name="pltSearchSpecialHoliday">
				</select>
				<select class="Name3PullDown" id="pltSearchStatusWithPay" name="pltSearchStatusWithPay">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY, vo.getPltSearchStatusWithPay(), true)%>
				</select>
				<select class="Name3PullDown" id="pltSearchStatusSpecial" name="pltSearchStatusSpecial">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchHolidayType2Special(), vo.getPltSearchStatusSpecial())%>
				</select>
				<select class="Name3PullDown" id="pltSearchSpecialOther" name="pltSearchSpecialOther">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchHolidayType2Other(), vo.getPltSearchSpecialOther())%>
				</select>
				<select class="Name3PullDown" id="pltSearchSpecialAbsence" name="pltSearchSpecialAbsence">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchHolidayType2Absence(), vo.getPltSearchSpecialAbsence())%>
				</select>
				<select class="Name3PullDown" id="pltSearchHolidayRange" name="pltSearchHolidayLength">
				</select>
				<select class="Name3PullDown" id="pltSearchHolidayRange1" name="pltSearchHolidayRange1">
					<%=HtmlUtility.getSelectOption(vo.getAryPltSearchHolidayRangePaidHoliday(), vo.getPltSearchHolidayRange1())%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("State")%></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltSearchState" name="pltSearchState">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_APPROVAL_STATE, vo.getPltSearchState(), true)%>
				</select>
			</td>
			<td class="TitleTd"><%=PfNameUtility.displayTerm(params)%></td>
			<td class="InputTd">
			<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_MONTH, "pltSearch", "", "", false, false, isSearchChanging) %>
				<select class="Number4PullDown" id="pltSearchYear" name="pltSearchYear">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchYear(), vo.getPltSearchYear(), true) %>
				</select>
				<%= params.getName("Year") %>
				<select class="Number2PullDown" id="pltSearchMonth" name="pltSearchMonth">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchMonth(), vo.getPltSearchMonth()) %>
				</select>
				<%= params.getName("Month") %>&nbsp;
				<button type="Button" class="Name2Button" id="btnRequestDate" onclick="submitTransfer(event, null, null, new Array('null', 'null', 'null', 'null'), '<%= HolidayRequestAction.CMD_SET_VIEW_PERIOD %>');"><%= vo.getJsSearchModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="RequestListInfo">
	<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
</div>
<div class="FixList" id="divList">
	<table class="LeftListTable LeftRequestListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thHolidayDate"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayRequestRequestDateComparator.class.getName() %>'), '<%= HolidayRequestAction.CMD_SORT %>')"><%= params.getName("Day") %><%= PlatformUtility.getSortMark(HolidayRequestRequestDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayType"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayRequestHolidayType1Comparator.class.getName() %>'), '<%= HolidayRequestAction.CMD_SORT %>')"><%= params.getName("Holiday","Type") %><%= PlatformUtility.getSortMark(HolidayRequestHolidayType1Comparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayRange"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayRequestHolidayType3Comparator.class.getName() %>'), '<%= HolidayRequestAction.CMD_SORT %>')"><%= params.getName("Range") %><%= PlatformUtility.getSortMark(HolidayRequestHolidayType3Comparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thHolidayReason"><%= params.getName("Holiday","Reason") %></th>
				<th class="ListSortTh" id="thHolidayState"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestStateComparator.class.getName() %>'), '<%= HolidayRequestAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(RequestStateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayApprover" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= RequestApproverNameComparator.class.getName() %>'), '<%= HolidayRequestAction.CMD_SORT %>')"><%= params.getName("Approver") %><%= PlatformUtility.getSortMark(RequestApproverNameComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (!vo.getList().isEmpty()) {
%>
					<input type="checkbox" class="CheckBox" onclick="doAllBoxChecked(this);">
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
<% if(!vo.getAryLblOnOff()[i].isEmpty()) { %>
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %>', '<%= TimeConst.PRM_TRANSFERRED_HOLIDAY_TYPE1 %>', '<%= HtmlUtility.escapeHTML(vo.getAryHolidayType1()[i]) %>', '<%= TimeConst.PRM_TRANSFERRED_HOLIDAY_TYPE2 %>', '<%= HtmlUtility.escapeHTML(vo.getAryHolidayType2()[i]) %>', '<%= TimeConst.PRM_TRANSFERRED_HOLIDAY_RANGE %>', '<%= HtmlUtility.escapeHTML(vo.getAryHolidayType3()[i]) %>', '<%= TimeConst.PRM_TRANSFERRED_START_TIME %>', '<%= vo.getAryStartTime()[i] %>'),'<%= HolidayRequestAction.CMD_EDIT_MODE %>')"><%= params.getName("Select") %></button>
<% } %>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayType1()[i]) %>&nbsp;<%= HtmlUtility.escapeHTML(vo.getAryLblHolidayType2()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayType3()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblRequestReason()[i]) %></td>
				<td class="ListSelectTd">
					<a class="Link" id="linkApprovalHistory" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>'), '<%= HolidayRequestAction.CMD_TRANSFER %>');"><span <%= vo.getAryStateStyle()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></span></a>
				</td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblApprover()[i]) %></td>
				<td class="ListSelectTd">
<% if(PlatformConst.CODE_STATUS_DRAFT.equals(vo.getAryWorkflowStatus()[i]) || PlatformConst.CODE_STATUS_APPLY.equals(vo.getAryWorkflowStatus()[i])) { %>
					<input type="checkbox" class="CheckBox" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbHolidayRequestListId()[i]) %>" />
<% } %>
				</td>
			</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
			<tr>
				<td class="BottomTd" colspan="8">
					<span class="TableButtonSpan">
						<button type="button" class="Name5Button" id="btnUpdate" onclick="submitRegist(event, null, checkBatchUpdateExtra, '<%= HolidayRequestAction.CMD_BATCH_UPDATE %>');"><%= params.getName("Application") %></button>
						<button type="button" class="Name5Button" id="btnWithdrawn" onclick="submitRegist(event, null, checkBatchWithdrawnExtra, '<%= HolidayRequestAction.CMD_BATCH_WITHDRAWN %>');"><%= params.getName("Ram", "Approval", "TakeDown") %></button>
					</span>
				</td>
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
<div class="RequestListPage">
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
</div>
<%
}
%>
