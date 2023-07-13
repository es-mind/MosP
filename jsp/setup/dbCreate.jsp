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
import = "jp.mosp.setup.vo.DbCreateVo"
import = "jp.mosp.setup.action.DbCreateAction"
import = "jp.mosp.setup.action.FirstUserAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
DbCreateVo vo = (DbCreateVo)params.getVo();

%>
<br>
<br>
<br>
<center><%= params.getName("DbCreate") %></center>
<br>
<br><center><%= params.getName("DbName") %><%= params.getName("Slash") %><%= params.getName("RoleName") %><%= params.getName("Slash") %><%= params.getName("RolePass") %><%= params.getName("InputSomething") %></center>
<br><center><%= params.getName("CreateDbMessage") %></center>
<br><center><%= params.getName("CreateDbMessage2") %></center>
<br>
<center>
	<table class="SetupTable" >
		<tr>
				<td class="SetupTitleTd">
				<font class="ServerFont"><label for="txtDbName"><%= params.getName("DbName") %></label></font>
			</td>
				<td class="SetupInputTd">
				<input type="text" class="Code50RequiredTextBox" id="txtDbName" name="txtDbName" value="<%= HtmlUtility.escapeHTML(vo.getTxtDbName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="SetupTitleTd">
				<font class="ServerFont"><label for="txtRoleName"><%= params.getName("RoleName") %></label></font>
			</td>
			<td class="SetupInputTd">
				<input type="text" class="Code50RequiredTextBox" id="txtRoleName" name="txtRoleName" value = "<%= HtmlUtility.escapeHTML(vo.getTxtRoleName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="SetupTitleTd">
				<font class="ServerFont"><label for="txtRolePw"><%= params.getName("RolePass") %></label></font>
			</td>
			<td class="SetupInputTd">
				<input type="text" class="LoginPassTextBox" id="txtRolePw" name="txtRolePw" value = "<%= HtmlUtility.escapeHTML(vo.getTxtRolePw()) %>" />
			</td>
		</tr>	
	</table>
<br>
<br><center><%= params.getName("CreateWait") %></center>
<br>
<button type="button" class="Name8Button"  onclick="submitRegist(event,  '', checkExtra, '<%=DbCreateAction.CMD_CHECK %>')"> <%= params.getName("DbCreateButton") %></button>
</center>
