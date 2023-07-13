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
language = "java"
pageEncoding = "UTF-8"
buffer = "256kb"
autoFlush = "false"
errorPage = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.EmployeeCodeComparator"
import = "jp.mosp.platform.comparator.base.EmployeeNameComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.comparator.system.AccountMasterRoleCodeComparator"
import = "jp.mosp.platform.comparator.system.AccountMasterUserIdComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.system.action.AccountMasterAction"
import = "jp.mosp.platform.system.constant.PlatformSystemConst"
import = "jp.mosp.platform.system.vo.AccountMasterVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
AccountMasterVo vo = (AccountMasterVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="OverInputTable">
		<tr>
			<th class="EditTableTh" colspan="4">
				<jsp:include page="<%= PlatformSystemConst.PATH_SYSTEM_EDIT_HEADER_JSP %>" flush="false" />
			</th>
		</tr>
	</table>
	<table class="UnderInputTable">
		<tr id="trEmployee">
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span><%= PfNameUtility.activateDate(params) %></span></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtEditActivateYear"  name="txtEditActivateYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateYear())  %>" />&nbsp;<label for="txtEditActivateYear" ><%= PfNameUtility.year(params)  %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateMonth" name="txtEditActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateMonth()) %>" />&nbsp;<label for="txtEditActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay"   name="txtEditActivateDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay())   %>" />&nbsp;<label for="txtEditActivateDay"  ><%= PfNameUtility.day(params)   %></label>
			</td>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span><label for="txtEditEmployeeCode"><%= PfNameUtility.employeeCode(params) %></label></span></td>
			<td class="InputTd" id="tdEmployeeCodeNoBoder">
				<input type="text" class="Code10RequiredTextBox" id="txtEditEmployeeCode" name="txtEditEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditEmployeeCode()) %>"/>&nbsp;
				<button type="button" class="Name2Button" id="btnEditEmployeeCode" onclick="submitForm(event, 'trEmployee', null, '<%= AccountMasterAction.CMD_SET_EMPLOYEE %>')"><%= PfNameUtility.activeteDateButton(params, vo.getModeEditEmployee()) %></button>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="pltEditRoleCode"><%= PfNameUtility.role(params) %></label></span></td>
			<td class="InputTd">
				<div>
					<select class="Name15PullDown" id="pltEditRoleCode" name="pltEditRoleCode">
						<%= HtmlUtility.getSelectOption(vo.getAryPltEditRoleCode(), vo.getPltEditRoleCode()) %>
					</select>
				</div>
			</td>
			<td class="TitleTd"><span><%= PfNameUtility.employeeName(params) %></span></td>
			<td class="InputTd" id="tdEmployeeNameNoBoder" ><%= HtmlUtility.escapeHTML(vo.getLblEditEmployeeName()) %></td>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span><label for="txtEditUserId"><%= PfNameUtility.userId(params) %></label></span></td>
			<td class="InputTd">
				<input type="text" class="UserId50RequiredTextBox" id="txtEditUserId" name="txtEditUserId" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditUserId()) %>" />
			</td>
			<td class="TitleTd"><span><label for="pltEditInactivate"><%= PfNameUtility.inactivate(params) %></label></span></td>
			<td class="InputTd">
				<select class="InactivateRequiredPullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnRegist" onclick="submitRegist(event, 'divEdit', null, '<%= AccountMasterAction.CMD_REGIST %>')"><%= PfNameUtility.insert(params) %></button>
			</td>
		</tr>
	</table>
<%
if (vo.getAryRoleType().length > 0) {
%>
	<table class="OverInputTable">
		<tr>
			<th class="EditTableTh" colspan="4">
				<label class="TitleTh" for="pltExtraRoles"><%=PfNameUtility.extraRole(params)%></label>
			</th>
		</tr>
	</table>
	<table class="UnderInputTable">
		<tr>
<%
for (int i = 0; i < vo.getAryRoleType().length; i++) {
%>
			<td class="TitleTd RoleTitleTd"><%=vo.getAryRoleTypeName(i)%></td>
			<td class="InputTd RoleInputTd">
				<%=HtmlUtility.getSelectTag("Name15PullDown", "", "pltExtraRoles", vo.getPltExtraRoles(i), vo.getAryPltExtraRoles(vo.getAryRoleType(i)), true, false)%>
			</td>
<%
if (i == vo.getAryRoleType().length - 1) {
%>
		</tr>
<%
} else if (i % 2 == 1) {
%>
		</tr>
		<tr>
<%
}
	}
%>
	</table>
<%
}
%>
</div>
<div class="List" id="divSearch">
	<table class="InputTable" id="tblBaseSettingSearch">
		<tr>
			<th class="ListTableTh" colspan="6"><span class="TitleTh"><%= PfNameUtility.search(params) %></span></th>
		</tr>
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span><%= PfNameUtility.activateDate(params) %></span></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear"  name="txtSearchActivateYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())  %>" />&nbsp;<label for="txtSearchActivateYear" ><%= PfNameUtility.year(params)  %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />&nbsp;<label for="txtSearchActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay"   name="txtSearchActivateDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay())   %>" />&nbsp;<label for="txtSearchActivateDay"  ><%= PfNameUtility.day(params)   %></label>
			</td>
			<td class="TitleTd"><span><label for="pltSearchEmployeeCode"><%= PfNameUtility.employeeCode(params) %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="pltSearchEmployeeCode" name="pltSearchEmployeeCode" value="<%= HtmlUtility.escapeHTML(vo.getPltSearchEmployeeCode()) %>" />
			</td>
			<td class="TitleTd"><span><label for="txtSearchEmployeeName"><%= PfNameUtility.employeeName(params) %></label></span></td>
			<td class="InputTd">
				<input type="text" class="Name12TextBox" id="pltSearchEmployeeName" name="pltSearchEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getPltSearchEmployeeName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span><label for="txtSearchUserId"><%= PfNameUtility.userId(params) %></label></span></td>
			<td class="InputTd" colspan="3">
				<input type="text" class="UserId50TextBox" id="txtSearchUserId" name="txtSearchUserId" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchUserId()) %>" />
			</td>
			<td class="TitleTd"><span><label for="pltSearchInactivate"><%= PfNameUtility.inactivate(params) %></label></span></td>
			<td class="InputTd">
				<select class="InactivatePullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= AccountMasterAction.CMD_SEARCH %>')"><%= PfNameUtility.search(params) %></button>
			</td>
		</tr>
	</table>
</div>
<div class="ListInfo" id="divListInfo">
	<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
</div>
<div class="FixList" id="divList">
	<table class="LeftListTable" id="list">
		<tr>
			<th class="ListSelectTh" id="thButton"></th>
			<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator.class.getName()        %>'), '<%= AccountMasterAction.CMD_SORT %>');"><%= PfNameUtility.activateDate(params)   %><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeCodeComparator.class.getName()        %>'), '<%= AccountMasterAction.CMD_SORT %>');"><%= PfNameUtility.employeeCode(params)   %><%= PlatformUtility.getSortMark(EmployeeCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EmployeeNameComparator.class.getName()        %>'), '<%= AccountMasterAction.CMD_SORT %>');"><%= PfNameUtility.employeeName(params)   %><%= PlatformUtility.getSortMark(EmployeeNameComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thUserId"       onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= AccountMasterUserIdComparator.class.getName() %>'), '<%= AccountMasterAction.CMD_SORT %>');"><%= PfNameUtility.userId(params)         %><%= PlatformUtility.getSortMark(AccountMasterUserIdComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="<%= vo.getAryRoleType().length > 0 ? "thExtraRoleCode" : "thRoleCode" %>" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= AccountMasterRoleCodeComparator.class.getName() %>'), '<%= AccountMasterAction.CMD_SORT %>');"><%= PfNameUtility.role(params) %><%= PlatformUtility.getSortMark(AccountMasterRoleCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thInactivate"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName()          %>'), '<%= AccountMasterAction.CMD_SORT %>');"><%= PfNameUtility.inactivateAbbr(params) %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params)   %></th>
			<th class="ListSelectTh" id="thSelect">
<%
if (!vo.getList().isEmpty()) {
%>
				<input type="checkbox" onclick="doAllBoxChecked(this);" />
<%
}
%>
			</th>
		</tr>
<%
for (int i = 0; i < vo.getAryLblEmployeeCode().length; i++) {
%>
		<tr>
			<td class="ListSelectTd">
				<button type="button" class="Name2Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblUserId()[i]) %>'), '<%= AccountMasterAction.CMD_EDIT_MODE %>')"><%= PfNameUtility.select(params) %></button>
			</td>
			<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %></td>
			<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
			<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
			<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblUserId()[i])       %></td>
			<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblRoleCode()[i])     %></td>
			<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i])   %></td>
			<td class="ListSelectTd"><%= HtmlUtility.getCheckTag(params, "", "", "ckbSelect", vo.getAryCkbRecordId()[i], false, vo.getCkbSelect()) %></td>
		</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>

		<tr>
			<th class="UnderTd" colspan="8">
				<span class="TableButtonSpan">
<% if (params.getApplicationPropertyBool(PlatformConst.APP_INIT_PASSWORD_IMPOSSIBLE) == false) { %>
					<button type="button" class="Name8Button" id="btnbtnPassWord" onclick="submitRegist(event, 'divList', checkExtra, '<%= AccountMasterAction.CMD_PASS_INIT %>')"><%= PfNameUtility.initPassrword(params) %></button>&nbsp;
<% } %>
					<button type="button" class="Name2Button" id="btnDelete" onclick="submitDelete(event, 'divList', checkExtra, '<%= AccountMasterAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %></button>
				</span>
			</th>
		</tr>
<%
}
%>
	</table>
</div>
<%
if (vo.getList().isEmpty()) {
	return;
}
%>
<div class="ListInfo" id="divListInfo">
	<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
</div>
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="6"><span class="TitleTh"><%= PfNameUtility.batchUpdate(params) %></span>
				<span class="TableLabelSpan">
					<input type="radio" class="RadioButton" id="roleCodeSelect" name="radBatchUpdateType" value="<%= AccountMasterAction.TYPE_BATCH_UPDATE_ROLE %>" <%= HtmlUtility.getChecked(vo.getRadBatchUpdateType().equals(AccountMasterAction.TYPE_BATCH_UPDATE_ROLE)) %>
						onclick="submitTransfer(event, null, checkBatchUpdateType, null, '<%= AccountMasterAction.CMD_SET_BATCH_UPDATE_TYPE %>')" /><%= PfNameUtility.selectRole(params) %>&nbsp;
					<input type="radio" class="RadioButton" id="inactivateSelect" name="radBatchUpdateType" value="<%= AccountMasterAction.TYPE_BATCH_UPDATE_INACTIVATE %>" <%= HtmlUtility.getChecked(vo.getRadBatchUpdateType().equals(AccountMasterAction.TYPE_BATCH_UPDATE_INACTIVATE)) %>
						onclick="submitTransfer(event, null, checkBatchUpdateType, null, '<%= AccountMasterAction.CMD_SET_BATCH_UPDATE_TYPE %>')" /><%= PfNameUtility.switchInactivate(params) %>
				</span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span><%= PfNameUtility.activateDate(params) %></span></td>
			<td class="InputTd" id="tdUpdateActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear"  name="txtUpdateActivateYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear())  %>" />&nbsp;<label for="txtUpdateActivateYear" ><%= PfNameUtility.year(params)  %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />&nbsp;<label for="txtUpdateActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay"   name="txtUpdateActivateDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay())   %>" />&nbsp;<label for="txtUpdateActivateDay"  ><%= PfNameUtility.day(params)   %></label>
			</td>
			<td class="TitleTd"><span><label for="pltUpdateRoleCode"><%= PfNameUtility.role(params) %></label></span></td>
			<td class="InputTd">
				<select class="RoleCodeRequiredPullDown" id="pltUpdateRoleCode" name="pltUpdateRoleCode">
					<%= HtmlUtility.getSelectOption(vo.getAryPltUpdateRoleCode(), vo.getPltUpdateRoleCode()) %>
				</select>
			</td>
			<td class="TitleTd"><span><label for="pltUpdateInactivate"><%= PfNameUtility.inactivate(params) %></label></span></td>
			<td class="InputTd">
				<select class="InactivateRequiredPullDown" id="pltUpdateInactivate" name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" class="Name2Button" id="btnUpdate" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= AccountMasterAction.CMD_BATCH_UPDATE %>')"><%= PfNameUtility.update(params) %></button>
			</td>
		</tr>
	</table>
</div>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= PfNameUtility.topOfPage(params) %></a>
</div>
