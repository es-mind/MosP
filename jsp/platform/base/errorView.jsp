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
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>MosPエラーページ</title>
	<link href="../pub/common/css/mosp.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../pub/common/js/mosp.js"></script>
</head>
<body>
<div id="error">
	<br />
	<br />
	<div class="ErrorMessageLabel">エラーが発生しました。</div>
	<br />
	必要なら管理者に連絡してください。
</div>
<%= HtmlUtility.getMessageDiv(params) %>
</body>
</html>
