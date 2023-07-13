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
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.utils.TimeUtility"
import = "jp.mosp.time.calculation.action.TotalTimeCardAction"
import = "jp.mosp.time.calculation.action.TotalTimeListAction"
import = "jp.mosp.time.calculation.vo.TotalTimeCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TotalTimeCardVo vo = (TotalTimeCardVo)params.getVo();
%>
<jsp:include page="<%=TimeConst.PATH_TIME_TOTAL_JSP%>" flush="false" />
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%=TimeConst.PATH_TIME_COMMON_INFO_JSP%>" flush="false" />
		</tr>
	</table>
</div>
<div class="List">
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="2">
				<span class="TitleTh"><%=params.getName("Correction","Information")%></span>
				<span class="TableButtonSpan">
					<button type="button" class="Name4Button" id="btnSelect" name="btnSelect" onclick="submitTransfer(event, '', null, null, '<%=TotalTimeCardAction.CMD_UPDATE%>');"><%=vo.isViewMode() ? params.getName("Correction") : params.getName("CorrectionRelease")%></button>
				</span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblCorrection">
		<tr>
			<td class="TitleTd" id="tdCorrectionTitle"><%=params.getName("Correction","History")%></td>
			<td class="InputTd">
				<%=HtmlUtility.escapeHTML(vo.getLblCorrectionHistory())%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtCorrectionReason"><%=params.getName("Correction","Reason")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Name40RequiredTextBox", "txtCorrectionReason", "txtCorrectionReason", vo.getTxtCorrectionReason(), vo.isViewMode())%>
			</td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%=params.getName("Attendance","Item")%></span></th>
		</tr>
	</table>
	<table class="FixTable" id="tblAttendance">
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Work","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWorkTimeHour", "txtWorkTimeHour", vo.getTxtWorkTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtWorkTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWorkTimeMinute", "txtWorkTimeMinute", vo.getTxtWorkTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtWorkTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("GoingWork","Days")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesWorkDate", "txtTimesWorkDate", vo.getTxtTimesWorkDate(), vo.isViewMode())%>&nbsp;<label for="txtTimesWorkDate"><%=PfNameUtility.day(params)%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("GoingWork","Frequency")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtTimesWork", "txtTimesWork", vo.getTxtTimesWork(), vo.isViewMode())%>&nbsp;<label for="txtTimesWork"><%=params.getName("CountNum")%></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%= TimeNamingUtility.legalHoliday(params) %><%= params.getName("GoingWork","Days")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtLegalWorkOnHoliday", "txtLegalWorkOnHoliday", vo.getTxtLegalWorkOnHoliday(), vo.isViewMode())%>&nbsp;<label for="txtLegalWorkOnHoliday"><%=PfNameUtility.day(params)%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%= TimeNamingUtility.prescribedHoliday(params) %><%=params.getName("GoingWork","Days")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtSpecificWorkOnHoliday", "txtSpecificWorkOnHoliday", vo.getTxtSpecificWorkOnHoliday(), vo.isViewMode())%>&nbsp;<label for="txtSpecificWorkOnHoliday"><%=PfNameUtility.day(params)%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("GoingWork","Performance","Days")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesAchievement", "txtTimesAchievement", vo.getTxtTimesAchievement(), vo.isViewMode())%>&nbsp;<label for="txtTimesAchievement"><%=PfNameUtility.day(params)%></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("GoingWork","Target","Days")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesTotalWorkDate", "txtTimesTotalWorkDate", vo.getTxtTimesTotalWorkDate(), vo.isViewMode())%>&nbsp;<label for="txtTimesTotalWorkDate"><%=PfNameUtility.day(params)%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesNonstop"><%=params.getName("DirectStart","Frequency")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesNonstop", "txtTimesNonstop", vo.getTxtTimesNonstop(), vo.isViewMode())%>&nbsp;<%=params.getName("CountNum")%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesNoreturn"><%=params.getName("DirectEnd","Frequency")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtTimesNoreturn", "txtTimesNoreturn", vo.getTxtTimesNoreturn(), vo.isViewMode())%>&nbsp;<%=params.getName("CountNum")%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Prescribed","Work","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtSpecificWorkTimeHour", "txtSpecificWorkTimeHour", vo.getTxtSpecificWorkTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtSpecificWorkTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtSpecificWorkTimeMinute", "txtSpecificWorkTimeMinute", vo.getTxtSpecificWorkTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtSpecificWorkTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)){
%>			
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("UnpaidShortTime")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtUnpaidShortTimeHour", "txtUnpaidShortTimeHour", vo.getTxtUnpaidShortTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtUnpaidShortTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtUnpaidShortTimeMinute", "txtUnpaidShortTimeMinute", vo.getTxtUnpaidShortTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtUnpaidShortTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
<%
} else {
%>
			<td class="Blank" colspan="2"></td>
<%
}
%>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%=params.getName("RestTime","Item")%></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblRest">
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("RestTime","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestTimeHour", "txtRestTimeHour", vo.getTxtRestTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtRestTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestTimeMinute", "txtRestTimeMinute", vo.getTxtRestTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtRestTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Midnight","RestTime")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestLateNightHour", "txtRestLateNightHour", vo.getTxtRestLateNightHour(), vo.isViewMode())%>&nbsp;<label for="txtRestLateNightHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestLateNightMinute", "txtRestLateNightMinute", vo.getTxtRestLateNightMinute(), vo.isViewMode())%>&nbsp;<label for="txtRestLateNightMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRestTitle"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Prescribed","WorkingHoliday","RestTime")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestWorkOnSpecificHour", "txtRestWorkOnSpecificHour", vo.getTxtRestWorkOnSpecificHour(), vo.isViewMode())%>&nbsp;<label for="txtRestWorkOnSpecificHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestWorkOnSpecificMinute", "txtRestWorkOnSpecificMinute", vo.getTxtRestWorkOnSpecificMinute(), vo.isViewMode())%>&nbsp;<label for="txtRestWorkOnSpecificMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Legal","WorkingHoliday","RestTime")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtRestWorkOnLegalHour", "txtRestWorkOnLegalHour", vo.getTxtRestWorkOnLegalHour(), vo.isViewMode())%>&nbsp;<label for="txtRestWorkOnLegalHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtRestWorkOnLegalMinute", "txtRestWorkOnLegalMinute", vo.getTxtRestWorkOnLegalMinute(), vo.isViewMode())%>&nbsp;<label for="txtRestWorkOnLegalMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("PrivateGoingOut")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtPrivateHour", "txtPrivateHour", vo.getTxtPrivateHour(), vo.isViewMode())%>&nbsp;<label for="txtPrivateHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtPrivateMinute", "txtPrivateMinute", vo.getTxtPrivateMinute(), vo.isViewMode())%>&nbsp;<label for="txtPrivateMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Official","GoingOut")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtPublicHour", "txtPublicHour", vo.getTxtPublicHour(), vo.isViewMode())%>&nbsp;<label for="txtPublicHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtPublicMinute", "txtPublicMinute", vo.getTxtPublicMinute(), vo.isViewMode())%>&nbsp;<label for="txtPublicMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("OvertimeWork","Item")%></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblOverTime">
		<tr>
			<td class="TitleTd" id="tdOverTimeTitle"><%=HtmlUtility.getRequiredMark()%><%=params.getName("OvertimeWork","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtOverTimeHour", "txtOverTimeHour", vo.getTxtOverTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtOverTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtOverTimeMinute", "txtOverTimeMinute", vo.getTxtOverTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtOverTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Legal","Inside","OvertimeWork")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtOverTimeInHour", "txtOverTimeInHour", vo.getTxtOverTimeInHour(), vo.isViewMode())%>&nbsp;<label for="txtOverTimeInHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtOverTimeInMinute", "txtOverTimeInMinute", vo.getTxtOverTimeInMinute(), vo.isViewMode())%>&nbsp;<label for="txtOverTimeInMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Legal","Outside","OvertimeWork")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtOverTimeOutHour", "txtOverTimeOutHour", vo.getTxtOverTimeOutHour(), vo.isViewMode())%>&nbsp;<label for="txtOverTimeOutHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtOverTimeOutMinute", "txtOverTimeOutMinute", vo.getTxtOverTimeOutMinute(), vo.isViewMode())%>&nbsp;<label for="txtOverTimeOutMinute"><%=params.getName("Minutes")%></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("OvertimeWork","Frequency")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtTimesOvertime", "txtTimesOvertime", vo.getTxtTimesOvertime(), vo.isViewMode())%>&nbsp;<label for="txtTimesOvertime"><%=params.getName("CountNum")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("No45","Time","Exceed","No60","Time","AndLess","OvertimeWork")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txt45HourOverTimeHour", "txt45HourOverTimeHour", vo.getTxt45HourOverTimeHour(), vo.isViewMode())%>&nbsp;<label for="txt45HourOverTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txt45HourOverTimeMinute", "txt45HourOverTimeMinute", vo.getTxt45HourOverTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txt45HourOverTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("No60","Time","Exceed","OvertimeWork")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txt60HourOverTimeHour", "txt60HourOverTimeHour", vo.getTxt60HourOverTimeHour(), vo.isViewMode())%>&nbsp;<label for="txt60HourOverTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txt60HourOverTimeMinute", "txt60HourOverTimeMinute", vo.getTxt60HourOverTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txt60HourOverTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Legal","WorkingHoliday","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWorkOnHolidayHour", "txtWorkOnHolidayHour", vo.getTxtWorkOnHolidayHour(), vo.isViewMode())%>&nbsp;<label for="txtWorkOnHolidayHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWorkOnHolidayMinute", "txtWorkOnHolidayMinute", vo.getTxtWorkOnHolidayMinute(), vo.isViewMode())%>&nbsp;<label for="txtWorkOnHolidayMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Prescribed","WorkingHoliday","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWorkSpecificOnHolidayHour", "txtWorkSpecificOnHolidayHour", vo.getTxtWorkSpecificOnHolidayHour(), vo.isViewMode())%>&nbsp;<label for="txtWorkSpecificOnHolidayHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWorkSpecificOnHolidayMinute", "txtWorkSpecificOnHolidayMinute", vo.getTxtWorkSpecificOnHolidayMinute(), vo.isViewMode())%>&nbsp;<label for="txtWorkSpecificOnHolidayMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("WorkingHoliday","Frequency")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtTimesWorkingHoliday", "txtTimesWorkingHoliday", vo.getTxtTimesWorkingHoliday(), vo.isViewMode())%>&nbsp;<label for="txtTimesWorkingHoliday"><%=params.getName("CountNum")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Midnight","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLateNightHour", "txtLateNightHour", vo.getTxtLateNightHour(), vo.isViewMode())%>&nbsp;<label for="txtLateNightHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLateNightMinute", "txtLateNightMinute", vo.getTxtLateNightMinute(), vo.isViewMode())%>&nbsp;<label for=txtLateNightMinute><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd" id="tdDecreaseTimeTitle"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Reduced","Target","Time")%></td>
			<td class="InputTd" id="tdDecreaseTimeInput">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtDecreaseTimeHour", "txtDecreaseTimeHour", vo.getTxtDecreaseTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtDecreaseTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtDecreaseTimeMinute", "txtDecreaseTimeMinute", vo.getTxtDecreaseTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtDecreaseTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%=params.getName("Tardiness","LeaveEarly","Item")%></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblLateLeaveEarly">
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtLateDays"><%=params.getName("Tardiness","SumTotal","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLateDays", "txtLateDays", vo.getTxtLateDays(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtLateDeduction"><%=params.getName("Tardiness","Thirty","Minutes","Over","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLateThirtyMinutesOrMore", "txtLateThirtyMinutesOrMore", vo.getTxtLateThirtyMinutesOrMore(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtLateNoDeduction"><%=params.getName("Tardiness","Thirty","Minutes","LessThan","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLateLessThanThirtyMinutes", "txtLateLessThanThirtyMinutes", vo.getTxtLateLessThanThirtyMinutes(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Tardiness","SumTotal","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLateTimeHour", "txtLateTimeHour", vo.getTxtLateTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtLateTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLateTimeMinute", "txtLateTimeMinute", vo.getTxtLateTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtLateTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Tardiness","Thirty","Minutes","Over","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLateThirtyMinutesOrMoreTimeHour", "txtLateThirtyMinutesOrMoreTimeHour", vo.getTxtLateThirtyMinutesOrMoreTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtLateDeductionHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLateThirtyMinutesOrMoreTimeMinute", "txtLateThirtyMinutesOrMoreTimeMinute", vo.getTxtLateThirtyMinutesOrMoreTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtLateDeductionMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Tardiness","Thirty","Minutes","LessThan","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLateLessThanThirtyMinutesTimeHour", "txtLateLessThanThirtyMinutesTimeHour", vo.getTxtLateLessThanThirtyMinutesTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtLateNoDeductionHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLateLessThanThirtyMinutesTimeMinute", "txtLateLessThanThirtyMinutesTimeMinute", vo.getTxtLateLessThanThirtyMinutesTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtLateNoDeductionMinute"><%=params.getName("Minutes")%></label>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtLeaveEarlyDays"><%=params.getName("LeaveEarly","SumTotal","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLeaveEarlyDays", "txtLeaveEarlyDays", vo.getTxtLeaveEarlyDays(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtLeaveEarlyDeduction"><%=params.getName("LeaveEarly","Thirty","Minutes","Over","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLeaveEarlyThirtyMinutesOrMore", "txtLeaveEarlyThirtyMinutesOrMore", vo.getTxtLeaveEarlyThirtyMinutesOrMore(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtLeaveEarlyNoDeduction"><%=params.getName("LeaveEarly","Thirty","Minutes","LessThan","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtLeaveEarlyLessThanThirtyMinutes", "txtLeaveEarlyLessThanThirtyMinutes", vo.getTxtLeaveEarlyLessThanThirtyMinutes(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("LeaveEarly","SumTotal","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLeaveEarlyTimeHour", "txtLeaveEarlyTimeHour", vo.getTxtLeaveEarlyTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtLeaveEarlyTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLeaveEarlyTimeMinute", "txtLeaveEarlyTimeMinute", vo.getTxtLeaveEarlyTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtLeaveEarlyTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("LeaveEarly","Thirty","Minutes","Over","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLeaveEarlyThirtyMinutesOrMoreTimeHour", "txtLeaveEarlyThirtyMinutesOrMoreTimeHour", vo.getTxtLeaveEarlyThirtyMinutesOrMoreTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtLeaveEarlyDeductionHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLeaveEarlyThirtyMinutesOrMoreTimeMinute", "txtLeaveEarlyThirtyMinutesOrMoreTimeMinute", vo.getTxtLeaveEarlyThirtyMinutesOrMoreTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtLeaveEarlyDeductionMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("LeaveEarly","Thirty","Minutes","LessThan","Time")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtLeaveEarlyLessThanThirtyMinutesTimeHour", "txtLeaveEarlyLessThanThirtyMinutesTimeHour", vo.getTxtLeaveEarlyLessThanThirtyMinutesTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtLeaveEarlyNoDeductionHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtLeaveEarlyLessThanThirtyMinutesTimeMinute", "txtLeaveEarlyLessThanThirtyMinutesTimeMinute", vo.getTxtLeaveEarlyLessThanThirtyMinutesTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtLeaveEarlyNoDeductionMinute"><%=params.getName("Minutes")%></label>
			</td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("DayOff","Item") %></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblHoliday">
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesHoliday"><%=params.getName("SumTotal","DayOff","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesHoliday", "txtTimesHoliday", vo.getTxtTimesHoliday(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesLegalHoliday"><%=params.getName("Legal","DayOff","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesLegalHoliday", "txtTimesLegalHoliday", vo.getTxtTimesLegalHoliday(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesSpecificHoliday"><%=params.getName("Prescribed","DayOff","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtTimesSpecificHoliday", "txtTimesSpecificHoliday", vo.getTxtTimesSpecificHoliday(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesSubstitute"><%=params.getName("SumTotal","Transfer","DayOff","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSubstitute", "txtTimesSubstitute", vo.getTxtTimesSubstitute(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesLegalHolidaySubstitute"><%=params.getName("Legal","Transfer","DayOff","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesLegalHolidaySubstitute", "txtTimesLegalHolidaySubstitute", vo.getTxtTimesLegalHolidaySubstitute(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesSpecificHolidaySubstitute"><%=params.getName("Prescribed","Transfer","DayOff","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecificHolidaySubstitute", "txtTimesSpecificHolidaySubstitute", vo.getTxtTimesSpecificHolidaySubstitute(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
		</tr>
	</table>
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="8">
				<span class="TitleTh"><%=params.getName("Holiday","Item")%></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tblVacation">
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesPaidHoliday"><%=params.getName("Salaried","Holiday","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesPaidHoliday", "txtTimesPaidHoliday", vo.getTxtTimesPaidHoliday(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtPaidholidayHour"><%=params.getName("Salaried","Holiday","Time")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtPaidholidayHour", "txtPaidholidayHour", vo.getTxtPaidholidayHour(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.time(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesStockHoliday"><%=params.getName("Stock","Holiday")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesStockHoliday", "txtTimesStockHoliday", vo.getTxtTimesStockHoliday(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
<%
if (TimeUtility.isSubHolidayRequestValid(params)) {
%>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesCompensation"><%=params.getName("SumTotal","CompensatoryHoliday","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesCompensation", "txtTimesCompensation", vo.getTxtTimesCompensation(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesLegalCompensation"><%=params.getName("Legal","CompensatoryHoliday","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesLegalCompensation", "txtTimesLegalCompensation", vo.getTxtTimesLegalCompensation(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesSpecificCompensation"><%=params.getName("Prescribed","CompensatoryHoliday","Days")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesSpecificCompensation", "txtTimesSpecificCompensation", vo.getTxtTimesSpecificCompensation(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
		<!-- 深夜代休日数 -->
	<%
	if (MospUtility.getFloat(vo.getTxtTimesLateCompensation()) > 0F) {
	%>
			<td class="TitleTd" id="tdLateNightTitle"><%=HtmlUtility.getRequiredMark()%><label for="txtTimesLatelCompensation"><%=params.getName("Midnight","CompensatoryHoliday","Days")%></label></td>
			<td class="InputTd" id="tdLateNightInput">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesLateCompensation", "txtTimesLateCompensation", vo.getTxtTimesLateCompensation(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td> 
		</tr>
	<%
	}
	%>
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtLegalCompensationUnused"><%=params.getName("Legal","CompensatoryHoliday","Ram","Acquisition")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtLegalCompensationUnused", "txtLegalCompensationUnused", vo.getTxtLegalCompensationUnused(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtSpecificCompensationUnused"><%=params.getName("Prescribed","CompensatoryHoliday","Ram","Acquisition")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtSpecificCompensationUnused", "txtSpecificCompensationUnused", vo.getTxtSpecificCompensationUnused(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
		<!-- 深夜代休未取得 -->
	 <%
	 if ( MospUtility.getFloat(vo.getTxtLateCompensationUnused()) > 0F) {
	 %>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtLateCompensationUnused"><%=params.getName("Midnight","CompensatoryHoliday","Ram","Acquisition")%></label></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtLateCompensationUnused", vo.getTxtLateCompensationUnused(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="Blank" colspan="2"></td>
	<%
	}
	%>
		</tr>
<%
}
%>
	</table>
	<!-- 特別休暇項目 -->
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="9">
				<span class="TitleTh">
					<label for="aryTxtTimesSpecialLeave,aryTxtTimesSpecialHour">
						<%=params.getName("Specially","Holiday","Item")%>
					</label>
				</span>
			</th>
		</tr>
	</table>
	<table class="FixTable">
<%
int specialLeaceLength = vo.getAryTxtTimesSpecialLeave().length;
for (int i = 0; i < specialLeaceLength; i++) {
	if (i % 2 == 0) {
%>
		<tr>
<%
}
%>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><%=HtmlUtility.escapeHTML(vo.getAryTxtTimesSpecialLeaveTitle(i))%><%=TimeNamingUtility.days(params)%></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "", "aryTxtTimesSpecialLeave", vo.getAryTxtTimesSpecialLeave(i), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
				
			</td>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><%=HtmlUtility.escapeHTML(vo.getAryTxtTimesSpecialLeaveTitle(i))%><%=PfNameUtility.time(params)%></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "", "aryTxtTimesSpecialHour", vo.getAryTxtTimesSpecialHour(i), vo.isViewMode())%>&nbsp;<%=PfNameUtility.time(params)%>
			</td>
<%
if (i == specialLeaceLength - 1 || i % 2 == 1) {
%>
			<td class="Blank" colspan="<%=specialLeaceLength % 2 == 0 ? 1 : 3%>"></td>
		</tr>
<%
}
}
%>
		<tr>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTotalSpecialHoliday"><%=params.getName("Specially","Holiday","SumTotal")%></label></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTotalSpecialHoliday", vo.getTxtTotalSpecialHoliday(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtSpecialHolidayHour"><%=params.getName("Specially","Holiday","Time")%></label></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtSpecialHolidayHour", vo.getTxtSpecialHolidayHour(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.time(params)%>
			</td>
			<td class="Blank" colspan="5"></td>
		</tr>
	</table>
	<!-- その他休暇項目 -->
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="9">
				<span class="TitleTh">
					<label for="aryTxtTimesOtherVacation,aryTxtTimesOtherVacationHour">
						<%=params.getName("Others","Holiday","Item")%>
					</label>
				</span>
			</th>
		</tr>
	</table>
	<table class="FixTable">
<%
int otherVacationLength = vo.getAryTxtTimesOtherVacation().length;
for (int i = 0; i < otherVacationLength; i++) {
	if (i % 2 == 0) {
%>
		<tr>
<%
}
%>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><%=HtmlUtility.escapeHTML(vo.getAryTxtTimesOtherVacationTitle(i))%><%=TimeNamingUtility.days(params)%></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "", "aryTxtTimesOtherVacation", vo.getAryTxtTimesOtherVacation(i), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
				
			</td>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><%=HtmlUtility.escapeHTML(vo.getAryTxtTimesOtherVacationTitle(i))%><%=PfNameUtility.time(params)%></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "", "aryTxtTimesOtherVacationHour", vo.getAryTxtTimesOtherVacationHour(i), vo.isViewMode())%>&nbsp;<%=PfNameUtility.time(params)%>
			</td>
<%
if (i == specialLeaceLength - 1 || i % 2 == 1) {
%>
			<td class="Blank" colspan="<%=specialLeaceLength % 2 == 0 ? 1 : 3%>"></td>
		</tr>
<%
}
}
%>
		<tr>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTotalOtherHoliday"><%=params.getName("Others","Holiday","SumTotal")%></label></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTotalOtherHoliday", vo.getTxtTotalOtherHoliday(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtOtherHolidayHour"><%=params.getName("Others","Holiday","Time")%></label></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtOtherHolidayHour", vo.getTxtOtherHolidayHour(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.time(params)%>
			</td>
			<td class="Blank" colspan="9"></td>
		</tr>
	</table>
	<!-- 欠勤項目 -->
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="12">
				<span class="TitleTh">
					<label for="aryTxtDeduction,aryTxtDeductionHour">
						<%=params.getName("Absence","Item")%>
					</label>
				</span>
			</th>
		</tr>
	</table>
	<table class="FixTable">
<%
int deductionLength = vo.getAryTxtDeduction().length;
for (int i = 0; i < deductionLength; i++) {
	if (i % 2 == 0) {
%>
		<tr>
<%
}
%>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><%=HtmlUtility.escapeHTML(vo.getAryTxtDeductionTitle(i))%><%=TimeNamingUtility.days(params)%></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "", "aryTxtDeduction", vo.getAryTxtDeduction(i), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
				
			</td>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><%=HtmlUtility.escapeHTML(vo.getAryTxtDeductionTitle(i))%><%=PfNameUtility.time(params)%></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "", "aryTxtDeductionHour", vo.getAryTxtDeductionHour(i), vo.isViewMode())%>&nbsp;<%=PfNameUtility.time(params)%>
			</td>
<%
if (i == specialLeaceLength - 1 || i % 2 == 1) {
%>
			<td class="Blank" colspan="<%=specialLeaceLength % 2 == 0 ? 1 : 3%>"></td>
		</tr>
<%
}
}
%>
		<tr>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtTotalDeduction"><%=params.getName("Absence","SumTotal")%></label></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTotalDeduction", vo.getTxtTotalDeduction(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
			<td class="TitleTd HolidayTitleTd"><%=HtmlUtility.getRequiredMark()%><label for="txtDeductionHour"><%=params.getName("Absence","Time")%></label></td>
			<td class="InputTd HolidayInputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtDeductionHour", vo.getTxtDeductionHour(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.time(params)%>
			</td>
			<td class="Blank" colspan="9"></td>
		</tr>
	</table>
	<!-- 時間外(60時間)項目 -->
	<table class="HeaderTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=params.getName("Time","Outside","FrontParentheses","No60","Time","BackParentheses","Item")%></span>
			</th>
		</tr>
	</table>
	<table class="FixTable" id="tbl">
		<tr>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Weekday","Time","Outside")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtWeekDayOverTimeHour", vo.getTxtWeekDayOverTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtWeekDayOverTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtWeekDayOverTimeMinute", vo.getTxtWeekDayOverTimeMinute(), vo.isViewMode())%>&nbsp;<label for="txtWeekDayOverTimeMinute"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Prescribed")%><%=params.getName("DayOff","Time","Outside")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Number3RequiredTextBox", "txtSpecificOverTimeHour", vo.getTxtSpecificOverTimeHour(), vo.isViewMode())%>&nbsp;<label for="txtSpecificOverTimeHour"><%=PfNameUtility.time(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtSpecificOverTimeMiunte", vo.getTxtSpecificOverTimeMiunte(), vo.isViewMode())%>&nbsp;<label for="txtSpecificOverTimeMiunte"><%=params.getName("Minutes")%></label>
			</td>
			<td class="TitleTd"><%=HtmlUtility.getRequiredMark()%><%=params.getName("Substitute","Holiday")%></td>
			<td class="InputTd">
				<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", "txtTimesAlternative", vo.getTxtTimesAlternative(), vo.isViewMode())%>&nbsp;<%=PfNameUtility.day(params)%>
			</td>
		</tr>
	</table>
</div>
<!-- 登録、再表示、集計一覧　ボタン -->
<div class="Button">
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, '', null, '<%= TotalTimeCardAction.CMD_INSERT %>')"><%= params.getName("Insert") %></button>
	<button type="button" id="btnReset" class="Name4Button" onclick="submitRegist(event, '', null, '<%= TotalTimeCardAction.CMD_RE_SHOW %>')"><%= params.getName("Refresh") %></button>
	<button type="button" id="btnCutOffResultList" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('null', 'null', 'null' , 'null'), '<%= TotalTimeListAction.CMD_RE_SHOW %>');" ><%= params.getName("Total","List") %></button>
</div>
