/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
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

import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.AdditionalLogicBeanInterface;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.entity.TimeDuration;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 時間単位休暇重複確認処理。<br>
 */
public class HolidayHourlyDuplicateCheckBean extends PlatformBean implements AdditionalLogicBeanInterface {
	
	/**
	 * 休暇申請参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface holidayRequestRefer;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanの準備
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public boolean doAdditionalLogic(Object... objects) throws MospException {
		// 休暇申請情報と行インデックスを取得
		HolidayRequestDtoInterface holidayRequestDto = MospUtility.castObject(objects[1]);
		Integer row = MospUtility.castObject(objects[2]);
		// 時間単位休暇の重複を確認
		checkHourlyHolidayDuplicate(holidayRequestDto, row);
		// 追加処理有り
		return true;
	}
	
	/**
	 * 時間単位休暇の重複を確認する。<bt>
	 * @param holidayRequestDto 対象休暇申請情報
	 * @param row               行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHourlyHolidayDuplicate(HolidayRequestDtoInterface holidayRequestDto, Integer row)
			throws MospException {
		// 時間単位休暇でない場合
		if (TimeRequestUtility.isHolidayRangeHour(holidayRequestDto) == false) {
			// 確認不要
			return;
		}
		// 個人IDと対象日(時間単位休暇は日の範囲指定が不可)を取得
		String personalId = holidayRequestDto.getPersonalId();
		Date targetDate = holidayRequestDto.getRequestStartDate();
		// 休暇申請情報群を取得
		List<HolidayRequestDtoInterface> dtos = holidayRequestRefer.getHolidayRequestListOnWorkflow(personalId,
				targetDate, targetDate);
		// 休暇申請情報群にある同一ワークフロー番号の情報を入れ替え
		TimeRequestUtility.replaceWorkflowDto(dtos, holidayRequestDto);
		// 時間単位休暇が無い場合
		if (TimeRequestUtility.hasHolidayRangeHour(dtos) == false) {
			// 確認不要
			return;
		}
		// 時間休時間間隔情報群を取得
		Set<TimeDuration> durations = TimeRequestUtility.getHourlyHolidayTimes(dtos);
		// 時間休時間間隔情報群の中に重複している時間間隔がある場合
		if (TimeUtility.isOverlap(durations)) {
			// 期間が重複する場合のメッセージを設定
			PfMessageUtility.addErrorTermOverlap(mospParams, TimeNamingUtility.hourlyHoliday(mospParams));
			// 確認終了
			return;
		}
	}
	
}
