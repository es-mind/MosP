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

		<table class="InputTable" id="approvalCard_tblLateEarly">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("TardinessLeaveEarlyInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Tardiness","Time") %></td>
				<td class="InputTd" id="lblLateTime"><%= HtmlUtility.escapeHTML(vo.getLblLateTime()) %></td>
				<td class="TitleTd"><%= params.getName("Tardiness","Reason") %></td>
				<td class="InputTd" id="lblLateReason"><%= HtmlUtility.escapeHTML(vo.getLblLateReason()) %></td>
				<td class="TitleTd"><%= params.getName("Certificates") %></td>
				<td class="InputTd" id="lblLateCertificate"><%= HtmlUtility.escapeHTML(vo.getLblLateCertificate()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Comment") %></td>
				<td class="InputTd" id="lblLateComment" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblLateComment()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("LeaveEarly","Time") %></td>
				<td class="InputTd" id="lblLeaveEarlyTime"><%= HtmlUtility.escapeHTML(vo.getLblLeaveEarlyTime()) %></td>
				<td class="TitleTd"><%= params.getName("LeaveEarly","Reason") %></td>
				<td class="InputTd" id="lblLeaveEarlyReason"><%= HtmlUtility.escapeHTML(vo.getLblLeaveEarlyReason()) %></td>
				<td class="TitleTd"><%= params.getName("Certificates") %></td>
				<td class="InputTd" id="lblLeaveEarlyCertificate"><%= HtmlUtility.escapeHTML(vo.getLblLeaveEarlyCertificate()) %></td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Comment") %></td>
				<td class="InputTd" id="lblLeaveEarlyComment" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblLeaveEarlyComment()) %></td>
			</tr>
		</table>
