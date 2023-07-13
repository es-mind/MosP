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
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.vo.AttendanceCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceCardVo vo = (AttendanceCardVo)params.getVo();
%>
	<table class="HeaderInputTable" id="attendanceCard_tblRestHeader">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("RestTimeInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblRest">
		<tr>
			<td class="TitleTd" id="tdRestTitle"><%= params.getName("RestTime","Time") %></td>
			<td class="InputTd" id="tdRestInput"><%= HtmlUtility.escapeHTML(vo.getLblRestTime()) %></td>
			<td class="TitleTd" id="tdRestTitle1"><label for="txtRestStartHour1"><%= params.getName("Rest1") %></label></td>
			<td class="InputTd" id="tdRestTitle1Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour1", "txtRestStartHour1", vo.getTxtRestStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute1", "txtRestStartMinute1", vo.getTxtRestStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour1", "txtRestEndHour1", vo.getTxtRestEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute1", "txtRestEndMinute1", vo.getTxtRestEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdRestTitle2"><label for="txtRestStartHour2"><%= params.getName("Rest2") %></label></td>
			<td class="InputTd" id="tdRestTitle2Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour2", "txtRestStartHour2", vo.getTxtRestStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute2", "txtRestStartMinute2", vo.getTxtRestStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour2", "txtRestEndHour2", vo.getTxtRestEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute2", "txtRestEndMinute2", vo.getTxtRestEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Midnight","RestTime","Time") %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblNightRestTime()) %></td>
			<td class="TitleTd" id="tdRestTitle3"><label for="txtRestStartHour3"><%= params.getName("Rest3") %></label></td>
			<td class="InputTd" id="tdRestTitle3Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour3", "txtRestStartHour3", vo.getTxtRestStartHour3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute3", "txtRestStartMinute3", vo.getTxtRestStartMinute3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour3", "txtRestEndHour3", vo.getTxtRestEndHour3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute3", "txtRestEndMinute3", vo.getTxtRestEndMinute3(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdRestTitle4"><label for="txtRestStartHour4"><%= params.getName("Rest4") %></label></td>
			<td class="InputTd" id="tdRestTitle4Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour4", "txtRestStartHour4", vo.getTxtRestStartHour4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute4", "txtRestStartMinute4", vo.getTxtRestStartMinute4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour4", "txtRestEndHour4", vo.getTxtRestEndHour4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute4", "txtRestEndMinute4", vo.getTxtRestEndMinute4(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="Blank" colspan="2" id="blankRest"></td>
			<td class="TitleTd" id="tdRestTitle5"><label for="txtRestStartHour5"><%= params.getName("Rest5") %></label></td>
			<td class="InputTd" id="tdRestTitle5Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour5", "txtRestStartHour5", vo.getTxtRestStartHour5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute5", "txtRestStartMinute5", vo.getTxtRestStartMinute5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour5", "txtRestEndHour5", vo.getTxtRestEndHour5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute5", "txtRestEndMinute5", vo.getTxtRestEndMinute5(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdRestTitle6"><label for="txtRestStartHour6"><%= params.getName("Rest6") %></label></td>
			<td class="InputTd" id="tdRestTitle6Time">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartHour6", "txtRestStartHour6", vo.getTxtRestStartHour6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestStartMinute6", "txtRestStartMinute6", vo.getTxtRestStartMinute6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndHour6", "txtRestEndHour6", vo.getTxtRestEndHour6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtRestEndMinute6", "txtRestEndMinute6", vo.getTxtRestEndMinute6(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdPublicGoingOutTitle"><%= params.getName("PublicGoingOut","Time") %></td>
			<td class="InputTd" id="tdPublicGoingOutInput"><%= vo.getLblPublicTime() %></td>
			<td class="TitleTd" id="tdPublicStartHour1Title" ><label for="txtPublicStartHour1"><%= params.getName("PublicGoingOut1") %></label></td>
			<td class="InputTd" id="tdPublicStartHour1Input">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartHour1", "txtPublicStartHour1", vo.getTxtPublicStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartMinute1", "txtPublicStartMinute1", vo.getTxtPublicStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndHour1", "txtPublicEndHour1", vo.getTxtPublicEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndMinute1", "txtPublicEndMinute1", vo.getTxtPublicEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd" id="tdPublicStartHour2Title" ><label for="txtPublicStartHour2"><%= params.getName("PublicGoingOut2") %></label></td>
			<td class="InputTd" id="tdPublicStartHour2Input">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartHour2", "txtPublicStartHour2", vo.getTxtPublicStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicStartMinute2", "txtPublicStartMinute2", vo.getTxtPublicStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndHour2", "txtPublicEndHour2", vo.getTxtPublicEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPublicEndMinute2", "txtPublicEndMinute2", vo.getTxtPublicEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdPrivateGoingOutTitle" ><%= params.getName("PrivateGoingOut","Time") %></td>
			<td class="InputTd" id="tdPrivateGoingOutInput" ><%= vo.getLblPrivateTime() %></td>
			<td class="TitleTd"><label for="txtPrivateStartHour1"><%= params.getName("PrivateGoingOut1") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateStartHour1", "txtPrivateStartHour1", vo.getTxtPrivateStartHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateStartMinute1", "txtPrivateStartMinute1", vo.getTxtPrivateStartMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateEndHour1", "txtPrivateEndHour1", vo.getTxtPrivateEndHour1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateEndMinute1", "txtPrivateEndMinute1", vo.getTxtPrivateEndMinute1(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd"><label for="txtPrivateStartHour2"><%= params.getName("PrivateGoingOut2") %></label></td>
			<td class="InputTd">
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateStartHour2", "txtPrivateStartHour2", vo.getTxtPrivateStartHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateStartMinute2", "txtPrivateStartMinute2", vo.getTxtPrivateStartMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateEndHour2", "txtPrivateEndHour2", vo.getTxtPrivateEndHour2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Hour") %>
				<%= HtmlUtility.getTextboxTag("Number2TextBox", "txtPrivateEndMinute2", "txtPrivateEndMinute2", vo.getTxtPrivateEndMinute2(), vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) %>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
	</table>
