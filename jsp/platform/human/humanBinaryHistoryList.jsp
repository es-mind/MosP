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
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.framework.utils.RoleUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.utils.HuNameUtility"
import = "jp.mosp.platform.human.vo.HumanBinaryHistoryListVo"
import = "jp.mosp.platform.human.action.HumanBinaryHistoryListAction"
import = "jp.mosp.platform.human.action.HumanBinaryHistoryCardAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.action.HumanBinaryOutputImageAction"
import = "jp.mosp.platform.human.action.HumanBinaryOutputFileAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanBinaryHistoryListVo vo = (HumanBinaryHistoryListVo)params.getVo();
// 画面情報から人事汎用管理区分取得
String division = vo.getDivision();
// 人事汎用管理区分参照権限
boolean isReferenceDivision = RoleUtility.getReferenceDivisionsList(params).contains(division);
%>
<jsp:include page="<%= params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP) %>" flush="false" />
<%
for (int i = 0; i < vo.getAryActiveteDate().length; i++) {
	// 拡張子取得
	String fileType = vo.getAryFileType(i);
%>
<div class="List">
	<table class="OverTable">
		<tr>
			<th colspan="6" class="ListTableTh" id="">
				<span class="TitleTh"><%= HtmlUtility.escapeHTML(vo.getAryActiveteDate(i)) %><%= NameUtility.wave(params) %></span>
				<span class="TableButtonSpan">
<%
	if(fileType.equals(MospConst.CODE_BINARY_FILE_FILE)){
%>
				<button type="button" class="Name4Button" id="btnOutput" onclick="submitFile(event, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= vo.getAryActiveteDate(i) %>'), '<%= HumanBinaryOutputFileAction.CMD_HISTORY_LIST_FILE %>');"><%= PfNameUtility.output(params) %></button>
<%
	}
%>				<% // 更新権限がある場合 %>
				<% if (!isReferenceDivision) { %>
				<button type="button" id="btnHumenInfo" name="btnDelete" class="Name4Button" onclick="submitTransfer(event, null, checkExtra, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= vo.getDivision() %>','<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= vo.getAryActiveteDate(i) %>'), '<%= HumanBinaryHistoryListAction.CMD_DELETE %>');"
					><%= PfNameUtility.deleteHistory(params) %></button>
				<% } %>
				<button type="button" id="btnHumenInfo" class="Name4Button"
					onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= vo.getDivision() %>','<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= vo.getAryActiveteDate(i) %>', '<%= PlatformConst.PRM_TRANSFERRED_ACTION %>',  '<%= HumanBinaryHistoryCardAction.CMD_EDIT_SELECT %>'), '<%= HumanBinaryHistoryListAction.CMD_TRANSFER %>');"
					>
					<% // 更新権限がある場合%>
					<%= isReferenceDivision ? PfNameUtility.referHistory(params) : PfNameUtility.edtiHistory(params)%>
					</button>
				</span>
			</th>
		</tr>
	</table>
	<table class="UnderTable">
		<tr>
			<td class="TitleTd" ><%= PfNameUtility.activateDate(params) %></td>
			<td class="InputTd" id="tdActivateDate"><%= HtmlUtility.escapeHTML(vo.getAryActiveteDate(i)) %></td>
			<td class="TitleTd" ><%= HuNameUtility.fileName(params) %></td>
			<td class="InputTd" id="tdFileName"><%= HtmlUtility.escapeHTML(vo.getAryFileName(i)) %></td>
		</tr>
		<tr>
			<td class="TitleTd" ><%= HuNameUtility.fileRemarks(params) %></td>
			<td class="InputTd" id="tdFileRemark" colspan="3"><%= HtmlUtility.escapeHTML(vo.getAryFileRemark(i)) %></td>
		</tr>
	</table>
<% 
	//画像の場合
	if(!fileType.equals(MospConst.CODE_BINARY_FILE_FILE)){
%>
<img src="../srv/?cmd=<%= HumanBinaryOutputImageAction.CMD_HISTORY_LIST_IMAGE %>&<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>=<%= vo.getAryActiveteDate(i) %>" />
<%
	}
} 
%>
</div>
<div class="Button">
	<% // 更新権限がある場合 %>
	<% if (!isReferenceDivision) { %>
		<button type="button" id="btnCmd01" class="Name4Button"
		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>',  '<%= vo.getDivision() %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>',  '<%= HumanBinaryHistoryCardAction.CMD_ADD_SELECT %>'), '<%= HumanBinaryHistoryListAction.CMD_TRANSFER %>');">
			<%= PfNameUtility.addHistory(params)%>
		</button>
	<% } %>
	<button type="button" id="btnCmd01" class="Name4Button"
	onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanInfoAction.class.getName() %>'), '<%= HumanBinaryHistoryListAction.CMD_TRANSFER %>');"
	><%= PfNameUtility.dataList(params) %></button>
</div>
