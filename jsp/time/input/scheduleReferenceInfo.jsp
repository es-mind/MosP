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
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.input.action.ScheduleReferenceAction"
import = "jp.mosp.time.input.vo.ScheduleReferenceVo"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
ScheduleReferenceVo vo = (ScheduleReferenceVo)params.getVo();
%>

<table class="LeftListTable" id="scheduleReference_tblTotal">
		<tr>
			<th class="ListSelectTh" id="thTotalTime" rowspan="2">
				<div><%= params.getName("SumTotal") %></div>
				<div><%= params.getName("Time") %></div>
			</th>
			<th class="ListSelectTh" id="thTotalTime"            ><%= params.getName("Work"      ) %></th>
			<th class="ListSelectTh" id="thTotalTime"            ><%= params.getName("RestTime"  ) %></th>
			<th class="ListSelectTh" id="thTotalTime" rowspan="2"><%= params.getName("Frequency" ) %></th>
			<th class="ListSelectTh" id="thTotalTime"            ><%= params.getName("GoingWork" ) %></th>
			<th class="ListSelectTh" id="thTotalTime" rowspan="2"><%= params.getName("DayOff"    ) %></th>
			<th class="ListSelectTh" id="thTotalTime"            ><%= params.getName("Legal"     ) %></th>
			<th class="ListSelectTh" id="thTotalTime"            ><%= params.getName("Prescribed") %></th>
			<td class="Blank" colspan="7" rowspan="2"></td>
		</tr>
		<tr>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTotalWork             ()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTotalRest             ()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesWork             ()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesLegalHoliday     ()) %></td>
			<td class="SelectTd"><%= HtmlUtility.escapeHTML(vo.getLblTimesPrescribedHoliday()) %></td>
		</tr>
	</table>
