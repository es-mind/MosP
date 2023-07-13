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
import = "jp.mosp.time.settings.action.WorkTypeCardAction"
import = "jp.mosp.time.settings.action.WorkTypeListAction"
import = "jp.mosp.time.settings.vo.WorkTypeCardVo"
import = "jp.mosp.time.utils.TimeNamingUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
WorkTypeCardVo vo = (WorkTypeCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable" id="tdBasic">
		<tr>
			<th class="ListTableTh" colspan="6">
				<jsp:include page="<%= TimeConst.PATH_SETTINGS_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear" name="txtEditActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear()) %>" />
				<label for="txtEditActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />
				<label for="txtEditActivateMonth"><%= params.getName("Month") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtWorkTypeCode"><%= TimeNamingUtility.workTypeCode(params) %></label></td>
			<td class="InputTd"><input type="text" class="Code10RequiredTextBox" id="txtWorkTypeCode" name="txtWorkTypeCode"  value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkTypeCode()) %>"/></td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtWorkTypeName"><%= params.getName("Work", "Form", "Name") %></label></td>
			<td class="InputTd"><input type="text" class="Name15RequiredTextBox" id="txtWorkTypeName" name="txtWorkTypeName"  value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkTypeName()) %>"/></td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtWorkTypeAbbr"><%= params.getName("Work", "Form", "Abbreviation") %></label></td>
			<td class="InputTd"><input type="text" class="Byte6RequiredTextBox" id="txtWorkTypeAbbr" name="txtWorkTypeAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkTypeAbbr()) %>"/></td>
			<td class="TitleTd"><label for="pltEditInactivate"><%= params.getName("Effectiveness", "Slash", "Inactivate") %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ListTable" id="tblWorkType">
		<tr>
			<td class="TitleTd">
				<span class="RequiredLabel">*&nbsp;</span><label for="txtWorkStartHour"><%= TimeNamingUtility.startWorkTime(params) %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtWorkStartHour" name="txtWorkStartHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkStartHour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtWorkStartMinute" name="txtWorkStartMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkStartMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd">
				<span class="RequiredLabel">*&nbsp;</span><label for="txtWorkEndHour"><%= TimeNamingUtility.endWorkTime(params) %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtWorkEndHour" name="txtWorkEndHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkEndHour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtWorkEndMinute" name="txtWorkEndMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkEndMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd">
				<label for="txtWorkTimeHour"><%= params.getName("Work", "Time") %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtWorkTimeHour" name="txtWorkTimeHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkTimeHour()) %>"/>&nbsp;<%= params.getName("Time") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtWorkTimeMinute" name="txtWorkTimeMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtWorkTimeMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd">
				<label for="txtRestTimeHour"><%= TimeNamingUtility.restTime(params) %></label>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtRestTimeHour" name="txtRestTimeHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestTimeHour()) %>"/>&nbsp;<%= params.getName("Time") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtRestTimeMinute" name="txtRestTimeMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestTimeMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtRestStart1Hour"><%= TimeNamingUtility.rest(params, 1) %></label>
			</td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Number2TextBox" id="txtRestStart1Hour" name="txtRestStart1Hour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestStart1Hour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestStart1Minute" name="txtRestStart1Minute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestStart1Minute()) %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestEnd1Hour" name="txtRestEnd1Hour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestEnd1Hour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestEnd1Minute" name="txtRestEnd1Minute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestEnd1Minute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd">
				<label for="txtRestStart2Hour"><%= TimeNamingUtility.rest(params, 2) %></label>
			</td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Number2TextBox" id="txtRestStart2Hour" name="txtRestStart2Hour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestStart2Hour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestStart2Minute" name="txtRestStart2Minute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestStart2Minute()) %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestEnd2Hour" name="txtRestEnd2Hour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestEnd2Hour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestEnd2Minute" name="txtRestEnd2Minute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestEnd2Minute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd">
				<label for="txtRestStart3Hour"><%= TimeNamingUtility.rest(params, 3) %></label>
			</td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Number2TextBox" id="txtRestStart3Hour" name="txtRestStart3Hour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestStart3Hour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestStart3Minute" name="txtRestStart3Minute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestStart3Minute()) %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestEnd3Hour" name="txtRestEnd3Hour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestEnd3Hour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox"  id="txtRestEnd3Minute" name="txtRestEnd3Minute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestEnd3Minute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd">
				<label for="txtRestStart4Hour"><%= TimeNamingUtility.rest(params, 4) %></label>
			</td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Number2TextBox" id="txtRestStart4Hour" name="txtRestStart4Hour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestStart4Hour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestStart4Minute" name="txtRestStart4Minute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestStart4Minute()) %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestEnd4Hour" name="txtRestEnd4Hour" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestEnd4Hour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtRestEnd4Minute" name="txtRestEnd4Minute" value="<%= HtmlUtility.escapeHTML(vo.getTxtRestEnd4Minute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd">
				<span class="RequiredLabel">*&nbsp;</span><label for="txtFrontStartHour"><%= TimeNamingUtility.amRest(params) %></label>
			</td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Number2RequiredTextBox" id="txtFrontStartHour" name="txtFrontStartHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtFrontStartHour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtFrontStartMinute" name="txtFrontStartMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtFrontStartMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtFrontEndHour" name="txtFrontEndHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtFrontEndHour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtFrontEndMinute" name="txtFrontEndMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtFrontEndMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
			<td class="TitleTd">
				<span class="RequiredLabel">*&nbsp;</span><label for="txtBackStartHour"><%= TimeNamingUtility.pmRest(params) %></label>
			</td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Number2RequiredTextBox" id="txtBackStartHour" name="txtBackStartHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtBackStartHour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtBackStartMinute" name="txtBackStartMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtBackStartMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;<%= params.getName("Wave") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtBackEndHour" name="txtBackEndHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtBackEndHour()) %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2RequiredTextBox"  id="txtBackEndMinute" name="txtBackEndMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtBackEndMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>
			</td>
		</tr>
		<tr>
			<td class="TitleTd">
				<span class="RequiredLabel">*&nbsp;</span><label for="txtOverPerHour"><%= TimeNamingUtility.overtimeRest(params) %></label>
			</td>
			<td class="InputTd" colspan="3">
				<input type="text" class="Number2RequiredTextBox" id="txtOverPerHour" name="txtOverPerHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtOverPerHour()) %>" />&nbsp;<label for="txtOverPerHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtOverPerMinute" name="txtOverPerMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtOverPerMinute()) %>"/>&nbsp;<label for="txtOverPerMinute"><%= params.getName("Minutes") %></label>&nbsp;<%= params.getName("Within") %>
				<input type="text" class="Number2RequiredTextBox" id="txtOverRestHour" name="txtOverRestHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtOverRestHour()) %>"/>&nbsp;<label for="txtOverRestHour"><%= params.getName("Time") %></label>&nbsp;
				<input type="text" class="Number2RequiredTextBox" id="txtOverRestMinute" name="txtOverRestMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtOverRestMinute()) %>"/>&nbsp;<label for="txtOverRestMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd">
				<span class="RequiredLabel">*&nbsp;</span><%= TimeNamingUtility.overtimeBeforeRest(params) %>
			</td>
			<td class="InputTd">
				<input type="text" class="Number2RequiredTextBox" id="txtOverBeforeHour" name="txtOverBeforeHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtOverBeforeHour()) %>" />&nbsp;<label for="txtOverBeforeHour"><%= params.getName("Time") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtOverBeforeMinute" name="txtOverBeforeMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtOverBeforeMinute()) %>" />&nbsp;<label for="txtOverBeforeMinute"><%= params.getName("Minutes") %></label>
			</td>
			<td class="TitleTd"><%= TimeNamingUtility.registActualOvertimeBeforeWork(params) %></td>
			<td class="InputTd">
				<select id="pltAutoBeforeOverWork" name="pltAutoBeforeOverWork">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltAutoBeforeOverWork(), false) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= TimeNamingUtility.halfHolidayRest(params) %></td>
			<td class="InputTd" id="tdHalfHoliday" colspan="7">
				<%= params.getName("TheFormerHalfDay") %>
				<input type="text" class="Number2TextBox" id="txtHalfRestHour" name="txtHalfRestHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtHalfRestHour()) %>" />&nbsp;<label for="txtOverBeforeHour"><%= params.getName("Hour") %></label>
				<input type="text" class="Number2TextBox" id="txtHalfRestMinute" name="txtHalfRestMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtHalfRestMinute()) %>" />&nbsp;<label for="txtOverBeforeMinute"><%= params.getName("Minutes") %></label>&nbsp;
				<%= params.getName("BeforeHalfWork") %>
				<input type="text" class="Number2TextBox" id="txtHalfRestStartHour" name="txtHalfRestStartHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtHalfRestStartHour()) %>" />&nbsp;<label for="txtOverPerHour"><%= params.getName("Hour") %></label>
				<input type="text" class="Number2TextBox" id="txtHalfRestStartMinute" name="txtHalfRestStartMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtHalfRestStartMinute()) %>"/>&nbsp;<label for="txtOverPerMinute"><%= params.getName("Minutes") %></label>
				<%= params.getName("Kara") %>
				<input type="text" class="Number2TextBox" id="txtHalfRestEndHour" name="txtHalfRestEndHour" value="<%= HtmlUtility.escapeHTML(vo.getTxtHalfRestEndHour()) %>" />&nbsp;<label for="txtOverPerHour"><%= params.getName("Hour") %></label>
				<input type="text" class="Number2TextBox" id="txtHalfRestEndMinute" name="txtHalfRestEndMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtHalfRestEndMinute()) %>"/>&nbsp;<label for="txtOverPerMinute"><%= params.getName("Minutes") %></label>
				<%= params.getName("UpToRestTime") %>
			</td>
		</tr>
<%
// 無給時短時間機能有効
if(params.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)){
%>
		<tr>
			<td class="TitleTd" id="tdDirectStartEnd">
				<%= params.getName("DirectStart", "Slash", "DirectEnd") %>
			</td>
			<td class="InputTd" colspan="3" id="tdDirectStartEndFlag">
				<input type="checkbox" class="CheckBox" id="ckbDirectStart" name="ckbDirectStart" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbDirectStart()) %>>&nbsp;<%= TimeNamingUtility.directStart(params) %>&nbsp;
				<input type="checkbox" class="CheckBox" id="ckbDirectEnd"   name="ckbDirectEnd"   value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getCkbDirectEnd())   %>>&nbsp;<%= TimeNamingUtility.directEnd(params)   %>&nbsp;
			</td>
			<td class="TitleTd" id="tdExcludeNightRest">
				<%= params.getName("ExcludeNightRest") %>
			</td>
			<td class="InputTd" id="tdMidnightRestExclusion">
				<select class="Name2PullDown" id="pltMidnightRestExclusion" name="pltMidnightRestExclusion">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltMidnightRestExclusion(), false) %>
				</select>
			</td>
			<td class="Blank" colspan="2" id="blank2"></td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdShortTimeNo1">
				<label for="txtShort1StartHour"><%= params.getName("ShortTime", "Time", "No1") %></label>
			</td>
			<td class="InputTd" colspan="3" id="tdTxtShort1Time">
				<input type="text" class="Number2TextBox" id="txtShort1StartHour"   name="txtShort1StartHour"   value="<%= HtmlUtility.escapeHTML(vo.getTxtShort1StartHour())   %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtShort1StartMinute" name="txtShort1StartMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtShort1StartMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;
				<%= params.getName("Wave") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtShort1EndHour"     name="txtShort1EndHour"     value="<%= HtmlUtility.escapeHTML(vo.getTxtShort1EndHour())     %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtShort1EndMinute"   name="txtShort1EndMinute"   value="<%= HtmlUtility.escapeHTML(vo.getTxtShort1EndMinute())   %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;
				<select class="Name2PullDown" id="pltShort1Type" name="pltShort1Type">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_SALARY_PAY_TYPE, vo.getPltShort1Type(), false) %>
				</select>
			</td>
			<td class="TitleTd" id="tdShortTimeNo2">
				<label for="txtShort2StartHour"><%= params.getName("ShortTime", "Time", "No2") %></label>
			</td>
			<td class="InputTd" colspan="3" id="tdTxtShort2Time">
				<input type="text" class="Number2TextBox" id="txtShort2StartHour"   name="txtShort2StartHour"   value="<%= HtmlUtility.escapeHTML(vo.getTxtShort2StartHour())   %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtShort2StartMinute" name="txtShort2StartMinute" value="<%= HtmlUtility.escapeHTML(vo.getTxtShort2StartMinute()) %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;
				<%= params.getName("Wave") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtShort2EndHour"     name="txtShort2EndHour"     value="<%= HtmlUtility.escapeHTML(vo.getTxtShort2EndHour())     %>"/>&nbsp;<%= params.getName("Hour") %>&nbsp;
				<input type="text" class="Number2TextBox" id="txtShort2EndMinute"   name="txtShort2EndMinute"   value="<%= HtmlUtility.escapeHTML(vo.getTxtShort2EndMinute())   %>"/>&nbsp;<%= params.getName("Minutes") %>&nbsp;
				<select class="Name2PullDown" id="pltShort2Type" name="pltShort2Type">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_SALARY_PAY_TYPE, vo.getPltShort2Type(), false) %>
				</select>
			</td>
		</tr>
<%
}
%>
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
	<button type="button" class="Name6Button" id="btnEdit" name="btnEdit" onclick="submitRegist(event, '', checkExtra, '<%= WorkTypeCardAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
	<button type="button" class="Name6Button" id="btnDelete" name="btnDelete" onclick="submitDelete(event, null, null, '<%= WorkTypeCardAction.CMD_DELETE %>')"><%= params.getName("History", "Delete") %></button>
<%
}
%>
	<button type="button" class="Name6Button" id="btnWorkTypeList" name="btnWorkTypeList" onclick="submitTransfer(event, null, null, null, '<%= WorkTypeListAction.CMD_RE_SHOW %>')"><%= params.getName("Work", "Form", "List") %></button>
</div>
