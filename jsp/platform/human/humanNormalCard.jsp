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
import = "jp.mosp.framework.property.ViewConfigProperty"
import = "jp.mosp.framework.xml.ViewProperty"
import = "jp.mosp.framework.xml.InputProperty"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.vo.HumanNormalCardVo"
import = "jp.mosp.platform.human.action.HumanNormalCardAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
HumanNormalCardVo vo = (HumanNormalCardVo)params.getVo();
//MosP処理情報から人事汎用管理区分及び表示区分を取得
String division = vo.getDivision();
//人事汎用管理区分設定取得
ViewConfigProperty viewConfig = params.getProperties().getViewConfigProperties().get(division);
String view = HumanNormalCardAction.KEY_VIEW_NORMAL_CARD;
//人事汎用表示区分設定取得
ViewProperty viewProperty = viewConfig.getView(view);
//人事汎用表示テーブル配列取得
String[] viewTables = viewProperty.getViewTableKeys();
%>
<jsp:include page="<%= params.getApplicationProperty(PlatformHumanConst.APP_HUMAN_COMMON_INFO_JSP) %>" flush="false" />
<div class="List" id="divNormalCard">
<%
// 表示テーブル毎に処理
for (String viewTable : viewTables) {
	// 人事汎用管理管理区分設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DIVISION,division);
	// 人事汎用管理画面設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_VIEW, view);
	// 表示テーブル設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_VIEW_TABLE, viewTable);
%>
	<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_VIEW_CONFIG_JSP %>" flush="false" />
<%
}
%>	
</div>
<div class="Button">
	<button type="button" id="btnRegist" class="Name4Button" onclick="submitRegist(event,'divNormalCard', null, '<%= HumanNormalCardAction.CMD_REGIST %>')"><%= PfNameUtility.insert(params) %></button>
	<button type="button" id="btnDelete" class="Name4Button" onclick="submitDelete(event, null, null, '<%= HumanNormalCardAction.CMD_DELETE %>')"><%= PfNameUtility.delete(params) %></button>
	<button type="button" id="btnBasicList" class="Name4Button"
			onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTION %>','<%= HumanInfoAction.class.getName() %>') ,'<%= HumanNormalCardAction.CMD_TRANSFER %>');"
	><%= PfNameUtility.dataList(params) %></button>
</div>
