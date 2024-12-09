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
language     = "java"
pageEncoding = "UTF-8"
buffer       = "256kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.utils.TimeNamingUtility"
import = "jp.mosp.time.settings.action.PaidHolidayDataGrantCardAction"
import = "jp.mosp.time.settings.action.PaidHolidayDataGrantListAction"
import = "jp.mosp.time.settings.vo.PaidHolidayDataGrantCardVo"
import = "jp.mosp.time.settings.vo.PaidHolidayDataGrantListVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PaidHolidayDataGrantCardVo vo = (PaidHolidayDataGrantCardVo)params.getVo();
%>
<div class="ListHeader">
	<table class="EmployeeLabelTable">
		<tr>
			<jsp:include page="<%=TimeConst.PATH_TIME_COMMON_INFO_JSP%>" flush="false" />
		</tr>
	</table>
</div>
<div class="FixList">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListTableTh" colspan="6">
					<span class="TitleTh">
						<%=TimeNamingUtility.paidHoliday(params)%><%=TimeNamingUtility.givingDays(params)%><%=TimeNamingUtility.correction(params)%>
					</span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="ListSelectTh"><%=TimeNamingUtility.givingDay(params)%></th>
				<th class="ListSelectTh"><%=PfNameUtility.activateDate(params)%></th>
				<th class="ListSelectTh"><%=PfNameUtility.limitDay(params)%></th>
				<th class="ListSelectTh"><%=TimeNamingUtility.givingDays(params)%><%=PfNameUtility.parentheses(params , TimeNamingUtility.beforeCorrection(params))%></th>
				<th class="ListSelectTh">
					<%=HtmlUtility.getRequiredMark()%><label for="aryTxtGrantDays"><%=TimeNamingUtility.givingDays(params)%></label>
				</th>
				<th class="ListSelectTh"></th>
			</tr>
<%
for (int i = 0; i < vo.getAryLblGrantDate().length; i++) {
%>
			<tr>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblGrantDate     ()[i])%></td>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblActivateDate  ()[i])%></td>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblExpirationDate()[i])%></td>
				<td class="ListSelectTd"><%=HtmlUtility.escapeHTML(vo.getAryLblGrantDays     ()[i])%></td>
				<td class="ListSelectTd">
					<%=HtmlUtility.getTextboxTag("Numeric4RequiredTextBox", MospConst.STR_EMPTY, "aryTxtGrantDays", vo.getAryTxtGrantDays()[i], false)%>
					<%=PfNameUtility.day(params)%>
				</td>
				<td class="ListSelectTd">
<%
if (vo.getAryRecordId()[i] != 0L) {
%>
					<button type="button" class="Name2Button" onclick="submitTransfer(event, null, confirmDelete, new Array('<%=PlatformConst.PRM_TRANSFERRED_INDEX%>', '<%=i%>'), '<%=PaidHolidayDataGrantCardAction.CMD_DELETE%>');"><%=PfNameUtility.delete(params)%></button>
<%
}
%>
				</td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
<div class="Button">
	<button type="button" class="Name6Button" id="btnRegist" onclick="submitRegist(event, '', checkExtra, '<%=PaidHolidayDataGrantCardAction.CMD_REGIST%>');"><%=PfNameUtility.insert(params)%></button>
	<button type="button" class="Name7Button" onclick="submitTransfer(event, null, null, null, '<%= PaidHolidayDataGrantListAction.CMD_RE_SHOW %>');"><%= params.getName(PaidHolidayDataGrantListVo.class.getName()) %><%= NameUtility.to(params) %></button>
</div>
