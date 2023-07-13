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
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.human.action.BasicCardAction"
import = "jp.mosp.platform.human.action.BasicListAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.vo.BasicCardVo"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
BasicCardVo vo = (BasicCardVo)params.getVo();
%>
<jsp:include page="<%= params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP) %>" flush="false" />
<div class="List">
	<table class="ListTable" id="tblCard">
		<tr>
			<td class="TitleTd">
				<%= HtmlUtility.getRequiredMark() %><span><%= PfNameUtility.activateDate(params) %></span>
			</td>
			<td class="InputTd" id="tdActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtActivateYear"  name="txtActivateYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear())  %>" />&nbsp;<label for="txtActivateYear" ><%= PfNameUtility.year(params)  %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth()) %>" />&nbsp;<label for="txtActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateDay"   name="txtActivateDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay())   %>" />&nbsp;<label for="txtActivateDay"  ><%= PfNameUtility.day(params)   %></label>
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'tdActivateDate', null, '<%= BasicCardAction.CMD_SET_ACTIVATION_DATE %>')"><%= PfNameUtility.activeteDateButton(params, vo.getModeActivateDate()) %></button>
			</td>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span><label for="txtEmployeeCode"><%= PfNameUtility.employeeCode(params) %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Code10RequiredTextBox" id="txtEmployeeCode" name="txtEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEmployeeCode()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span><label for="txtLastName"><%= PfNameUtility.lastName(params) %></label><%= PfNameUtility.slash(params) %><label for="txtFirstName"><%= PfNameUtility.firstName(params) %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name10RequiredTextBox" id="txtLastName" name="txtLastName" value="<%= HtmlUtility.escapeHTML(vo.getTxtLastName()) %>" />
				<input type="text" class="Name10TextBox" id="txtFirstName" name="txtFirstName" value="<%= HtmlUtility.escapeHTML(vo.getTxtFirstName()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtLastKana"><%= PfNameUtility.lastNameKana(params) %></label><%= PfNameUtility.slash(params) %><label for="txtFirstKana"><%= PfNameUtility.firstNameKana(params) %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Kana10TextBox" id="txtLastKana" name="txtLastKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtLastKana()) %>" />
				<input type="text" class="Kana10TextBox" id="txtFirstKana" name="txtFirstKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtFirstKana()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="pltSectionName"><%= PfNameUtility.section(params) %></label></span></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltSectionName" name="pltSectionName">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSection(), vo.getPltSectionName()) %>
				</select>
			</td>
			<td class="TitleTd"><span><label for="pltPositionName"><%= PfNameUtility.position(params) %></label></span></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltPositionName" name="pltPositionName">
					<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltPositionName()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="pltEmploymentName"><%= PfNameUtility.employmentContract(params) %></label></span></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEmploymentName" name="pltEmploymentName">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltEmploymentName()) %>
				</select>
			</td>
			<td class="TitleTd"><span><label for="pltWorkPlaceName"><%= PfNameUtility.workPlace(params) %></label></span></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltWorkPlaceName" name="pltWorkPlaceName">
					<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlaceName(), vo.getPltWorkPlaceName()) %>
				</select>
			</td>
		</tr>
<%
if (vo.getNeedPost()) {
%>		
		<tr>
			<td class="TitleTd"><span><label for="pltPostName"><%= PfNameUtility.post(params) %></label></span></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltPostName" name="pltPostName">
					<%= HtmlUtility.getSelectOption(vo.getAryPltPostName(), vo.getPltPostName()) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
}
%>		
	</table>
</div>
<div class="Button">
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
%>
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'tblCard', null, '<%= BasicCardAction.CMD_ADD %>')"><%= PfNameUtility.insert(params) %></button>
<%
} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'tblCard', null, '<%= BasicCardAction.CMD_UPDATE %>')"><%= PfNameUtility.update(params) %></button>
<%
}
%>
	<button type="button" id="btnHumenInfo" class="Name4Button"
		onclick="submitTransfer(event, 'tblCard', null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%=HumanInfoAction.class.getName() %>'), '<%= BasicCardAction.CMD_TRANSFER %>');">
		<%= PfNameUtility.dataList(params) %>
	</button>
	<button type="button" id="btnBasicList" class="Name4Button"
		onclick="submitTransfer(event, 'tblCard', null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%=BasicListAction.class.getName() %>'), '<%= BasicCardAction.CMD_TRANSFER%>');">
		<%= PfNameUtility.historyList(params) %>
	</button>
</div>
	
