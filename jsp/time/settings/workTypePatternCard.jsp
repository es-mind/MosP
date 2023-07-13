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
import = "jp.mosp.time.settings.action.WorkTypePatternCardAction"
import = "jp.mosp.time.settings.action.WorkTypePatternListAction"
import = "jp.mosp.time.settings.vo.WorkTypePatternCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
WorkTypePatternCardVo vo = (WorkTypePatternCardVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="ListTable">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<jsp:include page="<%=TimeConst.PATH_SETTINGS_EDIT_HEADER_JSP%>" flush="false" />
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=params.getName("ActivateDate")%></td>
				<td class="InputTd" id="tdEditActivateDate">
					<input type="text" name="txtEditActivateYear" id="txtEditActivateYear" class="Number4RequiredTextBox" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateYear())%>" />
					<label for="txtEditActivateYear"><%=params.getName("Year")%></label>
					<input type="text" name="txtEditActivateMonth" id=txtEditActivateMonth class="Number2RequiredTextBox" value="<%=HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth())%>" />
					<label for="txtEditActivateMonth"><%=params.getName("Month")%></label>
					<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'tdEditActivateDate', checkDateExtra, '<%=WorkTypePatternCardAction.CMD_SET_ACTIVATION_DATE%>')"><%=vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED) ? params.getName("Change") : params.getName("Decision")%></button>
				</td>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtPatternCode"><%=params.getName("Pattern")%><%=params.getName("Code")%></label></td>
				<td class="InputTd">
					<input type="text" class="Code10RequiredTextBox" id="txtPatternCode" name="txtPatternCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtPatternCode())%>" />
				</td>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtPatternName"><%=params.getName("Pattern")%><%=params.getName("Name")%></label></td>
				<td class="InputTd">
					<input type="text" class="Name15RequiredTextBox" id="txtPatternName" name="txtPatternName" value="<%=HtmlUtility.escapeHTML(vo.getTxtPatternName())%>" />
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtPatternAbbr"><%=params.getName("Pattern")%><%=params.getName("Abbreviation")%></label></td>
				<td class="InputTd">
					<input type="text" class="Byte6RequiredTextBox" id="txtPatternAbbr" name="txtPatternAbbr" value="<%=HtmlUtility.escapeHTML(vo.getTxtPatternAbbr())%>" />
				</td>
				<td class="TitleTd"><label for="pltEditInactivate"><%=params.getName("Effectiveness")%><%=params.getName("Slash")%><%=params.getName("Inactivate")%></label></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
						<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false)%>
					</select>
				</td>
				<td class="Blank" colspan="2"></td>
			</tr>
		</tbody>
	</table>
</div>
<div class="Select">
	<div class="SelectLeft">
		<div class="SelectLabel">
			<span><%=params.getName("Work")%><%=params.getName("Form")%></span>
		</div>
		<div id="divColumns" class="SelectMultiple">
			<select multiple="multiple" name="pltSelectTable" id="pltSelectTable"></select>
		</div>
	</div>
	<div class="SelectButton">
		<button type="button" class="Name6Button" id="btnAllSelect" onclick="selectAllItems('pltSelectTable', 'jsPltSelectSelected');"><%=params.getName("All")%><%=params.getName("Select")%>&nbsp;&gt;&gt;</button>
		<button type="button" class="Name6Button" id="btnSelect" onclick="selectItems('pltSelectTable', 'jsPltSelectSelected');"><%=params.getName("Select")%>&nbsp;&gt;</button>
		<button type="button" class="Name6Button" id="btnCancel" onclick="removeItems('pltSelectTable', 'jsPltSelectSelected', jsPltSelectTable);">&lt;&nbsp;<%=params.getName("Release")%></button>
		<button type="button" class="Name6Button" id="btnAllCancel" onclick="removeAllItems('pltSelectTable', 'jsPltSelectSelected', jsPltSelectTable);">&lt;&lt;&nbsp;<%=params.getName("All")%><%=params.getName("Release")%></button>
	</div>
	<div class="SelectRight">
		<div class="SelectLabel">
			<label for="jsPltSelectSelected"><%=PfNameUtility.selectItem(params)%></label>
		</div>
		<div class="SelectMultiple">
			<select multiple="multiple" name="jsPltSelectSelected" id="jsPltSelectSelected"></select>
		</div>
	</div>
	<div class="Button">
		<button type="button" class="Name4Button" id="btnRegist" onclick="submitRegist(event, 'divEdit', checkExtra, '<%= WorkTypePatternCardAction.CMD_REGIST %>');"><%= params.getName("Insert") %></button>
		<button type="button" class="Name4Button" id="btnDelete" onclick="submitDelete(event, null, null, '<%= WorkTypePatternCardAction.CMD_DELETE %>');"><%= params.getName("Delete") %></button>
		<button type="button" class="Name4Button" id="btnToList" onclick="submitTransfer(event, 'divEdit', null, null, '<%= WorkTypePatternListAction.CMD_RE_SHOW %>');"><%= params.getName("Information") %><%= params.getName("List") %></button>
	</div>
</div>
