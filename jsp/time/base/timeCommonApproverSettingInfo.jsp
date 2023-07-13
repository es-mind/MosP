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
import = "jp.mosp.time.base.TimeVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TimeVo vo = (TimeVo)params.getVo();
%>
	<table class="UnderInputTable" id="tblApproverSelect">
<% for (int i = 0; i < vo.getAryPltLblApproverSetting().length; i += 2) { %>
		<tr>
			<td class="TitleTd">
				<%= HtmlUtility.escapeHTML(vo.getAryPltLblApproverSetting()[i]) %>
			</td>
			<td class="InputTd">
				<select class="Name15PullDown" id=<%= vo.getPltApproverSetting()[i] %> name=<%= vo.getPltApproverSetting()[i] %>>
					<%= HtmlUtility.getSelectOption(vo.getAryApproverInfo()[i], vo.getAryPltApproverSetting()[i]) %>
				</select>
			</td>
	<% if (i + 1 < vo.getAryPltLblApproverSetting().length) { %>
			<td class="TitleTd">
				<%= HtmlUtility.escapeHTML(vo.getAryPltLblApproverSetting()[i + 1]) %>
			</td>
			<td class="InputTd">
				<select class="Name15PullDown" id=<%= vo.getPltApproverSetting()[i + 1] %> name=<%= vo.getPltApproverSetting()[i + 1] %>>
					<%= HtmlUtility.getSelectOption(vo.getAryApproverInfo()[i + 1], vo.getAryPltApproverSetting()[i + 1]) %>
				</select>
			</td>
	<% } else { %>
			<td class="Blank"></td>
	<% } %>
		</tr>
<% } %>
	</table>
