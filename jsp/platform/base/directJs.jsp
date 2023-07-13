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
autoFlush    = "true"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "java.lang.reflect.Field"
import = "jp.mosp.framework.base.BaseVo"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.js.DirectJs"
import = "jp.mosp.framework.utils.MenuJsUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "net.arnx.jsonic.JSON"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
BaseVo vo = params.getVo();
%>
<script type="text/javascript">
var procSeq = "<%= params.getProcSeq() %>";
<%
// VOから変数(modeXXX or jsXXX)を取得
Class<?> cls = vo.getClass();
while (cls != null) {
	Field[] fields = cls.getDeclaredFields();
	if (fields == null) {
		cls = cls.getSuperclass();
		continue;
	}
	for (Field field : fields) {
		if (writable(field) == false) {
			continue;
		}
		String fieldName = field.getName();
		// 有効日モード出力
		out.println(toModeActivateDate(fieldName));
		// 編集モード出力
		out.println(toModeCardEdit(fieldName));
		// 値の取得
		field.setAccessible(true);
		Object value = field.get(vo);
		if (value == null) {
			continue;
		}
		String name = field.getName();
		DirectJs directJs = field.getAnnotation(DirectJs.class);
		if (directJs !=null) {
			String direct = directJs.value();
			if (direct != null &&  direct.isEmpty() == false) {
				name = direct;
			}
		}
		// 値の出力
		out.println(convert(name, value));
	}
	// 継承元クラス取得
	cls = cls.getSuperclass();
}
%>
<%
// MosP汎用パラメータから変数(jsXXX)を取得
for (String key : params.getGeneralParamKeySet()) {
	if (key.indexOf(PlatformConst.PREFIX_DIRECT_JS) != 0) {
		continue;
	}
	out.println(convert(key, params.getGeneralParam(key)));
}
%>
<%
if (params.getUser() != null) {
%>
SELECT_MENU = <%= "'" + MenuJsUtility.getSelectMenu(params) + "'" %>;
<%= MenuJsUtility.getMenuJs(params) %>
<%
}
%>
</script>
<%-- modeActivateDate --%>
<%!
String toModeActivateDate(String name) {
	if (name == null) {
		return "";
	}
	
	if (name.contains("modeActivateDate") == false) {
		return "";
	}
	StringBuffer sb = new StringBuffer();
	sb.append("var MODE_ACTIVATE_DATE_FIXED = '"+ PlatformConst.MODE_ACTIVATE_DATE_FIXED + "';");
	sb.append("var MODE_ACTIVATE_DATE_CHANGING = '" + PlatformConst.MODE_ACTIVATE_DATE_CHANGING + "';");
	return sb.toString();
}
%>
<%-- modeCardEdit --%>
<%! 
String toModeCardEdit(String name) {
	if (name == null) {
		return "";
	}
	if (name.contains("modeCardEdit") == false) {
		return "";
	}
	StringBuffer sb = new StringBuffer();
	sb.append("var MODE_CARD_EDIT_INSERT = '" + PlatformConst.MODE_CARD_EDIT_INSERT + "';");
	sb.append("var MODE_CARD_EDIT_ADD = '" + PlatformConst.MODE_CARD_EDIT_ADD + "';");
	sb.append("var MODE_CARD_EDIT_EDIT = '" + PlatformConst.MODE_CARD_EDIT_EDIT + "';");
	return sb.toString();
}
%>
<%-- DirextJSでの出力判定 --%>
<%!
boolean writable(Field field) {
	if (field == null) {
		return false;
	}
	String name = field.getName();
	if (name.matches("(^mode.*|^js.*)")) {
		return true;
	}
	DirectJs directJs = field.getAnnotation(DirectJs.class);
	if (directJs !=null) {
		return true;
	}
	return false;
}
%>
<%-- 変換 --%>
<%!
String convert(String name, Object value) {
	if (name == null || value == null) {
		return "";
	}
	StringBuffer sb = new StringBuffer();
	sb.append("var " + name + " = ");
	sb.append(JSON.escapeScript(value));
	sb.append(";");
	return sb.toString();
}
%>
