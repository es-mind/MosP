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
import = "jp.mosp.setup.vo.SetupFinishVo"
import = "jp.mosp.setup.action.SetupFinishAction"
import = "jp.mosp.platform.portal.action.IndexAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SetupFinishVo vo = (SetupFinishVo)params.getVo();
%>
<br>
<br>
<br>
<center><%= params.getName("SetupFinish") %></center>
<br>
<br><center><%= params.getName("SetupMessage") %></center>
<br>
<center>
<table class="SetupTable">
	<tr>
		<td class="SetupTitleTd">
		<font  class="ServerFont"><%= params.getName("DbName") %></font></td>
		<td class="SetupInputTd">
		<%= HtmlUtility.escapeHTML(vo.getLblDataBase()) %>
		</td>
	</tr>
	<tr>
		<td class="SetupTitleTd">
		<font  class="ServerFont"><%= params.getName("RoleName") %></font></td>
		<td class="SetupInputTd">
		<%= HtmlUtility.escapeHTML(vo.getLblRoleName()) %>
		</td>
	</tr>
	<tr>
		<td class="SetupTitleTd">
		<font  class="ServerFont"><%= params.getName("RolePass") %></font></td>
		<td class="SetupInputTd">
		<%= HtmlUtility.escapeHTML(vo.getLblRolePass()) %>
		</td>
	</tr>
	<tr>
		<td class="SetupTitleTd">
		<font  class="ServerFont"><%= params.getName("MospLoginUser") %></font></td>
		<td class="SetupInputTd">
		<%= HtmlUtility.escapeHTML(vo.getLblMospUser()) %>
		</td>
	</tr>
	<tr>
		<td class="SetupTitleTd">
		<font  class="ServerFont"><%= params.getName("MospLoginPass") %></font></td>
		<td class="SetupInputTd">
		<%= HtmlUtility.escapeHTML(vo.getLblMospPass()) %>
		</td>
	</tr>
	
</table>
<br>
<br>
<button type="button" class="Name8Button"  onclick="submitRegist(event,  '', null, '<%=IndexAction.CMD_SHOW %>')"> <%= params.getName("GoMosp") %></button>
</center>
