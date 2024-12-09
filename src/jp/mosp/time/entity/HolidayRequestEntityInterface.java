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

import java.util.List;
import java.util.Map;

import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 休暇申請エンティティインターフェース。<br>
 * 特定の人及び日における休暇申請情報を保持する。<br>
 */
public interface HolidayRequestEntityInterface {
	
	/**
	 * 時間単位有給休暇時間数を取得する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @return 時間単位有給休暇時間数
	 */
	int countHourlyPaidHolidays(boolean isCompleted);
	
	/**
	 * 時間単位有給休暇時間数を取得する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @param excludeId   除外レコード識別ID
	 * @return 時間単位有給休暇時間数
	 */
	int countHourlyPaidHolidays(boolean isCompleted, long excludeId);
	
	/**
	 * 時間単位休暇時間数を取得する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @return 時間単位有給休暇時間数
	 */
	int countHourlyHolidays(boolean isCompleted);
	
	/**
	 * 時間単位休暇申請が存在するかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @return 確認結果(true：時間単位休暇が存在する、false：時間単位休暇が存在しない)
	 */
	boolean isHourlyHolidayExist(boolean isCompleted);
	
	/**
	 * 休暇申請情報群(キー：レコード識別ID)を設定する。<br>
	 * @param holidayList 休暇申請情報リスト
	 */
	void setHolidays(List<HolidayRequestDtoInterface> holidayList);
	
	/**
	 * ワークフロー情報群(キー：ワークフロー番号)を設定する。<br>
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 */
	void setWorkflows(Map<Long, WorkflowDtoInterface> workflows);
	
}
