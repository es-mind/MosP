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
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.NameUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PfNameUtility"
import = "jp.mosp.time.settings.action.ApplicationCardAction"
import = "jp.mosp.time.settings.action.CutoffMasterAction"
import = "jp.mosp.time.settings.action.HolidayMasterAction"
import = "jp.mosp.time.settings.action.PaidHolidayCardAction"
import = "jp.mosp.time.settings.action.ScheduleCardAction"
import = "jp.mosp.time.settings.action.TimeSettingCardAction"
import = "jp.mosp.time.settings.action.WorkTypeCardAction"
import = "jp.mosp.time.settings.action.WorkTypePatternCardAction"
import = "jp.mosp.time.settings.base.TimeSettingVo"
import = "jp.mosp.time.settings.vo.ApplicationCardVo"
import = "jp.mosp.time.settings.vo.CutoffMasterVo"
import = "jp.mosp.time.settings.vo.HolidayMasterVo"
import = "jp.mosp.time.settings.vo.PaidHolidayCardVo"
import = "jp.mosp.time.settings.vo.ScheduleCardVo"
import = "jp.mosp.time.settings.vo.TimeSettingCardVo"
import = "jp.mosp.time.settings.vo.WorkTypeCardVo"
import = "jp.mosp.time.settings.vo.WorkTypePatternCardVo"
%><%
// MosP処理情報及びVOを取得
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
TimeSettingVo vo = (TimeSettingVo)params.getVo();
// 編集ヘッダタイトル宣言(デフォルト：基本情報)
String headerTitle = PfNameUtility.basisInfo(params);
//ヘッダで使う文字列を作成
String newInsert = NameUtility.cornerParentheses(params, PfNameUtility.newInsert(params));
String addHistory = NameUtility.cornerParentheses(params, PfNameUtility.addHistory(params));
String edtiHistory = NameUtility.cornerParentheses(params, PfNameUtility.edtiHistory(params));
String replication = NameUtility.cornerParentheses(params, PfNameUtility.replication(params));
// モード変更コマンド宣言
String insertModeCommand = MospConst.STR_EMPTY;
String addModeCommand = MospConst.STR_EMPTY;
String editModeCommand = MospConst.STR_EMPTY;
String replicationModeCommand = MospConst.STR_EMPTY;
// キーコード宣言
String keyCode = MospConst.STR_EMPTY;
// VO確認
if (vo instanceof ApplicationCardVo) {
	// 設定適用管理
	insertModeCommand = ApplicationCardAction.CMD_INSERT_MODE;
	addModeCommand = ApplicationCardAction.CMD_ADD_MODE;
	editModeCommand = ApplicationCardAction.CMD_SELECT_SHOW;
	keyCode = ((ApplicationCardVo)vo).getTxtEditApplicationCode();
} else if (vo instanceof CutoffMasterVo) {
	// 締日管理
	headerTitle = PfNameUtility.edit(params);
	insertModeCommand = CutoffMasterAction.CMD_INSERT_MODE;
	addModeCommand = CutoffMasterAction.CMD_ADD_MODE;
	editModeCommand = CutoffMasterAction.CMD_EDIT_MODE;
	keyCode = ((CutoffMasterVo)vo).getTxtEditCutoffCode();
} else if (vo instanceof HolidayMasterVo) {
	// 休暇種別管理
	headerTitle = PfNameUtility.edit(params);
	insertModeCommand = HolidayMasterAction.CMD_INSERT_MODE;
	addModeCommand = HolidayMasterAction.CMD_ADD_MODE;
	editModeCommand = HolidayMasterAction.CMD_EDIT_MODE;
	keyCode = ((HolidayMasterVo)vo).getTxtEditHolidayCode();
} else if (vo instanceof PaidHolidayCardVo) {
	// 有給休暇設定
	insertModeCommand = PaidHolidayCardAction.CMD_INSERT_MODE;
	addModeCommand = PaidHolidayCardAction.CMD_ADD_MODE;
	editModeCommand = PaidHolidayCardAction.CMD_SELECT_SHOW;
	replicationModeCommand = PaidHolidayCardAction.CMD_REPLICATION_MODE;
	keyCode = ((PaidHolidayCardVo)vo).getTxtPaidHolidayCode();
} else if (vo instanceof ScheduleCardVo) {
	// カレンダ設定
	insertModeCommand = ScheduleCardAction.CMD_INSERT_MODE;
	addModeCommand = ScheduleCardAction.CMD_ADD_MODE;
	editModeCommand = ScheduleCardAction.CMD_SELECT_SHOW;
	replicationModeCommand = ScheduleCardAction.CMD_REPLICATION_MODE;
	keyCode = ((ScheduleCardVo)vo).getTxtScheduleCode();
} else if (vo instanceof TimeSettingCardVo) {
	// 勤怠設定
	insertModeCommand = TimeSettingCardAction.CMD_INSERT_MODE;
	addModeCommand = TimeSettingCardAction.CMD_ADD_MODE;
	editModeCommand = TimeSettingCardAction.CMD_SELECT_SHOW;
	replicationModeCommand = TimeSettingCardAction.CMD_REPLICATION_MODE;
	keyCode = ((TimeSettingCardVo)vo).getTxtSettingCode();
} else if (vo instanceof WorkTypeCardVo) {
	// 勤務形態
	insertModeCommand = WorkTypeCardAction.CMD_INSERT_MODE;
	addModeCommand = WorkTypeCardAction.CMD_ADD_MODE;
	editModeCommand = WorkTypeCardAction.CMD_SELECT_SHOW;
	keyCode = ((WorkTypeCardVo)vo).getTxtWorkTypeCode();
} else if (vo instanceof WorkTypePatternCardVo) {
	// 勤務形態パターン
	insertModeCommand = WorkTypePatternCardAction.CMD_INSERT_MODE;
	addModeCommand = WorkTypePatternCardAction.CMD_ADD_MODE;
	editModeCommand = WorkTypePatternCardAction.CMD_SELECT_SHOW;
	keyCode = ((WorkTypePatternCardVo)vo).getTxtPatternCode();
}
//新規登録モードの場合
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_INSERT)) {
%>
<span class="TitleTh"><%=headerTitle%><%=newInsert%></span>
<%
}
// 履歴追加モードの場合
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_ADD)) {
%>
<span class="TitleTh"><%=headerTitle%><%=addHistory%></span>
<a onclick="submitTransfer(event, 'divEdit', null, null, '<%=insertModeCommand%>');"><%=newInsert%></a>
<%
}
//履歴更新モードの場合
if (vo.getModeCardEdit().equals(PlatformConst.MODE_CARD_EDIT_EDIT)) {
%>
<span class="TitleTh"><%=headerTitle%><%=edtiHistory%></span>
<a onclick="submitTransfer(event, 'divEdit', null, null, '<%=insertModeCommand%>');"><%=newInsert%></a>
<a onclick="submitTransfer(event, null, null, null, '<%=addModeCommand%>');"><%=addHistory%></a>
<%
if (replicationModeCommand.isEmpty() == false) {
%>
<a onclick="submitTransfer(event, null, confirmReplicationMode, null, '<%=replicationModeCommand%>')"><%=replication%></a>
<%
}
%>
<div class="TableLabelSpan">
<%
// 前履歴有効日が設定されている場合
if (vo.getLblBackActivateDate() != null && vo.getLblBackActivateDate().isEmpty() == false) {
%>
	<a class="ActivateDateRollLink"
		onclick="submitTransfer(event, 'divEdit', null, new Array('<%=PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE%>', '<%=HtmlUtility.escapeHTML(vo.getLblBackActivateDate())%>', '<%=PlatformConst.PRM_TRANSFERRED_CODE%>' , '<%=HtmlUtility.escapeHTML(keyCode)%>'), '<%=editModeCommand%>');">
		&lt;&lt;&nbsp;
	</a>
<%
}
%>
	<span class=""><%=PfNameUtility.activateDate(params)%></span>
<%
// 次履歴有効日が設定されていない場合
if (vo.getLblNextActivateDate() == null || vo.getLblNextActivateDate().isEmpty()) {
%>
	&nbsp;<%=PfNameUtility.latest(params)%>
<%
} else {
// 次履歴有効日が設定されている場合
%>
	<a class="ActivateDateRollLink"
		onclick="submitTransfer(event, 'divEdit', null, new Array('<%=PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE%>', '<%=HtmlUtility.escapeHTML(vo.getLblNextActivateDate())%>', '<%=PlatformConst.PRM_TRANSFERRED_CODE%>' , '<%=HtmlUtility.escapeHTML(keyCode)%>'), '<%=editModeCommand%>');">
		&nbsp;&gt;&gt;
	</a>
<%
}
%>
	&nbsp;<%=PfNameUtility.history(params)%>&nbsp;<%=PfNameUtility.allCount(params, vo.getCountHistory())%>
</div>
<%
}
%>
