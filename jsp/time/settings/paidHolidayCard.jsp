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
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.settings.action.PaidHolidayCardAction"
import = "jp.mosp.time.settings.action.PaidHolidayListAction"
import = "jp.mosp.time.settings.vo.PaidHolidayCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PaidHolidayCardVo vo = (PaidHolidayCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable" id="tblBasic">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<jsp:include page="<%= TimeConst.PATH_SETTINGS_EDIT_HEADER_JSP %>" flush="false" />
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
				<td class="InputTd" id="tdActivateDate">
					<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>"/>&nbsp;<label for="txtEditActivateYear"><%= params.getName("Year") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>"/>&nbsp;<label for="txtEditActivateMonth"><%= params.getName("Month") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>"/>&nbsp;<label for="txtEditActivateDay"><%= params.getName("Day") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("PaidHolidayType") %></td>
				<td class="InputTd">
					<select class="Name3PullDown" id="pltPaidHolidayType" name="pltPaidHolidayType">
						<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_PAID_HOLIDAY_TYPE, vo.getPltPaidHolidayType(), false) %>
					</select>
				</td>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtPaidHolidayCode"><%= TimeNamingUtility.paidHolidayCodeAbbr(params) %></label></td>
				<td class="InputTd"><input type="text" class="Code10RequiredTextBox" id="txtPaidHolidayCode" name="txtPaidHolidayCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtPaidHolidayCode()) %>"/></td>
			</tr>
			<tr>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtPaidHolidayName"><%= TimeNamingUtility.paidHolidayNameAbbr(params) %></label></td>
				<td class="InputTd"><input type="text" class="Name15RequiredTextBox" id="txtPaidHolidayName" name="txtPaidHolidayName" value="<%= HtmlUtility.escapeHTML(vo.getTxtPaidHolidayName()) %>"/></td>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtPaidHolidayAbbr"><%= TimeNamingUtility.paidHolidayAbbreviationAbbr(params) %></label></td>
				<td class="InputTd"><input type="text" class="Byte6RequiredTextBox" id="txtPaidHolidayAbbr" name="txtPaidHolidayAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtPaidHolidayAbbr()) %>"/></td>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtWorkRatio"><%= params.getName("GoingWork","Rate") %></label></td>
				<td class="InputTd">
					<input type="text" class="Number3RequiredTextBox" id="txtWorkRatio" name="txtWorkRatio" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkRatio()) %>"/>&nbsp;<%= params.getName("Percent","Over") %>
				</td>
			</tr>
			<tr id="trTimelyUnitSet">
				<td class="TitleTd"><%= params.getName("HalfDay","Unit","Acquisition") %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltHalfDayUnit" name="pltHalfDayUnit">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltHalfDayUnit(), false) %>
					</select>
				</td>
				<td class="TitleTd"><span class="RequiredLabel"></span><%= params.getName("Time","Unit","Acquisition") %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltTimelyPaidHoliday" name="pltTimelyPaidHoliday">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltTimelyPaidHoliday(), false) %>
					</select>
				</td>
			</tr>
			<tr id="trTimePaidHolidaySet">
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("Time","Unit","Limit") %></td>
				<td class="InputTd">
					<select class="Number1PullDown" id="pltTimeAcquisitionLimitDays" name="pltTimeAcquisitionLimitDays">
						<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_TTIME_ACQUISITION_LIMIT_DAYS, vo.getPltTimeAcquisitionLimitDays(), false) %>
					</select>&nbsp;<%= params.getName("Day","Slash","Year")%>&nbsp;
					<select class="Number1PullDown" id="pltTimeAcquisitionLimitTimes" name="pltTimeAcquisitionLimitTimes">
						<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_TIME_ACQUISITION_LIMIT_TIMES, vo.getPltTimeAcquisitionLimitTimes(), false) %>
					</select>&nbsp;<%= params.getName("Time","Slash","Day") %>&nbsp;
				</td>
				<td class="TitleTd"><%= params.getName("Application","Time","Interval") %></td>
				<td class="InputTd">
					<select class="Number2PullDown" id="pltAppliTimeInterval" name="pltAppliTimeInterval">
						<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_APPLI_TIME_INTERVAL, vo.getPltAppliTimeInterval(), false) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= TimeNamingUtility.paidHolidayBroughtForwardAbbr(params) %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltMaxCarryOverYear" name="pltMaxCarryOverYear">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltMaxCarryOverYear(), false) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("Time","Unit","BroughtForward") %></td>
				<td class="InputTd">
					<select class="Name10PullDown" id="pltMaxCarryOverTimes" name="pltMaxCarryOverTimes">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_MAX_CARRY_OVER_TIMES, vo.getPltMaxCarryOverTimes(), false) %>
					</select>
				</td>
				<td class="TitleTd"><%= params.getName("DayOff", "GoingWork", "Handling") %></td>
				<td class="InputTd">
					<select class="Name5PullDown" id="pltWorkOnHolidayCalc" name="pltWorkOnHolidayCalc">
						<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_WORK_ON_HOLIDAY_CALC, vo.getPltWorkOnHolidayCalc(), false) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("Effectiveness","Slash","Inactivate") %></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ListTable" id="Proportionally">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="10">
					<span class="TitleTh"><%= params.getName("Giving") %></span>
				</th>
			</tr>
			<tr>
				<th rowspan="2" class="ListSelectTh"><div><%= params.getName("Week").substring(0, 1) %><%= params.getName("Prescribed") %></div><div><%= params.getName("Labor", "Time", "Num") %></div></th>
				<th class="ListSelectTh" rowspan="2"><div><%= params.getName("Week").substring(0, 1) %><%= params.getName("Prescribed") %></div><div><%= params.getName("Labor", "Days") %></div></th>
				<th class="ListSelectTh" rowspan="2"><div><%= params.getName("Year", "Prescribed") %></div><div><%= params.getName("Labor", "Days") %></div></th>
				<th class="ListSelectTh" colspan="7"><%= params.getName("ContinuousServiceTermsCountingFromTheEmploymentDay") %></th>
			</tr>
			<tr>
				<th class="ListSelectTh"><%= params.getName("0.5Years") %></th>
				<th class="ListSelectTh"><%= params.getName("1.5Years") %></th>
				<th class="ListSelectTh"><%= params.getName("2.5Years") %></th>
				<th class="ListSelectTh"><%= params.getName("3.5Years") %></th>
				<th class="ListSelectTh"><%= params.getName("4.5Years") %></th>
				<th class="ListSelectTh"><%= params.getName("5.5Years") %></th>
				<th class="ListSelectTh"><%= params.getName("6.5YearsOrMore") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd" id="td30HoursOrMore"><%= params.getName("30HoursOrMore") %></td>
				<td class="TitleTd"><%= params.getName("5DaysOrMore") %></td>
				<td class="TitleTd"><%= params.getName("217DaysOrMore") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtProportionallyFiveDaysOrMoreAndSixMonths" name="txtProportionallyFiveDaysOrMoreAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFiveDaysOrMoreAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFiveDaysOrMoreAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths" name="txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths" name="txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths" name="txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths" name="txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths" name="txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore" name="txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore()) %>">&nbsp;<label for="txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore"><%= params.getName("Day") %></label>
				</td>
			</tr>
			<tr>
				<td class="TitleTd" rowspan="4"><%= params.getName("LessThan30Hours") %></td>
				<td class="TitleTd"><%= params.getName("4Days") %></td>
				<td class="TitleTd"><%= params.getName("169To216Days") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyFourDaysAndSixMonths" id="txtProportionallyFourDaysAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFourDaysAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFourDaysAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyFourDaysAndOneYearAndSixMonths" id="txtProportionallyFourDaysAndOneYearAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFourDaysAndOneYearAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFourDaysAndOneYearAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyFourDaysAndTwoYearsAndSixMonths" id="txtProportionallyFourDaysAndTwoYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFourDaysAndTwoYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFourDaysAndTwoYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyFourDaysAndThreeYearsAndSixMonths" id="txtProportionallyFourDaysAndThreeYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFourDaysAndThreeYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFourDaysAndThreeYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyFourDaysAndFourYearsAndSixMonths" id="txtProportionallyFourDaysAndFourYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFourDaysAndFourYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFourDaysAndFourYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyFourDaysAndFiveYearsAndSixMonths" id="txtProportionallyFourDaysAndFiveYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFourDaysAndFiveYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyFourDaysAndFiveYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore" id="txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore()) %>">&nbsp;<label for="txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore"><%= params.getName("Day") %></label>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("3Days") %></td>
				<td class="TitleTd"><%= params.getName("121To168Days") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyThreeDaysAndSixMonths" id="txtProportionallyThreeDaysAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyThreeDaysAndSixMonths()) %>">&nbsp;<label for="txtProportionallyThreeDaysAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyThreeDaysAndOneYearAndSixMonths" id="txtProportionallyThreeDaysAndOneYearAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyThreeDaysAndOneYearAndSixMonths()) %>">&nbsp;<label for="txtProportionallyThreeDaysAndOneYearAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyThreeDaysAndTwoYearsAndSixMonths" id="txtProportionallyThreeDaysAndTwoYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyThreeDaysAndTwoYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyThreeDaysAndTwoYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyThreeDaysAndThreeYearsAndSixMonths" id="txtProportionallyThreeDaysAndThreeYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyThreeDaysAndThreeYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyThreeDaysAndThreeYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyThreeDaysAndFourYearsAndSixMonths" id="txtProportionallyThreeDaysAndFourYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyThreeDaysAndFourYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyThreeDaysAndFourYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyThreeDaysAndFiveYearsAndSixMonths" id="txtProportionallyThreeDaysAndFiveYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyThreeDaysAndFiveYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyThreeDaysAndFiveYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore" id="txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore()) %>">&nbsp;<label for="txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore"><%= params.getName("Day") %></label>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("2Days") %></td>
				<td class="TitleTd"><%= params.getName("73To120Days") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyTwoDaysAndSixMonths" id="txtProportionallyTwoDaysAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyTwoDaysAndSixMonths()) %>">&nbsp;<label for="txtProportionallyTwoDaysAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyTwoDaysAndOneYearAndSixMonths" id="txtProportionallyTwoDaysAndOneYearAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyTwoDaysAndOneYearAndSixMonths()) %>">&nbsp;<label for="txtProportionallyTwoDaysAndOneYearAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyTwoDaysAndTwoYearsAndSixMonths" id="txtProportionallyTwoDaysAndTwoYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyTwoDaysAndTwoYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyTwoDaysAndTwoYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyTwoDaysAndThreeYearsAndSixMonths" id="txtProportionallyTwoDaysAndThreeYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyTwoDaysAndThreeYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyTwoDaysAndThreeYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyTwoDaysAndFourYearsAndSixMonths" id="txtProportionallyTwoDaysAndFourYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyTwoDaysAndFourYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyTwoDaysAndFourYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyTwoDaysAndFiveYearsAndSixMonths" id="txtProportionallyTwoDaysAndFiveYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyTwoDaysAndFiveYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyTwoDaysAndFiveYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore" id="txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore()) %>">&nbsp;<label for="txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore"><%= params.getName("Day") %></label>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("1Day") %></td>
				<td class="TitleTd"><%= params.getName("48To72Days") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyOneDayAndSixMonths" id="txtProportionallyOneDayAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyOneDayAndSixMonths()) %>">&nbsp;<label for="txtProportionallyOneDayAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyOneDayAndOneYearAndSixMonths" id="txtProportionallyOneDayAndOneYearAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyOneDayAndOneYearAndSixMonths()) %>">&nbsp;<label for="txtProportionallyOneDayAndOneYearAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyOneDayAndTwoYearsAndSixMonths" id="txtProportionallyOneDayAndTwoYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyOneDayAndTwoYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyOneDayAndTwoYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyOneDayAndThreeYearsAndSixMonths" id="txtProportionallyOneDayAndThreeYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyOneDayAndThreeYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyOneDayAndThreeYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyOneDayAndFourYearsAndSixMonths" id="txtProportionallyOneDayAndFourYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyOneDayAndFourYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyOneDayAndFourYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyOneDayAndFiveYearsAndSixMonths" id="txtProportionallyOneDayAndFiveYearsAndSixMonths" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyOneDayAndFiveYearsAndSixMonths()) %>">&nbsp;<label for="txtProportionallyOneDayAndFiveYearsAndSixMonths"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" name="txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore" id="txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore" value="<%= HtmlUtility.escapeHTML(vo.getTxtProportionallyOneDayAndSixYearsAndSixMonthsOrMore()) %>">&nbsp;<label for="txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore"><%= params.getName("Day") %></label>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ListTable" id="FirstYearGiving">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="12">
					<span class="TitleTh"><%= params.getName("FirstYear","Giving") %></span>
				</th>
			</tr>
			<tr>
				<th class="ListSelectTh"><%= params.getName("Joined","Month") %></th>
				<th class="ListSelectTh"><%= params.getName("Giving","Month") %></th>
				<th class="ListSelectTh"><%= params.getName("GrantDate","Num") %></th>
				<th class="ListSelectTh"><%= params.getName("Use","TimeLimit") %></th>
				<th class="ListSelectTh"><%= params.getName("Joined","Month") %></th>
				<th class="ListSelectTh"><%= params.getName("Giving","Month") %></th>
				<th class="ListSelectTh"><%= params.getName("GrantDate","Num") %></th>
				<th class="ListSelectTh"><%= params.getName("Use","TimeLimit") %></th>
				<th class="ListSelectTh"><%= params.getName("Joined","Month") %></th>
				<th class="ListSelectTh"><%= params.getName("Giving","Month") %></th>
				<th class="ListSelectTh"><%= params.getName("GrantDate","Num") %></th>
				<th class="ListSelectTh"><%= params.getName("Use","TimeLimit") %></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><%= params.getName("January") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingJanuary" name="txtGivingTimingJanuary" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingJanuary()) %>"/>&nbsp;<label for="txtGivingTimingJanuary"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountJanuary" name="txtGivingAmountJanuary" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountJanuary()) %>"/>&nbsp;<label for="txtGivingAmountJanuary"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitJanuary" name="txtGivingLimitJanuary" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitJanuary()) %>"/>&nbsp;<label for="txtGivingLimitJanuary"><%= params.getName("Months") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("February") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingFebruary" name="txtGivingTimingFebruary" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingFebruary()) %>"/>&nbsp;<label for="txtGivingTimingFebruary"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountFebruary" name="txtGivingAmountFebruary" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountFebruary()) %>"/>&nbsp;<label for="txtGivingAmountFebruary"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitFebruary" name="txtGivingLimitFebruary" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitFebruary()) %>"/>&nbsp;<label for="txtGivingLimitFebruary"><%= params.getName("Months") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("March") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingMarch" name="txtGivingTimingMarch" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingMarch()) %>"/>&nbsp;<label for="txtGivingTimingMarch"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountMarch" name="txtGivingAmountMarch" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountMarch()) %>"/>&nbsp;<label for="txtGivingAmountMarch"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitMarch" name="txtGivingLimitMarch" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitMarch()) %>"/>&nbsp;<label for="txtGivingLimitMarch"><%= params.getName("Months") %></label>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("April") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingApril" name="txtGivingTimingApril" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingApril()) %>"/>&nbsp;<label for="txtGivingTimingApril"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountApril" name="txtGivingAmountApril" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountApril()) %>"/>&nbsp;<label for="txtGivingAmountApril"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitApril" name="txtGivingLimitApril" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitApril()) %>"/>&nbsp;<label for="txtGivingLimitApril"><%= params.getName("Months") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("May") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingMay" name="txtGivingTimingMay" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingMay()) %>"/>&nbsp;<label for="txtGivingTimingMay"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountMay" name="txtGivingAmountMay" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountMay()) %>"/>&nbsp;<label for="txtGivingAmountMay"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitMay" name="txtGivingLimitMay" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitMay()) %>"/>&nbsp;<label for="txtGivingLimitMay"><%= params.getName("Months") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("June") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingJune" name="txtGivingTimingJune" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingJune()) %>"/>&nbsp;<label for="txtGivingTimingJune"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountJune" name="txtGivingAmountJune" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountJune()) %>"/>&nbsp;<label for="txtGivingAmountJune"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitJune" name="txtGivingLimitJune" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitJune()) %>"/>&nbsp;<label for="txtGivingLimitJune"><%= params.getName("Months") %></label>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("July") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingJuly" name="txtGivingTimingJuly" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingJuly()) %>"/>&nbsp;<label for="txtGivingTimingJuly"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountJuly" name="txtGivingAmountJuly" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountJuly()) %>"/>&nbsp;<label for="txtGivingAmountJuly"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitJuly" name="txtGivingLimitJuly" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitJuly()) %>"/>&nbsp;<label for="txtGivingLimitJuly"><%= params.getName("Months") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("August") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingAugust" name="txtGivingTimingAugust" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingAugust()) %>"/>&nbsp;<label for="txtGivingTimingAugust"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountAugust" name="txtGivingAmountAugust" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountAugust()) %>"/>&nbsp;<label for="txtGivingAmountAugust"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitAugust" name="txtGivingLimitAugust" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitAugust()) %>"/>&nbsp;<label for="txtGivingLimitAugust"><%= params.getName("Months") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("September") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingSeptember" name="txtGivingTimingSeptember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingSeptember()) %>"/>&nbsp;<label for="txtGivingTimingSeptember"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountSeptember" name="txtGivingAmountSeptember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountSeptember()) %>"/>&nbsp;<label for="txtGivingAmountSeptember"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitSeptember" name="txtGivingLimitSeptember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitSeptember()) %>"/>&nbsp;<label for="txtGivingLimitSeptember"><%= params.getName("Months") %></label>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><%= params.getName("October") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingOctober" name="txtGivingTimingOctober" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingOctober()) %>"/>&nbsp;<label for="txtGivingTimingOctober"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountOctober" name="txtGivingAmountOctober" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountOctober()) %>"/>&nbsp;<label for="txtGivingAmountOctober"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitOctober" name="txtGivingLimitOctober" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitOctober()) %>"/>&nbsp;<label for="txtGivingLimitOctober"><%= params.getName("Months") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("November") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingNovember" name="txtGivingTimingNovember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingNovember()) %>"/>&nbsp;<label for="txtGivingTimingNovember"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountNovember" name="txtGivingAmountNovember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountNovember()) %>"/>&nbsp;<label for="txtGivingAmountNovember"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitNovember" name="txtGivingLimitNovember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitNovember()) %>"/>&nbsp;<label for="txtGivingLimitNovember"><%= params.getName("Months") %></label>
				</td>
				<td class="TitleTd"><%= params.getName("December") %></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingTimingDecember" name="txtGivingTimingDecember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingTimingDecember()) %>"/>&nbsp;<label for="txtGivingTimingDecember"><%= params.getName("Months","Later") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingAmountDecember" name="txtGivingAmountDecember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingAmountDecember()) %>"/>&nbsp;<label for="txtGivingAmountDecember"><%= params.getName("Day") %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtGivingLimitDecember" name="txtGivingLimitDecember" value="<%= HtmlUtility.escapeHTML(vo.getTxtGivingLimitDecember()) %>"/>&nbsp;<label for="txtGivingLimitDecember"><%= params.getName("Months") %></label>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ListTable" id="AutomaticGivingNorm">
		<tr>
			<th class="ListTableTh" colspan="13">
				<span class="TitleTh"><%= params.getName("Automatic","Giving","FrontWithCornerParentheses","Norm","Day","BackWithCornerParentheses") %></span>
				<span class="TableLabelSpan">
					<span class="RequiredLabel">*&nbsp;</span><%= params.getName("Norm") %><%= params.getName("Day") %>：
					<input type="text" class="Number2RequiredTextBox" id="txtPointDateMonth" name="txtPointDateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateMonth()) %>" />&nbsp;<label for="txtPointDateMonth"><%= params.getName("Month") %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtPointDateDay" name="txtPointDateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateDay()) %>" />&nbsp;<label for="txtPointDateDay"><%= params.getName("Day") %></label>
				</span>
			</th>
		</tr>
		<tr>
			<th class="ListSelectTh" id="thTimes"><%= params.getName("Norm","Day","Course","Frequency") %></th>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate1" name="txtTimesPointDate1" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate1()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate2" name="txtTimesPointDate2" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate2()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate3" name="txtTimesPointDate3" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate3()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate4" name="txtTimesPointDate4" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate4()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate5" name="txtTimesPointDate5" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate5()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate6" name="txtTimesPointDate6" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate6()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate7" name="txtTimesPointDate7" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate7()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate8" name="txtTimesPointDate8" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate8()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate9" name="txtTimesPointDate9" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate9()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate10" name="txtTimesPointDate10" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate10()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate11" name="txtTimesPointDate11" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate11()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
			<td class="TitleNumberTd">
				<input type="text" class="Number2TextBox" id="txtTimesPointDate12" name="txtTimesPointDate12" value="<%= HtmlUtility.escapeHTML(vo.getTxtTimesPointDate12()) %>" disabled />&nbsp;<%= params.getName("CountNum") %>
			</td>
		</tr>
		<tr>
			<th class="ListSelectTh" id="thDate"><%= params.getName("GrantDate") %><%= params.getName("Num") %></th>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount1" name="txtPointDateAmount1" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount1()) %>" disabled />&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount2" name="txtPointDateAmount2" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount2()) %>"/>&nbsp;<%= params.getName("Day") %>
				</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount3" name="txtPointDateAmount3" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount3()) %>"/>&nbsp;<%= params.getName("Day") %>
				</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount4" name="txtPointDateAmount4" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount4()) %>"/>&nbsp;<%= params.getName("Day") %>
				</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount5" name="txtPointDateAmount5" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount5()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount6" name="txtPointDateAmount6" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount6()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount7" name="txtPointDateAmount7" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount7()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount8" name="txtPointDateAmount8" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount8()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount9" name="txtPointDateAmount9" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount9()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount10" name="txtPointDateAmount10" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount10()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount11" name="txtPointDateAmount11" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount11()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="NumberTd">
				<input type="text" class="Number2TextBox" id="txtPointDateAmount12" name="txtPointDateAmount12" value="<%= HtmlUtility.escapeHTML(vo.getTxtPointDateAmount12()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="TitleNumberTd" colspan="13">
				<%= params.getName("SignStar","PaidHolidayCardMessage1") %>&nbsp;
				<%= HtmlUtility.getRequiredMark() %><input type="text" class="Number2RequiredTextBox" id="txtGeneralPointAmount" name="txtGeneralPointAmount" value="<%= HtmlUtility.escapeHTML(vo.getTxtGeneralPointAmount()) %>"/>&nbsp;<label for="txtSearchActivateYear"><%= params.getName("PaidHolidayCardMessage2") %></label>
			</td>
		</tr>
	</table>
	<table class="ListTable" id="AutomaticGivingJoined">
		<tr>
			<th class="ListTableTh" colspan="12">
				<span class="TitleTh"><%= params.getName("Automatic","Giving","FrontWithCornerParentheses","Joined","Day","BackWithCornerParentheses") %></span>
			</th>
		</tr>
		<tr>
			<th class="ListSelectTh" id="thNumber"></th>
			<th class="ListSelectTh" id="thTimes"><%= params.getName("Continuity","Work","NumberOfYears") %></th>
			<th class="ListSelectTh" id="thDate"><%= params.getName("GrantDate","Num") %></th>
			<th class="ListSelectTh" id="thNumber"></th>
			<th class="ListSelectTh" id="thTimes"><%= params.getName("Continuity","Work","NumberOfYears") %></th>
			<th class="ListSelectTh" id="thDate"><%= params.getName("GrantDate","Num") %></th>
			<th class="ListSelectTh" id="thNumber"></th>
			<th class="ListSelectTh" id="thTimes"><%= params.getName("Continuity","Work","NumberOfYears") %></th>
			<th class="ListSelectTh" id="thDate"><%= params.getName("GrantDate","Num") %></th>
			<th class="ListSelectTh" id="thNumber"></th>
			<th class="ListSelectTh" id="thTimes"><%= params.getName("Continuity","Work","NumberOfYears") %></th>
			<th class="ListSelectTh" id="thDate"><%= params.getName("GrantDate","Num") %></th>
		</tr>
		<tr>
			<td class="TitleNumberTd"><%= params.getName("No1") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear1" name="txtWorkYear1" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear1()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth1" name="txtWorkMonth1" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth1()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InpuTd"><input type="text" class="Number2TextBox" id="txtJoiningDateAmount1" name="txtJoiningDateAmount1" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount1()) %>"/>&nbsp;<%= params.getName("Day") %></td>
			<td class="TitleNumberTd"><%= params.getName("No2") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear2" name="txtWorkYear2" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear2()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth2" name="txtWorkMonth2" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth2()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount2" name="txtJoiningDateAmount2" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount2()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleNumberTd"><%= params.getName("No3") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear3" name="txtWorkYear3" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear3()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth3" name="txtWorkMonth3" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth3()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount3" name="txtJoiningDateAmount3" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount3()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleNumberTd"><%= params.getName("No4") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear4" name="txtWorkYear4" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear4()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth4" name="txtWorkMonth4" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth4()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount4" name="txtJoiningDateAmount4" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount4()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="TitleNumberTd"><%= params.getName("No5") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear5" name="txtWorkYear5" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear5()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth5" name="txtWorkMonth5" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth5()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd"><input type="text" class="Number2TextBox" id="txtJoiningDateAmount5" name="txtJoiningDateAmount5" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount5()) %>"/>&nbsp;<%= params.getName("Day") %></td>
			<td class="TitleNumberTd"><%= params.getName("No6") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear6" name="txtWorkYear6" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear6()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth6" name="txtWorkMonth6" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth6()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount6" name="txtJoiningDateAmount6" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount6()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleNumberTd"><%= params.getName("No7") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear7" name="txtWorkYear7" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear7()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth7" name="txtWorkMonth7" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth7()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount7" name="txtJoiningDateAmount7" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount7()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleNumberTd"><%= params.getName("No8") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear8" name="txtWorkYear8" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear8()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth8" name="txtWorkMonth8" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth8()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount8" name="txtJoiningDateAmount8" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount8()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="TitleNumberTd"><%= params.getName("No9") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear9" name="txtWorkYear9" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear9()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth9" name="txtWorkMonth9" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth9()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount9" name="txtJoiningDateAmount9" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount9()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleNumberTd"><%= params.getName("No10") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear10" name="txtWorkYear10" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear10()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth10" name="txtWorkMonth10" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth10()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount10" name="txtJoiningDateAmount10" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount10()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleNumberTd"><%= params.getName("No11") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear11" name="txtWorkYear11" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear11()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth11" name="txtWorkMonth11" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth11()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount11" name="txtJoiningDateAmount11" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount11()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
			<td class="TitleNumberTd"><%= params.getName("No12") %></td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtWorkYear12" name="txtWorkYear12" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkYear12()) %>" />&nbsp;<%= params.getName("Year") %>
				<input type="text" class="Number2TextBox" id="txtWorkMonth12" name="txtWorkMonth12" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkMonth12()) %>" />&nbsp;<%= params.getName("Months") %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2TextBox" id="txtJoiningDateAmount12" name="txtJoiningDateAmount12" value="<%= HtmlUtility.escapeHTML(vo.getTxtJoiningDateAmount12()) %>"/>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="InputTd" colspan="12">
				<%= params.getName("SignStar") %><%= params.getName("PaidHolidayCardMessage3") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtGeneralJoiningMonth" name="txtGeneralJoiningMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtGeneralJoiningMonth()) %>"/>&nbsp;<%= params.getName("Months","Every","In") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtGeneralJoiningAmount" name="txtGeneralJoiningAmount" value="<%= HtmlUtility.escapeHTML(vo.getTxtGeneralJoiningAmount()) %>"/>&nbsp;<%= params.getName("PaidHolidayCardMessage2") %>
			</td>
		</tr>
	</table>
	<table class="ListTable" id="tblStock">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6" id="thStock"><span class="TitleTh"><%= params.getName("Stock","Holiday","Set") %></span></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd">&nbsp;<span class="RequiredLabel">*&nbsp;</span><label for="txtStockYearAmount"><%= params.getName("Maximum","Years","Reserve","Days") %></label></td>
				<td class="InputTd">
					<input type="text" class="Number2RequiredTextBox" id="txtStockYearAmount" name="txtStockYearAmount" value="<%= HtmlUtility.escapeHTML(vo.getTxtStockYearAmount()) %>" />&nbsp;<%= params.getName("Day","Slash","Year") %>
				</td>
				<td class="TitleTd">&nbsp;<span class="RequiredLabel">*&nbsp;</span><label for="txtStockTotalAmount"><%= params.getName("Maximum","SumTotal","Reserve","Days") %></label></td>
				<td class="InputTd">
					<input type="text" class="Number3RequiredTextBox" id="txtStockTotalAmount" name="txtStockTotalAmount" value="<%= HtmlUtility.escapeHTML(vo.getTxtStockTotalAmount()) %>" />&nbsp;<%= params.getName("Day") %>
				</td>
				<td class="TitleTd">&nbsp;<span class="RequiredLabel">*&nbsp;</span><label for="txtStockLimitDate"><%= params.getName("Effectiveness","TimeLimit") %></label></td>
				<td class="InputTd">
					<%= params.getName("Giving","Kara") %>&nbsp;
					<input type="text" class="Number3RequiredTextBox" id="txtStockLimitDate" name="txtStockLimitDate" value="<%= HtmlUtility.escapeHTML(vo.getTxtStockLimitDate()) %>" />&nbsp;<%= params.getName("Months") %>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name6Button" onclick="submitRegist(event, '', checkPaidHolidayCard, '<%= PaidHolidayCardAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
	<button type="button" id="btnDelete" class="Name6Button" onclick="submitDelete(event, null, null, '<%= PaidHolidayCardAction.CMD_DELETE %>')"><%= params.getName("History","Delete") %></button>
<%
}
%>
	<button type="button" class="Name6Button" onclick="submitTransfer(event, 'divCard', null, null, '<%= PaidHolidayListAction.CMD_RE_SHOW %>')"><%= TimeNamingUtility.paidHolidaySettingListAbbr(params) %></button>
</div>
