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
import = "jp.mosp.platform.comparator.base.ActivateDateComparator"
import = "jp.mosp.platform.comparator.base.InactivateComparator"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.comparator.settings.CutoffMasterCutoffAbbrComparator"
import = "jp.mosp.time.comparator.settings.CutoffMasterCutoffCodeComparator"
import = "jp.mosp.time.comparator.settings.CutoffMasterCutoffDateComparator"
import = "jp.mosp.time.comparator.settings.CutoffMasterCutoffNameComparator"
import = "jp.mosp.time.comparator.settings.CutoffMasterNoApplovalComparator"
import = "jp.mosp.time.comparator.settings.CutoffMasterSelfTighteningComparator"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.settings.action.CutoffMasterAction"
import = "jp.mosp.time.settings.vo.CutoffMasterVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
CutoffMasterVo vo = (CutoffMasterVo)params.getVo();
%>
<div class="List" id="divEdit">
	<table class="InputTable">
		<tr>
			<th class="EditTableTh" colspan="6">
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
				<input type="text" class="Number2RequiredTextBox" id="txtEditActivateDay" name="txtEditActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditActivateDay()) %>" />
				<label for="txtEditActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditCutoffCode"><%= params.getName("CutoffDate", "Code") %></label></td>
			<td class="InputTd">
				<input type="text" class="Code10RequiredTextBox" id="txtEditCutoffCode" name="txtEditCutoffCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditCutoffCode()) %>" />
			</td>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditCutoffName"><%= params.getName("CutoffDate", "Name") %></label></td>
			<td class="InputTd">
				<input type="text" class="Name15RequiredTextBox" id="txtEditCutoffName" name="txtEditCutoffName" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditCutoffName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><label for="txtEditCutoffAbbr"><%= params.getName("CutoffDate", "Abbreviation") %></label></td>
			<td class="InputTd">
				<input type="text" class="Byte6RequiredTextBox" id="txtEditCutoffAbbr" name="txtEditCutoffAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtEditCutoffAbbr()) %>" />
			</td>
			<td class="TitleTd"><label for="pltEditCutoffDate"><%= params.getName("CutoffDate") %></label></td>
			<td class="InputTd">
				<select class="Name4PullDown" id="pltEditCutoffDate" name="pltEditCutoffDate">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_CUTOFF_DATE, vo.getPltEditCutoffDate(), false) %>
				</select>
			</td>
			<td class="TitleTd"><label for="pltEditNoApproval"><%= params.getName("Ram", "Approval", "Provisional", "Cutoff") %></label></td>
			<td class="InputTd">
				<select class="Name12PullDown" id="pltEditNoApproval" name="pltEditNoApproval">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_NO_APPROVAL, vo.getPltEditNoApproval(), false) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("SelfTightening") %></td>
			<td class="InputTd">
				<select id="pltEditSelfTightening" name="pltEditSelfTightening">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditSelfTightening(), false) %>
				</select>
			</td>
			<td class="TitleTd"><label for="pltEditInactivate"><%= params.getName("Effectiveness", "Slash", "Inactivate") %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltEditInactivate" name="pltEditInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltEditInactivate(), false) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divEdit', null, '<%= CutoffMasterAction.CMD_REGIST %>')"><%= params.getName("Insert") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="List" id="divSearch">
	<table class="InputTable">
		<tr>
			<th class="ListTableTh" colspan="6">
				<span class="TitleTh"><%= params.getName("Search") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><span class="RequiredLabel">*&nbsp;</span><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtSearchActivateYear" name="txtSearchActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateYear()) %>" />
				<label for="txtSearchActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateMonth" name="txtSearchActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateMonth()) %>" />
				<label for="txtSearchActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtSearchActivateDay" name="txtSearchActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchActivateDay()) %>" />
				<label for="txtSearchActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><label for="txtSearchCutoffCode"><%= params.getName("CutoffDate", "Code") %></label></td>
			<td class="InputTd">
				<input type="text" class="Code10TextBox" id="txtSearchCutoffCode" name="txtSearchCutoffCode" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchCutoffCode()) %>" />
			</td>
			<td class="TitleTd"><label for="txtSearchCutoffName"><%= params.getName("CutoffDate", "Name") %></label></td>
			<td class="InputTd">
				<input type="text" class="Name15TextBox" id="txtSearchCutoffName" name="txtSearchCutoffName" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchCutoffName()) %>" />
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><label for="txtSearchCutoffAbbr"><%= params.getName("CutoffDate", "Abbreviation") %></label></td>
			<td class="InputTd">
				<input type="text" class="Byte6TextBox" id="txtSearchCutoffAbbr" name="txtSearchCutoffAbbr" value="<%= HtmlUtility.escapeHTML(vo.getTxtSearchCutoffAbbr()) %>" />
			</td>
			<td class="TitleTd"><label for="pltSearchCutoffDate"></label><%= params.getName("CutoffDate") %></td>
			<td class="InputTd">
				<select class="Name3PullDown" id="pltSearchCutoffDate" name="pltSearchCutoffDate">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_CUTOFF_DATE, vo.getPltSearchCutoffDate(), true) %>
				</select>
			</td>
			<td class="TitleTd"><label for="pltSearchNoApproval"><%= params.getName("Ram", "Approval", "Provisional", "Cutoff") %></label></td>
			<td class="InputTd">
				<select class="Name12PullDown" id="pltSearchNoApproval" name="pltSearchNoApproval">
					<%= HtmlUtility.getSelectOption(params, TimeConst.CODE_KEY_NO_APPROVAL, vo.getPltSearchNoApproval(), true) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("SelfTightening") %></td>
			<td class="InputTd">
				<select id="pltSearchSelfTightening" name="pltSearchSelfTightening">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchSelfTightening(), true) %>
				</select>
			</td>
			<td class="TitleTd"><label for="pltSearchInactivate"><%= params.getName("Effectiveness", "Slash", "Inactivate") %></label></td>
			<td class="InputTd">
				<select class="Name2PullDown" id="pltSearchInactivate" name="pltSearchInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltSearchInactivate(), true) %>
				</select>
			</td>
			<td class="Blank" colspan="2"></td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitForm(event, 'divSearch', null, '<%= CutoffMasterAction.CMD_SEARCH %>')"><%= params.getName("Search") %></button>
			</td>
		</tr>
	</table>
</div>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="FixList" id="divList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSortTh" id="thActivateDate"   onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= ActivateDateComparator              .class.getName() %>'), '<%= CutoffMasterAction.CMD_SORT %>')"><%= params.getName("ActivateDate")                                           %><%= PlatformUtility.getSortMark(ActivateDateComparator              .class.getName(), params) %></th>
				<th class="ListSortTh" id="thCutoffCode"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffMasterCutoffCodeComparator    .class.getName() %>'), '<%= CutoffMasterAction.CMD_SORT %>')"><%= params.getName("CutoffDate", "Code")                                     %><%= PlatformUtility.getSortMark(CutoffMasterCutoffCodeComparator    .class.getName(), params) %></th>
				<th class="ListSortTh" id="thCutoffName"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffMasterCutoffNameComparator    .class.getName() %>'), '<%= CutoffMasterAction.CMD_SORT %>')"><%= params.getName("CutoffDate", "Name")                                     %><%= PlatformUtility.getSortMark(CutoffMasterCutoffNameComparator    .class.getName(), params) %></th>
				<th class="ListSortTh" id="thCutoffAbbr"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffMasterCutoffAbbrComparator    .class.getName() %>'), '<%= CutoffMasterAction.CMD_SORT %>')"><%= params.getName("CutoffDate", "Abbreviation")                             %><%= PlatformUtility.getSortMark(CutoffMasterCutoffAbbrComparator    .class.getName(), params) %></th>
				<th class="ListSortTh" id="thCutoffDate"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffMasterCutoffDateComparator    .class.getName() %>'), '<%= CutoffMasterAction.CMD_SORT %>')"><%= params.getName("CutoffDate")                                             %><%= PlatformUtility.getSortMark(CutoffMasterCutoffDateComparator    .class.getName(), params) %></th>
				<th class="ListSortTh" id="thNoApproval"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffMasterNoApplovalComparator    .class.getName() %>'), '<%= CutoffMasterAction.CMD_SORT %>')"><%= params.getName("Ram", "Approval", "Provisional", "Cutoff")               %><%= PlatformUtility.getSortMark(CutoffMasterNoApplovalComparator    .class.getName(), params) %></th>
				<th class="ListSortTh" id="thSelfTightening" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffMasterSelfTighteningComparator.class.getName() %>'), '<%= CutoffMasterAction.CMD_SORT %>')"><%= params.getName("SelfTightening")                                         %><%= PlatformUtility.getSortMark(CutoffMasterSelfTighteningComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thInactivate"     onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= InactivateComparator                .class.getName() %>'), '<%= CutoffMasterAction.CMD_SORT %>')"><%= params.getName("EffectivenessExistence", "Slash", "InactivateExistence") %><%= PlatformUtility.getSortMark(InactivateComparator                .class.getName(), params) %></th>
				<th class="ListSelectTh" id="thSelect">
<%
if (!vo.getList().isEmpty()) {
%>
					<input type="checkbox" class="" id="ckbSelect" onclick="doAllBoxChecked(this)">
<%
}
%>
				</th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getAryLblActivateDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd">
					<button type="button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getAryLblActivateDate()[i]) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(vo.getAryLblCutoffCode()[i]) %>'), '<%= CutoffMasterAction.CMD_EDIT_MODE %>')"><%= params.getName("Select") %></button>
				</td>
				<td                     ><%= vo.getAryLblActivateDate  ()[i] %></td>
				<td                     ><%= vo.getAryLblCutoffCode    ()[i] %></td>
				<td                     ><%= vo.getAryLblCutoffName    ()[i] %></td>
				<td                     ><%= vo.getAryLblCutoffAbbr    ()[i] %></td>
				<td class="ListSelectTd"><%= vo.getAryLblCutoffDate    ()[i] %></td>
				<td                     ><%= vo.getAryLblNoApproval    ()[i] %></td>
				<td class="ListSelectTd"><%= vo.getAryLblSelfTightening()[i] %></td>
				<td class="ListSelectTd"><%= vo.getAryLblInactivate    ()[i] %></td>
				<td class="ListSelectTd"><input type="checkbox" class="" name="ckbSelect" value="<%= vo.getAryCkbRecordId()[i] %>" <%= HtmlUtility.getChecked(vo.getAryCkbRecordId()[i], vo.getCkbSelect()) %> /></td>
			</tr>
<%
}
if (!vo.getList().isEmpty()) {
%>
			<tr>
				<td class="UnderTd" colspan="10">
					<span class="TableButtonSpan">
						<button type="button" class="Name4Button" onclick="submitDelete(event, 'divList', checkExtra, '<%= CutoffMasterAction.CMD_DELETE %>')"><%= params.getName("History", "Delete") %></button>
					</span>
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<%
if (vo.getAryLblActivateDate().length > 0) {
%>
<%= HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex()) %>
<div class="List" id="divUpdate">
	<table class="InputTable">
		<tr>
			<th class="UpdateTableTh" colspan="4">
				<span class="TitleTh"><%= params.getName("Bulk", "Update") %></span>
			</th>
		</tr>
		<tr>
			<td class="TitleTd"><%= params.getName("ActivateDate") %></td>
			<td class="InputTd">
				<input type="text" class="Number4RequiredTextBox" id="txtUpdateActivateYear" name="txtUpdateActivateYear" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateYear()) %>" />
				<label for="txtUpdateActivateYear"><%= params.getName("Year") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateMonth" name="txtUpdateActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateMonth()) %>" />
				<label for="txtUpdateActivateMonth"><%= params.getName("Month") %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtUpdateActivateDay" name="txtUpdateActivateDay" value="<%= HtmlUtility.escapeHTML(vo.getTxtUpdateActivateDay()) %>" />
				<label for="txtUpdateActivateDay"><%= params.getName("Day") %></label>
			</td>
			<td class="TitleTd"><%= params.getName("Effectiveness", "Slash", "Inactivate") %></td>
			<td class="InputTd">
				<select id="pltUpdateInactivate" name="pltUpdateInactivate">
					<%= HtmlUtility.getSelectOption(params, PlatformConst.CODE_KEY_INACTIVATE_FLAG, vo.getPltUpdateInactivate(), false) %>
				</select>
			</td>
		</tr>
	</table>
	<table class="ButtonTable">
		<tr>
			<td class="ButtonTd">
				<button type="button" id="btnRegist" class="Name2Button" onclick="submitRegist(event, 'divUpdate', checkExtra, '<%= CutoffMasterAction.CMD_BATCH_UPDATE %>')"><%= params.getName("Update") %></button>
			</td>
		</tr>
	</table>
</div>
<div class="MoveUpLink" id="divMoveUp">
	<a onclick="pageToTop()"><%= params.getName("UpperTriangular", "TopOfPage") %></a>
</div>
<%
}
%>
