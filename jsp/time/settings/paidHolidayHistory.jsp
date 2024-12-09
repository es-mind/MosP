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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.PaidHolidayHistoryListDateComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayHistoryListEmployeeCodeComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayHistoryListEmployeeNameComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayHistoryListFormerDateComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayHistoryListFormerTimeComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayHistoryListSectionCodeComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayHistoryListStockDateComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayHistoryListTimeComparator"
import = "jp.mosp.time.settings.action.PaidHolidayHistoryAction"
import = "jp.mosp.time.settings.action.PaidHolidayManagementAction"
import = "jp.mosp.time.settings.action.PaidHolidayReferenceAction"
import = "jp.mosp.time.settings.vo.PaidHolidayHistoryVo"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PaidHolidayHistoryVo vo = (PaidHolidayHistoryVo)params.getVo();
%>
<div class="Button">
	<button type="button" id="btnPaidHolidayManagement" class="Name6Button" onclick="submitTransfer(event, null, null, null, '<%=PaidHolidayManagementAction.CMD_SHOW%>');"><%=params.getName("PaidVacation","Confirmation")%></button>
</div>
<div class="List">
	<table class="OverInputTable">
		<tr>
			<th class="EditTableTh" colspan="4">
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
				<span class="TitleTh"><%=params.getName("PaidVacation","Giving","FrontWithCornerParentheses","Edit","BackWithCornerParentheses")%></span>
				<a onclick="submitTransfer(event, 'divEdit', null, null, '<%=PaidHolidayHistoryAction.CMD_INSERT_MODE%>')"><%=params.getName("FrontWithCornerParentheses","New","Insert","BackWithCornerParentheses")%></a>
				<div class="TableLabelSpan">
<%
if (vo.getLblBackActivateDate() != null && vo.getLblBackActivateDate().isEmpty() == false) {
%>
					<a class="ActivateDateRollLink"
						onclick="submitTransfer(event, 'divEdit', null, new Array('<%=PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE%>', '<%=HtmlUtility.escapeHTML(vo.getLblBackActivateDate())%>', '<%=PlatformConst.PRM_TRANSFERRED_CODE%>', '<%=HtmlUtility.escapeHTML(vo.getPersonalId())%>'), '<%=PaidHolidayHistoryAction.CMD_EDIT_TARGET%>');">
						&lt;&lt;&nbsp;
					</a>
<%
}
%>
					<span class=""><%=params.getName("GrantDate")%></span>
<%
if (vo.getLblNextActivateDate() == null || vo.getLblNextActivateDate().isEmpty()) {
%>
					&nbsp;<%=params.getName("Latest")%>
<%
} else {
%>
					<a class="ActivateDateRollLink"
						onclick="submitTransfer(event, 'divEdit', null, new Array('<%=PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE%>', '<%=HtmlUtility.escapeHTML(vo.getLblNextActivateDate())%>', '<%=PlatformConst.PRM_TRANSFERRED_CODE%>', '<%=HtmlUtility.escapeHTML(vo.getPersonalId())%>'), '<%=PaidHolidayHistoryAction.CMD_EDIT_TARGET%>');">
						&nbsp;&gt;&gt;
					</a>
<%
}
%>
					&nbsp;<%=params.getName("History")%>&nbsp;<%=params.getName("All") + vo.getCountHistory() + params.getName("Count")%>
				</div>
<%
} else {
%>
				<span class="TitleTh"><%=params.getName("PaidVacation","Giving","FrontWithCornerParentheses","New","Insert","BackWithCornerParentheses")%></span>
<%
}
%>
			</th>
		</tr>
	</table>
	<table class="UnderInputTable" id="tblOver">
		<tr id="trInsertCheck">
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("GrantDate")%></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateYear())%>" />
				<label for="txtEditActivateYear"><%=params.getName("Year")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth())%>" />
				<label for="txtEditActivateMonth"><%=params.getName("Month")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateDay())%>" />
				<label for="txtEditActivateDay"><%=params.getName("Day")%></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditEmployeeCode"><%=PfNameUtility.employeeCode(params)%></label></td>
			<td class="InputTd"  >
				<input type="text" class="Code10RequiredTextBox" id="txtEditEmployeeCode" name="txtEditEmployeeCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditEmployeeCode())%>"/>&nbsp;
				<button type="button" class="Name2Button" id="btnEmployeeCode" name="btnEmployeeCode" onclick="submitForm(event, 'trInsertCheck', null, '<%=PaidHolidayHistoryAction.CMD_SET_EMPLOYEE_CODE%>');"><%=vo.getJsModeEmployeeCode().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("Employee","FirstName")%></td>
			<td class="InputTd" id="tdEmployeeName"><%=HtmlUtility.escapeHTML(vo.getLblEditEmployeeName())%></td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="UnderInputTable" id="tblUnder">
		<tr>
			<th class="EditSelectTh" id="thGivingHead"></th>
			<th class="EditSelectTh" id="thGivingHead" colspan="2"><%=params.getName("SumTotal")%></th>
			<th class="EditSelectTh" id="thGivingHead" colspan="2"><%=params.getName("Ahead","FiscalYear")%>&nbsp;
				<select class="Name2PullDown" id="pltEditFormerGivingType" name="pltEditFormerGivingType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltFormerGivingType(), vo.getPltEditFormerGivingType())%>
				</select>
			</th>
			<th class="EditSelectTh" id="thGivingHead" colspan="2"><%=params.getName("Now","FiscalYear")%>&nbsp;
				<select class="Name2PullDown" id="pltEditGivingType" name="pltEditGivingType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltGivingType(), vo.getPltEditGivingType())%>
				</select>
			</th>
			<th class="EditSelectTh" id="thGivingHead"><%=params.getName("Stock")%>&nbsp;
				<select class="Name2PullDown" id="pltEditStockType" name="pltEditStockType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltStockType(), vo.getPltEditStockType())%>
				</select>
			</th>
		</tr>
		<tr>
			<th class="EditSelectTh"><%=params.getName("Possession","Num","FrontParentheses","Change","Ahead","BackParentheses")%></th>
			<td class="TitleTd" id="tdHalf"   <%=vo.getClaBeforeTotalDate()%>><%=HtmlUtility.escapeHTML(vo.getLblBeforeTotalDate())%><%=params.getName("Day")%></td>
			<td class="TitleTd" id="tdHalf"   <%=vo.getClaBeforeTotalTime()%>><%=HtmlUtility.escapeHTML(vo.getLblBeforeTotalTime())%><%=params.getName("Time")%></td>
			<td class="TitleTd" id="tdHalf"   <%=vo.getClaBeforeFormerDate()%>><%=HtmlUtility.escapeHTML(vo.getLblBeforeFormerDate())%><%=params.getName("Day")%></td>
			<td class="TitleTd" id="tdHalf"   <%=vo.getClaBeforeFormerTime()%>><%=HtmlUtility.escapeHTML(vo.getLblBeforeFormerTime())%><%=params.getName("Time")%></td>
			<td class="TitleTd" id="tdHalf"   <%=vo.getClaBeforeDate()%>><%=HtmlUtility.escapeHTML(vo.getLblBeforeDate())%><%=params.getName("Day")%></td>
			<td class="TitleTd" id="tdHalf"   <%=vo.getClaBeforeTime()%>><%=HtmlUtility.escapeHTML(vo.getLblBeforeTime())%><%=params.getName("Time")%></td>
			<td class="TitleTd" id="tdDouble" <%=vo.getClaBeforeStockDate()%>><%=HtmlUtility.escapeHTML(vo.getLblBeforeStockDate())%><%=params.getName("Day")%></td>
		</tr>
		<tr>
			<th class="EditSelectTh"><%=params.getName("Treatment","Days")%></th>
			<td class="SelectTd"></td>
			<td class="SelectTd"></td>
			<td class="SelectTd"><input type="text" class="Numeric4RequiredTextBox" id="txtEditFormerGivingDay" name="txtEditFormerGivingDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditFormerGivingDay())%>">&nbsp;<label for="txtEditFormerGivingDay"><%=params.getName("Day")%></label></td>
			<td class="SelectTd"><input type="text" class="Number2TextBox" id="txtEditFormerGivingTime" name="txtEditFormerGivingTime" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditFormerGivingTime())%>">&nbsp;<label for="txtRestStart1Hour"><%=params.getName("Time")%></label></td>
			<td class="SelectTd"><input type="text" class="Numeric4RequiredTextBox" id="txtEditGivingDay" name="txtEditGivingDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditGivingDay())%>">&nbsp;<label for="txtEditGivingDay"><%=params.getName("Day")%></label></td>
			<td class="SelectTd"><input type="text" class="Number2TextBox" id="txtEditGivingTime" name="txtEditGivingTime" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditGivingTime())%>">&nbsp;<label for="txtEditGivingTime"><%=params.getName("Time")%></label></td>
			<td class="SelectTd"><input type="text" class="Numeric4RequiredTextBox" id="txtEditGivingStockDay" name="txtEditGivingStockDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditGivingStockDay())%>">&nbsp;<label for="txtEditGivingStockDay"><%=params.getName("Day")%></label></td>
		</tr>
		<tr>
			<th class="EditSelectTh"><%=params.getName("Possession","Num","FrontParentheses","Change","Later","BackParentheses")%></th>
			<td class="TitleTd" id="tdGivingHoliday" <%=vo.getClaAfterTotalDate()%>><%=HtmlUtility.escapeHTML(vo.getLblAfterTotalDate())%><%=params.getName("Day")%></td>
			<td class="TitleTd" id="tdGivingHoliday" <%=vo.getClaAfterTotalTime()%>><%=HtmlUtility.escapeHTML(vo.getLblAfterTotalTime())%><%=params.getName("Time")%></td>
			<td class="TitleTd" id="tdGivingHoliday" <%=vo.getClaAfterFormerDate()%>><%=HtmlUtility.escapeHTML(vo.getLblAfterFormerDate())%><%=params.getName("Day")%></td>
			<td class="TitleTd" id="tdGivingHoliday" <%=vo.getClaAfterFormerTime()%>><%=HtmlUtility.escapeHTML(vo.getLblAfterFormerTime())%><%=params.getName("Time")%></td>
			<td class="TitleTd" id="tdGivingHoliday" <%=vo.getClaAfterDate()%>><%=HtmlUtility.escapeHTML(vo.getLblAfterDate())%><%=params.getName("Day")%></td>
			<td class="TitleTd" id="tdGivingHoliday" <%=vo.getClaAfterTime()%>><%=HtmlUtility.escapeHTML(vo.getLblAfterTime())%><%=params.getName("Time")%></td>
			<td class="TitleTd" id="tdGivingHoliday" <%=vo.getClaAfterStockDate()%>><%=HtmlUtility.escapeHTML(vo.getLblAfterStockDate())%><%=params.getName("Day")%></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, '', checkPaidHolidayGiving, '<%=PaidHolidayHistoryAction.CMD_REGIST%>')"><%=params.getName("Insert")%></button>
			</td>
		</tr>
	</table>
</div>
<div class="List">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Giving","Information","Search")%></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("GrantDate")%></td>
			<td class="InputTd" id="TdSearchActivateDate">
			<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>"/>
			<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
			<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>"/>
			<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
			<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())%>"/>
			<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>&nbsp;
				<button type="button" class="Name2Button" id="btnActivateDate"  onclick="submitForm(event, 'TdSearchActivateDate', null, '<%=PaidHolidayHistoryAction.CMD_SET_ACTIVATE_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
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
			<td class="Blank" colspan="2"></td>
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
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitTransfer(event, null, null, null, '<%=PaidHolidayHistoryAction.CMD_SEARCH%>');"><%=params.getName("Search")%></button>
			</td>
		</tr>
	</table>
</div>
<%=HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex())%>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=ActivateDateComparator.class.getName()%>'), '<%=PaidHolidayHistoryAction.CMD_SORT%>');"><%=params.getName("GrantDate")%><%=PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=PaidHolidayHistoryListEmployeeCodeComparator.class.getName()%>'), '<%=PaidHolidayHistoryAction.CMD_SORT%>');"><%=PfNameUtility.employeeCode(params)%><%= PlatformUtility.getSortMark(PaidHolidayHistoryListEmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayHistoryListEmployeeNameComparator.class.getName() %>'), '<%= PaidHolidayHistoryAction.CMD_SORT %>');"><%= params.getName("Employee","FirstName") %><%= PlatformUtility.getSortMark(PaidHolidayHistoryListEmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayHistoryListSectionCodeComparator.class.getName() %>'), '<%= PaidHolidayHistoryAction.CMD_SORT %>');"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(PaidHolidayHistoryListSectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thFormerDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayHistoryListFormerDateComparator.class.getName() %>'), '<%= PaidHolidayHistoryAction.CMD_SORT %>');"><%= params.getName("PreviousYear","Days") %><%= PlatformUtility.getSortMark(PaidHolidayHistoryListFormerDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thFormerTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayHistoryListFormerTimeComparator.class.getName() %>'), '<%= PaidHolidayHistoryAction.CMD_SORT %>');"><%= params.getName("PreviousYear","Time") %><%= PlatformUtility.getSortMark(PaidHolidayHistoryListFormerTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayHistoryListDateComparator.class.getName() %>'), '<%= PaidHolidayHistoryAction.CMD_SORT %>');"><%= params.getName("ThisYear","Days") %><%= PlatformUtility.getSortMark(PaidHolidayHistoryListDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayHistoryListTimeComparator.class.getName() %>'), '<%= PaidHolidayHistoryAction.CMD_SORT %>');"><%= params.getName("ThisYear","Time") %><%= PlatformUtility.getSortMark(PaidHolidayHistoryListTimeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thStockDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayHistoryListStockDateComparator.class.getName() %>'), '<%= PaidHolidayHistoryAction.CMD_SORT %>');"><%= params.getName("Stock") %><%= PlatformUtility.getSortMark(PaidHolidayHistoryListStockDateComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" id="btnPaidHolidayGivingCard" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= i %>', '<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%=PaidHolidayReferenceAction.class.getName() %>'), '<%= PaidHolidayHistoryAction.CMD_TRANSFER %>');"><%= params.getName("List") %></button>&nbsp;
					<button type="button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= HtmlUtility.escapeHTML(vo.getAryPersonalId(i)) %>', '<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>'), '<%= PaidHolidayHistoryAction.CMD_EDIT_TARGET %>');"><%= params.getName("Select") %></button>
				</td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection()[i]) %></td>
				<td class="ListSelectTd"><span <%= vo.getAryClaFormerDate()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblFormerDate()[i]) %><%= params.getName("Day") %></span></td>
				<td class="ListSelectTd"><span <%= vo.getAryClaFormerTime()[i] %>><%= HtmlUtility.escapeHTML(vo.getAryLblFormerTime()[i]) %><%= params.getName("Time") %></span></td>
				<td class="ListSelectTd"><span <%= vo.getAryClaDate()[i]       %>><%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %><%= params.getName("Day") %></span></td>
				<td class="ListSelectTd"><span <%= vo.getAryClaTime()[i]       %>><%= HtmlUtility.escapeHTML(vo.getAryLblTime()[i]) %><%= params.getName("Time") %></span></td>
				<td class="ListSelectTd"><span <%= vo.getAryClaStockDate()[i]  %>><%= HtmlUtility.escapeHTML(vo.getAryLblStockDate()[i]) %><%= params.getName("Day") %></span></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getList().isEmpty()) {
	return;
}
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
