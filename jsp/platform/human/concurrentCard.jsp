<%--
MosP - Mind Open Source Project
Copyright (C) esMind, LLC  https://www.e-s-mind.com/

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
buffer = "2048kb"
autoFlush = "false"
errorPage = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.action.ConcurrentCardAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.vo.ConcurrentCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ConcurrentCardVo vo = (ConcurrentCardVo)params.getVo();
%>
<jsp:include page="<%=params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP)%>" flush="false" />
<div class="List">
	<table class="ListTable" id="tblNowPosition">
		<tr>
			<td class="TitleTd" id="tdNowPosition">
				<%=PfNameUtility.currentMainCharge(params)%><%=PfNameUtility.colon(params)%>
<%
for (String sectionName : vo.getAryLblClassRoute()) {
%>
				<%=sectionName%>&nbsp;
<%
}
%>
				<%=HtmlUtility.escapeHTML(vo.getLblPositionName())%>
			</td>
		</tr>
	</table>
</div>
<div class="FixList">
	<table class="ListTable" id="tblAddList">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thListNumber"></th>
				<th class="ListSelectTh" id="thCardButtonDate"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.startDate(params) %></th>
				<th class="ListSelectTh" id="thCardDate"      ><%= PfNameUtility.endDate(params) %></th>
				<th class="ListSelectTh" id="thSectionAbbr"   ><%= PfNameUtility.section(params) %></th>
				<th class="ListSelectTh" id="thPositionAbbr"  ><%= PfNameUtility.position(params) %></th>
				<th class="ListSelectTh" id="thRemark"        ><%= PfNameUtility.remarks(params) %></th>
				<th class="ListSelectTh" id="thListSelect"><input type="checkbox" name="ckbAllSelect" onclick="doAllBoxChecked(this);setDeleteButtonDisabled();" /></th>
			</tr>
		</thead>
		<tbody id="addLeaveBody">
<%
for (int i = 0; i < vo.getAryTxtConcurrentStartYear().length; i++) {
%>
			<tr>
				<td class="NumberTd"><span class="RowIndex"></span></td>
				<td class="SelectTd" id="inputActivateDate<%= i %>">
					<input type="text" class="Number4RequiredTextBox" id="aryTxtConcurrentStartYear<%= i %>"  name="aryTxtConcurrentStartYear"  value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentStartYear() [i]) %>" /><label for="aryTxtConcurrentStartYear<%= i %>" ><%= PfNameUtility.year(params) %></label>
					<input type="text" class="Number2RequiredTextBox" id="aryTxtConcurrentStartMonth<%= i %>" name="aryTxtConcurrentStartMonth" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentStartMonth()[i]) %>" /><label for="aryTxtConcurrentStartMonth<%= i %>"><%= PfNameUtility.month(params)%></label>
					<input type="text" class="Number2RequiredTextBox" id="aryTxtConcurrentStartDay<%= i %>"   name="aryTxtConcurrentStartDay"   value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentStartDay()  [i]) %>" /><label for="aryTxtConcurrentStartDay<%= i %>"  ><%= PfNameUtility.day(params)  %></label>
					<button type="button" class="Name2Button" name="btnActivateDate"
						onclick="submitForm(event, null, checkExtraForActivate, '<%=ConcurrentCardAction.CMD_SET_ACTIVATION_DATE%>')">
						<%= PfNameUtility.activeteDateButton(params, vo.getModeActivateDateArray()[i]) %>
					</button>
					<input type="hidden" name="modeActivateDateArray" value="<%=HtmlUtility.escapeHTML(vo.getModeActivateDateArray()[i])%>" />
				</td>
				<td class="SelectTd">
					<input type="text" class="Number4TextBox" id="aryTxtConcurrentEndYear<%= i %>"  name="aryTxtConcurrentEndYear"  value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentEndYear() [i]) %>" /><label for="aryTxtConcurrentEndYear<%= i %>" ><%= PfNameUtility.year(params)  %></label>
					<input type="text" class="Number2TextBox" id="aryTxtConcurrentEndMonth<%= i %>" name="aryTxtConcurrentEndMonth" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentEndMonth()[i]) %>" /><label for="aryTxtConcurrentEndMonth<%= i %>"><%= PfNameUtility.month(params) %></label>
					<input type="text" class="Number2TextBox" id="aryTxtConcurrentEndDay<%= i %>"   name="aryTxtConcurrentEndDay"   value="<%= HtmlUtility.escapeHTML(vo.getAryTxtConcurrentEndDay()  [i]) %>" /><label for="aryTxtConcurrentEndDay<%= i %>"  ><%= PfNameUtility.day(params)   %></label>
				</td>
				<td class="SelectTd">
					<select class="Name12PullDown" id="aryPltSectionAbbr<%= i %>" name="arySectionAbbr">
						<%= HtmlUtility.getSelectOption(vo.getListAryPltSectionAbbr().get(i), vo.getArySectionAbbr()[i]) %>
					</select>
				</td>
				<td class="SelectTd">
					<select class="Name12PullDown" id="aryPltPosition<%= i %>" name="aryPosition">
						<%= HtmlUtility.getSelectOption(vo.getListAryPltPosition().get(i), vo.getAryPosition()[i]) %>
					</select>
				</td>
				<td class="SelectTd">
					<input type="text" class="Name10-50TextBox" id="aryTxtRemark<%= i %>" name="aryTxtRemark" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtRemark()[i]) %>" />
				</td>
				<td class="SelectTd">
					<input type="checkbox" name="ckbSelect" onclick="setDeleteButtonDisabled();" />
					<input type="hidden" name="aryHidPfaHumanConcurrentId" value="<%= HtmlUtility.escapeHTML(vo.getAryHidPfaHumanConcurrentId()[i]) %>" />
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" id="btnAddRow" class="Name4Button" onclick="addConcurrentRow();"><%= PfNameUtility.addRow(params) %></button>
	<button type="button" id="btnRegist" class="Name4Button"
		onclick="removeBlankRows();submitRegist(event, 'tblAddList', checkExtraForRegist, '<%= ConcurrentCardAction.CMD_UPDATE %>');"><%= PfNameUtility.insert(params) %></button>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= ConcurrentCardAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %></button>
	<button type="button" id="btnBasicList" class="Name4Button"
		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanInfoAction.class.getName() %>'), '<%= ConcurrentCardAction.CMD_TRANSFER %>');">
		<%= PfNameUtility.dataList(params) %>
	</button>
</div>
<div class="MoveUpLink">
	<a onclick="pageToTop();"><%= PfNameUtility.topOfPage(params) %></a>
</div>
