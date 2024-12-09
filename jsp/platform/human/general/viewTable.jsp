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
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.framework.property.ViewConfigProperty"
import = "jp.mosp.framework.property.ConventionProperty"
import = "jp.mosp.framework.xml.TableItemProperty"
import = "jp.mosp.framework.xml.ViewTableProperty"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.base.PlatformHumanVo"
import = "jp.mosp.platform.human.action.HumanArrayCardAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "java.util.List"
import = "java.util.ArrayList"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PlatformHumanVo vo = (PlatformHumanVo)params.getVo();
// MosP処理情報から人事汎用管理区分及び表示テーブルを取得
String division = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_DIVISION);
String viewTableKey = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_VIEW_TABLE);
// 人事汎用管理区分設定取得
ViewConfigProperty viewConfig = params.getProperties().getViewConfigProperties().get(division);
// 人事汎用表示テーブル設定取得
ViewTableProperty viewTableProperty = viewConfig.getViewTable(viewTableKey);
// 人事汎用項目リスト取得
List<TableItemProperty> tableItemList = viewTableProperty.getTableItem(); 
// 人事汎用表示テーブル形式取得
String viewTableType = viewTableProperty.getType();
// 人事汎用管理区分設定取得
ConventionProperty convention = params.getProperties().getConventionProperties().get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
// セル結合行数取得
int pair = viewTableProperty.getPair();
if (pair == 0) {
	pair = 1;
}
%>
<%
// 人事汎用表示テーブル形式確認(書類テーブルの場合)
if (viewTableType.equals(PlatformHumanConst.TYPE_VIEW_TABLE_CARD)) {
%>	
<table class="UnderTable" id="under<%=division %>Table">
<%
		// 人事汎用表示テーブル項目設定情報毎に処理
		// 項目数準備
		int itemCount = 0;
		// 行結合値準備
		int rowspanCount = 0;
		for(TableItemProperty tableItem : tableItemList){
			// スタイル設定取得
			int colspan = tableItem.getColspan();
			int rowspan = tableItem.getRowspan();	
			boolean isRequired = tableItem.isRequired();
			// 人事汎用表示テーブル項目キー取得
			String tableItemKey = tableItem.getKey();
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			String[] itemNames = tableItem.getItemNames();
			// 配列(String)を区切文字で区切った文字列取得
			String itemNameCsv = MospUtility.toSeparatedString(itemNames,MospConst.APP_PROPERTY_SEPARATOR);
			// 項目数(項目名)と結合
			if (itemCount % pair == 0) {
				// 行結合設定がある場合
				if(rowspanCount >1){
					// 行結合カウントを減らす
					rowspanCount--;
					// 項目カウントを増やす
					itemCount++;
				}
%>
	<tr>
<%				
			}
%>	
		<td class="TitleTd" id="td<%=tableItemKey %>Title" <% if(rowspan !=0) { %> rowspan ="<%= rowspan %>" <% } %> ><%if(isRequired){%><%= HtmlUtility.getRequiredMark() %><% }%><span><label for="<%= itemNameCsv %>"><%= NameUtility.getName(params, tableItemKey) %></label></span></td>
		<td class="InputTd"  id="td<%=tableItemKey %>Body" colspan="<%= colspan %>"  <% if(rowspan !=0) { %> rowspan = "<%= rowspan %>" <% } %>>
<%
			//人事汎用項目キー毎に処理
			for (int i = 0; i < itemKeys.length; i++) {
				// MosP処理情報に設定
				params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_ITEM_ITEM_KEY,itemKeys[i]);
				params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_ITEM_ITEM_NAME,itemNames[i]);
%>
			<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_ITEM_JSP %>" flush="false" /> 
<%
			}
			// 固定値設定
			params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE,null);
%>
		</td>
<%
			//行結合設定がある場合
			if (rowspan > 1) {
				// 行結合数カウント
				rowspanCount = rowspan;
			}
			// 項目数カウント
			itemCount++;
			// 列結合設定がある場合
			if (colspan > 1) {
				// 項目数 + (td + タイトルtd)/2-自分=項目数
				itemCount += (colspan + 1)/2-1;	
			}
			// 項目数が0ではなくかつ項目数と結合数を割った値が0でない場合
			if (itemCount != 0 && itemCount % pair == 0) {
				// 改行
%>
	</tr>
<%			
			}
		}
		// 項目数と結合数を割った値が0ではない場合
		if (itemCount % pair != 0) {
			// 列結合した余りのセルを隠す
			// 改行
%>
		<td class="Blank" colspan="<%= (pair - itemCount % pair) * 2 %>"></td>
	</tr>
<%
		}
%>
</table>
<%
}
// 人事汎用表示テーブル形式確認(一覧テーブルの場合)
if (viewTableType.equals(PlatformHumanConst.TYPE_VIEW_TABLE_ARRAY)) {
	// 人事汎用表示テーブル設定取得
	String dateName = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_DATE_NAME);
%>
<table class="UnderTable" id="under<%=division %>Table">
	<tr>
		<td class="TitleTd" id="arrayButton"></td>	
		<td class="TitleTd" id="arrayActivateDate">
<% 
	//日付名が設定されている場合
	if(dateName.isEmpty() == false && dateName != null){
%>
		<%= NameUtility.getName(params, dateName) %>
<%
	} else{
%>
		<%= PfNameUtility.activateDate(params) %>
<%
	}
%>
		</td>
<%
	// 人事汎用項目毎に処理
	for(TableItemProperty tableItem : tableItemList){
		// 人事汎用項目キーを取得
		String[] itemKeys = tableItem.getItemKeys();
		String[] itemNames = tableItem.getItemNames();
		// 人事汎用表示テーブル項目キー取得
		String tableItemKey = tableItem.getKey();
		// 配列(String)を区切文字で区切った文字列取得
		String itemKeyCsv = MospUtility.toSeparatedString(itemKeys,MospConst.APP_PROPERTY_SEPARATOR);
%>	
		<td class="TitleTd" id="td<%= tableItemKey %>Title"><label for="<%= itemKeyCsv %>"><%= NameUtility.getName(params, tableItemKey) %></label></td>
<%
	}
%>
	</tr>
	<tr>
<%		
	// 行IDリストを取得
	List<String> rowIdList = new ArrayList<String>(vo.getHumanArrayMap().get(division).keySet());
	// 行IDリスト毎に処理
	for(String rowId :rowIdList){
		// ボタン
%>
		<td class="ListSelectTd">
			<button type="button" id="tdButtoun" class="Name2Button"
					onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_CODE %>', '<%= rowId %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanArrayCardAction.CMD_EDIT_SELECT %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
					<%= PfNameUtility.select(params) %>
			</button>
		</td>
		<td class="ListInputTd" id="tdAryActiveDate" >
		<span><%= vo.getArrayItem(division,rowId,PlatformHumanConst.PRM_HUMAN_ARRAY_DATE) %></span>
		</td>
<% 		
		//人事汎用項目毎に処理
		for(TableItemProperty tableItem : tableItemList){
			// MosP処理情報に行ID設定
			params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_ARRAY_ROW_ID,rowId);
			// 人事汎用項目キーを取得
			String[] itemKeys = tableItem.getItemKeys();
			String[] itemNames = tableItem.getItemNames();
%>
		<td class="InputTd"  >
<%
			//人事汎用項目キー毎に処理
			for (int i = 0; i < itemKeys.length; i++) {
			// MosP処理情報に設定
			params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_ITEM_ITEM_KEY,itemKeys[i]);
			params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_ITEM_ITEM_NAME,itemNames[i]);
%>
			<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_ITEM_JSP %>" flush="false" /> 
<%
			}
			// 固定値設定
			params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_NEED_FIXED_VALUE,null);
%>
		</td>
<%
		}
%>
	</tr>
<%
	}
%>
</table>
<%
}
%>
