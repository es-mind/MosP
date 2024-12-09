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
import = "jp.mosp.time.utils.TimeHtmlUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.management.action.SubordinateFiscalReferenceAction"
import = "jp.mosp.time.management.vo.SubordinateFiscalReferenceVo"
import = "jp.mosp.time.base.AttendanceTotalVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SubordinateFiscalReferenceVo vo = (SubordinateFiscalReferenceVo)params.getVo();
%>
<%
if (vo.getRollArray() != null && vo.getRollArray().length != 0) {
%>
<div class="List">
	<table class="EmployeeCodeRollTable">
		<tr>
			<td class="RollTd" id="tdFormerCode">
				<%=HtmlUtility.escapeHTML(vo.getLblPrevEmployeeCode())%>
			</td>
			<td class="RollTd" id="tdFormerButton">
<%
if (vo.getLblPrevEmployeeCode() != null && !vo.getLblPrevEmployeeCode().isEmpty()) {
%>
				<a onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFER_SEARCH_MODE%>', '<%=TimeConst.SEARCH_BACK%>'), '<%=SubordinateFiscalReferenceAction.CMD_SEARCH%>');">
					<%=params.getName("UpperTriangular")%>
				</a>
<%
} else {
%>
					<span Class="FontGray"><%=params.getName("UpperTriangular")%></span>
<%
}
%>
			</td>
			<td class="RollTd">
				<%=PfNameUtility.employeeCode(params)%>
			</td>
			<td class="RollTd" id="tdNextButton">
<% if (vo.getLblNextEmployeeCode() != null && !vo.getLblNextEmployeeCode().isEmpty()) { %>
				<a onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFER_SEARCH_MODE %>', '<%= TimeConst.SEARCH_NEXT %>'), '<%= SubordinateFiscalReferenceAction.CMD_SEARCH %>');">
					<%= params.getName("LowerTriangular") %>
				</a>
<% } else { %>
					<span Class="FontGray"><%= params.getName("LowerTriangular") %></span>
<% } %>
			</td>
			<td class="RollTd" id="tdNextCode">
				<%= HtmlUtility.escapeHTML(vo.getLblNextEmployeeCode()) %>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
</div>

<div class="List">
<%
 for(int i=0; i<vo.getVoList().size(); i++){
%>

<%= TimeHtmlUtility.getAttendanceTotalTable(vo.getVoList().get(i)) %>

<%
 }
%>
</div>
