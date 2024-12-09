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
buffer       = "256kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.base.TotalTimeBaseVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TotalTimeBaseVo vo = (TotalTimeBaseVo)params.getVo();
%>
<div class="List">
	<table class="TotalInfoTable">
		<tr>
			<td class="TitleTd">
				<%=params.getName("Total") + params.getName("Month")%><%=PfNameUtility.colon(params)%><%=vo.getLblYearMonth()%>
				&nbsp;
				<%=params.getName("CutoffDate") + params.getName("Code")%><%=PfNameUtility.colon(params)%><%=vo.getCutoffCode()%>&nbsp;<%=HtmlUtility.escapeHTML(vo.getLblCutoffName())%>
				&nbsp;
				<%=params.getName("Cutoff")%><%=PfNameUtility.term(params)%><%=PfNameUtility.colon(params)%>&nbsp;<%= HtmlUtility.escapeHTML(vo.getLblCutoffFirstDate()) %>&nbsp;<%= NameUtility.wave(params) %>&nbsp;<%= HtmlUtility.escapeHTML(vo.getLblCutoffLastDate()) %>
			</td>
		</tr>
	</table>
</div>
