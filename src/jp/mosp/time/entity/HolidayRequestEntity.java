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
package jp.mosp.time.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇申請エンティティ。<br>
 */
public class HolidayRequestEntity implements HolidayRequestEntityInterface {
	
	/**
	 * 休暇申請情報群(キー：レコード識別ID)。<br>
	 */
	protected Map<Long, HolidayRequestDtoInterface>	holidays;
	
	/**
	 * ワークフロー情報群(キー：ワークフロー番号)。<br>
	 */
	protected Map<Long, WorkflowDtoInterface>		workflows;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public HolidayRequestEntity() {
		// 休暇申請情報群(キー：レコード識別ID)を準備
		holidays = new LinkedHashMap<Long, HolidayRequestDtoInterface>();
		// ワークフロー情報群(キー：ワークフロー番号)を準備
		workflows = new TreeMap<Long, WorkflowDtoInterface>();
	}
	
	@Override
	public int countHourlyPaidHolidays(boolean isCompleted) {
		// 時間単位有給休暇時間数を取得
		return countHourlyPaidHolidays(isCompleted, 0L);
	}
	
	@Override
	public int countHourlyPaidHolidays(boolean isCompleted, long excludeId) {
		// 時間単位有給休暇時間数を準備
		int count = 0;
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : holidays.values()) {
			// 休暇申請情報が時間単位有給休暇申請である場合
			if (isHourlyPaidHoliday(dto, isCompleted, excludeId)) {
				// 時間単位有給休暇時間数を加算
				count += TimeUtility.getHolidayHours(dto);
			}
		}
		// 時間単位有給休暇時間数を取得
		return count;
	}
	
	@Override
	public int countHourlyHolidays(boolean isCompleted) {
		// 時間単位休暇時間数を準備
		int count = 0;
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : holidays.values()) {
			// 休暇申請情報が時間単位休暇申請である場合
			if (isHourlyHoliday(dto, isCompleted, 0L)) {
				// 時間単位休暇時間数を加算
				count += TimeUtility.getHolidayHours(dto);
			}
		}
		// 時間単位休暇時間数を取得
		return count;
	}
	
	@Override
	public boolean isHourlyHolidayExist(boolean isCompleted) {
		// 時間単位休暇申請が存在するかを確認(時間単位休暇時間数が0より大きいことを確認)
		return countHourlyHolidays(isCompleted) > 0;
	}
	
	/**
	 * 休暇申請情報が時間単位休暇申請であるかを確認する。<br>
	 * @param dto         休暇申請情報
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @param excludeId   除外レコード識別ID
	 * @return 確認結果(true：時間単位休暇申請である、false：そうでない)
	 */
	protected boolean isHourlyHoliday(HolidayRequestDtoInterface dto, boolean isCompleted, long excludeId) {
		// 休暇申請情報が条件(承認済フラグ及び除外レコード識別ID)に合致しない場合
		if (isMatch(dto, isCompleted, excludeId) == false) {
			// 時間単位休暇申請でないと判断
			return false;
		}
		// 休暇申請情報が時間休であるかを確認
		return TimeRequestUtility.isHolidayRangeHour(dto);
	}
	
	/**
	 * 休暇申請情報が時間単位有給休暇申請であるかを確認する。<br>
	 * @param dto         休暇申請情報
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @param excludeId   除外レコード識別ID
	 * @return 確認結果(true：時間単位有給休暇申請である、false：そうでない)
	 */
	protected boolean isHourlyPaidHoliday(HolidayRequestDtoInterface dto, boolean isCompleted, long excludeId) {
		// 休暇申請情報が時間単位休暇申請でない場合
		if (isHourlyHoliday(dto, isCompleted, excludeId) == false) {
			// 時間単位有給休暇申請でないと判断
			return false;
		}
		// 休暇申請が有給休暇申請であるかを確認
		return TimeRequestUtility.isPaidHoliday(dto);
	}
	
	/**
	 * 休暇申請情報が条件(承認済フラグ及び除外レコード識別ID)に合致するかを確認する。<br>
	 * @param dto         休暇申請情報
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @param excludeId   除外レコード識別ID
	 * @return 確認結果(true：条件に合致する、false：合致しない)
	 */
	protected boolean isMatch(HolidayRequestDtoInterface dto, boolean isCompleted, long excludeId) {
		// 休暇申請情報が存在しない場合
		if (dto == null) {
			// 合致しないと判断
			return false;
		}
		// 休暇申請情報のレコード識別IDが除外レコード識別IDである場合
		if (dto.getRecordId() == excludeId) {
			// 合致しないと判断
			return false;
		}
		// 承認状況が合致するかを確認
		return WorkflowUtility.isStatusMatch(workflows.get(dto.getWorkflow()), isCompleted);
	}
	
	@Override
	public void setHolidays(List<HolidayRequestDtoInterface> holidayList) {
		// 休暇申請情報群(キー：レコード識別ID)を設定
		holidays = PlatformUtility.getRecordDtoMap(holidayList);
	}
	
	@Override
	public void setWorkflows(Map<Long, WorkflowDtoInterface> workflows) {
		// ワークフロー情報群(キー：ワークフロー番号)を設定
		this.workflows = workflows;
	}
	
}
