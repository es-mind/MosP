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
language="java"
pageEncoding="UTF-8"
buffer="16kb"
autoFlush="false"
errorPage="/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.vo.RetirementCardVo"
import = "jp.mosp.platform.human.action.RetirementCardAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.action.HumanInfoAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
RetirementCardVo vo = (RetirementCardVo)params.getVo();
%>
<jsp:include page="<%= params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP) %>" flush="false" />
<div class="List">
<table class="ListTable" id="tblAddList">
	<tr>
		<th class="ListSelectTh" id="thCardDate"><%=HtmlUtility.getRequiredMark()%><%= PfNameUtility.retirementDate(params) %></th>
		<th class="ListSelectTh" id="thRetirementReason"><%= PfNameUtility.retirementReason(params) %></th>
		<th class="ListSelectTh" id="thRetirementDetail"><label for="txtRetirementDetail"><%= PfNameUtility.reasonDetail(params) %></label></th>
	</tr>
	<tr>
		<td class="InputTd" id="">
			<input type="text" class="Number4RequiredTextBox" id="txtRetirementYear"  name="txtRetirementYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtRetirementYear())  %>" />&nbsp;<label for="txtRetirementYear" ><%= PfNameUtility.year(params)  %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtRetirementMonth" name="txtRetirementMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtRetirementMonth()) %>" />&nbsp;<label for="txtRetirementMonth"><%= PfNameUtility.month(params) %></label>
			<input type="text" class="Number2RequiredTextBox" id="txtRetirementDay"   name="txtRetirementDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtRetirementDay())   %>" />&nbsp;<label for="txtRetirementDay"  ><%= PfNameUtility.day(params)   %></label>
		</td>
		<td class="InputTd" id="">
			<select id="pltRetirementReason" name="pltRetirementReason">
			<%= HtmlUtility.getSelectOption(vo.getAryPltRetirementReason(), vo.getPltRetirementReason()) %>
			</select>
		</td>
		<td class="InputTd" id="">
			<textarea name="txtRetirementDetail" class="Name120TextArea" id="txtRetirementDetail" ><%= HtmlUtility.escapeHTML(vo.getTxtRetirementDetail()) %></textarea>
		</td>
	</tr>
</table>
<input type="hidden" id="hdnRecordId" value="<%= vo.getRecordId() %>" />
</div>
<div class="Button">
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'tblAddList', null, '<%= RetirementCardAction.CMD_UPDATE %>')"><%= PfNameUtility.insert(params) %></button>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= RetirementCardAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %></button>
	<button type="button" id="btnBasicList" class="Name4Button"
			onclick="submitTransfer(event, null, null, new Array( '<%= PlatformConst.PRM_TRANSFERRED_ACTION %>','<%= HumanInfoAction.class.getName() %>'), '<%= RetirementCardAction.CMD_TRANSFER %>');"
	><%= PfNameUtility.dataList(params) %></button>
</div>
