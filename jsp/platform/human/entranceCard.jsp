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
language="java"
pageEncoding="UTF-8"
buffer="256kb"
autoFlush="false"
errorPage="/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.action.BasicCardAction"
import = "jp.mosp.platform.human.action.BasicListAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.action.EntranceCardAction"
import = "jp.mosp.platform.human.vo.EntranceCardVo"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
EntranceCardVo vo = (EntranceCardVo)params.getVo();
%>
<jsp:include page="<%= params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP) %>" flush="false" />
<div class="List">
<table class="CardTable" id="tblCard">
	<tr>
		<td class="TitleTd"><span><%= PfNameUtility.entranceDate(params) %></span></td>
		<td class="InputTd">
			<input type="text" class="Number4RequiredTextBox" id="txtEntranceYear"  name="txtEntranceYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceYear())  %>" />&nbsp;<label for="txtEntranceYear" ><%= PfNameUtility.year(params)  %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtEntranceMonth" name="txtEntranceMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceMonth()) %>" />&nbsp;<label for="txtEntranceMonth"><%= PfNameUtility.month(params) %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtEntranceDay"   name="txtEntranceDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtEntranceDay())   %>" />&nbsp;<label for="txtEntranceDay"  ><%= PfNameUtility.day(params)   %></label>
		</td>
	</tr>
</table>
<input type="hidden" id="hdnRecordId" value="<%= vo.getRecordId() %>" />
</div>
<div class="Button">
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'tblCard', null, '<%= EntranceCardAction.CMD_REGIST %>')"><%= PfNameUtility.insert(params) %></button>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= EntranceCardAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %></button>
	<button type="button" id="btnBasicList" class="Name4Button"
			onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>','<%= HumanInfoAction.class.getName() %>') ,'<%= EntranceCardAction.CMD_TRANSFER %>');"
	><%= PfNameUtility.dataList(params) %></button>
</div>
