/*
 * MosP - Mind Open Source Project
 * Copyright (C) esMind, LLC  https://www.e-s-mind.com/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 勤怠一覧情報備考設定処理(休暇申請)。<br>
 */
public class AttendListReferRemarkHolidayBean extends AttendListReferRemarkBaseBean {
	
	@Override
	protected String getRemark(AttendListEntityInterface entity, AttendanceListDto dto) throws MospException {
		// 休暇申請用備考を準備
		Set<String> remarks = new LinkedHashSet<String>();
		// 接頭辞を準備
		String prefix = TimeNamingUtility.getVacation(mospParams);
		// 対象となる承認状況群を取得
		Set<String> statuses = getWorkflowStatuses(dto);
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = entity.getRequestEntity(dto);
		// 勤務形態コードを取得
		String workTypeCode = dto.getWorkTypeCode();
		// 休暇申請情報リストを取得
		List<HolidayRequestDtoInterface> requests = requestEntity.getHolidayRequestList(statuses);
		// ワークフロー情報群を取得
		Map<Long, WorkflowDtoInterface> workflows = requestEntity.getWorkflowMap();
		// 休暇申請情報リストを承認状態でソート
		WorkflowUtility.sortByStatus(requests, workflows);
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface request : requests) {
			// 時間単位の場合
			if (TimeRequestUtility.isHolidayRangeHour(request)) {
				// 次の休暇申請へ(時間単位は別途設定)
				continue;
			}
			// 連続休暇であり勤務形態コードが休日及び休日出勤日である場合
			if (TimeRequestUtility.isConsecutiveHolidays(request)
					&& TimeRequestUtility.isNotHolidayForConsecutiveHolidays(workTypeCode)) {
				// 次の休暇申請へ(連続休暇中の休日及び休日出勤日は休暇対象外)
				continue;
			}
			// 備考を設定
			remarks.add(getRequestWorkflowStatusRemark(prefix, workflows.get(request.getWorkflow())));
		}
		// 時間単位の備考を設定
		remarks.addAll(getHourlyHolidayRemarks(requests, workflows));
		// 備考の2番目の要素に1番目の要素が含まれる場合に1番目の要素を除去
		removeDuplicateRemark(remarks);
		// 休暇申請用備考を取得
		return getRemark(remarks);
	}
	
	/**
	 * 時間単位休暇用備考を取得する。<br>
	 * 一次戻と二次以上戻は、分けてカウントする。<br>
	 * @param dtos      休暇申請情報リスト
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 * @return 時間単位休暇用備考
	 */
	protected Set<String> getHourlyHolidayRemarks(List<HolidayRequestDtoInterface> dtos,
			Map<Long, WorkflowDtoInterface> workflows) {
		// 時間単位休暇申請用備考を準備
		Set<String> remarks = new LinkedHashSet<String>();
		// 時間単位休暇時間数群(キー：備考用各種申請承認状態文字列)を取得
		Map<String, Integer> map = getHourlyHolidays(dtos, workflows);
		// 時間単位休暇時間数群毎に処理
		for (Entry<String, Integer> entry : map.entrySet()) {
			// 備考用各種申請承認状態文字列と時間数を取得
			String status = entry.getKey();
			int hours = MospUtility.getInt(entry.getValue());
			// 備考を設定
			remarks.add(getHourlyHolidayRemark(status, hours));
		}
		// 時間単位休暇用備考を取得
		return remarks;
	}
	
	/**
	 * 時間単位休暇用備考を取得する。<br>
	 * @param status 備考用各種申請承認状態文字列
	 * @param hours  時間数
	 * @return 時間単位休暇用備考
	 */
	protected String getHourlyHolidayRemark(String status, int hours) {
		// 時間単位休暇用備考を準備
		StringBuilder remark = new StringBuilder(TimeNamingUtility.getVacation(mospParams));
		// 備考を設定
		remark.append(status);
		addRemark(remark, PfNameUtility.hour(mospParams));
		remark.append(hours);
		// 時間単位休暇用備考を取得
		return remark.toString();
	}
	
	/**
	 * 時間単位休暇時間数群(キー：備考用各種申請承認状態文字列)を取得する。<br>
	 * @param dtos      休暇申請情報リスト
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 * @return 時間単位休暇時間数群(キー：備考用各種申請承認状態文字列)
	 */
	protected Map<String, Integer> getHourlyHolidays(List<HolidayRequestDtoInterface> dtos,
			Map<Long, WorkflowDtoInterface> workflows) {
		// 時間単位休暇時間数群(キー：備考用各種申請承認状態文字列)を準備
		Map<String, Integer> hourlyHolidays = new LinkedHashMap<String, Integer>();
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 時間単位でない場合
			if (TimeRequestUtility.isHolidayRangeHour(dto) == false) {
				// 次の休暇申請へ
				continue;
			}
			// 備考用承認状態文字列を取得
			String status = getWorkflowStatusRemark(workflows.get(dto.getWorkflow()));
			// 時間単位休暇時間数を取得
			int hourlyHoliday = MospUtility.getInt(hourlyHolidays.get(status));
			// 時間単位休暇時間数を設定
			hourlyHolidays.put(status, hourlyHoliday + dto.getUseHour());
		}
		// 時間単位休暇時間数群(キー：備考用各種申請承認状態文字列)を取得
		return hourlyHolidays;
	}
	
	/**
	 * 備考の2番目の要素に1番目の要素が含まれる場合に1番目の要素を除去する。<br>
	 * <br>
	 * 例：<br>
	 * 1つ目(半日休暇：承認済)が2つ目(時間単位休暇：承認済)である場合、
	 * 「休暇済 休暇済 時1」となっている備考を「休暇済 時1」とする。<br>
	 * (但し、MosPの仕様上、半日休暇と時間単位休暇を同時に申請することはできない。)<br>
	 * @param remarks 備考
	 */
	protected void removeDuplicateRemark(Set<String> remarks) {
		// 備考が2つよりも少ない(1つ以下である)場合
		if (remarks.size() < 2) {
			// 処理不要
			return;
		}
		// 備考をリストとして取得
		List<String> list = new ArrayList<String>(remarks);
		// 備考の2番目の要素に1番目の要素が含まれる場合
		if (MospUtility.isContain(list.get(1), list.get(0))) {
			// 1番目の要素を除去
			remarks.remove(list.get(0));
		}
	}
	
}
