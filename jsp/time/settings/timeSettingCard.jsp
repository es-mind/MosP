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
buffer       = "512kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.utils.TimeMessageUtility"
import = "jp.mosp.time.settings.action.TimeSettingCardAction"
import = "jp.mosp.time.settings.action.TimeSettingListAction"
import = "jp.mosp.time.settings.vo.TimeSettingCardVo"
import = "jp.mosp.time.utils.TimeUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TimeSettingCardVo vo = (TimeSettingCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<jsp:include page="<%=TimeConst.PATH_SETTINGS_EDIT_HEADER_JSP%>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdActivateDate"><%=HtmlUtility.getRequiredMark()%><%=PfNameUtility.activateDate(params)%></td>
			<td class="InputTd" id="tdNoBorderBottom">
				<div id="divSearchCutoffDate">
					<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateYear())%>" />
					<label for="txtEditActivateYear"><%=params.getName("Year")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth())%>" />
					<label for="txtEditActivateMonth"><%=params.getName("Month")%></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateDay())%>" />
					<label for="txtEditActivateDay"><%=params.getName("Day")%></label>&nbsp;
					<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'divSearchCutoffDate', null, '<%=TimeSettingCardAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</div>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtSettingCode"><%=TimeNamingUtility.timeSettingCode(params)%></label></td>
			<td class="InputTd"><input type="text" class="Code10RequiredTextBox" id="txtSettingCode" name="txtSettingCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSettingCode())%>" /></td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtSettingName"><%=params.getName("WorkManage", "Set", "Name")%></label></td>
			<td class="InputTd"><input type="text" class="Name15RequiredTextBox" id="txtSettingName" name="txtSettingName" value="<%=HtmlUtility.escapeHTML(vo.getTxtSettingName())%>" /></td>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("CutoffDate", "Abbreviation")%></td>
			<td class="InputTd" id="tdNoBorderTop">
				<select class="Name13PullDown" id="pltCutoffDate" name="pltCutoffDate">
					<%=HtmlUtility.getSelectOption(vo.getAryPltCutoffDate(), vo.getPltCutoffDate())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtSettingAbbr"><%=params.getName("WorkManage", "Set", "Abbreviation")%></label></td>
			<td class="InputTd"><input type="text" class="Byte6RequiredTextBox" id="txtSettingAbbr" name="txtSettingAbbr" value="<%=HtmlUtility.escapeHTML(vo.getTxtSettingAbbr())%>"/></td>
			<td class="TitleTd"><%=params.getName("Effectiveness", "Slash", "Inactivate")%></td>
			<td class="InputTd">
				<select id="pltEditInactivate" name="pltEditInactivate">
					<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false)%>
				</select>
			</td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("WorkManage", "Set")%></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%=params.getName("WorkManage", "Management", "Target")%></td>
			<td class="InputTd">
				<select id="pltTimeManagement" name="pltTimeManagement">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_TIME_MANAGEMENT, vo.getPltTimeManagement(), false)%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Prescribed", "Labor", "Time")%></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtGeneralWorkTimeHour" name="txtGeneralWorkTimeHour" value="<%=HtmlUtility.escapeHTML(vo.getTxtGeneralWorkTimeHour())%>" />&nbsp;<label for="txtGeneralWorkTimeHour"><%=params.getName("Time")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtGeneralWorkTimeMinute" name="txtGeneralWorkTimeMinute" value="<%=HtmlUtility.escapeHTML(vo.getTxtGeneralWorkTimeMinute())%>" />&nbsp;<label for="txtGeneralWorkTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("StartDayTime")%></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtStartDayHour" name="txtStartDayHour" value="<%=HtmlUtility.escapeHTML(vo.getTxtStartDayHour())%>" />&nbsp;<label for="txtStartDayHour"><%=params.getName("Hour")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtStartDayMinute" name="txtStartDayMinute" value="<%=HtmlUtility.escapeHTML(vo.getTxtStartDayMinute())%>" />&nbsp;<label for="txtStartDayMinute"><%=params.getName("Minutes")%></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Tardiness", "LeaveEarly", "Limit", "Time", "FrontParentheses", "HalfDay", "BackParentheses")%></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtLateEarlyHalfHour" name="txtLateEarlyHalfHour" value="<%=HtmlUtility.escapeHTML(vo.getTxtLateEarlyHalfHour())%>" />&nbsp;<label for="txtLateEarlyHalfHour"><%=params.getName("Time")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtLateEarlyHalfMinute" name="txtLateEarlyHalfMinute" value="<%=HtmlUtility.escapeHTML(vo.getTxtLateEarlyHalfMinute())%>" />&nbsp;<label for="txtLateEarlyHalfMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=params.getName("Work", "Ahead", "OvertimeWork")%></td>
			<td class="InputTd">
				<select id="pltBeforeOverTime" name="pltBeforeOverTime">
					<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltBeforeOverTime(), false)%>
				</select>
			</td>
			<td class="TitleTd">
				<label for="pltStartWeek"><%=TimeNamingUtility.startDayOfTheWeek(params)%></label>
			</td>
			<td class="InputTd">
				<%=HtmlUtility.getSelectTag(params, "", "pltStartWeek", "pltStartWeek", vo.getPltStartWeek(), TimeConst.CODE_KEY_DAY_OF_THE_WEEK, false, false)%>
				<%=TimeNamingUtility.dayOfTheWeek(params)%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("ClosedVibration", "Acquisition", "TimeLimit", "FrontParentheses", "WorkingHoliday", "Ahead", "BackParentheses")%></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtTransferAheadLimitMonth" name="txtTransferAheadLimitMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtTransferAheadLimitMonth())%>" />&nbsp;<label for="txtTransferAheadLimitMonth"><%=params.getName("Months")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtTransferAheadLimitDate" name="txtTransferAheadLimitDate" value="<%=HtmlUtility.escapeHTML(vo.getTxtTransferAheadLimitDate())%>" />&nbsp;<label for="txtTransferAheadLimitDate"><%=params.getName("Day")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%= TimeNamingUtility.substituteHolidayLaterLimit(params) %></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtTransferLaterLimitMonth" name="txtTransferLaterLimitMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtTransferLaterLimitMonth())%>" />&nbsp;<label for="txtTransferLaterLimitMonth"><%=params.getName("Months")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtTransferLaterLimitDate" name="txtTransferLaterLimitDate" value="<%=HtmlUtility.escapeHTML(vo.getTxtTransferLaterLimitDate())%>" />&nbsp;<label for="txtTransferLaterLimitDate"><%=params.getName("Day")%></label>
			</td>
			<td class="TitleTd"><%=params.getName("Prescribed", "DayOff", "Handling")%></td>
			<td class="InputTd">
				<select id="pltSpecificHoliday" name="pltSpecificHoliday">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_SPECIFIC_HOLIDAY, vo.getPltSpecificHoliday(), false)%>
				</select>
			</td>
		</tr>
<%
if (TimeUtility.isSubHolidayRequestValid(params)) {
%>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("CompensatoryHoliday", "Acquisition", "Norm", "Time", "FrontParentheses", "HalfTime", "BackParentheses")%></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayHalfNormHour" name="txtSubHolidayHalfNormHour" value="<%=HtmlUtility.escapeHTML(vo.getTxtSubHolidayHalfNormHour())%>" />&nbsp;<label for="txtSubHolidayHalfNormHour"><%=params.getName("Time")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayHalfNormMinute" name="txtSubHolidayHalfNormMinute" value="<%=HtmlUtility.escapeHTML(vo.getTxtSubHolidayHalfNormMinute())%>" />&nbsp;<label for="txtSubHolidayHalfNormMinute"><%=params.getName("Minutes", "Over")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("CompensatoryHoliday", "Acquisition", "Norm", "Time", "FrontParentheses", "AllTime", "BackParentheses")%></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayAllNormHour" name="txtSubHolidayAllNormHour" value="<%=HtmlUtility.escapeHTML(vo.getTxtSubHolidayAllNormHour())%>" />&nbsp;<label for="txtSubHolidayAllNormHour"><%=params.getName("Time")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayAllNormMinute" name="txtSubHolidayAllNormMinute" value="<%=HtmlUtility.escapeHTML(vo.getTxtSubHolidayAllNormMinute())%>" />&nbsp;<label for="txtSubHolidayAllNormMinute"><%=params.getName("Minutes", "Over")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("CompensatoryHoliday", "Acquisition", "TimeLimit")%></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayLimitMonth" name="txtSubHolidayLimitMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSubHolidayLimitMonth())%>" />&nbsp;<label for="txtSubHolidayLimitMonth"><%=params.getName("Months")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSubHolidayLimitDate" name="txtSubHolidayLimitDate" value="<%=HtmlUtility.escapeHTML(vo.getTxtSubHolidayLimitDate())%>" />&nbsp;<label for="txtSubHolidayLimitDate"><%=params.getName("Day")%></label>
			</td>
		</tr>
<%
}
%>
		<tr>
			<td class="TitleTd"><span id="lblPortalAttendanceButtonDisplay"><%=params.getName("Portal", "Attendance", "Button", "Display")%></span></td>
			<td class="InputTd">
				<select id="pltPortalTimeButtons" name="pltPortalTimeButtons">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_PORTAL_TIME_BUTTONS, vo.getPltPortalTimeButtons(), false)%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("Portal", "RestTime", "Button", "Display")%></td>
			<td class="InputTd">
				<select id="pltPortalRestButtons" name="pltPortalRestButtons">
					<%=HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_PORTAL_REST_BUTTONS, vo.getPltPortalRestButtons(), false)%>
				</select>
			</td>
			<td class="TitleTd"><%=params.getName("Work", "Schedule", "Time", "Display")%></td>
			<td class="InputTd">
				<select id="pltUseScheduledTime" name="pltUseScheduledTime">
					<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUseScheduledTime(), false)%>
				</select>
			</td>
		</tr>
		<tr id="trTimeSettingBlank"></tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Time", "Rounding", "Set")%></span>
				<a></a>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyStart"><%=params.getName("Day", "GoingWork", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyStart" name="txtRoundDailyStart" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyStart())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyStart" name="pltRoundDailyStart">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyStart())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyEnd"><%=params.getName("Day", "RetireOffice", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyEnd" name="txtRoundDailyEnd" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyEnd())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyEnd" name="pltRoundDailyEnd">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyEnd())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyWork"><%=params.getName("Day", "Work", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyWork" name="txtRoundDailyWork" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyWork())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyWork" name="pltRoundDailyWork">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyWork())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyRestStart"><%=params.getName("Day", "RestTime", "Into", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyRestStart" name="txtRoundDailyRestStart" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyRestStart())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyRestStart" name="pltRoundDailyRestStart">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyRestStart())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyRestEnd"><%=params.getName("Day", "RestTime", "Return", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyRestEnd" name="txtRoundDailyRestEnd" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyRestEnd())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyRestEnd" name="pltRoundDailyRestEnd">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyRestEnd())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyRestTime"><%=params.getName("Day", "RestTime", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyRestTime" name="txtRoundDailyRestTime" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyRestTime())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyRestTime" name="pltRoundDailyRestTime">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyRestTime())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyPublicIn"><%=params.getName("Day", "Official", "GoingOut", "Into", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyPublicIn" name="txtRoundDailyPublicIn" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyPublicIn())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyPublicIn" name="pltRoundDailyPublicIn">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyPublicIn())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyPublicOut"><%=params.getName("Day", "Official", "GoingOut", "Return", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyPublicOut" name="txtRoundDailyPublicOut" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyPublicOut())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyPublicOut" name="pltRoundDailyPublicOut">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyPublicOut())%>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyLate"><%=params.getName("Day", "Tardiness", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyLate" name="txtRoundDailyLate" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyLate())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyLate" name="pltRoundDailyLate">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyLate())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyLeaveEaly"><%=params.getName("Day", "LeaveEarly", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyLeaveEarly" name="txtRoundDailyLeaveEarly" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyLeaveEarly())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyLeaveEaly" name="pltRoundDailyLeaveEaly">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyLeaveEaly())%>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyPrivateIn"><%=params.getName("Day", "Private", "GoingOut", "Into", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyPrivateIn" name="txtRoundDailyPrivateIn" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyPrivateIn())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyPrivateIn" name="pltRoundDailyPrivateIn">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyPrivateIn())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyPrivateOut"><%=params.getName("Day", "Private", "GoingOut", "Return", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyPrivateOut" name="txtRoundDailyPrivateOut" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyPrivateOut())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyPrivateOut" name="pltRoundDailyPrivateOut">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyPrivateOut())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyDecreaseTime"><%=params.getName("Day", "Reduced", "Target", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyDecreaseTime" name="txtRoundDailyDecreaseTime" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyDecreaseTime())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyDecreaseTime" name="pltRoundDailyDecreaseTime">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyDecreaseTime())%>
				</select>
			</td>
		</tr>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)){
%>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundDailyShortUnpaid"><%=params.getName("Day", "UnpaidShortTime", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundDailyShortUnpaid" name="txtRoundDailyShortUnpaid" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundDailyShortUnpaid())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundDailyShortUnpaid" name="pltRoundDailyShortUnpaid">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundDailyShortUnpaid())%>
				</select>
			</td>
			<td class="Blank" colspan="4"></td>
		</tr>
<%
}
%>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundMonthlyWork"><%=params.getName("Month", "Work", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyWork" name="txtRoundMonthlyWork" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyWork())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundMonthlyWork" name="pltRoundMonthlyWork">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyWork())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundMonthlyRest"><%=params.getName("Month", "RestTime", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyRest" name="txtRoundMonthlyRest" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyRest())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundMonthlyRest" name="pltRoundMonthlyRest">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyRest())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundMonthlyPublic"><%=params.getName("Month", "Official", "GoingOut", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyPublic" name="txtRoundMonthlyPublic" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyPublic())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundMonthlyPublic" name="pltRoundMonthlyPublic">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyPublic())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundMonthlyLate"><%=params.getName("Month", "Tardiness", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyLate" name="txtRoundMonthlyLate" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyLate())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundMonthlyLate" name="pltRoundMonthlyLate">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyLate())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundMonthlyLeaveEarly"><%=params.getName("Month", "LeaveEarly", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyLeaveEarly" name="txtRoundMonthlyLeaveEarly" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyLeaveEarly())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundMonthlyLeaveEarly" name="pltRoundMonthlyLeaveEarly">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyLeaveEarly())%>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundMonthlyPrivate"><%=params.getName("Month", "Private", "GoingOut", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyPrivate" name="txtRoundMonthlyPrivate" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyPrivate())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundMonthlyPrivate" name="pltRoundMonthlyPrivate">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyPrivate())%>
				</select>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundMonthlyDecreaseTime"><%=params.getName("Month", "Reduced", "Target", "Time", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyDecreaseTime" name="txtRoundMonthlyDecreaseTime" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyDecreaseTime())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundMonthlyDecreaseTime" name="pltRoundMonthlyDecreaseTime">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyDecreaseTime())%>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)){
%>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtRoundMonthlyShortUnpaid"><%=params.getName("Month", "UnpaidShortTime", "Rounding")%></label></td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRoundMonthlyShortUnpaid" name="txtRoundMonthlyShortUnpaid" value="<%=HtmlUtility.escapeHTML(vo.getTxtRoundMonthlyShortUnpaid())%>" />&nbsp;<%=params.getName("Minutes", "Unit")%>&nbsp;
				<select id="pltRoundMonthlyShortUnpaid" name="pltRoundMonthlyShortUnpaid">
					<%=HtmlUtility.getSelectOption(vo.getAryPltRoundingItems(), vo.getPltRoundMonthlyShortUnpaid())%>
				</select>
			</td>
			<td class="Blank" colspan="4"></td>
		</tr>
<%
}
%>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="3">
				<span class="TitleTh"><%=TimeNamingUtility.outOfTime(params)%><%=PfNameUtility.displaySetting(params)%></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd" rowspan="2">
				<%=TimeNamingUtility.overtimeRequestScreen(params)%><%=PfNameUtility.displaySetting(params)%>
			</td>
			<td class="TitleTd">
				<label for="txtLimit1WeekHour,txtLimit1WeekMinute"><%=TimeNamingUtility.oneWeekLimitTime(params)%></label>
			</td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number2TextBox", "txtLimit1WeekHour", vo.getTxtLimit1WeekHour(), false)%>
				<%=PfNameUtility.time(params)%>
				<%=HtmlUtility.getTextboxTag("Number2TextBox", "txtLimit1WeekMinute", vo.getTxtLimit1WeekMinute(), false)%>
				<%=PfNameUtility.minutes(params)%>
				<%=PfNameUtility.signStar(params)%><%=TimeMessageUtility.getLimitSettingDescription(params)%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtLimit1MonthHour,txtLimit1MonthMinute"><%=TimeNamingUtility.oneMonthLimitTime(params)%></label>
			</td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number2TextBox", "txtLimit1MonthHour", vo.getTxtLimit1MonthHour(), false)%>
				<%=PfNameUtility.time(params)%>
				<%=HtmlUtility.getTextboxTag("Number2TextBox", "txtLimit1MonthMinute", vo.getTxtLimit1MonthMinute(), false)%>
				<%=PfNameUtility.minutes(params)%>
				<%=PfNameUtility.signStar(params)%><%=TimeMessageUtility.getLimitSettingDescription(params)%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" rowspan="2">
				<%=TimeNamingUtility.attendanceListScreen(params)%><%=PfNameUtility.displaySetting(params)%>
			</td>
			<td class="TitleTd">
				<label for="txtAttention1MonthHour,txtAttention1MonthMinute"><%=TimeNamingUtility.oneMonthAttentionTime(params)%></label>
			</td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number2TextBox", "txtAttention1MonthHour", vo.getTxtAttention1MonthHour(), false)%>
				<%=PfNameUtility.time(params)%>
				<%=HtmlUtility.getTextboxTag("Number2TextBox", "txtAttention1MonthMinute", vo.getTxtAttention1MonthMinute(), false)%>
				<%=PfNameUtility.minutes(params)%>
				<%=PfNameUtility.signStar(params)%><%=TimeMessageUtility.getAttentionSettingDescription(params)%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtWarning1MonthHour,txtWarning1MonthMinute"><%=TimeNamingUtility.oneMonthWarningTime(params)%></label>
			</td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number2TextBox", "txtWarning1MonthHour", vo.getTxtWarning1MonthHour(), false)%>
				<%=PfNameUtility.time(params)%>
				<%=HtmlUtility.getTextboxTag("Number2TextBox", "txtWarning1MonthMinute", vo.getTxtWarning1MonthMinute(), false)%>
				<%=PfNameUtility.minutes(params)%>
				<%=PfNameUtility.signStar(params)%><%= TimeMessageUtility.getWarningSettingDescription(params) %>
			</td>
		</tr>
	</table>
<%
for (String addonJsp : vo.getAddonJsps()) {
%>
	<jsp:include page="<%= addonJsp %>" flush="false" />
<%
}
%>
</div>
<div class="Button">
	<button type="button" class="Name6Button" id="btnRegist" name="btnRegist" onclick="submitRegist(event, 'divEdit', checkExtra, '<%= TimeSettingCardAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
	<button type="button" class="Name6Button" id="btnDelete" name="btnDelete" onclick="submitDelete(event, null, null, '<%= TimeSettingCardAction.CMD_DELETE %>')"><%= params.getName("History", "Delete") %></button>
<%
}
%>
	<button type="button" class="Name6Button" id="btnTimeSettingList" name="btnTimeSettingList" onclick="submitTransfer(event, null, null, null, '<%= TimeSettingListAction.CMD_RE_SHOW %>')"><%= params.getName("WorkManage", "Set", "List") %></button>
</div>


