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
language     = "java"
pageEncoding = "UTF-8"
buffer       = "256kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.platform.portal.action.PortalAction"
import = "jp.mosp.platform.portal.vo.PortalVo"
import = "jp.mosp.time.portal.bean.impl.PortalAttendanceCheckBean"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
String mes = (String)params.getGeneralParam(PortalAttendanceCheckBean.PRM_ATTENDANCE_CHECK_MESSAGE);
%>
<%if(mes != null && mes.isEmpty() == false) { %>
<script language="Javascript">
<!--
function onLoadExtra(){
	// setToDayTableColor関数が存在する場合
	if (typeof setToDayTableColor == "function") {
		// 勤怠一覧用当日背景色設定
		setToDayTableColor("list");
	}
	alert("<%=mes%>");
}
//-->

</script>
<%
}
%>
