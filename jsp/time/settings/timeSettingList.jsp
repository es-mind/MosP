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
import = "jp.mosp.time.comparator.settings.TimeSettingMasterCutoffCodeComparator"
import = "jp.mosp.time.comparator.settings.TimeSettingMasterWorkSettingAbbrComparator"
import = "jp.mosp.time.comparator.settings.TimeSettingMasterWorkSettingCodeComparator"
import = "jp.mosp.time.comparator.settings.TimeSettingMasterWorkSettingNameComparator"
import = "jp.mosp.time.settings.action.TimeSettingCardAction"
import = "jp.mosp.time.settings.action.TimeSettingListAction"
import = "jp.mosp.time.settings.vo.TimeSettingListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TimeSettingListVo vo = (TimeSettingListVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("Search") %></span></th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdActivateDate"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd" id="tdNoBorderBottom">
				<div id="divSearchCutoffDate">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
					<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
					<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />
					<label for="txtSearchActivateDay"><%= params.getName("Day") %></label>
					<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'divSearchCutoffDate', null, '<%= TimeSettingListAction.CMD_SET_ACTIVATION_DATE %>')"><%= vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision") %></button>
				</div>
			</td>
			<td class="TitleTd"><label for="txtSearchTimeSettingCode"><%= params.getName("WorkManage","Set","Code") %></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchTimeSettingCode" name="txtSearchTimeSettingCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchTimeSettingCode()) %>"/></td>
			<td class="TitleTd"><label for="txtSearchTimeSettingName"><%= params.getName("WorkManage","Set","Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchTimeSettingName" name="txtSearchTimeSettingName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchTimeSettingName()) %>"/></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="pltSearchCutoffDate"><%= params.getName("CutoffDate","Abbreviation") %></label></td>
			<td class="InputTd" id="tdNoBorderTop">
				<select class="Name13PullDown" id="pltSearchCutoffDate" name="pltSearchCutoffDate">
					<option value=""></option>
					<%= HtmlUtility.getSelectOption(vo.getAryPltSearchCutoffDate(), vo.getPltSearchCutoffDate()) %>
				</select>
			</td>
			<td class="TitleTd"><label for="txtSearchTimeSettingAbbr"><%= params.getName("WorkManage","Set","Abbreviation") %></label></td>
			<td class="InputTd"><input type="text" class="Byte6TextBox" id="txtSearchTimeSettingAbbr" name="txtSearchTimeSettingAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchTimeSettingAbbr()) %>"/></td>
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
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= TimeSettingListAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
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
				<th class="ListSortTh"   id="thActivateDate"	onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= TimeSettingListAction.CMD_SORT %>')"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thTimeSettingCode"	onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= TimeSettingMasterWorkSettingCodeComparator.class.getName() %>'), '<%= TimeSettingListAction.CMD_SORT %>')"><%= params.getName("WorkManage","Set","Code") %><%= PlatformUtility.getSortMark(TimeSettingMasterWorkSettingCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thTimeSettingName"	onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= TimeSettingMasterWorkSettingNameComparator.class.getName() %>'), '<%= TimeSettingListAction.CMD_SORT %>')"><%= params.getName("WorkManage","Set","Name") %><%= PlatformUtility.getSortMark(TimeSettingMasterWorkSettingNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thTimeSettingAbbr"	onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= TimeSettingMasterWorkSettingAbbrComparator.class.getName() %>'), '<%= TimeSettingListAction.CMD_SORT %>')"><%= params.getName("WorkManage","Set","Abbreviation") %><%= PlatformUtility.getSortMark(TimeSettingMasterWorkSettingAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thCutoffDate"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= TimeSettingMasterCutoffCodeComparator.class.getName() %>'), '<%= TimeSettingListAction.CMD_SORT %>')"><%= params.getName("CutoffDate","Abbreviation") %><%= PlatformUtility.getSortMark(TimeSettingMasterCutoffCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh"   id="thInactivate"		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= TimeSettingListAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (!vo.getList().isEmpty()) {
%>
					<input type="checkbox" onclick="doAllBoxChecked(this);" />
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
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= vo.getAryLblSettingCode()[i] %>'), '<%= TimeSettingCardAction.CMD_SELECT_SHOW %>');"><%= params.getName("Detail") %></button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblSettingCode()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblSettingName()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblSettingAbbr()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblCutoff()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i]) %></td>
				<td class="ListSelectTd"><input type="checkbox" class="SelectCheckBox" name="ckbSelect" value="<%= vo.getAryCkbRecordId()[i] %>" <%= HtmlUtility.getChecked(vo.getAryCkbRecordId()[i], vo.getCkbSelect()) %> /></td>
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
if (vo.getAryLblActivateDate().length > 0) {
%>
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="4">
				<span class="TitleTh"><%= params.getName("Bulk","Update") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" />
				<label for="txtUpdateActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />
				<label for="txtUpdateActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" />
				<label for="txtUpdateActivateDay"><%= params.getName("Day") %></label>
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
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= TimeSettingListAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Update") %></button>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitForm(event, null, null, '<%= TimeSettingCardAction.CMD_SHOW %>')"><%= params.getName("New","Insert") %></button>
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
