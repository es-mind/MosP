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
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.platform.system.base.PlatformSystemVo"
import = "jp.mosp.platform.system.vo.EmploymentMasterVo"
import = "jp.mosp.platform.system.action.EmploymentMasterAction"
import = "jp.mosp.platform.system.vo.SectionMasterVo"
import = "jp.mosp.platform.system.action.SectionMasterAction"
import = "jp.mosp.platform.system.vo.WorkPlaceMasterVo"
import = "jp.mosp.platform.system.action.WorkPlaceMasterAction"
import = "jp.mosp.platform.system.vo.PositionMasterVo"
import = "jp.mosp.platform.system.action.PositionMasterAction"
import = "jp.mosp.platform.system.vo.NamingMasterVo"
import = "jp.mosp.platform.system.action.NamingMasterAction"
import = "jp.mosp.platform.system.vo.AccountMasterVo"
import = "jp.mosp.platform.system.action.AccountMasterAction"
import = "jp.mosp.platform.message.vo.MessageCardVo"
import = "jp.mosp.platform.message.action.MessageCardAction"
import = "jp.mosp.platform.file.vo.ExportCardVo"
import = "jp.mosp.platform.file.action.ExportCardAction"
import = "jp.mosp.platform.file.vo.ImportCardVo"
import = "jp.mosp.platform.file.action.ImportCardAction"
import = "jp.mosp.platform.workflow.vo.SubApproverSettingVo"
import = "jp.mosp.platform.workflow.action.SubApproverSettingAction"
import = "jp.mosp.platform.workflow.vo.UnitCardVo"
import = "jp.mosp.platform.workflow.action.UnitCardAction"
import = "jp.mosp.platform.workflow.vo.RouteCardVo"
import = "jp.mosp.platform.workflow.action.RouteCardAction"
import = "jp.mosp.platform.workflow.vo.RouteApplicationCardVo"
import = "jp.mosp.platform.workflow.action.RouteApplicationCardAction"
%><%
// MosP処理情報及びVOを取得
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PlatformSystemVo vo = (PlatformSystemVo)params.getVo();
// 編集ヘッダタイトル宣言(デフォルト：編集)
String headerTitle = PfNameUtility.edit(params);
// 編集モード名称宣言(デフォルト履歴編集)
String editModeTitle = PfNameUtility.history(params) + PfNameUtility.edit(params);
// モード変更コマンド宣言
String insertModeCommand = MospConst.STR_EMPTY;
String addModeCommand = MospConst.STR_EMPTY;
String editModeCommand = MospConst.STR_EMPTY;
String replicationModeCommand = MospConst.STR_EMPTY;
// キーコード宣言
String keyCode = "";
// VO確認
if (vo instanceof EmploymentMasterVo) {
	// 雇用契約マスタ
	insertModeCommand = EmploymentMasterAction.CMD_INSERT_MODE;
	addModeCommand = EmploymentMasterAction.CMD_ADD_MODE;
	editModeCommand = EmploymentMasterAction.CMD_EDIT_MODE;
	keyCode = ((EmploymentMasterVo)vo).getTxtEditEmploymentCode();
} else if (vo instanceof SectionMasterVo) {
	// 所属マスタ
	insertModeCommand = SectionMasterAction.CMD_INSERT_MODE;
	addModeCommand = SectionMasterAction.CMD_ADD_MODE;
	editModeCommand = SectionMasterAction.CMD_EDIT_MODE;
	keyCode = ((SectionMasterVo)vo).getTxtEditSectionCode();
} else if (vo instanceof WorkPlaceMasterVo) {
	// 勤務地マスタ
	insertModeCommand = WorkPlaceMasterAction.CMD_INSERT_MODE;
	addModeCommand = WorkPlaceMasterAction.CMD_ADD_MODE;
	editModeCommand = WorkPlaceMasterAction.CMD_EDIT_MODE;
	keyCode = ((WorkPlaceMasterVo)vo).getTxtEditWorkPlaceCode();
} else if (vo instanceof PositionMasterVo) {
	// 職位マスタ
	insertModeCommand = PositionMasterAction.CMD_INSERT_MODE;
	addModeCommand = PositionMasterAction.CMD_ADD_MODE;
	editModeCommand = PositionMasterAction.CMD_EDIT_MODE;
	keyCode = ((PositionMasterVo)vo).getTxtEditPositionCode();
} else if (vo instanceof NamingMasterVo) {
	// 名称区分マスタ
	insertModeCommand = NamingMasterAction.CMD_INSERT_MODE;
	addModeCommand = NamingMasterAction.CMD_ADD_MODE;
	editModeCommand = NamingMasterAction.CMD_EDIT_MODE;
	keyCode = ((NamingMasterVo)vo).getTxtEditNamingItemCode();
} else if (vo instanceof AccountMasterVo) {
	// アカウント
	insertModeCommand = AccountMasterAction.CMD_INSERT_MODE;
	addModeCommand = AccountMasterAction.CMD_ADD_MODE;
	editModeCommand = AccountMasterAction.CMD_EDIT_MODE;
	keyCode = ((AccountMasterVo)vo).getTxtEditUserId();
} else if (vo instanceof MessageCardVo) {
	// メッセージ
	insertModeCommand = MessageCardAction.CMD_INSERT_MODE;
	replicationModeCommand = MessageCardAction.CMD_REPLICATION_MODE;
	editModeTitle = PfNameUtility.edit(params);
} else if (vo instanceof ImportCardVo) {
	// エクスポート
	insertModeCommand = ImportCardAction.CMD_INSERT_MODE;
	editModeTitle = PfNameUtility.edit(params);
} else if (vo instanceof ExportCardVo) {
	// インポート
	insertModeCommand = ExportCardAction.CMD_INSERT_MODE;
	editModeTitle = PfNameUtility.edit(params);
} else if (vo instanceof SubApproverSettingVo) {
	// 代理登録
	insertModeCommand = SubApproverSettingAction.CMD_INSERT_MODE;
	editModeTitle = PfNameUtility.edit(params);
} else if (vo instanceof UnitCardVo) {
	// 承認ユニット
	insertModeCommand = UnitCardAction.CMD_INSERT_MODE;
	addModeCommand = UnitCardAction.CMD_ADD_MODE;
	editModeCommand = UnitCardAction.CMD_SELECT_SHOW;
	keyCode = ((UnitCardVo)vo).getTxtUnitCode();
} else if (vo instanceof RouteCardVo) {
	// 承認ルート
	insertModeCommand = RouteCardAction.CMD_INSERT_MODE;
	addModeCommand = RouteCardAction.CMD_ADD_MODE;
	editModeCommand = RouteCardAction.CMD_SELECT_SHOW;
	keyCode = ((RouteCardVo)vo).getTxtRouteCode();
} else if (vo instanceof RouteApplicationCardVo) {
	// ルート適用
	insertModeCommand = RouteApplicationCardAction.CMD_INSERT_MODE;
	addModeCommand = RouteApplicationCardAction.CMD_ADD_MODE;
	editModeCommand = RouteApplicationCardAction.CMD_SELECT_SHOW;
	keyCode = ((RouteApplicationCardVo)vo).getTxtApplicationCode();
} 

//モード毎に設定
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
%>

<span class="TitleTh"><%= headerTitle %><%= NameUtility.cornerParentheses(params, PfNameUtility.newInsert(params)) %></span>
<%
} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
%>
<span class="TitleTh"><%= headerTitle %><%= NameUtility.cornerParentheses(params, PfNameUtility.addHistory(params)) %></span>
<a onclick="submitTransfer(event, 'divEdit', null, null, '<%= insertModeCommand %>');"><%= NameUtility.cornerParentheses(params, PfNameUtility.newInsert(params)) %></a>
<%
} else if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
<span class="TitleTh"><%= headerTitle %><%= NameUtility.cornerParentheses(params, editModeTitle) %></span>
<a onclick="submitTransfer(event, 'divEdit', null, null, '<%= insertModeCommand %>');"><%= NameUtility.cornerParentheses(params, PfNameUtility.newInsert(params)) %></a>
<%
	if (addModeCommand.isEmpty() == false) {
%>
<a onclick="submitTransfer(event, null, null, null, '<%= addModeCommand %>');"><%= NameUtility.cornerParentheses(params, PfNameUtility.addHistory(params)) %></a>
<%
	}
	if (replicationModeCommand.isEmpty() == false) {
%>
<a onclick="submitTransfer(event, null, confirmReplicationMode, null, '<%= replicationModeCommand %>')"><%= NameUtility.cornerParentheses(params, PfNameUtility.replication(params)) %></a>
<%
	}
	if (editModeCommand.isEmpty()) {
		return;
	}
%>
<div class="TableLabelSpan">
<%
if (MospUtility.isEmpty(vo.getLblBackActivateDate()) == false) {
%>
	<a class="ActivateDateRollLink"
			onclick="submitTransfer(event, 'divEdit', null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getLblBackActivateDate()) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(keyCode) %>'), '<%= editModeCommand %>');">
		&lt;&lt;&nbsp;
	</a>
<%
}
%>
	<span class=""><%= PfNameUtility.activateDate(params) %></span>
<%
if (vo.getLblNextActivateDate() == null || vo.getLblNextActivateDate().isEmpty()) {
%>
		&nbsp;<%= PfNameUtility.latest(params) %>
<%
} else {
%>
	<a class="ActivateDateRollLink"
			onclick="submitTransfer(event, 'divEdit', null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getLblNextActivateDate()) %>', '<%= PlatformConst.PRM_TRANSFERRED_CODE %>' , '<%= HtmlUtility.escapeHTML(keyCode) %>'), '<%= editModeCommand %>');">
		&nbsp;&gt;&gt;
	</a>
<%
}
%>
	&nbsp;<%= PfNameUtility.history(params) %>&nbsp;<%= PfNameUtility.allCount(params, vo.getCountHistory()) %>
</div>
<%
}
%>
