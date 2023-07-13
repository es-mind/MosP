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
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.system.vo.NamingTypeListVo"
import = "jp.mosp.platform.system.action.NamingTypeListAction"
import = "jp.mosp.platform.system.action.NamingMasterAction"
import = "jp.mosp.platform.constant.PlatformConst"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
NamingTypeListVo vo = (NamingTypeListVo)params.getVo();
%>
<div class="FixList" id="divList">
		<table class="ListTable" id="list">
		<tr>
			<th class="ListSelectTh" id="thButton"></th>
			<th class="ListSelectTh" id="thNamingType"><%= PfNameUtility.namingType(params) %></th>
		</tr>
<%
for (int i = 0; i < vo.getAryLblNamingTypeName().length; i++) {
%>
		<tr>
			<td class="ListSelectTd" id="tdButton">
				<button type="button" class="Name2Button"
					onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblNamingTypeName()[i][0]) %>'), '<%= NamingTypeListAction.CMD_TRANSFER %>')"><%= PfNameUtility.select(params) %></button>
			</td>
			<td class="ListInputTd"  ><%= HtmlUtility.escapeHTML(vo.getAryLblNamingTypeName()[i][1]) %></td>		
		</tr>
<%
}
%>	
		</table>
</div>
