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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.time.input.vo.AttendanceCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceCardVo vo = (AttendanceCardVo)params.getVo();
%>
	<table class="HeaderInputTable" id="attendanceCard_tblLateEarlyHeader">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%= params.getName("TardinessLeaveEarlyInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblLateEarly">
		<tr>
			<td class="TitleTd"><%= params.getName("Tardiness","Time") %></td>
			<td class="InputTd" id="tdLateEarlyTime"><%= HtmlUtility.escapeHTML(vo.getLblLateTime()) %></td>
			<td class="TitleTd"><%= params.getName("Tardiness","Reason") %></td>
			<td class="InputTd" id="tdLateEarlyReason">
				<%= HtmlUtility.getSelectTag("Name4PullDown", "pltLateReason", "pltLateReason", vo.getPltLateReason(),vo.getAryPltLateReason(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><%= params.getName("Tardiness","Certificates") %></td>
			<td class="InputTd" id="tdLateEarlyCertificates">
				<%= HtmlUtility.getSelectTag("Name3PullDown", "pltLateCertificate", "pltLateCertificate", vo.getPltLateCertificate(),vo.getAryPltLateCertificate(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Tardiness","Comment") %></td>
			<td class="InputTd" id="tdLateEarlyComment" colspan="5">
				<%= HtmlUtility.getTextboxTag("Name50TextBox", "txtLateComment", "txtLateComment", vo.getTxtLateComment(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("LeaveEarly","Time") %></td>
			<td class="InputTd"><%= vo.getLblLeaveEarlyTime() %></td>
			<td class="TitleTd"><%= params.getName("LeaveEarly","Reason") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getSelectTag("Name4PullDown", "pltLeaveEarlyReason", "pltLeaveEarlyReason", vo.getPltLeaveEarlyReason(),vo.getAryPltLeaveEarlyReason(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
			<td class="TitleTd"><%= params.getName("LeaveEarly","Certificates") %></td>
			<td class="InputTd">
				<%= HtmlUtility.getSelectTag("Name3PullDown", "pltLeaveEarlyCertificate", "pltLeaveEarlyCertificate", vo.getPltLeaveEarlyCertificate(),vo.getAryPltLateCertificate(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("LeaveEarly","Comment") %></td>
			<td class="InputTd" id="tdLateEarlyComment" colspan="5">
				<%= HtmlUtility.getTextboxTag("Name50TextBox", "txtLeaveEarlyComment", "txtLeaveEarlyComment", vo.getTxtLeaveEarlyComment(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>
			</td>
		</tr>
	</table>
