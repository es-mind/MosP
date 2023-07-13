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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.utils.HuMessageUtility"
import = "jp.mosp.platform.human.utils.HuNameUtility"
import = "jp.mosp.platform.human.vo.HumanBinaryArrayCardVo"
import = "jp.mosp.platform.human.action.HumanBinaryArrayCardAction"
import = "jp.mosp.platform.human.action.HumanBinaryOutputFileAction"
import = "jp.mosp.platform.human.action.HumanBinaryOutputImageAction"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)params.getVo();
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
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span><%=PfNameUtility.activateDate(params)%></span></td>
			<td class="InputTd" id="inputActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtActivateYear"  name="txtActivateYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear())  %>" />&nbsp;<label for="txtActivateYear" ><%= PfNameUtility.year(params)  %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth()) %>" />&nbsp;<label for="txtActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateDay"   name="txtActivateDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay())   %>" />&nbsp;<label for="txtActivateDay"  ><%= PfNameUtility.day(params)   %></label>
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitForm(event, 'inputActivateDate', null, '<%=HumanBinaryArrayCardAction.CMD_SET_ACTIVATION_DATE%>')"><%=PfNameUtility.activeteDateButton(params, vo.getModeActivateDate())%></button>
			</td>
		</tr>
	</table>
</div>
<%
// 履歴編集の場合
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)){
	// ファイルの場合
	if(fileType.equals(MospConst.CODE_BINARY_FILE_FILE)){
%>
<div class="List">
	<table class="ListTable" id="tblActivateDate">
		<tr>
			<td class="TitleTd"><%= HuNameUtility.registeredFile(params) %></td>
			<td>	
				<button type="button" class="Name4Button" id="btnOutput" onclick="submitFile(event, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_INDEX %>','<%= vo.getRowId() %>'), '<%= HumanBinaryOutputFileAction.CMD_ARRAY_FILE %>');"><%= PfNameUtility.output(params) %></button>
			</td>
		</tr>
	</table>
</div>
<%
// 画像の場合
	}else{
%>
	<img src="../srv/?cmd=<%= HumanBinaryOutputImageAction.CMD_ARRAY_IMAGE %>&<%= PlatformConst.PRM_TRANSFERRED_INDEX %>=<%= vo.getRowId() %>" />
<%
}
}
// 有効日を決定しないと項目を表示しない
if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)) {
%>
<div class="List" id="divEdit">
	<table class="InputTable">
		<tr>
			<td class="TitleTd" ><span><%=HuNameUtility.fileRemarks(params)%></span></td>
			<td class="InputTd" id="txtFileRemark">
				<textarea maxlength="100" class="Name100TextArea" onkeyup="return isMaxlength(this)" rows="3" cols="50" id="txtFileRemark" name="txtFileRemark"><%= HtmlUtility.escapeHTML(vo.getTxtFileRemark()) %></textarea>
			</td>
		</tr>
<%
// 履歴編集以外の場合
if (!vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
		<tr>		
			<td class="TitleTd" ><%= HtmlUtility.getRequiredMark() %><span><%= PfNameUtility.targetFile(params) %></span></td>
			<td class="InputTd" id="inputFileValue" colspan="6">
				<label for="<%= HumanBinaryArrayCardAction.PRM_FILE_BINARY_ARRAY %>"></label><input type="file" class="FileTextBox" id="<%= HumanBinaryArrayCardAction.PRM_FILE_BINARY_ARRAY %>" name="<%= HumanBinaryArrayCardAction.PRM_FILE_BINARY_ARRAY %>" size="90" />
				<br><br>
				<label><%= HuMessageUtility.getPictureExtension(params) %></label>
			</td>
		</tr>
<%
}
%>
	</table>
</div>
<%
}
%>

<div class="Button">
	
<%
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
%>
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitFormMulti(event, 'tblCard', checkExtra, '<%= HumanBinaryArrayCardAction.CMD_ADD %>')"><%= PfNameUtility.insert(params) %></button>
<%
} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'tblCard', null, '<%= HumanBinaryArrayCardAction.CMD_UPDATE %>')"><%= PfNameUtility.update(params) %></button>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= HumanBinaryArrayCardAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %><%}%></button>
	<button type="button" id="btnHumenInfo" class="Name4Button"
		onclick="submitTransfer(event, 'tblCard', null, new Array('<%=PlatformConst.PRM_TRANSFERRED_TYPE%>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanInfoAction.class.getName() %>'), '<%= HumanBinaryArrayCardAction.CMD_TRANSFER %>');">
		<%= PfNameUtility.dataList(params) %>
	</button>
</div>
