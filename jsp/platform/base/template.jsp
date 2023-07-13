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
buffer       = "512kb"
autoFlush    = "true"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.base.BaseVo"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.portal.vo.LoginVo"
%><%
final String APP_BASE_JSP_FILES = "BaseJspFiles";
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
BaseVo vo = params.getVo();
int idx = 0;
String[] baseJspFiles = params.getApplicationProperties(APP_BASE_JSP_FILES);
String onsubmit = vo instanceof LoginVo ? "" : "return false;";
String propertyTime = params.getApplicationProperty(MospConst.APP_PROPERTY_TIME);
%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noindex,nofollow" />
<meta charset="<%= params.getApplicationProperty(MospConst.APP_CHARACTER_ENCODING) %>" />
<%
for (String extraMeta : params.getApplicationProperties(MospConst.APP_EXTRA_HTML_META)) {
%>
<%= extraMeta %>
<%
}
for (String cssFile : params.getCssFiles()) {
%>
<link href="..<%= cssFile %>?var=<%= propertyTime %>" rel="stylesheet" type="text/css" />
<%
}
for (String jsFile : params.getJsFiles()) {
%>
<script type="text/javascript" src="..<%= jsFile %>?var=<%= propertyTime %>"></script>
<%
}
%>
<jsp:include page="<%= baseJspFiles[idx++] %>" flush="false" />
<title>
	<%= params.getApplicationProperty(MospConst.APP_TITLE_PREFIX) %>
	<%= params.getApplicationProperty(MospConst.APP_TITLE) %>
	<%= params.getApplicationProperty(MospConst.APP_VERSION) %>
</title>
<link rel="shortcut icon" href="<%= params.getApplicationProperty(MospConst.APP_FAVICON_IMAGE) %>" />
</head>
<body id="base" onload="onLoad()">
<form name="form" onsubmit="<%= onsubmit %>">
<!-- ヘッダー(タイトルバー) -->
<div class="Header">
<jsp:include page="<%= baseJspFiles[idx++] %>" flush="false" />
</div>
<!-- ナビ(メニュー) -->
<div class="Navi">
<%
if (params.getNaviUrl() != null) {
%>
<jsp:include page="<%= params.getNaviUrl() %>" flush="false" />
<%
}
%>
</div>
<%= HtmlUtility.getMessageDiv(params) %>
<!-- アーティクル(内容) -->
<div class="Body" id="divBody">
<jsp:include page="<%= params.getArticleUrl() %>" flush="false" />
</div>
<!-- フッター -->
<div class="Footer">
<jsp:include page="<%= baseJspFiles[idx++] %>" flush="false" />
</div>
</form>
</body>
</html>
