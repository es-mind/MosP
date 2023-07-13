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
buffer="256kb"
autoFlush="false"
errorPage="/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.DateUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.utils.HuMessageUtility"
import = "jp.mosp.platform.human.utils.HuNameUtility"
import = "jp.mosp.platform.human.vo.HumanBinaryHistoryCardVo"
import = "jp.mosp.platform.human.action.HumanBinaryHistoryCardAction"
import = "jp.mosp.platform.human.action.HumanBinaryHistoryListAction"
import = "jp.mosp.platform.human.action.HumanBinaryOutputImageAction"
import = "jp.mosp.platform.human.action.HumanBinaryOutputFileAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)params.getVo();
//MosP処理情報から人事汎用管理区分及び表示区分を取得
String division = vo.getDivision();
if(division == null){
	division = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_DIVISION);
	}
String fileType = vo.getPltFileType();
%>
<jsp:include page="<%=params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP)%>" flush="false" />
<div class="List">
	<table class="ListTable" id="tblActivateDate">
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span><%= PfNameUtility.activateDate(params) %></span></td>
			<td class="InputTd" id="inputActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtActivateYear"  name="txtActivateYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear())%>"  />&nbsp;<label for="txtActivateYear" ><%= PfNameUtility.year(params)  %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth())%>" />&nbsp;<label for="txtActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateDay"   name="txtActivateDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay())%>"   />&nbsp;<label for="txtActivateDay"  ><%= PfNameUtility.day(params)   %></label>
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'inputActivateDate', null, '<%=HumanBinaryHistoryCardAction.CMD_SET_ACTIVATION_DATE%>')"><%=PfNameUtility.activeteDateButton(params, vo.getModeActivateDate())%></button>
			</td>
		</tr>
	</table>
</div>
<%
// 有効日を決定しないと項目を表示しない
if(vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)){
%>
<div class="List" id="divEdit">
	<table class="ListTable">
	<tr>
		<td class="TitleTd" ><span><label for="txtFileRemark"><%= HuNameUtility.fileRemarks(params) %></label></span></td>
		<td class="InputTd" id="txtFileRemark">
			<textarea class="Name100TextArea" id="txtFileRemark" name="txtFileRemark" ><%= HtmlUtility.escapeHTML(vo.getTxtFileRemark()) %></textarea>
		</td>
	</tr>
<%
// 履歴編集以外の場合
if (!vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)){
%>
	<tr>		
		<td class="TitleInputTd" id="tdFooter" colspan="2">
			<%= HtmlUtility.getRequiredMark() %>
			<span>
				<label for="<%= HumanBinaryHistoryCardAction.PRM_FILE_BINARY_HISTORY %>"><%= PfNameUtility.targetFile(params) %></label>&nbsp;:&nbsp;<input type="file" class="FileTextBox" id="<%= HumanBinaryHistoryCardAction.PRM_FILE_BINARY_HISTORY %>" name="<%= HumanBinaryHistoryCardAction.PRM_FILE_BINARY_HISTORY %>" size="90" />
				<br /><br />
				<label><%= HuMessageUtility.getPictureExtension(params) %></label>
			</span>
		</td>
	</tr>
<%
}
%>
	</table>
</div>
<%
//履歴編集の場合
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)){
	if(fileType.equals(MospConst.CODE_BINARY_FILE_FILE)) {
%>		
<div class="List">
	<table class="ListTable" id="tblActivateDate">
		<tr>
			<td class="TitleTd"><%= HuNameUtility.registeredFile(params) %></td>
			<td class="ListSelectTd">
				<button type="button" class="Name4Button" id="btnOutput" onclick="submitFile(event, null, new Array('<%="historyYear"%>','<%= vo.getTxtActivateYear() %>','<%="historyMonth"%>','<%= vo.getTxtActivateMonth() %>','<%="historyDay"%>','<%= vo.getTxtActivateDay() %>'), '<%= HumanBinaryOutputFileAction.CMD_HISTORY_FILE %>');"><%= PfNameUtility.output(params) %></button>
			</td>
		</tr>
	</table>
</div>

<%		
	// 画像の場合
	}else{
%>
	<img src="../srv/?cmd=<%= HumanBinaryOutputImageAction.CMD_HISTORY_IMAGE %>&historyYear=<%= vo.getTxtActivateYear() %>&historyMonth=<%= vo.getTxtActivateMonth() %>&historyDay=<%= vo.getTxtActivateDay() %>" />
<%
	}
}
}
%>
<div class="Button">
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
%>
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitFormMulti(event, 'divEdit', checkExtra, '<%= HumanBinaryHistoryCardAction.CMD_ADD %>')"><%= PfNameUtility.insert(params) %></button>
<%
} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'divEdit', null, '<%= HumanBinaryHistoryCardAction.CMD_UPDATE %>')"><%= PfNameUtility.update(params) %></button>
<%
}
%>
	<button type="button" id="btnHumenInfo" class="Name4Button"
		onclick="submitTransfer(event, 'tblCard', null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanInfoAction.class.getName() %>'), '<%= HumanBinaryHistoryCardAction.CMD_TRANSFER %>');">
		<%= PfNameUtility.dataList(params) %>
	</button>
	<button type="button" id="btnBasicList" class="Name4Button"
		onclick="submitTransfer(event, 'tblCard', null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanBinaryHistoryListAction.class.getName() %>'), '<%= HumanBinaryHistoryCardAction.CMD_TRANSFER %>');">
		<%= PfNameUtility.historyList(params) %>
	</button>
</div>

