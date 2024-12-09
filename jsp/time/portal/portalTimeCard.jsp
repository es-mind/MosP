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
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.platform.portal.action.PortalAction"
import = "jp.mosp.platform.portal.vo.PortalVo"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.portal.bean.impl.PortalTimeCardBean"
%><%
//VOを準備
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PortalVo vo = (PortalVo)params.getVo();
//VOを取得できなかった場合
if (vo == null) {
	// 処理終了
	return;
}
// ポータル出退勤ボタン表示
// (1：始業/終業、2：始業/定終業、3：始業/定終業/残終業、4：出勤、9：非表示)
int timeButton = MospUtility.getInt(vo.getPortalParameter(PortalTimeCardBean.PRM_TIME_BUTTON));
// ポータル休憩ボタン表示(1：表示、2：非表示)
int restButton = MospUtility.getInt(vo.getPortalParameter(PortalTimeCardBean.PRM_REST_BUTTON));
%>
<div id="divTimeCard">
	<table class="Time">
		<tr>
			<td class="TimeCardDateTd"><span id="lblTimeCardDate"></span></td>
			<td rowspan="2">
				<table>
					<tr>
						<td>
<%
if (timeButton == 1 || timeButton == 2 || timeButton == 3) {
%>
						<button type="button" class="TimeCardButton" id="btnStart" onclick="submitTransfer(event, '', null, new Array( '<%= PortalAction.PRM_PORTAL_BEAN_CLASS_NAME %>','<%= PortalTimeCardBean.class.getName() %>','<%= PortalTimeCardBean.PRM_RECODE_TYPE %>','<%= PortalTimeCardBean.RECODE_START_WORK %>'),'<%= PortalAction.CMD_REGIST %>' );"><%= params.getName("StartWork") %></button>
<%
}
if (timeButton == 1) {
%>
						<button type="button" class="TimeCardButton" id="btnEnd" onclick="submitTransfer(event, '', applicationCheck, new Array('<%= PortalAction.PRM_PORTAL_BEAN_CLASS_NAME %>','<%= PortalTimeCardBean.class.getName() %>','<%= PortalTimeCardBean.PRM_RECODE_TYPE %>','<%= PortalTimeCardBean.RECODE_END_WORK %>'), '<%= PortalAction.CMD_REGIST %>');"><%= params.getName("EndWork") %></button>
<%
}
if (timeButton == 2 || timeButton == 3) {
%>
						<button type="button" class="TimeCardButton" id="btnRegularEnd" onclick="submitTransfer(event, '', applicationCheck, new Array('<%= PortalAction.PRM_PORTAL_BEAN_CLASS_NAME %>','<%= PortalTimeCardBean.class.getName() %>','<%= PortalTimeCardBean.PRM_RECODE_TYPE %>','<%= PortalTimeCardBean.RECODE_REGULAR_END %>'), '<%= PortalAction.CMD_REGIST %>');"><%= params.getName("RegularTime", "EndWork") %></button>
<%
}
if (timeButton == 3) {
%>
						<button type="button" class="TimeCardButton" id="btnOverEnd" onclick="submitTransfer(event, '', null, new Array('<%= PortalAction.PRM_PORTAL_BEAN_CLASS_NAME %>','<%= PortalTimeCardBean.class.getName() %>','<%= PortalTimeCardBean.PRM_RECODE_TYPE %>','<%= PortalTimeCardBean.RECODE_OVER_END %>'), '<%= PortalAction.CMD_REGIST %>');" style="background-color: yellow;"><%= params.getName("OvertimeWork", "EffectivenessExistence", "EndWork") %></button>
<%
}
if (timeButton == 4) {
%>
						<button type="button" class="TimeCardButton" id="btnRegularWork" onclick="submitTransfer(event, '', applicationCheck, new Array('<%= PortalAction.PRM_PORTAL_BEAN_CLASS_NAME %>','<%= PortalTimeCardBean.class.getName() %>','<%= PortalTimeCardBean.PRM_RECODE_TYPE %>','<%= PortalTimeCardBean.RECODE_REGULAR_WORK %>'), '<%= PortalAction.CMD_REGIST %>');"><%= params.getName("GoingWork") %></button><%
}
%>	
						</td>
					</tr>
<%
if (restButton == 1 && timeButton != 4) {
%>
					<tr>
						<td>
						<button type="button" class="TimeCardButton" id="btnRestStart" onclick="submitTransfer(event, '', null, new Array('<%= PortalAction.PRM_PORTAL_BEAN_CLASS_NAME %>','<%= PortalTimeCardBean.class.getName() %>','<%= PortalTimeCardBean.PRM_RECODE_TYPE %>','<%= PortalTimeCardBean.RECODE_START_REST %>'), '<%= PortalAction.CMD_REGIST %>');"><%= params.getName("RestTime", "Into") %></button>
						<button type="button" class="TimeCardButton" id="btnRestEnd"   onclick="submitTransfer(event, '', null, new Array('<%= PortalAction.PRM_PORTAL_BEAN_CLASS_NAME %>', '<%= PortalTimeCardBean.class.getName() %>','<%= PortalTimeCardBean.PRM_RECODE_TYPE %>','<%= PortalTimeCardBean.RECODE_END_REST %>'), '<%= PortalAction.CMD_REGIST %>');"><%= params.getName("RestTime", "Return") %></button>
						</td>
					</tr>
<%
}
%>
				</table>
			</td>
		</tr>
		<tr>
			<td class="TimeCardTimeTd">
				<span id="lblTimeCardTime"></span>
			</td>
		</tr>
	</table>

<%
if (params.getApplicationPropertyBool(TimeConst.APP_VIEW_PORTAL_TIME)) {
%>
<script language="Javascript">
<!--
var timeCardTime = new Date();
var srvTime = parseInt(jsTime);
var weeks = new Array('<%= params.getName("SundayAbbr") %>','<%= params.getName("MondayAbbr") %>','<%= params.getName("TuesdayAbbr") %>','<%= params.getName("WednesdayAbbr") %>','<%= params.getName("ThursdayAbbr") %>','<%= params.getName("FridayAbbr") %>','<%= params.getName("SaturdayAbbr") %>');
function timeCard() {
	srvTime = srvTime +1000;
	timeCardTime.setTime(srvTime);
	setInnerHtml("lblTimeCardDate", getDate(timeCardTime));
	setInnerHtml("lblTimeCardTime", getTime(timeCardTime));
}
timeCard();
window.setInterval(timeCard,1000);

function getDate(date) {
	return date.getFullYear()
	+ "<%= params.getName("Year") %>"
	+ timeCardZeroSuppress(date.getMonth() + 1)
	+ "<%= params.getName("Month") %>"
	+ timeCardZeroSuppress(date.getDate())
	+ "<%= params.getName("Day") %>"
	+ "<%= params.getName("FrontParentheses") %>"
	+ weeks[date.getDay()]
	+ "<%= params.getName("BackParentheses") %>";
}

function getTime(date) {
	return timeCardZeroSuppress(date.getHours())
	+ "<%= params.getName("SingleColon") %>"
	+ timeCardZeroSuppress(date.getMinutes())
	+ "<%= params.getName("SingleColon") %>"
	+ timeCardZeroSuppress(date.getSeconds());
}

function timeCardZeroSuppress(time) {
	if (time < 10) {
		return "0" + time;
	}
	return time;
}
//-->

</script>
<%
}
%>
<script language="Javascript">
<!--

function applicationCheck() {
	return confirm (getMessage(MSG_REGIST_CONFIRMATION, '<%= vo.getPortalParameter(PortalTimeCardBean.PRM_RECORD_END_STR) %>'));
}

//-->

</script>
</div>
