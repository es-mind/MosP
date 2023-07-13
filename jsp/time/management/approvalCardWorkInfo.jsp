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


	<div id="divAttendance">
		<table class="InputTable" id="approvalCard_tblCorrection">
			<tr>
				<th class="ListTableTh" colspan="2">
					<span class="TitleTh"><%= params.getName("CorrectionInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("CorrectionSummary") %></td>
				<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblCorrectionHistory()) %></td>
			</tr>
		</table>
		<table class="InputTable" id="approvalCard_tblAttendance">
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh"><%= params.getName("AttendanceInformation") %></span>
				</th>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Work","Form") %></td>
				<td class="InputTd" id="lblWorkType">
					<%= HtmlUtility.escapeHTML(vo.getLblWorkType()) %>
				</td>
				<td class="TitleTd"><%= params.getName("StartWork","Moment") %></td>
				<td class="InputTd" id="lblStartTime">
					<%= HtmlUtility.escapeHTML(vo.getLblStartTime()) %>
				</td>
				<td class="TitleTd"><%= params.getName("EndWork","Moment") %></td>
				<td class="InputTd" id="lblEndTime">
					<%= HtmlUtility.escapeHTML(vo.getLblEndTime()) %>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Work","Time") %></td>
				<td class="InputTd" id="lblWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTime()) %></td>
				<td class="TitleTd"><%= params.getName("DirectStart","Slash","DirectEnd") %></td>
				<td class="InputTd" id="lblApprovalState"><%= HtmlUtility.escapeHTML(vo.getLblDirectWorkManage()) %></td>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)){
%>
				<td class="TitleTd" id="tdUnpaidShortTime" ><%= params.getName("UnpaidShortTime") %></td>
				<td class="InputTd" id="tdLblUnpaidShortTime" ><%= HtmlUtility.escapeHTML(vo.getLblUnpaidShortTime()) %></td>
<%
} else {
%>
				<td class="Blank" colspan="2"></td>
<%
}
%>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("WorkManage","Comment") %></td>
				<td class="InputTd" id="tdAttendanceComment" colspan="5"><%= HtmlUtility.escapeHTML(vo.getLblTimeComment()) %></td>
			</tr>
		</table>
