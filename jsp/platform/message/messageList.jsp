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
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.message.action.MessageListAction"
import = "jp.mosp.platform.message.action.MessageCardAction"
import = "jp.mosp.platform.message.vo.MessageListVo"
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.EndDateComparator"
import = "jp.mosp.platform.comparator.message.MessageNoComparator"
import = "jp.mosp.platform.comparator.message.MessageTypeComparator"
import = "jp.mosp.platform.comparator.message.MessageTitleComparator"
import = "jp.mosp.platform.comparator.base.InsertUserComparator"
import = "jp.mosp.platform.comparator.message.MessageImportanceComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.utils.PlatformUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
MessageListVo vo = (MessageListVo)params.getVo();
%>
<div class="List">
	<table class="InputTable" id="tblSearch">
			<tr>
				<th class="ListTableTh" colspan="6"><span class="TitleTh"><%=PfNameUtility.search(params)%></span></th>
			</tr>
			<tr>
				<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><%=PfNameUtility.displayTerm(params)%></td>
				<td class="InputTd">
					<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
					<label for="txtSearchActivateYear"><%= PfNameUtility.year(params) %></label>
					<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
					<label for="txtSearchActivateMonth"><%= PfNameUtility.month(params) %></label>
				</td>
				<td class="TitleTd"><label for="txtSearchMessageNo"><%= PfNameUtility.messageNo(params) %></label></td>
				<td class="InputTd"><input type="text" class="Code10TextBox" id="txtSearchMessageNo" name="txtSearchMessageNo" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchMessageNo()) %>" /></td>
				<td class="TitleTd"><label for="pltSearchMessageType"><%= PfNameUtility.messageType(params) %></label></td>
				<td class="InputTd">
					<select class="Name3PullDown" id="pltSearchMessageType" name="pltSearchMessageType">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_MESSAGE_TYPE, vo.getPltSearchMessageType(), true) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="txtSearchMessageTitle"><%= PfNameUtility.messageTitle(params) %></label></td>
				<td class="InputTd"><input type="text" class="Name15-30TextBox" id="txtSearchMessageTitle" name="txtSearchMessageTitle" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchMessageTitle()) %>"/></td>
				<td class="TitleTd"><label for="txtSearchEmployeeName"><%= PfNameUtility.insertUser(params) %></label></td>
				<td class="InputTd"><input type="text" class="Name10TextBox" id="txtSearchEmployeeName" name="txtSearchEmployeeName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchEmployeeName()) %>"/></td>
				<td class="TitleTd"><label for="pltSearchImportance"><%= PfNameUtility.importance(params) %></label></td>
				<td class="InputTd">
					<select class="Name1PullDown" id="pltSearchImportance" name="pltSearchImportance">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_MESSAGE_IMPORTANCE, vo.getPltSearchImportance(), true) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="TitleTd"><label for="pltSearchInactivate"><%= PfNameUtility.inactivate(params) %></label></td>
				<td class="InputTd">
					<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
						<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
					</select>
				</td>
				<td class="Blank" colspan="4"></td>
			</tr>
		</table>
		<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" id="btnSearch" class="Name2Button" onclick="submitForm(event, 'tblSearch', checkDatesYearMonth, '<%= MessageListAction.CMD_SEARCH %>')"><%= PfNameUtility.search(params) %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh"   id="thMessageNo"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= MessageNoComparator        .class.getName() %>'), '<%= MessageListAction.CMD_SORT %>')"><%= PfNameUtility.no(params)               %><%= PlatformUtility.getSortMark(MessageNoComparator        .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thStartDate"    onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator     .class.getName() %>'), '<%= MessageListAction.CMD_SORT %>')"><%= PfNameUtility.publishStartDate(params) %><%= PlatformUtility.getSortMark(ActivateDateComparator     .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thEndDate"      onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= EndDateComparator          .class.getName() %>'), '<%= MessageListAction.CMD_SORT %>')"><%= PfNameUtility.publishEndDate(params)   %><%= PlatformUtility.getSortMark(EndDateComparator          .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thMessageType"  onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= MessageTypeComparator      .class.getName() %>'), '<%= MessageListAction.CMD_SORT %>')"><%= PfNameUtility.type(params)             %><%= PlatformUtility.getSortMark(MessageTypeComparator      .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thMessageTitle" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= MessageTitleComparator     .class.getName() %>'), '<%= MessageListAction.CMD_SORT %>')"><%= PfNameUtility.messageTitle(params)     %><%= PlatformUtility.getSortMark(MessageTitleComparator     .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InsertUserComparator       .class.getName() %>'), '<%= MessageListAction.CMD_SORT %>')"><%= PfNameUtility.insertUser(params)       %><%= PlatformUtility.getSortMark(InsertUserComparator       .class.getName() , params) %></th>
				<th class="ListSortTh"   id="thImportance"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= MessageImportanceComparator.class.getName() %>'), '<%= MessageListAction.CMD_SORT %>')"><%= PfNameUtility.importance(params)       %><%= PlatformUtility.getSortMark(MessageImportanceComparator.class.getName() , params) %></th>
				<th class="ListSortTh"   id="thInactivate"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator       .class.getName() %>'), '<%= MessageListAction.CMD_SORT %>')"><%= PfNameUtility.inactivateAbbr(params)   %><%= PlatformUtility.getSortMark(InactivateComparator       .class.getName() , params) %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblStartDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblMessageNo()[i]) %>'), '<%= MessageCardAction.CMD_SELECT_SHOW %>');"><%= PfNameUtility.detail(params) %></button>
				</td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblMessageNo   ()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblStartDate   ()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEndDate     ()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblMessageType ()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblMessageTitle()[i]) %></td>
				<td class="ListInputTd" ><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblImportance  ()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblInactivate  ()[i]) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getList().size() > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<%
}
%>
<div class="Button">
	<button type="button" class="Name4Button" onclick="submitForm(event, null, null, '<%= MessageCardAction.CMD_SHOW %>')"><%= PfNameUtility.newInsert(params) %></button>
</div>
<%
if (vo.getList().size() == 0) {
	return;
}
%>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= PfNameUtility.topOfPage(params) %></a>
</div>
