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
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.comparator.settings.PaidHolidayMasterPaidHolidayAbbrComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayMasterPaidHolidayCodeComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayMasterPaidHolidayNameComparator"
import = "jp.mosp.time.comparator.settings.PaidHolidayMasterPaidHolidayTypeComparator"
import = "jp.mosp.time.settings.action.PaidHolidayCardAction"
import = "jp.mosp.time.settings.action.PaidHolidayListAction"
import = "jp.mosp.time.settings.vo.PaidHolidayListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PaidHolidayListVo vo = (PaidHolidayListVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Search") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd" id="tdActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
				<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
				<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />
				<label for="txtSearchActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><label for="txtSearchPaidHolidayName"><%= params.getName("PaidHolidayType") %></label></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltSearchPaidHolidayType" name="pltSearchPaidHolidayType">
					<option value=""></option>
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchPaidHolidayType(), vo.getPltSearchPaidHolidayType()) %>
				</select>
			</td>
			<td class="TitleTd"><label for="txtSearchPaidHolidayName"><%= TimeNamingUtility.paidHolidayCodeAbbr(params) %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchPaidHolidayCode" name="txtSearchPaidHolidayCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPaidHolidayCode()) %>" /></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchPaidHolidayName"><%= TimeNamingUtility.paidHolidayNameAbbr(params) %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchPaidHolidayName" name="txtSearchPaidHolidayName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPaidHolidayName()) %>" /></td>
			<td class="TitleTd"><label for="txtSearchPaidHolidayAbbr"><%= TimeNamingUtility.paidHolidayAbbreviationAbbr(params) %></label></td>
			<td class="InputTd"><input type="text" class="Byte6TextBox" id="txtSearchPaidHolidayAbbr" name="txtSearchPaidHolidayAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPaidHolidayAbbr()) %>" /></td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%= params.getName("Effectiveness","Slash","Inactivate") %></label></td>
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
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= PaidHolidayListAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<tr>
			<th class="ListSortTh" id="thButton"></th>
			<th class="ListSortTh" id="ththActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= PaidHolidayListAction.CMD_SORT %>')"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPaidHolidayType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayMasterPaidHolidayTypeComparator.class.getName() %>'), '<%= PaidHolidayListAction.CMD_SORT %>')"><%= params.getName("PaidHolidayType") %><%= PlatformUtility.getSortMark(PaidHolidayMasterPaidHolidayTypeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPaidHolidayCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayMasterPaidHolidayCodeComparator.class.getName() %>'), '<%= PaidHolidayListAction.CMD_SORT %>')"><%= TimeNamingUtility.paidHolidayCodeAbbr(params) %><%= PlatformUtility.getSortMark(PaidHolidayMasterPaidHolidayCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPaidHolidayName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayMasterPaidHolidayNameComparator.class.getName() %>'), '<%= PaidHolidayListAction.CMD_SORT %>')"><%= TimeNamingUtility.paidHolidayNameAbbr(params) %><%= PlatformUtility.getSortMark(PaidHolidayMasterPaidHolidayNameComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thPaidHolidayAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= PaidHolidayMasterPaidHolidayAbbrComparator.class.getName() %>'), '<%= PaidHolidayListAction.CMD_SORT %>')"><%= TimeNamingUtility.paidHolidayAbbreviationAbbr(params) %><%= PlatformUtility.getSortMark(PaidHolidayMasterPaidHolidayAbbrComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= PaidHolidayListAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
		</tr>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
		<tr>
			<td class="ListSelectTd">
				<button type="button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblPaidHolidayCode()[i]) %>'), '<%= PaidHolidayCardAction.CMD_SELECT_SHOW %>')"><%= params.getName("Detail") %></button>
			</td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHolidayType()[i]) %></td>
			<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHolidayCode()[i]) %></td>
			<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHolidayName()[i]) %></td>
			<td class="ListInputTd"  id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPaidHolidayAbbr()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i]) %></td>
		</tr>
<%
}
%>
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
	<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, null, '<%= PaidHolidayCardAction.CMD_SHOW %>');"><%= params.getName("New","Insert") %></button>
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
