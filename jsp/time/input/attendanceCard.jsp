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
<%@page import="jp.mosp.framework.utils.DateUtility"%>
<%@ page
language     = "java"
pageEncoding = "UTF-8"
buffer       = "512kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.CalendarHtmlUtility"
import = "jp.mosp.framework.utils.RoleUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.AttendanceCardAction"
import = "jp.mosp.time.input.action.WorkTypeChangeRequestAction"
import = "jp.mosp.time.input.vo.AttendanceCardVo"
import = "jp.mosp.time.utils.TimeUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AttendanceCardVo vo = (AttendanceCardVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%= TimeConst.PATH_TIME_COMMON_INFO_JSP %>" flush="false" />
		</tr>
	</table>
	<table class="DateChangeTable">
		<tr>
			<td><a class="RollLink" id="eventFormer" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= TimeConst.CODE_DATE_DECREMENT %>'), '<%= AttendanceCardAction.CMD_SEARCH %>');">&lt;&lt;<%= params.getName("Ahead","Day") %></a></td>
			<td>
				&nbsp;
				<%= CalendarHtmlUtility.getCalendarDiv(CalendarHtmlUtility.TYPE_DAY, "hdnTargetDate", "hdnTargetDate", DateUtility.getStringDate(vo.getTargetDate()), false, false, true) %>
				<%= DateUtility.getStringJapaneseDate(vo.getTargetDate()) %>
				<%= params.getName("FrontParentheses") %><span <%= vo.getLblWorkDayOfWeekStyle() %>><%= HtmlUtility.escapeHTML(vo.getLblDayOfTheWeek()) %></span><%= params.getName("BackParentheses") %>
				 
			</td>
			<td><a class="RollLink" id="eventNext" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= TimeConst.CODE_DATE_INCREMENT %>'), '<%= AttendanceCardAction.CMD_SEARCH %>');"><%= params.getName("NextDay") %>&gt;&gt;</a></td>
			<td class="BetweenTd"></td>
			<td>
				<button type="button" class="Name2Button" id="btnReset" name="btnReset" onclick="submitTransfer(event, null, null, new Array('<%= TimeConst.PRM_TRANSFERRED_GENERIC_CODE %>', '<%= TimeConst.CODE_DATE_RESET %>'), '<%= AttendanceCardAction.CMD_SEARCH %>');"><%= params.getName("Now","Day") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="List">
<%
// 承認個人ID利用
if(!params.isTargetApprovalUnit()){
%>		
	<table class="HeaderInputTable" id="attendanceCard_tblApproverSelectHeader">
		<tr>
			<th class="ListTableTh" colspan="2">
				<span class="TitleTh"><%= params.getName("Approver","Set") %></span>
			</th>
		</tr>
	</table>
	<jsp:include page="<%= TimeConst.PATH_TIME_APPROVER_PULLDOWN_JSP %>" flush="false" />
<%
}
%>	
	<table class="HeaderInputTable" id="attendanceCard_tblCorrectionHeader">
		<tr>
			<th class="ListTableTh" colspan="2">
				<span class="TitleTh"><%= params.getName("CorrectionInformation") %></span>
			</th>
		</tr>
	</table>
	<table class="FixInputTable" id="attendanceCard_tblCorrection">
		<tr>
			<td class="TitleTd"><%= params.getName("CorrectionSummary") %></td>
			<td class="InputTd"><%= HtmlUtility.escapeHTML(vo.getLblCorrectionHistory()) %></td>
		</tr>
	</table>
	
<%
// 出退勤情報・休憩情報・遅刻早退情報
for (String applicationProperty : params.getApplicationProperties("AttendanceCardItemJsp")) {
%>
	<jsp:include page="<%= applicationProperty %>" flush="false" />
<%
}
// アドオンJSP
for (String addonJsp : vo.getAddonJsps()) {
%>
	<jsp:include page="<%= addonJsp %>" flush="false" />
<%
}
%>
</div>
<div class="FixList">
	<table class="LeftListTable" id="attendanceCard_tblAttendanceRequest">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("WorkApprovalSituation") %></span></th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceComment()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblAttendanceState"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceState()) %></td>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblAttendanceApprover"><%= HtmlUtility.escapeHTML(vo.getLblAttendanceApprover()) %></td>
		</tr>
	</table>
<%
if (TimeUtility.isOvertimeRequestAvailable(params)) {
%>
	<table class="LeftListTable" id="attendanceCard_tblOvertimeRequest">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("OvertimeRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnOvertimeRequest" name="btnOvertimeRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblOvertimeTransferParams()) %>), '<%= HtmlUtility.escapeHTML(vo.getLblOvertimeCmd()) %>');"><%= params.getName("jp.mosp.time.input.vo.OvertimeRequestVo","Information","Confirmation") %></button>
				</span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblOvertimeType().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Type") %></td>
			<td class="InputTd" id="lblOvertimeType"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeType()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Application","Time") %></td>
			<td class="InputTd" id="lblOvertimeSchedule"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeSchedule()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Performance","Time") %></td>
			<td class="InputTd" id="lblOvertimeResult"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeResult()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("OvertimeWork","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeReason()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblOvertimeState"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeState()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeComment()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblOvertimeApprover"><%= HtmlUtility.escapeHTML(vo.getLblOvertimeApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (TimeUtility.isHolidayRequestAvailable(params)) {
%>
	<table class="LeftListTable" id="attendanceCard_tblHolidayRequest">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("HolidayRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnHolidayRequest" name="btnHolidayRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblHolidayTransferParams()) %>), '<%= HtmlUtility.escapeHTML(vo.getLblHolidayCmd()) %>');"><%= params.getName("jp.mosp.time.input.vo.HolidayRequestVo","Information","Confirmation") %></button>
				</span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblHolidayType().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("Holiday","Classification") %></td>
			<td class="InputTd" id="lblHolidayType"><%= HtmlUtility.escapeHTML(vo.getLblHolidayType()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Holiday","Content") %></td>
			<td class="InputTd" id="lblHolidayLength"><%= HtmlUtility.escapeHTML(vo.getLblHolidayLength()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Holiday","Time") %></td>
			<td class="InputTd" id="lblHolidayTime"><%= vo.getLblHolidayTime()[i] %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Holiday","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblHolidayReason()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Application", "Ahead", "Work", "Form") %></td>
			<td class="InputTd" id="lblHolidayWorkType"><%= HtmlUtility.escapeHTML(vo.getLblHolidayWorkType()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblHolidayComment()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblHolidayState()[i]) %></td>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblHolidayApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (TimeUtility.isWorkOnHolidayRequestAvailable(params)) {
%>
	<table class="LeftListTable" id="attendanceCard_tblWorkOnHolidayRequest">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("WorkOnHolidayRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnWorkOnHolidayRequest" name="btnWorkOnHolidayRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTransferParams()) %>), '<%= vo.getLblWorkOnHolidayCmd() %>');"><%= params.getName("SubstituteAbbr","GoingWorkAbbr","WorkingHoliday","Information","Confirmation") %></button>
				</span>
			</th>
		</tr>
<% if (!vo.getLblWorkOnHolidayDate().isEmpty()) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblWorkOnHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayDate()) %></td>
			<td class="TitleTd"><%= params.getName("Schedule") %></td>
			<td class="InputTd" id="lblWorkOnHolidayTime"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayTime()) %></td>
			<td class="TitleTd"><%= params.getName("Transfer","Day") %></td>
			<td class="InputTd" id="lblSubStituteDate"><%= HtmlUtility.escapeHTML(vo.getLblSubStituteDate()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblWorkOnHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayComment()) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblWorkOnHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkOnHolidayApprover()) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (TimeUtility.isSubHolidayRequestAvailable(params)) {
%>
	<table class="LeftListTable" id="attendanceCard_tblSubHolidayRequest">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("SubHolidayRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnSubHolidayRequest" name="btnSubHolidayRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblSubHolidayTransferParams()) %>), '<%= HtmlUtility.escapeHTML(vo.getLblSubHolidayCmd()) %>');"><%= params.getName("jp.mosp.time.input.vo.SubHolidayRequestVo") %><%= params.getName("Information") %><%= params.getName("Confirmation") %></button>
				</span>
			</th>
		</tr>
<% for (int i = 0; i < vo.getLblSubHolidayDate().length; i++) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("CompensatoryHoliday","Day") %></td>
			<td class="InputTd" id="lblSubHolidayDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayDate()[i]) %></td>
			<td class="TitleTd"><%= params.getName("Holiday","Content") %></td>
			<td class="InputTd" id="lblSubHolidayLength"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayLength()[i]) %></td>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblSubHolidayWorkDate"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayWorkDate()[i]) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayComment()[i]) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblSubHolidayState"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayState()[i]) %></td>
		</tr>
		<tr>
			<td class="Blank" colspan="4"></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblSubHolidayApprover"><%= HtmlUtility.escapeHTML(vo.getLblSubHolidayApprover()[i]) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (TimeUtility.isWorkTypeChangeRequestAvailable(params)) {
%>
	<table class="LeftListTable" id="attendanceCard_tblWorkTypeChangeRequest">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("WorkTypeChangeRequestSituation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnWorkTypeChangeRequest" name="btnWorkTypeChangeRequest"
						onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= WorkTypeChangeRequestAction.class.getName() %>'), '<%= AttendanceCardAction.CMD_TRANSFER %>');"><%= params.getName("WorkTypeAbbr") %><%= params.getName("Change") %><%= params.getName("Information") %><%= params.getName("Confirmation") %></button>
				</span>
			</th>
		</tr>
<% if (!vo.getLblWorkTypeChangeDate().isEmpty()) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblWorkTypeChangeDate"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeDate()) %></td>
			<td class="TitleTd"><%= params.getName("Change","Ahead","Work","Form") %></td>
			<td class="InputTd" id="lblWorkTypeChangeBeforeWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeBeforeWorkType()) %></td>
			<td class="TitleTd"><%= params.getName("Change","Later","Work","Form") %></td>
			<td class="InputTd" id="lblWorkTypeChangeAfterWorkType"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeAfterWorkType()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblWorkTypeChangeState"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeComment()) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblWorkTypeChangepprover"><%= HtmlUtility.escapeHTML(vo.getLblWorkTypeChangeApprover()) %></td>
		</tr>
<% } %>
	</table>
<%
}
if (params.getProperties().getMainMenuProperties().get("menuTimeInput").getMenuMap().containsKey("DifferenceRequest")
	&& RoleUtility.getUserMenuKeys(params).contains("DifferenceRequest")) {
%>
	<table class="LeftListTable" id="attendanceCard_tblDifferenceRequest">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= params.getName("jp.mosp.time.input.vo.DifferenceRequestVo","Situation") %></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name8Button" id="btnDifferenceRequest" name="btnDifferenceRequest"
						onclick="submitTransfer(event, null, null, new Array(<%= HtmlUtility.escapeHTML(vo.getLblDifferenceTransferParams()) %>), '<%= HtmlUtility.escapeHTML(vo.getLblDifferenceCmd()) %>');"><%= params.getName("TimeDifference","GoingWork","Information","Confirmation") %></button>
				</span>
			</th>
		</tr>
<% if (!vo.getLblDifferenceDate().isEmpty()) { %>
		<tr>
			<td class="TitleTd"><%= params.getName("GoingWork","Day") %></td>
			<td class="InputTd" id="lblDifferenceDate"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceDate()) %></td>
			<td class="TitleTd"><%= params.getName("Application","Ahead","Work","Form") %></td>
			<td class="InputTd" id="lblBeforeWorkType"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceWorkType()) %></td>
			<td class="TitleTd"><%= params.getName("Application","Later","Work","Time") %></td>
			<td class="InputTd" id="lblDifferenceWorkTime"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceWorkTime()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Application","Reason") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceReason()) %></td>
			<td class="TitleTd"><%= params.getName("State") %></td>
			<td class="InputTd" id="lblDifferenceState"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceState()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Comment") %></td>
			<td class="InputTd" id="tdApprovalComment" colspan="3"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceComment()) %></td>
			<td class="TitleTd"><%= params.getName("Approver") %></td>
			<td class="InputTd" id="lblDifferenceApprover"><%= HtmlUtility.escapeHTML(vo.getLblDifferenceApprover()) %></td>
		</tr>
<% } %>
	</table>
<%
}
%>
</div>

