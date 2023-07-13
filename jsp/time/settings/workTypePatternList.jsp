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
import = "jp.mosp.time.comparator.settings.WorkTypePatternAbbrComparator"
import = "jp.mosp.time.comparator.settings.WorkTypePatternCodeComparator"
import = "jp.mosp.time.comparator.settings.WorkTypePatternNameComparator"
import = "jp.mosp.time.settings.action.WorkTypePatternCardAction"
import = "jp.mosp.time.settings.action.WorkTypePatternListAction"
import = "jp.mosp.time.settings.vo.WorkTypePatternListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
WorkTypePatternListVo vo = (WorkTypePatternListVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="8">
					<span class="TitleTh"><%= params.getName("Search") %></span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
				<td class="InputTd">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
					<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
					<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
				</td>
				<td class="TitleTd"><label for="txtSearchPatternCode"><%= params.getName("Pattern","Code") %></label></td>
				<td class="InputTd">
					<input type="text" class="Code10TextBox" id="txtSearchPatternCode" name="txtSearchPatternCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPatternCode()) %>" />
				</td>
				<td class="TitleTd"><label for="txtSearchPatternName"><%= params.getName("Pattern","Name") %></label></td>
				<td class="InputTd">
					<input type="text" class="Name15TextBox" id="txtSearchPatternName" name="txtSearchPatternName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPatternName()) %>" />
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="txtSearchPatternAbbr"><%= params.getName("Pattern","Abbreviation") %></label></td>
				<td class="InputTd">
					<input type="text" class="Byte6TextBox" id="txtSearchPatternAbbr" name="txtSearchPatternAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchPatternAbbr()) %>" />
				</td>
				<td class="TitleTd"><label for="pltSearchInactivate"><%= params.getName("Effectiveness") %><%= params.getName("Slash","Inactivate") %></label></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
					</select>
				</td>
				<td class="Blank" colspan="2"></td>
			</tr>
		</tbody>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', checkDatesYearMonth, '<%= WorkTypePatternListAction.CMD_SEARCH %>');"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName() %>'), '<%= WorkTypePatternListAction.CMD_SORT %>');"><%= params.getName("ActivateDate") %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPatternCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypePatternCodeComparator.class.getName() %>'), '<%= WorkTypePatternListAction.CMD_SORT %>');"><%= params.getName("Pattern","Code") %><%= PlatformUtility.getSortMark(WorkTypePatternCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPatternName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypePatternNameComparator.class.getName() %>'), '<%= WorkTypePatternListAction.CMD_SORT %>');"><%= params.getName("Pattern","Name") %><%= PlatformUtility.getSortMark(WorkTypePatternNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPatternAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypePatternAbbrComparator.class.getName() %>'), '<%= WorkTypePatternListAction.CMD_SORT %>');"><%= params.getName("Pattern","Abbreviation") %><%= PlatformUtility.getSortMark(WorkTypePatternAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= WorkTypePatternListAction.CMD_SORT %>');"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
			</tr>
		</thead>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
		<tbody>
			<tr>
				<td class="ListSelectTd" id="">
					<button type="button" id="btnSelect<%= i %>" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= vo.getAryLblPatternCode()[i] %>'), '<%= WorkTypePatternCardAction.CMD_SELECT_SHOW %>');"><%= params.getName("Detail") %></button>
				</td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateMonth()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPatternCode()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPatternName()[i]) %></td>
				<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblPatternAbbr()[i]) %></td>
				<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i]) %></td>
			</tr>
		</tbody>
<%
}
%>
	</table>
</div>
<%
if (!vo.getList().isEmpty()) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<%
}
%>
<div class="Button">
	<button type="button" id="" class="Name4Button" onclick="submitTransfer(event, null, null, null, '<%= WorkTypePatternCardAction.CMD_SHOW %>');"><%= params.getName("New","Insert") %></button>
</div>
<%
if (!vo.getList().isEmpty()) {
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
