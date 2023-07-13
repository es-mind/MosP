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
buffer       = "1024kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.settings.action.ScheduleCardAction"
import = "jp.mosp.time.settings.action.ScheduleListAction"
import = "jp.mosp.time.settings.vo.ScheduleCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ScheduleCardVo vo = (ScheduleCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="InputTable">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<jsp:include page="<%=TimeConst.PATH_SETTINGS_EDIT_HEADER_JSP%>" flush="false" />
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><label for="pltFiscalYear"><%=params.getName("FiscalYear")%></label></td>
				<td class="InputTd">
					<select class="Number4PullDown" id="pltFiscalYear" name="pltFiscalYear">
						<%=HtmlUtility.getSelectOption(vo.getAryPltFiscalYear(), vo.getPltFiscalYear())%>
					</select>
					<%=params.getName("FiscalYear")%>&nbsp;
					<button type="button" class="Name2Button" id="btnActivateDateSet" name="btnActivateDateSet" onclick="submitTransfer(event,null, null, new Array('null', 'null', 'null' , 'null'), '<%=ScheduleCardAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</td>
				<td class="TitleTd"><label for="pltPattern"><%=params.getName("Pattern")%></label></td>
				<td class="InputTd">
					<select class="Name15PullDown" id="pltPattern" name="pltPattern">
						<%=HtmlUtility.getSelectOption(vo.getAryPltPattern(), vo.getPltPattern())%>
					</select>
					<button type="button" class="Name2Button" id="btnPatternSet" name="btnPatternSet" onclick="submitTransfer(event, 'divSchedule', null, new Array('null', 'null', 'null' , 'null'), '<%=ScheduleCardAction.CMD_SET_PATTERN%>')"><%=vo.getModePattern().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</td>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtScheduleCode"><%=params.getName("Calendar","Code")%></label></td>
				<td class="InputTd"><input type="text" class="Code10RequiredTextBox" id="txtScheduleCode" name="txtScheduleCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtScheduleCode())%>"/></td>
			</tr>
			<tr>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtScheduleName"><%=params.getName("Calendar","Name")%></label></td>
				<td class="InputTd"><input type="text" class="Name15RequiredTextBox" id="txtScheduleName" name="txtScheduleName" value="<%=HtmlUtility.escapeHTML(vo.getTxtScheduleName())%>"/></td>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtScheduleAbbr"><%=params.getName("Calendar","Abbreviation")%></label></td>
				<td class="InputTd"><input type="text" class="Byte6RequiredTextBox" id="txtScheduleAbbr" name="txtScheduleAbbr" value="<%=HtmlUtility.escapeHTML(vo.getTxtScheduleAbbr())%>"/></td>
				<td class="TitleTd"><label for="pltWorkTypeChange"><%=params.getName("Work","Form","Change")%></label></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltWorkTypeChange" name="pltWorkTypeChange">
						<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltWorkTypeChange(), false)%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="pltSearchInactivate"><%=params.getName("Effectiveness","Slash","Inactivate")%></label></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
						<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false)%>
					</select>
				</td>
				<td class="Blank" colspan="4"></td>
			</tr>
		</tbody>
	</table>
<%
if (vo.getJsCopyModeEdit() == ScheduleCardAction.CMD_REPLICATION_MODE) {
%>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" class="Name2Button" id="btnCopySet" name="btnCopySet" onclick="submitRegist(event, '', null, '<%=ScheduleCardAction.CMD_SET_COPY%>')"><%=params.getName("Replication")%></button>
			</td>
		</tr>
	</table>
<%
return;
}
%>
</div>
<%
if (vo.getModePattern() != PlatformConst.MODE_ACTIVATE_DATE_FIXED) {
	return;
}
%>
<div class="ListInfo" id="divListInfo">
	<table class="ListInfoTopTable">
		<tr>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorApril()%> id="btnApril" name="btnApril" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_APRIL%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("April")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorMay()%> id="btnMay" name="btnMay" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_MAY%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("May")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorJune()%> id="btnJune" name="btnJune" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_JUNE%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("June")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorJuly()%> id="btnJuly" name="btnJuly" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_JULY%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("July")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorAugust()%> id="btnAugust" name="btnAugust" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_AUGUST%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("August")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorSeptember()%> id="btnSeptember" name="btnSeptember" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_SEPTEMBER%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("September")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorOctorber()%> id="btnOctorber" name="btnOctorber" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_OCTOBER%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("October")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorNovember()%> id="btnNovember" name="btnNovember" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_NOVEMBER%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("November")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorDecember()%> id="btnDecember" name="btnDecember" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_DECEMBER%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("December")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorJanuary()%> id="btnJanuary" name="btnJanuary" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_JANUARY%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("January")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorFebruary()%> id="btnFebruary" name="btnFebruary" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_FEBRUARY%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("February")%></button>
			</td>
			<td>
				<button type="button" class="Name2Button" <%=vo.getButtonBackColorMarch()%> id="btnMarch" name="btnMarch" onclick="submitTransfer(event, null, null, new Array('<%=TimeConst.PRM_TRANSFERRED_MONTH%>', '<%=TimeConst.CODE_DISPLAY_MARCH%>'), '<%=ScheduleCardAction.CMD_MONTH_SWITCH%>');"><%=params.getName("March")%></button>
			</td>
		</tr>
	</table>
</div>
<div class="List" id="divEdit">
<%
if (vo.getPltWorkTypeMonth().length > 1) {
%>
	<table class="InputTable" id="tblWorkTypeSelect">
		<tr>
			<td class="ListTableTh"><span class="TitleTh"><%=params.getName("Work","Form","Given")%></span></td>
			<td class="InputTd" id="">
				<select class="Name25PullDown" id="pltWorkType" name="pltWorkType">
					<%=HtmlUtility.getSelectOption(vo.getAryPltWorkType(), vo.getPltWorkType())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleInputTd">
				<input type="radio" class="RadioButton" name="radioSelect" id="radioWeek" value="<%=HtmlUtility.escapeHTML(vo.getRadioWeek())%>"/>&nbsp;<%=params.getName("DayOfTheWeek","Given")%>
			</td>
			<td class="InputTd">
				<input type="checkbox" class="CheckBox" id="ckbMonday" name="ckbMonday">&nbsp;<span><%=params.getName("Monday","Day")%></span> 
				<input type="checkbox" class="CheckBox" id="ckbTuesday" name="ckbTuesday">&nbsp;<%=params.getName("Tuesday","Day")%> 
				<input type="checkbox" class="CheckBox" id="ckbWednesday" name="ckbWednesday">&nbsp;<%=params.getName("Wednesday","Day")%> 
				<input type="checkbox" class="CheckBox" id="ckbThursday" name="ckbThursday">&nbsp;<%=params.getName("Thursday","Day")%> 
				<input type="checkbox" class="CheckBox" id="ckbFriday" name="ckbFriday">&nbsp;<%=params.getName("Friday","Day")%> 
				<input type="checkbox" class="CheckBox" id="ckbSatureday" name="ckbSatureday">&nbsp;<%=params.getName("Saturday","Day")%> 
				<input type="checkbox" class="CheckBox" id="ckbSunday" name="ckbSunday">&nbsp;<%=params.getName("Sunday","Day")%> 
				<input type="checkbox" class="CheckBox" id="ckbNationalHoliday" name="ckbNationalHoliday">&nbsp;<%=params.getName("NationalHoliday")%>
			</td>
		</tr>
		<tr>
			<td class="TitleInputTd">
				<input type="radio" class="RadioButton" name="radioSelect" id="radioPeriod" value="<%=HtmlUtility.escapeHTML(vo.getRadioPeriod())%>"/>&nbsp;<%=PfNameUtility.term(params)%><%= params.getName("Given") %>
			</td>
			<td class="InputTd">
				<select id="pltScheduleStartDay" name="pltScheduleStartDay">
					<%= HtmlUtility.getSelectOption(vo.getAryPltScheduleDay(), vo.getPltScheduleStartDay()) %>
				</select>&nbsp;<%= params.getName("Day") %>&nbsp;<%= params.getName("Wave") %>&nbsp;
				<select id="pltScheduleEndDay" name="pltScheduleEndDay">
					<%= HtmlUtility.getSelectOption(vo.getAryPltScheduleDay(), vo.getPltScheduleEndDay()) %>
				</select>&nbsp;<%= params.getName("Day") %>
			</td>
		</tr>
		<tr>
			<td class="TitleInputTd" colspan="2">
				<input type="radio" class="RadioButton" name="radioSelect" id="radioCheck" value="<%= HtmlUtility.escapeHTML(vo.getRadioCheck()) %>"/>&nbsp;<%= params.getName("ScheduleCardMessage2") %>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name4Button" onclick="submitTransfer(event, null, null, null, '<%= ScheduleCardAction.CMD_REFLECTION %>');"><%= params.getName("This","Month","Reflection") %></button>
			</td>
			<td class="ButtonTd" id="">
				<button type="button" id="btnAllReflection" class="Name4Button" onclick="submitRegistExtra(event, '', null, null, '<%= ScheduleCardAction.CMD_ALL_REFLECTION %>');"><%= params.getName("FiscalYear","Insert") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="List" id="divSchedule">
	<table class="LeftListTable" id="tblSchedule">
		<thead>
			<tr>
				<th class="ListSelectTh" colspan="14"><span class="TitleTh"><%= vo.getLblFiscalYear() %></span></th>
			</tr>
		</thead>
	</table>
	<table class="ScheduleFrontTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thDay"><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thForm"><%= params.getName("Form") %></th>
				<th class="ListSelectTh" id="thGoingWork"><%= params.getName("GoingWork") %></th>
				<th class="ListSelectTh" id="thRetireOffice"><%= params.getName("RetireOffice") %></th>
				<th class="ListSelectTh" id="thWorkTime"><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="thRemarks"><%= params.getName("Remarks") %></th>
				<th class="ListSelectTh" id="thSelect"></th>
			</tr>
		</thead>
<% for (int i = 0; i < 16; i++) { %>
		<tbody>
			<tr>
			<% if (i < 15) { %>
				<td class="ListSelectTd" id=""><%= vo.getAryLblMonth()[i] %></td>
				<td class="ListSelectTd" id="">
					<select class="Name4PullDown" id="pltWorkTypeMonth" name="pltWorkTypeMonth">
						<%= HtmlUtility.getSelectOption(vo.getAryPltWorkTypeMonth(), vo.getPltWorkTypeMonth()[i]) %>
					</select>
				</td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblStartMonth()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEndMonth()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkMonth()[i]) %></td>
				<td class="ListSelectTd">
					<input type="text" class="Name10TextBox" id="txtRemarkMonth" name="txtRemarkMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtRemarkMonth()[i]) %>" />
				</td>
				<td class="ListSelectTd">
					<input type="checkbox" class="SelectCheckBox" name="ckbSelect" value="<%= vo.getAryCkbRecordId()[i] %>" <%= HtmlUtility.getChecked(vo.getAryCkbRecordId()[i], vo.getCkbSelect()) %> />
				</td>
			<% } else { %>
				<td class="Blank" colspan="7"></td>
			<% } %>
			</tr>
<% } %>
		</tbody>
	</table>
	<table class="ScheduleBackTable" id="list2">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thDay"><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thForm"><%= params.getName("Form") %></th>
				<th class="ListSelectTh" id="thGoingWork"><%= params.getName("GoingWork") %></th>
				<th class="ListSelectTh" id="thRetireOffice"><%= params.getName("RetireOffice") %></th>
				<th class="ListSelectTh" id="thWorkTime"><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="thRemarks"><%= params.getName("Remarks") %></th>
				<th class="ListSelectTh" id="thSelect">
					<input type="checkbox" class="" id="ckbSelect" onclick="doAllBoxChecked(this);">
				</th>
			</tr>
		</thead>
<% for (int i = 0; i < 16; i++) {
	if (i + 15 < vo.getPltWorkTypeMonth().length) { %>
		<tbody>
			<tr>
				<td class="ListSelectTd" id=""><%= vo.getAryLblMonth()[i + 15] %></td>
				<td class="ListSelectTd" id="">
					<select class="Name4PullDown" id="pltWorkTypeMonth" name="pltWorkTypeMonth">
						<%= HtmlUtility.getSelectOption(vo.getAryPltWorkTypeMonth(), vo.getPltWorkTypeMonth()[i + 15]) %>
					</select>
				</td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblStartMonth()[i + 15]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEndMonth()[i + 15]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkMonth()[i + 15]) %></td>
				<td class="ListSelectTd">
					<input type="text" class="Name10TextBox" id="txtRemarkMonth" name="txtRemarkMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtRemarkMonth()[i + 15]) %>" />
				</td>
				<td class="ListSelectTd">
					<input type="checkbox" class="SelectCheckBox" name="ckbSelect" value="<%= vo.getAryCkbRecordId()[i + 15] %>" <%= HtmlUtility.getChecked(vo.getAryCkbRecordId()[i + 15], vo.getCkbSelect()) %> />
				</td>
<% 	} else { %>
				<td class="Blank" colspan="7"></td>
<% 	} %>
			</tr>
<% } %>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name6Button" id="btnRegist" onclick="submitRegist(event, '', null, '<%= ScheduleCardAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
<% if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) { %>
	<button type="button" class="Name6Button" id="btnDelete" onclick="submitDelete(event, null, null, '<%= ScheduleCardAction.CMD_DELETE %>')"><%= params.getName("History","Delete") %></button>
<% } %>
	<button type="button" class="Name6Button" id="btnScheduleManagementList" onclick="submitTransfer(event, null, null, null, '<%= ScheduleListAction.CMD_RE_SHOW %>')"><%= params.getName("Calendar","List") %></button>
<%
}
%>
</div>
