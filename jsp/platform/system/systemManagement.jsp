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
buffer       = "512kb"
autoFlush    = "false"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.system.vo.SystemManagementVo"
import = "jp.mosp.platform.system.action.SystemManagementAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SystemManagementVo vo = (SystemManagementVo)params.getVo();
%>
<div class="List">
	<table>
<%
// アプリケーション設定キー毎に処理
for (String appKey : vo.getAppKeys()) {
%>
		<tr>
			<td class="TitleTd"><label for="<%= appKey %>"><%= vo.getAppPropertyName(appKey) %></label></td>
			<td class="InputTd" >
				<%= HtmlUtility.getTextboxTag(vo.getAppPropertyType(appKey), appKey, vo.getAppProperty(appKey), false) %>
				<%= vo.getAppPropertyDescriptions(appKey) %>
			</td>
		</tr>
<%
}
%>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name3Button" onclick="submitRegist(event, '', null, '<%= SystemManagementAction.CMD_REGIST %>');"><%= PfNameUtility.update(params) %></button>
</div>
