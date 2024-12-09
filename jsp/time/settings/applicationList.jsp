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
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.ApplicationMasterApplicationAbbrComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterApplicationCodeComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterEmploymentContractCodeComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterPaidHolidayCodeComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterPersonalIdComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterPositionCodeComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterScheduleCodeComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterSectionCodeComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterWorkPlaceCodeComparator"
import = "jp.mosp.time.comparator.settings.ApplicationMasterWorkSettingCodeComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.settings.action.ApplicationCardAction"
import = "jp.mosp.time.settings.action.ApplicationListAction"
import = "jp.mosp.time.settings.vo.ApplicationListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApplicationListVo vo = (ApplicationListVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("Search") %></span><a></a></th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<div id="divSearchClassRoute">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
					<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
					<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />
					<label for="txtSearchActivateDay"><%= params.getName("Day") %></label>
					<button type="button" class="Name2Button" onclick="submitForm(event, 'divSearchClassRoute', null, '<%= ApplicationListAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
				</div>
			</td>
			<td class="TitleTd"><label for="txtSearchApplicationCode"><%= params.getName("Set","Apply","Code") %></label></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchApplicationCode" name="txtSearchApplicationCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApplicationCode()) %>" />
			</td>
			<td class="TitleTd"><label for="txtSearchApplicationName"><%= params.getName("Set","Apply","Name") %></label></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchApplicationName" name="txtSearchApplicationName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApplicationName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchApplicationAbbr"><%= params.getName("Set","Apply","Abbreviation") %></label></td>
			<td class="InputTd">
				<input type="text" class="Byte6TextBox" id="txtSearchApplicationAbbr" name="txtSearchApplicationAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchApplicationAbbr()) %>" />
			</td>
			<td class="TitleTd"><%= params.getName("Effectiveness","Slash","Inactivate") %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="InputTable">
		<tr>
			<td class="TitleTd" id="tdRadio" rowspan="2">
				<input type="radio" class="RadioButton" name="radApplicationType" id="radMaster" value="<%= PlatformConst.APPLICATION_TYPE_MASTER %>" <%= HtmlUtility.getChecked(vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_MASTER)) %> />
			</td>
			<td class="TitleTd" id="tdMasterTitle1"><%= params.getName("WorkPlace","Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchWorkPlaceMaster" name="pltSearchWorkPlaceMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchWorkPlaceMaster(), vo.getPltSearchWorkPlaceMaster()) %>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%= params.getName("EmploymentContract","Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchEmploymentMaster" name="pltSearchEmploymentMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchEmploymentMaster(), vo.getPltSearchEmploymentMaster()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdMasterTitle1"><%= params.getName("Section","Name") %></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltSearchSectionMaster" name="pltSearchSectionMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchSectionMaster(), vo.getPltSearchSectionMaster()) %>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%= params.getName("Position","Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchPositionMaster" name="pltSearchPositionMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchPositionMaster(), vo.getPltSearchPositionMaster()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRadio">
				<input type="radio" class="RadioButton" name="radApplicationType" id="radEmployeeCode" value="<%= PlatformConst.APPLICATION_TYPE_PERSON %>" <%= HtmlUtility.getChecked(vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_PERSON)) %> />
			</td>
			<td class="TitleTd"><%= params.getName("Personal","FrontParentheses","InputCsv","BackParentheses") %></td>
			<td class="InputTd" colspan="3">
				<input type="text" class="CodeCsvTextBox" id="txtSearchEmployeeCode" name="txtSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeCode()) %>" />
			</td>
		</tr>
	</table>
	<table class="InputTable">
		<tr>
			<td class="TitleTd"><%= params.getName("WorkManage","Set","Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchWorkSetting" name="pltSearchWorkSetting">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchWorkSetting(), vo.getPltSearchWorkSetting()) %>
				</select>
			</td>
			<td class="TitleTd"><%= params.getName("Calendar","Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchSchedule" name="pltSearchSchedule">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchSchedule(), vo.getPltSearchSchedule()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("PaidVacation","Set","Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSearchPaidHoliday" name="pltSearchPaidHoliday">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchPaidHoliday(), vo.getPltSearchPaidHoliday()) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitTransfer(event, null, null, null, '<%= ApplicationListAction.CMD_SEARCH%>');" ><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh"	 id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh"	 id="thApplicationCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterApplicationCodeComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("Code") %><%= PlatformUtility.getSortMark(ApplicationMasterApplicationCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"	 id="thApplicationAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterApplicationAbbrComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("Abbreviation") %><%= PlatformUtility.getSortMark(ApplicationMasterApplicationAbbrComparator.class.getName(), params) %></th>
<%
if (vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_MASTER)) {
%>
				<th class="ListSortTh"	 id="thWorkPlace" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterWorkPlaceCodeComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("WorkPlaceAbbr") %><%= PlatformUtility.getSortMark(ApplicationMasterWorkPlaceCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"	 id="thEmployment" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterEmploymentContractCodeComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("EmploymentContractAbbr") %><%= PlatformUtility.getSortMark(ApplicationMasterEmploymentContractCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"	 id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterSectionCodeComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(ApplicationMasterSectionCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"	 id="thPosition" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterPositionCodeComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("Position") %><%= PlatformUtility.getSortMark(ApplicationMasterPositionCodeComparator.class.getName(), params) %></th>
<%
} else {
%>
				<th class="ListSortTh"   id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterPersonalIdComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("Personal") %><%= PlatformUtility.getSortMark(ApplicationMasterPersonalIdComparator.class.getName(), params) %></th>
<%
}
%>
				<th class="EditSortTh"	 id="tdBorderLeft" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterWorkSettingCodeComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("WorkManage","Set") %><%= PlatformUtility.getSortMark(ApplicationMasterWorkSettingCodeComparator.class.getName(), params) %></th>
				<th class="EditSortTh"	 id="thSchadeule" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterScheduleCodeComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("Calendar") %><%= PlatformUtility.getSortMark(ApplicationMasterScheduleCodeComparator.class.getName(), params) %></th>
				<th class="EditSortTh"	 id="thPaidHoliday" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ApplicationMasterPaidHolidayCodeComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= TimeNamingUtility.paidHolidaySettingAbbr(params) %><%= PlatformUtility.getSortMark(ApplicationMasterPaidHolidayCodeComparator.class.getName(), params) %></th>
				<th class="EditSortTh"	 id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= ApplicationListAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" id="btnSelect" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblApplicationCode()[i]) %>', '<%= TimeConst.PRM_TRANSFERRED_TYPE %>', '<%= HtmlUtility.escapeHTML(vo.getRadApplicationType()) %>'), '<%= ApplicationCardAction.CMD_SELECT_SHOW %>');" ><%= params.getName("Detail") %></button>
				</td>
				<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblApplicationCode()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblApplicationAbbr()[i]) %></td>
<% if (vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_MASTER)) { %>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblWorkPlace()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEmployment()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblSection()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPosition()[i]) %></td>
<% } else { %>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
<% } %>
				<td class="ListSelectTd" id="tdBorderLeft"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkSetting()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblSchedule()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHoliday()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i]) %></td>
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
	<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, null, '<%= ApplicationCardAction.CMD_SHOW %>');"><%= params.getName("New","Insert") %></button>
</div>
<%
if (vo.getAryLblActivateDate().length > 0) {
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
