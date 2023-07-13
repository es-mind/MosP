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
language = "java"
pageEncoding = "UTF-8"
buffer = "256kb"
autoFlush = "false"
errorPage = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.workflow.action.RouteCardAction"
import = "jp.mosp.platform.workflow.action.RouteListAction"
import = "jp.mosp.platform.workflow.vo.RouteCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
RouteCardVo vo = (RouteCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable">
		<tr>
			<th class="EditTableTh" colspan="6">
				<jsp:include page="<%= PlatformSystemConst.PATH_SYSTEM_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.activateDate(params) %></td>
			<td class="InputTd" rowspan="2">
				<div id="divEditActivateDate">
					<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />
					<label for="txtEditActivateYear"><%= PfNameUtility.year(params) %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />
					<label for="txtEditActivateMonth"><%= PfNameUtility.month(params) %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />
					<label for="txtEditActivateDay"><%= PfNameUtility.day(params) %></label>
				</div>
				<div>
					<select class="Number2PullDown" id="pltRouteStage" name="pltRouteStage">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_APPROVAL_COUNT, vo.getPltRouteStage(), false) %>
					</select>&nbsp;
					<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'divSearchUnitCode', null, '<%= RouteCardAction.CMD_SET_ACTIVATION_DATE %>')"><%= PfNameUtility.activeteDateButton(params, vo.getModeActivateDate()) %></button>
				</div>
			</td>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><label for="txtRouteCode"><%= PfNameUtility.routeCode(params) %></label></td>
			<td class="InputTd"><input type="text" class="Code10RequiredTextBox" id="txtRouteCode" name="txtRouteCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtRouteCode()) %>"/></td>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><label for="txtRouteName"><%= PfNameUtility.routeName(params) %></label></td>
			<td class="InputTd"><input type="text" class="Name15RequiredTextBox" id="txtRouteName" name="txtRouteName" value="<%= HtmlUtility.escapeHTML(vo.getTxtRouteName()) %>"/></td>
		</tr>
		<tr>	
			<td class="TitleTd"><label for="pltRouteStage"><%= PfNameUtility.stageNumber(params) %></label></td>
			
			<td class="TitleTd"><span><label for="pltInactivate"><%= PfNameUtility.inactivate(params) %></label></span></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="3"><span class="TitleTh"><%= PfNameUtility.routeSetting(params) %></span><a></a>
			</th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 1) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage1" name="pltUnitStage1">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage1()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName1"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage1()) %></td>
		</tr>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 1) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 2) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage2" name="pltUnitStage2">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage2()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName2"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage2()) %></td>
		</tr>
<% 
}
%>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 2) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 3) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage3" name="pltUnitStage3">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage3()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName3"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage3()) %></td>
		</tr>
<% 
}
%>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 3) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 4) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage4" name="pltUnitStage4">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage4()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName4"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage4()) %></td>
		</tr>
<% 
}
%>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 4) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 5) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage5" name="pltUnitStage5">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage5()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName5"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage5()) %></td>
		</tr>
<% 
}
%>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 5) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 6) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage6" name="pltUnitStage6">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage6()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName6"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage6()) %></td>
		</tr>
<% 
}
%>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 6) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 7) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage7" name="pltUnitStage7">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage7()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName7"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage7()) %></td>
		</tr>
<% 
}
%>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 7) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 8) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage8" name="pltUnitStage8">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage8()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName8"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage8()) %></td>
		</tr>
<% 
}
%>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 8) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 9) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage9" name="pltUnitStage9">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage9()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName9"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage9()) %></td>
		</tr>
<% 
}
%>
<%
if (Integer.valueOf(vo.getPltRouteStage()) > 9) {
%>
		<tr>
			<td class="TitleTd" id="tdNumber"><%= PfNameUtility.stage(params, 10) %></td>
			<td class="InputTd" id="tdUnit">
				<select class="Name15PullDown" id="pltUnitStage10" name="pltUnitStage10">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUnitStage(), vo.getPltUnitStage10()) %>
				</select>
			</td>
			<td class="TitleInputTd" id="tdEmployeeName10"><%= HtmlUtility.escapeHTML(vo.getLblApproverStage10()) %></td>
		</tr>
<% 
}
%>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name5Button" id="btnRegist" name="btnRegist" onclick="submitRegist(event, 'divEdit', checkExtra, '<%= RouteCardAction.CMD_REGIST %>')"><%= PfNameUtility.insert(params) %></button>
<% if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) { %>
	<button type="button" class="Name5Button" id="btnDelete" name="btnDelete" onclick="submitDelete(event, null, null, '<%= RouteCardAction.CMD_DELETE %>')"><%= PfNameUtility.deleteHistory(params) %></button>
<% } %>
	<button type="button" class="Name5Button" id="btnRouteList" name="btnRouteList" onclick="submitTransfer(event, null, null, null, '<%= RouteListAction.CMD_RE_SEARCH %>')"><%= PfNameUtility.routeList(params) %></button>
</div>
