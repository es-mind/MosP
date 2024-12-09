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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.file.action.ExportCardAction"
import = "jp.mosp.platform.file.action.HumanExportListAction"
import = "jp.mosp.platform.file.base.ExportListAction"
import = "jp.mosp.platform.file.vo.HumanExportListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanExportListVo vo = (HumanExportListVo)params.getVo();
%>
<jsp:include page="<%= ExportListAction.PATH_EXPORT_LIST_JSP %>" flush="false" />
<%
if (vo.getAryLblInactivate().length > 0) {
%>
<div class="List">
	<table class="LeftListTable" id="list">
		<tr>
			<td class="TitleTd"><%= PfNameUtility.activateDate(params) %></td>
			<td class="InputTd" id="tdActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtActivateYear" name="txtActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear()) %>" />
				<label for="txtActivateYear"><%= PfNameUtility.year(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth()) %>" />
				<label for="txtActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateDay" name="txtActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay()) %>" />
				<label for="txtActivateDay"><%= PfNameUtility.day(params) %></label>&nbsp;
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'tdActivateDate', null, '<%= HumanExportListAction.CMD_SET_ACTIVATION_DATE %>')">
					<%= PfNameUtility.activeteDateButton(params, vo.getModeActivateDate()) %>
				</button>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= PfNameUtility.workPlace(params) %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltWorkPlace" name="pltWorkPlace">
					<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlace(), vo.getPltWorkPlace()) %>
				</select>
			</td>
			<td class="TitleTd"><%= PfNameUtility.employmentContract(params) %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEmployment" name="pltEmployment">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltEmployment()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= PfNameUtility.section(params) %></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltSection" name="pltSection">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSection(), vo.getPltSection()) %>
				</select>
			</td>
			<td class="TitleTd"><%= PfNameUtility.position(params) %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltPosition" name="pltPosition">
					<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltPosition()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdFooter" colspan="4">
				<span class="FloatLeftSpan"><%= PfNameUtility.selectExportBeforeExecute(params) %></span>
				<span class="TableLabelSpan">
					<button type="button" class="Name2Button" id="btnExecute" onclick="submitFile(event, null, null, '<%= HumanExportListAction.CMD_EXPORT %>');"><%= PfNameUtility.execution(params) %></button>
				</span>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= HtmlUtility.escapeHTML(vo.getTableTypeCodeKey()) %>', '<%= PlatformConst.PRM_TRANSFERRED_COMMAND %>', '<%= HtmlUtility.escapeHTML(vo.getReShowCommand()) %>'), '<%= ExportCardAction.CMD_SHOW %>');"><%= PfNameUtility.newInsert(params) %></button>
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
