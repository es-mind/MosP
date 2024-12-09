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
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.bean.portal.impl.PortalMessageBean"
import = "jp.mosp.platform.portal.action.PortalAction"
import = "jp.mosp.platform.portal.vo.PortalVo"
%><%
//VOを準備
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PortalVo vo = (PortalVo)params.getVo();
// VOを取得できなかった場合
if (vo == null) {
	// 処理終了
	return;
}
%>
<div class="List" id="divInformation">
	<table class="ListTable" id="tblHeader">
		<tr><th class="PortalTableTh" colspan="2"><span class="TitleTh"><%= PfNameUtility.message(params) %></span></th></tr>
	</table>
	<table class="ListTable" id="tblMessage">
<%
for (int i = 0; i < vo.getPortalParameters(PortalMessageBean.PRM_ARY_LBL_MESSAGE_DATE).length; i++) {
%>
		<tr>
			<td class="MessageImportance">
				<span <%= vo.getPortalParameters(PortalMessageBean.PRM_ARY_LBL_MESSAGE_IMPORTANCE_STYLE)[i] %>>
					<%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalMessageBean.PRM_ARY_LBL_MESSAGE_IMPORTANCE)[i]) %>
				</span>
			</td>
			<td class="MessageDate">
				<span class="Bold"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalMessageBean.PRM_ARY_LBL_MESSAGE_DATE)[i]) %></span>
			</td>
			<td class="MessageTitle">
				<span class="Bold">
					<%= NameUtility.cornerParentheses(params, HtmlUtility.escapeHTML(vo.getPortalParameters(PortalMessageBean.PRM_ARY_LBL_MESSAGE_TYPE)[i])) %>
					<%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalMessageBean.PRM_ARY_LBL_MESSAGE_TITLE)[i]) %>
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="MessageBlank"></td>
			<td class="MessageBody"><%= vo.getPortalParameters(PortalMessageBean.PRM_ARY_LBL_MESSAGE)[i] %></td>
		</tr>
<%
}
if (vo.getPortalParameters(PortalMessageBean.PRM_ARY_LBL_MESSAGE_DATE).length == 0) {
%>
		<tr>
			<td class="InputTd" id="tdNoMessage">
				<div><%= PfNameUtility.noMessage(params) %></div>
			</td>
		</tr>
<%
} else if (MospUtility.isEqual(vo.getPortalParameter(PortalMessageBean.PRM_LBL_MESSAGE_COUNT), String.valueOf(0)) == false) {
%>
		<tr>
			<td colspan="3">
				<div>
					<a onclick="submitTransfer(event, null, null, new Array('<%= PortalAction.PRM_PORTAL_BEAN_CLASS_NAME %>', '<%= PortalMessageBean.class.getName() %>'), '<%= PortalAction.CMD_REGIST %>')">
						<%= NameUtility.other(params) %><%= NameUtility.count(params, MospUtility.getInt(vo.getPortalParameter(PortalMessageBean.PRM_LBL_MESSAGE_COUNT))) %>
					</a>
				</div>
			</td>
		</tr>
<%
}
%>
	</table>
</div>
