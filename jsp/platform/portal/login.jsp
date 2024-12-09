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
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.portal.action.AuthAction"
import = "jp.mosp.platform.utils.IpAddressUtility"
import = "jp.mosp.platform.portal.vo.LoginVo"
import = "jp.mosp.platform.portal.vo.PasswordChangeVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
LoginVo vo = (LoginVo)params.getVo();
boolean needEncrypt = true;
if (params.getProperties().getAddonProperties().get("Ldap") != null && params.getProperties().getAddonProperties().get("Ldap").isAddonValid()) {
	needEncrypt = false;
}
String loginImagePath = params.getApplicationProperty(PlatformConst.APP_LOGIN_IMAGE_PATH);
%>
<%
if (loginImagePath != null && loginImagePath.isEmpty() == false) {
%>
<div style="margin: auto; width: 980px; height: 250px;">
	<img src="<%=loginImagePath%>" alt="MosPV4" />
</div>
<%
}
%>
<div>
	<table class="LoginTable">
		<tr>
			<td class="LoginTitle">
				<label for="txtUserId"><%= PfNameUtility.userId(params) %></label><%= PfNameUtility.colon(params) %>
			</td>
			<td class="LoginInput">
				<input type="text" class="LoginIdTextBox" id="txtUserId" name="txtUserId" value="<%= HtmlUtility.escapeHTML(vo.getTxtUserId()) %>" />
			</td>
		</tr>
		<tr>
			<td class="LoginTitle">
				<label for="txtPassWord"><%= PfNameUtility.password(params) %></label><%= PfNameUtility.colon(params) %>
			</td>
			<td class="LoginInput">
				<input type="password" class="LoginPassTextBox" id="txtPassWord" value="" />
			</td>
		</tr>
	</table>
</div>
<div class="Button" id="divLoginButton">
	<button type="submit" class="LoginButton" onclick="return login('<%= AuthAction.CMD_AUTHENTICATE %>')"><%= PfNameUtility.login(params) %></button>
<%
if (vo.getLoginExtraButtoun() != null && !vo.getLoginExtraButtoun().isEmpty()) {
%>
	<jsp:include page="<%=vo.getLoginExtraButtoun()%>" flush="false" />
	<input type="hidden" name="loginExtraButtoun" value="<%= vo.getLoginExtraButtoun() %>"/>
<%
}
%>
</div>
<%
if (params.getApplicationPropertyBool(PlatformConst.APP_LOGIN_INITIALIZE_PASSWORD)) {
%>
<div>
	<p class="PasswordChange">
		<a onclick="changePassword()">
			<%= PfNameUtility.inCaseForgotPassword(params) %>
		</a>
	</p>
</div>
<div id="divPasswordChange">
	<table class="PasswordChangeTable">
		<tr>
			<td class="PasswordChangeTitle">
				<label for="txtPassChangeUserId"><%= PfNameUtility.userId(params) %></label><%= PfNameUtility.colon(params) %>
			</td>
			<td class="PasswordChangeInput">
				<input type="text" class="PassChangeUserIdTextBox" id="txtPassChangeUserId" name="txtPassChangeUserId" value="<%= HtmlUtility.escapeHTML(vo.getTxtPassChangeUserId()) %>" />
			</td>
		</tr>
		<tr>
			<td class="PasswordChangeTitle">
				<label for="txtMailAddress"><%= PfNameUtility.mailAddress(params) %></label><%= PfNameUtility.colon(params) %>
			</td>
			<td class="PasswordChangeInput">
				<input type="text" class="PassChangeMailAddressTextBox" id="txtMailAddress" name="txtMailAddress" value="<%= HtmlUtility.escapeHTML(vo.getTxtMailAddress()) %>" />
			</td>
		</tr>
	</table>
</div>
<div class="Button" id="divPasswordChangeButton">
	<button type="button" class="LoginButton" onclick="submitRegist(event, null, checkPasswordChange, '<%= AuthAction.CMD_SEND_MAIL %>')"><%= NameUtility.getName(params, PasswordChangeVo.class.getCanonicalName()) %></button>
</div>
<%
}
%>
<div>
	<table>
		<tr>
			<td>
				<div class="CopyLabel">
					<%= PfNameUtility.copyRight(params) %>
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" id="hdnPass" name="txtPassWord" value="" />
	<input type="hidden" id="needEncrypt" value="<%= needEncrypt %>" />
</div>
