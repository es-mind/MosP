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
<%@page import="jp.mosp.time.utils.TimeNamingUtility"%>
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
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterBackTimeComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterEndTimeComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterFrontTimeComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterRestTimeComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterStartDateComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterWorkTimeComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterWorkTypeAbbrComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterWorkTypeCodeComparator"
import = "jp.mosp.time.comparator.settings.WorkTypeMasterWorkTypeNameComparator"
import = "jp.mosp.time.settings.action.WorkTypeCardAction"
import = "jp.mosp.time.settings.action.WorkTypeListAction"
import = "jp.mosp.time.settings.vo.WorkTypeListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
WorkTypeListVo vo = (WorkTypeListVo)params.getVo();
%>
<div class="List" id="divSearch">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%=PfNameUtility.search(params)%></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%=PfNameUtility.activateDate(params)%></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear())%>" />
				<label for="txtSearchActivateYear"><%=params.getName("Year")%></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth())%>" />
				<label for="txtSearchActivateMonth"><%=params.getName("Month")%></label>
			</td>
			<td class="TitleTd"><label for="txtSearchWorkTypeCode"><%=TimeNamingUtility.workTypeCode(params)%></label></td>
			<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchWorkTypeCode" name="txtSearchWorkTypeCode" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchWorkTypeCode())%>" /></td>
			<td class="TitleTd"><label for="txtSearchWorkTypeName"><%=params.getName("Work","Form","Name")%></label></td>
			<td class="InputTd"><input type="text" class="Name15TextBox" id="txtSearchWorkTypeName" name="txtSearchWorkTypeName" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchWorkTypeName())%>" /></td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchWorkTypeAbbr"><%=params.getName("Work","Form","Abbreviation")%></label></td>
			<td class="InputTd"><input type="text" class="Byte6TextBox" id="txtSearchWorkTypeAbbr" name="txtSearchWorkTypeAbbr" value="<%=HtmlUtility.escapeHTML(vo.getTxtSearchWorkTypeAbbr())%>" /></td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%=params.getName("Effectiveness","Slash","Inactivate")%></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%=HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true)%>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitForm(event, 'divSearch', checkDatesYearMonth, '<%=WorkTypeListAction.CMD_SEARCH%>')"><%=params.getName("Search")%></button>
			</td>
		</tr>
	</table>
</div>
<%=HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex())%>
<div class="FixList" id="divList">
	<table class="LeftListTable" id="list">
		<tr>
			<th class="ListSelectTh" id="thButton"></th>
			<th class="ListSortTh" id="thActivateDate" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=ActivateDateComparator.class.getName()%>'), '<%=WorkTypeListAction.CMD_SORT%>')"><%=PfNameUtility.activateDate(params)%><%= PlatformUtility.getSortMark(ActivateDateComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thWorkTypeCode" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterWorkTypeCodeComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= params.getName("WorkTypeAbbr","Code") %><%= PlatformUtility.getSortMark(WorkTypeMasterWorkTypeCodeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thWorkTypeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterWorkTypeNameComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= params.getName("WorkTypeAbbr","Name") %><%= PlatformUtility.getSortMark(WorkTypeMasterWorkTypeNameComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thWorkTypeAbbr" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterWorkTypeAbbrComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= params.getName("WorkTypeAbbr","Abbreviation") %><%= PlatformUtility.getSortMark(WorkTypeMasterWorkTypeAbbrComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thStartTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterStartDateComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= TimeNamingUtility.startWork(params) %><%= PlatformUtility.getSortMark(WorkTypeMasterStartDateComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thEndTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterEndTimeComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= TimeNamingUtility.endWork(params) %><%= PlatformUtility.getSortMark(WorkTypeMasterEndTimeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thWorkTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterWorkTimeComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= params.getName("WorkTime") %><%= PlatformUtility.getSortMark(WorkTypeMasterWorkTimeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thRestTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterRestTimeComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= params.getName("RestTime") %><%= PlatformUtility.getSortMark(WorkTypeMasterRestTimeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thFrontTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterFrontTimeComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= params.getName("AmRest") %><%-- params.getName("FrontTime") --%><%= PlatformUtility.getSortMark(WorkTypeMasterFrontTimeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thBackTime" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= WorkTypeMasterBackTimeComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= params.getName("PmRest") %><%-- params.getName("BackTime") --%><%= PlatformUtility.getSortMark(WorkTypeMasterBackTimeComparator.class.getName(), params) %></th>
			<th class="ListSortTh" id="thInactivate" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator.class.getName() %>'), '<%= WorkTypeListAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence","Slash","InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator.class.getName(), params) %></th>
<%
if (!vo.getList().isEmpty()) {
%>
			<th class="ListSelectTh" id="thSelect"><input type="checkbox" class="" id="ckbSelect" onclick="doAllBoxChecked(this);"></th>
<%
} else {
%>
			<th class="ListSelectTh" id="thSelect"></th>
<%
}
%>
		</tr>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
		<tr>
			<td class="ListSelectTd">
				<button type="button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= vo.getAryLblWorkTypeCode()[i] %>'), '<%= WorkTypeCardAction.CMD_SELECT_SHOW %>')"><%= params.getName("Detail") %></button>
			</td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblActivateMonth()[i]) %></td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTypeCode()[i]) %></td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTypeName()[i]) %></td>
			<td class="ListInputTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTypeAbbr()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblStartTime()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblEndTime()[i])  %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblWorkTime()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML( vo.getAryLblRestTime()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblFrontTime()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblBackTime()[i]) %></td>
			<td class="ListSelectTd" id=""><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate()[i]) %></td>
			<td class="ListSelectTd" id=""><input type="checkbox" class="" name="ckbSelect" value="<%= HtmlUtility.escapeHTML(vo.getAryCkbWorkTypeListId()[i]) %>"></td>
		</tr>
<%
}
%>
	</table>
</div>
<%
if (!vo.getList().isEmpty()) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="4">
				<span class="TitleTh"><%= params.getName("Bulk","Update") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" />
				<label for="txtUpdateActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />
				<label for="txtUpdateActivateMonth"><%= params.getName("Month") %></label>
<%--
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" />
				<label for="txtUpdateActivateDay"><%= params.getName("Day") %></label>
--%>
			</td>
			<td class="TitleTd"><%= params.getName("Effectiveness","Slash","Inactivate") %></td>
			<td class="InputTd">
				<select id="pltUpdateInactivate" name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd" id="">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= WorkTypeListAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Update") %></button>
			</td>
		</tr>
	</table>
</div>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, null, '<%= WorkTypeCardAction.CMD_SHOW %>');"><%= params.getName("New","Insert") %></button>
</div>
<%
if (!vo.getList().isEmpty()) {
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop();"><%= params.getName("UpperTriangular","TopOfPage") %></a>
</div>
<%
}
%>
