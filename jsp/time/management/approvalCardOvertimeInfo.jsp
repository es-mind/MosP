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
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.management.action.ApprovalCardAction"
import = "jp.mosp.time.management.vo.ApprovalCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApprovalCardVo vo = (ApprovalCardVo)params.getVo();
%>
	<table class="InputTable" id="approvalCard_tblOverTime">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("OvertimeWorkInformation") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdOvertimeIn"><%= params.getName("OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOverTimeIn"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeIn()) %></td>
			<td class="TitleTd" id="tdOvertimeOut"><%= params.getName("Legal","Outside","OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOverTimeOut"><%= HtmlUtility.escapeHTML(vo.getLblOverTimeOut()) %></td>
			<td class="TitleTd"><%= params.getName("Midnight","Time") %></td>
			<td class="InputTd" id="lblLateNightTime"><%= HtmlUtility.escapeHTML(vo.getLblLateNightTime()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Prescribed","DayOff","Work","Time") %></td>
			<td class="InputTd" id="lblSpecificWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblSpecificWorkTimeIn()) %></td>
			<td class="TitleTd"><%= params.getName("Legal","DayOff","Work","Time") %></td>
			<td class="InputTd" id="lblLegalWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblLegalWorkTime()) %></td>
			<td class="TitleTd"><%= params.getName("Reduced","Target","Time") %></td>
			<td class="InputTd" id="lblDecreaseTime"><%= HtmlUtility.escapeHTML(vo.getLblDecreaseTime()) %></td>
		</tr>
	</table>
