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
autoFlush    = "false"
errorPage    = "/jsp/common/error.jsp"
%><%@ page
import = "jp.mosp.framework.base.MospParams"
import = "jp.mosp.framework.constant.MospConst"
import = "jp.mosp.framework.utils.HtmlUtility"
import = "jp.mosp.framework.utils.MospUtility"
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.portal.vo.PortalVo"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.AttendanceCardAction"
import = "jp.mosp.time.portal.bean.impl.PortalAttendanceListBean"
import = "jp.mosp.time.utils.AttendanceUtility"
%><%
// VOを準備
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
PortalVo vo = (PortalVo)params.getVo();
// VOを取得できなかった場合
if (vo == null) {
	// 処理終了
	return;
}
String[][] addons = params.getProperties().getCodeArray(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_LIST_ADDONS, false);
%>
<div class="List" id="divSchedule">
	<table class="ListTable" id="list">
		<thead>
			<tr>
				<th class="ListSelectTh" id="thButton"></th>
				<th class="ListSelectTh" id="thDate"><%= params.getName("Day") %></th>
				<th class="ListSelectTh" id="thSingle"><%= params.getName("TheWeek") %></th>
				<th class="ListSelectTh" id="thType"><%= params.getName("Form") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("StartWork") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("EndWork") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("WorkTime") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("RestTime") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("GoingOut") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("LateLeaveEarly") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Inside","Remainder") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("LeftOut") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("WorkingHoliday") %></th>
				<th class="ListSelectTh" id="thTime"><%= params.getName("Midnight") %></th>
<%
for (String[] addon : addons) {
	String addonName = vo.getPortalParameter(AttendanceUtility.getPortalParamAddonColumnNameKey(addon[0]));
	if (MospUtility.isEmpty(addonName) == false) {
%>
				<th class="ListSelectTh" id="thTime"><%= HtmlUtility.escapeHTML(addonName) %></th>
<%
	}
}
%>
				<th class="ListSelectTh" id="thType"><%= params.getName("State") %></th>
				<th class="ListSelectTh" id="thRemark"><%= params.getName("Remarks") %></th>
			</tr>
		</thead>
		<tbody>
<%
for (int i = 0; i < vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_WORK_DATE).length; i++) {
%>
			<tr>
				<td class="ListSelectTd"><button type="button" class="Name2Button" id="btnSelect" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_ACTIVATE_DATE %>', '<%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_DATE)[i]) %>'), '<%= AttendanceCardAction.CMD_SELECT_SHOW_FROM_PORTAL %>');"><%= params.getName("Detail") %></button></td>
				<td class="ListSelectTd" title = "<%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_DATE)[i])%>"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_WORK_DATE  )[i]) %></td>
				<td class="ListSelectTd"><span <%= vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_WEEK_STYLE)[i] %>><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_WEEK)[i]) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_WORK_TYPE  )[i]) %></td>
				<td class="ListSelectTd"><span <%= vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_START_TIME_STYLE)[i] %>><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_START_TIME )[i]) %></span></td>
				<td class="ListSelectTd"><span <%= vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_END_TIME_STYLE)[i] %>><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_END_TIME   )[i]) %></span></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_WORK_TIME  )[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_REST_TIME  )[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_PRIVATE_TIME)[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_LATE_LEAVE_EARLY)[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_OVER_IN    )[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_OVER_OUT   )[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_HOLIDAY    )[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_LATE_NIGHT )[i]) %></td>
<%
for (String[] addon : addons) {
	String[] addonValues = vo.getPortalParameters(AttendanceUtility.getPortalParamAddonColumnValueKey(addon[0]));
	String[] addonClasses = vo.getPortalParameters(AttendanceUtility.getPortalParamAddonColumnClassKey(addon[0]));
	if (addonValues.length > i && addonClasses.length > i) {
%>
				<td class="ListSelectTd<%= HtmlUtility.escapeHTML(addonClasses[i]) %>"><%= HtmlUtility.escapeHTML(addonValues[i]) %></td>
<%
	}
}
%>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_STATUS     )[i]) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getPortalParameters(PortalAttendanceListBean.PRM_ATTENDANCE_LIST_REMARK     )[i]) %></td>
			</tr>
<%
}
%>
		</tbody>
	</table>
</div>
