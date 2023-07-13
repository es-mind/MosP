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
buffer       = "16kb"
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.base.BaseVo"
import = "jp.mosp.framework.base.TopicPath"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.TopicPathUtility"
import = "jp.mosp.platform.base.PlatformVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
BaseVo vo = params.getVo();
if (params.getUser() == null) {
	return;
}
%>
<table class="MenuTable">
	<tr id="trMainMenu"></tr>
</table>
<div class="MenuTab" id="divSubMenu">
</div>
<div class="TopicPath">
<%
int i = 0;
for (TopicPath topicPath : params.getTopicPathList()) {
	if (i < params.getTopicPathList().size() - 1) {
		i++;
%>
	<span>
		<a onclick="submitTransfer(event, null, null, null, '<%= topicPath.getCommand() %>')"><%= TopicPathUtility.getTopicPathTitle(params, topicPath) %></a>
	</span>
	&gt;
<%
	} else {
%>
	<span id="now"><%= TopicPathUtility.getTopicPathTitle(params, topicPath) %></span>
<%
	}
}
%>
</div>
