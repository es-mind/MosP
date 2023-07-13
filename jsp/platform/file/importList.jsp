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
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.comparator.file.ImportMasterHeaderComparator"
import = "jp.mosp.platform.comparator.file.ImportMasterImportCodeComparator"
import = "jp.mosp.platform.comparator.file.ImportMasterImportNameComparator"
import = "jp.mosp.platform.comparator.file.ImportMasterImportTableComparator"
import = "jp.mosp.platform.comparator.file.ImportMasterTypeComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.file.action.ImportCardAction"
import = "jp.mosp.platform.file.base.ImportListAction"
import = "jp.mosp.platform.file.vo.ImportListVo"
import = "jp.mosp.platform.utils.PlatformUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ImportListVo vo = (ImportListVo)params.getVo();
%>
<div class="List">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= PfNameUtility.search(params) %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= PfNameUtility.fileType(params) %></td>
			<td class="InputTd" id="tdTableInput">
				<select class="Name15PullDown" id="pltSearchTable" name="pltSearchTable">
					<%= HtmlUtility.getSelectOption(vo.getAryPltTableType(), vo.getPltSearchTable()) %>
				</select>
			</td>
			<td class="TitleTd"><%= PfNameUtility.dataClass(params) %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchType"  name="pltSearchType" disabled>
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_FILE_TYPE, vo.getPltSearchType(), true) %>
				</select>
			</td>
			<td class="TitleTd"><%= PfNameUtility.importCode(params) %></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchCode" name="txtSearchCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchCode()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= PfNameUtility.importName(params) %></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchName" name="txtSearchName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchName()) %>" />
			</td>
			<td class="TitleTd"><%= PfNameUtility.withOrWithoutHeader(params) %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchHeader" name="pltSearchHeader">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_HEADER_TYPE, vo.getPltSearchHeader(), true) %>
				</select>
			</td>
			<td class="TitleTd"><%= PfNameUtility.inactivate(params) %></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="divSearch">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= HtmlUtility.escapeHTML(vo.getSearchCommand()) %>')"><%= PfNameUtility.search(params) %></button>
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
				<th class="ListSortTh" id="thCode"       onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ImportMasterImportCodeComparator .class.getName() %>'), '<%= vo.getSortCommand() %>')"><%= PfNameUtility.code(params)           %><%= PlatformUtility.getSortMark(ImportMasterImportCodeComparator.class.getName(), params)  %></th>
				<th class="ListSortTh" id="thName"       onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ImportMasterImportNameComparator .class.getName() %>'), '<%= vo.getSortCommand() %>')"><%= PfNameUtility.name(params)           %><%= PlatformUtility.getSortMark(ImportMasterImportNameComparator.class.getName(), params)  %></th>
				<th class="ListSortTh" id="thTable"      onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ImportMasterImportTableComparator.class.getName() %>'), '<%= vo.getSortCommand() %>')"><%= PfNameUtility.fileType(params)       %><%= PlatformUtility.getSortMark(ImportMasterImportTableComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thType"       onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ImportMasterTypeComparator       .class.getName() %>'), '<%= vo.getSortCommand() %>')"><%= PfNameUtility.dataClass(params)      %><%= PlatformUtility.getSortMark(ImportMasterTypeComparator.class.getName(), params)        %></th>
				<th class="ListSortTh" id="thHeader"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ImportMasterHeaderComparator     .class.getName() %>'), '<%= vo.getSortCommand() %>')"><%= PfNameUtility.header(params)         %><%= PlatformUtility.getSortMark(ImportMasterHeaderComparator.class.getName(), params)      %></th>
				<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator             .class.getName() %>'), '<%= vo.getSortCommand() %>')"><%= PfNameUtility.inactivateAbbr(params) %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params)              %></th>
				<th class="ListSelectTh" id="thSelect"><%= PfNameUtility.select(params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblInactivate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button"
							onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_CODE%>', '<%= vo.getAryLblCode(i) %>', '<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= vo.getTableTypeCodeKey() %>', '<%= PlatformConst.PRM_TRANSFERRED_COMMAND %>', '<%= vo.getReShowCommand() %>'), '<%= ImportCardAction.CMD_SELECT_SHOW %>');">
						<%= PfNameUtility.detail(params) %>
					</button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblCode      (i)) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblName      (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblTable     (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblType      (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblHeader    (i)) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate(i)) %></td>
				<td class="ListSelectTd">
<%
// 無効の場合
if(vo.getAryLblInactivate(i).equals(PfNameUtility.effective(params))){
%>
					<input type="radio" class="RadioButton" name="radSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryLblCode(i)) %>" <%= HtmlUtility.getChecked(vo.getRadSelect().equals(vo.getAryLblCode(i))) %> onclick="checkRadioSelect();">
<%
}
%>
				</td>
			</tr>
<%
}
if (vo.getAryLblInactivate().length > 0) {
%>
			<tr>
				<td class="TitleTd" id="tdFooter" colspan="8">
					<span class="FloatLeftSpan">
						<label for="<%= ImportListAction.PRM_FIL_IMPORT %>"><%= PfNameUtility.targetFile(params) %></label>&nbsp;:&nbsp;<input type="file" class="FileTextBox" id="<%= ImportListAction.PRM_FIL_IMPORT %>" name="<%= ImportListAction.PRM_FIL_IMPORT %>" />
					</span>
					<span class="TableButtonSpan">
						<button type="button" id="btnTemplate" name="btnTemplate" class="Name6Button" onclick="submitFile(event, null, null, '<%= HtmlUtility.escapeHTML(vo.getTemplateOutputCommand()) %>');"><%= PfNameUtility.template(params) %></button>
						<button type="button" id="btnExecute" name="btnExecute" class="Name2Button" onclick="submitFormMulti(event, null, checkExtra, '<%= HtmlUtility.escapeHTML(vo.getExecuteCommand()) %>');"><%= PfNameUtility.execution(params) %></button>
					</span>
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null,  new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= HtmlUtility.escapeHTML(vo.getTableTypeCodeKey()) %>', '<%= PlatformConst.PRM_TRANSFERRED_COMMAND %>', '<%= HtmlUtility.escapeHTML(vo.getReShowCommand()) %>'), '<%= ImportCardAction.CMD_SHOW %>');">
		<%= PfNameUtility.newInsert(params) %>
	</button>
</div>
<%
if (vo.getAryLblInactivate().length == 0) {
	return;
}
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= PfNameUtility.topOfPage(params) %></a>
</div>
