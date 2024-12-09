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
buffer       = "16kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.portal.action.PasswordChangeAction"
import = "jp.mosp.platform.portal.vo.PasswordChangeVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PasswordChangeVo vo = (PasswordChangeVo)params.getVo();
%>
<div class="List">
	<table class="PassWordTable">
		<tr>
			<td class="TitleTd">
				<%= HtmlUtility.getRequiredMark() %><label for="txtOldPassword"><%= PfNameUtility.currentPassrword(params) %></label>
			</td>
			<td class="InputTd">
				<input type="password" class="LoginPassTextBox" id="txtOldPassword" value="" />
			</td>
		<tr>
		<tr>
			<td class="TitleTd">
				<%= HtmlUtility.getRequiredMark() %><label for="txtNewPassword"><%= PfNameUtility.newPassrword(params) %></label>
			</td>
			<td class="InputTd">
				<input type="password" class="LoginPassTextBox" id="txtNewPassword" value="" />
			</td>
		<tr>
		<tr>
			<td class="TitleTd">
				<%= HtmlUtility.getRequiredMark() %><label for="txtConfirmPassword"><%= PfNameUtility.confirmPassrword(params) %></label>
			</td>
			<td class="InputTd">
				<input type="password" class="LoginPassTextBox" id="txtConfirmPassword" value="" />
			</td>
		<tr>
	</table>
</div>
<div class="Message">
	<table>
<%
for (String attention : vo.getAttentionList()) {
%>
		<tr>
			<td><span><%= attention %></span></td>
		</tr>
<%
}
%>
	</table>
</div>
<input type="hidden" id="hdnOldPassword"     name="hdnOldPassword"     value="" />
<input type="hidden" id="hdnNewPassword"     name="hdnNewPassword"     value="" />
<input type="hidden" id="hdnConfirmPassword" name="hdnConfirmPassword" value="" />
<div class="Button">
	<button type="button" class="Name4Button" id="btnPasswordChange" onclick="submitRegist(event, '', extraCheck, '<%= PasswordChangeAction.CMD_UPDATE %>')"><%= PfNameUtility.change(params) %></button>
</div>
