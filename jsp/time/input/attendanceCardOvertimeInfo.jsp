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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.time.input.action.AttendanceCardAction"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.vo.AttendanceCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceCardVo vo = (AttendanceCardVo)params.getVo();
%>
	<table class="HeaderInputTable" id="attendanceCard_tblOvertimeHeader">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("OvertimeWorkInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblOvertime">
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertime"><%= HtmlUtility.escapeHTML(vo.getLblOvertime()) %></td>
			<td class="TitleTd" id="tdOvertimeIn"><%= params.getName("Legal","Inside","OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertimeIn"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeIn()) %></td>
			<td class="TitleTd" id="tdOvertimeOut"><%= params.getName("Legal","Outside","OvertimeWork","Time") %></td>
			<td class="InputTd" id="lblOvertimeOut"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeOut()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Midnight","Work","Time") %></td>
			<td class="InputTd" id="lblLateNightTime"><%= HtmlUtility.escapeHTML(vo.getLblLateNightTime()) %></td>
			<td class="TitleTd"><%= params.getName("Prescribed","DayOff","Work","Time") %></td>
			<td class="InputTd" id="lblSpecificWorkTime"><%=HtmlUtility.escapeHTML( vo.getLblSpecificWorkTimeIn()) %></td>
			<td class="TitleTd"><%= params.getName("Legal","DayOff","Work","Time") %></td>
			<td class="InputTd" id="lblLegalWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblLegalWorkTime()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Reduced","Target","Time") %></td>
			<td class="InputTd" id="lblDecreaseTime"><%= HtmlUtility.escapeHTML(vo.getLblDecreaseTime()) %></td>
			<td class="Blank" colspan="4"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnRegist" name="btnRegist" onclick="submitRegistAfterExtraFanc(event, '', checkRegistTimes, checkRestTimes, '<%= AttendanceCardAction.CMD_APPLI %>')"><%= params.getName("Application") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnDraft" name="btnDraft" onclick="submitRegist(event, '', checkDraftTimes, '<%= AttendanceCardAction.CMD_DRAFT %>')"><%= params.getName("WorkPaper") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnDelete" onclick="submitRegist(event, null, null, '<%= AttendanceCardAction.CMD_DELETE %>')"><%= params.getName("Delete") %></button>
			</td>
		</tr>
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnCalc" name="btnCalc" onclick="submitRegist(event, '', checkRegistTimes, '<%= AttendanceCardAction.CMD_CALC %>')"><%= params.getName("TrialCalc") %></button>
			</td>
		</tr>
	</table>
