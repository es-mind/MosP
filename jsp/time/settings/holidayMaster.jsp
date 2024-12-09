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
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.HolidayMasterContinuousAcquisitionComparator"
import = "jp.mosp.time.comparator.settings.HolidayMasterHolidayAbbrComparator"
import = "jp.mosp.time.comparator.settings.HolidayMasterHolidayCodeComparator"
import = "jp.mosp.time.comparator.settings.HolidayMasterHolidayGivingComparator"
import = "jp.mosp.time.comparator.settings.HolidayMasterHolidayLimitDayComparator"
import = "jp.mosp.time.comparator.settings.HolidayMasterHolidayLimitMonthComparator"
import = "jp.mosp.time.comparator.settings.HolidayMasterHolidayName"
import = "jp.mosp.time.comparator.settings.HolidayMasterHolidayTypeComparator"
import = "jp.mosp.time.comparator.settings.HolidayMasterSalaryComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.settings.action.HolidayMasterAction"
import = "jp.mosp.time.settings.action.OtherHolidayHistoryAction"
import = "jp.mosp.time.settings.action.SpecialHolidayHistoryAction"
import = "jp.mosp.time.settings.vo.HolidayMasterVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HolidayMasterVo vo = (HolidayMasterVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="InputTable" id="tblCard">
		<thead>
			<tr>
				<th class="EditTableTh" colspan="6">
					<jsp:include page="<%=TimeConst.PATH_SETTINGS_EDIT_HEADER_JSP%>" flush="false" />
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("ActivateDate")%></td>
				<td class="InputTd">
					<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateYear())%>" />
					<label for="txtEditActivateYear"><%=params.getName("Year")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth())%>" />
					<label for="txtEditActivateMonth"><%=params.getName("Month")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateDay())%>" />
					<label for="txtEditActivateDay"><%=params.getName("Day")%></label>
				</td>
				<td class="TitleTd"><%=params.getName("Holiday","Type")%></td>
				<td class="InputTd">
					<select class="Name4PullDown" id="pltEditHolidayType" name="pltEditHolidayType">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_HOLIDAY_TYPE_MASTER, vo.getPltEditHolidayType(), false)%>
					</select>
				</td>
				<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtEditHolidayCode"><%=params.getName("Holiday","Code")%></label></td>
				<td class="InputTd">
					<input type="text" class="Code2RequiredTextBox" id="txtEditHolidayCode" name="txtEditHolidayCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayCode())%>" />
				</td>
			</tr>
			<tr>
				<td class="TitleTd">
					<%=HtmlUtility.getRequiredMark()%><label for="txtEditHolidayName"><%=params.getName("Holiday","Name")%></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Name15RequiredTextBox" id="txtEditHolidayName" name="txtEditHolidayName" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayName())%>" />
				</td>
				<td class="TitleTd">
					<%=HtmlUtility.getRequiredMark()%><label for="txtEditHolidayAbbr"><%=params.getName("Holiday","Abbreviation")%></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Byte6RequiredTextBox" id="txtEditHolidayAbbr" name="txtEditHolidayAbbr" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayAbbr())%>" />
				</td>
				<td class="TitleTd">
					<%=HtmlUtility.getRequiredMark()%><label for="txtEditHolidayGiving"><%=params.getName("Standard","Giving","Days")%></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Numeric4RequiredTextBox" id="txtEditHolidayGiving" name="txtEditHolidayGiving" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayGiving())%>" />&nbsp;<%=params.getName("Day")%>
					<input type="checkbox" class="CheckBox" id="ckbNoLimit" name="ckbNoLimit" value="<%=MospConst.CHECKBOX_ON%>" <%=HtmlUtility.getChecked(vo.getCkbNoLimit())%> />&nbsp;<%=params.getName("NoLimit")%>
				</td>
			</tr>
			<tr>
				<td class="TitleTd">
					<%=HtmlUtility.getRequiredMark()%><label for="txtEditHolidayLimit"><%=params.getName("Acquisition","TimeLimit")%></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtEditHolidayLimitMonth" name="txtEditHolidayLimitMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayLimitMonth())%>" />&nbsp;<%=params.getName("Months")%>
					<input type="text" class="Number2RequiredTextBox" id="txtEditHolidayLimitDay" name="txtEditHolidayLimitDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditHolidayLimitDay())%>" />&nbsp;<%=params.getName("Day")%>
				</td>
				<td class="TitleTd"><%=params.getName("HalfTime","Application")%></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditHalfHolidayRequest" name="pltEditHalfHolidayRequest">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_HALF_HOLIDAY_REQUEST, vo.getPltEditHalfHolidayRequest(), false)%>
					</select>
				</td>
				<td class="TitleTd"><span class="RequiredLabel"></span><%=params.getName("Time","Unit","Acquisition")%></td>
				<td class="InputTd">
					<%=HtmlUtility.getSelectTag(params, "Name2PullDown", "pltEditHourlyHoliday", "pltEditHourlyHoliday", vo.getPltEditHourlyHoliday(), PlatformConst.CODE_KEY_INACTIVATE_FLAG, false, false)%>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%=params.getName("Continuousness","Acquisition")%></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditContinue" name="pltEditContinue">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_CONTINUE, vo.getPltEditContinue(), false)%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("GoingWork","Rate","Calc")%></td>
				<td class="InputTd">
					<select class="Name5PullDown" id="pltEditPaidHolidayCalc" name="pltEditPaidHolidayCalc">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_PAID_HOLIDAY_CALC, vo.getPltEditPaidHolidayCalc(), false)%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("Salary","Type")%></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditSalary" name="pltEditSalary">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_SALARY_PAY_TYPE, vo.getPltEditSalary(), false)%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%=params.getName("Reason", "Input")%></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditReasonType" name="pltEditReasonType">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_REASON_TYPE, vo.getPltEditReasonType(), false)%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("Effectiveness","Slash","Inactivate")%></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
						<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false)%>
					</select>
				</td>
				<td class="Blank" colspan="2"></td>
			</tr>
		</tbody>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'tblCard', checkHolidayGiving, '<%=HolidayMasterAction.CMD_REGIST%>')"><%=params.getName("Insert")%></button>
			</td>
		</tr>
	</table>
</div>
<div class="List" id="divSearch">
	<table class="InputTable">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="10">
					<span class="TitleTh"><%=PfNameUtility.search(params)%></span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("ActivateDate")%></td>
				<td class="InputTd" colspan="3">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>" />
					<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>" />
					<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())%>" />
					<label for="txtSearchActivateDay"><%=params.getName("Day")%></label>
				<td class="TitleTd"><%=params.getName("Holiday","Type")%></td>
				<td class="InputTd">
					<select class="Name4PullDown" id="pltSearchHolidayType" name="pltSearchHolidayType">
						<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_HOLIDAY_MASTER_TYPE, vo.getPltSearchHolidayType(), true)%>
					</select>
				</td>
				<td class="TitleTd"><%=params.getName("Holiday","Code")%></td>
				<td class="InputTd">
					<input type="text" class="Code2TextBox" id="txtSearchHolidayCode" name="txtSearchHolidayCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchHolidayCode())%>" />
				</td>
				<td class="TitleTd"><%=params.getName("EffectivenessExistence","Slash","InactivateExistence")%></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
						<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true)%>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="divSearch">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%=HolidayMasterAction.CMD_SEARCH%>')"><%=PfNameUtility.search(params)%></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList" id="divList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayMasterHolidayCodeComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("Code") %><%= PlatformUtility.getSortMark(HolidayMasterHolidayCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayMasterHolidayTypeComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("Type") %><%= PlatformUtility.getSortMark(HolidayMasterHolidayTypeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayMasterHolidayName.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("Name") %><%= PlatformUtility.getSortMark(HolidayMasterHolidayName.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayMasterHolidayAbbrComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("Abbreviation") %><%= PlatformUtility.getSortMark(HolidayMasterHolidayAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayGiving" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayMasterHolidayGivingComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("Giving") %><%= PlatformUtility.getSortMark(HolidayMasterHolidayGivingComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayLimit" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayMasterHolidayLimitMonthComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("TimeLimit") %><%= PlatformUtility.getSortMark(HolidayMasterHolidayLimitMonthComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidayContinue" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayMasterContinuousAcquisitionComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("Continuousness") %><%= PlatformUtility.getSortMark(HolidayMasterContinuousAcquisitionComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thHolidaySalary" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= HolidayMasterSalaryComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("Salary") %><%= PlatformUtility.getSortMark(HolidayMasterSalaryComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= HolidayMasterAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (vo.getAryLblActivateDate().length > 0) {
%>
					<input type="checkbox" class="" id="ckbSelect" onclick="doAllBoxChecked(this)">
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblHolidayCode()[i]) %>',  '<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= vo.getAryLblHolidayType()[i] %>'), '<%= HolidayMasterAction.CMD_EDIT_MODE %>')"><%= params.getName("Select") %></button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayCode()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayTypeName()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayName()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayAbbr()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayGiving()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayLimit()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblHolidayContinue()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblHolidaySalary()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i]) %></td>
				<td class="ListSelectTd"><input type="checkbox" class="CheckBox" name="ckbSelect" value="<%= vo.getAryCkbRecordId()[i] %>" <%= HtmlUtility.getChecked(vo.getAryCkbRecordId()[i], vo.getCkbSelect()) %> /></td>
			</tr>
<%
}
if (vo.getAryLblActivateDate().length > 0) {
%>
			<tr>
				<td class="TitleTd" colspan="12">
					<span class="TableButtonSpan">
						<button type="button" class="Name4Button" id="btnDelete" onclick="submitDelete(event, 'divList', checkExtra, '<%= HolidayMasterAction.CMD_DELETE %>')"><%= params.getName("History","Delete") %></button>
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
if (vo.getAryLblActivateDate().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="4">
				<span class="TitleTh"><%= params.getName("Bulk","Update") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" /> <%= params.getName("Year") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" /> <%= params.getName("Month") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" /> <%= params.getName("Day") %>&nbsp;
			</td>
			<td class="TitleTd"><%= params.getName("Effectiveness","Slash","Inactivate") %></td>
			<td class="InputTd">
				<select id="pltUpdateInactivate" name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnUpdate" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= HolidayMasterAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Update") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name8Button" onclick="submitTransfer(event, null, null, null, '<%= SpecialHolidayHistoryAction.CMD_SHOW %>');"><%= params.getName("Specially","Holiday","Giving") %></button>
	<button type="button" class="Name8Button" onclick="submitTransfer(event, null, null, null, '<%= OtherHolidayHistoryAction.CMD_SHOW %>');"><%= params.getName("Others","Holiday","Giving") %></button>
</div>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
