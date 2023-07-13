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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.action.SuspensionCardAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.vo.SuspensionCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
SuspensionCardVo vo = (SuspensionCardVo)params.getVo();
%>
<jsp:include page="<%=params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP)%>" flush="false" />
<div class="FixList">
	<table class="ListTable" id="tblAddList">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thListNumber"></th>
				<th class="ListSelectTh" id="thCardDate"><%= HtmlUtility.getRequiredMark() %><%= PfNameUtility.suspensionStartDate(params) %></th>
				<th class="ListSelectTh" id="thCardDate"><%= PfNameUtility.suspensionEndDate(params) %></th>
				<th class="ListSelectTh" id="thCardDate"><%= HtmlUtility.getRequiredMark() %><%=PfNameUtility.expectedSuspensionEndDate(params) %></th>
				<th class="ListSelectTh" id="thSuspensionReason"><%= PfNameUtility.suspensionReason(params) %></th>
				<th class="ListSelectTh" id="thListSelect"><input type="checkbox" name="ckbAllSelect" onclick="doAllBoxChecked(this);setDeleteButtonDisabled();" /></th>
			</tr>
		</thead>
		<tbody id="addLeaveBody">
<%
for (int i = 0; i < vo.getAryTxtSuspensionStartYear().length; i++) {
%>	
			<tr>
				<td class="NumberTd"><span class="RowIndex"></span></td>
				<td class="InputTd">
					<input type="text" class="Number4RequiredTextBox" id="aryTxtSuspensionStartYear<%= i %>"  name="aryTxtSuspensionStartYear"  value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionStartYear()[i])  %>" />&nbsp;<label for="aryTxtSuspensionStartYear<%= i %>" ><%= PfNameUtility.year(params)  %></label>
					<input type="text" class="Number2RequiredTextBox" id="aryTxtSuspensionStartMonth<%= i %>" name="aryTxtSuspensionStartMonth" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionStartMonth()[i]) %>" />&nbsp;<label for="aryTxtSuspensionStartMonth<%= i %>"><%= PfNameUtility.month(params) %></label>
					<input type="text" class="Number2RequiredTextBox" id="aryTxtSuspensionStartDay<%= i %>"   name="aryTxtSuspensionStartDay"   value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionStartDay()[i])   %>" />&nbsp;<label for="aryTxtSuspensionStartDay<%= i %>"  ><%= PfNameUtility.day(params)   %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number4TextBox" id="aryTxtSuspensionEndYear<%= i %>"  name="aryTxtSuspensionEndYear"  value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionEndYear()[i])  %>" />&nbsp;<label for="aryTxtSuspensionEndYear<%= i %>" ><%= PfNameUtility.year(params)  %></label>
					<input type="text" class="Number2TextBox" id="aryTxtSuspensionEndMonth<%= i %>" name="aryTxtSuspensionEndMonth" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionEndMonth()[i]) %>" />&nbsp;<label for="aryTxtSuspensionEndMonth<%= i %>"><%= PfNameUtility.month(params) %></label>
					<input type="text" class="Number2TextBox" id="aryTxtSuspensionEndDay<%= i %>"   name="aryTxtSuspensionEndDay"   value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionEndDay()[i])   %>" />&nbsp;<label for="aryTxtSuspensionEndDay<%= i %>"  ><%= PfNameUtility.day(params)   %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Number4RequiredTextBox" id="aryTxtSuspensionScheduleEndYear<%= i %>"  name="aryTxtSuspensionScheduleEndYear"  value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionScheduleEndYear()[i])  %>" />&nbsp;<label for="aryTxtSuspensionScheduleEndYear<%= i %>" ><%= PfNameUtility.year(params)  %></label>
					<input type="text" class="Number2RequiredTextBox" id="aryTxtSuspensionScheduleEndMonth<%= i %>" name="aryTxtSuspensionScheduleEndMonth" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionScheduleEndMonth()[i]) %>" />&nbsp;<label for="aryTxtSuspensionScheduleEndMonth<%= i %>"><%= PfNameUtility.month(params) %></label>
					<input type="text" class="Number2RequiredTextBox" id="aryTxtSuspensionScheduleEndDay<%= i %>"   name="aryTxtSuspensionScheduleEndDay"   value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionScheduleEndDay()[i])   %>" />&nbsp;<label for="aryTxtSuspensionScheduleEndDay<%= i %>"  ><%= PfNameUtility.day(params)   %></label>
				</td>
				<td class="InputTd">
					<input type="text" class="Name30TextBox" id="aryTxtSuspensionReason<%= i %>" name="aryTxtSuspensionReason" value="<%= HtmlUtility.escapeHTML(vo.getAryTxtSuspensionReason()[i]) %>" />
				</td>
				<td class="SelectTd">
					<input type="checkbox" name="ckbSelect" onclick="setDeleteButtonDisabled()" />
					<input type="hidden" name="aryHidPfaHumanSuspension" value="<%= HtmlUtility.escapeHTML(vo.getAryHidPfaHumanSuspension()[i]) %>" />
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" id="btnAddRow" class="Name4Button" onclick="addSuspensionRow();"><%= PfNameUtility.addRow(params) %></button>
	<button type="button" id="btnRegist" class="Name4Button" onclick="removeBlankRows();submitRegist(event, 'tblAddList', null, '<%= SuspensionCardAction.CMD_UPDATE %>')"><%= PfNameUtility.insert(params) %></button>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= SuspensionCardAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %></button>
	<button type="button" id="btnBasicList" class="Name4Button"
		onclick="submitTransfer(event, 'addLeaveBody', null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>',  '<%= HumanInfoAction.class.getName() %>'), '<%= SuspensionCardAction.CMD_TRANSFER %>');">
		<%= PfNameUtility.dataList(params) %>
	</button>
</div>
<div class="MoveUpLink">
	<a onclick="pageToTop();"><%= PfNameUtility.topOfPage(params) %></a>
</div>
