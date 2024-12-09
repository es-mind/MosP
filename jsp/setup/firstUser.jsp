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
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.setup.vo.FirstUserVo"
import = "jp.mosp.setup.action.FirstUserAction"
import = "jp.mosp.setup.action.SetupFinishAction"
import = "jp.mosp.platform.utils.PfNameUtility"

%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
FirstUserVo vo = (FirstUserVo)params.getVo();
%>

<br>
<br>
<center><%=params.getName("FirstUserCard")%></center>
<br>
<br><center><%=params.getName("CardMessage")%></center>
<br>
<center>
<table class="SetupTable">
	<tr>
		<td class="SetupTitleTd">
			<font class="ServerFont"><label for="txtEmployeeCode"><%=PfNameUtility.employeeCode(params)%></label></font>
		</td>
		<td class="SetupInputTd">
			<input type="text" class="Code10RequiredTextBox" id="txtEmployeeCode" name="txtEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEmployeeCode()) %>" />
		</td>
	</tr>
		
	<tr>
		<td class="SetupTitleTd">
			<font class="ServerFont"><%= params.getName("ActivateDate") %></font>
		</td>
		<td class="SetupInputTd">
			<input type="text" class="Number4RequiredTextBox" id="txtActivateYear" name="txtActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear()) %>" />&nbsp;<label for="txtActivateYear"><%= params.getName("Year") %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth()) %>" />&nbsp;<label for="txtActivateMonth"><%= params.getName("Month") %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtActivateDay" name="txtActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay()) %>" />&nbsp;<label for="txtActivateDay"><%= params.getName("Day") %></label>
		</td>
	</tr>
	<tr>
		<td class="SetupTitleTd">
			<font class="ServerFont"><label for="txtLastName"><%= params.getName("LastName") %></label><%= params.getName("Slash") %><label for="txtFirstName"><%= params.getName("FirstName") %></label></font>
		</td>
		<td class="SetupInputTd">
			<input type="text" class="Name10RequiredTextBox" id="txtLastName" name="txtLastName" value="<%= HtmlUtility.escapeHTML(vo.getTxtLastName()) %>"/>
			<input type="text" class="Name10RequiredTextBox" id="txtFirstName" name="txtFirstName" value="<%= HtmlUtility.escapeHTML(vo.getTxtFirstName()) %>" />
		</td>
	</tr>
	<tr>
		<td class="SetupTitleTd">
			<font class=ServerFont><label for="txtLastKana"><%= params.getName("LastName") + params.getName("FrontParentheses") + params.getName("Kana") + params.getName("BackParentheses") %></label><%= params.getName("Slash") %><label for="txtFirstKana"><%= params.getName("FirstName") + params.getName("FrontParentheses") + params.getName("Kana") + params.getName("BackParentheses") %></label></font></td>
		<td class=SetupInputTd>
			<input type="text" class="Kana10RequiredTextBox" id="txtLastKana" name="txtLastKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtLastKana()) %>" />
			<input type="text" class="Kana10RequiredTextBox" id="txtFirstKana" name="txtFirstKana" value="<%= HtmlUtility.escapeHTML(vo.getTxtFirstKana()) %>" />
		</td>
	</tr>
	<tr>
		<td class="SetupTitleTd">
			<font class=ServerFont><span><label for="txtUserId"><%= params.getName("MospLoginUser") %></label></span></font>
		</td>
		<td class=SetupInputTd>
			<input type="text" class="UserId50RequiredTextBox" id="txtUserId" name="txtUserId" value="<%= HtmlUtility.escapeHTML(vo.getTxtUserId()) %>" />
		</td>
	</tr>
	<tr>
		<td class="SetupTitleTd">
			<font class="ServerFont"><%= params.getName("Joined") %><%= params.getName("Day") %></font>
		</td>
		<td class="SetupInputTd">
			<input type="text" class="Number4RequiredTextBox" id="txtEntranceYear" name="txtEntranceYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceYear()) %>" />&nbsp;<label for="txtEntranceYear"><%= params.getName("Year") %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtEntranceMonth" name="txtEntranceMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceMonth()) %>" />&nbsp;<label for="txtEntranceMonth"><%= params.getName("Month") %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtEntranceDay" name="txtEntranceDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceDay()) %>" />&nbsp;<label for="txtEntranceDay"><%= params.getName("Day") %></label>
		</td>
	</tr>
</table>
<br>
<br>
<div class="Button">
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, '', null, '<%=FirstUserAction.CMD_REGIST%>')"><%= params.getName("Insert") %></button>
</div>

</center>
