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
import = "jp.mosp.platform.constant.PlatformConst"
import = "jp.mosp.platform.utils.PlatformUtility"
import = "jp.mosp.time.constant.TimeConst"
import = "jp.mosp.time.calculation.action.CutoffErrorListAction"
import = "jp.mosp.time.calculation.action.TotalTimeAction"
import = "jp.mosp.time.calculation.vo.CutoffErrorListVo"
import = "jp.mosp.time.comparator.settings.CutoffErrorListDateComparator"
import = "jp.mosp.time.comparator.settings.CutoffErrorListEmployeeCodeComparator"
import = "jp.mosp.time.comparator.settings.CutoffErrorListEmployeeNameComparator"
import = "jp.mosp.time.comparator.settings.CutoffErrorListEmploymentAbbrComparator"
import = "jp.mosp.time.comparator.settings.CutoffErrorListPositionAbbrComparator"
import = "jp.mosp.time.comparator.settings.CutoffErrorListStateComparator"
import = "jp.mosp.time.comparator.settings.CutoffErrorListSectionAbbrComparator"
import = "jp.mosp.time.comparator.settings.CutoffErrorListTypeComparator"
import = "jp.mosp.time.comparator.settings.CutoffErrorListWorkPlaceAbbrComparator"
import = "jp.mosp.platform.utils.PfNameUtility"
%><%
MospParams params = (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
CutoffErrorListVo vo = (CutoffErrorListVo)params.getVo();
%>
<jsp:include page="<%=TimeConst.PATH_TIME_TOTAL_JSP%>" flush="false" />
<%=HtmlUtility.getListInfoFlex(params, vo.getList(), vo.getPageCommand(), vo.getDataPerPage(), vo.getSelectIndex())%>
<div class="FixList" id="divList">
	<table class="LeftListTable" id="list">
		<thead>
			<tr>
				<th class="ListSortTh" id="thDate" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=CutoffErrorListDateComparator.class.getName()%>'), '<%=CutoffErrorListAction.CMD_SORT%>')"><%=params.getName("Date")%><%=PlatformUtility.getSortMark(CutoffErrorListDateComparator.class.getName(), params)%></th>
				<th class="ListSortTh" id="thEmployeeCode" onclick="submitTransfer(event, null, null, new Array('<%=PlatformConst.PRM_TRANSFERRED_SORT_KEY%>', '<%=CutoffErrorListEmployeeCodeComparator.class.getName()%>'), '<%=CutoffErrorListAction.CMD_SORT%>')"><%=PfNameUtility.employeeCode(params)%><%= PlatformUtility.getSortMark(CutoffErrorListEmployeeCodeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployeeName" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffErrorListEmployeeNameComparator.class.getName() %>'), '<%= CutoffErrorListAction.CMD_SORT %>')"><%= params.getName("Employee") %><%= params.getName("FirstName") %><%= PlatformUtility.getSortMark(CutoffErrorListEmployeeNameComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thWorkType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffErrorListWorkPlaceAbbrComparator.class.getName() %>'), '<%= CutoffErrorListAction.CMD_SORT %>')"><%= params.getName("WorkPlace") %><%= PlatformUtility.getSortMark(CutoffErrorListWorkPlaceAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thEmployment" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffErrorListEmploymentAbbrComparator.class.getName() %>'), '<%= CutoffErrorListAction.CMD_SORT %>')"><%= params.getName("EmploymentContract") %><%= PlatformUtility.getSortMark(CutoffErrorListEmploymentAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thSection" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffErrorListSectionAbbrComparator.class.getName() %>'), '<%= CutoffErrorListAction.CMD_SORT %>')"><%= params.getName("Section") %><%= PlatformUtility.getSortMark(CutoffErrorListSectionAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thPosition" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffErrorListPositionAbbrComparator.class.getName() %>'), '<%= CutoffErrorListAction.CMD_SORT %>')"><%= params.getName("Position") %><%= PlatformUtility.getSortMark(CutoffErrorListPositionAbbrComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thType" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffErrorListTypeComparator.class.getName() %>'), '<%= CutoffErrorListAction.CMD_SORT %>')"><%= params.getName("Type") %><%= PlatformUtility.getSortMark(CutoffErrorListTypeComparator.class.getName(), params) %></th>
				<th class="ListSortTh" id="thState" onclick="submitTransfer(event, null, null, new Array('<%= PlatformConst.PRM_TRANSFERRED_SORT_KEY %>', '<%= CutoffErrorListStateComparator.class.getName() %>'), '<%= CutoffErrorListAction.CMD_SORT %>')"><%= params.getName("State") %><%= PlatformUtility.getSortMark(CutoffErrorListStateComparator.class.getName(), params) %></th>
			</tr>
		</thead>
		<tbody>
<% for (int i = 0; i < vo.getAryLblEmployeeCode().length; i++) { %>
			<tr>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblDate()[i]) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeCode()[i]) %></td>
				<td class="ListInputTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployeeName()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblWorkPlace()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblEmployment()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblSection()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblPosition()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblType()[i]) %></td>
				<td class="ListSelectTd"><%= HtmlUtility.escapeHTML(vo.getAryLblState()[i]) %></td>
			</tr>
<% } %>
		</tbody>
	</table>
</div>

