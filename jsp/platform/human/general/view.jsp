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
import = "jp.mosp.framework.property.ViewConfigProperty"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.framework.utils.RoleUtility"
import = "jp.mosp.framework.xml.ViewProperty"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.human.base.PlatformHumanVo"
import = "jp.mosp.platform.human.constant.PlatformHumanConst"
import = "jp.mosp.platform.human.utils.HuNameUtility"
import = "jp.mosp.platform.human.action.HumanHistoryCardAction"
import = "jp.mosp.platform.human.action.HumanHistoryListAction"
import = "jp.mosp.platform.human.action.HumanArrayCardAction"
import = "jp.mosp.platform.human.action.HumanNormalCardAction"
import = "jp.mosp.platform.human.action.HumanBinaryHistoryCardAction"
import = "jp.mosp.platform.human.action.HumanBinaryHistoryListAction"
import = "jp.mosp.platform.human.action.HumanBinaryArrayCardAction"
import = "jp.mosp.platform.human.action.HumanBinaryNormalCardAction"
import = "jp.mosp.platform.human.action.HumanInfoAction"
import = "jp.mosp.framework.xml.ViewTableProperty"
import = "jp.mosp.platform.human.action.HumanBinaryOutputFileAction"
%><%
// MosP処理情報取得
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
// MosP処理情報から人事汎用管理区分及び表示区分を取得
String division = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_DIVISION);
String view = (String)params.getGeneralParam(PlatformHumanConst.PRM_HUMAN_VIEW);
// 設定されていない場合
if(division == null){
	// 表示なし
	return;
}
// 人事汎用管理区分参照権限
boolean isReferenceDivision = RoleUtility.getReferenceDivisionsList(params).contains(division);
// 人事汎用管理区分設定取得
ViewConfigProperty viewConfig = params.getProperties().getViewConfigProperties().get(division);
String divisionType = viewConfig.getType();
// 人事汎用表示区分設定取得
ViewProperty viewProperty = viewConfig.getView(view);
// 人事汎用表示テーブル配列取得
String[] viewTableKeys = viewProperty.getViewTableKeys();
String[] viewTitles = viewProperty.getViewTableTitles();
// 人事情報一覧VOを取得
PlatformHumanVo vo = (PlatformHumanVo)params.getVo();
%>

<div class="List">
<%
// 表示テーブル毎に処理(帯作成)
for(int i=0; i<viewTableKeys.length; i++){	
	// 人事情報一覧画面の場合
	if (view.equals(HumanInfoAction.KEY_VIEW_HUMAN_INFO) ) {
		// 通常の場合
		if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)){
%>
		<table class="OverTable">
			<tr>
				<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh">
				<%= NameUtility.getName(params, viewTitles[i]) %></span>
					<span class="TableButtonSpan">
						<button type="button" class="Name2Button"
							onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanNormalCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
							<% // 更新権限がある場合 %>
							<%= isReferenceDivision ? PfNameUtility.reference(params) : PfNameUtility.edit(params)%>
						</button>
					</span>
				</th>
			</tr>
		</table>
<%
			// 1つも登録されていない場合帯だけ表示する。
			if (vo.getNormalMapInfo(division).isEmpty()) {
				return;
			}
		}
		// バイナリ通常の場合
		if (divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_NORMAL)) {
			// 値をVOから取得
			String[] fileType = vo.getAryBinaryFileTypeMap(division);
			String[] fileName = vo.getAryBinaryFileNameMap(division);
			String[] fileRemark = vo.getAryBinaryFileRemarkMap(division);
%>
			<table class="OverTable">
				<tr>
					<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh">
					<%= NameUtility.getName(params, viewTitles[i]) %></span>
						<span class="TableButtonSpan">
							<button type="button" class="Name2Button"
								onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanBinaryNormalCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
								<% // 更新権限がある場合%>
								<%= isReferenceDivision ? PfNameUtility.reference(params) : PfNameUtility.edit(params)%>
							</button>
						</span>
					</th>
				</tr>
			</table>
<%
			// 情報がない場合帯だけ
			if (fileName[0]==null || fileName.length ==0) {
				return;
			}
%>		
			<table class="UnderTable">
			<tr>
				<td class="TitleTd" id="tdBinaryFileNameTitle"><%= PfNameUtility.fileName(params) %></td>
				<td class="InputTd" id="tdBinaryFileNameBody"><%= HtmlUtility.escapeHTML(fileName[0]) %></td>
			</tr>
			<tr>
				<td class="TitleTd" id="tdBinaryRemarksTitle" ><%=PfNameUtility.remarks(params) %></td>
				<td class="InputTd" id="tdFileRemarkBody"><%= HtmlUtility.escapeHTML(fileRemark[0]) %></td>
<% 
			// ファイル出力の場合
			if (fileType[0].equals(MospConst.CODE_BINARY_FILE_FILE)) {
%>
				<td class="TitleTd"><%= HuNameUtility.registeredFile(params) %></td>
				<td>	
					<button type="button" class="Name4Button" id="btnOutput" onclick="submitFile(event, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>'), '<%= HumanBinaryOutputFileAction.CMD_NORAML_INFO_FILE %>');"><%= PfNameUtility.output(params) %></button>
				</td>
<%				
			}
%>
			</tr>
		</table>
<%
			// バイナリ区分
			return;
		}
		// 履歴の場合
		if (divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
			// 人事汎用表示テーブル設定取得
			ViewTableProperty viewTableProperty = viewConfig.getViewTable(viewTableKeys[i]);
			// 日付名設定
			params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DATE_NAME,viewTableProperty.getDateName());
%>
		<table class="OverTable">
			<tr>
				<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh"><%= NameUtility.getName(params, viewTitles[i]) %></span>
					<span class="TableButtonSpan">
						<% // 更新権限がある場合%>
						<% if (!isReferenceDivision) { %>
							<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanHistoryCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
								<%= PfNameUtility.addHistory(params)%>
							</button>
						<% } %>
						<%
						// 登録済みの場合だけ一覧ボタンを表示する。
						if (vo.getHistoryMapInfo(division).isEmpty() ==false) {
						%>&nbsp;
						<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>','<%= HumanHistoryListAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
							<%= PfNameUtility.historyList(params) %>
						</button>
						<%
						}
						%>
					</span>	
				</th>
			</tr>
		</table>
<%
			//1つも登録されていない場合帯だけ表示する。
			if (vo.getHistoryMapInfo(division).isEmpty()) {
				return;
			}
		}
		// バイナリ履歴の場合
		if (divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_HISTORY)) {
			// 値をVOから取得
			String[] activeDate = vo.getAryBinaryActiveDateMap(division);
			String[] fileType = vo.getAryBinaryFileTypeMap(division);
			String[] fileName = vo.getAryBinaryFileNameMap(division);
			String[] fileRemark = vo.getAryBinaryFileRemarkMap(division);
	%>
		<table class="OverTable">
			<tr>
				<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh"><%= NameUtility.getName(params, viewTitles[i]) %></span>
					<span class="TableButtonSpan">
						<% // 更新権限がある場合%>
						<% if (!isReferenceDivision) { %>
						<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanBinaryHistoryCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
							<%= PfNameUtility.addHistory(params) %>
						</button>
						<% } %>
						<%
						// 登録済みの場合だけ一覧ボタンを表示する。
						if (activeDate[0] != null) {
						%>&nbsp;
						<button type="button" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>','<%= HumanBinaryHistoryListAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
							<%= PfNameUtility.historyList(params) %>
						</button>
						<%
						}
						%>
					</span>	
				</th>
			</tr>
		</table>
<%
			// 情報がない場合帯だけ
			if (activeDate[0] == null || activeDate.length == 0) {
				return;
			}
%>
		<table class="UnderTable" id="under<%=division%>Table">
			<tr>
				<td class="TitleTd" id="tdBinaryActivateDateTitle"><%=PfNameUtility.activateDate(params) %></td>
				<td class="InputTd" id="tdBinaryActivateDateBody"><%= HtmlUtility.escapeHTML(activeDate[0]) %></td>
				<td class="TitleTd" id="tdBinaryFileNameTitle" ><%= PfNameUtility.fileName(params) %></td>
				<td class="InputTd" id="tdBinaryFileNameBody"<% if (fileType[0].equals(MospConst.CODE_BINARY_FILE_FILE)) {%>colspan="3"<%} %> ><%= HtmlUtility.escapeHTML(fileName[0]) %></td>
			</tr>
			<tr>
<% 
			// ファイル出力の場合
			if (fileType[0].equals(MospConst.CODE_BINARY_FILE_FILE)) {
%>
				<td class="TitleTd" id="tdBinaryFileOutputTitle"><%= PfNameUtility.outputFile(params) %></td>
				<td class="ListSelectTd">	
					<button type="button" class="Name4Button" id="btnBinaryFileOutput" onclick="submitFile(event, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>','<%= activeDate[0] %>'), '<%= HumanBinaryOutputFileAction.CMD_HISTORY_INFO_FILE %>');"><%= PfNameUtility.output(params) %></button>
				</td>
<%				
			}
%>
				<td class="TitleTd" id="tdBinaryFileRemarkTitle"><%= HuNameUtility.fileRemarks(params) %></td>
				<td class="InputTd" id="tdBinaryFileRemarkBody" colspan="3"><%= HtmlUtility.escapeHTML(fileRemark[0]) %></td>
			</tr>
			
		</table>
	<%
	// バイナリ区分
		return;
			}
			// 一覧の場合
			if (divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
		// 人事汎用表示テーブル設定取得
		ViewTableProperty viewTableProperty = viewConfig.getViewTable(viewTableKeys[i]);
		// 日付名設定
		params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DATE_NAME,viewTableProperty.getDateName());
	%>
		<table class="OverTable">
			<tr>
				<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh"><%= NameUtility.getName(params, viewTitles[i]) %></span>
			  		<span class="TableButtonSpan">
						<%
						// 更新権限がある場合
						%>
						<%
						if (!isReferenceDivision) {
						%>
				     	    <button type="button" class="Name2Button"
								onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanArrayCardAction.class.getName() %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
								<%= PfNameUtility.add(params) %>
							</button>
						<%
						}
						%>
					</span>
				</th>
			</tr>
		</table>
<%
//1つも登録されていない場合帯だけ表示
	if(vo.getArrayMapInfo(division).isEmpty()){
		return;
	}
		}
		// バイナリ一覧の場合
		if(divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_ARRAY)){
	// 人事汎用表示テーブル設定取得
	ViewTableProperty viewTableProperty = viewConfig.getViewTable(viewTableKeys[i]);
	// 日付名設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DATE_NAME,viewTableProperty.getDateName());
	// 値をVOから取得
	String[] rowId = vo.getAryBinaryRowIdMap(division);
	String[] activeDate = vo.getAryBinaryActiveDateMap(division);
	String[] fileType = vo.getAryBinaryFileTypeMap(division);
	String[] fileName = vo.getAryBinaryFileNameMap(division);
	String[] fileRemark = vo.getAryBinaryFileRemarkMap(division);
%>
		<table class="OverTable">
			<tr>
				<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh"><%=NameUtility.getName(params, viewTitles[i])%></span>
			  		<span class="TableButtonSpan">
						<%
						// 更新権限がある場合
						%>
						<%
						if (!isReferenceDivision) {
						%>
				     	    <button type="button" class="Name2Button" id="btnBinaryFileOutput"
								onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_TYPE%>','<%=division%>','<%=PlatformConst.PRM_TRANSFERRED_ACTION%>', '<%=HumanBinaryArrayCardAction.class.getName()%>'), '<%=HumanInfoAction.CMD_TRANSFER%>');">
								<%= PfNameUtility.add(params) %>
							</button>
						<%
						}
						%>
					</span>
				</th>
			</tr>
		</table>
<%
// 情報がない場合
	if(rowId.length == 0){
		return;
	}
%>
		<table class="UnderTable">
			<tr>
				<td class="TitleTd" id="tdBinaryButtonTitle"></td>	
				<td class="TitleTd" id="tdBinaryActivateDateTitle"><%= PfNameUtility.activateDate(params) %></td>
				<td class="TitleTd" id="tdBinaryFileNameTitle"><%= PfNameUtility.fileName(params) %></td>
				<td class="TitleTd" id="tdBinaryFileOutputTitle"><%= PfNameUtility.outputFile(params) %></td>
				<td class="TitleTd" id="tdBinaryFileRemarkTitle"><%= HuNameUtility.fileRemarks(params) %></td>
				
			</tr>
<%	
		// 情報毎に処理
		for (int idx = 0; idx < activeDate.length; idx++) {
%>
			<tr>
				<td>
					<button type="button" id="tdBinaryButtonBody" class="Name2Button"
						onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>', '<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= rowId[idx] %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>', '<%= HumanBinaryArrayCardAction.CMD_EDIT_SELECT %>'), '<%= HumanInfoAction.CMD_TRANSFER %>');">
						<%= PfNameUtility.select(params) %>
					</button>
				</td>
				<td class="InputTd" id="tdBinaryActivateDateBody"><%= HtmlUtility.escapeHTML(activeDate[idx]) %></td>
				<td class="InputTd" id="tdBinaryFileNameBody"><%= HtmlUtility.escapeHTML(fileName[idx]) %></td>
<% 
			// ファイル出力の場合
			if (fileType[idx].equals(MospConst.CODE_BINARY_FILE_FILE)) {
%>
				<td>	
					<button type="button" id="btnBinaryFileOutput" class="Name4Button" id="btnOutput" onclick="submitFile(event, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_INDEX %>', '<%= rowId[idx] %>'), '<%= HumanBinaryOutputFileAction.CMD_ARRAY_INFO_FILEE %>');"><%= PfNameUtility.output(params) %></button>
				</td>
<%				
			}
%>
				<td class="InputTd" id="tdBinaryFileRemarkBody"><%= HtmlUtility.escapeHTML(fileRemark[idx]) %></td>
			</tr>
<%
		}
%>
		</table>
	<%
			return;
		}
	}
	// 通常画面の場合
	if (view.equals(HumanNormalCardAction.KEY_VIEW_NORMAL_CARD) && divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
%>
		<table class="OverTable">
			<tr>
				<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh"><%= NameUtility.getName(params, viewTitles[i]) %></span>
				</th>
			</tr>
		</table>
<%
	}
	// 履歴登録画面の場合
	if(view.equals(HumanHistoryCardAction.KEY_VIEW_HUMAN_HISTORY_CARD) && divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)){
		// 人事汎用表示テーブル設定取得
		ViewTableProperty viewTableProperty = viewConfig.getViewTable(viewTableKeys[i]);
		// 日付名設定
		params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DATE_NAME,viewTableProperty.getDateName());
		// 画面区分(履歴追加/履歴編集)
		// 情報名配列取得
		String[] aryTitle = MospUtility.split(viewTitles[i], MospConst.APP_PROPERTY_SEPARATOR);
		// 情報名設定
		String title = aryTitle[0];
		// 画面区分(履歴追加/履歴編集)取得[HumanHistoryCard.jspで設定]
		String imegeDivision = (String)params.getGeneralParam(HumanHistoryCardAction.KEY_VIEW_HUMAN_HISTORY_CARD);
		// 履歴追加の場合
		if(imegeDivision != null && imegeDivision.equals(HumanHistoryCardAction.CMD_ADD_SELECT)){
			// 情報名配列に履歴追加名がある場合
			if(aryTitle.length>1 &&!aryTitle[1].isEmpty() && aryTitle[1] != null){
				// 情報名設定
				title = aryTitle[1];
			}
		}
%>
		<table class="OverTable">
			<tr>
				<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh"><%= NameUtility.getName(params, title) %></span>
				</th>
			</tr>
		</table>
<%
	}
	// 履歴一覧画面の場合
	if (view.equals(HumanHistoryListAction.KEY_VIEW_HISTORY_LIST) && divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
		// 有効日取得・設定
		String activeDate = (String)params.getGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE);
		params.addGeneralParam(PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE,activeDate);
		// 人事汎用表示テーブル設定取得
		ViewTableProperty viewTableProperty = viewConfig.getViewTable(viewTableKeys[i]);
%>
		<table class="OverTable">
			<tr>
				<th colspan="4" class="ListTableTh" id=<%= activeDate %>>
				<span class="TitleTh"><%= HtmlUtility.escapeHTML(activeDate) %><%= NameUtility.wave(params) %></span>
					<span class="TableButtonSpan">
					<button type="button" id="btnHumenInfo" class="Name4Button" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>',  '<%= activeDate %>','<%= PlatformConst.PRM_TRANSFERRED_TYPE %>',  '<%= division %>','<%= PlatformConst.PRM_TRANSFERRED_ACTION %>',  '<%= HumanHistoryCardAction.CMD_EDIT_SELECT %>'), '<%= HumanHistoryListAction.CMD_TRANSFER %>');">
					<% // 更新権限がある場合%>
					<%= isReferenceDivision ? PfNameUtility.referHistory(params) : PfNameUtility.edtiHistory(params)%>
					</button>&nbsp;
					<% // 更新権限がある場合%>
					<% if (!isReferenceDivision) { %>
						<button type="button" id="btnHumenInfo" name="btnDelete" class="Name4Button" onclick="submitTransfer(event,null,checkExtra,new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= activeDate %>','<%= PlatformConst.PRM_TRANSFERRED_TYPE %>','<%= division %>'),'<%= HumanHistoryListAction.CMD_DELETE %>');"><%= PfNameUtility.deleteHistory(params) %></button>
					<% } %>
					</span>
				</th>
			</tr>
		</table>
<%
	}
	// 一覧画面の場合
	if(view.equals(HumanArrayCardAction.KEY_VIEW_ARRAY_CARD) && divisionType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)){
		// 人事汎用表示テーブル設定取得
		ViewTableProperty viewTableProperty = viewConfig.getViewTable(viewTableKeys[i]);
		// 日付名設定
		params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_DATE_NAME,viewTableProperty.getDateName());
		// 画面区分(追加/編集)
		// 情報名配列取得
		String[] aryTitle = MospUtility.split(viewTitles[i], MospConst.APP_PROPERTY_SEPARATOR);
		// 情報名設定
		String title = aryTitle[0];
		// 画面区分(追加/編集)取得[HumanArrayCard.jspで設定]
		String imegeDivision = (String)params.getGeneralParam(HumanArrayCardAction.KEY_VIEW_ARRAY_CARD);
		// 履歴追加の場合
		if(imegeDivision != null && imegeDivision.equals(HumanArrayCardAction.CMD_ADD_SELECT)){
			// 情報名配列に履歴追加名がある場合
			if(aryTitle.length>1 &&!aryTitle[1].isEmpty() && aryTitle[1] != null){
				// 情報名設定
				title = aryTitle[1];
			}
		}
%>
		<table class="OverTable">
			<tr>
				<th colspan="3" class="ListTableTh" id="thDivision"><span class="TitleTh"><%= NameUtility.getName(params, title) %></span>
				</th>
			</tr>
		</table>
<%
	}
	// 表示テーブル設定
	params.addGeneralParam(PlatformHumanConst.PRM_HUMAN_VIEW_TABLE, viewTableKeys[i]);
%>
	<jsp:include page="<%= PlatformHumanConst.PATH_HUMAN_VIEW_TABLE_JSP %>" flush="false" />
<%
}
%>
</div>

