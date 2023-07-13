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
isErrorPage  = "true"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.LogUtility"
%><%
// JSP用エラーハンドリング
try {
	if (response.isCommitted() == false) {
		MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
		// エラーログ出力
		if (exception != null) {
			LogUtility.error(params, exception);
		}
		// セッションの初期化
		if (session != null) {
			session.invalidate();
		}
		request.getSession(true);
		// レスポンスステータス設定
		response.setStatus(200);
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
	<div class="ErrorMessageLabel">画面表示の際にエラーが発生しました。</div>
	<br />
	必要なら管理者に連絡してください。
</div>
</body>
</html>
<%
	}
} catch (Exception e) {
	e.printStackTrace();
}
%>
