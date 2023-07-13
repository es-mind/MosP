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
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.platform.human.base.PlatformHumanVo"
import = "jp.mosp.framework.property.ConventionProperty"
import = "jp.mosp.framework.property.ViewConfigProperty"
import = "jp.mosp.framework.xml.TableItemProperty"
import = "jp.mosp.framework.xml.ItemProperty"
%><%
// MosP処理情報・画面情報取得
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PlatformHumanVo vo = (PlatformHumanVo)params.getVo();
// MosP処理情報から必要な情報を取得
String division = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_DIVISION);
//人事汎用管理区分設定取得
ViewConfigProperty viewConfig = params.getProperties().getViewConfigProperties().get(division);
String divisionType = viewConfig.getType();
//MosP処理情報から情報を取得
String itemKey= (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_ITEM_ITEM_KEY);
String itemName = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_ITEM_ITEM_NAME);
String activeDate = (String)params.getGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE);
String rowId = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_ARRAY_ROW_ID);

//人事汎用項目区分設定情報を取得
ConventionProperty convention = params.getProperties().getConventionProperties().get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
//人事汎用項目区分情報から人事汎用表示テーブル項目で人事汎用項目情報を取得
ItemProperty itemProperty = convention.getItem(itemKey);
%>

<%
// 人事汎用項目形式情報取得
String itemType = itemProperty.getType();
String css = itemProperty.getCss();
if (css == null) {
	css = "";
} else {
	css = "class=\"" + css + "\"";
}
// MosP処理情報から固定値要否情報取得
Boolean needFixedValue = (Boolean)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE);

// 人事汎用項目形式情報確認(labelの場合) 
if(itemType.equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_LABEL) ){
	// 通常の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)){
		// 固定値確認
		if (itemProperty.getFixedValue() != null && needFixedValue == null ) {
%>
	<span <%= css %>><%= itemProperty.getFixedValue()  %></span>
<%
		} else if(itemProperty.getFixedValue() != null && needFixedValue == true) {
%>
	<span <%= css %>></span>
<%
		} else {
%>
	<span <%= css %>><%= vo.getNormalItem(division,itemName) %></span>
<%		
		}
	}
	// 履歴の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)){
		// 固定値確認
		if (itemProperty.getFixedValue() != null && needFixedValue == null ) {
%>
	<span <%= css %>><%= itemProperty.getFixedValue()  %></span>
<%
		} else if(itemProperty.getFixedValue() != null && needFixedValue == true) {
%>
	<span <%= css %>></span>
<%
		} else {
%>
	<span <%= css %>><%= vo.getHistoryItem(division, activeDate, itemName) %></span>
<%		
		}
	}
	// 一覧の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)){
		// 固定値確認
		if (itemProperty.getFixedValue() != null && needFixedValue == null ) {
%>
	<span <%= css %>><%= itemProperty.getFixedValue()  %></span>
<%
		} else if(itemProperty.getFixedValue() != null && needFixedValue == true) {
%>
	<span <%= css %>></span>
<%
		} else {
%>
	<span <%= css %>><%= vo.getArrayItem(division,rowId,itemName) %></span>
<%	
		}
	}
}

// 人事汎用項目形式情報取得
if(itemType.equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_CONCATENATED_LABEL) ){
	// 通常の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)){
%>
	<span <%= css %>><%= vo.getNormalItem(division, itemName) %></span>
<%	
	// 固定値設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE,null);
		// 値がない場合
		if(vo.getNormalItem(division, itemName).isEmpty() || vo.getNormalItem(division, itemName) == null){
		// 固定値要否確認設定
		needFixedValue = true;
		// MosP処理情報に設定
		params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE,needFixedValue);
		}
	}
	// 履歴の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)){
%>
	<span <%= css %>><%= vo.getHistoryItem(division, activeDate, itemName) %></span>
<%	
		//固定値設定
		params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE,null);
		// 値がない場合
		if(vo.getHistoryItem(division, activeDate, itemName).isEmpty() || vo.getHistoryItem(division, activeDate, itemName) == null){
			// 固定値要否確認設定
			needFixedValue = true;
			// MosP処理情報に設定
			params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE,needFixedValue);
		}
	}
	// 一覧の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)){
%>
	<span <%= css %>><%= vo.getArrayItem(division,rowId,itemName) %></span>
<%	
		//固定値設定
		params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE,null);
		// 値がない場合
		if(vo.getArrayItem(division,rowId,itemName).isEmpty() || vo.getArrayItem(division,rowId,itemName) == null){
			// 固定値要否確認設定
			needFixedValue = true;
			// MosP処理情報に設定
			params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE,needFixedValue);
		}
	}
}

// 人事汎用項目形式情報確認(textの場合) 
if (itemType.equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_TEXT)) {
	// 通常の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)){
%>
	<input type="<%= itemType %>" <%= css %> name="<%=itemName %>" id="<%=itemName %>" value="<%= vo.getNormalItem(division,itemName) %>" />
<%	
	}
	// 履歴の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)){
%>
	<input type="<%= itemType %>" <%= css %> name="<%=itemName %>" id="<%=itemName %>" value="<%= vo.getHistoryItem(division,activeDate,itemName) %>" />
<%		
	}
	// 一覧の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)){
%>
	<input type="<%= itemType %>" <%= css %> name="<%=itemName %>" id="<%=itemName %>" value="<%= vo.getArrayItem(division,rowId,itemName) %>" />
<%		
	}
}

//人事汎用項目形式情報確認(textAreaの場合) 
if (itemType.equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_TEXTAREA)) {
	// 通常の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)){
%>
	<textarea <%= css %> name="<%=itemName %>" id="<%=itemName %>" ><%= vo.getNormalItem(division,itemName) %></textarea>
<%	
	}
	// 履歴の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)){
%>
	<textarea  <%= css %> name="<%=itemName %>" id="<%=itemName %>"><%= vo.getHistoryItem(division,activeDate,itemName) %></textarea>
<%		
	}
	// 一覧の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)){
%>
	<textarea <%= css %> name="<%=itemName %>" id="<%=itemName %>"><%= vo.getArrayItem(division,rowId,itemName) %></textarea>
<%		
	}
}

// 人事汎用項目形式情報確認(selectの場合) 
if (itemType.equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_SELECT)) {
	// 通常の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)){
%>
	<select <%= css %> name="<%=itemName %>" id="<%=itemName %>"><%= HtmlUtility.getSelectOption(vo.getPltItem(itemName),vo.getNormalItem(division,itemName)) %></select>
<%
	}
	// 履歴の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)){
%>
	<select <%= css %> name="<%=itemName %>" id="<%=itemName %>"><%= HtmlUtility.getSelectOption(vo.getPltItem(itemName),vo.getHistoryItem(division,activeDate,itemName)) %></select>
<%		
	}
	// 一覧の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)){
%>
	<select <%= css %> name="<%=itemName %>" id="<%=itemName %>"><%= HtmlUtility.getSelectOption(vo.getPltItem(itemName),vo.getArrayItem(division,rowId,itemName)) %></select>
<%			
	}
}
// 人事汎用項目形式情報確認(checkboxの場合) 
if (itemType.equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_CHECK_BOX)) {
	// 通常の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)){
%>
	<input type="<%= itemType %>" <%= css %> name="<%=itemName %>" id="<%=itemName %>" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getNormalItem(division,itemName)) %> />
<%	
	}
	// 履歴の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)){
%>
	<input type="<%= itemType %>" <%= css %> name="<%=itemName %>" id="<%=itemName %>" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getHistoryItem(division,activeDate,itemName)) %> />
<%		
	}
	// 一覧の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)){
%>
	<input type="<%= itemType %>" <%= css %> name="<%=itemName %>" id="<%=itemName %>" value="<%= MospConst.CHECKBOX_ON %>" <%= HtmlUtility.getChecked(vo.getArrayItem(division,rowId,itemName)) %> />
<%	
	}
}
// 人事汎用項目形式情報確認(radiobuttonの場合) 
if (itemType.equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_RADIO)) {
	// 通常の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)){
%>
	<%= HtmlUtility.getRadioButonInput(css, itemName, vo.getPltItem(itemName),vo.getNormalItem(division,itemName)) %>
<%	
	}
	// 履歴の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)){
%>
	<%= HtmlUtility.getRadioButonInput(css, itemName, vo.getPltItem(itemName),vo.getHistoryItem(division,activeDate,itemName)) %>
<%		
	}
	// 一覧の場合
	if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)){
%>
	<%= HtmlUtility.getRadioButonInput(css, itemName, vo.getPltItem(itemName),vo.getArrayItem(division,rowId,itemName)) %>	
<%	
	}
}
%>
