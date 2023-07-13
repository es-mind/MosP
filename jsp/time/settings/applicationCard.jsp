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
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.settings.action.ApplicationCardAction"
import = "jp.mosp.time.settings.action.ApplicationListAction"
import = "jp.mosp.time.settings.vo.ApplicationCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ApplicationCardVo vo = (ApplicationCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<jsp:include page="<%=TimeConst.PATH_SETTINGS_EDIT_HEADER_JSP%>" flush="false" />
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=PfNameUtility.activateDate(params)%></td>
			<td class="InputTd" id="tdActivateDate">
				<%=HtmlUtility.getTextboxTag("Number4RequiredTextBox", "txtEditActivateYear" , "txtEditActivateYear" , vo.getTxtEditActivateYear (), false)%>&nbsp;<label for="txtSearchActivateYear" ><%=PfNameUtility.year (params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtEditActivateMonth", "txtEditActivateMonth", vo.getTxtEditActivateMonth(), false)%>&nbsp;<label for="txtSearchActivateMonth"><%=PfNameUtility.month(params)%></label>
				<%=HtmlUtility.getTextboxTag("Number2RequiredTextBox", "txtEditActivateDay"  , "txtEditActivateDay"  , vo.getTxtEditActivateDay  (), false)%>&nbsp;<label for="txtSearchActivateDay"  ><%=PfNameUtility.day  (params)%></label>
				<button type="button" class="Name2Button" id="btnEditActivateDate" onclick="submitForm(event, 'tdActivateDate', null, '<%=ApplicationCardAction.CMD_SET_ACTIVATION_DATE%>')"><%=PfNameUtility.activeteDateButton(params, vo.getModeActivateDate())%></button>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditApplicationCode"><%= params.getName("Set") %><%= params.getName("Apply") %><%= params.getName("Code") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Code10RequiredTextBox" id="txtEditApplicationCode" name="txtEditApplicationCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditApplicationCode()) %>" />
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditApplicationName"><%= params.getName("Set") %><%= params.getName("Apply") %><%= params.getName("Name") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name15RequiredTextBox" id="txtEditApplicationName" name="txtEditApplicationName" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditApplicationName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditApplicationAbbr"><%= params.getName("Set") %><%= params.getName("Apply") %><%= params.getName("Abbreviation") %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Byte6RequiredTextBox" id="txtEditApplicationAbbr" name="txtEditApplicationAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditApplicationAbbr()) %>" />
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="pltEditInactivate"><%= params.getName("Effectiveness") %><%= params.getName("Slash") %><%= params.getName("Inactivate") %></label></span></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="5">
				<span class="TitleTh"><%= params.getName("Set") %><%= params.getName("Apply") %><%= params.getName("Range") %></span><a></a>
			</th>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRadio" rowspan="2">
				<input type="radio" class="RadioButton" name="radApplicationType" id="radMaster" value="<%= PlatformConst.APPLICATION_TYPE_MASTER %>" <%= HtmlUtility.getChecked(vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_MASTER)) %> />
			</td>
			<td class="TitleTd" id="tdMasterTitle1"><%= params.getName("WorkPlace") %><%= params.getName("Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEditWorkPlaceMaster" name="pltEditWorkPlaceMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditWorkPlaceMaster(), vo.getPltEditWorkPlaceMaster()) %>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%= params.getName("EmploymentContract") %><%= params.getName("Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEditEmploymentMaster" name="pltEditEmploymentMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditEmploymentMaster(), vo.getPltEditEmploymentMaster()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdMasterTitle1"><%= params.getName("Section") %><%= params.getName("Name") %></td>
			<td class="InputTd">
				<select class="SectionNamePullDown" id="pltEditSectionMaster" name="pltEditSectionMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditSectionMaster(), vo.getPltEditSectionMaster()) %>
				</select>
			</td>
			<td class="TitleTd" id="tdMasterTitle2"><%= params.getName("Position") %><%= params.getName("Name") %></td>
			<td class="InputTd">
				<select class="Name15PullDown" id="pltEditPositionMaster" name="pltEditPositionMaster">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditPositionMaster(), vo.getPltEditPositionMaster()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd" id="tdRadio" rowspan="2">
				<input type="radio" class="RadioButton" name="radApplicationType" id="radEmployeeCode" value="<%= PlatformConst.APPLICATION_TYPE_PERSON %>" <%= HtmlUtility.getChecked(vo.getRadApplicationType().equals(PlatformConst.APPLICATION_TYPE_PERSON)) %> />
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><span><label for="txtEditEmployeeCode"><%= params.getName("Personal") + params.getName("FrontParentheses") + params.getName("InputCsv") + params.getName("BackParentheses") %></label></span></td>
			<td class="InputTd" colspan="3">
				<input type="text" class="CodeCsvTextBox" id="txtEditEmployeeCode" name="txtEditEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEmployeeCode()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("Apply") %><%= params.getName("TargetPerson") %><%= params.getName("FullName") %></td>
			<td class="TitleInputTd" colspan="3" id="lblEmployeeName">
				<%= HtmlUtility.escapeHTML(vo.getLblEmployeeName()) %>
			</td>
		</tr>
	</table>
	<table class="ListTable">
		<tr>
			<th class="ListTableTh" colspan="3">
				<span class="TitleTh"><%= params.getName("Set") %><%= params.getName("Information") %><%= params.getName("Select") %></span><a></a>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("WorkManage") %><%= params.getName("Set") %><%= params.getName("Name") %></td>
			<td class="TitleTd"><%= params.getName("Calendar") %><%= params.getName("Name") %></td>
			<td class="TitleTd"><%= params.getName("PaidVacation") %><%= params.getName("Set") %><%= params.getName("Name") %></td>
		</tr>
		<tr>
			<td class="SelectTd">
				<select class="Name15PullDown" id="pltEditWorkSetting" name="pltEditWorkSetting">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditWorkSetting(), vo.getPltEditWorkSetting()) %>
				</select>
			</td>
			<td class="SelectTd">
				<select class="Name15PullDown" id="pltEditSchedule" name="pltEditSchedule">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditSchedule(), vo.getPltEditSchedule()) %>
				</select>
			</td>
			<td class="SelectTd">
				<select class="Name15PullDown" id="pltEditPaidHoliday" name="pltEditPaidHoliday">
					<%= HtmlUtility.getSelectOption(vo.getAryPltEditPaidHoliday(), vo.getPltEditPaidHoliday()) %>
				</select>
			</td>
		</tr>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name4Button" id="btnRegist" name="btnRegist" onclick="submitRegist(event, 'divEdit', checkEmployeeCode, '<%= ApplicationCardAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
	<button type="button" class="Name4Button" id="btnDelete" name="btnDelete" onclick="submitDelete(event, null, null, '<%= ApplicationCardAction.CMD_DELETE %>')"><%= params.getName("History") %><%= params.getName("Delete") %></button>
	<button type="button" class="Name4Button" id="btnApplicationList" name="btnApplicationList" onclick="submitTransfer(event, 'divEdit', null, null, '<%= ApplicationListAction.CMD_RE_SEARCH %>')"><%= params.getName("Apply") %><%= params.getName("List") %></button>
</div>
