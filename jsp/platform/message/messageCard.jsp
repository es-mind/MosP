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
import = "jp.mosp.platform.constant.PlatformMailConst"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.message.action.MessageCardAction"
import = "jp.mosp.platform.message.action.MessageListAction"
import = "jp.mosp.platform.message.vo.MessageCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
MessageCardVo vo = (MessageCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<jsp:include page="<%= PlatformSystemConst.PATH_SYSTEM_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.publishStartDate(params) %></td>
			<td class="InputTd" id="tdEditActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />
				<label for="txtEditActivateYear"><%= PfNameUtility.year(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />
				<label for="txtEditActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />
				<label for="txtEditActivateDay"><%= PfNameUtility.day(params) %></label>&nbsp;
				<button type="button" class="Name2Button" id="btnStartDate" onclick="submitForm(event, 'tdEditActivateDate', null, '<%= MessageCardAction.CMD_SET_ACTIVATION_DATE %>')">
					<%= PfNameUtility.activeteDateButton(params, vo.getModeActivateDate()) %>
				</button>
			</td>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.publishEndDate(params) %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtEndYear" name="txtEndYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEndYear()) %>" />
				<label for="txtEndYear"><%= PfNameUtility.year(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEndMonth" name="txtEndMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEndMonth()) %>" />
				<label for="txtEndMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEndDay" name="txtEndDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEndDay()) %>" />
				<label for="txtEndDay"><%= PfNameUtility.day(params) %></label>
			</td>
			<td class="TitleTd"><label for="pltMessageType"><%= PfNameUtility.messageType(params) %></label></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltMessageType" name="pltMessageType">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_MESSAGE_TYPE, vo.getPltMessageType(), false) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= PfNameUtility.messageNo(params) %></td>
			<td class="InputTd" id="lblMessageNo"><%= HtmlUtility.escapeHTML(vo.getLblMessageNo()) %></td>
			<td class="TitleTd"><%= PfNameUtility.insertUser(params) %></td>
			<td class="InputTd" id="lblRegistUser"><%= HtmlUtility.escapeHTML(vo.getLblRegistUser()) %></td>
			<td class="TitleTd"><label for="pltImportance"><%= PfNameUtility.importance(params) %></label></td>
			<td class="InputTd">
				<select class="Name1PullDown" id="pltImportance" name="pltImportance">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_MESSAGE_IMPORTANCE, vo.getPltImportance(), false) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="pltInactivate"><%= PfNameUtility.inactivate(params) %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
			<td class="Blank" colspan="4"></td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="5">
				<span class="TitleTh"><%= PfNameUtility.applyRange(params) %></span>
				<span class="TableLabelSpan">
					<%= PfNameUtility.howToApplyAllRange(params) %>
				</span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRadio" rowspan="2">
				<input type="radio" class="RadioButton" name="radApplicationType" id="radMaster" value="<%= PlatformConst.APPLICATION_TYPE_MASTER %>" <%= HtmlUtility.getChecked(vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_MASTER)) %> />
			</td>
			<td class="TitleTd" id="tdMasterTitle1"><%= PfNameUtility.workPlaceName(params) %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltWorkPlace" name="pltWorkPlace">
					<%= HtmlUtility.getSelectOption(vo.getAryPltWorkPlace(), vo.getPltWorkPlace()) %>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%= PfNameUtility.employment(params) %><%= PfNameUtility.name(params) %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEmployment" name="pltEmployment">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEmployment(), vo.getPltEmployment()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdMasterTitle1"><%= PfNameUtility.sectionName(params) %></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltSection" name="pltSection">
					<%= HtmlUtility.getSelectOption(vo.getAryPltSection(), vo.getPltSection()) %>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%= PfNameUtility.positionName(params) %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltPosition" name="pltPosition">
					<%= HtmlUtility.getSelectOption(vo.getAryPltPosition(), vo.getPltPosition()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRadio" rowspan="2">
				<input type="radio" class="RadioButton" name="radApplicationType" id="radEmployeeCode" value="<%= PlatformConst.APPLICATION_TYPE_PERSON %>" <%= HtmlUtility.getChecked(vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_PERSON)) %> /></td>
			<td class="TitleTd"><label for="txtEmployeeCode"><%= PfNameUtility.personal(params)  %><%= PfNameUtility.parentheses(params, PfNameUtility.inputCsv(params)) %></label></td>
			<td class="InputTd" colspan="3">
			<input type="text" class="CodeCsvTextBox" id="txtEmployeeCode" name="txtEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEmployeeCode()) %>" /></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= PfNameUtility.publishedUserName(params) %></td>
			<td class="TitleInputTd" colspan="3" id="lblEmployeeName"><%= HtmlUtility.escapeHTML(vo.getLblEmployeeName()) %></td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh"><span class="TitleTh"><%= PfNameUtility.messageSetting(params) %></span><a></a></th>
		</tr>
		<tr>
			<td class="InputTd" id="tdOver"><input type="text" class="Name30RequiredTextBox" id="txtMessageTitle" name="txtMessageTitle" value="<%= HtmlUtility.escapeHTML(vo.getTxtMessageTitle()) %>" onFocus="hideFormGuide(this, '<%= PfNameUtility.inputTitle(params) %>');" onBlur="showFormGuide(this, '<%= PfNameUtility.inputTitle(params) %>');"/></td>
		</tr>
		<tr>
			<td class="InputTd" id="tdUnder"><textarea maxlength="200" onkeyup="return isMaxlength(this)" rows="3" cols="50" class="Name200TextArea" id="txtMessage" name="txtMessage"  onFocus="hideFormGuide(this, '<%= PfNameUtility.inputMessage(params) %>');" onBlur="showFormGuide(this, '<%= PfNameUtility.inputMessage(params) %>');"><%= HtmlUtility.escapeHTML(vo.getTxtMessage()) %></textarea></td>
		</tr>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name7Button" id="btnRegist" name="btnRegist" onclick="submitRegist(event, 'divEdit', checkExtra, '<%= MessageCardAction.CMD_REGIST %>')"><%= PfNameUtility.insert(params) %></button>
	<button type="button" class="Name7Button" id="btnDelete" name="btnDelete" onclick="submitDelete(event, null, null, '<%= MessageCardAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %></button>
<%
// メール機能が有効の場合
if(params.getApplicationPropertyBool(PlatformMailConst.APP_USE_MAIL)) {
%>
	<button type="button" class="Name7Button" id="btnSendMail" name="btnSendMail" onclick="submitRegist(event, null, null, '<%= MessageCardAction.CMD_SEND_MAIL %>')"><%= PfNameUtility.sendMail(params) %></button>
<%
}
%>
	<button type="button" class="Name7Button" id="btnMessageList" name="btnMessageList" onclick="submitTransfer(event, null, null, null, '<%= MessageListAction.CMD_RE_SEARCH %>')"><%= PfNameUtility.messageList(params) %></button>
</div>
