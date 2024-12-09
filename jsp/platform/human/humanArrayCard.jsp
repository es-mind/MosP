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
buffer = "256kb"
autoFlush = "false"
errorPage = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.framework.xml.ViewTableProperty"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.framework.xml.ViewProperty"
import = "jp.mosp.framework.property.ViewConfigProperty"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.action.HumanArrayCardAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.vo.HumanArrayCardVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanArrayCardVo vo = (HumanArrayCardVo)params.getVo();
// MosP処理情報から人事汎用管理区分及び表示区分を取得
String division = vo.getDivision();
if(division == null){
	division = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_DIVISION);
}
// 人事汎用管理区分設定取得
ViewConfigProperty viewConfig = params.getProperties().getViewConfigProperties().get(division);
String view = HumanArrayCardAction.KEY_VIEW_ARRAY_CARD;
// 人事汎用表示区分設定取得
ViewProperty viewProperty = viewConfig.getView(view);
// 人事汎用表示テーブル配列取得
String[] viewTables = viewProperty.getViewTableKeys();
// 画面名区分取得(履歴編集/履歴追加)
String imageName = (String)params.getGeneralParam(HumanArrayCardAction.KEY_VIEW_ARRAY_CARD);
// 履歴追加画面区分をMosP処理情報に追加
params.addGeneralParam(HumanArrayCardAction.KEY_VIEW_ARRAY_CARD, imageName);

%>
<jsp:include page="<%= params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP) %>" flush="false" />
<%
//表示テーブル毎に処理
for (String viewTable : viewTables) {
%>
<div class="List" id="divArrayCard">
	<table class="ListTable" id="tblActivateDate">
		<tr>
			<td class="TitleTd"><%= HtmlUtility.getRequiredMark() %><span>
<%
	//人事汎用表示テーブル設定取得
	String dateName = viewConfig.getViewTable(viewTable).getDateName();
	// 日付名設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DATE_NAME,dateName);
	// 日付名が設定されている場合
	if(dateName != null && dateName.isEmpty() == false){
%>
			<%= NameUtility.getName(params, dateName) %>
<%
	// 設定されていない場合
	}else{
%>
			<%= PfNameUtility.activateDate(params) %>
<%
	}
%>		
			</span></td>
			<td class="InputTd" id="inputActivateDate">
				<input type="text" class="Number4RequiredTextBox" id="txtActivateYear"  name="txtActivateYear"  value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateYear()) %>" />&nbsp;<label for="txtActivateYear" ><%= PfNameUtility.year(params)  %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateMonth" name="txtActivateMonth" value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateMonth())%>" />&nbsp;<label for="txtActivateMonth"><%= PfNameUtility.month(params) %></label>
				<input type="text" class="Number2RequiredTextBox" id="txtActivateDay"   name="txtActivateDay"   value="<%= HtmlUtility.escapeHTML(vo.getTxtActivateDay()) %>"  />&nbsp;<label for="txtActivateDay"  ><%= PfNameUtility.day(params)   %></label>
				<button type="button" class="Name2Button" id="btnActivateDate" onclick="submitTransfer(event,null, addValidate('inputActivateDate',null,event), new Array('<%= PlatformConst.PRM_TRANSFERRED_COMMAND %>', '<%= imageName %>'), '<%= HumanArrayCardAction.CMD_SET_ACTIVATION_DATE %>');"><%= PfNameUtility.activeteDateButton(params, vo.getModeActivateDate()) %></button>
			</td>
		</tr>
	</table>
<%
	// 人事汎用管理管理区分設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DIVISION,division);
	// 人事汎用管理画面設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_VIEW, view);
	// 表示テーブル設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_VIEW_TABLE, viewTable);
	
	// 有効日を決定しないと項目を表示しない
	if(vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)){
%>
	<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_VIEW_CONFIG_JSP %>" flush="false" />
<%
	}
}
%>	
</div>
<div class="Button">
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event, 'divArrayCard', null, '<%= HumanArrayCardAction.CMD_UPDATE %>')">
<%if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {%><%= PfNameUtility.insert(params) %><%} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {%><%= PfNameUtility.update(params) %></button>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= HumanArrayCardAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %><%}%></button>
	<button type="button" id="btnHumenInfo" class="Name4Button"
		onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%=HumanInfoAction.class.getName() %>'), '<%= HumanArrayCardAction.CMD_TRANSFER %>');">
		<%= PfNameUtility.dataList(params) %>
	</button>
</div>
